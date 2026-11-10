package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.MapPane;
import com.starpilot.starview.Nav;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import android.app.AlertDialog.Builder;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import kotlin.collections.SetsKt;

/* compiled from: MapPane.kt */
public final class MapPane extends FrameLayout {
    private final Banner banner;
    private final LinearLayout bottomBar;
    private final LinearLayout btnRow;
    private final TextView closeBtn;
    private final float d;
    private String dismissedFaster;
    private final TextView etaBig;
    private final TextView etaSmall;
    private final TextView faster;
    private final Runnable hideInfo;
    private final Host host;
    private final TextView info;
    private String lastButtons;
    private String lastDest;
    private double[] lastKnownLL;
    private String lastKnownRaw;
    private int lastVersion;
    private boolean lookFresh;
    private final MapCanvas map;
    private final TextView northBtn;
    private final TextView note;
    private final LinearLayout pinRow;
    private final HorizontalScrollView pinScroll;
    private Nav.Line prefetchedFor;
    private String previewShown;
    private final TextView recenter;
    private final FrameLayout sheet;
    private String sheetKind;
    private int sheetVersion;
    private boolean showRoutesWhenReady;
    private final LinearLayout side;
    private int stepScroll;
    private int tickN;
    private final MapTiles tiles;
    private final TextView tiltBtn;
    private final TextView voiceBtn;

    /* compiled from: MapPane.kt */
    public interface Host {
        void closeMap();

        DataClient getData();

        boolean getNight();

        UiSettings getSettings();

        Telemetry getTelemetry();

        void navigateTo(String name, String address, double lat, double lon);

        void openSearch();
    }

    /* VF: from Vineflower (jadx: ) */
    public MapPane(Context var1, MapPane.Host var2) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "host");
        this.host = var2;
        this.d = this.getResources().getDisplayMetrics().density;
        this.tiles = new MapTiles(var1, new MapPane$$ExternalSyntheticLambda9(this));
        this.map = new MapPane.MapCanvas(this, var1);
        this.banner = new MapPane.Banner(this, var1);
        this.faster = this.chip();
        this.note = new TextView(var1);
        this.info = new TextView(var1);
        this.bottomBar = new LinearLayout(var1);
        this.etaBig = new TextView(var1);
        this.etaSmall = new TextView(var1);
        this.btnRow = new LinearLayout(var1);
        LinearLayout var9 = new LinearLayout(var1);
        var9.setOrientation(0);
        this.pinRow = var9;
        this.pinScroll = new HorizontalScrollView(var1);
        this.side = new LinearLayout(var1);
        this.sheet = new FrameLayout(var1);
        this.sheetKind = "";
        this.sheetVersion = -1;
        this.lastVersion = -1;
        this.dismissedFaster = "";
        this.lastDest = "";
        this.setClipChildren(true);
        this.setBackgroundColor(Color.rgb(27, 30, 36));
        this.addView(this.map, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        View var3 = this.banner;
        android.widget.FrameLayout.LayoutParams var4 = new android.widget.FrameLayout.LayoutParams(-1, this.dpi(92), 48);
        var4.setMargins(this.dpi(10), this.dpi(10), this.dpi(10), 0);
        Unit var10 = Unit.INSTANCE;
        this.addView(var3, (android.view.ViewGroup.LayoutParams)var4);
        this.banner.setVisibility(8);
        var3 = (View)this.faster;
        var4 = new android.widget.FrameLayout.LayoutParams(-2, -2, 49);
        var4.topMargin = this.dpi(110);
        Unit var11 = Unit.INSTANCE;
        this.addView(var3, (android.view.ViewGroup.LayoutParams)var4);
        this.faster.setVisibility(8);
        TextView var12 = this.info;
        var12.setTextColor(-1);
        var12.setTextSize(14.0F);
        var12.setPadding(this.dpi(14), this.dpi(10), this.dpi(14), this.dpi(10));
        var12.setBackground((Drawable)card$default(this, -434101200, 14.0F, 0, false, 12, null));
        var12.setVisibility(8);
        var12.setMaxLines(4);
        View var13 = (View)this.info;
        android.widget.FrameLayout.LayoutParams var24 = new android.widget.FrameLayout.LayoutParams(-2, -2, 49);
        var24.topMargin = this.dpi(160);
        var24.setMarginStart(this.dpi(16));
        var24.setMarginEnd(this.dpi(16));
        Unit var32 = Unit.INSTANCE;
        this.addView(var13, (android.view.ViewGroup.LayoutParams)var24);
        TextView var14 = this.note;
        var14.setTextColor(-1);
        var14.setTextSize(16.0F);
        var14.setGravity(17);
        var14.setPadding(this.dpi(18), this.dpi(14), this.dpi(18), this.dpi(14));
        var14.setBackground((Drawable)card$default(this, -434101200, 16.0F, 0, false, 12, null));
        var14.setVisibility(8);
        var3 = (View)this.note;
        var4 = new android.widget.FrameLayout.LayoutParams(-2, -2, 17);
        var4.setMarginStart(this.dpi(20));
        var4.setMarginEnd(this.dpi(20));
        Unit var15 = Unit.INSTANCE;
        this.addView(var3, (android.view.ViewGroup.LayoutParams)var4);
        this.side.setOrientation(1);
        this.recenter = _init_$sideBtn(this, "◎", new MapPane$$ExternalSyntheticLambda10(this));
        _init_$sideBtn(this, "+", new MapPane$$ExternalSyntheticLambda12(this));
        _init_$sideBtn(this, "−", new MapPane$$ExternalSyntheticLambda13(this));
        this.northBtn = _init_$sideBtn(this, "▲", new MapPane$$ExternalSyntheticLambda14(this));
        this.tiltBtn = _init_$sideBtn(this, "3D", new MapPane$$ExternalSyntheticLambda15(this));
        this.voiceBtn = _init_$sideBtn(this, "\ud83d\udd0a", new MapPane$$ExternalSyntheticLambda16(this));
        this.closeBtn = _init_$sideBtn(this, "‹", new MapPane$$ExternalSyntheticLambda17(this));
        View var34 = (View)this.side;
        android.widget.FrameLayout.LayoutParams var16 = new android.widget.FrameLayout.LayoutParams(-2, -2, 8388629);
        var16.setMarginEnd(this.dpi(10));
        Unit var26 = Unit.INSTANCE;
        this.addView(var34, (android.view.ViewGroup.LayoutParams)var16);
        this.bottomBar.setOrientation(1);
        this.bottomBar.setBackground((Drawable)card$default(this, -350610141, 18.0F, 0, false, 12, null));
        this.bottomBar.setPadding(this.dpi(14), this.dpi(10), this.dpi(14), this.dpi(10));
        LinearLayout var5 = new LinearLayout(var1);
        var5.setOrientation(0);
        var5.setGravity(16);
        TextView var17 = this.etaBig;
        var17.setTextColor(-1);
        var17.setTextSize(24.0F);
        var17.setTypeface(Typeface.DEFAULT_BOLD);
        var17.setMaxLines(1);
        var17.setEllipsize(TruncateAt.END);
        TextView var18 = this.etaSmall;
        var18.setTextColor(Color.argb(200, 255, 255, 255));
        var18.setTextSize(15.0F);
        var18.setMaxLines(2);
        var18.setEllipsize(TruncateAt.END);
        var5.addView((View)this.etaBig, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-2, -2)));
        View var19 = (View)this.etaSmall;
        android.widget.LinearLayout.LayoutParams var35 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
        var35.setMarginStart(this.dpi(12));
        Unit var27 = Unit.INSTANCE;
        var5.addView(var19, (android.view.ViewGroup.LayoutParams)var35);
        this.bottomBar.addView((View)var5);
        this.pinScroll.addView((View)this.pinRow);
        this.pinScroll.setHorizontalScrollBarEnabled(false);
        LinearLayout var6 = this.bottomBar;
        var3 = (View)this.pinScroll;
        android.widget.LinearLayout.LayoutParams var36 = new android.widget.LinearLayout.LayoutParams(-1, -2);
        var36.topMargin = this.dpi(6);
        Unit var20 = Unit.INSTANCE;
        var6.addView(var3, (android.view.ViewGroup.LayoutParams)var36);
        this.btnRow.setOrientation(0);
        LinearLayout var29 = this.bottomBar;
        View var21 = (View)this.btnRow;
        android.widget.LinearLayout.LayoutParams var7 = new android.widget.LinearLayout.LayoutParams(-1, this.dpi(46));
        var7.topMargin = this.dpi(8);
        Unit var37 = Unit.INSTANCE;
        var29.addView(var21, (android.view.ViewGroup.LayoutParams)var7);
        View var8 = (View)this.bottomBar;
        android.widget.FrameLayout.LayoutParams var30 = new android.widget.FrameLayout.LayoutParams(-1, -2, 80);
        var30.setMargins(this.dpi(10), 0, this.dpi(10), this.dpi(10));
        Unit var22 = Unit.INSTANCE;
        this.addView(var8, (android.view.ViewGroup.LayoutParams)var30);
        this.sheet.setVisibility(8);
        this.sheet.setElevation(this.dp(8.0F));
        this.addView((View)this.sheet, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -2, 80)));
        this.refreshButtons();
        this.lastKnownRaw = "";
        this.lastKnownLL = new double[]{Double.NaN, Double.NaN};
        this.previewShown = "";
        this.lastButtons = "";
        this.hideInfo = new MapPane$$ExternalSyntheticLambda18(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float dp(float v) {
        return this.d * v;
    }

    private final int dpi(int v) {
        return MathKt.roundToInt(v * this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Telemetry getT() {
        return this.host.getTelemetry();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UiSettings getS() {
        return this.host.getSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tiles$lambda$1(final MapPane this$0) {
        this$0.post(new Runnable() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                MapPane.tiles$lambda$1$lambda$0(this$0);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tiles$lambda$1$lambda$0(MapPane this$0) {
        this$0.map.invalidate();
    }

    public final MapTiles getTiles() {
        return this.tiles;
    }

    private static final TextView _init_$sideBtn(MapPane this$0, String label, final Function0<Unit> function0) {
        TextView b = this$0.round(label, 44);
        b.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                function0.invoke();
            }
        });
        LinearLayout.LayoutParams $this$sideBtn_u24lambda_u2411_u24lambda_u2410 = new LinearLayout.LayoutParams(this$0.dpi(48), this$0.dpi(48));
        $this$sideBtn_u24lambda_u2411_u24lambda_u2410.bottomMargin = this$0.dpi(10);
        Unit unit = Unit.INSTANCE;
        this$0.side.addView(b, $this$sideBtn_u24lambda_u2411_u24lambda_u2410);
        return b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$12(MapPane this$0) {
        this$0.map.follow();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$13(MapPane this$0) {
        this$0.map.zoomBy(1.0d);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$14(MapPane this$0) {
        this$0.map.zoomBy(-1.0d);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$15(MapPane this$0) {
        this$0.map.compass();
        this$0.refreshButtons();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$16(MapPane this$0) {
        this$0.getS().setFloat("map_tilt", this$0.getS().getFloat("map_tilt", 0.0f) <= 5.0f ? 45.0f : 0.0f);
        this$0.map.invalidate();
        this$0.refreshButtons();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$17(MapPane this$0) {
        String voiceMode = Nav.INSTANCE.voiceMode(this$0.getS());
        String next = Intrinsics.areEqual(voiceMode, "all") ? "alerts" : Intrinsics.areEqual(voiceMode, "alerts") ? "off" : "all";
        this$0.getS().setStr("nav_voice", next);
        this$0.refreshButtons();
        this$0.showInfo(Intrinsics.areEqual(next, "all") ? "Voice: turn-by-turn directions and alerts" : Intrinsics.areEqual(next, "alerts") ? "Voice: alerts only (incidents, closures, faster routes)" : "Voice off");
        if (Intrinsics.areEqual(next, "all")) {
            Speech.INSTANCE.speak("Voice directions on", true);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$18(MapPane this$0) {
        this$0.host.closeMap();
        return Unit.INSTANCE;
    }

    private final void prefetchAhead() {
        Nav.Line l = Nav.INSTANCE.getLine();
        if (l == null) {
            return;
        }
        Double valueOf = Double.valueOf(Nav.INSTANCE.getAlong());
        double it = valueOf.doubleValue();
        int i = 1;
        if (!((Double.isInfinite(it) || Double.isNaN(it)) ? false : true)) {
            valueOf = null;
        }
        double a = valueOf != null ? valueOf.doubleValue() : 0.0d;
        LinkedHashSet set = new LinkedHashSet();
        int[] iArr = {16, 15, 13};
        int i2 = 0;
        while (i2 < 3) {
            int z = iArr[i2];
            int n = i << z;
            double dd = a;
            double end = Math.min(15000.0d + a, l.getLength());
            while (dd <= end) {
                int i3 = l.indexAt(dd);
                double end2 = end;
                int tx = (int) (n * l.getMx()[i3]);
                Nav.Line l2 = l;
                double a2 = a;
                int ty = (int) (l.getMy()[i3] * n);
                int dx = -1;
                while (true) {
                    if (dx >= 2) {
                        break;
                    }
                    int dy = -1;
                    for (int i4 = 2; dy < i4; i4 = 2) {
                        int[] iArr2 = iArr;
                        int i5 = ty + dy;
                        int ty2 = ty;
                        int ty3 = n - 1;
                        set.add(new Triple(Integer.valueOf(z), Integer.valueOf((((tx + dx) % n) + n) % n), Integer.valueOf(RangesKt.coerceIn(i5, 0, ty3))));
                        dy++;
                        iArr = iArr2;
                        ty = ty2;
                        tx = tx;
                    }
                    dx++;
                    tx = tx;
                }
                int[] iArr3 = iArr;
                dd += z >= 16 ? 200.0d : 400.0d;
                l = l2;
                end = end2;
                a = a2;
                iArr = iArr3;
            }
            i2++;
            l = l;
            i = 1;
        }
        this.tiles.prefetch(this.map.style(), CollectionsKt.take(set, 450));
    }

    public final void tick(long nowMs) {
        int n;
        this.tickN++;
        this.tiles.setHost(Link.INSTANCE.getHost());
        this.tiles.setDirectKey(Nav.INSTANCE.getTileKey());
        Nav.poll$default(Nav.INSTANCE, this.host.getData(), true, nowMs, false, 8, null);
        if (this.tickN % 600 == 300 && (n = this.tiles.takeDirectCount()) > 0) {
            this.host.getData().request("nav_tiles", MapsKt.mapOf(TuplesKt.to("count", Integer.valueOf(n))), new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda43
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit tick$lambda$29$lambda$28;
                    tick$lambda$29$lambda$28 = MapPane.tick$lambda$29$lambda$28((JSONObject) obj);
                    return tick$lambda$29$lambda$28;
                }
            });
        }
        if (Nav.INSTANCE.getActive() && Nav.INSTANCE.getLine() != null && (this.tickN % 3600 == 0 || Nav.INSTANCE.getLine() != this.prefetchedFor)) {
            this.prefetchedFor = Nav.INSTANCE.getLine();
            prefetchAhead();
        }
        double lat = carLat();
        double lon = carLon();
        Nav.INSTANCE.track(lat, lon, getT().getVEgo(), getT().isMetric(), getT().getStarted(), Nav.INSTANCE.voiceMode(getS()));
        this.map.onTick(nowMs);
        if (Nav.INSTANCE.getVersion() != this.lastVersion) {
            this.lastVersion = Nav.INSTANCE.getVersion();
            onNav();
        }
        if (this.tickN % 5 == 0) {
            refreshBars();
        }
        if (Intrinsics.areEqual(this.sheetKind, "steps") && this.tickN % 100 == 0) {
            fillSheet();
        }
        this.banner.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tick$lambda$29$lambda$28(JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Unit.INSTANCE;
    }

    private final double[] rtkNow() {
        return RtkPosition.INSTANCE.now(System.currentTimeMillis(), getT().getVEgo());
    }

    public final double carLat() {
        double[] rtkNow = rtkNow();
        if (rtkNow != null) {
            return rtkNow[0];
        }
        double outLat = GpsBridge.INSTANCE.getOutLat();
        if (((Double.isInfinite(outLat) || Double.isNaN(outLat)) ? false : true) && System.currentTimeMillis() - GpsBridge.INSTANCE.getLastLiveMs() < 30000) {
            return GpsBridge.INSTANCE.getOutLat();
        }
        double gpsLat = getT().getGpsLat();
        return (!Double.isInfinite(gpsLat) && !Double.isNaN(gpsLat)) ? getT().getGpsLat() : lastKnown()[0];
    }

    public final double carLon() {
        double[] rtkNow = rtkNow();
        if (rtkNow != null) {
            return rtkNow[1];
        }
        double outLon = GpsBridge.INSTANCE.getOutLon();
        boolean z = false;
        if (((Double.isInfinite(outLon) || Double.isNaN(outLon)) ? false : true) && System.currentTimeMillis() - GpsBridge.INSTANCE.getLastLiveMs() < 30000) {
            return GpsBridge.INSTANCE.getOutLon();
        }
        double gpsLon = getT().getGpsLon();
        if (!Double.isInfinite(gpsLon) && !Double.isNaN(gpsLon)) {
            z = true;
        }
        return z ? getT().getGpsLon() : lastKnown()[1];
    }

    private final double[] lastKnown() {
        double[] dArr;
        String raw = Telemetry.pStr$default(getT(), "LastGPSPosition", null, 2, null);
        if (!Intrinsics.areEqual(raw, this.lastKnownRaw)) {
            this.lastKnownRaw = raw;
            try {
                JSONObject j = new JSONObject(raw);
                dArr = new double[]{j.optDouble("latitude", Double.NaN), j.optDouble("longitude", Double.NaN)};
            } catch (Exception e) {
                dArr = new double[]{Double.NaN, Double.NaN};
            }
            this.lastKnownLL = dArr;
        }
        return this.lastKnownLL;
    }

    public final float carBearing() {
        if (rtkNow() != null) {
            double heading = RtkPosition.INSTANCE.getHeading();
            if ((Double.isInfinite(heading) || Double.isNaN(heading)) ? false : true) {
                return (float) RtkPosition.INSTANCE.getHeading();
            }
        }
        float outBearing = GpsBridge.INSTANCE.getOutBearing();
        return (!((Float.isInfinite(outBearing) || Float.isNaN(outBearing)) ? false : true) || System.currentTimeMillis() - GpsBridge.INSTANCE.getLastLiveMs() >= 30000) ? getT().getBearingDeg() : GpsBridge.INSTANCE.getOutBearing();
    }

    public final void expectRoutes() {
        this.showRoutesWhenReady = true;
        Nav.INSTANCE.refresh(this.host.getData());
    }

    public final void showPreview() {
        this.previewShown = "";
        openSheet("routes");
    }

    public final Map<String, Object> liveOrigin() {
        double[] p = rtkNow();
        if (p != null) {
            HashMap o = MapsKt.hashMapOf(TuplesKt.to("latitude", Double.valueOf(p[0])), TuplesKt.to("longitude", Double.valueOf(p[1])));
            double heading = RtkPosition.INSTANCE.getHeading();
            if ((Double.isInfinite(heading) || Double.isNaN(heading)) ? false : true) {
                o.put("bearing", Double.valueOf(RtkPosition.INSTANCE.getHeading()));
            }
            return o;
        }
        double outLat = GpsBridge.INSTANCE.getOutLat();
        if (!((Double.isInfinite(outLat) || Double.isNaN(outLat)) ? false : true) || System.currentTimeMillis() - GpsBridge.INSTANCE.getLastLiveMs() > 30000) {
            return null;
        }
        HashMap o2 = MapsKt.hashMapOf(TuplesKt.to("latitude", Double.valueOf(GpsBridge.INSTANCE.getOutLat())), TuplesKt.to("longitude", Double.valueOf(GpsBridge.INSTANCE.getOutLon())));
        float outBearing = GpsBridge.INSTANCE.getOutBearing();
        if ((Float.isInfinite(outBearing) || Float.isNaN(outBearing)) ? false : true) {
            o2.put("bearing", Double.valueOf(GpsBridge.INSTANCE.getOutBearing()));
        }
        return o2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final double drivenAlong() {
        if (Nav.INSTANCE.getInPreview() || Nav.INSTANCE.getShownSelected() != Nav.INSTANCE.getSelected()) {
            return Double.NaN;
        }
        return Nav.INSTANCE.getAlong();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void lookAt(int index) {
        Nav.INSTANCE.setLook((Nav.INSTANCE.getInPreview() || index != Nav.INSTANCE.getSelected()) ? index : -1);
        this.map.linesChanged();
        this.map.invalidate();
        openSheet("look");
    }

    private final void goWithLooked() {
        if (Nav.INSTANCE.getInPreview()) {
            if (Nav.INSTANCE.getPreview() != null) {
                Nav.INSTANCE.go(this.host.getData(), new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda23
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit goWithLooked$lambda$33;
                        goWithLooked$lambda$33 = MapPane.goWithLooked$lambda$33(MapPane.this, (String) obj);
                        return goWithLooked$lambda$33;
                    }
                });
                return;
            }
            String previewError = Nav.INSTANCE.getPreviewError();
            if (previewError.length() == 0) {
                previewError = "Still finding routes…";
            }
            toast(previewError);
            return;
        }
        int i = Nav.INSTANCE.getLook();
        Nav.INSTANCE.setLook(-1);
        if (i >= 0 && i != Nav.INSTANCE.getSelected()) {
            Nav.INSTANCE.choose(this.host.getData(), i, new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda24
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit goWithLooked$lambda$34;
                    goWithLooked$lambda$34 = MapPane.goWithLooked$lambda$34(MapPane.this, (String) obj);
                    return goWithLooked$lambda$34;
                }
            });
        }
        closeSheet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit goWithLooked$lambda$33(MapPane this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        } else {
            this$0.sheetKind = "";
            this$0.sheet.setVisibility(8);
            this$0.sheet.removeAllViews();
            this$0.map.follow();
            this$0.refreshBars();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit goWithLooked$lambda$34(MapPane this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final void onNav() {
        String var9;
        String var14;
        label96: {
            JSONObject var8 = Nav.INSTANCE.getDestination();
            var9 = "";
            if (var8 != null) {
                String var10 = var8.optString("name");
                double var3 = var8.optDouble("latitude");
                double var1 = var8.optDouble("longitude");
                var10 = var10 + "|" + var3 + "|" + var1;
                var14 = var10;
                if (var10 != null) {
                    break label96;
                }
            }

            var14 = "";
        }

        boolean var7 = Intrinsics.areEqual(var14, this.lastDest);
        boolean var6 = true;
        if (!var7) {
            this.lastDest = var14;
            this.dismissedFaster = "";
            boolean var5;
            if (var14.length() == 0) {
                var5 = true;
            } else {
                var5 = false;
            }

            if (var5) {
                if (Intrinsics.areEqual(this.sheetKind, "routes") || Intrinsics.areEqual(this.sheetKind, "steps")) {
                    this.closeSheet();
                }

                this.map.follow();
            }
        }

        if (this.showRoutesWhenReady && !Nav.INSTANCE.getRoutes().isEmpty()) {
            var var15 = Nav.INSTANCE.getLines();
            boolean var12;
            if (var15 instanceof Collection && ((Collection)var15).isEmpty()) {
                var12 = false;
            } else {
                label102: {
                    Iterator var16 = var15.iterator();

                    while (var16.hasNext()) {
                        if ((Nav.Line)var16.next() != null) {
                            var12 = true;
                        } else {
                            var12 = false;
                        }

                        if (var12) {
                            var12 = true;
                            break label102;
                        }
                    }

                    var12 = false;
                }
            }

            if (var12) {
                this.showRoutesWhenReady = false;
                this.openRoutes();
            }
        }

        Nav.Preview var19 = Nav.INSTANCE.getPreview();
        var14 = var9;
        if (var19 != null) {
            var14 = var19.getId();
            if (var14 == null) {
                var14 = var9;
            }
        }

        if (Nav.INSTANCE.getInPreview() && !Intrinsics.areEqual(var14, this.previewShown)) {
            this.previewShown = var14;
            if (!Intrinsics.areEqual(this.sheetKind, "routes") && !Intrinsics.areEqual(this.sheetKind, "look") && !Intrinsics.areEqual(this.sheetKind, "options")) {
                this.openSheet("routes");
            }

            boolean var13;
            if (var14.length() > 0) {
                var13 = var6;
            } else {
                var13 = false;
            }

            if (var13) {
                this.post(new MapPane$$ExternalSyntheticLambda7(this));
            }
        }

        this.map.linesChanged();
        if (this.sheet.getVisibility() == 0 && this.sheetVersion != Nav.INSTANCE.getVersion()) {
            this.fillSheet();
        }

        this.refreshButtons();
        this.refreshBars();
        this.map.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onNav$lambda$37(MapPane this$0) {
        this$0.map.linesChanged();
        this$0.map.overview();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void refreshButtons() {
        JSONObject jSONObject;
        Object element$iv;
        boolean dest = Nav.INSTANCE.getActive();
        JSONObject d = Nav.INSTANCE.getDestination();
        if (d != null) {
            jSONObject = Favorites.INSTANCE.find(d.optDouble("latitude"), d.optDouble("longitude"));
            if (jSONObject == null) {
                Iterable $this$firstOrNull$iv = Favorites.INSTANCE.getList();
                Iterator it = $this$firstOrNull$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        element$iv = it.next();
                        JSONObject f = (JSONObject) element$iv;
                        if (Intrinsics.areEqual(f.optString("name"), d.optString("name")) && Math.abs(f.optDouble("latitude") - d.optDouble("latitude")) < 0.003d && Math.abs(f.optDouble("longitude") - d.optDouble("longitude")) < 0.003d) {
                            break;
                        }
                    } else {
                        element$iv = null;
                        break;
                    }
                }
                jSONObject = (JSONObject) element$iv;
            }
        } else {
            jSONObject = null;
        }
        final boolean destSaved = jSONObject != null;
        String key = dest + "|" + Nav.INSTANCE.getRoutes().size() + "|" + getS().get("map_north_up") + "|" + Nav.INSTANCE.voiceMode(getS()) + "|" + destSaved + "|" + Favorites.INSTANCE.getVersion() + "|" + getS().getStr("fav_pins", "");
        this.northBtn.setText(this.map.getUserRotated() ? "⊕" : getS().get("map_north_up") ? "N" : "▲");
        this.tiltBtn.setText(getS().getFloat("map_tilt", 0.0f) > 5.0f ? "2D" : "3D");
        this.closeBtn.setText(Intrinsics.areEqual(getS().getStr("map_side", "left"), "right") ? "›" : "‹");
        TextView textView = this.voiceBtn;
        String voiceMode = Nav.INSTANCE.voiceMode(getS());
        textView.setText(Intrinsics.areEqual(voiceMode, "all") ? "🔊" : Intrinsics.areEqual(voiceMode, "alerts") ? "⚠" : "🔇");
        if (Intrinsics.areEqual(key, this.lastButtons)) {
            return;
        }
        this.lastButtons = key;
        this.btnRow.removeAllViews();
        if (!dest) {
            refreshButtons$b(this, "⌕  Where to?", true, 3.0f, new Function0() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit refreshButtons$lambda$48;
                    refreshButtons$lambda$48 = MapPane.refreshButtons$lambda$48(MapPane.this);
                    return refreshButtons$lambda$48;
                }
            });
            refreshButtons$b$default(this, "⚙", false, 0.8f, new Function0() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit refreshButtons$lambda$49;
                    refreshButtons$lambda$49 = MapPane.refreshButtons$lambda$49(MapPane.this);
                    return refreshButtons$lambda$49;
                }
            }, 4, null);
        } else {
            if (Nav.INSTANCE.getRoutes().size() > 1) {
                refreshButtons$b$default(this, "Routes", false, 0.0f, new Function0() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda51
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit refreshButtons$lambda$42;
                        refreshButtons$lambda$42 = MapPane.refreshButtons$lambda$42(MapPane.this);
                        return refreshButtons$lambda$42;
                    }
                }, 12, null);
            }
            refreshButtons$b$default(this, "Steps", false, 0.0f, new Function0() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda52
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit refreshButtons$lambda$43;
                    refreshButtons$lambda$43 = MapPane.refreshButtons$lambda$43(MapPane.this);
                    return refreshButtons$lambda$43;
                }
            }, 12, null);
            refreshButtons$b$default(this, "⌕", false, 0.6f, new Function0() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda53
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit refreshButtons$lambda$44;
                    refreshButtons$lambda$44 = MapPane.refreshButtons$lambda$44(MapPane.this);
                    return refreshButtons$lambda$44;
                }
            }, 4, null);
            refreshButtons$b$default(this, destSaved ? "★" : "☆", false, 0.5f, new Function0() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda54
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit refreshButtons$lambda$45;
                    refreshButtons$lambda$45 = MapPane.refreshButtons$lambda$45(MapPane.this, destSaved);
                    return refreshButtons$lambda$45;
                }
            }, 4, null);
            refreshButtons$b$default(this, "⚙", false, 0.6f, new Function0() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit refreshButtons$lambda$46;
                    refreshButtons$lambda$46 = MapPane.refreshButtons$lambda$46(MapPane.this);
                    return refreshButtons$lambda$46;
                }
            }, 4, null);
            refreshButtons$b$default(this, "End", false, 0.8f, new Function0() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit refreshButtons$lambda$47;
                    refreshButtons$lambda$47 = MapPane.refreshButtons$lambda$47(MapPane.this);
                    return refreshButtons$lambda$47;
                }
            }, 4, null);
        }
        this.pinRow.removeAllViews();
        List pins = dest ? CollectionsKt.emptyList() : Favorites.INSTANCE.pins(getS());
        for (final JSONObject p : (Iterable<JSONObject>) pins) {
            Pair<String, String> pinTitle = Favorites.INSTANCE.pinTitle(p);
            String icon = pinTitle.component1();
            final String title = pinTitle.component2();
            LinearLayout linearLayout = this.pinRow;
            final TextView $this$refreshButtons_u24lambda_u2451 = pill$default(this, icon + "  " + title, false, 2, null);
            $this$refreshButtons_u24lambda_u2451.setPadding(dpi(14), 0, dpi(14), 0);
            $this$refreshButtons_u24lambda_u2451.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MapPane.refreshButtons$lambda$51$lambda$50($this$refreshButtons_u24lambda_u2451, MapPane.this, title, p, view);
                }
            });
            LinearLayout.LayoutParams $this$refreshButtons_u24lambda_u2452 = new LinearLayout.LayoutParams(-2, dpi(42));
            $this$refreshButtons_u24lambda_u2452.setMarginEnd(dpi(8));
            Unit unit = Unit.INSTANCE;
            linearLayout.addView($this$refreshButtons_u24lambda_u2451, $this$refreshButtons_u24lambda_u2452);
        }
        this.pinScroll.setVisibility(pins.isEmpty() ? 8 : 0);
    }

    static /* synthetic */ void refreshButtons$b$default(MapPane mapPane, String str, boolean z, float f, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            f = 1.0f;
        }
        refreshButtons$b(mapPane, str, z, f, function0);
    }

    private static final void refreshButtons$b(MapPane this$0, String label, boolean accent, float weight, final Function0<Unit> function0) {
        TextView v = this$0.pill(label, accent);
        v.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda48
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                function0.invoke();
            }
        });
        LinearLayout.LayoutParams $this$refreshButtons_u24b_u24lambda_u2441 = new LinearLayout.LayoutParams(0, -1, weight);
        $this$refreshButtons_u24b_u24lambda_u2441.setMarginEnd(this$0.dpi(6));
        Unit unit = Unit.INSTANCE;
        this$0.btnRow.addView(v, $this$refreshButtons_u24b_u24lambda_u2441);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshButtons$lambda$42(MapPane this$0) {
        this$0.openRoutes();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshButtons$lambda$43(MapPane this$0) {
        this$0.openSheet("steps");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshButtons$lambda$44(MapPane this$0) {
        this$0.host.openSearch();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshButtons$lambda$45(MapPane this$0, boolean $destSaved) {
        this$0.saveDestination($destSaved);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshButtons$lambda$46(MapPane this$0) {
        this$0.openSheet("options");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshButtons$lambda$47(MapPane this$0) {
        this$0.confirmEnd();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshButtons$lambda$48(MapPane this$0) {
        this$0.host.openSearch();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshButtons$lambda$49(MapPane this$0) {
        this$0.openSheet("options");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void refreshButtons$lambda$51$lambda$50(TextView $this_apply, MapPane this$0, String $title, JSONObject $p, View it) {
        $this_apply.setVisibility(8);
        Host host = this$0.host;
        String optString = $p.optString("address");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        host.navigateTo($title, optString, $p.optDouble("latitude"), $p.optDouble("longitude"));
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final void refreshBars() {
        boolean var21 = false; Pair var30 = null; // declarations Vineflower left out
        boolean var7 = this.getT().isMetric();
        boolean var8 = Nav.INSTANCE.getLoaded();
        String var11 = "";
        String var9;
        if (!var8) {
            if (this.host.getData().getConnected()) {
                var9 = "";
            } else {
                var9 = "Not connected to the comma";
            }
        } else if (!Nav.INSTANCE.getSupported()) {
            var9 = "The comma needs the StarView navigation update (patch 9) for the map.";
        } else if (!Nav.INSTANCE.getHasKey()) {
            var9 = "Add your Mapbox key on the comma first:\nGalaxy › Navigation › App Keys.";
        } else if (!Nav.INSTANCE.getEnabled() && Nav.INSTANCE.getActive()) {
            var9 = "Navigation is off on the comma (Galaxy › Navigation).";
        } else {
            var9 = "";
        }

        this.note.setText(var9);
        TextView var10 = this.note;
        int var5;
        if (var9.length() == 0) {
            var5 = 1;
        } else {
            var5 = 0;
        }

        if ((var5 != 0)) {
            var5 = (byte)8;
        } else {
            var5 = (byte)0;
        }

        Object var12;
        var10.setVisibility(var5);
        var8 = Nav.INSTANCE.getActive();
        var12 = null;
        label214:
        if (var8) {
            double var1;
            label212: {
                var30 = Nav.INSTANCE.remaining();
                String var49 = Nav.INSTANCE.getStatus();
                var5 = var49.hashCode();
                var1 = 0.0;
                switch (var5) {
                    case -2094020847:
                        if (var49.equals("waitingForLocation")) {
                            break label212;
                        }
                        break;
                    case -734206867:
                        if (var49.equals("arrived")) {
                            this.etaBig.setText("Arrived");
                            TextView var13 = this.etaSmall;
                            JSONObject var31 = Nav.INSTANCE.getDestination();
                            if (var31 != null) {
                                var9 = var31.optString("name");
                            } else {
                                var9 = null;
                            }

                            String var50 = var9;
                            if (var9 == null) {
                                var50 = "";
                            }

                            var13.setText(var50);
                            break label214;
                        }
                        break;
                    case -72763833:
                        if (var49.equals("routeUnavailable")) {
                            this.etaBig.setText("No route");
                            this.etaSmall.setText("The comma tries again in 30 s");
                            break label214;
                        }
                        break;
                    case 1385652422:
                        if (var49.equals("routing")) {
                            break label212;
                        }
                }

                if (var30 != null) {
                    this.etaBig.setText(Nav.INSTANCE.etaText(((Number)var30.getFirst()).doubleValue()));
                    Nav.Route var51 = Nav.INSTANCE.getRoute();
                    double var3 = var1;
                    if (var51 != null) {
                        var3 = var51.getTypicalS();
                        boolean var19;
                        if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                            var19 = true;
                        } else {
                            var19 = false;
                        }

                        var3 = var1;
                        if (var19) {
                            var3 = var51.getDurationS() - var51.getTypicalS();
                        }
                    }

                    TextView var14 = this.etaSmall;
                    String var64 = Nav.INSTANCE.durationText(((Number)var30.getFirst()).doubleValue());
                    String var15 = Nav.distanceText$default(Nav.INSTANCE, ((Number)var30.getSecond()).doubleValue(), var7, false, 4, null);
                    if (var3 >= 120.0) {
                        var9 = Nav.INSTANCE.durationText(var3);
                        var9 = "  ·  +" + var9 + " traffic";
                    } else {
                        var9 = "";
                    }

                    String var52;
                    if (Nav.INSTANCE.getOffRoute()) {
                        var52 = "  ·  off the route";
                    } else {
                        var52 = "";
                    }

                    var14.setText(var64 + " · " + var15 + var9 + var52);
                    TextView var35 = this.etaSmall;
                    if (var3 >= 300.0) {
                        var5 = Color.rgb(255, 138, 128);
                    } else {
                        var5 = Color.argb(200, 255, 255, 255);
                    }

                    var35.setTextColor(var5);
                } else {
                    this.etaBig.setText("—");
                    TextView var65 = this.etaSmall;
                    JSONObject var36 = Nav.INSTANCE.getDestination();
                    if (var36 != null) {
                        var9 = var36.optString("name");
                    } else {
                        var9 = null;
                    }

                    String var53 = var9;
                    if (var9 == null) {
                        var53 = "";
                    }

                    var65.setText(var53);
                }
                break label214;
            }

            var10 = this.etaBig;
            if (Nav.INSTANCE.getRoutes().isEmpty()) {
                var9 = "Finding routes…";
            } else {
                Nav var66 = Nav.INSTANCE;
                if (var30 != null) {
                    var1 = ((Number)var30.getFirst()).doubleValue();
                }

                var9 = var66.etaText(var1);
            }

            CharSequence var39 = var9;
            var10.setText(var39);
            TextView var67 = this.etaSmall;
            JSONObject var40 = Nav.INSTANCE.getDestination();
            if (var40 != null) {
                var9 = var40.optString("name");
            } else {
                var9 = null;
            }

            String var55 = var9;
            if (var9 == null) {
                var55 = "";
            }

            var67.setText(var55);
        } else {
            this.etaBig.setText("");
            var10 = this.etaSmall;
            CharSequence var42;
            if (Nav.INSTANCE.getLoaded() && Nav.INSTANCE.getSupported() && Nav.INSTANCE.getHasKey()) {
                var42 = "No destination";
            } else {
                var42 = "";
            }

            var10.setText(var42);
        }

        int var6 = Nav.INSTANCE.faster();
        Iterator var57 = Nav.INSTANCE.getRoutes().iterator();

        do {
            if (!var57.hasNext()) {
                var9 = (String)var12;
                break;
            }

            var9 = (String)var57.next();
            if (((Nav.Route)(Object)var9).getIndex() == var6) {
                var21 = true;
            } else {
                var21 = false;
            }
        } while (!var21);

        Nav.Route var58 = (Nav.Route)(Object)var9;
        var12 = Nav.INSTANCE.getRoute();
        var9 = var11;
        if (var58 != null) {
            var9 = var11;
            if (var12 != null) {
                var5 = var58.getIndex();
                var6 = MathKt.roundToInt(var58.getDurationS() / 60);
                var9 = var5 + "|" + var6;
            }
        }

        if (var58 != null
            && var12 != null
            && !Intrinsics.areEqual(var9, this.dismissedFaster)
            && Intrinsics.areEqual(Nav.INSTANCE.getStatus(), "guiding")
            && !Intrinsics.areEqual(this.sheetKind, "routes")) {
            TextView var61 = this.faster;
            var12 = Nav.INSTANCE.durationText(((Nav.Route)var12).getDurationS() - var58.getDurationS());
            var61.setText("Faster route · saves " + var12 + "   TAKE IT    ✕");
            this.faster.setVisibility(0);
            this.faster.setOnTouchListener(new MapPane$$ExternalSyntheticLambda33(this, var9, ((Route) var58)));
        } else {
            this.faster.setVisibility(8);
        }

        TextView var45 = this.recenter;
        int var23;
        if (this.map.getFollowing()) {
            var23 = 8;
        } else {
            var23 = 0;
        }

        var45.setVisibility(var23);
        LinearLayout var46 = this.bottomBar;
        if (this.sheet.getVisibility() == 0) {
            var23 = (byte)8;
        } else {
            var23 = (byte)0;
        }

        var46.setVisibility(var23);
        MapPane.Banner var47 = this.banner;
        if (!Nav.INSTANCE.getActive()
            || this.sheet.getVisibility() == 0
            || this.getT().navState() == null && !SetsKt.setOf(new String[]{"routing", "routeUnavailable", "arrived"}).contains(Nav.INSTANCE.getStatus())) {
            var23 = (byte)8;
        } else {
            var23 = (byte)0;
        }

        var47.setVisibility(var23);
        if (this.getT().navState() != null && Nav.INSTANCE.nextLanes() != null) {
            var23 = (short)150;
        } else {
            var23 = (short)92;
        }

        var23 = this.dpi(var23);
        if (this.banner.getLayoutParams().height != var23) {
            MapPane.Banner var48 = this.banner;
            android.view.ViewGroup.LayoutParams var59 = this.banner.getLayoutParams();
            Intrinsics.checkNotNull(var59, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            var var60 = (android.widget.FrameLayout.LayoutParams & android.view.ViewGroup.LayoutParams)var59;
            var60.height = var23;
            var48.setLayoutParams((android.view.ViewGroup.LayoutParams)var60);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean refreshBars$lambda$54(MapPane this$0, String $fKey, Nav.Route $fr, View v, MotionEvent e) {
        if (e.getAction() == 1) {
            if (e.getX() > v.getWidth() - this$0.dp(44.0f)) {
                this$0.dismissedFaster = $fKey;
            } else {
                Nav.choose$default(Nav.INSTANCE, this$0.host.getData(), $fr.getIndex(), null, 4, null);
            }
            this$0.faster.setVisibility(8);
        }
        return true;
    }

    private final void saveDestination(boolean saved) {
        final JSONObject d = Nav.INSTANCE.getDestination();
        if (d == null) {
            return;
        }
        final String name = d.optString("name");
        final String address = d.optString("address");
        if (saved) {
            showInfo(name + " is saved. Hold it in the search screen for Home / Work or the main screen.");
        } else {
            String[] opts = {"☆  Save", "🏠  Save as Home", "💼  Save as Work", "📌  Save and show on the main screen"};
            new AlertDialog.Builder(getContext()).setTitle(name).setItems(opts, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda50
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    MapPane.saveDestination$lambda$60(d, MapPane.this, name, address, dialogInterface, i);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveDestination$lambda$60(final JSONObject $d, final MapPane this$0, String $name, String $address, DialogInterface dialogInterface, final int i) {
        String str;
        switch (i) {
            case 1:
                str = "home";
                break;
            case 2:
                str = "work";
                break;
            default:
                str = null;
                break;
        }
        String label = str;
        final Function1 then = new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit saveDestination$lambda$60$lambda$57;
                saveDestination$lambda$60$lambda$57 = MapPane.saveDestination$lambda$60$lambda$57(this$0, i, $d, (String) obj);
                return saveDestination$lambda$60$lambda$57;
            }
        };
        if (!$d.optBoolean("temporary")) {
            Favorites favorites = Favorites.INSTANCE;
            DataClient data = this$0.host.getData();
            Intrinsics.checkNotNull($name);
            Intrinsics.checkNotNull($address);
            favorites.save(data, $name, $address, $d.optDouble("latitude"), $d.optDouble("longitude"), label, then);
            return;
        }
        Favorites favorites2 = Favorites.INSTANCE;
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        DataClient data2 = this$0.host.getData();
        Intrinsics.checkNotNull($name);
        Intrinsics.checkNotNull($address);
        favorites2.saveFound(context, data2, $name, $address, label, new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit saveDestination$lambda$60$lambda$59;
                saveDestination$lambda$60$lambda$59 = MapPane.saveDestination$lambda$60$lambda$59(i, then, this$0, (String) obj);
                return saveDestination$lambda$60$lambda$59;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveDestination$lambda$60$lambda$57(MapPane this$0, int $i, JSONObject $d, String e) {
        JSONObject it;
        if (e != null) {
            this$0.toast(e);
        } else {
            if ($i == 3 && (it = Favorites.INSTANCE.find($d.optDouble("latitude"), $d.optDouble("longitude"))) != null) {
                Favorites.INSTANCE.setPinned(this$0.getS(), it, true);
            }
            this$0.showInfo("Saved");
            this$0.lastButtons = "";
            this$0.refreshButtons();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveDestination$lambda$60$lambda$59(int $i, Function1 $then, MapPane this$0, String e) {
        JSONObject it;
        if (e == null && $i == 3 && (it = (JSONObject) CollectionsKt.lastOrNull((List) Favorites.INSTANCE.getList())) != null) {
            Favorites.INSTANCE.setPinned(this$0.getS(), it, true);
        }
        $then.invoke(e);
        return Unit.INSTANCE;
    }

    private final void confirmEnd() {
        String str;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        JSONObject destination = Nav.INSTANCE.getDestination();
        if (destination == null || (str = destination.optString("name")) == null) {
            str = "the destination";
        }
        builder.setMessage("End the route to " + str + "?").setPositiveButton("End", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MapPane.confirmEnd$lambda$62(MapPane.this, dialogInterface, i);
            }
        }).setNegativeButton("Keep", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmEnd$lambda$62(final MapPane this$0, DialogInterface dialogInterface, int i) {
        Nav.INSTANCE.cancel(this$0.host.getData(), new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit confirmEnd$lambda$62$lambda$61;
                confirmEnd$lambda$62$lambda$61 = MapPane.confirmEnd$lambda$62$lambda$61(this$0, (String) obj);
                return confirmEnd$lambda$62$lambda$61;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit confirmEnd$lambda$62$lambda$61(MapPane this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        return Unit.INSTANCE;
    }

    public final void showInfo(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.info.setText(text);
        this.info.setVisibility(0);
        removeCallbacks(this.hideInfo);
        postDelayed(this.hideInfo, 6000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideInfo$lambda$63(MapPane this$0) {
        this$0.info.setVisibility(8);
    }

    private final void toast(String s) {
        Toast.makeText(getContext(), s, 1).show();
    }

    public final void openRoutes() {
        openSheet("routes");
        this.map.overview();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openSheet(String kind) {
        this.lookFresh = Intrinsics.areEqual(kind, "look") && !Intrinsics.areEqual(this.sheetKind, "look");
        this.sheetKind = kind;
        this.sheet.setVisibility(0);
        fillSheet();
        refreshBars();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void closeSheet() {
        String was = this.sheetKind;
        this.sheetKind = "";
        this.sheet.setVisibility(8);
        this.sheet.removeAllViews();
        refreshBars();
        if (Nav.INSTANCE.getInPreview()) {
            Nav.INSTANCE.cancelPreview();
        }
        if (Nav.INSTANCE.getLook() >= 0) {
            Nav.INSTANCE.setLook(-1);
            this.map.linesChanged();
        }
        if (Intrinsics.areEqual(was, "routes") || Intrinsics.areEqual(was, "look") || this.map.getPreviewing()) {
            this.map.follow();
        }
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void fillSheet() {
        String var20 = null; ScrollView var41 = null; // declarations Vineflower left out
        this.sheetVersion = Nav.INSTANCE.getVersion();
        Object var4 = this.sheet.getChildAt(0);
        LinearLayout var13;
        if (var4 instanceof LinearLayout) {
            var13 = (LinearLayout)var4;
        } else {
            var13 = null;
        }

        int var1;
        label160: {
            if (var13 != null) {
                var4 = var13.getChildAt(1);
                ScrollView var15;
                if (var4 instanceof ScrollView) {
                    var15 = (ScrollView)var4;
                } else {
                    var15 = null;
                }

                Integer var16;
                if (var15 != null) {
                    var16 = var15.getScrollY();
                } else {
                    var16 = null;
                }

                if (var16 != null) {
                    var1 = var16;
                    break label160;
                }
            }

            var1 = 0;
        }

        this.sheet.removeAllViews();
        LinearLayout var6 = new LinearLayout(this.getContext());
        var6.setOrientation(1);
        var6.setBackground((Drawable)card$default(this, -233169629, 20.0F, 0, true, 4, null));
        var6.setPadding(this.dpi(14), this.dpi(10), this.dpi(14), this.dpi(12));
        LinearLayout var7 = new LinearLayout(this.getContext());
        var7.setOrientation(0);
        var7.setGravity(16);
        boolean var3 = Nav.INSTANCE.getInPreview();
        if (Intrinsics.areEqual(this.sheetKind, "look")) {
            TextView var17 = pill$default(this, "‹", false, 2, null);
            var17.setTextSize(20.0F);
            var17.setOnClickListener(new MapPane$$ExternalSyntheticLambda26(this));
            var4 = (View)var17;
            android.widget.LinearLayout.LayoutParams var8 = new android.widget.LinearLayout.LayoutParams(this.dpi(48), this.dpi(40));
            var8.setMarginEnd(this.dpi(10));
            Unit var5 = Unit.INSTANCE;
            var7.addView(var4, (android.view.ViewGroup.LayoutParams)var8);
        }

        label153: {
            String var19 = this.sheetKind;
            switch (var19.hashCode()) {
                case -925132982:
                    if (var19.equals("routes")) {
                        if (var3) {
                            JSONObject var22 = Nav.INSTANCE.getPreviewing();
                            String var23;
                            if (var22 != null) {
                                var23 = var22.optString("name");
                            } else {
                                var23 = null;
                            }

                            String var36 = var23;
                            if (var23 == null) {
                                var36 = "";
                            }

                            var4 = var36;
                            boolean var2;
                            if (((String) var4).length() == 0) {
                                var2 = true;
                            } else {
                                var2 = false;
                            }

                            if (var2) {
                                var4 = "there";
                            }

                            var20 = "Routes to " + var4;
                        } else {
                            var20 = "Routes";
                        }
                        break label153;
                    }
                    break;
                case 3327647:
                    if (var19.equals("look")) {
                        Nav.Route var34 = Nav.INSTANCE.getShownRoute();
                        if (var34 != null) {
                            String var21 = Nav.INSTANCE.durationText(var34.getDurationS());
                            String var35 = Nav.distanceText$default(Nav.INSTANCE, var34.getDistanceM(), this.getT().isMetric(), false, 4, null);
                            var20 = var21 + " · " + var35;
                            if (var20 != null) {
                                break label153;
                            }
                        }

                        var20 = "Directions";
                        break label153;
                    }
                    break;
                case 109761319:
                    if (var19.equals("steps")) {
                        var20 = "Directions";
                        break label153;
                    }
            }

            var20 = "Route options";
        }

        TextView var25 = label$default(this, var20, 20.0F, true, false, 8, null);
        var25.setMaxLines(1);
        var25.setEllipsize(TruncateAt.END);
        var7.addView((View)var25, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
        if (Intrinsics.areEqual(this.sheetKind, "routes")) {
            TextView var26 = pill$default(this, "Avoid…", false, 2, null);
            var26.setOnClickListener(new MapPane$$ExternalSyntheticLambda27(this));
            var4 = (View)var26;
            android.widget.LinearLayout.LayoutParams var44 = new android.widget.LinearLayout.LayoutParams(-2, this.dpi(40));
            var44.setMarginEnd(this.dpi(8));
            Unit var37 = Unit.INSTANCE;
            var7.addView(var4, (android.view.ViewGroup.LayoutParams)var44);
        }

        if (var3 && (Intrinsics.areEqual(this.sheetKind, "routes") || Intrinsics.areEqual(this.sheetKind, "look"))) {
            TextView var28 = pill$default(this, "Cancel", false, 2, null);
            var28.setOnClickListener(new MapPane$$ExternalSyntheticLambda28(this));
            var4 = (View)var28;
            android.widget.LinearLayout.LayoutParams var38 = new android.widget.LinearLayout.LayoutParams(-2, this.dpi(40));
            var38.setMarginEnd(this.dpi(8));
            Unit var45 = Unit.INSTANCE;
            var7.addView(var4, (android.view.ViewGroup.LayoutParams)var38);
        }

        int var10;
        if (Intrinsics.areEqual(this.sheetKind, "look") && !var3 && Nav.INSTANCE.getLook() >= 0 && Nav.INSTANCE.getLook() != Nav.INSTANCE.getSelected()) {
            var10 = 1;
        } else {
            var10 = 0;
        }

        Pair var30;
        if (!var3 || !Intrinsics.areEqual(this.sheetKind, "routes") && !Intrinsics.areEqual(this.sheetKind, "look")) {
            if ((var10 != 0)) {
                var30 = TuplesKt.to("Use this route", new MapPane$$ExternalSyntheticLambda30(this));
            } else if (var3 && Intrinsics.areEqual(this.sheetKind, "options")) {
                var30 = TuplesKt.to("Done", new MapPane$$ExternalSyntheticLambda31(this));
            } else {
                var30 = TuplesKt.to("Done", new MapPane$$ExternalSyntheticLambda32(this));
            }
        } else {
            var30 = TuplesKt.to("Go", new MapPane$$ExternalSyntheticLambda29(this));
        }

        String var39 = (String)var30.component1();
        Function0 var31 = (Function0)var30.component2();
        TextView var40 = this.pill(var39, true);
        var40.setOnClickListener(new MapPane$$ExternalSyntheticLambda34(var31));
        var4 = (View)var40;
        if ((var10 != 0)) {
            var10 = -2;
        } else {
            var10 = this.dpi(84);
        }

        label112: {
            var7.addView(var4, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(var10, this.dpi(40))));
            var6.addView((View)var7);
            var41 = new ScrollView(this.getContext());
            LinearLayout var33 = new LinearLayout(this.getContext());
            var33.setOrientation(1);
            var41.addView((View)var33);
            View var42 = (View)var41;
            android.widget.LinearLayout.LayoutParams var9 = new android.widget.LinearLayout.LayoutParams(-1, -2);
            var9.topMargin = this.dpi(8);
            Unit var46 = Unit.INSTANCE;
            var6.addView(var42, (android.view.ViewGroup.LayoutParams)var9);
            String var43 = this.sheetKind;
            switch (var43.hashCode()) {
                case -925132982:
                    if (var43.equals("routes")) {
                        this.fillRoutes(var33);
                        break label112;
                    }
                    break;
                case 3327647:
                    if (var43.equals("look")) {
                        this.fillLook(var33);
                        break label112;
                    }
                    break;
                case 109761319:
                    if (var43.equals("steps")) {
                        this.fillSteps(var33);
                        break label112;
                    }
            }

            this.fillOptions(var33);
        }

        var10 = RangesKt.coerceAtLeast(MathKt.roundToInt(this.getHeight() * 0.62F), this.dpi(260));
        this.sheet.addView((View)var6, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -2)));
        var6.measure(MeasureSpec.makeMeasureSpec(RangesKt.coerceAtLeast(this.getWidth(), 1), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
        if (var6.getMeasuredHeight() > var10) {
            var6.setLayoutParams((android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, var10)));
        }

        var41.post(new MapPane$$ExternalSyntheticLambda35(var41, this, var1));
        this.lookFresh = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSheet$lambda$79(MapPane this$0) {
        this$0.goWithLooked();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSheet$lambda$80(MapPane this$0) {
        this$0.goWithLooked();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSheet$lambda$81(MapPane this$0) {
        this$0.openSheet("routes");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillSheet$lambda$82(MapPane this$0) {
        this$0.closeSheet();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillSheet$lambda$87(ScrollView $sv, MapPane this$0, int $scrollY) {
        $sv.scrollTo(0, (Intrinsics.areEqual(this$0.sheetKind, "steps") && $scrollY == 0) ? this$0.stepScroll : (Intrinsics.areEqual(this$0.sheetKind, "look") && this$0.lookFresh) ? 0 : $scrollY);
    }

    private final void fillRoutes(LinearLayout col) {
        boolean metric = getT().isMetric();
        List<Nav.Route> routes = Nav.INSTANCE.getShownRoutes();
        if (routes.isEmpty()) {
            String msg;
            if (Nav.INSTANCE.getInPreview() && Nav.INSTANCE.getPreviewError().length() > 0) {
                msg = "No routes: " + Nav.INSTANCE.getPreviewError();
            } else if (Nav.INSTANCE.getInPreview()) {
                JSONObject pv = Nav.INSTANCE.getPreviewing();
                String name = pv != null ? pv.optString("name") : null;
                if (name == null) {
                    name = "";
                }
                if (name.length() == 0) {
                    name = "there";
                }
                msg = "Finding routes to " + name + "…";
            } else if (Intrinsics.areEqual(Nav.INSTANCE.getStatus(), "routeUnavailable")) {
                msg = "No route found. The comma tries again in 30 s.";
            } else {
                msg = "Finding routes…";
            }
            col.addView(label$default(this, msg, 15.0f, false, false, 12, null));
            return;
        }
        if (Nav.INSTANCE.getInPreview()) {
            TextView hint = label$default(this, "Tap a route to see its directions. Nothing is sent to the comma until Go.", 13.0f, false, true, 4, null);
            hint.setPadding(0, 0, 0, dpi(8));
            col.addView(hint);
        }
        Iterator<Nav.Route> rit = routes.iterator();
        if (!rit.hasNext()) {
            throw new NoSuchElementException();
        }
        double fastest = rit.next().getDurationS();
        while (rit.hasNext()) {
            fastest = Math.min(fastest, rit.next().getDurationS());
        }
        for (final Nav.Route r : routes) {
            boolean sel = r.getIndex() == Nav.INSTANCE.getShownSelected();
            LinearLayout card = new LinearLayout(getContext());
            card.setOrientation(0);
            card.setPadding(dpi(12), dpi(10), dpi(12), dpi(10));
            card.setBackground(card$default(this, sel ? 858751213 : 352321535, 14.0f, sel ? -13664019 : 0, false, 8, null));
            View swatch = new View(getContext());
            swatch.setBackground(card$default(this, routeColor(sel), 6.0f, 0, false, 12, null));
            LinearLayout.LayoutParams swLp = new LinearLayout.LayoutParams(dpi(8), -1);
            swLp.setMarginEnd(dpi(12));
            card.addView(swatch, swLp);
            LinearLayout body = new LinearLayout(getContext());
            body.setOrientation(1);
            LinearLayout top = new LinearLayout(getContext());
            top.setOrientation(0);
            top.setGravity(80);
            top.addView(label$default(this, Nav.INSTANCE.durationText(r.getDurationS()), 22.0f, true, false, 8, null));
            top.addView(label$default(this, "   " + Nav.distanceText$default(Nav.INSTANCE, r.getDistanceM(), metric, false, 4, null) + "  ·  arrive " + Nav.INSTANCE.etaText(r.getDurationS()), 14.0f, false, true, 4, null));
            body.addView(top);
            ArrayList<Pair<String, Integer>> tags = new ArrayList<>();
            if (r.getDurationS() - fastest >= 60.0d) {
                tags.add(TuplesKt.to(Nav.INSTANCE.durationText(r.getDurationS() - fastest) + " slower", Integer.valueOf(Color.argb(190, 255, 255, 255))));
            } else if (routes.size() > 1) {
                tags.add(TuplesKt.to("Fastest", Integer.valueOf(Color.rgb(108, 214, 140))));
            }
            double typical = r.getTypicalS();
            if (!Double.isInfinite(typical) && !Double.isNaN(typical)) {
                double extra = r.getDurationS() - r.getTypicalS();
                String what = extra >= 120.0d ? "heavy traffic, +" + Nav.INSTANCE.durationText(extra) : "usual traffic";
                int tc = extra >= 300.0d ? Color.rgb(255, 122, 112) : extra >= 120.0d ? Color.rgb(255, 176, 64) : Color.rgb(108, 214, 140);
                tags.add(TuplesKt.to(what, Integer.valueOf(tc)));
            }
            if (r.getClasses().contains("toll")) {
                tags.add(TuplesKt.to("Tolls", Integer.valueOf(Color.rgb(255, 200, 87))));
            }
            if (r.getClasses().contains("motorway")) {
                tags.add(TuplesKt.to("Highways", Integer.valueOf(Color.argb(190, 255, 255, 255))));
            }
            if (r.getClasses().contains("ferry")) {
                tags.add(TuplesKt.to("Ferry", Integer.valueOf(Color.rgb(127, 196, 255))));
            }
            if (r.getClasses().contains("restricted")) {
                tags.add(TuplesKt.to("Restricted roads", Integer.valueOf(Color.rgb(255, 176, 64))));
            }
            if (r.getIncidents() > 0) {
                tags.add(TuplesKt.to(r.getIncidents() + " incident" + (r.getIncidents() > 1 ? "s" : ""), Integer.valueOf(Color.rgb(255, 122, 112))));
            }
            if (r.getClosed()) {
                tags.add(TuplesKt.to("Closure on route", Integer.valueOf(Color.rgb(255, 85, 85))));
            }
            if (!Nav.INSTANCE.getInPreview() && r.getIndex() == Nav.INSTANCE.getSelected()) {
                tags.add(0, TuplesKt.to("Driving now", Integer.valueOf(Color.rgb(127, 196, 255))));
            }
            LinearLayout tagRow = new LinearLayout(getContext());
            tagRow.setOrientation(0);
            TextView tagTv = new TextView(getContext());
            tagTv.setTextSize(13.0f);
            tagTv.setMaxLines(2);
            SpannableStringBuilder sb = new SpannableStringBuilder();
            int k = 0;
            for (Pair<String, Integer> tag : tags) {
                if (k > 0) {
                    sb.append("  ·  ");
                }
                k++;
                int start = sb.length();
                sb.append(tag.getFirst());
                sb.setSpan(new ForegroundColorSpan(tag.getSecond().intValue()), start, sb.length(), 0);
            }
            tagTv.setText(sb);
            tagRow.addView(tagTv);
            body.addView(tagRow);
            if (r.getVia().length() > 0) {
                TextView via = label$default(this, "via " + r.getVia(), 13.0f, false, true, 4, null);
                via.setMaxLines(1);
                via.setEllipsize(TextUtils.TruncateAt.END);
                body.addView(via);
            }
            card.addView(body, new LinearLayout.LayoutParams(0, -2, 1.0f));
            card.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda46
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MapPane.fillRoutes$lambda$100(MapPane.this, r, view);
                }
            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
            lp.bottomMargin = dpi(8);
            col.addView(card, lp);
        }
        JSONObject opts = Nav.INSTANCE.getOptions();
        List<String> avoided = new ArrayList<>();
        for (Pair<String, String> p : CollectionsKt.listOf(new Pair[]{TuplesKt.to("avoidTolls", "tolls"), TuplesKt.to("avoidHighways", "highways"), TuplesKt.to("avoidFerries", "ferries"), TuplesKt.to("avoidUnpaved", "unpaved roads"), TuplesKt.to("avoidCashTolls", "cash-only tolls"), TuplesKt.to("avoidTunnels", "tunnels"), TuplesKt.to("avoidBorders", "borders")})) {
            if (opts.optBoolean(p.getFirst())) {
                avoided.add(p.getSecond());
            }
        }
        int carpool = opts.optInt("carpool", 0);
        String a = avoided.isEmpty() ? "Avoiding nothing" : "Avoiding " + CollectionsKt.joinToString$default(avoided, ", ", null, null, 0, null, null, 62, null);
        String cp = carpool > 0 ? " · carpool lanes (" + carpool + "+)" : " · no carpool lanes";
        col.addView(label$default(this, a + cp, 13.0f, false, true, 4, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillRoutes$lambda$100(MapPane this$0, Nav.Route $r, View it) {
        this$0.lookAt($r.getIndex());
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void fillLook(LinearLayout var1) {
        Nav.Route var11 = Nav.INSTANCE.getShownRoute();
        Nav.Line var9 = Nav.INSTANCE.getShownLine();
        if (var11 != null && var9 != null) {
            Iterator var8 = Nav.INSTANCE.getShownRoutes().iterator();
            if (!var8.hasNext()) {
                throw new NoSuchElementException();
            } else {
                double var2 = ((Nav.Route)var8.next()).getDurationS();

                while (var8.hasNext()) {
                    var2 = Math.min(var2, ((Nav.Route)var8.next()).getDurationS());
                }

                boolean var7;
                ArrayList var10;
                label62: {
                    var10 = new ArrayList();
                    String var15 = Nav.INSTANCE.etaText(var11.getDurationS());
                    var10.add("arrive " + var15);
                    double var4 = var11.getDurationS();
                    var7 = true;
                    String var17;
                    if (var4 - var2 >= 60.0) {
                        String var16 = Nav.INSTANCE.durationText(var11.getDurationS() - var2);
                        var17 = var16 + " slower";
                    } else {
                        if (Nav.INSTANCE.getShownRoutes().size() <= 1) {
                            break label62;
                        }

                        var17 = "fastest";
                    }

                    var10.add(var17);
                }

                var2 = var11.getTypicalS();
                int var6;
                if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
                    var6 = 1;
                } else {
                    var6 = 0;
                }

                if ((var6 != 0) && var11.getDurationS() - var11.getTypicalS() >= 120.0) {
                    String var18 = Nav.INSTANCE.durationText(var11.getDurationS() - var11.getTypicalS());
                    var10.add("traffic +" + var18);
                }

                if (var11.getClasses().contains("toll")) {
                    var10.add("tolls");
                }

                if (var11.getIncidents() > 0) {
                    var6 = var11.getIncidents();
                    String var19;
                    if (var11.getIncidents() > 1) {
                        var19 = "s";
                    } else {
                        var19 = "";
                    }

                    var10.add(var6 + " incident" + var19);
                }

                boolean var14;
                if (var11.getVia().length() > 0) {
                    var14 = var7;
                } else {
                    var14 = false;
                }

                if (var14) {
                    String var20 = var11.getVia();
                    var10.add("via " + var20);
                }

                TextView var21 = label$default(
                    this, CollectionsKt.joinToString(var10, "  ·  ", "", "", -1, "...", null), 13.0F, false, true, 4, null
                );
                var21.setPadding(0, 0, 0, this.dpi(8));
                var1.addView((View)var21);
                this.fillSteps(var1, var9, false);
            }
        } else {
            var1.addView((View)label$default(this, "No directions for this route.", 15.0F, false, false, 12, null));
        }
    }

    private final void fillSteps(LinearLayout col) {
        Nav.Line l = Nav.INSTANCE.getLine();
        if (l == null || l.getSteps().isEmpty()) {
            col.addView(label$default(this, "No directions yet.", 15.0f, false, false, 12, null));
        } else {
            fillSteps(col, l, true);
        }
    }

    private final void fillSteps(LinearLayout col, Nav.Line l, boolean live) {
        if (l.getSteps().isEmpty()) {
            col.addView(label$default(this, "No directions yet.", 15.0f, false, false, 12, null));
            return;
        }
        int cur = live ? Nav.INSTANCE.currentStep(l) : -1;
        double along = live ? Nav.INSTANCE.getAlong() : Double.NaN;
        boolean metric = getT().isMetric();
        this.stepScroll = 0;
        int i = 0;
        for (final Nav.Step step : l.getSteps()) {
            int idx = i++;
            if (cur >= 0 && idx < cur) {
                continue;
            }
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(0);
            row.setGravity(16);
            row.setPadding(dpi(10), dpi(10), dpi(10), dpi(10));
            boolean next = idx == cur + 1 || (cur < 0 && idx == 0);
            row.setBackground(card$default(this, next ? 858751213 : 285212671, 12.0f, 0, false, 12, null));
            ImageView icon = new ImageView(getContext());
            NavIcons navIcons = NavIcons.INSTANCE;
            Context context = icon.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            Bitmap bm = navIcons.icon(context, step.getType(), step.getModifier());
            if (bm != null) {
                icon.setImageBitmap(bm);
            }
            LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(dpi(36), dpi(36));
            iconLp.setMarginEnd(dpi(12));
            row.addView(icon, iconLp);
            LinearLayout text = new LinearLayout(getContext());
            text.setOrientation(1);
            String what = step.getText();
            if (what.length() == 0) {
                what = step.getRoad();
            }
            text.addView(label$default(this, what, 16.0f, idx == cur + 1, false, 8, null));
            String when;
            if (!Double.isInfinite(along) && !Double.isNaN(along) && step.getStartM() > along) {
                when = "in " + Nav.distanceText$default(Nav.INSTANCE, step.getStartM() - along, metric, false, 4, null);
            } else if (step.getDistanceM() > 0.0d) {
                when = "then " + Nav.distanceText$default(Nav.INSTANCE, step.getDistanceM(), metric, false, 4, null);
            } else {
                when = "";
            }
            List<String> names = new ArrayList<>();
            for (String s : CollectionsKt.listOf(new String[]{step.getRef(), StringsKt.contains$default((CharSequence) step.getText(), (CharSequence) step.getRoad(), false, 2, (Object) null) ? "" : step.getRoad()})) {
                if (s.length() > 0) {
                    names.add(s);
                }
            }
            List<String> parts = new ArrayList<>();
            for (String s : CollectionsKt.listOf(new String[]{when, CollectionsKt.joinToString$default(names, " · ", null, null, 0, null, null, 62, null)})) {
                if (s.length() > 0) {
                    parts.add(s);
                }
            }
            text.addView(label$default(this, CollectionsKt.joinToString$default(parts, "   ", null, null, 0, null, null, 62, null), 13.0f, false, true, 4, null));
            row.addView(text, new LinearLayout.LayoutParams(0, -2, 1.0f));
            row.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda22
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MapPane.fillSteps$lambda$114(MapPane.this, step, view);
                }
            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
            lp.bottomMargin = dpi(6);
            col.addView(row, lp);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillSteps$lambda$114(MapPane this$0, Nav.Step $st, View it) {
        this$0.map.preview($st.getLat(), $st.getLon());
        this$0.showInfo($st.getText());
    }

    private final void fillOptions(LinearLayout col) {
        JSONObject o = Nav.INSTANCE.getOptions();
        col.addView(label("Avoid", 14.0f, true, true));
        for (Pair pair : (Iterable<Pair>) (Iterable) CollectionsKt.listOf(new Pair[]{TuplesKt.to("avoidTolls", "Tolls"), TuplesKt.to("avoidHighways", "Highways"), TuplesKt.to("avoidFerries", "Ferries"), TuplesKt.to("avoidUnpaved", "Unpaved roads"), TuplesKt.to("avoidCashTolls", "Cash-only tolls (fine with E-ZPass)"), TuplesKt.to("avoidTunnels", "Tunnels"), TuplesKt.to("avoidBorders", "Border crossings")})) {
            final String key = (String) pair.component1();
            String title = (String) pair.component2();
            col.addView(toggleRow(title, o.optBoolean(key), new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda36
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit fillOptions$lambda$117;
                    fillOptions$lambda$117 = MapPane.fillOptions$lambda$117(MapPane.this, key, ((Boolean) obj).booleanValue());
                    return fillOptions$lambda$117;
                }
            }));
        }
        TextView $this$fillOptions_u24lambda_u24118 = label("Carpool (HOV) lanes", 14.0f, true, true);
        $this$fillOptions_u24lambda_u24118.setPadding(0, dpi(12), 0, 0);
        col.addView($this$fillOptions_u24lambda_u24118);
        col.addView(label$default(this, "Left out of routes unless you have the people for them.", 13.0f, false, true, 4, null));
        LinearLayout $this$fillOptions_u24lambda_u24119 = new LinearLayout(getContext());
        $this$fillOptions_u24lambda_u24119.setOrientation(0);
        for (Pair pair2 : (Iterable<Pair>) (Iterable) CollectionsKt.listOf(new Pair[]{TuplesKt.to(0, "Off"), TuplesKt.to(2, "2+ people"), TuplesKt.to(3, "3+ people")})) {
            final int v = ((Number) pair2.component1()).intValue();
            String title2 = (String) pair2.component2();
            TextView $this$fillOptions_u24lambda_u24122 = pill(title2, o.optInt("carpool", 0) == v);
            $this$fillOptions_u24lambda_u24122.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda37
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MapPane.fillOptions$lambda$122$lambda$121(MapPane.this, v, view);
                }
            });
            LinearLayout.LayoutParams $this$fillOptions_u24lambda_u24123 = new LinearLayout.LayoutParams(0, dpi(42), 1.0f);
            $this$fillOptions_u24lambda_u24123.setMarginEnd(dpi(6));
            Unit unit = Unit.INSTANCE;
            $this$fillOptions_u24lambda_u24119.addView($this$fillOptions_u24lambda_u24122, $this$fillOptions_u24lambda_u24123);
        }
        LinearLayout.LayoutParams $this$fillOptions_u24lambda_u24124 = new LinearLayout.LayoutParams(-1, -2);
        $this$fillOptions_u24lambda_u24124.topMargin = dpi(6);
        Unit unit2 = Unit.INSTANCE;
        col.addView($this$fillOptions_u24lambda_u24119, $this$fillOptions_u24lambda_u24124);
        col.addView(toggleRow("Express toll lanes (HOT): free with the people, a toll without", o.optBoolean("expressLanes"), new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillOptions$lambda$126;
                fillOptions$lambda$126 = MapPane.fillOptions$lambda$126(MapPane.this, ((Boolean) obj).booleanValue());
                return fillOptions$lambda$126;
            }
        }));
        int i = 42;
        TextView $this$fillOptions_u24lambda_u24127 = label$default(this, "Changes ask the comma for new routes. Mapbox avoids these when it can.", 12.0f, false, true, 4, null);
        $this$fillOptions_u24lambda_u24127.setPadding(0, dpi(4), 0, dpi(8));
        col.addView($this$fillOptions_u24lambda_u24127);
        col.addView(label("Map", 14.0f, true, true));
        col.addView(toggleRow("Live traffic on every road", getS().get("map_traffic"), new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillOptions$lambda$128;
                fillOptions$lambda$128 = MapPane.fillOptions$lambda$128(MapPane.this, ((Boolean) obj).booleanValue());
                return fillOptions$lambda$128;
            }
        }));
        col.addView(toggleRow("North up (off: the way you drive is up)", getS().get("map_north_up"), new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda40
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillOptions$lambda$129;
                fillOptions$lambda$129 = MapPane.fillOptions$lambda$129(MapPane.this, ((Boolean) obj).booleanValue());
                return fillOptions$lambda$129;
            }
        }));
        TextView $this$fillOptions_u24lambda_u24130 = label("Voice", 14.0f, true, true);
        $this$fillOptions_u24lambda_u24130.setPadding(0, dpi(10), 0, 0);
        col.addView($this$fillOptions_u24lambda_u24130);
        LinearLayout $this$fillOptions_u24lambda_u24131 = new LinearLayout(getContext());
        $this$fillOptions_u24lambda_u24131.setOrientation(0);
        for (Pair pair3 : (Iterable<Pair>) (Iterable) CollectionsKt.listOf(new Pair[]{TuplesKt.to("all", "🔊 Turn-by-turn"), TuplesKt.to("alerts", "⚠ Alerts only"), TuplesKt.to("off", "🔇 Off")})) {
            final String v2 = (String) pair3.component1();
            String title3 = (String) pair3.component2();
            TextView $this$fillOptions_u24lambda_u24133 = pill(title3, Intrinsics.areEqual(Nav.INSTANCE.voiceMode(getS()), v2));
            $this$fillOptions_u24lambda_u24133.setTextSize(13.0f);
            $this$fillOptions_u24lambda_u24133.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda41
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MapPane.fillOptions$lambda$133$lambda$132(MapPane.this, v2, view);
                }
            });
            LinearLayout.LayoutParams $this$fillOptions_u24lambda_u24134 = new LinearLayout.LayoutParams(0, dpi(i), 1.0f);
            $this$fillOptions_u24lambda_u24134.setMarginEnd(dpi(6));
            Unit unit3 = Unit.INSTANCE;
            $this$fillOptions_u24lambda_u24131.addView($this$fillOptions_u24lambda_u24133, $this$fillOptions_u24lambda_u24134);
            i = 42;
        }
        LinearLayout.LayoutParams $this$fillOptions_u24lambda_u24135 = new LinearLayout.LayoutParams(-1, -2);
        $this$fillOptions_u24lambda_u24135.topMargin = dpi(6);
        Unit unit4 = Unit.INSTANCE;
        col.addView($this$fillOptions_u24lambda_u24131, $this$fillOptions_u24lambda_u24135);
        TextView $this$fillOptions_u24lambda_u24136 = label$default(this, "Alerts: crashes, closures and hazards ahead, and a faster route.", 12.0f, false, true, 4, null);
        $this$fillOptions_u24lambda_u24136.setPadding(0, dpi(4), 0, dpi(6));
        col.addView($this$fillOptions_u24lambda_u24136);
        col.addView(toggleRow("Map on the right side", Intrinsics.areEqual(getS().getStr("map_side", "left"), "right"), new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda42
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillOptions$lambda$138;
                fillOptions$lambda$138 = MapPane.fillOptions$lambda$138(MapPane.this, ((Boolean) obj).booleanValue());
                return fillOptions$lambda$138;
            }
        }));
        JSONObject u = Nav.INSTANCE.getUsage();
        JSONObject tl = u.optJSONObject("staticTiles");
        JSONObject dr = u.optJSONObject("directions");
        if (tl == null && dr == null) {
            return;
        }
        String[] strArr = new String[2];
        strArr[0] = tl != null ? tl.optInt("used") + " of " + tl.optInt("free") + " free map views" : null;
        strArr[1] = dr != null ? dr.optInt("used") + " of " + dr.optInt("free") + " free routes" : null;
        TextView $this$fillOptions_u24lambda_u24141 = label$default(this, "This month on the comma's Mapbox key: " + CollectionsKt.joinToString(CollectionsKt.listOfNotNull((Object[]) strArr), ", ", "", "", -1, "...", null) + ".", 12.0f, false, true, 4, null);
        $this$fillOptions_u24lambda_u24141.setPadding(0, dpi(10), 0, 0);
        col.addView($this$fillOptions_u24lambda_u24141);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillOptions$lambda$117(final MapPane this$0, String $key, boolean v) {
        Nav nav = Nav.INSTANCE;
        DataClient data = this$0.host.getData();
        JSONObject put = new JSONObject().put($key, v);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        nav.setOptions(data, put, new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillOptions$lambda$117$lambda$116;
                fillOptions$lambda$117$lambda$116 = MapPane.fillOptions$lambda$117$lambda$116(this$0, (String) obj);
                return fillOptions$lambda$117$lambda$116;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillOptions$lambda$117$lambda$116(MapPane this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillOptions$lambda$122$lambda$121(final MapPane this$0, int $v, View it) {
        Nav nav = Nav.INSTANCE;
        DataClient data = this$0.host.getData();
        JSONObject put = new JSONObject().put("carpool", $v);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        nav.setOptions(data, put, new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda49
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillOptions$lambda$122$lambda$121$lambda$120;
                fillOptions$lambda$122$lambda$121$lambda$120 = MapPane.fillOptions$lambda$122$lambda$121$lambda$120(this$0, (String) obj);
                return fillOptions$lambda$122$lambda$121$lambda$120;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillOptions$lambda$122$lambda$121$lambda$120(MapPane this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillOptions$lambda$126(final MapPane this$0, boolean v) {
        Nav nav = Nav.INSTANCE;
        DataClient data = this$0.host.getData();
        JSONObject put = new JSONObject().put("expressLanes", v);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        nav.setOptions(data, put, new Function1() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda45
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit fillOptions$lambda$126$lambda$125;
                fillOptions$lambda$126$lambda$125 = MapPane.fillOptions$lambda$126$lambda$125(this$0, (String) obj);
                return fillOptions$lambda$126$lambda$125;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillOptions$lambda$126$lambda$125(MapPane this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillOptions$lambda$128(MapPane this$0, boolean v) {
        this$0.getS().set("map_traffic", v);
        this$0.tiles.clearMemory();
        this$0.map.invalidate();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillOptions$lambda$129(MapPane this$0, boolean v) {
        this$0.getS().set("map_north_up", v);
        this$0.refreshButtons();
        this$0.map.invalidate();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillOptions$lambda$133$lambda$132(MapPane this$0, String $v, View it) {
        this$0.getS().setStr("nav_voice", $v);
        this$0.refreshButtons();
        this$0.fillSheet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fillOptions$lambda$138(final MapPane this$0, boolean v) {
        this$0.getS().setStr("map_side", v ? "right" : "left");
        this$0.host.closeMap();
        this$0.post(new Runnable() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                MapPane.fillOptions$lambda$138$lambda$137(this$0);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillOptions$lambda$138$lambda$137(MapPane this$0) {
        Context context = this$0.getContext();
        MainActivity mainActivity = context instanceof MainActivity ? (MainActivity) context : null;
        if (mainActivity != null) {
            mainActivity.openMap();
        }
    }

    private final GradientDrawable card(int color, float r, int stroke, boolean top) {
        GradientDrawable $this$card_u24lambda_u24142 = new GradientDrawable();
        $this$card_u24lambda_u24142.setColor(color);
        float dp = dp(r);
        if (top) {
            $this$card_u24lambda_u24142.setCornerRadii(new float[]{dp, dp(r), dp(r), dp(r), 0.0f, 0.0f, 0.0f, 0.0f});
        } else {
            $this$card_u24lambda_u24142.setCornerRadius(dp);
        }
        if (stroke != 0) {
            $this$card_u24lambda_u24142.setStroke(dpi(2), stroke);
        }
        return $this$card_u24lambda_u24142;
    }

    static /* synthetic */ GradientDrawable card$default(MapPane mapPane, int i, float f, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        if ((i3 & 8) != 0) {
            z = false;
        }
        return mapPane.card(i, f, i2, z);
    }

    private final TextView label(String text, float size, boolean bold, boolean dim) {
        TextView $this$label_u24lambda_u24143 = new TextView(getContext());
        $this$label_u24lambda_u24143.setText(text);
        $this$label_u24lambda_u24143.setTextSize(size);
        $this$label_u24lambda_u24143.setTextColor(dim ? Color.argb(170, 255, 255, 255) : -1);
        if (bold) {
            $this$label_u24lambda_u24143.setTypeface(Typeface.DEFAULT_BOLD);
        }
        return $this$label_u24lambda_u24143;
    }

    static /* synthetic */ TextView label$default(MapPane mapPane, String str, float f, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        return mapPane.label(str, f, z, z2);
    }

    private final TextView pill(String text, boolean accent) {
        TextView $this$pill_u24lambda_u24144 = new TextView(getContext());
        $this$pill_u24lambda_u24144.setText(text);
        $this$pill_u24lambda_u24144.setTextSize(15.0f);
        $this$pill_u24lambda_u24144.setTypeface(Typeface.DEFAULT_BOLD);
        $this$pill_u24lambda_u24144.setGravity(17);
        $this$pill_u24lambda_u24144.setTextColor(-1);
        $this$pill_u24lambda_u24144.setMaxLines(1);
        $this$pill_u24lambda_u24144.setBackground(card$default(this, accent ? -13664019 : 654311423, 12.0f, 0, false, 12, null));
        $this$pill_u24lambda_u24144.setPadding(dpi(10), 0, dpi(10), 0);
        return $this$pill_u24lambda_u24144;
    }

    static /* synthetic */ TextView pill$default(MapPane mapPane, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return mapPane.pill(str, z);
    }

    private final TextView round(String text, int size) {
        TextView $this$round_u24lambda_u24146 = new TextView(getContext());
        $this$round_u24lambda_u24146.setText(text);
        $this$round_u24lambda_u24146.setTextSize(20.0f);
        $this$round_u24lambda_u24146.setTypeface(Typeface.DEFAULT_BOLD);
        $this$round_u24lambda_u24146.setGravity(17);
        $this$round_u24lambda_u24146.setTextColor(-1);
        GradientDrawable $this$round_u24lambda_u24146_u24lambda_u24145 = new GradientDrawable();
        $this$round_u24lambda_u24146_u24lambda_u24145.setShape(1);
        $this$round_u24lambda_u24146_u24lambda_u24145.setColor(-434496221);
        $this$round_u24lambda_u24146_u24lambda_u24145.setStroke(dpi(1), 1090519039);
        $this$round_u24lambda_u24146.setBackground($this$round_u24lambda_u24146_u24lambda_u24145);
        $this$round_u24lambda_u24146.setMinimumWidth(dpi(size));
        $this$round_u24lambda_u24146.setMinimumHeight(dpi(size));
        return $this$round_u24lambda_u24146;
    }

    private final TextView chip() {
        TextView $this$chip_u24lambda_u24147 = new TextView(getContext());
        $this$chip_u24lambda_u24147.setTextSize(15.0f);
        $this$chip_u24lambda_u24147.setTypeface(Typeface.DEFAULT_BOLD);
        $this$chip_u24lambda_u24147.setTextColor(-1);
        $this$chip_u24lambda_u24147.setPadding(dpi(16), dpi(10), dpi(16), dpi(10));
        $this$chip_u24lambda_u24147.setBackground(card$default(this, -265381033, 22.0f, 0, false, 12, null));
        return $this$chip_u24lambda_u24147;
    }

    private final View toggleRow(String title, boolean on, final Function1<? super Boolean, Unit> onChange) {
        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(0);
        row.setGravity(16);
        row.setPadding(0, dpi(6), 0, dpi(6));
        row.addView(label$default(this, title, 15.0f, false, false, 12, null), new LinearLayout.LayoutParams(0, -2, 1.0f));
        final Switch $this$toggleRow_u24lambda_u24150 = new Switch(getContext());
        $this$toggleRow_u24lambda_u24150.setChecked(on);
        $this$toggleRow_u24lambda_u24150.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                MapPane.toggleRow$lambda$150$lambda$149(onChange, compoundButton, z);
            }
        });
        row.addView($this$toggleRow_u24lambda_u24150);
        row.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MapPane$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                $this$toggleRow_u24lambda_u24150.toggle();
            }
        });
        return row;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toggleRow$lambda$150$lambda$149(Function1 $onChange, CompoundButton compoundButton, boolean v) {
        $onChange.invoke(Boolean.valueOf(v));
    }

    private final int routeColor(boolean selected) {
        return selected ? -13664019 : -7362876;
    }

    /* compiled from: MapPane.kt */
    private static final class Banner extends View {
        private final Paint p;
        final /* synthetic */ MapPane this$0;
        private final Paint tp;

        /* VF: from Vineflower (jadx: ) */
        public Banner(MapPane var1, Context var2) {
            super(var2);
            Intrinsics.checkNotNullParameter(var2, "ctx");
            this.this$0 = var1;
            this.p = new Paint(3);
            Paint var3 = new Paint(1);
            var3.setColor(-1);
            this.tp = var3;
        }

        /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
        protected void onDraw(Canvas var1) {
        String var24 = null; Nav.Lane var33 = null; // declarations Vineflower left out
            Intrinsics.checkNotNullParameter(var1, "c");
            float var2 = this.getWidth();
            float var7 = this.getHeight();
            this.p.setColor(-266899898);
            var1.drawRoundRect(new RectF(0.0F, 0.0F, var2, var7), this.this$0.dp(16.0F), this.this$0.dp(16.0F), this.p);
            JSONObject var10 = this.this$0.getT().navState();
            if (var10 == null) {
                this.tp.setTextSize(this.this$0.dp(22.0F));
                this.tp.setTypeface(Typeface.DEFAULT_BOLD);
                String var26 = Nav.INSTANCE.getStatus();
                String var27;
                if (Intrinsics.areEqual(var26, "arrived")) {
                    var27 = "You have arrived";
                } else if (Intrinsics.areEqual(var26, "routeUnavailable")) {
                    var27 = "No route right now";
                } else {
                    var27 = "Finding the route…";
                }

                var1.drawText(var27, this.this$0.dp(18.0F), var7 / 2 + this.this$0.dp(8.0F), this.tp);
            } else {
                float var6 = Math.min(var7, this.this$0.dp(92.0F));
                float var4 = this.this$0.dp(12.0F);
                float var3 = 2;
                float var5 = var6 - var3 * var4;
                NavIcons var11 = NavIcons.INSTANCE;
                Context var12 = this.getContext();
                Intrinsics.checkNotNullExpressionValue(var12, "getContext(...)");
                String var13 = var10.optString("maneuverType");
                Intrinsics.checkNotNullExpressionValue(var13, "optString(...)");
                String var14 = var10.optString("maneuverModifier");
                Intrinsics.checkNotNullExpressionValue(var14, "optString(...)");
                Bitmap var28 = var11.icon(var12, var13, var14);
                if (var28 != null) {
                    var1.drawBitmap(var28, null, new RectF(var4, var4, var4 + var5, var4 + var5), this.p);
                }

                var5 = var4 + var5 + this.this$0.dp(14.0F);
                this.tp.setTypeface(Typeface.DEFAULT_BOLD);
                this.tp.setTextSize(this.this$0.dp(26.0F));
                var1.drawText(
                    Nav.distanceText$default(Nav.INSTANCE, var10.optDouble("maneuverDistance", 0.0), this.this$0.getT().isMetric(), false, 4, null),
                    var5,
                    this.this$0.dp(26.0F) + var4,
                    this.tp
                );
                this.tp.setTypeface(Typeface.DEFAULT);
                this.tp.setTextSize(this.this$0.dp(19.0F));
                var1.drawText(
                    TextUtils.ellipsize(var10.optString("maneuverPrimaryText"), new TextPaint(this.tp), var2 - var5 - var4, TruncateAt.END).toString(),
                    var5,
                    var6 - var4 - this.this$0.dp(6.0F),
                    this.tp
                );
                Pair var21 = Nav.INSTANCE.nextLanes();
                if (var21 != null) {
                    Nav.Step var22 = (Nav.Step)var21.getFirst();
                    if (var22 != null) {
                        List var23 = var22.getLanes();
                        if (var23 != null) {
                            if (var7 - var6 < this.this$0.dp(40.0F)) {
                                return;
                            }

                            this.p.setColor(855638016);
                            var1.drawRect(this.this$0.dp(6.0F), var6, var2 - this.this$0.dp(6.0F), var7 - this.this$0.dp(6.0F), this.p);
                            var4 = Math.min(this.this$0.dp(46.0F), (var2 - var3 * var4) / var23.size());
                            var5 = Math.min(var4 - this.this$0.dp(6.0F), var7 - var6 - this.this$0.dp(14.0F));
                            var2 = (var2 - var23.size() * var4) / var3;
                            var6 += (var7 - var6) / var3;
                            Iterator var32 = var23.iterator();

                            for (int var8 = 0; var32.hasNext(); var8++) {
                                label59: {
                                    boolean var9 = true;
                                    var33 = (Nav.Lane)var32.next();
                                    if (var33.getOk()) {
                                        if (var33.getUse().length() <= 0) {
                                            var9 = false;
                                        }

                                        if (var9) {
                                            var24 = var33.getUse();
                                            break label59;
                                        }
                                    }

                                    String var29 = CollectionsKt.firstOrNull(var33.getArrows());
                                    var24 = var29;
                                    if (var29 == null) {
                                        var24 = "straight";
                                    }
                                }

                                Paint var30 = this.p;
                                short var20;
                                if (var33.getOk()) {
                                    var20 = 255;
                                } else {
                                    var20 = 70;
                                }

                                var30.setAlpha(var20);
                                var11 = NavIcons.INSTANCE;
                                Context var34 = this.getContext();
                                Intrinsics.checkNotNullExpressionValue(var34, "getContext(...)");
                                Bitmap var25 = var11.icon(var34, "turn", this.laneModifier(var24));
                                if (var25 != null) {
                                    var1.drawBitmap(
                                        var25, null, new RectF(var2 + (var4 - var5) / var3, var6 - var5 / var3, var2 + (var4 + var5) / var3, var6 + var5 / var3), this.p
                                    );
                                }

                                this.p.setAlpha(255);
                                if (var8 > 0) {
                                    this.p.setColor(1442840575);
                                    var1.drawRect(var2 - this.this$0.dp(0.5F), var6 - var5 / var3, var2 + this.this$0.dp(0.5F), var6 + var5 / var3, this.p);
                                }

                                var2 += var4;
                            }

                            return;
                        }
                    }
                }
            }
        }

        /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
        private final String laneModifier(String var1) {
            switch (var1.hashCode()) {
                case -2016367553:
                    if (var1.equals("slight right")) {
                        return "slightRight";
                    }
                    break;
                case -1531469187:
                    if (var1.equals("sharp left")) {
                        return "sharpLeft";
                    }
                    break;
                case -757963388:
                    if (var1.equals("slight left")) {
                        return "slightLeft";
                    }
                    break;
                case -225243546:
                    if (var1.equals("sharp right")) {
                        return "sharpRight";
                    }
                    break;
                case 3317767:
                    String var5 = "left";
                    if (var1.equals("left")) {
                        return var5;
                    }
                    break;
                case 108511772:
                    String var4 = "right";
                    if (var1.equals("right")) {
                        return var4;
                    }
                    break;
                case 111623794:
                    String var2 = "uturn";
                    if (var1.equals("uturn")) {
                        return var2;
                    }
            }

            return "straight";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MapPane.kt */
    static final class MapCanvas extends View {
        private float bearing;
        private double cx;
        private double cy;
        private final Paint fillP;
        private boolean following;
        private final GestureDetector gestures;
        private final Matrix inv;
        private final double k;
        private int[] labelAt;
        private long lastTouchMs;
        private final Paint lineP;
        private final Matrix m;
        private boolean overviewOn;
        private final Path path;
        private boolean previewing;
        private final float[] pts;
        private final ScaleGestureDetector scale;
        private final Paint textP;
        final /* synthetic */ MapPane this$0;
        private final Paint tileP;
        private final Matrix tilt;
        private double twoAngle;
        private float twoMidY;
        private int twoMode;
        private float twoSpan;
        private double twoStartAngle;
        private float twoStartMidY;
        private float twoStartSpan;
        private boolean userRotated;
        private double zoom;
        private double zoomOffset;

        /* VF: from Vineflower (jadx: ) */
        public MapCanvas(MapPane var1, Context var2) {
            super(var2);
            Intrinsics.checkNotNullParameter(var2, "ctx");
            this.this$0 = var1;
            this.cx = Double.NaN;
            this.cy = Double.NaN;
            this.zoom = 15.5;
            this.following = true;
            this.k = RangesKt.coerceIn(this.this$0.d / 1.33F, 1.0F, 1.6F);
            this.tileP = new Paint(2);
            Paint var3 = new Paint(1);
            var3.setStyle(android.graphics.Paint.Style.STROKE);
            var3.setStrokeCap(android.graphics.Paint.Cap.ROUND);
            var3.setStrokeJoin(android.graphics.Paint.Join.ROUND);
            this.lineP = var3;
            this.fillP = new Paint(1);
            Paint var4 = new Paint(1);
            var4.setColor(-1);
            var4.setTypeface(Typeface.DEFAULT_BOLD);
            var4.setTextAlign(android.graphics.Paint.Align.CENTER);
            this.textP = var4;
            this.path = new Path();
            this.m = new Matrix();
            this.inv = new Matrix();
            this.pts = new float[8];
            this.labelAt = new int[0];
            this.tilt = new Matrix();
            this.scale = new ScaleGestureDetector(var2, (OnScaleGestureListener)(new SimpleOnScaleGestureListener() {
                final MapPane.MapCanvas this$0 = MapPane.MapCanvas.this;

                public boolean onScale(ScaleGestureDetector var1) {
                    Intrinsics.checkNotNullParameter(var1, "det");
                    if (this.this$0.twoMode != 2 && this.this$0.twoMode != 3) {
                        double var2x = Math.log(var1.getScaleFactor()) / Math.log(2.0);
                        if (this.this$0.getFollowing()) {
                            this.this$0.zoomOffset = RangesKt.coerceIn(this.this$0.zoomOffset + var2x, -6.0, 3.0);
                        } else {
                            this.this$0.setZoom(RangesKt.coerceIn(this.this$0.getZoom() + var2x, 3.0, 18.4));
                        }

                        this.this$0.lastTouchMs = System.currentTimeMillis();
                        this.this$0.invalidate();
                        return true;
                    } else {
                        return true;
                    }
                }
            }));
            this.gestures = new GestureDetector(var2, (OnGestureListener)(new SimpleOnGestureListener() {
                final MapPane.MapCanvas this$0 = MapPane.MapCanvas.this;

                public boolean onDoubleTap(MotionEvent var1) {
                    Intrinsics.checkNotNullParameter(var1, "e");
                    this.this$0.zoomBy(1.0);
                    return true;
                }

                public boolean onDown(MotionEvent var1) {
                    Intrinsics.checkNotNullParameter(var1, "e");
                    return true;
                }

                public void onLongPress(MotionEvent var1) {
                    Intrinsics.checkNotNullParameter(var1, "e");
                    this.this$0.dropPin(var1.getX(), var1.getY());
                }

                public boolean onScroll(MotionEvent var1, MotionEvent var2x, float var3x, float var4x) {
                    Intrinsics.checkNotNullParameter(var2x, "e2");
                    if (this.this$0.scale.isInProgress()) {
                        return true;
                    } else if (var2x.getPointerCount() > 1) {
                        return true;
                    } else {
                        this.this$0.unfollow();
                        this.this$0.overviewOn = false;
                        this.this$0.setMatrix();
                        double[] var8 = this.this$0.toMerc(var2x.getX() + var3x, var2x.getY() + var4x);
                        double[] var9 = this.this$0.toMerc(var2x.getX(), var2x.getY());
                        double var5 = var8[0];
                        boolean var7;
                        if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                            var7 = true;
                        } else {
                            var7 = false;
                        }

                        if (var7) {
                            var5 = var9[0];
                            if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                                var7 = true;
                            } else {
                                var7 = false;
                            }

                            if (var7) {
                                this.this$0.setCx(this.this$0.getCx() + (var8[0] - var9[0]));
                                this.this$0.setCy(this.this$0.getCy() + (var8[1] - var9[1]));
                            }
                        }

                        this.this$0.lastTouchMs = System.currentTimeMillis();
                        this.this$0.invalidate();
                        return true;
                    }
                }

                public boolean onSingleTapConfirmed(MotionEvent var1) {
                    Intrinsics.checkNotNullParameter(var1, "e");
                    this.this$0.tap(var1.getX(), var1.getY());
                    return true;
                }
            }));
        }

        public final float getBearing() {
            return this.bearing;
        }

        public final double getCx() {
            return this.cx;
        }

        public final double getCy() {
            return this.cy;
        }

        public final double getZoom() {
            return this.zoom;
        }

        public final void setBearing(float f) {
            this.bearing = f;
        }

        public final void setCx(double d) {
            this.cx = d;
        }

        public final void setCy(double d) {
            this.cy = d;
        }

        public final void setZoom(double d) {
            this.zoom = d;
        }

        public final boolean getFollowing() {
            return this.following;
        }

        public final boolean getPreviewing() {
            return this.previewing;
        }

        private final double world() {
            return Math.pow(2.0d, this.zoom) * 512.0d * this.k;
        }

        public final String style() {
            return this.this$0.getS().get("map_traffic") ? this.this$0.host.getNight() ? "trafficDark" : "trafficLight" : this.this$0.host.getNight() ? "dark" : "light";
        }

        private final float focusX() {
            return getWidth() / 2.0f;
        }

        private final float focusY() {
            float height;
            float f;
            if (!this.following || this.this$0.getS().get("map_north_up")) {
                height = getHeight();
                f = 0.5f;
            } else {
                height = getHeight();
                f = pitch() > 5.0f ? 0.72f : 0.66f;
            }
            return height * f;
        }

        public final float pitch() {
            if (this.overviewOn) {
                return 0.0f;
            }
            return RangesKt.coerceIn(this.this$0.getS().getFloat("map_tilt", 0.0f), 0.0f, 60.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void unfollow() {
            if (this.following) {
                if (!Double.isNaN(this.cx) && getWidth() > 0) {
                    setMatrix();
                    double[] p = toMerc(getWidth() / 2.0f, getHeight() * 0.5f);
                    this.cx = p[0];
                    this.cy = p[1];
                }
                this.following = false;
                this.this$0.refreshBars();
            }
        }

        public final void onTick(long nowMs) {
            String str;
            double coerceAtMost;
            double d;
            float floatValue;
            double lat = this.this$0.carLat();
            double lon = this.this$0.carLon();
            if (!this.following && this.this$0.tickN % 3 == 0) {
                invalidate();
            }
            if (Double.isNaN(this.cx)) {
                if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
                    this.cx = Nav.INSTANCE.mercX(lon);
                    this.cy = Nav.INSTANCE.mercY(lat);
                }
            }
            if (!this.following && !this.overviewOn && nowMs - this.lastTouchMs > 12000 && this.this$0.getT().getVEgo() > 3.0f) {
                follow();
            }
            if (this.following) {
                if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
                    this.cx = Nav.INSTANCE.mercX(lon);
                    this.cy = Nav.INSTANCE.mercY(lat);
                    double v = this.this$0.getT().getVEgo();
                    double d2 = 16.6d;
                    if (v < 5.0d) {
                        str = "map_north_up";
                    } else if (v < 13.0d) {
                        str = "map_north_up";
                        d2 = 16.6d - (((v - 5) / 8) * 0.8d);
                    } else {
                        str = "map_north_up";
                        if (v < 27.0d) {
                            coerceAtMost = ((v - 13) / 14) * 0.8d;
                            d = 15.8d;
                        } else {
                            coerceAtMost = RangesKt.coerceAtMost(((v - 27) / 8) * 0.4d, 0.4d);
                            d = 15.0d;
                        }
                        d2 = d - coerceAtMost;
                    }
                    double z = d2;
                    JSONObject nav = this.this$0.getT().navState();
                    if (nav != null && v < 20.0d && nav.optDouble("maneuverDistance", 9.0E9d) < 250.0d) {
                        z += 0.5d;
                    }
                    this.zoom += (RangesKt.coerceIn(z + this.zoomOffset, 3.0d, 18.4d) - this.zoom) * 0.08d;
                    if (this.this$0.getS().get(str)) {
                        floatValue = 0.0f;
                    } else {
                        Float valueOf = Float.valueOf(this.this$0.carBearing());
                        MapPane mapPane = this.this$0;
                        float it = valueOf.floatValue();
                        if (!((!Float.isInfinite(it) && !Float.isNaN(it)) && mapPane.getT().getVEgo() > 1.0f)) {
                            valueOf = null;
                        }
                        floatValue = valueOf != null ? valueOf.floatValue() : this.bearing;
                    }
                    float want = floatValue;
                    float dd = (((want - this.bearing) + 540.0f) % 360.0f) - 180.0f;
                    if (Math.abs(dd) < 0.3f) {
                        dd = 0.0f;
                    }
                    this.bearing = ((this.bearing + (0.2f * dd)) + 360.0f) % 360.0f;
                    invalidate();
                    return;
                }
            }
            if (!this.overviewOn && !this.userRotated) {
                if (!(this.bearing == 0.0f) && this.this$0.getS().get("map_north_up")) {
                    this.bearing = 0.0f;
                    invalidate();
                }
            }
        }

        public final void follow() {
            this.following = true;
            this.previewing = false;
            this.overviewOn = false;
            this.userRotated = false;
            this.zoomOffset = 0.0d;
            invalidate();
            this.this$0.refreshBars();
        }

        public final void compass() {
            if (!this.userRotated || this.following) {
                this.this$0.getS().set("map_north_up", !this.this$0.getS().get("map_north_up"));
            } else {
                this.userRotated = false;
                this.bearing = 0.0f;
            }
            invalidate();
        }

        /* recovered: the original JVM name (callers taken from Vineflower use it) */

        public final boolean getRotatedByHand() {

            return getUserRotated();

        }


        /* renamed from: getRotatedByHand, reason: from getter */
        public final boolean getUserRotated() {
            return this.userRotated;
        }

        public final void zoomBy(double dz) {
            if (this.following) {
                this.zoomOffset = RangesKt.coerceIn(this.zoomOffset + dz, -6.0d, 3.0d);
            } else {
                this.zoom = RangesKt.coerceIn(this.zoom + dz, 3.0d, 18.4d);
            }
            this.lastTouchMs = System.currentTimeMillis();
            invalidate();
        }

        public final void preview(double lat, double lon) {
            this.following = false;
            this.previewing = true;
            this.overviewOn = false;
            this.cx = Nav.INSTANCE.mercX(lon);
            this.cy = Nav.INSTANCE.mercY(lat);
            this.zoom = 17.0d;
            this.bearing = 0.0f;
            this.lastTouchMs = System.currentTimeMillis() + 20000;
            invalidate();
            this.this$0.refreshBars();
        }

        public final void overview() {
            List<Nav.Line> ls = CollectionsKt.filterNotNull(Nav.INSTANCE.getShownLines());
            if (!ls.isEmpty() && getWidth() != 0) {
                double x0 = 1.0d;
                double x1 = 0.0d;
                double y0 = 1.0d;
                double y1 = 0.0d;
                for (Nav.Line l : ls) {
                    int size = l.getSize();
                    for (int i = 0; i < size; i++) {
                        x0 = Math.min(x0, l.getMx()[i]);
                        x1 = Math.max(x1, l.getMx()[i]);
                        y0 = Math.min(y0, l.getMy()[i]);
                        y1 = Math.max(y1, l.getMy()[i]);
                    }
                }
                this.following = false;
                this.previewing = false;
                this.overviewOn = true;
                this.bearing = 0.0f;
                double d = 2;
                this.cx = (x0 + x1) / d;
                this.cy = (y0 + y1) / d;
                double usableH = getHeight() * 0.36d;
                double usableW = getWidth() * 0.8d;
                double d2 = 512;
                double zx = Math.log(usableW / ((Math.max(x1 - x0, 1.0E-9d) * d2) * this.k)) / Math.log(2.0d);
                double zy = Math.log(usableH / ((Math.max(y1 - y0, 1.0E-9d) * d2) * this.k)) / Math.log(2.0d);
                this.zoom = RangesKt.coerceIn(Math.min(zx, zy), 3.0d, 17.0d);
                this.cy += (getHeight() * 0.22d) / world();
                this.lastTouchMs = System.currentTimeMillis() + 3600000;
                invalidate();
            }
        }

        public final void linesChanged() {
            Nav.Line sel;
            Iterator it;
            Nav.Line sel2 = Nav.INSTANCE.getShownLine();
            int size = Nav.INSTANCE.getShownLines().size();
            int[] iArr = new int[size];
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = -1;
            }
            this.labelAt = iArr;
            Iterator it2 = Nav.INSTANCE.getShownLines().iterator();
            while (it2.hasNext()) {
                int i3 = i;
                i++;
                Nav.Line l = (Nav.Line) it2.next();
                if (l == null || sel2 == null || i3 == Nav.INSTANCE.getShownSelected()) {
                    sel = sel2;
                    it = it2;
                } else if (l.getSize() < 4) {
                    sel = sel2;
                    it = it2;
                } else {
                    int best = -1;
                    double bd = -1.0d;
                    int step = Math.max(1, l.getSize() / 60);
                    int sStep = Math.max(1, sel2.getSize() / 400);
                    int j = step;
                    while (j < l.getSize() - step) {
                        double md = Double.MAX_VALUE;
                        int q = 0;
                        while (q < sel2.getSize()) {
                            double dx = l.getMx()[j] - sel2.getMx()[q];
                            double dy = l.getMy()[j] - sel2.getMy()[q];
                            md = Math.min(md, (dx * dx) + (dy * dy));
                            q += sStep;
                            sel2 = sel2;
                            it2 = it2;
                        }
                        Nav.Line sel3 = sel2;
                        Iterator it3 = it2;
                        if (md > bd) {
                            double bd2 = md;
                            best = j;
                            bd = bd2;
                        }
                        j += step;
                        sel2 = sel3;
                        it2 = it3;
                    }
                    sel = sel2;
                    it = it2;
                    this.labelAt[i3] = best;
                }
                sel2 = sel;
                it2 = it;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setMatrix() {
            this.m.reset();
            this.m.postRotate(-this.bearing);
            double th = Math.toRadians(pitch());
            if (th > 0.01d) {
                float dist = RangesKt.coerceAtLeast(Math.max(getHeight(), getWidth()), 1) * 1.6f;
                this.tilt.setValues(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) Math.cos(th), 0.0f, 0.0f, (float) ((-Math.sin(th)) / dist), 1.0f});
                this.m.postConcat(this.tilt);
            }
            this.m.postTranslate(focusX(), focusY());
            this.m.invert(this.inv);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final double[] toMerc(float sx, float sy) {
            float[] p = {sx, sy};
            this.inv.mapPoints(p);
            double w = world();
            return new double[]{this.cx + (p[0] / w), this.cy + (p[1] / w)};
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            int i;
            int i2;
            int i3;
            float maxY;
            float minY;
            float minY2;
            int tx0;
            String style;
            int tx1;
            int i4;
            Canvas canvas;
            int i5;
            Canvas canvas2;
            int tx02;
            Object obj;
            int split;
            Nav.Line l;
            MapPane mapPane;
            int n;
            int r;
            int tx12;
            int split2;
            Nav.Line l2;
            int split3;
            int i6;
            int to;
            int i7;
            double tileSize;
            Iterator it;
            ArrayList order;
            double fx;
            int z;
            int ty1;
            Rect src;
            RectF dst;
            float minY3;
            String style2;
            double fy;
            float maxY2;
            Intrinsics.checkNotNullParameter(c, "c");
            if (this.this$0.host.getNight()) {
                i = 30;
                i2 = 36;
                i3 = 27;
            } else {
                i = 238;
                i2 = 241;
                i3 = 236;
            }
            c.drawColor(Color.rgb(i3, i, i2));
            if (Double.isNaN(this.cx)) {
                return;
            }
            setMatrix();
            double w = world();
            String style3 = style();
            this.pts[0] = 0.0f;
            int i8 = 1;
            this.pts[1] = 0.0f;
            int i9 = 2;
            this.pts[2] = getWidth();
            this.pts[3] = 0.0f;
            this.pts[4] = 0.0f;
            this.pts[5] = getHeight();
            this.pts[6] = getWidth();
            this.pts[7] = getHeight();
            this.inv.mapPoints(this.pts);
            float minX = Float.MAX_VALUE;
            float maxX = -3.4028235E38f;
            float minY4 = Float.MAX_VALUE;
            float maxY3 = -3.4028235E38f;
            for (int i10 = 0; i10 < 4; i10++) {
                minX = Math.min(minX, this.pts[i10 * 2]);
                maxX = Math.max(maxX, this.pts[i10 * 2]);
                minY4 = Math.min(minY4, this.pts[(i10 * 2) + 1]);
                maxY3 = Math.max(maxY3, this.pts[(i10 * 2) + 1]);
            }
            c.save();
            c.concat(this.m);
            int z2 = RangesKt.coerceIn((int) Math.floor(this.zoom + 0.4d), 0, this.following ? 16 : 18);
            if (pitch() > 25.0f) {
                z2 = RangesKt.coerceAtLeast(z2 - 1, 0);
            }
            int z3 = z2;
            while (true) {
                if (z3 <= 0) {
                    maxY = maxY3;
                    minY = minY4;
                    break;
                }
                double d = i9;
                maxY = maxY3;
                minY = minY4;
                if ((((maxX - minX) / (w / (i8 << z3))) + d) * (((maxY3 - minY4) / (w / (i8 << z3))) + d) <= 40.0d) {
                    break;
                }
                z3--;
                minY4 = minY;
                maxY3 = maxY;
                i9 = 2;
                i8 = 1;
            }
            int n2 = 1 << z3;
            double tileSize2 = w / n2;
            int txr = (int) Math.floor((this.cx + (minX / w)) * n2);
            int tx13 = (int) Math.floor((this.cx + (maxX / w)) * n2);
            float minY5 = minY;
            int ty0 = RangesKt.coerceAtLeast((int) Math.floor((this.cy + (minY5 / w)) * n2), 0);
            float maxY4 = maxY;
            int ty12 = RangesKt.coerceAtMost((int) Math.floor((this.cy + (maxY4 / w)) * n2), n2 - 1);
            HashSet want = new HashSet();
            int ty = ty0;
            if (ty <= ty12) {
                while (true) {
                    int txr2 = txr;
                    if (txr2 <= tx13) {
                        while (true) {
                            maxY2 = maxY4;
                            minY2 = minY5;
                            want.add(MapTiles.Companion.key(style3, z3, ((txr2 % n2) + n2) % n2, ty));
                            if (txr2 == tx13) {
                                break;
                            }
                            txr2++;
                            maxY4 = maxY2;
                            minY5 = minY2;
                        }
                    } else {
                        maxY2 = maxY4;
                        minY2 = minY5;
                    }
                    if (ty == ty12) {
                        break;
                    }
                    ty++;
                    maxY4 = maxY2;
                    minY5 = minY2;
                }
            } else {
                minY2 = minY5;
            }
            this.this$0.getTiles().setWanted(want);
            RectF dst2 = new RectF();
            Rect src2 = new Rect();
            int z4 = z3;
            Rect src3 = src2;
            double fx2 = n2 * this.cx;
            RectF dst3 = dst2;
            double fy2 = n2 * this.cy;
            ArrayList order2 = new ArrayList();
            int ty2 = ty0;
            if (ty2 <= ty12) {
                while (true) {
                    tx0 = txr;
                    if (txr <= tx13) {
                        while (true) {
                            style = style3;
                            order2.add(TuplesKt.to(Integer.valueOf(txr), Integer.valueOf(ty2)));
                            if (txr == tx13) {
                                break;
                            }
                            txr++;
                            style3 = style;
                        }
                    } else {
                        style = style3;
                    }
                    if (ty2 == ty12) {
                        break;
                    }
                    ty2++;
                    style3 = style;
                    txr = tx0;
                }
            } else {
                tx0 = txr;
                style = style3;
            }
            ArrayList $this$sortByDescending$iv = order2;
            if ($this$sortByDescending$iv.size() > 1) {
                final var fx2$f = fx2;
                final var fy2$f = fy2;
                CollectionsKt.sortWith($this$sortByDescending$iv, new Comparator() { // from class: com.starpilot.starview.MapPane$MapCanvas$onDraw$$inlined$sortByDescending$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(Object t, Object t2) {
                        Pair pair = (Pair) t2;
                        int txr3 = ((Number) pair.component1()).intValue();
                        int ty3 = ((Number) pair.component2()).intValue();
                        double ddx = (txr3 + 0.5d) - fx2$f;
                        double ddy = (ty3 + 0.5d) - fy2$f;
                        Double valueOf = Double.valueOf((ddx * ddx) + (ddy * ddy));
                        Pair pair2 = (Pair) t;
                        int txr4 = ((Number) pair2.component1()).intValue();
                        int ty4 = ((Number) pair2.component2()).intValue();
                        double ddx2 = (txr4 + 0.5d) - fx2$f;
                        double ddy2 = (ty4 + 0.5d) - fy2$f;
                        return ComparisonsKt.compareValues(valueOf, Double.valueOf((ddx2 * ddx2) + (ddy2 * ddy2)));
                    }
                });
            }
            Iterator it2 = order2.iterator();
            Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
            while (it2.hasNext()) {
                Object next = it2.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                Pair pair = (Pair) next;
                int txr3 = ((Number) pair.component1()).intValue();
                int ty3 = ((Number) pair.component2()).intValue();
                int tx = ((txr3 % n2) + n2) % n2;
                Iterator it3 = it2;
                ArrayList order3 = order2;
                double fx3 = fx2;
                float left = (float) (((txr3 / n2) - this.cx) * w);
                double fy3 = fy2;
                float top = (float) (((ty3 / n2) - this.cy) * w);
                RectF dst4 = dst3;
                dst4.set(left, top, ((float) tileSize2) + left + 0.6f, ((float) tileSize2) + top + 0.6f);
                int z5 = z4;
                String style4 = style;
                Bitmap b = this.this$0.getTiles().get(style4, z5, tx, ty3);
                if (b == null) {
                    int up = 1;
                    while (true) {
                        if (up >= 5) {
                            src3 = src3;
                            dst3 = dst4;
                            order2 = order3;
                            it2 = it3;
                            fx2 = fx3;
                            fy2 = fy3;
                            z4 = z5;
                            style = style4;
                            break;
                        }
                        int pz = z5 - up;
                        if (pz < 0) {
                            src3 = src3;
                            dst3 = dst4;
                            order2 = order3;
                            it2 = it3;
                            fx2 = fx3;
                            fy2 = fy3;
                            z4 = z5;
                            style = style4;
                            break;
                        }
                        Bitmap b2 = b;
                        int z6 = z5;
                        int ty13 = ty12;
                        Bitmap pb = this.this$0.getTiles().peek(style4, pz, tx >> up, ty3 >> up);
                        if (pb != null) {
                            int part = 512 >> up;
                            Rect src4 = src3;
                            src4.set((tx & ((1 << up) - 1)) * part, (ty3 & ((1 << up) - 1)) * part, 0, 0);
                            src4.right = src4.left + part;
                            src4.bottom = src4.top + part;
                            c.drawBitmap(pb, src4, dst4, this.tileP);
                            src3 = src4;
                            dst3 = dst4;
                            order2 = order3;
                            it2 = it3;
                            fx2 = fx3;
                            fy2 = fy3;
                            z4 = z6;
                            ty12 = ty13;
                            style = style4;
                            break;
                        }
                        up++;
                        b = b2;
                        z5 = z6;
                        ty12 = ty13;
                    }
                } else {
                    c.drawBitmap(b, (Rect) null, dst4, this.tileP);
                    z4 = z5;
                    style = style4;
                    dst3 = dst4;
                    order2 = order3;
                    it2 = it3;
                    fx2 = fx3;
                    fy2 = fy3;
                }
            }
            Canvas canvas3 = c;
            ArrayList order4 = order2;
            double fx4 = fx2;
            double fy4 = fy2;
            int ty14 = ty12;
            int z7 = z4;
            Rect src5 = src3;
            RectF dst5 = dst3;
            String style5 = style;
            int sel = Nav.INSTANCE.getShownSelected();
            Iterator it4 = Nav.INSTANCE.getShownLines().iterator();
            int i11 = 0;
            while (it4.hasNext()) {
                int i12 = i11;
                int i13 = i11 + 1;
                Nav.Line l3 = (Nav.Line) it4.next();
                if (l3 == null || i12 == sel) {
                    tileSize = tileSize2;
                    it = it4;
                    order = order4;
                    fx = fx4;
                    z = z7;
                    ty1 = ty14;
                    src = src5;
                    double d2 = fy4;
                    dst = dst5;
                    minY3 = minY2;
                    style2 = style5;
                    fy = d2;
                } else {
                    order = order4;
                    fx = fx4;
                    z = z7;
                    src = src5;
                    double d3 = fy4;
                    dst = dst5;
                    minY3 = minY2;
                    style2 = style5;
                    fy = d3;
                    ty1 = ty14;
                    tileSize = tileSize2;
                    it = it4;
                    stroke(c, l3, 0, l3.getSize() - 1, this.this$0.dp(10.0f), this.this$0.host.getNight() ? -13945790 : -10521711, w);
                    stroke(c, l3, 0, l3.getSize() - 1, this.this$0.dp(6.0f), this.this$0.host.getNight() ? -10587504 : -6441773, w);
                }
                order4 = order;
                it4 = it;
                i11 = i13;
                ty14 = ty1;
                dst5 = dst;
                src5 = src;
                tileSize2 = tileSize;
                z7 = z;
                fx4 = fx;
                style5 = style2;
                minY2 = minY3;
                fy4 = fy;
            }
            Nav.Line l4 = (Nav.Line) CollectionsKt.getOrNull(Nav.INSTANCE.getShownLines(), sel);
            if (l4 != null) {
                MapPane mapPane2 = this.this$0;
                double a = mapPane2.drivenAlong();
                int split4 = !Double.isInfinite(a) && !Double.isNaN(a) ? l4.indexAt(a) : 0;
                if (split4 > 0) {
                    split = split4;
                    l = l4;
                    mapPane = mapPane2;
                    n = 1090519040;
                    stroke(c, l4, 0, split4, mapPane2.dp(7.0f), -7432802, w);
                } else {
                    split = split4;
                    l = l4;
                    mapPane = mapPane2;
                    n = 1090519040;
                }
                Nav.Line line = l;
                int i14 = split;
                stroke(c, line, i14, l.getSize() - 1, mapPane.dp(12.0f), -16040559, w);
                stroke(c, line, i14, l.getSize() - 1, mapPane.dp(n), -13664019, w);
                int length = l.getTrafficLevel().length;
                int r2 = 0;
                while (r2 < length) {
                    int lv = l.getTrafficLevel()[r2];
                    if (lv >= 2) {
                        Nav.Line l5 = l;
                        int split5 = split;
                        int from = RangesKt.coerceAtLeast(Math.max(l5.indexAt(l.getTrafficFrom()[r2]) - 1, split5), 0);
                        int to2 = l5.indexAt(l5.getTrafficTo()[r2]);
                        if (to2 > from) {
                            if (lv == 5) {
                                to = to2;
                                tx12 = tx13;
                                this.lineP.setPathEffect(new DashPathEffect(new float[]{mapPane.dp(6.0f), mapPane.dp(5.0f)}, 0.0f));
                            } else {
                                to = to2;
                                tx12 = tx13;
                            }
                            float dp = mapPane.dp(8.0f);
                            switch (lv) {
                                case 2:
                                    i7 = -678365;
                                    break;
                                case 3:
                                    i7 = -1554886;
                                    break;
                                case 4:
                                    i7 = -7464165;
                                    break;
                                default:
                                    i7 = -15658735;
                                    break;
                            }
                            i6 = 1090519040;
                            split2 = split5;
                            l2 = l5;
                            split3 = length;
                            r = r2;
                            stroke(c, l5, from, to, dp, i7, w);
                            this.lineP.setPathEffect(null);
                        } else {
                            split2 = split5;
                            l2 = l5;
                            split3 = length;
                            r = r2;
                            i6 = n;
                            tx12 = tx13;
                        }
                    } else {
                        r = r2;
                        tx12 = tx13;
                        split2 = split;
                        l2 = l;
                        split3 = length;
                        i6 = n;
                    }
                    r2 = r + 1;
                    length = split3;
                    n = i6;
                    tx13 = tx12;
                    split = split2;
                    l = l2;
                }
                tx1 = 2;
                Unit unit = Unit.INSTANCE;
                Unit unit2 = Unit.INSTANCE;
            } else {
                tx1 = 2;
            }
            c.restore();
            JSONObject dpt = Nav.INSTANCE.getShownDestination();
            if (dpt != null) {
                double lat$iv = dpt.optDouble("latitude");
                double lon$iv = dpt.optDouble("longitude");
                if ((Double.isInfinite(lat$iv) || Double.isNaN(lat$iv)) ? false : true) {
                    if ((Double.isInfinite(lon$iv) || Double.isNaN(lon$iv)) ? false : true) {
                        float[] p$iv = screen(Nav.INSTANCE.mercX(lon$iv), Nav.INSTANCE.mercY(lat$iv), w);
                        if (p$iv[0] >= (-this.this$0.dp(40.0f)) && p$iv[1] >= (-this.this$0.dp(40.0f)) && p$iv[0] <= getWidth() + this.this$0.dp(40.0f) && p$iv[1] <= getHeight() + this.this$0.dp(40.0f)) {
                            float x = p$iv[0];
                            float y = p$iv[1];
                            pin(canvas3, x, y);
                        }
                    }
                }
                Unit unit3 = Unit.INSTANCE;
                Unit unit4 = Unit.INSTANCE;
            }
            Nav.Line l6 = Nav.INSTANCE.getShownLine();
            if (l6 != null) {
                for (Nav.Incident inc : l6.getIncidents()) {
                    double drivenAlong = this.this$0.drivenAlong();
                    if (!((Double.isInfinite(drivenAlong) || Double.isNaN(drivenAlong)) ? false : true) || inc.getEndM() >= this.this$0.drivenAlong()) {
                        double lat$iv2 = inc.getLat();
                        double lon$iv2 = inc.getLon();
                        if ((Double.isInfinite(lat$iv2) || Double.isNaN(lat$iv2)) ? false : true) {
                            if ((Double.isInfinite(lon$iv2) || Double.isNaN(lon$iv2)) ? false : true) {
                                float[] p$iv2 = screen(Nav.INSTANCE.mercX(lon$iv2), Nav.INSTANCE.mercY(lat$iv2), w);
                                if (p$iv2[0] >= (-this.this$0.dp(40.0f)) && p$iv2[1] >= (-this.this$0.dp(40.0f)) && p$iv2[0] <= getWidth() + this.this$0.dp(40.0f) && p$iv2[1] <= getHeight() + this.this$0.dp(40.0f)) {
                                    float x2 = p$iv2[0];
                                    float y2 = p$iv2[1];
                                    incident(canvas3, x2, y2, inc);
                                }
                            }
                        }
                    }
                }
            }
            int i15 = 0;
            for (Nav.Line ln2 : Nav.INSTANCE.getShownLines()) {
                int i16 = i15;
                int i17 = i15 + 1;
                Integer orNull = ArraysKt.getOrNull(this.labelAt, i16);
                int at = orNull != null ? orNull.intValue() : -1;
                if (ln2 == null || i16 == sel || at < 0) {
                    i5 = tx1;
                    canvas2 = canvas3;
                    tx02 = tx0;
                } else if (at >= ln2.getSize()) {
                    i5 = tx1;
                    canvas2 = canvas3;
                    tx02 = tx0;
                } else {
                    Iterable $this$firstOrNull$iv = Nav.INSTANCE.getShownRoutes();
                    Iterator it5 = $this$firstOrNull$iv.iterator();
                    while (true) {
                        if (it5.hasNext()) {
                            Object element$iv = it5.next();
                            if ((((Nav.Route) element$iv).getIndex() == i16 ? 1 : null) != null) {
                                obj = element$iv;
                            }
                        } else {
                            obj = null;
                        }
                    }
                    Nav.Route r3 = (Nav.Route) obj;
                    if (r3 == null) {
                        i5 = tx1;
                        canvas2 = canvas3;
                        tx02 = tx0;
                    } else {
                        Nav.Route cur = Nav.INSTANCE.getShownRoute();
                        double diff = cur != null ? r3.getDurationS() - cur.getDurationS() : 0.0d;
                        int i18 = tx1;
                        String txt = Math.abs(diff) < 60.0d ? "same time" : (diff > 0.0d ? "+" : "−") + Nav.INSTANCE.durationText(Math.abs(diff));
                        float[] xy = screen(ln2.getMx()[at], ln2.getMy()[at], w);
                        tx02 = tx0;
                        i5 = i18;
                        canvas2 = canvas3;
                        bubble(c, xy[0], xy[1], txt, diff < -60.0d ? -13722793 : -12958894);
                    }
                }
                canvas3 = canvas2;
                tx1 = i5;
                i15 = i17;
                tx0 = tx02;
            }
            int i19 = tx1;
            Canvas canvas4 = canvas3;
            if (this.overviewOn) {
                Nav.Route r4 = Nav.INSTANCE.getShownRoute();
                if (r4 != null) {
                    Nav.Line it6 = Nav.INSTANCE.getShownLine();
                    if (it6 != null) {
                        if (!(it6.getSize() > 0)) {
                            it6 = null;
                        }
                        if (it6 != null) {
                            Nav.Line ln3 = it6;
                            i4 = 0;
                            canvas = canvas4;
                            float[] xy2 = screen(ln3.getMx()[ln3.getSize() / i19], ln3.getMy()[ln3.getSize() / i19], w);
                            bubble(c, xy2[0], xy2[1], Nav.INSTANCE.durationText(r4.getDurationS()), -13664019);
                            Unit unit5 = Unit.INSTANCE;
                            Unit unit6 = Unit.INSTANCE;
                        }
                    }
                    i4 = 0;
                    canvas = canvas4;
                } else {
                    i4 = 0;
                    canvas = canvas4;
                }
            } else {
                i4 = 0;
                canvas = canvas4;
            }
            double lat = this.this$0.carLat();
            double lon = this.this$0.carLon();
            if (((Double.isInfinite(lat) || Double.isNaN(lat)) ? i4 : 1) != 0) {
                float[] xy3 = screen(Nav.INSTANCE.mercX(lon), Nav.INSTANCE.mercY(lat), w);
                Float valueOf = Float.valueOf(this.this$0.carBearing());
                float it7 = valueOf.floatValue();
                if (((Float.isInfinite(it7) || Float.isNaN(it7)) ? i4 : 1) == 0) {
                    valueOf = null;
                }
                car(canvas, xy3[i4], xy3[1], (valueOf != null ? valueOf.floatValue() : 0.0f) - this.bearing);
            }
            this.textP.setTextAlign(Paint.Align.LEFT);
            this.textP.setTextSize(this.this$0.dp(10.0f));
            this.textP.setColor(this.this$0.host.getNight() ? Color.argb(150, 255, 255, 255) : Color.argb(150, i4, i4, i4));
            canvas.drawText("© Mapbox © OpenStreetMap", this.this$0.dp(8.0f), (getHeight() - this.this$0.dp(4.0f)) - (this.this$0.bottomBar.getVisibility() == 0 ? this.this$0.dp(10.0f) + this.this$0.bottomBar.getHeight() : 0.0f), this.textP);
            this.textP.setTextAlign(Paint.Align.CENTER);
            this.textP.setColor(-1);
        }

        private final float[] screen(double mx, double my, double w) {
            float[] p = {(float) ((mx - this.cx) * w), (float) ((my - this.cy) * w)};
            this.m.mapPoints(p);
            return p;
        }

        private final void marker(Canvas c, double lat, double lon, double w, Function2<? super Float, ? super Float, Unit> draw) {
            if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
                if ((Double.isInfinite(lon) || Double.isNaN(lon)) ? false : true) {
                    float[] p = screen(Nav.INSTANCE.mercX(lon), Nav.INSTANCE.mercY(lat), w);
                    if (p[0] < (-this.this$0.dp(40.0f)) || p[1] < (-this.this$0.dp(40.0f)) || p[0] > getWidth() + this.this$0.dp(40.0f)) {
                        return;
                    }
                    if (p[1] > getHeight() + this.this$0.dp(40.0f)) {
                        return;
                    }
                    draw.invoke(Float.valueOf(p[0]), Float.valueOf(p[1]));
                }
            }
        }

        private final void stroke(Canvas c, Nav.Line l, int i0, int i1, float width, int color, double w) {
            if (i1 <= i0) {
                return;
            }
            this.path.reset();
            float lx = 0.0f;
            float ly = 0.0f;
            boolean started = false;
            int i = i0;
            if (i <= i1) {
                while (true) {
                    float x = (float) ((l.getMx()[i] - this.cx) * w);
                    float y = (float) ((l.getMy()[i] - this.cy) * w);
                    if (!started) {
                        this.path.moveTo(x, y);
                        lx = x;
                        ly = y;
                        started = true;
                    } else if (i == i1 || Math.abs(x - lx) >= 1.5f || Math.abs(y - ly) >= 1.5f) {
                        this.path.lineTo(x, y);
                        lx = x;
                        ly = y;
                    }
                    if (i == i1) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            this.lineP.setStrokeWidth(width);
            this.lineP.setColor(color);
            c.drawPath(this.path, this.lineP);
        }

        private final void car(Canvas c, float x, float y, float rot) {
            c.save();
            c.translate(x, y);
            c.rotate(rot);
            float r = this.this$0.dp(15.0f);
            Path p = new Path();
            p.moveTo(0.0f, -r);
            p.lineTo(r * 0.8f, r * 0.85f);
            p.lineTo(0.0f, 0.45f * r);
            p.lineTo((-r) * 0.8f, 0.85f * r);
            p.close();
            this.fillP.setStyle(Paint.Style.FILL);
            this.fillP.setColor(1429176557);
            c.drawCircle(0.0f, 0.0f, 1.6f * r, this.fillP);
            this.fillP.setColor(-1);
            this.fillP.setStyle(Paint.Style.STROKE);
            this.fillP.setStrokeWidth(this.this$0.dp(5.0f));
            this.fillP.setStrokeJoin(Paint.Join.ROUND);
            c.drawPath(p, this.fillP);
            this.fillP.setStyle(Paint.Style.FILL);
            this.fillP.setColor(-13664019);
            c.drawPath(p, this.fillP);
            c.restore();
        }

        private final void pin(Canvas c, float x, float y) {
            float r = this.this$0.dp(11.0f);
            this.fillP.setStyle(Paint.Style.FILL);
            this.fillP.setColor(-1755077);
            c.drawCircle(x, y - (r * 2.0f), r, this.fillP);
            Path p = new Path();
            p.moveTo(x - (r * 0.75f), y - (r * 1.4f));
            p.lineTo(x, y);
            p.lineTo((0.75f * r) + x, y - (1.4f * r));
            p.close();
            c.drawPath(p, this.fillP);
            this.fillP.setColor(-1);
            c.drawCircle(x, y - (2.0f * r), 0.4f * r, this.fillP);
        }

        private final void incident(Canvas c, float x, float y, Nav.Incident i) {
            float r = this.this$0.dp(14.0f);
            this.fillP.setStyle(Paint.Style.FILL);
            this.fillP.setColor((i.getClosed() || Intrinsics.areEqual(i.getType(), "road_closure")) ? -15000805 : Intrinsics.areEqual(i.getType(), "accident") ? -1755077 : Intrinsics.areEqual(i.getType(), "construction") ? -1012736 : -678365);
            c.drawCircle(x, y, r, this.fillP);
            this.fillP.setStyle(Paint.Style.STROKE);
            this.fillP.setStrokeWidth(this.this$0.dp(2.0f));
            this.fillP.setColor(-1);
            c.drawCircle(x, y, r, this.fillP);
            this.textP.setTextSize(this.this$0.dp(15.0f));
            c.drawText((i.getClosed() || Intrinsics.areEqual(i.getType(), "road_closure")) ? "⛔" : Intrinsics.areEqual(i.getType(), "construction") ? "🚧" : Intrinsics.areEqual(i.getType(), "accident") ? "!" : Intrinsics.areEqual(i.getType(), "weather") ? "☂" : "⚠", x, this.this$0.dp(5.5f) + y, this.textP);
        }

        private final void bubble(Canvas c, float x, float y, String text, int color) {
            this.textP.setTextSize(this.this$0.dp(14.0f));
            float tw = this.textP.measureText(text);
            float f = 2;
            RectF r = new RectF((x - (tw / f)) - this.this$0.dp(9.0f), y - this.this$0.dp(30.0f), (tw / f) + x + this.this$0.dp(9.0f), y - this.this$0.dp(6.0f));
            this.fillP.setStyle(Paint.Style.FILL);
            this.fillP.setColor(color);
            c.drawRoundRect(r, this.this$0.dp(8.0f), this.this$0.dp(8.0f), this.fillP);
            c.drawText(text, x, y - this.this$0.dp(13.0f), this.textP);
        }

        private final boolean twoFinger(MotionEvent e) {
            boolean z;
            int i;
            boolean z2 = false;
            if (e.getPointerCount() < 2) {
                if (e.getActionMasked() == 1 || e.getActionMasked() == 3) {
                    this.twoMode = 0;
                }
                return false;
            }
            float x0 = e.getX(0);
            float y0 = e.getY(0);
            float x1 = e.getX(1);
            float y1 = e.getY(1);
            double ang = Math.toDegrees(Math.atan2(y1 - y0, x1 - x0));
            float mid = (y0 + y1) / 2;
            float span = (float) Math.hypot(x1 - x0, y1 - y0);
            switch (e.getActionMasked()) {
                case 2:
                    if (this.twoMode == 0) {
                        double dA = Math.abs((((ang - this.twoStartAngle) + 540) % 360) - 180);
                        float dM = Math.abs(mid - this.twoStartMidY);
                        float dS = Math.abs(span - this.twoStartSpan);
                        if (dA > 10.0d) {
                            i = 2;
                        } else if (dM <= this.this$0.dp(22.0f) || dS >= this.this$0.dp(30.0f) || Math.abs(y1 - y0) >= 0.8f * span) {
                            i = dS > this.this$0.dp(24.0f) ? 1 : 0;
                        } else {
                            i = 3;
                        }
                        this.twoMode = i;
                    }
                    switch (this.twoMode) {
                        case 2:
                            double dA2 = (((ang - this.twoAngle) + 540) % 360) - 180;
                            if (this.following) {
                                unfollow();
                            }
                            z = true;
                            this.userRotated = true;
                            z2 = false;
                            this.overviewOn = false;
                            this.bearing = (((this.bearing - ((float) dA2)) % 360.0f) + 360.0f) % 360.0f;
                            invalidate();
                            this.this$0.refreshButtons();
                            break;
                        case 3:
                            float np = RangesKt.coerceIn(this.this$0.getS().getFloat("map_tilt", 0.0f) + ((this.twoMidY - mid) * 0.18f), 0.0f, 60.0f);
                            this.this$0.getS().setFloat("map_tilt", np);
                            invalidate();
                            this.this$0.refreshButtons();
                            z = true;
                            z2 = false;
                            break;
                        default:
                            z = true;
                            z2 = false;
                            break;
                    }
                    this.twoAngle = ang;
                    this.twoMidY = mid;
                    this.twoSpan = span;
                    this.lastTouchMs = System.currentTimeMillis();
                    break;
                case 5:
                    this.twoMode = 0;
                    this.twoAngle = ang;
                    this.twoMidY = mid;
                    this.twoSpan = span;
                    this.twoStartAngle = ang;
                    this.twoStartMidY = mid;
                    this.twoStartSpan = span;
                    z = true;
                    break;
                default:
                    z = true;
                    break;
            }
            return (this.twoMode == 2 || this.twoMode == 3) ? z : z2;
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            twoFinger(e);
            this.scale.onTouchEvent(e);
            this.gestures.onTouchEvent(e);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void tap(float sx, float sy) {
            double w;
            float[] prev;
            double u = world();
            setMatrix();
            Nav.Line l = Nav.INSTANCE.getShownLine();
            int jj = 1;
            if (l != null) {
                MapPane mapPane = this.this$0;
                for (Nav.Incident inc : l.getIncidents()) {
                    float[] p = screen(Nav.INSTANCE.mercX(inc.getLon()), Nav.INSTANCE.mercY(inc.getLat()), u);
                    if (((float) Math.hypot(p[0] - sx, p[1] - sy)) < mapPane.dp(26.0f)) {
                        String incidentWords = Nav.INSTANCE.incidentWords(inc);
                        String str = (!(inc.getImpact().length() > 0) || Intrinsics.areEqual(inc.getImpact(), "unknown")) ? "" : " · " + inc.getImpact();
                        double drivenAlong = mapPane.drivenAlong();
                        mapPane.showInfo(incidentWords + str + ((!(!Double.isInfinite(drivenAlong) && !Double.isNaN(drivenAlong)) || inc.getStartM() <= mapPane.drivenAlong()) ? "" : " · in " + Nav.distanceText$default(Nav.INSTANCE, inc.getStartM() - mapPane.drivenAlong(), mapPane.getT().isMetric(), false, 4, null)) + ((inc.getDescription().length() > 0 ? (char) 1 : (char) 0) != 0 ? "\n" + inc.getDescription() : ""));
                        return;
                    }
                }
            }
            double bd = this.this$0.dp(26.0f);
            int best = -1;
            double bd2 = bd;
            int best2 = 0;
            for (Nav.Line l2 : Nav.INSTANCE.getShownLines()) {
                int i = best2;
                int i2 = best2 + 1;
                if (l2 != null && i != Nav.INSTANCE.getShownSelected()) {
                    if (l2.getSize() >= 2) {
                        int step = Math.max(jj, l2.getSize() / 2000);
                        float[] prev2 = screen(l2.getMx()[r13], l2.getMy()[r13], u);
                        int j = step;
                        double bd3 = bd2;
                        int best3 = best;
                        float[] prev3 = prev2;
                        while (true) {
                            int jj2 = Math.min(j, l2.getSize() - jj);
                            int j2 = j;
                            float[] p2 = screen(l2.getMx()[jj2], l2.getMy()[jj2], u);
                            double ex = p2[r13] - prev3[r13];
                            double ey = p2[jj] - prev3[jj];
                            double l22 = (ex * ex) + (ey * ey);
                            double d = 0.0d;
                            if (l22 > 0.0d) {
                                w = u;
                                double w2 = sx - prev3[r13];
                                prev = prev3;
                                d = RangesKt.coerceIn(((w2 * ex) + ((sy - prev3[jj]) * ey)) / l22, 0.0d, 1.0d);
                            } else {
                                w = u;
                                prev = prev3;
                            }
                            double w3 = d;
                            r13 = 0;
                            double dd = Math.hypot((prev[0] + (w3 * ex)) - sx, (prev[1] + (w3 * ey)) - sy);
                            if (dd < bd3) {
                                bd3 = dd;
                                best3 = i;
                            }
                            if (jj2 == l2.getSize() - 1) {
                                break;
                            }
                            prev3 = p2;
                            j = j2 + step;
                            jj = 1;
                            u = w;
                        }
                        jj = 1;
                        best = best3;
                        best2 = i2;
                        bd2 = bd3;
                        u = w;
                    }
                }
                double w4 = u;
                jj = jj;
                best2 = i2;
                u = w4;
            }
            if (best >= 0) {
                this.this$0.lookAt(best);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void dropPin(float sx, float sy) {
            setMatrix();
            double[] p = toMerc(sx, sy);
            final double lat = Nav.INSTANCE.latOf(p[1]);
            final double lon = Nav.INSTANCE.lonOf(p[0]);
            final MapPane mapPane = this.this$0;
            new Thread(new Runnable() { // from class: com.starpilot.starview.MapPane$MapCanvas$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MapPane.MapCanvas.dropPin$lambda$19(MapPane.MapCanvas.this, lat, lon, mapPane);
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void dropPin$lambda$19(final MapCanvas this$0, final double $lat, final double $lon, final MapPane this$1) {
            final String addr;
            Address address;
            String str = null;
            try {
                List<Address> fromLocation = new Geocoder(this$0.getContext()).getFromLocation($lat, $lon, 1);
                if (fromLocation != null && (address = (Address) CollectionsKt.firstOrNull((List) fromLocation)) != null) {
                    str = address.getAddressLine(0);
                }
                addr = str;
            } catch (Exception e) {
                addr = null;
            }
            this$0.post(new Runnable() { // from class: com.starpilot.starview.MapPane$MapCanvas$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MapPane.MapCanvas.dropPin$lambda$19$lambda$18(addr, this$0, $lat, $lon, this$1);
                }
            });
        }

        /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
        public static final void dropPin$lambda$19$lambda$18(String var0, MapPane.MapCanvas var1, double var2, double var4, MapPane var6) {
            String var12;
            label27: {
                if (var0 != null) {
                    var12 = StringsKt.substringBefore(var0, ",", var0);
                    if (var12 != null) {
                        var12 = var12;
                        boolean var7;
                        if (var12.length() == 0) {
                            var7 = true;
                        } else {
                            var7 = false;
                        }

                        if (var7) {
                            var12 = null;
                        }

                        String var9 = var12;
                        var12 = var9;
                        if (var9 != null) {
                            break label27;
                        }
                    }
                }

                var12 = "Dropped pin";
            }

            android.app.AlertDialog.Builder var13 = new Builder(var1.getContext()).setTitle("Drive here?");
            String var10;
            if (var0 == null) {
                var10 = String.format("%.5f, %.5f", Arrays.copyOf(new Object[]{var2, var4}, 2));
                Intrinsics.checkNotNullExpressionValue(var10, "format(...)");
            } else {
                var10 = var0;
            }

            var13.setMessage(var10)
                .setPositiveButton("Go", new MapPane$MapCanvas$$ExternalSyntheticLambda1(var6, var12, var0, var2, var4))
                .setNegativeButton("Cancel", null)
                .show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void dropPin$lambda$19$lambda$18$lambda$17(MapPane this$0, String $name, String $addr, double $lat, double $lon, DialogInterface dialogInterface, int i) {
            this$0.host.navigateTo($name, $addr == null ? "" : $addr, $lat, $lon);
        }
    }
}
