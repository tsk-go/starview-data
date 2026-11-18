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
`DIR/drive_<window>.json` from the comma's lane export (modelV2 lines, GPS, radar per segment). `DIR/picks.txt` lists
the moments, one per line:
`window,YYYY-MM-DD HH:MM:SS (Eastern),name[,day][,cars][,nan][,lcL|lcR][,lanes=L:R[:E]][,nomap][,top][,zoom=D][,tilt=E]`
(`top` adds a bird's-eye check of the fit, `cars` three hand-placed cars, `nan` two cars with the detector's NaN
distance / heading, `lcL` / `lcR` a lane change to that side, `lanes=L:R[:E]` the lane card's count, `nomap` camera
only). Set up like PicTest, plus `it.systemProperty("laneData", DIR)`, and run with `--rerun` (Gradle doesn't see
picks.txt change). Each moment replays LaneMap + LaneFit over the 90 s before it.

## `render/SceneSeqTest.kt`: the Scene view as a film of a recorded drive (2.90.0)

Pictures of single moments (LaneSceneTest) can't show a road that jumps; this replays whole stretches of a drive at 10
frames a second. Same data folder as LaneSceneTest, plus `DIR/seq.txt`, one stretch per line:
`window,YYYY-MM-DD HH:MM:SS (Eastern),seconds,name[,day][,flip][,spin]` (`flip`: the lane card changes its mind as in
the owner's Nov 17 video, `spin`: three made-up detected cars whose guessed heading flickers). Set up like PicTest with
`it.systemProperty("picOut", System.getenv("PIC_OUT") ?: "/tmp")`, `it.systemProperty("laneData",
System.getenv("LANE_DATA") ?: "/tmp")` and `it.maxHeapSize = "3g"`, then
`SEQ_MODES=before,anchor PIC_OUT=OUT LANE_DATA=DIR ./gradlew :app:testDebugUnitTest --offline --rerun --tests
com.starpilot.starview.SceneSeqTest`. Modes: `before` (2.89: every frame from that instant's guess), `after`
(SceneSteady over LaneFit, never released), `anchor` (2.90.0: LaneAnchor + LaneRibbon). It writes
`OUT/<name>_<mode>/NNNN.png` and `OUT/<name>.txt` (when the drawn lane changed, every time the road ahead was laid
again and why; `ANCHOR_LOG=1` adds LaneAnchor's line at every step). Two numbers to watch after a change: "the road
ahead was laid N times" (once, plus once per fork really taken the other way) and the count of sudden picture changes
(mean absolute difference of consecutive frames at 320 x 200 above 5: 2.89 gave 4 - 21 per stretch, 2.90.0 0 - 1).
A film for the owner: `ffmpeg -framerate 10 -i OUT/<name>_before/%04d.png -framerate 10 -i OUT/<name>_anchor/%04d.png
-filter_complex "[0]scale=960:600[a];[1]scale=960:600[b];[a][b]vstack" -pix_fmt yuv420p film.mp4`.

## `render/GalaxyRowsTest.kt`: Galaxy's settings rows (2.90.1)

GalaxyMirror on rows built the way Galaxy's `settings.py` `_projection` sends them: the global acceleration / deceleration
profile selectors (`displayScale` "selected-profile": "StarPilot Default" shown as "Normal", not offered, the Default
button by the saved values) and a Developer Parameters slider's `learnedValue`; `page()` draws a page with them through
GalaxySettingsView's own `draw` into `OUT/galaxy_rows.png`. Set up like PicTest, then `PIC_OUT=OUT ./gradlew
:app:testDebugUnitTest --offline --rerun --tests com.starpilot.starview.GalaxyRowsTest`.

## `unit/`: plain JVM tests of single pieces

`SceneSteadyTest.kt`, `LaneAnchorTest.kt`, `LaneRibbonTest.kt` (2.90.0, the Scene view; Robolectric as for PicTest, no
drive data): SceneSteady's holds (the lane card's count, a line dropping out, a line's paint, a car's facing, the speed
sign); LaneAnchor on a made-up 3-lane road (the lane card changing its mind, driving across a line, a wrong start, no
GPS under a bridge); LaneRibbon on made-up maps of the places where the map's own lines are wrong - a lane more and a
lane fewer, an exit the map starts at the middle of the road, ramps it ends there (one lane, two lanes from either
side, one brought in over 500 ft in short pieces), and how much of the road behind us is drawn. Each LaneRibbon test
drives the map at 34 mph with LaneAnchor placing us and checks the thread for bends that aren't there and the lane
we're put in. Run: `./gradlew :app:testDebugUnitTest --offline --rerun --tests com.starpilot.starview.LaneRibbonTest`
(likewise the other two).

`ExitGuardTest.kt` (2.87.1): when the route's exit may signal by itself, on a made-up lane tracker (the Nov 12 left
fork: sure of no lane on the left, then "maybe one" right before the fork). Copy into
`app/app/src/test/java/com/starpilot/starview/`, add Robolectric as for PicTest (LaneBrain is plain Java but needs the
Android stubs) and run `./gradlew :app:testDebugUnitTest --offline --tests com.starpilot.starview.ExitGuardTest`.

`EdgeRoomTest.kt` (2.89.3): a road edge past a sure lane count is a wide shoulder (`LaneBrain.hybEdgeRoom`), on the
lane tracker itself fed the camera's edge as `edges()` does - same setup as ExitGuardTest.

`SpeedAlertTest.kt` (2.88.0): the speed limit warning's list and when its sound plays, every sound written as a WAV
file by the app's own code (AlertSounds, once and 3 times) and a picture of its settings section, into `-DpicOut`.

## `scene2/`: the road users' pictures in assets/scene2

LaneSceneView draws cars, people, signs and the van from pictures of the old Scene's own 3D models (assets/models):
`sprites.html` (three.js 0.169.0 in `node_modules`, `models` -> assets/models, served on 127.0.0.1:8765) renders each
model from 3 heights and 21 turns, the van from the chase camera at 12-48 deg; `gen.sh` runs it through Playwright
(`run.js`), `pack.py OUT` packs WebP sheets and `sprites.json`; `sheet.py` makes a contact sheet to look at. truck.glb and
bus.glb face backwards in their files (`flip=1`).
