"""Small smali reader / writer for the hybrid build: a class split into its header, fields and methods."""
import re

METHOD_HEAD = re.compile(r"^\.method (.*?)(\S+?\(.*?\)\S+)\s*$")
FIELD_HEAD = re.compile(r"^\.field (.*?)(\S+?):(\S+?)(?: = .*)?\s*$")


class SmaliClass:
  def __init__(self, text):
    self.header = []          # .class / .super / .source / .implements / class annotations, in order
    self.fields = []          # (key=(name, type), lines)
    self.methods = []         # (sig, lines)
    lines = text.splitlines(keepends=True)
    i = 0
    while i < len(lines):
      ln = lines[i]
      if ln.startswith(".method"):
        j = i
        while not lines[j].startswith(".end method"):
          j += 1
        sig = METHOD_HEAD.match(ln).group(2)
        self.methods.append((sig, lines[i:j + 1]))
        i = j + 1
      elif ln.startswith(".field"):
        m = FIELD_HEAD.match(ln)
        j = i
        # a field with annotations ends with ".end field"; a plain one is one line
        if i + 1 < len(lines) and lines[i + 1].startswith("    .annotation"):
          while not lines[j].startswith(".end field"):
            j += 1
        self.fields.append(((m.group(2), m.group(3)), lines[i:j + 1]))
        i = j + 1
      elif ln.startswith("# ") or ln.strip() == "":
        i += 1                # section comments and blank lines are rebuilt on write
      else:
        if ln.startswith(".annotation"):
          j = i
          while not lines[j].startswith(".end annotation"):
            j += 1
          self.header.extend(lines[i:j + 1])
          i = j + 1
        else:
          self.header.append(ln)
          i += 1

  def name(self):
    return re.match(r"^\.class .*?L([^;]+);", self.header[0]).group(1)

  def method_map(self):
    return dict(self.methods)

  def field_map(self):
    return dict(self.fields)

  def text(self):
    out = list(self.header) + ["\n"]
    for _, ls in self.fields:
      out.extend(ls)
    out.append("\n")
    for _, ls in self.methods:
      out.extend(ls)
      out.append("\n")
    return "".join(out)


def method_flags(lines):
  return METHOD_HEAD.match(lines[0]).group(1).split()


_NORM = [
  (re.compile(r"^\s*\.(line|local|end local|restart local|prologue|source)\b.*\n", re.M), ""),
  (re.compile(r"\$\$ExternalSynthetic(Lambda|Outline|Backport|ApiModelOutline)\d+"), r"$$ExternalSynthetic\1N"),
  (re.compile(r"\$(\d+);"), "$N;"),                       # javac anonymous classes Outer$12
  (re.compile(r"lambda\$(\w+?)\$\d+"), r"lambda$\1$N"),    # javac lambda bodies lambda$onCreate$3
  (re.compile(r"-\$\$Nest\$\w+"), "-$$Nest$X"),
  (re.compile(r"\s+#.*$", re.M), ""),
]


def normalize(lines):
  """A method's code without line numbers, local names and javac / d8 numbering, to tell real changes apart."""
  t = "".join(lines)
  for rx, rep in _NORM:
    t = rx.sub(rep, t)
  return t
