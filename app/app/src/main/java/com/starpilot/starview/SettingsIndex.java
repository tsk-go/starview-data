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

    /* JADX WARN: Code restructure failed: missing block: B:131:0x00d4, code lost:
    
        if (kotlin.text.StringsKt.endsWith$default(r13.getLabel(), " Controller", false, 2, (java.lang.Object) null) != false) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:129:? A[LOOP:1: B:20:0x0078->B:129:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00e1 A[EDGE_INSN: B:36:0x00e1->B:37:0x00e1 BREAK  A[LOOP:1: B:20:0x0078->B:129:?], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Summary summary(List<GalaxyRow> rows, String title) {
        int i;
        Object obj;
        boolean z;
        Object element$iv;
        Object obj2;
        boolean z2;
        Intrinsics.checkNotNullParameter(rows, "rows");
        Intrinsics.checkNotNullParameter(title, "title");
        List<GalaxyRow> $this$filter$iv = rows;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GalaxyRow it = (GalaxyRow) element$iv$iv;
            if ((GalaxyRow.Companion.isPipCrop(it) || it.getControl() == GalaxyRow.Control.GROUP || it.getControl() == GalaxyRow.Control.ACTION) ? false : true) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List<GalaxyRow> usable = (List) destination$iv$iv;
        String lowerCase = title.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        String t = StringsKt.trim((CharSequence) StringsKt.removeSuffix(lowerCase, (CharSequence) " personality")).toString();
        Iterator it2 = usable.iterator();
        while (true) {
            i = 2;
            obj = null;
            if (!it2.hasNext()) {
                z = false;
                element$iv = null;
                break;
            }
            element$iv = it2.next();
            GalaxyRow it3 = (GalaxyRow) element$iv;
            if (it3.getControl() == GalaxyRow.Control.SWITCH) {
                if (new Regex("^(enable|use|allow) ", RegexOption.IGNORE_CASE).containsMatchIn(it3.getLabel())) {
                    z = false;
                } else {
                    if (t.length() > 0) {
                        String lowerCase2 = it3.getLabel().toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                        if (Intrinsics.areEqual(lowerCase2, t)) {
                            z = false;
                        }
                    }
                    z = false;
                }
                z2 = true;
                if (!z2) {
                    break;
                }
            } else {
                z = false;
            }
            z2 = z;
            if (!z2) {
            }
        }
        GalaxyRow main = (GalaxyRow) element$iv;
        Boolean on = main != null ? Boolean.valueOf(Intrinsics.areEqual(main.getValue(), "On")) : null;
        ArrayList parts = new ArrayList();
        if (main != null) {
            parts.add(Intrinsics.areEqual((Object) on, (Object) true) ? "On" : "Off");
        }
        ArrayList values = new ArrayList();
        for (GalaxyRow r : usable) {
            if (values.size() >= i) {
                i = 2;
                z = false;
            } else if (r != main) {
                boolean numeric = StringsKt.toDoubleOrNull(r.getValue()) != null ? true : z;
                if (r.getControl() == GalaxyRow.Control.SELECT) {
                    String it4 = r.getValue();
                    if (!((StringsKt.isBlank(it4) || it4.length() > 26 || Intrinsics.areEqual(it4, "Selected Profile")) ? false : true)) {
                        it4 = null;
                    }
                    if (it4 != null) {
                        values.add(it4);
                        i = 2;
                        obj = null;
                        z = false;
                    } else {
                        i = 2;
                        obj = null;
                        z = false;
                    }
                } else {
                    if (r.getControl() != GalaxyRow.Control.SLIDER && (r.getControl() != GalaxyRow.Control.READOUT || !numeric)) {
                        i = 2;
                        obj = null;
                    } else if (r.getUnit().length() > 0) {
                        values.add(m1162short(r.getLabel()) + " " + r.withUnit(GalaxyRow.shown$default(r, null, 1, null)));
                        obj = null;
                        i = 2;
                        z = false;
                    } else {
                        obj = null;
                        i = 2;
                    }
                    z = false;
                }
            }
        }
        parts.addAll(values);
        if (main == null && values.isEmpty()) {
            List $this$firstOrNull$iv = usable;
            Iterator it5 = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it5.hasNext()) {
                    obj2 = null;
                    break;
                }
                Object element$iv2 = it5.next();
                Iterable $this$firstOrNull$iv2 = $this$firstOrNull$iv;
                if (((GalaxyRow) element$iv2).getControl() == GalaxyRow.Control.SWITCH) {
                    obj2 = element$iv2;
                    break;
                }
                $this$firstOrNull$iv = $this$firstOrNull$iv2;
            }
            GalaxyRow it6 = (GalaxyRow) obj2;
            if (it6 != null) {
                String label = it6.getLabel();
                String lowerCase3 = it6.getValue().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
                parts.add(label + " " + lowerCase3);
            }
        }
        return new Summary(on, CollectionsKt.joinToString$default(parts, " · ", null, null, 0, null, null, 62, null));
    }

    /* renamed from: short, reason: not valid java name */
    public final String m1162short(String label) {
        Intrinsics.checkNotNullParameter(label, "label");
        String l = label.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(l, "toLowerCase(...)");
        return StringsKt.contains$default((CharSequence) l, (CharSequence) "strength", false, 2, (Object) null) ? "strength" : StringsKt.contains$default((CharSequence) l, (CharSequence) "offset", false, 2, (Object) null) ? "offset" : (StringsKt.contains$default((CharSequence) l, (CharSequence) "minimum", false, 2, (Object) null) && StringsKt.contains$default((CharSequence) l, (CharSequence) "speed", false, 2, (Object) null)) ? "min speed" : StringsKt.contains$default((CharSequence) l, (CharSequence) "delay", false, 2, (Object) null) ? "delay" : StringsKt.contains$default((CharSequence) l, (CharSequence) "high-speed follow", false, 2, (Object) null) ? "highway" : StringsKt.contains$default((CharSequence) l, (CharSequence) "follow", false, 2, (Object) null) ? "follow" : StringsKt.contains$default((CharSequence) l, (CharSequence) "threshold", false, 2, (Object) null) ? "below" : CollectionsKt.joinToString$default(CollectionsKt.takeLast(StringsKt.split$default((CharSequence) l, new char[]{' '}, false, 0, 6, (Object) null), 2), " ", null, null, 0, null, null, 62, null);
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
            String lowerCase = CollectionsKt.joinToString$default(CollectionsKt.listOf((Object[]) new String[]{this.label, this.value, this.reason, this.unit, this.where}), " ", null, null, 0, null, null, 62, null).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            this.search = lowerCase;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public /* synthetic */ Entry(String str, String str2, String str3, String str4, String str5, String str6, SettingIcons.Icon icon, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, str3, str4, r7, r8, r9);
            SettingIcons.Icon icon2;
            String str7 = (i & 16) != 0 ? "" : str5;
            String str8 = (i & 32) != 0 ? "" : str6;
            if ((i & 64) == 0) {
                icon2 = icon;
            } else {
                icon2 = null;
            }
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
                    if (!StringsKt.contains$default((CharSequence) e.getSearch(), (CharSequence) element$iv, false, 2, (Object) null)) {
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
            public final int compare(T t, T t2) {
                SettingsIndex.Entry it2 = (SettingsIndex.Entry) t2;
                String lowerCase2 = it2.getLabel().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                Boolean valueOf = Boolean.valueOf(StringsKt.startsWith$default(lowerCase2, (String) words.get(0), false, 2, (Object) null));
                SettingsIndex.Entry it3 = (SettingsIndex.Entry) t;
                String lowerCase3 = it3.getLabel().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
                return ComparisonsKt.compareValues(valueOf, Boolean.valueOf(StringsKt.startsWith$default(lowerCase3, (String) words.get(0), false, 2, (Object) null)));
            }
        };
        Iterable $this$distinctBy$iv = CollectionsKt.sortedWith((List) destination$iv$iv2, new Comparator() { // from class: com.starpilot.starview.SettingsIndex$search$$inlined$thenBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
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
