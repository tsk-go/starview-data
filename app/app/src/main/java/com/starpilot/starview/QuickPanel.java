package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public final class QuickPanel extends FrameLayout {
   private final QuickPanel.Actions a;
   private final GridLayout grid;
   private final CarUi ui;

   // $VF: synthetic method
   public static void $r8$lambda$6SAzYfKec7BqMdh38GHytKz68OE(Function0 var0, LinearLayout var1, QuickPanel var2, View var3) {
      tile$lambda$13$lambda$12(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AOg_cNsc_fe6lKfDX9_QdHgVQKY/* $VF was: $r8$lambda$AOg_cNsc_fe6lKfDX9_QdHgVQKY*/(QuickPanel var0) {
      return paint$lambda$23(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$CduCVrO17IVsi8W519gBkMMejDg(QuickPanel var0, View var1) {
      _init_$lambda$1(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$HgdvV_WlKBDk_ZqRs_cq7VwABFw/* $VF was: $r8$lambda$HgdvV_WlKBDk_ZqRs_cq7VwABFw*/(QuickPanel var0) {
      return paint$lambda$14(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JEq6kSaxQnQXvvF_wvsYyUpzH8I() {
      return paint$lambda$20();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$VKoXvJK5xxruV6BORoZx2EeTYRI(QuickPanel var0) {
      return paint$lambda$22(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Yc9Saflj8fg3oheaGd8LFdXv7Go(QuickPanel var0) {
      return paint$lambda$19(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_9vJFMcyU4hU5aWFQ8YwK04cHy4(QuickPanel var0) {
      tile$lambda$13$lambda$12$lambda$11(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fOzhSM_4DfU4gzgcI7BExquAFnU/* $VF was: $r8$lambda$fOzhSM_4DfU4gzgcI7BExquAFnU*/(QuickPanel var0, int var1) {
      return paint$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lUkGwl0RZPa7mEJPGcvJ6b667tY(QuickPanel var0) {
      return paint$lambda$18(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mJpaOissPOMUUqjOahW7EXRss0c(QuickPanel var0) {
      return paint$lambda$24(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$o7bPnO9xuvDRE8Khvi_7sGsbg0I/* $VF was: $r8$lambda$o7bPnO9xuvDRE8Khvi_7sGsbg0I*/(QuickPanel var0) {
      return paint$lambda$21(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tchbnA8ErSi7TC4Xl0lwa_Ce8Xk(QuickPanel var0) {
      return paint$lambda$25(var0);
   }

   public QuickPanel(Context var1, QuickPanel.Actions var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "a");
      super(var1);
      this.a = var2;
      this.ui = new CarUi(var1, false, 2, null);
      GridLayout var7 = new GridLayout(var1);
      var7.setColumnCount(3);
      this.grid = var7;
      this.setBackgroundColor(Color.argb(215, 6, 8, 12));
      this.setClickable(true);
      this.setTranslationZ(this.ui.dp(150));
      this.setOnClickListener(new QuickPanel$$ExternalSyntheticLambda3(this));
      LinearLayout var8 = new LinearLayout(var1);
      var8.setOrientation(1);
      var8.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getFRAME(), 26, null, 4, null));
      var8.setPadding(this.ui.dp(24), this.ui.dp(20), this.ui.dp(24), this.ui.dp(24));
      var8.setClickable(true);
      LinearLayout var3 = new LinearLayout(var1);
      var3.setOrientation(0);
      var3.setGravity(16);
      TextView var5 = new TextView(var1);
      var5.setText("Quick settings");
      var5.setTextColor(CarUi.Companion.getTEXT());
      var5.setTextSize(26.0F);
      var5.setTypeface(Typeface.DEFAULT_BOLD);
      var3.addView((View)var5, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      var3.addView((View)this.ui.button("Close", true, new QuickPanel$$ExternalSyntheticLambda4(this)));
      var8.addView((View)var3);
      View var4 = (View)this.grid;
      android.widget.LinearLayout.LayoutParams var6 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var6.topMargin = this.ui.dp(14);
      Unit var9 = Unit.INSTANCE;
      var8.addView(var4, (LayoutParams)var6);
      this.addView((View)var8, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(this.ui.dp(920), -2, 17)));
      this.paint();
   }

   private static final void _init_$lambda$1(QuickPanel var0, View var1) {
      var0.a.close();
   }

   static final Unit lambda$5$lambda$4(QuickPanel var0) {
      var0.a.close();
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$14(QuickPanel var0) {
      var0.a.toggleExperimental();
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$17(QuickPanel var0, int var1) {
      var0.a.setPersonality((var1 + 1) % 3);
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$18(QuickPanel var0) {
      var0.a.toggleFocus();
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$19(QuickPanel var0) {
      var0.a.toggleMap();
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$20() {
      Speech.INSTANCE.setEnabled(Speech.INSTANCE.getEnabled() ^ true);
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$21(QuickPanel var0) {
      var0.a.toggleScene();
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$22(QuickPanel var0) {
      var0.keepDrive();
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$23(QuickPanel var0) {
      var0.a.toggleSaver();
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$24(QuickPanel var0) {
      var0.a.door("lock");
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$25(QuickPanel var0) {
      var0.a.door("unlock");
      return Unit.INSTANCE;
   }

   private final View tile(String var1, String var2, String var3, String var4, Boolean var5, Function0<Unit> var6) {
      boolean var11 = Intrinsics.areEqual(var5, true);
      LinearLayout var12 = new LinearLayout(this.getContext());
      var12.setOrientation(0);
      var12.setGravity(16);
      var12.setMinimumHeight(this.ui.dp(112));
      var12.setPadding(this.ui.dp(18), this.ui.dp(14), this.ui.dp(14), this.ui.dp(14));
      CarUi var13 = this.ui;
      int var7;
      if (var11) {
         var7 = Color.rgb(19, 34, 25);
      } else {
         var7 = CarUi.Companion.getCARD();
      }

      byte var8;
      byte var9;
      byte var10;
      if (var11) {
         var10 = 107;
         var8 = 75;
         var9 = 43;
      } else {
         var10 = 46;
         var8 = 58;
         var9 = 40;
      }

      var12.setBackground((Drawable)var13.rounded(var7, 20, Color.rgb(var9, var10, var8)));
      SvIcon var14 = SvIcon.INSTANCE;
      Context var24 = var12.getContext();
      Intrinsics.checkNotNullExpressionValue(var24, "getContext(...)");
      SvIcon.State var21;
      if (Intrinsics.areEqual(var5, false)) {
         var21 = SvIcon.State.OFF;
      } else {
         var21 = SvIcon.State.ON;
      }

      View var22 = var14.chip(var24, var3, var4, 56, var21);
      android.widget.LinearLayout.LayoutParams var19 = new android.widget.LinearLayout.LayoutParams(this.ui.dp(56), this.ui.dp(56));
      var19.rightMargin = this.ui.dp(14);
      Unit var17 = Unit.INSTANCE;
      var12.addView(var22, (LayoutParams)var19);
      LinearLayout var18 = new LinearLayout(var12.getContext());
      var18.setOrientation(1);
      TextView var20 = new TextView(var18.getContext());
      var20.setText(var1);
      var20.setTextColor(CarUi.Companion.getTEXT());
      var20.setTextSize(21.0F);
      var20.setTypeface(Typeface.DEFAULT_BOLD);
      var18.addView((View)var20);
      TextView var15 = new TextView(var18.getContext());
      var15.setText(var2);
      CarUi.Companion var16 = CarUi.Companion;
      if (var11) {
         var7 = var16.getACCENT();
      } else {
         var7 = var16.getDIM();
      }

      var15.setTextColor(var7);
      var15.setTextSize(16.0F);
      var18.addView((View)var15);
      var12.addView((View)var18, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      var12.setClickable(true);
      var12.setOnClickListener(new QuickPanel$$ExternalSyntheticLambda5(var6, var12, this));
      return (View)var12;
   }

   private static final void tile$lambda$13$lambda$12(Function0 var0, LinearLayout var1, QuickPanel var2, View var3) {
      var0.invoke();
      var1.postDelayed(new QuickPanel$$ExternalSyntheticLambda0(var2), 400L);
   }

   private static final void tile$lambda$13$lambda$12$lambda$11(QuickPanel var0) {
      var0.paint();
   }

   private final void toast(String var1) {
      Toast.makeText(this.getContext(), var1, 1).show();
   }

   public final void keepDrive() {
      KeepDrive var1 = KeepDrive.INSTANCE;
      Context var2 = this.getContext();
      Intrinsics.checkNotNullExpressionValue(var2, "getContext(...)");
      var1.keepNewest(var2);
   }

   public final void paint() {
      this.grid.removeAllViews();
      int var2 = this.a.personality();
      List var5 = CollectionsKt.listOf(new String[]{"Aggressive", "Standard", "Relaxed"});
      String var3;
      if (this.a.experimental()) {
         var3 = "On";
      } else {
         var3 = "Off - Chill";
      }

      View var4 = this.tile("Experimental", var3, "conditional", "speed", this.a.experimental(), new QuickPanel$$ExternalSyntheticLambda6(this));
      boolean var1;
      if (var2 >= 0 && var2 < var5.size()) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      if (var1) {
         var3 = (String)var5.get(var2);
      } else {
         var3 = "–";
      }

      String var26 = var3 + "  ·  tap for next";
      var3 = "standard";
      List var6 = CollectionsKt.listOf(new String[]{"aggressive", "standard", "relaxed"});
      if (var2 >= 0 && var2 < var6.size()) {
         var1 = (boolean)1;
      } else {
         var1 = (boolean)0;
      }

      if (var1) {
         var3 = (String)var6.get(var2);
      }

      View var27 = this.tile("Following", var26, var3, "person", null, new QuickPanel$$ExternalSyntheticLambda7(this, var2));
      if (this.a.focus()) {
         var3 = "On - only what matters";
      } else {
         var3 = "Off - all widgets";
      }

      View var29 = this.tile("Highway focus", var3, "eye", "display", this.a.focus(), new QuickPanel$$ExternalSyntheticLambda8(this));
      if (this.a.mapOpen()) {
         var3 = "Open";
      } else {
         var3 = "Closed";
      }

      View var7 = this.tile("Map", var3, "map", "tablet", this.a.mapOpen(), new QuickPanel$$ExternalSyntheticLambda9(this));
      if (Speech.INSTANCE.getEnabled()) {
         var3 = "Speaks alerts and turns";
      } else {
         var3 = "Chimes only";
      }

      View var8 = this.tile("Voice", var3, "speaker", "sound", Speech.INSTANCE.getEnabled(), new QuickPanel$$ExternalSyntheticLambda10());
      if (this.a.sceneOn()) {
         var3 = "Scene (3D)";
      } else {
         var3 = "Camera";
      }

      View var9 = this.tile("View", var3, "camera", "cam", null, new QuickPanel$$ExternalSyntheticLambda11(this));
      View var10 = this.tile("Keep this drive", "Galaxy keeps its recording", "bookmark", "display", null, new QuickPanel$$ExternalSyntheticLambda12(this));
      if (this.a.saver()) {
         var3 = "On - other tablet apps asleep";
      } else {
         var3 = "Off";
      }

      View var12 = this.tile("Battery saver", var3, "battery", "tablet", this.a.saver(), new QuickPanel$$ExternalSyntheticLambda13(this));
      View var23 = this.tile("Lock doors", "In Park", "lock", "device", null, new QuickPanel$$ExternalSyntheticLambda1(this));
      View var11 = this.tile("Unlock doors", "In Park", "lock", "device", null, new QuickPanel$$ExternalSyntheticLambda2(this));
      List var24 = CollectionsKt.listOf(new View[]{var4, var27, var29, var7, var8, var9, var10, var12, var23, var11});
      Iterator var25 = var24.iterator();
      var1 = 0;

      while (true) {
         var2 = var1;
         if (!var25.hasNext()) {
            return;
         }

         var1++;
         var8 = (View)var25.next();
         GridLayout var28 = this.grid;
         android.widget.GridLayout.LayoutParams var31 = new android.widget.GridLayout.LayoutParams(GridLayout.spec(var2 / 3), GridLayout.spec(var2 % 3, 1.0F));
         var31.width = 0;
         var31.setMargins(this.ui.dp(7), this.ui.dp(7), this.ui.dp(7), this.ui.dp(7));
         Unit var30 = Unit.INSTANCE;
         var28.addView(var8, (LayoutParams)var31);
      }
   }

   public interface Actions {
      void close();

      void door(String var1);

      boolean experimental();

      boolean focus();

      void listen();

      boolean mapOpen();

      int personality();

      boolean saver();

      boolean sceneOn();

      void setPersonality(int var1);

      void toggleExperimental();

      void toggleFocus();

      void toggleMap();

      void toggleSaver();

      void toggleScene();
   }
}
