package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GalaxyVasm.kt */
public final class VasmGeometry {
    public static final VasmGeometry INSTANCE = new VasmGeometry();
    private static final List<Pair<Integer, Integer>> FORMATS = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(1928, 1208), TuplesKt.to(1344, 760)});

    private VasmGeometry() {
    }

    public final List<Pair<Integer, Integer>> getFORMATS() {
        return FORMATS;
    }

    public final boolean supported(int w, int h) {
        Iterable $this$any$iv = FORMATS;
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            Pair it = (Pair) element$iv;
            if (((((Number) it.getFirst()).intValue() == w && ((Number) it.getSecond()).intValue() == h) ? 1 : null) != null) {
                return true;
            }
        }
        return false;
    }

    private final long orient(int[] a, int[] b, int[] c) {
        return ((b[0] - a[0]) * (c[1] - a[1])) - ((b[1] - a[1]) * (c[0] - a[0]));
    }

    private final boolean between(int a, int b, int c) {
        return b >= Math.min(a, c) && b <= Math.max(a, c);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0058, code lost:
    
        if ((r9 > 0) != (r11 > 0)) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean intersects(int[] a, int[] b, int[] c, int[] d) {
        long abc = orient(a, b, c);
        long abd = orient(a, b, d);
        long cda = orient(c, d, a);
        long cdb = orient(c, d, b);
        if (!intersects$on(a, c, b) && !intersects$on(a, d, b) && !intersects$on(c, a, d) && !intersects$on(c, b, d)) {
            if ((abc > 0) != (abd > 0)) {
            }
            return false;
        }
        return true;
    }

    private static final boolean intersects$on(int[] p, int[] q, int[] r) {
        return INSTANCE.orient(p, q, r) == 0 && INSTANCE.between(p[0], q[0], r[0]) && INSTANCE.between(p[1], q[1], r[1]);
    }

    public final boolean validPolygon(List<int[]> pts, int w, int h) {
        Iterable $this$any$iv;
        Intrinsics.checkNotNullParameter(pts, "pts");
        int size = pts.size();
        char c = 0;
        char c2 = 1;
        if (!(3 <= size && size < 33)) {
            return false;
        }
        List<int[]> $this$any$iv2 = pts;
        if (!($this$any$iv2 instanceof Collection) || !$this$any$iv2.isEmpty()) {
            Iterator it = $this$any$iv2.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    int[] it2 = (int[]) element$iv;
                    if (((it2[0] < 0 || it2[0] > w || it2[1] < 0 || it2[1] > h) ? 1 : null) != null) {
                        $this$any$iv = 1;
                        break;
                    }
                } else {
                    $this$any$iv = null;
                    break;
                }
            }
        } else {
            $this$any$iv = null;
        }
        if ($this$any$iv != null) {
            return false;
        }
        List<int[]> $this$map$iv = pts;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            int[] it3 = (int[]) item$iv$iv;
            destination$iv$iv.add(it3[0] + "," + it3[c2]);
            c2 = 1;
        }
        if (CollectionsKt.toSet((List) destination$iv$iv).size() != pts.size()) {
            return false;
        }
        double area = 0.0d;
        int i = 0;
        int size2 = pts.size();
        while (i < size2) {
            int[] a = pts.get(i);
            int[] b = pts.get((i + 1) % pts.size());
            area += (a[c] * b[1]) - (b[c] * a[1]);
            i++;
            c = 0;
        }
        if (Math.abs(area) <= w * 1.0E-9d * h) {
            return false;
        }
        int size3 = pts.size();
        for (int i2 = 0; i2 < size3; i2++) {
            int size4 = pts.size();
            for (int j = i2 + 2; j < size4; j++) {
                if ((i2 != 0 || j != pts.size() - 1) && intersects(pts.get(i2), pts.get((i2 + 1) % pts.size()), pts.get(j), pts.get((j + 1) % pts.size()))) {
                    return false;
                }
            }
        }
        return true;
    }

    public final JSONObject draft(int w, int h, List<int[]> cameraLeft, List<int[]> cameraRight) {
        Intrinsics.checkNotNullParameter(cameraLeft, "cameraLeft");
        Intrinsics.checkNotNullParameter(cameraRight, "cameraRight");
        if (!supported(w, h)) {
            return null;
        }
        if (cameraLeft.isEmpty() && cameraRight.isEmpty()) {
            return null;
        }
        if (!cameraLeft.isEmpty() && !validPolygon(cameraLeft, w, h)) {
            return null;
        }
        if (cameraRight.isEmpty() || validPolygon(cameraRight, w, h)) {
            return new JSONObject().put("version", 1).put("width", w).put("height", h).put("poly_left", draft$arr(cameraLeft)).put("poly_right", draft$arr(cameraRight));
        }
        return null;
    }

    private static final JSONArray draft$arr(List<int[]> list) {
        JSONArray $this$draft_u24arr_u24lambda_u243 = new JSONArray();
        for (int[] q : list) {
            $this$draft_u24arr_u24lambda_u243.put(new JSONArray().put(q[0]).put(q[1]));
        }
        return $this$draft_u24arr_u24lambda_u243;
    }
}
