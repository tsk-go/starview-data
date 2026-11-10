package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.starpilot.starview.SlowReason;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

/* compiled from: SlowReasonView.kt */
public final class SlowReasonView extends View {
    public static final Companion Companion = new Companion(null);
    private final Paint acc;
    private final Paint bar;
    private final Paint bg;
    private final float d;
    private final Paint sub;
    public Telemetry t;
    private final Paint title;
    private final TextPaint tp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SlowReasonView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.d = getResources().getDisplayMetrics().density;
        Paint $this$bg_u24lambda_u240 = new Paint(1);
        $this$bg_u24lambda_u240.setColor(Color.argb(200, 0, 0, 0));
        this.bg = $this$bg_u24lambda_u240;
        this.bar = new Paint(1);
        Paint $this$title_u24lambda_u241 = new Paint(1);
        $this$title_u24lambda_u241.setColor(-1);
        $this$title_u24lambda_u241.setTypeface(Typeface.DEFAULT_BOLD);
        $this$title_u24lambda_u241.setTextSize(this.d * 21.0f);
        this.title = $this$title_u24lambda_u241;
        Paint $this$sub_u24lambda_u242 = new Paint(1);
        $this$sub_u24lambda_u242.setColor(Color.rgb(200, 208, 222));
        $this$sub_u24lambda_u242.setTextSize(this.d * 15.0f);
        this.sub = $this$sub_u24lambda_u242;
        Paint $this$acc_u24lambda_u243 = new Paint(1);
        $this$acc_u24lambda_u243.setColor(-1);
        $this$acc_u24lambda_u243.setTypeface(Typeface.DEFAULT_BOLD);
        $this$acc_u24lambda_u243.setTextSize(this.d * 17.0f);
        $this$acc_u24lambda_u243.setTextAlign(Paint.Align.RIGHT);
        this.acc = $this$acc_u24lambda_u243;
        this.tp = new TextPaint(1);
        setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SlowReasonView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SlowReasonView.this.showHistory();
            }
        });
    }

    public /* synthetic */ SlowReasonView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    public final SlowReason.Out current() {
        SlowReason.Out shown;
        if (this.t == null) {
            return null;
        }
        synchronized (getT().getSlow()) {
            shown = getT().getSlow().getShown();
        }
        return shown;
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        SlowReason.Out r;
        String next;
        Intrinsics.checkNotNullParameter(c, "c");
        SlowReason.Out r2 = current();
        if (r2 == null) {
            return;
        }
        float w = getWidth();
        float h = getHeight();
        float rr = this.d * 16.0f;
        c.drawRoundRect(new RectF(0.0f, 0.0f, w, h), rr, rr, this.bg);
        this.bar.setColor(Companion.color(r2.getKind()));
        c.drawRoundRect(new RectF(0.0f, 0.0f, this.d * 8.0f, h), this.d * 4.0f, this.d * 4.0f, this.bar);
        float pad = this.d * 20.0f;
        float a = Math.min(getT().getATarget(), getT().getActAccel());
        boolean braking = a <= -0.45f;
        float accW = braking ? this.acc.measureText("−9.9 m/s²") + (this.d * 12.0f) : 0.0f;
        if (braking) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(Locale.US, "%.1f m/s²", Arrays.copyOf(new Object[]{Float.valueOf(a)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            c.drawText(StringsKt.replace$default(format, "-", "−", false, 4, (Object) null), w - (this.d * 14.0f), this.d * 32.0f, this.acc);
        }
        this.tp.set(this.title);
        c.drawText(TextUtils.ellipsize(r2.getTitle(), this.tp, ((w - pad) - accW) - (this.d * 14.0f), TextUtils.TruncateAt.END).toString(), pad, this.d * 32.0f, this.title);
        this.tp.set(this.sub);
        List<String> words = StringsKt.split$default((CharSequence) r2.getDetail(), new String[]{" "}, false, 0, 6, (Object) null);
        String line = "";
        float y = this.d * 58.0f;
        int lines = 0;
        for (String wd : words) {
            if (line.length() == 0) {
                r = r2;
                next = wd;
            } else {
                r = r2;
                next = line + " " + wd;
            }
            float rr2 = rr;
            float accW2 = accW;
            if (this.sub.measureText(next) > (w - pad) - (this.d * 14.0f)) {
                if (line.length() > 0) {
                    c.drawText(line, pad, y, this.sub);
                    y += this.d * 20.0f;
                    lines++;
                    line = wd;
                    if (lines == 1 && y > h - (this.d * 6.0f)) {
                        return;
                    }
                    r2 = r;
                    rr = rr2;
                    accW = accW2;
                }
            }
            line = next;
            r2 = r;
            rr = rr2;
            accW = accW2;
        }
        if (!(line.length() > 0) || y >= h) {
            return;
        }
        c.drawText(TextUtils.ellipsize(line, this.tp, (w - pad) - (this.d * 14.0f), TextUtils.TruncateAt.END).toString(), pad, y, this.sub);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showHistory() {
        List list;
        if (this.t == null) {
            return;
        }
        synchronized (getT().getSlow()) {
            list = CollectionsKt.toList(getT().getSlow().getHistory());
        }
        final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss", Locale.US);
        final String text = list.isEmpty() ? "No braking by openpilot since StarView started." : CollectionsKt.joinToString$default(list, "\n\n", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.SlowReasonView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence showHistory$lambda$7;
                showHistory$lambda$7 = SlowReasonView.showHistory$lambda$7(fmt, (SlowReason.Event) obj);
                return showHistory$lambda$7;
            }
        }, 30, null);
        new AlertDialog.Builder(getContext()).setTitle("Why it slowed (newest first)").setMessage(text).setPositiveButton("Close", (DialogInterface.OnClickListener) null).setNeutralButton("Copy", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SlowReasonView$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SlowReasonView.showHistory$lambda$8(SlowReasonView.this, text, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence showHistory$lambda$7(SimpleDateFormat $fmt, SlowReason.Event e) {
        Intrinsics.checkNotNullParameter(e, "e");
        return Companion.line$default(Companion, e, $fmt, false, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showHistory$lambda$8(SlowReasonView this$0, String $text, DialogInterface dialogInterface, int i) {
        Object systemService = this$0.getContext().getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipboardManager cm = (ClipboardManager) systemService;
        cm.setPrimaryClip(ClipData.newPlainText("StarView braking log", $text));
    }

    /* compiled from: SlowReasonView.kt */
    public static final class Companion {

        /* compiled from: SlowReasonView.kt */
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[SlowReason.Kind.values().length];
                try {
                    iArr[SlowReason.Kind.FCW.ordinal()] = 1;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[SlowReason.Kind.LEAD.ordinal()] = 2;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[SlowReason.Kind.MODEL.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[SlowReason.Kind.STOP.ordinal()] = 4;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[SlowReason.Kind.CURVE.ordinal()] = 5;
                } catch (NoSuchFieldError e5) {
                }
                try {
                    iArr[SlowReason.Kind.LIMIT.ordinal()] = 6;
                } catch (NoSuchFieldError e6) {
                }
                try {
                    iArr[SlowReason.Kind.TARGET.ordinal()] = 7;
                } catch (NoSuchFieldError e7) {
                }
                try {
                    iArr[SlowReason.Kind.COAST.ordinal()] = 8;
                } catch (NoSuchFieldError e8) {
                }
                try {
                    iArr[SlowReason.Kind.OTHER.ordinal()] = 9;
                } catch (NoSuchFieldError e9) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int color(SlowReason.Kind k) {
            Intrinsics.checkNotNullParameter(k, "k");
            switch (WhenMappings.$EnumSwitchMapping$0[k.ordinal()]) {
                case 1:
                    return Color.rgb(230, 40, 50);
                case 2:
                    return Color.rgb(255, 160, 40);
                case 3:
                    return Color.rgb(170, 110, 255);
                case 4:
                    return Color.rgb(230, 70, 70);
                case 5:
                    return Color.rgb(60, 160, 255);
                case 6:
                    return Color.rgb(240, 240, 240);
                case 7:
                case 8:
                    return Color.rgb(90, 200, 140);
                case 9:
                    return Color.rgb(150, 160, 180);
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        public static /* synthetic */ String line$default(Companion companion, SlowReason.Event event, SimpleDateFormat simpleDateFormat, boolean z, int i, Object obj) {
            if ((i & 4) != 0) {
                z = false;
            }
            return companion.line(event, simpleDateFormat, z);
        }

        public final String line(SlowReason.Event e, SimpleDateFormat fmt, boolean metric) {
            Intrinsics.checkNotNullParameter(e, "e");
            Intrinsics.checkNotNullParameter(fmt, "fmt");
            float f = metric ? 3.6f : 2.23694f;
            String u = metric ? "km/h" : "mph";
            String format = fmt.format(new Date(e.getStartMs()));
            int vStart = (int) (e.getVStart() * f);
            int vEnd = (int) (e.getVEnd() * f);
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format2 = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{Float.valueOf(e.getMinA())}, 1));
            Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
            return format + "  " + vStart + "→" + vEnd + " " + u + ", hardest " + format2 + " m/s², " + (((e.getEndMs() - e.getStartMs()) / 100) / 10.0d) + " s\n" + e.getReason().getTitle() + " - " + e.getReason().getDetail();
        }

        public final void log(Context ctx, SlowReason.Event e) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(e, "e");
            try {
                File f = new File(ctx.getFilesDir(), "braking_log.txt");
                if (f.length() > 1000000) {
                    FilesKt.writeText$default(f, CollectionsKt.joinToString$default(CollectionsKt.takeLast(FilesKt.readLines$default(f, null, 1, null), 2000), "\n", null, null, 0, null, null, 62, null) + "\n", null, 2, null);
                }
                FilesKt.appendText$default(f, new SimpleDateFormat("yyyy-MM-dd ", Locale.US).format(new Date(e.getStartMs())) + StringsKt.replace$default(line$default(this, e, new SimpleDateFormat("HH:mm:ss", Locale.US), false, 4, null), "\n", " | ", false, 4, (Object) null) + "\n", null, 2, null);
            } catch (Exception e2) {
            }
        }
    }
}
