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
    private String holdWhy;
    private int holding;
    private boolean lastArmed;
    private boolean lastLaneHold;
    private long lastSpoke;
    private LaneSuggest.State lastState;
    private TurnSignal.State lastTurn;
    private final LaneSuggest logic;
    private final Handler main;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LaneSuggestView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.logic = new LaneSuggest();
        this.turn = new TurnSignal();
        this.d = getResources().getDisplayMetrics().density;
        this.bg = new Paint(1);
        this.p = new Paint(1);
        TextPaint $this$title_u24lambda_u240 = new TextPaint(1);
        $this$title_u24lambda_u240.setColor(-1);
        $this$title_u24lambda_u240.setTypeface(Typeface.DEFAULT_BOLD);
        $this$title_u24lambda_u240.setTextSize(this.d * 20.0f);
        this.title = $this$title_u24lambda_u240;
        TextPaint $this$sub_u24lambda_u241 = new TextPaint(1);
        $this$sub_u24lambda_u241.setColor(Color.rgb(225, 230, 240));
        $this$sub_u24lambda_u241.setTextSize(this.d * 14.0f);
        this.sub = $this$sub_u24lambda_u241;
        Paint $this$btn_u24lambda_u242 = new Paint(1);
        $this$btn_u24lambda_u242.setColor(-1);
        $this$btn_u24lambda_u242.setTypeface(Typeface.DEFAULT_BOLD);
        $this$btn_u24lambda_u242.setTextSize(this.d * 17.0f);
        $this$btn_u24lambda_u242.setTextAlign(Paint.Align.CENTER);
        this.btn = $this$btn_u24lambda_u242;
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

    /* JADX WARN: Code restructure failed: missing block: B:92:0x0bfe, code lost:
    
        if (com.starpilot.starview.AmyVoice.INSTANCE.busy(r13) == false) goto L296;
     */
    /* JADX WARN: Removed duplicated region for block: B:252:0x06b0  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x06b3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void tick(LaneSuggest.In i, TurnSignal.In turnIn) {
        String str;
        String str2;
        LaneSuggest.State s;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        LaneSuggest.State s2;
        long now;
        TurnSignal.State ts;
        long now2;
        int i2;
        String str9;
        Pair pair;
        LaneSuggest.State s3;
        String tside;
        String str10;
        String str11;
        String lside;
        String str12;
        String str13;
        String str14;
        String str15;
        String str16;
        String str17;
        LaneSuggest.State s4;
        Object obj;
        String str18;
        StringBuilder append;
        String str19;
        String str20;
        boolean z;
        String str21;
        String str22;
        String str23;
        String str24;
        StringBuilder append2;
        String str25;
        String str26;
        long now3 = i != null ? i.getNowMs() : turnIn != null ? turnIn.getNowMs() : System.currentTimeMillis();
        probe(now3);
        LaneSuggest laneSuggest = this.logic;
        if (i != null) {
            laneSuggest.update(i);
        } else if (laneSuggest.getState() != LaneSuggest.State.IDLE && !this.logic.getTest()) {
            this.logic.reset();
        }
        if (turnIn != null && Intrinsics.areEqual((Object) this.commaConfirms, (Object) true)) {
            this.turn.update(turnIn);
        } else if (this.turn.getState() != TurnSignal.State.IDLE) {
            this.turn.reset();
        }
        LaneSuggest.State s5 = this.logic.getState();
        String side = this.logic.getDir() < 0 ? "left" : "right";
        boolean amy = Speech.INSTANCE.getUseAmy() && AmyVoice.INSTANCE.getReady();
        String str27 = ".";
        str = "left";
        if (s5 != this.lastState) {
            String lside2 = this.logic.getDir() < 0 ? "LEFT" : "RIGHT";
            switch (WhenMappings.$EnumSwitchMapping$0[s5.ordinal()]) {
                case 1:
                    lside = "sig_";
                    str12 = "Change lanes ";
                    str13 = ".";
                    str3 = "?";
                    str14 = "toUpperCase(...)";
                    if (this.lastState != LaneSuggest.State.WAITING) {
                        BlinkLog.INSTANCE.add("Change lanes? " + lside2 + " asked" + (this.logic.getAutoAsk() ? " (automatic, 3 s)" : "") + " - " + this.logic.getWhy());
                    }
                    Unit unit = Unit.INSTANCE;
                    break;
                case 2:
                    lside = "sig_";
                    str12 = "Change lanes ";
                    str13 = ".";
                    str3 = "?";
                    BlinkLog blinkLog = BlinkLog.INSTANCE;
                    String confirmedBy = this.logic.getConfirmedBy();
                    switch (confirmedBy.hashCode()) {
                        case 114595:
                            str14 = "toUpperCase(...)";
                            if (confirmedBy.equals("tap")) {
                                str15 = "tapping Change";
                                break;
                            }
                            str15 = this.logic.getConfirmedBy();
                            break;
                        case 3005871:
                            str14 = "toUpperCase(...)";
                            if (confirmedBy.equals("auto")) {
                                str15 = "automatic mode";
                                break;
                            }
                            str15 = this.logic.getConfirmedBy();
                            break;
                        case 105172251:
                            str14 = "toUpperCase(...)";
                            if (confirmedBy.equals("nudge")) {
                                String format = String.format("%.0f", Arrays.copyOf(new Object[]{Float.valueOf(this.logic.getNudgeTorque())}, 1));
                                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                                str15 = "a steering nudge (torque " + format + ")";
                                break;
                            }
                            str15 = this.logic.getConfirmedBy();
                            break;
                        case 108704329:
                            if (!confirmedBy.equals("route")) {
                                str14 = "toUpperCase(...)";
                                str15 = this.logic.getConfirmedBy();
                                break;
                            } else {
                                String why = this.logic.getWhy();
                                if (this.logic.getExitTries() > 1) {
                                    str14 = "toUpperCase(...)";
                                    str16 = ", try " + this.logic.getExitTries();
                                } else {
                                    str14 = "toUpperCase(...)";
                                    str16 = "";
                                }
                                str15 = "the route (" + why + str16 + ")";
                                break;
                            }
                        default:
                            str14 = "toUpperCase(...)";
                            str15 = this.logic.getConfirmedBy();
                            break;
                    }
                    blinkLog.add("Blinker " + lside2 + " ON - lane change confirmed by " + str15 + (this.logic.getTest() ? " (parked test)" : ""));
                    Unit unit2 = Unit.INSTANCE;
                    break;
                case 3:
                    lside = "sig_";
                    str12 = "Change lanes ";
                    str13 = ".";
                    str3 = "?";
                    BlinkLog.INSTANCE.add("Blinker off - openpilot started the lane change " + lside2);
                    Unit unit3 = Unit.INSTANCE;
                    str14 = "toUpperCase(...)";
                    break;
                case 4:
                    lside = "sig_";
                    str12 = "Change lanes ";
                    str13 = ".";
                    str3 = "?";
                    BlinkLog.INSTANCE.add("Change lanes: " + this.logic.getResult());
                    Unit unit4 = Unit.INSTANCE;
                    str14 = "toUpperCase(...)";
                    break;
                case 5:
                    lside = "sig_";
                    str12 = "Change lanes ";
                    str3 = "?";
                    BlinkLog blinkLog2 = BlinkLog.INSTANCE;
                    if (this.logic.getArmed()) {
                        String lowerCase = lside2.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                        str13 = ".";
                        str17 = "Blinker off - car in the " + lowerCase + " blind spot, goes again once it's clear";
                    } else {
                        str13 = ".";
                        String lowerCase2 = lside2.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                        str17 = "Change lanes? " + lside2 + " - car in the " + lowerCase2 + " blind spot, waiting to ask again";
                    }
                    blinkLog2.add(str17);
                    Unit unit5 = Unit.INSTANCE;
                    str14 = "toUpperCase(...)";
                    break;
                case 6:
                    lside = "sig_";
                    str12 = "Change lanes ";
                    if (this.lastState == LaneSuggest.State.ASKING) {
                        str3 = "?";
                        BlinkLog.INSTANCE.add("Change lanes? " + lside2 + " - not answered / No");
                    } else {
                        str3 = "?";
                        if (this.lastState == LaneSuggest.State.WAITING) {
                            BlinkLog.INSTANCE.add("Change lanes? " + lside2 + " - stopped waiting (No, gave up, or no longer needed)");
                        }
                    }
                    Unit unit6 = Unit.INSTANCE;
                    str13 = ".";
                    str14 = "toUpperCase(...)";
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            switch (WhenMappings.$EnumSwitchMapping$0[s5.ordinal()]) {
                case 1:
                    str7 = " - ";
                    s4 = s5;
                    String str28 = lside;
                    str6 = "substring(...)";
                    String str29 = str12;
                    str27 = str13;
                    obj = "nudge";
                    str4 = str14;
                    this.askedAt = now3;
                    this.repeated = false;
                    boolean route = !StringsKt.startsWith$default(this.logic.getWhy(), "the driving model", false, 2, (Object) null);
                    if (this.lastState == LaneSuggest.State.WAITING) {
                        BlinkLog.INSTANCE.add("Change lanes? " + (this.logic.getDir() < 0 ? "LEFT" : "RIGHT") + " asked again - the blind spot is clear");
                        String str30 = str3;
                        str2 = str28;
                        say("Clear now. Change lanes " + side + str30, AmySynth.Style.ASK, CollectionsKt.listOf("ask_" + side), now3);
                        str5 = "ask_";
                        str8 = str29;
                        str3 = str30;
                    } else {
                        str2 = str28;
                        String str31 = "ask_";
                        String str32 = str3;
                        if (this.logic.getAutoAsk()) {
                            Iterable $this$filter$iv = AmyPrompts.INSTANCE.ask(this.logic.getDir(), this.logic.getWhy());
                            int $i$f$filter = 0;
                            Collection destination$iv$iv = new ArrayList();
                            Iterable $this$filterTo$iv$iv = $this$filter$iv;
                            int $i$f$filterTo = 0;
                            Iterator it = $this$filterTo$iv$iv.iterator();
                            while (it.hasNext()) {
                                Iterable $this$filter$iv2 = $this$filter$iv;
                                Object element$iv$iv = it.next();
                                int $i$f$filter2 = $i$f$filter;
                                String it2 = (String) element$iv$iv;
                                Iterable $this$filterTo$iv$iv2 = $this$filterTo$iv$iv;
                                int $i$f$filterTo2 = $i$f$filterTo;
                                Iterator it3 = it;
                                if (StringsKt.startsWith$default(it2, str31, false, 2, (Object) null)) {
                                    str20 = str31;
                                } else {
                                    str20 = str31;
                                    if (!StringsKt.startsWith$default(it2, "lanes", false, 2, (Object) null)) {
                                        z = true;
                                        if (!z) {
                                            destination$iv$iv.add(element$iv$iv);
                                        }
                                        $this$filter$iv = $this$filter$iv2;
                                        $i$f$filter = $i$f$filter2;
                                        $this$filterTo$iv$iv = $this$filterTo$iv$iv2;
                                        $i$f$filterTo = $i$f$filterTo2;
                                        it = it3;
                                        str31 = str20;
                                    }
                                }
                                z = false;
                                if (!z) {
                                }
                                $this$filter$iv = $this$filter$iv2;
                                $i$f$filter = $i$f$filter2;
                                $this$filterTo$iv$iv = $this$filterTo$iv$iv2;
                                $i$f$filterTo = $i$f$filterTo2;
                                it = it3;
                                str31 = str20;
                            }
                            String str33 = str31;
                            List keys = CollectionsKt.plus((Collection<? extends String>) destination$iv$iv, "changing");
                            if (route) {
                                String substringBefore$default = StringsKt.substringBefore$default(Speech.INSTANCE.spoken(this.logic.getWhy()), str7, (String) null, 2, (Object) null);
                                if (substringBefore$default.length() > 0) {
                                    StringBuilder sb = new StringBuilder();
                                    char it4 = substringBefore$default.charAt(0);
                                    String valueOf = String.valueOf(it4);
                                    Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                                    String upperCase = valueOf.toUpperCase(Locale.ROOT);
                                    Intrinsics.checkNotNullExpressionValue(upperCase, str4);
                                    StringBuilder append3 = sb.append((Object) upperCase);
                                    String substring = substringBefore$default.substring(1);
                                    Intrinsics.checkNotNullExpressionValue(substring, str6);
                                    substringBefore$default = append3.append(substring).toString();
                                }
                                str19 = substringBefore$default + ". ";
                            } else {
                                str19 = "";
                            }
                            say(str19 + "Changing lanes " + side + str27, AmySynth.Style.ASK, keys, now3);
                            str8 = str29;
                            str5 = str33;
                            str3 = str32;
                        } else if (Intrinsics.areEqual(this.logic.getKind(), "pass")) {
                            str5 = str31;
                            say("Slower car ahead. Change lanes " + side + str32, AmySynth.Style.ASK, CollectionsKt.listOf(str31 + side), now3);
                            str3 = str32;
                            str8 = str29;
                        } else {
                            str5 = str31;
                            if (route) {
                                str18 = str29;
                                String spoken = Speech.INSTANCE.spoken(this.logic.getWhy());
                                if (spoken.length() > 0) {
                                    StringBuilder sb2 = new StringBuilder();
                                    char it5 = spoken.charAt(0);
                                    String valueOf2 = String.valueOf(it5);
                                    Intrinsics.checkNotNull(valueOf2, "null cannot be cast to non-null type java.lang.String");
                                    String upperCase2 = valueOf2.toUpperCase(Locale.ROOT);
                                    Intrinsics.checkNotNullExpressionValue(upperCase2, str4);
                                    StringBuilder append4 = sb2.append((Object) upperCase2);
                                    String substring2 = spoken.substring(1);
                                    Intrinsics.checkNotNullExpressionValue(substring2, str6);
                                    spoken = append4.append(substring2).toString();
                                }
                                append = new StringBuilder().append(spoken).append(". Change lanes ");
                            } else {
                                str18 = str29;
                                append = new StringBuilder().append(str18);
                            }
                            str3 = str32;
                            str8 = str18;
                            say(append.append(side).append(str32).toString(), AmySynth.Style.ASK, AmyPrompts.INSTANCE.ask(this.logic.getDir(), this.logic.getWhy()), now3);
                        }
                    }
                    Unit unit7 = Unit.INSTANCE;
                    break;
                case 2:
                    str7 = " - ";
                    String str34 = lside;
                    str6 = "substring(...)";
                    String str35 = str12;
                    str27 = str13;
                    obj = "nudge";
                    str4 = str14;
                    if (Intrinsics.areEqual(this.logic.getKind(), "exit")) {
                        s4 = s5;
                        str21 = str34;
                        if (this.logic.getExitTries() == 1) {
                            say((StringsKt.startsWith$default(this.logic.getWhy(), "fork", false, 2, (Object) null) ? "Fork" : "Exit") + " on the " + side + ". Signaling.", AmySynth.Style.SHORT, CollectionsKt.listOf(str21 + side), now3);
                        }
                    } else {
                        s4 = s5;
                        str21 = str34;
                        say("Signaling " + side + str27, AmySynth.Style.SHORT, CollectionsKt.listOf(str34 + side), now3);
                    }
                    Unit unit8 = Unit.INSTANCE;
                    str2 = str21;
                    str5 = "ask_";
                    str8 = str35;
                    break;
                case 3:
                    str7 = " - ";
                    str2 = lside;
                    str6 = "substring(...)";
                    String str36 = str12;
                    str27 = str13;
                    obj = "nudge";
                    str4 = str14;
                    if (!Intrinsics.areEqual(this.logic.getConfirmedBy(), "auto")) {
                        say("Changing lanes.", AmySynth.Style.SHORT, CollectionsKt.listOf("changing"), now3);
                    }
                    Unit unit9 = Unit.INSTANCE;
                    s4 = s5;
                    str5 = "ask_";
                    str8 = str36;
                    break;
                case 4:
                    str7 = " - ";
                    str2 = lside;
                    str6 = "substring(...)";
                    String str37 = str12;
                    if (StringsKt.contains$default((CharSequence) this.logic.getResult(), (CharSequence) "signaling again", false, 2, (Object) null)) {
                        str27 = str13;
                        obj = "nudge";
                        str4 = str14;
                    } else {
                        Pair<AmySynth.Style, String> result = AmyPrompts.INSTANCE.result(this.logic.getResult(), this.logic.getDir());
                        AmySynth.Style st = result.component1();
                        String key = result.component2();
                        if (StringsKt.startsWith$default(this.logic.getResult(), "changed lanes", false, 2, (Object) null)) {
                            str24 = "Done.";
                            str23 = str13;
                            str22 = str14;
                        } else {
                            String replace$default = StringsKt.replace$default(this.logic.getResult(), " - ", ". ", false, 4, (Object) null);
                            if (replace$default.length() > 0) {
                                StringBuilder sb3 = new StringBuilder();
                                char it6 = replace$default.charAt(0);
                                String valueOf3 = String.valueOf(it6);
                                Intrinsics.checkNotNull(valueOf3, "null cannot be cast to non-null type java.lang.String");
                                String upperCase3 = valueOf3.toUpperCase(Locale.ROOT);
                                str22 = str14;
                                Intrinsics.checkNotNullExpressionValue(upperCase3, str22);
                                StringBuilder append5 = sb3.append((Object) upperCase3);
                                String substring3 = replace$default.substring(1);
                                Intrinsics.checkNotNullExpressionValue(substring3, str6);
                                replace$default = append5.append(substring3).toString();
                            } else {
                                str22 = str14;
                            }
                            str23 = str13;
                            str24 = replace$default + str23;
                        }
                        str27 = str23;
                        obj = "nudge";
                        str4 = str22;
                        say(str24, st, CollectionsKt.listOf(key), now3);
                    }
                    Unit unit10 = Unit.INSTANCE;
                    s4 = s5;
                    str5 = "ask_";
                    str8 = str37;
                    break;
                case 5:
                    if (amy) {
                        AmyVoice.INSTANCE.ping();
                        str7 = " - ";
                        str2 = lside;
                        str6 = "substring(...)";
                        str26 = str12;
                    } else {
                        if (this.logic.getArmed()) {
                            append2 = new StringBuilder().append("Car on your ").append(side);
                            str25 = ". Waiting.";
                        } else {
                            append2 = new StringBuilder().append("Car on your ").append(side);
                            str25 = ". I'll ask again.";
                        }
                        str6 = "substring(...)";
                        str7 = " - ";
                        str2 = lside;
                        str26 = str12;
                        say(append2.append(str25).toString(), AmySynth.Style.SHORT, CollectionsKt.emptyList(), now3);
                    }
                    Unit unit11 = Unit.INSTANCE;
                    s4 = s5;
                    str5 = "ask_";
                    str27 = str13;
                    str8 = str26;
                    obj = "nudge";
                    str4 = str14;
                    break;
                default:
                    str7 = " - ";
                    s4 = s5;
                    str5 = "ask_";
                    str2 = lside;
                    str6 = "substring(...)";
                    str8 = str12;
                    str27 = str13;
                    obj = "nudge";
                    str4 = str14;
                    if (this.lastState == LaneSuggest.State.ASKING && amy) {
                        AmyVoice.INSTANCE.stop();
                    }
                    Unit unit12 = Unit.INSTANCE;
                    break;
            }
            s = s4;
            if (s == LaneSuggest.State.SIGNALING && Intrinsics.areEqual(this.logic.getConfirmedBy(), obj)) {
                performHapticFeedback(16);
            }
            this.lastState = s;
        } else {
            str2 = "sig_";
            s = s5;
            str3 = "?";
            str4 = "toUpperCase(...)";
            str5 = "ask_";
            str6 = "substring(...)";
            str7 = " - ";
            str8 = "Change lanes ";
        }
        TurnSignal.State ts2 = this.turn.getState();
        if (this.turn.getLaneHold() != this.lastLaneHold) {
            this.lastLaneHold = this.turn.getLaneHold();
            if (this.turn.getLaneHold() && this.turn.getState() == TurnSignal.State.WAITING) {
                BlinkLog.INSTANCE.add("Turn signal held off - not in a lane that turns " + (this.turn.getDir() < 0 ? str : "right") + " (route lanes + lane card)");
            }
        }
        if (ts2 != this.lastTurn) {
            String tside2 = this.turn.getDir() >= 0 ? "right" : "left";
            switch (WhenMappings.$EnumSwitchMapping$1[ts2.ordinal()]) {
                case 1:
                    s3 = s;
                    BlinkLog blinkLog3 = BlinkLog.INSTANCE;
                    String upperCase4 = tside2.toUpperCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(upperCase4, str4);
                    if (this.turn.getStreet().length() > 0) {
                        tside = tside2;
                        str10 = " onto " + this.turn.getStreet();
                    } else {
                        tside = tside2;
                        str10 = "";
                    }
                    String far = far(this.turn.getStartedAtM());
                    double gap = this.turn.getGap();
                    if ((Double.isInfinite(gap) || Double.isNaN(gap)) ? false : true) {
                        now = now3;
                        str11 = " (last cross street " + far(this.turn.getGap()) + " before the turn)";
                    } else {
                        now = now3;
                        str11 = "";
                    }
                    blinkLog3.add("Blinker " + upperCase4 + " ON - route turn" + str10 + ", " + far + " before it" + str11);
                    Unit unit13 = Unit.INSTANCE;
                    break;
                case 2:
                    s3 = s;
                    BlinkLog.INSTANCE.add("Turn signal off - " + this.turn.getResult());
                    Unit unit14 = Unit.INSTANCE;
                    tside = tside2;
                    now = now3;
                    break;
                case 3:
                    if (this.lastTurn == TurnSignal.State.SIGNALING) {
                        s3 = s;
                        BlinkLog.INSTANCE.add("Turn signal off - " + this.turn.getResult());
                    } else {
                        s3 = s;
                    }
                    Unit unit15 = Unit.INSTANCE;
                    tside = tside2;
                    now = now3;
                    break;
                default:
                    tside = tside2;
                    s3 = s;
                    now = now3;
                    Unit unit16 = Unit.INSTANCE;
                    break;
            }
            switch (WhenMappings.$EnumSwitchMapping$1[ts2.ordinal()]) {
                case 1:
                    ts = ts2;
                    String tside3 = tside;
                    s2 = s3;
                    double m = this.turn.getStartedAtM();
                    List dk = (120.0d > m ? 1 : (120.0d == m ? 0 : -1)) <= 0 && (m > 350.0d ? 1 : (m == 350.0d ? 0 : -1)) <= 0 ? CollectionsKt.listOf("turn_" + tside3 + "_" + AmyPrompts.INSTANCE.distKey(m)) : CollectionsKt.emptyList();
                    say("Signaling " + tside3 + " for the turn.", AmySynth.Style.SHORT, CollectionsKt.plus((Collection<? extends String>) dk, str2 + tside3), now);
                    Unit unit17 = Unit.INSTANCE;
                    break;
                case 2:
                    if (StringsKt.startsWith$default(this.turn.getResult(), "turned", false, 2, (Object) null)) {
                        ts = ts2;
                        s2 = s3;
                    } else {
                        String key2 = StringsKt.startsWith$default(this.turn.getResult(), "cancelled", false, 2, (Object) null) ? "stop_cancelled" : StringsKt.startsWith$default(this.turn.getResult(), "other blinker", false, 2, (Object) null) ? "stop_blinker" : "stop_generic";
                        String substringBefore$default2 = StringsKt.substringBefore$default(this.turn.getResult(), str7, (String) null, 2, (Object) null);
                        if (substringBefore$default2.length() > 0) {
                            StringBuilder sb4 = new StringBuilder();
                            char it7 = substringBefore$default2.charAt(0);
                            String valueOf4 = String.valueOf(it7);
                            Intrinsics.checkNotNull(valueOf4, "null cannot be cast to non-null type java.lang.String");
                            String upperCase5 = valueOf4.toUpperCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(upperCase5, str4);
                            StringBuilder append6 = sb4.append((Object) upperCase5);
                            String substring4 = substringBefore$default2.substring(1);
                            Intrinsics.checkNotNullExpressionValue(substring4, str6);
                            substringBefore$default2 = append6.append(substring4).toString();
                        }
                        ts = ts2;
                        s2 = s3;
                        say(substringBefore$default2 + str27, AmySynth.Style.STOPPED, CollectionsKt.listOf(key2), now);
                    }
                    Unit unit18 = Unit.INSTANCE;
                    break;
                default:
                    ts = ts2;
                    s2 = s3;
                    Unit unit182 = Unit.INSTANCE;
                    break;
            }
            this.lastTurn = ts;
        } else {
            s2 = s;
            now = now3;
            ts = ts2;
        }
        if (this.logic.getArmed() && !this.lastArmed && s2 == LaneSuggest.State.WAITING) {
            BlinkLog blinkLog4 = BlinkLog.INSTANCE;
            String str38 = this.logic.getDir() < 0 ? "LEFT" : "RIGHT";
            String format2 = String.format("%.0f", Arrays.copyOf(new Object[]{Float.valueOf(this.logic.getNudgeTorque())}, 1));
            Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
            blinkLog4.add(str8 + str38 + " confirmed by a steering nudge (torque " + format2 + ") - signals once the blind spot is clear");
            performHapticFeedback(16);
        }
        this.lastArmed = this.logic.getArmed();
        if (s2 != LaneSuggest.State.ASKING || this.logic.getAutoAsk() || this.repeated || now - this.askedAt <= 7000) {
            now2 = now;
        } else {
            if (amy) {
                now2 = now;
            } else {
                now2 = now;
            }
            this.repeated = true;
            say(str8 + side + str3, AmySynth.Style.SHORT, CollectionsKt.listOf(str5 + side), now2);
        }
        if (this.logic.getBeepNow()) {
            if (amy) {
                if (!AmyVoice.INSTANCE.busy(now2) && now2 - this.lastSpoke > 1500) {
                    AmyVoice.INSTANCE.ping();
                }
            } else if (!Speech.INSTANCE.getEnabled() || now2 - this.lastSpoke > 2500) {
                beep();
            }
        }
        if (amy && AmyVoice.INSTANCE.busy(now2)) {
            this.lastSpoke = now2;
        }
        if (this.logic.getHoldSide() != 0) {
            pair = TuplesKt.to(Integer.valueOf(this.logic.getHoldSide()), this.logic.getTest() ? "" : "lane");
            str9 = "";
            i2 = 0;
        } else if (this.turn.getHoldSide() != 0) {
            pair = TuplesKt.to(Integer.valueOf(this.turn.getHoldSide()), "turn");
            str9 = "";
            i2 = 0;
        } else {
            i2 = 0;
            str9 = "";
            pair = TuplesKt.to(0, str9);
        }
        int want = ((Number) pair.component1()).intValue();
        String why2 = (String) pair.component2();
        if (want != this.holding || !Intrinsics.areEqual(why2, this.holdWhy)) {
            int was = this.holding;
            this.holding = want;
            this.holdWhy = why2;
            this.main.removeCallbacks(this.repeat);
            if (want != 0) {
                this.repeat.run();
            } else if (was != 0) {
                send("off", str9);
            }
        }
        setVisibility((s2 == LaneSuggest.State.IDLE && (ts == TurnSignal.State.IDLE || ts == TurnSignal.State.WAITING)) ? 8 : i2);
        invalidate();
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
        String str2;
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
                    append = new StringBuilder().append(StringsKt.startsWith$default(this.logic.getWhy(), "fork", false, 2, (Object) null) ? "Fork" : "Exit").append(" ").append(side);
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
