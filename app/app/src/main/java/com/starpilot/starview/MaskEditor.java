package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;

/* compiled from: MaskEditor.kt */
public final class MaskEditor extends View {
    private final Path all;
    private final LinearLayout bar;
    private final ArrayList<Float> cur;
    private final float d;
    private final Detector det;
    private float downX;
    private float downY;
    private int dragP;
    private int dragV;
    private final Paint fill;
    private Bitmap img;
    private final Paint line;
    private boolean moved;
    private final Function1<Boolean, Unit> onCamera;
    private final Function0<Unit> onDone;
    private final Function1<Integer, Unit> paintCam;
    private final Function1<Integer, Unit> paintMode;
    private final Path path;
    private final RectF pic;
    private final MaskEditor$poll$1 poll;
    private ArrayList<float[]> polys;
    private final LinearLayout row;
    private int sel;
    private final Paint text;
    private final CarUi ui;
    private boolean wide;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v15, types: [com.starpilot.starview.MaskEditor$poll$1] */
    public MaskEditor(Context ctx, Detector det, boolean z, Function1<? super Boolean, Unit> onCamera, Function0<Unit> onDone) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(det, "det");
        Intrinsics.checkNotNullParameter(onCamera, "onCamera");
        Intrinsics.checkNotNullParameter(onDone, "onDone");
        this.det = det;
        this.wide = z;
        this.onCamera = onCamera;
        this.onDone = onDone;
        this.ui = new CarUi(ctx, false, 2, null);
        this.d = ctx.getResources().getDisplayMetrics().density;
        this.polys = new ArrayList<>(DetMasks.INSTANCE.get(this.wide));
        this.cur = new ArrayList<>();
        this.sel = -1;
        this.dragP = -2;
        this.dragV = -1;
        this.pic = new RectF();
        this.fill = new Paint(1);
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3 * this.d);
        this.line = paint;
        Paint paint2 = new Paint(1);
        paint2.setColor(-1);
        paint2.setTextSize(17 * this.d);
        paint2.setTextAlign(Paint.Align.CENTER);
        this.text = paint2;
        this.path = new Path();
        this.all = new Path();
        LinearLayout linearLayout = new LinearLayout(ctx);
        linearLayout.setOrientation(1);
        linearLayout.setGravity(1);
        linearLayout.setPadding(this.ui.dp(12), this.ui.dp(12), this.ui.dp(12), this.ui.dp(8));
        this.bar = linearLayout;
        LinearLayout linearLayout2 = new LinearLayout(ctx);
        linearLayout2.setOrientation(0);
        this.row = linearLayout2;
        this.poll = new Runnable() { // from class: com.starpilot.starview.MaskEditor$poll$1
            @Override // java.lang.Runnable
            public void run() {
                Detector detector;
                Detector detector2;
                Detector detector3;
                detector = MaskEditor.this.det;
                Bitmap s = detector.getSnap();
                if (s != null) {
                    detector3 = MaskEditor.this.det;
                    if (detector3.getSnapWide() == MaskEditor.this.getWide()) {
                        MaskEditor.this.img = s;
                        MaskEditor.this.invalidate();
                    }
                }
                detector2 = MaskEditor.this.det;
                detector2.setSnapWant(MaskEditor.this.getWide() ? 2 : 1);
                MaskEditor.this.postDelayed(this, 1000L);
            }
        };
        setBackgroundColor(Color.rgb(11, 13, 18));
        Pair<LinearLayout, Function1<Integer, Unit>> chips = this.ui.chips(CollectionsKt.listOf((Object[]) new String[]{"Road camera", "Wide camera"}), new Function1() { // from class: com.starpilot.starview.MaskEditor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit _init_$lambda$4;
                _init_$lambda$4 = MaskEditor._init_$lambda$4(MaskEditor.this, ((Integer) obj).intValue());
                return _init_$lambda$4;
            }
        });
        LinearLayout component1 = chips.component1();
        Function1<Integer, Unit> component2 = chips.component2();
        this.paintCam = component2;
        component2.invoke(Integer.valueOf(this.wide ? 1 : 0));
        this.bar.addView(this.row, new LinearLayout.LayoutParams(-2, -2));
        this.row.addView(component1, new LinearLayout.LayoutParams(this.ui.dp(400), -2));
        for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("Finish area", new Function0() { // from class: com.starpilot.starview.MaskEditor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit _init_$lambda$5;
                _init_$lambda$5 = MaskEditor._init_$lambda$5(MaskEditor.this);
                return _init_$lambda$5;
            }
        }), TuplesKt.to("Undo corner", new Function0() { // from class: com.starpilot.starview.MaskEditor$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit _init_$lambda$6;
                _init_$lambda$6 = MaskEditor._init_$lambda$6(MaskEditor.this);
                return _init_$lambda$6;
            }
        }), TuplesKt.to("Delete area", new Function0() { // from class: com.starpilot.starview.MaskEditor$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit _init_$lambda$7;
                _init_$lambda$7 = MaskEditor._init_$lambda$7(MaskEditor.this);
                return _init_$lambda$7;
            }
        })})) {
            String str = (String) pair.component1();
            Function0 function0 = (Function0) pair.component2();
            this.row.addView(new View(ctx), new LinearLayout.LayoutParams(this.ui.dp(10), 1));
            this.row.addView(CarUi.button$default(this.ui, str, false, function0, 2, null));
        }
        this.row.addView(new View(ctx), new LinearLayout.LayoutParams(this.ui.dp(10), 1));
        this.row.addView(this.ui.button("Done", true, new Function0() { // from class: com.starpilot.starview.MaskEditor$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit _init_$lambda$8;
                _init_$lambda$8 = MaskEditor._init_$lambda$8(MaskEditor.this);
                return _init_$lambda$8;
            }
        }));
        Pair<LinearLayout, Function1<Integer, Unit>> chips2 = this.ui.chips(CollectionsKt.listOf((Object[]) new String[]{"Ignore inside the areas", "Only look inside the areas"}), new Function1() { // from class: com.starpilot.starview.MaskEditor$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit _init_$lambda$9;
                _init_$lambda$9 = MaskEditor._init_$lambda$9(MaskEditor.this, ((Integer) obj).intValue());
                return _init_$lambda$9;
            }
        });
        LinearLayout component12 = chips2.component1();
        Function1<Integer, Unit> component22 = chips2.component2();
        this.paintMode = component22;
        component22.invoke(Integer.valueOf(DetMasks.INSTANCE.keep(this.wide) ? 1 : 0));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.ui.dp(620), -2);
        layoutParams.topMargin = this.ui.dp(8);
        Unit unit = Unit.INSTANCE;
        this.bar.addView(component12, layoutParams);
    }

    public final boolean getWide() {
        return this.wide;
    }

    public final void setWide(boolean z) {
        this.wide = z;
    }

    private final void outline(Path into, float[] p) {
        int length = p.length / 2;
        for (int v = 0; v < length; v++) {
            if (v == 0) {
                into.moveTo(sx(p[0]), sy(p[1]));
            } else {
                into.lineTo(sx(p[v * 2]), sy(p[(v * 2) + 1]));
            }
        }
        into.close();
    }

    public final LinearLayout getBar() {
        return this.bar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$4(MaskEditor this$0, int i) {
        this$0.camera(i == 1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$5(MaskEditor this$0) {
        this$0.close();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$6(MaskEditor this$0) {
        if (!this$0.cur.isEmpty()) {
            this$0.cur.remove(this$0.cur.size() - 1);
            this$0.cur.remove(this$0.cur.size() - 1);
            this$0.invalidate();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$7(MaskEditor this$0) {
        if (this$0.cur.isEmpty()) {
            int size = this$0.polys.size();
            int i = this$0.sel;
            boolean z = false;
            if (i >= 0 && i < size) {
                z = true;
            }
            if (z) {
                this$0.polys.remove(this$0.sel);
                this$0.sel = -1;
                this$0.save();
            }
        } else {
            this$0.cur.clear();
        }
        this$0.invalidate();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$8(MaskEditor this$0) {
        this$0.close();
        this$0.onDone.invoke();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$9(MaskEditor this$0, int i) {
        DetMasks.INSTANCE.setKeep(this$0.wide, i == 1);
        this$0.paintMode.invoke(Integer.valueOf(i));
        this$0.invalidate();
        return Unit.INSTANCE;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(this.poll);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.poll);
        this.det.setSnapWant(0);
        super.onDetachedFromWindow();
    }

    private final void camera(boolean w) {
        if (w == this.wide) {
            return;
        }
        close();
        this.wide = w;
        this.polys = new ArrayList<>(DetMasks.INSTANCE.get(w));
        this.sel = -1;
        this.img = null;
        this.paintCam.invoke(Integer.valueOf(w ? 1 : 0));
        this.paintMode.invoke(Integer.valueOf(DetMasks.INSTANCE.keep(w) ? 1 : 0));
        this.onCamera.invoke(Boolean.valueOf(w));
        invalidate();
    }

    private final void save() {
        DetMasks.INSTANCE.set(this.wide, this.polys);
    }

    private final void close() {
        if (this.cur.size() >= 6) {
            this.polys.add(CollectionsKt.toFloatArray(this.cur));
            this.sel = this.polys.size() - 1;
            save();
        }
        this.cur.clear();
        invalidate();
    }

    private final float fx(float x) {
        return RangesKt.coerceIn((x - this.pic.left) / this.pic.width(), 0.0f, 1.0f);
    }

    private final float fy(float y) {
        return RangesKt.coerceIn((y - this.pic.top) / this.pic.height(), 0.0f, 1.0f);
    }

    private final float sx(float f) {
        return this.pic.left + (this.pic.width() * f);
    }

    private final float sy(float f) {
        return this.pic.top + (this.pic.height() * f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Pair<Integer, Integer> corner(float x, float y) {
        Ref.ObjectRef best = new Ref.ObjectRef();
        Ref.FloatRef bd = new Ref.FloatRef();
        bd.element = 30 * this.d;
        corner$look(this, x, y, bd, best, -1, this.cur);
        int i = 0;
        for (float[] p : this.polys) {
            int i2 = i;
            Intrinsics.checkNotNull(p);
            corner$look(this, x, y, bd, best, i2, ArraysKt.toList(p));
            i++;
        }
        return (Pair) best.element;
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [T, kotlin.Pair] */
    private static final void corner$look(MaskEditor this$0, float $x, float $y, Ref.FloatRef bd, Ref.ObjectRef<Pair<Integer, Integer>> objectRef, int pi, List<Float> list) {
        int size = list.size() / 2;
        for (int v = 0; v < size; v++) {
            float dd = (float) Math.hypot(this$0.sx(list.get(v * 2).floatValue()) - $x, this$0.sy(list.get((v * 2) + 1).floatValue()) - $y);
            if (dd < bd.element) {
                bd.element = dd;
                objectRef.element = TuplesKt.to(Integer.valueOf(pi), Integer.valueOf(v));
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        int i;
        Intrinsics.checkNotNullParameter(e, "e");
        switch (e.getActionMasked()) {
            case 0:
                this.downX = e.getX();
                this.downY = e.getY();
                this.moved = false;
                Pair c = corner(e.getX(), e.getY());
                this.dragP = c != null ? c.getFirst().intValue() : -2;
                this.dragV = c != null ? c.getSecond().intValue() : -1;
                return true;
            case 1:
                if (this.moved) {
                    if (this.dragP >= 0) {
                        save();
                    }
                    return true;
                }
                if (this.img == null || !this.pic.contains(e.getX(), e.getY())) {
                    return true;
                }
                if (this.dragP == -1 && this.dragV == 0 && this.cur.size() >= 6) {
                    close();
                    return true;
                }
                if (this.cur.isEmpty()) {
                    List $this$indexOfLast$iv = this.polys;
                    ListIterator iterator$iv = $this$indexOfLast$iv.listIterator($this$indexOfLast$iv.size());
                    while (true) {
                        if (iterator$iv.hasPrevious()) {
                            float[] it = iterator$iv.previous();
                            DetMasks detMasks = DetMasks.INSTANCE;
                            Intrinsics.checkNotNull(it);
                            if (detMasks.inside(it, fx(e.getX()), fy(e.getY()))) {
                                i = iterator$iv.nextIndex();
                            }
                        } else {
                            i = -1;
                        }
                    }
                    int hit = i;
                    if (hit >= 0) {
                        this.sel = hit;
                        invalidate();
                        return true;
                    }
                }
                if (this.cur.size() < 200) {
                    this.cur.add(Float.valueOf(fx(e.getX())));
                    this.cur.add(Float.valueOf(fy(e.getY())));
                    this.sel = -1;
                    invalidate();
                }
                return true;
            case 2:
                if (((float) Math.hypot(e.getX() - this.downX, e.getY() - this.downY)) > 8 * this.d) {
                    this.moved = true;
                }
                if (this.moved && this.dragP > -2) {
                    if (this.dragP == -1) {
                        this.cur.set(this.dragV * 2, Float.valueOf(fx(e.getX())));
                        this.cur.set((this.dragV * 2) + 1, Float.valueOf(fy(e.getY())));
                    } else {
                        this.polys.get(this.dragP)[this.dragV * 2] = fx(e.getX());
                        this.polys.get(this.dragP)[(this.dragV * 2) + 1] = fy(e.getY());
                    }
                    invalidate();
                }
                return true;
            default:
                return true;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        String str;
        Intrinsics.checkNotNullParameter(c, "c");
        float top = this.bar.getHeight() + (8 * this.d);
        Bitmap b = this.img;
        if (b == null) {
            c.drawText("Waiting for the " + (this.wide ? "wide" : "road") + " camera… (it streams while the car is on)", getWidth() / 2.0f, getHeight() / 2.0f, this.text);
            return;
        }
        float s = Math.min(getWidth() / b.getWidth(), ((getHeight() - top) - (40 * this.d)) / b.getHeight());
        float w = b.getWidth() * s;
        float h = b.getHeight() * s;
        float f = 2;
        this.pic.set((getWidth() - w) / f, top, (getWidth() + w) / f, top + h);
        c.drawBitmap(b, (Rect) null, this.pic, (Paint) null);
        boolean keep = DetMasks.INSTANCE.keep(this.wide);
        int v = 170;
        int i = 114;
        this.fill.setColor(Color.argb(170, 114, 114, 114));
        if (keep && !this.polys.isEmpty()) {
            this.all.rewind();
            Iterator<float[]> it = this.polys.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                float[] p = it.next();
                Path path = this.all;
                Intrinsics.checkNotNull(p);
                outline(path, p);
            }
            c.save();
            c.clipOutPath(this.all);
            c.drawRect(this.pic, this.fill);
            c.restore();
        }
        Iterator it2 = this.polys.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            int i3 = i2;
            i2++;
            float[] p2 = (float[]) it2.next();
            this.path.rewind();
            Path path2 = this.path;
            Intrinsics.checkNotNull(p2);
            outline(path2, p2);
            if (!keep) {
                this.fill.setColor(Color.argb(v, i, i, i));
                c.drawPath(this.path, this.fill);
            }
            this.line.setColor(i3 == this.sel ? CarUi.Companion.getACCENT() : Color.argb(200, 255, 255, 255));
            c.drawPath(this.path, this.line);
            this.fill.setColor(this.line.getColor());
            int v2 = 0;
            for (int length = p2.length / 2; v2 < length; length = length) {
                c.drawCircle(sx(p2[v2 * 2]), sy(p2[(v2 * 2) + 1]), 7 * this.d, this.fill);
                v2++;
                it2 = it2;
            }
            v = 170;
            i = 114;
        }
        if (!this.cur.isEmpty()) {
            this.line.setColor(CarUi.Companion.getACCENT());
            this.fill.setColor(CarUi.Companion.getACCENT());
            int size = this.cur.size() / 2;
            int v3 = 0;
            while (v3 < size) {
                Float f2 = this.cur.get(v3 * 2);
                Intrinsics.checkNotNullExpressionValue(f2, "get(...)");
                float x = sx(f2.floatValue());
                Float f3 = this.cur.get((v3 * 2) + 1);
                Intrinsics.checkNotNullExpressionValue(f3, "get(...)");
                float y = sy(f3.floatValue());
                c.drawCircle(x, y, (v3 == 0 ? 11 : 7) * this.d, this.fill);
                if (v3 > 0) {
                    Float f4 = this.cur.get((v3 * 2) - 2);
                    Intrinsics.checkNotNullExpressionValue(f4, "get(...)");
                    float sx = sx(f4.floatValue());
                    Float f5 = this.cur.get((v3 * 2) - 1);
                    Intrinsics.checkNotNullExpressionValue(f5, "get(...)");
                    c.drawLine(sx, sy(f5.floatValue()), x, y, this.line);
                }
                v3++;
            }
        }
        if (this.cur.isEmpty()) {
            str = keep ? "Draw around what the detector should look at (the road through the windshield). Grey = ignored." : "Draw around what the detector should ignore (hood, dash, mirror mount, reflections). Grey = ignored.";
        } else {
            str = "Tap to add corners. Tap the first corner or Finish area to close it.";
        }
        c.drawText(str, getWidth() / 2.0f, this.pic.bottom + (28 * this.d), this.text);
    }
}
