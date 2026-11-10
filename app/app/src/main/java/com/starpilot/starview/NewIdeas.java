package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: NewIdeas.kt */
public final class NewIdeas {
    public static final NewIdeas INSTANCE = new NewIdeas();
    private static final Map<String, String> FIRST = MapsKt.mapOf(TuplesKt.to("shadows", "First version: warns only (no slowing), sunny days at corners; a trained model later."), TuplesKt.to("wheels", "First version: the camera's view of cars beside you moving sideways toward your lane (not the wheels themselves yet)."), TuplesKt.to("lane_memory", "Shifts up to 1 ft, only while Lane Centering is steering (two lines or one); with no lines at all the comma doesn't steer by it. Needs RTK fixed."), TuplesKt.to("crowd_line", "Shifts up to 1 ft toward the car ahead's line, only while Lane Centering is steering."), TuplesKt.to("teach", "Follows your taught path up to 1 ft inside the lane, at your taught speed (never faster than your set speed). Needs RTK fixed."), TuplesKt.to("carsick", "First version: a comfort score per drive (from the driver's head) - it doesn't change the steering yet."), TuplesKt.to("model_disagree", "Saves a lane picture whenever the lane model and the lane card disagree (for the Fix screen and training)."), TuplesKt.to("night_school", "Train on your PC with ml/lane_count/night_school.py; the tablet picks up the newest model from Download/StarView/models when a drive starts."), TuplesKt.to("eyes", "Eases off when you watch one side for over a second; where you look when stopped (the tablet, the radio) is learned and ignored."), TuplesKt.to("dooring", "Someone next to a parked car's street side: shifts up to 1 ft away (Lane Centering on) and eases off."), TuplesKt.to("sirens", "Needs the microphone (asked when you turn it on). Warns and adds room; it doesn't pull over by itself."), TuplesKt.to("school_bus", "First version: colour and flashing lights from the camera. It can't stop the car by itself (the comma never goes under 10 mph for advice): YOU stop."), TuplesKt.to("rain_camera", "First version: counts wiper sweeps across the picture; may miss slow intermittent wipers."), TuplesKt.to("horn", "Needs the microphone. It can't tell which side the honk came from."), TuplesKt.to("parking", "Spots seen in the last 20 minutes, near a destination set on the map."), TuplesKt.to("speed_cams", "Only the cameras someone put on OpenStreetMap (New York doesn't publish its list)."), TuplesKt.to("left_gap", "Shows the time only - you decide when to go."));
    private static final Set<String> CAMERA = SetsKt.setOf(new String[]{"ghosts", "shadows", "wheels", "lead_blinkers", "dooring", "green_countdown", "hybrid", "day_view", "model_disagree", "school_bus", "rain_camera", "parking", "actuated_lights", "double_parked", "left_gap"});
    private static final List<Idea> ALL = CollectionsKt.listOf(new Idea[]{new Idea("speed_bumps", "Speed bumps", "Bumps from the map (traffic calming) and every bump the car felt before (RTK + motion sensors, to the inch): slows down smoothly before each one instead of hitting it, and back up after.", "New York specials"), new Idea("nyc_cutins", "Highway cut-in guard", "Watches the cars beside you on the highway - drifting toward your lane, front wheels turning, blinker on - and remembers the spots where people cut in. Eases off early and opens the gap smoothly instead of braking hard when they dive in.", "New York specials"), new Idea("ghosts", "Object memory", "A person who walks behind a parked truck doesn't vanish: a \"ghost\" keeps moving at their last speed and the car slows where it could step out.", "Seeing what the camera can't"), new Idea("shadows", "Shadows at blind corners", "On sunny days a moving shadow on the ground at a corner means someone is coming before you can see them.", "Seeing what the camera can't"), new Idea("wheels", "Wheels before cars", "A neighbour's front wheels turning toward you - about half a second before the car itself moves over.", "Seeing what the camera can't"), new Idea("lead_blinkers", "Other cars' blinkers", "The car ahead signals right: it's about to brake - ease off early instead of late.", "Seeing what the camera can't"), new Idea("dooring", "Dooring zone", "Parked cars with someone in the driver's seat, brake lights on or a door opening: keep further from the parked cars for that stretch.", "Seeing what the camera can't"), new Idea("sirens", "Hearing sirens", "The microphones hear a siren and which side it's on: warns you early and gets ready to pull right.", "Seeing what the camera can't"), new Idea("lane_memory", "Your lanes from memory", "Every drive logged with RTK: after a few passes it knows where you really drive on each block. Faded lines or sun glare: lane centering follows your lane from memory.", "Memory of your streets"), new Idea("potholes", "Pothole memory", "The motion sensors feel every hole at an exact spot; next time it moves a little inside the lane to miss it, or slows.", "Memory of your streets"), new Idea("green_countdown", "Countdown to green", "New York lights run fixed cycles: it learns each light's timing from what it saw before, shows \"green in 12 s\" and coasts to arrive on green.", "Memory of your streets"), new Idea("spot_settings", "Settings per spot", "Where you take over or people cut in, it changes following distance, speed or lane offset just for those 500 ft.", "Memory of your streets"), new Idea("teach", "Teach mode", "Drive a tricky spot once with \"learn\" on (your driveway, a weird merge, a tight double-parked block): RTK keeps the path to the inch and the car follows it there next time.", "Memory of your streets"), new Idea("day_view", "Daytime view at night", "In the dark or in glare the tablet lays the same spot from a clear-day pass over the picture, so you see the lane lines anyway.", "Memory of your streets"), new Idea("crowd_line", "Follow the crowd's line", "No paint (snow, construction, fresh asphalt): the paths of the 3-4 cars ahead show where the lane really is.", "Learning from people"), new Idea("eyes", "Your eyes as a sensor", "The driver camera sees where you look: staring at something on the right makes the car careful there, or hints you're about to merge.", "Learning from people"), new Idea("takeover_why", "One tap after every takeover", "When you take over the tablet asks why (too close, swerved, wrong lane, braked late): every takeover becomes a labelled clip.", "Learning from people"), new Idea("voice_nudges", "Voice nudges", "\"Move over a bit\", \"take the next left\", \"stay behind the bus\" - a lane offset, a turn, a following distance.", "Learning from people"), new Idea("carsick", "Carsickness score", "The driver camera sees heads swaying: steering smoothness tuned until passengers' heads stop moving.", "Learning from people"), new Idea("unsure_slow", "Slow down when unsure", "When the model's lines and path get uncertain, a few mph less - like a person in fog or a work zone.", "Learning from people"), new Idea("self_labels", "Lane pictures that label themselves", "RTK knows your lane and the drive log every lane change: the lane pictures label themselves, no tapping.", "Training the models"), new Idea("model_disagree", "Two models side by side", "The big and small model run together; a clip is kept only where they disagree - the hard cases worth training on.", "Training the models"), new Idea("night_school", "Night school", "Parked at home, the eGPU trains on the day's hard clips (takeovers, disagreements, wrong lanes): in the morning a model fitted to your streets.", "Training the models"), new Idea("friends_fleet", "Friends fleet", "A few commas share one small map in a cloud folder - potholes, light timings, lanes, takeover spots - and every car learns from all of them.", "Training the models"), new Idea("school_bus", "School buses", "A yellow bus with its red lights flashing: slows down hard and tells you to STOP (New York law, both directions).", "From around the world"), new Idea("weather", "Weather offsets", "Rain, snow or fog (the comma's weather): more following room; in snow no faster than 40 mph.", "From around the world"), new Idea("rain_camera", "Rain from the camera", "No internet? The camera sees the wipers sweep: treated as rain (more room).", "From around the world"), new Idea("follow_flow", "Follow the flow", "On the highway never faster than the cars beside you (their typical speed + 3 mph).", "From around the world"), new Idea("far_lead", "Far lead", "Radar sees slow traffic far ahead before the model does: eases off early instead of braking late.", "From around the world"), new Idea("streak", "Takeover-free streak", "Miles since your last takeover and your best streak; a chip every 5 miles.", "From around the world"), new Idea("parking", "Open curb spots", "The camera notes open spaces between parked cars as you drive; near your destination it shows the closest one ahead.", "From around the world"), new Idea("speed_cams", "Speed cameras", "Speed cameras from the map: a chip, and no faster than the limit (+2) near one.", "From around the world"), new Idea("city_potholes", "The city's pothole list", "New York's open 311 pothole reports (last 45 days) join your pothole memory before you ever hit them.", "From around the world"), new Idea("actuated_lights", "Lights that change on demand", "Lights without a fixed cycle: how long their reds usually last, so the countdown still guesses.", "From around the world"), new Idea("sun_glare", "Sun glare", "The sun low and right in front of you (from where you are and the time): more room before the camera is blinded.", "From around the world"), new Idea("double_parked", "Double-parked memory", "Blocks and hours where your lane is usually blocked: a little slower there.", "From around the world"), new Idea("horn", "Horn detection", "The microphone hears a honk: a chip and a little more room.", "From around the world"), new Idea("left_gap", "Unprotected lefts", "Waiting to turn left: seconds until the next oncoming car reaches you.", "From around the world"), new Idea("hybrid", "Hybrid-smart driving", "The Sienna hybrid: light timing, hills from your RTK logs and stops on the route - coast and regen at the right moments for more mpg.", "The car itself")});

    /* compiled from: NewIdeas.kt */
    public static final class Idea {
        private final String desc;
        private final String id;
        private final String section;
        private final String title;

        public Idea(String id, String title, String desc, String section) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(desc, "desc");
            Intrinsics.checkNotNullParameter(section, "section");
            this.id = id;
            this.title = title;
            this.desc = desc;
            this.section = section;
        }

        public final String getDesc() {
            return this.desc;
        }

        public final String getId() {
            return this.id;
        }

        public final String getSection() {
            return this.section;
        }

        public final String getTitle() {
            return this.title;
        }
    }

    private NewIdeas() {
    }

    public final Set<String> getBUILT() {
        Iterable $this$map$iv = ALL;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Idea it = (Idea) item$iv$iv;
            destination$iv$iv.add(it.getId());
        }
        return CollectionsKt.toSet((List) destination$iv$iv);
    }

    public final Map<String, String> getFIRST() {
        return FIRST;
    }

    public final boolean needsCamera(UiSettings s) {
        Intrinsics.checkNotNullParameter(s, "s");
        Iterable $this$any$iv = CAMERA;
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            String it = (String) element$iv;
            if (s.get(INSTANCE.key(it))) {
                return true;
            }
        }
        return false;
    }

    public final String deviceName(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String string = Settings.Global.getString(ctx.getContentResolver(), "device_name");
        if (string == null) {
            string = Build.MODEL;
        }
        Intrinsics.checkNotNull(string);
        return StringsKt.take(new Regex("[^A-Za-z0-9_-]").replace(string, "_"), 32);
    }

    public final void importNightModel(final Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        new Thread(new Runnable() { // from class: com.starpilot.starview.NewIdeas$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NewIdeas.importNightModel$lambda$4(ctx);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void importNightModel$lambda$4(Context $ctx) {
        File file;
        try {
            File d = new File(Environment.getExternalStorageDirectory(), "Download/StarView/models");
            File[] listFiles = d.listFiles(new FileFilter() { // from class: com.starpilot.starview.NewIdeas$$ExternalSyntheticLambda1
                @Override // java.io.FileFilter
                public final boolean accept(File file2) {
                    boolean importNightModel$lambda$4$lambda$2;
                    importNightModel$lambda$4$lambda$2 = NewIdeas.importNightModel$lambda$4$lambda$2(file2);
                    return importNightModel$lambda$4$lambda$2;
                }
            });
            if (listFiles != null) {
                if (listFiles.length == 0) {
                    file = null;
                } else {
                    file = listFiles[0];
                    int lastIndex$iv = ArraysKt.getLastIndex(listFiles);
                    if (lastIndex$iv != 0) {
                        long maxValue$iv = file.lastModified();
                        int i$iv = 1;
                        if (1 <= lastIndex$iv) {
                            while (true) {
                                File file2 = listFiles[i$iv];
                                long v$iv = file2.lastModified();
                                if (maxValue$iv < v$iv) {
                                    file = file2;
                                    maxValue$iv = v$iv;
                                }
                                if (i$iv == lastIndex$iv) {
                                    break;
                                } else {
                                    i$iv++;
                                }
                            }
                        }
                    }
                }
                if (file == null) {
                    return;
                }
                File f = file;
                File mine = new File($ctx.getFilesDir(), "lane_count.lite");
                if (!mine.isFile() || mine.lastModified() < f.lastModified()) {
                    Uri fromFile = Uri.fromFile(f);
                    Intrinsics.checkNotNullExpressionValue(fromFile, "fromFile(...)");
                    LaneModelKt.importLaneModel($ctx, fromFile);
                }
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean importNightModel$lambda$4$lambda$2(File x) {
        String name = x.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        if (!StringsKt.startsWith(name, "lane_count", false)) {
            return false;
        }
        String name2 = x.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return StringsKt.endsWith(name2, ".lite", false);
    }

    public final List<Idea> getALL() {
        return ALL;
    }

    public final String key(String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        return "crazy_" + id;
    }

    public final boolean on(UiSettings s, String id) {
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(id, "id");
        return getBUILT().contains(id) && s.get(key(id));
    }
}
