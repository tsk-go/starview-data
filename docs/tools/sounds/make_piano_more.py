#!/usr/bin/env python3
"""More Comma-Piano packs, paced like StarPilot's own sounds (owner, Nov 23: "Give me more I think the high is too
fast check out Starpilot's sounds").

StarPilot's built-in sounds (selfdrive/assets/sounds_starpilot): piano-like notes in the middle of the keyboard (the
engage ~D#4-G#4, warnings around G4), engage / disengage four notes ~80 ms apart then a long ring; the warnings one
quick burst of notes that rings out, repeated every 0.65 s (prompt), 0.75 s (distracted, take control) and 0.8 s
(take control now) - not loud hammering. These packs keep that pace and register, on the same real instruments as
Comma-Piano (make_piano_pack.py: Salamander grand piano, VSCO 2 CE violins and harp):

  Comma-Piano-Calm     D major, gentle: engage D-A-D-F# rising slowly into strings; one soft dyad for the prompt
  Comma-Piano-Romance  B minor turning to D major: engage B-D-F# then the D chord opening, violins over it
  Comma-Piano-Grand    chords, not arpeggios: engage G/D to D like the opening of a theme, harp and strings
  Comma-Piano-Grand-Full  Grand with a full body: the piano's left hand, chords over both hands, cellos and violas
                       under the violins, a warmer tone and a bigger hall

  python3 make_piano_more.py OUT_DIR [CACHE]
"""
import sys
from pathlib import Path

import numpy as np

sys.path.insert(0, str(Path(__file__).resolve().parent))
import make_starview_pack as base  # noqa: E402
import make_piano_pack as pp  # noqa: E402
from make_piano_pack import piano, violins, harp_sweep, tremolo_loop, base_place as put, one_shot, loop  # noqa: E402

SR = base.SR
P_PROMPT, P_DIST, P_CRIT, P_NOW, P_MAX = 0.65, 0.75, 0.75, 0.8, 0.8  # StarPilot's own repeat times


def notes_at(x, seq, step, hold, vel=1.0, ring=0.3, start=0.0):
  """Notes one after another, `step` apart, each held `hold` (a list of names, or (name, vel) pairs)."""
  for i, nm in enumerate(seq):
    v = vel
    if isinstance(nm, tuple):
      nm, v = nm[0], vel * nm[1]
    put(x, piano(nm, hold, ring, v), start + i * step)


def chord(x, names, t0, hold, vel=1.0, ring=0.35, roll=0.012):
  """A chord, rolled a little from the bottom like a pianist's hand."""
  for i, nm in enumerate(names):
    put(x, piano(nm, hold, ring, vel / len(names) ** 0.35), t0 + i * roll)


def burst(seq, period, step=0.05, hold=0.45, vel=1.0, drive=0.0, verb=0.12, bed=None):
  """StarPilot's warning shape: a quick burst of notes, ringing out, once per period."""
  return loop(period, lambda x: notes_at(x, seq, step, hold, vel, 0.12), tail=1.0, verb=verb, drive=drive, bed=bed)


def warnings(prompt, dist, crit, now, maxed, bed_notes):
  return {
    "warning.wav": lambda: loop(P_PROMPT, lambda x: chord(x, prompt, 0.0, 0.4, 0.9, 0.15), tail=1.0, verb=0.14),
    "dm_warning.wav": lambda: burst(dist, P_DIST, 0.055, 0.4, 0.9),
    "critical.wav": lambda: burst(crit, P_CRIT, 0.05, 0.42, 1.0, drive=1.4),
    "dm_critical.wav": lambda: burst(now, P_NOW, 0.045, 0.45, 1.1, drive=1.7),
    # the last step: the same burst twice per period over quiet tremolo violins - still StarPilot's 0.8 s pace
    "dm_critical_max.wav": lambda: loop(P_MAX, lambda x: (notes_at(x, maxed, 0.045, 0.3, 1.15, 0.1),
                                                          notes_at(x, maxed, 0.045, 0.3, 1.15, 0.1, start=0.4)),
                                        tail=0.8, verb=0.08, drive=2.0,
                                        bed=lambda n: pp.tremolo_loop(bed_notes, n, 0.35)),
  }


# ---------------------------------------------------------------- Calm (D major)
def calm_engage(x):
  notes_at(x, (("D4", .7), ("A4", .75), ("D5", .9), ("F#5", 1.25)), 0.11, 1.1, ring=0.45)
  put(x, violins(("D4", "A4"), 1.6, attack=0.45, release=0.6), 0.1, 0.55)
  put(x, violins(("F#5",), 1.2, attack=0.2, release=0.5), 0.33, 0.55)


def calm_disengage(x):
  notes_at(x, (("F#5", 1.2), ("D5", 1.0), ("A4", .85), ("D4", .8)), 0.1, 0.9, ring=0.45)
  put(x, violins(("D4", "A4"), 1.3, attack=0.06, release=0.9, vel=0.7), 0.0, 0.5)


CALM = {
  "engage.wav": lambda: one_shot(1.7, calm_engage, verb=0.28),
  "disengage.wav": lambda: one_shot(1.4, calm_disengage, verb=0.25),
  "refuse.wav": lambda: one_shot(0.65, lambda x: chord(x, ("C#5", "D5"), 0.0, 0.12, 0.9, 0.12, 0.0), verb=0.12, fade=0.15),
  "pre_alert.wav": lambda: one_shot(0.9, lambda x: (harp_sweep(x, ("D4", "F#4", "A4", "D5"), 0.0, 0.035, 0.45),
                                                    put(x, piano("A4", 0.45, 0.25, 0.9), 0.15)), verb=0.22, fade=0.25),
  **warnings(prompt=("A4", "D5"), dist=("F#4", "A4", "D5"), crit=("D5", "F#5", "A5"), now=("A4", "D5", "F#5", "A5"),
             maxed=("D5", "F#5", "A5", "D6"), bed_notes=("D5", "A5")),
}


# ---------------------------------------------------------------- Romance (B minor -> D major)
def romance_engage(x):
  notes_at(x, (("B4", .8), ("D5", .85), ("F#5", 1.0)), 0.12, 0.6, ring=0.4)
  chord(x, ("D4", "A4", "D5", "F#5", "A5"), 0.38, 1.0, 1.2, 0.45, 0.018)
  put(x, violins(("A5",), 1.3, attack=0.25, release=0.6), 0.38, 0.6)
  put(x, violins(("D4", "F#4"), 1.5, attack=0.4, release=0.6), 0.1, 0.45)


def romance_disengage(x):
  notes_at(x, (("A5", 1.1), ("F#5", 1.0), ("D5", .9)), 0.12, 0.25, ring=0.3)
  chord(x, ("B3", "F#4", "B4", "D5"), 0.36, 0.8, 1.0, 0.45, 0.018)
  put(x, violins(("B4",), 1.2, attack=0.15, release=0.8, vel=0.7), 0.3, 0.45)


ROMANCE = {
  "engage.wav": lambda: one_shot(1.8, romance_engage, verb=0.28),
  "disengage.wav": lambda: one_shot(1.5, romance_disengage, verb=0.26),
  "refuse.wav": lambda: one_shot(0.65, lambda x: chord(x, ("B4", "C5"), 0.0, 0.12, 0.9, 0.12, 0.0), verb=0.12, fade=0.15),
  "pre_alert.wav": lambda: one_shot(0.9, lambda x: (harp_sweep(x, ("B3", "D4", "F#4", "B4"), 0.0, 0.035, 0.45),
                                                    put(x, piano("F#5", 0.45, 0.25, 0.9), 0.15)), verb=0.22, fade=0.25),
  **warnings(prompt=("F#4", "B4"), dist=("D4", "F#4", "B4"), crit=("B4", "D5", "F#5"), now=("F#4", "B4", "D5", "F#5"),
             maxed=("B4", "D5", "F#5", "B5"), bed_notes=("B4", "F#5")),
}


# ---------------------------------------------------------------- Grand (chords, D major)
def grand_engage(x):
  harp_sweep(x, ("D4", "G4", "B4", "D5", "G5"), 0.0, 0.03, 0.35)
  chord(x, ("D4", "G4", "B4", "D5"), 0.0, 0.32, 1.0, 0.3, 0.015)
  chord(x, ("D4", "F#4", "A4", "D5", "F#5"), 0.36, 1.1, 1.25, 0.5, 0.015)
  put(x, violins(("D4", "A4", "F#5"), 1.6, attack=0.35, release=0.6), 0.2, 0.6)


def grand_disengage(x):
  chord(x, ("D4", "F#4", "A4", "D5"), 0.0, 0.3, 1.1, 0.3, 0.015)
  chord(x, ("A3", "D4", "G4", "B4"), 0.34, 0.9, 1.0, 0.5, 0.02)
  put(x, violins(("D4", "G4"), 1.3, attack=0.08, release=0.9, vel=0.7), 0.0, 0.5)


GRAND = {
  "engage.wav": lambda: one_shot(1.8, grand_engage, verb=0.3),
  "disengage.wav": lambda: one_shot(1.5, grand_disengage, verb=0.27),
  "refuse.wav": lambda: one_shot(0.65, lambda x: chord(x, ("D4", "G#4", "D5"), 0.0, 0.12, 1.0, 0.12, 0.0), verb=0.12, fade=0.15),
  "pre_alert.wav": lambda: one_shot(0.9, lambda x: (harp_sweep(x, ("D4", "F#4", "A4", "D5"), 0.0, 0.035, 0.45),
                                                    chord(x, ("A4", "D5"), 0.15, 0.45, 0.9, 0.25)), verb=0.22, fade=0.25),
  **warnings(prompt=("D4", "A4", "D5"), dist=("D4", "F#4", "A4"), crit=("F#4", "A4", "D5"), now=("D4", "F#4", "A4", "D5"),
             maxed=("F#4", "A4", "D5", "F#5"), bed_notes=("A4", "D5")),
}

# ---------------------------------------------------------------- Grand, full body (owner, Nov 23: "I like the Comma
# Piano Grand. Make it more full body"): the piano's left hand (a bass octave under every chord and burst), chords
# spread over both hands, cellos and violas under the violins, a warmer tone (low-mids up, the very top down) and a
# bigger hall. The warnings keep StarPilot's pace; each burst note is doubled an octave down, the first with a bass.
from scipy.signal import lfilter  # noqa: E402
from make_signature_pack import hall  # noqa: E402
from make_piano_pack import section  # noqa: E402


def warm(x, presence=False):
  """+3.5 dB around 250 Hz (an octave and a half wide), -2 dB above 7 kHz: body without mud. And nothing under
  ~110 Hz: the comma's small speaker can't play it, and it would only eat the headroom the body needs (the bass
  notes' overtones still carry them)."""
  n = len(x)
  X = np.fft.rfft(np.concatenate([x, np.zeros(n)]))
  f = np.maximum(np.fft.rfftfreq(2 * n, 1 / SR), 1.0)
  g = 1 + 0.5 * np.exp(-(np.log2(f / 250) / 0.75) ** 2)
  g *= 1 - 0.2 / (1 + (7000 / f) ** 4)
  g *= f ** 4 / (f ** 4 + 110.0 ** 4)
  if presence:  # the warnings: +5.5 dB around 2 kHz so they cut through road noise on the small speaker
    g *= 1 + 0.9 * np.exp(-(np.log2(f / 2000) / 1.0) ** 2)
  return np.fft.irfft(X * g, 2 * n)[:n]


def full_one_shot(length, build, verb=0.32, fade=0.45):
  x = np.zeros(int(length * SR))
  build(x)
  x = hall(warm(x), verb, room=0.87, damp=0.3)
  x = np.tanh(x / np.max(np.abs(x)) * 1.35) / np.tanh(1.35)
  return base.finish(x, fade_out=fade)


def full_loop(period, build, tail=1.2, verb=0.14, drive=1.3, peak=0.89, bed=None):
  n = int(period * SR)
  x = np.zeros(n + int(tail * SR))
  build(x)
  x = hall(warm(x, presence=True), verb, room=0.86, damp=0.3)
  y = x[:n].copy()
  rest = x[n:]
  while len(rest):
    k = min(n, len(rest))
    y[:k] += rest[:k]
    rest = rest[k:]
  if bed is not None:
    y += warm(bed(n))
  y -= y.mean()
  return base.finish(y, peak=peak, fade_out=0, drive=drive, looped=True)


def down(nm, octaves=1):
  return nm[:-1] + str(int(nm[-1]) - octaves)


def bass(x, nm, t0, hold, vel=1.0):
  """The left hand: the note and the octave under it."""
  put(x, piano(nm, hold, 0.5, 0.8 * vel), t0)
  put(x, piano(down(nm), hold, 0.5, 0.7 * vel), t0 + 0.004)


def full_burst(seq, bass_note, period, step=0.05, hold=0.5, vel=1.0, drive=1.3, bed=None):
  def build(x):
    bass(x, bass_note, 0.0, hold + 0.1, 0.65 * vel)
    for i, nm in enumerate(seq):
      put(x, piano(nm, hold, 0.14, vel), i * step)
      put(x, piano(down(nm), hold, 0.14, 0.38 * vel), i * step + 0.003)
  return full_loop(period, build, drive=drive, bed=bed)


def gfull_engage(x):
  harp_sweep(x, ("D3", "G3", "B3", "D4", "G4", "B4", "D5"), 0.0, 0.026, 0.4)
  bass(x, "D3", 0.0, 0.36, 1.0)
  chord(x, ("D4", "G4", "B4", "D5", "G5"), 0.0, 0.34, 1.05, 0.3, 0.014)
  bass(x, "D3", 0.38, 1.5, 1.1)
  chord(x, ("D4", "F#4", "A4", "D5", "F#5", "A5"), 0.38, 1.4, 1.35, 0.55, 0.014)
  put(x, section("cello", ("D3", "A3"), 2.25, attack=0.3, release=0.8), 0.02, 0.9)
  put(x, section("viola", ("G4", "B4"), 0.55, attack=0.1, release=0.25), 0.0, 0.4)
  put(x, section("viola", ("F#4", "A4"), 1.85, attack=0.2, release=0.8), 0.36, 0.65)
  put(x, violins(("D5", "G5"), 0.55, attack=0.1, release=0.25), 0.0, 0.35)
  put(x, violins(("F#5", "A5", "D6"), 1.85, attack=0.2, release=0.8), 0.36, 0.55)


def gfull_disengage(x):
  bass(x, "D3", 0.0, 0.34, 1.1)
  chord(x, ("D4", "F#4", "A4", "D5", "F#5"), 0.0, 0.32, 1.15, 0.3, 0.014)
  bass(x, "B2", 0.36, 1.2, 1.0)
  chord(x, ("D4", "G4", "B4", "D5"), 0.36, 1.1, 1.05, 0.55, 0.018)
  put(x, section("cello", ("D3",), 0.5, attack=0.06, release=0.3), 0.0, 0.85)
  put(x, section("cello", ("B2", "G3"), 1.5, attack=0.12, release=0.9), 0.34, 0.9)
  put(x, violins(("D5", "G5"), 1.5, attack=0.1, release=1.0, vel=0.7), 0.3, 0.45)


GRAND_FULL = {
  "engage.wav": lambda: full_one_shot(2.3, gfull_engage),
  "disengage.wav": lambda: full_one_shot(1.9, gfull_disengage),
  "refuse.wav": lambda: full_one_shot(0.75, lambda x: (bass(x, "D3", 0.0, 0.12, 1.0),
                                                       chord(x, ("D4", "G#4", "D5"), 0.0, 0.12, 1.0, 0.12, 0.0),
                                                       chord(x, ("D4", "G#4"), 0.17, 0.1, 0.7, 0.1, 0.0)),
                                      verb=0.14, fade=0.2),
  "pre_alert.wav": lambda: full_one_shot(1.0, lambda x: (harp_sweep(x, ("D3", "F#3", "A3", "D4", "F#4", "A4", "D5"), 0.0, 0.03, 0.45),
                                                         bass(x, "D3", 0.15, 0.5, 0.8),
                                                         chord(x, ("A3", "D4", "A4", "D5"), 0.15, 0.5, 1.0, 0.25)),
                                         verb=0.24, fade=0.3),
  "warning.wav": lambda: full_loop(P_PROMPT, lambda x: (bass(x, "D3", 0.0, 0.45, 0.6),
                                                        chord(x, ("D4", "A4", "D5"), 0.0, 0.45, 0.95, 0.15))),
  "dm_warning.wav": lambda: full_burst(("D4", "F#4", "A4"), "D3", P_DIST, 0.055, 0.45, 0.9),
  "critical.wav": lambda: full_burst(("F#4", "A4", "D5"), "D3", P_CRIT, 0.05, 0.45, 1.0, drive=1.9),
  "dm_critical.wav": lambda: full_burst(("D4", "F#4", "A4", "D5"), "D3", P_NOW, 0.045, 0.48, 1.1, drive=2.2),
  "dm_critical_max.wav": lambda: full_loop(P_MAX, lambda x: [(bass(x, "D3", t0, 0.3, 0.7),
                                                              [(put(x, piano(nm, 0.3, 0.1, 1.15), t0 + i * 0.045),
                                                                put(x, piano(down(nm), 0.3, 0.1, 0.4), t0 + i * 0.045 + 0.003))
                                                               for i, nm in enumerate(("F#4", "A4", "D5", "F#5"))])
                                                             for t0 in (0.0, 0.4)],
                                           tail=0.8, verb=0.09, drive=2.1,
                                           bed=lambda n: pp.tremolo_loop(("A4", "D5"), n, 0.35)),
}

PACKS = {"Comma-Piano-Calm": CALM, "Comma-Piano-Romance": ROMANCE, "Comma-Piano-Grand": GRAND,
         "Comma-Piano-Grand-Full": GRAND_FULL}


def main(out, cache=None):
  if cache:
    pp.CACHE = Path(cache)
  out = Path(out)
  for name, sounds in PACKS.items():
    folder = out / name / "sounds"
    folder.mkdir(parents=True, exist_ok=True)
    preview = []
    for fname, make in sounds.items():
      x = make()
      base.write(folder / fname, x)
      preview += [np.tile(x, {"warning.wav": 4, "dm_warning.wav": 4, "critical.wav": 4, "dm_critical.wav": 4,
                               "dm_critical_max.wav": 4}.get(fname, 1)), np.zeros(int(0.7 * SR))]
    base.write(out / f"{name}.preview.wav", np.concatenate(preview) * 0.95)
    print(name, "ok")


if __name__ == "__main__":
  main(sys.argv[1] if len(sys.argv) > 1 else ".", sys.argv[2] if len(sys.argv) > 2 else None)
