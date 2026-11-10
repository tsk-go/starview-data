package com.starpilot.starview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starpilot.starview.LaneFixDialog;
import com.starpilot.starview.LaneRamps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout.LayoutParams;
import kotlin.jvm.internal.Ref.ObjectRef;

/* compiled from: LaneFixDialog.kt */
public final class LaneFixDialog {
    public static final LaneFixDialog INSTANCE = new LaneFixDialog();

    private LaneFixDialog() {
    }

    /* compiled from: LaneFixDialog.kt */
    public static final class Result {
        private final int extraL;
        private final int extraR;
        private final String kind;
        private final String kindR;
        private final int lane;
        private final int otherWay;
        private final boolean remember;
        private final int through;
        private final String way;

        public Result(int through, int extraL, int extraR, String kind, int lane, boolean remember, String way, int otherWay, String kindR) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(way, "way");
            Intrinsics.checkNotNullParameter(kindR, "kindR");
            this.through = through;
            this.extraL = extraL;
            this.extraR = extraR;
            this.kind = kind;
            this.lane = lane;
            this.remember = remember;
            this.way = way;
            this.otherWay = otherWay;
            this.kindR = kindR;
        }

        public /* synthetic */ Result(int i, int i2, int i3, String str, int i4, boolean z, String str2, int i5, String str3, int i6, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, i3, str, i4, z, (i6 & 64) != 0 ? "" : str2, (i6 & 128) != 0 ? 0 : i5, (i6 & 256) != 0 ? "" : str3);
        }

        public final int getExtraL() {
            return this.extraL;
        }

        public final int getExtraR() {
            return this.extraR;
        }

        public final String getKind() {
            return this.kind;
        }

        public final int getLane() {
            return this.lane;
        }

        public final boolean getRemember() {
            return this.remember;
        }

        public final int getThrough() {
            return this.through;
        }

        public final String getKindR() {
            return this.kindR;
        }

        public final int getOtherWay() {
            return this.otherWay;
        }

        public final String getWay() {
            return this.way;
        }

        public final int getTotal() {
            return this.through + this.extraL + this.extraR;
        }

        public final String getKindLeft() {
            return this.kind;
        }

        public final String getKindRight() {
            String str = this.kindR;
            if (str.length() == 0) {
                str = this.kind;
            }
            return str;
        }

        /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
        public final Boolean getTwoWay() {
            String var2 = this.way;
            switch (var2.hashCode()) {
                case 110182:
                    if (!var2.equals("one")) {
                        return null;
                    }
                    break;
                case 115276:
                    if (var2.equals("two")) {
                        boolean var1 = true;
                        return var1;
                    }

                    return null;
                case 1674318603:
                    if (!var2.equals("divided")) {
                        return null;
                    }
                    break;
                default:
                    return null;
            }

            boolean var4 = false;
            return var4;
        }

        private static final String text$ex(int n, String k, String side) {
            String str;
            if (n <= 0) {
                return null;
            }
            if (Intrinsics.areEqual(k, "exit")) {
                str = "exit lane" + (n > 1 ? "s" : "");
            } else {
                str = "merging";
            }
            return n + " " + str + " on the " + side;
        }

        /* VF: from Vineflower (jadx: ) */
        public final String text() {
            List var5 = CollectionsKt.listOfNotNull(
                new String[]{text$ex(this.extraL, this.getKindLeft(), "left"), text$ex(this.extraR, this.getKindRight(), "right")}
            );
            StringBuilder var3;
            if (this.lane > 0) {
                int var2 = this.lane;
                int var1 = this.getTotal();
                var3 = new StringBuilder().append("lane ").append(var2).append(" of ").append(var1);
            } else {
                int var7 = this.getTotal();
                var3 = new StringBuilder().append(var7).append(" lanes");
            }

            String var4 = var3.toString();
            String var6 = this.way;
            int var8 = var6.hashCode();
            String var11 = "";
            switch (var8) {
                case 110182:
                    if (var6.equals("one")) {
                        var11 = ", one-way";
                    }
                    break;
                case 115276:
                    if (var6.equals("two")) {
                        if (this.otherWay > 0) {
                            var8 = this.otherWay;
                            var11 = " (" + var8 + " the other way)";
                        }

                        var11 = ", two-way" + var11;
                    }
                    break;
                case 1674318603:
                    if (var6.equals("divided")) {
                        var11 = ", divided highway";
                    }
            }

            if (!var5.isEmpty()) {
                var8 = this.through;
                String var12 = CollectionsKt.joinToString(var5, " + ", "", "", -1, "...", null);
                var4 = var4 + " (" + var8 + " + " + var12 + ")";
            }

            return var4 + var11;
        }

        private static final String sentence$ex$1(int n, String k, String side) {
            if (n <= 0) {
                return null;
            }
            return n + " " + (Intrinsics.areEqual(k, "exit") ? "exit" : "merge") + " lane" + (n > 1 ? "s" : "") + " on the " + side;
        }

        public final String sentence() {
            StringBuilder append;
            List e = CollectionsKt.listOfNotNull((Object[]) new String[]{sentence$ex$1(this.extraL, getKindLeft(), "left"), sentence$ex$1(this.extraR, getKindRight(), "right")});
            if (this.lane > 0) {
                append = new StringBuilder().append("Lane ").append(this.lane).append(" of ").append(this.through);
            } else {
                append = new StringBuilder().append(this.through).append(" lane").append(this.through == 1 ? "" : "s");
            }
            String base = append.toString();
            return e.isEmpty() ? base : base + ", plus " + CollectionsKt.joinToString(e, " and ", "", "", -1, "...", null);
        }
    }

    /* compiled from: LaneFixDialog.kt */
    public static final class Road {
        public static final Companion Companion = new Companion(null);
        private int other;
        private List<Character> types;
        private String way;
        private int you;

        public Road(List<Character> types, int you, String way, int other) {
            Intrinsics.checkNotNullParameter(types, "types");
            Intrinsics.checkNotNullParameter(way, "way");
            this.types = types;
            this.you = you;
            this.way = way;
            this.other = other;
        }

        public final int getOther() {
            return this.other;
        }

        public final List<Character> getTypes() {
            return this.types;
        }

        public final String getWay() {
            return this.way;
        }

        public final int getYou() {
            return this.you;
        }

        public final void setOther(int i) {
            this.other = i;
        }

        public final void setTypes(List<Character> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.types = list;
        }

        public final void setWay(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.way = str;
        }

        public final void setYou(int i) {
            this.you = i;
        }

        public final int getEL() {
            Iterable $this$takeWhile$iv = this.types;
            ArrayList list$iv = new ArrayList();
            for (Object item$iv : $this$takeWhile$iv) {
                char it = ((Character) item$iv).charValue();
                if (!(it != 'n')) {
                    break;
                }
                list$iv.add(item$iv);
            }
            return list$iv.size();
        }

        public final int getER() {
            List list;
            List $this$takeLastWhile$iv = this.types;
            if ($this$takeLastWhile$iv.isEmpty()) {
                list = CollectionsKt.emptyList();
            } else {
                ListIterator iterator$iv = $this$takeLastWhile$iv.listIterator($this$takeLastWhile$iv.size());
                while (true) {
                    if (iterator$iv.hasPrevious()) {
                        char it = iterator$iv.previous().charValue();
                        if (!(it != 'n')) {
                            iterator$iv.next();
                            int expectedSize$iv = $this$takeLastWhile$iv.size() - iterator$iv.nextIndex();
                            if (expectedSize$iv == 0) {
                                list = CollectionsKt.emptyList();
                            } else {
                                ArrayList $this$takeLastWhile_u24lambda_u248$iv = new ArrayList(expectedSize$iv);
                                while (iterator$iv.hasNext()) {
                                    $this$takeLastWhile_u24lambda_u248$iv.add(iterator$iv.next());
                                }
                                list = $this$takeLastWhile_u24lambda_u248$iv;
                            }
                        }
                    } else {
                        list = CollectionsKt.toList($this$takeLastWhile$iv);
                        break;
                    }
                }
            }
            return list.size();
        }

        public final int getThrough() {
            return (this.types.size() - getEL()) - getER();
        }

        /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
        public final LaneFixDialog.Result result(boolean var1) {
            int var3;
            Character var8;
            Object var9;
            label62: {
                var8 = CollectionsKt.firstOrNull(this.types);
                var9 = null;
                var3 = 0;
                if (var8 != null) {
                    boolean var2;
                    if (var8 != 'n') {
                        var2 = true;
                    } else {
                        var2 = false;
                    }

                    if (var2) {
                        break label62;
                    }
                }

                var8 = null;
            }

            Character var11 = CollectionsKt.lastOrNull(this.types);
            Character var10 = (Character)var9;
            if (var11 != null) {
                boolean var12;
                if (var11 != 'n') {
                    var12 = true;
                } else {
                    var12 = false;
                }

                var10 = (Character)var9;
                if (var12) {
                    var10 = var11;
                }
            }

            if (var8 != null) {
                var9 = result$k(var8);
            } else {
                var9 = result$k(var10);
            }

            String var16;
            if (var8 != null && var10 != null && !Intrinsics.areEqual(var10, var8)) {
                var16 = result$k(var10);
            } else {
                var16 = "";
            }

            int var5 = this.getThrough();
            int var4 = this.getEL();
            int var6 = this.getER();
            int var13 = this.types.size();
            int var7 = this.you;
            int var14;
            if (var7 >= 0 && var7 < var13) {
                var14 = 1;
            } else {
                var14 = 0;
            }

            if ((var14 != 0)) {
                var14 = this.you + 1;
            } else {
                var14 = 0;
            }

            String var18 = this.way;
            if (Intrinsics.areEqual(this.way, "two")) {
                var3 = this.other;
            }

            return new LaneFixDialog.Result(var5, var4, var6, (String)var9, var14, var1, var18, var3, var16);
        }

        private static final String result$k(Character c) {
            return (c != null && c.charValue() == 'x') ? "exit" : (c != null && c.charValue() == 'm') ? "merge" : "";
        }

        public final void add(int side) {
            if (this.types.size() >= 8) {
                return;
            }
            List<Character> list = this.types;
            if (side < 0) {
                list.add(0, 'n');
                if (this.you >= 0) {
                    this.you++;
                }
            } else {
                list.add('n');
            }
            fixGroups();
        }

        public final void remove(int side) {
            if (this.types.size() <= 1) {
                return;
            }
            if (side < 0) {
                this.types.remove(0);
                this.you = this.you != 0 ? this.you - 1 : -1;
            } else {
                if (this.you == this.types.size() - 1) {
                    this.you = -1;
                }
                this.types.remove(this.types.size() - 1);
            }
            if (getThrough() == 0) {
                this.types.set(side >= 0 ? this.types.size() - 1 : 0, 'n');
            }
        }

        public final boolean taggable(int i) {
            if (!(i >= 0 && i < this.types.size())) {
                return false;
            }
            int l = getEL();
            int r = getER();
            boolean leftOk = i == l + (-1) || (i == l && l < 2 && getThrough() > 1);
            boolean rightOk = i == this.types.size() - r || (i == (this.types.size() - r) - 1 && r < 2 && getThrough() > 1);
            return leftOk || rightOk;
        }

        public final void cycle(int i) {
            char next;
            if (taggable(i)) {
                boolean z = true;
                if (i != getEL() - 1 && (i != getEL() || getEL() >= 2 || getThrough() <= 1)) {
                    z = false;
                }
                boolean left = z;
                switch (this.types.get(i).charValue()) {
                    case 'n':
                        next = 'x';
                        break;
                    case 'x':
                        next = 'm';
                        break;
                    default:
                        next = 'n';
                        break;
                }
                if (next == 'n') {
                    this.types.set(i, 'n');
                    return;
                }
                this.types.set(i, Character.valueOf(next));
                if (left) {
                    for (int j = 0; j < i; j++) {
                        this.types.set(j, Character.valueOf(next));
                    }
                } else {
                    int size = this.types.size();
                    for (int j2 = i + 1; j2 < size; j2++) {
                        this.types.set(j2, Character.valueOf(next));
                    }
                }
                fixGroups();
            }
        }

        private final void fixGroups() {
            int l = getEL();
            if (l > 1) {
                for (int j = 0; j < l; j++) {
                    this.types.set(j, this.types.get(l - 1));
                }
            }
            int r = getER();
            if (r > 1) {
                int size = this.types.size();
                for (int j2 = this.types.size() - r; j2 < size; j2++) {
                    this.types.set(j2, this.types.get(this.types.size() - r));
                }
            }
        }

        public final void addExtra(char kind, int side) {
            if (side < 0) {
                if (getEL() >= 2) {
                    return;
                }
                this.types.add(0, Character.valueOf(kind));
                if (this.you >= 0) {
                    this.you++;
                }
            } else if (getER() >= 2) {
                return;
            } else {
                this.types.add(Character.valueOf(kind));
            }
            fixGroups();
        }

        /* compiled from: LaneFixDialog.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final Road of(Result r, int preset) {
                boolean z = false;
                if (r == null || r.getThrough() <= 0) {
                    int coerceIn = RangesKt.coerceIn(preset, 1, 6);
                    ArrayList arrayList = new ArrayList(coerceIn);
                    for (int i = 0; i < coerceIn; i++) {
                        arrayList.add('n');
                    }
                    return new Road(arrayList, -1, "", 0);
                }
                int extraL = r.getExtraL();
                ArrayList arrayList2 = new ArrayList(extraL);
                for (int i2 = 0; i2 < extraL; i2++) {
                    arrayList2.add(Character.valueOf(of$c(r.getKindLeft())));
                }
                ArrayList arrayList3 = arrayList2;
                int coerceIn2 = RangesKt.coerceIn(r.getThrough(), 1, 6);
                ArrayList arrayList4 = new ArrayList(coerceIn2);
                for (int i3 = 0; i3 < coerceIn2; i3++) {
                    arrayList4.add('n');
                }
                List plus = CollectionsKt.plus((Collection) arrayList3, (Iterable) arrayList4);
                int extraR = r.getExtraR();
                ArrayList arrayList5 = new ArrayList(extraR);
                for (int i4 = 0; i4 < extraR; i4++) {
                    arrayList5.add(Character.valueOf(of$c(r.getKindRight())));
                }
                List t = CollectionsKt.plus((Collection) plus, (Iterable) arrayList5);
                List mutableList = CollectionsKt.toMutableList((Collection) t);
                int size = t.size();
                int lane = r.getLane();
                if (1 <= lane && lane <= size) {
                    z = true;
                }
                return new Road(mutableList, z ? r.getLane() - 1 : -1, r.getWay(), r.getOtherWay());
            }

            private static final char of$c(String k) {
                return Intrinsics.areEqual(k, "exit") ? 'x' : 'm';
            }
        }
    }

    /* compiled from: LaneFixDialog.kt */
    public static final class RoadView extends View {
        private final Function0<Unit> changed;
        private final float dn;
        private final ArrayList<RectF> lanes;
        private final Paint p;
        private final Road road;
        private final ArrayList<Pair<Integer, RectF>> tags;
        private final Paint txt;

        /* VF: from Vineflower (jadx: ) */
        public RoadView(Context var1, LaneFixDialog.Road var2, Function0<Unit> var3) {
            super(var1);
            Intrinsics.checkNotNullParameter(var1, "ctx");
            Intrinsics.checkNotNullParameter(var2, "road");
            Intrinsics.checkNotNullParameter(var3, "changed");
            this.road = var2;
            this.changed = var3;
            this.dn = this.getResources().getDisplayMetrics().density;
            this.p = new Paint(1);
            Paint var4 = new Paint(1);
            var4.setTextAlign(android.graphics.Paint.Align.CENTER);
            var4.setTypeface(Typeface.DEFAULT_BOLD);
            this.txt = var4;
            this.lanes = new ArrayList<>();
            this.tags = new ArrayList<>();
        }

        public final Function0<Unit> getChanged() {
            return this.changed;
        }

        public final Road getRoad() {
            return this.road;
        }

        private final float getTagH() {
            return 34 * this.dn;
        }

        @Override // android.view.View
        protected void onMeasure(int w, int h) {
            setMeasuredDimension(View.MeasureSpec.getSize(w), (int) (300 * this.dn));
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            float f;
            float f2;
            float f3;
            RectF r;
            float x;
            int k;
            float f4;
            int otherN;
            String str;
            char c2;
            char c3;
            String arrow;
            float centerX;
            float f5;
            float x2;
            int i;
            int i2;
            int i3;
            String str2;
            Intrinsics.checkNotNullParameter(c, "c");
            List t = this.road.getTypes();
            int k2 = t.size();
            float roadH = (getHeight() - getTagH()) - (10 * this.dn);
            int otherN2 = Intrinsics.areEqual(this.road.getWay(), "two") ? Math.max(1, this.road.getOther()) : 0;
            String str3 = "divided";
            float median = Intrinsics.areEqual(this.road.getWay(), "divided") ? 34 * this.dn : 0.0f;
            float f6 = 8;
            float avail = (getWidth() - (this.dn * f6)) - median;
            float lw = Math.min(120 * this.dn, avail / (k2 + (otherN2 * 0.8f)));
            float ow = 0.8f * lw;
            float full = (otherN2 * ow) + median + (k2 * lw);
            float f7 = 2;
            float x3 = (getWidth() - full) / f7;
            this.p.setStyle(Paint.Style.FILL);
            this.p.setColor(Color.rgb(42, 47, 56));
            float f8 = 6;
            float f9 = 14;
            c.drawRoundRect(new RectF(x3 - (this.dn * f8), 0.0f, x3 + full + (this.dn * f8), roadH), this.dn * f9, this.dn * f9, this.p);
            int i4 = 0;
            float x4 = x3;
            while (i4 < otherN2) {
                RectF r2 = new RectF(x4, 0.0f, x4 + ow, roadH);
                float f10 = f8;
                float f11 = f6;
                this.txt.setColor(Color.rgb(96, 102, 116));
                this.txt.setTextSize(34 * this.dn);
                c.drawText("↓", r2.centerX(), r2.centerY(), this.txt);
                if (i4 > 0) {
                    dashed(c, x4, roadH, Color.rgb(150, 156, 168));
                }
                x4 += ow;
                i4++;
                f8 = f10;
                f6 = f11;
            }
            float f12 = f8;
            float f13 = f6;
            if (otherN2 <= 0) {
                f = f7;
                f2 = f12;
                f3 = f13;
            } else {
                if (this.road.getOther() == 0) {
                    this.txt.setTextSize(13 * this.dn);
                    this.txt.setColor(Color.rgb(140, 146, 160));
                    c.drawText("other way", x4 - ((otherN2 * ow) / f7), roadH - (16 * this.dn), this.txt);
                }
                this.p.setColor(Color.rgb(233, 194, 59));
                float f14 = 5;
                f = f7;
                f2 = f12;
                f3 = f13;
                c.drawRect(x4 - (this.dn * f14), 0.0f, x4 - (this.dn * f7), roadH, this.p);
                c.drawRect(x4 + (f * this.dn), 0.0f, x4 + (f14 * this.dn), roadH, this.p);
            }
            if (median > 0.0f) {
                this.p.setColor(Color.rgb(58, 70, 52));
                c.drawRect(x4, 0.0f, x4 + median, roadH, this.p);
                this.txt.setTextSize(12 * this.dn);
                this.txt.setColor(Color.rgb(150, 170, 140));
                c.save();
                c.rotate(-90.0f, (median / f) + x4, roadH / f);
                c.drawText("barrier / median", (median / f) + x4, (roadH / f) + (4 * this.dn), this.txt);
                c.restore();
                x4 += median;
            }
            this.lanes.clear();
            this.tags.clear();
            float x0 = x4;
            float x5 = x4;
            int i5 = 0;
            while (true) {
                int k3 = 240;
                if (i5 < k2) {
                    RectF r3 = new RectF(x5, 0.0f, x5 + lw, roadH);
                    this.lanes.add(r3);
                    boolean extra = t.get(i5).charValue() != 'n';
                    if (!extra) {
                        r = r3;
                        x = x5;
                        k = k2;
                    } else {
                        this.p.setColor(Color.rgb(58, 51, 38));
                        c.drawRect(r3, this.p);
                        this.p.setColor(Color.rgb(47, 42, 32));
                        this.p.setStrokeWidth(7 * this.dn);
                        c.save();
                        c.clipRect(r3);
                        float s = -roadH;
                        while (s < lw) {
                            c.drawLine(r3.left + s, roadH, r3.left + s + roadH, 0.0f, this.p);
                            s += 22 * this.dn;
                            k3 = k3;
                            r3 = r3;
                            k2 = k2;
                            x5 = x5;
                        }
                        r = r3;
                        x = x5;
                        k = k2;
                        c.restore();
                    }
                    if (i5 != this.road.getYou()) {
                        f4 = f9;
                        otherN = otherN2;
                        str = str3;
                        c2 = 5;
                    } else {
                        this.p.setColor(Color.argb(46, 111, 227, 161));
                        c.drawRect(r, this.p);
                        this.p.setStyle(Paint.Style.STROKE);
                        this.p.setStrokeWidth(4 * this.dn);
                        this.p.setColor(Color.rgb(111, 227, 161));
                        c.drawRoundRect(new RectF(r.left + (this.dn * f), this.dn * f, r.right - (this.dn * f), roadH - (this.dn * f)), f3 * this.dn, this.dn * f3, this.p);
                        this.p.setStyle(Paint.Style.FILL);
                        float cw = Math.min(46 * this.dn, 0.42f * lw);
                        float ch = 1.65f * cw;
                        float cx = r.centerX();
                        float cy = 0.55f * roadH;
                        otherN = otherN2;
                        str = str3;
                        this.p.setColor(Color.rgb(111, 227, 161));
                        float f15 = 12;
                        c.drawRoundRect(new RectF(cx - (cw / f), cy - (ch / f), cx + (cw / f), cy + (ch / f)), this.dn * f15, f15 * this.dn, this.p);
                        this.p.setColor(Color.rgb(11, 61, 35));
                        float f16 = 7;
                        float f17 = 9;
                        f4 = f9;
                        RectF rectF = new RectF((cx - (cw / f)) + (this.dn * f16), (cy - (ch / f)) + (this.dn * f17), ((cw / f) + cx) - (f16 * this.dn), (cy - (ch / f)) + (f17 * this.dn) + (0.24f * ch));
                        c2 = 5;
                        float f18 = 5;
                        c.drawRoundRect(rectF, this.dn * f18, f18 * this.dn, this.p);
                    }
                    boolean onLeft = i5 < this.road.getEL();
                    if (extra) {
                        c3 = 'x';
                        arrow = (t.get(i5).charValue() != 'x' ? !onLeft : onLeft) ? "↖" : "↗";
                    } else {
                        arrow = "↑";
                        c3 = 'x';
                    }
                    this.txt.setTextSize(40 * this.dn);
                    this.txt.setColor(extra ? Color.rgb(240, 181, 74) : Color.rgb(142, 151, 168));
                    if (i5 != this.road.getYou()) {
                        centerX = r.centerX();
                        f5 = 0.42f;
                    } else {
                        centerX = r.centerX();
                        f5 = 0.22f;
                    }
                    c.drawText(arrow, centerX, f5 * roadH, this.txt);
                    this.txt.setTextSize(20 * this.dn);
                    this.txt.setColor(i5 == this.road.getYou() ? Color.rgb(111, 227, 161) : Color.rgb(207, 214, 228));
                    c.drawText(i5 == this.road.getYou() ? (i5 + 1) + " · you" : String.valueOf(i5 + 1), r.centerX(), roadH - (18 * this.dn), this.txt);
                    if (extra) {
                        this.txt.setTextSize(15 * this.dn);
                        this.txt.setColor(Color.rgb(240, 181, 74));
                        c.drawText(t.get(i5).charValue() == c3 ? "EXIT" : "MERGE", r.centerX(), 24 * this.dn, this.txt);
                    }
                    if (i5 > 0) {
                        x2 = x;
                        dashed(c, x2, roadH, (t.get(i5).charValue() == t.get(i5 + (-1)).charValue() || (!extra && t.get(i5 + (-1)).charValue() == 'n')) ? Color.rgb(217, 221, 230) : -1);
                    } else {
                        x2 = x;
                    }
                    if (this.road.taggable(i5)) {
                        RectF tr = new RectF(r.left + (this.dn * f2), (this.dn * f3) + roadH, r.right - (this.dn * f2), (this.dn * f3) + roadH + getTagH());
                        this.tags.add(TuplesKt.to(Integer.valueOf(i5), tr));
                        Paint paint = this.p;
                        if (extra) {
                            i = 72;
                            i2 = 26;
                            i3 = 96;
                        } else {
                            i = 54;
                            i2 = 66;
                            i3 = 48;
                        }
                        paint.setColor(Color.rgb(i3, i, i2));
                        float f19 = 9;
                        c.drawRoundRect(tr, this.dn * f19, f19 * this.dn, this.p);
                        this.txt.setTextSize(this.dn * f4);
                        this.txt.setColor(extra ? Color.rgb(240, 181, 74) : Color.rgb(200, 206, 218));
                        switch (t.get(i5).charValue()) {
                            case 'm':
                                str2 = "MERGE ▸";
                                break;
                            case 'x':
                                str2 = "EXIT ▸";
                                break;
                            default:
                                str2 = "normal ▸";
                                break;
                        }
                        c.drawText(str2, tr.centerX(), tr.centerY() + (5 * this.dn), this.txt);
                    }
                    x5 = x2 + lw;
                    i5++;
                    otherN2 = otherN;
                    k2 = k;
                    str3 = str;
                    f9 = f4;
                } else {
                    float x6 = x5;
                    int otherN3 = otherN2;
                    this.p.setColor(Intrinsics.areEqual(this.road.getWay(), str3) ? Color.rgb(233, 194, 59) : otherN3 > 0 ? 0 : Color.rgb(240, 240, 240));
                    if (otherN3 == 0) {
                        float f20 = 3;
                        c.drawRect(x0 - (this.dn * f20), 0.0f, x0 + (f20 * this.dn), roadH, this.p);
                    }
                    this.p.setColor(Color.rgb(240, 240, 240));
                    float f21 = 3;
                    c.drawRect(x6 - (this.dn * f21), 0.0f, x6 + (f21 * this.dn), roadH, this.p);
                    return;
                }
            }
        }

        private final void dashed(Canvas c, float x, float h, int col) {
            Paint q = new Paint(1);
            q.setColor(col);
            q.setStrokeWidth(5 * this.dn);
            q.setPathEffect(new DashPathEffect(new float[]{18 * this.dn, 14 * this.dn}, 0.0f));
            q.setStyle(Paint.Style.STROKE);
            Path $this$dashed_u24lambda_u242 = new Path();
            float f = 6;
            $this$dashed_u24lambda_u242.moveTo(x, this.dn * f);
            $this$dashed_u24lambda_u242.lineTo(x, h - (f * this.dn));
            c.drawPath($this$dashed_u24lambda_u242, q);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            if (e.getAction() == 0) {
                return true;
            }
            if (e.getAction() != 1) {
                return false;
            }
            Iterator<Pair<Integer, RectF>> it = this.tags.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                Pair<Integer, RectF> next = it.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                Pair<Integer, RectF> pair = next;
                int i = pair.component1().intValue();
                RectF r = pair.component2();
                if (r.contains(e.getX(), e.getY())) {
                    this.road.cycle(i);
                    this.changed.invoke();
                    invalidate();
                    return true;
                }
            }
            int i2 = 0;
            for (RectF r2 : this.lanes) {
                int i3 = i2;
                i2++;
                if (r2.contains(e.getX(), e.getY())) {
                    this.road.setYou(this.road.getYou() == i3 ? -1 : i3);
                    this.changed.invoke();
                    invalidate();
                    performClick();
                    return true;
                }
            }
            return false;
        }

        @Override // android.view.View
        public boolean performClick() {
            super.performClick();
            return true;
        }
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;Type inference failed;incorrect) */
    public final void show(
        Context var1,
        String var2,
        int var3,
        String var4,
        int var5,
        boolean var6,
        int var7,
        Function0<Unit> var8,
        LaneFixDialog.Result var9,
        Bitmap var10,
        String var11,
        List<LaneRamps.Ramp> var12,
        Function1<? super String, Unit> var13,
        LaneFixDialog.Result var14,
        Function1<? super LaneFixDialog.Result, Unit> var15
    ) {
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "title");
        Intrinsics.checkNotNullParameter(var11, "pictureNote");
        Intrinsics.checkNotNullParameter(var12, "ramps");
        Intrinsics.checkNotNullParameter(var15, "onSave");
        float var16 = var1.getResources().getDisplayMetrics().density;
        LaneFixDialog.Road.Companion var20 = LaneFixDialog.Road.Companion;
        LaneFixDialog.Result var19;
        if (var14 == null) {
            var19 = var9;
        } else {
            var19 = var14;
        }

        if (var5 <= 0) {
            var5 = 3;
        }

        LaneFixDialog.Road var23 = var20.of(var19, var5);
        Dialog var63 = new Dialog(var1);
        var63.requestWindowFeature(1);
        var5 = Color.rgb(207, 214, 228);
        CheckBox var21 = new CheckBox(var1);
        CharSequence var55 = var4;
        boolean var18;
        if (var55 != null && var55.length() != 0) {
            var18 = false;
        } else {
            var18 = true;
        }

        label130: {
            if (!var18) {
                if (var9 != null) {
                    var6 = var9.getRemember();
                }

                if (var6) {
                    var6 = true;
                    break label130;
                }
            }

            var6 = false;
        }

        var21.setChecked(var6);
        CharSequence var56 = var4;
        if (var56 != null && var56.length() != 0) {
            var18 = false;
        } else {
            var18 = true;
        }

        var21.setEnabled(var18 ^ true);
        var21.setTextSize(15.0F);
        var21.setTextColor(Color.rgb(170, 178, 194));
        TextView var25 = show$label(var5, var1, var16, "", 22.0F, Color.rgb(111, 227, 161));
        var25.setTypeface(null, 1);
        LinearLayout var28 = new LinearLayout(var1);
        LinearLayout var27 = new LinearLayout(var1);
        var27.setGravity(16);
        ObjectRef var24 = new ObjectRef();
        ObjectRef var22 = new ObjectRef();
        var22.element = (new LaneFixDialog$$ExternalSyntheticLambda9(var28, var23, var27, var21, var25, var4, var22, var24, var1, var16, var5));
        var24.element = (new LaneFixDialog.RoadView(var1, var23, new LaneFixDialog$$ExternalSyntheticLambda11(var22)));
        LinearLayout var26 = new LinearLayout(var1);
        var26.addView((View)show$side(var1, var16, var23, var22, var24, var5, -1));
        LaneFixDialog.RoadView var39;
        if (var24.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("roadView");
            var39 = null;
        } else {
            var39 = (LaneFixDialog.RoadView)var24.element;
        }

        var26.addView(var39, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
        var26.addView((View)show$side(var1, var16, var23, var22, var24, var5, 1));
        LinearLayout var57 = new LinearLayout(var1);
        var57.setOrientation(1);
        var57.addView((View)var28);
        var57.addView((View)var27);
        if (var3 > 0) {
            var4 = "  (map says " + var3 + ")";
        } else {
            var4 = "";
        }

        String var66 = "Lanes going YOUR way. Tap the lane you're in.  + / − add or take away a lane." + var4;
        var57.addView((View)show$label$default(var5, var1, var16, var66, 16.0F, 0, 32, null));
        var57.addView((View)var26);
        var57.addView(
            (View)show$label(
                var5, var1, var16, "The tag under an edge lane: normal → EXIT → MERGE (a lane that's only there for a while).", 14.0F, Color.rgb(154, 163, 181)
            )
        );
        var57.addView((View)var25);
        var57.addView((View)var21);
        if (var7 > 0) {
            var57.addView((View)show$label(var5, var1, var16, "You saved " + var7 + " lanes for this stretch before.", 14.0F, Color.rgb(160, 168, 184)));
        }

        LinearLayout var64 = new LinearLayout(var1);
        var64.setOrientation(1);
        var64.setPadding(0, 0, show$dp(var16, 26), 0);
        if (var10 != null) {
            ZoomImageView var58 = new ZoomImageView(var1);
            var58.setImageBitmap(var10);
            float var17 = (float)var10.getHeight() / Math.max(1, var10.getWidth());
            var64.addView((View)var58, (android.view.ViewGroup.LayoutParams)(new LayoutParams(show$dp(var16, 560), (int)(show$dp(var16, 560) * var17))));
            boolean var37;
            if (var11.length() > 0) {
                var37 = true;
            } else {
                var37 = false;
            }

            if (var37) {
                var64.addView((View)show$label(var5, var1, var16, var11, 14.0F, Color.rgb(154, 163, 181)));
            }
        }

        var6 = var12.isEmpty();
        String var59 = "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams";
        LinearLayout var52;
        if (!var6) {
            Iterator var65 = var12.iterator();
            var3 = var5;
            var52 = var57;
            var4 = var59;

            while (var65.hasNext()) {
                LaneRamps.Ramp var67 = (LaneRamps.Ramp)var65.next();
                LinearLayout var60 = new LinearLayout(var1);
                var60.setGravity(16);
                String var69 = var67.text();
                var60.addView((View)show$label(var3, var1, var16, "Map: " + var69, 15.0F, Color.rgb(240, 181, 74)));
                TextView var70 = show$chip(var1, var16, "Add it", false, false, new LaneFixDialog$$ExternalSyntheticLambda12(var23, var67, var22, var24));
                android.view.ViewGroup.LayoutParams var68 = var70.getLayoutParams();
                Intrinsics.checkNotNull(var68, var4);
                ((android.widget.LinearLayout.LayoutParams)var68).setMarginStart(show$dp(var16, 12));
                var60.addView((View)var70);
                var64.addView((View)var60);
            }
        } else {
            var3 = var5;
            String var61 = "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams";
            var52 = var57;
            var4 = var61;
        }

        LinearLayout var62 = new LinearLayout(var1);
        var62.setPadding(0, show$dp(var16, 12), 0, 0);
        if (var9 != null) {
            var62.addView((View)show$chip(var1, var16, "Same as last", true, false, new LaneFixDialog$$ExternalSyntheticLambda13(var9, var21, var63, var15)));
        }

        if (var13 != null) {
            var62.addView((View)show$chip(var1, var16, "Skip", false, false, new LaneFixDialog$$ExternalSyntheticLambda14(var63, var13)));
            var62.addView((View)show$chip(var1, var16, "Intersection", false, false, new LaneFixDialog$$ExternalSyntheticLambda15(var63, var13)));
            var62.addView((View)show$chip(var1, var16, "Changing lanes", false, false, new LaneFixDialog$$ExternalSyntheticLambda16(var63, var13)));
        }

        if (var62.getChildCount() > 0) {
            var64.addView((View)var62);
        }

        if (var9 != null && var14 == null) {
            String var49 = var9.text();
            var64.addView((View)show$label(var3, var1, var16, "Started from your last fix: " + var49, 14.0F, Color.rgb(154, 163, 181)));
        }

        LinearLayout var50 = new LinearLayout(var1);
        if (var10 == null && var12.isEmpty()) {
            var50.setOrientation(1);
            if (var64.getChildCount() > 0) {
                var50.addView((View)var64);
            }

            var50.addView((View)var52);
        } else {
            var50.addView((View)var64, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-2, -2)));
            var50.addView((View)var52, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
        }

        LinearLayout var51 = new LinearLayout(var1);
        var51.setGravity(16);
        var51.setPadding(0, show$dp(var16, 14), 0, 0);
        if (var7 > 0 && var8 != null) {
            TextView var47 = show$bbtn(
                var1, var16, "Delete saved count", Color.rgb(48, 54, 66), Color.rgb(255, 150, 150), new LaneFixDialog$$ExternalSyntheticLambda17(var63, var8)
            );
            android.view.ViewGroup.LayoutParams var53 = var47.getLayoutParams();
            Intrinsics.checkNotNull(var53, var4);
            ((android.widget.LinearLayout.LayoutParams)var53).setMarginStart(0);
            var51.addView((View)var47);
        }

        var51.addView(new View(var1), (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, 1, 1.0F)));
        var51.addView((View)show$bbtn(var1, var16, "Cancel", Color.rgb(48, 54, 66), -1, new LaneFixDialog$$ExternalSyntheticLambda1(var63)));
        var51.addView(
            (View)show$bbtn(var1, var16, "Save", Color.rgb(111, 227, 161), -16777216, new LaneFixDialog$$ExternalSyntheticLambda2(var23, var21, var63, var15))
        );
        LinearLayout var43 = new LinearLayout(var1);
        var43.setOrientation(1);
        var43.setPadding(show$dp(var16, 28), show$dp(var16, 20), show$dp(var16, 28), show$dp(var16, 20));
        GradientDrawable var48 = new GradientDrawable();
        var48.setCornerRadius(22 * var16);
        var48.setColor(Color.rgb(27, 31, 39));
        var43.setBackground((Drawable)var48);
        TextView var31 = show$label(var3, var1, var16, var2, 24.0F, -1);
        var31.setTypeface(null, 1);
        var43.addView((View)var31);
        var43.addView((View)var50);
        var43.addView((View)var51);
        var21.setOnCheckedChangeListener(new LaneFixDialog$$ExternalSyntheticLambda10());
        Function0 var32;
        if (var22.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            var32 = null;
        } else {
            var32 = (Function0)var22.element;
        }

        var32.invoke();
        ScrollView var33 = new ScrollView(var1);
        var33.addView((View)var43);
        var63.setContentView((View)var33);
        Window var34 = var63.getWindow();
        if (var34 != null) {
            var34.setBackgroundDrawable((Drawable)(new ColorDrawable(0)));
            Unit var35 = Unit.INSTANCE;
        }

        var63.show();
        DisplayMetrics var29 = var1.getResources().getDisplayMetrics();
        Window var36 = var63.getWindow();
        if (var36 != null) {
            var36.setLayout((int)(var29.widthPixels * 0.96F), -2);
            Unit var30 = Unit.INSTANCE;
        }
    }

    private static final int show$dp(float d, int v) {
        return (int) (v * d);
    }

    private static final TextView show$label(int grey, Context $ctx, float d, String t, float size, int col) {
        TextView $this$show_u24label_u24lambda_u241 = new TextView($ctx);
        $this$show_u24label_u24lambda_u241.setText(t);
        $this$show_u24label_u24lambda_u241.setTextSize(size);
        $this$show_u24label_u24lambda_u241.setTextColor(col);
        $this$show_u24label_u24lambda_u241.setPadding(0, show$dp(d, 6), 0, show$dp(d, 6));
        return $this$show_u24label_u24lambda_u241;
    }

    static /* synthetic */ TextView show$label$default(int i, Context context, float f, String str, float f2, int i2, int i3, Object obj) {
        if ((i3 & 16) != 0) {
            f2 = 17.0f;
        }
        return show$label(i, context, f, str, f2, (i3 & 32) != 0 ? i : i2);
    }

    private static final TextView show$chip(Context $ctx, float d, String t, boolean on, boolean big, final Function0<Unit> function0) {
        int i;
        int i2;
        int i3;
        TextView $this$show_u24chip_u24lambda_u245 = new TextView($ctx);
        $this$show_u24chip_u24lambda_u245.setText(t);
        $this$show_u24chip_u24lambda_u245.setTextSize(big ? 18.0f : 15.0f);
        $this$show_u24chip_u24lambda_u245.setGravity(17);
        $this$show_u24chip_u24lambda_u245.setTypeface(null, 1);
        $this$show_u24chip_u24lambda_u245.setTextColor(on ? -16777216 : -1);
        GradientDrawable $this$show_u24chip_u24lambda_u245_u24lambda_u242 = new GradientDrawable();
        $this$show_u24chip_u24lambda_u245_u24lambda_u242.setCornerRadius(12 * d);
        if (on) {
            i = 227;
            i2 = 161;
            i3 = 111;
        } else {
            i = 54;
            i2 = 66;
            i3 = 48;
        }
        $this$show_u24chip_u24lambda_u245_u24lambda_u242.setColor(Color.rgb(i3, i, i2));
        $this$show_u24chip_u24lambda_u245.setBackground($this$show_u24chip_u24lambda_u245_u24lambda_u242);
        $this$show_u24chip_u24lambda_u245.setPadding(show$dp(d, big ? 20 : 14), show$dp(d, big ? 12 : 9), show$dp(d, big ? 20 : 14), show$dp(d, big ? 12 : 9));
        $this$show_u24chip_u24lambda_u245.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                function0.invoke();
            }
        });
        LinearLayout.LayoutParams $this$show_u24chip_u24lambda_u245_u24lambda_u244 = new LinearLayout.LayoutParams(-2, -2);
        $this$show_u24chip_u24lambda_u245_u24lambda_u244.setMarginEnd(show$dp(d, 10));
        $this$show_u24chip_u24lambda_u245.setLayoutParams($this$show_u24chip_u24lambda_u245_u24lambda_u244);
        return $this$show_u24chip_u24lambda_u245;
    }

    static /* synthetic */ TextView show$chip$default(Context context, float f, String str, boolean z, boolean z2, Function0 function0, int i, Object obj) {
        if ((i & 16) != 0) {
            z2 = true;
        }
        return show$chip(context, f, str, z, z2, function0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$lambda$11(LinearLayout $wayRow, final Road $road, LinearLayout $otherRow, CheckBox $keep, TextView $say, String $street, final Ref.ObjectRef $paint, final Ref.ObjectRef $roadView, Context $ctx, float $d, int $grey) {
        String str;
        $wayRow.removeAllViews();
        for (Pair pair : (Iterable<Pair>) CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("one", "One-way"), TuplesKt.to("divided", "Divided highway"), TuplesKt.to("two", "Two-way street")})) {
            final String k = (String) pair.component1();
            String t = (String) pair.component2();
            $wayRow.addView(show$chip$default($ctx, $d, t, Intrinsics.areEqual($road.getWay(), k), false, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit show$lambda$11$lambda$9;
                    show$lambda$11$lambda$9 = LaneFixDialog.show$lambda$11$lambda$9($road, k, $paint, $roadView);
                    return show$lambda$11$lambda$9;
                }
            }, 16, null));
        }
        $otherRow.removeAllViews();
        if (Intrinsics.areEqual($road.getWay(), "two")) {
            $otherRow.addView(show$label$default($grey, $ctx, $d, "Lanes the other way:  ", 15.0f, 0, 32, null));
            int k2 = 1;
            while (k2 < 5) {
                $otherRow.addView(show$chip($ctx, $d, String.valueOf(k2), $road.getOther() == k2, false, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit show$lambda$11$lambda$10;
                        show$lambda$11$lambda$10 = LaneFixDialog.show$lambda$11$lambda$10($road, k2, $paint, $roadView);
                        return show$lambda$11$lambda$10;
                    }
                }));
                k2++;
            }
        }
        Result r = $road.result($keep.isChecked());
        $say.setText(r.sentence() + (($road.getYou() >= 0 || $road.getTypes().size() <= 1) ? "" : "  -  tap your lane"));
        String str2 = $street;
        if (str2 == null || str2.length() == 0) {
            str = "No street - just for now";
        } else {
            str = "Remember " + $road.getThrough() + " lane" + ($road.getThrough() != 1 ? "s" : "") + " for this stretch of " + $street + " (merge / exit lanes are not remembered)";
        }
        $keep.setText(str);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    public static final Unit show$lambda$11$lambda$9(LaneFixDialog.Road var0, String var1, ObjectRef var2, ObjectRef var3) {
        if (Intrinsics.areEqual(var0.getWay(), var1)) {
            var1 = "";
        }

        var0.setWay(var1);
        if (!Intrinsics.areEqual(var0.getWay(), "two")) {
            var0.setOther(0);
        }

        Function0 var4 = ((Function0) var2.element);
        Object var7 = null;
        if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            var4 = null;
        } else {
            var4 = (Function0)var2.element;
        }

        var4.invoke();
        LaneFixDialog.RoadView var6;
        if (var3.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("roadView");
            var6 = (LaneFixDialog.RoadView)var7;
        } else {
            var6 = (LaneFixDialog.RoadView)var3.element;
        }

        var6.invalidate();
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    public static final Unit show$lambda$11$lambda$10(LaneFixDialog.Road var0, int var1, ObjectRef var2, ObjectRef var3) {
        if (var0.getOther() == var1) {
            var1 = 0;
        }

        var0.setOther(var1);
        Function0 var5 = ((Function0) var2.element);
        Object var4 = null;
        if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            var5 = null;
        } else {
            var5 = (Function0)var2.element;
        }

        var5.invoke();
        LaneFixDialog.RoadView var7;
        if (var3.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("roadView");
            var7 = (LaneFixDialog.RoadView)var4;
        } else {
            var7 = (LaneFixDialog.RoadView)var3.element;
        }

        var7.invalidate();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$lambda$12(Ref.ObjectRef $paint) {
        Function0 function0;
        if ($paint.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function0 = null;
        } else {
            function0 = (Function0) $paint.element;
        }
        function0.invoke();
        return Unit.INSTANCE;
    }

    private static final TextView show$pm(Context $ctx, float d, final Ref.ObjectRef<Function0<Unit>> objectRef, final Ref.ObjectRef<RoadView> objectRef2, String t, final Function0<Unit> function0) {
        TextView $this$show_u24pm_u24lambda_u2416 = new TextView($ctx);
        $this$show_u24pm_u24lambda_u2416.setText(t);
        $this$show_u24pm_u24lambda_u2416.setTextSize(28.0f);
        $this$show_u24pm_u24lambda_u2416.setGravity(17);
        $this$show_u24pm_u24lambda_u2416.setTypeface(null, 1);
        $this$show_u24pm_u24lambda_u2416.setTextColor(-1);
        GradientDrawable $this$show_u24pm_u24lambda_u2416_u24lambda_u2413 = new GradientDrawable();
        $this$show_u24pm_u24lambda_u2416_u24lambda_u2413.setCornerRadius(14 * d);
        $this$show_u24pm_u24lambda_u2416_u24lambda_u2413.setColor(Color.rgb(48, 54, 66));
        $this$show_u24pm_u24lambda_u2416.setBackground($this$show_u24pm_u24lambda_u2416_u24lambda_u2413);
        $this$show_u24pm_u24lambda_u2416.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LaneFixDialog.show$pm$lambda$16$lambda$14(function0, objectRef, objectRef2, view);
            }
        });
        LinearLayout.LayoutParams $this$show_u24pm_u24lambda_u2416_u24lambda_u2415 = new LinearLayout.LayoutParams(show$dp(d, 60), show$dp(d, 60));
        $this$show_u24pm_u24lambda_u2416_u24lambda_u2415.bottomMargin = show$dp(d, 10);
        $this$show_u24pm_u24lambda_u2416.setLayoutParams($this$show_u24pm_u24lambda_u2416_u24lambda_u2415);
        return $this$show_u24pm_u24lambda_u2416;
    }

    /* VF: from Vineflower (jadx: ) */
    public static final void show$pm$lambda$16$lambda$14(Function0 var0, ObjectRef var1, ObjectRef var2, View var3) {
        var0.invoke();
        Function0 var4 = ((Function0) var1.element);
        Object var7 = null;
        if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            var4 = null;
        } else {
            var4 = (Function0)var1.element;
        }

        var4.invoke();
        LaneFixDialog.RoadView var6;
        if (var2.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("roadView");
            var6 = (LaneFixDialog.RoadView)var7;
        } else {
            var6 = (LaneFixDialog.RoadView)var2.element;
        }

        var6.invalidate();
    }

    private static final LinearLayout show$side(Context $ctx, float d, final Road road, Ref.ObjectRef<Function0<Unit>> objectRef, Ref.ObjectRef<RoadView> objectRef2, int grey, final int s) {
        LinearLayout $this$show_u24side_u24lambda_u2420 = new LinearLayout($ctx);
        $this$show_u24side_u24lambda_u2420.setOrientation(1);
        $this$show_u24side_u24lambda_u2420.setGravity(1);
        $this$show_u24side_u24lambda_u2420.setPadding(show$dp(d, 8), show$dp(d, 40), show$dp(d, 8), 0);
        $this$show_u24side_u24lambda_u2420.addView(show$pm($ctx, d, objectRef, objectRef2, "+", new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit show$side$lambda$20$lambda$17;
                show$side$lambda$20$lambda$17 = LaneFixDialog.show$side$lambda$20$lambda$17(road, s);
                return show$side$lambda$20$lambda$17;
            }
        }));
        $this$show_u24side_u24lambda_u2420.addView(show$pm($ctx, d, objectRef, objectRef2, "−", new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit show$side$lambda$20$lambda$18;
                show$side$lambda$20$lambda$18 = LaneFixDialog.show$side$lambda$20$lambda$18(road, s);
                return show$side$lambda$20$lambda$18;
            }
        }));
        TextView $this$show_u24side_u24lambda_u2420_u24lambda_u2419 = show$label(grey, $ctx, d, s < 0 ? "left" : "right", 14.0f, Color.rgb(154, 163, 181));
        $this$show_u24side_u24lambda_u2420_u24lambda_u2419.setGravity(17);
        $this$show_u24side_u24lambda_u2420.addView($this$show_u24side_u24lambda_u2420_u24lambda_u2419);
        return $this$show_u24side_u24lambda_u2420;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$side$lambda$20$lambda$17(Road $road, int $s) {
        $road.add($s);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$side$lambda$20$lambda$18(Road $road, int $s) {
        $road.remove($s);
        return Unit.INSTANCE;
    }

    private static final void show$save(Dialog dlg, Function1<? super Result, Unit> function1, Result r) {
        dlg.dismiss();
        function1.invoke(r);
    }

    /* VF: from Vineflower (jadx: ) */
    public static final Unit show$lambda$26(LaneFixDialog.Road var0, LaneRamps.Ramp var1, ObjectRef var2, ObjectRef var3) {
        char var4;
        if (Intrinsics.areEqual(var1.getKind(), "exit")) {
            var4 = 'x';
        } else {
            var4 = 'm';
        }

        var0.addExtra(var4, var1.getSide());
        Function0 var5 = ((Function0) var2.element);
        Object var8 = null;
        if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            var5 = null;
        } else {
            var5 = (Function0)var2.element;
        }

        var5.invoke();
        LaneFixDialog.RoadView var7;
        if (var3.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("roadView");
            var7 = (LaneFixDialog.RoadView)var8;
        } else {
            var7 = (LaneFixDialog.RoadView)var3.element;
        }

        var7.invalidate();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$lambda$29(Result $last, CheckBox $keep, Dialog $dlg, Function1 $onSave) {
        show$save($dlg, $onSave, new Result($last.getThrough(), $last.getExtraL(), $last.getExtraR(), $last.getKind(), $last.getLane(), $keep.isChecked(), $last.getWay(), $last.getOtherWay(), $last.getKindR()));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$lambda$30(Dialog $dlg, Function1 $onSkip) {
        $dlg.dismiss();
        $onSkip.invoke("skip");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$lambda$31(Dialog $dlg, Function1 $onSkip) {
        $dlg.dismiss();
        $onSkip.invoke("intersection");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$lambda$32(Dialog $dlg, Function1 $onSkip) {
        $dlg.dismiss();
        $onSkip.invoke("lane_change");
        return Unit.INSTANCE;
    }

    private static final TextView show$bbtn(Context $ctx, float d, String t, int bg, int fg, final Function0<Unit> function0) {
        TextView $this$show_u24bbtn_u24lambda_u2438 = new TextView($ctx);
        $this$show_u24bbtn_u24lambda_u2438.setText(t);
        $this$show_u24bbtn_u24lambda_u2438.setTextSize(19.0f);
        $this$show_u24bbtn_u24lambda_u2438.setTypeface(null, 1);
        $this$show_u24bbtn_u24lambda_u2438.setGravity(17);
        $this$show_u24bbtn_u24lambda_u2438.setTextColor(fg);
        GradientDrawable $this$show_u24bbtn_u24lambda_u2438_u24lambda_u2435 = new GradientDrawable();
        $this$show_u24bbtn_u24lambda_u2438_u24lambda_u2435.setCornerRadius(14 * d);
        $this$show_u24bbtn_u24lambda_u2438_u24lambda_u2435.setColor(bg);
        $this$show_u24bbtn_u24lambda_u2438.setBackground($this$show_u24bbtn_u24lambda_u2438_u24lambda_u2435);
        $this$show_u24bbtn_u24lambda_u2438.setPadding(show$dp(d, 30), show$dp(d, 14), show$dp(d, 30), show$dp(d, 14));
        $this$show_u24bbtn_u24lambda_u2438.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                function0.invoke();
            }
        });
        LinearLayout.LayoutParams $this$show_u24bbtn_u24lambda_u2438_u24lambda_u2437 = new LinearLayout.LayoutParams(-2, -2);
        $this$show_u24bbtn_u24lambda_u2438_u24lambda_u2437.setMarginStart(show$dp(d, 14));
        $this$show_u24bbtn_u24lambda_u2438.setLayoutParams($this$show_u24bbtn_u24lambda_u2438_u24lambda_u2437);
        return $this$show_u24bbtn_u24lambda_u2438;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$lambda$39(Dialog $dlg, Function0 $onForget) {
        $dlg.dismiss();
        $onForget.invoke();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$lambda$41(Dialog $dlg) {
        $dlg.dismiss();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$lambda$42(Road $road, CheckBox $keep, Dialog $dlg, Function1 $onSave) {
        show$save($dlg, $onSave, $road.result($keep.isChecked()));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void show$lambda$46(CompoundButton compoundButton, boolean z) {
    }
}
