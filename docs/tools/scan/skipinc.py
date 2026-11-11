import re,sys,glob
exec(open(sys.argv[0].replace('skipinc.py','onceloop.py')).read().split('for f in sorted')[0])  # strip()
INC = re.compile(r'^\s*(\w+)(\+\+|\s*\+=\s*[^;]+|\s*=\s*\1\s*\+\s*1);\s*$')
def match_close(s, i):
    d = 0
    while i < len(s):
        if s[i] == '{': d += 1
        elif s[i] == '}':
            d -= 1
            if d == 0: return i
        i += 1
    return -1
for f in sorted(glob.glob(sys.argv[1]+'/*.java')):
    s = strip(open(f).read())
    for m in re.finditer(r'\bwhile\s*\(', s):
        i = m.end()-1; d = 0
        try:
            while True:
                if s[i] == '(': d += 1
                elif s[i] == ')':
                    d -= 1
                    if d == 0: break
                i += 1
            j = i+1
            while s[j].isspace(): j += 1
            if s[j] != '{': continue
            k = match_close(s, j)
        except IndexError:
            continue
        body = s[j+1:k]
        lines = [l for l in body.split('\n') if l.strip()]
        if not lines: continue
        cond = s[m.end():i]
        mm = None
        for ln in lines[-4:]:
            x = INC.match(ln)
            if x and re.search(r'\b' + re.escape(x.group(1)) + r'\b', cond): mm = x
        if not mm: continue
        var = mm.group(1)
        # continue statements belonging to this loop (not inside nested loops)
        # crude: remove nested loop bodies
        nb = body; out = ''; p = 0
        for nm in re.finditer(r'\b(while|for|do)\b', body):
            pass
        # find 'continue;' positions and check nested depth of loops
        for cm in re.finditer(r'\bcontinue\s*;', body):
            pre = body[:cm.start()]
            # is it inside a nested loop? count loop openings not closed
            nested = False
            for lm in re.finditer(r'\b(while|for)\s*\(', pre):
                # find its body start and end within body
                q = lm.end()-1; dd = 0
                while q < len(pre):
                    if pre[q] == '(': dd += 1
                    elif pre[q] == ')':
                        dd -= 1
                        if dd == 0: break
                    q += 1
                q2 = q+1
                while q2 < len(body) and body[q2].isspace(): q2 += 1
                if q2 < len(body) and body[q2] == '{':
                    e = match_close(body, q2)
                    if e == -1 or e > cm.start(): nested = True; break
            if nested: continue
            prev = [l for l in pre.split('\n') if l.strip()]
            if any(re.match(r'^\s*' + re.escape(var) + r'(\+\+|\s*\+=|\s*=)', l) for l in prev[-4:]): continue
            line = s.count('\n', 0, j) + body[:cm.start()].count('\n') + 1
            print(f'{f.split("/")[-1]}:{line}: while-loop step "{lines[-1].strip()}" skipped by continue')
