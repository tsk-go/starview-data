"""cmpcast2.py: (what produced the value, cast type) pairs in the rebuilt method that the original method never has."""
import re, pickle, collections, sys
orig = pickle.load(open("orig.pkl", "rb")); rb = pickle.load(open("rb.pkl", "rb"))
SRC_OK = ("next", "get", "getValue", "getKey", "getFirst", "getSecond", "component1", "component2", "component3",
          "invoke", "opt", "remove", "poll", "peek", "first", "last", "firstOrNull", "lastOrNull", "getOrNull",
          "previous", "removeFirst", "removeLast", "pollFirst", "elementAt", "single", "maxByOrNull", "minByOrNull", "getElement")
def pairs(m):
    out = set(); lastinv = None; defs = {}
    for ins in m["insns"]:
        if ins.startswith("invoke"):
            mm = re.search(r"\}, (L[^;]+;)\.([^:]+):", ins)
            lastinv = mm.group(2) if mm else None
            continue
        mr = re.match(r"move-result-object (v\d+)", ins)
        if mr:
            defs[mr.group(1)] = lastinv; continue
        mv = re.match(r"move-object(?:/from16|/16)? (v\d+), (v\d+)", ins)
        if mv:
            defs[mv.group(1)] = defs.get(mv.group(2)); continue
        if ins.startswith("check-cast"):
            reg = ins.split()[1].rstrip(","); t = ins.split(", ")[-1].split(" //")[0].strip()
            src = defs.get(reg)
            if src in SRC_OK: out.add((src, t))
            continue
        w = re.match(r"[a-z\-/0-9]+ (v\d+)", ins)
        if w and not ins.startswith(("if-", "iput", "sput", "aput", "return", "throw", "monitor", "fill", "packed", "sparse", "goto")):
            defs[w.group(1)] = None
    return out
rows = []
for c, ms in sorted(rb.items()):
    oc = orig.get(c)
    if oc is None: continue
    allo = set()
    for k, m in oc.items():
        if k != "__super__": allo |= pairs(m)
    for k, m in ms.items():
        if k == "__super__": continue
        o = oc.get(k)
        base = pairs(o) if o is not None else allo
        extra = pairs(m) - base
        # a cast to the same type from another source in the original method is fine
        otypes = {t for _, t in base}
        if "--strict" not in sys.argv: extra = {(s, t) for s, t in extra if t not in otypes}
        if extra:
            rows.append(f"{c[1:-1].split('/')[-1]}.{k[0]}: " + " ".join(f"{s}->{t.split('/')[-1]}" for s, t in sorted(extra)))
open("reportcast2.txt", "w").write("\n".join(rows))
print(len(rows))
