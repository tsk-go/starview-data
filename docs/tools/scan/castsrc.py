import re,sys,glob,os
d=sys.argv[1]
pat=re.compile(r"\((Iterable|Collection|List|Map|Set|ArrayList|HashMap|LinkedHashMap)(<[^()]*?>)?\) *\(?(var[0-9]+[a-z]*)\b")
for f in sorted(glob.glob(d+'/*.java')):
    if 'ExternalSynthetic' in f: continue
    L=open(f).read().split('\n')
    for i,l in enumerate(L):
        for m in pat.finditer(l):
            v=m.group(3); T=m.group(1)
            asg=[]
            for j in range(i-1,max(0,i-400),-1):
                s=L[j]
                if re.search(r"(public|private|protected|static).*\(.*\)\s*\{\s*$",s) and not s.strip().startswith(('if','for','while','}')): break
                mm=re.search(r"(?:^|[\s(])"+re.escape(v)+r"\s*=\s*(.*?);?\s*$",s)
                if mm and '==' not in s[mm.start():mm.start()+len(v)+4]:
                    asg.append((j+1,mm.group(1)[:110]))
                    if len(asg)>=3: break
            print(f"{os.path.basename(f)}:{i+1} ({T}) {v}")
            for a in asg: print(f"    {a[0]}: {a[1]}")
