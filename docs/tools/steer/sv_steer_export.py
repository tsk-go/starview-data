#!/usr/bin/env python3
"""sv_steer_export.py NAME T0 T1 [T0 T1 ...]: openpilot's steering on stretches of a drive (unix times), for finding a
wobble off the comma - what the model asked for (modelV2), what the turn assist / smoothing / lane centering made of it
(controlsState.desiredCurvature, starpilotLateralState), what the torque controller did (p / i / f / output), what went
to the car (carControl, carOutput) and what the car did (steering angle, rate, driver and EPS torque, yaw rate), with
the live steering ratio, torque and delay estimates and the steering settings. Fast signals at 50 a second.

Reads only the finished rlogs it needs, streamed (a few MB at a time), nice 19 / idle I/O: run it PARKED. Drives
older than the comma keeps are read from the EXTDATA stick: it takes the log archive's lock for that (no copy runs
meanwhile), mounts the stick read-only and unmounts it again. Writes /data/starview/steer_NAME.json.gz - no positions,
but still a drive's data: never put it in git. Copy it to /data/sv_steer_export.py on the comma."""
import glob, gzip, json, math, os, subprocess, sys, time
os.nice(19)
subprocess.run(["ionice", "-c3", "-p", str(os.getpid())], check=False, capture_output=True)
sys.path.insert(0, "/data/openpilot")
from openpilot.cereal import log
from openpilot.starpilot.system.starview.doorbelt import _messages   # streamed: a few MB, never the whole rlog

name = sys.argv[1]
wins = [(float(sys.argv[i]), float(sys.argv[i + 1])) for i in range(2, len(sys.argv) - 1, 2)]
if not wins:
  sys.exit("usage: sv_steer_export.py NAME T0 T1 [T0 T1 ...]")
SRC = "/data/media/0/realdata"
MNT = "/data/extstorage/mnt"
LOCK = "/dev/shm/starview_logarchive.lock"
OUT = "/data/starview/steer_%s.json.gz" % name

# service -> keep every n-th message
EVERY = {"carState": 2, "carControl": 2, "carOutput": 2, "controlsState": 2, "starpilotLateralState": 5, "modelV2": 1,
         "deviceMotion": 5, "vehicleParameters": 5, "lateralTorqueParameters": 1, "lateralDelay": 1, "radarState": 2,
         "selfdriveState": 10, "onroadEvents": 1, "lateralManeuverPlan": 1, "clocks": 1, "gpsLocationExternal": 1}
PARAMS = ["TurnAssist", "LaneCentering", "LaneCenteringStrength", "LaneCenterOffset", "LaneCenteringPauseOnSignal",
          "HighwaySmoothingLevel", "LatSmoothSeconds", "LateralControllerSelection", "LateralManeuverMode",
          "SteeringLearningPreferences", "SteeringDelayPreferences", "AdvancedLateralTune", "SteerFriction",
          "SteerFrictionStock", "SteerLatAccel", "SteerLatAccelStock", "TorqueOverrideDocument", "AlwaysOnLateral",
          "PauseLateralSpeed", "PauseLateralOnSignal", "ExperimentalMode", "AutomaticSteeringLiveTrials",
          "AutomaticSteeringProfiles", "DrivingModel", "Model", "ModelName"]


def rnd(v):
  if isinstance(v, bool) or v is None:
    return v
  if isinstance(v, float):
    return None if not math.isfinite(v) else float("%.5g" % v)
  return v


def flat(d, pre="", out=None, depth=0):
  """numbers, bools and short strings of a to_dict(); lists of up to 6 numbers as name.0 .. name.5; nothing deeper than 4"""
  out = {} if out is None else out
  for k, v in d.items():
    key = pre + k
    if isinstance(v, dict):
      if depth < 4:
        flat(v, key + ".", out, depth + 1)
    elif isinstance(v, (list, tuple)):
      if 0 < len(v) <= 6 and all(isinstance(x, (int, float)) and not isinstance(x, bool) for x in v):
        for i, x in enumerate(v):
          out["%s.%d" % (key, i)] = rnd(float(x))
    elif isinstance(v, (int, float, bool)):
      out[key] = rnd(v)
    elif isinstance(v, str) and len(v) <= 40:
      out[key] = v
  return out


def at(arr, xs, x):
  """arr's value at xs = x (model points), linear"""
  try:
    if len(arr) == 0 or len(xs) != len(arr):
      return None
    for i in range(1, len(xs)):
      if xs[i] >= x:
        f = (x - xs[i - 1]) / max(xs[i] - xs[i - 1], 1e-6)
        return rnd(arr[i - 1] + f * (arr[i] - arr[i - 1]))
    return rnd(arr[-1])
  except Exception:
    return None


def get(o, path):
  """o's field at a dotted path, None when this build's schema doesn't have it"""
  try:
    for k in path.split("."):
      o = getattr(o, k)
    return o if isinstance(o, (int, float, bool)) else str(o)
  except Exception:
    return None


FIELDS = {
  "carState": {"v": "vEgo", "a": "aEgo", "vRaw": "vEgoRaw", "standstill": "standstill", "angle": "steeringAngleDeg",
               "rate": "steeringRateDeg", "torque": "steeringTorque", "torqueEps": "steeringTorqueEps",
               "pressed": "steeringPressed", "faultTemp": "steerFaultTemporary", "lb": "leftBlinker", "rb": "rightBlinker",
               "brake": "brakePressed", "gas": "gasPressed", "yawRate": "yawRate"},
  "carControl": {"latActive": "latActive", "longActive": "longActive", "torque": "actuators.torque",
                 "curvature": "actuators.curvature", "angle": "actuators.steeringAngleDeg", "accel": "actuators.accel",
                 "longState": "actuators.longControlState"},
  "carOutput": {"torque": "actuatorsOutput.torque", "torqueCan": "actuatorsOutput.torqueOutputCan",
                "angle": "actuatorsOutput.steeringAngleDeg"},
  "radarState": {"d": "leadOne.dRel", "y": "leadOne.yRel", "vRel": "leadOne.vRel", "vLead": "leadOne.vLead",
                 "present": "leadOne.present"},
  "deviceMotion": {"yaw": "angularVelocityDevice.z", "roll": "orientationNED.x", "pitch": "orientationNED.y"},
  "selfdriveState": {"enabled": "enabled", "active": "active", "state": "state", "experimental": "experimentalMode",
                     "alert": "alertText1"},
}


def row(w, m):
  if w in FIELDS:
    o = getattr(m, w)
    return {k: get(o, f) for k, f in FIELDS[w].items()}
  if w == "controlsState":
    c = m.controlsState
    r = {"curvature": get(c, "curvature"), "desiredCurvature": get(c, "desiredCurvature")}
    try:
      lw = c.lateralControlState.which()
      r["lat"] = str(lw)
      r.update(flat(getattr(c.lateralControlState, lw).to_dict(), "lat."))
    except Exception:
      pass
    return r
  if w == "modelV2":
    v = m.modelV2
    r = {"desiredCurvature": get(v, "action.desiredCurvature"), "desiredAccel": get(v, "action.desiredAcceleration"),
         "lc": get(v, "meta.laneChangeState"), "dropPerc": get(v, "frameDropPerc")}
    try:
      xs = list(v.position.x)
      for x in (3, 6, 10, 20, 40):
        r["pathY%d" % x] = at(list(v.position.y), xs, x)
      r["yawRate0"] = v.orientationRate.z[0]
      for i, p in enumerate(v.laneLineProbs):
        r["laneP%d" % i] = p
      for i, l in enumerate(v.laneLines):
        r["laneY%d" % i] = at(list(l.y), list(l.x), 6)
      for i, e in enumerate(v.roadEdges):
        r["edgeY%d" % i] = at(list(e.y), list(e.x), 6)
      if len(v.leadsV3):
        l = v.leadsV3[0]
        r["leadP"] = l.prob
        if len(l.x):
          r["leadX"], r["leadY"] = l.x[0], l.y[0]
    except Exception:
      pass
    return r
  if w == "onroadEvents":
    return {"events": ",".join(str(e.name) for e in m.onroadEvents)}
  if w in ("vehicleParameters", "lateralTorqueParameters", "lateralDelay", "starpilotLateralState", "lateralManeuverPlan"):
    return flat(getattr(m, w).to_dict())
  return None


def rlog(d):
  return next((p for p in (d + "/rlog.zst", d + "/rlog.bz2", d + "/rlog") if os.path.exists(p)), None)


def head_time(d):
  """the segment's start (unix), from its first clock or GPS time - only its first messages are read"""
  for i, dat in enumerate(_messages(rlog(d))):
    if i > 20000:
      return None
    with log.Event.from_bytes(dat, traversal_limit_in_words=2 ** 63 - 1) as m:
      w = m.which()
      if w == "clocks" and m.clocks.wallTimeNanos > 1.6e18:
        return m.clocks.wallTimeNanos / 1e9
      if w == "gpsLocationExternal" and m.gpsLocationExternal.hasFix and m.gpsLocationExternal.unixTimestampMillis > 0:
        return m.gpsLocationExternal.unixTimestampMillis / 1e3
  return None


def segments(root):
  """(segment end, folder) of the segments with any of the stretches. A segment's rlog is last written at its end (60 s
  segments), so those whose rlog was last written from 20 s before T0 to 70 s after T1; when that finds too few (a log
  rewritten later has a later time), every segment whose rlog is newer than T0 is asked its own start time."""
  found = [(os.path.getmtime(rlog(d)), d) for d in glob.glob(root + "/*--*")
           if rlog(d) and any(a - 20 <= os.path.getmtime(rlog(d)) <= b + 70 for a, b in wins)]
  if covered(found):
    return sorted(found)
  found = []
  for d in glob.glob(root + "/*--*"):
    if not rlog(d) or os.path.getmtime(rlog(d)) < min(a for a, _ in wins) - 20:
      continue
    try:
      t0 = head_time(d)
    except Exception:
      t0 = None
    if t0 is not None and any(a - 80 <= t0 <= b + 5 for a, b in wins):
      found.append((t0 + 60, d))
  return sorted(found)


def covered(segs):
  """every stretch has about a segment per minute here"""
  return all(sum(a - 20 <= t <= b + 70 for t, _ in segs) >= (b - a) / 60 for a, b in wins)


def sh(cmd):
  return subprocess.run(("" if os.geteuid() == 0 else "sudo -n ") + cmd, shell=True, capture_output=True, text=True)


def read(segs):
  data, n_all = {}, 0
  for _, d in segs:
    rows, cnt, wall = [], {}, []
    for dat in _messages(rlog(d)):
      with log.Event.from_bytes(dat, traversal_limit_in_words=2 ** 63 - 1) as m:
        w = m.which()
        if w not in EVERY:
          continue
        if w == "clocks":
          if m.clocks.wallTimeNanos > 1.6e18:
            wall.append(m.clocks.wallTimeNanos / 1e9 - m.logMonoTime / 1e9)
          continue
        if w == "gpsLocationExternal":
          g = m.gpsLocationExternal
          if g.hasFix and g.unixTimestampMillis > 0:
            wall.append(g.unixTimestampMillis / 1e3 - m.logMonoTime / 1e9)
          continue
        cnt[w] = cnt.get(w, 0) + 1
        if cnt[w] % EVERY[w]:
          continue
        try:
          r = row(w, m)
        except Exception as e:
          r = {"error": str(e)[:80]}
        if r is not None:
          rows.append((w, m.logMonoTime, r))
    if not wall:
      print(os.path.basename(d), "no clock or GPS time: skipped")
      continue
    wall.sort()
    off = wall[len(wall) // 2]
    kept = 0
    for w, mono, r in rows:
      t = mono / 1e9 + off
      if not any(a - 20 <= t <= b + 5 for a, b in wins):
        continue
      s = data.setdefault(w, {"t": []})
      s["t"].append(round(t, 3))
      n = len(s["t"])
      for k, v in r.items():
        col = s.setdefault(k, [None] * (n - 1))
        col.extend([None] * (n - 1 - len(col)))
        col.append(rnd(v))
      kept += 1
    n_all += kept
    print(os.path.basename(d), kept, "rows")
  for s in data.values():       # columns a later row didn't have
    for k, col in s.items():
      col.extend([None] * (len(s["t"]) - len(col)))
  return data, n_all


def params():
  out = {}
  for k in PARAMS:
    for base in ("/data/params/d", "/dev/shm/params/d"):
      try:
        out[k] = open(os.path.join(base, k), "rb").read()[:4000].decode("utf-8", "replace")
        break
      except OSError:
        pass
  return out


def when(t):
  return time.strftime("%a %b %d %H:%M", time.localtime(t))


def main():
  segs = segments(SRC)
  where = "comma"
  locked = mounted = False
  ends = sorted(os.path.getmtime(rlog(d)) for d in glob.glob(SRC + "/*--*") if rlog(d))
  print("on the comma: %d segments for those times; its drives go back to %s" % (len(segs), when(ends[0]) if ends else "-"))
  try:
    if not covered(segs):
      where = "stick"
      for _ in range(60):                       # a copy running: wait for it (up to 10 min), never alongside it
        try:
          fd = os.open(LOCK, os.O_CREAT | os.O_EXCL | os.O_WRONLY)
          os.write(fd, str(os.getpid()).encode())
          os.close(fd)
          locked = True
          break
        except FileExistsError:
          try:
            os.kill(int(open(LOCK).read()), 0)
            alive = True
          except PermissionError:
            alive = True
          except (OSError, ValueError):
            alive = False
          if alive:
            print("the log copy to the stick is running: waiting...")
            time.sleep(10)
          else:
            try:
              os.unlink(LOCK)
            except OSError:
              pass
      if not locked:
        sys.exit("the log copy didn't finish within 10 min: try again later")
      if not any(l.split()[1] == MNT for l in open("/proc/mounts")):
        dev = sh("blkid -L EXTDATA").stdout.strip()
        if not dev.startswith("/dev/"):
          msg = ("no EXTDATA stick found - it's on the USB hub, which is switched off a few minutes after the car: "
                 "turn the car on (Park) and run this again")
          if not segs:
            sys.exit(msg)
          print(msg + "; going on with what the comma has")
          dev = None
        if dev:
          os.makedirs(MNT, exist_ok=True)
          r = sh("mount -t ext4 -o ro,noatime %s %s" % (dev, MNT))
          if r.returncode:
            sys.exit("couldn't mount the stick: " + (r.stdout + r.stderr).strip())
          mounted = True
      have = {os.path.basename(d) for _, d in segs}      # the comma's own copy first, the rest from the stick
      if mounted or any(l.split()[1] == MNT for l in open("/proc/mounts")):
        segs = sorted(segs + [(t, d) for t, d in segments(MNT) if os.path.basename(d) not in have])
    print("reading", len(segs), "segments from the", where + ":", [os.path.basename(d) for _, d in segs])
    if not segs:
      sys.exit("no drive found for those times")
    data, n = read(segs)
  finally:
    if mounted:
      sh("umount %s" % MNT)
    if locked:
      try:
        os.unlink(LOCK)
      except OSError:
        pass
  out = {"name": name, "windows": wins, "from": where, "params": params(), "services": data}
  with gzip.open(OUT, "wt") as f:
    json.dump(out, f, separators=(",", ":"))
  print("wrote", OUT, "(%d rows, %.1f MB)" % (n, os.path.getsize(OUT) / 1e6))


main()
