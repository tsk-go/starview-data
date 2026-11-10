package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

/* compiled from: DetMasks.kt */
public final class DetMasks {
    private static SharedPreferences prefs;
    private static volatile int version;
    public static final DetMasks INSTANCE = new DetMasks();
    private static final ArrayList<float[]>[] polys = {new ArrayList<>(), new ArrayList<>()};
    private static final boolean[] keep = new boolean[2];

    private DetMasks() {
    }

    public final int getVersion() {
        return version;
    }

    private final int i(boolean wide) {
        return wide ? 1 : 0;
    }

    private final String key(boolean wide) {
        return wide ? "det_mask_wide" : "det_mask_road";
    }

    public final synchronized void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (prefs != null) {
            return;
        }
        SharedPreferences p = ctx.getApplicationContext().getSharedPreferences("starview_ui", 0);
        prefs = p;
        boolean[] zArr = {false, true};
        for (int i = 0; i < 2; i++) {
            boolean w = zArr[i];
            ArrayList<float[]> arrayList = polys[i(w)];
            String string = p.getString(key(w), "");
            if (string == null) {
                string = "";
            }
            arrayList.addAll(decode(string));
            keep[i(w)] = p.getBoolean(key(w) + "_keep", false);
        }
        version++;
    }

    public final synchronized List<float[]> get(boolean wide) {
        Collection destination$iv$iv;
        Iterable $this$map$iv = polys[i(wide)];
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            float[] it = (float[]) item$iv$iv;
            float[] copyOf = Arrays.copyOf(it, it.length);
            Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
            destination$iv$iv.add(copyOf);
        }
        return (List) destination$iv$iv;
    }

    public final synchronized void set(boolean wide, List<float[]> list) {
        SharedPreferences.Editor edit;
        SharedPreferences.Editor putString;
        Intrinsics.checkNotNullParameter(list, "list");
        ArrayList p = polys[i(wide)];
        p.clear();
        List<float[]> $this$filter$iv = list;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            if (((float[]) element$iv$iv).length < 6) {
                z = false;
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$map$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            float[] it2 = (float[]) item$iv$iv;
            float[] copyOf = Arrays.copyOf(it2, it2.length);
            Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
            destination$iv$iv2.add(copyOf);
        }
        p.addAll((List) destination$iv$iv2);
        SharedPreferences sharedPreferences = prefs;
        if (sharedPreferences != null && (edit = sharedPreferences.edit()) != null && (putString = edit.putString(key(wide), encode(p))) != null) {
            putString.apply();
        }
        version++;
    }

    public final synchronized boolean keep(boolean wide) {
        return keep[i(wide)];
    }

    public final synchronized void setKeep(boolean wide, boolean v) {
        SharedPreferences.Editor edit;
        keep[i(wide)] = v;
        SharedPreferences sharedPreferences = prefs;
        if (sharedPreferences != null && (edit = sharedPreferences.edit()) != null) {
            SharedPreferences.Editor putBoolean = edit.putBoolean(key(wide) + "_keep", v);
            if (putBoolean != null) {
                putBoolean.apply();
            }
        }
        version++;
    }

    public final boolean[] maskFor(boolean wide, int w, int h) {
        Pair pair;
        synchronized (this) {
            pair = TuplesKt.to(INSTANCE.get(wide), Boolean.valueOf(keep[INSTANCE.i(wide)]));
        }
        List l = (List) pair.component1();
        boolean k = ((Boolean) pair.component2()).booleanValue();
        boolean[] m = raster(l, w, h);
        if (m == null) {
            return null;
        }
        if (k) {
            int length = m.length;
            for (int j = 0; j < length; j++) {
                m[j] = !m[j];
            }
        }
        return m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence encode$lambda$5(float[] p) {
        Intrinsics.checkNotNullParameter(p, "p");
        return ArraysKt.joinToString$default(p, (CharSequence) ",", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1() { // from class: com.starpilot.starview.DetMasks$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence encode$lambda$5$lambda$4;
                encode$lambda$5$lambda$4 = DetMasks.encode$lambda$5$lambda$4(((Float) obj).floatValue());
                return encode$lambda$5$lambda$4;
            }
        }, 30, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence encode$lambda$5$lambda$4(float it) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%.4f", Arrays.copyOf(new Object[]{Float.valueOf(it)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final String encode(List<float[]> l) {
        Intrinsics.checkNotNullParameter(l, "l");
        return CollectionsKt.joinToString$default(l, "|", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.DetMasks$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence encode$lambda$5;
                encode$lambda$5 = DetMasks.encode$lambda$5((float[]) obj);
                return encode$lambda$5;
            }
        }, 30, null);
    }

    public final List<float[]> decode(String s) {
        Collection destination$iv$iv;
        Intrinsics.checkNotNullParameter(s, "s");
        int i = 1;
        char c = 0;
        Iterable $this$mapNotNull$iv = StringsKt.split$default((CharSequence) s, new char[]{'|'}, false, 0, 6, (Object) null);
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            String p = (String) element$iv$iv$iv;
            char[] cArr = new char[i];
            cArr[c] = ',';
            Iterable $this$mapNotNull$iv2 = StringsKt.split$default((CharSequence) p, cArr, false, 0, 6, (Object) null);
            Collection destination$iv$iv3 = new ArrayList();
            for (Object element$iv$iv$iv2 : $this$mapNotNull$iv2) {
                Float floatOrNull = StringsKt.toFloatOrNull((String) element$iv$iv$iv2);
                if (floatOrNull != null) {
                    destination$iv$iv = destination$iv$iv3;
                    destination$iv$iv.add(floatOrNull);
                } else {
                    destination$iv$iv = destination$iv$iv3;
                }
                destination$iv$iv3 = destination$iv$iv;
            }
            List it = (List) destination$iv$iv3;
            List list = it;
            Iterable $this$mapNotNull$iv3 = $this$mapNotNull$iv;
            if (!(it.size() >= 6 && it.size() % 2 == 0)) {
                list = null;
            }
            float[] floatArray = list != null ? CollectionsKt.toFloatArray(list) : null;
            if (floatArray != null) {
                destination$iv$iv2.add(floatArray);
            }
            $this$mapNotNull$iv = $this$mapNotNull$iv3;
            i = 1;
            c = 0;
        }
        return (List) destination$iv$iv2;
    }

    public final boolean[] raster(List<float[]> list, int w, int h) {
        int i;
        Intrinsics.checkNotNullParameter(list, "list");
        if (list.isEmpty() || w <= 0 || h <= 0) {
            return null;
        }
        boolean[] m = new boolean[w * h];
        float[] xs = new float[256];
        for (float[] p : list) {
            int n = p.length / 2;
            for (int y = 0; y < h; y++) {
                float fy = (y + 0.5f) / h;
                int k = 0;
                int a = 0;
                while (true) {
                    if (a >= n) {
                        break;
                    }
                    int b = (a + 1) % n;
                    float ay = p[(a * 2) + 1];
                    float by = p[(b * 2) + 1];
                    if ((ay > fy ? 0 : 1) != (by <= fy ? 1 : 0) && k < xs.length) {
                        xs[k] = (p[a * 2] + (((fy - ay) / (by - ay)) * (p[b * 2] - p[a * 2]))) * w;
                        k++;
                    }
                    a++;
                }
                ArraysKt.sort(xs, 0, k);
                int j = 0;
                while (j + 1 < k) {
                    int x0 = Math.max(i, (int) Math.ceil(xs[j] - 0.5f));
                    int x1 = Math.min(w - 1, ((int) Math.ceil(xs[j + 1] - 0.5f)) - 1);
                    int x = x0;
                    if (x <= x1) {
                        while (true) {
                            m[(y * w) + x] = true;
                            if (x != x1) {
                                x++;
                            }
                        }
                    }
                    j += 2;
                    i = 0;
                }
            }
        }
        return m;
    }

    public final boolean inside(float[] p, float fx, float fy) {
        Intrinsics.checkNotNullParameter(p, "p");
        int n = p.length / 2;
        boolean c = false;
        for (int a = 0; a < n; a++) {
            int b = ((a + n) - 1) % n;
            float ay = p[(a * 2) + 1];
            float by = p[(b * 2) + 1];
            if ((ay > fy) != (by > fy) && fx < (((p[b * 2] - p[a * 2]) * (fy - ay)) / (by - ay)) + p[a * 2]) {
                c = c ? false : true;
            }
        }
        return c;
    }
}
