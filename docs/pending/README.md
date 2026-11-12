# Pending: written, not yet in tsk-go/openpilot

## Map speed limits ignored with GPS source "Prefer phone GPS" (found Nov 11; fixed on tsk-go/openpilot Dom in `9e1e44c67`, patch 74 / fork fix 6)

With Galaxy > Navigation > GPS Source = "Prefer phone GPS" (the RTK receiver reaches the comma through the phone GPS
handoff, StarView patch 16), mapd matches the road and its limit fine (`mapdOut`: tileLoaded, matchedLimit, e.g. 55 on
a parkway), but the speed-limit side never takes it: map stays `unknown` in `slcState.sourceReadings`.
mapd (Go) writes `gpsSource = 4` (its own GpsSourcePhone), which the Python schema's `MapdOut.GpsSource` enum doesn't
have (`str()` raises "Member was null", so `speed_limits/map_source.copy_frame` drops the frame), and
`map_source.GPS_MAX_AGE_NS` has no 'phone' entry either. Parked, mapd also refuses the phone fix on purpose (no
heading: bearingAccuracy NaN -> noMatch before loading tiles). Upstream bug (firestar); fix = add `phone @4` to
`MapdOut.GpsSource` in openpilot/cereal/custom.capnp and a 'phone' age limit in map_source.py, or tell firestar.
Workaround now: GPS Source "Phone GPS as backup" (the comma's own GPS when it has a fix, RTK only without one).
