package com.starpilot.starview;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Images.Media;
import android.view.PixelCopy;
import android.view.SurfaceView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.LongRef;
import kotlin.text.StringsKt;

public final class LaneShots {
   public static final LaneShots.Companion Companion = new LaneShots.Companion(null);
   private Function0<String> answer;
   private final Context ctx;
   private volatile Detector detector;
   private volatile String driveFolder;
   private final File index;
   private final Lazy io$delegate;
   private Function0<Pair<Integer, Integer>> lanes;
   private long lastEventMs;
   private long lastPeriodicMs;
   private Function0<String> modelSays;
   private Function0<? extends List<LaneRamps.Ramp>> ramps;
   private final File reviews;
   private Function0<LaneBrain.Road> road;
   private Function0<? extends SurfaceView> scene;

   // $VF: synthetic method
   public static ExecutorService $r8$lambda$2mh5xYq8rN3kj_J1JMh4EV3XbLg/* $VF was: $r8$lambda$2mh5xYq8rN3kj_J1JMh4EV3XbLg*/() {
      return io_delegate$lambda$0();
   }

   // $VF: synthetic method
   public static void $r8$lambda$6M9_xhXxQOOgmFADdpW09Ez8mYY(
      LaneShots var0,
      Bitmap var1,
      Bitmap[] var2,
      int var3,
      int var4,
      String var5,
      LaneShots.Why var6,
      long var7,
      LaneBrain.Road var9,
      String var10,
      Bitmap var11,
      Bitmap var12,
      List var13,
      String var14,
      File var15
   ) {
      request$both$lambda$2$lambda$1(var0, var1, var2, var3, var4, var5, var6, var7, var9, var10, var11, var12, var13, var14, var15);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gyhQoIhCC0TVRIOTxGCTlBkPitg(
      Object var0,
      Bitmap[] var1,
      Bitmap[] var2,
      Detector var3,
      BooleanRef var4,
      BooleanRef var5,
      Bitmap[] var6,
      LaneShots var7,
      Bitmap[] var8,
      int var9,
      int var10,
      String var11,
      LaneShots.Why var12,
      long var13,
      LaneBrain.Road var15,
      String var16,
      List var17,
      String var18,
      File var19,
      Bitmap var20
   ) {
      return request$lambda$10(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var15, var16, var17, var18, var19, var20);
   }

   // $VF: synthetic method
   public static void $r8$lambda$hHknUq62ntTyYpsraGJiAtJRGkE(
      Object var0,
      BooleanRef var1,
      Bitmap[] var2,
      BooleanRef var3,
      Bitmap[] var4,
      Bitmap[] var5,
      LaneShots var6,
      Bitmap[] var7,
      int var8,
      int var9,
      String var10,
      LaneShots.Why var11,
      long var12,
      LaneBrain.Road var14,
      String var15,
      List var16,
      String var17,
      File var18
   ) {
      request$lambda$6(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var14, var15, var16, var17, var18);
   }

   // $VF: synthetic method
   public static void $r8$lambda$lQCS6paatwVqiz_xGkdELBQ5uDM(
      Object var0,
      Bitmap[] var1,
      Bitmap var2,
      BooleanRef var3,
      Bitmap[] var4,
      BooleanRef var5,
      Bitmap[] var6,
      Bitmap[] var7,
      LaneShots var8,
      int var9,
      int var10,
      String var11,
      LaneShots.Why var12,
      long var13,
      LaneBrain.Road var15,
      String var16,
      List var17,
      String var18,
      File var19,
      int var20
   ) {
      request$lambda$8(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var15, var16, var17, var18, var19, var20);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$u_gVHrlSpZ3h7MWvyzqgwrljTW0/* $VF was: $r8$lambda$u_gVHrlSpZ3h7MWvyzqgwrljTW0*/(
      Object var0,
      BooleanRef var1,
      Bitmap[] var2,
      Bitmap[] var3,
      BooleanRef var4,
      Bitmap[] var5,
      LaneShots var6,
      Bitmap[] var7,
      int var8,
      int var9,
      String var10,
      LaneShots.Why var11,
      long var12,
      LaneBrain.Road var14,
      String var15,
      List var16,
      String var17,
      File var18,
      Bitmap var19
   ) {
      return request$lambda$4(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var14, var15, var16, var17, var18, var19);
   }

   public LaneShots(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      this.ctx = var1;
      this.index = new File(this.ctx.getFilesDir(), "lane_shots.txt");
      this.reviews = new File(this.ctx.getFilesDir(), "lane_reviews.txt");
      this.io$delegate = LazyKt.lazy(new LaneShots$$ExternalSyntheticLambda0());
      String var2 = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.US).format(new Date());
      this.driveFolder = "Pictures/StarView Lanes/" + var2;
   }

   // $VF: synthetic method
   public static Bitmap compose$default(LaneShots var0, Bitmap var1, Bitmap var2, int var3, int var4, String var5, List var6, int var7, Object var8) {
      if ((var7 & 32) != 0) {
         var6 = CollectionsKt.emptyList();
      }

      return var0.compose(var1, var2, var3, var4, var5, var6);
   }

   private static final void export$lambda$72$lambda$71$put(ZipOutputStream var0, LongRef var1, String var2, byte[] var3) {
      var0.putNextEntry(new ZipEntry(var2));
      var0.write(var3);
      var0.closeEntry();
      var1.element += var3.length;
   }

   private static final String export$q(String var0) {
      var0 = StringsKt.replace$default(var0, "\"", "'", false, 4, null);
      return "\"" + var0 + "\"";
   }

   private final ExecutorService getIo() {
      return (ExecutorService)this.io$delegate.getValue();
   }

   private static final ExecutorService io_delegate$lambda$0() {
      return Executors.newSingleThreadExecutor();
   }

   // $VF: synthetic method
   public static void mark$default(LaneShots var0, String var1, int var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = "";
      }

      var0.mark(var1, var2, var3);
   }

   private static final void request$both(
      Object param0,
      Bitmap[] param1,
      BooleanRef param2,
      BooleanRef param3,
      Bitmap[] param4,
      Bitmap[] param5,
      LaneShots param6,
      Bitmap[] param7,
      int param8,
      int param9,
      String param10,
      LaneShots.Why param11,
      long param12,
      LaneBrain.Road param14,
      String param15,
      List<LaneRamps.Ramp> param16,
      String param17,
      File param18
   ) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: bipush 0
      // 04: aaload
      // 05: astore 19
      // 07: aload 19
      // 09: ifnonnull 0f
      // 0c: aload 0
      // 0d: monitorexit
      // 0e: return
      // 0f: aload 2
      // 10: getfield kotlin/jvm/internal/Ref$BooleanRef.element Z
      // 13: ifeq 69
      // 16: aload 3
      // 17: getfield kotlin/jvm/internal/Ref$BooleanRef.element Z
      // 1a: ifne 20
      // 1d: goto 0c
      // 20: aload 1
      // 21: bipush 0
      // 22: aconst_null
      // 23: aastore
      // 24: aload 4
      // 26: bipush 0
      // 27: aaload
      // 28: astore 1
      // 29: aload 5
      // 2b: bipush 0
      // 2c: aaload
      // 2d: astore 2
      // 2e: aload 6
      // 30: invokespecial com/starpilot/starview/LaneShots.getIo ()Ljava/util/concurrent/ExecutorService;
      // 33: astore 3
      // 34: new com/starpilot/starview/LaneShots$$ExternalSyntheticLambda1
      // 37: astore 4
      // 39: aload 4
      // 3b: aload 6
      // 3d: aload 19
      // 3f: aload 7
      // 41: iload 8
      // 43: iload 9
      // 45: aload 10
      // 47: aload 11
      // 49: lload 12
      // 4b: aload 14
      // 4d: aload 15
      // 4f: aload 1
      // 50: aload 2
      // 51: aload 16
      // 53: aload 17
      // 55: aload 18
      // 57: invokespecial com/starpilot/starview/LaneShots$$ExternalSyntheticLambda1.<init> (Lcom/starpilot/starview/LaneShots;Landroid/graphics/Bitmap;[Landroid/graphics/Bitmap;IILjava/lang/String;Lcom/starpilot/starview/LaneShots$Why;JLcom/starpilot/starview/LaneBrain$Road;Ljava/lang/String;Landroid/graphics/Bitmap;Landroid/graphics/Bitmap;Ljava/util/List;Ljava/lang/String;Ljava/io/File;)V
      // 5a: aload 3
      // 5b: aload 4
      // 5d: invokeinterface java/util/concurrent/ExecutorService.execute (Ljava/lang/Runnable;)V 2
      // 62: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 65: astore 1
      // 66: aload 0
      // 67: monitorexit
      // 68: return
      // 69: goto 0c
      // 6c: astore 1
      // 6d: aload 0
      // 6e: monitorexit
      // 6f: aload 1
      // 70: athrow
   }

   private static final void request$both$lambda$2$lambda$1(
      LaneShots var0,
      Bitmap var1,
      Bitmap[] var2,
      int var3,
      int var4,
      String var5,
      LaneShots.Why var6,
      long var7,
      LaneBrain.Road var9,
      String var10,
      Bitmap var11,
      Bitmap var12,
      List var13,
      String var14,
      File var15
   ) {
      var0.save(var1, var2[0], var3, var4, var5, var6, var7, var9, var10, var11, var12, var13, var14, var15);
   }

   // $VF: synthetic method
   public static void request$default(LaneShots var0, String var1, LaneShots.Why var2, File var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = null;
      }

      var0.request(var1, var2, var3);
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final Unit request$lambda$10(
      Object var0,
      Bitmap[] var1,
      Bitmap[] var2,
      Detector var3,
      BooleanRef var4,
      BooleanRef var5,
      Bitmap[] var6,
      LaneShots var7,
      Bitmap[] var8,
      int var9,
      int var10,
      String var11,
      LaneShots.Why var12,
      long var13,
      LaneBrain.Road var15,
      String var16,
      List var17,
      String var18,
      File var19,
      Bitmap var20
   ) {
      Intrinsics.checkNotNullParameter(var20, "bmp");
      synchronized (var0){} // $VF: monitorenter 
      var1[0] = var20;

      try {
         var2[0] = var3.getLastClean();
         Unit var23 = Unit.INSTANCE;
      } finally {
         // $VF: monitorexit
      }

      request$both(var0, var1, var4, var5, var2, var6, var7, var8, var9, var10, var11, var12, var13, var15, var16, var17, var18, var19);
      return Unit.INSTANCE;
   }

   private static final Unit request$lambda$4(
      Object param0,
      BooleanRef param1,
      Bitmap[] param2,
      Bitmap[] param3,
      BooleanRef param4,
      Bitmap[] param5,
      LaneShots param6,
      Bitmap[] param7,
      int param8,
      int param9,
      String param10,
      LaneShots.Why param11,
      long param12,
      LaneBrain.Road param14,
      String param15,
      List param16,
      String param17,
      File param18,
      Bitmap param19
   ) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 19
      // 02: ldc_w "b"
      // 05: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 08: aload 0
      // 09: monitorenter
      // 0a: aload 1
      // 0b: getfield kotlin/jvm/internal/Ref$BooleanRef.element Z
      // 0e: ifne 1b
      // 11: aload 2
      // 12: bipush 0
      // 13: aload 19
      // 15: aastore
      // 16: aload 1
      // 17: bipush 1
      // 18: putfield kotlin/jvm/internal/Ref$BooleanRef.element Z
      // 1b: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1e: astore 19
      // 20: aload 0
      // 21: monitorexit
      // 22: aload 0
      // 23: aload 3
      // 24: aload 4
      // 26: aload 1
      // 27: aload 5
      // 29: aload 2
      // 2a: aload 6
      // 2c: aload 7
      // 2e: iload 8
      // 30: iload 9
      // 32: aload 10
      // 34: aload 11
      // 36: lload 12
      // 38: aload 14
      // 3a: aload 15
      // 3c: aload 16
      // 3e: aload 17
      // 40: aload 18
      // 42: invokestatic com/starpilot/starview/LaneShots.request$both (Ljava/lang/Object;[Landroid/graphics/Bitmap;Lkotlin/jvm/internal/Ref$BooleanRef;Lkotlin/jvm/internal/Ref$BooleanRef;[Landroid/graphics/Bitmap;[Landroid/graphics/Bitmap;Lcom/starpilot/starview/LaneShots;[Landroid/graphics/Bitmap;IILjava/lang/String;Lcom/starpilot/starview/LaneShots$Why;JLcom/starpilot/starview/LaneBrain$Road;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/io/File;)V
      // 45: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 48: areturn
      // 49: astore 1
      // 4a: aload 0
      // 4b: monitorexit
      // 4c: aload 1
      // 4d: athrow
   }

   private static final void request$lambda$6(
      Object var0,
      BooleanRef var1,
      Bitmap[] var2,
      BooleanRef var3,
      Bitmap[] var4,
      Bitmap[] var5,
      LaneShots var6,
      Bitmap[] var7,
      int var8,
      int var9,
      String var10,
      LaneShots.Why var11,
      long var12,
      LaneBrain.Road var14,
      String var15,
      List var16,
      String var17,
      File var18
   ) {
      synchronized (var0) {
         var1.element = true;
         Unit var19 = Unit.INSTANCE;
      }

      request$both(var0, var2, var3, var1, var4, var5, var6, var7, var8, var9, var10, var11, var12, var14, var15, var16, var17, var18);
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final void request$lambda$8(
      Object var0,
      Bitmap[] var1,
      Bitmap var2,
      BooleanRef var3,
      Bitmap[] var4,
      BooleanRef var5,
      Bitmap[] var6,
      Bitmap[] var7,
      LaneShots var8,
      int var9,
      int var10,
      String var11,
      LaneShots.Why var12,
      long var13,
      LaneBrain.Road var15,
      String var16,
      List var17,
      String var18,
      File var19,
      int var20
   ) {
      synchronized (var0){} // $VF: monitorenter 
      if (var20 == 0) {
         var1[0] = var2;
      }

      try {
         var3.element = true;
         Unit var23 = Unit.INSTANCE;
      } finally {
         // $VF: monitorexit
      }

      request$both(var0, var4, var3, var5, var6, var7, var8, var1, var9, var10, var11, var12, var13, var15, var16, var17, var18, var19);
   }

   private final boolean roomLeft() {
      boolean var1 = true;

      long var2;
      try {
         var2 = Environment.getExternalStorageDirectory().getUsableSpace();
      } catch (Exception var5) {
         return var1;
      }

      if (var2 <= 2147483648L) {
         var1 = false;
      }

      return var1;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final void save(
      Bitmap var1,
      Bitmap var2,
      int var3,
      int var4,
      String var5,
      LaneShots.Why var6,
      long var7,
      LaneBrain.Road var9,
      String var10,
      Bitmap var11,
      Bitmap var12,
      List<LaneRamps.Ramp> var13,
      String var14,
      File var15
   ) {
      Bitmap var18;
      try {
         var18 = this.compose(var1, var2, var3, var4, var5, var13);
      } catch (Exception var131) {
         return;
      }

      label482:
      if (var15 != null) {
         try {
            FileOutputStream var19 = new FileOutputStream(var15);
            var147 = var19;
         } catch (Exception var130) {
            break label482;
         }

         try {
            FileOutputStream var153 = var147;
            var18.compress(CompressFormat.JPEG, 82, var153);
         } catch (Throwable var129) {
            Throwable var152 = var129;

            try {
               throw var152;
            } finally {
               try {
                  CloseableKt.closeFinally(var147, var152);
               } catch (Exception var127) {
                  break label482;
               }
            }
         }

         try {
            CloseableKt.closeFinally(var147, null);
         } catch (Exception var111) {
         }
      }

      if (var2 != null) {
         try {
            var2.recycle();
         } catch (Exception var126) {
            return;
         }
      }

      try {
         var136 = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US);
      } catch (Exception var125) {
         return;
      }

      try {
         var148 = new Date(var7);
         String var149 = var136.format(var148);
         StringBuilder var137 = new StringBuilder();
         var150 = var137.append("lane_").append(var149).append(".jpg").toString();
         var138 = new ContentValues();
         var138.put("_display_name", var150);
         var138.put("mime_type", "image/jpeg");
         var138.put("relative_path", this.driveFolder);
      } catch (Exception var124) {
         return;
      }

      try {
         var139 = this.ctx.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, var138);
      } catch (Exception var123) {
         return;
      }

      if (var139 != null) {
         OutputStream var154;
         try {
            var154 = this.ctx.getContentResolver().openOutputStream(var139);
         } catch (Exception var122) {
            return;
         }

         if (var154 != null) {
            try {
               var154 = var154;
            } catch (Exception var121) {
               return;
            }

            try {
               OutputStream var20 = var154;
               var18.compress(CompressFormat.JPEG, 72, var20);
            } catch (Throwable var120) {
               Throwable var140 = var120;

               try {
                  throw var140;
               } finally {
                  try {
                     CloseableKt.closeFinally(var154, var140);
                  } catch (Exception var117) {
                     return;
                  }
               }
            }

            try {
               CloseableKt.closeFinally(var154, null);
            } catch (Exception var119) {
               return;
            }
         }

         String var21;
         String var158;
         try {
            var18.recycle();
            var1.recycle();
            var151 = StringsKt.substringAfterLast$default(this.driveFolder, '/', null, 2, null);
            File var132 = new File(this.getCleanDir(), var151);
            var132.mkdirs();
            String var156 = StringsKt.removeSuffix(var150, ".jpg");
            var145 = save$keep(var132, var156, var151, var11, "road");
            var157 = save$keep(var132, var156, var151, var12, "wide");
            var146 = this.index;
            var158 = var139.toString();
            var21 = var6.name();
            var144 = StringsKt.replace$default(StringsKt.replace$default(var5, "\n", " | ", false, 4, null), "\t", " ", false, 4, null);
         } catch (Exception var116) {
            return;
         }

         long var16;
         if (var9 != null) {
            try {
               var16 = var9.getId();
            } catch (Exception var115) {
               return;
            }
         } else {
            var16 = 0L;
         }

         label428: {
            if (var9 != null) {
               try {
                  var141 = var9.getName();
               } catch (Exception var114) {
                  return;
               }

               var133 = var141;
               if (var141 != null) {
                  break label428;
               }
            }

            var133 = "";
         }

         try {
            var134 = StringsKt.replace$default(var133, "\t", " ", false, 4, null);
         } catch (Exception var113) {
            return;
         }

         try {
            var142 = LaneRamps.INSTANCE.codes(var13);
         } catch (Exception var112) {
            return;
         }

         try {
            String var135 = CollectionsKt.joinToString$default(
               CollectionsKt.listOf(
                  new String[]{
                     var150, var158, String.valueOf(var7), var21, var144, var151, String.valueOf(var16), var134, var10, var145, var157, var142, var14
                  }
               ),
               "\t",
               null,
               null,
               0,
               null,
               null,
               62,
               null
            );
            StringBuilder var143 = new StringBuilder();
            FilesKt.appendText$default(var146, var143.append(var135).append("\n").toString(), null, 2, null);
            if (StringsKt.startsWith$default(var5, "[auto]", false, 2, null)) {
               mark$default(this, var150, 1, null, 4, null);
            }
         } catch (Exception var110) {
         }
      }
   }

   // $VF: synthetic method
   static void save$default(
      LaneShots var0,
      Bitmap var1,
      Bitmap var2,
      int var3,
      int var4,
      String var5,
      LaneShots.Why var6,
      long var7,
      LaneBrain.Road var9,
      String var10,
      Bitmap var11,
      Bitmap var12,
      List var13,
      String var14,
      File var15,
      int var16,
      Object var17
   ) {
      if ((var16 & 2048) != 0) {
         var13 = CollectionsKt.emptyList();
      }

      if ((var16 & 4096) != 0) {
         var14 = "";
      }

      if ((var16 & 8192) != 0) {
         var15 = null;
      }

      var0.save(var1, var2, var3, var4, var5, var6, var7, var9, var10, var11, var12, var13, var14, var15);
   }

   private static final String save$keep(File param0, String param1, String param2, Bitmap param3, String param4) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc_w ""
      // 03: astore 5
      // 05: aload 3
      // 06: ifnonnull 0d
      // 09: ldc_w ""
      // 0c: areturn
      // 0d: new java/io/FileOutputStream
      // 10: astore 8
      // 12: new java/io/File
      // 15: astore 7
      // 17: new java/lang/StringBuilder
      // 1a: astore 6
      // 1c: aload 6
      // 1e: invokespecial java/lang/StringBuilder.<init> ()V
      // 21: aload 7
      // 23: aload 0
      // 24: aload 6
      // 26: aload 1
      // 27: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2a: ldc_w "_"
      // 2d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 30: aload 4
      // 32: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 35: ldc_w ".jpg"
      // 38: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3e: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 41: aload 8
      // 43: aload 7
      // 45: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 48: aload 8
      // 4a: checkcast java/io/Closeable
      // 4d: astore 0
      // 4e: aload 0
      // 4f: checkcast java/io/FileOutputStream
      // 52: astore 6
      // 54: aload 3
      // 55: getstatic android/graphics/Bitmap$CompressFormat.JPEG Landroid/graphics/Bitmap$CompressFormat;
      // 58: bipush 80
      // 5a: aload 6
      // 5c: checkcast java/io/OutputStream
      // 5f: invokevirtual android/graphics/Bitmap.compress (Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
      // 62: pop
      // 63: aload 0
      // 64: aconst_null
      // 65: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 68: new java/lang/StringBuilder
      // 6b: astore 0
      // 6c: aload 0
      // 6d: invokespecial java/lang/StringBuilder.<init> ()V
      // 70: aload 0
      // 71: aload 2
      // 72: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 75: ldc_w "/"
      // 78: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 7b: aload 1
      // 7c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 7f: ldc_w "_"
      // 82: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 85: aload 4
      // 87: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 8a: ldc_w ".jpg"
      // 8d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 90: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 93: astore 0
      // 94: goto ad
      // 97: astore 1
      // 98: aload 1
      // 99: athrow
      // 9a: astore 2
      // 9b: aload 0
      // 9c: aload 1
      // 9d: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // a0: aload 2
      // a1: athrow
      // a2: astore 0
      // a3: aload 3
      // a4: invokevirtual android/graphics/Bitmap.recycle ()V
      // a7: aload 0
      // a8: athrow
      // a9: astore 0
      // aa: aload 5
      // ac: astore 0
      // ad: aload 3
      // ae: invokevirtual android/graphics/Bitmap.recycle ()V
      // b1: aload 0
      // b2: areturn
   }

   private final void sketch(Canvas var1, float var2, float var3, float var4, int var5, int var6, List<LaneRamps.Ramp> var7) {
      Paint var18 = new Paint(1);
      var18.setColor(Color.rgb(28, 32, 40));
      var1.drawRect(var2, 0.0F, var2 + var3, var4, var18);
      int var15;
      if (var5 > 0) {
         var15 = var5;
      } else if (var6 > 0) {
         var15 = var6 + 1;
      } else {
         var15 = 0;
      }

      Paint var17 = new Paint(1);
      var17.setColor(Color.rgb(200, 208, 222));
      var17.setTextSize(20.0F);
      var17.setTextAlign(Align.CENTER);
      if (var15 <= 0) {
         float var28 = 2;
         var1.drawText("lane unknown", var2 + var3 / var28, var4 / var28, var17);
      } else {
         float var11 = Math.min(64.0F, (var3 - 40.0F) / var15);
         float var8 = var15;
         float var12 = 2;
         float var13 = var2 + (var3 - var8 * var11) / var12;
         var18.setColor(Color.rgb(120, 126, 136));
         var1.drawRect(var13, 30.0F, var13 + var15 * var11, var4 - 20.0F, var18);
         if (var15 <= 0) {
            int var14 = 0;

            while (true) {
               int var16;
               if (var14 == 0) {
                  var16 = Color.rgb(242, 194, 48);
               } else {
                  var16 = -1;
               }

               var18.setColor(var16);
               float var9 = var13 + var14 * var11;
               if (var14 != 0 && var14 != var15) {
                  for (float var25 = 34.0F; var25 < var4 - 24.0F; var25 += 36.0F) {
                     var1.drawRect(var9 - 1.5F, var25, var9 + 1.5F, var25 + 18.0F, var18);
                  }
               } else {
                  var1.drawRect(var9 - 2.0F, 30.0F, var9 + 2.0F, var4 - 20.0F, var18);
               }

               if (var14 == var15) {
                  break;
               }

               var14++;
            }
         }

         boolean var30;
         if (1 <= var6 && var6 <= var15) {
            var30 = true;
         } else {
            var30 = false;
         }

         if (var30) {
            var18.setColor(Color.rgb(55, 138, 221));
            var8 = var13 + (var6 - 0.5F) * var11;
            var1.drawRoundRect(var8 - var11 * 0.28F, var4 - 120.0F, var8 + 0.28F * var11, var4 - 50.0F, 8.0F, 8.0F, var18);
         }

         for (LaneRamps.Ramp var21 : var7) {
            if (var21.getSide() < 0) {
               var8 = var13 - var11;
            } else {
               var8 = var13 + var15 * var11;
            }

            if (!(var8 < var2) && !(var8 + var11 > var2 + var3)) {
               var18.setColor(Color.rgb(96, 72, 26));
               var1.drawRect(var8, 30.0F, var8 + var11, var4 - 20.0F, var18);
               var18.setColor(Color.rgb(240, 181, 74));
               float var29;
               if (var21.getSide() < 0) {
                  var29 = var8 - 2.0F;
               } else {
                  var29 = var8 + var11 - 2.0F;
               }

               float var10;
               if (var21.getSide() < 0) {
                  var10 = var8 + 2.0F;
               } else {
                  var10 = var8 + var11 + 2.0F;
               }

               var1.drawRect(var29, 30.0F, var10, var4 - 20.0F, var18);
               Paint var20 = new Paint(1);
               var20.setColor(Color.rgb(240, 181, 74));
               var20.setTextSize(15.0F);
               var20.setTextAlign(Align.CENTER);
               var20.setTypeface(Typeface.DEFAULT_BOLD);
               String var22;
               if (Intrinsics.areEqual(var21.getKind(), "exit")) {
                  var22 = "EXIT";
               } else {
                  var22 = "MERGE";
               }

               var1.drawText(var22, var8 + var11 / var12, var4 / var12, var20);
            }
         }

         StringBuilder var24;
         if (var5 > 0) {
            String var23;
            if (var6 > 0) {
               var23 = String.valueOf(var6);
            } else {
               var23 = "?";
            }

            var24 = new StringBuilder().append("card: lane ").append(var23).append(" of ").append(var5);
         } else {
            var24 = new StringBuilder().append("card: lane ").append(var6).append(" of ?");
         }

         var1.drawText(var24.toString(), var2 + var3 / var12, 22.0F, var17);
      }
   }

   // $VF: synthetic method
   static void sketch$default(LaneShots var0, Canvas var1, float var2, float var3, float var4, int var5, int var6, List var7, int var8, Object var9) {
      if ((var8 & 64) != 0) {
         var7 = CollectionsKt.emptyList();
      }

      var0.sketch(var1, var2, var3, var4, var5, var6, var7);
   }

   public final Bitmap compose(Bitmap var1, Bitmap var2, int var3, int var4, String var5, List<LaneRamps.Ramp> var6) {
      Intrinsics.checkNotNullParameter(var1, "src");
      Intrinsics.checkNotNullParameter(var5, "caption");
      Intrinsics.checkNotNullParameter(var6, "rp");
      List var17 = StringsKt.split$default(var5, new String[]{"\n"}, false, 0, 6, null);
      int var13 = var17.size();
      int var12 = var1.getHeight();
      int var11;
      if (var2 != null) {
         var11 = var2.getWidth() * var12 / var2.getHeight();
      } else {
         var11 = 300;
      }

      Bitmap var15 = Bitmap.createBitmap(var1.getWidth() + 6 + var11, var12 + var13 * 26 + 30, Config.ARGB_8888);
      Intrinsics.checkNotNullExpressionValue(var15, "createBitmap(...)");
      Canvas var16 = new Canvas(var15);
      var16.drawColor(-16777216);
      var16.drawBitmap(var1, 0.0F, 0.0F, null);
      float var8 = 6.0F + var1.getWidth();
      if (var2 != null) {
         var16.drawBitmap(var2, null, new RectF(var8, 0.0F, var11 + var8, var12), new Paint(2));
      } else {
         this.sketch(var16, var8, var11, var12, var3, var4, var6);
      }

      var3 = var11;
      if (!var6.isEmpty()) {
         Paint var18 = new Paint(1);
         var18.setColor(-16777216);
         var18.setTextSize(19.0F);
         var18.setTypeface(Typeface.DEFAULT_BOLD);
         Paint var27 = new Paint(1);
         var27.setColor(Color.rgb(240, 181, 74));
         Iterable var22 = var6;
         boolean var14 = false;
         var4 = 0;

         for (LaneRamps.Ramp var19 : var22) {
            if (var4 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            var19 = var19;
            String var21 = var19.text();
            Locale var20 = Locale.US;
            Intrinsics.checkNotNullExpressionValue(var20, "US");
            String var38 = var21.toUpperCase(var20);
            Intrinsics.checkNotNullExpressionValue(var38, "toUpperCase(...)");
            String var39 = "MAP: " + var38;
            float var10 = var18.measureText(var39);
            float var9 = var4 * 34.0F + 10.0F;
            float var7;
            if (var19.getSide() < 0) {
               var7 = var8 + 8.0F;
            } else {
               var7 = var8 + var3 - var10 - 24.0F;
            }

            var16.drawRoundRect(new RectF(var7, var9, var7 + var10 + 16.0F, var9 + 28.0F), 8.0F, 8.0F, var27);
            var16.drawText(var39, var7 + 8.0F, 21.0F + var9, var18);
            var4++;
         }
      }

      Paint var28 = new Paint(1);
      var28.setColor(-1);
      var28.setTextSize(22.0F);
      var28.setTypeface(Typeface.DEFAULT_BOLD);
      Paint var23 = new Paint(1);
      var23.setColor(Color.rgb(200, 208, 222));
      var23.setTextSize(19.0F);
      Iterable var30 = var17;
      var3 = 0;

      for (Object var31 : var30) {
         if (var3 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         String var36 = (String)var31;
         var8 = var1.getHeight();
         float var33 = var3;
         Paint var32;
         if (var3 == 0) {
            var32 = var28;
         } else {
            var32 = var23;
         }

         var16.drawText(var36, 12.0F, var8 + 30.0F + var33 * 26.0F, var32);
         var3++;
      }

      return var15;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final int deleteDrive(String var1) {
      Intrinsics.checkNotNullParameter(var1, "drive");

      List var4;
      try {
         var4 = FilesKt.readLines$default(this.index, null, 1, null);
      } catch (Exception var14) {
         return 0;
      }

      Iterable var43 = var4;
      ArrayList var7 = new ArrayList();
      ArrayList var6 = new ArrayList();
      Iterator var8 = var43.iterator();

      while (true) {
         boolean var3 = var8.hasNext();
         Iterable var5 = "";
         if (!var3) {
            Pair var46 = new Pair<>(var7, var6);
            List var62 = (List)var46.component1();
            var4 = (List)var46.component2();
            Iterator var63 = var62.iterator();

            while (var63.hasNext()) {
               List var58 = StringsKt.split$default((String & CharSequence)var63.next(), new String[]{"\t"}, false, 0, 6, null);

               try {
                  this.ctx.getContentResolver().delete(Uri.parse((String)var58.get(1)), null, null);
               } catch (Exception var13) {
               }

               boolean var37;
               if (9 < var58.size()) {
                  var37 = true;
               } else {
                  var37 = false;
               }

               Object var54;
               if (var37) {
                  var54 = var58.get(9);
               } else {
                  var54 = "";
               }

               if (10 < var58.size()) {
                  var37 = true;
               } else {
                  var37 = false;
               }

               Object var59;
               if (var37) {
                  var59 = var58.get(10);
               } else {
                  var59 = "";
               }

               for (var var54_it : CollectionsKt.listOf(new String[]{(String)var54, (String)var59})) {

                  var54 = var54_it;
                  if (((CharSequence)var54).length() > 0) {
                     var37 = true;
                  } else {
                     var37 = false;
                  }

                  if (var37) {
                     new File(this.getCleanDir(), (String)var54).delete();
                  }
               }
            }

            File var56 = new File(this.getCleanDir(), var1);
            if (var56.isDirectory()) {
               boolean var41;
               label231: {
                  String[] var29 = var56.list();
                  if (var29 != null) {
                     if (var29.length == 0) {
                        var41 = true;
                     } else {
                        var41 = false;
                     }

                     if (!var41) {
                        var41 = true;
                        break label231;
                     }
                  }

                  var41 = false;
               }

               if (!var41) {
                  var56.delete();
               }
            }

            File var30 = this.index;
            if (!var4.isEmpty()) {
               var5 = CollectionsKt.joinToString$default(var4, "\n", null, null, 0, null, null, 62, null);
               var5 = var5 + "\n";
            }

            FilesKt.writeText$default(var30, var5, null, 2, null);
            var5 = var62;
            var var31 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
            Iterator var52 = var5.iterator();

            while (var52.hasNext()) {
               var31.add(StringsKt.substringBefore$default((String)var52.next(), '\t', null, 2, null));
            }

            List var32 = var31;
            HashSet var64 = CollectionsKt.toHashSet(var32);

            try {
               var61 = this.reviews;
            } catch (Exception var28) {
               return var62.size();
            }

            try {
               var57 = FilesKt.readLines$default(this.reviews, null, 1, null);
            } catch (Exception var27) {
               return var62.size();
            }

            try {
            } catch (Exception var26) {
               return var62.size();
            }

            try {
               var33 = new ArrayList();
            } catch (Exception var25) {
               return var62.size();
            }

            ArrayList var10;
            try {
               var10 = var33;
            } catch (Exception var24) {
               return var62.size();
            }

            Iterator var11;
            try {
               var11 = var57.iterator();
            } catch (Exception var23) {
               return var62.size();
            }

            while (true) {
               try {
                  if (!var11.hasNext()) {
                     break;
                  }
               } catch (Exception var22) {
                  return var62.size();
               }

               try {
                  var4 = (List)var11.next();
               } catch (Exception var21) {
                  return var62.size();
               }

               try {
                  var53 = (String)var4;
               } catch (Exception var20) {
                  return var62.size();
               }

               try {
                  if (!var64.contains(StringsKt.substringBefore$default(var53, '\t', null, 2, null))) {
                     var10.add(var4);
                  }
               } catch (Exception var19) {
                  return var62.size();
               }
            }

            try {
               var34 = var10;
            } catch (Exception var18) {
               return var62.size();
            }

            try {
               var1 = CollectionsKt.joinToString$default(var34, "\n", null, null, 0, null, null, 62, null);
            } catch (Exception var17) {
               return var62.size();
            }

            boolean var42;
            label172: {
               label171: {
                  try {
                     if (var1.length() == 0) {
                        break label171;
                     }
                  } catch (Exception var16) {
                     return var62.size();
                  }

                  var42 = false;
                  break label172;
               }

               var42 = true;
            }

            if (!var42) {
               try {
                  StringBuilder var49 = new StringBuilder();
                  var1 = var49.append(var1).append("\n").toString();
               } catch (Exception var15) {
                  return var62.size();
               }
            }

            try {
               FilesKt.writeText$default(var61, var1, null, 2, null);
            } catch (Exception var12) {
            }

            return var62.size();
         }

         Object var9 = var8.next();
         var4 = StringsKt.split$default((String & CharSequence)var9, new String[]{"\t"}, false, 0, 6, null);
         boolean var2;
         if (5 < var4.size()) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            var5 = (Iterable)var4.get(5);
         }

         var4 = (CharSequence)var5;
         if (var4.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            var4 = "earlier";
         }

         if (Intrinsics.areEqual(var4, var1)) {
            var7.add(var9);
         } else {
            var6.add(var9);
         }
      }
   }

   public final String export(Function1<? super String, Unit> param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: ldc_w "lane_counts.json"
      // 003: astore 30
      // 005: ldc_w "lane_log.txt"
      // 008: astore 31
      // 00a: ldc_w "\n"
      // 00d: astore 11
      // 00f: aload 1
      // 010: ldc_w "progress"
      // 013: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 016: aload 0
      // 017: getfield com/starpilot/starview/LaneShots.index Ljava/io/File;
      // 01a: aconst_null
      // 01b: bipush 1
      // 01c: aconst_null
      // 01d: invokestatic kotlin/io/FilesKt.readLines$default (Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/util/List;
      // 020: checkcast java/lang/Iterable
      // 023: astore 10
      // 025: new java/util/ArrayList
      // 028: astore 9
      // 02a: aload 9
      // 02c: aload 10
      // 02e: bipush 10
      // 030: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 033: invokespecial java/util/ArrayList.<init> (I)V
      // 036: aload 9
      // 038: checkcast java/util/Collection
      // 03b: astore 9
      // 03d: aload 10
      // 03f: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 044: astore 10
      // 046: aload 10
      // 048: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 04d: ifeq 07a
      // 050: aload 9
      // 052: aload 10
      // 054: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 059: checkcast java/lang/String
      // 05c: checkcast java/lang/CharSequence
      // 05f: bipush 1
      // 060: anewarray 502
      // 063: dup
      // 064: bipush 0
      // 065: ldc_w "\t"
      // 068: aastore
      // 069: bipush 0
      // 06a: bipush 0
      // 06b: bipush 6
      // 06d: aconst_null
      // 06e: invokestatic kotlin/text/StringsKt.split$default (Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;
      // 071: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 076: pop
      // 077: goto 046
      // 07a: aload 9
      // 07c: checkcast java/util/List
      // 07f: astore 9
      // 081: aload 9
      // 083: checkcast java/lang/Iterable
      // 086: astore 10
      // 088: new java/util/ArrayList
      // 08b: astore 9
      // 08d: aload 9
      // 08f: invokespecial java/util/ArrayList.<init> ()V
      // 092: aload 9
      // 094: checkcast java/util/Collection
      // 097: astore 9
      // 099: aload 10
      // 09b: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 0a0: astore 10
      // 0a2: aload 10
      // 0a4: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0a9: ifeq 0db
      // 0ac: aload 10
      // 0ae: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0b3: astore 12
      // 0b5: aload 12
      // 0b7: checkcast java/util/List
      // 0ba: invokeinterface java/util/List.size ()I 1
      // 0bf: bipush 5
      // 0c0: if_icmplt 0c8
      // 0c3: bipush 1
      // 0c4: istore 2
      // 0c5: goto 0ca
      // 0c8: bipush 0
      // 0c9: istore 2
      // 0ca: iload 2
      // 0cb: ifeq 0d8
      // 0ce: aload 9
      // 0d0: aload 12
      // 0d2: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 0d7: pop
      // 0d8: goto 0a2
      // 0db: aload 9
      // 0dd: checkcast java/util/List
      // 0e0: astore 12
      // 0e2: goto 0ec
      // 0e5: astore 9
      // 0e7: invokestatic kotlin/collections/CollectionsKt.emptyList ()Ljava/util/List;
      // 0ea: astore 12
      // 0ec: aload 12
      // 0ee: invokeinterface java/util/List.isEmpty ()Z 1
      // 0f3: ifeq 0fa
      // 0f6: ldc_w "Nothing to export yet."
      // 0f9: areturn
      // 0fa: new java/io/File
      // 0fd: dup
      // 0fe: aload 0
      // 0ff: getfield com/starpilot/starview/LaneShots.ctx Landroid/content/Context;
      // 102: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 105: ldc_w "lane_exported.txt"
      // 108: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 10b: astore 13
      // 10d: aload 13
      // 10f: aconst_null
      // 110: bipush 1
      // 111: aconst_null
      // 112: invokestatic kotlin/io/FilesKt.readLines$default (Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/util/List;
      // 115: checkcast java/lang/Iterable
      // 118: invokestatic kotlin/collections/CollectionsKt.toHashSet (Ljava/lang/Iterable;)Ljava/util/HashSet;
      // 11b: astore 9
      // 11d: goto 12b
      // 120: astore 9
      // 122: new java/util/HashSet
      // 125: dup
      // 126: invokespecial java/util/HashSet.<init> ()V
      // 129: astore 9
      // 12b: aload 9
      // 12d: astore 10
      // 12f: new java/util/HashMap
      // 132: dup
      // 133: invokespecial java/util/HashMap.<init> ()V
      // 136: astore 14
      // 138: aload 0
      // 139: getfield com/starpilot/starview/LaneShots.reviews Ljava/io/File;
      // 13c: aconst_null
      // 13d: bipush 1
      // 13e: aconst_null
      // 13f: invokestatic kotlin/io/FilesKt.readLines$default (Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/util/List;
      // 142: checkcast java/lang/Iterable
      // 145: astore 9
      // 147: aload 9
      // 149: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 14e: astore 15
      // 150: aload 15
      // 152: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 157: ifeq 1a1
      // 15a: aload 15
      // 15c: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 161: checkcast java/lang/String
      // 164: checkcast java/lang/CharSequence
      // 167: bipush 1
      // 168: anewarray 502
      // 16b: dup
      // 16c: bipush 0
      // 16d: ldc_w "\t"
      // 170: aastore
      // 171: bipush 0
      // 172: bipush 0
      // 173: bipush 6
      // 175: aconst_null
      // 176: invokestatic kotlin/text/StringsKt.split$default (Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;
      // 179: astore 16
      // 17b: aload 16
      // 17d: invokeinterface java/util/List.size ()I 1
      // 182: bipush 2
      // 183: if_icmplt 19e
      // 186: aload 14
      // 188: checkcast java/util/Map
      // 18b: aload 16
      // 18d: bipush 0
      // 18e: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 193: aload 16
      // 195: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 19a: pop
      // 19b: goto 19e
      // 19e: goto 150
      // 1a1: goto 1a6
      // 1a4: astore 9
      // 1a6: new java/lang/StringBuilder
      // 1a9: dup
      // 1aa: ldc_w "picture,time_ms,drive,why,street_id,street,card_lane,card_total,card_conf,your_mark,fix_road_lanes,fix_lane,fix_extra_left,fix_extra_right,fix_kind,fix_way,fix_other_way_lanes,road_file,wide_file,caption,skip_why,fix_kind_right,map_ramps,model_guess\n"
      // 1ad: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 1b0: astore 32
      // 1b2: aload 12
      // 1b4: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 1b9: astore 15
      // 1bb: aload 15
      // 1bd: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1c2: ifeq 661
      // 1c5: aload 15
      // 1c7: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 1cc: checkcast java/util/List
      // 1cf: astore 33
      // 1d1: bipush 8
      // 1d3: aload 33
      // 1d5: invokeinterface java/util/List.size ()I 1
      // 1da: if_icmpge 1e2
      // 1dd: bipush 1
      // 1de: istore 2
      // 1df: goto 1e4
      // 1e2: bipush 0
      // 1e3: istore 2
      // 1e4: iload 2
      // 1e5: ifeq 1f6
      // 1e8: aload 33
      // 1ea: bipush 8
      // 1ec: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 1f1: astore 9
      // 1f3: goto 1fb
      // 1f6: ldc_w "0,0,0"
      // 1f9: astore 9
      // 1fb: aload 9
      // 1fd: checkcast java/lang/CharSequence
      // 200: bipush 1
      // 201: anewarray 502
      // 204: dup
      // 205: bipush 0
      // 206: ldc_w ","
      // 209: aastore
      // 20a: bipush 0
      // 20b: bipush 0
      // 20c: bipush 6
      // 20e: aconst_null
      // 20f: invokestatic kotlin/text/StringsKt.split$default (Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;
      // 212: astore 19
      // 214: aload 14
      // 216: aload 33
      // 218: bipush 0
      // 219: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 21e: invokevirtual java/util/HashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 221: checkcast java/util/List
      // 224: astore 29
      // 226: aload 29
      // 228: ifnull 241
      // 22b: aload 29
      // 22d: bipush 1
      // 22e: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 231: ldc_w "-1"
      // 234: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 237: ifeq 241
      // 23a: aload 29
      // 23c: astore 9
      // 23e: goto 257
      // 241: aload 29
      // 243: ifnull 254
      // 246: aload 29
      // 248: checkcast java/lang/Iterable
      // 24b: bipush 2
      // 24c: invokestatic kotlin/collections/CollectionsKt.take (Ljava/lang/Iterable;I)Ljava/util/List;
      // 24f: astore 9
      // 251: goto 257
      // 254: aconst_null
      // 255: astore 9
      // 257: aload 33
      // 259: bipush 0
      // 25a: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 25f: checkcast java/lang/String
      // 262: invokestatic com/starpilot/starview/LaneShots.export$q (Ljava/lang/String;)Ljava/lang/String;
      // 265: astore 35
      // 267: aload 33
      // 269: bipush 2
      // 26a: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 26f: astore 34
      // 271: bipush 5
      // 272: aload 33
      // 274: invokeinterface java/util/List.size ()I 1
      // 279: if_icmpge 281
      // 27c: bipush 1
      // 27d: istore 2
      // 27e: goto 283
      // 281: bipush 0
      // 282: istore 2
      // 283: iload 2
      // 284: ifeq 294
      // 287: aload 33
      // 289: bipush 5
      // 28a: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 28f: astore 16
      // 291: goto 299
      // 294: ldc_w ""
      // 297: astore 16
      // 299: aload 16
      // 29b: checkcast java/lang/String
      // 29e: invokestatic com/starpilot/starview/LaneShots.export$q (Ljava/lang/String;)Ljava/lang/String;
      // 2a1: astore 36
      // 2a3: aload 33
      // 2a5: bipush 3
      // 2a6: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 2ab: astore 37
      // 2ad: bipush 6
      // 2af: aload 33
      // 2b1: invokeinterface java/util/List.size ()I 1
      // 2b6: if_icmpge 2be
      // 2b9: bipush 1
      // 2ba: istore 2
      // 2bb: goto 2c0
      // 2be: bipush 0
      // 2bf: istore 2
      // 2c0: iload 2
      // 2c1: ifeq 2d2
      // 2c4: aload 33
      // 2c6: bipush 6
      // 2c8: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 2cd: astore 16
      // 2cf: goto 2d7
      // 2d2: ldc_w "0"
      // 2d5: astore 16
      // 2d7: bipush 7
      // 2d9: aload 33
      // 2db: invokeinterface java/util/List.size ()I 1
      // 2e0: if_icmpge 2e8
      // 2e3: bipush 1
      // 2e4: istore 2
      // 2e5: goto 2ea
      // 2e8: bipush 0
      // 2e9: istore 2
      // 2ea: iload 2
      // 2eb: ifeq 2fc
      // 2ee: aload 33
      // 2f0: bipush 7
      // 2f2: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 2f7: astore 17
      // 2f9: goto 301
      // 2fc: ldc_w ""
      // 2ff: astore 17
      // 301: aload 17
      // 303: checkcast java/lang/String
      // 306: invokestatic com/starpilot/starview/LaneShots.export$q (Ljava/lang/String;)Ljava/lang/String;
      // 309: astore 38
      // 30b: aload 19
      // 30d: invokeinterface java/util/List.size ()I 1
      // 312: ifle 31a
      // 315: bipush 1
      // 316: istore 2
      // 317: goto 31c
      // 31a: bipush 0
      // 31b: istore 2
      // 31c: iload 2
      // 31d: ifeq 32d
      // 320: aload 19
      // 322: bipush 0
      // 323: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 328: astore 17
      // 32a: goto 332
      // 32d: ldc_w "0"
      // 330: astore 17
      // 332: bipush 1
      // 333: aload 19
      // 335: invokeinterface java/util/List.size ()I 1
      // 33a: if_icmpge 342
      // 33d: bipush 1
      // 33e: istore 2
      // 33f: goto 344
      // 342: bipush 0
      // 343: istore 2
      // 344: iload 2
      // 345: ifeq 355
      // 348: aload 19
      // 34a: bipush 1
      // 34b: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 350: astore 18
      // 352: goto 35a
      // 355: ldc_w "0"
      // 358: astore 18
      // 35a: bipush 2
      // 35b: aload 19
      // 35d: invokeinterface java/util/List.size ()I 1
      // 362: if_icmpge 36a
      // 365: bipush 1
      // 366: istore 2
      // 367: goto 36c
      // 36a: bipush 0
      // 36b: istore 2
      // 36c: iload 2
      // 36d: ifeq 37d
      // 370: aload 19
      // 372: bipush 2
      // 373: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 378: astore 19
      // 37a: goto 382
      // 37d: ldc_w "0"
      // 380: astore 19
      // 382: aload 9
      // 384: ifnull 39b
      // 387: aload 9
      // 389: bipush 1
      // 38a: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 38d: checkcast java/lang/String
      // 390: astore 21
      // 392: aload 21
      // 394: astore 20
      // 396: aload 21
      // 398: ifnonnull 3a0
      // 39b: ldc_w ""
      // 39e: astore 20
      // 3a0: aload 9
      // 3a2: ifnull 3b9
      // 3a5: aload 9
      // 3a7: bipush 2
      // 3a8: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 3ab: checkcast java/lang/String
      // 3ae: astore 22
      // 3b0: aload 22
      // 3b2: astore 21
      // 3b4: aload 22
      // 3b6: ifnonnull 3be
      // 3b9: ldc_w ""
      // 3bc: astore 21
      // 3be: aload 9
      // 3c0: ifnull 3d7
      // 3c3: aload 9
      // 3c5: bipush 3
      // 3c6: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 3c9: checkcast java/lang/String
      // 3cc: astore 23
      // 3ce: aload 23
      // 3d0: astore 22
      // 3d2: aload 23
      // 3d4: ifnonnull 3dc
      // 3d7: ldc_w ""
      // 3da: astore 22
      // 3dc: aload 9
      // 3de: ifnull 3f5
      // 3e1: aload 9
      // 3e3: bipush 4
      // 3e4: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 3e7: checkcast java/lang/String
      // 3ea: astore 24
      // 3ec: aload 24
      // 3ee: astore 23
      // 3f0: aload 24
      // 3f2: ifnonnull 3fa
      // 3f5: ldc_w ""
      // 3f8: astore 23
      // 3fa: aload 9
      // 3fc: ifnull 416
      // 3ff: aload 9
      // 401: bipush 5
      // 402: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 405: checkcast java/lang/String
      // 408: astore 25
      // 40a: aload 25
      // 40c: astore 24
      // 40e: aload 25
      // 410: ifnonnull 41b
      // 413: goto 416
      // 416: ldc_w ""
      // 419: astore 24
      // 41b: aload 9
      // 41d: ifnull 435
      // 420: aload 9
      // 422: bipush 6
      // 424: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 427: checkcast java/lang/String
      // 42a: astore 26
      // 42c: aload 26
      // 42e: astore 25
      // 430: aload 26
      // 432: ifnonnull 43a
      // 435: ldc_w ""
      // 438: astore 25
      // 43a: aload 9
      // 43c: ifnull 454
      // 43f: aload 9
      // 441: bipush 7
      // 443: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 446: checkcast java/lang/String
      // 449: astore 27
      // 44b: aload 27
      // 44d: astore 26
      // 44f: aload 27
      // 451: ifnonnull 459
      // 454: ldc_w ""
      // 457: astore 26
      // 459: aload 9
      // 45b: ifnull 473
      // 45e: aload 9
      // 460: bipush 8
      // 462: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 465: checkcast java/lang/String
      // 468: astore 28
      // 46a: aload 28
      // 46c: astore 27
      // 46e: aload 28
      // 470: ifnonnull 478
      // 473: ldc_w ""
      // 476: astore 27
      // 478: bipush 9
      // 47a: aload 33
      // 47c: invokeinterface java/util/List.size ()I 1
      // 481: if_icmpge 489
      // 484: bipush 1
      // 485: istore 2
      // 486: goto 48b
      // 489: bipush 0
      // 48a: istore 2
      // 48b: iload 2
      // 48c: ifeq 49d
      // 48f: aload 33
      // 491: bipush 9
      // 493: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 498: astore 28
      // 49a: goto 4a2
      // 49d: ldc_w ""
      // 4a0: astore 28
      // 4a2: aload 28
      // 4a4: checkcast java/lang/String
      // 4a7: invokestatic com/starpilot/starview/LaneShots.export$q (Ljava/lang/String;)Ljava/lang/String;
      // 4aa: astore 39
      // 4ac: bipush 10
      // 4ae: aload 33
      // 4b0: invokeinterface java/util/List.size ()I 1
      // 4b5: if_icmpge 4bd
      // 4b8: bipush 1
      // 4b9: istore 2
      // 4ba: goto 4bf
      // 4bd: bipush 0
      // 4be: istore 2
      // 4bf: iload 2
      // 4c0: ifeq 4d1
      // 4c3: aload 33
      // 4c5: bipush 10
      // 4c7: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 4cc: astore 28
      // 4ce: goto 4d6
      // 4d1: ldc_w ""
      // 4d4: astore 28
      // 4d6: aload 28
      // 4d8: checkcast java/lang/String
      // 4db: invokestatic com/starpilot/starview/LaneShots.export$q (Ljava/lang/String;)Ljava/lang/String;
      // 4de: astore 41
      // 4e0: aload 33
      // 4e2: bipush 4
      // 4e3: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 4e8: checkcast java/lang/String
      // 4eb: invokestatic com/starpilot/starview/LaneShots.export$q (Ljava/lang/String;)Ljava/lang/String;
      // 4ee: astore 40
      // 4f0: aload 29
      // 4f2: ifnull 503
      // 4f5: aload 29
      // 4f7: bipush 1
      // 4f8: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 4fb: checkcast java/lang/String
      // 4fe: astore 28
      // 500: goto 506
      // 503: aconst_null
      // 504: astore 28
      // 506: aload 28
      // 508: ldc_w "0"
      // 50b: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 50e: ifeq 525
      // 511: aload 29
      // 513: bipush 2
      // 514: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 517: checkcast java/lang/String
      // 51a: astore 29
      // 51c: aload 29
      // 51e: astore 28
      // 520: aload 29
      // 522: ifnonnull 52a
      // 525: ldc_w ""
      // 528: astore 28
      // 52a: aload 9
      // 52c: ifnull 544
      // 52f: aload 9
      // 531: bipush 9
      // 533: invokestatic kotlin/collections/CollectionsKt.getOrNull (Ljava/util/List;I)Ljava/lang/Object;
      // 536: checkcast java/lang/String
      // 539: astore 29
      // 53b: aload 29
      // 53d: astore 9
      // 53f: aload 29
      // 541: ifnonnull 549
      // 544: ldc_w ""
      // 547: astore 9
      // 549: bipush 11
      // 54b: aload 33
      // 54d: invokeinterface java/util/List.size ()I 1
      // 552: if_icmpge 55a
      // 555: bipush 1
      // 556: istore 2
      // 557: goto 55c
      // 55a: bipush 0
      // 55b: istore 2
      // 55c: iload 2
      // 55d: ifeq 56e
      // 560: aload 33
      // 562: bipush 11
      // 564: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 569: astore 29
      // 56b: goto 573
      // 56e: ldc_w ""
      // 571: astore 29
      // 573: aload 29
      // 575: checkcast java/lang/String
      // 578: invokestatic com/starpilot/starview/LaneShots.export$q (Ljava/lang/String;)Ljava/lang/String;
      // 57b: astore 42
      // 57d: bipush 12
      // 57f: aload 33
      // 581: invokeinterface java/util/List.size ()I 1
      // 586: if_icmpge 58e
      // 589: bipush 1
      // 58a: istore 2
      // 58b: goto 590
      // 58e: bipush 0
      // 58f: istore 2
      // 590: iload 2
      // 591: ifeq 5a2
      // 594: aload 33
      // 596: bipush 12
      // 598: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 59d: astore 29
      // 59f: goto 5a7
      // 5a2: ldc_w ""
      // 5a5: astore 29
      // 5a7: aload 32
      // 5a9: bipush 24
      // 5ab: anewarray 502
      // 5ae: dup
      // 5af: bipush 0
      // 5b0: aload 35
      // 5b2: aastore
      // 5b3: dup
      // 5b4: bipush 1
      // 5b5: aload 34
      // 5b7: aastore
      // 5b8: dup
      // 5b9: bipush 2
      // 5ba: aload 36
      // 5bc: aastore
      // 5bd: dup
      // 5be: bipush 3
      // 5bf: aload 37
      // 5c1: aastore
      // 5c2: dup
      // 5c3: bipush 4
      // 5c4: aload 16
      // 5c6: aastore
      // 5c7: dup
      // 5c8: bipush 5
      // 5c9: aload 38
      // 5cb: aastore
      // 5cc: dup
      // 5cd: bipush 6
      // 5cf: aload 17
      // 5d1: aastore
      // 5d2: dup
      // 5d3: bipush 7
      // 5d5: aload 18
      // 5d7: aastore
      // 5d8: dup
      // 5d9: bipush 8
      // 5db: aload 19
      // 5dd: aastore
      // 5de: dup
      // 5df: bipush 9
      // 5e1: aload 20
      // 5e3: aastore
      // 5e4: dup
      // 5e5: bipush 10
      // 5e7: aload 21
      // 5e9: aastore
      // 5ea: dup
      // 5eb: bipush 11
      // 5ed: aload 22
      // 5ef: aastore
      // 5f0: dup
      // 5f1: bipush 12
      // 5f3: aload 23
      // 5f5: aastore
      // 5f6: dup
      // 5f7: bipush 13
      // 5f9: aload 24
      // 5fb: aastore
      // 5fc: dup
      // 5fd: bipush 14
      // 5ff: aload 25
      // 601: aastore
      // 602: dup
      // 603: bipush 15
      // 605: aload 26
      // 607: aastore
      // 608: dup
      // 609: bipush 16
      // 60b: aload 27
      // 60d: aastore
      // 60e: dup
      // 60f: bipush 17
      // 611: aload 39
      // 613: aastore
      // 614: dup
      // 615: bipush 18
      // 617: aload 41
      // 619: aastore
      // 61a: dup
      // 61b: bipush 19
      // 61d: aload 40
      // 61f: aastore
      // 620: dup
      // 621: bipush 20
      // 623: aload 28
      // 625: aastore
      // 626: dup
      // 627: bipush 21
      // 629: aload 9
      // 62b: aastore
      // 62c: dup
      // 62d: bipush 22
      // 62f: aload 42
      // 631: aastore
      // 632: dup
      // 633: bipush 23
      // 635: aload 29
      // 637: checkcast java/lang/String
      // 63a: invokestatic com/starpilot/starview/LaneShots.export$q (Ljava/lang/String;)Ljava/lang/String;
      // 63d: aastore
      // 63e: invokestatic kotlin/collections/CollectionsKt.listOf ([Ljava/lang/Object;)Ljava/util/List;
      // 641: checkcast java/lang/Iterable
      // 644: ldc_w ","
      // 647: checkcast java/lang/CharSequence
      // 64a: aconst_null
      // 64b: aconst_null
      // 64c: bipush 0
      // 64d: aconst_null
      // 64e: aconst_null
      // 64f: bipush 62
      // 651: aconst_null
      // 652: invokestatic kotlin/collections/CollectionsKt.joinToString$default (Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;
      // 655: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 658: bipush 10
      // 65a: invokevirtual java/lang/StringBuilder.append (C)Ljava/lang/StringBuilder;
      // 65d: pop
      // 65e: goto 1bb
      // 661: aload 12
      // 663: checkcast java/lang/Iterable
      // 666: astore 9
      // 668: bipush 0
      // 669: istore 8
      // 66b: new java/util/ArrayList
      // 66e: dup
      // 66f: invokespecial java/util/ArrayList.<init> ()V
      // 672: checkcast java/util/Collection
      // 675: astore 15
      // 677: aload 9
      // 679: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 67e: astore 14
      // 680: aload 14
      // 682: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 687: ifeq 6b3
      // 68a: aload 14
      // 68c: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 691: astore 16
      // 693: aload 10
      // 695: aload 16
      // 697: checkcast java/util/List
      // 69a: bipush 0
      // 69b: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 6a0: invokevirtual java/util/HashSet.contains (Ljava/lang/Object;)Z
      // 6a3: ifne 6b0
      // 6a6: aload 15
      // 6a8: aload 16
      // 6aa: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 6af: pop
      // 6b0: goto 680
      // 6b3: aload 15
      // 6b5: checkcast java/util/List
      // 6b8: astore 9
      // 6ba: aload 9
      // 6bc: checkcast java/lang/Iterable
      // 6bf: astore 14
      // 6c1: bipush 0
      // 6c2: istore 8
      // 6c4: new java/util/ArrayList
      // 6c7: dup
      // 6c8: invokespecial java/util/ArrayList.<init> ()V
      // 6cb: checkcast java/util/Collection
      // 6ce: astore 17
      // 6d0: aload 14
      // 6d2: astore 9
      // 6d4: aload 9
      // 6d6: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 6db: astore 18
      // 6dd: aload 18
      // 6df: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 6e4: ifeq 822
      // 6e7: aload 18
      // 6e9: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 6ee: checkcast java/util/List
      // 6f1: astore 19
      // 6f3: bipush 9
      // 6f5: aload 19
      // 6f7: invokeinterface java/util/List.size ()I 1
      // 6fc: if_icmpge 704
      // 6ff: bipush 1
      // 700: istore 2
      // 701: goto 706
      // 704: bipush 0
      // 705: istore 2
      // 706: iload 2
      // 707: ifeq 718
      // 70a: aload 19
      // 70c: bipush 9
      // 70e: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 713: astore 15
      // 715: goto 71d
      // 718: ldc_w ""
      // 71b: astore 15
      // 71d: bipush 10
      // 71f: aload 19
      // 721: invokeinterface java/util/List.size ()I 1
      // 726: if_icmpge 72e
      // 729: bipush 1
      // 72a: istore 2
      // 72b: goto 730
      // 72e: bipush 0
      // 72f: istore 2
      // 730: iload 2
      // 731: ifeq 742
      // 734: aload 19
      // 736: bipush 10
      // 738: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 73d: astore 16
      // 73f: goto 747
      // 742: ldc_w ""
      // 745: astore 16
      // 747: bipush 2
      // 748: anewarray 502
      // 74b: dup
      // 74c: bipush 0
      // 74d: aload 15
      // 74f: aastore
      // 750: dup
      // 751: bipush 1
      // 752: aload 16
      // 754: aastore
      // 755: invokestatic kotlin/collections/CollectionsKt.listOf ([Ljava/lang/Object;)Ljava/util/List;
      // 758: checkcast java/lang/Iterable
      // 75b: astore 15
      // 75d: new java/util/ArrayList
      // 760: dup
      // 761: invokespecial java/util/ArrayList.<init> ()V
      // 764: checkcast java/util/Collection
      // 767: astore 16
      // 769: aload 15
      // 76b: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 770: astore 20
      // 772: aload 20
      // 774: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 779: ifeq 7ad
      // 77c: aload 20
      // 77e: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 783: astore 21
      // 785: aload 21
      // 787: checkcast java/lang/String
      // 78a: checkcast java/lang/CharSequence
      // 78d: invokeinterface java/lang/CharSequence.length ()I 1
      // 792: ifle 79a
      // 795: bipush 1
      // 796: istore 2
      // 797: goto 79c
      // 79a: bipush 0
      // 79b: istore 2
      // 79c: iload 2
      // 79d: ifeq 7aa
      // 7a0: aload 16
      // 7a2: aload 21
      // 7a4: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 7a9: pop
      // 7aa: goto 772
      // 7ad: aload 16
      // 7af: checkcast java/util/List
      // 7b2: astore 15
      // 7b4: aload 15
      // 7b6: checkcast java/lang/Iterable
      // 7b9: astore 16
      // 7bb: new java/util/ArrayList
      // 7be: dup
      // 7bf: aload 16
      // 7c1: bipush 10
      // 7c3: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 7c6: invokespecial java/util/ArrayList.<init> (I)V
      // 7c9: checkcast java/util/Collection
      // 7cc: astore 20
      // 7ce: aload 16
      // 7d0: astore 15
      // 7d2: aload 15
      // 7d4: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 7d9: astore 21
      // 7db: aload 21
      // 7dd: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 7e2: ifeq 809
      // 7e5: aload 21
      // 7e7: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 7ec: checkcast java/lang/String
      // 7ef: astore 22
      // 7f1: aload 20
      // 7f3: aload 19
      // 7f5: bipush 0
      // 7f6: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 7fb: aload 22
      // 7fd: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 800: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 805: pop
      // 806: goto 7db
      // 809: aload 20
      // 80b: checkcast java/util/List
      // 80e: astore 15
      // 810: aload 15
      // 812: checkcast java/lang/Iterable
      // 815: astore 15
      // 817: aload 17
      // 819: aload 15
      // 81b: invokestatic kotlin/collections/CollectionsKt.addAll (Ljava/util/Collection;Ljava/lang/Iterable;)Z
      // 81e: pop
      // 81f: goto 6dd
      // 822: aload 17
      // 824: checkcast java/util/List
      // 827: astore 19
      // 829: new java/text/SimpleDateFormat
      // 82c: dup
      // 82d: ldc_w "yyyy-MM-dd_HH-mm"
      // 830: getstatic java/util/Locale.US Ljava/util/Locale;
      // 833: invokespecial java/text/SimpleDateFormat.<init> (Ljava/lang/String;Ljava/util/Locale;)V
      // 836: new java/util/Date
      // 839: dup
      // 83a: invokespecial java/util/Date.<init> ()V
      // 83d: invokevirtual java/text/SimpleDateFormat.format (Ljava/util/Date;)Ljava/lang/String;
      // 840: astore 15
      // 842: bipush 0
      // 843: istore 5
      // 845: bipush 0
      // 846: istore 3
      // 847: bipush 0
      // 848: istore 2
      // 849: ldc2_w 25165824
      // 84c: lstore 6
      // 84e: new java/util/ArrayList
      // 851: dup
      // 852: invokespecial java/util/ArrayList.<init> ()V
      // 855: astore 17
      // 857: aload 32
      // 859: astore 14
      // 85b: aload 31
      // 85d: astore 9
      // 85f: aload 30
      // 861: astore 10
      // 863: iinc 5 1
      // 866: new java/lang/StringBuilder
      // 869: dup
      // 86a: invokespecial java/lang/StringBuilder.<init> ()V
      // 86d: ldc_w "lane_data_"
      // 870: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 873: aload 15
      // 875: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 878: ldc_w "_part"
      // 87b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 87e: iload 5
      // 880: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 883: ldc_w ".zip"
      // 886: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 889: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 88c: astore 18
      // 88e: new android/content/ContentValues
      // 891: dup
      // 892: invokespecial android/content/ContentValues.<init> ()V
      // 895: astore 16
      // 897: aload 16
      // 899: ldc_w "_display_name"
      // 89c: aload 18
      // 89e: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 8a1: aload 16
      // 8a3: ldc_w "mime_type"
      // 8a6: ldc_w "application/zip"
      // 8a9: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 8ac: aload 16
      // 8ae: ldc_w "relative_path"
      // 8b1: ldc_w "Download/StarView/Lane data"
      // 8b4: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 8b7: aload 0
      // 8b8: getfield com/starpilot/starview/LaneShots.ctx Landroid/content/Context;
      // 8bb: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 8be: getstatic android/provider/MediaStore$Downloads.EXTERNAL_CONTENT_URI Landroid/net/Uri;
      // 8c1: aload 16
      // 8c3: invokevirtual android/content/ContentResolver.insert (Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
      // 8c6: astore 16
      // 8c8: aload 16
      // 8ca: ifnonnull 8e3
      // 8cd: new java/lang/StringBuilder
      // 8d0: dup
      // 8d1: invokespecial java/lang/StringBuilder.<init> ()V
      // 8d4: ldc_w "Couldn't create "
      // 8d7: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 8da: aload 18
      // 8dc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 8df: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 8e2: areturn
      // 8e3: new kotlin/jvm/internal/Ref$LongRef
      // 8e6: dup
      // 8e7: invokespecial kotlin/jvm/internal/Ref$LongRef.<init> ()V
      // 8ea: astore 20
      // 8ec: aload 0
      // 8ed: getfield com/starpilot/starview/LaneShots.ctx Landroid/content/Context;
      // 8f0: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 8f3: aload 16
      // 8f5: invokevirtual android/content/ContentResolver.openOutputStream (Landroid/net/Uri;)Ljava/io/OutputStream;
      // 8f8: astore 16
      // 8fa: aload 16
      // 8fc: ifnull af6
      // 8ff: aload 16
      // 901: checkcast java/io/Closeable
      // 904: astore 16
      // 906: aload 16
      // 908: checkcast java/io/OutputStream
      // 90b: astore 18
      // 90d: new java/util/zip/ZipOutputStream
      // 910: astore 21
      // 912: aload 21
      // 914: aload 18
      // 916: invokespecial java/util/zip/ZipOutputStream.<init> (Ljava/io/OutputStream;)V
      // 919: aload 21
      // 91b: checkcast java/io/Closeable
      // 91e: astore 18
      // 920: aload 18
      // 922: checkcast java/util/zip/ZipOutputStream
      // 925: astore 21
      // 927: iload 5
      // 929: bipush 1
      // 92a: if_icmpne 9b5
      // 92d: aload 14
      // 92f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 932: astore 22
      // 934: aload 22
      // 936: ldc_w "toString(...)"
      // 939: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 93c: aload 22
      // 93e: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 941: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 944: astore 22
      // 946: aload 22
      // 948: ldc_w "getBytes(...)"
      // 94b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 94e: aload 21
      // 950: aload 20
      // 952: ldc_w "labels.csv"
      // 955: aload 22
      // 957: invokestatic com/starpilot/starview/LaneShots.export$lambda$72$lambda$71$put (Ljava/util/zip/ZipOutputStream;Lkotlin/jvm/internal/Ref$LongRef;Ljava/lang/String;[B)V
      // 95a: new java/io/File
      // 95d: astore 22
      // 95f: aload 22
      // 961: aload 0
      // 962: getfield com/starpilot/starview/LaneShots.ctx Landroid/content/Context;
      // 965: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 968: aload 9
      // 96a: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 96d: aload 21
      // 96f: aload 20
      // 971: aload 9
      // 973: aload 22
      // 975: invokestatic kotlin/io/FilesKt.readBytes (Ljava/io/File;)[B
      // 978: invokestatic com/starpilot/starview/LaneShots.export$lambda$72$lambda$71$put (Ljava/util/zip/ZipOutputStream;Lkotlin/jvm/internal/Ref$LongRef;Ljava/lang/String;[B)V
      // 97b: goto 980
      // 97e: astore 22
      // 980: new java/io/File
      // 983: astore 22
      // 985: aload 22
      // 987: aload 0
      // 988: getfield com/starpilot/starview/LaneShots.ctx Landroid/content/Context;
      // 98b: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 98e: aload 10
      // 990: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 993: aload 21
      // 995: aload 20
      // 997: aload 10
      // 999: aload 22
      // 99b: invokestatic kotlin/io/FilesKt.readBytes (Ljava/io/File;)[B
      // 99e: invokestatic com/starpilot/starview/LaneShots.export$lambda$72$lambda$71$put (Ljava/util/zip/ZipOutputStream;Lkotlin/jvm/internal/Ref$LongRef;Ljava/lang/String;[B)V
      // 9a1: goto 9b5
      // 9a4: astore 22
      // 9a6: goto 9b5
      // 9a9: astore 1
      // 9aa: iload 3
      // 9ab: istore 2
      // 9ac: goto ad3
      // 9af: astore 1
      // 9b0: iload 3
      // 9b1: istore 2
      // 9b2: goto ad3
      // 9b5: iload 2
      // 9b6: istore 4
      // 9b8: iload 3
      // 9b9: istore 2
      // 9ba: iload 4
      // 9bc: istore 3
      // 9bd: iload 3
      // 9be: aload 19
      // 9c0: invokeinterface java/util/List.size ()I 1
      // 9c5: if_icmpge aa6
      // 9c8: aload 20
      // 9ca: getfield kotlin/jvm/internal/Ref$LongRef.element J
      // 9cd: lload 6
      // 9cf: lcmp
      // 9d0: ifge aa6
      // 9d3: aload 19
      // 9d5: iload 3
      // 9d6: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 9db: checkcast kotlin/Pair
      // 9de: astore 23
      // 9e0: aload 23
      // 9e2: invokevirtual kotlin/Pair.component1 ()Ljava/lang/Object;
      // 9e5: checkcast java/lang/String
      // 9e8: astore 22
      // 9ea: aload 23
      // 9ec: invokevirtual kotlin/Pair.component2 ()Ljava/lang/Object;
      // 9ef: checkcast java/lang/String
      // 9f2: astore 23
      // 9f4: iinc 3 1
      // 9f7: new java/lang/StringBuilder
      // 9fa: astore 24
      // 9fc: aload 24
      // 9fe: invokespecial java/lang/StringBuilder.<init> ()V
      // a01: aload 24
      // a03: ldc_w "frames/"
      // a06: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // a09: aload 23
      // a0b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // a0e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // a11: astore 24
      // a13: new java/io/File
      // a16: astore 25
      // a18: aload 25
      // a1a: aload 0
      // a1b: invokevirtual com/starpilot/starview/LaneShots.getCleanDir ()Ljava/io/File;
      // a1e: aload 23
      // a20: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // a23: aload 21
      // a25: aload 20
      // a27: aload 24
      // a29: aload 25
      // a2b: invokestatic kotlin/io/FilesKt.readBytes (Ljava/io/File;)[B
      // a2e: invokestatic com/starpilot/starview/LaneShots.export$lambda$72$lambda$71$put (Ljava/util/zip/ZipOutputStream;Lkotlin/jvm/internal/Ref$LongRef;Ljava/lang/String;[B)V
      // a31: iinc 2 1
      // a34: goto a4f
      // a37: astore 1
      // a38: goto ad3
      // a3b: astore 23
      // a3d: goto a4f
      // a40: astore 1
      // a41: goto ad3
      // a44: astore 23
      // a46: goto a4f
      // a49: astore 1
      // a4a: goto ad3
      // a4d: astore 23
      // a4f: aload 17
      // a51: checkcast java/util/List
      // a54: invokestatic kotlin/collections/CollectionsKt.lastOrNull (Ljava/util/List;)Ljava/lang/Object;
      // a57: aload 22
      // a59: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // a5c: ifne a67
      // a5f: aload 17
      // a61: aload 22
      // a63: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // a66: pop
      // a67: iload 2
      // a68: bipush 50
      // a6a: irem
      // a6b: ifne a9f
      // a6e: new java/lang/StringBuilder
      // a71: astore 22
      // a73: aload 22
      // a75: invokespecial java/lang/StringBuilder.<init> ()V
      // a78: aload 1
      // a79: aload 22
      // a7b: ldc_w "part "
      // a7e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // a81: iload 5
      // a83: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // a86: ldc_w ": "
      // a89: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // a8c: iload 2
      // a8d: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // a90: ldc_w " frames…"
      // a93: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // a96: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // a99: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // a9e: pop
      // a9f: goto 9bd
      // aa2: astore 1
      // aa3: goto ad3
      // aa6: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // aa9: astore 20
      // aab: aload 18
      // aad: aconst_null
      // aae: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // ab1: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // ab4: astore 18
      // ab6: aload 16
      // ab8: aconst_null
      // ab9: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // abc: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // abf: astore 16
      // ac1: goto afe
      // ac4: astore 1
      // ac5: goto ae9
      // ac8: astore 1
      // ac9: goto ad3
      // acc: astore 1
      // acd: goto ad3
      // ad0: astore 1
      // ad1: iload 3
      // ad2: istore 2
      // ad3: aload 1
      // ad4: athrow
      // ad5: astore 9
      // ad7: aload 18
      // ad9: aload 1
      // ada: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // add: aload 9
      // adf: athrow
      // ae0: astore 1
      // ae1: goto ae9
      // ae4: astore 1
      // ae5: goto ae9
      // ae8: astore 1
      // ae9: aload 1
      // aea: athrow
      // aeb: astore 9
      // aed: aload 16
      // aef: aload 1
      // af0: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // af3: aload 9
      // af5: athrow
      // af6: iload 2
      // af7: istore 4
      // af9: iload 3
      // afa: istore 2
      // afb: iload 4
      // afd: istore 3
      // afe: iload 3
      // aff: aload 19
      // b01: invokeinterface java/util/List.size ()I 1
      // b06: if_icmplt bd5
      // b09: aload 17
      // b0b: checkcast java/lang/Iterable
      // b0e: invokestatic kotlin/collections/CollectionsKt.distinct (Ljava/lang/Iterable;)Ljava/util/List;
      // b11: checkcast java/lang/Iterable
      // b14: aload 11
      // b16: checkcast java/lang/CharSequence
      // b19: aconst_null
      // b1a: aconst_null
      // b1b: bipush 0
      // b1c: aconst_null
      // b1d: aconst_null
      // b1e: bipush 62
      // b20: aconst_null
      // b21: invokestatic kotlin/collections/CollectionsKt.joinToString$default (Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;
      // b24: astore 9
      // b26: aload 17
      // b28: invokevirtual java/util/ArrayList.isEmpty ()Z
      // b2b: ifeq b35
      // b2e: ldc_w ""
      // b31: astore 1
      // b32: goto b38
      // b35: aload 11
      // b37: astore 1
      // b38: new java/lang/StringBuilder
      // b3b: astore 10
      // b3d: aload 10
      // b3f: invokespecial java/lang/StringBuilder.<init> ()V
      // b42: aload 10
      // b44: aload 9
      // b46: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // b49: aload 1
      // b4a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // b4d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // b50: astore 1
      // b51: aload 13
      // b53: aload 1
      // b54: aconst_null
      // b55: bipush 2
      // b56: aconst_null
      // b57: invokestatic kotlin/io/FilesKt.appendText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // b5a: goto b62
      // b5d: astore 1
      // b5e: goto b62
      // b61: astore 1
      // b62: aload 0
      // b63: getfield com/starpilot/starview/LaneShots.ctx Landroid/content/Context;
      // b66: ldc_w "lane_pictures"
      // b69: bipush 0
      // b6a: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // b6d: invokeinterface android/content/SharedPreferences.edit ()Landroid/content/SharedPreferences$Editor; 1
      // b72: ldc_w "exported_at"
      // b75: invokestatic java/lang/System.currentTimeMillis ()J
      // b78: invokeinterface android/content/SharedPreferences$Editor.putLong (Ljava/lang/String;J)Landroid/content/SharedPreferences$Editor; 4
      // b7d: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
      // b82: aload 12
      // b84: invokeinterface java/util/List.size ()I 1
      // b89: istore 3
      // b8a: iload 5
      // b8c: bipush 1
      // b8d: if_icmple b97
      // b90: ldc_w "s"
      // b93: astore 1
      // b94: goto b9b
      // b97: ldc_w ""
      // b9a: astore 1
      // b9b: new java/lang/StringBuilder
      // b9e: dup
      // b9f: invokespecial java/lang/StringBuilder.<init> ()V
      // ba2: ldc_w "Exported "
      // ba5: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // ba8: iload 3
      // ba9: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // bac: ldc_w " labels and "
      // baf: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // bb2: iload 2
      // bb3: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // bb6: ldc_w " new frames in "
      // bb9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // bbc: iload 5
      // bbe: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // bc1: ldc_w " file"
      // bc4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // bc7: aload 1
      // bc8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // bcb: ldc_w " to Download/StarView/Lane data."
      // bce: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // bd1: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // bd4: areturn
      // bd5: iload 3
      // bd6: istore 4
      // bd8: iload 2
      // bd9: istore 3
      // bda: iload 4
      // bdc: istore 2
      // bdd: goto 863
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final Map<String, String> fixes() {
      HashMap var10;
      try {
         var10 = new HashMap();
      } catch (Exception var44) {
         return new HashMap<>();
      }

      Iterator var11;
      try {
         var11 = FilesKt.readLines$default(this.reviews, null, 1, null).iterator();
      } catch (Exception var37) {
         return new HashMap<>();
      }

      while (true) {
         String var7;
         try {
            if (!var11.hasNext()) {
               break;
            }

            var7 = (String & CharSequence)var11.next();
         } catch (Exception var38) {
            return new HashMap<>();
         }

         boolean var6 = false;

         List var14;
         label265: {
            Map var12;
            Object var13;
            try {
               var14 = StringsKt.split$default(var7, new String[]{"\t"}, false, 0, 6, null);
               if (var14.size() < 4 || !Intrinsics.areEqual(var14.get(1), "-1")) {
                  break label265;
               }

               var12 = var10;
               var13 = var14.get(0);
               var52 = StringsKt.toIntOrNull((String)var14.get(2));
            } catch (Exception var43) {
               return new HashMap<>();
            }

            int var1;
            if (var52 != null) {
               try {
                  var1 = var52;
               } catch (Exception var36) {
                  return new HashMap<>();
               }
            } else {
               var1 = 0;
            }

            int var2;
            try {
               var2 = var14.size();
            } catch (Exception var35) {
               return new HashMap<>();
            }

            boolean var45;
            if (4 < var2) {
               var45 = 1;
            } else {
               var45 = 0;
            }

            String var8 = "0";
            if (var45) {
               try {
                  var7 = (String)var14.get(4);
               } catch (Exception var34) {
                  return new HashMap<>();
               }
            } else {
               var7 = "0";
            }

            try {
               var54 = StringsKt.toIntOrNull(var7);
            } catch (Exception var33) {
               return new HashMap<>();
            }

            if (var54 != null) {
               try {
                  var45 = var54;
               } catch (Exception var32) {
                  return new HashMap<>();
               }
            } else {
               var45 = 0;
            }

            boolean var3;
            label242: {
               label241: {
                  try {
                     if (5 < var14.size()) {
                        break label241;
                     }
                  } catch (Exception var42) {
                     return new HashMap<>();
                  }

                  var3 = 0;
                  break label242;
               }

               var3 = 1;
            }

            if (var3) {
               try {
                  var7 = (String)var14.get(5);
               } catch (Exception var31) {
                  return new HashMap<>();
               }
            } else {
               var7 = "0";
            }

            try {
               var56 = StringsKt.toIntOrNull(var7);
            } catch (Exception var30) {
               return new HashMap<>();
            }

            if (var56 != null) {
               try {
                  var3 = var56;
               } catch (Exception var29) {
                  return new HashMap<>();
               }
            } else {
               var3 = 0;
            }

            int var4;
            try {
               var4 = var14.size();
            } catch (Exception var28) {
               return new HashMap<>();
            }

            boolean var48;
            if (6 < var4) {
               var48 = 1;
            } else {
               var48 = 0;
            }

            String var9 = "";
            if (var48) {
               try {
                  var7 = (String)var14.get(6);
               } catch (Exception var27) {
                  return new HashMap<>();
               }
            } else {
               var7 = "";
            }

            String var15;
            try {
               var15 = var7;
               var58 = StringsKt.toIntOrNull((String)var14.get(3));
            } catch (Exception var26) {
               return new HashMap<>();
            }

            if (var58 != null) {
               try {
                  var48 = var58;
               } catch (Exception var25) {
                  return new HashMap<>();
               }
            } else {
               var48 = 0;
            }

            boolean var5;
            label234: {
               label233: {
                  try {
                     if (7 < var14.size()) {
                        break label233;
                     }
                  } catch (Exception var41) {
                     return new HashMap<>();
                  }

                  var5 = 0;
                  break label234;
               }

               var5 = 1;
            }

            if (var5) {
               try {
                  var7 = (String)var14.get(7);
               } catch (Exception var24) {
                  return new HashMap<>();
               }
            } else {
               var7 = "";
            }

            String var16;
            label226: {
               label225: {
                  try {
                     var16 = var7;
                     if (8 < var14.size()) {
                        break label225;
                     }
                  } catch (Exception var40) {
                     return new HashMap<>();
                  }

                  var5 = (boolean)0;
                  break label226;
               }

               var5 = (boolean)1;
            }

            if (var5) {
               try {
                  var7 = (String)var14.get(8);
               } catch (Exception var23) {
                  return new HashMap<>();
               }
            } else {
               var7 = var8;
            }

            try {
               var61 = StringsKt.toIntOrNull(var7);
            } catch (Exception var22) {
               return new HashMap<>();
            }

            if (var61 != null) {
               try {
                  var5 = var61;
               } catch (Exception var21) {
                  return new HashMap<>();
               }
            } else {
               var5 = 0;
            }

            label218: {
               try {
                  if (9 >= var14.size()) {
                     break label218;
                  }
               } catch (Exception var39) {
                  return new HashMap<>();
               }

               var6 = true;
            }

            if (var6) {
               try {
                  var7 = (String)var14.get(9);
               } catch (Exception var20) {
                  return new HashMap<>();
               }
            } else {
               var7 = var9;
            }

            try {
               var8 = var7;
               LaneFixDialog.Result var63 = new LaneFixDialog.Result(var1, var45, var3, var15, var48, false, var16, var5, var8);
               var12.put(var13, var63.text());
               continue;
            } catch (Exception var19) {
               return new HashMap<>();
            }
         }

         try {
            if (var14.size() >= 2) {
               var10.remove(var14.get(0));
            }
         } catch (Exception var18) {
            return new HashMap<>();
         }
      }

      try {
         return var10;
      } catch (Exception var17) {
         return new HashMap<>();
      }
   }

   public final Function0<String> getAnswer() {
      return this.answer;
   }

   public final File getCleanDir() {
      File var2 = this.ctx.getExternalFilesDir(null);
      File var1 = var2;
      if (var2 == null) {
         var1 = this.ctx.getFilesDir();
      }

      return new File(var1, "lane_clean");
   }

   public final Detector getDetector() {
      return this.detector;
   }

   public final String getDriveFolder() {
      return this.driveFolder;
   }

   public final Function0<Pair<Integer, Integer>> getLanes() {
      return this.lanes;
   }

   public final Function0<String> getModelSays() {
      return this.modelSays;
   }

   public final Function0<List<LaneRamps.Ramp>> getRamps() {
      return (Function0<List<LaneRamps.Ramp>>)this.ramps;
   }

   public final Function0<LaneBrain.Road> getRoad() {
      return this.road;
   }

   public final Function0<SurfaceView> getScene() {
      return this.scene;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final List<LaneShots.Shot> list() {
      ArrayList var9;
      Iterator var10;
      try {
         Iterable var46 = FilesKt.readLines$default(this.index, null, 1, null);
         ArrayList var7 = new ArrayList();
         var9 = var7;
         var10 = var46.iterator();
      } catch (Exception var33) {
         return CollectionsKt.emptyList();
      }

      while (true) {
         LaneShots.Shot var55;
         label196: {
            label203: {
               String var11;
               String var12;
               List var15;
               try {
                  if (!var10.hasNext()) {
                     break;
                  }

                  var15 = StringsKt.split$default((String & CharSequence)var10.next(), new String[]{"\t"}, false, 0, 6, null);
                  if (var15.size() < 5) {
                     break label203;
                  }

                  var12 = (String)var15.get(0);
                  var11 = (String)var15.get(1);
                  var47 = StringsKt.toLongOrNull((String)var15.get(2));
               } catch (Exception var39) {
                  return CollectionsKt.emptyList();
               }

               long var4 = 0L;
               long var2;
               if (var47 != null) {
                  try {
                     var2 = var47;
                  } catch (Exception var32) {
                     return CollectionsKt.emptyList();
                  }
               } else {
                  var2 = 0L;
               }

               int var1;
               String var13;
               String var14;
               try {
                  var13 = (String)var15.get(3);
                  var14 = StringsKt.replace$default((String)var15.get(4), " | ", "\n", false, 4, null);
                  var1 = var15.size();
               } catch (Exception var31) {
                  return CollectionsKt.emptyList();
               }

               boolean var40;
               if (5 < var1) {
                  var40 = true;
               } else {
                  var40 = false;
               }

               String var8 = "";
               if (var40) {
                  try {
                     var55 = (LaneShots.Shot)var15.get(5);
                  } catch (Exception var30) {
                     return CollectionsKt.emptyList();
                  }
               } else {
                  var55 = "";
               }

               String var16;
               label184: {
                  label183: {
                     try {
                        var16 = (String)var55;
                        if (6 < var15.size()) {
                           break label183;
                        }
                     } catch (Exception var38) {
                        return CollectionsKt.emptyList();
                     }

                     var40 = false;
                     break label184;
                  }

                  var40 = true;
               }

               if (var40) {
                  try {
                     var55 = (LaneShots.Shot)var15.get(6);
                  } catch (Exception var29) {
                     return CollectionsKt.emptyList();
                  }
               } else {
                  var55 = "0";
               }

               try {
                  var50 = StringsKt.toLongOrNull((String)var55);
               } catch (Exception var28) {
                  return CollectionsKt.emptyList();
               }

               if (var50 != null) {
                  try {
                     var4 = var50;
                  } catch (Exception var27) {
                     return CollectionsKt.emptyList();
                  }
               }

               label176: {
                  label175: {
                     try {
                        if (7 < var15.size()) {
                           break label175;
                        }
                     } catch (Exception var37) {
                        return CollectionsKt.emptyList();
                     }

                     var40 = false;
                     break label176;
                  }

                  var40 = true;
               }

               if (var40) {
                  try {
                     var55 = (LaneShots.Shot)var15.get(7);
                  } catch (Exception var26) {
                     return CollectionsKt.emptyList();
                  }
               } else {
                  var55 = "";
               }

               String var17;
               label168: {
                  label167: {
                     try {
                        var17 = (String)var55;
                        if (11 < var15.size()) {
                           break label167;
                        }
                     } catch (Exception var36) {
                        return CollectionsKt.emptyList();
                     }

                     var40 = false;
                     break label168;
                  }

                  var40 = true;
               }

               if (var40) {
                  try {
                     var55 = (LaneShots.Shot)var15.get(11);
                  } catch (Exception var25) {
                     return CollectionsKt.emptyList();
                  }
               } else {
                  var55 = "";
               }

               String var18;
               label160: {
                  label159: {
                     try {
                        var18 = (String)var55;
                        if (12 < var15.size()) {
                           break label159;
                        }
                     } catch (Exception var35) {
                        return CollectionsKt.emptyList();
                     }

                     var40 = false;
                     break label160;
                  }

                  var40 = true;
               }

               if (var40) {
                  try {
                     var55 = (LaneShots.Shot)var15.get(12);
                  } catch (Exception var24) {
                     return CollectionsKt.emptyList();
                  }
               } else {
                  var55 = "";
               }

               String var19;
               label152: {
                  label151: {
                     try {
                        var19 = (String)var55;
                        if (9 < var15.size()) {
                           break label151;
                        }
                     } catch (Exception var34) {
                        return CollectionsKt.emptyList();
                     }

                     var40 = false;
                     break label152;
                  }

                  var40 = true;
               }

               if (var40) {
                  try {
                     var55 = (LaneShots.Shot)var15.get(9);
                  } catch (Exception var23) {
                     return CollectionsKt.emptyList();
                  }
               } else {
                  var55 = var8;
               }

               try {
                  var8 = (String)var55;
                  var55 = new LaneShots.Shot(var12, var11, var2, var13, var14, var16, var4, var17, var18, var19, var8);
                  break label196;
               } catch (Exception var22) {
                  return CollectionsKt.emptyList();
               }
            }

            var55 = null;
         }

         if (var55 != null) {
            try {
               var9.add(var55);
            } catch (Exception var21) {
               return CollectionsKt.emptyList();
            }
         }
      }

      List var6;
      try {
         var6 = var9;
      } catch (Exception var20) {
         var6 = CollectionsKt.emptyList();
      }

      return var6;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void mark(String var1, int var2, String var3) {
      Intrinsics.checkNotNullParameter(var1, "name");
      Intrinsics.checkNotNullParameter(var3, "why");

      File var5;
      try {
         var5 = this.reviews;
      } catch (Exception var10) {
         return;
      }

      label40: {
         if (var2 == 0) {
            boolean var4;
            label37: {
               label36: {
                  try {
                     if (var3.length() > 0) {
                        break label36;
                     }
                  } catch (Exception var9) {
                     return;
                  }

                  var4 = false;
                  break label37;
               }

               var4 = true;
            }

            if (var4) {
               try {
                  StringBuilder var6 = new StringBuilder();
                  var3 = var6.append("\t").append(var3).toString();
                  break label40;
               } catch (Exception var8) {
                  return;
               }
            }
         }

         var3 = "";
      }

      try {
         StringBuilder var12 = new StringBuilder();
         FilesKt.appendText$default(var5, var12.append(var1).append("\t").append(var2).append(var3).append("\n").toString(), null, 2, null);
      } catch (Exception var7) {
      }
   }

   public final void markFix(String var1, LaneFixDialog.Result var2) {
      Intrinsics.checkNotNullParameter(var1, "name");
      Intrinsics.checkNotNullParameter(var2, "r");

      try {
         File var10 = this.reviews;
         int var5 = var2.getThrough();
         int var6 = var2.getLane();
         int var3 = var2.getExtraL();
         int var4 = var2.getExtraR();
         String var8 = var2.getKind();
         String var9 = var2.getWay();
         int var7 = var2.getOtherWay();
         String var13 = var2.getKindR();
         StringBuilder var11 = new StringBuilder();
         FilesKt.appendText$default(
            var10,
            var11.append(var1)
               .append("\t-1\t")
               .append(var5)
               .append("\t")
               .append(var6)
               .append("\t")
               .append(var3)
               .append("\t")
               .append(var4)
               .append("\t")
               .append(var8)
               .append("\t")
               .append(var9)
               .append("\t")
               .append(var7)
               .append("\t")
               .append(var13)
               .append("\n")
               .toString(),
            null,
            2,
            null
         );
      } catch (Exception var12) {
      }
   }

   public final void newDrive() {
      String var1 = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.US).format(new Date());
      this.driveFolder = "Pictures/StarView Lanes/" + var1;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void request(String var1, LaneShots.Why var2, File var3) {
      String var14 = "";
      Intrinsics.checkNotNullParameter(var1, "caption");
      Intrinsics.checkNotNullParameter(var2, "why");
      Detector var15 = this.detector;
      if (var15 != null) {
         if (this.roomLeft()) {
            long var7 = System.currentTimeMillis();
            switch (LaneShots.WhenMappings.$EnumSwitchMapping$0[var2.ordinal()]) {
               case 1:
                  if (var7 - this.lastEventMs < 5000L) {
                     return;
                  }

                  this.lastEventMs = var7;
                  break;
               case 2:
                  if (var7 - this.lastPeriodicMs < 30000L) {
                     return;
                  }

                  this.lastPeriodicMs = var7;
               case 3:
                  break;
               default:
                  throw new NoWhenBranchMatchedException();
            }

            if (var2 != LaneShots.Why.EVERY_30S) {
               this.lastPeriodicMs = var7;
            }

            boolean var6;
            Pair var31;
            label120: {
               Function0 var9 = this.lanes;
               var6 = false;
               if (var9 != null) {
                  Pair var10 = (Pair)var9.invoke();
                  var31 = var10;
                  if (var10 != null) {
                     break label120;
                  }
               }

               var31 = TuplesKt.to(0, 0);
            }

            int var4 = ((Number)var31.component1()).intValue();
            int var5 = ((Number)var31.component2()).intValue();
            Function0 var32 = this.road;
            SurfaceView var12 = null;
            LaneBrain.Road var39;
            if (var32 != null) {
               var39 = (LaneBrain.Road)var32.invoke();
            } else {
               var39 = null;
            }

            String var11;
            label114: {
               Function0 var33 = this.answer;
               if (var33 != null) {
                  String var34 = (String)var33.invoke();
                  var11 = var34;
                  if (var34 != null) {
                     break label114;
                  }
               }

               var11 = "0,0,0";
            }

            Function0 var35 = this.scene;
            if (var35 != null) {
               var12 = (SurfaceView)var35.invoke();
            }

            List var13;
            label127: {
               try {
                  var36 = this.ramps;
               } catch (Exception var30) {
                  var13 = CollectionsKt.emptyList();
                  break label127;
               }

               label103: {
                  if (var36 != null) {
                     try {
                        var13 = (List)var36.invoke();
                     } catch (Exception var29) {
                        var13 = CollectionsKt.emptyList();
                        break label127;
                     }

                     var37 = var13;
                     if (var13 != null) {
                        break label103;
                     }
                  }

                  try {
                     var37 = CollectionsKt.emptyList();
                  } catch (Exception var28) {
                     var13 = CollectionsKt.emptyList();
                     break label127;
                  }
               }

               var13 = var37;
            }

            label94: {
               Function0 var16;
               try {
                  var16 = this.modelSays;
               } catch (Exception var27) {
                  var38 = "";
                  break label94;
               }

               var38 = var14;
               label91:
               if (var16 != null) {
                  try {
                     var38 = (String)var16.invoke();
                  } catch (Exception var26) {
                     var38 = "";
                     break label91;
                  }

                  if (var38 == null) {
                     var38 = var14;
                  }
               }
            }

            Bitmap[] var42 = new Bitmap[1];
            Bitmap[] var41 = new Bitmap[1];
            Bitmap[] var20 = new Bitmap[1];
            Bitmap[] var18 = new Bitmap[1];
            BooleanRef var17 = new BooleanRef();
            if (var12 == null) {
               var6 = true;
            }

            var17.element = var6;
            BooleanRef var21 = new BooleanRef();
            var21.element = var15.wideLive() ^ true;
            Object var19 = new Object();
            if (!var21.element) {
               var15.setWideReq(
                  new LaneShots$$ExternalSyntheticLambda2(
                     var19, var21, var18, var41, var17, var20, this, var42, var4, var5, var1, var2, var7, var39, var11, var13, var38, var3
                  )
               );
               new Handler(Looper.getMainLooper())
                  .postDelayed(
                     new LaneShots$$ExternalSyntheticLambda3(
                        var19, var21, var41, var17, var20, var18, this, var42, var4, var5, var1, var2, var7, var39, var11, var13, var38, var3
                     ),
                     800L
                  );
            }

            if (var12 != null && var12.getWidth() > 0 && var12.getHeight() > 0) {
               try {
                  Bitmap var24 = Bitmap.createBitmap(640, var12.getHeight() * 640 / var12.getWidth(), Config.ARGB_8888);
                  Intrinsics.checkNotNullExpressionValue(var24, "createBitmap(...)");
                  LaneShots$$ExternalSyntheticLambda4 var22 = new LaneShots$$ExternalSyntheticLambda4(
                     var19, var42, var24, var17, var41, var21, var20, var18, this, var4, var5, var1, var2, var7, var39, var11, var13, var38, var3
                  );
                  Handler var23 = new Handler(Looper.getMainLooper());
                  PixelCopy.request(var12, var24, var22, var23);
               } catch (Exception var25) {
                  var17.element = true;
               }
            } else {
               var17.element = true;
            }

            var15.setShotReq(
               new LaneShots$$ExternalSyntheticLambda5(
                  var19, var41, var20, var15, var17, var21, var18, this, var42, var4, var5, var1, var2, var7, var39, var11, var13, var38, var3
               )
            );
         }
      }
   }

   public final void setAnswer(Function0<String> var1) {
      this.answer = var1;
   }

   public final void setDetector(Detector var1) {
      this.detector = var1;
   }

   public final void setDriveFolder(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.driveFolder = var1;
   }

   public final void setLanes(Function0<Pair<Integer, Integer>> var1) {
      this.lanes = var1;
   }

   public final void setModelSays(Function0<String> var1) {
      this.modelSays = var1;
   }

   public final void setRamps(Function0<? extends List<LaneRamps.Ramp>> var1) {
      this.ramps = var1;
   }

   public final void setRoad(Function0<LaneBrain.Road> var1) {
      this.road = var1;
   }

   public final void setScene(Function0<? extends SurfaceView> var1) {
      this.scene = var1;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final Map<String, String> skipReasons() {
      HashMap var3;
      Iterator var4;
      try {
         var3 = new HashMap();
         var4 = FilesKt.readLines$default(this.reviews, null, 1, null).iterator();
      } catch (Exception var11) {
         return new HashMap<>();
      }

      while (true) {
         String var15;
         try {
            if (!var4.hasNext()) {
               break;
            }

            var15 = (String & CharSequence)var4.next();
         } catch (Exception var14) {
            return new HashMap<>();
         }

         boolean var1 = false;

         label78: {
            Map var5;
            Object var6;
            try {
               var16 = StringsKt.split$default(var15, new String[]{"\t"}, false, 0, 6, null);
               if (var16.size() < 2) {
                  continue;
               }

               if (!Intrinsics.areEqual(var16.get(1), "0")) {
                  break label78;
               }

               var5 = var3;
               var6 = var16.get(0);
            } catch (Exception var13) {
               return new HashMap<>();
            }

            label56: {
               try {
                  if (2 >= var16.size()) {
                     break label56;
                  }
               } catch (Exception var12) {
                  return new HashMap<>();
               }

               var1 = true;
            }

            if (var1) {
               try {
                  var15 = (String)var16.get(2);
               } catch (Exception var10) {
                  return new HashMap<>();
               }
            } else {
               var15 = "skip";
            }

            try {
               var5.put(var6, var15);
               continue;
            } catch (Exception var9) {
               return new HashMap<>();
            }
         }

         try {
            var3.remove(var16.get(0));
         } catch (Exception var8) {
            return new HashMap<>();
         }
      }

      Map var2;
      try {
         var2 = var3;
      } catch (Exception var7) {
         var2 = new HashMap();
      }

      return var2;
   }

   public final void tick(Function0<String> var1) {
      Intrinsics.checkNotNullParameter(var1, "caption");
      if (System.currentTimeMillis() - this.lastPeriodicMs >= 30000L) {
         request$default(this, (String)var1.invoke(), LaneShots.Why.EVERY_30S, null, 4, null);
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final Map<String, Integer> verdicts() {
      Iterator var3;
      HashMap var13;
      try {
         var13 = new HashMap();
         var3 = FilesKt.readLines$default(this.reviews, null, 1, null).iterator();
      } catch (Exception var10) {
         return new HashMap<>();
      }

      while (true) {
         String var4;
         try {
            if (!var3.hasNext()) {
               break;
            }

            var4 = (String & CharSequence)var3.next();
         } catch (Exception var12) {
            return new HashMap<>();
         }

         int var1 = 0;

         Map var5;
         Integer var15;
         try {
            List var6 = StringsKt.split$default(var4, new String[]{"\t"}, false, 0, 6, null);
            if (var6.size() < 2) {
               continue;
            }

            var5 = var13;
            var4 = (String)var6.get(0);
            var15 = StringsKt.toIntOrNull((String)var6.get(1));
         } catch (Exception var11) {
            return new HashMap<>();
         }

         if (var15 != null) {
            try {
               var1 = var15;
            } catch (Exception var9) {
               return new HashMap<>();
            }
         }

         try {
            var5.put(var4, var1);
         } catch (Exception var8) {
            return new HashMap<>();
         }
      }

      try {
         var2 = var13;
      } catch (Exception var7) {
         var2 = new HashMap();
      }

      return var2;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final String caption(LaneBrain var1, String var2, float var3) {
         Intrinsics.checkNotNullParameter(var1, "tr");
         Intrinsics.checkNotNullParameter(var2, "why");
         Pair var7 = var1.laneFromLeft();
         int var6 = ((Number)var7.component1()).intValue();
         double var4 = ((Number)var7.component2()).doubleValue();
         String var17;
         if (var6 > 0) {
            String var16 = var1.label();
            var6 = (int)(100 * var4);
            var17 = var16 + " (" + var6 + "%)";
         } else {
            var17 = "Lane ?";
         }

         String var18;
         label29: {
            LaneBrain.Road var9 = var1.getRoad();
            if (var9 != null) {
               var18 = var9.getName();
               boolean var14;
               if (var18.length() == 0) {
                  var14 = true;
               } else {
                  var14 = false;
               }

               if (var14) {
                  var18 = var9.getKind();
               }

               String var19 = var18;
               var18 = var19;
               if (var19 != null) {
                  break label29;
               }
            }

            var18 = "no street";
         }

         String var20 = new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
         var6 = (int)var3;
         var2 = StringsKt.take(var2, 110);
         String var11;
         if (var1.modelFresh()) {
            String var10 = var1.getModelText();
            var11 = "   ·   " + var10;
         } else {
            var11 = "";
         }

         return var20 + "   " + var17 + "   ·   " + var18 + "   ·   " + var6 + " mph\n" + var2 + var11;
      }
   }

   public static final class Shot {
      private final long atMs;
      private final String caption;
      private final String drive;
      private final String model;
      private final String name;
      private final String ramps;
      private final String roadFile;
      private final String street;
      private final String uri;
      private final long wayId;
      private final String why;

      public Shot(String var1, String var2, long var3, String var5, String var6, String var7, long var8, String var10, String var11, String var12, String var13) {
         Intrinsics.checkNotNullParameter(var1, "name");
         Intrinsics.checkNotNullParameter(var2, "uri");
         Intrinsics.checkNotNullParameter(var5, "why");
         Intrinsics.checkNotNullParameter(var6, "caption");
         Intrinsics.checkNotNullParameter(var7, "drive");
         Intrinsics.checkNotNullParameter(var10, "street");
         Intrinsics.checkNotNullParameter(var11, "ramps");
         Intrinsics.checkNotNullParameter(var12, "model");
         Intrinsics.checkNotNullParameter(var13, "roadFile");
         super();
         this.name = var1;
         this.uri = var2;
         this.atMs = var3;
         this.why = var5;
         this.caption = var6;
         this.drive = var7;
         this.wayId = var8;
         this.street = var10;
         this.ramps = var11;
         this.model = var12;
         this.roadFile = var13;
      }

      // $VF: synthetic method
      public Shot(
         String var1,
         String var2,
         long var3,
         String var5,
         String var6,
         String var7,
         long var8,
         String var10,
         String var11,
         String var12,
         String var13,
         int var14,
         DefaultConstructorMarker var15
      ) {
         if ((var14 & 64) != 0) {
            var8 = 0L;
         }

         if ((var14 & 128) != 0) {
            var10 = "";
         }

         if ((var14 & 256) != 0) {
            var11 = "";
         }

         if ((var14 & 512) != 0) {
            var12 = "";
         }

         if ((var14 & 1024) != 0) {
            var13 = "";
         }

         this(var1, var2, var3, var5, var6, var7, var8, var10, var11, var12, var13);
      }

      public final long getAtMs() {
         return this.atMs;
      }

      public final String getCaption() {
         return this.caption;
      }

      public final String getDrive() {
         return this.drive;
      }

      public final String getModel() {
         return this.model;
      }

      public final String getName() {
         return this.name;
      }

      public final String getRamps() {
         return this.ramps;
      }

      public final String getRoadFile() {
         return this.roadFile;
      }

      public final String getStreet() {
         return this.street;
      }

      public final String getUri() {
         return this.uri;
      }

      public final long getWayId() {
         return this.wayId;
      }

      public final String getWhy() {
         return this.why;
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[LaneShots.Why.values().length];

         try {
            var0[LaneShots.Why.EVENT.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[LaneShots.Why.EVERY_30S.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[LaneShots.Why.USER.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }

   public static enum Why {
      EVENT,
      EVERY_30S,
      USER;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(LaneShots.Why.$VALUES);
      private static final LaneShots.Why[] $VALUES = $values();

      // $VF: synthetic method
      private static final LaneShots.Why[] $values() {
         return new LaneShots.Why[]{EVENT, USER, EVERY_30S};
      }

      public static EnumEntries<LaneShots.Why> getEntries() {
         return $ENTRIES;
      }
   }
}
