package com.starpilot.starview;

import android.util.Log;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class Discovery {
   public static final Discovery INSTANCE = new Discovery();
   public static final int PORT = 8091;
   private static final String TAG = "StarView/Discovery";

   private Discovery() {
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final List<InetAddress> broadcastAddresses() {
      List var1 = new ArrayList();

      label54: {
         Iterator var10;
         try {
            Enumeration var2 = NetworkInterface.getNetworkInterfaces();
            Intrinsics.checkNotNullExpressionValue(var2, "getNetworkInterfaces(...)");
            var10 = CollectionsKt.iterator(var2);
         } catch (Exception var7) {
            Log.w("StarView/Discovery", "enumerating interfaces: " + var7);
            break label54;
         }

         label53:
         while (true) {
            NetworkInterface var3;
            try {
               do {
                  if (!var10.hasNext()) {
                     break label53;
                  }

                  var3 = (NetworkInterface)var10.next();
               } while (!var3.isUp() || var3.isLoopback());
            } catch (Exception var9) {
               Log.w("StarView/Discovery", "enumerating interfaces: " + var9);
               break;
            }

            try {
               var12 = var3.getInterfaceAddresses().iterator();
            } catch (Exception var6) {
               Log.w("StarView/Discovery", "enumerating interfaces: " + var6);
               break;
            }

            while (true) {
               InetAddress var4;
               try {
                  if (!var12.hasNext()) {
                     break;
                  }

                  var4 = ((InterfaceAddress)var12.next()).getBroadcast();
               } catch (Exception var8) {
                  Log.w("StarView/Discovery", "enumerating interfaces: " + var8);
                  break label53;
               }

               if (var4 != null) {
                  try {
                     var1.add(var4);
                  } catch (Exception var5) {
                     Log.w("StarView/Discovery", "enumerating interfaces: " + var5);
                     break label53;
                  }
               }
            }
         }
      }

      InetAddress var11 = InetAddress.getByName("255.255.255.255");
      Intrinsics.checkNotNullExpressionValue(var11, "getByName(...)");
      var1.add(var11);
      return CollectionsKt.distinct(var1);
   }

   // $VF: synthetic method
   public static Discovery.Found find$default(Discovery var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 1500;
      }

      return var0.find(var1);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final List<Triple<Integer, Integer, String>> subnets() {
      List var3 = new ArrayList();

      Iterator var6;
      try {
         Enumeration var4 = NetworkInterface.getNetworkInterfaces();
         Intrinsics.checkNotNullExpressionValue(var4, "getNetworkInterfaces(...)");
         var6 = CollectionsKt.iterator(var4);
      } catch (Exception var12) {
         return var3;
      }

      label65:
      while (true) {
         NetworkInterface var15;
         try {
            do {
               if (!var6.hasNext()) {
                  return var3;
               }

               var15 = (NetworkInterface)var6.next();
            } while (!var15.isUp() || var15.isLoopback());
         } catch (Exception var13) {
            break;
         }

         Iterator var5;
         try {
            var5 = var15.getInterfaceAddresses().iterator();
         } catch (Exception var11) {
            break;
         }

         while (true) {
            int var1;
            int var2;
            label59: {
               label58: {
                  InterfaceAddress var8;
                  try {
                     while (true) {
                        if (!var5.hasNext()) {
                           continue label65;
                        }

                        var8 = (InterfaceAddress)var5.next();
                        byte[] var7 = var8.getAddress().getAddress();
                        if (var7.length == 4) {
                           var2 = ByteBuffer.wrap(var7).getInt();
                           if (var8.getNetworkPrefixLength() <= 0) {
                              break label58;
                           }
                           break;
                        }
                     }
                  } catch (Exception var14) {
                     return var3;
                  }

                  try {
                     var1 = -1 << 32 - var8.getNetworkPrefixLength();
                     break label59;
                  } catch (Exception var10) {
                     return var3;
                  }
               }

               var1 = 0;
            }

            try {
               Triple var16 = new Triple<>(var2 & var1, var1, var15.getName());
               var3.add(var16);
            } catch (Exception var9) {
               return var3;
            }
         }
      }

      return var3;
   }

   public final Discovery.Found find(int param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: ldc "<get-values>(...)"
      // 002: astore 17
      // 004: new java/net/DatagramSocket
      // 007: dup
      // 008: invokespecial java/net/DatagramSocket.<init> ()V
      // 00b: astore 22
      // 00d: new java/util/LinkedHashMap
      // 010: dup
      // 011: invokespecial java/util/LinkedHashMap.<init> ()V
      // 014: astore 19
      // 016: aload 19
      // 018: astore 21
      // 01a: aload 22
      // 01c: bipush 1
      // 01d: invokevirtual java/net/DatagramSocket.setBroadcast (Z)V
      // 020: aload 19
      // 022: astore 21
      // 024: aload 22
      // 026: sipush 250
      // 029: invokevirtual java/net/DatagramSocket.setSoTimeout (I)V
      // 02c: aload 19
      // 02e: astore 21
      // 030: getstatic com/starpilot/starview/Link.INSTANCE Lcom/starpilot/starview/Link;
      // 033: invokevirtual com/starpilot/starview/Link.nonce ()Ljava/lang/String;
      // 036: astore 23
      // 038: aload 19
      // 03a: astore 21
      // 03c: new java/lang/StringBuilder
      // 03f: astore 16
      // 041: aload 19
      // 043: astore 21
      // 045: aload 16
      // 047: invokespecial java/lang/StringBuilder.<init> ()V
      // 04a: aload 19
      // 04c: astore 21
      // 04e: aload 16
      // 050: ldc "STARVIEW? "
      // 052: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 055: aload 23
      // 057: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 05a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 05d: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 060: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 063: astore 18
      // 065: aload 19
      // 067: astore 21
      // 069: aload 18
      // 06b: ldc "getBytes(...)"
      // 06d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 070: aload 19
      // 072: astore 21
      // 074: aload 0
      // 075: invokespecial com/starpilot/starview/Discovery.broadcastAddresses ()Ljava/util/List;
      // 078: astore 24
      // 07a: aload 19
      // 07c: astore 21
      // 07e: new java/lang/StringBuilder
      // 081: astore 16
      // 083: aload 19
      // 085: astore 21
      // 087: aload 16
      // 089: invokespecial java/lang/StringBuilder.<init> ()V
      // 08c: aload 19
      // 08e: astore 21
      // 090: ldc "StarView/Discovery"
      // 092: aload 16
      // 094: ldc "broadcasting to "
      // 096: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 099: aload 24
      // 09b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 09e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0a1: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;)I
      // 0a4: pop
      // 0a5: aload 19
      // 0a7: astore 21
      // 0a9: invokestatic java/lang/System.currentTimeMillis ()J
      // 0ac: iload 1
      // 0ad: i2l
      // 0ae: ladd
      // 0af: lstore 8
      // 0b1: lconst_0
      // 0b2: lstore 4
      // 0b4: aload 19
      // 0b6: astore 21
      // 0b8: sipush 2048
      // 0bb: newarray 8
      // 0bd: astore 20
      // 0bf: lconst_0
      // 0c0: lstore 2
      // 0c1: aload 19
      // 0c3: astore 21
      // 0c5: invokestatic java/lang/System.currentTimeMillis ()J
      // 0c8: lstore 6
      // 0ca: lload 6
      // 0cc: lload 8
      // 0ce: lcmp
      // 0cf: ifge 3ae
      // 0d2: invokestatic java/lang/System.currentTimeMillis ()J
      // 0d5: lload 4
      // 0d7: lsub
      // 0d8: ldc2_w 500
      // 0db: lcmp
      // 0dc: ifle 15b
      // 0df: aload 24
      // 0e1: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 0e6: astore 25
      // 0e8: aload 25
      // 0ea: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0ef: ifeq 153
      // 0f2: aload 25
      // 0f4: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0f9: checkcast java/net/InetAddress
      // 0fc: astore 21
      // 0fe: new java/net/DatagramPacket
      // 101: astore 16
      // 103: aload 18
      // 105: arraylength
      // 106: istore 1
      // 107: aload 16
      // 109: aload 18
      // 10b: iload 1
      // 10c: aload 21
      // 10e: sipush 8091
      // 111: invokespecial java/net/DatagramPacket.<init> ([BILjava/net/InetAddress;I)V
      // 114: aload 22
      // 116: aload 16
      // 118: invokevirtual java/net/DatagramSocket.send (Ljava/net/DatagramPacket;)V
      // 11b: goto 0e8
      // 11e: astore 16
      // 120: goto 125
      // 123: astore 16
      // 125: new java/lang/StringBuilder
      // 128: astore 26
      // 12a: aload 26
      // 12c: invokespecial java/lang/StringBuilder.<init> ()V
      // 12f: ldc "StarView/Discovery"
      // 131: aload 26
      // 133: ldc_w "send "
      // 136: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 139: aload 21
      // 13b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 13e: ldc_w ": "
      // 141: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 144: aload 16
      // 146: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 149: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 14c: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 14f: pop
      // 150: goto 0e8
      // 153: invokestatic java/lang/System.currentTimeMillis ()J
      // 156: lstore 4
      // 158: goto 15b
      // 15b: new java/net/DatagramPacket
      // 15e: astore 26
      // 160: aload 26
      // 162: aload 20
      // 164: aload 20
      // 166: arraylength
      // 167: invokespecial java/net/DatagramPacket.<init> ([BI)V
      // 16a: aload 22
      // 16c: aload 26
      // 16e: invokevirtual java/net/DatagramSocket.receive (Ljava/net/DatagramPacket;)V
      // 171: new org/json/JSONObject
      // 174: astore 16
      // 176: aload 26
      // 178: invokevirtual java/net/DatagramPacket.getData ()[B
      // 17b: astore 25
      // 17d: aload 25
      // 17f: ldc_w "getData(...)"
      // 182: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 185: aload 26
      // 187: invokevirtual java/net/DatagramPacket.getLength ()I
      // 18a: istore 1
      // 18b: new java/lang/String
      // 18e: astore 21
      // 190: aload 21
      // 192: aload 25
      // 194: bipush 0
      // 195: iload 1
      // 196: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 199: invokespecial java/lang/String.<init> ([BIILjava/nio/charset/Charset;)V
      // 19c: aload 16
      // 19e: aload 21
      // 1a0: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 1a3: aload 16
      // 1a5: ldc_w "type"
      // 1a8: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 1ab: ldc_w "starview"
      // 1ae: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 1b1: ifeq 32b
      // 1b4: aload 26
      // 1b6: invokevirtual java/net/DatagramPacket.getAddress ()Ljava/net/InetAddress;
      // 1b9: invokevirtual java/net/InetAddress.getHostAddress ()Ljava/lang/String;
      // 1bc: astore 25
      // 1be: aload 25
      // 1c0: ifnonnull 1c6
      // 1c3: goto 237
      // 1c6: aload 16
      // 1c8: ldc_w "dongleId"
      // 1cb: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 1ce: astore 21
      // 1d0: getstatic com/starpilot/starview/Link.INSTANCE Lcom/starpilot/starview/Link;
      // 1d3: astore 26
      // 1d5: aload 21
      // 1d7: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 1da: aload 16
      // 1dc: ldc_w "proof"
      // 1df: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 1e2: astore 27
      // 1e4: aload 27
      // 1e6: ldc_w "optString(...)"
      // 1e9: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1ec: aload 26
      // 1ee: aload 21
      // 1f0: aload 23
      // 1f2: ldc_w ""
      // 1f5: aload 27
      // 1f7: invokevirtual com/starpilot/starview/Link.commaProofOk (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
      // 1fa: istore 14
      // 1fc: aload 19
      // 1fe: aload 25
      // 200: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 203: checkcast com/starpilot/starview/Discovery$Found
      // 206: astore 26
      // 208: aload 26
      // 20a: ifnull 229
      // 20d: aload 26
      // 20f: invokevirtual com/starpilot/starview/Discovery$Found.getVerified ()Z
      // 212: istore 15
      // 214: iload 15
      // 216: bipush 1
      // 217: if_icmpne 229
      // 21a: bipush 1
      // 21b: istore 1
      // 21c: goto 22b
      // 21f: astore 16
      // 221: goto 330
      // 224: astore 16
      // 226: goto 335
      // 229: bipush 0
      // 22a: istore 1
      // 22b: iload 1
      // 22c: ifeq 24a
      // 22f: iload 14
      // 231: ifeq 237
      // 234: goto 24a
      // 237: aload 17
      // 239: astore 16
      // 23b: lload 4
      // 23d: lstore 6
      // 23f: aload 18
      // 241: astore 16
      // 243: lload 8
      // 245: lstore 6
      // 247: goto 3a6
      // 24a: aload 19
      // 24c: checkcast java/util/Map
      // 24f: astore 28
      // 251: new com/starpilot/starview/Discovery$Found
      // 254: astore 27
      // 256: lload 2
      // 257: lstore 10
      // 259: lload 2
      // 25a: lstore 12
      // 25c: aload 16
      // 25e: ldc_w "port"
      // 261: sipush 8090
      // 264: invokevirtual org/json/JSONObject.optInt (Ljava/lang/String;I)I
      // 267: istore 1
      // 268: lload 2
      // 269: lstore 10
      // 26b: lload 2
      // 26c: lstore 12
      // 26e: aload 16
      // 270: ldc_w "branch"
      // 273: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 276: astore 26
      // 278: lload 2
      // 279: lstore 10
      // 27b: lload 2
      // 27c: lstore 12
      // 27e: aload 26
      // 280: ldc_w "optString(...)"
      // 283: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 286: lload 2
      // 287: lstore 10
      // 289: lload 2
      // 28a: lstore 12
      // 28c: aload 16
      // 28e: ldc_w "commit"
      // 291: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 294: astore 16
      // 296: lload 2
      // 297: lstore 10
      // 299: lload 2
      // 29a: lstore 12
      // 29c: aload 16
      // 29e: ldc_w "optString(...)"
      // 2a1: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 2a4: lload 2
      // 2a5: lstore 10
      // 2a7: lload 2
      // 2a8: lstore 12
      // 2aa: aload 27
      // 2ac: aload 25
      // 2ae: iload 1
      // 2af: aload 21
      // 2b1: aload 26
      // 2b3: aload 16
      // 2b5: iload 14
      // 2b7: invokespecial com/starpilot/starview/Discovery$Found.<init> (Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V
      // 2ba: lload 2
      // 2bb: lstore 10
      // 2bd: lload 2
      // 2be: lstore 12
      // 2c0: aload 28
      // 2c2: aload 25
      // 2c4: aload 27
      // 2c6: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 2cb: pop
      // 2cc: lload 2
      // 2cd: lstore 6
      // 2cf: lload 2
      // 2d0: lconst_0
      // 2d1: lcmp
      // 2d2: ifne 2e0
      // 2d5: lload 2
      // 2d6: lstore 10
      // 2d8: lload 2
      // 2d9: lstore 12
      // 2db: invokestatic java/lang/System.currentTimeMillis ()J
      // 2de: lstore 6
      // 2e0: lload 6
      // 2e2: lstore 10
      // 2e4: lload 6
      // 2e6: lstore 12
      // 2e8: lload 6
      // 2ea: lstore 2
      // 2eb: aload 0
      // 2ec: aload 0
      // 2ed: aload 25
      // 2ef: invokevirtual com/starpilot/starview/Discovery.interfaceFor (Ljava/lang/String;)Ljava/lang/String;
      // 2f2: invokevirtual com/starpilot/starview/Discovery.isUsb (Ljava/lang/String;)Z
      // 2f5: ifeq 37d
      // 2f8: iload 14
      // 2fa: ifne 315
      // 2fd: lload 6
      // 2ff: lstore 10
      // 301: lload 6
      // 303: lstore 12
      // 305: getstatic com/starpilot/starview/Link.INSTANCE Lcom/starpilot/starview/Link;
      // 308: invokevirtual com/starpilot/starview/Link.getPaired ()Z
      // 30b: istore 14
      // 30d: lload 6
      // 30f: lstore 2
      // 310: iload 14
      // 312: ifne 37d
      // 315: lload 6
      // 317: lstore 2
      // 318: goto 3ae
      // 31b: astore 16
      // 31d: lload 10
      // 31f: lstore 2
      // 320: goto 358
      // 323: astore 16
      // 325: lload 12
      // 327: lstore 2
      // 328: goto 37d
      // 32b: goto 37d
      // 32e: astore 16
      // 330: goto 358
      // 333: astore 16
      // 335: goto 37d
      // 338: astore 16
      // 33a: goto 358
      // 33d: astore 16
      // 33f: goto 37d
      // 342: astore 16
      // 344: goto 34e
      // 347: astore 16
      // 349: goto 353
      // 34c: astore 16
      // 34e: goto 358
      // 351: astore 16
      // 353: goto 37d
      // 356: astore 16
      // 358: new java/lang/StringBuilder
      // 35b: astore 21
      // 35d: aload 21
      // 35f: invokespecial java/lang/StringBuilder.<init> ()V
      // 362: ldc "StarView/Discovery"
      // 364: aload 21
      // 366: ldc_w "recv: "
      // 369: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 36c: aload 16
      // 36e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 371: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 374: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 377: pop
      // 378: goto 37d
      // 37b: astore 16
      // 37d: lload 2
      // 37e: lconst_0
      // 37f: lcmp
      // 380: ifeq 396
      // 383: invokestatic java/lang/System.currentTimeMillis ()J
      // 386: lstore 6
      // 388: lload 6
      // 38a: lload 2
      // 38b: lsub
      // 38c: ldc2_w 600
      // 38f: lcmp
      // 390: ifle 396
      // 393: goto 3ae
      // 396: aload 17
      // 398: astore 16
      // 39a: lload 4
      // 39c: lstore 6
      // 39e: aload 18
      // 3a0: astore 16
      // 3a2: lload 8
      // 3a4: lstore 6
      // 3a6: goto 0c1
      // 3a9: astore 16
      // 3ab: goto 64b
      // 3ae: aload 19
      // 3b0: astore 21
      // 3b2: aload 19
      // 3b4: invokevirtual java/util/LinkedHashMap.isEmpty ()Z
      // 3b7: istore 14
      // 3b9: iload 14
      // 3bb: ifeq 3c5
      // 3be: aload 22
      // 3c0: invokevirtual java/net/DatagramSocket.close ()V
      // 3c3: aconst_null
      // 3c4: areturn
      // 3c5: aload 19
      // 3c7: astore 21
      // 3c9: aload 19
      // 3cb: invokevirtual java/util/LinkedHashMap.values ()Ljava/util/Collection;
      // 3ce: astore 16
      // 3d0: aload 19
      // 3d2: astore 21
      // 3d4: aload 16
      // 3d6: aload 17
      // 3d8: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 3db: aload 19
      // 3dd: astore 21
      // 3df: aload 16
      // 3e1: checkcast java/lang/Iterable
      // 3e4: astore 18
      // 3e6: aload 19
      // 3e8: astore 21
      // 3ea: new java/util/ArrayList
      // 3ed: astore 16
      // 3ef: aload 19
      // 3f1: astore 21
      // 3f3: aload 16
      // 3f5: invokespecial java/util/ArrayList.<init> ()V
      // 3f8: aload 19
      // 3fa: astore 21
      // 3fc: aload 16
      // 3fe: checkcast java/util/Collection
      // 401: astore 16
      // 403: aload 19
      // 405: astore 21
      // 407: aload 18
      // 409: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 40e: astore 18
      // 410: aload 19
      // 412: astore 21
      // 414: aload 18
      // 416: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 41b: istore 14
      // 41d: iload 14
      // 41f: ifeq 443
      // 422: aload 18
      // 424: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 429: astore 20
      // 42b: aload 20
      // 42d: checkcast com/starpilot/starview/Discovery$Found
      // 430: invokevirtual com/starpilot/starview/Discovery$Found.getVerified ()Z
      // 433: ifeq 440
      // 436: aload 16
      // 438: aload 20
      // 43a: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 43f: pop
      // 440: goto 410
      // 443: aload 19
      // 445: astore 21
      // 447: aload 16
      // 449: checkcast java/util/List
      // 44c: astore 16
      // 44e: aload 19
      // 450: astore 21
      // 452: aload 16
      // 454: checkcast java/util/Collection
      // 457: astore 16
      // 459: aload 19
      // 45b: astore 21
      // 45d: aload 16
      // 45f: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 464: istore 14
      // 466: iload 14
      // 468: ifeq 483
      // 46b: aload 19
      // 46d: invokevirtual java/util/LinkedHashMap.values ()Ljava/util/Collection;
      // 470: astore 16
      // 472: aload 16
      // 474: aload 17
      // 476: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 479: aload 16
      // 47b: checkcast java/lang/Iterable
      // 47e: invokestatic kotlin/collections/CollectionsKt.toList (Ljava/lang/Iterable;)Ljava/util/List;
      // 481: astore 16
      // 483: aload 19
      // 485: astore 21
      // 487: aload 16
      // 489: checkcast java/util/List
      // 48c: astore 20
      // 48e: aload 19
      // 490: astore 21
      // 492: aload 20
      // 494: checkcast java/lang/Iterable
      // 497: astore 16
      // 499: bipush 0
      // 49a: istore 14
      // 49c: aload 19
      // 49e: astore 21
      // 4a0: aload 16
      // 4a2: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 4a7: astore 23
      // 4a9: aload 19
      // 4ab: astore 21
      // 4ad: aload 23
      // 4af: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 4b4: istore 15
      // 4b6: iload 15
      // 4b8: ifeq 4ed
      // 4bb: aload 23
      // 4bd: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 4c2: astore 18
      // 4c4: aload 18
      // 4c6: checkcast com/starpilot/starview/Discovery$Found
      // 4c9: astore 21
      // 4cb: getstatic com/starpilot/starview/Discovery.INSTANCE Lcom/starpilot/starview/Discovery;
      // 4ce: getstatic com/starpilot/starview/Discovery.INSTANCE Lcom/starpilot/starview/Discovery;
      // 4d1: aload 21
      // 4d3: invokevirtual com/starpilot/starview/Discovery$Found.getHost ()Ljava/lang/String;
      // 4d6: invokevirtual com/starpilot/starview/Discovery.interfaceFor (Ljava/lang/String;)Ljava/lang/String;
      // 4d9: invokevirtual com/starpilot/starview/Discovery.isUsb (Ljava/lang/String;)Z
      // 4dc: istore 15
      // 4de: iload 15
      // 4e0: ifeq 4ea
      // 4e3: aload 18
      // 4e5: astore 16
      // 4e7: goto 4f0
      // 4ea: goto 4a9
      // 4ed: aconst_null
      // 4ee: astore 16
      // 4f0: aload 19
      // 4f2: astore 21
      // 4f4: aload 16
      // 4f6: checkcast com/starpilot/starview/Discovery$Found
      // 4f9: astore 16
      // 4fb: aload 16
      // 4fd: astore 18
      // 4ff: aload 16
      // 501: ifnonnull 51a
      // 504: aload 20
      // 506: invokestatic kotlin/collections/CollectionsKt.first (Ljava/util/List;)Ljava/lang/Object;
      // 509: astore 16
      // 50b: aload 16
      // 50d: ldc_w "first(...)"
      // 510: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 513: aload 16
      // 515: checkcast com/starpilot/starview/Discovery$Found
      // 518: astore 18
      // 51a: aload 19
      // 51c: astore 21
      // 51e: aload 19
      // 520: invokevirtual java/util/LinkedHashMap.values ()Ljava/util/Collection;
      // 523: astore 16
      // 525: aload 19
      // 527: astore 21
      // 529: aload 16
      // 52b: aload 17
      // 52d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 530: aload 19
      // 532: astore 21
      // 534: aload 16
      // 536: checkcast java/lang/Iterable
      // 539: astore 20
      // 53b: bipush 0
      // 53c: istore 14
      // 53e: aload 19
      // 540: astore 21
      // 542: new java/util/ArrayList
      // 545: astore 16
      // 547: aload 19
      // 549: astore 21
      // 54b: aload 16
      // 54d: aload 20
      // 54f: bipush 10
      // 551: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 554: invokespecial java/util/ArrayList.<init> (I)V
      // 557: aload 19
      // 559: astore 21
      // 55b: aload 16
      // 55d: checkcast java/util/Collection
      // 560: astore 23
      // 562: aload 20
      // 564: astore 17
      // 566: aload 19
      // 568: astore 21
      // 56a: aload 17
      // 56c: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 571: astore 24
      // 573: aload 19
      // 575: astore 16
      // 577: aload 20
      // 579: astore 19
      // 57b: aload 16
      // 57d: astore 21
      // 57f: aload 24
      // 581: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 586: ifeq 603
      // 589: aload 16
      // 58b: astore 21
      // 58d: aload 24
      // 58f: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 594: checkcast com/starpilot/starview/Discovery$Found
      // 597: astore 20
      // 599: aload 16
      // 59b: astore 21
      // 59d: aload 20
      // 59f: invokevirtual com/starpilot/starview/Discovery$Found.getHost ()Ljava/lang/String;
      // 5a2: astore 25
      // 5a4: aload 16
      // 5a6: astore 21
      // 5a8: getstatic com/starpilot/starview/Discovery.INSTANCE Lcom/starpilot/starview/Discovery;
      // 5ab: astore 21
      // 5ad: aload 21
      // 5af: aload 20
      // 5b1: invokevirtual com/starpilot/starview/Discovery$Found.getHost ()Ljava/lang/String;
      // 5b4: invokevirtual com/starpilot/starview/Discovery.interfaceFor (Ljava/lang/String;)Ljava/lang/String;
      // 5b7: astore 21
      // 5b9: aload 20
      // 5bb: invokevirtual com/starpilot/starview/Discovery$Found.getVerified ()Z
      // 5be: ifeq 5c9
      // 5c1: ldc_w ", verified"
      // 5c4: astore 20
      // 5c6: goto 5ce
      // 5c9: ldc_w ""
      // 5cc: astore 20
      // 5ce: new java/lang/StringBuilder
      // 5d1: astore 26
      // 5d3: aload 26
      // 5d5: invokespecial java/lang/StringBuilder.<init> ()V
      // 5d8: aload 23
      // 5da: aload 26
      // 5dc: aload 25
      // 5de: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5e1: ldc_w "("
      // 5e4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5e7: aload 21
      // 5e9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5ec: aload 20
      // 5ee: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5f1: ldc_w ")"
      // 5f4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5f7: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 5fa: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 5ff: pop
      // 600: goto 57b
      // 603: aload 23
      // 605: checkcast java/util/List
      // 608: astore 17
      // 60a: aload 18
      // 60c: invokevirtual com/starpilot/starview/Discovery$Found.getHost ()Ljava/lang/String;
      // 60f: astore 19
      // 611: new java/lang/StringBuilder
      // 614: astore 16
      // 616: aload 16
      // 618: invokespecial java/lang/StringBuilder.<init> ()V
      // 61b: ldc "StarView/Discovery"
      // 61d: aload 16
      // 61f: ldc_w "responders: "
      // 622: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 625: aload 17
      // 627: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 62a: ldc_w " -> "
      // 62d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 630: aload 19
      // 632: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 635: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 638: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;)I
      // 63b: pop
      // 63c: aload 22
      // 63e: invokevirtual java/net/DatagramSocket.close ()V
      // 641: aload 18
      // 643: areturn
      // 644: astore 16
      // 646: goto 64b
      // 649: astore 16
      // 64b: aload 22
      // 64d: invokevirtual java/net/DatagramSocket.close ()V
      // 650: aload 16
      // 652: athrow
   }

   public final String interfaceFor(String var1) {
      Intrinsics.checkNotNullParameter(var1, "host");

      int var2;
      try {
         var2 = ByteBuffer.wrap(InetAddress.getByName(var1).getAddress()).getInt();
      } catch (Exception var6) {
         return "";
      }

      for (Triple var5 : this.subnets()) {
         int var4 = ((Number)var5.component1()).intValue();
         int var3 = ((Number)var5.component2()).intValue();
         String var8 = (String)var5.component3();
         if ((var2 & var3) == var4) {
            return var8;
         }
      }

      return "";
   }

   public final boolean isUsb(String var1) {
      Intrinsics.checkNotNullParameter(var1, "ifname");
      boolean var2 = false;
      if (StringsKt.startsWith$default(var1, "rndis", false, 2, null)
         || StringsKt.startsWith$default(var1, "ncm", false, 2, null)
         || StringsKt.startsWith$default(var1, "usb", false, 2, null)) {
         var2 = true;
      }

      return var2;
   }

   public final boolean usbInterfaceUp() {
      var var3 = this.subnets();
      boolean var2 = var3 instanceof Collection;
      boolean var1 = false;
      if (!var2 || !((Collection)var3).isEmpty()) {
         for (Triple var5 : var3) {
            if (INSTANCE.isUsb((String)var5.getThird())) {
               var1 = true;
               break;
            }
         }
      }

      return var1;
   }

   public static final class Found {
      private final String branch;
      private final String commit;
      private final String dongleId;
      private final String host;
      private final int port;
      private final boolean verified;

      public Found(String var1, int var2, String var3, String var4, String var5, boolean var6) {
         Intrinsics.checkNotNullParameter(var1, "host");
         Intrinsics.checkNotNullParameter(var3, "dongleId");
         Intrinsics.checkNotNullParameter(var4, "branch");
         Intrinsics.checkNotNullParameter(var5, "commit");
         super();
         this.host = var1;
         this.port = var2;
         this.dongleId = var3;
         this.branch = var4;
         this.commit = var5;
         this.verified = var6;
      }

      // $VF: synthetic method
      public static Discovery.Found copy$default(
         Discovery.Found var0, String var1, int var2, String var3, String var4, String var5, boolean var6, int var7, Object var8
      ) {
         if ((var7 & 1) != 0) {
            var1 = var0.host;
         }

         if ((var7 & 2) != 0) {
            var2 = var0.port;
         }

         if ((var7 & 4) != 0) {
            var3 = var0.dongleId;
         }

         if ((var7 & 8) != 0) {
            var4 = var0.branch;
         }

         if ((var7 & 16) != 0) {
            var5 = var0.commit;
         }

         if ((var7 & 32) != 0) {
            var6 = var0.verified;
         }

         return var0.copy(var1, var2, var3, var4, var5, var6);
      }

      public final String component1() {
         return this.host;
      }

      public final int component2() {
         return this.port;
      }

      public final String component3() {
         return this.dongleId;
      }

      public final String component4() {
         return this.branch;
      }

      public final String component5() {
         return this.commit;
      }

      public final boolean component6() {
         return this.verified;
      }

      public final Discovery.Found copy(String var1, int var2, String var3, String var4, String var5, boolean var6) {
         Intrinsics.checkNotNullParameter(var1, "host");
         Intrinsics.checkNotNullParameter(var3, "dongleId");
         Intrinsics.checkNotNullParameter(var4, "branch");
         Intrinsics.checkNotNullParameter(var5, "commit");
         return new Discovery.Found(var1, var2, var3, var4, var5, var6);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof Discovery.Found)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.host, var1.host)) {
               return false;
            } else if (this.port != var1.port) {
               return false;
            } else if (!Intrinsics.areEqual(this.dongleId, var1.dongleId)) {
               return false;
            } else if (!Intrinsics.areEqual(this.branch, var1.branch)) {
               return false;
            } else {
               return !Intrinsics.areEqual(this.commit, var1.commit) ? false : this.verified == var1.verified;
            }
         }
      }

      public final String getBranch() {
         return this.branch;
      }

      public final String getCommit() {
         return this.commit;
      }

      public final String getDongleId() {
         return this.dongleId;
      }

      public final String getHost() {
         return this.host;
      }

      public final int getPort() {
         return this.port;
      }

      public final boolean getVerified() {
         return this.verified;
      }

      @Override
      public int hashCode() {
         return (
                  (((this.host.hashCode() * 31 + Integer.hashCode(this.port)) * 31 + this.dongleId.hashCode()) * 31 + this.branch.hashCode()) * 31
                     + this.commit.hashCode()
               )
               * 31
            + Boolean.hashCode(this.verified);
      }

      @Override
      public String toString() {
         String var4 = this.host;
         int var1 = this.port;
         String var6 = this.dongleId;
         String var5 = this.branch;
         String var3 = this.commit;
         boolean var2 = this.verified;
         return "Found(host=" + var4 + ", port=" + var1 + ", dongleId=" + var6 + ", branch=" + var5 + ", commit=" + var3 + ", verified=" + var2 + ")";
      }
   }
}
