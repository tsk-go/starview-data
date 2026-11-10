package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.math.MathKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

public final class Hpms {
   public static final int BRIDGE = 1;
   public static final Hpms INSTANCE = new Hpms();
   public static final int TUNNEL = 2;
   private static volatile File dir;
   private static long listedAt;
   private static final HashMap<String, Hpms.Pack> packs = new HashMap<>();

   // $VF: synthetic method
   public static boolean $r8$lambda$Gb1NXyKKM1BvM07hDqp6YBCOMlc(File var0) {
      return files$lambda$2(var0);
   }

   private Hpms() {
   }

   private final double angleDiff(double var1, double var3) {
      var1 -= var3;

      while (true) {
         var3 = var1;
         if (!(var1 > Math.PI)) {
            while (var3 < -Math.PI) {
               var3 += Math.PI * 2;
            }

            return var3;
         }

         var1 -= Math.PI * 2;
      }
   }

   private static final boolean files$lambda$2(File var0) {
      String var1 = var0.getName();
      Intrinsics.checkNotNullExpressionValue(var1, "getName(...)");
      CharSequence var2 = var1;
      return new Regex("hpms_[A-Z]{2}\\.bin").matches(var2);
   }

   // $VF: synthetic method
   public static Hpms.Match match$default(Hpms var0, double var1, double var3, double var5, Boolean var7, double var8, String var10, int var11, Object var12) {
      if ((var11 & 16) != 0) {
         var8 = 45.0;
      }

      if ((var11 & 32) != 0) {
         var10 = "";
      }

      return var0.match(var1, var3, var5, var7, var8, var10);
   }

   private final List<Hpms.Pack> open() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: getstatic com/starpilot/starview/Hpms.packs Ljava/util/HashMap;
      // 03: astore 4
      // 05: aload 4
      // 07: monitorenter
      // 08: invokestatic java/lang/System.currentTimeMillis ()J
      // 0b: lstore 1
      // 0c: lload 1
      // 0d: getstatic com/starpilot/starview/Hpms.listedAt J
      // 10: lsub
      // 11: ldc2_w 60000
      // 14: lcmp
      // 15: ifle 91
      // 18: lload 1
      // 19: putstatic com/starpilot/starview/Hpms.listedAt J
      // 1c: getstatic com/starpilot/starview/Hpms.INSTANCE Lcom/starpilot/starview/Hpms;
      // 1f: invokevirtual com/starpilot/starview/Hpms.files ()Ljava/util/Map;
      // 22: invokeinterface java/util/Map.entrySet ()Ljava/util/Set; 1
      // 27: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 2c: astore 5
      // 2e: aload 5
      // 30: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 35: ifeq 91
      // 38: aload 5
      // 3a: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 3f: checkcast java/util/Map$Entry
      // 42: astore 7
      // 44: aload 7
      // 46: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 4b: checkcast java/lang/String
      // 4e: astore 6
      // 50: aload 7
      // 52: invokeinterface java/util/Map$Entry.getValue ()Ljava/lang/Object; 1
      // 57: checkcast java/io/File
      // 5a: astore 9
      // 5c: getstatic com/starpilot/starview/Hpms.packs Ljava/util/HashMap;
      // 5f: aload 6
      // 61: invokevirtual java/util/HashMap.containsKey (Ljava/lang/Object;)Z
      // 64: istore 3
      // 65: iload 3
      // 66: ifne 2e
      // 69: getstatic com/starpilot/starview/Hpms.packs Ljava/util/HashMap;
      // 6c: checkcast java/util/Map
      // 6f: astore 8
      // 71: new com/starpilot/starview/Hpms$Pack
      // 74: astore 7
      // 76: aload 7
      // 78: aload 9
      // 7a: invokespecial com/starpilot/starview/Hpms$Pack.<init> (Ljava/io/File;)V
      // 7d: aload 8
      // 7f: aload 6
      // 81: aload 7
      // 83: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 88: pop
      // 89: goto 2e
      // 8c: astore 6
      // 8e: goto 2e
      // 91: getstatic com/starpilot/starview/Hpms.packs Ljava/util/HashMap;
      // 94: invokevirtual java/util/HashMap.values ()Ljava/util/Collection;
      // 97: astore 5
      // 99: aload 5
      // 9b: ldc "<get-values>(...)"
      // 9d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // a0: aload 5
      // a2: checkcast java/lang/Iterable
      // a5: invokestatic kotlin/collections/CollectionsKt.toList (Ljava/lang/Iterable;)Ljava/util/List;
      // a8: astore 5
      // aa: aload 4
      // ac: monitorexit
      // ad: aload 5
      // af: areturn
      // b0: astore 5
      // b2: aload 4
      // b4: monitorexit
      // b5: aload 5
      // b7: athrow
   }

   public final Map<String, File> files() {
      File var3 = dir;
      if (var3 != null) {
         File[] var5 = var3.listFiles(new Hpms$$ExternalSyntheticLambda0());
         if (var5 != null) {
            Map var7 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var5.length), 16));

            for (File var4 : var5) {
               String var6 = var4.getName();
               Intrinsics.checkNotNullExpressionValue(var6, "getName(...)");
               var6 = var6.substring(5, 7);
               Intrinsics.checkNotNullExpressionValue(var6, "substring(...)");
               var7.put(var6, var4);
            }

            return var7;
         }
      }

      return MapsKt.emptyMap();
   }

   public final File getDir() {
      return dir;
   }

   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      if (dir == null) {
         File var2 = new File(var1.getFilesDir(), "hpms");
         var2.mkdirs();
         dir = var2;
      }
   }

   public final Hpms.Match match(double var1, double var3, double var5, Boolean var7, double var8, String var10) {
      Intrinsics.checkNotNullParameter(var10, "preferName");
      Hpms.Match var19 = null;
      Hpms.Match var18 = null;

      for (Hpms.Pack var24 : this.open()) {
         if (var24.covers(var1, var3)) {
            Iterator var23 = var24.near(var1, var3, var8).iterator();
            Hpms.Match var20 = var18;
            var18 = var19;

            while (var23.hasNext()) {
               double var15;
               Hpms.Section var25;
               double var26;
               label79: {
                  Triple var29 = (Triple)var23.next();
                  var25 = (Hpms.Section)var29.component1();
                  var15 = ((Number)var29.component2()).doubleValue();
                  var26 = ((Number)var29.component3()).doubleValue();
                  Hpms.Match var21;
                  if (var7 != null && !Intrinsics.areEqual(var25.getRamp(), var7)) {
                     var21 = var18;
                  } else {
                     double var13 = Math.toDegrees(Math.abs(this.angleDiff(var26, var5)));
                     var26 = var13;
                     if (var13 > 90.0) {
                        var26 = 180.0 - var13;
                     }

                     var21 = var18;
                     if (!(var26 > 30.0)) {
                        label61: {
                           var13 = var15 + var26 * 0.5;
                           if (var18 != null) {
                              var19 = var18;
                              if (!(var13 < var18.getDistM() + var18.getAngleDeg() * 0.5)) {
                                 break label61;
                              }
                           }

                           var19 = new Hpms.Match(var25, var15, var26, var24.getAbbr());
                        }

                        boolean var17;
                        if (var10.length() > 0) {
                           var17 = true;
                        } else {
                           var17 = false;
                        }

                        var21 = var19;
                        if (var17) {
                           var21 = var19;
                           if (Intrinsics.areEqual(var25.getName(), var10)) {
                              if (var20 == null) {
                                 break label79;
                              }

                              var21 = var19;
                              if (var13 < var20.getDistM() + var20.getAngleDeg() * 0.5) {
                                 break label79;
                              }
                           }
                        }
                     }
                  }

                  var18 = var21;
                  continue;
               }

               var20 = new Hpms.Match(var25, var15, var26, var24.getAbbr());
               var18 = var19;
            }

            var19 = var18;
            var18 = var20;
         }
      }

      if (var18 == null) {
         var18 = var19;
      }

      return var18;
   }

   public final void reload() {
      HashMap var1 = packs;
      synchronized (var1) {
         packs.clear();
         Unit var2 = Unit.INSTANCE;
      }

      listedAt = 0L;
   }

   public final void useDir(File var1) {
      Intrinsics.checkNotNullParameter(var1, "d");
      dir = var1;
      HashMap var5 = packs;
      synchronized (var5) {
         packs.clear();
         Unit var2 = Unit.INSTANCE;
      }

      listedAt = 0L;
   }

   public static final class Match {
      private final double angleDeg;
      private final double distM;
      private final Hpms.Section s;
      private final String state;

      public Match(Hpms.Section var1, double var2, double var4, String var6) {
         Intrinsics.checkNotNullParameter(var1, "s");
         Intrinsics.checkNotNullParameter(var6, "state");
         super();
         this.s = var1;
         this.distM = var2;
         this.angleDeg = var4;
         this.state = var6;
      }

      public final double getAngleDeg() {
         return this.angleDeg;
      }

      public final double getDistM() {
         return this.distM;
      }

      public final Hpms.Section getS() {
         return this.s;
      }

      public final String getState() {
         return this.state;
      }
   }

   public static final class Pack {
      private final String abbr;
      private final ByteBuffer buf;
      private final File file;
      private final int maxTx;
      private final int maxTy;
      private final int minTx;
      private final int minTy;
      private final int nChunks;
      private final int nNames;
      private final int nTiles;
      private final HashMap<Integer, String> names;
      private final int oChunks;
      private final int oCidx;
      private final int oNames;
      private final int oTiles;
      private final int state;
      private final int tile;
      private final int year;

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public Pack(File var1) {
         Intrinsics.checkNotNullParameter(var1, "file");
         super();
         this.file = var1;
         String var11 = this.file.getName();
         Intrinsics.checkNotNullExpressionValue(var11, "getName(...)");
         this.abbr = StringsKt.take(StringsKt.removePrefix(var11, "hpms_"), 2);
         this.names = new HashMap<>();
         var var12 = new RandomAccessFile(this.file, "r");

         try {
            RandomAccessFile var14 = var12;
            this.buf = var14.getChannel().map(MapMode.READ_ONLY, 0L, var14.length());
            Unit var15 = Unit.INSTANCE;
         } catch (Throwable var10) {
            Throwable var3 = var10;

            try {
               throw var3;
            } finally {
               CloseableKt.closeFinally(var12, var10);
            }
         }

         CloseableKt.closeFinally(var12, null);
         this.buf.order(ByteOrder.LITTLE_ENDIAN);
         byte[] var16 = new byte[8];
         ByteBuffer var13 = this.buf;
         boolean var2 = false;
         ((Buffer)var13).position(0);
         this.buf.get(var16);
         if (Intrinsics.areEqual(new String(var16, Charsets.US_ASCII), "SVHPMS1\u0000")) {
            if (this.buf.getShort(8) == 1) {
               var2 = true;
            }

            if (var2) {
               this.state = this.buf.getShort(10) & '\uffff';
               this.year = this.buf.getShort(12) & '\uffff';
               this.tile = this.buf.getShort(14) & '\uffff';
               this.nNames = this.buf.getInt(16);
               this.nTiles = this.buf.getInt(20);
               this.nChunks = this.buf.getInt(24);
               this.oNames = this.buf.getInt(28);
               this.oTiles = this.buf.getInt(32);
               this.oCidx = this.buf.getInt(36);
               this.oChunks = this.buf.getInt(40);
               this.minTx = this.buf.getInt(48);
               this.minTy = this.buf.getInt(52);
               this.maxTx = this.buf.getInt(56);
               this.maxTy = this.buf.getInt(60);
            } else {
               throw new IllegalArgumentException("unknown version".toString());
            }
         } else {
            throw new IllegalArgumentException("not an HPMS pack".toString());
         }
      }

      private final long e5(double var1) {
         return MathKt.roundToLong(100000.0 * var1);
      }

      private final String name(int var1) {
         Map var8 = this.names;
         Integer var7 = var1;
         Object var6 = var8.get(var7);
         if (var6 == null) {
            int var4 = this.oNames;
            int var2 = this.nNames;
            int var3 = this.buf.getInt(this.oNames + var1 * 4);
            var6 = new byte[this.buf.getInt(this.oNames + (var1 + 1) * 4) - var3];
            var1 = 0;

            for (int var5 = ((Object[])var6).length; var1 < var5; var1++) {
               ((Object[])var6)[var1] = this.buf.get(var4 + (var2 + 1) * 4 + var3 + var1);
            }

            var6 = new String((byte[])var6, Charsets.UTF_8);
            var8.put(var7, var6);
         }

         return (String)var6;
      }

      private static final int section$u8(Hpms.Pack var0, IntRef var1, int var2) {
         int var3 = var0.buf.get(var1.element + var2) & 255;
         var2 = var3;
         if (var3 == 255) {
            var2 = -1;
         }

         return var2;
      }

      private static final long section$varint(Hpms.Pack var0, IntRef var1) {
         long var4 = 0L;
         byte var2 = 0;

         long var6;
         int var9;
         do {
            ByteBuffer var8 = var0.buf;
            int var3 = var1.element++;
            var9 = var8.get(var3) & 255;
            var6 = var4 | (long)(var9 & 127) << var2;
            var2 += 7;
            var4 = var6;
         } while (var9 >= 128);

         return var6 >>> 1 ^ -(1L & var6);
      }

      private final IntRange tileRange(int var1, int var2) {
         int var4 = 0;
         int var3 = this.nTiles;

         while (var4 < var3) {
            int var5 = var4 + var3 >>> 1;
            int var7 = this.oTiles + var5 * 16;
            int var6 = this.buf.getInt(var7);
            var7 = this.buf.getInt(var7 + 4);
            if (var6 < var1 || var6 == var1 && var7 < var2) {
               var4 = var5 + 1;
            } else {
               var3 = var5;
            }
         }

         if (var4 >= this.nTiles) {
            return IntRange.Companion.getEMPTY();
         } else {
            var3 = this.oTiles + var4 * 16;
            if (this.buf.getInt(var3) == var1 && this.buf.getInt(var3 + 4) == var2) {
               var1 = this.buf.getInt(var3 + 8);
               return RangesKt.until(var1, this.buf.getInt(var3 + 12) + var1);
            } else {
               return IntRange.Companion.getEMPTY();
            }
         }
      }

      public final boolean covers(double var1, double var3) {
         int var5 = (int)Math.floorDiv(this.e5(var3), (long)this.tile);
         int var6 = (int)Math.floorDiv(this.e5(var1), (long)this.tile);
         int var7 = this.minTx;
         boolean var8 = true;
         boolean var9;
         if (var5 <= this.maxTx + 1 && var7 - 1 <= var5) {
            var9 = 1;
         } else {
            var9 = 0;
         }

         if (var9) {
            var9 = this.minTy;
            boolean var11;
            if (var6 <= this.maxTy + 1 && var9 - 1 <= var6) {
               var11 = true;
            } else {
               var11 = false;
            }

            if (var11) {
               return var8;
            }
         }

         return false;
      }

      public final String getAbbr() {
         return this.abbr;
      }

      public final File getFile() {
         return this.file;
      }

      public final int getMaxTx() {
         return this.maxTx;
      }

      public final int getMaxTy() {
         return this.maxTy;
      }

      public final int getMinTx() {
         return this.minTx;
      }

      public final int getMinTy() {
         return this.minTy;
      }

      public final int getState() {
         return this.state;
      }

      public final int getYear() {
         return this.year;
      }

      public final List<Triple<Hpms.Section, Double, Double>> near(double var1, double var3, double var5) {
         int var26 = (int)Math.floorDiv(this.e5(var3), (long)this.tile);
         int var25 = (int)Math.floorDiv(this.e5(var1), (long)this.tile);
         double var7 = Math.cos(Math.toRadians(var1)) * 111320.0;
         double var9 = 110574.0;
         ArrayList var33 = new ArrayList();

         for (int var29 = -1; var29 < 2; var29++) {
            int var30 = -1;

            while (var30 < 2) {
               IntRange var34 = this.tileRange(var26 + var29, var25 + var30);
               int var31 = var34.getFirst();
               int var32 = var34.getLast();
               double var41;
               double var42;
               int var46;
               if (var31 > var32) {
                  var41 = var7;
                  var42 = var9;
                  var46 = var26;
               } else {
                  var41 = var9;
                  var9 = var7;

                  while (true) {
                     Hpms.Section var47 = this.section(var31);
                     double var15 = Double.MAX_VALUE;
                     var42 = 0.0;
                     var46 = var47.getLon().length;
                     int var27 = 0;
                     var7 = var41;

                     while (var27 < var46 - 1) {
                        double var23 = (var47.getLon()[var27] - var3) * var9;
                        double var17 = (var47.getLat()[var27] - var1) * var7;
                        double var19 = var47.getLon()[var27 + 1];
                        var41 = var47.getLat()[var27 + 1];
                        var19 = (var19 - var3) * var9 - var23;
                        double var21 = (var41 - var1) * var7 - var17;
                        var41 = var19 * var19 + var21 * var21;
                        if (var41 < 1.0E-6) {
                           var41 = 0.0;
                        } else {
                           var41 = RangesKt.coerceIn(-(var23 * var19 + var17 * var21) / var41, 0.0, 1.0);
                        }

                        var17 = Math.hypot(var23 + var41 * var19, var17 + var41 * var21);
                        var41 = var15;
                        if (var17 < var15) {
                           var42 = Math.atan2(var21, var19);
                           var41 = var17;
                        }

                        var27++;
                        var15 = var41;
                     }

                     var27 = var25;
                     if (var15 <= var5) {
                        var33.add(new Triple<>(var47, var15, var42));
                     }

                     var46 = var26;
                     var25 = var25;
                     var42 = var7;
                     var41 = var9;
                     if (var31 == var32) {
                        break;
                     }

                     var31++;
                     var25 = var27;
                     var41 = var7;
                  }
               }

               var30++;
               var26 = var46;
               var9 = var42;
               var7 = var41;
            }
         }

         return var33;
      }

      public final Hpms.Section section(int var1) {
         IntRef var10 = new IntRef();
         var10.element = this.oChunks + this.buf.getInt(this.oCidx + var1 * 4);
         int[] var11 = new int[8];

         for (int var12 = 0; var12 < 8; var12++) {
            var11[var12] = section$u8(this, var10, var12);
         }

         int var3 = this.buf.getInt(var10.element + 8);
         int var2 = this.buf.getShort(var10.element + 12) & '\uffff';
         long var6 = this.buf.getInt(var10.element + 14);
         long var4 = this.buf.getInt(var10.element + 18);
         var10.element += 22;
         double[] var9 = new double[var2];
         double[] var8 = new double[var2];
         var9[0] = var6 / 100000.0;
         var8[0] = var4 / 100000.0;

         for (int var13 = 1; var13 < var2; var13++) {
            var6 += section$varint(this, var10);
            var4 += section$varint(this, var10);
            var9[var13] = var6 / 100000.0;
            var8[var13] = var4 / 100000.0;
         }

         return new Hpms.Section(var11[0], var11[1], var11[2], var11[3], var11[4], var11[5], var11[6], var11[7], this.name(var3), var9, var8);
      }
   }

   public static final class Section {
      private final int access;
      private final int dirLanes;
      private final int fSystem;
      private final int facility;
      private final int laneWidthFt;
      private final int lanes;
      private final double[] lat;
      private final double[] lon;
      private final int median;
      private final String name;
      private final int structure;

      public Section(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, String var9, double[] var10, double[] var11) {
         Intrinsics.checkNotNullParameter(var9, "name");
         Intrinsics.checkNotNullParameter(var10, "lon");
         Intrinsics.checkNotNullParameter(var11, "lat");
         super();
         this.lanes = var1;
         this.dirLanes = var2;
         this.facility = var3;
         this.structure = var4;
         this.fSystem = var5;
         this.laneWidthFt = var6;
         this.access = var7;
         this.median = var8;
         this.name = var9;
         this.lon = var10;
         this.lat = var11;
      }

      public final int getAccess() {
         return this.access;
      }

      public final boolean getBridge() {
         int var1 = this.structure;
         boolean var2 = true;
         if (var1 != 1) {
            var2 = false;
         }

         return var2;
      }

      public final int getDirLanes() {
         return this.dirLanes;
      }

      public final int getFSystem() {
         return this.fSystem;
      }

      public final int getFacility() {
         return this.facility;
      }

      public final int getLaneWidthFt() {
         return this.laneWidthFt;
      }

      public final int getLanes() {
         return this.lanes;
      }

      public final double[] getLat() {
         return this.lat;
      }

      public final double[] getLon() {
         return this.lon;
      }

      public final int getMedian() {
         return this.median;
      }

      public final String getName() {
         return this.name;
      }

      public final boolean getOurLanesSure() {
         int var1 = this.dirLanes;
         boolean var2 = false;
         boolean var3;
         if (1 <= var1 && var1 < 21) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3 || this.facility != 2 || this.lanes % 2 == 0) {
            var2 = true;
         }

         return var2;
      }

      public final boolean getRamp() {
         boolean var1;
         if (this.facility == 4) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public final int getStructure() {
         return this.structure;
      }

      public final boolean getTunnel() {
         boolean var1;
         if (this.structure == 2) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public final int ourLanes() {
         int var1 = this.dirLanes;
         byte var2 = 0;
         boolean var3;
         if (1 <= var1 && var1 < 21) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         if (var3) {
            var3 = this.dirLanes;
         } else {
            var3 = this.lanes;
            boolean var6;
            if (1 <= var3 && var3 < 41) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (!var6) {
               var3 = var2;
            } else if (this.facility == 2) {
               var3 = (this.lanes + 1) / 2;
            } else {
               var3 = this.lanes;
            }
         }

         return var3;
      }
   }
}
