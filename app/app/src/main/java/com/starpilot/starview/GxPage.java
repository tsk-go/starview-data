package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import androidx.core.widget.NestedScrollView;
import java.io.Closeable;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.Ref.DoubleRef;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public abstract class GxPage extends FrameLayout {
   public static final GxPage.Companion Companion = new GxPage.Companion(null);
   private static final MediaType JSON = MediaType.Companion.get("application/json; charset=utf-8");
   private static final HashMap<Long, OkHttpClient> clients = new HashMap<>();
   private final Activity act;
   private boolean built;
   private final LinearLayout col;
   private final GxHost gx;
   private final Handler main;
   private final boolean needsOffroad;
   private final ArrayList<Runnable> pollRunners;
   private final ArrayList<Pair<Long, Function0<Unit>>> polls;
   private final NestedScrollView scroll;
   private boolean shown;
   private final CarUi ui;

   // $VF: synthetic method
   public static Unit $r8$lambda$3jVi8nvSOxIQOGXu0jaQ6Ox04Bw(String var0, JSONObject var1) {
      return send$lambda$14(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$591ZT9YkSRkFUTycFuGPNeqPTRg(Function0 var0, DialogInterface var1, int var2) {
      confirm$lambda$69(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7QwwaRIsuV1PXBg0sHbaTa1qjdw(Function2 var0, int var1, String var2, String var3) {
      return getText$lambda$13(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$BCc6LkH6Ixf8BgGYysLh4WKCVjc(String var0, String var1, String var2, GxPage var3, String var4) {
      download$lambda$29(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static String $r8$lambda$DcH2_ciURqWWTWnI9fF3HGSj7LA/* $VF was: $r8$lambda$DcH2_ciURqWWTWnI9fF3HGSj7LA*/(double var0) {
      return stepper$lambda$46(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$EB6ZjHoSmPnWC0gfvV0XDOICD1c(Function2 var0, String var1, Object var2) {
      return getArray$lambda$12(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FjJeObTGz5t9nZWZ9HDUsl36Gqg(GxPage var0, String var1, boolean var2) {
      return paramToggle$lambda$44(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GXLiMD2B7X66zeGAlitFOOL_rPQ(Function2 var0, String var1, Object var2) {
      return upload$lambda$18(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KABUoisI2SH8fSIFX2CpuZbzdlI(
      DoubleRef var0, double var1, double var3, double var5, Function1 var7, TextView var8, Function1 var9
   ) {
      return stepper$lambda$49(var0, var1, var3, var5, var7, var8, var9);
   }

   // $VF: synthetic method
   public static void $r8$lambda$MlvOLXeVZRROwU6ugEOMH0LQc8k(NestedScrollView var0) {
      logToEnd$lambda$58$lambda$57(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$NGxkzleH6T0zamm7V8GWx6qBY98(GxPage var0, Function2 var1, byte[] var2, String var3) {
      readUri$lambda$24$lambda$23(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SOgW_p_33ZR2MF_B8dzPt2mwmqY/* $VF was: $r8$lambda$SOgW_p_33ZR2MF_B8dzPt2mwmqY*/(
      ObjectRef var0, HashMap var1, Function1 var2, GxPage var3, LinearLayout var4, Function2 var5, int var6
   ) {
      return tabs$lambda$67(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SlaiSiUWB7TzOMIqiVCkRvfhd6Y(Function2 var0, String var1, Object var2) {
      return send$lambda$15(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$TETaejmVgVBjqwq5Qm7H1GxNkGY(ObjectRef var0, Function1 var1, int var2) {
      return choice$lambda$45(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$WwwUx2GYS_XSyGFRrXOm4FQ4wfU/* $VF was: $r8$lambda$WwwUx2GYS_XSyGFRrXOm4FQ4wfU*/(
      DoubleRef var0, TextView var1, Function1 var2, double var3
   ) {
      return stepper$lambda$50(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XVH6SfewfMKdfcpduM9L1tomRjk(
      DoubleRef var0, double var1, double var3, double var5, Function1 var7, TextView var8, Function1 var9
   ) {
      return stepper$lambda$48(var0, var1, var3, var5, var7, var8, var9);
   }

   // $VF: synthetic method
   public static void $r8$lambda$YEUmXzg_ZFKpOV1bUx_PaOtlIKs/* $VF was: $r8$lambda$YEUmXzg_ZFKpOV1bUx_PaOtlIKs*/(
      boolean[] var0, DialogInterface var1, int var2, boolean var3
   ) {
      pickMany$lambda$75(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$fe0H9llZ3wgxQdxlkl3eKHoeCjQ(Object var0, String var1, String var2, String var3, long var4, GxPage var6, Function3 var7) {
      call$lambda$8(var0, var1, var2, var3, var4, var6, var7);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gO5NdWtKDZ8Y7YdsXaYhXNKq8KQ(GxPage var0, Function1 var1, String var2) {
      return putParam$lambda$36(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hEC3C5IHeA14GFOhqcD5n6WHZac(
      ObjectRef var0, HashMap var1, Function1 var2, GxPage var3, LinearLayout var4, Function2 var5, int var6
   ) {
      return tabs$lambda$66(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ixkkVwnwTOzhkaGFpcO9YT1zF28(Function1 var0, DialogInterface var1, int var2) {
      pick$lambda$74(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$k2wISKvh0VJhtqub5TG_6LcGc5s/* $VF was: $r8$lambda$k2wISKvh0VJhtqub5TG_6LcGc5s*/(
      GxPage var0, Function2 var1, int var2, String var3, String var4
   ) {
      return json$lambda$10(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$nTiggnGnY9YuTMe9HVDQldwyRLI(Function1 var0, boolean[] var1, DialogInterface var2, int var3) {
      pickMany$lambda$77(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nkxF_CLSebcobXq1Y2GGchJDaKk(GxPage var0, String var1, Function1 var2, String var3, JSONObject var4) {
      return doAction$lambda$17(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$opVGb9O5t_7CGZ_heAAP8i7XF48(GxPage var0, Function3 var1, IntRef var2, ObjectRef var3, ObjectRef var4) {
      call$lambda$8$lambda$7(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$uAvjxwXd0x_skf3KNleB9Ck0dGs/* $VF was: $r8$lambda$uAvjxwXd0x_skf3KNleB9Ck0dGs*/(GxPage var0, Uri var1, Function2 var2) {
      readUri$lambda$24(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$vX1m71ZggLIPXHZ9e__C_pVcN9c/* $VF was: $r8$lambda$vX1m71ZggLIPXHZ9e__C_pVcN9c*/(
      GxPage var0, String var1, String var2, ImageView var3, Function1 var4
   ) {
      loadImage$lambda$34(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$x0dS817ob5t8Vqwb5MmjGssXEQY(ObjectRef var0, int var1) {
      return tabs$lambda$61(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$yVjMa_0kEGHt09PS4eEzqHeDKoI(GxPage var0, ObjectRef var1) {
      download$lambda$29$lambda$28(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$yqSAXtiz_kUv_SGGlvqa4WVMJDg/* $VF was: $r8$lambda$yqSAXtiz_kUv_SGGlvqa4WVMJDg*/(
      Function1 var0, EditText var1, DialogInterface var2, int var3
   ) {
      prompt$lambda$73(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zBYWh__oWXzC6fT1O7em4yER9q0(Function2 var0, String var1, Object var2) {
      return getJson$lambda$11(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$zzY3TNfMq88vw6z4xhdlz5wrFRU(Bitmap var0, ImageView var1, Function1 var2) {
      loadImage$lambda$34$lambda$33(var0, var1, var2);
   }

   public GxPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super((Context)var1);
      this.act = var1;
      this.gx = var2;
      this.ui = new CarUi((Context)this.act, false, 2, null);
      this.main = new Handler(Looper.getMainLooper());
      NestedScrollView var3 = new NestedScrollView((Context)this.act);
      var3.setFillViewport(true);
      this.scroll = var3;
      LinearLayout var4 = new LinearLayout((Context)this.act);
      var4.setOrientation(1);
      var4.setPadding(this.ui.dp(10), this.ui.dp(4), this.ui.dp(24), this.ui.dp(60));
      this.col = var4;
      this.polls = new ArrayList<>();
      this.pollRunners = new ArrayList<>();
      this.scroll.addView((View)this.col, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -2)));
      this.addView((View)this.scroll, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
   }

   // $VF: synthetic method
   public static final HashMap access$getClients$cp() {
      return clients;
   }

   // $VF: synthetic method
   public static LinearLayout box$default(GxPage var0, ViewGroup var1, int var2, Object var3) {
      if (var3 == null) {
         if ((var2 & 1) != 0) {
            var1 = (ViewGroup)var0.col;
         }

         return var0.box(var1);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: box");
      }
   }

   // $VF: synthetic method
   public static LinearLayout buttons$default(GxPage var0, Pair[] var1, ViewGroup var2, boolean var3, int var4, Object var5) {
      if (var5 == null) {
         if ((var4 & 2) != 0) {
            var2 = (ViewGroup)var0.col;
         }

         if ((var4 & 4) != 0) {
            var3 = false;
         }

         return var0.buttons(var1, var2, var3);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: buttons");
      }
   }

   // $VF: synthetic method
   public static void call$default(GxPage var0, String var1, String var2, Object var3, long var4, Function3 var6, int var7, Object var8) {
      if (var8 == null) {
         if ((var7 & 4) != 0) {
            var3 = null;
         }

         if ((var7 & 8) != 0) {
            var4 = 20L;
         }

         var0.call(var1, var2, var3, var4, var6);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: call");
      }
   }

   private static final void call$lambda$8(Object param0, String param1, String param2, String param3, long param4, GxPage param6, Function3 param7) {
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
      // 000: new kotlin/jvm/internal/Ref$IntRef
      // 003: dup
      // 004: invokespecial kotlin/jvm/internal/Ref$IntRef.<init> ()V
      // 007: astore 13
      // 009: aload 13
      // 00b: bipush -1
      // 00c: putfield kotlin/jvm/internal/Ref$IntRef.element I
      // 00f: new kotlin/jvm/internal/Ref$ObjectRef
      // 012: dup
      // 013: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 016: astore 12
      // 018: ldc_w ""
      // 01b: astore 10
      // 01d: aload 12
      // 01f: ldc_w ""
      // 022: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 025: new kotlin/jvm/internal/Ref$ObjectRef
      // 028: dup
      // 029: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 02c: astore 11
      // 02e: aload 0
      // 02f: ifnonnull 05e
      // 032: aload 1
      // 033: ldc_w "GET"
      // 036: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 039: ifne 059
      // 03c: aload 1
      // 03d: ldc_w "DELETE"
      // 040: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 043: ifeq 049
      // 046: goto 059
      // 049: getstatic okhttp3/RequestBody.Companion Lokhttp3/RequestBody$Companion;
      // 04c: ldc_w "{}"
      // 04f: getstatic com/starpilot/starview/GxPage.JSON Lokhttp3/MediaType;
      // 052: invokevirtual okhttp3/RequestBody$Companion.create (Ljava/lang/String;Lokhttp3/MediaType;)Lokhttp3/RequestBody;
      // 055: astore 0
      // 056: goto 09d
      // 059: aconst_null
      // 05a: astore 0
      // 05b: goto 09d
      // 05e: aload 0
      // 05f: instanceof org/json/JSONObject
      // 062: ifne 08f
      // 065: aload 0
      // 066: instanceof org/json/JSONArray
      // 069: ifeq 06f
      // 06c: goto 08f
      // 06f: aload 0
      // 070: instanceof okhttp3/RequestBody
      // 073: ifeq 07e
      // 076: aload 0
      // 077: checkcast okhttp3/RequestBody
      // 07a: astore 0
      // 07b: goto 09d
      // 07e: getstatic okhttp3/RequestBody.Companion Lokhttp3/RequestBody$Companion;
      // 081: aload 0
      // 082: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 085: getstatic com/starpilot/starview/GxPage.JSON Lokhttp3/MediaType;
      // 088: invokevirtual okhttp3/RequestBody$Companion.create (Ljava/lang/String;Lokhttp3/MediaType;)Lokhttp3/RequestBody;
      // 08b: astore 0
      // 08c: goto 09d
      // 08f: getstatic okhttp3/RequestBody.Companion Lokhttp3/RequestBody$Companion;
      // 092: aload 0
      // 093: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 096: getstatic com/starpilot/starview/GxPage.JSON Lokhttp3/MediaType;
      // 099: invokevirtual okhttp3/RequestBody$Companion.create (Ljava/lang/String;Lokhttp3/MediaType;)Lokhttp3/RequestBody;
      // 09c: astore 0
      // 09d: new okhttp3/Request$Builder
      // 0a0: astore 14
      // 0a2: aload 14
      // 0a4: invokespecial okhttp3/Request$Builder.<init> ()V
      // 0a7: new java/lang/StringBuilder
      // 0aa: astore 15
      // 0ac: aload 15
      // 0ae: invokespecial java/lang/StringBuilder.<init> ()V
      // 0b1: aload 15
      // 0b3: ldc_w "http://"
      // 0b6: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0b9: astore 15
      // 0bb: aload 15
      // 0bd: aload 2
      // 0be: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0c1: ldc_w ":8082"
      // 0c4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0c7: astore 2
      // 0c8: aload 14
      // 0ca: aload 2
      // 0cb: aload 3
      // 0cc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0cf: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0d2: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 0d5: aload 1
      // 0d6: aload 0
      // 0d7: invokevirtual okhttp3/Request$Builder.method (Ljava/lang/String;Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 0da: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 0dd: astore 0
      // 0de: getstatic com/starpilot/starview/GxPage.Companion Lcom/starpilot/starview/GxPage$Companion;
      // 0e1: astore 1
      // 0e2: aload 1
      // 0e3: lload 4
      // 0e5: invokevirtual com/starpilot/starview/GxPage$Companion.client (J)Lokhttp3/OkHttpClient;
      // 0e8: aload 0
      // 0e9: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 0ec: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 0f1: checkcast java/io/Closeable
      // 0f4: astore 2
      // 0f5: aload 2
      // 0f6: checkcast okhttp3/Response
      // 0f9: astore 3
      // 0fa: aload 13
      // 0fc: aload 3
      // 0fd: invokevirtual okhttp3/Response.code ()I
      // 100: putfield kotlin/jvm/internal/Ref$IntRef.element I
      // 103: aload 3
      // 104: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 107: astore 1
      // 108: aload 10
      // 10a: astore 0
      // 10b: aload 1
      // 10c: ifnull 11e
      // 10f: aload 1
      // 110: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 113: astore 0
      // 114: aload 0
      // 115: ifnonnull 11e
      // 118: aload 10
      // 11a: astore 0
      // 11b: goto 11e
      // 11e: aload 12
      // 120: aload 0
      // 121: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 124: aload 3
      // 125: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 128: istore 9
      // 12a: iload 9
      // 12c: ifne 1ce
      // 12f: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 132: astore 0
      // 133: new org/json/JSONObject
      // 136: astore 10
      // 138: aload 10
      // 13a: aload 12
      // 13c: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 13f: checkcast java/lang/String
      // 142: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 145: aload 10
      // 147: ldc_w "error"
      // 14a: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 14d: checkcast java/lang/CharSequence
      // 150: astore 1
      // 151: aload 1
      // 152: astore 0
      // 153: aload 1
      // 154: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 157: ifeq 163
      // 15a: aload 10
      // 15c: ldc_w "message"
      // 15f: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 162: astore 0
      // 163: aload 0
      // 164: checkcast java/lang/String
      // 167: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 16a: astore 0
      // 16b: goto 17b
      // 16e: astore 0
      // 16f: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 172: astore 1
      // 173: aload 0
      // 174: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 177: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 17a: astore 0
      // 17b: aload 0
      // 17c: astore 1
      // 17d: aload 0
      // 17e: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 181: ifeq 186
      // 184: aconst_null
      // 185: astore 1
      // 186: aload 1
      // 187: checkcast java/lang/String
      // 18a: astore 0
      // 18b: aload 0
      // 18c: ifnull 1aa
      // 18f: aload 0
      // 190: checkcast java/lang/CharSequence
      // 193: astore 1
      // 194: aload 1
      // 195: astore 0
      // 196: aload 1
      // 197: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 19a: ifeq 19f
      // 19d: aconst_null
      // 19e: astore 0
      // 19f: aload 0
      // 1a0: checkcast java/lang/String
      // 1a3: astore 1
      // 1a4: aload 1
      // 1a5: astore 0
      // 1a6: aload 1
      // 1a7: ifnonnull 1c8
      // 1aa: aload 3
      // 1ab: invokevirtual okhttp3/Response.code ()I
      // 1ae: istore 8
      // 1b0: new java/lang/StringBuilder
      // 1b3: astore 0
      // 1b4: aload 0
      // 1b5: invokespecial java/lang/StringBuilder.<init> ()V
      // 1b8: aload 0
      // 1b9: ldc_w "HTTP "
      // 1bc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1bf: iload 8
      // 1c1: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1c4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1c7: astore 0
      // 1c8: aload 11
      // 1ca: aload 0
      // 1cb: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 1ce: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1d1: astore 0
      // 1d2: aload 2
      // 1d3: aconst_null
      // 1d4: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1d7: goto 208
      // 1da: astore 0
      // 1db: aload 0
      // 1dc: athrow
      // 1dd: astore 1
      // 1de: aload 2
      // 1df: aload 0
      // 1e0: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1e3: aload 1
      // 1e4: athrow
      // 1e5: astore 0
      // 1e6: goto 1f2
      // 1e9: astore 0
      // 1ea: goto 1f2
      // 1ed: astore 0
      // 1ee: goto 1f2
      // 1f1: astore 0
      // 1f2: aload 0
      // 1f3: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 1f6: astore 2
      // 1f7: aload 2
      // 1f8: astore 1
      // 1f9: aload 2
      // 1fa: ifnonnull 202
      // 1fd: aload 0
      // 1fe: invokevirtual java/lang/Exception.toString ()Ljava/lang/String;
      // 201: astore 1
      // 202: aload 11
      // 204: aload 1
      // 205: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 208: aload 6
      // 20a: getfield com/starpilot/starview/GxPage.main Landroid/os/Handler;
      // 20d: new com/starpilot/starview/GxPage$$ExternalSyntheticLambda16
      // 210: dup
      // 211: aload 6
      // 213: aload 7
      // 215: aload 13
      // 217: aload 12
      // 219: aload 11
      // 21b: invokespecial com/starpilot/starview/GxPage$$ExternalSyntheticLambda16.<init> (Lcom/starpilot/starview/GxPage;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/internal/Ref$IntRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 21e: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 221: pop
      // 222: return
   }

   private static final void call$lambda$8$lambda$7(GxPage var0, Function3 var1, IntRef var2, ObjectRef var3, ObjectRef var4) {
      if (!var0.act.isFinishing() && !var0.act.isDestroyed()) {
         var1.invoke(var2.element, var3.element, var4.element);
      }
   }

   // $VF: synthetic method
   public static LinearLayout card$default(GxPage var0, ViewGroup var1, int var2, Object var3) {
      if (var3 == null) {
         if ((var2 & 1) != 0) {
            var1 = (ViewGroup)var0.col;
         }

         return var0.card(var1);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: card");
      }
   }

   // $VF: synthetic method
   public static Function1 choice$default(GxPage var0, String var1, String var2, List var3, int var4, ViewGroup var5, Function1 var6, int var7, Object var8) {
      if (var8 == null) {
         if ((var7 & 16) != 0) {
            var5 = (ViewGroup)var0.col;
         }

         return var0.choice(var1, var2, var3, var4, var5, var6);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: choice");
      }
   }

   private static final Unit choice$lambda$45(ObjectRef var0, Function1 var1, int var2) {
      Function1 var3;
      if (var0.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var3 = null;
      } else {
         var3 = (Function1)var0.element;
      }

      var3.invoke(var2);
      var1.invoke(var2);
      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void confirm$default(GxPage var0, String var1, String var2, String var3, Function0 var4, int var5, Object var6) {
      if (var6 == null) {
         if ((var5 & 4) != 0) {
            var3 = "OK";
         }

         var0.confirm(var1, var2, var3, var4);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: confirm");
      }
   }

   private static final void confirm$lambda$69(Function0 var0, DialogInterface var1, int var2) {
      var0.invoke();
   }

   // $VF: synthetic method
   public static void doAction$default(GxPage var0, String var1, String var2, JSONObject var3, String var4, Function1 var5, int var6, Object var7) {
      if (var7 == null) {
         if ((var6 & 4) != 0) {
            var3 = null;
         }

         if ((var6 & 8) != 0) {
            var4 = null;
         }

         if ((var6 & 16) != 0) {
            var5 = null;
         }

         var0.doAction(var1, var2, var3, var4, var5);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: doAction");
      }
   }

   private static final Unit doAction$lambda$17(GxPage var0, String var1, Function1 var2, String var3, JSONObject var4) {
      if (var3 != null) {
         var0.toast(var3);
      } else {
         if (var1 == null) {
            Object var6 = null;
            Object var5 = null;
            var1 = (String)var6;
            if (var4 != null) {
               String var7 = var4.optString("message");
               var1 = (String)var6;
               if (var7 != null) {
                  var1 = var7;
                  if (StringsKt.isBlank(var1)) {
                     var1 = (String)var5;
                  }

                  var1 = var1;
               }
            }
         }

         if (var1 != null) {
            var0.toast(var1);
         }
      }

      if (var3 == null && var2 != null) {
         var2.invoke(var4);
      }

      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void download$default(GxPage var0, String var1, String var2, String var3, int var4, Object var5) {
      if (var5 == null) {
         if ((var4 & 4) != 0) {
            var3 = "application/octet-stream";
         }

         var0.download(var1, var2, var3);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: download");
      }
   }

   private static final void download$lambda$29(String param0, String param1, String param2, GxPage param3, String param4) {
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
      // 000: new kotlin/jvm/internal/Ref$ObjectRef
      // 003: dup
      // 004: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 007: astore 6
      // 009: getstatic com/starpilot/starview/GxPage.Companion Lcom/starpilot/starview/GxPage$Companion;
      // 00c: ldc2_w 300
      // 00f: invokevirtual com/starpilot/starview/GxPage$Companion.client (J)Lokhttp3/OkHttpClient;
      // 012: astore 8
      // 014: new okhttp3/Request$Builder
      // 017: astore 9
      // 019: aload 9
      // 01b: invokespecial okhttp3/Request$Builder.<init> ()V
      // 01e: new java/lang/StringBuilder
      // 021: astore 7
      // 023: aload 7
      // 025: invokespecial java/lang/StringBuilder.<init> ()V
      // 028: aload 8
      // 02a: aload 9
      // 02c: aload 7
      // 02e: ldc_w "http://"
      // 031: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 034: aload 0
      // 035: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 038: ldc_w ":8082"
      // 03b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 03e: aload 1
      // 03f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 042: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 045: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 048: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 04b: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 04e: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 053: checkcast java/io/Closeable
      // 056: astore 1
      // 057: aload 1
      // 058: checkcast okhttp3/Response
      // 05b: astore 0
      // 05c: aload 0
      // 05d: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 060: ifeq 123
      // 063: new android/content/ContentValues
      // 066: astore 7
      // 068: aload 7
      // 06a: invokespecial android/content/ContentValues.<init> ()V
      // 06d: aload 7
      // 06f: ldc_w "_display_name"
      // 072: aload 2
      // 073: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 076: aload 7
      // 078: ldc_w "mime_type"
      // 07b: aload 4
      // 07d: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 080: aload 7
      // 082: ldc_w "relative_path"
      // 085: ldc_w "Download/StarView"
      // 088: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 08b: aload 3
      // 08c: getfield com/starpilot/starview/GxPage.act Landroid/app/Activity;
      // 08f: invokevirtual android/app/Activity.getContentResolver ()Landroid/content/ContentResolver;
      // 092: getstatic android/provider/MediaStore$Downloads.EXTERNAL_CONTENT_URI Landroid/net/Uri;
      // 095: aload 7
      // 097: invokevirtual android/content/ContentResolver.insert (Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
      // 09a: astore 4
      // 09c: aload 4
      // 09e: ifnull 116
      // 0a1: aload 3
      // 0a2: getfield com/starpilot/starview/GxPage.act Landroid/app/Activity;
      // 0a5: invokevirtual android/app/Activity.getContentResolver ()Landroid/content/ContentResolver;
      // 0a8: aload 4
      // 0aa: invokevirtual android/content/ContentResolver.openOutputStream (Landroid/net/Uri;)Ljava/io/OutputStream;
      // 0ad: astore 4
      // 0af: aload 4
      // 0b1: ifnull 0f3
      // 0b4: aload 4
      // 0b6: checkcast java/io/Closeable
      // 0b9: astore 4
      // 0bb: aload 4
      // 0bd: checkcast java/io/OutputStream
      // 0c0: astore 7
      // 0c2: aload 0
      // 0c3: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0c6: astore 0
      // 0c7: aload 0
      // 0c8: ifnull 0de
      // 0cb: aload 0
      // 0cc: invokevirtual okhttp3/ResponseBody.byteStream ()Ljava/io/InputStream;
      // 0cf: astore 0
      // 0d0: aload 0
      // 0d1: ifnull 0de
      // 0d4: aload 0
      // 0d5: aload 7
      // 0d7: bipush 0
      // 0d8: bipush 2
      // 0d9: aconst_null
      // 0da: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
      // 0dd: pop2
      // 0de: aload 4
      // 0e0: aconst_null
      // 0e1: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0e4: goto 0f3
      // 0e7: astore 2
      // 0e8: aload 2
      // 0e9: athrow
      // 0ea: astore 0
      // 0eb: aload 4
      // 0ed: aload 2
      // 0ee: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0f1: aload 0
      // 0f2: athrow
      // 0f3: aload 1
      // 0f4: aconst_null
      // 0f5: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0f8: new java/lang/StringBuilder
      // 0fb: astore 0
      // 0fc: aload 0
      // 0fd: invokespecial java/lang/StringBuilder.<init> ()V
      // 100: aload 6
      // 102: aload 0
      // 103: ldc_w "Saved to Downloads/StarView/"
      // 106: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 109: aload 2
      // 10a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 10d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 110: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 113: goto 17c
      // 116: new java/lang/IllegalStateException
      // 119: astore 0
      // 11a: aload 0
      // 11b: ldc_w "cannot create file"
      // 11e: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 121: aload 0
      // 122: athrow
      // 123: new java/lang/IllegalStateException
      // 126: astore 2
      // 127: aload 0
      // 128: invokevirtual okhttp3/Response.code ()I
      // 12b: istore 5
      // 12d: new java/lang/StringBuilder
      // 130: astore 0
      // 131: aload 0
      // 132: invokespecial java/lang/StringBuilder.<init> ()V
      // 135: aload 2
      // 136: aload 0
      // 137: ldc_w "HTTP "
      // 13a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 13d: iload 5
      // 13f: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 142: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 145: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 148: aload 2
      // 149: athrow
      // 14a: astore 0
      // 14b: goto 14f
      // 14e: astore 0
      // 14f: aload 0
      // 150: athrow
      // 151: astore 2
      // 152: aload 1
      // 153: aload 0
      // 154: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 157: aload 2
      // 158: athrow
      // 159: astore 0
      // 15a: goto 15e
      // 15d: astore 0
      // 15e: aload 0
      // 15f: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 162: astore 0
      // 163: aload 6
      // 165: new java/lang/StringBuilder
      // 168: dup
      // 169: invokespecial java/lang/StringBuilder.<init> ()V
      // 16c: ldc_w "Download failed: "
      // 16f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 172: aload 0
      // 173: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 176: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 179: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 17c: aload 3
      // 17d: getfield com/starpilot/starview/GxPage.main Landroid/os/Handler;
      // 180: new com/starpilot/starview/GxPage$$ExternalSyntheticLambda2
      // 183: dup
      // 184: aload 3
      // 185: aload 6
      // 187: invokespecial com/starpilot/starview/GxPage$$ExternalSyntheticLambda2.<init> (Lcom/starpilot/starview/GxPage;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 18a: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 18d: pop
      // 18e: return
   }

   private static final void download$lambda$29$lambda$28(GxPage var0, ObjectRef var1) {
      var0.toast((String)var1.element);
   }

   private static final Unit getArray$lambda$12(Function2 var0, String var1, Object var2) {
      JSONArray var3 = null;
      if (var1 == null) {
         if (!(var2 instanceof JSONArray)) {
            var1 = "bad reply";
         } else {
            var1 = null;
         }
      }

      if (var2 instanceof JSONArray) {
         var3 = (JSONArray)var2;
      }

      var0.invoke(var1, var3);
      return Unit.INSTANCE;
   }

   private static final Unit getJson$lambda$11(Function2 var0, String var1, Object var2) {
      JSONObject var3 = null;
      if (var1 == null) {
         if (!(var2 instanceof JSONObject)) {
            var1 = "bad reply";
         } else {
            var1 = null;
         }
      }

      if (var2 instanceof JSONObject) {
         var3 = (JSONObject)var2;
      }

      var0.invoke(var1, var3);
      return Unit.INSTANCE;
   }

   private static final Unit getText$lambda$13(Function2 var0, int var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var2, "t");
      var0.invoke(var3, var2);
      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static TextView header$default(GxPage var0, String var1, ViewGroup var2, int var3, Object var4) {
      if (var4 == null) {
         if ((var3 & 2) != 0) {
            var2 = (ViewGroup)var0.col;
         }

         return var0.header(var1, var2);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: header");
      }
   }

   // $VF: synthetic method
   public static ImageView image$default(GxPage var0, int var1, ViewGroup var2, int var3, Object var4) {
      if (var4 == null) {
         if ((var3 & 2) != 0) {
            var2 = (ViewGroup)var0.col;
         }

         return var0.image(var1, var2);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: image");
      }
   }

   // $VF: synthetic method
   public static void json$default(GxPage var0, String var1, String var2, Object var3, long var4, Function2 var6, int var7, Object var8) {
      if (var8 == null) {
         if ((var7 & 4) != 0) {
            var3 = null;
         }

         if ((var7 & 8) != 0) {
            var4 = 20L;
         }

         var0.json(var1, var2, var3, var4, var6);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: json");
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final Unit json$lambda$10(GxPage var0, Function2 var1, int var2, String var3, String var4) {
      Intrinsics.checkNotNullParameter(var3, "text");
      Object var5 = null;

      label89: {
         label88: {
            label87: {
               try {
                  Result.Companion var20 = Result.Companion;
                  if (StringsKt.isBlank(var3)) {
                     break label87;
                  }
               } catch (Throwable var17) {
                  Result.Companion var18 = Result.Companion;
                  var19 = KtCompat.resultOf(ResultKt.createFailure(var17));
                  break label89;
               }

               try {
                  JSONTokener var22 = new JSONTokener(var3);
                  var23 = var22.nextValue();
                  break label88;
               } catch (Throwable var16) {
                  Result.Companion var21 = Result.Companion;
                  var19 = KtCompat.resultOf(ResultKt.createFailure(var16));
                  break label89;
               }
            }

            var23 = null;
         }

         label80:
         try {
            var19 = KtCompat.resultOf(var23);
         } catch (Throwable var15) {
            Result.Companion var24 = Result.Companion;
            var19 = KtCompat.resultOf(ResultKt.createFailure(var15));
            break label80;
         }
      }

      if (KtCompat.isFailure(var19)) {
         var19 = var5;
      }

      var1.invoke(var4, var19);
      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void loadImage$default(GxPage var0, String var1, ImageView var2, Function1 var3, int var4, Object var5) {
      if (var5 == null) {
         if ((var4 & 4) != 0) {
            var3 = null;
         }

         var0.loadImage(var1, var2, var3);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: loadImage");
      }
   }

   private static final void loadImage$lambda$34(GxPage param0, String param1, String param2, ImageView param3, Function1 param4) {
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
      // 00: aconst_null
      // 01: astore 5
      // 03: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 06: astore 6
      // 08: getstatic com/starpilot/starview/GxPage.Companion Lcom/starpilot/starview/GxPage$Companion;
      // 0b: ldc2_w 15
      // 0e: invokevirtual com/starpilot/starview/GxPage$Companion.client (J)Lokhttp3/OkHttpClient;
      // 11: astore 7
      // 13: new okhttp3/Request$Builder
      // 16: astore 8
      // 18: aload 8
      // 1a: invokespecial okhttp3/Request$Builder.<init> ()V
      // 1d: new java/lang/StringBuilder
      // 20: astore 6
      // 22: aload 6
      // 24: invokespecial java/lang/StringBuilder.<init> ()V
      // 27: aload 7
      // 29: aload 8
      // 2b: aload 6
      // 2d: ldc_w "http://"
      // 30: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 33: aload 1
      // 34: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 37: ldc_w ":8082"
      // 3a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3d: aload 2
      // 3e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 41: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 44: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 47: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 4a: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 4d: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 52: checkcast java/io/Closeable
      // 55: astore 2
      // 56: aload 2
      // 57: checkcast okhttp3/Response
      // 5a: astore 1
      // 5b: aload 1
      // 5c: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 5f: ifne 67
      // 62: aconst_null
      // 63: astore 1
      // 64: goto 81
      // 67: aload 1
      // 68: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 6b: astore 1
      // 6c: aload 1
      // 6d: ifnull 62
      // 70: aload 1
      // 71: invokevirtual okhttp3/ResponseBody.bytes ()[B
      // 74: astore 1
      // 75: aload 1
      // 76: ifnull 62
      // 79: aload 1
      // 7a: bipush 0
      // 7b: aload 1
      // 7c: arraylength
      // 7d: invokestatic android/graphics/BitmapFactory.decodeByteArray ([BII)Landroid/graphics/Bitmap;
      // 80: astore 1
      // 81: aload 2
      // 82: aconst_null
      // 83: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 86: aload 1
      // 87: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 8a: astore 1
      // 8b: goto a9
      // 8e: astore 6
      // 90: aload 6
      // 92: athrow
      // 93: astore 1
      // 94: aload 2
      // 95: aload 6
      // 97: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 9a: aload 1
      // 9b: athrow
      // 9c: astore 2
      // 9d: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // a0: astore 1
      // a1: aload 2
      // a2: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // a5: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // a8: astore 1
      // a9: aload 1
      // aa: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // ad: ifeq b6
      // b0: aload 5
      // b2: astore 1
      // b3: goto b6
      // b6: aload 1
      // b7: checkcast android/graphics/Bitmap
      // ba: astore 1
      // bb: aload 0
      // bc: getfield com/starpilot/starview/GxPage.main Landroid/os/Handler;
      // bf: new com/starpilot/starview/GxPage$$ExternalSyntheticLambda17
      // c2: dup
      // c3: aload 1
      // c4: aload 3
      // c5: aload 4
      // c7: invokespecial com/starpilot/starview/GxPage$$ExternalSyntheticLambda17.<init> (Landroid/graphics/Bitmap;Landroid/widget/ImageView;Lkotlin/jvm/functions/Function1;)V
      // ca: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // cd: pop
      // ce: return
   }

   private static final void loadImage$lambda$34$lambda$33(Bitmap var0, ImageView var1, Function1 var2) {
      if (var0 != null) {
         var1.setImageBitmap(var0);
      }

      if (var2 != null) {
         var2.invoke(var0);
      }
   }

   // $VF: synthetic method
   public static TextView logBox$default(GxPage var0, int var1, ViewGroup var2, int var3, Object var4) {
      if (var4 == null) {
         if ((var3 & 1) != 0) {
            var1 = 420;
         }

         if ((var3 & 2) != 0) {
            var2 = (ViewGroup)var0.col;
         }

         return var0.logBox(var1, var2);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logBox");
      }
   }

   private static final void logToEnd$lambda$58$lambda$57(NestedScrollView var0) {
      var0.fullScroll(130);
   }

   // $VF: synthetic method
   public static TextView note$default(GxPage var0, String var1, ViewGroup var2, int var3, Object var4) {
      if (var4 == null) {
         if ((var3 & 2) != 0) {
            var2 = (ViewGroup)var0.col;
         }

         return var0.note(var1, var2);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: note");
      }
   }

   // $VF: synthetic method
   public static Pair paramToggle$default(GxPage var0, String var1, String var2, String var3, ViewGroup var4, int var5, Object var6) {
      if (var6 == null) {
         if ((var5 & 4) != 0) {
            var3 = null;
         }

         if ((var5 & 8) != 0) {
            var4 = (ViewGroup)var0.col;
         }

         return var0.paramToggle(var1, var2, var3, var4);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: paramToggle");
      }
   }

   private static final Unit paramToggle$lambda$44(GxPage var0, String var1, boolean var2) {
      putParam$default(var0, var1, var2, null, 4, null);
      return Unit.INSTANCE;
   }

   private static final void pick$lambda$74(Function1 var0, DialogInterface var1, int var2) {
      var0.invoke(var2);
   }

   private static final void pickMany$lambda$75(boolean[] var0, DialogInterface var1, int var2, boolean var3) {
      var0[var2] = var3;
   }

   private static final void pickMany$lambda$77(Function1 var0, boolean[] var1, DialogInterface var2, int var3) {
      Iterable var4 = ArraysKt.getIndices(var1);
      var var7 = new ArrayList();

      for (var var4_it : var4) {

         var4 = var4_it;
         if (var1[((Number)var4).intValue()]) {
            var7.add(var4);
         }
      }

      List var6 = var7;
      var0.invoke(CollectionsKt.toSet(var6));
   }

   // $VF: synthetic method
   public static ProgressBar progress$default(GxPage var0, ViewGroup var1, int var2, Object var3) {
      if (var3 == null) {
         if ((var2 & 1) != 0) {
            var1 = (ViewGroup)var0.col;
         }

         return var0.progress(var1);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: progress");
      }
   }

   // $VF: synthetic method
   public static void prompt$default(GxPage var0, String var1, String var2, String var3, int var4, String var5, Function1 var6, int var7, Object var8) {
      if (var8 == null) {
         if ((var7 & 2) != 0) {
            var2 = null;
         }

         if ((var7 & 4) != 0) {
            var3 = "";
         }

         if ((var7 & 8) != 0) {
            var4 = 1;
         }

         if ((var7 & 16) != 0) {
            var5 = null;
         }

         var0.prompt(var1, var2, var3, var4, var5, var6);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: prompt");
      }
   }

   private static final void prompt$lambda$73(Function1 var0, EditText var1, DialogInterface var2, int var3) {
      var0.invoke(var1.getText().toString());
   }

   // $VF: synthetic method
   public static void putParam$default(GxPage var0, String var1, Object var2, Function1 var3, int var4, Object var5) {
      if (var5 == null) {
         if ((var4 & 4) != 0) {
            var3 = null;
         }

         var0.putParam(var1, var2, var3);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: putParam");
      }
   }

   private static final Unit putParam$lambda$36(GxPage var0, Function1 var1, String var2) {
      if (var2 != null) {
         var0.toast(var2);
      }

      if (var1 != null) {
         var1.invoke(var2);
      }

      return Unit.INSTANCE;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final void readUri$lambda$24(GxPage var0, Uri var1, Function2 var2) {
      Object var5 = null;

      Object var219;
      label909: {
         try {
            var219 = Result.Companion;
            var219 = var0.act.getContentResolver().openInputStream(var1);
         } catch (Throwable var217) {
            var219 = Result.Companion;
            var219 = (Cursor)KtCompat.resultOf(ResultKt.createFailure(var217));
            break label909;
         }

         if (var219 != null) {
            InputStream var4;
            try {
               var4 = (InputStream & Closeable)var219;
            } catch (Throwable var216) {
               var219 = Result.Companion;
               var219 = (Cursor)KtCompat.resultOf(ResultKt.createFailure(var216));
               break label909;
            }

            try {
               var219 = ByteStreamsKt.readBytes(var4);
            } catch (Throwable var215) {
               var219 = var215;

               try {
                  throw var219;
               } finally {
                  try {
                     CloseableKt.closeFinally(var4, (Throwable)var219);
                  } catch (Throwable var212) {
                     var219 = Result.Companion;
                     var219 = (Cursor)KtCompat.resultOf(ResultKt.createFailure(var212));
                     break label909;
                  }
               }
            }

            try {
               CloseableKt.closeFinally(var4, null);
            } catch (Throwable var214) {
               var219 = Result.Companion;
               var219 = (Cursor)KtCompat.resultOf(ResultKt.createFailure(var214));
               break label909;
            }
         } else {
            var219 = null;
         }

         label891:
         try {
            var219 = (Cursor)KtCompat.resultOf(var219);
         } catch (Throwable var211) {
            var219 = Result.Companion;
            var219 = (Cursor)KtCompat.resultOf(ResultKt.createFailure(var211));
            break label891;
         }
      }

      Object var235 = var219;
      if (KtCompat.isFailure(var219)) {
         var235 = null;
      }

      byte[] var6 = (byte[])var235;

      label911: {
         try {
            var219 = Result.Companion;
            var219 = var0.act.getContentResolver().query(var1, new String[]{"_display_name"}, null, null, null);
         } catch (Throwable var209) {
            var235 = Result.Companion;
            var219 = KtCompat.resultOf(ResultKt.createFailure(var209));
            break label911;
         }

         if (var219 != null) {
            try {
               var235 = (Closeable & Cursor)var219;
            } catch (Throwable var208) {
               var235 = Result.Companion;
               var219 = KtCompat.resultOf(ResultKt.createFailure(var208));
               break label911;
            }

            label880: {
               try {
                  var219 = (Cursor)var235;
                  if (var219.moveToFirst()) {
                     var219 = var219.getString(0);
                     break label880;
                  }
               } catch (Throwable var210) {
                  Throwable var231 = var210;

                  try {
                     throw var231;
                  } finally {
                     try {
                        CloseableKt.closeFinally((Closeable)var235, var231);
                     } catch (Throwable var205) {
                        var235 = Result.Companion;
                        var219 = KtCompat.resultOf(ResultKt.createFailure(var205));
                        break label911;
                     }
                  }
               }

               var219 = null;
            }

            try {
               CloseableKt.closeFinally((Closeable)var235, null);
            } catch (Throwable var207) {
               var235 = Result.Companion;
               var219 = KtCompat.resultOf(ResultKt.createFailure(var207));
               break label911;
            }
         } else {
            var219 = null;
         }

         label866:
         try {
            var219 = KtCompat.resultOf(var219);
         } catch (Throwable var204) {
            var235 = Result.Companion;
            var219 = KtCompat.resultOf(ResultKt.createFailure(var204));
            break label866;
         }
      }

      if (KtCompat.isFailure(var219)) {
         var219 = var5;
      }

      var235 = (String)var219;
      String var234 = (String)var235;
      if (var235 == null) {
         String var218 = var1.getLastPathSegment();
         var234 = var218;
         if (var218 == null) {
            var234 = "file";
         }
      }

      var0.main.post(new GxPage$$ExternalSyntheticLambda24(var0, var2, var6, var234));
   }

   private static final void readUri$lambda$24$lambda$23(GxPage var0, Function2 var1, byte[] var2, String var3) {
      if (!var0.act.isDestroyed()) {
         var1.invoke(var2, var3);
      }
   }

   // $VF: synthetic method
   public static GxPage.Row row$default(GxPage var0, String var1, String var2, ViewGroup var3, int var4, int var5, Object var6) {
      if (var6 == null) {
         if ((var5 & 2) != 0) {
            var2 = null;
         }

         if ((var5 & 4) != 0) {
            var3 = (ViewGroup)var0.col;
         }

         if ((var5 & 8) != 0) {
            var4 = 0;
         }

         return var0.row(var1, var2, var3, var4);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: row");
      }
   }

   // $VF: synthetic method
   public static void send$default(GxPage var0, String var1, String var2, JSONObject var3, long var4, Function2 var6, int var7, Object var8) {
      if (var8 == null) {
         if ((var7 & 4) != 0) {
            var3 = null;
         }

         if ((var7 & 8) != 0) {
            var4 = 30L;
         }

         if ((var7 & 16) != 0) {
            var6 = new GxPage$$ExternalSyntheticLambda18();
         }

         var0.send(var1, var2, var3, var4, (Function2<? super String, ? super JSONObject, Unit>)var6);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: send");
      }
   }

   private static final Unit send$lambda$14(String var0, JSONObject var1) {
      return Unit.INSTANCE;
   }

   private static final Unit send$lambda$15(Function2 var0, String var1, Object var2) {
      if (var2 instanceof JSONObject) {
         var2 = var2;
      } else {
         var2 = null;
      }

      var0.invoke(var1, var2);
      return Unit.INSTANCE;
   }

   private final void startPoll(long var1, Function0<Unit> var3) {
      Runnable var4 = new Runnable(this, var3, var1) {
         final Function0<Unit> $block;
         final long $ms;
         final GxPage this$0;

         {
            this.this$0 = var1;
            this.$block = var2;
            this.$ms = var3x;
         }

         @Override
         public void run() {
            if (this.this$0.getShown()) {
               try {
                  this.$block.invoke();
               } catch (Exception var2) {
                  Log.e("StarView/Gx", "poll", var2);
               }

               this.this$0.getMain().postDelayed(this, this.$ms);
            }
         }
      };
      this.pollRunners.add(var4);
      this.main.post(var4);
   }

   private final void startPolls() {
      this.stopPolls();
      Iterator var1 = this.polls.iterator();
      Intrinsics.checkNotNullExpressionValue(var1, "iterator(...)");

      while (var1.hasNext()) {
         Object var2 = var1.next();
         Intrinsics.checkNotNullExpressionValue(var2, "next(...)");
         var2 = (Pair)var2;
         this.startPoll(((Number)var2.component1()).longValue(), (Function0<Unit>)var2.component2());
      }
   }

   // $VF: synthetic method
   public static Function1 stepper$default(
      GxPage var0,
      String var1,
      String var2,
      double var3,
      double var5,
      double var7,
      double var9,
      Function1 var11,
      ViewGroup var12,
      Function1 var13,
      int var14,
      Object var15
   ) {
      if (var15 == null) {
         if ((var14 & 64) != 0) {
            var11 = new GxPage$$ExternalSyntheticLambda3();
         }

         if ((var14 & 128) != 0) {
            var12 = (ViewGroup)var0.col;
         }

         return var0.stepper(var1, var2, var3, var5, var7, var9, (Function1<? super Double, String>)var11, var12, var13);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: stepper");
      }
   }

   private static final String stepper$lambda$46(double var0) {
      StringCompanionObject var2 = StringCompanionObject.INSTANCE;
      String var3 = String.format(Locale.US, "%.2f", Arrays.copyOf(new Object[]{var0}, 1));
      Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      return var3;
   }

   private static final Unit stepper$lambda$48(DoubleRef var0, double var1, double var3, double var5, Function1 var7, TextView var8, Function1 var9) {
      stepper$write(var0, var3, var5, var1, var7, var8, var9, var0.element - var1);
      return Unit.INSTANCE;
   }

   private static final Unit stepper$lambda$49(DoubleRef var0, double var1, double var3, double var5, Function1 var7, TextView var8, Function1 var9) {
      stepper$write(var0, var3, var5, var1, var7, var8, var9, var0.element + var1);
      return Unit.INSTANCE;
   }

   private static final Unit stepper$lambda$50(DoubleRef var0, TextView var1, Function1 var2, double var3) {
      var0.element = var3;
      stepper$paint(var1, var2, var0);
      return Unit.INSTANCE;
   }

   private static final void stepper$paint(TextView var0, Function1<? super Double, String> var1, DoubleRef var2) {
      var0.setText((CharSequence)var1.invoke(var2.element));
   }

   private static final void stepper$write(
      DoubleRef var0,
      double var1,
      double var3,
      double var5,
      Function1<? super Double, Unit> var7,
      TextView var8,
      Function1<? super Double, String> var9,
      double var10
   ) {
      var0.element = RangesKt.coerceIn(Math.round(RangesKt.coerceIn(var10, var1, var3) / var5) * var5, var1, var3);
      stepper$paint(var8, var9, var0);
      var7.invoke(var0.element);
   }

   private final void stopPolls() {
      Iterator var1 = this.pollRunners.iterator();
      Intrinsics.checkNotNullExpressionValue(var1, "iterator(...)");

      while (var1.hasNext()) {
         Object var2 = var1.next();
         Intrinsics.checkNotNullExpressionValue(var2, "next(...)");
         var2 = (Runnable)var2;
         this.main.removeCallbacks((Runnable)var2);
      }

      this.pollRunners.clear();
   }

   // $VF: synthetic method
   public static String str$default(GxPage var0, JSONObject var1, String var2, String var3, int var4, Object var5) {
      if (var5 == null) {
         if ((var4 & 2) != 0) {
            var3 = "";
         }

         return var0.str(var1, var2, var3);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: str");
      }
   }

   // $VF: synthetic method
   public static Function1 tabs$default(GxPage var0, List var1, int var2, Function1 var3, Function2 var4, int var5, Object var6) {
      if (var6 == null) {
         if ((var5 & 2) != 0) {
            var2 = 0;
         }

         if ((var5 & 4) != 0) {
            var3 = null;
         }

         return var0.tabs(var1, var2, var3, var4);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: tabs");
      }
   }

   private static final Unit tabs$lambda$61(ObjectRef var0, int var1) {
      Function1 var2;
      if (var0.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("selectRef");
         var2 = null;
      } else {
         var2 = (Function1)var0.element;
      }

      var2.invoke(var1);
      return Unit.INSTANCE;
   }

   private static final Unit tabs$lambda$66(ObjectRef var0, HashMap var1, Function1 var2, GxPage var3, LinearLayout var4, Function2 var5, int var6) {
      tabs$select(var0, var1, var2, var3, var4, var5, var6);
      return Unit.INSTANCE;
   }

   private static final Unit tabs$lambda$67(ObjectRef var0, HashMap var1, Function1 var2, GxPage var3, LinearLayout var4, Function2 var5, int var6) {
      tabs$select(var0, var1, var2, var3, var4, var5, var6);
      return Unit.INSTANCE;
   }

   private static final void tabs$select(
      ObjectRef<Function1<Integer, Unit>> var0,
      HashMap<Integer, LinearLayout> var1,
      Function1<? super Integer, Unit> var2,
      GxPage var3,
      LinearLayout var4,
      Function2<? super Integer, ? super LinearLayout, Unit> var5,
      int var6
   ) {
      Function1 var9;
      if (var0.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paintTabs");
         var9 = null;
      } else {
         var9 = (Function1)var0.element;
      }

      var9.invoke(var6);
      Iterator var10 = var1.entrySet().iterator();

      while (var10.hasNext()) {
         ((LinearLayout)((Entry)var10.next()).getValue()).setVisibility(8);
      }

      Map var12 = var1;
      Integer var7 = var6;
      Object var11 = var12.get(var7);
      if (var11 == null) {
         var11 = new LinearLayout((Context)var3.act);
         var11.setOrientation(1);
         var4.addView((View)var11, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));

         try {
            var5.invoke(var6, var11);
         } catch (Exception var8) {
            Log.e("StarView/Gx", "tab " + var6, var8);
            var11.addView((View)var3.empty("This tab failed to build:\n" + var8));
         }

         var12.put(var7, var11);
      }

      ((LinearLayout)var11).setVisibility(0);
      if (var2 != null) {
         var2.invoke(var6);
      }
   }

   // $VF: synthetic method
   public static TextView text$default(GxPage var0, String var1, ViewGroup var2, float var3, int var4, boolean var5, int var6, Object var7) {
      if (var7 == null) {
         if ((var6 & 4) != 0) {
            var3 = 15.0F;
         }

         if ((var6 & 8) != 0) {
            var4 = CarUi.Companion.getDIM();
         }

         if ((var6 & 16) != 0) {
            var5 = false;
         }

         return var0.text(var1, var2, var3, var4, var5);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: text");
      }
   }

   // $VF: synthetic method
   public static Pair toggle$default(GxPage var0, String var1, String var2, boolean var3, ViewGroup var4, Function1 var5, int var6, Object var7) {
      if (var7 == null) {
         if ((var6 & 8) != 0) {
            var4 = (ViewGroup)var0.col;
         }

         return var0.toggle(var1, var2, var3, var4, var5);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toggle");
      }
   }

   // $VF: synthetic method
   public static void upload$default(GxPage var0, String var1, List var2, Map var3, Function2 var4, int var5, Object var6) {
      if (var6 == null) {
         if ((var5 & 4) != 0) {
            var3 = MapsKt.emptyMap();
         }

         var0.upload(var1, var2, var3, var4);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: upload");
      }
   }

   private static final Unit upload$lambda$18(Function2 var0, String var1, Object var2) {
      if (var2 instanceof JSONObject) {
         var2 = var2;
      } else {
         var2 = null;
      }

      var0.invoke(var1, var2);
      return Unit.INSTANCE;
   }

   public final String ago(double var1) {
      long var3 = (long)(System.currentTimeMillis() / 1000.0 - var1);
      String var5;
      if (var3 < 60L) {
         var5 = "just now";
      } else {
         String var6;
         StringBuilder var10;
         if (var3 < 3600L) {
            var3 /= 60;
            var10 = new StringBuilder().append(var3);
            var6 = " min ago";
         } else if (var3 < 86400L) {
            var3 /= 3600;
            var10 = new StringBuilder().append(var3);
            var6 = " h ago";
         } else {
            var3 /= 86400;
            var10 = new StringBuilder().append(var3);
            var6 = " d ago";
         }

         var5 = var10.append(var6).toString();
      }

      return var5;
   }

   public final void alert(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "title");
      Intrinsics.checkNotNullParameter(var2, "msg");
      new Builder((Context)this.act).setTitle(var1).setMessage(var2).setPositiveButton("OK", null).show();
   }

   public final LinearLayout box(ViewGroup var1) {
      Intrinsics.checkNotNullParameter(var1, "into");
      LinearLayout var2 = new LinearLayout((Context)this.act);
      var2.setOrientation(1);
      var1.addView((View)var2, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
      return var2;
   }

   public abstract void build();

   public final LinearLayout buttons(Pair<String, ? extends Function0<Unit>>[] var1, ViewGroup var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "items");
      Intrinsics.checkNotNullParameter(var2, "into");
      LinearLayout var7 = new LinearLayout((Context)this.act);
      var7.setOrientation(0);
      var7.setPadding(0, this.ui.dp(4), 0, this.ui.dp(8));
      int var5 = var1.length;

      for (int var4 = 0; var4 < var5; var4++) {
         Pair var8 = var1[var4];
         CarUi var9 = this.ui;
         String var10 = (String)var8.getFirst();
         boolean var6;
         if (var3 && var4 == 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         View var14 = (View)var9.button(var10, var6, (Function0<Unit>)var8.getSecond());
         android.widget.LinearLayout.LayoutParams var13 = new android.widget.LinearLayout.LayoutParams(-2, -2);
         var13.rightMargin = this.ui.dp(10);
         Unit var12 = Unit.INSTANCE;
         var7.addView(var14, (LayoutParams)var13);
      }

      HorizontalScrollView var11 = new HorizontalScrollView((Context)this.act);
      var11.setHorizontalScrollBarEnabled(false);
      var11.addView((View)var7);
      var2.addView((View)var11);
      return var7;
   }

   public final String bytes(double var1) {
      String var6;
      if (var1 >= 1.0E9) {
         StringCompanionObject var5 = StringCompanionObject.INSTANCE;
         var6 = String.format(Locale.US, "%.1f GB", Arrays.copyOf(new Object[]{var1 / 1.0E9}, 1));
      } else if (var1 >= 1000000.0) {
         StringCompanionObject var7 = StringCompanionObject.INSTANCE;
         var6 = String.format(Locale.US, "%.0f MB", Arrays.copyOf(new Object[]{var1 / 1000000.0}, 1));
      } else {
         if (!(var1 >= 1000.0)) {
            long var3 = (long)var1;
            var6 = var3 + " B";
            return var6;
         }

         StringCompanionObject var8 = StringCompanionObject.INSTANCE;
         var6 = String.format(Locale.US, "%.0f KB", Arrays.copyOf(new Object[]{var1 / 1000.0}, 1));
      }

      Intrinsics.checkNotNullExpressionValue(var6, "format(...)");
      return var6;
   }

   public final void call(String var1, String var2, Object var3, long var4, Function3<? super Integer, ? super String, ? super String, Unit> var6) {
      Intrinsics.checkNotNullParameter(var1, "method");
      Intrinsics.checkNotNullParameter(var2, "path");
      Intrinsics.checkNotNullParameter(var6, "then");
      String var7 = this.getHost();
      if (var7 == null) {
         GxPage var8 = this;
         var6.invoke(-1, "", "Not connected");
      } else {
         new Thread(new GxPage$$ExternalSyntheticLambda5(var3, var1, var7, var2, var4, this, var6)).start();
      }
   }

   public final LinearLayout card(ViewGroup var1) {
      Intrinsics.checkNotNullParameter(var1, "into");
      LinearLayout var2 = new LinearLayout((Context)this.act);
      var2.setOrientation(1);
      var2.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 0, null, 6, null));
      var2.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
      var1.addView((View)var2, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
      return var2;
   }

   public final Function1<Integer, Unit> choice(String var1, String var2, List<String> var3, int var4, ViewGroup var5, Function1<? super Integer, Unit> var6) {
      Intrinsics.checkNotNullParameter(var1, "title");
      Intrinsics.checkNotNullParameter(var3, "labels");
      Intrinsics.checkNotNullParameter(var5, "into");
      Intrinsics.checkNotNullParameter(var6, "onPick");
      LinearLayout var8 = this.card(var5);
      text$default(this, var1, (ViewGroup)var8, 21.0F, CarUi.Companion.getTEXT(), false, 16, null);
      CharSequence var9 = var2;
      boolean var7;
      if (var9 != null && !StringsKt.isBlank(var9)) {
         var7 = false;
      } else {
         var7 = true;
      }

      if (!var7) {
         text$default(this, var2, (ViewGroup)var8, 0.0F, 0, false, 28, null);
      }

      ObjectRef var16 = new ObjectRef();
      Pair var14 = this.ui.chips(var3, new GxPage$$ExternalSyntheticLambda7(var16, var6));
      LinearLayout var10 = (LinearLayout)var14.component1();
      var16.element = (T)((Function1)var14.component2());
      var10.setPadding(0, this.ui.dp(10), 0, 0);
      var8.addView((View)var10);
      Function1 var11 = var16.element;
      Object var15 = null;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var11 = null;
      } else {
         var11 = (Function1)var16.element;
      }

      var11.invoke(var4);
      if (var16.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var11 = (Function1)var15;
      } else {
         var11 = (Function1)var16.element;
      }

      return var11;
   }

   public final void confirm(String var1, String var2, String var3, Function0<Unit> var4) {
      Intrinsics.checkNotNullParameter(var1, "title");
      Intrinsics.checkNotNullParameter(var3, "yes");
      Intrinsics.checkNotNullParameter(var4, "then");
      Builder var5 = new Builder((Context)this.act).setTitle(var1);
      if (var2 != null) {
         var5.setMessage(var2);
      }

      var5.setPositiveButton(var3, new GxPage$$ExternalSyntheticLambda19(var4)).setNegativeButton("Cancel", null).show();
   }

   public final void doAction(String var1, String var2, JSONObject var3, String var4, Function1<? super JSONObject, Unit> var5) {
      Intrinsics.checkNotNullParameter(var1, "method");
      Intrinsics.checkNotNullParameter(var2, "path");
      send$default(this, var1, var2, var3, 0L, new GxPage$$ExternalSyntheticLambda9(this, var4, var5), 8, null);
   }

   public final void download(String var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "fileName");
      Intrinsics.checkNotNullParameter(var3, "mime");
      String var4 = this.getHost();
      if (var4 != null) {
         this.toast("Downloading " + var2 + "…");
         new Thread(new GxPage$$ExternalSyntheticLambda4(var4, var1, var2, this, var3)).start();
      }
   }

   public final TextView empty(String var1) {
      Intrinsics.checkNotNullParameter(var1, "text");
      TextView var2 = new TextView((Context)this.act);
      var2.setText(var1);
      var2.setTextColor(CarUi.Companion.getDIM());
      var2.setTextSize(19.0F);
      var2.setGravity(17);
      var2.setPadding(this.ui.dp(30), this.ui.dp(40), this.ui.dp(30), this.ui.dp(40));
      return var2;
   }

   public final String enc(String var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      var1 = URLEncoder.encode(var1, "UTF-8");
      Intrinsics.checkNotNullExpressionValue(var1, "encode(...)");
      return StringsKt.replace$default(var1, "+", "%20", false, 4, null);
   }

   public final void every(long var1, Function0<Unit> var3) {
      Intrinsics.checkNotNullParameter(var3, "block");
      this.polls.add(TuplesKt.to(var1, var3));
      if (this.shown) {
         this.startPoll(var1, var3);
      }
   }

   public final Activity getAct() {
      return this.act;
   }

   public final GalaxyApi getApi() {
      return this.gx.getGxApi();
   }

   public final void getArray(String var1, Function2<? super String, ? super JSONArray, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "then");
      json$default(this, "GET", var1, null, 0L, new GxPage$$ExternalSyntheticLambda20(var2), 12, null);
   }

   public final LinearLayout getCol() {
      return this.col;
   }

   public final GxHost getGx() {
      return this.gx;
   }

   public final String getHost() {
      return this.gx.getGxHost();
   }

   public final void getJson(String var1, Function2<? super String, ? super JSONObject, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "then");
      json$default(this, "GET", var1, null, 0L, new GxPage$$ExternalSyntheticLambda25(var2), 12, null);
   }

   public final Handler getMain() {
      return this.main;
   }

   public boolean getNeedsOffroad() {
      return this.needsOffroad;
   }

   public final NestedScrollView getScroll() {
      return this.scroll;
   }

   public final boolean getShown() {
      return this.shown;
   }

   public final void getText(String var1, Function2<? super String, ? super String, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "then");
      call$default(this, "GET", var1, null, 0L, new GxPage$$ExternalSyntheticLambda23(var2), 12, null);
   }

   public final CarUi getUi() {
      return this.ui;
   }

   public final TextView header(String var1, ViewGroup var2) {
      Intrinsics.checkNotNullParameter(var1, "text");
      Intrinsics.checkNotNullParameter(var2, "into");
      TextView var3 = this.ui.header(var1);
      var2.addView((View)var3);
      return var3;
   }

   public final void hide() {
      if (this.shown) {
         this.shown = false;
         this.stopPolls();

         try {
            this.onHide();
         } catch (Exception var2) {
         }
      }
   }

   public final ImageView image(int var1, ViewGroup var2) {
      Intrinsics.checkNotNullParameter(var2, "into");
      ImageView var6 = new ImageView((Context)this.act);
      var6.setScaleType(ScaleType.FIT_CENTER);
      var6.setBackground((Drawable)CarUi.rounded$default(this.ui, Color.rgb(6, 8, 11), 12, null, 4, null));
      View var4 = (View)var6;
      android.widget.LinearLayout.LayoutParams var3 = new android.widget.LinearLayout.LayoutParams(-1, this.ui.dp(var1));
      var3.bottomMargin = this.ui.dp(8);
      Unit var5 = Unit.INSTANCE;
      var2.addView(var4, (LayoutParams)var3);
      return var6;
   }

   public final void json(String var1, String var2, Object var3, long var4, Function2<? super String, Object, Unit> var6) {
      Intrinsics.checkNotNullParameter(var1, "method");
      Intrinsics.checkNotNullParameter(var2, "path");
      Intrinsics.checkNotNullParameter(var6, "then");
      this.call(var1, var2, var3, var4, new GxPage$$ExternalSyntheticLambda26(this, var6));
   }

   public final void loadImage(String var1, ImageView var2, Function1<? super Bitmap, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "into");
      String var4 = this.getHost();
      if (var4 != null) {
         new Thread(new GxPage$$ExternalSyntheticLambda27(this, var4, var1, var2, var3)).start();
      }
   }

   public final TextView logBox(int var1, ViewGroup var2) {
      Intrinsics.checkNotNullParameter(var2, "into");
      TextView var3 = new TextView((Context)this.act);
      var3.setTypeface(Typeface.MONOSPACE);
      var3.setTextSize(13.0F);
      var3.setTextColor(Color.rgb(210, 220, 230));
      var3.setTextIsSelectable(true);
      var3.setPadding(this.ui.dp(12), this.ui.dp(10), this.ui.dp(12), this.ui.dp(10));
      HorizontalScrollView var5 = new HorizontalScrollView((Context)this.act);
      var5.addView((View)var3);
      NestedScrollView var4 = new NestedScrollView((Context)this.act);
      var4.addView((View)var5);
      var4.setBackground((Drawable)this.ui.rounded(Color.rgb(6, 8, 11), 12, Color.rgb(40, 44, 52)));
      var4.setNestedScrollingEnabled(true);
      View var7 = (View)var4;
      android.widget.LinearLayout.LayoutParams var6 = new android.widget.LinearLayout.LayoutParams(-1, this.ui.dp(var1));
      var6.bottomMargin = this.ui.dp(8);
      Unit var8 = Unit.INSTANCE;
      var2.addView(var7, (LayoutParams)var6);
      return var3;
   }

   public final void logToEnd(TextView var1) {
      Intrinsics.checkNotNullParameter(var1, "tv");
      ViewParent var3 = var1.getParent();
      NestedScrollView var2 = null;
      ViewParent var4;
      if (var3 != null) {
         var4 = var3.getParent();
      } else {
         var4 = null;
      }

      if (var4 instanceof NestedScrollView) {
         var2 = (NestedScrollView)var4;
      }

      if (var2 != null) {
         var2.post(new GxPage$$ExternalSyntheticLambda14(var2));
      }
   }

   public final TextView note(String var1, ViewGroup var2) {
      Intrinsics.checkNotNullParameter(var1, "text");
      Intrinsics.checkNotNullParameter(var2, "into");
      TextView var3 = this.ui.note(var1);
      var2.addView((View)var3);
      return var3;
   }

   public final List<JSONObject> objects(JSONArray var1) {
      Intrinsics.checkNotNullParameter(var1, "<this>");
      Iterable var3 = RangesKt.until(0, var1.length());
      var var2 = new ArrayList();
      Iterator var4 = var3.iterator();

      while (var4.hasNext()) {
         JSONObject var5 = var1.optJSONObject(((IntIterator)var4).nextInt());
         if (var5 != null) {
            var2.add(var5);
         }
      }

      return var2;
   }

   public void onHide() {
   }

   public void onShow() {
   }

   public void onTick() {
   }

   public final boolean pBool(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      GalaxyApi var3 = this.getApi();
      boolean var2;
      if (var3 != null) {
         var2 = var3.bool(var1);
      } else {
         var2 = false;
      }

      return var2;
   }

   public final Double pNum(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      GalaxyApi var2 = this.getApi();
      Double var3;
      if (var2 != null) {
         var3 = var2.double_(var1);
      } else {
         var3 = null;
      }

      return var3;
   }

   public final String pStr(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      GalaxyApi var2 = this.getApi();
      if (var2 != null) {
         String var4 = var2.string(var1);
         if (var4 != null) {
            return var4;
         }
      }

      return "";
   }

   public final Pair<GxPage.Row, Switch> paramToggle(String var1, String var2, String var3, ViewGroup var4) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Intrinsics.checkNotNullParameter(var2, "title");
      Intrinsics.checkNotNullParameter(var4, "into");
      return this.toggle(var2, var3, this.pBool(var1), var4, new GxPage$$ExternalSyntheticLambda11(this, var1));
   }

   public final void pick(String var1, List<String> var2, Function1<? super Integer, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "title");
      Intrinsics.checkNotNullParameter(var2, "labels");
      Intrinsics.checkNotNullParameter(var3, "then");
      new Builder((Context)this.act)
         .setTitle(var1)
         .setItems(var2.toArray(new String[0]), new GxPage$$ExternalSyntheticLambda21(var3))
         .setNegativeButton("Cancel", null)
         .show();
   }

   public final void pickMany(String var1, List<String> var2, Set<Integer> var3, Function1<? super Set<Integer>, Unit> var4) {
      Intrinsics.checkNotNullParameter(var1, "title");
      Intrinsics.checkNotNullParameter(var2, "labels");
      Intrinsics.checkNotNullParameter(var3, "checked");
      Intrinsics.checkNotNullParameter(var4, "then");
      int var6 = var2.size();
      boolean[] var7 = new boolean[var6];

      for (int var5 = 0; var5 < var6; var5++) {
         var7[var5] = var3.contains(var5);
      }

      new Builder((Context)this.act)
         .setTitle(var1)
         .setMultiChoiceItems(var2.toArray(new String[0]), var7, new GxPage$$ExternalSyntheticLambda31(var7))
         .setPositiveButton("OK", new GxPage$$ExternalSyntheticLambda1(var4, var7))
         .setNegativeButton("Cancel", null)
         .show();
   }

   public final ProgressBar progress(ViewGroup var1) {
      Intrinsics.checkNotNullParameter(var1, "into");
      ProgressBar var5 = new ProgressBar((Context)this.act, null, 16842872);
      var5.setMax(100);
      var5.setProgressTintList(ColorStateList.valueOf(CarUi.Companion.getACCENT()));
      View var4 = (View)var5;
      android.widget.LinearLayout.LayoutParams var2 = new android.widget.LinearLayout.LayoutParams(-1, this.ui.dp(14));
      var2.topMargin = this.ui.dp(6);
      var2.bottomMargin = this.ui.dp(6);
      Unit var3 = Unit.INSTANCE;
      var1.addView(var4, (LayoutParams)var2);
      return var5;
   }

   public final void prompt(String var1, String var2, String var3, int var4, String var5, Function1<? super String, Unit> var6) {
      Intrinsics.checkNotNullParameter(var1, "title");
      Intrinsics.checkNotNullParameter(var3, "initial");
      Intrinsics.checkNotNullParameter(var6, "then");
      EditText var7 = new EditText((Context)this.act);
      var7.setText(var3);
      var7.setHint(var2);
      var7.setInputType(var4);
      var7.setTextSize(20.0F);
      var7.setSelection(var7.getText().length());
      FrameLayout var9 = new FrameLayout((Context)this.act);
      var9.setPadding(this.ui.dp(22), this.ui.dp(8), this.ui.dp(22), 0);
      var9.addView((View)var7);
      Builder var8 = new Builder((Context)this.act).setTitle(var1);
      if (var5 != null) {
         var8.setMessage(var5);
      }

      var8.setView((View)var9).setPositiveButton("OK", new GxPage$$ExternalSyntheticLambda15(var6, var7)).setNegativeButton("Cancel", null).show();
   }

   public final void putParam(String var1, Object var2, Function1<? super String, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "key");
      GalaxyApi var4 = this.getApi();
      if (var4 == null) {
         this.toast("Not connected");
      } else {
         GalaxyApi.put$default(var4, var1, var2, null, new GxPage$$ExternalSyntheticLambda22(this, var3), 4, null);
      }
   }

   public final void readUri(Uri var1, Function2<byte[], ? super String, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "uri");
      Intrinsics.checkNotNullParameter(var2, "then");
      new Thread(new GxPage$$ExternalSyntheticLambda8(this, var1, var2)).start();
   }

   public final void rebuild() {
      this.col.removeAllViews();
      this.polls.clear();
      this.stopPolls();
      this.build();
      if (this.shown) {
         this.startPolls();
      }
   }

   public final GxPage.Row row(String var1, String var2, ViewGroup var3, int var4) {
      Intrinsics.checkNotNullParameter(var1, "title");
      Intrinsics.checkNotNullParameter(var3, "into");
      GxPage.Row var5 = new GxPage.Row(this, var1, var2);
      var3.addView((View)var5.getView(), (LayoutParams)CarUi.lp$default(this.ui, var4, 0, 2, null));
      return var5;
   }

   public final void send(String var1, String var2, JSONObject var3, long var4, Function2<? super String, ? super JSONObject, Unit> var6) {
      Intrinsics.checkNotNullParameter(var1, "method");
      Intrinsics.checkNotNullParameter(var2, "path");
      Intrinsics.checkNotNullParameter(var6, "then");
      this.json(var1, var2, var3, var4, new GxPage$$ExternalSyntheticLambda6(var6));
   }

   public final void setProgress(ProgressBar var1, double var2) {
      Intrinsics.checkNotNullParameter(var1, "p");
      if (var2 < 0.0) {
         var1.setIndeterminate(true);
      } else {
         var1.setIndeterminate(false);
         var1.setProgress(RangesKt.coerceIn((int)var2, 0, 100));
      }
   }

   public final void show() {
      if (this.getHost() == null) {
         this.col.removeAllViews();
         this.col.addView((View)this.empty("Not connected to the comma. Connect first, then come back."));
         this.built = false;
      } else {
         if (!this.built) {
            this.built = true;
            this.col.removeAllViews();

            try {
               this.build();
            } catch (Exception var4) {
               String var2 = this.getClass().getSimpleName();
               Log.e("StarView/Gx", "build " + var2, var4);
               this.col.addView((View)this.empty("This page failed to build:\n" + var4));
            }
         }

         this.shown = true;
         this.startPolls();

         try {
            this.onShow();
         } catch (Exception var3) {
            Log.e("StarView/Gx", "show", var3);
         }
      }
   }

   public final Function1<Double, Unit> stepper(
      String var1,
      String var2,
      double var3,
      double var5,
      double var7,
      double var9,
      Function1<? super Double, String> var11,
      ViewGroup var12,
      Function1<? super Double, Unit> var13
   ) {
      Intrinsics.checkNotNullParameter(var1, "title");
      Intrinsics.checkNotNullParameter(var11, "fmt");
      Intrinsics.checkNotNullParameter(var12, "into");
      Intrinsics.checkNotNullParameter(var13, "onChange");
      GxPage.Row var14 = row$default(this, var1, var2, var12, 0, 8, null);
      DoubleRef var16 = new DoubleRef();
      var16.element = var3;
      TextView var15 = new TextView((Context)this.act);
      var15.setTextSize(22.0F);
      var15.setTextColor(CarUi.Companion.getTEXT());
      var15.setTypeface(Typeface.DEFAULT_BOLD);
      var15.setMinWidth(this.ui.dp(90));
      var15.setGravity(17);
      var14.add((View)this.ui.stepper("−", new GxPage$$ExternalSyntheticLambda10(var16, var9, var5, var7, var13, var15, var11)));
      var14.add((View)var15);
      var14.add((View)this.ui.stepper("+", new GxPage$$ExternalSyntheticLambda12(var16, var9, var5, var7, var13, var15, var11)));
      stepper$paint(var15, var11, var16);
      return new GxPage$$ExternalSyntheticLambda13(var16, var15, var11);
   }

   public final String str(JSONObject var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var1, "<this>");
      Intrinsics.checkNotNullParameter(var2, "k");
      Intrinsics.checkNotNullParameter(var3, "def");
      if (var1.has(var2) && !var1.isNull(var2)) {
         var3 = var1.optString(var2, var3);
         Intrinsics.checkNotNullExpressionValue(var3, "optString(...)");
      }

      return var3;
   }

   public final List<String> strings(JSONArray var1) {
      Intrinsics.checkNotNullParameter(var1, "<this>");
      Iterable var3 = RangesKt.until(0, var1.length());
      var var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
      Iterator var4 = var3.iterator();

      while (var4.hasNext()) {
         var2.add(var1.optString(((IntIterator)var4).nextInt()));
      }

      return var2;
   }

   public final Function1<Integer, Unit> tabs(
      List<String> var1, int var2, Function1<? super Integer, Unit> var3, Function2<? super Integer, ? super LinearLayout, Unit> var4
   ) {
      Intrinsics.checkNotNullParameter(var1, "labels");
      Intrinsics.checkNotNullParameter(var4, "build");
      ObjectRef var5 = new ObjectRef();
      ObjectRef var6 = new ObjectRef();
      Pair var8 = this.ui.browserTabs(var1, 1, new GxPage$$ExternalSyntheticLambda28(var6));
      View var7 = (View)var8.component1();
      var5.element = (T)((Function1)var8.component2());
      this.col.addView(var7, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
      LinearLayout var10 = new LinearLayout((Context)this.act);
      var10.setOrientation(1);
      var10.setBackground((Drawable)this.ui.corners(CarUi.Companion.getSECTION(), 0, 0, 14, 14));
      var10.setPadding(this.ui.dp(14), this.ui.dp(14), this.ui.dp(14), this.ui.dp(6));
      this.col.addView((View)var10, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
      HashMap var9 = new HashMap();
      var6.element = (T)(new GxPage$$ExternalSyntheticLambda29(var5, var9, var3, this, var10, var4));
      tabs$select(var5, var9, var3, this, var10, var4, RangesKt.coerceIn(var2, 0, var1.size() - 1));
      return new GxPage$$ExternalSyntheticLambda30(var5, var9, var3, this, var10, var4);
   }

   public final TextView text(String var1, ViewGroup var2, float var3, int var4, boolean var5) {
      Intrinsics.checkNotNullParameter(var1, "s");
      Intrinsics.checkNotNullParameter(var2, "into");
      TextView var6 = new TextView((Context)this.act);
      var6.setText(var1);
      var6.setTextSize(var3);
      var6.setTextColor(var4);
      if (var5) {
         var6.setTypeface(Typeface.DEFAULT_BOLD);
      }

      var6.setPadding(0, this.ui.dp(2), 0, this.ui.dp(2));
      var2.addView((View)var6);
      return var6;
   }

   public final void tick() {
      if (this.shown) {
         try {
            this.onTick();
         } catch (Exception var2) {
            Log.e("StarView/Gx", "tick", var2);
         }
      }
   }

   public final void toast(String var1) {
      Intrinsics.checkNotNullParameter(var1, "msg");
      Toast.makeText((Context)this.act, var1, 0).show();
   }

   public final Pair<GxPage.Row, Switch> toggle(String var1, String var2, boolean var3, ViewGroup var4, Function1<? super Boolean, Unit> var5) {
      Intrinsics.checkNotNullParameter(var1, "title");
      Intrinsics.checkNotNullParameter(var4, "into");
      Intrinsics.checkNotNullParameter(var5, "onChange");
      GxPage.Row var6 = row$default(this, var1, var2, var4, 0, 8, null);
      return TuplesKt.to(var6, var6.switch_(var3, var5));
   }

   public final void upload(
      String var1, List<Triple<String, String, Pair<byte[], String>>> var2, Map<String, String> var3, Function2<? super String, ? super JSONObject, Unit> var4
   ) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "files");
      Intrinsics.checkNotNullParameter(var3, "fields");
      Intrinsics.checkNotNullParameter(var4, "then");
      okhttp3.MultipartBody.Builder var5 = new okhttp3.MultipartBody.Builder(null, 1, null).setType(MultipartBody.FORM);

      for (Entry var9 : var3.entrySet()) {
         var5.addFormDataPart((String)var9.getKey(), (String)var9.getValue());
      }

      for (Triple var7 : var2) {
         String var8 = (String)var7.component1();
         String var10 = (String)var7.component2();
         Pair var12 = (Pair)var7.component3();
         var5.addFormDataPart(
            var8,
            var10,
            RequestBody.Companion.create$default(
               RequestBody.Companion, (byte[])var12.getFirst(), MediaType.Companion.get((String)var12.getSecond()), 0, 0, 6, null
            )
         );
      }

      this.json("POST", var1, var5.build(), 120L, new GxPage$$ExternalSyntheticLambda0(var4));
   }

   public final String url(String var1) {
      Intrinsics.checkNotNullParameter(var1, "path");
      String var2 = this.getHost();
      return "http://" + var2 + ":8082" + var1;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final OkHttpClient client(long param1) {
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
         // 00: aload 0
         // 01: monitorenter
         // 02: invokestatic com/starpilot/starview/GxPage.access$getClients$cp ()Ljava/util/HashMap;
         // 05: checkcast java/util/Map
         // 08: astore 4
         // 0a: lload 1
         // 0b: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
         // 0e: astore 5
         // 10: aload 4
         // 12: aload 5
         // 14: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
         // 19: astore 3
         // 1a: aload 3
         // 1b: ifnonnull 50
         // 1e: new okhttp3/OkHttpClient$Builder
         // 21: astore 3
         // 22: aload 3
         // 23: invokespecial okhttp3/OkHttpClient$Builder.<init> ()V
         // 26: aload 3
         // 27: ldc2_w 5
         // 2a: getstatic java/util/concurrent/TimeUnit.SECONDS Ljava/util/concurrent/TimeUnit;
         // 2d: invokevirtual okhttp3/OkHttpClient$Builder.connectTimeout (JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;
         // 30: lload 1
         // 31: getstatic java/util/concurrent/TimeUnit.SECONDS Ljava/util/concurrent/TimeUnit;
         // 34: invokevirtual okhttp3/OkHttpClient$Builder.readTimeout (JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;
         // 37: lload 1
         // 38: getstatic java/util/concurrent/TimeUnit.SECONDS Ljava/util/concurrent/TimeUnit;
         // 3b: invokevirtual okhttp3/OkHttpClient$Builder.writeTimeout (JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;
         // 3e: invokevirtual okhttp3/OkHttpClient$Builder.build ()Lokhttp3/OkHttpClient;
         // 41: astore 3
         // 42: aload 4
         // 44: aload 5
         // 46: aload 3
         // 47: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
         // 4c: pop
         // 4d: goto 50
         // 50: aload 3
         // 51: checkcast okhttp3/OkHttpClient
         // 54: astore 3
         // 55: aload 0
         // 56: monitorexit
         // 57: aload 3
         // 58: areturn
         // 59: astore 3
         // 5a: aload 0
         // 5b: monitorexit
         // 5c: aload 3
         // 5d: athrow
      }

      public final MediaType getJSON() {
         return GxPage.JSON;
      }
   }

   public final class Row {
      private final TextView desc;
      final GxPage this$0;
      private final TextView titleView;
      private final LinearLayout view;

      // $VF: synthetic method
      public static void $r8$lambda$533ufi061xsLsMKn2NF_d32FZS8(Function1 var0, CompoundButton var1, boolean var2) {
         switch$lambda$4(var0, var1, var2);
      }

      public Row(String var1, String var2, String var3) {
         Intrinsics.checkNotNullParameter(var2, "title");
         this.this$0 = var1;
         super();
         LinearLayout var6 = new LinearLayout((Context)this.this$0.getAct());
         var1 = this.this$0;
         var6.setOrientation(0);
         var6.setGravity(16);
         var6.setMinimumHeight(var1.getUi().dp(76));
         var6.setPadding(var1.getUi().dp(20), var1.getUi().dp(14), var1.getUi().dp(18), var1.getUi().dp(14));
         var6.setBackground((Drawable)CarUi.rounded$default(var1.getUi(), CarUi.Companion.getCARD(), 0, null, 6, null));
         this.view = var6;
         var6 = new LinearLayout((Context)this.this$0.getAct());
         boolean var5 = true;
         var6.setOrientation(1);
         TextView var9 = new TextView((Context)this.this$0.getAct());
         var9.setText(var2);
         var9.setTextColor(CarUi.Companion.getTEXT());
         var9.setTextSize(21.0F);
         this.titleView = var9;
         TextView var12 = new TextView((Context)this.this$0.getAct());
         GxPage var7 = this.this$0;
         CharSequence var10;
         if (var3 != null) {
            var10 = var3;
         } else {
            var10 = "";
         }

         var12.setText(var10);
         var12.setTextColor(CarUi.Companion.getDIM());
         var12.setTextSize(15.0F);
         var12.setPadding(0, var7.getUi().dp(4), 0, 0);
         CharSequence var11 = var3;
         byte var4 = var5;
         if (var11 != null) {
            if (StringsKt.isBlank(var11)) {
               var4 = var5;
            } else {
               var4 = 0;
            }
         }

         if (var4) {
            var4 = 8;
         } else {
            var4 = 0;
         }

         var12.setVisibility(var4);
         this.desc = var12;
         var6.addView((View)this.titleView);
         var6.addView((View)this.desc);
         this.view.addView((View)var6, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      }

      // $VF: synthetic method
      public static TextView button$default(GxPage.Row var0, String var1, boolean var2, Function0 var3, int var4, Object var5) {
         if ((var4 & 2) != 0) {
            var2 = false;
         }

         return var0.button(var1, var2, var3);
      }

      private static final void switch$lambda$4(Function1 var0, CompoundButton var1, boolean var2) {
         if (var1.isPressed()) {
            var0.invoke(var2);
         }
      }

      public final void add(View var1) {
         Intrinsics.checkNotNullParameter(var1, "v");
         this.view.addView(var1);
      }

      public final TextView button(String var1, boolean var2, Function0<Unit> var3) {
         Intrinsics.checkNotNullParameter(var1, "label");
         Intrinsics.checkNotNullParameter(var3, "onClick");
         TextView var4 = this.this$0.getUi().button(var1, var2, var3);
         if (this.view.getChildCount() > 1) {
            this.view
               .addView(new View((Context)this.this$0.getAct()), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.this$0.getUi().dp(8), 1)));
         }

         this.view.addView((View)var4);
         return var4;
      }

      public final TextView getDesc() {
         return this.desc;
      }

      public final TextView getTitleView() {
         return this.titleView;
      }

      public final LinearLayout getView() {
         return this.view;
      }

      public final boolean getVisible() {
         boolean var1;
         if (this.view.getVisibility() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public final void setDesc(String var1) {
         TextView var5 = this.desc;
         CharSequence var4;
         if (var1 != null) {
            var4 = var1;
         } else {
            var4 = "";
         }

         var5.setText(var4);
         TextView var7 = this.desc;
         CharSequence var6 = var1;
         byte var3 = 0;
         boolean var2;
         if (var6 != null && !StringsKt.isBlank(var6)) {
            var2 = false;
         } else {
            var2 = true;
         }

         if (var2) {
            var3 = 8;
         }

         var7.setVisibility(var3);
      }

      public final void setTitle(String var1) {
         Intrinsics.checkNotNullParameter(var1, "text");
         this.titleView.setText(var1);
      }

      public final void setVisible(boolean var1) {
         LinearLayout var3 = this.view;
         byte var2;
         if (var1) {
            var2 = 0;
         } else {
            var2 = 8;
         }

         var3.setVisibility(var2);
      }

      public final Switch switch_(boolean var1, Function1<? super Boolean, Unit> var2) {
         Intrinsics.checkNotNullParameter(var2, "onChange");
         Switch var3 = this.this$0.getUi().bigSwitch();
         var3.setChecked(var1);
         var3.setOnCheckedChangeListener(new GxPage$Row$$ExternalSyntheticLambda0(var2));
         this.view.addView((View)var3);
         return var3;
      }

      public final TextView value(String var1) {
         Intrinsics.checkNotNullParameter(var1, "text");
         TextView var2 = new TextView((Context)this.this$0.getAct());
         GxPage var3 = this.this$0;
         var2.setText(var1);
         var2.setTextColor(CarUi.Companion.getTEXT());
         var2.setTextSize(20.0F);
         var2.setTypeface(Typeface.DEFAULT_BOLD);
         var2.setPadding(var3.getUi().dp(12), 0, var3.getUi().dp(4), 0);
         this.view.addView((View)var2);
         return var2;
      }
   }
}
