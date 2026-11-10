package com.starpilot.starview;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;

public final class AmySynth {
   private static final double A5 = 880.0;
   private static final double A6 = 1760.0;
   private static final double CS7 = 2217.46;
   private static final double E5 = 659.26;
   private static final double E6 = 1318.51;
   private static final double E7 = 2637.0;
   public static final AmySynth INSTANCE = new AmySynth();
   private static final double[][] PARTIALS;
   public static final int SR = 48000;
   private static final float[] TAB;
   private static final int TN = 4096;
   public static final int VOICE_SR = 24000;

   static {
      float[] var1 = new float[4097];

      for (int var0 = 0; var0 < 4097; var0++) {
         var1[var0] = (float)Math.sin(var0 * (Math.PI * 2) / 4096);
      }

      TAB = var1;
      PARTIALS = new double[][]{{1.0, 1.0, 1.0}, {2.0, 0.28, 1.6}, {3.0, 0.1, 2.4}, {2.76, 0.06, 3.0}, {5.4, 0.025, 4.5}};
   }

   private AmySynth() {
   }

   private final AmySynth.Stereo bells(int var1, List<AmySynth.Bell> var2) {
      float[] var24 = new float[var1];

      for (AmySynth.Bell var25 : var2) {
         double var5 = var25.getStart();
         double var3 = '뮀';
         int var18 = (int)(var5 * var3);

         for (double[] var23 : PARTIALS) {
            var5 = var23[0];
            float var17 = (float)var23[1];
            float var14 = (float)Math.exp(-var25.getDecay() * var23[2] / var3);
            float var15 = 1.0F;
            float var13 = (float)Math.exp(-0.003472222222222222);
            float var16 = 1.0F;

            for (int var21 = var18; var21 < var1 && var15 > 1.0E-5F; var21++) {
               double var7 = (var21 - var18) / var3;
               double var9 = 1;
               double var11 = this.sn(var7 * 4.5);
               var24[var21] += this.sn(var25.getF0() * var5 * var7 * (var9 + var11 * 0.0015)) * var17 * var15 * (1 - var16) * var25.getAmp();
               var15 *= var14;
               var16 *= var13;
            }
         }
      }

      AmySynth.Stereo var27 = new AmySynth.Stereo(var1);
      System.arraycopy(var24, 0, var27.getL(), 0, var1);
      System.arraycopy(var24, 0, var27.getR(), 0, var1);
      this.delayAdd(var27.getL(), var24, 23.0, 0.22F);
      this.delayAdd(var27.getL(), var24, 61.0, 0.12F);
      this.delayAdd(var27.getL(), var24, 113.0, 0.06F);
      this.delayAdd(var27.getR(), var24, 31.0, 0.22F);
      this.delayAdd(var27.getR(), var24, 73.0, 0.12F);
      this.delayAdd(var27.getR(), var24, 127.0, 0.06F);
      float var29 = 1.0E-9F;

      for (int var31 = 0; var31 < var1; var31++) {
         var29 = Math.max(var29, Math.max(Math.abs(var27.getL()[var31]), Math.abs(var27.getR()[var31])));
      }

      var29 = 0.85F / var29;

      for (int var32 = 0; var32 < var1; var32++) {
         float[] var33 = var27.getL();
         var33[var32] *= var29;
         var33 = var27.getR();
         var33[var32] *= var29;
      }

      return var27;
   }

   private final void delayAdd(float[] var1, float[] var2, double var3, float var5) {
      int var7 = (int)('뮀' * var3 / 1000);
      int var6 = var7;

      for (int var8 = var1.length; var6 < var8; var6++) {
         var1[var6] += var2[var6 - var7] * var5;
      }
   }

   private final AmySynth.Stereo pad(int var1, double var2) {
      AmySynth.Stereo var25 = new AmySynth.Stereo(var1);
      Random var22 = new Random(7L);
      double[] var24 = new double[]{220.0, 277.18, 329.63, 493.88, 659.26};
      int var19 = var24.length;

      for (int var17 = 0; var17 < var19; var17++) {
         int var18 = var17;
         double var4 = var24[var17];

         for (Pair var23 : CollectionsKt.listOf(new Pair[]{TuplesKt.to(0, -0.9), TuplesKt.to(1, 0.9)})) {
            int var20 = ((Number)var23.component1()).intValue();
            double var6 = ((Number)var23.component2()).doubleValue();
            double var8 = var22.nextDouble();
            float[] var37;
            if (var20 == 0) {
               var37 = var25.getL();
            } else {
               var37 = var25.getR();
            }

            float var14;
            if (var18 < 3) {
               var14 = 0.9F;
            } else {
               var14 = 0.45F;
            }

            double var10 = var4 + var6;
            var6 = var18 * 0.07 + 0.35;

            for (int var34 = 0; var34 < var1; var34++) {
               double var12 = (double)var34 / '뮀';
               float var16 = 1;
               float var15 = this.sn(var6 * var12 + var8);
               var37[var34] += (this.sn(var10 * var12 + var8) + this.sn(2 * var10 * var12) * 0.18F) * (var16 + var15 * 0.08F) * var14;
            }
         }
      }

      double var31 = '뮀';
      int var33 = RangesKt.coerceAtMost((int)(var2 * var31), var1);

      for (int var32 = 0; var32 < var1; var32++) {
         var2 = Math.min(1.0, var32 / var31 / 0.45);
         double var29 = var2 * var2;
         var2 = var29;
         if (var32 >= var1 - var33) {
            var2 = (double)(var1 - var32) / var33;
            var2 = var29 * (var2 * var2);
         }

         float[] var35 = var25.getL();
         var35[var32] *= (float)var2;
         var35 = var25.getR();
         var35[var32] *= (float)var2;
      }

      return var25;
   }

   private final void pluck(float[] var1, double var2, double var4, float var6) {
      double var7 = '뮀';
      int var14 = (int)(var4 * var7);
      if (var14 < var1.length) {
         float var12 = (float)Math.exp(-6.666666666666667E-5);
         float var10 = 1.0F;
         float var11 = (float)Math.exp(-0.005208333333333333);
         float var9 = 1.0F;

         for (int var13 = var14; var13 < var1.length && var10 > 1.0E-5F; var13++) {
            var1[var13] += this.sn((var13 - var14) * var2 / var7) * var10 * (1 - var9) * var6;
            var10 *= var12;
            var9 *= var11;
         }
      }
   }

   // $VF: synthetic method
   public static short[] render$default(AmySynth var0, AmySynth.Style var1, float[] var2, float var3, float var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = 1.0F;
      }

      if ((var5 & 8) != 0) {
         var4 = 1.0F;
      }

      return var0.render(var1, var2, var3, var4);
   }

   private final float sn(double var1) {
      double var3 = Math.floor(var1);
      var1 = 4096 * (var1 - var3);
      int var5 = (int)var1;
      return TAB[var5] + (TAB[var5 + 1] - TAB[var5]) * (float)(var1 - var5);
   }

   private static final float stretch$corr(int var0, float[] var1, int var2, int var3, int var4) {
      float var5 = 0.0F;
      int var6 = 0;

      while (var6 < var0) {
         var5 += var1[var3 + var6] * var1[var2 + var6];
         var6 += var4;
      }

      return var5;
   }

   // $VF: synthetic method
   public static float[] voice$default(AmySynth var0, List var1, float var2, float var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = 0.0F;
      }

      return var0.voice(var1, var2, var3);
   }

   public final short[] render(AmySynth.Style var1, float[] var2, float var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "style");
      Intrinsics.checkNotNullParameter(var2, "voice");
      double var5;
      double var7;
      boolean var12;
      List var18;
      switch (AmySynth.WhenMappings.$EnumSwitchMapping$0[var1.ordinal()]) {
         case 1:
            var18 = CollectionsKt.listOf(
               new AmySynth.Bell[]{
                  new AmySynth.Bell(880.0, 0.0, 0.55F, 2.2),
                  new AmySynth.Bell(1318.51, 0.11, 0.48F, 2.0),
                  new AmySynth.Bell(1760.0, 0.22, 0.42F, 1.7),
                  new AmySynth.Bell(554.37, 0.22, 0.18F, 1.4)
               }
            );
            var5 = 0.62;
            var7 = 1.6;
            var12 = 1;
            break;
         case 2:
            var18 = CollectionsKt.listOf(new AmySynth.Bell[]{new AmySynth.Bell(1318.51, 0.0, 0.45F, 2.6), new AmySynth.Bell(1760.0, 0.09, 0.38F, 2.4)});
            var5 = 0.3;
            var7 = 0.5;
            var12 = 0;
            break;
         case 3:
            var18 = CollectionsKt.listOf(new AmySynth.Bell(1760.0, 0.0, 0.3F, 3.0));
            var5 = 0.2;
            var7 = 1.3;
            var12 = 0;
            break;
         case 4:
            var18 = CollectionsKt.listOf(
               new AmySynth.Bell[]{
                  new AmySynth.Bell(1318.51, 0.0, 0.45F, 2.4), new AmySynth.Bell(880.0, 0.13, 0.45F, 2.4), new AmySynth.Bell(659.26, 0.26, 0.4F, 2.0)
               }
            );
            var5 = 0.55;
            var7 = 0.6;
            var12 = 0;
            break;
         case 5:
            var18 = CollectionsKt.listOf(new AmySynth.Bell[]{new AmySynth.Bell(1318.51, 0.0, 0.45F, 2.6), new AmySynth.Bell(1760.0, 0.09, 0.38F, 2.4)});
            var5 = 0.0;
            var7 = 0.9;
            var12 = 0;
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      int var14 = var2.length;
      double var9 = '뮀';
      int var15 = (int)(var5 * var9);
      int var13 = var15 + var14 + (int)(var7 * var9);
      float[] var20 = new float[var13];
      float[] var19 = new float[var13];
      if (var12 && var4 > 0.01F) {
         AmySynth.Stereo var21 = this.pad(var13, 1.4);
         int var16 = Math.max(0, var15 - 4800);
         int var17 = Math.min(var13, var15 + var14 + 7200);
         var12 = 0;

         for (var5 = var9; var12 < var13; var12++) {
            float var11 = 1.0F;
            if (var12 >= var16 && var12 < var17) {
               if (var12 < var16 + 5760) {
                  var11 = 1.0F - (var12 - var16) * 0.4F / 5760;
               } else {
                  var11 = 0.6F;
                  if (var12 >= var17 - 5760) {
                     var11 = (var12 - (var17 - 5760)) * 0.4F / 5760 + 0.6F;
                  }
               }
            }

            var20[var12] += var21.getL()[var12] * 0.045F * var4 * var11;
            var19[var12] += var21.getR()[var12] * 0.045F * var4 * var11;
         }
      } else {
         var5 = var9;
      }

      AmySynth.Stereo var50 = this.bells(var13, var18);
      if (var1 == AmySynth.Style.PING) {
         var4 = 0.45F;
      } else {
         var4 = 0.62F;
      }

      var4 *= var3;

      for (int var41 = 0; var41 < var13; var41++) {
         var20[var41] += var50.getL()[var41] * var4;
         var19[var41] += var50.getR()[var41] * var4;
      }

      for (int var42 = 0; var42 < var14; var42++) {
         int var48 = var15 + var42;
         var20[var48] += var2[var42] * 0.95F;
         var48 = var15 + var42;
         var19[var48] += var2[var42] * 0.95F;
      }

      if (var1 == AmySynth.Style.ASK || var1 == AmySynth.Style.DONE) {
         var7 = (var15 + var14) / var5;
         if (var1 == AmySynth.Style.ASK) {
            var5 = 0.15;
         } else {
            var5 = 0.1;
         }

         var5 += var7;
         var2 = new float[var13];
         this.pluck(var2, 1760.0, var5, 1.0F);
         this.pluck(var2, 2217.46, var5 + 0.3, 0.8F);
         this.pluck(var2, 2637.0, var5 + 0.6, 0.6F);
         if (var1 == AmySynth.Style.ASK) {
            var4 = 0.05F;
         } else {
            var4 = 0.09F;
         }

         var3 = var4 * var3;

         for (int var43 = 0; var43 < var13; var43++) {
            var20[var43] += var2[var43] * var3;
            var19[var43] += var2[var43] * var3 * 0.8F;
         }
      }

      var2 = new float[var13];
      float[] var51 = new float[var13];

      for (Pair var22 : CollectionsKt.listOf(
         new Pair[]{TuplesKt.to(29.0, 0.16F), TuplesKt.to(47.0, 0.12F), TuplesKt.to(83.0, 0.08F), TuplesKt.to(131.0, 0.05F), TuplesKt.to(197.0, 0.03F)}
      )) {
         var5 = ((Number)var22.component1()).doubleValue();
         var3 = ((Number)var22.component2()).floatValue();
         this.delayAdd(var2, var20, var5, var3);
         this.delayAdd(var51, var19, var5 * 1.13, var3);
      }

      var14 = Math.min(var13, 14400);
      var3 = 1.0E-9F;

      for (int var44 = 0; var44 < var13; var44++) {
         var20[var44] += var2[var44];
         var19[var44] += var51[var44];
         if (var44 >= var13 - var14) {
            var4 = (float)(var13 - var44) / var14;
            var20[var44] *= var4 * var4;
            var19[var44] *= var4 * var4;
         }

         var3 = Math.max(var3, Math.max(Math.abs(var20[var44]), Math.abs(var19[var44])));
      }

      float var39 = 0.89F / var3;
      var15 = var13 * 2;
      short[] var23 = new short[var15];

      for (int var45 = 0; var45 < var15; var45++) {
         if (var45 % 2 == 0) {
            var4 = var20[var45 / 2];
         } else {
            var4 = var19[var45 / 2];
         }

         var23[var45] = (short)RangesKt.coerceIn((int)(var4 * var39 * 32767.0F), -32768, 32767);
      }

      return var23;
   }

   public final float[] stretch(float[] var1, int var2, float var3) {
      Intrinsics.checkNotNullParameter(var1, "x");
      if (!(Math.abs(var3 - 1.0F) < 0.01F)) {
         boolean var10;
         if (var1.length == 0) {
            var10 = 1;
         } else {
            var10 = 0;
         }

         if (!var10) {
            int var16 = (int)(var2 * 0.03) / 2 * 2;
            int var15 = var16 / 2;
            double var4 = var15;
            double var6 = var3;
            int var13 = (int)(var2 * 0.01);
            int var14 = MathKt.roundToInt(var1.length / var3);
            float[] var21 = new float[var1.length + var13 + var16 * 2 + var13 * 2 + 4];
            System.arraycopy(var1, 0, var21, var13, var1.length);
            float[] var22 = new float[var16];

            for (int var24 = 0; var24 < var16; var24++) {
               var22[var24] = (float)(0.5 - Math.cos(var24 * (Math.PI * 2) / var16) * 0.5);
            }

            float[] var20 = new float[var14 + var16];
            var1 = new float[var14 + var16];
            var10 = var13;
            int var12 = 0;

            while (true) {
               int var17 = var12 * var15;
               if (var17 >= var14) {
                  break;
               }

               var2 = MathKt.roundToInt(var12 * (var4 * var6)) + var13;
               if (var12 > 0) {
                  int var18 = var10 + var15;
                  float var8 = Float.NEGATIVE_INFINITY;
                  var10 = var2;
                  int var11 = Math.max(0, var2 - var13);
                  int var19 = Math.min(var21.length - var16 - 1, var2 + var13);

                  while (var11 <= var19) {
                     float var9 = stretch$corr(var16, var21, var18, var11, 2);
                     var3 = var8;
                     if (var9 > var8) {
                        var3 = var9;
                        var10 = var11;
                     }

                     var11 += 2;
                     var8 = var3;
                  }

                  var8 = Float.NEGATIVE_INFINITY;
                  var11 = var10;
                  var2 = Math.max(0, var10 - 1);
                  var19 = Math.min(var21.length - var16 - 1, var10 + 1);
                  if (var2 <= var19) {
                     var10 = var10;

                     while (true) {
                        float var33 = stretch$corr(var16, var21, var18, var2, 1);
                        var3 = var8;
                        if (var33 > var8) {
                           var3 = var33;
                           var10 = var2;
                        }

                        var11 = var10;
                        if (var2 == var19) {
                           break;
                        }

                        var2++;
                        var8 = var3;
                     }
                  }

                  var2 = var11;
               } else {
                  var2 = var2;
               }

               if (var2 + var16 > var21.length) {
                  break;
               }

               for (int var37 = 0; var37 < var16; var37++) {
                  int var39 = var17 + var37;
                  var20[var39] += var21[var2 + var37] * var22[var37];
                  var39 = var17 + var37;
                  var1[var39] += var22[var37];
               }

               var12++;
               var10 = var2;
            }

            var21 = new float[var14];

            for (int var28 = 0; var28 < var14; var28++) {
               if (var1[var28] > 0.001F) {
                  var3 = var20[var28] / var1[var28];
               } else {
                  var3 = var20[var28];
               }

               var21[var28] = var3;
            }

            return var21;
         }
      }

      return var1;
   }

   public final float[] upsample2(float[] var1) {
      Intrinsics.checkNotNullParameter(var1, "x");
      int var4 = var1.length * 2;
      float[] var6 = new float[var4];

      for (int var3 = 0; var3 < var4; var3++) {
         int var5 = var3 / 2;
         float var2 = var1[var5];
         if (var3 % 2 != 0) {
            var2 = (var1[Math.min(var5 + 1, var1.length - 1)] + var2) * 0.5F;
         }

         var6[var3] = var2;
      }

      return var6;
   }

   public final float[] voice(List<float[]> var1, float var2, float var3) {
      Intrinsics.checkNotNullParameter(var1, "clips");
      short var11 = 5280;
      Iterator var14 = var1.iterator();
      int var9 = 0;

      while (var14.hasNext()) {
         float[] var15 = (float[])var14.next();
         var9 += var15.length;
      }

      float[] var26 = new float[var9 + Math.max(0, var1.size() - 1) * 5280];
      var9 = 0;
      Iterator var27 = var1.iterator();
      int var10 = 0;

      while (var27.hasNext()) {
         int var12 = var10 + 1;
         float[] var16 = (float[])var27.next();
         var10 = var9;
         if (var10 > 0) {
            var10 = var9 + 5280;
         }

         System.arraycopy(var16, 0, var26, var10, var16.length);
         var9 = var10 + var16.length;
         var10 = var12;
      }

      float var8 = RangesKt.coerceIn(var2, 0.5F, 2.0F);
      if (Math.abs(var3) > 0.05F) {
         boolean var22;
         if (var26.length == 0) {
            var22 = true;
         } else {
            var22 = false;
         }

         if (!var22) {
            double var4 = Math.pow(2.0, RangesKt.coerceIn(var3, -12.0F, 12.0F) / 12.0);
            int var24 = (int)(var26.length / var4);
            float[] var28 = new float[var24];

            for (int var23 = 0; var23 < var24; var23++) {
               double var6 = var23 * var4;
               int var25 = (int)var6;
               var2 = (float)(var6 - var25);
               if (var25 + 1 < var26.length) {
                  var2 = var26[var25] + (var26[var25 + 1] - var26[var25]) * var2;
               } else {
                  var2 = var26[Math.min(var25, var26.length - 1)];
               }

               var28[var23] = var2;
            }

            var2 = (float)(var8 / var4);
            return this.upsample2(this.stretch(var28, 24000, var2));
         }
      }

      return this.upsample2(this.stretch(var26, 24000, var8));
   }

   public final float[] wavFloats(byte[] var1) {
      Intrinsics.checkNotNullParameter(var1, "bytes");
      int var2 = 12;

      while (true) {
         int var4 = var1.length;
         int var3 = 0;
         if (var2 + 8 > var4) {
            return new float[0];
         }

         String var6 = new String(var1, var2, 4, Charsets.US_ASCII);
         var4 = var1[var2 + 4] & 255 | (var1[var2 + 5] & 255) << 8 | (var1[var2 + 6] & 255) << 16 | (var1[var2 + 7] & 255) << 24;
         if (Intrinsics.areEqual(var6, "data")) {
            var4 = Math.min(var4, var1.length - var2 - 8) / 2;

            for (var9 = new float[var4]; var3 < var4; var3++) {
               int var5 = var2 + 8 + var3 * 2;
               var9[var3] = (short)(var1[var5] & 255 | var1[var5 + 1] << 8) / 32768.0F;
            }

            return var9;
         }

         var2 += var4 + 8 + (var4 & 1);
      }
   }

   private static final class Bell {
      private final float amp;
      private final double decay;
      private final double f0;
      private final double start;

      public Bell(double var1, double var3, float var5, double var6) {
         this.f0 = var1;
         this.start = var3;
         this.amp = var5;
         this.decay = var6;
      }

      // $VF: synthetic method
      public static AmySynth.Bell copy$default(AmySynth.Bell var0, double var1, double var3, float var5, double var6, int var8, Object var9) {
         if ((var8 & 1) != 0) {
            var1 = var0.f0;
         }

         if ((var8 & 2) != 0) {
            var3 = var0.start;
         }

         if ((var8 & 4) != 0) {
            var5 = var0.amp;
         }

         if ((var8 & 8) != 0) {
            var6 = var0.decay;
         }

         return var0.copy(var1, var3, var5, var6);
      }

      public final double component1() {
         return this.f0;
      }

      public final double component2() {
         return this.start;
      }

      public final float component3() {
         return this.amp;
      }

      public final double component4() {
         return this.decay;
      }

      public final AmySynth.Bell copy(double var1, double var3, float var5, double var6) {
         return new AmySynth.Bell(var1, var3, var5, var6);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof AmySynth.Bell)) {
            return false;
         } else {
            var1 = var1;
            if (Double.compare(this.f0, var1.f0) != 0) {
               return false;
            } else if (Double.compare(this.start, var1.start) != 0) {
               return false;
            } else {
               return Float.compare(this.amp, var1.amp) != 0 ? false : Double.compare(this.decay, var1.decay) == 0;
            }
         }
      }

      public final float getAmp() {
         return this.amp;
      }

      public final double getDecay() {
         return this.decay;
      }

      public final double getF0() {
         return this.f0;
      }

      public final double getStart() {
         return this.start;
      }

      @Override
      public int hashCode() {
         return ((Double.hashCode(this.f0) * 31 + Double.hashCode(this.start)) * 31 + Float.hashCode(this.amp)) * 31 + Double.hashCode(this.decay);
      }

      @Override
      public String toString() {
         double var1 = this.f0;
         double var5 = this.start;
         float var7 = this.amp;
         double var3 = this.decay;
         return "Bell(f0=" + var1 + ", start=" + var5 + ", amp=" + var7 + ", decay=" + var3 + ")";
      }
   }

   private static final class Stereo {
      private final float[] l;
      private final float[] r;

      public Stereo(int var1) {
         this.l = new float[var1];
         this.r = new float[var1];
      }

      public final float[] getL() {
         return this.l;
      }

      public final float[] getR() {
         return this.r;
      }
   }

   public static enum Style {
      ASK,
      DONE,
      PING,
      SHORT,
      STOPPED;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(AmySynth.Style.$VALUES);
      private static final AmySynth.Style[] $VALUES = $values();

      // $VF: synthetic method
      private static final AmySynth.Style[] $values() {
         return new AmySynth.Style[]{ASK, SHORT, DONE, STOPPED, PING};
      }

      public static EnumEntries<AmySynth.Style> getEntries() {
         return $ENTRIES;
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[AmySynth.Style.values().length];

         try {
            var0[AmySynth.Style.ASK.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[AmySynth.Style.SHORT.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[AmySynth.Style.DONE.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[AmySynth.Style.STOPPED.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[AmySynth.Style.PING.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
