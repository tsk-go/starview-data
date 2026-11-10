package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class ConnectionsView extends ScrollView {
   private final HashMap<String, ConnectionsView.Card> cards;
   private final Handler main;
   private final Function1<String, Unit> open;
   private final CarUi ui;

   // $VF: synthetic method
   public static Unit $r8$lambda$6upwz_amlUU2u4quftqZ1UeSDX0/* $VF was: $r8$lambda$6upwz_amlUU2u4quftqZ1UeSDX0*/(ConnectionsView var0, JSONObject var1) {
      return refresh$lambda$24(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AJHONcKdwezw5tv5K9_hyfN2AdA/* $VF was: $r8$lambda$AJHONcKdwezw5tv5K9_hyfN2AdA*/(ConnectionsView var0, JSONObject var1) {
      return refresh$lambda$10(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$O3TNp0Nf3DPU9NiCsagaKoQXa7c(ConnectionsView var0, JSONObject var1, String var2) {
      return refresh$lambda$18(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Qq0VzPJ3Obpl26OeVHkAsw8luG0(ConnectionsView var0, JSONObject var1, String var2) {
      return refresh$lambda$16(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ezJKC0_7P6Ra68A5_J_jbsQJqls/* $VF was: $r8$lambda$ezJKC0_7P6Ra68A5_J_jbsQJqls*/(
      ConnectionsView var0, JSONObject var1, String var2
   ) {
      return refresh$lambda$20(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$gLf1nIT5hGeUKSBhQjpg3D8Hbak(ConnectionsView var0, String var1, String var2, Boolean var3) {
      set$lambda$9(var0, var1, var2, var3);
   }

   public ConnectionsView(Context var1, CarUi var2, Function1<? super String, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "open");
      super(var1);
      this.ui = var2;
      this.open = var3;
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
         LayoutParams var28 = new LayoutParams(this.ui.dp(52), this.ui.dp(52));
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
         var17.addView((View)var26, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
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

   static final void lambda$7$lambda$6(ConnectionsView var0, Triple var1, View var2) {
      var0.open.invoke((String)var1.getThird());
   }

   private static final Unit refresh$lambda$10(ConnectionsView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "st");
      String var5 = var1.optString("ssid");
      boolean var3 = var1.optBoolean("tethering");
      boolean var2 = true;
      Boolean var4 = true;
      if (var3) {
         String var6 = var1.optString("tether_ssid");
         var0.set("wifi", "Tethering · " + var6, var4);
      } else {
         Intrinsics.checkNotNull(var5);
         if (var5.length() <= 0) {
            var2 = false;
         }

         if (var2) {
            String var7 = var1.optString("ip");
            var0.set("wifi", var5 + " · " + var7, var4);
         } else {
            var0.set("wifi", "Not connected", false);
         }
      }

      return Unit.INSTANCE;
   }

   private static final Unit refresh$lambda$16(ConnectionsView var0, JSONObject var1, String var2) {
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

   private static final Unit refresh$lambda$18(ConnectionsView var0, JSONObject var1, String var2) {
      if (var1 != null) {
         String var3 = ToolLines.INSTANCE.line("/bluetooth", var1);
         var2 = var3;
         if (var3 == null) {
            var2 = "–";
         }

         var0.set("bluetooth", var2, var1.optBoolean("powered"));
      }

      return Unit.INSTANCE;
   }

   private static final Unit refresh$lambda$20(ConnectionsView var0, JSONObject var1, String var2) {
      if (var1 != null) {
         if (var1.optBoolean("paired")) {
            var2 = "Paired";
         } else {
            var2 = "Not paired";
         }

         var0.set("star", var2, var1.optBoolean("paired"));
      }

      return Unit.INSTANCE;
   }

   private static final Unit refresh$lambda$24(ConnectionsView var0, JSONObject var1) {
      if (var1 != null) {
         label25: {
            for (var var5_it : GalaxyRow.Companion.parse(var1)) {
               var5 = var5_it;
               boolean var2;
               if (((GalaxyRow)var5).getControl() == GalaxyRow.Control.SWITCH) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               if (var2) {
                  break label25;
               }
            }

            var5 = null;
         }

         GalaxyRow var6 = (GalaxyRow)var5;
         if (var6 != null) {
            String var7 = var6.getLabel();
            String var4 = var6.getValue().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var4, "toLowerCase(...)");
            var0.set("upload", var7 + ": " + var4, Intrinsics.areEqual(var6.getValue(), "On"));
         }
      }

      return Unit.INSTANCE;
   }

   private static final void set$lambda$9(ConnectionsView var0, String var1, String var2, Boolean var3) {
      ConnectionsView.Card var4 = var0.cards.get(var1);
      if (var4 != null) {
         var4.getState().setText(var2);
         SvIcon.Chip var8 = var4.getChip();
         SvIcon.State var6;
         if (Intrinsics.areEqual(var3, true)) {
            var6 = SvIcon.State.ON;
         } else if (Intrinsics.areEqual(var3, false)) {
            var6 = SvIcon.State.OFF;
         } else {
            if (var3 != null) {
               throw new NoWhenBranchMatchedException();
            }

            var6 = SvIcon.State.NA;
         }

         SvIcon.Chip.set$default(var8, null, null, var6, 3, null);
         LinearLayout var7 = var4.getBox();
         GradientDrawable var5;
         if (Intrinsics.areEqual(var3, true)) {
            var5 = var0.ui.rounded(Color.rgb(19, 34, 25), 20, Color.rgb(43, 107, 75));
         } else {
            var5 = CarUi.rounded$default(var0.ui, CarUi.Companion.getCARD(), 20, null, 4, null);
         }

         var7.setBackground((Drawable)var5);
      }
   }

   public final void refresh() {
      DataClient var7 = Link.INSTANCE.getData();
      boolean var2 = true;
      boolean var1;
      if (var7 != null && var7.getConnected()) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      String var5 = "";
      String var11;
      if (var1) {
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
         var1 = (boolean)0;
      }

      Object var18 = null;
      if (var1) {
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

   public final boolean set(String var1, String var2, Boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "id");
      Intrinsics.checkNotNullParameter(var2, "text");
      return this.main.post(new ConnectionsView$$ExternalSyntheticLambda1(this, var1, var2, var3));
   }

   private static final class Card {
      private final LinearLayout box;
      private final SvIcon.Chip chip;
      private final TextView state;

      public Card(SvIcon.Chip var1, TextView var2, LinearLayout var3) {
         Intrinsics.checkNotNullParameter(var1, "chip");
         Intrinsics.checkNotNullParameter(var2, "state");
         Intrinsics.checkNotNullParameter(var3, "box");
         super();
         this.chip = var1;
         this.state = var2;
         this.box = var3;
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
}
