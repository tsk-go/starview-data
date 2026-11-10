package com.starpilot.starview;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.PixelCopy;
import android.view.SurfaceView;
import com.starpilot.starview.LaneBrain;
import com.starpilot.starview.LaneFixDialog;
import com.starpilot.starview.LaneRamps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import android.graphics.Paint.Align;

/* compiled from: LaneShots.kt */
public final class LaneShots {
    public static final Companion Companion = new Companion(null);
    private Function0<String> answer;
    private final Context ctx;
    private volatile Detector detector;
    private volatile String driveFolder;
    private final File index;

    /* renamed from: io$delegate, reason: from kotlin metadata */
    private final Lazy io;
    private Function0<Pair<Integer, Integer>> lanes;
    private long lastEventMs;
    private long lastPeriodicMs;
    private Function0<String> modelSays;
    private Function0<? extends List<LaneRamps.Ramp>> ramps;
    private final File reviews;
    private Function0<LaneBrain.Road> road;
    private Function0<? extends SurfaceView> scene;

    /* compiled from: LaneShots.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Why.values().length];
            try {
                iArr[Why.EVENT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Why.EVERY_30S.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Why.USER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* compiled from: LaneShots.kt */
    public enum Why {
        EVENT,
        USER,
        EVERY_30S;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Why> getEntries() {
            return $ENTRIES;
        }
    }

    public LaneShots(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
        this.index = new File(this.ctx.getFilesDir(), "lane_shots.txt");
        this.reviews = new File(this.ctx.getFilesDir(), "lane_reviews.txt");
        this.io = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.LaneShots$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ExecutorService newSingleThreadExecutor;
                newSingleThreadExecutor = Executors.newSingleThreadExecutor();
                return newSingleThreadExecutor;
            }
        });
        this.driveFolder = "Pictures/StarView Lanes/" + new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.US).format(new Date());
    }

    /* compiled from: LaneShots.kt */
    public static final class Shot {
        private final long atMs;
        private final String caption;
        private final String drive;
        private final String model;
        private final String name;
        private final String ramps;
        private final String roadFile;
        private final String street;
        private final String uri;
        private final long wayId;
        private final String why;

        public Shot(String name, String uri, long atMs, String why, String caption, String drive, long wayId, String street, String ramps, String model, String roadFile) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(uri, "uri");
            Intrinsics.checkNotNullParameter(why, "why");
            Intrinsics.checkNotNullParameter(caption, "caption");
            Intrinsics.checkNotNullParameter(drive, "drive");
            Intrinsics.checkNotNullParameter(street, "street");
            Intrinsics.checkNotNullParameter(ramps, "ramps");
            Intrinsics.checkNotNullParameter(model, "model");
            Intrinsics.checkNotNullParameter(roadFile, "roadFile");
            this.name = name;
            this.uri = uri;
            this.atMs = atMs;
            this.why = why;
            this.caption = caption;
            this.drive = drive;
            this.wayId = wayId;
            this.street = street;
            this.ramps = ramps;
            this.model = model;
            this.roadFile = roadFile;
        }

        public /* synthetic */ Shot(String str, String str2, long j, String str3, String str4, String str5, long j2, String str6, String str7, String str8, String str9, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, j, str3, str4, str5, (i & 64) != 0 ? 0L : j2, (i & 128) != 0 ? "" : str6, (i & 256) != 0 ? "" : str7, (i & 512) != 0 ? "" : str8, (i & 1024) != 0 ? "" : str9);
        }

        public final long getAtMs() {
            return this.atMs;
        }

        public final String getCaption() {
            return this.caption;
        }

        public final String getDrive() {
            return this.drive;
        }

        public final String getName() {
            return this.name;
        }

        public final String getUri() {
            return this.uri;
        }

        public final String getWhy() {
            return this.why;
        }

        public final String getModel() {
            return this.model;
        }

        public final String getRamps() {
            return this.ramps;
        }

        public final String getRoadFile() {
            return this.roadFile;
        }

        public final String getStreet() {
            return this.street;
        }

        public final long getWayId() {
            return this.wayId;
        }
    }

    public final Detector getDetector() {
        return this.detector;
    }

    public final void setDetector(Detector detector) {
        this.detector = detector;
    }

    public final Function0<LaneBrain.Road> getRoad() {
        return this.road;
    }

    public final void setRoad(Function0<LaneBrain.Road> function0) {
        this.road = function0;
    }

    public final Function0<String> getAnswer() {
        return this.answer;
    }

    public final void setAnswer(Function0<String> function0) {
        this.answer = function0;
    }

    public final File getCleanDir() {
        File externalFilesDir = this.ctx.getExternalFilesDir(null);
        if (externalFilesDir == null) {
            externalFilesDir = this.ctx.getFilesDir();
        }
        return new File(externalFilesDir, "lane_clean");
    }

    public final Function0<SurfaceView> getScene() {
        return (Function0) this.scene;
    }

    public final void setScene(Function0<? extends SurfaceView> function0) {
        this.scene = function0;
    }

    public final Function0<Pair<Integer, Integer>> getLanes() {
        return this.lanes;
    }

    public final void setLanes(Function0<Pair<Integer, Integer>> function0) {
        this.lanes = function0;
    }

    public final Function0<List<LaneRamps.Ramp>> getRamps() {
        return (Function0) this.ramps;
    }

    public final void setRamps(Function0<? extends List<LaneRamps.Ramp>> function0) {
        this.ramps = function0;
    }

    public final Function0<String> getModelSays() {
        return this.modelSays;
    }

    public final void setModelSays(Function0<String> function0) {
        this.modelSays = function0;
    }

    private final ExecutorService getIo() {
        return (ExecutorService) this.io.getValue();
    }

    public final String getDriveFolder() {
        return this.driveFolder;
    }

    public final void setDriveFolder(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.driveFolder = str;
    }

    public final void newDrive() {
        this.driveFolder = "Pictures/StarView Lanes/" + new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.US).format(new Date());
    }

    private final boolean roomLeft() {
        try {
            return Environment.getExternalStorageDirectory().getUsableSpace() > 2147483648L;
        } catch (Exception e) {
            return true;
        }
    }

    public static /* synthetic */ void request$default(LaneShots laneShots, String str, Why why, File file, int i, Object obj) {
        if ((i & 4) != 0) {
            file = null;
        }
        laneShots.request(str, why, file);
    }

    public final void request(final String caption, final Why why, final File copyTo) {
        Pair<Integer, Integer> pair;
        String str;
        final List rp;
        final String ms;
        final Ref.BooleanRef wideDone;
        final Ref.BooleanRef sceneDone;
        final Bitmap[] wide;
        final Bitmap[] clean;
        final Bitmap[] cam;
        final Bitmap[] side;
        SurfaceView sv;
        final long now;
        Detector det;
        Ref.BooleanRef sceneDone2;
        List emptyList;
        String str2 = "";
        Intrinsics.checkNotNullParameter(caption, "caption");
        Intrinsics.checkNotNullParameter(why, "why");
        Detector det2 = this.detector;
        if (det2 != null && roomLeft()) {
            long now2 = System.currentTimeMillis();
            switch (WhenMappings.$EnumSwitchMapping$0[why.ordinal()]) {
                case 1:
                    if (now2 - this.lastEventMs >= 5000) {
                        this.lastEventMs = now2;
                        break;
                    } else {
                        return;
                    }
                case 2:
                    if (now2 - this.lastPeriodicMs >= 30000) {
                        this.lastPeriodicMs = now2;
                        break;
                    } else {
                        return;
                    }
                case 3:
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            if (why != Why.EVERY_30S) {
                this.lastPeriodicMs = now2;
            }
            Function0<Pair<Integer, Integer>> function0 = this.lanes;
            if (function0 == null || (pair = function0.invoke()) == null) {
                pair = TuplesKt.to(0, 0);
            }
            final int m = pair.component1().intValue();
            final int n = pair.component2().intValue();
            Function0<LaneBrain.Road> function02 = this.road;
            final LaneBrain.Road rd = function02 != null ? function02.invoke() : null;
            Function0<String> function03 = this.answer;
            if (function03 == null || (str = function03.invoke()) == null) {
                str = "0,0,0";
            }
            final String ans = str;
            Function0<? extends SurfaceView> function04 = this.scene;
            SurfaceView sv2 = function04 != null ? function04.invoke() : null;
            try {
                Function0<? extends List<LaneRamps.Ramp>> function05 = this.ramps;
                if (function05 == null || (emptyList = function05.invoke()) == null) {
                    emptyList = CollectionsKt.emptyList();
                }
                rp = emptyList;
            } catch (Exception e) {
                rp = CollectionsKt.emptyList();
            }
            try {
                Function0<String> function06 = this.modelSays;
                if (function06 != null) {
                    String invoke = function06.invoke();
                    if (invoke != null) {
                        str2 = invoke;
                    }
                }
                ms = str2;
            } catch (Exception e2) {
                ms = "";
            }
            Bitmap[] side2 = new Bitmap[1];
            final Bitmap[] cam2 = new Bitmap[1];
            Bitmap[] clean2 = new Bitmap[1];
            final Bitmap[] wide2 = new Bitmap[1];
            Ref.BooleanRef sceneDone3 = new Ref.BooleanRef();
            sceneDone3.element = sv2 == null;
            Ref.BooleanRef wideDone2 = new Ref.BooleanRef();
            wideDone2.element = !det2.wideLive();
            final Object lock = new Object();
            if (wideDone2.element) {
                wideDone = wideDone2;
                sceneDone = sceneDone3;
                wide = wide2;
                clean = clean2;
                cam = cam2;
                side = side2;
                sv = sv2;
                now = now2;
                det = det2;
            } else {
                wideDone = wideDone2;
                sceneDone = sceneDone3;
                wide = wide2;
                clean = clean2;
                cam = cam2;
                side = side2;
                sv = sv2;
                now = now2;
                det = det2;
                det.setWideReq(new Function1() { // from class: com.starpilot.starview.LaneShots$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit request$lambda$4;
                        request$lambda$4 = LaneShots.request$lambda$4(lock, wideDone, wide2, cam2, sceneDone, clean, LaneShots.this, side, m, n, caption, why, now, rd, ans, rp, ms, copyTo, (Bitmap) obj);
                        return request$lambda$4;
                    }
                });
                final LaneBrain.Road road = rd;
                final List list = rp;
                final String str3 = ms;
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.starpilot.starview.LaneShots$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaneShots.request$lambda$6(lock, wideDone, cam, sceneDone, clean, wide, LaneShots.this, side, m, n, caption, why, now, road, ans, list, str3, copyTo);
                    }
                }, 800L);
            }
            SurfaceView sv3 = sv;
            if (sv3 == null || sv3.getWidth() <= 0 || sv3.getHeight() <= 0) {
                sceneDone2 = sceneDone;
                sceneDone2.element = true;
            } else {
                try {
                    final Bitmap b = Bitmap.createBitmap(640, (sv3.getHeight() * 640) / sv3.getWidth(), Bitmap.Config.ARGB_8888);
                    Intrinsics.checkNotNullExpressionValue(b, "createBitmap(...)");
                    final Bitmap[] bitmapArr = side;
                    final Ref.BooleanRef booleanRef = sceneDone;
                    final Bitmap[] bitmapArr2 = cam;
                    final Ref.BooleanRef booleanRef2 = wideDone;
                    final Bitmap[] bitmapArr3 = clean;
                    final Bitmap[] bitmapArr4 = wide;
                    final long j = now;
                    final LaneBrain.Road road2 = rd;
                    final List list2 = rp;
                    final String str4 = ms;
                    PixelCopy.request(sv3, b, new PixelCopy.OnPixelCopyFinishedListener() { // from class: com.starpilot.starview.LaneShots$$ExternalSyntheticLambda4
                        @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                        public final void onPixelCopyFinished(int i) {
                            LaneShots.request$lambda$8(lock, bitmapArr, b, booleanRef, bitmapArr2, booleanRef2, bitmapArr3, bitmapArr4, LaneShots.this, m, n, caption, why, j, road2, ans, list2, str4, copyTo, i);
                        }
                    }, new Handler(Looper.getMainLooper()));
                    sceneDone2 = sceneDone;
                } catch (Exception e3) {
                    sceneDone2 = sceneDone;
                    sceneDone2.element = true;
                }
            }
            final Bitmap[] bitmapArr5 = cam;
            final Bitmap[] bitmapArr6 = clean;
            final Detector detector = det;
            final Ref.BooleanRef booleanRef3 = sceneDone2;
            final Ref.BooleanRef booleanRef4 = wideDone;
            final Bitmap[] bitmapArr7 = wide;
            final Bitmap[] bitmapArr8 = side;
            final long j2 = now;
            final LaneBrain.Road road3 = rd;
            final List list3 = rp;
            final String str5 = ms;
            det.setShotReq(new Function1() { // from class: com.starpilot.starview.LaneShots$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit request$lambda$10;
                    request$lambda$10 = LaneShots.request$lambda$10(lock, bitmapArr5, bitmapArr6, detector, booleanRef3, booleanRef4, bitmapArr7, LaneShots.this, bitmapArr8, m, n, caption, why, j2, road3, ans, list3, str5, copyTo, (Bitmap) obj);
                    return request$lambda$10;
                }
            });
        }
    }

    private static final void request$both(Object lock, Bitmap[] cam, Ref.BooleanRef sceneDone, Ref.BooleanRef wideDone, Bitmap[] clean, Bitmap[] wide, final LaneShots this$0, final Bitmap[] side, final int m, final int n, final String $caption, final Why $why, final long now, final LaneBrain.Road rd, final String ans, final List<LaneRamps.Ramp> list, final String ms, final File $copyTo) {
        synchronized (lock) {
            final Bitmap c = cam[0];
            if (c != null && sceneDone.element && wideDone.element) {
                cam[0] = null;
                final Bitmap cl = clean[0];
                final Bitmap wd = wide[0];
                this$0.getIo().execute(new Runnable() { // from class: com.starpilot.starview.LaneShots$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaneShots.request$both$lambda$2$lambda$1(this$0, c, side, m, n, $caption, $why, now, rd, ans, cl, wd, list, ms, $copyTo);
                    }
                });
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void request$both$lambda$2$lambda$1(LaneShots this$0, Bitmap $c, Bitmap[] $side, int $m, int $n, String $caption, Why $why, long $now, LaneBrain.Road $rd, String $ans, Bitmap $cl, Bitmap $wd, List $rp, String $ms, File $copyTo) {
        this$0.save($c, $side[0], $m, $n, $caption, $why, $now, $rd, $ans, $cl, $wd, $rp, $ms, $copyTo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit request$lambda$4(Object $lock, Ref.BooleanRef $wideDone, Bitmap[] $wide, Bitmap[] $cam, Ref.BooleanRef $sceneDone, Bitmap[] $clean, LaneShots this$0, Bitmap[] $side, int $m, int $n, String $caption, Why $why, long $now, LaneBrain.Road $rd, String $ans, List $rp, String $ms, File $copyTo, Bitmap b) {
        Intrinsics.checkNotNullParameter(b, "b");
        synchronized ($lock) {
            if (!$wideDone.element) {
                $wide[0] = b;
                $wideDone.element = true;
            }
            Unit unit = Unit.INSTANCE;
        }
        request$both($lock, $cam, $sceneDone, $wideDone, $clean, $wide, this$0, $side, $m, $n, $caption, $why, $now, $rd, $ans, $rp, $ms, $copyTo);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void request$lambda$6(Object $lock, Ref.BooleanRef $wideDone, Bitmap[] $cam, Ref.BooleanRef $sceneDone, Bitmap[] $clean, Bitmap[] $wide, LaneShots this$0, Bitmap[] $side, int $m, int $n, String $caption, Why $why, long $now, LaneBrain.Road $rd, String $ans, List $rp, String $ms, File $copyTo) {
        synchronized ($lock) {
            $wideDone.element = true;
            Unit unit = Unit.INSTANCE;
        }
        request$both($lock, $cam, $sceneDone, $wideDone, $clean, $wide, this$0, $side, $m, $n, $caption, $why, $now, $rd, $ans, $rp, $ms, $copyTo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void request$lambda$8(Object $lock, Bitmap[] $side, Bitmap $b, Ref.BooleanRef $sceneDone, Bitmap[] $cam, Ref.BooleanRef $wideDone, Bitmap[] $clean, Bitmap[] $wide, LaneShots this$0, int $m, int $n, String $caption, Why $why, long $now, LaneBrain.Road $rd, String $ans, List $rp, String $ms, File $copyTo, int r) {
        synchronized ($lock) {
            if (r == 0) {
                $side[0] = $b;
            }
            $sceneDone.element = true;
            Unit unit = Unit.INSTANCE;
        }
        request$both($lock, $cam, $sceneDone, $wideDone, $clean, $wide, this$0, $side, $m, $n, $caption, $why, $now, $rd, $ans, $rp, $ms, $copyTo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit request$lambda$10(Object $lock, Bitmap[] $cam, Bitmap[] $clean, Detector $det, Ref.BooleanRef $sceneDone, Ref.BooleanRef $wideDone, Bitmap[] $wide, LaneShots this$0, Bitmap[] $side, int $m, int $n, String $caption, Why $why, long $now, LaneBrain.Road $rd, String $ans, List $rp, String $ms, File $copyTo, Bitmap bmp) {
        Intrinsics.checkNotNullParameter(bmp, "bmp");
        synchronized ($lock) {
            $cam[0] = bmp;
            $clean[0] = $det.getLastClean();
            Unit unit = Unit.INSTANCE;
        }
        request$both($lock, $cam, $sceneDone, $wideDone, $clean, $wide, this$0, $side, $m, $n, $caption, $why, $now, $rd, $ans, $rp, $ms, $copyTo);
        return Unit.INSTANCE;
    }

    public final void tick(Function0<String> caption) {
        Intrinsics.checkNotNullParameter(caption, "caption");
        if (System.currentTimeMillis() - this.lastPeriodicMs >= 30000) {
            request$default(this, caption.invoke(), Why.EVERY_30S, null, 4, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void save(Bitmap src, Bitmap sceneBmp, int m, int n, String caption, Why why, long atMs, LaneBrain.Road rd, String ans, Bitmap clean, Bitmap wide, List<LaneRamps.Ramp> rp, String ms, File copyTo) {
        FileOutputStream fileOutputStream;
        String str;
        try {
            Bitmap out = compose(src, sceneBmp, m, n, caption, rp);
            if (copyTo != null) {
                try {
                    fileOutputStream = new FileOutputStream(copyTo);
                    Throwable cause = null;
                    try {
                        out.compress(Bitmap.CompressFormat.JPEG, 82, fileOutputStream);
                    } catch (Throwable th) {
                        cause = th;
                        throw th;
                    } finally {
                        CloseableKt.closeFinally(fileOutputStream, cause);
                    }
                } catch (Exception e) {
                }
            }
            if (sceneBmp != null) {
                sceneBmp.recycle();
            }
            try {
                String name = "lane_" + new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(new Date(atMs)) + ".jpg";
                ContentValues $this$save_u24lambda_u2412 = new ContentValues();
                $this$save_u24lambda_u2412.put("_display_name", name);
                $this$save_u24lambda_u2412.put("mime_type", "image/jpeg");
                $this$save_u24lambda_u2412.put("relative_path", this.driveFolder);
                Uri uri = this.ctx.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, $this$save_u24lambda_u2412);
                if (uri == null) {
                    return;
                }
                OutputStream openOutputStream = this.ctx.getContentResolver().openOutputStream(uri);
                if (openOutputStream != null) {
                    Throwable cause2 = null;
                    try {
                        out.compress(Bitmap.CompressFormat.JPEG, 72, openOutputStream);
                    } catch (Throwable th) {
                        cause2 = th;
                        throw th;
                    } finally {
                        CloseableKt.closeFinally(openOutputStream, cause2);
                    }
                }
                out.recycle();
                src.recycle();
                String drive = StringsKt.substringAfterLast(this.driveFolder, '/', this.driveFolder);
                File $this$save_u24lambda_u2414 = new File(getCleanDir(), drive);
                $this$save_u24lambda_u2414.mkdirs();
                String base = StringsKt.removeSuffix(name, (CharSequence) ".jpg");
                String roadF = save$keep($this$save_u24lambda_u2414, base, drive, clean, "road");
                String wideF = save$keep($this$save_u24lambda_u2414, base, drive, wide, "wide");
                File file = this.index;
                String[] strArr = new String[13];
                strArr[0] = name;
                strArr[1] = uri.toString();
                strArr[2] = String.valueOf(atMs);
                strArr[3] = why.name();
                strArr[4] = StringsKt.replace(StringsKt.replace(caption, "\n", " | ", false), "\t", " ", false);
                strArr[5] = drive;
                strArr[6] = String.valueOf(rd != null ? rd.getId() : 0L);
                if (rd == null || (str = rd.getName()) == null) {
                    str = "";
                }
                strArr[7] = StringsKt.replace(str, "\t", " ", false);
                strArr[8] = ans;
                strArr[9] = roadF;
                strArr[10] = wideF;
                strArr[11] = LaneRamps.INSTANCE.codes(rp);
                strArr[12] = ms;
                FilesKt.appendText(file, CollectionsKt.joinToString(CollectionsKt.listOf((Object[]) strArr), "\t", "", "", -1, "...", null) + "\n", kotlin.text.Charsets.UTF_8);
                if (StringsKt.startsWith(caption, "[auto]", false)) {
                    mark$default(this, name, 1, null, 4, null);
                }
            } catch (Exception e2) {
            }
        } catch (Exception e3) {
        }
    }

    private static final String save$keep(File dir, String base, String drive, Bitmap b, String tag) {
        String str = "";
        if (b == null) {
            return "";
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(dir, base + "_" + tag + ".jpg"));
            try {
                FileOutputStream it = fileOutputStream;
                b.compress(Bitmap.CompressFormat.JPEG, 80, it);
                CloseableKt.closeFinally(fileOutputStream, null);
                str = drive + "/" + base + "_" + tag + ".jpg";
            } finally {
            }
        } catch (Exception e) {
        } catch (Throwable th) {
            b.recycle();
            throw th;
        }
        b.recycle();
        return str;
    }

    public static /* synthetic */ Bitmap compose$default(LaneShots laneShots, Bitmap bitmap, Bitmap bitmap2, int i, int i2, String str, List list, int i3, Object obj) {
        if ((i3 & 32) != 0) {
            list = CollectionsKt.emptyList();
        }
        return laneShots.compose(bitmap, bitmap2, i, i2, str, list);
    }

    public final Bitmap compose(Bitmap src, Bitmap sceneBmp, int m, int n, String caption, List<LaneRamps.Ramp> rp) {
        float rx;
        int i;
        Canvas c;
        Bitmap out;
        int rightW;
        int rightW2;
        int rightW3;
        float x;
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(caption, "caption");
        Intrinsics.checkNotNullParameter(rp, "rp");
        List lines = StringsKt.split((CharSequence) caption, new String[]{"\n"}, false, 0);
        int bar = (lines.size() * 26) + 30;
        int h = src.getHeight();
        int rightW4 = sceneBmp != null ? (sceneBmp.getWidth() * h) / sceneBmp.getHeight() : 300;
        Bitmap out2 = Bitmap.createBitmap(src.getWidth() + 6 + rightW4, h + bar, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(out2, "createBitmap(...)");
        Canvas c2 = new Canvas(out2);
        c2.drawColor(-16777216);
        c2.drawBitmap(src, 0.0f, 0.0f, (Paint) null);
        float rx2 = 6.0f + src.getWidth();
        if (sceneBmp != null) {
            c2.drawBitmap(sceneBmp, (Rect) null, new RectF(rx2, 0.0f, rightW4 + rx2, h), new Paint(2));
            rx = rx2;
            c = c2;
            out = out2;
            rightW = rightW4;
            i = -16777216;
        } else {
            rx = rx2;
            i = -16777216;
            c = c2;
            out = out2;
            rightW = rightW4;
            sketch(c2, rx2, rightW4, h, m, n, rp);
        }
        if (!rp.isEmpty()) {
            Paint $this$compose_u24lambda_u2416 = new Paint(1);
            $this$compose_u24lambda_u2416.setColor(i);
            $this$compose_u24lambda_u2416.setTextSize(19.0f);
            $this$compose_u24lambda_u2416.setTypeface(Typeface.DEFAULT_BOLD);
            Paint bp = new Paint(1);
            bp.setColor(Color.rgb(240, 181, 74));
            List<LaneRamps.Ramp> $this$forEachIndexed$iv = rp;
            int $i$f$forEachIndexed = 0;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int index$iv2 = index$iv + 1;
                if (index$iv < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                LaneRamps.Ramp r = (LaneRamps.Ramp) item$iv;
                String text = r.text();
                Locale US = Locale.US;
                Intrinsics.checkNotNullExpressionValue(US, "US");
                String upperCase = text.toUpperCase(US);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                String t = "MAP: " + upperCase;
                float tw = $this$compose_u24lambda_u2416.measureText(t);
                float y = (index$iv * 34.0f) + 10.0f;
                Iterable $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
                if (r.getSide() < 0) {
                    rightW2 = rightW;
                    rightW3 = $i$f$forEachIndexed;
                    x = rx + 8.0f;
                } else {
                    rightW2 = rightW;
                    rightW3 = $i$f$forEachIndexed;
                    x = ((rx + rightW2) - tw) - 24.0f;
                }
                int rightW5 = rightW2;
                c.drawRoundRect(new RectF(x, y, x + tw + 16.0f, y + 28.0f), 8.0f, 8.0f, bp);
                c.drawText(t, x + 8.0f, 21.0f + y, $this$compose_u24lambda_u2416);
                index$iv = index$iv2;
                $i$f$forEachIndexed = rightW3;
                $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                rightW = rightW5;
            }
        }
        Paint big = new Paint(1);
        big.setColor(-1);
        big.setTextSize(22.0f);
        big.setTypeface(Typeface.DEFAULT_BOLD);
        Paint small = new Paint(1);
        small.setColor(Color.rgb(200, 208, 222));
        small.setTextSize(19.0f);
        int i2 = 0;
        for (Object item$iv2 : lines) {
            int index$iv3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String s = (String) item$iv2;
            Paint big2 = big;
            c.drawText(s, 12.0f, src.getHeight() + 30.0f + (i2 * 26.0f), i2 == 0 ? big2 : small);
            i2 = index$iv3;
            small = small;
            big = big2;
        }
        return out;
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final void sketch(Canvas var1, float var2, float var3, float var4, int var5, int var6, List<LaneRamps.Ramp> var7) {
        Paint var18 = new Paint(1);
        var18.setColor(Color.rgb(28, 32, 40));
        var1.drawRect(var2, 0.0F, var2 + var3, var4, var18);
        int var15;
        if (var5 > 0) {
            var15 = var5;
        } else if (var6 > 0) {
            var15 = var6 + 1;
        } else {
            var15 = 0;
        }

        Paint var17 = new Paint(1);
        var17.setColor(Color.rgb(200, 208, 222));
        var17.setTextSize(20.0F);
        var17.setTextAlign(android.graphics.Paint.Align.CENTER);
        if (var15 <= 0) {
            float var28 = 2;
            var1.drawText("lane unknown", var2 + var3 / var28, var4 / var28, var17);
        } else {
            float var11 = Math.min(64.0F, (var3 - 40.0F) / var15);
            float var8 = var15;
            float var12 = 2;
            float var13 = var2 + (var3 - var8 * var11) / var12;
            var18.setColor(Color.rgb(120, 126, 136));
            var1.drawRect(var13, 30.0F, var13 + var15 * var11, var4 - 20.0F, var18);
            if (var15 <= 0) {
                int var14 = 0;

                while (true) {
                    int var16;
                    if (var14 == 0) {
                        var16 = Color.rgb(242, 194, 48);
                    } else {
                        var16 = -1;
                    }

                    var18.setColor(var16);
                    float var9 = var13 + var14 * var11;
                    if (var14 != 0 && var14 != var15) {
                        for (float var25 = 34.0F; var25 < var4 - 24.0F; var25 += 36.0F) {
                            var1.drawRect(var9 - 1.5F, var25, var9 + 1.5F, var25 + 18.0F, var18);
                        }
                    } else {
                        var1.drawRect(var9 - 2.0F, 30.0F, var9 + 2.0F, var4 - 20.0F, var18);
                    }

                    if (var14 == var15) {
                        break;
                    }

                    var14++;
                }
            }

            boolean var30;
            if (1 <= var6 && var6 <= var15) {
                var30 = true;
            } else {
                var30 = false;
            }

            if (var30) {
                var18.setColor(Color.rgb(55, 138, 221));
                var8 = var13 + (var6 - 0.5F) * var11;
                var1.drawRoundRect(var8 - var11 * 0.28F, var4 - 120.0F, var8 + 0.28F * var11, var4 - 50.0F, 8.0F, 8.0F, var18);
            }

            for (LaneRamps.Ramp var21 : var7) {
                if (var21.getSide() < 0) {
                    var8 = var13 - var11;
                } else {
                    var8 = var13 + var15 * var11;
                }

                if (!(var8 < var2) && !(var8 + var11 > var2 + var3)) {
                    var18.setColor(Color.rgb(96, 72, 26));
                    var1.drawRect(var8, 30.0F, var8 + var11, var4 - 20.0F, var18);
                    var18.setColor(Color.rgb(240, 181, 74));
                    float var29;
                    if (var21.getSide() < 0) {
                        var29 = var8 - 2.0F;
                    } else {
                        var29 = var8 + var11 - 2.0F;
                    }

                    float var10;
                    if (var21.getSide() < 0) {
                        var10 = var8 + 2.0F;
                    } else {
                        var10 = var8 + var11 + 2.0F;
                    }

                    var1.drawRect(var29, 30.0F, var10, var4 - 20.0F, var18);
                    Paint var20 = new Paint(1);
                    var20.setColor(Color.rgb(240, 181, 74));
                    var20.setTextSize(15.0F);
                    var20.setTextAlign(android.graphics.Paint.Align.CENTER);
                    var20.setTypeface(Typeface.DEFAULT_BOLD);
                    String var22;
                    if (Intrinsics.areEqual(var21.getKind(), "exit")) {
                        var22 = "EXIT";
                    } else {
                        var22 = "MERGE";
                    }

                    var1.drawText(var22, var8 + var11 / var12, var4 / var12, var20);
                }
            }

            StringBuilder var24;
            if (var5 > 0) {
                String var23;
                if (var6 > 0) {
                    var23 = String.valueOf(var6);
                } else {
                    var23 = "?";
                }

                var24 = new StringBuilder().append("card: lane ").append(var23).append(" of ").append(var5);
            } else {
                var24 = new StringBuilder().append("card: lane ").append(var6).append(" of ?");
            }

            var1.drawText(var24.toString(), var2 + var3 / var12, 22.0F, var17);
        }
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final int deleteDrive(String var1) {
        ArrayList var33 = null; ArrayList var34 = null; String var53 = null; Object var57 = null; Object var61 = null; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "drive");

        Object var4;
        try {
            var4 = FilesKt.readLines(this.index, kotlin.text.Charsets.UTF_8);
        } catch (Exception var14) {
            return 0;
        }

        Iterable var43 = ((Iterable) var4);
        ArrayList var7 = new ArrayList();
        ArrayList var6 = new ArrayList();
        Iterator var8 = var43.iterator();

        while (true) {
            boolean var3 = var8.hasNext();
            Object var5 = "";
            if (!var3) {
                Pair var46 = new Pair<>(var7, var6);
                List var62 = (List)var46.component1();
                List var4s = (List)var46.component2();
                Iterator var63 = var62.iterator();

                while (var63.hasNext()) {
                    List var58 = StringsKt.split((String & CharSequence)var63.next(), new String[]{"\t"}, false, 0);

                    try {
                        this.ctx.getContentResolver().delete(Uri.parse((String)var58.get(1)), null, null);
                    } catch (Exception var13) {
                    }

                    boolean var37;
                    if (9 < var58.size()) {
                        var37 = true;
                    } else {
                        var37 = false;
                    }

                    Object var54;
                    if (var37) {
                        var54 = var58.get(9);
                    } else {
                        var54 = "";
                    }

                    if (10 < var58.size()) {
                        var37 = true;
                    } else {
                        var37 = false;
                    }

                    Object var59;
                    if (var37) {
                        var59 = var58.get(10);
                    } else {
                        var59 = "";
                    }

                    for (Object var54$it : (Iterable) CollectionsKt.listOf(new String[]{(String)var54, (String)var59})) {
                        var54 = var54$it;
                        if (((CharSequence)var54).length() > 0) {
                            var37 = true;
                        } else {
                            var37 = false;
                        }

                        if (var37) {
                            new File(this.getCleanDir(), (String)var54).delete();
                        }
                    }
                }

                File var56 = new File(this.getCleanDir(), var1);
                if (var56.isDirectory()) {
                    boolean var41;
                    label231: {
                        String[] var29 = var56.list();
                        if (var29 != null) {
                            if (var29.length == 0) {
                                var41 = true;
                            } else {
                                var41 = false;
                            }

                            if (!var41) {
                                var41 = true;
                                break label231;
                            }
                        }

                        var41 = false;
                    }

                    if (!var41) {
                        var56.delete();
                    }
                }

                File var30 = this.index;
                if (!var4s.isEmpty()) {
                    var5 = CollectionsKt.joinToString(var4s, "\n", "", "", -1, "...", null);
                    var5 = var5 + "\n";
                }

                FilesKt.writeText(var30, ((String) var5), kotlin.text.Charsets.UTF_8);
                var5 = var62;
                var var31 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
                Iterator var52 = ((Iterable) var5).iterator();

                while (var52.hasNext()) {
                    var31.add(StringsKt.substringBefore((String)var52.next(), '\t', ((String)var52.next())));
                }

                List var32 = var31;
                HashSet var64 = CollectionsKt.toHashSet(var32);

                try {
                    var61 = this.reviews;
                } catch (Exception var28) {
                    return var62.size();
                }

                try {
                    var57 = FilesKt.readLines(this.reviews, kotlin.text.Charsets.UTF_8);
                } catch (Exception var27) {
                    return var62.size();
                }

                try {
                } catch (Exception var26) {
                    return var62.size();
                }

                try {
                    var33 = new ArrayList();
                } catch (Exception var25) {
                    return var62.size();
                }

                ArrayList var10;
                try {
                    var10 = var33;
                } catch (Exception var24) {
                    return var62.size();
                }

                Iterator var11;
                try {
                    var11 = var57.iterator();
                } catch (Exception var23) {
                    return var62.size();
                }

                while (true) {
                    try {
                        if (!var11.hasNext()) {
                            break;
                        }
                    } catch (Exception var22) {
                        return var62.size();
                    }

                    try {
                        var4 = (List)var11.next();
                    } catch (Exception var21) {
                        return var62.size();
                    }

                    try {
                        var53 = (String)var4;
                    } catch (Exception var20) {
                        return var62.size();
                    }

                    try {
                        if (!var64.contains(StringsKt.substringBefore(var53, '\t', var53))) {
                            var10.add(var4);
                        }
                    } catch (Exception var19) {
                        return var62.size();
                    }
                }

                try {
                    var34 = var10;
                } catch (Exception var18) {
                    return var62.size();
                }

                try {
                    var1 = CollectionsKt.joinToString(var34, "\n", "", "", -1, "...", null);
                } catch (Exception var17) {
                    return var62.size();
                }

                boolean var42;
                label172: {
                    label171: {
                        try {
                            if (var1.length() == 0) {
                                break label171;
                            }
                        } catch (Exception var16) {
                            return var62.size();
                        }

                        var42 = false;
                        break label172;
                    }

                    var42 = true;
                }

                if (!var42) {
                    try {
                        StringBuilder var49 = new StringBuilder();
                        var1 = var49.append(var1).append("\n").toString();
                    } catch (Exception var15) {
                        return var62.size();
                    }
                }

                try {
                    FilesKt.writeText(((File) var61), var1, kotlin.text.Charsets.UTF_8);
                } catch (Exception var12) {
                }

                return var62.size();
            }

            Object var9 = var8.next();
            var4 = StringsKt.split((String & CharSequence)var9, new String[]{"\t"}, false, 0);
            boolean var2;
            if (5 < ((List) var4).size()) {
                var2 = true;
            } else {
                var2 = false;
            }

            if (var2) {
                var5 = (Iterable)((List) var4).get(5);
            }

            var4 = (CharSequence)var5;
            if (((CharSequence) var4).length() == 0) {
                var2 = true;
            } else {
                var2 = false;
            }

            if (var2) {
                var4 = "earlier";
            }

            if (Intrinsics.areEqual(var4, var1)) {
                var7.add(var9);
            } else {
                var6.add(var9);
            }
        }
    }

    public final List<Shot> list() {
        Shot shot;
        try {
            int i = 1;
            Iterable $this$mapNotNull$iv = FilesKt.readLines(this.index, kotlin.text.Charsets.UTF_8);
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
                String ln = (String) element$iv$iv$iv;
                String[] strArr = new String[i];
                strArr[0] = "\t";
                List p = StringsKt.split((CharSequence) ln, strArr, false, 0);
                Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                if (p.size() >= 5) {
                    String str = (String) p.get(0);
                    String str2 = (String) p.get(1);
                    Long longOrNull = StringsKt.toLongOrNull((String) p.get(2));
                    long longValue = longOrNull != null ? longOrNull.longValue() : 0L;
                    String str3 = (String) p.get(3);
                    String replace$default = StringsKt.replace((String) p.get(4), " | ", "\n", false);
                    Object obj = "";
                    String str4 = (String) (5 < p.size() ? p.get(5) : "");
                    Long longOrNull2 = StringsKt.toLongOrNull((String) (6 < p.size() ? p.get(6) : "0"));
                    long longValue2 = longOrNull2 != null ? longOrNull2.longValue() : 0L;
                    String str5 = (String) (7 < p.size() ? p.get(7) : "");
                    String str6 = (String) (11 < p.size() ? p.get(11) : "");
                    String str7 = (String) (12 < p.size() ? p.get(12) : "");
                    if (9 < p.size()) {
                        obj = p.get(9);
                    }
                    shot = new Shot(str, str2, longValue, str3, replace$default, str4, longValue2, str5, str6, str7, (String) obj);
                } else {
                    shot = null;
                }
                if (shot != null) {
                    destination$iv$iv.add(shot);
                }
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
                i = 1;
            }
            return (List) destination$iv$iv;
        } catch (Exception e) {
            return CollectionsKt.emptyList();
        }
    }

    public final Map<String, Integer> verdicts() {
        try {
            HashMap m = new HashMap();
            Iterable $this$forEach$iv = FilesKt.readLines(this.reviews, kotlin.text.Charsets.UTF_8);
            for (Object element$iv : $this$forEach$iv) {
                String ln = (String) element$iv;
                List p = StringsKt.split((CharSequence) ln, new String[]{"\t"}, false, 0);
                if (p.size() >= 2) {
                    HashMap hashMap = m;
                    Object obj = p.get(0);
                    Integer intOrNull = StringsKt.toIntOrNull((String) p.get(1));
                    hashMap.put(obj, Integer.valueOf(intOrNull != null ? intOrNull.intValue() : 0));
                }
            }
            return m;
        } catch (Exception e) {
            return new HashMap();
        }
    }

    public final Map<String, String> skipReasons() {
        try {
            HashMap m = new HashMap();
            Iterable $this$forEach$iv = FilesKt.readLines(this.reviews, kotlin.text.Charsets.UTF_8);
            for (Object element$iv : $this$forEach$iv) {
                String ln = (String) element$iv;
                List p = StringsKt.split((CharSequence) ln, new String[]{"\t"}, false, 0);
                if (p.size() >= 2) {
                    if (Intrinsics.areEqual(p.get(1), "0")) {
                        m.put(p.get(0), 2 < p.size() ? p.get(2) : "skip");
                    } else {
                        m.remove(p.get(0));
                    }
                }
            }
            return m;
        } catch (Exception e) {
            return new HashMap();
        }
    }

    public static /* synthetic */ void mark$default(LaneShots laneShots, String str, int i, String str2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str2 = "";
        }
        laneShots.mark(str, i, str2);
    }

    public final void mark(String name, int v, String why) {
        String str;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(why, "why");
        try {
            File file = this.reviews;
            if (v == 0) {
                if (why.length() > 0) {
                    str = "\t" + why;
                    FilesKt.appendText(file, name + "\t" + v + str + "\n", kotlin.text.Charsets.UTF_8);
                }
            }
            str = "";
            FilesKt.appendText(file, name + "\t" + v + str + "\n", kotlin.text.Charsets.UTF_8);
        } catch (Exception e) {
        }
    }

    public final void markFix(String name, LaneFixDialog.Result r) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(r, "r");
        try {
            FilesKt.appendText(this.reviews, name + "\t-1\t" + r.getThrough() + "\t" + r.getLane() + "\t" + r.getExtraL() + "\t" + r.getExtraR() + "\t" + r.getKind() + "\t" + r.getWay() + "\t" + r.getOtherWay() + "\t" + r.getKindR() + "\n", kotlin.text.Charsets.UTF_8);
        } catch (Exception e) {
        }
    }

    public final Map<String, String> fixes() {
        HashMap r;
        try {
            r = new HashMap();
        } catch (Exception e) {
        }
        try {
            Iterable $this$forEach$iv = FilesKt.readLines(this.reviews, kotlin.text.Charsets.UTF_8);
            for (Object element$iv : $this$forEach$iv) {
                String ln = (String) element$iv;
                List p = StringsKt.split((CharSequence) ln, new String[]{"\t"}, false, 0);
                if (p.size() >= 4 && Intrinsics.areEqual(p.get(1), "-1")) {
                    HashMap hashMap = r;
                    Object obj = p.get(0);
                    Integer intOrNull = StringsKt.toIntOrNull((String) p.get(2));
                    int intValue = intOrNull != null ? intOrNull.intValue() : 0;
                    Object obj2 = "0";
                    Integer intOrNull2 = StringsKt.toIntOrNull((String) (4 < p.size() ? p.get(4) : "0"));
                    int intValue2 = intOrNull2 != null ? intOrNull2.intValue() : 0;
                    Integer intOrNull3 = StringsKt.toIntOrNull((String) (5 < p.size() ? p.get(5) : "0"));
                    int intValue3 = intOrNull3 != null ? intOrNull3.intValue() : 0;
                    Object obj3 = "";
                    String str = (String) (6 < p.size() ? p.get(6) : "");
                    Integer intOrNull4 = StringsKt.toIntOrNull((String) p.get(3));
                    int intValue4 = intOrNull4 != null ? intOrNull4.intValue() : 0;
                    String str2 = (String) (7 < p.size() ? p.get(7) : "");
                    if (8 < p.size()) {
                        obj2 = p.get(8);
                    }
                    Integer intOrNull5 = StringsKt.toIntOrNull((String) obj2);
                    int intValue5 = intOrNull5 != null ? intOrNull5.intValue() : 0;
                    if (9 < p.size()) {
                        obj3 = p.get(9);
                    }
                    hashMap.put(obj, new LaneFixDialog.Result(intValue, intValue2, intValue3, str, intValue4, false, str2, intValue5, (String) obj3).text());
                } else if (p.size() >= 2) {
                    r.remove(p.get(0));
                }
            }
            return r;
        } catch (Exception e2) {
            return new HashMap();
        }
    }

    /* recovered by hand from the bytecode (jadx lost a branch here, Vineflower could not decompile it) */
    public final String export(Function1<? super String, Unit> progress) {
        String countsName = "lane_counts.json";
        String logName = "lane_log.txt";
        String nl = "\n";
        Intrinsics.checkNotNullParameter(progress, "progress");
        List rows;
        try {
            List<String> lines = FilesKt.readLines(this.index, kotlin.text.Charsets.UTF_8);
            ArrayList split = new ArrayList(CollectionsKt.collectionSizeOrDefault(lines, 10));
            for (String it : lines) {
                split.add(StringsKt.split((CharSequence) it, new String[]{"\t"}, false, 0));
            }
            ArrayList keep = new ArrayList();
            for (Object it : split) {
                if (((List) it).size() >= 5) {
                    keep.add(it);
                }
            }
            rows = keep;
        } catch (Exception e) {
            rows = CollectionsKt.emptyList();
        }
        if (rows.isEmpty()) {
            return "Nothing to export yet.";
        }
        File done = new File(this.ctx.getFilesDir(), "lane_exported.txt");
        HashSet already;
        try {
            already = CollectionsKt.toHashSet(FilesKt.readLines(done, kotlin.text.Charsets.UTF_8));
        } catch (Exception e) {
            already = new HashSet();
        }
        HashMap rev = new HashMap();
        try {
            for (String ln : FilesKt.readLines(this.reviews, kotlin.text.Charsets.UTF_8)) {
                List p = StringsKt.split((CharSequence) ln, new String[]{"\t"}, false, 0);
                if (p.size() >= 2) {
                    rev.put(p.get(0), p);
                }
            }
        } catch (Exception e) {
        }
        StringBuilder csv = new StringBuilder("picture,time_ms,drive,why,street_id,street,card_lane,card_total,card_conf,your_mark,fix_road_lanes,fix_lane,fix_extra_left,fix_extra_right,fix_kind,fix_way,fix_other_way_lanes,road_file,wide_file,caption,skip_why,fix_kind_right,map_ramps,model_guess\n");
        for (Object o : rows) {
            List p = (List) o;
            List ans = StringsKt.split((CharSequence) (8 < p.size() ? (String) p.get(8) : "0,0,0"), new String[]{","}, false, 0);
            List r0 = (List) rev.get(p.get(0));
            // a skipped picture ("-1") keeps its reason; a reviewed one only its first two fields
            List r = (r0 != null && Intrinsics.areEqual(CollectionsKt.getOrNull(r0, 1), "-1")) ? r0 : (r0 != null ? CollectionsKt.take(r0, 2) : null);
            String[] f = new String[24];
            f[0] = export$q((String) p.get(0));
            f[1] = (String) p.get(2);
            f[2] = export$q(5 < p.size() ? (String) p.get(5) : "");
            f[3] = (String) p.get(3);
            f[4] = 6 < p.size() ? (String) p.get(6) : "0";
            f[5] = export$q(7 < p.size() ? (String) p.get(7) : "");
            f[6] = ans.size() > 0 ? (String) ans.get(0) : "0";
            f[7] = 1 < ans.size() ? (String) ans.get(1) : "0";
            f[8] = 2 < ans.size() ? (String) ans.get(2) : "0";
            for (int k = 1; k <= 8; k++) {
                String v = r != null ? (String) CollectionsKt.getOrNull(r, k) : null;
                f[8 + k] = v != null ? v : "";
            }
            f[17] = export$q(9 < p.size() ? (String) p.get(9) : "");
            f[18] = export$q(10 < p.size() ? (String) p.get(10) : "");
            f[19] = export$q((String) p.get(4));
            String skipWhy = null;
            if (Intrinsics.areEqual(r0 != null ? (String) CollectionsKt.getOrNull(r0, 1) : null, "0")) {
                skipWhy = (String) CollectionsKt.getOrNull(r0, 2);
            }
            f[20] = skipWhy != null ? skipWhy : "";
            String kindR = r != null ? (String) CollectionsKt.getOrNull(r, 9) : null;
            f[21] = kindR != null ? kindR : "";
            f[22] = export$q(11 < p.size() ? (String) p.get(11) : "");
            f[23] = export$q(12 < p.size() ? (String) p.get(12) : "");
            csv.append(CollectionsKt.joinToString(CollectionsKt.listOf(f), ",", "", "", -1, "...", null)).append('\n');
        }
        ArrayList fresh = new ArrayList();
        for (Object o : rows) {
            if (!already.contains(((List) o).get(0))) {
                fresh.add(o);
            }
        }
        ArrayList files = new ArrayList();
        for (Object o : fresh) {
            List p = (List) o;
            List<String> two = CollectionsKt.listOf(new String[]{9 < p.size() ? (String) p.get(9) : "", 10 < p.size() ? (String) p.get(10) : ""});
            ArrayList nonEmpty = new ArrayList();
            for (String s : two) {
                if (s.length() > 0) {
                    nonEmpty.add(s);
                }
            }
            ArrayList pairs = new ArrayList(CollectionsKt.collectionSizeOrDefault(nonEmpty, 10));
            for (Object s : nonEmpty) {
                pairs.add(TuplesKt.to(p.get(0), (String) s));
            }
            CollectionsKt.addAll(files, pairs);
        }
        String stamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm", Locale.US).format(new Date());
        int part = 0;
        int frames = 0;
        int i = 0;
        long LIMIT = 25165824L;
        ArrayList newDone = new ArrayList();
        do {
            part++;
            String name = "lane_data_" + stamp + "_part" + part + ".zip";
            ContentValues cv = new ContentValues();
            cv.put("_display_name", name);
            cv.put("mime_type", "application/zip");
            cv.put("relative_path", "Download/StarView/Lane data");
            Uri uri = this.ctx.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv);
            if (uri == null) {
                return "Couldn't create " + name;
            }
            Ref.LongRef size = new Ref.LongRef();
            OutputStream os = this.ctx.getContentResolver().openOutputStream(uri);
            if (os != null) {
                Throwable osCause = null;
                try {
                    ZipOutputStream z = new ZipOutputStream(os);
                    Throwable zCause = null;
                    try {
                        if (part == 1) {
                            String text = csv.toString();
                            Intrinsics.checkNotNullExpressionValue(text, "toString(...)");
                            byte[] bytes = text.getBytes(Charsets.UTF_8);
                            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                            export$lambda$72$lambda$71$put(z, size, "labels.csv", bytes);
                            try {
                                export$lambda$72$lambda$71$put(z, size, logName, FilesKt.readBytes(new File(this.ctx.getFilesDir(), logName)));
                            } catch (Exception e) {
                            }
                            try {
                                export$lambda$72$lambda$71$put(z, size, countsName, FilesKt.readBytes(new File(this.ctx.getFilesDir(), countsName)));
                            } catch (Exception e) {
                            }
                        }
                        while (i < files.size() && size.element < LIMIT) {
                            Pair pr = (Pair) files.get(i);
                            String pic = (String) pr.component1();
                            String rel = (String) pr.component2();
                            i++;
                            try {
                                export$lambda$72$lambda$71$put(z, size, "frames/" + rel, FilesKt.readBytes(new File(getCleanDir(), rel)));
                                frames++;
                            } catch (Exception e) {
                            }
                            if (!Intrinsics.areEqual(CollectionsKt.lastOrNull((List) newDone), pic)) {
                                newDone.add(pic);
                            }
                            if (frames % 50 == 0) {
                                progress.invoke("part " + part + ": " + frames + " frames…");
                            }
                        }
                    } catch (Throwable th) {
                        zCause = th;
                        throw th;
                    } finally {
                        CloseableKt.closeFinally(z, zCause);
                    }
                } catch (Throwable th) {
                    osCause = th;
                    throw th;
                } finally {
                    CloseableKt.closeFinally(os, osCause);
                }
            }
        } while (i < files.size());
        try {
            FilesKt.appendText(done, CollectionsKt.joinToString(CollectionsKt.distinct(newDone), nl, "", "", -1, "...", null) + (newDone.isEmpty() ? "" : nl), kotlin.text.Charsets.UTF_8);
        } catch (Exception e) {
        }
        this.ctx.getSharedPreferences("lane_pictures", 0).edit().putLong("exported_at", System.currentTimeMillis()).apply();
        return "Exported " + rows.size() + " labels and " + frames + " new frames in " + part + " file" + (part > 1 ? "s" : "") + " to Download/StarView/Lane data.";
    }

    private static final String export$q(String v) {
        return "\"" + StringsKt.replace(v, "\"", "'", false) + "\"";
    }

    private static final void export$lambda$72$lambda$71$put(ZipOutputStream $z, Ref.LongRef size, String entry, byte[] bytes) {
        $z.putNextEntry(new ZipEntry(entry));
        $z.write(bytes);
        $z.closeEntry();
        size.element += bytes.length;
    }

    /* compiled from: LaneShots.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
        public final String caption(LaneBrain var1, String var2, float var3) {
            Intrinsics.checkNotNullParameter(var1, "tr");
            Intrinsics.checkNotNullParameter(var2, "why");
            Pair var7 = var1.laneFromLeft();
            int var6 = ((Number)var7.component1()).intValue();
            double var4 = ((Number)var7.component2()).doubleValue();
            String var17;
            if (var6 > 0) {
                String var16 = var1.label();
                var6 = (int)(100 * var4);
                var17 = var16 + " (" + var6 + "%)";
            } else {
                var17 = "Lane ?";
            }

            String var18;
            label29: {
                LaneBrain.Road var9 = var1.getRoad();
                if (var9 != null) {
                    var18 = var9.getName();
                    boolean var14;
                    if (var18.length() == 0) {
                        var14 = true;
                    } else {
                        var14 = false;
                    }

                    if (var14) {
                        var18 = var9.getKind();
                    }

                    String var19 = var18;
                    var18 = var19;
                    if (var19 != null) {
                        break label29;
                    }
                }

                var18 = "no street";
            }

            String var20 = new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
            var6 = (int)var3;
            var2 = StringsKt.take(var2, 110);
            String var11;
            if (var1.modelFresh()) {
                String var10 = var1.getModelText();
                var11 = "   ·   " + var10;
            } else {
                var11 = "";
            }

            return var20 + "   " + var17 + "   ·   " + var18 + "   ·   " + var6 + " mph\n" + var2 + var11;
        }
    }
}
