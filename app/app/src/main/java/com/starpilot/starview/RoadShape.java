package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

public final class RoadShape {
   public static final double CORNER = 8.0;
   public static final RoadShape INSTANCE = new RoadShape();

   private RoadShape() {
   }

   private final long key(double var1, double var3) {
      return Math.round(var1 * 10.0) * 4000003L + Math.round(10.0 * var3);
   }

   private final boolean near(OsmRoads.Way var1, double var2, double var4) {
      int var6 = 0;

      for (int var7 = var1.getE().length; var6 < var7; var6++) {
         if (Math.hypot(var1.getE()[var6] - var2, var1.getN()[var6] - var4) < 1.0) {
            return true;
         }
      }

      return false;
   }

   private static final void round$add(ArrayList<Double> var0, ArrayList<Double> var1, double var2, double var4) {
      if (var0.isEmpty() || Math.abs(CollectionsKt.last(var0).doubleValue() - var2) + Math.abs(CollectionsKt.last(var1).doubleValue() - var4) > 1.0E-6) {
         var0.add(var2);
         var1.add(var4);
      }
   }

   private static final double[] rounded$goesOn(
      OsmRoads.Way var0,
      double var1,
      double var3,
      double var5,
      ArrayList<double[]> var7,
      ObjectRef<HashMap<Long, List<Pair<OsmRoads.Way, Integer>>>> var8,
      List<OsmRoads.Way> var9,
      int var10
   ) {
      if (var10 == 0) {
         var10 = 0;
      } else {
         var10 = var0.getE().length - 1;
      }

      if (Math.hypot(var0.getE()[var10] - var1, var0.getN()[var10] - var3) >= var5) {
         var7.add(new double[]{var0.getE()[var10], var0.getN()[var10]});
         return null;
      } else {
         List var12 = rounded$index(var8, var9).get(INSTANCE.key(var0.getE()[var10], var0.getN()[var10]));
         if (var12 == null) {
            return null;
         } else {
            Iterable var16 = var12;
            var7 = new ArrayList();

            for (Object var19 : var16) {
               boolean var21;
               if (((OsmRoads.Way)((Pair)var19).getFirst()).getId() != var0.getId()) {
                  var21 = true;
               } else {
                  var21 = false;
               }

               if (var21) {
                  var7.add(var19);
               }
            }

            List var14 = var7;
            if (var14.size() != 1) {
               return null;
            } else {
               Pair var18 = (Pair)var14.get(0);
               OsmRoads.Way var15 = (OsmRoads.Way)var18.component1();
               int var11 = ((Number)var18.component2()).intValue();
               if (var11 != 0 && var11 != var15.getE().length - 1) {
                  return null;
               } else if (Intrinsics.areEqual(StringsKt.removeSuffix(var15.getKind(), "_link"), StringsKt.removeSuffix(var0.getKind(), "_link"))
                  || var15.getHighway() && var0.getHighway()) {
                  boolean var22;
                  if (var15.getName().length() > 0) {
                     var22 = 1;
                  } else {
                     var22 = 0;
                  }

                  if (var22) {
                     if (var0.getName().length() > 0) {
                        var22 = (boolean)1;
                     } else {
                        var22 = (boolean)0;
                     }

                     if (var22
                        && !OsmRoads.INSTANCE.sameStreet(var15.getName(), var0.getName())
                        && !StringsKt.endsWith$default(var15.getKind(), "_link", false, 2, null)
                        && !StringsKt.endsWith$default(var0.getKind(), "_link", false, 2, null)) {
                        return null;
                     }
                  }

                  if (var11 == 0) {
                     var22 = 1;
                  } else {
                     var22 = var15.getE().length - 2;
                  }

                  boolean var25;
                  if (var22 >= 0 && var22 < var15.getE().length) {
                     var25 = true;
                  } else {
                     var25 = false;
                  }

                  return !var25 ? null : new double[]{var15.getE()[var22], var15.getN()[var22]};
               } else {
                  return null;
               }
            }
         }
      }
   }

   private static final HashMap<Long, List<Pair<OsmRoads.Way, Integer>>> rounded$index(
      ObjectRef<HashMap<Long, List<Pair<OsmRoads.Way, Integer>>>> var0, List<OsmRoads.Way> var1
   ) {
      HashMap var4 = (HashMap)var0.element;
      if (var4 != null) {
         return var4;
      } else {
         var4 = new HashMap();

         for (OsmRoads.Way var6 : var1) {
            int var2 = 0;

            for (int var3 = var6.getE().length; var2 < var3; var2++) {
               Map var7 = var4;
               Long var8 = INSTANCE.key(var6.getE()[var2], var6.getN()[var2]);
               Object var9 = var7.get(var8);
               if (var9 == null) {
                  var9 = new ArrayList(2);
                  var7.put(var8, var9);
               }

               ((List)var9).add(TuplesKt.to(var6, var2));
            }
         }

         var0.element = (T)var4;
         return var4;
      }
   }

   // $VF: synthetic method
   public static Pair through$default(RoadShape var0, double[] var1, double[] var2, double[] var3, double[] var4, double var5, int var7, Object var8) {
      if ((var7 & 16) != 0) {
         var5 = 3.0;
      }

      return var0.through(var1, var2, var3, var4, var5);
   }

   private static final double through$kn(double var0, double var2, double var4, double var6) {
      return RangesKt.coerceAtLeast(Math.sqrt(Math.hypot(var4 - var0, var6 - var2)), 1.0E-4);
   }

   private static final boolean through$sharp(double var0, double var2, double var4, double var6, double var8, double var10) {
      double var12 = Math.hypot(var4 - var0, var6 - var2);
      double var14 = Math.hypot(var8 - var4, var10 - var6);
      boolean var16 = true;
      if (!(var12 < 1.0E-6) && !(var14 < 1.0E-6)) {
         if (!(((var4 - var0) * (var8 - var4) + (var6 - var2) * (var10 - var6)) / (var12 * var14) < 0.17)) {
            var16 = false;
         }

         return var16;
      } else {
         return true;
      }
   }

   public final List<double[]> crossings(OsmRoads.Way var1, OsmRoads.Way var2) {
      Intrinsics.checkNotNullParameter(var1, "a");
      Intrinsics.checkNotNullParameter(var2, "b");
      if (!(var1.getMaxE() < var2.getMinE()) && !(var2.getMaxE() < var1.getMinE()) && !(var1.getMaxN() < var2.getMinN()) && !(var2.getMaxN() < var1.getMinN())) {
         ArrayList var28 = new ArrayList();
         int var21 = var1.getE().length;
         boolean var26 = true;
         int var23 = var21 - 1;

         for (int var22 = 0; var22 < var23; var22++) {
            double var3 = var1.getE()[var22];
            double var5 = var1.getN()[var22];
            double var9 = var1.getE()[var22 + 1] - var3;
            double var7 = var1.getN()[var22 + 1] - var5;
            int var25 = var2.getE().length - 1;
            int var24 = 0;

            while (var24 < var25) {
               double var15 = var2.getE()[var24];
               double var11 = var2.getN()[var24];
               double var19 = var2.getE()[var24 + 1] - var15;
               double var13 = var2.getN()[var24 + 1] - var11;
               if (!(Math.max(var3, var3 + var9) < Math.min(var15, var15 + var19))
                  && !(Math.max(var15, var15 + var19) < Math.min(var3, var3 + var9))
                  && !(Math.max(var5, var5 + var7) < Math.min(var11, var11 + var13))
                  && !(Math.max(var11, var11 + var13) < Math.min(var5, var5 + var7))) {
                  double var17 = var9 * var13 - var7 * var19;
                  if (!(Math.abs(var17) < 1.0E-9)) {
                     var13 = ((var15 - var3) * var13 - (var11 - var5) * var19) / var17;
                     var11 = ((var15 - var3) * var7 - (var11 - var5) * var9) / var17;
                     if (!(var13 < -1.0E-9) && !(var13 > 1.000000001) && !(var11 < -1.0E-9) && !(var11 > 1.000000001)) {
                        var11 = var3 + var13 * var9;
                        var13 = var5 + var13 * var7;
                        if (!this.near(var1, var11, var13) || !this.near(var2, var11, var13)) {
                           var var27 = var28;
                           var26 = false;
                           boolean var36;
                           if (var27 instanceof Collection && var27.isEmpty()) {
                              var36 = true;
                           } else {
                              label119: {
                                 for (double[] var30 : var27) {
                                    if (Math.hypot(var30[0] - var11, var30[1] - var13) < 5.0) {
                                       var36 = true;
                                    } else {
                                       var36 = false;
                                    }

                                    if (var36) {
                                       var36 = false;
                                       break label119;
                                    }
                                 }

                                 var36 = true;
                              }
                           }

                           if (var36) {
                              var28.add(new double[]{var11, var13});
                           }
                        }
                     }
                  }
               }

               var24++;
               var26 = true;
            }
         }

         return var28;
      } else {
         return CollectionsKt.emptyList();
      }
   }

   public final double[] heights(double[] var1, double[] var2, int var3, List<double[]> var4, double var5, double var7, double var9) {
      Intrinsics.checkNotNullParameter(var1, "xs");
      Intrinsics.checkNotNullParameter(var2, "ys");
      Intrinsics.checkNotNullParameter(var4, "cross");
      double[] var14 = new double[var3];

      for (int var13 = 0; var13 < var3; var13++) {
         double var11 = Double.MAX_VALUE;

         for (double[] var16 : var4) {
            var11 = Math.min(var11, Math.hypot(var1[var13] - var16[0], var2[var13] - var16[1]));
         }

         var11 = RangesKt.coerceIn((var5 + var7 - var11) / var7, 0.0, 1.0);
         var14[var13] = var9 * var11 * var11 * (3 - 2 * var11);
      }

      return var14;
   }

   public final Pair<double[], double[]> round(double[] var1, double[] var2, double[] var3, double[] var4, double var5) {
      Intrinsics.checkNotNullParameter(var1, "e");
      Intrinsics.checkNotNullParameter(var2, "n");
      int var36 = var1.length;
      if (var36 < 2) {
         return TuplesKt.to(var1, var2);
      } else {
         ArrayList var45 = new ArrayList(var36 + 2);
         ArrayList var44 = new ArrayList(var36 + 2);
         if (var3 != null) {
            var45.add(var3[0]);
            var44.add(var3[1]);
         }

         for (int var29 = 0; var29 < var36; var29++) {
            var45.add(var1[var29]);
            var44.add(var2[var29]);
         }

         if (var4 != null) {
            var45.add(var4[0]);
            var44.add(var4[1]);
         }

         ArrayList var42 = new ArrayList(var36 * 3);
         ArrayList var43 = new ArrayList(var36 * 3);
         byte var63;
         if (var3 != null) {
            var63 = 1;
         } else {
            var63 = 0;
         }

         int var31 = var45.size();
         byte var30;
         if (var4 != null) {
            var30 = 1;
         } else {
            var30 = 0;
         }

         var31 = var31 - 1 - var30;
         if (var3 == null) {
            Object var46 = var45.get(0);
            Intrinsics.checkNotNullExpressionValue(var46, "get(...)");
            double var7 = ((Number)var46).doubleValue();
            var46 = var44.get(0);
            Intrinsics.checkNotNullExpressionValue(var46, "get(...)");
            round$add(var42, var43, var7, ((Number)var46).doubleValue());
         }

         byte var35 = (byte)var63;
         var63 = var45.size() - 1;
         var30 = 1;

         while (var30 < var63) {
            if (var30 >= var35) {
               int var75 = var31;
               if (var30 > var31) {
                  var31 = var30;
                  var63 = var31;
                  var30 = var63;
               } else {
                  Object var83 = var45.get(var30 - 1);
                  Intrinsics.checkNotNullExpressionValue(var83, "get(...)");
                  double var15 = ((Number)var83).doubleValue();
                  var83 = var44.get(var30 - 1);
                  Intrinsics.checkNotNullExpressionValue(var83, "get(...)");
                  double var17 = ((Number)var83).doubleValue();
                  var83 = var45.get(var30);
                  Intrinsics.checkNotNullExpressionValue(var83, "get(...)");
                  double var9 = ((Number)var83).doubleValue();
                  var83 = var44.get(var30);
                  Intrinsics.checkNotNullExpressionValue(var83, "get(...)");
                  double var11 = ((Number)var83).doubleValue();
                  var83 = var45.get(var30 + 1);
                  Intrinsics.checkNotNullExpressionValue(var83, "get(...)");
                  double var19 = ((Number)var83).doubleValue();
                  var83 = var44.get(var30 + 1);
                  Intrinsics.checkNotNullExpressionValue(var83, "get(...)");
                  double var21 = ((Number)var83).doubleValue();
                  double var54 = Math.hypot(var9 - var15, var11 - var17);
                  double var25 = Math.hypot(var19 - var9, var21 - var11);
                  byte var33;
                  if (var3 != null && var30 == var35) {
                     var33 = 1;
                  } else if (var4 != null && var30 == var31) {
                     var33 = 2;
                  } else {
                     var33 = 0;
                  }

                  if (!(var54 < 1.0E-6) && !(var25 < 1.0E-6)) {
                     double var23 = Math.toDegrees(
                        Math.acos(RangesKt.coerceIn(((var9 - var15) * (var19 - var9) + (var11 - var17) * (var21 - var11)) / (var54 * var25), -1.0, 1.0))
                     );
                     if (!(var23 < 2.0) && !(var23 > 150.0)) {
                        double var13 = Math.min(var5, Math.min(var54 * 0.45, 0.45 * var25));
                        var15 = (var15 - var9) / var54;
                        var17 = (var17 - var11) / var54;
                        var19 = (var19 - var9) / var25;
                        var21 = (var21 - var11) / var25;
                        int var41 = RangesKt.coerceIn((int)Math.ceil(var23 / 5.0), 2, 18) * 2;
                        if (var33 == 1) {
                           var31 = var41 / 2;
                        } else {
                           var31 = 0;
                        }

                        int var37;
                        if (var33 == 2) {
                           var37 = var41 / 2;
                        } else {
                           var37 = var41;
                        }

                        int var40 = var31;
                        if (var31 <= var37) {
                           while (true) {
                              var25 = (double)var40 / var41;
                              double var27 = 1;
                              var23 = (var27 - var25) * (var27 - var25);
                              var27 = (var27 - var25) * (2 * var25);
                              var25 *= var25;
                              round$add(
                                 var42,
                                 var43,
                                 var23 * (var9 + var15 * var13) + var27 * var9 + var25 * (var9 + var19 * var13),
                                 var23 * (var11 + var17 * var13) + var27 * var11 + var25 * (var11 + var21 * var13)
                              );
                              var63 = var75;
                              var30 = var63;
                              var31 = var30;
                              if (var40 == var37) {
                                 break;
                              }

                              var40++;
                              var63 = var63;
                              var30 = var30;
                           }
                        } else {
                           var31 = var30;
                           var63 = var31;
                           var30 = var63;
                        }
                     } else {
                        var30 = var63;
                        var63 = var31;
                        round$add(var42, var43, var9, var11);
                        var31 = var30;
                     }
                  } else {
                     var30 = var63;
                     var63 = var31;
                     round$add(var42, var43, var9, var11);
                     var31 = var30;
                  }
               }
            } else {
               var31 = var30;
               var30 = var63;
               var63 = var31;
            }

            int var78 = var31 + 1;
            var31 = var63;
            var63 = var30;
            var30 = var78;
         }

         if (var4 == null) {
            Object var50 = var45.get(var45.size() - 1);
            Intrinsics.checkNotNullExpressionValue(var50, "get(...)");
            var5 = ((Number)var50).doubleValue();
            var50 = var44.get(var44.size() - 1);
            Intrinsics.checkNotNullExpressionValue(var50, "get(...)");
            round$add(var42, var43, var5, ((Number)var50).doubleValue());
         }

         if (var42.size() < 2) {
            return TuplesKt.to(var1, var2);
         } else {
            var30 = var42.size();
            var1 = new double[var30];

            for (int var66 = 0; var66 < var30; var66++) {
               Object var48 = var42.get(var66);
               Intrinsics.checkNotNullExpressionValue(var48, "get(...)");
               var1[var66] = ((Number)var48).doubleValue();
            }

            var30 = var43.size();
            var2 = new double[var30];

            for (int var67 = 0; var67 < var30; var67++) {
               Object var52 = var43.get(var67);
               Intrinsics.checkNotNullExpressionValue(var52, "get(...)");
               var2[var67] = ((Number)var52).doubleValue();
            }

            return TuplesKt.to(var1, var2);
         }
      }
   }

   public final List<OsmRoads.Way> rounded(
      List<OsmRoads.Way> var1, List<OsmRoads.Way> var2, double var3, double var5, double var7, Map<Long, RoadShape.Rounded> var9
   ) {
      Intrinsics.checkNotNullParameter(var1, "ways");
      Intrinsics.checkNotNullParameter(var2, "near");
      Intrinsics.checkNotNullParameter(var9, "cache");
      ObjectRef var13 = new ObjectRef();
      ArrayList var12 = new ArrayList(var1.size());

      for (OsmRoads.Way var15 : var1) {
         RoadShape.Rounded var18 = (RoadShape.Rounded)var9.get(var15.getId());
         if (var18 != null) {
            boolean var10;
            var var16 = var18.getOpenEnds();
            boolean var11 = var16 instanceof Collection;
            var10 = true;
            label40:
            if (!var11 || !((Collection)var16).isEmpty()) {
               for (double[] var17 : var16) {
                  if (Math.hypot(var17[0] - var3, var17[1] - var5) < var7) {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  if (var10) {
                     var10 = false;
                     break label40;
                  }
               }

               var10 = true;
            }

            if (var10) {
               var12.add(var18.getWay());
               continue;
            }
         }

         ArrayList var25 = new ArrayList();
         double[] var19 = rounded$goesOn(var15, var3, var5, var7, var25, var13, var2, 0);
         double[] var26 = rounded$goesOn(var15, var3, var5, var7, var25, var13, var2, 1);
         Pair var20;
         if (this.spline(var15.getKind())) {
            var20 = through$default(this, var15.getE(), var15.getN(), var19, var26, 0.0, 16, null);
         } else {
            var20 = this.round(var15.getE(), var15.getN(), var19, var26, 8.0);
         }

         var26 = (double[])var20.component1();
         double[] var21 = (double[])var20.component2();
         OsmRoads.Way var22 = new OsmRoads.Way(
            var15.getId(), var15.getName(), var15.getKind(), var15.getOneway(), var15.getWidth(), var26, var21, var15.getLanes()
         );
         var9.put(var15.getId(), new RoadShape.Rounded(var22, var25));
         var12.add(var22);
      }

      if (var9.size() > 6000) {
         var9.clear();
      }

      return var12;
   }

   public final boolean spline(String var1) {
      Intrinsics.checkNotNullParameter(var1, "kind");
      boolean var2 = false;
      if (StringsKt.startsWith$default(var1, "motorway", false, 2, null)
         || StringsKt.startsWith$default(var1, "trunk", false, 2, null)
         || StringsKt.startsWith$default(var1, "primary", false, 2, null)
         || StringsKt.startsWith$default(var1, "secondary", false, 2, null)
         || StringsKt.endsWith$default(var1, "_link", false, 2, null)) {
         var2 = true;
      }

      return var2;
   }

   public final Pair<double[], double[]> through(double[] var1, double[] var2, double[] var3, double[] var4, double var5) {
      Intrinsics.checkNotNullParameter(var1, "e");
      Intrinsics.checkNotNullParameter(var2, "n");
      int var75 = var1.length;
      if (var75 < 2) {
         return TuplesKt.to(var1, var2);
      } else {
         ArrayList var77 = new ArrayList(var75 * 4);
         ArrayList var79 = new ArrayList(var75 * 4);
         var77.add(var1[0]);
         var79.add(var2[0]);
         int var73 = 0;

         for (int var71 = var75; var73 < var75 - 1; var73++) {
            double var23 = var1[var73];
            double var21 = var2[var73];
            double var19 = var1[var73 + 1];
            double var25 = var2[var73 + 1];
            double var7;
            double var9;
            if (var73 > 0) {
               var7 = var1[var73 - 1];
               var9 = var2[var73 - 1];
            } else if (var3 != null) {
               var7 = var3[0];
               var9 = var3[1];
            } else {
               var9 = 2;
               var7 = var9 * var23 - var19;
               var9 = var9 * var21 - var25;
            }

            double var11;
            double var13;
            if (var73 < var71 - 2) {
               var11 = var1[var73 + 2];
               var13 = var2[var73 + 2];
            } else if (var4 != null) {
               var11 = var4[0];
               var13 = var4[1];
            } else {
               var13 = 2;
               var11 = var13 * var19 - var23;
               var13 = var13 * var25 - var21;
            }

            double var17 = var7;
            double var15 = var9;
            if (through$sharp(var7, var9, var23, var21, var19, var25)) {
               var7 = 2;
               var17 = var7 * var23 - var19;
               var15 = var7 * var21 - var25;
            }

            if (through$sharp(var23, var21, var19, var25, var11, var13)) {
               var7 = 2;
               var11 = var7 * var19 - var23;
               var13 = var7 * var25 - var21;
            }

            var7 = Math.hypot(var19 - var23, var25 - var21);
            int var76 = RangesKt.coerceIn((int)Math.ceil(var7 / var5), 1, 60);
            if (var76 != 1 && !(var7 < 1.0E-6)) {
               double var57 = 0.0 + through$kn(var17, var15, var23, var21);
               double var43 = var57 + through$kn(var23, var21, var19, var25);
               double var53 = var43 + through$kn(var19, var25, var11, var13);
               int var74 = 1;
               if (1 <= var76) {
                  while (true) {
                     double var33 = var57 + (var43 - var57) * var74 / var76;
                     double var29 = (var57 - var33) / (var57 - 0.0);
                     double var63 = (var33 - 0.0) / (var57 - 0.0);
                     double var61 = (var57 - var33) / (var57 - 0.0);
                     double var31 = (var33 - 0.0) / (var57 - 0.0);
                     double var51 = (var43 - var33) / (var43 - var57) * var23 + (var33 - var57) / (var43 - var57) * var19;
                     double var37 = (var43 - var33) / (var43 - var57) * var21 + (var33 - var57) / (var43 - var57) * var25;
                     double var65 = (var53 - var33) / (var53 - var43);
                     double var69 = (var33 - var43) / (var53 - var43);
                     double var49 = (var53 - var33) / (var53 - var43);
                     double var39 = (var33 - var43) / (var53 - var43);
                     double var47 = (var43 - var33) / (var43 - 0.0);
                     double var35 = (var33 - 0.0) / (var43 - 0.0);
                     var9 = (var43 - var33) / (var43 - 0.0);
                     double var45 = (var33 - 0.0) / (var43 - 0.0);
                     double var41 = (var53 - var33) / (var53 - var57);
                     double var55 = (var33 - var57) / (var53 - var57);
                     double var67 = (var53 - var33) / (var53 - var57);
                     double var27 = (var33 - var57) / (var53 - var57);
                     double var59 = (var43 - var33) / (var43 - var57);
                     var7 = (var33 - var57) / (var43 - var57);
                     int var72 = var71;
                     ArrayList var78 = var77;
                     var77.add(
                        var59 * (var47 * (var29 * var17 + var63 * var23) + var35 * var51) + var7 * (var41 * var51 + var55 * (var65 * var19 + var69 * var11))
                     );
                     var79.add(
                        (var43 - var33) / (var43 - var57) * (var9 * (var61 * var15 + var31 * var21) + var45 * var37)
                           + (var33 - var57) / (var43 - var57) * (var67 * var37 + var27 * (var49 * var25 + var39 * var13))
                     );
                     var77 = var77;
                     var71 = var71;
                     if (var74 == var76) {
                        break;
                     }

                     var74++;
                     var77 = var78;
                     var71 = var72;
                  }
               }
            } else {
               var77.add(var19);
               var79.add(var25);
            }
         }

         int var93 = var77.size();
         var1 = new double[var93];

         for (int var91 = 0; var91 < var93; var91++) {
            Object var81 = var77.get(var91);
            Intrinsics.checkNotNullExpressionValue(var81, "get(...)");
            var1[var91] = ((Number)var81).doubleValue();
         }

         var93 = var79.size();
         var2 = new double[var93];

         for (int var92 = 0; var92 < var93; var92++) {
            Object var83 = var79.get(var92);
            Intrinsics.checkNotNullExpressionValue(var83, "get(...)");
            var2[var92] = ((Number)var83).doubleValue();
         }

         return TuplesKt.to(var1, var2);
      }
   }

   public static final class Rounded {
      private final List<double[]> openEnds;
      private final OsmRoads.Way way;

      public Rounded(OsmRoads.Way var1, List<double[]> var2) {
         Intrinsics.checkNotNullParameter(var1, "way");
         Intrinsics.checkNotNullParameter(var2, "openEnds");
         super();
         this.way = var1;
         this.openEnds = var2;
      }

      public final List<double[]> getOpenEnds() {
         return this.openEnds;
      }

      public final OsmRoads.Way getWay() {
         return this.way;
      }
   }
}
