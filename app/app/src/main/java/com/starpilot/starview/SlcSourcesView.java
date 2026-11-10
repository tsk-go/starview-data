package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import org.capnproto.SlcPeek;

/* compiled from: ExtraViews.kt */
public final class SlcSourcesView extends View {
    private final Paint p;
    public Telemetry t;
    private final Paint tp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SlcSourcesView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.p = new Paint(1);
        Paint $this$tp_u24lambda_u240 = new Paint(1);
        $this$tp_u24lambda_u240.setTypeface(Typeface.DEFAULT_BOLD);
        this.tp = $this$tp_u24lambda_u240;
    }

    public /* synthetic */ SlcSourcesView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0074, code lost:
    
        if (r4 != null) goto L25;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onDraw(Canvas c) {
        float rr;
        String active;
        String str;
        SlcPeek.Reading r;
        SlcPeek.Slc st;
        List rows;
        int i;
        int i2;
        float f;
        float dpf;
        float dpf2;
        float dpf3;
        float dpf4;
        float dpf5;
        float dpf6;
        float dpf7;
        float dpf8;
        float dpf9;
        Iterable iterable;
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null || !getT().getStarted()) {
            return;
        }
        if (!(getT().getSp7() ? getT().s("slc_sources_sp7") : getT().pBool("SpeedLimitSources", false))) {
            return;
        }
        float conv = getT().isMetric() ? 3.6f : 2.23694f;
        SlcPeek.Slc slc = getT().getSlc();
        List list = null;
        if (slc != null) {
            SlcPeek.Slc it = System.nanoTime() - getT().getSlcAtNs() < 3000000000L ? 1 : null;
        }
        slc = null;
        SlcPeek.Slc st2 = slc;
        Map labels = MapsKt.mapOf(TuplesKt.to("dashboard", "DASH"), TuplesKt.to("map", "MAP"), TuplesKt.to("vision", "VISION"), TuplesKt.to("online", "ONLINE"));
        if (st2 != null && (iterable = st2.readings) != null) {
            Iterable $this$filter$iv = iterable;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                SlcPeek.Reading it2 = (SlcPeek.Reading) element$iv$iv;
                if (it2.enabled) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            list = (List) destination$iv$iv;
        }
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        List<SlcPeek.Reading> rows2 = list;
        float w = getWidth();
        float h = getHeight();
        rr = ExtraViewsKt.dpf(this, 12.0f);
        this.p.setStyle(Paint.Style.FILL);
        this.p.setColor(Color.argb(175, 0, 0, 0));
        c.drawRoundRect(new RectF(0.0f, 0.0f, w, h), rr, rr, this.p);
        this.p.setStyle(Paint.Style.STROKE);
        this.p.setStrokeWidth(1.0f);
        this.p.setColor(Color.argb(80, 196, 205, 208));
        c.drawRoundRect(new RectF(0.5f, 0.5f, w - 0.5f, h - 0.5f), rr, rr, this.p);
        if (rows2.isEmpty()) {
            this.tp.setTextAlign(Paint.Align.CENTER);
            Paint paint = this.tp;
            dpf8 = ExtraViewsKt.dpf(this, 14.0f);
            paint.setTextSize(dpf8);
            this.tp.setColor(Color.rgb(166, 166, 166));
            String str2 = st2 == null ? "No speed limit data" : st2.readings.isEmpty() ? "No speed limit" : "No sources enabled";
            float f2 = 2;
            dpf9 = ExtraViewsKt.dpf(this, 5.0f);
            c.drawText(str2, w / f2, (h / f2) + dpf9, this.tp);
            return;
        }
        float rowH = h / rows2.size();
        Intrinsics.checkNotNull(st2);
        String str3 = "valid";
        String active2 = Intrinsics.areEqual(st2.kind, "valid") ? st2.source : st2.acceptedSource;
        int i3 = 0;
        for (SlcPeek.Reading r2 : rows2) {
            int i4 = i3;
            i3++;
            float y = i4 * rowH;
            boolean on = Intrinsics.areEqual(r2.source, active2);
            if (i4 > 0) {
                this.p.setStyle(Paint.Style.FILL);
                this.p.setColor(Color.argb(100, 196, 205, 208));
                dpf6 = ExtraViewsKt.dpf(this, 10.0f);
                dpf7 = ExtraViewsKt.dpf(this, 10.0f);
                active = active2;
                str = str3;
                st = st2;
                r = r2;
                i = 196;
                rows = rows2;
                i2 = 208;
                f = 0.0f;
                c.drawRect(dpf6, y, w - dpf7, y + 1.0f, this.p);
            } else {
                active = active2;
                str = str3;
                r = r2;
                st = st2;
                rows = rows2;
                i = 196;
                i2 = 208;
                f = 0.0f;
            }
            if (on) {
                this.p.setStyle(Paint.Style.FILL);
                this.p.setColor(Color.argb(230, i, 205, i2));
                dpf4 = ExtraViewsKt.dpf(this, 6.0f);
                dpf5 = ExtraViewsKt.dpf(this, 10.0f);
                c.drawRoundRect(new RectF(dpf4, y + (0.25f * rowH), dpf5, y + (0.75f * rowH)), 2.0f, 2.0f, this.p);
            }
            this.tp.setTextAlign(Paint.Align.LEFT);
            Paint paint2 = this.tp;
            dpf = ExtraViewsKt.dpf(this, 20.0f);
            paint2.setTextSize(Math.min(0.36f * rowH, dpf));
            this.tp.setColor(on ? -1 : Color.rgb(166, 166, 166));
            SlcPeek.Reading r3 = r;
            String str4 = (String) labels.get(r3.source);
            if (str4 == null) {
                String source = r3.source;
                Intrinsics.checkNotNullExpressionValue(source, "source");
                str4 = source.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(str4, "toUpperCase(...)");
            }
            dpf2 = ExtraViewsKt.dpf(this, 18.0f);
            c.drawText(str4, dpf2, y + (rowH * 0.64f), this.tp);
            this.tp.setTextAlign(Paint.Align.RIGHT);
            String str5 = str;
            String valueOf = (!Intrinsics.areEqual(r3.kind, str5) || r3.speedMps <= f) ? "–" : String.valueOf(MathKt.roundToInt(r3.speedMps * conv));
            dpf3 = ExtraViewsKt.dpf(this, 14.0f);
            c.drawText(valueOf, w - dpf3, y + (0.64f * rowH), this.tp);
            str3 = str5;
            st2 = st;
            rows2 = rows;
            active2 = active;
        }
    }
}
