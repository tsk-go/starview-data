#!/usr/bin/env python3
"""Comma sound packs, second round (owner, Nov 22: "Come up with something better"): designed sounds, not single
tones - each alert is layered (a noise sweep, a chord through a moving filter, a short attack) - and still nothing
like the tablet's (no sine beeps, bells, marimba, chirps, no A-major bells with discrete echoes).

  Comma-Starship     sci-fi cockpit, D: engage = a rising whoosh that blooms into a bright chord ("systems
                     online"), disengage = the chord powering down while a whoosh falls; prompts are holographic
                     blips (FM); "take control" = a red-alert whoop that gets faster
  Comma-GrandTourer  premium car, F: warm analog plucks and a soft major-ninth swell for engage, the same falling
                     for disengage; warnings are resonant pulsing stabs
  Comma-Neon         synthwave, E minor: wide detuned-saw arpeggios with a riser; warnings are gated chords that
                     pump faster and faster

Mono, 16-bit, 48 kHz StarPilot pack files; repeating alerts are seamless loops; "take control" warnings loud.

  python3 make_device_packs_v2.py OUT_DIR
"""
import sys
from pathlib import Path

import numpy as np
from scipy.signal import istft, lfilter, stft

sys.path.insert(0, str(Path(__file__).resolve().parent))
import make_starview_pack as base  # noqa: E402
from make_starview_pack import SR, t_of  # noqa: E402

TOP = 9000.0


def hz(name):
  names = {"C": -9, "C#": -8, "Db": -8, "D": -7, "D#": -6, "Eb": -6, "E": -5, "F": -4, "F#": -3, "Gb": -3, "G": -2,
           "G#": -1, "Ab": -1, "A": 0, "A#": 1, "Bb": 1, "B": 2}
  return 440.0 * 2 ** ((names[name[:-1]] + 12 * (int(name[-1]) - 4)) / 12)


def curve(n, points, kind="exp"):
  """A control curve through (time, value) points: exponential (for frequencies) or linear."""
  t = t_of(n)
  ts = [p[0] for p in points]
  vs = [p[1] for p in points]
  if kind == "exp":
    return np.exp(np.interp(t, ts, np.log(vs)))
  return np.interp(t, ts, vs)


def adsr(n, a, d, s, r, hold):
  t = t_of(n)
  e = np.where(t < a, (t / max(a, 1e-4)) ** 1.5, s + (1 - s) * np.exp(-(t - a) / max(d, 1e-4)))
  return np.where(t > a + hold, e * np.exp(-(t - a - hold) / max(r, 1e-4)), e)


# ---------------------------------------------------------------- sources
def osc(n, freq, shape="saw", cutoff=None, res=0.0, voices=1, spread=0.0, seed=1):
  """Band-limited oscillator, one or more detuned voices, each harmonic through a 4-pole low-pass with resonance.
  freq / cutoff: a number or a per-sample array."""
  freq = np.broadcast_to(np.asarray(freq, float), (n,))
  cutoff = np.broadcast_to(np.asarray(cutoff if cutoff is not None else 20000.0, float), (n,))
  rng = np.random.default_rng(seed)
  out = np.zeros(n)
  fmax = float(freq.max())
  for v in range(voices):
    cents = spread * ((v - (voices - 1) / 2) / max(1, (voices - 1) / 2)) if voices > 1 else 0.0
    det = 2 ** (cents / 1200)
    phase = 2 * np.pi * np.cumsum(freq * det) / SR + rng.random() * 2 * np.pi
    k = 1
    while k * fmax * det < TOP:
      if shape == "saw":
        a = 1 / k
      elif shape == "square":
        a = 1 / k if k % 2 else 0
      else:  # triangle
        a = (1 / k ** 2) * (1 if (k // 2) % 2 == 0 else -1) if k % 2 else 0
      if a:
        hf = k * freq * det
        g = 1 / np.sqrt(1 + (hf / cutoff) ** 8)
        if res:
          g = g * (1 + res * np.exp(-((hf - cutoff) / (0.18 * cutoff)) ** 2))
        out += np.sin(k * phase) * a * g
      k += 1
  return out / max(1, voices) ** 0.5


def fm(n, freq, ratio, index, seed=2):
  freq = np.broadcast_to(np.asarray(freq, float), (n,))
  index = np.broadcast_to(np.asarray(index, float), (n,))
  ph = 2 * np.pi * np.cumsum(freq) / SR
  return np.sin(ph + index * np.sin(ratio * ph + np.random.default_rng(seed).random()))


def swoosh(n, centers, width=0.35, seed=3):
  """Noise through a band-pass that moves (centers: (time, Hz) points), shaped in the frequency domain."""
  noise = np.random.default_rng(seed).standard_normal(n + 4096)
  f, tt, Z = stft(noise, SR, nperseg=1024, noverlap=768)
  c = np.exp(np.interp(tt, [p[0] for p in centers], np.log([p[1] for p in centers])))
  lf = np.log(np.maximum(f, 1.0))[:, None]
  mask = np.exp(-((lf - np.log(c)[None, :]) / width) ** 2)
  _, y = istft(Z * mask, SR, nperseg=1024, noverlap=768)
  y = y[:n]
  return y / (np.max(np.abs(y)) + 1e-9)


def plate(x, mix=0.14, decay=0.55):
  """A small smooth reverb (Schroeder: four combs, two all-passes) - a wash, unlike the tablet's discrete echoes."""
  y = np.zeros_like(x)
  for ms in (29.7, 37.1, 41.1, 43.7):
    d = int(SR * ms / 1000)
    g = 10 ** (-3 * ms / 1000 / decay)
    a = np.zeros(d + 1)
    a[0], a[d] = 1, -g
    y += lfilter([1], a, x)
  for ms, g in ((5.0, 0.7), (1.7, 0.7)):
    d = int(SR * ms / 1000)
    b = np.zeros(d + 1)
    a = np.zeros(d + 1)
    b[0], b[d] = -g, 1
    a[0], a[d] = 1, -g
    y = lfilter(b, a, y)
  return x + y * mix / 4


def place(out, x, start, gain=1.0):
  s0 = int(start * SR)
  k = min(len(x), len(out) - s0)
  if k > 0:
    out[s0:s0 + k] += x[:k] * gain


# ---------------------------------------------------------------- render helpers
def one_shot(length, build, verb=0.0, fade=0.1, peak=0.89):
  x = np.zeros(int(length * SR))
  build(x)
  if verb:
    x = plate(x, verb)
  return base.finish(x, peak=peak, fade_out=fade)


def loop(period, build, tail=0.4, verb=0.0, drive=0.0, peak=0.89):
  n = int(period * SR)
  x = np.zeros(n + int(tail * SR))
  build(x)
  if verb:
    x = plate(x, verb)
  y = x[:n].copy()
  rest = x[n:]
  while len(rest):
    k = min(n, len(rest))
    y[:k] += rest[:k]
    rest = rest[k:]
  y -= y.mean()
  return base.finish(y, peak=peak, fade_out=0, drive=drive, looped=True)


def secs(s):
  return int(s * SR)


# ================================================================ Comma-Starship (D)
def chord_bloom(dur, notes, cut_from, cut_peak, cut_end, drop=0.0, voices=5, spread=14, res=0.6, amp=1.0):
  n = secs(dur)
  t = t_of(n)
  cut = curve(n, [(0, cut_from), (dur * 0.18, cut_peak), (dur, cut_end)])
  bend = 2 ** (-drop / 12 * np.clip(t / dur, 0, 1) ** 1.6) if drop else 1.0
  x = sum(osc(n, hz(nm) * bend, "saw", cut, res, voices, spread, seed=i) for i, nm in enumerate(notes))
  return x * adsr(n, 0.012, dur * 0.35, 0.35, dur * 0.25, dur * 0.45) * amp / len(notes) ** 0.5


def holo_blip(dur, f0, f1, amp=1.0, ratio=2.0):
  n = secs(dur)
  f = curve(n, [(0, f0), (dur, f1)])
  idx = curve(n, [(0, 2.5), (dur, 0.3)], "lin")
  e = adsr(n, 0.003, dur * 0.3, 0.2, dur * 0.2, dur * 0.4)
  return fm(n, f, ratio, idx) * e * amp


def whoop(dur, f0, f1, amp=1.0, cut=3500):
  n = secs(dur)
  f = curve(n, [(0, f0), (dur * 0.85, f1), (dur, f1)])
  x = osc(n, f, "saw", cut, 0.8, 3, 10) + 0.5 * osc(n, f * 0.5, "square", cut * 0.7, 0.0, 1)
  return x * adsr(n, 0.01, dur, 1.0, 0.03, dur * 0.88) * amp


STARSHIP = {
  "engage.wav": lambda: one_shot(1.1, lambda x: (
    place(x, swoosh(secs(0.4), [(0, 700), (0.4, 6000)]) * curve(secs(0.4), [(0, 0.0), (0.3, 0.9), (0.4, 0.2)], "lin"), 0.0, 0.35),
    place(x, chord_bloom(0.8, ("D5", "F#5", "A5", "E6"), 500, 7000, 1800), 0.3, 1.0),
    place(x, holo_blip(0.12, 2349, 3136, ratio=3.0), 0.3, 0.25)), verb=0.18),
  "disengage.wav": lambda: one_shot(1.1, lambda x: (
    place(x, chord_bloom(0.85, ("E6", "A5", "F#5", "D5"), 5000, 5500, 400, drop=7.0), 0.0, 1.0),
    place(x, swoosh(secs(0.6), [(0, 6000), (0.6, 500)]) * curve(secs(0.6), [(0, 0.6), (0.6, 0.0)], "lin"), 0.05, 0.3)), verb=0.15),
  "refuse.wav": lambda: one_shot(0.55, lambda x: (
    place(x, fm(secs(0.14), hz("C#4"), 1.0, 3.2) * adsr(secs(0.14), 0.004, .05, .7, .02, .1), 0.0, 0.9),
    place(x, fm(secs(0.2), hz("C4"), 1.0, 3.2) * adsr(secs(0.2), 0.004, .06, .7, .04, .15), 0.17, 0.9))),
  "pre_alert.wav": lambda: one_shot(0.45, lambda x: (
    place(x, holo_blip(0.16, 1175, 1760), 0.0, 0.9), place(x, holo_blip(0.12, 2349, 2349, ratio=3.0), 0.09, 0.4)), verb=0.15),
  "warning.wav": lambda: loop(0.9, lambda x: (place(x, holo_blip(0.11, 1760, 1480), 0.0, 1.0),
                                              place(x, holo_blip(0.13, 1175, 1175), 0.15, 1.0)), verb=0.12),
  "dm_warning.wav": lambda: loop(0.7, lambda x: [place(x, holo_blip(0.09, f, f * 1.06), i * 0.09, 1.0)
                                                for i, f in enumerate((1175, 1480, 1760))], verb=0.1),
  "critical.wav": lambda: loop(0.5, lambda x: place(x, whoop(0.42, 620, 1350), 0.0, 1.0), tail=0.1, drive=2.4),
  "dm_critical.wav": lambda: loop(0.4, lambda x: (place(x, whoop(0.34, 700, 1700, cut=4500), 0.0, 1.0)),
                                  tail=0.1, drive=2.8),
  "dm_critical_max.wav": lambda: loop(0.3, lambda x: (place(x, whoop(0.26, 800, 2100, cut=5500), 0.0, 1.0)),
                                      tail=0.1, drive=3.2, peak=0.95),
}


# ================================================================ Comma-GrandTourer (F)
def gt_pluck(dur, notes, cut_peak=4200, amp=1.0, decay=0.18):
  n = secs(dur)
  cut = curve(n, [(0, cut_peak), (decay * 1.5, 900), (dur, 600)])
  x = sum(osc(n, hz(nm), "saw", cut, 0.5, 2, 8, seed=i) + 0.6 * osc(n, hz(nm) * 0.5, "square", cut * 0.6, 0.0, 1)
          for i, nm in enumerate(notes))
  return x * adsr(n, 0.003, decay, 0.15, dur * 0.3, dur * 0.3) * amp / len(notes) ** 0.5


def gt_swell(dur, notes, amp=1.0):
  n = secs(dur)
  cut = curve(n, [(0, 700), (dur * 0.5, 2600), (dur, 1400)])
  x = sum(osc(n, hz(nm), "saw", cut, 0.2, 3, 12, seed=10 + i) for i, nm in enumerate(notes))
  return x * adsr(n, dur * 0.35, dur * 0.3, 0.5, dur * 0.2, dur * 0.25) * amp / len(notes) ** 0.5


def gt_stab(dur, notes, cut0, amp=1.0):
  n = secs(dur)
  cut = curve(n, [(0, cut0), (dur, cut0 * 0.45)])
  x = sum(osc(n, hz(nm), "square", cut, 1.2, 2, 6, seed=20 + i) for i, nm in enumerate(notes))
  return x * adsr(n, 0.002, dur * 0.5, 0.3, 0.01, dur * 0.8) * amp / len(notes) ** 0.5


GRANDTOURER = {
  "engage.wav": lambda: one_shot(1.3, lambda x: (
    place(x, gt_pluck(0.5, ("F5", "C6")), 0.0, 0.8), place(x, gt_pluck(0.6, ("A5", "F6")), 0.13, 0.9),
    place(x, gt_swell(1.1, ("F5", "A5", "C6", "E6", "G6")), 0.12, 0.55)), verb=0.2, fade=0.3),
  "disengage.wav": lambda: one_shot(1.1, lambda x: (
    place(x, gt_pluck(0.45, ("A5", "F6")), 0.0, 0.9), place(x, gt_pluck(0.45, ("F5", "C6")), 0.13, 0.85),
    place(x, gt_pluck(0.6, ("C5", "F5"), cut_peak=2500), 0.26, 0.85)), verb=0.18, fade=0.3),
  "refuse.wav": lambda: one_shot(0.6, lambda x: (
    place(x, gt_pluck(0.2, ("F4", "Gb4"), 2000, decay=0.08), 0.0, 1.0),
    place(x, gt_pluck(0.3, ("F4", "Gb4"), 1800, decay=0.1), 0.16, 1.0))),
  "pre_alert.wav": lambda: one_shot(0.6, lambda x: place(x, gt_pluck(0.5, ("C6", "G6"), 5000), 0.0, 1.0), verb=0.15),
  "warning.wav": lambda: loop(0.9, lambda x: (place(x, gt_pluck(0.2, ("C6",), 5000, decay=0.07), 0.0, 1.0),
                                              place(x, gt_pluck(0.2, ("C6",), 5000, decay=0.07), 0.14, 1.0)), verb=0.12),
  "dm_warning.wav": lambda: loop(0.7, lambda x: [place(x, gt_pluck(0.18, (nm,), 5200, decay=0.06), i * 0.1, 1.0)
                                                for i, nm in enumerate(("C6", "F6", "A6"))], verb=0.1),
  "critical.wav": lambda: loop(0.5, lambda x: [place(x, gt_stab(0.1, ("F6", "C7"), 5000), i * 0.125, 1.0)
                                              for i in range(4)], tail=0.1, drive=2.6),
  "dm_critical.wav": lambda: loop(0.4, lambda x: [place(x, gt_stab(0.08, ("A6", "F7") if i % 2 else ("F6", "C7"), 6000),
                                                        i * 0.1, 1.0) for i in range(4)], tail=0.1, drive=3.0),
  "dm_critical_max.wav": lambda: loop(0.32, lambda x: [place(x, gt_stab(0.065, ("C7", "F7"), 7000), i * 0.08, 1.0)
                                                      for i in range(4)], tail=0.1, drive=3.4, peak=0.95),
}


# ================================================================ Comma-Neon (E minor)
def neon_saw(dur, notes, cut_from, cut_to, amp=1.0, gate=None):
  n = secs(dur)
  cut = curve(n, [(0, cut_from), (dur, cut_to)])
  voices, spread = (3, 14) if gate else (7, 22)
  x = sum(osc(n, hz(nm), "saw", cut, 0.7, voices, spread, seed=30 + i) for i, nm in enumerate(notes))
  if gate:  # a gated chord: on for the first 55 % of every beat, cut hard after - pumps at the beat
    t = t_of(n)
    ph = (t % gate) / gate
    e = np.clip((0.55 - ph) * gate / 0.006, 0.0, 1.0) * np.minimum(1.0, ph * gate / 0.002) * (1 - 0.35 * ph)
  else:
    e = adsr(n, 0.005, dur * 0.4, 0.55, 0.04, dur * 0.85)
  return x * e * amp / len(notes) ** 0.5


def riser(dur, f0, f1):
  n = secs(dur)
  return swoosh(n, [(0, f0), (dur, f1)], 0.45) * curve(n, [(0, 0.0), (dur * 0.85, 1.0), (dur, 0.0)], "lin")


NEON = {
  "engage.wav": lambda: one_shot(1.0, lambda x: (
    place(x, riser(0.32, 900, 7000), 0.0, 0.25),
    [place(x, neon_saw(0.16, (nm,), 1200, 5000), 0.05 + i * 0.07, 0.8) for i, nm in enumerate(("E5", "G5", "B5"))],
    place(x, neon_saw(0.6, ("E5", "B5", "E6", "G6"), 6000, 1500), 0.26, 1.0)), verb=0.16, fade=0.25),
  "disengage.wav": lambda: one_shot(1.0, lambda x: (
    [place(x, neon_saw(0.16, (nm,), 5000, 1500), i * 0.07, 0.85) for i, nm in enumerate(("E6", "B5", "G5"))],
    place(x, neon_saw(0.6, ("E4", "B4", "E5"), 3000, 400), 0.21, 1.0)), verb=0.14, fade=0.25),
  "refuse.wav": lambda: one_shot(0.55, lambda x: (
    place(x, neon_saw(0.13, ("E4", "F4", "B4"), 2500, 900), 0.0, 1.0),
    place(x, neon_saw(0.2, ("E4", "F4", "B4"), 2200, 600), 0.16, 1.0))),
  "pre_alert.wav": lambda: one_shot(0.5, lambda x: (
    place(x, neon_saw(0.12, ("B5",), 4000, 2000), 0.0, 0.9), place(x, neon_saw(0.25, ("E6", "B6"), 6000, 1800), 0.08, 0.9)), verb=0.14),
  "warning.wav": lambda: loop(0.9, lambda x: (place(x, neon_saw(0.1, ("E6", "B6"), 5500, 2200), 0.0, 1.0),
                                              place(x, neon_saw(0.12, ("E6", "B6"), 5500, 2200), 0.15, 1.0)), verb=0.1),
  "dm_warning.wav": lambda: loop(0.7, lambda x: [place(x, neon_saw(0.09, (nm,), 6000, 2500), i * 0.085, 1.0)
                                                for i, nm in enumerate(("E6", "G6", "B6"))], verb=0.08),
  "critical.wav": lambda: loop(0.5, lambda x: place(x, neon_saw(0.5, ("E6", "B6", "E7"), 7000, 4000, gate=0.125), 0.0, 1.0),
                               tail=0.05, drive=1.8),
  "dm_critical.wav": lambda: loop(0.4, lambda x: place(x, neon_saw(0.4, ("G6", "B6", "E7"), 8000, 5000, gate=0.1), 0.0, 1.0),
                                  tail=0.05, drive=2.1),
  "dm_critical_max.wav": lambda: loop(0.32, lambda x: place(x, neon_saw(0.32, ("B6", "E7", "G7"), 8500, 6000, gate=0.08),
                                                             0.0, 1.0), tail=0.05, drive=2.4, peak=0.95),
}

PACKS = {"Comma-Starship": STARSHIP, "Comma-GrandTourer": GRANDTOURER, "Comma-Neon": NEON}


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
