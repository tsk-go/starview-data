package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import org.capnproto.SlcPeek;
import org.json.JSONObject;
import android.graphics.Paint.Align;

/* compiled from: BigUiViews.kt */
public final class SlcActionsView extends View {
    private final Paint bg;
    private final Paint line;
    private int pressed;
    private List<? extends Object> prompt;
    private Function1<? super JSONObject, Unit> send;
    public Telemetry t;
    private final Paint tp;

    /* VF: from Vineflower (jadx: ) */
    public SlcActionsView(Context var1, AttributeSet var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        this.pressed = -1;
        Paint var3 = new Paint(1);
        var3.setColor(-1);
        var3.setTextAlign(android.graphics.Paint.Align.CENTER);
        var3.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
        this.tp = var3;
        Paint var4 = new Paint(1);
        var4.setColor(Color.argb(170, 155, 178, 173));
        this.line = var4;
        Paint var5 = new Paint(1);
        var5.setColor(Color.argb(150, 0, 0, 0));
        this.bg = var5;
    }

    public /* synthetic */ SlcActionsView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    /* compiled from: BigUiViews.kt */
    public static final class Control {
        private final long decision;
        private final String kind;
        private final String label;
        private final long presentation;
        private final String session;
        private final float speedMps;

        public Control(String kind, String label, String session, long decision, long presentation, float speedMps) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(session, "session");
            this.kind = kind;
            this.label = label;
            this.session = session;
            this.decision = decision;
            this.presentation = presentation;
            this.speedMps = speedMps;
        }

        public final long getDecision() {
            return this.decision;
        }

        public final String getKind() {
            return this.kind;
        }

        public final String getLabel() {
            return this.label;
        }

        public final long getPresentation() {
            return this.presentation;
        }

        public final String getSession() {
            return this.session;
        }

        public final float getSpeedMps() {
            return this.speedMps;
        }
    }

    public final Function1<JSONObject, Unit> getSend() {
        return this.send;
    }

    public final void setSend(Function1<? super JSONObject, Unit> function1) {
        this.send = function1;
    }

    private final List<Object> pendingContext(SlcPeek.Slc s) {
        if (s == null || !Intrinsics.areEqual(getT().getAlertSize(), "none") || !Intrinsics.areEqual(s.kind, "valid") || !s.enabled || s.autoAdoptionPending) {
            return null;
        }
        float f = s.pendingMps;
        if ((Float.isInfinite(f) || Float.isNaN(f)) ? false : true) {
            String sessionId = s.sessionId;
            Intrinsics.checkNotNullExpressionValue(sessionId, "sessionId");
            if (!(sessionId.length() == 0) && s.decisionId > 0 && s.presentationId > 0) {
                return CollectionsKt.listOf(s.sessionId, Long.valueOf(s.decisionId), Long.valueOf(s.presentationId), Float.valueOf(s.pendingMps));
            }
        }
        return null;
    }

    public final boolean openPrompt() {
        List ctx = pendingContext(Telemetry.slcLive$default(getT(), 0L, 1, null));
        if (ctx == null) {
            return false;
        }
        this.prompt = ctx;
        invalidate();
        return true;
    }

    public static /* synthetic */ List controls$default(SlcActionsView slcActionsView, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = System.nanoTime();
        }
        return slcActionsView.controls(j);
    }

    public final List<Control> controls(long nowNs) {
        boolean raisesSet;
        SlcPeek.Slc s = getT().slcLive(nowNs);
        if (this.prompt != null && !Intrinsics.areEqual(this.prompt, pendingContext(s))) {
            this.prompt = null;
        }
        if (s != null && Intrinsics.areEqual(getT().getAlertSize(), "none") && s.enabled && Intrinsics.areEqual(s.kind, "valid")) {
            String sessionId = s.sessionId;
            Intrinsics.checkNotNullExpressionValue(sessionId, "sessionId");
            if (!(sessionId.length() == 0)) {
                float pending = s.pendingMps;
                if (((Float.isInfinite(pending) || Float.isNaN(pending)) ? false : true) && pending > 0.0f && s.decisionId > 0) {
                    if (s.autoAdoptionPending || this.prompt == null) {
                        return CollectionsKt.emptyList();
                    }
                    String sessionId2 = s.sessionId;
                    Intrinsics.checkNotNullExpressionValue(sessionId2, "sessionId");
                    String sessionId3 = s.sessionId;
                    Intrinsics.checkNotNullExpressionValue(sessionId3, "sessionId");
                    return CollectionsKt.listOf(new Control[]{new Control("accept", "Accept", sessionId2, s.decisionId, s.presentationId, pending), new Control("reject", "Decline", sessionId3, s.decisionId, s.presentationId, pending)});
                }
                if (!((Float.isInfinite(pending) || Float.isNaN(pending)) ? false : true)) {
                    float f = s.speedMps;
                    if (((Float.isInfinite(f) || Float.isNaN(f)) ? false : true) && s.speedMps > 0.0f && s.presentationId > 0) {
                        float k = getT().isMetric() ? 3.6f : 2.2369363f;
                        float f2 = s.adjustedMps;
                        float adjusted = (!(!Float.isInfinite(f2) && !Float.isNaN(f2)) || s.adjustedMps <= 0.0f) ? s.speedMps : s.adjustedMps;
                        float vCruiseCluster = getT().getVCruiseCluster();
                        float setKph = (1.0f > vCruiseCluster ? 1 : (1.0f == vCruiseCluster ? 0 : -1)) <= 0 && (vCruiseCluster > 250.0f ? 1 : (vCruiseCluster == 250.0f ? 0 : -1)) <= 0 ? getT().getVCruiseCluster() : Float.NaN;
                        if (getT().getCruiseEnabled()) {
                            if (((Float.isInfinite(setKph) || Float.isNaN(setKph)) ? false : true) && MathKt.roundToInt((setKph / 3.6f) * k) < MathKt.roundToInt(adjusted * k)) {
                                raisesSet = true;
                                if (!s.hasAccepted && Math.abs(s.acceptedMps - s.speedMps) <= 0.02f && !s.driverOverride && !raisesSet) {
                                    return CollectionsKt.emptyList();
                                }
                                float f3 = s.adjustedMps;
                                String label = ((Float.isInfinite(f3) && !Float.isNaN(f3)) || s.adjustedMps <= 0.0f) ? "USE LIMIT" : "Use limit " + MathKt.roundToInt(s.adjustedMps * k);
                                String sessionId4 = s.sessionId;
                                Intrinsics.checkNotNullExpressionValue(sessionId4, "sessionId");
                                return CollectionsKt.listOf(new Control("adopt", label, sessionId4, s.decisionId, s.presentationId, s.speedMps));
                            }
                        }
                        raisesSet = false;
                        if (!s.hasAccepted) {
                        }
                        float f32 = s.adjustedMps;
                        String label2 = ((Float.isInfinite(f32) && !Float.isNaN(f32)) || s.adjustedMps <= 0.0f) ? "USE LIMIT" : "Use limit " + MathKt.roundToInt(s.adjustedMps * k);
                        String sessionId42 = s.sessionId;
                        Intrinsics.checkNotNullExpressionValue(sessionId42, "sessionId");
                        return CollectionsKt.listOf(new Control("adopt", label2, sessionId42, s.decisionId, s.presentationId, s.speedMps));
                    }
                }
                return CollectionsKt.emptyList();
            }
        }
        return CollectionsKt.emptyList();
    }

    private final List<RectF> boxes(int n) {
        return n == 2 ? CollectionsKt.listOf(new RectF[]{new RectF(0.0f, 0.0f, getWidth() / 2.0f, getHeight()), new RectF(getWidth() / 2.0f, 0.0f, getWidth(), getHeight())}) : CollectionsKt.listOf(new RectF(0.0f, 0.0f, getWidth(), getHeight()));
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        float dpf;
        float dpf2;
        float dpf3;
        float dpf4;
        float dpf5;
        float dpf6;
        Intrinsics.checkNotNullParameter(c, "c");
        if (this.t == null) {
            return;
        }
        List<Control> cs = controls$default(this, 0L, 1, null);
        if (cs.isEmpty()) {
            return;
        }
        float rr = getHeight() / 2.0f;
        c.drawRoundRect(new RectF(0.0f, 0.0f, getWidth(), getHeight()), rr, rr, this.bg);
        this.tp.setTextSize(getHeight() * 0.42f);
        List bx = boxes(cs.size());
        if (cs.size() == 2) {
            Paint paint = this.line;
            dpf6 = BigUiViewsKt.dpf(this, 1.5f);
            paint.setStrokeWidth(dpf6);
            c.drawLine(getWidth() / 2.0f, 0.22f * getHeight(), getWidth() / 2.0f, 0.78f * getHeight(), this.line);
        }
        int i = 0;
        for (Control ctl : cs) {
            int i2 = i;
            int i3 = i + 1;
            RectF r = ((RectF) bx.get(i2));
            float y = r.centerY() + (this.tp.getTextSize() * 0.36f);
            Paint paint2 = this.tp;
            dpf = BigUiViewsKt.dpf(this, 2.0f);
            dpf2 = BigUiViewsKt.dpf(this, 1.0f);
            paint2.setShadowLayer(dpf, 0.0f, dpf2, -16777216);
            c.drawText(ctl.getLabel(), r.centerX(), y, this.tp);
            if (this.pressed == i2) {
                float w = this.tp.measureText(ctl.getLabel());
                Paint paint3 = this.tp;
                dpf3 = BigUiViewsKt.dpf(this, 2.5f);
                paint3.setStrokeWidth(dpf3);
                float f = 2;
                float centerX = r.centerX() - (w / f);
                dpf4 = BigUiViewsKt.dpf(this, 6.0f);
                float f2 = y + dpf4;
                float y2 = r.centerX() + (w / f);
                dpf5 = BigUiViewsKt.dpf(this, 6.0f);
                c.drawLine(centerX, f2, y2, y + dpf5, this.tp);
            }
            i = i3;
        }
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public boolean onTouchEvent(MotionEvent var1) {
        Intrinsics.checkNotNullParameter(var1, "e");
        if (this.t == null) {
            return false;
        } else {
            List var3 = controls$default(this, 0L, 1, null);
            if (var3.isEmpty()) {
                return false;
            } else {
                List var4 = this.boxes(var3.size());
                int var2 = 0;
                Iterator var7 = var4.iterator();

                while (true) {
                    if (!var7.hasNext()) {
                        var2 = -1;
                        break;
                    }

                    if (((RectF)var7.next()).contains(var1.getX(), var1.getY())) {
                        break;
                    }

                    var2++;
                }

                switch (var1.getActionMasked()) {
                    case 0:
                        this.pressed = var2;
                        this.invalidate();
                        break;
                    case 1:
                        if (var2 >= 0 && var2 == this.pressed) {
                            SlcActionsView.Control var8 = (SlcActionsView.Control)var3.get(var2);
                            Function1 var6 = this.send;
                            if (var6 != null) {
                                JSONObject var5 = new JSONObject()
                                    .put("action", "slc_action")
                                    .put("kind", var8.getKind())
                                    .put("session", var8.getSession())
                                    .put("decision", var8.getDecision())
                                    .put("presentation", var8.getPresentation())
                                    .put("speed", var8.getSpeedMps());
                                Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
                                var6.invoke(var5);
                            }

                            if (!Intrinsics.areEqual(var8.getKind(), "adopt")) {
                                this.prompt = null;
                            }
                        }

                        this.pressed = -1;
                        this.invalidate();
                    case 2:
                    default:
                        break;
                    case 3:
                        this.pressed = -1;
                        this.invalidate();
                }

                return true;
            }
        }
    }
}
