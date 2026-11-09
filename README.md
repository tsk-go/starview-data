# StarView data

Road data for the StarView tablet app's lane card.

## HPMS lane counts (release `hpms-2024`)

One file per state (`hpms_XX.bin`, 50 states + DC + PR, 367 MB in all) plus `manifest.json` (state, file, bytes, sha256).
The tablet downloads them on Wi-Fi from Settings › Lane road data.

- Source: FHWA Highway Performance Monitoring System, "HPMS Spatial All Sections - 2024"
  (data.transportation.gov dataset 42um-tgh5), a U.S. government work in the public domain.
- Kept: classified roads (functional system 1-6) with a through-lane count, and every ramp. Local streets are left out.
- Per section: through lanes (both ways and per direction), facility type (one-way / two-way / ramp), structure
  (bridge / tunnel), functional system, lane width, access control, median type, route name, and its line.
- Through lanes leave out acceleration / deceleration (merge / exit), weaving and collector-distributor lanes, so a lane
  beside a ramp that the camera sees on top of this count is a merge or exit lane.

Format: StarView HPMS pack v1, little-endian. Polylines cut into chunks of at most 48 points / ~600 m, filed by the tile
(0.01 degree) of their middle point, coordinates in 1e-5 degrees with zigzag-varint deltas. See `tools/hpms/pack.py` and
`Hpms.kt` in the StarView app.
