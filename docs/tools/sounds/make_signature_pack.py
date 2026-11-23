#!/usr/bin/env python3
"""Comma-Signature: the comma's own sound - a four-note signature every alert is made from (owner, Nov 22: "Come up
with something amazing!!!").

The signature: D5 - A5 - E6 - F#6 (up a fifth, up a fifth, up a step: open, rising, landing on the bright ninth),
played by an FM brass section over strings, opened by a harp sweep, with a shimmer above and a smooth hall
(Freeverb). Nothing like the tablet: no sine beeps, bells, marimba or chirps, no A-major bells with discrete echoes.

  engage          the signature in full: harp sweep, brass D-A-E, the held F#6 swelling over strings, shimmer
  disengage       the signature turned around, falling, the strings closing down
  refuse          muted brass on a tritone, twice ("denied")
  pre_alert       a harp sweep up to the signature's last note, touched by the brass
  prompt          two notes of the signature (A5, E6) on an electric piano with its tremolo - repeats while it lasts
  distracted      three rising notes of it, quicker, the same piano
  take control    the brass playing the signature's first two notes as a driving figure, faster at each step

Comma-Signature-Night: the same, warmer and softer for the one-shots (less brass edge, more strings, the top rolled
off above 3.2 kHz); the prompts and warnings are the same as by day. Mono, 16-bit, 48 kHz StarPilot pack files; repeating alerts are seamless loops.

  python3 make_signature_pack.py OUT_DIR
"""
import sys
from pathlib import Path

import numpy as np
from scipy.signal import lfilter

sys.path.insert(0, str(Path(__file__).resolve().parent))
import make_starview_pack as base  # noqa: E402
from make_starview_pack import SR, t_of  # noqa: E402
from make_device_packs_v2 import hz, osc, curve, place, secs  # noqa: E402

SIGNATURE = ("D5", "A5", "E6", "F#6")
BEAT = (0.0, 0.10, 0.20, 0.32)


# ---------------------------------------------------------------- instruments
def brass(dur, note, bright=1.0, amp=1.0, release=0.12, scoop=25.0, vib=True, seed=0):
  """FM brass: carrier and modulator 1:1, the brightness (index) following the loudness, a pitch scoop into the note,
  vibrato fading in after a quarter second; two players 5 cents apart."""
  n = secs(dur + release)
  t = t_of(n)
  f0 = hz(note) if isinstance(note, str) else note
  rng = np.random.default_rng(seed)
  att = 0.022
  env = np.where(t < att, (t / att) ** 1.3, 0.78 + 0.22 * np.exp(-(t - att) / 0.08))
  env = np.where(t > dur, env * np.exp(-(t - dur) / (release / 3)), env)
  out = np.zeros(n)
  for cents in (-2.5, 2.5):
    f = f0 * 2 ** ((cents - scoop * np.exp(-t / 0.035)) / 1200)
    if vib:
      f = f * (1 + 0.004 * np.sin(2 * np.pi * 5.4 * t + rng.random() * 6) * np.clip((t - 0.25) / 0.3, 0, 1))
    ph = 2 * np.pi * np.cumsum(f) / SR + rng.random() * 6
    index = (0.6 + 3.2 * bright * env ** 1.6)
    out += np.sin(ph + index * np.sin(ph))
  return out * env * amp * 0.5


def strings(dur, notes, cut0=900, cut1=3200, attack=0.16, amp=1.0, seed=40):
  n = secs(dur)
  cut = curve(n, [(0, cut0), (min(dur, 0.5), cut1), (dur, cut1 * 0.8)])
  x = sum(osc(n, hz(nm) * (1 + 0.0025 * np.sin(2 * np.pi * 5.1 * t_of(n) + i)), "saw", cut, 0.3, 7, 16, seed=seed + i)
          for i, nm in enumerate(notes))
  t = t_of(n)
  e = np.minimum(1, t / attack) ** 2 * np.minimum(1, (dur - t) / 0.25)
  return x * e * amp / len(notes) ** 0.5


def harp_note(f, dur, amp=1.0):
  n = secs(dur)
  t = t_of(n)
  x = np.zeros(n)
  for k, a in enumerate((1.0, .55, .35, .2, .12, .07), start=1):
    if k * f < 9000:
      x += np.sin(2 * np.pi * f * k * t) * a * np.exp(-t * (1.4 + 0.9 * k))
  return x * (1 - np.exp(-t / 0.0015)) * amp


def harp_sweep(x, notes, start, step, amp):
  for i, nm in enumerate(notes):
    place(x, harp_note(hz(nm), 1.2), start + i * step, amp * (0.75 + 0.25 * i / max(1, len(notes) - 1)))


def epiano(dur, note, amp=1.0):
  """Electric piano (FM 1:1, the tine's brightness dying fast) with its suitcase tremolo."""
  n = secs(dur)
  t = t_of(n)
  f = hz(note)
  ph = 2 * np.pi * f * t
  index = 2.4 * np.exp(-t / 0.12) + 0.35
  x = np.sin(ph + index * np.sin(ph)) * np.exp(-t / 0.9)
  x += np.sin(2 * np.pi * f * 2 * t) * 0.12 * np.exp(-t / 0.25)
  x *= (1 - np.exp(-t / 0.002)) * (1 + 0.12 * np.sin(2 * np.pi * 4.8 * t))
  return x * amp


def shimmer(dur, notes, amp=1.0):
  n = secs(dur)
  t = t_of(n)
  x = sum(np.sin(2 * np.pi * hz(nm) * m * t + i) * (0.6 if m == 2 else 0.3) for i, nm in enumerate(notes) for m in (2, 4)
          if hz(nm) * m < 9000)
  e = np.minimum(1, t / 0.3) ** 2 * np.minimum(1, (dur - t) / 0.4) * (1 + 0.25 * np.sin(2 * np.pi * 6.0 * t))
  return x * e * amp / len(notes)


def hall(x, mix=0.22, room=0.84, damp=0.25):
  """Freeverb, mono: eight damped combs and four all-passes - a smooth hall."""
  scale = SR / 44100
  wet = np.zeros(len(x))
  for d in (1116, 1188, 1277, 1356, 1422, 1491, 1557, 1617):
    D = int(d * scale)
    a = np.zeros(D + 1)
    a[0], a[1], a[D] = 1, -damp, -room * (1 - damp)
    wet += lfilter([1, -damp], a, x)
  for d in (556, 441, 341, 225):
    D = int(d * scale)
    b = np.zeros(D + 1)
    a = np.zeros(D + 1)
    b[0], b[D] = -0.5, 1
    a[0], a[D] = 1, -0.5
    wet = lfilter(b, a, wet)
  return x + wet * mix / 8 * 3


def master(x, glue=1.25):
  """A little glue: soft saturation so the layers sit together, before the usual peak level."""
  return np.tanh(x / np.max(np.abs(x)) * glue) / np.tanh(glue)


# ---------------------------------------------------------------- render
def soften(x, cutoff):
  """A gentle low-pass (the night pack's one-shots): rolls the top off above the cutoff, zero-padded so nothing wraps."""
  n = len(x)
  X = np.fft.rfft(np.concatenate([x, np.zeros(n)]))
  f = np.fft.rfftfreq(2 * n, 1 / SR)
  return np.fft.irfft(X / np.sqrt(1 + (f / cutoff) ** 4), 2 * n)[:n]


def one_shot(length, build, verb=0.22, fade=0.3, soft=None):
  x = np.zeros(int(length * SR))
  build(x)
  x = master(hall(x, verb))
  if soft:
    x = soften(x, soft)
  return base.finish(x, fade_out=fade)


def loop(period, build, tail=0.8, verb=0.12, drive=0.0, peak=0.89):
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
  y -= y.mean()
  return base.finish(y, peak=peak, fade_out=0, drive=drive, looped=True)


def pack(night=False):
  b = 0.55 if night else 1.0       # brass edge
  s = 1.4 if night else 1.0        # strings
  soft = 3200 if night else None   # the top rolled off

  def engage(x):
    harp_sweep(x, ("D5", "E5", "F#5", "A5", "B5", "D6"), 0.0, 0.018, 0.22)
    for i, (nm, t0) in enumerate(zip(SIGNATURE, BEAT)):
      held = i == len(SIGNATURE) - 1
      place(x, brass(0.75 if held else 0.09, nm, b * (1.0 if held else 0.85), seed=i), t0, 0.85 if held else 0.7)
    place(x, strings(1.3, ("D5", "F#5", "A5", "E6")), 0.18, 0.55 * s)
    place(x, shimmer(1.1, ("D6", "F#6", "A6")), 0.34, 0.16)

  def disengage(x):
    for i, (nm, t0) in enumerate(zip(reversed(SIGNATURE), BEAT)):
      last = i == len(SIGNATURE) - 1
      place(x, brass(0.5 if last else 0.09, nm, b * 0.7, scoop=0, vib=False, seed=10 + i), t0, 0.7)
    place(x, strings(1.15, ("A4", "D5", "F#5"), 2600, 700, 0.05), 0.0, 0.5 * s)

  def refuse(x):
    for t0 in (0.0, 0.17):
      place(x, brass(0.09, "D5", 0.35, scoop=0, vib=False, seed=20), t0, 0.8)
      place(x, brass(0.09, "G#5", 0.35, scoop=0, vib=False, seed=21), t0, 0.6)

  def pre_alert(x):
    harp_sweep(x, ("A5", "B5", "D6", "E6", "F#6"), 0.0, 0.03, 0.28)
    place(x, brass(0.22, "F#6", b * 0.6, seed=30), 0.13, 0.5)

  return {
    "engage.wav": lambda: one_shot(1.7, engage, soft=soft),
    "disengage.wav": lambda: one_shot(1.5, disengage, soft=soft),
    "refuse.wav": lambda: one_shot(0.7, refuse, verb=0.12, fade=0.15, soft=soft),
    "pre_alert.wav": lambda: one_shot(1.0, pre_alert, soft=soft),
    "warning.wav": lambda: loop(0.9, lambda x: (place(x, epiano(0.9, "A5"), 0.0, 0.8), place(x, epiano(0.9, "E6"), 0.13, 0.8))),
    "dm_warning.wav": lambda: loop(0.7, lambda x: [place(x, epiano(0.7, nm), i * 0.085, 0.8)
                                                  for i, nm in enumerate(("D6", "E6", "F#6"))]),
    # take control: the signature's first two notes as a driving figure, brass bright and pushed
    "critical.wav": lambda: loop(0.5, lambda x: [place(x, brass(0.08, nm, 1.3, release=0.04, scoop=0, vib=False, seed=40 + i),
                                                       i * 0.125, 1.0)
                                                 for i, nm in enumerate(("D6", "A6", "D6", "A6"))],
                                 tail=0.4, verb=0.06, drive=2.4),
    "dm_critical.wav": lambda: loop(0.4, lambda x: [place(x, brass(0.065, nm, 1.5, release=0.03, scoop=0, vib=False, seed=50 + i),
                                                          i * 0.1, 1.0)
                                                    for i, nm in enumerate(("A6", "E7", "A6", "E7"))],
                                    tail=0.3, verb=0.05, drive=2.8),
    "dm_critical_max.wav": lambda: loop(0.32, lambda x: [place(x, brass(0.05, nm, 1.7, release=0.025, scoop=0, vib=False,
                                                                        seed=60 + i), i * 0.08, 1.0)
                                                         for i, nm in enumerate(("F#6", "D7", "F#6", "D7"))],
                                        tail=0.3, verb=0.04, drive=3.2, peak=0.95),
  }


PACKS = {"Comma-Signature": pack(), "Comma-Signature-Night": pack(night=True)}


def main(out):
  out = Path(out)
  for name, sounds in PACKS.items():
    folder = out / name / "sounds"
    folder.mkdir(parents=True, exist_ok=True)
    preview = []
    for fname, make in sounds.items():
      x = make()
      base.write(folder / fname, x)
      preview += [np.tile(x, base.LOOPED.get(fname, 1)), np.zeros(int(0.7 * SR))]
    base.write(out / f"{name}.preview.wav", np.concatenate(preview) * 0.95)
    print(name, "ok")


if __name__ == "__main__":
  main(sys.argv[1] if len(sys.argv) > 1 else ".")
