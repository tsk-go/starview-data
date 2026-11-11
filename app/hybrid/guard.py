#!/usr/bin/env python3
"""Stops a release that would silently drop a change.

The hybrid build ships 2.84.1's own code for every class not in merge.py's FEATURES, and in the decisions.METHOD_LEVEL
classes for every method decisions.py doesn't take from our build. So a change to that code would build fine and then
never reach the tablet. This fails the build when:
  - a source file changed since the hybrid build came in belongs to a class that is neither in FEATURES nor merged
    method by method;
  - a method of a METHOD_LEVEL class differs from the recovered source as first finished (base.json) and decisions.py
    says nothing about it (new methods are fine: they always ship);
  - a method that ships as 2.84.1's code ("orig" / "patch") changed again after that decision (its digest differs).

  guard.py BUILD_SMALI_DIR          (run from the repo root; full git history needed)
  guard.py BUILD_SMALI_DIR --digests   print the digests decisions.py should record now
"""
import json
import os
import subprocess
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
import changes  # noqa: E402
from decisions import DECISIONS, METHOD_LEVEL  # noqa: E402
from merge import FEATURES  # noqa: E402
from patches import PATCHES  # noqa: E402

SRC = "app/app/src/main/java/com/starpilot/starview/"
APP = changes.APP


def git(*args):
  return subprocess.run(["git", *args], check=True, capture_output=True, text=True).stdout


def files_check():
  base = git("log", "--diff-filter=A", "--format=%H", "--", "app/hybrid/merge.py").split()
  if not base:
    return []
  # modified files only: a file added since then holds new classes, which always come from the build
  changed = git("diff", "--name-only", "--diff-filter=MR", f"{base[-1]}..HEAD", "--", SRC).split()
  bad = []
  for path in changed:
    stem = os.path.splitext(os.path.basename(path))[0].split("$", 1)[0]
    if stem not in FEATURES and stem not in METHOD_LEVEL and os.path.exists(path):
      bad.append(f"{path}: its class ships as 2.84.1's code (not in FEATURES in merge.py, not in decisions.METHOD_LEVEL)")
  return bad


def sigs_for(head_members, cls, key):
  ms = head_members.get(APP + cls, {"m": []})["m"]
  return [s for s in ms if s == key or (key.endswith("(") and s.startswith(key))]


def digest_of(head, head_members, cls, key):
  sigs = sigs_for(head_members, cls, key) or [key]
  return changes.h("|".join(changes.method_digest(head, APP + cls, s) for s in sorted(sigs)))


def covered(cls, sig):
  for key in DECISIONS.get(cls, {}):
    if key == sig or (key.endswith("(") and sig.startswith(key)) or (sig.endswith("(") and key.startswith(sig)):
      return key
  return None


def methods_check(build_dir, print_digests=False):
  with open(os.path.join(HERE, "base.json"), encoding="utf-8") as f:
    base = json.load(f)
  texts = changes.load_families(build_dir, sorted(METHOD_LEVEL))
  head = changes.digest(texts)
  head_members = changes.members(texts)
  bad = []
  for cls_full, sigs in sorted(changes.changed(base["digest"], head).items()):
    cls = cls_full[len(APP):]
    base_m = set(base["members"].get(cls_full, {"m": []})["m"])
    for sig in sorted(sigs):
      new = (sig not in base_m) if not sig.endswith("(") else not any(s.startswith(sig) for s in base_m)
      if new:
        continue
      if not covered(cls, sig):
        bad.append(f"{cls}->{sig}: changed since the recovery, but decisions.py doesn't say what ships "
                   "(\"build\" = ours, or 2.84.1's code as it is / patched)")
  for cls, d in sorted(DECISIONS.items()):
    for key, v in sorted(d.items()):
      if v == "build":
        continue
      kind, recorded = v
      now = digest_of(head, head_members, cls, key)
      if print_digests:
        print(f'    "{key}": ("{kind}", "{now}"),   # {cls}')
      elif now != recorded:
        bad.append(f"{cls}->{key}: ships as 2.84.1's code ({kind}) but our source of it changed since that was decided "
                   f"(digest {now}, decided at {recorded}): port the change into patches.py (or make it \"build\") and "
                   "record the new digest")
      if kind == "patch" and (cls, key) not in PATCHES:
        bad.append(f"{cls}->{key}: decided \"patch\" but patches.py has no edits for it")
  for cls, key in PATCHES:
    v = DECISIONS.get(cls, {}).get(key)
    if not (isinstance(v, tuple) and v[0] == "patch"):
      bad.append(f"{cls}->{key}: patches.py edits it but decisions.py doesn't say \"patch\"")
  return bad


def main():
  build_dir = sys.argv[1]
  if "--digests" in sys.argv:
    methods_check(build_dir, print_digests=True)
    return 0
  bad = files_check() + methods_check(build_dir)
  if bad:
    print("guard: these changes would not reach the tablet as they are:")
    for b in bad:
      print("  " + b)
    return 1
  print("guard: every change since the recovery is accounted for")
  return 0


if __name__ == "__main__":
  sys.exit(main())
