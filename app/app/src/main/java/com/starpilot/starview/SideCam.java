package com.starpilot.starview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.media.Image;
import android.media.Image.Plane;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class SideCam {
   public static final SideCam INSTANCE = new SideCam();
   private static final long KEEP_MS = 5000L;
   private static final long MIN_FRAME_MS = 80L;
   public static final int OUT = 240;
   private static VideoClient client;
   private static final int[] gamma;
   private static volatile long imageAtMs;
   private static final ConcurrentHashMap<String, Bitmap> images = new ConcurrentHashMap<>();
   private static volatile boolean invert;
   private static volatile long lastFrameMs;
   private static long lastWantMs;
   private static volatile SideCam.Mask mask;
   private static SideCam.Mask maskParsed;
   private static String maskRaw = "\u0000";
   private static Function0<Unit> onFrame;
   private static volatile Set<String> sides = SetsKt.emptySet();
   private static volatile String status = "";

   // $VF: synthetic method
   public static Unit $r8$lambda$BTRIbZ6tnQzgWajs4_uS48Ptva4/* $VF was: $r8$lambda$BTRIbZ6tnQzgWajs4_uS48Ptva4*/(int var0, int var1) {
      return tick$lambda$1(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$HHuDnZ1mffHYuXbJUFzr3vTn_Og/* $VF was: $r8$lambda$HHuDnZ1mffHYuXbJUFzr3vTn_Og*/(VideoClient var0) {
      close$lambda$6$lambda$5(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cN6HsfkK0tXY5CILZFLXSvRY4AQ(Image var0) {
      return tick$lambda$3$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$f2JQZxA5bVuMYWUpNfRTeoC2WxQ(String var0) {
      return tick$lambda$0(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$fpU8DT8F9ONmCJzw36nYPma_5eI(VideoClient var0) {
      tick$lambda$4(var0);
   }

   static {
      int[] var1 = new int[256];

      for (int var0 = 0; var0 < 256; var0++) {
         var1[var0] = RangesKt.coerceIn(MathKt.roundToInt(Math.pow(var0 / 255.0, 0.78125) * 255.0), 0, 255);
      }

      gamma = var1;
   }

   private SideCam() {
   }

   private static final void close$lambda$6$lambda$5(VideoClient var0) {
      var0.close();
   }

   private final void frame(Image var1) {
      long var27 = System.currentTimeMillis();
      if (var27 - lastFrameMs >= 80L) {
         SideCam.Mask var30 = mask;
         if (var30 != null) {
            Set var29 = sides;
            if (!var29.isEmpty()) {
               lastFrameMs = var27;
               int var15 = var1.getWidth();
               int var14 = var1.getHeight();
               Plane[] var33 = var1.getPlanes();
               Plane var32 = var33[0];
               Plane var31 = var33[1];
               Plane var35 = var33[2];
               ByteBuffer var39 = var32.getBuffer();
               ByteBuffer var37 = var31.getBuffer();
               ByteBuffer var34 = var35.getBuffer();
               int var21 = var32.getRowStride();
               int var17 = var32.getPixelStride();
               int var22 = var31.getRowStride();
               int var18 = var31.getPixelStride();
               int var20 = var35.getRowStride();
               int var19 = var35.getPixelStride();

               for (String var57 : var29) {
                  float[] var40 = var30.crop(var57, var15, var14);
                  if (var40 != null) {
                     int[] var36 = new int[57600];

                     for (int var13 = 0; var13 < 240; var13++) {
                        float var3 = var13;
                        float var2 = 240;
                        var3 = (var3 + 0.5F) / var2 * 2.0F - 1.0F;

                        for (int var16 = 0; var16 < 240; var16++) {
                           float var8 = (var16 + 0.5F) / var2 * 2.0F - 1.0F;
                           float var6 = (float)Math.sqrt(var8 * var8 + var3 * var3);
                           if (var6 > 1.01F) {
                              var36[var13 * 240 + var16] = 0;
                           } else {
                              float var7 = (float)Math.sqrt(Math.max(0.0F, 1.0F - var6 * var6));
                              float var5 = RangesKt.coerceIn((var16 + 0.5F) / var2 + var8 * 0.016F * (1.0F - var7), 0.001F, 0.999F);
                              float var9 = RangesKt.coerceIn((var13 + 0.5F) / var2 + 0.016F * var3 * (1.0F - var7), 0.001F, 0.999F);
                              float var4 = var5;
                              if (invert) {
                                 var4 = 1.0F - var5;
                              }

                              int var26 = RangesKt.coerceIn((int)(var40[0] + var40[2] * var4), 0, var15 - 1);
                              int var25 = RangesKt.coerceIn((int)(var40[1] + var40[2] * var9), 0, var14 - 1);
                              byte var24 = var39.get(var25 * var21 + var26 * var17);
                              int var23 = (var37.get(var25 / 2 * var22 + var26 / 2 * var18) & 255) - 128;
                              var25 = (var34.get(var25 / 2 * var20 + var26 / 2 * var19) & 255) - 128;
                              float var11 = ((var24 & 255) - 16) * 1.164F;
                              var5 = var25;
                              var9 = var23;
                              float var12 = var25;
                              float var10 = var23;
                              var4 = 1.0F - 0.15F * this.smooth(0.22F, 0.98F, var6);
                              var5 = gamma[RangesKt.coerceIn((int)(var5 * 1.596F + var11), 0, 255)];
                              var9 = gamma[RangesKt.coerceIn((int)(var11 - var9 * 0.392F - var12 * 0.813F), 0, 255)];
                              var10 = gamma[RangesKt.coerceIn((int)(var10 * 2.017F + var11), 0, 255)];
                              var11 = 0.28F + var8;
                              var8 = 0.34F + var3;
                              var7 = (1.0F - this.smooth(0.0F, 0.22F, var11 * var11 + var8 * var8)) * var7 * 0.12F * 255.0F;
                              var5 = var5 * var4 + var7;
                              var8 = var9 * var4 + var7;
                              var7 = var10 * var4 + var7;
                              var4 = this.smooth(0.6F, 0.99F, var6) * 0.1F;
                              var36[var13 * 240 + var16] = Color.argb(
                                 RangesKt.coerceIn(MathKt.roundToInt((1.0F - this.smooth(0.985F, 1.01F, var6)) * (1.0F - 1.2F * var4) * 255.0F), 0, 255),
                                 RangesKt.coerceIn(MathKt.roundToInt(var5 + (122.399994F - var5) * var4), 0, 255),
                                 RangesKt.coerceIn(MathKt.roundToInt(var8 + (178.5F - var8) * var4), 0, 255),
                                 RangesKt.coerceIn(MathKt.roundToInt(var7 + (255.0F - var7) * var4), 0, 255)
                              );
                           }
                        }
                     }

                     images.put(var57, Bitmap.createBitmap(var36, 240, 240, Config.ARGB_8888));
                  }
               }

               imageAtMs = var27;
               Function0 var41 = onFrame;
               if (var41 != null) {
                  var41.invoke();
               }
            }
         }
      }
   }

   private final SideCam.Mask maskOf(Telemetry var1) {
      Object var2 = var1.getParams().get("PIPPreviewMask");
      if (var2 != null) {
         var2 = var2.toString();
      } else {
         var2 = null;
      }

      if (!Intrinsics.areEqual(var2, maskRaw)) {
         maskRaw = (String)var2;
         maskParsed = SideCam.Mask.Companion.parse((String)var2);
      }

      return maskParsed;
   }

   private final float smooth(float var1, float var2, float var3) {
      var1 = RangesKt.coerceIn((var3 - var1) / (var2 - var1), 0.0F, 1.0F);
      return var1 * var1 * (3 - 2 * var1);
   }

   // $VF: synthetic method
   public static void tick$default(SideCam var0, Telemetry var1, String var2, int var3, long var4, int var6, Object var7) {
      if ((var6 & 8) != 0) {
         var4 = System.currentTimeMillis();
      }

      var0.tick(var1, var2, var3, var4);
   }

   private static final Unit tick$lambda$0(String var0) {
      Intrinsics.checkNotNullParameter(var0, "s");
      status = var0;
      return Unit.INSTANCE;
   }

   private static final Unit tick$lambda$1(int var0, int var1) {
      return Unit.INSTANCE;
   }

   private static final Unit tick$lambda$3$lambda$2(Image var0) {
      Intrinsics.checkNotNullParameter(var0, "img");
      INSTANCE.frame(var0);
      return Unit.INSTANCE;
   }

   private static final void tick$lambda$4(VideoClient var0) {
      var0.close();
   }

   public final void close() {
      VideoClient var1 = client;
      if (var1 != null) {
         new Thread(new SideCam$$ExternalSyntheticLambda4(var1)).start();
      }

      client = null;
      images.clear();
   }

   public final long getImageAtMs() {
      return imageAtMs;
   }

   public final ConcurrentHashMap<String, Bitmap> getImages() {
      return images;
   }

   public final Function0<Unit> getOnFrame() {
      return onFrame;
   }

   public final String getStatus() {
      return status;
   }

   public final void setImageAtMs(long var1) {
      imageAtMs = var1;
   }

   public final void setOnFrame(Function0<Unit> var1) {
      onFrame = var1;
   }

   public final void setStatus(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      status = var1;
   }

   public final void tick(Telemetry var1, String var2, int var3, long var4) {
      Intrinsics.checkNotNullParameter(var1, "t");
      List var7 = this.wanted(var1);
      sides = CollectionsKt.toSet(var7);
      mask = this.maskOf(var1);
      boolean var6 = false;
      invert = var1.pBool("PIPPreviewInvert", false);
      if (!var7.isEmpty()) {
         lastWantMs = var4;
      }

      if (!var7.isEmpty() || var4 - lastWantMs < 5000L) {
         var6 = true;
      }

      VideoClient var8 = client;
      if (var6 && var8 == null && var2 != null) {
         VideoClient var9 = new VideoClient(new SideCam$$ExternalSyntheticLambda0(), new SideCam$$ExternalSyntheticLambda1());
         var9.setOnImage(new SideCam$$ExternalSyntheticLambda2());
         var9.connect(var2, var3, "driver", null);
         client = var9;
      } else if (!var6 && var8 != null) {
         new Thread(new SideCam$$ExternalSyntheticLambda3(var8)).start();
         client = null;
         images.clear();
      }
   }

   public final List<String> wanted(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "t");
      if (var1.getStarted() && var1.s("side_views") && var1.pBool("PIPPreviewEnabled", false)) {
         SideCam.Mask var6 = this.maskOf(var1);
         if (var6 == null) {
            return CollectionsKt.emptyList();
         } else {
            boolean var3 = var1.pBool("PIPPreviewShowOnBlinker", false);
            boolean var4 = var1.pBool("PIPPreviewShowOnBSM", false);
            ArrayList var7 = new ArrayList(2);

            for (Triple var10 : CollectionsKt.listOf(
               new Triple[]{
                  new Triple<>("right", var1.getRightBlinker(), var1.getRightBlindspot()), new Triple<>("left", var1.getLeftBlinker(), var1.getLeftBlindspot())
               }
            )) {
               String var8 = (String)var10.component1();
               boolean var2 = (Boolean)var10.component2();
               boolean var5 = (Boolean)var10.component3();
               float[] var11;
               if (Intrinsics.areEqual(var8, "right")) {
                  var11 = var6.getCenterLeft();
               } else {
                  var11 = var6.getCenterRight();
               }

               if (var11 != null && (var3 && var2 || var4 && var5)) {
                  var7.add(var8);
               }
            }

            return var7;
         }
      } else {
         return CollectionsKt.emptyList();
      }
   }

   public static final class Mask {
      public static final SideCam.Mask.Companion Companion = new SideCam.Mask.Companion(null);
      private static final SideCam.Mask DEFAULT = new SideCam.Mask(1928, 1208, 580.0F, new float[]{315.0F, 548.0F}, new float[]{1571.0F, 539.0F});
      private final float[] centerLeft;
      private final float[] centerRight;
      private final float cropSize;
      private final int height;
      private final int width;

      public Mask(int var1, int var2, float var3, float[] var4, float[] var5) {
         this.width = var1;
         this.height = var2;
         this.cropSize = var3;
         this.centerLeft = var4;
         this.centerRight = var5;
      }

      public final float[] crop(String var1, int var2, int var3) {
         Intrinsics.checkNotNullParameter(var1, "vehicleSide");
         float[] var9;
         if (Intrinsics.areEqual(var1, "right")) {
            var9 = this.centerLeft;
         } else {
            var9 = this.centerRight;
         }

         if (var9 == null) {
            return null;
         } else {
            float var4 = (float)var2 / this.width;
            float var8 = (float)var3 / this.height;
            float var6 = Math.max(1.0F, this.cropSize * Math.min(var4, var8));
            float var5 = var9[0];
            float var7 = 2;
            return new float[]{var5 * var4 - var6 / var7, var9[1] * var8 - var6 / var7, var6};
         }
      }

      public final float[] getCenterLeft() {
         return this.centerLeft;
      }

      public final float[] getCenterRight() {
         return this.centerRight;
      }

      public final float getCropSize() {
         return this.cropSize;
      }

      public final int getHeight() {
         return this.height;
      }

      public final int getWidth() {
         return this.width;
      }

      public static final class Companion {
         private Companion() {
         }

         // $VF: synthetic method
         public Companion(DefaultConstructorMarker var1) {
            this();
         }

         private static final float[] parse$center(JSONObject var0, float var1, int var2, int var3, String var4) {
            if (var0.isNull(var4)) {
               return null;
            } else {
               JSONArray var9 = var0.getJSONArray(var4);
               boolean var8;
               if (var9.length() == 2) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               if (!var8) {
                  throw new IllegalArgumentException("Failed requirement.".toString());
               } else {
                  float var6 = (float)var9.getDouble(0);
                  float var7 = (float)var9.getDouble(1);
                  if (!Float.isInfinite(var6) && !Float.isNaN(var6)) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  label47: {
                     if (var8) {
                        if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
                           var8 = true;
                        } else {
                           var8 = false;
                        }

                        if (var8) {
                           float var5 = 2;
                           if (var6 - var1 / var5 >= 0.0F && var1 / var5 + var6 <= var2 && var7 - var1 / var5 >= 0.0F && var1 / var5 + var7 <= var3) {
                              var10 = true;
                              break label47;
                           }
                        }
                     }

                     var10 = false;
                  }

                  if (var10) {
                     return new float[]{var6, var7};
                  } else {
                     throw new IllegalArgumentException("Failed requirement.".toString());
                  }
               }
            }
         }

         public final SideCam.Mask getDEFAULT() {
            return SideCam.Mask.DEFAULT;
         }

         // $VF: Duplicated exception handlers to handle obfuscated exceptions
         public final SideCam.Mask parse(String var1) {
            if (var1 != null && !StringsKt.isBlank(var1)) {
               Object var7 = null;

               JSONObject var8;
               try {
                  var8 = new JSONObject(var1);
                  Iterator var18 = var8.keys();
                  Intrinsics.checkNotNullExpressionValue(var18, "keys(...)");
                  var19 = SequencesKt.toSet(SequencesKt.asSequence(var18));
               } catch (Exception var15) {
                  return (SideCam.Mask)var7;
               }

               boolean var4 = false;

               try {
                  if (!Intrinsics.areEqual(var19, SetsKt.setOf(new String[]{"width", "height", "crop_size", "center_left", "center_right"}))) {
                     return null;
                  }
               } catch (Exception var16) {
                  return (SideCam.Mask)var7;
               }

               int var5;
               int var6;
               try {
                  var5 = var8.getInt("width");
                  var6 = var8.getInt("height");
               } catch (Exception var14) {
                  return (SideCam.Mask)var7;
               }

               boolean var3;
               if (1 <= var5 && var5 < 8193) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (!var3) {
                  return null;
               } else {
                  if (1 <= var6 && var6 < 8193) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  if (!var3) {
                     return null;
                  } else {
                     float var2;
                     try {
                        var2 = (float)var8.getDouble("crop_size");
                     } catch (Exception var13) {
                        return (SideCam.Mask)var7;
                     }

                     var3 = var4;

                     label120: {
                        try {
                           if (Float.isInfinite(var2)) {
                              break label120;
                           }
                        } catch (Exception var12) {
                           return (SideCam.Mask)var7;
                        }

                        var3 = var4;

                        try {
                           if (Float.isNaN(var2)) {
                              break label120;
                           }
                        } catch (Exception var11) {
                           return (SideCam.Mask)var7;
                        }

                        var3 = true;
                     }

                     if (!var3 || var2 < 1.0F) {
                        return null;
                     } else {
                        try {
                           if (var2 > Math.min(var5, var6)) {
                              return null;
                           }
                        } catch (Exception var10) {
                           return (SideCam.Mask)var7;
                        }

                        try {
                           var17 = new SideCam.Mask(
                              var5, var6, var2, parse$center(var8, var2, var5, var6, "center_left"), parse$center(var8, var2, var5, var6, "center_right")
                           );
                        } catch (Exception var9) {
                           var17 = (SideCam.Mask)var7;
                        }

                        return var17;
                     }
                  }
               }
            } else {
               return this.getDEFAULT();
            }
         }
      }
   }
}
