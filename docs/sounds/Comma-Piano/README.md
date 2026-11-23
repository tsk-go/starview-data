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

## Credits

- Grand piano: **Salamander Grand Piano** by Alexander Holm, licensed CC-BY 3.0
  (https://creativecommons.org/licenses/by/3.0/), the Tone.js mp3 set (github.com/Tonejs/audio). The notes are
  shifted in pitch, shortened, layered and processed here.
- Violin section and harp: **VS Chamber Orchestra: Community Edition** by Versilian Studios (CC0;
  github.com/sgossner/VSCO-2-CE).
