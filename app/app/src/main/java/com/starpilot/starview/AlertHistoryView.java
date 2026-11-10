package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: JournalPages.kt */
public final class AlertHistoryView extends CardPage {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlertHistoryView(Context ctx, CarUi ui) {
        super(ctx, ui);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
    }

    @Override // com.starpilot.starview.CardPage
    public void refresh() {
        SimpleDateFormat simpleDateFormat;
        getCol().removeAllViews();
        List alerts = CollectionsKt.asReversed(DriveJournal.INSTANCE.read("alert"));
        if (!alerts.isEmpty()) {
            final List $this$groupingBy$iv = alerts;
            Iterable $this$sortedByDescending$iv = GroupingKt.eachCount(new Grouping<JSONObject, String>() { // from class: com.starpilot.starview.AlertHistoryView$refresh$$inlined$groupingBy$1
                @Override // kotlin.collections.Grouping
                public Iterator<JSONObject> sourceIterator() {
                    return $this$groupingBy$iv.iterator();
                }

                @Override // kotlin.collections.Grouping
                public String keyOf(JSONObject element) {
                    JSONObject it = element;
                    return it.optString("text");
                }
            }).entrySet();
            List<Map.Entry> freq = CollectionsKt.take(CollectionsKt.sortedWith($this$sortedByDescending$iv, new Comparator() { // from class: com.starpilot.starview.AlertHistoryView$refresh$$inlined$sortedByDescending$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Map.Entry it = (Map.Entry) t2;
                    Integer num = (Integer) it.getValue();
                    Map.Entry it2 = (Map.Entry) t;
                    return ComparisonsKt.compareValues(num, (Integer) it2.getValue());
                }
            }), 6);
            LinearLayout $this$refresh_u24lambda_u243 = card();
            $this$refresh_u24lambda_u243.addView(title("Most often", "bell", "sound"));
            for (Map.Entry entry : freq) {
                String txt = (String) entry.getKey();
                int n = ((Number) entry.getValue()).intValue();
                $this$refresh_u24lambda_u243.addView(line(n + "×   " + txt, CarUi.Companion.getTEXT(), 17.0f));
            }
            add($this$refresh_u24lambda_u243);
            String day = "";
            LinearLayout c = card();
            add(c);
            c.addView(title("All alerts", "history", "sound"));
            for (JSONObject a : CollectionsKt.take(alerts, 300)) {
                String dd = new SimpleDateFormat("EEEE, MMM d", Locale.US).format(new Date(a.optLong("t")));
                if (!Intrinsics.areEqual(dd, day)) {
                    day = dd;
                    CarUi ui = getUi();
                    Intrinsics.checkNotNull(dd);
                    c.addView(ui.header(dd));
                }
                String optString = a.optString("status");
                int color = Intrinsics.areEqual(optString, "critical") ? Color.rgb(255, 138, 128) : Intrinsics.areEqual(optString, "userprompt") ? CarUi.Companion.getWARN() : CarUi.Companion.getTEXT();
                simpleDateFormat = JournalPagesKt.timeFmt;
                c.addView(line(simpleDateFormat.format(new Date(a.optLong("t"))) + "   " + a.optString("text"), color, 17.0f));
                String[] strArr = new String[3];
                String optString2 = a.optString("why");
                String str = null;
                if (StringsKt.isBlank(optString2)) {
                    optString2 = null;
                }
                strArr[0] = optString2;
                strArr[1] = a.optInt("mph") + " mph";
                String optString3 = a.optString("road");
                if (!StringsKt.isBlank(optString3)) {
                    str = optString3;
                }
                strArr[2] = str;
                String sub = CollectionsKt.joinToString(CollectionsKt.listOfNotNull((Object[]) strArr), " · ", "", "", -1, "...", null);
                c.addView(line("            " + sub, CarUi.Companion.getDIM(), 14.0f));
            }
            return;
        }
        LinearLayout $this$refresh_u24lambda_u240 = card();
        $this$refresh_u24lambda_u240.addView(title("Alert history", "bell", "sound"));
        $this$refresh_u24lambda_u240.addView(CardPage.line$default(this, "No alerts noted yet. The tablet notes each alert while it's linked.", 0, 0.0f, 6, null));
        add($this$refresh_u24lambda_u240);
    }
}
