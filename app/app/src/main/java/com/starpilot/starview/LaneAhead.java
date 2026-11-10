package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

public final class LaneAhead {
   public static final LaneAhead.Companion Companion = new LaneAhead.Companion(null);
   private volatile boolean busy;
   private volatile LaneAhead.Drop drop;
   private final ArrayDeque<double[]> gaps;
   private long lastLookMs;
   private double lastLookOdo;
   private Function2<? super String, ? super Boolean, Unit> onEvent;
   private volatile List<LaneAhead.Sample> profile;
   private Function3<? super Double, ? super Double, ? super Double, ? extends List<LaneAhead.Sample>> profileSource;
   private volatile Triple<Double, ? extends List<LaneAhead.Sample>, Integer> result;
   private volatile LaneAhead.Stage stage = LaneAhead.Stage.NONE;
   private final ExecutorService worker;

   // $VF: synthetic method
   public static Thread $r8$lambda$36mpTJv_ZnHYgWMtFqRL1LzAAjE(Runnable var0) {
      return worker$lambda$6(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ASB25d6_BwQdX8It_g4lvBRMyco/* $VF was: $r8$lambda$ASB25d6_BwQdX8It_g4lvBRMyco*/(
      LaneAhead var0, double var1, double var3, double var5, double var7, int var9, String var10
   ) {
      update$lambda$4(var0, var1, var3, var5, var7, var9, var10);
   }

   public LaneAhead() {
      this.profile = CollectionsKt.emptyList();
      this.lastLookOdo = -1.0E9;
      this.gaps = new ArrayDeque<>();
      this.worker = Executors.newSingleThreadExecutor(new LaneAhead$$ExternalSyntheticLambda1());
   }

   private final void apply(double var1, List<LaneAhead.Sample> var3, int var4, double var5) {
      this.profile = var3;
      Triple var13 = Companion.findDrop(var3, var4);
      LaneAhead.Drop var12 = this.drop;
      Object var11 = null;
      if (var13 == null) {
         if (var12 != null && var12.getAtOdo() - var5 > 300.0) {
            this.drop = null;
            this.stage = LaneAhead.Stage.NONE;
         }
      } else {
         double var7 = ((Number)var13.component1()).doubleValue();
         int var9 = ((Number)var13.component2()).intValue();
         int var10 = ((Number)var13.component3()).intValue();
         var1 += var7;
         if (!(var1 - var5 < 30.0)) {
            if (var12 == null || !(Math.abs(var12.getAtOdo() - var1) < 200.0) || var12.getFrom() != var9 || var12.getTo() != var10) {
               if (var12 == null || !(var12.getAtOdo() - var5 < 300.0)) {
                  Iterator var24 = var3.iterator();

                  do {
                     if (!var24.hasNext()) {
                        var16 = var11;
                        break;
                     }

                     var16 = var24.next();
                     if (((LaneAhead.Sample)var16).getName().length() > 0) {
                        var21 = true;
                     } else {
                        var21 = false;
                     }
                  } while (!var21);

                  label65: {
                     LaneAhead.Sample var17 = (LaneAhead.Sample)var16;
                     var11 = "";
                     if (var17 != null) {
                        var18 = var17.getName();
                        if (var18 != null) {
                           break label65;
                        }
                     }

                     var18 = "";
                  }

                  LaneAhead.Drop var26 = new LaneAhead.Drop(var1, var9, var10, var18);
                  Function2 var25 = this.onEvent;
                  if (var25 != null) {
                     String var14 = Companion.dist(var1 - var5, false);
                     boolean var22;
                     if (var26.getRoad().length() > 0) {
                        var22 = true;
                     } else {
                        var22 = false;
                     }

                     String var19 = (String)var11;
                     if (var22) {
                        String var20 = var26.getRoad();
                        var19 = ", " + var20;
                     }

                     var25.invoke("ahead: " + var9 + " → " + var10 + " lanes in " + var14 + " (HPMS" + var19 + ")", true);
                  }

                  this.drop = var26;
                  this.stage = LaneAhead.Stage.WATCHING;
               }
            }
         }
      }
   }

   private final List<Pair<Double, double[]>> roadPath(double var1, double var3, double var5) {
      if (Double.isNaN(OsmRoads.INSTANCE.getLat0())) {
         return null;
      } else {
         Pair var18 = OsmRoads.INSTANCE.toLocal(var1, var3);
         double var9 = ((Number)var18.component1()).doubleValue();
         var3 = ((Number)var18.component2()).doubleValue();
         OsmRoads.Match var50 = OsmRoads.INSTANCE.matchSeg(var9, var3, var5);
         if (var50 != null) {
            boolean var13;
            if (var50.getDist() < 20.0) {
               var13 = 1;
            } else {
               var13 = 0;
            }

            if (!var13) {
               var50 = null;
            }

            if (var50 != null) {
               OsmRoads.Way var21 = var50.getWay();
               if (StringsKt.endsWith$default(var21.getKind(), "_link", false, 2, null)) {
                  return null;
               }

               ArrayList var25 = new ArrayList();
               String var51 = "_link";
               List var20 = roadPath$ordered(var21, var9, var3, var5);
               HashSet var19 = new HashSet();
               var3 = 0.0;

               String var24;
               while (true) {
                  var19.add(var21.getId());

                  for (double[] var23 : var20) {
                     if (!var25.isEmpty()) {
                        var3 += Math.hypot(var23[0] - ((double[])CollectionsKt.last(var25))[0], var23[1] - ((double[])CollectionsKt.last(var25))[1]);
                     }

                     var25.add(var23);
                  }

                  var24 = "get(...)";
                  if (var3 > 1050.0 || var25.size() < 2) {
                     break;
                  }

                  double[] var26 = CollectionsKt.last(var25);
                  Object var68 = var25.get(var25.size() - 2);
                  Intrinsics.checkNotNullExpressionValue(var68, "get(...)");
                  var68 = (double[])var68;
                  var1 = var26[1];
                  var5 = (double)((Object[])var68)[1];
                  var1 = Math.atan2(var1 - var5, var26[0] - ((Object[])var68)[0]);
                  Iterable var73 = OsmRoads.INSTANCE.near(var26[0], var26[1], 15.0);
                  boolean var16 = false;
                  var var28 = new ArrayList();

                  for (Object var30 : var73) {
                     label179: {
                        OsmRoads.Way var29 = (OsmRoads.Way)var30;
                        if (!var19.contains(var29.getId()) && !StringsKt.endsWith$default(var29.getKind(), var51, false, 2, null)) {
                           if (var21.getName().length() > 0) {
                              var13 = (boolean)1;
                           } else {
                              var13 = (boolean)0;
                           }

                           boolean var17;
                           if (var13) {
                              var17 = OsmRoads.INSTANCE.sameStreet(var29.getName(), var21.getName());
                           } else {
                              var17 = Intrinsics.areEqual(var29.getKind(), var21.getKind());
                           }

                           if (var17) {
                              var13 = (boolean)1;
                              break label179;
                           }
                        }

                        var13 = (boolean)0;
                     }

                     if (var13) {
                        var28.add(var30);
                     }
                  }

                  List var65 = var28;
                  var73 = var65;
                  var var76 = new ArrayList();

                  for (OsmRoads.Way var52 : var73) {
                     label200: {
                        if (Math.hypot(var52.getE()[0] - var26[0], var52.getN()[0] - var26[1]) < 15.0) {
                           var13 = 0;
                        } else {
                           if (!(Math.hypot(var52.getE()[var52.getE().length - 1] - var26[0], var52.getN()[var52.getN().length - 1] - var26[1]) < 15.0)) {
                              var18 = null;
                              break label200;
                           }

                           var13 = var52.getE().length - 1;
                        }

                        if (var52.getOneway() != 0) {
                           boolean var14;
                           if (var52.getOneway() > 0) {
                              var14 = true;
                           } else {
                              var14 = false;
                           }

                           boolean var15;
                           if (var13 == 0) {
                              var15 = true;
                           } else {
                              var15 = false;
                           }

                           if (var14 != var15) {
                              var18 = null;
                              break label200;
                           }
                        }

                        int var48;
                        if (var13 == 0) {
                           var48 = 1;
                        } else {
                           var48 = var52.getE().length - 2;
                        }

                        double var7 = Math.abs(Math.atan2(var52.getN()[var48] - var52.getN()[var13], var52.getE()[var48] - var52.getE()[var13]) - var1);
                        var5 = var7;
                        if (var7 > Math.PI) {
                           var5 = (Math.PI * 2) - var7;
                        }

                        if (var5 > Math.toRadians(35.0)) {
                           var18 = null;
                        } else {
                           var18 = TuplesKt.to(var52, var5);
                        }
                     }

                     if (var18 != null) {
                        var76.add(var18);
                     }
                  }

                  List var54 = var76;
                  Iterable var55 = var54;
                  Iterator var75 = var55.iterator();
                  if (!var75.hasNext()) {
                     var18 = null;
                  } else {
                     var18 = (Pair)var75.next();
                     if (var75.hasNext()) {
                        var5 = ((Number)var18.getSecond()).doubleValue();
                        var21 = var18;

                        do {
                           var68 = var75.next();
                           double var11 = ((Number)((Pair)var68).getSecond()).doubleValue();
                           double var39 = var5;
                           var18 = var21;
                           if (Double.compare(var5, var11) > 0) {
                              var18 = (Pair)var68;
                              var39 = var11;
                           }

                           var5 = var39;
                           var21 = var18;
                        } while (var75.hasNext());
                     }
                  }

                  var18 = var18;
                  if (var18 == null) {
                     break;
                  }

                  var21 = (OsmRoads.Way)var18.getFirst();
                  if (var21 == null) {
                     break;
                  }

                  var68 = CollectionsKt.drop(roadPath$ordered(var21, var26[0], var26[1], var1), 1);
                  var51 = var51;
                  var20 = (List)var68;
               }

               ArrayList var67 = new ArrayList();
               double var40 = 0.0;
               double var42 = 100.0;
               int var49 = 1;
               var13 = var25.size();
               var1 = var42;

               for (String var58 = var24; var49 < var13; var49++) {
                  Object var60 = var25.get(var49 - 1);
                  Intrinsics.checkNotNullExpressionValue(var60, var58);
                  double[] var64 = (double[])var60;
                  var60 = var25.get(var49);
                  Intrinsics.checkNotNullExpressionValue(var60, var58);
                  var60 = (double[])var60;
                  var9 = Math.hypot(((Object[])var60)[0] - var64[0], ((Object[])var60)[1] - var64[1]);
                  if (!(var9 < 0.01)) {
                     while (var40 + var9 >= var1 && var1 <= 1000.0) {
                        var42 = (var1 - var40) / var9;
                        Pair var72 = OsmRoads.INSTANCE
                           .toLatLon(var64[0] + (((Object[])var60)[0] - var64[0]) * var42, var64[1] + (((Object[])var60)[1] - var64[1]) * var42);
                        var67.add(
                           TuplesKt.to(
                              var1,
                              new double[]{
                                 ((Number)var72.component1()).doubleValue(),
                                 ((Number)var72.component2()).doubleValue(),
                                 Math.atan2(((Object[])var60)[1] - var64[1], ((Object[])var60)[0] - var64[0])
                              }
                           )
                        );
                        var1 += 100.0;
                     }

                     var40 += var9;
                  }
               }

               ArrayList var59;
               if (!var67.isEmpty()) {
                  var59 = var67;
               } else {
                  var59 = null;
               }

               return var59;
            }
         }

         return null;
      }
   }

   private static final List<double[]> roadPath$ordered(OsmRoads.Way var0, double var1, double var3, double var5) {
      Iterator var14 = RangesKt.until(0, var0.getE().length - 1).iterator();
      double var7 = 0.0;

      while (var14.hasNext()) {
         int var13 = ((IntIterator)var14).nextInt();
         var7 += (var0.getE()[var13 + 1] - var0.getE()[var13]) * Math.cos(var5) + (var0.getN()[var13 + 1] - var0.getN()[var13]) * Math.sin(var5);
      }

      boolean var20;
      if (var7 >= 0.0) {
         var20 = 1;
      } else {
         var20 = 0;
      }

      double[] var25 = var0.getE();
      if (var20) {
         var14 = ArraysKt.getIndices(var25);
      } else {
         var14 = RangesKt.reversed(ArraysKt.getIndices(var25));
      }

      Iterable var15 = CollectionsKt.toList((Iterable)var14);
      var var27 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var15, 10));
      Iterator var29 = var15.iterator();

      while (var29.hasNext()) {
         var20 = ((Number)var29.next()).intValue();
         var27.add(new double[]{var0.getE()[var20], var0.getN()[var20]});
      }

      List var30 = var27;
      Iterator var16 = CollectionsKt.getIndices(var30).iterator();
      Object var17;
      if (!var16.hasNext()) {
         var17 = null;
      } else {
         var17 = (Integer)var16.next();
         if (var16.hasNext()) {
            var20 = var17.intValue();
            var7 = Math.hypot(((double[])var30.get(var20))[0] - var1, ((double[])var30.get(var20))[1] - var3);

            while (true) {
               var14 = (Iterator)var16.next();
               var20 = ((Number)var14).intValue();
               double var11 = Math.hypot(((double[])var30.get(var20))[0] - var1, ((double[])var30.get(var20))[1] - var3);
               double var9 = var7;
               if (Double.compare(var7, var11) > 0) {
                  var17 = var14;
                  var9 = var11;
               }

               if (!var16.hasNext()) {
                  break;
               }

               var7 = var9;
            }
         }
      }

      var17 = var17;
      if (var17 != null) {
         var20 = var17;
      } else {
         var20 = 0;
      }

      if (var20 < var30.size() - 1
         && (((double[])var30.get(var20))[0] - var1) * Math.cos(var5) + (((double[])var30.get(var20))[1] - var3) * Math.sin(var5) < 0.0) {
         var20++;
      }

      return CollectionsKt.plus(CollectionsKt.listOf(new double[]{var1, var3}), CollectionsKt.drop(var30, var20));
   }

   private final List<Pair<Double, double[]>> routePath(double var1, double var3) {
      Nav.Line var23 = Nav.INSTANCE.getLine();
      if (var23 == null) {
         return null;
      } else {
         double var5 = Nav.INSTANCE.getAlong();
         boolean var21;
         if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
            var21 = 1;
         } else {
            var21 = 0;
         }

         if (var21 && var23.getSize() >= 2) {
            var1 = ((Number)Nav.Line.locate$default(var23, var1, var3, 0, 4, null).component1()).doubleValue();
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
               var21 = (boolean)1;
            } else {
               var21 = (boolean)0;
            }

            if (var21 && !(var1 > 40.0)) {
               ArrayList var22 = new ArrayList();

               for (double var25 = 100.0; var25 <= 1600.0 && var5 + var25 < var23.getLength(); var25 += 100.0) {
                  var21 = RangesKt.coerceIn(var23.indexAt(var5 + var25), 1, var23.getSize() - 1);
                  double var19 = RangesKt.coerceIn(
                     (var5 + var25 - var23.getCum()[var21 - 1]) / Math.max(0.01, var23.getCum()[var21] - var23.getCum()[var21 - 1]), 0.0, 1.0
                  );
                  double var7 = var23.getMx()[var21 - 1];
                  double var17 = var23.getMx()[var21];
                  double var13 = var23.getMx()[var21 - 1];
                  double var9 = var23.getMy()[var21 - 1];
                  double var11 = var23.getMy()[var21];
                  double var15 = var23.getMy()[var21 - 1];
                  var22.add(
                     TuplesKt.to(
                        var25,
                        new double[]{
                           Nav.INSTANCE.latOf(var9 + (var11 - var15) * var19),
                           Nav.INSTANCE.lonOf(var7 + (var17 - var13) * var19),
                           Math.atan2(-(var23.getMy()[var21] - var23.getMy()[var21 - 1]), var23.getMx()[var21] - var23.getMx()[var21 - 1])
                        }
                     )
                  );
               }

               if (var22.isEmpty()) {
                  var22 = null;
               }

               return var22;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   private final void sideFromCamera(double var1, LaneBrain var3, LaneBrain.Frame var4) {
      LaneAhead.Drop var18 = this.drop;
      if (var4 != null) {
         float var15 = var4.getEdgeL();
         boolean var16;
         if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
            var16 = 1;
         } else {
            var16 = 0;
         }

         double var5;
         double var11;
         label273: {
            var11 = Double.NaN;
            if (var16) {
               var15 = var4.getYL();
               if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
                  var16 = (boolean)1;
               } else {
                  var16 = (boolean)0;
               }

               if (var16 && var4.getPL() > 0.4F) {
                  var5 = var4.getEdgeL() - Math.abs(var4.getYL());
                  break label273;
               }
            }

            var5 = Double.NaN;
         }

         var15 = var4.getEdgeR();
         if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
            var16 = (boolean)1;
         } else {
            var16 = (boolean)0;
         }

         double var7;
         label257: {
            if (var16) {
               var15 = var4.getYR();
               if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
                  var16 = (boolean)1;
               } else {
                  var16 = (boolean)0;
               }

               if (var16 && var4.getPR() > 0.4F) {
                  var7 = var4.getEdgeR() - Math.abs(var4.getYR());
                  break label257;
               }
            }

            var7 = Double.NaN;
         }

         var15 = var4.getYL();
         if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
            var16 = (boolean)1;
         } else {
            var16 = (boolean)0;
         }

         double var9 = var11;
         if (var16) {
            var15 = var4.getYR();
            if (!Float.isInfinite(var15) && !Float.isNaN(var15)) {
               var16 = (boolean)1;
            } else {
               var16 = (boolean)0;
            }

            var9 = var11;
            if (var16) {
               var9 = var11;
               if (var4.getPL() > 0.4F) {
                  var9 = var11;
                  if (var4.getPR() > 0.4F) {
                     var9 = var4.getYR() - var4.getYL();
                  }
               }
            }
         }

         if (var4.getOpChange() != 0) {
            this.gaps.clear();
         } else {
            if (this.gaps.isEmpty() || var1 - this.gaps.last()[0] >= 5.0) {
               this.gaps.addLast(new double[]{var1, var5, var7, var9, var4.getYL(), var4.getYR()});
            }

            while (!this.gaps.isEmpty() && var1 - ((double[])this.gaps.first())[0] > 220.0) {
               this.gaps.removeFirst();
            }

            if (var18 != null && var18.getSide() == 0 && !(var18.getAtOdo() - var1 > 450.0) && this.gaps.size() >= 20) {
               var7 = var3.getLaneW();
               var5 = sideFromCamera$trend(this, 1);
               var1 = sideFromCamera$trend(this, 2);
               var11 = sideFromCamera$trend(this, 3);
               double var13 = sideFromCamera$trend(this, 4);
               var9 = sideFromCamera$trend(this, 5);
               if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                  var16 = (boolean)1;
               } else {
                  var16 = (boolean)0;
               }

               if (var16 && var1 < var7 * -0.6 && !(var5 < var7 * -0.3)) {
                  var16 = 1;
               } else {
                  boolean var38;
                  if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                     var38 = true;
                  } else {
                     var38 = false;
                  }

                  if (var38 && var5 < var7 * -0.6 && !(var1 < -0.3 * var7)) {
                     var16 = -1;
                  } else {
                     label296: {
                        if (!Double.isInfinite(var11) && !Double.isNaN(var11)) {
                           var38 = true;
                        } else {
                           var38 = false;
                        }

                        if (var38 && var11 < -0.5) {
                           if (!Double.isInfinite(var9) && !Double.isNaN(var9)) {
                              var38 = true;
                           } else {
                              var38 = false;
                           }

                           if (var38) {
                              if (!Double.isInfinite(var13) && !Double.isNaN(var13)) {
                                 var38 = true;
                              } else {
                                 var38 = false;
                              }

                              if (var38 && -var9 > Math.abs(var13) + 0.3) {
                                 var16 = 1;
                                 break label296;
                              }
                           }
                        }

                        if (!Double.isInfinite(var11) && !Double.isNaN(var11)) {
                           var38 = true;
                        } else {
                           var38 = false;
                        }

                        if (var38 && var11 < -0.5) {
                           if (!Double.isInfinite(var9) && !Double.isNaN(var9)) {
                              var38 = true;
                           } else {
                              var38 = false;
                           }

                           if (var38) {
                              if (!Double.isInfinite(var13) && !Double.isNaN(var13)) {
                                 var38 = true;
                              } else {
                                 var38 = false;
                              }

                              if (var38 && var13 > Math.abs(var9) + 0.3) {
                                 var16 = -1;
                                 break label296;
                              }
                           }
                        }

                        var16 = 0;
                     }
                  }
               }

               if (var16 != 0) {
                  if (var16 <= 0) {
                     var1 = var5;
                  }

                  boolean var17;
                  if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                     var17 = true;
                  } else {
                     var17 = false;
                  }

                  if (var17 && var1 < var7 * -0.6) {
                     var17 = true;
                  } else {
                     var17 = false;
                  }

                  var18.setSide(var16);
                  String var20;
                  if (var17) {
                     var20 = "road edge closing in";
                  } else {
                     var20 = "our lane narrowing";
                  }

                  var18.setSideWhy(var20);
                  Function2 var22 = this.onEvent;
                  if (var22 != null) {
                     String var21 = Companion.sideName(var16).toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var21, "toLowerCase(...)");
                     String var47 = var18.getSideWhy();
                     var22.invoke("the " + var21 + " lane ends (camera: " + var47 + ")", true);
                  }
               }
            }
         }
      }
   }

   private static final double sideFromCamera$trend(LaneAhead var0, int var1) {
      Iterable var6 = CollectionsKt.take(var0.gaps, var0.gaps.size() / 3);
      var var7 = new ArrayList();
      Iterator var8 = var6.iterator();

      while (true) {
         boolean var5 = var8.hasNext();
         boolean var4 = true;
         if (!var5) {
            List var15 = var7;
            Iterable var9 = CollectionsKt.takeLast(var0.gaps, var0.gaps.size() / 3);
            var7 = new ArrayList();
            var8 = var9.iterator();

            while (var8.hasNext()) {
               Double var10 = ((double[])var8.next())[var1];
               double var12 = var10.doubleValue();
               if (!Double.isInfinite(var12) && !Double.isNaN(var12)) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               if (!var4) {
                  var10 = null;
               }

               if (var10 != null) {
                  var7.add(var10);
               }
            }

            List var11 = var7;
            if (var15.size() >= 4 && var11.size() >= 4) {
               return CollectionsKt.averageOfDouble(var11) - CollectionsKt.averageOfDouble(var15);
            }

            return Double.NaN;
         }

         Double var14 = ((double[])var8.next())[var1];
         double var2 = var14.doubleValue();
         if (Double.isInfinite(var2) || Double.isNaN(var2)) {
            var4 = false;
         }

         if (!var4) {
            var14 = null;
         }

         if (var14 != null) {
            var7.add(var14);
         }
      }
   }

   private static final void update$lambda$4(LaneAhead param0, double param1, double param3, double param5, double param7, int param9, String param10) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: dload 1
      // 002: dload 3
      // 003: invokespecial com/starpilot/starview/LaneAhead.routePath (DD)Ljava/util/List;
      // 006: astore 15
      // 008: aload 15
      // 00a: astore 14
      // 00c: aload 15
      // 00e: ifnonnull 028
      // 011: aload 0
      // 012: dload 1
      // 013: dload 3
      // 014: dload 5
      // 016: invokespecial com/starpilot/starview/LaneAhead.roadPath (DDD)Ljava/util/List;
      // 019: astore 14
      // 01b: goto 028
      // 01e: astore 10
      // 020: goto 142
      // 023: astore 14
      // 025: aconst_null
      // 026: astore 14
      // 028: aload 14
      // 02a: ifnull 14a
      // 02d: aload 14
      // 02f: checkcast java/lang/Iterable
      // 032: astore 14
      // 034: new java/util/ArrayList
      // 037: astore 15
      // 039: aload 15
      // 03b: aload 14
      // 03d: bipush 10
      // 03f: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 042: invokespecial java/util/ArrayList.<init> (I)V
      // 045: aload 15
      // 047: checkcast java/util/Collection
      // 04a: astore 16
      // 04c: aload 14
      // 04e: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 053: astore 17
      // 055: aload 17
      // 057: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 05c: ifeq 11c
      // 05f: aload 17
      // 061: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 066: checkcast kotlin/Pair
      // 069: astore 14
      // 06b: aload 14
      // 06d: invokevirtual kotlin/Pair.component1 ()Ljava/lang/Object;
      // 070: checkcast java/lang/Number
      // 073: invokevirtual java/lang/Number.doubleValue ()D
      // 076: dstore 1
      // 077: aload 14
      // 079: invokevirtual kotlin/Pair.component2 ()Ljava/lang/Object;
      // 07c: checkcast [D
      // 07f: astore 15
      // 081: getstatic com/starpilot/starview/Hpms.INSTANCE Lcom/starpilot/starview/Hpms;
      // 084: aload 15
      // 086: bipush 0
      // 087: daload
      // 088: aload 15
      // 08a: bipush 1
      // 08b: daload
      // 08c: aload 15
      // 08e: bipush 2
      // 08f: daload
      // 090: bipush 0
      // 091: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 094: dconst_0
      // 095: aload 10
      // 097: bipush 16
      // 099: aconst_null
      // 09a: invokestatic com/starpilot/starview/Hpms.match$default (Lcom/starpilot/starview/Hpms;DDDLjava/lang/Boolean;DLjava/lang/String;ILjava/lang/Object;)Lcom/starpilot/starview/Hpms$Match;
      // 09d: astore 14
      // 09f: goto 0a7
      // 0a2: astore 14
      // 0a4: aconst_null
      // 0a5: astore 14
      // 0a7: new com/starpilot/starview/LaneAhead$Sample
      // 0aa: astore 18
      // 0ac: aload 14
      // 0ae: ifnull 0c7
      // 0b1: aload 14
      // 0b3: invokevirtual com/starpilot/starview/Hpms$Match.getS ()Lcom/starpilot/starview/Hpms$Section;
      // 0b6: astore 19
      // 0b8: aload 19
      // 0ba: ifnull 0c7
      // 0bd: aload 19
      // 0bf: invokevirtual com/starpilot/starview/Hpms$Section.ourLanes ()I
      // 0c2: istore 13
      // 0c4: goto 0ca
      // 0c7: bipush 0
      // 0c8: istore 13
      // 0ca: aload 15
      // 0cc: bipush 0
      // 0cd: daload
      // 0ce: dstore 11
      // 0d0: aload 15
      // 0d2: bipush 1
      // 0d3: daload
      // 0d4: dstore 3
      // 0d5: aload 15
      // 0d7: bipush 2
      // 0d8: daload
      // 0d9: dstore 5
      // 0db: aload 14
      // 0dd: ifnull 0fc
      // 0e0: aload 14
      // 0e2: invokevirtual com/starpilot/starview/Hpms$Match.getS ()Lcom/starpilot/starview/Hpms$Section;
      // 0e5: astore 14
      // 0e7: aload 14
      // 0e9: ifnull 0fc
      // 0ec: aload 14
      // 0ee: invokevirtual com/starpilot/starview/Hpms$Section.getName ()Ljava/lang/String;
      // 0f1: astore 15
      // 0f3: aload 15
      // 0f5: astore 14
      // 0f7: aload 15
      // 0f9: ifnonnull 100
      // 0fc: ldc ""
      // 0fe: astore 14
      // 100: aload 18
      // 102: dload 1
      // 103: iload 13
      // 105: dload 11
      // 107: dload 3
      // 108: dload 5
      // 10a: aload 14
      // 10c: invokespecial com/starpilot/starview/LaneAhead$Sample.<init> (DIDDDLjava/lang/String;)V
      // 10f: aload 16
      // 111: aload 18
      // 113: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 118: pop
      // 119: goto 055
      // 11c: aload 16
      // 11e: checkcast java/util/List
      // 121: astore 14
      // 123: new kotlin/Triple
      // 126: astore 10
      // 128: aload 10
      // 12a: dload 7
      // 12c: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 12f: aload 14
      // 131: iload 9
      // 133: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 136: invokespecial kotlin/Triple.<init> (Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
      // 139: aload 0
      // 13a: aload 10
      // 13c: putfield com/starpilot/starview/LaneAhead.result Lkotlin/Triple;
      // 13f: goto 14a
      // 142: aload 0
      // 143: bipush 0
      // 144: putfield com/starpilot/starview/LaneAhead.busy Z
      // 147: aload 10
      // 149: athrow
      // 14a: aload 0
      // 14b: bipush 0
      // 14c: putfield com/starpilot/starview/LaneAhead.busy Z
      // 14f: return
   }

   private static final Thread worker$lambda$6(Runnable var0) {
      Thread var1 = new Thread(var0, "lane-ahead");
      var1.setDaemon(true);
      return var1;
   }

   public final double distance(double var1) {
      LaneAhead.Drop var3 = this.drop;
      if (var3 != null) {
         var1 = var3.getAtOdo() - var1;
      } else {
         var1 = Double.NaN;
      }

      return var1;
   }

   public final LaneAhead.Drop getDrop() {
      return this.drop;
   }

   public final Function2<String, Boolean, Unit> getOnEvent() {
      return this.onEvent;
   }

   public final List<LaneAhead.Sample> getProfile() {
      return this.profile;
   }

   public final Function3<Double, Double, Double, List<LaneAhead.Sample>> getProfileSource() {
      return (Function3<Double, Double, Double, List<LaneAhead.Sample>>)this.profileSource;
   }

   public final LaneAhead.Stage getStage() {
      return this.stage;
   }

   public final Pair<Integer, String> need(LaneBrain var1) {
      Intrinsics.checkNotNullParameter(var1, "b");
      LaneAhead.Drop var7 = this.drop;
      Integer var8 = 0;
      if (var7 == null) {
         return TuplesKt.to(var8, "");
      } else if (var7.getSide() != 0 && (this.stage == LaneAhead.Stage.PREPARE || this.stage == LaneAhead.Stage.URGENT)) {
         Pair var9 = var1.sideLanes(var7.getSide());
         int var4 = ((Number)var9.component1()).intValue();
         double var2 = ((Number)var9.component2()).doubleValue();
         if (var4 == 0 && !(var2 < 0.6)) {
            Pair var10 = var1.sideLanes(-var7.getSide());
            var4 = ((Number)var10.component1()).intValue();
            var2 = ((Number)var10.component2()).doubleValue();
            if (var4 >= 1 && !(var2 < 0.5)) {
               int var6 = -var7.getSide();
               String var11 = Companion.sideName(var7.getSide()).toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var11, "toLowerCase(...)");
               int var5 = var7.getFrom();
               var4 = var7.getTo();
               return TuplesKt.to(var6, var11 + " lane ends ahead (" + var5 + " → " + var4 + " lanes)");
            } else {
               return TuplesKt.to(var8, "");
            }
         } else {
            return TuplesKt.to(var8, "");
         }
      } else {
         return TuplesKt.to(var8, "");
      }
   }

   public final void reset() {
      this.drop = null;
      this.stage = LaneAhead.Stage.NONE;
      this.profile = CollectionsKt.emptyList();
      this.lastLookOdo = -1.0E9;
      this.gaps.clear();
      this.result = null;
   }

   public final void setOnEvent(Function2<? super String, ? super Boolean, Unit> var1) {
      this.onEvent = var1;
   }

   public final void setProfileSource(Function3<? super Double, ? super Double, ? super Double, ? extends List<LaneAhead.Sample>> var1) {
      this.profileSource = var1;
   }

   public final String text(double var1, boolean var3) {
      LaneAhead.Drop var8 = this.drop;
      if (var8 == null) {
         return "";
      } else {
         double var4 = var8.getAtOdo();
         String var9 = Companion.dist(var4 - var1, var3);
         String var11;
         if (this.stage == LaneAhead.Stage.URGENT && var8.getSide() != 0) {
            var9 = Companion.sideName(var8.getSide());
            String var12 = Companion.sideName(-var8.getSide()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var12, "toLowerCase(...)");
            var11 = var9 + " lane ends - merge " + var12 + " now";
         } else if (var8.getSide() != 0) {
            String var10 = Companion.sideName(var8.getSide());
            var11 = var10 + " lane ends in " + var9;
         } else {
            int var7 = var8.getFrom();
            int var6 = var8.getTo();
            var11 = var7 + " → " + var6 + " lanes in " + var9;
         }

         return var11;
      }
   }

   public final void update(long var1, double var3, double var5, double var7, double var9, float var11, LaneBrain var12, LaneBrain.Frame var13) {
      Intrinsics.checkNotNullParameter(var12, "b");
      this.sideFromCamera(var3, var12, var13);
      LaneAhead.Drop var21 = this.drop;
      Boolean var20 = true;
      if (var21 != null) {
         double var14 = var21.getAtOdo() - var3;
         if (var14 < -60.0) {
            if (var21.getSide() != 0) {
               var12.roadLaneEnded(var21.getSide(), var1);
            }

            Function2 var22 = this.onEvent;
            if (var22 != null) {
               int var19 = var21.getFrom();
               int var18 = var21.getTo();
               String var26;
               if (var21.getSide() != 0) {
                  String var25 = Companion.sideName(var21.getSide()).toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var25, "toLowerCase(...)");
                  var26 = " (" + var25 + " lane ended)";
               } else {
                  var26 = "";
               }

               var22.invoke("passed: " + var19 + " → " + var18 + " lanes" + var26, var20);
            }

            this.drop = null;
            this.stage = LaneAhead.Stage.NONE;
         } else {
            int var37;
            label130: {
               if (var21.getSide() != 0) {
                  Pair var27 = var12.sideLanes(var21.getSide());
                  var37 = ((Number)var27.component1()).intValue();
                  double var16 = ((Number)var27.component2()).doubleValue();
                  boolean var36;
                  if (var37 == 0 && var16 >= 0.6) {
                     var36 = true;
                  } else {
                     var36 = false;
                  }

                  if (var36) {
                     var37 = 1;
                     break label130;
                  }
               }

               var37 = 0;
            }

            double var34 = Math.max(183.0, var11 * 8.0);
            LaneAhead.Stage var28;
            if (var14 < 61.0 && var37) {
               var28 = LaneAhead.Stage.URGENT;
            } else if (var14 < var34) {
               var28 = LaneAhead.Stage.PREPARE;
            } else {
               var28 = LaneAhead.Stage.WATCHING;
            }

            this.stage = var28;
            if (this.stage == LaneAhead.Stage.PREPARE && !var21.getPrepared()) {
               var21.setPrepared(true);
               Function2 var45 = this.onEvent;
               if (var45 != null) {
                  String var23 = Companion.dist(var14, false);
                  String var44 = this.text(var3, false);
                  String var29;
                  if (var37) {
                     var29 = " - we're in it";
                  } else {
                     var29 = "";
                  }

                  var45.invoke("lane ends ahead, " + var23 + ": " + var44 + var29, var20);
               }
            }
         }
      }

      Triple var30 = this.result;
      if (var30 != null) {
         double var33 = ((Number)var30.component1()).doubleValue();
         List var43 = (List)var30.component2();
         int var38 = ((Number)var30.component3()).intValue();
         this.result = null;
         this.apply(var33, var43, var38, var3);
      }

      if (!(var11 < 2.0F)) {
         boolean var39;
         if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
            var39 = 1;
         } else {
            var39 = 0;
         }

         if (var39) {
            if (!Double.isInfinite(var7) && !Double.isNaN(var7)) {
               var39 = (boolean)1;
            } else {
               var39 = (boolean)0;
            }

            if (var39) {
               if (!Double.isInfinite(var9) && !Double.isNaN(var9)) {
                  var39 = (boolean)1;
               } else {
                  var39 = (boolean)0;
               }

               if (var39 && !this.busy) {
                  if (var3 - this.lastLookOdo < 150.0 && var1 - this.lastLookMs < 20000L) {
                     return;
                  }

                  label92: {
                     this.lastLookOdo = var3;
                     this.lastLookMs = var1;
                     LaneBrain.RoadInfo var31 = var12.getInfo();
                     if (var31 != null) {
                        var32 = var31.getHpmsName();
                        if (var32 != null) {
                           break label92;
                        }
                     }

                     var32 = "";
                  }

                  if (Intrinsics.areEqual(var12.getModel().getSrc(), "HPMS")) {
                     var39 = var12.getModel().getLanes();
                  } else {
                     var39 = 0;
                  }

                  Function3 var24 = this.profileSource;
                  if (var24 != null) {
                     this.apply(var3, (List<LaneAhead.Sample>)var24.invoke(var5, var7, var9), var39, var3);
                     return;
                  }

                  this.busy = true;
                  this.worker.execute(new LaneAhead$$ExternalSyntheticLambda0(this, var5, var7, var9, var3, var39, var32));
                  return;
               }
            }
         }
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final String dist(double var1, boolean var3) {
         String var7;
         if (var3) {
            if (var1 >= 1000.0) {
               StringCompanionObject var5 = StringCompanionObject.INSTANCE;
               var7 = String.format(Locale.US, "%.1f km", Arrays.copyOf(new Object[]{var1 / 1000}, 1));
               Intrinsics.checkNotNullExpressionValue(var7, "format(...)");
            } else {
               int var4 = (int)(Math.max(0.0, var1) / 10);
               var7 = var4 * 10 + " m";
            }
         } else if (var1 * 3.28084 >= 1500.0) {
            StringCompanionObject var8 = StringCompanionObject.INSTANCE;
            var7 = String.format(Locale.US, "%.1f mi", Arrays.copyOf(new Object[]{var1 / 1609.34}, 1));
            Intrinsics.checkNotNullExpressionValue(var7, "format(...)");
         } else {
            int var6 = (int)(Math.max(0.0, var1) * 3.28084 / 50);
            var7 = var6 * 50 + " ft";
         }

         return var7;
      }

      public final Triple<Double, Integer, Integer> findDrop(List<LaneAhead.Sample> var1, int var2) {
         Intrinsics.checkNotNullParameter(var1, "s");
         Iterable var9 = var1;
         var var12 = new ArrayList();

         for (var var9_it : var9) {

            var9 = var9_it;
            boolean var5;
            if (((LaneAhead.Sample)var9).getLanes() > 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var5) {
               var12.add(var9);
            }
         }

         var1 = var12;
         if (var1.size() < 4) {
            return null;
         } else {
            if (var2 <= 0) {
               Iterable var28 = CollectionsKt.take(var1, 2);
               var var22 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var28, 10));
               Iterator var29 = var28.iterator();

               while (var29.hasNext()) {
                  var22.add(((LaneAhead.Sample)var29.next()).getLanes());
               }

               List var23 = var22;
               Integer var24 = CollectionsKt.maxOrNull(var23);
               if (var24 == null) {
                  return null;
               }

               var2 = var24;
            }

            int var20 = 0;
            int var8 = var1.size();
            int var6 = var2;

            while (var20 < var8) {
               int var7 = ((LaneAhead.Sample)var1.get(var20)).getLanes();
               if (var7 > var6) {
                  var2 = var7;
               } else {
                  var2 = var6;
                  if (var7 != var6) {
                     Iterable var30 = CollectionsKt.drop(var1, var20);
                     ArrayList var25 = new ArrayList();

                     for (Object var11 : var30) {
                        boolean var16;
                        if (((LaneAhead.Sample)var11).getD() - ((LaneAhead.Sample)var1.get(var20)).getD() <= 300.0) {
                           var16 = true;
                        } else {
                           var16 = false;
                        }

                        if (!var16) {
                           break;
                        }

                        var25.add(var11);
                     }

                     List var26 = var25;
                     var2 = var6;
                     if (var26.size() >= 3) {
                        var2 = var6;
                        if (!(CollectionsKt.<LaneAhead.Sample>last(var26).getD() - ((LaneAhead.Sample)var1.get(var20)).getD() < 200.0)) {
                           var30 = var26;
                           boolean var18;
                           if (var30 instanceof Collection && ((Collection)var30).isEmpty()) {
                              var18 = false;
                           } else {
                              label150: {
                                 Iterator var33 = var30.iterator();

                                 while (var33.hasNext()) {
                                    if (((LaneAhead.Sample)var33.next()).getLanes() >= var6) {
                                       var18 = true;
                                    } else {
                                       var18 = false;
                                    }

                                    if (var18) {
                                       var18 = true;
                                       break label150;
                                    }
                                 }

                                 var18 = false;
                              }
                           }

                           if (!var18) {
                              double var3;
                              if (var20 > 0) {
                                 var3 = ((LaneAhead.Sample)var1.get(var20 - 1)).getD();
                              } else {
                                 var3 = 0.0;
                              }

                              if (((LaneAhead.Sample)var1.get(var20)).getD() - var3 > 250.0) {
                                 return null;
                              }

                              var3 = (((LaneAhead.Sample)var1.get(var20)).getD() + var3) / 2;
                              Iterator var34 = var26.iterator();
                              if (var34.hasNext()) {
                                 Comparable var14 = ((LaneAhead.Sample)var34.next()).getLanes();

                                 while (var34.hasNext()) {
                                    Comparable var27 = ((LaneAhead.Sample)var34.next()).getLanes();
                                    if (var14.compareTo(var27) < 0) {
                                       var14 = var27;
                                    }
                                 }

                                 return new Triple<>(var3, var6, var14);
                              }

                              throw new NoSuchElementException();
                           }

                           var2 = var6;
                        }
                     }
                  }
               }

               var20++;
               var6 = var2;
            }

            return null;
         }
      }

      public final String sideName(int var1) {
         String var2;
         if (var1 < 0) {
            var2 = "Left";
         } else {
            var2 = "Right";
         }

         return var2;
      }
   }

   public static final class Drop {
      private boolean announced;
      private final double atOdo;
      private final int from;
      private boolean prepared;
      private final String road;
      private int side;
      private String sideWhy;
      private final int to;

      public Drop(double var1, int var3, int var4, String var5) {
         Intrinsics.checkNotNullParameter(var5, "road");
         super();
         this.atOdo = var1;
         this.from = var3;
         this.to = var4;
         this.road = var5;
         this.sideWhy = "";
      }

      public final boolean getAnnounced() {
         return this.announced;
      }

      public final double getAtOdo() {
         return this.atOdo;
      }

      public final int getFrom() {
         return this.from;
      }

      public final boolean getPrepared() {
         return this.prepared;
      }

      public final String getRoad() {
         return this.road;
      }

      public final int getSide() {
         return this.side;
      }

      public final String getSideWhy() {
         return this.sideWhy;
      }

      public final int getTo() {
         return this.to;
      }

      public final void setAnnounced(boolean var1) {
         this.announced = var1;
      }

      public final void setPrepared(boolean var1) {
         this.prepared = var1;
      }

      public final void setSide(int var1) {
         this.side = var1;
      }

      public final void setSideWhy(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.sideWhy = var1;
      }
   }

   public static final class Sample {
      private final double d;
      private final double dir;
      private final int lanes;
      private final double lat;
      private final double lon;
      private final String name;

      public Sample(double var1, int var3, double var4, double var6, double var8, String var10) {
         Intrinsics.checkNotNullParameter(var10, "name");
         super();
         this.d = var1;
         this.lanes = var3;
         this.lat = var4;
         this.lon = var6;
         this.dir = var8;
         this.name = var10;
      }

      public final double getD() {
         return this.d;
      }

      public final double getDir() {
         return this.dir;
      }

      public final int getLanes() {
         return this.lanes;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLon() {
         return this.lon;
      }

      public final String getName() {
         return this.name;
      }
   }

   public static enum Stage {
      NONE,
      PREPARE,
      URGENT,
      WATCHING;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(LaneAhead.Stage.$VALUES);
      private static final LaneAhead.Stage[] $VALUES = $values();

      // $VF: synthetic method
      private static final LaneAhead.Stage[] $values() {
         return new LaneAhead.Stage[]{NONE, WATCHING, PREPARE, URGENT};
      }

      public static EnumEntries<LaneAhead.Stage> getEntries() {
         return $ENTRIES;
      }
   }
}
