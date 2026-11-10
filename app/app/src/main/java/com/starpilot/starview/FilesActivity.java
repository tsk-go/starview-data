package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.Result.Companion;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.LongRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FilesActivity extends AppCompatActivity {
   private Button allFilesBtn;
   private volatile boolean busy;
   private final AtomicBoolean cancel;
   private Button cancelBtn;
   private FilesActivity.Pane comma;
   private Button copyBtn;
   private TextView copyHint;
   private float d;
   private final SimpleDateFormat dateFmt;
   private final int dim;
   private final OkHttpClient http;
   private final ExecutorService io;
   private Button pauseBtn;
   private final AtomicBoolean paused;
   private ProgressBar progBar;
   private LinearLayout progBox;
   private TextView progTv;
   private LinearLayout queueList;
   private final ArrayList<FilesActivity.QRow> queueRows;
   private FilesActivity.Pane tablet;
   private final Handler ui = new Handler(Looper.getMainLooper());

   // $VF: synthetic method
   public static void $r8$lambda$_8kcHvo9siipij5zMRAns3W5T_w/* $VF was: $r8$lambda$_8kcHvo9siipij5zMRAns3W5T_w*/(
      FilesActivity var0, int var1, long var2, IntRef var4
   ) {
      startCopy$lambda$51$lambda$45(var0, var1, var2, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$08fyzqrBHAt4NrLqgQGKL2YPbHY(
      FilesActivity var0, boolean var1, boolean var2, String var3, String var4, String var5, ObjectRef var6, Function1 var7, CountDownLatch var8
   ) {
      askClash$lambda$60(var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static void $r8$lambda$956CWgGB2Yl7M_iHD6nzrN6CT3M(EditText var0, Function1 var1, DialogInterface var2, int var3) {
      input$lambda$93(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$9Nu8SiN62Gm3nn3wZJ0WmolR0xg(ObjectRef var0, AlertDialog var1, CountDownLatch var2, View var3) {
      askClash$lambda$60$lambda$59$lambda$58$lambda$57(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$B71AwJUKmdzGtWn_i06clGOpXCE/* $VF was: $r8$lambda$B71AwJUKmdzGtWn_i06clGOpXCE*/(
      ObjectRef var0, CheckBox var1, Function1 var2, CountDownLatch var3, DialogInterface var4, int var5
   ) {
      askClash$lambda$60$lambda$56(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static void $r8$lambda$B79RCNZEvkbcdoI2ASdzVuhWeSw(FilesActivity var0, Exception var1) {
      saveText$lambda$83$lambda$82(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$DIH6C1wmN4yN_SYmpRJnYzOVPIA(FilesActivity var0, int var1, String var2) {
      startCopy$lambda$51$lambda$44(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$DsZSTqM3dGchn1wSHlOFO7OUjT0(Function0 var0) {
      op$lambda$30$lambda$28(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$EwWSzq4mOJlmJeuU9Q_cfGmFWHE(FilesActivity var0) {
      startCopy$lambda$51$lambda$50$lambda$48(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$JqD4IyJwlS2IPdFsiqmx5JvPcY4(FilesActivity.Pane var0, FilesActivity var1, String var2, String var3, Function0 var4) {
      saveText$lambda$83(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$NYfsFhljQJESxSmgStW3R_3oAIo/* $VF was: $r8$lambda$NYfsFhljQJESxSmgStW3R_3oAIo*/(FilesActivity var0, View var1) {
      onCreate$lambda$6$lambda$5(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$O7lLKftVtezxQNBDwWCj4Jk8uBE(FilesActivity var0, JSONObject var1, Function0 var2) {
      op$lambda$30(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$OumoQvEMN5AiiWxnjucR44_MfhI(FilesActivity var0, Button var1, View var2) {
      onCreate$lambda$19$lambda$18(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PGYk27CuUgATmOahFdXELmr_pe4(LongRef var0, long var1, FilesActivity var3, String var4, long var5, int var7, long var8) {
      return startCopy$lambda$51$lambda$41(var0, var1, var3, var4, var5, var7, var8);
   }

   // $VF: synthetic method
   public static void $r8$lambda$PPmkhAurUWo_2ZcYAr6Olva_kW4/* $VF was: $r8$lambda$PPmkhAurUWo_2ZcYAr6Olva_kW4*/(FilesActivity var0, int var1) {
      startCopy$lambda$51$lambda$42(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$QXQHjiQe7Ehrw_vRmEzbqUf_moY/* $VF was: $r8$lambda$QXQHjiQe7Ehrw_vRmEzbqUf_moY*/(FilesActivity var0, View var1) {
      onCreate$lambda$21$lambda$20(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$UGyT5pxmrun_CFEhsPrmsfi1NQE/* $VF was: $r8$lambda$UGyT5pxmrun_CFEhsPrmsfi1NQE*/(
      FilesActivity var0, FilesActivity.Entry var1, EditText var2, DialogInterface var3, int var4
   ) {
      showPreview$lambda$74(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Y8ByfF5V_kKU6LfUvwAlcDVCbt4/* $VF was: $r8$lambda$Y8ByfF5V_kKU6LfUvwAlcDVCbt4*/(
      FilesActivity var0, FilesActivity.Pane var1, String var2, EditText var3, AlertDialog var4, DialogInterface var5, int var6
   ) {
      showPreview$lambda$78$lambda$77(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static void $r8$lambda$YFxHCuh6m3arDphGs1cz6TTtw_k(FilesActivity var0) {
      startCopy$lambda$51$lambda$46(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_PUkzWo_kxG_6I31EzJ13GILGGo(AlertDialog var0) {
      return showPreview$lambda$78$lambda$77$lambda$76(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_bxrS8y10aAVjZ9k3a6QHgwHCIU(FilesActivity var0, JSONObject var1, Exception var2) {
      op$lambda$30$lambda$29(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$dGTUW9LA0_U8x686ur_0iZqJWFY/* $VF was: $r8$lambda$dGTUW9LA0_U8x686ur_0iZqJWFY*/(FilesActivity var0, View var1) {
      onCreate$lambda$4$lambda$3(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$elgoBHE2TaogUnJ2LcZesHkZhnk(ObjectRef var0, CheckBox var1, Function1 var2, CountDownLatch var3, DialogInterface var4, int var5) {
      askClash$lambda$60$lambda$55(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fQWqrW0LEXfM_Cafm8WwMhvFN38(ObjectRef var0, FilesActivity.Clash var1) {
      return startCopy$lambda$51$lambda$36(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$iis_4VCUd9jHtWsQ_yr77kWkF0s/* $VF was: $r8$lambda$iis_4VCUd9jHtWsQ_yr77kWkF0s*/(
      FilesActivity var0, FilesActivity.Pane var1, FilesActivity.Pane var2
   ) {
      startCopy$lambda$51$lambda$50(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$iyOIDxXC3Z_Hu9yFEphUs6Smhko(FilesActivity var0, int var1) {
      startCopy$lambda$51$lambda$43(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$j7TmjCGtthbm44BgX48carO2U_k/* $VF was: $r8$lambda$j7TmjCGtthbm44BgX48carO2U_k*/(
      FilesActivity var0, String var1, long var2, long var4, double var6, int var8, long var9
   ) {
      startCopy$lambda$51$lambda$41$lambda$40(var0, var1, var2, var4, var6, var8, var9);
   }

   // $VF: synthetic method
   public static void $r8$lambda$jLvs_jqhkcmZe2cX9OqyBfYUCOY/* $VF was: $r8$lambda$jLvs_jqhkcmZe2cX9OqyBfYUCOY*/(
      ObjectRef var0, CheckBox var1, Function1 var2, CountDownLatch var3, DialogInterface var4, int var5
   ) {
      askClash$lambda$60$lambda$54(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static void $r8$lambda$kauXKJhwnfObxYLAbR1AVAyS9Dg(FilesActivity var0, View var1) {
      onCreate$lambda$11$lambda$10(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$p8sOASHJSjKBQzIjMnPMurxJffg(
      EditText var0, FilesActivity var1, FilesActivity.Entry var2, String var3, String var4, FilesActivity.Pane var5, AlertDialog var6, View var7
   ) {
      showPreview$lambda$78(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static void $r8$lambda$qaWGeGXWatKDfVFhWzaRX_xhRds/* $VF was: $r8$lambda$qaWGeGXWatKDfVFhWzaRX_xhRds*/(
      FilesActivity var0, DialogInterface var1, int var2
   ) {
      askCopy$lambda$31(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$qtb27KLDavSMbS4frEWO1NIQPWw(FilesActivity var0, Exception var1) {
      startCopy$lambda$51$lambda$47(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$tqAt6bsxQCbcUYIt2UnLNws2P1Y(FilesActivity.Pane var0, FilesActivity var1, String var2, List var3, FilesActivity.Pane var4) {
      startCopy$lambda$51(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$uU8aYRD1F_o2yhTWQtw2kaywgjc/* $VF was: $r8$lambda$uU8aYRD1F_o2yhTWQtw2kaywgjc*/(
      FilesActivity var0, FilesActivity.Pane var1, FilesActivity.Pane var2, DialogInterface var3, int var4
   ) {
      confirmCopy$lambda$32(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$yD8yLJcDlVgzNa_GlsbSVrhDet8(FilesActivity var0, ArrayList var1, CountDownLatch var2) {
      startCopy$lambda$51$lambda$39(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$zA6qWUXuWCzxw178OFj2kLgC_to/* $VF was: $r8$lambda$zA6qWUXuWCzxw178OFj2kLgC_to*/(
      FilesActivity var0, Function0 var1, FilesActivity.Pane var2
   ) {
      saveText$lambda$83$lambda$81(var0, var1, var2);
   }

   public FilesActivity() {
      this.io = Executors.newSingleThreadExecutor();
      this.http = new Builder()
         .addInterceptor(Link.INSTANCE.getAuthInterceptor())
         .connectTimeout(5L, TimeUnit.SECONDS)
         .readTimeout(0L, TimeUnit.MILLISECONDS)
         .writeTimeout(0L, TimeUnit.MILLISECONDS)
         .build();
      this.dim = Color.rgb(143, 163, 199);
      this.d = 1.0F;
      this.cancel = new AtomicBoolean(false);
      this.queueRows = new ArrayList<>();
      this.paused = new AtomicBoolean(false);
      this.dateFmt = new SimpleDateFormat("MM-dd HH:mm", Locale.US);
   }

   // $VF: synthetic method
   public static final AtomicBoolean access$getCancel$p(FilesActivity var0) {
      return var0.cancel;
   }

   // $VF: synthetic method
   public static final OkHttpClient access$getHttp$p(FilesActivity var0) {
      return var0.http;
   }

   // $VF: synthetic method
   public static final void access$waitIfPaused(FilesActivity var0) {
      var0.waitIfPaused();
   }

   private final FilesActivity.Clash askClash(
      String var1, boolean var2, boolean var3, String var4, String var5, Function1<? super FilesActivity.Clash, Unit> var6
   ) {
      CountDownLatch var7 = new CountDownLatch(1);
      ObjectRef var8 = new ObjectRef();
      var8.element = (T)FilesActivity.Clash.CANCEL;
      this.ui.post(new FilesActivity$$ExternalSyntheticLambda18(this, var3, var2, var1, var4, var5, var8, var6, var7));
      var7.await();
      return (FilesActivity.Clash)var8.element;
   }

   private static final void askClash$lambda$60(
      FilesActivity var0, boolean var1, boolean var2, String var3, String var4, String var5, ObjectRef var6, Function1 var7, CountDownLatch var8
   ) {
      CheckBox var12 = new CheckBox((Context)var0);
      var12.setText("Do this for all remaining conflicts");
      var12.setPadding(var0.dp(8), var0.dp(8), 0, 0);
      String var10;
      if (var1) {
         var10 = "A folder";
      } else {
         var10 = "A file";
      }

      boolean var9;
      if (var2 && var1) {
         var9 = true;
      } else {
         var9 = false;
      }

      String var11;
      if (var9) {
         var11 = "\n\nReplace merges the folders: files with the same name are overwritten, other files stay.";
      } else {
         var11 = "";
      }

      var11 = var10 + " named \"" + var3 + "\" already exists in " + var4 + ":" + var5 + "." + var11;
      LinearLayout var16 = new LinearLayout((Context)var0);
      var16.setOrientation(1);
      var16.setPadding(var0.dp(20), var0.dp(8), var0.dp(20), 0);
      var16.addView((View)var12);
      String[] var17 = new String[4];
      if (var9) {
         var4 = "Replace (merge)";
      } else {
         var4 = "Replace";
      }

      var17[0] = var4;
      var17[1] = "Keep both";
      var17[2] = "Skip";
      var17[3] = "Cancel copy";
      AlertDialog var14 = new android.app.AlertDialog.Builder((Context)var0)
         .setTitle("\"" + var3 + "\" already exists")
         .setMessage(var11)
         .setView((View)var16)
         .setCancelable(false)
         .setPositiveButton(var17[0], new FilesActivity$$ExternalSyntheticLambda0(var6, var12, var7, var8))
         .setNeutralButton(var17[1], new FilesActivity$$ExternalSyntheticLambda11(var6, var12, var7, var8))
         .setNegativeButton(var17[2], new FilesActivity$$ExternalSyntheticLambda22(var6, var12, var7, var8))
         .show();
      Button var13 = new Button((Context)var0);
      var13.setText(var17[3]);
      var13.setOnClickListener(new FilesActivity$$ExternalSyntheticLambda29(var6, var14, var8));
      var16.addView((View)var13);
   }

   private static final void askClash$lambda$60$lambda$54(ObjectRef var0, CheckBox var1, Function1 var2, CountDownLatch var3, DialogInterface var4, int var5) {
      var0.element = (T)FilesActivity.Clash.REPLACE;
      if (var1.isChecked()) {
         var2.invoke(var0.element);
      }

      var3.countDown();
   }

   private static final void askClash$lambda$60$lambda$55(ObjectRef var0, CheckBox var1, Function1 var2, CountDownLatch var3, DialogInterface var4, int var5) {
      var0.element = (T)FilesActivity.Clash.KEEP_BOTH;
      if (var1.isChecked()) {
         var2.invoke(var0.element);
      }

      var3.countDown();
   }

   private static final void askClash$lambda$60$lambda$56(ObjectRef var0, CheckBox var1, Function1 var2, CountDownLatch var3, DialogInterface var4, int var5) {
      var0.element = (T)FilesActivity.Clash.SKIP;
      if (var1.isChecked()) {
         var2.invoke(var0.element);
      }

      var3.countDown();
   }

   private static final void askClash$lambda$60$lambda$59$lambda$58$lambda$57(ObjectRef var0, AlertDialog var1, CountDownLatch var2, View var3) {
      var0.element = (T)FilesActivity.Clash.CANCEL;
      var1.dismiss();
      var2.countDown();
   }

   private final void askCopy() {
      if (!this.busy) {
         FilesActivity.Pane var6 = this.comma;
         FilesActivity.Pane var4 = null;
         Object var5 = null;
         FilesActivity.Pane var3 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("comma");
            var3 = null;
         }

         int var1 = var3.getSelected().size();
         var6 = this.tablet;
         var3 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
            var3 = null;
         }

         int var2 = var3.getSelected().size();
         if (var1 > 0 && var2 == 0) {
            var4 = this.comma;
            var3 = var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("comma");
               var3 = null;
            }

            var4 = this.tablet;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tablet");
               var4 = (FilesActivity.Pane)var5;
            }

            this.confirmCopy(var3, var4);
         } else if (var2 > 0 && var1 == 0) {
            var5 = this.tablet;
            var3 = (FilesActivity.Pane)var5;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tablet");
               var3 = null;
            }

            var5 = this.comma;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("comma");
            } else {
               var4 = (FilesActivity.Pane)var5;
            }

            this.confirmCopy(var3, var4);
         } else if (var1 > 0 && var2 > 0) {
            var5 = new android.app.AlertDialog.Builder((Context)this).setTitle("Copy which selection?");
            var4 = this.tablet;
            var3 = var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tablet");
               var3 = null;
            }

            String var9 = var3.getPath();
            String var22 = var1 + " item(s) from comma → tablet:" + var9;
            var4 = this.comma;
            var3 = var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("comma");
               var3 = null;
            }

            String var11 = var3.getPath();
            var5.setItems(new String[]{var22, var2 + " item(s) from tablet → comma:" + var11}, new FilesActivity$$ExternalSyntheticLambda26(this))
               .setNegativeButton("Cancel", null)
               .show();
         } else {
            this.toast("Select files or folders first");
         }
      }
   }

   private static final void askCopy$lambda$31(FilesActivity var0, DialogInterface var1, int var2) {
      FilesActivity.Pane var3 = null;
      FilesActivity.Pane var5;
      if (var2 == 0) {
         FilesActivity.Pane var4 = var0.comma;
         var5 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("comma");
            var5 = null;
         }

         var4 = var0.tablet;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
         } else {
            var3 = var4;
         }
      } else {
         FilesActivity.Pane var7 = var0.tablet;
         var5 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
            var5 = null;
         }

         var7 = var0.comma;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("comma");
         } else {
            var3 = var7;
         }
      }

      var0.confirmCopy(var5, var3);
   }

   private final void buildQueue(List<FilesActivity.Job> var1) {
      LinearLayout var4 = this.queueList;
      LinearLayout var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("queueList");
         var3 = null;
      }

      var3.removeAllViews();
      this.queueRows.clear();
      Iterator var5 = var1.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         FilesActivity.Job var6 = (FilesActivity.Job)var5.next();
         if (var2 >= 300) {
            var3 = this.queueList;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("queueList");
               var3 = null;
            }

            TextView var23 = new TextView((Context)this);
            var2 = var1.size();
            var23.setText("… and " + (var2 - 300) + " more files");
            var23.setTextColor(this.dim);
            var23.setTextSize(12.0F);
            var23.setPadding(this.dp(6), this.dp(4), 0, this.dp(4));
            var3.addView((View)var23);
            break;
         }

         FileKinds var18 = FileKinds.INSTANCE;
         String var11 = new File(var6.getSrcPath()).getName();
         Intrinsics.checkNotNullExpressionValue(var11, "getName(...)");
         FileKinds.Kind var19 = var18.of(var11, false);
         LinearLayout var7 = new LinearLayout((Context)this);
         var7.setOrientation(0);
         var7.setGravity(16);
         var7.setPadding(this.dp(6), this.dp(4), this.dp(6), this.dp(4));
         View var12 = new View((Context)this);
         var12.setBackground(SvIcon.drawable$default(SvIcon.INSTANCE, var19.getIcon(), var19.getColor(), 0, 4, null));
         LayoutParams var20 = new LayoutParams(this.dp(18), this.dp(18));
         var20.rightMargin = this.dp(8);
         Unit var8 = Unit.INSTANCE;
         var7.addView(var12, (android.view.ViewGroup.LayoutParams)var20);
         TextView var13 = new TextView((Context)this);
         var13.setText(new File(var6.getSrcPath()).getName());
         var13.setTextColor(-1);
         var13.setTextSize(13.0F);
         var13.setMaxLines(1);
         var13.setEllipsize(TruncateAt.MIDDLE);
         var7.addView((View)var13, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
         ProgressBar var24 = new ProgressBar((Context)this, null, 16842872);
         var24.setMax(1000);
         var24.setProgress(0);
         View var21 = (View)var24;
         LayoutParams var14 = new LayoutParams(this.dp(140), -2);
         var14.leftMargin = this.dp(10);
         var14.rightMargin = this.dp(10);
         Unit var9 = Unit.INSTANCE;
         var7.addView(var21, (android.view.ViewGroup.LayoutParams)var14);
         TextView var25 = new TextView((Context)this);
         String var15 = this.fmtSize(var6.getSize());
         var25.setText("waiting · " + var15);
         var25.setTextColor(this.dim);
         var25.setTextSize(12.0F);
         var25.setMinWidth(this.dp(120));
         var7.addView((View)var25);
         var4 = this.queueList;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("queueList");
            var3 = null;
         }

         var3.addView((View)var7);
         this.queueRows.add(new FilesActivity.QRow(this, var6, var7, var25, var24));
      }
   }

   private final void confirmCopy(FilesActivity.Pane var1, FilesActivity.Pane var2) {
      List var8 = CollectionsKt.toList(var1.getSelected());
      boolean var4 = var1.getRemote();
      String var6 = "comma";
      String var5;
      if (var4) {
         var5 = "comma";
      } else {
         var5 = "tablet";
      }

      if (!var2.getRemote()) {
         var6 = "tablet";
      }

      String var9 = CollectionsKt.joinToString$default(CollectionsKt.take(var8, 8), "\n", null, null, 0, null, null, 62, null);
      String var7;
      if (var8.size() > 8) {
         int var3 = var8.size();
         var7 = "\n… and " + (var3 - 8) + " more";
      } else {
         var7 = "";
      }

      var7 = var9 + var7;
      android.app.AlertDialog.Builder var14 = new android.app.AlertDialog.Builder((Context)this);
      int var11 = var8.size();
      android.app.AlertDialog.Builder var15 = var14.setTitle("Copy " + var11 + " item(s) " + var5 + " → " + var6 + "?");
      String var13 = var1.getPath();
      String var10 = var2.getPath();
      var15.setMessage("From:  " + var5 + ":" + var13 + "\nTo:      " + var6 + ":" + var10 + "\n\n" + var7)
         .setPositiveButton("Copy", new FilesActivity$$ExternalSyntheticLambda24(this, var1, var2))
         .setNegativeButton("Cancel", null)
         .show();
   }

   private static final void confirmCopy$lambda$32(FilesActivity var0, FilesActivity.Pane var1, FilesActivity.Pane var2, DialogInterface var3, int var4) {
      var0.startCopy(var1, var2);
   }

   private final void download(String param1, FilesActivity.Job param2, Function1<? super Long, Unit> param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: new java/io/File
      // 003: dup
      // 004: aload 2
      // 005: invokevirtual com/starpilot/starview/FilesActivity$Job.getDstPath ()Ljava/lang/String;
      // 008: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 00b: astore 11
      // 00d: aload 11
      // 00f: invokevirtual java/io/File.getParentFile ()Ljava/io/File;
      // 012: astore 9
      // 014: aload 9
      // 016: ifnull 01f
      // 019: aload 9
      // 01b: invokevirtual java/io/File.mkdirs ()Z
      // 01e: pop
      // 01f: aload 11
      // 021: invokevirtual java/io/File.getParentFile ()Ljava/io/File;
      // 024: astore 9
      // 026: aload 11
      // 028: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 02b: astore 10
      // 02d: new java/io/File
      // 030: dup
      // 031: aload 9
      // 033: new java/lang/StringBuilder
      // 036: dup
      // 037: invokespecial java/lang/StringBuilder.<init> ()V
      // 03a: ldc_w "."
      // 03d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 040: aload 10
      // 042: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 045: ldc_w ".part"
      // 048: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 04b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 04e: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 051: astore 13
      // 053: aload 0
      // 054: getfield com/starpilot/starview/FilesActivity.http Lokhttp3/OkHttpClient;
      // 057: astore 12
      // 059: new okhttp3/Request$Builder
      // 05c: dup
      // 05d: invokespecial okhttp3/Request$Builder.<init> ()V
      // 060: astore 10
      // 062: aload 2
      // 063: invokevirtual com/starpilot/starview/FilesActivity$Job.getSrcPath ()Ljava/lang/String;
      // 066: invokestatic android/net/Uri.encode (Ljava/lang/String;)Ljava/lang/String;
      // 069: astore 9
      // 06b: aload 12
      // 06d: aload 10
      // 06f: new java/lang/StringBuilder
      // 072: dup
      // 073: invokespecial java/lang/StringBuilder.<init> ()V
      // 076: ldc_w "http://"
      // 079: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 07c: aload 1
      // 07d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 080: ldc_w ":8090/fs/get?path="
      // 083: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 086: aload 9
      // 088: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 08b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 08e: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 091: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 094: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 097: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 09c: checkcast java/io/Closeable
      // 09f: astore 10
      // 0a1: aload 10
      // 0a3: checkcast okhttp3/Response
      // 0a6: astore 9
      // 0a8: aload 9
      // 0aa: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 0ad: ifne 133
      // 0b0: aload 9
      // 0b2: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0b5: astore 1
      // 0b6: aload 1
      // 0b7: ifnull 109
      // 0ba: aload 1
      // 0bb: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 0be: astore 1
      // 0bf: aload 1
      // 0c0: ifnull 109
      // 0c3: aload 1
      // 0c4: checkcast java/lang/CharSequence
      // 0c7: astore 1
      // 0c8: aload 1
      // 0c9: invokeinterface java/lang/CharSequence.length ()I 1
      // 0ce: ifne 0d7
      // 0d1: bipush 1
      // 0d2: istore 4
      // 0d4: goto 0da
      // 0d7: bipush 0
      // 0d8: istore 4
      // 0da: iload 4
      // 0dc: ifeq 0fe
      // 0df: aload 9
      // 0e1: invokevirtual okhttp3/Response.code ()I
      // 0e4: istore 4
      // 0e6: new java/lang/StringBuilder
      // 0e9: astore 1
      // 0ea: aload 1
      // 0eb: invokespecial java/lang/StringBuilder.<init> ()V
      // 0ee: aload 1
      // 0ef: ldc_w "HTTP "
      // 0f2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0f5: iload 4
      // 0f7: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 0fa: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0fd: astore 1
      // 0fe: aload 1
      // 0ff: checkcast java/lang/String
      // 102: astore 2
      // 103: aload 2
      // 104: astore 1
      // 105: aload 2
      // 106: ifnonnull 128
      // 109: aload 9
      // 10b: invokevirtual okhttp3/Response.code ()I
      // 10e: istore 4
      // 110: new java/lang/StringBuilder
      // 113: astore 1
      // 114: aload 1
      // 115: invokespecial java/lang/StringBuilder.<init> ()V
      // 118: aload 1
      // 119: ldc_w "HTTP "
      // 11c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 11f: iload 4
      // 121: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 124: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 127: astore 1
      // 128: new java/lang/RuntimeException
      // 12b: astore 2
      // 12c: aload 2
      // 12d: aload 1
      // 12e: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // 131: aload 2
      // 132: athrow
      // 133: aload 9
      // 135: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 138: astore 1
      // 139: aload 1
      // 13a: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 13d: aload 1
      // 13e: invokevirtual okhttp3/ResponseBody.byteStream ()Ljava/io/InputStream;
      // 141: astore 1
      // 142: new java/io/FileOutputStream
      // 145: astore 9
      // 147: aload 9
      // 149: aload 13
      // 14b: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 14e: aload 9
      // 150: checkcast java/io/Closeable
      // 153: astore 12
      // 155: aload 1
      // 156: astore 9
      // 158: aload 12
      // 15a: checkcast java/io/FileOutputStream
      // 15d: astore 15
      // 15f: aload 1
      // 160: astore 9
      // 162: ldc_w 262144
      // 165: newarray 8
      // 167: astore 14
      // 169: lconst_0
      // 16a: lstore 7
      // 16c: lconst_0
      // 16d: lstore 5
      // 16f: aload 1
      // 170: astore 9
      // 172: aload 1
      // 173: aload 14
      // 175: invokevirtual java/io/InputStream.read ([B)I
      // 178: istore 4
      // 17a: iload 4
      // 17c: ifle 1dd
      // 17f: aload 1
      // 180: astore 9
      // 182: aload 0
      // 183: getfield com/starpilot/starview/FilesActivity.cancel Ljava/util/concurrent/atomic/AtomicBoolean;
      // 186: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 189: ifne 1d0
      // 18c: aload 1
      // 18d: astore 9
      // 18f: aload 0
      // 190: invokespecial com/starpilot/starview/FilesActivity.waitIfPaused ()V
      // 193: aload 1
      // 194: astore 9
      // 196: aload 15
      // 198: aload 14
      // 19a: bipush 0
      // 19b: iload 4
      // 19d: invokevirtual java/io/FileOutputStream.write ([BII)V
      // 1a0: lload 7
      // 1a2: iload 4
      // 1a4: i2l
      // 1a5: ladd
      // 1a6: lstore 7
      // 1a8: invokestatic java/lang/System.nanoTime ()J
      // 1ab: lload 5
      // 1ad: lsub
      // 1ae: ldc2_w 100000000
      // 1b1: lcmp
      // 1b2: ifle 1c9
      // 1b5: invokestatic java/lang/System.nanoTime ()J
      // 1b8: lstore 5
      // 1ba: aload 3
      // 1bb: lload 7
      // 1bd: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 1c0: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 1c5: pop
      // 1c6: goto 1c9
      // 1c9: goto 16f
      // 1cc: astore 1
      // 1cd: goto 24a
      // 1d0: new java/lang/InterruptedException
      // 1d3: astore 1
      // 1d4: aload 1
      // 1d5: ldc_w "cancelled"
      // 1d8: invokespecial java/lang/InterruptedException.<init> (Ljava/lang/String;)V
      // 1db: aload 1
      // 1dc: athrow
      // 1dd: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1e0: astore 1
      // 1e1: aload 12
      // 1e3: aconst_null
      // 1e4: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1e7: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1ea: astore 1
      // 1eb: aload 10
      // 1ed: aconst_null
      // 1ee: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 1f1: aload 13
      // 1f3: aload 11
      // 1f5: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 1f8: ifne 230
      // 1fb: aload 11
      // 1fd: invokevirtual java/io/File.delete ()Z
      // 200: pop
      // 201: aload 13
      // 203: aload 11
      // 205: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 208: ifeq 20e
      // 20b: goto 230
      // 20e: aload 11
      // 210: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 213: astore 1
      // 214: new java/lang/RuntimeException
      // 217: dup
      // 218: new java/lang/StringBuilder
      // 21b: dup
      // 21c: invokespecial java/lang/StringBuilder.<init> ()V
      // 21f: ldc_w "rename failed: "
      // 222: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 225: aload 1
      // 226: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 229: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 22c: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // 22f: athrow
      // 230: aload 2
      // 231: invokevirtual com/starpilot/starview/FilesActivity$Job.getMtime ()J
      // 234: lconst_0
      // 235: lcmp
      // 236: ifle 248
      // 239: aload 11
      // 23b: aload 2
      // 23c: invokevirtual com/starpilot/starview/FilesActivity$Job.getMtime ()J
      // 23f: sipush 1000
      // 242: i2l
      // 243: lmul
      // 244: invokevirtual java/io/File.setLastModified (J)Z
      // 247: pop
      // 248: return
      // 249: astore 1
      // 24a: goto 24e
      // 24d: astore 1
      // 24e: aload 1
      // 24f: athrow
      // 250: astore 2
      // 251: aload 12
      // 253: aload 1
      // 254: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 257: aload 2
      // 258: athrow
      // 259: astore 1
      // 25a: goto 25e
      // 25d: astore 1
      // 25e: aload 1
      // 25f: athrow
      // 260: astore 2
      // 261: aload 10
      // 263: aload 1
      // 264: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 267: aload 2
      // 268: athrow
   }

   private final int dp(int var1) {
      return (int)(var1 * this.d);
   }

   private final String fmtDate(long var1) {
      String var3;
      if (var1 <= 0L) {
         var3 = "";
      } else {
         var3 = this.dateFmt.format(new Date(1000 * var1));
         Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      }

      return var3;
   }

   private final String fmtSize(long var1) {
      String var5;
      if (var1 < 1024L) {
         var5 = var1 + " B";
      } else {
         if (var1 < 1048576L) {
            var5 = String.format("%.1f KB", Arrays.copyOf(new Object[]{var1 / 1024.0}, 1));
         } else {
            double var3 = var1;
            if (var1 < 1073741824L) {
               var5 = String.format("%.1f MB", Arrays.copyOf(new Object[]{var3 / 1048576.0}, 1));
            } else {
               var5 = String.format("%.2f GB", Arrays.copyOf(new Object[]{var3 / 1.0737418E9F}, 1));
            }
         }

         Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
      }

      return var5;
   }

   private final String freeName(String var1, boolean var2, Set<String> var3) {
      int var8;
      label41: {
         byte var5 = -1;
         if (!var2) {
            Integer var6 = StringsKt.lastIndexOf$default(var1, '.', 0, false, 6, null);
            boolean var4;
            if (var6.intValue() > 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (!var4) {
               var6 = null;
            }

            if (var6 != null) {
               var8 = var6;
               break label41;
            }
         }

         var8 = var5;
      }

      String var10;
      if (var8 > 0) {
         var10 = var1.substring(0, var8);
         Intrinsics.checkNotNullExpressionValue(var10, "substring(...)");
      } else {
         var10 = var1;
      }

      if (var8 > 0) {
         var1 = var1.substring(var8);
         Intrinsics.checkNotNullExpressionValue(var1, "substring(...)");
      } else {
         var1 = "";
      }

      var8 = 1;

      while (var3.contains(var10 + " (" + var8 + ")" + var1)) {
         var8++;
      }

      return var10 + " (" + var8 + ")" + var1;
   }

   private final String getHost() {
      return Link.INSTANCE.getHost();
   }

   private final JSONObject getJson(String param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield com/starpilot/starview/FilesActivity.http Lokhttp3/OkHttpClient;
      // 04: new okhttp3/Request$Builder
      // 07: dup
      // 08: invokespecial okhttp3/Request$Builder.<init> ()V
      // 0b: aload 1
      // 0c: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 0f: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 12: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 15: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 1a: checkcast java/io/Closeable
      // 1d: astore 4
      // 1f: aload 4
      // 21: checkcast okhttp3/Response
      // 24: astore 5
      // 26: aload 5
      // 28: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 2b: astore 1
      // 2c: aload 1
      // 2d: ifnull 3b
      // 30: aload 1
      // 31: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 34: astore 3
      // 35: aload 3
      // 36: astore 1
      // 37: aload 3
      // 38: ifnonnull 3f
      // 3b: ldc_w ""
      // 3e: astore 1
      // 3f: aload 5
      // 41: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 44: ifne 8d
      // 47: aload 1
      // 48: checkcast java/lang/CharSequence
      // 4b: astore 1
      // 4c: aload 1
      // 4d: invokeinterface java/lang/CharSequence.length ()I 1
      // 52: ifne 5a
      // 55: bipush 1
      // 56: istore 2
      // 57: goto 5c
      // 5a: bipush 0
      // 5b: istore 2
      // 5c: iload 2
      // 5d: ifeq 7d
      // 60: aload 5
      // 62: invokevirtual okhttp3/Response.code ()I
      // 65: istore 2
      // 66: new java/lang/StringBuilder
      // 69: astore 1
      // 6a: aload 1
      // 6b: invokespecial java/lang/StringBuilder.<init> ()V
      // 6e: aload 1
      // 6f: ldc_w "HTTP "
      // 72: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 75: iload 2
      // 76: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 79: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 7c: astore 1
      // 7d: aload 1
      // 7e: checkcast java/lang/String
      // 81: astore 1
      // 82: new java/lang/RuntimeException
      // 85: astore 3
      // 86: aload 3
      // 87: aload 1
      // 88: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // 8b: aload 3
      // 8c: athrow
      // 8d: new org/json/JSONObject
      // 90: dup
      // 91: aload 1
      // 92: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 95: astore 1
      // 96: aload 4
      // 98: aconst_null
      // 99: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 9c: aload 1
      // 9d: areturn
      // 9e: astore 3
      // 9f: aload 3
      // a0: athrow
      // a1: astore 1
      // a2: aload 4
      // a4: aload 3
      // a5: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // a8: aload 1
      // a9: athrow
   }

   private final boolean hasAllFiles() {
      boolean var1;
      if (VERSION.SDK_INT >= 30 && !Environment.isExternalStorageManager()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private final void input(String var1, String var2, String var3, Function1<? super String, Unit> var4) {
      EditText var5 = new EditText((Context)this);
      var5.setText(var3);
      var5.setHint(var2);
      var5.setInputType(1);
      var5.setPadding(this.dp(20), this.dp(16), this.dp(20), this.dp(16));
      new android.app.AlertDialog.Builder((Context)this)
         .setTitle(var1)
         .setView((View)var5)
         .setPositiveButton("OK", new FilesActivity$$ExternalSyntheticLambda8(var5, var4))
         .setNegativeButton("Cancel", null)
         .show();
   }

   // $VF: synthetic method
   static void input$default(FilesActivity var0, String var1, String var2, String var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = "";
      }

      var0.input(var1, var2, var3, var4);
   }

   private static final void input$lambda$93(EditText var0, Function1 var1, DialogInterface var2, int var3) {
      String var4 = StringsKt.trim(var0.getText().toString()).toString();
      boolean var5;
      if (var4.length() > 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5) {
         var1.invoke(var4);
      }
   }

   private final String join(String var1, String var2) {
      StringBuilder var3;
      if (StringsKt.endsWith$default(var1, "/", false, 2, null)) {
         var3 = new StringBuilder().append(var1);
      } else {
         var3 = new StringBuilder().append(var1).append("/");
      }

      return var3.append(var2).toString();
   }

   private final void markRow(int var1, FilesActivity.QState var2, long var3, String var5) {
      FilesActivity.QRow var6 = CollectionsKt.getOrNull(this.queueRows, var1);
      if (var6 != null) {
         switch (FilesActivity.WhenMappings.$EnumSwitchMapping$1[var2.ordinal()]) {
            case 1:
               TextView var12 = var6.getState();
               var5 = this.fmtSize(var6.getJob().getSize());
               var12.setText("waiting · " + var5);
               break;
            case 2:
               ProgressBar var10 = var6.getBar();
               if (var6.getJob().getSize() > 0L) {
                  var1 = (int)(1000 * var3 / var6.getJob().getSize());
               } else {
                  var1 = 0;
               }

               var10.setProgress(var1);
               TextView var15 = var6.getState();
               String var11 = this.fmtSize(var3);
               var5 = this.fmtSize(var6.getJob().getSize());
               var15.setText(var11 + " / " + var5);
               var6.getState().setTextColor(-1);
               break;
            case 3:
               var6.getBar().setProgress(1000);
               var6.getState().setText("✓ done");
               var6.getState().setTextColor(Color.rgb(111, 227, 161));
               break;
            case 4:
               TextView var7 = var6.getState();
               String var9;
               if (var5 == null) {
                  var9 = "failed";
               } else {
                  var9 = var5;
               }

               var7.setText("✕ " + var9);
               var6.getState().setTextColor(Color.rgb(255, 138, 128));
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }
      }
   }

   // $VF: synthetic method
   static void markRow$default(FilesActivity var0, int var1, FilesActivity.QState var2, long var3, String var5, int var6, Object var7) {
      if ((var6 & 4) != 0) {
         var3 = 0L;
      }

      if ((var6 & 8) != 0) {
         var5 = null;
      }

      var0.markRow(var1, var2, var3, var5);
   }

   private static final void onCreate$lambda$11$lambda$10(FilesActivity var0, View var1) {
      var0.askCopy();
   }

   private static final void onCreate$lambda$19$lambda$18(FilesActivity var0, Button var1, View var2) {
      boolean var3 = var0.paused.get() ^ true;
      var0.paused.set(var3);
      String var4;
      if (var3) {
         var4 = "Resume";
      } else {
         var4 = "Pause";
      }

      var1.setText(var4);
   }

   private static final void onCreate$lambda$21$lambda$20(FilesActivity var0, View var1) {
      var0.cancel.set(true);
      var0.paused.set(false);
   }

   private static final void onCreate$lambda$4$lambda$3(FilesActivity var0, View var1) {
      var0.requestAllFiles();
   }

   private static final void onCreate$lambda$6$lambda$5(FilesActivity var0, View var1) {
      var0.finish();
   }

   private final void op(JSONObject var1, Function0<Unit> var2) {
      this.io.execute(new FilesActivity$$ExternalSyntheticLambda21(this, var1, var2));
   }

   private static final void op$lambda$30(FilesActivity var0, JSONObject var1, Function0 var2) {
      try {
         var0.opSync(var1);
         Handler var4 = var0.ui;
         FilesActivity$$ExternalSyntheticLambda27 var3 = new FilesActivity$$ExternalSyntheticLambda27(var2);
         var4.post(var3);
      } catch (Exception var5) {
         var0.ui.post(new FilesActivity$$ExternalSyntheticLambda28(var0, var1, var5));
      }
   }

   private static final void op$lambda$30$lambda$28(Function0 var0) {
      var0.invoke();
   }

   private static final void op$lambda$30$lambda$29(FilesActivity var0, JSONObject var1, Exception var2) {
      String var3 = var1.optString("op");
      String var4 = var2.getMessage();
      var0.toast(var3 + ": " + var4);
   }

   private final JSONObject opSync(JSONObject param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: invokespecial com/starpilot/starview/FilesActivity.getHost ()Ljava/lang/String;
      // 004: astore 4
      // 006: aload 4
      // 008: ifnull 13f
      // 00b: aload 0
      // 00c: getfield com/starpilot/starview/FilesActivity.http Lokhttp3/OkHttpClient;
      // 00f: astore 3
      // 010: new okhttp3/Request$Builder
      // 013: dup
      // 014: invokespecial okhttp3/Request$Builder.<init> ()V
      // 017: new java/lang/StringBuilder
      // 01a: dup
      // 01b: invokespecial java/lang/StringBuilder.<init> ()V
      // 01e: ldc_w "http://"
      // 021: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 024: aload 4
      // 026: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 029: ldc_w ":8090/fs/op"
      // 02c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 02f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 032: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 035: astore 4
      // 037: getstatic okhttp3/RequestBody.Companion Lokhttp3/RequestBody$Companion;
      // 03a: astore 6
      // 03c: getstatic okhttp3/MediaType.Companion Lokhttp3/MediaType$Companion;
      // 03f: ldc_w "application/json"
      // 042: invokevirtual okhttp3/MediaType$Companion.get (Ljava/lang/String;)Lokhttp3/MediaType;
      // 045: astore 5
      // 047: aload 1
      // 048: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
      // 04b: astore 1
      // 04c: aload 1
      // 04d: ldc_w "toString(...)"
      // 050: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 053: aload 3
      // 054: aload 4
      // 056: aload 6
      // 058: aload 5
      // 05a: aload 1
      // 05b: invokevirtual okhttp3/RequestBody$Companion.create (Lokhttp3/MediaType;Ljava/lang/String;)Lokhttp3/RequestBody;
      // 05e: invokevirtual okhttp3/Request$Builder.post (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 061: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 064: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 067: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 06c: checkcast java/io/Closeable
      // 06f: astore 4
      // 071: aload 4
      // 073: checkcast okhttp3/Response
      // 076: astore 5
      // 078: aload 5
      // 07a: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 07d: astore 1
      // 07e: aload 1
      // 07f: ifnull 08d
      // 082: aload 1
      // 083: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 086: astore 3
      // 087: aload 3
      // 088: astore 1
      // 089: aload 3
      // 08a: ifnonnull 091
      // 08d: ldc_w ""
      // 090: astore 1
      // 091: new org/json/JSONObject
      // 094: astore 3
      // 095: aload 3
      // 096: aload 1
      // 097: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 09a: aload 3
      // 09b: astore 1
      // 09c: goto 0ed
      // 09f: astore 3
      // 0a0: new org/json/JSONObject
      // 0a3: astore 3
      // 0a4: aload 3
      // 0a5: invokespecial org/json/JSONObject.<init> ()V
      // 0a8: bipush 0
      // 0a9: istore 2
      // 0aa: aload 3
      // 0ab: ldc_w "ok"
      // 0ae: bipush 0
      // 0af: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Z)Lorg/json/JSONObject;
      // 0b2: astore 3
      // 0b3: aload 1
      // 0b4: checkcast java/lang/CharSequence
      // 0b7: astore 1
      // 0b8: aload 1
      // 0b9: invokeinterface java/lang/CharSequence.length ()I 1
      // 0be: ifne 0c3
      // 0c1: bipush 1
      // 0c2: istore 2
      // 0c3: iload 2
      // 0c4: ifeq 0e4
      // 0c7: aload 5
      // 0c9: invokevirtual okhttp3/Response.code ()I
      // 0cc: istore 2
      // 0cd: new java/lang/StringBuilder
      // 0d0: astore 1
      // 0d1: aload 1
      // 0d2: invokespecial java/lang/StringBuilder.<init> ()V
      // 0d5: aload 1
      // 0d6: ldc_w "HTTP "
      // 0d9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0dc: iload 2
      // 0dd: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 0e0: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0e3: astore 1
      // 0e4: aload 3
      // 0e5: ldc_w "error"
      // 0e8: aload 1
      // 0e9: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 0ec: astore 1
      // 0ed: aload 1
      // 0ee: ldc_w "ok"
      // 0f1: invokevirtual org/json/JSONObject.optBoolean (Ljava/lang/String;)Z
      // 0f4: ifeq 103
      // 0f7: aload 1
      // 0f8: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 0fb: aload 4
      // 0fd: aconst_null
      // 0fe: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 101: aload 1
      // 102: areturn
      // 103: new java/lang/RuntimeException
      // 106: astore 3
      // 107: aload 5
      // 109: invokevirtual okhttp3/Response.code ()I
      // 10c: istore 2
      // 10d: new java/lang/StringBuilder
      // 110: astore 5
      // 112: aload 5
      // 114: invokespecial java/lang/StringBuilder.<init> ()V
      // 117: aload 3
      // 118: aload 1
      // 119: ldc_w "error"
      // 11c: aload 5
      // 11e: ldc_w "HTTP "
      // 121: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 124: iload 2
      // 125: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 128: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 12b: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 12e: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // 131: aload 3
      // 132: athrow
      // 133: astore 1
      // 134: aload 1
      // 135: athrow
      // 136: astore 3
      // 137: aload 4
      // 139: aload 1
      // 13a: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 13d: aload 3
      // 13e: athrow
      // 13f: new java/lang/RuntimeException
      // 142: dup
      // 143: ldc_w "not connected"
      // 146: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // 149: athrow
   }

   private final void requestAllFiles() {
      if (VERSION.SDK_INT >= 30) {
         try {
            String var1 = this.getPackageName();
            StringBuilder var2 = new StringBuilder();
            Intent var3 = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION", Uri.parse(var2.append("package:").append(var1).toString()));
            this.startActivity(var3);
         } catch (Exception var4) {
            this.startActivity(new Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION"));
         }
      }
   }

   private final void saveText(FilesActivity.Pane var1, String var2, String var3, Function0<Unit> var4) {
      this.io.execute(new FilesActivity$$ExternalSyntheticLambda23(var1, this, var2, var3, var4));
   }

   private static final void saveText$lambda$83(FilesActivity.Pane param0, FilesActivity param1, String param2, String param3, Function0 param4) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: aload 0
      // 001: invokevirtual com/starpilot/starview/FilesActivity$Pane.getRemote ()Z
      // 004: ifeq 14d
      // 007: aload 1
      // 008: invokespecial com/starpilot/starview/FilesActivity.getHost ()Ljava/lang/String;
      // 00b: astore 7
      // 00d: aload 7
      // 00f: ifnull 140
      // 012: aload 1
      // 013: getfield com/starpilot/starview/FilesActivity.http Lokhttp3/OkHttpClient;
      // 016: astore 6
      // 018: new okhttp3/Request$Builder
      // 01b: astore 8
      // 01d: aload 8
      // 01f: invokespecial okhttp3/Request$Builder.<init> ()V
      // 022: aload 2
      // 023: invokestatic android/net/Uri.encode (Ljava/lang/String;)Ljava/lang/String;
      // 026: astore 2
      // 027: new java/lang/StringBuilder
      // 02a: astore 9
      // 02c: aload 9
      // 02e: invokespecial java/lang/StringBuilder.<init> ()V
      // 031: aload 8
      // 033: aload 9
      // 035: ldc_w "http://"
      // 038: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 03b: aload 7
      // 03d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 040: ldc_w ":8090/fs/put?path="
      // 043: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 046: aload 2
      // 047: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 04a: ldc_w "&mtime=0"
      // 04d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 050: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 053: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 056: astore 7
      // 058: getstatic okhttp3/RequestBody.Companion Lokhttp3/RequestBody$Companion;
      // 05b: astore 8
      // 05d: getstatic okhttp3/MediaType.Companion Lokhttp3/MediaType$Companion;
      // 060: ldc_w "application/octet-stream"
      // 063: invokevirtual okhttp3/MediaType$Companion.get (Ljava/lang/String;)Lokhttp3/MediaType;
      // 066: astore 2
      // 067: aload 3
      // 068: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 06b: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 06e: astore 3
      // 06f: aload 3
      // 070: ldc_w "getBytes(...)"
      // 073: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 076: aload 6
      // 078: aload 7
      // 07a: aload 8
      // 07c: aload 2
      // 07d: aload 3
      // 07e: bipush 0
      // 07f: bipush 0
      // 080: bipush 12
      // 082: aconst_null
      // 083: invokestatic okhttp3/RequestBody$Companion.create$default (Lokhttp3/RequestBody$Companion;Lokhttp3/MediaType;[BIIILjava/lang/Object;)Lokhttp3/RequestBody;
      // 086: invokevirtual okhttp3/Request$Builder.put (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 089: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 08c: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 08f: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 094: checkcast java/io/Closeable
      // 097: astore 3
      // 098: aload 3
      // 099: checkcast okhttp3/Response
      // 09c: astore 6
      // 09e: aload 6
      // 0a0: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 0a3: ifne 129
      // 0a6: aload 6
      // 0a8: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0ab: astore 0
      // 0ac: aload 0
      // 0ad: ifnull 0ff
      // 0b0: aload 0
      // 0b1: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 0b4: astore 0
      // 0b5: aload 0
      // 0b6: ifnull 0ff
      // 0b9: aload 0
      // 0ba: checkcast java/lang/CharSequence
      // 0bd: astore 0
      // 0be: aload 0
      // 0bf: invokeinterface java/lang/CharSequence.length ()I 1
      // 0c4: ifne 0cd
      // 0c7: bipush 1
      // 0c8: istore 5
      // 0ca: goto 0d0
      // 0cd: bipush 0
      // 0ce: istore 5
      // 0d0: iload 5
      // 0d2: ifeq 0f4
      // 0d5: aload 6
      // 0d7: invokevirtual okhttp3/Response.code ()I
      // 0da: istore 5
      // 0dc: new java/lang/StringBuilder
      // 0df: astore 0
      // 0e0: aload 0
      // 0e1: invokespecial java/lang/StringBuilder.<init> ()V
      // 0e4: aload 0
      // 0e5: ldc_w "HTTP "
      // 0e8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0eb: iload 5
      // 0ed: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 0f0: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0f3: astore 0
      // 0f4: aload 0
      // 0f5: checkcast java/lang/String
      // 0f8: astore 2
      // 0f9: aload 2
      // 0fa: astore 0
      // 0fb: aload 2
      // 0fc: ifnonnull 11e
      // 0ff: aload 6
      // 101: invokevirtual okhttp3/Response.code ()I
      // 104: istore 5
      // 106: new java/lang/StringBuilder
      // 109: astore 0
      // 10a: aload 0
      // 10b: invokespecial java/lang/StringBuilder.<init> ()V
      // 10e: aload 0
      // 10f: ldc_w "HTTP "
      // 112: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 115: iload 5
      // 117: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 11a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 11d: astore 0
      // 11e: new java/lang/RuntimeException
      // 121: astore 2
      // 122: aload 2
      // 123: aload 0
      // 124: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // 127: aload 2
      // 128: athrow
      // 129: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 12c: astore 2
      // 12d: aload 3
      // 12e: aconst_null
      // 12f: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 132: goto 161
      // 135: astore 0
      // 136: aload 0
      // 137: athrow
      // 138: astore 2
      // 139: aload 3
      // 13a: aload 0
      // 13b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 13e: aload 2
      // 13f: athrow
      // 140: new java/lang/RuntimeException
      // 143: astore 0
      // 144: aload 0
      // 145: ldc_w "not connected"
      // 148: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // 14b: aload 0
      // 14c: athrow
      // 14d: new java/io/File
      // 150: astore 6
      // 152: aload 6
      // 154: aload 2
      // 155: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 158: aload 6
      // 15a: aload 3
      // 15b: aconst_null
      // 15c: bipush 2
      // 15d: aconst_null
      // 15e: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 161: aload 1
      // 162: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 165: astore 2
      // 166: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda19
      // 169: astore 3
      // 16a: aload 3
      // 16b: aload 1
      // 16c: aload 4
      // 16e: aload 0
      // 16f: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda19.<init> (Lcom/starpilot/starview/FilesActivity;Lkotlin/jvm/functions/Function0;Lcom/starpilot/starview/FilesActivity$Pane;)V
      // 172: aload 2
      // 173: aload 3
      // 174: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 177: pop
      // 178: goto 18d
      // 17b: astore 0
      // 17c: aload 1
      // 17d: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 180: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda20
      // 183: dup
      // 184: aload 1
      // 185: aload 0
      // 186: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda20.<init> (Lcom/starpilot/starview/FilesActivity;Ljava/lang/Exception;)V
      // 189: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 18c: pop
      // 18d: return
   }

   private static final void saveText$lambda$83$lambda$81(FilesActivity var0, Function0 var1, FilesActivity.Pane var2) {
      var0.toast("Saved");
      var1.invoke();
      var2.refresh();
   }

   private static final void saveText$lambda$83$lambda$82(FilesActivity var0, Exception var1) {
      String var2 = var1.getMessage();
      var0.toast("save: " + var2);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final void showPreview(FilesActivity.Pane var1, FilesActivity.Entry var2, String var3, FileKinds.Kind var4, byte[] var5, boolean var6) {
      String var10;
      if (var1.getRemote()) {
         var10 = "comma";
      } else {
         var10 = "tablet";
      }

      if (var4.getPreview() == FileKinds.Preview.IMAGE) {
         Bitmap var125 = BitmapFactory.decodeByteArray(var5, 0, var5.length);
         if (var125 == null) {
            FilesActivity var126 = this;
            String var128 = var2.getName();
            var126.toast(var128 + ": not an image this tablet can read");
         } else {
            ImageView var129 = new ImageView((Context)this);
            var129.setImageBitmap(var125);
            var129.setAdjustViewBounds(true);
            var129.setBackgroundColor(-16777216);
            android.app.AlertDialog.Builder var137 = new android.app.AlertDialog.Builder((Context)this);
            String var127 = var2.getName();
            int var138 = var125.getWidth();
            int var8 = var125.getHeight();
            var137.setTitle(var127 + "  ·  " + var138 + "×" + var8 + "  ·  " + var10).setView((View)var129).setPositiveButton("Close", null).show();
         }
      } else if (!FileKinds.INSTANCE.showable(var5)) {
         String var124 = var2.getName();
         this.toast(var124 + ": binary file - copy it to open it");
      } else {
         String var9;
         String var11 = new String(var5, Charsets.UTF_8);
         var9 = var11;
         label760:
         if (var4.getPreview() == FileKinds.Preview.JSON) {
            try {
               Companion var140 = Result.Companion;
            } catch (Throwable var123) {
               Companion var139 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var123));
               var9 = var11;
               break label760;
            }

            try {
               FilesActivity var142 = this;
            } catch (Throwable var122) {
               Companion var141 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var122));
               var9 = var11;
               break label760;
            }

            label768: {
               label769: {
                  try {
                     if (!StringsKt.startsWith$default(StringsKt.trimStart(var11).toString(), "[", false, 2, null)) {
                        break label769;
                     }
                  } catch (Throwable var121) {
                     Companion var143 = Result.Companion;
                     KtCompat.resultOf(ResultKt.createFailure(var121));
                     var9 = var11;
                     break label760;
                  }

                  try {
                  } catch (Throwable var120) {
                     Companion var144 = Result.Companion;
                     KtCompat.resultOf(ResultKt.createFailure(var120));
                     var9 = var11;
                     break label760;
                  }

                  try {
                     var145 = new JSONArray(var11);
                  } catch (Throwable var119) {
                     Companion var146 = Result.Companion;
                     KtCompat.resultOf(ResultKt.createFailure(var119));
                     var9 = var11;
                     break label760;
                  }

                  try {
                     var9 = var145.toString(2);
                     break label768;
                  } catch (Throwable var118) {
                     Companion var147 = Result.Companion;
                     KtCompat.resultOf(ResultKt.createFailure(var118));
                     var9 = var11;
                     break label760;
                  }
               }

               try {
               } catch (Throwable var117) {
                  Companion var148 = Result.Companion;
                  KtCompat.resultOf(ResultKt.createFailure(var117));
                  var9 = var11;
                  break label760;
               }

               try {
                  var149 = new JSONObject(var11);
               } catch (Throwable var116) {
                  Companion var150 = Result.Companion;
                  KtCompat.resultOf(ResultKt.createFailure(var116));
                  var9 = var11;
                  break label760;
               }

               try {
                  var9 = var149.toString(2);
               } catch (Throwable var115) {
                  Companion var151 = Result.Companion;
                  KtCompat.resultOf(ResultKt.createFailure(var115));
                  var9 = var11;
                  break label760;
               }
            }

            label725:
            try {
               KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable var114) {
               Companion var152 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var114));
               var9 = var9;
               break label725;
            }
         }

         boolean var7;
         if (!var6 && var5.length <= 262144) {
            var7 = true;
         } else {
            var7 = false;
         }

         EditText var154 = new EditText((Context)this);
         var154.setText(var9);
         var154.setTypeface(Typeface.MONOSPACE);
         var154.setTextSize(13.0F);
         var154.setTextColor(-1);
         var154.setBackgroundColor(Color.rgb(11, 13, 18));
         var154.setGravity(8388659);
         var154.setPadding(this.dp(14), this.dp(10), this.dp(14), this.dp(10));
         var154.setFocusable(false);
         var154.setFocusableInTouchMode(false);
         var154.setHorizontallyScrolling(true);
         var154.setInputType(655361);
         FrameLayout var153 = new FrameLayout((Context)this);
         ScrollView var131 = new ScrollView((Context)this);
         var131.addView((View)var154);
         var153.addView((View)var131, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, this.dp(560))));
         String var13 = var2.getName();
         String var12 = this.fmtSize(var2.getSize());
         String var133;
         if (var6) {
            String var132 = this.fmtSize(var5.length);
            var133 = "  ·  first " + var132;
         } else {
            var133 = "";
         }

         String var134 = var13 + "  ·  " + var12 + "  ·  " + var10 + var133;
         android.app.AlertDialog.Builder var135 = new android.app.AlertDialog.Builder((Context)this)
            .setTitle(var134)
            .setView((View)var153)
            .setNegativeButton("Close", null)
            .setNeutralButton("Copy text", new FilesActivity$$ExternalSyntheticLambda30(this, var2, var154));
         if (var7) {
            var135.setPositiveButton("Edit", null);
         }

         AlertDialog var136 = var135.show();
         if (var7) {
            var136.getButton(-1).setOnClickListener(new FilesActivity$$ExternalSyntheticLambda31(var154, this, var2, var10, var3, var1, var136));
         }
      }
   }

   private static final void showPreview$lambda$74(FilesActivity var0, FilesActivity.Entry var1, EditText var2, DialogInterface var3, int var4) {
      Object var5 = var0.getSystemService("clipboard");
      Intrinsics.checkNotNull(var5, "null cannot be cast to non-null type android.content.ClipboardManager");
      ((ClipboardManager)var5).setPrimaryClip(ClipData.newPlainText(var1.getName(), var2.getText().toString()));
      var0.toast("Copied");
   }

   private static final void showPreview$lambda$78(
      EditText var0, FilesActivity var1, FilesActivity.Entry var2, String var3, String var4, FilesActivity.Pane var5, AlertDialog var6, View var7
   ) {
      if (!var0.isFocusable()) {
         var0.setFocusable(true);
         var0.setFocusableInTouchMode(true);
         var0.requestFocus();
         Intrinsics.checkNotNull(var7, "null cannot be cast to non-null type android.widget.Button");
         ((Button)var7).setText("Save");
      } else {
         android.app.AlertDialog.Builder var9 = new android.app.AlertDialog.Builder((Context)var1);
         String var8 = var2.getName();
         var9.setTitle("Save " + var8 + " on the " + var3 + "?")
            .setMessage("The file is replaced with your edit. " + var4)
            .setPositiveButton("Save", new FilesActivity$$ExternalSyntheticLambda17(var1, var5, var4, var0, var6))
            .setNegativeButton("Cancel", null)
            .show();
      }
   }

   private static final void showPreview$lambda$78$lambda$77(
      FilesActivity var0, FilesActivity.Pane var1, String var2, EditText var3, AlertDialog var4, DialogInterface var5, int var6
   ) {
      var0.saveText(var1, var2, var3.getText().toString(), new FilesActivity$$ExternalSyntheticLambda32(var4));
   }

   private static final Unit showPreview$lambda$78$lambda$77$lambda$76(AlertDialog var0) {
      var0.dismiss();
      return Unit.INSTANCE;
   }

   private final void startCopy(FilesActivity.Pane var1, FilesActivity.Pane var2) {
      if (!this.busy) {
         String var7 = this.getHost();
         if (var7 == null) {
            this.toast("Not connected");
         } else {
            List var6 = CollectionsKt.toList(var1.getSelected());
            if (!var6.isEmpty()) {
               this.busy = true;
               this.cancel.set(false);
               this.paused.set(false);
               Button var3 = this.pauseBtn;
               Object var4 = null;
               if (var3 != null) {
                  Button var5 = this.pauseBtn;
                  var3 = var5;
                  if (var5 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("pauseBtn");
                     var3 = null;
                  }

                  var3.setText("Pause");
               }

               this.updateButtons();
               LinearLayout var12 = this.progBox;
               LinearLayout var9 = var12;
               if (var12 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("progBox");
                  var9 = null;
               }

               var9.setVisibility(0);
               TextView var13 = this.progTv;
               TextView var10 = var13;
               if (var13 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("progTv");
                  var10 = null;
               }

               var10.setText("Preparing…");
               ProgressBar var11 = this.progBar;
               if (var11 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("progBar");
                  var11 = (ProgressBar)var4;
               }

               var11.setProgress(0);
               this.io.execute(new FilesActivity$$ExternalSyntheticLambda25(var2, this, var7, var6, var1));
            }
         }
      }
   }

   private static final void startCopy$lambda$51(FilesActivity.Pane param0, FilesActivity param1, String param2, List param3, FilesActivity.Pane param4) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: ldc_w "rel"
      // 003: astore 16
      // 005: new java/util/HashMap
      // 008: astore 19
      // 00a: aload 19
      // 00c: invokespecial java/util/HashMap.<init> ()V
      // 00f: aload 0
      // 010: invokevirtual com/starpilot/starview/FilesActivity$Pane.getRemote ()Z
      // 013: istore 8
      // 015: ldc_w "http://"
      // 018: astore 21
      // 01a: iload 8
      // 01c: ifeq 0b1
      // 01f: aload 0
      // 020: invokevirtual com/starpilot/starview/FilesActivity$Pane.getPath ()Ljava/lang/String;
      // 023: invokestatic android/net/Uri.encode (Ljava/lang/String;)Ljava/lang/String;
      // 026: astore 17
      // 028: new java/lang/StringBuilder
      // 02b: astore 15
      // 02d: aload 15
      // 02f: invokespecial java/lang/StringBuilder.<init> ()V
      // 032: aload 1
      // 033: aload 15
      // 035: ldc_w "http://"
      // 038: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 03b: aload 2
      // 03c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 03f: ldc_w ":8090/fs/list?path="
      // 042: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 045: aload 17
      // 047: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 04a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 04d: invokespecial com/starpilot/starview/FilesActivity.getJson (Ljava/lang/String;)Lorg/json/JSONObject;
      // 050: ldc_w "entries"
      // 053: invokevirtual org/json/JSONObject.getJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
      // 056: astore 17
      // 058: bipush 0
      // 059: istore 5
      // 05b: aload 17
      // 05d: invokevirtual org/json/JSONArray.length ()I
      // 060: istore 6
      // 062: iload 5
      // 064: iload 6
      // 066: if_icmpge 102
      // 069: aload 17
      // 06b: iload 5
      // 06d: invokevirtual org/json/JSONArray.getJSONObject (I)Lorg/json/JSONObject;
      // 070: astore 15
      // 072: aload 19
      // 074: checkcast java/util/Map
      // 077: aload 15
      // 079: ldc_w "name"
      // 07c: invokevirtual org/json/JSONObject.getString (Ljava/lang/String;)Ljava/lang/String;
      // 07f: aload 15
      // 081: ldc_w "dir"
      // 084: invokevirtual org/json/JSONObject.getBoolean (Ljava/lang/String;)Z
      // 087: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 08a: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 08f: pop
      // 090: iinc 5 1
      // 093: goto 062
      // 096: astore 2
      // 097: aload 0
      // 098: astore 3
      // 099: aload 4
      // 09b: astore 3
      // 09c: goto 93a
      // 09f: astore 2
      // 0a0: aload 0
      // 0a1: astore 3
      // 0a2: aload 4
      // 0a4: astore 3
      // 0a5: goto 8b8
      // 0a8: astore 2
      // 0a9: aload 0
      // 0aa: astore 2
      // 0ab: aload 4
      // 0ad: astore 2
      // 0ae: goto 8fc
      // 0b1: new java/io/File
      // 0b4: astore 15
      // 0b6: aload 15
      // 0b8: aload 0
      // 0b9: invokevirtual com/starpilot/starview/FilesActivity$Pane.getPath ()Ljava/lang/String;
      // 0bc: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 0bf: aload 15
      // 0c1: invokevirtual java/io/File.listFiles ()[Ljava/io/File;
      // 0c4: astore 15
      // 0c6: aload 15
      // 0c8: ifnull 102
      // 0cb: bipush 0
      // 0cc: istore 8
      // 0ce: aload 15
      // 0d0: arraylength
      // 0d1: istore 6
      // 0d3: bipush 0
      // 0d4: istore 5
      // 0d6: iload 5
      // 0d8: iload 6
      // 0da: if_icmpge 102
      // 0dd: aload 15
      // 0df: iload 5
      // 0e1: aaload
      // 0e2: astore 17
      // 0e4: aload 19
      // 0e6: checkcast java/util/Map
      // 0e9: aload 17
      // 0eb: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 0ee: aload 17
      // 0f0: invokevirtual java/io/File.isDirectory ()Z
      // 0f3: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0f6: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0fb: pop
      // 0fc: iinc 5 1
      // 0ff: goto 0d6
      // 102: new java/util/ArrayList
      // 105: astore 20
      // 107: aload 20
      // 109: invokespecial java/util/ArrayList.<init> ()V
      // 10c: new java/util/ArrayList
      // 10f: astore 17
      // 111: aload 17
      // 113: invokespecial java/util/ArrayList.<init> ()V
      // 116: new kotlin/jvm/internal/Ref$ObjectRef
      // 119: astore 18
      // 11b: aload 18
      // 11d: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 120: aload 3
      // 121: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 126: astore 25
      // 128: aload 21
      // 12a: astore 15
      // 12c: aload 17
      // 12e: astore 3
      // 12f: aload 25
      // 131: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 136: istore 8
      // 138: iload 8
      // 13a: ifeq 560
      // 13d: aload 25
      // 13f: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 144: checkcast java/lang/String
      // 147: astore 17
      // 149: aload 4
      // 14b: invokevirtual com/starpilot/starview/FilesActivity$Pane.getEntries ()Ljava/util/List;
      // 14e: checkcast java/lang/Iterable
      // 151: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 156: astore 22
      // 158: aload 22
      // 15a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 15f: istore 8
      // 161: iload 8
      // 163: ifeq 1a1
      // 166: aload 22
      // 168: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 16d: astore 21
      // 16f: aload 21
      // 171: checkcast com/starpilot/starview/FilesActivity$Entry
      // 174: invokevirtual com/starpilot/starview/FilesActivity$Entry.getName ()Ljava/lang/String;
      // 177: aload 17
      // 179: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 17c: istore 8
      // 17e: iload 8
      // 180: ifeq 158
      // 183: goto 1a4
      // 186: astore 2
      // 187: aload 0
      // 188: astore 3
      // 189: aload 4
      // 18b: astore 3
      // 18c: goto 93a
      // 18f: astore 2
      // 190: aload 0
      // 191: astore 3
      // 192: aload 4
      // 194: astore 3
      // 195: goto 8b8
      // 198: astore 2
      // 199: aload 0
      // 19a: astore 2
      // 19b: aload 4
      // 19d: astore 2
      // 19e: goto 8fc
      // 1a1: aconst_null
      // 1a2: astore 21
      // 1a4: aload 21
      // 1a6: checkcast com/starpilot/starview/FilesActivity$Entry
      // 1a9: astore 22
      // 1ab: aload 22
      // 1ad: ifnonnull 1b3
      // 1b0: goto 12f
      // 1b3: aload 19
      // 1b5: aload 17
      // 1b7: invokevirtual java/util/HashMap.containsKey (Ljava/lang/Object;)Z
      // 1ba: istore 8
      // 1bc: iload 8
      // 1be: ifeq 308
      // 1c1: aload 18
      // 1c3: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 1c6: checkcast com/starpilot/starview/FilesActivity$Clash
      // 1c9: astore 21
      // 1cb: aload 21
      // 1cd: ifnonnull 226
      // 1d0: aload 22
      // 1d2: invokevirtual com/starpilot/starview/FilesActivity$Entry.getDir ()Z
      // 1d5: istore 9
      // 1d7: aload 19
      // 1d9: aload 17
      // 1db: invokevirtual java/util/HashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 1de: bipush 1
      // 1df: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 1e2: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 1e5: istore 8
      // 1e7: aload 0
      // 1e8: invokevirtual com/starpilot/starview/FilesActivity$Pane.getRemote ()Z
      // 1eb: istore 10
      // 1ed: iload 10
      // 1ef: ifeq 1fa
      // 1f2: ldc_w "comma"
      // 1f5: astore 21
      // 1f7: goto 1ff
      // 1fa: ldc_w "tablet"
      // 1fd: astore 21
      // 1ff: aload 0
      // 200: invokevirtual com/starpilot/starview/FilesActivity$Pane.getPath ()Ljava/lang/String;
      // 203: astore 23
      // 205: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda33
      // 208: astore 24
      // 20a: aload 24
      // 20c: aload 18
      // 20e: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda33.<init> (Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 211: aload 1
      // 212: aload 17
      // 214: iload 9
      // 216: iload 8
      // 218: aload 21
      // 21a: aload 23
      // 21c: aload 24
      // 21e: invokespecial com/starpilot/starview/FilesActivity.askClash (Ljava/lang/String;ZZLjava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Lcom/starpilot/starview/FilesActivity$Clash;
      // 221: astore 21
      // 223: goto 226
      // 226: getstatic com/starpilot/starview/FilesActivity$WhenMappings.$EnumSwitchMapping$0 [I
      // 229: aload 21
      // 22b: invokevirtual com/starpilot/starview/FilesActivity$Clash.ordinal ()I
      // 22e: iaload
      // 22f: tableswitch 29 1 4 198 195 154 36
      // 24c: new kotlin/NoWhenBranchMatchedException
      // 24f: astore 2
      // 250: goto 302
      // 253: aload 17
      // 255: astore 21
      // 257: aload 19
      // 259: aload 21
      // 25b: invokevirtual java/util/HashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 25e: aload 22
      // 260: invokevirtual com/starpilot/starview/FilesActivity$Entry.getDir ()Z
      // 263: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 266: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 269: ifne 30c
      // 26c: aload 0
      // 26d: invokevirtual com/starpilot/starview/FilesActivity$Pane.getRemote ()Z
      // 270: ifeq 2ac
      // 273: new org/json/JSONObject
      // 276: astore 23
      // 278: aload 23
      // 27a: invokespecial org/json/JSONObject.<init> ()V
      // 27d: aload 23
      // 27f: ldc_w "op"
      // 282: ldc_w "delete"
      // 285: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 288: ldc_w "path"
      // 28b: aload 1
      // 28c: aload 0
      // 28d: invokevirtual com/starpilot/starview/FilesActivity$Pane.getPath ()Ljava/lang/String;
      // 290: aload 21
      // 292: invokespecial com/starpilot/starview/FilesActivity.join (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 295: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 298: astore 21
      // 29a: aload 21
      // 29c: ldc_w "put(...)"
      // 29f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 2a2: aload 1
      // 2a3: aload 21
      // 2a5: invokespecial com/starpilot/starview/FilesActivity.opSync (Lorg/json/JSONObject;)Lorg/json/JSONObject;
      // 2a8: pop
      // 2a9: goto 2c6
      // 2ac: new java/io/File
      // 2af: astore 23
      // 2b1: aload 23
      // 2b3: aload 1
      // 2b4: aload 0
      // 2b5: invokevirtual com/starpilot/starview/FilesActivity$Pane.getPath ()Ljava/lang/String;
      // 2b8: aload 21
      // 2ba: invokespecial com/starpilot/starview/FilesActivity.join (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 2bd: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 2c0: aload 23
      // 2c2: invokestatic kotlin/io/FilesKt.deleteRecursively (Ljava/io/File;)Z
      // 2c5: pop
      // 2c6: goto 30c
      // 2c9: aload 17
      // 2cb: astore 21
      // 2cd: aload 22
      // 2cf: invokevirtual com/starpilot/starview/FilesActivity$Entry.getDir ()Z
      // 2d2: istore 8
      // 2d4: aload 19
      // 2d6: invokevirtual java/util/HashMap.keySet ()Ljava/util/Set;
      // 2d9: astore 23
      // 2db: aload 23
      // 2dd: ldc_w "<get-keys>(...)"
      // 2e0: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 2e3: aload 1
      // 2e4: aload 21
      // 2e6: iload 8
      // 2e8: aload 23
      // 2ea: invokespecial com/starpilot/starview/FilesActivity.freeName (Ljava/lang/String;ZLjava/util/Set;)Ljava/lang/String;
      // 2ed: astore 21
      // 2ef: goto 314
      // 2f2: goto 12f
      // 2f5: new java/lang/InterruptedException
      // 2f8: astore 2
      // 2f9: aload 2
      // 2fa: ldc_w "cancelled"
      // 2fd: invokespecial java/lang/InterruptedException.<init> (Ljava/lang/String;)V
      // 300: aload 2
      // 301: athrow
      // 302: aload 2
      // 303: invokespecial kotlin/NoWhenBranchMatchedException.<init> ()V
      // 306: aload 2
      // 307: athrow
      // 308: aload 17
      // 30a: astore 21
      // 30c: aload 17
      // 30e: astore 21
      // 310: aload 17
      // 312: astore 21
      // 314: aload 19
      // 316: checkcast java/util/Map
      // 319: aload 21
      // 31b: aload 22
      // 31d: invokevirtual com/starpilot/starview/FilesActivity$Entry.getDir ()Z
      // 320: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 323: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 328: pop
      // 329: aload 1
      // 32a: aload 4
      // 32c: invokevirtual com/starpilot/starview/FilesActivity$Pane.getPath ()Ljava/lang/String;
      // 32f: aload 17
      // 331: invokespecial com/starpilot/starview/FilesActivity.join (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 334: astore 23
      // 336: aload 1
      // 337: aload 0
      // 338: invokevirtual com/starpilot/starview/FilesActivity$Pane.getPath ()Ljava/lang/String;
      // 33b: aload 21
      // 33d: invokespecial com/starpilot/starview/FilesActivity.join (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 340: astore 26
      // 342: aload 22
      // 344: invokevirtual com/starpilot/starview/FilesActivity$Entry.getDir ()Z
      // 347: istore 8
      // 349: iload 8
      // 34b: ifne 371
      // 34e: new com/starpilot/starview/FilesActivity$Job
      // 351: astore 17
      // 353: aload 17
      // 355: aload 23
      // 357: aload 26
      // 359: aload 22
      // 35b: invokevirtual com/starpilot/starview/FilesActivity$Entry.getSize ()J
      // 35e: aload 22
      // 360: invokevirtual com/starpilot/starview/FilesActivity$Entry.getMtime ()J
      // 363: invokespecial com/starpilot/starview/FilesActivity$Job.<init> (Ljava/lang/String;Ljava/lang/String;JJ)V
      // 366: aload 20
      // 368: aload 17
      // 36a: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 36d: pop
      // 36e: goto 12f
      // 371: aload 3
      // 372: aload 26
      // 374: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 377: pop
      // 378: aload 4
      // 37a: invokevirtual com/starpilot/starview/FilesActivity$Pane.getRemote ()Z
      // 37d: ifeq 479
      // 380: aload 23
      // 382: invokestatic android/net/Uri.encode (Ljava/lang/String;)Ljava/lang/String;
      // 385: astore 21
      // 387: new java/lang/StringBuilder
      // 38a: astore 22
      // 38c: aload 22
      // 38e: invokespecial java/lang/StringBuilder.<init> ()V
      // 391: aload 22
      // 393: aload 15
      // 395: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 398: astore 22
      // 39a: aload 1
      // 39b: aload 22
      // 39d: aload 2
      // 39e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3a1: ldc_w ":8090/fs/walk?path="
      // 3a4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3a7: aload 21
      // 3a9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3ac: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3af: invokespecial com/starpilot/starview/FilesActivity.getJson (Ljava/lang/String;)Lorg/json/JSONObject;
      // 3b2: astore 22
      // 3b4: aload 22
      // 3b6: ldc_w "dirs"
      // 3b9: invokevirtual org/json/JSONObject.getJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
      // 3bc: astore 21
      // 3be: bipush 0
      // 3bf: istore 5
      // 3c1: aload 21
      // 3c3: invokevirtual org/json/JSONArray.length ()I
      // 3c6: istore 6
      // 3c8: iload 5
      // 3ca: iload 6
      // 3cc: if_icmpge 3f3
      // 3cf: aload 21
      // 3d1: iload 5
      // 3d3: invokevirtual org/json/JSONArray.getString (I)Ljava/lang/String;
      // 3d6: astore 24
      // 3d8: aload 24
      // 3da: ldc_w "getString(...)"
      // 3dd: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 3e0: aload 3
      // 3e1: aload 1
      // 3e2: aload 26
      // 3e4: aload 24
      // 3e6: invokespecial com/starpilot/starview/FilesActivity.join (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 3e9: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 3ec: pop
      // 3ed: iinc 5 1
      // 3f0: goto 3c8
      // 3f3: aload 22
      // 3f5: ldc_w "files"
      // 3f8: invokevirtual org/json/JSONObject.getJSONArray (Ljava/lang/String;)Lorg/json/JSONArray;
      // 3fb: astore 24
      // 3fd: bipush 0
      // 3fe: istore 5
      // 400: aload 24
      // 402: invokevirtual org/json/JSONArray.length ()I
      // 405: istore 6
      // 407: aload 21
      // 409: astore 17
      // 40b: aload 24
      // 40d: astore 21
      // 40f: iload 5
      // 411: iload 6
      // 413: if_icmpge 476
      // 416: aload 21
      // 418: iload 5
      // 41a: invokevirtual org/json/JSONArray.getJSONObject (I)Lorg/json/JSONObject;
      // 41d: astore 24
      // 41f: new com/starpilot/starview/FilesActivity$Job
      // 422: astore 27
      // 424: aload 24
      // 426: aload 16
      // 428: invokevirtual org/json/JSONObject.getString (Ljava/lang/String;)Ljava/lang/String;
      // 42b: astore 28
      // 42d: aload 28
      // 42f: ldc_w "getString(...)"
      // 432: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 435: aload 1
      // 436: aload 23
      // 438: aload 28
      // 43a: invokespecial com/starpilot/starview/FilesActivity.join (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 43d: astore 29
      // 43f: aload 24
      // 441: aload 16
      // 443: invokevirtual org/json/JSONObject.getString (Ljava/lang/String;)Ljava/lang/String;
      // 446: astore 28
      // 448: aload 28
      // 44a: ldc_w "getString(...)"
      // 44d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 450: aload 27
      // 452: aload 29
      // 454: aload 1
      // 455: aload 26
      // 457: aload 28
      // 459: invokespecial com/starpilot/starview/FilesActivity.join (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 45c: aload 24
      // 45e: ldc_w "size"
      // 461: invokevirtual org/json/JSONObject.getLong (Ljava/lang/String;)J
      // 464: lconst_0
      // 465: invokespecial com/starpilot/starview/FilesActivity$Job.<init> (Ljava/lang/String;Ljava/lang/String;JJ)V
      // 468: aload 20
      // 46a: aload 27
      // 46c: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 46f: pop
      // 470: iinc 5 1
      // 473: goto 40f
      // 476: goto 12f
      // 479: new java/io/File
      // 47c: astore 17
      // 47e: aload 17
      // 480: aload 23
      // 482: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 485: aload 17
      // 487: invokestatic kotlin/io/FilesKt.walkTopDown (Ljava/io/File;)Lkotlin/io/FileTreeWalk;
      // 48a: checkcast kotlin/sequences/Sequence
      // 48d: astore 22
      // 48f: bipush 0
      // 490: istore 8
      // 492: aload 22
      // 494: invokeinterface kotlin/sequences/Sequence.iterator ()Ljava/util/Iterator; 1
      // 499: astore 17
      // 49b: aload 23
      // 49d: astore 21
      // 49f: aload 17
      // 4a1: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 4a6: ifeq 545
      // 4a9: aload 17
      // 4ab: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 4b0: checkcast java/io/File
      // 4b3: astore 23
      // 4b5: new java/io/File
      // 4b8: astore 24
      // 4ba: aload 24
      // 4bc: aload 21
      // 4be: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 4c1: aload 23
      // 4c3: aload 24
      // 4c5: invokestatic kotlin/io/FilesKt.relativeTo (Ljava/io/File;Ljava/io/File;)Ljava/io/File;
      // 4c8: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 4cb: astore 28
      // 4cd: aload 28
      // 4cf: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 4d2: aload 28
      // 4d4: checkcast java/lang/CharSequence
      // 4d7: invokeinterface java/lang/CharSequence.length ()I 1
      // 4dc: ifne 4e5
      // 4df: bipush 1
      // 4e0: istore 5
      // 4e2: goto 4e8
      // 4e5: bipush 0
      // 4e6: istore 5
      // 4e8: iload 5
      // 4ea: ifne 542
      // 4ed: aload 23
      // 4ef: invokevirtual java/io/File.isDirectory ()Z
      // 4f2: ifeq 505
      // 4f5: aload 3
      // 4f6: aload 1
      // 4f7: aload 26
      // 4f9: aload 28
      // 4fb: invokespecial com/starpilot/starview/FilesActivity.join (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 4fe: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 501: pop
      // 502: goto 53f
      // 505: new com/starpilot/starview/FilesActivity$Job
      // 508: astore 24
      // 50a: aload 23
      // 50c: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 50f: astore 27
      // 511: aload 27
      // 513: ldc_w "getAbsolutePath(...)"
      // 516: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 519: aload 24
      // 51b: aload 27
      // 51d: aload 1
      // 51e: aload 26
      // 520: aload 28
      // 522: invokespecial com/starpilot/starview/FilesActivity.join (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      // 525: aload 23
      // 527: invokevirtual java/io/File.length ()J
      // 52a: aload 23
      // 52c: invokevirtual java/io/File.lastModified ()J
      // 52f: sipush 1000
      // 532: i2l
      // 533: ldiv
      // 534: invokespecial com/starpilot/starview/FilesActivity$Job.<init> (Ljava/lang/String;Ljava/lang/String;JJ)V
      // 537: aload 20
      // 539: aload 24
      // 53b: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 53e: pop
      // 53f: goto 542
      // 542: goto 49f
      // 545: goto 12f
      // 548: astore 2
      // 549: goto 555
      // 54c: astore 2
      // 54d: goto 559
      // 550: astore 2
      // 551: goto 55d
      // 554: astore 2
      // 555: goto 097
      // 558: astore 2
      // 559: goto 0a0
      // 55c: astore 2
      // 55d: goto 0a9
      // 560: aload 3
      // 561: astore 16
      // 563: aload 2
      // 564: astore 3
      // 565: aload 16
      // 567: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 56a: astore 3
      // 56b: aload 3
      // 56c: ldc_w "iterator(...)"
      // 56f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 572: aload 3
      // 573: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 578: istore 8
      // 57a: iload 8
      // 57c: ifeq 5ef
      // 57f: aload 3
      // 580: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 585: astore 15
      // 587: aload 15
      // 589: ldc_w "next(...)"
      // 58c: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 58f: aload 15
      // 591: checkcast java/lang/String
      // 594: astore 15
      // 596: aload 0
      // 597: invokevirtual com/starpilot/starview/FilesActivity$Pane.getRemote ()Z
      // 59a: ifeq 5ce
      // 59d: new org/json/JSONObject
      // 5a0: astore 17
      // 5a2: aload 17
      // 5a4: invokespecial org/json/JSONObject.<init> ()V
      // 5a7: aload 17
      // 5a9: ldc_w "op"
      // 5ac: ldc_w "mkdir"
      // 5af: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 5b2: ldc_w "path"
      // 5b5: aload 15
      // 5b7: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 5ba: astore 15
      // 5bc: aload 15
      // 5be: ldc_w "put(...)"
      // 5c1: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 5c4: aload 1
      // 5c5: aload 15
      // 5c7: invokespecial com/starpilot/starview/FilesActivity.opSync (Lorg/json/JSONObject;)Lorg/json/JSONObject;
      // 5ca: pop
      // 5cb: goto 572
      // 5ce: new java/io/File
      // 5d1: astore 17
      // 5d3: aload 17
      // 5d5: aload 15
      // 5d7: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 5da: aload 17
      // 5dc: invokevirtual java/io/File.mkdirs ()Z
      // 5df: pop
      // 5e0: goto 572
      // 5e3: astore 2
      // 5e4: goto 555
      // 5e7: astore 2
      // 5e8: goto 559
      // 5eb: astore 2
      // 5ec: goto 55d
      // 5ef: aload 20
      // 5f1: checkcast java/lang/Iterable
      // 5f4: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 5f9: astore 3
      // 5fa: lconst_0
      // 5fb: lstore 11
      // 5fd: aload 3
      // 5fe: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 603: istore 8
      // 605: iload 8
      // 607: ifeq 626
      // 60a: aload 3
      // 60b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 610: checkcast com/starpilot/starview/FilesActivity$Job
      // 613: astore 15
      // 615: aload 15
      // 617: invokevirtual com/starpilot/starview/FilesActivity$Job.getSize ()J
      // 61a: lstore 13
      // 61c: lload 11
      // 61e: lload 13
      // 620: ladd
      // 621: lstore 11
      // 623: goto 5fd
      // 626: new kotlin/jvm/internal/Ref$LongRef
      // 629: astore 18
      // 62b: aload 18
      // 62d: invokespecial kotlin/jvm/internal/Ref$LongRef.<init> ()V
      // 630: invokestatic java/lang/System.nanoTime ()J
      // 633: lstore 13
      // 635: new java/util/concurrent/CountDownLatch
      // 638: astore 3
      // 639: aload 3
      // 63a: bipush 1
      // 63b: invokespecial java/util/concurrent/CountDownLatch.<init> (I)V
      // 63e: aload 1
      // 63f: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 642: astore 15
      // 644: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda1
      // 647: astore 17
      // 649: aload 17
      // 64b: aload 1
      // 64c: aload 20
      // 64e: aload 3
      // 64f: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda1.<init> (Lcom/starpilot/starview/FilesActivity;Ljava/util/ArrayList;Ljava/util/concurrent/CountDownLatch;)V
      // 652: aload 15
      // 654: aload 17
      // 656: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 659: pop
      // 65a: aload 3
      // 65b: invokevirtual java/util/concurrent/CountDownLatch.await ()V
      // 65e: new kotlin/jvm/internal/Ref$IntRef
      // 661: astore 15
      // 663: aload 15
      // 665: invokespecial kotlin/jvm/internal/Ref$IntRef.<init> ()V
      // 668: aload 20
      // 66a: checkcast java/lang/Iterable
      // 66d: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 672: astore 21
      // 674: bipush 0
      // 675: istore 5
      // 677: aload 2
      // 678: astore 17
      // 67a: aload 21
      // 67c: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 681: istore 8
      // 683: iload 8
      // 685: ifeq 823
      // 688: iload 5
      // 68a: bipush 1
      // 68b: iadd
      // 68c: istore 6
      // 68e: aload 21
      // 690: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 695: checkcast com/starpilot/starview/FilesActivity$Job
      // 698: astore 17
      // 69a: aload 1
      // 69b: invokespecial com/starpilot/starview/FilesActivity.waitIfPaused ()V
      // 69e: aload 1
      // 69f: getfield com/starpilot/starview/FilesActivity.cancel Ljava/util/concurrent/atomic/AtomicBoolean;
      // 6a2: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 6a5: ifne 80a
      // 6a8: aload 20
      // 6aa: invokevirtual java/util/ArrayList.size ()I
      // 6ad: istore 7
      // 6af: new java/io/File
      // 6b2: astore 22
      // 6b4: aload 22
      // 6b6: aload 17
      // 6b8: invokevirtual com/starpilot/starview/FilesActivity$Job.getSrcPath ()Ljava/lang/String;
      // 6bb: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 6be: aload 22
      // 6c0: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 6c3: astore 23
      // 6c5: new java/lang/StringBuilder
      // 6c8: astore 22
      // 6ca: aload 22
      // 6cc: invokespecial java/lang/StringBuilder.<init> ()V
      // 6cf: aload 22
      // 6d1: iload 5
      // 6d3: bipush 1
      // 6d4: iadd
      // 6d5: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 6d8: ldc_w "/"
      // 6db: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 6de: iload 7
      // 6e0: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 6e3: ldc_w "  "
      // 6e6: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 6e9: aload 23
      // 6eb: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 6ee: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 6f1: astore 23
      // 6f3: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda2
      // 6f6: astore 22
      // 6f8: aload 22
      // 6fa: aload 18
      // 6fc: lload 13
      // 6fe: aload 1
      // 6ff: aload 23
      // 701: lload 11
      // 703: iload 5
      // 705: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda2.<init> (Lkotlin/jvm/internal/Ref$LongRef;JLcom/starpilot/starview/FilesActivity;Ljava/lang/String;JI)V
      // 708: aload 1
      // 709: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 70c: astore 23
      // 70e: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda3
      // 711: astore 24
      // 713: aload 24
      // 715: aload 1
      // 716: iload 5
      // 718: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda3.<init> (Lcom/starpilot/starview/FilesActivity;I)V
      // 71b: aload 23
      // 71d: aload 24
      // 71f: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 722: pop
      // 723: aload 4
      // 725: invokevirtual com/starpilot/starview/FilesActivity$Pane.getRemote ()Z
      // 728: istore 8
      // 72a: iload 8
      // 72c: ifeq 742
      // 72f: aload 0
      // 730: invokevirtual com/starpilot/starview/FilesActivity$Pane.getRemote ()Z
      // 733: ifne 742
      // 736: aload 1
      // 737: aload 2
      // 738: aload 17
      // 73a: aload 22
      // 73c: invokespecial com/starpilot/starview/FilesActivity.download (Ljava/lang/String;Lcom/starpilot/starview/FilesActivity$Job;Lkotlin/jvm/functions/Function1;)V
      // 73f: goto 75a
      // 742: aload 4
      // 744: invokevirtual com/starpilot/starview/FilesActivity$Pane.getRemote ()Z
      // 747: ifne 778
      // 74a: aload 0
      // 74b: invokevirtual com/starpilot/starview/FilesActivity$Pane.getRemote ()Z
      // 74e: ifeq 778
      // 751: aload 1
      // 752: aload 2
      // 753: aload 17
      // 755: aload 22
      // 757: invokespecial com/starpilot/starview/FilesActivity.upload (Ljava/lang/String;Lcom/starpilot/starview/FilesActivity$Job;Lkotlin/jvm/functions/Function1;)V
      // 75a: aload 1
      // 75b: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 75e: astore 22
      // 760: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda4
      // 763: astore 23
      // 765: aload 23
      // 767: aload 1
      // 768: iload 5
      // 76a: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda4.<init> (Lcom/starpilot/starview/FilesActivity;I)V
      // 76d: aload 22
      // 76f: aload 23
      // 771: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 774: pop
      // 775: goto 7c8
      // 778: new java/lang/RuntimeException
      // 77b: astore 22
      // 77d: aload 22
      // 77f: ldc_w "same side"
      // 782: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // 785: aload 22
      // 787: athrow
      // 788: astore 2
      // 789: goto 7f0
      // 78c: astore 22
      // 78e: aload 1
      // 78f: getfield com/starpilot/starview/FilesActivity.cancel Ljava/util/concurrent/atomic/AtomicBoolean;
      // 792: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 795: ifne 7e2
      // 798: aload 15
      // 79a: aload 15
      // 79c: getfield kotlin/jvm/internal/Ref$IntRef.element I
      // 79f: bipush 1
      // 7a0: iadd
      // 7a1: putfield kotlin/jvm/internal/Ref$IntRef.element I
      // 7a4: aload 22
      // 7a6: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 7a9: astore 24
      // 7ab: aload 1
      // 7ac: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 7af: astore 23
      // 7b1: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda5
      // 7b4: astore 22
      // 7b6: aload 22
      // 7b8: aload 1
      // 7b9: iload 5
      // 7bb: aload 24
      // 7bd: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda5.<init> (Lcom/starpilot/starview/FilesActivity;ILjava/lang/String;)V
      // 7c0: aload 23
      // 7c2: aload 22
      // 7c4: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 7c7: pop
      // 7c8: aload 18
      // 7ca: aload 18
      // 7cc: getfield kotlin/jvm/internal/Ref$LongRef.element J
      // 7cf: aload 17
      // 7d1: invokevirtual com/starpilot/starview/FilesActivity$Job.getSize ()J
      // 7d4: ladd
      // 7d5: putfield kotlin/jvm/internal/Ref$LongRef.element J
      // 7d8: aload 2
      // 7d9: astore 17
      // 7db: iload 6
      // 7dd: istore 5
      // 7df: goto 677
      // 7e2: new java/lang/InterruptedException
      // 7e5: astore 2
      // 7e6: aload 2
      // 7e7: ldc_w "cancelled"
      // 7ea: invokespecial java/lang/InterruptedException.<init> (Ljava/lang/String;)V
      // 7ed: aload 2
      // 7ee: athrow
      // 7ef: astore 2
      // 7f0: aload 2
      // 7f1: athrow
      // 7f2: astore 2
      // 7f3: goto 555
      // 7f6: astore 2
      // 7f7: goto 559
      // 7fa: astore 2
      // 7fb: goto 55d
      // 7fe: astore 2
      // 7ff: goto 555
      // 802: astore 2
      // 803: goto 559
      // 806: astore 2
      // 807: goto 55d
      // 80a: new java/lang/InterruptedException
      // 80d: astore 2
      // 80e: aload 2
      // 80f: ldc_w "cancelled"
      // 812: invokespecial java/lang/InterruptedException.<init> (Ljava/lang/String;)V
      // 815: aload 2
      // 816: athrow
      // 817: astore 2
      // 818: goto 555
      // 81b: astore 2
      // 81c: goto 559
      // 81f: astore 2
      // 820: goto 55d
      // 823: aload 20
      // 825: invokevirtual java/util/ArrayList.size ()I
      // 828: istore 5
      // 82a: aload 15
      // 82c: getfield kotlin/jvm/internal/Ref$IntRef.element I
      // 82f: istore 6
      // 831: aload 1
      // 832: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 835: astore 16
      // 837: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda6
      // 83a: astore 3
      // 83b: aload 4
      // 83d: astore 2
      // 83e: aload 3
      // 83f: aload 1
      // 840: iload 5
      // 842: iload 6
      // 844: isub
      // 845: lload 11
      // 847: aload 15
      // 849: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda6.<init> (Lcom/starpilot/starview/FilesActivity;IJLkotlin/jvm/internal/Ref$IntRef;)V
      // 84c: aload 16
      // 84e: aload 3
      // 84f: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 852: pop
      // 853: aload 1
      // 854: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 857: astore 3
      // 858: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda35
      // 85b: dup
      // 85c: aload 1
      // 85d: aload 2
      // 85e: aload 0
      // 85f: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda35.<init> (Lcom/starpilot/starview/FilesActivity;Lcom/starpilot/starview/FilesActivity$Pane;Lcom/starpilot/starview/FilesActivity$Pane;)V
      // 862: astore 1
      // 863: aload 3
      // 864: astore 0
      // 865: goto 92a
      // 868: astore 2
      // 869: aload 0
      // 86a: astore 3
      // 86b: goto 8a0
      // 86e: astore 2
      // 86f: aload 0
      // 870: astore 3
      // 871: goto 8b3
      // 874: astore 2
      // 875: aload 0
      // 876: astore 3
      // 877: goto 8f7
      // 87a: astore 2
      // 87b: aload 0
      // 87c: astore 3
      // 87d: aload 4
      // 87f: astore 3
      // 880: goto 89b
      // 883: astore 2
      // 884: aload 0
      // 885: astore 3
      // 886: aload 4
      // 888: astore 3
      // 889: goto 8ae
      // 88c: astore 2
      // 88d: aload 0
      // 88e: astore 3
      // 88f: aload 4
      // 891: astore 3
      // 892: goto 8f2
      // 895: astore 2
      // 896: aload 0
      // 897: astore 3
      // 898: aload 4
      // 89a: astore 3
      // 89b: aload 0
      // 89c: astore 3
      // 89d: aload 4
      // 89f: astore 3
      // 8a0: aload 0
      // 8a1: astore 3
      // 8a2: aload 4
      // 8a4: astore 3
      // 8a5: goto 93a
      // 8a8: astore 2
      // 8a9: aload 0
      // 8aa: astore 3
      // 8ab: aload 4
      // 8ad: astore 3
      // 8ae: aload 0
      // 8af: astore 3
      // 8b0: aload 4
      // 8b2: astore 3
      // 8b3: aload 0
      // 8b4: astore 3
      // 8b5: aload 4
      // 8b7: astore 3
      // 8b8: aload 0
      // 8b9: astore 15
      // 8bb: aload 4
      // 8bd: astore 3
      // 8be: aload 1
      // 8bf: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 8c2: astore 17
      // 8c4: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda34
      // 8c7: astore 16
      // 8c9: aload 16
      // 8cb: aload 1
      // 8cc: aload 2
      // 8cd: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda34.<init> (Lcom/starpilot/starview/FilesActivity;Ljava/lang/Exception;)V
      // 8d0: aload 17
      // 8d2: aload 16
      // 8d4: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 8d7: pop
      // 8d8: aload 1
      // 8d9: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 8dc: astore 0
      // 8dd: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda35
      // 8e0: dup
      // 8e1: aload 1
      // 8e2: aload 3
      // 8e3: aload 15
      // 8e5: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda35.<init> (Lcom/starpilot/starview/FilesActivity;Lcom/starpilot/starview/FilesActivity$Pane;Lcom/starpilot/starview/FilesActivity$Pane;)V
      // 8e8: astore 1
      // 8e9: goto 92a
      // 8ec: astore 2
      // 8ed: aload 0
      // 8ee: astore 3
      // 8ef: aload 4
      // 8f1: astore 3
      // 8f2: aload 0
      // 8f3: astore 3
      // 8f4: aload 4
      // 8f6: astore 3
      // 8f7: aload 0
      // 8f8: astore 2
      // 8f9: aload 4
      // 8fb: astore 2
      // 8fc: aload 0
      // 8fd: astore 3
      // 8fe: aload 4
      // 900: astore 2
      // 901: aload 1
      // 902: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 905: astore 15
      // 907: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda7
      // 90a: astore 16
      // 90c: aload 16
      // 90e: aload 1
      // 90f: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda7.<init> (Lcom/starpilot/starview/FilesActivity;)V
      // 912: aload 15
      // 914: aload 16
      // 916: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 919: pop
      // 91a: aload 1
      // 91b: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 91e: astore 0
      // 91f: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda35
      // 922: dup
      // 923: aload 1
      // 924: aload 2
      // 925: aload 3
      // 926: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda35.<init> (Lcom/starpilot/starview/FilesActivity;Lcom/starpilot/starview/FilesActivity$Pane;Lcom/starpilot/starview/FilesActivity$Pane;)V
      // 929: astore 1
      // 92a: aload 0
      // 92b: aload 1
      // 92c: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 92f: pop
      // 930: return
      // 931: aload 0
      // 932: astore 2
      // 933: aload 4
      // 935: astore 2
      // 936: astore 2
      // 937: goto 8a0
      // 93a: aload 1
      // 93b: getfield com/starpilot/starview/FilesActivity.ui Landroid/os/Handler;
      // 93e: new com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda35
      // 941: dup
      // 942: aload 1
      // 943: aload 4
      // 945: aload 0
      // 946: invokespecial com/starpilot/starview/FilesActivity$$ExternalSyntheticLambda35.<init> (Lcom/starpilot/starview/FilesActivity;Lcom/starpilot/starview/FilesActivity$Pane;Lcom/starpilot/starview/FilesActivity$Pane;)V
      // 949: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 94c: pop
      // 94d: aload 2
      // 94e: athrow
   }

   private static final Unit startCopy$lambda$51$lambda$36(ObjectRef var0, FilesActivity.Clash var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0.element = (T)var1;
      return Unit.INSTANCE;
   }

   private static final void startCopy$lambda$51$lambda$39(FilesActivity var0, ArrayList var1, CountDownLatch var2) {
      var0.buildQueue(var1);
      var2.countDown();
   }

   private static final Unit startCopy$lambda$51$lambda$41(LongRef var0, long var1, FilesActivity var3, String var4, long var5, int var7, long var8) {
      long var12 = var0.element + var8;
      double var10 = (System.nanoTime() - var1) / 1.0E9;
      if (var10 > 0.5) {
         var10 = var12 / var10;
      } else {
         var10 = 0.0;
      }

      var3.ui.post(new FilesActivity$$ExternalSyntheticLambda15(var3, var4, var12, var5, var10, var7, var8));
      return Unit.INSTANCE;
   }

   private static final void startCopy$lambda$51$lambda$41$lambda$40(FilesActivity var0, String var1, long var2, long var4, double var6, int var8, long var9) {
      TextView var13 = var0.progTv;
      Object var14 = null;
      TextView var12 = var13;
      if (var13 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progTv");
         var12 = null;
      }

      String var19;
      if (var0.paused.get()) {
         var19 = "PAUSED  ·  ";
      } else {
         var19 = "";
      }

      String var15 = var0.fmtSize(var2);
      String var16 = var0.fmtSize(var4);
      String var17 = var0.fmtSize((long)var6);
      var12.setText(var19 + var1 + "  ·  " + var15 + " / " + var16 + "  ·  " + var17 + "/s");
      ProgressBar var18 = var0.progBar;
      if (var18 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progBar");
         var18 = (ProgressBar)var14;
      }

      int var11;
      if (var4 > 0L) {
         var11 = (int)(1000 * var2 / var4);
      } else {
         var11 = 0;
      }

      var18.setProgress(var11);
      markRow$default(var0, var8, FilesActivity.QState.COPYING, var9, null, 8, null);
   }

   private static final void startCopy$lambda$51$lambda$42(FilesActivity var0, int var1) {
      markRow$default(var0, var1, FilesActivity.QState.COPYING, 0L, null, 8, null);
   }

   private static final void startCopy$lambda$51$lambda$43(FilesActivity var0, int var1) {
      markRow$default(var0, var1, FilesActivity.QState.DONE, 0L, null, 12, null);
   }

   private static final void startCopy$lambda$51$lambda$44(FilesActivity var0, int var1, String var2) {
      markRow$default(var0, var1, FilesActivity.QState.FAILED, 0L, var2, 4, null);
   }

   private static final void startCopy$lambda$51$lambda$45(FilesActivity var0, int var1, long var2, IntRef var4) {
      String var6 = var0.fmtSize(var2);
      String var7;
      if (var4.element > 0) {
         int var5 = var4.element;
         var7 = " - " + var5 + " failed (marked in the list)";
      } else {
         var7 = "";
      }

      var0.toast("Copied " + var1 + " file(s), " + var6 + var7);
   }

   private static final void startCopy$lambda$51$lambda$46(FilesActivity var0) {
      var0.toast("Cancelled");
   }

   private static final void startCopy$lambda$51$lambda$47(FilesActivity var0, Exception var1) {
      String var2 = var1.getMessage();
      var0.toast("Copy failed: " + var2);
   }

   private static final void startCopy$lambda$51$lambda$50(FilesActivity var0, FilesActivity.Pane var1, FilesActivity.Pane var2) {
      boolean var3 = false;
      var0.busy = false;
      var1.getSelected().clear();
      var1.render();
      var2.refresh();
      var0.updateButtons();
      TextView var11 = var0.progTv;
      TextView var9 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progTv");
         var9 = null;
      }

      var9.setText("Finished");
      Handler var12 = var0.ui;
      FilesActivity$$ExternalSyntheticLambda16 var10 = new FilesActivity$$ExternalSyntheticLambda16(var0);
      var var7 = var0.queueRows;
      if (!(var7 instanceof Collection) || !var7.isEmpty()) {
         Iterator var8 = var7.iterator();

         while (var8.hasNext()) {
            CharSequence var6 = ((FilesActivity.QRow)var8.next()).getState().getText();
            Intrinsics.checkNotNullExpressionValue(var6, "getText(...)");
            if (StringsKt.startsWith$default(var6, "✕", false, 2, null)) {
               var3 = true;
               break;
            }
         }
      }

      long var4;
      if (var3) {
         var4 = 15000L;
      } else {
         var4 = 3000L;
      }

      var12.postDelayed(var10, var4);
   }

   private static final void startCopy$lambda$51$lambda$50$lambda$48(FilesActivity var0) {
      if (!var0.busy) {
         LinearLayout var1 = var0.progBox;
         LinearLayout var2 = var1;
         if (var1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progBox");
            var2 = null;
         }

         var2.setVisibility(8);
      }
   }

   private final void toast(String var1) {
      Toast.makeText((Context)this, var1, 1).show();
   }

   private final void updateButtons() {
      if (this.copyBtn != null) {
         FilesActivity.Pane var6 = this.comma;
         Object var7 = null;
         FilesActivity.Pane var5 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("comma");
            var5 = null;
         }

         int var3 = var5.getSelected().size();
         var6 = this.tablet;
         var5 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
            var5 = null;
         }

         int var2 = var5.getSelected().size();
         Button var16 = this.copyBtn;
         Button var10 = var16;
         if (var16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyBtn");
            var10 = null;
         }

         boolean var4;
         if (this.busy || var3 <= 0 && var2 <= 0) {
            var4 = false;
         } else {
            var4 = true;
         }

         var10.setEnabled(var4);
         Button var17 = this.copyBtn;
         Button var11 = var17;
         if (var17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyBtn");
            var11 = null;
         }

         Button var8 = this.copyBtn;
         Button var18 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyBtn");
            var18 = null;
         }

         float var1;
         if (var18.isEnabled()) {
            var1 = 1.0F;
         } else {
            var1 = 0.4F;
         }

         var11.setAlpha(var1);
         Button var12 = this.copyBtn;
         Button var19 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyBtn");
            var19 = null;
         }

         String var13;
         if (var3 > 0 && var2 == 0) {
            var13 = "→";
         } else if (var2 > 0 && var3 == 0) {
            var13 = "←";
         } else {
            var13 = "⇄";
         }

         var19.setText(var13);
         TextView var20 = this.copyHint;
         if (var20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("copyHint");
            var20 = (TextView)var7;
         }

         CharSequence var14;
         if (this.busy) {
            var14 = "copying…";
         } else if (var3 > 0 && var2 == 0) {
            var14 = var3 + " selected on comma → tablet folder";
         } else if (var2 > 0 && var3 == 0) {
            var14 = var2 + " selected on tablet → comma folder";
         } else if (var3 > 0 && var2 > 0) {
            var14 = "selected on both sides — tap to choose";
         } else {
            var14 = "select files or folders on either side";
         }

         var20.setText(var14);
      }
   }

   private final void upload(String param1, FilesActivity.Job param2, Function1<? super Long, Unit> param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: new com/starpilot/starview/FilesActivity$upload$body$1
      // 03: dup
      // 04: new java/io/File
      // 07: dup
      // 08: aload 2
      // 09: invokevirtual com/starpilot/starview/FilesActivity$Job.getSrcPath ()Ljava/lang/String;
      // 0c: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 0f: aload 0
      // 10: aload 3
      // 11: invokespecial com/starpilot/starview/FilesActivity$upload$body$1.<init> (Ljava/io/File;Lcom/starpilot/starview/FilesActivity;Lkotlin/jvm/functions/Function1;)V
      // 14: astore 3
      // 15: aload 2
      // 16: invokevirtual com/starpilot/starview/FilesActivity$Job.getDstPath ()Ljava/lang/String;
      // 19: invokestatic android/net/Uri.encode (Ljava/lang/String;)Ljava/lang/String;
      // 1c: astore 7
      // 1e: aload 2
      // 1f: invokevirtual com/starpilot/starview/FilesActivity$Job.getMtime ()J
      // 22: lstore 5
      // 24: new java/lang/StringBuilder
      // 27: dup
      // 28: invokespecial java/lang/StringBuilder.<init> ()V
      // 2b: ldc_w "http://"
      // 2e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 31: aload 1
      // 32: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 35: ldc_w ":8090/fs/put?path="
      // 38: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3b: aload 7
      // 3d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 40: ldc_w "&mtime="
      // 43: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 46: lload 5
      // 48: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 4b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 4e: astore 1
      // 4f: aload 0
      // 50: getfield com/starpilot/starview/FilesActivity.http Lokhttp3/OkHttpClient;
      // 53: new okhttp3/Request$Builder
      // 56: dup
      // 57: invokespecial okhttp3/Request$Builder.<init> ()V
      // 5a: aload 1
      // 5b: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 5e: aload 3
      // 5f: checkcast okhttp3/RequestBody
      // 62: invokevirtual okhttp3/Request$Builder.put (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 65: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 68: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 6b: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 70: checkcast java/io/Closeable
      // 73: astore 3
      // 74: aload 3
      // 75: checkcast okhttp3/Response
      // 78: astore 7
      // 7a: aload 7
      // 7c: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 7f: astore 1
      // 80: aload 1
      // 81: ifnull 8f
      // 84: aload 1
      // 85: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 88: astore 2
      // 89: aload 2
      // 8a: astore 1
      // 8b: aload 2
      // 8c: ifnonnull 93
      // 8f: ldc_w ""
      // 92: astore 1
      // 93: aload 7
      // 95: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 98: ifne e6
      // 9b: aload 1
      // 9c: checkcast java/lang/CharSequence
      // 9f: astore 1
      // a0: aload 1
      // a1: invokeinterface java/lang/CharSequence.length ()I 1
      // a6: ifne af
      // a9: bipush 1
      // aa: istore 4
      // ac: goto b2
      // af: bipush 0
      // b0: istore 4
      // b2: iload 4
      // b4: ifeq d6
      // b7: aload 7
      // b9: invokevirtual okhttp3/Response.code ()I
      // bc: istore 4
      // be: new java/lang/StringBuilder
      // c1: astore 1
      // c2: aload 1
      // c3: invokespecial java/lang/StringBuilder.<init> ()V
      // c6: aload 1
      // c7: ldc_w "HTTP "
      // ca: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // cd: iload 4
      // cf: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // d2: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // d5: astore 1
      // d6: aload 1
      // d7: checkcast java/lang/String
      // da: astore 2
      // db: new java/lang/RuntimeException
      // de: astore 1
      // df: aload 1
      // e0: aload 2
      // e1: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // e4: aload 1
      // e5: athrow
      // e6: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // e9: astore 1
      // ea: aload 3
      // eb: aconst_null
      // ec: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // ef: return
      // f0: astore 2
      // f1: aload 2
      // f2: athrow
      // f3: astore 1
      // f4: aload 3
      // f5: aload 2
      // f6: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // f9: aload 1
      // fa: athrow
   }

   private final void waitIfPaused() {
      while (this.paused.get() && !this.cancel.get()) {
         Thread.sleep(150L);
      }
   }

   @Override
   protected void attachBaseContext(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "base");
      super.attachBaseContext(NarrowDensity.wrap$default(NarrowDensity.INSTANCE, var1, 0, 0.0F, 6, null));
   }

   @Override
   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Link.INSTANCE.loadKey((Context)this);
      this.d = this.getResources().getDisplayMetrics().density;
      LinearLayout var4 = new LinearLayout((Context)this);
      var4.setOrientation(1);
      var4.setBackgroundColor(Color.rgb(11, 13, 18));
      LinearLayout var3 = new LinearLayout((Context)this);
      var3.setOrientation(0);
      var3.setGravity(16);
      var3.setPadding(this.dp(20), this.dp(10), this.dp(20), this.dp(4));
      TextView var6 = new TextView((Context)this);
      var6.setText("Files");
      var6.setTextColor(-1);
      var6.setTextSize(20.0F);
      var6.setTypeface(null, 1);
      var3.addView((View)var6);
      var3.addView(new View((Context)this), (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, 1, 1.0F)));
      Button var7 = new Button((Context)this);
      var7.setText("Allow all files");
      var7.setTextSize(12.0F);
      var7.setOnClickListener(new FilesActivity$$ExternalSyntheticLambda9(this));
      this.allFilesBtn = var7;
      Button var2 = this.allFilesBtn;
      Button var8 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("allFilesBtn");
         var8 = null;
      }

      var3.addView((View)var8);
      TextView var9 = new TextView((Context)this);
      var9.setText("Done");
      var9.setTextColor(Color.rgb(111, 227, 161));
      var9.setTextSize(20.0F);
      var9.setPadding(this.dp(20), this.dp(10), this.dp(4), this.dp(10));
      var9.setOnClickListener(new FilesActivity$$ExternalSyntheticLambda10(this));
      var3.addView((View)var9);
      var4.addView((View)var3);
      var3 = new LinearLayout((Context)this);
      var3.setOrientation(0);
      this.comma = new FilesActivity.Pane((boolean)this, true, "comma");
      this.tablet = new FilesActivity.Pane((boolean)this, false, "tablet");
      FilesActivity.Pane var32 = this.comma;
      FilesActivity.Pane var10 = var32;
      if (var32 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("comma");
         var10 = null;
      }

      var3.addView(var10.build(), (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -1, 1.0F)));
      LinearLayout var5 = new LinearLayout((Context)this);
      var5.setOrientation(1);
      var5.setGravity(17);
      var5.setPadding(this.dp(4), 0, this.dp(4), 0);
      Button var11 = new Button((Context)this);
      var11.setText("⇄");
      var11.setTextSize(30.0F);
      var11.setTypeface(null, 1);
      var11.setTextColor(Color.rgb(11, 13, 18));
      var11.setStateListAnimator(null);
      GradientDrawable var33 = new GradientDrawable();
      var33.setShape(1);
      var33.setColor(Color.rgb(111, 227, 161));
      var11.setBackground((Drawable)var33);
      var11.setLayoutParams((android.view.ViewGroup.LayoutParams)(new LayoutParams(this.dp(96), this.dp(96))));
      var11.setOnClickListener(new FilesActivity$$ExternalSyntheticLambda12(this));
      this.copyBtn = var11;
      TextView var12 = new TextView((Context)this);
      var12.setText("");
      var12.setTextColor(this.dim);
      var12.setTextSize(11.0F);
      var12.setGravity(17);
      var12.setPadding(0, this.dp(6), 0, 0);
      var12.setMaxWidth(this.dp(110));
      this.copyHint = var12;
      var2 = this.copyBtn;
      Button var13 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("copyBtn");
         var13 = null;
      }

      var5.addView((View)var13);
      TextView var35 = this.copyHint;
      TextView var14 = var35;
      if (var35 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("copyHint");
         var14 = null;
      }

      var5.addView((View)var14);
      var3.addView((View)var5, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-2, -1)));
      FilesActivity.Pane var36 = this.tablet;
      FilesActivity.Pane var15 = var36;
      if (var36 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tablet");
         var15 = null;
      }

      var3.addView(var15.build(), (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -1, 1.0F)));
      var4.addView((View)var3, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, 0, 1.0F)));
      LinearLayout var16 = new LinearLayout((Context)this);
      var16.setOrientation(1);
      var16.setPadding(this.dp(16), this.dp(6), this.dp(16), this.dp(10));
      var16.setVisibility(8);
      var16.setBackgroundColor(Color.rgb(17, 20, 25));
      this.progBox = var16;
      var3 = new LinearLayout((Context)this);
      var3.setOrientation(0);
      var3.setGravity(16);
      var5 = new LinearLayout((Context)this);
      var5.setOrientation(1);
      TextView var17 = new TextView((Context)this);
      var17.setTextColor(-1);
      var17.setTextSize(13.0F);
      var17.setMaxLines(1);
      var17.setEllipsize(TruncateAt.MIDDLE);
      this.progTv = var17;
      ProgressBar var18 = new ProgressBar((Context)this, null, 16842872);
      var18.setMax(1000);
      this.progBar = var18;
      TextView var37 = this.progTv;
      TextView var19 = var37;
      if (var37 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progTv");
         var19 = null;
      }

      var5.addView((View)var19);
      ProgressBar var38 = this.progBar;
      ProgressBar var20 = var38;
      if (var38 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progBar");
         var20 = null;
      }

      var5.addView((View)var20);
      var3.addView((View)var5, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      Button var21 = new Button((Context)this);
      var21.setText("Pause");
      var21.setOnClickListener(new FilesActivity$$ExternalSyntheticLambda13(this, var21));
      this.pauseBtn = var21;
      var2 = this.pauseBtn;
      Button var22 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pauseBtn");
         var22 = null;
      }

      var3.addView((View)var22);
      Button var23 = new Button((Context)this);
      var23.setText("Cancel");
      var23.setOnClickListener(new FilesActivity$$ExternalSyntheticLambda14(this));
      this.cancelBtn = var23;
      var2 = this.cancelBtn;
      Button var24 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("cancelBtn");
         var24 = null;
      }

      var3.addView((View)var24);
      LinearLayout var41 = this.progBox;
      LinearLayout var25 = var41;
      if (var41 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progBox");
         var25 = null;
      }

      var25.addView((View)var3);
      LinearLayout var26 = new LinearLayout((Context)this);
      var26.setOrientation(1);
      this.queueList = var26;
      LinearLayout var42 = this.progBox;
      LinearLayout var27 = var42;
      if (var42 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progBox");
         var27 = null;
      }

      ScrollView var51 = new ScrollView((Context)this);
      var3 = this.queueList;
      LinearLayout var43 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("queueList");
         var43 = null;
      }

      var51.addView((View)var43);
      var27.addView((View)var51, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, this.dp(170))));
      LinearLayout var44 = this.progBox;
      LinearLayout var28 = var44;
      if (var44 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progBox");
         var28 = null;
      }

      var4.addView((View)var28);
      this.setContentView((View)var4);
      FilesActivity.Pane var45 = this.tablet;
      FilesActivity.Pane var29 = var45;
      if (var45 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tablet");
         var29 = null;
      }

      var29.setPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
      FilesActivity.Pane var46 = this.comma;
      FilesActivity.Pane var30 = var46;
      if (var46 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("comma");
         var30 = null;
      }

      var30.refresh();
      FilesActivity.Pane var31 = this.tablet;
      if (var31 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tablet");
         var31 = null;
      }

      var31.refresh();
      this.updateButtons();
   }

   @Override
   protected void onDestroy() {
      this.cancel.set(true);
      this.io.shutdownNow();
      super.onDestroy();
   }

   @Override
   protected void onResume() {
      super.onResume();
      Button var4 = this.allFilesBtn;
      Object var3 = null;
      Button var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("allFilesBtn");
         var2 = null;
      }

      byte var1;
      if (this.hasAllFiles()) {
         var1 = 8;
      } else {
         var1 = 0;
      }

      var2.setVisibility(var1);
      if (this.tablet != null) {
         FilesActivity.Pane var5 = this.tablet;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tablet");
            var5 = (FilesActivity.Pane)var3;
         }

         var5.refresh();
      }
   }

   private static enum Clash {
      CANCEL,
      KEEP_BOTH,
      REPLACE,
      SKIP;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(FilesActivity.Clash.$VALUES);
      private static final FilesActivity.Clash[] $VALUES = $values();

      // $VF: synthetic method
      private static final FilesActivity.Clash[] $values() {
         return new FilesActivity.Clash[]{REPLACE, KEEP_BOTH, SKIP, CANCEL};
      }

      public static EnumEntries<FilesActivity.Clash> getEntries() {
         return $ENTRIES;
      }
   }

   private static final class Entry {
      private final boolean dir;
      private final long mtime;
      private final String name;
      private final long size;

      public Entry(String var1, boolean var2, long var3, long var5) {
         Intrinsics.checkNotNullParameter(var1, "name");
         super();
         this.name = var1;
         this.dir = var2;
         this.size = var3;
         this.mtime = var5;
      }

      // $VF: synthetic method
      public static FilesActivity.Entry copy$default(FilesActivity.Entry var0, String var1, boolean var2, long var3, long var5, int var7, Object var8) {
         if ((var7 & 1) != 0) {
            var1 = var0.name;
         }

         if ((var7 & 2) != 0) {
            var2 = var0.dir;
         }

         if ((var7 & 4) != 0) {
            var3 = var0.size;
         }

         if ((var7 & 8) != 0) {
            var5 = var0.mtime;
         }

         return var0.copy(var1, var2, var3, var5);
      }

      public final String component1() {
         return this.name;
      }

      public final boolean component2() {
         return this.dir;
      }

      public final long component3() {
         return this.size;
      }

      public final long component4() {
         return this.mtime;
      }

      public final FilesActivity.Entry copy(String var1, boolean var2, long var3, long var5) {
         Intrinsics.checkNotNullParameter(var1, "name");
         return new FilesActivity.Entry(var1, var2, var3, var5);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof FilesActivity.Entry)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.name, var1.name)) {
               return false;
            } else if (this.dir != var1.dir) {
               return false;
            } else {
               return this.size != var1.size ? false : this.mtime == var1.mtime;
            }
         }
      }

      public final boolean getDir() {
         return this.dir;
      }

      public final long getMtime() {
         return this.mtime;
      }

      public final String getName() {
         return this.name;
      }

      public final long getSize() {
         return this.size;
      }

      @Override
      public int hashCode() {
         return ((this.name.hashCode() * 31 + Boolean.hashCode(this.dir)) * 31 + Long.hashCode(this.size)) * 31 + Long.hashCode(this.mtime);
      }

      @Override
      public String toString() {
         String var6 = this.name;
         boolean var5 = this.dir;
         long var3 = this.size;
         long var1 = this.mtime;
         return "Entry(name=" + var6 + ", dir=" + var5 + ", size=" + var3 + ", mtime=" + var1 + ")";
      }
   }

   private static final class Job {
      private final String dstPath;
      private final long mtime;
      private final long size;
      private final String srcPath;

      public Job(String var1, String var2, long var3, long var5) {
         Intrinsics.checkNotNullParameter(var1, "srcPath");
         Intrinsics.checkNotNullParameter(var2, "dstPath");
         super();
         this.srcPath = var1;
         this.dstPath = var2;
         this.size = var3;
         this.mtime = var5;
      }

      // $VF: synthetic method
      public static FilesActivity.Job copy$default(FilesActivity.Job var0, String var1, String var2, long var3, long var5, int var7, Object var8) {
         if ((var7 & 1) != 0) {
            var1 = var0.srcPath;
         }

         if ((var7 & 2) != 0) {
            var2 = var0.dstPath;
         }

         if ((var7 & 4) != 0) {
            var3 = var0.size;
         }

         if ((var7 & 8) != 0) {
            var5 = var0.mtime;
         }

         return var0.copy(var1, var2, var3, var5);
      }

      public final String component1() {
         return this.srcPath;
      }

      public final String component2() {
         return this.dstPath;
      }

      public final long component3() {
         return this.size;
      }

      public final long component4() {
         return this.mtime;
      }

      public final FilesActivity.Job copy(String var1, String var2, long var3, long var5) {
         Intrinsics.checkNotNullParameter(var1, "srcPath");
         Intrinsics.checkNotNullParameter(var2, "dstPath");
         return new FilesActivity.Job(var1, var2, var3, var5);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof FilesActivity.Job)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.srcPath, var1.srcPath)) {
               return false;
            } else if (!Intrinsics.areEqual(this.dstPath, var1.dstPath)) {
               return false;
            } else {
               return this.size != var1.size ? false : this.mtime == var1.mtime;
            }
         }
      }

      public final String getDstPath() {
         return this.dstPath;
      }

      public final long getMtime() {
         return this.mtime;
      }

      public final long getSize() {
         return this.size;
      }

      public final String getSrcPath() {
         return this.srcPath;
      }

      @Override
      public int hashCode() {
         return ((this.srcPath.hashCode() * 31 + this.dstPath.hashCode()) * 31 + Long.hashCode(this.size)) * 31 + Long.hashCode(this.mtime);
      }

      @Override
      public String toString() {
         String var6 = this.srcPath;
         String var5 = this.dstPath;
         long var3 = this.size;
         long var1 = this.mtime;
         return "Job(srcPath=" + var6 + ", dstPath=" + var5 + ", size=" + var3 + ", mtime=" + var1 + ")";
      }
   }

   private final class Pane {
      private List<FilesActivity.Entry> entries;
      public TextView info;
      private String lastError;
      public LinearLayout list;
      private String path;
      public TextView pathTv;
      private final boolean remote;
      public LinearLayout root;
      private final LinkedHashSet<String> selected;
      private boolean sortAsc;
      private final LinkedHashMap<String, TextView> sortBtns;
      private String sortKey;
      final FilesActivity this$0;
      private final String title;

      // $VF: synthetic method
      public static boolean $r8$lambda$1CJQ31BgS6Bsc6Z8aqxGNx98Z5g(FilesActivity.Pane var0, FilesActivity.Entry var1, View var2) {
         return render$lambda$41(var0, var1, var2);
      }

      // $VF: synthetic method
      public static void $r8$lambda$1bweikimENm3sr6bm3DSN87snLo(FilesActivity.Pane var0, ArrayList var1, FilesActivity var2, long var3, long var5) {
         refresh$lambda$32$lambda$30(var0, var1, var2, var3, var5);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$48JNkSPVymJzVysFFVvXzH5UCH0(FilesActivity.Pane var0) {
         return build$lambda$17(var0);
      }

      // $VF: synthetic method
      public static void $r8$lambda$4nASDcN_4nc4hDNz_fIBFxn2XwY/* $VF was: $r8$lambda$4nASDcN_4nc4hDNz_fIBFxn2XwY*/(FilesActivity.Pane var0, Exception var1) {
         refresh$lambda$32$lambda$31(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$6jQnrJpoZPkARr5SRPf6ALkvtl8(FilesActivity.Pane var0) {
         return build$lambda$18(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$CHAPsCsAwe6BOvRwRwUOgJ9d9SQ(FilesActivity.Pane var0) {
         return build$lambda$16(var0);
      }

      // $VF: synthetic method
      public static void $r8$lambda$Dd4jRFCc1gwbl4EMZqX5S1on5k0(
         FileKinds.Kind var0, FilesActivity.Pane var1, FilesActivity var2, String var3, FilesActivity.Entry var4
      ) {
         openSelected$lambda$58(var0, var1, var2, var3, var4);
      }

      // $VF: synthetic method
      public static void $r8$lambda$Nc029LQIpClDjon2oVVPkAdj6H0(FilesActivity var0, String var1, FilesActivity.Pane var2) {
         refresh$lambda$32(var0, var1, var2);
      }

      // $VF: synthetic method
      public static void $r8$lambda$OFWOFRwWWCH8Llju044B8aw6sEU(FilesActivity var0, Exception var1) {
         openSelected$lambda$58$lambda$57(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$P_G36B3FytfNQAieiG_6Mev00wM/* $VF was: $r8$lambda$P_G36B3FytfNQAieiG_6Mev00wM*/(FilesActivity.Pane var0) {
         return build$lambda$15(var0);
      }

      // $VF: synthetic method
      public static void $r8$lambda$PiP8j3cu_zYDSDd8w65dJUykob0(
         FilesActivity var0, FilesActivity.Pane var1, FilesActivity.Entry var2, String var3, FileKinds.Kind var4, byte[] var5, boolean var6
      ) {
         openSelected$lambda$58$lambda$56(var0, var1, var2, var3, var4, var5, var6);
      }

      // $VF: synthetic method
      public static void $r8$lambda$RJhDkH43Fp2XXeOgpMJ8QuqvjoI(FilesActivity.Pane var0, FilesActivity var1, List var2, DialogInterface var3, int var4) {
         deleteSelected$lambda$47(var0, var1, var2, var3, var4);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$TJmYVCvIuZpwJVtYH7q1zkyrS2s(FilesActivity.Pane var0) {
         return build$lambda$19(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$Zc0h3fYFh1IMcUhilOqMZ_1DChM(FilesActivity.Pane var0) {
         return mkdir$lambda$44$lambda$43(var0);
      }

      // $VF: synthetic method
      public static void $r8$lambda$a5Rdi5LFJMMeV6qs43bcPXKSfC0(FilesActivity.Pane var0, View var1) {
         build$lambda$21$lambda$20(var0, var1);
      }

      // $VF: synthetic method
      public static void $r8$lambda$bRLE_fZ9_rStr1UAnfpXMYjW_9o(Function0 var0, View var1) {
         build$small$lambda$12$lambda$11(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$btcBiNFFTEe9oA32cmlFjcu0DBE(FilesActivity.Pane var0, FilesActivity var1, String var2) {
         return mkdir$lambda$44(var0, var1, var2);
      }

      // $VF: synthetic method
      public static void $r8$lambda$czhrCyLirC77cl9XwGSvAK5QdaU(List var0, FilesActivity.Pane var1, FilesActivity var2, DialogInterface var3, int var4) {
         goToPathDialog$lambda$61(var0, var1, var2, var3, var4);
      }

      // $VF: synthetic method
      public static void $r8$lambda$d2x5vithcBPyFYhxWfB1t_mldO0/* $VF was: $r8$lambda$d2x5vithcBPyFYhxWfB1t_mldO0*/(
         FilesActivity.Pane var0, ObjectRef var1, FilesActivity var2
      ) {
         deleteSelected$lambda$47$lambda$46$lambda$45(var0, var1, var2);
      }

      // $VF: synthetic method
      public static void $r8$lambda$erow627nu9U7ZUwGSlTs0Y1Xez0(FilesActivity.Pane var0, String var1, View var2) {
         build$lambda$25$lambda$24(var0, var1, var2);
      }

      // $VF: synthetic method
      public static void $r8$lambda$hRmICyXxz6gJsn6Fy9Sgm3FDQ5k(List var0, FilesActivity var1, FilesActivity.Pane var2) {
         deleteSelected$lambda$47$lambda$46(var0, var1, var2);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$i0KA3NDB5WwPZL0FmE8J497AIJY(FilesActivity.Pane var0, String var1) {
         return goToPathDialog$lambda$61$lambda$60(var0, var1);
      }

      // $VF: synthetic method
      public static void $r8$lambda$pIn7SxVHok5PbIPdsjXW8NtYRCU(FilesActivity.Entry var0, FilesActivity.Pane var1, View var2) {
         render$lambda$40(var0, var1, var2);
      }

      public Pane(boolean var1, String var2, String var3) {
         Intrinsics.checkNotNullParameter(var3, "title");
         this.this$0 = var1;
         super();
         this.remote = var2;
         this.title = var3;
         String var4;
         if (this.remote) {
            var4 = "/data";
         } else {
            var4 = Environment.getExternalStorageDirectory().getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(var4, "getAbsolutePath(...)");
         }

         this.path = var4;
         this.entries = CollectionsKt.emptyList();
         this.selected = new LinkedHashSet<>();
         this.sortKey = "name";
         this.sortAsc = true;
         this.sortBtns = new LinkedHashMap<>();
      }

      private static final Unit build$lambda$15(FilesActivity.Pane var0) {
         var0.up();
         return Unit.INSTANCE;
      }

      private static final Unit build$lambda$16(FilesActivity.Pane var0) {
         var0.refresh();
         return Unit.INSTANCE;
      }

      private static final Unit build$lambda$17(FilesActivity.Pane var0) {
         var0.mkdir();
         return Unit.INSTANCE;
      }

      private static final Unit build$lambda$18(FilesActivity.Pane var0) {
         var0.openSelected();
         return Unit.INSTANCE;
      }

      private static final Unit build$lambda$19(FilesActivity.Pane var0) {
         var0.deleteSelected();
         return Unit.INSTANCE;
      }

      private static final void build$lambda$21$lambda$20(FilesActivity.Pane var0, View var1) {
         var0.goToPathDialog();
      }

      private static final void build$lambda$25$lambda$24(FilesActivity.Pane var0, String var1, View var2) {
         var0.setSort(var1);
      }

      private static final Button build$small(FilesActivity var0, String var1, Function0<Unit> var2) {
         Button var3 = new Button((Context)var0);
         var3.setText(var1);
         var3.setTextSize(13.0F);
         var3.setAllCaps(false);
         var3.setMinWidth(0);
         var3.setMinimumWidth(0);
         var3.setMinHeight(var0.dp(44));
         var3.setMinimumHeight(var0.dp(44));
         var3.setTextColor(-1);
         GradientDrawable var4 = new GradientDrawable();
         var4.setCornerRadius(var0.dp(10));
         var4.setColor(Color.rgb(30, 38, 47));
         var4.setStroke(var0.dp(1), Color.rgb(60, 66, 78));
         var3.setBackground((Drawable)var4);
         var3.setPadding(var0.dp(12), 0, var0.dp(12), 0);
         var3.setStateListAnimator(null);
         var3.setOnClickListener(new FilesActivity$Pane$$ExternalSyntheticLambda6(var2));
         LayoutParams var5 = new LayoutParams(-2, var0.dp(44));
         var5.leftMargin = var0.dp(6);
         var3.setLayoutParams((android.view.ViewGroup.LayoutParams)var5);
         return var3;
      }

      private static final void build$small$lambda$12$lambda$11(Function0 var0, View var1) {
         var0.invoke();
      }

      private static final void deleteSelected$lambda$47(FilesActivity.Pane var0, FilesActivity var1, List var2, DialogInterface var3, int var4) {
         if (var0.remote) {
            var1.io.execute(new FilesActivity$Pane$$ExternalSyntheticLambda7(var2, var1, var0));
         } else {
            for (String var6 : var2) {
               FilesKt.deleteRecursively(new File(var0.path, var6));
            }

            var0.selected.clear();
            var0.refresh();
         }
      }

      private static final void deleteSelected$lambda$47$lambda$46(List var0, FilesActivity var1, FilesActivity.Pane var2) {
         ObjectRef var3 = new ObjectRef();

         for (String var4 : var0) {
            try {
               JSONObject var5 = new JSONObject();
               JSONObject var8 = var5.put("op", "delete").put("path", var1.join(var2.path, var4));
               Intrinsics.checkNotNullExpressionValue(var8, "put(...)");
               var1.opSync(var8);
            } catch (Exception var6) {
               var3.element = (T)var6.getMessage();
            }
         }

         var1.ui.post(new FilesActivity$Pane$$ExternalSyntheticLambda13(var2, var3, var1));
      }

      private static final void deleteSelected$lambda$47$lambda$46$lambda$45(FilesActivity.Pane var0, ObjectRef var1, FilesActivity var2) {
         var0.selected.clear();
         var0.refresh();
         if (var1.element != null) {
            Object var3 = var1.element;
            var2.toast("delete: " + var3);
         }
      }

      private static final void goToPathDialog$lambda$61(List var0, FilesActivity.Pane var1, FilesActivity var2, DialogInterface var3, int var4) {
         if (var4 < var0.size()) {
            var1.path = (String)var0.get(var4);
            var1.selected.clear();
            var1.refresh();
         } else {
            var2.input("Path", "/…", var1.path, new FilesActivity$Pane$$ExternalSyntheticLambda0(var1));
         }
      }

      private static final Unit goToPathDialog$lambda$61$lambda$60(FilesActivity.Pane var0, String var1) {
         Intrinsics.checkNotNullParameter(var1, "p");
         boolean var2 = true;
         var1 = StringsKt.trimEnd(var1, new char[]{'/'});
         if (var1.length() != 0) {
            var2 = false;
         }

         if (var2) {
            var1 = "/";
         }

         var0.path = var1;
         var0.selected.clear();
         var0.refresh();
         return Unit.INSTANCE;
      }

      private static final Unit mkdir$lambda$44(FilesActivity.Pane var0, FilesActivity var1, String var2) {
         Intrinsics.checkNotNullParameter(var2, "name");
         if (var0.remote) {
            JSONObject var3 = new JSONObject().put("op", "mkdir").put("path", var1.join(var0.path, var2));
            Intrinsics.checkNotNullExpressionValue(var3, "put(...)");
            var1.op(var3, new FilesActivity$Pane$$ExternalSyntheticLambda5(var0));
         } else {
            new File(var0.path, var2).mkdirs();
            var0.refresh();
         }

         return Unit.INSTANCE;
      }

      private static final Unit mkdir$lambda$44$lambda$43(FilesActivity.Pane var0) {
         var0.refresh();
         return Unit.INSTANCE;
      }

      private static final void openSelected$lambda$58(
         FileKinds.Kind param0, FilesActivity.Pane param1, FilesActivity param2, String param3, FilesActivity.Entry param4
      ) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 000: aload 0
         // 001: invokevirtual com/starpilot/starview/FileKinds$Kind.getPreview ()Lcom/starpilot/starview/FileKinds$Preview;
         // 004: astore 10
         // 006: getstatic com/starpilot/starview/FileKinds$Preview.IMAGE Lcom/starpilot/starview/FileKinds$Preview;
         // 009: astore 9
         // 00b: aload 10
         // 00d: aload 9
         // 00f: if_acmpne 01a
         // 012: ldc2_w 12582912
         // 015: lstore 6
         // 017: goto 01f
         // 01a: ldc2_w 1048576
         // 01d: lstore 6
         // 01f: aload 1
         // 020: getfield com/starpilot/starview/FilesActivity$Pane.remote Z
         // 023: istore 8
         // 025: bipush 1
         // 026: istore 5
         // 028: iload 8
         // 02a: ifeq 18d
         // 02d: aload 2
         // 02e: invokestatic com/starpilot/starview/FilesActivity.access$getHost (Lcom/starpilot/starview/FilesActivity;)Ljava/lang/String;
         // 031: astore 10
         // 033: aload 10
         // 035: ifnull 180
         // 038: aload 2
         // 039: invokestatic com/starpilot/starview/FilesActivity.access$getHttp$p (Lcom/starpilot/starview/FilesActivity;)Lokhttp3/OkHttpClient;
         // 03c: astore 13
         // 03e: new okhttp3/Request$Builder
         // 041: astore 9
         // 043: aload 9
         // 045: invokespecial okhttp3/Request$Builder.<init> ()V
         // 048: aload 3
         // 049: invokestatic android/net/Uri.encode (Ljava/lang/String;)Ljava/lang/String;
         // 04c: astore 12
         // 04e: new java/lang/StringBuilder
         // 051: astore 11
         // 053: aload 11
         // 055: invokespecial java/lang/StringBuilder.<init> ()V
         // 058: aload 13
         // 05a: aload 9
         // 05c: aload 11
         // 05e: ldc_w "http://"
         // 061: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 064: aload 10
         // 066: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 069: ldc_w ":8090/fs/get?path="
         // 06c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 06f: aload 12
         // 071: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 074: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 077: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
         // 07a: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
         // 07d: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
         // 080: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
         // 085: checkcast java/io/Closeable
         // 088: astore 10
         // 08a: aload 10
         // 08c: checkcast okhttp3/Response
         // 08f: astore 9
         // 091: aload 9
         // 093: invokevirtual okhttp3/Response.isSuccessful ()Z
         // 096: ifne 119
         // 099: aload 9
         // 09b: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
         // 09e: astore 0
         // 09f: aload 0
         // 0a0: ifnull 0ef
         // 0a3: aload 0
         // 0a4: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
         // 0a7: astore 0
         // 0a8: aload 0
         // 0a9: ifnull 0ef
         // 0ac: aload 0
         // 0ad: checkcast java/lang/CharSequence
         // 0b0: astore 0
         // 0b1: aload 0
         // 0b2: invokeinterface java/lang/CharSequence.length ()I 1
         // 0b7: ifne 0bd
         // 0ba: goto 0c0
         // 0bd: bipush 0
         // 0be: istore 5
         // 0c0: iload 5
         // 0c2: ifeq 0e4
         // 0c5: aload 9
         // 0c7: invokevirtual okhttp3/Response.code ()I
         // 0ca: istore 5
         // 0cc: new java/lang/StringBuilder
         // 0cf: astore 0
         // 0d0: aload 0
         // 0d1: invokespecial java/lang/StringBuilder.<init> ()V
         // 0d4: aload 0
         // 0d5: ldc_w "HTTP "
         // 0d8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 0db: iload 5
         // 0dd: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
         // 0e0: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 0e3: astore 0
         // 0e4: aload 0
         // 0e5: checkcast java/lang/String
         // 0e8: astore 1
         // 0e9: aload 1
         // 0ea: astore 0
         // 0eb: aload 1
         // 0ec: ifnonnull 10e
         // 0ef: aload 9
         // 0f1: invokevirtual okhttp3/Response.code ()I
         // 0f4: istore 5
         // 0f6: new java/lang/StringBuilder
         // 0f9: astore 0
         // 0fa: aload 0
         // 0fb: invokespecial java/lang/StringBuilder.<init> ()V
         // 0fe: aload 0
         // 0ff: ldc_w "HTTP "
         // 102: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 105: iload 5
         // 107: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
         // 10a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 10d: astore 0
         // 10e: new java/lang/RuntimeException
         // 111: astore 1
         // 112: aload 1
         // 113: aload 0
         // 114: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
         // 117: aload 1
         // 118: athrow
         // 119: aload 9
         // 11b: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
         // 11e: astore 9
         // 120: aload 9
         // 122: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 125: aload 9
         // 127: invokevirtual okhttp3/ResponseBody.byteStream ()Ljava/io/InputStream;
         // 12a: astore 12
         // 12c: new java/io/ByteArrayOutputStream
         // 12f: astore 11
         // 131: aload 11
         // 133: invokespecial java/io/ByteArrayOutputStream.<init> ()V
         // 136: ldc_w 65536
         // 139: newarray 8
         // 13b: astore 9
         // 13d: aload 12
         // 13f: aload 9
         // 141: invokevirtual java/io/InputStream.read ([B)I
         // 144: istore 5
         // 146: iload 5
         // 148: ifle 164
         // 14b: aload 11
         // 14d: invokevirtual java/io/ByteArrayOutputStream.size ()I
         // 150: i2l
         // 151: lload 6
         // 153: lcmp
         // 154: ifge 164
         // 157: aload 11
         // 159: aload 9
         // 15b: bipush 0
         // 15c: iload 5
         // 15e: invokevirtual java/io/ByteArrayOutputStream.write ([BII)V
         // 161: goto 13d
         // 164: aload 11
         // 166: invokevirtual java/io/ByteArrayOutputStream.toByteArray ()[B
         // 169: astore 9
         // 16b: aload 10
         // 16d: aconst_null
         // 16e: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 171: goto 1f7
         // 174: astore 1
         // 175: aload 1
         // 176: athrow
         // 177: astore 0
         // 178: aload 10
         // 17a: aload 1
         // 17b: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 17e: aload 0
         // 17f: athrow
         // 180: new java/lang/RuntimeException
         // 183: astore 0
         // 184: aload 0
         // 185: ldc_w "not connected"
         // 188: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
         // 18b: aload 0
         // 18c: athrow
         // 18d: new java/io/FileInputStream
         // 190: astore 9
         // 192: new java/io/File
         // 195: astore 10
         // 197: aload 10
         // 199: aload 3
         // 19a: invokespecial java/io/File.<init> (Ljava/lang/String;)V
         // 19d: aload 9
         // 19f: aload 10
         // 1a1: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
         // 1a4: aload 9
         // 1a6: checkcast java/io/Closeable
         // 1a9: astore 10
         // 1ab: aload 10
         // 1ad: checkcast java/io/FileInputStream
         // 1b0: astore 11
         // 1b2: new java/io/ByteArrayOutputStream
         // 1b5: astore 12
         // 1b7: aload 12
         // 1b9: invokespecial java/io/ByteArrayOutputStream.<init> ()V
         // 1bc: ldc_w 65536
         // 1bf: newarray 8
         // 1c1: astore 9
         // 1c3: aload 11
         // 1c5: aload 9
         // 1c7: invokevirtual java/io/FileInputStream.read ([B)I
         // 1ca: istore 5
         // 1cc: iload 5
         // 1ce: ifle 1ea
         // 1d1: aload 12
         // 1d3: invokevirtual java/io/ByteArrayOutputStream.size ()I
         // 1d6: i2l
         // 1d7: lload 6
         // 1d9: lcmp
         // 1da: ifge 1ea
         // 1dd: aload 12
         // 1df: aload 9
         // 1e1: bipush 0
         // 1e2: iload 5
         // 1e4: invokevirtual java/io/ByteArrayOutputStream.write ([BII)V
         // 1e7: goto 1c3
         // 1ea: aload 12
         // 1ec: invokevirtual java/io/ByteArrayOutputStream.toByteArray ()[B
         // 1ef: astore 9
         // 1f1: aload 10
         // 1f3: aconst_null
         // 1f4: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 1f7: aload 4
         // 1f9: invokevirtual com/starpilot/starview/FilesActivity$Entry.getSize ()J
         // 1fc: aload 9
         // 1fe: arraylength
         // 1ff: i2l
         // 200: lcmp
         // 201: ifle 20a
         // 204: bipush 1
         // 205: istore 8
         // 207: goto 20d
         // 20a: bipush 0
         // 20b: istore 8
         // 20d: aload 2
         // 20e: invokestatic com/starpilot/starview/FilesActivity.access$getUi$p (Lcom/starpilot/starview/FilesActivity;)Landroid/os/Handler;
         // 211: astore 10
         // 213: new com/starpilot/starview/FilesActivity$Pane$$ExternalSyntheticLambda11
         // 216: astore 11
         // 218: aload 11
         // 21a: aload 2
         // 21b: aload 1
         // 21c: aload 4
         // 21e: aload 3
         // 21f: aload 0
         // 220: aload 9
         // 222: iload 8
         // 224: invokespecial com/starpilot/starview/FilesActivity$Pane$$ExternalSyntheticLambda11.<init> (Lcom/starpilot/starview/FilesActivity;Lcom/starpilot/starview/FilesActivity$Pane;Lcom/starpilot/starview/FilesActivity$Entry;Ljava/lang/String;Lcom/starpilot/starview/FileKinds$Kind;[BZ)V
         // 227: aload 10
         // 229: aload 11
         // 22b: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
         // 22e: pop
         // 22f: goto 258
         // 232: astore 0
         // 233: aload 0
         // 234: athrow
         // 235: astore 1
         // 236: aload 10
         // 238: aload 0
         // 239: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 23c: aload 1
         // 23d: athrow
         // 23e: astore 0
         // 23f: goto 247
         // 242: astore 0
         // 243: goto 247
         // 246: astore 0
         // 247: aload 2
         // 248: invokestatic com/starpilot/starview/FilesActivity.access$getUi$p (Lcom/starpilot/starview/FilesActivity;)Landroid/os/Handler;
         // 24b: new com/starpilot/starview/FilesActivity$Pane$$ExternalSyntheticLambda15
         // 24e: dup
         // 24f: aload 2
         // 250: aload 0
         // 251: invokespecial com/starpilot/starview/FilesActivity$Pane$$ExternalSyntheticLambda15.<init> (Lcom/starpilot/starview/FilesActivity;Ljava/lang/Exception;)V
         // 254: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
         // 257: pop
         // 258: return
      }

      private static final void openSelected$lambda$58$lambda$56(
         FilesActivity var0, FilesActivity.Pane var1, FilesActivity.Entry var2, String var3, FileKinds.Kind var4, byte[] var5, boolean var6
      ) {
         Intrinsics.checkNotNull(var5);
         var0.showPreview(var1, var2, var3, var4, var5, var6);
      }

      private static final void openSelected$lambda$58$lambda$57(FilesActivity var0, Exception var1) {
         String var2 = var1.getMessage();
         var0.toast("open: " + var2);
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      private static final void refresh$lambda$32(FilesActivity var0, String var1, FilesActivity.Pane var2) {
         label54: {
            String var9;
            StringBuilder var25;
            try {
               var9 = Uri.encode(var2.path);
               var25 = new StringBuilder();
               var25 = var25.append("http://");
            } catch (Exception var19) {
               var20 = var19;
               break label54;
            }

            try {
               var1 = var25.append(var1).append(":8090/fs/list?path=").append(var9).toString();
            } catch (Exception var18) {
               var20 = var18;
               break label54;
            }

            JSONObject var11;
            JSONArray var13;
            try {
               var11 = var0.getJson(var1);
               var13 = var11.getJSONArray("entries");
               var22 = new ArrayList();
            } catch (Exception var17) {
               var20 = var17;
               break label54;
            }

            int var3 = 0;

            int var4;
            try {
               var4 = var13.length();
            } catch (Exception var16) {
               var20 = var16;
               break label54;
            }

            for (; var3 < var4; var3++) {
               try {
                  JSONObject var12 = var13.getJSONObject(var3);
                  String var26 = var12.getString("name");
                  Intrinsics.checkNotNullExpressionValue(var26, "getString(...)");
                  FilesActivity.Entry var23 = new FilesActivity.Entry(var26, var12.getBoolean("dir"), var12.optLong("size"), var12.optLong("mtime"));
                  var22.add(var23);
               } catch (Exception var15) {
                  var20 = var15;
                  break label54;
               }
            }

            try {
               long var5 = var11.optLong("free");
               long var7 = var11.optLong("total");
               Handler var27 = var0.ui;
               FilesActivity$Pane$$ExternalSyntheticLambda8 var24 = new FilesActivity$Pane$$ExternalSyntheticLambda8(var2, var22, var0, var5, var7);
               var27.post(var24);
               return;
            } catch (Exception var14) {
               var20 = var14;
            }
         }

         var0.ui.post(new FilesActivity$Pane$$ExternalSyntheticLambda9(var2, var20));
      }

      private static final void refresh$lambda$32$lambda$30(FilesActivity.Pane var0, ArrayList var1, FilesActivity var2, long var3, long var5) {
         var0.entries = var0.sorted(var1);
         var0.lastError = null;
         var0.render();
         TextView var8 = var0.getInfo();
         int var7 = var1.size();
         String var9 = var2.fmtSize(var3);
         String var10 = var2.fmtSize(var5);
         var8.setText(var7 + " items · free " + var9 + " of " + var10);
      }

      private static final void refresh$lambda$32$lambda$31(FilesActivity.Pane var0, Exception var1) {
         var0.lastError = var1.getMessage();
         var0.entries = CollectionsKt.emptyList();
         var0.render();
         TextView var2 = var0.getInfo();
         String var3 = var1.getMessage();
         var2.setText("error: " + var3);
      }

      private static final void render$lambda$40(FilesActivity.Entry var0, FilesActivity.Pane var1, View var2) {
         if (var0.getDir() && var1.selected.isEmpty()) {
            var1.enter(var0.getName());
         } else {
            var1.toggle(var0.getName());
         }
      }

      private static final boolean render$lambda$41(FilesActivity.Pane var0, FilesActivity.Entry var1, View var2) {
         var0.toggle(var1.getName());
         return true;
      }

      private final List<FilesActivity.Entry> sorted(List<FilesActivity.Entry> var1) {
         Comparator var5;
         label28: {
            String var2 = this.sortKey;
            switch (var2.hashCode()) {
               case 3076014:
                  if (var2.equals("date")) {
                     var5 = new Comparator() {
                        @Override
                        public final int compare(T var1, T var2x) {
                           return ComparisonsKt.compareValues(((FilesActivity.Entry)var1).getMtime(), ((FilesActivity.Entry)var2x).getMtime());
                        }
                     };
                     break label28;
                  }
                  break;
               case 3530753:
                  if (var2.equals("size")) {
                     var5 = new Comparator() {
                        @Override
                        public final int compare(T var1, T var2) {
                           return ComparisonsKt.compareValues(((FilesActivity.Entry)var1).getSize(), ((FilesActivity.Entry)var2).getSize());
                        }
                     };
                     break label28;
                  }
                  break;
               case 3575610:
                  if (var2.equals("type")) {
                     var5 = new Comparator(new Comparator() {
                        @Override
                        public final int compare(T var1, T var2) {
                           var1 = StringsKt.substringAfterLast(((FilesActivity.Entry)var1).getName(), '.', "").toLowerCase(Locale.ROOT);
                           Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                           Comparable var4 = var1;
                           var2 = StringsKt.substringAfterLast(((FilesActivity.Entry)var2).getName(), '.', "").toLowerCase(Locale.ROOT);
                           Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
                           return ComparisonsKt.compareValues(var4, var2);
                        }
                     }) {
                        final Comparator $this_thenBy;

                        {
                           this.$this_thenBy = var1;
                        }

                        @Override
                        public final int compare(T var1, T var2) {
                           int var3 = this.$this_thenBy.compare(var1, var2);
                           if (var3 == 0) {
                              var1 = ((FilesActivity.Entry)var1).getName().toLowerCase(Locale.ROOT);
                              Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                              Comparable var5 = var1;
                              var2 = ((FilesActivity.Entry)var2).getName().toLowerCase(Locale.ROOT);
                              Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
                              var3 = ComparisonsKt.compareValues(var5, var2);
                           }

                           return var3;
                        }
                     };
                     break label28;
                  }
            }

            var5 = new Comparator() {
               @Override
               public final int compare(T var1, T var2) {
                  var1 = ((FilesActivity.Entry)var1).getName().toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                  Comparable var4 = var1;
                  var2 = ((FilesActivity.Entry)var2).getName().toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
                  return ComparisonsKt.compareValues(var4, var2);
               }
            };
         }

         Comparator var3 = new Comparator() {
            @Override
            public final int compare(T var1, T var2) {
               return ComparisonsKt.compareValues(((FilesActivity.Entry)var1).getDir() ^ true, ((FilesActivity.Entry)var2).getDir() ^ true);
            }
         };
         Iterable var4 = var1;
         if (!this.sortAsc) {
            var5 = var5.reversed();
         }

         Intrinsics.checkNotNull(var5);
         return CollectionsKt.sortedWith(var4, ComparisonsKt.then(var3, var5));
      }

      private final void updateSortBar() {
         for (Map.Entry var4 : this.sortBtns.entrySet()) {
            String var5 = (String)var4.getKey();
            TextView var7 = (TextView)var4.getValue();
            byte var3 = Intrinsics.areEqual(var5, this.sortKey);
            boolean var2;
            if (var5.length() > 0) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            if (var2) {
               StringBuilder var10 = new StringBuilder();
               char var1 = var5.charAt(0);
               String var8 = String.valueOf(var1);
               Intrinsics.checkNotNull(var8, "null cannot be cast to non-null type java.lang.String");
               var8 = var8.toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var8, "toUpperCase(...)");
               StringBuilder var11 = var10.append(var8);
               var5 = var5.substring(1);
               Intrinsics.checkNotNullExpressionValue(var5, "substring(...)");
               var5 = var11.append(var5).toString();
            }

            String var12;
            if (var3 != 0) {
               if (this.sortAsc) {
                  var12 = " ▲";
               } else {
                  var12 = " ▼";
               }
            } else {
               var12 = "";
            }

            var7.setText(var5 + var12);
            if (var3 != 0) {
               var2 = Color.rgb(111, 227, 161);
            } else {
               var2 = this.this$0.dim;
            }

            var7.setTextColor(var2);
            var7.setTypeface(null, var3);
         }
      }

      public final View build() {
         LinearLayout var2 = new LinearLayout((Context)this.this$0);
         FilesActivity var1 = this.this$0;
         var2.setOrientation(1);
         var2.setPadding(var1.dp(8), 0, var1.dp(8), 0);
         this.setRoot(var2);
         LinearLayout var6 = new LinearLayout((Context)this.this$0);
         var6.setOrientation(0);
         var6.setGravity(16);
         TextView var12 = new TextView((Context)this.this$0);
         var12.setText(this.title);
         var12.setTextColor(-1);
         var12.setTextSize(16.0F);
         var12.setTypeface(null, 1);
         var6.addView((View)var12);
         var6.addView(new View((Context)this.this$0), (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, 1, 1.0F)));
         var6.addView((View)build$small(this.this$0, "↑ Up", new FilesActivity$Pane$$ExternalSyntheticLambda19(this)));
         var6.addView((View)build$small(this.this$0, "⟳", new FilesActivity$Pane$$ExternalSyntheticLambda20(this)));
         var6.addView((View)build$small(this.this$0, "+ Folder", new FilesActivity$Pane$$ExternalSyntheticLambda21(this)));
         var6.addView((View)build$small(this.this$0, "Open", new FilesActivity$Pane$$ExternalSyntheticLambda22(this)));
         var6.addView((View)build$small(this.this$0, "Delete", new FilesActivity$Pane$$ExternalSyntheticLambda1(this)));
         this.getRoot().addView((View)var6);
         TextView var13 = new TextView((Context)this.this$0);
         var1 = this.this$0;
         var13.setTextColor(Color.rgb(111, 227, 161));
         var13.setTextSize(13.0F);
         var13.setTypeface(Typeface.MONOSPACE);
         var13.setPadding(var1.dp(4), var1.dp(2), var1.dp(4), var1.dp(4));
         var13.setOnClickListener(new FilesActivity$Pane$$ExternalSyntheticLambda2(this));
         this.setPathTv(var13);
         this.getRoot().addView((View)this.getPathTv());
         LinearLayout var8 = new LinearLayout((Context)this.this$0);
         FilesActivity var14 = this.this$0;
         var8.setOrientation(0);
         var8.setGravity(16);
         var8.setPadding(var14.dp(4), 0, var14.dp(4), var14.dp(4));
         TextView var3 = new TextView((Context)this.this$0);
         FilesActivity var15 = this.this$0;
         var3.setText("Sort:");
         var3.setTextColor(var15.dim);
         var3.setTextSize(12.0F);
         var3.setPadding(0, 0, var15.dp(8), 0);
         var8.addView((View)var3);

         for (String var19 : CollectionsKt.listOf(new String[]{"name", "size", "date", "type"})) {
            TextView var4 = new TextView((Context)this.this$0);
            FilesActivity var5 = this.this$0;
            var4.setTextSize(13.0F);
            var4.setPadding(var5.dp(8), var5.dp(6), var5.dp(8), var5.dp(6));
            var4.setOnClickListener(new FilesActivity$Pane$$ExternalSyntheticLambda3(this, var19));
            this.sortBtns.put(var19, var4);
            var8.addView((View)var4);
         }

         this.updateSortBar();
         this.getRoot().addView((View)var8);
         LinearLayout var9 = new LinearLayout((Context)this.this$0);
         var9.setOrientation(1);
         this.setList(var9);
         var2 = this.getRoot();
         ScrollView var10 = new ScrollView((Context)this.this$0);
         var10.addView((View)this.getList());
         var2.addView((View)var10, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, 0, 1.0F)));
         TextView var18 = new TextView((Context)this.this$0);
         var1 = this.this$0;
         var18.setTextColor(var1.dim);
         var18.setTextSize(12.0F);
         var18.setPadding(var1.dp(4), var1.dp(4), var1.dp(4), var1.dp(6));
         this.setInfo(var18);
         this.getRoot().addView((View)this.getInfo());
         return (View)this.getRoot();
      }

      public final void deleteSelected() {
         if (this.selected.isEmpty()) {
            this.this$0.toast("Select files or folders first (tap to select, tap a folder with nothing selected to open it)");
         } else {
            List var4 = CollectionsKt.toList(this.selected);
            android.app.AlertDialog.Builder var3 = new android.app.AlertDialog.Builder((Context)this.this$0);
            int var1 = var4.size();
            String var2;
            if (this.remote) {
               var2 = "the comma";
            } else {
               var2 = "the tablet";
            }

            var3.setTitle("Delete " + var1 + " item(s) from " + var2 + "?")
               .setMessage(CollectionsKt.joinToString$default(var4, "\n", null, null, 0, null, null, 62, null))
               .setPositiveButton("Delete", new FilesActivity$Pane$$ExternalSyntheticLambda17(this, this.this$0, var4))
               .setNegativeButton("Cancel", null)
               .show();
         }
      }

      public final void enter(String var1) {
         Intrinsics.checkNotNullParameter(var1, "name");
         StringBuilder var2;
         if (Intrinsics.areEqual(this.path, "/")) {
            var2 = new StringBuilder();
         } else {
            String var3 = this.path;
            var2 = new StringBuilder().append(var3);
         }

         this.path = var2.append("/").append(var1).toString();
         this.selected.clear();
         this.refresh();
      }

      public final List<FilesActivity.Entry> getEntries() {
         return this.entries;
      }

      public final TextView getInfo() {
         TextView var1 = this.info;
         if (var1 != null) {
            return var1;
         } else {
            Intrinsics.throwUninitializedPropertyAccessException("info");
            return null;
         }
      }

      public final String getLastError() {
         return this.lastError;
      }

      public final LinearLayout getList() {
         LinearLayout var1 = this.list;
         if (var1 != null) {
            return var1;
         } else {
            Intrinsics.throwUninitializedPropertyAccessException("list");
            return null;
         }
      }

      public final String getPath() {
         return this.path;
      }

      public final TextView getPathTv() {
         TextView var1 = this.pathTv;
         if (var1 != null) {
            return var1;
         } else {
            Intrinsics.throwUninitializedPropertyAccessException("pathTv");
            return null;
         }
      }

      public final boolean getRemote() {
         return this.remote;
      }

      public final LinearLayout getRoot() {
         LinearLayout var1 = this.root;
         if (var1 != null) {
            return var1;
         } else {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            return null;
         }
      }

      public final LinkedHashSet<String> getSelected() {
         return this.selected;
      }

      public final boolean getSortAsc() {
         return this.sortAsc;
      }

      public final String getSortKey() {
         return this.sortKey;
      }

      public final String getTitle() {
         return this.title;
      }

      public final void goToPathDialog() {
         boolean var1 = this.remote;
         String var3 = "/";
         List var2;
         if (var1) {
            var2 = CollectionsKt.listOf(new String[]{"/data", "/data/openpilot", "/data/media/0/realdata", "/data/params/d", "/data/extstorage", "/tmp", "/"});
         } else {
            String var5 = Environment.getExternalStorageDirectory().getAbsolutePath();
            String var6 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            String var4 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
            File var7 = this.this$0.getExternalFilesDir(null);
            String var8 = var3;
            if (var7 != null) {
               var8 = var7.getAbsolutePath();
               if (var8 == null) {
                  var8 = var3;
               }
            }

            var2 = CollectionsKt.listOf(new String[]{var5, var6, var4, var8});
         }

         new android.app.AlertDialog.Builder((Context)this.this$0)
            .setTitle("Go to")
            .setItems(
               CollectionsKt.plus(var2, "Type a path…").toArray(new String[0]), new FilesActivity$Pane$$ExternalSyntheticLambda14(var2, this, this.this$0)
            )
            .show();
      }

      public final void mkdir() {
         FilesActivity var1 = this.this$0;
         String var2 = this.path;
         FilesActivity.input$default(var1, "New folder in " + var2, "name", null, new FilesActivity$Pane$$ExternalSyntheticLambda4(this, this.this$0), 4, null);
      }

      public final void openSelected() {
         String var4 = CollectionsKt.singleOrNull(this.selected);
         Object var3 = null;
         String var2 = var4;
         if (var4 == null) {
            Iterable var17 = this.entries;
            var2 = null;
            boolean var1 = false;
            Iterator var5 = var17.iterator();

            while (true) {
               if (!var5.hasNext()) {
                  if (!var1) {
                     var2 = null;
                  }
                  break;
               }

               var4 = (String)var5.next();
               if (!((FilesActivity.Entry)var4).getDir()) {
                  if (var1) {
                     var2 = null;
                     break;
                  }

                  var2 = var4;
                  var1 = true;
               }
            }

            FilesActivity.Entry var9 = (FilesActivity.Entry)var2;
            if (var9 != null) {
               var2 = var9.getName();
            } else {
               var2 = null;
            }
         }

         Iterator var21 = this.entries.iterator();

         while (true) {
            if (!var21.hasNext()) {
               var2 = (String)var3;
               break;
            }

            var4 = (String)var21.next();
            FilesActivity.Entry var6 = (FilesActivity.Entry)var4;
            boolean var7;
            if (Intrinsics.areEqual(var6.getName(), var2) && !var6.getDir()) {
               var7 = true;
            } else {
               var7 = false;
            }

            if (var7) {
               var2 = var4;
               break;
            }
         }

         FilesActivity.Entry var11 = (FilesActivity.Entry)var2;
         if (var11 == null) {
            var3 = this.this$0;
            FilesActivity.Pane var13 = this;
            ((FilesActivity)var3).toast("Select one file to open");
         } else {
            FileKinds.Kind var20 = FileKinds.INSTANCE.of(var11.getName(), false);
            if (var20.getPreview() == FileKinds.Preview.NONE) {
               var3 = this.this$0;
               var2 = var11.getName();
               ((FilesActivity)var3).toast(var2 + ": can't be shown here - copy it to the tablet to open it");
            } else {
               var3 = this.this$0.join(this.path, var11.getName());
               this.this$0.io.execute(new FilesActivity$Pane$$ExternalSyntheticLambda18(var20, this, this.this$0, (String)var3, var11));
            }
         }
      }

      public final void refresh() {
         this.getPathTv().setText(this.path);
         if (this.remote) {
            String var4 = this.this$0.getHost();
            if (var4 == null) {
               this.getInfo().setText("not connected");
               return;
            }

            this.this$0.io.execute(new FilesActivity$Pane$$ExternalSyntheticLambda16(this.this$0, var4, this));
         } else {
            File var7 = new File(this.path);
            File[] var6 = var7.listFiles();
            if (var6 == null) {
               this.entries = CollectionsKt.emptyList();
               this.render();
               TextView var17 = this.getInfo();
               String var15;
               if (this.this$0.hasAllFiles()) {
                  var15 = this.path;
                  var15 = "cannot read " + var15;
               } else {
                  var15 = "tap 'Allow all files' to browse tablet storage";
               }

               var17.setText(var15);
               return;
            }

            boolean var3 = false;
            var var8 = new ArrayList(var6.length);

            for (File var9 : var6) {
               String var10 = var9.getName();
               Intrinsics.checkNotNullExpressionValue(var10, "getName(...)");
               var8.add(new FilesActivity.Entry(var10, var9.isDirectory(), var9.length(), var9.lastModified() / 1000));
            }

            List var12 = var8;
            this.entries = this.sorted(var12);
            this.render();
            TextView var13 = this.getInfo();
            int var11 = this.entries.size();
            String var16 = this.this$0.fmtSize(var7.getUsableSpace());
            var13.setText(var11 + " items · free " + var16);
         }
      }

      public final void render() {
         this.getList().removeAllViews();

         for (FilesActivity.Entry var5 : this.entries) {
            LinearLayout var3 = new LinearLayout((Context)this.this$0);
            FilesActivity var2 = this.this$0;
            var3.setOrientation(0);
            var3.setGravity(16);
            var3.setPadding(var2.dp(10), var2.dp(9), var2.dp(10), var2.dp(9));
            var3.setBackgroundResource(R.drawable.card_bg);
            boolean var1 = this.selected.contains(var5.getName());
            var3.setAlpha(1.0F);
            if (var1) {
               var3.setBackgroundColor(Color.rgb(22, 80, 50));
            }

            FileKinds.Kind var6 = FileKinds.INSTANCE.of(var5.getName(), var5.getDir());
            View var8 = new View((Context)this.this$0);
            var8.setBackground(SvIcon.drawable$default(SvIcon.INSTANCE, var6.getIcon(), var6.getColor(), 0, 4, null));
            LayoutParams var7 = new LayoutParams(this.this$0.dp(22), this.this$0.dp(22));
            var7.rightMargin = this.this$0.dp(10);
            Unit var17 = Unit.INSTANCE;
            var3.addView(var8, (android.view.ViewGroup.LayoutParams)var7);
            TextView var9 = new TextView((Context)this.this$0);
            var9.setText(var5.getName());
            var9.setTextColor(-1);
            var9.setTextSize(15.0F);
            var9.setMaxLines(1);
            var9.setEllipsize(TruncateAt.MIDDLE);
            var3.addView((View)var9, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
            TextView var18 = new TextView((Context)this.this$0);
            var2 = this.this$0;
            var18.setText(var2.fmtDate(var5.getMtime()));
            var18.setTextColor(var2.dim);
            var18.setTextSize(11.0F);
            var18.setPadding(var2.dp(8), 0, 0, 0);
            var3.addView((View)var18);
            TextView var19 = new TextView((Context)this.this$0);
            FilesActivity var20 = this.this$0;
            String var11;
            if (var5.getDir()) {
               var11 = "";
            } else {
               var11 = var20.fmtSize(var5.getSize());
            }

            var19.setText(var11);
            var19.setTextColor(var20.dim);
            var19.setTextSize(12.0F);
            var19.setPadding(var20.dp(10), 0, 0, 0);
            var19.setMinWidth(var20.dp(64));
            var19.setGravity(8388613);
            var3.addView((View)var19);
            var3.setOnClickListener(new FilesActivity$Pane$$ExternalSyntheticLambda10(var5, this));
            var3.setOnLongClickListener(new FilesActivity$Pane$$ExternalSyntheticLambda12(this, var5));
            LayoutParams var12 = new LayoutParams(-1, -2);
            var12.bottomMargin = this.this$0.dp(3);
            this.getList().addView((View)var3, (android.view.ViewGroup.LayoutParams)var12);
         }

         if (this.entries.isEmpty()) {
            LinearLayout var16 = this.getList();
            TextView var14 = new TextView((Context)this.this$0);
            FilesActivity var15 = this.this$0;
            String var13 = this.lastError;
            if (var13 == null) {
               var13 = "(empty)";
            }

            var14.setText(var13);
            var14.setTextColor(var15.dim);
            var14.setPadding(var15.dp(10), var15.dp(10), var15.dp(10), var15.dp(10));
            var16.addView((View)var14);
         }

         this.this$0.updateButtons();
      }

      public final void setEntries(List<FilesActivity.Entry> var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.entries = var1;
      }

      public final void setInfo(TextView var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.info = var1;
      }

      public final void setLastError(String var1) {
         this.lastError = var1;
      }

      public final void setList(LinearLayout var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.list = var1;
      }

      public final void setPath(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.path = var1;
      }

      public final void setPathTv(TextView var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.pathTv = var1;
      }

      public final void setRoot(LinearLayout var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.root = var1;
      }

      public final void setSort(String var1) {
         Intrinsics.checkNotNullParameter(var1, "key");
         if (Intrinsics.areEqual(var1, this.sortKey)) {
            this.sortAsc ^= true;
         } else {
            this.sortKey = var1;
            this.sortAsc = Intrinsics.areEqual(var1, "name");
         }

         this.entries = this.sorted(this.entries);
         this.render();
         this.updateSortBar();
      }

      public final void setSortAsc(boolean var1) {
         this.sortAsc = var1;
      }

      public final void setSortKey(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.sortKey = var1;
      }

      public final void toggle(String var1) {
         Intrinsics.checkNotNullParameter(var1, "name");
         if (!this.selected.remove(var1)) {
            this.selected.add(var1);
         }

         this.render();
      }

      public final void up() {
         String var2 = this.path;
         String var1 = "/";
         if (!Intrinsics.areEqual(var2, "/")) {
            var2 = new File(this.path).getParent();
            if (var2 != null) {
               var1 = var2;
            }

            this.path = var1;
            this.selected.clear();
            this.refresh();
         }
      }
   }

   private final class QRow {
      private final ProgressBar bar;
      private final FilesActivity.Job job;
      private final TextView state;
      final FilesActivity this$0;
      private final LinearLayout view;

      public QRow(FilesActivity.Job var1, LinearLayout var2, TextView var3, ProgressBar var4, ProgressBar var5) {
         Intrinsics.checkNotNullParameter(var2, "job");
         Intrinsics.checkNotNullParameter(var3, "view");
         Intrinsics.checkNotNullParameter(var4, "state");
         Intrinsics.checkNotNullParameter(var5, "bar");
         this.this$0 = var1;
         super();
         this.job = var2;
         this.view = var3;
         this.state = var4;
         this.bar = var5;
      }

      public final ProgressBar getBar() {
         return this.bar;
      }

      public final FilesActivity.Job getJob() {
         return this.job;
      }

      public final TextView getState() {
         return this.state;
      }

      public final LinearLayout getView() {
         return this.view;
      }
   }

   private static enum QState {
      COPYING,
      DONE,
      FAILED,
      WAITING;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(FilesActivity.QState.$VALUES);
      private static final FilesActivity.QState[] $VALUES = $values();

      // $VF: synthetic method
      private static final FilesActivity.QState[] $values() {
         return new FilesActivity.QState[]{WAITING, COPYING, DONE, FAILED};
      }

      public static EnumEntries<FilesActivity.QState> getEntries() {
         return $ENTRIES;
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;
      public static final int[] $EnumSwitchMapping$1;

      static {
         int[] var0 = new int[FilesActivity.Clash.values().length];

         try {
            var0[FilesActivity.Clash.CANCEL.ordinal()] = 1;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[FilesActivity.Clash.SKIP.ordinal()] = 2;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[FilesActivity.Clash.KEEP_BOTH.ordinal()] = 3;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[FilesActivity.Clash.REPLACE.ordinal()] = 4;
         } catch (NoSuchFieldError var6) {
         }

         $EnumSwitchMapping$0 = var0;
         var0 = new int[FilesActivity.QState.values().length];

         try {
            var0[FilesActivity.QState.WAITING.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[FilesActivity.QState.COPYING.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[FilesActivity.QState.DONE.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[FilesActivity.QState.FAILED.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$1 = var0;
      }
   }
}
