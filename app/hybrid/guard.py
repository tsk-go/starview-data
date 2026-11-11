#!/usr/bin/env python3
"""Stops a release that would silently drop a change.

The hybrid build ships 2.84.1's own code for every class not in merge.py's FEATURES. So a change to any other class's
source would build fine and then never reach the tablet. This fails the build when a source file changed since the
hybrid build was introduced belongs to a class that isn't in FEATURES (add it there, after checking that class with
the comparison tools in docs/tools, or keep the change out).

  guard.py        (run from the repo root, full git history needed)
"""
import os
import subprocess
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from merge import FEATURES  # noqa: E402

SRC = "app/app/src/main/java/com/starpilot/starview/"


def git(*args):
  return subprocess.run(["git", *args], check=True, capture_output=True, text=True).stdout


def main():
  base = git("log", "--diff-filter=A", "--format=%H", "--", "app/hybrid/merge.py").split()
  if not base:
    print("guard: hybrid build not committed yet, nothing to compare")
    return 0
  # modified files only: a file added since then holds new classes, which always come from the build
  changed = git("diff", "--name-only", "--diff-filter=MR", f"{base[-1]}..HEAD", "--", SRC).split()
  bad = []
  for path in changed:
    stem = os.path.splitext(os.path.basename(path))[0]
    if stem not in FEATURES and os.path.exists(path):
      bad.append(path)
  if bad:
    print("guard: these sources changed, but their classes ship as 2.84.1's code (not in FEATURES in app/hybrid/merge.py),")
    print("so the change would never reach the tablet:")
    for p in bad:
      print("  " + p)
    return 1
  print(f"guard: {len(changed)} changed source files, all in FEATURES")
  return 0


if __name__ == "__main__":
  sys.exit(main())
