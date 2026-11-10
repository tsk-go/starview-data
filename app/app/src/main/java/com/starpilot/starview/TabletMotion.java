package com.starpilot.starview;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class TabletMotion implements SensorEventListener {
   public static final TabletMotion.Companion Companion = new TabletMotion.Companion(null);
   public static final float THRESHOLD = 3.2F;
   private volatile float aEgo;
   private final float[] fwd;
   private float fwdW;
   private final float[] g;
   private boolean gInit;
   private float hp;
   private final ConcurrentLinkedQueue<TabletMotion.Jolt> jolts;
   private float[] lastGyro;
   private long lastJoltMs;
   private boolean on;
   private long peakAtMs;
   private float peakPitch;
   private float peakRoll;
   private float peakV;
   private float prevV;
   private final SensorManager sm;
   private volatile float speed;
   private volatile float yawRate;

   public TabletMotion(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      Object var2 = var1.getSystemService("sensor");
      Intrinsics.checkNotNull(var2, "null cannot be cast to non-null type android.hardware.SensorManager");
      this.sm = (SensorManager)var2;
      this.g = new float[3];
      this.fwd = new float[3];
      this.lastGyro = new float[3];
      this.jolts = new ConcurrentLinkedQueue<>();
   }

   public final float getAEgo() {
      return this.aEgo;
   }

   public final boolean getOriented() {
      boolean var1;
      if (this.fwdW > 20.0F) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final float getSpeed() {
      return this.speed;
   }

   public final float getYawRate() {
      return this.yawRate;
   }

   public void onAccuracyChanged(Sensor var1, int var2) {
   }

   public void onSensorChanged(SensorEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      if (var1.sensor.getType() == 4) {
         float[] var25 = var1.values;
         Intrinsics.checkNotNullExpressionValue(var25, "values");
         float[] var26 = Arrays.copyOf(var25, var25.length);
         Intrinsics.checkNotNullExpressionValue(var26, "copyOf(...)");
         this.lastGyro = var26;
      } else {
         float[] var19 = var1.values;
         boolean var18 = this.gInit;
         byte var15 = 1;
         if (!var18) {
            Intrinsics.checkNotNull(var19);
            ArraysKt.copyInto$default(var19, this.g, 0, 0, 0, 14, null);
            this.gInit = true;
         } else {
            for (int var14 = 0; var14 < 3; var14++) {
               float[] var21 = this.g;
               var21[var14] += (var19[var14] - this.g[var14]) * 0.01F;
            }

            float var3 = (float)Math.sqrt(this.g[0] * this.g[0] + this.g[1] * this.g[1] + this.g[2] * this.g[2]);
            float var2 = 1.0F;
            float var4 = RangesKt.coerceAtLeast(var3, 1.0F);
            float[] var22 = new float[3];

            for (int var34 = 0; var34 < 3; var34++) {
               var22[var34] = this.g[var34] / var4;
            }

            var3 = var19[0] * var22[0] + var19[1] * var22[1] + var19[2] * var22[2] - var4;
            if (Math.abs(this.aEgo) > 0.8F && Math.abs(this.yawRate) < 0.05F && this.speed > 3.0F) {
               float[] var20 = new float[3];

               for (int var35 = 0; var35 < 3; var35++) {
                  var20[var35] = var19[var35] - (var3 + var4) * var22[var35];
               }

               if (!(this.aEgo > 0.0F)) {
                  var2 = -1.0F;
               }

               for (int var36 = 0; var36 < 3; var36++) {
                  var19 = this.fwd;
                  var19[var36] += var20[var36] * var2 * 0.02F;
               }

               this.fwdW = RangesKt.coerceAtMost(this.fwdW + Math.abs(this.aEgo) * 0.02F, 1000.0F);
            }

            this.hp = (this.hp + var3 - this.prevV) * 0.8F;
            this.prevV = var3;
            long var16 = System.currentTimeMillis();
            if (!(this.speed < 2.5F) && var16 - this.lastJoltMs >= 1200L) {
               var3 = 0.0F;
               var2 = 0.0F;
               if (this.getOriented()) {
                  var2 = RangesKt.coerceAtLeast((float)Math.sqrt(this.fwd[0] * this.fwd[0] + this.fwd[1] * this.fwd[1] + this.fwd[2] * this.fwd[2]), 0.001F);
                  var19 = new float[3];

                  for (int var37 = 0; var37 < 3; var37++) {
                     var19[var37] = this.fwd[var37] / var2;
                  }

                  var3 = var22[1];
                  float var13 = var19[2];
                  var2 = var22[2];
                  var4 = var19[1];
                  float var12 = var22[2];
                  float var11 = var19[0];
                  float var6 = var22[0];
                  float var10 = var19[2];
                  float var8 = var22[0];
                  float var7 = var19[1];
                  float var9 = var22[1];
                  float var5 = var19[0];
                  float[] var23 = new float[]{var3 * var13 - var2 * var4, var12 * var11 - var6 * var10, var8 * var7 - var9 * var5};
                  var2 = this.lastGyro[0] * var19[0] + this.lastGyro[1] * var19[1] + this.lastGyro[2] * var19[2];
                  var3 = this.lastGyro[0] * var23[0] + this.lastGyro[1] * var23[1] + this.lastGyro[2] * var23[2];
               }

               if (Math.abs(this.hp) > Math.abs(this.peakV)) {
                  this.peakV = this.hp;
                  this.peakAtMs = var16;
               }

               if (Math.abs(var3) > Math.abs(this.peakPitch)) {
                  this.peakPitch = var3;
               }

               if (Math.abs(var2) > Math.abs(this.peakRoll)) {
                  this.peakRoll = var2;
               }

               if (var16 - this.peakAtMs > 250L) {
                  if (Math.abs(this.peakV) > 3.2F) {
                     String var24;
                     if (!this.getOriented()) {
                        var24 = "rough";
                     } else if (Math.abs(this.peakRoll) > Math.abs(this.peakPitch) * 1.3F) {
                        var24 = "pothole";
                     } else {
                        var24 = "bump";
                     }

                     byte var38;
                     if (Intrinsics.areEqual(var24, "pothole")) {
                        if (this.peakRoll > 0.0F) {
                           var38 = var15;
                        } else {
                           var38 = -1;
                        }
                     } else {
                        var38 = 0;
                     }

                     this.jolts.add(new TabletMotion.Jolt(var24, var38, Math.abs(this.peakV), this.peakAtMs));
                     this.lastJoltMs = var16;
                  }

                  this.peakV = 0.0F;
                  this.peakPitch = 0.0F;
                  this.peakRoll = 0.0F;
                  this.peakAtMs = var16;
               }
            }
         }
      }
   }

   public final TabletMotion.Jolt poll() {
      return this.jolts.poll();
   }

   public final void setAEgo(float var1) {
      this.aEgo = var1;
   }

   public final void setSpeed(float var1) {
      this.speed = var1;
   }

   public final void setYawRate(float var1) {
      this.yawRate = var1;
   }

   public final void start() {
      if (!this.on) {
         this.on = true;
         Sensor var1 = this.sm.getDefaultSensor(1);
         if (var1 != null) {
            this.sm.registerListener(this, var1, 1);
         }

         var1 = this.sm.getDefaultSensor(4);
         if (var1 != null) {
            this.sm.registerListener(this, var1, 1);
         }
      }
   }

   public final void stop() {
      if (this.on) {
         this.on = false;
         this.sm.unregisterListener(this);
         this.jolts.clear();
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

   public static final class Jolt {
      private final long atMs;
      private final String kind;
      private final int side;
      private final float size;

      public Jolt(String var1, int var2, float var3, long var4) {
         Intrinsics.checkNotNullParameter(var1, "kind");
         super();
         this.kind = var1;
         this.side = var2;
         this.size = var3;
         this.atMs = var4;
      }

      public final long getAtMs() {
         return this.atMs;
      }

      public final String getKind() {
         return this.kind;
      }

      public final int getSide() {
         return this.side;
      }

      public final float getSize() {
         return this.size;
      }
   }
}
