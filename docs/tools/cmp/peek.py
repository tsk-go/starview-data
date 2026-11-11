import sys
exec(open("cmp2.py").read().split("uo = units_orig()")[0])
def sig(f): return set(f[0]) | set(f[1]) | {x for x in f[2] if not x.startswith("<anon>.")}
uo = units_orig(); ur = units_rb()
import collections
byg = collections.defaultdict(list)
for u in ur: byg[top(u[0])].append(u)
targets = sys.argv[1:]
for c,k,m in uo:
    name = c.split("/")[-1][:-1]+"."+k[0]
    if not any(t in name for t in targets): continue
    fo = feats(m); so = sig(fo)
    print("==", name, k[1]); print("   O:", sorted(so))
    cands = []
    for u in byg[top(c)]:
        fr = feats(u[2]); sr = sig(fr)
        if not sr: continue
        cands.append((len(so & sr)/len(so|sr), u, sr))
    for j,u,sr in sorted(cands, key=lambda x:-x[0])[:2]:
        print(f"   R {j:.2f} {u[0].split('/')[-1]}.{u[1][0]}:", sorted(sr)[:25])
