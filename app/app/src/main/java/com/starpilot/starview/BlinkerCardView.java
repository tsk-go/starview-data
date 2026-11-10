package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

public final class BlinkerCardView extends View {
   private final Paint bg;
   private final Paint big;
   private final float d;
   private final RectF leftR;
   private final Handler main;
   private final Paint p;
   private final HashMap<Integer, Integer> pointers;
   private final Runnable repeat;
   private final RectF rightR;
   private String sent;
   private String status;
   private boolean statusErr;
   public Telemetry t;
   private final Paint txt;

   // $VF: synthetic method
   public static Unit $r8$lambda$A4OxtvJPxrrtBuajE3xrv4hkOXY(BlinkerCardView var0, boolean var1, String var2, JSONObject var3) {
      return send$lambda$5(var0, var1, var2, var3);
   }

   public BlinkerCardView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.d = this.getResources().getDisplayMetrics().density;
      Paint var3 = new Paint(1);
      var3.setColor(Color.argb(200, 0, 0, 0));
      this.bg = var3;
      this.p = new Paint(1);
      Paint var4 = new Paint(1);
      var4.setColor(Color.rgb(200, 208, 222));
      var4.setTextSize(this.d * 13.0F);
      var4.setTextAlign(Align.CENTER);
      this.txt = var4;
      Paint var5 = new Paint(1);
      var5.setColor(-1);
      var5.setTextSize(this.d * 15.0F);
      var5.setTypeface(Typeface.DEFAULT_BOLD);
      var5.setTextAlign(Align.CENTER);
      this.big = var5;
      this.leftR = new RectF();
      this.rightR = new RectF();
      this.pointers = new HashMap<>();
      this.main = new Handler(Looper.getMainLooper());
      this.sent = "off";
      this.status = "hold a button to signal";
      this.repeat = new Runnable() {

         @Override
         public void run() {
            String var1x = BlinkerCardView.this.held();
            if (Intrinsics.areEqual(var1x, "left") || Intrinsics.areEqual(var1x, "right")) {
               BlinkerCardView.this.send(var1x);
               BlinkerCardView.this.main.postDelayed(this, 250L);
            }
         }
      };
   }

   // $VF: synthetic method
   public BlinkerCardView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private final String held() {
      Collection var5 = this.pointers.values();
      Intrinsics.checkNotNullExpressionValue(var5, "<get-values>(...)");
      var var8 = var5;
      boolean var4 = ((Collection)var8).isEmpty();
      boolean var3 = true;
      boolean var1;
      if (var4) {
         var1 = false;
      } else {
         label65: {
            Iterator var9 = var8.iterator();

            while (var9.hasNext()) {
               if ((Integer)var9.next() < 0) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               if (var1) {
                  var1 = true;
                  break label65;
               }
            }

            var1 = false;
         }
      }

      var5 = this.pointers.values();
      Intrinsics.checkNotNullExpressionValue(var5, "<get-values>(...)");
      var var11 = var5;
      boolean var2;
      if (((Collection)var11).isEmpty()) {
         var2 = false;
      } else {
         label67: {
            Iterator var12 = var11.iterator();

            while (var12.hasNext()) {
               if ((Integer)var12.next() > 0) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               if (var2) {
                  var2 = var3;
                  break label67;
               }
            }

            var2 = false;
         }
      }

      String var13;
      if (var1 && var2) {
         var13 = "both";
      } else if (var1) {
         var13 = "left";
      } else if (var2) {
         var13 = "right";
      } else {
         var13 = "off";
      }

      return var13;
   }

   private static final void onDraw$button(BlinkerCardView var0, String var1, Canvas var2, boolean var3, RectF var4, int var5, boolean var6, boolean var7) {
      Paint var14 = var0.p;
      int var18;
      if (var6 && Intrinsics.areEqual(var1, "both")) {
         var18 = Color.rgb(120, 40, 40);
      } else {
         short var11;
         byte var12;
         short var13;
         if (var6) {
            var11 = 140;
            var12 = 20;
            var13 = 200;
         } else {
            var11 = 54;
            var12 = 66;
            var13 = 48;
         }

         var18 = Color.rgb(var13, var11, var12);
      }

      var14.setColor(var18);
      var2.drawRoundRect(var4, var0.d * 14.0F, var0.d * 14.0F, var0.p);
      Paint var15 = var0.p;
      short var19;
      short var20;
      short var21;
      if (var7 && var3) {
         var19 = 220;
         var20 = 100;
         var21 = 60;
      } else if (var7) {
         var19 = 110;
         var20 = 50;
         var21 = 30;
      } else {
         var19 = 176;
         var20 = 188;
         var21 = 170;
      }

      var15.setColor(Color.rgb(var21, var19, var20));
      float var8 = var4.centerX();
      float var9 = var4.centerY() - var0.d * 6.0F;
      float var10 = Math.min(var4.width(), var4.height()) * 0.28F;
      Path var16 = new Path();
      if (var5 < 0) {
         var16.moveTo(var8 - var10, var9);
         var16.lineTo(var8, var9 - var10 * 0.8F);
         var16.lineTo(var8, var9 - var10 * 0.35F);
         var16.lineTo(var8 + var10, var9 - var10 * 0.35F);
         var16.lineTo(var8 + var10, var10 * 0.35F + var9);
         var16.lineTo(var8, 0.35F * var10 + var9);
         var16.lineTo(var8, 0.8F * var10 + var9);
      } else {
         var16.moveTo(var8 + var10, var9);
         var16.lineTo(var8, var9 - var10 * 0.8F);
         var16.lineTo(var8, var9 - var10 * 0.35F);
         var16.lineTo(var8 - var10, var9 - var10 * 0.35F);
         var16.lineTo(var8 - var10, var10 * 0.35F + var9);
         var16.lineTo(var8, 0.35F * var10 + var9);
         var16.lineTo(var8, 0.8F * var10 + var9);
      }

      var16.close();
      var2.drawPath(var16, var0.p);
      if (var6) {
         var1 = "HOLDING";
      } else if (var5 < 0) {
         var1 = "LEFT";
      } else {
         var1 = "RIGHT";
      }

      var2.drawText(var1, var8, var4.bottom - var0.d * 8.0F, var0.big);
   }

   private final void send(String var1) {
      DataClient var3 = Link.INSTANCE.getData();
      if (var3 != null && var3.getConnected()) {
         boolean var2 = Intrinsics.areEqual(var1, this.sent);
         this.sent = var1;
         var3.request("blink", MapsKt.mapOf(TuplesKt.to("side", var1)), new BlinkerCardView$$ExternalSyntheticLambda0(this, true ^ var2, var1));
      } else {
         this.status = "not connected to the comma";
         this.statusErr = true;
         this.invalidate();
      }
   }

   private static final Unit send$lambda$5(BlinkerCardView var0, boolean var1, String var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var3, "ack");
      if (!var3.optBoolean("ok")) {
         var0.status = var3.optString("error", "rejected");
         var0.statusErr = true;
      } else if (var1) {
         if (Intrinsics.areEqual(var2, "off")) {
            var2 = "released";
         } else {
            var2 = var2 + " signal on - let go to stop";
         }

         var0.status = var2;
         var0.statusErr = false;
      }

      var0.invalidate();
      return Unit.INSTANCE;
   }

   private final void update() {
      String var1 = this.held();
      this.main.removeCallbacks(this.repeat);
      if (Intrinsics.areEqual(var1, "both")) {
         this.send("off");
         this.status = "both = off (the relay box never closes both sides)";
         this.statusErr = true;
      } else if (Intrinsics.areEqual(var1, "off")) {
         if (!Intrinsics.areEqual(this.sent, "off")) {
            this.send("off");
         }
      } else {
         this.send(var1);
         this.main.postDelayed(this.repeat, 250L);
      }

      this.invalidate();
   }

   public final Telemetry getT() {
      Telemetry var1 = this.t;
      if (var1 != null) {
         return var1;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("t");
         return null;
      }
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.pointers.clear();
      this.main.removeCallbacks(this.repeat);
      if (!Intrinsics.areEqual(this.sent, "off")) {
         this.send("off");
      }
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      float var2 = this.getWidth();
      float var3 = this.getHeight();
      float var4 = this.d * 10.0F;
      var1.drawRoundRect(new RectF(0.0F, 0.0F, var2, var3), this.d * 16.0F, this.d * 16.0F, this.bg);
      float var6 = 3;
      float var5 = 2;
      var6 = (var2 - var6 * var4) / var5;
      float var7 = var3 - var4 - this.d * 26.0F;
      this.leftR.set(var4, var4, var4 + var6, var4 + var7);
      this.rightR.set(var5 * var4 + var6, var4, var2 - var4, var4 + var7);
      String var15 = this.held();
      boolean var12;
      if (System.currentTimeMillis() / 350 % 2 == 0L) {
         var12 = true;
      } else {
         var12 = false;
      }

      boolean var13;
      if (this.t != null && this.getT().getLeftBlinker()) {
         var13 = true;
      } else {
         var13 = false;
      }

      boolean var14;
      if (this.t != null && this.getT().getRightBlinker()) {
         var14 = true;
      } else {
         var14 = false;
      }

      RectF var16 = this.leftR;
      Collection var17 = this.pointers.values();
      Intrinsics.checkNotNullExpressionValue(var17, "<get-values>(...)");
      var var28 = var17;
      boolean var11;
      if (((Collection)var28).isEmpty()) {
         var11 = false;
      } else {
         label109: {
            Iterator var29 = var28.iterator();

            while (var29.hasNext()) {
               boolean var8;
               if ((Integer)var29.next() < 0) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               if (var8) {
                  var11 = true;
                  break label109;
               }
            }

            var11 = false;
         }
      }

      onDraw$button(this, var15, var1, var12, var16, -1, var11, var13);
      var16 = this.rightR;
      var17 = this.pointers.values();
      Intrinsics.checkNotNullExpressionValue(var17, "<get-values>(...)");
      var var31 = var17;
      if (((Collection)var31).isEmpty()) {
         var11 = false;
      } else {
         label111: {
            Iterator var32 = var31.iterator();

            while (var32.hasNext()) {
               boolean var19;
               if ((Integer)var32.next() > 0) {
                  var19 = true;
               } else {
                  var19 = false;
               }

               if (var19) {
                  var11 = true;
                  break label111;
               }
            }

            var11 = false;
         }
      }

      onDraw$button(this, var15, var1, var12, var16, 1, var11, var14);
      Paint var22 = this.txt;
      short var9;
      short var10;
      short var20;
      if (this.statusErr) {
         var20 = 130;
         var10 = 120;
         var9 = 255;
      } else {
         var20 = 208;
         var10 = 222;
         var9 = 200;
      }

      label113: {
         var22.setColor(Color.rgb(var9, var20, var10));
         var15 = BlinkerTurn.INSTANCE.label();
         if (var15 != null) {
            String var26 = "openpilot: " + var15;
            var15 = var26;
            if (var26 != null) {
               break label113;
            }
         }

         if (var13 && var14) {
            var15 = "openpilot sees: both";
         } else if (var13) {
            var15 = "openpilot sees: LEFT";
         } else if (var14) {
            var15 = "openpilot sees: RIGHT";
         } else {
            var15 = "openpilot sees: none";
         }
      }

      String var27 = this.status;
      var1.drawText(var27 + "  ·  " + var15, var2 / var5, var3 - this.d * 9.0F, this.txt);
      if (!this.pointers.isEmpty() || var13 || var14) {
         this.postInvalidateDelayed(120L);
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      int var3 = var1.getActionIndex();
      switch (var1.getActionMasked()) {
         case 0:
         case 5:
            byte var2;
            if (this.leftR.contains(var1.getX(var3), var1.getY(var3))) {
               var2 = -1;
            } else if (this.rightR.contains(var1.getX(var3), var1.getY(var3))) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            if (var2 != 0) {
               this.pointers.put(var1.getPointerId(var3), Integer.valueOf(var2));
               this.performHapticFeedback(1);
               this.update();
            }
            break;
         case 1:
         case 6:
            if (this.pointers.remove(var1.getPointerId(var3)) != null) {
               this.update();
            }
         case 2:
         case 4:
         default:
            break;
         case 3:
            this.pointers.clear();
            this.update();
      }

      return true;
   }

   protected void onVisibilityChanged(View var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "changedView");
      super.onVisibilityChanged(var1, var2);
      if (var2 != 0 && !this.pointers.isEmpty()) {
         this.pointers.clear();
         this.update();
      }
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }
}
