package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

public final class DriveState {
   public static final DriveState INSTANCE = new DriveState();
   private static volatile long fetchedAt;
   private static volatile String modeNow = "default";
   private static volatile boolean restarting;

   // $VF: synthetic method
   public static Unit $r8$lambda$0J3iX2xtecLFuZ5TBbH285D1tIE(Function0 var0, JSONObject var1, String var2) {
      return mode$lambda$1(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4a96r2mG2LAqeX4ht0moIGrQoa0(Activity var0, Handler var1, Function1 var2, String var3, JSONObject var4) {
      return restart$lambda$4(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$i7AGpvPcZ1Xlkqp5YqjQ2xrBLU0(Activity var0, Function1 var1, JSONObject var2) {
      return request$lambda$2(var0, var1, var2);
   }

   private DriveState() {
   }

   // $VF: synthetic method
   public static String mode$default(DriveState var0, boolean var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = false;
      }

      if ((var3 & 2) != 0) {
         var2 = null;
      }

      return var0.mode(var1, var2);
   }

   private static final Unit mode$lambda$1(Function0 var0, JSONObject var1, String var2) {
      String var3;
      if (var1 != null) {
         var3 = var1.optString("mode");
      } else {
         var3 = null;
      }

      var2 = "onroad";
      String var4;
      if (Intrinsics.areEqual(var3, "onroad")) {
         var4 = var2;
      } else {
         var2 = "offroad";
         if (Intrinsics.areEqual(var3, "offroad")) {
            var4 = var2;
         } else {
            if (var3 == null) {
               return Unit.INSTANCE;
            }

            var4 = "default";
         }
      }

      if (!Intrinsics.areEqual(var4, modeNow)) {
         modeNow = var4;
         if (var0 != null) {
            var0.invoke();
         }
      }

      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void request$default(DriveState var0, Activity var1, String var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = null;
      }

      var0.request(var1, var2, var3);
   }

   private static final Unit request$lambda$2(Activity var0, Function1 var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "ack");
      boolean var3 = var2.optBoolean("ok");
      if (!var3) {
         Toast.makeText((Context)var0, var2.optString("error", "rejected"), 0).show();
      }

      mode$default(INSTANCE, true, null, 2, null);
      if (var1 != null) {
         var1.invoke(var3);
      }

      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void restart$default(DriveState var0, Activity var1, Function1 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.restart(var1, var2);
   }

   private static final void restart$done(Activity var0, Function1<? super Boolean, Unit> var1, boolean var2, String var3) {
      restarting = false;
      Toast.makeText((Context)var0, var3, 1).show();
      mode$default(INSTANCE, true, null, 2, null);
      if (var1 != null) {
         var1.invoke(var2);
      }
   }

   private static final Unit restart$lambda$4(Activity var0, Handler var1, Function1 var2, String var3, JSONObject var4) {
      Intrinsics.checkNotNullParameter(var4, "ack");
      if (!var4.optBoolean("ok")) {
         String var5 = var4.optString("error", "The comma refused to go offroad");
         Intrinsics.checkNotNullExpressionValue(var5, "optString(...)");
         restart$done(var0, var2, false, var5);
         return Unit.INSTANCE;
      } else {
         Toast.makeText((Context)var0, "Restarting openpilot: going offroad…", 0).show();
         var1.postDelayed(
            new Anon_restart_1_wait_1(System.currentTimeMillis(), var1, var3, var0, var2),
            1000L
         );
         return Unit.INSTANCE;
      }
   }

   public final boolean engaged() {
      Telemetry var3 = Link.INSTANCE.getTelemetry();
      boolean var2 = false;
      boolean var1 = var2;
      if (var3 != null) {
         if (!var3.getEnabled() && !var3.iBool("engaged", false)) {
            return var2;
         }

         var1 = true;
      }

      return var1;
   }

   public final boolean forcedOffroad() {
      return Intrinsics.areEqual(mode$default(this, false, null, 3, null), "offroad");
   }

   public final boolean forcedOnroad() {
      return Intrinsics.areEqual(mode$default(this, false, null, 3, null), "onroad");
   }

   public final boolean getRestarting() {
      return restarting;
   }

   public final String mode(boolean var1, Function0<Unit> var2) {
      long var3 = System.currentTimeMillis();
      if (var1 || var3 - fetchedAt >= 3000L) {
         fetchedAt = var3;
         Galaxy7 var5 = Galaxy7.Companion.current();
         if (var5 != null) {
            var5.get("/api/drive-state/status", new DriveState$$ExternalSyntheticLambda1(var2));
         }
      }

      return modeNow;
   }

   public final Boolean onroad() {
      Telemetry var3 = Link.INSTANCE.getTelemetry();
      if (var3 == null) {
         return null;
      } else {
         DataClient var4 = Link.INSTANCE.getData();
         boolean var2 = false;
         boolean var1 = var2;
         if (var4 != null) {
            var1 = var2;
            if (var4.getConnected()) {
               var1 = true;
            }
         }

         if (!var1) {
            return null;
         } else {
            var4 = (DataClient)var3.getInfo().get("onroad");
            return var4 == null ? null : var3.iBool("onroad", Intrinsics.areEqual(var4, true));
         }
      }
   }

   public final void request(Activity var1, String var2, Function1<? super Boolean, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "mode");
      DataClient var4 = Link.INSTANCE.getData();
      if (var4 != null && var4.getConnected()) {
         var4.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", var2)), new DriveState$$ExternalSyntheticLambda2(var1, var3));
      } else {
         Toast.makeText((Context)var1, "Not connected to the comma", 0).show();
         if (var3 != null) {
            var3.invoke(false);
         }
      }
   }

   public final void restart(Activity var1, Function1<? super Boolean, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      if (!restarting) {
         DataClient var5 = Link.INSTANCE.getData();
         if (var5 != null && var5.getConnected()) {
            String var3 = modeNow;
            if (!Intrinsics.areEqual(var3, "onroad")) {
               var3 = null;
            }

            String var4 = var3;
            if (var3 == null) {
               var4 = "default";
            }

            Handler var6 = new Handler(Looper.getMainLooper());
            restarting = true;
            var5.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", "offroad")), new DriveState$$ExternalSyntheticLambda0(var1, var6, var2, var4));
         } else {
            Toast.makeText((Context)var1, "Not connected to the comma", 0).show();
            if (var2 != null) {
               var2.invoke(false);
            }
         }
      }
   }

   public static final class OffroadBar {
      private final Activity act;
      private final TextView desc;
      private String lastLook;
      private boolean pending;
      private final Switch sw;
      private boolean syncing;
      private final TextView title;
      private final CarUi ui;
      private final LinearLayout view;

      // $VF: synthetic method
      public static Unit $r8$lambda$7Y1qOLTPOLFu4436c10vd8Ocyxo(DriveState.OffroadBar var0, boolean var1) {
         return send$lambda$9(var0, var1);
      }

      // $VF: synthetic method
      public static void $r8$lambda$QJJTQflDOwW4ANcg_P3tth6xE9M/* $VF was: $r8$lambda$QJJTQflDOwW4ANcg_P3tth6xE9M*/(
         DriveState.OffroadBar var0, CompoundButton var1, boolean var2
      ) {
         _init_$lambda$8(var0, var1, var2);
      }

      public OffroadBar(Activity var1) {
         Intrinsics.checkNotNullParameter(var1, "act");
         super();
         this.act = var1;
         this.ui = new CarUi((Context)this.act, false, 2, null);
         TextView var3 = new TextView((Context)this.act);
         var3.setTextColor(CarUi.Companion.getTEXT());
         var3.setTextSize(17.0F);
         var3.setTypeface(Typeface.DEFAULT_BOLD);
         var3.setText("Offroad");
         this.title = var3;
         TextView var4 = new TextView((Context)this.act);
         var4.setTextColor(CarUi.Companion.getDIM());
         var4.setTextSize(13.0F);
         this.desc = var4;
         Switch var5 = new Switch((Context)this.act);
         var5.setScaleX(1.15F);
         var5.setScaleY(1.15F);
         var5.setPadding(this.ui.dp(12), 0, 0, 0);
         this.sw = var5;
         this.lastLook = "";
         LinearLayout var6 = new LinearLayout((Context)this.act);
         var6.setOrientation(0);
         var6.setGravity(16);
         var6.setPadding(this.ui.dp(16), this.ui.dp(4), this.ui.dp(12), this.ui.dp(4));
         var6.setVisibility(8);
         var6.setMinimumWidth(this.ui.dp(240));
         LinearLayout var2 = new LinearLayout((Context)this.act);
         var2.setOrientation(1);
         var2.addView((View)this.title);
         var2.addView((View)this.desc);
         var6.addView((View)var2, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
         var6.addView((View)this.sw);
         this.view = var6;
         this.sw.setOnCheckedChangeListener(new DriveState$OffroadBar$$ExternalSyntheticLambda3(this));
      }

      private static final void _init_$lambda$8(DriveState.OffroadBar var0, CompoundButton var1, boolean var2) {
         if (!var0.syncing) {
            if (var2) {
               new Builder((Context)var0.act)
                  .setTitle("Go offroad?")
                  .setMessage("openpilot stops all driving processes and will NOT drive until you switch this back off (Auto).")
                  .setPositiveButton("Go offroad", new DriveState$OffroadBar$$ExternalSyntheticLambda0(var0))
                  .setNegativeButton("Cancel", new DriveState$OffroadBar$$ExternalSyntheticLambda1(var0))
                  .setOnCancelListener(new DriveState$OffroadBar$$ExternalSyntheticLambda2(var0))
                  .show();
            } else {
               var0.send("default");
            }
         }
      }

      static final void lambda$8$lambda$5(DriveState.OffroadBar var0, DialogInterface var1, int var2) {
         var0.send("offroad");
      }

      static final void lambda$8$lambda$6(DriveState.OffroadBar var0, DialogInterface var1, int var2) {
         var0.sync(true);
      }

      static final void lambda$8$lambda$7(DriveState.OffroadBar var0, DialogInterface var1) {
         var0.sync(true);
      }

      private final void send(String var1) {
         this.pending = true;
         DriveState.INSTANCE.request(this.act, var1, new DriveState$OffroadBar$$ExternalSyntheticLambda4(this));
      }

      private static final Unit send$lambda$9(DriveState.OffroadBar var0, boolean var1) {
         var0.pending = false;
         var0.sync(true);
         return Unit.INSTANCE;
      }

      public final LinearLayout getView() {
         return this.view;
      }

      public final void sync(boolean var1) {
         boolean var5 = DriveState.INSTANCE.forcedOffroad();
         Boolean var6 = DriveState.INSTANCE.onroad();
         boolean var4 = true;
         boolean var2;
         if (!var1 || !var5 && !Intrinsics.areEqual(var6, true)) {
            var2 = 0;
         } else {
            var2 = 1;
         }

         byte var3;
         if (var2) {
            var3 = 0;
         } else {
            var3 = 8;
         }

         if (this.view.getVisibility() != var3) {
            this.view.setVisibility(var3);
         }

         if (var2) {
            var1 = DriveState.INSTANCE.engaged();
            String var12 = var5 + "|" + var1;
            if (!Intrinsics.areEqual(var12, this.lastLook)) {
               this.lastLook = var12;
               LinearLayout var7 = this.view;
               CarUi var13 = this.ui;
               if (var5) {
                  var2 = Color.rgb(70, 22, 28);
               } else {
                  var2 = CarUi.Companion.getCARD_ON();
               }

               if (var5) {
                  var3 = Color.rgb(201, 34, 49);
               } else {
                  var3 = CarUi.Companion.getWARN();
               }

               var7.setBackground((Drawable)var13.rounded(var2, 12, var3));
               TextView var18 = this.title;
               String var14;
               if (var5) {
                  var14 = "Forced offroad";
               } else {
                  var14 = "Offroad";
               }

               var18.setText(var14);
               TextView var19 = this.desc;
               String var15;
               if (var5) {
                  var15 = "won't drive · tap to return";
               } else if (var1) {
                  var15 = "disengage first";
               } else {
                  var15 = "tap to go offroad";
               }

               CharSequence var16 = var15;
               var19.setText(var16);
            }

            this.syncing = true;
            if (this.sw.isChecked() != var5) {
               this.sw.setChecked(var5);
            }

            Switch var17 = this.sw;
            if (this.pending || !var5 && var1) {
               var1 = false;
            } else {
               var1 = var4;
            }

            var17.setEnabled(var1);
            this.syncing = false;
         }
      }
   }

   static final class Anon_restart_1_wait_1 implements Runnable {
      Anon_restart_1_wait_1(long var1, Handler var3x, String var4x, Activity var5, Function1<Boolean, Unit> var6) {
         this.$t0 = var1;
         this.$main = var3x;
         this.$back = var4x;
         this.$act = var5;
         this.$then = var6;
      }

               final Activity $act;
               final String $back;
               final Handler $main;
               final long $t0;
               final Function1<Boolean, Unit> $then;

               // $VF: synthetic method
               public static Unit $r8$lambda$or5dfPyJjm5XLFvlu__QhWkcT7Q(String var0, Activity var1, Function1 var2, JSONObject var3) {
                  return run$lambda$1$lambda$0(var0, var1, var2, var3);
               }

               // $VF: synthetic method
               public static void $r8$lambda$th_akg7FZ29ta3z9p_TG2bSH7Gs/* $VF was: $r8$lambda$th_akg7FZ29ta3z9p_TG2bSH7Gs*/(
                  String var0, Activity var1, Function1 var2
               ) {
                  run$lambda$1(var0, var1, var2);
               }

               private static final void run$lambda$1(String var0x, Activity var1x, Function1 var2x) {
                  DataClient var3x = Link.INSTANCE.getData();
                  if (var3x != null && var3x.getConnected()) {
                     var3x.request(
                        "drive_state", MapsKt.mapOf(TuplesKt.to("mode", var0x)), new DriveState$restart$1$wait$1$$ExternalSyntheticLambda0(var0x, var1x, var2x)
                     );
                  } else {
                     if (Intrinsics.areEqual(var0x, "onroad")) {
                        var0x = "Onroad";
                     } else {
                        var0x = "Auto";
                     }

                     DriveState.restart$done(var1x, var2x, false, "Lost the comma - set the drive state back to " + var0x + " in Settings");
                  }
               }

               private static final Unit run$lambda$1$lambda$0(String var0x, Activity var1x, Function1 var2x, JSONObject var3x) {
                  Intrinsics.checkNotNullParameter(var3x, "a2");
                  if (var3x.optBoolean("ok")) {
                     if (Intrinsics.areEqual(var0x, "onroad")) {
                        var0x = "Onroad";
                     } else {
                        var0x = "Auto";
                     }

                     DriveState.restart$done(var1x, var2x, true, "openpilot is starting again (" + var0x + ") - the big model reloads in a minute or so");
                  } else {
                     var0x = var3x.optString("error", "rejected");
                     DriveState.restart$done(var1x, var2x, false, "Couldn't switch back: " + var0x + " - set it in Settings › Drive state");
                  }

                  return Unit.INSTANCE;
               }

               @Override
               public void run() {
                  boolean var5 = Intrinsics.areEqual(DriveState.INSTANCE.onroad(), false);
                  long var3x = System.currentTimeMillis();
                  long var1x = this.$t0;
                  if (!var5 && var3x - var1x < 25000L) {
                     this.$main.postDelayed(this, 500L);
                  } else {
                     Handler var7 = this.$main;
                     DriveState$restart$1$wait$1$$ExternalSyntheticLambda1 var6 = new DriveState$restart$1$wait$1$$ExternalSyntheticLambda1(
                        this.$back, this.$act, this.$then
                     );
                     if (var5) {
                        var1x = 3000L;
                     } else {
                        var1x = 0L;
                     }

                     var7.postDelayed(var6, var1x);
                  }
               }
               }
}
