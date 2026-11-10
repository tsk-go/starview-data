package com.starpilot.starview;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Favorites.kt */
public final class Favorites {
    public static final Favorites INSTANCE = new Favorites();
    private static volatile List<? extends JSONObject> list = CollectionsKt.emptyList();

    /* renamed from: main$delegate, reason: from kotlin metadata */
    private static final Lazy main = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.Favorites$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Handler main_delegate$lambda$0;
            main_delegate$lambda$0 = Favorites.main_delegate$lambda$0();
            return main_delegate$lambda$0;
        }
    });
    private static volatile int version;

    private Favorites() {
    }

    public final List<JSONObject> getList() {
        return (List) list;
    }

    public final int getVersion() {
        return version;
    }

    private final Handler getMain() {
        return (Handler) main.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Handler main_delegate$lambda$0() {
        return new Handler(Looper.getMainLooper());
    }

    public final boolean same(JSONObject a, double lat, double lon) {
        Intrinsics.checkNotNullParameter(a, "a");
        return Math.abs(a.optDouble("latitude") - lat) < 2.0E-5d && Math.abs(a.optDouble("longitude") - lon) < 2.0E-5d;
    }

    public final JSONObject find(double lat, double lon) {
        Object element$iv;
        Iterable $this$firstOrNull$iv = list;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                if (INSTANCE.same(it2, lat, lon)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        return (JSONObject) element$iv;
    }

    public final String icon(JSONObject f) {
        String optString = f != null ? f.optString("label") : null;
        return Intrinsics.areEqual(optString, "home") ? "🏠" : Intrinsics.areEqual(optString, "work") ? "💼" : "★";
    }

    public final String title(JSONObject f) {
        Intrinsics.checkNotNullParameter(f, "f");
        String optString = f.optString("label");
        return Intrinsics.areEqual(optString, "home") ? "Home" : Intrinsics.areEqual(optString, "work") ? "Work" : f.optString("name");
    }

    private final void took(JSONObject r) {
        JSONArray a = r.optJSONArray("favorites");
        if (a != null) {
            Iterable $this$mapNotNull$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList();
            Iterator<Integer> it = $this$mapNotNull$iv.iterator();
            while (it.hasNext()) {
                int element$iv$iv$iv = ((IntIterator) it).nextInt();
                JSONObject optJSONObject = a.optJSONObject(element$iv$iv$iv);
                if (optJSONObject != null) {
                    destination$iv$iv.add(optJSONObject);
                }
            }
            list = (List) destination$iv$iv;
            version++;
        }
    }

    /* VF: from Vineflower (jadx: ) */
    public static void load$default(Favorites var0, DataClient var1, Function0 var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = null;
        }

        var0.load(var1, var2);
    }

    public final void load(DataClient data, final Function0<Unit> then) {
        if (data == null || !data.getConnected()) {
            if (then != null) {
                then.invoke();
                return;
            }
            return;
        }
        data.request("nav_list", kotlin.collections.MapsKt.emptyMap(), new Function1() { // from class: com.starpilot.starview.Favorites$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit load$lambda$4;
                load$lambda$4 = Favorites.load$lambda$4(then, (JSONObject) obj);
                return load$lambda$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$4(Function0 $then, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            INSTANCE.took(r);
        }
        if ($then != null) {
            $then.invoke();
        }
        return Unit.INSTANCE;
    }

    public final void save(DataClient data, String name, String address, double lat, double lon, String label, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(then, "then");
        if (data == null) {
            then.invoke("Not connected to the comma");
            return;
        }
        HashMap args = new HashMap(MapsKt.mapOf(TuplesKt.to("name", name), TuplesKt.to("address", address), TuplesKt.to("latitude", Double.valueOf(lat)), TuplesKt.to("longitude", Double.valueOf(lon))));
        if (label != null) {
            args.put("label", label);
        }
        data.request("nav_favorite", args, new Function1() { // from class: com.starpilot.starview.Favorites$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit save$lambda$5;
                save$lambda$5 = Favorites.save$lambda$5(then, (JSONObject) obj);
                return save$lambda$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit save$lambda$5(Function1 $then, JSONObject r) {
        String unsupported;
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            INSTANCE.took(r);
            unsupported = null;
        } else {
            Favorites favorites = INSTANCE;
            String optString = r.optString("error");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            unsupported = favorites.unsupported(optString);
        }
        $then.invoke(unsupported);
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void saveFound$default(Favorites favorites, Context context, DataClient dataClient, String str, String str2, String str3, Function1 function1, int i, Object obj) {
        if ((i & 16) != 0) {
            str3 = null;
        }
        favorites.saveFound(context, dataClient, str, str2, str3, function1);
    }

    public final void saveFound(final Context ctx, final DataClient data, final String name, final String address, final String label, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(then, "then");
        new Thread(new Runnable() { // from class: com.starpilot.starview.Favorites$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                Favorites.saveFound$lambda$9(ctx, name, address, then, data, label);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveFound$lambda$9(Context $ctx, final String $name, final String $address, final Function1 $then, final DataClient $data, final String $label) {
        Address found = null;
        try {
            if (Geocoder.isPresent()) {
                Geocoder geocoder = new Geocoder($ctx, Locale.getDefault());
                Iterable $this$filter$iv = CollectionsKt.listOf(new String[]{$name, $address});
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    String it = (String) element$iv$iv;
                    if (it.length() > 0) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                List<Address> fromLocationName = geocoder.getFromLocationName(CollectionsKt.joinToString((List) destination$iv$iv, ", ", "", "", -1, "...", null), 1);
                if (fromLocationName != null) {
                    found = (Address) CollectionsKt.firstOrNull((List) fromLocationName);
                }
            }
        } catch (Exception e) {
        }
        final Address hit = found;
        INSTANCE.getMain().post(new Runnable() { // from class: com.starpilot.starview.Favorites$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Favorites.saveFound$lambda$9$lambda$8(hit, $then, $data, $name, $address, $label);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveFound$lambda$9$lambda$8(Address $hit, Function1 $then, DataClient $data, String $name, String $address, String $label) {
        if ($hit == null || !$hit.hasLatitude()) {
            $then.invoke("Couldn't find that address to save it - search for the street address instead");
            return;
        }
        Favorites favorites = INSTANCE;
        String str = $address;
        if (str.length() == 0) {
            String addressLine = $hit.getAddressLine(0);
            if (addressLine == null) {
                addressLine = "";
            }
            str = addressLine;
        }
        favorites.save($data, $name, str, $hit.getLatitude(), $hit.getLongitude(), $label, $then);
    }

    public final void remove(final Context ctx, DataClient data, final JSONObject f, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(f, "f");
        Intrinsics.checkNotNullParameter(then, "then");
        if (data == null) {
            then.invoke("Not connected to the comma");
        } else {
            data.request("nav_unfavorite", MapsKt.mapOf(TuplesKt.to("id", f.optString("id"))), new Function1() { // from class: com.starpilot.starview.Favorites$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit remove$lambda$10;
                    remove$lambda$10 = Favorites.remove$lambda$10(ctx, f, then, (JSONObject) obj);
                    return remove$lambda$10;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit remove$lambda$10(Context $ctx, JSONObject $f, Function1 $then, JSONObject r) {
        String unsupported;
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            INSTANCE.took(r);
            INSTANCE.setPinned(new UiSettings($ctx), $f, false);
            unsupported = null;
        } else {
            Favorites favorites = INSTANCE;
            String optString = r.optString("error");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            unsupported = favorites.unsupported(optString);
        }
        $then.invoke(unsupported);
        return Unit.INSTANCE;
    }

    public final void label(DataClient data, JSONObject f, String label, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(f, "f");
        Intrinsics.checkNotNullParameter(then, "then");
        if (data == null) {
            then.invoke("Not connected to the comma");
        } else {
            data.request("nav_label", MapsKt.mapOf(TuplesKt.to("id", f.optString("id")), TuplesKt.to("label", label)), new Function1() { // from class: com.starpilot.starview.Favorites$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit label$lambda$11;
                    label$lambda$11 = Favorites.label$lambda$11(then, (JSONObject) obj);
                    return label$lambda$11;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit label$lambda$11(Function1 $then, JSONObject r) {
        String unsupported;
        Intrinsics.checkNotNullParameter(r, "r");
        if (r.optBoolean("ok")) {
            INSTANCE.took(r);
            unsupported = null;
        } else {
            Favorites favorites = INSTANCE;
            String optString = r.optString("error");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            unsupported = favorites.unsupported(optString);
        }
        $then.invoke(unsupported);
        return Unit.INSTANCE;
    }

    private final String unsupported(String e) {
        if (StringsKt.contains((CharSequence) e, (CharSequence) "unknown action", false)) {
            return "The comma needs the StarView navigation update (patch 10) for saved places";
        }
        String str = e;
        if (str.length() == 0) {
            str = "No answer from the comma";
        }
        return str;
    }

    public final List<JSONObject> pins(UiSettings s) {
        Intrinsics.checkNotNullParameter(s, "s");
        try {
            JSONArray a = new JSONArray(s.getStr("fav_pins", "[]"));
            Iterable $this$mapNotNull$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList();
            Iterator<Integer> it = $this$mapNotNull$iv.iterator();
            while (it.hasNext()) {
                int element$iv$iv$iv = ((IntIterator) it).nextInt();
                JSONObject optJSONObject = a.optJSONObject(element$iv$iv$iv);
                if (optJSONObject != null) {
                    destination$iv$iv.add(optJSONObject);
                }
            }
            return (List) destination$iv$iv;
        } catch (Exception e) {
            return CollectionsKt.emptyList();
        }
    }

    public final boolean pinned(UiSettings s, double lat, double lon) {
        Intrinsics.checkNotNullParameter(s, "s");
        Iterable $this$any$iv = pins(s);
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            JSONObject it = (JSONObject) element$iv;
            if (INSTANCE.same(it, lat, lon)) {
                return true;
            }
        }
        return false;
    }

    public final void setPinned(UiSettings s, JSONObject f, boolean on) {
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(f, "f");
        double lat = f.optDouble("latitude");
        double lon = f.optDouble("longitude");
        Iterable $this$filterNot$iv = pins(s);
        int $i$f$filterNot = 0;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filterNot$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            Iterable $this$filterNot$iv2 = $this$filterNot$iv;
            int $i$f$filterNot2 = $i$f$filterNot;
            Collection destination$iv$iv2 = destination$iv$iv;
            if (!INSTANCE.same(it, lat, lon)) {
                destination$iv$iv2.add(element$iv$iv);
            }
            destination$iv$iv = destination$iv$iv2;
            $this$filterNot$iv = $this$filterNot$iv2;
            $i$f$filterNot = $i$f$filterNot2;
        }
        List<JSONObject> rest = (List) destination$iv$iv;
        JSONArray out = new JSONArray();
        for (JSONObject p : rest) {
            out.put(p);
        }
        if (on) {
            out.put(new JSONObject().put("name", f.optString("name")).put("address", f.optString("address")).put("latitude", lat).put("longitude", lon).put("label", f.optString("label")));
        }
        String jSONArray = out.toString();
        Intrinsics.checkNotNullExpressionValue(jSONArray, "toString(...)");
        s.setStr("fav_pins", jSONArray);
        version++;
    }

    public final Pair<String, String> pinTitle(JSONObject p) {
        Intrinsics.checkNotNullParameter(p, "p");
        JSONObject f = find(p.optDouble("latitude"), p.optDouble("longitude"));
        if (f == null) {
            f = p;
        }
        return TuplesKt.to(icon(f), title(f));
    }
}
