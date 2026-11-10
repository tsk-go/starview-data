package com.starpilot.starview;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.starpilot.starview.GalaxyRow;
import com.starpilot.starview.SettingsIndex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: SettingsHome.kt */
public final class GalaxyPages {
    public static final GalaxyPages INSTANCE = new GalaxyPages();
    private static final HashMap<String, Hit> cache = new HashMap<>();
    private static final Handler main = new Handler(Looper.getMainLooper());

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SettingsHome.kt */
    static final class Hit {
        private final long at;
        private final JSONObject page;

        public Hit(long at, JSONObject page) {
            Intrinsics.checkNotNullParameter(page, "page");
            this.at = at;
            this.page = page;
        }

        public final long getAt() {
            return this.at;
        }

        public final JSONObject getPage() {
            return this.page;
        }
    }

    private GalaxyPages() {
    }

    public static /* synthetic */ void get$default(GalaxyPages galaxyPages, String str, long j, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            j = 15000;
        }
        galaxyPages.get(str, j, function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void get$lambda$2$lambda$1(Function1 $done, Hit $h) {
        $done.invoke($h.getPage());
    }

    public final void get(final String page, long maxAgeMs, final Function1<? super JSONObject, Unit> done) {
        Intrinsics.checkNotNullParameter(page, "page");
        Intrinsics.checkNotNullParameter(done, "done");
        final Hit h = cache.get(page);
        if (h != null) {
            if (!(System.currentTimeMillis() - h.getAt() < maxAgeMs)) {
                h = null;
            }
            if (h != null) {
                main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyPages$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        GalaxyPages.get$lambda$2$lambda$1(done, h);
                    }
                });
                return;
            }
        }
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyPages$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    done.invoke(null);
                }
            });
        } else {
            g.get("/api/settings/pages/" + Uri.encode(page), new Function2() { // from class: com.starpilot.starview.GalaxyPages$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit unit;
                    unit = GalaxyPages.get$lambda$4(page, done, (JSONObject) obj, (String) obj2);
                    return unit;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit get$lambda$4(String $page, Function1 $done, JSONObject o, String str) {
        if (o != null) {
            cache.put($page, new Hit(System.currentTimeMillis(), o));
        }
        $done.invoke(o);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit summary$lambda$6(Function1 $done, JSONObject o) {
        SettingsIndex.Summary summary;
        if (o != null) {
            SettingsIndex settingsIndex = SettingsIndex.INSTANCE;
            List<GalaxyRow> parse = GalaxyRow.Companion.parse(o);
            String optString = o.optString("title");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            summary = settingsIndex.summary(parse, optString);
        } else {
            summary = null;
        }
        $done.invoke(summary);
        return Unit.INSTANCE;
    }

    public final void summary(String page, final Function1<? super SettingsIndex.Summary, Unit> done) {
        Intrinsics.checkNotNullParameter(page, "page");
        Intrinsics.checkNotNullParameter(done, "done");
        get$default(this, page, 0L, new Function1() { // from class: com.starpilot.starview.GalaxyPages$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit summary$lambda$6;
                summary$lambda$6 = GalaxyPages.summary$lambda$6(done, (JSONObject) obj);
                return summary$lambda$6;
            }
        }, 2, null);
    }

    public final void crawl(Function1<? super List<SettingsIndex.Entry>, Unit> onEntries) {
        Intrinsics.checkNotNullParameter(onEntries, "onEntries");
        ArrayList out = new ArrayList();
        HashSet seen = new HashSet();
        crawl$visit(seen, out, onEntries, "hub", 0, "Driving controls");
    }

    private static final void crawl$visit(final HashSet<String> hashSet, final ArrayList<SettingsIndex.Entry> arrayList, final Function1<? super List<SettingsIndex.Entry>, Unit> function1, final String page, final int depth, final String title) {
        if (hashSet.add(page)) {
            INSTANCE.get(page, 60000L, new Function1() { // from class: com.starpilot.starview.GalaxyPages$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit crawl$visit$lambda$8;
                    crawl$visit$lambda$8 = GalaxyPages.crawl$visit$lambda$8(page, depth, arrayList, function1, title, hashSet, (JSONObject) obj);
                    return crawl$visit$lambda$8;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit crawl$visit$lambda$8(String $page, int $depth, ArrayList $out, Function1 $onEntries, String $title, HashSet $seen, JSONObject o) {
        if (o == null) {
            return Unit.INSTANCE;
        }
        String optString = o.optString("title");
        if (StringsKt.isBlank(optString)) {
            optString = $title;
        }
        String t = optString;
        List rows = GalaxyRow.Companion.visible($page, GalaxyRow.Companion.parse(o));
        ArrayList add = new ArrayList();
        String str = "|";
        String str2 = "gsub:";
        int i = 1;
        if (!Intrinsics.areEqual($page, "hub")) {
            Iterator<GalaxyRow> it = rows.iterator();
            while (it.hasNext()) {
                GalaxyRow r = it.next();
                if ((r.getPage().length() == 0 ? i : 0) == 0 || StringsKt.isBlank(r.getLabel())) {
                    i = 1;
                } else if (r.getControl() != GalaxyRow.Control.READOUT || !StringsKt.isBlank(r.getValue())) {
                    String label = r.getLabel();
                    String withUnit = r.withUnit(GalaxyRow.shown$default(r, null, i, null));
                    Intrinsics.checkNotNull(t);
                    add.add(new SettingsIndex.Entry(label, withUnit, t, "gsub:" + $page + "|" + t, r.getReason(), r.getUnit(), SettingIcons.INSTANCE.forRow($page, r.getLabel(), "", r.getControl())));
                    it = it;
                    i = 1;
                }
            }
        }
        for (GalaxyRow r2 : rows) {
            if (r2.getPage().length() > 0) {
                String label2 = r2.getLabel();
                Intrinsics.checkNotNull(t);
                add.add(new SettingsIndex.Entry(label2, "", t, str2 + r2.getPage() + str + r2.getLabel(), r2.getValue(), "", SettingIcons.INSTANCE.forPage(r2.getPage())));
                if ($depth < 3) {
                    crawl$visit($seen, $out, $onEntries, r2.getPage(), $depth + 1, r2.getLabel());
                    add = add;
                    t = t;
                    str2 = str2;
                    str = str;
                }
            }
        }
        $out.addAll(add);
        $onEntries.invoke(new ArrayList($out));
        return Unit.INSTANCE;
    }
}
