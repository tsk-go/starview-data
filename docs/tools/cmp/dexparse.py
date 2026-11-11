"""dexparse.py: parse `dexdump -d` text into {class: {(name, type): method}} for com/starpilot classes."""
import re, pickle, sys
PKG = "Lcom/starpilot/"
def parse(paths):
    classes = {}
    for p in paths:
        cur = None; meth = None; in_code = False
        with open(p, errors="ignore") as fh:
            for line in fh:
                if line.startswith("  Class descriptor  : '"):
                    d = line.split("'")[1]
                    cur = classes.setdefault(d, {}) if d.startswith(PKG) else None
                    meth = None; continue
                if cur is None: continue
                s = line.strip()
                if s.startswith("Superclass        :") :
                    cur.setdefault("__super__", line.split("'")[1] if "'" in line else ""); continue
                m = re.match(r"\s+name\s+: '(.*)'$", line)
                if m:
                    meth = {"name": m.group(1), "type": None, "insns": [], "catches": []}; continue
                m = re.match(r"\s+type\s+: '(.*)'$", line)
                if m and meth is not None and meth["type"] is None:
                    meth["type"] = m.group(1); cur[(meth["name"], meth["type"])] = meth; continue
                if meth is None: continue
                m = re.search(r"\|[0-9a-f]{4}: (.*)$", line)
                if m:
                    meth["insns"].append(m.group(1)); continue
    return classes
if __name__ == "__main__":
    out = sys.argv[1]
    c = parse(sys.argv[2:])
    pickle.dump(c, open(out, "wb"))
    print(len(c), "classes")
