package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: ExtraViews.kt */
public final class NavCardView extends View {
    private boolean collapsed;
    private final Paint p;
    public Telemetry t;
    private final Paint tp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NavCardView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.p = new Paint(3);
        Paint $this$tp_u24lambda_u240 = new Paint(1);
        $this$tp_u24lambda_u240.setColor(-1);
        $this$tp_u24lambda_u240.setTypeface(Typeface.DEFAULT_BOLD);
        this.tp = $this$tp_u24lambda_u240;
    }

    public /* synthetic */ NavCardView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    public final boolean getCollapsed() {
        return this.collapsed;
    }

    public final void setCollapsed(boolean z) {
        this.collapsed = z;
    }

    private final Bitmap icon(String type, String modifier) {
        NavIcons navIcons = NavIcons.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return navIcons.icon(context, type, modifier);
    }

    private final String fmtDist(float m) {
        if (getT().isMetric()) {
            if (m < 1000.0f) {
                int step = m < 500.0f ? 25 : 50;
                return (MathKt.roundToInt(m / step) * step) + " m";
            }
            float km = m / 1000.0f;
            if (km >= 10.0f) {
                return ((int) Math.floor(km)) + " km";
            }
            String format = String.format("%.1f km", Arrays.copyOf(new Object[]{Float.valueOf(km)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            return format;
        }
        float ft = 3.28084f * m;
        if (ft < 1000.0f) {
            int step2 = ft <= 100.0f ? 10 : 50;
            return (MathKt.roundToInt(ft / step2) * step2) + " ft";
        }
        float mi = ft / 5280.0f;
        if (mi >= 10.0f) {
            return ((int) Math.floor(mi)) + " mi";
        }
        String format2 = String.format("%.1f mi", Arrays.copyOf(new Object[]{Float.valueOf(mi)}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        return format2;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        Pair navCard$default;
        JSONObject nav;
        float rr;
        float pad;
        float dpf;
        float dpf2;
        float dpf3;
        float size;
        float size2;
        float dpf4;
        float dpf5;
        float dpf6;
        float dpf7;
        float dpf8;
        float dpf9;
        float dpf10;
        float dpf11;
        float dpf12;
        float dpf13;
        float dpf14;
        float dpf15;
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null || (navCard$default = Telemetry.navCard$default(getT(), 0L, 1, null)) == null || (nav = (JSONObject) navCard$default.getFirst()) == null) {
            return;
        }
        this.collapsed = getT().mBool("NavInstructionCollapsed", false);
        float w = getWidth();
        float h = getHeight();
        rr = ExtraViewsKt.dpf(this, 16.0f);
        this.p.setStyle(Paint.Style.FILL);
        this.p.setColor(Color.argb(192, 0, 0, 0));
        String optString = nav.optString("maneuverType");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        String optString2 = nav.optString("maneuverModifier");
        Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
        Bitmap ic = icon(optString, optString2);
        if (this.collapsed) {
            float s = Math.min(w, h);
            c.drawRoundRect(new RectF(w - s, 0.0f, w, s), rr, rr, this.p);
            if (ic != null) {
                c.drawBitmap(ic, (Rect) null, new RectF((w - s) + (s * 0.15f), s * 0.15f, w - (0.15f * s), 0.85f * s), this.p);
                return;
            }
            return;
        }
        c.drawRoundRect(new RectF(0.0f, 0.0f, w, h), rr, rr, this.p);
        pad = ExtraViewsKt.dpf(this, 12.0f);
        float iconS = h * 0.5f;
        if (ic != null) {
            c.drawBitmap(ic, (Rect) null, new RectF(pad, pad, pad + iconS, pad + iconS), this.p);
        }
        this.tp.setTextAlign(Paint.Align.LEFT);
        Paint paint = this.tp;
        dpf = ExtraViewsKt.dpf(this, 16.0f);
        paint.setTextSize(dpf);
        this.tp.setTypeface(Typeface.DEFAULT_BOLD);
        String fmtDist = fmtDist((float) nav.optDouble("maneuverDistance", 0.0d));
        dpf2 = ExtraViewsKt.dpf(this, 20.0f);
        c.drawText(fmtDist, pad, pad + iconS + dpf2, this.tp);
        dpf3 = ExtraViewsKt.dpf(this, 14.0f);
        float tx = pad + iconS + dpf3;
        float maxW = (w - tx) - pad;
        String primary = nav.optString("maneuverPrimaryText");
        size = ExtraViewsKt.dpf(this, 22.0f);
        this.tp.setTextSize(size);
        while (true) {
            size2 = size;
            if (this.tp.measureText(primary) <= 2 * maxW) {
                break;
            }
            dpf14 = ExtraViewsKt.dpf(this, 15.0f);
            if (size2 <= dpf14) {
                break;
            }
            dpf15 = ExtraViewsKt.dpf(this, 1.0f);
            size = size2 - dpf15;
            this.tp.setTextSize(size);
        }
        Intrinsics.checkNotNull(primary);
        List lines = wrap(primary, maxW, 2);
        float y = pad + size2;
        float y2 = y;
        for (String l : lines) {
            c.drawText(l, tx, y2, this.tp);
            y2 += 1.1f * size2;
        }
        String secondary = nav.optString("maneuverSecondaryText");
        Intrinsics.checkNotNull(secondary);
        if (secondary.length() > 0) {
            this.tp.setTypeface(Typeface.DEFAULT);
            Paint paint2 = this.tp;
            dpf12 = ExtraViewsKt.dpf(this, 13.0f);
            paint2.setTextSize(dpf12);
            this.tp.setColor(Color.argb(180, 255, 255, 255));
            String str = (String) CollectionsKt.firstOrNull((List) wrap(secondary, maxW, 1));
            if (str == null) {
                str = "";
            }
            dpf13 = ExtraViewsKt.dpf(this, 2.0f);
            c.drawText(str, tx, dpf13 + y2, this.tp);
            this.tp.setColor(-1);
        }
        String nextType = nav.optString("nextManeuverType");
        String nextMod = nav.optString("nextManeuverModifier");
        Intrinsics.checkNotNull(nextType);
        if (!(nextType.length() > 0)) {
            Intrinsics.checkNotNull(nextMod);
            if (!(nextMod.length() > 0)) {
                return;
            }
        }
        this.p.setColor(Color.argb(50, 255, 255, 255));
        dpf4 = ExtraViewsKt.dpf(this, 34.0f);
        dpf5 = ExtraViewsKt.dpf(this, 33.0f);
        c.drawRect(tx, h - dpf4, w - pad, h - dpf5, this.p);
        this.tp.setTypeface(Typeface.DEFAULT);
        Paint paint3 = this.tp;
        dpf6 = ExtraViewsKt.dpf(this, 13.0f);
        paint3.setTextSize(dpf6);
        dpf7 = ExtraViewsKt.dpf(this, 12.0f);
        c.drawText("Then", tx, h - dpf7, this.tp);
        Intrinsics.checkNotNull(nextMod);
        Bitmap it = icon(nextType, nextMod);
        if (it != null) {
            dpf8 = ExtraViewsKt.dpf(this, 44.0f);
            dpf9 = ExtraViewsKt.dpf(this, 30.0f);
            dpf10 = ExtraViewsKt.dpf(this, 66.0f);
            dpf11 = ExtraViewsKt.dpf(this, 8.0f);
            c.drawBitmap(it, (Rect) null, new RectF(dpf8 + tx, h - dpf9, dpf10 + tx, h - dpf11), this.p);
        }
    }

    private final List<String> wrap(String text, float maxW, int maxLines) {
        List<String> words = StringsKt.split$default((CharSequence) text, new String[]{" "}, false, 0, 6, (Object) null);
        ArrayList out = new ArrayList();
        String cur = "";
        for (String wd : words) {
            String cand = cur.length() == 0 ? wd : cur + " " + wd;
            if (this.tp.measureText(cand) > maxW) {
                if (!(cur.length() == 0)) {
                    out.add(cur);
                    cur = wd;
                    if (out.size() == maxLines) {
                        break;
                    }
                }
            }
            cur = cand;
        }
        if (out.size() < maxLines) {
            if (cur.length() > 0) {
                out.add(cur);
            }
        }
        if (out.size() == maxLines && !Intrinsics.areEqual(text, CollectionsKt.joinToString$default(out, " ", null, null, 0, null, null, 62, null))) {
            Object obj = out.get(maxLines - 1);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            out.set(maxLines - 1, StringsKt.trimEnd((CharSequence) obj).toString() + "…");
        }
        return out;
    }
}
