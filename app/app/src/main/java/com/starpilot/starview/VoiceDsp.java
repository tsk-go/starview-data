package com.starpilot.starview;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;

public final class VoiceDsp {
   public static final VoiceDsp INSTANCE = new VoiceDsp();

   private VoiceDsp() {
   }

   private final float[] biquad(float[] var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      float[] var28 = new float[var1.length];
      double var16 = 0.0;
      double var18 = 0.0;
      double var14 = 0.0;
      double var20 = 0.0;
      int var26 = 0;

      for (int var27 = var1.length; var26 < var27; var26++) {
         double var22 = var1[var26];
         double var24 = (var2 * var22 + var4 * var16 + var6 * var18 - var10 * var14 - var12 * var20) / var8;
         var28[var26] = (float)var24;
         var18 = var16;
         var16 = var22;
         var20 = var14;
         var14 = var24;
      }

      return var28;
   }

   // $VF: synthetic method
   public static float[] highpass$default(VoiceDsp var0, float[] var1, int var2, double var3, double var5, int var7, Object var8) {
      if ((var7 & 4) != 0) {
         var3 = 90.0;
      }

      if ((var7 & 8) != 0) {
         var5 = 0.7071;
      }

      return var0.highpass(var1, var2, var3, var5);
   }

   // $VF: synthetic method
   public static float[] peak$default(VoiceDsp var0, float[] var1, int var2, double var3, double var5, double var7, int var9, Object var10) {
      if ((var9 & 4) != 0) {
         var3 = 3000.0;
      }

      if ((var9 & 8) != 0) {
         var5 = 1.0;
      }

      if ((var9 & 16) != 0) {
         var7 = 2.0;
      }

      return var0.peak(var1, var2, var3, var5, var7);
   }

   // $VF: synthetic method
   public static float[] trim$default(VoiceDsp var0, float[] var1, int var2, double var3, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = -45.0;
      }

      return var0.trim(var1, var2, var3);
   }

   private static final boolean trim$loud(float[] var0, int var1, double var2, int var4) {
      double var5 = 0.0;
      int var8 = 0;

      for (int var7 = var4; var7 < Math.min(var0.length, var4 + var1); var7++) {
         var5 += var0[var7] * var0[var7];
         var8++;
      }

      boolean var9;
      if (var8 > 0 && Math.sqrt(var5 / var8) > var2) {
         var9 = true;
      } else {
         var9 = false;
      }

      return var9;
   }

   public final float[] clean(float[] var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "x");
      return this.resample(
         peak$default(this, highpass$default(this, trim$default(this, var1, var2, 0.0, 4, null), var2, 0.0, 0.0, 12, null), var2, 0.0, 0.0, 0.0, 28, null),
         var2,
         24000
      );
   }

   public final float[] highpass(float[] var1, int var2, double var3, double var5) {
      Intrinsics.checkNotNullParameter(var1, "x");
      double var7 = (Math.PI * 2) * var3 / var2;
      var3 = Math.cos(var7);
      double var9 = Math.sin(var7);
      var7 = 2;
      var9 /= var7 * var5;
      var5 = 1;
      return this.biquad(var1, (var5 + var3) / var7, -(var5 + var3), (var5 + var3) / var7, var5 + var9, -2 * var3, var5 - var9);
   }

   public final float[] peak(float[] var1, int var2, double var3, double var5, double var7) {
      Intrinsics.checkNotNullParameter(var1, "x");
      var7 = Math.pow(10.0, var7 / 40);
      double var9 = (Math.PI * 2) * var3 / var2;
      var3 = Math.cos(var9);
      var9 = Math.sin(var9) / (2 * var5);
      var5 = 1;
      double var11 = -2;
      return this.biquad(var1, var5 + var9 * var7, var11 * var3, var5 - var9 * var7, var9 / var7 + var5, var11 * var3, var5 - var9 / var7);
   }

   public final float[] resample(float[] var1, int var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "x");
      if (var2 == var3) {
         return var1;
      } else {
         int var16 = (int)((long)var1.length * var3 / var2);
         float[] var22 = new float[var16];
         double var10 = Math.min(1.0, (double)var3 / var2) * 0.95;
         double var12 = 16 / var10;
         int var18 = 0;

         while (var18 < var16) {
            double var4 = (double)var18 * var2 / var3;
            int var17 = (int)Math.ceil(var4 - var12);
            int var19 = (int)Math.floor(var4 + var12);
            double var6 = 0.0;
            int var20 = Math.max(0, var17);
            int var21 = Math.min(var1.length - 1, var19);
            double var8;
            if (var20 <= var21) {
               while (true) {
                  double var14 = var4 - var20;
                  var8 = var14 * var10;
                  if (Math.abs(var8) < 1.0E-9) {
                     var8 = 1.0;
                  } else {
                     var8 = Math.sin(var8 * Math.PI) / (var8 * Math.PI);
                  }

                  var6 += var1[var20] * var10 * var8 * (Math.cos(Math.PI * var14 / var12) * 0.5 + 0.5);
                  var8 = var6;
                  var17 = var16;
                  if (var20 == var21) {
                     break;
                  }

                  var20++;
               }
            } else {
               var17 = var16;
               var8 = var6;
            }

            var22[var18] = (float)var8;
            var18++;
            var16 = var17;
         }

         return var22;
      }
   }

   public final float[] trim(float[] var1, int var2, double var3) {
      Intrinsics.checkNotNullParameter(var1, "x");
      var3 = Math.pow(10.0, var3 / 20);
      int var7 = Math.max(1, var2 / 50);
      int var5 = 0;

      while (var5 < var1.length && !trim$loud(var1, var7, var3, var5)) {
         var5 += var7 / 2;
      }

      int var6 = var1.length - var7;

      while (var6 > var5 && !trim$loud(var1, var7, var3, Math.max(0, var6))) {
         var6 -= var7 / 2;
      }

      var2 /= 100;
      var5 = Math.max(0, var5 - var2);
      var2 = Math.min(var1.length, var6 + var7 + var2);
      if (var2 > var5) {
         var1 = ArraysKt.copyOfRange(var1, var5, var2);
      }

      return var1;
   }

   public final byte[] wav16(float[] var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "x");
      ByteBuffer var4 = ByteBuffer.allocate(var1.length * 2 + 44).order(ByteOrder.LITTLE_ENDIAN);
      byte[] var5 = "RIFF".getBytes(Charsets.UTF_8);
      Intrinsics.checkNotNullExpressionValue(var5, "getBytes(...)");
      ByteBuffer var6 = var4.put(var5).putInt(var1.length * 2 + 36);
      var5 = "WAVEfmt ".getBytes(Charsets.UTF_8);
      Intrinsics.checkNotNullExpressionValue(var5, "getBytes(...)");
      var6.put(var5).putInt(16).putShort((short)1).putShort((short)1).putInt(var2).putInt(var2 * 2).putShort((short)2).putShort((short)16);
      var5 = "data".getBytes(Charsets.UTF_8);
      Intrinsics.checkNotNullExpressionValue(var5, "getBytes(...)");
      var4.put(var5).putInt(var1.length * 2);
      int var3 = var1.length;

      for (int var8 = 0; var8 < var3; var8++) {
         var4.putShort((short)RangesKt.coerceIn((int)(32767.0F * var1[var8]), -32768, 32767));
      }

      byte[] var7 = var4.array();
      Intrinsics.checkNotNullExpressionValue(var7, "array(...)");
      return var7;
   }
}
