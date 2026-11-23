#!/usr/bin/env python3
"""Comma-Piano: the comma's alerts as short Yanni-style phrases on real recorded instruments (owner, Nov 22: "I love
the first 5 minutes" of a Yanni collection - grand piano over lush strings, harp sweeps, a warm hall).

Instruments (downloaded on first run into CACHE, not kept in git):
  grand piano   Salamander Grand Piano by Alexander Holm, CC-BY 3.0 (the Tone.js mp3 set of it)
  strings, harp VS Chamber Orchestra: Community Edition by Versilian Studios, CC0 (violin section sustain with
                vibrato and tremolo, Kaleidoscope harp). Its string notes are named an octave low (VlnEns A2 is A3).

Key of D major. Nothing like the tablet: no sine beeps, bells, marimba, chirps, no synth pad.

  engage        piano arpeggio up D-F#-A-D, then E-F#, the violins swelling under it and taking up the last
                note, a harp sweep
  disengage     the piano coming down F#-D-A-F# and settling on D, the violins fading
  refuse        two soft clusters (D + D#), damped
  pre_alert     a harp sweep up to a high A on the piano
  prompt        A then D on the piano (repeats while it lasts)
  distracted    D-E-F# on the piano, quicker (repeats)
  take control  piano octaves hammered over tremolo violins, faster and higher at each step (seamless loops)

  python3 make_piano_pack.py OUT_DIR [CACHE]
"""
import subprocess
import sys
import urllib.request
from fractions import Fraction
from functools import lru_cache
from pathlib import Path

import numpy as np
from scipy.signal import resample_poly

sys.path.insert(0, str(Path(__file__).resolve().parent))
import make_starview_pack as base  # noqa: E402
from make_starview_pack import SR, t_of  # noqa: E402
from make_signature_pack import hall  # noqa: E402

VSCO = "https://raw.githubusercontent.com/sgossner/VSCO-2-CE/master/"
TONE = "https://raw.githubusercontent.com/Tonejs/audio/master/salamander/"
NAMES = {"C": 0, "C#": 1, "Db": 1, "D": 2, "D#": 3, "Ds": 3, "Eb": 3, "E": 4, "F": 5, "F#": 6, "Fs": 6, "Gb": 6,
         "G": 7, "G#": 8, "Ab": 8, "A": 9, "A#": 10, "Bb": 10, "B": 11}

# (instrument, real MIDI note) -> (url, file name in the cache)
PIANO = {nm: (TONE + f"{nm}.mp3", f"piano_{nm}.mp3")
         for nm in ("A3", "C4", "Ds4", "Fs4", "A4", "C5", "Ds5", "Fs5", "A5", "C6", "Ds6", "Fs6", "A6", "C7", "Ds7",
                    "Fs7", "A7", "C8")}
VIOLIN = {nm: (VSCO + f"Strings/Violin%20Section/susVib/VlnEns_susVib_{nm.replace('#', '%23')}_v2.wav",
               f"violin_{nm.replace('#', 's')}.wav") for nm in ("G2", "A2", "B2", "D3", "F#3", "A3", "C4", "E4", "G4", "B4", "D5")}
TREM = {nm: (VSCO + f"Strings/Violin%20Section/Trem/VlnEns_Trem_{nm}_v2.wav", f"trem_{nm}.wav")
        for nm in ("G2", "A2", "B2", "D3", "A3", "C4", "E4", "G4", "B4", "D5")}
HARP = {nm: (VSCO + f"Strings/Harp/KSHarp_{nm}_mf.wav", f"harp_{nm}.wav")
        for nm in ("G3", "B3", "D4", "F4", "A4", "C5", "E5", "G5", "B5", "D6", "F6", "A6", "B6")}
OCTAVE_LOW = {"violin": 12, "trem": 12, "piano": 0, "harp": 0}  # VSCO's string names are an octave low
CACHE = Path("/tmp/comma-piano-samples")


def midi(name):
  return NAMES[name[:-1]] + 12 * (int(name[-1]) + 1)


def decode(path):
  raw = subprocess.run(["ffmpeg", "-v", "error", "-i", str(path), "-ac", "1", "-ar", str(SR), "-f", "f32le", "-"],
                       capture_output=True, check=True).stdout
  return np.frombuffer(raw, np.float32).astype(float)


@lru_cache(maxsize=None)
def sample(kind, name):
  url, fname = {"piano": PIANO, "violin": VIOLIN, "trem": TREM, "harp": HARP}[kind][name]
  path = CACHE / fname
  if not path.exists():
    CACHE.mkdir(parents=True, exist_ok=True)
    urllib.request.urlretrieve(url, path)
  x = decode(path)
  onset = max(0, int(np.argmax(np.abs(x) > 0.02 * np.max(np.abs(x)))) - int(0.002 * SR))
  return x[onset:]


def table(kind):
  return {"piano": PIANO, "violin": VIOLIN, "trem": TREM, "harp": HARP}[kind]


def play(kind, note, length):
  """The note from the nearest recorded one, shifted by resampling; `length` seconds of it."""
  target = midi(note)
  names = list(table(kind))
  src = min(names, key=lambda nm: abs(midi(nm) + OCTAVE_LOW[kind] - target))
  shift = target - (midi(src) + OCTAVE_LOW[kind])
  x = sample(kind, src)
  need = int(length * SR * 2 ** (shift / 12)) + 64
  x = x[:need]
  if shift:
    r = Fraction(2 ** (-shift / 12)).limit_denominator(400)
    x = resample_poly(x, r.numerator, r.denominator)
  n = int(length * SR)
  return np.pad(x, (0, max(0, n - len(x))))[:n]


def piano(note, hold, ring=0.35, vel=1.0):
  """A key held `hold` seconds, then the damper (a quick fade over `ring`)."""
  x = play("piano", note, hold + ring)
  t = t_of(len(x))
  x *= np.where(t > hold, np.exp(-(t - hold) / (ring / 4)), 1.0)
  return x * vel


def violins(notes, length, attack=0.35, release=0.5, vel=1.0, skip=0.25):
  """A string swell: the sustained part of each note (past the bow's start), faded in and out."""
  n = int(length * SR)
  x = np.zeros(n)
  for nm in notes:
    x += play("violin", nm, length + skip)[int(skip * SR):][:n]
  t = t_of(n)
  env = np.minimum(1, t / attack) ** 2 * np.clip((length - t) / release, 0, 1) ** 1.5
  return x * env * vel / len(notes) ** 0.5


def tremolo_loop(notes, n, vel=1.0):
  """`n` samples of tremolo violins that loop: a steady stretch with its end crossfaded into its start."""
  xf = int(0.04 * SR)
  seg = sum(play("trem", nm, (n + xf) / SR + 1.0)[int(0.8 * SR):][:n + xf] for nm in notes)
  out = seg[:n].copy()
  w = np.linspace(0, 1, xf)
  out[:xf] = seg[:xf] * w + seg[n:n + xf] * (1 - w)
  return out * vel / len(notes) ** 0.5


def harp_sweep(x, notes, start, step, vel=1.0):
  for i, nm in enumerate(notes):
    base_place(x, play("harp", nm, 1.4) * (0.7 + 0.3 * i / max(1, len(notes) - 1)), start + i * step, vel)


def base_place(out, x, start, gain=1.0):
  s0 = int(start * SR)
  k = min(len(x), len(out) - s0)
  if k > 0:
    out[s0:s0 + k] += x[:k] * gain


# ---------------------------------------------------------------- rendering
def one_shot(length, build, verb=0.3, fade=0.35):
  x = np.zeros(int(length * SR))
  build(x)
  x = hall(x, verb)
  x = np.tanh(x / np.max(np.abs(x)) * 1.15) / np.tanh(1.15)  # a little glue
  return base.finish(x, fade_out=fade)


def loop(period, build, tail=1.2, verb=0.12, drive=0.0, peak=0.89, bed=None):
  n = int(period * SR)
  x = np.zeros(n + int(tail * SR))
  build(x)
  x = hall(x, verb)
  y = x[:n].copy()
  rest = x[n:]
  while len(rest):
    k = min(n, len(rest))
    y[:k] += rest[:k]
    rest = rest[k:]
  if bed is not None:  # a steady layer that already loops (tremolo strings)
    y += bed(n)
  y -= y.mean()
  return base.finish(y, peak=peak, fade_out=0, drive=drive, looped=True)


def engage(x):
  harp_sweep(x, ("A4", "B4", "D5", "E5", "F#5", "A5", "B5", "D6"), 0.0, 0.022, 0.35)
  # the arpeggio stays under the melody; the high notes are quieter in the recording, so they're lifted to sing on top
  for nm, t0, hold, v in (("D5", 0.0, 0.9, .6), ("F#5", .075, 0.85, .6), ("A5", .15, 0.8, .7), ("D6", .225, .25, 1.2),
                          ("E6", .45, .15, 1.6), ("F#6", .62, 1.0, 2.2)):
    base_place(x, piano(nm, hold, 0.4, v), t0)
  base_place(x, violins(("A4", "D5"), 1.85, attack=0.45, release=0.7), 0.05, 0.5)
  # the violins take up the melody's last note with the piano, as in Yanni's themes
  base_place(x, violins(("F#6",), 1.3, attack=0.18, release=0.6), 0.62, 0.75)


def disengage(x):
  for nm, t0, hold, v in (("F#6", 0.0, .1, 1.5), ("D6", .09, .1, 1.2), ("A5", .18, .1, 1.0), ("F#5", .27, .12, .9),
                          ("D5", .38, .9, .9), ("A4", .38, .9, .6)):
    base_place(x, piano(nm, hold, 0.4, v), t0)
  base_place(x, violins(("D5", "F#5"), 1.5, attack=0.08, release=1.0, vel=0.7), 0.0, 0.7)


def refuse(x):
  for t0 in (0.0, 0.18):
    base_place(x, piano("D5", 0.1, 0.12, .9) + piano("D#5", 0.1, 0.12, .8), t0)


def pre_alert(x):
  harp_sweep(x, ("D5", "F#5", "A5", "D6", "F#6"), 0.0, 0.03, 0.5)
  base_place(x, piano("A6", 0.5, 0.3, 0.9), 0.16)


SOUNDS = {
  "engage.wav": lambda: one_shot(1.95, engage),
  "disengage.wav": lambda: one_shot(1.6, disengage),
  "refuse.wav": lambda: one_shot(0.8, refuse, verb=0.15, fade=0.2),
  "pre_alert.wav": lambda: one_shot(1.1, pre_alert),
  "warning.wav": lambda: loop(0.9, lambda x: (base_place(x, piano("A5", .45, .2), 0.0),
                                              base_place(x, piano("D6", .45, .2), 0.15))),
  "dm_warning.wav": lambda: loop(0.7, lambda x: [base_place(x, piano(nm, .3, .15), i * 0.09)
                                                for i, nm in enumerate(("D6", "E6", "F#6"))]),
  "critical.wav": lambda: loop(0.5, lambda x: [base_place(x, piano(nm, .09, .05) + piano(nm[:-1] + str(int(nm[-1]) + 1), .09, .05),
                                                          i * 0.125) for i, nm in enumerate(("D6", "A5", "D6", "A5"))],
                               tail=0.6, verb=0.06, drive=2.4,
                               bed=lambda n: tremolo_loop(("D5", "A5"), n, 0.9)),
  "dm_critical.wav": lambda: loop(0.4, lambda x: [base_place(x, piano(nm, .07, .04) + piano(nm[:-1] + str(int(nm[-1]) + 1), .07, .04),
                                                             i * 0.1) for i, nm in enumerate(("A6", "D6", "A6", "D6"))],
                                  tail=0.5, verb=0.05, drive=2.8,
                                  bed=lambda n: tremolo_loop(("A5", "D6"), n, 1.0)),
  "dm_critical_max.wav": lambda: loop(0.32, lambda x: [base_place(x, piano(nm, .055, .03) + piano(nm[:-1] + str(int(nm[-1]) + 1), .055, .03),
                                                                  i * 0.08) for i, nm in enumerate(("D7", "F#6", "D7", "F#6"))],
                                      tail=0.4, verb=0.04, drive=3.2, peak=0.95,
                                      bed=lambda n: tremolo_loop(("D6", "F#6"), n, 1.1)),
}


def main(out, cache=None):
  global CACHE
  if cache:
    CACHE = Path(cache)
  out = Path(out)
  folder = out / "Comma-Piano" / "sounds"
  folder.mkdir(parents=True, exist_ok=True)
  preview = []
  for fname, make in SOUNDS.items():
    x = make()
    base.write(folder / fname, x)
    preview += [np.tile(x, base.LOOPED.get(fname, 1)), np.zeros(int(0.7 * SR))]
    print(f"{fname:22s} {len(x) / SR:.2f} s  rms {np.sqrt(np.mean(x * x)):.2f}")
  base.write(out / "Comma-Piano.preview.wav", np.concatenate(preview) * 0.95)


if __name__ == "__main__":
  main(sys.argv[1] if len(sys.argv) > 1 else ".", sys.argv[2] if len(sys.argv) > 2 else None)
