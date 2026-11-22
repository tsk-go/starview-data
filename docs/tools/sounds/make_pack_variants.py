#!/usr/bin/env python3
"""More StarView sound packs: the same alerts, key and echo as the "StarView" pack (make_starview_pack.py), each
played on another instrument.

  StarView-Marimba   wooden bars, short and round
  StarView-Glass     glass bells, long and airy, a wider echo
  StarView-Kalimba   thumb piano, plucked and dry
  StarView-Harp      plucked strings, engage / disengage as a quick glide up / down
  StarView-Ambient   the voice alerts' background chord up front, soft bells over it, a long echo

Every pack keeps the "take control" warnings loud, fast and repeating (soft saturation, seamless loops).

  python3 make_pack_variants.py OUT_DIR     # writes OUT_DIR/<pack>/sounds/*.wav and OUT_DIR/<pack>.preview.wav
"""
import sys
from pathlib import Path

import numpy as np

sys.path.insert(0, str(Path(__file__).resolve().parent))
import make_starview_pack as base  # noqa: E402
from make_starview_pack import SR, t_of, A5, B5, CS6, E6, A6, CS7, E7, A7, E5, DS5  # noqa: E402

FS6 = 1479.98
NOTES_UP = (A5, CS6, E6, A6)


# ---------------------------------------------------------------- instruments: out, f, start, amp, decay, bright
def _span(out, start):
  s0 = int(start * SR)
  return s0, t_of(max(0, len(out) - s0))


def marimba(out, f, start, amp, decay, bright=1.0):
  s0, t = _span(out, start)
  if not len(t):
    return
  att = 1 - np.exp(-t / 0.0015)
  x = np.sin(2 * np.pi * f * t) * np.exp(-decay * 1.6 * t)
  x += np.sin(2 * np.pi * f * 3.93 * t) * 0.3 * bright * np.exp(-decay * 12 * t)
  click = np.random.default_rng(int(f)).standard_normal(len(t)) * np.exp(-t / 0.0025) * 0.04
  out[s0:] += (x * att + click) * amp


def glass(out, f, start, amp, decay, bright=1.0):
  s0, t = _span(out, start)
  if not len(t):
    return
  vib = 1 + np.sin(2 * np.pi * 5.5 * t) * 0.0025
  att = 1 - np.exp(-t / 0.012)
  for i, (ratio, a, k) in enumerate(((1.0, 1.0, 1.0), (2.32, .30, 1.5), (4.25, .15, 2.2), (6.63, .06, 3.0))):
    out[s0:] += np.sin(2 * np.pi * f * ratio * t * vib) * a * (bright if i else 1) * np.exp(-decay * 0.6 * k * t) * att * amp


def kalimba(out, f, start, amp, decay, bright=1.0):
  s0, t = _span(out, start)
  if not len(t):
    return
  bend = 1 + 0.004 * np.exp(-t / 0.02)  # the tine's pitch settles just after the pluck
  att = 1 - np.exp(-t / 0.001)
  x = np.sin(2 * np.pi * f * t * bend) * np.exp(-decay * 1.1 * t)
  x += np.sin(2 * np.pi * f * 5.95 * t) * 0.22 * bright * np.exp(-decay * 8 * t)
  x += np.sin(2 * np.pi * f * 2.0 * t) * 0.08 * np.exp(-decay * 1.5 * t)
  out[s0:] += x * att * amp


def harp(out, f, start, amp, decay, bright=1.0):
  s0, t = _span(out, start)
  if not len(t):
    return
  att = 1 - np.exp(-t / 0.002)
  x = np.zeros(len(t))
  for n, a in enumerate((1.0, .5, .32, .18, .1, .06), start=1):
    x += np.sin(2 * np.pi * f * n * t) * a * (bright if n > 1 else 1) * np.exp(-decay * (0.8 + 0.35 * n) * t)
  out[s0:] += x * att * amp


def soft_bell(out, f, start, amp, decay, bright=1.0):
  """The voice alerts' bell with a slow 25 ms attack, for the ambient pack."""
  tmp = np.zeros(len(out))
  base.bell(tmp, f, start, amp, decay, bright)
  s0, t = _span(out, start)
  tmp[s0:] *= 1 - np.exp(-t / 0.025)
  out += tmp


def hall(x):
  """The voice alerts' echo plus three later ones, for glass and ambient."""
  y = base.room(x)
  for ms, g in ((260, .10), (330, .07), (410, .05)):
    d = int(SR * ms / 1000)
    y[d:] += x[:-d] * g
  return y


# ---------------------------------------------------------------- one pack from an instrument
def make_pack(play, echo=base.room, pad_gain=0.06, engage_notes=None, disengage_notes=None, spacing=0.08,
              engage_len=1.2, disengage_len=1.1, pad_under=("engage",), decay_scale=1.0):
  d = decay_scale

  def one_shot(length, notes, pad=False, fade=0.25, extra=None):
    x = np.zeros(int(length * SR))
    if pad:
      x += base.pad(len(x), 0.12 if pad_gain < 0.1 else 0.3, 0.6 if pad_gain < 0.1 else 0.9) * pad_gain
    for f, s, a, dec in notes:
      play(x, f, s, a, dec * d)
    if extra:
      extra(x)
    return base.finish(echo(x), fade_out=fade)

  def loop(period, strikes, tail=1.5, drive=0.0, peak=0.89):
    n = int(period * SR)
    x = np.zeros(n + int(tail * SR))
    for f, start, amp, dec, bright in strikes:
      play(x, f, start, amp, dec * d, bright)
    x = echo(x)
    y = x[:n].copy()
    rest = x[n:]
    while len(rest):
      k = min(n, len(rest))
      y[:k] += rest[:k]
      rest = rest[k:]
    y -= y.mean()
    return base.finish(y, peak=peak, fade_out=0, drive=drive, looped=True)

  up = engage_notes or [(f, i * spacing, .5 - .03 * i, 2.2) for i, f in enumerate(NOTES_UP)]
  down = disengage_notes or [(E6, 0.0, .45, 2.4), (A5, .13, .45, 2.4), (E5, .26, .40, 2.0)]

  def pre_extra(x):
    for f, s, a in ((A6, .14, 1.0), (CS7, .28, .8), (E7, .42, .6)):
      play(x, f, s, a * 0.14, 3.0 * d)

  return {
    "engage.wav": lambda: one_shot(engage_len, up, pad="engage" in pad_under),
    "disengage.wav": lambda: one_shot(disengage_len, down, pad="disengage" in pad_under),
    "refuse.wav": lambda: one_shot(0.8, [(E5, 0.0, .55, 3.5), (DS5, .17, .55, 3.5)], fade=0.15),
    "pre_alert.wav": lambda: one_shot(1.0, [(A6, 0.0, .30, 3.0)], pad="pre_alert" in pad_under, fade=0.2,
                                      extra=pre_extra),
    "warning.wav": lambda: loop(0.9, [(E6, 0.0, .45, 2.6, 1.0), (A6, .09, .38, 2.4, 1.0)]),
    "dm_warning.wav": lambda: loop(0.75, [(A6, 0.0, .42, 3.0, 1.0), (CS7, .07, .40, 3.0, 1.0), (E7, .14, .38, 3.0, 1.0)]),
    "critical.wav": lambda: loop(0.5, [(E7, 0.0, .6, 6.0, 1.3), (A6, .125, .55, 6.0, 1.3), (E7, .25, .6, 6.0, 1.3),
                                       (A6, .375, .55, 6.0, 1.3)], tail=1.0, drive=2.2),
    "dm_critical.wav": lambda: loop(0.4, [(f, s, a, 7.0, 1.4) for s, acc in ((0.0, 1.0), (.1, .8), (.2, 1.0), (.3, .8))
                                          for f, a in ((A6, .5 * acc), (CS7, .35 * acc), (E7, .45 * acc))],
                                    tail=0.8, drive=2.6),
    "dm_critical_max.wav": lambda: loop(0.32, [(f, s, a, 8.0, 1.5) for s in (0.0, .08, .16, .24)
                                              for f, a in ((A6, .5), (E7, .45), (A7, .3))], tail=0.8, drive=3.2,
                                        peak=0.95),
  }


GLIDE_UP = [(f, i * 0.045, .42, 2.0) for i, f in enumerate((A5, B5, CS6, E6, FS6, A6))]
GLIDE_DOWN = [(f, i * 0.05, .42, 2.0) for i, f in enumerate((A6, FS6, E6, CS6, B5, A5))]

PACKS = {
  "StarView-Marimba": dict(play=marimba, spacing=0.07,
                           disengage_notes=[(E6, 0.0, .5, 2.4), (CS6, .1, .5, 2.4), (A5, .2, .5, 2.2)]),
  "StarView-Glass": dict(play=glass, echo=hall, spacing=0.1, engage_len=1.6, disengage_len=1.5, pad_gain=0.05),
  "StarView-Kalimba": dict(play=kalimba, spacing=0.07,
                           engage_notes=[(f, i * 0.07, .5, 2.2) for i, f in enumerate((A5, E6, A6, CS7))]),
  "StarView-Harp": dict(play=harp, engage_notes=GLIDE_UP, disengage_notes=GLIDE_DOWN, engage_len=1.3,
                        disengage_len=1.3),
  "StarView-Ambient": dict(play=soft_bell, echo=hall, pad_gain=0.2, spacing=0.14, engage_len=1.9, disengage_len=1.8,
                           pad_under=("engage", "disengage", "pre_alert")),
}


def main(out):
  out = Path(out)
  for name, conf in PACKS.items():
    sounds = make_pack(**conf)
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
