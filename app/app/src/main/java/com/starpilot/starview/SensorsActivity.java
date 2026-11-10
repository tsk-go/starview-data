package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.jvm.internal.Intrinsics;

public final class SensorsActivity extends AppCompatActivity {
   private SensorsView view;

   // $VF: synthetic method
   public static void $r8$lambda$XatNt3XQuJfr30u3mdDhlIUf7CY(SensorsActivity var0, View var1) {
      onCreate$lambda$6$lambda$5(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$fZ5cL9IQZKudHrwMmjcqwhorz88(SensorsActivity var0, View var1) {
      onCreate$lambda$4$lambda$3(var0, var1);
   }

   private static final void onCreate$lambda$4$lambda$3(SensorsActivity var0, View var1) {
      SensorsView var3 = var0.view;
      SensorsView var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("view");
         var2 = null;
      }

      var2.reset();
   }

   private static final void onCreate$lambda$6$lambda$5(SensorsActivity var0, View var1) {
      var0.finish();
   }

   @Override
   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.getWindow().addFlags(128);
      float var3 = this.getResources().getDisplayMetrics().density;
      LinearLayout var5 = new LinearLayout((Context)this);
      var5.setOrientation(1);
      var5.setBackgroundColor(Color.rgb(11, 13, 18));
      LinearLayout var4 = new LinearLayout((Context)this);
      var4.setOrientation(0);
      var4.setGravity(16);
      float var2 = 16;
      var4.setPadding((int)(var2 * var3), (int)(10 * var3), (int)(var2 * var3), (int)(6 * var3));
      TextView var6 = new TextView((Context)this);
      var6.setText("GPS & sensors");
      var6.setTextColor(-1);
      var6.setTextSize(20.0F);
      SensorsView var7 = null;
      var6.setTypeface(null, 1);
      var4.addView((View)var6, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      this.view = new SensorsView((Context)this);
      Button var9 = new Button((Context)this);
      var9.setText("Reset");
      var9.setOnClickListener(new SensorsActivity$$ExternalSyntheticLambda0(this));
      var4.addView((View)var9);
      Button var10 = new Button((Context)this);
      var10.setText("Close");
      var10.setOnClickListener(new SensorsActivity$$ExternalSyntheticLambda1(this));
      var4.addView((View)var10);
      var5.addView((View)var4, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
      SensorsView var8 = this.view;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("view");
      } else {
         var7 = var8;
      }

      var5.addView(var7, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
      this.setContentView((View)var5);
   }
}
