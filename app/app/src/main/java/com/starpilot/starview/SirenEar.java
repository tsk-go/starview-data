package com.starpilot.starview;


public final class SirenEar {
   public static final SirenEar INSTANCE = new SirenEar();
   private static double bandAvg;
   private static int loudFrames;
   private static volatile String status = "off";
   private static Thread thread;
   private static volatile boolean want;
   private static volatile boolean wantHorn;

   // $VF: synthetic method
   public static void $r8$lambda$BKmVQfQp_BZfoHF3CA4xo8vVSjM() {
      want$lambda$0();
   }

   private SirenEar() {
   }

   private final void fft(double[] var1, double[] var2) {
      int var22 = var1.length;
      int var19 = 0;

      for (int var20 = 1; var20 < var22; var20++) {
         int var21 = var22 >> 1;

         while (true) {
            int var23 = var19;
            var19 ^= var21;
            if ((var23 & var21) == 0) {
               if (var20 < var19) {
                  double var3 = var1[var20];
                  var1[var20] = var1[var19];
                  var1[var19] = var3;
                  var3 = var2[var20];
                  var2[var20] = var2[var19];
                  var2[var19] = var3;
               }
               break;
            }

            var21 >>= 1;
         }
      }

      byte var28 = 2;

      for (int var29 = var22; var28 <= var29; var28 <<= 1) {
         double var25 = (-Math.PI * 2) / var28;
         double var9 = Math.cos(var25);
         double var11 = Math.sin(var25);

         for (byte var30 = 0; var30 < var29; var30 += var28) {
            double var5 = 1.0;
            var25 = 0.0;
            var22 = 0;
            int var32 = var28 / 2;

            while (var22 < var32) {
               double var17 = var1[var30 + var22];
               double var13 = var2[var30 + var22];
               double var7 = var1[var30 + var22 + var28 / 2] * var5 - var2[var30 + var22 + var28 / 2] * var25;
               double var15 = var1[var30 + var22 + var28 / 2] * var25 + var2[var30 + var22 + var28 / 2] * var5;
               var1[var30 + var22] = var17 + var7;
               var2[var30 + var22] = var13 + var15;
               var1[var30 + var22 + var28 / 2] = var17 - var7;
               var2[var30 + var22 + var28 / 2] = var13 - var15;
               var7 = var5 * var11 + var25 * var9;
               var5 = var5 * var9 - var25 * var11;
               var22++;
               var25 = var7;
            }
         }
      }
   }

   private final void run() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: sipush 8000
      // 003: bipush 16
      // 005: bipush 2
      // 006: invokestatic android/media/AudioRecord.getMinBufferSize (III)I
      // 009: istore 7
      // 00b: new android/media/AudioRecord
      // 00e: dup
      // 00f: bipush 1
      // 010: sipush 8000
      // 013: bipush 16
      // 015: bipush 2
      // 016: iload 7
      // 018: sipush 512
      // 01b: bipush 4
      // 01c: imul
      // 01d: invokestatic java/lang/Math.max (II)I
      // 020: invokespecial android/media/AudioRecord.<init> (IIIII)V
      // 023: astore 20
      // 025: aload 20
      // 027: invokevirtual android/media/AudioRecord.getState ()I
      // 02a: bipush 1
      // 02b: if_icmpeq 03d
      // 02e: ldc "microphone not allowed"
      // 030: putstatic com/starpilot/starview/SirenEar.status Ljava/lang/String;
      // 033: aconst_null
      // 034: putstatic com/starpilot/starview/SirenEar.thread Ljava/lang/Thread;
      // 037: aload 20
      // 039: invokevirtual android/media/AudioRecord.release ()V
      // 03c: return
      // 03d: sipush 512
      // 040: newarray 9
      // 042: astore 16
      // 044: sipush 512
      // 047: newarray 7
      // 049: astore 18
      // 04b: sipush 512
      // 04e: newarray 7
      // 050: astore 17
      // 052: new kotlin/collections/ArrayDeque
      // 055: dup
      // 056: invokespecial kotlin/collections/ArrayDeque.<init> ()V
      // 059: astore 21
      // 05b: aload 16
      // 05d: astore 19
      // 05f: aload 18
      // 061: astore 19
      // 063: aload 17
      // 065: astore 19
      // 067: iload 7
      // 069: istore 8
      // 06b: aload 16
      // 06d: astore 19
      // 06f: aload 18
      // 071: astore 19
      // 073: aload 17
      // 075: astore 19
      // 077: iload 7
      // 079: istore 8
      // 07b: aload 20
      // 07d: invokevirtual android/media/AudioRecord.startRecording ()V
      // 080: aload 16
      // 082: astore 19
      // 084: aload 18
      // 086: astore 19
      // 088: aload 17
      // 08a: astore 19
      // 08c: iload 7
      // 08e: istore 8
      // 090: aload 16
      // 092: astore 19
      // 094: aload 18
      // 096: astore 19
      // 098: aload 17
      // 09a: astore 19
      // 09c: iload 7
      // 09e: istore 8
      // 0a0: ldc "listening"
      // 0a2: putstatic com/starpilot/starview/SirenEar.status Ljava/lang/String;
      // 0a5: aload 16
      // 0a7: astore 19
      // 0a9: aload 18
      // 0ab: astore 19
      // 0ad: aload 17
      // 0af: astore 19
      // 0b1: iload 7
      // 0b3: istore 8
      // 0b5: aload 16
      // 0b7: astore 19
      // 0b9: aload 18
      // 0bb: astore 19
      // 0bd: aload 17
      // 0bf: astore 19
      // 0c1: iload 7
      // 0c3: istore 8
      // 0c5: getstatic com/starpilot/starview/SirenEar.want Z
      // 0c8: istore 13
      // 0ca: iload 13
      // 0cc: ifeq 43f
      // 0cf: bipush 0
      // 0d0: istore 8
      // 0d2: iload 8
      // 0d4: sipush 512
      // 0d7: if_icmpge 104
      // 0da: aload 20
      // 0dc: aload 16
      // 0de: iload 8
      // 0e0: sipush 512
      // 0e3: iload 8
      // 0e5: isub
      // 0e6: invokevirtual android/media/AudioRecord.read ([SII)I
      // 0e9: istore 9
      // 0eb: iload 9
      // 0ed: ifle 104
      // 0f0: iload 8
      // 0f2: iload 9
      // 0f4: iadd
      // 0f5: istore 8
      // 0f7: goto 0d2
      // 0fa: astore 16
      // 0fc: goto 494
      // 0ff: astore 16
      // 101: goto 461
      // 104: bipush 0
      // 105: istore 8
      // 107: iload 8
      // 109: sipush 512
      // 10c: if_icmpge 152
      // 10f: aload 16
      // 111: iload 8
      // 113: saload
      // 114: istore 9
      // 116: iload 9
      // 118: i2d
      // 119: dstore 3
      // 11a: iload 8
      // 11c: i2d
      // 11d: ldc2_w 6.283185307179586
      // 120: dmul
      // 121: sipush 512
      // 124: bipush 1
      // 125: isub
      // 126: i2d
      // 127: ddiv
      // 128: dstore 1
      // 129: aload 18
      // 12b: iload 8
      // 12d: dload 3
      // 12e: ldc2_w 0.5
      // 131: dload 1
      // 132: invokestatic java/lang/Math.cos (D)D
      // 135: ldc2_w 0.5
      // 138: dmul
      // 139: dsub
      // 13a: dmul
      // 13b: dastore
      // 13c: aload 17
      // 13e: iload 8
      // 140: dconst_0
      // 141: dastore
      // 142: iinc 8 1
      // 145: goto 107
      // 148: astore 16
      // 14a: goto 494
      // 14d: astore 16
      // 14f: goto 461
      // 152: aload 0
      // 153: aload 18
      // 155: aload 17
      // 157: invokespecial com/starpilot/starview/SirenEar.fft ([D[D)V
      // 15a: sipush 512
      // 15d: sipush 500
      // 160: imul
      // 161: sipush 8000
      // 164: idiv
      // 165: istore 12
      // 167: sipush 512
      // 16a: sipush 1800
      // 16d: imul
      // 16e: sipush 8000
      // 171: idiv
      // 172: istore 11
      // 174: dconst_0
      // 175: dstore 1
      // 176: bipush 0
      // 177: istore 10
      // 179: bipush 0
      // 17a: istore 9
      // 17c: iload 11
      // 17e: iload 12
      // 180: isub
      // 181: bipush 1
      // 182: iadd
      // 183: newarray 7
      // 185: astore 19
      // 187: iload 12
      // 189: istore 8
      // 18b: dload 1
      // 18c: dstore 3
      // 18d: iload 8
      // 18f: iload 11
      // 191: if_icmpgt 1de
      // 194: dload 1
      // 195: dstore 3
      // 196: aload 18
      // 198: iload 8
      // 19a: daload
      // 19b: aload 18
      // 19d: iload 8
      // 19f: daload
      // 1a0: dmul
      // 1a1: aload 17
      // 1a3: iload 8
      // 1a5: daload
      // 1a6: aload 17
      // 1a8: iload 8
      // 1aa: daload
      // 1ab: dmul
      // 1ac: dadd
      // 1ad: dstore 5
      // 1af: aload 19
      // 1b1: iload 8
      // 1b3: iload 12
      // 1b5: isub
      // 1b6: dload 5
      // 1b8: dastore
      // 1b9: dload 3
      // 1ba: dstore 1
      // 1bb: dload 5
      // 1bd: dload 3
      // 1be: dcmpl
      // 1bf: ifle 1c9
      // 1c2: dload 5
      // 1c4: dstore 1
      // 1c5: iload 8
      // 1c7: istore 9
      // 1c9: iload 9
      // 1cb: istore 10
      // 1cd: dload 1
      // 1ce: dstore 3
      // 1cf: iload 8
      // 1d1: iload 11
      // 1d3: if_icmpeq 1de
      // 1d6: iinc 8 1
      // 1d9: dload 1
      // 1da: dstore 3
      // 1db: goto 196
      // 1de: aload 19
      // 1e0: invokestatic kotlin/collections/ArraysKt.sorted ([D)Ljava/util/List;
      // 1e3: aload 19
      // 1e5: arraylength
      // 1e6: bipush 2
      // 1e7: idiv
      // 1e8: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 1ed: checkcast java/lang/Number
      // 1f0: invokevirtual java/lang/Number.doubleValue ()D
      // 1f3: dstore 1
      // 1f4: dload 3
      // 1f5: bipush 40
      // 1f7: i2d
      // 1f8: dload 1
      // 1f9: dmul
      // 1fa: dcmpl
      // 1fb: ifle 217
      // 1fe: dload 3
      // 1ff: ldc2_w 1.0E8
      // 202: dcmpl
      // 203: ifle 217
      // 206: iload 10
      // 208: i2d
      // 209: sipush 8000
      // 20c: i2d
      // 20d: dmul
      // 20e: sipush 512
      // 211: i2d
      // 212: ddiv
      // 213: dstore 1
      // 214: goto 21b
      // 217: ldc2_w NaN
      // 21a: dstore 1
      // 21b: iload 11
      // 21d: istore 8
      // 21f: aload 21
      // 221: dload 1
      // 222: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 225: invokevirtual kotlin/collections/ArrayDeque.addLast (Ljava/lang/Object;)V
      // 228: getstatic com/starpilot/starview/SirenEar.wantHorn Z
      // 22b: istore 13
      // 22d: iload 13
      // 22f: ifeq 2fc
      // 232: dconst_0
      // 233: dstore 3
      // 234: sipush 512
      // 237: sipush 300
      // 23a: imul
      // 23b: sipush 8000
      // 23e: idiv
      // 23f: istore 9
      // 241: sipush 512
      // 244: sipush 700
      // 247: imul
      // 248: sipush 8000
      // 24b: idiv
      // 24c: istore 10
      // 24e: dload 3
      // 24f: dstore 1
      // 250: iload 9
      // 252: iload 10
      // 254: if_icmpgt 280
      // 257: dload 3
      // 258: aload 18
      // 25a: iload 9
      // 25c: daload
      // 25d: aload 18
      // 25f: iload 9
      // 261: daload
      // 262: dmul
      // 263: aload 17
      // 265: iload 9
      // 267: daload
      // 268: aload 17
      // 26a: iload 9
      // 26c: daload
      // 26d: dmul
      // 26e: dadd
      // 26f: dadd
      // 270: dstore 3
      // 271: dload 3
      // 272: dstore 1
      // 273: iload 9
      // 275: iload 10
      // 277: if_icmpeq 280
      // 27a: iinc 9 1
      // 27d: goto 257
      // 280: getstatic com/starpilot/starview/SirenEar.bandAvg D
      // 283: dconst_0
      // 284: dcmpg
      // 285: ifne 28e
      // 288: bipush 1
      // 289: istore 9
      // 28b: goto 291
      // 28e: bipush 0
      // 28f: istore 9
      // 291: iload 9
      // 293: ifeq 29a
      // 296: dload 1
      // 297: putstatic com/starpilot/starview/SirenEar.bandAvg D
      // 29a: dload 1
      // 29b: bipush 12
      // 29d: i2d
      // 29e: getstatic com/starpilot/starview/SirenEar.bandAvg D
      // 2a1: dmul
      // 2a2: dcmpl
      // 2a3: ifle 2b9
      // 2a6: dload 1
      // 2a7: ldc2_w 5.0E9
      // 2aa: dcmpl
      // 2ab: ifle 2b9
      // 2ae: getstatic com/starpilot/starview/SirenEar.loudFrames I
      // 2b1: bipush 1
      // 2b2: iadd
      // 2b3: putstatic com/starpilot/starview/SirenEar.loudFrames I
      // 2b6: goto 2fc
      // 2b9: getstatic com/starpilot/starview/SirenEar.loudFrames I
      // 2bc: istore 9
      // 2be: bipush 3
      // 2bf: iload 9
      // 2c1: if_icmpgt 2d1
      // 2c4: iload 9
      // 2c6: bipush 31
      // 2c8: if_icmpge 2d1
      // 2cb: bipush 1
      // 2cc: istore 9
      // 2ce: goto 2d4
      // 2d1: bipush 0
      // 2d2: istore 9
      // 2d4: iload 9
      // 2d6: ifeq 2e5
      // 2d9: getstatic com/starpilot/starview/Ideas.INSTANCE Lcom/starpilot/starview/Ideas;
      // 2dc: invokevirtual com/starpilot/starview/Ideas.getMore ()Lcom/starpilot/starview/IdeasMore;
      // 2df: invokestatic java/lang/System.currentTimeMillis ()J
      // 2e2: invokevirtual com/starpilot/starview/IdeasMore.setHornAtMs (J)V
      // 2e5: bipush 0
      // 2e6: putstatic com/starpilot/starview/SirenEar.loudFrames I
      // 2e9: getstatic com/starpilot/starview/SirenEar.bandAvg D
      // 2ec: dload 1
      // 2ed: getstatic com/starpilot/starview/SirenEar.bandAvg D
      // 2f0: dsub
      // 2f1: ldc2_w 0.05
      // 2f4: dmul
      // 2f5: dadd
      // 2f6: putstatic com/starpilot/starview/SirenEar.bandAvg D
      // 2f9: goto 2fc
      // 2fc: aload 21
      // 2fe: invokevirtual kotlin/collections/ArrayDeque.size ()I
      // 301: istore 9
      // 303: iload 9
      // 305: bipush 32
      // 307: if_icmple 313
      // 30a: aload 21
      // 30c: invokevirtual kotlin/collections/ArrayDeque.removeFirst ()Ljava/lang/Object;
      // 30f: pop
      // 310: goto 2fc
      // 313: aload 21
      // 315: invokevirtual kotlin/collections/ArrayDeque.size ()I
      // 318: bipush 32
      // 31a: if_icmpne 432
      // 31d: aload 21
      // 31f: checkcast java/lang/Iterable
      // 322: astore 22
      // 324: new java/util/ArrayList
      // 327: astore 19
      // 329: aload 19
      // 32b: invokespecial java/util/ArrayList.<init> ()V
      // 32e: aload 19
      // 330: checkcast java/util/Collection
      // 333: astore 19
      // 335: aload 22
      // 337: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 33c: astore 23
      // 33e: aload 23
      // 340: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 345: istore 13
      // 347: iload 13
      // 349: ifeq 370
      // 34c: aload 23
      // 34e: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 353: astore 22
      // 355: aload 22
      // 357: checkcast java/lang/Number
      // 35a: invokevirtual java/lang/Number.doubleValue ()D
      // 35d: invokestatic java/lang/Double.isNaN (D)Z
      // 360: ifne 36d
      // 363: aload 19
      // 365: aload 22
      // 367: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 36c: pop
      // 36d: goto 33e
      // 370: aload 19
      // 372: checkcast java/util/List
      // 375: astore 22
      // 377: aload 22
      // 379: invokeinterface java/util/List.size ()I 1
      // 37e: bipush 24
      // 380: if_icmplt 42f
      // 383: bipush 1
      // 384: istore 9
      // 386: ldc2_w NaN
      // 389: dstore 1
      // 38a: aload 21
      // 38c: invokevirtual kotlin/collections/ArrayDeque.iterator ()Ljava/util/Iterator;
      // 38f: astore 19
      // 391: aload 19
      // 393: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 398: istore 13
      // 39a: iload 13
      // 39c: ifeq 3ef
      // 39f: aload 19
      // 3a1: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 3a6: checkcast java/lang/Number
      // 3a9: invokevirtual java/lang/Number.doubleValue ()D
      // 3ac: dstore 3
      // 3ad: iload 9
      // 3af: istore 8
      // 3b1: dload 3
      // 3b2: invokestatic java/lang/Double.isNaN (D)Z
      // 3b5: ifne 3d7
      // 3b8: iload 9
      // 3ba: istore 8
      // 3bc: dload 1
      // 3bd: invokestatic java/lang/Double.isNaN (D)Z
      // 3c0: ifne 3d7
      // 3c3: iload 9
      // 3c5: istore 8
      // 3c7: dload 3
      // 3c8: dload 1
      // 3c9: dsub
      // 3ca: invokestatic java/lang/Math.abs (D)D
      // 3cd: ldc2_w 160.0
      // 3d0: dcmpl
      // 3d1: ifle 3d7
      // 3d4: bipush 0
      // 3d5: istore 8
      // 3d7: dload 3
      // 3d8: invokestatic java/lang/Double.isNaN (D)Z
      // 3db: istore 13
      // 3dd: iload 8
      // 3df: istore 9
      // 3e1: iload 13
      // 3e3: ifne 391
      // 3e6: dload 3
      // 3e7: dstore 1
      // 3e8: iload 8
      // 3ea: istore 9
      // 3ec: goto 391
      // 3ef: iload 9
      // 3f1: ifeq 42c
      // 3f4: aload 22
      // 3f6: checkcast java/lang/Iterable
      // 3f9: invokestatic kotlin/collections/CollectionsKt.maxOrThrow (Ljava/lang/Iterable;)D
      // 3fc: aload 22
      // 3fe: checkcast java/lang/Iterable
      // 401: invokestatic kotlin/collections/CollectionsKt.minOrThrow (Ljava/lang/Iterable;)D
      // 404: dsub
      // 405: ldc2_w 220.0
      // 408: dcmpl
      // 409: ifle 42c
      // 40c: getstatic com/starpilot/starview/Ideas.INSTANCE Lcom/starpilot/starview/Ideas;
      // 40f: invokevirtual com/starpilot/starview/Ideas.getSense ()Lcom/starpilot/starview/IdeasSense;
      // 412: astore 19
      // 414: invokestatic java/lang/System.currentTimeMillis ()J
      // 417: lstore 14
      // 419: aload 19
      // 41b: lload 14
      // 41d: ldc2_w 6000
      // 420: ladd
      // 421: invokevirtual com/starpilot/starview/IdeasSense.setSirenUntil (J)V
      // 424: goto 0a5
      // 427: astore 16
      // 429: goto 461
      // 42c: goto 0a5
      // 42f: goto 0a5
      // 432: goto 0a5
      // 435: astore 16
      // 437: goto 494
      // 43a: astore 16
      // 43c: goto 461
      // 43f: aload 20
      // 441: invokevirtual android/media/AudioRecord.stop ()V
      // 444: goto 449
      // 447: astore 16
      // 449: aload 20
      // 44b: invokevirtual android/media/AudioRecord.release ()V
      // 44e: aconst_null
      // 44f: putstatic com/starpilot/starview/SirenEar.thread Ljava/lang/Thread;
      // 452: ldc "off"
      // 454: putstatic com/starpilot/starview/SirenEar.status Ljava/lang/String;
      // 457: goto 491
      // 45a: astore 16
      // 45c: goto 494
      // 45f: astore 16
      // 461: aload 16
      // 463: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 466: astore 17
      // 468: new java/lang/StringBuilder
      // 46b: astore 16
      // 46d: aload 16
      // 46f: invokespecial java/lang/StringBuilder.<init> ()V
      // 472: aload 16
      // 474: ldc "error: "
      // 476: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 479: aload 17
      // 47b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 47e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 481: putstatic com/starpilot/starview/SirenEar.status Ljava/lang/String;
      // 484: aload 20
      // 486: invokevirtual android/media/AudioRecord.stop ()V
      // 489: goto 449
      // 48c: astore 16
      // 48e: goto 449
      // 491: return
      // 492: astore 16
      // 494: aload 20
      // 496: invokevirtual android/media/AudioRecord.stop ()V
      // 499: goto 49e
      // 49c: astore 17
      // 49e: aload 20
      // 4a0: invokevirtual android/media/AudioRecord.release ()V
      // 4a3: aconst_null
      // 4a4: putstatic com/starpilot/starview/SirenEar.thread Ljava/lang/Thread;
      // 4a7: ldc "off"
      // 4a9: putstatic com/starpilot/starview/SirenEar.status Ljava/lang/String;
      // 4ac: aload 16
      // 4ae: athrow
      // 4af: astore 16
      // 4b1: aload 16
      // 4b3: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 4b6: astore 16
      // 4b8: new java/lang/StringBuilder
      // 4bb: dup
      // 4bc: invokespecial java/lang/StringBuilder.<init> ()V
      // 4bf: ldc_w "no microphone: "
      // 4c2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4c5: aload 16
      // 4c7: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4ca: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 4cd: putstatic com/starpilot/starview/SirenEar.status Ljava/lang/String;
      // 4d0: aconst_null
      // 4d1: putstatic com/starpilot/starview/SirenEar.thread Ljava/lang/Thread;
      // 4d4: return
   }

   private static final void want$lambda$0() {
      INSTANCE.run();
   }

   public final String getStatus() {
      return status;
   }

   public final boolean getWantHorn() {
      return wantHorn;
   }

   public final void setWantHorn(boolean var1) {
      wantHorn = var1;
   }

   public final void want(boolean var1) {
      boolean var2;
      if (!var1 && !wantHorn) {
         var2 = false;
      } else {
         var2 = true;
      }

      want = var2;
      if (var1 && thread == null) {
         Thread var3 = new Thread(new SirenEar$$ExternalSyntheticLambda0(), "siren-ear");
         var3.setDaemon(true);
         var3.setPriority(1);
         var3.start();
         thread = var3;
      }
   }
}
