package com.starpilot.starview;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

public final class Ideas {
   public static final Ideas INSTANCE = new Ideas();
   private static Context app;
   private static volatile String chip;
   private static volatile Boolean commaOk;
   private static long lastSendMs;
   private static double lastYaw = Double.NaN;
   private static final IdeasMemory memory = new IdeasMemory();
   private static final IdeasMore more = new IdeasMore();
   private static TabletMotion motion;
   private static volatile List<Ideas.Part> parts = CollectionsKt.emptyList();
   private static final IdeasRoad road = new IdeasRoad();
   private static final IdeasSense sense = new IdeasSense();
   private static boolean sentSomething;
   private static long streetMs;
   private static OsmRoads.Way streetWay;
   private static long tick;

   // $VF: synthetic method
   public static Unit $r8$lambda$Zu98bLXrgwdIF0UW9Wk2oev9O18(JSONObject var0) {
      return send$lambda$14(var0);
   }

   private Ideas() {
   }

   // $VF: synthetic method
   public static double approach$default(Ideas var0, double var1, double var3, double var5, int var7, Object var8) {
      if ((var7 & 4) != 0) {
         var5 = 0.8;
      }

      return var0.approach(var1, var3, var5);
   }

   private final void send(List<Ideas.Part> var1, long var2) {
      DataClient var15 = Link.INSTANCE.getData();
      if (var15 != null) {
         if (!var15.getConnected()) {
            sentSomething = false;
         } else {
            Iterable var9 = var1;
            var var19 = new ArrayList();
            Iterator var10 = var9.iterator();

            while (true) {
               boolean var8 = var10.hasNext();
               boolean var7 = true;
               if (!var8) {
                  List var17 = var19;
                  if (var17.isEmpty() && !sentSomething) {
                     return;
                  } else {
                     lastSendMs = var2;
                     HashMap var16 = new HashMap();
                     if (!var17.isEmpty()) {
                        var9 = var17;
                        var var20 = new ArrayList();
                        Iterator var39 = var9.iterator();

                        while (var39.hasNext()) {
                           Double var43 = ((Ideas.Part)var39.next()).getCap();
                           if (var43 != null) {
                              var20.add(var43);
                           }
                        }

                        var1 = var20;
                        Double var22 = CollectionsKt.minOrNull(var1);
                        if (var22 != null) {
                           double var4 = var22.doubleValue();
                           var16.put("cap", var4);
                        }

                        Iterator var23 = var17.iterator();
                        if (!var23.hasNext()) {
                           throw new NoSuchElementException();
                        }

                        double var30 = ((Ideas.Part)var23.next()).getFollow();

                        while (var23.hasNext()) {
                           var30 = Math.max(var30, ((Ideas.Part)var23.next()).getFollow());
                        }

                        Double var24 = var30;
                        boolean var34;
                        if (var24.doubleValue() > 0.0) {
                           var34 = true;
                        } else {
                           var34 = false;
                        }

                        Object var12 = null;
                        if (!var34) {
                           var24 = null;
                        }

                        if (var24 != null) {
                           var30 = var24.doubleValue();
                           var16.put("follow", var30);
                        }

                        Iterator var18 = var17.iterator();
                        Object var25;
                        if (!var18.hasNext()) {
                           var25 = null;
                        } else {
                           var25 = var18.next();
                           if (var18.hasNext()) {
                              Comparable var40 = Math.abs(((Ideas.Part)var25).getOffset());
                              Object var46 = var25;

                              do {
                                 Object var14 = var18.next();
                                 Comparable var13 = Math.abs(((Ideas.Part)var14).getOffset());
                                 var25 = var46;
                                 Comparable var44 = var40;
                                 if (var40.compareTo(var13) < 0) {
                                    var25 = var14;
                                    var44 = var13;
                                 }

                                 var46 = var25;
                                 var40 = var44;
                              } while (var18.hasNext());
                           }
                        }

                        var25 = (Ideas.Part)var25;
                        if (var25 != null) {
                           var25 = ((Ideas.Part)var25).getOffset();
                           if (((Number)var25).doubleValue() == 0.0) {
                              var34 = true;
                           } else {
                              var34 = false;
                           }

                           if (var34) {
                              var25 = null;
                           }

                           if (var25 != null) {
                              var30 = ((Number)var25).doubleValue();
                              var16.put("offset", var30);
                           }
                        }

                        Map var45 = var16;

                        label111: {
                           for (var var25_it : var17) {
                              var25 = var25_it;
                              if (((Ideas.Part)var25).getCap() != null) {
                                 var34 = true;
                              } else {
                                 var34 = false;
                              }

                              if (var34) {
                                 break label111;
                              }
                           }

                           var25 = var12;
                        }

                        Ideas.Part var42 = (Ideas.Part)var25;
                        var25 = var42;
                        if (var42 == null) {
                           var25 = (Ideas.Part)CollectionsKt.first(var17);
                        }

                        var45.put("why", ((Ideas.Part)var25).getId());
                     }

                     sentSomething = var17.isEmpty() ^ true;
                     var15.request("advice", var16, new Ideas$$ExternalSyntheticLambda0());
                     return;
                  }
               }

               Object var37 = var10.next();
               Ideas.Part var11 = (Ideas.Part)var37;
               boolean var6 = var7;
               if (var11.getCap() == null) {
                  var6 = var7;
                  if (!(var11.getFollow() > 0.0)) {
                     if (var11.getOffset() == 0.0) {
                        var6 = true;
                     } else {
                        var6 = false;
                     }

                     if (!var6) {
                        var6 = var7;
                     } else {
                        var6 = false;
                     }
                  }
               }

               if (var6) {
                  var19.add(var37);
               }
            }
         }
      }
   }

   private static final Unit send$lambda$14(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "ack");
      commaOk = var0.optBoolean("ok");
      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void tick$default(Ideas var0, Telemetry var1, UiSettings var2, Detector var3, long var4, int var6, Object var7) {
      if ((var6 & 8) != 0) {
         var4 = System.currentTimeMillis();
      }

      var0.tick(var1, var2, var3, var4);
   }

   public final double approach(double var1, double var3, double var5) {
      return Math.sqrt(var1 * var1 + 2 * var5 * Math.max(0.0, var3));
   }

   public final void endOfDrive() {
      IdeaSpots.saveSoon$default(IdeaSpots.INSTANCE, 0L, true, 1, null);
      memory.endOfDrive();
   }

   public final long ft(double var1) {
      return Math.round(3.28084 * var1 / 10.0) * 10;
   }

   public final String getChip() {
      return chip;
   }

   public final Boolean getCommaOk() {
      return commaOk;
   }

   public final IdeasMemory getMemory() {
      return memory;
   }

   public final IdeasMore getMore() {
      return more;
   }

   public final List<Ideas.Part> getParts() {
      return parts;
   }

   public final IdeasSense getSense() {
      return sense;
   }

   public final Ideas.Here here(Telemetry var1, long var2) {
      Intrinsics.checkNotNullParameter(var1, "t");
      double var10 = Math.max(0.0, (double)var1.getVEgo());
      double[] var14 = RtkPosition.INSTANCE.now(var2, var10);
      double var4;
      double var6;
      double var8;
      String var15;
      if (var14 != null) {
         var4 = var14[0];
         var6 = var14[1];
         var15 = RtkPosition.INSTANCE.getKind();
         var8 = RtkPosition.INSTANCE.getHeading();
      } else {
         label50: {
            OsmRoads.Fix var16 = OsmRoads.INSTANCE.getLastFix();
            if (var16 == null) {
               return null;
            }

            if (System.nanoTime() - var16.getTNs() > 3000000000L) {
               return null;
            }

            var4 = var16.getLat();
            var6 = var16.getLon();
            if (var16.getSpeed() > 2.0F) {
               float var12 = var16.getBearingDeg();
               boolean var13;
               if (!Float.isInfinite(var12) && !Float.isNaN(var12)) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               if (var13) {
                  var8 = var16.getBearingDeg();
                  var15 = "none";
                  break label50;
               }
            }

            var15 = "none";
            var8 = Double.NaN;
         }
      }

      boolean var17;
      if (!Double.isInfinite(var8) && !Double.isNaN(var8)) {
         var17 = true;
      } else {
         var17 = false;
      }

      if (var17 && var10 > 2.0) {
         lastYaw = (Math.PI / 2) - Math.toRadians(var8);
      }

      return new Ideas.Here(var4, var6, lastYaw, var10, var15, var2);
   }

   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      app = var1.getApplicationContext();
      IdeaSpots var4 = IdeaSpots.INSTANCE;
      Context var3 = app;
      Object var2 = null;
      var1 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("app");
         var1 = null;
      }

      var4.init(var1);
      IdeasMemory var7 = memory;
      var1 = app;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("app");
         var1 = (Context)var2;
      }

      var7.init(var1);
   }

   public final double lateralOnStreet(Ideas.Here var1) {
      Intrinsics.checkNotNullParameter(var1, "h");
      if (Intrinsics.areEqual(var1.getRtk(), "fixed") && !Double.isNaN(var1.getYaw()) && !Double.isNaN(OsmRoads.INSTANCE.getLat0())) {
         Pair var7 = OsmRoads.INSTANCE.toLocal(var1.getLat(), var1.getLon());
         double var2 = ((Number)var7.component1()).doubleValue();
         double var4 = ((Number)var7.component2()).doubleValue();
         OsmRoads.Match var8 = OsmRoads.INSTANCE.matchSeg(var2, var4, var1.getYaw());
         if (var8 != null) {
            boolean var6;
            if (var8.getDist() < 15.0) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (!var6) {
               var8 = null;
            }

            if (var8 != null) {
               return var8.rightOf(var1.getYaw());
            }
         }

         return Double.NaN;
      } else {
         return Double.NaN;
      }
   }

   public final double mph(double var1) {
      return var1 / 0.44704;
   }

   public final boolean on(UiSettings var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "s");
      Intrinsics.checkNotNullParameter(var2, "id");
      return NewIdeas.INSTANCE.on(var1, var2);
   }

   public final IdeasRoad road() {
      return road;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final OsmRoads.Way street(Ideas.Here var1) {
      Intrinsics.checkNotNullParameter(var1, "h");
      if (var1.getTMs() - streetMs < 1000L) {
         return streetWay;
      } else {
         streetMs = var1.getTMs();
         boolean var7 = Double.isNaN(var1.getYaw());
         Object var9 = null;
         OsmRoads.Way var8 = (OsmRoads.Way)var9;
         if (!var7) {
            if (Double.isNaN(OsmRoads.INSTANCE.getLat0())) {
               var8 = (OsmRoads.Way)var9;
            } else {
               label44: {
                  try {
                     Pair var14 = OsmRoads.INSTANCE.toLocal(var1.getLat(), var1.getLon());
                     double var2 = ((Number)var14.component1()).doubleValue();
                     double var4 = ((Number)var14.component2()).doubleValue();
                     var13 = OsmRoads.INSTANCE.matchSeg(var2, var4, var1.getYaw());
                  } catch (Exception var12) {
                     var8 = (OsmRoads.Way)var9;
                     break label44;
                  }

                  var8 = (OsmRoads.Way)var9;
                  label41:
                  if (var13 != null) {
                     boolean var6;
                     label39: {
                        label38: {
                           try {
                              if (var13.getDist() < 15.0) {
                                 break label38;
                              }
                           } catch (Exception var11) {
                              var8 = (OsmRoads.Way)var9;
                              break label41;
                           }

                           var6 = false;
                           break label39;
                        }

                        var6 = true;
                     }

                     if (!var6) {
                        var13 = null;
                     }

                     var8 = (OsmRoads.Way)var9;
                     if (var13 != null) {
                        try {
                           var8 = var13.getWay();
                        } catch (Exception var10) {
                           var8 = (OsmRoads.Way)var9;
                        }
                     }
                  }
               }
            }
         }

         streetWay = var8;
         return streetWay;
      }
   }

   public final void tick(Telemetry var1, UiSettings var2, Detector var3, long var4) {
      Intrinsics.checkNotNullParameter(var1, "t");
      Intrinsics.checkNotNullParameter(var2, "s");
      if (app != null) {
         tick++;
         var var9 = NewIdeas.INSTANCE.getALL();
         boolean var6;
         if (var9 instanceof Collection && ((Collection)var9).isEmpty()) {
            var6 = 0;
         } else {
            Iterator var25 = var9.iterator();

            while (true) {
               if (!var25.hasNext()) {
                  var6 = 0;
                  break;
               }

               NewIdeas.Idea var10 = (NewIdeas.Idea)var25.next();
               if (var2.get(NewIdeas.INSTANCE.key(var10.getId()))) {
                  var6 = 1;
                  break;
               }
            }
         }

         Object var30 = null;
         if (!var6) {
            if (sentSomething) {
               this.send(CollectionsKt.emptyList(), var4);
            }

            TabletMotion var16 = motion;
            if (var16 != null) {
               var16.stop();
            }

            chip = null;
            parts = CollectionsKt.emptyList();
         } else {
            if (!this.on(var2, "speed_bumps") && !this.on(var2, "potholes")) {
               TabletMotion var28 = motion;
               if (var28 != null) {
                  var28.stop();
               }
            } else {
               TabletMotion var11 = motion;
               TabletMotion var26 = var11;
               if (var11 == null) {
                  Context var31 = app;
                  Context var27 = var31;
                  if (var31 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("app");
                     var27 = null;
                  }

                  var26 = new TabletMotion(var27);
                  motion = var26;
               }

               var26.setSpeed(var1.getVEgo());
               var26.setAEgo(var1.getAEgo());
               var26.setYawRate(var1.getYawRate());
               var26.start();
            }

            Ideas.Here var32 = this.here(var1, var4);
            ArrayList var29 = new ArrayList();
            road.tick(var1, var2, var32, motion, var4, var29);
            sense.tick(var1, var2, var32, var3, var4, var29);
            memory.tick(var1, var2, var32, var3, var4, var29);
            more.tick(var1, var2, var32, var3, var4, var29);
            parts = var29;
            Iterable var17 = var29;
            var var12 = new ArrayList();

            for (Object var21 : var17) {
               if (((Ideas.Part)var21).getChip().length() > 0) {
                  var6 = (boolean)1;
               } else {
                  var6 = (boolean)0;
               }

               if (var6) {
                  var12.add(var21);
               }
            }

            List var13 = var12;
            Iterator var33 = var13.iterator();
            Object var14;
            if (!var33.hasNext()) {
               var14 = null;
            } else {
               var14 = var33.next();
               if (var33.hasNext()) {
                  int var7 = ((Ideas.Part)var14).getPrio();
                  Object var19 = var14;

                  do {
                     Object var22 = var33.next();
                     int var8 = ((Ideas.Part)var22).getPrio();
                     var14 = var19;
                     var6 = var7;
                     if (var7 < var8) {
                        var14 = var22;
                        var6 = var8;
                     }

                     var19 = var14;
                     var7 = var6;
                  } while (var33.hasNext());
               }
            }

            Ideas.Part var20 = (Ideas.Part)var14;
            var14 = var30;
            if (var20 != null) {
               var14 = var20.getChip();
            }

            chip = (String)var14;
            if (var4 - lastSendMs >= 500L || var29.isEmpty() && sentSomething) {
               this.send(var29, var4);
            }

            if (tick % 200 == 0L) {
               IdeaSpots.saveSoon$default(IdeaSpots.INSTANCE, var4, false, 2, null);
            }
         }
      }
   }

   public static final class Here {
      private final double lat;
      private final double lon;
      private final String rtk;
      private final long tMs;
      private final double v;
      private final double yaw;

      public Here(double var1, double var3, double var5, double var7, String var9, long var10) {
         Intrinsics.checkNotNullParameter(var9, "rtk");
         super();
         this.lat = var1;
         this.lon = var3;
         this.yaw = var5;
         this.v = var7;
         this.rtk = var9;
         this.tMs = var10;
      }

      public final double getHdgDeg() {
         double var1;
         if (Double.isNaN(this.yaw)) {
            var1 = Double.NaN;
         } else {
            var1 = (90.0 - Math.toDegrees(this.yaw) + 360.0) % 360.0;
         }

         return var1;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLon() {
         return this.lon;
      }

      public final String getRtk() {
         return this.rtk;
      }

      public final long getTMs() {
         return this.tMs;
      }

      public final double getV() {
         return this.v;
      }

      public final double getYaw() {
         return this.yaw;
      }
   }

   public static final class Part {
      private final Double cap;
      private final String chip;
      private final double follow;
      private final String id;
      private final double offset;
      private final int prio;

      public Part(String var1, Double var2, double var3, double var5, String var7, int var8) {
         Intrinsics.checkNotNullParameter(var1, "id");
         Intrinsics.checkNotNullParameter(var7, "chip");
         super();
         this.id = var1;
         this.cap = var2;
         this.follow = var3;
         this.offset = var5;
         this.chip = var7;
         this.prio = var8;
      }

      // $VF: synthetic method
      public Part(String var1, Double var2, double var3, double var5, String var7, int var8, int var9, DefaultConstructorMarker var10) {
         if ((var9 & 2) != 0) {
            var2 = null;
         }

         double var11 = 0.0;
         if ((var9 & 4) != 0) {
            var3 = 0.0;
         }

         if ((var9 & 8) != 0) {
            var5 = var11;
         }

         if ((var9 & 16) != 0) {
            var7 = "";
         }

         if ((var9 & 32) != 0) {
            var8 = 0;
         }

         this(var1, var2, var3, var5, var7, var8);
      }

      public final Double getCap() {
         return this.cap;
      }

      public final String getChip() {
         return this.chip;
      }

      public final double getFollow() {
         return this.follow;
      }

      public final String getId() {
         return this.id;
      }

      public final double getOffset() {
         return this.offset;
      }

      public final int getPrio() {
         return this.prio;
      }
   }
}
