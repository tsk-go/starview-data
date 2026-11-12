# Pending: written, not yet in tsk-go/openpilot

## Two signed commits to land on tsk-go/openpilot `Dom` (written Nov 12, on top of `8bea50d87`)

Written in a session without `GIT_SIGNING_KEY_B64`, so not committed there. In a session with the key (the fork's
CLAUDE.md signing setup first), from a checkout of tsk-go/openpilot on `Dom`:

```
git am --committer-date-is-author-date -S docs/pending/0001-*.patch docs/pending/0002-*.patch   # paths from this repo
```

then check `git log -2 --format='%h %GK %s'` shows the signing key on both, run the two tests, push, and delete the two
patch files and this section.

- `0001` - patch 72, fork history 42: starviewd starts StarView again on the tablet when it disappears while the car
  is on (an update replaced it, or a crash). Now also what the owner was promised: never over another app -
  `_may_cover(_tablet_front())` allows only the home screen (launcher from `cmd package resolve-activity`), the lock
  screen / system UI, or nothing adb can tell; another app in front -> it looks again 20 s later (within the 5 min).
  Test: `openpilot/starpilot/system/starview/tests/test_reopen.py` (5 tests).
- `0002` - patch 74, fork fix 6 / history 43: the "Prefer phone GPS" speed-limit bug below - `phone @4` in
  `MapdOut.GpsSource` (openpilot/cereal/custom.capnp) and a 10 s 'phone' age limit (mapd's own) in
  `speed_limits/map_source.py`. Test: `test_phone_gps_handoff_is_read_and_kept_by_its_own_age` in
  `speed_limits/tests/test_map_source.py`.

## Map speed limits ignored with GPS source "Prefer phone GPS" (found Nov 11; fixed in `0002` above)

With Galaxy > Navigation > GPS Source = "Prefer phone GPS" (the RTK receiver reaches the comma through the phone GPS
handoff, StarView patch 16), mapd matches the road and its limit fine (`mapdOut`: tileLoaded, matchedLimit, e.g. 55 on
a parkway), but the speed-limit side never takes it: map stays `unknown` in `slcState.sourceReadings`.
mapd (Go) writes `gpsSource = 4` (its own GpsSourcePhone), which the Python schema's `MapdOut.GpsSource` enum doesn't
have (`str()` raises "Member was null", so `speed_limits/map_source.copy_frame` drops the frame), and
`map_source.GPS_MAX_AGE_NS` has no 'phone' entry either. Parked, mapd also refuses the phone fix on purpose (no
heading: bearingAccuracy NaN -> noMatch before loading tiles). Upstream bug (firestar); fix = add `phone @4` to
`MapdOut.GpsSource` in openpilot/cereal/custom.capnp and a 'phone' age limit in map_source.py, or tell firestar.
Workaround now: GPS Source "Phone GPS as backup" (the comma's own GPS when it has a fix, RTK only without one).
