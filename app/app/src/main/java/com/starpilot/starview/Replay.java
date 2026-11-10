package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Replay.kt */
public final class Replay {
    public static final Replay INSTANCE = new Replay();
    private static volatile long gotAtNs;
    private static volatile JSONObject status;

    private Replay() {
    }

    public final JSONObject getStatus() {
        return status;
    }

    public final boolean getSupported() {
        return status != null;
    }

    public final boolean getActive() {
        JSONObject jSONObject = status;
        return jSONObject != null && jSONObject.optBoolean("active", false);
    }

    public final void update(JSONObject r) {
        status = r;
        gotAtNs = System.nanoTime();
    }

    public static /* synthetic */ double position$default(Replay replay, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = System.nanoTime();
        }
        return replay.position(j);
    }

    public final double position(long nowNs) {
        JSONObject s = status;
        if (s == null) {
            return 0.0d;
        }
        double t = s.optDouble("t", 0.0d);
        if (!getActive() || s.optBoolean("paused") || s.optBoolean("loading")) {
            return t;
        }
        double ahead = RangesKt.coerceIn((nowNs - gotAtNs) / 1.0E9d, 0.0d, 1.5d) * s.optDouble("speed", 1.0d);
        return RangesKt.coerceAtMost(t + ahead, s.optDouble("dur", t + ahead));
    }

    public final String clock(double sec) {
        int s = RangesKt.coerceAtLeast((int) sec, 0);
        String format = s >= 3600 ? String.format("%d:%02d:%02d", Arrays.copyOf(new Object[]{Integer.valueOf(s / 3600), Integer.valueOf((s / 60) % 60), Integer.valueOf(s % 60)}, 3)) : String.format("%d:%02d", Arrays.copyOf(new Object[]{Integer.valueOf(s / 60), Integer.valueOf(s % 60)}, 2));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void control$default(Replay replay, String str, double d, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            d = 0.0d;
        }
        if ((i & 4) != 0) {
            function1 = null;
        }
        replay.control(str, d, function1);
    }

    public final void control(String op, double value, Function1<? super JSONObject, Unit> cb) {
        Intrinsics.checkNotNullParameter(op, "op");
        DataClient data = Link.INSTANCE.getData();
        if (data != null) {
            data.request("replay_control", MapsKt.mapOf(TuplesKt.to("op", op), TuplesKt.to("value", Double.valueOf(value))), cb);
        }
    }

    public final String label(JSONObject d) {
        Intrinsics.checkNotNullParameter(d, "d");
        long start = d.optLong("start", 0L);
        String day = start > 0 ? new SimpleDateFormat("EEE MMM d · h:mm a", Locale.US).format(new Date(1000 * start)) : d.optString("route");
        int mins = d.optInt("minutes", d.optInt("segments", 0));
        String[] strArr = new String[2];
        strArr[0] = d.optBoolean("video") ? "video" : "no video";
        strArr[1] = !d.optBoolean("full", true) ? "light log" : null;
        List extra = CollectionsKt.listOfNotNull((Object[]) strArr);
        return day + " · " + mins + " min · " + CollectionsKt.joinToString$default(extra, " · ", null, null, 0, null, null, 62, null);
    }

    public final void pick(final Activity act) {
        Intrinsics.checkNotNullParameter(act, "act");
        final DataClient dc = Link.INSTANCE.getData();
        if (dc == null || !dc.getConnected()) {
            new AlertDialog.Builder(act).setMessage("Not connected to the comma.").setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
        } else {
            DataClient.request$default(dc, "replay_list", null, new Function1() { // from class: com.starpilot.starview.Replay$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit pick$lambda$4;
                    pick$lambda$4 = Replay.pick$lambda$4(act, dc, (JSONObject) obj);
                    return pick$lambda$4;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit pick$lambda$4(final Activity $act, final DataClient $dc, JSONObject r) {
        Intrinsics.checkNotNullParameter(r, "r");
        if ($act.isFinishing()) {
            return Unit.INSTANCE;
        }
        if (!r.optBoolean("ok")) {
            new AlertDialog.Builder($act).setMessage("The comma can't replay drives: " + r.optString("error", "update its StarView patch")).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
            return Unit.INSTANCE;
        }
        JSONArray arr = r.optJSONArray("drives");
        Iterable $this$mapNotNull$iv = RangesKt.until(0, arr != null ? arr.length() : 0);
        Collection destination$iv$iv = new ArrayList();
        Iterator<Integer> it = $this$mapNotNull$iv.iterator();
        while (it.hasNext()) {
            int element$iv$iv$iv = ((IntIterator) it).nextInt();
            JSONObject optJSONObject = arr != null ? arr.optJSONObject(element$iv$iv$iv) : null;
            if (optJSONObject != null) {
                destination$iv$iv.add(optJSONObject);
            }
        }
        final List drives = (List) destination$iv$iv;
        if (drives.isEmpty()) {
            new AlertDialog.Builder($act).setMessage("No recorded drives on the comma.").setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
            return Unit.INSTANCE;
        }
        AlertDialog.Builder title = new AlertDialog.Builder($act).setTitle("Replay a drive");
        List $this$map$iv = drives;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            JSONObject it2 = (JSONObject) item$iv$iv;
            destination$iv$iv2.add(INSTANCE.label(it2));
        }
        Collection thisCollection$iv = (List) destination$iv$iv2;
        title.setItems((CharSequence[]) thisCollection$iv.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.Replay$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                Replay.pick$lambda$4$lambda$3(DataClient.this, drives, $act, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pick$lambda$4$lambda$3(DataClient $dc, final List $drives, final Activity $act, DialogInterface dialogInterface, final int which) {
        $dc.request("replay_start", MapsKt.mapOf(TuplesKt.to("route", ((JSONObject) $drives.get(which)).optString("route")), TuplesKt.to("at", 0)), new Function1() { // from class: com.starpilot.starview.Replay$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit pick$lambda$4$lambda$3$lambda$2;
                pick$lambda$4$lambda$3$lambda$2 = Replay.pick$lambda$4$lambda$3$lambda$2($drives, which, $act, (JSONObject) obj);
                return pick$lambda$4$lambda$3$lambda$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit pick$lambda$4$lambda$3$lambda$2(List $drives, int $which, Activity $act, JSONObject a) {
        Intrinsics.checkNotNullParameter(a, "a");
        if (a.optBoolean("ok")) {
            BlinkLog.INSTANCE.add("Drive replay started: " + INSTANCE.label((JSONObject) $drives.get($which)));
            $act.finish();
        } else {
            new AlertDialog.Builder($act).setMessage("Couldn't start it: " + a.optString("error")).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
        }
        return Unit.INSTANCE;
    }
}
