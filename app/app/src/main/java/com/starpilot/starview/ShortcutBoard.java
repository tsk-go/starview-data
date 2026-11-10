package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: SettingsLauncher.kt */
public final class ShortcutBoard {
    public static final Companion Companion = new Companion(null);
    public static final int PER_PAGE = 12;
    private final List<List<Item>> pages = new ArrayList();

    /* compiled from: SettingsLauncher.kt */
    public static final class Item {
        private final String group;
        private final String icon;
        private final String label;
        private final String target;
        private final String where;

        public Item(String label, String where, String target, String icon, String group) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(where, "where");
            Intrinsics.checkNotNullParameter(target, "target");
            Intrinsics.checkNotNullParameter(icon, "icon");
            Intrinsics.checkNotNullParameter(group, "group");
            this.label = label;
            this.where = where;
            this.target = target;
            this.icon = icon;
            this.group = group;
        }

        public final String getGroup() {
            return this.group;
        }

        public final String getIcon() {
            return this.icon;
        }

        public final String getLabel() {
            return this.label;
        }

        public final String getTarget() {
            return this.target;
        }

        public final String getWhere() {
            return this.where;
        }

        public final JSONObject json() {
            JSONObject put = new JSONObject().put("l", this.label).put("w", this.where).put("t", this.target).put("i", this.icon).put("g", this.group);
            Intrinsics.checkNotNullExpressionValue(put, "put(...)");
            return put;
        }
    }

    public ShortcutBoard(String json) {
        int i;
        Item item;
        try {
            JSONArray a = new JSONObject(json == null ? "{}" : json).optJSONArray("pages");
            a = a == null ? new JSONArray() : a;
            int p = 0;
            int length = a.length();
            while (p < length) {
                JSONArray arr = a.optJSONArray(p);
                arr = arr == null ? new JSONArray() : arr;
                List<List<Item>> list = this.pages;
                Iterable $this$mapNotNull$iv = RangesKt.until(0, arr.length());
                Collection destination$iv$iv = new ArrayList();
                Iterator<Integer> it = $this$mapNotNull$iv.iterator();
                while (it.hasNext()) {
                    int element$iv$iv$iv = ((IntIterator) it).nextInt();
                    JSONArray a2 = a;
                    JSONObject p0 = arr.optJSONObject(element$iv$iv$iv);
                    if (p0 != null) {
                        i = length;
                        item = Companion.item(p0);
                    } else {
                        i = length;
                        item = null;
                    }
                    if (item != null) {
                        destination$iv$iv.add(item);
                    }
                    a = a2;
                    length = i;
                }
                JSONArray a3 = a;
                int i2 = length;
                list.add(CollectionsKt.toMutableList((Collection) CollectionsKt.take((List) destination$iv$iv, 12)));
                p++;
                a = a3;
                length = i2;
            }
        } catch (Exception e) {
            this.pages.clear();
        }
        normalize();
    }

    /* compiled from: SettingsLauncher.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Item item(JSONObject o) {
            Intrinsics.checkNotNullParameter(o, "o");
            String optString = o.optString("t");
            if (optString.length() == 0) {
                optString = null;
            }
            String it = optString;
            if (it == null) {
                return null;
            }
            String optString2 = o.optString("l", it);
            Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
            String optString3 = o.optString("w");
            Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
            String optString4 = o.optString("i", "new");
            Intrinsics.checkNotNullExpressionValue(optString4, "optString(...)");
            String optString5 = o.optString("g", "device");
            Intrinsics.checkNotNullExpressionValue(optString5, "optString(...)");
            return new Item(optString2, optString3, it, optString4, optString5);
        }
    }

    public final List<List<Item>> getPages() {
        return this.pages;
    }

    public final void normalize() {
        if (this.pages.isEmpty()) {
            this.pages.add(new ArrayList());
        }
        Iterable $this$filter$iv = CollectionsKt.drop(this.pages, 1);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            List it = (List) element$iv$iv;
            if (!it.isEmpty()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List right = CollectionsKt.toMutableList(destination$iv$iv);
        if (right.isEmpty() || ((List) CollectionsKt.last(right)).size() >= 12) {
            right.add(new ArrayList());
        }
        while (this.pages.size() > 1) {
            this.pages.remove(this.pages.size() - 1);
        }
        this.pages.addAll(right);
    }

    public final boolean contains(String target) {
        boolean z;
        Intrinsics.checkNotNullParameter(target, "target");
        Iterable $this$any$iv = this.pages;
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            Iterable p = (List) element$iv;
            Iterable $this$any$iv2 = p;
            if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
                Iterator it = $this$any$iv2.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    Object element$iv2 = it.next();
                    Item it2 = (Item) element$iv2;
                    if (Intrinsics.areEqual(it2.getTarget(), target)) {
                        z = true;
                        break;
                    }
                }
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ int add$default(ShortcutBoard shortcutBoard, Item item, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -1;
        }
        return shortcutBoard.add(item, i);
    }

    public final int add(Item item, int page) {
        Object element$iv;
        int p;
        Intrinsics.checkNotNullParameter(item, "item");
        List order = page >= 0 && page < this.pages.size() ? CollectionsKt.plus((Iterable) RangesKt.until(page, this.pages.size()), (Iterable) RangesKt.until(0, page)) : CollectionsKt.toList(CollectionsKt.getIndices(this.pages));
        List $this$firstOrNull$iv = order;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            int it2 = ((Number) element$iv).intValue();
            int it3 = this.pages.get(it2).size() < 12 ? 1 : 0;
            if (it3 != 0) {
                break;
            }
        }
        Integer num = (Integer) element$iv;
        if (num != null) {
            p = num.intValue();
        } else {
            ShortcutBoard $this$add_u24lambda_u245 = this;
            $this$add_u24lambda_u245.pages.add(new ArrayList());
            p = $this$add_u24lambda_u245.pages.size() - 1;
        }
        this.pages.get(p).add(item);
        List target = this.pages.get(p);
        normalize();
        List $this$indexOfFirst$iv = this.pages;
        int index$iv = 0;
        for (Object item$iv : $this$indexOfFirst$iv) {
            List it4 = (List) item$iv;
            boolean it5 = it4 == target;
            if (it5) {
                return index$iv;
            }
            index$iv++;
        }
        return -1;
    }

    public final void remove(int page, int i) {
        List it = (List) CollectionsKt.getOrNull(this.pages, page);
        if (it != null) {
            boolean z = false;
            if (i >= 0 && i < it.size()) {
                z = true;
            }
            if (!z) {
                it = null;
            }
            if (it != null) {
            }
        }
        normalize();
    }

    public final void move(int page, int i, int delta) {
        List p = (List) CollectionsKt.getOrNull(this.pages, page);
        if (p == null) {
            return;
        }
        int j = i + delta;
        if (i >= 0 && i < p.size()) {
            if (!(j >= 0 && j < p.size())) {
                return;
            }
            Item t = (Item) p.get(i);
            p.set(i, p.get(j));
            p.set(j, t);
        }
    }

    public final boolean toPage(int page, int i, int delta) {
        List q;
        List p = (List) CollectionsKt.getOrNull(this.pages, page);
        if (p == null || (q = (List) CollectionsKt.getOrNull(this.pages, page + delta)) == null) {
            return false;
        }
        if (!(i >= 0 && i < p.size()) || q.size() >= 12) {
            return false;
        }
        q.add(p.remove(i));
        normalize();
        return true;
    }

    public final String json() {
        JSONObject jSONObject = new JSONObject();
        Iterable $this$map$iv = this.pages;
        int i = 10;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Iterable p = (List) item$iv$iv;
            Iterable $this$map$iv2 = p;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, i));
            for (Object item$iv$iv2 : $this$map$iv2) {
                Item it = (Item) item$iv$iv2;
                destination$iv$iv2.add(it.json());
            }
            destination$iv$iv.add(new JSONArray(destination$iv$iv2));
            i = 10;
        }
        String jSONObject2 = jSONObject.put("pages", new JSONArray(destination$iv$iv)).toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject2, "toString(...)");
        return jSONObject2;
    }
}
