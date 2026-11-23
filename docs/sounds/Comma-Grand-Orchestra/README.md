# Comma-Grand-Orchestra

Comma-Piano-Grand-Full with more instruments (owner, Nov 23: "What I meant add more instruments"): French horns,
flute, oboe, a solo violin and timpani join the grand piano, the harp and the cello, viola and violin sections.
Same chords as Grand (G/D to D for engage) and StarPilot's pace for the warnings. Made by
`docs/tools/sounds/make_orchestra_pack.py`.

| File | Alert | |
|---|---|---|
| engage.wav | engaged | timpani roll into the D chord's downbeat; piano G/D then D with its left hand; horns holding the chords; solo violin and flute on the top line; oboe B4 to A4; the string sections; harp sweep |
| disengage.wav | disengaged | D then G/B: horns and cellos stepping down, flute A5 to G5, a soft timpani note |
| refuse.wav | can't engage | the piano's cluster with a low horn and a muted timpani stroke |
| pre_alert.wav | heads-up | harp sweep, piano chord, a flute note |
| warning.wav | prompt (repeats every 0.65 s) | the piano's chord with a soft flute on top |
| dm_warning.wav | distracted (0.75 s) | the piano burst with the oboe on its last note |
| critical.wav | take control (0.75 s) | the piano burst doubled by short horns and a timpani stroke |
| dm_critical.wav | take control now (0.8 s) | the same, fuller and louder |
| dm_critical_max.wav | last step (0.8 s) | the burst twice per 0.8 s with horns and timpani over tremolo violins |

## Credits

- Grand piano: **Salamander Grand Piano** by Alexander Holm, CC-BY 3.0
  (https://creativecommons.org/licenses/by/3.0/), the Tone.js mp3 set. Notes shifted in pitch, shortened, layered
  and processed here.
- Horns, flute, oboe, solo violin, timpani, harp and the string sections: **VS Chamber Orchestra: Community Edition**
  by Versilian Studios (CC0; github.com/sgossner/VSCO-2-CE).
