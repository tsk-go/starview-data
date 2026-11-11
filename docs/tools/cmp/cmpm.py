"""cmpm.py: per-method multiset comparison of invoke counts (detects duplicated / dropped calls)."""
import re, pickle, collections, sys
src = open("cmp.py").read().split("report = []")[0]
exec(src)
NOISE = re.compile(r"closeFinally|Intrinsics|\.(iterator|hasNext|next|valueOf|intValue|longValue|doubleValue|floatValue|booleanValue|getFirst|getSecond|component\d|toString|append|length|size|get|equals|hashCode|isNaN|isInfinite|isEmpty)$|<init>$|^<anon>")
def counts(m):
    c = collections.Counter()
    for ins in m["insns"]:
        if not ins.startswith("invoke-"): continue
        mm = re.search(r"\}, (L[^;]+;)\.([^:]+):", ins)
        if not mm: continue
        owner, name = mm.group(1), mm.group(2)
        if owner.startswith(SKIP_OWNER) or BOX.match(owner): continue
        name = re.sub(r"\$default$","",name)
        if ANON.search(owner): owner = "<anon>"
        owner = owner.replace("$Companion_;", "$Companion;")
        if re.match(r"access\$(get|set)", name): continue
        name = re.sub(r"^access\$", "", name)
        mn = re.match(r"-\$\$Nest\$(m?)(.*)", name)
        if mn:
            if not mn.group(1) and re.match(r"f(get|put)", mn.group(2)): continue
            name = mn.group(2)
        key = norm_owner(owner)+"."+name
        if NOISE.search(key): continue
        c[key] += 1
    return c
rows = []
for c, ms in sorted(orig.items()):
    if is_anon(c): continue
    rc = rb.get(c)
    if rc is None: continue
    for k, m in ms.items():
        if k == "__super__" or k not in rc: continue
        co = counts(m); cr = counts(rc[k])
        more = {x: cr[x]-co[x] for x in cr if cr[x] > co[x] and x in co}
        less = {x: co[x]-cr[x] for x in co if co[x] > cr[x] and x in cr}
        if more or less:
            rows.append((sum(more.values())+sum(less.values()), f"{c.split('/')[-1][:-1]}.{k[0]}{k[1][:60]}  more={dict(more)} less={dict(less)}"))
rows.sort(key=lambda r: -r[0])
open("reportm.txt","w").write("\n".join(r[1] for r in rows))
print(len(rows))
