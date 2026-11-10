package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout.LayoutParams;
import kotlin.Result.Companion;

/* compiled from: GalaxyVasm.kt */
public final class VasmEditorCard extends LinearLayout {
    private String active;
    private final ArrayList<int[]> camLeft;
    private final ArrayList<int[]> camRight;
    private String cameraError;
    private final Board canvas;
    private final TextView clearBtn;
    private boolean editable;
    private int height0;
    private Bitmap image;
    private String lastEditor;
    private final TextView leftBtn;
    private String localNote;
    private final TextView note;
    private final TextView parkedNote;
    private final TextView rightBtn;
    private final Function1<JSONObject, Unit> save;
    private final TextView saveBtn;
    private final TextView snapBtn;
    private boolean snapshotAsked;
    private final TextView status;
    private final CarUi ui;
    private final TextView undoBtn;
    private boolean warming;
    private int width0;

    /* VF: from Vineflower (jadx: ) */
    public VasmEditorCard(Context var1, CarUi var2, Function1<? super JSONObject, Unit> var3) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        Intrinsics.checkNotNullParameter(var3, "save");
        this.ui = var2;
        this.save = (Function1) var3;
        this.width0 = 1928;
        this.height0 = 1208;
        this.camLeft = new ArrayList<>();
        this.camRight = new ArrayList<>();
        this.active = "cameraRight";
        this.lastEditor = "";
        this.cameraError = "";
        this.localNote = "";
        this.status = this.ui.note("");
        this.note = this.ui.note("");
        TextView var9 = this.ui.note("Turn the vehicle off to change these settings.");
        var9.setTextColor(Color.rgb(255, 184, 101));
        this.parkedNote = var9;
        this.leftBtn = CarUi.button$default(this.ui, "Left", false, new VasmEditorCard$$ExternalSyntheticLambda1(this), 2, null);
        this.rightBtn = CarUi.button$default(this.ui, "Right", false, new VasmEditorCard$$ExternalSyntheticLambda2(this), 2, null);
        this.undoBtn = CarUi.button$default(this.ui, "↶ Undo", false, new VasmEditorCard$$ExternalSyntheticLambda3(this), 2, null);
        this.clearBtn = CarUi.button$default(this.ui, "Clear", false, new VasmEditorCard$$ExternalSyntheticLambda4(this), 2, null);
        this.saveBtn = this.ui.button("Save", true, new VasmEditorCard$$ExternalSyntheticLambda5(this));
        this.snapBtn = CarUi.button$default(this.ui, "Take new snapshot", false, new VasmEditorCard$$ExternalSyntheticLambda6(this), 2, null);
        this.canvas = new VasmEditorCard.Board(this, var1);
        this.setOrientation(1);
        this.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 0, null, 6, null));
        this.setPadding(this.ui.dp(18), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
        TextView var10 = new TextView(var1);
        var10.setText("Camera Window Regions");
        var10.setTextColor(CarUi.Companion.getTEXT());
        var10.setTextSize(20.0F);
        var10.setTypeface(Typeface.DEFAULT_BOLD);
        var10.setPadding(this.ui.dp(6), 0, 0, this.ui.dp(6));
        this.addView((View)var10);
        this.addView(
            (View)this.ui
                .note(
                    "The display is mirrored: vehicle left is camera right; vehicle right is camera left. Trace visible side glass, leaving pillars and interior out."
                )
        );
        this.addView((View)this.parkedNote);
        this.addView((View)this.status);
        LinearLayout var11 = new LinearLayout(var1);
        var11.setOrientation(0);

        for (View var13 : CollectionsKt.listOf(new TextView[]{this.leftBtn, this.rightBtn, this.undoBtn, this.clearBtn, this.saveBtn})) {
            android.widget.LinearLayout.LayoutParams var4 = new LayoutParams(-2, -2);
            var4.rightMargin = this.ui.dp(10);
            Unit var5 = Unit.INSTANCE;
            var11.addView(var13, (android.view.ViewGroup.LayoutParams)var4);
        }

        var11.addView(new View(var1), (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, 1, 1.0F)));
        var11.addView((View)this.snapBtn);
        HorizontalScrollView var7 = new HorizontalScrollView(var1);
        var7.setHorizontalScrollBarEnabled(false);
        var7.addView((View)var11);
        View var12 = (View)var7;
        android.widget.LinearLayout.LayoutParams var14 = new LayoutParams(-1, -2);
        var14.bottomMargin = this.ui.dp(10);
        Unit var8 = Unit.INSTANCE;
        this.addView(var12, (android.view.ViewGroup.LayoutParams)var14);
        this.addView(this.canvas, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, -2)));
        this.addView((View)this.note);
        this.addView((View)this.ui.note("A saved choice alone does not activate monitoring."));
        this.paint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit leftBtn$lambda$1(VasmEditorCard this$0) {
        this$0.pick("cameraRight");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit rightBtn$lambda$2(VasmEditorCard this$0) {
        this$0.pick("cameraLeft");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit undoBtn$lambda$4(VasmEditorCard this$0) {
        ArrayList it = this$0.pts();
        if (!it.isEmpty()) {
            it.remove(it.size() - 1);
            this$0.changed();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit clearBtn$lambda$5(VasmEditorCard this$0) {
        this$0.pts().clear();
        this$0.changed();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveBtn$lambda$6(VasmEditorCard this$0) {
        this$0.trySave();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit snapBtn$lambda$7(VasmEditorCard this$0) {
        this$0.snapshot();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ArrayList<int[]> pts() {
        return Intrinsics.areEqual(this.active, "cameraLeft") ? this.camLeft : this.camRight;
    }

    private final void pick(String side) {
        if (drawable()) {
            this.active = side;
            paint();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean drawable() {
        return this.editable && VasmGeometry.INSTANCE.supported(this.width0, this.height0) && this.image != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void changed() {
        this.localNote = "Draft only; review the regions before saving.";
        paint();
    }

    public final void update(JSONObject page, boolean rowAvailable) {
        String sig;
        Intrinsics.checkNotNullParameter(page, "page");
        boolean parked = page.optBoolean("parked", false);
        this.editable = parked && rowAvailable;
        this.parkedNote.setVisibility(parked ? 8 : 0);
        JSONObject ed = page.optJSONObject("editor");
        if (ed == null || (sig = ed.toString()) == null) {
            sig = "null";
        }
        if (!Intrinsics.areEqual(sig, this.lastEditor)) {
            this.lastEditor = sig;
            JSONArray l = ed != null ? ed.optJSONArray("cameraLeft") : null;
            JSONArray r = ed != null ? ed.optJSONArray("cameraRight") : null;
            if (ed == null || l == null || r == null) {
                this.localNote = "Camera regions are unavailable. Restore the saved settings before editing.";
            } else {
                this.width0 = ed.optInt("width", 1928);
                this.height0 = ed.optInt("height", 1208);
                update$read(l, this.camLeft);
                update$read(r, this.camRight);
                this.localNote = VasmGeometry.INSTANCE.supported(this.width0, this.height0) ? "" : "This saved camera size cannot be edited here. Choose a supported size and redraw the regions.";
            }
        }
        if (parked && !this.snapshotAsked) {
            this.snapshotAsked = true;
            snapshot();
        }
        paint();
    }

    private static final void update$read(JSONArray a, List<int[]> list) {
        list.clear();
        int length = a.length();
        for (int i = 0; i < length; i++) {
            JSONArray it = a.optJSONArray(i);
            if (it != null) {
                list.add(new int[]{MathKt.roundToInt(it.optDouble(0)), MathKt.roundToInt(it.optDouble(1))});
            }
        }
    }

    public final void snapshot() {
        Galaxy7 gx = Galaxy7.Companion.current();
        if (gx == null || this.warming) {
            return;
        }
        this.warming = true;
        this.cameraError = "";
        paint();
        JSONObject put = new JSONObject().put("camera", "cabin");
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        gx.postImage("/api/cameras/snapshot", put, new Function2() { // from class: com.starpilot.starview.VasmEditorCard$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit snapshot$lambda$16;
                snapshot$lambda$16 = VasmEditorCard.snapshot$lambda$16(VasmEditorCard.this, (byte[]) obj, (String) obj2);
                return snapshot$lambda$16;
            }
        });
    }

    /* VF: from Vineflower (jadx: ) */
    public static final Unit snapshot$lambda$16(VasmEditorCard var0, byte[] var1, String var2) {
        var0.warming = false;
        Bitmap var3 = null;
        Object var4 = null;
        if (var1 != null) {
            label50:
            try {
                kotlin.Result.Companion var10 = Result.Companion;
                var3 = (Bitmap)KtCompat.resultOf(BitmapFactory.decodeByteArray(var1, 0, var1.length));
            } catch (Throwable var7) {
                kotlin.Result.Companion var5 = Result.Companion;
                var3 = (Bitmap)KtCompat.resultOf(ResultKt.createFailure(var7));
                break label50;
            }

            if (KtCompat.isFailure(var3)) {
                var3 = (Bitmap)var4;
            }

            var3 = var3;
        }

        if (var3 != null) {
            var0.image = var3;
        }

        String var8;
        if (var3 != null) {
            var8 = "";
        } else if (var2 == null) {
            if (var1 != null) {
                var8 = "Camera frame could not be displayed.";
            } else {
                var8 = "No cabin frame was available. Try taking a new snapshot.";
            }
        } else {
            var8 = var2;
        }

        var0.cameraError = var8;
        var0.paint();
        return Unit.INSTANCE;
    }

    private final void trySave() {
        if (drawable()) {
            JSONObject d = VasmGeometry.INSTANCE.draft(this.width0, this.height0, this.camLeft, this.camRight);
            if (d == null) {
                this.localNote = "Draw 3–32 vertices around at least one window. Regions cannot cross or leave the frame.";
                paint();
            } else {
                this.localNote = "";
                paint();
                this.save.invoke(d);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void paint() {
        String str;
        TextView textView = this.status;
        if (this.warming) {
            str = "Warming up the camera · about 10 seconds…";
        } else {
            str = this.cameraError.length() > 0 ? this.cameraError : this.image != null ? "Snapshot ready" : "Take a cabin snapshot while parked.";
        }
        textView.setText(str);
        this.status.setTextColor((!(this.cameraError.length() > 0) || this.warming) ? CarUi.Companion.getDIM() : Color.rgb(255, 120, 120));
        this.note.setText(this.localNote);
        this.note.setVisibility(this.localNote.length() == 0 ? 8 : 0);
        boolean d = drawable();
        boolean has = !pts().isEmpty();
        Iterator it = CollectionsKt.listOf(new Pair[]{TuplesKt.to(this.leftBtn, Boolean.valueOf(Intrinsics.areEqual(this.active, "cameraRight"))), TuplesKt.to(this.rightBtn, Boolean.valueOf(Intrinsics.areEqual(this.active, "cameraLeft")))}).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Pair pair = (Pair) it.next();
            TextView b = (TextView) pair.component1();
            boolean on = ((Boolean) pair.component2()).booleanValue();
            b.setBackground(CarUi.rounded$default(this.ui, on ? Color.rgb(150, 110, 240) : CarUi.Companion.getCARD_ON(), 12, null, 4, null));
            if (!d) {
                r4 = 0.45f;
            }
            b.setAlpha(r4);
        }
        this.undoBtn.setAlpha((d && has) ? 1.0f : 0.45f);
        this.clearBtn.setAlpha(this.undoBtn.getAlpha());
        this.saveBtn.setAlpha(d ? 1.0f : 0.45f);
        this.snapBtn.setAlpha(this.warming ? 0.45f : 1.0f);
        this.canvas.invalidate();
    }

    /* compiled from: GalaxyVasm.kt */
    private static final class Board extends View {
        private int drag;
        private String dragSide;
        private final Paint p;
        final /* synthetic */ VasmEditorCard this$0;

        /* VF: from Vineflower (jadx: ) */
        public Board(VasmEditorCard var1, Context var2) {
            super(var2);
            Intrinsics.checkNotNullParameter(var2, "ctx");
            this.this$0 = var1;
            this.p = new Paint(3);
            this.drag = -1;
            this.dragSide = "";
        }

        @Override // android.view.View
        protected void onMeasure(int wSpec, int hSpec) {
            int w = View.MeasureSpec.getSize(wSpec);
            setMeasuredDimension(w, (int) ((w * this.this$0.height0) / Math.max(1, this.this$0.width0)));
        }

        private final float sx(int x) {
            return getWidth() - ((x * getWidth()) / this.this$0.width0);
        }

        private final float sy(int y) {
            return (y * getHeight()) / this.this$0.height0;
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            Intrinsics.checkNotNullParameter(c, "c");
            float w = getWidth();
            float h = getHeight();
            this.p.setStyle(Paint.Style.FILL);
            this.p.setColor(Color.rgb(21, 24, 33));
            c.drawRect(0.0f, 0.0f, w, h, this.p);
            Bitmap b = this.this$0.image;
            if (b != null) {
                c.save();
                c.scale(-1.0f, 1.0f, w / 2, 0.0f);
                c.drawBitmap(b, (Rect) null, new RectF(0.0f, 0.0f, w, h), this.p);
                c.restore();
            }
            float dot = Math.max(5.0f, w / 320.0f) * 1.6f;
            for (Pair pair : (Iterable<Pair>) (Iterable) CollectionsKt.listOf(new Pair[]{TuplesKt.to(this.this$0.camRight, Integer.valueOf(Color.rgb(94, 229, 238))), TuplesKt.to(this.this$0.camLeft, Integer.valueOf(Color.rgb(255, 184, 101)))})) {
                ArrayList list = (ArrayList) pair.component1();
                int col = ((Number) pair.component2()).intValue();
                if (!list.isEmpty()) {
                    Path path = new Path();
                    path.moveTo(sx(((int[]) list.get(0))[0]), sy(((int[]) list.get(0))[1]));
                    for (int[] q : ((int[]) CollectionsKt.drop(list, 1))) {
                        path.lineTo(sx(q[0]), sy(q[1]));
                    }
                    if (list.size() >= 3) {
                        path.close();
                        this.p.setStyle(Paint.Style.FILL);
                        this.p.setColor(Color.argb(56, Color.red(col), Color.green(col), Color.blue(col)));
                        c.drawPath(path, this.p);
                    }
                    this.p.setStyle(Paint.Style.STROKE);
                    this.p.setStrokeWidth(Math.max(2.0f, w / 640.0f) * 1.5f);
                    this.p.setColor(col);
                    c.drawPath(path, this.p);
                    this.p.setStyle(Paint.Style.FILL);
                    Iterator it = list.iterator();
                    Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                    while (it.hasNext()) {
                        Object next = it.next();
                        Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                        int[] q2 = (int[]) next;
                        c.drawCircle(sx(q2[0]), sy(q2[1]), dot, this.p);
                    }
                }
            }
        }

        private final int[] source(float x, float y) {
            float fx = x / getWidth();
            float fy = y / getHeight();
            boolean z = false;
            if (!(0.0f <= fx && fx <= 1.0f)) {
                return null;
            }
            if (0.0f <= fy && fy <= 1.0f) {
                z = true;
            }
            if (z) {
                return new int[]{MathKt.roundToInt((1 - fx) * this.this$0.width0), MathKt.roundToInt(this.this$0.height0 * fy)};
            }
            return null;
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent e) {
            ArrayList list;
            int i;
            int[] it;
            Intrinsics.checkNotNullParameter(e, "e");
            char c = 0;
            if (!this.this$0.drawable()) {
                return false;
            }
            char c2 = 1;
            switch (e.getActionMasked()) {
                case 0:
                    ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    int[] pt = source(e.getX(), e.getY());
                    if (pt == null) {
                        return true;
                    }
                    ArrayList list2 = this.this$0.pts();
                    float radius = (this.this$0.ui.dp(20) * this.this$0.width0) / Math.max(1, getWidth());
                    ArrayList $this$indexOfFirst$iv = list2;
                    int index$iv = 0;
                    Iterator it2 = $this$indexOfFirst$iv.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            Object item$iv = it2.next();
                            int[] it3 = (int[]) item$iv;
                            list = list2;
                            if (((float) Math.hypot((double) ((float) (it3[c] - pt[c])), (double) ((float) (it3[c2] - pt[c2])))) <= radius) {
                                i = index$iv;
                            } else {
                                index$iv++;
                                list2 = list;
                                c = 0;
                                c2 = 1;
                            }
                        } else {
                            list = list2;
                            i = -1;
                        }
                    }
                    int i2 = i;
                    if (i2 >= 0) {
                        this.drag = i2;
                        this.dragSide = this.this$0.active;
                        return true;
                    }
                    if (list.size() < 32) {
                        list.add(pt);
                        this.this$0.changed();
                        return true;
                    }
                    this.this$0.localNote = "Each side allows at most 32 vertices.";
                    this.this$0.paint();
                    return true;
                case 1:
                case 3:
                    this.drag = -1;
                    ViewParent parent2 = getParent();
                    if (parent2 != null) {
                        parent2.requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                    return true;
                case 2:
                    if (this.drag >= 0 && Intrinsics.areEqual(this.dragSide, this.this$0.active) && (it = source(RangesKt.coerceIn(e.getX(), 0.0f, getWidth()), RangesKt.coerceIn(e.getY(), 0.0f, getHeight()))) != null) {
                        VasmEditorCard vasmEditorCard = this.this$0;
                        vasmEditorCard.pts().set(this.drag, it);
                        vasmEditorCard.changed();
                        return true;
                    }
                    return true;
                default:
                    return true;
            }
        }
    }
}
