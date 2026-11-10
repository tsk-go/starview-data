package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public final class PairScanner {
   public static final PairScanner INSTANCE = new PairScanner();
   private static final long REPROMPT_MS = 300000L;
   private static volatile long lastPromptMs;
   private static volatile boolean prompting;

   // $VF: synthetic method
   public static Unit $r8$lambda$65rZIulRrfl67owF5_o60_DKdR8/* $VF was: $r8$lambda$65rZIulRrfl67owF5_o60_DKdR8*/() {
      return scan$lambda$0();
   }

   // $VF: synthetic method
   public static void $r8$lambda$6ANvnnw_4A6wgWTdT4LowLMRffY/* $VF was: $r8$lambda$6ANvnnw_4A6wgWTdT4LowLMRffY*/(Activity var0, Exception var1) {
      scan$lambda$3(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$DI3VXgVTmbiBHv1_HzLA9RcHEgI(DialogInterface var0) {
      promptIfNeeded$lambda$5(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$R_XWu66_wBMrK4NRKKHh9AvBEYU/* $VF was: $r8$lambda$R_XWu66_wBMrK4NRKKHh9AvBEYU*/(Function1 var0, Object var1) {
      scan$lambda$2(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$anqJeywV8THAv9oyitEk65Xd_oo(Activity var0, Function0 var1, Barcode var2) {
      return scan$lambda$1(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$b5yq75f45BguqjsFUj5BXAA_q9s(Activity var0, Function0 var1, DialogInterface var2, int var3) {
      promptIfNeeded$lambda$4(var0, var1, var2, var3);
   }

   private PairScanner() {
   }

   private static final void promptIfNeeded$lambda$4(Activity var0, Function0 var1, DialogInterface var2, int var3) {
      INSTANCE.scan(var0, var1);
   }

   private static final void promptIfNeeded$lambda$5(DialogInterface var0) {
      prompting = false;
   }

   // $VF: synthetic method
   public static void scan$default(PairScanner var0, Activity var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = new PairScanner$$ExternalSyntheticLambda5();
      }

      var0.scan(var1, (Function0<Unit>)var2);
   }

   private static final Unit scan$lambda$0() {
      return Unit.INSTANCE;
   }

   private static final Unit scan$lambda$1(Activity var0, Function0 var1, Barcode var2) {
      Link var5 = Link.INSTANCE;
      Context var4 = var0.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var4, "getApplicationContext(...)");
      String var3 = var2.getRawValue();
      String var6 = var3;
      if (var3 == null) {
         var6 = "";
      }

      if (var5.pairFromUri(var4, var6)) {
         Toast.makeText((Context)var0, "Paired with the comma", 0).show();
         var1.invoke();
      } else {
         Toast.makeText((Context)var0, "That isn't a StarView pairing code. On the comma: Settings › starview › show qr", 1).show();
      }

      return Unit.INSTANCE;
   }

   private static final void scan$lambda$2(Function1 var0, Object var1) {
      var0.invoke(var1);
   }

   private static final void scan$lambda$3(Activity var0, Exception var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      Context var2 = (Context)var0;
      String var3 = var1.getMessage();
      Toast.makeText(var2, "Scanner unavailable: " + var3, 1).show();
   }

   public final void promptIfNeeded(Activity var1, Function0<Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "onPaired");
      long var3 = System.currentTimeMillis();
      if (Link.INSTANCE.getPairingRequired() && !prompting && !var1.isFinishing() && var3 - lastPromptMs >= 300000L) {
         prompting = true;
         lastPromptMs = var3;
         new Builder((Context)var1)
            .setTitle("Pair with your comma")
            .setMessage("On the comma, open Settings › starview › show qr, then tap Scan.")
            .setPositiveButton("Scan", new PairScanner$$ExternalSyntheticLambda3(var1, var2))
            .setNegativeButton("Later", null)
            .setOnDismissListener(new PairScanner$$ExternalSyntheticLambda4())
            .show();
      }
   }

   public final void scan(Activity var1, Function0<Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "onPaired");
      GmsBarcodeScannerOptions var3 = new com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions.Builder().setBarcodeFormats(256).build();
      Intrinsics.checkNotNullExpressionValue(var3, "build(...)");
      GmsBarcodeScanning.getClient((Context)var1, var3)
         .startScan()
         .addOnSuccessListener(new PairScanner$$ExternalSyntheticLambda1(new PairScanner$$ExternalSyntheticLambda0(var1, var2)))
         .addOnFailureListener(new PairScanner$$ExternalSyntheticLambda2(var1));
   }
}
