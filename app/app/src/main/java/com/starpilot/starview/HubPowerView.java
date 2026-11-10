package com.starpilot.starview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class HubPowerView extends CardPage {
   private String err;
   private final Runnable poll;
   private boolean shown;
   private JSONObject status;
   private final SimpleDateFormat timeFmt;

   // $VF: synthetic method
   public static Unit $r8$lambda$0cGQSXJen7dg3TIs0Ccl9Km6moc(boolean var0, Function0 var1) {
      return stepRow$lambda$14$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$3p0JS_3QKxHfsxaAFhp9S_Jw2uU(HubPowerView var0, int var1) {
      return paint$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5FEortJxe24_Mic8id4ggDkkEvM(HubPowerView var0, double var1) {
      return paint$lambda$19(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5PDJEOwIzbe1Thuw_69q5l3EdCo(HubPowerView var0, int var1) {
      return paint$lambda$16(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8HVrgb6NVsTdzh94Dp1umjZSpgA(HubPowerView var0, double var1) {
      return paint$lambda$18(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9O8UOrbm9m3ylEDfHE681zoZMq4(HubPowerView var0, boolean var1) {
      return paint$lambda$15(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AaWGn8x2_RhDiuVwPx8g8VP7eTY/* $VF was: $r8$lambda$AaWGn8x2_RhDiuVwPx8g8VP7eTY*/(HubPowerView var0, int var1) {
      return paint$lambda$22(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LJrhm7CVZhOOtK6B3K1aW6KhR18(HubPowerView var0, JSONObject var1) {
      return load$lambda$0(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$VAUUWD9FKLVQcRqZn2nJp6qik4Y(HubPowerView var0, int var1) {
      return paint$lambda$24(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Y_oe_FSmhmPM4hlGOJpluTNSe5E/* $VF was: $r8$lambda$Y_oe_FSmhmPM4hlGOJpluTNSe5E*/(
      Function1 var0, CompoundButton var1, boolean var2
   ) {
      switchRow$lambda$7$lambda$6$lambda$5(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fBDXRWIgi3zvhLXRBH399T2JFxk(boolean var0, Function0 var1) {
      return stepRow$lambda$14$lambda$9(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$t4pK1QalFSI7MFRVgIRbtGAgKvQ(HubPowerView var0, boolean var1) {
      return paint$lambda$20(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tAJ8nPoogsBoohVM46dXJTx2N_Q/* $VF was: $r8$lambda$tAJ8nPoogsBoohVM46dXJTx2N_Q*/(HubPowerView var0, int var1) {
      return paint$lambda$21(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xYeWVl95ITDOhLFOHNBReFswfPw(HubPowerView var0, JSONObject var1) {
      return send$lambda$1(var0, var1);
   }

   public HubPowerView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
      this.timeFmt = new SimpleDateFormat("EEE h:mm a", Locale.US);
      this.poll = new Runnable() {

         @Override
         public void run() {
            if (HubPowerView.this.shown) {
               HubPowerView.this.load();
               HubPowerView.this.getMain().postDelayed(this, 5000L);
            }
         }
      };
   }

   private final void load() {
      req$default(this, "status", null, new HubPowerView$$ExternalSyntheticLambda4(this), 2, null);
   }

   private static final Unit load$lambda$0(HubPowerView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      String var2;
      if (var1.has("delay_min")) {
         var0.status = var1;
         var2 = null;
      } else {
         var2 = var1.optString("error", "The comma didn't answer");
      }

      var0.err = var2;
      var0.paint();
      return Unit.INSTANCE;
   }

   private final void paint() {
      this.getCol().removeAllViews();
      JSONObject var8 = this.status;
      LinearLayout var9 = this.card();
      this.add((View)var9);
      var9.addView(this.title("Power management", "power", "device"));
      if (var8 == null) {
         CardPage var44 = this;
         String var39 = this.err;
         String var38 = var39;
         if (var39 == null) {
            var38 = "Asking the comma…";
         }

         var9.addView((View)CardPage.line$default(var44, var38, 0, 0.0F, 6, null));
      } else {
         var9.addView(
            (View)CardPage.line$default(
               this,
               "What keeps running on the car battery after you turn the car off - the comma itself, and the USB hub with everything on it. Only turning the car off counts; going offroad with the car on doesn't.",
               0,
               0.0F,
               6,
               null
            )
         );
         JSONObject var7 = var8.optJSONObject("comma");
         var9 = this.card();
         this.add((View)var9);
         var9.addView(this.title("Comma", "device", "device"));
         if (var7 == null) {
            var9.addView((View)CardPage.line$default(this, "Not available on this comma.", 0, 0.0F, 6, null));
         } else {
            boolean var6 = var7.optBoolean("editable");
            boolean var5 = var7.optBoolean("enabled");
            int var3 = var7.optInt("hours", 30);
            double var1 = var7.optDouble("volts", 11.8);
            if (!var7.optBoolean("valid", true)) {
               var9.addView((View)this.line("Galaxy's saved Parked Power can't be read - switch it off to go back to stock.", CarUi.Companion.getWARN(), 15.0F));
            }

            String var23;
            if (var5) {
               var23 = "Your limits below. The comma turns itself off after the time, or sooner if the battery drops to the cutoff.";
            } else {
               var23 = "Off = stock: the comma stays on up to 30 h after you park and turns off only at 11.8 V.";
            }

            var9.addView(this.switchRow("Parked Power", var23, var5, var6, new HubPowerView$$ExternalSyntheticLambda0(this)));
            if (var5) {
               var9.addView(
                  this.stepRow(
                     "Maximum parked time",
                     var3 + " h",
                     var6,
                     new HubPowerView$$ExternalSyntheticLambda5(this, var3),
                     new HubPowerView$$ExternalSyntheticLambda6(this, var3)
                  )
               );
               StringCompanionObject var24 = StringCompanionObject.INSTANCE;
               String var25 = String.format(Locale.US, "%.1f V", Arrays.copyOf(new Object[]{var1}, 1));
               Intrinsics.checkNotNullExpressionValue(var25, "format(...)");
               var9.addView(
                  this.stepRow(
                     "Low-voltage cutoff",
                     var25,
                     var6,
                     new HubPowerView$$ExternalSyntheticLambda7(this, var1),
                     new HubPowerView$$ExternalSyntheticLambda8(this, var1)
                  )
               );
            }

            if (!var6) {
               var9.addView((View)this.line("These change with the car parked (openpilot offroad), like in Galaxy.", CarUi.Companion.getDIM(), 14.0F));
            }
         }

         boolean var21 = var8.optBoolean("on");
         int var4 = var8.optInt("delay_min", 5);
         LinearLayout var10 = this.card();
         this.add((View)var10);
         var10.addView(this.title("USB hub", "link", "device"));
         String var26;
         if (!var8.optBoolean("can_switch")) {
            var26 = "The comma can't switch its USB port - the hub stays on until the comma turns off.";
         } else {
            label122: {
               if (!var8.isNull("error")) {
                  String var27 = var8.optString("error");
                  Intrinsics.checkNotNullExpressionValue(var27, "optString(...)");
                  boolean var16;
                  if (var27.length() > 0) {
                     var16 = true;
                  } else {
                     var16 = false;
                  }

                  if (var16) {
                     String var30 = var8.optString("error");
                     var26 = "Couldn't switch the port: " + var30;
                     break label122;
                  }
               }

               if (!var21) {
                  var26 = "Off: the hub stays on until the comma itself turns off.";
               } else if (Intrinsics.areEqual(var8.optString("car"), "on")) {
                  var26 = "Car on - the hub stays on.";
               } else if (Intrinsics.areEqual(var8.optString("car"), "off") && !var8.isNull("cut_in_s")) {
                  String var29 = HubDelay.INSTANCE.text(Math.max(1, (var8.optInt("cut_in_s") + 59) / 60));
                  var26 = "Car off - the hub turns off in " + var29 + ".";
               } else if (Intrinsics.areEqual(var8.optString("port"), "off")) {
                  var26 = "The hub is off. It comes back when you start the car (or unlock it / open a door).";
               } else {
                  String var28 = HubDelay.INSTANCE.text(var4);
                  var26 = "Turns the hub off " + var28 + " after the car.";
               }
            }
         }

         label110: {
            HubPowerView$$ExternalSyntheticLambda9 var11 = new HubPowerView$$ExternalSyntheticLambda9(this);
            var41 = "off";
            var10.addView(this.switchRow("Turn the hub off after the car", var26, var21, true, var11));
            if (var8.optBoolean("can_switch")) {
               if (var8.isNull("error")) {
                  break label110;
               }

               String var45 = var8.optString("error");
               Intrinsics.checkNotNullExpressionValue(var45, "optString(...)");
               boolean var17;
               if (var45.length() > 0) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               if (!var17) {
                  break label110;
               }
            }

            var10.addView((View)this.line(var26, CarUi.Companion.getWARN(), 15.0F));
         }

         if (var21) {
            var10.addView(
               this.stepRow(
                  "Turn off after",
                  HubDelay.INSTANCE.text(var4),
                  true,
                  new HubPowerView$$ExternalSyntheticLambda10(this, var4),
                  new HubPowerView$$ExternalSyntheticLambda11(this, var4)
               )
            );
            CarUi var46 = this.getUi();
            Iterable var31 = HubDelay.INSTANCE.getPRESETS();
            var21 = false;
            var var13 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var31, 10));
            Iterator var12 = var31.iterator();

            while (var12.hasNext()) {
               int var18 = ((Number)var12.next()).intValue();
               var13.add(HubDelay.INSTANCE.text(var18));
            }

            List var32 = var13;
            Pair var47 = var46.chips(var32, new HubPowerView$$ExternalSyntheticLambda12(this));
            LinearLayout var33 = (LinearLayout)var47.component1();
            ((Function1)var47.component2()).invoke(HubDelay.INSTANCE.getPRESETS().indexOf(var4));
            View var52 = (View)var33;
            LayoutParams var48 = CarUi.lp$default(this.getUi(), 0, 4, 1, null);
            var48.topMargin = this.getUi().dp(6);
            Unit var34 = Unit.INSTANCE;
            var10.addView(var52, (android.view.ViewGroup.LayoutParams)var48);
            double var14 = var8.optDouble("hub_cutoff_v", 12.0);
            String var35;
            if (var8.isNull("battery_v")) {
               var35 = "";
            } else {
               StringCompanionObject var36 = StringCompanionObject.INSTANCE;
               var35 = String.format(Locale.US, " Battery now %.1f V.", Arrays.copyOf(new Object[]{var8.optDouble("battery_v")}, 1));
               Intrinsics.checkNotNullExpressionValue(var35, "format(...)");
            }

            StringCompanionObject var49 = StringCompanionObject.INSTANCE;
            String var50 = String.format(
               Locale.US,
               "Also off right away if the battery drops below %.1f V - 0.2 V above the comma's own cutoff, so the hub goes first.",
               Arrays.copyOf(new Object[]{var14}, 1)
            );
            Intrinsics.checkNotNullExpressionValue(var50, "format(...)");
            var10.addView((View)this.line(var50 + var35, CarUi.Companion.getDIM(), 14.0F));
            var10.addView(
               (View)this.line(
                  "The key fob's window closing and StarLock's window close need the hub (the radio dongle and relay box are on it), so after parking they work for as long as the hub is on.",
                  CarUi.Companion.getDIM(),
                  14.0F
               )
            );
         }

         LinearLayout var51 = this.card();
         this.add((View)var51);
         var51.addView(this.title("Recent", "history", "device"));
         JSONArray var43 = var8.optJSONArray("events");
         if (var43 != null && var43.length() != 0) {
            int var19 = 0;
            var4 = var43.length();

            for (String var37 = var41; var19 < var4; var19++) {
               JSONObject var53 = var43.optJSONObject(var19);
               if (var53 != null) {
                  String var42;
                  if (Intrinsics.areEqual(var53.optString("what"), var37)) {
                     var42 = "Hub off";
                  } else {
                     var42 = "Hub on";
                  }

                  SimpleDateFormat var55 = this.timeFmt;
                  double var15 = var53.optDouble("t");
                  String var56 = var55.format(new Date((long)(1000 * var15)));
                  String var54 = var53.optString("why");
                  var51.addView((View)this.line(var56 + "  ·  " + var42 + "  ·  " + var54, CarUi.Companion.getTEXT(), 15.0F));
               }
            }
         } else {
            var51.addView((View)CardPage.line$default(this, "Nothing yet.", 0, 0.0F, 6, null));
         }
      }
   }

   private static final Unit paint$lambda$15(HubPowerView var0, boolean var1) {
      var0.send("comma_set", MapsKt.mapOf(TuplesKt.to("enabled", var1)));
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$16(HubPowerView var0, int var1) {
      var0.send("comma_set", MapsKt.mapOf(TuplesKt.to("hours", RangesKt.coerceAtLeast(var1 - 1, 1))));
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$17(HubPowerView var0, int var1) {
      var0.send("comma_set", MapsKt.mapOf(TuplesKt.to("hours", RangesKt.coerceAtMost(var1 + 1, 30))));
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$18(HubPowerView var0, double var1) {
      var0.send("comma_set", MapsKt.mapOf(TuplesKt.to("volts", Math.max(11.8, var1 - 0.1))));
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$19(HubPowerView var0, double var1) {
      var0.send("comma_set", MapsKt.mapOf(TuplesKt.to("volts", Math.min(12.5, 0.1 + var1))));
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$20(HubPowerView var0, boolean var1) {
      var0.send("set", MapsKt.mapOf(TuplesKt.to("on", var1)));
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$21(HubPowerView var0, int var1) {
      var0.send("set", MapsKt.mapOf(TuplesKt.to("delay_min", HubDelay.INSTANCE.step(var1, false))));
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$22(HubPowerView var0, int var1) {
      var0.send("set", MapsKt.mapOf(TuplesKt.to("delay_min", HubDelay.INSTANCE.step(var1, true))));
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$24(HubPowerView var0, int var1) {
      var0.send("set", MapsKt.mapOf(TuplesKt.to("delay_min", HubDelay.INSTANCE.getPRESETS().get(var1))));
      return Unit.INSTANCE;
   }

   private final void req(String var1, Map<String, ? extends Object> var2, Function1<? super JSONObject, Unit> var3) {
      DataClient var4 = Link.INSTANCE.getData();
      if (var4 == null) {
         JSONObject var5 = new JSONObject().put("ok", false).put("error", "Not connected to the comma.");
         Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
         var3.invoke(var5);
      } else {
         var4.request("hub_power", MapsKt.plus(MapsKt.mapOf(TuplesKt.to("op", var1)), var2), var3);
      }
   }

   // $VF: synthetic method
   static void req$default(HubPowerView var0, String var1, Map var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = MapsKt.emptyMap();
      }

      var0.req(var1, var2, var3);
   }

   private final void send(String var1, Map<String, ? extends Object> var2) {
      this.req(var1, var2, new HubPowerView$$ExternalSyntheticLambda2(this));
   }

   private static final Unit send$lambda$1(HubPowerView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.has("delay_min")) {
         var0.status = var1;
      }

      if (!var1.optBoolean("ok")) {
         String var2 = var1.optString("error", "The comma didn't take it");
         Intrinsics.checkNotNullExpressionValue(var2, "optString(...)");
         var0.toast(var2);
      }

      var0.paint();
      return Unit.INSTANCE;
   }

   private final View stepRow(String var1, String var2, boolean var3, Function0<Unit> var4, Function0<Unit> var5) {
      LinearLayout var9 = new LinearLayout(this.getContext());
      var9.setOrientation(0);
      var9.setGravity(16);
      var9.setPadding(0, this.getUi().dp(8), 0, this.getUi().dp(8));
      TextView var10 = new TextView(var9.getContext());
      var10.setText(var1);
      CarUi.Companion var11 = CarUi.Companion;
      int var8;
      if (var3) {
         var8 = var11.getTEXT();
      } else {
         var8 = var11.getDIM();
      }

      var10.setTextColor(var8);
      var10.setTextSize(18.0F);
      View var12 = (View)var10;
      float var7 = 1.0F;
      var9.addView(var12, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      TextView var15 = this.getUi().stepper("−", new HubPowerView$$ExternalSyntheticLambda13(var3, var4));
      float var6;
      if (var3) {
         var6 = 1.0F;
      } else {
         var6 = 0.35F;
      }

      var15.setAlpha(var6);
      TextView var13 = this.getUi().stepper("+", new HubPowerView$$ExternalSyntheticLambda1(var3, var5));
      if (var3) {
         var6 = var7;
      } else {
         var6 = 0.35F;
      }

      var13.setAlpha(var6);
      var9.addView((View)var15);
      TextView var16 = new TextView(var9.getContext());
      var16.setText(var2);
      CarUi.Companion var14 = CarUi.Companion;
      if (var3) {
         var8 = var14.getTEXT();
      } else {
         var8 = var14.getDIM();
      }

      var16.setTextColor(var8);
      var16.setTextSize(24.0F);
      var16.setTypeface(Typeface.DEFAULT_BOLD);
      var16.setGravity(17);
      var9.addView((View)var16, (android.view.ViewGroup.LayoutParams)(new LayoutParams(this.getUi().dp(150), -2)));
      var9.addView((View)var13);
      return (View)var9;
   }

   private static final Unit stepRow$lambda$14$lambda$11(boolean var0, Function0 var1) {
      if (var0) {
         var1.invoke();
      }

      return Unit.INSTANCE;
   }

   private static final Unit stepRow$lambda$14$lambda$9(boolean var0, Function0 var1) {
      if (var0) {
         var1.invoke();
      }

      return Unit.INSTANCE;
   }

   private final View switchRow(String var1, String var2, boolean var3, boolean var4, Function1<? super Boolean, Unit> var5) {
      LinearLayout var7 = new LinearLayout(this.getContext());
      var7.setOrientation(0);
      var7.setGravity(16);
      var7.setPadding(0, this.getUi().dp(8), 0, this.getUi().dp(8));
      LinearLayout var8 = new LinearLayout(var7.getContext());
      var8.setOrientation(1);
      TextView var9 = new TextView(var8.getContext());
      var9.setText(var1);
      CarUi.Companion var10 = CarUi.Companion;
      int var6;
      if (var4) {
         var6 = var10.getTEXT();
      } else {
         var6 = var10.getDIM();
      }

      var9.setTextColor(var6);
      var9.setTextSize(19.0F);
      var9.setTypeface(Typeface.DEFAULT_BOLD);
      var8.addView((View)var9);
      TextView var11 = new TextView(var8.getContext());
      var11.setText(var2);
      var11.setTextColor(CarUi.Companion.getDIM());
      var11.setTextSize(15.0F);
      var11.setPadding(0, this.getUi().dp(2), this.getUi().dp(12), 0);
      var8.addView((View)var11);
      var7.addView((View)var8, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      Switch var12 = this.getUi().bigSwitch();
      var12.setChecked(var3);
      var12.setEnabled(var4);
      var12.setOnCheckedChangeListener(new HubPowerView$$ExternalSyntheticLambda3(var5));
      var7.addView((View)var12);
      return (View)var7;
   }

   private static final void switchRow$lambda$7$lambda$6$lambda$5(Function1 var0, CompoundButton var1, boolean var2) {
      var0.invoke(var2);
   }

   private final void toast(String var1) {
      Toast.makeText(this.getContext(), var1, 1).show();
   }

   @Override
   public void refresh() {
      this.start();
   }

   public final void start() {
      this.shown = true;
      this.getMain().removeCallbacks(this.poll);
      this.getMain().post(this.poll);
   }

   public final void stop() {
      this.shown = false;
      this.getMain().removeCallbacks(this.poll);
   }
}
