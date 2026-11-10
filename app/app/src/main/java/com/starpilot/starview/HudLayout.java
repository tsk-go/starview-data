package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

/* compiled from: HudLayout.kt */
public final class HudLayout {
    public static final Companion Companion = new Companion(null);
    public static final String KEY = "layout";
    private final float d;
    private float displayLong;
    private float displayShort;
    private final List<Item> items;
    private final SharedPreferences prefs;
    private final HashMap<String, State> states;
    private String theme;
    private float windowScale;

    /* compiled from: HudLayout.kt */
    public static final class Anchor {
        private final int dx;
        private final int dy;
        private final int gravity;

        public Anchor(int gravity, int dx, int dy) {
            this.gravity = gravity;
            this.dx = dx;
            this.dy = dy;
        }

        public final int getDx() {
            return this.dx;
        }

        public final int getDy() {
            return this.dy;
        }

        public final int getGravity() {
            return this.gravity;
        }
    }

    public HudLayout(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.prefs = ctx.getSharedPreferences("starview_hud", 0);
        this.d = ctx.getResources().getDisplayMetrics().density;
        int i = 48;
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z = false;
        boolean z2 = false;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        boolean z3 = false;
        int i2 = 48;
        int i3 = 48;
        int i4 = 48;
        DefaultConstructorMarker defaultConstructorMarker3 = null;
        boolean z4 = false;
        boolean z5 = false;
        int i5 = 48;
        DefaultConstructorMarker defaultConstructorMarker4 = null;
        boolean z6 = false;
        DefaultConstructorMarker defaultConstructorMarker5 = null;
        boolean z7 = false;
        boolean z8 = false;
        this.items = CollectionsKt.listOf((Object[]) new Item[]{new Item(R.id.maxCard, "maxCard", "MAX set speed", new Anchor(8388659, 24, 20), false, false, 48, null), new Item(R.id.slc, "slc", "Speed limit", new Anchor(8388659, 126, 20), z, z2, i, defaultConstructorMarker), new Item(R.id.slcSources, "slcSources", "Speed limit sources", new Anchor(8388659, 228, 20), z, z2, i, defaultConstructorMarker), new Item(R.id.gauge, "gauge", "Curve / stop gauge", new Anchor(8388659, 24, 134), z, z2, i, defaultConstructorMarker), new Item(R.id.compass, "compass", "Compass", new Anchor(49, 0, 64), z3, z, 48, defaultConstructorMarker2), new Item(R.id.gear, "gear", "Settings", new Anchor(49, 0, 14), z3, z, 32, defaultConstructorMarker2), new Item(R.id.wheel, "wheel", "Steering wheel", new Anchor(8388661, 24, 20), z3, z, i2, defaultConstructorMarker2), new Item(R.id.egpu, "egpu", "eGPU status", new Anchor(8388661, 24, 188), z3, z, i2, defaultConstructorMarker2), new Item(R.id.navCard, "navCard", "Navigation", new Anchor(8388693, 24, 136), z3, z, i2, defaultConstructorMarker2), new Item(R.id.nudgeHint, "nudgeHint", "Exit nudge prompt", new Anchor(49, 0, 100), z3, z, i2, defaultConstructorMarker2), new Item(R.id.slowCard, "slowCard", "Why it's slowing", new Anchor(49, 0, 200), z3, z, i2, defaultConstructorMarker2), new Item(R.id.laneCard, "laneCard", "Lane tracker (test)", new Anchor(8388661, 24, 250), z3, z, i2, defaultConstructorMarker2), new Item(R.id.laneSuggest, "laneSuggest", "Change lanes? (confirm)", new Anchor(49, 0, 310), z3, z, 32, defaultConstructorMarker2), new Item(R.id.blinkCard, "blinkCard", "Blinker buttons (test)", new Anchor(81, 0, 64), z3, z, i3, defaultConstructorMarker2), new Item(R.id.gpsCard, "gpsCard", "GPS satellites", new Anchor(8388659, 24, 250), z3, z, i3, defaultConstructorMarker2), new Item(R.id.weather, "weather", "Weather", new Anchor(8388691, 28, 150), z4, z5, i4, defaultConstructorMarker3), new Item(R.id.speedBox, "speedBox", "Speed", new Anchor(8388691, 28, 16), z3, z, i3, defaultConstructorMarker2), new Item(R.id.roadName, "roadName", "Road name", new Anchor(81, 0, 10), z4, z5, i4, defaultConstructorMarker3), new Item(R.id.devMetrics, "devMetrics", "Device metrics", new Anchor(81, 0, 34), z6, z3, i5, defaultConstructorMarker4), new Item(R.id.personality, "personality", "Following distance", new Anchor(8388693, 130, 20), z6, z3, i5, defaultConstructorMarker4), new Item(R.id.dm, "dm", "Driver monitoring", new Anchor(8388693, 24, 20), z6, z3, i5, defaultConstructorMarker4), new Item(R.id.slcActions, "slcActions", "Speed limit buttons (Accept / Decline)", new Anchor(8388659, 126, 132), z4, z5, i4, defaultConstructorMarker3), new Item(R.id.curveLabel, "curveLabel", "Curve speed label", new Anchor(8388661, 134, 26), z7, z6, 48, defaultConstructorMarker5), new Item(R.id.clockTv, "clock", "Clock", new Anchor(8388661, 134, 66), z7, z6, 16, defaultConstructorMarker5), new Item(R.id.modeText, "modeText", "Driving mode", new Anchor(81, 0, 58), z8, false, 16, null), new Item(R.id.devSidebar, "devSidebar", "Developer sidebar", new Anchor(8388661, 24, 250), z4, z5, 16, defaultConstructorMarker3), new Item(R.id.pipLeft, "pipLeft", "Side view - left", new Anchor(8388691, 24, 24), false, z8, 48, null), new Item(R.id.pipRight, "pipRight", "Side view - right", new Anchor(8388693, 24, 24), false, false, 48, null)});
        this.states = new HashMap<>();
        this.theme = "day";
        this.windowScale = 1.0f;
    }

    /* compiled from: HudLayout.kt */
    public static final class Item {
        private final Anchor anchor;
        private final boolean canHide;
        private final boolean defaultOn;
        private final int id;
        private final String key;
        private final String label;

        public Item(int id, String key, String label, Anchor anchor, boolean canHide, boolean defaultOn) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(anchor, "anchor");
            this.id = id;
            this.key = key;
            this.label = label;
            this.anchor = anchor;
            this.canHide = canHide;
            this.defaultOn = defaultOn;
        }

        public /* synthetic */ Item(int i, String str, String str2, Anchor anchor, boolean z, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, str, str2, anchor, (i2 & 16) != 0 ? true : z, (i2 & 32) != 0 ? true : z2);
        }

        public final Anchor getAnchor() {
            return this.anchor;
        }

        public final boolean getCanHide() {
            return this.canHide;
        }

        public final boolean getDefaultOn() {
            return this.defaultOn;
        }

        public final int getId() {
            return this.id;
        }

        public final String getKey() {
            return this.key;
        }

        public final String getLabel() {
            return this.label;
        }
    }

    /* compiled from: HudLayout.kt */
    public static final class State {
        private float ch;
        private float cw;
        private boolean on;
        private float scale;
        private float x;
        private float y;

        public State() {
            this(false, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 63, null);
        }

        public State(boolean on, float x, float y, float scale, float cw, float ch) {
            this.on = on;
            this.x = x;
            this.y = y;
            this.scale = scale;
            this.cw = cw;
            this.ch = ch;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public /* synthetic */ State(boolean z, float f, float f2, float f3, float f4, float f5, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, r12, r1, r2, r3, (i & 32) == 0 ? f5 : Float.NaN);
            float f6;
            z = (i & 1) != 0 ? true : z;
            float f7 = (i & 2) != 0 ? Float.NaN : f;
            float f8 = (i & 4) != 0 ? Float.NaN : f2;
            float f9 = (i & 8) != 0 ? 1.0f : f3;
            if ((i & 16) == 0) {
                f6 = f4;
            } else {
                f6 = Float.NaN;
            }
        }

        public final boolean getOn() {
            return this.on;
        }

        public final float getScale() {
            return this.scale;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        public final void setOn(boolean z) {
            this.on = z;
        }

        public final void setScale(float f) {
            this.scale = f;
        }

        public final void setX(float f) {
            this.x = f;
        }

        public final void setY(float f) {
            this.y = f;
        }

        public final float getCh() {
            return this.ch;
        }

        public final float getCw() {
            return this.cw;
        }

        public final void setCh(float f) {
            this.ch = f;
        }

        public final void setCw(float f) {
            this.cw = f;
        }
    }

    private final int dp(int v) {
        return (int) (v * this.d);
    }

    public final List<Item> getItems() {
        return this.items;
    }

    public final HashMap<String, State> getStates() {
        return this.states;
    }

    public final String getTheme() {
        return this.theme;
    }

    public final float getWindowScale() {
        return this.windowScale;
    }

    public final void setWindowScale(float f) {
        this.windowScale = f;
    }

    public final float getDisplayLong() {
        return this.displayLong;
    }

    public final float getDisplayShort() {
        return this.displayShort;
    }

    public final void setDisplayLong(float f) {
        this.displayLong = f;
    }

    public final void setDisplayShort(float f) {
        this.displayShort = f;
    }

    private final float keep(float v0, float b, float saved, float now) {
        float f = 2;
        float c = (b / f) + v0;
        float f2 = 3;
        return c < saved / f2 ? v0 : c > (f * saved) / f2 ? now - (saved - v0) : ((now / f) + (c - (saved / f))) - (b / f);
    }

    private final Pair<Integer, Integer> savedPos(State s, int rw, int rh, int w, int h) {
        float ws = this.windowScale;
        float sw = Float.isNaN(s.getCw()) ? this.displayLong > 0.0f ? this.displayLong : rw / ws : s.getCw();
        float sh = Float.isNaN(s.getCh()) ? this.displayShort > 0.0f ? this.displayShort : rh / ws : s.getCh();
        float bw = w / ws;
        float bh = h / ws;
        float x = keep(s.getX() * sw, bw, sw, rw / ws) * ws;
        float y = keep(s.getY() * sh, bh, sh, rh / ws) * ws;
        return TuplesKt.to(Integer.valueOf((int) x), Integer.valueOf((int) y));
    }

    public final void place(State s, float left, float top, int rw, int rh) {
        Intrinsics.checkNotNullParameter(s, "s");
        s.setX(left / rw);
        s.setY(top / rh);
        s.setCw(rw / this.windowScale);
        s.setCh(rh / this.windowScale);
    }

    public final void load(String theme) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        this.theme = theme;
        String raw = this.prefs.getString("layout", null);
        if (raw == null && (raw = this.prefs.getString("layout_day", null)) == null) {
            raw = this.prefs.getString("layout_night", null);
        }
        restore(raw);
    }

    public final String snapshot() {
        JSONObject o = new JSONObject();
        for (Map.Entry<String, State> entry : this.states.entrySet()) {
            String k = entry.getKey();
            State s = entry.getValue();
            o.put(k, new JSONObject().put("on", s.getOn()).put("x", Float.isNaN(s.getX()) ? JSONObject.NULL : Double.valueOf(s.getX())).put("y", Float.isNaN(s.getY()) ? JSONObject.NULL : Double.valueOf(s.getY())).put("s", s.getScale()).put("cw", Float.isNaN(s.getCw()) ? JSONObject.NULL : Double.valueOf(s.getCw())).put("ch", Float.isNaN(s.getCh()) ? JSONObject.NULL : Double.valueOf(s.getCh())));
        }
        String jSONObject = o.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
        return jSONObject;
    }

    public final void restore(String raw) {
        this.states.clear();
        if (raw == null) {
            return;
        }
        try {
            Result.Companion companion = Result.Companion;
            HudLayout $this$restore_u24lambda_u240 = this;
            int i = 0;
            JSONObject o = new JSONObject(raw);
            Iterator<String> keys = o.keys();
            Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
            while (keys.hasNext()) {
                String k = keys.next();
                JSONObject e = o.getJSONObject(k);
                $this$restore_u24lambda_u240.states.put(k, new State(e.optBoolean("on", true), (float) e.optDouble("x", Double.NaN), (float) e.optDouble("y", Double.NaN), (float) e.optDouble("s", 1.0d), (float) e.optDouble("cw", Double.NaN), (float) e.optDouble("ch", Double.NaN)));
                $this$restore_u24lambda_u240 = $this$restore_u24lambda_u240;
                i = i;
            }
            Result.m1191constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
    }

    public final void save() {
        this.prefs.edit().putString("layout", snapshot()).apply();
    }

    public final void reset() {
        this.states.clear();
        this.prefs.edit().remove("layout").remove("layout_day").remove("layout_night").apply();
    }

    /* compiled from: HudLayout.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private final boolean defaultOn(String key) {
        Object element$iv;
        Iterable $this$firstOrNull$iv = this.items;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                Item it2 = (Item) element$iv;
                if (Intrinsics.areEqual(it2.getKey(), key)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        Item item = (Item) element$iv;
        if (item != null) {
            return item.getDefaultOn();
        }
        return true;
    }

    public final State state(String key) {
        State state;
        Intrinsics.checkNotNullParameter(key, "key");
        Map $this$getOrPut$iv = this.states;
        State state2 = $this$getOrPut$iv.get(key);
        if (state2 == null) {
            state = new State(defaultOn(key), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 62, null);
            $this$getOrPut$iv.put(key, state);
        } else {
            state = state2;
        }
        return state;
    }

    public final boolean enabled(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        State state = this.states.get(key);
        return state != null ? state.getOn() : defaultOn(key);
    }

    public final void adopt(FrameLayout root) {
        Intrinsics.checkNotNullParameter(root, "root");
        Iterator<Item> it = this.items.iterator();
        while (it.hasNext()) {
            View v = root.findViewById(it.next().getId());
            if (v != null) {
                ViewParent parent = v.getParent();
                ViewGroup parent2 = parent instanceof ViewGroup ? (ViewGroup) parent : null;
                if (parent2 != null) {
                    int w = v.getLayoutParams().width;
                    int h = v.getLayoutParams().height;
                    if (parent2 != root) {
                        parent2.removeView(v);
                        root.addView(v, new FrameLayout.LayoutParams(w, h, 8388659));
                    } else {
                        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                        ((FrameLayout.LayoutParams) layoutParams).gravity = 8388659;
                    }
                    v.setPivotX(0.0f);
                    v.setPivotY(0.0f);
                }
            }
        }
        int[] iArr = {R.id.topLeft};
        for (int i = 0; i < 1; i++) {
            int id = iArr[i];
            View it2 = root.findViewById(id);
            if (it2 != null) {
                root.removeView(it2);
            }
        }
        Iterable $this$map$iv = RangesKt.until(0, root.getChildCount());
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it3 = $this$map$iv.iterator();
        while (it3.hasNext()) {
            int item$iv$iv = ((IntIterator) it3).nextInt();
            destination$iv$iv.add(root.getChildAt(item$iv$iv));
        }
        Iterable $this$filter$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            View it4 = (View) element$iv$iv;
            if ((((it4 instanceof ViewGroup) && ((ViewGroup) it4).getId() == -1 && ((ViewGroup) it4).getChildCount() == 0) ? 1 : null) != null) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        List leftovers = (List) destination$iv$iv2;
        Iterator it5 = leftovers.iterator();
        while (it5.hasNext()) {
            root.removeView((View) it5.next());
        }
    }

    public final Pair<Integer, Integer> defaultPos(Item item, int rootW, int rootH, int w, int h) {
        int x;
        Intrinsics.checkNotNullParameter(item, "item");
        int g = item.getAnchor().getGravity();
        float ws = this.windowScale;
        if ((g & 8388613) == 8388613) {
            x = (rootW - w) - ((int) (dp(item.getAnchor().getDx()) * ws));
        } else {
            x = (g & 1) == 1 ? (rootW - w) / 2 : (int) (dp(item.getAnchor().getDx()) * ws);
        }
        int y = (g & 80) == 80 ? (rootH - h) - ((int) (dp(item.getAnchor().getDy()) * ws)) : (int) (dp(item.getAnchor().getDy()) * ws);
        return TuplesKt.to(Integer.valueOf(x), Integer.valueOf(y));
    }

    public final void apply(FrameLayout root) {
        Intrinsics.checkNotNullParameter(root, "root");
        int rw = root.getWidth();
        int rh = root.getHeight();
        if (rw == 0 || rh == 0) {
            return;
        }
        for (Item it : this.items) {
            View v = root.findViewById(it.getId());
            if (v != null) {
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
                if (layoutParams2 != null) {
                    FrameLayout.LayoutParams lp = layoutParams2;
                    State s = this.states.get(it.getKey());
                    float sc = (s != null ? s.getScale() : 1.0f) * this.windowScale;
                    int w = (int) ((v.getWidth() > 0 ? v.getWidth() : v.getMeasuredWidth()) * sc);
                    int h = (int) ((v.getHeight() > 0 ? v.getHeight() : v.getMeasuredHeight()) * sc);
                    Pair<Integer, Integer> defaultPos = defaultPos(it, rw, rh, w, h);
                    int dx = defaultPos.component1().intValue();
                    int dy = defaultPos.component2().intValue();
                    Pair sp = (s == null || Float.isNaN(s.getX()) || Float.isNaN(s.getY())) ? null : savedPos(s, rw, rh, w, h);
                    int x = sp != null ? RangesKt.coerceIn(sp.getFirst().intValue(), 0, Math.max(0, rw - w)) : dx;
                    int y = sp != null ? RangesKt.coerceIn(sp.getSecond().intValue(), 0, Math.max(0, rh - h)) : dy;
                    if (lp.leftMargin != x || lp.topMargin != y) {
                        lp.leftMargin = x;
                        lp.topMargin = y;
                        lp.gravity = 8388659;
                        v.setLayoutParams(lp);
                    }
                    if (!(v.getScaleX() == sc)) {
                        v.setScaleX(sc);
                        v.setScaleY(sc);
                    }
                }
            }
        }
    }

    public final RectF rect(FrameLayout root, Item item) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(item, "item");
        View v = root.findViewById(item.getId());
        if (v == null) {
            return null;
        }
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 == null) {
            return null;
        }
        FrameLayout.LayoutParams lp = layoutParams2;
        State state = this.states.get(item.getKey());
        float sc = (state != null ? state.getScale() : 1.0f) * this.windowScale;
        float w = (v.getWidth() > 0 ? v.getWidth() : v.getMeasuredWidth()) * sc;
        float h = (v.getHeight() > 0 ? v.getHeight() : v.getMeasuredHeight()) * sc;
        return new RectF(lp.leftMargin, lp.topMargin, lp.leftMargin + w, lp.topMargin + h);
    }
}
