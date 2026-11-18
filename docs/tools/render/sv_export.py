#!/usr/bin/env python3
"""sv_export.py NAME T0 T1: the lane data of a stretch of drive (unix times T0..T1) for StarView's off-tablet Scene
replay (docs/tools/render: lane_conv.py, SceneSeqTest.kt) - modelV2 lines / edges / path / leads, the comma's GPS, the
RTK receiver's fix as the comma passed it on (starpilotNavigation.phoneGps), radar lead and blinkers at 4 a second -
plus the OpenStreetMap extract around it and the segments' qcamera.ts. Reads only the finished rlogs it needs (run it
parked); nice 19 / idle I/O. Copy it to /data/sv_export.py on the comma. Writes /data/starview/export_NAME.tar.gz,
which holds where the car drove: never put it in git."""
import glob, gzip, io, json, os, sys, tarfile, urllib.request
os.nice(19)
try:
  import subprocess; subprocess.run(["ionice", "-c3", "-p", str(os.getpid())], check=False)
except Exception:
  pass
sys.path.insert(0, "/data/openpilot")
from openpilot.cereal import log
from openpilot.starpilot.system.starview.doorbelt import _messages   # streamed: a few MB, never the whole rlog

name, t0, t1 = sys.argv[1], float(sys.argv[2]), float(sys.argv[3])
root = "/data/media/0/realdata"
def rlog(d):
  return next((p for p in (d + "/rlog.zst", d + "/rlog.bz2", d + "/rlog") if os.path.exists(p)), None)
# a segment's rlog is last written at its end (60 s segments): those ending from 95 s before T0 to 70 s after T1
segs = sorted(d for d in glob.glob(root + "/*--*") if rlog(d) and t0 - 95 <= os.path.getmtime(rlog(d)) <= t1 + 70)
print("segments:", [os.path.basename(s) for s in segs])
out, x_idx, lat, lon = [], None, [], []
for d in segs:
  gps, model, radar, car, rtk, off, n = [], [], [], [], [], [], 0
  for dat in _messages(rlog(d)):
   with log.Event.from_bytes(dat, traversal_limit_in_words=2 ** 63 - 1) as m:
    w = m.which()
    if w == "gpsLocationExternal":
      g = m.gpsLocationExternal
      if g.hasFix and g.unixTimestampMillis > 0:
        off.append(g.unixTimestampMillis / 1e3 - m.logMonoTime / 1e9)
        gps.append({"mono": m.logMonoTime, "lat": g.latitude, "lon": g.longitude, "bearing": g.bearingDeg,
                    "acc": g.horizontalAccuracy, "speed": g.speed})
    elif w == "modelV2":
      n += 1
      if n % 5:
        continue
      v = m.modelV2
      if x_idx is None and len(v.laneLines):
        x_idx = [round(x, 3) for x in v.laneLines[0].x]
      ld = [{"p": l.prob, "x": l.x[0], "y": l.y[0]} for l in ([v.leadsV3[0]] if len(v.leadsV3) else []) if len(l.x)]
      model.append({"mono": m.logMonoTime, "lanes": [{"y": [round(y, 3) for y in l.y]} for l in v.laneLines],
                    "lane_p": [round(p, 3) for p in v.laneLineProbs], "lane_std": [round(s, 3) for s in v.laneLineStds],
                    "edges": [{"y": [round(y, 3) for y in e.y]} for e in v.roadEdges],
                    "edge_std": [round(s, 3) for s in v.roadEdgeStds],
                    "path": {"x": [round(x, 2) for x in v.position.x], "y": [round(y, 3) for y in v.position.y]}, "leads": ld})
    elif w == "radarState":
      l = m.radarState.leadOne
      radar.append({"mono": m.logMonoTime, "lead1": [l.dRel, l.yRel, l.vRel, bool(l.present)]})
    elif w == "starpilotNavigation":
      g = m.starpilotNavigation.phoneGps          # (unset: all zero)
      if g.hasFix and g.latitude != 0:
        rtk.append({"mono": g.sourceMonoTime or m.logMonoTime, "lat": g.latitude, "lon": g.longitude,
                    "acc": g.horizontalAccuracy, "bearing": g.bearingDeg, "speed": g.speed})
    elif w == "carState":
      c = m.carState
      car.append({"mono": m.logMonoTime, "lb": c.leftBlinker, "rb": c.rightBlinker})
  if not off:
    print(os.path.basename(d), "no GPS fix: skipped"); continue
  off.sort(); o = off[len(off) // 2]
  for lst in (gps, model, radar, car, rtk):
    for r in lst:
      r["t"] = round(r.pop("mono") / 1e9 + o, 3)
  keep = lambda lst: [r for r in lst if t0 - 90 <= r["t"] <= t1 + 5]   # 90 s before: the replay warms up on it
  seg = {"gps": keep(gps), "model": keep(model), "radar": keep(radar[::5]), "car": keep(car[::5]), "rtk": keep(rtk)}
  if seg["model"]:
    out.append(seg); lat += [g["lat"] for g in seg["gps"]]; lon += [g["lon"] for g in seg["gps"]]
  print(os.path.basename(d), len(seg["model"]), "model frames,", len(seg["rtk"]), "RTK fixes")
buf = io.BytesIO()
with tarfile.open(fileobj=buf, mode="w:gz") as tar:
  data = gzip.compress(json.dumps({"x_idx": x_idx, "windows": {name: {"segments": out}}}).encode())
  ti = tarfile.TarInfo("lane_%s.json.gz" % name); ti.size = len(data); tar.addfile(ti, io.BytesIO(data))
  if lat:
    bb = (min(lon) - 0.004, min(lat) - 0.004, max(lon) + 0.004, max(lat) + 0.004)
    try:
      osm = urllib.request.urlopen("https://api.openstreetmap.org/api/0.6/map?bbox=%.5f,%.5f,%.5f,%.5f" % bb, timeout=60).read()
      ti = tarfile.TarInfo("osm_%s.osm" % name); ti.size = len(osm); tar.addfile(ti, io.BytesIO(osm))
      print("OSM extract", len(osm) // 1024, "kB")
    except Exception as e:
      print("no OSM extract (offline mode?):", e, "- bbox", ",".join("%.5f" % x for x in bb))
  for d in segs:                                # the small road video of each segment, to compare
    q = d + "/qcamera.ts"
    if os.path.exists(q):
      tar.add(q, arcname="%s_%s_qcamera.ts" % (name, os.path.basename(d)))
dst = "/data/starview/export_%s.tar.gz" % name
open(dst, "wb").write(buf.getvalue())
print("wrote", dst, len(buf.getvalue()) // 1024, "kB")
