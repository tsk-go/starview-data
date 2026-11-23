#!/usr/bin/env python3
"""Comma-Grand-Orchestra: Comma-Piano-Grand-Full with more instruments (owner, Nov 23: "What I meant add more
instruments") - French horns, flute, oboe, a solo violin and timpani join the grand piano, harp and the string
sections, Yanni-style. Same chords (G/D to D for engage), same StarPilot pace for the warnings.

  engage        a soft timpani roll into the D chord's downbeat (timpani hit); piano G/D then D with its left hand;
                horns holding the chords; the solo violin and the flute taking the top line (D6 / A5); the oboe
                moving B4 to A4 between the chords; cellos, violas and violins under it all; harp sweep
  disengage     D then G/B: horns and cellos stepping down, flute A5 to G5, a soft timpani note on the second chord
  refuse        the piano's cluster with a low horn and a muted timpani stroke
  pre_alert     harp sweep, piano chord, a flute note
  prompt        the piano's chord with a soft flute A5 on top (StarPilot's 0.65 s)
  distracted    the piano burst with the oboe on its last note (0.75 s)
  take control  the piano burst doubled by short horns and a timpani stroke, louder at each step (0.75, 0.8, 0.8 s)

Instruments: Salamander Grand Piano (Alexander Holm, CC-BY 3.0) and VS Chamber Orchestra: Community Edition
(Versilian Studios, CC0) - see make_piano_pack.py.

  python3 make_orchestra_pack.py OUT_DIR [CACHE]
"""
import sys
from pathlib import Path

import numpy as np

sys.path.insert(0, str(Path(__file__).resolve().parent))
import make_starview_pack as base  # noqa: E402
import make_piano_pack as pp  # noqa: E402
from make_piano_pack import piano, violins, harp_sweep, section, play, base_place as put  # noqa: E402
from make_piano_more import (chord, bass, down, full_one_shot, full_loop, P_PROMPT, P_DIST, P_CRIT, P_NOW,  # noqa: E402
                             P_MAX)

SR = base.SR
t_of = base.t_of


def winds(kind, notes, length, attack=0.08, release=0.4, vel=1.0):
  """A wind or solo instrument with its own attack (no bow skip), shaped to `length`."""
  return section(kind, notes, length, attack=attack, release=release, vel=vel, skip=0.0)


def timp(note, length=1.5, vel=1.0):
  return play("timp", note, length) * vel


def roll(note, length, vel=1.0):
  """A timpani roll swelling in over `length` (taken from the middle of the recorded roll)."""
  x = play("timproll", note, length + 1.0)[int(1.0 * SR):][:int(length * SR)]
  t = t_of(len(x))
  return x * (t / length) ** 1.6 * vel


def stab(kind, notes, length, vel=1.0):
  """A short brass note: the recording's own attack, cut after `length` with a quick fade."""
  n = int((length + 0.08) * SR)
  x = sum(play(kind, nm, length + 0.08) for nm in notes)[:n]
  t = t_of(n)
  return x * np.clip((length + 0.08 - t) / 0.08, 0, 1) * vel / len(notes) ** 0.5


def engage(x):
  harp_sweep(x, ("D3", "G3", "B3", "D4", "G4", "B4", "D5"), 0.0, 0.026, 1.0)
  put(x, roll("D3", 0.38, 1.0), 0.0)
  put(x, timp("D3", 2.0, 1.6), 0.38)
  bass(x, "D3", 0.0, 0.36, 1.0)
  chord(x, ("D4", "G4", "B4", "D5", "G5"), 0.0, 0.34, 1.0, 0.3, 0.014)
  bass(x, "D3", 0.38, 1.5, 1.1)
  chord(x, ("D4", "F#4", "A4", "D5", "F#5", "A5"), 0.38, 1.4, 1.3, 0.55, 0.014)
  put(x, winds("horn", ("G3", "B3", "D4"), 0.5, attack=0.12, release=0.2), 0.0, 0.9)
  put(x, winds("horn", ("F#3", "A3", "D4"), 1.95, attack=0.08, release=0.8), 0.36, 1.25)
  put(x, winds("oboe", ("B4",), 0.42, attack=0.06, release=0.12), 0.0, 0.6)
  put(x, winds("oboe", ("A4",), 1.7, attack=0.05, release=0.7), 0.38, 0.6)
  put(x, winds("flute", ("A5",), 1.75, attack=0.06, release=0.8), 0.38, 0.95)
  put(x, winds("solovln", ("D6",), 1.85, attack=0.1, release=0.8), 0.38, 1.15)
  put(x, section("cello", ("D3", "A3"), 2.3, attack=0.3, release=0.8), 0.02, 0.85)
  put(x, section("viola", ("G4", "B4"), 0.55, attack=0.1, release=0.25), 0.0, 0.4)
  put(x, section("viola", ("F#4", "A4"), 1.9, attack=0.2, release=0.8), 0.36, 0.6)
  put(x, violins(("D5", "G5"), 0.55, attack=0.1, release=0.25), 0.0, 0.35)
  put(x, violins(("F#5", "A5", "D6"), 1.9, attack=0.2, release=0.8), 0.36, 0.5)


def disengage(x):
  bass(x, "D3", 0.0, 0.34, 1.1)
  chord(x, ("D4", "F#4", "A4", "D5", "F#5"), 0.0, 0.32, 1.1, 0.3, 0.014)
  bass(x, "B2", 0.36, 1.2, 1.0)
  chord(x, ("D4", "G4", "B4", "D5"), 0.36, 1.1, 1.0, 0.55, 0.018)
  put(x, timp("B2", 1.5, 0.9), 0.36)
  put(x, winds("horn", ("F#3", "A3", "D4"), 0.42, attack=0.05, release=0.15), 0.0, 1.0)
  put(x, winds("horn", ("G3", "B3", "D4"), 1.45, attack=0.08, release=0.9), 0.36, 1.0)
  put(x, winds("flute", ("A5",), 0.42, attack=0.05, release=0.15), 0.0, 0.6)
  put(x, winds("flute", ("G5",), 1.4, attack=0.05, release=0.9), 0.36, 0.6)
  put(x, section("cello", ("D3",), 0.5, attack=0.06, release=0.3), 0.0, 0.85)
  put(x, section("cello", ("B2", "G3"), 1.5, attack=0.12, release=0.9), 0.34, 0.9)
  put(x, violins(("D5", "G5"), 1.5, attack=0.1, release=1.0, vel=0.7), 0.3, 0.45)


def refuse(x):
  bass(x, "D3", 0.0, 0.12, 1.0)
  chord(x, ("D4", "G#4", "D5"), 0.0, 0.12, 1.0, 0.12, 0.0)
  chord(x, ("D4", "G#4"), 0.17, 0.1, 0.7, 0.1, 0.0)
  put(x, stab("horn", ("D3", "G#3"), 0.14, 0.9), 0.0)
  put(x, timp("D3", 0.5, 0.6), 0.0)


def pre_alert(x):
  harp_sweep(x, ("D3", "F#3", "A3", "D4", "F#4", "A4", "D5"), 0.0, 0.03, 1.0)
  bass(x, "D3", 0.15, 0.5, 0.8)
  chord(x, ("A3", "D4", "A4", "D5"), 0.15, 0.5, 1.0, 0.25)
  put(x, winds("flute", ("A5",), 0.6, attack=0.05, release=0.35), 0.15, 0.6)


def burst_with(seq, bass_note, step, hold, vel, extra):
  def build(x):
    bass(x, bass_note, 0.0, hold + 0.1, 0.65 * vel)
    for i, nm in enumerate(seq):
      put(x, piano(nm, hold, 0.14, vel), i * step)
      put(x, piano(down(nm), hold, 0.14, 0.38 * vel), i * step + 0.003)
    extra(x)
  return build


SOUNDS = {
  "engage.wav": lambda: full_one_shot(2.4, engage, verb=0.34),
  "disengage.wav": lambda: full_one_shot(1.9, disengage, verb=0.3),
  "refuse.wav": lambda: full_one_shot(0.75, refuse, verb=0.14, fade=0.2),
  "pre_alert.wav": lambda: full_one_shot(1.0, pre_alert, verb=0.24, fade=0.3),
  "warning.wav": lambda: full_loop(P_PROMPT, lambda x: (bass(x, "D3", 0.0, 0.45, 0.6),
                                                        chord(x, ("D4", "A4", "D5"), 0.0, 0.45, 0.95, 0.15),
                                                        put(x, winds("flute", ("A5",), 0.45, attack=0.04, release=0.2), 0.0, 0.45))),
  "dm_warning.wav": lambda: full_loop(P_DIST, burst_with(("D4", "F#4", "A4"), "D3", 0.055, 0.45, 0.9,
                                                         lambda x: put(x, winds("oboe", ("A4",), 0.45, attack=0.03, release=0.2), 0.11, 0.5))),
  "critical.wav": lambda: full_loop(P_CRIT, burst_with(("F#4", "A4", "D5"), "D3", 0.05, 0.45, 1.0,
                                                       lambda x: (put(x, stab("horn", ("A3", "D4"), 0.22, 1.0), 0.0),
                                                                  put(x, timp("D3", 0.7, 0.5), 0.0))), drive=1.9),
  "dm_critical.wav": lambda: full_loop(P_NOW, burst_with(("D4", "F#4", "A4", "D5"), "D3", 0.045, 0.48, 1.1,
                                                         lambda x: (put(x, stab("horn", ("F#3", "A3", "D4"), 0.26, 1.15), 0.0),
                                                                    put(x, timp("D3", 0.8, 0.65), 0.0))), drive=2.2),
  "dm_critical_max.wav": lambda: full_loop(P_MAX, lambda x: [(bass(x, "D3", t0, 0.3, 0.7),
                                                              [(put(x, piano(nm, 0.3, 0.1, 1.15), t0 + i * 0.045),
                                                                put(x, piano(down(nm), 0.3, 0.1, 0.4), t0 + i * 0.045 + 0.003))
                                                               for i, nm in enumerate(("F#4", "A4", "D5", "F#5"))],
                                                              put(x, stab("horn", ("A3", "D4", "F#4"), 0.2, 1.2), t0),
                                                              put(x, timp("D3", 0.4, 0.7), t0))
                                                             for t0 in (0.0, 0.4)],
                                           tail=0.8, verb=0.09, drive=2.1,
                                           bed=lambda n: pp.tremolo_loop(("A4", "D5"), n, 0.35)),
}


def main(out, cache=None):
  if cache:
    pp.CACHE = Path(cache)
  out = Path(out)
  folder = out / "Comma-Grand-Orchestra" / "sounds"
  folder.mkdir(parents=True, exist_ok=True)
  preview = []
  for fname, make in SOUNDS.items():
    x = make()
    base.write(folder / fname, x)
    preview += [np.tile(x, 4 if fname in base.LOOPED else 1), np.zeros(int(0.7 * SR))]
    print(f"{fname:22s} {len(x) / SR:.2f} s  rms {np.sqrt(np.mean(x * x)):.2f}")
  base.write(out / "Comma-Grand-Orchestra.preview.wav", np.concatenate(preview) * 0.95)


if __name__ == "__main__":
  main(sys.argv[1] if len(sys.argv) > 1 else ".", sys.argv[2] if len(sys.argv) > 2 else None)
