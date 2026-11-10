package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.graphics.PathParser;
import java.util.HashMap;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SvIcon.kt */
public final class SvIcon {
    public static final String CAM = "cam";
    public static final String DEVICE = "device";
    public static final String DISPLAY = "display";
    public static final int NA_BG = -15394526;
    public static final int NA_FG = -11840408;
    public static final int OFF_BG = -14999248;
    public static final int OFF_FG = -7497813;
    public static final String PERSON = "person";
    public static final String SOUND = "sound";
    public static final String SPEED = "speed";
    public static final String STEER = "steer";
    public static final String TABLET = "tablet";
    public static final SvIcon INSTANCE = new SvIcon();
    private static final HashMap<String, Path> cache = new HashMap<>();

    private SvIcon() {
    }

    public final boolean has(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return SvIconPaths.INSTANCE.getPATHS().containsKey(name);
    }

    public final Path path(String name) {
        Path path;
        Intrinsics.checkNotNullParameter(name, "name");
        Map $this$getOrPut$iv = cache;
        Path path2 = ((Path) $this$getOrPut$iv.get(name));
        if (path2 == null) {
            String d = SvIconPaths.INSTANCE.getPATHS().get(name);
            if (d == null) {
                d = (String) MapsKt.getValue(SvIconPaths.INSTANCE.getPATHS(), "slider");
            }
            Path createPathFromPathData = PathParser.createPathFromPathData(d);
            if (createPathFromPathData == null) {
                createPathFromPathData = new Path();
            }
            path = createPathFromPathData;
            $this$getOrPut$iv.put(name, path);
        } else {
            path = path2;
        }
        return path;
    }

    public final int fg(String group) {
        Intrinsics.checkNotNullParameter(group, "group");
        Pair<Integer, Integer> pair = SvIconPaths.INSTANCE.getGROUPS().get(group);
        if (pair != null) {
            return pair.getFirst().intValue();
        }
        return -7497813;
    }

    public final int bg(String group) {
        Intrinsics.checkNotNullParameter(group, "group");
        Pair<Integer, Integer> pair = SvIconPaths.INSTANCE.getGROUPS().get(group);
        if (pair != null) {
            return pair.getSecond().intValue();
        }
        return -14999248;
    }

    /* compiled from: SvIcon.kt */
    public enum State {
        ON,
        OFF,
        NA;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<State> getEntries() {
            return $ENTRIES;
        }
    }

    public static /* synthetic */ Drawable drawable$default(SvIcon svIcon, String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i2 = -1;
        }
        return svIcon.drawable(str, i, i2);
    }

    public final Drawable drawable(String name, int color, int sizePx) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new Glyph(name, color, sizePx);
    }

    public static /* synthetic */ View chip$default(SvIcon svIcon, Context context, String str, String str2, int i, State state, int i2, Object obj) {
        if ((i2 & 16) != 0) {
            state = State.ON;
        }
        return svIcon.chip(context, str, str2, i, state);
    }

    public final View chip(Context ctx, String name, String group, int sizeDp, State state) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(group, "group");
        Intrinsics.checkNotNullParameter(state, "state");
        Chip it = new Chip(ctx, name, group, state);
        int px = (int) (sizeDp * ctx.getResources().getDisplayMetrics().density);
        it.setMinimumWidth(px);
        it.setMinimumHeight(px);
        return it;
    }

    /* compiled from: SvIcon.kt */
    private static final class Glyph extends Drawable {
        private final int color;
        private final Matrix m;
        private final String name;
        private final Path p;
        private final Paint paint;
        private final int size;

        public Glyph(String name, int color, int size) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.name = name;
            this.color = color;
            this.size = size;
            Paint $this$paint_u24lambda_u240 = new Paint(1);
            $this$paint_u24lambda_u240.setStyle(Paint.Style.STROKE);
            $this$paint_u24lambda_u240.setStrokeCap(Paint.Cap.ROUND);
            $this$paint_u24lambda_u240.setStrokeJoin(Paint.Join.ROUND);
            this.paint = $this$paint_u24lambda_u240;
            this.m = new Matrix();
            this.p = new Path();
        }

        public /* synthetic */ Glyph(String str, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i, (i3 & 4) != 0 ? -1 : i2);
        }

        public final int getColor() {
            return this.color;
        }

        public final String getName() {
            return this.name;
        }

        public final int getSize() {
            return this.size;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.size;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.size;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas c) {
            Intrinsics.checkNotNullParameter(c, "c");
            Rect b = getBounds();
            Intrinsics.checkNotNullExpressionValue(b, "getBounds(...)");
            float s = Math.min(b.width(), b.height()) / 24.0f;
            this.paint.setColor(this.color);
            this.paint.setStrokeWidth(2.0f * s);
            this.m.setScale(s, s);
            float f = 24;
            float f2 = 2;
            this.m.postTranslate(b.left + ((b.width() - (f * s)) / f2), b.top + ((b.height() - (f * s)) / f2));
            SvIcon.INSTANCE.path(this.name).transform(this.m, this.p);
            c.drawPath(this.p, this.paint);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int alpha) {
            this.paint.setAlpha(alpha);
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter cf) {
            this.paint.setColorFilter(cf);
        }

        @Override // android.graphics.drawable.Drawable
        @Deprecated(message = "Deprecated in Java")
        public int getOpacity() {
            return -3;
        }
    }

    /* compiled from: SvIcon.kt */
    public static final class Chip extends View {
        private final Paint bgPaint;
        private String group;
        private String name;
        private final RectF r;
        private State state;

        /* compiled from: SvIcon.kt */
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[State.values().length];
                try {
                    iArr[State.ON.ordinal()] = 1;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[State.OFF.ordinal()] = 2;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[State.NA.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* VF: from Vineflower (jadx: ) */
        public Chip(Context var1, String var2, String var3, SvIcon.State var4) {
            super(var1);
            Intrinsics.checkNotNullParameter(var1, "ctx");
            Intrinsics.checkNotNullParameter(var2, "name");
            Intrinsics.checkNotNullParameter(var3, "group");
            Intrinsics.checkNotNullParameter(var4, "state");
            this.name = var2;
            this.group = var3;
            this.state = var4;
            this.bgPaint = new Paint(1);
            this.r = new RectF();
        }

        public final String getGroup() {
            return this.group;
        }

        public final String getName() {
            return this.name;
        }

        public final State getState() {
            return this.state;
        }

        public final void setGroup(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.group = str;
        }

        public final void setName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.name = str;
        }

        public final void setState(State state) {
            Intrinsics.checkNotNullParameter(state, "<set-?>");
            this.state = state;
        }

        public static /* synthetic */ void set$default(Chip chip, String str, String str2, State state, int i, Object obj) {
            if ((i & 1) != 0) {
                str = chip.name;
            }
            if ((i & 2) != 0) {
                str2 = chip.group;
            }
            if ((i & 4) != 0) {
                state = chip.state;
            }
            chip.set(str, str2, state);
        }

        public final void set(String name, String group, State state) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(group, "group");
            Intrinsics.checkNotNullParameter(state, "state");
            this.name = name;
            this.group = group;
            this.state = state;
            invalidate();
        }

        @Override // android.view.View
        protected void onMeasure(int w, int h) {
            setMeasuredDimension(View.resolveSize(getMinimumWidth(), w), View.resolveSize(getMinimumHeight(), h));
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            int valueOf;
            int bg;
            Intrinsics.checkNotNullParameter(c, "c");
            float sz = Math.min(getWidth(), getHeight());
            switch (WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()]) {
                case 1:
                    valueOf = Integer.valueOf(SvIcon.INSTANCE.fg(this.group));
                    bg = SvIcon.INSTANCE.bg(this.group);
                    break;
                case 2:
                    valueOf = -7497813;
                    bg = -14999248;
                    break;
                case 3:
                    valueOf = -11840408;
                    bg = -15394526;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            Pair pair = TuplesKt.to(valueOf, Integer.valueOf(bg));
            int f = ((Number) pair.component1()).intValue();
            int b = ((Number) pair.component2()).intValue();
            this.bgPaint.setColor(b);
            float f2 = 2;
            this.r.set((getWidth() - sz) / f2, (getHeight() - sz) / f2, (getWidth() + sz) / f2, (getHeight() + sz) / f2);
            c.drawRoundRect(this.r, sz * 0.3f, 0.3f * sz, this.bgPaint);
            Glyph g = new Glyph(this.name, f, 0, 4, null);
            int inset = (int) (0.23f * sz);
            g.setBounds(((int) this.r.left) + inset, ((int) this.r.top) + inset, ((int) this.r.right) - inset, ((int) this.r.bottom) - inset);
            g.draw(c);
        }
    }
}
