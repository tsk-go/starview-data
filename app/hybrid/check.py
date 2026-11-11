#!/usr/bin/env python3
"""Link check of the merged smali: every class, method and field the app's code uses exists and may be used.

  check.py MERGED_DIR

Mixing 2.84.1's compiled classes with our build only works if each side finds what it calls on the other with the
same name, parameters and access. Android would only find a mistake when that code runs (NoSuchMethodError,
IllegalAccessError, VerifyError), so this checks all of it up front: references from app classes
(com/starpilot/starview, org/capnproto, com/k2fsa) to any class in the APK are resolved like ART does (the class,
then its superclasses, then its interfaces). Framework classes (android.*, java.*...) aren't in the APK and are
skipped. Exit 1 with a list when anything doesn't resolve.
"""
import os
import re
import sys

APP_PREFIXES = ("com/starpilot/", "org/capnproto/", "com/k2fsa/")
CLASS_RE = re.compile(r"^\.class (.*?)L([^;]+);")
SUPER_RE = re.compile(r"^\.super L([^;]+);")
IMPL_RE = re.compile(r"^\.implements L([^;]+);")
FIELD_RE = re.compile(r"^\.field (.*?)(\S+?):(\S+?)(?: = .*)?$")
METHOD_RE = re.compile(r"^\.method (.*?)(\S+?\(.*?\)\S+)$")
INVOKE_RE = re.compile(r"^\s*invoke-(virtual|super|direct|static|interface|polymorphic)(?:/range)? \{[^}]*\}, (\[*L[^;]+;|\[[^-]+)->(\S+?\(.*?\)\S+)")
FIELDOP_RE = re.compile(r"^\s*[is](?:get|put)(?:-\w+)? [^,]+(?:, [^,]+)?, L([^;]+);->(\S+?):(\S+)")
TYPEOP_RE = re.compile(r"^\s*(?:new-instance|check-cast|instance-of|const-class) [^,]+(?:, [^,]+)?, (\[*)L([^;]+);")


class Cls:
  __slots__ = ("name", "flags", "sup", "ifaces", "fields", "methods", "path")

  def __init__(self, name, flags, path):
    self.name, self.flags, self.path = name, flags, path
    self.sup, self.ifaces, self.fields, self.methods = None, [], {}, {}


def parse(path):
  c, refs, cur = None, [], None
  with open(path, encoding="utf-8") as f:
    for line in f:
      if c is None:
        m = CLASS_RE.match(line)
        if m:
          c = Cls(m.group(2), m.group(1).split(), path)
        continue
      if line.startswith(".super"):
        m = SUPER_RE.match(line); c.sup = m.group(1) if m else None
      elif line.startswith(".implements"):
        c.ifaces.append(IMPL_RE.match(line).group(1))
      elif line.startswith(".field"):
        m = FIELD_RE.match(line.rstrip("\n"))
        if m:
          c.fields[(m.group(2), m.group(3))] = m.group(1).split()
      elif line.startswith(".method"):
        m = METHOD_RE.match(line.rstrip("\n"))
        c.methods[m.group(2)] = m.group(1).split()
        cur = m.group(2)
      elif cur:
        m = INVOKE_RE.match(line)
        if m:
          owner = m.group(2)
          if owner.startswith("L"):
            refs.append(("m", m.group(1), owner[1:-1], m.group(3), cur))
          continue
        m = FIELDOP_RE.match(line)
        if m:
          refs.append(("f", line.strip().split()[0], m.group(1), (m.group(2), m.group(3)), cur))
          continue
        m = TYPEOP_RE.match(line)
        if m:
          refs.append(("c", None, m.group(2), None, cur))
  return c, refs


def load(root):
  all_cls, app_refs = {}, []
  for dirpath, _, files in os.walk(root):
    for f in files:
      if not f.endswith(".smali"):
        continue
      c, refs = parse(os.path.join(dirpath, f))
      if c is None:
        continue
      all_cls[c.name] = c
      if c.name.startswith(APP_PREFIXES):
        app_refs.extend((c.name,) + r for r in refs)
  return all_cls, app_refs


def find_method(cls, sig):
  """ART-style lookup: the class, its superclasses, then every interface (default / abstract methods)."""
  k = cls
  while k is not None:
    c = CLS.get(k)
    if c is None:
      return "external"
    if sig in c.methods:
      return c, c.methods[sig]
    k = c.sup
  stack = [cls]
  seen = set()
  while stack:
    k = stack.pop()
    c = CLS.get(k)
    if c is None or k in seen:
      continue
    seen.add(k)
    for i in c.ifaces:
      ic = CLS.get(i)
      if ic is None:
        return "external"
      if sig in ic.methods:
        return ic, ic.methods[sig]
      stack.append(i)
    if c.sup:
      stack.append(c.sup)
  return None


def find_field(cls, key):
  stack = [cls]
  seen = set()
  while stack:
    k = stack.pop()
    if k in seen:
      continue
    seen.add(k)
    c = CLS.get(k)
    if c is None:
      return "external"
    if key in c.fields:
      return c, c.fields[key]
    stack.extend(c.ifaces)
    if c.sup:
      stack.append(c.sup)
  return None


def pkg(name):
  return name.rsplit("/", 1)[0]


def accessible(ref_cls, owner, flags):
  if "public" in flags:
    return True
  if "private" in flags:
    return ref_cls == owner.name
  if pkg(ref_cls) == pkg(owner.name):
    return True                      # package-private and protected within the package
  if "protected" in flags:
    k = CLS.get(ref_cls)
    while k is not None:
      if k.sup == owner.name:
        return True
      k = CLS.get(k.sup) if k.sup else None
  return False


def main(root):
  global CLS
  CLS, refs = load(root)
  errors = []
  for ref_cls, kind, op, owner, what, in_method in refs:
    where = f"{ref_cls}.{in_method}"
    if owner not in CLS:
      if owner.startswith(APP_PREFIXES):
        errors.append(f"missing class {owner} (used in {where})")
      continue
    if kind == "c":
      continue
    if kind == "m":
      if what.startswith("<init>") and op != "direct":
        continue
      r = find_method(owner, what)
      if r is None:
        errors.append(f"missing method {owner}->{what} (used in {where})")
      elif r != "external":
        c, flags = r
        if not accessible(ref_cls, c, flags):
          errors.append(f"not accessible: {' '.join(flags)} {c.name}->{what} (used in {where})")
        if op == "static" and "static" not in flags:
          errors.append(f"static call to instance method {c.name}->{what} (used in {where})")
        if op in ("virtual", "interface", "direct") and "static" in flags:
          errors.append(f"instance call to static method {c.name}->{what} (used in {where})")
        if op == "direct" and not what.startswith("<init>") and "private" not in flags:
          errors.append(f"invoke-direct on non-private method {c.name}->{what} (used in {where})")
        if op in ("virtual", "interface") and "private" in flags:
          errors.append(f"invoke-{op} on private method {c.name}->{what} (used in {where})")
        oc = CLS[owner]
        if op == "interface" and "interface" not in oc.flags:
          errors.append(f"invoke-interface on class {owner}->{what} (used in {where})")
        if op == "virtual" and "interface" in oc.flags:
          errors.append(f"invoke-virtual on interface {owner}->{what} (used in {where})")
    else:
      r = find_field(owner, what)
      if r is None:
        errors.append(f"missing field {owner}->{what[0]}:{what[1]} (used in {where})")
      elif r != "external":
        c, flags = r
        if not accessible(ref_cls, c, flags):
          errors.append(f"not accessible: {' '.join(flags)} {c.name}->{what[0]} (used in {where})")
        static_op = op.startswith("s")
        if "put" in op and "final" in flags and ref_cls != c.name:
          errors.append(f"{op} to final field {c.name}->{what[0]} from another class (used in {where})")
        if static_op != ("static" in flags):
          errors.append(f"{op} on {'static' if 'static' in flags else 'instance'} field {c.name}->{what[0]} (used in {where})")
  # every concrete app class implements every abstract method of its supertypes
  for c in CLS.values():
    if not c.name.startswith(APP_PREFIXES) or "abstract" in c.flags or "interface" in c.flags:
      continue
    needed, stack, seen = {}, [c.name], set()
    while stack:
      k = stack.pop()
      kc = CLS.get(k)
      if kc is None or k in seen:
        continue
      seen.add(k)
      for sig, flags in kc.methods.items():
        if "abstract" in flags and sig not in needed:
          needed[sig] = k
      stack.extend(kc.ifaces)
      if kc.sup:
        stack.append(kc.sup)
    for sig, owner in needed.items():
      r = find_method(c.name, sig)
      impl = r not in (None, "external") and "abstract" not in r[1]
      if not impl:
        # an interface default method counts too
        if not any(sig in CLS[i].methods and "abstract" not in CLS[i].methods[sig] for i in seen if i in CLS):
          errors.append(f"{c.name} doesn't implement {owner}->{sig}")
  errors = sorted(set(errors))
  for e in errors:
    print(e)
  print(f"checked {len(refs)} references from app classes: {len(errors)} problems")
  return 1 if errors else 0


if __name__ == "__main__":
  sys.exit(main(sys.argv[1]))
