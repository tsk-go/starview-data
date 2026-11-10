package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starpilot.starview.GalaxyRow;
import com.starpilot.starview.SvIcon;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout.LayoutParams;

/* compiled from: DeviceHubs.kt */
public final class ConnectionsView extends ScrollView {
    private final HashMap<String, Card> cards;
    private final Handler main;
    private final Function1<String, Unit> open;
    private final CarUi ui;

    /* compiled from: DeviceHubs.kt */
    private static final class Card {
        private final LinearLayout box;
        private final SvIcon.Chip chip;
        private final TextView state;

        public Card(SvIcon.Chip chip, TextView state, LinearLayout box) {
            Intrinsics.checkNotNullParameter(chip, "chip");
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(box, "box");
            this.chip = chip;
            this.state = state;
            this.box = box;
        }

        public final LinearLayout getBox() {
            return this.box;
        }

        public final SvIcon.Chip getChip() {
            return this.chip;
        }

        public final TextView getState() {
            return this.state;
        }
    }

    /* VF: from Vineflower (jadx: ) */
    public ConnectionsView(Context var1, CarUi var2, Function1<? super String, Unit> var3) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        Intrinsics.checkNotNullParameter(var3, "open");
        this.ui = var2;
        this.open = (Function1) var3;
        this.cards = new HashMap<>();
        this.main = new Handler(Looper.getMainLooper());
        GridLayout var5 = new GridLayout(var1);
        var5.setColumnCount(2);
        var5.setPadding(this.ui.dp(2), this.ui.dp(2), this.ui.dp(2), this.ui.dp(30));
        Triple var7 = new Triple<>("wifi", "Wi-Fi", "native:network");
        Triple var6 = new Triple<>("link", "This tablet", "tablet:connection");
        Triple var10 = new Triple<>("hotspot", "Galaxy hotspot", "gweb:/galaxy|Galaxy Access & Install");
        Triple var11 = new Triple<>("bluetooth", "Bluetooth", "gweb:/bluetooth|Bluetooth");
        Triple var9 = new Triple<>("star", "Remote Galaxy", "gweb:/galaxy|Galaxy Access & Install");
        Triple var14 = new Triple<>("upload", "Uploads", "galaxy:data");
        String var13 = "satellite";
        Triple var8 = new Triple<>("satellite", "RTK GPS", "tablet:rtk");
        Iterator var19 = CollectionsKt.listOf(new Triple[]{var7, var6, var10, var11, var9, var14, var8}).iterator();

        for (int var4 = 0; var19.hasNext(); var4++) {
            var7 = (Triple)var19.next();
            String var15;
            if (!Intrinsics.areEqual(var7.getFirst(), "link") && !Intrinsics.areEqual(var7.getFirst(), var13)) {
                var15 = "device";
            } else {
                var15 = "tablet";
            }

            View var16 = SvIcon.chip$default(SvIcon.INSTANCE, var1, (String)var7.getFirst(), var15, 52, null, 16, null);
            Intrinsics.checkNotNull(var16, "null cannot be cast to non-null type com.starpilot.starview.SvIcon.Chip");
            SvIcon.Chip var22 = (SvIcon.Chip)var16;
            TextView var24 = new TextView(var1);
            var24.setText("…");
            var24.setTextColor(CarUi.Companion.getDIM());
            var24.setTextSize(16.0F);
            LinearLayout var17 = new LinearLayout(var1);
            var17.setOrientation(0);
            var17.setGravity(16);
            var17.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 20, null, 4, null));
            var17.setPadding(this.ui.dp(18), this.ui.dp(18), this.ui.dp(16), this.ui.dp(18));
            var17.setMinimumHeight(this.ui.dp(110));
            View var25 = var22;
            android.widget.LinearLayout.LayoutParams var28 = new android.widget.LinearLayout.LayoutParams(this.ui.dp(52), this.ui.dp(52));
            var28.rightMargin = this.ui.dp(16);
            Unit var12 = Unit.INSTANCE;
            var17.addView(var25, (android.view.ViewGroup.LayoutParams)var28);
            LinearLayout var26 = new LinearLayout(var1);
            var26.setOrientation(1);
            TextView var29 = new TextView(var1);
            var29.setText((CharSequence)var7.getSecond());
            var29.setTextColor(CarUi.Companion.getTEXT());
            var29.setTextSize(20.0F);
            var29.setTypeface(Typeface.DEFAULT_BOLD);
            var26.addView((View)var29);
            var26.addView((View)var24);
            var17.addView((View)var26, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
            TextView var27 = new TextView(var1);
            var27.setText("›");
            var27.setTextSize(30.0F);
            var27.setTextColor(CarUi.Companion.getDIM());
            var17.addView((View)var27);
            var17.setClickable(true);
            var17.setOnClickListener(new ConnectionsView$$ExternalSyntheticLambda0(this, var7));
            this.cards.put((String)var7.getFirst(), new ConnectionsView.Card(var22, var24, var17));
            View var21 = (View)var17;
            android.widget.GridLayout.LayoutParams var23 = new android.widget.GridLayout.LayoutParams(GridLayout.spec(var4 / 2), GridLayout.spec(var4 % 2, 1.0F));
            var23.width = 0;
            var23.setMargins(this.ui.dp(7), this.ui.dp(7), this.ui.dp(7), this.ui.dp(7));
            Unit var18 = Unit.INSTANCE;
            var5.addView(var21, (android.view.ViewGroup.LayoutParams)var23);
        }

        this.addView((View)var5);
    }

    public final boolean set(final String id, final String text, final Boolean on) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(text, "text");
        return this.main.post(new Runnable() { // from class: com.starpilot.starview.ConnectionsView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsView.set$lambda$9(ConnectionsView.this, id, text, on);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void set$lambda$9(ConnectionsView this$0, String $id, String $text, Boolean $on) {
        SvIcon.State state;
        Card c = this$0.cards.get($id);
        if (c == null) {
            return;
        }
        c.getState().setText($text);
        SvIcon.Chip chip = c.getChip();
        if (Intrinsics.areEqual((Object) $on, (Object) true)) {
            state = SvIcon.State.ON;
        } else if (Intrinsics.areEqual((Object) $on, (Object) false)) {
            state = SvIcon.State.OFF;
        } else {
            if ($on != null) {
                throw new NoWhenBranchMatchedException();
            }
            state = SvIcon.State.NA;
        }
        SvIcon.Chip.set$default(chip, null, null, state, 3, null);
        c.getBox().setBackground(Intrinsics.areEqual((Object) $on, (Object) true) ? this$0.ui.rounded(Color.rgb(19, 34, 25), 20, Integer.valueOf(Color.rgb(43, 107, 75))) : CarUi.rounded$default(this$0.ui, CarUi.Companion.getCARD(), 20, null, 4, null));
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public final void refresh() {
        RtkStatus var19 = null; // declarations Vineflower left out
        DataClient var7 = Link.INSTANCE.getData();
        int var2 = 1;
        int var1;
        if (var7 != null && var7.getConnected()) {
            var1 = 1;
        } else {
            var1 = 0;
        }

        String var5 = "";
        String var11;
        if ((var1 != 0)) {
            String var6 = Link.INSTANCE.getHost();
            var11 = var6;
            if (var6 == null) {
                var11 = "";
            }

            var11 = "Linked · " + var11;
        } else {
            var11 = "Not linked";
        }

        boolean var3;
        if (var7 != null && var7.getConnected()) {
            var3 = true;
        } else {
            var3 = false;
        }

        this.set("link", var11, var3);
        if (var7 != null && var7.getConnected()) {
            var1 = var2;
        } else {
            var1 = (((boolean)false) ? 1 : 0);
        }

        Object var18 = null;
        if ((var1 != 0)) {
            var7.request("wifi_status", MapsKt.emptyMap(), new ConnectionsView$$ExternalSyntheticLambda2(this));
        } else {
            this.set("wifi", "Unknown (tablet not linked)", null);
        }

        label47: {
            var19 = Ntrip.INSTANCE.getRtk();
            if (var19 != null) {
                String var8 = var19.getWords();
                var1 = var19.getUsed();
                var11 = var5;
                if (var19.getHasFix()) {
                    var11 = RtkStatus.Companion.accuracy(var19.getHAccM(), false);
                    var11 = " · ±" + var11;
                }

                var5 = var8 + " · " + var1 + " sats" + var11;
                var11 = var5;
                if (var5 != null) {
                    break label47;
                }
            }

            var11 = "No receiver seen";
        }

        Boolean var17 = (Boolean)var18;
        if (var19 != null) {
            var17 = var19.getHasFix();
        }

        this.set("satellite", var11, var17);
        Galaxy7 var15 = Galaxy7.Companion.current();
        if (var15 != null) {
            var15.get("/api/galaxy/hotspot", new ConnectionsView$$ExternalSyntheticLambda3(this));
            var15.get("/api/bluetooth/status", new ConnectionsView$$ExternalSyntheticLambda4(this));
            var15.get("/api/galaxy/status", new ConnectionsView$$ExternalSyntheticLambda5(this));
            GalaxyPages.get$default(GalaxyPages.INSTANCE, "data", 0L, new ConnectionsView$$ExternalSyntheticLambda6(this), 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$10(ConnectionsView this$0, JSONObject st) {
        Intrinsics.checkNotNullParameter(st, "st");
        String ssid = st.optString("ssid");
        if (st.optBoolean("tethering")) {
            this$0.set("wifi", "Tethering · " + st.optString("tether_ssid"), true);
        } else {
            Intrinsics.checkNotNull(ssid);
            if (ssid.length() > 0) {
                this$0.set("wifi", ssid + " · " + st.optString("ip"), true);
            } else {
                this$0.set("wifi", "Not connected", false);
            }
        }
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit refresh$lambda$16(ConnectionsView var0, JSONObject var1, String var2) {
        if (var1 != null) {
            if (!var1.optBoolean("active")) {
                var2 = "Off";
            } else {
                label26: {
                    JSONObject var4 = var1.optJSONObject("config");
                    if (var4 != null) {
                        var2 = var4.optString("ssid");
                        if (var2 != null) {
                            CharSequence var3 = var2;
                            CharSequence var6 = var3;
                            if (StringsKt.isBlank(var3)) {
                                var6 = null;
                            }

                            var2 = (String)var6;
                            if (var2 != null) {
                                String var10 = " · " + var2;
                                var2 = var10;
                                if (var10 != null) {
                                    break label26;
                                }
                            }
                        }
                    }

                    var2 = "";
                }

                var2 = "On" + var2;
            }

            var0.set("hotspot", var2, var1.optBoolean("active"));
        }

        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$18(ConnectionsView this$0, JSONObject o, String str) {
        if (o != null) {
            String line = ToolLines.INSTANCE.line("/bluetooth", o);
            if (line == null) {
                line = "–";
            }
            this$0.set("bluetooth", line, Boolean.valueOf(o.optBoolean("powered")));
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$20(ConnectionsView this$0, JSONObject o, String str) {
        if (o != null) {
            this$0.set("star", o.optBoolean("paired") ? "Paired" : "Not paired", Boolean.valueOf(o.optBoolean("paired")));
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$24(ConnectionsView this$0, JSONObject o) {
        Object element$iv;
        if (o != null) {
            Iterable $this$firstOrNull$iv = GalaxyRow.Companion.parse(o);
            Iterator it = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    element$iv = null;
                    break;
                }
                element$iv = it.next();
                if (((GalaxyRow) element$iv).getControl() == GalaxyRow.Control.SWITCH) {
                    break;
                }
            }
            GalaxyRow r = (GalaxyRow) element$iv;
            if (r != null) {
                String label = r.getLabel();
                String lowerCase = r.getValue().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                this$0.set("upload", label + ": " + lowerCase, Boolean.valueOf(Intrinsics.areEqual(r.getValue(), "On")));
            }
        }
        return Unit.INSTANCE;
    }

    // recovered from Vineflower (R8 lambda body jadx inlined)
    static final void lambda$7$lambda$6(ConnectionsView var0, Triple var1, View var2) {
       var0.open.invoke((String)var1.getThird());
    }
}
