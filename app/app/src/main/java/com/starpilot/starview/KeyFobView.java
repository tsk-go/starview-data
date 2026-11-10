package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class KeyFobView extends CardPage {
   private String err;
   private JSONArray events;
   private long eventsAt;
   private final Runnable poll;
   private final int red;
   private boolean shown;
   private JSONObject status;
   private final SimpleDateFormat timeFmt;

   // $VF: synthetic method
   public static Unit $r8$lambda$7k_86kD1kaUnfBZFWH6VgxnA0D8(KeyFobView var0, JSONObject var1) {
      return load$lambda$2$lambda$1(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KDgX_wvYQjcqIcfnQ7UeCzp_zJ8/* $VF was: $r8$lambda$KDgX_wvYQjcqIcfnQ7UeCzp_zJ8*/(KeyFobView var0, JSONObject var1) {
      return paint$lambda$9$lambda$8$lambda$7$lambda$6(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MmZ5_lFayHtvNvd0vv3HcMrYHqM/* $VF was: $r8$lambda$MmZ5_lFayHtvNvd0vv3HcMrYHqM*/(KeyFobView var0, JSONObject var1) {
      return paint$lambda$11$lambda$10(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gTy72HlDYXsy1l6LrCLhnLSfCEU(KeyFobView var0) {
      return paint$lambda$13(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jXlUMtsSxMfi6Mslm44KFfa5D9s(KeyFobView var0, JSONObject var1) {
      return load$lambda$2(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kpHwbSTbjv7geBQCAeRvJIQtyfY(KeyFobView var0, String var1) {
      return paint$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$riaEqd9R6kqc5i2_NTYMQNLVeos(KeyFobView var0, JSONObject var1) {
      return paint$lambda$13$lambda$12(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$x44LF6xrcq4u9FXU9hoEdi79cVI(KeyFobView var0, CompoundButton var1, boolean var2) {
      paint$lambda$9$lambda$8$lambda$7(var0, var1, var2);
   }

   public KeyFobView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
      this.timeFmt = new SimpleDateFormat("EEE h:mm:ss a", Locale.US);
      this.events = new JSONArray();
      this.red = Color.rgb(255, 138, 128);
      this.poll = new Runnable() {

         @Override
         public void run() {
            if (KeyFobView.this.shown) {
               KeyFobView.this.load();
               Handler var4 = KeyFobView.this.getMain();
               Runnable var6 = this;
               JSONObject var5 = KeyFobView.this.status;
               int var1x;
               if (var5 != null) {
                  var1x = var5.optInt("learning");
               } else {
                  var1x = 0;
               }

               long var2x;
               if (var1x > 0) {
                  var2x = 1500L;
               } else {
                  var2x = 5000L;
               }

               var4.postDelayed(var6, var2x);
            }
         }
      };
   }

   private final View buttons(View... var1) {
      LinearLayout var5 = new LinearLayout(this.getContext());
      int var2 = 0;
      var5.setOrientation(0);
      var5.setPadding(0, this.getUi().dp(10), 0, 0);

      for (int var3 = var1.length; var2 < var3; var2++) {
         View var6 = var1[var2];
         LayoutParams var4 = new LayoutParams(-2, -2);
         var4.rightMargin = this.getUi().dp(12);
         Unit var7 = Unit.INSTANCE;
         var5.addView(var6, (android.view.ViewGroup.LayoutParams)var4);
      }

      return (View)var5;
   }

   private final void load() {
      req$default(this, "status", null, new KeyFobView$$ExternalSyntheticLambda4(this), 2, null);
   }

   private static final Unit load$lambda$2(KeyFobView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      boolean var2 = var1.optBoolean("ok");
      Object var5 = null;
      if (var2) {
         var0.status = var1;
         var0.err = null;
      } else {
         var0.err = var1.optString("error", "The comma didn't answer");
      }

      long var3 = System.currentTimeMillis();
      JSONArray var6 = var1.optJSONArray("recent");
      Double var7 = (Double)var5;
      if (var6 != null) {
         var7 = (Double)var5;
         if (var6.length() > 0) {
            JSONObject var8 = var6.optJSONObject(var6.length() - 1);
            var7 = (Double)var5;
            if (var8 != null) {
               var7 = var8.optDouble("t");
            }
         }
      }

      if (var3 - var0.eventsAt > 30000L
         || var7 != null && var0.events.length() > 0 && var7 > var0.events.optJSONObject(0).optDouble("t")
         || var7 != null && var0.events.length() == 0) {
         var0.eventsAt = var3;
         req$default(var0, "history", null, new KeyFobView$$ExternalSyntheticLambda0(var0), 2, null);
      } else {
         var0.paint();
      }

      return Unit.INSTANCE;
   }

   private static final Unit load$lambda$2$lambda$1(KeyFobView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "h");
      if (var1.optBoolean("ok")) {
         JSONArray var2 = var1.optJSONArray("events");
         JSONArray var3 = var2;
         if (var2 == null) {
            var3 = new JSONArray();
         }

         var0.events = var3;
      }

      var0.paint();
      return Unit.INSTANCE;
   }

   private final void paint() {
      this.getCol().removeAllViews();
      JSONObject var16 = this.status;
      LinearLayout var12 = this.card();
      this.add((View)var12);
      var12.addView(this.title("Key fob", "key", "device"));
      if (var16 == null) {
         CardPage var38 = this;
         String var35 = this.err;
         String var32 = var35;
         if (var35 == null) {
            var32 = "Asking the comma…";
         }

         var12.addView((View)CardPage.line$default(var38, var32, 0, 0.0F, 6, null));
      } else {
         var12.addView(
            (View)CardPage.line$default(
               this,
               "Car on, in Park: a press of Lock or Unlock flips the locks (the car ignores its fob while running). Car just turned off (in Park): hold Lock 2½ seconds - the windows close for 15 seconds (a short press stops them). Driving: the radio is off.",
               0,
               0.0F,
               6,
               null
            )
         );
         Pair var11 = this.radioWords(var16);
         String var15 = (String)var11.component1();
         var12.addView((View)this.line(var15, ((Number)var11.component2()).intValue(), 18.0F));
         int var5 = var16.optInt("others");
         String var13 = "";
         if (var5 > 0) {
            String var26;
            if (var5 == 1) {
               var26 = "";
            } else {
               var26 = "s";
            }

            var12.addView(
               (View)this.line(
                  "Ignored " + var5 + " signal" + var26 + " that weren't your fob (other transmitters nearby) since StarView started.",
                  CarUi.Companion.getDIM(),
                  14.0F
               )
            );
         }

         boolean var10 = var16.optBoolean("on");
         LinearLayout var27 = new LinearLayout(this.getContext());
         var27.setOrientation(0);
         var27.setGravity(16);
         var27.setPadding(0, this.getUi().dp(10), 0, 0);
         TextView var14 = new TextView(var27.getContext());
         var14.setText("Use the key fob");
         var14.setTextColor(CarUi.Companion.getTEXT());
         var14.setTextSize(19.0F);
         var14.setTypeface(Typeface.DEFAULT_BOLD);
         var27.addView((View)var14, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
         Switch var36 = this.getUi().bigSwitch();
         var36.setChecked(var10);
         var36.setOnCheckedChangeListener(new KeyFobView$$ExternalSyntheticLambda1(this));
         var27.addView((View)var36);
         var12.addView((View)var27);
         LinearLayout var37 = this.card();
         this.add((View)var37);
         var37.addView(this.title("Fobs", "lock", "device"));
         JSONArray var17 = var16.optJSONArray("ids");
         JSONArray var28 = var17;
         if (var17 == null) {
            var28 = new JSONArray();
         }

         if (var28.length() == 0) {
            var37.addView((View)CardPage.line$default(this, "None yet.", 0, 0.0F, 6, null));
         }

         int var3 = 0;

         for (int var4 = var28.length(); var3 < var4; var3++) {
            String var39 = var28.optString(var3);
            var37.addView((View)this.line("Fob " + (var3 + 1) + "  ·  " + var39, CarUi.Companion.getTEXT(), 17.0F));
            var37.addView(this.buttons(CarUi.button$default(this.getUi(), "Forget", false, new KeyFobView$$ExternalSyntheticLambda2(this, var39), 2, null)));
         }

         int var6 = var16.optInt("learning");
         if (var6 > 0) {
            var37.addView(
               (View)this.line("Press Lock on the fob twice, a few seconds apart, near the van (" + var6 + "s left).", CarUi.Companion.getACCENT(), 18.0F)
            );
         } else {
            CarUi var33 = this.getUi();
            boolean var9;
            if (var28.length() == 0) {
               var9 = true;
            } else {
               var9 = false;
            }

            var37.addView(this.buttons(var33.button("Add a fob…", var9, new KeyFobView$$ExternalSyntheticLambda3(this))));
         }

         LinearLayout var20 = this.card();
         this.add((View)var20);
         var20.addView(this.title("What it heard", "history", "device"));
         if (this.events.length() == 0) {
            var20.addView((View)CardPage.line$default(this, "Nothing yet.", 0, 0.0F, 6, null));
         }

         int var7 = 0;
         int var25 = Math.min(this.events.length(), 60);

         for (String var34 = var13; var7 < var25; var7++) {
            JSONObject var21 = this.events.optJSONObject(var7);
            if (var21 != null) {
               String var19;
               label136: {
                  var19 = var21.optString("did");
                  if (var19 != null) {
                     switch (var19.hashCode()) {
                        case -1097452790:
                           if (var19.equals("locked")) {
                              var29 = "Locked";
                              break label136;
                           }
                           break;
                        case -210949405:
                           if (var19.equals("unlocked")) {
                              var29 = "Unlocked";
                              break label136;
                           }
                           break;
                        case 50780643:
                           if (var19.equals("learned")) {
                              var29 = "Learned";
                              break label136;
                           }
                           break;
                        case 1349493379:
                           if (var19.equals("windows")) {
                              var29 = "Closed the windows";
                              break label136;
                           }
                           break;
                        case 2129323981:
                           if (var19.equals("nothing")) {
                              var29 = "Nothing";
                              break label136;
                           }
                     }
                  }

                  Intrinsics.checkNotNull(var19);
                  boolean var23;
                  if (var19.length() > 0) {
                     var23 = true;
                  } else {
                     var23 = false;
                  }

                  if (var23) {
                     StringBuilder var30 = new StringBuilder();
                     String var40 = String.valueOf(var19.charAt(0));
                     Intrinsics.checkNotNull(var40, "null cannot be cast to non-null type java.lang.String");
                     String var41 = var40.toUpperCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var41, "toUpperCase(...)");
                     StringBuilder var31 = var30.append(var41);
                     String var42 = var19.substring(1);
                     Intrinsics.checkNotNullExpressionValue(var42, "substring(...)");
                     var29 = var31.append(var42).toString();
                  } else {
                     var29 = var19;
                  }
               }

               String var43;
               if (Intrinsics.areEqual(var21.optString("kind"), "hold")) {
                  var43 = "Hold";
               } else {
                  var43 = "Press";
               }

               String var18;
               if (var21.optBoolean("ours")) {
                  var18 = var34;
               } else {
                  var18 = "  ·  not yours";
               }

               String var22 = var21.optString("why", var34);
               if (Intrinsics.areEqual(var19, "nothing")) {
                  var3 = CarUi.Companion.getDIM();
               } else {
                  Intrinsics.checkNotNull(var19);
                  if (StringsKt.endsWith$default(var19, "failed", false, 2, null)) {
                     var3 = this.red;
                  } else {
                     var3 = CarUi.Companion.getTEXT();
                  }
               }

               SimpleDateFormat var44 = this.timeFmt;
               double var1 = var21.optDouble("t");
               String var46 = var44.format(new Date((long)(1000 * var1)));
               Intrinsics.checkNotNull(var22);
               boolean var8;
               if (var22.length() > 0) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               if (var8) {
                  var19 = " - " + var22;
               } else {
                  var19 = var34;
               }

               var20.addView((View)this.line(var46 + "  ·  " + var43 + var18 + "  ·  " + var29 + var19, var3, 15.0F));
            }
         }
      }
   }

   private static final Unit paint$lambda$11(KeyFobView var0, String var1) {
      var0.req("forget", MapsKt.mapOf(TuplesKt.to("id", var1)), new KeyFobView$$ExternalSyntheticLambda6(var0));
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$11$lambda$10(KeyFobView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         var0.status = var1;
         var0.paint();
      } else {
         String var2 = var1.optString("error");
         Intrinsics.checkNotNullExpressionValue(var2, "optString(...)");
         var0.toast(var2);
      }

      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$13(KeyFobView var0) {
      req$default(var0, "learn", null, new KeyFobView$$ExternalSyntheticLambda7(var0), 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$13$lambda$12(KeyFobView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         var0.status = var1;
         var0.paint();
         var0.getMain().removeCallbacks(var0.poll);
         var0.getMain().postDelayed(var0.poll, 1500L);
      } else {
         String var2 = var1.optString("error");
         Intrinsics.checkNotNullExpressionValue(var2, "optString(...)");
         var0.toast(var2);
      }

      return Unit.INSTANCE;
   }

   private static final void paint$lambda$9$lambda$8$lambda$7(KeyFobView var0, CompoundButton var1, boolean var2) {
      var0.req("set", MapsKt.mapOf(TuplesKt.to("on", var2)), new KeyFobView$$ExternalSyntheticLambda5(var0));
   }

   private static final Unit paint$lambda$9$lambda$8$lambda$7$lambda$6(KeyFobView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         var0.status = var1;
      } else {
         String var2 = var1.optString("error");
         Intrinsics.checkNotNullExpressionValue(var2, "optString(...)");
         var0.toast(var2);
      }

      var0.paint();
      return Unit.INSTANCE;
   }

   private final Pair<String, Integer> radioWords(JSONObject var1) {
      String var2 = var1.optString("mode", "");
      String var3 = var1.optString("radio");
      if (var3 != null) {
         switch (var3.hashCode()) {
            case -1775170701:
               if (var3.equals("restarting")) {
                  return TuplesKt.to("The radio stopped - starting it again", CarUi.Companion.getWARN());
               }
               break;
            case -1745372787:
               if (var3.equals("no rtl_sdr")) {
                  return TuplesKt.to("The radio tools aren't on the comma (run /data/tools/rtl_build.sh)", CarUi.Companion.getWARN());
               }
               break;
            case -1218715461:
               if (var3.equals("listening")) {
                  String var5;
                  if (Intrinsics.areEqual(var2, "off")) {
                     var5 = "Listening - hold Lock 2½ s to close the windows";
                  } else {
                     var5 = "Listening - a press locks or unlocks";
                  }

                  return TuplesKt.to(var5, CarUi.Companion.getACCENT());
               }
               break;
            case 231075644:
               if (var3.equals("no dongle")) {
                  return TuplesKt.to("The radio dongle isn't plugged into the comma", CarUi.Companion.getWARN());
               }
               break;
            case 1316806720:
               if (var3.equals("starting")) {
                  return TuplesKt.to("Starting the radio…", CarUi.Companion.getTEXT());
               }
               break;
            case 1856461213:
               if (var3.equals("no fob learned")) {
                  return TuplesKt.to("No fob learned yet - add one below", CarUi.Companion.getWARN());
               }
         }
      }

      String var6;
      if (!var1.optBoolean("on")) {
         var6 = "Switched off";
      } else {
         var6 = "Resting - it listens with the car on in Park, and for 30 minutes after it's off";
      }

      return TuplesKt.to(var6, CarUi.Companion.getDIM());
   }

   private final void req(String var1, Map<String, ? extends Object> var2, Function1<? super JSONObject, Unit> var3) {
      DataClient var4 = Link.INSTANCE.getData();
      if (var4 == null) {
         JSONObject var5 = new JSONObject().put("ok", false).put("error", "Not connected to the comma.");
         Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
         var3.invoke(var5);
      } else {
         var4.request("fob", MapsKt.plus(MapsKt.mapOf(TuplesKt.to("op", var1)), var2), var3);
      }
   }

   // $VF: synthetic method
   static void req$default(KeyFobView var0, String var1, Map var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = MapsKt.emptyMap();
      }

      var0.req(var1, var2, var3);
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
      this.eventsAt = 0L;
      this.getMain().post(this.poll);
   }

   public final void stop() {
      this.shown = false;
      this.getMain().removeCallbacks(this.poll);
   }
}
