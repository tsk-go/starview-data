"""genpatch.py ROOT OUT: writes app/hybrid/patches.py from anchor-based edit specs against 2.84.1's smali (apktool d -r output in ROOT).
Checks: anchors unique, p0 never written in a method whose edit uses p0, labels in deleted ranges only used there,
4-bit register limits for non-range invokes."""
import glob
import os
import re
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from smali import SmaliClass  # noqa: E402

ROOT = sys.argv[1]
OUT = sys.argv[2]
APP = "com/starpilot/starview/"
DIRECTIVE = re.compile(r"^\s*(\.|:|#|$)")
WRITES = re.compile(r"^\s*(move|const|iget|sget|aget|new-instance|new-array|check-cast|instance-of|array-length|neg|not|"
                    r"int-to|long-to|float-to|double-to|add|sub|mul|div|rem|and|or|xor|shl|shr|ushr|cmp)\S* (\S+?),")


def method_lines(cls, sig):
  for d in sorted(glob.glob(ROOT + "/smali*")):
    p = f"{d}/{APP}{cls}.smali"
    if os.path.exists(p):
      for s, ls in SmaliClass(open(p).read()).methods:
        if s == sig:
          return ls
  raise SystemExit(f"no {cls}->{sig}")


def instr_idx(ls):
  return [i for i, l in enumerate(ls) if not DIRECTIVE.match(l)]


class Method:
  def __init__(self, cls, sig):
    self.cls, self.sig = cls, sig
    self.lines = method_lines(cls, sig)
    self.edits = []
    m = re.match(r"\s*\.locals (\d+)", self.lines[1])
    self.locals = int(m.group(1))
    self.text = "".join(self.lines)

  def p_written(self, reg="p0"):
    return [l.strip() for l in self.lines if (m := WRITES.match(l)) and m.group(2) == reg]

  def find(self, anchor, nth=None):
    hits = [i for i, l in enumerate(self.lines) if l.strip() == anchor]
    if nth is None and len(hits) != 1:
      raise SystemExit(f"{self.cls}->{self.sig}: anchor {anchor!r} found {len(hits)} times")
    return hits[nth or 0]

  def context(self, i, j, before=True):
    """raw text lines[i:j], widened until it is unique in the method"""
    while self.text.count("".join(self.lines[i:j])) != 1:
      if before and i > 0:
        i -= 1
      elif j < len(self.lines):
        j += 1
      else:
        i -= 1
    return i, j

  def add(self, i, j, new_mid):
    """replace lines[i:j] by new_mid (list of raw lines), with unique context around"""
    a, b = self.context(i, j) if j > i else self.context(max(i - 1, 0), i)
    old = "".join(self.lines[a:b])
    new = "".join(self.lines[a:i]) + "".join(new_mid) + "".join(self.lines[j:b])
    self.edits.append((old, new))

  def insert_after(self, anchor, ins, nth=None):
    i = self.find(anchor, nth)
    a, b = self.context(i, i + 1)
    old = "".join(self.lines[a:b])
    self.edits.append((old, old + "".join("    " + x + "\n" for x in ins)))

  def insert_before(self, anchor, ins, nth=None):
    i = self.find(anchor, nth)
    a, b = self.context(i, i + 1, before=False)
    old = "".join(self.lines[a:b])
    pre = "".join(self.lines[a:i])
    self.edits.append((old, pre + "".join("    " + x + "\n" for x in ins) + "".join(self.lines[i:b])))

  def insert_at_start(self, ins):
    i = instr_idx(self.lines)[0]
    self.insert_before(self.lines[i].strip(), ins, nth=0 if self.text.count(self.lines[i]) > 1 else None)

  def replace_line(self, anchor, new, nth=None):
    i = self.find(anchor, nth)
    a, b = self.context(i, i + 1)
    old = "".join(self.lines[a:b])
    new_t = "".join(self.lines[a:i]) + "".join("    " + x + "\n" for x in new) + "".join(self.lines[i + 1:b])
    self.edits.append((old, new_t))

  def delete_range(self, start_anchor, end_anchor, ins=(), keep_end=True):
    """delete from the line start_anchor (inclusive) to end_anchor (exclusive if keep_end, else inclusive)"""
    i = self.find(start_anchor)
    j = self.find(end_anchor)
    j = j if keep_end else j + 1
    block = self.lines[i:j]
    defined = [l.strip()[1:] for l in block if l.strip().startswith(":")]
    rest = self.lines[:i] + self.lines[j:]
    for lab in defined:
      if any(re.search(r":" + re.escape(lab) + r"\b", l) for l in rest):
        raise SystemExit(f"{self.cls}->{self.sig}: label {lab} in the deleted block is used outside it")
    old = "".join(block)
    if self.text.count(old) != 1:
      raise SystemExit("deleted block not unique")
    self.edits.append((old, "".join("    " + x + "\n" for x in ins)))


def check_regs(m, ins):
  for x in ins:
    if x.startswith("invoke-") and "/range" not in x:
      for r in re.findall(r"\b([vp])(\d+)\b", x.split("}")[0]):
        n = int(r[1]) + (m.locals if r[0] == "p" else 0)
        if n > 15:
          raise SystemExit(f"{m.cls}->{m.sig}: {x}: register {r[0]}{r[1]} = v{n} needs /range")


METHODS = []


def M(cls, sig):
  m = Method(cls, sig)
  METHODS.append(m)
  return m


MA = "Lcom/starpilot/starview/MainActivity;"
# ---------------------------------------------------------------- MainActivity
m = M("MainActivity", "<init>()V")
m.insert_before("return-void", [f"invoke-virtual/range {{p0 .. p0}}, {MA}->hybInit()V"])
m = M("MainActivity", "onCreate(Landroid/os/Bundle;)V")
m.insert_after("invoke-super/range {p0 .. p1}, Landroidx/appcompat/app/AppCompatActivity;->onCreate(Landroid/os/Bundle;)V",
               [f"invoke-virtual/range {{p0 .. p0}}, {MA}->hybCreateEarly()V"])
m.insert_before("return-void", [f"invoke-virtual/range {{p0 .. p0}}, {MA}->hybCreateLate()V"])
m = M("MainActivity", "onNewIntent(Landroid/content/Intent;)V")
m.insert_after(f"invoke-virtual {{p0, p1}}, {MA}->setIntent(Landroid/content/Intent;)V",
               [f"invoke-virtual {{p0, p1}}, {MA}->hybNewIntent(Landroid/content/Intent;)Z", "move-result v0",
                "if-eqz v0, :hyb_go_on", "return-void", ":hyb_go_on"])
m = M("MainActivity", "navTick(J)V")
m.insert_after("move-object v0, p0", [f"invoke-virtual {{p0}}, {MA}->hybNavAlert()V"], nth=0)
m.insert_after("invoke-static/range {v5 .. v12}, Lcom/starpilot/starview/Nav;->poll$default(Lcom/starpilot/starview/Nav;"
               "Lcom/starpilot/starview/DataClient;ZJZILjava/lang/Object;)V", [f"invoke-virtual {{p0}}, {MA}->hybNavTrack()V"])
m.replace_line(f"invoke-virtual {{p0}}, {MA}->openMap()V", [])
m = M("MainActivity", "setupMapFrame()V")
m.insert_at_start([f"invoke-virtual {{p0}}, {MA}->hybMapFrame()V"])
m = M("MainActivity", "startHudEdit()V")
m.insert_at_start([f"invoke-virtual {{p0}}, {MA}->hybEditStart()V"])
m = M("MainActivity", "stopHudEdit()V")
m.insert_at_start([f"invoke-virtual {{p0}}, {MA}->hybEditStop()V"])
for sig, cond in [("navigateTo$lambda$177$lambda$176(Lcom/starpilot/starview/MainActivity;Ljava/lang/String;Lorg/json/JSONObject;)Lkotlin/Unit;", None),
                  ("setDestinationNow$lambda$181(Lcom/starpilot/starview/MainActivity;Lorg/json/JSONObject;)Lkotlin/Unit;", None)]:
  m = M("MainActivity", sig)
  m.delete_range(f"invoke-virtual {{p0}}, {MA}->openMap()V",
                 "invoke-virtual {v0}, Lcom/starpilot/starview/MapPane;->expectRoutes()V", keep_end=False,
                 ins=[f"invoke-virtual {{p0}}, {MA}->closeMap()V"])
# ---------------------------------------------------------------- the screen tick
UT = "Lcom/starpilot/starview/MainActivity$uiTick$1;"
m = M("MainActivity$uiTick$1", "run()V")
# the turn card also shows when hybCardForced says so (the screen editor; 2.86.1: a held instruction without GPS)
m.insert_after(":cond_9c", [f"iget-object v3, v1, {UT}->this$0:{MA}", f"invoke-virtual {{v3}}, {MA}->hybCardForced()Z",
                            "move-result v3", "if-nez v3, :hyb_navcard_on"])
i = m.find("goto :goto_3f")
k = max(x for x in instr_idx(m.lines) if x < i)
assert m.lines[k].strip() == "const/4 v3, 0x0", m.lines[k]
m.add(k, k, [":hyb_navcard_on\n"])
m.insert_after("invoke-virtual {v0, v3}, Lcom/starpilot/starview/NavCardView;->setVisibility(I)V",
               [f"iget-object v0, v1, {UT}->this$0:{MA}", f"invoke-virtual {{v0}}, {MA}->hybNavMapTick()V"])
i = m.find(":cond_118")
first = min(x for x in instr_idx(m.lines) if x > i)
j = m.find(":cond_11d")
block = m.lines[first:j]
assert any("access$getMapBtn$p" in l for l in block) and any("setMarginStart" in l for l in block)
defined = [l.strip()[1:] for l in block if l.strip().startswith(":")]
rest = m.lines[:first] + m.lines[j:]
for lab in defined:
  assert not any(re.search(r":" + re.escape(lab) + r"\b", l) for l in rest), lab
a, b = m.context(i, j + 1)
old = "".join(m.lines[a:b])
new = "".join(m.lines[a:first]) + "    # 2.85.3: the map button is a screen-editor widget now (HudLayout places it)\n" + "".join(m.lines[j:b])
m.edits.append((old, new))
# 2.86.5: the big speed number from SpeedShown (true speed learned from GPS); v2 = Telemetry, v0 = the speed (m/s)
i = m.find(":goto_14")
k = min(x for x in instr_idx(m.lines) if x > i)
assert m.lines[k].strip() == "move/from16 v31, v0", m.lines[k]
assert "getVEgo()F" in "".join(m.lines[i - 6:i]) and "use_wheel_speed" in "".join(m.lines[i - 60:i])
a, b = m.context(k, k + 1, before=False)
old = "".join(m.lines[a:b])
m.edits.append((old, "".join(m.lines[a:k]) + "    invoke-static {v2, v0}, Lcom/starpilot/starview/SpeedShown;->mps(Lcom/starpilot/starview/Telemetry;F)F\n"
                "    move-result v0\n" + "".join(m.lines[k:b])))
# ---------------------------------------------------------------- MapPane
MP = "Lcom/starpilot/starview/MapPane;"
m = M("MapPane", "<init>(Landroid/content/Context;Lcom/starpilot/starview/MapPane$Host;)V")
m.insert_before("return-void", [f"invoke-virtual/range {{p0 .. p0}}, {MP}->hybInit()V"])
m = M("MapPane", "goWithLooked$lambda$33(Lcom/starpilot/starview/MapPane;Ljava/lang/String;)Lkotlin/Unit;")
m.insert_after(f"invoke-direct {{p0}}, {MP}->refreshBars()V",
               [f"iget-object v0, p0, {MP}->host:Lcom/starpilot/starview/MapPane$Host;",
                "invoke-interface {v0}, Lcom/starpilot/starview/MapPane$Host;->closeMap()V"])
m = M("MapPane$Banner", "onDraw(Landroid/graphics/Canvas;)V")
m.replace_line("const v2, -0xfe891ba", ["sget-object v2, Lcom/starpilot/starview/NavOverlay;->INSTANCE:Lcom/starpilot/starview/NavOverlay;",
                                        "invoke-virtual {v2}, Lcom/starpilot/starview/NavOverlay;->getBG()I", "move-result v2"])
m.insert_after("invoke-virtual {v7, v1, v2, v3, v4}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V",
               ["invoke-virtual {v0, v7, v8, v9}, Lcom/starpilot/starview/MapPane$Banner;->hybEdge(Landroid/graphics/Canvas;FF)V"], nth=0)
# ---------------------------------------------------------------- SettingsActivity
SA = "Lcom/starpilot/starview/SettingsActivity;"
m = M("SettingsActivity", "tabletAboutPage()Landroid/view/View;")
m.insert_after("invoke-virtual {v3, v8, v9}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V",
               [f"invoke-virtual {{v1, v3}}, {SA}->hybAboutRows(Landroid/widget/LinearLayout;)V"], nth=0)
m = M("SettingsActivity", "tabletNavPage()Landroid/view/View;")
m.replace_line('const-string v2, "Open the map when a route starts"', ['const-string v2, "See-through map under the turn card"'])
m.replace_line('const-string v3, "Also a destination set in Galaxy or on the comma."',
               ['const-string v3, "Street lines over the road view while the car is on, and your route while one is set. Move or resize it, the turn card and every button in the screen editor (hold the road view). Tap the map or the card for the full navigation screen."'])
for sig in ["tabletNavPage$lambda$254(Lcom/starpilot/starview/UiSettings;)Z",
            "tabletNavPage$lambda$255(Lcom/starpilot/starview/UiSettings;Z)Lkotlin/Unit;"]:
  m = M("SettingsActivity", sig)
  m.replace_line('const-string v0, "map_auto_open"', ['const-string v0, "nav_map"'])
# ---------------------------------------------------------------- WheelButton
WB = "Lcom/starpilot/starview/WheelButton;"
m = M("WheelButton", "onDraw(Landroid/graphics/Canvas;)V")
m.replace_line(f"iget-object v6, p0, {WB}->exp:Landroid/graphics/Bitmap;", [f"iget-object v6, p0, {WB}->wheel:Landroid/graphics/Bitmap;"])
i = m.find("if-nez v9, :cond_d")
prev = [x for x in instr_idx(m.lines) if x < i][-1]
assert "move-result v9" in m.lines[prev]
prev2 = [x for x in instr_idx(m.lines) if x < prev][-1]
assert "getExperimentalMode" in m.lines[prev2], m.lines[prev2]
m.replace_line("if-nez v9, :cond_d", [])

# ---------------------------------------------------------------- HudEditor (the screen editor)
HE = "Lcom/starpilot/starview/HudEditor;"
PV = "Lcom/starpilot/starview/HudEditor$Preview;"
ST = "Lcom/starpilot/starview/HudLayout$State;"
m = M("HudEditor", "<init>(Landroid/content/Context;Landroid/widget/FrameLayout;Lcom/starpilot/starview/HudLayout;"
                   "Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V")
m.replace_line("const v4, 0x3f147ae1    # 0.58f", ["const v4, 0x3f4ccccd    # 0.8f"])     # 2.85.7: preview 80 %
m.replace_line("const v4, 0x3ed70a3d    # 0.42f", ["const v4, 0x3e4ccccd    # 0.2f"])     # panel 20 %
m = M("HudEditor", "editTab$lambda$43(Lcom/starpilot/starview/HudEditor;Lcom/starpilot/starview/HudLayout$State;)Lkotlin/Unit;")
m.insert_before(f"invoke-direct {{p0}}, {HE}->changed()V",            # Reset: a free size goes back too
                ["const/high16 v0, 0x7fc00000    # Float.NaN", f"invoke-virtual {{p1, v0}}, {ST}->setW(F)V",
                 f"invoke-virtual {{p1, v0}}, {ST}->setH(F)V"])
m = M("HudEditor", "panel(Landroid/content/Context;)Landroid/view/View;")
i = m.find('const-string v10, "Done"')
k = min(x for x in range(i, len(m.lines)) if m.lines[x].strip() ==
        "invoke-virtual {v3, v4}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V")
a, b = m.context(k, k + 1)
old = "".join(m.lines[a:b])
m.edits.append((old, old + f"    invoke-virtual {{v0, v3, v1}}, {HE}->hybButtonRows(Landroid/widget/LinearLayout;Landroid/content/Context;)V\n"))
m = M("HudEditor", "editTab()V")
i = m.find('const-string v5, "Size"')
k = min(x for x in range(i, len(m.lines)) if m.lines[x].strip() ==
        "invoke-virtual {v4, v5, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V")
assert any("CarUi;->lp$default" in l for l in m.lines[k - 14:k]), "not the Size row's addView"
a, b = m.context(k, k + 1)
old = "".join(m.lines[a:b])
m.edits.append((old, old + f"    invoke-virtual {{v0, v1, v2}}, {HE}->hybFreeSize(Lcom/starpilot/starview/HudLayout$Item;{ST})V\n"))
m = M("HudEditor$Preview", "onTouchEvent(Landroid/view/MotionEvent;)Z")
m.insert_after(f"iput v7, v0, {PV}->startW:F", [f"invoke-virtual {{v0, v3}}, {PV}->hybStartH(Landroid/graphics/RectF;)V"])
i = m.find(":cond_7")
k = min(x for x in instr_idx(m.lines) if x > i)
assert m.lines[k].strip() == "move-object v7, v10", m.lines[k]
assert "startScale" in m.lines[min(x for x in instr_idx(m.lines) if x > k)]
a, b = m.context(k, k + 1)
old = "".join(m.lines[a:b])
m.edits.append((old, old + f"    invoke-virtual {{v0, v1, v10, v3, v11}}, {PV}->hybFreeResize(Lcom/starpilot/starview/HudLayout$Item;{ST}FF)Z\n"
                "    move-result v5\n    if-nez v5, :goto_0\n"))

# ---------------------------------------------------------------- LaneSuggest
# 2.86.6: the route's exit signals by itself only with exactly one lane to its side (was: unless 2 or more)
m = M("LaneSuggest", "exitDue(Lcom/starpilot/starview/LaneSuggest$In;J)Z")
i = m.find("invoke-virtual/range {p1 .. p1}, Lcom/starpilot/starview/LaneSuggest$In;->getExitSideLanes()I")
k = [x for x in instr_idx(m.lines) if x > i][:3]
assert [m.lines[x].strip() for x in k] == ["move-result v1", "const/4 v4, 0x2", "if-lt v1, v4, :cond_4"], k
m.replace_line("const/4 v4, 0x2", ["const/4 v4, 0x1"])
m.replace_line("if-lt v1, v4, :cond_4", ["if-eq v1, v4, :cond_4"])

# ---------------------------------------------------------------- VoiceNote
# 2.86.6: our listener (VoiceListen keeps every stretch of the note) and a segmented session
VN = "Lcom/starpilot/starview/VoiceNote;"
m = M("VoiceNote", "start(Ljava/io/File;Lkotlin/jvm/functions/Function1;)Ljava/lang/String;")
i = m.find("new-instance v9, Lcom/starpilot/starview/VoiceNote$start$1;")
j = m.find("invoke-direct {v9, p0}, Lcom/starpilot/starview/VoiceNote$start$1;-><init>(Lcom/starpilot/starview/VoiceNote;)V")
assert [x for x in instr_idx(m.lines) if x > i][0] == j
m.add(i, j + 1, [f"    invoke-virtual {{p0}}, {VN}->hybListener()Landroid/speech/RecognitionListener;\n", "\n",
                 "    move-result-object v9\n"])
m.insert_before("invoke-virtual {v4, v7}, Landroid/speech/SpeechRecognizer;->startListening(Landroid/content/Intent;)V",
                [f"invoke-virtual {{p0, v7}}, {VN}->hybIntent(Landroid/content/Intent;)V"])

# ---------------------------------------------------------------- checks + output
for m in METHODS:
  for old, new in m.edits:
    check_regs(m, [l.strip() for l in new.splitlines()])
  uses_p0 = any("p0" in new and "p0" not in old for old, new in m.edits) or any(
      re.search(r"\{p0", n) for _, n in m.edits)
  if uses_p0 and m.p_written("p0"):
    raise SystemExit(f"{m.cls}->{m.sig}: p0 is written: {m.p_written('p0')[:3]}")
  # apply in order to be sure they apply
  t = m.text
  for old, new in m.edits:
    assert t.count(old) == 1, (m.cls, m.sig, old[:200])
    t = t.replace(old, new)

with open(OUT, "w") as f:
  f.write('"""Edits to 2.84.1\'s own smali (decisions.py "patch"): (class, method) -> [(old text, new text), ...].\n\n'
          'Each old text must be in that method exactly once (apktool 2.10.0\'s output of the 2.84.1 APK, so it is fixed); they\n'
          'apply in order. The new text only calls into a new method of ours ("hyb...", in our source next to the code it\n'
          'stands for, which calls it at the same place), swaps a constant or drops a few lines, so 2.84.1\'s code stays as it\n'
          'was around it. Written by app/hybrid/genpatch.py from anchors, which also checks registers and labels.\n"""\n\n'
          'PATCHES = {\n')
  for m in METHODS:
    f.write(f"  ({m.cls!r}, {m.sig!r}): [\n")
    for old, new in m.edits:
      f.write(f"    ({old!r},\n     {new!r}),\n")
    f.write("  ],\n")
  f.write("}\n")
print(f"{sum(len(m.edits) for m in METHODS)} edits in {len(METHODS)} methods")
