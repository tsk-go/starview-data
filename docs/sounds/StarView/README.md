# Sound packs for the comma

**Comma-Signature** and **Comma-Signature-Night** (owner, Nov 22: "Come up with something amazing!!!",
`docs/tools/sounds/make_signature_pack.py`): the comma's own four-note signature, D5 - A5 - E6 - F#6, that every
alert is made from - engage plays it in full on FM brass over strings with a harp sweep, a shimmer and a smooth hall;
disengage plays it falling; refuse is muted brass on a tritone; pre-alert a harp sweep up to its last note; the
prompts two and three of its notes on an electric piano; "take control" its first two notes as a driving brass figure,
faster at each step. Night: the one-shots warmer and softer (less brass edge, more strings, top rolled off), the
prompts and warnings as by day.

**Comma-Starship, Comma-GrandTourer, Comma-Neon** (owner, Nov 22: "Come up with something better"): layered,
designed sounds (`docs/tools/sounds/make_device_packs_v2.py`). Starship (D): a rising whoosh blooming into a bright
chord for engage, the chord powering down for disengage, holographic FM blips, a red-alert whoop for "take control".
GrandTourer (F): warm analog plucks and a soft major-ninth swell, resonant pulsing stabs for the warnings. Neon
(E minor): detuned-saw arpeggios with a riser, gated chords that pump faster for the warnings.

**Comma-Synth, Comma-Pulse, Comma-Retro** (owner, Nov 22: "it has to sound different than the ones on the app, I
should know it's from the device"): no sine beeps, bells, marimba or chirps like the tablet's speed warning, no
A-major bells and echo like its voice alerts - filtered saw stabs in D (Synth), gliding squares in F# with a
two-tone siren for "take control" (Pulse), 8-bit arpeggios in G (Retro); no echo. Made by
`docs/tools/sounds/make_device_packs.py`.

The StarView* packs below sound like the tablet's voice alerts on purpose - kept, not for the comma.

## StarView (bells)

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
