package com.starpilot.starview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.starpilot.starview.Ideas;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: IdeasMemory.kt */
public final class DayView {
    private File dir;
    private volatile Ideas.Here here;
    private ArrayList<double[]> index;
    private long lastLookMs;
    private double lastSaveLat;
    private double lastSaveLon;
    private volatile Bitmap showing;
    private volatile long showingAtMs;
    private volatile boolean wanted;

    public final boolean getWanted() {
        return this.wanted;
    }

    public final void setWanted(boolean z) {
        this.wanted = z;
    }

    public final Ideas.Here getHere() {
        return this.here;
    }

    public final void setHere(Ideas.Here here) {
        this.here = here;
    }

    public final Bitmap getShowing() {
        return this.showing;
    }

    public final long getShowingAtMs() {
        return this.showingAtMs;
    }

    public final void setShowing(Bitmap bitmap) {
        this.showing = bitmap;
    }

    public final void setShowingAtMs(long j) {
        this.showingAtMs = j;
    }

    public final void init(File d) {
        Intrinsics.checkNotNullParameter(d, "d");
        d.mkdirs();
        this.dir = d;
    }

    /* recovered by hand from the bytecode (jadx lost its synchronized/use blocks) */
    public final void feed(Bitmap bmp, float meanY, long nowMs) {
        File d;
        Intrinsics.checkNotNullParameter(bmp, "bmp");
        Ideas.Here h = this.here;
        if (h == null || (d = this.dir) == null || Double.isNaN(h.getYaw())) {
            return;
        }
        if (Intrinsics.areEqual(h.getRtk(), "none") && h.getV() < 1.0d) {
            return;
        }
        ArrayList it2 = this.index;
        if (it2 == null) {
            it2 = loadIndex(d);
            this.index = it2;
        }
        ArrayList it3 = it2;
        if (meanY <= 90.0f || h.getV() <= 3.0d) {
            if (meanY >= 45.0f || nowMs - this.lastLookMs <= 1000) {
                return;
            }
            this.lastLookMs = nowMs;
            Object obj;
            synchronized (it3) {
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : it3) {
                    if (IdeaSpots.INSTANCE.headingOk(((double[]) element$iv$iv)[2], h.getHdgDeg(), 30.0d)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                Iterator iterator$iv = ((List) destination$iv$iv).iterator();
                if (iterator$iv.hasNext()) {
                    Object minElem$iv = iterator$iv.next();
                    if (iterator$iv.hasNext()) {
                        double[] it4 = (double[]) minElem$iv;
                        double minValue$iv = Math.hypot((it4[0] - h.getLat()) * 111195.0d, (it4[1] - h.getLon()) * 111320.0d * Math.cos(Math.toRadians(h.getLat())));
                        do {
                            Object e$iv = iterator$iv.next();
                            double[] it5 = (double[]) e$iv;
                            double v$iv = Math.hypot((it5[0] - h.getLat()) * 111195.0d, (it5[1] - h.getLon()) * 111320.0d * Math.cos(Math.toRadians(h.getLat())));
                            if (Double.compare(minValue$iv, v$iv) > 0) {
                                minElem$iv = e$iv;
                                minValue$iv = v$iv;
                            }
                        } while (iterator$iv.hasNext());
                    }
                    obj = minElem$iv;
                } else {
                    obj = null;
                }
            }
            double[] best = (double[]) obj;
            if (best == null || Math.hypot((best[0] - h.getLat()) * 111195.0d, (best[1] - h.getLon()) * 111320.0d * Math.cos(Math.toRadians(h.getLat()))) >= 12.0d) {
                return;
            }
            String format = String.format(Locale.US, "%.6f_%.6f_%03d_%d.jpg", Arrays.copyOf(new Object[]{Double.valueOf(best[0]), Double.valueOf(best[1]), Integer.valueOf((int) best[2]), Long.valueOf((long) best[3])}, 4));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            File f = new File(d, format);
            Bitmap it6 = BitmapFactory.decodeFile(f.getPath());
            if (it6 != null) {
                this.showing = it6;
                this.showingAtMs = nowMs;
            }
            return;
        }
        double moved = Math.hypot((h.getLat() - this.lastSaveLat) * 111195.0d, (h.getLon() - this.lastSaveLon) * 111320.0d * Math.cos(Math.toRadians(h.getLat())));
        if (moved < 15.0d) {
            return;
        }
        this.lastSaveLat = h.getLat();
        this.lastSaveLon = h.getLon();
        Bitmap small = Bitmap.createScaledBitmap(bmp, 320, (bmp.getHeight() * 320) / bmp.getWidth(), true);
        Intrinsics.checkNotNullExpressionValue(small, "createScaledBitmap(...)");
        String name = String.format(Locale.US, "%.6f_%.6f_%03d_%d.jpg", Arrays.copyOf(new Object[]{Double.valueOf(h.getLat()), Double.valueOf(h.getLon()), Integer.valueOf((int) h.getHdgDeg()), Long.valueOf(nowMs)}, 4));
        Intrinsics.checkNotNullExpressionValue(name, "format(...)");
        try {
            FileOutputStream it = new FileOutputStream(new File(d, name));
            Throwable cause = null;
            try {
                small.compress(Bitmap.CompressFormat.JPEG, 60, it);
            } catch (Throwable th) {
                cause = th;
                throw th;
            } finally {
                CloseableKt.closeFinally(it, cause);
            }
        } catch (Exception e) {
        }
        small.recycle();
        synchronized (it3) {
            it3.add(new double[]{h.getLat(), h.getLon(), h.getHdgDeg(), nowMs});
        }
        if (it3.size() % 200 == 0) {
            trim(d, it3);
        }
    }

    private final ArrayList<double[]> loadIndex(File d) {
        ArrayList $this$loadIndex_u24lambda_u249 = new ArrayList();
        String[] list = d.list();
        if (list != null) {
            for (String str : list) {
                Intrinsics.checkNotNull(str);
                List p = StringsKt.split((CharSequence) StringsKt.removeSuffix(str, (CharSequence) ".jpg"), new String[]{"_"}, false, 0);
                if (p.size() == 4) {
                    try {
                        $this$loadIndex_u24lambda_u249.add(new double[]{Double.parseDouble((String) p.get(0)), Double.parseDouble((String) p.get(1)), Double.parseDouble((String) p.get(2)), Double.parseDouble((String) p.get(3))});
                    } catch (Exception e) {
                    }
                }
            }
        }
        return $this$loadIndex_u24lambda_u249;
    }

    private final void trim(File d, ArrayList<double[]> idx) {
        File[] files = d.listFiles();
        if (files == null) {
            return;
        }
        long j = 0;
        for (File it : files) {
            j += it.length();
        }
        long total = j;
        if (total < 419430400) {
            return;
        }
        for (File f : (Iterable<File>) ArraysKt.sortedWith(files, new Comparator() { // from class: com.starpilot.starview.DayView$trim$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(Object t, Object t2) {
                File it2 = (File) t;
                String name = it2.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                String substringAfterLast$default = StringsKt.substringAfterLast(name, '_', name);
                File it3 = (File) t2;
                String name2 = it3.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                return ComparisonsKt.compareValues(substringAfterLast$default, StringsKt.substringAfterLast(name2, '_', name2));
            }
        })) {
            if (total < 367001600) {
                break;
            }
            total -= f.length();
            f.delete();
        }
        synchronized (idx) {
            idx.clear();
            idx.addAll(loadIndex(d));
        }
    }
}
