package com.starpilot.starview;

import com.starpilot.starview.GalaxyRow;
import com.starpilot.starview.SettingIcons;
import com.starpilot.starview.SettingsIndex;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;

/* compiled from: SettingsIndex.kt */
public final class SettingsIndex {
    public static final SettingsIndex INSTANCE = new SettingsIndex();

    /* compiled from: SettingsIndex.kt */
    public static final class Summary {
        private final String line;
        private final Boolean on;

        public Summary(Boolean on, String line) {
            Intrinsics.checkNotNullParameter(line, "line");
            this.on = on;
            this.line = line;
        }

        public final String getLine() {
            return this.line;
        }

        public final Boolean getOn() {
            return this.on;
        }
    }

    private SettingsIndex() {
    }

    public static /* synthetic */ Summary summary$default(SettingsIndex settingsIndex, List list, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "";
        }
        return settingsIndex.summary(list, str);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final SettingsIndex.Summary summary(List<GalaxyRow> var1, String var2) {
        Object var12 = null; Object var19 = null; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "rows");
        Intrinsics.checkNotNullParameter(var2, "title");
        Iterable var4 = var1;
        var var10 = new ArrayList();

        for (Object var4$it : (Iterable) var4) {
            var4 = (Iterable) var4$it;
            Object var6 = (GalaxyRow)var4;
            boolean var3;
            if (!GalaxyRow.Companion.isPipCrop(((GalaxyRow) var6)) && ((GalaxyRow) var6).getControl() != GalaxyRow.Control.GROUP && ((GalaxyRow) var6).getControl() != GalaxyRow.Control.ACTION) {
                var3 = true;
            } else {
                var3 = false;
            }

            if (var3) {
                var10.add(var4);
            }
        }

        List var28 = var10;
        String var11 = var2.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(var11, "toLowerCase(...)");
        var2 = StringsKt.trim(StringsKt.removeSuffix(var11, " personality")).toString();
        Iterator var7 = var28.iterator();

        boolean var22;
        do {
            if (!var7.hasNext()) {
                var12 = null;
                break;
            }

            label150: {
                var12 = var7.next();
                GalaxyRow var32 = (GalaxyRow)var12;
                if (var32.getControl() == GalaxyRow.Control.SWITCH) {
                    if (new Regex("^(enable|use|allow) ", RegexOption.IGNORE_CASE).containsMatchIn(var32.getLabel())) {
                        break label150;
                    }

                    if (var2.length() > 0) {
                        var22 = true;
                    } else {
                        var22 = false;
                    }

                    if (var22) {
                        String var34 = var32.getLabel().toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var34, "toLowerCase(...)");
                        if (Intrinsics.areEqual(var34, var2)) {
                            break label150;
                        }
                    }

                    if (StringsKt.endsWith(var32.getLabel(), " Controller", false)) {
                        break label150;
                    }
                }

                var22 = false;
                continue;
            }

            var22 = true;
        } while (!var22);

        GalaxyRow var35 = (GalaxyRow)var12;
        var2 = "On";
        Boolean var13;
        if (var35 != null) {
            var13 = Intrinsics.areEqual(var35.getValue(), "On");
        } else {
            var13 = null;
        }

        ArrayList var33 = new ArrayList();
        if (var35 != null) {
            if (!Intrinsics.areEqual(var13, true)) {
                var2 = "Off";
            }

            var33.add(var2);
        }

        ArrayList var37 = new ArrayList();

        for (GalaxyRow var9 : (Iterable<GalaxyRow>) var28) {
            if (var37.size() < 2 && var9 != var35) {
                if (StringsKt.toDoubleOrNull(var9.getValue()) != null) {
                    var22 = true;
                } else {
                    var22 = false;
                }

                if (var9.getControl() == GalaxyRow.Control.SELECT) {
                    var2 = var9.getValue();
                    if (!StringsKt.isBlank(var2) && var2.length() <= 26 && !Intrinsics.areEqual(var2, "Selected Profile")) {
                        var22 = true;
                    } else {
                        var22 = false;
                    }

                    if (!var22) {
                        var2 = null;
                    }

                    if (var2 != null) {
                        var37.add(var2);
                    }
                } else if (var9.getControl() == GalaxyRow.Control.SLIDER || var9.getControl() == GalaxyRow.Control.READOUT && var22) {
                    if (var9.getUnit().length() > 0) {
                        var22 = true;
                    } else {
                        var22 = false;
                    }

                    if (var22) {
                        var2 = this.shortLabel(var9.getLabel());
                        String var38 = var9.withUnit(GalaxyRow.shown$default(var9, null, 1, null));
                        var37.add(var2 + " " + var38);
                    }
                }
            }
        }

        var33.addAll(var37);
        if (var35 == null && var37.isEmpty()) {
            label109: {
                for (Object var29 : var28) {
                    if (((GalaxyRow)var29).getControl() == GalaxyRow.Control.SWITCH) {
                        var22 = true;
                    } else {
                        var22 = false;
                    }

                    if (var22) {
                        var19 = var29;
                        break label109;
                    }
                }

                var19 = null;
            }

            GalaxyRow var30 = (GalaxyRow)var19;
            if (var30 != null) {
                var2 = var30.getLabel();
                String var31 = var30.getValue().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(var31, "toLowerCase(...)");
                var33.add(var2 + " " + var31);
            }
        }

        return new SettingsIndex.Summary(var13, CollectionsKt.joinToString(var33, " · ", "", "", -1, "...", null));
    }

    /* renamed from: short, reason: not valid java name */
    public final String shortLabel(String label) {
        Intrinsics.checkNotNullParameter(label, "label");
        String l = label.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(l, "toLowerCase(...)");
        return StringsKt.contains((CharSequence) l, (CharSequence) "strength", false) ? "strength" : StringsKt.contains((CharSequence) l, (CharSequence) "offset", false) ? "offset" : (StringsKt.contains((CharSequence) l, (CharSequence) "minimum", false) && StringsKt.contains((CharSequence) l, (CharSequence) "speed", false)) ? "min speed" : StringsKt.contains((CharSequence) l, (CharSequence) "delay", false) ? "delay" : StringsKt.contains((CharSequence) l, (CharSequence) "high-speed follow", false) ? "highway" : StringsKt.contains((CharSequence) l, (CharSequence) "follow", false) ? "follow" : StringsKt.contains((CharSequence) l, (CharSequence) "threshold", false) ? "below" : CollectionsKt.joinToString(CollectionsKt.takeLast(StringsKt.split((CharSequence) l, new char[]{' '}, false, 0), 2), " ", "", "", -1, "...", null);
    }

    /* compiled from: SettingsIndex.kt */
    public static final class Entry {
        private final SettingIcons.Icon icon;
        private final String label;
        private final String reason;
        private final String search;
        private final String target;
        private final String unit;
        private final String value;
        private final String where;

        public Entry(String label, String value, String where, String target, String reason, String unit, SettingIcons.Icon icon) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(where, "where");
            Intrinsics.checkNotNullParameter(target, "target");
            Intrinsics.checkNotNullParameter(reason, "reason");
            Intrinsics.checkNotNullParameter(unit, "unit");
            this.label = label;
            this.value = value;
            this.where = where;
            this.target = target;
            this.reason = reason;
            this.unit = unit;
            this.icon = icon;
            String lowerCase = CollectionsKt.joinToString(CollectionsKt.listOf(new String[]{this.label, this.value, this.reason, this.unit, this.where}), " ", "", "", -1, "...", null).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            this.search = lowerCase;
        }

        public /* synthetic */ Entry(String str, String str2, String str3, String str4, String str5, String str6, SettingIcons.Icon icon, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, str3, str4, (i & 16) != 0 ? "" : str5, (i & 32) != 0 ? "" : str6, (i & 64) != 0 ? null : icon);
        }

        public final String getLabel() {
            return this.label;
        }

        public final String getReason() {
            return this.reason;
        }

        public final String getTarget() {
            return this.target;
        }

        public final String getUnit() {
            return this.unit;
        }

        public final String getValue() {
            return this.value;
        }

        public final String getWhere() {
            return this.where;
        }

        public final SettingIcons.Icon getIcon() {
            return this.icon;
        }

        public final String getSearch() {
            return this.search;
        }
    }

    public static /* synthetic */ List search$default(SettingsIndex settingsIndex, List list, String str, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 40;
        }
        return settingsIndex.search(list, str, i);
    }

    public final List<Entry> search(List<Entry> entries, String query, int limit) {
        Iterable $this$filter$iv;
        int $i$f$filter;
        int $i$f$filter2;
        boolean z;
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(query, "query");
        String lowerCase = query.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        int i = 0;
        Iterable $this$filter$iv2 = new Regex("\\s+").split(lowerCase, 0);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv2) {
            if (((String) element$iv$iv).length() > 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        final List words = (List) destination$iv$iv;
        if (words.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        List<Entry> $this$filter$iv3 = entries;
        int $i$f$filter3 = 0;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv3) {
            Entry e = (Entry) element$iv$iv2;
            List $this$all$iv = words;
            if (($this$all$iv instanceof Collection) && $this$all$iv.isEmpty()) {
                $this$filter$iv = $this$filter$iv3;
                $i$f$filter = $i$f$filter3;
                z = true;
                $i$f$filter2 = i;
            } else {
                Iterator it = $this$all$iv.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        $this$filter$iv = $this$filter$iv3;
                        $i$f$filter = $i$f$filter3;
                        $i$f$filter2 = i;
                        z = true;
                        break;
                    }
                    Object element$iv = it.next();
                    $this$filter$iv = $this$filter$iv3;
                    $i$f$filter = $i$f$filter3;
                    $i$f$filter2 = 0;
                    if (!StringsKt.contains((CharSequence) e.getSearch(), (CharSequence) element$iv, false)) {
                        z = false;
                        break;
                    }
                    i = 0;
                    $this$filter$iv3 = $this$filter$iv;
                    $i$f$filter3 = $i$f$filter;
                }
            }
            if (z) {
                destination$iv$iv2.add(element$iv$iv2);
            }
            i = $i$f$filter2;
            $this$filter$iv3 = $this$filter$iv;
            $i$f$filter3 = $i$f$filter;
        }
        final Comparator comparator = new Comparator() { // from class: com.starpilot.starview.SettingsIndex$search$$inlined$compareByDescending$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(Object t, Object t2) {
                SettingsIndex.Entry it2 = (SettingsIndex.Entry) t2;
                String lowerCase2 = it2.getLabel().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                Boolean valueOf = Boolean.valueOf(StringsKt.startsWith(lowerCase2, (String) words.get(0), false));
                SettingsIndex.Entry it3 = (SettingsIndex.Entry) t;
                String lowerCase3 = it3.getLabel().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
                return ComparisonsKt.compareValues(valueOf, Boolean.valueOf(StringsKt.startsWith(lowerCase3, (String) words.get(0), false)));
            }
        };
        Iterable $this$distinctBy$iv = CollectionsKt.sortedWith((List) destination$iv$iv2, new Comparator() { // from class: com.starpilot.starview.SettingsIndex$search$$inlined$thenBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(Object t, Object t2) {
                int previousCompare = comparator.compare(t, t2);
                if (previousCompare != 0) {
                    return previousCompare;
                }
                SettingsIndex.Entry it2 = (SettingsIndex.Entry) t;
                String lowerCase2 = it2.getLabel().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                SettingsIndex.Entry it3 = (SettingsIndex.Entry) t2;
                String lowerCase3 = it3.getLabel().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
                return ComparisonsKt.compareValues(lowerCase2, lowerCase3);
            }
        });
        HashSet set$iv = new HashSet();
        ArrayList list$iv = new ArrayList();
        for (Object e$iv : $this$distinctBy$iv) {
            Entry it2 = (Entry) e$iv;
            if (set$iv.add(it2.getLabel() + "|" + it2.getTarget())) {
                list$iv.add(e$iv);
            }
        }
        ArrayList $this$distinctBy$iv2 = list$iv;
        return CollectionsKt.take($this$distinctBy$iv2, limit);
    }

    public final List<String> linkedPages(List<GalaxyRow> rows) {
        Intrinsics.checkNotNullParameter(rows, "rows");
        List<GalaxyRow> $this$mapNotNull$iv = rows;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            GalaxyRow it = (GalaxyRow) element$iv$iv$iv;
            String page = it.getPage();
            if (page.length() == 0) {
                page = null;
            }
            String str = page;
            if (str != null) {
                destination$iv$iv.add(str);
            }
        }
        return CollectionsKt.distinct((List) destination$iv$iv);
    }
}
