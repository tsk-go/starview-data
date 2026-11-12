"""pack.py OUTDIR: raw_*.json (three.js renders) -> StarView assets/scene2: one WebP grid per model and height, the van per
elevation, and sprites.json describing them."""
import base64, io, json, os, sys
from PIL import Image
out = sys.argv[1]
os.makedirs(out, exist_ok=True)
COLS = 7
meta = {"models": {}}
def img(c):
  return Image.open(io.BytesIO(base64.b64decode(c["url"].split(",")[1]))).convert("RGBA")
total = 0
for m in ["car_sedan", "car_suv", "car_hatch", "car_coupe", "truck", "bus", "motorcycle", "bicycle", "person", "traffic_light", "stop_sign"]:
  r = json.load(open(f"raw_{m}.json"))
  mm = {"L": round(r["L"], 3), "W": round(r["W"], 3), "H": round(r["H"], 3), "yaws": r["yaws"], "pitches": r["pitches"], "ppm": r["ppm"], "sheets": []}
  for pi, p in enumerate(r["pitches"]):
    cells = [c for c in r["cells"] if c["pitch"] == p]
    cw, ch = cells[0]["w"], cells[0]["h"]
    rows = (len(cells) + COLS - 1) // COLS
    sheet = Image.new("RGBA", (COLS * cw, rows * ch), (0, 0, 0, 0))
    for k, c in enumerate(cells):
      sheet.paste(img(c), ((k % COLS) * cw, (k // COLS) * ch))
    fn = f"{m}_{p}.webp"
    sheet.save(f"{out}/{fn}", "WEBP", quality=90, method=6)
    total += os.path.getsize(f"{out}/{fn}")
    mm["sheets"].append({"file": fn, "cw": cw, "ch": ch, "ax": round(cells[0]["ax"], 1), "ay": round(cells[0]["ay"], 1), "cols": COLS, "w": sheet.size[0], "h": sheet.size[1]})
  meta["models"][m] = mm
r = json.load(open("raw_ego.json"))
ego = {"D": 17.24, "cy": 0.80, "h": 2000, "cells": []}
for c in r["cells"]:
  im = img(c)
  bb = im.getbbox()
  fn = f"ego_{c['elev']}.webp"
  im.crop(bb).save(f"{out}/{fn}", "WEBP", quality=92, method=6)
  total += os.path.getsize(f"{out}/{fn}")
  ego["cells"].append({"elev": c["elev"], "file": fn, "x": round(bb[0] - c["ax"], 1), "y": round(bb[1] - c["ay"], 1), "w": bb[2] - bb[0], "h": bb[3] - bb[1]})
meta["ego"] = ego
json.dump(meta, open(f"{out}/sprites.json", "w"), indent=1)
print("total bytes", total, "max sheet", max((s["w"], s["h"]) for mm in meta["models"].values() for s in mm["sheets"]))
