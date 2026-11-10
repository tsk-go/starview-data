package com.starpilot.starview;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Process;
import android.os.SystemClock;
import android.os.Build.VERSION;
import android.util.Log;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.Result.Companion;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

public final class GpsBridge {
   private static final long DR_AFTER_MS = 1500L;
   private static final long DR_MAX_MS = 60000L;
   public static final GpsBridge INSTANCE = new GpsBridge();
   private static final double LOOKAHEAD_HEADING_S = 0.8;
   private static final double LOOKAHEAD_S = 0.4;
   private static final String TAG = "StarView/Gps";
   private static volatile Context app;
   private static volatile long doubtedAtMs;
   private static volatile double[] drXform;
   private static volatile long drXformAtMs;
   private static final GpsFilter filter = new GpsFilter();
   private static volatile long fixes;
   private static volatile float lastAcc = 5.0F;
   private static double lastAlt;
   private static volatile long lastDrPushMs;
   private static long lastFallbackMs;
   private static volatile long lastFixMs;
   private static long lastFusedPushMs;
   private static volatile long lastLiveMs;
   private static long lastPushMs;
   private static long lastSetupTryMs;
   private static double liveOdoDist = Double.NaN;
   private static volatile String mode = "none";
   private static volatile float outBearing = Float.NaN;
   private static volatile String outKind = "";
   private static volatile double outLat = Double.NaN;
   private static volatile double outLon = Double.NaN;
   private static volatile boolean ready;
   private static volatile String status = "waiting for the comma's GPS";
   private static volatile double turnSentDeg;
   private static float turnSentLast = Float.NaN;
   private static volatile boolean usingFallback;
   private static volatile boolean usingRtk;
   private static volatile float vEgoNow;

   private GpsBridge() {
   }

   private final LocationManager lm() {
      Context var1 = app;
      LocationManager var2 = null;
      Object var3;
      if (var1 != null) {
         var3 = var1.getSystemService("location");
      } else {
         var3 = null;
      }

      if (var3 instanceof LocationManager) {
         var2 = (LocationManager)var3;
      }

      return var2;
   }

   // $VF: synthetic method
   public static void onFix$default(
      GpsBridge var0,
      double var1,
      double var3,
      double var5,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      String var13,
      long var14,
      int var16,
      Object var17
   ) {
      if ((var16 & 512) != 0) {
         var13 = "";
      }

      if ((var16 & 1024) != 0) {
         var14 = 0L;
      }

      var0.onFix(var1, var3, var5, var7, var8, var9, var10, var11, var12, var13, var14);
   }

   private final SharedPreferences prefs(Context var1) {
      return var1.getSharedPreferences("starview", 0);
   }

   private final List<String> providers() {
      List var1 = CollectionsKt.createListBuilder();
      var1.add("gps");
      var1.add("network");
      if (VERSION.SDK_INT >= 31) {
         var1.add("fused");
      }

      return CollectionsKt.build(var1);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void push(
      double var1,
      double var3,
      double var5,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      boolean var13,
      boolean var14,
      boolean var15
   ) {
      double[] var22 = RtkPosition.INSTANCE.now(System.currentTimeMillis(), vEgoNow);
      if (var22 != null) {
         var1 = var22[0];
      }

      if (var22 != null) {
         var3 = var22[1];
      }

      if (var22 != null) {
         double var16 = RtkPosition.INSTANCE.getAccM();
         boolean var19;
         if (!Double.isInfinite(var16) && !Double.isNaN(var16)) {
            var19 = true;
         } else {
            var19 = false;
         }

         if (var19) {
            var9 = RangesKt.coerceAtLeast((float)RtkPosition.INSTANCE.getAccM(), 0.01F);
         } else {
            var9 = 1.0F;
         }
      }

      if (var22 != null) {
         double var69 = RtkPosition.INSTANCE.getHeading();
         boolean var71;
         if (!Double.isInfinite(var69) && !Double.isNaN(var69)) {
            var71 = true;
         } else {
            var71 = false;
         }

         if (var71) {
            var8 = (float)RtkPosition.INSTANCE.getHeading();
         }
      }

      boolean var20;
      if (var22 != null) {
         var20 = true;
      } else {
         var20 = false;
      }

      usingRtk = var20;
      outLat = var1;
      outLon = var3;
      String var21;
      if (var15) {
         var21 = "held";
      } else if (var14) {
         var21 = "dr";
      } else if (var13) {
         var21 = "live";
      } else {
         var21 = "last";
      }

      label386: {
         outKind = var21;
         outBearing = var8;
         if ((var13 || var14) && !var15 && vEgoNow > 1.5F) {
            boolean var72;
            if (!Float.isInfinite(var8) && !Float.isNaN(var8)) {
               var72 = true;
            } else {
               var72 = false;
            }

            if (var72) {
               float var18 = turnSentLast;
               if (!Float.isNaN(var18)) {
                  turnSentDeg -= (var8 - var18 + 540.0F) % 360.0F - 180.0F;
               }

               turnSentLast = var8;
               break label386;
            }
         }

         turnSentLast = Float.NaN;
      }

      Context var77 = app;
      if (var77 != null) {
         if (this.enabled(var77)) {
            LocationManager var24 = this.lm();
            if (var24 != null) {
               if (this.setup(var24)) {
                  label394: {
                     label378: {
                        Iterator var23;
                        try {
                           var23 = this.providers().iterator();
                        } catch (SecurityException var67) {
                           break label378;
                        } catch (Exception var68) {
                           var78 = var68;
                           break label394;
                        }

                        Iterator var81 = var23;

                        while (true) {
                           try {
                              if (!var81.hasNext()) {
                                 break;
                              }
                           } catch (SecurityException var55) {
                              break label378;
                           } catch (Exception var56) {
                              var78 = var56;
                              break label394;
                           }

                           String var26;
                           try {
                              var26 = (String)var81.next();
                           } catch (SecurityException var53) {
                              break label378;
                           } catch (Exception var54) {
                              var78 = var54;
                              break label394;
                           }

                           Location var25;
                           try {
                           } catch (SecurityException var51) {
                              break label378;
                           } catch (Exception var52) {
                              var78 = var52;
                              break label394;
                           }

                           try {
                              var25 = new Location(var26);
                           } catch (SecurityException var49) {
                              break label378;
                           } catch (Exception var50) {
                              var78 = var50;
                              break label394;
                           }

                           try {
                              var25.setLatitude(var1);
                           } catch (SecurityException var47) {
                              break label378;
                           } catch (Exception var48) {
                              var78 = var48;
                              break label394;
                           }

                           try {
                              var25.setLongitude(var3);
                           } catch (SecurityException var45) {
                              break label378;
                           } catch (Exception var46) {
                              var78 = var46;
                              break label394;
                           }

                           try {
                              var25.setAltitude(var5);
                           } catch (SecurityException var43) {
                              break label378;
                           } catch (Exception var44) {
                              var78 = var44;
                              break label394;
                           }

                           try {
                              var25.setSpeed(RangesKt.coerceAtLeast(var7, 0.0F));
                              if (!Float.isNaN(var8)) {
                                 var25.setBearing((var8 % 360.0F + 360.0F) % 360.0F);
                              }
                           } catch (SecurityException var65) {
                              break label378;
                           } catch (Exception var66) {
                              var78 = var66;
                              break label394;
                           }

                           boolean var73;
                           label317: {
                              label316: {
                                 try {
                                    if (!Float.isInfinite(var9) && !Float.isNaN(var9)) {
                                       break label316;
                                    }
                                 } catch (SecurityException var63) {
                                    break label378;
                                 } catch (Exception var64) {
                                    var78 = var64;
                                    break label394;
                                 }

                                 var73 = false;
                                 break label317;
                              }

                              var73 = true;
                           }

                           float var70;
                           if (var73 && var9 > 0.0F) {
                              var70 = var9;
                           } else {
                              var70 = 5.0F;
                           }

                           try {
                              var25.setAccuracy(var70);
                           } catch (SecurityException var41) {
                              break label378;
                           } catch (Exception var42) {
                              var78 = var42;
                              break label394;
                           }

                           label301: {
                              label300: {
                                 try {
                                    if (!Float.isInfinite(var10) && !Float.isNaN(var10)) {
                                       break label300;
                                    }
                                 } catch (SecurityException var61) {
                                    break label378;
                                 } catch (Exception var62) {
                                    var78 = var62;
                                    break label394;
                                 }

                                 var73 = false;
                                 break label301;
                              }

                              var73 = true;
                           }

                           if (var73 && var10 > 0.0F) {
                              try {
                                 var25.setVerticalAccuracyMeters(var10);
                              } catch (SecurityException var39) {
                                 break label378;
                              } catch (Exception var40) {
                                 var78 = var40;
                                 break label394;
                              }
                           }

                           label290: {
                              label289: {
                                 try {
                                    if (!Float.isInfinite(var11) && !Float.isNaN(var11)) {
                                       break label289;
                                    }
                                 } catch (SecurityException var59) {
                                    break label378;
                                 } catch (Exception var60) {
                                    var78 = var60;
                                    break label394;
                                 }

                                 var73 = false;
                                 break label290;
                              }

                              var73 = true;
                           }

                           if (var73 && var11 > 0.0F) {
                              try {
                                 var25.setBearingAccuracyDegrees(var11);
                              } catch (SecurityException var37) {
                                 break label378;
                              } catch (Exception var38) {
                                 var78 = var38;
                                 break label394;
                              }
                           }

                           label279: {
                              label278: {
                                 try {
                                    if (!Float.isInfinite(var12) && !Float.isNaN(var12)) {
                                       break label278;
                                    }
                                 } catch (SecurityException var57) {
                                    break label378;
                                 } catch (Exception var58) {
                                    var78 = var58;
                                    break label394;
                                 }

                                 var73 = false;
                                 break label279;
                              }

                              var73 = true;
                           }

                           if (var73 && var12 > 0.0F) {
                              try {
                                 var25.setSpeedAccuracyMetersPerSecond(var12);
                              } catch (SecurityException var35) {
                                 break label378;
                              } catch (Exception var36) {
                                 var78 = var36;
                                 break label394;
                              }
                           }

                           try {
                              var25.setTime(System.currentTimeMillis());
                              var25.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                           } catch (SecurityException var33) {
                              break label378;
                           } catch (Exception var34) {
                              var78 = var34;
                              break label394;
                           }

                           try {
                              var24.setTestProviderLocation(var26, var25);
                           } catch (SecurityException var31) {
                              break label378;
                           } catch (Exception var32) {
                              var78 = var32;
                              break label394;
                           }
                        }

                        label232: {
                           try {
                              lastFixMs = System.currentTimeMillis();
                              fixes++;
                              if (usingRtk) {
                                 var21 = RtkPosition.INSTANCE.getKind();
                                 StringBuilder var82 = new StringBuilder();
                                 var21 = var82.append("sending the RTK receiver's position (").append(var21).append(") to this tablet").toString();
                                 break label232;
                              }
                           } catch (SecurityException var29) {
                              break label378;
                           } catch (Exception var30) {
                              var78 = var30;
                              break label394;
                           }

                           if (var15) {
                              var21 = "car stopped: holding the position still";
                           } else if (var13) {
                              var21 = "sending the comma's live GPS to this tablet";
                           } else if (var14) {
                              var21 = "GPS gap: sending wheel speed + gyro position (dead reckoning)";
                           } else {
                              var21 = "car off / no live fix: sending the comma's last known position";
                           }
                        }

                        try {
                           status = var21;
                           return;
                        } catch (SecurityException var27) {
                        } catch (Exception var28) {
                           var78 = var28;
                           break label394;
                        }
                     }

                     ready = false;
                     status = "not allowed yet: make StarView the mock location app (see below)";
                     return;
                  }

                  ready = false;
                  Log.w("StarView/Gps", "push: " + var78);
               }
            }
         }
      }
   }

   // $VF: synthetic method
   static void push$default(
      GpsBridge var0,
      double var1,
      double var3,
      double var5,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      boolean var13,
      boolean var14,
      boolean var15,
      int var16,
      Object var17
   ) {
      if ((var16 & 1024) != 0) {
         var14 = false;
      }

      if ((var16 & 2048) != 0) {
         var15 = false;
      }

      var0.push(var1, var3, var5, var7, var8, var9, var10, var11, var12, var13, var14, var15);
   }

   private final boolean setup(LocationManager param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: getstatic com/starpilot/starview/GpsBridge.ready Z
      // 05: istore 6
      // 07: iload 6
      // 09: ifeq 10
      // 0c: aload 0
      // 0d: monitorexit
      // 0e: bipush 1
      // 0f: ireturn
      // 10: invokestatic android/os/SystemClock.elapsedRealtime ()J
      // 13: lstore 4
      // 15: getstatic com/starpilot/starview/GpsBridge.lastSetupTryMs J
      // 18: lstore 2
      // 19: lload 4
      // 1b: lload 2
      // 1c: lsub
      // 1d: ldc2_w 5000
      // 20: lcmp
      // 21: ifge 28
      // 24: aload 0
      // 25: monitorexit
      // 26: bipush 0
      // 27: ireturn
      // 28: lload 4
      // 2a: putstatic com/starpilot/starview/GpsBridge.lastSetupTryMs J
      // 2d: aload 0
      // 2e: invokespecial com/starpilot/starview/GpsBridge.providers ()Ljava/util/List;
      // 31: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 36: astore 8
      // 38: aload 8
      // 3a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 3f: ifeq ac
      // 42: aload 8
      // 44: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 49: checkcast java/lang/String
      // 4c: astore 7
      // 4e: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 51: astore 9
      // 53: aload 0
      // 54: checkcast com/starpilot/starview/GpsBridge
      // 57: astore 9
      // 59: aload 1
      // 5a: aload 7
      // 5c: invokevirtual android/location/LocationManager.removeTestProvider (Ljava/lang/String;)V
      // 5f: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 62: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 65: pop
      // 66: goto 79
      // 69: astore 9
      // 6b: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 6e: astore 10
      // 70: aload 9
      // 72: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 75: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 78: pop
      // 79: getstatic android/os/Build$VERSION.SDK_INT I
      // 7c: bipush 31
      // 7e: if_icmplt 93
      // 81: aload 1
      // 82: aload 7
      // 84: bipush 0
      // 85: bipush 0
      // 86: bipush 0
      // 87: bipush 0
      // 88: bipush 1
      // 89: bipush 1
      // 8a: bipush 1
      // 8b: bipush 1
      // 8c: bipush 1
      // 8d: invokevirtual android/location/LocationManager.addTestProvider (Ljava/lang/String;ZZZZZZZII)V
      // 90: goto a2
      // 93: aload 1
      // 94: aload 7
      // 96: bipush 0
      // 97: bipush 0
      // 98: bipush 0
      // 99: bipush 0
      // 9a: bipush 1
      // 9b: bipush 1
      // 9c: bipush 1
      // 9d: bipush 1
      // 9e: bipush 1
      // 9f: invokevirtual android/location/LocationManager.addTestProvider (Ljava/lang/String;ZZZZZZZII)V
      // a2: aload 1
      // a3: aload 7
      // a5: bipush 1
      // a6: invokevirtual android/location/LocationManager.setTestProviderEnabled (Ljava/lang/String;Z)V
      // a9: goto 38
      // ac: bipush 1
      // ad: putstatic com/starpilot/starview/GpsBridge.ready Z
      // b0: ldc_w "sending the comma's GPS to this tablet"
      // b3: putstatic com/starpilot/starview/GpsBridge.status Ljava/lang/String;
      // b6: aload 0
      // b7: monitorexit
      // b8: bipush 1
      // b9: ireturn
      // ba: astore 8
      // bc: aload 8
      // be: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // c1: astore 7
      // c3: new java/lang/StringBuilder
      // c6: astore 1
      // c7: aload 1
      // c8: invokespecial java/lang/StringBuilder.<init> ()V
      // cb: aload 1
      // cc: ldc_w "can't add the GPS provider: "
      // cf: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // d2: aload 7
      // d4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // d7: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // da: putstatic com/starpilot/starview/GpsBridge.status Ljava/lang/String;
      // dd: ldc "StarView/Gps"
      // df: ldc_w "setup"
      // e2: aload 8
      // e4: checkcast java/lang/Throwable
      // e7: invokestatic android/util/Log.w (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // ea: pop
      // eb: goto f5
      // ee: astore 1
      // ef: ldc_w "not allowed yet: make StarView the mock location app (see below)"
      // f2: putstatic com/starpilot/starview/GpsBridge.status Ljava/lang/String;
      // f5: aload 0
      // f6: monitorexit
      // f7: bipush 0
      // f8: ireturn
      // f9: astore 1
      // fa: aload 0
      // fb: monitorexit
      // fc: aload 1
      // fd: athrow
   }

   public final double[] drPosition$app_debug(double[] var1, double[] var2, double var3) {
      Intrinsics.checkNotNullParameter(var1, "x");
      Intrinsics.checkNotNullParameter(var2, "o");
      double var11 = var1[0];
      double var15 = Math.cos(var11);
      double var19 = Math.sin(var11);
      double var21 = var2[0];
      double var17 = var2[1];
      double var13 = var1[1];
      double var23 = var2[0];
      double var9 = var2[1];
      double var7 = var1[2];
      double var5 = var1[3];
      var7 = (var23 * var19 + var9 * var15 + var7) / 110540.0;
      var9 = var1[4];
      var13 = (var21 * var15 - var17 * var19 + var13) / (Math.cos(Math.toRadians(var1[3])) * 111320.0);
      var11 = Math.toDegrees(var11 + var2[2]);
      boolean var25;
      if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
         var25 = true;
      } else {
         var25 = false;
      }

      if (var25) {
         var3 = var2[3] - var3;
      } else {
         var3 = 0.0;
      }

      return new double[]{var5 + var7, var9 + var13, ((90.0 - var11) % 360.0 + 360.0) % 360.0, var3};
   }

   public final boolean enabled(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      return this.prefs(var1).getBoolean("gps_bridge", true);
   }

   public final void fallback(String var1, float var2) {
      Intrinsics.checkNotNullParameter(var1, "lastGpsJson");
      long var8 = System.currentTimeMillis();
      if (var8 - lastLiveMs < 3000L) {
         mode = "live";
      } else if (var8 - lastDrPushMs < 1500L) {
         mode = "dr";
      } else if (var2 > 2.0F) {
         if (!Intrinsics.areEqual(mode, "none")) {
            this.stop();
            status = "car moving but the comma has no GPS fix: tablet location withdrawn";
         }

         mode = "none";
      } else {
         if (var8 - lastFallbackMs >= 1000L) {
            boolean var7;
            if (var1.length() == 0) {
               var7 = true;
            } else {
               var7 = false;
            }

            if (!var7) {
               lastFallbackMs = var8;
               mode = "last";

               try {
                  JSONObject var10 = new JSONObject(var1);
                  double var3 = var10.optDouble("latitude", Double.NaN);
                  double var5 = var10.optDouble("longitude", Double.NaN);
                  if (Double.isNaN(var3) || Double.isNaN(var5) || Math.abs(var3) < 1.0E-6 && Math.abs(var5) < 1.0E-6) {
                     return;
                  }

                  usingFallback = true;
                  push$default(
                     this,
                     var3,
                     var5,
                     var10.optDouble("altitude", 0.0),
                     0.0F,
                     (float)var10.optDouble("bearing", Double.NaN),
                     25.0F,
                     Float.NaN,
                     Float.NaN,
                     Float.NaN,
                     false,
                     false,
                     false,
                     3072,
                     null
                  );
               } catch (Exception var11) {
               }

               return;
            }
         }
      }
   }

   public final GpsFilter getFilter() {
      return filter;
   }

   public final long getFixes() {
      return fixes;
   }

   public final long getLastDrPushMs() {
      return lastDrPushMs;
   }

   public final long getLastFixMs() {
      return lastFixMs;
   }

   public final long getLastLiveMs() {
      return lastLiveMs;
   }

   public final String getMode() {
      return mode;
   }

   public final float getOutBearing() {
      return outBearing;
   }

   public final String getOutKind() {
      return outKind;
   }

   public final double getOutLat() {
      return outLat;
   }

   public final double getOutLon() {
      return outLon;
   }

   public final String getStatus() {
      return status;
   }

   public final double getTurnSentDeg() {
      return turnSentDeg;
   }

   public final boolean getUsingFallback() {
      return usingFallback;
   }

   public final boolean getUsingRtk() {
      return usingRtk;
   }

   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      app = var1.getApplicationContext();
   }

   public final void onCarState(float var1) {
      long var17 = System.currentTimeMillis();
      vEgoNow = var1;
      double[] var21 = Odometry.INSTANCE.at(System.nanoTime());
      filter.onSpeed(var17, Math.abs(var1), var21);
      double[] var20 = filter.fused(var17, var21);
      String var19 = "dr";
      if (var20 != null) {
         if (var17 - lastFusedPushMs >= 45L) {
            lastFusedPushMs = var17;
            var21 = Odometry.INSTANCE.motion();
            double var24 = var21[0];
            double var8 = var21[1];
            if (var24 > 2.0 && !filter.isHolding(var17)) {
               double var6 = Math.toRadians(var20[2]);
               double var10 = var6 - var8 * 0.4 / 2;
               double var4 = Math.sin(var10);
               var10 = Math.cos(var10);
               var20[0] += var24 * 0.4 * var10 / 110540.0;
               var20[1] += var24 * 0.4 * var4 / (Math.cos(Math.toRadians(var20[0])) * 111320.0);
               var20[2] = (Math.toDegrees(var6 - 0.8 * var8) % 360.0 + 360.0) % 360.0;
            }

            boolean var14;
            if (var20[3] > 1.5) {
               var14 = true;
            } else {
               var14 = false;
            }

            boolean var15;
            if (var1 < 0.1F && filter.isHolding(var17)) {
               var15 = true;
            } else {
               var15 = false;
            }

            if (var14) {
               lastDrPushMs = var17;
            } else {
               var19 = "live";
            }

            mode = var19;
            var24 = var20[0];
            double var27 = var20[1];
            double var26 = lastAlt;
            if (var15) {
               var1 = 0.0F;
            } else {
               var1 = Math.abs(var1);
            }

            float var13 = (float)var20[2];
            float var12;
            if (var14) {
               var12 = (float)RangesKt.coerceAtMost(var20[3] * 2.0 + 5.0, 50.0);
            } else {
               var12 = lastAcc;
            }

            boolean var16;
            if (!var14) {
               var16 = true;
            } else {
               var16 = false;
            }

            this.push(var24, var27, var26, var1, var13, var12, Float.NaN, 3.0F, 0.3F, var16, var14, var15);
         }
      } else if (lastLiveMs != 0L && var17 - lastLiveMs >= 1500L && var17 - lastLiveMs <= 60000L) {
         if (var17 - lastDrPushMs >= 190L) {
            double[] var29 = filter.predict(var17, var21);
            if (var29 != null) {
               double var23 = RangesKt.coerceAtMost(5.0 + var29[3] * 0.03, 50.0);
               lastDrPushMs = var17;
               mode = "dr";
               push$default(
                  this,
                  var29[0],
                  var29[1],
                  lastAlt,
                  RangesKt.coerceAtLeast(var1, 0.0F),
                  (float)var29[2],
                  (float)var23,
                  Float.NaN,
                  10.0F,
                  Float.NaN,
                  false,
                  true,
                  false,
                  2048,
                  null
               );
            } else {
               double[] var30 = drXform;
               if (var30 != null) {
                  if (var17 - drXformAtMs <= 60000L && !Double.isNaN(var30[3])) {
                     double[] var31 = this.drPosition$app_debug(var30, var21, liveOdoDist);
                     double var2 = RangesKt.coerceAtMost(var31[3] * 0.03 + 5.0, 50.0);
                     lastDrPushMs = var17;
                     mode = "dr";
                     push$default(
                        this,
                        var31[0],
                        var31[1],
                        lastAlt,
                        RangesKt.coerceAtLeast(var1, 0.0F),
                        (float)var31[2],
                        (float)var2,
                        Float.NaN,
                        10.0F,
                        Float.NaN,
                        false,
                        true,
                        false,
                        2048,
                        null
                     );
                  }
               }
            }
         }
      }
   }

   public final void onFix(
      double var1, double var3, double var5, float var7, float var8, float var9, float var10, float var11, float var12, String var13, long var14
   ) {
      Intrinsics.checkNotNullParameter(var13, "src");
      long var18 = System.nanoTime();
      Long var20;
      if (var14 != 0L) {
         var20 = Odometry.INSTANCE.monoToLocal(var14);
      } else {
         var20 = null;
      }

      if (var20 != null) {
         var14 = RangesKt.coerceIn(var20, var18 - 2000000000L, var18);
      } else {
         var14 = var18;
      }

      double[] var25 = Odometry.INSTANCE.at(var14);
      double[] var21 = Odometry.INSTANCE.at(var14 - 200000000L);
      double var16 = Math.IEEEremainder(var25[2] - var21[2], Math.PI * 2) / 0.2;
      GpsFilter.Out var22 = filter.onFix(System.currentTimeMillis(), var1, var3, var9, var7, var8, var11, var13, var25, vEgoNow, var16);
      if (var22 != null) {
         lastLiveMs = System.currentTimeMillis();
         usingFallback = false;
         lastAlt = var5;
         lastAcc = var22.getAcc();
         if (filter.fused(System.currentTimeMillis(), var25) == null) {
            var14 = SystemClock.elapsedRealtime();
            if (var14 - lastPushMs >= 90L) {
               lastPushMs = var14;
               lastAlt = var5;
               liveOdoDist = var25[3];
               lastLiveMs = System.currentTimeMillis();
               usingFallback = false;
               push$default(
                  this,
                  var22.getLat(),
                  var22.getLon(),
                  var5,
                  var22.getSpeed(),
                  var22.getBearing(),
                  var22.getAcc(),
                  var10,
                  var11,
                  var12,
                  true,
                  false,
                  Intrinsics.areEqual(var22.getKind(), "held"),
                  1024,
                  null
               );
            }
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final Boolean permitted() {
      Context var4 = app;
      Object var2 = null;
      if (var4 == null) {
         return null;
      } else {
         boolean var1;
         label27: {
            label26: {
               try {
                  Object var3 = var4.getSystemService("appops");
                  Intrinsics.checkNotNull(var3, "null cannot be cast to non-null type android.app.AppOpsManager");
                  if (((AppOpsManager)var3).unsafeCheckOpNoThrow("android:mock_location", Process.myUid(), var4.getPackageName()) == 0) {
                     break label26;
                  }
               } catch (Exception var6) {
                  return (Boolean)var2;
               }

               var1 = false;
               break label27;
            }

            var1 = true;
         }

         Boolean var7;
         try {
            var7 = var1;
         } catch (Exception var5) {
            return (Boolean)var2;
         }

         return var7;
      }
   }

   public final void resetTurn() {
      turnSentDeg = 0.0;
      turnSentLast = Float.NaN;
   }

   public final void setEnabled(Context var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      this.prefs(var1).edit().putBoolean("gps_bridge", var2).apply();
      if (!var2) {
         this.stop();
      }
   }

   public final void setFit(double var1, double var3, double var5, double var7, double var9, boolean var11) {
      long var12 = System.currentTimeMillis();
      drXform = new double[]{var1, var3, var5, var7, var9};
      drXformAtMs = var12;
      if (var11) {
         doubtedAtMs = var12;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void stop() {
      LocationManager var1 = this.lm();
      if (var1 != null) {
         for (String var3 : this.providers()) {
            try {
               Companion var7 = Result.Companion;
               GpsBridge var8 = this;
               var1.setTestProviderEnabled(var3, false);
               var1.removeTestProvider(var3);
               KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable var6) {
               Companion var4 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var6));
               continue;
            }
         }

         ready = false;
         status = "off";
      }
   }
}
