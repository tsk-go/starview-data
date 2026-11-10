package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import com.starpilot.starview.JournalLogic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: JournalPages.kt */
public final class DrivingTrendsView extends CardPage {
    /* VF: from Vineflower (jadx: ) */
    public DrivingTrendsView(Context var1, CarUi var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
    }

    @Override // com.starpilot.starview.CardPage
    public void refresh() {
        boolean z;
        Iterable $this$map$iv;
        int $i$f$map;
        Iterable $this$mapTo$iv$iv;
        String str;
        Iterable $this$map$iv2;
        String str2;
        getCol().removeAllViews();
        List weekly = JournalLogic.Companion.weekly$default(JournalLogic.Companion, DriveJournal.INSTANCE.read("drive"), System.currentTimeMillis(), 0, 4, null);
        LinearLayout tc = card();
        add(tc);
        tc.addView(title("Takeovers per 100 miles, by week", "plots", "tablet"));
        List $this$all$iv = weekly;
        if (!($this$all$iv instanceof Collection) || !$this$all$iv.isEmpty()) {
            Iterator it = $this$all$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                Object element$iv = it.next();
                double doubleValue = ((Number) ((Pair) element$iv).getFirst()).doubleValue();
                if ((Double.isInfinite(doubleValue) || Double.isNaN(doubleValue)) ? false : true) {
                    z = false;
                    break;
                }
            }
        } else {
            z = true;
        }
        if (z) {
            tc.addView(CardPage.line$default(this, "Fills in as the tablet notes drives (it needs a week or two).", 0, 0.0f, 6, null));
        } else {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            List $this$map$iv3 = weekly;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
            Iterator it2 = $this$map$iv3.iterator();
            while (it2.hasNext()) {
                destination$iv$iv.add(Float.valueOf((float) ((Number) ((Pair) it2.next()).getFirst()).doubleValue()));
            }
            ArrayList arrayList = (List) destination$iv$iv;
            Iterable $this$map$iv4 = CollectionsKt.getIndices(weekly);
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv4, 10));
            Iterator<Integer> it3 = $this$map$iv4.iterator();
            while (it3.hasNext()) {
                int item$iv$iv = ((IntIterator) it3).nextInt();
                if (item$iv$iv == weekly.size() - 1) {
                    str2 = "this wk";
                    $this$map$iv2 = $this$map$iv4;
                } else {
                    $this$map$iv2 = $this$map$iv4;
                    str2 = "-" + ((weekly.size() - 1) - item$iv$iv);
                }
                destination$iv$iv2.add(str2);
                $this$map$iv4 = $this$map$iv2;
            }
            ArrayList arrayList2 = (List) destination$iv$iv2;
            int rgb = Color.rgb(127, 180, 255);
            List $this$map$iv5 = weekly;
            int $i$f$map2 = 0;
            Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv5, 10));
            Iterable<Pair> $this$mapTo$iv$iv2 = $this$map$iv5;
            for (Pair it4 : $this$mapTo$iv$iv2) {
                double doubleValue2 = ((Number) it4.getFirst()).doubleValue();
                if ((Double.isInfinite(doubleValue2) || Double.isNaN(doubleValue2)) ? false : true) {
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    $this$map$iv = $this$map$iv5;
                    $i$f$map = $i$f$map2;
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                    str = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{it4.getFirst()}, 1));
                    Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                } else {
                    $this$map$iv = $this$map$iv5;
                    $i$f$map = $i$f$map2;
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                    str = "";
                }
                destination$iv$iv3.add(str);
                $this$map$iv5 = $this$map$iv;
                $this$mapTo$iv$iv2 = $this$mapTo$iv$iv;
                $i$f$map2 = $i$f$map;
            }
            tc.addView(new Bars(context, arrayList, arrayList2, rgb, (List) destination$iv$iv3), new LinearLayout.LayoutParams(-1, getUi().dp(170)));
            tc.addView(CardPage.line$default(this, "Lower is better. Miles per week: " + CollectionsKt.joinToString(weekly, "  ", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.DrivingTrendsView$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CharSequence refresh$lambda$4;
                    refresh$lambda$4 = DrivingTrendsView.refresh$lambda$4((Pair) obj);
                    return refresh$lambda$4;
                }
            }), 0, 0.0f, 6, null));
        }
        Galaxy7 current = Galaxy7.Companion.current();
        if (current != null) {
            current.get("/api/drives/stats?timezone=" + TimeZone.getDefault().getID(), new Function2() { // from class: com.starpilot.starview.DrivingTrendsView$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit refresh$lambda$12;
                    refresh$lambda$12 = DrivingTrendsView.refresh$lambda$12(DrivingTrendsView.this, (JSONObject) obj, (String) obj2);
                    return refresh$lambda$12;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence refresh$lambda$4(Pair it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return String.valueOf(((Number) it.getSecond()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$12(DrivingTrendsView this$0, JSONObject o, String err) {
        String v;
        String miles;
        String miles2;
        String dur;
        if (o == null) {
            LinearLayout $this$refresh_u24lambda_u2412_u24lambda_u245 = this$0.card();
            $this$refresh_u24lambda_u2412_u24lambda_u245.addView(CardPage.line$default(this$0, err == null ? "Galaxy's drive stats aren't available" : err, 0, 0.0f, 6, null));
            this$0.add($this$refresh_u24lambda_u2412_u24lambda_u245);
            return Unit.INSTANCE;
        }
        LinearLayout c = this$0.card();
        this$0.add(c);
        c.addView(this$0.title("All drives (Galaxy)", "star", "device"));
        JSONObject t = o.optJSONObject("totals");
        if (t != null) {
            Pair<String, String>[] pairArr = new Pair[4];
            pairArr[0] = TuplesKt.to(String.valueOf(t.optInt("drives")), "drives");
            miles2 = JournalPagesKt.miles(t.optDouble("distanceMeters"));
            pairArr[1] = TuplesKt.to(miles2, "distance");
            dur = JournalPagesKt.dur(t.optDouble("durationSeconds"));
            pairArr[2] = TuplesKt.to(dur, "time");
            pairArr[3] = TuplesKt.to((t.optDouble("durationSeconds") > 0.0d ? Math.round((t.optDouble("engagedSeconds") * 100) / t.optDouble("durationSeconds")) : 0L) + "%", "openpilot on");
            c.addView(this$0.stats(pairArr));
        }
        JSONObject w = o.optJSONObject("week");
        if (w != null) {
            JSONArray days = w.optJSONArray("days");
            if (days != null && days.length() > 0) {
                Iterable $this$map$iv = RangesKt.until(0, days.length());
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                Iterable $this$mapTo$iv$iv = $this$map$iv;
                Iterator<Integer> it = $this$mapTo$iv$iv.iterator();
                while (it.hasNext()) {
                    int item$iv$iv = ((IntIterator) it).nextInt();
                    destination$iv$iv.add(Float.valueOf((float) (days.getJSONObject(item$iv$iv).optDouble("distanceMeters") / 1609.344d)));
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv;
                }
                List vals = (List) destination$iv$iv;
                Iterable $this$map$iv2 = RangesKt.until(0, days.length());
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
                Iterator<Integer> it2 = $this$map$iv2.iterator();
                while (it2.hasNext()) {
                    int item$iv$iv2 = ((IntIterator) it2).nextInt();
                    String optString = days.getJSONObject(item$iv$iv2).optString("date");
                    Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                    destination$iv$iv2.add(StringsKt.takeLast(optString, 5));
                }
                List labels = (List) destination$iv$iv2;
                miles = JournalPagesKt.miles(w.optDouble("distanceMeters"));
                c.addView(CardPage.line$default(this$0, "This week: " + miles + ", " + w.optDouble("engagedPercent") + "% with openpilot on", 0, 0.0f, 6, null), CarUi.lp$default(this$0.getUi(), 0, 4, 1, null));
                Context context = this$0.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                int accent = CarUi.Companion.getACCENT();
                List $this$map$iv3 = vals;
                Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
                for (Object item$iv$iv3 : $this$map$iv3) {
                    float it3 = ((Number) item$iv$iv3).floatValue();
                    destination$iv$iv3.add(it3 >= 1.0f ? String.valueOf(Math.round(it3)) : "");
                    w = w;
                }
                c.addView(new Bars(context, vals, labels, accent, (List) destination$iv$iv3), new LinearLayout.LayoutParams(-1, this$0.getUi().dp(150)));
            }
        }
        JSONArray r = o.optJSONArray("records");
        if (r != null && r.length() > 0) {
            LinearLayout rc = this$0.card();
            this$0.add(rc);
            rc.addView(this$0.title("Personal records", "flag", "device"));
            int length = r.length();
            for (int i = 0; i < length; i++) {
                JSONObject x = r.getJSONObject(i);
                String optString2 = x.optString("unit");
                if (Intrinsics.areEqual(optString2, "m")) {
                    v = JournalPagesKt.miles(x.optDouble("value"));
                } else if (Intrinsics.areEqual(optString2, "s")) {
                    v = JournalPagesKt.dur(x.optDouble("value"));
                } else {
                    v = x.optString("value") + " " + x.optString("unit");
                }
                rc.addView(this$0.line(x.optString("label") + ":  " + v, CarUi.Companion.getTEXT(), 17.0f));
            }
        }
        return Unit.INSTANCE;
    }
}
