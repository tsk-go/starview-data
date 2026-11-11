import re,sys,glob
exec(open(sys.argv[0].replace('dupinc.py','onceloop.py')).read().split('for f in sorted')[0])  # reuse strip()
INC = re.compile(r'^\s*(\w+)(\+\+|--|\s*[+-]=\s*[^;]+);\s*$')
for f in sorted(glob.glob(sys.argv[1]+'/*.java')):
    s = strip(open(f).read())
    for m in re.finditer(r'\bif\s*\(', s):
      try:
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
          k = j; d = 0
          while True:
              if s[k] == '{': d += 1
              elif s[k] == '}':
                  d -= 1
                  if d == 0: break
              k += 1
          after = s[k+1:k+40].lstrip()
          if after.startswith('else'): continue
          body = s[j+1:k]
          # top-level statements at end of body
          lines = [l for l in body.split('\n') if l.strip()]
          if not lines: continue
          last = lines[-1]
          mm = INC.match(last) or (len(lines) > 1 and INC.match(lines[-2]))
          if not mm: continue
          stmt = mm.group(0).strip()
          # the next lines after the if-block, leaving enclosing blocks allowed (not entering new ones)
          rel = 0; found = False; n = 0
          for ln in s[k+1:].split('\n')[1:14]:
              t = ln.strip()
              if rel <= 0 and t == stmt: found = True; break
              if re.match(r'(while|for|do)\b', t) or re.match(r'(return|continue|break|throw)\b', t) and rel <= 0: break
              rel += ln.count('{') - ln.count('}')
              if rel < -3: break
          if found:
              print(f'{f.split("/")[-1]}:{s.count(chr(10),0,m.start())+1}: {stmt}')

      except IndexError:
        pass