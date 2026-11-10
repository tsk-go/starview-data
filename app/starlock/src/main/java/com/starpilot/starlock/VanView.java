package com.starpilot.starlock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;
import com.google.mlkit.common.MlKitException;
import java.util.Map;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VanView.kt */
public final class VanView extends View {
    private Map<String, Integer> doors;
    private int glow;
    private final Paint p;

    /* VF: from Vineflower (jadx: ) */
    public VanView(Context var1) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.doors = MapsKt.emptyMap();
        this.p = new Paint(1);
    }

    public final Map<String, Integer> getDoors() {
        return this.doors;
    }

    public final void setDoors(Map<String, Integer> v) {
        Intrinsics.checkNotNullParameter(v, "v");
        this.doors = v;
        invalidate();
    }

    public final int getGlow() {
        return this.glow;
    }

    public final void setGlow(int v) {
        this.glow = v;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        Intrinsics.checkNotNullParameter(c, "c");
        float w = getWidth();
        float h = getHeight();
        if (Color.alpha(this.glow) > 0) {
            float f = 2;
            this.p.setShader(new RadialGradient(w / f, h / f, Math.min(w, h) * 0.55f, new int[]{Color.argb(110, Color.red(this.glow), Color.green(this.glow), Color.blue(this.glow)), 0}, (float[]) null, Shader.TileMode.CLAMP));
            c.drawRect(0.0f, 0.0f, w, h, this.p);
            this.p.setShader(null);
        }
        float s = Math.min(w / 230.0f, h / 350.0f);
        c.save();
        float f2 = 2;
        c.translate((w - (190 * s)) / f2, (h - (330 * s)) / f2);
        c.scale(s, s);
        onDraw$rr(this, c, 8.0f, 88.0f, 24.0f, 98.0f, 4.0f, Color.rgb(154, 163, 175));
        onDraw$rr(this, c, 166.0f, 88.0f, 182.0f, 98.0f, 4.0f, Color.rgb(154, 163, 175));
        this.p.setShader(new LinearGradient(0.0f, 14.0f, 0.0f, 318.0f, Color.rgb(233, 237, 242), Color.rgb(174, 182, 193), Shader.TileMode.CLAMP));
        c.drawRoundRect(new RectF(22.0f, 14.0f, 168.0f, 318.0f), 52.0f, 52.0f, this.p);
        this.p.setShader(null);
        this.p.setColor(Color.rgb(207, 213, 221));
        Path $this$onDraw_u24lambda_u240 = new Path();
        $this$onDraw_u24lambda_u240.moveTo(40.0f, 30.0f);
        $this$onDraw_u24lambda_u240.quadTo(95.0f, 8.0f, 150.0f, 30.0f);
        $this$onDraw_u24lambda_u240.lineTo(150.0f, 70.0f);
        $this$onDraw_u24lambda_u240.quadTo(95.0f, 60.0f, 40.0f, 70.0f);
        $this$onDraw_u24lambda_u240.close();
        c.drawPath($this$onDraw_u24lambda_u240, this.p);
        this.p.setColor(Color.rgb(32, 39, 51));
        Path $this$onDraw_u24lambda_u241 = new Path();
        $this$onDraw_u24lambda_u241.moveTo(38.0f, 78.0f);
        $this$onDraw_u24lambda_u241.quadTo(95.0f, 64.0f, 152.0f, 78.0f);
        $this$onDraw_u24lambda_u241.lineTo(146.0f, 112.0f);
        $this$onDraw_u24lambda_u241.quadTo(95.0f, 104.0f, 44.0f, 112.0f);
        $this$onDraw_u24lambda_u241.close();
        c.drawPath($this$onDraw_u24lambda_u241, this.p);
        onDraw$rr(this, c, 46.0f, 116.0f, 144.0f, 266.0f, 14.0f, Color.rgb(195, 202, 211));
        onDraw$rr(this, c, 62.0f, 150.0f, 128.0f, 220.0f, 8.0f, Color.rgb(112, 120, 132));
        this.p.setColor(Color.rgb(32, 39, 51));
        Path $this$onDraw_u24lambda_u242 = new Path();
        $this$onDraw_u24lambda_u242.moveTo(48.0f, 272.0f);
        $this$onDraw_u24lambda_u242.quadTo(95.0f, 280.0f, 142.0f, 272.0f);
        $this$onDraw_u24lambda_u242.lineTo(146.0f, 296.0f);
        $this$onDraw_u24lambda_u242.quadTo(95.0f, 306.0f, 44.0f, 296.0f);
        $this$onDraw_u24lambda_u242.close();
        c.drawPath($this$onDraw_u24lambda_u242, this.p);
        int grey = Color.rgb(90, 98, 110);
        Integer num = this.doors.get("driver");
        onDraw$rr(this, c, 20.0f, 96.0f, 29.0f, 158.0f, 4.0f, num != null ? num.intValue() : grey);
        Integer num2 = this.doors.get("passenger");
        onDraw$rr(this, c, 161.0f, 96.0f, 170.0f, 158.0f, 4.0f, num2 != null ? num2.intValue() : grey);
        Integer num3 = this.doors.get("slide_l");
        onDraw$rr(this, c, 20.0f, 168.0f, 29.0f, 254.0f, 4.0f, num3 != null ? num3.intValue() : grey);
        Integer num4 = this.doors.get("slide_r");
        onDraw$rr(this, c, 161.0f, 168.0f, 170.0f, 254.0f, 4.0f, num4 != null ? num4.intValue() : grey);
        Integer num5 = this.doors.get("back");
        onDraw$rr(this, c, 56.0f, 309.0f, 134.0f, 318.0f, 4.0f, num5 != null ? num5.intValue() : grey);
        c.restore();
    }

    private static final void onDraw$rr(VanView this$0, Canvas $c, float l, float t, float r, float b, float rad, int color) {
        this$0.p.setColor(color);
        $c.drawRoundRect(new RectF(l, t, r, b), rad, rad, this$0.p);
    }
}
