package com.starpilot.starview;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starpilot.starview.GalaxyTools;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import android.view.View;
import android.widget.GridLayout.LayoutParams;

/* compiled from: DeviceHubs.kt */
public final class ToolsHomeView extends ScrollView {
    public static final Companion Companion = new Companion(null);
    private static final Map<String, String> DESCRIPTIONS = MapsKt.mapOf(TuplesKt.to("/", "Drives, distance, last drive"), TuplesKt.to("/recordings", "Replay, export MP4, keep"), TuplesKt.to("/manage_models", "Download and pick models"), TuplesKt.to("/model_laboratory", "Compare model variants"), TuplesKt.to("/logs", "Live console, crashes"), TuplesKt.to("/tuning/plots", "Live control plots"), TuplesKt.to("/navigation", "Offline maps, places"), TuplesKt.to("/bluetooth", "Pair, connect, forget"), TuplesKt.to("/galaxy", "Remote access, PIN"), TuplesKt.to("/android-auto", "Phone projection"));
    private final HashMap<String, TextView> lines;
    private final Function1<String, Unit> open;
    private final List<GalaxyTools.Tool> tools;
    private final CarUi ui;

    /* VF: from Vineflower (jadx: ) */
    public ToolsHomeView(Context var1, CarUi var2, List<GalaxyTools.Tool> var3, Function1<? super String, Unit> var4) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        Intrinsics.checkNotNullParameter(var3, "tools");
        Intrinsics.checkNotNullParameter(var4, "open");
        this.ui = var2;
        this.tools = var3;
        this.open = var4;
        this.lines = new HashMap<>();
        GridLayout var7 = new GridLayout(var1);
        var7.setColumnCount(4);
        var7.setPadding(this.ui.dp(2), this.ui.dp(2), this.ui.dp(2), this.ui.dp(30));
        Iterable var11 = this.tools;
        var var18 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var11, 10));

        for (GalaxyTools.Tool var8 : ((Tool) var11)) {
            String var9 = var8.getName();
            String var25 = var8.getPath();
            String var6 = var8.getName();
            var18.add(new Triple<>(var9, "gweb:" + var25 + "|" + var6, var8.getPath()));
        }

        List var12 = var18;
        List var26 = CollectionsKt.plus(
            var12, CollectionsKt.listOf(new Triple[]{new Triple<>("Terminal", "native:terminal", "#terminal"), new Triple<>("Files", "activity:files", "#files")})
        );
        Iterator var24 = var26.iterator();

        for (int var5 = 0; var24.hasNext(); var5++) {
            Triple var30 = (Triple)var24.next();
            String var13 = (String)var30.getThird();
            String var19;
            if (Intrinsics.areEqual(var13, "#terminal")) {
                var19 = "terminal";
            } else if (Intrinsics.areEqual(var13, "#files")) {
                var19 = "folder";
            } else {
                var19 = SettingsActivity.Companion.toolIcon((String)var30.getThird());
            }

            String var14 = (String)var30.getThird();
            String var15;
            if (Intrinsics.areEqual(var14, "#terminal")) {
                var15 = "Shell tabs on the comma, saved commands";
            } else if (Intrinsics.areEqual(var14, "#files")) {
                var15 = "Comma ↔ tablet, both ways";
            } else {
                String var27 = DESCRIPTIONS.get(var30.getThird());
                var15 = var27;
                if (var27 == null) {
                    var15 = "Galaxy tool";
                }
            }

            CarUi var31 = this.ui;
            String var10 = (String)var30.getFirst();
            String var28;
            if (StringsKt.startsWith((String)var30.getThird(), "#", false)) {
                var28 = "tablet";
            } else {
                var28 = "device";
            }

            Pair var20 = var31.iconTile(var10, var15, var19, var28, new ToolsHomeView$$ExternalSyntheticLambda1(this, var30));
            LinearLayout var16 = (LinearLayout)var20.component1();
            TextView var21 = (TextView)var20.component2();
            var16.setMinimumHeight(this.ui.dp(168));
            this.lines.put((String)var30.getThird(), var21);
            View var17 = (View)var16;
            android.widget.GridLayout.LayoutParams var22 = new LayoutParams(GridLayout.spec(var5 / 4), GridLayout.spec(var5 % 4, 1.0F));
            var22.width = 0;
            var22.setMargins(this.ui.dp(7), this.ui.dp(7), this.ui.dp(7), this.ui.dp(7));
            Unit var29 = Unit.INSTANCE;
            var7.addView(var17, (android.view.ViewGroup.LayoutParams)var22);
        }

        this.addView((View)var7);
    }

    /* VF: from Vineflower (jadx: ) */
    public static final Unit _init_$lambda$2(ToolsHomeView var0, Triple var1) {
        var0.open.invoke((String)var1.getSecond());
        return Unit.INSTANCE;
    }

    public final void refresh() {
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            return;
        }
        for (Map.Entry<String, TextView> entry : this.lines.entrySet()) {
            final String path = entry.getKey();
            final TextView tv = entry.getValue();
            String ep = ToolLines.INSTANCE.endpoint(path);
            if (ep != null) {
                g.get(ep, new Function2() { // from class: com.starpilot.starview.ToolsHomeView$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Unit refresh$lambda$6;
                        refresh$lambda$6 = ToolsHomeView.refresh$lambda$6(path, tv, (JSONObject) obj, (String) obj2);
                        return refresh$lambda$6;
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$6(String $path, TextView $tv, JSONObject o, String str) {
        String it;
        if (o != null && (it = ToolLines.INSTANCE.line($path, o)) != null) {
            $tv.setText(it);
        }
        return Unit.INSTANCE;
    }

    /* compiled from: DeviceHubs.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<String, String> getDESCRIPTIONS() {
            return ToolsHomeView.DESCRIPTIONS;
        }
    }
}
