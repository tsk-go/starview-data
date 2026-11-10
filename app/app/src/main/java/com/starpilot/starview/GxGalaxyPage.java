package com.starpilot.starview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class GxGalaxyPage extends GxPage {
   private String gUrl;
   private boolean loading;
   private LinearLayout pairBox;
   private boolean paired;
   private boolean submitting;
   private String tsAuthUrl;
   private LinearLayout tsBox;
   private boolean tsBusy;
   private boolean tsInstalled;
   private boolean tsLoaded;

   // $VF: synthetic method
   public static Unit $r8$lambda$Dx_35bB9YiFAG7LzgcBQmx0q04A/* $VF was: $r8$lambda$Dx_35bB9YiFAG7LzgcBQmx0q04A*/(GxGalaxyPage var0, String var1) {
      return askPassword$lambda$6(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FA8e98tig9TXmnvjznwI7uPBxA4(GxGalaxyPage var0, int var1, String var2, String var3) {
      return loadPair$lambda$2(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MValnrDMKLDidrY9B_kCif7AzP0/* $VF was: $r8$lambda$MValnrDMKLDidrY9B_kCif7AzP0*/(GxGalaxyPage var0) {
      return paintTs$lambda$18(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MaQQXgiBlgV5cuWTNns4N1QbSmk(GxGalaxyPage var0) {
      return unpair$lambda$13(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$RUzAMUYjGkw601nk1rVmOjMSI7k(GxGalaxyPage var0) {
      return paintPair$lambda$4(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZIOc0Yl_WFfg66NuKb4wMn55S7o(GxGalaxyPage var0, String var1, JSONObject var2) {
      return pair$lambda$9(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Za48nJVNADn_tuGiN4m95kcxDaM/* $VF was: $r8$lambda$Za48nJVNADn_tuGiN4m95kcxDaM*/(
      GxGalaxyPage var0, String var1, JSONObject var2
   ) {
      return loadTs$lambda$14(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_gWztZGom9ruLlJlJ4xKuXAQy_c/* $VF was: $r8$lambda$_gWztZGom9ruLlJlJ4xKuXAQy_c*/(GxGalaxyPage var0) {
      return paintPair$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$bkznSt85ClgZW_RTNZe5r4ZRn64/* $VF was: $r8$lambda$bkznSt85ClgZW_RTNZe5r4ZRn64*/(
      GxGalaxyPage var0, String var1, JSONObject var2
   ) {
      return install$lambda$21(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$idvwExDHn_7_WwJheW7twg7coAE/* $VF was: $r8$lambda$idvwExDHn_7_WwJheW7twg7coAE*/(GxGalaxyPage var0) {
      return paintTs$lambda$17(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mTxLJk_7lUrsx0xxgZha3QzTSdk(GxGalaxyPage var0, String var1, JSONObject var2) {
      return unpair$lambda$13$lambda$12(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mf3A5LVoDgeQjS5w640SWtqFqGI(GxGalaxyPage var0) {
      return uninstall$lambda$25(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$n6br8yoYPV2CvaWZZpyHVntlkTE(GxGalaxyPage var0, String var1, JSONObject var2) {
      return uninstall$lambda$25$lambda$24(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$umXQeRfRKHU2ZlMOLlowB4XvAq8(GxGalaxyPage var0) {
      return paintTs$lambda$15(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wufQ3SXENOOlzduPqelvX8pkUNs(GxGalaxyPage var0) {
      return paintPair$lambda$5(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$x1Q2jGQTh0uUS9WcOioLjZh6aig(GxGalaxyPage var0) {
      return paintTs$lambda$16(var0);
   }

   public GxGalaxyPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.loading = true;
      this.gUrl = "";
      this.tsAuthUrl = "";
   }

   private final void askPassword() {
      if (!this.submitting) {
         this.prompt(
            "Pair with Galaxy",
            "Password (min 6 characters)",
            "",
            129,
            "Set a password to secure your connection.",
            new GxGalaxyPage$$ExternalSyntheticLambda9(this)
         );
      }
   }

   private static final Unit askPassword$lambda$6(GxGalaxyPage var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "pw");
      var0.pair(var1);
      return Unit.INSTANCE;
   }

   private final void install() {
      if (!this.tsBusy) {
         this.tsBusy = true;
         this.paintTs();
         this.toast("Install started...");
         this.send("POST", "/api/tailscale/setup", null, 300L, new GxGalaxyPage$$ExternalSyntheticLambda6(this));
      }
   }

   private static final Unit install$lambda$21(GxGalaxyPage var0, String var1, JSONObject var2) {
      var0.tsBusy = false;
      if (var1 != null) {
         CharSequence var9 = var1;
         Object var4 = var9;
         if (StringsKt.isBlank(var9)) {
            var4 = "Failed to install Tailscale.";
         }

         var0.toast((String)var4);
      } else {
         label38: {
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var3 = var1;
                  CharSequence var6 = var3;
                  if (StringsKt.isBlank(var3)) {
                     var6 = null;
                  }

                  var1 = (String)var6;
                  if (var1 != null) {
                     break label38;
                  }
               }
            }

            var1 = "Tailscale setup started.";
         }

         label31: {
            var0.toast(var1);
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "auth_url", null, 2, null);
               if (var1 != null) {
                  break label31;
               }
            }

            var1 = "";
         }

         if (!StringsKt.isBlank(var1) && !Intrinsics.areEqual(var1, "null")) {
            var0.tsAuthUrl = var1;
            var0.open(var1);
         }
      }

      var0.paintTs();
      var0.loadTs();
      return Unit.INSTANCE;
   }

   private final void loadPair() {
      GxPage.call$default(this, "GET", "/api/galaxy/status", null, 0L, new GxGalaxyPage$$ExternalSyntheticLambda11(this), 12, null);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final Unit loadPair$lambda$2(GxGalaxyPage var0, int var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var2, "text");
      if (var1 < 0) {
         var0.toast("Failed to check pairing status.");
      } else {
         boolean var6;
         if (200 <= var1 && var1 < 300) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var6) {
            label43:
            try {
               Result.Companion var10 = Result.Companion;
               JSONObject var11 = new JSONObject(var2);
               var8 = KtCompat.resultOf(var11);
            } catch (Throwable var5) {
               Result.Companion var7 = Result.Companion;
               var8 = KtCompat.resultOf(ResultKt.createFailure(var5));
               break label43;
            }

            Object var12 = var8;
            if (KtCompat.isFailure(var8)) {
               var12 = null;
            }

            JSONObject var9 = (JSONObject)var12;
            if (var9 != null) {
               var0.paired = var9.optBoolean("paired");
               var0.gUrl = GxPage.str$default(var0, var9, "url", null, 2, null);
            }
         }
      }

      var0.loading = false;
      var0.paintPair();
      return Unit.INSTANCE;
   }

   private final void loadTs() {
      this.getJson("/api/tailscale/installed", new GxGalaxyPage$$ExternalSyntheticLambda5(this));
   }

   private static final Unit loadTs$lambda$14(GxGalaxyPage var0, String var1, JSONObject var2) {
      boolean var5 = false;
      boolean var4 = var5;
      if (var1 == null) {
         boolean var3;
         if (var2 != null && var2.optBoolean("installed")) {
            var3 = true;
         } else {
            var3 = false;
         }

         var4 = var5;
         if (var3) {
            var4 = true;
         }
      }

      var0.tsInstalled = var4;
      var0.tsLoaded = true;
      var0.paintTs();
      return Unit.INSTANCE;
   }

   private final void open(String var1) {
      if (!GxVehicleKt.access$gxVehicleOpenUrl(this.getAct(), var1)) {
         this.toast("No browser on this tablet can open the link.");
      }
   }

   private final void paintPair() {
      LinearLayout var5 = this.pairBox;
      Object var2 = null;
      Object var3 = null;
      Object var4 = null;
      LinearLayout var1 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pairBox");
         var1 = null;
      }

      var1.removeAllViews();
      if (this.loading) {
         var1 = this.pairBox;
         if (var1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            var1 = (LinearLayout)var4;
         }

         var1.addView((View)this.empty("Checking pairing status…"));
      } else {
         if (this.paired) {
            var4 = this;
            var3 = this.pairBox;
            var1 = (LinearLayout)var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pairBox");
               var1 = null;
            }

            var3 = GxPage.row$default((GxPage)var4, "Paired", "Your device is paired with Galaxy. Access it remotely at:", (ViewGroup)var1, 0, 8, null);
            ((GxPage.Row)var3).getTitleView().setTextColor(CarUi.Companion.getACCENT());
            String var7;
            if (this.submitting) {
               var7 = "Unpairing…";
            } else {
               var7 = "Unpair";
            }

            GxVehicleKt.access$gxVehicleEnable(
               (View)GxPage.Row.button$default((GxPage.Row)var3, var7, false, new GxGalaxyPage$$ExternalSyntheticLambda0(this), 2, null),
               this.submitting ^ true
            );
            if (!StringsKt.isBlank(this.gUrl)) {
               var1 = this.pairBox;
               if (var1 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("pairBox");
                  var1 = (LinearLayout)var2;
               }

               var3 = this.card((ViewGroup)var1);
               this.text(this.gUrl, (ViewGroup)var3, 20.0F, CarUi.Companion.getACCENT(), true).setTextIsSelectable(true);
               GxPage var9 = this;
               var2 = TuplesKt.to("Open Link", new GxGalaxyPage$$ExternalSyntheticLambda7(this));
               var3 = (ViewGroup)var3;
               GxPage.buttons$default(var9, new Pair[]{(Pair)var2}, (ViewGroup)var3, false, 4, null);
            }
         } else {
            var2 = this;
            var1 = this.pairBox;
            if (var1 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pairBox");
               var1 = (LinearLayout)var3;
            }

            var2 = GxPage.row$default(
               (GxPage)var2,
               "Not Paired",
               "Pair your device with Galaxy to access The Galaxy remotely from anywhere. Set a password to secure your connection.",
               (ViewGroup)var1,
               0,
               8,
               null
            );
            String var11;
            if (this.submitting) {
               var11 = "Pairing…";
            } else {
               var11 = "Pair";
            }

            GxVehicleKt.access$gxVehicleEnable(
               (View)((GxPage.Row)var2).button(var11, true, new GxGalaxyPage$$ExternalSyntheticLambda8(this)), this.submitting ^ true
            );
         }
      }
   }

   private static final Unit paintPair$lambda$3(GxGalaxyPage var0) {
      var0.unpair();
      return Unit.INSTANCE;
   }

   private static final Unit paintPair$lambda$4(GxGalaxyPage var0) {
      var0.open(var0.gUrl);
      return Unit.INSTANCE;
   }

   private static final Unit paintPair$lambda$5(GxGalaxyPage var0) {
      var0.askPassword();
      return Unit.INSTANCE;
   }

   private final void paintTs() {
      LinearLayout var5 = this.tsBox;
      Object var4 = null;
      GxPage.Row var3 = null;
      LinearLayout var2 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tsBox");
         var2 = null;
      }

      var2.removeAllViews();
      if (!this.tsLoaded) {
         var2 = this.tsBox;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            var2 = var3;
         }

         this.note("Checking Tailscale install status...", (ViewGroup)var2);
      } else {
         GxPage var23 = this;
         int var1 = CarUi.Companion.getWARN();
         var3 = this.tsBox;
         var2 = (LinearLayout)var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            var2 = null;
         }

         GxVehicleKt.access$gxVehicleNotice(var23, "Not recommended. Using Galaxy Tunnel is the preferred remote connection method.", var1, (ViewGroup)var2);
         GxPage var6 = this;
         String var8;
         if (this.tsInstalled) {
            var8 = "Installed on this device.";
         } else {
            var8 = "Not installed.";
         }

         var5 = this.tsBox;
         var3 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            var3 = null;
         }

         var3 = GxPage.row$default(var6, "Tailscale", var8, (ViewGroup)var3, 0, 8, null);
         TextView var10;
         if (!this.tsInstalled) {
            String var9;
            if (this.tsBusy) {
               var9 = "Installing...";
            } else {
               var9 = "Install Tailscale";
            }

            var10 = var3.button(var9, true, new GxGalaxyPage$$ExternalSyntheticLambda13(this));
         } else {
            String var11;
            if (this.tsBusy) {
               var11 = "Uninstalling...";
            } else {
               var11 = "Uninstall Tailscale";
            }

            var10 = GxPage.Row.button$default(var3, var11, false, new GxGalaxyPage$$ExternalSyntheticLambda14(this), 2, null);
         }

         GxVehicleKt.access$gxVehicleEnable((View)var10, this.tsBusy ^ true);
         if (!StringsKt.isBlank(this.tsAuthUrl)) {
            LinearLayout var20 = this.tsBox;
            var2 = var20;
            if (var20 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tsBox");
               var2 = null;
            }

            var5 = this.card((ViewGroup)var2);
            GxPage.text$default(this, "Sign in to Tailscale to authenticate this device:", (ViewGroup)var5, 17.0F, CarUi.Companion.getTEXT(), false, 16, null);
            this.text(this.tsAuthUrl, (ViewGroup)var5, 18.0F, CarUi.Companion.getACCENT(), true).setTextIsSelectable(true);
            GxPage var13 = this;
            Pair var21 = TuplesKt.to("Open Login Page", new GxGalaxyPage$$ExternalSyntheticLambda15(this));
            ViewGroup var26 = (ViewGroup)var5;
            GxPage.buttons$default(var13, new Pair[]{var21}, var26, false, 4, null);
         }

         GxPage var27 = this;
         LinearLayout var22 = this.tsBox;
         var2 = var22;
         if (var22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            var2 = null;
         }

         GxPage.Row.button$default(
            GxPage.row$default(var27, "Download for your other devices", "tailscale.com/download", (ViewGroup)var2, 0, 8, null),
            "Open",
            false,
            new GxGalaxyPage$$ExternalSyntheticLambda1(this),
            2,
            null
         );
         var2 = this.tsBox;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBox");
            var2 = (LinearLayout)var4;
         }

         this.note("Installing opens the Tailscale login page to authenticate this device.", (ViewGroup)var2);
      }
   }

   private static final Unit paintTs$lambda$15(GxGalaxyPage var0) {
      var0.install();
      return Unit.INSTANCE;
   }

   private static final Unit paintTs$lambda$16(GxGalaxyPage var0) {
      var0.uninstall();
      return Unit.INSTANCE;
   }

   private static final Unit paintTs$lambda$17(GxGalaxyPage var0) {
      var0.open(var0.tsAuthUrl);
      return Unit.INSTANCE;
   }

   private static final Unit paintTs$lambda$18(GxGalaxyPage var0) {
      var0.open("https://tailscale.com/download");
      return Unit.INSTANCE;
   }

   private final void pair(String var1) {
      var1 = StringsKt.trim(var1).toString();
      if (var1.length() < 6) {
         this.toast("Password must be at least 6 characters.");
      } else {
         this.submitting = true;
         this.paintPair();
         GxPage.send$default(
            this, "POST", "/api/galaxy/pair", new JSONObject().put("password", var1), 0L, new GxGalaxyPage$$ExternalSyntheticLambda4(this), 8, null
         );
      }
   }

   private static final Unit pair$lambda$9(GxGalaxyPage var0, String var1, JSONObject var2) {
      var0.submitting = false;
      if (var1 != null) {
         CharSequence var8 = var1;
         Object var3 = var8;
         if (StringsKt.isBlank(var8)) {
            var3 = "Network error — is the device reachable?";
         }

         var0.toast((String)var3);
      } else {
         label34: {
            var0.paired = true;
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "url", null, 2, null);
               if (var1 != null) {
                  break label34;
               }
            }

            var1 = "";
         }

         label29: {
            var0.gUrl = var1;
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var9 = var1;
                  CharSequence var6 = var9;
                  if (StringsKt.isBlank(var9)) {
                     var6 = null;
                  }

                  var1 = (String)var6;
                  if (var1 != null) {
                     break label29;
                  }
               }
            }

            var1 = "Paired!";
         }

         var0.toast(var1);
      }

      var0.paintPair();
      return Unit.INSTANCE;
   }

   private final void uninstall() {
      if (!this.tsBusy) {
         this.confirm(
            "Uninstall Tailscale?",
            "This disconnects the device from your tailnet and removes the Tailscale binaries and state.",
            "Uninstall",
            new GxGalaxyPage$$ExternalSyntheticLambda12(this)
         );
      }
   }

   private static final Unit uninstall$lambda$25(GxGalaxyPage var0) {
      var0.tsBusy = true;
      var0.paintTs();
      var0.toast("Uninstall started...");
      var0.send("POST", "/api/tailscale/uninstall", null, 180L, new GxGalaxyPage$$ExternalSyntheticLambda10(var0));
      return Unit.INSTANCE;
   }

   private static final Unit uninstall$lambda$25$lambda$24(GxGalaxyPage var0, String var1, JSONObject var2) {
      var0.tsBusy = false;
      if (var1 != null) {
         CharSequence var7 = var1;
         Object var3 = var7;
         if (StringsKt.isBlank(var7)) {
            var3 = "Failed to uninstall Tailscale.";
         }

         var0.toast((String)var3);
      } else {
         label25: {
            var0.tsAuthUrl = "";
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var8 = var1;
                  CharSequence var5 = var8;
                  if (StringsKt.isBlank(var8)) {
                     var5 = null;
                  }

                  var1 = (String)var5;
                  if (var1 != null) {
                     break label25;
                  }
               }
            }

            var1 = "Tailscale uninstalled.";
         }

         var0.toast(var1);
      }

      var0.paintTs();
      var0.loadTs();
      return Unit.INSTANCE;
   }

   private final void unpair() {
      if (!this.submitting) {
         this.confirm(
            "Confirm Unpair",
            "Are you sure you want to unpair from Galaxy? You will lose remote access until you pair again.",
            "Unpair",
            new GxGalaxyPage$$ExternalSyntheticLambda2(this)
         );
      }
   }

   private static final Unit unpair$lambda$13(GxGalaxyPage var0) {
      var0.submitting = true;
      var0.paintPair();
      GxPage.send$default(var0, "POST", "/api/galaxy/unpair", null, 0L, new GxGalaxyPage$$ExternalSyntheticLambda3(var0), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit unpair$lambda$13$lambda$12(GxGalaxyPage var0, String var1, JSONObject var2) {
      var0.submitting = false;
      if (var1 != null) {
         CharSequence var7 = var1;
         Object var3 = var7;
         if (StringsKt.isBlank(var7)) {
            var3 = "Network error — is the device reachable?";
         }

         var0.toast((String)var3);
      } else {
         label25: {
            var0.paired = false;
            var0.gUrl = "";
            if (var2 != null) {
               var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
               if (var1 != null) {
                  CharSequence var8 = var1;
                  CharSequence var5 = var8;
                  if (StringsKt.isBlank(var8)) {
                     var5 = null;
                  }

                  var1 = (String)var5;
                  if (var1 != null) {
                     break label25;
                  }
               }
            }

            var1 = "Unpaired!";
         }

         var0.toast(var1);
      }

      var0.paintPair();
      return Unit.INSTANCE;
   }

   @Override
   public void build() {
      GxPage.header$default(this, "Galaxy Pairing", null, 2, null);
      this.pairBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Tailscale", null, 2, null);
      GxPage.note$default(
         this,
         "Tailscale creates a secure, private connection between your openpilot device and your phone or PC so you can access and control it from anywhere!",
         null,
         2,
         null
      );
      this.tsBox = GxPage.box$default(this, null, 1, null);
      this.paintPair();
      this.paintTs();
   }

   @Override
   public void onShow() {
      this.loadPair();
      this.loadTs();
   }
}
