package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.BitmapFactory.Options;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class BookmarksView extends CardPage {
   private final SimpleDateFormat fmt;
   private MediaPlayer player;

   // $VF: synthetic method
   public static void $r8$lambda$C48WM_kfFDR_ykKgVRFmapuYXsk/* $VF was: $r8$lambda$C48WM_kfFDR_ykKgVRFmapuYXsk*/(
      Bookmarks.Item var0, BookmarksView var1, DialogInterface var2, int var3
   ) {
      paint$lambda$14$lambda$13(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$HQzPExjHkBWgrp6tgpuIt61Gnic(Bookmarks.Item var0, BookmarksView var1, EditText var2, DialogInterface var3, int var4) {
      edit$lambda$18(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$I_a8X7HKCIpyL1AOuCws7msQsv8/* $VF was: $r8$lambda$I_a8X7HKCIpyL1AOuCws7msQsv8*/(BookmarksView var0, Bookmarks.Item var1) {
      return paint$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Ig607ewwKSeY8cY_dTeNYZTinRg(BookmarksView var0) {
      return paint$lambda$4(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$L3VaeYpzCHwr66eVT3TjyIit_Ww(BookmarksView var0) {
      return paint$lambda$3(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Nrf6ZlD3LDSFnYr0QjzZZRQBEQA(BookmarksView var0, String var1, int var2) {
      paint$exportNow$lambda$2$lambda$1(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Uotwtu4Pd9_aCaiAV1U9sGMlwVo/* $VF was: $r8$lambda$Uotwtu4Pd9_aCaiAV1U9sGMlwVo*/(BookmarksView var0, Bookmarks.Item var1) {
      return paint$lambda$14(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fqo3CkCWp03xmEhIx_H8Hic5_yA/* $VF was: $r8$lambda$fqo3CkCWp03xmEhIx_H8Hic5_yA*/(EditText var0, JSONObject var1) {
      return edit$lambda$18$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$nnNz1i7YUMfzIsZPsjbY_1kYfPw/* $VF was: $r8$lambda$nnNz1i7YUMfzIsZPsjbY_1kYfPw*/(BookmarksView var0, boolean var1) {
      paint$exportNow$lambda$2(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pym7T4HeKx6UigR5unKcj9LDpng(BookmarksView var0, Bookmarks.Item var1) {
      return paint$lambda$12(var0, var1);
   }

   public BookmarksView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
      this.fmt = new SimpleDateFormat("EEE MMM d, h:mm:ss a", Locale.US);
      this.paint();
   }

   private final void edit(Bookmarks.Item var1) {
      EditText var2 = new EditText(this.getContext());
      var2.setText(var1.getNote());
      var2.setHint("What happened here?");
      var2.setMinLines(2);
      new Builder(this.getContext())
         .setTitle("Bookmark note")
         .setView((View)var2)
         .setPositiveButton("Save", new BookmarksView$$ExternalSyntheticLambda7(var1, this, var2))
         .setNegativeButton("Cancel", null)
         .show();
   }

   private static final void edit$lambda$18(Bookmarks.Item var0, BookmarksView var1, EditText var2, DialogInterface var3, int var4) {
      Bookmarks.INSTANCE.update(var0, new BookmarksView$$ExternalSyntheticLambda9(var2));
      var1.paint();
   }

   private static final Unit edit$lambda$18$lambda$17(EditText var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "j");
      var1.put("note", StringsKt.trim(var0.getText().toString()).toString());
      return Unit.INSTANCE;
   }

   private final void paint() {
      this.getCol().removeAllViews();
      Bookmarks var3 = Bookmarks.INSTANCE;
      Context var2 = this.getContext();
      Intrinsics.checkNotNullExpressionValue(var2, "getContext(...)");
      List var12 = var3.list(var2);
      LinearLayout var23 = this.card();
      this.add((View)var23);
      var23.addView(this.title("Bookmarks", "bookmark", "tablet"));
      var23.addView(
         (View)this.line(
            "Tap \ud83d\udd16 on the drive screen (or say \"bookmark\") to mark a moment; hold \ud83c\udfa4 on the card that pops up to say what happened. Export them and attach the zip in a chat: Claude reads exactly what the tablet knew at that moment.",
            CarUi.Companion.getDIM(),
            15.0F
         )
      );
      LinearLayout var4 = new LinearLayout(this.getContext());
      var4.setOrientation(0);
      var4.setPadding(0, this.getUi().dp(10), 0, 0);
      var4.addView((View)this.getUi().button("Export new", true, new BookmarksView$$ExternalSyntheticLambda2(this)));
      var4.addView(new View(this.getContext()), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.getUi().dp(12), 1)));
      var4.addView((View)CarUi.button$default(this.getUi(), "Export all", false, new BookmarksView$$ExternalSyntheticLambda3(this), 2, null));
      var23.addView((View)var4);
      if (var12.isEmpty()) {
         var23.addView((View)this.line("No bookmarks yet.", CarUi.Companion.getTEXT(), 16.0F));
      } else {
         for (Bookmarks.Item var25 : var12) {
            LinearLayout var24 = this.card();
            this.add((View)var24);
            TextView var7 = new TextView(this.getContext());
            String var6 = this.fmt.format(new Date(var25.getAtMs()));
            String var13;
            if (var25.getJson().optBoolean("exported")) {
               var13 = "  ·  exported";
            } else {
               var13 = "";
            }

            var7.setText(var6 + var13);
            var7.setTextColor(CarUi.Companion.getTEXT());
            var7.setTextSize(17.0F);
            var7.setTypeface(Typeface.DEFAULT_BOLD);
            var24.addView((View)var7);
            Iterable var26 = CollectionsKt.listOf(new String[]{var25.getTitle(), var25.getPlace(), var25.getJson().optString("speed")});
            var var14 = new ArrayList();

            for (Object var31 : var26) {
               String var8 = (String)var31;
               Intrinsics.checkNotNull(var8);
               boolean var1;
               if (var8.length() > 0) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               if (var1) {
                  var14.add(var31);
               }
            }

            List var15 = var14;
            var24.addView(
               (View)this.line(CollectionsKt.joinToString$default(var15, "  ·  ", null, null, 0, null, null, 62, null), CarUi.Companion.getDIM(), 15.0F)
            );
            String var16 = var25.getNote();
            Intrinsics.checkNotNullExpressionValue(var16, "<get-note>(...)");
            boolean var10;
            if (var16.length() > 0) {
               var10 = true;
            } else {
               var10 = false;
            }

            if (var10) {
               String var17 = var25.getNote();
               var24.addView((View)this.line("✎ " + var17, CarUi.Companion.getTEXT(), 16.0F));
            }

            String var18 = var25.getTranscript();
            Intrinsics.checkNotNullExpressionValue(var18, "<get-transcript>(...)");
            if (var18.length() > 0) {
               var10 = true;
            } else {
               var10 = false;
            }

            if (var10) {
               String var19 = var25.getTranscript();
               var24.addView((View)this.line("\ud83c\udfa4 “" + var19 + "”", CarUi.Companion.getTEXT(), 16.0F));
            } else if (var25.getHasVoice()) {
               var24.addView((View)this.line("\ud83c\udfa4 voice note (no transcript)", CarUi.Companion.getDIM(), 15.0F));
            }

            if (var25.getHasPicture()) {
               ImageView var20 = new ImageView(this.getContext());
               var20.setAdjustViewBounds(true);
               var20.setScaleType(ScaleType.FIT_START);

               try {
                  File var28 = new File(var25.getDir(), "picture.jpg");
                  String var34 = var28.getPath();
                  Options var29 = new Options();
                  var29.inSampleSize = 2;
                  Unit var32 = Unit.INSTANCE;
                  var20.setImageBitmap(BitmapFactory.decodeFile(var34, var29));
               } catch (Exception var9) {
               }

               View var33 = (View)var20;
               android.widget.LinearLayout.LayoutParams var21 = new android.widget.LinearLayout.LayoutParams(-1, -2);
               var21.topMargin = this.getUi().dp(6);
               Unit var30 = Unit.INSTANCE;
               var24.addView(var33, (LayoutParams)var21);
            }

            LinearLayout var22 = new LinearLayout(this.getContext());
            var22.setOrientation(0);
            var22.setPadding(0, this.getUi().dp(8), 0, 0);
            if (var25.getHasVoice()) {
               var22.addView((View)CarUi.button$default(this.getUi(), "▶ Play", false, new BookmarksView$$ExternalSyntheticLambda4(this, var25), 2, null));
               var22.addView(new View(this.getContext()), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.getUi().dp(10), 1)));
            }

            var22.addView((View)CarUi.button$default(this.getUi(), "✎ Note", false, new BookmarksView$$ExternalSyntheticLambda5(this, var25), 2, null));
            var22.addView(new View(this.getContext()), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.getUi().dp(10), 1)));
            var22.addView((View)CarUi.button$default(this.getUi(), "Delete", false, new BookmarksView$$ExternalSyntheticLambda6(this, var25), 2, null));
            var24.addView((View)var22);
         }
      }
   }

   private static final void paint$exportNow(BookmarksView var0, boolean var1) {
      new Thread(new BookmarksView$$ExternalSyntheticLambda0(var0, var1)).start();
   }

   private static final void paint$exportNow$lambda$2(BookmarksView var0, boolean var1) {
      Bookmarks var4 = Bookmarks.INSTANCE;
      Context var3 = var0.getContext();
      Intrinsics.checkNotNullExpressionValue(var3, "getContext(...)");
      Pair var5 = var4.export(var3, var1);
      String var6 = (String)var5.component1();
      int var2 = ((Number)var5.component2()).intValue();
      var0.getMain().post(new BookmarksView$$ExternalSyntheticLambda8(var0, var6, var2));
   }

   private static final void paint$exportNow$lambda$2$lambda$1(BookmarksView var0, String var1, int var2) {
      Context var3 = var0.getContext();
      if (var1 == null) {
         if (var2 == 1) {
            var1 = "";
         } else {
            var1 = "s";
         }

         var1 = "Exported " + var2 + " bookmark" + var1 + " to Download/StarView/Bookmarks";
      }

      Toast.makeText(var3, var1, 1).show();
      var0.paint();
   }

   private static final Unit paint$lambda$11(BookmarksView var0, Bookmarks.Item var1) {
      var0.play(new File(var1.getDir(), "voice.wav"));
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$12(BookmarksView var0, Bookmarks.Item var1) {
      var0.edit(var1);
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$14(BookmarksView var0, Bookmarks.Item var1) {
      new Builder(var0.getContext())
         .setTitle("Delete this bookmark?")
         .setPositiveButton("Delete", new BookmarksView$$ExternalSyntheticLambda1(var1, var0))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void paint$lambda$14$lambda$13(Bookmarks.Item var0, BookmarksView var1, DialogInterface var2, int var3) {
      Bookmarks.INSTANCE.delete(var0);
      var1.paint();
   }

   private static final Unit paint$lambda$3(BookmarksView var0) {
      paint$exportNow(var0, true);
      return Unit.INSTANCE;
   }

   private static final Unit paint$lambda$4(BookmarksView var0) {
      paint$exportNow(var0, false);
      return Unit.INSTANCE;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void play(File var1) {
      MediaPlayer var9;
      try {
         var9 = this.player;
      } catch (Exception var5) {
         Context var6 = this.getContext();
         String var2 = var5.getMessage();
         Toast.makeText(var6, "Can't play: " + var2, 0).show();
         return;
      }

      if (var9 != null) {
         try {
            var9.release();
         } catch (Exception var4) {
            Context var7 = this.getContext();
            String var10 = var4.getMessage();
            Toast.makeText(var7, "Can't play: " + var10, 0).show();
            return;
         }
      }

      try {
         var9 = new MediaPlayer();
         var9.setDataSource(var1.getPath());
         var9.prepare();
         var9.start();
         this.player = var9;
      } catch (Exception var3) {
         Context var8 = this.getContext();
         String var11 = var3.getMessage();
         Toast.makeText(var8, "Can't play: " + var11, 0).show();
      }
   }

   @Override
   public void refresh() {
      this.paint();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void stop() {
      label18: {
         MediaPlayer var1;
         try {
            var1 = this.player;
         } catch (Exception var3) {
            break label18;
         }

         if (var1 != null) {
            try {
               var1.release();
            } catch (Exception var2) {
            }
         }
      }

      this.player = null;
   }
}
