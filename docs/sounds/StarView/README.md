# StarView sound pack

The comma's driving alerts in the style of StarView's voice alerts: soft bells with a short echo, the warm chord
under the engage chime, all in the voice alerts' key (A major). Made by `docs/tools/sounds/make_starview_pack.py`
(run it again to rebuild these files). Mono, 16-bit, 48 kHz, as StarPilot's sound packs must be.

| File | Alert | |
|---|---|---|
| engage.wav | openpilot engaged | four bells rising, with the chord |
| disengage.wav | disengaged | the voice alerts' "stopped": three bells falling |
| refuse.wav | can't engage | a low bell and the semitone under it |
| pre_alert.wav | heads-up before an alert | one high bell, three plucks rising |
| warning.wav | prompt (repeats while it lasts) | two bells, E then A |
| dm_warning.wav | distracted (repeats) | three bells rising, faster |
| critical.wav | take control (repeats) | high and low bells in turn, loud |
| dm_critical.wav | take control now (repeats) | the chord struck ten times a second, louder |
| dm_critical_max.wav | the last, loudest step (repeats) | faster and brighter still |

More packs, same alerts, key and echo, other instruments (`docs/tools/sounds/make_pack_variants.py`):
`StarView-Marimba` (wooden bars), `StarView-Glass` (glass bells, wider echo), `StarView-Kalimba` (thumb piano),
`StarView-Harp` (plucked strings; engage / disengage glide up / down), `StarView-Ambient` (the voice alerts'
background chord up front under engage, disengage and the pre-alert). Each folder here is one pack.

Install on the comma: a pack's files go to `/data/themes/theme_packs/<pack>/sounds/`, then choose it as the
Sound Pack in Galaxy (Sounds & Alerts). Each alert's volume is still set in Galaxy.
