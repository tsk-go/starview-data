package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

public final class LaneRamps {
   public static final double EXIT_AHEAD = 320.0;
   public static final LaneRamps INSTANCE = new LaneRamps();
   public static final double MERGE_BEHIND = 260.0;

   // $VF: synthetic method
   public static CharSequence $r8$lambda$Hp_JCuq0ibYmyhRxvOuHCoKJaYc(LaneRamps.Ramp var0) {
      return codes$lambda$4(var0);
   }

   private LaneRamps() {
   }

   private static final CharSequence codes$lambda$4(LaneRamps.Ramp var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.code();
   }

   public final String codes(List<LaneRamps.Ramp> var1) {
      Intrinsics.checkNotNullParameter(var1, "l");
      return CollectionsKt.joinToString$default(var1, ";", null, null, 0, null, new LaneRamps$$ExternalSyntheticLambda0(), 30, null);
   }

   public final double distTo(OsmRoads.Way var1, double var2, double var4, double var6, double var8) {
      Intrinsics.checkNotNullParameter(var1, "w");
      double var10 = Double.MAX_VALUE;
      int var23 = 0;

      for (int var22 = var1.getE().length - 1; var23 < var22; var23++) {
         double var18 = var1.getE()[var23];
         double var16 = var1.getN()[var23];
         double var14 = var1.getE()[var23 + 1] - var18;
         double var20 = var1.getN()[var23 + 1] - var16;
         double var12 = var14 * var14 + var20 * var20;
         if (var1.getOneway() == 0 || !(var12 > 1.0E-6) || !(var1.getOneway() * (var14 * var6 + var20 * var8) / Math.sqrt(var12) < 0.3)) {
            if (var12 < 1.0E-6) {
               var12 = 0.0;
            } else {
               var12 = RangesKt.coerceIn(((var2 - var18) * var14 + (var4 - var16) * var20) / var12, 0.0, 1.0);
            }

            var10 = Math.min(var10, Math.hypot(var2 - (var12 * var14 + var18), var4 - (var12 * var20 + var16)));
         }
      }

      return var10;
   }

   public final List<LaneRamps.Ramp> parse(String var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      Iterable var12 = StringsKt.split$default(var1, new String[]{";"}, false, 0, 6, null);
      boolean var6 = false;
      var var10 = new ArrayList();
      boolean var5 = false;
      Iterator var9 = var12.iterator();

      while (var9.hasNext()) {
         List var11 = StringsKt.split$default((String & CharSequence)var9.next(), new String[]{","}, false, 0, 6, null);
         LaneRamps.Ramp var8;
         if (var11.size() < 3) {
            var8 = null;
         } else {
            String var13 = (String)var11.get(0);
            byte var4;
            if (Intrinsics.areEqual(var11.get(1), "L")) {
               var4 = -1;
            } else {
               var4 = 1;
            }

            Double var14 = StringsKt.toDoubleOrNull((String)var11.get(2));
            double var2;
            if (var14 != null) {
               var2 = var14;
            } else {
               var2 = 0.0;
            }

            var8 = new LaneRamps.Ramp(var13, var4, var2);
         }

         if (var8 != null) {
            var10.add(var8);
         }
      }

      return var10;
   }

   public final List<LaneRamps.Ramp> scan(List<OsmRoads.Way> var1, OsmRoads.Way var2, double var3, double var5, double var7) {
      Intrinsics.checkNotNullParameter(var1, "ways");
      Intrinsics.checkNotNullParameter(var2, "our");
      long var25 = var2.getId();
      Iterable var30 = var1;
      var var29 = new ArrayList();

      for (Object var31 : var30) {
         boolean var44;
         label155: {
            label154: {
               OsmRoads.Way var54 = (OsmRoads.Way)var31;
               if (var54.getId() != var25) {
                  if (StringsKt.endsWith$default(var54.getKind(), "_link", false, 2, null) || !Intrinsics.areEqual(var54.getKind(), var2.getKind())) {
                     break label154;
                  }

                  if (var2.getName().length() > 0) {
                     var44 = true;
                  } else {
                     var44 = false;
                  }

                  if (!var44 || !OsmRoads.INSTANCE.sameStreet(var54.getName(), var2.getName())) {
                     break label154;
                  }
               }

               var44 = true;
               break label155;
            }

            var44 = false;
         }

         if (var44) {
            var29.add(var31);
         }
      }

      List var48 = var29;
      Collection var55 = var48;
      Iterator var49 = var55;
      if (var55.isEmpty()) {
         var49 = CollectionsKt.<OsmRoads.Way>listOf(var2);
      }

      List var35 = (List)var49;
      double var9 = Math.cos(var7);
      double var13 = Math.sin(var7);
      HashMap var56 = new HashMap();
      Iterator var57 = var1.iterator();
      var7 = var9;
      var1 = var35;

      while (var57.hasNext()) {
         var2 = (OsmRoads.Way)var57.next();
         if (var2.getId() != var25 && StringsKt.endsWith$default(var2.getKind(), "_link", false, 2, null) && var2.getE().length >= 2) {
            for (int var45 = 0; var45 < 2; var45++) {
               int var22;
               if (var45 == 0) {
                  var22 = 0;
               } else {
                  var22 = var2.getE().length - 1;
               }

               double var11 = var2.getE()[var22] - var3;
               var9 = var2.getN()[var22] - var5;
               double var15 = var11 * var7 + var9 * var13;
               if (!(Math.abs(var11 * var13 - var9 * var7) > 40.0)) {
                  var49 = var1.iterator();
                  if (!var49.hasNext()) {
                     throw new NoSuchElementException();
                  }

                  OsmRoads.Way var58 = (OsmRoads.Way)var49.next();
                  var9 = INSTANCE.distTo(var58, var2.getE()[var22], var2.getN()[var22], var7, var13);

                  while (var49.hasNext()) {
                     var58 = (OsmRoads.Way)var49.next();
                     var9 = Math.min(var9, INSTANCE.distTo(var58, var2.getE()[var22], var2.getN()[var22], var7, var13));
                  }

                  long var27 = var25;
                  if (var9 > 10.0) {
                     var25 = var25;
                  } else {
                     int var23;
                     if (var45 == 0) {
                        var23 = var2.getE().length - 1;
                     } else {
                        var23 = 0;
                     }

                     double[] var51 = var2.getE();
                     int var24;
                     if (var45 == 0) {
                        var24 = Math.min(var51.length - 1, 1);
                     } else {
                        var24 = Math.max(0, var51.length - 2);
                     }

                     double var17 = var2.getE()[var23] - var2.getE()[var22];
                     var9 = var2.getN()[var23];
                     var11 = var2.getN()[var22];
                     double var19 = var9 - var11;
                     if (Math.hypot(var17, var19) < 30.0) {
                        var25 = var25;
                     } else {
                        var11 = (var2.getE()[var24] - var2.getE()[var22]) * var13 - (var2.getN()[var24] - var2.getN()[var22]) * var7;
                        var9 = var11;
                        if (Math.abs(var11) < 1.0) {
                           var9 = var17 * var13 - var19 * var7;
                        }

                        byte var46;
                        if (var9 > 0.0) {
                           var46 = 1;
                        } else {
                           var46 = -1;
                        }

                        String var52;
                        if (var17 * var7 + var19 * var13 > 0.0) {
                           var52 = "exit";
                        } else {
                           var52 = "merge";
                        }

                        boolean var47;
                        if (Intrinsics.areEqual(var52, "exit") ? !(-20.0 <= var15) || !(var15 <= 320.0) : !(-260.0 <= var15) || !(var15 <= 60.0)) {
                           var47 = false;
                        } else {
                           var47 = true;
                        }

                        if (var47) {
                           LaneRamps.Ramp var60 = new LaneRamps.Ramp(var52, var46, var15);
                           LaneRamps.Ramp var53 = (LaneRamps.Ramp)var56.get(Integer.valueOf(var46));
                           if (var53 != null) {
                              var25 = var25;
                              if (!(Math.abs(var60.getDist()) < Math.abs(var53.getDist()))) {
                                 continue;
                              }
                           }

                           var56.put(Integer.valueOf(var46), var60);
                           var25 = var27;
                        } else {
                           var25 = var25;
                        }
                     }
                  }
               }
            }
         }
      }

      Collection var34 = var56.values();
      Intrinsics.checkNotNullExpressionValue(var34, "<get-values>(...)");
      return CollectionsKt.sortedWith(var34, new Comparator() {
         @Override
         public final int compare(T var1, T var2x) {
            return ComparisonsKt.compareValues(((LaneRamps.Ramp)var1).getSide(), ((LaneRamps.Ramp)var2x).getSide());
         }
      });
   }

   public static final class Ramp {
      private final double dist;
      private final String kind;
      private final int side;

      public Ramp(String var1, int var2, double var3) {
         Intrinsics.checkNotNullParameter(var1, "kind");
         super();
         this.kind = var1;
         this.side = var2;
         this.dist = var3;
      }

      public final String code() {
         String var4 = this.kind;
         String var3;
         if (this.side < 0) {
            var3 = "L";
         } else {
            var3 = "R";
         }

         long var1 = Math.round(this.dist);
         return var4 + "," + var3 + "," + var1;
      }

      public final double getDist() {
         return this.dist;
      }

      public final long getFeet() {
         return Math.round(Math.abs(this.dist) * 3.28084 / 10.0) * 10;
      }

      public final String getKind() {
         return this.kind;
      }

      public final int getSide() {
         return this.side;
      }

      public final String text() {
         String var6 = this.kind;
         String var5 = "exit";
         if (!Intrinsics.areEqual(var6, "exit")) {
            var5 = "merge";
         }

         if (this.side < 0) {
            var6 = "left";
         } else {
            var6 = "right";
         }

         double var1 = this.dist;
         long var3 = this.getFeet();
         String var7;
         StringBuilder var8;
         if (var1 >= 0.0) {
            var8 = new StringBuilder().append(", ").append(var3);
            var7 = " ft ahead";
         } else {
            var8 = new StringBuilder().append(", ").append(var3);
            var7 = " ft behind";
         }

         var7 = var8.append(var7).toString();
         return var5 + " on the " + var6 + var7;
      }
   }
}
