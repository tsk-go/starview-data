package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class DrivingMapView extends ScrollView {
   private static final List<Pair<String, List<DrivingMapView.Card>>> COLUMNS;
   public static final DrivingMapView.Companion Companion = new DrivingMapView.Companion(null);
   private HashMap<String, String> hubTitles;
   private final Function2<String, String, Unit> open;
   private final HashMap<String, DrivingMapView.Shown> shown;
   private final CarUi ui;

   // $VF: synthetic method
   public static Unit $r8$lambda$0M0i3rdvKJGODfb55024GqXXMo4(DrivingMapView var0, JSONObject var1) {
      return refresh$lambda$13(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6mBrzrBgza2SM0NMEdvyHivLk8g(DrivingMapView var0, String var1, SettingsIndex.Summary var2) {
      return refresh$lambda$14(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$LBvSRQe2l6zWd_1RtA2m5tKMvLY/* $VF was: $r8$lambda$LBvSRQe2l6zWd_1RtA2m5tKMvLY*/(
      DrivingMapView var0, DrivingMapView.Card var1, View var2
   ) {
      card$lambda$10$lambda$9(var0, var1, var2);
   }

   static {
      Pair var0 = TuplesKt.to(
         "Speed",
         CollectionsKt.listOf(
            new DrivingMapView.Card[]{
               new DrivingMapView.Card("profiles", "Long Planner"),
               new DrivingMapView.Card("slc", "Speed Limit Controller"),
               new DrivingMapView.Card("curve", "Curve Speed Controller"),
               new DrivingMapView.Card("conditional", "Conditional Driving Modes")
            }
         )
      );
      Pair var1 = TuplesKt.to(
         "Steering",
         CollectionsKt.listOf(
            new DrivingMapView.Card[]{
               new DrivingMapView.Card("lane", "Lane Centering"),
               new DrivingMapView.Card("lane_change", "Lane Changes"),
               new DrivingMapView.Card("torque", "Steering and Torque"),
               new DrivingMapView.Card("aol", "Always On Lateral")
            }
         )
      );
      Pair var2 = TuplesKt.to(
         "Personalities",
         CollectionsKt.listOf(
            new DrivingMapView.Card[]{
               new DrivingMapView.Card("traffic", "Traffic"),
               new DrivingMapView.Card("aggressive", "Aggressive"),
               new DrivingMapView.Card("standard", "Standard"),
               new DrivingMapView.Card("relaxed", "Relaxed")
            }
         )
      );
      Pair var3 = TuplesKt.to("Buttons", CollectionsKt.listOf(new DrivingMapView.Card("wheel", "Wheel Controls")));
      COLUMNS = CollectionsKt.listOf(new Pair[]{var0, var1, var2, var3});
   }

   public DrivingMapView(Context var1, CarUi var2, Function2<? super String, ? super String, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "open");
      super(var1);
      this.ui = var2;
      this.open = var3;
      this.shown = new HashMap<>();
      this.hubTitles = new HashMap<>();
      LinearLayout var10 = new LinearLayout(var1);
      var10.setOrientation(0);
      var10.setPadding(this.ui.dp(4), this.ui.dp(4), this.ui.dp(4), this.ui.dp(40));

      for (Pair var4 : COLUMNS) {
         String var5 = (String)var4.component1();
         List var6 = (List)var4.component2();
         LinearLayout var11 = new LinearLayout(var1);
         var11.setOrientation(1);
         TextView var13 = this.ui.header(var5);
         var13.setPadding(this.ui.dp(6), this.ui.dp(4), 0, this.ui.dp(8));
         var11.addView((View)var13);
         Iterator var16 = var6.iterator();

         while (var16.hasNext()) {
            View var8 = this.card((DrivingMapView.Card)var16.next());
            LayoutParams var7 = new LayoutParams(-1, -2);
            var7.bottomMargin = this.ui.dp(10);
            Unit var14 = Unit.INSTANCE;
            var11.addView(var8, (android.view.ViewGroup.LayoutParams)var7);
         }

         View var12 = (View)var11;
         LayoutParams var15 = new LayoutParams(0, -2, 1.0F);
         var15.leftMargin = this.ui.dp(6);
         var15.rightMargin = this.ui.dp(6);
         Unit var17 = Unit.INSTANCE;
         var10.addView(var12, (android.view.ViewGroup.LayoutParams)var15);
      }

      this.addView((View)var10);
   }

   private final View card(DrivingMapView.Card var1) {
      SettingIcons.Icon var2 = SettingIcons.INSTANCE.forPage(var1.getPage());
      SvIcon var3 = SvIcon.INSTANCE;
      Context var4 = this.getContext();
      Intrinsics.checkNotNullExpressionValue(var4, "getContext(...)");
      View var9 = SvIcon.chip$default(var3, var4, var2.getName(), var2.getGroup(), 46, null, 16, null);
      Intrinsics.checkNotNull(var9, "null cannot be cast to non-null type com.starpilot.starview.SvIcon.Chip");
      SvIcon.Chip var5 = (SvIcon.Chip)var9;
      TextView var11 = new TextView(this.getContext());
      var11.setText(var1.getTitle());
      var11.setTextColor(CarUi.Companion.getTEXT());
      var11.setTextSize(19.0F);
      var11.setTypeface(Typeface.DEFAULT_BOLD);
      TextView var12 = new TextView(this.getContext());
      var12.setText("…");
      var12.setTextColor(CarUi.Companion.getDIM());
      var12.setTextSize(15.0F);
      var12.setMaxLines(2);
      LinearLayout var10 = new LinearLayout(this.getContext());
      var10.setOrientation(0);
      var10.setGravity(16);
      var10.setPadding(this.ui.dp(14), this.ui.dp(14), this.ui.dp(12), this.ui.dp(14));
      var10.setBackground((Drawable)this.ui.rounded(CarUi.Companion.getCARD(), 18, Color.rgb(34, 40, 55)));
      var10.setMinimumHeight(this.ui.dp(92));
      View var6 = var5;
      LayoutParams var8 = new LayoutParams(this.ui.dp(46), this.ui.dp(46));
      var8.rightMargin = this.ui.dp(14);
      Unit var7 = Unit.INSTANCE;
      var10.addView(var6, (android.view.ViewGroup.LayoutParams)var8);
      LinearLayout var13 = new LinearLayout(var10.getContext());
      var13.setOrientation(1);
      var13.addView((View)var11);
      var13.addView((View)var12);
      var10.addView((View)var13, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      var10.setClickable(true);
      var10.setOnClickListener(new DrivingMapView$$ExternalSyntheticLambda0(this, var1));
      this.shown.put(var1.getPage(), new DrivingMapView.Shown(var10, var5, var12));
      return (View)var10;
   }

   private static final void card$lambda$10$lambda$9(DrivingMapView var0, DrivingMapView.Card var1, View var2) {
      Function2 var3 = var0.open;
      String var4 = var1.getPage();
      String var6 = var0.hubTitles.get(var1.getPage());
      String var5 = var6;
      if (var6 == null) {
         var5 = var1.getTitle();
      }

      var3.invoke(var4, var5);
   }

   private static final Unit refresh$lambda$13(DrivingMapView var0, JSONObject var1) {
      if (var1 != null) {
         for (GalaxyRow var4 : GalaxyRow.Companion.parse(var1)) {
            boolean var2;
            if (var4.getPage().length() > 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (var2) {
               var0.hubTitles.put(var4.getPage(), var4.getLabel());
            }
         }
      }

      return Unit.INSTANCE;
   }

   private static final Unit refresh$lambda$14(DrivingMapView var0, String var1, SettingsIndex.Summary var2) {
      var0.paint(var1, var2);
      return Unit.INSTANCE;
   }

   public final void paint(String var1, SettingsIndex.Summary var2) {
      Intrinsics.checkNotNullParameter(var1, "page");
      DrivingMapView.Shown var5 = this.shown.get(var1);
      if (var5 != null) {
         Object var3;
         TextView var6;
         label42: {
            var6 = var5.getLine();
            var3 = null;
            if (var2 != null) {
               var1 = var2.getLine();
               if (var1 != null) {
                  CharSequence var4 = var1;
                  CharSequence var8 = var4;
                  if (StringsKt.isBlank(var4)) {
                     var8 = null;
                  }

                  var1 = (String)var8;
                  if (var1 != null) {
                     break label42;
                  }
               }
            }

            if (var2 == null) {
               var1 = "Not available";
            } else {
               var1 = "Open to adjust";
            }
         }

         var6.setText(var1);
         Boolean var10 = (Boolean)var3;
         if (var2 != null) {
            var10 = var2.getOn();
         }

         LinearLayout var14 = var5.getBox();
         if (Intrinsics.areEqual(var10, true)) {
            var3 = this.ui.rounded(Color.rgb(19, 34, 25), 18, Color.rgb(43, 107, 75));
         } else {
            var3 = this.ui.rounded(CarUi.Companion.getCARD(), 18, Color.rgb(34, 40, 55));
         }

         var14.setBackground((Drawable)var3);
         var3 = var5.getChip();
         SvIcon.State var11;
         if (Intrinsics.areEqual(var10, false)) {
            var11 = SvIcon.State.OFF;
         } else if (var2 == null) {
            var11 = SvIcon.State.NA;
         } else {
            var11 = SvIcon.State.ON;
         }

         SvIcon.Chip.set$default((SvIcon.Chip)var3, null, null, var11, 3, null);
      }
   }

   public final void refresh() {
      GalaxyPages.get$default(GalaxyPages.INSTANCE, "hub", 0L, new DrivingMapView$$ExternalSyntheticLambda1(this), 2, null);
      Set var1 = this.shown.keySet();
      Intrinsics.checkNotNullExpressionValue(var1, "<get-keys>(...)");

      for (String var2 : CollectionsKt.toList(var1)) {
         GalaxyPages.INSTANCE.summary(var2, new DrivingMapView$$ExternalSyntheticLambda2(this, var2));
      }
   }

   public static final class Card {
      private final String page;
      private final String title;

      public Card(String var1, String var2) {
         Intrinsics.checkNotNullParameter(var1, "page");
         Intrinsics.checkNotNullParameter(var2, "title");
         super();
         this.page = var1;
         this.title = var2;
      }

      public final String getPage() {
         return this.page;
      }

      public final String getTitle() {
         return this.title;
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final List<Pair<String, List<DrivingMapView.Card>>> getCOLUMNS() {
         return DrivingMapView.COLUMNS;
      }
   }

   private static final class Shown {
      private final LinearLayout box;
      private final SvIcon.Chip chip;
      private final TextView line;

      public Shown(LinearLayout var1, SvIcon.Chip var2, TextView var3) {
         Intrinsics.checkNotNullParameter(var1, "box");
         Intrinsics.checkNotNullParameter(var2, "chip");
         Intrinsics.checkNotNullParameter(var3, "line");
         super();
         this.box = var1;
         this.chip = var2;
         this.line = var3;
      }

      public final LinearLayout getBox() {
         return this.box;
      }

      public final SvIcon.Chip getChip() {
         return this.chip;
      }

      public final TextView getLine() {
         return this.line;
      }
   }
}
