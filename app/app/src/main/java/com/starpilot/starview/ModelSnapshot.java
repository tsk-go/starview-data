package com.starpilot.starview;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ModelSnapshot {
   private final float[] accelX;
   private final float[] desirePred;
   private final float[] edgeStd;
   private final float[][] edgeX;
   private final float[][] edgeY;
   private final float[][] edgeZ;
   private final long frameId;
   private final int laneChangeDir;
   private final boolean laneChangeFinishing;
   private final boolean laneChangeOff;
   private final float laneChangeProb;
   private final boolean laneChanging;
   private final float[] laneProb;
   private final float[] laneStd;
   private final float[][] laneX;
   private final float[][] laneY;
   private final float[][] laneZ;
   private final float modelDesiredCurvature;
   private final float[] posX;
   private final float[] posY;
   private final float[] posYStd;
   private final float[] posZ;

   public ModelSnapshot(
      long var1,
      float[] var3,
      float[] var4,
      float[] var5,
      float[] var6,
      float[][] var7,
      float[][] var8,
      float[][] var9,
      float[] var10,
      float[][] var11,
      float[][] var12,
      float[][] var13,
      float[] var14,
      float[] var15,
      float[] var16,
      boolean var17,
      float var18,
      boolean var19,
      int var20,
      boolean var21,
      float[] var22,
      float var23
   ) {
      Intrinsics.checkNotNullParameter(var3, "posX");
      Intrinsics.checkNotNullParameter(var4, "posY");
      Intrinsics.checkNotNullParameter(var5, "posZ");
      Intrinsics.checkNotNullParameter(var6, "accelX");
      Intrinsics.checkNotNullParameter(var7, "laneX");
      Intrinsics.checkNotNullParameter(var8, "laneY");
      Intrinsics.checkNotNullParameter(var9, "laneZ");
      Intrinsics.checkNotNullParameter(var10, "laneProb");
      Intrinsics.checkNotNullParameter(var11, "edgeX");
      Intrinsics.checkNotNullParameter(var12, "edgeY");
      Intrinsics.checkNotNullParameter(var13, "edgeZ");
      Intrinsics.checkNotNullParameter(var14, "edgeStd");
      Intrinsics.checkNotNullParameter(var15, "laneStd");
      Intrinsics.checkNotNullParameter(var16, "posYStd");
      Intrinsics.checkNotNullParameter(var22, "desirePred");
      super();
      this.frameId = var1;
      this.posX = var3;
      this.posY = var4;
      this.posZ = var5;
      this.accelX = var6;
      this.laneX = var7;
      this.laneY = var8;
      this.laneZ = var9;
      this.laneProb = var10;
      this.edgeX = var11;
      this.edgeY = var12;
      this.edgeZ = var13;
      this.edgeStd = var14;
      this.laneStd = var15;
      this.posYStd = var16;
      this.laneChangeOff = var17;
      this.modelDesiredCurvature = var18;
      this.laneChanging = var19;
      this.laneChangeDir = var20;
      this.laneChangeFinishing = var21;
      this.desirePred = var22;
      this.laneChangeProb = var23;
   }

   // $VF: synthetic method
   public ModelSnapshot(
      long var1,
      float[] var3,
      float[] var4,
      float[] var5,
      float[] var6,
      float[][] var7,
      float[][] var8,
      float[][] var9,
      float[] var10,
      float[][] var11,
      float[][] var12,
      float[][] var13,
      float[] var14,
      float[] var15,
      float[] var16,
      boolean var17,
      float var18,
      boolean var19,
      int var20,
      boolean var21,
      float[] var22,
      float var23,
      int var24,
      DefaultConstructorMarker var25
   ) {
      if ((var24 & 8192) != 0) {
         var15 = new float[0];
      }

      if ((var24 & 16384) != 0) {
         var16 = new float[0];
      }

      if ((32768 & var24) != 0) {
         var17 = true;
      }

      if ((65536 & var24) != 0) {
         var18 = 0.0F;
      }

      if ((131072 & var24) != 0) {
         var19 = false;
      }

      if ((262144 & var24) != 0) {
         var20 = 0;
      }

      if ((524288 & var24) != 0) {
         var21 = false;
      }

      if ((1048576 & var24) != 0) {
         var22 = new float[0];
      }

      if ((var24 & 2097152) != 0) {
         var23 = Float.NaN;
      }

      this(var1, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21, var22, var23);
   }

   public final float[] getAccelX() {
      return this.accelX;
   }

   public final float[] getDesirePred() {
      return this.desirePred;
   }

   public final float[] getEdgeStd() {
      return this.edgeStd;
   }

   public final float[][] getEdgeX() {
      return this.edgeX;
   }

   public final float[][] getEdgeY() {
      return this.edgeY;
   }

   public final float[][] getEdgeZ() {
      return this.edgeZ;
   }

   public final long getFrameId() {
      return this.frameId;
   }

   public final int getLaneChangeDir() {
      return this.laneChangeDir;
   }

   public final boolean getLaneChangeFinishing() {
      return this.laneChangeFinishing;
   }

   public final boolean getLaneChangeOff() {
      return this.laneChangeOff;
   }

   public final float getLaneChangeProb() {
      return this.laneChangeProb;
   }

   public final boolean getLaneChanging() {
      return this.laneChanging;
   }

   public final float[] getLaneProb() {
      return this.laneProb;
   }

   public final float[] getLaneStd() {
      return this.laneStd;
   }

   public final float[][] getLaneX() {
      return this.laneX;
   }

   public final float[][] getLaneY() {
      return this.laneY;
   }

   public final float[][] getLaneZ() {
      return this.laneZ;
   }

   public final float getModelDesiredCurvature() {
      return this.modelDesiredCurvature;
   }

   public final float[] getPosX() {
      return this.posX;
   }

   public final float[] getPosY() {
      return this.posY;
   }

   public final float[] getPosYStd() {
      return this.posYStd;
   }

   public final float[] getPosZ() {
      return this.posZ;
   }
}
