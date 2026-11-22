#!/usr/bin/env python3
"""The "StarView" sound pack for the comma's driving alerts, in the voice alerts' style.

Same instruments as StarView's voice alerts (AmySynth): the bell (five partials, 6 ms attack, slight vibrato), the
pluck, the warm chord under the voice (the pad, here an octave up so the comma's small speaker carries it), the
same early echoes, the same key (A major: A, C#, E). Written as StarPilot sound pack files: mono, 16-bit, 48 kHz,
one file per alert (openpilot/starpilot/audio/sound_pack.py). The alerts that repeat (prompt repeat, distracted,
both "take control" warnings) are one seamless loop each: their echo tail is folded back to the start.

  python3 make_starview_pack.py OUT_DIR     # writes OUT_DIR/*.wav and OUT_DIR/preview.wav
"""
import sys
import wave
from pathlib import Path

import numpy as np

SR = 48000
PARTIALS = ((1.0, 1.0, 1.0), (2.0, 0.28, 1.6), (3.0, 0.10, 2.4), (2.76, 0.06, 3.0), (5.4, 0.025, 4.5))
A5, B5, CS6, E6, A6, CS7, E7, A7 = 880.0, 987.77, 1108.73, 1318.51, 1760.0, 2217.46, 2637.02, 3520.0
E5, DS5 = 659.26, 622.25


def t_of(n):
  return np.arange(n) / SR


def bell(out, f0, start, amp, decay, bright=1.0):
  """AmySynth.bells' voice: partials with their own decay, a 6 ms attack, 4.5 Hz vibrato of 0.15 %."""
  s0 = int(start * SR)
  if s0 >= len(out):
    return
  t = t_of(len(out) - s0)
  det = 1 + np.sin(2 * np.pi * 4.5 * t) * 0.0015
  attack = 1 - np.exp(-t / 0.006)
  for i, (ratio, a, k) in enumerate(PARTIALS):
    a = a * (bright if i else 1.0)
    out[s0:] += np.sin(2 * np.pi * f0 * ratio * t * det) * a * np.exp(-decay * k * t) * attack * amp


def pluck(out, f, start, amp):
  s0 = int(start * SR)
  if s0 >= len(out):
    return
  t = t_of(len(out) - s0)
  out[s0:] += np.sin(2 * np.pi * f * t) * np.exp(-t / 0.3125) * (1 - np.exp(-t / 0.004)) * amp


def pad(n, attack, fade, chord=(440.0, 554.37, 659.26, 987.77, 1318.51)):
  """The chord under the voice (AmySynth.pad: detuned pairs, slow tremolo, a touch of the octave), an octave up."""
  rng = np.random.default_rng(7)
  t = t_of(n)
  out = np.zeros(n)
  for i, f in enumerate(chord):
    for det in (-0.9, 0.9):
      ph = rng.random()
      g = 0.9 if i < 3 else 0.45
      trem = 1 + np.sin(2 * np.pi * ((0.35 + i * 0.07) * t + ph)) * 0.08
      ff = f + det
      out += (np.sin(2 * np.pi * (ff * t + ph)) + np.sin(2 * np.pi * 2 * ff * t) * 0.18) * trem * g * 0.5
  env = np.minimum(1.0, t / attack) ** 2
  fo = min(int(fade * SR), n)
  if fo:
    x = np.linspace(1, 0, fo)
    env[-fo:] *= x * x
  return out * env


def room(x):
  """Both of AmySynth's echo sets, folded to mono (left and right averaged)."""
  y = x.copy()
  for ms, g in ((23, .11), (31, .11), (61, .06), (73, .06), (113, .03), (127, .03),
                (29, .16), (33, .16), (47, .12), (53, .12), (83, .08), (94, .08), (131, .05), (148, .05), (197, .03),
                (223, .03)):
    d = int(SR * ms / 1000)
    y[d:] += x[:-d] * g * 0.5
  return y


def lowpass_circular(x, cutoff=7000.0):
  """Above ~7 kHz the comma's speaker gives nothing but hiss; the saturation's overtones would fold back as grit."""
  f = np.fft.rfftfreq(len(x), 1 / SR)
  gain = np.clip((cutoff * 1.25 - f) / (cutoff * 0.25), 0, 1)
  return np.fft.irfft(np.fft.rfft(x) * gain, len(x))


def finish(x, peak=0.89, fade_out=0.12, drive=0.0, looped=False):
  if drive:  # louder without a harsher peak: a soft saturation for the "take control" warnings
    x = lowpass_circular(np.tanh(x / np.max(np.abs(x)) * drive))
  x = x / np.max(np.abs(x)) * peak
  fo = min(int(fade_out * SR), len(x))
  if fo:
    x[-fo:] *= np.linspace(1, 0, fo) ** 2
  if not looped:  # a loop's first sample follows its last: no fade there
    fi = 48  # 1 ms: no click at the start
    x[:fi] *= np.linspace(0, 1, fi)
  return x


def one_shot(length, make, **kw):
  x = np.zeros(int(length * SR))
  make(x)
  return finish(room(x), **kw)


def loop(period, strikes, tail=1.5, drive=0.0, peak=0.89):
  """A seamless loop: render a period plus its tail, then fold the tail back over the start."""
  n = int(period * SR)
  x = np.zeros(n + int(tail * SR))
  for f, start, amp, decay, bright in strikes:
    bell(x, f, start, amp, decay, bright)
  x = room(x)
  y = x[:n].copy()
  rest = x[n:]
  while len(rest):
    k = min(n, len(rest))
    y[:k] += rest[:k]
    rest = rest[k:]
  y -= y.mean()
  return finish(y, peak=peak, fade_out=0, drive=drive, looped=True)


def engage(x):
  x += pad(len(x), 0.12, 0.6) * 0.06
  for f, s, a in ((A5, 0.0, .50), (CS6, .08, .45), (E6, .16, .42), (A6, .24, .40)):
    bell(x, f, s, a, 2.2)


def disengage(x):  # the voice alerts' "stopped": falling E6, A5, E5
  for f, s, a, d in ((E6, 0.0, .45, 2.4), (A5, .13, .45, 2.4), (E5, .26, .40, 2.0)):
    bell(x, f, s, a, d)


def refuse(x):  # "uh-uh": a low bell and the semitone under it
  bell(x, E5, 0.0, .55, 3.5, bright=1.6)
  bell(x, DS5, .17, .55, 3.5, bright=1.6)


def pre_alert(x):  # the voice alerts' "done": one high bell and three plucks rising
  bell(x, A6, 0.0, .30, 3.0)
  for f, s, a in ((A6, .14, 1.0), (CS7, .28, .8), (E7, .42, .6)):
    pluck(x, f, s, a * 0.12)


SOUNDS = {
  "engage.wav": lambda: one_shot(1.2, engage, fade_out=0.25),
  "disengage.wav": lambda: one_shot(1.1, disengage, fade_out=0.25),
  "refuse.wav": lambda: one_shot(0.8, refuse, fade_out=0.15),
  "pre_alert.wav": lambda: one_shot(1.0, pre_alert, fade_out=0.2),
  # prompt (once) and prompt repeat (looped): the voice alerts' "short" - E6 then A6
  "warning.wav": lambda: loop(0.9, [(E6, 0.0, .45, 2.6, 1.0), (A6, .09, .38, 2.4, 1.0)]),
  # distracted: three bells rising, a little faster
  "dm_warning.wav": lambda: loop(0.75, [(A6, 0.0, .42, 3.0, 1.0), (CS7, .07, .40, 3.0, 1.0), (E7, .14, .38, 3.0, 1.0)]),
  # take control (soft): high and low bells in turn, four a half second, louder
  "critical.wav": lambda: loop(0.5, [(E7, 0.0, .6, 6.0, 1.3), (A6, .125, .55, 6.0, 1.3), (E7, .25, .6, 6.0, 1.3),
                                     (A6, .375, .55, 6.0, 1.3)], tail=1.0, drive=2.2),
  # take control now: the A major chord struck ten times a second, loud
  "dm_critical.wav": lambda: loop(0.4, [(f, s, a, 7.0, 1.4) for s, acc in ((0.0, 1.0), (.1, .8), (.2, 1.0), (.3, .8))
                                        for f, a in ((A6, .5 * acc), (CS7, .35 * acc), (E7, .45 * acc))],
                                  tail=0.8, drive=2.6),
  "dm_critical_max.wav": lambda: loop(0.32, [(f, s, a, 8.0, 1.5) for s in (0.0, .08, .16, .24)
                                            for f, a in ((A6, .5), (E7, .45), (A7, .3))], tail=0.8, drive=3.2,
                                      peak=0.95),
}
LOOPED = {"warning.wav": 4, "dm_warning.wav": 4, "critical.wav": 6, "dm_critical.wav": 6, "dm_critical_max.wav": 6}


def write(path, x):
  pcm = np.clip(np.round(x * 32767), -32768, 32767).astype("<i2")
  with wave.open(str(path), "wb") as w:
    w.setnchannels(1)
    w.setsampwidth(2)
    w.setframerate(SR)
    w.writeframes(pcm.tobytes())


def main(out):
  out = Path(out)
  out.mkdir(parents=True, exist_ok=True)
  preview = []
  for name, make in SOUNDS.items():
    x = make()
    write(out / name, x)
    preview += [np.tile(x, LOOPED.get(name, 1)), np.zeros(int(0.7 * SR))]
    print(f"{name:22s} {len(x) / SR:.2f} s  peak {np.max(np.abs(x)):.2f}  rms {np.sqrt(np.mean(x * x)):.3f}"
          f"{'  (loops)' if name in LOOPED else ''}")
  write(out / "preview.wav", np.concatenate(preview) * 0.95)


if __name__ == "__main__":
  main(sys.argv[1] if len(sys.argv) > 1 else "StarView")
