package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class JournalLogic {
   public static final JournalLogic.Companion Companion = new JournalLogic.Companion(null);
   private int alerts;
   private boolean brakeHeld;
   private long driveStart;
   private boolean driving;
   private long engagedMs;
   private int hardBrakes;
   private JournalLogic.Sample last;
   private String lastAlert;
   private double meters;
   private final ArrayList<JSONObject> out = new ArrayList<>();
   private double startLat;
   private double startLon;
   private String startRoad;
   private int takeovers;

   public JournalLogic() {
      this.lastAlert = "";
      this.startLat = Double.NaN;
      this.startLon = Double.NaN;
      this.startRoad = "";
   }

   public final void feed(JournalLogic.Sample var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      JournalLogic.Sample var9 = this.last;
      this.last = var1;
      if (var1.getStarted() && !this.driving) {
         this.driving = true;
         this.driveStart = var1.getTMs();
         this.meters = 0.0;
         this.engagedMs = 0L;
         this.takeovers = 0;
         this.alerts = 0;
         this.hardBrakes = 0;
         this.lastAlert = "";
         this.startLat = var1.getLat();
         this.startLon = var1.getLon();
         this.startRoad = var1.getRoad();
      }

      if (var9 != null && this.driving) {
         long var5 = RangesKt.coerceIn(var1.getTMs() - var9.getTMs(), 0L, 2000L);
         this.meters = this.meters + var1.getVEgo() * (float)var5 / 1000.0;
         if (var1.getEnabled() && var9.getEnabled()) {
            this.engagedMs += var5;
         }

         if (var9.getEnabled() && !var1.getEnabled() && var9.getVEgo() > 2.5F) {
            this.takeovers++;
            String var7;
            if (var1.getBrake() || var9.getBrake()) {
               var7 = "brake";
            } else if (var1.getGas() || var9.getGas()) {
               var7 = "gas";
            } else if (var1.getSteering() || var9.getSteering()) {
               var7 = "steering";
            } else if (!StringsKt.isBlank(var1.getAlert1())) {
               var7 = "alert";
            } else {
               var7 = "button";
            }

            ArrayList var10 = this.out;
            JSONObject var11 = new JSONObject()
               .put("k", "takeover")
               .put("t", var1.getTMs())
               .put("cause", var7)
               .put("mph", Math.round(var9.getVEgo() * 2.23694))
               .put("road", var1.getRoad());
            CharSequence var8 = var1.getAlert1();
            var7 = var8;
            if (StringsKt.isBlank(var8)) {
               var7 = var9.getAlert1();
            }

            JSONObject var25 = var11.put("alert", var7);
            double var2 = var1.getLat();
            boolean var4;
            if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (var4) {
               var2 = var1.getLon();
               if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               if (var4) {
                  if (var1.getLat() == 0.0) {
                     var4 = true;
                  } else {
                     var4 = false;
                  }

                  if (!var4) {
                     var25.put("lat", var1.getLat()).put("lon", var1.getLon());
                  }
               }
            }

            var10.add(var25);
         }

         if (var1.getAEgo() < -3.0F && var1.getVEgo() > 3.0F) {
            if (!this.brakeHeld) {
               this.hardBrakes++;
               this.brakeHeld = true;
            }
         } else if (var1.getAEgo() > -1.5F) {
            this.brakeHeld = false;
         }
      }

      String var28 = StringsKt.trim(var1.getAlert1()).toString();
      if (this.driving) {
         boolean var18;
         if (var28.length() > 0) {
            var18 = true;
         } else {
            var18 = false;
         }

         if (var18 && !Intrinsics.areEqual(var28, this.lastAlert)) {
            this.alerts++;
            ArrayList var26 = this.out;
            JSONObject var30 = new JSONObject()
               .put("k", "alert")
               .put("t", var1.getTMs())
               .put("text", var28)
               .put("why", StringsKt.trim(var1.getAlert2()).toString())
               .put("status", var1.getAlertStatus())
               .put("mph", Math.round(var1.getVEgo() * 2.23694))
               .put("road", var1.getRoad());
            double var14 = var1.getLat();
            if (!Double.isInfinite(var14) && !Double.isNaN(var14)) {
               var18 = true;
            } else {
               var18 = false;
            }

            if (var18) {
               if (var1.getLat() == 0.0) {
                  var18 = true;
               } else {
                  var18 = false;
               }

               if (!var18) {
                  var30.put("lat", var1.getLat()).put("lon", var1.getLon());
               }
            }

            var26.add(var30);
         }
      }

      this.lastAlert = var28;
      if (!var1.getStarted() && this.driving) {
         this.driving = false;
         if (this.meters > 200.0 || var1.getTMs() - this.driveStart > 120000L) {
            ArrayList var27 = this.out;
            JSONObject var29 = new JSONObject().put("k", "drive").put("t", this.driveStart).put("end", var1.getTMs()).put("meters", Math.round(this.meters));
            long var23;
            if (var1.getTMs() > this.driveStart) {
               var23 = Math.round(this.engagedMs * 100.0 / (var1.getTMs() - this.driveStart));
            } else {
               var23 = 0L;
            }

            JSONObject var12 = var29.put("engagedPct", var23)
               .put("takeovers", this.takeovers)
               .put("alerts", this.alerts)
               .put("hardBrakes", this.hardBrakes)
               .put("from", this.startRoad)
               .put("to", var1.getRoad());
            double var15 = this.startLat;
            boolean var21;
            if (!Double.isInfinite(var15) && !Double.isNaN(var15)) {
               var21 = true;
            } else {
               var21 = false;
            }

            if (var21) {
               if (this.startLat == 0.0) {
                  var21 = true;
               } else {
                  var21 = false;
               }

               if (!var21) {
                  var12.put("lat", this.startLat).put("lon", this.startLon);
               }
            }

            var27.add(var12);
         }
      }
   }

   public final ArrayList<JSONObject> getOut() {
      return this.out;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      // $VF: synthetic method
      public static List spots$default(JournalLogic.Companion var0, List var1, double var2, int var4, Object var5) {
         if ((var4 & 2) != 0) {
            var2 = 150.0;
         }

         return var0.spots(var1, var2);
      }

      // $VF: synthetic method
      public static List weekly$default(JournalLogic.Companion var0, List var1, long var2, int var4, int var5, Object var6) {
         if ((var5 & 4) != 0) {
            var4 = 8;
         }

         return var0.weekly(var1, var2, var4);
      }

      public final double meters(double var1, double var3, double var5, double var7) {
         double var9 = Math.toRadians(var5 - var1);
         var7 = Math.toRadians(var7 - var3);
         var3 = 2;
         var1 = Math.sin(var9 / var3) * Math.sin(var9 / var3)
            + Math.cos(Math.toRadians(var1)) * Math.cos(Math.toRadians(var5)) * Math.sin(var7 / var3) * Math.sin(var7 / var3);
         return var3 * 6371000.0 * Math.atan2(Math.sqrt(var1), Math.sqrt(1 - var1));
      }

      public final List<JournalLogic.Companion.Spot> spots(List<? extends JSONObject> var1, double var2) {
         Intrinsics.checkNotNullParameter(var1, "takeovers");
         ArrayList var18 = new ArrayList();

         for (JSONObject var21 : var1) {
            if (var21.has("lat")) {
               double var8 = var21.getDouble("lat");
               double var10 = var21.getDouble("lon");
               Iterator var22 = var18.iterator();
               boolean var16 = var22.hasNext();
               Object var19 = null;
               Object var17;
               if (!var16) {
                  var17 = null;
               } else {
                  var17 = (JournalLogic$Companion$spots$Acc)var22.next();
                  if (var22.hasNext()) {
                     JournalLogic$Companion$spots$Acc var20 = var17;
                     double var4 = JournalLogic.Companion.meters(var20.getLat(), var20.getLon(), var8, var10);

                     do {
                        var20 = (JournalLogic$Companion$spots$Acc)var22.next();
                        JournalLogic$Companion$spots$Acc var24 = var20;
                        JournalLogic.Companion var23 = JournalLogic.Companion;
                        double var6 = var24.getLat();
                        double var12 = var24.getLon();
                        var6 = var23.meters(var6, var12, var8, var10);
                        if (Double.compare(var4, var6) > 0) {
                           var4 = var6;
                           var17 = var20;
                        }
                     } while (var22.hasNext());
                  }
               }

               JournalLogic$Companion$spots$Acc var37 = var17;
               byte var15 = 0;
               var17 = (JournalLogic$Companion$spots$Acc)var19;
               if (var37 != null) {
                  boolean var14;
                  if (JournalLogic.Companion.meters(var37.getLat(), var37.getLon(), var8, var10) < var2) {
                     var14 = true;
                  } else {
                     var14 = false;
                  }

                  var17 = (JournalLogic$Companion$spots$Acc)var19;
                  if (var14) {
                     var17 = var37;
                  }
               }

               var19 = var21.optString("cause", "?");
               if (var17 != null) {
                  var17.setLat((var17.getLat() * var17.getN() + var8) / (var17.getN() + 1));
                  var17.setLon((var17.getLon() * var17.getN() + var10) / (var17.getN() + 1));
                  var17.setN(var17.getN() + 1);
                  if (var21.optLong("t") > var17.getLastT()) {
                     var17.setLastT(var21.optLong("t"));
                     String var38 = var21.optString("road");
                     Intrinsics.checkNotNullExpressionValue(var38, "optString(...)");
                     if (!StringsKt.isBlank(var38)) {
                        var17.setRoad(var21.optString("road"));
                     }
                  }

                  Map var39 = var17.getCauses();
                  Integer var30 = var17.getCauses().get(var19);
                  int var28 = var15;
                  if (var30 != null) {
                     var28 = var30;
                  }

                  var39.put(var19, var28 + 1);
               } else {
                  String var31 = var21.optString("road");
                  Intrinsics.checkNotNullExpressionValue(var31, "optString(...)");
                  var18.add(
                     new JournalLogic$Companion$spots$Acc(var8, var10, 1, var31, var21.optLong("t"), MapsKt.hashMapOf(new Pair[]{TuplesKt.to(var19, 1)}))
                  );
               }
            }
         }

         Iterable var26 = CollectionsKt.sortedWith(var18, new Comparator(new Comparator() {
            @Override
            public final int compare(T var1, T var2) {
               return ComparisonsKt.compareValues(((JournalLogic$Companion$spots$Acc)var2).getN(), ((JournalLogic$Companion$spots$Acc)var1).getN());
            }
         }) {
            final Comparator $this_thenByDescending;

            {
               this.$this_thenByDescending = var1;
            }

            @Override
            public final int compare(T var1, T var2x) {
               int var3 = this.$this_thenByDescending.compare(var1, var2x);
               if (var3 == 0) {
                  var3 = ComparisonsKt.compareValues(((JournalLogic$Companion$spots$Acc)var2x).getLastT(), ((JournalLogic$Companion$spots$Acc)var1).getLastT());
               }

               return var3;
            }
         });
         var var32 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var26, 10));

         for (JournalLogic$Companion$spots$Acc var33 : var26) {
            var32.add(new JournalLogic.Companion.Spot(var33.getLat(), var33.getLon(), var33.getN(), var33.getRoad(), var33.getLastT(), var33.getCauses()));
         }

         return var32;
      }

      public final List<Pair<Double, Integer>> weekly(List<? extends JSONObject> var1, long var2, int var4) {
         Intrinsics.checkNotNullParameter(var1, "drives");
         long var11 = 604800000L;
         Iterable var20 = RangesKt.downTo(var4 - 1, 0);
         boolean var19 = false;
         var var23 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var20, 10));

         for (Iterator var24 = var20.iterator(); var24.hasNext(); var11 = var11) {
            var4 = ((IntIterator)var24).nextInt();
            long var15 = var4 + 1;
            long var13 = var2 - var4 * var11;
            Iterable var22 = var1;
            var var21 = new ArrayList();

            for (var var22_it : var22) {

               var22 = var22_it;
               long var17 = ((JSONObject)var22).optLong("t");
               boolean var27;
               if (var2 - var15 * var11 <= var17 && var17 < var13) {
                  var27 = true;
               } else {
                  var27 = false;
               }

               if (var27) {
                  var21.add(var22);
               }
            }

            List var33 = var21;
            Iterator var31 = var33.iterator();
            var15 = 0L;
            var13 = var15;

            while (var31.hasNext()) {
               var13 += ((JSONObject)var31.next()).optLong("meters");
            }

            double var7 = var13 / 1609.344;
            Iterator var35 = var33.iterator();
            var4 = 0;

            while (var35.hasNext()) {
               JSONObject var34 = (JSONObject)var35.next();
               var4 += var34.optInt("takeovers");
            }

            double var5;
            if (var7 >= 1.0) {
               var5 = var4 * 100.0 / var7;
            } else {
               var5 = Double.NaN;
            }

            var23.add(TuplesKt.to(var5, (int)Math.round(var7)));
         }

         return var23;
      }

      public static final class Spot {
         private final Map<String, Integer> causes;
         private final int count;
         private final long lastT;
         private final double lat;
         private final double lon;
         private final String road;

         public Spot(double var1, double var3, int var5, String var6, long var7, Map<String, Integer> var9) {
            Intrinsics.checkNotNullParameter(var6, "road");
            Intrinsics.checkNotNullParameter(var9, "causes");
            super();
            this.lat = var1;
            this.lon = var3;
            this.count = var5;
            this.road = var6;
            this.lastT = var7;
            this.causes = var9;
         }

         public final Map<String, Integer> getCauses() {
            return this.causes;
         }

         public final int getCount() {
            return this.count;
         }

         public final long getLastT() {
            return this.lastT;
         }

         public final double getLat() {
            return this.lat;
         }

         public final double getLon() {
            return this.lon;
         }

         public final String getRoad() {
            return this.road;
         }
      }
   }

   public static final class Sample {
      private final float aEgo;
      private final String alert1;
      private final String alert2;
      private final String alertStatus;
      private final boolean brake;
      private final boolean enabled;
      private final boolean gas;
      private final double lat;
      private final double lon;
      private final String road;
      private final boolean started;
      private final boolean steering;
      private final long tMs;
      private final float vEgo;

      public Sample(
         long var1,
         boolean var3,
         boolean var4,
         float var5,
         float var6,
         String var7,
         String var8,
         String var9,
         boolean var10,
         boolean var11,
         boolean var12,
         double var13,
         double var15,
         String var17
      ) {
         Intrinsics.checkNotNullParameter(var7, "alert1");
         Intrinsics.checkNotNullParameter(var8, "alert2");
         Intrinsics.checkNotNullParameter(var9, "alertStatus");
         Intrinsics.checkNotNullParameter(var17, "road");
         super();
         this.tMs = var1;
         this.started = var3;
         this.enabled = var4;
         this.vEgo = var5;
         this.aEgo = var6;
         this.alert1 = var7;
         this.alert2 = var8;
         this.alertStatus = var9;
         this.brake = var10;
         this.gas = var11;
         this.steering = var12;
         this.lat = var13;
         this.lon = var15;
         this.road = var17;
      }

      public final float getAEgo() {
         return this.aEgo;
      }

      public final String getAlert1() {
         return this.alert1;
      }

      public final String getAlert2() {
         return this.alert2;
      }

      public final String getAlertStatus() {
         return this.alertStatus;
      }

      public final boolean getBrake() {
         return this.brake;
      }

      public final boolean getEnabled() {
         return this.enabled;
      }

      public final boolean getGas() {
         return this.gas;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLon() {
         return this.lon;
      }

      public final String getRoad() {
         return this.road;
      }

      public final boolean getStarted() {
         return this.started;
      }

      public final boolean getSteering() {
         return this.steering;
      }

      public final long getTMs() {
         return this.tMs;
      }

      public final float getVEgo() {
         return this.vEgo;
      }
   }
}
