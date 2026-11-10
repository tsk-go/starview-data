package com.starpilot.starview;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.starpilot.starview.JournalLogic;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

/* compiled from: JournalPages.kt */
public final class TakeoverSpotsView extends CardPage {
    /* VF: from Vineflower (jadx: ) */
    public TakeoverSpotsView(Context var1, CarUi var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
    }

    @Override // com.starpilot.starview.CardPage
    public void refresh() {
        String str;
        List tk;
        SimpleDateFormat simpleDateFormat;
        TakeoverSpotsView takeoverSpotsView = this;
        getCol().removeAllViews();
        List tk2 = DriveJournal.INSTANCE.read("takeover");
        List spots = JournalLogic.Companion.spots$default(JournalLogic.Companion, tk2, 0.0d, 2, null);
        LinearLayout head = card();
        takeoverSpotsView.add(head);
        head.addView(takeoverSpotsView.title("Takeover hot spots", "pin", "tablet"));
        TakeoverSpotsView takeoverSpotsView2 = takeoverSpotsView;
        if (tk2.isEmpty()) {
            str = "No takeovers noted yet. The tablet notes each one (with its place) while it's linked.";
        } else {
            str = tk2.size() + " takeovers in " + spots.size() + " places. A place you take over at again and again is worth a look: a lane that splits, a sharp curve, a merge.";
        }
        head.addView(CardPage.line$default(takeoverSpotsView2, str, 0, 0.0f, 6, null));
        Iterator it = CollectionsKt.take(spots, 30).iterator();
        while (it.hasNext()) {
            JournalLogic.Companion.Spot s = (JournalLogic.Companion.Spot) it.next();
            LinearLayout c = card();
            takeoverSpotsView.add(c);
            LinearLayout $this$refresh_u24lambda_u247 = new LinearLayout(getContext());
            $this$refresh_u24lambda_u247.setOrientation(0);
            $this$refresh_u24lambda_u247.setGravity(16);
            TextView $this$refresh_u24lambda_u247_u24lambda_u240 = new TextView($this$refresh_u24lambda_u247.getContext());
            $this$refresh_u24lambda_u247_u24lambda_u240.setText(s.getCount() + "×");
            $this$refresh_u24lambda_u247_u24lambda_u240.setTextColor(s.getCount() >= 3 ? CarUi.Companion.getWARN() : CarUi.Companion.getTEXT());
            $this$refresh_u24lambda_u247_u24lambda_u240.setTextSize(28.0f);
            $this$refresh_u24lambda_u247_u24lambda_u240.setTypeface(Typeface.DEFAULT_BOLD);
            $this$refresh_u24lambda_u247_u24lambda_u240.setMinWidth(getUi().dp(80));
            $this$refresh_u24lambda_u247.addView($this$refresh_u24lambda_u247_u24lambda_u240);
            LinearLayout $this$refresh_u24lambda_u247_u24lambda_u246 = new LinearLayout($this$refresh_u24lambda_u247.getContext());
            $this$refresh_u24lambda_u247_u24lambda_u246.setOrientation(1);
            TextView $this$refresh_u24lambda_u247_u24lambda_u246_u24lambda_u242 = new TextView($this$refresh_u24lambda_u247_u24lambda_u246.getContext());
            String road = s.getRoad();
            if (StringsKt.isBlank(road)) {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                tk = tk2;
                String format = String.format(Locale.US, "%.5f, %.5f", Arrays.copyOf(new Object[]{Double.valueOf(s.getLat()), Double.valueOf(s.getLon())}, 2));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                road = format;
            } else {
                tk = tk2;
            }
            $this$refresh_u24lambda_u247_u24lambda_u246_u24lambda_u242.setText(road);
            $this$refresh_u24lambda_u247_u24lambda_u246_u24lambda_u242.setTextColor(CarUi.Companion.getTEXT());
            $this$refresh_u24lambda_u247_u24lambda_u246_u24lambda_u242.setTextSize(19.0f);
            $this$refresh_u24lambda_u247_u24lambda_u246.addView($this$refresh_u24lambda_u247_u24lambda_u246_u24lambda_u242);
            TextView $this$refresh_u24lambda_u247_u24lambda_u246_u24lambda_u245 = new TextView($this$refresh_u24lambda_u247_u24lambda_u246.getContext());
            Iterable $this$sortedByDescending$iv = s.getCauses().entrySet();
            List spots2 = spots;
            String joinToString$default = CollectionsKt.joinToString(CollectionsKt.sortedWith($this$sortedByDescending$iv, new Comparator() { // from class: com.starpilot.starview.TakeoverSpotsView$refresh$lambda$7$lambda$6$lambda$5$$inlined$sortedByDescending$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Map.Entry it2 = (Map.Entry) t2;
                    Integer num = (Integer) it2.getValue();
                    Map.Entry it3 = (Map.Entry) t;
                    return ComparisonsKt.compareValues(num, (Integer) it3.getValue());
                }
            }), " · ", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.TakeoverSpotsView$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CharSequence refresh$lambda$7$lambda$6$lambda$5$lambda$4;
                    refresh$lambda$7$lambda$6$lambda$5$lambda$4 = TakeoverSpotsView.refresh$lambda$7$lambda$6$lambda$5$lambda$4((Map.Entry) obj);
                    return refresh$lambda$7$lambda$6$lambda$5$lambda$4;
                }
            });
            simpleDateFormat = JournalPagesKt.dayFmt;
            $this$refresh_u24lambda_u247_u24lambda_u246_u24lambda_u245.setText(joinToString$default + "  ·  last " + simpleDateFormat.format(new Date(s.getLastT())));
            $this$refresh_u24lambda_u247_u24lambda_u246_u24lambda_u245.setTextColor(CarUi.Companion.getDIM());
            $this$refresh_u24lambda_u247_u24lambda_u246_u24lambda_u245.setTextSize(15.0f);
            $this$refresh_u24lambda_u247_u24lambda_u246.addView($this$refresh_u24lambda_u247_u24lambda_u246_u24lambda_u245);
            $this$refresh_u24lambda_u247.addView($this$refresh_u24lambda_u247_u24lambda_u246, new LinearLayout.LayoutParams(0, -2, 1.0f));
            c.addView($this$refresh_u24lambda_u247);
            takeoverSpotsView = this;
            head = head;
            spots = spots2;
            it = it;
            tk2 = tk;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence refresh$lambda$7$lambda$6$lambda$5$lambda$4(Map.Entry it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getValue() + " " + it.getKey();
    }
}
