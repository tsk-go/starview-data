"""cmp2.py: match lambda/anonymous bodies (original methods with no same-name counterpart) to rebuilt anon-class methods."""
import re, pickle, collections
exec(open("cmp.py").read().split("report = []")[0])  # reuse feats/normalizers
def top(c): return c[1:-1].split("/")[-1].split("$")[0]
def units_orig():
    out=[]
    for c, ms in orig.items():
        if re.search(r"\$\$ExternalSyntheticLambda\d+", c): continue
        rc = rb.get(c)
        for k, m in ms.items():
            if k == "__super__" or k[0].startswith("$r8$lambda$") or k[0].startswith("access$"): continue
            if k[0] in ("<init>",) and rc is None: pass
            if rc is not None and k in rc: continue
            if re.match(r"component\d+$|copy(\$default)?$", k[0]): continue
            out.append((c, k, m))
    return out
def units_rb():
    out=[]
    for c, ms in rb.items():
        oc = orig.get(c)
        for k, m in ms.items():
            if k == "__super__": continue
            if oc is not None and k in oc: continue
            out.append((c, k, m))
    return out
uo = units_orig(); ur = units_rb()
byg = collections.defaultdict(list)
for u in ur: byg[top(u[0])].append(u)
def sig(f): return set(f[0]) | set(f[1]) | {x for x in f[2] if not x.startswith("<anon>.")}
rep=[]; unmatched=[]
used=set()
for c,k,m in sorted(uo, key=lambda x:(x[0],x[1])):
    fo = feats(m); so = sig(fo)
    if not so: continue
    best=None; bj=0
    for u in byg[top(c)]:
        fr = feats(u[2]); sr = sig(fr)
        if not sr: continue
        j = len(so & sr) / len(so | sr)
        if j > bj: bj=j; best=(u,fr)
    if best is None or bj < 0.3:
        unmatched.append(f"{c} {k[0]}{k[1]}  best={bj:.2f}"); continue
    u,fr = best
    diffs=[]
    for i, lab in enumerate(["str", "inv", "fld", "type", "num"]):
        miss = fo[i]-fr[i]; extra = fr[i]-fo[i]
        if lab=="str": extra -= {'"..."','""'}
        if lab=="fld":
            miss = {x for x in miss if not x.startswith("<anon>.")}; extra = {x for x in extra if not x.startswith("<anon>.")}
        if miss: diffs.append(f"  -{lab}: "+" | ".join(sorted(map(str,miss)))[:500])
        if extra: diffs.append(f"  +{lab}: "+" | ".join(sorted(map(str,extra)))[:500])
    if diffs and any(d.startswith("  -") for d in diffs):
        rep.append(f"{c.split('/')[-1]}.{k[0]}  ~ {u[0].split('/')[-1]}.{u[1][0]} (j={bj:.2f})\n"+"\n".join(diffs))
open("report2.txt","w").write("\n".join(rep)); open("unmatched2.txt","w").write("\n".join(unmatched))
print("orig units", len(uo), "rb units", len(ur), "diff", len(rep), "unmatched", len(unmatched))
