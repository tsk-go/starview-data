package com.starpilot.starview;

import android.os.Handler;
import android.os.Looper;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: NavModel.kt */
public final class Nav {
    private static boolean busy;
    private static long busySinceMs;
    private static volatile JSONObject destination;
    private static String detailKey;
    private static long lastAskMs;
    private static volatile boolean loaded;
    private static volatile boolean offRoute;
    private static volatile Preview preview;
    private static int previewSeq;
    private static volatile JSONObject previewing;
    private static String routeKey;
    private static Line saidLine;
    private static volatile int selected;
    private static volatile String tileKey;
    private static volatile int version;
    public static final Nav INSTANCE = new Nav();
    private static volatile boolean supported = true;
    private static volatile String status = "";
    private static volatile boolean hasKey = true;
    private static volatile boolean enabled = true;
    private static volatile List<Route> routes = CollectionsKt.emptyList();
    private static volatile List<Line> lines = CollectionsKt.emptyList();
    private static volatile JSONObject options = new JSONObject();
    private static volatile JSONObject usage = new JSONObject();
    private static volatile String error = "";
    private static final ExecutorService parser = Executors.newSingleThreadExecutor();

    /* renamed from: main$delegate, reason: from kotlin metadata */
    private static final Lazy main = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Handler main_delegate$lambda$0;
            main_delegate$lambda$0 = Nav.main_delegate$lambda$0();
            return main_delegate$lambda$0;
        }
    });
    private static volatile String previewError = "";
    private static volatile int look = -1;
    private static volatile double along = Double.NaN;
    private static int hint = -1;
    private static final HashSet<String> announced = new HashSet<>();
    private static final HashSet<String> said = new HashSet<>();
    private static double lastAlong = Double.NaN;
    private static String lastStatus = "";
    private static String fasterSaid = "";

    /* compiled from: NavModel.kt */
    public static final class Route {
        private final List<String> classes;
        private final boolean closed;
        private final double distanceM;
        private final double durationS;
        private final int incidents;
        private final int index;
        private final double typicalS;
        private final String via;

        public Route(int index, double durationS, double distanceM, double typicalS, String via, List<String> classes, int incidents, boolean closed) {
            Intrinsics.checkNotNullParameter(via, "via");
            Intrinsics.checkNotNullParameter(classes, "classes");
            this.index = index;
            this.durationS = durationS;
            this.distanceM = distanceM;
            this.typicalS = typicalS;
            this.via = via;
            this.classes = classes;
            this.incidents = incidents;
            this.closed = closed;
        }

        public final double getDistanceM() {
            return this.distanceM;
        }

        public final double getDurationS() {
            return this.durationS;
        }

        public final int getIndex() {
            return this.index;
        }

        public final double getTypicalS() {
            return this.typicalS;
        }

        public final String getVia() {
            return this.via;
        }

        public final List<String> getClasses() {
            return this.classes;
        }

        public final boolean getClosed() {
            return this.closed;
        }

        public final int getIncidents() {
            return this.incidents;
        }
    }

    private Nav() {
    }

    /* compiled from: NavModel.kt */
    public static final class Step {
        private final double distanceM;
        private final double durationS;
        private final List<Lane> lanes;
        private final double lat;
        private final double lon;
        private final String modifier;
        private final String ref;
        private final String road;
        private final double startM;
        private final String text;
        private final String type;

        public Step(String text, String type, String modifier, String road, String ref, double startM, double distanceM, double durationS, double lat, double lon, List<Lane> lanes) {
            Intrinsics.checkNotNullParameter(text, "text");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(modifier, "modifier");
            Intrinsics.checkNotNullParameter(road, "road");
            Intrinsics.checkNotNullParameter(ref, "ref");
            Intrinsics.checkNotNullParameter(lanes, "lanes");
            this.text = text;
            this.type = type;
            this.modifier = modifier;
            this.road = road;
            this.ref = ref;
            this.startM = startM;
            this.distanceM = distanceM;
            this.durationS = durationS;
            this.lat = lat;
            this.lon = lon;
            this.lanes = lanes;
        }

        public /* synthetic */ Step(String str, String str2, String str3, String str4, String str5, double d, double d2, double d3, double d4, double d5, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, str3, str4, str5, d, d2, d3, d4, d5, (i & 1024) != 0 ? CollectionsKt.emptyList() : list);
        }

        public final String getModifier() {
            return this.modifier;
        }

        public final String getRef() {
            return this.ref;
        }

        public final String getRoad() {
            return this.road;
        }

        public final String getText() {
            return this.text;
        }

        public final String getType() {
            return this.type;
        }

        public final double getDistanceM() {
            return this.distanceM;
        }

        public final double getDurationS() {
            return this.durationS;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLon() {
            return this.lon;
        }

        public final double getStartM() {
            return this.startM;
        }

        public final List<Lane> getLanes() {
            return this.lanes;
        }
    }

    /* compiled from: NavModel.kt */
    public static final class Lane {
        private final List<String> arrows;
        private final boolean ok;
        private final String use;

        public Lane(List<String> arrows, boolean ok, String use) {
            Intrinsics.checkNotNullParameter(arrows, "arrows");
            Intrinsics.checkNotNullParameter(use, "use");
            this.arrows = arrows;
            this.ok = ok;
            this.use = use;
        }

        public final List<String> getArrows() {
            return this.arrows;
        }

        public final boolean getOk() {
            return this.ok;
        }

        public final String getUse() {
            return this.use;
        }
    }

    /* compiled from: NavModel.kt */
    public static final class Incident {
        private final boolean closed;
        private final String description;
        private final double endM;
        private final String id;
        private final String impact;
        private final double lat;
        private final double lon;
        private final double startM;
        private final String subType;
        private final String type;

        public Incident(String id, String type, String subType, String impact, String description, boolean closed, double startM, double endM, double lat, double lon) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(subType, "subType");
            Intrinsics.checkNotNullParameter(impact, "impact");
            Intrinsics.checkNotNullParameter(description, "description");
            this.id = id;
            this.type = type;
            this.subType = subType;
            this.impact = impact;
            this.description = description;
            this.closed = closed;
            this.startM = startM;
            this.endM = endM;
            this.lat = lat;
            this.lon = lon;
        }

        public final String getDescription() {
            return this.description;
        }

        public final String getId() {
            return this.id;
        }

        public final String getImpact() {
            return this.impact;
        }

        public final String getSubType() {
            return this.subType;
        }

        public final String getType() {
            return this.type;
        }

        public final boolean getClosed() {
            return this.closed;
        }

        public final double getEndM() {
            return this.endM;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLon() {
            return this.lon;
        }

        public final double getStartM() {
            return this.startM;
        }
    }

    /* compiled from: NavModel.kt */
    public static final class Line {
        private final double[] cum;
        private final List<Incident> incidents;
        private final double[] mx;
        private final double[] my;
        private final List<Step> steps;
        private final double[] trafficFrom;
        private final int[] trafficLevel;
        private final double[] trafficTo;
        private final double[] voiceAt;
        private final List<String> voiceText;

        public Line(double[] mx, double[] my, double[] cum, double[] trafficFrom, double[] trafficTo, int[] trafficLevel, List<Incident> incidents, List<Step> steps, double[] voiceAt, List<String> voiceText) {
            Intrinsics.checkNotNullParameter(mx, "mx");
            Intrinsics.checkNotNullParameter(my, "my");
            Intrinsics.checkNotNullParameter(cum, "cum");
            Intrinsics.checkNotNullParameter(trafficFrom, "trafficFrom");
            Intrinsics.checkNotNullParameter(trafficTo, "trafficTo");
            Intrinsics.checkNotNullParameter(trafficLevel, "trafficLevel");
            Intrinsics.checkNotNullParameter(incidents, "incidents");
            Intrinsics.checkNotNullParameter(steps, "steps");
            Intrinsics.checkNotNullParameter(voiceAt, "voiceAt");
            Intrinsics.checkNotNullParameter(voiceText, "voiceText");
            this.mx = mx;
            this.my = my;
            this.cum = cum;
            this.trafficFrom = trafficFrom;
            this.trafficTo = trafficTo;
            this.trafficLevel = trafficLevel;
            this.incidents = incidents;
            this.steps = steps;
            this.voiceAt = voiceAt;
            this.voiceText = voiceText;
        }

        public /* synthetic */ Line(double[] dArr, double[] dArr2, double[] dArr3, double[] dArr4, double[] dArr5, int[] iArr, List list, List list2, double[] dArr6, List list3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(dArr, dArr2, dArr3, dArr4, dArr5, iArr, list, list2, (i & 256) != 0 ? new double[0] : dArr6, (i & 512) != 0 ? CollectionsKt.emptyList() : list3);
        }

        public final double[] getCum() {
            return this.cum;
        }

        public final double[] getMx() {
            return this.mx;
        }

        public final double[] getMy() {
            return this.my;
        }

        public final double[] getTrafficFrom() {
            return this.trafficFrom;
        }

        public final double[] getTrafficTo() {
            return this.trafficTo;
        }

        public final List<Incident> getIncidents() {
            return this.incidents;
        }

        public final List<Step> getSteps() {
            return this.steps;
        }

        public final int[] getTrafficLevel() {
            return this.trafficLevel;
        }

        public final double[] getVoiceAt() {
            return this.voiceAt;
        }

        public final List<String> getVoiceText() {
            return this.voiceText;
        }

        public final int getSize() {
            return this.mx.length;
        }

        public final double getLength() {
            if (this.cum.length == 0) {
                return 0.0d;
            }
            return this.cum[this.cum.length - 1];
        }

        public final int indexAt(double d) {
            int lo = 0;
            int hi = this.cum.length - 1;
            while (lo < hi) {
                int m = (lo + hi) / 2;
                if (this.cum[m] < d) {
                    lo = m + 1;
                } else {
                    hi = m;
                }
            }
            return lo;
        }

        public final int levelAt(double d) {
            int lo = 0;
            int hi = this.trafficFrom.length - 1;
            int best = -1;
            while (lo <= hi) {
                int m = (lo + hi) / 2;
                if (this.trafficFrom[m] <= d) {
                    best = m;
                    lo = m + 1;
                } else {
                    hi = m - 1;
                }
            }
            if (best < 0 || d > this.trafficTo[best]) {
                return 0;
            }
            return this.trafficLevel[best];
        }

        public static /* synthetic */ Triple locate$default(Line line, double d, double d2, int i, int i2, Object obj) {
            if ((i2 & 4) != 0) {
                i = -1;
            }
            return line.locate(d, d2, i);
        }

        public final Triple<Double, Double, Integer> locate(double lat, double lon, int hint) {
            double x = Nav.INSTANCE.mercX(lon);
            double y = Nav.INSTANCE.mercY(lat);
            double mpu = Math.cos(Math.toRadians(lat)) * 4.0075016686E7d;
            if (getSize() < 2) {
                return new Triple<>(Double.valueOf(0.0d), Double.valueOf(Double.MAX_VALUE), 0);
            }
            if (hint >= 0) {
                Triple near = locate$scan(this, x, y, mpu, RangesKt.coerceAtLeast(hint - 60, 0), RangesKt.coerceAtMost(hint + 400, getSize() - 1));
                if (((Number) near.getSecond()).doubleValue() < 60.0d) {
                    return near;
                }
            }
            return locate$scan(this, x, y, mpu, 0, getSize() - 1);
        }

        private static final Triple<Double, Double, Integer> locate$scan(Line this$0, double x, double y, double mpu, int from, int to) {
            double bestAlong = Double.MAX_VALUE;
            double bestAlong2 = 0.0d;
            int bestI = from;
            for (int i = from; i < to; i++) {
                double ax = this$0.mx[i];
                double ay = this$0.my[i];
                double dx = this$0.mx[i + 1] - ax;
                double dy = this$0.my[i + 1] - ay;
                double l2 = (dx * dx) + (dy * dy);
                double t = l2 > 0.0d ? RangesKt.coerceIn((((x - ax) * dx) + ((y - ay) * dy)) / l2, 0.0d, 1.0d) : 0.0d;
                double px = (ax + (t * dx)) - x;
                double py = (ay + (t * dy)) - y;
                double d = (px * px) + (py * py);
                if (d < bestAlong) {
                    double bestAlong3 = this$0.cum[i] + ((this$0.cum[i + 1] - this$0.cum[i]) * t);
                    bestI = i;
                    bestAlong2 = bestAlong3;
                    bestAlong = d;
                }
            }
            return new Triple<>(Double.valueOf(bestAlong2), Double.valueOf(Math.sqrt(bestAlong) * mpu), Integer.valueOf(bestI));
        }
    }

    public final boolean getSupported() {
        return supported;
    }

    public final boolean getLoaded() {
        return loaded;
    }

    public final String getStatus() {
        return status;
    }

    public final boolean getHasKey() {
        return hasKey;
    }

    public final boolean getEnabled() {
        return enabled;
    }

    public final JSONObject getDestination() {
        return destination;
    }

    public final int getSelected() {
        return selected;
    }

    public final List<Route> getRoutes() {
        return routes;
    }

    public final List<Line> getLines() {
        return lines;
    }

    public final JSONObject getOptions() {
        return options;
    }

    public final JSONObject getUsage() {
        return usage;
    }

    public final String getError() {
        return error;
    }

    public final String getTileKey() {
        return tileKey;
    }

    public final int getVersion() {
        return version;
    }

    private final Handler getMain() {
        return (Handler) main.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Handler main_delegate$lambda$0() {
        return new Handler(Looper.getMainLooper());
    }

    public final Line getLine() {
        return (Line) CollectionsKt.getOrNull(lines, selected);
    }

    public final Route getRoute() {
        return (Route) CollectionsKt.getOrNull(routes, selected);
    }

    public final boolean getActive() {
        return destination != null;
    }

    /* compiled from: NavModel.kt */
    public static final class Preview {
        private final JSONObject destination;
        private final String id;
        private final List<Line> lines;
        private final List<Route> routes;

        public Preview(String id, JSONObject destination, List<Route> routes, List<Line> lines) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(destination, "destination");
            Intrinsics.checkNotNullParameter(routes, "routes");
            Intrinsics.checkNotNullParameter(lines, "lines");
            this.id = id;
            this.destination = destination;
            this.routes = routes;
            this.lines = lines;
        }

        public final JSONObject getDestination() {
            return this.destination;
        }

        public final String getId() {
            return this.id;
        }

        public final List<Line> getLines() {
            return this.lines;
        }

        public final List<Route> getRoutes() {
            return this.routes;
        }
    }

    public final Preview getPreview() {
        return preview;
    }

    public final JSONObject getPreviewing() {
        return previewing;
    }

    public final String getPreviewError() {
        return previewError;
    }

    public final int getLook() {
        return look;
    }

    public final void setLook(int i) {
        look = i;
    }

    public final boolean getInPreview() {
        return previewing != null;
    }

    public final List<Route> getShownRoutes() {
        List<Route> routes2;
        if (!getInPreview()) {
            return routes;
        }
        Preview preview2 = preview;
        return (preview2 == null || (routes2 = preview2.getRoutes()) == null) ? CollectionsKt.emptyList() : routes2;
    }

    public final List<Line> getShownLines() {
        List<Line> lines2;
        if (!getInPreview()) {
            return lines;
        }
        Preview preview2 = preview;
        return (preview2 == null || (lines2 = preview2.getLines()) == null) ? CollectionsKt.emptyList() : lines2;
    }

    public final int getShownSelected() {
        if (look >= 0) {
            return look;
        }
        if (getInPreview()) {
            return 0;
        }
        return selected;
    }

    public final Line getShownLine() {
        return (Line) CollectionsKt.getOrNull(getShownLines(), getShownSelected());
    }

    public final Route getShownRoute() {
        Object element$iv;
        Iterable $this$firstOrNull$iv = getShownRoutes();
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                Route it2 = (Route) element$iv;
                if (it2.getIndex() == INSTANCE.getShownSelected()) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        return (Route) element$iv;
    }

    public final JSONObject getShownDestination() {
        JSONObject jSONObject = previewing;
        return jSONObject == null ? destination : jSONObject;
    }

    public final void startPreview(DataClient data, Map<String, ? extends Object> place, final JSONObject shown, Map<String, ? extends Object> origin, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(place, "place");
        Intrinsics.checkNotNullParameter(shown, "shown");
        Intrinsics.checkNotNullParameter(then, "then");
        previewSeq++;
        final int seq = previewSeq;
        previewing = shown;
        preview = null;
        look = -1;
        previewError = "";
        version++;
        HashMap args = new HashMap(place);
        if (origin != null) {
            args.put("origin", new JSONObject(origin));
        }
        data.request("nav_preview", args, new Function1() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit startPreview$lambda$5;
                startPreview$lambda$5 = Nav.startPreview$lambda$5(seq, then, shown, (JSONObject) obj);
                return startPreview$lambda$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startPreview$lambda$5(final int $seq, final Function1 $then, final JSONObject $shown, final JSONObject j) {
        Intrinsics.checkNotNullParameter(j, "j");
        if ($seq != previewSeq) {
            return Unit.INSTANCE;
        }
        if (!j.optBoolean("ok")) {
            String optString = j.optString("error");
            if (optString.length() == 0) {
                optString = "no answer";
            }
            String e = optString;
            Intrinsics.checkNotNull(e);
            if (StringsKt.contains((CharSequence) e, (CharSequence) "unknown action", false)) {
                previewing = null;
            } else {
                previewError = e;
            }
            version++;
            $then.invoke(e);
            return Unit.INSTANCE;
        }
        parser.execute(new Runnable() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                Nav.startPreview$lambda$5$lambda$4(j, $shown, $seq, $then);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startPreview$lambda$5$lambda$4(JSONObject $j, JSONObject $shown, final int $seq, final Function1 $then) {
        Preview preview2;
        try {
            Pair<List<Route>, List<Line>> parseRoutes = INSTANCE.parseRoutes($j);
            List rs = parseRoutes.component1();
            List ls = parseRoutes.component2();
            String optString = $j.optString("previewId");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            JSONObject optJSONObject = $j.optJSONObject("destination");
            if (optJSONObject == null) {
                optJSONObject = $shown;
            }
            preview2 = new Preview(optString, optJSONObject, rs, ls);
        } catch (Exception e) {
            preview2 = null;
        }
        final Preview p = preview2;
        INSTANCE.getMain().post(new Runnable() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                Nav.startPreview$lambda$5$lambda$4$lambda$3($seq, p, $then);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startPreview$lambda$5$lambda$4$lambda$3(int $seq, Preview $p, Function1 $then) {
        if ($seq != previewSeq) {
            return;
        }
        if ($p == null || $p.getRoutes().isEmpty()) {
            previewError = "No route found";
        } else {
            preview = $p;
            previewing = $p.getDestination();
        }
        version++;
        $then.invoke(($p == null || $p.getRoutes().isEmpty()) ? previewError : null);
    }

    public final void go(final DataClient data, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(then, "then");
        Preview p = preview;
        if (p == null) {
            then.invoke("No routes yet");
        } else {
            int index = getShownSelected();
            data.request("nav_go", MapsKt.mapOf(TuplesKt.to("previewId", p.getId()), TuplesKt.to("index", Integer.valueOf(index))), new Function1() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit go$lambda$7;
                    go$lambda$7 = Nav.go$lambda$7(then, data, (JSONObject) obj);
                    return go$lambda$7;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit go$lambda$7(Function1 $then, DataClient $data, JSONObject j) {
        Intrinsics.checkNotNullParameter(j, "j");
        if (!j.optBoolean("ok")) {
            String optString = j.optString("error");
            if (optString.length() == 0) {
                optString = "no answer";
            }
            $then.invoke(optString);
            return Unit.INSTANCE;
        }
        previewSeq++;
        preview = null;
        previewing = null;
        look = -1;
        previewError = "";
        INSTANCE.newDestination($data);
        $then.invoke(null);
        return Unit.INSTANCE;
    }

    public final void cancelPreview() {
        previewSeq++;
        preview = null;
        previewing = null;
        look = -1;
        previewError = "";
        version++;
    }

    public static /* synthetic */ void poll$default(Nav nav, DataClient dataClient, boolean z, long j, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            z2 = false;
        }
        nav.poll(dataClient, z, j, z2);
    }

    public final void poll(DataClient data, boolean mapOpen, long nowMs, boolean force) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.getConnected()) {
            if (busy && nowMs - busySinceMs < 10000) {
                return;
            }
            long every = (getActive() || mapOpen) ? 2000L : 10000L;
            if (force || nowMs - lastAskMs >= every) {
                lastAskMs = nowMs;
                busy = true;
                busySinceMs = nowMs;
                HashMap args = new HashMap();
                String it = routeKey;
                if (it != null) {
                    args.put("routeKey", it);
                }
                String it2 = detailKey;
                if (it2 != null) {
                    args.put("detailKey", it2);
                }
                data.request("nav_state", args, new Function1() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit poll$lambda$14;
                        poll$lambda$14 = Nav.poll$lambda$14((JSONObject) obj);
                        return poll$lambda$14;
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit poll$lambda$14(final JSONObject j) {
        Intrinsics.checkNotNullParameter(j, "j");
        if (!j.optBoolean("ok")) {
            busy = false;
            String e = j.optString("error");
            Intrinsics.checkNotNull(e);
            if (StringsKt.contains((CharSequence) e, (CharSequence) "unknown action", false)) {
                supported = false;
                loaded = true;
                version++;
            } else {
                if ((e.length() > 0) && !Intrinsics.areEqual(e, "timeout")) {
                    error = e;
                    version++;
                }
            }
            return Unit.INSTANCE;
        }
        supported = true;
        error = "";
        if (!j.optBoolean("unchanged")) {
            parser.execute(new Runnable() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Nav.poll$lambda$14$lambda$13(j);
                }
            });
            return Unit.INSTANCE;
        }
        busy = false;
        if (!loaded) {
            loaded = true;
            version++;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void poll$lambda$14$lambda$13(final JSONObject $j) {
        Function0 parsed;
        try {
            parsed = INSTANCE.parse($j);
        } catch (Exception e) {
            parsed = null;
        }
        final var parsed$f = parsed;
        INSTANCE.getMain().post(new Runnable() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Nav.poll$lambda$14$lambda$13$lambda$12(parsed$f, $j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void poll$lambda$14$lambda$13$lambda$12(Function0 $parsed, JSONObject $j) {
        boolean z;
        boolean z2 = false;
        busy = false;
        if ($parsed != null) {
            $parsed.invoke();
            String optString = $j.optString("routeKey");
            if (optString.length() != 0) {
                z = false;
            } else {
                z = true;
            }
            String str = null;
            if (z) {
                optString = null;
            }
            routeKey = optString;
            if ($j.has("details")) {
                String optString2 = $j.optString("detailKey");
                if (optString2.length() == 0) {
                    z2 = true;
                }
                if (!z2) {
                    str = optString2;
                }
                detailKey = str;
            }
        }
        loaded = true;
        version++;
    }

    public final void refresh(DataClient data) {
        Intrinsics.checkNotNullParameter(data, "data");
        routeKey = null;
        lastAskMs = 0L;
        if (busy) {
            return;
        }
        poll(data, true, System.currentTimeMillis(), true);
    }

    public final void newDestination(DataClient data) {
        Intrinsics.checkNotNullParameter(data, "data");
        routes = CollectionsKt.emptyList();
        lines = CollectionsKt.emptyList();
        detailKey = null;
        version++;
        refresh(data);
    }

    public final void forget() {
        routeKey = null;
        detailKey = null;
        destination = null;
        routes = CollectionsKt.emptyList();
        lines = CollectionsKt.emptyList();
        status = "";
        loaded = false;
        version++;
    }

    public final Pair<List<Route>, List<Line>> parseRoutes(JSONObject j) {
        Collection destination$iv$iv;
        JSONArray ra;
        List emptyList;
        Intrinsics.checkNotNullParameter(j, "j");
        ArrayList rs = new ArrayList();
        JSONArray ra2 = j.optJSONArray("routes");
        if (ra2 == null) {
            ra2 = new JSONArray();
        }
        int i = 0;
        int length = ra2.length();
        while (i < length) {
            JSONObject o = ra2.optJSONObject(i);
            if (o == null) {
                ra = ra2;
            } else {
                int optInt = o.optInt("index", i);
                double optDouble = o.optDouble("durationSeconds", 0.0d);
                double optDouble2 = o.optDouble("distanceMeters", 0.0d);
                double optDouble3 = o.isNull("typicalSeconds") ? Double.NaN : o.optDouble("typicalSeconds", Double.NaN);
                String optString = o.optString("via");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                JSONArray a = o.optJSONArray("classes");
                if (a != null) {
                    Iterable $this$map$iv = RangesKt.until(0, a.length());
                    Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    Iterator<Integer> it = $this$map$iv.iterator();
                    while (it.hasNext()) {
                        int item$iv$iv = ((IntIterator) it).nextInt();
                        destination$iv$iv2.add(a.optString(item$iv$iv));
                        ra2 = ra2;
                    }
                    ra = ra2;
                    emptyList = (List) destination$iv$iv2;
                } else {
                    ra = ra2;
                    emptyList = CollectionsKt.emptyList();
                }
                rs.add(new Route(optInt, optDouble, optDouble2, optDouble3, optString, emptyList, o.optInt("incidents", 0), o.optBoolean("closed", false)));
            }
            i++;
            ra2 = ra;
        }
        JSONArray da = j.optJSONArray("details");
        if (da != null) {
            Iterable $this$map$iv2 = RangesKt.until(0, da.length());
            Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            Iterator<Integer> it2 = $this$map$iv2.iterator();
            while (it2.hasNext()) {
                int item$iv$iv2 = ((IntIterator) it2).nextInt();
                JSONObject it3 = da.optJSONObject(item$iv$iv2);
                destination$iv$iv3.add(it3 != null ? INSTANCE.parseLine(it3) : null);
            }
            destination$iv$iv = (List) destination$iv$iv3;
        } else {
            destination$iv$iv = CollectionsKt.emptyList();
        }
        List ls = (List) destination$iv$iv;
        return TuplesKt.to((List<Route>) rs, (List<Line>) ls);
    }

    private final Function0<Unit> parse(JSONObject j) {
        Pair<List<Route>, List<Line>> parseRoutes = parseRoutes(j);
        final List rs = parseRoutes.component1();
        List parsedLines = parseRoutes.component2();
        String str = null;
        final List newLines = j.has("details") ? parsedLines : null;
        final JSONObject dest = j.optJSONObject("destination");
        final String st = j.optString("status");
        final int sel = j.optInt("selectedRoute", 0);
        JSONObject optJSONObject = j.optJSONObject("options");
        if (optJSONObject == null) {
            optJSONObject = new JSONObject();
        }
        final JSONObject opts = optJSONObject;
        JSONObject optJSONObject2 = j.optJSONObject("usage");
        if (optJSONObject2 == null) {
            optJSONObject2 = new JSONObject();
        }
        final JSONObject use = optJSONObject2;
        final boolean key = j.optBoolean("hasKey", true);
        final boolean en = j.optBoolean("enabled", true);
        String optString = j.optString("tileKey");
        if (!(optString.length() == 0)) {
            str = optString;
        }
        final String tk = str;
        return new Function0() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit parse$lambda$21;
                parse$lambda$21 = Nav.parse$lambda$21(tk, rs, dest, st, sel, opts, use, key, en, newLines);
                return parse$lambda$21;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit parse$lambda$21(String $tk, List $rs, JSONObject $dest, String $st, int $sel, JSONObject $opts, JSONObject $use, boolean $key, boolean $en, List $newLines) {
        tileKey = $tk;
        routes = $rs;
        destination = $dest;
        status = $st;
        selected = $sel;
        options = $opts;
        usage = $use;
        hasKey = $key;
        enabled = $en;
        if ($newLines != null) {
            lines = $newLines;
        } else if ($rs.isEmpty()) {
            lines = CollectionsKt.emptyList();
        }
        if ($dest == null) {
            lines = CollectionsKt.emptyList();
            announced.clear();
            detailKey = null;
        }
        return Unit.INSTANCE;
    }

    public final Line parseLine(JSONObject o) {
        double[] from;
        double[] to;
        int[] lv;
        int n;
        double[] mx;
        ArrayList inc;
        JSONArray a;
        int i;
        ArrayList inc2;
        int i2;
        List lanes;
        ArrayList inc3;
        JSONArray la;
        int i3;
        Iterable $this$mapTo$iv$iv;
        List emptyList;
        int i4;
        JSONArray a2;
        double[] pts;
        double[] mx2;
        int[] lv2;
        double[] to2;
        double[] from2;
        int n2;
        int i5;
        double[] my;
        double[] cum;
        Intrinsics.checkNotNullParameter(o, "o");
        String optString = o.optString("polyline");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        double[] pts2 = decodePolyline(optString);
        if (pts2.length < 4) {
            return null;
        }
        int n3 = pts2.length / 2;
        double[] mx3 = new double[n3];
        double[] my2 = new double[n3];
        double[] cum2 = new double[n3];
        int i6 = 0;
        while (i6 < n3) {
            mx3[i6] = mercX(pts2[(i6 * 2) + 1]);
            my2[i6] = mercY(pts2[i6 * 2]);
            if (i6 > 0) {
                i5 = i6;
                my = my2;
                cum = cum2;
                cum[i5] = cum2[i6 - 1] + metres(pts2[(i6 * 2) - 2], pts2[(i6 * 2) - 1], pts2[i6 * 2], pts2[(i6 * 2) + 1]);
            } else {
                i5 = i6;
                my = my2;
                cum = cum2;
            }
            i6 = i5 + 1;
            my2 = my;
            cum2 = cum;
        }
        double[] my3 = my2;
        double[] cum3 = cum2;
        JSONArray ta = o.optJSONArray("traffic");
        if (ta == null) {
            ta = new JSONArray();
        }
        double[] from3 = new double[ta.length()];
        double[] to3 = new double[ta.length()];
        int[] lv3 = new int[ta.length()];
        int length = ta.length();
        for (int i7 = 0; i7 < length; i7++) {
            JSONArray r = ta.optJSONArray(i7);
            if (r != null) {
                from3[i7] = r.optDouble(0);
                to3[i7] = r.optDouble(1);
                lv3[i7] = r.optInt(2);
            }
        }
        ArrayList inc4 = new ArrayList();
        JSONArray a3 = o.optJSONArray("incidents");
        if (a3 != null) {
            int length2 = a3.length();
            int i8 = 0;
            while (i8 < length2) {
                JSONObject x = a3.optJSONObject(i8);
                if (x == null) {
                    i4 = length2;
                    from2 = from3;
                    to2 = to3;
                    lv2 = lv3;
                    a2 = a3;
                    pts = pts2;
                    n2 = n3;
                    mx2 = mx3;
                } else {
                    i4 = length2;
                    a2 = a3;
                    pts = pts2;
                    String optString2 = x.optString("id");
                    Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                    mx2 = mx3;
                    String optString3 = x.optString("type");
                    Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
                    lv2 = lv3;
                    String optString4 = x.optString("subType");
                    Intrinsics.checkNotNullExpressionValue(optString4, "optString(...)");
                    to2 = to3;
                    String optString5 = x.optString("impact");
                    Intrinsics.checkNotNullExpressionValue(optString5, "optString(...)");
                    from2 = from3;
                    String optString6 = x.optString("description");
                    Intrinsics.checkNotNullExpressionValue(optString6, "optString(...)");
                    n2 = n3;
                    inc4.add(new Incident(optString2, optString3, optString4, optString5, optString6, x.optBoolean("closed"), x.optDouble("startMeters"), x.optDouble("endMeters"), x.optDouble("latitude"), x.optDouble("longitude")));
                }
                i8++;
                length2 = i4;
                mx3 = mx2;
                a3 = a2;
                pts2 = pts;
                lv3 = lv2;
                to3 = to2;
                from3 = from2;
                n3 = n2;
            }
            from = from3;
            to = to3;
            lv = lv3;
            n = n3;
            mx = mx3;
        } else {
            from = from3;
            to = to3;
            lv = lv3;
            n = n3;
            mx = mx3;
        }
        ArrayList steps = new ArrayList();
        JSONArray a4 = o.optJSONArray("steps");
        if (a4 != null) {
            int i9 = 0;
            int i10 = 0;
            int length3 = a4.length();
            while (i10 < length3) {
                JSONObject x2 = a4.optJSONObject(i10);
                if (x2 == null) {
                    a = a4;
                    i = i9;
                    inc2 = inc4;
                    i2 = length3;
                } else {
                    JSONArray la2 = x2.optJSONArray("lanes");
                    if (la2 != null) {
                        int i11 = 0;
                        a = a4;
                        Iterable $this$mapNotNull$iv = RangesKt.until(0, la2.length());
                        i = i9;
                        Collection destination$iv$iv = new ArrayList();
                        Iterator<Integer> it = $this$mapNotNull$iv.iterator();
                        while (it.hasNext()) {
                            int element$iv$iv$iv = ((IntIterator) it).nextInt();
                            Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                            JSONObject optJSONObject = la2.optJSONObject(element$iv$iv$iv);
                            if (optJSONObject != null) {
                                destination$iv$iv.add(optJSONObject);
                            }
                            $this$mapNotNull$iv = $this$mapNotNull$iv2;
                        }
                        Iterable $this$map$iv = (List) destination$iv$iv;
                        i2 = length3;
                        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        Iterable $this$mapTo$iv$iv2 = $this$map$iv;
                        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
                            JSONObject ln = (JSONObject) item$iv$iv;
                            Iterable $this$map$iv2 = $this$map$iv;
                            JSONArray d = ln.optJSONArray("d");
                            if (d != null) {
                                la = la2;
                                i3 = i11;
                                Iterable $this$map$iv3 = RangesKt.until(0, d.length());
                                inc3 = inc4;
                                $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                                Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
                                Iterator<Integer> it2 = $this$map$iv3.iterator();
                                while (it2.hasNext()) {
                                    int item$iv$iv2 = ((IntIterator) it2).nextInt();
                                    destination$iv$iv3.add(d.optString(item$iv$iv2));
                                }
                                emptyList = (List) destination$iv$iv3;
                            } else {
                                inc3 = inc4;
                                la = la2;
                                i3 = i11;
                                $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                                emptyList = CollectionsKt.emptyList();
                            }
                            boolean optBoolean = ln.optBoolean("ok");
                            String optString7 = ln.optString("use");
                            Intrinsics.checkNotNullExpressionValue(optString7, "optString(...)");
                            destination$iv$iv2.add(new Lane(emptyList, optBoolean, optString7));
                            inc4 = inc3;
                            $this$map$iv = $this$map$iv2;
                            la2 = la;
                            i11 = i3;
                            $this$mapTo$iv$iv2 = $this$mapTo$iv$iv;
                        }
                        inc2 = inc4;
                        lanes = (List) destination$iv$iv2;
                    } else {
                        a = a4;
                        i = i9;
                        inc2 = inc4;
                        i2 = length3;
                        lanes = CollectionsKt.emptyList();
                    }
                    String optString8 = x2.optString("text");
                    Intrinsics.checkNotNullExpressionValue(optString8, "optString(...)");
                    String optString9 = x2.optString("type");
                    Intrinsics.checkNotNullExpressionValue(optString9, "optString(...)");
                    String optString10 = x2.optString("modifier");
                    Intrinsics.checkNotNullExpressionValue(optString10, "optString(...)");
                    String optString11 = x2.optString("road");
                    Intrinsics.checkNotNullExpressionValue(optString11, "optString(...)");
                    String optString12 = x2.optString("ref");
                    Intrinsics.checkNotNullExpressionValue(optString12, "optString(...)");
                    steps.add(new Step(optString8, optString9, optString10, optString11, optString12, x2.optDouble("startMeters"), x2.optDouble("distanceMeters"), x2.optDouble("durationSeconds"), x2.optDouble("latitude"), x2.optDouble("longitude"), lanes));
                }
                i10++;
                a4 = a;
                i9 = i;
                length3 = i2;
                inc4 = inc2;
            }
            inc = inc4;
        } else {
            inc = inc4;
        }
        Step it3 = (Step) CollectionsKt.lastOrNull((List) steps);
        double total = it3 != null ? it3.getStartM() + it3.getDistanceM() : 0.0d;
        if (total > 1.0d && cum3[n - 1] > 1.0d) {
            double k = total / cum3[n - 1];
            int i12 = 0;
            while (true) {
                int n4 = n;
                if (i12 >= n4) {
                    break;
                }
                cum3[i12] = cum3[i12] * k;
                i12++;
                n = n4;
            }
        }
        JSONArray va = o.optJSONArray("voice");
        if (va == null) {
            va = new JSONArray();
        }
        ArrayList vAt = new ArrayList();
        ArrayList vText = new ArrayList();
        int length4 = va.length();
        for (int i13 = 0; i13 < length4; i13++) {
            JSONArray r2 = va.optJSONArray(i13);
            if (r2 != null) {
                String t = r2.optString(1);
                Intrinsics.checkNotNull(t);
                if (t.length() > 0) {
                    vAt.add(Double.valueOf(r2.optDouble(0)));
                    vText.add(t);
                }
            }
        }
        return new Line(mx, my3, cum3, from, to, lv, inc, steps, CollectionsKt.toDoubleArray(vAt), vText);
    }

    /* VF: from Vineflower (jadx: ) */
    public static void choose$default(Nav var0, DataClient var1, int var2, Function1 var3, int var4, Object var5) {
        if ((var4 & 4) != 0) {
            var3 = null;
        }

        var0.choose(var1, var2, var3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit choose$lambda$30(int $index, DataClient $data, Function1 $then, JSONObject j) {
        Intrinsics.checkNotNullParameter(j, "j");
        if (j.optBoolean("ok")) {
            selected = $index;
            version++;
            INSTANCE.refresh($data);
        }
        if ($then != null) {
            $then.invoke(j.optBoolean("ok") ? null : j.optString("error"));
        }
        return Unit.INSTANCE;
    }

    public final void choose(final DataClient data, final int index, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(data, "data");
        data.request("nav_route", MapsKt.mapOf(TuplesKt.to("index", Integer.valueOf(index))), new Function1() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit choose$lambda$30;
                choose$lambda$30 = Nav.choose$lambda$30(index, data, then, (JSONObject) obj);
                return choose$lambda$30;
            }
        });
    }

    /* VF: from Vineflower (jadx: ) */
    public static void setOptions$default(Nav var0, DataClient var1, JSONObject var2, Function1 var3, int var4, Object var5) {
        if ((var4 & 4) != 0) {
            var3 = null;
        }

        var0.setOptions(var1, var2, var3);
    }

    public final void setOptions(final DataClient data, JSONObject patch, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(patch, "patch");
        data.request("nav_options", MapsKt.mapOf(TuplesKt.to("options", patch)), new Function1() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit options$lambda$32;
                options$lambda$32 = Nav.setOptions$lambda$32(data, then, (JSONObject) obj);
                return options$lambda$32;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setOptions$lambda$32(DataClient $data, Function1 $then, JSONObject j) {
        Intrinsics.checkNotNullParameter(j, "j");
        if (j.optBoolean("ok")) {
            JSONObject it = j.optJSONObject("options");
            if (it != null) {
                options = it;
            }
            version++;
            INSTANCE.refresh($data);
        }
        if ($then != null) {
            $then.invoke(j.optBoolean("ok") ? null : j.optString("error"));
        }
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    public static void cancel$default(Nav var0, DataClient var1, Function1 var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = null;
        }

        var0.cancel(var1, var2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit cancel$lambda$33(DataClient $data, Function1 $then, JSONObject j) {
        Intrinsics.checkNotNullParameter(j, "j");
        if (j.optBoolean("ok")) {
            destination = null;
            routes = CollectionsKt.emptyList();
            lines = CollectionsKt.emptyList();
            detailKey = null;
            version++;
            INSTANCE.refresh($data);
        }
        if ($then != null) {
            $then.invoke(j.optBoolean("ok") ? null : j.optString("error"));
        }
        return Unit.INSTANCE;
    }

    public final void cancel(final DataClient data, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(data, "data");
        data.request("nav_cancel", kotlin.collections.MapsKt.emptyMap(), new Function1() { // from class: com.starpilot.starview.Nav$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit cancel$lambda$33;
                cancel$lambda$33 = Nav.cancel$lambda$33(data, then, (JSONObject) obj);
                return cancel$lambda$33;
            }
        });
    }

    public final double getAlong() {
        return along;
    }

    public final boolean getOffRoute() {
        return offRoute;
    }

    public final String voiceMode(UiSettings s) {
        Intrinsics.checkNotNullParameter(s, "s");
        String it = s.getStr("nav_voice", "all");
        return SetsKt.setOf(new String[]{"all", "alerts", "off"}).contains(it) ? it : "all";
    }

    public final void track(double lat, double lon, float vEgo, boolean metric, boolean speak, String mode) {
        int i;
        Object element$iv;
        int i2;
        Intrinsics.checkNotNullParameter(mode, "mode");
        if (!Intrinsics.areEqual(status, lastStatus)) {
            if (speak && Intrinsics.areEqual(mode, "all") && Intrinsics.areEqual(lastStatus, "guiding") && Intrinsics.areEqual(status, "routing") && vEgo > 3.0f) {
                Speech.INSTANCE.speak("Rerouting", false);
            }
            lastStatus = status;
        }
        Line l = getLine();
        if (l != null) {
            if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
                if (!((Double.isInfinite(lon) || Double.isNaN(lon)) ? false : true)) {
                    along = Double.NaN;
                    hint = -1;
                    lastAlong = Double.NaN;
                }
                Triple<Double, Double, Integer> locate = l.locate(lat, lon, hint);
                double a = locate.component1().doubleValue();
                double off = locate.component2().doubleValue();
                int i3 = locate.component3().intValue();
                hint = i3;
                offRoute = off > 60.0d;
                along = offRoute ? Double.NaN : a;
                if (l != saidLine) {
                    saidLine = l;
                    lastAlong = Double.NaN;
                    if (!offRoute) {
                        int k = 0;
                        int length = l.getVoiceAt().length;
                        while (k < length) {
                            if (l.getVoiceAt()[k] < a - 30.0d) {
                                i2 = i3;
                                said.add(key(l.getVoiceText().get(k), l.getVoiceAt()[k]));
                            } else {
                                i2 = i3;
                            }
                            k++;
                            i3 = i2;
                        }
                    }
                }
                if (speak && !Intrinsics.areEqual(mode, "off") && !offRoute) {
                    if (vEgo >= 0.5f) {
                        if (Intrinsics.areEqual(mode, "all")) {
                            double reach = (vEgo * 1.2d) + a;
                            int pick = -1;
                            int length2 = l.getVoiceAt().length;
                            for (int k2 = 0; k2 < length2; k2++) {
                                double at = l.getVoiceAt()[k2];
                                if (at <= reach && at >= a - 120.0d && said.add(key(l.getVoiceText().get(k2), at))) {
                                    pick = k2;
                                }
                            }
                            if (pick >= 0) {
                                Speech.INSTANCE.speak(l.getVoiceText().get(pick), false);
                            }
                        }
                        lastAlong = a;
                        if (vEgo < 3.0f) {
                            return;
                        }
                        Iterator<Incident> it = l.getIncidents().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                i = 1;
                                break;
                            }
                            Incident inc = it.next();
                            double ahead = inc.getStartM() - a;
                            if (150.0d <= ahead && ahead <= 1700.0d) {
                                String id = inc.getId();
                                if (id.length() == 0) {
                                    id = inc.getLat() + "," + inc.getLon();
                                }
                                String key = id;
                                if (announced.add(key)) {
                                    i = 1;
                                    Speech.INSTANCE.speak(incidentWords(inc) + " ahead, in " + Speech.INSTANCE.spoken(distanceText(ahead, metric, true)), false);
                                    break;
                                }
                            }
                        }
                        int f = faster();
                        Iterable $this$firstOrNull$iv = routes;
                        Iterator it2 = $this$firstOrNull$iv.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                element$iv = it2.next();
                                Route it3 = (Route) element$iv;
                                if ((it3.getIndex() == f ? i : 0) != 0) {
                                    break;
                                }
                            } else {
                                element$iv = null;
                                break;
                            }
                        }
                        Route fr = (Route) element$iv;
                        Route cur = getRoute();
                        if (fr != null && cur != null) {
                            String k3 = fr.getIndex() + "|" + MathKt.roundToInt(fr.getDurationS() / 60);
                            if (!Intrinsics.areEqual(k3, fasterSaid)) {
                                fasterSaid = k3;
                                Speech.INSTANCE.speak("A faster route is available. It saves " + StringsKt.replace(durationText(cur.getDurationS() - fr.getDurationS()), "min", "minutes", false) + ".", false);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                }
                lastAlong = offRoute ? Double.NaN : a;
                return;
            }
        }
        along = Double.NaN;
        hint = -1;
        lastAlong = Double.NaN;
    }

    private final String key(String text, double at) {
        return text + "@" + MathKt.roundToInt(at / 50);
    }

    public final String routeAhead() {
        Line l = getLine();
        if (l == null) {
            return null;
        }
        Double valueOf = Double.valueOf(along);
        double it = valueOf.doubleValue();
        if (!((Double.isInfinite(it) || Double.isNaN(it)) ? false : true)) {
            valueOf = null;
        }
        double a = valueOf != null ? valueOf.doubleValue() : 0.0d;
        int i0 = l.indexAt(a);
        int i1 = l.indexAt(80000.0d + a);
        if (i1 - i0 < 1) {
            return null;
        }
        int step = Math.max(1, (i1 - i0) / 150);
        ArrayList pts = new ArrayList();
        for (int i = i0; i < i1; i += step) {
            pts.add(TuplesKt.to(Double.valueOf(latOf(l.getMy()[i])), Double.valueOf(lonOf(l.getMx()[i]))));
        }
        pts.add(TuplesKt.to(Double.valueOf(latOf(l.getMy()[i1])), Double.valueOf(lonOf(l.getMx()[i1]))));
        return encodePolyline(pts);
    }

    public final String encodePolyline(List<Pair<Double, Double>> points) {
        double lat;
        long v;
        Intrinsics.checkNotNullParameter(points, "points");
        StringBuilder sb = new StringBuilder();
        long la = 0;
        long lo = 0;
        for (Pair<Double, Double> pair : points) {
            double lat2 = pair.component1().doubleValue();
            double lon = pair.component2().doubleValue();
            long a = Math.round(lat2 * 100000.0d);
            long b = Math.round(100000.0d * lon);
            long[] jArr = {a - la, b - lo};
            int i = 0;
            for (int i2 = 2; i < i2; i2 = 2) {
                long v0 = jArr[i];
                if (v0 < 0) {
                    lat = lat2;
                    v = ~(v0 << 1);
                } else {
                    lat = lat2;
                    v = v0 << 1;
                }
                while (v >= 32) {
                    sb.append((char) (((v & 31) | 32) + 63));
                    v >>= 5;
                    lo = lo;
                }
                sb.append((char) (63 + v));
                i++;
                lat2 = lat;
                lo = lo;
            }
            la = a;
            lo = b;
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    public final Pair<Step, Double> nextLanes() {
        Step next;
        Line l = getLine();
        if (l == null) {
            return null;
        }
        double a = along;
        if (!((Double.isInfinite(a) || Double.isNaN(a)) ? false : true) || (next = (Step) CollectionsKt.getOrNull(l.getSteps(), currentStep(l) + 1)) == null) {
            return null;
        }
        double d = next.getStartM() - a;
        if (next.getLanes().size() < 2) {
            return null;
        }
        if (0.0d <= d && d <= 3000.0d) {
            return TuplesKt.to(next, Double.valueOf(d));
        }
        return null;
    }

    public static /* synthetic */ int currentStep$default(Nav nav, Line line, int i, Object obj) {
        if ((i & 1) != 0) {
            line = nav.getLine();
        }
        return nav.currentStep(line);
    }

    public final int currentStep(Line l) {
        List<Step> s;
        double a = along;
        if (l == null || (s = l.getSteps()) == null) {
            return -1;
        }
        if (!((Double.isInfinite(a) || Double.isNaN(a)) ? false : true) || s.isEmpty()) {
            return -1;
        }
        int k = 0;
        int size = s.size();
        for (int i = 0; i < size; i++) {
            if (s.get(i).getStartM() <= 1.0d + a) {
                k = i;
            }
        }
        return k;
    }

    public final Pair<Double, Double> remaining() {
        Route r = getRoute();
        if (r == null) {
            return null;
        }
        Line l = getLine();
        double a = along;
        if (l != null) {
            if ((Double.isInfinite(a) || Double.isNaN(a)) ? false : true) {
                double left = RangesKt.coerceAtLeast(l.getLength() - a, 0.0d);
                return TuplesKt.to(Double.valueOf(r.getDurationS() * (left / RangesKt.coerceAtLeast(l.getLength(), 1.0d))), Double.valueOf(left));
            }
        }
        return TuplesKt.to(Double.valueOf(r.getDurationS()), Double.valueOf(r.getDistanceM()));
    }

    public final int faster() {
        Route cur = getRoute();
        if (cur == null) {
            return -1;
        }
        int best = -1;
        double bestS = cur.getDurationS();
        for (Route r : routes) {
            if (r.getIndex() != cur.getIndex() && !r.getClosed() && r.getDurationS() < bestS) {
                best = r.getIndex();
                bestS = r.getDurationS();
            }
        }
        if (best < 0 || cur.getDurationS() - bestS < 180.0d || bestS > cur.getDurationS() * 0.92d) {
            return -1;
        }
        return best;
    }

    public final String incidentWords(Incident i) {
        Intrinsics.checkNotNullParameter(i, "i");
        if (i.getClosed() || Intrinsics.areEqual(i.getType(), "road_closure")) {
            return "Road closed";
        }
        if (Intrinsics.areEqual(i.getType(), "accident")) {
            return "Crash reported";
        }
        if (Intrinsics.areEqual(i.getType(), "construction")) {
            return "Construction";
        }
        if (Intrinsics.areEqual(i.getType(), "disabled_vehicle")) {
            return "Stalled vehicle";
        }
        if (Intrinsics.areEqual(i.getType(), "lane_restriction")) {
            return "Lane closed";
        }
        if (Intrinsics.areEqual(i.getType(), "road_hazard")) {
            return "Hazard";
        }
        if (Intrinsics.areEqual(i.getType(), "congestion")) {
            return "Traffic jam";
        }
        if (Intrinsics.areEqual(i.getType(), "weather")) {
            return "Weather hazard";
        }
        if (Intrinsics.areEqual(i.getType(), "planned_event") || Intrinsics.areEqual(i.getType(), "mass_transit")) {
            return "Event";
        }
        return "Traffic incident";
    }

    public static /* synthetic */ String distanceText$default(Nav nav, double d, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = false;
        }
        return nav.distanceText(d, z, z2);
    }

    public final String distanceText(double m, boolean metric, boolean spoken) {
        StringBuilder append;
        String str;
        if (metric) {
            if (m < 1000.0d) {
                append = new StringBuilder().append(RangesKt.coerceAtLeast(MathKt.roundToInt(m / 50) * 50, 50));
                str = " m";
            } else {
                if (m < 10000.0d) {
                    String format = String.format("%.1f km", Arrays.copyOf(new Object[]{Double.valueOf(m / 1000)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                    return format;
                }
                append = new StringBuilder().append(MathKt.roundToInt(m / 1000));
                str = " km";
            }
            return append.append(str).toString();
        }
        double ft = 3.28084d * m;
        if (ft < 1000.0d) {
            return RangesKt.coerceAtLeast(MathKt.roundToInt(ft / 50) * 50, 50) + " " + (spoken ? "feet" : "ft");
        }
        double mi = ft / 5280;
        if (mi >= 10.0d) {
            return MathKt.roundToInt(mi) + " mi";
        }
        String format2 = String.format("%.1f mi", Arrays.copyOf(new Object[]{Double.valueOf(mi)}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        return format2;
    }

    public final String durationText(double s) {
        StringBuilder append;
        int m = RangesKt.coerceAtLeast(MathKt.roundToInt(s / 60), 1);
        if (m < 60) {
            append = new StringBuilder().append(m);
        } else {
            append = new StringBuilder().append(m / 60).append(" h ").append(m % 60);
        }
        return append.append(" min").toString();
    }

    public final String etaText(double s) {
        String format = DateFormat.getTimeInstance(3).format(new Date(System.currentTimeMillis() + ((long) (1000 * s))));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final double mercX(double lon) {
        return (180.0d + lon) / 360.0d;
    }

    public final double mercY(double lat) {
        double r = Math.toRadians(RangesKt.coerceIn(lat, -85.0d, 85.0d));
        return (1.0d - (Math.log(Math.tan(r) + (1.0d / Math.cos(r))) / 3.141592653589793d)) / 2.0d;
    }

    public final double lonOf(double x) {
        return (360.0d * x) - 180.0d;
    }

    public final double latOf(double y) {
        return Math.toDegrees(Math.atan(Math.sinh((1.0d - (2.0d * y)) * 3.141592653589793d)));
    }

    public final double metres(double lat1, double lon1, double lat2, double lon2) {
        double dy = (lat2 - lat1) * 111195.0d;
        double dx = (lon2 - lon1) * 111195.0d * Math.cos(Math.toRadians((lat1 + lat2) / 2));
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    public final double[] decodePolyline(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        ArrayList out = new ArrayList(s.length() / 3);
        int i = 0;
        int lat = 0;
        int lon = 0;
        while (i < s.length()) {
            int k = 0;
            while (k < 2) {
                int shift = 0;
                int result = 0;
                while (i < s.length()) {
                    int i2 = i + 1;
                    int b = s.charAt(i) - 63;
                    result |= (b & 31) << shift;
                    shift += 5;
                    if (b >= 32) {
                        i = i2;
                    } else {
                        int d = (result & 1) != 0 ? ~(result >> 1) : result >> 1;
                        if (k == 0) {
                            lat += d;
                        } else {
                            lon += d;
                        }
                        k++;
                        i = i2;
                    }
                }
                return CollectionsKt.toDoubleArray(out);
            }
            out.add(Double.valueOf(lat / 100000.0d));
            out.add(Double.valueOf(lon / 100000.0d));
        }
        return CollectionsKt.toDoubleArray(out);
    }
}
