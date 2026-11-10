package com.starpilot.starview;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.GridLayout.LayoutParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class ToolsHomeView extends ScrollView {
   public static final ToolsHomeView.Companion Companion = new ToolsHomeView.Companion(null);
   private static final Map<String, String> DESCRIPTIONS;
   private final HashMap<String, TextView> lines;
   private final Function1<String, Unit> open;
   private final List<GalaxyTools.Tool> tools;
   private final CarUi ui;

   // $VF: synthetic method
   public static Unit $r8$lambda$OTf69NCniz28mXUCCX3t4aWRah4(String var0, TextView var1, JSONObject var2, String var3) {
      return refresh$lambda$6(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$aGExotcvWhjF_KyqY_zYl2s_2ss/* $VF was: $r8$lambda$aGExotcvWhjF_KyqY_zYl2s_2ss*/(ToolsHomeView var0, Triple var1) {
      return _init_$lambda$2(var0, var1);
   }

   static {
      Pair var0 = TuplesKt.to("/", "Drives, distance, last drive");
      Pair var3 = TuplesKt.to("/recordings", "Replay, export MP4, keep");
      Pair var1 = TuplesKt.to("/manage_models", "Download and pick models");
      Pair var6 = TuplesKt.to("/model_laboratory", "Compare model variants");
      Pair var8 = TuplesKt.to("/logs", "Live console, crashes");
      Pair var4 = TuplesKt.to("/tuning/plots", "Live control plots");
      Pair var5 = TuplesKt.to("/navigation", "Offline maps, places");
      Pair var7 = TuplesKt.to("/bluetooth", "Pair, connect, forget");
      Pair var2 = TuplesKt.to("/galaxy", "Remote access, PIN");
      DESCRIPTIONS = MapsKt.mapOf(new Pair[]{var0, var3, var1, var6, var8, var4, var5, var7, var2, TuplesKt.to("/android-auto", "Phone projection")});
   }

   public ToolsHomeView(Context var1, CarUi var2, List<GalaxyTools.Tool> var3, Function1<? super String, Unit> var4) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "tools");
      Intrinsics.checkNotNullParameter(var4, "open");
      super(var1);
      this.ui = var2;
      this.tools = var3;
      this.open = var4;
      this.lines = new HashMap<>();
      GridLayout var7 = new GridLayout(var1);
      var7.setColumnCount(4);
      var7.setPadding(this.ui.dp(2), this.ui.dp(2), this.ui.dp(2), this.ui.dp(30));
      Iterable var11 = this.tools;
      var var18 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var11, 10));

      for (GalaxyTools.Tool var8 : var11) {
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
         if (StringsKt.startsWith$default((String)var30.getThird(), "#", false, 2, null)) {
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
         LayoutParams var22 = new LayoutParams(GridLayout.spec(var5 / 4), GridLayout.spec(var5 % 4, 1.0F));
         var22.width = 0;
         var22.setMargins(this.ui.dp(7), this.ui.dp(7), this.ui.dp(7), this.ui.dp(7));
         Unit var29 = Unit.INSTANCE;
         var7.addView(var17, (android.view.ViewGroup.LayoutParams)var22);
      }

      this.addView((View)var7);
   }

   private static final Unit _init_$lambda$2(ToolsHomeView var0, Triple var1) {
      var0.open.invoke((String)var1.getSecond());
      return Unit.INSTANCE;
   }

   private static final Unit refresh$lambda$6(String var0, TextView var1, JSONObject var2, String var3) {
      if (var2 != null) {
         var0 = ToolLines.INSTANCE.line(var0, var2);
         if (var0 != null) {
            var1.setText(var0);
         }
      }

      return Unit.INSTANCE;
   }

   public final void refresh() {
      Galaxy7 var1 = Galaxy7.Companion.current();
      if (var1 != null) {
         for (Entry var4 : this.lines.entrySet()) {
            String var3 = (String)var4.getKey();
            TextView var6 = (TextView)var4.getValue();
            String var5 = ToolLines.INSTANCE.endpoint(var3);
            if (var5 != null) {
               var1.get(var5, new ToolsHomeView$$ExternalSyntheticLambda0(var3, var6));
            }
         }
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final Map<String, String> getDESCRIPTIONS() {
         return ToolsHomeView.DESCRIPTIONS;
      }
   }
}
