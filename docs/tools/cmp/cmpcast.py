"""cmpcast.py: check-cast types the rebuilt method has that the original method never casts to (a wrong cast = crash)."""
import re, pickle, sys, collections
orig = pickle.load(open("orig.pkl", "rb")); rb = pickle.load(open("rb.pkl", "rb"))
def casts(m, skipnull=False):
    c = set(); nulls = set()
    for ins in m["insns"]:
        mm = re.match(r"const(?:/4|/16)? (v\d+), #int 0\b", ins)
        mv = re.match(r"move-object(?:/from16|/16)? (v\d+), (v\d+)", ins)
        if ins.startswith("check-cast"):
            reg = ins.split()[1].rstrip(",")
            # a cast of a null constant (javac's (T) null) can't fail
            if not (skipnull and reg in nulls):
                c.add(ins.split(", ")[-1].split(" //")[0].strip())
            continue
        if mm: nulls.add(mm.group(1)); continue
        if mv:
            if mv.group(2) in nulls: nulls.add(mv.group(1))
            else: nulls.discard(mv.group(1))
            continue
        w = re.match(r"[a-z\-/0-9]+ (v\d+)", ins)
        if w and not ins.startswith(("invoke", "if-", "iput", "sput", "aput", "return", "throw", "monitor", "fill", "check-cast", "packed", "sparse", "goto")):
            nulls.discard(w.group(1))
    return c
def allcasts(cls):
    s = set()
    for k, m in cls.items():
        if k != "__super__": s |= casts(m)
    return s
out = []; cnt = collections.Counter()
for c, ms in sorted(rb.items()):
    oc = orig.get(c)
    if oc is None: continue
    ocall = allcasts(oc)
    for k, m in ms.items():
        if k == "__super__": continue
        o = oc.get(k)
        oset = casts(o) if o is not None else ocall
        extra = casts(m, True) - oset
        if extra:
            out.append(f"{c[1:-1].split('/')[-1]}.{k[0]}{'' if o is not None else ' (new)'}: " + " ".join(sorted(extra)))
            for t in extra: cnt[t] += 1
open("reportcast.txt", "w").write("\n".join(out))
print(len(out), "methods")
for t, n in cnt.most_common(60): print(n, t)
