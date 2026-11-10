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

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x0023, code lost:
        
            if (r0.equals("one") == false) goto L16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:4:0x0010, code lost:
        
            if (r0.equals("divided") == false) goto L16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:5:0x0026, code lost:
        
            r0 = false;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Boolean getTwoWay() {
            String str = this.way;
            switch (str.hashCode()) {
                case 110182:
                    break;
                case 115276:
                    if (str.equals("two")) {
                        boolean z = true;
                        return Boolean.valueOf(z);
                    }
                    return null;
                case 1674318603:
                    break;
                default:
                    return null;
            }
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

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
        java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
         */
        public final String text() {
            String str;
            List e = CollectionsKt.listOfNotNull((Object[]) new String[]{text$ex(this.extraL, getKindLeft(), "left"), text$ex(this.extraR, getKindRight(), "right")});
            String base = (this.lane > 0 ? new StringBuilder().append("lane ").append(this.lane).append(" of ").append(getTotal()) : new StringBuilder().append(getTotal()).append(" lanes")).toString();
            String str2 = this.way;
            switch (str2.hashCode()) {
                case 110182:
                    if (str2.equals("one")) {
                        str = ", one-way";
                        break;
                    }
                    break;
                case 115276:
                    if (str2.equals("two")) {
                        str = ", two-way" + (this.otherWay > 0 ? " (" + this.otherWay + " the other way)" : "");
                        break;
                    }
                    break;
                case 1674318603:
                    if (str2.equals("divided")) {
                        str = ", divided highway";
                        break;
                    }
                    break;
            }
            String tw = str;
            return (e.isEmpty() ? base : base + " (" + this.through + " + " + CollectionsKt.joinToString$default(e, " + ", null, null, 0, null, null, 62, null) + ")") + tw;
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
            return e.isEmpty() ? base : base + ", plus " + CollectionsKt.joinToString$default(e, " and ", null, null, 0, null, null, 62, null);
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

        /* JADX WARN: Code restructure failed: missing block: B:6:0x001b, code lost:
        
            if (r6 != 0) goto L11;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Result result(boolean remember) {
            Character kl = (Character) CollectionsKt.firstOrNull((List) this.types);
            Character ch = null;
            if (kl != null) {
                char it = kl.charValue();
                char it2 = it != 'n' ? (char) 1 : (char) 0;
            }
            kl = null;
            Character ch2 = (Character) CollectionsKt.lastOrNull((List) this.types);
            if (ch2 != null) {
                char it3 = ch2.charValue();
                if (it3 != 'n') {
                    ch = ch2;
                }
            }
            Character kr = ch;
            String kind = kl != null ? result$k(kl) : result$k(kr);
            String kindR = (kl == null || kr == null || Intrinsics.areEqual(kr, kl)) ? "" : result$k(kr);
            int through = getThrough();
            int el = getEL();
            int er = getER();
            int size = this.types.size();
            int i = this.you;
            return new Result(through, el, er, kind, i >= 0 && i < size ? this.you + 1 : 0, remember, this.way, Intrinsics.areEqual(this.way, "two") ? this.other : 0, kindR);
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RoadView(Context ctx, Road road, Function0<Unit> changed) {
            super(ctx);
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(road, "road");
            Intrinsics.checkNotNullParameter(changed, "changed");
            this.road = road;
            this.changed = changed;
            this.dn = getResources().getDisplayMetrics().density;
            this.p = new Paint(1);
            Paint $this$txt_u24lambda_u240 = new Paint(1);
            $this$txt_u24lambda_u240.setTextAlign(Paint.Align.CENTER);
            $this$txt_u24lambda_u240.setTypeface(Typeface.DEFAULT_BOLD);
            this.txt = $this$txt_u24lambda_u240;
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x03db  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0429  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x04da  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x05f2  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0619  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0637  */
    /* JADX WARN: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x05f9  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0483  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0393  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0160  */
    /* JADX WARN: Type inference failed for: r0v18, types: [T, com.starpilot.starview.LaneFixDialog$RoadView] */
    /* JADX WARN: Type inference failed for: r1v9, types: [T, com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda9] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void show(final Context ctx, String title, int mapSays, final String street, int preset, boolean rememberDefault, int saved, final Function0<Unit> onForget, final Result last, Bitmap picture, String pictureNote, List<LaneRamps.Ramp> ramps, final Function1<? super String, Unit> onSkip, Result start, final Function1<? super Result, Unit> onSave) {
        boolean z;
        final Ref.ObjectRef roadView;
        RoadView roadView2;
        int i;
        LinearLayout left;
        float d;
        LinearLayout right;
        int grey;
        String str;
        LinearLayout $this$show_u24lambda_u2428;
        final Dialog dlg;
        Ref.ObjectRef paint;
        LinearLayout $this$show_u24lambda_u2433;
        LinearLayout $this$show_u24lambda_u2434;
        LinearLayout bottom;
        Ref.ObjectRef paint2;
        Function0 function0;
        Window window;
        Window window2;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(pictureNote, "pictureNote");
        Intrinsics.checkNotNullParameter(ramps, "ramps");
        Intrinsics.checkNotNullParameter(onSave, "onSave");
        final float d2 = ctx.getResources().getDisplayMetrics().density;
        final Road road = Road.Companion.of(start == null ? last : start, preset > 0 ? preset : 3);
        final Dialog $this$show_u24lambda_u240 = new Dialog(ctx);
        $this$show_u24lambda_u240.requestWindowFeature(1);
        final int grey2 = Color.rgb(207, 214, 228);
        final CheckBox $this$show_u24lambda_u246 = new CheckBox(ctx);
        String str2 = street;
        if (!(str2 == null || str2.length() == 0)) {
            if (last != null ? last.getRemember() : rememberDefault) {
                z = true;
                $this$show_u24lambda_u246.setChecked(z);
                String str3 = street;
                $this$show_u24lambda_u246.setEnabled(!(str3 != null || str3.length() == 0));
                $this$show_u24lambda_u246.setTextSize(15.0f);
                $this$show_u24lambda_u246.setTextColor(Color.rgb(170, 178, 194));
                final TextView $this$show_u24lambda_u247 = show$label(grey2, ctx, d2, "", 22.0f, Color.rgb(111, 227, 161));
                $this$show_u24lambda_u247.setTypeface(null, 1);
                final LinearLayout wayRow = new LinearLayout(ctx);
                final LinearLayout $this$show_u24lambda_u248 = new LinearLayout(ctx);
                $this$show_u24lambda_u248.setGravity(16);
                roadView = new Ref.ObjectRef();
                final Ref.ObjectRef paint3 = new Ref.ObjectRef();
                paint3.element = new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda9
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit show$lambda$11;
                        show$lambda$11 = LaneFixDialog.show$lambda$11(wayRow, road, $this$show_u24lambda_u248, $this$show_u24lambda_u246, $this$show_u24lambda_u247, street, paint3, roadView, ctx, d2, grey2);
                        return show$lambda$11;
                    }
                };
                roadView.element = new RoadView(ctx, road, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda11
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit show$lambda$12;
                        show$lambda$12 = LaneFixDialog.show$lambda$12(Ref.ObjectRef.this);
                        return show$lambda$12;
                    }
                });
                LinearLayout $this$show_u24lambda_u2421 = new LinearLayout(ctx);
                $this$show_u24lambda_u2421.addView(show$side(ctx, d2, road, paint3, roadView, grey2, -1));
                if (roadView.element != 0) {
                    Intrinsics.throwUninitializedPropertyAccessException("roadView");
                    roadView2 = null;
                } else {
                    roadView2 = (RoadView) roadView.element;
                }
                $this$show_u24lambda_u2421.addView(roadView2, new LinearLayout.LayoutParams(0, -2, 1.0f));
                $this$show_u24lambda_u2421.addView(show$side(ctx, d2, road, paint3, roadView, grey2, 1));
                LinearLayout $this$show_u24lambda_u2422 = new LinearLayout(ctx);
                $this$show_u24lambda_u2422.setOrientation(1);
                $this$show_u24lambda_u2422.addView(wayRow);
                $this$show_u24lambda_u2422.addView($this$show_u24lambda_u248);
                LinearLayout right2 = $this$show_u24lambda_u2422;
                right2.addView(show$label$default(grey2, ctx, d2, "Lanes going YOUR way. Tap the lane you're in.  + / − add or take away a lane." + (mapSays <= 0 ? "  (map says " + mapSays + ")" : ""), 16.0f, 0, 32, null));
                right2.addView($this$show_u24lambda_u2421);
                int i2 = 181;
                right2.addView(show$label(grey2, ctx, d2, "The tag under an edge lane: normal → EXIT → MERGE (a lane that's only there for a while).", 14.0f, Color.rgb(154, 163, 181)));
                right2.addView($this$show_u24lambda_u247);
                right2.addView($this$show_u24lambda_u246);
                if (saved <= 0) {
                    i = 0;
                    right2.addView(show$label(grey2, ctx, d2, "You saved " + saved + " lanes for this stretch before.", 14.0f, Color.rgb(160, 168, 184)));
                } else {
                    i = 0;
                }
                LinearLayout $this$show_u24lambda_u2423 = new LinearLayout(ctx);
                $this$show_u24lambda_u2423.setOrientation(1);
                $this$show_u24lambda_u2423.setPadding(i, i, show$dp(d2, 26), i);
                if (picture == null) {
                    ZoomImageView $this$show_u24lambda_u2424 = new ZoomImageView(ctx);
                    $this$show_u24lambda_u2424.setImageBitmap(picture);
                    float ratio = picture.getHeight() / Math.max(1, picture.getWidth());
                    $this$show_u24lambda_u2423.addView($this$show_u24lambda_u2424, new LinearLayout.LayoutParams(show$dp(d2, 560), (int) (show$dp(d2, 560) * ratio)));
                    if (pictureNote.length() > 0) {
                        left = $this$show_u24lambda_u2423;
                        d = d2;
                        left.addView(show$label(grey2, ctx, d2, pictureNote, 14.0f, Color.rgb(154, 163, 181)));
                    } else {
                        left = $this$show_u24lambda_u2423;
                        d = d2;
                    }
                } else {
                    left = $this$show_u24lambda_u2423;
                    d = d2;
                }
                String str4 = "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams";
                if (ramps.isEmpty()) {
                    for (final LaneRamps.Ramp rp : ramps) {
                        LinearLayout $this$show_u24lambda_u2425 = new LinearLayout(ctx);
                        $this$show_u24lambda_u2425.setGravity(16);
                        int grey3 = grey2;
                        String str5 = str4;
                        $this$show_u24lambda_u2425.addView(show$label(grey2, ctx, d, "Map: " + rp.text(), 15.0f, Color.rgb(240, i2, 74)));
                        TextView $this$show_u24lambda_u2427 = show$chip(ctx, d, "Add it", false, false, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda12
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit show$lambda$26;
                                show$lambda$26 = LaneFixDialog.show$lambda$26(LaneFixDialog.Road.this, rp, paint3, roadView);
                                return show$lambda$26;
                            }
                        });
                        ViewGroup.LayoutParams layoutParams = $this$show_u24lambda_u2427.getLayoutParams();
                        Intrinsics.checkNotNull(layoutParams, str5);
                        ((LinearLayout.LayoutParams) layoutParams).setMarginStart(show$dp(d, 12));
                        $this$show_u24lambda_u2425.addView($this$show_u24lambda_u2427);
                        left.addView($this$show_u24lambda_u2425);
                        str4 = str5;
                        right2 = right2;
                        grey2 = grey3;
                        i2 = 181;
                    }
                    right = right2;
                    grey = grey2;
                    str = str4;
                } else {
                    right = right2;
                    grey = grey2;
                    str = "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams";
                }
                $this$show_u24lambda_u2428 = new LinearLayout(ctx);
                $this$show_u24lambda_u2428.setPadding(0, show$dp(d, 12), 0, 0);
                if (last == null) {
                    dlg = $this$show_u24lambda_u240;
                    $this$show_u24lambda_u2428.addView(show$chip(ctx, d, "Same as last", true, false, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda13
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit show$lambda$29;
                            show$lambda$29 = LaneFixDialog.show$lambda$29(LaneFixDialog.Result.this, $this$show_u24lambda_u246, $this$show_u24lambda_u240, onSave);
                            return show$lambda$29;
                        }
                    }));
                } else {
                    dlg = $this$show_u24lambda_u240;
                }
                if (onSkip == null) {
                    float f = d;
                    paint = paint3;
                    $this$show_u24lambda_u2428.addView(show$chip(ctx, f, "Skip", false, false, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda14
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit show$lambda$30;
                            show$lambda$30 = LaneFixDialog.show$lambda$30(dlg, onSkip);
                            return show$lambda$30;
                        }
                    }));
                    $this$show_u24lambda_u2428.addView(show$chip(ctx, f, "Intersection", false, false, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda15
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit show$lambda$31;
                            show$lambda$31 = LaneFixDialog.show$lambda$31(dlg, onSkip);
                            return show$lambda$31;
                        }
                    }));
                    $this$show_u24lambda_u2428.addView(show$chip(ctx, f, "Changing lanes", false, false, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda16
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit show$lambda$32;
                            show$lambda$32 = LaneFixDialog.show$lambda$32(dlg, onSkip);
                            return show$lambda$32;
                        }
                    }));
                } else {
                    paint = paint3;
                }
                if ($this$show_u24lambda_u2428.getChildCount() > 0) {
                    left.addView($this$show_u24lambda_u2428);
                }
                if (last != null && start == null) {
                    left.addView(show$label(grey, ctx, d, "Started from your last fix: " + last.text(), 14.0f, Color.rgb(154, 163, 181)));
                }
                $this$show_u24lambda_u2433 = new LinearLayout(ctx);
                if (picture == null && ramps.isEmpty()) {
                    $this$show_u24lambda_u2433.setOrientation(1);
                    if (left.getChildCount() > 0) {
                        $this$show_u24lambda_u2433.addView(left);
                    }
                    $this$show_u24lambda_u2433.addView(right);
                    $this$show_u24lambda_u2434 = new LinearLayout(ctx);
                    $this$show_u24lambda_u2434.setGravity(16);
                    $this$show_u24lambda_u2434.setPadding(0, show$dp(d, 14), 0, 0);
                    if (saved > 0 || onForget == null) {
                        bottom = $this$show_u24lambda_u2434;
                    } else {
                        bottom = $this$show_u24lambda_u2434;
                        TextView $this$show_u24lambda_u2440 = show$bbtn(ctx, d, "Delete saved count", Color.rgb(48, 54, 66), Color.rgb(255, 150, 150), new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda17
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit show$lambda$39;
                                show$lambda$39 = LaneFixDialog.show$lambda$39(dlg, onForget);
                                return show$lambda$39;
                            }
                        });
                        ViewGroup.LayoutParams layoutParams2 = $this$show_u24lambda_u2440.getLayoutParams();
                        Intrinsics.checkNotNull(layoutParams2, str);
                        ((LinearLayout.LayoutParams) layoutParams2).setMarginStart(0);
                        bottom.addView($this$show_u24lambda_u2440);
                    }
                    bottom.addView(new View(ctx), new LinearLayout.LayoutParams(0, 1, 1.0f));
                    bottom.addView(show$bbtn(ctx, d, "Cancel", Color.rgb(48, 54, 66), -1, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit show$lambda$41;
                            show$lambda$41 = LaneFixDialog.show$lambda$41(dlg);
                            return show$lambda$41;
                        }
                    }));
                    bottom.addView(show$bbtn(ctx, d, "Save", Color.rgb(111, 227, 161), -16777216, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit show$lambda$42;
                            show$lambda$42 = LaneFixDialog.show$lambda$42(LaneFixDialog.Road.this, $this$show_u24lambda_u246, dlg, onSave);
                            return show$lambda$42;
                        }
                    }));
                    LinearLayout $this$show_u24lambda_u2445 = new LinearLayout(ctx);
                    $this$show_u24lambda_u2445.setOrientation(1);
                    $this$show_u24lambda_u2445.setPadding(show$dp(d, 28), show$dp(d, 20), show$dp(d, 28), show$dp(d, 20));
                    GradientDrawable $this$show_u24lambda_u2445_u24lambda_u2443 = new GradientDrawable();
                    $this$show_u24lambda_u2445_u24lambda_u2443.setCornerRadius(22 * d);
                    $this$show_u24lambda_u2445_u24lambda_u2443.setColor(Color.rgb(27, 31, 39));
                    $this$show_u24lambda_u2445.setBackground($this$show_u24lambda_u2445_u24lambda_u2443);
                    TextView $this$show_u24lambda_u2445_u24lambda_u2444 = show$label(grey, ctx, d, title, 24.0f, -1);
                    $this$show_u24lambda_u2445_u24lambda_u2444.setTypeface(null, 1);
                    $this$show_u24lambda_u2445.addView($this$show_u24lambda_u2445_u24lambda_u2444);
                    $this$show_u24lambda_u2445.addView($this$show_u24lambda_u2433);
                    $this$show_u24lambda_u2445.addView(bottom);
                    $this$show_u24lambda_u246.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda10
                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                        public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                            LaneFixDialog.show$lambda$46(compoundButton, z2);
                        }
                    });
                    paint2 = paint;
                    if (paint2.element != 0) {
                        Intrinsics.throwUninitializedPropertyAccessException("paint");
                        function0 = null;
                    } else {
                        function0 = (Function0) paint2.element;
                    }
                    function0.invoke();
                    ScrollView $this$show_u24lambda_u2447 = new ScrollView(ctx);
                    $this$show_u24lambda_u2447.addView($this$show_u24lambda_u2445);
                    dlg.setContentView($this$show_u24lambda_u2447);
                    window = dlg.getWindow();
                    if (window != null) {
                        window.setBackgroundDrawable(new ColorDrawable(0));
                        Unit unit = Unit.INSTANCE;
                    }
                    dlg.show();
                    DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
                    window2 = dlg.getWindow();
                    if (window2 == null) {
                        window2.setLayout((int) (dm.widthPixels * 0.96f), -2);
                        Unit unit2 = Unit.INSTANCE;
                        return;
                    }
                    return;
                }
                $this$show_u24lambda_u2433.addView(left, new LinearLayout.LayoutParams(-2, -2));
                $this$show_u24lambda_u2433.addView(right, new LinearLayout.LayoutParams(0, -2, 1.0f));
                $this$show_u24lambda_u2434 = new LinearLayout(ctx);
                $this$show_u24lambda_u2434.setGravity(16);
                $this$show_u24lambda_u2434.setPadding(0, show$dp(d, 14), 0, 0);
                if (saved > 0) {
                }
                bottom = $this$show_u24lambda_u2434;
                bottom.addView(new View(ctx), new LinearLayout.LayoutParams(0, 1, 1.0f));
                bottom.addView(show$bbtn(ctx, d, "Cancel", Color.rgb(48, 54, 66), -1, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit show$lambda$41;
                        show$lambda$41 = LaneFixDialog.show$lambda$41(dlg);
                        return show$lambda$41;
                    }
                }));
                bottom.addView(show$bbtn(ctx, d, "Save", Color.rgb(111, 227, 161), -16777216, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit show$lambda$42;
                        show$lambda$42 = LaneFixDialog.show$lambda$42(LaneFixDialog.Road.this, $this$show_u24lambda_u246, dlg, onSave);
                        return show$lambda$42;
                    }
                }));
                LinearLayout $this$show_u24lambda_u24452 = new LinearLayout(ctx);
                $this$show_u24lambda_u24452.setOrientation(1);
                $this$show_u24lambda_u24452.setPadding(show$dp(d, 28), show$dp(d, 20), show$dp(d, 28), show$dp(d, 20));
                GradientDrawable $this$show_u24lambda_u2445_u24lambda_u24432 = new GradientDrawable();
                $this$show_u24lambda_u2445_u24lambda_u24432.setCornerRadius(22 * d);
                $this$show_u24lambda_u2445_u24lambda_u24432.setColor(Color.rgb(27, 31, 39));
                $this$show_u24lambda_u24452.setBackground($this$show_u24lambda_u2445_u24lambda_u24432);
                TextView $this$show_u24lambda_u2445_u24lambda_u24442 = show$label(grey, ctx, d, title, 24.0f, -1);
                $this$show_u24lambda_u2445_u24lambda_u24442.setTypeface(null, 1);
                $this$show_u24lambda_u24452.addView($this$show_u24lambda_u2445_u24lambda_u24442);
                $this$show_u24lambda_u24452.addView($this$show_u24lambda_u2433);
                $this$show_u24lambda_u24452.addView(bottom);
                $this$show_u24lambda_u246.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda10
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                        LaneFixDialog.show$lambda$46(compoundButton, z2);
                    }
                });
                paint2 = paint;
                if (paint2.element != 0) {
                }
                function0.invoke();
                ScrollView $this$show_u24lambda_u24472 = new ScrollView(ctx);
                $this$show_u24lambda_u24472.addView($this$show_u24lambda_u24452);
                dlg.setContentView($this$show_u24lambda_u24472);
                window = dlg.getWindow();
                if (window != null) {
                }
                dlg.show();
                DisplayMetrics dm2 = ctx.getResources().getDisplayMetrics();
                window2 = dlg.getWindow();
                if (window2 == null) {
                }
            }
        }
        z = false;
        $this$show_u24lambda_u246.setChecked(z);
        String str32 = street;
        $this$show_u24lambda_u246.setEnabled(!(str32 != null || str32.length() == 0));
        $this$show_u24lambda_u246.setTextSize(15.0f);
        $this$show_u24lambda_u246.setTextColor(Color.rgb(170, 178, 194));
        final TextView $this$show_u24lambda_u2472 = show$label(grey2, ctx, d2, "", 22.0f, Color.rgb(111, 227, 161));
        $this$show_u24lambda_u2472.setTypeface(null, 1);
        final LinearLayout wayRow2 = new LinearLayout(ctx);
        final LinearLayout $this$show_u24lambda_u2482 = new LinearLayout(ctx);
        $this$show_u24lambda_u2482.setGravity(16);
        roadView = new Ref.ObjectRef();
        final Ref.ObjectRef paint32 = new Ref.ObjectRef();
        paint32.element = new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit show$lambda$11;
                show$lambda$11 = LaneFixDialog.show$lambda$11(wayRow2, road, $this$show_u24lambda_u2482, $this$show_u24lambda_u246, $this$show_u24lambda_u2472, street, paint32, roadView, ctx, d2, grey2);
                return show$lambda$11;
            }
        };
        roadView.element = new RoadView(ctx, road, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit show$lambda$12;
                show$lambda$12 = LaneFixDialog.show$lambda$12(Ref.ObjectRef.this);
                return show$lambda$12;
            }
        });
        LinearLayout $this$show_u24lambda_u24212 = new LinearLayout(ctx);
        $this$show_u24lambda_u24212.addView(show$side(ctx, d2, road, paint32, roadView, grey2, -1));
        if (roadView.element != 0) {
        }
        $this$show_u24lambda_u24212.addView(roadView2, new LinearLayout.LayoutParams(0, -2, 1.0f));
        $this$show_u24lambda_u24212.addView(show$side(ctx, d2, road, paint32, roadView, grey2, 1));
        LinearLayout $this$show_u24lambda_u24222 = new LinearLayout(ctx);
        $this$show_u24lambda_u24222.setOrientation(1);
        $this$show_u24lambda_u24222.addView(wayRow2);
        $this$show_u24lambda_u24222.addView($this$show_u24lambda_u2482);
        LinearLayout right22 = $this$show_u24lambda_u24222;
        right22.addView(show$label$default(grey2, ctx, d2, "Lanes going YOUR way. Tap the lane you're in.  + / − add or take away a lane." + (mapSays <= 0 ? "  (map says " + mapSays + ")" : ""), 16.0f, 0, 32, null));
        right22.addView($this$show_u24lambda_u24212);
        int i22 = 181;
        right22.addView(show$label(grey2, ctx, d2, "The tag under an edge lane: normal → EXIT → MERGE (a lane that's only there for a while).", 14.0f, Color.rgb(154, 163, 181)));
        right22.addView($this$show_u24lambda_u2472);
        right22.addView($this$show_u24lambda_u246);
        if (saved <= 0) {
        }
        LinearLayout $this$show_u24lambda_u24232 = new LinearLayout(ctx);
        $this$show_u24lambda_u24232.setOrientation(1);
        $this$show_u24lambda_u24232.setPadding(i, i, show$dp(d2, 26), i);
        if (picture == null) {
        }
        String str42 = "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams";
        if (ramps.isEmpty()) {
        }
        $this$show_u24lambda_u2428 = new LinearLayout(ctx);
        $this$show_u24lambda_u2428.setPadding(0, show$dp(d, 12), 0, 0);
        if (last == null) {
        }
        if (onSkip == null) {
        }
        if ($this$show_u24lambda_u2428.getChildCount() > 0) {
        }
        if (last != null) {
            left.addView(show$label(grey, ctx, d, "Started from your last fix: " + last.text(), 14.0f, Color.rgb(154, 163, 181)));
        }
        $this$show_u24lambda_u2433 = new LinearLayout(ctx);
        if (picture == null) {
            $this$show_u24lambda_u2433.setOrientation(1);
            if (left.getChildCount() > 0) {
            }
            $this$show_u24lambda_u2433.addView(right);
            $this$show_u24lambda_u2434 = new LinearLayout(ctx);
            $this$show_u24lambda_u2434.setGravity(16);
            $this$show_u24lambda_u2434.setPadding(0, show$dp(d, 14), 0, 0);
            if (saved > 0) {
            }
            bottom = $this$show_u24lambda_u2434;
            bottom.addView(new View(ctx), new LinearLayout.LayoutParams(0, 1, 1.0f));
            bottom.addView(show$bbtn(ctx, d, "Cancel", Color.rgb(48, 54, 66), -1, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit show$lambda$41;
                    show$lambda$41 = LaneFixDialog.show$lambda$41(dlg);
                    return show$lambda$41;
                }
            }));
            bottom.addView(show$bbtn(ctx, d, "Save", Color.rgb(111, 227, 161), -16777216, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit show$lambda$42;
                    show$lambda$42 = LaneFixDialog.show$lambda$42(LaneFixDialog.Road.this, $this$show_u24lambda_u246, dlg, onSave);
                    return show$lambda$42;
                }
            }));
            LinearLayout $this$show_u24lambda_u244522 = new LinearLayout(ctx);
            $this$show_u24lambda_u244522.setOrientation(1);
            $this$show_u24lambda_u244522.setPadding(show$dp(d, 28), show$dp(d, 20), show$dp(d, 28), show$dp(d, 20));
            GradientDrawable $this$show_u24lambda_u2445_u24lambda_u244322 = new GradientDrawable();
            $this$show_u24lambda_u2445_u24lambda_u244322.setCornerRadius(22 * d);
            $this$show_u24lambda_u2445_u24lambda_u244322.setColor(Color.rgb(27, 31, 39));
            $this$show_u24lambda_u244522.setBackground($this$show_u24lambda_u2445_u24lambda_u244322);
            TextView $this$show_u24lambda_u2445_u24lambda_u244422 = show$label(grey, ctx, d, title, 24.0f, -1);
            $this$show_u24lambda_u2445_u24lambda_u244422.setTypeface(null, 1);
            $this$show_u24lambda_u244522.addView($this$show_u24lambda_u2445_u24lambda_u244422);
            $this$show_u24lambda_u244522.addView($this$show_u24lambda_u2433);
            $this$show_u24lambda_u244522.addView(bottom);
            $this$show_u24lambda_u246.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda10
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    LaneFixDialog.show$lambda$46(compoundButton, z2);
                }
            });
            paint2 = paint;
            if (paint2.element != 0) {
            }
            function0.invoke();
            ScrollView $this$show_u24lambda_u244722 = new ScrollView(ctx);
            $this$show_u24lambda_u244722.addView($this$show_u24lambda_u244522);
            dlg.setContentView($this$show_u24lambda_u244722);
            window = dlg.getWindow();
            if (window != null) {
            }
            dlg.show();
            DisplayMetrics dm22 = ctx.getResources().getDisplayMetrics();
            window2 = dlg.getWindow();
            if (window2 == null) {
            }
        }
        $this$show_u24lambda_u2433.addView(left, new LinearLayout.LayoutParams(-2, -2));
        $this$show_u24lambda_u2433.addView(right, new LinearLayout.LayoutParams(0, -2, 1.0f));
        $this$show_u24lambda_u2434 = new LinearLayout(ctx);
        $this$show_u24lambda_u2434.setGravity(16);
        $this$show_u24lambda_u2434.setPadding(0, show$dp(d, 14), 0, 0);
        if (saved > 0) {
        }
        bottom = $this$show_u24lambda_u2434;
        bottom.addView(new View(ctx), new LinearLayout.LayoutParams(0, 1, 1.0f));
        bottom.addView(show$bbtn(ctx, d, "Cancel", Color.rgb(48, 54, 66), -1, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit show$lambda$41;
                show$lambda$41 = LaneFixDialog.show$lambda$41(dlg);
                return show$lambda$41;
            }
        }));
        bottom.addView(show$bbtn(ctx, d, "Save", Color.rgb(111, 227, 161), -16777216, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit show$lambda$42;
                show$lambda$42 = LaneFixDialog.show$lambda$42(LaneFixDialog.Road.this, $this$show_u24lambda_u246, dlg, onSave);
                return show$lambda$42;
            }
        }));
        LinearLayout $this$show_u24lambda_u2445222 = new LinearLayout(ctx);
        $this$show_u24lambda_u2445222.setOrientation(1);
        $this$show_u24lambda_u2445222.setPadding(show$dp(d, 28), show$dp(d, 20), show$dp(d, 28), show$dp(d, 20));
        GradientDrawable $this$show_u24lambda_u2445_u24lambda_u2443222 = new GradientDrawable();
        $this$show_u24lambda_u2445_u24lambda_u2443222.setCornerRadius(22 * d);
        $this$show_u24lambda_u2445_u24lambda_u2443222.setColor(Color.rgb(27, 31, 39));
        $this$show_u24lambda_u2445222.setBackground($this$show_u24lambda_u2445_u24lambda_u2443222);
        TextView $this$show_u24lambda_u2445_u24lambda_u2444222 = show$label(grey, ctx, d, title, 24.0f, -1);
        $this$show_u24lambda_u2445_u24lambda_u2444222.setTypeface(null, 1);
        $this$show_u24lambda_u2445222.addView($this$show_u24lambda_u2445_u24lambda_u2444222);
        $this$show_u24lambda_u2445222.addView($this$show_u24lambda_u2433);
        $this$show_u24lambda_u2445222.addView(bottom);
        $this$show_u24lambda_u246.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda10
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                LaneFixDialog.show$lambda$46(compoundButton, z2);
            }
        });
        paint2 = paint;
        if (paint2.element != 0) {
        }
        function0.invoke();
        ScrollView $this$show_u24lambda_u2447222 = new ScrollView(ctx);
        $this$show_u24lambda_u2447222.addView($this$show_u24lambda_u2445222);
        dlg.setContentView($this$show_u24lambda_u2447222);
        window = dlg.getWindow();
        if (window != null) {
        }
        dlg.show();
        DisplayMetrics dm222 = ctx.getResources().getDisplayMetrics();
        window2 = dlg.getWindow();
        if (window2 == null) {
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
                Function0.this.invoke();
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
        for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("one", "One-way"), TuplesKt.to("divided", "Divided highway"), TuplesKt.to("two", "Two-way street")})) {
            final String k = (String) pair.component1();
            String t = (String) pair.component2();
            $wayRow.addView(show$chip$default($ctx, $d, t, Intrinsics.areEqual($road.getWay(), k), false, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit show$lambda$11$lambda$9;
                    show$lambda$11$lambda$9 = LaneFixDialog.show$lambda$11$lambda$9(LaneFixDialog.Road.this, k, $paint, $roadView);
                    return show$lambda$11$lambda$9;
                }
            }, 16, null));
        }
        $otherRow.removeAllViews();
        if (Intrinsics.areEqual($road.getWay(), "two")) {
            $otherRow.addView(show$label$default($grey, $ctx, $d, "Lanes the other way:  ", 15.0f, 0, 32, null));
            final int k2 = 1;
            while (k2 < 5) {
                $otherRow.addView(show$chip($ctx, $d, String.valueOf(k2), $road.getOther() == k2, false, new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit show$lambda$11$lambda$10;
                        show$lambda$11$lambda$10 = LaneFixDialog.show$lambda$11$lambda$10(LaneFixDialog.Road.this, k2, $paint, $roadView);
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Unit show$lambda$11$lambda$9(Road $road, String $k, Ref.ObjectRef $paint, Ref.ObjectRef $roadView) {
        Function0 function0;
        $road.setWay(Intrinsics.areEqual($road.getWay(), $k) ? "" : $k);
        if (!Intrinsics.areEqual($road.getWay(), "two")) {
            $road.setOther(0);
        }
        RoadView roadView = null;
        if ($paint.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function0 = null;
        } else {
            function0 = (Function0) $paint.element;
        }
        function0.invoke();
        if ($roadView.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("roadView");
        } else {
            roadView = (RoadView) $roadView.element;
        }
        roadView.invalidate();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Unit show$lambda$11$lambda$10(Road $road, int $k, Ref.ObjectRef $paint, Ref.ObjectRef $roadView) {
        Function0 function0;
        $road.setOther($road.getOther() == $k ? 0 : $k);
        RoadView roadView = null;
        if ($paint.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function0 = null;
        } else {
            function0 = (Function0) $paint.element;
        }
        function0.invoke();
        if ($roadView.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("roadView");
        } else {
            roadView = (RoadView) $roadView.element;
        }
        roadView.invalidate();
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
                LaneFixDialog.show$pm$lambda$16$lambda$14(Function0.this, objectRef, objectRef2, view);
            }
        });
        LinearLayout.LayoutParams $this$show_u24pm_u24lambda_u2416_u24lambda_u2415 = new LinearLayout.LayoutParams(show$dp(d, 60), show$dp(d, 60));
        $this$show_u24pm_u24lambda_u2416_u24lambda_u2415.bottomMargin = show$dp(d, 10);
        $this$show_u24pm_u24lambda_u2416.setLayoutParams($this$show_u24pm_u24lambda_u2416_u24lambda_u2415);
        return $this$show_u24pm_u24lambda_u2416;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void show$pm$lambda$16$lambda$14(Function0 $click, Ref.ObjectRef $paint, Ref.ObjectRef $roadView, View it) {
        Function0 function0;
        $click.invoke();
        RoadView roadView = null;
        if ($paint.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function0 = null;
        } else {
            function0 = (Function0) $paint.element;
        }
        function0.invoke();
        if ($roadView.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("roadView");
        } else {
            roadView = (RoadView) $roadView.element;
        }
        roadView.invalidate();
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
                show$side$lambda$20$lambda$17 = LaneFixDialog.show$side$lambda$20$lambda$17(LaneFixDialog.Road.this, s);
                return show$side$lambda$20$lambda$17;
            }
        }));
        $this$show_u24side_u24lambda_u2420.addView(show$pm($ctx, d, objectRef, objectRef2, "−", new Function0() { // from class: com.starpilot.starview.LaneFixDialog$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit show$side$lambda$20$lambda$18;
                show$side$lambda$20$lambda$18 = LaneFixDialog.show$side$lambda$20$lambda$18(LaneFixDialog.Road.this, s);
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Unit show$lambda$26(Road $road, LaneRamps.Ramp $rp, Ref.ObjectRef $paint, Ref.ObjectRef $roadView) {
        Function0 function0;
        $road.addExtra(Intrinsics.areEqual($rp.getKind(), "exit") ? 'x' : 'm', $rp.getSide());
        RoadView roadView = null;
        if ($paint.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function0 = null;
        } else {
            function0 = (Function0) $paint.element;
        }
        function0.invoke();
        if ($roadView.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("roadView");
        } else {
            roadView = (RoadView) $roadView.element;
        }
        roadView.invalidate();
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
                Function0.this.invoke();
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
