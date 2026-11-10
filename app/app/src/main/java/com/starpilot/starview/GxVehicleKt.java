package com.starpilot.starview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.Result.Companion;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public final class GxVehicleKt {
   private static final int GX_VEHICLE_ERR = Color.rgb(255, 107, 107);

   // $VF: synthetic method
   public static final int access$getGX_VEHICLE_ERR$p() {
      return GX_VEHICLE_ERR;
   }

   // $VF: synthetic method
   public static final List access$gxVehicleButtons(GxPage var0, ViewGroup var1, List var2, boolean var3) {
      return gxVehicleButtons(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static final EditText access$gxVehicleEdit(GxPage var0, String var1, String var2, int var3) {
      return gxVehicleEdit(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static final void access$gxVehicleEnable(View var0, boolean var1) {
      gxVehicleEnable(var0, var1);
   }

   // $VF: synthetic method
   public static final TextView access$gxVehicleNotice(GxPage var0, String var1, int var2, ViewGroup var3) {
      return gxVehicleNotice(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static final boolean access$gxVehicleOpenUrl(Activity var0, String var1) {
      return gxVehicleOpenUrl(var0, var1);
   }

   private static final List<TextView> gxVehicleButtons(
      GxPage var0, ViewGroup var1, List<? extends Triple<String, Boolean, ? extends Function0<Unit>>> var2, boolean var3
   ) {
      Iterable var7 = var2;
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));

      for (Triple var16 : var7) {
         var6.add(TuplesKt.to(var16.getFirst(), var16.getThird()));
      }

      List var11 = var6;
      Collection var12 = var11;
      Pair[] var13 = var12.toArray(new Pair[0]);
      LinearLayout var10 = var0.buttons(Arrays.copyOf(var13, var13.length), var1, var3);
      ArrayList var9 = new ArrayList();
      int var4 = 0;

      for (int var5 = var10.getChildCount(); var4 < var5; var4++) {
         View var14 = var10.getChildAt(var4);
         Intrinsics.checkNotNull(var14, "null cannot be cast to non-null type android.widget.TextView");
         var var15 = (TextView & View)var14;
         gxVehicleEnable((View)var15, (Boolean)((Triple)var2.get(var4)).getSecond());
         var9.add(var15);
      }

      return var9;
   }

   // $VF: synthetic method
   static List gxVehicleButtons$default(GxPage var0, ViewGroup var1, List var2, boolean var3, int var4, Object var5) {
      if ((var4 & 8) != 0) {
         var3 = false;
      }

      return gxVehicleButtons(var0, var1, var2, var3);
   }

   private static final EditText gxVehicleEdit(GxPage var0, String var1, String var2, int var3) {
      EditText var4 = new EditText((Context)var0.getAct());
      var4.setText(var2);
      var4.setHint(var1);
      var4.setInputType(var3);
      var4.setTextSize(20.0F);
      var4.setTextColor(CarUi.Companion.getTEXT());
      var4.setHintTextColor(CarUi.Companion.getDIM());
      var4.setSingleLine(true);
      return var4;
   }

   // $VF: synthetic method
   static EditText gxVehicleEdit$default(GxPage var0, String var1, String var2, int var3, int var4, Object var5) {
      if ((var4 & 8) != 0) {
         var3 = 1;
      }

      return gxVehicleEdit(var0, var1, var2, var3);
   }

   private static final void gxVehicleEnable(View var0, boolean var1) {
      var0.setEnabled(var1);
      float var2;
      if (var1) {
         var2 = 1.0F;
      } else {
         var2 = 0.4F;
      }

      var0.setAlpha(var2);
   }

   private static final TextView gxVehicleNotice(GxPage var0, String var1, int var2, ViewGroup var3) {
      TextView var4 = new TextView((Context)var0.getAct());
      var4.setText(var1);
      var4.setTextColor(var2);
      var4.setTextSize(17.0F);
      var4.setPadding(var0.getUi().dp(16), var0.getUi().dp(12), var0.getUi().dp(16), var0.getUi().dp(12));
      var4.setBackground(
         (Drawable)var0.getUi()
            .rounded(
               Color.argb(40, Color.red(var2), Color.green(var2), Color.blue(var2)), 12, Color.argb(120, Color.red(var2), Color.green(var2), Color.blue(var2))
            )
      );
      var3.addView((View)var4, (LayoutParams)CarUi.lp$default(var0.getUi(), 0, 0, 3, null));
      return var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final boolean gxVehicleOpenUrl(Activity var0, String var1) {
      label24:
      try {
         Companion var2 = Result.Companion;
         Intent var8 = new Intent("android.intent.action.VIEW", Uri.parse(var1));
         var0.startActivity(var8.addFlags(268435456));
         var5 = KtCompat.resultOf(true);
      } catch (Throwable var4) {
         Companion var6 = Result.Companion;
         var5 = KtCompat.resultOf(ResultKt.createFailure(var4));
         break label24;
      }

      Object var7 = var5;
      if (KtCompat.isFailure(var5)) {
         var7 = false;
      }

      return (Boolean)var7;
   }
}
