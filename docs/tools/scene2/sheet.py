"""sheet.py RESULT.json OUT.png: contact sheet of the sprite cells on a dark background (to look at)."""
import base64, io, json, sys
from PIL import Image
r = json.load(open(sys.argv[1]))
cells = r["cells"]
ims = [Image.open(io.BytesIO(base64.b64decode(c["url"].split(",")[1]))).convert("RGBA") for c in cells]
cols = len(r.get("yaws", [0])) if "yaws" in r else len(cells)
rows = (len(ims) + cols - 1) // cols
cw = max(i.size[0] for i in ims); ch = max(i.size[1] for i in ims)
sheet = Image.new("RGBA", (cols * cw, rows * ch), (24, 28, 36, 255))
for k, im in enumerate(ims):
  sheet.alpha_composite(im, ((k % cols) * cw, (k // cols) * ch))
sheet.convert("RGB").save(sys.argv[2])
print(r["name"], r["L"], r["W"], r["H"], sheet.size)
