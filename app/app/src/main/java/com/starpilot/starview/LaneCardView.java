package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.starpilot.starview.LaneBrain;
import com.starpilot.starview.LaneFixDialog;
import com.starpilot.starview.LaneRamps;
import com.starpilot.starview.LaneShots;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: LaneCardView.kt */
public final class LaneCardView extends View {
    public static final Companion Companion = new Companion(null);
    private final long UNDO_MS;
    private Function0<Pair<String, Boolean>> ahead;
    private final Paint bg;
    private final TextPaint big;
    private final Paint box;
    private final TextPaint btnText;
    private final float d;
    private final TextPaint dim;
    private final RectF fixR;
    private final TextPaint fixText;
    private String flash;
    private long flashMs;
    private boolean flashOk;
    private long lastCheckMs;
    private boolean lastCounted;
    private boolean lastOk;
    private int nRight;
    private int nWrong;
    private final RectF noR;
    private final RectF okR;
    private final SharedPreferences prefs;
    private int pressed;
    private Function0<? extends List<LaneRamps.Ramp>> ramps;
    private LaneShots shots;
    private final TextPaint small;
    private final TextPaint stat;
    private final RectF topR;
    private LaneBrain tracker;
    private final RectF undoR;
    private Function0<Triple<Double, Double, Float>> where;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LaneCardView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.where = new Function0() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Triple where$lambda$0;
                where$lambda$0 = LaneCardView.where$lambda$0();
                return where$lambda$0;
            }
        };
        this.d = getResources().getDisplayMetrics().density;
        Paint $this$bg_u24lambda_u241 = new Paint(1);
        $this$bg_u24lambda_u241.setColor(Color.argb(200, 0, 0, 0));
        this.bg = $this$bg_u24lambda_u241;
        this.box = new Paint(1);
        TextPaint $this$big_u24lambda_u242 = new TextPaint(1);
        $this$big_u24lambda_u242.setColor(-1);
        $this$big_u24lambda_u242.setTypeface(Typeface.DEFAULT_BOLD);
        $this$big_u24lambda_u242.setTextSize(this.d * 24.0f);
        this.big = $this$big_u24lambda_u242;
        TextPaint $this$small_u24lambda_u243 = new TextPaint(1);
        $this$small_u24lambda_u243.setColor(Color.rgb(200, 208, 222));
        $this$small_u24lambda_u243.setTextSize(this.d * 13.5f);
        this.small = $this$small_u24lambda_u243;
        TextPaint $this$dim_u24lambda_u244 = new TextPaint(1);
        $this$dim_u24lambda_u244.setColor(Color.rgb(150, 160, 178));
        $this$dim_u24lambda_u244.setTextSize(this.d * 12.5f);
        this.dim = $this$dim_u24lambda_u244;
        TextPaint $this$stat_u24lambda_u245 = new TextPaint(1);
        $this$stat_u24lambda_u245.setColor(Color.rgb(225, 230, 240));
        $this$stat_u24lambda_u245.setTextSize(this.d * 13.0f);
        $this$stat_u24lambda_u245.setTypeface(Typeface.DEFAULT_BOLD);
        this.stat = $this$stat_u24lambda_u245;
        TextPaint $this$btnText_u24lambda_u246 = new TextPaint(1);
        $this$btnText_u24lambda_u246.setColor(-1);
        $this$btnText_u24lambda_u246.setTypeface(Typeface.DEFAULT_BOLD);
        $this$btnText_u24lambda_u246.setTextSize(this.d * 24.0f);
        $this$btnText_u24lambda_u246.setTextAlign(Paint.Align.CENTER);
        this.btnText = $this$btnText_u24lambda_u246;
        this.prefs = ctx.getSharedPreferences("lane_card", 0);
        this.nRight = this.prefs.getInt("right", 0);
        this.nWrong = this.prefs.getInt("wrong", 0);
        this.flash = "";
        this.flashOk = true;
        this.okR = new RectF();
        this.noR = new RectF();
        this.undoR = new RectF();
        this.fixR = new RectF();
        this.topR = new RectF();
        TextPaint $this$fixText_u24lambda_u247 = new TextPaint(1);
        $this$fixText_u24lambda_u247.setColor(-1);
        $this$fixText_u24lambda_u247.setTypeface(Typeface.DEFAULT_BOLD);
        $this$fixText_u24lambda_u247.setTextSize(this.d * 16.0f);
        $this$fixText_u24lambda_u247.setTextAlign(Paint.Align.CENTER);
        this.fixText = $this$fixText_u24lambda_u247;
        this.lastOk = true;
        this.UNDO_MS = 8000L;
        setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LaneCardView.this.showHistory();
            }
        });
        setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean _init_$lambda$9;
                _init_$lambda$9 = LaneCardView._init_$lambda$9(LaneCardView.this, view);
                return _init_$lambda$9;
            }
        });
    }

    public /* synthetic */ LaneCardView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final LaneBrain getTracker() {
        return this.tracker;
    }

    public final void setTracker(LaneBrain laneBrain) {
        this.tracker = laneBrain;
    }

    public final LaneShots getShots() {
        return this.shots;
    }

    public final void setShots(LaneShots laneShots) {
        this.shots = laneShots;
    }

    public final Function0<List<LaneRamps.Ramp>> getRamps() {
        return this.ramps;
    }

    public final void setRamps(Function0<? extends List<LaneRamps.Ramp>> function0) {
        this.ramps = function0;
    }

    public final Function0<Pair<String, Boolean>> getAhead() {
        return this.ahead;
    }

    public final void setAhead(Function0<Pair<String, Boolean>> function0) {
        this.ahead = function0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Triple where$lambda$0() {
        Double valueOf = Double.valueOf(Double.NaN);
        return new Triple(valueOf, valueOf, Float.valueOf(0.0f));
    }

    public final Function0<Triple<Double, Double, Float>> getWhere() {
        return this.where;
    }

    public final void setWhere(Function0<Triple<Double, Double, Float>> function0) {
        Intrinsics.checkNotNullParameter(function0, "<set-?>");
        this.where = function0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean _init_$lambda$9(LaneCardView this$0, View it) {
        try {
            this$0.getContext().startActivity(new Intent(this$0.getContext(), (Class<?>) LanePicturesActivity.class));
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    private final int confColor(double c) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        if (c < 0.7d) {
            if (c >= 0.4d) {
                i4 = 196;
                i5 = 0;
            } else if (c > 0.0d) {
                i4 = 140;
                i5 = 60;
            } else {
                i = 138;
                i2 = 150;
                i3 = 130;
            }
            return Color.rgb(255, i4, i5);
        }
        i = 199;
        i2 = 89;
        i3 = 52;
        return Color.rgb(i3, i, i2);
    }

    private final String ago(long ms) {
        StringBuilder append;
        String str;
        long s = (System.currentTimeMillis() - ms) / 1000;
        if (s < 60) {
            append = new StringBuilder().append(s);
            str = "s ago";
        } else {
            append = new StringBuilder().append(s / 60);
            str = "m ago";
        }
        return append.append(str).toString();
    }

    private final void line(Canvas c, String text, TextPaint p, float y, float w) {
        c.drawText(TextUtils.ellipsize(text, p, w, TextUtils.TruncateAt.END).toString(), this.d * 14.0f, y, p);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(61:5|(1:7)(1:248)|8|(1:247)(1:12)|13|(1:15)(1:(1:245)(1:246))|16|(1:243)(1:20)|21|(1:23)(1:242)|24|(1:241)(1:28)|29|(8:31|(1:239)(1:35)|36|(2:38|(4:40|41|(3:43|(10:(1:45)(1:235)|(2:47|(7:49|50|(1:52)(1:232)|53|(1:231)(1:56)|57|(2:59|(3:61|62|(1:64)(1:65))(4:225|226|62|(0)(0)))(2:227|(4:229|226|62|(0)(0))(3:230|62|(0)(0)))))(1:234)|233|50|(0)(0)|53|(0)|231|57|(0)(0))|66)(1:236)|67))(1:238)|237|41|(0)(0)|67)(1:240)|68|(1:70)(1:224)|(1:72)|73|(3:75|(1:77)(1:222)|78)(1:223)|79|(1:85)|(1:87)(1:221)|(1:91)|92|(1:94)(32:218|(1:220)|96|(3:98|(1:100)(1:103)|(1:102))|104|(1:106)(1:217)|(1:108)|109|(1:111)(2:(6:204|(1:206)(1:215)|(1:208)|209|(1:211)(1:214)|212)|216)|112|(1:114)(1:202)|115|(1:117)(1:201)|118|119|120|(1:197)|124|(1:126)(1:196)|(3:128|(1:130)(1:179)|131)(9:180|(1:182)(1:195)|183|(1:185)(1:194)|186|(1:188)(1:193)|189|(1:191)|192)|132|(1:134)(1:178)|135|(1:137)(1:177)|138|(1:140)(1:176)|141|(1:143)(1:175)|144|(1:148)|149|(4:165|(1:167)(1:174)|168|(2:170|171)(2:172|173))(7:(1:153)(1:164)|154|(1:156)(1:163)|157|(1:159)(1:162)|160|161))|95|96|(0)|104|(0)(0)|(0)|109|(0)(0)|112|(0)(0)|115|(0)(0)|118|119|120|(1:122)|197|124|(0)(0)|(0)(0)|132|(0)(0)|135|(0)(0)|138|(0)(0)|141|(0)(0)|144|(2:146|148)|149|(0)|165|(0)(0)|168|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x05d1, code lost:
    
        r0 = kotlin.TuplesKt.to("", false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x0511, code lost:
    
        if (r0 == null) goto L157;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x046f  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0524  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x05f4  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x05fa  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x077f  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x079a  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x07dc  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0800  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x08bd  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0903  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0939  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x08c8  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0803  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x07de  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x07a3  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0781  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0653  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x05f6  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0542  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x04d3  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x046c  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x022f A[LOOP:0: B:44:0x0163->B:64:0x022f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x023d A[EDGE_INSN: B:65:0x023d->B:66:0x023d BREAK  A[LOOP:0: B:44:0x0163->B:64:0x022f], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0436  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onDraw(Canvas c) {
        int total;
        float pad;
        float f;
        float w;
        int m;
        LaneBrain tr;
        String str;
        LaneBrain.Event last;
        String str2;
        String str3;
        String str4;
        float pad2;
        String ah;
        int i;
        int i2;
        int i3;
        float cy;
        long now;
        float scoreW;
        int i4;
        char c2;
        int i5;
        int rgb;
        Function0<Pair<String, Boolean>> function0;
        String str5;
        int extraFromR;
        int extraFromL;
        int extraFromL2;
        int lanes;
        int i6;
        DashPathEffect dashPathEffect;
        int rgb2;
        int i7;
        Intrinsics.checkNotNullParameter(c, "c");
        LaneBrain tr2 = this.tracker;
        if (tr2 == null) {
            return;
        }
        float w2 = getWidth();
        float h = getHeight();
        float pad3 = this.d * 14.0f;
        c.drawRoundRect(new RectF(0.0f, 0.0f, w2, h), this.d * 16.0f, this.d * 16.0f, this.bg);
        Pair<Integer, Double> laneFromLeft = tr2.laneFromLeft();
        int n = laneFromLeft.component1().intValue();
        double conf = laneFromLeft.component2().doubleValue();
        Pair<Integer, String> laneCount = tr2.laneCount();
        int m2 = laneCount.component1().intValue();
        String mSrc = laneCount.component2();
        int total2 = m2 > 0 ? Math.max(m2, n) : 0;
        this.box.setColor((n <= 0 || conf >= 0.6d) ? confColor(conf) : Color.rgb(130, 138, 150));
        c.drawRoundRect(new RectF(0.0f, 0.0f, this.d * 6.0f, h), this.d * 3.0f, this.d * 3.0f, this.box);
        String title = tr2.label();
        LaneBrain.RoadModel rm = tr2.getModel();
        int lanes2 = total2 > 0 ? total2 : n > 0 ? n + 1 : 0;
        float diagW = 1 <= lanes2 && lanes2 < 9 ? (lanes2 * 18.0f * this.d) + ((lanes2 - 1) * 4.0f * this.d) + pad3 : 0.0f;
        float h2 = 2;
        c.drawText(TextUtils.ellipsize(title, this.big, (w2 - (pad3 * h2)) - diagW, TextUtils.TruncateAt.END).toString(), pad3, this.d * 32.0f, this.big);
        if (!(1 <= lanes2 && lanes2 < 9)) {
            total = total2;
            pad = pad3;
            f = h2;
            w = w2;
            m = m2;
            tr = tr2;
        } else {
            float bw = this.d * 18.0f;
            float gap = this.d * 4.0f;
            float bh = this.d * 26.0f;
            int extraFromL3 = (rm.getLanes() <= 0 || total2 != rm.getTotal()) ? 0 : rm.getExtraL();
            if (rm.getLanes() > 0) {
                m = m2;
                if (total2 == rm.getTotal()) {
                    extraFromR = rm.getExtraR();
                    total = total2;
                    float x = ((w2 - pad3) - (lanes2 * bw)) - ((lanes2 - 1) * gap);
                    float y = this.d * 12.0f;
                    tr = tr2;
                    int i8 = 1;
                    if (1 > lanes2) {
                        w = w2;
                        float w3 = x;
                        while (true) {
                            boolean ours = i8 == n;
                            if (i8 > extraFromL3) {
                                extraFromL = extraFromL3;
                                int extraFromL4 = lanes2 - extraFromR;
                                if (i8 <= extraFromL4) {
                                    extraFromL2 = 0;
                                    int n2 = n;
                                    int extraFromR2 = extraFromR;
                                    this.box.setStyle(!ours ? Paint.Style.FILL : Paint.Style.STROKE);
                                    pad = pad3;
                                    this.box.setStrokeWidth(this.d * 2.0f);
                                    Paint paint = this.box;
                                    if (extraFromL2 != 0 || ours) {
                                        lanes = lanes2;
                                        f = h2;
                                        i6 = i8;
                                        dashPathEffect = null;
                                    } else {
                                        f = h2;
                                        lanes = lanes2;
                                        i6 = i8;
                                        dashPathEffect = new DashPathEffect(new float[]{this.d * 4.0f, this.d * 3.0f}, 0.0f);
                                    }
                                    paint.setPathEffect(dashPathEffect);
                                    Paint paint2 = this.box;
                                    if (ours) {
                                        if (extraFromL2 != 0) {
                                            rgb2 = Color.rgb(255, 196, 0);
                                            paint2.setColor(rgb2);
                                            c.drawRoundRect(new RectF(w3, y, w3 + bw, y + bh), this.d * 4.0f, this.d * 4.0f, this.box);
                                            w3 += bw + gap;
                                            i7 = i6;
                                            lanes2 = lanes;
                                            if (i7 != lanes2) {
                                            }
                                        } else {
                                            rgb2 = Color.rgb(120, 130, 146);
                                            paint2.setColor(rgb2);
                                            c.drawRoundRect(new RectF(w3, y, w3 + bw, y + bh), this.d * 4.0f, this.d * 4.0f, this.box);
                                            w3 += bw + gap;
                                            i7 = i6;
                                            lanes2 = lanes;
                                            if (i7 != lanes2) {
                                            }
                                        }
                                    } else if (conf < 0.6d) {
                                        rgb2 = Color.rgb(130, 138, 150);
                                        paint2.setColor(rgb2);
                                        c.drawRoundRect(new RectF(w3, y, w3 + bw, y + bh), this.d * 4.0f, this.d * 4.0f, this.box);
                                        w3 += bw + gap;
                                        i7 = i6;
                                        lanes2 = lanes;
                                        if (i7 != lanes2) {
                                            break;
                                        }
                                        i8 = i7 + 1;
                                        extraFromL3 = extraFromL;
                                        n = n2;
                                        pad3 = pad;
                                        extraFromR = extraFromR2;
                                        h2 = f;
                                    } else {
                                        rgb2 = confColor(conf);
                                        paint2.setColor(rgb2);
                                        c.drawRoundRect(new RectF(w3, y, w3 + bw, y + bh), this.d * 4.0f, this.d * 4.0f, this.box);
                                        w3 += bw + gap;
                                        i7 = i6;
                                        lanes2 = lanes;
                                        if (i7 != lanes2) {
                                        }
                                    }
                                }
                            } else {
                                extraFromL = extraFromL3;
                            }
                            extraFromL2 = 1;
                            int n22 = n;
                            int extraFromR22 = extraFromR;
                            this.box.setStyle(!ours ? Paint.Style.FILL : Paint.Style.STROKE);
                            pad = pad3;
                            this.box.setStrokeWidth(this.d * 2.0f);
                            Paint paint3 = this.box;
                            if (extraFromL2 != 0) {
                            }
                            lanes = lanes2;
                            f = h2;
                            i6 = i8;
                            dashPathEffect = null;
                            paint3.setPathEffect(dashPathEffect);
                            Paint paint22 = this.box;
                            if (ours) {
                            }
                        }
                    } else {
                        pad = pad3;
                        f = h2;
                        w = w2;
                    }
                    this.box.setStyle(Paint.Style.FILL);
                    this.box.setPathEffect(null);
                }
            } else {
                m = m2;
            }
            extraFromR = 0;
            total = total2;
            float x2 = ((w2 - pad3) - (lanes2 * bw)) - ((lanes2 - 1) * gap);
            float y2 = this.d * 12.0f;
            tr = tr2;
            int i82 = 1;
            if (1 > lanes2) {
            }
            this.box.setStyle(Paint.Style.FILL);
            this.box.setPathEffect(null);
        }
        float lineW = w - (f * pad);
        LaneBrain tr3 = tr;
        Pair<Integer, Double> side = tr3.side(-1);
        int sl = side.component1().intValue();
        double ql = side.component2().doubleValue();
        Pair<Integer, Double> side2 = tr3.side(1);
        int sr = side2.component1().intValue();
        double qr = side2.component2().doubleValue();
        double d = 100;
        line(c, "lanes left of us: " + sl + " (" + ((int) (ql * d)) + "%)  ·  right: " + sr + " (" + ((int) (d * qr)) + "%)", this.small, this.d * 56.0f, lineW);
        String joinToString$default = CollectionsKt.joinToString$default(CollectionsKt.take(tr3.getWhy(), 2), "  ·  ", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence onDraw$lambda$10;
                onDraw$lambda$10 = LaneCardView.onDraw$lambda$10(LaneCardView.this, (LaneBrain.Event) obj);
                return onDraw$lambda$10;
            }
        }, 30, null);
        if (joinToString$default.length() == 0) {
            joinToString$default = "nothing seen yet";
        }
        line(c, "why: " + ((Object) joinToString$default), this.small, this.d * 74.0f, lineW);
        LaneBrain.Road road = tr3.getRoad();
        LaneBrain.RoadInfo inf = tr3.getInfo();
        StringBuilder $this$onDraw_u24lambda_u2412 = new StringBuilder();
        $this$onDraw_u24lambda_u2412.append("road: ");
        if (rm.getLanes() > 0) {
            str = rm.getLanes() + " lane" + (rm.getLanes() == 1 ? "" : "s") + " (" + rm.getSrc() + ")";
        } else {
            str = "count not known";
        }
        $this$onDraw_u24lambda_u2412.append(str);
        if (inf != null && inf.getHpmsLanes() > 0 && !Intrinsics.areEqual(rm.getSrc(), "HPMS")) {
            $this$onDraw_u24lambda_u2412.append(" · HPMS " + inf.getHpmsLanes());
        }
        if ((road != null ? road.getLanesOurWay() : 0) > 0 && !Intrinsics.areEqual(rm.getSrc(), "map")) {
            Intrinsics.checkNotNull(road);
            $this$onDraw_u24lambda_u2412.append(" · map " + road.getLanesOurWay());
        }
        if (!rm.getTunnel()) {
            str5 = rm.getBridge() ? " · bridge" : " · tunnel";
            if (inf != null) {
                if (inf.getExtraWhy().length() > 0) {
                    $this$onDraw_u24lambda_u2412.append(" · " + inf.getExtraWhy());
                }
            }
            if (mSrc.length() <= 0) {
                $this$onDraw_u24lambda_u2412.append("  → " + mSrc);
            }
            String roadTxt = $this$onDraw_u24lambda_u2412.toString();
            Intrinsics.checkNotNullExpressionValue(roadTxt, "toString(...)");
            line(c, roadTxt, this.dim, 91.0f * this.d, lineW);
            last = tr3.getEvents().firstOrNull();
            if (last == null) {
                str2 = last.getText() + " · " + ago(last.getAtMs());
            } else {
                if (road != null) {
                    String name = road.getName();
                    if (name.length() == 0) {
                        name = road.getKind();
                    }
                    str2 = "on " + ((Object) name) + (road.getOneway() ? " (one-way)" : "");
                }
                str2 = "waiting for a turn, a ramp or a lane line";
            }
            String ev = str2;
            if (tr3.getLastCameraMs() <= 0) {
                str3 = " · ";
                str4 = "camera confirmed " + ago(tr3.getLastCameraMs());
            } else {
                str3 = " · ";
                str4 = "camera: nothing yet";
            }
            String cam = str4;
            String hp = !Hpms.INSTANCE.files().isEmpty() ? "  ·  no road data (Settings › Lanes)" : "";
            String roadTxt2 = str3;
            pad2 = pad;
            line(c, cam + "  ·  lane known " + tr3.knownPct() + "% of " + ((int) (tr3.getDriveS() / 60)) + " min" + hp, this.small, 109.0f * this.d, lineW);
            function0 = this.ahead;
            if (function0 != null || (r0 = function0.invoke()) == null) {
                Pair<String, Boolean> pair = TuplesKt.to("", false);
            }
            ah = pair.component1();
            boolean urgent = pair.component2().booleanValue();
            if (!(ah.length() <= 0)) {
                TextPaint textPaint = this.stat;
                if (urgent) {
                    c2 = 255;
                    rgb = Color.rgb(255, 90, 80);
                    i5 = 0;
                } else {
                    c2 = 255;
                    i5 = 0;
                    rgb = Color.rgb(255, 196, 0);
                }
                textPaint.setColor(rgb);
                line(c, "⚠ " + ah, this.stat, this.d * 129.0f, lineW);
                this.stat.setColor(Color.rgb(225, 230, 240));
                i = i5;
                i2 = 1;
            } else {
                i = 0;
                i2 = 1;
                line(c, "Drive: " + tr3.getNChanges() + " lane change" + (tr3.getNChanges() == 1 ? "" : "s") + (tr3.getNUndone() > 0 ? " (" + tr3.getNUndone() + " undone)" : "") + roadTxt2 + tr3.getNTurns() + " turn" + (tr3.getNTurns() == 1 ? "" : "s") + roadTxt2 + tr3.getNRamps() + " ramp" + (tr3.getNRamps() == 1 ? "" : "s"), this.stat, this.d * 129.0f, lineW);
            }
            float f2 = f;
            float w4 = w;
            i3 = i2;
            line(c, ev, this.dim, this.d * 147.0f, lineW);
            float r = this.d * 22.0f;
            cy = (h - (this.d * 8.0f)) - r;
            this.noR.set((w4 - pad2) - (f2 * r), cy - r, w4 - pad2, cy + r);
            this.okR.set((this.noR.left - (this.d * 12.0f)) - (f2 * r), cy - r, this.noR.left - (this.d * 12.0f), cy + r);
            this.fixR.set((this.okR.left - (this.d * 12.0f)) - (this.d * 64.0f), cy - r, this.okR.left - (this.d * 12.0f), cy + r);
            this.box.setColor(this.pressed != 3 ? -1 : Color.rgb(52, 92, 170));
            c.drawRoundRect(this.fixR, r, r, this.box);
            this.fixText.setColor(this.pressed != 3 ? Color.rgb(52, 92, 170) : -1);
            c.drawText("Fix", this.fixR.centerX(), this.fixR.centerY() + (this.d * 6.0f), this.fixText);
            this.topR.set(0.0f, 0.0f, w4, this.d * 44.0f);
            onDraw$button(this, c, this.okR, Color.rgb(34, 150, 74), "✓", this.pressed != i3 ? i3 : i);
            onDraw$button(this, c, this.noR, Color.rgb(200, 52, 52), "✗", this.pressed != -1 ? i3 : i);
            int tot = this.nRight + this.nWrong;
            now = System.currentTimeMillis();
            scoreW = (this.fixR.left - pad2) - (this.d * 8.0f);
            this.undoR.set(0.0f, cy - r, this.fixR.left - (this.d * 4.0f), cy + r);
            if (this.lastCounted && now - this.lastCheckMs < this.UNDO_MS) {
                i = i3;
            }
            i4 = i;
            if (now - this.flashMs >= 2500 || i4 != 0) {
                this.stat.setColor(!this.flashOk ? Color.rgb(90, 220, 120) : Color.rgb(255, 120, 110));
                c.drawText(TextUtils.ellipsize(this.flash, this.stat, scoreW, TextUtils.TruncateAt.END).toString(), pad2, cy - (this.d * 3.0f), this.stat);
                this.stat.setColor(Color.rgb(225, 230, 240));
                if (i4 == 0) {
                    c.drawText("tap here to undo (" + (((this.UNDO_MS - (now - this.lastCheckMs)) / 1000) + 1) + "s)", pad2, (this.d * 14.0f) + cy, this.dim);
                    return;
                }
                return;
            }
            String txt = tot == 0 ? "is the lane right? tap ✓ or ✗ (stopped / passenger)" : "your checks: " + this.nRight + " of " + tot + " right (" + ((this.nRight * 100) / tot) + "%)";
            c.drawText(TextUtils.ellipsize(txt, tot == 0 ? this.dim : this.stat, scoreW, TextUtils.TruncateAt.END).toString(), pad2, (this.d * 5.0f) + cy, tot == 0 ? this.dim : this.stat);
            return;
        }
        $this$onDraw_u24lambda_u2412.append(str5);
        if (inf != null) {
        }
        if (mSrc.length() <= 0) {
        }
        String roadTxt3 = $this$onDraw_u24lambda_u2412.toString();
        Intrinsics.checkNotNullExpressionValue(roadTxt3, "toString(...)");
        line(c, roadTxt3, this.dim, 91.0f * this.d, lineW);
        last = tr3.getEvents().firstOrNull();
        if (last == null) {
        }
        String ev2 = str2;
        if (tr3.getLastCameraMs() <= 0) {
        }
        String cam2 = str4;
        String hp2 = !Hpms.INSTANCE.files().isEmpty() ? "  ·  no road data (Settings › Lanes)" : "";
        String roadTxt22 = str3;
        pad2 = pad;
        line(c, cam2 + "  ·  lane known " + tr3.knownPct() + "% of " + ((int) (tr3.getDriveS() / 60)) + " min" + hp2, this.small, 109.0f * this.d, lineW);
        function0 = this.ahead;
        if (function0 != null) {
        }
        Pair<String, Boolean> pair2 = TuplesKt.to("", false);
        ah = pair2.component1();
        boolean urgent2 = pair2.component2().booleanValue();
        if (!(ah.length() <= 0)) {
        }
        float f22 = f;
        float w42 = w;
        i3 = i2;
        line(c, ev2, this.dim, this.d * 147.0f, lineW);
        float r2 = this.d * 22.0f;
        cy = (h - (this.d * 8.0f)) - r2;
        this.noR.set((w42 - pad2) - (f22 * r2), cy - r2, w42 - pad2, cy + r2);
        this.okR.set((this.noR.left - (this.d * 12.0f)) - (f22 * r2), cy - r2, this.noR.left - (this.d * 12.0f), cy + r2);
        this.fixR.set((this.okR.left - (this.d * 12.0f)) - (this.d * 64.0f), cy - r2, this.okR.left - (this.d * 12.0f), cy + r2);
        this.box.setColor(this.pressed != 3 ? -1 : Color.rgb(52, 92, 170));
        c.drawRoundRect(this.fixR, r2, r2, this.box);
        this.fixText.setColor(this.pressed != 3 ? Color.rgb(52, 92, 170) : -1);
        c.drawText("Fix", this.fixR.centerX(), this.fixR.centerY() + (this.d * 6.0f), this.fixText);
        this.topR.set(0.0f, 0.0f, w42, this.d * 44.0f);
        onDraw$button(this, c, this.okR, Color.rgb(34, 150, 74), "✓", this.pressed != i3 ? i3 : i);
        onDraw$button(this, c, this.noR, Color.rgb(200, 52, 52), "✗", this.pressed != -1 ? i3 : i);
        int tot2 = this.nRight + this.nWrong;
        now = System.currentTimeMillis();
        scoreW = (this.fixR.left - pad2) - (this.d * 8.0f);
        this.undoR.set(0.0f, cy - r2, this.fixR.left - (this.d * 4.0f), cy + r2);
        if (this.lastCounted) {
            i = i3;
        }
        i4 = i;
        if (now - this.flashMs >= 2500) {
        }
        this.stat.setColor(!this.flashOk ? Color.rgb(90, 220, 120) : Color.rgb(255, 120, 110));
        c.drawText(TextUtils.ellipsize(this.flash, this.stat, scoreW, TextUtils.TruncateAt.END).toString(), pad2, cy - (this.d * 3.0f), this.stat);
        this.stat.setColor(Color.rgb(225, 230, 240));
        if (i4 == 0) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence onDraw$lambda$10(LaneCardView this$0, LaneBrain.Event it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getText() + " " + this$0.ago(it.getAtMs());
    }

    private static final void onDraw$button(LaneCardView this$0, Canvas $c, RectF rect, int col, String label, boolean held) {
        this$0.box.setColor(held ? -1 : col);
        $c.drawOval(rect, this$0.box);
        this$0.btnText.setColor(held ? col : -1);
        $c.drawText(label, rect.centerX(), rect.centerY() + (this$0.d * 8.5f), this$0.btnText);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        int hit;
        Intrinsics.checkNotNullParameter(e, "e");
        if (this.okR.contains(e.getX(), e.getY())) {
            hit = 1;
        } else if (this.noR.contains(e.getX(), e.getY())) {
            hit = -1;
        } else if (this.fixR.contains(e.getX(), e.getY()) || this.topR.contains(e.getX(), e.getY())) {
            hit = 3;
        } else {
            hit = (this.lastCounted && System.currentTimeMillis() - this.lastCheckMs < this.UNDO_MS && this.undoR.contains(e.getX(), e.getY())) ? 2 : 0;
        }
        switch (e.getActionMasked()) {
            case 0:
                if (hit != 0) {
                    this.pressed = hit;
                    invalidate();
                    return true;
                }
                break;
            case 1:
                if (this.pressed != 0) {
                    int p = this.pressed;
                    this.pressed = 0;
                    if (hit == p) {
                        switch (p) {
                            case 2:
                                undo();
                                break;
                            case 3:
                                askCount();
                                break;
                            default:
                                check(p == 1);
                                break;
                        }
                    }
                    invalidate();
                    return true;
                }
                break;
            case 2:
                if (this.pressed != 0) {
                    return true;
                }
                break;
            case 3:
                if (this.pressed != 0) {
                    this.pressed = 0;
                    invalidate();
                    return true;
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    private final void check(boolean ok) {
        String str;
        StringBuilder sb;
        String str2;
        String sb2;
        LaneBrain tr = this.tracker;
        if (tr == null) {
            return;
        }
        Pair<Integer, Double> laneFromLeft = tr.laneFromLeft();
        int n = laneFromLeft.component1().intValue();
        double conf = laneFromLeft.component2().doubleValue();
        Pair<Integer, String> laneCount = tr.laneCount();
        int m = laneCount.component1().intValue();
        String src = laneCount.component2();
        if (n > 0) {
            str = "lane " + n + " of " + (m > 0 ? String.valueOf(m) : "?");
        } else {
            str = "no lane";
        }
        String shown = str;
        if (n > 0) {
            if (ok) {
                this.nRight++;
            } else {
                this.nWrong++;
            }
            this.prefs.edit().putInt("right", this.nRight).putInt("wrong", this.nWrong).apply();
        }
        LaneShots laneShots = this.shots;
        if (laneShots != null) {
            LaneShots.request$default(laneShots, LaneShots.Companion.caption(tr, ok ? "you tapped ✓ (right)" : "you tapped ✗ (wrong)", this.where.invoke().getThird().floatValue()), LaneShots.Why.USER, null, 4, null);
        }
        LaneBrain.check$default(tr, ok, 0L, 2, null);
        this.lastOk = ok;
        this.lastCounted = n > 0;
        this.lastCheckMs = System.currentTimeMillis();
        this.flashOk = ok;
        this.flashMs = System.currentTimeMillis();
        if (n == 0) {
            sb2 = "card showed no lane - not counted";
        } else {
            if (ok) {
                sb = new StringBuilder();
                str2 = "saved: right (";
            } else {
                sb = new StringBuilder();
                str2 = "saved: wrong (";
            }
            sb2 = sb.append(str2).append(shown).append(")").toString();
        }
        this.flash = sb2;
        Triple<Double, Double, Float> invoke = this.where.invoke();
        double lat = invoke.component1().doubleValue();
        double lon = invoke.component2().doubleValue();
        float mph = invoke.component3().floatValue();
        Companion companion = Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%s  %.6f,%.6f  %3.0f mph  CHECK %s: card said %s (%s%s, %d%%) L=%d R=%d  score %d/%d", Arrays.copyOf(new Object[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()), Double.valueOf(lat), Double.valueOf(lon), Float.valueOf(mph), ok ? "RIGHT" : "WRONG", shown, m > 0 ? "count from " : "", src, Integer.valueOf((int) (100 * conf)), Integer.valueOf(tr.lanesLeft()), Integer.valueOf(tr.lanesRight()), Integer.valueOf(this.nRight), Integer.valueOf(this.nRight + this.nWrong)}, 13));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        companion.appendLog(context, format);
        performHapticFeedback(1);
    }

    private final void askCount() {
        LaneFixDialog.Result start;
        String str;
        List<LaneRamps.Ramp> emptyList;
        List<LaneRamps.Ramp> emptyList2;
        String name;
        Integer num;
        final LaneBrain tr = this.tracker;
        if (tr == null) {
            return;
        }
        final int n0 = tr.laneFromLeft().component1().intValue();
        final int m0 = tr.laneCount().component1().intValue();
        final LaneBrain.Road road = tr.getRoad();
        int saved = (road == null || (num = tr.getMyCounts().get(Long.valueOf(road.getId()))) == null) ? 0 : num.intValue();
        String way = road == null ? "" : road.getHighway() ? "divided" : road.getOneway() ? "one" : "two";
        if (m0 > 0) {
            start = new LaneFixDialog.Result(m0, 0, 0, "", 1 <= n0 && n0 <= m0 ? n0 : 0, true, way, 0, null, 384, null);
        } else {
            start = null;
        }
        LaneFixDialog laneFixDialog = LaneFixDialog.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        LaneBrain.Road road2 = tr.getRoad();
        int lanesOurWay = road2 != null ? road2.getLanesOurWay() : 0;
        if (road == null || (name = road.getName()) == null) {
            str = null;
        } else {
            String str2 = name;
            if (str2.length() == 0) {
                str2 = "this street";
            }
            str = str2;
        }
        int i = m0 > 0 ? m0 : 0;
        Function0 function0 = new Function0() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit askCount$lambda$17;
                askCount$lambda$17 = LaneCardView.askCount$lambda$17(LaneBrain.this, this);
                return askCount$lambda$17;
            }
        };
        try {
            Function0<? extends List<LaneRamps.Ramp>> function02 = this.ramps;
            if (function02 == null || (emptyList2 = function02.invoke()) == null) {
                emptyList2 = CollectionsKt.emptyList();
            }
            emptyList = emptyList2;
        } catch (Exception e) {
            emptyList = CollectionsKt.emptyList();
        }
        laneFixDialog.show(context, "Fix lanes", lanesOurWay, str, i, true, (r34 & 64) != 0 ? 0 : saved, (r34 & 128) != 0 ? null : function0, (r34 & 256) != 0 ? null : null, (r34 & 512) != 0 ? null : null, (r34 & 1024) != 0 ? "" : null, (r34 & 2048) != 0 ? CollectionsKt.emptyList() : emptyList, (r34 & 4096) != 0 ? null : null, (r34 & 8192) != 0 ? null : start, new Function1() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit askCount$lambda$18;
                askCount$lambda$18 = LaneCardView.askCount$lambda$18(LaneCardView.this, tr, n0, m0, road, (LaneFixDialog.Result) obj);
                return askCount$lambda$18;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit askCount$lambda$17(LaneBrain $tr, LaneCardView this$0) {
        $tr.setCount(0, 0, true, (r22 & 8) != 0 ? $tr.lastMs : 0L, (r22 & 16) != 0 ? 0 : 0, (r22 & 32) != 0 ? 0 : 0, (r22 & 64) != 0 ? "" : null, (r22 & 128) != 0 ? "" : null);
        this$0.flashOk = true;
        this$0.flashMs = System.currentTimeMillis();
        this$0.flash = "saved count deleted - the map's again";
        this$0.invalidate();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit askCount$lambda$18(LaneCardView this$0, LaneBrain $tr, int $n0, int $m0, LaneBrain.Road $road, LaneFixDialog.Result r) {
        String str;
        Intrinsics.checkNotNullParameter(r, "r");
        LaneShots laneShots = this$0.shots;
        if (laneShots != null) {
            LaneShots.request$default(laneShots, LaneShots.Companion.caption($tr, "before your Fix: you said " + r.text(), this$0.where.invoke().getThird().floatValue()), LaneShots.Why.USER, null, 4, null);
        }
        $tr.setCount(r.getThrough(), r.getLane(), r.getRemember(), (r22 & 8) != 0 ? $tr.lastMs : 0L, (r22 & 16) != 0 ? 0 : r.getExtraL(), (r22 & 32) != 0 ? 0 : r.getExtraR(), (r22 & 64) != 0 ? "" : r.getKind(), (r22 & 128) != 0 ? "" : r.getKindR());
        this$0.flashOk = true;
        this$0.flashMs = System.currentTimeMillis();
        String str2 = "";
        this$0.flash = "saved: " + r.text() + (r.getRemember() ? " (" + r.getThrough() + " remembered)" : "");
        Triple<Double, Double, Float> invoke = this$0.where.invoke();
        double lat = invoke.component1().doubleValue();
        double lon = invoke.component2().doubleValue();
        float mph = invoke.component3().floatValue();
        Companion companion = Companion;
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.US;
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
        Double valueOf = Double.valueOf(lat);
        Double valueOf2 = Double.valueOf(lon);
        Float valueOf3 = Float.valueOf(mph);
        String text = r.text();
        Integer valueOf4 = Integer.valueOf($n0);
        Integer valueOf5 = Integer.valueOf($m0);
        LaneBrain.Road road = $tr.getRoad();
        Integer valueOf6 = Integer.valueOf(road != null ? road.getLanesOurWay() : 0);
        if (r.getRemember()) {
            int through = r.getThrough();
            if ($road == null || (str = $road.getName()) == null) {
                str = "?";
            }
            str2 = " " + through + " remembered for " + str + " (way " + ($road != null ? Long.valueOf($road.getId()) : null) + ")";
        }
        String format2 = String.format(locale, "%s  %.6f,%.6f  %3.0f mph  COUNT you: %s (card said lane %d of %d, map %d)%s", Arrays.copyOf(new Object[]{format, valueOf, valueOf2, valueOf3, text, valueOf4, valueOf5, valueOf6, str2}, 9));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        companion.appendLog(context, format2);
        this$0.invalidate();
        return Unit.INSTANCE;
    }

    private final void undo() {
        if (this.lastCounted) {
            if (this.lastOk) {
                this.nRight = Math.max(0, this.nRight - 1);
            } else {
                this.nWrong = Math.max(0, this.nWrong - 1);
            }
            this.prefs.edit().putInt("right", this.nRight).putInt("wrong", this.nWrong).apply();
            this.lastCounted = false;
            this.flashOk = true;
            this.flashMs = System.currentTimeMillis();
            this.flash = "undone - the " + (this.lastOk ? "✓" : "✗") + " is not counted";
            Companion companion = Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            companion.appendLog(context, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()) + "  CHECK UNDONE (" + (this.lastOk ? "RIGHT" : "WRONG") + ")  score " + this.nRight + "/" + (this.nRight + this.nWrong));
            performHapticFeedback(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void askReset() {
        final LaneBrain tr = this.tracker;
        if (tr == null) {
            return;
        }
        String[] items = {"The lane and this drive's counts (relearns in a few seconds)", "Your check score (" + this.nRight + " of " + (this.nRight + this.nWrong) + " right)", "Both"};
        new AlertDialog.Builder(getContext()).setTitle("Reset").setItems(items, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LaneCardView.askReset$lambda$19(LaneBrain.this, this, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void askReset$lambda$19(LaneBrain $tr, LaneCardView this$0, DialogInterface dialogInterface, int which) {
        String str;
        switch (which) {
            case 0:
            case 2:
                LaneBrain.reset$default($tr, false, 1, null);
                break;
        }
        switch (which) {
            case 1:
            case 2:
                this$0.nRight = 0;
                this$0.nWrong = 0;
                this$0.prefs.edit().putInt("right", 0).putInt("wrong", 0).apply();
                break;
        }
        this$0.lastCounted = false;
        this$0.flashOk = true;
        this$0.flashMs = System.currentTimeMillis();
        switch (which) {
            case 0:
                str = "lane reset - relearning";
                break;
            case 1:
                str = "score reset";
                break;
            default:
                str = "lane and score reset";
                break;
        }
        this$0.flash = str;
        this$0.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showHistory() {
        LaneBrain tr = this.tracker;
        if (tr == null) {
            return;
        }
        final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String head = "This drive: " + tr.summary() + "\nYour checks: " + this.nRight + " of " + (this.nRight + this.nWrong) + " right\n\n";
        final String text = head + (tr.getEvents().isEmpty() ? "Nothing yet." : CollectionsKt.joinToString$default(tr.getEvents(), "\n", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence showHistory$lambda$20;
                showHistory$lambda$20 = LaneCardView.showHistory$lambda$20(fmt, (LaneBrain.Event) obj);
                return showHistory$lambda$20;
            }
        }, 30, null));
        new AlertDialog.Builder(getContext()).setTitle("Lanes (newest first)").setMessage(text).setPositiveButton("Close", (DialogInterface.OnClickListener) null).setNegativeButton("Reset…", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LaneCardView.this.askReset();
            }
        }).setNeutralButton("Copy", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LaneCardView.showHistory$lambda$22(LaneCardView.this, text, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence showHistory$lambda$20(SimpleDateFormat $fmt, LaneBrain.Event it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return $fmt.format(new Date(it.getAtMs())) + "  " + it.getText();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showHistory$lambda$22(LaneCardView this$0, String $text, DialogInterface dialogInterface, int i) {
        Object systemService = this$0.getContext().getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipboardManager cm = (ClipboardManager) systemService;
        cm.setPrimaryClip(ClipData.newPlainText("StarView lane log", $text));
    }

    /* compiled from: LaneCardView.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void appendLog(Context ctx, String line) {
            try {
                File f = new File(ctx.getFilesDir(), "lane_log.txt");
                if (f.length() > 1000000) {
                    FilesKt.writeText$default(f, CollectionsKt.joinToString$default(CollectionsKt.takeLast(FilesKt.readLines$default(f, null, 1, null), 3000), "\n", null, null, 0, null, null, 62, null) + "\n", null, 2, null);
                }
                try {
                    FilesKt.appendText$default(f, line + "\n", null, 2, null);
                } catch (Exception e) {
                }
            } catch (Exception e2) {
            }
        }

        public final void log(Context ctx, LaneBrain tr, LaneBrain.Event e, double lat, double lon, float mph) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(tr, "tr");
            Intrinsics.checkNotNullParameter(e, "e");
            Pair<Integer, Double> laneFromLeft = tr.laneFromLeft();
            int n = laneFromLeft.component1().intValue();
            double conf = laneFromLeft.component2().doubleValue();
            Pair<Integer, String> laneCount = tr.laneCount();
            int m = laneCount.component1().intValue();
            String src = laneCount.component2();
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(e.getAtMs()));
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format2 = String.format(Locale.US, "  %.6f,%.6f  %3.0f mph  ", Arrays.copyOf(new Object[]{Double.valueOf(lat), Double.valueOf(lon), Float.valueOf(mph)}, 3));
            Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
            appendLog(ctx, format + format2 + e.getText() + "  => " + tr.label() + " [lane " + (n > 0 ? Integer.valueOf(n) : "?") + " of " + (m > 0 ? m + " (" + src + ")" : "?") + "], " + ((int) (100 * conf)) + "%  L=" + tr.lanesLeft() + " R=" + tr.lanesRight());
        }
    }
}
