package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

public final class LaneFeed {
   public static final LaneFeed.Companion Companion = new LaneFeed.Companion(null);
   private volatile Hpms.Match hpms;
   private LaneBrain.RoadInfo info;
   private long lastFrameId = -1L;
   private long lastRoadMs;
   private double lastYaw = Double.NaN;
   private int pendingEntry;
   private volatile List<LaneRamps.Ramp> ramps = CollectionsKt.emptyList();
   private LaneBrain.Road road;
   private OsmRoads.Way way;

   private static final float frame$edge(ModelSnapshot var0, int var1) {
      int var4 = ((Object[])var0.getEdgeX()).length;
      float var3 = Float.NaN;
      if (var1 < var4) {
         float[] var6 = var0.getEdgeStd();
         boolean var5 = true;
         boolean var15;
         if (var1 >= 0 && var1 < var6.length) {
            var15 = true;
         } else {
            var15 = false;
         }

         float var2;
         if (var15) {
            var2 = var6[var1];
         } else {
            var2 = 9.0F;
         }

         if (!(var2 > 1.5F)) {
            var6 = var0.getEdgeX()[var1];
            float[] var8 = var0.getEdgeY()[var1];
            ArrayList var7 = new ArrayList();
            var1 = 0;

            for (int var16 = var6.length; var1 < var16; var1++) {
               if (!(var6[var1] < 3.0F)) {
                  if (var6[var1] > 15.0F) {
                     break;
                  }

                  var7.add(Math.abs(var8[var1]));
               }
            }

            if (var7.isEmpty()) {
               return Float.NaN;
            }

            CollectionsKt.sort(var7);
            Float var9 = (Float)var7.get(var7.size() / 2);
            Float var18 = var9;
            Intrinsics.checkNotNull(var18);
            var2 = var18;
            boolean var12;
            if (0.4F <= var2 && var2 <= 25.0F) {
               var12 = var5;
            } else {
               var12 = false;
            }

            if (!var12) {
               var9 = null;
            }

            var9 = var9;
            var2 = var3;
            if (var9 != null) {
               var2 = var9;
            }

            return var2;
         }
      }

      return Float.NaN;
   }

   private static final float frame$lineAt(ModelSnapshot var0, int var1, float var2) {
      if (var1 < ((Object[])var0.getLaneX()).length && var1 < ((Object[])var0.getLaneY()).length) {
         float[] var3 = var0.getLaneX()[var1];
         float[] var4 = var0.getLaneY()[var1];
         if (var3.length < 2) {
            return Float.NaN;
         } else {
            var1 = 1;

            while (var1 < var3.length - 1 && var3[var1] < var2) {
               var1++;
            }

            var2 = RangesKt.coerceIn((var2 - var3[var1 - 1]) / Math.max(0.01F, var3[var1] - var3[var1 - 1]), 0.0F, 1.0F);
            return var4[var1 - 1] + (var4[var1] - var4[var1 - 1]) * var2;
         }
      } else {
         return Float.NaN;
      }
   }

   private static final void frame$place(IntRef var0, IntRef var1, BooleanRef var2, ModelSnapshot var3, float var4, float var5, float var6) {
      if (!(var4 < 5.0F) && !(var4 > 70.0F)) {
         float var7 = frame$lineAt(var3, 1, var4);
         var4 = frame$lineAt(var3, 2, var4);
         boolean var11 = Float.isInfinite(var7);
         boolean var10 = false;
         boolean var9;
         if (!var11 && !Float.isNaN(var7)) {
            var9 = 1;
         } else {
            var9 = 0;
         }

         if (var9) {
            if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
               var9 = (boolean)1;
            } else {
               var9 = (boolean)0;
            }

            if (var9) {
               float var8 = var4 - var7;
               var9 = var10;
               if (2.5F <= var8) {
                  var9 = var10;
                  if (var8 <= 4.6F) {
                     var9 = (boolean)1;
                  }
               }

               if (!var9) {
                  return;
               }

               var4 = (var5 - (var7 + var4) / 2.0F) / var8;
               var9 = Math.round(var4);
               if (!(Math.abs(var4 - var9) > 0.3F) && var9 != 0) {
                  if (var6 > 3.0F) {
                     if (var9 < 0) {
                        var0.element = Math.max(var0.element, -var9);
                     } else {
                        var1.element = Math.max(var1.element, var9);
                     }
                  } else if (var6 < -3.0F && var9 == -1) {
                     var2.element = true;
                  }

                  return;
               }

               return;
            }
         }
      }
   }

   private final LaneBrain.Road mapdRoad(Telemetry var1) {
      if (System.nanoTime() - var1.getMapdAtNs() <= 3000000000L && var1.getMapdMatched()) {
         var var7 = var1.getMapdWayName();
         boolean var2;
         if (var7.length() == 0) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         if (var2) {
            var7 = var1.getRoadName();
         }

         var7 = var7;
         boolean var5 = var1.getMapdOneWay();
         boolean var6 = var1.getMapdFreeway();
         if (var1.getMapdLanes() <= 0) {
            var2 = 0;
         } else {
            var2 = var1.getMapdLanes();
            if (!var5) {
               var2 = Math.max(1, var2 / 2);
            }
         }

         String var8 = var1.getMapdWayRef();
         long var3 = ("mapd:" + var7 + "|" + var8).hashCode();
         String var9;
         if (var6) {
            var9 = "motorway";
         } else {
            var9 = "road";
         }

         if (!var5 && !var6) {
            var5 = false;
         } else {
            var5 = true;
         }

         return new LaneBrain.Road(var3, var7, var9, var5, var6, var2);
      } else {
         return null;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void matchOwnMap() {
      OsmRoads.Fix var18 = OsmRoads.INSTANCE.getLastFix();
      if (var18 != null && !Double.isNaN(OsmRoads.INSTANCE.getLat0()) && System.nanoTime() - var18.getTNs() <= 3000000000L) {
         if (var18.getSpeed() > 3.0F) {
            float var11 = var18.getBearingDeg();
            boolean var12;
            if (!Float.isInfinite(var11) && !Float.isNaN(var11)) {
               var12 = true;
            } else {
               var12 = false;
            }

            if (var12) {
               this.lastYaw = (Math.PI / 2) - Math.toRadians(var18.getBearingDeg());
            }
         }

         double var7 = this.lastYaw;
         if (!Double.isNaN(var7)) {
            double var1;
            double var9;
            OsmRoads.Match var29;
            label128: {
               Pair var16 = OsmRoads.INSTANCE.toLocal(var18.getLat(), var18.getLon());
               var1 = ((Number)var16.component1()).doubleValue();
               var9 = ((Number)var16.component2()).doubleValue();
               var29 = OsmRoads.INSTANCE.matchSeg(var1, var9, var7);
               if (var29 != null) {
                  boolean var26;
                  if (var29.getDist() < 15.0) {
                     var26 = true;
                  } else {
                     var26 = false;
                  }

                  if (var26) {
                     break label128;
                  }
               }

               var29 = null;
            }

            OsmRoads.Way var17;
            if (var29 != null) {
               var17 = var29.getWay();
            } else {
               var17 = null;
            }

            label121: {
               label143: {
                  double var3;
                  double var5;
                  Hpms var19;
                  try {
                     var19 = Hpms.INSTANCE;
                     var3 = var18.getLat();
                     var5 = var18.getLon();
                  } catch (Exception var25) {
                     break label143;
                  }

                  label116: {
                     if (var17 != null) {
                        try {
                           var30 = var17.getKind();
                        } catch (Exception var24) {
                           break label143;
                        }

                        if (var30 != null) {
                           boolean var13;
                           try {
                              var13 = StringsKt.endsWith$default(var30, "_link", false, 2, null);
                           } catch (Exception var23) {
                              break label143;
                           }

                           var31 = var13;
                           break label116;
                        }
                     }

                     var31 = null;
                  }

                  try {
                     var32 = Hpms.match$default(var19, var3, var5, var7, var31, 0.0, null, 48, null);
                     break label121;
                  } catch (Exception var22) {
                  }
               }

               var32 = null;
            }

            this.hpms = var32;
            if (var17 == null) {
               this.road = null;
               this.way = null;
               this.ramps = CollectionsKt.emptyList();
            } else {
               label101:
               if (var17.getHighway() && !StringsKt.endsWith$default(var17.getKind(), "_link", false, 2, null)) {
                  label97: {
                     try {
                        var37 = LaneRamps.INSTANCE;
                        var34 = OsmRoads.INSTANCE;
                     } catch (Exception var21) {
                        break label97;
                     }

                     try {
                        var33 = var37.scan(var34.near(var1, var9, 450.0), var17, var1, var9, var7);
                        break label101;
                     } catch (Exception var20) {
                     }
                  }

                  var33 = CollectionsKt.emptyList();
               } else {
                  var33 = CollectionsKt.emptyList();
               }

               this.ramps = var33;
               OsmRoads.Way var35 = this.way;
               if (var35 != null
                  && var35.getId() != var17.getId()
                  && StringsKt.endsWith$default(var35.getKind(), "_link", false, 2, null)
                  && var17.getHighway()
                  && !StringsKt.endsWith$default(var17.getKind(), "_link", false, 2, null)) {
                  this.pendingEntry = Companion.rampSide(var35, var17, var1, var9, var7);
               }

               this.way = var17;
               int var27;
               if (var17.getLanes() <= 0) {
                  var27 = 0;
               } else if (var17.getOneway() != 0) {
                  var27 = var17.getLanes();
               } else {
                  var27 = Math.max(1, var17.getLanes() / 2);
               }

               long var14 = var17.getId();
               String var36 = var17.getName();
               String var38 = var17.getKind();
               boolean var28;
               if (var17.getOneway() != 0) {
                  var28 = true;
               } else {
                  var28 = false;
               }

               this.road = new LaneBrain.Road(var14, var36, var38, var28, var17.getHighway(), var27);
            }
         }
      } else {
         this.road = null;
         this.way = null;
         this.ramps = CollectionsKt.emptyList();
         this.hpms = null;
      }
   }

   private final void matchRoad(Telemetry var1) {
      this.matchOwnMap();
      if (this.road == null) {
         this.road = this.mapdRoad(var1);
      }

      this.info = this.roadInfo();
   }

   private final LaneBrain.RoadInfo roadInfo() {
      Hpms.Match var18 = this.hpms;
      ArrayList var17 = new ArrayList();
      Iterator var15 = this.ramps.iterator();
      String var13 = "";
      String var14 = "";
      int var6 = 0;
      int var7 = 0;

      while (var15.hasNext()) {
         LaneRamps.Ramp var16 = (LaneRamps.Ramp)var15.next();
         if (var16.getSide() < 0) {
            var7 = 1;
            var14 = var16.getKind();
         } else {
            var6 = 1;
            var13 = var16.getKind();
         }

         String var45 = var16.text();
         var17.add("map: " + var45);
      }

      int var5 = 0;
      double var1 = Double.NaN;

      try {
         var34 = Nav.INSTANCE.nextLanes();
      } catch (Exception var23) {
         var34 = null;
      }

      if (var34 != null && ((Number)var34.getSecond()).doubleValue() < 600.0) {
         Nav.Step var19 = (Nav.Step)var34.component1();
         double var3 = ((Number)var34.component2()).doubleValue();
         var1 = var3;
         Iterable var35 = var19.getLanes();
         var var20 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var35, 10));
         Iterator var21 = var35.iterator();

         while (var21.hasNext()) {
            var var46 = ((Nav.Lane)var21.next()).getArrows();
            boolean var10;
            if (var46 instanceof Collection && ((Collection)var46).isEmpty()) {
               var10 = false;
            } else {
               var15 = var46.iterator();

               while (true) {
                  if (!var15.hasNext()) {
                     var10 = false;
                     break;
                  }

                  String var22 = (String)var15.next();
                  boolean var24;
                  if (!Intrinsics.areEqual(var22, "straight") && !Intrinsics.areEqual(var22, "none")) {
                     var24 = false;
                  } else {
                     var24 = true;
                  }

                  if (var24) {
                     var10 = true;
                     break;
                  }
               }
            }

            var20.add(var10);
         }

         List var47 = var20;
         var var37 = var47;
         if (var37 instanceof Collection && ((Collection)var37).isEmpty()) {
            var5 = 0;
         } else {
            var5 = 0;

            for (boolean var28 : var37) {
               if (var28) {
                  int var8 = var5 + 1;
                  var5 = var8;
                  if (var8 < 0) {
                     CollectionsKt.throwCountOverflow();
                     var5 = var8;
                  }
               }
            }
         }

         if (Intrinsics.areEqual(var19.getType(), "off ramp") && var3 < 320.0 && var5 > 0) {
            Iterable var39 = CollectionsKt.reversed(var47);
            ArrayList var55 = new ArrayList();

            for (Object var59 : var39) {
               boolean var29 = (Boolean)var59;
               if (var29) {
                  break;
               }

               var55.add(var59);
            }

            int var9 = var55.size();
            Iterable var40 = var47;
            ArrayList var56 = new ArrayList();

            for (Object var48 : var40) {
               boolean var30 = (Boolean)var48;
               if (var30) {
                  break;
               }

               var56.add(var48);
            }

            int var25 = var56.size();
            String var41 = "s";
            if (var9 > var6) {
               String var49 = "exit";
               if (var9 > 1) {
                  var13 = "s";
               } else {
                  var13 = "";
               }

               var17.add("route: " + var9 + " exit-only lane" + var13 + " right");
               var6 = var9;
               var13 = var49;
            }

            if (var25 > var7) {
               var7 = var25;
               if (var25 > 1) {
                  var14 = var41;
               } else {
                  var14 = "";
               }

               var17.add("route: " + var25 + " exit-only lane" + var14 + " left");
               var14 = "exit";
            }
         }
      }

      int var26;
      label145: {
         if (var18 != null) {
            Hpms.Section var42 = var18.getS();
            if (var42 != null) {
               var26 = var42.ourLanes();
               break label145;
            }
         }

         var26 = 0;
      }

      label140: {
         if (var18 != null) {
            Hpms.Section var43 = var18.getS();
            if (var43 != null) {
               var44 = var43.getName();
               if (var44 != null) {
                  break label140;
               }
            }
         }

         var44 = "";
      }

      boolean var31;
      label134: {
         if (var18 != null) {
            Hpms.Section var50 = var18.getS();
            if (var50 != null && var50.getRamp()) {
               var31 = true;
               break label134;
            }
         }

         var31 = false;
      }

      boolean var27;
      label128: {
         if (var18 != null) {
            Hpms.Section var51 = var18.getS();
            if (var51 != null && !var51.getOurLanesSure()) {
               var27 = true;
               break label128;
            }
         }

         var27 = false;
      }

      boolean var11;
      label122: {
         if (var18 != null) {
            Hpms.Section var52 = var18.getS();
            if (var52 != null && var52.getTunnel()) {
               var11 = true;
               break label122;
            }
         }

         var11 = false;
      }

      boolean var12;
      label116: {
         if (var18 != null) {
            Hpms.Section var53 = var18.getS();
            if (var53 != null && var53.getBridge()) {
               var12 = true;
               break label116;
            }
         }

         var12 = false;
      }

      String var54 = CollectionsKt.joinToString$default(var17, ", ", null, null, 0, null, null, 62, null);
      return new LaneBrain.RoadInfo(var26, var44, var31, var11, var12, var27 ^ true, var7, var6, var14, var13, var54, var5, var1);
   }

   private final LaneFeed.Rtk rtkOffset(long var1, double var3) {
      if (!Double.isNaN(OsmRoads.INSTANCE.getLat0()) && RtkPosition.INSTANCE.fast(var1)) {
         double[] var12 = RtkPosition.INSTANCE.now(var1, var3);
         if (var12 == null) {
            return null;
         } else {
            double var5 = RtkPosition.INSTANCE.getHeading();
            boolean var11 = Double.isInfinite(var5);
            boolean var10 = true;
            boolean var9;
            if (!var11 && !Double.isNaN(var5)) {
               var9 = true;
            } else {
               var9 = false;
            }

            if (var9 && !(var3 < 3.0)) {
               double var7 = (Math.PI / 2) - Math.toRadians(var5);
               Pair var16 = OsmRoads.INSTANCE.toLocal(var12[0], var12[1]);
               var5 = ((Number)var16.component1()).doubleValue();
               var3 = ((Number)var16.component2()).doubleValue();
               OsmRoads.Match var17 = OsmRoads.INSTANCE.matchSeg(var5, var3, var7);
               if (var17 != null) {
                  if (var17.getDist() < 15.0) {
                     var9 = var10;
                  } else {
                     var9 = false;
                  }

                  if (!var9) {
                     var17 = null;
                  }

                  if (var17 != null) {
                     return new LaneFeed.Rtk(var17.rightOf(var7), Math.max(0.2, RtkPosition.INSTANCE.getAccM()), var17.getWay().getId());
                  }
               }

               return null;
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public final LaneBrain.Frame frame(Telemetry var1, LaneMarks var2, long var3) {
      Intrinsics.checkNotNullParameter(var1, "t");
      ModelSnapshot var37 = var1.getModel();
      if (var37 == null) {
         return null;
      } else if (var37.getFrameId() == this.lastFrameId) {
         return null;
      } else {
         this.lastFrameId = var37.getFrameId();
         float var9 = var1.getPoseYawZ();
         boolean var27 = Float.isInfinite(var9);
         int var22 = 0;
         boolean var21;
         if (!var27 && !Float.isNaN(var9)) {
            var21 = 1;
         } else {
            var21 = 0;
         }

         if (var21 && System.nanoTime() - var1.getPoseAtNs() < 500000000L) {
            var21 = (boolean)1;
         } else {
            var21 = (boolean)0;
         }

         if (var21) {
            var9 = var1.getPoseYawZ() * Odometry.INSTANCE.getGyroSign();
         } else {
            if (var1.getSteerRatioLive() > 5.0F) {
               var9 = var1.getSteerRatioLive();
            } else {
               var9 = 15.5F;
            }

            var9 = (float)(var1.getVEgo() * Math.tan(Math.toRadians(var1.getSteeringAngleDeg() - var1.getAngleOffsetDeg()) / var9) / 3.03);
         }

         if (var3 - this.lastRoadMs > 500L) {
            this.lastRoadMs = var3;
            this.matchRoad(var1);
         }

         int var25;
         LaneMarks.Mark[] var34;
         label156: {
            var25 = this.pendingEntry;
            this.pendingEntry = 0;
            if (var2 != null) {
               if (System.nanoTime() - var2.getLastNs() < 1000000000L) {
                  var21 = (boolean)1;
               } else {
                  var21 = (boolean)0;
               }

               if (!var21) {
                  var2 = null;
               }

               if (var2 != null) {
                  var34 = var2.getMarks();
                  break label156;
               }
            }

            var34 = null;
         }

         IntRef var32 = new IntRef();
         IntRef var33 = new IntRef();
         BooleanRef var35 = new BooleanRef();
         BooleanRef var42;
         if (var1.getVEgo() > 3.0F) {
            RadarTrack[] var36 = var1.getRadar();
            int var23 = var36.length;
            byte var24 = 0;
            IntRef var39 = var32;
            var32 = var33;
            BooleanRef var60 = var35;
            RadarTrack[] var62 = var36;

            for (int var51 = var24; var51 < var23; var51++) {
               RadarTrack var67 = var62[var51];
               if (var67.getMeasured()) {
                  frame$place(var39, var32, var60, var37, var67.getDRel() + 2.3F, -var67.getYRel(), var1.getVEgo() + var67.getVRel());
               }
            }

            IntRef var63 = var39;
            Lead[] var40 = new Lead[2];
            var40[var22] = var1.getLeadLeft();
            Lead var68 = var1.getLeadRight();
            var21 = 1;
            var40[1] = var68;

            for (Lead var69 : CollectionsKt.listOf(var40)) {
               if (var69.getStatus()) {
                  frame$place(var63, var32, var60, var37, var69.getDRel() + 2.3F, -var69.getYRel(), var69.getVLead());
               }
            }

            var42 = var60;
            var33 = var32;
            var32 = var63;
         } else {
            var42 = var35;
            var21 = 1;
         }

         LaneFeed.Rtk var70 = this.rtkOffset(var3, var1.getVEgo());
         float var16 = var1.getVEgo();
         float var14 = frame$lineAt(var37, var21, 3.0F);
         float var15 = frame$lineAt(var37, 2, 3.0F);
         float[] var64 = var37.getLaneProb();
         if (var21 < var64.length) {
            var22 = var21;
         } else {
            var22 = (byte)0;
         }

         float var10;
         if (var22 != 0) {
            var10 = var64[var21];
         } else {
            var10 = 0.0F;
         }

         float[] var65 = var37.getLaneProb();
         if (2 < var65.length) {
            var22 = var21;
         } else {
            var22 = (byte)0;
         }

         float var11;
         if (var22 != 0) {
            var11 = var65[2];
         } else {
            var11 = 0.0F;
         }

         float var18 = frame$edge(var37, 0);
         float var20 = frame$edge(var37, var21);
         LaneMarks.Mark var66;
         if (var34 != null) {
            var66 = ArraysKt.getOrNull(var34, var21);
         } else {
            var66 = null;
         }

         LaneMarks.Mark var61;
         if (var34 != null) {
            var61 = ArraysKt.getOrNull(var34, 2);
         } else {
            var61 = null;
         }

         LaneBrain.Road var38 = this.road;
         if (var37.getLaneChanging()) {
            var22 = var37.getLaneChangeDir();
         } else {
            var22 = 0;
         }

         if (!var37.getLaneChanging() || !var37.getLaneChangeFinishing()) {
            var21 = 0;
         }

         int var57 = var32.element;
         int var26 = var33.element;
         var27 = var42.element;
         float var19 = frame$lineAt(var37, 0, 3.0F);
         float var17 = frame$lineAt(var37, 3, 3.0F);
         float[] var43 = var37.getLaneProb();
         boolean var55;
         if (var43.length > 0) {
            var55 = true;
         } else {
            var55 = false;
         }

         float var12;
         if (var55) {
            var12 = var43[0];
         } else {
            var12 = 0.0F;
         }

         float[] var44 = var37.getLaneProb();
         if (3 < var44.length) {
            var55 = true;
         } else {
            var55 = false;
         }

         float var13;
         if (var55) {
            var13 = var44[3];
         } else {
            var13 = 0.0F;
         }

         boolean var29 = var1.getLeftBlindspot();
         boolean var28 = var1.getRightBlindspot();
         double var7 = Double.NaN;
         double var5;
         if (var70 != null) {
            var5 = var70.getOffset();
         } else {
            var5 = Double.NaN;
         }

         if (var70 != null) {
            var7 = var70.getSigma();
         }

         long var30;
         if (var70 != null) {
            var30 = var70.getWay();
         } else {
            var30 = 0L;
         }

         return new LaneBrain.Frame(
            var3,
            var16,
            var9,
            var14,
            var15,
            var10,
            var11,
            var18,
            var20,
            var66,
            var61,
            var38,
            var25,
            var22,
            (boolean)var21,
            var57,
            var26,
            var27,
            var19,
            var17,
            var12,
            var13,
            var29,
            var28,
            var5,
            var7,
            var30,
            var37.getLaneChangeProb(),
            this.info
         );
      }
   }

   public final Hpms.Match getHpms() {
      return this.hpms;
   }

   public final List<LaneRamps.Ramp> getRamps() {
      return this.ramps;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final int rampSide(OsmRoads.Way var1, OsmRoads.Way var2, double var3, double var5, double var7) {
         Intrinsics.checkNotNullParameter(var1, "ramp");
         Intrinsics.checkNotNullParameter(var2, "hw");
         double var9 = Double.MAX_VALUE;
         double var17 = 0.0;
         double var15 = 0.0;
         double var13 = 0.0;
         double var11 = 0.0;
         int var31 = 0;
         int var32 = var2.getE().length;

         while (var31 < var32 - 1) {
            double var27 = var2.getE()[var31];
            double var25 = var2.getN()[var31];
            double var23 = var2.getE()[var31 + 1] - var27;
            double var21 = var2.getN()[var31 + 1] - var25;
            double var19 = var23 * var23 + var21 * var21;
            if (!(var19 < 0.01)) {
               var19 = RangesKt.coerceIn(((var3 - var27) * var23 + (var5 - var25) * var21) / var19, 0.0, 1.0);
               double var29 = Math.hypot(var3 - (var27 + var19 * var23), var5 - (var25 + var19 * var21));
               var19 = var9;
               if (var29 < var9) {
                  var19 = var29;
                  var17 = var27;
                  var15 = var25;
                  var13 = var23;
                  var11 = var21;
               }
            } else {
               var19 = var9;
            }

            var31++;
            var9 = var19;
         }

         if (var9 > 40.0) {
            return 0;
         } else {
            double var41;
            double var42;
            label56: {
               if (var2.getOneway() >= 0) {
                  var42 = var13;
                  var41 = var11;
                  if (var2.getOneway() != 0) {
                     break label56;
                  }

                  var42 = var13;
                  var41 = var11;
                  if (!(Math.cos(Math.atan2(var11, var13) - var7) < 0.0)) {
                     break label56;
                  }
               }

               var42 = -var13;
               var41 = -var11;
            }

            double var43 = Math.hypot(var42, var41);
            var11 = 0.0;
            var32 = 0;
            var31 = 0;
            int var34 = var1.getE().length;

            while (var31 < var34) {
               double var44 = var1.getE()[var31];
               double var45 = var1.getN()[var31];
               var13 = Math.hypot(var44 - var3, var45 - var5);
               int var33;
               if (!(var13 < 20.0)) {
                  if (var13 > 200.0) {
                     var33 = var32;
                     var13 = var11;
                  } else {
                     var33 = var32;
                     var13 = var11;
                     if (!((var44 - var3) * Math.cos(var7) + (var45 - var5) * Math.sin(var7) > 0.0)) {
                        var13 = var11 + ((var45 - var15) * var42 - (var44 - var17) * var41) / var43;
                        var33 = var32 + 1;
                     }
                  }
               } else {
                  var13 = var11;
                  var33 = var32;
               }

               var31++;
               var32 = var33;
               var11 = var13;
            }

            if (var32 == 0) {
               return 0;
            } else {
               var3 = var11 / var32;
               byte var47;
               if (var3 > 4.0) {
                  var47 = -1;
               } else if (var3 < -4.0) {
                  var47 = 1;
               } else {
                  var47 = 0;
               }

               return var47;
            }
         }
      }
   }

   public static final class Rtk {
      private final double offset;
      private final double sigma;
      private final long way;

      public Rtk(double var1, double var3, long var5) {
         this.offset = var1;
         this.sigma = var3;
         this.way = var5;
      }

      public final double getOffset() {
         return this.offset;
      }

      public final double getSigma() {
         return this.sigma;
      }

      public final long getWay() {
         return this.way;
      }
   }
}
