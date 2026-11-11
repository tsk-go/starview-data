import re,sys
# usage: divlines.py File.java Class$Inner method  -> prints lines with '/' division inside that method (first match per class)
path, cls, meth = sys.argv[1:4]
src = open(path).read().split('\n')
inner = cls.split('$')[1] if '$' in cls else None
start = 0
if inner:
    for i,l in enumerate(src):
        if re.search(r'class\s+' + re.escape(inner) + r'\b', l): start = i; break
pat = re.compile(r'^\s*(?:(?:public|private|protected|static|final|synchronized)\s+)*[\w<>\[\],.? ]+\s+' + re.escape(meth) + r'\s*\(')
for i in range(start, len(src)):
    if pat.search(src[i]) and not src[i].strip().startswith(('return','//')):
        depth = 0; began = False
        for j in range(i, len(src)):
            l = src[j]
            if re.search(r'[\w\)\]] / [\w\(]', l) or re.search(r'[\w\)\]]/[\w\(]', l.split('//')[0]):
                print(f'{j+1}: {l.strip()[:200]}')
            depth += l.count('{') - l.count('}')
            if '{' in l: began = True
            if began and depth <= 0: break
        break
