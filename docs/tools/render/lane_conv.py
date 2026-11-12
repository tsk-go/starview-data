"""lane_conv.py OSM_DIR EXPORT.json.gz OUT_DIR: data for LaneSceneTest.kt (the new Scene view on a recorded drive).
OSM_DIR/*.osm (OpenStreetMap API "map" extracts around the drive) -> OUT_DIR/osm_all.json, the Overpass-style JSON
LaneMap.parse reads; the drive export (windows of segments: model lines, GPS, radar, carState) -> OUT_DIR/drive_<window>.json,
one frame per model message with the GPS at that moment. The output holds where the car drove: never put it in git."""
import bisect, glob, gzip, json, os, sys
import xml.etree.ElementTree as ET
OSM_DIR, EXPORT, OUT = sys.argv[1:4]
os.chdir(OUT)
els = {}
for f in sorted(glob.glob(OSM_DIR + "/*.osm")):
  root = ET.parse(f).getroot()
  nodes = {n.get("id"): (float(n.get("lat")), float(n.get("lon"))) for n in root.iter("node")}
  for w in root.iter("way"):
    t = {x.get("k"): x.get("v") for x in w.iter("tag")}
    if "highway" not in t: continue
    refs = [nd.get("ref") for nd in w.iter("nd")]
    if not all(r in nodes for r in refs): continue      # cut at the extract's edge: keep the complete copy if any
    g = [{"lat": nodes[r][0], "lon": nodes[r][1]} for r in refs]
    if len(g) >= 2: els[w.get("id")] = {"type": "way", "id": int(w.get("id")), "tags": t, "geometry": g}
json.dump({"elements": list(els.values())}, open("osm_all.json", "w"))
print("ways", len(els))
d = json.load(gzip.open(EXPORT, "rt"))
x_idx = d["x_idx"]
for wn, w in d["windows"].items():
  segs = w["segments"]
  if not segs: continue
  gps = sorted((r for s in segs for r in s["gps"]), key=lambda r: r["t"])
  model = sorted((r for s in segs for r in s["model"]), key=lambda r: r["t"])
  radar = sorted((r for s in segs for r in s["radar"]), key=lambda r: r["t"])
  car = sorted((r for s in segs for r in s["car"]), key=lambda r: r["t"])
  if not gps or not model: continue
  gt = [r["t"] for r in gps]; rt = [r["t"] for r in radar]; ct = [r["t"] for r in car]
  frames = []
  for m in model:
    i = bisect.bisect_left(gt, m["t"])
    if i == 0 or i >= len(gps): continue
    a, b = gps[i - 1], gps[i]
    k = (m["t"] - a["t"]) / max(1e-6, b["t"] - a["t"])
    db = ((b["bearing"] - a["bearing"] + 540) % 360) - 180
    g = {"lat": a["lat"] + k * (b["lat"] - a["lat"]), "lon": a["lon"] + k * (b["lon"] - a["lon"]),
         "bearing": (a["bearing"] + k * db) % 360, "acc": a["acc"], "speed": a["speed"] + k * (b["speed"] - a["speed"])}
    j = bisect.bisect_left(rt, m["t"]); rad = radar[min(j, len(radar) - 1)] if radar else None
    j = bisect.bisect_left(ct, m["t"]); cr = car[min(j, len(car) - 1)] if car else None
    lead = next((ld for ld in m.get("leads", []) if ld.get("p", 0) > 0.5 and ld.get("x")), None)
    fr = {"t": m["t"], **g, "lx": x_idx, "lanes": [ln["y"] for ln in m["lanes"]], "lp": m["lane_p"], "lstd": m["lane_std"],
          "edges": [e["y"] for e in m["edges"]], "estd": m["edge_std"], "px": m["path"]["x"], "py": m["path"]["y"],
          "lead": [lead["x"], lead["y"]] if lead else ([rad["lead1"][0] + 2.3, -rad["lead1"][1]] if rad and rad["lead1"][3] else None),
          "lb": bool(cr and cr.get("lb")), "rb": bool(cr and cr.get("rb"))}
    frames.append(fr)
  json.dump(frames, open(f"drive_{wn}.json", "w"))
  print(wn, len(frames), frames[0]["t"], frames[-1]["t"])
