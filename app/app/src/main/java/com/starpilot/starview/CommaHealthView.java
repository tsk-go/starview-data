package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class CommaHealthView extends CardPage {
   private final LinearLayout hw;
   private final LinearLayout model;
   private final Runnable poll;
   private final LinearLayout procs;
   private boolean shown;
   private final LinearLayout summary;

   // $VF: synthetic method
   public static Unit $r8$lambda$agaZKFoGCUPO0ru3K0PgmlqlvDI(CommaHealthView var0, Telemetry var1, JSONObject var2, String var3) {
      return refresh$lambda$0(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rXErHwG6dFa4s7WAEyvJj_KKVoY(CommaHealthView var0, Telemetry var1, JSONObject var2, String var3) {
      return refresh$lambda$1(var0, var1, var2, var3);
   }

   public CommaHealthView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
      this.summary = this.card();
      this.procs = this.card();
      this.model = this.card();
      this.hw = this.card();
      this.poll = new Runnable() {

         @Override
         public void run() {
            if (CommaHealthView.this.shown) {
               CommaHealthView.this.refresh();
               CommaHealthView.this.getMain().postDelayed(this, 10000L);
            }
         }
      };
      this.add((View)this.summary);
      this.add((View)this.procs);
      this.add((View)this.model);
      this.add((View)this.hw);
   }

   private final void paint(JSONObject var1, Telemetry var2) {
      JSONObject var16 = var1.optJSONObject("memory");
      JSONObject var22 = var1.optJSONObject("storage");
      double var3;
      if (var16 != null) {
         var3 = var16.optDouble("percent", Double.NaN);
      } else {
         var3 = Double.NaN;
      }

      this.summary.removeAllViews();
      this.summary.addView(this.title("Comma health", "chip", "device"));
      LinearLayout var20 = this.summary;
      Pair[] var21 = new Pair[5];
      boolean var10;
      if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      String var17 = "–";
      String var15;
      if (var10) {
         long var11 = Math.round(var3);
         var15 = var11 + "%";
      } else {
         var15 = "–";
      }

      String var18;
      label144: {
         var18 = "";
         if (var16 != null) {
            double var5 = var16.optDouble("availableMiB");
            long var47 = Math.round(var5);
            String var19 = var47 + " MB free";
            var59 = var19;
            if (var19 != null) {
               break label144;
            }
         }

         var59 = "";
      }

      label139: {
         var21[0] = TuplesKt.to(var15, "memory · " + var59);
         long var48 = Math.round(var1.optDouble("cpuPercent", 0.0));
         var21[1] = TuplesKt.to(var48 + "%", "CPU");
         if (var2 != null) {
            Float var51 = var2.getCpuTempC();
            float var9 = var51.floatValue();
            if (var9 > 0.0F) {
               var10 = (boolean)1;
            } else {
               var10 = (boolean)0;
            }

            if (!var10) {
               var51 = null;
            }

            if (var51 != null) {
               var9 = var51.floatValue();
               var10 = Math.round(9 * var9 / 5 + 32);
               String var60 = var10 + "°F";
               var15 = var60;
               if (var60 != null) {
                  break label139;
               }
            }
         }

         var15 = "–";
      }

      String var61 = var18;
      if (var2 != null) {
         var61 = var2.getThermalStatus();
         if (var61 == null) {
            var61 = var18;
         }
      }

      label131: {
         var21[2] = TuplesKt.to(var15, "CPU temp · " + var61);
         if (var22 != null) {
            Double var29 = var22.optDouble("usedGiB", Double.NaN);
            double var38 = var29.doubleValue();
            boolean var44;
            if (!Double.isInfinite(var38) && !Double.isNaN(var38)) {
               var44 = true;
            } else {
               var44 = false;
            }

            if (!var44) {
               var29 = null;
            }

            if (var29 != null) {
               var38 = var29.doubleValue();
               long var13 = Math.round(var38);
               long var49 = Math.round(var22.optDouble("totalGiB"));
               var30 = var13 + "/" + var49 + " GB";
               if (var30 == null) {
                  var30 = var17;
               }
               break label131;
            }
         }

         var30 = var17;
      }

      var21[3] = TuplesKt.to(var30, "storage");
      var21[4] = TuplesKt.to(JournalPagesKt.access$dur(var1.optDouble("uptimeSeconds", 0.0)), "up");
      var20.addView(this.stats(var21));
      List var66 = HealthHistory.INSTANCE.points();
      if (var66.size() >= 2) {
         LinearLayout var62 = this.summary;
         CardPage var53 = this;
         String var31 = JournalPagesKt.access$dur(
            (CollectionsKt.<HealthHistory.Point>last(var66).getTMs() - CollectionsKt.<HealthHistory.Point>first(var66).getTMs()) / 1000.0
         );
         var62.addView(
            (View)CardPage.line$default(var53, "Memory, last " + var31 + " (the line is 90%: \"Low Memory\" starts there)", 0, 0.0F, 6, null),
            (LayoutParams)CarUi.lp$default(this.getUi(), 0, 4, 1, null)
         );
         LinearLayout var54 = this.summary;
         Context var32 = this.getContext();
         Intrinsics.checkNotNullExpressionValue(var32, "getContext(...)");
         Iterable var67 = var66;
         var var63 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var67, 10));
         Iterator var68 = var67.iterator();

         while (var68.hasNext()) {
            var63.add((float)((HealthHistory.Point)var68.next()).getMemPct());
         }

         List var64 = var63;
         if (var3 > 85.0) {
            var10 = CarUi.Companion.getWARN();
         } else {
            var10 = CarUi.Companion.getACCENT();
         }

         var54.addView(
            new Spark(
               var32,
               var64,
               100.0F,
               var10,
               90.0F,
               CollectionsKt.listOf(
                  new String[]{JournalPagesKt.access$getTimeFmt$p().format(new Date(CollectionsKt.<HealthHistory.Point>first(var66).getTMs())), "now"}
               )
            ),
            (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, this.getUi().dp(110)))
         );
      } else {
         this.summary.addView((View)CardPage.line$default(this, "The memory trend starts drawing after a minute with the tablet linked.", 0, 0.0F, 6, null));
      }

      this.procs.removeAllViews();
      this.procs.addView(this.title("What holds the memory", "data", "device"));
      JSONArray var65 = var1.optJSONArray("processes");
      List var23;
      if (var65 == null) {
         var23 = CollectionsKt.emptyList();
      } else {
         Iterable var33 = RangesKt.until(0, var65.length());
         var var24 = new ArrayList();
         Iterator var34 = var33.iterator();

         while (var34.hasNext()) {
            JSONObject var55 = var65.optJSONObject(((IntIterator)var34).nextInt());
            if (var55 != null) {
               var24.add(var55);
            }
         }

         List var25 = var24;
         Iterable var35 = var25;
         var var26 = new ArrayList();

         for (Object var36 : var35) {
            if (!((JSONObject)var36).optBoolean("kernel")) {
               var26.add(var36);
            }
         }

         List var27 = var26;
         Iterable var28 = var27;
         var23 = CollectionsKt.take(CollectionsKt.sortedWith(var28, new Comparator() {
            @Override
            public final int compare(T var1, T var2x) {
               return ComparisonsKt.compareValues(((JSONObject)var2x).optDouble("memoryMiB", 0.0), ((JSONObject)var1).optDouble("memoryMiB", 0.0));
            }
         }), 10);
      }

      JSONObject var57 = CollectionsKt.firstOrNull(var23);
      String var37 = "memoryMiB";
      double var40 = 1.0;
      if (var57 != null) {
         var40 = var57.optDouble("memoryMiB", 1.0);
      }

      for (JSONObject var74 : var23) {
         double var7 = var74.optDouble(var37, 0.0);
         LinearLayout var70 = this.procs;
         LinearLayout var69 = new LinearLayout(this.getContext());
         var69.setOrientation(0);
         var69.setGravity(16);
         var69.setPadding(0, this.getUi().dp(4), 0, this.getUi().dp(4));
         TextView var71 = new TextView(var69.getContext());
         String var75 = var74.optString("name");
         Intrinsics.checkNotNullExpressionValue(var75, "optString(...)");
         var71.setText(StringsKt.take(StringsKt.substringAfterLast$default(var75, '/', null, 2, null), 40));
         var71.setTextColor(CarUi.Companion.getTEXT());
         var71.setTextSize(16.0F);
         var71.setMaxLines(1);
         var69.addView((View)var71, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.getUi().dp(300), -2)));
         FrameLayout var76 = new FrameLayout(var69.getContext());
         View var72 = new View(var76.getContext());
         CarUi var77 = this.getUi();
         if (var7 > 300.0) {
            var10 = CarUi.Companion.getWARN();
         } else {
            var10 = Color.rgb(127, 180, 255);
         }

         var72.setBackground((Drawable)CarUi.rounded$default(var77, var10, 6, null, 4, null));
         var76.addView(
            var72,
            (LayoutParams)(
               new android.widget.FrameLayout.LayoutParams(
                  RangesKt.coerceAtLeast((int)(this.getUi().dp(420) * (var7 / var40)), this.getUi().dp(4)), this.getUi().dp(14), 16
               )
            )
         );
         var69.addView((View)var76, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, this.getUi().dp(26), 1.0F)));
         TextView var73 = new TextView(var69.getContext());
         long var50 = Math.round(var7);
         var73.setText(var50 + " MB");
         var73.setTextColor(CarUi.Companion.getDIM());
         var73.setTextSize(15.0F);
         var73.setGravity(8388613);
         var69.addView((View)var73, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.getUi().dp(90), -2)));
         var70.addView((View)var69);
      }

      if (var23.isEmpty()) {
         this.procs.addView((View)CardPage.line$default(this, "Galaxy didn't list processes.", 0, 0.0F, 6, null));
      }
   }

   private final void paintHardware(Telemetry var1) {
      boolean var3;
      String var5;
      String var6;
      LinearLayout var8;
      CardPage var9;
      String var15;
      label52: {
         this.hw.removeAllViews();
         this.hw.addView(this.title("On the hub", "link", "tablet"));
         RtkStatus var4 = Ntrip.INSTANCE.getRtk();
         var8 = this.hw;
         var9 = this;
         var5 = "";
         var6 = "not seen";
         var3 = false;
         if (var4 != null) {
            String var7 = var4.getWords();
            int var2 = var4.getUsed();
            if (var4.getHasFix()) {
               var15 = RtkStatus.Companion.accuracy(var4.getHAccM(), false);
               var15 = " · ±" + var15;
            } else {
               var15 = "";
            }

            var7 = var7 + " · " + var2 + " sats" + var15;
            var15 = var7;
            if (var7 != null) {
               break label52;
            }
         }

         var15 = "not seen";
      }

      var8.addView((View)CardPage.line$default(var9, "RTK receiver: " + var15, 0, 0.0F, 6, null));
      LinearLayout var25 = this.hw;
      CardPage var27 = this;
      boolean var11;
      if (var1 != null && var1.getChestnutPresent()) {
         var11 = true;
      } else {
         var11 = false;
      }

      var15 = var6;
      if (var11) {
         var15 = "connected";
      }

      var25.addView((View)CardPage.line$default(var27, "eGPU: " + var15, 0, 0.0F, 6, null));
      var25 = this.hw;
      CardPage var22 = this;
      DataClient var17 = Link.INSTANCE.getData();
      var11 = var3;
      if (var17 != null) {
         var11 = var3;
         if (var17.getConnected()) {
            var11 = true;
         }
      }

      if (var11) {
         var15 = Link.INSTANCE.getHost();
         if (var15 == null) {
            var15 = var5;
         }

         var15 = "linked (" + var15 + ")";
      } else {
         var15 = "not linked";
      }

      label37: {
         var25.addView((View)CardPage.line$default(var22, "This tablet: " + var15, 0, 0.0F, 6, null));
         var21 = this.hw;
         var23 = this;
         if (var1 != null) {
            var15 = var1.getNetworkType();
            var10 = var15;
            if (var15 != null) {
               break label37;
            }
         }

         var10 = "–";
      }

      var21.addView((View)CardPage.line$default(var23, "Network: " + var10, 0, 0.0F, 6, null));
   }

   private final void paintModel(JSONObject var1, Telemetry var2) {
      this.model.removeAllViews();
      this.model.addView(this.title("Driving model", "chip", "device"));
      if (var1 == null) {
         this.model.addView((View)CardPage.line$default(this, "Model status not available", 0, 0.0F, 6, null));
      } else {
         LinearLayout var8 = this.model;
         CharSequence var7 = var1.optString("loadedId");
         LinearLayout var6 = var7;
         if (StringsKt.isBlank(var7)) {
            var6 = "–";
         }

         var8.addView((View)this.line("Running: " + var6, CarUi.Companion.getTEXT(), 18.0F));
         var6 = var1.optString("health");
         boolean var5 = StringsKt.isBlank((CharSequence)var6);
         var7 = null;
         if (var5) {
            var6 = null;
         }

         var6 = (String)var6;
         if (var6 != null) {
            this.model.addView((View)CardPage.line$default(this, "Health: " + var6, 0, 0.0F, 6, null));
         }

         CharSequence var21 = var1.optString("fallbackReason");
         var6 = var21;
         if (StringsKt.isBlank(var21)) {
            var6 = null;
         }

         var6 = (String)var6;
         if (var6 != null) {
            this.model.addView((View)CardPage.line$default(this, "Fell back because: " + var6, CarUi.Companion.getWARN(), 0.0F, 4, null));
         }

         var6 = var1.optString("requestedId");
         Intrinsics.checkNotNull(var6);
         var5 = StringsKt.isBlank((CharSequence)var6);
         boolean var4 = true;
         boolean var3;
         if (!var5 && !Intrinsics.areEqual(var6, var1.optString("loadedId"))) {
            var3 = true;
         } else {
            var3 = false;
         }

         String var9 = var7;
         if (var3) {
            var9 = (String)var6;
         }

         if (var9 != null) {
            this.model.addView((View)CardPage.line$default(this, "Asked for: " + var9 + " (next start)", 0, 0.0F, 6, null));
         }

         var6 = this.model;
         CardPage var20 = this;
         if (var2 != null && var2.getChestnutPresent()) {
            var3 = var4;
         } else {
            var3 = false;
         }

         String var10;
         if (var3) {
            var10 = "eGPU: connected";
         } else {
            var10 = "eGPU: not seen";
         }

         var6.addView((View)CardPage.line$default(var20, var10, 0, 0.0F, 6, null));
      }
   }

   private static final Unit refresh$lambda$0(CommaHealthView var0, Telemetry var1, JSONObject var2, String var3) {
      if (var2 != null) {
         var0.paint(var2, var1);
      } else {
         var0.summary.removeAllViews();
         var0.summary.addView(var0.title("Comma health", "chip", "device"));
         LinearLayout var5 = var0.summary;
         CardPage var6 = var0;
         String var4;
         if (var3 == null) {
            var4 = "Galaxy didn't answer";
         } else {
            var4 = var3;
         }

         var5.addView((View)CardPage.line$default(var6, var4, 0, 0.0F, 6, null));
      }

      return Unit.INSTANCE;
   }

   private static final Unit refresh$lambda$1(CommaHealthView var0, Telemetry var1, JSONObject var2, String var3) {
      var0.paintModel(var2, var1);
      return Unit.INSTANCE;
   }

   @Override
   public void refresh() {
      Telemetry var1 = Link.INSTANCE.getTelemetry();
      Galaxy7 var2 = Galaxy7.Companion.current();
      if (var2 == null) {
         this.summary.removeAllViews();
         this.summary.addView(this.title("Comma health", "chip", "device"));
         this.summary.addView((View)CardPage.line$default(this, "Not connected to the comma.", 0, 0.0F, 6, null));
      } else {
         var2.get("/api/system/monitor", new CommaHealthView$$ExternalSyntheticLambda0(this, var1));
         var2.get("/api/models/status", new CommaHealthView$$ExternalSyntheticLambda1(this, var1));
         this.paintHardware(var1);
      }
   }

   public final void start() {
      this.shown = true;
      this.getMain().removeCallbacks(this.poll);
      this.getMain().post(this.poll);
   }

   public final void stop() {
      this.shown = false;
      this.getMain().removeCallbacks(this.poll);
   }
}
