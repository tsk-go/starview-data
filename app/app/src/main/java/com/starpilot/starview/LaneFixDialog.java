package com.starpilot.starview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.MeasureSpec;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;

public final class LaneFixDialog {
   public static final LaneFixDialog INSTANCE = new LaneFixDialog();

   // $VF: synthetic method
   public static void $r8$lambda$1t_7okHEmrzYa4_0vXNkB_DPkfU/* $VF was: $r8$lambda$1t_7okHEmrzYa4_0vXNkB_DPkfU*/(
      Function0 var0, ObjectRef var1, ObjectRef var2, View var3
   ) {
      show$pm$lambda$16$lambda$14(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8o_Wd1f9hIwr5vuWIAHoJDmGIeM/* $VF was: $r8$lambda$8o_Wd1f9hIwr5vuWIAHoJDmGIeM*/(ObjectRef var0) {
      return show$lambda$12(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$A26GIn3Q1VenArZTcbwtcT2b_6I(
      LinearLayout var0,
      LaneFixDialog.Road var1,
      LinearLayout var2,
      CheckBox var3,
      TextView var4,
      String var5,
      ObjectRef var6,
      ObjectRef var7,
      Context var8,
      float var9,
      int var10
   ) {
      return show$lambda$11(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$L3NCM_zvg2g9iABGJLxfRyulQfI/* $VF was: $r8$lambda$L3NCM_zvg2g9iABGJLxfRyulQfI*/(LaneFixDialog.Road var0, int var1) {
      return show$side$lambda$20$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$NzAgOkpSNz2yK0tKyCt_P4XEkBU/* $VF was: $r8$lambda$NzAgOkpSNz2yK0tKyCt_P4XEkBU*/(
      LaneFixDialog.Road var0, LaneRamps.Ramp var1, ObjectRef var2, ObjectRef var3
   ) {
      return show$lambda$26(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$T2jKSoCRqmN9S5YouJe66Aa_G1Q/* $VF was: $r8$lambda$T2jKSoCRqmN9S5YouJe66Aa_G1Q*/(
      LaneFixDialog.Result var0, CheckBox var1, Dialog var2, Function1 var3
   ) {
      return show$lambda$29(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$YEYNiGHEq7bBMqL3TAy7SBd154k(Dialog var0, Function1 var1) {
      return show$lambda$30(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$aysn8cUNJMl21MZLo0U__uAno80/* $VF was: $r8$lambda$aysn8cUNJMl21MZLo0U__uAno80*/(Dialog var0, Function1 var1) {
      return show$lambda$31(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$bBNLD7GbOykpfgEcjNfKGjN_2Lw/* $VF was: $r8$lambda$bBNLD7GbOykpfgEcjNfKGjN_2Lw*/(LaneFixDialog.Road var0, int var1) {
      return show$side$lambda$20$lambda$18(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$f60zQXFJPv9wNF7gSE_m5Fx7y_8/* $VF was: $r8$lambda$f60zQXFJPv9wNF7gSE_m5Fx7y_8*/(Dialog var0, Function0 var1) {
      return show$lambda$39(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$j27GHSruPQBeWnDA78XKQhh5eSA(Function0 var0, View var1) {
      show$bbtn$lambda$38$lambda$36(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jRRFjg0YkkSybMgaAPZ87VcjJYg(Dialog var0, Function1 var1) {
      return show$lambda$32(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$l9hN3wMBBhpVcP6Q1twHQSO3lBE(CompoundButton var0, boolean var1) {
      show$lambda$46(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pG8US98W1_K2Avq_Gb310TWizUQ/* $VF was: $r8$lambda$pG8US98W1_K2Avq_Gb310TWizUQ*/(
      LaneFixDialog.Road var0, CheckBox var1, Dialog var2, Function1 var3
   ) {
      return show$lambda$42(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qxF7cmEjHmcm4gOaLkVXqEkgAJs(LaneFixDialog.Road var0, int var1, ObjectRef var2, ObjectRef var3) {
      return show$lambda$11$lambda$10(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$yu1SNDjVKk826BNyKWaLJKnM3i8(Function0 var0, View var1) {
      show$chip$lambda$5$lambda$3(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zkKYfvsnWVX0GbpzR6Bb9_Y2wqs/* $VF was: $r8$lambda$zkKYfvsnWVX0GbpzR6Bb9_Y2wqs*/(
      LaneFixDialog.Road var0, String var1, ObjectRef var2, ObjectRef var3
   ) {
      return show$lambda$11$lambda$9(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zyN3sNUugMIIU777DlxC26MJvmY(Dialog var0) {
      return show$lambda$41(var0);
   }

   private LaneFixDialog() {
   }

   private static final TextView show$bbtn(Context var0, float var1, String var2, int var3, int var4, Function0<Unit> var5) {
      TextView var6 = new TextView(var0);
      var6.setText(var2);
      var6.setTextSize(19.0F);
      var6.setTypeface(null, 1);
      var6.setGravity(17);
      var6.setTextColor(var4);
      GradientDrawable var7 = new GradientDrawable();
      var7.setCornerRadius(14 * var1);
      var7.setColor(var3);
      var6.setBackground((Drawable)var7);
      var6.setPadding(show$dp(var1, 30), show$dp(var1, 14), show$dp(var1, 30), show$dp(var1, 14));
      var6.setOnClickListener(new LaneFixDialog$$ExternalSyntheticLambda6(var5));
      LayoutParams var8 = new LayoutParams(-2, -2);
      var8.setMarginStart(show$dp(var1, 14));
      var6.setLayoutParams((android.view.ViewGroup.LayoutParams)var8);
      return var6;
   }

   private static final void show$bbtn$lambda$38$lambda$36(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final TextView show$chip(Context var0, float var1, String var2, boolean var3, boolean var4, Function0<Unit> var5) {
      TextView var12 = new TextView(var0);
      var12.setText(var2);
      float var6;
      if (var4) {
         var6 = 18.0F;
      } else {
         var6 = 15.0F;
      }

      var12.setTextSize(var6);
      var12.setGravity(17);
      var12.setTypeface(null, 1);
      int var7;
      if (var3) {
         var7 = -16777216;
      } else {
         var7 = -1;
      }

      var12.setTextColor(var7);
      GradientDrawable var13 = new GradientDrawable();
      byte var10 = 12;
      var13.setCornerRadius(12 * var1);
      short var8;
      short var9;
      byte var15;
      if (var3) {
         var8 = 227;
         var9 = 161;
         var15 = 111;
      } else {
         var8 = 54;
         var9 = 66;
         var15 = 48;
      }

      var13.setColor(Color.rgb(var15, var8, var9));
      var12.setBackground((Drawable)var13);
      int var20 = 20;
      if (var4) {
         var15 = 20;
      } else {
         var15 = 14;
      }

      var9 = show$dp(var1, var15);
      if (var4) {
         var15 = 12;
      } else {
         var15 = 9;
      }

      int var11 = show$dp(var1, var15);
      if (var4) {
         var15 = (byte)var20;
      } else {
         var15 = 14;
      }

      var20 = show$dp(var1, var15);
      if (var4) {
         var15 = var10;
      } else {
         var15 = 9;
      }

      var12.setPadding(var9, var11, var20, show$dp(var1, var15));
      var12.setOnClickListener(new LaneFixDialog$$ExternalSyntheticLambda0(var5));
      LayoutParams var14 = new LayoutParams(-2, -2);
      var14.setMarginEnd(show$dp(var1, 10));
      var12.setLayoutParams((android.view.ViewGroup.LayoutParams)var14);
      return var12;
   }

   // $VF: synthetic method
   static TextView show$chip$default(Context var0, float var1, String var2, boolean var3, boolean var4, Function0 var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var4 = true;
      }

      return show$chip(var0, var1, var2, var3, var4, var5);
   }

   private static final void show$chip$lambda$5$lambda$3(Function0 var0, View var1) {
      var0.invoke();
   }

   // $VF: synthetic method
   public static void show$default(
      LaneFixDialog var0,
      Context var1,
      String var2,
      int var3,
      String var4,
      int var5,
      boolean var6,
      int var7,
      Function0 var8,
      LaneFixDialog.Result var9,
      Bitmap var10,
      String var11,
      List var12,
      Function1 var13,
      LaneFixDialog.Result var14,
      Function1 var15,
      int var16,
      Object var17
   ) {
      if ((var16 & 64) != 0) {
         var7 = 0;
      }

      if ((var16 & 128) != 0) {
         var8 = null;
      }

      if ((var16 & 256) != 0) {
         var9 = null;
      }

      if ((var16 & 512) != 0) {
         var10 = null;
      }

      if ((var16 & 1024) != 0) {
         var11 = "";
      }

      if ((var16 & 2048) != 0) {
         var12 = CollectionsKt.emptyList();
      }

      if ((var16 & 4096) != 0) {
         var13 = null;
      }

      if ((var16 & 8192) != 0) {
         var14 = null;
      }

      var0.show(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15);
   }

   private static final int show$dp(float var0, int var1) {
      return (int)(var1 * var0);
   }

   private static final TextView show$label(int var0, Context var1, float var2, String var3, float var4, int var5) {
      TextView var6 = new TextView(var1);
      var6.setText(var3);
      var6.setTextSize(var4);
      var6.setTextColor(var5);
      var6.setPadding(0, show$dp(var2, 6), 0, show$dp(var2, 6));
      return var6;
   }

   // $VF: synthetic method
   static TextView show$label$default(int var0, Context var1, float var2, String var3, float var4, int var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var4 = 17.0F;
      }

      if ((var6 & 32) != 0) {
         var5 = var0;
      }

      return show$label(var0, var1, var2, var3, var4, var5);
   }

   private static final Unit show$lambda$11(
      LinearLayout var0,
      LaneFixDialog.Road var1,
      LinearLayout var2,
      CheckBox var3,
      TextView var4,
      String var5,
      ObjectRef var6,
      ObjectRef var7,
      Context var8,
      float var9,
      int var10
   ) {
      var0.removeAllViews();
      Pair var13 = TuplesKt.to("one", "One-way");
      boolean var11 = false;

      for (Pair var14 : CollectionsKt.listOf(new Pair[]{var13, TuplesKt.to("divided", "Divided highway"), TuplesKt.to("two", "Two-way street")})) {
         String var15 = (String)var14.component1();
         var0.addView(
            (View)show$chip$default(
               var8,
               var9,
               (String)var14.component2(),
               Intrinsics.areEqual(var1.getWay(), var15),
               false,
               new LaneFixDialog$$ExternalSyntheticLambda3(var1, var15, var6, var7),
               16,
               null
            )
         );
      }

      var2.removeAllViews();
      if (Intrinsics.areEqual(var1.getWay(), "two")) {
         var2.addView((View)show$label$default(var10, var8, var9, "Lanes the other way:  ", 15.0F, 0, 32, null));

         for (int var22 = 1; var22 < 5; var22++) {
            boolean var12;
            if (var1.getOther() == var22) {
               var12 = true;
            } else {
               var12 = false;
            }

            var2.addView((View)show$chip(var8, var9, String.valueOf(var22), var12, false, new LaneFixDialog$$ExternalSyntheticLambda4(var1, var22, var6, var7)));
         }
      }

      String var21 = var1.result(var3.isChecked()).sentence();
      var10 = var1.getYou();
      String var20 = "";
      String var16;
      if (var10 < 0 && var1.getTypes().size() > 1) {
         var16 = "  -  tap your lane";
      } else {
         var16 = "";
      }

      label37: {
         var4.setText(var21 + var16);
         CharSequence var17 = var5;
         if (var17 != null) {
            var24 = var11;
            if (var17.length() != 0) {
               break label37;
            }
         }

         var24 = true;
      }

      String var19;
      if (!var24) {
         var10 = var1.getThrough();
         String var18;
         if (var1.getThrough() == 1) {
            var18 = var20;
         } else {
            var18 = "s";
         }

         var19 = "Remember " + var10 + " lane" + var18 + " for this stretch of " + var5 + " (merge / exit lanes are not remembered)";
      } else {
         var19 = "No street - just for now";
      }

      var3.setText(var19);
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$11$lambda$10(LaneFixDialog.Road var0, int var1, ObjectRef var2, ObjectRef var3) {
      if (var0.getOther() == var1) {
         var1 = 0;
      }

      var0.setOther(var1);
      Function0 var5 = var2.element;
      Object var4 = null;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var5 = null;
      } else {
         var5 = (Function0)var2.element;
      }

      var5.invoke();
      LaneFixDialog.RoadView var7;
      if (var3.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("roadView");
         var7 = (LaneFixDialog.RoadView)var4;
      } else {
         var7 = (LaneFixDialog.RoadView)var3.element;
      }

      var7.invalidate();
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$11$lambda$9(LaneFixDialog.Road var0, String var1, ObjectRef var2, ObjectRef var3) {
      if (Intrinsics.areEqual(var0.getWay(), var1)) {
         var1 = "";
      }

      var0.setWay(var1);
      if (!Intrinsics.areEqual(var0.getWay(), "two")) {
         var0.setOther(0);
      }

      Function0 var4 = var2.element;
      Object var7 = null;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var4 = null;
      } else {
         var4 = (Function0)var2.element;
      }

      var4.invoke();
      LaneFixDialog.RoadView var6;
      if (var3.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("roadView");
         var6 = (LaneFixDialog.RoadView)var7;
      } else {
         var6 = (LaneFixDialog.RoadView)var3.element;
      }

      var6.invalidate();
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$12(ObjectRef var0) {
      Function0 var1;
      if (var0.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var1 = null;
      } else {
         var1 = (Function0)var0.element;
      }

      var1.invoke();
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$26(LaneFixDialog.Road var0, LaneRamps.Ramp var1, ObjectRef var2, ObjectRef var3) {
      char var4;
      if (Intrinsics.areEqual(var1.getKind(), "exit")) {
         var4 = 'x';
      } else {
         var4 = 'm';
      }

      var0.addExtra(var4, var1.getSide());
      Function0 var5 = var2.element;
      Object var8 = null;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var5 = null;
      } else {
         var5 = (Function0)var2.element;
      }

      var5.invoke();
      LaneFixDialog.RoadView var7;
      if (var3.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("roadView");
         var7 = (LaneFixDialog.RoadView)var8;
      } else {
         var7 = (LaneFixDialog.RoadView)var3.element;
      }

      var7.invalidate();
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$29(LaneFixDialog.Result var0, CheckBox var1, Dialog var2, Function1 var3) {
      show$save(
         var2,
         var3,
         new LaneFixDialog.Result(
            var0.getThrough(),
            var0.getExtraL(),
            var0.getExtraR(),
            var0.getKind(),
            var0.getLane(),
            var1.isChecked(),
            var0.getWay(),
            var0.getOtherWay(),
            var0.getKindR()
         )
      );
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$30(Dialog var0, Function1 var1) {
      var0.dismiss();
      var1.invoke("skip");
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$31(Dialog var0, Function1 var1) {
      var0.dismiss();
      var1.invoke("intersection");
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$32(Dialog var0, Function1 var1) {
      var0.dismiss();
      var1.invoke("lane_change");
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$39(Dialog var0, Function0 var1) {
      var0.dismiss();
      var1.invoke();
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$41(Dialog var0) {
      var0.dismiss();
      return Unit.INSTANCE;
   }

   private static final Unit show$lambda$42(LaneFixDialog.Road var0, CheckBox var1, Dialog var2, Function1 var3) {
      show$save(var2, var3, var0.result(var1.isChecked()));
      return Unit.INSTANCE;
   }

   private static final void show$lambda$46(CompoundButton var0, boolean var1) {
   }

   private static final TextView show$pm(
      Context var0, float var1, ObjectRef<Function0<Unit>> var2, ObjectRef<LaneFixDialog.RoadView> var3, String var4, Function0<Unit> var5
   ) {
      TextView var6 = new TextView(var0);
      var6.setText(var4);
      var6.setTextSize(28.0F);
      var6.setGravity(17);
      var6.setTypeface(null, 1);
      var6.setTextColor(-1);
      GradientDrawable var8 = new GradientDrawable();
      var8.setCornerRadius(14 * var1);
      var8.setColor(Color.rgb(48, 54, 66));
      var6.setBackground((Drawable)var8);
      var6.setOnClickListener(new LaneFixDialog$$ExternalSyntheticLambda5(var5, var2, var3));
      LayoutParams var7 = new LayoutParams(show$dp(var1, 60), show$dp(var1, 60));
      var7.bottomMargin = show$dp(var1, 10);
      var6.setLayoutParams((android.view.ViewGroup.LayoutParams)var7);
      return var6;
   }

   private static final void show$pm$lambda$16$lambda$14(Function0 var0, ObjectRef var1, ObjectRef var2, View var3) {
      var0.invoke();
      Function0 var4 = var1.element;
      Object var7 = null;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var4 = null;
      } else {
         var4 = (Function0)var1.element;
      }

      var4.invoke();
      LaneFixDialog.RoadView var6;
      if (var2.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("roadView");
         var6 = (LaneFixDialog.RoadView)var7;
      } else {
         var6 = (LaneFixDialog.RoadView)var2.element;
      }

      var6.invalidate();
   }

   private static final void show$save(Dialog var0, Function1<? super LaneFixDialog.Result, Unit> var1, LaneFixDialog.Result var2) {
      var0.dismiss();
      var1.invoke(var2);
   }

   private static final LinearLayout show$side(
      Context var0, float var1, LaneFixDialog.Road var2, ObjectRef<Function0<Unit>> var3, ObjectRef<LaneFixDialog.RoadView> var4, int var5, int var6
   ) {
      LinearLayout var7 = new LinearLayout(var0);
      var7.setOrientation(1);
      var7.setGravity(1);
      var7.setPadding(show$dp(var1, 8), show$dp(var1, 40), show$dp(var1, 8), 0);
      var7.addView((View)show$pm(var0, var1, var3, var4, "+", new LaneFixDialog$$ExternalSyntheticLambda7(var2, var6)));
      var7.addView((View)show$pm(var0, var1, var3, var4, "−", new LaneFixDialog$$ExternalSyntheticLambda8(var2, var6)));
      String var9;
      if (var6 < 0) {
         var9 = "left";
      } else {
         var9 = "right";
      }

      TextView var8 = show$label(var5, var0, var1, var9, 14.0F, Color.rgb(154, 163, 181));
      var8.setGravity(17);
      var7.addView((View)var8);
      return var7;
   }

   private static final Unit show$side$lambda$20$lambda$17(LaneFixDialog.Road var0, int var1) {
      var0.add(var1);
      return Unit.INSTANCE;
   }

   private static final Unit show$side$lambda$20$lambda$18(LaneFixDialog.Road var0, int var1) {
      var0.remove(var1);
      return Unit.INSTANCE;
   }

   public final void show(
      Context var1,
      String var2,
      int var3,
      String var4,
      int var5,
      boolean var6,
      int var7,
      Function0<Unit> var8,
      LaneFixDialog.Result var9,
      Bitmap var10,
      String var11,
      List<LaneRamps.Ramp> var12,
      Function1<? super String, Unit> var13,
      LaneFixDialog.Result var14,
      Function1<? super LaneFixDialog.Result, Unit> var15
   ) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "title");
      Intrinsics.checkNotNullParameter(var11, "pictureNote");
      Intrinsics.checkNotNullParameter(var12, "ramps");
      Intrinsics.checkNotNullParameter(var15, "onSave");
      float var16 = var1.getResources().getDisplayMetrics().density;
      LaneFixDialog.Road.Companion var20 = LaneFixDialog.Road.Companion;
      LaneFixDialog.Result var19;
      if (var14 == null) {
         var19 = var9;
      } else {
         var19 = var14;
      }

      if (var5 <= 0) {
         var5 = 3;
      }

      LaneFixDialog.Road var23 = var20.of(var19, var5);
      Dialog var63 = new Dialog(var1);
      var63.requestWindowFeature(1);
      var5 = Color.rgb(207, 214, 228);
      CheckBox var21 = new CheckBox(var1);
      CharSequence var55 = var4;
      boolean var18;
      if (var55 != null && var55.length() != 0) {
         var18 = false;
      } else {
         var18 = true;
      }

      label130: {
         if (!var18) {
            if (var9 != null) {
               var6 = var9.getRemember();
            }

            if (var6) {
               var6 = true;
               break label130;
            }
         }

         var6 = false;
      }

      var21.setChecked(var6);
      CharSequence var56 = var4;
      if (var56 != null && var56.length() != 0) {
         var18 = false;
      } else {
         var18 = true;
      }

      var21.setEnabled(var18 ^ true);
      var21.setTextSize(15.0F);
      var21.setTextColor(Color.rgb(170, 178, 194));
      TextView var25 = show$label(var5, var1, var16, "", 22.0F, Color.rgb(111, 227, 161));
      var25.setTypeface(null, 1);
      LinearLayout var28 = new LinearLayout(var1);
      LinearLayout var27 = new LinearLayout(var1);
      var27.setGravity(16);
      ObjectRef var24 = new ObjectRef();
      ObjectRef var22 = new ObjectRef();
      var22.element = (T)(new LaneFixDialog$$ExternalSyntheticLambda9(var28, var23, var27, var21, var25, var4, var22, var24, var1, var16, var5));
      var24.element = (T)(new LaneFixDialog.RoadView(var1, var23, new LaneFixDialog$$ExternalSyntheticLambda11(var22)));
      LinearLayout var26 = new LinearLayout(var1);
      var26.addView((View)show$side(var1, var16, var23, var22, var24, var5, -1));
      LaneFixDialog.RoadView var39;
      if (var24.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("roadView");
         var39 = null;
      } else {
         var39 = (LaneFixDialog.RoadView)var24.element;
      }

      var26.addView(var39, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      var26.addView((View)show$side(var1, var16, var23, var22, var24, var5, 1));
      LinearLayout var57 = new LinearLayout(var1);
      var57.setOrientation(1);
      var57.addView((View)var28);
      var57.addView((View)var27);
      if (var3 > 0) {
         var4 = "  (map says " + var3 + ")";
      } else {
         var4 = "";
      }

      String var66 = "Lanes going YOUR way. Tap the lane you're in.  + / − add or take away a lane." + var4;
      var57.addView((View)show$label$default(var5, var1, var16, var66, 16.0F, 0, 32, null));
      var57.addView((View)var26);
      var57.addView(
         (View)show$label(
            var5, var1, var16, "The tag under an edge lane: normal → EXIT → MERGE (a lane that's only there for a while).", 14.0F, Color.rgb(154, 163, 181)
         )
      );
      var57.addView((View)var25);
      var57.addView((View)var21);
      if (var7 > 0) {
         var57.addView((View)show$label(var5, var1, var16, "You saved " + var7 + " lanes for this stretch before.", 14.0F, Color.rgb(160, 168, 184)));
      }

      LinearLayout var64 = new LinearLayout(var1);
      var64.setOrientation(1);
      var64.setPadding(0, 0, show$dp(var16, 26), 0);
      if (var10 != null) {
         ZoomImageView var58 = new ZoomImageView(var1);
         var58.setImageBitmap(var10);
         float var17 = (float)var10.getHeight() / Math.max(1, var10.getWidth());
         var64.addView((View)var58, (android.view.ViewGroup.LayoutParams)(new LayoutParams(show$dp(var16, 560), (int)(show$dp(var16, 560) * var17))));
         boolean var37;
         if (var11.length() > 0) {
            var37 = true;
         } else {
            var37 = false;
         }

         if (var37) {
            var64.addView((View)show$label(var5, var1, var16, var11, 14.0F, Color.rgb(154, 163, 181)));
         }
      }

      var6 = var12.isEmpty();
      String var59 = "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams";
      LinearLayout var52;
      if (!var6) {
         Iterator var65 = var12.iterator();
         var3 = var5;
         var52 = var57;
         var4 = var59;

         while (var65.hasNext()) {
            LaneRamps.Ramp var67 = (LaneRamps.Ramp)var65.next();
            LinearLayout var60 = new LinearLayout(var1);
            var60.setGravity(16);
            String var69 = var67.text();
            var60.addView((View)show$label(var3, var1, var16, "Map: " + var69, 15.0F, Color.rgb(240, 181, 74)));
            TextView var70 = show$chip(var1, var16, "Add it", false, false, new LaneFixDialog$$ExternalSyntheticLambda12(var23, var67, var22, var24));
            android.view.ViewGroup.LayoutParams var68 = var70.getLayoutParams();
            Intrinsics.checkNotNull(var68, var4);
            ((LayoutParams)var68).setMarginStart(show$dp(var16, 12));
            var60.addView((View)var70);
            var64.addView((View)var60);
         }
      } else {
         var3 = var5;
         String var61 = "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams";
         var52 = var57;
         var4 = var61;
      }

      LinearLayout var62 = new LinearLayout(var1);
      var62.setPadding(0, show$dp(var16, 12), 0, 0);
      if (var9 != null) {
         var62.addView((View)show$chip(var1, var16, "Same as last", true, false, new LaneFixDialog$$ExternalSyntheticLambda13(var9, var21, var63, var15)));
      }

      if (var13 != null) {
         var62.addView((View)show$chip(var1, var16, "Skip", false, false, new LaneFixDialog$$ExternalSyntheticLambda14(var63, var13)));
         var62.addView((View)show$chip(var1, var16, "Intersection", false, false, new LaneFixDialog$$ExternalSyntheticLambda15(var63, var13)));
         var62.addView((View)show$chip(var1, var16, "Changing lanes", false, false, new LaneFixDialog$$ExternalSyntheticLambda16(var63, var13)));
      }

      if (var62.getChildCount() > 0) {
         var64.addView((View)var62);
      }

      if (var9 != null && var14 == null) {
         String var49 = var9.text();
         var64.addView((View)show$label(var3, var1, var16, "Started from your last fix: " + var49, 14.0F, Color.rgb(154, 163, 181)));
      }

      LinearLayout var50 = new LinearLayout(var1);
      if (var10 == null && var12.isEmpty()) {
         var50.setOrientation(1);
         if (var64.getChildCount() > 0) {
            var50.addView((View)var64);
         }

         var50.addView((View)var52);
      } else {
         var50.addView((View)var64, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-2, -2)));
         var50.addView((View)var52, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      }

      LinearLayout var51 = new LinearLayout(var1);
      var51.setGravity(16);
      var51.setPadding(0, show$dp(var16, 14), 0, 0);
      if (var7 > 0 && var8 != null) {
         TextView var47 = show$bbtn(
            var1, var16, "Delete saved count", Color.rgb(48, 54, 66), Color.rgb(255, 150, 150), new LaneFixDialog$$ExternalSyntheticLambda17(var63, var8)
         );
         android.view.ViewGroup.LayoutParams var53 = var47.getLayoutParams();
         Intrinsics.checkNotNull(var53, var4);
         ((LayoutParams)var53).setMarginStart(0);
         var51.addView((View)var47);
      }

      var51.addView(new View(var1), (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, 1, 1.0F)));
      var51.addView((View)show$bbtn(var1, var16, "Cancel", Color.rgb(48, 54, 66), -1, new LaneFixDialog$$ExternalSyntheticLambda1(var63)));
      var51.addView(
         (View)show$bbtn(var1, var16, "Save", Color.rgb(111, 227, 161), -16777216, new LaneFixDialog$$ExternalSyntheticLambda2(var23, var21, var63, var15))
      );
      LinearLayout var43 = new LinearLayout(var1);
      var43.setOrientation(1);
      var43.setPadding(show$dp(var16, 28), show$dp(var16, 20), show$dp(var16, 28), show$dp(var16, 20));
      GradientDrawable var48 = new GradientDrawable();
      var48.setCornerRadius(22 * var16);
      var48.setColor(Color.rgb(27, 31, 39));
      var43.setBackground((Drawable)var48);
      TextView var31 = show$label(var3, var1, var16, var2, 24.0F, -1);
      var31.setTypeface(null, 1);
      var43.addView((View)var31);
      var43.addView((View)var50);
      var43.addView((View)var51);
      var21.setOnCheckedChangeListener(new LaneFixDialog$$ExternalSyntheticLambda10());
      Function0 var32;
      if (var22.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var32 = null;
      } else {
         var32 = (Function0)var22.element;
      }

      var32.invoke();
      ScrollView var33 = new ScrollView(var1);
      var33.addView((View)var43);
      var63.setContentView((View)var33);
      Window var34 = var63.getWindow();
      if (var34 != null) {
         var34.setBackgroundDrawable((Drawable)(new ColorDrawable(0)));
         Unit var35 = Unit.INSTANCE;
      }

      var63.show();
      DisplayMetrics var29 = var1.getResources().getDisplayMetrics();
      Window var36 = var63.getWindow();
      if (var36 != null) {
         var36.setLayout((int)(var29.widthPixels * 0.96F), -2);
         Unit var30 = Unit.INSTANCE;
      }
   }

   public static final class Result {
      private final int extraL;
      private final int extraR;
      private final String kind;
      private final String kindR;
      private final int lane;
      private final int otherWay;
      private final boolean remember;
      private final int through;
      private final String way;

      public Result(int var1, int var2, int var3, String var4, int var5, boolean var6, String var7, int var8, String var9) {
         Intrinsics.checkNotNullParameter(var4, "kind");
         Intrinsics.checkNotNullParameter(var7, "way");
         Intrinsics.checkNotNullParameter(var9, "kindR");
         super();
         this.through = var1;
         this.extraL = var2;
         this.extraR = var3;
         this.kind = var4;
         this.lane = var5;
         this.remember = var6;
         this.way = var7;
         this.otherWay = var8;
         this.kindR = var9;
      }

      // $VF: synthetic method
      public Result(
         int var1, int var2, int var3, String var4, int var5, boolean var6, String var7, int var8, String var9, int var10, DefaultConstructorMarker var11
      ) {
         if ((var10 & 64) != 0) {
            var7 = "";
         }

         if ((var10 & 128) != 0) {
            var8 = 0;
         }

         if ((var10 & 256) != 0) {
            var9 = "";
         }

         this(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      }

      private static final String sentence$ex$1(int var0, String var1, String var2) {
         if (var0 <= 0) {
            var1 = null;
         } else {
            String var3 = "exit";
            if (Intrinsics.areEqual(var1, "exit")) {
               var1 = var3;
            } else {
               var1 = "merge";
            }

            if (var0 > 1) {
               var3 = "s";
            } else {
               var3 = "";
            }

            var1 = var0 + " " + var1 + " lane" + var3 + " on the " + var2;
         }

         return var1;
      }

      private static final String text$ex(int var0, String var1, String var2) {
         if (var0 <= 0) {
            var1 = null;
         } else {
            if (Intrinsics.areEqual(var1, "exit")) {
               if (var0 > 1) {
                  var1 = "s";
               } else {
                  var1 = "";
               }

               var1 = "exit lane" + var1;
            } else {
               var1 = "merging";
            }

            var1 = var0 + " " + var1 + " on the " + var2;
         }

         return var1;
      }

      public final int getExtraL() {
         return this.extraL;
      }

      public final int getExtraR() {
         return this.extraR;
      }

      public final String getKind() {
         return this.kind;
      }

      public final String getKindLeft() {
         return this.kind;
      }

      public final String getKindR() {
         return this.kindR;
      }

      public final String getKindRight() {
         var var2 = this.kindR;
         boolean var1;
         if (var2.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            var2 = this.kind;
         }

         return var2;
      }

      public final int getLane() {
         return this.lane;
      }

      public final int getOtherWay() {
         return this.otherWay;
      }

      public final boolean getRemember() {
         return this.remember;
      }

      public final int getThrough() {
         return this.through;
      }

      public final int getTotal() {
         return this.through + this.extraL + this.extraR;
      }

      public final Boolean getTwoWay() {
         String var2 = this.way;
         switch (var2.hashCode()) {
            case 110182:
               if (!var2.equals("one")) {
                  return null;
               }
               break;
            case 115276:
               if (var2.equals("two")) {
                  boolean var1 = true;
                  return var1;
               }

               return null;
            case 1674318603:
               if (!var2.equals("divided")) {
                  return null;
               }
               break;
            default:
               return null;
         }

         boolean var4 = false;
         return var4;
      }

      public final String getWay() {
         return this.way;
      }

      public final String sentence() {
         List var4 = CollectionsKt.listOfNotNull(
            new String[]{sentence$ex$1(this.extraL, this.getKindLeft(), "left"), sentence$ex$1(this.extraR, this.getKindRight(), "right")}
         );
         StringBuilder var3;
         if (this.lane > 0) {
            int var1 = this.lane;
            int var2 = this.through;
            var3 = new StringBuilder().append("Lane ").append(var1).append(" of ").append(var2);
         } else {
            int var5 = this.through;
            String var6;
            if (this.through == 1) {
               var6 = "";
            } else {
               var6 = "s";
            }

            var3 = new StringBuilder().append(var5).append(" lane").append(var6);
         }

         String var7 = var3.toString();
         if (!var4.isEmpty()) {
            String var8 = CollectionsKt.joinToString$default(var4, " and ", null, null, 0, null, null, 62, null);
            var7 = var7 + ", plus " + var8;
         }

         return var7;
      }

      public final String text() {
         List var5 = CollectionsKt.listOfNotNull(
            new String[]{text$ex(this.extraL, this.getKindLeft(), "left"), text$ex(this.extraR, this.getKindRight(), "right")}
         );
         StringBuilder var3;
         if (this.lane > 0) {
            int var2 = this.lane;
            int var1 = this.getTotal();
            var3 = new StringBuilder().append("lane ").append(var2).append(" of ").append(var1);
         } else {
            int var7 = this.getTotal();
            var3 = new StringBuilder().append(var7).append(" lanes");
         }

         String var4 = var3.toString();
         String var6 = this.way;
         int var8 = var6.hashCode();
         String var11 = "";
         switch (var8) {
            case 110182:
               if (var6.equals("one")) {
                  var11 = ", one-way";
               }
               break;
            case 115276:
               if (var6.equals("two")) {
                  if (this.otherWay > 0) {
                     var8 = this.otherWay;
                     var11 = " (" + var8 + " the other way)";
                  }

                  var11 = ", two-way" + var11;
               }
               break;
            case 1674318603:
               if (var6.equals("divided")) {
                  var11 = ", divided highway";
               }
         }

         if (!var5.isEmpty()) {
            var8 = this.through;
            String var12 = CollectionsKt.joinToString$default(var5, " + ", null, null, 0, null, null, 62, null);
            var4 = var4 + " (" + var8 + " + " + var12 + ")";
         }

         return var4 + var11;
      }
   }

   public static final class Road {
      public static final LaneFixDialog.Road.Companion Companion = new LaneFixDialog.Road.Companion(null);
      private int other;
      private List<Character> types;
      private String way;
      private int you;

      public Road(List<Character> var1, int var2, String var3, int var4) {
         Intrinsics.checkNotNullParameter(var1, "types");
         Intrinsics.checkNotNullParameter(var3, "way");
         super();
         this.types = var1;
         this.you = var2;
         this.way = var3;
         this.other = var4;
      }

      private final void fixGroups() {
         int var2 = this.getEL();
         if (var2 > 1) {
            for (int var1 = 0; var1 < var2; var1++) {
               this.types.set(var1, this.types.get(var2 - 1));
            }
         }

         var2 = this.getER();
         if (var2 > 1) {
            int var4 = this.types.size() - var2;

            for (int var3 = this.types.size(); var4 < var3; var4++) {
               this.types.set(var4, this.types.get(this.types.size() - var2));
            }
         }
      }

      private static final String result$k(Character var0) {
         String var1;
         if (var0 != null && var0 == 'x') {
            var1 = "exit";
         } else if (var0 != null && var0 == 'm') {
            var1 = "merge";
         } else {
            var1 = "";
         }

         return var1;
      }

      public final void add(int var1) {
         if (this.types.size() < 8) {
            List var2 = this.types;
            if (var1 < 0) {
               var2.add(0, 'n');
               if (this.you >= 0) {
                  this.you++;
               }
            } else {
               var2.add('n');
            }

            this.fixGroups();
         }
      }

      public final void addExtra(char var1, int var2) {
         if (var2 < 0) {
            if (this.getEL() >= 2) {
               return;
            }

            this.types.add(0, var1);
            if (this.you >= 0) {
               this.you++;
            }
         } else {
            if (this.getER() >= 2) {
               return;
            }

            this.types.add(var1);
         }

         this.fixGroups();
      }

      public final void cycle(int var1) {
         if (this.taggable(var1)) {
            int var5 = this.getEL();
            boolean var4 = true;
            boolean var3 = var4;
            if (var1 != var5 - 1) {
               if (var1 == this.getEL() && this.getEL() < 2 && this.getThrough() > 1) {
                  var3 = var4;
               } else {
                  var3 = false;
               }
            }

            char var2;
            switch (this.types.get(var1)) {
               case 'n':
                  var2 = 'x';
                  break;
               case 'x':
                  var2 = 'm';
                  break;
               default:
                  var2 = 'n';
            }

            if (var2 == 'n') {
               this.types.set(var1, 'n');
            } else {
               this.types.set(var1, var2);
               if (var3) {
                  for (int var7 = 0; var7 < var1; var7++) {
                     this.types.set(var7, var2);
                  }
               } else {
                  var1++;

                  for (int var8 = this.types.size(); var1 < var8; var1++) {
                     this.types.set(var1, var2);
                  }
               }

               this.fixGroups();
            }
         }
      }

      public final int getEL() {
         Iterable var3 = this.types;
         ArrayList var2 = new ArrayList();

         for (Object var4 : var3) {
            boolean var1;
            if ((Character)var4 != 'n') {
               var1 = true;
            } else {
               var1 = false;
            }

            if (!var1) {
               break;
            }

            var2.add(var4);
         }

         return var2.size();
      }

      public final int getER() {
         List var3 = this.types;
         List var2;
         if (var3.isEmpty()) {
            var2 = CollectionsKt.emptyList();
         } else {
            ListIterator var5 = var3.listIterator(var3.size());

            while (var5.hasPrevious()) {
               boolean var1;
               if ((Character)var5.previous() != 'n') {
                  var1 = 1;
               } else {
                  var1 = 0;
               }

               if (!var1) {
                  var5.next();
                  var1 = var3.size() - var5.nextIndex();
                  if (var1 == 0) {
                     var2 = CollectionsKt.emptyList();
                  } else {
                     ArrayList var6 = new ArrayList(var1);

                     while (var5.hasNext()) {
                        var6.add(var5.next());
                     }

                     var2 = var6;
                  }

                  return var2.size();
               }
            }

            var2 = CollectionsKt.toList(var3);
         }

         return var2.size();
      }

      public final int getOther() {
         return this.other;
      }

      public final int getThrough() {
         return this.types.size() - this.getEL() - this.getER();
      }

      public final List<Character> getTypes() {
         return this.types;
      }

      public final String getWay() {
         return this.way;
      }

      public final int getYou() {
         return this.you;
      }

      public final void remove(int var1) {
         if (this.types.size() > 1) {
            int var2 = -1;
            byte var3 = 0;
            if (var1 < 0) {
               this.types.remove(0);
               if (this.you != 0) {
                  var2 = this.you - 1;
               }

               this.you = var2;
            } else {
               if (this.you == this.types.size() - 1) {
                  this.you = -1;
               }

               this.types.remove(this.types.size() - 1);
            }

            if (this.getThrough() == 0) {
               List var4 = this.types;
               if (var1 < 0) {
                  var1 = var3;
               } else {
                  var1 = this.types.size() - 1;
               }

               var4.set(var1, 'n');
            }
         }
      }

      public final LaneFixDialog.Result result(boolean var1) {
         int var3;
         Character var8;
         Object var9;
         label62: {
            var8 = CollectionsKt.firstOrNull(this.types);
            var9 = null;
            var3 = 0;
            if (var8 != null) {
               boolean var2;
               if (var8 != 'n') {
                  var2 = true;
               } else {
                  var2 = false;
               }

               if (var2) {
                  break label62;
               }
            }

            var8 = null;
         }

         Character var11 = CollectionsKt.lastOrNull(this.types);
         Character var10 = (Character)var9;
         if (var11 != null) {
            boolean var12;
            if (var11 != 'n') {
               var12 = true;
            } else {
               var12 = false;
            }

            var10 = (Character)var9;
            if (var12) {
               var10 = var11;
            }
         }

         if (var8 != null) {
            var9 = result$k(var8);
         } else {
            var9 = result$k(var10);
         }

         String var16;
         if (var8 != null && var10 != null && !Intrinsics.areEqual(var10, var8)) {
            var16 = result$k(var10);
         } else {
            var16 = "";
         }

         int var5 = this.getThrough();
         int var4 = this.getEL();
         int var6 = this.getER();
         int var13 = this.types.size();
         int var7 = this.you;
         boolean var14;
         if (var7 >= 0 && var7 < var13) {
            var14 = 1;
         } else {
            var14 = 0;
         }

         if (var14) {
            var14 = this.you + 1;
         } else {
            var14 = 0;
         }

         String var18 = this.way;
         if (Intrinsics.areEqual(this.way, "two")) {
            var3 = this.other;
         }

         return new LaneFixDialog.Result(var5, var4, var6, (String)var9, var14, var1, var18, var3, var16);
      }

      public final void setOther(int var1) {
         this.other = var1;
      }

      public final void setTypes(List<Character> var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.types = var1;
      }

      public final void setWay(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.way = var1;
      }

      public final void setYou(int var1) {
         this.you = var1;
      }

      public final boolean taggable(int var1) {
         boolean var4 = false;
         boolean var2;
         if (var1 >= 0 && var1 < this.types.size()) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         if (!var2) {
            return false;
         } else {
            var2 = this.getEL();
            int var3 = this.getER();
            boolean var7;
            if (var1 == var2 - 1 || var1 == var2 && var2 < 2 && this.getThrough() > 1) {
               var7 = true;
            } else {
               var7 = false;
            }

            boolean var5;
            if (var1 == this.types.size() - var3 || var1 == this.types.size() - var3 - 1 && var3 < 2 && this.getThrough() > 1) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var7 || var5) {
               var4 = true;
            }

            return var4;
         }
      }

      public static final class Companion {
         private Companion() {
         }

         // $VF: synthetic method
         public Companion(DefaultConstructorMarker var1) {
            this();
         }

         private static final char of$c(String var0) {
            char var1;
            if (Intrinsics.areEqual(var0, "exit")) {
               var1 = 'x';
            } else {
               var1 = 'm';
            }

            return var1;
         }

         public final LaneFixDialog.Road of(LaneFixDialog.Result var1, int var2) {
            Character var7 = 'n';
            int var3 = -1;
            boolean var4 = false;
            if (var1 != null && var1.getThrough() > 0) {
               int var5 = var1.getExtraL();
               ArrayList var8 = new ArrayList(var5);

               for (int var12 = 0; var12 < var5; var12++) {
                  var8.add(of$c(var1.getKindLeft()));
               }

               var var22 = var8;
               var5 = RangesKt.coerceIn(var1.getThrough(), 1, 6);
               ArrayList var9 = new ArrayList(var5);

               for (int var13 = 0; var13 < var5; var13++) {
                  var9.add(var7);
               }

               Collection var23 = CollectionsKt.plus(var22, var9);
               var5 = var1.getExtraR();
               ArrayList var20 = new ArrayList(var5);

               for (int var14 = 0; var14 < var5; var14++) {
                  var20.add(of$c(var1.getKindRight()));
               }

               List var24 = CollectionsKt.plus(var23, var20);
               List var21 = CollectionsKt.toMutableList(var24);
               var5 = var24.size();
               int var6 = var1.getLane();
               boolean var15 = var4;
               if (1 <= var6) {
                  var15 = var4;
                  if (var6 <= var5) {
                     var15 = true;
                  }
               }

               if (var15) {
                  var3 = var1.getLane() - 1;
               }

               return new LaneFixDialog.Road(var21, var3, var1.getWay(), var1.getOtherWay());
            } else {
               var3 = RangesKt.coerceIn(var2, 1, 6);
               ArrayList var10 = new ArrayList(var3);

               for (int var11 = 0; var11 < var3; var11++) {
                  var10.add(var7);
               }

               return new LaneFixDialog.Road(var10, -1, "", 0);
            }
         }
      }
   }

   public static final class RoadView extends View {
      private final Function0<Unit> changed;
      private final float dn;
      private final ArrayList<RectF> lanes;
      private final Paint p;
      private final LaneFixDialog.Road road;
      private final ArrayList<Pair<Integer, RectF>> tags;
      private final Paint txt;

      public RoadView(Context var1, LaneFixDialog.Road var2, Function0<Unit> var3) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var2, "road");
         Intrinsics.checkNotNullParameter(var3, "changed");
         super(var1);
         this.road = var2;
         this.changed = var3;
         this.dn = this.getResources().getDisplayMetrics().density;
         this.p = new Paint(1);
         Paint var4 = new Paint(1);
         var4.setTextAlign(Align.CENTER);
         var4.setTypeface(Typeface.DEFAULT_BOLD);
         this.txt = var4;
         this.lanes = new ArrayList<>();
         this.tags = new ArrayList<>();
      }

      private final void dashed(Canvas var1, float var2, float var3, int var4) {
         Paint var7 = new Paint(1);
         var7.setColor(var4);
         var7.setStrokeWidth(5 * this.dn);
         var7.setPathEffect((PathEffect)(new DashPathEffect(new float[]{18 * this.dn, 14 * this.dn}, 0.0F)));
         var7.setStyle(Style.STROKE);
         Path var6 = new Path();
         float var5 = 6;
         var6.moveTo(var2, this.dn * var5);
         var6.lineTo(var2, var3 - var5 * this.dn);
         var1.drawPath(var6, var7);
      }

      private final float getTagH() {
         return 34 * this.dn;
      }

      public final Function0<Unit> getChanged() {
         return this.changed;
      }

      public final LaneFixDialog.Road getRoad() {
         return this.road;
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         List var29 = this.road.getTypes();
         int var22 = var29.size();
         float var9 = this.getHeight() - this.getTagH() - 10 * this.dn;
         int var19;
         if (Intrinsics.areEqual(this.road.getWay(), "two")) {
            var19 = Math.max(1, this.road.getOther());
         } else {
            var19 = 0;
         }

         String var26 = this.road.getWay();
         String var27 = "divided";
         float var2;
         if (Intrinsics.areEqual(var26, "divided")) {
            var2 = 34 * this.dn;
         } else {
            var2 = 0.0F;
         }

         float var5 = this.getWidth();
         float var4 = 8;
         float var3 = this.dn;
         float var10 = Math.min(120 * this.dn, (var5 - var3 * var4 - var2) / (var22 + var19 * 0.8F));
         float var7 = 0.8F * var10;
         float var6 = var19 * var7 + var2 + var22 * var10;
         var3 = this.getWidth();
         float var11 = 2;
         var3 = (var3 - var6) / var11;
         this.p.setStyle(Style.FILL);
         this.p.setColor(Color.rgb(42, 47, 56));
         var5 = 6;
         RectF var56 = new RectF(var3 - this.dn * var5, 0.0F, var3 + var6 + this.dn * var5, var9);
         var6 = 14;
         var1.drawRoundRect(var56, this.dn * var6, this.dn * var6, this.p);

         for (int var20 = 0; var20 < var19; var20++) {
            RectF var57 = new RectF(var3, 0.0F, var3 + var7, var9);
            this.txt.setColor(Color.rgb(96, 102, 116));
            this.txt.setTextSize(34 * this.dn);
            var1.drawText("↓", var57.centerX(), var57.centerY(), this.txt);
            if (var20 > 0) {
               this.dashed(var1, var3, var9, Color.rgb(150, 156, 168));
            }

            var3 += var7;
         }

         if (var19 > 0) {
            if (this.road.getOther() == 0) {
               this.txt.setTextSize(13 * this.dn);
               this.txt.setColor(Color.rgb(140, 146, 160));
               var1.drawText("other way", var3 - var19 * var7 / var11, var9 - 16 * this.dn, this.txt);
            }

            this.p.setColor(Color.rgb(233, 194, 59));
            var7 = 5;
            var1.drawRect(var3 - this.dn * var7, 0.0F, var3 - this.dn * var11, var9, this.p);
            var1.drawRect(var3 + var11 * this.dn, 0.0F, var3 + var7 * this.dn, var9, this.p);
         }

         if (var2 > 0.0F) {
            this.p.setColor(Color.rgb(58, 70, 52));
            var1.drawRect(var3, 0.0F, var3 + var2, var9, this.p);
            this.txt.setTextSize(12 * this.dn);
            this.txt.setColor(Color.rgb(150, 170, 140));
            var1.save();
            var1.rotate(-90.0F, var2 / var11 + var3, var9 / var11);
            var1.drawText("barrier / median", var2 / var11 + var3, var9 / var11 + 4 * this.dn, this.txt);
            var1.restore();
            var3 += var2;
         }

         this.lanes.clear();
         this.tags.clear();
         var2 = var3;
         int var21 = 0;
         var19 = var22;

         while (true) {
            short var23 = 240;
            if (var21 >= var19) {
               Paint var68 = this.p;
               if (Intrinsics.areEqual(this.road.getWay(), var27)) {
                  var19 = Color.rgb(233, 194, 59);
               } else if (var19 > 0) {
                  var19 = 0;
               } else {
                  var19 = Color.rgb(240, 240, 240);
               }

               var68.setColor(var19);
               if (var19 == 0) {
                  var4 = 3;
                  var1.drawRect(var3 - this.dn * var4, 0.0F, var3 + var4 * this.dn, var9, this.p);
               }

               this.p.setColor(Color.rgb(240, 240, 240));
               var3 = 3;
               var1.drawRect(var2 - this.dn * var3, 0.0F, var2 + var3 * this.dn, var9, this.p);
               return;
            }

            RectF var58 = new RectF(var2, 0.0F, var2 + var10, var9);
            this.lanes.add(var58);
            boolean var49;
            if ((Character)var29.get(var21) != 'n') {
               var49 = 1;
            } else {
               var49 = 0;
            }

            RectF var28;
            if (!var49) {
               var28 = var58;
            } else {
               this.p.setColor(Color.rgb(58, 51, 38));
               var1.drawRect(var58, this.p);
               this.p.setColor(Color.rgb(47, 42, 32));
               this.p.setStrokeWidth(7 * this.dn);
               var1.save();
               var1.clipRect(var58);

               for (float var39 = -var9; var39 < var10; var39 += 22 * this.dn) {
                  var1.drawLine(var58.left + var39, var9, var58.left + var39 + var9, 0.0F, this.p);
               }

               var1.restore();
               var28 = var58;
            }

            if (var21 == this.road.getYou()) {
               this.p.setColor(Color.argb(46, 111, 227, 161));
               var1.drawRect(var28, this.p);
               this.p.setStyle(Style.STROKE);
               this.p.setStrokeWidth(4 * this.dn);
               this.p.setColor(Color.rgb(111, 227, 161));
               var1.drawRoundRect(
                  new RectF(var28.left + this.dn * var11, this.dn * var11, var28.right - this.dn * var11, var9 - this.dn * var11),
                  var4 * this.dn,
                  this.dn * var4,
                  this.p
               );
               this.p.setStyle(Style.FILL);
               float var8 = Math.min(46 * this.dn, 0.42F * var10);
               float var13 = 1.65F * var8;
               float var12 = var28.centerX();
               var7 = 0.55F * var9;
               this.p.setColor(Color.rgb(111, 227, 161));
               RectF var59 = new RectF(var12 - var8 / var11, var7 - var13 / var11, var12 + var8 / var11, var7 + var13 / var11);
               float var14 = 12;
               var1.drawRoundRect(var59, this.dn * var14, var14 * this.dn, this.p);
               this.p.setColor(Color.rgb(11, 61, 35));
               float var18 = var8 / var11;
               float var16 = 7;
               var14 = this.dn;
               float var17 = var13 / var11;
               float var15 = 9;
               RectF var60 = new RectF(
                  var12 - var18 + var14 * var16,
                  var7 - var17 + this.dn * var15,
                  var8 / var11 + var12 - var16 * this.dn,
                  var7 - var13 / var11 + var15 * this.dn + 0.24F * var13
               );
               var7 = 5;
               var1.drawRoundRect(var60, this.dn * var7, var7 * this.dn, this.p);
            }

            boolean var51;
            if (var21 < this.road.getEL()) {
               var51 = 1;
            } else {
               var51 = 0;
            }

            if (!var49) {
               var26 = "↑";
            } else if (var29.get(var21) == 'x' ? var51 : !var51) {
               var26 = "↖";
            } else {
               var26 = "↗";
            }

            this.txt.setTextSize(40 * this.dn);
            Paint var30 = this.txt;
            if (var49) {
               var51 = Color.rgb(240, 181, 74);
            } else {
               var51 = Color.rgb(142, 151, 168);
            }

            var30.setColor(var51);
            float var44;
            if (var21 != this.road.getYou()) {
               var7 = var28.centerX();
               var44 = 0.42F;
            } else {
               var7 = var28.centerX();
               var44 = 0.22F;
            }

            var1.drawText(var26, var7, var44 * var9, this.txt);
            this.txt.setTextSize(20 * this.dn);
            Paint var62 = this.txt;
            if (var21 == this.road.getYou()) {
               var51 = Color.rgb(111, 227, 161);
            } else {
               var51 = Color.rgb(207, 214, 228);
            }

            var62.setColor(var51);
            if (var21 == this.road.getYou()) {
               var26 = var21 + 1 + " · you";
            } else {
               var26 = String.valueOf(var21 + 1);
            }

            var1.drawText(var26, var28.centerX(), var9 - 18 * this.dn, this.txt);
            if (var49) {
               this.txt.setTextSize(15 * this.dn);
               this.txt.setColor(Color.rgb(240, 181, 74));
               if ((Character)var29.get(var21) == 'x') {
                  var26 = "EXIT";
               } else {
                  var26 = "MERGE";
               }

               var1.drawText(var26, var28.centerX(), 24 * this.dn, this.txt);
            }

            if (var21 > 0) {
               if ((Character)var29.get(var21) == (Character)var29.get(var21 - 1) || !var49 && (Character)var29.get(var21 - 1) == 'n') {
                  var51 = Color.rgb(217, 221, 230);
               } else {
                  var51 = -1;
               }

               this.dashed(var1, var2, var9, var51);
            }

            if (this.road.taggable(var21)) {
               var28 = new RectF(var28.left + this.dn * var5, this.dn * var4 + var9, var28.right - this.dn * var5, this.dn * var4 + var9 + this.getTagH());
               this.tags.add(TuplesKt.to(var21, var28));
               Paint var65 = this.p;
               byte var24;
               byte var25;
               byte var55;
               if (var49) {
                  var24 = 72;
                  var25 = 26;
                  var55 = 96;
               } else {
                  var24 = 54;
                  var25 = 66;
                  var55 = 48;
               }

               var65.setColor(Color.rgb(var55, var24, var25));
               var7 = 9;
               var1.drawRoundRect(var28, this.dn * var7, var7 * this.dn, this.p);
               this.txt.setTextSize(this.dn * var6);
               Paint var66 = this.txt;
               if (var49) {
                  var49 = Color.rgb(240, 181, 74);
               } else {
                  var49 = Color.rgb(200, 206, 218);
               }

               var66.setColor(var49);
               switch (var29.get(var21)) {
                  case 'm':
                     var26 = "MERGE ▸";
                     break;
                  case 'x':
                     var26 = "EXIT ▸";
                     break;
                  default:
                     var26 = "normal ▸";
               }

               var1.drawText(var26, var28.centerX(), var28.centerY() + 5 * this.dn, this.txt);
            }

            var2 += var10;
            var21++;
         }
      }

      protected void onMeasure(int var1, int var2) {
         this.setMeasuredDimension(MeasureSpec.getSize(var1), (int)(300 * this.dn));
      }

      public boolean onTouchEvent(MotionEvent var1) {
         Intrinsics.checkNotNullParameter(var1, "e");
         if (var1.getAction() == 0) {
            return true;
         } else if (var1.getAction() != 1) {
            return false;
         } else {
            Iterator var4 = this.tags.iterator();
            Intrinsics.checkNotNullExpressionValue(var4, "iterator(...)");

            while (var4.hasNext()) {
               Object var5 = var4.next();
               Intrinsics.checkNotNullExpressionValue(var5, "next(...)");
               var5 = (Pair)var5;
               int var2 = ((Number)var5.component1()).intValue();
               if (((RectF)var5.component2()).contains(var1.getX(), var1.getY())) {
                  this.road.cycle(var2);
                  this.changed.invoke();
                  this.invalidate();
                  return true;
               }
            }

            var4 = this.lanes.iterator();
            int var7 = 0;

            int var3;
            do {
               var3 = var7;
               if (!var4.hasNext()) {
                  return false;
               }

               var7++;
            } while (!((RectF)var4.next()).contains(var1.getX(), var1.getY()));

            LaneFixDialog.Road var6 = this.road;
            if (this.road.getYou() == var3) {
               var3 = -1;
            }

            var6.setYou(var3);
            this.changed.invoke();
            this.invalidate();
            this.performClick();
            return true;
         }
      }

      public boolean performClick() {
         super.performClick();
         return true;
      }
   }
}
