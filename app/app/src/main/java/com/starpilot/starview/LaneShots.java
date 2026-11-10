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

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

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
        return this.scene;
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
        return this.ramps;
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
                        request$lambda$4 = LaneShots.request$lambda$4(lock, wideDone, wide2, cam2, sceneDone, clean, this, side, m, n, caption, why, now, rd, ans, rp, ms, copyTo, (Bitmap) obj);
                        return request$lambda$4;
                    }
                });
                final LaneBrain.Road road = rd;
                final List list = rp;
                final String str3 = ms;
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.starpilot.starview.LaneShots$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaneShots.request$lambda$6(lock, wideDone, cam, sceneDone, clean, wide, this, side, m, n, caption, why, now, road, ans, list, str3, copyTo);
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
                            LaneShots.request$lambda$8(lock, bitmapArr, b, booleanRef, bitmapArr2, booleanRef2, bitmapArr3, bitmapArr4, this, m, n, caption, why, j, road2, ans, list2, str4, copyTo, i);
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
                    request$lambda$10 = LaneShots.request$lambda$10(lock, bitmapArr5, bitmapArr6, detector, booleanRef3, booleanRef4, bitmapArr7, this, bitmapArr8, m, n, caption, why, j2, road3, ans, list3, str5, copyTo, (Bitmap) obj);
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
                    try {
                        FileOutputStream it = fileOutputStream;
                        out.compress(Bitmap.CompressFormat.JPEG, 82, it);
                        CloseableKt.closeFinally(fileOutputStream, null);
                    } finally {
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
                    fileOutputStream = openOutputStream;
                    try {
                        OutputStream it2 = fileOutputStream;
                        Boolean.valueOf(out.compress(Bitmap.CompressFormat.JPEG, 72, it2));
                        CloseableKt.closeFinally(fileOutputStream, null);
                    } finally {
                        try {
                            throw th;
                        } finally {
                        }
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00ee A[LOOP:0: B:12:0x009a->B:20:0x00ee, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00f1 A[EDGE_INSN: B:21:0x00f1->B:30:0x00f1 BREAK  A[LOOP:0: B:12:0x009a->B:20:0x00ee], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sketch(Canvas c, float x0, float w, float h, int m, int n, List<LaneRamps.Ramp> rp) {
        float f;
        int i;
        Paint p = new Paint(1);
        p.setColor(Color.rgb(28, 32, 40));
        c.drawRect(x0, 0.0f, x0 + w, h, p);
        int k = m > 0 ? m : n > 0 ? n + 1 : 0;
        Paint $this$sketch_u24lambda_u2422 = new Paint(1);
        $this$sketch_u24lambda_u2422.setColor(Color.rgb(200, 208, 222));
        float f2 = 20.0f;
        $this$sketch_u24lambda_u2422.setTextSize(20.0f);
        $this$sketch_u24lambda_u2422.setTextAlign(Paint.Align.CENTER);
        if (k <= 0) {
            float f3 = 2;
            c.drawText("lane unknown", x0 + (w / f3), h / f3, $this$sketch_u24lambda_u2422);
            return;
        }
        float lw = Math.min(64.0f, (w - 40.0f) / k);
        float f4 = 2;
        float left = x0 + ((w - (k * lw)) / f4);
        p.setColor(Color.rgb(120, 126, 136));
        c.drawRect(left, 30.0f, left + (k * lw), h - 20.0f, p);
        if (0 <= k) {
            int i2 = 0;
            while (true) {
                p.setColor(i2 == 0 ? Color.rgb(242, 194, 48) : -1);
                float x = left + (i2 * lw);
                if (i2 == 0) {
                    i = i2;
                } else if (i2 != k) {
                    float y = 34.0f;
                    while (y < h - 24.0f) {
                        c.drawRect(x - 1.5f, y, x + 1.5f, y + 18.0f, p);
                        y += 36.0f;
                        i2 = i2;
                    }
                    i = i2;
                    if (i != k) {
                        break;
                    } else {
                        i2 = i + 1;
                    }
                } else {
                    i = i2;
                }
                c.drawRect(x - 2.0f, 30.0f, x + 2.0f, h - 20.0f, p);
                if (i != k) {
                }
            }
        }
        if (1 <= n && n <= k) {
            p.setColor(Color.rgb(55, 138, 221));
            float cx = left + ((n - 0.5f) * lw);
            f = f4;
            c.drawRoundRect(cx - (lw * 0.28f), h - 120.0f, cx + (0.28f * lw), h - 50.0f, 8.0f, 8.0f, p);
        } else {
            f = f4;
        }
        for (LaneRamps.Ramp r : rp) {
            float ex = r.getSide() < 0 ? left - lw : left + (k * lw);
            if (ex < x0) {
                f2 = 20.0f;
            } else if (ex + lw <= x0 + w) {
                p.setColor(Color.rgb(96, 72, 26));
                c.drawRect(ex, 30.0f, ex + lw, h - f2, p);
                p.setColor(Color.rgb(240, 181, 74));
                c.drawRect(r.getSide() < 0 ? ex - 2.0f : (ex + lw) - 2.0f, 30.0f, r.getSide() < 0 ? ex + 2.0f : ex + lw + 2.0f, h - f2, p);
                Paint tt = new Paint(1);
                tt.setColor(Color.rgb(240, 181, 74));
                tt.setTextSize(15.0f);
                tt.setTextAlign(Paint.Align.CENTER);
                tt.setTypeface(Typeface.DEFAULT_BOLD);
                c.drawText(Intrinsics.areEqual(r.getKind(), "exit") ? "EXIT" : "MERGE", ex + (lw / f), h / f, tt);
                f2 = 20.0f;
            }
        }
        c.drawText((m > 0 ? new StringBuilder().append("card: lane ").append(n > 0 ? String.valueOf(n) : "?").append(" of ").append(m) : new StringBuilder().append("card: lane ").append(n).append(" of ?")).toString(), x0 + (w / f), 22.0f, $this$sketch_u24lambda_u2422);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:78:0x015c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int deleteDrive(String drive) {
        String str;
        boolean z;
        Intrinsics.checkNotNullParameter(drive, "drive");
        char c = 0;
        try {
            int i = 1;
            List readLines$default = FilesKt.readLines(this.index, kotlin.text.Charsets.UTF_8);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Iterator it = readLines$default.iterator();
            while (true) {
                str = "";
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                String[] strArr = new String[1];
                strArr[c] = "\t";
                List split$default = StringsKt.split((CharSequence) next, strArr, false, 0);
                String str2 = (CharSequence) (5 < split$default.size() ? split$default.get(5) : str);
                if (str2.length() == 0) {
                    str2 = "earlier";
                }
                if (Intrinsics.areEqual(str2, drive)) {
                    arrayList.add(next);
                    c = 0;
                } else {
                    arrayList2.add(next);
                    c = 0;
                }
            }
            Pair pair = new Pair(arrayList, arrayList2);
            List<String> list = (List) pair.component1();
            List list2 = (List) pair.component2();
            for (String str3 : list) {
                String[] strArr2 = new String[i];
                strArr2[0] = "\t";
                List split$default2 = StringsKt.split((CharSequence) str3, strArr2, false, 0);
                try {
                    this.ctx.getContentResolver().delete(Uri.parse((String) split$default2.get(i)), null, null);
                } catch (Exception e) {
                }
                String[] strArr3 = new String[2];
                strArr3[0] = 9 < split$default2.size() ? split$default2.get(9) : "";
                strArr3[1] = 10 < split$default2.size() ? split$default2.get(10) : "";
                for (String str4 : CollectionsKt.listOf((Object[]) strArr3)) {
                    if (str4.length() > 0) {
                        new File(getCleanDir(), str4).delete();
                    }
                }
                i = 1;
            }
            File file = new File(getCleanDir(), drive);
            if (file.isDirectory()) {
                String[] list3 = file.list();
                if (list3 != null) {
                    if (!(list3.length == 0)) {
                        z = true;
                        if (!z) {
                            file.delete();
                        }
                    }
                }
                z = false;
                if (!z) {
                }
            }
            File file2 = this.index;
            String str5 = str;
            if (!list2.isEmpty()) {
                str5 = CollectionsKt.joinToString(list2, "\n", "", "", -1, "...", null) + "\n";
            }
            FilesKt.writeText(file2, str5, kotlin.text.Charsets.UTF_8);
            List list4 = list;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
            Iterator it2 = list4.iterator();
            while (it2.hasNext()) {
                arrayList3.add(StringsKt.substringBefore((String) it2.next(), '\t', ((String) it2.next())));
            }
            HashSet hashSet = CollectionsKt.toHashSet(arrayList3);
            try {
                File file3 = this.reviews;
                List readLines$default2 = FilesKt.readLines(this.reviews, kotlin.text.Charsets.UTF_8);
                ArrayList arrayList4 = new ArrayList();
                for (Object obj : readLines$default2) {
                    List list5 = readLines$default2;
                    List list6 = list2;
                    try {
                        if (!hashSet.contains(StringsKt.substringBefore((String) obj, '\t', ((String) obj)))) {
                            arrayList4.add(obj);
                        }
                        list2 = list6;
                        readLines$default2 = list5;
                    } catch (Exception e2) {
                    }
                }
                String joinToString$default = CollectionsKt.joinToString(arrayList4, "\n", "", "", -1, "...", null);
                if (!(joinToString$default.length() == 0)) {
                    joinToString$default = joinToString$default + "\n";
                }
                FilesKt.writeText(file3, joinToString$default, kotlin.text.Charsets.UTF_8);
            } catch (Exception e3) {
            }
            return list.size();
        } catch (Exception e4) {
            return 0;
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

    /* JADX WARN: Code restructure failed: missing block: B:106:0x02c9, code lost:
    
        if (r21 == null) goto L130;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0334  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0339  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x03c0  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x039c  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0368  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x033e  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0315  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x026b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String export(Function1<? super String, Unit> progress) {
        List emptyList;
        HashSet hashSet;
        String str;
        String str2;
        StringBuilder csv;
        long LIMIT;
        int sent;
        Throwable th;
        Throwable th2;
        String sb;
        ZipOutputStream z;
        int i;
        long LIMIT2;
        HashMap rev;
        List r;
        String str3;
        Iterable $this$forEach$iv;
        String str4 = "lane_counts.json";
        String str5 = "lane_log.txt";
        String str6 = "\n";
        Intrinsics.checkNotNullParameter(progress, "progress");
        int i2 = 1;
        try {
            Iterable $this$map$iv = FilesKt.readLines(this.index, kotlin.text.Charsets.UTF_8);
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                String it = (String) item$iv$iv;
                destination$iv$iv.add(StringsKt.split((CharSequence) it, new String[]{"\t"}, false, 0));
            }
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv : (List) destination$iv$iv) {
                List it2 = (List) element$iv$iv;
                if (it2.size() >= 5) {
                    destination$iv$iv2.add(element$iv$iv);
                }
            }
            emptyList = (List) destination$iv$iv2;
        } catch (Exception e) {
            emptyList = CollectionsKt.emptyList();
        }
        List rows = emptyList;
        if (rows.isEmpty()) {
            return "Nothing to export yet.";
        }
        File done = new File(this.ctx.getFilesDir(), "lane_exported.txt");
        try {
            hashSet = CollectionsKt.toHashSet(FilesKt.readLines(done, kotlin.text.Charsets.UTF_8));
        } catch (Exception e2) {
            hashSet = new HashSet();
        }
        HashSet already = hashSet;
        HashMap rev2 = new HashMap();
        int i3 = 2;
        try {
            Iterable $this$forEach$iv2 = FilesKt.readLines(this.reviews, kotlin.text.Charsets.UTF_8);
            for (Object element$iv : $this$forEach$iv2) {
                String ln = (String) element$iv;
                String[] strArr = new String[i2];
                strArr[0] = "\t";
                List p = StringsKt.split((CharSequence) ln, strArr, false, 0);
                if (p.size() >= i3) {
                    $this$forEach$iv = $this$forEach$iv2;
                    rev2.put(p.get(0), p);
                } else {
                    $this$forEach$iv = $this$forEach$iv2;
                }
                $this$forEach$iv2 = $this$forEach$iv;
                i2 = 1;
                i3 = 2;
            }
        } catch (Exception e3) {
        }
        StringBuilder csv2 = new StringBuilder("picture,time_ms,drive,why,street_id,street,card_lane,card_total,card_conf,your_mark,fix_road_lanes,fix_lane,fix_extra_left,fix_extra_right,fix_kind,fix_way,fix_other_way_lanes,road_file,wide_file,caption,skip_why,fix_kind_right,map_ramps,model_guess\n");
        Iterator it3 = rows.iterator();
        while (it3.hasNext()) {
            List p2 = (List) it3.next();
            List ans = StringsKt.split((CharSequence) (8 < p2.size() ? p2.get(8) : "0,0,0"), new String[]{","}, false, 0);
            Iterator it4 = it3;
            List r0 = (List) rev2.get(p2.get(0));
            if (r0 != null) {
                rev = rev2;
                if (Intrinsics.areEqual(CollectionsKt.getOrNull(r0, 1), "-1")) {
                    r = r0;
                    String[] strArr2 = new String[24];
                    strArr2[0] = export$q((String) p2.get(0));
                    strArr2[1] = p2.get(2);
                    File done2 = done;
                    strArr2[2] = export$q((String) (!(5 >= p2.size()) ? p2.get(5) : ""));
                    strArr2[3] = p2.get(3);
                    String str7 = str6;
                    strArr2[4] = !(6 >= p2.size()) ? p2.get(6) : "0";
                    strArr2[5] = export$q((String) (!(7 >= p2.size()) ? p2.get(7) : ""));
                    strArr2[6] = !(ans.size() <= 0) ? ans.get(0) : "0";
                    strArr2[7] = !(1 >= ans.size()) ? ans.get(1) : "0";
                    strArr2[8] = !(2 >= ans.size()) ? ans.get(2) : "0";
                    if (r != null || (r13 = (String) CollectionsKt.getOrNull(r, 1)) == null) {
                        String str8 = "";
                    }
                    strArr2[9] = str8;
                    if (r != null || (r13 = (String) CollectionsKt.getOrNull(r, 2)) == null) {
                        String str9 = "";
                    }
                    strArr2[10] = str9;
                    if (r != null || (r5 = (String) CollectionsKt.getOrNull(r, 3)) == null) {
                        String str10 = "";
                    }
                    strArr2[11] = str10;
                    if (r != null || (r28 = (String) CollectionsKt.getOrNull(r, 4)) == null) {
                        String str11 = "";
                    }
                    strArr2[12] = str11;
                    if (r != null) {
                        str3 = (String) CollectionsKt.getOrNull(r, 5);
                    }
                    str3 = "";
                    strArr2[13] = str3;
                    if (r != null || (r5 = (String) CollectionsKt.getOrNull(r, 6)) == null) {
                        String str12 = "";
                    }
                    strArr2[14] = str12;
                    if (r != null || (r5 = (String) CollectionsKt.getOrNull(r, 7)) == null) {
                        String str13 = "";
                    }
                    strArr2[15] = str13;
                    if (r != null || (r5 = (String) CollectionsKt.getOrNull(r, 8)) == null) {
                        String str14 = "";
                    }
                    strArr2[16] = str14;
                    strArr2[17] = export$q((String) (!(9 >= p2.size()) ? p2.get(9) : ""));
                    strArr2[18] = export$q((String) (!(10 >= p2.size()) ? p2.get(10) : ""));
                    strArr2[19] = export$q((String) p2.get(4));
                    if (Intrinsics.areEqual(r0 == null ? (String) CollectionsKt.getOrNull(r0, 1) : null, "0") || (r9 = (String) CollectionsKt.getOrNull(r0, 2)) == null) {
                        String str15 = "";
                    }
                    strArr2[20] = str15;
                    if (r != null || (r5 = (String) CollectionsKt.getOrNull(r, 9)) == null) {
                        String str16 = "";
                    }
                    strArr2[21] = str16;
                    strArr2[22] = export$q((String) (!(11 >= p2.size()) ? p2.get(11) : ""));
                    strArr2[23] = export$q((String) (!(12 >= p2.size()) ? p2.get(12) : ""));
                    csv2.append(CollectionsKt.joinToString(CollectionsKt.listOf((Object[]) strArr2), ",", "", "", -1, "...", null)).append('\n');
                    it3 = it4;
                    rev2 = rev;
                    done = done2;
                    str6 = str7;
                }
            } else {
                rev = rev2;
            }
            r = r0 != null ? CollectionsKt.take(r0, 2) : null;
            String[] strArr22 = new String[24];
            strArr22[0] = export$q((String) p2.get(0));
            strArr22[1] = p2.get(2);
            File done22 = done;
            strArr22[2] = export$q((String) (!(5 >= p2.size()) ? p2.get(5) : ""));
            strArr22[3] = p2.get(3);
            String str72 = str6;
            strArr22[4] = !(6 >= p2.size()) ? p2.get(6) : "0";
            strArr22[5] = export$q((String) (!(7 >= p2.size()) ? p2.get(7) : ""));
            strArr22[6] = !(ans.size() <= 0) ? ans.get(0) : "0";
            strArr22[7] = !(1 >= ans.size()) ? ans.get(1) : "0";
            strArr22[8] = !(2 >= ans.size()) ? ans.get(2) : "0";
            if (r != null) {
            }
            String str82 = "";
            strArr22[9] = str82;
            if (r != null) {
            }
            String str92 = "";
            strArr22[10] = str92;
            if (r != null) {
            }
            String str102 = "";
            strArr22[11] = str102;
            if (r != null) {
            }
            String str112 = "";
            strArr22[12] = str112;
            if (r != null) {
            }
            str3 = "";
            strArr22[13] = str3;
            if (r != null) {
            }
            String str122 = "";
            strArr22[14] = str122;
            if (r != null) {
            }
            String str132 = "";
            strArr22[15] = str132;
            if (r != null) {
            }
            String str142 = "";
            strArr22[16] = str142;
            strArr22[17] = export$q((String) (!(9 >= p2.size()) ? p2.get(9) : ""));
            strArr22[18] = export$q((String) (!(10 >= p2.size()) ? p2.get(10) : ""));
            strArr22[19] = export$q((String) p2.get(4));
            if (Intrinsics.areEqual(r0 == null ? (String) CollectionsKt.getOrNull(r0, 1) : null, "0")) {
            }
            String str152 = "";
            strArr22[20] = str152;
            if (r != null) {
            }
            String str162 = "";
            strArr22[21] = str162;
            strArr22[22] = export$q((String) (!(11 >= p2.size()) ? p2.get(11) : ""));
            strArr22[23] = export$q((String) (!(12 >= p2.size()) ? p2.get(12) : ""));
            csv2.append(CollectionsKt.joinToString(CollectionsKt.listOf((Object[]) strArr22), ",", "", "", -1, "...", null)).append('\n');
            it3 = it4;
            rev2 = rev;
            done = done22;
            str6 = str72;
        }
        String str17 = str6;
        File done3 = done;
        List $this$filter$iv = rows;
        int $i$f$filter = 0;
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv) {
            List it5 = (List) element$iv$iv2;
            Iterable $this$filter$iv2 = $this$filter$iv;
            int $i$f$filter2 = $i$f$filter;
            if (!already.contains(it5.get(0))) {
                destination$iv$iv3.add(element$iv$iv2);
            }
            $i$f$filter = $i$f$filter2;
            $this$filter$iv = $this$filter$iv2;
        }
        Iterable list$iv$iv = (List) destination$iv$iv3;
        int $i$f$flatMap = 0;
        Collection destination$iv$iv4 = new ArrayList();
        Iterable<List> $this$flatMapTo$iv$iv = list$iv$iv;
        for (List p3 : $this$flatMapTo$iv$iv) {
            Iterable $this$flatMap$iv = list$iv$iv;
            int $i$f$flatMap2 = $i$f$flatMap;
            String[] strArr3 = new String[2];
            Iterable $this$flatMapTo$iv$iv2 = $this$flatMapTo$iv$iv;
            strArr3[0] = 9 < p3.size() ? p3.get(9) : "";
            strArr3[1] = 10 < p3.size() ? p3.get(10) : "";
            Iterable $this$filter$iv3 = CollectionsKt.listOf((Object[]) strArr3);
            Collection destination$iv$iv5 = new ArrayList();
            for (Object element$iv$iv3 : $this$filter$iv3) {
                Iterable $this$filter$iv4 = $this$filter$iv3;
                String it6 = (String) element$iv$iv3;
                if (it6.length() > 0) {
                    destination$iv$iv5.add(element$iv$iv3);
                }
                $this$filter$iv3 = $this$filter$iv4;
            }
            Iterable $this$map$iv2 = (List) destination$iv$iv5;
            HashSet already2 = already;
            Collection destination$iv$iv6 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            Iterable $this$mapTo$iv$iv = $this$map$iv2;
            for (Object item$iv$iv2 : $this$mapTo$iv$iv) {
                String it7 = (String) item$iv$iv2;
                destination$iv$iv6.add(TuplesKt.to(p3.get(0), it7));
                $this$mapTo$iv$iv = $this$mapTo$iv$iv;
                $this$map$iv2 = $this$map$iv2;
            }
            CollectionsKt.addAll(destination$iv$iv4, (List) destination$iv$iv6);
            $i$f$flatMap = $i$f$flatMap2;
            list$iv$iv = $this$flatMap$iv;
            $this$flatMapTo$iv$iv = $this$flatMapTo$iv$iv2;
            already = already2;
        }
        List files = (List) destination$iv$iv4;
        String stamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm", Locale.US).format(new Date());
        int part = 0;
        int sent2 = 0;
        int i4 = 0;
        long LIMIT3 = 25165824;
        ArrayList newDone = new ArrayList();
        while (true) {
            int part2 = part + 1;
            String name = "lane_data_" + stamp + "_part" + part2 + ".zip";
            ContentValues $this$export_u24lambda_u2470 = new ContentValues();
            String stamp2 = stamp;
            int sent3 = sent2;
            $this$export_u24lambda_u2470.put("_display_name", name);
            int i5 = i4;
            $this$export_u24lambda_u2470.put("mime_type", "application/zip");
            $this$export_u24lambda_u2470.put("relative_path", "Download/StarView/Lane data");
            Uri uri = this.ctx.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, $this$export_u24lambda_u2470);
            if (uri == null) {
                return "Couldn't create " + name;
            }
            Ref.LongRef size = new Ref.LongRef();
            OutputStream openOutputStream = this.ctx.getContentResolver().openOutputStream(uri);
            if (openOutputStream != null) {
                OutputStream outputStream = openOutputStream;
                try {
                    OutputStream os = outputStream;
                    try {
                        ZipOutputStream zipOutputStream = new ZipOutputStream(os);
                        try {
                            ZipOutputStream z2 = zipOutputStream;
                            if (part2 == 1) {
                                try {
                                    sb = csv2.toString();
                                    csv = csv2;
                                } catch (Throwable th3) {
                                    th2 = th3;
                                }
                                try {
                                    Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
                                    byte[] bytes = sb.getBytes(Charsets.UTF_8);
                                    Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                                    z = z2;
                                    export$lambda$72$lambda$71$put(z, size, "labels.csv", bytes);
                                    try {
                                        export$lambda$72$lambda$71$put(z, size, str5, FilesKt.readBytes(new File(this.ctx.getFilesDir(), str5)));
                                    } catch (Exception e4) {
                                    }
                                    try {
                                        export$lambda$72$lambda$71$put(z, size, str4, FilesKt.readBytes(new File(this.ctx.getFilesDir(), str4)));
                                    } catch (Exception e5) {
                                    }
                                } catch (Throwable th4) {
                                    th2 = th4;
                                    try {
                                        throw th2;
                                    } catch (Throwable th5) {
                                        try {
                                            CloseableKt.closeFinally(zipOutputStream, th2);
                                            throw th5;
                                        } catch (Throwable th6) {
                                            th = th6;
                                            try {
                                                throw th;
                                            } catch (Throwable th7) {
                                                CloseableKt.closeFinally(outputStream, th);
                                                throw th7;
                                            }
                                        }
                                    }
                                }
                            } else {
                                csv = csv2;
                                z = z2;
                            }
                            sent = i5;
                            while (true) {
                                try {
                                    if (sent >= files.size()) {
                                        str = str4;
                                        str2 = str5;
                                        break;
                                    }
                                    str = str4;
                                    str2 = str5;
                                    if (size.element >= LIMIT3) {
                                        break;
                                    }
                                    Pair pair = (Pair) files.get(sent);
                                    String pic = (String) pair.component1();
                                    String rel = (String) pair.component2();
                                    int i6 = sent + 1;
                                    try {
                                        i = i6;
                                        try {
                                            LIMIT2 = LIMIT3;
                                            try {
                                                export$lambda$72$lambda$71$put(z, size, "frames/" + rel, FilesKt.readBytes(new File(getCleanDir(), rel)));
                                                sent3++;
                                            } catch (Exception e6) {
                                            } catch (Throwable th8) {
                                                th2 = th8;
                                                throw th2;
                                            }
                                        } catch (Exception e7) {
                                            LIMIT2 = LIMIT3;
                                        } catch (Throwable th9) {
                                            th2 = th9;
                                        }
                                    } catch (Exception e8) {
                                        i = i6;
                                        LIMIT2 = LIMIT3;
                                    } catch (Throwable th10) {
                                        th2 = th10;
                                    }
                                    int i7 = sent3;
                                    try {
                                        if (!Intrinsics.areEqual(CollectionsKt.lastOrNull((List) newDone), pic)) {
                                            newDone.add(pic);
                                        }
                                        if (i7 % 50 == 0) {
                                            progress.invoke("part " + part2 + ": " + i7 + " frames…");
                                        }
                                        sent3 = i7;
                                        sent = i;
                                        str4 = str;
                                        str5 = str2;
                                        LIMIT3 = LIMIT2;
                                    } catch (Throwable th11) {
                                        th2 = th11;
                                        sent3 = i7;
                                        throw th2;
                                    }
                                } catch (Throwable th12) {
                                    th2 = th12;
                                }
                            }
                            LIMIT = LIMIT3;
                            try {
                                Unit unit = Unit.INSTANCE;
                                try {
                                    CloseableKt.closeFinally(zipOutputStream, null);
                                    Unit unit2 = Unit.INSTANCE;
                                    CloseableKt.closeFinally(outputStream, null);
                                    Unit unit3 = Unit.INSTANCE;
                                    sent2 = sent3;
                                } catch (Throwable th13) {
                                    th = th13;
                                    throw th;
                                }
                            } catch (Throwable th14) {
                                th2 = th14;
                                throw th2;
                            }
                        } catch (Throwable th15) {
                            th2 = th15;
                        }
                    } catch (Throwable th16) {
                        th = th16;
                    }
                } catch (Throwable th17) {
                    th = th17;
                }
            } else {
                str = str4;
                str2 = str5;
                csv = csv2;
                LIMIT = LIMIT3;
                sent = i5;
                sent2 = sent3;
            }
            if (sent >= files.size()) {
                try {
                    try {
                        FilesKt.appendText(done3, CollectionsKt.joinToString(CollectionsKt.distinct(newDone), str17, "", "", -1, "...", null) + (newDone.isEmpty() ? "" : str17), kotlin.text.Charsets.UTF_8);
                    } catch (Exception e9) {
                    }
                } catch (Exception e10) {
                }
                this.ctx.getSharedPreferences("lane_pictures", 0).edit().putLong("exported_at", System.currentTimeMillis()).apply();
                return "Exported " + rows.size() + " labels and " + sent2 + " new frames in " + part2 + " file" + (part2 > 1 ? "s" : "") + " to Download/StarView/Lane data.";
            }
            i4 = sent;
            part = part2;
            stamp = stamp2;
            str4 = str;
            csv2 = csv;
            str5 = str2;
            LIMIT3 = LIMIT;
        }
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

        /* JADX WARN: Code restructure failed: missing block: B:12:0x006c, code lost:
        
            if (r6 == null) goto L16;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final String caption(LaneBrain tr, String why, float mph) {
            String str;
            Intrinsics.checkNotNullParameter(tr, "tr");
            Intrinsics.checkNotNullParameter(why, "why");
            Pair<Integer, Double> laneFromLeft = tr.laneFromLeft();
            int n = laneFromLeft.component1().intValue();
            double q = laneFromLeft.component2().doubleValue();
            String lane = n > 0 ? tr.label() + " (" + ((int) (100 * q)) + "%)" : "Lane ?";
            LaneBrain.Road it = tr.getRoad();
            if (it != null) {
                String name = it.getName();
                if (name.length() == 0) {
                    name = it.getKind();
                }
                str = name;
            }
            str = "no street";
            String road = str;
            return new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()) + "   " + lane + "   ·   " + road + "   ·   " + ((int) mph) + " mph\n" + StringsKt.take(why, 110) + (tr.modelFresh() ? "   ·   " + tr.getModelText() : "");
        }
    }
}
