"""cmp.py: per-method feature comparison of the original dex and the rebuilt dex."""
import re, pickle, sys, collections
orig = pickle.load(open("orig.pkl", "rb")); rb = pickle.load(open("rb.pkl", "rb"))
ANON = re.compile(r"\$\$ExternalSyntheticLambda\d+|\$\d+(?=;|\$)|\$\$inlined\$")
COLL = {"Ljava/lang/Iterable;","Ljava/util/Collection;","Ljava/util/List;","Ljava/util/ArrayList;","Ljava/util/Set;","Ljava/util/HashSet;","Ljava/util/LinkedHashSet;","Ljava/util/Map;","Ljava/util/HashMap;","Ljava/util/LinkedHashMap;","Ljava/util/AbstractMap;","Ljava/util/AbstractCollection;","Lkotlin/collections/ArrayDeque;","Ljava/util/concurrent/ConcurrentHashMap;","Ljava/util/Iterator;","Ljava/util/ListIterator;","Ljava/util/SortedMap;","Ljava/util/TreeMap;"}
STR = {"Ljava/lang/String;","Ljava/lang/CharSequence;","Ljava/lang/Object;"}
SKIP_OWNER = ("Lkotlin/jvm/internal/Intrinsics;","Lkotlin/Result;","Lcom/starpilot/starview/KtCompat;","Ljava/lang/StringBuilder;","Lkotlin/ResultKt;","Lkotlin/jvm/internal/Ref")
BOX = re.compile(r"^Ljava/lang/(Integer|Long|Double|Float|Boolean|Short|Byte|Character|Number);$")
def norm_owner(o):
    if o in COLL: return "coll"
    if o in STR: return "obj"
    return o
def feats(m):
    strs=set(); inv=set(); flds=set(); types=set(); nums=set()
    for ins in m["insns"]:
        if ins.startswith("const-string"):
            s = ins.split(", ",1)[1]
            s = re.sub(r" // string@[0-9a-f]+$","",s)
            strs.add(s)
        elif ins.startswith("invoke-"):
            mm = re.search(r"\}, (L[^;]+;)\.([^:]+):", ins)
            if not mm: continue
            owner, name = mm.group(1), mm.group(2)
            if owner.startswith(SKIP_OWNER) or BOX.match(owner): continue
            if name.startswith("$r8$lambda$") or name in ("<init>",) and ANON.search(owner): continue
            name = re.sub(r"\$default$","",name)
            if ANON.search(owner): owner = "<anon>"
            owner = owner.replace("$Companion_;", "$Companion;")
            ma = re.match(r"access\$(get|set)(\w+?)\$c?p$", name)
            if ma:
                fn = ma.group(2); fn = fn if fn.isupper() else fn[0].lower()+fn[1:]
                flds.add(owner+"."+fn+(".r" if ma.group(1)=="get" else ".w")); continue
            mn = re.match(r"-\$\$Nest\$s?f(get|put)(\w+)$", name)
            if mn:
                flds.add(owner+"."+mn.group(2)+(".r" if mn.group(1)=="get" else ".w")); continue
            mn = re.match(r"-\$\$Nest\$s?m(\w+)$", name)
            if mn: name = mn.group(1)
            if name.startswith("access$"): name = name[7:]
            mk = re.match(r"m\d+(double|switch|short|import|int|long|float|char|byte|boolean|new|default|class|if|for|do|try|case|this|super|package)$", name)
            if mk: name = mk.group(1)
            if name == "getIsPreserved": name = "isPreserved"
            inv.add(norm_owner(owner)+"."+name)
        elif re.match(r"[is](get|put)", ins):
            mm = re.search(r"(L[^;]+;)\.([^:]+):", ins)
            if mm:
                o = mm.group(1)
                if ANON.search(o): o="<anon>"
                o = o.replace("$Companion_;", "$Companion;")
                if o in ("Lkotlin/text/Charsets;","Lkotlin/Unit;","Lkotlin/Result;"): continue
                flds.add(o+"."+mm.group(2)+(".w" if "put" in ins.split()[0] else ".r"))
        elif re.match(r"(new-instance|check-cast|instance-of|new-array|filled-new-array)", ins):
            mm = re.search(r"(\[*L[^;]+;|\[+[A-Z])\s*(//.*)?$", ins)
            if mm:
                t = mm.group(1)
                if ANON.search(t) or t in ("Ljava/lang/StringBuilder;",) or "Ref$" in t: continue
                if ins.startswith("check-cast"):
                    continue  # casts are noisy (jadx/javac insert their own); compared separately if needed
                types.add(ins.split()[0].split("/")[0]+":"+t)
        elif re.match(r"const(-wide)?(/high16|/16|/4|/32)? ", ins) and ("#float" in ins or "#double" in ins):
            mm = re.search(r"#(float|double) ([-0-9.eE+]+)", ins)
            if mm:
                try:
                    v = float(mm.group(2))
                except ValueError:
                    continue
                if v not in (0.0, 1.0): nums.add(round(v, 6))
    return strs, inv, flds, types, nums
def is_anon(c): return bool(ANON.search(c))
report = []
stats = collections.Counter()
missing_methods = []
for c, ms in sorted(orig.items()):
    if is_anon(c): continue
    rc = rb.get(c)
    if rc is None:
        missing_methods.append((c, "<whole class>")); continue
    for k, m in ms.items():
        if k == "__super__": continue
        if k[0].startswith("$r8$lambda$"): continue
        r = rc.get(k)
        if r is None:
            # same name, other signature?
            alt = [kk for kk in rc if kk != "__super__" and kk[0] == k[0]]
            missing_methods.append((c, k[0]+k[1]+("  (rebuilt has "+", ".join(a[1] for a in alt)+")" if alt else "")))
            continue
        fo = feats(m); fr = feats(r)
        diffs = []
        for i, lab in enumerate(["str", "inv", "fld", "type", "num"]):
            miss = fo[i] - fr[i]; extra = fr[i] - fo[i]
            if lab == "str": extra -= {'"..."', '""'}
            if miss: diffs.append(f"  -{lab}: " + " | ".join(sorted(map(str, miss)))[:600])
            if extra: diffs.append(f"  +{lab}: " + " | ".join(sorted(map(str, extra)))[:600])
        stats["methods"] += 1
        if diffs:
            stats["diff"] += 1
            report.append(f"{c[1:-1].split('/')[-1]}.{k[0]}{k[1]}\n" + "\n".join(diffs))
extra_methods = []
for c, ms in sorted(rb.items()):
    if is_anon(c): continue
    oc = orig.get(c)
    if oc is None: extra_methods.append((c, "<whole class>")); continue
    for k in ms:
        if k == "__super__": continue
        if k not in oc: extra_methods.append((c, k[0]+k[1]))
open("report.txt","w").write("\n".join(report))
open("missing.txt","w").write("\n".join(f"{a} {b}" for a,b in missing_methods))
open("extra.txt","w").write("\n".join(f"{a} {b}" for a,b in extra_methods))
print(dict(stats), "missing", len(missing_methods), "extra", len(extra_methods))
