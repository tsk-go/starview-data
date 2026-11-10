package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

public final class LaneCardView extends View {
   public static final LaneCardView.Companion Companion = new LaneCardView.Companion(null);
   private final long UNDO_MS;
   private Function0<Pair<String, Boolean>> ahead;
   private final Paint bg;
   private final TextPaint big;
   private final Paint box;
   private final TextPaint btnText;
   private final float d;
   private final TextPaint dim;
   private final RectF fixR;
   private final TextPaint fixText;
   private String flash;
   private long flashMs;
   private boolean flashOk;
   private long lastCheckMs;
   private boolean lastCounted;
   private boolean lastOk;
   private int nRight;
   private int nWrong;
   private final RectF noR;
   private final RectF okR;
   private final SharedPreferences prefs;
   private int pressed;
   private Function0<? extends List<LaneRamps.Ramp>> ramps;
   private LaneShots shots;
   private final TextPaint small;
   private final TextPaint stat;
   private final RectF topR;
   private LaneBrain tracker;
   private final RectF undoR;
   private Function0<Triple<Double, Double, Float>> where;

   // $VF: synthetic method
   public static CharSequence $r8$lambda$1U_yfB5XcVI7B4mRq7p9iLo3lIg/* $VF was: $r8$lambda$1U_yfB5XcVI7B4mRq7p9iLo3lIg*/(
      SimpleDateFormat var0, LaneBrain.Event var1
   ) {
      return showHistory$lambda$20(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$B1owyUhdYtj_ag4lsRvoKpnSWkA(
      LaneCardView var0, LaneBrain var1, int var2, int var3, LaneBrain.Road var4, LaneFixDialog.Result var5
   ) {
      return askCount$lambda$18(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Triple $r8$lambda$N2jxy0zmbCmxhR8aM4EC3NKxUxw() {
      return where$lambda$0();
   }

   // $VF: synthetic method
   public static void $r8$lambda$V14tNLeXALRJFDRdwVwL6WXbo1E(LaneCardView var0, DialogInterface var1, int var2) {
      showHistory$lambda$21(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$XLE7aUDi8TYtJXwg7puQVpzdY9A(LaneCardView var0, String var1, DialogInterface var2, int var3) {
      showHistory$lambda$22(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$YquApjy5rEoYAYS2orOSfP0YDbk(LaneCardView var0, LaneBrain.Event var1) {
      return onDraw$lambda$10(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$h3zx_y2WDrOvmJNubZpRcdxqIIM/* $VF was: $r8$lambda$h3zx_y2WDrOvmJNubZpRcdxqIIM*/(LaneCardView var0, View var1) {
      _init_$lambda$8(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$j5Afx5WJwJHh9R02wMsl6vuEHhQ(LaneCardView var0, View var1) {
      return _init_$lambda$9(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tdZVyVOgUz80rPwu1la_nXiA6tU(LaneBrain var0, LaneCardView var1) {
      return askCount$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$zLIQHnk15jTsd10Ck3zzmCJm9eI(LaneBrain var0, LaneCardView var1, DialogInterface var2, int var3) {
      askReset$lambda$19(var0, var1, var2, var3);
   }

   public LaneCardView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.where = new LaneCardView$$ExternalSyntheticLambda1();
      this.d = this.getResources().getDisplayMetrics().density;
      Paint var4 = new Paint(1);
      var4.setColor(Color.argb(200, 0, 0, 0));
      this.bg = var4;
      this.box = new Paint(1);
      TextPaint var5 = new TextPaint(1);
      var5.setColor(-1);
      var5.setTypeface(Typeface.DEFAULT_BOLD);
      var5.setTextSize(this.d * 24.0F);
      this.big = var5;
      TextPaint var6 = new TextPaint(1);
      var6.setColor(Color.rgb(200, 208, 222));
      var6.setTextSize(this.d * 13.5F);
      this.small = var6;
      TextPaint var7 = new TextPaint(1);
      var7.setColor(Color.rgb(150, 160, 178));
      var7.setTextSize(this.d * 12.5F);
      this.dim = var7;
      TextPaint var8 = new TextPaint(1);
      var8.setColor(Color.rgb(225, 230, 240));
      var8.setTextSize(this.d * 13.0F);
      var8.setTypeface(Typeface.DEFAULT_BOLD);
      this.stat = var8;
      TextPaint var9 = new TextPaint(1);
      var9.setColor(-1);
      var9.setTypeface(Typeface.DEFAULT_BOLD);
      var9.setTextSize(this.d * 24.0F);
      var9.setTextAlign(Align.CENTER);
      this.btnText = var9;
      this.prefs = var1.getSharedPreferences("lane_card", 0);
      this.nRight = this.prefs.getInt("right", 0);
      this.nWrong = this.prefs.getInt("wrong", 0);
      this.flash = "";
      this.flashOk = true;
      this.okR = new RectF();
      this.noR = new RectF();
      this.undoR = new RectF();
      this.fixR = new RectF();
      this.topR = new RectF();
      TextPaint var3 = new TextPaint(1);
      var3.setColor(-1);
      var3.setTypeface(Typeface.DEFAULT_BOLD);
      var3.setTextSize(this.d * 16.0F);
      var3.setTextAlign(Align.CENTER);
      this.fixText = var3;
      this.lastOk = true;
      this.UNDO_MS = 8000L;
      this.setOnClickListener(new LaneCardView$$ExternalSyntheticLambda2(this));
      this.setOnLongClickListener(new LaneCardView$$ExternalSyntheticLambda3(this));
   }

   // $VF: synthetic method
   public LaneCardView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private static final void _init_$lambda$8(LaneCardView var0, View var1) {
      var0.showHistory();
   }

   private static final boolean _init_$lambda$9(LaneCardView var0, View var1) {
      try {
         Context var4 = var0.getContext();
         Intent var2 = new Intent(var0.getContext(), LanePicturesActivity.class);
         var4.startActivity(var2);
      } catch (Exception var3) {
      }

      return true;
   }

   private final String ago(long var1) {
      var1 = (System.currentTimeMillis() - var1) / 1000;
      StringBuilder var3;
      String var4;
      if (var1 < 60L) {
         var3 = new StringBuilder().append(var1);
         var4 = "s ago";
      } else {
         var1 /= 60;
         var3 = new StringBuilder().append(var1);
         var4 = "m ago";
      }

      return var3.append(var4).toString();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void askCount() {
      LaneBrain var11 = this.tracker;
      if (var11 != null) {
         int var1;
         int var4;
         int var5;
         LaneBrain.Road var10;
         label100: {
            var4 = var11.laneFromLeft().component1().intValue();
            var5 = var11.laneCount().component1().intValue();
            var10 = var11.getRoad();
            if (var10 != null) {
               Integer var6 = var11.getMyCounts().get(var10.getId());
               if (var6 != null) {
                  var1 = var6;
                  break label100;
               }
            }

            var1 = 0;
         }

         String var21;
         if (var10 == null) {
            var21 = "";
         } else if (var10.getHighway()) {
            var21 = "divided";
         } else if (var10.getOneway()) {
            var21 = "one";
         } else {
            var21 = "two";
         }

         int var3 = 1;
         LaneFixDialog.Result var7;
         if (var5 > 0) {
            boolean var2;
            if (1 <= var4 && var4 <= var5) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            if (var2) {
               var2 = var4;
            } else {
               var2 = 0;
            }

            var7 = new LaneFixDialog.Result(var5, 0, 0, "", var2, true, var21, 0, null, 384, null);
         } else {
            var7 = null;
         }

         LaneFixDialog var12 = LaneFixDialog.INSTANCE;
         Context var13 = this.getContext();
         Intrinsics.checkNotNullExpressionValue(var13, "getContext(...)");
         LaneBrain.Road var22 = var11.getRoad();
         int var19;
         if (var22 != null) {
            var19 = var22.getLanesOurWay();
         } else {
            var19 = 0;
         }

         String var8;
         label86: {
            if (var10 != null) {
               var21 = var10.getName();
               if (var21 != null) {
                  var21 = var21;
                  if (var21.length() != 0) {
                     var3 = 0;
                  }

                  if (var3) {
                     var21 = "this street";
                  }

                  var8 = var21;
                  break label86;
               }
            }

            var8 = null;
         }

         if (var5 > 0) {
            var3 = var5;
         } else {
            var3 = 0;
         }

         LaneCardView$$ExternalSyntheticLambda8 var14 = new LaneCardView$$ExternalSyntheticLambda8(var11, this);

         label106: {
            try {
               var26 = this.ramps;
            } catch (Exception var17) {
               var25 = CollectionsKt.emptyList();
               break label106;
            }

            if (var26 != null) {
               List var9;
               try {
                  var9 = (List)var26.invoke();
               } catch (Exception var16) {
                  var25 = CollectionsKt.emptyList();
                  break label106;
               }

               var25 = var9;
               if (var9 != null) {
                  break label106;
               }
            }

            try {
               var25 = CollectionsKt.emptyList();
            } catch (Exception var15) {
               var25 = CollectionsKt.emptyList();
            }
         }

         LaneFixDialog.show$default(
            var12,
            var13,
            "Fix lanes",
            var19,
            var8,
            var3,
            true,
            var1,
            var14,
            null,
            null,
            null,
            var25,
            null,
            var7,
            new LaneCardView$$ExternalSyntheticLambda9(this, var11, var4, var5, var10),
            5888,
            null
         );
      }
   }

   private static final Unit askCount$lambda$17(LaneBrain var0, LaneCardView var1) {
      LaneBrain.setCount$default(var0, 0, 0, true, 0L, 0, 0, null, null, 248, null);
      var1.flashOk = true;
      var1.flashMs = System.currentTimeMillis();
      var1.flash = "saved count deleted - the map's again";
      var1.invalidate();
      return Unit.INSTANCE;
   }

   private static final Unit askCount$lambda$18(LaneCardView var0, LaneBrain var1, int var2, int var3, LaneBrain.Road var4, LaneFixDialog.Result var5) {
      Intrinsics.checkNotNullParameter(var5, "r");
      LaneShots var14 = var0.shots;
      if (var14 != null) {
         LaneShots.Companion var16 = LaneShots.Companion;
         String var15 = var5.text();
         LaneShots.request$default(
            var14, var16.caption(var1, "before your Fix: you said " + var15, var0.where.invoke().getThird().floatValue()), LaneShots.Why.USER, null, 4, null
         );
      }

      LaneBrain.setCount$default(
         var1, var5.getThrough(), var5.getLane(), var5.getRemember(), 0L, var5.getExtraL(), var5.getExtraR(), var5.getKind(), var5.getKindR(), 8, null
      );
      var0.flashOk = true;
      var0.flashMs = System.currentTimeMillis();
      String var31 = var5.text();
      boolean var13 = var5.getRemember();
      String var30 = "";
      String var27;
      if (var13) {
         int var11 = var5.getThrough();
         var27 = " (" + var11 + " remembered)";
      } else {
         var27 = "";
      }

      var0.flash = "saved: " + var31 + var27;
      Triple var28 = var0.where.invoke();
      double var8 = ((Number)var28.component1()).doubleValue();
      double var6 = ((Number)var28.component2()).doubleValue();
      float var10 = ((Number)var28.component3()).floatValue();
      LaneCardView.Companion var29 = Companion;
      Context var32 = var0.getContext();
      Intrinsics.checkNotNullExpressionValue(var32, "getContext(...)");
      StringCompanionObject var17 = StringCompanionObject.INSTANCE;
      Locale var33 = Locale.US;
      String var18 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
      String var19 = var5.text();
      LaneBrain.Road var20 = var1.getRoad();
      int var26;
      if (var20 != null) {
         var26 = var20.getLanesOurWay();
      } else {
         var26 = 0;
      }

      String var22;
      if (var5.getRemember()) {
         int var12;
         label30: {
            var12 = var5.getThrough();
            if (var4 != null) {
               String var25 = var4.getName();
               var21 = var25;
               if (var25 != null) {
                  break label30;
               }
            }

            var21 = "?";
         }

         Long var24;
         if (var4 != null) {
            var24 = var4.getId();
         } else {
            var24 = null;
         }

         var22 = " " + var12 + " remembered for " + var21 + " (way " + var24 + ")";
      } else {
         var22 = var30;
      }

      String var23 = String.format(
         var33,
         "%s  %.6f,%.6f  %3.0f mph  COUNT you: %s (card said lane %d of %d, map %d)%s",
         Arrays.copyOf(new Object[]{var18, var8, var6, var10, var19, var2, var3, var26, var22}, 9)
      );
      Intrinsics.checkNotNullExpressionValue(var23, "format(...)");
      var29.appendLog(var32, var23);
      var0.invalidate();
      return Unit.INSTANCE;
   }

   private final void askReset() {
      LaneBrain var4 = this.tracker;
      if (var4 != null) {
         int var2 = this.nRight;
         int var1 = this.nRight;
         int var3 = this.nWrong;
         String var5 = "Your check score (" + var2 + " of " + (var1 + var3) + " right)";
         new Builder(this.getContext())
            .setTitle("Reset")
            .setItems(
               new String[]{"The lane and this drive's counts (relearns in a few seconds)", var5, "Both"},
               new LaneCardView$$ExternalSyntheticLambda4(var4, this)
            )
            .setNegativeButton("Cancel", null)
            .show();
      }
   }

   private static final void askReset$lambda$19(LaneBrain var0, LaneCardView var1, DialogInterface var2, int var3) {
      switch (var3) {
         case 0:
         case 2:
            LaneBrain.reset$default(var0, false, 1, null);
         case 1:
         default:
            switch (var3) {
               case 1:
               case 2:
                  var1.nRight = 0;
                  var1.nWrong = 0;
                  var1.prefs.edit().putInt("right", 0).putInt("wrong", 0).apply();
               default:
                  var1.lastCounted = false;
                  var1.flashOk = true;
                  var1.flashMs = System.currentTimeMillis();
                  String var4;
                  switch (var3) {
                     case 0:
                        var4 = "lane reset - relearning";
                        break;
                     case 1:
                        var4 = "score reset";
                        break;
                     default:
                        var4 = "lane and score reset";
                  }

                  var1.flash = var4;
                  var1.invalidate();
            }
      }
   }

   private final void check(boolean var1) {
      LaneBrain var19 = this.tracker;
      if (var19 != null) {
         Pair var16 = var19.laneFromLeft();
         int var10 = ((Number)var16.component1()).intValue();
         double var6 = ((Number)var16.component2()).doubleValue();
         var16 = var19.laneCount();
         int var9 = ((Number)var16.component1()).intValue();
         String var20 = (String)var16.component2();
         String var29;
         if (var10 > 0) {
            String var28;
            if (var9 > 0) {
               var28 = String.valueOf(var9);
            } else {
               var28 = "?";
            }

            var29 = "lane " + var10 + " of " + var28;
         } else {
            var29 = "no lane";
         }

         if (var10 > 0) {
            if (var1) {
               this.nRight++;
            } else {
               this.nWrong++;
            }

            this.prefs.edit().putInt("right", this.nRight).putInt("wrong", this.nWrong).apply();
         }

         LaneShots var21 = this.shots;
         if (var21 != null) {
            LaneShots.Companion var18 = LaneShots.Companion;
            String var17;
            if (var1) {
               var17 = "you tapped ✓ (right)";
            } else {
               var17 = "you tapped ✗ (wrong)";
            }

            LaneShots.request$default(var21, var18.caption(var19, var17, this.where.invoke().getThird().floatValue()), LaneShots.Why.USER, null, 4, null);
         }

         LaneBrain.check$default(var19, var1, 0L, 2, null);
         this.lastOk = var1;
         boolean var15;
         if (var10 > 0) {
            var15 = true;
         } else {
            var15 = false;
         }

         this.lastCounted = var15;
         this.lastCheckMs = System.currentTimeMillis();
         this.flashOk = var1;
         this.flashMs = System.currentTimeMillis();
         String var31;
         if (var10 == 0) {
            var31 = "card showed no lane - not counted";
         } else {
            StringBuilder var36 = null;
            if (var1) {
               var36 = new StringBuilder();
               var31 = "saved: right (";
            } else {
               var36 = new StringBuilder();
               var31 = "saved: wrong (";
            }

            var31 = var36.append(var31).append(var29).append(")").toString();
         }

         this.flash = var31;
         Triple var33 = this.where.invoke();
         double var2 = ((Number)var33.component1()).doubleValue();
         double var4 = ((Number)var33.component2()).doubleValue();
         float var8 = ((Number)var33.component3()).floatValue();
         LaneCardView.Companion var22 = Companion;
         Context var38 = this.getContext();
         Intrinsics.checkNotNullExpressionValue(var38, "getContext(...)");
         StringCompanionObject var34 = StringCompanionObject.INSTANCE;
         Locale var24 = Locale.US;
         String var23 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
         if (var1) {
            var31 = "RIGHT";
         } else {
            var31 = "WRONG";
         }

         String var37;
         if (var9 > 0) {
            var37 = "count from ";
         } else {
            var37 = "";
         }

         var10 = (int)(100 * var6);
         int var12 = var19.lanesLeft();
         var9 = var19.lanesRight();
         int var13 = this.nRight;
         int var11 = this.nRight;
         int var14 = this.nWrong;
         String var30 = String.format(
            var24,
            "%s  %.6f,%.6f  %3.0f mph  CHECK %s: card said %s (%s%s, %d%%) L=%d R=%d  score %d/%d",
            Arrays.copyOf(new Object[]{var23, var2, var4, var8, var31, var29, var37, var20, var10, var12, var9, var13, var11 + var14}, 13)
         );
         Intrinsics.checkNotNullExpressionValue(var30, "format(...)");
         var22.appendLog(var38, var30);
         this.performHapticFeedback(1);
      }
   }

   private final int confColor(double var1) {
      if (var1 >= 0.7) {
         short var10 = 199;
         byte var9 = 89;
         byte var11 = 52;
         return Color.rgb(var11, var10, var9);
      } else {
         byte var3;
         short var4;
         if (var1 >= 0.4) {
            var4 = 196;
            var3 = 0;
         } else {
            if (!(var1 > 0.0)) {
               var3 = 138;
               short var5 = 150;
               var4 = 130;
               return Color.rgb(var4, var3, var5);
            }

            var4 = 140;
            var3 = 60;
         }

         return Color.rgb(255, var4, var3);
      }
   }

   private final void line(Canvas var1, String var2, TextPaint var3, float var4, float var5) {
      var1.drawText(TextUtils.ellipsize(var2, var3, var5, TruncateAt.END).toString(), this.d * 14.0F, var4, (Paint)var3);
   }

   private static final void onDraw$button(LaneCardView var0, Canvas var1, RectF var2, int var3, String var4, boolean var5) {
      Paint var8 = var0.box;
      byte var7 = -1;
      int var6;
      if (var5) {
         var6 = -1;
      } else {
         var6 = var3;
      }

      var8.setColor(var6);
      var1.drawOval(var2, var0.box);
      TextPaint var10 = var0.btnText;
      var6 = var7;
      if (var5) {
         var6 = var3;
      }

      var10.setColor(var6);
      var1.drawText(var4, var2.centerX(), var2.centerY() + var0.d * 8.5F, (Paint)var0.btnText);
   }

   private static final CharSequence onDraw$lambda$10(LaneCardView var0, LaneBrain.Event var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      String var2 = var1.getText();
      String var3 = var0.ago(var1.getAtMs());
      return var2 + " " + var3;
   }

   private final void showHistory() {
      LaneBrain var6 = this.tracker;
      if (var6 != null) {
         SimpleDateFormat var4 = new SimpleDateFormat("HH:mm:ss", Locale.US);
         String var5 = var6.summary();
         int var2 = this.nRight;
         int var1 = this.nRight;
         int var3 = this.nWrong;
         var5 = "This drive: " + var5 + "\nYour checks: " + var2 + " of " + (var1 + var3) + " right\n\n";
         String var7;
         if (var6.getEvents().isEmpty()) {
            var7 = "Nothing yet.";
         } else {
            var7 = CollectionsKt.joinToString$default(var6.getEvents(), "\n", null, null, 0, null, new LaneCardView$$ExternalSyntheticLambda5(var4), 30, null);
         }

         String var8 = var5 + var7;
         new Builder(this.getContext())
            .setTitle("Lanes (newest first)")
            .setMessage(var8)
            .setPositiveButton("Close", null)
            .setNegativeButton("Reset…", new LaneCardView$$ExternalSyntheticLambda6(this))
            .setNeutralButton("Copy", new LaneCardView$$ExternalSyntheticLambda7(this, var8))
            .show();
      }
   }

   private static final CharSequence showHistory$lambda$20(SimpleDateFormat var0, LaneBrain.Event var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      String var2 = var0.format(new Date(var1.getAtMs()));
      String var3 = var1.getText();
      return var2 + "  " + var3;
   }

   private static final void showHistory$lambda$21(LaneCardView var0, DialogInterface var1, int var2) {
      var0.askReset();
   }

   private static final void showHistory$lambda$22(LaneCardView var0, String var1, DialogInterface var2, int var3) {
      Object var4 = var0.getContext().getSystemService("clipboard");
      Intrinsics.checkNotNull(var4, "null cannot be cast to non-null type android.content.ClipboardManager");
      ((ClipboardManager)var4).setPrimaryClip(ClipData.newPlainText("StarView lane log", var1));
   }

   private final void undo() {
      if (this.lastCounted) {
         if (this.lastOk) {
            this.nRight = Math.max(0, this.nRight - 1);
         } else {
            this.nWrong = Math.max(0, this.nWrong - 1);
         }

         this.prefs.edit().putInt("right", this.nRight).putInt("wrong", this.nWrong).apply();
         this.lastCounted = false;
         this.flashOk = true;
         this.flashMs = System.currentTimeMillis();
         String var4;
         if (this.lastOk) {
            var4 = "✓";
         } else {
            var4 = "✗";
         }

         this.flash = "undone - the " + var4 + " is not counted";
         LaneCardView.Companion var7 = Companion;
         Context var5 = this.getContext();
         Intrinsics.checkNotNullExpressionValue(var5, "getContext(...)");
         String var6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
         if (this.lastOk) {
            var4 = "RIGHT";
         } else {
            var4 = "WRONG";
         }

         int var2 = this.nRight;
         int var1 = this.nRight;
         int var3 = this.nWrong;
         var7.appendLog(var5, var6 + "  CHECK UNDONE (" + var4 + ")  score " + var2 + "/" + (var1 + var3));
         this.performHapticFeedback(1);
      }
   }

   private static final Triple where$lambda$0() {
      Double var0 = Double.NaN;
      return new Triple<>(var0, var0, 0.0F);
   }

   public final Function0<Pair<String, Boolean>> getAhead() {
      return this.ahead;
   }

   public final Function0<List<LaneRamps.Ramp>> getRamps() {
      return (Function0<List<LaneRamps.Ramp>>)this.ramps;
   }

   public final LaneShots getShots() {
      return this.shots;
   }

   public final LaneBrain getTracker() {
      return this.tracker;
   }

   public final Function0<Triple<Double, Double, Float>> getWhere() {
      return this.where;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      LaneBrain var33 = this.tracker;
      if (var33 != null) {
         float var11 = this.getWidth();
         float var12 = this.getHeight();
         float var8 = this.d * 14.0F;
         var1.drawRoundRect(new RectF(0.0F, 0.0F, var11, var12), this.d * 16.0F, this.d * 16.0F, this.bg);
         Pair var28 = var33.laneFromLeft();
         int var21 = ((Number)var28.component1()).intValue();
         double var2 = ((Number)var28.component2()).doubleValue();
         var28 = var33.laneCount();
         int var18 = ((Number)var28.component1()).intValue();
         String var29 = (String)var28.component2();
         int var19;
         if (var18 > 0) {
            var19 = Math.max(var18, var21);
         } else {
            var19 = 0;
         }

         Paint var90 = this.box;
         if (var21 > 0 && var2 < 0.6) {
            var18 = Color.rgb(130, 138, 150);
         } else {
            var18 = this.confColor(var2);
         }

         var90.setColor(var18);
         var1.drawRoundRect(new RectF(0.0F, 0.0F, this.d * 6.0F, var12), this.d * 3.0F, this.d * 3.0F, this.box);
         String var91 = var33.label();
         LaneBrain.RoadModel var30 = var33.getModel();
         if (var19 > 0) {
            var18 = var19;
         } else if (var21 > 0) {
            var18 = var21 + 1;
         } else {
            var18 = 0;
         }

         boolean var20;
         if (1 <= var18 && var18 < 9) {
            var20 = 1;
         } else {
            var20 = 0;
         }

         float var10;
         if (var20) {
            var10 = var18 * 18.0F * this.d + (var18 - 1) * 4.0F * this.d + var8;
         } else {
            var10 = 0.0F;
         }

         CharSequence var92 = var91;
         TextPaint var31 = this.big;
         float var9 = 2;
         var1.drawText(TextUtils.ellipsize(var92, var31, var11 - var8 * var9 - var10, TruncateAt.END).toString(), var8, this.d * 32.0F, (Paint)this.big);
         if (1 <= var18 && var18 < 9) {
            var20 = (boolean)1;
         } else {
            var20 = (boolean)0;
         }

         if (var20) {
            float var16 = this.d * 18.0F;
            float var14 = this.d * 4.0F;
            float var15 = this.d;
            if (var30.getLanes() > 0 && var19 == var30.getTotal()) {
               var20 = var30.getExtraL();
            } else {
               var20 = 0;
            }

            if (var30.getLanes() > 0 && var19 == var30.getTotal()) {
               var19 = var30.getExtraR();
            } else {
               var19 = 0;
            }

            var10 = var18;
            float var17 = var18 - 1;
            float var13 = this.d * 12.0F;
            int var22 = 1;
            if (1 <= var18) {
               var10 = var11 - var8 - var10 * var16 - var17 * var14;
               int var23 = var18;

               while (true) {
                  boolean var50;
                  if (var22 == var21) {
                     var50 = 1;
                  } else {
                     var50 = 0;
                  }

                  boolean var24;
                  if (var22 > var20 && var22 <= var23 - var19) {
                     var24 = false;
                  } else {
                     var24 = true;
                  }

                  Paint var132 = this.box;
                  Style var93;
                  if (var50) {
                     var93 = Style.FILL;
                  } else {
                     var93 = Style.STROKE;
                  }

                  var132.setStyle(var93);
                  Paint var94 = this.box;
                  var94.setStrokeWidth(this.d * 2.0F);
                  Paint var133 = this.box;
                  PathEffect var95;
                  if (var24 && !var50) {
                     var95 = (PathEffect)(new DashPathEffect(new float[]{this.d * 4.0F, this.d * 3.0F}, 0.0F));
                  } else {
                     var95 = null;
                  }

                  var133.setPathEffect(var95);
                  Paint var96 = this.box;
                  if (var50) {
                     if (var2 < 0.6) {
                        var50 = Color.rgb(130, 138, 150);
                     } else {
                        var50 = this.confColor(var2);
                     }
                  } else if (var24) {
                     var50 = Color.rgb(255, 196, 0);
                  } else {
                     var50 = Color.rgb(120, 130, 146);
                  }

                  var96.setColor(var50);
                  var1.drawRoundRect(new RectF(var10, var13, var10 + var16, var13 + var15 * 26.0F), this.d * 4.0F, this.d * 4.0F, this.box);
                  var10 += var16 + var14;
                  if (var22 == var23) {
                     break;
                  }

                  var22++;
                  var23 = var23;
               }
            }

            this.box.setStyle(Style.FILL);
            this.box.setPathEffect(null);
         }

         var10 = var11 - var9 * var8;
         var28 = var33.side(-1);
         var20 = ((Number)var28.component1()).intValue();
         var2 = ((Number)var28.component2()).doubleValue();
         var28 = var33.side(1);
         var18 = ((Number)var28.component1()).intValue();
         double var6 = ((Number)var28.component2()).doubleValue();
         double var4 = 100;
         var21 = (int)(var2 * var4);
         var19 = (int)(var4 * var6);
         this.line(var1, "lanes left of us: " + var20 + " (" + var21 + "%)  ·  right: " + var18 + " (" + var19 + "%)", this.small, this.d * 56.0F, var10);
         var28 = CollectionsKt.joinToString$default(
            CollectionsKt.take(var33.getWhy(), 2), "  ·  ", null, null, 0, null, new LaneCardView$$ExternalSyntheticLambda0(this), 30, null
         );
         boolean var53;
         if (var28.length() == 0) {
            var53 = 1;
         } else {
            var53 = 0;
         }

         if (var53) {
            var28 = "nothing seen yet";
         }

         this.line(var1, "why: " + var28, this.small, this.d * 74.0F, var10);
         LaneBrain.Road var134 = var33.getRoad();
         LaneBrain.RoadInfo var35 = var33.getInfo();
         StringBuilder var34 = new StringBuilder();
         var34.append("road: ");
         var53 = var30.getLanes();
         String var32 = "s";
         String var101;
         if (var53 > 0) {
            var53 = var30.getLanes();
            String var100;
            if (var30.getLanes() == 1) {
               var100 = "";
            } else {
               var100 = "s";
            }

            String var36 = var30.getSrc();
            var101 = var53 + " lane" + var100 + " (" + var36 + ")";
         } else {
            var101 = "count not known";
         }

         var34.append(var101);
         if (var35 != null && var35.getHpmsLanes() > 0 && !Intrinsics.areEqual(var30.getSrc(), "HPMS")) {
            var53 = var35.getHpmsLanes();
            var34.append(" · HPMS " + var53);
         }

         if (var134 != null) {
            var53 = var134.getLanesOurWay();
         } else {
            var53 = 0;
         }

         if (var53 > 0 && !Intrinsics.areEqual(var30.getSrc(), "map")) {
            Intrinsics.checkNotNull(var134);
            var53 = var134.getLanesOurWay();
            var34.append(" · map " + var53);
         }

         label290: {
            String var102;
            if (var30.getTunnel()) {
               var102 = " · tunnel";
            } else {
               if (!var30.getBridge()) {
                  break label290;
               }

               var102 = " · bridge";
            }

            var34.append(var102);
         }

         if (var35 != null) {
            boolean var59;
            if (var35.getExtraWhy().length() > 0) {
               var59 = true;
            } else {
               var59 = false;
            }

            if (var59) {
               String var103 = var35.getExtraWhy();
               var34.append(" · " + var103);
            }
         }

         boolean var60;
         if (var29.length() > 0) {
            var60 = 1;
         } else {
            var60 = 0;
         }

         if (var60) {
            var34.append("  → " + var29);
         }

         String var104 = var34.toString();
         Intrinsics.checkNotNullExpressionValue(var104, "toString(...)");
         this.line(var1, var104, this.dim, 91.0F * this.d, var10);
         LaneBrain.Event var105 = var33.getEvents().firstOrNull();
         String var107;
         if (var105 != null) {
            var29 = var105.getText();
            String var106 = this.ago(var105.getAtMs());
            var107 = var29 + " · " + var106;
         } else {
            label280: {
               if (var134 != null) {
                  var28 = var134.getName();
                  if (var28.length() == 0) {
                     var60 = (boolean)1;
                  } else {
                     var60 = (boolean)0;
                  }

                  if (var60) {
                     var28 = var134.getKind();
                  }

                  if (var134.getOneway()) {
                     var29 = " (one-way)";
                  } else {
                     var29 = "";
                  }

                  var29 = "on " + var28 + var29;
                  var107 = var29;
                  if (var29 != null) {
                     break label280;
                  }
               }

               var107 = "waiting for a turn, a ramp or a lane line";
            }
         }

         if (var33.getLastCameraMs() > 0L) {
            var29 = this.ago(var33.getLastCameraMs());
            var29 = "camera confirmed " + var29;
         } else {
            var29 = "camera: nothing yet";
         }

         String var128;
         if (Hpms.INSTANCE.files().isEmpty()) {
            var128 = "  ·  no road data (Settings › Lanes)";
         } else {
            var128 = "";
         }

         var19 = var33.knownPct();
         var60 = (int)(var33.getDriveS() / 60);
         this.line(var1, var29 + "  ·  lane known " + var19 + "% of " + var60 + " min" + var128, this.small, 109.0F * this.d, var10);

         label358: {
            try {
               var123 = this.ahead;
            } catch (Exception var39) {
               var122 = TuplesKt.to("", false);
               break label358;
            }

            if (var123 != null) {
               try {
                  var129 = (Pair)var123.invoke();
               } catch (Exception var38) {
                  var122 = TuplesKt.to("", false);
                  break label358;
               }

               var122 = var129;
               if (var129 != null) {
                  break label358;
               }
            }

            try {
               var122 = TuplesKt.to("", false);
            } catch (Exception var37) {
               var122 = TuplesKt.to("", false);
            }
         }

         String var130 = (String)var122.component1();
         boolean var25 = (Boolean)var122.component2();
         boolean var63;
         if (var130.length() > 0) {
            var63 = 1;
         } else {
            var63 = 0;
         }

         if (var63) {
            TextPaint var124 = this.stat;
            if (var25) {
               var63 = Color.rgb(255, 90, 80);
            } else {
               var63 = Color.rgb(255, 196, 0);
            }

            var124.setColor(var63);
            this.line(var1, "⚠ " + var130, this.stat, this.d * 129.0F, var10);
            this.stat.setColor(Color.rgb(225, 230, 240));
         } else {
            var63 = var33.getNChanges();
            if (var33.getNChanges() == 1) {
               var29 = "";
            } else {
               var29 = "s";
            }

            String var131;
            if (var33.getNUndone() > 0) {
               var19 = var33.getNUndone();
               var131 = " (" + var19 + " undone)";
            } else {
               var131 = "";
            }

            var19 = var33.getNTurns();
            String var135;
            if (var33.getNTurns() == 1) {
               var135 = "";
            } else {
               var135 = "s";
            }

            var20 = var33.getNRamps();
            if (var33.getNRamps() == 1) {
               var32 = "";
            }

            this.line(
               var1,
               "Drive: " + var63 + " lane change" + var29 + var131 + " · " + var19 + " turn" + var135 + " · " + var20 + " ramp" + var32,
               this.stat,
               this.d * 129.0F,
               var10
            );
         }

         int var77 = 0;
         this.line(var1, var107, this.dim, this.d * 147.0F, var10);
         var10 = this.d * 22.0F;
         var12 = var12 - this.d * 8.0F - var10;
         this.noR.set(var11 - var8 - var9 * var10, var12 - var10, var11 - var8, var12 + var10);
         this.okR.set(this.noR.left - this.d * 12.0F - var9 * var10, var12 - var10, this.noR.left - this.d * 12.0F, var12 + var10);
         this.fixR.set(this.okR.left - this.d * 12.0F - this.d * 64.0F, var12 - var10, this.okR.left - this.d * 12.0F, var12 + var10);
         Paint var109 = this.box;
         if (this.pressed == 3) {
            var63 = -1;
         } else {
            var63 = Color.rgb(52, 92, 170);
         }

         var109.setColor(var63);
         var1.drawRoundRect(this.fixR, var10, var10, this.box);
         TextPaint var110 = this.fixText;
         if (this.pressed == 3) {
            var63 = Color.rgb(52, 92, 170);
         } else {
            var63 = -1;
         }

         var110.setColor(var63);
         var1.drawText("Fix", this.fixR.centerX(), this.fixR.centerY() + this.d * 6.0F, (Paint)this.fixText);
         this.topR.set(0.0F, 0.0F, var11, this.d * 44.0F);
         RectF var111 = this.okR;
         var63 = Color.rgb(34, 150, 74);
         if (this.pressed == 1) {
            var25 = true;
         } else {
            var25 = false;
         }

         onDraw$button(this, var1, var111, var63, "✓", var25);
         RectF var112 = this.noR;
         var63 = Color.rgb(200, 52, 52);
         if (this.pressed == -1) {
            var25 = true;
         } else {
            var25 = false;
         }

         onDraw$button(this, var1, var112, var63, "✗", var25);
         var20 = this.nRight + this.nWrong;
         long var26 = System.currentTimeMillis();
         var9 = this.fixR.left - var8 - this.d * 8.0F;
         this.undoR.set(0.0F, var12 - var10, this.fixR.left - this.d * 4.0F, var12 + var10);
         int var70 = var77;
         if (this.lastCounted) {
            var70 = var77;
            if (var26 - this.lastCheckMs < this.UNDO_MS) {
               var70 = 1;
            }
         }

         if (var26 - this.flashMs >= 2500L && !var70) {
            String var114;
            if (var20 == 0) {
               var114 = "is the lane right? tap ✓ or ✗ (stopped / passenger)";
            } else {
               var77 = this.nRight;
               var70 = this.nRight * 100 / var20;
               var114 = "your checks: " + var77 + " of " + var20 + " right (" + var70 + "%)";
            }

            CharSequence var126 = var114;
            TextPaint var115;
            if (var20 == 0) {
               var115 = this.dim;
            } else {
               var115 = this.stat;
            }

            var29 = TextUtils.ellipsize(var126, var115, var9, TruncateAt.END).toString();
            var9 = this.d;
            TextPaint var116;
            if (var20 == 0) {
               var116 = this.dim;
            } else {
               var116 = this.stat;
            }

            var1.drawText(var29, var8, var9 * 5.0F + var12, (Paint)var116);
         } else {
            TextPaint var113 = this.stat;
            if (this.flashOk) {
               var77 = Color.rgb(90, 220, 120);
            } else {
               var77 = Color.rgb(255, 120, 110);
            }

            var113.setColor(var77);
            var1.drawText(TextUtils.ellipsize(this.flash, this.stat, var9, TruncateAt.END).toString(), var8, var12 - this.d * 3.0F, (Paint)this.stat);
            this.stat.setColor(Color.rgb(225, 230, 240));
            if (var70) {
               var26 = (this.UNDO_MS - (var26 - this.lastCheckMs)) / 1000;
               var1.drawText("tap here to undo (" + (var26 + 1L) + "s)", var8, this.d * 14.0F + var12, (Paint)this.dim);
            }
         }
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      boolean var5 = this.okR.contains(var1.getX(), var1.getY());
      boolean var4 = false;
      byte var2;
      if (var5) {
         var2 = 1;
      } else if (this.noR.contains(var1.getX(), var1.getY())) {
         var2 = -1;
      } else if (this.fixR.contains(var1.getX(), var1.getY()) || this.topR.contains(var1.getX(), var1.getY())) {
         var2 = 3;
      } else if (this.lastCounted && System.currentTimeMillis() - this.lastCheckMs < this.UNDO_MS && this.undoR.contains(var1.getX(), var1.getY())) {
         var2 = 2;
      } else {
         var2 = 0;
      }

      switch (var1.getActionMasked()) {
         case 0:
            if (var2 != 0) {
               this.pressed = var2;
               this.invalidate();
               return true;
            }
            break;
         case 1:
            if (this.pressed != 0) {
               int var3 = this.pressed;
               this.pressed = 0;
               if (var2 == var3) {
                  switch (var3) {
                     case 2:
                        this.undo();
                        break;
                     case 3:
                        this.askCount();
                        break;
                     default:
                        if (var3 == 1) {
                           var4 = true;
                        }

                        this.check(var4);
                  }
               }

               this.invalidate();
               return true;
            }
            break;
         case 2:
            if (this.pressed != 0) {
               return true;
            }
            break;
         case 3:
            if (this.pressed != 0) {
               this.pressed = 0;
               this.invalidate();
               return true;
            }
      }

      return super.onTouchEvent(var1);
   }

   public final void setAhead(Function0<Pair<String, Boolean>> var1) {
      this.ahead = var1;
   }

   public final void setRamps(Function0<? extends List<LaneRamps.Ramp>> var1) {
      this.ramps = var1;
   }

   public final void setShots(LaneShots var1) {
      this.shots = var1;
   }

   public final void setTracker(LaneBrain var1) {
      this.tracker = var1;
   }

   public final void setWhere(Function0<Triple<Double, Double, Float>> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.where = var1;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      private final void appendLog(Context var1, String var2) {
         long var3;
         File var5;
         try {
            var5 = new File(var1.getFilesDir(), "lane_log.txt");
            var3 = var5.length();
         } catch (Exception var10) {
            return;
         }

         if (var3 > 1000000L) {
            try {
               String var6 = CollectionsKt.joinToString$default(
                  CollectionsKt.takeLast(FilesKt.readLines$default(var5, null, 1, null), 3000), "\n", null, null, 0, null, null, 62, null
               );
               StringBuilder var11 = new StringBuilder();
               FilesKt.writeText$default(var5, var11.append(var6).append("\n").toString(), null, 2, null);
            } catch (Exception var9) {
               return;
            }
         }

         try {
            var12 = new StringBuilder();
         } catch (Exception var8) {
            return;
         }

         try {
            FilesKt.appendText$default(var5, var12.append(var2).append("\n").toString(), null, 2, null);
         } catch (Exception var7) {
         }
      }

      public final void log(Context var1, LaneBrain var2, LaneBrain.Event var3, double var4, double var6, float var8) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var2, "tr");
         Intrinsics.checkNotNullParameter(var3, "e");
         Pair var14 = var2.laneFromLeft();
         int var12 = ((Number)var14.component1()).intValue();
         double var9 = ((Number)var14.component2()).doubleValue();
         var14 = var2.laneCount();
         int var11 = ((Number)var14.component1()).intValue();
         String var16 = (String)var14.component2();
         String var15 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(var3.getAtMs()));
         StringCompanionObject var24 = StringCompanionObject.INSTANCE;
         String var17 = String.format(Locale.US, "  %.6f,%.6f  %3.0f mph  ", Arrays.copyOf(new Object[]{var4, var6, var8}, 3));
         Intrinsics.checkNotNullExpressionValue(var17, "format(...)");
         String var19 = var3.getText();
         String var18 = var2.label();
         String var25 = "?";
         Object var20;
         if (var12 > 0) {
            var20 = var12;
         } else {
            var20 = "?";
         }

         if (var11 > 0) {
            var25 = var11 + " (" + var16 + ")";
         }

         int var13 = (int)(100 * var9);
         var12 = var2.lanesLeft();
         var11 = var2.lanesRight();
         this.appendLog(var1, var15 + var17 + var19 + "  => " + var18 + " [lane " + var20 + " of " + var25 + "], " + var13 + "%  L=" + var12 + " R=" + var11);
      }
   }
}
