package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.starpilot.starview.HealthHistory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: JournalPages.kt */
public final class CommaHealthView extends CardPage {
    private final LinearLayout hw;
    private final LinearLayout model;
    private final CommaHealthView$poll$1 poll;
    private final LinearLayout procs;
    private boolean shown;
    private final LinearLayout summary;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.starpilot.starview.CommaHealthView$poll$1] */
    public CommaHealthView(Context ctx, CarUi ui) {
        super(ctx, ui);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        this.summary = card();
        this.procs = card();
        this.model = card();
        this.hw = card();
        this.poll = new Runnable() { // from class: com.starpilot.starview.CommaHealthView$poll$1
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                z = CommaHealthView.this.shown;
                if (z) {
                    CommaHealthView.this.refresh();
                    CommaHealthView.this.getMain().postDelayed(this, 10000L);
                }
            }
        };
        add(this.summary);
        add(this.procs);
        add(this.model);
        add(this.hw);
    }

    public final void start() {
        this.shown = true;
        getMain().removeCallbacks(this.poll);
        getMain().post(this.poll);
    }

    public final void stop() {
        this.shown = false;
        getMain().removeCallbacks(this.poll);
    }

    @Override // com.starpilot.starview.CardPage
    public void refresh() {
        final Telemetry t = Link.INSTANCE.getTelemetry();
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            this.summary.removeAllViews();
            this.summary.addView(title("Comma health", "chip", "device"));
            this.summary.addView(CardPage.line$default(this, "Not connected to the comma.", 0, 0.0f, 6, null));
        } else {
            g.get("/api/system/monitor", new Function2() { // from class: com.starpilot.starview.CommaHealthView$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit refresh$lambda$0;
                    refresh$lambda$0 = CommaHealthView.refresh$lambda$0(CommaHealthView.this, t, (JSONObject) obj, (String) obj2);
                    return refresh$lambda$0;
                }
            });
            g.get("/api/models/status", new Function2() { // from class: com.starpilot.starview.CommaHealthView$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit refresh$lambda$1;
                    refresh$lambda$1 = CommaHealthView.refresh$lambda$1(CommaHealthView.this, t, (JSONObject) obj, (String) obj2);
                    return refresh$lambda$1;
                }
            });
            paintHardware(t);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$0(CommaHealthView this$0, Telemetry $t, JSONObject o, String err) {
        if (o != null) {
            this$0.paint(o, $t);
        } else {
            this$0.summary.removeAllViews();
            this$0.summary.addView(this$0.title("Comma health", "chip", "device"));
            this$0.summary.addView(CardPage.line$default(this$0, err == null ? "Galaxy didn't answer" : err, 0, 0.0f, 6, null));
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$1(CommaHealthView this$0, Telemetry $t, JSONObject o, String str) {
        this$0.paintModel(o, $t);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x008a, code lost:
    
        if (r2 == null) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x011c, code lost:
    
        if (r5 == null) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0572  */
    /* JADX WARN: Removed duplicated region for block: B:76:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0325  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void paint(JSONObject o, Telemetry t) {
        Pair<String, String>[] pairArr;
        String str;
        String str2;
        String str3;
        Object obj;
        String dur;
        List pts;
        JSONArray arr;
        List take;
        List list;
        Iterator it;
        String dur2;
        SimpleDateFormat simpleDateFormat;
        String thermalStatus;
        JSONObject mem = o.optJSONObject("memory");
        JSONObject st = o.optJSONObject("storage");
        double pct = mem != null ? mem.optDouble("percent", Double.NaN) : Double.NaN;
        this.summary.removeAllViews();
        this.summary.addView(title("Comma health", "chip", "device"));
        LinearLayout linearLayout = this.summary;
        Pair<String, String>[] pairArr2 = new Pair[5];
        String str4 = "–";
        if ((Double.isInfinite(pct) || Double.isNaN(pct)) ? false : true) {
            pairArr = pairArr2;
            str = Math.round(pct) + "%";
        } else {
            pairArr = pairArr2;
            str = "–";
        }
        String str5 = "";
        if (mem != null) {
            double it2 = mem.optDouble("availableMiB");
            str2 = Math.round(it2) + " MB free";
        }
        str2 = "";
        pairArr[0] = TuplesKt.to(str, "memory · " + str2);
        pairArr[1] = TuplesKt.to(Math.round(o.optDouble("cpuPercent", 0.0d)) + "%", "CPU");
        if (t != null) {
            Float valueOf = Float.valueOf(t.getCpuTempC());
            float it3 = valueOf.floatValue();
            if (!(it3 > 0.0f)) {
                valueOf = null;
            }
            if (valueOf != null) {
                float it4 = valueOf.floatValue();
                str3 = Math.round(((9 * it4) / 5) + 32) + "°F";
            }
        }
        str3 = "–";
        if (t != null && (thermalStatus = t.getThermalStatus()) != null) {
            str5 = thermalStatus;
        }
        pairArr[2] = TuplesKt.to(str3, "CPU temp · " + str5);
        if (st != null) {
            Double valueOf2 = Double.valueOf(st.optDouble("usedGiB", Double.NaN));
            double it5 = valueOf2.doubleValue();
            if (!((Double.isInfinite(it5) || Double.isNaN(it5)) ? false : true)) {
                valueOf2 = null;
            }
            if (valueOf2 != null) {
                double it6 = valueOf2.doubleValue();
                obj = "storage";
                String str6 = Math.round(it6) + "/" + Math.round(st.optDouble("totalGiB")) + " GB";
                if (str6 != null) {
                    str4 = str6;
                }
                pairArr[3] = TuplesKt.to(str4, obj);
                dur = JournalPagesKt.dur(o.optDouble("uptimeSeconds", 0.0d));
                pairArr[4] = TuplesKt.to(dur, "up");
                linearLayout.addView(stats(pairArr));
                pts = HealthHistory.INSTANCE.points();
                if (pts.size() < 2) {
                    LinearLayout linearLayout2 = this.summary;
                    dur2 = JournalPagesKt.dur((((HealthHistory.Point) CollectionsKt.last(pts)).getTMs() - ((HealthHistory.Point) CollectionsKt.first(pts)).getTMs()) / 1000.0d);
                    linearLayout2.addView(CardPage.line$default(this, "Memory, last " + dur2 + " (the line is 90%: \"Low Memory\" starts there)", 0, 0.0f, 6, null), CarUi.lp$default(getUi(), 0, 4, 1, null));
                    LinearLayout linearLayout3 = this.summary;
                    Context context = getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                    List $this$map$iv = pts;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        HealthHistory.Point it7 = (HealthHistory.Point) item$iv$iv;
                        destination$iv$iv.add(Float.valueOf(it7.getMemPct()));
                    }
                    ArrayList arrayList = (List) destination$iv$iv;
                    int warn = pct > 85.0d ? CarUi.Companion.getWARN() : CarUi.Companion.getACCENT();
                    Float valueOf3 = Float.valueOf(90.0f);
                    simpleDateFormat = JournalPagesKt.timeFmt;
                    linearLayout3.addView(new Spark(context, arrayList, 100.0f, warn, valueOf3, CollectionsKt.listOf((Object[]) new String[]{simpleDateFormat.format(new Date(((HealthHistory.Point) CollectionsKt.first(pts)).getTMs())), "now"})), new LinearLayout.LayoutParams(-1, getUi().dp(110)));
                } else {
                    this.summary.addView(CardPage.line$default(this, "The memory trend starts drawing after a minute with the tablet linked.", 0, 0.0f, 6, null));
                }
                this.procs.removeAllViews();
                this.procs.addView(title("What holds the memory", "data", "device"));
                arr = o.optJSONArray("processes");
                if (arr != null) {
                    take = CollectionsKt.emptyList();
                } else {
                    Iterable $this$mapNotNull$iv = RangesKt.until(0, arr.length());
                    Collection destination$iv$iv2 = new ArrayList();
                    Iterator<Integer> it8 = $this$mapNotNull$iv.iterator();
                    while (it8.hasNext()) {
                        int element$iv$iv$iv = ((IntIterator) it8).nextInt();
                        JSONObject optJSONObject = arr.optJSONObject(element$iv$iv$iv);
                        if (optJSONObject != null) {
                            destination$iv$iv2.add(optJSONObject);
                        }
                    }
                    Iterable $this$filter$iv = (List) destination$iv$iv2;
                    Collection destination$iv$iv3 = new ArrayList();
                    for (Object element$iv$iv : $this$filter$iv) {
                        JSONObject it9 = (JSONObject) element$iv$iv;
                        if (!it9.optBoolean("kernel")) {
                            destination$iv$iv3.add(element$iv$iv);
                        }
                    }
                    Iterable $this$sortedByDescending$iv = (List) destination$iv$iv3;
                    take = CollectionsKt.take(CollectionsKt.sortedWith($this$sortedByDescending$iv, new Comparator() { // from class: com.starpilot.starview.CommaHealthView$paint$$inlined$sortedByDescending$1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Comparator
                        public final int compare(T t2, T t3) {
                            JSONObject it10 = (JSONObject) t3;
                            JSONObject it11 = (JSONObject) t2;
                            return ComparisonsKt.compareValues(Double.valueOf(it10.optDouble("memoryMiB", 0.0d)), Double.valueOf(it11.optDouble("memoryMiB", 0.0d)));
                        }
                    }), 10);
                }
                list = take;
                JSONObject jSONObject = (JSONObject) CollectionsKt.firstOrNull(list);
                String str7 = "memoryMiB";
                double top = jSONObject != null ? jSONObject.optDouble("memoryMiB", 1.0d) : 1.0d;
                it = list.iterator();
                while (it.hasNext()) {
                    JSONObject pr = (JSONObject) it.next();
                    double mib = pr.optDouble(str7, 0.0d);
                    LinearLayout linearLayout4 = this.procs;
                    LinearLayout $this$paint_u24lambda_u2415 = new LinearLayout(getContext());
                    List pts2 = pts;
                    $this$paint_u24lambda_u2415.setOrientation(0);
                    $this$paint_u24lambda_u2415.setGravity(16);
                    JSONArray arr2 = arr;
                    Iterator it10 = it;
                    $this$paint_u24lambda_u2415.setPadding(0, getUi().dp(4), 0, getUi().dp(4));
                    TextView $this$paint_u24lambda_u2415_u24lambda_u2411 = new TextView($this$paint_u24lambda_u2415.getContext());
                    String optString = pr.optString("name");
                    String str8 = str7;
                    Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                    double pct2 = pct;
                    $this$paint_u24lambda_u2415_u24lambda_u2411.setText(StringsKt.take(StringsKt.substringAfterLast$default(optString, '/', (String) null, 2, (Object) null), 40));
                    $this$paint_u24lambda_u2415_u24lambda_u2411.setTextColor(CarUi.Companion.getTEXT());
                    $this$paint_u24lambda_u2415_u24lambda_u2411.setTextSize(16.0f);
                    $this$paint_u24lambda_u2415_u24lambda_u2411.setMaxLines(1);
                    $this$paint_u24lambda_u2415.addView($this$paint_u24lambda_u2415_u24lambda_u2411, new LinearLayout.LayoutParams(getUi().dp(300), -2));
                    FrameLayout $this$paint_u24lambda_u2415_u24lambda_u2413 = new FrameLayout($this$paint_u24lambda_u2415.getContext());
                    View $this$paint_u24lambda_u2415_u24lambda_u2413_u24lambda_u2412 = new View($this$paint_u24lambda_u2415_u24lambda_u2413.getContext());
                    $this$paint_u24lambda_u2415_u24lambda_u2413_u24lambda_u2412.setBackground(CarUi.rounded$default(getUi(), mib > 300.0d ? CarUi.Companion.getWARN() : Color.rgb(127, 180, 255), 6, null, 4, null));
                    $this$paint_u24lambda_u2415_u24lambda_u2413.addView($this$paint_u24lambda_u2415_u24lambda_u2413_u24lambda_u2412, new FrameLayout.LayoutParams(RangesKt.coerceAtLeast((int) (getUi().dp(420) * (mib / top)), getUi().dp(4)), getUi().dp(14), 16));
                    $this$paint_u24lambda_u2415.addView($this$paint_u24lambda_u2415_u24lambda_u2413, new LinearLayout.LayoutParams(0, getUi().dp(26), 1.0f));
                    TextView $this$paint_u24lambda_u2415_u24lambda_u2414 = new TextView($this$paint_u24lambda_u2415.getContext());
                    $this$paint_u24lambda_u2415_u24lambda_u2414.setText(Math.round(mib) + " MB");
                    $this$paint_u24lambda_u2415_u24lambda_u2414.setTextColor(CarUi.Companion.getDIM());
                    $this$paint_u24lambda_u2415_u24lambda_u2414.setTextSize(15.0f);
                    $this$paint_u24lambda_u2415_u24lambda_u2414.setGravity(8388613);
                    $this$paint_u24lambda_u2415.addView($this$paint_u24lambda_u2415_u24lambda_u2414, new LinearLayout.LayoutParams(getUi().dp(90), -2));
                    linearLayout4.addView($this$paint_u24lambda_u2415);
                    pts = pts2;
                    arr = arr2;
                    it = it10;
                    str7 = str8;
                    pct = pct2;
                }
                if (list.isEmpty()) {
                    return;
                }
                this.procs.addView(CardPage.line$default(this, "Galaxy didn't list processes.", 0, 0.0f, 6, null));
                return;
            }
        }
        obj = "storage";
        pairArr[3] = TuplesKt.to(str4, obj);
        dur = JournalPagesKt.dur(o.optDouble("uptimeSeconds", 0.0d));
        pairArr[4] = TuplesKt.to(dur, "up");
        linearLayout.addView(stats(pairArr));
        pts = HealthHistory.INSTANCE.points();
        if (pts.size() < 2) {
        }
        this.procs.removeAllViews();
        this.procs.addView(title("What holds the memory", "data", "device"));
        arr = o.optJSONArray("processes");
        if (arr != null) {
        }
        list = take;
        JSONObject jSONObject2 = (JSONObject) CollectionsKt.firstOrNull(list);
        String str72 = "memoryMiB";
        if (jSONObject2 != null) {
        }
        it = list.iterator();
        while (it.hasNext()) {
        }
        if (list.isEmpty()) {
        }
    }

    private final void paintModel(JSONObject o, Telemetry t) {
        this.model.removeAllViews();
        this.model.addView(title("Driving model", "chip", "device"));
        if (o == null) {
            this.model.addView(CardPage.line$default(this, "Model status not available", 0, 0.0f, 6, null));
            return;
        }
        LinearLayout linearLayout = this.model;
        String optString = o.optString("loadedId");
        if (StringsKt.isBlank(optString)) {
            optString = "–";
        }
        linearLayout.addView(line("Running: " + ((Object) optString), CarUi.Companion.getTEXT(), 18.0f));
        String optString2 = o.optString("health");
        if (StringsKt.isBlank(optString2)) {
            optString2 = null;
        }
        String it = optString2;
        if (it != null) {
            this.model.addView(CardPage.line$default(this, "Health: " + it, 0, 0.0f, 6, null));
        }
        String optString3 = o.optString("fallbackReason");
        if (StringsKt.isBlank(optString3)) {
            optString3 = null;
        }
        String it2 = optString3;
        if (it2 != null) {
            this.model.addView(CardPage.line$default(this, "Fell back because: " + it2, CarUi.Companion.getWARN(), 0.0f, 4, null));
        }
        String it3 = o.optString("requestedId");
        Intrinsics.checkNotNull(it3);
        String it4 = !StringsKt.isBlank(it3) && !Intrinsics.areEqual(it3, o.optString("loadedId")) ? it3 : null;
        if (it4 != null) {
            this.model.addView(CardPage.line$default(this, "Asked for: " + it4 + " (next start)", 0, 0.0f, 6, null));
        }
        this.model.addView(CardPage.line$default(this, t != null && t.getChestnutPresent() ? "eGPU: connected" : "eGPU: not seen", 0, 0.0f, 6, null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0076, code lost:
    
        if (r3 == null) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void paintHardware(Telemetry t) {
        String str;
        String str2;
        String str3;
        this.hw.removeAllViews();
        this.hw.addView(title("On the hub", "link", "tablet"));
        RtkStatus rtk = Ntrip.INSTANCE.getRtk();
        LinearLayout linearLayout = this.hw;
        CommaHealthView commaHealthView = this;
        boolean z = false;
        if (rtk != null) {
            str = rtk.getWords() + " · " + rtk.getUsed() + " sats" + (rtk.getHasFix() ? " · ±" + RtkStatus.Companion.accuracy(rtk.getHAccM(), false) : "");
        }
        str = "not seen";
        linearLayout.addView(CardPage.line$default(commaHealthView, "RTK receiver: " + str, 0, 0.0f, 6, null));
        this.hw.addView(CardPage.line$default(this, "eGPU: " + (t != null && t.getChestnutPresent() ? "connected" : "not seen"), 0, 0.0f, 6, null));
        LinearLayout linearLayout2 = this.hw;
        CommaHealthView commaHealthView2 = this;
        DataClient data = Link.INSTANCE.getData();
        if (data != null && data.getConnected()) {
            z = true;
        }
        if (z) {
            String host = Link.INSTANCE.getHost();
            str2 = "linked (" + (host != null ? host : "") + ")";
        } else {
            str2 = "not linked";
        }
        linearLayout2.addView(CardPage.line$default(commaHealthView2, "This tablet: " + str2, 0, 0.0f, 6, null));
        LinearLayout linearLayout3 = this.hw;
        CommaHealthView commaHealthView3 = this;
        if (t == null || (str3 = t.getNetworkType()) == null) {
            str3 = "–";
        }
        linearLayout3.addView(CardPage.line$default(commaHealthView3, "Network: " + str3, 0, 0.0f, 6, null));
    }
}
