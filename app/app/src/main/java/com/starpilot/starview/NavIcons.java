package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.HashMap;

public final class NavIcons {
   public static final NavIcons INSTANCE = new NavIcons();
   private static final HashMap<String, Bitmap> cache = new HashMap<>();

   private NavIcons() {
   }

   public final Bitmap icon(Context param1, String param2, String param3) {
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
      // 000: aload 1
      // 001: ldc "ctx"
      // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 006: aload 2
      // 007: ldc "type"
      // 009: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00c: aload 3
      // 00d: ldc "modifier"
      // 00f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 012: aload 2
      // 013: checkcast java/lang/CharSequence
      // 016: astore 2
      // 017: aload 2
      // 018: invokeinterface java/lang/CharSequence.length ()I 1
      // 01d: ifne 026
      // 020: bipush 1
      // 021: istore 4
      // 023: goto 029
      // 026: bipush 0
      // 027: istore 4
      // 029: iload 4
      // 02b: ifeq 031
      // 02e: ldc "turn"
      // 030: astore 2
      // 031: aload 2
      // 032: checkcast java/lang/String
      // 035: astore 6
      // 037: aload 6
      // 039: astore 2
      // 03a: aload 6
      // 03c: ldc "rotary"
      // 03e: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 041: ifeq 047
      // 044: ldc "roundabout"
      // 046: astore 2
      // 047: aload 2
      // 048: invokevirtual java/lang/String.hashCode ()I
      // 04b: lookupswitch 41 4 -1335343116 80 -567202649 68 595233003 56 1318962443 44
      // 074: goto 0a7
      // 077: aload 2
      // 078: ldc "new name"
      // 07a: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 07d: ifne 0a4
      // 080: goto 074
      // 083: aload 2
      // 084: ldc "notification"
      // 086: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 089: ifne 0a4
      // 08c: goto 074
      // 08f: aload 2
      // 090: ldc "continue"
      // 092: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 095: ifne 0a4
      // 098: goto 074
      // 09b: aload 2
      // 09c: ldc "depart"
      // 09e: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 0a1: ifeq 074
      // 0a4: ldc "turn"
      // 0a6: astore 2
      // 0a7: aload 2
      // 0a8: ldc " "
      // 0aa: ldc "_"
      // 0ac: bipush 0
      // 0ad: bipush 4
      // 0ae: aconst_null
      // 0af: invokestatic kotlin/text/StringsKt.replace$default (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Ljava/lang/String;
      // 0b2: astore 7
      // 0b4: ldc "left"
      // 0b6: ldc "left"
      // 0b8: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 0bb: astore 9
      // 0bd: ldc "right"
      // 0bf: ldc "right"
      // 0c1: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 0c4: astore 2
      // 0c5: ldc "straight"
      // 0c7: ldc "straight"
      // 0c9: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 0cc: astore 10
      // 0ce: ldc "slightLeft"
      // 0d0: ldc "slight_left"
      // 0d2: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 0d5: astore 8
      // 0d7: ldc "slightRight"
      // 0d9: ldc "slight_right"
      // 0db: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 0de: astore 6
      // 0e0: ldc "sharpLeft"
      // 0e2: ldc "sharp_left"
      // 0e4: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 0e7: astore 11
      // 0e9: bipush 7
      // 0eb: anewarray 122
      // 0ee: dup
      // 0ef: bipush 0
      // 0f0: aload 9
      // 0f2: aastore
      // 0f3: dup
      // 0f4: bipush 1
      // 0f5: aload 2
      // 0f6: aastore
      // 0f7: dup
      // 0f8: bipush 2
      // 0f9: aload 10
      // 0fb: aastore
      // 0fc: dup
      // 0fd: bipush 3
      // 0fe: aload 8
      // 100: aastore
      // 101: dup
      // 102: bipush 4
      // 103: aload 6
      // 105: aastore
      // 106: dup
      // 107: bipush 5
      // 108: aload 11
      // 10a: aastore
      // 10b: dup
      // 10c: bipush 6
      // 10e: ldc "sharpRight"
      // 110: ldc "sharp_right"
      // 112: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 115: aastore
      // 116: invokestatic kotlin/collections/MapsKt.mapOf ([Lkotlin/Pair;)Ljava/util/Map;
      // 119: astore 2
      // 11a: aload 2
      // 11b: aload 3
      // 11c: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 121: checkcast java/lang/String
      // 124: astore 6
      // 126: aload 6
      // 128: astore 2
      // 129: aload 6
      // 12b: ifnonnull 131
      // 12e: ldc ""
      // 130: astore 2
      // 131: aload 3
      // 132: ldc "uturn"
      // 134: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 137: ifeq 140
      // 13a: ldc "direction_uturn"
      // 13c: astore 3
      // 13d: goto 18f
      // 140: aload 2
      // 141: checkcast java/lang/CharSequence
      // 144: invokeinterface java/lang/CharSequence.length ()I 1
      // 149: ifne 152
      // 14c: bipush 1
      // 14d: istore 4
      // 14f: goto 155
      // 152: bipush 0
      // 153: istore 4
      // 155: iload 4
      // 157: ifeq 16f
      // 15a: new java/lang/StringBuilder
      // 15d: dup
      // 15e: invokespecial java/lang/StringBuilder.<init> ()V
      // 161: ldc "direction_"
      // 163: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 166: aload 7
      // 168: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 16b: astore 3
      // 16c: goto 18a
      // 16f: new java/lang/StringBuilder
      // 172: dup
      // 173: invokespecial java/lang/StringBuilder.<init> ()V
      // 176: ldc "direction_"
      // 178: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 17b: aload 7
      // 17d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 180: ldc "_"
      // 182: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 185: aload 2
      // 186: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 189: astore 3
      // 18a: aload 3
      // 18b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 18e: astore 3
      // 18f: getstatic com/starpilot/starview/NavIcons.cache Ljava/util/HashMap;
      // 192: astore 7
      // 194: aload 7
      // 196: monitorenter
      // 197: getstatic com/starpilot/starview/NavIcons.cache Ljava/util/HashMap;
      // 19a: checkcast java/util/Map
      // 19d: astore 8
      // 19f: aload 8
      // 1a1: aload 3
      // 1a2: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 1a7: astore 6
      // 1a9: aload 6
      // 1ab: ifnonnull 244
      // 1ae: aload 1
      // 1af: invokevirtual android/content/Context.getResources ()Landroid/content/res/Resources;
      // 1b2: astore 9
      // 1b4: aload 9
      // 1b6: aload 3
      // 1b7: ldc "drawable"
      // 1b9: aload 1
      // 1ba: invokevirtual android/content/Context.getPackageName ()Ljava/lang/String;
      // 1bd: invokevirtual android/content/res/Resources.getIdentifier (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
      // 1c0: istore 5
      // 1c2: iload 5
      // 1c4: ifne 209
      // 1c7: aload 2
      // 1c8: checkcast java/lang/CharSequence
      // 1cb: invokeinterface java/lang/CharSequence.length ()I 1
      // 1d0: ifle 1d9
      // 1d3: bipush 1
      // 1d4: istore 4
      // 1d6: goto 1dc
      // 1d9: bipush 0
      // 1da: istore 4
      // 1dc: iload 4
      // 1de: ifeq 209
      // 1e1: new java/lang/StringBuilder
      // 1e4: astore 6
      // 1e6: aload 6
      // 1e8: invokespecial java/lang/StringBuilder.<init> ()V
      // 1eb: aload 9
      // 1ed: aload 6
      // 1ef: ldc "direction_turn_"
      // 1f1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1f4: aload 2
      // 1f5: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1f8: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1fb: ldc "drawable"
      // 1fd: aload 1
      // 1fe: invokevirtual android/content/Context.getPackageName ()Ljava/lang/String;
      // 201: invokevirtual android/content/res/Resources.getIdentifier (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
      // 204: istore 4
      // 206: goto 20d
      // 209: iload 5
      // 20b: istore 4
      // 20d: iload 4
      // 20f: istore 5
      // 211: iload 4
      // 213: ifne 225
      // 216: aload 9
      // 218: ldc "direction_turn_straight"
      // 21a: ldc "drawable"
      // 21c: aload 1
      // 21d: invokevirtual android/content/Context.getPackageName ()Ljava/lang/String;
      // 220: invokevirtual android/content/res/Resources.getIdentifier (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
      // 223: istore 5
      // 225: iload 5
      // 227: ifne 22f
      // 22a: aconst_null
      // 22b: astore 1
      // 22c: goto 237
      // 22f: aload 9
      // 231: iload 5
      // 233: invokestatic android/graphics/BitmapFactory.decodeResource (Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;
      // 236: astore 1
      // 237: aload 8
      // 239: aload 3
      // 23a: aload 1
      // 23b: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 240: pop
      // 241: goto 247
      // 244: aload 6
      // 246: astore 1
      // 247: aload 1
      // 248: checkcast android/graphics/Bitmap
      // 24b: astore 1
      // 24c: aload 7
      // 24e: monitorexit
      // 24f: aload 1
      // 250: areturn
      // 251: astore 1
      // 252: aload 7
      // 254: monitorexit
      // 255: aload 1
      // 256: athrow
   }
}
