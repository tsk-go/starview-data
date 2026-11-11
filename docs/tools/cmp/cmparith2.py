import re, pickle, collections
src = open("cmp.py").read().split("report = []")[0]
exec(src)
def counts(m):
    c = collections.Counter()
    for ins in m["insns"]:
        op = re.sub(r"/(2addr|lit8|lit16)$", "", ins.split()[0]) if ins else ""
        mm = re.match(r"(mul|add|sub|div|rem|shl|shr|ushr)-(int|long|float|double)$", op)
        if mm: c[mm.group(1)+"-"+mm.group(2)] += 1
        if op in ("int-to-long","long-to-int"): c[op]+=1
    return c
for cl, ms in sorted(orig.items()):
    if is_anon(cl): continue
    rc = rb.get(cl)
    if rc is None: continue
    for k, m in ms.items():
        if k == "__super__" or k[0].startswith("$r8$lambda$"): continue
        r = rc.get(k)
        if r is None: continue
        a, b = counts(m), counts(r)
        flags = []
        for opn in ("mul","add","sub","shl"):
            if b[opn+"-int"] > a[opn+"-int"] and a[opn+"-long"] > b[opn+"-long"]:
                flags.append(f"{opn}: int {a[opn+'-int']}->{b[opn+'-int']} long {a[opn+'-long']}->{b[opn+'-long']}")
        if flags: print(cl.split('/')[-1], k[0], "; ".join(flags), f"i2l {a['int-to-long']}->{b['int-to-long']}")
