package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class IdeasSense {
   public static final IdeasSense.Companion Companion = new IdeasSense.Companion(null);
   private static final Set<Integer> VEHICLES = SetsKt.setOf(new Integer[]{2, 3, 5, 7});
   private long driftMs;
   private String driftText;
   private double eyesFrom;
   private int eyesSide;
   private long eyesUntil;
   private final ArrayList<IdeasSense.Ghost> ghosts;
   private long lastMs;
   private final LeadLamps leadLamps;
   private double leadSigFrom;
   private long lookSince;
   private final HashMap<Long, IdeasSense.Person> people = new HashMap<>();
   private final ArrayDeque<Float> restPitch;
   private final ArrayDeque<Float> restYaw;
   private volatile long shadowAtMs;
   private volatile long sirenUntil;

   public IdeasSense() {
      this.ghosts = new ArrayList<>();
      this.eyesFrom = Double.NaN;
      this.restYaw = new ArrayDeque<>();
      this.restPitch = new ArrayDeque<>();
      this.leadLamps = new LeadLamps();
      this.leadSigFrom = Double.NaN;
   }

   private final void dooring(List<Detector.Obj> var1, double var2, List<Ideas.Part> var4) {
      if (!(var2 < 3.0)) {
         for (Detector.Obj var11 : var1) {
            if (VEHICLES.contains(var11.getCls()) && !var11.getMoving()) {
               float var7 = var11.getX();
               boolean var9 = true;
               boolean var8;
               if (2.0F <= var7 && var7 <= 25.0F) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               if (var8) {
                  var7 = Math.abs(var11.getCy());
                  if (1.6F <= var7 && var7 <= 4.2F) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  if (var8) {
                     var var12 = var1;
                     if (var12 instanceof Collection && ((Collection)var12).isEmpty()) {
                        var8 = false;
                     } else {
                        Iterator var13 = var12.iterator();

                        while (true) {
                           if (!var13.hasNext()) {
                              var8 = false;
                              break;
                           }

                           Detector.Obj var19 = (Detector.Obj)var13.next();
                           if (var19.getCls() == 0
                              && Math.abs(var19.getX() - var11.getCx()) < 3.0F
                              && Math.abs(var19.getY()) < Math.abs(var11.getCy()) + 0.3F
                              && Math.abs(var19.getY()) > Math.abs(var11.getCy()) - 2.0F) {
                              var8 = true;
                           } else {
                              var8 = false;
                           }

                           if (var8) {
                              var8 = var9;
                              break;
                           }
                        }
                     }

                     if (var8) {
                        double var5 = Math.max(4.4704, var2 - 1.3);
                        if (var11.getCy() > 0.0F) {
                           var2 = -0.3;
                        } else {
                           var2 = 0.3;
                        }

                        var4.add(new Ideas.Part("dooring", var5, 0.0, var2, "Door zone - giving room", 75, 4, null));
                        return;
                     }
                  }
               }
            }
         }
      }
   }

   private final void drift(List<Detector.Obj> var1, long var2) {
      this.driftText = null;

      for (Detector.Obj var7 : var1) {
         if (VEHICLES.contains(var7.getCls()) && var7.getVelValid()) {
            float var4 = var7.getX();
            boolean var6 = true;
            boolean var5;
            if (0.0F <= var4 && var4 <= 30.0F) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var5) {
               var4 = Math.abs(var7.getCy());
               if (1.6F <= var4 && var4 <= 4.8F) {
                  var5 = var6;
               } else {
                  var5 = false;
               }

               if (var5 && -Math.signum(var7.getCy()) * var7.getVy() > 0.5F) {
                  this.driftText = "Car beside you turning toward your lane - making room";
                  this.driftMs = var2;
                  return;
               }
            }
         }
      }
   }

   private final void eyes(Telemetry var1, double var2, long var4, List<Ideas.Part> var6) {
      if (!var1.getDmFace()) {
         this.lookSince = 0L;
      } else {
         float var7 = var1.getDmYaw();
         float var8 = var1.getDmPitch();
         if (var2 < 0.5 && (Math.abs(var7) > 0.25F || var8 < -0.25F)) {
            this.restYaw.addLast(var7);
            this.restPitch.addLast(var8);

            while (this.restYaw.size() > 300) {
               this.restYaw.removeFirst();
               this.restPitch.removeFirst();
            }
         }

         Float var13 = CollectionsKt.getOrNull(CollectionsKt.sorted(this.restYaw), this.restYaw.size() / 2);
         Float var14 = CollectionsKt.getOrNull(CollectionsKt.sorted(this.restPitch), this.restPitch.size() / 2);
         byte var11 = 1;
         boolean var12 = false;
         boolean var10;
         if (var13 != null && var14 != null && Math.abs(var7 - var13) < 0.22F && Math.abs(var8 - var14) < 0.22F) {
            var10 = true;
         } else {
            var10 = false;
         }

         byte var9 = var12;
         if (Math.abs(var7) > 0.4F) {
            var9 = var12;
            if (var8 > -0.2F) {
               var9 = var12;
               if (!var10) {
                  if (3.0 <= var2 && var2 <= 13.5) {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  var9 = var12;
                  if (var10) {
                     var9 = var12;
                     if (var1.getEnabled()) {
                        var9 = 1;
                     }
                  }
               }
            }
         }

         if (var9) {
            if (this.lookSince == 0L) {
               this.lookSince = var4;
            }
         } else {
            this.lookSince = 0L;
         }

         if (this.lookSince != 0L && var4 - this.lookSince > 1200L) {
            this.eyesUntil = 4000L + var4;
            var9 = var11;
            if (var7 > 0.0F) {
               var9 = -1;
            }

            this.eyesSide = var9;
            if (Double.isNaN(this.eyesFrom)) {
               this.eyesFrom = var2;
            }
         }

         if (var4 < this.eyesUntil) {
            var2 = Math.max(4.4704, this.eyesFrom - 1.3);
            String var15;
            if (this.eyesSide < 0) {
               var15 = "left";
            } else {
               var15 = "right";
            }

            var6.add(new Ideas.Part("eyes", var2, 0.0, 0.0, "You're watching the " + var15 + " - easing off", 45, 12, null));
         } else {
            this.eyesFrom = Double.NaN;
         }
      }
   }

   private final void ghosts(List<Detector.Obj> var1, double var2, float var4, long var5, List<Ideas.Part> var7) {
      Iterable var17 = var1;
      var var16 = new ArrayList();

      for (Object var18 : var17) {
         Detector.Obj var39 = (Detector.Obj)var18;
         boolean var12;
         if (VEHICLES.contains(var39.getCls()) && !var39.getMoving()) {
            var12 = true;
         } else {
            var12 = false;
         }

         if (var12) {
            var16.add(var18);
         }
      }

      List var33 = var16;
      HashSet var40 = new HashSet();
      Iterator var21 = var1.iterator();

      while (true) {
         boolean var13 = var21.hasNext();
         float var9 = 0.0F;
         float var10 = 0.0F;
         if (!var13) {
            Iterator var22 = this.people.entrySet().iterator();

            while (var22.hasNext()) {
               Object var34 = var22.next();
               Intrinsics.checkNotNullExpressionValue(var34, "next(...)");
               Entry var43 = (Entry)var34;
               var34 = var43.getKey();
               Intrinsics.checkNotNullExpressionValue(var34, "component1(...)");
               var34 = (Long)var34;
               IdeasSense.Person var44 = (IdeasSense.Person)var43.getValue();
               Intrinsics.checkNotNullExpressionValue(var44, "component2(...)");
               var44 = var44;
               if (!var40.contains(var34) && var5 - var44.getSeenMs() > 400L) {
                  if (var44.getBehind()) {
                     float var26 = var44.getX();
                     boolean var30;
                     if (3.0F <= var26 && var26 <= 35.0F) {
                        var30 = true;
                     } else {
                        var30 = false;
                     }

                     if (var30) {
                        var26 = Math.abs(var44.getY());
                        if (1.0F <= var26 && var26 <= 7.0F) {
                           var30 = true;
                        } else {
                           var30 = false;
                        }

                        if (var30 && var44.getVy() * var44.getY() < 0.0F && Math.abs(var44.getVy()) > 0.5F) {
                           this.ghosts.add(new IdeasSense.Ghost(var44.getX(), var44.getY(), var44.getVy(), var5));
                        }
                     }
                  }

                  var22.remove();
               }
            }

            Iterator var41 = this.ghosts.iterator();
            Intrinsics.checkNotNullExpressionValue(var41, "iterator(...)");
            IdeasSense.Ghost var23 = null;

            while (var41.hasNext()) {
               IdeasSense.Ghost var37 = (IdeasSense.Ghost)var41.next();
               Intrinsics.checkNotNullExpressionValue(var37, "next(...)");
               var37 = var37;
               var37.setX(var37.getX() - (float)(var4 * var2));
               var37.setY(var37.getY() + var37.getVy() * var4);
               if (var5 - var37.getBornMs() > 4000L || var37.getX() < 0.0F) {
                  var41.remove();
               } else if (Math.abs(var37.getY()) < 2.0F && (var23 == null || var37.getX() < var23.getX())) {
                  var23 = var37;
               }
            }

            if (var23 != null) {
               var7.add(
                  new Ideas.Part(
                     "ghosts",
                     Math.max(4.4704, Ideas.approach$default(Ideas.INSTANCE, 6.7056, var23.getX() - 5.0, 0.0, 4, null)),
                     0.0,
                     0.0,
                     "Someone may step out from behind a parked car",
                     85,
                     12,
                     null
                  )
               );
            }

            return;
         }

         Detector.Obj var42 = (Detector.Obj)var21.next();
         if (var42.getCls() == 0 && !var42.getWideCam()) {
            var40.add(var42.getId());
            var var46 = var33;
            if (var46 instanceof Collection && ((Collection)var46).isEmpty()) {
               var13 = false;
            } else {
               Iterator var20 = var46.iterator();

               while (true) {
                  if (!var20.hasNext()) {
                     var13 = false;
                     break;
                  }

                  Detector.Obj var47 = (Detector.Obj)var20.next();
                  boolean var29;
                  if (Math.abs(var47.getCx() - var42.getX()) < 5.0F && Math.abs(var47.getCy() - var42.getY()) < 2.5F) {
                     var29 = true;
                  } else {
                     var29 = false;
                  }

                  if (var29) {
                     var13 = true;
                     break;
                  }
               }
            }

            IdeasSense.Person var48 = this.people.get(var42.getId());
            if (var48 != null) {
               var48.setX(var42.getX());
               var48.setY(var42.getY());
               float var8;
               if (var42.getVelValid()) {
                  var8 = var42.getVx();
               } else {
                  var8 = 0.0F;
               }

               var48.setVx(var8);
               var8 = var10;
               if (var42.getVelValid()) {
                  var8 = var42.getVy();
               }

               var48.setVy(var8);
               var48.setSeenMs(var5);
               var48.setBehind(var13);
            } else {
               Map var49 = this.people;
               long var14 = var42.getId();
               var10 = var42.getX();
               float var11 = var42.getY();
               float var25;
               if (var42.getVelValid()) {
                  var25 = var42.getVx();
               } else {
                  var25 = 0.0F;
               }

               if (var42.getVelValid()) {
                  var9 = var42.getVy();
               }

               var49.put(var14, new IdeasSense.Person(var10, var11, var25, var9, var5, var13));
            }
         }
      }
   }

   private final void leadBlinkers(Telemetry var1, double var2, long var4, List<Ideas.Part> var6) {
      int var7 = this.leadLamps.side(var4);
      Lead var8 = var1.getLeadOne();
      if (var7 != 0 && var8.getStatus() && !(var8.getDRel() > 60.0F) && !(var2 < 4.0)) {
         if (Double.isNaN(this.leadSigFrom)) {
            this.leadSigFrom = var2;
         }

         var2 = Math.max(4.4704, this.leadSigFrom - 1.0);
         String var9;
         if (var7 < 0) {
            var9 = "left";
         } else {
            var9 = "right";
         }

         var6.add(new Ideas.Part("lead_blinkers", var2, 0.3, 0.0, "Car ahead signaling " + var9 + " - easing off", 55, 8, null));
      } else {
         this.leadSigFrom = Double.NaN;
      }
   }

   public final String cameraDrift(long var1) {
      String var4 = this.driftText;
      if (var4 != null) {
         boolean var3;
         if (var1 - this.driftMs < 600L) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            return var4;
         }
      }

      return null;
   }

   public final LeadLamps getLeadLamps() {
      return this.leadLamps;
   }

   public final long getShadowAtMs() {
      return this.shadowAtMs;
   }

   public final long getSirenUntil() {
      return this.sirenUntil;
   }

   public final void setShadowAtMs(long var1) {
      this.shadowAtMs = var1;
   }

   public final void setSirenUntil(long var1) {
      this.sirenUntil = var1;
   }

   public final void tick(Telemetry var1, UiSettings var2, Ideas.Here var3, Detector var4, long var5, List<Ideas.Part> var7) {
      Intrinsics.checkNotNullParameter(var1, "t");
      Intrinsics.checkNotNullParameter(var2, "s");
      Intrinsics.checkNotNullParameter(var7, "out");
      float var10;
      if (this.lastMs == 0L) {
         var10 = 0.0F;
      } else {
         var10 = RangesKt.coerceIn((float)(var5 - this.lastMs) / 1000.0F, 0.0F, 0.5F);
      }

      List var14;
      label73: {
         this.lastMs = var5;
         if (var4 != null) {
            var14 = var4.getObjects();
            if (var14 != null) {
               boolean var11;
               if (var5 - var4.getObjectsAtMs() < 800L) {
                  var11 = true;
               } else {
                  var11 = false;
               }

               List var13;
               if (var11) {
                  var13 = var14;
               } else {
                  var13 = null;
               }

               var14 = var13;
               if (var13 != null) {
                  break label73;
               }
            }
         }

         var14 = CollectionsKt.emptyList();
      }

      double var8 = var1.getVEgo();
      this.leadLamps.setEnabled(Ideas.INSTANCE.on(var2, "lead_blinkers"));
      if (Ideas.INSTANCE.on(var2, "ghosts")) {
         this.ghosts(var14, var8, var10, var5, var7);
      }

      if (Ideas.INSTANCE.on(var2, "dooring")) {
         this.dooring(var14, var8, var7);
      }

      if (Ideas.INSTANCE.on(var2, "wheels")) {
         this.drift(var14, var5);
      } else {
         this.driftText = null;
      }

      if (Ideas.INSTANCE.on(var2, "lead_blinkers")) {
         this.leadBlinkers(var1, var8, var5, var7);
      } else {
         this.leadSigFrom = Double.NaN;
      }

      if (Ideas.INSTANCE.on(var2, "sirens")) {
         SirenEar.INSTANCE.want(true);
         if (var5 < this.sirenUntil) {
            var7.add(new Ideas.Part("sirens", null, 0.3, 0.0, "Siren nearby - be ready to pull over", 90, 10, null));
         }
      } else {
         SirenEar.INSTANCE.want(false);
      }

      if (Ideas.INSTANCE.on(var2, "shadows") && var5 - this.shadowAtMs < 2500L) {
         var7.add(new Ideas.Part("shadows", null, 0.0, 0.0, "Moving shadow at the corner - someone may be coming", 50, 14, null));
      }

      if (Ideas.INSTANCE.on(var2, "eyes")) {
         this.eyes(var1, var8, var5, var7);
      }

      if (Ideas.INSTANCE.on(var2, "green_countdown") || Ideas.INSTANCE.on(var2, "hybrid")) {
         Ideas.INSTANCE.getMemory().getLights().see(var3, var14, var5);
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final Set<Integer> getVEHICLES() {
         return IdeasSense.VEHICLES;
      }
   }

   private static final class Ghost {
      private final long bornMs;
      private final float vy;
      private float x;
      private float y;

      public Ghost(float var1, float var2, float var3, long var4) {
         this.x = var1;
         this.y = var2;
         this.vy = var3;
         this.bornMs = var4;
      }

      public final long getBornMs() {
         return this.bornMs;
      }

      public final float getVy() {
         return this.vy;
      }

      public final float getX() {
         return this.x;
      }

      public final float getY() {
         return this.y;
      }

      public final void setX(float var1) {
         this.x = var1;
      }

      public final void setY(float var1) {
         this.y = var1;
      }
   }

   private static final class Person {
      private boolean behind;
      private long seenMs;
      private float vx;
      private float vy;
      private float x;
      private float y;

      public Person(float var1, float var2, float var3, float var4, long var5, boolean var7) {
         this.x = var1;
         this.y = var2;
         this.vx = var3;
         this.vy = var4;
         this.seenMs = var5;
         this.behind = var7;
      }

      public final boolean getBehind() {
         return this.behind;
      }

      public final long getSeenMs() {
         return this.seenMs;
      }

      public final float getVx() {
         return this.vx;
      }

      public final float getVy() {
         return this.vy;
      }

      public final float getX() {
         return this.x;
      }

      public final float getY() {
         return this.y;
      }

      public final void setBehind(boolean var1) {
         this.behind = var1;
      }

      public final void setSeenMs(long var1) {
         this.seenMs = var1;
      }

      public final void setVx(float var1) {
         this.vx = var1;
      }

      public final void setVy(float var1) {
         this.vy = var1;
      }

      public final void setX(float var1) {
         this.x = var1;
      }

      public final void setY(float var1) {
         this.y = var1;
      }
   }
}
