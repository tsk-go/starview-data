package com.starpilot.starview;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class ToolLines {
   public static final ToolLines INSTANCE = new ToolLines();

   private ToolLines() {
   }

   public final String endpoint(String var1) {
      Intrinsics.checkNotNullParameter(var1, "path");
      if (Intrinsics.areEqual(var1, "/")) {
         var1 = "/api/drives/stats";
      } else if (StringsKt.contains$default(var1, "recording", false, 2, null)) {
         var1 = "/api/recordings/local";
      } else if (StringsKt.contains$default(var1, "manage_models", false, 2, null)) {
         var1 = "/api/models/status";
      } else if (StringsKt.contains$default(var1, "logs", false, 2, null)) {
         var1 = "/api/crash-reports";
      } else if (StringsKt.contains$default(var1, "bluetooth", false, 2, null)) {
         var1 = "/api/bluetooth/status";
      } else if (StringsKt.contains$default(var1, "galaxy", false, 2, null)) {
         var1 = "/api/galaxy/status";
      } else if (StringsKt.contains$default(var1, "navigation", false, 2, null)) {
         var1 = "/api/navigation/offline";
      } else {
         var1 = null;
      }

      return var1;
   }

   public final String line(String param1, JSONObject param2) {
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
      // 001: ldc "path"
      // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 006: aload 2
      // 007: ldc "o"
      // 009: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00c: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 00f: astore 10
      // 011: aload 0
      // 012: checkcast com/starpilot/starview/ToolLines
      // 015: astore 10
      // 017: aload 1
      // 018: ldc "/"
      // 01a: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 01d: istore 9
      // 01f: bipush 0
      // 020: istore 5
      // 022: iload 9
      // 024: ifeq 13b
      // 027: aload 2
      // 028: ldc "totals"
      // 02a: invokevirtual org/json/JSONObject.optJSONObject (Ljava/lang/String;)Lorg/json/JSONObject;
      // 02d: astore 2
      // 02e: aload 2
      // 02f: ifnull 136
      // 032: aload 2
      // 033: ldc "distanceMeters"
      // 035: ldc2_w NaN
      // 038: invokevirtual org/json/JSONObject.optDouble (Ljava/lang/String;D)D
      // 03b: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 03e: astore 1
      // 03f: aload 1
      // 040: checkcast java/lang/Number
      // 043: invokevirtual java/lang/Number.doubleValue ()D
      // 046: dstore 3
      // 047: dload 3
      // 048: invokestatic java/lang/Double.isInfinite (D)Z
      // 04b: ifne 05b
      // 04e: dload 3
      // 04f: invokestatic java/lang/Double.isNaN (D)Z
      // 052: ifne 05b
      // 055: bipush 1
      // 056: istore 5
      // 058: goto 05e
      // 05b: bipush 0
      // 05c: istore 5
      // 05e: iload 5
      // 060: ifeq 066
      // 063: goto 068
      // 066: aconst_null
      // 067: astore 1
      // 068: aload 1
      // 069: ifnull 081
      // 06c: aload 1
      // 06d: checkcast java/lang/Number
      // 070: invokevirtual java/lang/Number.doubleValue ()D
      // 073: ldc2_w 1609.344
      // 076: ddiv
      // 077: invokestatic java/lang/Math.round (D)J
      // 07a: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 07d: astore 1
      // 07e: goto 083
      // 081: aconst_null
      // 082: astore 1
      // 083: aload 2
      // 084: ldc "drives"
      // 086: bipush -1
      // 087: invokevirtual org/json/JSONObject.optInt (Ljava/lang/String;I)I
      // 08a: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 08d: astore 2
      // 08e: aload 2
      // 08f: checkcast java/lang/Number
      // 092: invokevirtual java/lang/Number.intValue ()I
      // 095: iflt 09e
      // 098: bipush 1
      // 099: istore 5
      // 09b: goto 0a1
      // 09e: bipush 0
      // 09f: istore 5
      // 0a1: iload 5
      // 0a3: ifeq 0a9
      // 0a6: goto 0ab
      // 0a9: aconst_null
      // 0aa: astore 2
      // 0ab: aload 2
      // 0ac: ifnull 0d2
      // 0af: aload 2
      // 0b0: checkcast java/lang/Number
      // 0b3: invokevirtual java/lang/Number.intValue ()I
      // 0b6: istore 5
      // 0b8: new java/lang/StringBuilder
      // 0bb: astore 2
      // 0bc: aload 2
      // 0bd: invokespecial java/lang/StringBuilder.<init> ()V
      // 0c0: aload 2
      // 0c1: iload 5
      // 0c3: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 0c6: ldc " drives"
      // 0c8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0cb: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0ce: astore 2
      // 0cf: goto 0d4
      // 0d2: aconst_null
      // 0d3: astore 2
      // 0d4: aload 1
      // 0d5: ifnull 0fb
      // 0d8: aload 1
      // 0d9: checkcast java/lang/Number
      // 0dc: invokevirtual java/lang/Number.longValue ()J
      // 0df: lstore 7
      // 0e1: new java/lang/StringBuilder
      // 0e4: astore 1
      // 0e5: aload 1
      // 0e6: invokespecial java/lang/StringBuilder.<init> ()V
      // 0e9: aload 1
      // 0ea: lload 7
      // 0ec: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 0ef: ldc " mi"
      // 0f1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0f4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0f7: astore 1
      // 0f8: goto 0fd
      // 0fb: aconst_null
      // 0fc: astore 1
      // 0fd: bipush 2
      // 0fe: anewarray 179
      // 101: dup
      // 102: bipush 0
      // 103: aload 2
      // 104: aastore
      // 105: dup
      // 106: bipush 1
      // 107: aload 1
      // 108: aastore
      // 109: invokestatic kotlin/collections/CollectionsKt.listOfNotNull ([Ljava/lang/Object;)Ljava/util/List;
      // 10c: checkcast java/lang/Iterable
      // 10f: ldc " · "
      // 111: checkcast java/lang/CharSequence
      // 114: aconst_null
      // 115: aconst_null
      // 116: bipush 0
      // 117: aconst_null
      // 118: aconst_null
      // 119: bipush 62
      // 11b: aconst_null
      // 11c: invokestatic kotlin/collections/CollectionsKt.joinToString$default (Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;
      // 11f: checkcast java/lang/CharSequence
      // 122: astore 2
      // 123: aload 2
      // 124: astore 1
      // 125: aload 2
      // 126: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 129: ifeq 12e
      // 12c: aconst_null
      // 12d: astore 1
      // 12e: aload 1
      // 12f: checkcast java/lang/String
      // 132: astore 1
      // 133: goto 138
      // 136: aconst_null
      // 137: astore 1
      // 138: goto 4f8
      // 13b: aload 1
      // 13c: checkcast java/lang/CharSequence
      // 13f: astore 10
      // 141: ldc "recording"
      // 143: checkcast java/lang/CharSequence
      // 146: astore 11
      // 148: aload 10
      // 14a: aload 11
      // 14c: bipush 0
      // 14d: bipush 2
      // 14e: aconst_null
      // 14f: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      // 152: istore 9
      // 154: ldc ""
      // 156: astore 10
      // 158: iload 9
      // 15a: ifeq 228
      // 15d: aload 2
      // 15e: ldc "routes"
      // 160: invokevirtual org/json/JSONObject.optJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
      // 163: astore 1
      // 164: aload 1
      // 165: ifnull 223
      // 168: bipush 0
      // 169: istore 9
      // 16b: bipush 0
      // 16c: aload 1
      // 16d: invokevirtual org/json/JSONArray.length ()I
      // 170: invokestatic kotlin/ranges/RangesKt.until (II)Lkotlin/ranges/IntRange;
      // 173: checkcast java/lang/Iterable
      // 176: astore 2
      // 177: aload 2
      // 178: instanceof java/util/Collection
      // 17b: ifeq 18d
      // 17e: aload 2
      // 17f: checkcast java/util/Collection
      // 182: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 187: ifeq 18d
      // 18a: goto 1dc
      // 18d: bipush 0
      // 18e: istore 5
      // 190: aload 2
      // 191: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 196: astore 2
      // 197: aload 2
      // 198: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 19d: ifeq 1dc
      // 1a0: aload 1
      // 1a1: aload 2
      // 1a2: checkcast kotlin/collections/IntIterator
      // 1a5: invokevirtual kotlin/collections/IntIterator.nextInt ()I
      // 1a8: invokevirtual org/json/JSONArray.optJSONObject (I)Lorg/json/JSONObject;
      // 1ab: astore 11
      // 1ad: aload 11
      // 1af: ifnull 1c3
      // 1b2: aload 11
      // 1b4: ldc "preserved"
      // 1b6: invokevirtual org/json/JSONObject.optBoolean (Ljava/lang/String;)Z
      // 1b9: bipush 1
      // 1ba: if_icmpne 1c3
      // 1bd: bipush 1
      // 1be: istore 6
      // 1c0: goto 1c6
      // 1c3: bipush 0
      // 1c4: istore 6
      // 1c6: iload 6
      // 1c8: ifeq 1d9
      // 1cb: iinc 5 1
      // 1ce: iload 5
      // 1d0: ifge 1d6
      // 1d3: invokestatic kotlin/collections/CollectionsKt.throwCountOverflow ()V
      // 1d6: goto 197
      // 1d9: goto 197
      // 1dc: aload 1
      // 1dd: invokevirtual org/json/JSONArray.length ()I
      // 1e0: istore 6
      // 1e2: iload 5
      // 1e4: ifle 204
      // 1e7: new java/lang/StringBuilder
      // 1ea: astore 1
      // 1eb: aload 1
      // 1ec: invokespecial java/lang/StringBuilder.<init> ()V
      // 1ef: aload 1
      // 1f0: ldc " · "
      // 1f2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1f5: iload 5
      // 1f7: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1fa: ldc " kept"
      // 1fc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1ff: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 202: astore 10
      // 204: new java/lang/StringBuilder
      // 207: astore 1
      // 208: aload 1
      // 209: invokespecial java/lang/StringBuilder.<init> ()V
      // 20c: aload 1
      // 20d: iload 6
      // 20f: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 212: ldc " drives"
      // 214: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 217: aload 10
      // 219: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 21c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 21f: astore 1
      // 220: goto 225
      // 223: aconst_null
      // 224: astore 1
      // 225: goto 4f8
      // 228: aload 1
      // 229: checkcast java/lang/CharSequence
      // 22c: astore 11
      // 22e: ldc "manage_models"
      // 230: checkcast java/lang/CharSequence
      // 233: astore 12
      // 235: aload 11
      // 237: aload 12
      // 239: bipush 0
      // 23a: bipush 2
      // 23b: aconst_null
      // 23c: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      // 23f: istore 9
      // 241: iload 9
      // 243: ifeq 29c
      // 246: aload 2
      // 247: ldc "loadedName"
      // 249: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 24c: checkcast java/lang/CharSequence
      // 24f: astore 10
      // 251: aload 10
      // 253: astore 1
      // 254: aload 10
      // 256: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 259: ifeq 264
      // 25c: aload 2
      // 25d: ldc_w "loadedId"
      // 260: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 263: astore 1
      // 264: aload 1
      // 265: checkcast java/lang/CharSequence
      // 268: astore 2
      // 269: aload 2
      // 26a: astore 1
      // 26b: aload 2
      // 26c: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 26f: ifeq 274
      // 272: aconst_null
      // 273: astore 1
      // 274: aload 1
      // 275: checkcast java/lang/String
      // 278: astore 1
      // 279: aload 1
      // 27a: ifnull 297
      // 27d: new java/lang/StringBuilder
      // 280: astore 2
      // 281: aload 2
      // 282: invokespecial java/lang/StringBuilder.<init> ()V
      // 285: aload 2
      // 286: ldc_w "Running: "
      // 289: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 28c: aload 1
      // 28d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 290: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 293: astore 1
      // 294: goto 4f8
      // 297: aconst_null
      // 298: astore 1
      // 299: goto 4f8
      // 29c: aload 1
      // 29d: checkcast java/lang/CharSequence
      // 2a0: astore 11
      // 2a2: ldc "logs"
      // 2a4: checkcast java/lang/CharSequence
      // 2a7: astore 12
      // 2a9: aload 11
      // 2ab: aload 12
      // 2ad: bipush 0
      // 2ae: bipush 2
      // 2af: aconst_null
      // 2b0: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      // 2b3: istore 9
      // 2b5: iload 9
      // 2b7: ifeq 32f
      // 2ba: aload 2
      // 2bb: ldc_w "reportCount"
      // 2be: bipush -1
      // 2bf: invokevirtual org/json/JSONObject.optInt (Ljava/lang/String;I)I
      // 2c2: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 2c5: astore 1
      // 2c6: aload 1
      // 2c7: checkcast java/lang/Number
      // 2ca: invokevirtual java/lang/Number.intValue ()I
      // 2cd: iflt 2d6
      // 2d0: bipush 1
      // 2d1: istore 5
      // 2d3: goto 2d9
      // 2d6: bipush 0
      // 2d7: istore 5
      // 2d9: iload 5
      // 2db: ifeq 2e1
      // 2de: goto 2e3
      // 2e1: aconst_null
      // 2e2: astore 1
      // 2e3: aload 1
      // 2e4: ifnull 32a
      // 2e7: aload 1
      // 2e8: checkcast java/lang/Number
      // 2eb: invokevirtual java/lang/Number.intValue ()I
      // 2ee: istore 5
      // 2f0: iload 5
      // 2f2: ifne 2fc
      // 2f5: ldc_w "No crash reports"
      // 2f8: astore 1
      // 2f9: goto 327
      // 2fc: iload 5
      // 2fe: bipush 1
      // 2ff: if_icmpne 305
      // 302: goto 30a
      // 305: ldc_w "s"
      // 308: astore 10
      // 30a: new java/lang/StringBuilder
      // 30d: astore 1
      // 30e: aload 1
      // 30f: invokespecial java/lang/StringBuilder.<init> ()V
      // 312: aload 1
      // 313: iload 5
      // 315: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 318: ldc_w " crash report"
      // 31b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 31e: aload 10
      // 320: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 323: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 326: astore 1
      // 327: goto 32c
      // 32a: aconst_null
      // 32b: astore 1
      // 32c: goto 4f8
      // 32f: aload 1
      // 330: checkcast java/lang/CharSequence
      // 333: astore 11
      // 335: ldc "bluetooth"
      // 337: checkcast java/lang/CharSequence
      // 33a: astore 12
      // 33c: aload 11
      // 33e: aload 12
      // 340: bipush 0
      // 341: bipush 2
      // 342: aconst_null
      // 343: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      // 346: istore 9
      // 348: iload 9
      // 34a: ifeq 436
      // 34d: aload 2
      // 34e: ldc_w "powered"
      // 351: bipush 0
      // 352: invokevirtual org/json/JSONObject.optBoolean (Ljava/lang/String;Z)Z
      // 355: ifne 35f
      // 358: ldc_w "Bluetooth off"
      // 35b: astore 1
      // 35c: goto 4f8
      // 35f: aload 2
      // 360: ldc_w "devices"
      // 363: invokevirtual org/json/JSONObject.optJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
      // 366: astore 1
      // 367: aload 1
      // 368: ifnull 431
      // 36b: bipush 0
      // 36c: aload 1
      // 36d: invokevirtual org/json/JSONArray.length ()I
      // 370: invokestatic kotlin/ranges/RangesKt.until (II)Lkotlin/ranges/IntRange;
      // 373: checkcast java/lang/Iterable
      // 376: astore 2
      // 377: aload 2
      // 378: instanceof java/util/Collection
      // 37b: ifeq 390
      // 37e: aload 2
      // 37f: checkcast java/util/Collection
      // 382: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 387: ifeq 390
      // 38a: bipush 0
      // 38b: istore 5
      // 38d: goto 3e8
      // 390: bipush 0
      // 391: istore 5
      // 393: aload 2
      // 394: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 399: astore 10
      // 39b: aload 10
      // 39d: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 3a2: ifeq 3e8
      // 3a5: aload 1
      // 3a6: aload 10
      // 3a8: checkcast kotlin/collections/IntIterator
      // 3ab: invokevirtual kotlin/collections/IntIterator.nextInt ()I
      // 3ae: invokevirtual org/json/JSONArray.optJSONObject (I)Lorg/json/JSONObject;
      // 3b1: astore 2
      // 3b2: aload 2
      // 3b3: ifnull 3c7
      // 3b6: aload 2
      // 3b7: ldc_w "connected"
      // 3ba: invokevirtual org/json/JSONObject.optBoolean (Ljava/lang/String;)Z
      // 3bd: bipush 1
      // 3be: if_icmpne 3c7
      // 3c1: bipush 1
      // 3c2: istore 6
      // 3c4: goto 3ca
      // 3c7: bipush 0
      // 3c8: istore 6
      // 3ca: iload 6
      // 3cc: ifeq 39b
      // 3cf: iload 5
      // 3d1: bipush 1
      // 3d2: iadd
      // 3d3: istore 6
      // 3d5: iload 6
      // 3d7: istore 5
      // 3d9: iload 6
      // 3db: ifge 39b
      // 3de: invokestatic kotlin/collections/CollectionsKt.throwCountOverflow ()V
      // 3e1: iload 6
      // 3e3: istore 5
      // 3e5: goto 39b
      // 3e8: iload 5
      // 3ea: ifle 40a
      // 3ed: new java/lang/StringBuilder
      // 3f0: astore 1
      // 3f1: aload 1
      // 3f2: invokespecial java/lang/StringBuilder.<init> ()V
      // 3f5: aload 1
      // 3f6: iload 5
      // 3f8: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 3fb: ldc_w " connected"
      // 3fe: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 401: astore 1
      // 402: aload 1
      // 403: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 406: astore 1
      // 407: goto 42e
      // 40a: aload 1
      // 40b: invokevirtual org/json/JSONArray.length ()I
      // 40e: istore 5
      // 410: new java/lang/StringBuilder
      // 413: astore 1
      // 414: aload 1
      // 415: invokespecial java/lang/StringBuilder.<init> ()V
      // 418: aload 1
      // 419: ldc_w "On · "
      // 41c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 41f: iload 5
      // 421: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 424: ldc_w " known"
      // 427: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 42a: astore 1
      // 42b: goto 402
      // 42e: goto 35c
      // 431: aconst_null
      // 432: astore 1
      // 433: goto 35c
      // 436: aload 1
      // 437: checkcast java/lang/CharSequence
      // 43a: astore 11
      // 43c: ldc "galaxy"
      // 43e: checkcast java/lang/CharSequence
      // 441: astore 12
      // 443: aload 11
      // 445: aload 12
      // 447: bipush 0
      // 448: bipush 2
      // 449: aconst_null
      // 44a: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      // 44d: istore 9
      // 44f: iload 9
      // 451: ifeq 46c
      // 454: aload 2
      // 455: ldc_w "paired"
      // 458: invokevirtual org/json/JSONObject.optBoolean (Ljava/lang/String;)Z
      // 45b: ifeq 465
      // 45e: ldc_w "Remote access paired"
      // 461: astore 1
      // 462: goto 469
      // 465: ldc_w "Remote access not paired"
      // 468: astore 1
      // 469: goto 4f8
      // 46c: aload 1
      // 46d: checkcast java/lang/CharSequence
      // 470: astore 11
      // 472: ldc "navigation"
      // 474: checkcast java/lang/CharSequence
      // 477: astore 1
      // 478: aload 11
      // 47a: aload 1
      // 47b: bipush 0
      // 47c: bipush 2
      // 47d: aconst_null
      // 47e: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      // 481: ifeq 4f6
      // 484: aload 2
      // 485: ldc_w "areas"
      // 488: invokevirtual org/json/JSONObject.optJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
      // 48b: astore 1
      // 48c: aload 1
      // 48d: ifnull 49f
      // 490: aload 1
      // 491: invokevirtual org/json/JSONArray.length ()I
      // 494: istore 5
      // 496: iload 5
      // 498: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 49b: astore 1
      // 49c: goto 4b6
      // 49f: aload 2
      // 4a0: ldc_w "areas"
      // 4a3: invokevirtual org/json/JSONObject.optJSONObject (Ljava/lang/String;)Lorg/json/JSONObject;
      // 4a6: astore 1
      // 4a7: aload 1
      // 4a8: ifnull 4b4
      // 4ab: aload 1
      // 4ac: invokevirtual org/json/JSONObject.length ()I
      // 4af: istore 5
      // 4b1: goto 496
      // 4b4: aconst_null
      // 4b5: astore 1
      // 4b6: aload 1
      // 4b7: ifnull 4f1
      // 4ba: aload 1
      // 4bb: checkcast java/lang/Number
      // 4be: invokevirtual java/lang/Number.intValue ()I
      // 4c1: istore 5
      // 4c3: iload 5
      // 4c5: bipush 1
      // 4c6: if_icmpne 4cc
      // 4c9: goto 4d1
      // 4cc: ldc_w "s"
      // 4cf: astore 10
      // 4d1: new java/lang/StringBuilder
      // 4d4: astore 1
      // 4d5: aload 1
      // 4d6: invokespecial java/lang/StringBuilder.<init> ()V
      // 4d9: aload 1
      // 4da: iload 5
      // 4dc: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 4df: ldc_w " offline map area"
      // 4e2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4e5: aload 10
      // 4e7: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 4ea: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 4ed: astore 1
      // 4ee: goto 4f8
      // 4f1: aconst_null
      // 4f2: astore 1
      // 4f3: goto 4f8
      // 4f6: aconst_null
      // 4f7: astore 1
      // 4f8: aload 1
      // 4f9: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 4fc: astore 1
      // 4fd: goto 519
      // 500: astore 1
      // 501: goto 50d
      // 504: astore 1
      // 505: goto 50d
      // 508: astore 1
      // 509: goto 50d
      // 50c: astore 1
      // 50d: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 510: astore 2
      // 511: aload 1
      // 512: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 515: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 518: astore 1
      // 519: aload 1
      // 51a: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 51d: ifeq 525
      // 520: aconst_null
      // 521: astore 1
      // 522: goto 525
      // 525: aload 1
      // 526: checkcast java/lang/String
      // 529: areturn
   }
}
