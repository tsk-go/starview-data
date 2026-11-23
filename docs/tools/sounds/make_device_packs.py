#!/usr/bin/env python3
"""Sound packs for the comma's driving alerts that can't be taken for the tablet's sounds.

The tablet (StarView) uses sine beeps, bells, marimba, chirps (AlertSounds: the speed warning) and A-major bells over
a warm chord (the voice alerts). These packs use none of that - synth voices, no echo, other keys - so an alert from
the comma is known by ear:

  Comma-Synth   two detuned saw waves through a filter that opens on each note (D major), engage / disengage as
                stabs opening up / closing down
  Comma-Pulse   square waves that glide between notes (F# major): engage "bwee-oop" up, disengage down, the
                "take control" warnings a two-tone siren
  Comma-Retro   8-bit chip sounds (G major): fast arpeggios, a classic "error" for refuse

Same files and rules as every StarPilot sound pack (mono, 16-bit, 48 kHz); the repeating alerts are seamless loops;
the "take control" warnings stay loud and fast.

  python3 make_device_packs.py OUT_DIR     # writes OUT_DIR/<pack>/sounds/*.wav and OUT_DIR/<pack>.preview.wav
"""
import sys
from pathlib import Path

import numpy as np

sys.path.insert(0, str(Path(__file__).resolve().parent))
import make_starview_pack as base  # noqa: E402
from make_starview_pack import SR, t_of  # noqa: E402

NYQ_SAFE = 9000.0  # partials above this are left out (band-limited waves, nothing the comma's speaker plays anyway)


def note(name):
  names = {"C": -9, "C#": -8, "D": -7, "D#": -6, "E": -5, "F": -4, "F#": -3, "G": -2, "G#": -1, "A": 0, "A#": 1, "B": 2}
  return 440.0 * 2 ** ((names[name[:-1]] + 12 * (int(name[-1]) - 4)) / 12)


def env_adsr(n, attack, decay, sustain, release, hold):
  t = t_of(n)
  e = np.where(t < attack, t / max(attack, 1e-4),
               sustain + (1 - sustain) * np.exp(-(t - attack) / max(decay, 1e-4)))
  r0 = attack + hold
  e = np.where(t > r0, e * np.exp(-(t - r0) / max(release, 1e-4)), e)
  return e


# ---------------------------------------------------------------- voices: out, f (or a frequency path), start, dur, amp
def synth(out, f, start, dur, amp, bright=1.0):
  """Two saws 7 cents apart; each harmonic through a 4-pole low-pass whose cutoff opens fast and settles."""
  s0 = int(start * SR)
  n = min(len(out) - s0, int((dur + 0.35) * SR))
  if n <= 0:
    return
  t = t_of(n)
  cutoff = 700 + (3800 * bright) * np.exp(-t / 0.12) + 900 * bright
  e = env_adsr(n, 0.004, 0.09, 0.55, 0.08, dur)
  x = np.zeros(n)
  for det in (-0.002, 0.002):
    ff = f * (1 + det)
    k = 1
    while k * ff < NYQ_SAFE:
      g = 1 / np.sqrt(1 + (k * ff / cutoff) ** 8)
      x += np.sin(2 * np.pi * k * ff * t + det * 40 * k) * g / k
      k += 1
  out[s0:s0 + n] += x * e * amp * 0.5


def square_path(out, freqs, start, dur, amp, glide=0.03, duty_odd_only=True):
  """A square wave whose pitch glides from note to note (freqs: (time from start, frequency) pairs)."""
  s0 = int(start * SR)
  n = min(len(out) - s0, int((dur + 0.06) * SR))
  if n <= 0:
    return
  t = t_of(n)
  f = np.full(n, freqs[0][1])
  for (t0, f0), (t1, f1) in zip(freqs, freqs[1:]):
    f = np.where(t >= t1, f1 + (f0 - f1) * np.exp(-(t - t1) / glide), f)
  phase = 2 * np.pi * np.cumsum(f) / SR
  x = np.zeros(n)
  kmax = int(NYQ_SAFE / max(f.max(), 1))
  for k in range(1, kmax + 1, 2 if duty_odd_only else 1):
    x += np.sin(k * phase) / k
  e = env_adsr(n, 0.003, 0.25, 0.75, 0.02, dur)
  out[s0:s0 + n] += x * e * amp * 0.6


def chip(out, f, start, dur, amp, duty=0.25):
  """A band-limited pulse wave (8-bit style), hard gate."""
  s0 = int(start * SR)
  n = min(len(out) - s0, int(dur * SR))
  if n <= 0:
    return
  t = t_of(n)
  x = np.zeros(n)
  k = 1
  while k * f < NYQ_SAFE:
    x += np.sin(np.pi * k * duty) / k * np.cos(2 * np.pi * k * f * t)
    k += 1
  gate = np.minimum(1, t / 0.002) * np.minimum(1, (dur - t) / 0.003)
  out[s0:s0 + n] += x * gate * amp * 0.9


def noise_tick(out, start, amp):
  s0 = int(start * SR)
  n = min(len(out) - s0, int(0.012 * SR))
  if n <= 0:
    return
  out[s0:s0 + n] += np.random.default_rng(s0).standard_normal(n) * np.exp(-t_of(n) / 0.003) * amp


# ---------------------------------------------------------------- rendering
def one_shot(length, events, fade=0.08):
  x = np.zeros(int(length * SR))
  for ev in events:
    ev(x)
  return base.finish(x, fade_out=fade)


def loop(period, events, tail=0.5, drive=0.0, peak=0.89):
  n = int(period * SR)
  x = np.zeros(n + int(tail * SR))
  for ev in events:
    ev(x)
  y = x[:n].copy()
  rest = x[n:]
  while len(rest):
    k = min(n, len(rest))
    y[:k] += rest[:k]
    rest = rest[k:]
  y -= y.mean()
  return base.finish(y, peak=peak, fade_out=0, drive=drive, looped=True)


def S(f, s, d, a, b=1.0):
  return lambda x: synth(x, note(f) if isinstance(f, str) else f, s, d, a, b)


def chord(names, s, d, a, b=1.0):
  return [S(nm, s, d, a / len(names) ** 0.5, b) for nm in names]


def P(path, s, d, a, glide=0.03):
  return lambda x: square_path(x, [(tt, note(nm)) for tt, nm in path], s, d, a, glide)


def C(nm, s, d, a, duty=0.25):
  return lambda x: chip(x, note(nm), s, d, a, duty)


def T(s, a):
  return lambda x: noise_tick(x, s, a)


PACKS = {
  "Comma-Synth": {  # D major, filtered saws
    "engage.wav": lambda: one_shot(0.8, chord(("D5", "A5"), 0.0, 0.10, .9, .7) + chord(("F#5", "D6"), 0.14, 0.28, 1.0, 1.0)),
    "disengage.wav": lambda: one_shot(0.9, chord(("F#5", "D6"), 0.0, 0.10, 1.0, 1.0) + chord(("D5", "A5"), 0.14, 0.10, .9, .7)
                                      + chord(("A4", "D5"), 0.28, 0.25, .9, .4)),
    "refuse.wav": lambda: one_shot(0.6, chord(("D4", "D#4", "A4"), 0.0, 0.09, 1.0, .6) + chord(("D4", "D#4", "A4"), 0.17, 0.12, 1.0, .5)),
    "pre_alert.wav": lambda: one_shot(0.6, chord(("A5", "D6"), 0.0, 0.12, 1.0, .9)),
    "warning.wav": lambda: loop(0.9, [S("D6", 0.0, .08, .9), S("A5", .14, .10, .9)]),
    "dm_warning.wav": lambda: loop(0.7, [S("D6", 0.0, .06, .85), S("F#6", .09, .06, .85), S("A6", .18, .08, .85)]),
    "critical.wav": lambda: loop(0.5, [c for s in (0.0, .125, .25, .375) for c in chord(("D6", "A6"), s, .07, 1.0, 1.3)],
                                 drive=3.2),
    "dm_critical.wav": lambda: loop(0.4, [S("A6" if i % 2 else "D7", i * .1, .06, 1.0, 1.4) for i in range(4)]
                                    + [S("D6", i * .1, .06, .6, 1.4) for i in range(4)], drive=2.8),
    "dm_critical_max.wav": lambda: loop(0.32, [S("D7" if i % 2 else "A6", i * .08, .05, 1.0, 1.5) for i in range(4)]
                                        + [S("F#6", i * .08, .05, .7, 1.5) for i in range(4)], drive=3.2, peak=0.95),
  },
  "Comma-Pulse": {  # F# major, gliding squares
    "engage.wav": lambda: one_shot(0.6, [P([(0, "F#5"), (.07, "C#6"), (.16, "F#6")], 0.0, .32, .8, .02)]),
    "disengage.wav": lambda: one_shot(0.6, [P([(0, "F#6"), (.08, "C#6"), (.18, "F#5")], 0.0, .34, .8, .025)]),
    "refuse.wav": lambda: one_shot(0.45, [P([(0, "C#5")], 0.0, .09, .8), P([(0, "C5")], .14, .14, .8)]),
    "pre_alert.wav": lambda: one_shot(0.35, [P([(0, "C#6"), (.03, "F#6")], 0.0, .10, .7, .012)]),
    "warning.wav": lambda: loop(0.9, [P([(0, "F#6")], 0.0, .07, .7), P([(0, "F#6")], .13, .07, .7)]),
    "dm_warning.wav": lambda: loop(0.7, [P([(0, "C#6"), (.04, "F#6")], 0.0, .08, .7, .01), P([(0, "C#6"), (.04, "A#6")], .14, .1, .7, .01)]),
    "critical.wav": lambda: loop(0.5, [P([(0, "C#7"), (.125, "F#6"), (.25, "C#7"), (.375, "F#6")], 0.0, .5, .9, .006)],
                                 tail=0.05, drive=1.8),
    "dm_critical.wav": lambda: loop(0.4, [P([(0, "F#7"), (.1, "C#7"), (.2, "F#7"), (.3, "C#7")], 0.0, .4, .95, .004)],
                                    tail=0.05, drive=2.2),
    "dm_critical_max.wav": lambda: loop(0.32, [P([(0, "F#7"), (.04, "A#6"), (.08, "F#7"), (.12, "A#6"), (.16, "F#7"),
                                                  (.2, "A#6"), (.24, "F#7"), (.28, "A#6")], 0.0, .32, 1.0, .003)],
                                        tail=0.05, drive=2.6, peak=0.95),
  },
  "Comma-Retro": {  # G major, 8-bit
    "engage.wav": lambda: one_shot(0.45, [C(nm, i * .045, .045, .7) for i, nm in enumerate(("G5", "B5", "D6"))]
                                   + [C("G6", .135, .2, .7)]),
    "disengage.wav": lambda: one_shot(0.45, [C(nm, i * .045, .045, .7) for i, nm in enumerate(("G6", "D6", "B5"))]
                                      + [C("G5", .135, .2, .7)]),
    "refuse.wav": lambda: one_shot(0.4, [C("D4", 0.0, .1, .8, .5), C("C#4", .12, .22, .8, .5), T(0.0, .3), T(.12, .3)]),
    "pre_alert.wav": lambda: one_shot(0.3, [C("D6", 0.0, .04, .7), C("G6", .045, .12, .7)]),
    "warning.wav": lambda: loop(0.9, [C("B6", 0.0, .06, .7), C("B6", .1, .06, .7)]),
    "dm_warning.wav": lambda: loop(0.7, [C(nm, i * .05, .05, .7) for i, nm in enumerate(("G6", "B6", "D7"))]),
    "critical.wav": lambda: loop(0.5, [C(nm, i * .0625, .06, .9, .5) for i, nm in
                                       enumerate(("G6", "D7", "G6", "D7", "G6", "D7", "G6", "D7"))], tail=0.05, drive=1.6),
    "dm_critical.wav": lambda: loop(0.4, [C(nm, i * .05, .048, .95, .5) for i, nm in
                                          enumerate(("D7", "G7", "D7", "G7", "D7", "G7", "D7", "G7"))], tail=0.05, drive=2.0),
    "dm_critical_max.wav": lambda: loop(0.32, [C(nm, i * .04, .038, 1.0, .5) for i, nm in
                                              enumerate(("G7", "D7", "G7", "D7", "G7", "D7", "G7", "D7"))],
                                        tail=0.05, drive=2.4, peak=0.95),
  },
}


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
