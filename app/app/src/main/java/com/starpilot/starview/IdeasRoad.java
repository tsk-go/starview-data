package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class IdeasRoad {
   public static final double BUMP_MPS = 5.36448;
   public static final IdeasRoad.Companion Companion = new IdeasRoad.Companion(null);
   public static final float UNSURE_STD = 0.6F;
   private Ideas.Here askHere;
   private volatile long askWhy;
   private long bumpSlowUntil;
   private double cutinFrom;
   private String cutinText;
   private long cutinUntil;
   private float leadD;
   private long leadMs;
   private long sureSince;
   private final HashMap<Long, ArrayDeque<float[]>> trackHist;
   private double unsureFrom = Double.NaN;
   private long unsureSince;
   private boolean wasEnabled;

   public IdeasRoad() {
      this.trackHist = new HashMap<>();
      this.cutinFrom = Double.NaN;
      this.cutinText = "";
      this.leadD = Float.NaN;
   }

   private final void bumps(Telemetry var1, Ideas.Here var2, long var3, List<Ideas.Part> var5) {
      if (!Double.isNaN(var2.getYaw())) {
         OsmRoads.Way var14 = Ideas.INSTANCE.street(var2);
         boolean var10;
         if (var14 != null && var14.getHighway()) {
            var10 = true;
         } else {
            var10 = false;
         }

         if (!var10) {
            Iterable var11 = IdeaSpots.around$default(
               IdeaSpots.INSTANCE,
               var2.getLat(),
               var2.getLon(),
               var2.getYaw(),
               SetsKt.setOf(new String[]{"bump", "rough", "map_bump"}),
               140.0,
               6.0,
               7.0,
               0,
               false,
               384,
               null
            );
            var var15 = new ArrayList();

            for (Object var13 : var11) {
               IdeaSpots.Seen var21 = (IdeaSpots.Seen)var13;
               if (!Intrinsics.areEqual(var21.getSpot().getKind(), "map_bump") && var21.getSpot().getCount() < 2) {
                  var10 = false;
               } else {
                  var10 = true;
               }

               if (var10) {
                  var15.add(var13);
               }
            }

            List var16 = var15;
            IdeaSpots.Seen var17 = CollectionsKt.firstOrNull(var16);
            double var6 = 5.36448;
            if (var17 == null) {
               if (var3 < this.bumpSlowUntil) {
                  var5.add(new Ideas.Part("speed_bumps", 5.36448, 0.0, 0.0, "Speed bump", 60, 12, null));
               }

               return;
            }

            if (Intrinsics.areEqual(var17.getSpot().getNote(), "cushion")) {
               var6 = 6.7056;
            }

            double var8 = Ideas.approach$default(Ideas.INSTANCE, var6, var17.getAhead() - 3.0, 0.0, 4, null);
            if (var17.getAhead() <= 3.0) {
               this.bumpSlowUntil = var3 + 1500L;
            }

            if (var8 < var2.getV() + 2.0 || var17.getAhead() < 40.0) {
               var6 = Math.max(var8, var6);
               var3 = Ideas.INSTANCE.ft(Math.max(0.0, var17.getAhead()));
               var5.add(new Ideas.Part("speed_bumps", var6, 0.0, 0.0, "Speed bump in " + var3 + " ft", 60, 12, null));
            }

            return;
         }
      }
   }

   private final void cutins(Telemetry var1, UiSettings var2, Ideas.Here var3, long var4, List<Ideas.Part> var6) {
      double var7;
      boolean var29;
      label151: {
         label150: {
            var7 = var1.getVEgo();
            if (!(var7 > 15.0)) {
               if (var3 == null) {
                  break label150;
               }

               OsmRoads.Way var15 = Ideas.INSTANCE.street(var3);
               if (var15 != null && var15.getHighway()) {
                  var29 = (boolean)1;
               } else {
                  var29 = (boolean)0;
               }

               if (!var29) {
                  break label150;
               }
            }

            var29 = 1;
            break label151;
         }

         var29 = 0;
      }

      HashSet var18 = new HashSet();
      String var32 = null;
      ModelSnapshot var19 = var1.getModel();
      RadarTrack[] var16 = var1.getRadar();
      int var12 = var16.length;
      int var13 = 0;

      while (var13 < var12) {
         RadarTrack var20 = var16[var13];
         var18.add(var20.getId());
         Map var22 = this.trackHist;
         Long var21 = var20.getId();
         ArrayDeque var17 = (ArrayDeque)var22.get(var21);
         if (var17 == null) {
            var17 = new ArrayDeque();
            var22.put(var21, var17);
         }

         var17 = var17;
         float var9;
         if (var19 != null && var19.getPosX().length > 2 && var20.getDRel() > 0.0F) {
            var9 = Companion.interp(var20.getDRel(), var19.getPosX(), var19.getPosY());
         } else {
            var9 = 0.0F;
         }

         var17.addLast(new float[]{(float)(var4 % 1000000L), var20.getDRel(), Math.abs(var20.getYRel() + var9)});

         while (var17.size() > 12) {
            var17.removeFirst();
         }

         String var35;
         if (var29 && var17.size() >= 6) {
            var9 = var20.getDRel();
            boolean var14;
            if (-3.0F <= var9 && var9 <= 40.0F) {
               var14 = true;
            } else {
               var14 = false;
            }

            if (!var14) {
               var35 = var32;
            } else {
               float[] var36 = (float[])var17.first();
               float[] var37 = (float[])var17.last();
               var9 = (var37[0] - var36[0]) / 1000.0F;
               var35 = var32;
               if (!(var9 < 0.4F)) {
                  float var10 = (var36[2] - var37[2]) / var9;
                  var9 = var37[2];
                  if (1.6F <= var9 && var9 <= 4.6F) {
                     var14 = true;
                  } else {
                     var14 = false;
                  }

                  var35 = var32;
                  if (var14) {
                     var35 = var32;
                     if (var10 > 0.45F) {
                        var35 = "Car drifting into your lane - making room";
                     }
                  }
               }
            }
         } else {
            var35 = var32;
         }

         var13++;
         var32 = var35;
      }

      this.trackHist.keySet().retainAll(var18);
      String var33;
      if (var32 == null) {
         var33 = var32;
         if (Ideas.INSTANCE.on(var2, "wheels")) {
            String var25 = Ideas.INSTANCE.getSense().cameraDrift(var4);
            var33 = var32;
            if (var25 != null) {
               var33 = var25;
            }
         }
      } else {
         var33 = var32;
      }

      if (var33 != null && var29) {
         this.cutinUntil = 3000L + var4;
         if (Double.isNaN(this.cutinFrom)) {
            this.cutinFrom = var7;
         }

         this.cutinText = var33;
      }

      Lead var23 = var1.getLeadOne();
      if (var23.getStatus()) {
         if (!Float.isNaN(this.leadD)
            && var4 - this.leadMs < 400L
            && this.leadD - var23.getDRel() > 8.0F
            && var23.getDRel() < 30.0F
            && var29
            && var3 != null
            && !Double.isNaN(var3.getYaw())) {
            IdeaSpots.record$default(IdeaSpots.INSTANCE, "cutin", var3.getLat(), var3.getLon(), var3.getHdgDeg(), 0.0, null, 0L, 60.0, null, 368, null);
         }

         this.leadD = var23.getDRel();
         this.leadMs = var4;
      } else {
         this.leadD = Float.NaN;
      }

      if (var4 < this.cutinUntil) {
         var6.add(new Ideas.Part("nyc_cutins", Math.max(4.4704, this.cutinFrom - 1.5), 0.5, 0.0, this.cutinText, 70, 8, null));
      } else {
         this.cutinFrom = Double.NaN;
         if (var29 && var3 != null && !Double.isNaN(var3.getYaw())) {
            IdeaSpots.Seen var24 = CollectionsKt.firstOrNull(
               IdeaSpots.around$default(
                  IdeaSpots.INSTANCE, var3.getLat(), var3.getLon(), var3.getYaw(), SetsKt.setOf("cutin"), 400.0, 50.0, 25.0, 2, false, 256, null
               )
            );
            if (var24 != null) {
               var29 = var24.getSpot().getCount();
               var6.add(new Ideas.Part("nyc_cutins", null, 0.3, 0.0, "Cut-in spot - extra room (" + var29 + "x)", 20, 10, null));
            }
         }
      }
   }

   private final void learnJolts(UiSettings var1, Ideas.Here var2, TabletMotion var3, long var4) {
      while (true) {
         TabletMotion.Jolt var16 = var3.poll();
         if (var16 == null) {
            return;
         }

         if (var2 != null && !Double.isNaN(var2.getYaw())) {
            String var17 = var16.getKind();
            switch (var17.hashCode()) {
               case -390630251:
                  if (!var17.equals("pothole") || !Ideas.INSTANCE.on(var1, "potholes")) {
                     continue;
                  }

                  double var19 = Ideas.INSTANCE.lateralOnStreet(var2);
                  IdeaSpots var26 = IdeaSpots.INSTANCE;
                  double var20 = var2.getLat();
                  double var12 = var2.getLon();
                  double var21 = var2.getHdgDeg();
                  if (!Double.isNaN(var19)) {
                     var19 += var16.getSide() * 0.8;
                  }

                  String var23;
                  if (var16.getSide() < 0) {
                     var23 = "left wheel";
                  } else {
                     var23 = "right wheel";
                  }

                  IdeaSpots.record$default(var26, "pothole", var20, var12, var21, var19, var23, 0L, 4.0, null, 320, null);
                  continue;
               case 3035446:
                  if (!var17.equals("bump")) {
                     continue;
                  }
                  break;
               case 108703929:
                  if (!var17.equals("rough")) {
                     continue;
                  }
                  break;
               default:
                  continue;
            }

            if (Ideas.INSTANCE.on(var1, "speed_bumps") && var2.getV() < 13.5) {
               OsmRoads.Way var24 = Ideas.INSTANCE.street(var2);
               boolean var15 = false;
               boolean var14 = var15;
               if (var24 != null) {
                  var14 = var15;
                  if (var24.getHighway()) {
                     var14 = true;
                  }
               }

               if (!var14) {
                  IdeaSpots var25 = IdeaSpots.INSTANCE;
                  String var18 = var16.getKind();
                  double var10 = var2.getLat();
                  double var6 = var2.getLon();
                  double var8 = var2.getHdgDeg();
                  String var22 = String.format("felt %.1f", Arrays.copyOf(new Object[]{var16.getSize()}, 1));
                  Intrinsics.checkNotNullExpressionValue(var22, "format(...)");
                  IdeaSpots.record$default(var25, var18, var10, var6, var8, 0.0, var22, 0L, 0.0, null, 464, null);
               }
            }
         }
      }
   }

   private final void potholes(Telemetry var1, Ideas.Here var2, List<Ideas.Part> var3) {
      if (!Double.isNaN(var2.getYaw())) {
         IdeaSpots.Seen var9 = CollectionsKt.firstOrNull(
            IdeaSpots.around$default(
               IdeaSpots.INSTANCE, var2.getLat(), var2.getLon(), var2.getYaw(), SetsKt.setOf("pothole"), 60.0, 2.0, 4.0, 0, false, 384, null
            )
         );
         if (var9 != null) {
            double var6 = Ideas.INSTANCE.lateralOnStreet(var2);
            double var4 = var9.getSpot().getLateral();
            if (!Double.isNaN(var6) && !Double.isNaN(var4)) {
               var4 -= var6;
               if (Math.abs(Math.abs(var4) - 0.8) < 0.45) {
                  if (var4 > 0.0) {
                     var4 = -0.3;
                  } else {
                     var4 = 0.3;
                  }

                  var3.add(new Ideas.Part("potholes", null, 0.0, var4, "Pothole ahead - moving over a little", 40, 6, null));
                  return;
               }

               if (Math.abs(var4) > 1.4) {
                  return;
               }
            }

            Double var10 = Math.max(6.7056, var2.getV() - 2.2);
            var10.doubleValue();
            boolean var8;
            if (var9.getAhead() < 40.0) {
               var8 = true;
            } else {
               var8 = false;
            }

            if (!var8) {
               var10 = null;
            }

            var3.add(new Ideas.Part("potholes", var10, 0.0, 0.0, "Pothole ahead", 40, 12, null));
         }
      }
   }

   private final void spots(Telemetry var1, Ideas.Here var2, List<Ideas.Part> var3) {
      if (!Double.isNaN(var2.getYaw())) {
         IdeaSpots.Seen var8 = CollectionsKt.firstOrNull(
            IdeaSpots.around$default(
               IdeaSpots.INSTANCE, var2.getLat(), var2.getLon(), var2.getYaw(), SetsKt.setOf("takeover"), 150.0, 20.0, 15.0, 2, false, 256, null
            )
         );
         if (var8 != null) {
            double var4 = Math.max(4.4704, var2.getV() - 1.3);
            int var7 = var8.getSpot().getCount();
            String var9 = var8.getSpot().getNote();
            boolean var6;
            if (var9.length() > 0) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (!var6) {
               var9 = null;
            }

            label25: {
               if (var9 != null) {
                  String var11 = " (" + var9 + ")";
                  var10 = var11;
                  if (var11 != null) {
                     break label25;
                  }
               }

               var10 = "";
            }

            var3.add(new Ideas.Part("spot_settings", var4, 0.3, 0.0, "You took over here " + var7 + "x" + var10, 25, 8, null));
         }
      }
   }

   private final void takeovers(Telemetry var1, UiSettings var2, Ideas.Here var3, long var4) {
      boolean var6 = var1.getEnabled();
      if (this.wasEnabled && !var6 && var1.getVEgo() > 2.5F) {
         if (var3 != null && (Ideas.INSTANCE.on(var2, "spot_settings") || Ideas.INSTANCE.on(var2, "takeover_why"))) {
            IdeaSpots.record$default(IdeaSpots.INSTANCE, "takeover", var3.getLat(), var3.getLon(), var3.getHdgDeg(), 0.0, null, 0L, 60.0, null, 368, null);
         }

         if (Ideas.INSTANCE.on(var2, "takeover_why")) {
            this.askWhy = var4;
            this.askHere = var3;
         }
      }

      this.wasEnabled = var6;
   }

   private final void unsure(Telemetry var1, long var2, List<Ideas.Part> var4) {
      ModelSnapshot var11 = var1.getModel();
      if (var11 != null) {
         double var5 = var1.getVEgo();
         float var7;
         if (var11.getPosYStd().length == var11.getPosX().length && var11.getPosX().length > 2) {
            var7 = Companion.interp(20.0F, var11.getPosX(), var11.getPosYStd());
         } else {
            var7 = Float.NaN;
         }

         boolean var10 = Float.isInfinite(var7);
         boolean var9 = true;
         boolean var8;
         if (!var10 && !Float.isNaN(var7)) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8 && var7 > 0.6F && var5 > 6.0 && var1.getEnabled()) {
            var8 = var9;
         } else {
            var8 = false;
         }

         if (var8) {
            if (this.unsureSince == 0L) {
               this.unsureSince = var2;
            }

            this.sureSince = 0L;
         } else {
            if (this.sureSince == 0L) {
               this.sureSince = var2;
            }

            if (var2 - this.sureSince > 3000L) {
               this.unsureSince = 0L;
               this.unsureFrom = Double.NaN;
            }
         }

         if (this.unsureSince != 0L && var2 - this.unsureSince > 1000L) {
            if (Double.isNaN(this.unsureFrom)) {
               this.unsureFrom = var5;
            }

            var4.add(new Ideas.Part("unsure_slow", Math.max(4.4704, this.unsureFrom - 2.2), 0.0, 0.0, "Model unsure - 5 mph slower", 30, 12, null));
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void answerWhy(Context var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      long var4 = this.askWhy;
      this.askWhy = 0L;
      if (var2 != null && var4 != 0L) {
         Ideas.Here var9 = this.askHere;
         if (var9 != null) {
            IdeaSpots.Spot var6 = IdeaSpots.record$default(
               IdeaSpots.INSTANCE, "takeover", var9.getLat(), var9.getLon(), var9.getHdgDeg(), 0.0, var2, 0L, 60.0, null, 336, null
            );
            var6.setCount(var6.getCount() - 1);
         }

         boolean var3;
         File var8;
         try {
            var8 = new File(var1.getFilesDir(), "takeover_why.csv");
            var3 = var8.exists();
         } catch (Exception var18) {
            return;
         }

         if (!var3) {
            try {
               FilesKt.writeText$default(var8, "time,lat,lon,mph,why\n", null, 2, null);
            } catch (Exception var17) {
               return;
            }
         }

         Date var21;
         try {
            var19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
         } catch (Exception var16) {
            return;
         }

         String var10;
         try {
            var21 = new Date(var4);
            var10 = var19.format(var21);
         } catch (Exception var15) {
            return;
         }

         Object var7 = "";
         Object var20;
         if (var9 != null) {
            try {
               var20 = var9.getLat();
            } catch (Exception var14) {
               return;
            }
         } else {
            var20 = "";
         }

         Object var22;
         if (var9 != null) {
            try {
               var22 = var9.getLon();
            } catch (Exception var13) {
               return;
            }
         } else {
            var22 = "";
         }

         if (var9 != null) {
            try {
               var7 = Math.round(Ideas.INSTANCE.mph(var9.getV()));
            } catch (Exception var12) {
               return;
            }
         }

         try {
            StringBuilder var23 = new StringBuilder();
            FilesKt.appendText$default(
               var8,
               var23.append(var10)
                  .append(",")
                  .append(var20)
                  .append(",")
                  .append(var22)
                  .append(",")
                  .append(var7)
                  .append(",")
                  .append(var2)
                  .append("\n")
                  .toString(),
               null,
               2,
               null
            );
         } catch (Exception var11) {
         }
      }
   }

   public final long getAskWhy() {
      return this.askWhy;
   }

   public final void tick(Telemetry var1, UiSettings var2, Ideas.Here var3, TabletMotion var4, long var5, List<Ideas.Part> var7) {
      Intrinsics.checkNotNullParameter(var1, "t");
      Intrinsics.checkNotNullParameter(var2, "s");
      Intrinsics.checkNotNullParameter(var7, "out");
      if (Ideas.INSTANCE.on(var2, "speed_bumps") && var3 != null) {
         MapBumps.INSTANCE.want(var3.getLat(), var3.getLon());
         this.bumps(var1, var3, var5, var7);
      }

      if (var4 != null) {
         this.learnJolts(var2, var3, var4, var5);
      }

      if (Ideas.INSTANCE.on(var2, "potholes") && var3 != null) {
         this.potholes(var1, var3, var7);
      }

      if (Ideas.INSTANCE.on(var2, "nyc_cutins")) {
         this.cutins(var1, var2, var3, var5, var7);
      }

      if (Ideas.INSTANCE.on(var2, "unsure_slow")) {
         this.unsure(var1, var5, var7);
      }

      if (Ideas.INSTANCE.on(var2, "spot_settings") && var3 != null) {
         this.spots(var1, var3, var7);
      }

      this.takeovers(var1, var2, var3, var5);
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final float interp(float var1, float[] var2, float[] var3) {
         Intrinsics.checkNotNullParameter(var2, "xs");
         Intrinsics.checkNotNullParameter(var3, "ys");
         if (var1 <= var2[0]) {
            return var3[0];
         } else {
            int var4 = 1;

            for (int var5 = var2.length; var4 < var5; var4++) {
               if (var2[var4] >= var1) {
                  var1 = (var1 - var2[var4 - 1]) / Math.max(0.001F, var2[var4] - var2[var4 - 1]);
                  return var3[var4 - 1] + (var3[var4] - var3[var4 - 1]) * var1;
               }
            }

            return ArraysKt.last(var3);
         }
      }
   }
}
