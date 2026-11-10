package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import java.io.File;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.Result.Companion;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class DriverProfilesView extends CardPage {
   // $VF: synthetic method
   public static Unit $r8$lambda$1QLavDfu2gUUiUw2tFd624HqeB4(DriverProfilesView var0) {
      return refresh$lambda$1(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$EXwCQMFfEq3UryZRXJ6IvgWOA44(EditText var0, DriverProfilesView var1, DialogInterface var2, int var3) {
      askSave$lambda$17(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FBjfCCQOtpP4P0BfdfbTRshK11g(DriverProfilesView var0, String var1, JSONObject var2, String var3) {
      return askSave$lambda$17$lambda$16(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$FFpS_qKI2b2XA8tynn_aWkZgJDY(DriverProfilesView var0, JSONObject var1, DialogInterface var2, int var3) {
      apply$lambda$22(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$FkFxo6PpPxM7QtbyAvNDULuuwsU(File var0) {
      return refresh$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Y8mXL8mIZpXAsMRRhZTAlrA6pTA(DriverProfilesView var0, JSONObject var1) {
      return refresh$lambda$14$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$oqJlnziAaQ0s4QoETrbMbau__3k/* $VF was: $r8$lambda$oqJlnziAaQ0s4QoETrbMbau__3k*/(
      File var0, DriverProfilesView var1, DialogInterface var2, int var3
   ) {
      refresh$lambda$14$lambda$13$lambda$12(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sKKaAxYN6jHN5zYb3WI1ITrkReo(DriverProfilesView var0, JSONObject var1, JSONObject var2, String var3) {
      return apply$lambda$22$lambda$21(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xDwRIdsc8Efiju69UtP0TZCcipw(LinearLayout var0, JSONObject var1, File var2, DriverProfilesView var3) {
      return refresh$lambda$14$lambda$13(var0, var1, var2, var3);
   }

   public DriverProfilesView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
   }

   private final void apply(JSONObject var1) {
      Builder var2 = new Builder(this.getContext());
      String var3 = var1.optString("name");
      var2.setTitle("Apply \"" + var3 + "\"?")
         .setMessage("StarPilot's settings and this tablet's settings change to this profile. The car must be parked (Galaxy only restores while parked).")
         .setPositiveButton("Apply", new DriverProfilesView$$ExternalSyntheticLambda6(this, var1))
         .setNegativeButton("Cancel", null)
         .show();
   }

   private static final void apply$lambda$22(DriverProfilesView var0, JSONObject var1, DialogInterface var2, int var3) {
      Galaxy7 var5 = Galaxy7.Companion.current();
      if (var5 == null) {
         var0.toast("Not connected to the comma");
      } else {
         JSONObject var4 = var1.optJSONObject("galaxy");
         if (var4 == null) {
            var0.toast("This profile has no StarPilot settings");
         } else {
            var5.post("/api/settings/restore", var4, new DriverProfilesView$$ExternalSyntheticLambda8(var0, var1));
         }
      }
   }

   private static final Unit apply$lambda$22$lambda$21(DriverProfilesView var0, JSONObject var1, JSONObject var2, String var3) {
      if (var2 == null) {
         String var10;
         if (var3 == null) {
            var10 = "Galaxy refused";
         } else {
            var10 = var3;
         }

         var0.toast("StarPilot settings not applied: " + var10);
         return Unit.INSTANCE;
      } else {
         JSONObject var12 = var1.optJSONObject("tablet");
         int var4;
         if (var12 != null) {
            SettingsBackup var6 = SettingsBackup.INSTANCE;
            Context var7 = var0.getContext();
            Intrinsics.checkNotNullExpressionValue(var7, "getContext(...)");
            var4 = var6.import_(var7, var12);
         } else {
            var4 = 0;
         }

         var3 = var1.optString("name");
         Integer var8 = var2.optInt("restored", -1);
         int var5 = var8.intValue();
         boolean var14;
         if (var5 >= 0) {
            var14 = 1;
         } else {
            var14 = 0;
         }

         String var11 = null;
         if (!var14) {
            var8 = null;
         }

         if (var8 != null) {
            var14 = var8.intValue();
            var11 = var14 + " StarPilot settings changed";
         }

         String var9 = CollectionsKt.joinToString$default(
            CollectionsKt.listOfNotNull(new String[]{var11, var4 + " tablet settings"}), ", ", null, null, 0, null, null, 62, null
         );
         var0.toast("Applied \"" + var3 + "\": " + var9);
         return Unit.INSTANCE;
      }
   }

   private final void askSave() {
      EditText var1 = new EditText(this.getContext());
      var1.setHint("Driver's name");
      new Builder(this.getContext())
         .setTitle("Save profile")
         .setView((View)var1)
         .setPositiveButton("Save", new DriverProfilesView$$ExternalSyntheticLambda4(var1, this))
         .setNegativeButton("Cancel", null)
         .show();
   }

   private static final void askSave$lambda$17(EditText var0, DriverProfilesView var1, DialogInterface var2, int var3) {
      String var4 = StringsKt.trim(var0.getText().toString()).toString();
      boolean var6;
      if (var4.length() == 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (!var6) {
         Galaxy7 var5 = Galaxy7.Companion.current();
         if (var5 == null) {
            var1.toast("Not connected to the comma");
         } else {
            var5.get("/api/settings/backup", new DriverProfilesView$$ExternalSyntheticLambda5(var1, var4));
         }
      }
   }

   private static final Unit askSave$lambda$17$lambda$16(DriverProfilesView var0, String var1, JSONObject var2, String var3) {
      if (var2 == null) {
         if (var3 == null) {
            var1 = "Galaxy didn't give its settings";
         } else {
            var1 = var3;
         }

         var0.toast(var1);
         return Unit.INSTANCE;
      } else {
         var2 = new JSONObject().put("name", var1).put("savedAt", System.currentTimeMillis()).put("galaxy", var2);
         SettingsBackup var4 = SettingsBackup.INSTANCE;
         Context var9 = var0.getContext();
         Intrinsics.checkNotNullExpressionValue(var9, "getContext(...)");
         var2 = var2.put("tablet", var0.feel(var4.export(var9)));
         File var10 = var0.getDir();
         CharSequence var12 = var1;
         String var13 = new Regex("[^A-Za-z0-9 _-]").replace(var12, "_");
         File var11 = new File(var10, var13 + ".json");
         String var8 = var2.toString();
         Intrinsics.checkNotNullExpressionValue(var8, "toString(...)");
         FilesKt.writeText$default(var11, var8, null, 2, null);
         var0.toast("Saved \"" + var1 + "\"");
         var0.refresh();
         return Unit.INSTANCE;
      }
   }

   private final JSONObject feel(JSONObject var1) {
      JSONObject var2 = var1.optJSONObject("prefs");
      if (var2 == null) {
         return var1;
      } else {
         Iterator var3 = var2.keys();
         Intrinsics.checkNotNullExpressionValue(var3, "keys(...)");

         for (String var5 : SequencesKt.toList(SequencesKt.asSequence(var3))) {
            if (!SetsKt.setOf(new String[]{"starview_ui", "starview_hud", "speech"}).contains(var5)) {
               var2.remove(var5);
            }
         }

         return var1;
      }
   }

   private final File getDir() {
      File var1 = new File(this.getContext().getFilesDir(), "profiles");
      var1.mkdirs();
      return var1;
   }

   private static final Unit refresh$lambda$1(DriverProfilesView var0) {
      var0.askSave();
      return Unit.INSTANCE;
   }

   private static final Unit refresh$lambda$14$lambda$11(DriverProfilesView var0, JSONObject var1) {
      var0.apply(var1);
      return Unit.INSTANCE;
   }

   private static final Unit refresh$lambda$14$lambda$13(LinearLayout var0, JSONObject var1, File var2, DriverProfilesView var3) {
      Builder var4 = new Builder(var0.getContext());
      String var5 = var1.optString("name");
      var4.setMessage("Delete the profile \"" + var5 + "\"?")
         .setPositiveButton("Delete", new DriverProfilesView$$ExternalSyntheticLambda7(var2, var3))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void refresh$lambda$14$lambda$13$lambda$12(File var0, DriverProfilesView var1, DialogInterface var2, int var3) {
      var0.delete();
      var1.refresh();
   }

   private static final boolean refresh$lambda$3(File var0) {
      String var1 = var0.getName();
      Intrinsics.checkNotNullExpressionValue(var1, "getName(...)");
      return StringsKt.endsWith$default(var1, ".json", false, 2, null);
   }

   private final void toast(String var1) {
      Toast.makeText(this.getContext(), var1, 1).show();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void refresh() {
      List var15;
      label82: {
         this.getCol().removeAllViews();
         LinearLayout var5 = this.card();
         this.add((View)var5);
         var5.addView(this.title("Driver profiles", "standard", "person"));
         var5.addView(
            (View)CardPage.line$default(
               this,
               "Each driver keeps their own feel: following distance, speed offsets, steering, sounds, the tablet's screen. Save how it's set now under a name; apply a profile while parked.",
               0,
               0.0F,
               6,
               null
            )
         );
         View var4 = (View)this.getUi().button("Save current settings as a profile…", true, new DriverProfilesView$$ExternalSyntheticLambda0(this));
         LayoutParams var2 = CarUi.lp$default(this.getUi(), 0, 0, 1, null);
         var2.topMargin = this.getUi().dp(8);
         Unit var3 = Unit.INSTANCE;
         var5.addView(var4, (android.view.ViewGroup.LayoutParams)var2);
         File[] var14 = this.getDir().listFiles(new DriverProfilesView$$ExternalSyntheticLambda1());
         if (var14 != null) {
            List var21 = ArraysKt.sortedWith(var14, new Comparator() {
               @Override
               public final int compare(T var1, T var2x) {
                  var1 = ((File)var1).getName();
                  Intrinsics.checkNotNullExpressionValue(var1, "getName(...)");
                  var1 = var1.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                  Comparable var5x = var1;
                  var2x = ((File)var2x).getName();
                  Intrinsics.checkNotNullExpressionValue(var2x, "getName(...)");
                  var2x = var2x.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var2x, "toLowerCase(...)");
                  return ComparisonsKt.compareValues(var5x, var2x);
               }
            });
            var15 = var21;
            if (var21 != null) {
               break label82;
            }
         }

         var15 = CollectionsKt.emptyList();
      }

      if (var15.isEmpty()) {
         LinearLayout var22 = this.card();
         var22.addView((View)CardPage.line$default(this, "No profiles yet.", 0, 0.0F, 6, null));
         this.add((View)var22);
      }

      for (File var7 : var15) {
         label73:
         try {
            Companion var17 = Result.Companion;
            DriverProfilesView var18 = this;
            Intrinsics.checkNotNull(var7);
            JSONObject var19 = new JSONObject(FilesKt.readText$default(var7, null, 1, null));
            var15 = (List)KtCompat.resultOf(var19);
         } catch (Throwable var13) {
            Companion var6 = Result.Companion;
            var15 = (List)KtCompat.resultOf(ResultKt.createFailure(var13));
            break label73;
         }

         LinearLayout var24 = var15;
         if (KtCompat.isFailure(var15)) {
            var24 = null;
         }

         JSONObject var20 = (JSONObject)var24;
         if (var20 != null) {
            int var1;
            label69: {
               var24 = this.card();
               this.add((View)var24);
               JSONObject var8 = var20.optJSONObject("galaxy");
               if (var8 != null) {
                  JSONArray var26 = var8.optJSONArray("settings");
                  if (var26 != null) {
                     var1 = var26.length();
                     break label69;
                  }
               }

               var1 = 0;
            }

            LinearLayout var27 = new LinearLayout(this.getContext());
            var27.setOrientation(0);
            var27.setGravity(16);
            SvIcon var9 = SvIcon.INSTANCE;
            Context var10 = var27.getContext();
            Intrinsics.checkNotNullExpressionValue(var10, "getContext(...)");
            View var11 = SvIcon.chip$default(var9, var10, "standard", "person", 48, null, 16, null);
            LayoutParams var30 = new LayoutParams(this.getUi().dp(48), this.getUi().dp(48));
            var30.rightMargin = this.getUi().dp(14);
            Unit var28 = Unit.INSTANCE;
            var27.addView(var11, (android.view.ViewGroup.LayoutParams)var30);
            LinearLayout var29 = new LinearLayout(var27.getContext());
            var29.setOrientation(1);
            TextView var31 = new TextView(var29.getContext());
            var31.setText(var20.optString("name"));
            var31.setTextColor(CarUi.Companion.getTEXT());
            var31.setTextSize(21.0F);
            var31.setTypeface(Typeface.DEFAULT_BOLD);
            var29.addView((View)var31);
            TextView var32 = new TextView(var29.getContext());
            String var33 = JournalPagesKt.access$getDayFmt$p().format(new Date(var20.optLong("savedAt")));
            var32.setText("Saved " + var33 + " · " + var1 + " StarPilot settings + tablet");
            var32.setTextColor(CarUi.Companion.getDIM());
            var32.setTextSize(15.0F);
            var29.addView((View)var32);
            var27.addView((View)var29, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
            var27.addView((View)this.getUi().button("Apply", true, new DriverProfilesView$$ExternalSyntheticLambda2(this, var20)));
            var27.addView(new View(var27.getContext()), (android.view.ViewGroup.LayoutParams)(new LayoutParams(this.getUi().dp(10), 1)));
            var27.addView(
               (View)CarUi.button$default(this.getUi(), "Delete", false, new DriverProfilesView$$ExternalSyntheticLambda3(var27, var20, var7, this), 2, null)
            );
            var24.addView((View)var27);
         }
      }
   }
}
