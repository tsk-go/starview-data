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

public final class Traffic {
   public static final double BUMPER = 2.3;
   public static final Traffic.Companion Companion = new Traffic.Companion(null);
   private static final long KEEP_MS = 600L;
   private static final long LEAD_ID_BASE = -100L;
   private static final double MAX_X = 110.0;
   private static final double MAX_Y = 9.0;
   private static final int MIN_HITS = 3;
   private final HashMap<Long, Traffic.Vehicle> byId = new HashMap<>();
   private long lastRadarFrame = -1L;
   private final ArrayList<Traffic.Vehicle> vehicles = new ArrayList<>();

   // $VF: synthetic method
   public static boolean $r8$lambda$7uFahVxZ70iMKwSQ8WCWnpBo4JQ(long var0, Traffic.Vehicle var2) {
      return update$lambda$5(var0, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$lchRO_uxQKoxzFTg2jxu06mRX30(Traffic.Vehicle var0) {
      return update$lambda$4(var0);
   }

   private static final boolean update$lambda$4(Traffic.Vehicle var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.getLead() ^ true;
   }

   private static final boolean update$lambda$5(long var0, Traffic.Vehicle var2) {
      Intrinsics.checkNotNullParameter(var2, "it");
      boolean var3;
      if (var0 - var2.getLastSeen$app_debug() > 600L) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   private final void updateBrake(Traffic.Vehicle var1, float var2, long var3) {
      boolean var8 = Float.isNaN(var1.getLastV$app_debug());
      boolean var7 = true;
      if (!var8 && var3 > var1.getLastVTime$app_debug()) {
         float var5 = (float)(var3 - var1.getLastVTime$app_debug()) / 1000.0F;
         boolean var6;
         if (0.02F <= var5 && var5 <= 1.0F) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var6) {
            var5 = (var2 - var1.getLastV$app_debug()) / var5;
            var1.setAccel$app_debug(var1.getAccel$app_debug() * 0.7F + var5 * 0.3F);
         }
      }

      var1.setLastV$app_debug(var2);
      var1.setLastVTime$app_debug(var3);
      if (!(var1.getAccel$app_debug() < -0.9F) || !(var2 > 0.3F)) {
         var7 = false;
      }

      var1.setBrake(var7);
   }

   public final ArrayList<Traffic.Vehicle> getVehicles() {
      return this.vehicles;
   }

   public final List<Traffic.Vehicle> update(Telemetry var1, long var2, boolean var4, boolean var5) {
      Intrinsics.checkNotNullParameter(var1, "t");
      ArrayList var15 = new ArrayList(4);
      var15.add(var1.getLeadOne());
      var15.add(var1.getLeadTwo());
      if (var5) {
         var15.add(var1.getLeadLeft());
         var15.add(var1.getLeadRight());
      }

      Iterator var16 = var15.iterator();
      int var7 = 0;

      while (true) {
         boolean var13 = var16.hasNext();
         var5 = true;
         boolean var12 = true;
         if (!var13) {
            if (var4 && var1.getRadarFrame() != this.lastRadarFrame) {
               this.lastRadarFrame = var1.getRadarFrame();
               float var6 = var1.getVEgo();

               for (RadarTrack var42 : var1.getRadar()) {
                  if (!(var42.getDRel() <= 0.0F) && !(var42.getDRel() > 110.0) && !(Math.abs(var42.getYRel()) > 9.0)) {
                     Map var44 = this.byId;
                     Long var46 = var42.getId();
                     Traffic.Vehicle var37 = (Traffic.Vehicle)var44.get(var46);
                     if (var37 == null) {
                        var37 = new Traffic.Vehicle(var42.getId());
                        var37.setKind((int)((var42.getId() ^ var42.getId() >>> 7) & 3L));
                        var44.put(var46, var37);
                     }

                     var37 = var37;
                     var37.setX(var42.getDRel() + 2.3);
                     var37.setY(-var42.getYRel());
                     var37.setVRel(var42.getVRel());
                     var37.setVAbs(var42.getVRel() + var6);
                     if (Math.abs(var37.getVAbs()) < 1.2F) {
                        var4 = true;
                     } else {
                        var4 = false;
                     }

                     var37.setStationary(var4);
                     int var9 = var37.getHits$app_debug();
                     var4 = true;
                     var37.setHits$app_debug(var9 + 1);
                     var37.setLastSeen$app_debug(var2);
                     this.updateBrake(var37, var37.getVAbs(), var2);
                  }
               }
            } else if (!var4) {
               Collection var20 = this.byId.values();
               Intrinsics.checkNotNullExpressionValue(var20, "<get-values>(...)");
               CollectionsKt.removeAll(var20, new Traffic$$ExternalSyntheticLambda0());
            }

            Collection var22 = this.byId.values();
            Intrinsics.checkNotNullExpressionValue(var22, "<get-values>(...)");
            CollectionsKt.removeAll(var22, new Traffic$$ExternalSyntheticLambda1(var2));
            this.vehicles.clear();
            Collection var23 = this.byId.values();
            Intrinsics.checkNotNullExpressionValue(var23, "<get-values>(...)");
            Iterable var34 = var23;
            var var24 = new ArrayList();

            for (Object var39 : var34) {
               if (((Traffic.Vehicle)var39).getLead()) {
                  var24.add(var39);
               }
            }

            List var25 = var24;
            Iterator var36 = this.byId.values().iterator();

            while (true) {
               Traffic.Vehicle var45;
               while (true) {
                  if (!var36.hasNext()) {
                     return this.vehicles;
                  }

                  Iterator var40 = (Iterator)var36.next();
                  Intrinsics.checkNotNullExpressionValue(var40, "next(...)");
                  var45 = (Traffic.Vehicle)var40;
                  if (var45.getHits$app_debug() >= 3) {
                     if (var45.getLead()) {
                        break;
                     }

                     boolean var32 = false;
                     var40 = var25.iterator();

                     while (true) {
                        var31 = var32;
                        if (!var40.hasNext()) {
                           break;
                        }

                        Traffic.Vehicle var43 = (Traffic.Vehicle)var40.next();
                        if (Math.hypot(var43.getX() - var45.getX(), var43.getY() - var45.getY()) < 3.0) {
                           var31 = true;
                           break;
                        }
                     }

                     if (!var31 && (!var45.getStationary() || !(Math.abs(var45.getY()) < 1.0) || !(var45.getX() > 60.0))) {
                        break;
                     }
                  }
               }

               this.vehicles.add(var45);
            }
         }

         Lead var17 = (Lead)var16.next();
         long var10 = -100L - var7;
         if (var17.getStatus() && !(var17.getDRel() <= 0.5F)) {
            Map var19 = this.byId;
            Long var18 = var10;
            Traffic.Vehicle var14 = (Traffic.Vehicle)var19.get(var18);
            if (var14 == null) {
               var14 = new Traffic.Vehicle(var10);
               var14.setKind(var7 % 4);
               var14.setLead(true);
               var19.put(var18, var14);
            }

            var14 = var14;
            var14.setX(var17.getDRel() + 2.3);
            var14.setY(-var17.getYRel());
            var14.setVRel(var17.getVRel());
            var14.setVAbs(var17.getVLead());
            if (var17.getVLead() < 0.5F) {
               var5 = var12;
            } else {
               var5 = false;
            }

            var14.setStationary(var5);
            var14.setLastSeen$app_debug(var2);
            var14.setHits$app_debug(3);
            this.updateBrake(var14, var17.getVLead(), var2);
         } else {
            this.byId.remove(var10);
         }

         var7++;
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

   public static final class Vehicle {
      private float accel;
      private boolean brake;
      private int hits;
      private final long id;
      private int kind;
      private long lastSeen;
      private float lastV;
      private long lastVTime;
      private boolean lead;
      private boolean remembered;
      private boolean stationary;
      private float vAbs;
      private float vRel;
      private double x;
      private double y;

      public Vehicle(long var1) {
         this.id = var1;
         this.lastV = Float.NaN;
      }

      public final float getAccel$app_debug() {
         return this.accel;
      }

      public final boolean getBrake() {
         return this.brake;
      }

      public final int getHits$app_debug() {
         return this.hits;
      }

      public final long getId() {
         return this.id;
      }

      public final int getKind() {
         return this.kind;
      }

      public final long getLastSeen$app_debug() {
         return this.lastSeen;
      }

      public final float getLastV$app_debug() {
         return this.lastV;
      }

      public final long getLastVTime$app_debug() {
         return this.lastVTime;
      }

      public final boolean getLead() {
         return this.lead;
      }

      public final boolean getRemembered() {
         return this.remembered;
      }

      public final boolean getStationary() {
         return this.stationary;
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

      public final void setAccel$app_debug(float var1) {
         this.accel = var1;
      }

      public final void setBrake(boolean var1) {
         this.brake = var1;
      }

      public final void setHits$app_debug(int var1) {
         this.hits = var1;
      }

      public final void setKind(int var1) {
         this.kind = var1;
      }

      public final void setLastSeen$app_debug(long var1) {
         this.lastSeen = var1;
      }

      public final void setLastV$app_debug(float var1) {
         this.lastV = var1;
      }

      public final void setLastVTime$app_debug(long var1) {
         this.lastVTime = var1;
      }

      public final void setLead(boolean var1) {
         this.lead = var1;
      }

      public final void setRemembered(boolean var1) {
         this.remembered = var1;
      }

      public final void setStationary(boolean var1) {
         this.stationary = var1;
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
}
