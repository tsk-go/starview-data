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

/* compiled from: DeviceHubs.kt */
public final class ToolsHomeView extends ScrollView {
    public static final Companion Companion = new Companion(null);
    private static final Map<String, String> DESCRIPTIONS = MapsKt.mapOf(TuplesKt.to("/", "Drives, distance, last drive"), TuplesKt.to("/recordings", "Replay, export MP4, keep"), TuplesKt.to("/manage_models", "Download and pick models"), TuplesKt.to("/model_laboratory", "Compare model variants"), TuplesKt.to("/logs", "Live console, crashes"), TuplesKt.to("/tuning/plots", "Live control plots"), TuplesKt.to("/navigation", "Offline maps, places"), TuplesKt.to("/bluetooth", "Pair, connect, forget"), TuplesKt.to("/galaxy", "Remote access, PIN"), TuplesKt.to("/android-auto", "Phone projection"));
    private final HashMap<String, TextView> lines;
    private final Function1<String, Unit> open;
    private final List<GalaxyTools.Tool> tools;
    private final CarUi ui;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ToolsHomeView(Context ctx, CarUi ui, List<GalaxyTools.Tool> tools, Function1<? super String, Unit> open) {
        super(ctx);
        String str;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        Intrinsics.checkNotNullParameter(tools, "tools");
        Intrinsics.checkNotNullParameter(open, "open");
        this.ui = ui;
        this.tools = tools;
        this.open = open;
        this.lines = new HashMap<>();
        GridLayout grid = new GridLayout(ctx);
        grid.setColumnCount(4);
        grid.setPadding(this.ui.dp(2), this.ui.dp(2), this.ui.dp(2), this.ui.dp(30));
        Iterable $this$map$iv = this.tools;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            GalaxyTools.Tool it = (GalaxyTools.Tool) item$iv$iv;
            destination$iv$iv.add(new Triple(it.getName(), "gweb:" + it.getPath() + "|" + it.getName(), it.getPath()));
            $this$map$iv = $this$map$iv;
        }
        boolean z = false;
        int i = 1;
        List all = CollectionsKt.plus(destination$iv$iv, (Iterable) CollectionsKt.listOf((Object[]) new Triple[]{new Triple("Terminal", "native:terminal", "#terminal"), new Triple("Files", "activity:files", "#files")}));
        Iterator it2 = all.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            int i3 = i2;
            int i4 = i2 + i;
            final Triple t = (Triple) it2.next();
            String str2 = (String) t.getThird();
            String icon = Intrinsics.areEqual(str2, "#terminal") ? "terminal" : Intrinsics.areEqual(str2, "#files") ? "folder" : SettingsActivity.Companion.toolIcon((String) t.getThird());
            String str3 = (String) t.getThird();
            if (Intrinsics.areEqual(str3, "#terminal")) {
                str = "Shell tabs on the comma, saved commands";
            } else if (Intrinsics.areEqual(str3, "#files")) {
                str = "Comma ↔ tablet, both ways";
            } else {
                str = DESCRIPTIONS.get(t.getThird());
                if (str == null) {
                    str = "Galaxy tool";
                }
            }
            String desc = str;
            Pair<LinearLayout, TextView> iconTile = this.ui.iconTile((String) t.getFirst(), desc, icon, StringsKt.startsWith((String) t.getThird(), "#", false) ? "tablet" : "device", new Function0() { // from class: com.starpilot.starview.ToolsHomeView$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$2;
                    _init_$lambda$2 = ToolsHomeView._init_$lambda$2(ToolsHomeView.this, t);
                    return _init_$lambda$2;
                }
            });
            LinearLayout tile = iconTile.component1();
            TextView sub = iconTile.component2();
            tile.setMinimumHeight(this.ui.dp(168));
            this.lines.put(t.getThird(), sub);
            GridLayout.LayoutParams $this$_init__u24lambda_u243 = new GridLayout.LayoutParams(GridLayout.spec(i3 / 4), GridLayout.spec(i3 % 4, 1.0f));
            $this$_init__u24lambda_u243.width = 0;
            List all2 = all;
            $this$_init__u24lambda_u243.setMargins(this.ui.dp(7), this.ui.dp(7), this.ui.dp(7), this.ui.dp(7));
            Unit unit = Unit.INSTANCE;
            grid.addView(tile, $this$_init__u24lambda_u243);
            all = all2;
            it2 = it2;
            i2 = i4;
            z = false;
            i = 1;
        }
        addView(grid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Unit _init_$lambda$2(ToolsHomeView this$0, Triple $t) {
        this$0.open.invoke($t.getSecond());
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
