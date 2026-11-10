package com.starpilot.starview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class GalaxyWeb extends LinearLayout {
   private final FrameLayout frame;
   private final String host;
   private final Function0<Boolean> onClose;
   private final String path;
   private final TextView placeholder;
   private final CarUi ui;

   // $VF: synthetic method
   public static boolean $r8$lambda$Fazdyfq3OuKWtCQknNKPko05nw4() {
      return _init_$lambda$0();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KLxDCh_DnL4JIi4ho5OC1jFzT44(GalaxyWeb var0) {
      return show$lambda$8(var0);
   }

   public GalaxyWeb(Context var1, CarUi var2, String var3, String var4, String var5, Function0<Boolean> var6) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "host");
      Intrinsics.checkNotNullParameter(var4, "path");
      Intrinsics.checkNotNullParameter(var5, "title");
      Intrinsics.checkNotNullParameter(var6, "onClose");
      super(var1);
      this.ui = var2;
      this.host = var3;
      this.path = var4;
      this.onClose = var6;
      this.frame = new FrameLayout(var1);
      TextView var8 = new TextView(var1);
      var8.setText("Opening…");
      var8.setTextColor(CarUi.Companion.getDIM());
      var8.setTextSize(18.0F);
      var8.setGravity(17);
      var8.setBackgroundColor(CarUi.Companion.getFRAME());
      this.placeholder = var8;
      this.setOrientation(1);
      LinearLayout var9 = new LinearLayout(var1);
      var9.setOrientation(0);
      var9.setGravity(16);
      var9.setPadding(this.ui.dp(4), 0, this.ui.dp(4), this.ui.dp(8));
      TextView var10 = new TextView(var1);
      var10.setText("‹  Back");
      var10.setTextColor(CarUi.Companion.getACCENT());
      var10.setTextSize(18.0F);
      var10.setTypeface(Typeface.DEFAULT_BOLD);
      var10.setPadding(this.ui.dp(6), this.ui.dp(6), this.ui.dp(16), this.ui.dp(6));
      var10.setOnClickListener(new GalaxyWeb$$ExternalSyntheticLambda1(this));
      var9.addView((View)var10);
      TextView var11 = new TextView(var1);
      var11.setText(var5);
      var11.setTextColor(CarUi.Companion.getTEXT());
      var11.setTextSize(22.0F);
      var11.setTypeface(Typeface.DEFAULT_BOLD);
      var9.addView((View)var11, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      TextView var7 = new TextView(var1);
      var7.setText("↻");
      var7.setTextColor(CarUi.Companion.getDIM());
      var7.setTextSize(24.0F);
      var7.setPadding(this.ui.dp(16), 0, this.ui.dp(10), 0);
      var7.setOnClickListener(new GalaxyWeb$$ExternalSyntheticLambda2());
      var9.addView((View)var7);
      this.addView((View)var9);
      this.frame.setBackgroundColor(CarUi.Companion.getFRAME());
      this.addView((View)this.frame, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
      this.show();
   }

   // $VF: synthetic method
   public GalaxyWeb(Context var1, CarUi var2, String var3, String var4, String var5, Function0 var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 32) != 0) {
         var6 = new GalaxyWeb$$ExternalSyntheticLambda0();
      }

      this(var1, var2, var3, var4, var5, (Function0<Boolean>)var6);
   }

   private static final boolean _init_$lambda$0() {
      return false;
   }

   static final void lambda$4$lambda$3(GalaxyWeb var0, View var1) {
      if (!var0.back()) {
         var0.show();
      }
   }

   static final void lambda$7$lambda$6(View var0) {
      GalaxyWebPool.INSTANCE.reload();
   }

   private static final Unit show$lambda$8(GalaxyWeb var0) {
      var0.placeholder.setVisibility(8);
      return Unit.INSTANCE;
   }

   public final boolean back() {
      WebView var3 = GalaxyWebPool.INSTANCE.getWeb();
      if (var3 == null) {
         return this.onClose.invoke();
      } else {
         String var5;
         label34: {
            var5 = var3.getUrl();
            if (var5 != null) {
               var5 = StringsKt.substringAfter(var5, '#', "");
               if (var5 != null) {
                  var5 = var5;
                  boolean var1;
                  if (var5.length() == 0) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  if (var1) {
                     var5 = "/";
                  }

                  var5 = var5;
                  break label34;
               }
            }

            var5 = null;
         }

         if (var3.getParent() == this.frame && var5 != null && !Intrinsics.areEqual(var5, this.path) && var3.canGoBack()) {
            var3.goBack();
            return true;
         } else {
            return this.onClose.invoke();
         }
      }
   }

   public final void destroy() {
      WebView var2 = GalaxyWebPool.INSTANCE.getWeb();
      ViewParent var1;
      if (var2 != null) {
         var1 = var2.getParent();
      } else {
         var1 = null;
      }

      if (var1 == this.frame) {
         this.frame.removeView((View)var2);
      }

      GalaxyWebPool.INSTANCE.park();
   }

   public final void show() {
      GalaxyWebPool var2 = GalaxyWebPool.INSTANCE;
      Context var3 = this.getContext();
      Intrinsics.checkNotNullExpressionValue(var3, "getContext(...)");
      WebView var7 = var2.get(var3, this.host);
      if (var7.getParent() != this.frame) {
         ViewParent var4 = var7.getParent();
         ViewGroup var5;
         if (var4 instanceof ViewGroup) {
            var5 = (ViewGroup)var4;
         } else {
            var5 = null;
         }

         if (var5 != null) {
            var5.removeView((View)var7);
         }

         this.frame.removeAllViews();
         this.frame.addView((View)var7, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
         this.frame.addView((View)this.placeholder, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      }

      TextView var6 = this.placeholder;
      byte var1;
      if (GalaxyWebPool.INSTANCE.getReady()) {
         var1 = 8;
      } else {
         var1 = 0;
      }

      var6.setVisibility(var1);
      GalaxyWebPool.INSTANCE.whenReady(new GalaxyWeb$$ExternalSyntheticLambda3(this));
      GalaxyWebPool.INSTANCE.go(this.path);
   }
}
