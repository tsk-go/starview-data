package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class AlertHistoryView extends CardPage {
   public AlertHistoryView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
   }

   @Override
   public void refresh() {
      this.getCol().removeAllViews();
      List var3 = CollectionsKt.asReversed(DriveJournal.INSTANCE.read("alert"));
      if (var3.isEmpty()) {
         LinearLayout var13 = this.card();
         var13.addView(this.title("Alert history", "bell", "sound"));
         var13.addView((View)CardPage.line$default(this, "No alerts noted yet. The tablet notes each alert while it's linked.", 0, 0.0F, 6, null));
         this.add((View)var13);
      } else {
         List var5 = CollectionsKt.take(CollectionsKt.sortedWith(GroupingKt.eachCount(new Grouping<JSONObject, String>(var3) {
            final Iterable $this_groupingBy;

            {
               this.$this_groupingBy = var1;
            }

            @Override
            public String keyOf(JSONObject var1) {
               return ((JSONObject)var1).optString("text");
            }

            @Override
            public Iterator<JSONObject> sourceIterator() {
               return this.$this_groupingBy.iterator();
            }
         }).entrySet(), new Comparator() {
            @Override
            public final int compare(T var1, T var2) {
               return ComparisonsKt.compareValues((T)((Integer & Comparable)((Entry)var2).getValue()), (T)((Integer & Comparable)((Entry)var1).getValue()));
            }
         }), 6);
         LinearLayout var4 = this.card();
         var4.addView(this.title("Most often", "bell", "sound"));

         for (Entry var7 : var5) {
            String var20 = (String)var7.getKey();
            int var1 = ((Number)var7.getValue()).intValue();
            var4.addView((View)this.line(var1 + "×   " + var20, CarUi.Companion.getTEXT(), 17.0F));
         }

         this.add((View)var4);
         String var14 = "";
         LinearLayout var26 = this.card();
         this.add((View)var26);
         var26.addView(this.title("All alerts", "history", "sound"));

         for (JSONObject var24 : CollectionsKt.take(var3, 300)) {
            String var21 = new SimpleDateFormat("EEEE, MMM d", Locale.US).format(new Date(var24.optLong("t")));
            String var12 = var14;
            if (!Intrinsics.areEqual(var21, var14)) {
               var12 = var21;
               CarUi var15 = this.getUi();
               Intrinsics.checkNotNull(var21);
               var26.addView((View)var15.header(var21));
            }

            String var16 = var24.optString("status");
            int var10;
            if (Intrinsics.areEqual(var16, "critical")) {
               var10 = Color.rgb(255, 138, 128);
            } else if (Intrinsics.areEqual(var16, "userprompt")) {
               var10 = CarUi.Companion.getWARN();
            } else {
               var10 = CarUi.Companion.getTEXT();
            }

            String var22 = JournalPagesKt.access$getTimeFmt$p().format(new Date(var24.optLong("t")));
            String var17 = var24.optString("text");
            var26.addView((View)this.line(var22 + "   " + var17, var10, 17.0F));
            CharSequence var18 = var24.optString("why");
            boolean var2 = StringsKt.isBlank(var18);
            CharSequence var23 = null;
            if (var2) {
               var18 = null;
            }

            var10 = var24.optInt("mph");
            String var9 = var10 + " mph";
            CharSequence var25 = var24.optString("road");
            if (!StringsKt.isBlank(var25)) {
               var23 = var25;
            }

            String var19 = CollectionsKt.joinToString$default(
               CollectionsKt.listOfNotNull(new String[]{var18, var9, var23}), " · ", null, null, 0, null, null, 62, null
            );
            var26.addView((View)this.line("            " + var19, CarUi.Companion.getDIM(), 14.0F));
            var14 = var12;
         }
      }
   }
}
