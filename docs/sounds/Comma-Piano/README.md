# Comma-Piano

The comma's alerts as short Yanni-style phrases on real recorded instruments, in D major: a grand piano over a
violin section, harp sweeps, a warm hall. Made by `docs/tools/sounds/make_piano_pack.py` (it downloads the
instrument recordings it needs on first run).

| File | Alert | |
|---|---|---|
| engage.wav | engaged | piano arpeggio up D-F#-A-D, then E-F#; the violins swell under it and take up the last note; harp sweep |
| disengage.wav | disengaged | the piano coming down F#-D-A-F# and settling on D, the violins fading |
| refuse.wav | can't engage | two soft piano clusters (D + D#), damped |
| pre_alert.wav | heads-up | a harp sweep up to a high A on the piano |
| warning.wav | prompt (repeats) | A then D on the piano |
| dm_warning.wav | distracted (repeats) | D-E-F# on the piano, quicker |
| critical.wav | take control (repeats) | piano octaves hammered over tremolo violins |
| dm_critical.wav | take control now (repeats) | the same, faster and higher |
| dm_critical_max.wav | last step (repeats) | faster and higher still |

## More, paced like StarPilot's own sounds (Nov 23)

Owner: "I think the high is too fast check out Starpilot's sounds". StarPilot's built-in sounds are piano-like notes
in the middle of the keyboard; their warnings are one quick burst that rings out, every 0.65 s (prompt), 0.75 s
(distracted, take control) and 0.8 s (take control now). `docs/tools/sounds/make_piano_more.py` makes three packs at
that pace and register, same instruments:

- **Comma-Piano-Calm** (D major): engage D-A-D-F# rising slowly into strings; a soft dyad for the prompt.
- **Comma-Piano-Romance** (B minor turning to D): engage B-D-F#, then the D chord opening, violins over it.
- **Comma-Piano-Grand** (D major, chords): engage G/D to D like the opening of a theme, harp and strings.

The last "take control" step plays its burst twice per 0.8 s over quiet tremolo violins.

**Comma-Piano-Grand-Full** (owner: "I like the Comma Piano Grand. Make it more full body"): Grand with the piano's
left hand (a bass octave under every chord, and under the first note of every warning burst, each burst note doubled
an octave down), chords spread over both hands, cello and viola sections under the violins, a warmer tone (+3.5 dB
around 250 Hz, nothing under 110 Hz - the comma's speaker can't play it) and a bigger hall. The warnings keep
StarPilot's pace, with +5.5 dB around 2 kHz so they stay as clear as Grand's on the small speaker.

## Credits

- Grand piano: **Salamander Grand Piano** by Alexander Holm, licensed CC-BY 3.0
  (https://creativecommons.org/licenses/by/3.0/), the Tone.js mp3 set (github.com/Tonejs/audio). The notes are
  shifted in pitch, shortened, layered and processed here.
- Violin, viola and cello sections and harp: **VS Chamber Orchestra: Community Edition** by Versilian Studios (CC0;
  github.com/sgossner/VSCO-2-CE).
