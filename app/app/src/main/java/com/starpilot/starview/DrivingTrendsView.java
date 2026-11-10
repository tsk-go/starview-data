package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class DrivingTrendsView extends CardPage {
   // $VF: synthetic method
   public static CharSequence $r8$lambda$OqVnkdTAXJuWhJYrEGdqnJA5rLE(Pair var0) {
      return refresh$lambda$4(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$RPDA3G_H6Zrq3j_xGLlGQ_g7JlQ/* $VF was: $r8$lambda$RPDA3G_H6Zrq3j_xGLlGQ_g7JlQ*/(
      DrivingTrendsView var0, JSONObject var1, String var2
   ) {
      return refresh$lambda$12(var0, var1, var2);
   }

   public DrivingTrendsView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
   }

   private static final Unit refresh$lambda$12(DrivingTrendsView var0, JSONObject var1, String var2) {
      if (var1 == null) {
         LinearLayout var32 = var0.card();
         CardPage var35 = var0;
         String var20;
         if (var2 == null) {
            var20 = "Galaxy's drive stats aren't available";
         } else {
            var20 = var2;
         }

         var32.addView((View)CardPage.line$default(var35, var20, 0, 0.0F, 6, null));
         var0.add((View)var32);
         return Unit.INSTANCE;
      } else {
         LinearLayout var11 = var0.card();
         var0.add((View)var11);
         var11.addView(var0.title("All drives (Galaxy)", "star", "device"));
         JSONObject var21 = var1.optJSONObject("totals");
         if (var21 != null) {
            Pair var12 = TuplesKt.to(String.valueOf(var21.optInt("drives")), "drives");
            Pair var10 = TuplesKt.to(JournalPagesKt.access$miles(var21.optDouble("distanceMeters")), "distance");
            Pair var13 = TuplesKt.to(JournalPagesKt.access$dur(var21.optDouble("durationSeconds")), "time");
            long var8;
            if (var21.optDouble("durationSeconds") > 0.0) {
               var8 = Math.round(var21.optDouble("engagedSeconds") * 100 / var21.optDouble("durationSeconds"));
            } else {
               var8 = 0L;
            }

            var11.addView(var0.stats(new Pair[]{var12, var10, var13, TuplesKt.to(var8 + "%", "openpilot on")}));
         }

         JSONObject var29 = var1.optJSONObject("week");
         if (var29 != null) {
            JSONArray var39 = var29.optJSONArray("days");
            if (var39 != null && var39.length() > 0) {
               Iterable var22 = RangesKt.until(0, var39.length());
               var var36 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var22, 10));
               Iterator var14 = var22.iterator();

               while (var14.hasNext()) {
                  var36.add((float)(var39.getJSONObject(((IntIterator)var14).nextInt()).optDouble("distanceMeters") / 1609.344));
               }

               List var37 = var36;
               Iterable var41 = RangesKt.until(0, var39.length());
               var var23 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var41, 10));
               Iterator var15 = var41.iterator();

               while (var15.hasNext()) {
                  String var42 = var39.getJSONObject(((IntIterator)var15).nextInt()).optString("date");
                  Intrinsics.checkNotNullExpressionValue(var42, "optString(...)");
                  var23.add(StringsKt.takeLast(var42, 5));
               }

               List var40 = var23;
               CardPage var24 = var0;
               String var43 = JournalPagesKt.access$miles(var29.optDouble("distanceMeters"));
               double var3 = var29.optDouble("engagedPercent");
               var11.addView(
                  (View)CardPage.line$default(var24, "This week: " + var43 + ", " + var3 + "% with openpilot on", 0, 0.0F, 6, null),
                  (LayoutParams)CarUi.lp$default(var0.getUi(), 0, 4, 1, null)
               );
               Context var44 = var0.getContext();
               Intrinsics.checkNotNullExpressionValue(var44, "getContext(...)");
               int var6 = CarUi.Companion.getACCENT();
               Iterable var25 = var37;
               var var45 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var25, 10));
               Iterator var16 = var25.iterator();

               while (var16.hasNext()) {
                  float var5 = ((Number)var16.next()).floatValue();
                  String var30;
                  if (var5 >= 1.0F) {
                     var30 = String.valueOf(Math.round(var5));
                  } else {
                     var30 = "";
                  }

                  var45.add(var30);
               }

               List var26 = var45;
               var11.addView(new Bars(var44, var37, var40, var6, var26), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, var0.getUi().dp(150))));
            }
         }

         JSONArray var27 = var1.optJSONArray("records");
         if (var27 != null && var27.length() > 0) {
            LinearLayout var31 = var0.card();
            var0.add((View)var31);
            var31.addView(var0.title("Personal records", "flag", "device"));
            int var28 = 0;

            for (int var7 = var27.length(); var28 < var7; var28++) {
               JSONObject var33 = var27.getJSONObject(var28);
               String var17 = var33.optString("unit");
               String var18;
               if (Intrinsics.areEqual(var17, "m")) {
                  var18 = JournalPagesKt.access$miles(var33.optDouble("value"));
               } else if (Intrinsics.areEqual(var17, "s")) {
                  var18 = JournalPagesKt.access$dur(var33.optDouble("value"));
               } else {
                  String var38 = var33.optString("value");
                  String var19 = var33.optString("unit");
                  var18 = var38 + " " + var19;
               }

               String var34 = var33.optString("label");
               var31.addView((View)var0.line(var34 + ":  " + var18, CarUi.Companion.getTEXT(), 17.0F));
            }
         }

         return Unit.INSTANCE;
      }
   }

   private static final CharSequence refresh$lambda$4(Pair var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return String.valueOf(((Number)var0.getSecond()).intValue());
   }

   @Override
   public void refresh() {
      this.getCol().removeAllViews();
      List var10 = JournalLogic.Companion.weekly$default(JournalLogic.Companion, DriveJournal.INSTANCE.read("drive"), System.currentTimeMillis(), 0, 4, null);
      LinearLayout var9 = this.card();
      this.add((View)var9);
      var9.addView(this.title("Takeovers per 100 miles, by week", "plots", "tablet"));
      var var6 = var10;
      boolean var18;
      if (var6 instanceof Collection && ((Collection)var6).isEmpty()) {
         var18 = 1;
      } else {
         Iterator var22 = var6.iterator();

         while (true) {
            if (!var22.hasNext()) {
               var18 = 1;
               break;
            }

            double var1 = ((Number)((Pair)var22.next()).getFirst()).doubleValue();
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
               var18 = (boolean)1;
            } else {
               var18 = (boolean)0;
            }

            if (var18) {
               var18 = 0;
               break;
            }
         }
      }

      if (var18) {
         var9.addView((View)CardPage.line$default(this, "Fills in as the tablet notes drives (it needs a week or two).", 0, 0.0F, 6, null));
      } else {
         Context var11 = this.getContext();
         Intrinsics.checkNotNullExpressionValue(var11, "getContext(...)");
         Iterable var7 = var10;
         var var23 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));
         Iterator var28 = var7.iterator();

         while (var28.hasNext()) {
            var23.add((float)((Number)((Pair)var28.next()).getFirst()).doubleValue());
         }

         List var12 = var23;
         var6 = CollectionsKt.getIndices(var10);
         var var13 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10));
         Iterator var8 = var6.iterator();

         while (var8.hasNext()) {
            var18 = ((IntIterator)var8).nextInt();
            String var29;
            if (var18 == var10.size() - 1) {
               var29 = "this wk";
            } else {
               int var4 = var10.size();
               var29 = "-" + (var4 - 1 - var18);
            }

            var13.add(var29);
         }

         List var35 = var13;
         int var21 = Color.rgb(127, 180, 255);
         var7 = var10;
         boolean var5 = false;
         var var15 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));

         for (Pair var16 : var7) {
            double var17 = ((Number)var16.getFirst()).doubleValue();
            boolean var20;
            if (!Double.isInfinite(var17) && !Double.isNaN(var17)) {
               var20 = true;
            } else {
               var20 = false;
            }

            String var34;
            if (var20) {
               StringCompanionObject var33 = StringCompanionObject.INSTANCE;
               var34 = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{var16.getFirst()}, 1));
               Intrinsics.checkNotNullExpressionValue(var34, "format(...)");
            } else {
               var34 = "";
            }

            var15.add(var34);
         }

         List var25 = var15;
         var9.addView(new Bars(var11, var12, var35, var21, var25), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, this.getUi().dp(170))));
         CardPage var26 = this;
         String var31 = CollectionsKt.joinToString$default(var10, "  ", null, null, 0, null, new DrivingTrendsView$$ExternalSyntheticLambda0(), 30, null);
         var9.addView((View)CardPage.line$default(var26, "Lower is better. Miles per week: " + var31, 0, 0.0F, 6, null));
      }

      Galaxy7 var27 = Galaxy7.Companion.current();
      if (var27 != null) {
         String var32 = TimeZone.getDefault().getID();
         var27.get("/api/drives/stats?timezone=" + var32, new DrivingTrendsView$$ExternalSyntheticLambda1(this));
      }
   }
}
