package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.LaneMapLog;
import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

/* compiled from: RoadData.kt */
public final class RoadDataView extends CardPage {
    private final RoadDataView$poll$1 poll;

    /* VF: from Vineflower (jadx: Type inference failed) */
    public RoadDataView(Context var1, CarUi var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        this.poll = new Runnable() {
            final RoadDataView this$0 = RoadDataView.this;

            @Override
            public void run() {
                this.this$0.paint();
                if (RoadData.INSTANCE.getBusy()) {
                    this.this$0.getMain().postDelayed(this, 1000L);
                }
            }
        };
        this.paint();
    }

    @Override // com.starpilot.starview.CardPage
    public void refresh() {
        paint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void paint() {
        String str;
        String format;
        getCol().removeAllViews();
        Map files = Hpms.INSTANCE.files();
        long j = 0;
        for (File it : files.values()) {
            j += it.length();
        }
        long bytes = j;
        LinearLayout c = card();
        add(c);
        c.addView(title("Lane road data", "lane", "tablet"));
        c.addView(line("How many lanes every road in the US really has - the government's road survey (FHWA HPMS, 2024). It counts the road's own lanes only, so the lane card can tell a merge or exit lane beside the road from one more lane, and knows tunnels and bridges (a solid line there splits lanes, it's not the road's edge). No new data: the card works as before.", CarUi.Companion.getDIM(), 15.0f));
        if (files.isEmpty()) {
            str = "On the tablet: nothing yet.";
        } else {
            str = "On the tablet: " + files.size() + " state" + (files.size() == 1 ? "" : "s") + " (" + RoadData.INSTANCE.mb(bytes) + "): " + CollectionsKt.joinToString(CollectionsKt.sorted(files.keySet()), " ", "", "", -1, "...", null);
        }
        c.addView(line(str, CarUi.Companion.getTEXT(), 16.0f));
        if (RoadData.INSTANCE.getStatus().length() > 0) {
            c.addView(line(RoadData.INSTANCE.getStatus(), (StringsKt.startsWith(RoadData.INSTANCE.getStatus(), "Couldn't", false) || StringsKt.startsWith(RoadData.INSTANCE.getStatus(), "Not on", false)) ? CarUi.Companion.getWARN() : CarUi.Companion.getTEXT(), 15.0f));
        }
        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(0);
        row.setPadding(0, getUi().dp(10), 0, 0);
        if (RoadData.INSTANCE.getBusy()) {
            row.addView(CarUi.button$default(getUi(), "Stop", false, new Function0() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paint$lambda$2;
                    paint$lambda$2 = RoadDataView.paint$lambda$2();
                    return paint$lambda$2;
                }
            }, 2, null));
        } else {
            row.addView(getUi().button("Download all (Wi-Fi)", true, new Function0() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paint$lambda$5;
                    paint$lambda$5 = RoadDataView.paint$lambda$5(RoadDataView.this);
                    return paint$lambda$5;
                }
            }));
        }
        View $this$paint_u24lambda_u246 = getUi().spacer(1);
        $this$paint_u24lambda_u246.setLayoutParams(new LinearLayout.LayoutParams(getUi().dp(12), 1));
        row.addView($this$paint_u24lambda_u246);
        row.addView(CarUi.button$default(getUi(), "Import a file…", false, new Function0() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$10;
                paint$lambda$10 = RoadDataView.paint$lambda$10(RoadDataView.this);
                return paint$lambda$10;
            }
        }, 2, null));
        c.addView(row);
        RoadData roadData = RoadData.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        c.addView(line("Downloads once, ~370 MB for all states, only on Wi-Fi. From: " + roadData.base(context), CarUi.Companion.getDIM(), 13.0f));
        LaneMapLog.Export export = LaneMapLog.Export.INSTANCE;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        Triple<Integer, Long, Long> summary = export.summary(context2);
        int drives = summary.component1().intValue();
        long points = summary.component2().longValue();
        long tb = summary.component3().longValue();
        LinearLayout t = card();
        add(t);
        t.addView(title("Your lane map", "satellite", "tablet"));
        t.addView(line("While you drive with an RTK fix, the tablet notes exactly where across the road you are, every ~3 ft. After a few drives on a road, the traces show where each lane, merge lane and exit lane really is - to inches. Nothing uses them yet: they're checked against your drives first.", CarUi.Companion.getDIM(), 15.0f));
        if (drives == 0) {
            format = "Nothing yet.";
        } else {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%d drive%s · %,d points (~%.0f mi) · %s", Arrays.copyOf(new Object[]{Integer.valueOf(drives), drives != 1 ? "s" : "", Long.valueOf(points), Double.valueOf((points * 0.9d) / 1609.34d), RoadData.INSTANCE.mb(tb)}, 5));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        }
        t.addView(line(format, CarUi.Companion.getTEXT(), 16.0f));
        TextView button$default = CarUi.button$default(getUi(), "Export lane traces", false, new Function0() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$13;
                paint$lambda$13 = RoadDataView.paint$lambda$13(RoadDataView.this);
                return paint$lambda$13;
            }
        }, 2, null);
        LinearLayout.LayoutParams $this$paint_u24lambda_u2414 = CarUi.lp$default(getUi(), 0, 4, 1, null);
        $this$paint_u24lambda_u2414.topMargin = getUi().dp(8);
        Unit unit = Unit.INSTANCE;
        t.addView(button$default, $this$paint_u24lambda_u2414);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$2() {
        RoadData.INSTANCE.cancel();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$5(final RoadDataView this$0) {
        RoadData roadData = RoadData.INSTANCE;
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        roadData.download(context, new Function0() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paint$lambda$5$lambda$4;
                paint$lambda$5$lambda$4 = RoadDataView.paint$lambda$5$lambda$4(this$0);
                return paint$lambda$5$lambda$4;
            }
        });
        this$0.getMain().removeCallbacks(this$0.poll);
        this$0.getMain().post(this$0.poll);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$5$lambda$4(final RoadDataView this$0) {
        this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this$0.paint();
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$10(final RoadDataView this$0) {
        Object context = this$0.getContext();
        GxHost gxHost = context instanceof GxHost ? (GxHost) context : null;
        if (gxHost != null) {
            gxHost.pickFile(new String[]{"*/*"}, new Function1() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paint$lambda$10$lambda$9;
                    paint$lambda$10$lambda$9 = RoadDataView.paint$lambda$10$lambda$9(this$0, (Uri) obj);
                    return paint$lambda$10$lambda$9;
                }
            });
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$10$lambda$9(final RoadDataView this$0, final Uri uri) {
        if (uri != null) {
            new Thread(new Runnable() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RoadDataView.paint$lambda$10$lambda$9$lambda$8(this$0, uri);
                }
            }).start();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void paint$lambda$10$lambda$9$lambda$8(final RoadDataView this$0, Uri $uri) {
        RoadData roadData = RoadData.INSTANCE;
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        final String err = roadData.importFile(context, $uri);
        this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                RoadDataView.paint$lambda$10$lambda$9$lambda$8$lambda$7(this$0, err);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void paint$lambda$10$lambda$9$lambda$8$lambda$7(RoadDataView this$0, String $err) {
        Toast.makeText(this$0.getContext(), $err == null ? "Imported." : $err, 1).show();
        this$0.paint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paint$lambda$13(final RoadDataView this$0) {
        new Thread(new Runnable() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RoadDataView.paint$lambda$13$lambda$12(this$0);
            }
        }).start();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void paint$lambda$13$lambda$12(final RoadDataView this$0) {
        LaneMapLog.Export export = LaneMapLog.Export.INSTANCE;
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        final String err = export.toDownloads(context);
        this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.RoadDataView$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                RoadDataView.paint$lambda$13$lambda$12$lambda$11(this$0, err);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void paint$lambda$13$lambda$12$lambda$11(RoadDataView this$0, String $err) {
        Toast.makeText(this$0.getContext(), $err == null ? "Saved to Download/StarView/Lane data" : $err, 1).show();
    }
}
