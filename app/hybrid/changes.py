#!/usr/bin/env python3
"""Which methods did we change on purpose? Compares our build now with our build of the recovered source as first
finished (commit 755431e, before any feature work; its summary is app/hybrid/base.json). A method counts as changed
when its code differs (line numbers, local names and javac / d8 numbering ignored), or when an anonymous class or
lambda it creates changed. decisions.py must say for every changed method whether 2.84.1's code ships (as it is or
patched) or ours; guard.py checks that.

  changes.py base ROOT                 base.json for ROOT (apktool d -r output of the 755431e build)
  changes.py diff BASE ROOT            "Class sig" per changed method (BASE: base.json or an apktool folder)
  changes.py hash ROOT Class sig...    the digest of those methods now (decisions.py records it for 2.84.1 code)
"""
import collections
import hashlib
import json
import os
import re
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from smali import SmaliClass, normalize  # noqa: E402

APP = "com/starpilot/starview/"
ENCL_RX = re.compile(r"Ldalvik/annotation/EnclosingMethod;\s*\n\s*value = L([^;]+);->(\S+)")
ENCL_CLASS_RX = re.compile(r"Ldalvik/annotation/EnclosingClass;\s*\n\s*value = L([^;]+);")
ANON_RX = re.compile(r"Ldalvik/annotation/InnerClass;\s*\n\s*accessFlags = \S+\s*\n\s*name = null")
ANON = re.compile(r"\$\d+$")
JAVAC_LAMBDA = re.compile(r"^lambda\$(.+?)\$\d+\(")


def method_level():
  from decisions import METHOD_LEVEL
  return sorted(METHOD_LEVEL)


def load_families(root, fams):
  out = {}
  wanted = set(fams)
  for dirpath, _, files in os.walk(root):
    for f in files:
      if not f.endswith(".smali"):
        continue
      stem = f[:-6]
      if stem.split("$", 1)[0] not in wanted:
        continue
      rel = os.path.relpath(os.path.join(dirpath, f), root).split("/", 1)[1]
      if rel.startswith(APP):
        with open(os.path.join(dirpath, f), encoding="utf-8") as fh:
          out[APP + stem] = fh.read()
  return out


def enclosing(text):
  """Where an anonymous class is created: (class, method); in a field initializer: (class, "<init>(")."""
  m = ENCL_RX.search(text)
  if m:
    return m.group(1), m.group(2)
  if ANON_RX.search(text):
    m = ENCL_CLASS_RX.search(text)
    if m:
      return m.group(1), "<init>("
  return None


def top_enclosing(texts, owner, sig):
  """Walk anonymous classes and javac lambda bodies up to a method of a named class."""
  seen = set()
  while (owner, sig) not in seen:
    seen.add((owner, sig))
    m = JAVAC_LAMBDA.match(sig)
    if m:                                   # lambda$onCreate$3(...) -> onCreate (any overload)
      sig = m.group(1) + "("
      continue
    if ANON.search(owner) and owner in texts:
      e = enclosing(texts[owner])
      if e:
        owner, sig = e
        continue
    break
  return owner, sig


def h(s):
  return hashlib.sha256(s.encode()).hexdigest()[:12]


def digest(texts):
  """key -> hash. "m|Class|sig": a named class's method; "a|Class|sig": the anonymous classes a method creates;
  "l|Class|name(": the javac lambda bodies of every overload of a method."""
  d = {}
  anon = collections.defaultdict(list)
  lam = collections.defaultdict(list)
  for cls, t in texts.items():
    e = enclosing(t) if ANON.search(cls) else None
    if e and e[0].split("$", 1)[0] == cls.split("$", 1)[0]:
      o, s = top_enclosing(texts, *e)
      anon[(o, s)].append(normalize(t.splitlines(keepends=True)[1:]))
      continue
    if "$$" in cls:
      continue                              # d8's synthetic classes: they follow the code that uses them
    for sig, lines in SmaliClass(t).methods:
      m = JAVAC_LAMBDA.match(sig)
      if m:
        lam[(cls, m.group(1) + "(")].append(normalize(lines[1:]))
      elif not sig.startswith(("$r8$lambda$", "-$$Nest$")):
        d[f"m|{cls}|{sig}"] = h(normalize(lines))
  for (o, s), bodies in anon.items():
    d[f"a|{o}|{s}"] = h("\0".join(sorted(bodies)))
  for (o, s), bodies in lam.items():
    d[f"l|{o}|{s}"] = h("\0".join(sorted(bodies)))
  return d


def members(texts):
  out = {}
  for cls, t in texts.items():
    c = SmaliClass(t)
    out[cls] = {"m": sorted(s for s, _ in c.methods), "f": sorted(f"{k[0]}:{k[1]}" for k, _ in c.fields)}
  return out


def changed(base_digest, head_digest):
  """{class: set of sigs (or "name(" = every overload)} that differ."""
  ch = collections.defaultdict(set)
  for k in set(base_digest) | set(head_digest):
    if base_digest.get(k) != head_digest.get(k):
      _, cls, sig = k.split("|", 2)
      ch[cls].add(sig)
  return ch


def method_digest(head_digest, cls, sig):
  """What decisions.py records for a method shipped as 2.84.1's code: everything changes.py looks at for it."""
  name = sig.split("(")[0] + "("
  parts = [head_digest.get(f"m|{cls}|{sig}", "-"), head_digest.get(f"a|{cls}|{sig}", "-"),
           head_digest.get(f"l|{cls}|{name}", "-")]
  return h("|".join(parts))


def load_base(path, fams):
  if os.path.isdir(path):
    t = load_families(path, fams)
    return {"digest": digest(t), "members": members(t)}
  with open(path, encoding="utf-8") as f:
    return json.load(f)


def main():
  cmd = sys.argv[1]
  fams = method_level()
  if cmd == "base":
    t = load_families(sys.argv[2], fams)
    json.dump({"digest": digest(t), "members": members(t)}, sys.stdout, indent=0, sort_keys=True)
    print()
  elif cmd == "diff":
    base = load_base(sys.argv[2], fams)
    head = digest(load_families(sys.argv[3], fams))
    ch = changed(base["digest"], head)
    for cls in sorted(ch):
      for sig in sorted(ch[cls]):
        print(cls[len(APP):], sig)
  elif cmd == "hash":
    head = digest(load_families(sys.argv[2], fams))
    for cls, sig in zip(sys.argv[3::2], sys.argv[4::2]):
      print(cls, sig, method_digest(head, APP + cls, sig))
  else:
    sys.exit(__doc__)


if __name__ == "__main__":
  main()
