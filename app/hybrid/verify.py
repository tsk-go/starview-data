#!/usr/bin/env python3
"""A small register verifier for the methods the hybrid build changed (patched or taken from our build).

Android checks every method's registers when it loads a class (ART's verifier): a value used as the wrong kind (an
object where a number is expected, a register never set on some path, half of a long) makes the whole class fail
with a VerifyError - for MainActivity that means StarView can't start. Nothing on this computer runs ART, so this
follows the same rules in a simpler way: per register only the kind (number, long/double pair, object, zero, unset,
conflicting) along every path, including jumps into catch blocks. It doesn't check class types against each other.

It is run on 2.84.1's own classes too: a complaint that 2.84.1's method has as well is this checker's own limit, not
a problem; only new complaints fail.

  verify.py ORIG_SMALI_DIR MERGED_SMALI_DIR [Class...]   (default: decisions.METHOD_LEVEL; inner classes included)
"""
import glob
import os
import re
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from smali import SmaliClass  # noqa: E402

APP = "com/starpilot/starview/"
U, Z, I, J, H, R, X = "unset", "zero", "num", "wide", "wide-hi", "obj", "conflict"
LABEL = re.compile(r"^:(\S+)")
REG = re.compile(r"\b([vp])(\d+)\b")


def merge(a, b):
  if a == b:
    return a
  if {a, b} == {Z, I}:
    return I
  if {a, b} == {Z, R}:
    return R
  return X


def kind_of(t):
  """category of a type descriptor"""
  if t in ("J", "D"):
    return J
  if t.startswith(("L", "[")):
    return R
  return I


def param_types(sig):
  inner = sig[sig.index("(") + 1:sig.index(")")]
  out, i = [], 0
  while i < len(inner):
    j = i
    while inner[j] == "[":
      j += 1
    if inner[j] == "L":
      j = inner.index(";", j)
    out.append(inner[i:j + 1])
    i = j + 1
  return out


def regs_in(braces, nloc):
  """registers of an invoke / filled-new-array list: {v1, v2} or {v1 .. v5} (p registers included)"""
  rs = [int(n) + (nloc if k == "p" else 0) for k, n in REG.findall(braces)]
  if ".." in braces and len(rs) == 2:
    return list(range(rs[0], rs[1] + 1))
  return rs


class Method:
  def __init__(self, cls, sig, lines):
    self.cls, self.sig = cls, sig
    head = lines[0]
    self.static = " static " in head
    self.nloc = 0
    self.ins, self.labels, self.catches, self.data = [], {}, [], {}
    cur_data = None
    self.pending_label = None
    in_annotation = False
    for ln in lines[1:]:
      s = ln.strip()
      if not s or s.startswith("#"):
        continue
      if s.startswith(".annotation"):
        in_annotation = True
        continue
      if in_annotation:
        in_annotation = not s.startswith(".end annotation")
        continue
      if s.startswith(".locals"):
        self.nloc = int(s.split()[1])
        continue
      if s.startswith((".packed-switch", ".sparse-switch", ".array-data")):
        cur_data = []
        self.data[self.pending_label] = cur_data
        continue
      if s.startswith((".end packed-switch", ".end sparse-switch", ".end array-data")):
        cur_data = None
        continue
      if cur_data is not None:
        m = re.search(r":(\S+)", s)
        if m:
          cur_data.append(m.group(1))
        continue
      if s.startswith(".catch"):
        m = re.match(r"\.catch\S* .*\{:(\S+) \.\. :(\S+)\} :(\S+)", s)
        self.catches.append(m.groups())
        continue
      if s.startswith("."):
        continue
      m = LABEL.match(s)
      if m:
        self.labels[m.group(1)] = len(self.ins)
        self.pending_label = m.group(1)
        continue
      self.ins.append(s)
    self.nregs = self.nloc + (0 if self.static else 1) + sum(2 if t in ("J", "D") else 1 for t in param_types(sig))

  def reg(self, tok):
    k, n = tok[0], int(tok[1:])
    return n + (self.nloc if k == "p" else 0)

  def entry(self):
    st = [U] * self.nregs
    r = self.nloc
    if not self.static:
      st[r] = R
      r += 1
    for t in param_types(self.sig):
      k = kind_of(t)
      if k == J:
        st[r], st[r + 1] = J, H
        r += 2
      else:
        st[r] = k
        r += 1
    return st


WIDE_OPS = ("-long", "-double")


def op_kind(op):
  """category an arithmetic / conversion op works on, from its name: (result, operands)"""
  if "-to-" in op:
    a, b = op.split("-to-")
    src = J if a.endswith(("long", "double")) else I
    dst = J if b.split("/")[0] in ("long", "double") else I
    return dst, src
  k = J if any(op.split("/")[0].endswith(w) for w in WIDE_OPS) else I
  return k, k


def verify(m):
  errs = []
  n = len(m.ins)
  states = [None] * n
  try_ranges = [(m.labels[a], m.labels[b], m.labels[h]) for a, b, h in m.catches]
  work = [0]
  states[0] = m.entry()

  def need(st, r, k, i, what):
    have = st[r] if r < len(st) else U
    ok = (have == k) or (have == Z and k in (I, R))
    if k == J:
      ok = have == J and r + 1 < len(st) and st[r + 1] == H
    if not ok:
      errs.append((i, f"{what}: v{r} is {have}, needs {k}"))

  def put(st, r, k):
    if k == J:
      st[r], st[r + 1] = J, H
    else:
      st[r] = k
    # writing over half of a pair breaks the pair
    if r > 0 and st[r - 1] == J and k != J:
      st[r - 1] = X
    if k != J and r + 1 < len(st) and st[r + 1] == H:
      st[r + 1] = X

  def flow(i, st):
    if i >= n:
      return
    if states[i] is None:
      states[i] = list(st)
      work.append(i)
      return
    new = [merge(a, b) for a, b in zip(states[i], st)]
    if new != states[i]:
      states[i] = new
      work.append(i)

  while work:
    i = work.pop()
    st = list(states[i])
    s = m.ins[i]
    op, _, rest = s.partition(" ")
    args = [a.strip() for a in rest.split(",")] if rest else []
    succ, fall = [], True
    try:
      if op.startswith("invoke-"):
        br = rest[rest.index("{"):rest.index("}") + 1]
        rs = regs_in(br, m.nloc)
        sig = rest.split("->", 1)[1].strip()
        kinds = ([] if op.startswith("invoke-static") else [R])
        for t in param_types(sig):
          kinds.append(kind_of(t))
        j = 0
        for k in kinds:
          if j >= len(rs):
            errs.append((i, f"too few registers for {sig}"))
            break
          need(st, rs[j], k, i, op)
          j += 2 if k == J else 1
      elif op.startswith("filled-new-array"):
        pass
      elif op.startswith("move-result"):
        k = J if op == "move-result-wide" else R if op == "move-result-object" else I
        put(st, m.reg(args[0]), k)
      elif op == "move-exception":
        put(st, m.reg(args[0]), R)
      elif op.startswith("move"):
        k = J if "wide" in op else R if "object" in op else I
        need(st, m.reg(args[1]), k, i, op)
        put(st, m.reg(args[0]), k)
      elif op.startswith("return"):
        fall = False
        if op != "return-void":
          k = J if op == "return-wide" else R if op == "return-object" else I
          need(st, m.reg(args[0]), k, i, op)
      elif op.startswith("const-wide"):
        put(st, m.reg(args[0]), J)
      elif op.startswith(("const-string", "const-class", "const-method")):
        put(st, m.reg(args[0]), R)
      elif op.startswith("const"):
        v = args[1].split()[0]
        put(st, m.reg(args[0]), Z if re.fullmatch(r"-?0x0|0", v) else I)
      elif op in ("monitor-enter", "monitor-exit", "fill-array-data"):
        need(st, m.reg(args[0]), R, i, op)
      elif op == "throw":
        need(st, m.reg(args[0]), R, i, op)
        fall = False
      elif op == "check-cast":
        need(st, m.reg(args[0]), R, i, op)
        put(st, m.reg(args[0]), R)
      elif op == "instance-of":
        need(st, m.reg(args[1]), R, i, op)
        put(st, m.reg(args[0]), I)
      elif op == "array-length":
        need(st, m.reg(args[1]), R, i, op)
        put(st, m.reg(args[0]), I)
      elif op == "new-instance":
        put(st, m.reg(args[0]), R)
      elif op == "new-array":
        need(st, m.reg(args[1]), I, i, op)
        put(st, m.reg(args[0]), R)
      elif op.startswith("goto"):
        succ.append(m.labels[args[0][1:]])
        fall = False
      elif op in ("packed-switch", "sparse-switch"):
        need(st, m.reg(args[0]), I, i, op)
        succ += [m.labels[x] for x in m.data[args[1][1:]]]
      elif op.startswith("cmp"):
        k = J if op.endswith(("long", "double")) else I
        need(st, m.reg(args[1]), k, i, op)
        need(st, m.reg(args[2]), k, i, op)
        put(st, m.reg(args[0]), I)
      elif op.startswith("if-"):
        if op.endswith("z"):
          r = m.reg(args[0])
          if st[r] in (U, X, H, J):
            errs.append((i, f"{op}: v{r} is {st[r]}"))
        else:
          a, b = m.reg(args[0]), m.reg(args[1])
          for r in (a, b):
            if st[r] in (U, X, H, J):
              errs.append((i, f"{op}: v{r} is {st[r]}"))
          if op in ("if-eq", "if-ne") and {st[a], st[b]} == {I, R}:
            errs.append((i, f"{op}: compares a number with an object"))
        succ.append(m.labels[args[-1][1:]])
      elif op.startswith(("aget", "aput")):
        k = J if op.endswith("wide") else R if op.endswith("object") else I
        need(st, m.reg(args[1]), R, i, op)
        need(st, m.reg(args[2]), I, i, op)
        if op.startswith("aget"):
          put(st, m.reg(args[0]), k)
        else:
          need(st, m.reg(args[0]), k, i, op)
      elif op.startswith(("iget", "iput", "sget", "sput")):
        t = rest.split(":")[-1].strip()
        k = kind_of(t)
        if op.startswith("i"):
          need(st, m.reg(args[1]), R, i, op)
        if "get" in op:
          put(st, m.reg(args[0]), k)
        else:
          need(st, m.reg(args[0]), k, i, op)
      elif op == "nop":
        pass
      elif re.match(r"^(neg|not|int-to|long-to|float-to|double-to)", op):
        dst, src = op_kind(op)
        need(st, m.reg(args[1]), src, i, op)
        put(st, m.reg(args[0]), dst)
      elif re.match(r"^(add|sub|mul|div|rem|and|or|xor|shl|shr|ushr|rsub)-", op):
        dst, src = op_kind(op)
        shift = op.split("-")[0] in ("shl", "shr", "ushr")
        if "/lit" in op:
          need(st, m.reg(args[1]), I, i, op)
          put(st, m.reg(args[0]), I)
        elif op.endswith("/2addr"):
          need(st, m.reg(args[0]), dst, i, op)
          need(st, m.reg(args[1]), I if shift else src, i, op)
          put(st, m.reg(args[0]), dst)
        else:
          need(st, m.reg(args[1]), src, i, op)
          need(st, m.reg(args[2]), I if shift else src, i, op)
          put(st, m.reg(args[0]), dst)
      else:
        errs.append((i, f"unknown instruction {op}"))
    except (KeyError, IndexError, ValueError) as e:
      errs.append((i, f"can't read {s!r}: {e}"))
    # a throwing instruction may jump into its catch block with the registers as they were before it
    for a, b, h in try_ranges:
      if a <= i < b:
        flow(h, states[i])
    for t in succ:
      flow(t, st)
    if fall:
      flow(i + 1, st)
  return sorted(set(errs))


def load(root, cls):
  for d in sorted(glob.glob(os.path.join(root, "smali*"))):
    p = os.path.join(d, APP + cls + ".smali")
    if os.path.exists(p):
      with open(p, encoding="utf-8") as f:
        return SmaliClass(f.read())
  return None


def family_classes(root, fam):
  out = set()
  for d in glob.glob(os.path.join(root, "smali*", APP)):
    for f in os.listdir(d):
      if f.endswith(".smali") and f[:-6].split("$", 1)[0] == fam:
        out.add(f[:-6])
  return out


def main():
  orig, merged, fams = sys.argv[1], sys.argv[2], sys.argv[3:]
  if not fams:
    from decisions import METHOD_LEVEL
    fams = sorted(METHOD_LEVEL)
  problems, checked = [], 0
  for fam in fams:
    for cls in sorted(family_classes(merged, fam)):
      mc, oc = load(merged, cls), load(orig, cls)
      om = oc.method_map() if oc else {}
      for sig, lines in mc.methods:
        if " abstract " in lines[0] or " native " in lines[0]:
          continue
        if oc and sig in om and om[sig] == lines:
          continue                       # 2.84.1's method, unchanged: ART took it as it is
        checked += 1
        mine = {e for _, e in verify(Method(cls, sig, lines))}
        theirs = {e for _, e in verify(Method(cls, sig, om[sig]))} if oc and sig in om else set()
        for e in sorted(mine - theirs):
          problems.append(f"{cls}->{sig}: {e}")
  for p in problems:
    print("verify: " + p)
  print(f"verify: {checked} changed or new methods checked, {len(problems)} problems")
  return 1 if problems else 0


if __name__ == "__main__":
  sys.exit(main())
