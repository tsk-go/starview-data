"""cmparith.py: per-method counts of division / conversion opcodes, original vs rebuilt (finds int division that should be double, dropped casts)."""
import re, pickle, collections, sys
src = open("cmp.py").read().split("report = []")[0]
exec(src)
FAM = re.compile(r"^(div|rem|mul|add|sub)-(int|long|float|double)|^(int|long|float|double)-to-(int|long|float|double)$")
def counts(m):
    c = collections.Counter()
    for ins in m["insns"]:
        op = ins.split()[0] if ins else ""
        op = re.sub(r"/(2addr|lit8|lit16)$", "", op)
        if op.startswith(("div-","rem-")) or "-to-" in op and op.split("-to-")[0] in ("int","long","float","double"):
            c[op] += 1
    return c
out = []
for c, ms in sorted(orig.items()):
    if is_anon(c): continue
    rc = rb.get(c)
    if rc is None: continue
    for k, m in ms.items():
        if k == "__super__" or k[0].startswith("$r8$lambda$"): continue
        r = rc.get(k)
        if r is None: continue
        a, b = counts(m), counts(r)
        if a != b:
            diff = {op: (a[op], b[op]) for op in set(a) | set(b) if a[op] != b[op]}
            # interesting: original has double/float division the rebuilt lacks, or rebuilt has int division the original lacks
            flag = any(op.startswith(("div-double","div-float","int-to-double","int-to-float","long-to-double","long-to-float")) and o > n for op, (o, n) in diff.items()) \
                or any(op.startswith(("div-int","div-long","rem-int")) and n > o for op, (o, n) in diff.items())
            out.append((flag, c, k[0], diff))
out.sort(key=lambda t: (not t[0], t[1]))
for f, c, name, diff in out:
    print(("!! " if f else "   ") + c.split("/")[-1] + " " + name + " " + " ".join(f"{op}:{o}->{n}" for op, (o, n) in sorted(diff.items())))
