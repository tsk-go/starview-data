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
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.text.TextUtils.TruncateAt;

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

    /* VF: from Vineflower (jadx: ) */
    public LaneCardView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.where = new LaneCardView$$ExternalSyntheticLambda1();
        this.d = this.getResources().getDisplayMetrics().density;
        Paint var4 = new Paint(1);
        var4.setColor(Color.argb(200, 0, 0, 0));
        this.bg = var4;
        this.box = new Paint(1);
        TextPaint var5 = new TextPaint(1);
        var5.setColor(-1);
        var5.setTypeface(Typeface.DEFAULT_BOLD);
        var5.setTextSize(this.d * 24.0F);
        this.big = var5;
        TextPaint var6 = new TextPaint(1);
        var6.setColor(Color.rgb(200, 208, 222));
        var6.setTextSize(this.d * 13.5F);
        this.small = var6;
        TextPaint var7 = new TextPaint(1);
        var7.setColor(Color.rgb(150, 160, 178));
        var7.setTextSize(this.d * 12.5F);
        this.dim = var7;
        TextPaint var8 = new TextPaint(1);
        var8.setColor(Color.rgb(225, 230, 240));
        var8.setTextSize(this.d * 13.0F);
        var8.setTypeface(Typeface.DEFAULT_BOLD);
        this.stat = var8;
        TextPaint var9 = new TextPaint(1);
        var9.setColor(-1);
        var9.setTypeface(Typeface.DEFAULT_BOLD);
        var9.setTextSize(this.d * 24.0F);
        var9.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.btnText = var9;
        this.prefs = var1.getSharedPreferences("lane_card", 0);
        this.nRight = this.prefs.getInt("right", 0);
        this.nWrong = this.prefs.getInt("wrong", 0);
        this.flash = "";
        this.flashOk = true;
        this.okR = new RectF();
        this.noR = new RectF();
        this.undoR = new RectF();
        this.fixR = new RectF();
        this.topR = new RectF();
        TextPaint var3 = new TextPaint(1);
        var3.setColor(-1);
        var3.setTypeface(Typeface.DEFAULT_BOLD);
        var3.setTextSize(this.d * 16.0F);
        var3.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.fixText = var3;
        this.lastOk = true;
        this.UNDO_MS = 8000L;
        this.setOnClickListener(new LaneCardView$$ExternalSyntheticLambda2(this));
        this.setOnLongClickListener(new LaneCardView$$ExternalSyntheticLambda3(this));
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    protected void onDraw(Canvas var1) {
        Object var122 = null; Object var123 = null; Pair var129 = null; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "c");
        LaneBrain var33 = this.tracker;
        if (var33 != null) {
            float var11 = this.getWidth();
            float var12 = this.getHeight();
            float var8 = this.d * 14.0F;
            var1.drawRoundRect(new RectF(0.0F, 0.0F, var11, var12), this.d * 16.0F, this.d * 16.0F, this.bg);
            Object var28 = var33.laneFromLeft();
            int var21 = ((Number)((Pair) var28).component1()).intValue();
            double var2 = ((Number)((Pair) var28).component2()).doubleValue();
            var28 = var33.laneCount();
            int var18 = ((Number)((Pair) var28).component1()).intValue();
            String var29 = (String)((Pair) var28).component2();
            int var19;
            if (var18 > 0) {
                var19 = Math.max(var18, var21);
            } else {
                var19 = 0;
            }

            Paint var90 = this.box;
            if (var21 > 0 && var2 < 0.6) {
                var18 = Color.rgb(130, 138, 150);
            } else {
                var18 = this.confColor(var2);
            }

            var90.setColor(var18);
            var1.drawRoundRect(new RectF(0.0F, 0.0F, this.d * 6.0F, var12), this.d * 3.0F, this.d * 3.0F, this.box);
            String var91 = var33.label();
            LaneBrain.RoadModel var30 = var33.getModel();
            if (var19 > 0) {
                var18 = var19;
            } else if (var21 > 0) {
                var18 = var21 + 1;
            } else {
                var18 = 0;
            }

            int var20;
            if (1 <= var18 && var18 < 9) {
                var20 = 1;
            } else {
                var20 = 0;
            }

            float var10;
            if ((var20 != 0)) {
                var10 = var18 * 18.0F * this.d + (var18 - 1) * 4.0F * this.d + var8;
            } else {
                var10 = 0.0F;
            }

            CharSequence var92 = var91;
            TextPaint var31 = this.big;
            float var9 = 2;
            var1.drawText(TextUtils.ellipsize(var92, var31, var11 - var8 * var9 - var10, TruncateAt.END).toString(), var8, this.d * 32.0F, (Paint)this.big);
            if (1 <= var18 && var18 < 9) {
                var20 = (((boolean)true) ? 1 : 0);
            } else {
                var20 = (((boolean)false) ? 1 : 0);
            }

            if ((var20 != 0)) {
                float var16 = this.d * 18.0F;
                float var14 = this.d * 4.0F;
                float var15 = this.d;
                if (var30.getLanes() > 0 && var19 == var30.getTotal()) {
                    var20 = var30.getExtraL();
                } else {
                    var20 = 0;
                }

                if (var30.getLanes() > 0 && var19 == var30.getTotal()) {
                    var19 = var30.getExtraR();
                } else {
                    var19 = 0;
                }

                var10 = var18;
                float var17 = var18 - 1;
                float var13 = this.d * 12.0F;
                int var22 = 1;
                if (1 <= var18) {
                    var10 = var11 - var8 - var10 * var16 - var17 * var14;
                    int var23 = var18;

                    while (true) {
                        int var50;
                        if (var22 == var21) {
                            var50 = 1;
                        } else {
                            var50 = 0;
                        }

                        boolean var24;
                        if (var22 > var20 && var22 <= var23 - var19) {
                            var24 = false;
                        } else {
                            var24 = true;
                        }

                        Paint var132 = this.box;
                        android.graphics.Paint.Style var93;
                        if ((var50 != 0)) {
                            var93 = android.graphics.Paint.Style.FILL;
                        } else {
                            var93 = android.graphics.Paint.Style.STROKE;
                        }

                        var132.setStyle(var93);
                        Paint var94 = this.box;
                        var94.setStrokeWidth(this.d * 2.0F);
                        Paint var133 = this.box;
                        PathEffect var95;
                        if (var24 && !var50) {
                            var95 = (PathEffect)(new DashPathEffect(new float[]{this.d * 4.0F, this.d * 3.0F}, 0.0F));
                        } else {
                            var95 = null;
                        }

                        var133.setPathEffect(var95);
                        Paint var96 = this.box;
                        if ((var50 != 0)) {
                            if (var2 < 0.6) {
                                var50 = Color.rgb(130, 138, 150);
                            } else {
                                var50 = this.confColor(var2);
                            }
                        } else if (var24) {
                            var50 = Color.rgb(255, 196, 0);
                        } else {
                            var50 = Color.rgb(120, 130, 146);
                        }

                        var96.setColor(var50);
                        var1.drawRoundRect(new RectF(var10, var13, var10 + var16, var13 + var15 * 26.0F), this.d * 4.0F, this.d * 4.0F, this.box);
                        var10 += var16 + var14;
                        if (var22 == var23) {
                            break;
                        }

                        var22++;
                        var23 = var23;
                    }
                }

                this.box.setStyle(android.graphics.Paint.Style.FILL);
                this.box.setPathEffect(null);
            }

            var10 = var11 - var9 * var8;
            var28 = var33.side(-1);
            var20 = ((Number)((Pair) var28).component1()).intValue();
            var2 = ((Number)((Pair) var28).component2()).doubleValue();
            var28 = var33.side(1);
            var18 = ((Number)((Pair) var28).component1()).intValue();
            double var6 = ((Number)((Pair) var28).component2()).doubleValue();
            double var4 = 100;
            var21 = (int)(var2 * var4);
            var19 = (int)(var4 * var6);
            this.line(var1, "lanes left of us: " + var20 + " (" + var21 + "%)  ·  right: " + var18 + " (" + var19 + "%)", this.small, this.d * 56.0F, var10);
            var28 = CollectionsKt.joinToString(CollectionsKt.take(var33.getWhy(), 2), "  ·  ", "", "", -1, "...", new LaneCardView$$ExternalSyntheticLambda0(this));
            int var53;
            if (((String) var28).length() == 0) {
                var53 = 1;
            } else {
                var53 = 0;
            }

            if ((var53 != 0)) {
                var28 = "nothing seen yet";
            }

            this.line(var1, "why: " + var28, this.small, this.d * 74.0F, var10);
            LaneBrain.Road var134 = var33.getRoad();
            LaneBrain.RoadInfo var35 = var33.getInfo();
            StringBuilder var34 = new StringBuilder();
            var34.append("road: ");
            var53 = var30.getLanes();
            String var32 = "s";
            String var101;
            if (var53 > 0) {
                var53 = var30.getLanes();
                String var100;
                if (var30.getLanes() == 1) {
                    var100 = "";
                } else {
                    var100 = "s";
                }

                String var36 = var30.getSrc();
                var101 = var53 + " lane" + var100 + " (" + var36 + ")";
            } else {
                var101 = "count not known";
            }

            var34.append(var101);
            if (var35 != null && var35.getHpmsLanes() > 0 && !Intrinsics.areEqual(var30.getSrc(), "HPMS")) {
                var53 = var35.getHpmsLanes();
                var34.append(" · HPMS " + var53);
            }

            if (var134 != null) {
                var53 = var134.getLanesOurWay();
            } else {
                var53 = 0;
            }

            if (var53 > 0 && !Intrinsics.areEqual(var30.getSrc(), "map")) {
                Intrinsics.checkNotNull(var134);
                var53 = var134.getLanesOurWay();
                var34.append(" · map " + var53);
            }

            label290: {
                String var102;
                if (var30.getTunnel()) {
                    var102 = " · tunnel";
                } else {
                    if (!var30.getBridge()) {
                        break label290;
                    }

                    var102 = " · bridge";
                }

                var34.append(var102);
            }

            if (var35 != null) {
                boolean var59;
                if (var35.getExtraWhy().length() > 0) {
                    var59 = true;
                } else {
                    var59 = false;
                }

                if (var59) {
                    String var103 = var35.getExtraWhy();
                    var34.append(" · " + var103);
                }
            }

            int var60;
            if (var29.length() > 0) {
                var60 = 1;
            } else {
                var60 = 0;
            }

            if ((var60 != 0)) {
                var34.append("  → " + var29);
            }

            String var104 = var34.toString();
            Intrinsics.checkNotNullExpressionValue(var104, "toString(...)");
            this.line(var1, var104, this.dim, 91.0F * this.d, var10);
            LaneBrain.Event var105 = var33.getEvents().firstOrNull();
            String var107;
            if (var105 != null) {
                var29 = var105.getText();
                String var106 = this.ago(var105.getAtMs());
                var107 = var29 + " · " + var106;
            } else {
                label280: {
                    if (var134 != null) {
                        var28 = var134.getName();
                        if (((String) var28).length() == 0) {
                            var60 = (((boolean)true) ? 1 : 0);
                        } else {
                            var60 = (((boolean)false) ? 1 : 0);
                        }

                        if ((var60 != 0)) {
                            var28 = var134.getKind();
                        }

                        if (var134.getOneway()) {
                            var29 = " (one-way)";
                        } else {
                            var29 = "";
                        }

                        var29 = "on " + var28 + var29;
                        var107 = var29;
                        if (var29 != null) {
                            break label280;
                        }
                    }

                    var107 = "waiting for a turn, a ramp or a lane line";
                }
            }

            if (var33.getLastCameraMs() > 0L) {
                var29 = this.ago(var33.getLastCameraMs());
                var29 = "camera confirmed " + var29;
            } else {
                var29 = "camera: nothing yet";
            }

            String var128;
            if (Hpms.INSTANCE.files().isEmpty()) {
                var128 = "  ·  no road data (Settings › Lanes)";
            } else {
                var128 = "";
            }

            var19 = var33.knownPct();
            var60 = (int)(var33.getDriveS() / 60);
            this.line(var1, var29 + "  ·  lane known " + var19 + "% of " + var60 + " min" + var128, this.small, 109.0F * this.d, var10);

            label358: {
                try {
                    var123 = this.ahead;
                } catch (Exception var39) {
                    var122 = TuplesKt.to("", false);
                    break label358;
                }

                if (var123 != null) {
                    try {
                        var129 = (Pair)var123.invoke();
                    } catch (Exception var38) {
                        var122 = TuplesKt.to("", false);
                        break label358;
                    }

                    var122 = var129;
                    if (var129 != null) {
                        break label358;
                    }
                }

                try {
                    var122 = TuplesKt.to("", false);
                } catch (Exception var37) {
                    var122 = TuplesKt.to("", false);
                }
            }

            String var130 = (String)var122.component1();
            boolean var25 = (Boolean)var122.component2();
            int var63;
            if (var130.length() > 0) {
                var63 = 1;
            } else {
                var63 = 0;
            }

            if ((var63 != 0)) {
                TextPaint var124 = this.stat;
                if (var25) {
                    var63 = Color.rgb(255, 90, 80);
                } else {
                    var63 = Color.rgb(255, 196, 0);
                }

                var124.setColor(var63);
                this.line(var1, "⚠ " + var130, this.stat, this.d * 129.0F, var10);
                this.stat.setColor(Color.rgb(225, 230, 240));
            } else {
                var63 = var33.getNChanges();
                if (var33.getNChanges() == 1) {
                    var29 = "";
                } else {
                    var29 = "s";
                }

                String var131;
                if (var33.getNUndone() > 0) {
                    var19 = var33.getNUndone();
                    var131 = " (" + var19 + " undone)";
                } else {
                    var131 = "";
                }

                var19 = var33.getNTurns();
                String var135;
                if (var33.getNTurns() == 1) {
                    var135 = "";
                } else {
                    var135 = "s";
                }

                var20 = var33.getNRamps();
                if (var33.getNRamps() == 1) {
                    var32 = "";
                }

                this.line(
                    var1,
                    "Drive: " + var63 + " lane change" + var29 + var131 + " · " + var19 + " turn" + var135 + " · " + var20 + " ramp" + var32,
                    this.stat,
                    this.d * 129.0F,
                    var10
                );
            }

            int var77 = 0;
            this.line(var1, var107, this.dim, this.d * 147.0F, var10);
            var10 = this.d * 22.0F;
            var12 = var12 - this.d * 8.0F - var10;
            this.noR.set(var11 - var8 - var9 * var10, var12 - var10, var11 - var8, var12 + var10);
            this.okR.set(this.noR.left - this.d * 12.0F - var9 * var10, var12 - var10, this.noR.left - this.d * 12.0F, var12 + var10);
            this.fixR.set(this.okR.left - this.d * 12.0F - this.d * 64.0F, var12 - var10, this.okR.left - this.d * 12.0F, var12 + var10);
            Paint var109 = this.box;
            if (this.pressed == 3) {
                var63 = -1;
            } else {
                var63 = Color.rgb(52, 92, 170);
            }

            var109.setColor(var63);
            var1.drawRoundRect(this.fixR, var10, var10, this.box);
            TextPaint var110 = this.fixText;
            if (this.pressed == 3) {
                var63 = Color.rgb(52, 92, 170);
            } else {
                var63 = -1;
            }

            var110.setColor(var63);
            var1.drawText("Fix", this.fixR.centerX(), this.fixR.centerY() + this.d * 6.0F, (Paint)this.fixText);
            this.topR.set(0.0F, 0.0F, var11, this.d * 44.0F);
            RectF var111 = this.okR;
            var63 = Color.rgb(34, 150, 74);
            if (this.pressed == 1) {
                var25 = true;
            } else {
                var25 = false;
            }

            onDraw$button(this, var1, var111, (((var63 != 0)) ? 1 : 0), "✓", var25);
            RectF var112 = this.noR;
            var63 = Color.rgb(200, 52, 52);
            if (this.pressed == -1) {
                var25 = true;
            } else {
                var25 = false;
            }

            onDraw$button(this, var1, var112, (((var63 != 0)) ? 1 : 0), "✗", var25);
            var20 = this.nRight + this.nWrong;
            long var26 = System.currentTimeMillis();
            var9 = this.fixR.left - var8 - this.d * 8.0F;
            this.undoR.set(0.0F, var12 - var10, this.fixR.left - this.d * 4.0F, var12 + var10);
            int var70 = var77;
            if (this.lastCounted) {
                var70 = var77;
                if (var26 - this.lastCheckMs < this.UNDO_MS) {
                    var70 = 1;
                }
            }

            if (var26 - this.flashMs >= 2500L && !var70) {
                String var114;
                if (!var20) {
                    var114 = "is the lane right? tap ✓ or ✗ (stopped / passenger)";
                } else {
                    var77 = this.nRight;
                    var70 = this.nRight * 100 / var20;
                    var114 = "your checks: " + var77 + " of " + var20 + " right (" + var70 + "%)";
                }

                CharSequence var126 = var114;
                TextPaint var115;
                if (!var20) {
                    var115 = this.dim;
                } else {
                    var115 = this.stat;
                }

                var29 = TextUtils.ellipsize(var126, var115, var9, TruncateAt.END).toString();
                var9 = this.d;
                TextPaint var116;
                if (!var20) {
                    var116 = this.dim;
                } else {
                    var116 = this.stat;
                }

                var1.drawText(var29, var8, var9 * 5.0F + var12, (Paint)var116);
            } else {
                TextPaint var113 = this.stat;
                if (this.flashOk) {
                    var77 = Color.rgb(90, 220, 120);
                } else {
                    var77 = Color.rgb(255, 120, 110);
                }

                var113.setColor(var77);
                var1.drawText(TextUtils.ellipsize(this.flash, this.stat, var9, TruncateAt.END).toString(), var8, var12 - this.d * 3.0F, (Paint)this.stat);
                this.stat.setColor(Color.rgb(225, 230, 240));
                if ((var70 != 0)) {
                    var26 = (this.UNDO_MS - (var26 - this.lastCheckMs)) / 1000;
                    var1.drawText("tap here to undo (" + (var26 + 1L) + "s)", var8, this.d * 14.0F + var12, (Paint)this.dim);
                }
            }
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
                askCount$lambda$17 = LaneCardView.askCount$lambda$17(tr, LaneCardView.this);
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
                LaneCardView.askReset$lambda$19(tr, LaneCardView.this, dialogInterface, i);
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
        final String text = head + (tr.getEvents().isEmpty() ? "Nothing yet." : CollectionsKt.joinToString(tr.getEvents(), "\n", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.LaneCardView$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence showHistory$lambda$20;
                showHistory$lambda$20 = LaneCardView.showHistory$lambda$20(fmt, (LaneBrain.Event) obj);
                return showHistory$lambda$20;
            }
        }));
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
                    FilesKt.writeText(f, CollectionsKt.joinToString(CollectionsKt.takeLast(FilesKt.readLines(f, kotlin.text.Charsets.UTF_8), 3000), "\n", "", "", -1, "...", null) + "\n", kotlin.text.Charsets.UTF_8);
                }
                try {
                    FilesKt.appendText(f, line + "\n", kotlin.text.Charsets.UTF_8);
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
