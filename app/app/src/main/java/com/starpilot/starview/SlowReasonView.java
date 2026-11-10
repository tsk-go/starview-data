package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

public final class SlowReasonView extends View {
   public static final SlowReasonView.Companion Companion = new SlowReasonView.Companion(null);
   private final Paint acc;
   private final Paint bar;
   private final Paint bg;
   private final float d;
   private final Paint sub;
   public Telemetry t;
   private final Paint title;
   private final TextPaint tp;

   // $VF: synthetic method
   public static CharSequence $r8$lambda$99in1d_4Q33fAOdn_3SYv7mG8Xo(SimpleDateFormat var0, SlowReason.Event var1) {
      return showHistory$lambda$7(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$TSzxY26FKpDdd7YQm_cJsqiCIAQ(SlowReasonView var0, View var1) {
      _init_$lambda$5(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$rmJjO2dpy3rchO_ezpACqRKHqdI/* $VF was: $r8$lambda$rmJjO2dpy3rchO_ezpACqRKHqdI*/(
      SlowReasonView var0, String var1, DialogInterface var2, int var3
   ) {
      showHistory$lambda$8(var0, var1, var2, var3);
   }

   public SlowReasonView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.d = this.getResources().getDisplayMetrics().density;
      Paint var3 = new Paint(1);
      var3.setColor(Color.argb(200, 0, 0, 0));
      this.bg = var3;
      this.bar = new Paint(1);
      Paint var4 = new Paint(1);
      var4.setColor(-1);
      var4.setTypeface(Typeface.DEFAULT_BOLD);
      var4.setTextSize(this.d * 21.0F);
      this.title = var4;
      Paint var5 = new Paint(1);
      var5.setColor(Color.rgb(200, 208, 222));
      var5.setTextSize(this.d * 15.0F);
      this.sub = var5;
      Paint var6 = new Paint(1);
      var6.setColor(-1);
      var6.setTypeface(Typeface.DEFAULT_BOLD);
      var6.setTextSize(this.d * 17.0F);
      var6.setTextAlign(Align.RIGHT);
      this.acc = var6;
      this.tp = new TextPaint(1);
      this.setOnClickListener(new SlowReasonView$$ExternalSyntheticLambda0(this));
   }

   // $VF: synthetic method
   public SlowReasonView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private static final void _init_$lambda$5(SlowReasonView var0, View var1) {
      var0.showHistory();
   }

   private final void showHistory() {
      if (this.t != null) {
         SlowReason.Tracker var2 = this.getT().getSlow();
         List var1;
         synchronized (var2) {
            var1 = CollectionsKt.toList(this.getT().getSlow().getHistory());
         }

         SimpleDateFormat var6 = new SimpleDateFormat("HH:mm:ss", Locale.US);
         String var5;
         if (var1.isEmpty()) {
            var5 = "No braking by openpilot since StarView started.";
         } else {
            var5 = CollectionsKt.joinToString$default(var1, "\n\n", null, null, 0, null, new SlowReasonView$$ExternalSyntheticLambda1(var6), 30, null);
         }

         new Builder(this.getContext())
            .setTitle("Why it slowed (newest first)")
            .setMessage(var5)
            .setPositiveButton("Close", null)
            .setNeutralButton("Copy", new SlowReasonView$$ExternalSyntheticLambda2(this, var5))
            .show();
      }
   }

   private static final CharSequence showHistory$lambda$7(SimpleDateFormat var0, SlowReason.Event var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      return SlowReasonView.Companion.line$default(Companion, var1, var0, false, 4, null);
   }

   private static final void showHistory$lambda$8(SlowReasonView var0, String var1, DialogInterface var2, int var3) {
      Object var4 = var0.getContext().getSystemService("clipboard");
      Intrinsics.checkNotNull(var4, "null cannot be cast to non-null type android.content.ClipboardManager");
      ((ClipboardManager)var4).setPrimaryClip(ClipData.newPlainText("StarView braking log", var1));
   }

   public final SlowReason.Out current() {
      SlowReason.Out var1;
      if (this.t == null) {
         var1 = null;
      } else {
         SlowReason.Tracker var2 = this.getT().getSlow();
         synchronized (var2) {
            var1 = this.getT().getSlow().getShown();
         }
      }

      return var1;
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

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      SlowReason.Out var10 = this.current();
      if (var10 != null) {
         float var5 = this.getWidth();
         float var7 = this.getHeight();
         float var3 = this.d * 16.0F;
         float var2 = 0.0F;
         var1.drawRoundRect(new RectF(0.0F, 0.0F, var5, var7), var3, var3, this.bg);
         this.bar.setColor(Companion.color(var10.getKind()));
         var1.drawRoundRect(new RectF(0.0F, 0.0F, this.d * 8.0F, var7), this.d * 4.0F, this.d * 4.0F, this.bar);
         float var6 = this.d * 20.0F;
         float var4 = Math.min(this.getT().getATarget(), this.getT().getActAccel());
         boolean var8;
         if (var4 <= -0.45F) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         if (var8) {
            var2 = this.acc.measureText("−9.9 m/s²") + this.d * 12.0F;
         }

         if (var8) {
            StringCompanionObject var11 = StringCompanionObject.INSTANCE;
            String var19 = String.format(Locale.US, "%.1f m/s²", Arrays.copyOf(new Object[]{var4}, 1));
            Intrinsics.checkNotNullExpressionValue(var19, "format(...)");
            var1.drawText(StringsKt.replace$default(var19, "-", "−", false, 4, null), var5 - this.d * 14.0F, this.d * 32.0F, this.acc);
         }

         this.tp.set(this.title);
         var1.drawText(
            TextUtils.ellipsize(var10.getTitle(), this.tp, var5 - var6 - var2 - this.d * 14.0F, TruncateAt.END).toString(), var6, this.d * 32.0F, this.title
         );
         this.tp.set(this.sub);
         List var12 = StringsKt.split$default(var10.getDetail(), new String[]{" "}, false, 0, 6, null);
         String var20 = "";
         var4 = this.d * 58.0F;
         var8 = 0;

         for (String var13 : var12) {
            boolean var9;
            if (var20.length() == 0) {
               var9 = true;
            } else {
               var9 = false;
            }

            String var21;
            if (var9) {
               var21 = var13;
            } else {
               var21 = var20 + " " + var13;
            }

            if (this.sub.measureText(var21) > var5 - var6 - this.d * 14.0F) {
               if (var20.length() > 0) {
                  var9 = true;
               } else {
                  var9 = false;
               }

               if (var9) {
                  var1.drawText(var20, var6, var4, this.sub);
                  var4 += this.d * 20.0F;
                  var8++;
                  var20 = var13;
                  if (var8 == 1 && var4 > var7 - this.d * 6.0F) {
                     return;
                  }
                  continue;
               }
            }

            var20 = var21;
         }

         boolean var17;
         if (var20.length() > 0) {
            var17 = true;
         } else {
            var17 = false;
         }

         if (var17 && var4 < var7) {
            var1.drawText(TextUtils.ellipsize(var20, this.tp, var5 - var6 - this.d * 14.0F, TruncateAt.END).toString(), var6, var4, this.sub);
         }
      }
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      // $VF: synthetic method
      public static String line$default(SlowReasonView.Companion var0, SlowReason.Event var1, SimpleDateFormat var2, boolean var3, int var4, Object var5) {
         if ((var4 & 4) != 0) {
            var3 = false;
         }

         return var0.line(var1, var2, var3);
      }

      public final int color(SlowReason.Kind var1) {
         Intrinsics.checkNotNullParameter(var1, "k");
         int var2;
         switch (SlowReasonView.Companion.WhenMappings.$EnumSwitchMapping$0[var1.ordinal()]) {
            case 1:
               var2 = Color.rgb(230, 40, 50);
               break;
            case 2:
               var2 = Color.rgb(255, 160, 40);
               break;
            case 3:
               var2 = Color.rgb(170, 110, 255);
               break;
            case 4:
               var2 = Color.rgb(230, 70, 70);
               break;
            case 5:
               var2 = Color.rgb(60, 160, 255);
               break;
            case 6:
               var2 = Color.rgb(240, 240, 240);
               break;
            case 7:
            case 8:
               var2 = Color.rgb(90, 200, 140);
               break;
            case 9:
               var2 = Color.rgb(150, 160, 180);
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }

         return var2;
      }

      public final String line(SlowReason.Event var1, SimpleDateFormat var2, boolean var3) {
         Intrinsics.checkNotNullParameter(var1, "e");
         Intrinsics.checkNotNullParameter(var2, "fmt");
         float var6;
         if (var3) {
            var6 = 3.6F;
         } else {
            var6 = 2.23694F;
         }

         String var9;
         if (var3) {
            var9 = "km/h";
         } else {
            var9 = "mph";
         }

         String var13 = var2.format(new Date(var1.getStartMs()));
         int var8 = (int)(var1.getVStart() * var6);
         int var7 = (int)(var1.getVEnd() * var6);
         StringCompanionObject var10 = StringCompanionObject.INSTANCE;
         String var14 = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{var1.getMinA()}, 1));
         Intrinsics.checkNotNullExpressionValue(var14, "format(...)");
         double var4 = (var1.getEndMs() - var1.getStartMs()) / 100 / 10.0;
         String var11 = var1.getReason().getTitle();
         String var12 = var1.getReason().getDetail();
         return var13 + "  " + var8 + "→" + var7 + " " + var9 + ", hardest " + var14 + " m/s², " + var4 + " s\n" + var11 + " - " + var12;
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      public final void log(Context var1, SlowReason.Event var2) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var2, "e");

         long var3;
         File var5;
         try {
            var5 = new File(var1.getFilesDir(), "braking_log.txt");
            var3 = var5.length();
         } catch (Exception var9) {
            return;
         }

         if (var3 > 1000000L) {
            try {
               String var6 = CollectionsKt.joinToString$default(
                  CollectionsKt.takeLast(FilesKt.readLines$default(var5, null, 1, null), 2000), "\n", null, null, 0, null, null, 62, null
               );
               StringBuilder var10 = new StringBuilder();
               FilesKt.writeText$default(var5, var10.append(var6).append("\n").toString(), null, 2, null);
            } catch (Exception var8) {
               return;
            }
         }

         try {
            SimpleDateFormat var11 = new SimpleDateFormat("yyyy-MM-dd ", Locale.US);
            Date var14 = new Date(var2.getStartMs());
            String var12 = var11.format(var14);
            SimpleDateFormat var15 = new SimpleDateFormat("HH:mm:ss", Locale.US);
            String var16 = StringsKt.replace$default(line$default(this, var2, var15, false, 4, null), "\n", " | ", false, 4, null);
            StringBuilder var13 = new StringBuilder();
            FilesKt.appendText$default(var5, var13.append(var12).append(var16).append("\n").toString(), null, 2, null);
         } catch (Exception var7) {
         }
      }

      // $VF: synthetic class
      public final class WhenMappings {
         public static final int[] $EnumSwitchMapping$0;

         static {
            int[] var0 = new int[SlowReason.Kind.values().length];

            try {
               var0[SlowReason.Kind.FCW.ordinal()] = 1;
            } catch (NoSuchFieldError var10) {
            }

            try {
               var0[SlowReason.Kind.LEAD.ordinal()] = 2;
            } catch (NoSuchFieldError var9) {
            }

            try {
               var0[SlowReason.Kind.MODEL.ordinal()] = 3;
            } catch (NoSuchFieldError var8) {
            }

            try {
               var0[SlowReason.Kind.STOP.ordinal()] = 4;
            } catch (NoSuchFieldError var7) {
            }

            try {
               var0[SlowReason.Kind.CURVE.ordinal()] = 5;
            } catch (NoSuchFieldError var6) {
            }

            try {
               var0[SlowReason.Kind.LIMIT.ordinal()] = 6;
            } catch (NoSuchFieldError var5) {
            }

            try {
               var0[SlowReason.Kind.TARGET.ordinal()] = 7;
            } catch (NoSuchFieldError var4) {
            }

            try {
               var0[SlowReason.Kind.COAST.ordinal()] = 8;
            } catch (NoSuchFieldError var3) {
            }

            try {
               var0[SlowReason.Kind.OTHER.ordinal()] = 9;
            } catch (NoSuchFieldError var2) {
            }

            $EnumSwitchMapping$0 = var0;
         }
      }
   }
}
