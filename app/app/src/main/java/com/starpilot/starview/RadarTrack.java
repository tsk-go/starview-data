package com.starpilot.starview;


public final class RadarTrack {
   private final float dRel;
   private final long id;
   private final boolean measured;
   private final float vRel;
   private final float yRel;

   public RadarTrack(long var1, float var3, float var4, float var5, boolean var6) {
      this.id = var1;
      this.dRel = var3;
      this.yRel = var4;
      this.vRel = var5;
      this.measured = var6;
   }

   public final float getDRel() {
      return this.dRel;
   }

   public final long getId() {
      return this.id;
   }

   public final boolean getMeasured() {
      return this.measured;
   }

   public final float getVRel() {
      return this.vRel;
   }

   public final float getYRel() {
      return this.yRel;
   }
}
