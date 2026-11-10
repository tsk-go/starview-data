package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.GalaxyRow;
import com.starpilot.starview.SettingIcons;
import com.starpilot.starview.SvIcon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;
import java.util.Map;
import kotlin.jvm.internal.Ref.ObjectRef;

/* compiled from: GalaxySettingsView.kt */
public final class GalaxySettingsView extends FrameLayout {
    public static final Companion Companion = new Companion(null);
    public static final long REFRESH_MS = 3000;
    private boolean busy;
    private final LinearLayout col;
    private JSONObject data;
    private final Function1<LinearLayout, Unit> footer;
    private final Galaxy7 g;
    private JSONObject hub;
    private String lastShown;
    private final Handler main;
    private final Function2<String, String, Unit> openWeb;
    private final Function2<String, LinearLayout, Unit> pageExtras;
    private final Runnable poll;
    private final Root root;
    private final ScrollView scroll;
    private final ArrayList<String> stack;
    private final HashMap<String, String> titles;
    private boolean touching;
    private final CarUi ui;
    private VasmEditorCard vasmCard;
    private boolean visible;

    /* compiled from: GalaxySettingsView.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GalaxyRow.Control.values().length];
            try {
                iArr[GalaxyRow.Control.GROUP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[GalaxyRow.Control.SWITCH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[GalaxyRow.Control.SELECT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[GalaxyRow.Control.SLIDER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[GalaxyRow.Control.ACTION.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[GalaxyRow.Control.READOUT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ GalaxySettingsView(Context context, CarUi carUi, Root root, Function2 function2, Function1 function1, Function2 function22, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, carUi, root, function2, (i & 16) != 0 ? null : function1, (i & 32) != 0 ? null : function22);
    }

    /* compiled from: GalaxySettingsView.kt */
    public static abstract class Entry {
        public /* synthetic */ Entry(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Entry() {
        }

        /* compiled from: GalaxySettingsView.kt */
        public static final class Hub extends Entry {
            private final List<String> pages;

            /* VF: from Vineflower (jadx: ) */
            public Hub(List<String> var1) {
                super(null);
                Intrinsics.checkNotNullParameter(var1, "pages");
                this.pages = var1;
            }

            public final List<String> getPages() {
                return this.pages;
            }
        }

        /* compiled from: GalaxySettingsView.kt */
        public static final class Page extends Entry {
            private final String desc;
            private final String label;
            private final String page;

            /* VF: from Vineflower (jadx: ) */
            public Page(String var1, String var2, String var3) {
                super(null);
                Intrinsics.checkNotNullParameter(var1, "label");
                Intrinsics.checkNotNullParameter(var2, "desc");
                Intrinsics.checkNotNullParameter(var3, "page");
                this.label = var1;
                this.desc = var2;
                this.page = var3;
            }

            public final String getDesc() {
                return this.desc;
            }

            public final String getLabel() {
                return this.label;
            }

            public final String getPage() {
                return this.page;
            }
        }

        /* compiled from: GalaxySettingsView.kt */
        public static final class Web extends Entry {
            private final String desc;
            private final String label;
            private final String path;

            /* VF: from Vineflower (jadx: ) */
            public Web(String var1, String var2, String var3) {
                super(null);
                Intrinsics.checkNotNullParameter(var1, "label");
                Intrinsics.checkNotNullParameter(var2, "desc");
                Intrinsics.checkNotNullParameter(var3, "path");
                this.label = var1;
                this.desc = var2;
                this.path = var3;
            }

            public final String getDesc() {
                return this.desc;
            }

            public final String getLabel() {
                return this.label;
            }

            public final String getPath() {
                return this.path;
            }
        }

        /* compiled from: GalaxySettingsView.kt */
        public static final class Unplaced extends Entry {
            private final Set<String> placed;

            /* VF: from Vineflower (jadx: ) */
            public Unplaced(Set<String> var1) {
                Intrinsics.checkNotNullParameter(var1, "placed");
                super(null);
        super(var1);
                this.placed = var1;
            }

            public final Set<String> getPlaced() {
                return this.placed;
            }
        }
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public GalaxySettingsView(
        Context var1,
        CarUi var2,
        GalaxySettingsView.Root var3,
        Function2<? super String, ? super String, Unit> var4,
        Function1<? super LinearLayout, Unit> var5,
        Function2<? super String, ? super LinearLayout, Unit> var6
    ) {
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        Intrinsics.checkNotNullParameter(var3, "root");
        Intrinsics.checkNotNullParameter(var4, "openWeb");
        this.ui = var2;
        this.root = var3;
        this.openWeb = (Function2) var4;
        this.footer = (Function1) var5;
        this.pageExtras = (Function2) var6;
        this.g = Galaxy7.Companion.current();
        this.main = new Handler(Looper.getMainLooper());
        this.stack = new ArrayList<>();
        this.lastShown = "";
        this.scroll = new ScrollView(var1);
        LinearLayout var7 = new LinearLayout(var1);
        var7.setOrientation(1);
        var7.setPadding(this.ui.dp(10), this.ui.dp(4), this.ui.dp(24), this.ui.dp(60));
        this.col = var7;
        this.scroll.addView((View)this.col);
        this.addView((View)this.scroll, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
        this.poll = new Runnable() {
            final GalaxySettingsView this$0 = GalaxySettingsView.this;

            @Override
            public void run() {
                if (this.this$0.visible) {
                    if (!this.this$0.busy && !this.this$0.touching) {
                        this.this$0.load(true);
                    }

                    this.this$0.main.postDelayed(this, 3000L);
                }
            }
        };
        this.titles = new HashMap<>();
    }

    /* compiled from: GalaxySettingsView.kt */
    public static final class Root {
        private final List<Entry> entries;
        private final String note;
        private final String page;
        private final String title;

        /* VF: from Vineflower (jadx: ) */
        public Root(String var1, String var2, List<? extends GalaxySettingsView.Entry> var3, String var4) {
            super();
            Intrinsics.checkNotNullParameter(var1, "title");
            Intrinsics.checkNotNullParameter(var3, "entries");
            this.title = var1;
            this.page = var2;
            this.entries = (List) var3;
            this.note = var4;
        }

        public /* synthetic */ Root(String str, String str2, List list, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? CollectionsKt.emptyList() : list, (i & 8) != 0 ? null : str3);
        }

        public final List<Entry> getEntries() {
            return this.entries;
        }

        public final String getNote() {
            return this.note;
        }

        public final String getPage() {
            return this.page;
        }

        public final String getTitle() {
            return this.title;
        }
    }

    public final void show() {
        this.visible = true;
        if (this.root.getPage() != null && this.stack.isEmpty()) {
            this.stack.add(this.root.getPage());
        }
        load(false);
        this.main.removeCallbacks(this.poll);
        this.main.postDelayed(this.poll, 3000L);
    }

    public final void hide() {
        this.visible = false;
        this.main.removeCallbacks(this.poll);
    }

    private final String getCurrent() {
        return (String) CollectionsKt.lastOrNull((List) this.stack);
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final boolean getNeedsParked() {
        JSONObject var4 = this.data;
        boolean var3 = false;
        boolean var2 = var3;
        if (var4 != null) {
            var2 = var3;
            if (!var4.optBoolean("parked", true)) {
                var var8 = GalaxyRow.Companion.parse(var4);
                boolean var7;
                if (var8 instanceof Collection && ((Collection)var8).isEmpty()) {
                    var7 = false;
                } else {
                    Iterator var9 = var8.iterator();

                    while (true) {
                        if (!var9.hasNext()) {
                            var7 = false;
                            break;
                        }

                        label37: {
                            GalaxyRow var5 = (GalaxyRow)var9.next();
                            if (!var5.getAvailable()) {
                                if (var5.getPage().length() == 0) {
                                    var7 = true;
                                } else {
                                    var7 = false;
                                }

                                if (var7) {
                                    var7 = true;
                                    break label37;
                                }
                            }

                            var7 = false;
                        }

                        if (var7) {
                            var7 = true;
                            break;
                        }
                    }
                }

                var2 = var3;
                if (var7) {
                    var2 = true;
                }
            }
        }

        return var2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void load(final boolean quiet) {
        Galaxy7 gx = this.g;
        if (gx == null) {
            message("Not connected to the comma. Connect first, then come back.");
            return;
        }
        final String page = getCurrent();
        if (page == null) {
            Iterable $this$any$iv = this.root.getEntries();
            boolean z = false;
            if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                Iterator it = $this$any$iv.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object element$iv = it.next();
                    Entry it2 = (Entry) element$iv;
                    if (((it2 instanceof Entry.Hub) || (it2 instanceof Entry.Unplaced))) {
                        z = true;
                        break;
                    }
                }
            }
            if (!z) {
                drawRoot(null);
                return;
            } else {
                gx.get("/api/settings/pages/hub", new Function2() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda22
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Unit load$lambda$5;
                        load$lambda$5 = GalaxySettingsView.load$lambda$5(GalaxySettingsView.this, (JSONObject) obj, (String) obj2);
                        return load$lambda$5;
                    }
                });
                return;
            }
        }
        if (!quiet) {
            JSONObject jSONObject = this.data;
            if (!Intrinsics.areEqual(jSONObject != null ? jSONObject.optString("page") : null, page)) {
                message("Loading…");
            }
        }
        gx.get("/api/settings/pages/" + Uri.encode(page), new Function2() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit load$lambda$6;
                load$lambda$6 = GalaxySettingsView.load$lambda$6(GalaxySettingsView.this, page, quiet, (JSONObject) obj, (String) obj2);
                return load$lambda$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$5(GalaxySettingsView this$0, JSONObject o, String err) {
        if (this$0.getCurrent() != null) {
            return Unit.INSTANCE;
        }
        if (o != null) {
            this$0.hub = o;
        }
        this$0.drawRoot(o == null && this$0.hub == null ? err : null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$6(GalaxySettingsView this$0, String $page, boolean $quiet, JSONObject o, String err) {
        if (!Intrinsics.areEqual(this$0.getCurrent(), $page)) {
            return Unit.INSTANCE;
        }
        if (o != null) {
            this$0.data = o;
            this$0.draw(o);
            return Unit.INSTANCE;
        }
        if (!$quiet || this$0.data == null) {
            this$0.message(err == null ? "Galaxy didn't answer" : err);
        }
        return Unit.INSTANCE;
    }

    private final void message(String text) {
        this.lastShown = "";
        this.col.removeAllViews();
        backBar();
        this.col.addView(this.ui.note(text), CarUi.lp$default(this.ui, 0, 0, 3, null));
    }

    private final void backBar() {
        String parent;
        if (this.stack.size() <= (this.root.getPage() != null ? 1 : 0)) {
            return;
        }
        if (this.stack.size() >= 2) {
            String str = this.stack.get(this.stack.size() - 2);
            Intrinsics.checkNotNullExpressionValue(str, "get(...)");
            parent = titleOf(str);
        } else {
            parent = this.root.getTitle();
        }
        LinearLayout linearLayout = this.col;
        TextView $this$backBar_u24lambda_u248 = new TextView(getContext());
        $this$backBar_u24lambda_u248.setText("‹  " + parent);
        $this$backBar_u24lambda_u248.setTextColor(CarUi.Companion.getACCENT());
        $this$backBar_u24lambda_u248.setTextSize(18.0f);
        $this$backBar_u24lambda_u248.setTypeface(Typeface.DEFAULT_BOLD);
        $this$backBar_u24lambda_u248.setPadding(this.ui.dp(6), this.ui.dp(6), this.ui.dp(6), this.ui.dp(12));
        $this$backBar_u24lambda_u248.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GalaxySettingsView.this.back();
            }
        });
        linearLayout.addView($this$backBar_u24lambda_u248);
    }

    private final String titleOf(String page) {
        String str = this.titles.get(page);
        if (str != null) {
            return str;
        }
        if (!(page.length() > 0)) {
            return page;
        }
        StringBuilder sb = new StringBuilder();
        char it = page.charAt(0);
        String valueOf = String.valueOf(it);
        Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
        String upperCase = valueOf.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        StringBuilder append = sb.append((Object) upperCase);
        String substring = page.substring(1);
        Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
        return append.append(substring).toString();
    }

    public final boolean back() {
        if (this.stack.size() <= (this.root.getPage() != null ? 1 : 0)) {
            return false;
        }
        this.stack.remove(this.stack.size() - 1);
        this.data = null;
        this.lastShown = "";
        load(false);
        this.scroll.scrollTo(0, 0);
        return true;
    }

    private final void open(String page) {
        if (this.busy) {
            return;
        }
        this.stack.add(page);
        this.data = null;
        this.lastShown = "";
        load(false);
        this.scroll.scrollTo(0, 0);
    }

    private final void drawRoot(String err) {
        boolean $this$any$ivB = false;
        String str;
        Iterable $this$any$iv;
        List emptyList;
        Object element$iv;
        JSONArray optJSONArray;
        JSONObject jSONObject = this.hub;
        if (jSONObject == null || (optJSONArray = jSONObject.optJSONArray("rows")) == null || (str = optJSONArray.toString()) == null) {
            str = "";
        }
        String sig = "root|" + str + (err != null ? err : "");
        if (Intrinsics.areEqual(sig, this.lastShown)) {
            return;
        }
        this.lastShown = sig;
        this.col.removeAllViews();
        Object obj = null;
        if (this.root.getNote() != null) {
            this.col.addView(this.ui.note(this.root.getNote()), CarUi.lp$default(this.ui, 0, 0, 3, null));
        }
        if (err != null) {
            this.col.addView(this.ui.note(err), CarUi.lp$default(this.ui, 0, 0, 3, null));
        }
        Iterable $this$any$iv2 = this.root.getEntries();
        if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
            Iterator it = $this$any$iv2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    $this$any$ivB = false;
                    break;
                }
                Object element$iv2 = it.next();
                Entry it2 = (Entry) element$iv2;
                if (((it2 instanceof Entry.Hub) && Intrinsics.areEqual(CollectionsKt.toSet(((Entry.Hub) it2).getPages()), SetsKt.setOf(new String[]{"traffic", "aggressive", "standard", "relaxed"})))) {
                    $this$any$ivB = true;
                    break;
                }
            }
        } else {
            $this$any$ivB = false;
        }
        if ($this$any$ivB) {
            GalaxyVisuals galaxyVisuals = GalaxyVisuals.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            galaxyVisuals.personalities(context, this.ui, this.col, new Function1() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda27
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Unit drawRoot$lambda$11;
                    drawRoot$lambda$11 = GalaxySettingsView.drawRoot$lambda$11(GalaxySettingsView.this, (String) obj2);
                    return drawRoot$lambda$11;
                }
            });
        }
        JSONObject it3 = this.hub;
        if (it3 == null || (emptyList = GalaxyRow.Companion.parse(it3)) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        List hubRows = emptyList;
        for (GalaxyRow r : (Iterable<GalaxyRow>) hubRows) {
            if (r.getPage().length() > 0) {
                this.titles.put(r.getPage(), r.getLabel());
            }
        }
        Iterator<Entry> it4 = this.root.getEntries().iterator();
        int count = 0;
        while (it4.hasNext()) {
            Entry e = it4.next();
            if (e instanceof Entry.Hub) {
                for (final String p : ((Entry.Hub) e).getPages()) {
                    List $this$firstOrNull$iv = hubRows;
                    Iterator it5 = $this$firstOrNull$iv.iterator();
                    while (true) {
                        if (it5.hasNext()) {
                            element$iv = it5.next();
                            if (Intrinsics.areEqual(((GalaxyRow) element$iv).getPage(), p)) {
                                break;
                            }
                        } else {
                            element$iv = obj;
                            break;
                        }
                    }
                    GalaxyRow r2 = (GalaxyRow) element$iv;
                    if (r2 != null) {
                        linkCard$default(this, r2.getLabel(), r2.getValue(), r2.getAvailable(), false, SettingIcons.INSTANCE.forPage(p), new Function0() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda28
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit drawRoot$lambda$15$lambda$14;
                                drawRoot$lambda$15$lambda$14 = GalaxySettingsView.drawRoot$lambda$15$lambda$14(GalaxySettingsView.this, p);
                                return drawRoot$lambda$15$lambda$14;
                            }
                        }, 8, null);
                        count++;
                        e = e;
                    }
                }
            } else if (e instanceof Entry.Page) {
                this.titles.put(((Entry.Page) e).getPage(), ((Entry.Page) e).getLabel());
                final var e$f1 = e;
                linkCard$default(this, ((Entry.Page) e).getLabel(), ((Entry.Page) e).getDesc(), true, false, SettingIcons.INSTANCE.forPage(((Entry.Page) e).getPage()), new Function0() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda29
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit drawRoot$lambda$16;
                        drawRoot$lambda$16 = GalaxySettingsView.drawRoot$lambda$16(GalaxySettingsView.this, e$f1);
                        return drawRoot$lambda$16;
                    }
                }, 8, null);
                count++;
            } else if (e instanceof Entry.Web) {
                final var e$f = e;
                linkCard(((Entry.Web) e).getLabel(), ((Entry.Web) e).getDesc(), true, true, webIcon(((Entry.Web) e).getPath()), new Function0() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda30
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit drawRoot$lambda$17;
                        drawRoot$lambda$17 = GalaxySettingsView.drawRoot$lambda$17(GalaxySettingsView.this, e$f);
                        return drawRoot$lambda$17;
                    }
                });
                count++;
            } else {
                if (!(e instanceof Entry.Unplaced)) {
                    throw new NoWhenBranchMatchedException();
                }
                List $this$filter$iv = hubRows;
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    GalaxyRow it6 = (GalaxyRow) element$iv$iv;
                    if ((it6.getPage().length() > 0) && !((Entry.Unplaced) e).getPlaced().contains(it6.getPage())) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                for (final GalaxyRow r3 : (GalaxyRow) destination$iv$iv) {
                    linkCard$default(this, r3.getLabel(), r3.getValue(), r3.getAvailable(), false, SettingIcons.INSTANCE.forPage(r3.getPage()), new Function0() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda31
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit drawRoot$lambda$19;
                            drawRoot$lambda$19 = GalaxySettingsView.drawRoot$lambda$19(GalaxySettingsView.this, r3);
                            return drawRoot$lambda$19;
                        }
                    }, 8, null);
                    count++;
                }
                obj = null;
            }
        }
        if (count == 0 && err == null) {
            this.col.addView(this.ui.note(this.hub == null ? "Loading…" : "Nothing here for this car and StarPilot version."), CarUi.lp$default(this.ui, 0, 0, 3, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit drawRoot$lambda$11(GalaxySettingsView this$0, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.open(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit drawRoot$lambda$15$lambda$14(GalaxySettingsView this$0, String $p) {
        this$0.open($p);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit drawRoot$lambda$16(GalaxySettingsView this$0, Entry $e) {
        this$0.open(((Entry.Page) $e).getPage());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit drawRoot$lambda$17(GalaxySettingsView this$0, Entry $e) {
        this$0.openWeb.invoke(((Entry.Web) $e).getPath(), ((Entry.Web) $e).getLabel());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit drawRoot$lambda$19(GalaxySettingsView this$0, GalaxyRow $r) {
        this$0.open($r.getPage());
        return Unit.INSTANCE;
    }

    static /* synthetic */ void linkCard$default(GalaxySettingsView galaxySettingsView, String str, String str2, boolean z, boolean z2, SettingIcons.Icon icon, Function0 function0, int i, Object obj) {
        if ((i & 8) != 0) {
            z2 = false;
        }
        boolean z3 = z2;
        if ((i & 16) != 0) {
            icon = null;
        }
        galaxySettingsView.linkCard(str, str2, z, z3, icon, function0);
    }

    private final void linkCard(String title, String desc, final boolean enabled, boolean web, SettingIcons.Icon icon, final Function0<Unit> onClick) {
        View view;
        CarUi carUi = this.ui;
        String str = desc;
        if (StringsKt.isBlank(str)) {
            str = null;
        }
        String str2 = str;
        if (icon != null) {
            SvIcon svIcon = SvIcon.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            view = svIcon.chip(context, icon.getName(), icon.getGroup(), 46, enabled ? SvIcon.State.ON : SvIcon.State.NA);
        } else {
            view = null;
        }
        LinearLayout r = (LinearLayout) CarUi.row$default(carUi, title, str2, 0, view, null, 20, null).component1();
        TextView $this$linkCard_u24lambda_u2422 = new TextView(getContext());
        $this$linkCard_u24lambda_u2422.setText(web ? "↗" : "›");
        $this$linkCard_u24lambda_u2422.setTextSize(30.0f);
        $this$linkCard_u24lambda_u2422.setTextColor(CarUi.Companion.getDIM());
        $this$linkCard_u24lambda_u2422.setPadding(this.ui.dp(14), 0, this.ui.dp(6), 0);
        r.addView($this$linkCard_u24lambda_u2422);
        r.setAlpha(enabled ? 1.0f : 0.45f);
        r.setClickable(true);
        r.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GalaxySettingsView.linkCard$lambda$23(enabled, onClick, view2);
            }
        });
        this.col.addView(r, CarUi.lp$default(this.ui, 0, 0, 3, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void linkCard$lambda$23(boolean $enabled, Function0 $onClick, View it) {
        if ($enabled) {
            $onClick.invoke();
        }
    }

    private final int vasmEditorRow(JSONObject page) {
        if (Intrinsics.areEqual(page.optString("page"), "vasm")) {
            return page.optInt("editorRow", -1);
        }
        return -1;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final void draw(JSONObject var1) {
        List var9 = GalaxyRow.Companion.parse(var1);
        int var4 = this.vasmEditorRow(var1);
        if (var4 >= 0) {
            VasmEditorCard var7 = this.vasmCard;
            VasmEditorCard var6 = var7;
            if (var7 == null) {
                Context var21 = this.getContext();
                Intrinsics.checkNotNullExpressionValue(var21, "getContext(...)");
                var6 = new VasmEditorCard(var21, this.ui, new GalaxySettingsView$$ExternalSyntheticLambda9(this, var4));
                this.vasmCard = var6;
            }

            GalaxyRow var32 = ((GalaxyRow) CollectionsKt.getOrNull(var9, var4));
            boolean var5;
            if (var32 != null && var32.getAvailable()) {
                var5 = true;
            } else {
                var5 = false;
            }

            var6.update(var1, var5);
        }

        Object var22 = var1.optString("title");
        String var33 = var1.optString("subtitle");
        boolean var20 = var1.optBoolean("vehicleIdentified");
        String var8 = CollectionsKt.joinToString(var9, "\n", "", "", -1, "...", new GalaxySettingsView$$ExternalSyntheticLambda10());
        var22 = var22 + "|" + var33 + "|" + var20 + "|" + var8;
        if (!Intrinsics.areEqual(var22, this.lastShown)) {
            int var2;
            if (this.lastShown.length() > 0) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            if ((var2 != 0)) {
                var2 = this.scroll.getScrollY();
            } else {
                var2 = 0;
            }

            this.lastShown = ((String) var22);
            var33 = var1.optString("page");
            Map var10 = this.titles;
            CharSequence var36 = var1.optString("title");
            var22 = var36;
            if (StringsKt.isBlank(var36)) {
                Intrinsics.checkNotNull(var33);
                var22 = this.titleOf(var33);
            }

            var10.put(var33, var22);
            this.col.removeAllViews();
            this.backBar();
            if (this.stack.size() > 1 || this.root.getPage() == null) {
                LinearLayout var25 = this.col;
                TextView var37 = new TextView(this.getContext());
                Intrinsics.checkNotNull(var33);
                var37.setText(this.titleOf(var33));
                var37.setTextColor(CarUi.Companion.getTEXT());
                var37.setTextSize(24.0F);
                var37.setTypeface(Typeface.DEFAULT_BOLD);
                var37.setPadding(this.ui.dp(6), 0, 0, this.ui.dp(4));
                var25.addView((View)var37);
            }

            var22 = var1.optString("subtitle");
            Intrinsics.checkNotNull(var22);
            if (StringsKt.isBlank(((CharSequence) var22))) {
                var22 = null;
            }

            if (var22 != null) {
                this.col.addView((View)this.ui.note(((String) var22)), (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
            }

            if (!var1.optBoolean("vehicleIdentified", true)) {
                this.col
                    .addView(
                        (View)this.ui.note("The comma hasn't identified the car yet: settings for this car show once it has (turn the car on once)."),
                        (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null)
                    );
            }

            if (Intrinsics.areEqual(this.root.getPage(), var33) && this.root.getNote() != null) {
                this.col.addView((View)this.ui.note(this.root.getNote()), (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
            }

            GalaxyRow.Companion var11 = GalaxyRow.Companion;
            Intrinsics.checkNotNull(var33);
            Iterable var12 = var11.visible(var33, var9);
            var var27 = new ArrayList();

            for (Object var44 : var12) {
                GalaxyRow var42 = (GalaxyRow)var44;
                boolean var3;
                if (var4 < 0 || var42.getIndex() != var4 && !Intrinsics.areEqual(var42.getLabel(), "Saved spot-monitor settings")) {
                    var3 = true;
                } else {
                    var3 = false;
                }

                if (var3) {
                    var27.add(var44);
                }
            }

            List var28 = var27;
            if (var4 >= 0) {
                VasmEditorCard var39 = this.vasmCard;
                if (var39 != null) {
                    ViewParent var13 = var39.getParent();
                    ViewGroup var14;
                    if (var13 instanceof ViewGroup) {
                        var14 = (ViewGroup)var13;
                    } else {
                        var14 = null;
                    }

                    if (var14 != null) {
                        var14.removeView((View)var39);
                    }

                    this.col.addView((View)var39, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
                }
            }

            if (var28.isEmpty()) {
                this.col.addView((View)this.ui.note("No settings here for this car."), (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
            }

            GalaxyVisuals var15 = GalaxyVisuals.INSTANCE;
            Context var40 = this.getContext();
            Intrinsics.checkNotNullExpressionValue(var40, "getContext(...)");
            Set var41 = var15.header(
                var40,
                this.ui,
                var33,
                var28,
                this.col,
                new GalaxySettingsView$$ExternalSyntheticLambda12(this),
                new GalaxySettingsView$$ExternalSyntheticLambda13(this)
            );
            Iterator var29 = var28.iterator();
            String var16 = var33;

            while (var29.hasNext()) {
                GalaxyRow var35 = (GalaxyRow)var29.next();
                if (!var41.contains(var35.getIndex())) {
                    GalaxyRow.Control var43 = var35.getControl();
                    switch (GalaxySettingsView.WhenMappings.$EnumSwitchMapping$0[var43.ordinal()]) {
                        case 1:
                            this.titles.put(var35.getPage(), var35.getLabel());
                            linkCard$default(
                                this,
                                var35.getLabel(),
                                var35.getValue(),
                                var35.getAvailable(),
                                false,
                                SettingIcons.INSTANCE.forRow(var16, var35.getLabel(), var35.getPage(), var35.getControl()),
                                new GalaxySettingsView$$ExternalSyntheticLambda14(this, var35),
                                8,
                                null
                            );
                            break;
                        case 2:
                            this.switchRow(var35);
                            break;
                        case 3:
                            this.selectRow(var35);
                            break;
                        case 4:
                            this.sliderRow(var35);
                            break;
                        case 5:
                            this.actionRow(var35);
                            break;
                        case 6:
                            this.readoutRow(var35);
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                }
            }

            Function2 var30 = this.pageExtras;
            if (var30 != null) {
                var30.invoke(var16, this.col);
            }

            if (Intrinsics.areEqual(var16, this.root.getPage()) && this.stack.size() == 1) {
                for (GalaxySettingsView.Entry var17 : this.root.getEntries()) {
                    if (var17 instanceof GalaxySettingsView.Entry.Web) {
                        this.linkCard(
                            ((GalaxySettingsView.Entry.Web)var17).getLabel(),
                            ((GalaxySettingsView.Entry.Web)var17).getDesc(),
                            true,
                            true,
                            this.webIcon(((GalaxySettingsView.Entry.Web)var17).getPath()),
                            new GalaxySettingsView$$ExternalSyntheticLambda15(this, var17)
                        );
                    } else if (var17 instanceof GalaxySettingsView.Entry.Page) {
                        this.titles.put(((GalaxySettingsView.Entry.Page)var17).getPage(), ((GalaxySettingsView.Entry.Page)var17).getLabel());
                        linkCard$default(
                            this,
                            ((GalaxySettingsView.Entry.Page)var17).getLabel(),
                            ((GalaxySettingsView.Entry.Page)var17).getDesc(),
                            true,
                            false,
                            SettingIcons.INSTANCE.forPage(((GalaxySettingsView.Entry.Page)var17).getPage()),
                            new GalaxySettingsView$$ExternalSyntheticLambda16(this, var17),
                            8,
                            null
                        );
                    }
                }

                Function1 var18 = this.footer;
                if (var18 != null) {
                    var18.invoke(this.col);
                }
            }

            if (var2 > 0) {
                this.scroll.post(new GalaxySettingsView$$ExternalSyntheticLambda17(this, var2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit draw$lambda$24(GalaxySettingsView this$0, int $editorRow, JSONObject d) {
        Intrinsics.checkNotNullParameter(d, "d");
        this$0.reviewDraft($editorRow, d);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence draw$lambda$26(GalaxyRow it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getLabel() + "|" + it.getValue() + "|" + it.getAvailable() + "|" + it.getReason() + "|" + it.getChoices() + "|" + it.getMinimum() + "|" + it.getMaximum() + "|" + it.getDefaultValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit draw$lambda$33(GalaxySettingsView this$0, GalaxyRow r, String v) {
        Intrinsics.checkNotNullParameter(r, "r");
        Intrinsics.checkNotNullParameter(v, "v");
        if (!this$0.busy) {
            this$0.saveValue(r, v);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit draw$lambda$34(GalaxySettingsView this$0, GalaxyRow r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (!this$0.busy) {
            this$0.resetDefault(r);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit draw$lambda$35(GalaxySettingsView this$0, GalaxyRow $r) {
        this$0.open($r.getPage());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit draw$lambda$36(GalaxySettingsView this$0, Entry $e) {
        this$0.openWeb.invoke(((Entry.Web) $e).getPath(), ((Entry.Web) $e).getLabel());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit draw$lambda$37(GalaxySettingsView this$0, Entry $e) {
        this$0.open(((Entry.Page) $e).getPage());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void draw$lambda$38(GalaxySettingsView this$0, int $keepY) {
        this$0.scroll.scrollTo(0, $keepY);
    }

    private final void dim(View v, GalaxyRow r) {
        v.setAlpha(r.getEditable() ? 1.0f : 0.45f);
    }

    private final View icon(GalaxyRow r) {
        SettingIcons settingIcons = SettingIcons.INSTANCE;
        String current = getCurrent();
        if (current == null) {
            current = "";
        }
        SettingIcons.Icon ic = settingIcons.forRow(current, r.getLabel(), r.getPage(), r.getControl());
        SvIcon.State st = !r.getAvailable() ? SvIcon.State.NA : (r.getControl() != GalaxyRow.Control.SWITCH || Intrinsics.areEqual(r.getValue(), "On")) ? SvIcon.State.ON : SvIcon.State.OFF;
        SvIcon svIcon = SvIcon.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return svIcon.chip(context, ic.getName(), ic.getGroup(), 46, st);
    }

    private final String tag(GalaxyRow r) {
        SettingIcons settingIcons = SettingIcons.INSTANCE;
        String current = getCurrent();
        if (current == null) {
            current = "";
        }
        if (settingIcons.forRow(current, r.getLabel(), r.getPage(), r.getControl()).getKnown()) {
            return null;
        }
        return "NEW";
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final SettingIcons.Icon webIcon(String var1) {
        if (StringsKt.contains(var1, "events", false)) {
            var1 = "sentry";
        } else {
            boolean var2 = StringsKt.contains(var1, "vasm", false);
            String var3 = "crop";
            if (var2) {
                var1 = var3;
            } else if (StringsKt.contains(var1, "pip", false)) {
                var1 = var3;
            } else if (StringsKt.contains(var1, "theme", false)) {
                var1 = "palette";
            } else if (StringsKt.contains(var1, "vehicle", false)) {
                var1 = "car";
            } else {
                CharSequence var7 = var1;
                var1 = "camera";
                if (!StringsKt.contains(var7, "camera", false)) {
                    var1 = "link";
                }
            }
        }

        SettingIcons var5 = SettingIcons.INSTANCE;
        String var4 = this.root.getPage();
        String var8 = var4;
        if (var4 == null) {
            var8 = "cameras";
        }

        return new SettingIcons.Icon(var1, var5.group(var8), false, 4, null);
    }

    private final String desc(GalaxyRow r) {
        String replace$default = StringsKt.replace(r.getShownReason(), "https://firestar.link/discord", "the StarPilot Discord", false);
        if (StringsKt.isBlank(replace$default)) {
            replace$default = null;
        }
        return replace$default;
    }

    private final void defaultButton(LinearLayout parent, final GalaxyRow r) {
        if (r.getShowDefault()) {
            TextView $this$defaultButton_u24lambda_u2441 = CarUi.button$default(this.ui, "Default", false, new Function0() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda25
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit defaultButton$lambda$40;
                    defaultButton$lambda$40 = GalaxySettingsView.defaultButton$lambda$40(r, GalaxySettingsView.this);
                    return defaultButton$lambda$40;
                }
            }, 2, null);
            $this$defaultButton_u24lambda_u2441.setMinWidth(this.ui.dp(100));
            $this$defaultButton_u24lambda_u2441.setTextSize(16.0f);
            LinearLayout.LayoutParams $this$defaultButton_u24lambda_u2442 = new LinearLayout.LayoutParams(-2, -2);
            $this$defaultButton_u24lambda_u2442.rightMargin = this.ui.dp(10);
            Unit unit = Unit.INSTANCE;
            parent.addView($this$defaultButton_u24lambda_u2441, $this$defaultButton_u24lambda_u2442);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit defaultButton$lambda$40(GalaxyRow $r, GalaxySettingsView this$0) {
        if ($r.getEditable()) {
            this$0.resetDefault($r);
        }
        return Unit.INSTANCE;
    }

    private final void switchRow(final GalaxyRow r) {
        LinearLayout row = (LinearLayout) CarUi.row$default(this.ui, r.getLabel(), desc(r), 0, icon(r), tag(r), 4, null).component1();
        defaultButton(row, r);
        Switch sw = this.ui.bigSwitch();
        sw.setChecked(Intrinsics.areEqual(r.getValue(), "On"));
        sw.setEnabled(r.getEditable());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                GalaxySettingsView.switchRow$lambda$43(r, GalaxySettingsView.this, compoundButton, z);
            }
        });
        row.addView(sw);
        dim(row, r);
        this.col.addView(row, CarUi.lp$default(this.ui, 0, 0, 3, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void switchRow$lambda$43(GalaxyRow $r, GalaxySettingsView this$0, CompoundButton b, boolean on) {
        if (!$r.getEditable() || this$0.busy) {
            b.setChecked(!on);
        } else {
            this$0.saveValue($r, on ? "On" : "Off");
        }
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;Type inference failed;incorrect) */
    private final void selectRow(GalaxyRow var1) {
        int var2 = var1.getChoices().size();
        int var7;
        if (2 <= var2 && var2 < 5) {
            var7 = 1;
        } else {
            var7 = 0;
        }

        label34: {
            if ((var7 != 0)) {
                Iterator var3 = var1.getChoices().iterator();
                var7 = 0;

                while (var3.hasNext()) {
                    String var4 = (String)var3.next();
                    var7 += var4.length();
                }

                if (var7 <= 40) {
                    var7 = (((boolean)true) ? 1 : 0);
                    break label34;
                }
            }

            var7 = (((boolean)false) ? 1 : 0);
        }

        if ((var7 != 0)) {
            LinearLayout var15 = new LinearLayout(this.getContext());
            var15.setOrientation(1);
            var15.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 0, null, 6, null));
            var15.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
            LinearLayout var11 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), this.desc(var1), 0, this.icon(var1), this.tag(var1), 4, null)
                .component1();
            var11.setBackground(null);
            var11.setPadding(0, 0, 0, 0);
            var11.setMinimumHeight(0);
            this.defaultButton(var11, var1);
            var15.addView((View)var11);
            ObjectRef var12 = new ObjectRef();
            Pair var6 = this.ui.chips(var1.getChoices(), new GalaxySettingsView$$ExternalSyntheticLambda20(var1, this, var12));
            LinearLayout var5 = (LinearLayout)var6.component1();
            var12.element = ((Function1)var6.component2());
            var5.setPadding(0, this.ui.dp(10), 0, 0);
            var15.addView((View)var5);
            Function1 var13;
            if (var12.element == null) {
                Intrinsics.throwUninitializedPropertyAccessException("paint");
                var13 = null;
            } else {
                var13 = (Function1)var12.element;
            }

            var13.invoke(var1.getChoices().indexOf(var1.getValue()));
            this.dim((View)var15, var1);
            this.col.addView((View)var15, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
        } else {
            LinearLayout var10 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), this.desc(var1), 0, this.icon(var1), this.tag(var1), 4, null)
                .component1();
            this.defaultButton(var10, var1);
            TextView var14 = CarUi.button$default(this.ui, var1.getValue(), false, new GalaxySettingsView$$ExternalSyntheticLambda21(var1, this), 2, null);
            var14.setMinWidth(this.ui.dp(200));
            var10.addView((View)var14);
            this.dim((View)var10, var1);
            this.col.addView((View)var10, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit selectRow$lambda$46(GalaxyRow $r, GalaxySettingsView this$0, Ref.ObjectRef $paint, int i) {
        Function1 function1;
        if ($r.getEditable() && !this$0.busy && !Intrinsics.areEqual($r.getChoices().get(i), $r.getValue())) {
            if ($paint.element == null) {
                Intrinsics.throwUninitializedPropertyAccessException("paint");
                function1 = null;
            } else {
                function1 = (Function1) $paint.element;
            }
            function1.invoke(Integer.valueOf(i));
            this$0.saveValue($r, $r.getChoices().get(i));
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit selectRow$lambda$48(final GalaxyRow $r, final GalaxySettingsView this$0) {
        if (!$r.getEditable() || this$0.busy) {
            return Unit.INSTANCE;
        }
        AlertDialog.Builder title = new AlertDialog.Builder(this$0.getContext()).setTitle($r.getLabel());
        Collection $this$toTypedArray$iv = $r.getChoices();
        title.setSingleChoiceItems((CharSequence[]) $this$toTypedArray$iv.toArray(new String[0]), $r.getChoices().indexOf($r.getValue()), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GalaxySettingsView.selectRow$lambda$48$lambda$47($r, this$0, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void selectRow$lambda$48$lambda$47(GalaxyRow $r, GalaxySettingsView this$0, DialogInterface d, int i) {
        d.dismiss();
        if (Intrinsics.areEqual($r.getChoices().get(i), $r.getValue())) {
            return;
        }
        this$0.saveValue($r, $r.getChoices().get(i));
    }

    private final void sliderRow(final GalaxyRow r) {
        final GalaxyRow.Bounds bounds = r.bounds();
        if (bounds == null) {
            readoutRow(r);
            return;
        }
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        linearLayout.setBackground(CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 0, null, 6, null));
        linearLayout.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(10));
        LinearLayout linearLayout2 = (LinearLayout) CarUi.row$default(this.ui, r.getLabel(), desc(r), 0, icon(r), tag(r), 4, null).component1();
        linearLayout2.setBackground(null);
        linearLayout2.setPadding(0, 0, 0, 0);
        linearLayout2.setMinimumHeight(0);
        final TextView textView = new TextView(getContext());
        textView.setTextSize(22.0f);
        textView.setTextColor(CarUi.Companion.getTEXT());
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setGravity(8388613);
        textView.setPadding(this.ui.dp(12), 0, this.ui.dp(4), 0);
        defaultButton(linearLayout2, r);
        linearLayout2.addView(textView);
        linearLayout.addView(linearLayout2);
        final int round = (int) Math.round((bounds.getMax() - bounds.getMin()) / bounds.getStep());
        final int i = round + (bounds.getAuto() ? 1 : 0);
        final String shown$default = GalaxyRow.shown$default(r, null, 1, null);
        textView.setText(r.withUnit(shown$default));
        final SeekBar seekBar = new SeekBar(getContext());
        seekBar.setMax(i);
        seekBar.setProgress(sliderRow$posOf(i, bounds, round, shown$default));
        seekBar.setEnabled(r.getEditable());
        seekBar.setMinimumHeight(this.ui.dp(48));
        seekBar.setThumbTintList(ColorStateList.valueOf(-1));
        seekBar.setProgressTintList(ColorStateList.valueOf(CarUi.Companion.getACCENT()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.starpilot.starview.GalaxySettingsView$sliderRow$1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar sb, int p, boolean fromUser) {
                String sliderRow$valueAt;
                String withUnit;
                TextView textView2 = textView;
                GalaxyRow galaxyRow = r;
                sliderRow$valueAt = GalaxySettingsView.sliderRow$valueAt(bounds, round, p);
                withUnit = galaxyRow.withUnit(sliderRow$valueAt);
                textView2.setText(withUnit);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar sb) {
                this.touching = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar sb) {
                this.touching = false;
                GalaxySettingsView.sliderRow$commit(shown$default, r, GalaxySettingsView.this, bounds, round, seekBar.getProgress());
            }
        });
        LinearLayout linearLayout3 = new LinearLayout(getContext());
        linearLayout3.setOrientation(0);
        linearLayout3.setGravity(16);
        linearLayout3.setPadding(0, this.ui.dp(8), 0, 0);
        linearLayout3.addView(this.ui.stepper("−", new Function0() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit sliderRow$lambda$55;
                sliderRow$lambda$55 = GalaxySettingsView.sliderRow$lambda$55(r, GalaxySettingsView.this, seekBar, shown$default, bounds, round);
                return sliderRow$lambda$55;
            }
        }));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, this.ui.dp(48), 1.0f);
        layoutParams.leftMargin = this.ui.dp(8);
        layoutParams.rightMargin = this.ui.dp(8);
        Unit unit = Unit.INSTANCE;
        linearLayout3.addView(seekBar, layoutParams);
        linearLayout3.addView(this.ui.stepper("+", new Function0() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit sliderRow$lambda$57;
                sliderRow$lambda$57 = GalaxySettingsView.sliderRow$lambda$57(r, GalaxySettingsView.this, seekBar, i, shown$default, bounds, round);
                return sliderRow$lambda$57;
            }
        }));
        linearLayout.addView(linearLayout3);
        TextView textView2 = new TextView(getContext());
        textView2.setText(GalaxyRow.Companion.fmt(bounds.getMin()) + " to " + GalaxyRow.Companion.fmt(bounds.getMax()) + ((!(r.getUnit().length() > 0) || r.getLaneStrength()) ? "" : " " + r.getUnit()) + (bounds.getAuto() ? " · Auto at the right" : "") + " · step " + GalaxyRow.Companion.fmt(bounds.getStep()));
        textView2.setTextColor(CarUi.Companion.getDIM());
        textView2.setTextSize(13.0f);
        textView2.setPadding(this.ui.dp(2), this.ui.dp(4), 0, 0);
        linearLayout.addView(textView2);
        dim(linearLayout, r);
        this.col.addView(linearLayout, CarUi.lp$default(this.ui, 0, 0, 3, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String sliderRow$valueAt(GalaxyRow.Bounds b, int steps, int p) {
        return (!b.getAuto() || p <= steps) ? GalaxyRow.Companion.fmt(GalaxyRow.Companion.snap(b.getMin() + (p * b.getStep()), b)) : "Auto";
    }

    private static final int sliderRow$posOf(int maxPos, GalaxyRow.Bounds b, int steps, String v) {
        if (Intrinsics.areEqual(v, "Auto")) {
            return maxPos;
        }
        Double doubleOrNull = StringsKt.toDoubleOrNull(v);
        double it = ((doubleOrNull != null ? doubleOrNull.doubleValue() : b.getMin()) - b.getMin()) / b.getStep();
        return RangesKt.coerceIn((int) Math.round(it), 0, steps);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sliderRow$commit(String shownNow, GalaxyRow $r, GalaxySettingsView this$0, GalaxyRow.Bounds b, int steps, int p) {
        String v = sliderRow$valueAt(b, steps, p);
        if (Intrinsics.areEqual(v, shownNow)) {
            return;
        }
        String out = "Auto";
        if (!Intrinsics.areEqual(v, "Auto") && (out = $r.saved(Double.parseDouble(v))) == null) {
            return;
        }
        this$0.saveValue($r, out);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit sliderRow$lambda$55(GalaxyRow $r, GalaxySettingsView this$0, SeekBar $bar, String $shownNow, GalaxyRow.Bounds $b, int $steps) {
        if ($r.getEditable() && !this$0.busy && $bar.getProgress() > 0) {
            $bar.setProgress($bar.getProgress() - 1);
            sliderRow$commit($shownNow, $r, this$0, $b, $steps, $bar.getProgress());
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit sliderRow$lambda$57(GalaxyRow $r, GalaxySettingsView this$0, SeekBar $bar, int $maxPos, String $shownNow, GalaxyRow.Bounds $b, int $steps) {
        if ($r.getEditable() && !this$0.busy && $bar.getProgress() < $maxPos) {
            $bar.setProgress($bar.getProgress() + 1);
            sliderRow$commit($shownNow, $r, this$0, $b, $steps, $bar.getProgress());
        }
        return Unit.INSTANCE;
    }

    private final void actionRow(final GalaxyRow r) {
        CarUi carUi = this.ui;
        String label = r.getLabel();
        String[] strArr = new String[2];
        strArr[0] = desc(r);
        String value = r.getValue();
        if (StringsKt.isBlank(value)) {
            value = null;
        }
        strArr[1] = value;
        String joinToString$default = CollectionsKt.joinToString(CollectionsKt.listOfNotNull((Object[]) strArr), "\n", "", "", -1, "...", null);
        if (StringsKt.isBlank(joinToString$default)) {
            joinToString$default = null;
        }
        LinearLayout row = (LinearLayout) CarUi.row$default(carUi, label, joinToString$default, 0, icon(r), tag(r), 4, null).component1();
        if (r.getAction()) {
            String str = "Reset";
            if (!Intrinsics.areEqual(r.getRepairValue(), "Reset") && !new Regex("reset|default", RegexOption.IGNORE_CASE).containsMatchIn(r.getLabel())) {
                str = r.getRepairValue().length() > 0 ? "Set " + r.getRepairValue() : "Run";
            }
            String text = str;
            row.addView(CarUi.button$default(this.ui, text, false, new Function0() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda33
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit actionRow$lambda$61;
                    actionRow$lambda$61 = GalaxySettingsView.actionRow$lambda$61(r, GalaxySettingsView.this);
                    return actionRow$lambda$61;
                }
            }, 2, null));
        }
        dim(row, r);
        this.col.addView(row, CarUi.lp$default(this.ui, 0, 0, 3, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit actionRow$lambda$61(GalaxyRow $r, GalaxySettingsView this$0) {
        if ($r.getEditable()) {
            this$0.review($r);
        }
        return Unit.INSTANCE;
    }

    private final void readoutRow(GalaxyRow r) {
        if (r.getHeading() || (StringsKt.isBlank(r.getValue()) && StringsKt.isBlank(r.getReason()))) {
            this.col.addView(this.ui.header(r.getLabel()));
            return;
        }
        LinearLayout row = (LinearLayout) CarUi.row$default(this.ui, r.getLabel(), desc(r), 0, icon(r), tag(r), 4, null).component1();
        if (!StringsKt.isBlank(r.getValue())) {
            TextView $this$readoutRow_u24lambda_u2462 = new TextView(getContext());
            $this$readoutRow_u24lambda_u2462.setText(r.withUnit(GalaxyRow.shown$default(r, null, 1, null)));
            $this$readoutRow_u24lambda_u2462.setTextColor(CarUi.Companion.getDIM());
            $this$readoutRow_u24lambda_u2462.setTextSize(18.0f);
            $this$readoutRow_u24lambda_u2462.setPadding(this.ui.dp(14), 0, this.ui.dp(6), 0);
            $this$readoutRow_u24lambda_u2462.setMaxWidth(this.ui.dp(360));
            row.addView($this$readoutRow_u24lambda_u2462);
        }
        this.col.addView(row, CarUi.lp$default(this.ui, 0, 0, 3, null));
    }

    private final String viewId() {
        String optString;
        JSONObject jSONObject = this.data;
        String str = null;
        if (jSONObject == null || (optString = jSONObject.optString("view")) == null) {
            return null;
        }
        String str2 = optString;
        if (!StringsKt.isBlank(str2)) {
            str = str2;
        }
        return str;
    }

    private final Object jsonValue(GalaxyRow r, String v) {
        if (r.getControl() == GalaxyRow.Control.SLIDER && !Intrinsics.areEqual(v, "Auto")) {
            Long longOrNull = StringsKt.toLongOrNull(v);
            if (longOrNull != null) {
                return longOrNull;
            }
            Double doubleOrNull = StringsKt.toDoubleOrNull(v);
            if (doubleOrNull != null) {
                return doubleOrNull;
            }
        }
        return v;
    }

    private final void saveValue(final GalaxyRow r, String v) {
        String view;
        Galaxy7 gx = this.g;
        if (gx == null || (view = viewId()) == null) {
            return;
        }
        this.busy = true;
        JSONObject put = new JSONObject().put("view", view).put("row", r.getIndex()).put("value", jsonValue(r, v));
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        gx.post("/api/settings/preview", put, new Function2() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit saveValue$lambda$66;
                saveValue$lambda$66 = GalaxySettingsView.saveValue$lambda$66(GalaxySettingsView.this, r, (JSONObject) obj, (String) obj2);
                return saveValue$lambda$66;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveValue$lambda$66(final GalaxySettingsView this$0, final GalaxyRow $r, JSONObject o, String err) {
        String optString;
        String intent = null;
        if (o != null && (optString = o.optString("intent")) != null) {
            String str = optString;
            if (!StringsKt.isBlank(str)) {
                intent = str;
            }
            intent = intent;
        }
        if (intent == null) {
            this$0.failed(err == null ? "Galaxy didn't accept that" : err);
            return Unit.INSTANCE;
        }
        this$0.confirm(intent, new Function0() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit saveValue$lambda$66$lambda$65;
                saveValue$lambda$66$lambda$65 = GalaxySettingsView.saveValue$lambda$66$lambda$65(this$0, $r);
                return saveValue$lambda$66$lambda$65;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveValue$lambda$66$lambda$65(GalaxySettingsView this$0, GalaxyRow $r) {
        this$0.previewSound($r);
        return Unit.INSTANCE;
    }

    private final void previewSound(GalaxyRow r) {
        Galaxy7 galaxy7;
        if (!(r.getPreviewSound().length() == 0) && (galaxy7 = this.g) != null) {
            JSONObject put = new JSONObject().put("key", r.getPreviewSound());
            Intrinsics.checkNotNullExpressionValue(put, "put(...)");
            galaxy7.post("/api/sounds/preview", put, new Function2() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda18
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit previewSound$lambda$67;
                    previewSound$lambda$67 = GalaxySettingsView.previewSound$lambda$67((JSONObject) obj, (String) obj2);
                    return previewSound$lambda$67;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit previewSound$lambda$67(JSONObject jSONObject, String str) {
        return Unit.INSTANCE;
    }

    private final void resetDefault(GalaxyRow r) {
        String view;
        Galaxy7 gx = this.g;
        if (gx == null || (view = viewId()) == null) {
            return;
        }
        this.busy = true;
        JSONObject put = new JSONObject().put("view", view).put("row", r.getIndex());
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        gx.post("/api/settings/reset-default", put, new Function2() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit resetDefault$lambda$69;
                resetDefault$lambda$69 = GalaxySettingsView.resetDefault$lambda$69(GalaxySettingsView.this, (JSONObject) obj, (String) obj2);
                return resetDefault$lambda$69;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit resetDefault$lambda$69(GalaxySettingsView this$0, JSONObject o, String err) {
        String intent;
        String optString;
        if (o == null || (optString = o.optString("intent")) == null) {
            intent = null;
        } else {
            String str = optString;
            if (StringsKt.isBlank(str)) {
                str = null;
            }
            intent = str;
        }
        if (intent == null) {
            this$0.failed(err == null ? "Galaxy didn't accept that" : err);
            return Unit.INSTANCE;
        }
        confirm$default(this$0, intent, null, 2, null);
        return Unit.INSTANCE;
    }

    private final void reviewDraft(int row, JSONObject draft) {
        String view;
        Galaxy7 gx = this.g;
        if (gx == null || (view = viewId()) == null || this.busy) {
            return;
        }
        this.busy = true;
        JSONObject put = new JSONObject().put("view", view).put("row", row).put("direction", 0).put("draft", draft);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        gx.post("/api/settings/preview", put, new Function2() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit reviewDraft$lambda$74;
                reviewDraft$lambda$74 = GalaxySettingsView.reviewDraft$lambda$74(GalaxySettingsView.this, (JSONObject) obj, (String) obj2);
                return reviewDraft$lambda$74;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit reviewDraft$lambda$74(final GalaxySettingsView this$0, JSONObject o, String err) {
        String optString;
        String intent = null;
        if (o != null && (optString = o.optString("intent")) != null) {
            String str = optString;
            if (!StringsKt.isBlank(str)) {
                intent = str;
            }
            intent = intent;
        }
        if (intent == null) {
            this$0.failed(err == null ? "Galaxy didn't accept those regions" : err);
            return Unit.INSTANCE;
        }
        final var intent$f1 = intent;
        new AlertDialog.Builder(this$0.getContext()).setTitle("Save changes?").setMessage(o.optString("question", "Save the camera window regions?")).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GalaxySettingsView.confirm$default(this$0, intent$f1, null, 2, null);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GalaxySettingsView.reviewDraft$lambda$74$lambda$72(this$0, dialogInterface, i);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                GalaxySettingsView.reviewDraft$lambda$74$lambda$73(this$0, dialogInterface);
            }
        }).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void reviewDraft$lambda$74$lambda$72(GalaxySettingsView this$0, DialogInterface dialogInterface, int i) {
        this$0.busy = false;
        this$0.load(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void reviewDraft$lambda$74$lambda$73(GalaxySettingsView this$0, DialogInterface it) {
        this$0.busy = false;
        this$0.load(true);
    }

    private final void review(final GalaxyRow r) {
        String viewId;
        Galaxy7 galaxy7 = this.g;
        if (galaxy7 == null || (viewId = viewId()) == null) {
            return;
        }
        this.busy = true;
        JSONObject put = new JSONObject().put("view", viewId).put("row", r.getIndex()).put("direction", 1 ^ (r.getConfirm() ? 1 : 0));
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        galaxy7.post("/api/settings/preview", put, new Function2() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit review$lambda$79;
                review$lambda$79 = GalaxySettingsView.review$lambda$79(GalaxySettingsView.this, r, (JSONObject) obj, (String) obj2);
                return review$lambda$79;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit review$lambda$79(final GalaxySettingsView this$0, GalaxyRow $r, JSONObject o, String err) {
        String optString;
        String intent = null;
        if (o != null && (optString = o.optString("intent")) != null) {
            String str = optString;
            if (!StringsKt.isBlank(str)) {
                intent = str;
            }
            intent = intent;
        }
        if (intent == null) {
            this$0.failed(err == null ? "Galaxy didn't accept that" : err);
            return Unit.INSTANCE;
        }
        final var intent$f = intent;
        new AlertDialog.Builder(this$0.getContext()).setTitle("Confirm").setMessage(o.optString("question", "Apply " + $r.getLabel() + "?")).setPositiveButton("Confirm", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda37
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GalaxySettingsView.confirm$default(this$0, intent$f, null, 2, null);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda38
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GalaxySettingsView.review$lambda$79$lambda$77(this$0, dialogInterface, i);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                GalaxySettingsView.review$lambda$79$lambda$78(this$0, dialogInterface);
            }
        }).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void review$lambda$79$lambda$77(GalaxySettingsView this$0, DialogInterface dialogInterface, int i) {
        this$0.busy = false;
        this$0.load(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void review$lambda$79$lambda$78(GalaxySettingsView this$0, DialogInterface it) {
        this$0.busy = false;
        this$0.load(true);
    }

    /* VF: from Vineflower (jadx: ) */
    public static void confirm$default(GalaxySettingsView var0, String var1, Function0 var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = new GalaxySettingsView$$ExternalSyntheticLambda26();
        }

        var0.confirm(var1, (Function0<Unit>)var2);
    }

    private final void confirm(String intent, final Function0<Unit> saved) {
        String page;
        Galaxy7 gx = this.g;
        if (gx == null || (page = getCurrent()) == null) {
            return;
        }
        JSONObject put = new JSONObject().put("intent", intent).put("confirmed", true).put("page", page);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        gx.post("/api/settings/confirm", put, new Function2() { // from class: com.starpilot.starview.GalaxySettingsView$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit confirm$lambda$81;
                confirm$lambda$81 = GalaxySettingsView.confirm$lambda$81(GalaxySettingsView.this, saved, (JSONObject) obj, (String) obj2);
                return confirm$lambda$81;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit confirm$lambda$81(GalaxySettingsView this$0, Function0 $saved, JSONObject o, String err) {
        boolean z = false;
        this$0.busy = false;
        if (o != null && o.optBoolean("saved")) {
            z = true;
        }
        if (!z) {
            this$0.failed(err == null ? "Galaxy couldn't confirm the save - current values are shown" : err);
            return Unit.INSTANCE;
        }
        $saved.invoke();
        JSONObject fresh = o.optJSONObject("page");
        if (fresh == null || !Intrinsics.areEqual(fresh.optString("page"), this$0.getCurrent())) {
            this$0.load(true);
        } else {
            this$0.data = fresh;
            this$0.draw(fresh);
        }
        return Unit.INSTANCE;
    }

    private final void failed(String err) {
        this.busy = false;
        Toast.makeText(getContext(), err, 1).show();
        this.lastShown = "";
        load(true);
    }

    /* compiled from: GalaxySettingsView.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
