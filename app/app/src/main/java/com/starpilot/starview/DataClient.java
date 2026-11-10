package com.starpilot.starview;

import ai.comma.cereal.Log.Event;
import ai.comma.cereal.Log.Event.Reader;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.OkHttpClient.Builder;
import okio.ByteString;
import org.capnproto.ReaderOptions;
import org.capnproto.Serialize;
import org.json.JSONObject;

public final class DataClient {
   public static final DataClient.Companion Companion = new DataClient.Companion(null);
   private static final ReaderOptions READER_OPTS = new ReaderOptions(8388608L, 64);
   private static final Map<String, Integer> SUBSCRIBE;
   private static final String TAG = "StarView/Data";
   private volatile boolean connected;
   private JSONObject hello;
   private final OkHttpClient http;
   private final Handler main;
   private final Function2<String, Boolean, Unit> onStatus;
   private final ConcurrentHashMap<String, Function1<JSONObject, Unit>> pending;
   private long pingSentNs;
   private final AtomicInteger reqCounter;
   private final Telemetry telemetry;
   private WebSocket ws;

   // $VF: synthetic method
   public static void $r8$lambda$1BDrYwnybIyX7AFXbBBuehQPZxE(Function1 var0) {
      request$lambda$1(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$dhaFPyz5Zo2ZhsOfu7bn_PpawOI/* $VF was: $r8$lambda$dhaFPyz5Zo2ZhsOfu7bn_PpawOI*/(DataClient var0, String var1) {
      request$lambda$0(var0, var1);
   }

   static {
      Integer var27 = 20;
      Pair var0 = TuplesKt.to("carState", var27);
      Integer var13 = 0;
      Pair var1 = TuplesKt.to("selfdriveState", var27);
      Integer var17 = 1;
      Integer var21 = 10;
      Pair var7 = TuplesKt.to("controlsState", var21);
      Pair var12 = TuplesKt.to("carControl", var21);
      Pair var11 = TuplesKt.to("carOutput", var21);
      Integer var14 = 5;
      Pair var8 = TuplesKt.to("liveParameters", var14);
      Pair var10 = TuplesKt.to("longitudinalPlan", var14);
      Pair var2 = TuplesKt.to("deviceState", var13);
      Pair var9 = TuplesKt.to("roadCameraState", var17);
      Pair var3 = TuplesKt.to("liveCalibration", var13);
      Pair var5 = TuplesKt.to("modelV2", var13);
      Pair var6 = TuplesKt.to("radarState", var13);
      Pair var4 = TuplesKt.to("driverMonitoringState", var21);
      Pair var30 = TuplesKt.to("slcState", var14);
      Pair var15 = TuplesKt.to("starpilotLateralState", var21);
      Pair var16 = TuplesKt.to("liveDelay", var17);
      Pair var31 = TuplesKt.to("liveTorqueParameters", var17);
      Pair var26 = TuplesKt.to("carParams", var13);
      Pair var18 = TuplesKt.to("starpilotPlan", var13);
      Pair var23 = TuplesKt.to("starpilotCarState", var21);
      Pair var24 = TuplesKt.to("starpilotRadarState", var21);
      Pair var25 = TuplesKt.to("mapdOut", var13);
      Pair var20 = TuplesKt.to("liveTracks", var21);
      Pair var22 = TuplesKt.to("gpsLocationExternal", var13);
      Pair var19 = TuplesKt.to("gpsLocation", var13);
      Pair var28 = TuplesKt.to("livePose", var27);
      Pair var33 = TuplesKt.to("accelerometer", var21);
      Pair var32 = TuplesKt.to("gyroscope", var21);
      Pair var29 = TuplesKt.to("ubloxGnss", var13);
      SUBSCRIBE = MapsKt.mapOf(
         new Pair[]{
            var0,
            var1,
            var7,
            var12,
            var11,
            var8,
            var10,
            var2,
            var9,
            var3,
            var5,
            var6,
            var4,
            var30,
            var15,
            var16,
            var31,
            var26,
            var18,
            var23,
            var24,
            var25,
            var20,
            var22,
            var19,
            var28,
            var33,
            var32,
            var29
         }
      );
   }

   public DataClient(Telemetry var1, Function2<? super String, ? super Boolean, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "telemetry");
      Intrinsics.checkNotNullParameter(var2, "onStatus");
      super();
      this.telemetry = var1;
      this.onStatus = var2;
      this.http = new Builder().pingInterval(5L, TimeUnit.SECONDS).readTimeout(0L, TimeUnit.MILLISECONDS).build();
      this.pending = new ConcurrentHashMap<>();
      this.main = new Handler(Looper.getMainLooper());
      this.reqCounter = new AtomicInteger(1);
   }

   // $VF: synthetic method
   public static void request$default(DataClient var0, String var1, Map var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = MapsKt.emptyMap();
      }

      if ((var4 & 4) != 0) {
         var3 = null;
      }

      var0.request(var1, var2, var3);
   }

   private static final void request$lambda$0(DataClient var0, String var1) {
      Function1 var2 = var0.pending.remove(var1);
      if (var2 != null) {
         JSONObject var3 = new JSONObject().put("ok", false).put("error", "timeout");
         Intrinsics.checkNotNullExpressionValue(var3, "put(...)");
         var2.invoke(var3);
      }
   }

   private static final void request$lambda$1(Function1 var0) {
      JSONObject var1 = new JSONObject().put("ok", false).put("error", "not connected");
      Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
      var0.invoke(var1);
   }

   public final void close() {
      WebSocket var1 = this.ws;
      if (var1 != null) {
         var1.close(1000, "bye");
      }

      this.ws = null;
      this.connected = false;
   }

   public final void connect(String var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "host");
      this.close();
      Request var3 = Link.INSTANCE.withSession(new okhttp3.Request.Builder().url("ws://" + var1 + ":" + var2 + "/data"), var1, var2).build();
      this.ws = this.http
         .newWebSocket(
            var3,
            new Anon_connect_1(this)
         );
   }

   public final void control(JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "req");
      WebSocket var2 = this.ws;
      if (var2 != null) {
         String var3 = var1.toString();
         Intrinsics.checkNotNullExpressionValue(var3, "toString(...)");
         var2.send(var3);
      }
   }

   public final boolean getConnected() {
      return this.connected;
   }

   public final JSONObject getHello() {
      return this.hello;
   }

   public final void ping() {
      this.pingSentNs = System.nanoTime();
      WebSocket var1 = this.ws;
      if (var1 != null) {
         var1.send("{\"ping\":1}");
      }
   }

   public final void request(String var1, Map<String, ? extends Object> var2, Function1<? super JSONObject, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "action");
      Intrinsics.checkNotNullParameter(var2, "args");
      int var4 = this.reqCounter.getAndIncrement();
      String var6 = "r" + var4;
      JSONObject var7 = new JSONObject();
      var7.put("action", var1);
      var7.put("id", var6);

      for (Entry var10 : var2.entrySet()) {
         var7.put((String)var10.getKey(), var10.getValue());
      }

      if (var3 != null) {
         this.pending.put(var6, var3);
         this.main.postDelayed(new DataClient$$ExternalSyntheticLambda0(this, var6), 90000L);
      }

      WebSocket var9 = this.ws;
      boolean var5;
      if (var9 != null) {
         String var11 = var7.toString();
         Intrinsics.checkNotNullExpressionValue(var11, "toString(...)");
         var5 = var9.send(var11);
      } else {
         var5 = false;
      }

      if (!var5 && var3 != null) {
         this.pending.remove(var6);
         this.main.post(new DataClient$$ExternalSyntheticLambda1(var3));
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final Map<String, Integer> getSUBSCRIBE() {
         return DataClient.SUBSCRIBE;
      }
   }

   static final class Anon_connect_1 extends WebSocketListener {
      Anon_connect_1(DataClient var1) {
         this.this$0 = var1;
      }

               final DataClient this$0;

               // $VF: synthetic method
               public static void $r8$lambda$EdeUDaZ_lE5MMpVVwYFBtCnH8T8(Function1 var0, JSONObject var1) {
                  onMessage$lambda$0(var0, var1);
               }

               // $VF: synthetic method
               public static boolean $r8$lambda$VVK8DCCgFXZayQd91Rm30B2s6AI(String var0) {
                  return onMessage$lambda$1(var0);
               }

               // $VF: synthetic method
               public static CharSequence $r8$lambda$qZYiYUsSHDbByv21R_mwcooth_o/* $VF was: $r8$lambda$qZYiYUsSHDbByv21R_mwcooth_o*/(
                  JSONObject var0, String var1
               ) {
                  return onMessage$lambda$2(var0, var1);
               }

               private static final void onMessage$lambda$0(Function1 var0, JSONObject var1x) {
                  var0.invoke(var1x);
               }

               private static final boolean onMessage$lambda$1(String var0) {
                  return SetsKt.setOf(new String[]{"ok", "type", "id"}).contains(var0) ^ true;
               }

               private static final CharSequence onMessage$lambda$2(JSONObject var0, String var1x) {
                  Object var2x = var0.opt(var1x);
                  return var1x + "=" + var2x;
               }

               @Override
               public void onClosed(WebSocket var1, int var2x, String var3x) {
                  Intrinsics.checkNotNullParameter(var1, "webSocket");
                  Intrinsics.checkNotNullParameter(var3x, "reason");
                  WebSocket var4 = this.this$0.ws;
                  if (var4 == null || var1 == var4) {
                     this.this$0.connected = false;
                     this.this$0.onStatus.invoke("data: closed (" + var2x + ")", false);
                  }
               }

               @Override
               public void onClosing(WebSocket var1, int var2x, String var3x) {
                  Intrinsics.checkNotNullParameter(var1, "webSocket");
                  Intrinsics.checkNotNullParameter(var3x, "reason");
                  var1.close(1000, null);
               }

               @Override
               public void onFailure(WebSocket var1, Throwable var2x, Response var3x) {
                  Intrinsics.checkNotNullParameter(var1, "webSocket");
                  Intrinsics.checkNotNullParameter(var2x, "t");
                  WebSocket var5 = this.this$0.ws;
                  if (var5 == null || var1 == var5) {
                     this.this$0.connected = false;
                     boolean var4;
                     if (var3x != null && var3x.code() == 401) {
                        var4 = true;
                     } else {
                        var4 = false;
                     }

                     if (var4 && Link.INSTANCE.getPaired()) {
                        Link.INSTANCE.forgetSession();
                        this.this$0.onStatus.invoke("data: signing in again", false);
                     } else {
                        if (var3x != null && var3x.code() == 401) {
                           var4 = true;
                        } else {
                           var4 = false;
                        }

                        if (var4) {
                           Link.INSTANCE.setPairingRequired(true);
                           this.this$0.onStatus.invoke("data: pairing required - on the comma open Settings › starview › show qr, then scan it", false);
                        } else {
                           Function2 var6 = this.this$0.onStatus;
                           String var7 = var2x.getMessage();
                           var6.invoke("data: " + var7, false);
                        }
                     }
                  }
               }

               // $VF: Duplicated exception handlers to handle obfuscated exceptions
               @Override
               public void onMessage(WebSocket var1, String var2x) {
                  Intrinsics.checkNotNullParameter(var1, "webSocket");
                  Intrinsics.checkNotNullParameter(var2x, "text");

                  JSONObject var17;
                  try {
                     var17 = new JSONObject(var2x);
                  } catch (Exception var35) {
                     Log.w("StarView/Data", "text: " + var35);
                     return;
                  }

                  boolean var14;
                  try {
                     var14 = Intrinsics.areEqual(var17.optString("type"), "hello");
                  } catch (Exception var34) {
                     Log.w("StarView/Data", "text: " + var34);
                     return;
                  }

                  if (var14) {
                     try {
                        this.this$0.hello = var17;
                        Function2 var46 = this.this$0.onStatus;
                        String var36 = var17.optString("dongleId");
                        String var18 = var17.optString("branch");
                        String var57 = var17.optString("commit");
                        StringBuilder var19 = new StringBuilder();
                        var46.invoke(var19.append("data: hello from ").append(var36).append(" ").append(var18).append(" ").append(var57).toString(), true);
                     } catch (Exception var22) {
                        Log.w("StarView/Data", "text: " + var22);
                     }
                  } else {
                     try {
                        if (var17.has("pong")) {
                           this.this$0.telemetry.setRttMs((System.nanoTime() - this.this$0.pingSentNs) / 1000000.0);
                           return;
                        }
                     } catch (Exception var33) {
                        Log.w("StarView/Data", "text: " + var33);
                        return;
                     }

                     try {
                        if (Intrinsics.areEqual(var17.optString("type"), "params")) {
                           this.this$0.telemetry.applyParams(var17);
                           return;
                        }
                     } catch (Exception var32) {
                        Log.w("StarView/Data", "text: " + var32);
                        return;
                     }

                     double var3x;
                     double var5;
                     double var7;
                     double var9;
                     double var11;
                     boolean var13;
                     label172: {
                        label145: {
                           label144: {
                              try {
                                 if (!Intrinsics.areEqual(var17.optString("type"), "rtk")) {
                                    break label145;
                                 }

                                 long var15 = System.currentTimeMillis();
                                 var7 = var17.optDouble("lat", Double.NaN);
                                 var11 = var17.optDouble("lon", Double.NaN);
                                 var3x = var17.optDouble("hAccM", Double.NaN);
                                 var9 = var17.optDouble("headingDeg", Double.NaN);
                                 var5 = var17.optDouble("speedMs", Double.NaN);
                                 RtkPosition var47 = RtkPosition.INSTANCE;
                                 String var37 = var17.optString("rtk", "none");
                                 Intrinsics.checkNotNullExpressionValue(var37, "optString(...)");
                                 var47.feedFast(var7, var11, var3x, var9, var5, var37, var17.optLong("ageMs", 0L), var15);
                                 if (!Double.isInfinite(var7) && !Double.isNaN(var7)) {
                                    break label144;
                                 }
                              } catch (Exception var31) {
                                 Log.w("StarView/Data", "text: " + var31);
                                 return;
                              }

                              var13 = false;
                              break label172;
                           }

                           var13 = true;
                           break label172;
                        }

                        label131: {
                           label130: {
                              try {
                                 if (!Intrinsics.areEqual(var17.optString("type"), "ack")) {
                                    return;
                                 }

                                 var38 = var17.optString("id", "");
                                 Intrinsics.checkNotNull(var38);
                                 if (var38.length() > 0) {
                                    break label130;
                                 }
                              } catch (Exception var30) {
                                 Log.w("StarView/Data", "text: " + var30);
                                 return;
                              }

                              var13 = false;
                              break label131;
                           }

                           var13 = true;
                        }

                        Function1 var39;
                        if (var13) {
                           try {
                              var39 = this.this$0.pending.remove(var38);
                           } catch (Exception var29) {
                              Log.w("StarView/Data", "text: " + var29);
                              return;
                           }
                        } else {
                           var39 = null;
                        }

                        if (var39 != null) {
                           try {
                              Handler var48 = this.this$0.main;
                              DataClient$connect$1$$ExternalSyntheticLambda0 var59 = new DataClient$connect$1$$ExternalSyntheticLambda0(var39, var17);
                              var48.post(var59);
                              return;
                           } catch (Exception var28) {
                              Log.w("StarView/Data", "text: " + var28);
                              return;
                           }
                        } else {
                           try {
                              var14 = var17.optBoolean("ok");
                           } catch (Exception var26) {
                              Log.w("StarView/Data", "text: " + var26);
                              return;
                           }

                           String var44;
                           if (var14) {
                              try {
                                 Iterator var40 = var17.keys();
                                 Intrinsics.checkNotNullExpressionValue(var40, "keys(...)");
                                 Sequence var49 = SequencesKt.asSequence(var40);
                                 DataClient$connect$1$$ExternalSyntheticLambda1 var41 = new DataClient$connect$1$$ExternalSyntheticLambda1();
                                 Iterable var50 = SequencesKt.toList(SequencesKt.filter(var49, var41));
                                 CharSequence var60 = " ";
                                 DataClient$connect$1$$ExternalSyntheticLambda2 var42 = new DataClient$connect$1$$ExternalSyntheticLambda2(var17);
                                 var2x = CollectionsKt.joinToString$default(var50, var60, null, null, 0, null, var42, 30, null);
                                 StringBuilder var43 = new StringBuilder();
                                 var44 = var43.append("OK ").append(var2x).toString();
                              } catch (Exception var25) {
                                 Log.w("StarView/Data", "text: " + var25);
                                 return;
                              }
                           } else {
                              try {
                                 var2x = var17.optString("error");
                                 StringBuilder var45 = new StringBuilder();
                                 var44 = var45.append("Rejected: ").append(var2x).toString();
                              } catch (Exception var24) {
                                 Log.w("StarView/Data", "text: " + var24);
                                 return;
                              }
                           }

                           if (!var14) {
                              try {
                                 Log.w("StarView/Data", var44);
                              } catch (Exception var23) {
                                 Log.w("StarView/Data", "text: " + var23);
                                 return;
                              }
                           }

                           try {
                              Function2 var53 = this.this$0.onStatus;
                              StringBuilder var58 = new StringBuilder();
                              var53.invoke(var58.append("ack: ").append(var44).toString(), true);
                           } catch (Exception var20) {
                              Log.w("StarView/Data", "text: " + var20);
                           }

                           return;
                        }
                     }

                     if (var13) {
                        label112: {
                           label111: {
                              try {
                                 if (!Double.isInfinite(var3x) && !Double.isNaN(var3x)) {
                                    break label111;
                                 }
                              } catch (Exception var27) {
                                 Log.w("StarView/Data", "text: " + var27);
                                 return;
                              }

                              var13 = false;
                              break label112;
                           }

                           var13 = true;
                        }

                        if (var13 && var3x < 3.0) {
                           try {
                              OsmRoads.INSTANCE
                                 .onFix(var7, var11, (float)var9, (float)var5, (float)var3x, (float)var17.optDouble("headingAccDeg", Double.NaN), 0L, 0L, true);
                           } catch (Exception var21) {
                              Log.w("StarView/Data", "text: " + var21);
                           }
                        }
                     }
                  }
               }

               @Override
               public void onMessage(WebSocket var1, ByteString var2x) {
                  Intrinsics.checkNotNullParameter(var1, "webSocket");
                  Intrinsics.checkNotNullParameter(var2x, "bytes");

                  try {
                     int var3x = var2x.getByte(0) & 255;
                     String var5 = var2x.substring(1, var3x + 1).utf8();
                     ByteBuffer var7 = var2x.asByteBuffer();
                     ((Buffer)var7).position(var3x + 1);
                     ByteBuffer var8 = var7.slice();
                     this.this$0.telemetry.count(var5, var8.remaining());
                     Reader var6 = Serialize.read(var8.order(ByteOrder.LITTLE_ENDIAN), DataClient.READER_OPTS).getRoot(Event.factory);
                     Telemetry var9 = this.this$0.telemetry;
                     Intrinsics.checkNotNull(var6);
                     var9.apply(var6);
                  } catch (Exception var4) {
                     Log.w("StarView/Data", "frame: " + var4);
                  }
               }

               @Override
               public void onOpen(WebSocket var1, Response var2x) {
                  Intrinsics.checkNotNullParameter(var1, "webSocket");
                  Intrinsics.checkNotNullParameter(var2x, "response");
                  this.this$0.connected = true;
                  String var3x = new JSONObject(MapsKt.mapOf(TuplesKt.to("subscribe", new JSONObject(DataClient.Companion.getSUBSCRIBE())))).toString();
                  Intrinsics.checkNotNullExpressionValue(var3x, "toString(...)");
                  var1.send(var3x);
                  this.this$0.onStatus.invoke("data: connected", true);
               }
               }
}
