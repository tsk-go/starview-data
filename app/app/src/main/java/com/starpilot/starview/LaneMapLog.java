package com.starpilot.starview;

import android.content.Context;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

public final class LaneMapLog {
   public static final LaneMapLog.Companion Companion = new LaneMapLog.Companion(null);
   private static final long MAX_BYTES = 314572800L;
   public static final int REC = 48;
   private static final double STEP_M = 0.9;
   private final ByteBuffer buf;
   private final Context ctx;
   private File file;
   private long lastFlushMs;
   private double lastLat;
   private double lastLon;
   private BufferedOutputStream out;
   private volatile long points;

   // $VF: synthetic method
   public static boolean $r8$lambda$cU9gsUGJlaLPG14eYOIA3lOM_RU/* $VF was: $r8$lambda$cU9gsUGJlaLPG14eYOIA3lOM_RU*/(File var0) {
      return prune$lambda$0(var0);
   }

   public LaneMapLog(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      this.ctx = var1;
      this.lastLat = Double.NaN;
      this.lastLon = Double.NaN;
      this.buf = ByteBuffer.allocate(48).order(ByteOrder.LITTLE_ENDIAN);
   }

   private final BufferedOutputStream open(long var1) {
      BufferedOutputStream var3;
      try {
         File var8 = Companion.dir(this.ctx);
         this.prune(var8);
         SimpleDateFormat var5 = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
         Date var6 = new Date();
         String var9 = var5.format(var6);
         StringBuilder var12 = new StringBuilder();
         File var4 = new File(var8, var12.append(var9).append(".lm").toString());
         FileOutputStream var10 = new FileOutputStream(var4);
         var3 = new BufferedOutputStream(var10, 32768);
         byte[] var11 = "SVLM1\u0000".getBytes(Charsets.US_ASCII);
         Intrinsics.checkNotNullExpressionValue(var11, "getBytes(...)");
         var3.write(var11);
         var3.write(new byte[]{1, 0, 48, 0});
         this.file = var4;
         this.out = var3;
         this.lastFlushMs = var1;
      } catch (Exception var7) {
         var3 = null;
      }

      return var3;
   }

   private final void prune(File var1) {
      File[] var6 = var1.listFiles(new LaneMapLog$$ExternalSyntheticLambda0());
      if (var6 != null) {
         List var7 = ArraysKt.sortedWith(var6, new Comparator() {
            @Override
            public final int compare(T var1, T var2) {
               return ComparisonsKt.compareValues(((File)var1).getName(), ((File)var2).getName());
            }
         });
         if (var7 != null) {
            Iterator var4 = var7.iterator();
            long var2 = 0L;

            while (var4.hasNext()) {
               File var5 = (File)var4.next();
               var2 += var5.length();
            }

            for (File var8 : var7) {
               if (var2 <= 314572800L) {
                  break;
               }

               var2 -= var8.length();
               var8.delete();
            }

            return;
         }
      }
   }

   private static final boolean prune$lambda$0(File var0) {
      String var1 = var0.getName();
      Intrinsics.checkNotNullExpressionValue(var1, "getName(...)");
      return StringsKt.endsWith$default(var1, ".lm", false, 2, null);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void close() {
      label41: {
         BufferedOutputStream var1;
         try {
            var1 = this.out;
         } catch (Exception var5) {
            break label41;
         }

         if (var1 != null) {
            try {
               var1.flush();
            } catch (Exception var4) {
               break label41;
            }
         }

         try {
            var1 = this.out;
         } catch (Exception var3) {
            break label41;
         }

         if (var1 != null) {
            try {
               var1.close();
            } catch (Exception var2) {
            }
         }
      }

      File var7 = this.file;
      if (var7 != null && var7.length() <= 10L) {
         var7.delete();
      }

      this.out = null;
      this.file = null;
      this.lastLat = Double.NaN;
      this.lastLon = Double.NaN;
   }

   public final void frame(long var1, float var3, LaneBrain.Frame var4, LaneBrain var5, boolean var6) {
      Intrinsics.checkNotNullParameter(var5, "b");
      if (!(var3 < 1.3F) && RtkPosition.INSTANCE.fast(var1)) {
         String var34 = RtkPosition.INSTANCE.getKind();
         if (Intrinsics.areEqual(var34, "fixed") || Intrinsics.areEqual(var34, "float")) {
            double var9 = RtkPosition.INSTANCE.getAccM();
            boolean var27 = Double.isInfinite(var9);
            boolean var24 = true;
            boolean var21;
            if (!var27 && !Double.isNaN(var9)) {
               var21 = 1;
            } else {
               var21 = 0;
            }

            if (var21 && !(var9 > 0.5)) {
               double[] var32 = RtkPosition.INSTANCE.now(var1, var3);
               if (var32 != null) {
                  double var11 = var32[0];
                  double var7 = var32[1];
                  double var13 = this.lastLat;
                  if (!Double.isInfinite(var13) && !Double.isNaN(var13)) {
                     var21 = (boolean)1;
                  } else {
                     var21 = (boolean)0;
                  }

                  if (var21) {
                     double var15 = (var11 - this.lastLat) * 111195.0;
                     var13 = (var7 - this.lastLon) * 111195.0 * Math.cos(Math.toRadians(var11));
                     if (var13 * var13 + var15 * var15 < 0.81) {
                        return;
                     }
                  }

                  this.lastLat = var11;
                  this.lastLon = var7;
                  BufferedOutputStream var33 = this.out;
                  BufferedOutputStream var54 = var33;
                  if (var33 == null) {
                     var33 = this.open(var1);
                     var54 = var33;
                     if (var33 == null) {
                        return;
                     }
                  }

                  Pair var56 = var5.laneFromLeft();
                  int var26 = ((Number)var56.component1()).intValue();
                  double var43 = ((Number)var56.component2()).doubleValue();
                  int var25 = var5.laneCount().component1().intValue();
                  LaneBrain.RoadModel var36 = var5.getModel();
                  LaneBrain.RoadInfo var57 = var5.getInfo();
                  int var22 = 0;
                  if (var6) {
                     var22 = 0 | 1;
                  }

                  int var23;
                  if (var4 != null) {
                     var23 = var4.getOpChange();
                  } else {
                     var23 = 0;
                  }

                  var21 = var22;
                  if (var23 != 0) {
                     var21 = var22 | 2;
                  }

                  boolean var51;
                  if (var57 != null && var57.getHpmsRamp()) {
                     var51 = var24;
                  } else {
                     var51 = 0;
                  }

                  var22 = var21;
                  if (var51) {
                     var22 = var21 | 4;
                  }

                  var51 = var22;
                  if (var36.getTunnel()) {
                     var51 = var22 | 8;
                  }

                  var21 = var51;
                  if (var36.getBridge()) {
                     var21 = var51 | 16;
                  }

                  var22 = var21;
                  if (var36.getExtraL() > 0) {
                     var22 = var21 | 32;
                  }

                  var21 = var22;
                  if (var36.getExtraR() > 0) {
                     var21 = var22 | 64;
                  }

                  LaneMapLog.Companion var58 = Companion;
                  ByteBuffer var35 = this.buf;
                  Intrinsics.checkNotNullExpressionValue(var35, "buf");
                  long var30 = System.currentTimeMillis();
                  var13 = RtkPosition.INSTANCE.getHeading();
                  var6 = Intrinsics.areEqual(var34, "fixed");
                  float var20 = Float.NaN;
                  float var17;
                  if (var4 != null) {
                     var17 = var4.getYL();
                  } else {
                     var17 = Float.NaN;
                  }

                  float var18;
                  if (var4 != null) {
                     var18 = var4.getYR();
                  } else {
                     var18 = Float.NaN;
                  }

                  float var19;
                  if (var4 != null) {
                     var19 = var4.getPL();
                  } else {
                     var19 = Float.NaN;
                  }

                  if (var4 != null) {
                     var20 = var4.getPR();
                  }

                  var22 = var36.getLanes();
                  LaneBrain.Road var39 = var5.getRoad();
                  long var28;
                  if (var39 != null) {
                     var28 = var39.getId();
                  } else {
                     var28 = 0L;
                  }

                  var58.encode(var35, var30, var11, var7, var13, var3, var6, var9, var26, var25, var43, var21, var17, var18, var19, var20, var22, var28);

                  label149: {
                     try {
                        var54.write(this.buf.array(), 0, 48);
                        this.points++;
                        var28 = this.lastFlushMs;
                     } catch (Exception var38) {
                        break label149;
                     }

                     if (var1 - var28 <= 10000L) {
                        return;
                     }

                     try {
                        var54.flush();
                        this.lastFlushMs = var1;
                        return;
                     } catch (Exception var37) {
                     }
                  }

                  this.close();
               }
            }
         }
      }
   }

   public final long getPoints() {
      return this.points;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private static final int decode$u8(ByteBuffer var0, int var1, int var2) {
         return var0.get(var1 + var2) & 0xFF;
      }

      private static final int encode$cm(float var0) {
         boolean var1;
         if (!Float.isInfinite(var0) && !Float.isNaN(var0)) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         if (var1) {
            var1 = RangesKt.coerceIn((int)(100 * var0), -32000, 32000);
         } else {
            var1 = -32768;
         }

         return var1;
      }

      private static final int encode$pct(float var0) {
         boolean var1;
         if (!Float.isInfinite(var0) && !Float.isNaN(var0)) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         if (var1) {
            var1 = RangesKt.coerceIn((int)(100 * var0), 0, 100);
         } else {
            var1 = 255;
         }

         return var1;
      }

      public final LaneMapLog.Companion.Point decode(ByteBuffer var1, int var2) {
         Intrinsics.checkNotNullParameter(var1, "b");
         var1.order(ByteOrder.LITTLE_ENDIAN);
         int var19 = var1.getShort(var2 + 16) & '\uffff';
         short var18 = var1.getShort(var2 + 26);
         short var17 = var1.getShort(var2 + 28);
         long var23 = var1.getLong(var2);
         double var11 = var1.getInt(var2 + 8) / 1.0E7;
         double var9 = var1.getInt(var2 + 12) / 1.0E7;
         double var3;
         if (var19 == 65535) {
            var3 = Double.NaN;
         } else {
            var3 = var19 / 100.0;
         }

         double var15 = ('\uffff' & var1.getShort(var2 + 18)) / 100.0;
         boolean var25;
         if (decode$u8(var1, var2, 20) == 2) {
            var25 = true;
         } else {
            var25 = false;
         }

         double var13 = decode$u8(var1, var2, 21) / 100.0;
         int var20 = decode$u8(var1, var2, 22);
         int var22 = decode$u8(var1, var2, 23);
         var19 = decode$u8(var1, var2, 24);
         int var21 = decode$u8(var1, var2, 25);
         double var5;
         if (var18 == -32768) {
            var5 = Double.NaN;
         } else {
            var5 = var18 / 100.0;
         }

         double var7;
         if (var17 == -32768) {
            var7 = Double.NaN;
         } else {
            var7 = var17 / 100.0;
         }

         return new LaneMapLog.Companion.Point(
            var23, var11, var9, var3, var15, var25, var13, var20, var22, var19, var21, var5, var7, decode$u8(var1, var2, 32), var1.getLong(var2 + 33)
         );
      }

      public final File dir(Context var1) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         File var2 = new File(var1.getFilesDir(), "lanemap");
         var2.mkdirs();
         return var2;
      }

      public final void encode(
         ByteBuffer var1,
         long var2,
         double var4,
         double var6,
         double var8,
         float var10,
         boolean var11,
         double var12,
         int var14,
         int var15,
         double var16,
         int var18,
         float var19,
         float var20,
         float var21,
         float var22,
         int var23,
         long var24
      ) {
         Intrinsics.checkNotNullParameter(var1, "buf");
         boolean var30 = Double.isInfinite(var8);
         byte var29 = 1;
         boolean var28;
         if (!var30 && !Double.isNaN(var8)) {
            var28 = 1;
         } else {
            var28 = 0;
         }

         if (var28) {
            double var26 = 360;
            var28 = (int)((var8 % var26 + var26) % var26 * 100);
         } else {
            var28 = 65535;
         }

         ((Buffer)var1).clear();
         var1.putLong(var2);
         var1.putInt((int)Math.round(var4 * 1.0E7));
         var1.putInt((int)Math.round(1.0E7 * var6));
         var1.putShort((short)var28);
         var1.putShort((short)RangesKt.coerceIn((int)(100 * var10), 0, 65535));
         byte var33 = var29;
         if (var11) {
            var33 = 2;
         }

         var1.put((byte)var33);
         var4 = 100;
         var1.put((byte)RangesKt.coerceIn((int)(var12 * var4), 0, 255));
         var1.put((byte)RangesKt.coerceIn(var14, 0, 255));
         var1.put((byte)RangesKt.coerceIn(var15, 0, 255));
         var1.put((byte)RangesKt.coerceIn((int)(var4 * var16), 0, 100));
         var1.put((byte)var18);
         var1.putShort((short)encode$cm(var19));
         var1.putShort((short)encode$cm(var20));
         var1.put((byte)encode$pct(var21));
         var1.put((byte)encode$pct(var22));
         var1.put((byte)RangesKt.coerceIn(var23, 0, 255));
         var1.putLong(var24);

         while (var1.position() < 48) {
            var1.put((byte)0);
         }
      }

      public static final class Point {
         private final double accM;
         private final int conf;
         private final boolean fixed;
         private final int flags;
         private final double headingDeg;
         private final int lane;
         private final int lanes;
         private final double lat;
         private final double lon;
         private final int roadLanes;
         private final double speed;
         private final long unixMs;
         private final long wayId;
         private final double yL;
         private final double yR;

         public Point(
            long var1,
            double var3,
            double var5,
            double var7,
            double var9,
            boolean var11,
            double var12,
            int var14,
            int var15,
            int var16,
            int var17,
            double var18,
            double var20,
            int var22,
            long var23
         ) {
            this.unixMs = var1;
            this.lat = var3;
            this.lon = var5;
            this.headingDeg = var7;
            this.speed = var9;
            this.fixed = var11;
            this.accM = var12;
            this.lane = var14;
            this.lanes = var15;
            this.conf = var16;
            this.flags = var17;
            this.yL = var18;
            this.yR = var20;
            this.roadLanes = var22;
            this.wayId = var23;
         }

         public final double getAccM() {
            return this.accM;
         }

         public final int getConf() {
            return this.conf;
         }

         public final boolean getFixed() {
            return this.fixed;
         }

         public final int getFlags() {
            return this.flags;
         }

         public final double getHeadingDeg() {
            return this.headingDeg;
         }

         public final int getLane() {
            return this.lane;
         }

         public final int getLanes() {
            return this.lanes;
         }

         public final double getLat() {
            return this.lat;
         }

         public final double getLon() {
            return this.lon;
         }

         public final int getRoadLanes() {
            return this.roadLanes;
         }

         public final double getSpeed() {
            return this.speed;
         }

         public final long getUnixMs() {
            return this.unixMs;
         }

         public final long getWayId() {
            return this.wayId;
         }

         public final double getYL() {
            return this.yL;
         }

         public final double getYR() {
            return this.yR;
         }
      }
   }

   public static final class Export {
      public static final LaneMapLog.Export INSTANCE = new LaneMapLog.Export();

      // $VF: synthetic method
      public static boolean $r8$lambda$S3yd4IT4_psLI65wwVAjwMSzD0U(File var0) {
         return toDownloads$lambda$3(var0);
      }

      // $VF: synthetic method
      public static boolean $r8$lambda$_prXlW_dq9xQMrfgVWKJm5z_sdk/* $VF was: $r8$lambda$_prXlW_dq9xQMrfgVWKJm5z_sdk*/(File var0) {
         return summary$lambda$0(var0);
      }

      private Export() {
      }

      private static final boolean summary$lambda$0(File var0) {
         String var1 = var0.getName();
         Intrinsics.checkNotNullExpressionValue(var1, "getName(...)");
         return StringsKt.endsWith$default(var1, ".lm", false, 2, null);
      }

      private static final boolean toDownloads$lambda$3(File var0) {
         String var3 = var0.getName();
         Intrinsics.checkNotNullExpressionValue(var3, "getName(...)");
         boolean var2 = false;
         boolean var1 = var2;
         if (StringsKt.endsWith$default(var3, ".lm", false, 2, null)) {
            var1 = var2;
            if (var0.length() > 10L) {
               var1 = true;
            }
         }

         return var1;
      }

      public final Triple<Integer, Long, Long> summary(Context var1) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         File[] var11 = LaneMapLog.Companion.dir(var1).listFiles(new LaneMapLog$Export$$ExternalSyntheticLambda0());
         byte var3 = 0;
         Long var10 = 0L;
         if (var11 == null) {
            return new Triple<>(0, var10, var10);
         } else {
            int var4 = var11.length;
            int var2 = 0;

            long var6;
            for (var6 = 0L; var2 < var4; var2++) {
               File var14 = var11[var2];
               var6 += var14.length();
            }

            int var5 = var11.length;
            var4 = var11.length;
            long var8 = 0L;

            for (int var12 = var3; var12 < var4; var12++) {
               File var15 = var11[var12];
               var8 += Math.max(0L, (var15.length() - 10) / 48);
            }

            return new Triple<>(var5, var8, var6);
         }
      }

      public final String toDownloads(Context param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 000: aload 1
         // 001: ldc "ctx"
         // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
         // 006: getstatic com/starpilot/starview/LaneMapLog.Companion Lcom/starpilot/starview/LaneMapLog$Companion;
         // 009: aload 1
         // 00a: invokevirtual com/starpilot/starview/LaneMapLog$Companion.dir (Landroid/content/Context;)Ljava/io/File;
         // 00d: astore 3
         // 00e: new com/starpilot/starview/LaneMapLog$Export$$ExternalSyntheticLambda1
         // 011: astore 4
         // 013: aload 4
         // 015: invokespecial com/starpilot/starview/LaneMapLog$Export$$ExternalSyntheticLambda1.<init> ()V
         // 018: aload 3
         // 019: aload 4
         // 01b: invokevirtual java/io/File.listFiles (Ljava/io/FileFilter;)[Ljava/io/File;
         // 01e: astore 4
         // 020: aload 4
         // 022: ifnull 03a
         // 025: new com/starpilot/starview/LaneMapLog$Export$toDownloads$$inlined$sortedBy$1
         // 028: astore 3
         // 029: aload 3
         // 02a: invokespecial com/starpilot/starview/LaneMapLog$Export$toDownloads$$inlined$sortedBy$1.<init> ()V
         // 02d: aload 4
         // 02f: aload 3
         // 030: checkcast java/util/Comparator
         // 033: invokestatic kotlin/collections/ArraysKt.sortedWith ([Ljava/lang/Object;Ljava/util/Comparator;)Ljava/util/List;
         // 036: astore 3
         // 037: goto 03c
         // 03a: aconst_null
         // 03b: astore 3
         // 03c: aload 3
         // 03d: checkcast java/util/Collection
         // 040: astore 4
         // 042: aload 4
         // 044: ifnull 059
         // 047: aload 4
         // 049: invokeinterface java/util/Collection.isEmpty ()Z 1
         // 04e: ifeq 054
         // 051: goto 059
         // 054: bipush 0
         // 055: istore 2
         // 056: goto 05b
         // 059: bipush 1
         // 05a: istore 2
         // 05b: iload 2
         // 05c: ifeq 065
         // 05f: ldc "No lane traces yet - they're logged while driving with an RTK fix."
         // 061: astore 1
         // 062: goto 1f9
         // 065: new java/text/SimpleDateFormat
         // 068: astore 5
         // 06a: aload 5
         // 06c: ldc "yyyy-MM-dd_HH-mm"
         // 06e: getstatic java/util/Locale.US Ljava/util/Locale;
         // 071: invokespecial java/text/SimpleDateFormat.<init> (Ljava/lang/String;Ljava/util/Locale;)V
         // 074: new java/util/Date
         // 077: astore 4
         // 079: aload 4
         // 07b: invokespecial java/util/Date.<init> ()V
         // 07e: aload 5
         // 080: aload 4
         // 082: invokevirtual java/text/SimpleDateFormat.format (Ljava/util/Date;)Ljava/lang/String;
         // 085: astore 5
         // 087: new java/lang/StringBuilder
         // 08a: astore 4
         // 08c: aload 4
         // 08e: invokespecial java/lang/StringBuilder.<init> ()V
         // 091: aload 4
         // 093: ldc "lanemap_"
         // 095: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 098: aload 5
         // 09a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 09d: ldc ".zip"
         // 09f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 0a2: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 0a5: astore 5
         // 0a7: new android/content/ContentValues
         // 0aa: astore 4
         // 0ac: aload 4
         // 0ae: invokespecial android/content/ContentValues.<init> ()V
         // 0b1: aload 4
         // 0b3: ldc "_display_name"
         // 0b5: aload 5
         // 0b7: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
         // 0ba: aload 4
         // 0bc: ldc "mime_type"
         // 0be: ldc "application/zip"
         // 0c0: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
         // 0c3: aload 4
         // 0c5: ldc "relative_path"
         // 0c7: ldc "Download/StarView/Lane data"
         // 0c9: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
         // 0cc: aload 1
         // 0cd: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
         // 0d0: getstatic android/provider/MediaStore$Downloads.EXTERNAL_CONTENT_URI Landroid/net/Uri;
         // 0d3: aload 4
         // 0d5: invokevirtual android/content/ContentResolver.insert (Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
         // 0d8: astore 6
         // 0da: aload 6
         // 0dc: ifnull 21c
         // 0df: aload 1
         // 0e0: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
         // 0e3: aload 6
         // 0e5: invokevirtual android/content/ContentResolver.openOutputStream (Landroid/net/Uri;)Ljava/io/OutputStream;
         // 0e8: astore 1
         // 0e9: aload 1
         // 0ea: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 0ed: aload 1
         // 0ee: checkcast java/io/Closeable
         // 0f1: astore 6
         // 0f3: aload 6
         // 0f5: checkcast java/io/OutputStream
         // 0f8: astore 1
         // 0f9: new java/util/zip/ZipOutputStream
         // 0fc: astore 5
         // 0fe: aload 5
         // 100: aload 1
         // 101: invokespecial java/util/zip/ZipOutputStream.<init> (Ljava/io/OutputStream;)V
         // 104: aload 5
         // 106: checkcast java/io/Closeable
         // 109: astore 7
         // 10b: aload 3
         // 10c: astore 5
         // 10e: aload 4
         // 110: astore 5
         // 112: aload 7
         // 114: checkcast java/util/zip/ZipOutputStream
         // 117: astore 8
         // 119: aload 3
         // 11a: astore 5
         // 11c: aload 4
         // 11e: astore 5
         // 120: aload 3
         // 121: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
         // 126: astore 5
         // 128: aload 4
         // 12a: astore 1
         // 12b: aload 5
         // 12d: astore 4
         // 12f: aload 3
         // 130: astore 5
         // 132: aload 1
         // 133: astore 5
         // 135: aload 4
         // 137: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 13c: ifeq 1e3
         // 13f: aload 3
         // 140: astore 5
         // 142: aload 1
         // 143: astore 5
         // 145: aload 4
         // 147: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 14c: checkcast java/io/File
         // 14f: astore 9
         // 151: aload 3
         // 152: astore 5
         // 154: aload 1
         // 155: astore 5
         // 157: new java/util/zip/ZipEntry
         // 15a: astore 10
         // 15c: aload 3
         // 15d: astore 5
         // 15f: aload 1
         // 160: astore 5
         // 162: aload 10
         // 164: aload 9
         // 166: invokevirtual java/io/File.getName ()Ljava/lang/String;
         // 169: invokespecial java/util/zip/ZipEntry.<init> (Ljava/lang/String;)V
         // 16c: aload 3
         // 16d: astore 5
         // 16f: aload 1
         // 170: astore 5
         // 172: aload 8
         // 174: aload 10
         // 176: invokevirtual java/util/zip/ZipOutputStream.putNextEntry (Ljava/util/zip/ZipEntry;)V
         // 179: aload 3
         // 17a: astore 5
         // 17c: aload 1
         // 17d: astore 5
         // 17f: new java/io/FileInputStream
         // 182: astore 10
         // 184: aload 3
         // 185: astore 5
         // 187: aload 1
         // 188: astore 5
         // 18a: aload 9
         // 18c: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 18f: aload 3
         // 190: astore 5
         // 192: aload 1
         // 193: astore 5
         // 195: aload 10
         // 197: aload 9
         // 199: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
         // 19c: aload 3
         // 19d: astore 5
         // 19f: aload 1
         // 1a0: astore 5
         // 1a2: aload 10
         // 1a4: checkcast java/io/Closeable
         // 1a7: astore 5
         // 1a9: aload 5
         // 1ab: checkcast java/io/FileInputStream
         // 1ae: checkcast java/io/InputStream
         // 1b1: astore 9
         // 1b3: aload 8
         // 1b5: checkcast java/io/OutputStream
         // 1b8: astore 10
         // 1ba: aload 9
         // 1bc: aload 10
         // 1be: bipush 0
         // 1bf: bipush 2
         // 1c0: aconst_null
         // 1c1: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
         // 1c4: pop2
         // 1c5: aload 5
         // 1c7: aconst_null
         // 1c8: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 1cb: aload 8
         // 1cd: invokevirtual java/util/zip/ZipOutputStream.closeEntry ()V
         // 1d0: goto 12f
         // 1d3: astore 1
         // 1d4: goto 1d8
         // 1d7: astore 1
         // 1d8: aload 1
         // 1d9: athrow
         // 1da: astore 3
         // 1db: aload 5
         // 1dd: aload 1
         // 1de: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 1e1: aload 3
         // 1e2: athrow
         // 1e3: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 1e6: astore 1
         // 1e7: aload 7
         // 1e9: aconst_null
         // 1ea: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 1ed: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 1f0: astore 1
         // 1f1: aload 6
         // 1f3: aconst_null
         // 1f4: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 1f7: aconst_null
         // 1f8: astore 1
         // 1f9: goto 258
         // 1fc: astore 1
         // 1fd: goto 201
         // 200: astore 1
         // 201: aload 1
         // 202: athrow
         // 203: astore 3
         // 204: aload 7
         // 206: aload 1
         // 207: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 20a: aload 3
         // 20b: athrow
         // 20c: astore 1
         // 20d: goto 211
         // 210: astore 1
         // 211: aload 1
         // 212: athrow
         // 213: astore 3
         // 214: aload 6
         // 216: aload 1
         // 217: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 21a: aload 3
         // 21b: athrow
         // 21c: new java/lang/IllegalStateException
         // 21f: astore 3
         // 220: new java/lang/StringBuilder
         // 223: astore 1
         // 224: aload 1
         // 225: invokespecial java/lang/StringBuilder.<init> ()V
         // 228: aload 3
         // 229: aload 1
         // 22a: ldc_w "couldn't create "
         // 22d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 230: aload 5
         // 232: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 235: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 238: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
         // 23b: aload 3
         // 23c: athrow
         // 23d: astore 1
         // 23e: aload 1
         // 23f: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
         // 242: astore 1
         // 243: new java/lang/StringBuilder
         // 246: dup
         // 247: invokespecial java/lang/StringBuilder.<init> ()V
         // 24a: ldc_w "Couldn't export: "
         // 24d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 250: aload 1
         // 251: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 254: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 257: astore 1
         // 258: aload 1
         // 259: areturn
      }
   }
}
