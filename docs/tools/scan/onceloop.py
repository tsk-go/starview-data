import re,sys,glob
def strip(s):
    # remove strings, chars and comments
    s = re.sub(r'//[^\n]*', lambda m: ' '*len(m.group()), s)
    s = re.sub(r'/\*.*?\*/', lambda m: re.sub(r'[^\n]', ' ', m.group()), s, flags=re.S)
    s = re.sub(r'"(?:\\.|[^"\\])*"', lambda m: '"'+' '*(len(m.group())-2)+'"', s)
    s = re.sub(r"'(?:\\.|[^'\\])'", "' '", s)
    return s
for f in sorted(glob.glob(sys.argv[1]+'/*.java')):
    raw = open(f).read(); s = strip(raw)
    for m in re.finditer(r'\b(while|for)\s*\(', s):
        # find the '(' ... ')' then '{'
        i = m.end()-1; d = 0
        while True:
            if s[i] == '(': d += 1
            elif s[i] == ')':
                d -= 1
                if d == 0: break
            i += 1
        j = i+1
        while s[j].isspace(): j += 1
        if s[j] != '{': continue
        # match body
        k = j; d = 0
        while True:
            if s[k] == '{': d += 1
            elif s[k] == '}':
                d -= 1
                if d == 0: break
            k += 1
        body = s[j+1:k]
        # last top-level statement
        t = body.rstrip()
        # walk back: if ends with '}', it's a block statement -> not unconditional
        if not t.endswith(';'): continue
        # find start of last statement at depth 0
        d = 0; p = len(t)-2
        while p >= 0:
            c = t[p]
            if c == '}': d += 1
            elif c == '{':
                if d == 0: break
                d -= 1
            elif c == ';' and d == 0: break
            p -= 1
        last = t[p+1:].strip()
        if re.match(r'(return\b|break\s*;|throw\b)', last):
            # ignore if preceded by label-less 'else' or 'if' on same statement (e.g. "if (x) return;")
            line = s.count('\n', 0, m.start())+1
            print(f'{f.split("/")[-1]}:{line}: {m.group(1)} ... last: {last[:60]}')
