#!/usr/bin/env python3
"""Hybrid StarView: the original 2.84.1 bytecode for every class we never changed on purpose, our build for the rest.

The app's source was recovered with a decompiler, which makes silent mistakes (wrong casts, int instead of double,
loops that never end). Those only show when that code runs. So instead of trusting the recovered copy, every class
that has no deliberate change keeps the original app's own compiled code, which ran fine; only the classes listed in
FEATURES (and classes that don't exist in 2.84.1 at all) come from our build.

  merge.py ORIG_SMALI_DIR NEW_SMALI_DIR OUT_DIR     (apktool d -r output folders; OUT_DIR gets the merged smali_* dirs)

Resource IDs: the original code has them as plain numbers, so the build keeps 2.84.1's IDs (app/stable-ids.txt).
check.py then verifies that every method / field the merged code uses exists and may be used.
"""
import json
import os
import re
import shutil
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import methodmerge  # noqa: E402
from decisions import DECISIONS, METHOD_LEVEL  # noqa: E402
from patches import PATCHES  # noqa: E402

# Classes (with all their inner, anonymous and lambda classes) that come from our build as a whole: changed on purpose
# since the recovery, or new. The classes in decisions.METHOD_LEVEL are merged method by method (methodmerge.py).
# Everything else in com/starpilot/starview (and every other class) is 2.84.1's own code.
FEATURES = {
  # driving screen, navigation, screen editor (2.85.x)
  "HudLayout", "HudLayoutKt", "NavOverlay", "NavPos", "NavCardView", "NavMapView",
  # settings defaults
  "UiSettingsKt",
  # automatic updates, freeze recorder (new)
  "AppUpdate", "AppUpdateKt", "AppUpdateReceiver", "AppReplacedReceiver", "Relaunch", "Overlay", "AnrWatch",
  # resource IDs (R) always from the build
  "R",
}
APP = "com/starpilot/starview/"


def family(rel):
  """smali_classes3/com/starpilot/starview/MainActivity$uiTick$1.smali -> com/starpilot/starview/MainActivity"""
  path = rel.split("/", 1)[1][:-len(".smali")]
  d, name = os.path.split(path)
  return d + "/" + name.split("$", 1)[0]


def classes(root):
  out = {}
  for dirpath, _, files in os.walk(root):
    for f in files:
      if f.endswith(".smali"):
        rel = os.path.relpath(os.path.join(dirpath, f), root)
        if rel.split("/", 1)[0].startswith("smali"):
          out[rel.split("/", 1)[1]] = rel          # class path -> dir-qualified path
  return out


def is_feature(fam):
  return fam.startswith(APP) and fam[len(APP):] in FEATURES


def is_method_level(fam):
  return fam.startswith(APP) and fam[len(APP):] in METHOD_LEVEL


def read(root, rel):
  with open(os.path.join(root, rel), encoding="utf-8") as f:
    return f.read()


def write(root, rel, text):
  dst = os.path.join(root, rel)
  os.makedirs(os.path.dirname(dst), exist_ok=True)
  with open(dst, "w", encoding="utf-8") as f:
    f.write(text)


def merge_methods(orig, new, out, o, n, ofam):
  """The METHOD_LEVEL families, method by method."""
  with open(os.path.join(os.path.dirname(os.path.abspath(__file__)), "base.json"), encoding="utf-8") as f:
    base = json.load(f)["members"]
  orig_class = lambda c: family(f"x/{c}.smali") in ofam and not is_feature(family(f"x/{c}.smali"))  # noqa: E731
  total = 0
  for short in sorted(METHOD_LEVEL):
    fam = APP + short
    o_rel = {c[:-6]: rel for c, rel in o.items() if family(rel) == fam}
    n_rel = {c[:-6]: rel for c, rel in n.items() if family(rel) == fam}
    build = {}
    for cls, d in DECISIONS.items():
      if cls.split("$", 1)[0] == short:
        build[APP + cls] = [sig for sig, v in d.items() if v == "build"]
    patches = {(APP + cls, sig): edits for (cls, sig), edits in PATCHES.items() if cls.split("$", 1)[0] == short}
    fm = methodmerge.FamilyMerge(fam, {c: read(orig, r) for c, r in o_rel.items()},
                                 {c: read(new, r) for c, r in n_rel.items()},
                                 {c: v for c, v in base.items() if c.split("$", 1)[0] == fam}, build, patches,
                                 orig_class)
    try:
      texts = fm.run()
    except methodmerge.MergeError as e:
      sys.exit(f"hybrid: {short}: {e}")
    home = os.path.dirname(o_rel[fam])
    for cls, text in texts.items():
      write(out, o_rel.get(cls, os.path.join(home, cls.rsplit("/", 1)[1] + ".smali")), text)
    total += len(texts)
    taken = len(texts) - len(o_rel)
    print(f"  {short}: {len(o_rel)} classes of 2.84.1 + {taken} of ours; " + "; ".join(fm.report))
  return total


def main(orig, new, out):
  o, n = classes(orig), classes(new)
  ofam = {family(v) for v in o.values()}
  if os.path.exists(out):
    shutil.rmtree(out)
  took_new, took_orig, new_only = 0, 0, []
  for cls, rel in n.items():
    fam = family(rel)
    if (is_feature(fam) or fam not in ofam) and not is_method_level(fam):
      dst = os.path.join(out, rel)
      os.makedirs(os.path.dirname(dst), exist_ok=True)
      shutil.copy2(os.path.join(new, rel), dst)
      took_new += 1
      if fam not in ofam and fam.startswith(APP):
        new_only.append(cls)
  for cls, rel in o.items():
    fam = family(rel)
    if is_feature(fam) or is_method_level(fam):
      continue
    dst = os.path.join(out, rel)
    os.makedirs(os.path.dirname(dst), exist_ok=True)
    shutil.copy2(os.path.join(orig, rel), dst)
    took_orig += 1
  took_methods = merge_methods(orig, new, out, o, n, ofam)
  # The recovered Java names Kotlin's companion classes "Companion_" (a Java class can't share the name of its own
  # "Companion" field). Where the outer class is 2.84.1's, our classes must use its real name, "Companion".
  orig_fams = {f for f in ofam if not is_feature(f)}
  renamed = 0
  for rel in classes(out).values():
    path = os.path.join(out, rel)
    if not is_feature(family(rel)) and family(rel) in ofam:
      continue                                    # 2.84.1's own class (or merged method by method): done
    with open(path, encoding="utf-8") as f:
      text = f.read()
    def fix(m):
      return m.group(0) if m.group(1) not in orig_fams else f"L{m.group(1)}$Companion;"
    new_text = re.sub(r"L([\w/]+)\$Companion_;", fix, text)
    if new_text != text:
      renamed += 1
      with open(path, "w", encoding="utf-8") as f:
        f.write(new_text)
  if renamed:
    print(f"Companion_ -> Companion in {renamed} classes")
  # one class may only be in one dex
  seen = {}
  for rel in classes(out).values():
    cls = rel.split("/", 1)[1]
    if cls in seen:
      sys.exit(f"duplicate class {cls}: {seen[cls]} and {rel}")
    seen[cls] = rel
  print(f"merged: {took_orig} classes from 2.84.1, {took_new} from the build, {took_methods} method by method "
        f"({len(new_only)} new: {', '.join(sorted({c[:-6].split('$')[0] for c in new_only})[:20])})")


if __name__ == "__main__":
  main(*sys.argv[1:4])
