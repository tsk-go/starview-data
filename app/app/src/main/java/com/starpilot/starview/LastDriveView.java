package com.starpilot.starview;

import android.content.Context;
import android.widget.LinearLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: JournalPages.kt */
public final class LastDriveView extends CardPage {
    /* VF: from Vineflower (jadx: ) */
    public LastDriveView(Context var1, CarUi var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
    }

    @Override // com.starpilot.starview.CardPage
    public void refresh() {
        SimpleDateFormat simpleDateFormat;
        String miles;
        String dur;
        SimpleDateFormat simpleDateFormat2;
        List evs;
        JSONObject d;
        LinearLayout c;
        String str;
        String it;
        getCol().removeAllViews();
        List drives = DriveJournal.INSTANCE.read("drive");
        JSONObject d2 = (JSONObject) CollectionsKt.lastOrNull(drives);
        LinearLayout c2 = card();
        add(c2);
        c2.addView(title("Last drive", "car", "tablet"));
        if (d2 == null) {
            c2.addView(CardPage.line$default(this, "No drive noted yet: the tablet starts noting drives while it's linked.", 0, 0.0f, 6, null));
        } else {
            double secs = (d2.optLong("end") - d2.optLong("t")) / 1000.0d;
            LastDriveView lastDriveView = this;
            simpleDateFormat = JournalPagesKt.dayFmt;
            String format = simpleDateFormat.format(new Date(d2.optLong("t")));
            Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{d2.optString("from"), d2.optString("to")});
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                String it2 = (String) element$iv$iv;
                Intrinsics.checkNotNull(it2);
                if (!StringsKt.isBlank(it2)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            c2.addView(CardPage.line$default(lastDriveView, format + "  ·  " + CollectionsKt.joinToString(CollectionsKt.distinct((List) destination$iv$iv), " → ", "", "", -1, "...", null), 0, 0.0f, 6, null));
            miles = JournalPagesKt.miles(d2.optDouble("meters"));
            dur = JournalPagesKt.dur(secs);
            c2.addView(stats(TuplesKt.to(miles, "distance"), TuplesKt.to(dur, "time"), TuplesKt.to(d2.optInt("engagedPct") + "%", "openpilot on"), TuplesKt.to(String.valueOf(d2.optInt("takeovers")), "takeovers"), TuplesKt.to(String.valueOf(d2.optInt("alerts")), "alerts"), TuplesKt.to(String.valueOf(d2.optInt("hardBrakes")), "hard brakes")));
            Iterable $this$filter$iv2 = DriveJournal.read$default(DriveJournal.INSTANCE, null, 1, null);
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv2 : $this$filter$iv2) {
                List drives2 = drives;
                JSONObject it3 = (JSONObject) element$iv$iv2;
                long optLong = d2.optLong("t");
                long optLong2 = d2.optLong("end");
                long optLong3 = it3.optLong("t");
                if (((optLong > optLong3 ? 1 : (optLong == optLong3 ? 0 : -1)) <= 0 && (optLong3 > optLong2 ? 1 : (optLong3 == optLong2 ? 0 : -1)) <= 0) && !Intrinsics.areEqual(it3.optString("k"), "drive")) {
                    destination$iv$iv2.add(element$iv$iv2);
                }
                drives = drives2;
            }
            List evs2 = (List) destination$iv$iv2;
            if (!evs2.isEmpty()) {
                LinearLayout tl = card();
                add(tl);
                tl.addView(title("What happened", "history", "tablet"));
                for (JSONObject e : (Iterable) (JSONObject) ((JSONObject) CollectionsKt.takeLast(evs2, 40))) {
                    LastDriveView lastDriveView2 = this;
                    simpleDateFormat2 = JournalPagesKt.timeFmt;
                    String format2 = simpleDateFormat2.format(new Date(e.optLong("t")));
                    String str2 = "";
                    if (Intrinsics.areEqual(e.optString("k"), "takeover")) {
                        String optString = e.optString("cause");
                        int optInt = e.optInt("mph");
                        evs = evs2;
                        String optString2 = e.optString("road");
                        if (StringsKt.isBlank(optString2)) {
                            optString2 = null;
                        }
                        String it4 = optString2;
                        if (it4 != null) {
                            d = d2;
                            c = c2;
                            String it5 = " · " + it4;
                            if (it5 != null) {
                                str2 = it5;
                            }
                        } else {
                            d = d2;
                            c = c2;
                        }
                        str = "Took over (" + optString + ") at " + optInt + " mph" + str2;
                    } else {
                        evs = evs2;
                        d = d2;
                        c = c2;
                        String optString3 = e.optString("text");
                        String optString4 = e.optString("why");
                        if (StringsKt.isBlank(optString4)) {
                            optString4 = null;
                        }
                        String it6 = optString4;
                        if (it6 != null && (it = " - " + it6) != null) {
                            str2 = it;
                        }
                        str = optString3 + str2;
                    }
                    tl.addView(CardPage.line$default(lastDriveView2, format2 + "   " + str, CarUi.Companion.getTEXT(), 0.0f, 4, null));
                    evs2 = evs;
                    d2 = d;
                    c2 = c;
                }
            }
        }
        Galaxy7 current = Galaxy7.Companion.current();
        if (current != null) {
            current.get("/api/drives/stats?timezone=" + TimeZone.getDefault().getID(), new Function2() { // from class: com.starpilot.starview.LastDriveView$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit refresh$lambda$8;
                    refresh$lambda$8 = LastDriveView.refresh$lambda$8(LastDriveView.this, (JSONObject) obj, (String) obj2);
                    return refresh$lambda$8;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$8(LastDriveView this$0, JSONObject o, String str) {
        JSONObject g;
        String miles;
        String dur;
        if (o == null || (g = o.optJSONObject("lastDrive")) == null) {
            return Unit.INSTANCE;
        }
        LinearLayout gc = this$0.card();
        this$0.add(gc);
        gc.addView(this$0.title("Galaxy's record", "star", "device"));
        miles = JournalPagesKt.miles(g.optDouble("distanceMeters"));
        dur = JournalPagesKt.dur(g.optDouble("durationSeconds"));
        gc.addView(this$0.stats(TuplesKt.to(miles, "distance"), TuplesKt.to(dur, "time"), TuplesKt.to(Math.round(g.optDouble("engagedPercent", 0.0d)) + "%", "engaged"), TuplesKt.to(String.valueOf(g.optInt("distractedMoments")), "distracted"), TuplesKt.to(String.valueOf(g.optInt("unresponsiveMoments")), "unresponsive")));
        String optString = g.optString("model");
        if (StringsKt.isBlank(optString)) {
            optString = null;
        }
        String it = optString;
        if (it != null) {
            gc.addView(CardPage.line$default(this$0, "Model: " + it, 0, 0.0f, 6, null));
        }
        return Unit.INSTANCE;
    }
}
