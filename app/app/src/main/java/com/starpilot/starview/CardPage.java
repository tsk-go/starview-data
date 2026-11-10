package com.starpilot.starview;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

public class CardPage extends ScrollView {
   private final LinearLayout col;
   private final Handler main;
   private final CarUi ui;

   public CardPage(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1);
      this.ui = var2;
      LinearLayout var3 = new LinearLayout(var1);
      var3.setOrientation(1);
      var3.setPadding(this.ui.dp(4), this.ui.dp(4), this.ui.dp(4), this.ui.dp(40));
      this.col = var3;
      this.main = new Handler(Looper.getMainLooper());
      this.addView((View)this.col);
   }

   // $VF: synthetic method
   public static TextView line$default(CardPage var0, String var1, int var2, float var3, int var4, Object var5) {
      if (var5 == null) {
         if ((var4 & 2) != 0) {
            var2 = CarUi.Companion.getDIM();
         }

         if ((var4 & 4) != 0) {
            var3 = 16.0F;
         }

         return var0.line(var1, var2, var3);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: line");
      }
   }

   protected final void add(View var1) {
      Intrinsics.checkNotNullParameter(var1, "v");
      this.col.addView(var1, (LayoutParams)CarUi.lp$default(this.ui, 0, 12, 1, null));
   }

   protected final LinearLayout card() {
      LinearLayout var1 = new LinearLayout(this.getContext());
      var1.setOrientation(1);
      var1.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 18, null, 4, null));
      var1.setPadding(this.ui.dp(20), this.ui.dp(16), this.ui.dp(20), this.ui.dp(16));
      return var1;
   }

   protected final LinearLayout getCol() {
      return this.col;
   }

   protected final Handler getMain() {
      return this.main;
   }

   protected final CarUi getUi() {
      return this.ui;
   }

   protected final TextView line(String var1, int var2, float var3) {
      Intrinsics.checkNotNullParameter(var1, "text");
      TextView var4 = new TextView(this.getContext());
      var4.setText(var1);
      var4.setTextColor(var2);
      var4.setTextSize(var3);
      var4.setPadding(0, this.ui.dp(3), 0, this.ui.dp(3));
      return var4;
   }

   public void refresh() {
   }

   protected final View stat(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "big");
      Intrinsics.checkNotNullParameter(var2, "small");
      LinearLayout var3 = new LinearLayout(this.getContext());
      var3.setOrientation(1);
      var3.setPadding(0, 0, this.ui.dp(30), 0);
      TextView var4 = new TextView(var3.getContext());
      var4.setText(var1);
      var4.setTextColor(CarUi.Companion.getTEXT());
      var4.setTextSize(28.0F);
      var4.setTypeface(Typeface.DEFAULT_BOLD);
      var3.addView((View)var4);
      TextView var5 = new TextView(var3.getContext());
      var5.setText(var2);
      var5.setTextColor(CarUi.Companion.getDIM());
      var5.setTextSize(14.0F);
      var3.addView((View)var5);
      return (View)var3;
   }

   protected final View stats(Pair<String, String>... var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      LinearLayout var5 = new LinearLayout(this.getContext());
      int var2 = 0;
      var5.setOrientation(0);

      for (int var3 = var1.length; var2 < var3; var2++) {
         Pair var4 = var1[var2];
         var5.addView(this.stat((String)var4.component1(), (String)var4.component2()));
      }

      return (View)var5;
   }

   protected final View title(String var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var1, "text");
      Intrinsics.checkNotNullParameter(var2, "icon");
      Intrinsics.checkNotNullParameter(var3, "group");
      LinearLayout var4 = new LinearLayout(this.getContext());
      var4.setOrientation(0);
      var4.setGravity(16);
      var4.setPadding(0, 0, 0, this.ui.dp(8));
      SvIcon var5 = SvIcon.INSTANCE;
      Context var6 = var4.getContext();
      Intrinsics.checkNotNullExpressionValue(var6, "getContext(...)");
      View var7 = SvIcon.chip$default(var5, var6, var2, var3, 40, null, 16, null);
      android.widget.LinearLayout.LayoutParams var9 = new android.widget.LinearLayout.LayoutParams(this.ui.dp(40), this.ui.dp(40));
      var9.rightMargin = this.ui.dp(12);
      Unit var10 = Unit.INSTANCE;
      var4.addView(var7, (LayoutParams)var9);
      TextView var8 = new TextView(var4.getContext());
      var8.setText(var1);
      var8.setTextColor(CarUi.Companion.getTEXT());
      var8.setTextSize(20.0F);
      var8.setTypeface(Typeface.DEFAULT_BOLD);
      var4.addView((View)var8);
      return (View)var4;
   }
}
