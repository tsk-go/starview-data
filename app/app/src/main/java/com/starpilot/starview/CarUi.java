package com.starpilot.starview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class CarUi {
   private static final int ACCENT = Color.rgb(111, 227, 161);
   private static final int BG = Color.rgb(11, 13, 18);
   private static final int CARD = Color.rgb(24, 27, 34);
   private static final int CARD_ON = Color.rgb(30, 38, 47);
   public static final CarUi.Companion Companion = new CarUi.Companion(null);
   public static final float DESC = 15.0F;
   private static final int DIM = Color.rgb(150, 160, 180);
   private static final int FRAME = Color.rgb(17, 20, 25);
   public static final float HEADER = 14.0F;
   private static final int PAGE = Color.rgb(34, 50, 80);
   private static final int PAGE_DIV = Color.rgb(48, 65, 95);
   private static final int PAGE_EDGE = Color.rgb(127, 180, 255);
   public static final int ROW_MIN = 76;
   private static final int SECTION = Color.rgb(30, 35, 44);
   private static final int STRIP = Color.rgb(5, 6, 8);
   private static final int TAB_SEP = Color.rgb(38, 43, 53);
   private static final int TAB_TEXT = Color.rgb(154, 163, 181);
   private static final int TEXT = -1;
   public static final float TITLE = 21.0F;
   private static final int WARN = Color.rgb(255, 170, 60);
   private Function1<? super String, SettingIcons.Icon> autoIcon;
   private final int card;
   private final Context ctx;
   private final float d;
   private final boolean flat;
   private final int gap;

   // $VF: synthetic method
   public static void $r8$lambda$2o3rF0YAwaPZmBhR7cNUS6JUE_g(Function0 var0, View var1) {
      button$lambda$31$lambda$30(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$AUtg_yPeLdyEgUajfFYVdpMPMs0/* $VF was: $r8$lambda$AUtg_yPeLdyEgUajfFYVdpMPMs0*/(Function0 var0, View var1) {
      stepper$lambda$33$lambda$32(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Hj57XFusv3EElRlY3Xi7F1swsxA(Function0 var0, View var1) {
      iconTile$lambda$47$lambda$46(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JS939idijTplSbYZgRvy5_fk5HI/* $VF was: $r8$lambda$JS939idijTplSbYZgRvy5_fk5HI*/(
      ArrayList var0, CarUi var1, int var2, int var3, int var4, boolean var5, int var6
   ) {
      return browserTabs$lambda$12(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static void $r8$lambda$aYTpzsw4pW7_mXfVCkCy_L0jBP4(Function1 var0, int var1, View var2) {
      browserTabs$lambda$7$lambda$6(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$iTWoG1rNAAMe48gLhjnP1Bmgyaw(Function1 var0, int var1, View var2) {
      chips$lambda$36$lambda$35(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vOX9hdtx7lXP1A1z4bnQOuAaCWw(ArrayList var0, CarUi var1, int var2) {
      return chips$lambda$38(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$xTOZ_7qu7lyNPorhnUzlQVGGBAQ/* $VF was: $r8$lambda$xTOZ_7qu7lyNPorhnUzlQVGGBAQ*/(Function0 var0, View var1) {
      tile$lambda$43$lambda$42(var0, var1);
   }

   public CarUi(Context var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      this.ctx = var1;
      this.flat = var2;
      int var3;
      if (this.flat) {
         var3 = PAGE;
      } else {
         var3 = CARD;
      }

      this.card = var3;
      byte var4;
      if (this.flat) {
         var4 = 0;
      } else {
         var4 = 8;
      }

      this.gap = var4;
      this.d = this.ctx.getResources().getDisplayMetrics().density;
   }

   // $VF: synthetic method
   public CarUi(Context var1, boolean var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      this(var1, var2);
   }

   private static final Unit browserTabs$lambda$12(ArrayList var0, CarUi var1, int var2, int var3, int var4, boolean var5, int var6) {
      Iterator var12 = var0.iterator();

      for (int var8 = 0; var12.hasNext(); var8++) {
         TextView var11 = (TextView)var12.next();
         boolean var9;
         if (var8 == var6) {
            var9 = true;
         } else {
            var9 = false;
         }

         Drawable var15;
         if (var9) {
            LayerDrawable var10 = new LayerDrawable(
               (Drawable[])(new GradientDrawable[]{var1.corners(var2, var3, var3, 0, 0), var1.corners(var4, var3, var3, 0, 0)})
            );
            var10.setLayerInset(1, 0, var1.dp(3), 0, 0);
            var15 = (Drawable)var10;
         } else if (var8 < var0.size() - 1 && var8 + 1 != var6) {
            GradientDrawable var16 = new GradientDrawable();
            var16.setColor(TAB_SEP);
            Unit var13 = Unit.INSTANCE;
            LayerDrawable var17 = new LayerDrawable((Drawable[])(new GradientDrawable[]{var16}));
            var17.setLayerInset(0, 0, 0, 0, 0);
            var17.setLayerGravity(0, 8388629);
            var17.setLayerWidth(0, var1.dp(1));
            byte var7;
            if (var5) {
               var7 = 22;
            } else {
               var7 = 18;
            }

            var17.setLayerHeight(0, var1.dp(var7));
            var15 = (Drawable)var17;
         } else {
            var15 = null;
         }

         var11.setBackground(var15);
         int var14;
         if (var9) {
            if (var5) {
               var14 = ACCENT;
            } else {
               var14 = TEXT;
            }
         } else {
            var14 = TAB_TEXT;
         }

         var11.setTextColor(var14);
         Typeface var18;
         if (var9) {
            var18 = Typeface.DEFAULT_BOLD;
         } else {
            var18 = Typeface.DEFAULT;
         }

         var11.setTypeface(var18);
      }

      return Unit.INSTANCE;
   }

   private static final void browserTabs$lambda$7$lambda$6(Function1 var0, int var1, View var2) {
      var0.invoke(var1);
   }

   // $VF: synthetic method
   public static TextView button$default(CarUi var0, String var1, boolean var2, Function0 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      return var0.button(var1, var2, var3);
   }

   private static final void button$lambda$31$lambda$30(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final void chips$lambda$36$lambda$35(Function1 var0, int var1, View var2) {
      var0.invoke(var1);
   }

   private static final Unit chips$lambda$38(ArrayList var0, CarUi var1, int var2) {
      Iterator var8 = var0.iterator();
      int var3 = 0;

      while (var8.hasNext()) {
         int var4 = 1;
         int var6 = var3 + 1;
         TextView var7 = (TextView)var8.next();
         boolean var10;
         if (var3 == var2) {
            var10 = (boolean)var4;
         } else {
            var10 = false;
         }

         if (var10) {
            var4 = CARD_ON;
         } else {
            var4 = CARD;
         }

         int var5;
         if (var10) {
            var5 = ACCENT;
         } else {
            var5 = Color.rgb(50, 55, 66);
         }

         var7.setBackground((Drawable)var1.rounded(var4, 12, var5));
         if (var10) {
            var4 = ACCENT;
         } else {
            var4 = TEXT;
         }

         var7.setTextColor(var4);
         Typeface var9;
         if (var10) {
            var9 = Typeface.DEFAULT_BOLD;
         } else {
            var9 = Typeface.DEFAULT;
         }

         var7.setTypeface(var9);
         var3 = var6;
      }

      return Unit.INSTANCE;
   }

   private static final void iconTile$lambda$47$lambda$46(Function0 var0, View var1) {
      var0.invoke();
   }

   // $VF: synthetic method
   public static LayoutParams lp$default(CarUi var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.gap;
      }

      return var0.lp(var1, var2);
   }

   // $VF: synthetic method
   public static GradientDrawable rounded$default(CarUi var0, int var1, int var2, Integer var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 14;
      }

      if ((var4 & 4) != 0) {
         var3 = null;
      }

      return var0.rounded(var1, var2, var3);
   }

   // $VF: synthetic method
   public static Pair row$default(CarUi var0, String var1, String var2, int var3, View var4, String var5, int var6, Object var7) {
      if ((var6 & 2) != 0) {
         var2 = null;
      }

      if ((var6 & 4) != 0) {
         var3 = 0;
      }

      if ((var6 & 8) != 0) {
         var4 = null;
      }

      if ((var6 & 16) != 0) {
         var5 = null;
      }

      return var0.row(var1, var2, var3, var4, var5);
   }

   private static final void stepper$lambda$33$lambda$32(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final void tile$lambda$43$lambda$42(Function0 var0, View var1) {
      var0.invoke();
   }

   public final Switch bigSwitch() {
      Switch var1 = new Switch(this.ctx);
      var1.setScaleX(1.45F);
      var1.setScaleY(1.45F);
      var1.setPadding(this.dp(14), 0, this.dp(14), 0);
      return var1;
   }

   public final Pair<View, Function1<Integer, Unit>> browserTabs(List<String> var1, int var2, Function1<? super Integer, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "labels");
      Intrinsics.checkNotNullParameter(var3, "onPick");
      boolean var10;
      if (var2 == 1) {
         var10 = true;
      } else {
         var10 = false;
      }

      ArrayList var12 = new ArrayList();
      ViewGroup var30;
      if (var10) {
         LinearLayout var11 = new LinearLayout(this.ctx);
         var11.setOrientation(0);
         var11.setPadding(this.dp(8), this.dp(8), this.dp(8), 0);
         var30 = (ViewGroup)var11;
      } else {
         FlowRow var31 = new FlowRow(this.ctx, this.dp(2), this.dp(4));
         var31.setPadding(this.dp(14), this.dp(12), this.dp(14), 0);
         var30 = var31;
      }

      Iterator var13 = var1.iterator();
      var2 = 0;

      while (var13.hasNext()) {
         int var6 = var2 + 1;
         String var16 = (String)var13.next();
         TextView var14 = new TextView(this.ctx);
         var14.setText(var16);
         float var4;
         if (var10) {
            var4 = 18.0F;
         } else {
            var4 = 15.0F;
         }

         var14.setTextSize(var4);
         var14.setGravity(17);
         var14.setTextColor(TAB_TEXT);
         var14.setSingleLine(true);
         byte var22;
         if (var10) {
            var22 = 20;
         } else {
            var22 = 16;
         }

         int var7 = this.dp(var22);
         if (var10) {
            var22 = 14;
         } else {
            var22 = 10;
         }

         int var8 = this.dp(var22);
         if (var10) {
            var22 = 20;
         } else {
            var22 = 16;
         }

         int var9 = this.dp(var22);
         if (var10) {
            var22 = 15;
         } else {
            var22 = 11;
         }

         var14.setPadding(var7, var8, var9, this.dp(var22));
         var14.setOnClickListener(new CarUi$$ExternalSyntheticLambda4(var3, var2));
         var12.add(var14);
         View var32 = (View)var14;
         android.view.ViewGroup.LayoutParams var18;
         if (var10) {
            LayoutParams var15 = new LayoutParams(-2, -2);
            var15.rightMargin = this.dp(2);
            Unit var17 = Unit.INSTANCE;
            var18 = (android.view.ViewGroup.LayoutParams)var15;
         } else {
            var18 = new android.view.ViewGroup.LayoutParams(-2, -2);
         }

         var30.addView(var32, var18);
         var2 = var6;
      }

      if (var10) {
         var2 = SECTION;
      } else {
         var2 = PAGE;
      }

      int var28;
      if (var10) {
         var28 = ACCENT;
      } else {
         var28 = PAGE_EDGE;
      }

      byte var29;
      if (var10) {
         var29 = 14;
      } else {
         var29 = 10;
      }

      CarUi$$ExternalSyntheticLambda5 var27 = new CarUi$$ExternalSyntheticLambda5(var12, this, var28, var29, var2, var10);
      View var20;
      if (var10) {
         HorizontalScrollView var19 = new HorizontalScrollView(this.ctx);
         var19.setHorizontalScrollBarEnabled(false);
         var19.setFillViewport(true);
         var19.setBackground((Drawable)this.corners(STRIP, 14, 14, 0, 0));
         var19.addView((View)var30);
         var20 = (View)var19;
      } else {
         var30.setBackgroundColor(SECTION);
         var20 = (View)var30;
      }

      return TuplesKt.to(var20, var27);
   }

   public final TextView button(String var1, boolean var2, Function0<Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "label");
      Intrinsics.checkNotNullParameter(var3, "onClick");
      TextView var5 = new TextView(this.ctx);
      var5.setText(var1);
      var5.setTextSize(18.0F);
      int var4;
      if (var2) {
         var4 = BG;
      } else {
         var4 = TEXT;
      }

      var5.setTextColor(var4);
      var5.setTypeface(Typeface.DEFAULT_BOLD);
      var5.setGravity(17);
      var5.setMinWidth(this.dp(120));
      var5.setMinHeight(this.dp(56));
      var5.setPadding(this.dp(22), this.dp(10), this.dp(22), this.dp(10));
      if (var2) {
         var4 = ACCENT;
      } else {
         var4 = CARD_ON;
      }

      Integer var6;
      if (var2) {
         var6 = null;
      } else {
         var6 = Color.rgb(60, 66, 78);
      }

      var5.setBackground((Drawable)this.rounded(var4, 12, var6));
      var5.setOnClickListener(new CarUi$$ExternalSyntheticLambda3(var3));
      return var5;
   }

   public final Pair<LinearLayout, Function1<Integer, Unit>> chips(List<String> var1, Function1<? super Integer, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "labels");
      Intrinsics.checkNotNullParameter(var2, "onPick");
      LinearLayout var5 = new LinearLayout(this.ctx);
      var5.setOrientation(0);
      ArrayList var7 = new ArrayList();
      Iterator var6 = var1.iterator();
      int var3 = 0;

      while (var6.hasNext()) {
         int var4 = var3 + 1;
         String var9 = (String)var6.next();
         TextView var8 = new TextView(this.ctx);
         var8.setText(var9);
         var8.setTextSize(17.0F);
         var8.setGravity(17);
         var8.setMinHeight(this.dp(54));
         var8.setPadding(this.dp(12), this.dp(8), this.dp(12), this.dp(8));
         var8.setOnClickListener(new CarUi$$ExternalSyntheticLambda1(var2, var3));
         var7.add(var8);
         View var13 = (View)var8;
         LayoutParams var12 = new LayoutParams(0, -2, 1.0F);
         if (var3 < var1.size() - 1) {
            var3 = this.dp(8);
         } else {
            var3 = 0;
         }

         var12.rightMargin = var3;
         Unit var10 = Unit.INSTANCE;
         var5.addView(var13, (android.view.ViewGroup.LayoutParams)var12);
         var3 = var4;
      }

      return TuplesKt.to(var5, new CarUi$$ExternalSyntheticLambda2(var7, this));
   }

   public final GradientDrawable corners(int var1, int var2, int var3, int var4, int var5) {
      GradientDrawable var6 = new GradientDrawable();
      var6.setColor(var1);
      var6.setCornerRadii(new float[]{this.dp(var2), this.dp(var2), this.dp(var3), this.dp(var3), this.dp(var4), this.dp(var4), this.dp(var5), this.dp(var5)});
      return var6;
   }

   public final int dp(int var1) {
      return (int)(var1 * this.d);
   }

   public final Function1<String, SettingIcons.Icon> getAutoIcon() {
      return this.autoIcon;
   }

   public final int getCard() {
      return this.card;
   }

   public final Context getCtx() {
      return this.ctx;
   }

   public final float getD() {
      return this.d;
   }

   public final boolean getFlat() {
      return this.flat;
   }

   public final int getGap() {
      return this.gap;
   }

   public final TextView header(String var1) {
      Intrinsics.checkNotNullParameter(var1, "text");
      TextView var2 = new TextView(this.ctx);
      var1 = var1.toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toUpperCase(...)");
      var2.setText(var1);
      var2.setTextColor(DIM);
      var2.setTextSize(14.0F);
      var2.setLetterSpacing(0.1F);
      var2.setPadding(this.dp(6), this.dp(22), 0, this.dp(8));
      return var2;
   }

   public final Pair<LinearLayout, TextView> iconTile(String var1, String var2, String var3, String var4, Function0<Unit> var5) {
      Intrinsics.checkNotNullParameter(var1, "label");
      Intrinsics.checkNotNullParameter(var3, "icon");
      Intrinsics.checkNotNullParameter(var4, "group");
      Intrinsics.checkNotNullParameter(var5, "onClick");
      TextView var6 = new TextView(this.ctx);
      CharSequence var7;
      if (var2 != null) {
         var7 = var2;
      } else {
         var7 = "";
      }

      var6.setText(var7);
      var6.setTextColor(DIM);
      var6.setTextSize(15.0F);
      var6.setMaxLines(2);
      var6.setEllipsize(TruncateAt.END);
      LinearLayout var8 = new LinearLayout(this.ctx);
      var8.setOrientation(1);
      var8.setBackground((Drawable)rounded$default(this, CARD, 20, null, 4, null));
      var8.setPadding(this.dp(20), this.dp(18), this.dp(18), this.dp(16));
      var8.addView(
         SvIcon.chip$default(SvIcon.INSTANCE, this.ctx, var3, var4, 58, null, 16, null),
         (android.view.ViewGroup.LayoutParams)(new LayoutParams(this.dp(58), this.dp(58)))
      );
      var8.addView(new View(this.ctx), (android.view.ViewGroup.LayoutParams)(new LayoutParams(1, 0, 1.0F)));
      TextView var9 = new TextView(this.ctx);
      var9.setText(var1);
      var9.setTextColor(TEXT);
      var9.setTextSize(21.0F);
      var9.setTypeface(Typeface.DEFAULT_BOLD);
      var9.setPadding(0, this.dp(10), 0, this.dp(2));
      var9.setSingleLine(true);
      var9.setEllipsize(TruncateAt.END);
      var8.addView((View)var9);
      var8.addView((View)var6);
      var8.setClickable(true);
      var8.setOnClickListener(new CarUi$$ExternalSyntheticLambda7(var5));
      return TuplesKt.to(var8, var6);
   }

   public final LayoutParams lp(int var1, int var2) {
      LayoutParams var3 = new LayoutParams(-1, -2);
      var3.bottomMargin = this.dp(var2);
      var3.leftMargin = this.dp(var1 * 26);
      return var3;
   }

   public final TextView note(String var1) {
      Intrinsics.checkNotNullParameter(var1, "text");
      TextView var2 = new TextView(this.ctx);
      var2.setText(var1);
      var2.setTextColor(DIM);
      var2.setTextSize(15.0F);
      var2.setPadding(this.dp(6), this.dp(4), this.dp(6), this.dp(10));
      return var2;
   }

   public final TextView railItem(String var1, Integer var2) {
      Intrinsics.checkNotNullParameter(var1, "label");
      TextView var3 = new TextView(this.ctx);
      var3.setText(var1);
      var3.setTextSize(18.0F);
      var3.setTextColor(TEXT);
      var3.setGravity(16);
      var3.setMinHeight(this.dp(66));
      var3.setPadding(this.dp(16), this.dp(8), this.dp(12), this.dp(8));
      if (var2 != null) {
         var3.setCompoundDrawablesRelativeWithIntrinsicBounds(var2, 0, 0, 0);
         var3.setCompoundDrawablePadding(this.dp(14));
         var3.setCompoundDrawableTintList(ColorStateList.valueOf(DIM));
      }

      var3.setBackground((Drawable)rounded$default(this, CARD, 0, null, 6, null));
      return var3;
   }

   public final TextView railItem(String var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var1, "label");
      Intrinsics.checkNotNullParameter(var2, "icon");
      Intrinsics.checkNotNullParameter(var3, "group");
      TextView var4 = this.railItem(var1, null);
      var4.setCompoundDrawablesRelativeWithIntrinsicBounds(SvIcon.INSTANCE.drawable(var2, SvIcon.INSTANCE.fg(var3), this.dp(26)), null, null, null);
      var4.setCompoundDrawablePadding(this.dp(14));
      return var4;
   }

   public final void railSelect(TextView var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "v");
      int var3 = 0;
      Drawable var7;
      if (var2) {
         LayerDrawable var4 = new LayerDrawable((Drawable[])(new GradientDrawable[]{this.corners(ACCENT, 14, 0, 0, 14), this.corners(FRAME, 14, 0, 0, 14)}));
         var4.setLayerInset(1, this.dp(4), 0, 0, 0);
         var7 = (Drawable)var4;
      } else {
         var7 = (Drawable)rounded$default(this, CARD, 0, null, 6, null);
      }

      var1.setBackground(var7);
      android.view.ViewGroup.LayoutParams var8 = var1.getLayoutParams();
      LayoutParams var9;
      if (var8 instanceof LayoutParams) {
         var9 = (LayoutParams & android.view.ViewGroup.LayoutParams)var8;
      } else {
         var9 = null;
      }

      if (var9 != null) {
         if (!var2) {
            var3 = this.dp(18);
         }

         if (var9.rightMargin != var3) {
            var9.rightMargin = var3;
            var1.setLayoutParams((android.view.ViewGroup.LayoutParams)var9);
         }
      }

      if (var2) {
         var3 = ACCENT;
      } else {
         var3 = TEXT;
      }

      var1.setTextColor(var3);
      Typeface var10;
      if (var2) {
         var10 = Typeface.DEFAULT_BOLD;
      } else {
         var10 = Typeface.DEFAULT;
      }

      var1.setTypeface(var10);
      if (var2) {
         var3 = ACCENT;
      } else {
         var3 = DIM;
      }

      var1.setCompoundDrawableTintList(ColorStateList.valueOf(var3));
   }

   public final GradientDrawable rounded(int var1, int var2, Integer var3) {
      GradientDrawable var4 = new GradientDrawable();
      var4.setColor(var1);
      var4.setCornerRadius(this.dp(var2));
      if (var3 != null) {
         var4.setStroke(this.dp(2), var3);
      }

      return var4;
   }

   public final Pair<LinearLayout, TextView> row(String var1, String var2, int var3, View var4, String var5) {
      LinearLayout var6;
      Intrinsics.checkNotNullParameter(var1, "title");
      var6 = new LinearLayout(this.ctx);
      var6.setOrientation(0);
      var6.setGravity(16);
      var6.setMinimumHeight(this.dp(76));
      var6.setPadding(this.dp(20), this.dp(14), this.dp(18), this.dp(14));
      var6.setBackground(this.rowBg());
      label37:
      if (var4 == null) {
         Function1 var13 = this.autoIcon;
         if (var13 != null) {
            SettingIcons.Icon var14 = (SettingIcons.Icon)var13.invoke(var1);
            if (var14 != null) {
               var4 = SvIcon.chip$default(SvIcon.INSTANCE, this.ctx, var14.getName(), var14.getGroup(), 46, null, 16, null);
               break label37;
            }
         }

         var4 = null;
      }

      if (var4 != null) {
         LayoutParams var7 = new LayoutParams(this.dp(46), this.dp(46));
         var7.rightMargin = this.dp(16);
         Unit var8 = Unit.INSTANCE;
         var6.addView(var4, (android.view.ViewGroup.LayoutParams)var7);
      }

      LinearLayout var15 = new LinearLayout(this.ctx);
      var15.setOrientation(1);
      if (var5 == null) {
         TextView var16 = new TextView(this.ctx);
         var16.setText(var1);
         var16.setTextColor(TEXT);
         var16.setTextSize(21.0F);
         var15.addView((View)var16);
      } else {
         LinearLayout var19 = new LinearLayout(this.ctx);
         var19.setOrientation(0);
         var19.setGravity(16);
         TextView var20 = new TextView(this.ctx);
         var20.setText(var1);
         var20.setTextColor(TEXT);
         var20.setTextSize(21.0F);
         var19.addView((View)var20);
         TextView var9 = new TextView(this.ctx);
         var9.setText(var5);
         var9.setTextColor(WARN);
         var9.setTextSize(13.0F);
         var9.setTypeface(Typeface.DEFAULT_BOLD);
         var9.setPadding(this.dp(8), this.dp(2), this.dp(8), this.dp(2));
         var9.setBackground((Drawable)rounded$default(this, Color.rgb(61, 46, 23), 8, null, 4, null));
         View var10 = (View)var9;
         LayoutParams var17 = new LayoutParams(-2, -2);
         var17.leftMargin = this.dp(10);
         Unit var21 = Unit.INSTANCE;
         var19.addView(var10, (android.view.ViewGroup.LayoutParams)var17);
         var15.addView((View)var19);
      }

      TextView var11 = null;
      CharSequence var18 = var2;
      boolean var12;
      if (var18 != null && !StringsKt.isBlank(var18)) {
         var12 = false;
      } else {
         var12 = true;
      }

      if (!var12) {
         var11 = new TextView(this.ctx);
         var11.setText(var2);
         var11.setTextColor(DIM);
         var11.setTextSize(15.0F);
         var11.setPadding(0, this.dp(4), 0, 0);
         var15.addView((View)var11);
      }

      var6.addView((View)var15, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      return TuplesKt.to(var6, var11);
   }

   public final Drawable rowBg() {
      if (!this.flat) {
         return (Drawable)rounded$default(this, this.card, 0, null, 6, null);
      } else {
         GradientDrawable var2 = new GradientDrawable();
         var2.setColor(PAGE_DIV);
         GradientDrawable var1 = new GradientDrawable();
         var1.setColor(PAGE);
         LayerDrawable var3 = new LayerDrawable((Drawable[])(new GradientDrawable[]{var2, var1}));
         var3.setLayerInset(1, 0, 0, 0, this.dp(1));
         return (Drawable)var3;
      }
   }

   public final void setAutoIcon(Function1<? super String, SettingIcons.Icon> var1) {
      this.autoIcon = var1;
   }

   public final View spacer(int var1) {
      View var2 = new View(this.ctx);
      var2.setLayoutParams((android.view.ViewGroup.LayoutParams)(new LayoutParams(1, this.dp(var1))));
      return var2;
   }

   public final TextView stepper(String var1, Function0<Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "label");
      Intrinsics.checkNotNullParameter(var2, "onClick");
      TextView var3 = new TextView(this.ctx);
      var3.setText(var1);
      var3.setTextSize(26.0F);
      var3.setTextColor(TEXT);
      var3.setGravity(17);
      var3.setTypeface(Typeface.DEFAULT_BOLD);
      var3.setLayoutParams((android.view.ViewGroup.LayoutParams)(new LayoutParams(this.dp(62), this.dp(62))));
      var3.setBackground((Drawable)this.rounded(CARD_ON, 31, Color.rgb(60, 66, 78)));
      var3.setOnClickListener(new CarUi$$ExternalSyntheticLambda0(var2));
      return var3;
   }

   public final LinearLayout tile(String var1, String var2, int var3, int var4, Function0<Unit> var5) {
      Intrinsics.checkNotNullParameter(var1, "label");
      Intrinsics.checkNotNullParameter(var5, "onClick");
      LinearLayout var6 = new LinearLayout(this.ctx);
      var6.setOrientation(1);
      var6.setGravity(17);
      var6.setBackground((Drawable)rounded$default(this, CARD, 18, null, 4, null));
      var6.setPadding(this.dp(16), this.dp(18), this.dp(16), this.dp(18));
      ImageView var7 = new ImageView(this.ctx);
      var7.setImageResource(var3);
      var7.setColorFilter(ACCENT);
      var6.addView((View)var7, (android.view.ViewGroup.LayoutParams)(new LayoutParams(this.dp(var4), this.dp(var4))));
      TextView var9 = new TextView(this.ctx);
      var9.setText(var1);
      var9.setTextColor(TEXT);
      var9.setTextSize(22.0F);
      var9.setTypeface(Typeface.DEFAULT_BOLD);
      var9.setGravity(17);
      var9.setPadding(0, this.dp(12), 0, 0);
      var6.addView((View)var9);
      if (var2 != null) {
         TextView var8 = new TextView(this.ctx);
         var8.setText(var2);
         var8.setTextColor(DIM);
         var8.setTextSize(15.0F);
         var8.setGravity(17);
         var8.setPadding(0, this.dp(4), 0, 0);
         var6.addView((View)var8);
      }

      var6.setClickable(true);
      var6.setOnClickListener(new CarUi$$ExternalSyntheticLambda6(var5));
      return var6;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final int getACCENT() {
         return CarUi.ACCENT;
      }

      public final int getBG() {
         return CarUi.BG;
      }

      public final int getCARD() {
         return CarUi.CARD;
      }

      public final int getCARD_ON() {
         return CarUi.CARD_ON;
      }

      public final int getDIM() {
         return CarUi.DIM;
      }

      public final int getFRAME() {
         return CarUi.FRAME;
      }

      public final int getPAGE() {
         return CarUi.PAGE;
      }

      public final int getPAGE_DIV() {
         return CarUi.PAGE_DIV;
      }

      public final int getPAGE_EDGE() {
         return CarUi.PAGE_EDGE;
      }

      public final int getSECTION() {
         return CarUi.SECTION;
      }

      public final int getSTRIP() {
         return CarUi.STRIP;
      }

      public final int getTAB_SEP() {
         return CarUi.TAB_SEP;
      }

      public final int getTAB_TEXT() {
         return CarUi.TAB_TEXT;
      }

      public final int getTEXT() {
         return CarUi.TEXT;
      }

      public final int getWARN() {
         return CarUi.WARN;
      }
   }
}
