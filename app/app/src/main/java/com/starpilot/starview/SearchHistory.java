package com.starpilot.starview;

import com.starpilot.starview.SearchHistory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: SettingsHome.kt */
public final class SearchHistory {
    public static final Companion Companion = new Companion(null);
    public static final int KEEP = 30;
    private final LinkedHashMap<String, Item> items = new LinkedHashMap<>();

    /* compiled from: SettingsHome.kt */
    public static final class Item {
        private final int count;
        private final long lastMs;
        private final String text;

        public Item(String text, int count, long lastMs) {
            Intrinsics.checkNotNullParameter(text, "text");
            this.text = text;
            this.count = count;
            this.lastMs = lastMs;
        }

        public final int getCount() {
            return this.count;
        }

        public final long getLastMs() {
            return this.lastMs;
        }

        public final String getText() {
            return this.text;
        }
    }

    public SearchHistory(String json) {
        try {
            JSONArray a = new JSONArray(json == null ? "[]" : json);
            int length = a.length();
            for (int i = 0; i < length; i++) {
                JSONObject o = a.optJSONObject(i);
                if (o != null) {
                    String t = o.optString("q");
                    Companion companion = Companion;
                    Intrinsics.checkNotNull(t);
                    if (companion.key(t).length() > 0) {
                        this.items.put(Companion.key(t), new Item(StringsKt.trim((CharSequence) t).toString(), RangesKt.coerceAtLeast(o.optInt("n", 1), 1), o.optLong("t")));
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /* compiled from: SettingsHome.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String key(String q) {
            Intrinsics.checkNotNullParameter(q, "q");
            String lowerCase = StringsKt.trim((CharSequence) q).toString().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            return new Regex("\\s+").replace(lowerCase, " ");
        }
    }

    public final List<Item> list() {
        Collection<Item> values = this.items.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        final Comparator comparator = new Comparator() { // from class: com.starpilot.starview.SearchHistory$list$$inlined$compareByDescending$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                SearchHistory.Item it = (SearchHistory.Item) t2;
                Integer valueOf = Integer.valueOf(it.getCount());
                SearchHistory.Item it2 = (SearchHistory.Item) t;
                return ComparisonsKt.compareValues(valueOf, Integer.valueOf(it2.getCount()));
            }
        };
        return CollectionsKt.sortedWith(values, new Comparator() { // from class: com.starpilot.starview.SearchHistory$list$$inlined$thenByDescending$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int previousCompare = comparator.compare(t, t2);
                if (previousCompare != 0) {
                    return previousCompare;
                }
                SearchHistory.Item it = (SearchHistory.Item) t2;
                Long valueOf = Long.valueOf(it.getLastMs());
                SearchHistory.Item it2 = (SearchHistory.Item) t;
                return ComparisonsKt.compareValues(valueOf, Long.valueOf(it2.getLastMs()));
            }
        });
    }

    public final void record(String q, long nowMs) {
        Intrinsics.checkNotNullParameter(q, "q");
        String k = Companion.key(q);
        if (k.length() < 2) {
            return;
        }
        Item old = this.items.get(k);
        this.items.put(k, new Item(new Regex("\\s+").replace(StringsKt.trim((CharSequence) q).toString(), " "), (old != null ? old.getCount() : 0) + 1, nowMs));
        if (this.items.size() > 30) {
            Iterable $this$forEach$iv = CollectionsKt.drop(list(), 30);
            for (Object element$iv : $this$forEach$iv) {
                Item it = (Item) element$iv;
                this.items.remove(Companion.key(it.getText()));
            }
        }
    }

    public final void remove(String q) {
        Intrinsics.checkNotNullParameter(q, "q");
        this.items.remove(Companion.key(q));
    }

    public final void clear() {
        this.items.clear();
    }

    public final String json() {
        Iterable values = this.items.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        Iterable $this$map$iv = values;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Item it = (Item) item$iv$iv;
            destination$iv$iv.add(new JSONObject().put("q", it.getText()).put("n", it.getCount()).put("t", it.getLastMs()));
        }
        String jSONArray = new JSONArray(destination$iv$iv).toString();
        Intrinsics.checkNotNullExpressionValue(jSONArray, "toString(...)");
        return jSONArray;
    }
}
