package com.starpilot.starview;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class LastDriveView extends CardPage {
   // $VF: synthetic method
   public static Unit $r8$lambda$fJZkqxQrpUjQLZhbSOomkeU2MXs(LastDriveView var0, JSONObject var1, String var2) {
      return refresh$lambda$8(var0, var1, var2);
   }

   public LastDriveView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
   }

   private static final Unit refresh$lambda$8(LastDriveView var0, JSONObject var1, String var2) {
      if (var1 != null) {
         JSONObject var8 = var1.optJSONObject("lastDrive");
         if (var8 != null) {
            LinearLayout var5 = var0.card();
            var0.add((View)var5);
            var5.addView(var0.title("Galaxy's record", "star", "device"));
            Pair var6 = TuplesKt.to(JournalPagesKt.access$miles(var8.optDouble("distanceMeters")), "distance");
            Pair var7 = TuplesKt.to(JournalPagesKt.access$dur(var8.optDouble("durationSeconds")), "time");
            long var3 = Math.round(var8.optDouble("engagedPercent", 0.0));
            Pair var9 = TuplesKt.to(var3 + "%", "engaged");
            Pair var12 = TuplesKt.to(String.valueOf(var8.optInt("distractedMoments")), "distracted");
            var5.addView(var0.stats(new Pair[]{var6, var7, var9, var12, TuplesKt.to(String.valueOf(var8.optInt("unresponsiveMoments")), "unresponsive")}));
            CharSequence var13 = var8.optString("model");
            CharSequence var10 = var13;
            if (StringsKt.isBlank(var13)) {
               var10 = null;
            }

            String var11 = (String)var10;
            if (var11 != null) {
               var5.addView((View)CardPage.line$default(var0, "Model: " + var11, 0, 0.0F, 6, null));
            }

            return Unit.INSTANCE;
         }
      }

      return Unit.INSTANCE;
   }

   @Override
   public void refresh() {
      this.getCol().removeAllViews();
      List var11 = DriveJournal.INSTANCE.read("drive");
      JSONObject var12 = CollectionsKt.lastOrNull(var11);
      LinearLayout var13 = this.card();
      this.add((View)var13);
      var13.addView(this.title("Last drive", "car", "tablet"));
      if (var12 == null) {
         var13.addView((View)CardPage.line$default(this, "No drive noted yet: the tablet starts noting drives while it's linked.", 0, 0.0F, 6, null));
      } else {
         double var1 = (var12.optLong("end") - var12.optLong("t")) / 1000.0;
         CardPage var15 = this;
         String var14 = JournalPagesKt.access$getDayFmt$p().format(new Date(var12.optLong("t")));
         Iterable var17 = CollectionsKt.listOf(new String[]{var12.optString("from"), var12.optString("to")});
         var var16 = new ArrayList();

         for (Object var18 : var17) {
            String var19 = (String)var18;
            Intrinsics.checkNotNull(var19);
            if (!StringsKt.isBlank(var19)) {
               var16.add(var18);
            }
         }

         List var41 = var16;
         String var42 = CollectionsKt.joinToString$default(CollectionsKt.distinct(var41), " → ", null, null, 0, null, null, 62, null);
         var13.addView((View)CardPage.line$default(var15, var14 + "  ·  " + var42, 0, 0.0F, 6, null));
         Pair var52 = TuplesKt.to(JournalPagesKt.access$miles(var12.optDouble("meters")), "distance");
         Pair var43 = TuplesKt.to(JournalPagesKt.access$dur(var1), "time");
         int var3 = var12.optInt("engagedPct");
         Pair var49 = TuplesKt.to(var3 + "%", "openpilot on");
         Pair var27 = TuplesKt.to(String.valueOf(var12.optInt("takeovers")), "takeovers");
         Pair var32 = TuplesKt.to(String.valueOf(var12.optInt("alerts")), "alerts");
         var13.addView(this.stats(new Pair[]{var52, var43, var49, var27, var32, TuplesKt.to(String.valueOf(var12.optInt("hardBrakes")), "hard brakes")}));
         Iterable var33 = DriveJournal.read$default(DriveJournal.INSTANCE, null, 1, null);
         var var28 = new ArrayList();

         for (Object var44 : var33) {
            JSONObject var50 = (JSONObject)var44;
            long var6 = var12.optLong("t");
            long var4 = var12.optLong("end");
            long var8 = var50.optLong("t");
            boolean var22;
            if (var6 <= var8 && var8 <= var4) {
               var22 = true;
            } else {
               var22 = false;
            }

            if (var22 && !Intrinsics.areEqual(var50.optString("k"), "drive")) {
               var22 = true;
            } else {
               var22 = false;
            }

            if (var22) {
               var28.add(var44);
            }
         }

         List var29 = var28;
         if (!var29.isEmpty()) {
            LinearLayout var53 = this.card();
            this.add((View)var53);
            var53.addView(this.title("What happened", "history", "tablet"));

            for (JSONObject var35 : CollectionsKt.takeLast(var29, 40)) {
               CardPage var54 = this;
               String var20 = JournalPagesKt.access$getTimeFmt$p().format(new Date(var35.optLong("t")));
               boolean var10 = Intrinsics.areEqual(var35.optString("k"), "takeover");
               var14 = "";
               if (var10) {
                  String var21 = var35.optString("cause");
                  var3 = var35.optInt("mph");
                  CharSequence var45 = var35.optString("road");
                  CharSequence var36 = var45;
                  if (StringsKt.isBlank(var45)) {
                     var36 = null;
                  }

                  String var37 = (String)var36;
                  if (var37 != null) {
                     String var38 = " · " + var37;
                     if (var38 != null) {
                        var14 = var38;
                     }
                  }

                  var14 = "Took over (" + var21 + ") at " + var3 + " mph" + var14;
               } else {
                  String var55 = var35.optString("text");
                  CharSequence var46 = var35.optString("why");
                  CharSequence var39 = var46;
                  if (StringsKt.isBlank(var46)) {
                     var39 = null;
                  }

                  String var47 = (String)var39;
                  String var40 = var14;
                  if (var47 != null) {
                     var40 = " - " + var47;
                     if (var40 == null) {
                        var40 = var14;
                     }
                  }

                  var14 = var55 + var40;
               }

               var53.addView((View)CardPage.line$default(var54, var20 + "   " + var14, CarUi.Companion.getTEXT(), 0.0F, 4, null));
            }
         }
      }

      Galaxy7 var25 = Galaxy7.Companion.current();
      if (var25 != null) {
         String var26 = TimeZone.getDefault().getID();
         var25.get("/api/drives/stats?timezone=" + var26, new LastDriveView$$ExternalSyntheticLambda0(this));
      }
   }
}
