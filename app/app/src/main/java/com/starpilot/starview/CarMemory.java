package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class CarMemory {
   public static final double BSM_FROM = -9.0;
   public static final long BSM_QUIET_MS = 800L;
   public static final double BSM_TO = -1.0;
   public static final double BUMPER = 2.3;
   public static final CarMemory.Companion Companion = new CarMemory.Companion(null);
   public static final long KEEP_MS = 8000L;
   public static final long SEEN_MS = 350L;
   public static final double Y_MAX = 6.5;
   public static final double Y_MIN = 1.8;
   private final HashMap<Long, CarMemory.Car> byId = new HashMap<>();
   private volatile List<CarMemory.Car> ghosts;
   private long lastMs;
   private long lastRadarFrame = -1L;

   // $VF: synthetic method
   public static boolean $r8$lambda$3wCqkKXhNHYsuWaICgF7cbO6rbo(List var0, CarMemory.Car var1) {
      return update$lambda$2(var0, var1);
   }

   public CarMemory() {
      this.ghosts = CollectionsKt.emptyList();
   }

   private final void see(long var1, double var3, double var5, float var7, float var8, long var9) {
      if (!(var3 > 110.0) && !(Math.abs(var5) > 9.0)) {
         Map var13 = this.byId;
         Long var12 = var1;
         CarMemory.Car var11 = (CarMemory.Car)var13.get(var12);
         if (var11 == null) {
            var11 = new CarMemory.Car(var1);
            var13.put(var12, var11);
         }

         var11 = var11;
         var11.setX(var3);
         var11.setY(var5);
         var11.setVRel(var7);
         var11.setVAbs(var8);
         var11.setLastSeenMs(var9);
         var11.setHits(var11.getHits() + 1);
      }
   }

   private static final boolean update$lambda$2(List var0, CarMemory.Car var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      boolean var5 = var1.getRemembered();
      boolean var4 = false;
      boolean var3 = var4;
      if (var5) {
         var var7 = var0;
         boolean var9;
         if (var7 instanceof Collection && ((Collection)var7).isEmpty()) {
            var9 = false;
         } else {
            Iterator var8 = var7.iterator();

            while (true) {
               if (!var8.hasNext()) {
                  var9 = false;
                  break;
               }

               CarMemory.Car var6 = (CarMemory.Car)var8.next();
               if (var6 != var1 && Math.abs(var6.getX() - var1.getX()) < 6.0 && Math.abs(var6.getY() - var1.getY()) < 1.5) {
                  var9 = true;
               } else {
                  var9 = false;
               }

               if (var9) {
                  var9 = true;
                  break;
               }
            }
         }

         var3 = var4;
         if (var9) {
            var3 = true;
         }
      }

      return var3;
   }

   public final boolean beside(int var1) {
      var var7 = this.ghosts;
      boolean var6 = var7 instanceof Collection;
      boolean var5 = false;
      if (!var6 || !((Collection)var7).isEmpty()) {
         for (CarMemory.Car var12 : var7) {
            double var2 = var12.getY();
            boolean var4;
            if (var1 < 0 ? !(var2 > 0.0) : !(var2 < 0.0)) {
               var4 = false;
            } else {
               var4 = true;
            }

            label40: {
               if (var4) {
                  var2 = var12.getX();
                  if (-10.0 <= var2 && var2 <= 5.0) {
                     var4 = true;
                  } else {
                     var4 = false;
                  }

                  if (var4 && Math.abs(var12.getY()) <= 5.0) {
                     var4 = true;
                     break label40;
                  }
               }

               var4 = false;
            }

            if (var4) {
               var5 = true;
               break;
            }
         }
      }

      return var5;
   }

   public final List<CarMemory.Car> getGhosts() {
      return this.ghosts;
   }

   public final void update(Telemetry var1, long var2, boolean var4, boolean var5) {
      Intrinsics.checkNotNullParameter(var1, "t");
      double var6;
      if (this.lastMs == 0L) {
         var6 = 0.0;
      } else {
         var6 = RangesKt.coerceIn((var2 - this.lastMs) / 1000.0, 0.0, 0.5);
      }

      this.lastMs = var2;
      if (var1.getStarted() && !(var1.getVEgo() < 2.0F)) {
         if (var1.getRadarFrame() != this.lastRadarFrame) {
            this.lastRadarFrame = var1.getRadarFrame();

            for (RadarTrack var14 : var1.getRadar()) {
               if (var14.getMeasured()) {
                  this.see(var14.getId(), var14.getDRel() + 2.3, -var14.getYRel(), var14.getVRel(), var1.getVEgo() + var14.getVRel(), var2);
               }
            }
         }

         Iterator var19 = CollectionsKt.listOf(new Lead[]{var1.getLeadLeft(), var1.getLeadRight()}).iterator();

         for (int var26 = 0; var19.hasNext(); var26++) {
            Lead var32 = (Lead)var19.next();
            if (var32.getStatus()) {
               this.see(-200L - var26, var32.getDRel() + 2.3, -var32.getYRel(), var32.getVRel(), var32.getVLead(), var2);
            }
         }

         ArrayList var36 = new ArrayList();
         Collection var20 = this.byId.values();
         Intrinsics.checkNotNullExpressionValue(var20, "<get-values>(...)");
         Iterable var33 = var20;
         var var21 = new ArrayList();

         for (var var33_it : var33) {

            var33 = var33_it;
            CarMemory.Car var16 = (CarMemory.Car)var33;
            boolean var27;
            if (var2 - var16.getLastSeenMs() <= 350L && var16.getHits() >= 3) {
               var27 = true;
            } else {
               var27 = false;
            }

            if (var27) {
               var21.add(var33);
            }
         }

         List var35 = var21;
         Collection var22 = this.byId.values();
         Intrinsics.checkNotNullExpressionValue(var22, "<get-values>(...)");
         CollectionsKt.removeAll(var22, new CarMemory$$ExternalSyntheticLambda0(var35));
         Iterator var23 = this.byId.values().iterator();

         while (var23.hasNext()) {
            CarMemory.Car var37 = (CarMemory.Car)var23.next();
            Intrinsics.checkNotNullExpressionValue(var37, "next(...)");
            var37 = var37;
            if (var2 - var37.getLastSeenMs() <= 350L) {
               var37.setRemembered(false);
            } else {
               double var8 = Math.abs(var37.getY());
               boolean var28;
               if (1.8 <= var8 && var8 <= 6.5) {
                  var28 = true;
               } else {
                  var28 = false;
               }

               if (var28 && var37.getX() < 30.0 && var37.getVAbs() > 3.0F && var37.getHits() >= 5) {
                  var28 = true;
               } else {
                  var28 = false;
               }

               if (!var28) {
                  if (var2 - var37.getLastSeenMs() > 1500L) {
                     var23.remove();
                  }
               } else {
                  if (!var37.getRemembered()) {
                     var37.setRemembered(true);
                     var37.setRememberedSinceMs(var2);
                     var37.setBsmQuietSinceMs(0L);
                  }

                  var37.setX(var37.getX() + var37.getVRel() * var6);
                  boolean var12;
                  if (var37.getY() > 0.0) {
                     var12 = var4;
                  } else {
                     var12 = var5;
                  }

                  var8 = var37.getX();
                  if (-9.0 <= var8 && var8 <= -1.0) {
                     var28 = true;
                  } else {
                     var28 = false;
                  }

                  if (var28 && var12) {
                     var37.setRememberedSinceMs(var2);
                     var37.setBsmQuietSinceMs(0L);
                  } else if (var28) {
                     if (var37.getBsmQuietSinceMs() == 0L) {
                        var37.setBsmQuietSinceMs(var2);
                     }
                  } else {
                     var37.setBsmQuietSinceMs(0L);
                  }

                  if ((var37.getBsmQuietSinceMs() == 0L || var2 - var37.getBsmQuietSinceMs() <= 800L)
                     && var2 - var37.getRememberedSinceMs() <= 8000L
                     && !(var37.getX() < -25.0)
                     && !(var37.getX() > 60.0)) {
                     var28 = false;
                  } else {
                     var28 = true;
                  }

                  if (var28) {
                     var23.remove();
                  } else {
                     var36.add(var37);
                  }
               }
            }
         }

         Iterable var40 = var36;
         var var39 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var40, 10));

         for (CarMemory.Car var17 : var40) {
            CarMemory.Car var18 = new CarMemory.Car(var17.getId());
            var18.setX(var17.getX());
            var18.setY(var17.getY());
            var18.setVRel(var17.getVRel());
            var18.setVAbs(var17.getVAbs());
            var18.setRemembered(true);
            var39.add(var18);
         }

         List var24 = var39;
         this.ghosts = var24;
      } else {
         this.byId.clear();
         this.ghosts = CollectionsKt.emptyList();
      }
   }

   public static final class Car {
      private long bsmQuietSinceMs;
      private int hits;
      private final long id;
      private long lastSeenMs;
      private boolean remembered;
      private long rememberedSinceMs;
      private float vAbs;
      private float vRel;
      private double x;
      private double y;

      public Car(long var1) {
         this.id = var1;
      }

      public final long getBsmQuietSinceMs() {
         return this.bsmQuietSinceMs;
      }

      public final int getHits() {
         return this.hits;
      }

      public final long getId() {
         return this.id;
      }

      public final long getLastSeenMs() {
         return this.lastSeenMs;
      }

      public final boolean getRemembered() {
         return this.remembered;
      }

      public final long getRememberedSinceMs() {
         return this.rememberedSinceMs;
      }

      public final float getVAbs() {
         return this.vAbs;
      }

      public final float getVRel() {
         return this.vRel;
      }

      public final double getX() {
         return this.x;
      }

      public final double getY() {
         return this.y;
      }

      public final void setBsmQuietSinceMs(long var1) {
         this.bsmQuietSinceMs = var1;
      }

      public final void setHits(int var1) {
         this.hits = var1;
      }

      public final void setLastSeenMs(long var1) {
         this.lastSeenMs = var1;
      }

      public final void setRemembered(boolean var1) {
         this.remembered = var1;
      }

      public final void setRememberedSinceMs(long var1) {
         this.rememberedSinceMs = var1;
      }

      public final void setVAbs(float var1) {
         this.vAbs = var1;
      }

      public final void setVRel(float var1) {
         this.vRel = var1;
      }

      public final void setX(double var1) {
         this.x = var1;
      }

      public final void setY(double var1) {
         this.y = var1;
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }
}
