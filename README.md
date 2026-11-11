# StarView data

The StarView tablet app's source (`app/`) and its road data for the lane card.

## The app (`app/`)

Android project (Gradle 8.14, AGP 8.7, JDK 17). The Java was recovered from the StarView 2.84.1 APK after the original
Kotlin source was lost, then checked method by method against that APK's bytecode; see `app/app/compile-stubs/README.md`
for the one build trick it needs. The `*.kt` files are original Kotlin (where an older saved copy still matched the APK)
or new code.

- **Releases:** `.github/workflows/starview-app.yml` builds every push to `main` that touches `app/`. When `versionName`
  in `app/app/build.gradle.kts` is new, it publishes release `v<versionName>` with `StarView-<versionName>.apk`.
  Bump `versionName` and `versionCode` together for each release.
- **Updating the tablet:** from 2.85.8 StarView installs a new release by itself within about a minute, also while
  driving (Settings › Tablet › About › "Install updates automatically" turns that off; "Check for updates" still works).
  So a push to `main` with a new `versionName` reaches the tablet at once: drafts go on side branches. See
  `docs/STARVIEW-APP.md`.
- **Signing key:** every build is signed with the StarView key so it installs over the app already on the tablet. The key
  is not in this public repo: the workflow takes it from the repository secret `STARVIEW_KEYSTORE_B64` (base64 of
  `starview.jks`), and a local build reads `app/keystore/starview.jks` (git-ignored). Losing the key means the next
  version can only be installed after uninstalling StarView, which wipes its settings.
- **StarLock** (the phone's Lock / Unlock, `app/starlock`): recovered the same way from StarLock 2.19 and released as
  `starlock-v<versionName>` (`StarLock-<versionName>.apk`) by the same workflow, signed with the same key. Its library
  dex files rebuild byte-identical to 2.19's; only the app's own code differs (decompiled, then checked against 2.19).
  From 2.19.1 it updates itself the same way: Updates › Check for updates.
- **Local build:** `cd app && ./gradlew :app:assembleDebug :starlock:assembleDebug` (needs the Android SDK in
  `local.properties` or `ANDROID_HOME`).

## Docs (`docs/`)

- `docs/SETUP.md`: the whole build - parts, how each is connected and set up (tablet, USB hub and power, drive
  archive stick, RTK GPS, relay box, phone and StarLock, key fob radio, eGPU, offline mode) and what we learned.
- `docs/STARVIEW-APP.md`: the running record of the app - how releases and automatic updates work, the owner's
  direction for the driving screen, how to find crashes and freezes on the tablet, and every version with what changed
  and why. Kept the same as `claude/starview-app-source.md` in the StarView project on claude.ai.
- `docs/tools/`: the tools that compared the recovered source with the original APK, the source scanners, and the
  off-device rendering test.
- `docs/pending/`: work written but not yet landed (a patch for tsk-go/openpilot waiting for a signed commit).

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
