package com.starpilot.starview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

public final class TakeoverSpotsView extends CardPage {
   // $VF: synthetic method
   public static CharSequence $r8$lambda$6Dq35WIx1S_Ir3v02h3OwukEBoQ(Entry var0) {
      return refresh$lambda$7$lambda$6$lambda$5$lambda$4(var0);
   }

   public TakeoverSpotsView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
   }

   private static final CharSequence refresh$lambda$7$lambda$6$lambda$5$lambda$4(Entry var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      Object var1 = var0.getValue();
      Object var2 = var0.getKey();
      return var1 + " " + var2;
   }

   @Override
   public void refresh() {
      this.getCol().removeAllViews();
      List var6 = DriveJournal.INSTANCE.read("takeover");
      List var5 = JournalLogic.Companion.spots$default(JournalLogic.Companion, var6, 0.0, 2, null);
      LinearLayout var4 = this.card();
      this.add((View)var4);
      var4.addView(this.title("Takeover hot spots", "pin", "tablet"));
      CardPage var7 = this;
      String var3;
      if (var6.isEmpty()) {
         var3 = "No takeovers noted yet. The tablet notes each one (with its place) while it's linked.";
      } else {
         int var1 = var6.size();
         int var2 = var5.size();
         var3 = var1
            + " takeovers in "
            + var2
            + " places. A place you take over at again and again is worth a look: a lane that splits, a sharp curve, a merge.";
      }

      var4.addView((View)CardPage.line$default(var7, var3, 0, 0.0F, 6, null));

      for (JournalLogic.Companion.Spot var11 : CollectionsKt.take(var5, 30)) {
         LinearLayout var9 = this.card();
         this.add((View)var9);
         LinearLayout var8 = new LinearLayout(this.getContext());
         var8.setOrientation(0);
         var8.setGravity(16);
         TextView var16 = new TextView(var8.getContext());
         int var13 = var11.getCount();
         var16.setText(var13 + "×");
         if (var11.getCount() >= 3) {
            var13 = CarUi.Companion.getWARN();
         } else {
            var13 = CarUi.Companion.getTEXT();
         }

         var16.setTextColor(var13);
         var16.setTextSize(28.0F);
         var16.setTypeface(Typeface.DEFAULT_BOLD);
         var16.setMinWidth(this.getUi().dp(80));
         var8.addView((View)var16);
         LinearLayout var10 = new LinearLayout(var8.getContext());
         var10.setOrientation(1);
         TextView var12 = new TextView(var10.getContext());
         var7 = var11.getRoad();
         if (StringsKt.isBlank(var7)) {
            StringCompanionObject var18 = StringCompanionObject.INSTANCE;
            var7 = String.format(Locale.US, "%.5f, %.5f", Arrays.copyOf(new Object[]{var11.getLat(), var11.getLon()}, 2));
            Intrinsics.checkNotNullExpressionValue(var7, "format(...)");
         }

         var12.setText((CharSequence)var7);
         var12.setTextColor(CarUi.Companion.getTEXT());
         var12.setTextSize(19.0F);
         var10.addView((View)var12);
         TextView var19 = new TextView(var10.getContext());
         String var21 = CollectionsKt.joinToString$default(CollectionsKt.sortedWith(var11.getCauses().entrySet(), new Comparator() {
            @Override
            public final int compare(T var1, T var2) {
               return ComparisonsKt.compareValues((T)((Integer & Comparable)((Entry)var2).getValue()), (T)((Integer & Comparable)((Entry)var1).getValue()));
            }
         }), " · ", null, null, 0, null, new TakeoverSpotsView$$ExternalSyntheticLambda0(), 30, null);
         String var20 = JournalPagesKt.access$getDayFmt$p().format(new Date(var11.getLastT()));
         var19.setText(var21 + "  ·  last " + var20);
         var19.setTextColor(CarUi.Companion.getDIM());
         var19.setTextSize(15.0F);
         var10.addView((View)var19);
         var8.addView((View)var10, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
         var9.addView((View)var8);
      }
   }
}
