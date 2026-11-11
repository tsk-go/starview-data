"""Method-level hybrid for the classes we changed on purpose (decisions.py lists them).

merge.py takes 2.84.1's own compiled code for every class we never touched. In the few classes we did change (the
driving screen, settings, the navigation pane, the screen editor, the steering wheel), most methods are still
untouched, and our build of those is the decompiler's copy. So these classes are merged method by method too:

  - every method starts as 2.84.1's own code;
  - decisions.PATCH edits 2.84.1's code in place (a few smali lines: a call into a new method of ours, a changed
    string, a skipped block) - for big methods where we changed a few lines;
  - decisions.BUILD methods are replaced by our build's (small methods we rewrote);
  - methods that don't exist in 2.84.1 or in the recovered source as first finished (base.json) are new: always ours;
  - anything our methods use that 2.84.1 doesn't have (javac's anonymous classes and lambdas, d8's accessors, the
    recovered source's own helpers) comes along from the build, renamed where the name is taken ("$hyb").

The result must still pass check.py (every call between the two halves resolves, with the right access and kind).
"""
import collections
import re

from smali import SmaliClass, method_flags

APP = "com/starpilot/starview/"
CLASS_REF = re.compile(r"L(com/starpilot/starview/[^;\s<>]+);")
MREF = re.compile(r"L(com/starpilot/starview/[^;\s<>]+);->([^\s(:]+\([^)\s]*\)[^\s,}]+)")
FREF = re.compile(r"L(com/starpilot/starview/[^;\s<>]+);->([^\s(:]+):([^\s,}]+)")
INVOKE = re.compile(r"^(\s*)invoke-(virtual|direct|static)(/range)? (\{[^}]*\}), L([^;]+);->(\S+?\(.*?\)\S+)(\s*)$")
FIELD_WRITE = re.compile(r"^\s*[is]put\S* [^,]+(?:, [^,]+)?, L([^;]+);->([^\s:]+):(\S+)")
ENCLOSING_METHOD = "Ldalvik/annotation/EnclosingMethod;"
ANON_RX = re.compile(r"Ldalvik/annotation/InnerClass;\s*\n\s*accessFlags = \S+\s*\n\s*name = null")


class MergeError(Exception):
  pass


def fam_of(name):
  return name.split("$", 1)[0]


def field_key(lines):
  m = re.match(r"^\.field (.*?)(\S+?):(\S+?)(?: = .*)?\s*$", lines[0])
  return m.group(2), m.group(3)


def set_flags(line, drop):
  """Remove access flags (e.g. "private", "final") from a .field / .method line."""
  head, rest = line.split(" ", 1)
  words = rest.split(" ")
  i = 0
  while i < len(words) - 1 and words[i] in ("public", "private", "protected", "static", "final", "volatile",
                                             "transient", "synthetic", "enum", "abstract", "synchronized",
                                             "bridge", "varargs", "native", "strict", "constructor",
                                             "declared-synchronized"):
    i += 1
  flags = [w for w in words[:i] if w not in drop]
  return " ".join([head] + flags + words[i:])


class FamilyMerge:
  def __init__(self, fam, o_texts, r_texts, base, build, patches, is_orig_class):
    """fam: e.g. "com/starpilot/starview/MainActivity"; o_texts / r_texts: class name -> smali text of that family;
    base: {class: {"m": [sigs], "f": ["name:type"]}} of the recovered source as first finished; build: {class: [sig
    or "name(" for every overload]}; patches: {(class, sig): [(old, new), ...]}; is_orig_class(name): True when that
    class (outside this family) ships as 2.84.1's code (for the Companion_ names of the recovered source)."""
    self.fam = fam
    self.O = {n: SmaliClass(t) for n, t in o_texts.items()}
    self.R = {n: SmaliClass(t) for n, t in r_texts.items()}
    self.out = {n: SmaliClass(t) for n, t in o_texts.items()}
    self.base = {n: {"m": set(v["m"]), "f": set(v["f"])} for n, v in base.items()}
    self.build = build
    self.patches = patches
    self.is_orig_class = is_orig_class
    self.rename = {}                       # build's own class -> its name in the merged app
    for n, c in self.R.items():
      if self.own(n, c):
        self.rename[n] = fam + "$hyb" + n[len(fam):]
    self.r_methods = collections.defaultdict(set)   # class -> sigs whose code is the build's (for the fix-ups)
    self.patched = set()
    self.placed_classes = set()
    self.fmap = {}
    self.unprivate = set()
    self.queue = collections.deque()
    self.report = []

  # -- which build classes are the same class as 2.84.1's --------------------------------------------------------
  def own(self, n, c):
    """True for classes only the build has as such: javac's anonymous / local classes and d8's synthetic classes.
    Named classes (inner classes, the recovered source's own "Outer$name$1" files) are the same class as 2.84.1's."""
    if "$$" in n:
      return True
    head = "".join(c.header)
    return ENCLOSING_METHOD in head or bool(ANON_RX.search(head))

  def shared(self, n):
    return n in self.out and n not in self.rename.values() and n in self.O

  # -- rewriting the build's code for the merged class -------------------------------------------------------------
  def field_map(self, cls, name, typ):
    """A field the build's code uses on a 2.84.1 class: the same field in 2.84.1, maybe under another name / type."""
    key = (cls, name, typ)
    if key in self.fmap:
      return self.fmap[key]
    o = self.O[cls].field_map()
    out_fields = self.out[cls].field_map()
    if (name, typ) in o or (name, typ) in out_fields:
      res = (name, typ)
    elif f"{name}:{typ}" not in self.base.get(cls, {"f": set()})["f"]:
      res = (name, typ)                     # new field: added below when used
    elif (name + "$delegate", typ) in o:
      res = (name + "$delegate", typ)       # Kotlin's "by lazy" field, renamed by the decompiler
    else:
      same = [k for k in o if k[0] == name]
      if len(same) != 1:
        raise MergeError(f"{cls}: the build uses field {name}:{typ}, which 2.84.1 doesn't have and isn't new")
      res = same[0]                         # same field, the recovered source declared a supertype
    self.fmap[key] = res
    return res

  def rewrite(self, text):
    def cls_sub(m):
      n = m.group(1)
      if n in self.rename:
        return f"L{self.rename[n]};"
      if n.endswith("$Companion_") and self.is_orig_class(n[:-1]):
        return f"L{n[:-1]};"
      return m.group(0)

    def field_sub(m):
      cls, name, typ = m.group(1), m.group(2), m.group(3)
      if cls in self.rename or not self.shared(cls):
        return m.group(0)
      name2, typ2 = self.field_map(cls, name, typ)
      return f"L{cls};->{name2}:{typ2}"

    text = FREF.sub(field_sub, text)
    return CLASS_REF.sub(cls_sub, text)

  # -- what the build's code needs ---------------------------------------------------------------------------------
  def scan(self, text, where):
    for m in MREF.finditer(text):
      cls, sig = m.group(1), m.group(2)
      if fam_of(cls) != self.fam:
        continue
      if cls in self.out:
        # a method the class itself doesn't declare in the build is inherited (View, Activity...): nothing to bring
        if sig not in self.out[cls].method_map() and cls in self.R and sig in self.R[cls].method_map():
          self.queue.append(("m", cls, sig, where))
      else:
        self.want_class(cls, where)
    for m in FREF.finditer(text):
      cls, name, typ = m.group(1), m.group(2), m.group(3)
      if fam_of(cls) != self.fam:
        continue
      if cls in self.out:
        if (name, typ) not in self.out[cls].field_map() and cls in self.R and (name, typ) in self.R[cls].field_map():
          self.queue.append(("f", cls, (name, typ), where))
      else:
        self.want_class(cls, where)
    for m in CLASS_REF.finditer(text):
      cls = m.group(1)
      if fam_of(cls) == self.fam and cls not in self.out:
        self.want_class(cls, where)

  def want_class(self, merged_name, where):
    src = {v: k for k, v in self.rename.items()}.get(merged_name, merged_name)
    if src not in self.R:
      raise MergeError(f"{where} uses class {merged_name}, which neither 2.84.1 nor the build has")
    self.queue.append(("c", src, None, where))

  # -- placing code --------------------------------------------------------------------------------------------------
  def place_method(self, cls, sig, where):
    if sig in self.r_methods[cls]:
      return
    if cls not in self.R or sig not in self.R[cls].method_map():
      raise MergeError(f"{where} uses {cls}->{sig}, which neither 2.84.1 nor the build has")
    if (cls, sig) in self.patched:
      raise MergeError(f"{cls}->{sig} is both patched and taken from the build")
    lines = self.R[cls].method_map()[sig]
    text = self.rewrite("".join(lines))
    new = text.splitlines(keepends=True)
    out = self.out[cls]
    for i, (s, old) in enumerate(out.methods):
      if s == sig:
        # 2.84.1's callers stay as they are: the method keeps 2.84.1's access flags, only the code is ours
        if ("static" in method_flags(old)) != ("static" in method_flags(new)):
          raise MergeError(f"{cls}->{sig}: static in one, not in the other")
        new = [old[0]] + new[1:]
        out.methods[i] = (sig, new)
        break
    else:
      out.methods.append((sig, new))
    self.r_methods[cls].add(sig)
    self.scan(text, f"{cls}->{sig}")

  def place_field(self, cls, key, where):
    out = self.out[cls]
    if key in out.field_map():
      return
    r = self.R[cls].field_map() if cls in self.R else {}
    if key not in r:
      raise MergeError(f"{where} uses field {cls}->{key[0]}:{key[1]}, which neither 2.84.1 nor the build has")
    out.fields.append((key, list(r[key])))
    self.report.append(f"new field {cls[len(APP):]}.{key[0]}")

  def place_class(self, src, where):
    if src in self.placed_classes:
      return
    self.placed_classes.add(src)
    name = self.rename.get(src, src)
    if name in self.O:
      raise MergeError(f"{where}: the build's class {src} would replace 2.84.1's")
    text = self.rewrite(self.R[src].text())
    c = SmaliClass(text)
    self.out[name] = c
    for sig, _ in c.methods:
      self.r_methods[name].add(sig)
    self.scan(text, name)

  # -- the whole family ----------------------------------------------------------------------------------------------
  def run(self):
    # 1. patches to 2.84.1's own methods
    for (cls, sig), edits in self.patches.items():
      if cls not in self.out or sig not in self.out[cls].method_map():
        raise MergeError(f"patch for {cls}->{sig}: 2.84.1 has no such method")
      out = self.out[cls]
      for i, (s, lines) in enumerate(out.methods):
        if s != sig:
          continue
        text = "".join(lines)
        for n, (old, new) in enumerate(edits):
          k = text.count(old)
          if k != 1:
            raise MergeError(f"patch {n + 1} for {cls}->{sig}: its old text is there {k} times, not once")
          text = text.replace(old, new)
        out.methods[i] = (sig, text.splitlines(keepends=True))
        self.patched.add((cls, sig))
        self.scan(text, f"{cls}->{sig} (patched)")
        self.report.append(f"patched {cls[len(APP):]}->{sig.split('(')[0]}")
    # 2. methods of ours that replace 2.84.1's
    for cls, sigs in self.build.items():
      for want in sigs:
        hits = [s for s in self.R[cls].method_map() if s == want or (want.endswith("(") and s.startswith(want))]
        if not hits:
          raise MergeError(f"build method {cls}->{want}: the build has no such method")
        for s in hits:
          self.place_method(cls, s, "decisions.BUILD")
          self.report.append(f"build {cls[len(APP):]}->{s.split('(')[0]}")
    # 3. new methods / fields / classes (not in 2.84.1, not in the recovered source as first finished)
    for n, c in self.R.items():
      if n in self.rename:
        continue
      base = self.base.get(n)
      if n not in self.O:
        if base is None:
          self.queue.append(("c", n, None, "new class"))
        continue
      for key, _ in c.fields:
        if key not in self.O[n].field_map() and f"{key[0]}:{key[1]}" not in base["f"]:
          self.place_field(n, key, "new field")
      for sig, _ in c.methods:
        if sig not in self.O[n].method_map() and sig not in base["m"]:
          self.place_method(n, sig, "new method")
          self.report.append(f"new {n[len(APP):]}->{sig.split('(')[0]}")
    # 4. everything those need
    while self.queue:
      kind, cls, what, where = self.queue.popleft()
      if kind == "c":
        self.place_class(cls, where)
      elif kind == "m":
        if what not in self.out[cls].method_map():
          self.place_method(cls, what, where)
      else:
        self.place_field(cls, what, where)
    self.fix_access()
    return {n: c.text() for n, c in self.out.items()}

  def fix_access(self):
    """2.84.1's Kotlin keeps most members private; the build's code may use them from its own inner classes or call
    them with the other invoke kind. Fields simply lose "private" / "final" where needed; method calls get the
    invoke kind 2.84.1's method needs (a private method can only be called from its own class)."""
    flags = {n: {s: method_flags(ls) for s, ls in c.methods} for n, c in self.out.items()}
    field_flags_drop = collections.defaultdict(set)
    for cls, sigs in list(self.r_methods.items()):
      c = self.out[cls]
      for i, (sig, lines) in enumerate(c.methods):
        if sig not in sigs and (cls, sig) not in self.patched:
          continue
        new = []
        for ln in lines:
          m = INVOKE.match(ln)
          if m and m.group(5) in flags and fam_of(m.group(5)) == self.fam and not m.group(6).startswith("<init>"):
            tgt = flags[m.group(5)].get(m.group(6))
            if tgt is not None:
              kind = m.group(2)
              tcls, tsig = m.group(5), m.group(6)
              if "private" in tgt and tcls != cls:
                # Kotlin's own accessor for it (access$name, static, the object first), else it stops being private
                name, rest = tsig.split("(", 1)
                acc = f"access${name}(L{tcls};{rest}"
                if "static" not in tgt and acc in flags[tcls] and "static" in flags[tcls][acc]:
                  ln = f"{m.group(1)}invoke-static{m.group(3) or ''} {m.group(4)}, L{tcls};->{acc}{m.group(7)}"
                  new.append(ln)
                  continue
                self.unprivate.add((tcls, tsig))
                tgt = [f for f in tgt if f != "private"]
              if "static" in tgt:
                kind = "static"
              elif "private" in tgt:
                kind = "direct"
              else:
                kind = "virtual"
              if kind != m.group(2):
                ln = f"{m.group(1)}invoke-{kind}{m.group(3) or ''} {m.group(4)}, L{tcls};->{tsig}{m.group(7)}"
          new.append(ln)
          for fm in FREF.finditer(ln):
            fcls = fm.group(1)
            if fcls in self.out and fam_of(fcls) == self.fam:
              if fcls != cls:
                field_flags_drop[(fcls, fm.group(2), fm.group(3))].add("private")
              if FIELD_WRITE.match(ln) and (fcls != cls or not sig.startswith(("<init>", "<clinit>"))):
                field_flags_drop[(fcls, fm.group(2), fm.group(3))].add("final")
        c.methods[i] = (sig, new)
    # a private method of 2.84.1 the build's code calls from another class: package-private, and 2.84.1's own calls
    # to it become invoke-virtual (a non-private method may not be called with invoke-direct)
    for tcls, tsig in sorted(self.unprivate):
      c = self.out[tcls]
      for i, (sig, lines) in enumerate(c.methods):
        if sig == tsig:
          lines = [set_flags(lines[0], {"private"})] + list(lines[1:])
        new = []
        for ln in lines:
          m = INVOKE.match(ln)
          if m and m.group(2) == "direct" and m.group(5) == tcls and m.group(6) == tsig:
            ln = f"{m.group(1)}invoke-virtual{m.group(3) or ''} {m.group(4)}, L{tcls};->{tsig}{m.group(7)}"
          new.append(ln)
        c.methods[i] = (sig, new)
      self.report.append(f"method {tcls[len(APP):]}->{tsig.split('(')[0]}: no longer private")
    for (fcls, name, typ), drop in field_flags_drop.items():
      c = self.out[fcls]
      for i, (key, lines) in enumerate(c.fields):
        if key == (name, typ):
          old = lines[0]
          lines = [set_flags(old, drop)] + list(lines[1:])
          if lines[0] != old:
            c.fields[i] = (key, lines)
            self.report.append(f"field {fcls[len(APP):]}.{name}: no longer {'/'.join(sorted(drop))}")
