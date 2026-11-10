package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class IdeasMore {
   public static final IdeasMore.Companion Companion = new IdeasMore.Companion(null);
   private volatile long busAtMs;
   private volatile float busX;
   private long dparkMs;
   private final ArrayDeque<float[]> flow = new ArrayDeque<>();
   private volatile long hornAtMs;
   private int lastMile;
   private long lastMs;
   private long parkSeenMs;
   private long streakChipUntil;
   private float streakMem;
   private boolean wasEnabled;
   private volatile long wipersAtMs;

   private final void doubleParked(Ideas.Here var1, List<Detector.Obj> var2, double var3, long var5, List<Ideas.Part> var7) {
      if (!Double.isNaN(var1.getYaw())) {
         Calendar var30 = Calendar.getInstance();
         var30.setTimeInMillis(var5);
         int var27 = var30.get(7);
         boolean var43;
         if (var27 != 7 && var27 != 1) {
            var43 = false;
         } else {
            var43 = true;
         }

         int var29 = var30.get(11);
         if (var3 > 2.0 && var5 - this.dparkMs > 5000L) {
            Iterator var31 = var2.iterator();

            boolean var44;
            do {
               if (!var31.hasNext()) {
                  var35 = null;
                  break;
               }

               var35 = var31.next();
               Detector.Obj var47 = (Detector.Obj)var35;
               if (IdeasSense.Companion.getVEHICLES().contains(var47.getCls()) && !var47.getMoving() && Math.abs(var47.getCy()) < 1.2F) {
                  float var26 = var47.getCx();
                  if (10.0F <= var26 && var26 <= 50.0F) {
                     var44 = true;
                  } else {
                     var44 = false;
                  }

                  if (var44) {
                     var44 = true;
                     continue;
                  }
               }

               var44 = false;
            } while (!var44);

            Detector.Obj var36 = (Detector.Obj)var35;
            if (var36 != null) {
               this.dparkMs = var5;
               double var14 = var36.getCx();
               double var10 = Math.cos(var1.getYaw());
               double var16 = var36.getCy();
               double var12 = Math.sin(var1.getYaw());
               double var24 = var36.getCx();
               double var18 = Math.sin(var1.getYaw());
               double var22 = var36.getCy();
               double var20 = Math.cos(var1.getYaw());
               IdeaSpots var48 = IdeaSpots.INSTANCE;
               double var8 = var1.getLat();
               var20 = (var24 * var18 - var22 * var20) / 111195.0;
               var18 = var1.getLon();
               var10 = (var14 * var10 + var16 * var12) / (Math.cos(Math.toRadians(var1.getLat())) * 111320.0);
               var12 = var1.getHdgDeg();
               String var37;
               if (var43) {
                  var37 = "we";
               } else {
                  var37 = "wd";
               }

               IdeaSpots.record$default(var48, "dpark", var8 + var20, var18 + var10, var12, 0.0, var37 + var29, var5, 25.0, null, 272, null);
            }
         }

         Iterator var49 = IdeaSpots.around$default(
               IdeaSpots.INSTANCE, var1.getLat(), var1.getLon(), var1.getYaw(), SetsKt.setOf("dpark"), 200.0, 0.0, 6.0, 2, false, 288, null
            )
            .iterator();

         while (true) {
            if (var49.hasNext()) {
               Object var38 = var49.next();
               String var50 = ((IdeaSpots.Seen)var38).getSpot().getNote();
               String var33;
               if (var43) {
                  var33 = "we";
               } else {
                  var33 = "wd";
               }

               int var46;
               label67: {
                  if (StringsKt.startsWith$default(var50, var33, false, 2, null)) {
                     Integer var34 = StringsKt.toIntOrNull(StringsKt.drop(var50, 2));
                     if (var34 != null) {
                        var46 = var34;
                     } else {
                        var46 = -9;
                     }

                     if (Math.abs(var46 - var29) <= 1) {
                        var46 = 1;
                        break label67;
                     }
                  }

                  var46 = 0;
               }

               if (!var46) {
                  continue;
               }

               var32 = var38;
               break;
            }

            var32 = null;
            break;
         }

         if ((IdeaSpots.Seen)var32 != null) {
            var7.add(new Ideas.Part("double_parked", Math.max(4.4704, var3 - 1.3), 0.0, 0.0, "Double-parked cars usually here now", 24, 12, null));
         }
      }
   }

   private final void farLead(Telemetry var1, double var2, List<Ideas.Part> var4) {
      if (!(var2 < 12.0)) {
         ModelSnapshot var17 = var1.getModel();
         RadarTrack var13 = null;
         RadarTrack[] var16 = var1.getRadar();
         int var10 = var16.length;
         int var8 = 0;

         while (var8 < var10) {
            RadarTrack var15 = var16[var8];
            RadarTrack var14 = var13;
            if (var15.getMeasured()) {
               float var7 = var15.getDRel();
               boolean var9;
               if (50.0F <= var7 && var7 <= 170.0F) {
                  var9 = true;
               } else {
                  var9 = false;
               }

               if (!var9) {
                  var14 = var13;
               } else {
                  if (var17 != null && var17.getPosX().length > 2) {
                     var7 = IdeasRoad.Companion.interp(var15.getDRel(), var17.getPosX(), var17.getPosY());
                  } else {
                     var7 = 0.0F;
                  }

                  var14 = var13;
                  if (!(Math.abs(var15.getYRel() + var7) > 1.3F)) {
                     if (var15.getVRel() > -6.0F) {
                        var14 = var13;
                     } else {
                        label70: {
                           if (var1.getLeadOne().getStatus()) {
                              var14 = var13;
                              if (var1.getLeadOne().getDRel() < var15.getDRel() - 10.0F) {
                                 break label70;
                              }
                           }

                           if (var13 != null) {
                              var14 = var13;
                              if (!(var15.getDRel() < var13.getDRel())) {
                                 break label70;
                              }
                           }

                           var14 = var15;
                        }
                     }
                  }
               }
            }

            var8++;
            var13 = var14;
         }

         if (var13 != null) {
            double var5 = Math.max(0.0, var2 + var13.getVRel());
            var5 = Ideas.INSTANCE.approach(var5 + 2.0, var13.getDRel() - 40.0 - 2.0 * var5, 0.5);
            if (var5 < var2 - 0.5) {
               var2 = Math.max(4.4704, var5);
               long var11 = Ideas.INSTANCE.ft(var13.getDRel());
               var4.add(new Ideas.Part("far_lead", var2, 0.0, 0.0, "Slow traffic " + var11 + " ft ahead - easing off", 65, 12, null));
            }
         }
      }
   }

   private final void followFlow(Telemetry var1, double var2, long var4, List<Ideas.Part> var6) {
      ModelSnapshot var16 = var1.getModel();
      ArrayList var15 = new ArrayList();
      RadarTrack[] var17 = var1.getRadar();
      int var14 = var17.length;
      int var11 = 0;

      while (true) {
         boolean var13 = true;
         if (var11 >= var14) {
            if (var15.size() >= 3) {
               this.flow.addLast(new float[]{(float)(var4 % 10000000L), ((Number)CollectionsKt.sorted(var15).get(var15.size() / 2)).floatValue()});
            }

            while (
               !this.flow.isEmpty()
                  && ((float)(var4 % 10000000L) - ((float[])this.flow.first())[0] > 5000.0F || (float)(var4 % 10000000L) < ((float[])this.flow.first())[0])
            ) {
               this.flow.removeFirst();
            }

            if (this.flow.size() < 10) {
               return;
            }

            Iterable var25 = this.flow;
            var var19 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var25, 10));
            Iterator var26 = var25.iterator();

            while (var26.hasNext()) {
               var19.add(((float[])var26.next())[1]);
            }

            List var20 = var19;
            double var7 = ((Number)CollectionsKt.sorted(var20).get(this.flow.size() / 2)).floatValue();
            if (var7 > 10.0 && var7 > 0.7 * var2 && var2 > 2.2 + var7) {
               var4 = Math.round(Ideas.INSTANCE.mph(var7));
               var6.add(new Ideas.Part("follow_flow", 1.3 + var7, 0.0, 0.0, "Following the flow: " + var4 + " mph", 16, 12, null));
            }

            return;
         }

         RadarTrack var18 = var17[var11];
         if (var18.getMeasured()) {
            float var9 = var18.getDRel();
            boolean var12;
            if (-10.0F <= var9 && var9 <= 100.0F) {
               var12 = true;
            } else {
               var12 = false;
            }

            if (var12) {
               float var10 = 0.0F;
               var9 = var10;
               if (var16 != null) {
                  var9 = var10;
                  if (var16.getPosX().length > 2) {
                     var9 = var10;
                     if (var18.getDRel() > 0.0F) {
                        var9 = IdeasRoad.Companion.interp(var18.getDRel(), var16.getPosX(), var16.getPosY());
                     }
                  }
               }

               var9 = Math.abs(var18.getYRel() + var9);
               if (2.0F <= var9 && var9 <= 7.5F) {
                  var12 = var13;
               } else {
                  var12 = false;
               }

               if (var12) {
                  var15.add((float)(var2 + var18.getVRel()));
               }
            }
         }

         var11++;
      }
   }

   private final void glare(Ideas.Here var1, List<Ideas.Part> var2) {
      if (!Double.isNaN(var1.getYaw())) {
         Pair var9 = Companion.sun(var1.getLat(), var1.getLon(), var1.getTMs());
         double var5 = ((Number)var9.component1()).doubleValue();
         double var3 = ((Number)var9.component2()).doubleValue();
         boolean var8 = false;
         boolean var7 = var8;
         if (0.0 <= var5) {
            var7 = var8;
            if (var5 <= 22.0) {
               var7 = true;
            }
         }

         if (var7) {
            if (!(Math.abs(Math.IEEEremainder(var3 - var1.getHdgDeg(), 360.0)) > 22.0)) {
               var2.add(new Ideas.Part("sun_glare", null, 0.3, 0.0, "Sun glare - more room", 26, 10, null));
            }
         }
      }
   }

   private final String leftGap(Telemetry var1, List<Detector.Obj> var2, double var3) {
      boolean var8 = var1.getLeftBlinker();
      Object var9 = null;
      if (var8 && !var1.getRightBlinker() && !(var3 > 3.0)) {
         Iterable var17 = var2;
         var var12 = new ArrayList();
         Iterator var10 = var17.iterator();

         while (true) {
            var8 = var10.hasNext();
            boolean var7 = true;
            if (!var8) {
               List var13 = var12;
               Iterator var14 = var13.iterator();
               Float var15;
               if (!var14.hasNext()) {
                  var15 = (Float)var9;
               } else {
                  Detector.Obj var19 = (Detector.Obj)var14.next();
                  float var22 = var19.getX() / -var19.getVx();

                  while (var14.hasNext()) {
                     Detector.Obj var20 = (Detector.Obj)var14.next();
                     var22 = Math.min(var22, var20.getX() / -var20.getVx());
                  }

                  var15 = var22;
               }

               if (var15 != null) {
                  String var16 = String.format("%.0f", Arrays.copyOf(new Object[]{var15}, 1));
                  Intrinsics.checkNotNullExpressionValue(var16, "format(...)");
                  return "Next oncoming car in " + var16 + " s";
               }

               return "Oncoming lanes look clear";
            }

            Object var11;
            boolean var24;
            label66: {
               var11 = var10.next();
               Detector.Obj var18 = (Detector.Obj)var11;
               if (IdeasSense.Companion.getVEHICLES().contains(var18.getCls()) && var18.getVelValid() && var18.getVx() < -2.0F) {
                  float var5 = var18.getCy();
                  if (-15.0F <= var5 && var5 <= -2.0F) {
                     var24 = true;
                  } else {
                     var24 = false;
                  }

                  if (var24) {
                     var5 = var18.getX();
                     if (5.0F <= var5 && var5 <= 150.0F) {
                        var24 = true;
                     } else {
                        var24 = false;
                     }

                     if (var24) {
                        var24 = var7;
                        break label66;
                     }
                  }
               }

               var24 = false;
            }

            if (var24) {
               var12.add(var11);
            }
         }
      } else {
         return null;
      }
   }

   private final void parking(Ideas.Here var1, List<Detector.Obj> var2, long var3, List<Ideas.Part> var5) {
      if (!Double.isNaN(var1.getYaw())) {
         if (var3 - this.parkSeenMs > 1000L) {
            double var6 = var1.getV();
            boolean var24;
            if (1.0 <= var6 && var6 <= 12.0) {
               var24 = 1;
            } else {
               var24 = 0;
            }

            if (var24) {
               this.parkSeenMs = var3;
               Iterable var29 = var2;
               var var34 = new ArrayList();

               for (Object var30 : var29) {
                  label109: {
                     Detector.Obj var31 = (Detector.Obj)var30;
                     if (IdeasSense.Companion.getVEHICLES().contains(var31.getCls()) && !var31.getMoving()) {
                        float var22 = var31.getCy();
                        if (1.8F <= var22 && var22 <= 4.5F) {
                           var24 = (boolean)1;
                        } else {
                           var24 = (boolean)0;
                        }

                        if (var24) {
                           var22 = var31.getCx();
                           if (3.0F <= var22 && var22 <= 35.0F) {
                              var24 = (boolean)1;
                           } else {
                              var24 = (boolean)0;
                           }

                           if (var24) {
                              var24 = (boolean)1;
                              break label109;
                           }
                        }
                     }

                     var24 = (boolean)0;
                  }

                  if (var24) {
                     var34.add(var30);
                  }
               }

               var2 = var34;
               var2 = CollectionsKt.sortedWith(var2, new Comparator() {
                  @Override
                  public final int compare(T var1, T var2x) {
                     return ComparisonsKt.compareValues(((Detector.Obj)var1).getCx(), ((Detector.Obj)var2x).getCx());
                  }
               });
               var24 = var2.size();

               for (int var25 = 1; var25 < var24; var25++) {
                  float var47 = ((Detector.Obj)var2.get(var25)).getCx() - 2.35F - (((Detector.Obj)var2.get(var25 - 1)).getCx() + 2.35F);
                  boolean var26;
                  if (6.5F <= var47 && var47 <= 14.0F) {
                     var26 = true;
                  } else {
                     var26 = false;
                  }

                  if (var26) {
                     float var23 = (((Detector.Obj)var2.get(var25 - 1)).getCx() + ((Detector.Obj)var2.get(var25)).getCx()) / 2.0F;
                     var47 = (((Detector.Obj)var2.get(var25 - 1)).getCy() + ((Detector.Obj)var2.get(var25)).getCy()) / 2.0F;
                     double var18 = var23;
                     var6 = Math.cos(var1.getYaw());
                     double var20 = var47;
                     double var14 = Math.sin(var1.getYaw());
                     double var8 = var23;
                     double var10 = Math.sin(var1.getYaw());
                     double var16 = var47;
                     double var12 = Math.cos(var1.getYaw());
                     IdeaSpots.record$default(
                        IdeaSpots.INSTANCE,
                        "parking",
                        var1.getLat() + (var8 * var10 - var16 * var12) / 111195.0,
                        var1.getLon() + (var18 * var6 + var20 * var14) / (Math.cos(Math.toRadians(var1.getLat())) * 111320.0),
                        var1.getHdgDeg(),
                        0.0,
                        null,
                        var3,
                        6.0,
                        null,
                        304,
                        null
                     );
                  }
               }

               IdeaSpots.INSTANCE.expire("parking", 1200000L, var3);
            }
         }

         JSONObject var37 = Nav.INSTANCE.getDestination();
         if (var37 != null) {
            double var42 = var37.optDouble("latitude", var37.optDouble("lat", Double.NaN));
            double var44 = var37.optDouble("longitude", var37.optDouble("lon", Double.NaN));
            if (!Double.isNaN(var42) && !Double.isNaN(var44)) {
               Pair var38 = IdeaSpots.INSTANCE.local(var1.getLat(), var1.getLon(), var42, var44);
               var44 = ((Number)var38.component1()).doubleValue();
               var42 = ((Number)var38.component2()).doubleValue();
               if (!(var44 * var44 + var42 * var42 > 250000.0)) {
                  Iterator var39 = IdeaSpots.around$default(
                        IdeaSpots.INSTANCE, var1.getLat(), var1.getLon(), var1.getYaw(), SetsKt.setOf("parking"), 300.0, 0.0, 12.0, 0, false, 416, null
                     )
                     .iterator();

                  boolean var53;
                  do {
                     if (!var39.hasNext()) {
                        var32 = null;
                        break;
                     }

                     var32 = var39.next();
                     if (((IdeaSpots.Seen)var32).getAhead() > 10.0) {
                        var53 = true;
                     } else {
                        var53 = false;
                     }
                  } while (!var53);

                  IdeaSpots.Seen var33 = (IdeaSpots.Seen)var32;
                  if (var33 != null) {
                     long var27 = Ideas.INSTANCE.ft(var33.getAhead());
                     var3 = (var3 - var33.getSpot().getLastMs()) / '\uea60';
                     var5.add(
                        new Ideas.Part("parking", null, 0.0, 0.0, "Open spot ~" + var27 + " ft ahead on the right (seen " + var3 + " min ago)", 8, 14, null)
                     );
                  }
               }
            }
         }
      }
   }

   private final void speedCams(Telemetry var1, Ideas.Here var2, List<Ideas.Part> var3) {
      if (!Double.isNaN(var2.getYaw())) {
         IdeaSpots.Seen var13 = CollectionsKt.firstOrNull(
            IdeaSpots.around$default(
               IdeaSpots.INSTANCE, var2.getLat(), var2.getLon(), var2.getYaw(), SetsKt.setOf("speed_cam"), 250.0, 10.0, 20.0, 0, false, 128, null
            )
         );
         if (var13 != null) {
            double var4 = var1.getSlcSpeedLimit();
            Double var14;
            if (var4 > 4.0) {
               var14 = 0.89408 + var4;
            } else {
               var14 = null;
            }

            label31: {
               if (var14 != null) {
                  double var6 = var14.doubleValue();
                  boolean var8;
                  if (var6 < var2.getV() + 3.0) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  if (var8) {
                     break label31;
                  }
               }

               var14 = null;
            }

            long var11 = Ideas.INSTANCE.ft(Math.max(0.0, var13.getAhead()));
            String var15;
            if (var4 > 4.0) {
               long var9 = Math.round(Ideas.INSTANCE.mph(var4));
               var15 = " - " + var9 + " mph";
            } else {
               var15 = "";
            }

            var3.add(new Ideas.Part("speed_cams", var14, 0.0, 0.0, "Speed camera " + var11 + " ft" + var15, 62, 12, null));
         }
      }
   }

   private final void streak(Telemetry var1, UiSettings var2, double var3, long var5, List<Ideas.Part> var7) {
      boolean var10 = var1.getEnabled();
      if (this.wasEnabled && !var10 && var1.getVEgo() > 2.5F) {
         float var8 = var2.getFloat("streak_m", 0.0F);
         if (var8 > var2.getFloat("streak_best_m", 0.0F)) {
            var2.setFloat("streak_best_m", var8);
         }

         var2.setFloat("streak_m", 0.0F);
         this.lastMile = 0;
      }

      this.wasEnabled = var10;
      if (var10 && var3 > 0.0) {
         float var11 = var2.getFloat("streak_m", 0.0F) + (float)(var1.getVEgo() * var3);
         if (var5 / 1000 % 5 == 0L) {
            var2.setFloat("streak_m", var11);
         } else {
            this.streakMem = var11;
         }

         int var9 = (int)(Math.max(var11, this.streakMem) / 1609.34F);
         if (var9 >= 1 && var9 != this.lastMile && var9 % 5 == 0) {
            this.lastMile = var9;
            this.streakChipUntil = var5 + 6000L;
         }
      }

      if (var5 < this.streakChipUntil) {
         int var12 = this.lastMile;
         var7.add(new Ideas.Part("streak", null, 0.0, 0.0, var12 + " miles without a takeover", 1, 14, null));
      }
   }

   private final void weather(Telemetry var1, UiSettings var2, long var3, List<Ideas.Part> var5) {
      boolean var10 = Ideas.INSTANCE.on(var2, "rain_camera");
      boolean var9 = true;
      boolean var6;
      if (var10 && var3 - this.wipersAtMs < 120000L) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (Ideas.INSTANCE.on(var2, "weather") || var6) {
         int var7;
         if (Ideas.INSTANCE.on(var2, "weather")) {
            var7 = var1.getWeatherId();
         } else {
            var7 = 0;
         }

         boolean var8;
         if (600 <= var7 && var7 < 700) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            var5.add(new Ideas.Part("weather", 17.8816, 0.5, 0.0, "Snow - slower, more room", 22, 8, null));
         } else {
            if (700 <= var7 && var7 < 800) {
               var8 = true;
            } else {
               var8 = false;
            }

            if (var8) {
               var5.add(new Ideas.Part("weather", null, 0.4, 0.0, "Fog - more room", 21, 10, null));
            } else {
               if (200 <= var7 && var7 < 600) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               if (var8 || var6) {
                  label52: {
                     if (var6) {
                        if (200 <= var7 && var7 < 600) {
                           var6 = var9;
                        } else {
                           var6 = false;
                        }

                        if (!var6) {
                           var11 = "Wipers on - more room";
                           break label52;
                        }
                     }

                     var11 = "Rain - more room";
                  }

                  var5.add(new Ideas.Part("weather", null, 0.3, 0.0, var11, 20, 10, null));
               }
            }
         }
      }
   }

   public final long getBusAtMs() {
      return this.busAtMs;
   }

   public final float getBusX() {
      return this.busX;
   }

   public final long getHornAtMs() {
      return this.hornAtMs;
   }

   public final long getWipersAtMs() {
      return this.wipersAtMs;
   }

   public final void setBusAtMs(long var1) {
      this.busAtMs = var1;
   }

   public final void setBusX(float var1) {
      this.busX = var1;
   }

   public final void setHornAtMs(long var1) {
      this.hornAtMs = var1;
   }

   public final void setWipersAtMs(long var1) {
      this.wipersAtMs = var1;
   }

   public final String streakText(UiSettings var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      String var2 = String.format(
         "Now: %.1f mi - best: %.1f mi",
         Arrays.copyOf(
            new Object[]{
               var1.getFloat("streak_m", 0.0F) / 1609.34F, Math.max(var1.getFloat("streak_best_m", 0.0F), var1.getFloat("streak_m", 0.0F)) / 1609.34F
            },
            2
         )
      );
      Intrinsics.checkNotNullExpressionValue(var2, "format(...)");
      return var2;
   }

   public final void tick(Telemetry var1, UiSettings var2, Ideas.Here var3, Detector var4, long var5, List<Ideas.Part> var7) {
      Intrinsics.checkNotNullParameter(var1, "t");
      Intrinsics.checkNotNullParameter(var2, "s");
      Intrinsics.checkNotNullParameter(var7, "out");
      double var8;
      if (this.lastMs == 0L) {
         var8 = 0.0;
      } else {
         var8 = RangesKt.coerceIn((var5 - this.lastMs) / 1000.0, 0.0, 0.5);
      }

      List var19;
      label117: {
         this.lastMs = var5;
         if (var4 != null) {
            var19 = var4.getObjects();
            if (var19 != null) {
               boolean var12;
               if (var5 - var4.getObjectsAtMs() < 800L) {
                  var12 = true;
               } else {
                  var12 = false;
               }

               List var15;
               if (var12) {
                  var15 = var19;
               } else {
                  var15 = null;
               }

               var19 = var15;
               if (var15 != null) {
                  break label117;
               }
            }
         }

         var19 = CollectionsKt.emptyList();
      }

      double var10;
      boolean var18;
      label110: {
         label109: {
            var10 = var1.getVEgo();
            if (!(var10 > 15.0)) {
               if (var3 == null) {
                  break label109;
               }

               OsmRoads.Way var16 = Ideas.INSTANCE.street(var3);
               if (var16 != null && var16.getHighway()) {
                  var18 = true;
               } else {
                  var18 = false;
               }

               if (!var18) {
                  break label109;
               }
            }

            var18 = true;
            break label110;
         }

         var18 = false;
      }

      WantIdeas.INSTANCE.setSchoolBus(Ideas.INSTANCE.on(var2, "school_bus"));
      WantIdeas.INSTANCE.setWipers(Ideas.INSTANCE.on(var2, "rain_camera"));
      if (Ideas.INSTANCE.on(var2, "school_bus") && var5 - this.busAtMs < 2500L) {
         var7.add(
            new Ideas.Part(
               "school_bus",
               Math.max(4.4704, Ideas.INSTANCE.approach(4.4704, this.busX - 15.0, 1.0)),
               0.0,
               0.0,
               "School bus - red lights flashing: STOP",
               99,
               12,
               null
            )
         );
      }

      this.weather(var1, var2, var5, var7);
      if (Ideas.INSTANCE.on(var2, "follow_flow") && var18) {
         this.followFlow(var1, var10, var5, var7);
      }

      if (Ideas.INSTANCE.on(var2, "far_lead")) {
         this.farLead(var1, var10, var7);
      }

      if (Ideas.INSTANCE.on(var2, "streak")) {
         this.streak(var1, var2, var8, var5, var7);
      }

      if (Ideas.INSTANCE.on(var2, "parking") && var3 != null) {
         this.parking(var3, var19, var5, var7);
      }

      if (Ideas.INSTANCE.on(var2, "speed_cams") && var3 != null) {
         MapCams.INSTANCE.want(var3.getLat(), var3.getLon());
         this.speedCams(var1, var3, var7);
      }

      if (Ideas.INSTANCE.on(var2, "city_potholes") && var3 != null) {
         City311.INSTANCE.want(var3.getLat(), var3.getLon());
      }

      Ideas.INSTANCE.getMemory().getLights().setActuated(Ideas.INSTANCE.on(var2, "actuated_lights"));
      if (Ideas.INSTANCE.on(var2, "sun_glare") && var3 != null) {
         this.glare(var3, var7);
      }

      if (Ideas.INSTANCE.on(var2, "double_parked") && var3 != null && !var18) {
         this.doubleParked(var3, var19, var10, var5, var7);
      }

      if (Ideas.INSTANCE.on(var2, "horn")) {
         SirenEar.INSTANCE.setWantHorn(true);
         SirenEar.INSTANCE.want(true);
         if (var5 - this.hornAtMs < 3000L) {
            var7.add(new Ideas.Part("horn", null, 0.3, 0.0, "Someone honked - check around you", 80, 10, null));
         }
      } else {
         SirenEar.INSTANCE.setWantHorn(false);
      }

      if (Ideas.INSTANCE.on(var2, "left_gap")) {
         String var14 = this.leftGap(var1, var19, var10);
         if (var14 != null) {
            var7.add(new Ideas.Part("left_gap", null, 0.0, 0.0, var14, 5, 14, null));
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

      public final Pair<Double, Double> sun(double var1, double var3, long var5) {
         double var9 = var5 / 8.64E7 + 2440587.5 - 2451545.0;
         double var7 = Math.toRadians(0.98560028 * var9 + 357.529);
         var7 = Math.toRadians(Math.sin(var7) * 1.915 + (0.98564736 * var9 + 280.459) + Math.sin(2 * var7) * 0.02);
         double var13 = Math.toRadians(23.439 - 3.6E-7 * var9);
         double var11 = Math.atan2(Math.cos(var13) * Math.sin(var7), Math.cos(var7));
         var7 = Math.asin(Math.sin(var13) * Math.sin(var7));
         var3 = Math.toRadians(15.0 * ((24.06570982441908 * var9 + 18.697374558) % 24.0) + var3) - var11;
         var9 = Math.toRadians(var1);
         var1 = Math.asin(Math.sin(var9) * Math.sin(var7) + Math.cos(var9) * Math.cos(var7) * Math.cos(var3));
         var3 = Math.atan2(-Math.sin(var3), Math.tan(var7) * Math.cos(var9) - Math.sin(var9) * Math.cos(var3));
         return TuplesKt.to(Math.toDegrees(var1), (Math.toDegrees(var3) + 360.0) % 360.0);
      }
   }
}
