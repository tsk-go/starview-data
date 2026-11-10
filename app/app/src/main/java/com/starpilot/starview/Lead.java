package com.starpilot.starview;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class Lead {
   private final float dRel;
   private final float modelProb;
   private final boolean radar;
   private final boolean status;
   private final float vLead;
   private final float vRel;
   private final float yRel;

   public Lead(boolean var1, float var2, float var3, float var4, float var5, float var6, boolean var7) {
      this.status = var1;
      this.dRel = var2;
      this.yRel = var3;
      this.vRel = var4;
      this.vLead = var5;
      this.modelProb = var6;
      this.radar = var7;
   }

   // $VF: synthetic method
   public Lead(boolean var1, float var2, float var3, float var4, float var5, float var6, boolean var7, int var8, DefaultConstructorMarker var9) {
      if ((var8 & 64) != 0) {
         var7 = false;
      }

      this(var1, var2, var3, var4, var5, var6, var7);
   }

   public final float getDRel() {
      return this.dRel;
   }

   public final float getModelProb() {
      return this.modelProb;
   }

   public final boolean getRadar() {
      return this.radar;
   }

   public final boolean getStatus() {
      return this.status;
   }

   public final float getVLead() {
      return this.vLead;
   }

   public final float getVRel() {
      return this.vRel;
   }

   public final float getYRel() {
      return this.yRel;
   }
}
