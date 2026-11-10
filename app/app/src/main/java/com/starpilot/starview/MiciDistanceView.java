package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import android.graphics.PorterDuff.Mode;

/* compiled from: BigUiViews.kt */
public final class MiciDistanceView extends View {
    private float alphaF;
    private final float[] barGreen;
    private final float[] barOn;
    private final List<Pair<Pair<Float, Float>, Pair<Float, Float>>> barSizes;
    private final float[] barY;
    private final List<Pair<Bitmap, Bitmap>> bars;
    private final Bitmap car;
    private final Bitmap carGreen;
    private float carGreenF;
    private final Pair<Pair<Float, Float>, Pair<Float, Float>> carSize;
    private float carWhite;
    private double changedAtS;
    private boolean isDown;
    private long lastNs;
    private int lastPersonality;
    private final Paint p;
    private final PorterDuffColorFilter red;
    public Telemetry t;

    /* VF: from Vineflower (jadx: ) */
    public MiciDistanceView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.car = this.bmp(R.drawable.mici_car);
        this.carGreen = this.bmp(R.drawable.mici_car_green);
        Pair var5 = TuplesKt.to(this.bmp(R.drawable.mici_distance_1), this.bmp(R.drawable.mici_distance_1_green));
        Pair var3 = TuplesKt.to(this.bmp(R.drawable.mici_distance_2), this.bmp(R.drawable.mici_distance_2_green));
        this.bars = CollectionsKt.listOf(new Pair[]{var5, var3, TuplesKt.to(this.bmp(R.drawable.mici_distance_3), this.bmp(R.drawable.mici_distance_3_green))});
        Float var4 = 35.0F;
        this.carSize = TuplesKt.to(TuplesKt.to(var4, 27.0F), TuplesKt.to(50.0F, 42.0F));
        this.barSizes = CollectionsKt.listOf(
            new Pair[]{
                TuplesKt.to(TuplesKt.to(32.0F, 7.0F), TuplesKt.to(60.0F, var4)),
                TuplesKt.to(TuplesKt.to(40.0F, 9.0F), TuplesKt.to(68.0F, 37.0F)),
                TuplesKt.to(TuplesKt.to(48.0F, 11.0F), TuplesKt.to(76.0F, 39.0F))
            }
        );
        this.barY = new float[]{122.0F, 136.0F, 152.0F};
        this.p = new Paint(3);
        this.red = new PorterDuffColorFilter(Color.rgb(200, 32, 48), android.graphics.PorterDuff.Mode.MULTIPLY);
        this.barOn = new float[3];
        this.barGreen = new float[3];
        this.lastPersonality = -1;
        this.changedAtS = -10.0;
    }

    public /* synthetic */ MiciDistanceView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final Telemetry getT() {
        Telemetry telemetry = this.t;
        if (telemetry != null) {
            return telemetry;
        }
        Intrinsics.throwUninitializedPropertyAccessException("t");
        return null;
    }

    public final void setT(Telemetry telemetry) {
        Intrinsics.checkNotNullParameter(telemetry, "<set-?>");
        this.t = telemetry;
    }

    /* recovered: the original JVM name (callers taken from Vineflower use it) */

    public final boolean isDown() {

        return getIsDown();

    }


    /* renamed from: isDown, reason: from getter */
    public final boolean getIsDown() {
        return this.isDown;
    }

    public final void setDown(boolean z) {
        this.isDown = z;
    }

    private final Bitmap bmp(int id) {
        return BitmapFactory.decodeResource(getResources(), id);
    }

    private final int personalityRaw() {
        String personality = getT().getPersonality();
        if (Intrinsics.areEqual(personality, "aggressive")) {
            return 0;
        }
        return Intrinsics.areEqual(personality, "relaxed") ? 2 : 1;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        float f;
        double nowS;
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null) {
            return;
        }
        long now = System.nanoTime();
        float dt = this.lastNs == 0 ? 0.05f : RangesKt.coerceIn((now - this.lastNs) / 1.0E9f, 0.001f, 0.2f);
        this.lastNs = now;
        double nowS2 = now / 1.0E9d;
        boolean engaged = getT().getEnabled();
        this.alphaF = BigUiViewsKt.ease(this.alphaF, (engaged && Intrinsics.areEqual(getT().getAlertSize(), "none")) ? 1.0f : 0.45f, dt, 0.05f);
        float scale = Math.min((getWidth() - 4.0f) / 76.0f, (getHeight() - 4.0f) / 94.0f);
        float cx = getWidth() / 2.0f;
        float cy = getHeight() / 2.0f;
        boolean hasLead = getT().getLeadOne().getStatus();
        boolean overriding = false;
        boolean longActive = getT().getLongActive() || getT().getEnabled();
        boolean engagedLead = hasLead && longActive;
        if (engagedLead) {
            f = 0.0f;
        } else {
            f = hasLead ? 1.0f : 0.35f;
        }
        float whiteTarget = f;
        if (!longActive) {
            this.carWhite = whiteTarget;
            this.carGreenF = 0.0f;
        }
        this.carWhite = BigUiViewsKt.ease(this.carWhite, whiteTarget, dt, 0.1f);
        this.carGreenF = BigUiViewsKt.ease(this.carGreenF, engagedLead ? 1.0f : 0.0f, dt, 0.1f);
        Bitmap car = this.car;
        Intrinsics.checkNotNullExpressionValue(car, "car");
        onDraw$draw$default(scale, cy, this, c, cx, car, this.carSize.getFirst(), 100.0f, this.carWhite * this.alphaF, null, 512, null);
        Bitmap carGreen = this.carGreen;
        Intrinsics.checkNotNullExpressionValue(carGreen, "carGreen");
        onDraw$draw$default(scale, cy, this, c, cx, carGreen, this.carSize.getSecond(), 100.0f, this.carGreenF * this.alphaF, null, 512, null);
        int pers = personalityRaw();
        if (this.lastPersonality < 0 || pers == this.lastPersonality) {
            nowS = nowS2;
        } else {
            nowS = nowS2;
            this.changedAtS = nowS;
        }
        this.lastPersonality = pers;
        boolean highlight = nowS - this.changedAtS < 2.5d;
        boolean traffic = getT().getTrafficMode();
        if (getT().getGasPressed() && Intrinsics.areEqual(getT().getOpState(), "overriding")) {
            overriding = true;
        }
        float blink = (!overriding || nowS % 0.375d <= 0.1875d) ? 1.0f : 0.3888889f;
        int count = traffic ? 1 : pers + 1;
        int i = 0;
        while (i < 3) {
            this.barOn[i] = BigUiViewsKt.ease(this.barOn[i], i < count ? 1.0f : 0.0f, dt, 0.1f);
            this.barGreen[i] = BigUiViewsKt.ease(this.barGreen[i], (highlight && i == count + (-1) && !traffic) ? 1.0f : 0.0f, dt, 0.1f);
            float a = (((1 - this.barOn[i]) * 0.35f) + ((this.barOn[i] - this.barGreen[i]) * 0.9f * blink)) * this.alphaF;
            Bitmap first = this.bars.get(i).getFirst();
            Intrinsics.checkNotNullExpressionValue(first, "<get-first>(...)");
            int pers2 = pers;
            int pers3 = i;
            onDraw$draw(scale, cy, this, c, cx, first, this.barSizes.get(i).getFirst(), this.barY[i], a, (!traffic || i >= count) ? null : this.red);
            Bitmap second = this.bars.get(pers3).getSecond();
            Intrinsics.checkNotNullExpressionValue(second, "<get-second>(...)");
            onDraw$draw$default(scale, cy, this, c, cx, second, this.barSizes.get(pers3).getSecond(), this.barY[pers3], this.barGreen[pers3] * blink * this.alphaF, null, 512, null);
            i = pers3 + 1;
            pers = pers2;
            count = count;
            nowS = nowS;
        }
    }

    static /* synthetic */ void onDraw$draw$default(float f, float f2, MiciDistanceView miciDistanceView, Canvas canvas, float f3, Bitmap bitmap, Pair pair, float f4, float f5, PorterDuffColorFilter porterDuffColorFilter, int i, Object obj) {
        onDraw$draw(f, f2, miciDistanceView, canvas, f3, bitmap, pair, f4, f5, (i & 512) != 0 ? null : porterDuffColorFilter);
    }

    private static final void onDraw$draw(float scale, float cy, MiciDistanceView this$0, Canvas $c, float cx, Bitmap b, Pair<Float, Float> pair, float y, float a, PorterDuffColorFilter filter) {
        if (a <= 0.004f) {
            return;
        }
        float w = pair.getFirst().floatValue() * scale;
        float h = pair.getSecond().floatValue() * scale;
        float yc = ((y - 126.0f) * scale) + cy;
        this$0.p.setAlpha(RangesKt.coerceIn(MathKt.roundToInt(255 * a * (this$0.isDown ? 0.6f : 1.0f)), 0, 255));
        this$0.p.setColorFilter(filter);
        float f = 2;
        $c.drawBitmap(b, (Rect) null, new RectF(cx - (w / f), yc - (h / f), (w / f) + cx, (h / f) + yc), this$0.p);
        this$0.p.setColorFilter(null);
    }
}
