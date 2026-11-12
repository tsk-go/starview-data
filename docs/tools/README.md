# Tools used to recover and check the StarView source

None of this is built or shipped; it lives outside `app/`, so pushing it never starts the release workflow.

## `cmp/` and `scan/`: is the rebuilt code the same as the 2.84.1 APK?

The Java in `app/` was decompiled (jadx, Vineflower where jadx failed) from the 2.84.1 APK. Decompilers make mistakes
that compile cleanly, so every method was compared with the original bytecode. Work folder `S` (any scratch folder):

1. Original listings: unzip the 2.84.1 APK into `$S/jadx/dex`, then `dexdump -d classes*.dex > classesN.txt` there
   (`/opt/android-sdk/build-tools/34.0.0/dexdump`).
2. Rebuild listings: `./gradlew :app:assembleDebug`, unzip `app/app/build/outputs/apk/debug/app-debug.apk` into `$S/rb`
   and dexdump it the same way.
3. `python3 cmp/dexparse.py orig.pkl $S/jadx/dex/classes*.txt` and `python3 cmp/dexparse.py rb.pkl $S/rb/classes*.txt`.

Then, from `cmp/`:
- `cmp.py` / `cmp2.py` / `cmpm.py`: calls and strings per method, original vs rebuild.
- `cmpcast.py`, `cmpcast2.py [--strict]`: casts per method (value source -> cast type). This found the 2.84.2 start-up
  crash (a GPS fix cast to a collection) and its look-alikes.
- `cmparith.py`, `cmparith2.py`: division / conversion / long-vs-int opcodes per method (int division where the original
  divided decimals: blank map tiles in 2.85.0; int overflow where the original used long).

From `scan/` (source scanners, run on `app/app/src/main/java/com/starpilot/starview`):
- `bc.sh Class method [n]` / `bcr.sh`: one method's original / rebuilt bytecode, compact.
- `onceloop.py`: loops whose body always exits (`Nav.decodePolyline` returned after one point: every route empty).
- `dupinc.py`: an if-block repeating the loop step. `skipinc.py`: a `continue` that skips the loop step (the endless
  loop in `LaneAhead.findDrop`, the 2.85.4 freeze). `divlines.py`: divisions to review.
- `castsrc.py DIR`: every explicit collection cast on a reused decompiler variable with that variable's last assignments
  (the 2.85.6 audit after the Presets crash).

## `render/`: pictures of views without the tablet

`PicTest.kt` renders StarView's own views (wheel, see-through map, the whole screen editor) to PNG with Robolectric, so a
change can be shown to the owner before it's built. To use it, copy it to `app/app/src/test/java/com/starpilot/starview/`
and add to `app/app/build.gradle.kts` (never commit these):

```kotlin
// inside android { }
testOptions { unitTests.isIncludeAndroidResources = true; unitTests.all { it.systemProperty("robolectric.offline", "true"); it.systemProperty("robolectric.dependency.dir", file("build/robo").absolutePath); it.systemProperty("picOut", "/some/folder"); it.maxHeapSize = "3g" } }
// inside dependencies { }
testImplementation("junit:junit:4.13.2")
testImplementation("org.robolectric:robolectric:4.14.1")
```

`build/robo/` needs `android-all-instrumented-15-robolectric-12650502-i7.jar` (fetch it through a Gradle configuration;
Maven Central rate-limits Robolectric's own downloader). Run `./gradlew :app:testDebugUnitTest --offline --tests
com.starpilot.starview.PicTest`. The tablet is 1472 x 920 dp at density 2.0 (qualifiers `w1472dp-h920dp-land-xhdpi`).

## `run_stubbed.py`: the comma's StarView tests on a PC

`PYTHONPATH=<openpilot>:<openpilot>/opendbc_repo python3 run_stubbed.py -q <test file>` stubs the compiled modules a PC
lacks (msgq, params) so starviewd's tests import.

## `render/LaneSceneTest.kt`: the new Scene view on a recorded drive

The 2.87.0 Scene view (LaneSceneView, with LaneMap and LaneFit) run off the tablet on a real drive, to show the owner
before a release. Data (never in git - it holds where the car drove): `render/lane_conv.py OSM_DIR EXPORT.json.gz DIR`
makes `DIR/osm_all.json` from OpenStreetMap extracts (`api.openstreetmap.org/api/0.6/map?bbox=...`) and
`DIR/drive_<window>.json` from the comma's lane export (modelV2 lines, GPS, radar per segment). `DIR/picks.txt` lists the
moments: `window,YYYY-MM-DD HH:MM:SS (Eastern),name[,day][,cars][,top][,zoom=D][,tilt=E]` (`top` adds a bird's-eye check
of the fit, `cars` three hand-placed cars). Set up like PicTest, plus `it.systemProperty("laneData", DIR)`, and run with
`--rerun` (Gradle doesn't see picks.txt change). Each moment replays LaneMap + LaneFit over the 90 s before it.

## `scene2/`: the road users' pictures in assets/scene2

LaneSceneView draws cars, people, signs and the van from pictures of the old Scene's own 3D models (assets/models):
`sprites.html` (three.js 0.169.0 in `node_modules`, `models` -> assets/models, served on 127.0.0.1:8765) renders each
model from 3 heights and 21 turns, the van from the chase camera at 12-48 deg; `gen.sh` runs it through Playwright
(`run.js`), `pack.py OUT` packs WebP sheets and `sprites.json`; `sheet.py` makes a contact sheet to look at. truck.glb and
bus.glb face backwards in their files (`flip=1`).
