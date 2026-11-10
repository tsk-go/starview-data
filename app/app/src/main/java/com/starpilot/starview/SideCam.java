package com.starpilot.starview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: SideCam.kt */
public final class SideCam {
    private static final long KEEP_MS = 5000;
    private static final long MIN_FRAME_MS = 80;
    public static final int OUT = 240;
    private static VideoClient client;
    private static final int[] gamma;
    private static volatile long imageAtMs;
    private static volatile boolean invert;
    private static volatile long lastFrameMs;
    private static long lastWantMs;
    private static volatile Mask mask;
    private static Mask maskParsed;
    private static Function0<Unit> onFrame;
    public static final SideCam INSTANCE = new SideCam();
    private static String maskRaw = "\u0000";
    private static volatile Set<String> sides = SetsKt.emptySet();
    private static final ConcurrentHashMap<String, Bitmap> images = new ConcurrentHashMap<>();
    private static volatile String status = "";

    private SideCam() {
    }

    /* compiled from: SideCam.kt */
    public static final class Mask {
        public static final Companion Companion = new Companion(null);
        private static final Mask DEFAULT = new Mask(1928, 1208, 580.0f, new float[]{315.0f, 548.0f}, new float[]{1571.0f, 539.0f});
        private final float[] centerLeft;
        private final float[] centerRight;
        private final float cropSize;
        private final int height;
        private final int width;

        public Mask(int width, int height, float cropSize, float[] centerLeft, float[] centerRight) {
            this.width = width;
            this.height = height;
            this.cropSize = cropSize;
            this.centerLeft = centerLeft;
            this.centerRight = centerRight;
        }

        public final float[] getCenterLeft() {
            return this.centerLeft;
        }

        public final float[] getCenterRight() {
            return this.centerRight;
        }

        public final float getCropSize() {
            return this.cropSize;
        }

        public final int getHeight() {
            return this.height;
        }

        public final int getWidth() {
            return this.width;
        }

        public final float[] crop(String vehicleSide, int fw, int fh) {
            Intrinsics.checkNotNullParameter(vehicleSide, "vehicleSide");
            float[] c = Intrinsics.areEqual(vehicleSide, "right") ? this.centerLeft : this.centerRight;
            if (c == null) {
                return null;
            }
            float sx = fw / this.width;
            float sy = fh / this.height;
            float size = Math.max(1.0f, this.cropSize * Math.min(sx, sy));
            float f = 2;
            return new float[]{(c[0] * sx) - (size / f), (c[1] * sy) - (size / f), size};
        }

        /* compiled from: SideCam.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final Mask getDEFAULT() {
                return Mask.DEFAULT;
            }

            public final Mask parse(String raw) {
                if (raw == null || StringsKt.isBlank(raw)) {
                    return getDEFAULT();
                }
                try {
                    JSONObject o = new JSONObject(raw);
                    Iterator<String> keys = o.keys();
                    Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
                    boolean z = false;
                    if (!Intrinsics.areEqual(SequencesKt.toSet(SequencesKt.asSequence(keys)), SetsKt.setOf((Object[]) new String[]{"width", "height", "crop_size", "center_left", "center_right"}))) {
                        return null;
                    }
                    int w = o.getInt("width");
                    int h = o.getInt("height");
                    if (1 <= w && w < 8193) {
                        if (1 <= h && h < 8193) {
                            float size = (float) o.getDouble("crop_size");
                            if (!Float.isInfinite(size) && !Float.isNaN(size)) {
                                z = true;
                            }
                            if (z && size >= 1.0f && size <= Math.min(w, h)) {
                                return new Mask(w, h, size, parse$center(o, size, w, h, "center_left"), parse$center(o, size, w, h, "center_right"));
                            }
                            return null;
                        }
                    }
                    return null;
                } catch (Exception e) {
                    return null;
                }
            }

            /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
            private static final float[] parse$center(JSONObject var0, float var1, int var2, int var3, String var4) {
        boolean var10 = false; // declarations Vineflower left out
                if (var0.isNull(var4)) {
                    return null;
                } else {
                    JSONArray var9 = var0.getJSONArray(var4);
                    boolean var8;
                    if (var9.length() == 2) {
                        var8 = true;
                    } else {
                        var8 = false;
                    }

                    if (!var8) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    } else {
                        float var6 = (float)var9.getDouble(0);
                        float var7 = (float)var9.getDouble(1);
                        if (!Float.isInfinite(var6) && !Float.isNaN(var6)) {
                            var8 = true;
                        } else {
                            var8 = false;
                        }

                        label47: {
                            if (var8) {
                                if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
                                    var8 = true;
                                } else {
                                    var8 = false;
                                }

                                if (var8) {
                                    float var5 = 2;
                                    if (var6 - var1 / var5 >= 0.0F && var1 / var5 + var6 <= var2 && var7 - var1 / var5 >= 0.0F && var1 / var5 + var7 <= var3) {
                                        var10 = true;
                                        break label47;
                                    }
                                }
                            }

                            var10 = false;
                        }

                        if (var10) {
                            return new float[]{var6, var7};
                        } else {
                            throw new IllegalArgumentException("Failed requirement.".toString());
                        }
                    }
                }
            }
        }
    }

    static {
        int[] iArr = new int[256];
        for (int i = 0; i < 256; i++) {
            iArr[i] = RangesKt.coerceIn(MathKt.roundToInt(Math.pow(i / 255.0d, 0.78125d) * 255.0d), 0, 255);
        }
        gamma = iArr;
    }

    private final Mask maskOf(Telemetry t) {
        Object obj = t.getParams().get("PIPPreviewMask");
        String raw = obj != null ? obj.toString() : null;
        if (!Intrinsics.areEqual(raw, maskRaw)) {
            maskRaw = raw;
            maskParsed = Mask.Companion.parse(raw);
        }
        return maskParsed;
    }

    public final List<String> wanted(Telemetry t) {
        Intrinsics.checkNotNullParameter(t, "t");
        if (!t.getStarted() || !t.s("side_views") || !t.pBool("PIPPreviewEnabled", false)) {
            return CollectionsKt.emptyList();
        }
        Mask mask2 = maskOf(t);
        if (mask2 == null) {
            return CollectionsKt.emptyList();
        }
        boolean onBlinker = t.pBool("PIPPreviewShowOnBlinker", false);
        boolean onBsm = t.pBool("PIPPreviewShowOnBSM", false);
        ArrayList out = new ArrayList(2);
        for (Triple triple : (Iterable<Triple>) (Iterable) CollectionsKt.listOf((Object[]) new Triple[]{new Triple("right", Boolean.valueOf(t.getRightBlinker()), Boolean.valueOf(t.getRightBlindspot())), new Triple("left", Boolean.valueOf(t.getLeftBlinker()), Boolean.valueOf(t.getLeftBlindspot()))})) {
            String side = (String) triple.component1();
            boolean blinker = ((Boolean) triple.component2()).booleanValue();
            boolean bsm = ((Boolean) triple.component3()).booleanValue();
            if ((Intrinsics.areEqual(side, "right") ? mask2.getCenterLeft() : mask2.getCenterRight()) != null && ((onBlinker && blinker) || (onBsm && bsm))) {
                out.add(side);
            }
        }
        return out;
    }

    public final ConcurrentHashMap<String, Bitmap> getImages() {
        return images;
    }

    public final long getImageAtMs() {
        return imageAtMs;
    }

    public final void setImageAtMs(long j) {
        imageAtMs = j;
    }

    public final String getStatus() {
        return status;
    }

    public final void setStatus(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        status = str;
    }

    public final Function0<Unit> getOnFrame() {
        return onFrame;
    }

    public final void setOnFrame(Function0<Unit> function0) {
        onFrame = function0;
    }

    public static /* synthetic */ void tick$default(SideCam sideCam, Telemetry telemetry, String str, int i, long j, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            j = System.currentTimeMillis();
        }
        sideCam.tick(telemetry, str, i, j);
    }

    public final void tick(Telemetry t, String host, int port, long nowMs) {
        Intrinsics.checkNotNullParameter(t, "t");
        List want = wanted(t);
        sides = CollectionsKt.toSet(want);
        mask = maskOf(t);
        invert = t.pBool("PIPPreviewInvert", false);
        if (!want.isEmpty()) {
            lastWantMs = nowMs;
        }
        boolean keep = !want.isEmpty() || nowMs - lastWantMs < 5000;
        final VideoClient c = client;
        if (keep && c == null && host != null) {
            VideoClient v = new VideoClient(new Function1() { // from class: com.starpilot.starview.SideCam$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit tick$lambda$0;
                    tick$lambda$0 = SideCam.tick$lambda$0((String) obj);
                    return tick$lambda$0;
                }
            }, new Function2() { // from class: com.starpilot.starview.SideCam$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit tick$lambda$1;
                    tick$lambda$1 = SideCam.tick$lambda$1(((Integer) obj).intValue(), ((Integer) obj2).intValue());
                    return tick$lambda$1;
                }
            });
            v.setOnImage(new Function1() { // from class: com.starpilot.starview.SideCam$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit tick$lambda$3$lambda$2;
                    tick$lambda$3$lambda$2 = SideCam.tick$lambda$3$lambda$2((Image) obj);
                    return tick$lambda$3$lambda$2;
                }
            });
            v.connect(host, port, "driver", null);
            client = v;
            return;
        }
        if (!keep && c != null) {
            new Thread(new Runnable() { // from class: com.starpilot.starview.SideCam$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    c.close();
                }
            }).start();
            client = null;
            images.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tick$lambda$0(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        status = s;
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tick$lambda$1(int i, int i2) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tick$lambda$3$lambda$2(Image img) {
        Intrinsics.checkNotNullParameter(img, "img");
        INSTANCE.frame(img);
        return Unit.INSTANCE;
    }

    public final void close() {
        final VideoClient c = client;
        if (c != null) {
            new Thread(new Runnable() { // from class: com.starpilot.starview.SideCam$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    c.close();
                }
            }).start();
        }
        client = null;
        images.clear();
    }

    private final void frame(Image img) {
        Mask m;
        Set want;
        String side;
        int j;
        int[] px;
        float f;
        int fw;
        int fh;
        ByteBuffer vb;
        float py;
        SideCam sideCam = this;
        long now = System.currentTimeMillis();
        if (now - lastFrameMs >= 80 && (m = mask) != null) {
            Set want2 = sides;
            if (want2.isEmpty()) {
                return;
            }
            lastFrameMs = now;
            int fw2 = img.getWidth();
            int fh2 = img.getHeight();
            Image.Plane[] pl = img.getPlanes();
            Image.Plane yP = pl[0];
            Image.Plane uP = pl[1];
            Image.Plane vP = pl[2];
            ByteBuffer yb = yP.getBuffer();
            ByteBuffer ub = uP.getBuffer();
            ByteBuffer vb2 = vP.getBuffer();
            int yRow = yP.getRowStride();
            int yPix = yP.getPixelStride();
            int uRow = uP.getRowStride();
            int uPix = uP.getPixelStride();
            int vRow = vP.getRowStride();
            int vPix = vP.getPixelStride();
            for (String side2 : (Iterable<String>) want2) {
                float[] cr = m.crop(side2, fw2, fh2);
                if (cr != null) {
                    int[] px2 = new int[57600];
                    Mask m2 = m;
                    int j2 = 0;
                    while (true) {
                        want = want2;
                        if (j2 >= 240) {
                            break;
                        }
                        Image.Plane[] pl2 = pl;
                        Image.Plane yP2 = yP;
                        float f2 = 240;
                        float py2 = (((j2 + 0.5f) / f2) * 2.0f) - 1.0f;
                        Image.Plane uP2 = uP;
                        int i = 0;
                        for (int i2 = 240; i < i2; i2 = 240) {
                            float pxx = (((i + 0.5f) / f2) * 2.0f) - 1.0f;
                            Image.Plane vP2 = vP;
                            long now2 = now;
                            float r = (float) Math.sqrt((pxx * pxx) + (py2 * py2));
                            if (r > 1.01f) {
                                px2[(j2 * 240) + i] = 0;
                                py = py2;
                                fw = fw2;
                                fh = fh2;
                                px = px2;
                                f = f2;
                                vb = vb2;
                                side = side2;
                                j = j2;
                            } else {
                                int j3 = j2;
                                float z = (float) Math.sqrt(Math.max(0.0f, 1.0f - (r * r)));
                                side = side2;
                                float u = RangesKt.coerceIn(((i + 0.5f) / f2) + (pxx * 0.016f * (1.0f - z)), 0.001f, 0.999f);
                                j = j3;
                                px = px2;
                                f = f2;
                                float v = RangesKt.coerceIn(((j + 0.5f) / f2) + (0.016f * py2 * (1.0f - z)), 0.001f, 0.999f);
                                if (invert) {
                                    u = 1.0f - u;
                                }
                                int sx = RangesKt.coerceIn((int) (cr[0] + (cr[2] * u)), 0, fw2 - 1);
                                fw = fw2;
                                int fw3 = fh2 - 1;
                                int sy = RangesKt.coerceIn((int) (cr[1] + (cr[2] * v)), 0, fw3);
                                int yy = (yb.get((sy * yRow) + (sx * yPix)) & 255) - 16;
                                fh = fh2;
                                int fh3 = ((sy / 2) * uRow) + ((sx / 2) * uPix);
                                int cu = (ub.get(fh3) & 255) - 128;
                                int cv = (vb2.get(((sy / 2) * vRow) + ((sx / 2) * vPix)) & 255) - 128;
                                float y1 = yy * 1.164f;
                                float rr = (cv * 1.596f) + y1;
                                float v2 = cu;
                                float gg = (y1 - (v2 * 0.392f)) - (cv * 0.813f);
                                float bb = (cu * 2.017f) + y1;
                                float edge = sideCam.smooth(0.22f, 0.98f, r);
                                float shade = 1.0f - (0.15f * edge);
                                float rr2 = gamma[RangesKt.coerceIn((int) rr, 0, 255)] * shade;
                                vb = vb2;
                                float gg2 = gamma[RangesKt.coerceIn((int) gg, 0, 255)] * shade;
                                float bb2 = gamma[RangesKt.coerceIn((int) bb, 0, 255)] * shade;
                                float hx = 0.28f + pxx;
                                float hy = 0.34f + py2;
                                float hl = (1.0f - sideCam.smooth(0.0f, 0.22f, (hx * hx) + (hy * hy))) * z * 0.12f * 255.0f;
                                float rr3 = rr2 + hl;
                                float gg3 = gg2 + hl;
                                float bb3 = bb2 + hl;
                                float rim = sideCam.smooth(0.6f, 0.99f, r) * 0.1f;
                                int a = RangesKt.coerceIn(MathKt.roundToInt((1.0f - sideCam.smooth(0.985f, 1.01f, r)) * (1.0f - (1.2f * rim)) * 255.0f), 0, 255);
                                py = py2;
                                px[(j * 240) + i] = Color.argb(a, RangesKt.coerceIn(MathKt.roundToInt(rr3 + ((122.399994f - rr3) * rim)), 0, 255), RangesKt.coerceIn(MathKt.roundToInt(gg3 + ((178.5f - gg3) * rim)), 0, 255), RangesKt.coerceIn(MathKt.roundToInt(bb3 + ((255.0f - bb3) * rim)), 0, 255));
                            }
                            i++;
                            sideCam = this;
                            j2 = j;
                            fw2 = fw;
                            py2 = py;
                            now = now2;
                            vP = vP2;
                            px2 = px;
                            f2 = f;
                            side2 = side;
                            fh2 = fh;
                            vb2 = vb;
                        }
                        j2++;
                        want2 = want;
                        pl = pl2;
                        yP = yP2;
                        uP = uP2;
                        side2 = side2;
                        sideCam = this;
                    }
                    images.put(side2, Bitmap.createBitmap(px2, 240, 240, Bitmap.Config.ARGB_8888));
                    sideCam = this;
                    want2 = want;
                    m = m2;
                    now = now;
                }
            }
            imageAtMs = now;
            Function0<Unit> function0 = onFrame;
            if (function0 != null) {
                function0.invoke();
            }
        }
    }

    private final float smooth(float e0, float e1, float x) {
        float t = RangesKt.coerceIn((x - e0) / (e1 - e0), 0.0f, 1.0f);
        return t * t * (3 - (2 * t));
    }
}
