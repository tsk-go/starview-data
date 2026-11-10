package com.starpilot.starview;

import android.content.Context;
import com.starpilot.starview.JournalLogic;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: DriveJournal.kt */
public final class DriveJournal {
    private static final int KEEP = 3000;
    private static File file;
    private static long lastFeed;
    public static final DriveJournal INSTANCE = new DriveJournal();
    private static final JournalLogic logic = new JournalLogic();

    private DriveJournal() {
    }

    public final void init(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (file == null) {
            file = new File(ctx.getApplicationContext().getFilesDir(), "journal.jsonl");
        }
    }

    public final void onState(Telemetry t) {
        Object minElem$iv;
        Intrinsics.checkNotNullParameter(t, "t");
        long now = System.currentTimeMillis();
        if (now - lastFeed < 100) {
            return;
        }
        lastFeed = now;
        Iterable values = t.getGpsFixes().values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        Iterable $this$filter$iv = values;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GpsFixInfo it = (GpsFixInfo) element$iv$iv;
            if (it.getHasFix()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$minByOrNull$iv = (List) destination$iv$iv;
        Iterator iterator$iv = $this$minByOrNull$iv.iterator();
        if (iterator$iv.hasNext()) {
            minElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                GpsFixInfo it2 = (GpsFixInfo) minElem$iv;
                float minValue$iv = it2.getHAcc();
                do {
                    Object e$iv = iterator$iv.next();
                    GpsFixInfo it3 = (GpsFixInfo) e$iv;
                    float v$iv = it3.getHAcc();
                    if (Float.compare(minValue$iv, v$iv) > 0) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        } else {
            minElem$iv = null;
        }
        GpsFixInfo fix = (GpsFixInfo) minElem$iv;
        synchronized (logic) {
            logic.feed(new JournalLogic.Sample(now, t.getStarted(), t.getEnabled(), t.getVEgo(), t.getAEgo(), t.getAlertText1(), t.getAlertText2(), t.getAlertStatus(), t.getBrakePressed(), t.getGasPressed(), t.getSteeringPressed(), fix != null ? fix.getLat() : Double.NaN, fix != null ? fix.getLon() : Double.NaN, t.getRoadName()));
            if (logic.getOut().isEmpty()) {
                return;
            }
            final String lines = CollectionsKt.joinToString(logic.getOut(), "", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.DriveJournal$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CharSequence onState$lambda$6$lambda$2;
                    onState$lambda$6$lambda$2 = DriveJournal.onState$lambda$6$lambda$2((JSONObject) obj);
                    return onState$lambda$6$lambda$2;
                }
            });
            logic.getOut().clear();
            final File f = file;
            if (f == null) {
                return;
            }
            new Thread(new Runnable() { // from class: com.starpilot.starview.DriveJournal$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DriveJournal.onState$lambda$6$lambda$5(f, lines);
                }
            }).start();
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence onState$lambda$6$lambda$2(JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it + "\n";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onState$lambda$6$lambda$5(File $f, String $lines) {
        synchronized (INSTANCE) {
            DriveJournal $this$onState_u24lambda_u246_u24lambda_u245_u24lambda_u244_u24lambda_u243 = INSTANCE;
            try {
                Result.Companion companion = Result.Companion;
                FilesKt.appendText($f, $lines, kotlin.text.Charsets.UTF_8);
                $this$onState_u24lambda_u246_u24lambda_u245_u24lambda_u244_u24lambda_u243.trim($f);
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void trim(File f) {
        if (f.length() < 1500000) {
            return;
        }
        List keep = CollectionsKt.takeLast(FilesKt.readLines(f, kotlin.text.Charsets.UTF_8), 3000);
        FilesKt.writeText(f, CollectionsKt.joinToString(keep, "\n", "", "\n", -1, "...", null), kotlin.text.Charsets.UTF_8);
    }

    public static /* synthetic */ List read$default(DriveJournal driveJournal, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return driveJournal.read(str);
    }

    public final List<JSONObject> read(String kind) {
        Object m1191constructorimpl;
        Iterable iterable;
        Object m1191constructorimpl2;
        File f = file;
        if (f == null) {
            return CollectionsKt.emptyList();
        }
        synchronized (this) {
            DriveJournal driveJournal = INSTANCE;
            try {
                Result.Companion companion = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(FilesKt.readLines(f, kotlin.text.Charsets.UTF_8));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            List emptyList = CollectionsKt.emptyList();
            if (KtCompat.isFailure(m1191constructorimpl)) {
                m1191constructorimpl = emptyList;
            }
            iterable = (List) m1191constructorimpl;
        }
        Iterable $this$mapNotNull$iv = iterable;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            String it = (String) element$iv$iv$iv;
            DriveJournal driveJournal2 = INSTANCE;
            try {
                Result.Companion companion3 = Result.Companion;
                m1191constructorimpl2 = KtCompat.resultOf(new JSONObject(it));
            } catch (Throwable th2) {
                Result.Companion companion4 = Result.Companion;
                m1191constructorimpl2 = KtCompat.resultOf(ResultKt.createFailure(th2));
            }
            if (KtCompat.isFailure(m1191constructorimpl2)) {
                m1191constructorimpl2 = null;
            }
            JSONObject jSONObject = (JSONObject) m1191constructorimpl2;
            if (jSONObject != null) {
                destination$iv$iv.add(jSONObject);
            }
        }
        Iterable $this$filter$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            JSONObject it2 = (JSONObject) element$iv$iv;
            if (kind == null || Intrinsics.areEqual(it2.optString("k"), kind)) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv2;
    }

    public final void clear() {
        synchronized (this) {
            File file2 = file;
            if (file2 != null) {
                Boolean.valueOf(file2.delete());
            }
        }
    }

    private final List<JSONObject> objects(JSONArray $this$objects) {
        Iterable $this$map$iv = RangesKt.until(0, $this$objects.length());
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it = $this$map$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            destination$iv$iv.add($this$objects.getJSONObject(item$iv$iv));
        }
        return (List) destination$iv$iv;
    }
}
