package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.starpilot.starview.AmySynth;
import com.starpilot.starview.LaneSuggest;
import com.starpilot.starview.OsmRoads;
import com.starpilot.starview.TurnSignal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import android.graphics.Paint.Align;

/* compiled from: LaneSuggest.kt */
public final class LaneSuggestView extends View {
    public static final Companion Companion = new Companion(null);
    private static volatile Boolean lastCommaConfirms;
    private long askedAt;
    private final Paint bg;
    private final Paint btn;
    private volatile Boolean commaConfirms;
    private long crossAt;
    private double crossGap;
    private final float d;
    String holdWhy;
    int holding;
    private boolean lastArmed;
    private boolean lastLaneHold;
    private long lastSpoke;
    private LaneSuggest.State lastState;
    private TurnSignal.State lastTurn;
    private final LaneSuggest logic;
    final Handler main;
    private boolean metric;
    private final RectF noR;
    private final RectF okR;
    private final Paint p;
    private long probedAt;
    private final LaneSuggestView$repeat$1 repeat;
    private boolean repeated;
    private final TextPaint sub;
    private final TextPaint title;
    private ToneGenerator tone;
    private final TurnSignal turn;
    private boolean wasUp;

    /* compiled from: LaneSuggest.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[LaneSuggest.State.values().length];
            try {
                iArr[LaneSuggest.State.ASKING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[LaneSuggest.State.SIGNALING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[LaneSuggest.State.CHANGING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[LaneSuggest.State.RESULT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[LaneSuggest.State.WAITING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[LaneSuggest.State.IDLE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[TurnSignal.State.values().length];
            try {
                iArr2[TurnSignal.State.SIGNALING.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr2[TurnSignal.State.DONE.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr2[TurnSignal.State.WAITING.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* VF: from Vineflower (jadx: ) */
    public LaneSuggestView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.logic = new LaneSuggest();
        this.turn = new TurnSignal();
        this.d = this.getResources().getDisplayMetrics().density;
        this.bg = new Paint(1);
        this.p = new Paint(1);
        TextPaint var3 = new TextPaint(1);
        var3.setColor(-1);
        var3.setTypeface(Typeface.DEFAULT_BOLD);
        var3.setTextSize(this.d * 20.0F);
        this.title = var3;
        TextPaint var4 = new TextPaint(1);
        var4.setColor(Color.rgb(225, 230, 240));
        var4.setTextSize(this.d * 14.0F);
        this.sub = var4;
        Paint var5 = new Paint(1);
        var5.setColor(-1);
        var5.setTypeface(Typeface.DEFAULT_BOLD);
        var5.setTextSize(this.d * 17.0F);
        var5.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.btn = var5;
        this.okR = new RectF();
        this.noR = new RectF();
        this.main = new Handler(Looper.getMainLooper());
        this.holdWhy = "";
        this.lastState = LaneSuggest.State.IDLE;
        this.lastTurn = TurnSignal.State.IDLE;
        this.crossGap = Double.NaN;
        this.repeat = new LaneSuggestView$repeat$1(this);
    }

    public /* synthetic */ LaneSuggestView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final LaneSuggest getLogic() {
        return this.logic;
    }

    public final TurnSignal getTurn() {
        return this.turn;
    }

    public final Boolean getCommaConfirms() {
        return this.commaConfirms;
    }

    /* compiled from: LaneSuggest.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Boolean getLastCommaConfirms() {
            return LaneSuggestView.lastCommaConfirms;
        }

        public final void setLastCommaConfirms(Boolean bool) {
            LaneSuggestView.lastCommaConfirms = bool;
        }
    }

    private final void setCommaConfirms(Boolean v) {
        this.commaConfirms = v;
        lastCommaConfirms = v;
    }

    public final boolean getMetric() {
        return this.metric;
    }

    public final void setMetric(boolean z) {
        this.metric = z;
    }

    public final void probe(long now) {
        DataClient dc = Link.INSTANCE.getData();
        boolean up = dc != null && dc.getConnected();
        if (up != this.wasUp) {
            this.wasUp = up;
            if (up) {
                setCommaConfirms(null);
            }
        }
        if (!up || this.commaConfirms != null || this.holding != 0 || now - this.probedAt < 10000) {
            return;
        }
        this.probedAt = now;
        Intrinsics.checkNotNull(dc);
        dc.request("blink", MapsKt.mapOf(TuplesKt.to("side", "off")), new Function1() { // from class: com.starpilot.starview.LaneSuggestView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit probe$lambda$3;
                probe$lambda$3 = LaneSuggestView.probe$lambda$3(LaneSuggestView.this, (JSONObject) obj);
                return probe$lambda$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit probe$lambda$3(LaneSuggestView this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.setCommaConfirms(Boolean.valueOf(r.optBoolean("signal", false)));
        }
        return Unit.INSTANCE;
    }

    public final double crossGapFor(JSONObject nav, long now) {
        if (this.turn.getState() != TurnSignal.State.WAITING || nav == null) {
            if (this.turn.getState() == TurnSignal.State.IDLE) {
                this.crossGap = Double.NaN;
            }
            return this.crossGap;
        }
        if (now - this.crossAt < 1000) {
            return this.crossGap;
        }
        this.crossAt = now;
        OsmRoads.Fix f = OsmRoads.INSTANCE.getLastFix();
        if (f != null && !Double.isNaN(OsmRoads.INSTANCE.getLat0()) && System.nanoTime() - f.getTNs() <= 3000000000L) {
            float bearingDeg = f.getBearingDeg();
            if (((Float.isInfinite(bearingDeg) || Float.isNaN(bearingDeg)) ? false : true) && f.getSpeed() >= 2.0f) {
                double turnAhead = this.turn.getDistM();
                if (!((Double.isInfinite(turnAhead) || Double.isNaN(turnAhead)) ? false : true) || turnAhead <= 0.0d) {
                    return this.crossGap;
                }
                Pair<Double, Double> local = OsmRoads.INSTANCE.toLocal(f.getLat(), f.getLon());
                double e = local.component1().doubleValue();
                double n = local.component2().doubleValue();
                double yaw = 1.5707963267948966d - Math.toRadians(f.getBearingDeg());
                TurnSignal.Companion companion = TurnSignal.Companion;
                List<Pair<Double, String>> crossStreetsAhead = OsmRoads.INSTANCE.crossStreetsAhead(e, n, yaw, turnAhead + 20.0d);
                String optString = nav.optString("maneuverPrimaryText");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                double g = companion.lastCrossGap(crossStreetsAhead, turnAhead, optString);
                if ((Double.isInfinite(g) || Double.isNaN(g)) ? false : true) {
                    this.crossGap = g;
                }
                return this.crossGap;
            }
        }
        return this.crossGap;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final void tick(LaneSuggest.In var1, TurnSignal.In var2) {
        String var32 = null; // declarations Vineflower left out
        long var11;
        if (var1 != null) {
            var11 = var1.getNowMs();
        } else if (var2 != null) {
            var11 = var2.getNowMs();
        } else {
            var11 = System.currentTimeMillis();
        }

        this.probe(var11);
        LaneSuggest var13 = this.logic;
        if (var1 != null) {
            var13.update(var1);
        } else if (var13.getState() != LaneSuggest.State.IDLE && !this.logic.getTest()) {
            this.logic.reset();
        }

        if (var2 != null && Intrinsics.areEqual(this.commaConfirms, true)) {
            this.turn.update(var2);
        } else if (this.turn.getState() != TurnSignal.State.IDLE) {
            this.turn.reset();
        }

        LaneSuggest.State var59 = this.logic.getState();
        String var79;
        if (this.logic.getDir() < 0) {
            var79 = "left";
        } else {
            var79 = "right";
        }

        int var6;
        if (Speech.INSTANCE.getUseAmy() && AmyVoice.INSTANCE.getReady()) {
            var6 = 1;
        } else {
            var6 = 0;
        }

        LaneSuggest.State var21 = this.lastState;
        String var17 = "left";
        String var14;
        if (var59 != var21) {
            if (this.logic.getDir() < 0) {
                var14 = "LEFT";
            } else {
                var14 = "RIGHT";
            }

            switch (LaneSuggestView.WhenMappings.$EnumSwitchMapping$0[var59.ordinal()]) {
                case 1:
                    if (this.lastState != LaneSuggest.State.WAITING) {
                        BlinkLog var97 = BlinkLog.INSTANCE;
                        String var35;
                        if (this.logic.getAutoAsk()) {
                            var35 = " (automatic, 3 s)";
                        } else {
                            var35 = "";
                        }

                        String var122 = this.logic.getWhy();
                        var97.add("Change lanes? " + var14 + " asked" + var35 + " - " + var122);
                    }

                    Unit var36 = Unit.INSTANCE;
                    break;
                case 2:
                    BlinkLog var16;
                    label436: {
                        var16 = BlinkLog.INSTANCE;
                        String var30 = this.logic.getConfirmedBy();
                        switch (var30.hashCode()) {
                            case 114595:
                                if (var30.equals("tap")) {
                                    var32 = "tapping Change";
                                    break label436;
                                }
                                break;
                            case 3005871:
                                if (var30.equals("auto")) {
                                    var32 = "automatic mode";
                                    break label436;
                                }
                                break;
                            case 105172251:
                                if (var30.equals("nudge")) {
                                    String var33 = String.format("%.0f", Arrays.copyOf(new Object[]{this.logic.getNudgeTorque()}, 1));
                                    Intrinsics.checkNotNullExpressionValue(var33, "format(...)");
                                    var32 = "a steering nudge (torque " + var33 + ")";
                                    break label436;
                                }
                                break;
                            case 108704329:
                                if (var30.equals("route")) {
                                    String var95 = this.logic.getWhy();
                                    String var31;
                                    if (this.logic.getExitTry() > 1) {
                                        int var7 = this.logic.getExitTry();
                                        var31 = ", try " + var7;
                                    } else {
                                        var31 = "";
                                    }

                                    var32 = "the route (" + var95 + var31 + ")";
                                    break label436;
                                }
                        }

                        var32 = this.logic.getConfirmedBy();
                    }

                    String var96;
                    if (this.logic.getTest()) {
                        var96 = " (parked test)";
                    } else {
                        var96 = "";
                    }

                    var16.add("Blinker " + var14 + " ON - lane change confirmed by " + var32 + var96);
                    Unit var34 = Unit.INSTANCE;
                    break;
                case 3:
                    BlinkLog.INSTANCE.add("Blinker off - openpilot started the lane change " + var14);
                    Unit var29 = Unit.INSTANCE;
                    break;
                case 4:
                    BlinkLog var27 = BlinkLog.INSTANCE;
                    var14 = this.logic.getResult();
                    var27.add("Change lanes: " + var14);
                    Unit var28 = Unit.INSTANCE;
                    break;
                case 5:
                    BlinkLog var15 = BlinkLog.INSTANCE;
                    String var24;
                    if (this.logic.getArmed()) {
                        String var23 = var14.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var23, "toLowerCase(...)");
                        var24 = "Blinker off - car in the " + var23 + " blind spot, goes again once it's clear";
                    } else {
                        String var25 = var14.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var25, "toLowerCase(...)");
                        var24 = "Change lanes? " + var14 + " - car in the " + var25 + " blind spot, waiting to ask again";
                    }

                    var15.add(var24);
                    Unit var26 = Unit.INSTANCE;
                    break;
                case 6:
                    if (this.lastState == LaneSuggest.State.ASKING) {
                        BlinkLog.INSTANCE.add("Change lanes? " + var14 + " - not answered / No");
                    } else if (this.lastState == LaneSuggest.State.WAITING) {
                        BlinkLog.INSTANCE.add("Change lanes? " + var14 + " - stopped waiting (No, gave up, or no longer needed)");
                    }

                    Unit var22 = Unit.INSTANCE;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }

            String var37;
            switch (LaneSuggestView.WhenMappings.$EnumSwitchMapping$0[var59.ordinal()]) {
                case 1:
                    this.askedAt = var11;
                    this.repeated = false;
                    boolean var8 = StringsKt.startsWith(this.logic.getWhy(), "the driving model", false) ^ true;
                    if (this.lastState == LaneSuggest.State.WAITING) {
                        BlinkLog var85 = BlinkLog.INSTANCE;
                        String var48;
                        if (this.logic.getDir() < 0) {
                            var48 = "LEFT";
                        } else {
                            var48 = "RIGHT";
                        }

                        var85.add("Change lanes? " + var48 + " asked again - the blind spot is clear");
                        this.say("Clear now. Change lanes " + var79 + "?", AmySynth.Style.ASK, CollectionsKt.listOf("ask_" + var79), var11);
                        var37 = "ask_";
                    } else {
                        var37 = "ask_";
                        if (this.logic.getAutoAsk()) {
                            Iterable var125 = AmyPrompts.INSTANCE.ask(this.logic.getDir(), this.logic.getWhy());
                            boolean var10 = false;
                            var var142 = new ArrayList();
                            boolean var9 = false;

                            for (Object var19 : var125) {
                                String var20 = (String)var19;
                                boolean var71;
                                if (!StringsKt.startsWith(var20, var37, false) && !StringsKt.startsWith(var20, "lanes", false)) {
                                    var71 = true;
                                } else {
                                    var71 = false;
                                }

                                if (var71) {
                                    var142.add(var19);
                                }
                            }

                            List var87 = var142;
                            List var126 = CollectionsKt.plus(var87, "changing");
                            if (var8) {
                                String var100 = StringsKt.substringBefore(Speech.INSTANCE.spoken(this.logic.getWhy()), " - ", (Speech.INSTANCE.spoken(this.logic.getWhy())));
                                boolean var72;
                                if (var100.length() > 0) {
                                    var72 = true;
                                } else {
                                    var72 = false;
                                }

                                var14 = var100;
                                if (var72) {
                                    StringBuilder var89 = new StringBuilder();
                                    String var143 = String.valueOf(var100.charAt(0));
                                    Intrinsics.checkNotNull(var143, "null cannot be cast to non-null type java.lang.String");
                                    String var144 = var143.toUpperCase(Locale.ROOT);
                                    Intrinsics.checkNotNullExpressionValue(var144, "toUpperCase(...)");
                                    StringBuilder var90 = var89.append(var144);
                                    var100 = var100.substring(1);
                                    Intrinsics.checkNotNullExpressionValue(var100, "substring(...)");
                                    var14 = var90.append(var100).toString();
                                }

                                var14 = var14 + ". ";
                            } else {
                                var14 = "";
                            }

                            this.say(var14 + "Changing lanes " + var79 + ".", AmySynth.Style.ASK, var126, var11);
                        } else if (Intrinsics.areEqual(this.logic.getKind(), "pass")) {
                            String var127 = "Slower car ahead. Change lanes " + var79 + "?";
                            AmySynth.Style var92 = AmySynth.Style.ASK;
                            List var102 = CollectionsKt.listOf("ask_" + var79);
                            var37 = "ask_";
                            this.say(var127, var92, var102, var11);
                        } else {
                            var14 = "ask_";
                            StringBuilder var49;
                            if (!var8) {
                                var49 = new StringBuilder().append("Change lanes ");
                            } else {
                                String var103 = Speech.INSTANCE.spoken(this.logic.getWhy());
                                boolean var73;
                                if (var103.length() > 0) {
                                    var73 = true;
                                } else {
                                    var73 = false;
                                }

                                String var50 = var103;
                                if (var73) {
                                    StringBuilder var51 = new StringBuilder();
                                    String var128 = String.valueOf(var103.charAt(0));
                                    Intrinsics.checkNotNull(var128, "null cannot be cast to non-null type java.lang.String");
                                    var128 = var128.toUpperCase(Locale.ROOT);
                                    Intrinsics.checkNotNullExpressionValue(var128, "toUpperCase(...)");
                                    StringBuilder var52 = var51.append(var128);
                                    var103 = var103.substring(1);
                                    Intrinsics.checkNotNullExpressionValue(var103, "substring(...)");
                                    var50 = var52.append(var103).toString();
                                }

                                var49 = new StringBuilder().append(var50).append(". Change lanes ");
                            }

                            String var130 = var49.append(var79).append("?").toString();
                            AmySynth.Style var105 = AmySynth.Style.ASK;
                            List var53 = AmyPrompts.INSTANCE.ask(this.logic.getDir(), this.logic.getWhy());
                            this.say(var130, var105, var53, var11);
                            var37 = var14;
                        }
                    }

                    Unit var94 = Unit.INSTANCE;
                    break;
                case 2:
                    if (!Intrinsics.areEqual(this.logic.getKind(), "exit")) {
                        this.say("Signaling " + var79 + ".", AmySynth.Style.SHORT, CollectionsKt.listOf("sig_" + var79), var11);
                    } else if (this.logic.getExitTry() == 1) {
                        String var46;
                        if (StringsKt.startsWith(this.logic.getWhy(), "fork", false)) {
                            var46 = "Fork";
                        } else {
                            var46 = "Exit";
                        }

                        this.say(var46 + " on the " + var79 + ". Signaling.", AmySynth.Style.SHORT, CollectionsKt.listOf("sig_" + var79), var11);
                    }

                    Unit var47 = Unit.INSTANCE;
                    var37 = "ask_";
                    break;
                case 3:
                    if (!Intrinsics.areEqual(this.logic.getConfirmedBy(), "auto")) {
                        this.say("Changing lanes.", AmySynth.Style.SHORT, CollectionsKt.listOf("changing"), var11);
                    }

                    Unit var45 = Unit.INSTANCE;
                    var37 = "ask_";
                    break;
                case 4:
                    if (!StringsKt.contains(this.logic.getResult(), "signaling again", false)) {
                        Pair var40 = AmyPrompts.INSTANCE.result(this.logic.getResult(), this.logic.getDir());
                        AmySynth.Style var84 = (AmySynth.Style)var40.component1();
                        String var98 = (String)var40.component2();
                        String var41;
                        if (StringsKt.startsWith(this.logic.getResult(), "changed lanes", false)) {
                            var41 = "Done.";
                        } else {
                            String var42 = StringsKt.replace(this.logic.getResult(), " - ", ". ", false);
                            boolean var70;
                            if (var42.length() > 0) {
                                var70 = true;
                            } else {
                                var70 = false;
                            }

                            if (var70) {
                                StringBuilder var123 = new StringBuilder();
                                char var3 = var42.charAt(0);
                                String var18 = String.valueOf(var3);
                                Intrinsics.checkNotNull(var18, "null cannot be cast to non-null type java.lang.String");
                                var18 = var18.toUpperCase(Locale.ROOT);
                                Intrinsics.checkNotNullExpressionValue(var18, "toUpperCase(...)");
                                var123 = var123.append(var18);
                                String var43 = var42.substring(1);
                                Intrinsics.checkNotNullExpressionValue(var43, "substring(...)");
                                var42 = var123.append(var43).toString();
                            }

                            var41 = var42 + ".";
                        }

                        List var99 = CollectionsKt.listOf(var98);
                        this.say(var41, var84, var99, var11);
                    }

                    Unit var44 = Unit.INSTANCE;
                    var37 = "ask_";
                    break;
                case 5:
                    if ((var6 != 0)) {
                        AmyVoice.INSTANCE.ping();
                    } else {
                        StringBuilder var38;
                        if (this.logic.getArmed()) {
                            var38 = new StringBuilder().append("Car on your ").append(var79);
                            var14 = ". Waiting.";
                        } else {
                            var38 = new StringBuilder().append("Car on your ").append(var79);
                            var14 = ". I'll ask again.";
                        }

                        this.say(var38.append(var14).toString(), AmySynth.Style.SHORT, CollectionsKt.emptyList(), var11);
                    }

                    Unit var39 = Unit.INSTANCE;
                    var37 = "ask_";
                    break;
                default:
                    var37 = "ask_";
                    if (this.lastState == LaneSuggest.State.ASKING && (var6 != 0)) {
                        AmyVoice.INSTANCE.stop();
                    }

                    Unit var82 = Unit.INSTANCE;
            }

            LaneSuggest.State var106 = LaneSuggest.State.SIGNALING;
            if (var59 == var106 && Intrinsics.areEqual(this.logic.getConfirmedBy(), "nudge")) {
                this.performHapticFeedback(16);
            }

            this.lastState = var59;
            var14 = var37;
        } else {
            var14 = "ask_";
        }

        TurnSignal.State var145 = this.turn.getState();
        if (this.turn.getLaneHold() != this.lastLaneHold) {
            this.lastLaneHold = this.turn.getLaneHold();
            if (this.turn.getLaneHold() && this.turn.getState() == TurnSignal.State.WAITING) {
                BlinkLog var107 = BlinkLog.INSTANCE;
                String var60;
                if (this.turn.getDir() < 0) {
                    var60 = "left";
                } else {
                    var60 = "right";
                }

                var107.add("Turn signal held off - not in a lane that turns " + var60 + " (route lanes + lane card)");
            }
        }

        if (var145 != this.lastTurn) {
            String var61;
            if (this.turn.getDir() < 0) {
                var61 = var17;
            } else {
                var61 = "right";
            }

            switch (LaneSuggestView.WhenMappings.$EnumSwitchMapping$1[var145.ordinal()]) {
                case 1:
                    BlinkLog var146 = BlinkLog.INSTANCE;
                    var17 = var61.toUpperCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(var17, "toUpperCase(...)");
                    boolean var74;
                    if (this.turn.getStreet().length() > 0) {
                        var74 = true;
                    } else {
                        var74 = false;
                    }

                    String var114;
                    if (var74) {
                        var114 = this.turn.getStreet();
                        var114 = " onto " + var114;
                    } else {
                        var114 = "";
                    }

                    String var147 = this.far(this.turn.getStartedAtM());
                    double var4 = this.turn.getGap();
                    if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                        var74 = true;
                    } else {
                        var74 = false;
                    }

                    String var134;
                    if (var74) {
                        var134 = this.far(this.turn.getGap());
                        var134 = " (last cross street " + var134 + " before the turn)";
                    } else {
                        var134 = "";
                    }

                    var146.add("Blinker " + var17 + " ON - route turn" + var114 + ", " + var147 + " before it" + var134);
                    Unit var115 = Unit.INSTANCE;
                    break;
                case 2:
                    BlinkLog var111 = BlinkLog.INSTANCE;
                    String var132 = this.turn.getResult();
                    var111.add("Turn signal off - " + var132);
                    Unit var112 = Unit.INSTANCE;
                    break;
                case 3:
                    if (this.lastTurn == TurnSignal.State.SIGNALING) {
                        BlinkLog var131 = BlinkLog.INSTANCE;
                        String var109 = this.turn.getResult();
                        var131.add("Turn signal off - " + var109);
                    }

                    Unit var110 = Unit.INSTANCE;
                    break;
                default:
                    Unit var108 = Unit.INSTANCE;
            }

            switch (LaneSuggestView.WhenMappings.$EnumSwitchMapping$1[var145.ordinal()]) {
                case 1:
                    double var66 = this.turn.getStartedAtM();
                    boolean var77;
                    if (120.0 <= var66 && var66 <= 350.0) {
                        var77 = true;
                    } else {
                        var77 = false;
                    }

                    List var120;
                    if (var77) {
                        String var119 = AmyPrompts.INSTANCE.distKey(var66);
                        var120 = CollectionsKt.listOf("turn_" + var61 + "_" + var119);
                    } else {
                        var120 = CollectionsKt.emptyList();
                    }

                    this.say("Signaling " + var61 + " for the turn.", AmySynth.Style.SHORT, CollectionsKt.plus(var120, "sig_" + var61), var11);
                    Unit var64 = Unit.INSTANCE;
                    break;
                case 2:
                    if (!StringsKt.startsWith(this.turn.getResult(), "turned", false)) {
                        String var62;
                        if (StringsKt.startsWith(this.turn.getResult(), "cancelled", false)) {
                            var62 = "stop_cancelled";
                        } else if (StringsKt.startsWith(this.turn.getResult(), "other blinker", false)) {
                            var62 = "stop_blinker";
                        } else {
                            var62 = "stop_generic";
                        }

                        String var135 = StringsKt.substringBefore(this.turn.getResult(), " - ", this.turn.getResult());
                        boolean var76;
                        if (var135.length() > 0) {
                            var76 = true;
                        } else {
                            var76 = false;
                        }

                        String var116 = var135;
                        if (var76) {
                            StringBuilder var117 = new StringBuilder();
                            var17 = String.valueOf(var135.charAt(0));
                            Intrinsics.checkNotNull(var17, "null cannot be cast to non-null type java.lang.String");
                            var17 = var17.toUpperCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(var17, "toUpperCase(...)");
                            StringBuilder var118 = var117.append(var17);
                            var135 = var135.substring(1);
                            Intrinsics.checkNotNullExpressionValue(var135, "substring(...)");
                            var116 = var118.append(var135).toString();
                        }

                        this.say(var116 + ".", AmySynth.Style.STOPPED, CollectionsKt.listOf(var62), var11);
                    }
                default:
                    Unit var63 = Unit.INSTANCE;
            }

            this.lastTurn = var145;
        }

        if (this.logic.getArmed() && !this.lastArmed && var59 == LaneSuggest.State.WAITING) {
            BlinkLog var121 = BlinkLog.INSTANCE;
            String var55;
            if (this.logic.getDir() < 0) {
                var55 = "LEFT";
            } else {
                var55 = "RIGHT";
            }

            String var137 = String.format("%.0f", Arrays.copyOf(new Object[]{this.logic.getNudgeTorque()}, 1));
            Intrinsics.checkNotNullExpressionValue(var137, "format(...)");
            var121.add("Change lanes " + var55 + " confirmed by a steering nudge (torque " + var137 + ") - signals once the blind spot is clear");
            this.performHapticFeedback(16);
        }

        this.lastArmed = this.logic.getArmed();
        if (var59 == LaneSuggest.State.ASKING
            && !this.logic.getAutoAsk()
            && !this.repeated
            && var11 - this.askedAt > 7000L
            && ((var6 == 0) || !AmyVoice.INSTANCE.busy(var11))) {
            this.repeated = true;
            this.say("Change lanes " + var79 + "?", AmySynth.Style.SHORT, CollectionsKt.listOf(var14 + var79), var11);
        }

        if (this.logic.getBeepNow()) {
            if ((var6 != 0)) {
                if (!AmyVoice.INSTANCE.busy(var11) && var11 - this.lastSpoke > 1500L) {
                    AmyVoice.INSTANCE.ping();
                }
            } else if (!Speech.INSTANCE.getEnabled() || var11 - this.lastSpoke > 2500L) {
                this.beep();
            }
        }

        if ((var6 != 0) && AmyVoice.INSTANCE.busy(var11)) {
            this.lastSpoke = var11;
        }

        Pair var57;
        if (this.logic.getHoldSide() != 0) {
            var6 = this.logic.getHoldSide();
            String var56;
            if (this.logic.getTest()) {
                var56 = "";
            } else {
                var56 = "lane";
            }

            var57 = TuplesKt.to(var6, var56);
        } else if (this.turn.getHoldSide() != 0) {
            var57 = TuplesKt.to(this.turn.getHoldSide(), "turn");
        } else {
            var57 = TuplesKt.to(0, "");
        }

        int var78 = ((Number)var57.component1()).intValue();
        String var58 = (String)var57.component2();
        if (var78 != this.holding || !Intrinsics.areEqual(var58, this.holdWhy)) {
            var6 = this.holding;
            this.holding = var78;
            this.holdWhy = var58;
            this.main.removeCallbacks(this.repeat);
            if (var78 != 0) {
                this.repeat.run();
            } else if (var6 != 0) {
                this.send("off", "");
            }
        }

        byte var69;
        if (var59 != LaneSuggest.State.IDLE || var145 != TurnSignal.State.IDLE && var145 != TurnSignal.State.WAITING) {
            var69 = 0;
        } else {
            var69 = 8;
        }

        this.setVisibility(var69);
        this.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void send(String side, String why) {
        DataClient dc = Link.INSTANCE.getData();
        if (dc == null || !dc.getConnected()) {
            return;
        }
        dc.request("blink", why.length() == 0 ? MapsKt.mapOf(TuplesKt.to("side", side)) : MapsKt.mapOf(TuplesKt.to("side", side), TuplesKt.to("why", why)), new Function1() { // from class: com.starpilot.starview.LaneSuggestView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit send$lambda$9;
                send$lambda$9 = LaneSuggestView.send$lambda$9(LaneSuggestView.this, (JSONObject) obj);
                return send$lambda$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit send$lambda$9(LaneSuggestView this$0, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            this$0.setCommaConfirms(Boolean.valueOf(r.optBoolean("signal", false)));
        }
        return Unit.INSTANCE;
    }

    private final void say(String text, AmySynth.Style style, List<String> keys, long now) {
        if (Speech.INSTANCE.getEnabled()) {
            if (Speech.INSTANCE.getUseAmy() && AmyVoice.INSTANCE.getReady()) {
                AmyVoice.INSTANCE.play(style, keys);
            } else {
                Speech.speak$default(Speech.INSTANCE, text, false, 2, null);
            }
            this.lastSpoke = now;
        }
    }

    private final void beep() {
        try {
            ToneGenerator it = this.tone;
            if (it == null) {
                it = new ToneGenerator(4, 90);
                this.tone = it;
            }
            it.startTone(28, 350);
        } catch (Exception e) {
        }
    }

    public final void testAsk(String side, String why) {
        Intrinsics.checkNotNullParameter(side, "side");
        Intrinsics.checkNotNullParameter(why, "why");
        LaneSuggest laneSuggest = this.logic;
        int i = Intrinsics.areEqual(side, "right") ? 1 : -1;
        String str = why;
        if (StringsKt.isBlank(str)) {
            str = "the driving model wants to move " + side;
        }
        laneSuggest.testAsk(i, str, System.currentTimeMillis());
    }

    public final void stop() {
        if (this.logic.getState() == LaneSuggest.State.ASKING && Speech.INSTANCE.getUseAmy()) {
            AmyVoice.INSTANCE.stop();
        }
        this.logic.dismiss(System.currentTimeMillis());
        this.turn.reset();
        this.main.removeCallbacks(this.repeat);
        if (this.holding != 0) {
            send("off", "");
        }
        this.holding = 0;
        this.holdWhy = "";
        setVisibility(8);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.main.removeCallbacks(this.repeat);
        if (this.holding != 0) {
            send("off", "");
        }
        this.holding = 0;
        ToneGenerator toneGenerator = this.tone;
        if (toneGenerator != null) {
            toneGenerator.release();
        }
        this.tone = null;
    }

    private final boolean turnCard() {
        return this.logic.getState() == LaneSuggest.State.IDLE && this.turn.getState() != TurnSignal.State.IDLE;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        if (e.getActionMasked() != 1) {
            return true;
        }
        long now = System.currentTimeMillis();
        if (turnCard()) {
            if (this.noR.contains(e.getX(), e.getY())) {
                this.turn.cancel(now);
                performHapticFeedback(1);
            }
        } else if (this.okR.contains(e.getX(), e.getY()) && this.logic.getState() == LaneSuggest.State.ASKING) {
            LaneSuggest.confirm$default(this.logic, now, null, 2, null);
            performHapticFeedback(1);
        } else if (this.noR.contains(e.getX(), e.getY())) {
            this.logic.dismiss(now);
            performHapticFeedback(1);
        }
        invalidate();
        return true;
    }

    private final String far(double m) {
        StringBuilder append;
        String str;
        if (!((Double.isInfinite(m) || Double.isNaN(m)) ? false : true)) {
            return "";
        }
        if (!this.metric) {
            if (m < 300.0d) {
                return Math.max(0, ((int) ((3.28084d * m) / 10)) * 10) + " ft";
            }
            return (((int) ((m / 1609.34d) * 10)) / 10.0d) + " mi";
        }
        if (m < 1000.0d) {
            append = new StringBuilder().append(((int) (m / 10)) * 10);
            str = " m";
        } else {
            append = new StringBuilder().append(((int) (m / 100)) / 10.0d);
            str = " km";
        }
        return append.append(str).toString();
    }

    private final void arrow(Canvas c, float ax, float ay, float sz, int dir) {
        Path path = new Path();
        if (dir < 0) {
            path.moveTo(ax - sz, ay);
            path.lineTo(ax, ay - (sz * 0.8f));
            path.lineTo(ax, ay - (sz * 0.35f));
            path.lineTo(ax + sz, ay - (sz * 0.35f));
            path.lineTo(ax + sz, (sz * 0.35f) + ay);
            path.lineTo(ax, (0.35f * sz) + ay);
            path.lineTo(ax, (0.8f * sz) + ay);
        } else {
            path.moveTo(ax + sz, ay);
            path.lineTo(ax, ay - (sz * 0.8f));
            path.lineTo(ax, ay - (sz * 0.35f));
            path.lineTo(ax - sz, ay - (sz * 0.35f));
            path.lineTo(ax - sz, (sz * 0.35f) + ay);
            path.lineTo(ax, (0.35f * sz) + ay);
            path.lineTo(ax, (0.8f * sz) + ay);
        }
        path.close();
        this.p.setColor(-1);
        c.drawPath(path, this.p);
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        int rgb;
        boolean z;
        float textW;
        StringBuilder append;
        String str;
        String str2 = "";
        StringBuilder append2;
        String str3;
        Intrinsics.checkNotNullParameter(c, "c");
        float w = getWidth();
        float h = getHeight();
        float pad = this.d * 14.0f;
        long now = System.currentTimeMillis();
        boolean on = (now / ((long) 400)) % ((long) 2) == 0;
        float ax = pad + (this.d * 28.0f);
        float f = 2;
        float ay = h / f;
        float sz = this.d * 24.0f;
        float tx = ax + sz + (this.d * 18.0f);
        float bw = this.d * 104.0f;
        float bh = h - (f * pad);
        this.okR.setEmpty();
        this.noR.setEmpty();
        if (turnCard()) {
            TurnSignal.State ts = this.turn.getState();
            this.bg.setColor(ts == TurnSignal.State.SIGNALING ? on ? Color.rgb(20, 90, 160) : Color.rgb(14, 62, 112) : Color.argb(220, 30, 34, 42));
            c.drawRoundRect(new RectF(0.0f, 0.0f, w, h), this.d * 18.0f, this.d * 18.0f, this.bg);
            arrow(c, ax, ay, sz, this.turn.getDir());
            String tside = this.turn.getDir() < 0 ? "LEFT" : "RIGHT";
            if (ts == TurnSignal.State.SIGNALING) {
                this.noR.set((w - pad) - (0.9f * bw), pad, w - pad, pad + bh);
                this.p.setColor(Color.argb(160, 0, 0, 0));
                c.drawRoundRect(this.noR, this.d * 12.0f, this.d * 12.0f, this.p);
                c.drawText("Cancel", this.noR.centerX(), this.noR.centerY() + (this.d * 6.0f), this.btn);
                float textW2 = (this.noR.left - tx) - (this.d * 10.0f);
                c.drawText(TextUtils.ellipsize("Turn signal " + tside, this.title, textW2, TextUtils.TruncateAt.END).toString(), tx, (h / f) - (this.d * 4.0f), this.title);
                double dist = this.turn.getDistM();
                String str4 = this.turn.getStreet().length() > 0 ? "onto " + this.turn.getStreet() : "route turn";
                if (((Double.isInfinite(dist) || Double.isNaN(dist)) ? false : true) && dist > 5.0d) {
                    str2 = " · " + far(dist);
                }
                String tside2 = str2;
                String line = str4 + tside2;
                c.drawText(TextUtils.ellipsize(line, this.sub, textW2, TextUtils.TruncateAt.END).toString(), tx, (h / f) + (this.d * 20.0f), this.sub);
                postInvalidateDelayed(200L);
                return;
            }
            c.drawText(TextUtils.ellipsize(this.turn.getResult(), this.sub, (w - tx) - pad, TextUtils.TruncateAt.END).toString(), tx, (h / f) + (this.d * 6.0f), this.sub);
            return;
        }
        LaneSuggest.State s = this.logic.getState();
        Paint paint = this.bg;
        switch (WhenMappings.$EnumSwitchMapping$0[s.ordinal()]) {
            case 1:
                if (!on) {
                    rgb = Color.rgb(90, 55, 0);
                    break;
                } else {
                    rgb = Color.rgb(200, 120, 0);
                    break;
                }
            case 2:
                rgb = Color.rgb(20, 90, 160);
                break;
            case 3:
                rgb = Color.rgb(20, 120, 60);
                break;
            case 4:
            default:
                rgb = Color.argb(220, 30, 34, 42);
                break;
            case 5:
                rgb = Color.rgb(110, 70, 10);
                break;
        }
        paint.setColor(rgb);
        c.drawRoundRect(new RectF(0.0f, 0.0f, w, h), this.d * 18.0f, this.d * 18.0f, this.bg);
        arrow(c, ax, ay, sz, this.logic.getDir());
        String side = this.logic.getDir() < 0 ? "LEFT" : "RIGHT";
        switch (WhenMappings.$EnumSwitchMapping$0[s.ordinal()]) {
            case 1:
                if (this.logic.getAutoAsk()) {
                    this.noR.set((w - pad) - (0.9f * bw), pad, w - pad, pad + bh);
                    this.p.setColor(Color.argb(160, 0, 0, 0));
                    c.drawRoundRect(this.noR, this.d * 12.0f, this.d * 12.0f, this.p);
                    c.drawText("No", this.noR.centerX(), this.noR.centerY() + (this.d * 6.0f), this.btn);
                    textW = (this.noR.left - tx) - (this.d * 10.0f);
                    int secs = (int) ((this.logic.autoLeftMs(now) + 999) / 1000);
                    c.drawText(TextUtils.ellipsize("Changing lanes " + side + " in " + secs + "…", this.title, textW, TextUtils.TruncateAt.END).toString(), tx, (h / f) - (this.d * 4.0f), this.title);
                    z = false;
                } else {
                    this.noR.set((w - pad) - (0.7f * bw), pad, w - pad, pad + bh);
                    this.okR.set((this.noR.left - (this.d * 10.0f)) - bw, pad, this.noR.left - (this.d * 10.0f), pad + bh);
                    this.p.setColor(Color.rgb(34, 150, 74));
                    c.drawRoundRect(this.okR, this.d * 12.0f, this.d * 12.0f, this.p);
                    c.drawText("Change", this.okR.centerX(), this.okR.centerY() + (this.d * 6.0f), this.btn);
                    z = false;
                    this.p.setColor(Color.argb(160, 0, 0, 0));
                    c.drawRoundRect(this.noR, this.d * 12.0f, this.d * 12.0f, this.p);
                    c.drawText("No", this.noR.centerX(), this.noR.centerY() + (this.d * 6.0f), this.btn);
                    textW = (this.okR.left - tx) - (this.d * 10.0f);
                    c.drawText(TextUtils.ellipsize("Change lanes " + side + "?", this.title, textW, TextUtils.TruncateAt.END).toString(), tx, (h / f) - (this.d * 4.0f), this.title);
                }
                c.drawText(TextUtils.ellipsize((this.logic.getLabel().length() > 0 ? true : z ? this.logic.getLabel() + "  ·  " : "") + this.logic.getWhy(), this.sub, textW, TextUtils.TruncateAt.END).toString(), tx, (h / f) + (this.d * 20.0f), this.sub);
                break;
            case 2:
                this.noR.set((w - pad) - (0.9f * bw), pad, w - pad, pad + bh);
                this.p.setColor(Color.argb(160, 0, 0, 0));
                c.drawRoundRect(this.noR, this.d * 12.0f, this.d * 12.0f, this.p);
                c.drawText("Cancel", this.noR.centerX(), this.noR.centerY() + (this.d * 6.0f), this.btn);
                float textW3 = (this.noR.left - tx) - (this.d * 10.0f);
                boolean exitCard = Intrinsics.areEqual(this.logic.getKind(), "exit");
                if (exitCard) {
                    append = new StringBuilder().append(StringsKt.startsWith(this.logic.getWhy(), "fork", false) ? "Fork" : "Exit").append(" ").append(side);
                    str = " - signaling…";
                } else {
                    append = new StringBuilder().append("Signaling ").append(side);
                    str = "…";
                }
                c.drawText(TextUtils.ellipsize(append.append(str).toString(), this.title, textW3, TextUtils.TruncateAt.END).toString(), tx, (h / f) - (this.d * 4.0f), this.title);
                String confirmedBy = this.logic.getConfirmedBy();
                if (Intrinsics.areEqual(confirmedBy, "nudge")) {
                    str2 = "confirmed by your nudge - ";
                } else if (Intrinsics.areEqual(confirmedBy, "auto")) {
                    str2 = "automatic - ";
                }
                String by = str2;
                c.drawText(TextUtils.ellipsize(exitCard ? this.logic.getWhy() : by + "waiting for openpilot to start the lane change", this.sub, textW3, TextUtils.TruncateAt.END).toString(), tx, (h / f) + (this.d * 20.0f), this.sub);
                break;
            case 3:
                float f2 = (w - tx) - pad;
                c.drawText("Changing lanes " + side, tx, (h / f) + (this.d * 8.0f), this.title);
                break;
            case 4:
            default:
                c.drawText(TextUtils.ellipsize(this.logic.getResult(), this.sub, (w - tx) - pad, TextUtils.TruncateAt.END).toString(), tx, (h / f) + (this.d * 6.0f), this.sub);
                break;
            case 5:
                this.noR.set((w - pad) - (0.7f * bw), pad, w - pad, pad + bh);
                this.p.setColor(Color.argb(160, 0, 0, 0));
                c.drawRoundRect(this.noR, this.d * 12.0f, this.d * 12.0f, this.p);
                c.drawText("No", this.noR.centerX(), this.noR.centerY() + (this.d * 6.0f), this.btn);
                float textW4 = (this.noR.left - tx) - (this.d * 10.0f);
                if (this.logic.getArmed()) {
                    String lowerCase = side.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                    append2 = new StringBuilder().append("Car on your ").append(lowerCase);
                    str3 = " - goes when clear";
                } else {
                    String lowerCase2 = side.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                    append2 = new StringBuilder().append("Car on your ").append(lowerCase2);
                    str3 = " - waiting";
                }
                c.drawText(TextUtils.ellipsize(append2.append(str3).toString(), this.title, textW4, TextUtils.TruncateAt.END).toString(), tx, (h / f) - (this.d * 4.0f), this.title);
                c.drawText(TextUtils.ellipsize(this.logic.getArmed() ? "confirmed - signals once the blind spot is clear · No cancels" : "asks again once it's clear · nudge now to go when it's clear", this.sub, textW4, TextUtils.TruncateAt.END).toString(), tx, (h / f) + (this.d * 20.0f), this.sub);
                break;
        }
        if (s == LaneSuggest.State.ASKING || s == LaneSuggest.State.WAITING || s == LaneSuggest.State.SIGNALING) {
            postInvalidateDelayed(200L);
        }
    }
}
