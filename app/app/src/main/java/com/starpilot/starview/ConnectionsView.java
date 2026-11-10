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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ConnectionsView(Context ctx, CarUi ui, Function1<? super String, Unit> open) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        Intrinsics.checkNotNullParameter(open, "open");
        this.ui = ui;
        this.open = open;
        this.cards = new HashMap<>();
        this.main = new Handler(Looper.getMainLooper());
        GridLayout $this$_init__u24lambda_u240 = new GridLayout(ctx);
        $this$_init__u24lambda_u240.setColumnCount(2);
        $this$_init__u24lambda_u240.setPadding(this.ui.dp(2), this.ui.dp(2), this.ui.dp(2), this.ui.dp(30));
        int i = 0;
        boolean z = true;
        String str = "satellite";
        List<Triple> list = CollectionsKt.listOf((Object[]) new Triple[]{new Triple("wifi", "Wi-Fi", "native:network"), new Triple("link", "This tablet", "tablet:connection"), new Triple("hotspot", "Galaxy hotspot", "gweb:/galaxy|Galaxy Access & Install"), new Triple("bluetooth", "Bluetooth", "gweb:/bluetooth|Bluetooth"), new Triple("star", "Remote Galaxy", "gweb:/galaxy|Galaxy Access & Install"), new Triple("upload", "Uploads", "galaxy:data"), new Triple("satellite", "RTK GPS", "tablet:rtk")});
        int i2 = 0;
        for (final Triple c : list) {
            int i3 = i2;
            int i4 = i2 + 1;
            String group = (Intrinsics.areEqual(c.getFirst(), "link") || Intrinsics.areEqual(c.getFirst(), str)) ? "tablet" : "device";
            String str2 = str;
            View chip$default = SvIcon.chip$default(SvIcon.INSTANCE, ctx, (String) c.getFirst(), group, 52, null, 16, null);
            Intrinsics.checkNotNull(chip$default, "null cannot be cast to non-null type com.starpilot.starview.SvIcon.Chip");
            SvIcon.Chip chip = (SvIcon.Chip) chip$default;
            TextView $this$_init__u24lambda_u241 = new TextView(ctx);
            $this$_init__u24lambda_u241.setText("…");
            $this$_init__u24lambda_u241.setTextColor(CarUi.Companion.getDIM());
            $this$_init__u24lambda_u241.setTextSize(16.0f);
            LinearLayout $this$_init__u24lambda_u247 = new LinearLayout(ctx);
            $this$_init__u24lambda_u247.setOrientation(i);
            $this$_init__u24lambda_u247.setGravity(16);
            $this$_init__u24lambda_u247.setBackground(CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 20, null, 4, null));
            $this$_init__u24lambda_u247.setPadding(this.ui.dp(18), this.ui.dp(18), this.ui.dp(16), this.ui.dp(18));
            $this$_init__u24lambda_u247.setMinimumHeight(this.ui.dp(110));
            LinearLayout.LayoutParams $this$lambda_u247_u24lambda_u242 = new LinearLayout.LayoutParams(this.ui.dp(52), this.ui.dp(52));
            $this$lambda_u247_u24lambda_u242.rightMargin = this.ui.dp(16);
            Unit unit = Unit.INSTANCE;
            $this$_init__u24lambda_u247.addView(chip, $this$lambda_u247_u24lambda_u242);
            LinearLayout $this$lambda_u247_u24lambda_u244 = new LinearLayout(ctx);
            $this$lambda_u247_u24lambda_u244.setOrientation(1);
            TextView $this$lambda_u247_u24lambda_u244_u24lambda_u243 = new TextView(ctx);
            $this$lambda_u247_u24lambda_u244_u24lambda_u243.setText((CharSequence) c.getSecond());
            $this$lambda_u247_u24lambda_u244_u24lambda_u243.setTextColor(CarUi.Companion.getTEXT());
            $this$lambda_u247_u24lambda_u244_u24lambda_u243.setTextSize(20.0f);
            $this$lambda_u247_u24lambda_u244_u24lambda_u243.setTypeface(Typeface.DEFAULT_BOLD);
            $this$lambda_u247_u24lambda_u244.addView($this$lambda_u247_u24lambda_u244_u24lambda_u243);
            $this$lambda_u247_u24lambda_u244.addView($this$_init__u24lambda_u241);
            $this$_init__u24lambda_u247.addView($this$lambda_u247_u24lambda_u244, new LinearLayout.LayoutParams(0, -2, 1.0f));
            TextView $this$lambda_u247_u24lambda_u245 = new TextView(ctx);
            $this$lambda_u247_u24lambda_u245.setText("›");
            $this$lambda_u247_u24lambda_u245.setTextSize(30.0f);
            $this$lambda_u247_u24lambda_u245.setTextColor(CarUi.Companion.getDIM());
            $this$_init__u24lambda_u247.addView($this$lambda_u247_u24lambda_u245);
            $this$_init__u24lambda_u247.setClickable(true);
            $this$_init__u24lambda_u247.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.ConnectionsView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ConnectionsView.this.open.invoke(c.getThird());
                }
            });
            this.cards.put(c.getFirst(), new Card(chip, $this$_init__u24lambda_u241, $this$_init__u24lambda_u247));
            GridLayout.LayoutParams $this$_init__u24lambda_u248 = new GridLayout.LayoutParams(GridLayout.spec(i3 / 2), GridLayout.spec(i3 % 2, 1.0f));
            $this$_init__u24lambda_u248.width = 0;
            $this$_init__u24lambda_u248.setMargins(this.ui.dp(7), this.ui.dp(7), this.ui.dp(7), this.ui.dp(7));
            Unit unit2 = Unit.INSTANCE;
            $this$_init__u24lambda_u240.addView($this$_init__u24lambda_u247, $this$_init__u24lambda_u248);
            i2 = i4;
            str = str2;
            z = true;
            i = 0;
        }
        addView($this$_init__u24lambda_u240);
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

    /* JADX WARN: Code restructure failed: missing block: B:29:0x00c4, code lost:
    
        if (r2 == null) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void refresh() {
        String str;
        String str2;
        DataClient d = Link.INSTANCE.getData();
        if (d != null && d.getConnected()) {
            String host = Link.INSTANCE.getHost();
            if (host == null) {
                host = "";
            }
            str = "Linked · " + host;
        } else {
            str = "Not linked";
        }
        set("link", str, Boolean.valueOf(d != null && d.getConnected()));
        if (d != null && d.getConnected()) {
            d.request("wifi_status", MapsKt.emptyMap(), new Function1() { // from class: com.starpilot.starview.ConnectionsView$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit refresh$lambda$10;
                    refresh$lambda$10 = ConnectionsView.refresh$lambda$10(ConnectionsView.this, (JSONObject) obj);
                    return refresh$lambda$10;
                }
            });
        } else {
            set("wifi", "Unknown (tablet not linked)", null);
        }
        RtkStatus r = Ntrip.INSTANCE.getRtk();
        if (r != null) {
            str2 = r.getWords() + " · " + r.getUsed() + " sats" + (r.getHasFix() ? " · ±" + RtkStatus.Companion.accuracy(r.getHAccM(), false) : "");
        }
        str2 = "No receiver seen";
        set("satellite", str2, r != null ? Boolean.valueOf(r.getHasFix()) : null);
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            return;
        }
        g.get("/api/galaxy/hotspot", new Function2() { // from class: com.starpilot.starview.ConnectionsView$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit refresh$lambda$16;
                refresh$lambda$16 = ConnectionsView.refresh$lambda$16(ConnectionsView.this, (JSONObject) obj, (String) obj2);
                return refresh$lambda$16;
            }
        });
        g.get("/api/bluetooth/status", new Function2() { // from class: com.starpilot.starview.ConnectionsView$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit refresh$lambda$18;
                refresh$lambda$18 = ConnectionsView.refresh$lambda$18(ConnectionsView.this, (JSONObject) obj, (String) obj2);
                return refresh$lambda$18;
            }
        });
        g.get("/api/galaxy/status", new Function2() { // from class: com.starpilot.starview.ConnectionsView$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit refresh$lambda$20;
                refresh$lambda$20 = ConnectionsView.refresh$lambda$20(ConnectionsView.this, (JSONObject) obj, (String) obj2);
                return refresh$lambda$20;
            }
        });
        GalaxyPages.get$default(GalaxyPages.INSTANCE, "data", 0L, new Function1() { // from class: com.starpilot.starview.ConnectionsView$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit refresh$lambda$24;
                refresh$lambda$24 = ConnectionsView.refresh$lambda$24(ConnectionsView.this, (JSONObject) obj);
                return refresh$lambda$24;
            }
        }, 2, null);
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0040, code lost:
    
        if (r2 != null) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit refresh$lambda$16(ConnectionsView this$0, JSONObject o, String str) {
        String str2;
        String s;
        String optString;
        if (o != null) {
            if (o.optBoolean("active")) {
                JSONObject optJSONObject = o.optJSONObject("config");
                if (optJSONObject != null && (optString = optJSONObject.optString("ssid")) != null) {
                    String str3 = optString;
                    if (StringsKt.isBlank(str3)) {
                        str3 = null;
                    }
                    String s2 = str3;
                    if (s2 != null) {
                        s = " · " + s2;
                    }
                }
                s = "";
                str2 = "On" + s;
            } else {
                str2 = "Off";
            }
            this$0.set("hotspot", str2, Boolean.valueOf(o.optBoolean("active")));
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
}
