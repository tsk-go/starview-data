package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

public final class RoadDataView extends CardPage {
   private final Runnable poll;

   // $VF: synthetic method
   public static Unit $r8$lambda$9Zq7j28SBsyox3c8l6yiiMteDXc(RoadDataView var0) {
      return paint$lambda$13(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$AvoQ96dn_RXpC62uasM_9F42eMw/* $VF was: $r8$lambda$AvoQ96dn_RXpC62uasM_9F42eMw*/(RoadDataView var0, String var1) {
      paint$lambda$10$lambda$9$lambda$8$lambda$7(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$B8XhQTLTnsFQzh6RCufgpAKynwk() {
      return paint$lambda$2();
   }

   // $VF: synthetic method
   public static void $r8$lambda$EwLkRKSo9x88Gx_f1qPrQWpSTz8(RoadDataView var0) {
      paint$lambda$5$lambda$4$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$H1HKMzeQWFm19vkW9oTv8MKbfWA(RoadDataView var0) {
      return paint$lambda$5(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$PGX69GTX9pcbyepxnyArMU5ceb8(RoadDataView var0, Uri var1) {
      paint$lambda$10$lambda$9$lambda$8(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QAkkxaCw6Xv_u0KA3_RjTfVQOhw/* $VF was: $r8$lambda$QAkkxaCw6Xv_u0KA3_RjTfVQOhw*/(RoadDataView var0) {
      return paint$lambda$5$lambda$4(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$WMvOpOmN_3C_8L96_8bJDuaCTWk/* $VF was: $r8$lambda$WMvOpOmN_3C_8L96_8bJDuaCTWk*/(RoadDataView var0, Uri var1) {
      return paint$lambda$10$lambda$9(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$hvD0SMBh5pMSSmmTgKt2H3h0VtA(RoadDataView var0, String var1) {
      paint$lambda$13$lambda$12$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sO_KgpSQik0M6BGYRDJdeSgoJ1E/* $VF was: $r8$lambda$sO_KgpSQik0M6BGYRDJdeSgoJ1E*/(RoadDataView var0) {
      return paint$lambda$10(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$vXEOm1Ao3IOLGKmQ4R4aEzr_Oxo/* $VF was: $r8$lambda$vXEOm1Ao3IOLGKmQ4R4aEzr_Oxo*/(RoadDataView var0) {
      paint$lambda$13$lambda$12(var0);
   }

   public RoadDataView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
      this.poll = new Runnable() {

         @Override
         public void run() {
            RoadDataView.this.paint();
            if (RoadData.INSTANCE.getBusy()) {
               RoadDataView.this.getMain().postDelayed(this, 1000L);
            }
         }
      };
      this.paint();
   }

   private final void paint() {
      this.getCol().removeAllViews();
      Map var10 = Hpms.INSTANCE.files();
      Iterator var8 = var10.values().iterator();
      long var2 = 0L;

      while (var8.hasNext()) {
         File var7 = (File)var8.next();
         var2 += var7.length();
      }

      LinearLayout var9 = this.card();
      this.add((View)var9);
      var9.addView(this.title("Lane road data", "lane", "tablet"));
      var9.addView(
         (View)this.line(
            "How many lanes every road in the US really has - the government's road survey (FHWA HPMS, 2024). It counts the road's own lanes only, so the lane card can tell a merge or exit lane beside the road from one more lane, and knows tunnels and bridges (a solid line there splits lanes, it's not the road's edge). No new data: the card works as before.",
            CarUi.Companion.getDIM(),
            15.0F
         )
      );
      boolean var6 = var10.isEmpty();
      String var28 = "";
      String var16;
      if (var6) {
         var16 = "On the tablet: nothing yet.";
      } else {
         int var1 = var10.size();
         if (var10.size() == 1) {
            var16 = "";
         } else {
            var16 = "s";
         }

         String var11 = RoadData.INSTANCE.mb(var2);
         String var32 = CollectionsKt.joinToString$default(CollectionsKt.sorted(var10.keySet()), " ", null, null, 0, null, null, 62, null);
         var16 = "On the tablet: " + var1 + " state" + var16 + " (" + var11 + "): " + var32;
      }

      var9.addView((View)this.line(var16, CarUi.Companion.getTEXT(), 16.0F));
      boolean var12;
      if (RoadData.INSTANCE.getStatus().length() > 0) {
         var12 = 1;
      } else {
         var12 = 0;
      }

      if (var12) {
         var16 = RoadData.INSTANCE.getStatus();
         if (!StringsKt.startsWith$default(RoadData.INSTANCE.getStatus(), "Couldn't", false, 2, null)
            && !StringsKt.startsWith$default(RoadData.INSTANCE.getStatus(), "Not on", false, 2, null)) {
            var12 = CarUi.Companion.getTEXT();
         } else {
            var12 = CarUi.Companion.getWARN();
         }

         var9.addView((View)this.line(var16, var12, 15.0F));
      }

      LinearLayout var33 = new LinearLayout(this.getContext());
      var33.setOrientation(0);
      var33.setPadding(0, this.getUi().dp(10), 0, 0);
      if (RoadData.INSTANCE.getBusy()) {
         var33.addView((View)CarUi.button$default(this.getUi(), "Stop", false, new RoadDataView$$ExternalSyntheticLambda7(), 2, null));
      } else {
         var33.addView((View)this.getUi().button("Download all (Wi-Fi)", true, new RoadDataView$$ExternalSyntheticLambda8(this)));
      }

      View var19 = this.getUi().spacer(1);
      var19.setLayoutParams((LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.getUi().dp(12), 1)));
      var33.addView(var19);
      var33.addView((View)CarUi.button$default(this.getUi(), "Import a file…", false, new RoadDataView$$ExternalSyntheticLambda9(this), 2, null));
      var9.addView((View)var33);
      RoadData var34 = RoadData.INSTANCE;
      Context var20 = this.getContext();
      Intrinsics.checkNotNullExpressionValue(var20, "getContext(...)");
      var16 = var34.base(var20);
      var9.addView((View)this.line("Downloads once, ~370 MB for all states, only on Wi-Fi. From: " + var16, CarUi.Companion.getDIM(), 13.0F));
      LaneMapLog.Export var30 = LaneMapLog.Export.INSTANCE;
      Context var22 = this.getContext();
      Intrinsics.checkNotNullExpressionValue(var22, "getContext(...)");
      Triple var23 = var30.summary(var22);
      var12 = ((Number)var23.component1()).intValue();
      var2 = ((Number)var23.component2()).longValue();
      long var4 = ((Number)var23.component3()).longValue();
      var9 = this.card();
      this.add((View)var9);
      var9.addView(this.title("Your lane map", "satellite", "tablet"));
      var9.addView(
         (View)this.line(
            "While you drive with an RTK fix, the tablet notes exactly where across the road you are, every ~3 ft. After a few drives on a road, the traces show where each lane, merge lane and exit lane really is - to inches. Nothing uses them yet: they're checked against your drives first.",
            CarUi.Companion.getDIM(),
            15.0F
         )
      );
      if (var12 == 0) {
         var16 = "Nothing yet.";
      } else {
         StringCompanionObject var25 = StringCompanionObject.INSTANCE;
         Locale var35 = Locale.US;
         if (var12 == 1) {
            var16 = var28;
         } else {
            var16 = "s";
         }

         var16 = String.format(
            var35,
            "%d drive%s · %,d points (~%.0f mi) · %s",
            Arrays.copyOf(new Object[]{var12, var16, var2, var2 * 0.9 / 1609.34, RoadData.INSTANCE.mb(var4)}, 5)
         );
         Intrinsics.checkNotNullExpressionValue(var16, "format(...)");
      }

      var9.addView((View)this.line(var16, CarUi.Companion.getTEXT(), 16.0F));
      View var36 = (View)CarUi.button$default(this.getUi(), "Export lane traces", false, new RoadDataView$$ExternalSyntheticLambda10(this), 2, null);
      android.widget.LinearLayout.LayoutParams var29 = CarUi.lp$default(this.getUi(), 0, 4, 1, null);
      var29.topMargin = this.getUi().dp(8);
      Unit var27 = Unit.INSTANCE;
      var9.addView(var36, (LayoutParams)var29);
   }

   private static final Unit paint$lambda$10(RoadDataView var0) {
      Context var1 = var0.getContext();
      GxHost var3;
      if (var1 instanceof GxHost) {
         var3 = (GxHost)var1;
      } else {
         var3 = null;
      }

      if (var3 != null) {
         RoadDataView$$ExternalSyntheticLambda3 var2 = new RoadDataView$$ExternalSyntheticLambda3(var0);
         var3.pickFile(new String[]{"*/*"}, var2);
      }

      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$10$lambda$9(RoadDataView var0, Uri var1) {
      if (var1 != null) {
         new Thread(new RoadDataView$$ExternalSyntheticLambda0(var0, var1)).start();
      }

      return Unit.INSTANCE;
   }

   private static final void paint$lambda$10$lambda$9$lambda$8(RoadDataView var0, Uri var1) {
      RoadData var2 = RoadData.INSTANCE;
      Context var3 = var0.getContext();
      Intrinsics.checkNotNullExpressionValue(var3, "getContext(...)");
      String var4 = var2.import_(var3, var1);
      var0.getMain().post(new RoadDataView$$ExternalSyntheticLambda1(var0, var4));
   }

   private static final void paint$lambda$10$lambda$9$lambda$8$lambda$7(RoadDataView var0, String var1) {
      Context var2 = var0.getContext();
      if (var1 == null) {
         var1 = "Imported.";
      }

      Toast.makeText(var2, var1, 1).show();
      var0.paint();
   }

   private static final Unit paint$lambda$13(RoadDataView var0) {
      new Thread(new RoadDataView$$ExternalSyntheticLambda2(var0)).start();
      return Unit.INSTANCE;
   }

   private static final void paint$lambda$13$lambda$12(RoadDataView var0) {
      LaneMapLog.Export var1 = LaneMapLog.Export.INSTANCE;
      Context var2 = var0.getContext();
      Intrinsics.checkNotNullExpressionValue(var2, "getContext(...)");
      String var3 = var1.toDownloads(var2);
      var0.getMain().post(new RoadDataView$$ExternalSyntheticLambda4(var0, var3));
   }

   private static final void paint$lambda$13$lambda$12$lambda$11(RoadDataView var0, String var1) {
      Context var2 = var0.getContext();
      if (var1 == null) {
         var1 = "Saved to Download/StarView/Lane data";
      }

      Toast.makeText(var2, var1, 1).show();
   }

   private static final Unit paint$lambda$2() {
      RoadData.INSTANCE.cancel();
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$5(RoadDataView var0) {
      RoadData var2 = RoadData.INSTANCE;
      Context var1 = var0.getContext();
      Intrinsics.checkNotNullExpressionValue(var1, "getContext(...)");
      var2.download(var1, new RoadDataView$$ExternalSyntheticLambda5(var0));
      var0.getMain().removeCallbacks(var0.poll);
      var0.getMain().post(var0.poll);
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$5$lambda$4(RoadDataView var0) {
      var0.getMain().post(new RoadDataView$$ExternalSyntheticLambda6(var0));
      return Unit.INSTANCE;
   }

   private static final void paint$lambda$5$lambda$4$lambda$3(RoadDataView var0) {
      var0.paint();
   }

   @Override
   public void refresh() {
      this.paint();
   }
}
