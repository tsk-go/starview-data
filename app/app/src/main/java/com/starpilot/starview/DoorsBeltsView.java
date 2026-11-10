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
    private final DoorsBeltsView$poll$1 poll;
    private final int red;
    private final SimpleDateFormat shortFmt;
    private boolean shown;
    private JSONObject status;
    private long statusAt;
    private String statusErr;
    private final SimpleDateFormat timeFmt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.starpilot.starview.DoorsBeltsView$poll$1] */
    public DoorsBeltsView(Context ctx, CarUi ui) {
        super(ctx, ui);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        this.dayFmt = new SimpleDateFormat("EEEE, MMM d", Locale.US);
        this.timeFmt = new SimpleDateFormat("h:mm:ss a", Locale.US);
        this.shortFmt = new SimpleDateFormat("EEE MMM d, h:mm a", Locale.US);
        this.dateFmt = new SimpleDateFormat("MMM d", Locale.US);
        this.red = Color.rgb(255, 138, 128);
        LearnState st = saved;
        if (st != null) {
            if (Intrinsics.areEqual(st.getPhase(), "still")) {
                st.setPhase("ready");
            }
            this.learn = new Learn(this, st);
            getMain().post(new Runnable() { // from class: com.starpilot.starview.DoorsBeltsView$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    DoorsBeltsView.lambda$1$lambda$0(DoorsBeltsView.this);
                }
            });
        }
        this.poll = new Runnable() { // from class: com.starpilot.starview.DoorsBeltsView$poll$1
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                JSONObject jSONObject;
                long j;
                z = DoorsBeltsView.this.shown;
                if (z) {
                    long now = System.currentTimeMillis();
                    if (DoorsBeltsView.this.learn == null) {
                        j = DoorsBeltsView.this.statusAt;
                        if (now - j > 30000) {
                            DoorsBeltsView.this.loadStatus();
                        }
                    }
                    jSONObject = DoorsBeltsView.this.status;
                    boolean z2 = false;
                    if (jSONObject != null && jSONObject.optBoolean("carOn")) {
                        z2 = true;
                    }
                    if (z2 || DoorsBeltsView.this.learn != null) {
                        DoorsBeltsView.this.loadLive();
                    }
                    DoorsBeltsView.this.getMain().postDelayed(this, DoorsBeltsView.this.learn != null ? 5000L : 2000L);
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

        /* JADX WARN: Multi-variable type inference failed */
        public LearnState(List<? extends JSONObject> list) {
            Intrinsics.checkNotNullParameter(list, "list");
            this.list = list;
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

    /* JADX WARN: Removed duplicated region for block: B:107:0x057b  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x05ec  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x05f2  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x05f5  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x05ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void paint() {
        String str;
        int count$iv;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String day;
        String day2;
        String str8;
        String str9;
        String str10;
        LinearLayout fc;
        int i;
        String str11;
        List parts;
        String str12;
        String str13;
        String k;
        String str14;
        getCol().removeAllViews();
        JSONObject s = this.status;
        LinearLayout head = card();
        add(head);
        String str15 = "device";
        head.addView(title("Doors & belts", "car", "device"));
        if (s == null) {
            DoorsBeltsView doorsBeltsView = this;
            String str16 = this.statusErr;
            if (str16 == null) {
                str16 = "Asking the comma…";
            }
            head.addView(CardPage.line$default(doorsBeltsView, str16, 0, 0.0f, 6, null));
            return;
        }
        head.addView(CardPage.line$default(this, "The comma checks every door and seat-belt switch after each drive, from the drive's own logs, once the car is off. Nothing extra runs while driving.", 0, 0.0f, 6, null));
        JSONObject scanning = s.optJSONObject("scanning");
        int pending = s.optInt("pending");
        DoorsBeltsView doorsBeltsView2 = this;
        if (scanning != null) {
            str = "Checking drives now: " + scanning.optInt("done") + " of " + scanning.optInt("total") + " minutes read";
        } else if (pending > 0) {
            str = pending + " minute" + (pending == 1 ? "" : "s") + " of driving to check - it starts a couple of minutes after the car is off";
        } else {
            str = "Every drive is checked";
        }
        head.addView(CardPage.line$default(doorsBeltsView2, str, CarUi.Companion.getTEXT(), 0.0f, 4, null));
        long learnedAt = s.optLong("learnedAt", 0L);
        Iterable $this$count$iv = items();
        if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
            count$iv = 0;
        } else {
            count$iv = 0;
            for (Object element$iv : $this$count$iv) {
                JSONObject it = (JSONObject) element$iv;
                JSONObject scanning2 = scanning;
                int pending2 = pending;
                Iterable $this$count$iv2 = $this$count$iv;
                if (Intrinsics.areEqual(it.optString("source"), "learned") || Intrinsics.areEqual(it.optString("source"), "standard")) {
                    count$iv++;
                    if (count$iv < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                    scanning = scanning2;
                    pending = pending2;
                    $this$count$iv = $this$count$iv2;
                } else {
                    scanning = scanning2;
                    pending = pending2;
                    $this$count$iv = $this$count$iv2;
                }
            }
        }
        int known = count$iv;
        DoorsBeltsView doorsBeltsView3 = this;
        if (learnedAt > 0) {
            str2 = "s";
            str3 = "";
            str4 = "Watching " + known + " of " + items().size() + " switches · learned " + this.dateFmt.format(new Date(1000 * learnedAt));
        } else {
            str2 = "s";
            str3 = "";
            str4 = "Watching the 4 doors and the driver's belt. Learn the rest once (car on, in Park, about 5 minutes).";
        }
        head.addView(CardPage.line$default(doorsBeltsView3, str4, 0, 0.0f, 6, null));
        View[] viewArr = new View[1];
        viewArr[0] = getUi().button(learnedAt > 0 ? "Learn again…" : "Learn…", learnedAt == 0, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$10;
                paint$lambda$10 = DoorsBeltsView.paint$lambda$10(DoorsBeltsView.this);
                return paint$lambda$10;
            }
        });
        head.addView(buttons(viewArr));
        LinearLayout it2 = card();
        add(it2);
        this.nowCard = it2;
        paintNow();
        JSONArray drives = s.optJSONArray("drives");
        if (drives == null) {
            drives = new JSONArray();
        }
        HashMap fast = new HashMap();
        HashMap fastDrives = new HashMap();
        HashMap total = new HashMap();
        int i2 = 0;
        int length = drives.length();
        while (true) {
            str5 = "changes";
            LinearLayout head2 = head;
            str6 = "keys(...)";
            int known2 = known;
            str7 = "fast";
            if (i2 >= length) {
                break;
            }
            long learnedAt2 = learnedAt;
            JSONObject d = drives.getJSONObject(i2);
            JSONObject f = d.optJSONObject("fast");
            if (f != null) {
                Iterator<String> keys = f.keys();
                Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
                while (keys.hasNext()) {
                    Iterator<String> it3 = keys;
                    String k2 = keys.next();
                    String str17 = str2;
                    HashMap hashMap = fast;
                    Integer num = (Integer) fast.get(k2);
                    JSONObject f2 = f;
                    hashMap.put(k2, Integer.valueOf((num != null ? num.intValue() : 0) + f.optInt(k2)));
                    HashMap hashMap2 = fastDrives;
                    Integer num2 = (Integer) fastDrives.get(k2);
                    hashMap2.put(k2, Integer.valueOf((num2 != null ? num2.intValue() : 0) + 1));
                    str2 = str17;
                    keys = it3;
                    f = f2;
                }
                str14 = str2;
                Unit unit = Unit.INSTANCE;
                Unit unit2 = Unit.INSTANCE;
            } else {
                str14 = str2;
            }
            JSONObject c = d.optJSONObject("changes");
            if (c != null) {
                Iterator<String> keys2 = c.keys();
                Intrinsics.checkNotNullExpressionValue(keys2, "keys(...)");
                while (keys2.hasNext()) {
                    String k3 = keys2.next();
                    HashMap hashMap3 = total;
                    Integer num3 = (Integer) total.get(k3);
                    hashMap3.put(k3, Integer.valueOf((num3 != null ? num3.intValue() : 0) + c.optInt(k3)));
                    c = c;
                }
                Unit unit3 = Unit.INSTANCE;
                Unit unit4 = Unit.INSTANCE;
            }
            i2++;
            head = head2;
            known = known2;
            learnedAt = learnedAt2;
            str2 = str14;
        }
        String str18 = str2;
        LinearLayout fc2 = card();
        add(fc2);
        fc2.addView(title("Flickers", "pulse", "device"));
        if (drives.length() == 0) {
            fc2.addView(CardPage.line$default(this, "No drive checked yet.", 0, 0.0f, 6, null));
        } else if (fast.isEmpty()) {
            fc2.addView(CardPage.line$default(this, "No flicker in the last " + drives.length() + " drive" + (drives.length() == 1 ? str3 : str18) + ": no switch changed twice within 3 seconds.", CarUi.Companion.getACCENT(), 0.0f, 4, null));
        } else {
            Iterable entrySet = fast.entrySet();
            Intrinsics.checkNotNullExpressionValue(entrySet, "<get-entries>(...)");
            Iterable $this$sortedByDescending$iv = entrySet;
            Iterator it4 = CollectionsKt.sortedWith($this$sortedByDescending$iv, new Comparator() { // from class: com.starpilot.starview.DoorsBeltsView$paint$$inlined$sortedByDescending$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Map.Entry it5 = (Map.Entry) t2;
                    Integer num4 = (Integer) it5.getValue();
                    Map.Entry it6 = (Map.Entry) t;
                    return ComparisonsKt.compareValues(num4, (Integer) it6.getValue());
                }
            }).iterator();
            while (it4.hasNext()) {
                Object next = it4.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                Map.Entry entry = (Map.Entry) next;
                Object key = entry.getKey();
                Intrinsics.checkNotNullExpressionValue(key, "component1(...)");
                String k4 = (String) key;
                Object value = entry.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "component2(...)");
                Integer n = (Integer) value;
                String nameOf = nameOf(k4);
                HashMap fast2 = fast;
                Iterator it5 = it4;
                String str19 = n.intValue() == 1 ? str3 : str18;
                Object obj = fastDrives.get(k4);
                HashMap fastDrives2 = fastDrives;
                int length2 = drives.length();
                Integer num4 = (Integer) total.get(k4);
                fc2.addView(line(nameOf + ": " + n + " quick change" + str19 + " in " + obj + " of the last " + length2 + " drives (" + (num4 != null ? num4.intValue() : n.intValue()) + " changes in all)", CarUi.Companion.getWARN(), 17.0f));
                fast = fast2;
                it4 = it5;
                fastDrives = fastDrives2;
                total = total;
            }
        }
        LinearLayout dc = card();
        add(dc);
        dc.addView(title("Drives", "history", "device"));
        if (drives.length() == 0) {
            dc.addView(CardPage.line$default(this, "Each drive shows up here after the car is off.", 0, 0.0f, 6, null));
        }
        int i3 = 0;
        int min = Math.min(drives.length(), 20);
        while (i3 < min) {
            JSONObject d2 = drives.getJSONObject(i3);
            JSONArray drives2 = drives;
            final JSONObject c2 = d2.optJSONObject(str5);
            String str20 = str5;
            JSONObject f3 = d2.optJSONObject(str7);
            JSONObject m = d2.optJSONObject("moving");
            if (c2 == null) {
                str8 = str6;
                str9 = str7;
                str10 = str15;
                fc = fc2;
                i = min;
                str11 = str3;
            } else if (c2.length() == 0) {
                str8 = str6;
                str9 = str7;
                str10 = str15;
                fc = fc2;
                i = min;
                str11 = str3;
            } else {
                fc = fc2;
                Iterator<String> keys3 = c2.keys();
                Intrinsics.checkNotNullExpressionValue(keys3, str6);
                Iterable $this$sortedByDescending$iv2 = SequencesKt.toList(SequencesKt.asSequence(keys3));
                str8 = str6;
                Iterable $this$map$iv = CollectionsKt.sortedWith($this$sortedByDescending$iv2, new Comparator() { // from class: com.starpilot.starview.DoorsBeltsView$paint$$inlined$sortedByDescending$2
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        String it6 = (String) t2;
                        String it7 = (String) t;
                        return ComparisonsKt.compareValues(Integer.valueOf(c2.optInt(it6)), Integer.valueOf(c2.optInt(it7)));
                    }
                });
                i = min;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                Iterable $this$mapTo$iv$iv = $this$map$iv;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    Iterable $this$map$iv2 = $this$map$iv;
                    String k5 = (String) item$iv$iv;
                    Intrinsics.checkNotNull(k5);
                    Iterable $this$mapTo$iv$iv2 = $this$mapTo$iv$iv;
                    String nameOf2 = nameOf(k5);
                    String str21 = str3;
                    int optInt = c2.optInt(k5);
                    String str22 = str7;
                    JSONObject c3 = c2;
                    String[] strArr = new String[2];
                    if (f3 != null) {
                        Integer valueOf = Integer.valueOf(f3.optInt(k5));
                        if ((valueOf.intValue() > 0 ? 1 : 0) == 0) {
                            valueOf = null;
                        }
                        if (valueOf != null) {
                            str12 = str15;
                            str13 = valueOf.intValue() + " quick";
                            strArr[0] = str13;
                            if (m != null) {
                                Integer valueOf2 = Integer.valueOf(m.optInt(k5));
                                if ((valueOf2.intValue() > 0 ? 1 : 0) == 0) {
                                    valueOf2 = null;
                                }
                                if (valueOf2 != null) {
                                    k = valueOf2.intValue() + " moving";
                                    strArr[1] = k;
                                    String it6 = CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull((Object[]) strArr), ", ", null, null, 0, null, null, 62, null);
                                    destination$iv$iv.add(nameOf2 + " " + optInt + "×" + ((Object) (it6.length() == 0 ? str21 : " (" + it6 + ")")));
                                    $this$map$iv = $this$map$iv2;
                                    $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                                    str3 = str21;
                                    c2 = c3;
                                    str7 = str22;
                                    str15 = str12;
                                }
                            }
                            k = null;
                            strArr[1] = k;
                            String it62 = CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull((Object[]) strArr), ", ", null, null, 0, null, null, 62, null);
                            destination$iv$iv.add(nameOf2 + " " + optInt + "×" + ((Object) (it62.length() == 0 ? str21 : " (" + it62 + ")")));
                            $this$map$iv = $this$map$iv2;
                            $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                            str3 = str21;
                            c2 = c3;
                            str7 = str22;
                            str15 = str12;
                        }
                    }
                    str12 = str15;
                    str13 = null;
                    strArr[0] = str13;
                    if (m != null) {
                    }
                    k = null;
                    strArr[1] = k;
                    String it622 = CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull((Object[]) strArr), ", ", null, null, 0, null, null, 62, null);
                    destination$iv$iv.add(nameOf2 + " " + optInt + "×" + ((Object) (it622.length() == 0 ? str21 : " (" + it622 + ")")));
                    $this$map$iv = $this$map$iv2;
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                    str3 = str21;
                    c2 = c3;
                    str7 = str22;
                    str15 = str12;
                }
                str9 = str7;
                str10 = str15;
                str11 = str3;
                parts = (List) destination$iv$iv;
                JSONObject s2 = s;
                List parts2 = parts;
                dc.addView(line(this.shortFmt.format(new Date(d2.optLong("start") * 1000)) + " · " + d2.optInt("segs") + " min", CarUi.Companion.getTEXT(), 17.0f));
                dc.addView(line("      " + CollectionsKt.joinToString$default(parts2, " · ", null, null, 0, null, null, 62, null), (f3 != null || f3.length() <= 0) ? CarUi.Companion.getDIM() : CarUi.Companion.getWARN(), 15.0f));
                i3++;
                s = s2;
                drives = drives2;
                str5 = str20;
                fc2 = fc;
                str6 = str8;
                min = i;
                str3 = str11;
                str7 = str9;
                str15 = str10;
            }
            parts = CollectionsKt.listOf("nothing changed");
            JSONObject s22 = s;
            List parts22 = parts;
            dc.addView(line(this.shortFmt.format(new Date(d2.optLong("start") * 1000)) + " · " + d2.optInt("segs") + " min", CarUi.Companion.getTEXT(), 17.0f));
            dc.addView(line("      " + CollectionsKt.joinToString$default(parts22, " · ", null, null, 0, null, null, 62, null), (f3 != null || f3.length() <= 0) ? CarUi.Companion.getDIM() : CarUi.Companion.getWARN(), 15.0f));
            i3++;
            s = s22;
            drives = drives2;
            str5 = str20;
            fc2 = fc;
            str6 = str8;
            min = i;
            str3 = str11;
            str7 = str9;
            str15 = str10;
        }
        String str23 = str7;
        String str24 = str15;
        JSONObject s3 = s;
        JSONArray evs = s3.optJSONArray("events");
        if (evs == null) {
            evs = new JSONArray();
        }
        if (evs.length() > 0) {
            LinearLayout ec = card();
            add(ec);
            ec.addView(title("Every change", "bell", str24));
            String day3 = "";
            int i4 = 0;
            int min2 = Math.min(evs.length(), 300);
            while (i4 < min2) {
                JSONObject e = evs.getJSONObject(i4);
                JSONArray evs2 = evs;
                JSONObject s4 = s3;
                Date t = new Date((long) (1000 * e.optDouble("t")));
                String dd = this.dayFmt.format(t);
                if (!Intrinsics.areEqual(dd, day3)) {
                    day3 = dd;
                    CarUi ui = getUi();
                    Intrinsics.checkNotNull(dd);
                    ec.addView(ui.header(dd));
                }
                String id = e.optString("item");
                String[] strArr2 = new String[2];
                if (e.optBoolean("moving")) {
                    day = day3;
                    day2 = e.optInt("mph") + " mph";
                } else {
                    day = day3;
                    day2 = "parked";
                }
                strArr2[0] = day2;
                String str25 = str23;
                strArr2[1] = e.optBoolean(str25) ? "QUICK" : null;
                String tail = CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull((Object[]) strArr2), " · ", null, null, 0, null, null, 62, null);
                String format = this.timeFmt.format(t);
                Intrinsics.checkNotNull(id);
                int i5 = min2;
                LinearLayout dc2 = dc;
                ec.addView(line(format + "   " + nameOf(id) + " " + word(id, e.optBoolean("on")) + "   · " + tail, e.optBoolean(str25) ? CarUi.Companion.getWARN() : CarUi.Companion.getTEXT(), 16.0f));
                i4++;
                str23 = str25;
                evs = evs2;
                s3 = s4;
                day3 = day;
                min2 = i5;
                dc = dc2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$10(DoorsBeltsView this$0) {
        this$0.openLearn();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0138, code lost:
    
        if (r13.equals("null") == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0144, code lost:
    
        r10 = "not learned";
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0141, code lost:
    
        if (r13.equals("") == false) goto L53;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void paintNow() {
        String str;
        String str2;
        int dim;
        String s2;
        LinearLayout c = this.nowCard;
        if (c == null) {
            return;
        }
        c.removeAllViews();
        c.addView(title("Right now", "eye", "device"));
        JSONObject s = this.status;
        if (s == null) {
            return;
        }
        JSONObject lv = this.live;
        if (!s.optBoolean("carOn")) {
            c.addView(CardPage.line$default(this, "Shows every switch live with the car on and in Park.", 0, 0.0f, 6, null));
            return;
        }
        if (lv == null) {
            DoorsBeltsView doorsBeltsView = this;
            String str3 = this.liveErr;
            if (str3 == null) {
                str3 = "Reading…";
            }
            c.addView(CardPage.line$default(doorsBeltsView, str3, 0, 0.0f, 6, null));
            return;
        }
        JSONObject words = lv.optJSONObject("words");
        LinearLayout grid = new LinearLayout(getContext());
        ?? r8 = 1;
        grid.setOrientation(1);
        for (JSONObject it : items()) {
            String id = it.optString("id");
            String w = null;
            if (words != null && (s2 = words.optString(id)) != null) {
                if (((!(s2.length() > 0 ? r8 : false) || Intrinsics.areEqual(s2, "null")) ? null : r8) != null) {
                    w = s2;
                }
            }
            String src = it.optString("source");
            LinearLayout $this$paintNow_u24lambda_u2426 = new LinearLayout(getContext());
            $this$paintNow_u24lambda_u2426.setOrientation(0);
            $this$paintNow_u24lambda_u2426.setGravity(16);
            $this$paintNow_u24lambda_u2426.setPadding(0, getUi().dp(3), 0, getUi().dp(3));
            TextView $this$paintNow_u24lambda_u2426_u24lambda_u2424 = new TextView($this$paintNow_u24lambda_u2426.getContext());
            JSONObject s3 = s;
            $this$paintNow_u24lambda_u2426_u24lambda_u2424.setText(it.optString("name"));
            $this$paintNow_u24lambda_u2426_u24lambda_u2424.setTextColor(CarUi.Companion.getTEXT());
            $this$paintNow_u24lambda_u2426_u24lambda_u2424.setTextSize(17.0f);
            $this$paintNow_u24lambda_u2426.addView($this$paintNow_u24lambda_u2426_u24lambda_u2424, new LinearLayout.LayoutParams(getUi().dp(260), -2));
            TextView $this$paintNow_u24lambda_u2426_u24lambda_u2425 = new TextView($this$paintNow_u24lambda_u2426.getContext());
            if (w != null) {
                str2 = w;
            } else {
                if (src != null) {
                    switch (src.hashCode()) {
                        case -1282241876:
                            if (src.equals("not on bus")) {
                                str = "not on the comma's bus";
                                break;
                            }
                            break;
                        case 0:
                            break;
                        case 3392903:
                            break;
                    }
                    str2 = str;
                }
                str = "–";
                str2 = str;
            }
            $this$paintNow_u24lambda_u2426_u24lambda_u2425.setText(str2);
            if (Intrinsics.areEqual(w, "open")) {
                dim = CarUi.Companion.getWARN();
            } else if (Intrinsics.areEqual(w, "buckled")) {
                dim = CarUi.Companion.getACCENT();
            } else {
                CarUi.Companion_ companion = CarUi.Companion;
                dim = w == null ? companion.getDIM() : companion.getTEXT();
            }
            $this$paintNow_u24lambda_u2426_u24lambda_u2425.setTextColor(dim);
            $this$paintNow_u24lambda_u2426_u24lambda_u2425.setTextSize(17.0f);
            $this$paintNow_u24lambda_u2426_u24lambda_u2425.setTypeface(w != null ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            $this$paintNow_u24lambda_u2426.addView($this$paintNow_u24lambda_u2426_u24lambda_u2425);
            grid.addView($this$paintNow_u24lambda_u2426);
            r8 = 1;
            s = s3;
        }
        c.addView(grid);
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
            if (SetsKt.setOf((Object[]) new String[]{"intro", "error"}).contains(getPhase())) {
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
                        for (Map.Entry entry$iv : $this$filterValues$iv.entrySet()) {
                            JSONObject it = entry$iv.getValue();
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
                                paint$lambda$21 = DoorsBeltsView.Learn.paint$lambda$21(DoorsBeltsView.this, this);
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
                                paint$lambda$2 = DoorsBeltsView.Learn.paint$lambda$2(DoorsBeltsView.this, this);
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
                        paint$lambda$12 = DoorsBeltsView.Learn.paint$lambda$12(DoorsBeltsView.this, this);
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
                    paint$lambda$6 = DoorsBeltsView.Learn.paint$lambda$6(DoorsBeltsView.this, this);
                    return paint$lambda$6;
                }
            }), CarUi.button$default(ui9, "Skip", false, new Function0() { // from class: com.starpilot.starview.DoorsBeltsView$Learn$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paint$lambda$8;
                    paint$lambda$8 = DoorsBeltsView.Learn.paint$lambda$8(DoorsBeltsView.this, this);
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
                    paint$lambda$2$lambda$1 = DoorsBeltsView.Learn.paint$lambda$2$lambda$1(DoorsBeltsView.Learn.this, this$0, (JSONObject) obj);
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
                        DoorsBeltsView.Learn.paint$lambda$2$lambda$1$lambda$0(DoorsBeltsView.this, this$0);
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
                    paint$lambda$6$lambda$5 = DoorsBeltsView.Learn.paint$lambda$6$lambda$5(DoorsBeltsView.Learn.this, (JSONObject) obj);
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
                    paint$lambda$10$lambda$9 = DoorsBeltsView.Learn.paint$lambda$10$lambda$9(DoorsBeltsView.this, (JSONObject) obj);
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
                    paint$lambda$8$lambda$7 = DoorsBeltsView.Learn.paint$lambda$8$lambda$7(DoorsBeltsView.Learn.this, (JSONObject) obj);
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
                    paint$lambda$12$lambda$11 = DoorsBeltsView.Learn.paint$lambda$12$lambda$11(DoorsBeltsView.Learn.this, (JSONObject) obj);
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
                    paint$lambda$14$lambda$13 = DoorsBeltsView.Learn.paint$lambda$14$lambda$13(DoorsBeltsView.this, (JSONObject) obj);
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
                    paint$lambda$21$lambda$20 = DoorsBeltsView.Learn.paint$lambda$21$lambda$20(DoorsBeltsView.Learn.this, this$0, (JSONObject) obj);
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
                    paint$lambda$23$lambda$22 = DoorsBeltsView.Learn.paint$lambda$23$lambda$22(DoorsBeltsView.this, (JSONObject) obj);
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
                    paint$lambda$26$lambda$25 = DoorsBeltsView.Learn.paint$lambda$26$lambda$25(DoorsBeltsView.this, (JSONObject) obj);
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
