package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.CarUi;
import com.starpilot.starview.DoorsBeltsView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Handler;
import android.widget.LinearLayout.LayoutParams;
import java.util.Map.Entry;

/* compiled from: DoorsBelts.kt */
public final class DoorsBeltsView extends CardPage {
    public static final Companion Companion = new Companion(null);
    private static LearnState saved;
    private final SimpleDateFormat dateFmt;
    private final SimpleDateFormat dayFmt;
    private Learn learn;
    private JSONObject live;
    private String liveErr;
    private LinearLayout nowCard;
    private final Runnable poll;
    private final int red;
    private final SimpleDateFormat shortFmt;
    private boolean shown;
    private JSONObject status;
    private long statusAt;
    private String statusErr;
    private final SimpleDateFormat timeFmt;

    /* VF: from Vineflower (jadx: Type inference failed) */
    public DoorsBeltsView(Context var1, CarUi var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        this.dayFmt = new SimpleDateFormat("EEEE, MMM d", Locale.US);
        this.timeFmt = new SimpleDateFormat("h:mm:ss a", Locale.US);
        this.shortFmt = new SimpleDateFormat("EEE MMM d, h:mm a", Locale.US);
        this.dateFmt = new SimpleDateFormat("MMM d", Locale.US);
        this.red = Color.rgb(255, 138, 128);
        DoorsBeltsView.LearnState var3 = saved;
        if (var3 != null) {
            if (Intrinsics.areEqual(var3.getPhase(), "still")) {
                var3.setPhase("ready");
            }

            this.learn = new DoorsBeltsView.Learn(this, var3);
            this.getMain().post(new DoorsBeltsView$$ExternalSyntheticLambda3(this));
        }

        this.poll = new Runnable() {
            final DoorsBeltsView this$0 = DoorsBeltsView.this;

            @Override
            public void run() {
                if (this.this$0.shown) {
                    long var3x = System.currentTimeMillis();
                    if (this.this$0.learn == null && var3x - this.this$0.statusAt > 30000L) {
                        this.this$0.loadStatus();
                    }

                    JSONObject var5 = this.this$0.status;
                    boolean var2x = false;
                    boolean var1x = var2x;
                    if (var5 != null) {
                        var1x = var2x;
                        if (var5.optBoolean("carOn")) {
                            var1x = true;
                        }
                    }

                    if (var1x || this.this$0.learn != null) {
                        this.this$0.loadLive();
                    }

                    Handler var8 = this.this$0.getMain();
                    Runnable var6 = this;
                    if (this.this$0.learn != null) {
                        var3x = 5000L;
                    } else {
                        var3x = 2000L;
                    }

                    var8.postDelayed(var6, var3x);
                }
            }
        };
    }

    /* compiled from: DoorsBelts.kt */
    private static final class LearnState {
        private String error;
        private final LinkedHashMap<String, JSONObject> found;
        private int i;
        private final List<JSONObject> list;
        private String phase;
        private JSONObject result;

        /* VF: from Vineflower (jadx: ) */
        public LearnState(List<? extends JSONObject> var1) {
            super();
            Intrinsics.checkNotNullParameter(var1, "list");
            this.list = (List) var1;
            this.phase = "intro";
            this.error = "";
            this.found = new LinkedHashMap<>();
        }

        public final List<JSONObject> getList() {
            return this.list;
        }

        public final int getI() {
            return this.i;
        }

        public final void setI(int i) {
            this.i = i;
        }

        public final String getPhase() {
            return this.phase;
        }

        public final void setPhase(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.phase = str;
        }

        public final JSONObject getResult() {
            return this.result;
        }

        public final void setResult(JSONObject jSONObject) {
            this.result = jSONObject;
        }

        public final String getError() {
            return this.error;
        }

        public final void setError(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.error = str;
        }

        public final LinkedHashMap<String, JSONObject> getFound() {
            return this.found;
        }
    }

    /* compiled from: DoorsBelts.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static final void lambda$1$lambda$0(DoorsBeltsView this$0) {
        Learn learn = this$0.learn;
        if (learn != null) {
            learn.paint();
        }
    }

    public final void start() {
        this.shown = true;
        getMain().removeCallbacks(this.poll);
        this.statusAt = 0L;
        getMain().post(this.poll);
    }

    public final void stop() {
        this.shown = false;
        getMain().removeCallbacks(this.poll);
    }

    @Override // com.starpilot.starview.CardPage
    public void refresh() {
        start();
    }

    static /* synthetic */ void req$default(DoorsBeltsView doorsBeltsView, String str, String str2, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        doorsBeltsView.req(str, str2, function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void req(String op, String item, Function1<? super JSONObject, Unit> cb) {
        DataClient d = Link.INSTANCE.getData();
        if (d != null) {
            d.request("doorbelt", item == null ? MapsKt.mapOf(TuplesKt.to("op", op)) : MapsKt.mapOf(TuplesKt.to("op", op), TuplesKt.to("item", item)), cb);
            return;
        }
        JSONObject put = new JSONObject().put("ok", false).put("error", "Not connected to the comma.");
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        cb.invoke(put);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadStatus() {
        this.statusAt = System.currentTimeMillis();
        req$default(this, "status", null, new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadStatus$lambda$2;
                loadStatus$lambda$2 = DoorsBeltsView.loadStatus$lambda$2(DoorsBeltsView.this, (JSONObject) obj);
                return loadStatus$lambda$2;
            }
        }, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadStatus$lambda$2(DoorsBeltsView this$0, JSONObject r) {
        String optString;
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.status = r;
            optString = null;
        } else {
            optString = r.optString("error", "The comma didn't answer");
        }
        this$0.statusErr = optString;
        if (this$0.learn == null) {
            this$0.paint();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadLive() {
        req$default(this, "live", null, new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadLive$lambda$3;
                loadLive$lambda$3 = DoorsBeltsView.loadLive$lambda$3(DoorsBeltsView.this, (JSONObject) obj);
                return loadLive$lambda$3;
            }
        }, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadLive$lambda$3(DoorsBeltsView this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.live = r;
            this$0.liveErr = null;
        } else {
            this$0.live = null;
            this$0.liveErr = r.optString("error");
        }
        if (this$0.learn == null) {
            this$0.paintNow();
        } else {
            Learn learn = this$0.learn;
            if (learn != null) {
                learn.liveTick(r);
            }
        }
        return Unit.INSTANCE;
    }

    private final List<JSONObject> items() {
        JSONArray a;
        JSONObject jSONObject = this.status;
        if (jSONObject == null || (a = jSONObject.optJSONArray("items")) == null) {
            return CollectionsKt.emptyList();
        }
        Iterable $this$map$iv = RangesKt.until(0, a.length());
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it = $this$map$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            destination$iv$iv.add(a.getJSONObject(item$iv$iv));
        }
        return (List) destination$iv$iv;
    }

    private final String nameOf(String id) {
        Object element$iv;
        String optString;
        Iterable $this$firstOrNull$iv = items();
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                if (Intrinsics.areEqual(it2.optString("id"), id)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        JSONObject jSONObject = (JSONObject) element$iv;
        return (jSONObject == null || (optString = jSONObject.optString("name")) == null) ? id : optString;
    }

    private final String kindOf(String id) {
        Object element$iv;
        String optString;
        Iterable $this$firstOrNull$iv = items();
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                if (Intrinsics.areEqual(it2.optString("id"), id)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        JSONObject jSONObject = (JSONObject) element$iv;
        return (jSONObject == null || (optString = jSONObject.optString("kind")) == null) ? "door" : optString;
    }

    private final String word(String id, boolean on) {
        return Intrinsics.areEqual(kindOf(id), "belt") ? on ? "buckled" : "unbuckled" : on ? "open" : "closed";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void toast(String s) {
        Toast.makeText(getContext(), s, 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final View buttons(View... b) {
        LinearLayout $this$buttons_u24lambda_u248 = new LinearLayout(getContext());
        $this$buttons_u24lambda_u248.setOrientation(0);
        $this$buttons_u24lambda_u248.setPadding(0, getUi().dp(10), 0, 0);
        for (View v : b) {
            LinearLayout.LayoutParams $this$buttons_u24lambda_u248_u24lambda_u247 = new LinearLayout.LayoutParams(-2, -2);
            $this$buttons_u24lambda_u248_u24lambda_u247.rightMargin = getUi().dp(12);
            Unit unit = Unit.INSTANCE;
            $this$buttons_u24lambda_u248.addView(v, $this$buttons_u24lambda_u248_u24lambda_u247);
        }
        return $this$buttons_u24lambda_u248;
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void paint() {
        this.getCol().removeAllViews();
        JSONObject var18 = this.status;
        LinearLayout var12 = this.card();
        this.add((View)var12);
        String var17 = "device";
        var12.addView(this.title("Doors & belts", "car", "device"));
        if (var18 == null) {
            CardPage var96 = this;
            String var81 = this.statusErr;
            String var68 = var81;
            if (var81 == null) {
                var68 = "Asking the comma…";
            }

            var12.addView((View)CardPage.line$default(var96, var68, 0, 0.0F, 6, null));
        } else {
            var12.addView(
                (View)CardPage.line$default(
                    this,
                    "The comma checks every door and seat-belt switch after each drive, from the drive's own logs, once the car is off. Nothing extra runs while driving.",
                    0,
                    0.0F,
                    6,
                    null
                )
            );
            JSONObject var11 = var18.optJSONObject("scanning");
            int var4 = var18.optInt("pending");
            CardPage var13 = this;
            String var10;
            if (var11 != null) {
                int var3 = var11.optInt("done");
                int var5 = var11.optInt("total");
                var10 = "Checking drives now: " + var3 + " of " + var5 + " minutes read";
            } else if (var4 > 0) {
                if (var4 == 1) {
                    var10 = "";
                } else {
                    var10 = "s";
                }

                var10 = var4 + " minute" + var10 + " of driving to check - it starts a couple of minutes after the car is off";
            } else {
                var10 = "Every drive is checked";
            }

            var12.addView((View)CardPage.line$default(var13, var10, CarUi.Companion.getTEXT(), 0.0F, 4, null));
            long var8 = var18.optLong("learnedAt", 0L);
            var var57 = this.items();
            int var30;
            if (var57 instanceof Collection && ((Collection)var57).isEmpty()) {
                var30 = 0;
            } else {
                var30 = 0;

                for (JSONObject var14 : var57) {
                    boolean var40;
                    if (!Intrinsics.areEqual(var14.optString("source"), "learned") && !Intrinsics.areEqual(var14.optString("source"), "standard")) {
                        var40 = false;
                    } else {
                        var40 = true;
                    }

                    if (var40) {
                        if (++var30 < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
            }

            var13 = this;
            if (var8 > 0L) {
                var4 = this.items().size();
                var10 = this.dateFmt.format(new Date(1000 * var8));
                var10 = "Watching " + var30 + " of " + var4 + " switches · learned " + var10;
            } else {
                var10 = "Watching the 4 doors and the driver's belt. Learn the rest once (car on, in Park, about 5 minutes).";
            }

            String var19 = "";
            String var69 = "s";
            var12.addView((View)CardPage.line$default(var13, var10, 0, 0.0F, 6, null));
            CarUi var93 = this.getUi();
            if (var8 > 0L) {
                var10 = "Learn again…";
            } else {
                var10 = "Learn…";
            }

            boolean var7;
            if (var8 == 0L) {
                var7 = true;
            } else {
                var7 = false;
            }

            var12.addView(this.buttons(var93.button(var10, var7, new DoorsBeltsView$$ExternalSyntheticLambda0(this))));
            LinearLayout var61 = this.card();
            this.add((View)var61);
            this.nowCard = var61;
            this.paintNow();
            JSONArray var62 = var18.optJSONArray("drives");
            JSONArray var94 = var62;
            if (var62 == null) {
                var94 = new JSONArray();
            }

            HashMap var15 = new HashMap();
            HashMap var16 = new HashMap();
            HashMap var20 = new HashMap();
            var4 = 0;
            int var6 = var94.length();
            var10 = var69;

            while (true) {
                String var24 = "changes";
                String var21 = "keys(...)";
                String var22 = "fast";
                if (var4 >= var6) {
                    LinearLayout var25 = this.card();
                    this.add((View)var25);
                    var25.addView(this.title("Flickers", "pulse", "device"));
                    if (var94.length() == 0) {
                        var25.addView((View)CardPage.line$default(this, "No drive checked yet.", 0, 0.0F, 6, null));
                    } else if (var15.isEmpty()) {
                        CardPage var70 = this;
                        var30 = var94.length();
                        if (var94.length() == 1) {
                            var10 = "";
                        }

                        var25.addView(
                            (View)CardPage.line$default(
                                var70,
                                "No flicker in the last " + var30 + " drive" + var10 + ": no switch changed twice within 3 seconds.",
                                CarUi.Companion.getACCENT(),
                                0.0F,
                                4,
                                null
                            )
                        );
                    } else {
                        Set var71 = var15.entrySet();
                        Intrinsics.checkNotNullExpressionValue(var71, "<get-entries>(...)");
                        Iterator var88 = CollectionsKt.sortedWith(
                                var71,
                                new Comparator() {
                                    @Override
                                    public final int compare(Object var1, Object var2) {
                                        return ComparisonsKt.compareValues(
                                            ((Comparable)((java.util.Map.Entry)var2).getValue()), ((Comparable)((java.util.Map.Entry)var1).getValue())
                                        );
                                    }
                                }
                            )
                            .iterator();
                        HashMap var72 = var20;
                        HashMap var99 = var16;

                        while (var88.hasNext()) {
                            java.util.Map.Entry var103 = (java.util.Map.Entry)var88.next();
                            Intrinsics.checkNotNullExpressionValue(var103, "next(...)");
                            var103 = var103;
                            Object var126 = (Integer)var103.getKey();
                            Intrinsics.checkNotNullExpressionValue(var126, "component1(...)");
                            var126 = (String)var126;
                            var103 = (java.util.Map.Entry)var103.getValue();
                            Intrinsics.checkNotNullExpressionValue(var103, "component2(...)");
                            Integer var26 = (Integer)(Object)var103;
                            String var146 = this.nameOf((String)var126);
                            String var106;
                            if (var26) {
                                var106 = "";
                            } else {
                                var106 = var10;
                            }

                            Object var27 = var99.get(var126);
                            var4 = var94.length();
                            var126 = (Integer)var72.get(var126);
                            if (var126 != null) {
                                var30 = var126;
                            } else {
                                var30 = var26;
                            }

                            var25.addView(
                                (View)this.line(
                                    var146
                                        + ": "
                                        + var26
                                        + " quick change"
                                        + var106
                                        + " in "
                                        + var27
                                        + " of the last "
                                        + var4
                                        + " drives ("
                                        + var30
                                        + " changes in all)",
                                    CarUi.Companion.getWARN(),
                                    17.0F
                                )
                            );
                        }
                    }

                    LinearLayout var147 = this.card();
                    this.add((View)var147);
                    var147.addView(this.title("Drives", "history", "device"));
                    if (var94.length() == 0) {
                        var147.addView((View)CardPage.line$default(this, "Each drive shows up here after the car is off.", 0, 0.0F, 6, null));
                    }

                    var4 = 0;
                    var30 = Math.min(var94.length(), 20);
                    String var73 = var19;
                    String var101 = var24;
                    String var89 = var17;
                    JSONArray var111 = var94;
                    var10 = var22;
                    String var107 = var21;

                    while (var4 < var30) {
                        JSONObject var151 = var111.getJSONObject(var4);
                        var18 = var151.optJSONObject(var101);
                        JSONObject var150 = var151.optJSONObject(var10);
                        JSONObject var153 = var151.optJSONObject("moving");
                        String var74;
                        String var90;
                        List var122;
                        if (var18 != null && var18.length() != 0) {
                            Iterator var123 = var18.keys();
                            Intrinsics.checkNotNullExpressionValue(var123, var107);
                            final var var18$f = var18;
                            Iterable var130 = CollectionsKt.sortedWith(SequencesKt.toList(SequencesKt.asSequence(var123)), new Comparator() {
                                final JSONObject $c$inlined = var18$f;

                                @Override
                                public final int compare(Object var1, Object var2) {
                                    var2 = var2;
                                    Comparable var5 = this.$c$inlined.optInt(((String) var2));
                                    var1 = var1;
                                    return ComparisonsKt.compareValues(var5, this.$c$inlined.optInt(((String) var1)));
                                }
                            });
                            var var152 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var130, 10));

                            for (Object var22$it : (Iterable) var130) {
                                var22 = (String) var22$it;
                                String var29;
                                label212: {
                                    Intrinsics.checkNotNull(var22);
                                    var29 = this.nameOf(var22);
                                    var6 = var18.optInt(var22);
                                    if (var150 != null) {
                                        Integer var135 = var150.optInt(var22);
                                        int var44 = var135.intValue();
                                        int var45;
                                        if (var44 > 0) {
                                            var45 = 1;
                                        } else {
                                            var45 = 0;
                                        }

                                        if ((var45 == 0)) {
                                            var135 = null;
                                        }

                                        if (var135 != null) {
                                            var45 = var135.intValue();
                                            var21 = var45 + " quick";
                                            break label212;
                                        }
                                    }

                                    var21 = null;
                                }

                                label206: {
                                    if (var153 != null) {
                                        Integer var142 = var153.optInt(var22);
                                        int var47 = var142.intValue();
                                        int var48;
                                        if (var47 > 0) {
                                            var48 = 1;
                                        } else {
                                            var48 = 0;
                                        }

                                        if ((var48 == 0)) {
                                            var142 = null;
                                        }

                                        if (var142 != null) {
                                            var48 = var142.intValue();
                                            var22 = var48 + " moving";
                                            break label206;
                                        }
                                    }

                                    var22 = null;
                                }

                                var21 = CollectionsKt.joinToString(CollectionsKt.listOfNotNull(new String[]{var21, var22}), ", ", "", "", -1, "...", null);
                                boolean var50;
                                if (var21.length() == 0) {
                                    var50 = true;
                                } else {
                                    var50 = false;
                                }

                                if (var50) {
                                    var21 = var73;
                                } else {
                                    var21 = " (" + var21 + ")";
                                }

                                var152.add(var29 + " " + var6 + "×" + var21);
                            }

                            var10 = var89;
                            var122 = var152;
                            var90 = var73;
                            var74 = var10;
                        } else {
                            var90 = var73;
                            List var129 = CollectionsKt.listOf("nothing changed");
                            var10 = var89;
                            var74 = var10;
                            var122 = var129;
                        }

                        String var116 = this.shortFmt.format(new Date(var151.optLong("start") * 1000));
                        int var51 = var151.optInt("segs");
                        var147.addView((View)this.line(var116 + " · " + var51 + " min", CarUi.Companion.getTEXT(), 17.0F));
                        String var117 = CollectionsKt.joinToString(var122, " · ", "", "", -1, "...", null);
                        String var118 = "      " + var117;
                        if (var150 != null && var150.length() > 0) {
                            var51 = CarUi.Companion.getWARN();
                        } else {
                            var51 = CarUi.Companion.getDIM();
                        }

                        var147.addView((View)this.line(var118, var51, 15.0F));
                        var4++;
                        var89 = var10;
                        var10 = var74;
                        var73 = var90;
                    }

                    String var100 = var10;
                    JSONArray var75 = var18.optJSONArray("events");
                    JSONArray var66 = var75;
                    if (var75 == null) {
                        var66 = new JSONArray();
                    }

                    if (var66.length() > 0) {
                        LinearLayout var112 = this.card();
                        this.add((View)var112);
                        var112.addView(this.title("Every change", "bell", var89));
                        String var76 = "";
                        var4 = 0;
                        var30 = Math.min(var66.length(), 300);
                        JSONArray var102 = var66;

                        while (var4 < var30) {
                            var18 = var102.getJSONObject(var4);
                            double var1 = var18.optDouble("t");
                            Date var124 = new Date((long)(1000 * var1));
                            String var108 = this.dayFmt.format(var124);
                            var10 = var76;
                            if (!Intrinsics.areEqual(var108, var76)) {
                                var10 = var108;
                                CarUi var77 = this.getUi();
                                Intrinsics.checkNotNull(var108);
                                var112.addView((View)var77.header(var108));
                            }

                            String var131 = var18.optString("item");
                            String var78;
                            if (var18.optBoolean("moving")) {
                                int var53 = var18.optInt("mph");
                                var78 = var53 + " mph";
                            } else {
                                var78 = "parked";
                            }

                            String var109;
                            if (var18.optBoolean(var100)) {
                                var109 = "QUICK";
                            } else {
                                var109 = null;
                            }

                            String var79 = CollectionsKt.joinToString(CollectionsKt.listOfNotNull(new String[]{var78, var109}), " · ", "", "", -1, "...", null);
                            String var110 = this.timeFmt.format(var124);
                            Intrinsics.checkNotNull(var131);
                            var19 = this.nameOf(var131);
                            String var132 = this.word(var131, var18.optBoolean("on"));
                            String var80 = var110 + "   " + var19 + " " + var132 + "   · " + var79;
                            int var54;
                            if (var18.optBoolean(var100)) {
                                var54 = CarUi.Companion.getWARN();
                            } else {
                                var54 = CarUi.Companion.getTEXT();
                            }

                            var112.addView((View)this.line(var80, var54, 16.0F));
                            var4++;
                            var76 = var10;
                        }
                    }

                    return;
                }

                JSONObject var133 = var94.getJSONObject(var4);
                JSONObject var82 = var133.optJSONObject("fast");
                if (var82 != null) {
                    Iterator var97 = var82.keys();
                    Intrinsics.checkNotNullExpressionValue(var97, "keys(...)");

                    while (var97.hasNext()) {
                        var22 = (String)var97.next();
                        Map var148 = var15;
                        Integer var23 = (Integer)var15.get(var22);
                        int var41;
                        if (var23 != null) {
                            var41 = var23;
                        } else {
                            var41 = 0;
                        }

                        var148.put(var22, var41 + var82.optInt(var22));
                        Map var144 = var16;
                        Integer var149 = (Integer)var16.get(var22);
                        if (var149 != null) {
                            var41 = var149;
                        } else {
                            var41 = 0;
                        }

                        var144.put(var22, var41 + 1);
                    }

                    Unit var83 = Unit.INSTANCE;
                    Unit var84 = Unit.INSTANCE;
                }

                JSONObject var85 = var133.optJSONObject("changes");
                if (var85 != null) {
                    Iterator var98 = var85.keys();
                    Intrinsics.checkNotNullExpressionValue(var98, "keys(...)");

                    while (var98.hasNext()) {
                        var22 = (String)var98.next();
                        Map var134 = var20;
                        Integer var145 = (Integer)var20.get(var22);
                        int var43;
                        if (var145 != null) {
                            var43 = var145;
                        } else {
                            var43 = 0;
                        }

                        var134.put(var22, var43 + var85.optInt(var22));
                    }

                    Unit var86 = Unit.INSTANCE;
                    Unit var87 = Unit.INSTANCE;
                }

                var4++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$10(DoorsBeltsView this$0) {
        this$0.openLearn();
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Type inference failed;incorrect) */
    private final void paintNow() {
        LinearLayout var6 = this.nowCard;
        if (var6 != null) {
            var6.removeAllViews();
            var6.addView(this.title("Right now", "eye", "device"));
            JSONObject var3 = this.status;
            if (var3 != null) {
                JSONObject var2 = this.live;
                if (!var3.optBoolean("carOn")) {
                    var6.addView((View)CardPage.line$default(this, "Shows every switch live with the car on and in Park.", 0, 0.0F, 6, null));
                } else if (var2 == null) {
                    CardPage var22 = this;
                    String var21 = this.liveErr;
                    String var20 = var21;
                    if (var21 == null) {
                        var20 = "Reading…";
                    }

                    var6.addView((View)CardPage.line$default(var22, var20, 0, 0.0F, 6, null));
                } else {
                    JSONObject var7 = var2.optJSONObject("words");
                    LinearLayout var8 = new LinearLayout(this.getContext());
                    var8.setOrientation(1);

                    for (JSONObject var10 : this.items()) {
                        String var5 = var10.optString("id");
                        CarUi.Companion var14 = null;
                        String var4 = var14;
                        if (var7 != null) {
                            var5 = var7.optString(var5);
                            var4 = var14;
                            if (var5 != null) {
                                boolean var1;
                                if (var5.length() > 0) {
                                    var1 = true;
                                } else {
                                    var1 = false;
                                }

                                if (var1 && !Intrinsics.areEqual(var5, "null")) {
                                    var1 = true;
                                } else {
                                    var1 = false;
                                }

                                var4 = var14;
                                if (var1) {
                                    var4 = var5;
                                }
                            }
                        }

                        var14 = var10.optString("source");
                        LinearLayout var24 = new LinearLayout(this.getContext());
                        var24.setOrientation(0);
                        var24.setGravity(16);
                        var24.setPadding(0, this.getUi().dp(3), 0, this.getUi().dp(3));
                        TextView var11 = new TextView(var24.getContext());
                        var11.setText(var10.optString("name"));
                        var11.setTextColor(CarUi.Companion.getTEXT());
                        var11.setTextSize(17.0F);
                        var24.addView((View)var11, (android.view.ViewGroup.LayoutParams)(new LayoutParams(this.getUi().dp(260), -2)));
                        TextView var25 = new TextView(var24.getContext());
                        if (var4 != null) {
                            var14 = var4;
                        } else {
                            label82: {
                                label81: {
                                    if (var14 != null) {
                                        switch (var14.hashCode()) {
                                            case -1282241876:
                                                if (var14.equals("not on bus")) {
                                                    var14 = "not on the comma's bus";
                                                    break label82;
                                                }
                                                break;
                                            case 0:
                                                if (var14.equals("")) {
                                                    break label81;
                                                }
                                                break;
                                            case 3392903:
                                                if (var14.equals("null")) {
                                                    break label81;
                                                }
                                        }
                                    }

                                    var14 = "–";
                                    break label82;
                                }

                                var14 = "not learned";
                            }

                            var14 = (CharSequence)var14;
                        }

                        var25.setText((CharSequence)var14);
                        int var13;
                        if (Intrinsics.areEqual(var4, "open")) {
                            var13 = CarUi.Companion.getWARN();
                        } else if (Intrinsics.areEqual(var4, "buckled")) {
                            var13 = CarUi.Companion.getACCENT();
                        } else {
                            var14 = CarUi.Companion;
                            if (var4 == null) {
                                var13 = var14.getDIM();
                            } else {
                                var13 = var14.getTEXT();
                            }
                        }

                        var25.setTextColor(var13);
                        var25.setTextSize(17.0F);
                        Typeface var19;
                        if (var4 != null) {
                            var19 = Typeface.DEFAULT_BOLD;
                        } else {
                            var19 = Typeface.DEFAULT;
                        }

                        var25.setTypeface(var19);
                        var24.addView((View)var25);
                        var8.addView((View)var24);
                    }

                    var6.addView((View)var8);
                }
            }
        }
    }

    private final void openLearn() {
        JSONObject jSONObject = this.status;
        boolean z = false;
        if (jSONObject != null && jSONObject.optBoolean("carOn")) {
            z = true;
        }
        if (!z) {
            toast("Turn the car on and put it in Park first.");
            return;
        }
        LearnState st = new LearnState(items());
        saved = st;
        Learn it = new Learn(this, st);
        it.paint();
        this.learn = it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void closeLearn() {
        this.learn = null;
        saved = null;
        this.statusAt = 0L;
        loadStatus();
        paint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DoorsBelts.kt */
    final class Learn {
        private final LearnState st;
        final /* synthetic */ DoorsBeltsView this$0;

        public Learn(DoorsBeltsView this$0, LearnState st) {
            Intrinsics.checkNotNullParameter(st, "st");
            this.this$0 = this$0;
            this.st = st;
        }

        public final LearnState getSt() {
            return this.st;
        }

        public final List<JSONObject> getList() {
            return this.st.getList();
        }

        public final int getI() {
            return this.st.getI();
        }

        public final void setI(int v) {
            this.st.setI(v);
        }

        public final String getPhase() {
            return this.st.getPhase();
        }

        public final void setPhase(String v) {
            Intrinsics.checkNotNullParameter(v, "v");
            this.st.setPhase(v);
        }

        public final JSONObject getResult() {
            return this.st.getResult();
        }

        public final void setResult(JSONObject v) {
            this.st.setResult(v);
        }

        public final String getError() {
            return this.st.getError();
        }

        public final void setError(String v) {
            Intrinsics.checkNotNullParameter(v, "v");
            this.st.setError(v);
        }

        public final LinkedHashMap<String, JSONObject> getFound() {
            return this.st.getFound();
        }

        public final String id() {
            return getList().get(getI()).optString("id");
        }

        public final String name() {
            return getList().get(getI()).optString("name");
        }

        public final boolean belt() {
            return Intrinsics.areEqual(getList().get(getI()).optString("kind"), "belt");
        }

        public final void liveTick(JSONObject r) {
            Intrinsics.checkNotNullParameter(r, "r");
            if (SetsKt.setOf(new String[]{"intro", "error"}).contains(getPhase())) {
                return;
            }
            if (r.optBoolean("ok")) {
                if (r.optBoolean("learning") || Intrinsics.areEqual(getPhase(), "still")) {
                    return;
                }
                JSONObject put = new JSONObject().put("error", "Learn stopped on the comma - start it again.");
                Intrinsics.checkNotNullExpressionValue(put, "put(...)");
                fail(put);
                return;
            }
            fail(r);
        }

        public final void fail(JSONObject r) {
            Intrinsics.checkNotNullParameter(r, "r");
            setError(r.optString("error", "The comma didn't answer"));
            setPhase("error");
            paint();
        }

        public final void paint() {
            this.this$0.getCol().removeAllViews();
            LinearLayout c = this.this$0.card();
            this.this$0.add(c);
            c.addView(this.this$0.title("Learn doors & belts", "car", "device"));
            String phase = getPhase();
            switch (phase.hashCode()) {
                case -1857640538:
                    if (phase.equals("summary")) {
                        Map $this$filterValues$iv = getFound();
                        LinkedHashMap result$iv = new LinkedHashMap();
                        for (Map.Entry entry$iv : (Iterable<Map.Entry>) $this$filterValues$iv.entrySet()) {
                            JSONObject it = ((JSONObject) entry$iv.getValue());
                            JSONObject it2 = it != null ? 1 : null;
                            if (it2 != null) {
                                result$iv.put(entry$iv.getKey(), entry$iv.getValue());
                            }
                        }
                        LinkedHashMap $this$filterValues$iv2 = result$iv;
                        Set got = $this$filterValues$iv2.keySet();
                        c.addView(this.this$0.line("Found " + got.size() + " of " + getFound().size() + " you worked.", CarUi.Companion.getTEXT(), 18.0f));
                        for (Map.Entry<String, JSONObject> entry : getFound().entrySet()) {
                            String k = entry.getKey();
                            JSONObject v = entry.getValue();
                            DoorsBeltsView doorsBeltsView = this.this$0;
                            String str = v != null ? "✓  " : "✗  ";
                            Iterable $this$first$iv = getList();
                            for (Object element$iv : $this$first$iv) {
                                JSONObject it3 = (JSONObject) element$iv;
                                if (Intrinsics.areEqual(it3.optString("id"), k)) {
                                    String str2 = str + ((JSONObject) element$iv).optString("name");
                                    CarUi.Companion_ companion = CarUi.Companion;
                                    c.addView(doorsBeltsView.line(str2, v != null ? companion.getACCENT() : companion.getWARN(), 17.0f));
                                }
                            }
                            throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            break;
                        }
                        c.addView(CardPage.line$default(this.this$0, "Save, and the comma checks these from the next drive on (and the last week again).", 0, 0.0f, 6, null));
                        DoorsBeltsView doorsBeltsView2 = this.this$0;
                        CarUi ui = this.this$0.getUi();
                        final DoorsBeltsView doorsBeltsView3 = this.this$0;
                        CarUi ui2 = this.this$0.getUi();
                        final DoorsBeltsView doorsBeltsView4 = this.this$0;
                        c.addView(doorsBeltsView2.buttons(ui.button("Save", true, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda14
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit paint$lambda$21;
                                paint$lambda$21 = DoorsBeltsView.Learn.paint$lambda$21(DoorsBeltsView.this, DoorsBeltsView.Learn.this);
                                return paint$lambda$21;
                            }
                        }), CarUi.button$default(ui2, "Cancel", false, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit paint$lambda$23;
                                paint$lambda$23 = DoorsBeltsView.Learn.paint$lambda$23(DoorsBeltsView.this);
                                return paint$lambda$23;
                            }
                        }, 2, null)));
                        return;
                    }
                    return;
                case -934426595:
                    if (phase.equals("result")) {
                        TextView $this$paint_u24lambda_u2415 = new TextView(this.this$0.getContext());
                        $this$paint_u24lambda_u2415.setText(name());
                        $this$paint_u24lambda_u2415.setTextColor(CarUi.Companion.getTEXT());
                        $this$paint_u24lambda_u2415.setTextSize(26.0f);
                        $this$paint_u24lambda_u2415.setTypeface(Typeface.DEFAULT_BOLD);
                        c.addView($this$paint_u24lambda_u2415);
                        JSONObject f = getResult();
                        if (f != null) {
                            DoorsBeltsView doorsBeltsView5 = this.this$0;
                            int optInt = f.optInt("bus");
                            String hexString = Integer.toHexString(f.optInt("addr"));
                            Intrinsics.checkNotNullExpressionValue(hexString, "toHexString(...)");
                            String upperCase = hexString.toUpperCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                            c.addView(doorsBeltsView5.line("Found it ✓  (bus " + optInt + ", 0x" + upperCase + " byte " + f.optInt("byte") + " bit " + f.optInt("bit") + ", changed " + f.optInt("changes") + " times)", CarUi.Companion.getACCENT(), 18.0f));
                        } else {
                            c.addView(this.this$0.line("Nothing on the comma's bus moved with it. Try again, or skip it: that switch may only be on a bus the comma doesn't see.", CarUi.Companion.getWARN(), 18.0f));
                        }
                        c.addView(this.this$0.buttons(this.this$0.getUi().button("Next", true, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda12
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit paint$lambda$16;
                                paint$lambda$16 = DoorsBeltsView.Learn.paint$lambda$16(DoorsBeltsView.Learn.this);
                                return paint$lambda$16;
                            }
                        }), CarUi.button$default(this.this$0.getUi(), "Again", false, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda13
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit paint$lambda$17;
                                paint$lambda$17 = DoorsBeltsView.Learn.paint$lambda$17(DoorsBeltsView.Learn.this);
                                return paint$lambda$17;
                            }
                        }, 2, null)));
                        return;
                    }
                    return;
                case 96784904:
                    if (phase.equals("error")) {
                        c.addView(this.this$0.line(getError(), this.this$0.red, 18.0f));
                        DoorsBeltsView doorsBeltsView6 = this.this$0;
                        CarUi ui3 = this.this$0.getUi();
                        final DoorsBeltsView doorsBeltsView7 = this.this$0;
                        c.addView(doorsBeltsView6.buttons(this.this$0.getUi().button("Start over", true, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit paint$lambda$24;
                                paint$lambda$24 = DoorsBeltsView.Learn.paint$lambda$24(DoorsBeltsView.Learn.this);
                                return paint$lambda$24;
                            }
                        }), CarUi.button$default(ui3, "Close", false, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit paint$lambda$26;
                                paint$lambda$26 = DoorsBeltsView.Learn.paint$lambda$26(DoorsBeltsView.this);
                                return paint$lambda$26;
                            }
                        }, 2, null)));
                        return;
                    }
                    return;
                case 100361836:
                    if (phase.equals("intro")) {
                        c.addView(this.this$0.line("Car on, in Park. Close every door and unbuckle every belt first.", CarUi.Companion.getTEXT(), 18.0f));
                        c.addView(CardPage.line$default(this.this$0, "For each one: tap Start, then open and close it twice (a belt: buckle and unbuckle it twice), then tap Done. Skip the seats this van doesn't have.", 0, 0.0f, 6, null));
                        c.addView(CardPage.line$default(this.this$0, "Move between seats or step out between steps if you need to - just not while a step runs, and don't touch any other door or belt during one.", 0, 0.0f, 6, null));
                        DoorsBeltsView doorsBeltsView8 = this.this$0;
                        CarUi ui4 = this.this$0.getUi();
                        final DoorsBeltsView doorsBeltsView9 = this.this$0;
                        CarUi ui5 = this.this$0.getUi();
                        final DoorsBeltsView doorsBeltsView10 = this.this$0;
                        c.addView(doorsBeltsView8.buttons(ui4.button("Begin", true, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit paint$lambda$2;
                                paint$lambda$2 = DoorsBeltsView.Learn.paint$lambda$2(DoorsBeltsView.this, DoorsBeltsView.Learn.this);
                                return paint$lambda$2;
                            }
                        }), CarUi.button$default(ui5, "Cancel", false, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda5
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit paint$lambda$3;
                                paint$lambda$3 = DoorsBeltsView.Learn.paint$lambda$3(DoorsBeltsView.this);
                                return paint$lambda$3;
                            }
                        }, 2, null)));
                        return;
                    }
                    return;
                case 108386723:
                    if (!phase.equals("ready")) {
                        return;
                    }
                    break;
                case 109765032:
                    if (phase.equals("still")) {
                        c.addView(this.this$0.line("Hold still a few seconds - the comma is listening to the bus…", CarUi.Companion.getTEXT(), 18.0f));
                        return;
                    }
                    return;
                case 1525164849:
                    if (!phase.equals("working")) {
                        return;
                    }
                    break;
                default:
                    return;
            }
            c.addView(this.this$0.line((getI() + 1) + " of " + getList().size(), CarUi.Companion.getDIM(), 15.0f));
            TextView $this$paint_u24lambda_u244 = new TextView(this.this$0.getContext());
            DoorsBeltsView doorsBeltsView11 = this.this$0;
            $this$paint_u24lambda_u244.setText(name());
            $this$paint_u24lambda_u244.setTextColor(CarUi.Companion.getTEXT());
            $this$paint_u24lambda_u244.setTextSize(30.0f);
            $this$paint_u24lambda_u244.setTypeface(Typeface.DEFAULT_BOLD);
            $this$paint_u24lambda_u244.setPadding(0, doorsBeltsView11.getUi().dp(6), 0, doorsBeltsView11.getUi().dp(6));
            c.addView($this$paint_u24lambda_u244);
            if (!Intrinsics.areEqual(getPhase(), "ready")) {
                c.addView(this.this$0.line(belt() ? "Now: buckle, unbuckle, buckle, unbuckle." : "Now: open, close, open, close.", CarUi.Companion.getWARN(), 20.0f));
                DoorsBeltsView doorsBeltsView12 = this.this$0;
                CarUi ui6 = this.this$0.getUi();
                final DoorsBeltsView doorsBeltsView13 = this.this$0;
                CarUi ui7 = this.this$0.getUi();
                final DoorsBeltsView doorsBeltsView14 = this.this$0;
                c.addView(doorsBeltsView12.buttons(ui6.button("Done", true, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda9
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paint$lambda$12;
                        paint$lambda$12 = DoorsBeltsView.Learn.paint$lambda$12(DoorsBeltsView.this, DoorsBeltsView.Learn.this);
                        return paint$lambda$12;
                    }
                }), CarUi.button$default(ui7, "Cancel", false, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda10
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paint$lambda$14;
                        paint$lambda$14 = DoorsBeltsView.Learn.paint$lambda$14(DoorsBeltsView.this);
                        return paint$lambda$14;
                    }
                }, 2, null)));
                return;
            }
            c.addView(this.this$0.line(belt() ? "Tap Start, then buckle and unbuckle it twice. Tap Done when it's unbuckled." : "Tap Start, then open and close it twice. Tap Done when it's closed.", CarUi.Companion.getTEXT(), 18.0f));
            DoorsBeltsView doorsBeltsView15 = this.this$0;
            CarUi ui8 = this.this$0.getUi();
            final DoorsBeltsView doorsBeltsView16 = this.this$0;
            CarUi ui9 = this.this$0.getUi();
            final DoorsBeltsView doorsBeltsView17 = this.this$0;
            CarUi ui10 = this.this$0.getUi();
            final DoorsBeltsView doorsBeltsView18 = this.this$0;
            c.addView(doorsBeltsView15.buttons(ui8.button("Start", true, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paint$lambda$6;
                    paint$lambda$6 = DoorsBeltsView.Learn.paint$lambda$6(DoorsBeltsView.this, DoorsBeltsView.Learn.this);
                    return paint$lambda$6;
                }
            }), CarUi.button$default(ui9, "Skip", false, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paint$lambda$8;
                    paint$lambda$8 = DoorsBeltsView.Learn.paint$lambda$8(DoorsBeltsView.this, DoorsBeltsView.Learn.this);
                    return paint$lambda$8;
                }
            }, 2, null), CarUi.button$default(ui10, "Cancel", false, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paint$lambda$10;
                    paint$lambda$10 = DoorsBeltsView.Learn.paint$lambda$10(DoorsBeltsView.this);
                    return paint$lambda$10;
                }
            }, 2, null)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$2(final DoorsBeltsView this$0, final Learn this$1) {
            DoorsBeltsView.req$default(this$0, "begin", null, new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda21
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$2$lambda$1;
                    paint$lambda$2$lambda$1 = DoorsBeltsView.Learn.paint$lambda$2$lambda$1(this$1, this$0, (JSONObject) obj);
                    return paint$lambda$2$lambda$1;
                }
            }, 2, null);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$2$lambda$1(final Learn this$0, final DoorsBeltsView this$1, JSONObject r) {
            Intrinsics.checkNotNullParameter(r, "r");
            if (r.optBoolean("ok")) {
                this$0.setPhase("still");
                this$0.paint();
                this$1.getMain().postDelayed(new Runnable() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        DoorsBeltsView.Learn.paint$lambda$2$lambda$1$lambda$0(this$1, this$0);
                    }
                }, 5000L);
            } else {
                this$0.fail(r);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void paint$lambda$2$lambda$1$lambda$0(DoorsBeltsView this$0, Learn this$1) {
            if (this$0.learn == this$1 && Intrinsics.areEqual(this$1.getPhase(), "still")) {
                this$1.setPhase("ready");
                this$1.paint();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$3(DoorsBeltsView this$0) {
            this$0.closeLearn();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$6(DoorsBeltsView this$0, final Learn this$1) {
            this$0.req("start", this$1.id(), new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$6$lambda$5;
                    paint$lambda$6$lambda$5 = DoorsBeltsView.Learn.paint$lambda$6$lambda$5(this$1, (JSONObject) obj);
                    return paint$lambda$6$lambda$5;
                }
            });
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$6$lambda$5(Learn this$0, JSONObject r) {
            Intrinsics.checkNotNullParameter(r, "r");
            if (r.optBoolean("ok")) {
                this$0.setPhase("working");
                this$0.paint();
            } else {
                this$0.fail(r);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$10(final DoorsBeltsView this$0) {
            DoorsBeltsView.req$default(this$0, "cancel", null, new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda20
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$10$lambda$9;
                    paint$lambda$10$lambda$9 = DoorsBeltsView.Learn.paint$lambda$10$lambda$9(this$0, (JSONObject) obj);
                    return paint$lambda$10$lambda$9;
                }
            }, 2, null);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$10$lambda$9(DoorsBeltsView this$0, JSONObject it) {
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.closeLearn();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$8(DoorsBeltsView this$0, final Learn this$1) {
            this$0.req("skip", this$1.id(), new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda19
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$8$lambda$7;
                    paint$lambda$8$lambda$7 = DoorsBeltsView.Learn.paint$lambda$8$lambda$7(this$1, (JSONObject) obj);
                    return paint$lambda$8$lambda$7;
                }
            });
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$8$lambda$7(Learn this$0, JSONObject it) {
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.next();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$12(DoorsBeltsView this$0, final Learn this$1) {
            this$0.req("done", this$1.id(), new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda18
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$12$lambda$11;
                    paint$lambda$12$lambda$11 = DoorsBeltsView.Learn.paint$lambda$12$lambda$11(this$1, (JSONObject) obj);
                    return paint$lambda$12$lambda$11;
                }
            });
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$12$lambda$11(Learn this$0, JSONObject r) {
            Intrinsics.checkNotNullParameter(r, "r");
            if (r.optBoolean("ok")) {
                this$0.setResult(r.optJSONObject("found"));
                this$0.getFound().put(this$0.id(), this$0.getResult());
                this$0.setPhase("result");
                this$0.paint();
            } else {
                this$0.fail(r);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$14(final DoorsBeltsView this$0) {
            DoorsBeltsView.req$default(this$0, "cancel", null, new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$14$lambda$13;
                    paint$lambda$14$lambda$13 = DoorsBeltsView.Learn.paint$lambda$14$lambda$13(this$0, (JSONObject) obj);
                    return paint$lambda$14$lambda$13;
                }
            }, 2, null);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$14$lambda$13(DoorsBeltsView this$0, JSONObject it) {
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.closeLearn();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$16(Learn this$0) {
            this$0.next();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$17(Learn this$0) {
            this$0.setPhase("ready");
            this$0.paint();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$21(final DoorsBeltsView this$0, final Learn this$1) {
            DoorsBeltsView.req$default(this$0, "save", null, new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda22
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$21$lambda$20;
                    paint$lambda$21$lambda$20 = DoorsBeltsView.Learn.paint$lambda$21$lambda$20(this$1, this$0, (JSONObject) obj);
                    return paint$lambda$21$lambda$20;
                }
            }, 2, null);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$21$lambda$20(Learn this$0, DoorsBeltsView this$1, JSONObject r) {
            Intrinsics.checkNotNullParameter(r, "r");
            if (r.optBoolean("ok")) {
                this$1.toast("Saved");
                this$1.closeLearn();
            } else {
                this$0.fail(r);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$23(final DoorsBeltsView this$0) {
            DoorsBeltsView.req$default(this$0, "cancel", null, new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda17
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$23$lambda$22;
                    paint$lambda$23$lambda$22 = DoorsBeltsView.Learn.paint$lambda$23$lambda$22(this$0, (JSONObject) obj);
                    return paint$lambda$23$lambda$22;
                }
            }, 2, null);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$23$lambda$22(DoorsBeltsView this$0, JSONObject it) {
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.closeLearn();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$24(Learn this$0) {
            this$0.getFound().clear();
            this$0.setI(0);
            this$0.setPhase("intro");
            this$0.paint();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$26(final DoorsBeltsView this$0) {
            DoorsBeltsView.req$default(this$0, "cancel", null, new Function1() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$26$lambda$25;
                    paint$lambda$26$lambda$25 = DoorsBeltsView.Learn.paint$lambda$26$lambda$25(this$0, (JSONObject) obj);
                    return paint$lambda$26$lambda$25;
                }
            }, 2, null);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paint$lambda$26$lambda$25(DoorsBeltsView this$0, JSONObject it) {
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.closeLearn();
            return Unit.INSTANCE;
        }

        public final void next() {
            String str;
            if (getI() + 1 < getList().size()) {
                setI(getI() + 1);
                str = "ready";
            } else {
                str = "summary";
            }
            setPhase(str);
            paint();
        }
    }
}
