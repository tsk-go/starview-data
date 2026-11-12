# Pending: written, not yet in tsk-go/openpilot

## `openpilot-patch72-reopen.diff` (Nov 11)

Comma-side backstop: starviewd starts StarView again on the tablet when it disappears while the car is on (a crash
mid-drive). Fork history item 42 in the fork's CLAUDE.md, starviewd `Hub._reopen_check`, test `test_reopen.py`.

Not committed because that session had no `GIT_SIGNING_KEY_B64` (every commit to the fork must be signed). To land it,
in a session with the key, on the fork's `Dom` branch:

```
git apply docs/pending/openpilot-patch72-reopen.diff   # from a checkout of tsk-go/openpilot, path adjusted
```

Before committing, change one thing the owner was promised: leave StarView alone when another app is in front
(`_tablet_front()` returns another package that isn't the launcher), so it never pops up over an app he's using. Then
update the CLAUDE.md entry to say so, run the test, sign, push, and delete this file.

## Map speed limits ignored with GPS source "Prefer phone GPS" (found Nov 11, comma side, not fixed yet)

With Galaxy > Navigation > GPS Source = "Prefer phone GPS" (the RTK receiver reaches the comma through the phone GPS
handoff, StarView patch 16), mapd matches the road and its limit fine (`mapdOut`: tileLoaded, matchedLimit, e.g. 55 on
the Palisades Parkway), but the speed-limit side never takes it: map stays `unknown` in `slcState.sourceReadings`.
mapd (Go) writes `gpsSource = 4` (its own GpsSourcePhone), which the Python schema's `MapdOut.GpsSource` enum doesn't
have (`str()` raises "Member was null", so `speed_limits/map_source.copy_frame` drops the frame), and
`map_source.GPS_MAX_AGE_NS` has no 'phone' entry either. Parked, mapd also refuses the phone fix on purpose (no
heading: bearingAccuracy NaN -> noMatch before loading tiles). Upstream bug (firestar); fix = add `phone @4` to
`MapdOut.GpsSource` in openpilot/cereal/custom.capnp and a 'phone' age limit in map_source.py, or tell firestar.
Workaround now: GPS Source "Phone GPS as backup" (the comma's own GPS when it has a fix, RTK only without one).
