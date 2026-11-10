package com.starpilot.starview;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class LanePicturesActivity extends AppCompatActivity {
   private List<LaneShots.Shot> all = CollectionsKt.emptyList();
   private TextView caption;
   private List<LaneShots.Shot> cur;
   private String drive;
   private LinearLayout drives;
   private boolean exporting;
   private final HashMap<String, String> fixed;
   private int i;
   private ZoomImageView img;
   private TextView info;
   private final Lazy lastPrefs$delegate;
   private final HashMap<String, Integer> marks = new HashMap<>();
   private final ActivityResultLauncher<String[]> pickModel;
   private TextView score;
   private LaneShots shots;
   private final HashMap<String, String> skipWhy;

   // $VF: synthetic method
   public static Unit $r8$lambda$0fD0zYPquVOYDAtGqA2Vj3thVY4(LanePicturesActivity var0, AlertDialog var1, String var2) {
      return exportNow$lambda$65$lambda$63(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$1ooKWBXfUrO0EZnxpEfBtv_3Ur8(LanePicturesActivity var0, String var1) {
      pickModel$lambda$23$lambda$22$lambda$21(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6aL4_x7Jh_3Tr3VycYCjt7WivAc/* $VF was: $r8$lambda$6aL4_x7Jh_3Tr3VycYCjt7WivAc*/(LanePicturesActivity var0) {
      return onCreate$lambda$8(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$7Luug_KxZGNDMNgKWOTPnxwsBHo(LanePicturesActivity var0, DialogInterface var1, int var2) {
      afterMark$lambda$74(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7PSGgEahSkxGR9VunSdg1U0oM1s(LanePicturesActivity var0) {
      return onCreate$lambda$4(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$9X6V_esuRC5uB0P1rTadbIh2Y40/* $VF was: $r8$lambda$9X6V_esuRC5uB0P1rTadbIh2Y40*/(
      LanePicturesActivity var0, DialogInterface var1, int var2
   ) {
      modelDialog$lambda$24(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Cf0Y0bRTiODUGHsGGvZgKIzSEF4(LanePicturesActivity var0, String var1, View var2) {
      listDrives$lambda$43$lambda$41(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Kfx50ccD_mDFqOmd1CASZh3oMwI/* $VF was: $r8$lambda$Kfx50ccD_mDFqOmd1CASZh3oMwI*/(LanePicturesActivity var0, AlertDialog var1) {
      exportNow$lambda$65(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$M31cmNCYV8F_6XiByhc24v5ZawA(LanePicturesActivity var0) {
      return onCreate$lambda$20(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Mux2AvKhS54jKKu_eNWI3ROn7yM(LanePicturesActivity var0, String var1, DialogInterface var2, int var3) {
      afterMark$lambda$75(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$OlPLD5hrzxfb4D0R1Ep9geUQGs0(LanePicturesActivity var0, String var1, DialogInterface var2, int var3) {
      askDelete$lambda$78(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$RR3vFpkJUs_gr_bLxKzu3yjdfvc(LanePicturesActivity var0, LaneShots.Shot var1, LaneFixDialog.Result var2) {
      return askFix$lambda$61(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$TZJ1h8Ixl9emF58uCPnGgZy9g0g(LanePicturesActivity var0, String var1) {
      return askFix$lambda$60(var0, var1);
   }

   // $VF: synthetic method
   public static SharedPreferences $r8$lambda$UQH1F5RqynBzP3Kcb8jUhrvHqUI(LanePicturesActivity var0) {
      return lastPrefs_delegate$lambda$52(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$XDJWGkcy0kc_QqMmr_8Zf_yRRzs(AlertDialog var0, String var1) {
      exportNow$lambda$65$lambda$63$lambda$62(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Yuhh0ArGCUW4KSNuQxjrO5WjgzE(LanePicturesActivity var0, Uri var1) {
      pickModel$lambda$23(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cGto8p5Z100E3gwA9VEgK6HgJXQ(LanePicturesActivity var0) {
      return onCreate$lambda$18(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$e0tVuXqkAwvhUXkKnHir2_RSRjc(LanePicturesActivity var0) {
      return onCreate$lambda$16(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$e9yO86RlR3ODJP_HwSlHjhBGCEY/* $VF was: $r8$lambda$e9yO86RlR3ODJP_HwSlHjhBGCEY*/(LanePicturesActivity var0, Uri var1) {
      pickModel$lambda$23$lambda$22(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$eR3fZQGJ0JX82V_GRG7cxxT20mg(LaneRamps.Ramp var0) {
      return show$lambda$50(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$fHmbvonUJh0UzDPVKXHQiBZfn00(AlertDialog var0, LanePicturesActivity var1, String var2) {
      exportNow$lambda$65$lambda$64(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$mYCT1ynT8j9f_zt1sZbt6LvXpRE(LanePicturesActivity var0, String var1, List var2, View var3) {
      return listDrives$lambda$43$lambda$42(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pvSZ9cFGzvSzq4LsPlYButA5QIs(LanePicturesActivity var0, LaneShots.Shot var1) {
      return askFix$lambda$58(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$q6XSR0UDMSSs920_d8yPPJWRSxg(LanePicturesActivity var0) {
      return onCreate$lambda$6(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rnGdhtqdD7oY5GMkOgVK7Gaz4L0(LanePicturesActivity var0) {
      return onCreate$lambda$17(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$wZNoBBUUc5B3L8MOxrMHqW1Xyoo(Function0 var0, View var1) {
      btn$lambda$27$lambda$26(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$z0EGQeOlrM8LyH4KzqmRUC257xk(LanePicturesActivity var0) {
      return onCreate$lambda$19(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zA5vyAidEHpE_1CrYyMz5WQNc1Q/* $VF was: $r8$lambda$zA5vyAidEHpE_1CrYyMz5WQNc1Q*/(LanePicturesActivity var0) {
      return onCreate$lambda$15(var0);
   }

   public LanePicturesActivity() {
      this.fixed = new HashMap<>();
      this.skipWhy = new HashMap<>();
      this.drive = "";
      this.cur = CollectionsKt.emptyList();
      ActivityResultLauncher var1 = this.registerForActivityResult(new OpenDocument(), new LanePicturesActivity$$ExternalSyntheticLambda3(this));
      Intrinsics.checkNotNullExpressionValue(var1, "registerForActivityResult(...)");
      this.pickModel = var1;
      this.lastPrefs$delegate = LazyKt.lazy(new LanePicturesActivity$$ExternalSyntheticLambda4(this));
   }

   private final void afterMark(boolean var1) {
      this.getLastPrefs().edit().putLong("marked_at", System.currentTimeMillis()).apply();
      this.listDrives();
      this.show();
      if (!var1 && this.driveDone()) {
         var var6 = this.cur;
         int var20;
         if (var6 instanceof Collection && ((Collection)var6).isEmpty()) {
            var20 = 0;
         } else {
            int var2 = 0;

            for (LaneShots.Shot var7 : var6) {
               Integer var35 = this.marks.get(var7.getName());
               if (var35 != null) {
                  var20 = var35;
               } else {
                  var20 = 0;
               }

               boolean var18;
               if (var20 > 0) {
                  var18 = 1;
               } else {
                  var18 = 0;
               }

               if (var18) {
                  var18 = var2 + 1;
                  var2 = var18;
                  if (var18 < 0) {
                     CollectionsKt.throwCountOverflow();
                     var2 = var18;
                  }
               }
            }

            var20 = var2;
         }

         var6 = this.cur;
         int var23;
         if (var6 instanceof Collection && ((Collection)var6).isEmpty()) {
            var23 = 0;
         } else {
            int var12 = 0;

            for (LaneShots.Shot var36 : var6) {
               Integer var37 = this.marks.get(var36.getName());
               if (var37 != null) {
                  var23 = var37;
               } else {
                  var23 = 0;
               }

               boolean var21;
               if (var23 < 0) {
                  var21 = 1;
               } else {
                  var21 = 0;
               }

               if (var21) {
                  var21 = var12 + 1;
                  var12 = var21;
                  if (var21 < 0) {
                     CollectionsKt.throwCountOverflow();
                     var12 = var21;
                  }
               }
            }

            var23 = var12;
         }

         var6 = this.cur;
         int var25;
         if (var6 instanceof Collection && ((Collection)var6).isEmpty()) {
            var25 = 0;
         } else {
            int var13 = 0;

            for (LaneShots.Shot var38 : var6) {
               Integer var39 = this.marks.get(var38.getName());
               boolean var5;
               if (var39 != null && var39 == 0) {
                  var5 = 1;
               } else {
                  var5 = 0;
               }

               if (var5) {
                  var5 = var13 + 1;
                  var13 = var5;
                  if (var5 < 0) {
                     CollectionsKt.throwCountOverflow();
                     var13 = var5;
                  }
               }
            }

            var25 = var13;
         }

         Iterator var8 = this.driveNames().iterator();

         while (true) {
            if (!var8.hasNext()) {
               var6 = null;
               break;
            }

            Object var40 = var8.next();
            String var9 = (String)var40;
            var6 = this.all;
            boolean var16;
            if (var6 instanceof Collection && ((Collection)var6).isEmpty()) {
               var16 = false;
            } else {
               label178: {
                  for (LaneShots.Shot var11 : var6) {
                     var6 = var11.getDrive();
                     if (var6.length() == 0) {
                        var16 = true;
                     } else {
                        var16 = false;
                     }

                     if (var16) {
                        var6 = "earlier";
                     }

                     if (Intrinsics.areEqual(var6, var9) && !this.marks.containsKey(var11.getName())) {
                        var16 = true;
                     } else {
                        var16 = false;
                     }

                     if (var16) {
                        var16 = true;
                        break label178;
                     }
                  }

                  var16 = false;
               }
            }

            if (var16) {
               var6 = (Iterable)var40;
               break;
            }
         }

         String var34 = (String)var6;
         Builder var41 = new Builder((Context)this).setTitle("Done ✓");
         int var17 = this.cur.size();
         String var43 = this.drive;
         var41 = var41.setMessage("All " + var17 + " pictures of " + var43 + " are done: " + var20 + " right, " + var23 + " wrong, " + var25 + " skipped.")
            .setPositiveButton("Export", new LanePicturesActivity$$ExternalSyntheticLambda12(this))
            .setNegativeButton("OK", null);
         if (var34 != null) {
            var41.setNeutralButton("Next drive", new LanePicturesActivity$$ExternalSyntheticLambda13(this, var34));
         }

         var41.show();
      }
   }

   private static final void afterMark$lambda$74(LanePicturesActivity var0, DialogInterface var1, int var2) {
      var0.exportNow();
   }

   private static final void afterMark$lambda$75(LanePicturesActivity var0, String var1, DialogInterface var2, int var3) {
      var0.pick(var1);
   }

   private final void askDelete(String var1, List<LaneShots.Shot> var2) {
      long var6 = this.getLastPrefs().getLong("exported_at", 0L);
      boolean var5 = true;
      boolean var4 = var5;
      if (var6 != 0L) {
         var var8 = var2;
         boolean var10;
         if (var8 instanceof Collection && ((Collection)var8).isEmpty()) {
            var10 = false;
         } else {
            label47: {
               Iterator var12 = var8.iterator();

               while (var12.hasNext()) {
                  if (((LaneShots.Shot)var12.next()).getAtMs() > var6) {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  if (var10) {
                     var10 = true;
                     break label47;
                  }
               }

               var10 = false;
            }
         }

         var4 = var5;
         if (!var10) {
            if (this.getLastPrefs().getLong("marked_at", 0L) > var6) {
               var4 = var5;
            } else {
               var4 = false;
            }
         }
      }

      Builder var13 = new Builder((Context)this).setTitle("Delete " + var1 + "?");
      int var11 = var2.size();
      String var9;
      if (var4) {
         var9 = "\n\n⚠ Not everything is exported yet - Export first if you want to keep it for training.";
      } else {
         var9 = "\n\nEverything was exported.";
      }

      var13.setMessage(var11 + " pictures, their training frames and your marks for them." + var9)
         .setPositiveButton("Delete", new LanePicturesActivity$$ExternalSyntheticLambda17(this, var1))
         .setNegativeButton("Cancel", null)
         .show();
   }

   private static final void askDelete$lambda$78(LanePicturesActivity var0, String var1, DialogInterface var2, int var3) {
      LaneShots var6 = var0.shots;
      Object var5 = null;
      Object var4 = null;
      LaneShots var12 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shots");
         var12 = null;
      }

      var3 = var12.deleteDrive(var1);
      var6 = var0.shots;
      LaneShots var13 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shots");
         var13 = null;
      }

      var0.all = var13.list();
      var0.marks.clear();
      HashMap var7 = var0.marks;
      var6 = var0.shots;
      LaneShots var14 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shots");
         var14 = null;
      }

      var7.putAll(var14.verdicts());
      var0.fixed.clear();
      var7 = var0.fixed;
      var6 = var0.shots;
      LaneShots var15 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shots");
         var15 = null;
      }

      var7.putAll(var15.fixes());
      Toast.makeText((Context)var0, "Deleted " + var3 + " pictures", 0).show();
      List var24 = var0.driveNames();
      if (var24.isEmpty()) {
         var0.drive = "";
         var0.cur = CollectionsKt.emptyList();
         ZoomImageView var16 = var0.img;
         ZoomImageView var8 = var16;
         if (var16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("img");
            var8 = null;
         }

         var8.setImageDrawable(null);
         TextView var17 = var0.caption;
         TextView var9 = var17;
         if (var17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("caption");
            var9 = null;
         }

         var9.setText("No lane pictures.");
         TextView var10 = var0.info;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("info");
            var10 = (TextView)var4;
         }

         var10.setText("");
         var0.listDrives();
      } else {
         label41: {
            if (!Intrinsics.areEqual(var1, var0.drive)) {
               String var18 = var0.drive;
               var1 = (String)var5;
               if (var24.contains(var18)) {
                  var1 = var18;
               }

               var19 = var1;
               if (var1 != null) {
                  break label41;
               }
            }

            var19 = CollectionsKt.first(var24);
         }

         var0.pick(var19);
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final void askFix() {
      if (!this.cur.isEmpty()) {
         LaneShots.Shot var9 = this.cur.get(this.i);
         long var4 = var9.getWayId();
         int var3 = 0;
         int var1;
         if (var4 != 0L) {
            LaneBrain.Live var7 = LaneBrain.Live.INSTANCE;
            Context var6 = this.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(var6, "getApplicationContext(...)");
            var1 = var7.savedCount(var6, var9.getWayId());
         } else {
            var1 = 0;
         }

         Bitmap var53;
         label274: {
            LaneShots var8;
            File var58;
            try {
               var8 = this.shots;
            } catch (Exception var47) {
               var53 = null;
               break label274;
            }

            LaneShots var54 = var8;
            if (var8 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("shots");
               } catch (Exception var46) {
                  var53 = null;
                  break label274;
               }

               var54 = null;
            }

            boolean var2;
            label259: {
               label258: {
                  try {
                     var58 = new File(var54.getCleanDir(), var9.getRoadFile());
                     if (var9.getRoadFile().length() > 0) {
                        break label258;
                     }
                  } catch (Exception var45) {
                     var53 = null;
                     break label274;
                  }

                  var2 = false;
                  break label259;
               }

               var2 = true;
            }

            label251: {
               label250: {
                  if (var2) {
                     try {
                        if (var58.exists()) {
                           break label250;
                        }
                     } catch (Exception var44) {
                        var53 = null;
                        break label274;
                     }
                  }

                  var2 = false;
                  break label251;
               }

               var2 = true;
            }

            File var55;
            if (var2) {
               var55 = var58;
            } else {
               var55 = null;
            }

            if (var55 != null) {
               try {
                  var53 = BitmapFactory.decodeFile(var55.getPath());
               } catch (Exception var38) {
                  var53 = null;
               }
            } else {
               var53 = null;
            }
         }

         label241:
         if (var53 == null) {
            try {
               var56 = this.getContentResolver().openInputStream(Uri.parse(var9.getUri()));
            } catch (Exception var43) {
               var53 = null;
               break label241;
            }

            label237:
            if (var56 != null) {
               InputStream var59;
               try {
                  var59 = var56;
               } catch (Exception var42) {
                  var53 = null;
                  break label237;
               }

               try {
                  var53 = BitmapFactory.decodeStream(var59);
               } catch (Throwable var41) {
                  Throwable var57 = var41;

                  try {
                     throw var57;
                  } finally {
                     try {
                        CloseableKt.closeFinally(var59, var57);
                     } catch (Exception var39) {
                        var53 = null;
                        break label237;
                     }
                  }
               }

               try {
                  CloseableKt.closeFinally(var59, null);
               } catch (Exception var37) {
                  var53 = null;
               }
            } else {
               var53 = null;
            }
         }

         LaneFixDialog var10 = LaneFixDialog.INSTANCE;
         Context var11 = (Context)this;
         String var61;
         if (var9.getWayId() != 0L) {
            var61 = var9.getStreet();
            boolean var49;
            if (var61.length() == 0) {
               var49 = true;
            } else {
               var49 = false;
            }

            if (var49) {
               var61 = "this street";
            }

            var61 = var61;
         } else {
            var61 = null;
         }

         LanePicturesActivity$$ExternalSyntheticLambda5 var13 = new LanePicturesActivity$$ExternalSyntheticLambda5(this, var9);
         LaneFixDialog.Result var12 = this.lastFix();
         Object var62 = var9.getStreet();
         int var50 = var3;
         if (var62.length() == 0) {
            var50 = 1;
         }

         if (var50) {
            var62 = "no street";
         }

         String var14 = StringsKt.take(StringsKt.substringBefore$default(var9.getCaption(), "\n", null, 2, null), 9);
         var3 = this.i;
         var50 = this.cur.size();
         LaneFixDialog.show$default(
            var10,
            var11,
            "Fix lanes",
            0,
            var61,
            0,
            false,
            var1,
            var13,
            var12,
            var53,
            var62 + "  ·  " + var14 + "  ·  pinch to zoom  ·  picture " + (var3 + 1) + " of " + var50,
            LaneRamps.INSTANCE.parse(var9.getRamps()),
            new LanePicturesActivity$$ExternalSyntheticLambda6(this),
            null,
            new LanePicturesActivity$$ExternalSyntheticLambda7(this, var9),
            8192,
            null
         );
      }
   }

   private static final Unit askFix$lambda$58(LanePicturesActivity var0, LaneShots.Shot var1) {
      LaneBrain.Live var2 = LaneBrain.Live.INSTANCE;
      Context var3 = var0.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var3, "getApplicationContext(...)");
      var2.forgetCount(var3, var1.getWayId());
      return Unit.INSTANCE;
   }

   private static final Unit askFix$lambda$60(LanePicturesActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "why");
      var0.mark(0, var1);
      return Unit.INSTANCE;
   }

   private static final Unit askFix$lambda$61(LanePicturesActivity var0, LaneShots.Shot var1, LaneFixDialog.Result var2) {
      Intrinsics.checkNotNullParameter(var2, "r");
      var0.keepLast(var2);
      boolean var3 = var0.driveDone();
      LaneShots var5 = var0.shots;
      LaneShots var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shots");
         var4 = null;
      }

      var4.markFix(var1.getName(), var2);
      var0.marks.put(var1.getName(), -1);
      var0.fixed.put(var1.getName(), var2.text());
      if (var2.getRemember() && var1.getWayId() != 0L) {
         LaneBrain.Live var7 = LaneBrain.Live.INSTANCE;
         Context var6 = var0.getApplicationContext();
         Intrinsics.checkNotNullExpressionValue(var6, "getApplicationContext(...)");
         var7.rememberCount(var6, var1.getWayId(), var2.getThrough());
      }

      if (var0.i < var0.cur.size() - 1) {
         var0.i++;
      }

      var0.afterMark(var3);
      return Unit.INSTANCE;
   }

   private final TextView btn(String var1, int var2, Function0<Unit> var3) {
      TextView var4 = new TextView((Context)this);
      var4.setText(var1);
      var4.setTextSize(20.0F);
      var4.setGravity(17);
      var4.setTextColor(-1);
      var4.setTypeface(null, 1);
      GradientDrawable var5 = new GradientDrawable();
      var5.setCornerRadius(12 * this.getD());
      var5.setColor(var2);
      var4.setBackground((Drawable)var5);
      var4.setOnClickListener(new LanePicturesActivity$$ExternalSyntheticLambda2(var3));
      return var4;
   }

   private static final void btn$lambda$27$lambda$26(Function0 var0, View var1) {
      var0.invoke();
   }

   private final int dp(int var1) {
      return (int)(var1 * this.getD());
   }

   private final boolean driveDone() {
      boolean var4 = this.cur.isEmpty();
      boolean var3 = false;
      boolean var2 = var3;
      if (!var4) {
         var var5 = this.cur;
         boolean var1;
         if (var5 instanceof Collection && ((Collection)var5).isEmpty()) {
            var1 = true;
         } else {
            Iterator var6 = var5.iterator();

            while (true) {
               if (!var6.hasNext()) {
                  var1 = true;
                  break;
               }

               LaneShots.Shot var7 = (LaneShots.Shot)var6.next();
               if (!this.marks.containsKey(var7.getName())) {
                  var1 = false;
                  break;
               }
            }
         }

         var2 = var3;
         if (var1) {
            var2 = true;
         }
      }

      return var2;
   }

   private final List<String> driveNames() {
      Iterable var2 = this.all;
      var var3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));
      Iterator var4 = var2.iterator();

      while (var4.hasNext()) {
         var var5 = ((LaneShots.Shot)var4.next()).getDrive();
         boolean var1;
         if (var5.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            var5 = "earlier";
         }

         var3.add(var5);
      }

      List var6 = var3;
      return CollectionsKt.sortedDescending(CollectionsKt.distinct(var6));
   }

   private final void exportNow() {
      if (!this.exporting) {
         this.exporting = true;
         new Thread(
               new LanePicturesActivity$$ExternalSyntheticLambda16(
                  this, new Builder((Context)this).setTitle("Export for training").setMessage("Packing…").setCancelable(false).show()
               )
            )
            .start();
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final void exportNow$lambda$65(LanePicturesActivity var0, AlertDialog var1) {
      String var7;
      label28: {
         LaneShots var3;
         try {
            var3 = var0.shots;
         } catch (Exception var6) {
            var7 = var6.getMessage();
            var7 = "Export failed: " + var7;
            break label28;
         }

         LaneShots var8 = var3;
         if (var3 == null) {
            try {
               Intrinsics.throwUninitializedPropertyAccessException("shots");
            } catch (Exception var5) {
               var7 = var5.getMessage();
               var7 = "Export failed: " + var7;
               break label28;
            }

            var8 = null;
         }

         try {
            LanePicturesActivity$$ExternalSyntheticLambda14 var11 = new LanePicturesActivity$$ExternalSyntheticLambda14(var0, var1);
            var7 = var8.export(var11);
         } catch (Exception var4) {
            var7 = var4.getMessage();
            var7 = "Export failed: " + var7;
         }
      }

      var0.runOnUiThread(new LanePicturesActivity$$ExternalSyntheticLambda15(var1, var0, var7));
   }

   private static final Unit exportNow$lambda$65$lambda$63(LanePicturesActivity var0, AlertDialog var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "p");
      var0.runOnUiThread(new LanePicturesActivity$$ExternalSyntheticLambda27(var1, var2));
      return Unit.INSTANCE;
   }

   private static final void exportNow$lambda$65$lambda$63$lambda$62(AlertDialog var0, String var1) {
      var0.setMessage(var1);
   }

   private static final void exportNow$lambda$65$lambda$64(AlertDialog var0, LanePicturesActivity var1, String var2) {
      var0.dismiss();
      var1.exporting = false;
      new Builder((Context)var1)
         .setTitle("Export for training")
         .setMessage(
            var2
               + "\n\nAttach those zip files in the chat (each is under 25 MB), or put them in your StarView Transfer Drive folder to keep. Next time, Export only adds the frames that are new."
         )
         .setPositiveButton("OK", null)
         .show();
   }

   private final float getD() {
      return this.getResources().getDisplayMetrics().density;
   }

   private final SharedPreferences getLastPrefs() {
      return (SharedPreferences)this.lastPrefs$delegate.getValue();
   }

   private final void keepLast(LaneFixDialog.Result var1) {
      JSONObject var2 = new JSONObject()
         .put("through", var1.getThrough())
         .put("extraL", var1.getExtraL())
         .put("extraR", var1.getExtraR())
         .put("kind", var1.getKind())
         .put("lane", var1.getLane())
         .put("remember", var1.getRemember())
         .put("otherWay", var1.getOtherWay())
         .put("way", var1.getWay())
         .put("kindR", var1.getKindR());
      this.getLastPrefs().edit().putString("last_fix", var2.toString()).apply();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final LaneFixDialog.Result lastFix() {
      Object var8 = null;

      JSONObject var10;
      String var17;
      try {
         var17 = this.getLastPrefs().getString("last_fix", null);
      } catch (Exception var15) {
         return (LaneFixDialog.Result)var8;
      }

      if (var17 == null) {
         return null;
      } else {
         boolean var1;
         int var2;
         int var3;
         int var4;
         int var5;
         boolean var6;
         String var9;
         label51: {
            label50: {
               try {
                  var10 = new JSONObject(var17);
                  var4 = var10.getInt("through");
                  var2 = var10.optInt("extraL");
                  var3 = var10.optInt("extraR");
                  var9 = var10.optString("kind");
                  Intrinsics.checkNotNullExpressionValue(var9, "optString(...)");
                  var5 = var10.optInt("lane");
                  var6 = var10.optBoolean("remember");
                  var17 = var10.optString("way");
                  if (var17.length() == 0) {
                     break label50;
                  }
               } catch (Exception var14) {
                  return (LaneFixDialog.Result)var8;
               }

               var1 = 0;
               break label51;
            }

            var1 = 1;
         }

         label43:
         if (var1) {
            label41: {
               label40: {
                  try {
                     if (var10.has("twoWay")) {
                        if (var10.getBoolean("twoWay")) {
                           break label41;
                        }
                        break label40;
                     }
                  } catch (Exception var13) {
                     return (LaneFixDialog.Result)var8;
                  }

                  var17 = "";
                  break label43;
               }

               var17 = "one";
               break label43;
            }

            var17 = "two";
         }

         try {
            Intrinsics.checkNotNullExpressionValue(var17, "ifEmpty(...)");
            String var11 = var17;
            var1 = var10.optInt("otherWay");
            String var19 = var10.optString("kindR");
            Intrinsics.checkNotNullExpressionValue(var19, "optString(...)");
            var7 = new LaneFixDialog.Result(var4, var2, var3, var9, var5, var6, var11, var1, var19);
         } catch (Exception var12) {
            var7 = (LaneFixDialog.Result)var8;
         }

         return var7;
      }
   }

   private static final SharedPreferences lastPrefs_delegate$lambda$52(LanePicturesActivity var0) {
      return var0.getSharedPreferences("lane_pictures", 0);
   }

   private final void listDrives() {
      LinearLayout var7 = this.drives;
      String var8 = "drives";
      LinearLayout var6 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("drives");
         var6 = null;
      }

      var6.removeAllViews();
      Iterator var42 = this.driveNames().iterator();

      while (true) {
         boolean var5 = var42.hasNext();
         String var46 = "";
         if (!var5) {
            Iterable var55 = this.all;
            var var43 = new ArrayList();

            for (Object var71 : var55) {
               LaneShots.Shot var84 = (LaneShots.Shot)var71;
               Integer var85 = this.marks.get(var84.getName());
               int var25;
               if (var85 != null) {
                  var25 = var85;
               } else {
                  var25 = 0;
               }

               boolean var26;
               if (var25 != 0) {
                  var26 = true;
               } else {
                  var26 = false;
               }

               if (var26) {
                  var43.add(var71);
               }
            }

            List var86 = var43;
            Iterable var57 = var86;
            var var44 = new ArrayList();

            for (Object var72 : var57) {
               if (Intrinsics.areEqual(((LaneShots.Shot)var72).getWhy(), "EVERY_30S")) {
                  var44.add(var72);
               }
            }

            List var73 = var44;
            TextView var59 = this.score;
            TextView var45 = var59;
            if (var59 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("score");
               var45 = null;
            }

            CharSequence var52;
            if (var86.isEmpty()) {
               var52 = "nothing marked yet";
            } else {
               var var60 = var86;
               int var35;
               if (var60 instanceof Collection && ((Collection)var60).isEmpty()) {
                  var35 = 0;
               } else {
                  int var27 = 0;

                  for (LaneShots.Shot var88 : var60) {
                     var88 = this.marks.get(var88.getName());
                     Intrinsics.checkNotNull(var88);
                     boolean var33;
                     if (((Number)var88).intValue() > 0) {
                        var33 = 1;
                     } else {
                        var33 = 0;
                     }

                     if (var33) {
                        var33 = var27 + 1;
                        var27 = var33;
                        if (var33 < 0) {
                           CollectionsKt.throwCountOverflow();
                           var27 = var33;
                        }
                     }
                  }

                  var35 = var27;
               }

               int var41 = var86.size();
               if (!var73.isEmpty()) {
                  var var53 = var73;
                  int var28;
                  if (var53 instanceof Collection && ((Collection)var53).isEmpty()) {
                     var28 = 0;
                  } else {
                     var28 = 0;

                     for (LaneShots.Shot var62 : var53) {
                        var8 = this.marks.get(var62.getName());
                        Intrinsics.checkNotNull(var8);
                        boolean var38;
                        if (((Number)var8).intValue() > 0) {
                           var38 = 1;
                        } else {
                           var38 = 0;
                        }

                        if (var38) {
                           var38 = var28 + 1;
                           var28 = var38;
                           if (var38 < 0) {
                              CollectionsKt.throwCountOverflow();
                              var28 = var38;
                           }
                        }
                     }
                  }

                  int var40 = 100 * var28 / var73.size();
                  var28 = var73.size();
                  var46 = "  ·  every-30-s: " + var40 + "% of " + var28;
               }

               var52 = "all drives: " + var35 + " of " + var41 + " right" + var46;
            }

            var45.setText(var52);
            return;
         }

         String var11 = (String)var42.next();
         Iterable var9 = this.all;
         var var10 = new ArrayList();

         for (Object var12 : var9) {
            var9 = ((LaneShots.Shot)var12).getDrive();
            boolean var1;
            if (var9.length() == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (var1) {
               var9 = "earlier";
            }

            if (Intrinsics.areEqual(var9, var11)) {
               var10.add(var12);
            }
         }

         List var90 = var10;
         Iterable var74 = var90;
         var var65 = new ArrayList();

         for (Object var75 : var74) {
            LaneShots.Shot var14 = (LaneShots.Shot)var75;
            Integer var93 = this.marks.get(var14.getName());
            int var15;
            if (var93 != null) {
               var15 = var93;
            } else {
               var15 = 0;
            }

            boolean var16;
            if (var15 != 0) {
               var16 = true;
            } else {
               var16 = false;
            }

            if (var16) {
               var65.add(var75);
            }
         }

         List var92 = var65;
         var9 = var90;
         int var31;
         if (var9 instanceof Collection && ((Collection)var9).isEmpty()) {
            var31 = 0;
         } else {
            int var17 = 0;

            for (LaneShots.Shot var76 : var9) {
               Integer var77 = this.marks.get(var76.getName());
               boolean var2;
               if (var77 != null && var77 == 0) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var2) {
                  var2 = var17 + 1;
                  var17 = var2;
                  if (var2 < 0) {
                     CollectionsKt.throwCountOverflow();
                     var17 = var2;
                  }
               }
            }

            var31 = var17;
         }

         var9 = var92;
         int var18;
         if (var9 instanceof Collection && ((Collection)var9).isEmpty()) {
            var18 = 0;
         } else {
            var18 = 0;

            for (LaneShots.Shot var78 : var9) {
               Object var79 = this.marks.get(var78.getName());
               Intrinsics.checkNotNull(var79);
               boolean var3;
               if (((Number)var79).intValue() > 0) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var3) {
                  if (++var18 < 0) {
                     CollectionsKt.throwCountOverflow();
                  }
               }
            }
         }

         LinearLayout var80 = this.drives;
         LinearLayout var70 = var80;
         if (var80 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(var8);
            var70 = null;
         }

         TextView var94 = new TextView((Context)this);
         int var36 = var90.size();
         int var4 = var92.size();
         String var81;
         if (var31 > 0) {
            var81 = " · " + var31 + " skipped";
         } else {
            var81 = "";
         }

         if (!var92.isEmpty()) {
            var18 = var18 * 100 / var92.size();
            var46 = " · " + var18 + "% right";
         }

         var94.setText(var11 + "\n" + var36 + " pictures · " + var4 + " marked" + var81 + var46);
         var94.setTextSize(15.0F);
         if (Intrinsics.areEqual(var11, this.drive)) {
            var18 = -16777216;
         } else {
            var18 = -1;
         }

         label272: {
            var94.setTextColor(var18);
            var94.setPadding(this.dp(12), this.dp(10), this.dp(12), this.dp(10));
            if (!var90.isEmpty()) {
               var var47 = var90;
               boolean var21;
               if (var47 instanceof Collection && ((Collection)var47).isEmpty()) {
                  var21 = true;
               } else {
                  Iterator var82 = var47.iterator();

                  while (true) {
                     if (!var82.hasNext()) {
                        var21 = true;
                        break;
                     }

                     LaneShots.Shot var48 = (LaneShots.Shot)var82.next();
                     if (!this.marks.containsKey(var48.getName())) {
                        var21 = false;
                        break;
                     }
                  }
               }

               if (var21) {
                  var22 = true;
                  break label272;
               }
            }

            var22 = false;
         }

         if (var22) {
            CharSequence var49 = var94.getText();
            var94.setText("✓ " + var49);
         }

         GradientDrawable var50 = new GradientDrawable();
         var50.setCornerRadius(10 * this.getD());
         if (Intrinsics.areEqual(var11, this.drive)) {
            var18 = Color.rgb(111, 227, 161);
         } else {
            byte var24;
            byte var32;
            byte var37;
            if (var22) {
               var32 = 64;
               var37 = 42;
               var24 = 22;
            } else {
               var32 = 34;
               var37 = 44;
               var24 = 30;
            }

            var18 = Color.rgb(var24, var32, var37);
         }

         var50.setColor(var18);
         var94.setBackground((Drawable)var50);
         var94.setOnClickListener(new LanePicturesActivity$$ExternalSyntheticLambda9(this, var11));
         var94.setOnLongClickListener(new LanePicturesActivity$$ExternalSyntheticLambda10(this, var11, var90));
         View var87 = (View)var94;
         LayoutParams var83 = new LayoutParams(-1, -2);
         var83.bottomMargin = this.dp(8);
         Unit var51 = Unit.INSTANCE;
         var70.addView(var87, (android.view.ViewGroup.LayoutParams)var83);
      }
   }

   private static final void listDrives$lambda$43$lambda$41(LanePicturesActivity var0, String var1, View var2) {
      var0.pick(var1);
   }

   private static final boolean listDrives$lambda$43$lambda$42(LanePicturesActivity var0, String var1, List var2, View var3) {
      var0.askDelete(var1, var2);
      return true;
   }

   private final void mark(int var1, String var2) {
      if (!this.cur.isEmpty()) {
         boolean var3 = this.driveDone();
         LaneShots.Shot var6 = this.cur.get(this.i);
         LaneShots var5 = this.shots;
         LaneShots var4 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shots");
            var4 = null;
         }

         var4.mark(var6.getName(), var1, var2);
         this.marks.put(var6.getName(), var1);
         this.fixed.remove(var6.getName());
         if (var1 == 0) {
            Map var9 = this.skipWhy;
            String var10 = var6.getName();
            Object var8 = var2;
            boolean var7;
            if (var8.length() == 0) {
               var7 = true;
            } else {
               var7 = false;
            }

            if (var7) {
               var8 = "skip";
            }

            var9.put(var10, var8);
         }

         if (this.i < this.cur.size() - 1) {
            this.i++;
         }

         this.afterMark(var3);
      }
   }

   // $VF: synthetic method
   static void mark$default(LanePicturesActivity var0, int var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = "";
      }

      var0.mark(var1, var2);
   }

   private final void modelDialog() {
      Context var1 = this.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var1, "getApplicationContext(...)");
      LaneModel var2 = new LaneModel(var1);
      String var4;
      if (var2.getHasModel()) {
         String var3 = String.format("%.1f", Arrays.copyOf(new Object[]{var2.modelFile().length() / 1000000.0}, 1));
         Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
         String var5 = new SimpleDateFormat("MMM d, HH:mm", Locale.US).format(new Date(var2.modelFile().lastModified()));
         var4 = "Installed: " + var3 + " MB, from " + var5 + ".";
      } else {
         var4 = "No lane model installed yet.";
      }

      new Builder((Context)this)
         .setTitle("Lane model")
         .setMessage(
            var4
               + "\n\nThe model trained on your lane pictures. It looks at the road camera every 2 s and tells the lane card how many lanes are left and right of you - on city streets only for now (on highways it needs pictures from more drives).\n\nA new model comes as a .lite file: download it on this tablet, then Import."
         )
         .setPositiveButton("Import…", new LanePicturesActivity$$ExternalSyntheticLambda8(this))
         .setNegativeButton("Close", null)
         .show();
   }

   private static final void modelDialog$lambda$24(LanePicturesActivity var0, DialogInterface var1, int var2) {
      var0.pickModel.launch(new String[]{"*/*"});
   }

   private static final Unit onCreate$lambda$15(LanePicturesActivity var0) {
      if (var0.i > 0) {
         var0.i--;
         var0.show();
      }

      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$16(LanePicturesActivity var0) {
      mark$default(var0, 1, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$17(LanePicturesActivity var0) {
      mark$default(var0, -1, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$18(LanePicturesActivity var0) {
      var0.askFix();
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$19(LanePicturesActivity var0) {
      mark$default(var0, 0, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$20(LanePicturesActivity var0) {
      if (var0.i < var0.cur.size() - 1) {
         var0.i++;
         var0.show();
      }

      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$4(LanePicturesActivity var0) {
      var0.modelDialog();
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$6(LanePicturesActivity var0) {
      var0.exportNow();
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$8(LanePicturesActivity var0) {
      var0.finish();
      return Unit.INSTANCE;
   }

   private static final LayoutParams onCreate$lp(LanePicturesActivity var0) {
      LayoutParams var1 = new LayoutParams(0, var0.dp(60), 1.0F);
      var1.setMarginEnd(var0.dp(10));
      return var1;
   }

   private final void pick(String var1) {
      this.drive = var1;
      Iterable var5 = this.all;
      var var6 = new ArrayList();
      Iterator var7 = var5.iterator();

      while (true) {
         boolean var4 = var7.hasNext();
         byte var3 = 0;
         int var2 = 0;
         if (!var4) {
            List var9 = var6;
            this.cur = var9;
            List var10 = this.cur;
            var2 = 0;
            Iterator var14 = var10.iterator();

            while (true) {
               if (!var14.hasNext()) {
                  var2 = -1;
                  break;
               }

               LaneShots.Shot var11 = (LaneShots.Shot)var14.next();
               if (!this.marks.containsKey(var11.getName())) {
                  break;
               }

               var2++;
            }

            if (var2 < 0) {
               var2 = var3;
            }

            this.i = var2;
            this.listDrives();
            this.show();
            return;
         }

         Object var8 = var7.next();
         Object var13 = ((LaneShots.Shot)var8).getDrive();
         if (var13.length() == 0) {
            var2 = 1;
         }

         if (var2) {
            var13 = "earlier";
         }

         if (Intrinsics.areEqual(var13, var1)) {
            var6.add(var8);
         }
      }
   }

   private static final void pickModel$lambda$23(LanePicturesActivity var0, Uri var1) {
      if (var1 != null) {
         new Thread(new LanePicturesActivity$$ExternalSyntheticLambda19(var0, var1)).start();
      }
   }

   private static final void pickModel$lambda$23$lambda$22(LanePicturesActivity var0, Uri var1) {
      Context var2 = var0.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var2, "getApplicationContext(...)");
      var0.runOnUiThread(new LanePicturesActivity$$ExternalSyntheticLambda1(var0, new LaneModel(var2).importModel(var1)));
   }

   private static final void pickModel$lambda$23$lambda$22$lambda$21(LanePicturesActivity var0, String var1) {
      Builder var2 = new Builder((Context)var0).setTitle("Lane model");
      if (var1 == null) {
         var1 = "Installed. The lane card uses it on city streets from the next drive.";
      }

      var2.setMessage(var1).setPositiveButton("OK", null).show();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final void show() {
      if (!this.cur.isEmpty()) {
         LaneShots.Shot var9 = this.cur.get(this.i);

         Bitmap var5;
         label257: {
            InputStream var4;
            try {
               var4 = this.getContentResolver().openInputStream(Uri.parse(var9.getUri()));
            } catch (Exception var29) {
               var5 = null;
               break label257;
            }

            label248:
            if (var4 != null) {
               try {
                  var4 = var4;
               } catch (Exception var28) {
                  var5 = null;
                  break label248;
               }

               try {
                  var5 = BitmapFactory.decodeStream(var4);
               } catch (Throwable var27) {
                  Throwable var39 = var27;

                  try {
                     throw var39;
                  } finally {
                     try {
                        CloseableKt.closeFinally(var4, var39);
                     } catch (Exception var25) {
                        var5 = null;
                        break label248;
                     }
                  }
               }

               try {
                  CloseableKt.closeFinally(var4, null);
               } catch (Exception var24) {
                  var5 = null;
               }
            } else {
               var5 = null;
            }
         }

         ZoomImageView var31 = this.img;
         if (var5 != null) {
            ZoomImageView var6 = var31;
            if (var31 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("img");
               var6 = null;
            }

            var6.setImageBitmap(var5);
         } else {
            ZoomImageView var41 = var31;
            if (var31 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("img");
               var41 = null;
            }

            var41.setImageDrawable(null);
         }

         TextView var42 = this.caption;
         TextView var32 = var42;
         if (var42 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("caption");
            var32 = null;
         }

         var32.setText(var9.getCaption());
         Integer var8 = this.marks.get(var9.getName());
         TextView var33 = this.info;
         TextView var7 = var33;
         if (var33 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("info");
            var7 = null;
         }

         int var2 = this.i;
         boolean var1 = true;
         int var3 = this.cur.size();
         String var34 = var9.getWhy();
         String var43;
         if (Intrinsics.areEqual(var34, "USER")) {
            var43 = "you tapped";
         } else if (Intrinsics.areEqual(var34, "EVERY_30S")) {
            var43 = "every 30 s";
         } else {
            var43 = "lane event";
         }

         String var10 = "";
         String var35;
         if (var8 == null) {
            var35 = "  ·  not marked";
         } else if (var8 > 0) {
            var35 = "  ·  marked RIGHT";
         } else if (var8 == 0) {
            label229: {
               String var36 = this.skipWhy.get(var9.getName());
               if (var36 != null) {
                  switch (var36.hashCode()) {
                     case 169749129:
                        if (var36.equals("intersection")) {
                           var37 = " (intersection)";
                           break label229;
                        }
                        break;
                     case 1641332067:
                        if (var36.equals("lane_change")) {
                           var37 = " (changing lanes)";
                           break label229;
                        }
                  }
               }

               var37 = "";
            }

            var35 = "  ·  skipped" + var37;
         } else {
            var35 = "  ·  marked WRONG";
         }

         if (var9.getRamps().length() <= 0) {
            var1 = false;
         }

         String var45;
         if (var1) {
            String var44 = CollectionsKt.joinToString$default(
               LaneRamps.INSTANCE.parse(var9.getRamps()), ", ", null, null, 0, null, new LanePicturesActivity$$ExternalSyntheticLambda18(), 30, null
            );
            var45 = "  ·  map: " + var44;
         } else {
            var45 = "";
         }

         label218: {
            String var46 = this.fixed.get(var9.getName());
            if (var46 != null) {
               String var11 = "  ·  you fixed it: " + var46;
               var47 = var11;
               if (var11 != null) {
                  break label218;
               }
            }

            var47 = "";
         }

         if (var5 == null) {
            var10 = "  ·  picture deleted";
         }

         var7.setText(var2 + 1 + " of " + var3 + "  ·  " + var43 + var35 + var45 + var47 + var10);
         TextView var40 = this.info;
         TextView var38 = var40;
         if (var40 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("info");
            var38 = null;
         }

         var38.setVisibility(0);
      }
   }

   private static final CharSequence show$lambda$50(LaneRamps.Ramp var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.text();
   }

   @Override
   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Context var6 = this.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var6, "getApplicationContext(...)");
      this.shots = new LaneShots(var6);
      LaneShots var2 = this.shots;
      LaneShots var7 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shots");
         var7 = null;
      }

      this.all = var7.list();
      HashMap var3 = this.marks;
      var2 = this.shots;
      LaneShots var8 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shots");
         var8 = null;
      }

      var3.putAll(var8.verdicts());
      var3 = this.fixed;
      var2 = this.shots;
      LaneShots var9 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shots");
         var9 = null;
      }

      var3.putAll(var9.fixes());
      var3 = this.skipWhy;
      var2 = this.shots;
      LaneShots var10 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("shots");
         var10 = null;
      }

      var3.putAll(var10.skipReasons());
      LinearLayout var39 = new LinearLayout((Context)this);
      var39.setOrientation(1);
      var39.setBackgroundColor(Color.rgb(11, 13, 18));
      LinearLayout var4 = new LinearLayout((Context)this);
      var4.setGravity(16);
      var4.setPadding(this.dp(16), this.dp(10), this.dp(16), this.dp(6));
      TextView var11 = new TextView((Context)this);
      var11.setText("Lane pictures");
      var11.setTextColor(-1);
      var11.setTextSize(20.0F);
      var11.setTypeface(null, 1);
      var4.addView((View)var11, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      TextView var12 = new TextView((Context)this);
      var12.setTextSize(15.0F);
      var12.setTextColor(Color.rgb(200, 208, 222));
      var12.setPadding(0, 0, this.dp(16), 0);
      this.score = var12;
      TextView var29 = this.score;
      TextView var13 = var29;
      if (var29 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("score");
         var13 = null;
      }

      var4.addView((View)var13);
      View var30 = (View)this.btn("Lane model", Color.rgb(48, 54, 66), new LanePicturesActivity$$ExternalSyntheticLambda0(this));
      LayoutParams var14 = new LayoutParams(this.dp(150), this.dp(44));
      var14.setMarginEnd(this.dp(10));
      Unit var5 = Unit.INSTANCE;
      var4.addView(var30, (android.view.ViewGroup.LayoutParams)var14);
      View var31 = (View)this.btn("Export", Color.rgb(52, 92, 170), new LanePicturesActivity$$ExternalSyntheticLambda11(this));
      LayoutParams var41 = new LayoutParams(this.dp(130), this.dp(44));
      var41.setMarginEnd(this.dp(10));
      Unit var15 = Unit.INSTANCE;
      var4.addView(var31, (android.view.ViewGroup.LayoutParams)var41);
      var4.addView(
         (View)this.btn("Close", Color.rgb(48, 54, 66), new LanePicturesActivity$$ExternalSyntheticLambda20(this)),
         (android.view.ViewGroup.LayoutParams)(new LayoutParams(this.dp(110), this.dp(44)))
      );
      var39.addView((View)var4);
      var4 = new LinearLayout((Context)this);
      LinearLayout var16 = new LinearLayout((Context)this);
      var16.setOrientation(1);
      var16.setPadding(this.dp(12), 0, this.dp(8), this.dp(12));
      this.drives = var16;
      ScrollView var42 = new ScrollView((Context)this);
      LinearLayout var32 = this.drives;
      LinearLayout var17 = var32;
      if (var32 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("drives");
         var17 = null;
      }

      var42.addView((View)var17);
      var4.addView((View)var42, (android.view.ViewGroup.LayoutParams)(new LayoutParams(this.dp(250), -1)));
      LinearLayout var43 = new LinearLayout((Context)this);
      var43.setOrientation(1);
      var43.setPadding(this.dp(8), 0, this.dp(16), this.dp(12));
      this.img = new ZoomImageView((Context)this);
      ZoomImageView var33 = this.img;
      ZoomImageView var18 = var33;
      if (var33 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("img");
         var18 = null;
      }

      var43.addView((View)var18, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, 0, 1.0F)));
      TextView var19 = new TextView((Context)this);
      var19.setTextSize(16.0F);
      var19.setTextColor(-1);
      var19.setPadding(0, this.dp(6), 0, 0);
      this.caption = var19;
      TextView var20 = new TextView((Context)this);
      var20.setTextSize(14.0F);
      var20.setTextColor(Color.rgb(160, 168, 184));
      var20.setPadding(0, this.dp(2), 0, this.dp(8));
      this.info = var20;
      TextView var34 = this.caption;
      TextView var21 = var34;
      if (var34 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("caption");
         var21 = null;
      }

      var43.addView((View)var21);
      TextView var35 = this.info;
      TextView var22 = var35;
      if (var35 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("info");
         var22 = null;
      }

      var43.addView((View)var22);
      LinearLayout var23 = new LinearLayout((Context)this);
      var23.addView(
         (View)this.btn("◀", Color.rgb(48, 54, 66), new LanePicturesActivity$$ExternalSyntheticLambda21(this)),
         (android.view.ViewGroup.LayoutParams)onCreate$lp(this)
      );
      var23.addView(
         (View)this.btn("✓  Right", Color.rgb(34, 150, 74), new LanePicturesActivity$$ExternalSyntheticLambda22(this)),
         (android.view.ViewGroup.LayoutParams)onCreate$lp(this)
      );
      var23.addView(
         (View)this.btn("✗  Wrong", Color.rgb(200, 52, 52), new LanePicturesActivity$$ExternalSyntheticLambda23(this)),
         (android.view.ViewGroup.LayoutParams)onCreate$lp(this)
      );
      var23.addView(
         (View)this.btn("Fix", Color.rgb(52, 92, 170), new LanePicturesActivity$$ExternalSyntheticLambda24(this)),
         (android.view.ViewGroup.LayoutParams)onCreate$lp(this)
      );
      var23.addView(
         (View)this.btn("Skip", Color.rgb(90, 96, 110), new LanePicturesActivity$$ExternalSyntheticLambda25(this)),
         (android.view.ViewGroup.LayoutParams)onCreate$lp(this)
      );
      var23.addView(
         (View)this.btn("▶", Color.rgb(48, 54, 66), new LanePicturesActivity$$ExternalSyntheticLambda26(this)),
         (android.view.ViewGroup.LayoutParams)onCreate$lp(this)
      );
      var43.addView((View)var23);
      var4.addView((View)var43, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -1, 1.0F)));
      var39.addView((View)var4, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, 0, 1.0F)));
      this.setContentView((View)var39);
      List var24 = this.driveNames();
      if (var24.isEmpty()) {
         TextView var36 = this.caption;
         TextView var25 = var36;
         if (var36 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("caption");
            var25 = null;
         }

         var25.setText("No lane pictures yet. They're taken while driving with the lane card on.");
         this.listDrives();
      } else {
         this.pick(CollectionsKt.first(var24));
      }
   }
}
