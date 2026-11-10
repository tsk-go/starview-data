package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class BookmarkCard extends LinearLayout {
   private final Function0<Unit> askMic;
   private final TextView close;
   private final float d;
   private final Runnable hide;
   private Bookmarks.Item item;
   private final Handler main;
   private boolean recording;
   private final Function0<Boolean> stopped;
   private final TextView sub;
   private int takes;
   private final TextView talk;
   private final TextView title;
   private final TextView type;
   private VoiceNote voice;

   // $VF: synthetic method
   public static Unit $r8$lambda$6ksCi_ZwErqxLIBf_DvQGCgBWsU/* $VF was: $r8$lambda$6ksCi_ZwErqxLIBf_DvQGCgBWsU*/(
      Bookmarks.Item var0, BookmarkCard var1, String var2, String var3
   ) {
      return startTalk$lambda$16(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$7hE33pOXrz_1p8QAMwgPwqOjLRs(BookmarkCard var0, View var1) {
      _init_$lambda$9(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$9TdOq5yp_LDCOWmw_MFl5sPwdQY(BookmarkCard var0, DialogInterface var1, int var2) {
      typeNote$lambda$20(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$FslzKIBETsx_u7VEIx17SIeGEZ8/* $VF was: $r8$lambda$FslzKIBETsx_u7VEIx17SIeGEZ8*/(
      EditText var0, Bookmarks.Item var1, BookmarkCard var2, DialogInterface var3, int var4
   ) {
      typeNote$lambda$19(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LCjav9P_taTIG8I7g674QE9915k(String var0, JSONObject var1) {
      return typeNote$lambda$19$lambda$18(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$P7FDibAfptVnD31jpIRbY07_anM/* $VF was: $r8$lambda$P7FDibAfptVnD31jpIRbY07_anM*/(BookmarkCard var0, View var1) {
      _init_$lambda$8(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$ZvQ06WjDXX5tkY_Ulcu9geTjIb8(BookmarkCard var0, View var1, MotionEvent var2) {
      return _init_$lambda$7(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$f1lfJz50WExzx_9_f2zHcQVpjjA/* $VF was: $r8$lambda$f1lfJz50WExzx_9_f2zHcQVpjjA*/(BookmarkCard var0) {
      hide$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ut9CmPK3chgGacxtpfSA2_q62yg/* $VF was: $r8$lambda$ut9CmPK3chgGacxtpfSA2_q62yg*/(String var0, String var1, JSONObject var2) {
      return startTalk$lambda$16$lambda$15(var0, var1, var2);
   }

   public BookmarkCard(Context var1, Function0<Boolean> var2, Function0<Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "stopped");
      Intrinsics.checkNotNullParameter(var3, "askMic");
      super(var1);
      this.stopped = var2;
      this.askMic = var3;
      this.d = this.getResources().getDisplayMetrics().density;
      this.main = new Handler(Looper.getMainLooper());
      TextView var5 = new TextView(var1);
      var5.setTextColor(-1);
      var5.setTextSize(20.0F);
      var5.setTypeface(Typeface.DEFAULT_BOLD);
      this.title = var5;
      TextView var6 = new TextView(var1);
      var6.setTextColor(Color.rgb(200, 208, 222));
      var6.setTextSize(15.0F);
      this.sub = var6;
      this.talk = new TextView(var1);
      this.type = new TextView(var1);
      this.close = new TextView(var1);
      this.hide = new BookmarkCard$$ExternalSyntheticLambda1(this);
      this.setOrientation(1);
      this.setVisibility(8);
      this.setPadding(this.dp(18), this.dp(14), this.dp(18), this.dp(14));
      GradientDrawable var7 = new GradientDrawable();
      var7.setCornerRadius(this.dp(18));
      var7.setColor(Color.argb(225, 18, 20, 26));
      var7.setStroke(this.dp(2), Color.rgb(255, 196, 0));
      this.setBackground((Drawable)var7);
      this.addView((View)this.title);
      this.addView((View)this.sub);
      LinearLayout var4 = new LinearLayout(var1);
      var4.setOrientation(0);
      var4.setPadding(0, this.dp(10), 0, 0);
      _init_$pill(this, var4, this.talk, "\ud83c\udfa4  Hold to talk", Color.rgb(52, 92, 170));
      _init_$pill(this, var4, this.type, "✎  Type", Color.rgb(70, 76, 90));
      _init_$pill(this, var4, this.close, "✕", Color.rgb(70, 76, 90));
      this.addView((View)var4);
      this.talk.setOnTouchListener(new BookmarkCard$$ExternalSyntheticLambda2(this));
      this.type.setOnClickListener(new BookmarkCard$$ExternalSyntheticLambda3(this));
      this.close.setOnClickListener(new BookmarkCard$$ExternalSyntheticLambda4(this));
   }

   private static final boolean _init_$lambda$7(BookmarkCard var0, View var1, MotionEvent var2) {
      switch (var2.getActionMasked()) {
         case 0:
            var0.startTalk();
            break;
         case 1:
         case 3:
            var0.stopTalk();
         case 2:
      }

      return true;
   }

   private static final void _init_$lambda$8(BookmarkCard var0, View var1) {
      var0.typeNote();
   }

   private static final void _init_$lambda$9(BookmarkCard var0, View var1) {
      var0.main.removeCallbacks(var0.hide);
      var0.setVisibility(8);
   }

   private static final void _init_$pill(BookmarkCard var0, LinearLayout var1, TextView var2, String var3, int var4) {
      var2.setText(var3);
      var2.setTextColor(-1);
      var2.setTextSize(18.0F);
      var2.setTypeface(Typeface.DEFAULT_BOLD);
      var2.setGravity(17);
      var2.setPadding(var0.dp(18), var0.dp(12), var0.dp(18), var0.dp(12));
      GradientDrawable var7 = new GradientDrawable();
      var7.setCornerRadius(var0.dp(24));
      var7.setColor(var4);
      var2.setBackground((Drawable)var7);
      View var8 = (View)var2;
      LayoutParams var6 = new LayoutParams(-2, -2);
      var6.setMarginEnd(var0.dp(10));
      Unit var5 = Unit.INSTANCE;
      var1.addView(var8, (android.view.ViewGroup.LayoutParams)var6);
   }

   private final int dp(int var1) {
      return (int)(var1 * this.d);
   }

   private static final void hide$lambda$2(BookmarkCard var0) {
      if (!var0.recording) {
         var0.setVisibility(8);
      }
   }

   private final void startTalk() {
      Bookmarks.Item var3 = this.item;
      if (var3 != null) {
         if (this.getContext().checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
            this.askMic.invoke();
         } else {
            Context var2 = this.getContext();
            Intrinsics.checkNotNullExpressionValue(var2, "getContext(...)");
            VoiceNote var4 = new VoiceNote(var2);
            this.voice = var4;
            this.takes++;
            String var5;
            if (this.takes == 1) {
               var5 = "voice.wav";
            } else {
               int var1 = this.takes;
               var5 = "voice" + var1 + ".wav";
            }

            String var6 = var4.start(new File(var3.getDir(), var5), new BookmarkCard$$ExternalSyntheticLambda7(var3, this, var5));
            if (var6 != null) {
               this.sub.setText("Couldn't record: " + var6);
            } else {
               this.recording = true;
               this.talk.setText("●  Recording… let go to stop");
               this.main.removeCallbacks(this.hide);
            }
         }
      }
   }

   private static final Unit startTalk$lambda$16(Bookmarks.Item var0, BookmarkCard var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var3, "text");
      Bookmarks.INSTANCE.update(var0, new BookmarkCard$$ExternalSyntheticLambda8(var2, var3));
      TextView var6 = var1.sub;
      boolean var4;
      if (var3.length() > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      String var5;
      if (var4) {
         var5 = "“" + var3 + "”";
      } else {
         var5 = "voice note saved (no transcript on this tablet)";
      }

      var6.setText(var5);
      if (!var1.recording) {
         var1.main.removeCallbacks(var1.hide);
         var1.main.postDelayed(var1.hide, 6000L);
      }

      return Unit.INSTANCE;
   }

   private static final Unit startTalk$lambda$16$lambda$15(String var0, String var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "j");
      JSONArray var5 = var2.optJSONArray("voices");
      JSONArray var4 = var5;
      if (var5 == null) {
         var4 = new JSONArray();
         var2.put("voices", var4);
      }

      var4.put(new JSONObject().put("file", var0).put("text", var1));
      Iterable var9 = RangesKt.until(0, var4.length());
      var var7 = new ArrayList();
      Iterator var8 = var9.iterator();

      while (var8.hasNext()) {
         JSONObject var6 = var4.optJSONObject(((IntIterator)var8).nextInt());
         var5 = null;
         var1 = var5;
         if (var6 != null) {
            String var13 = var6.optString("text");
            var1 = var5;
            if (var13 != null) {
               boolean var3;
               if (var13.length() > 0) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               var1 = var5;
               if (var3) {
                  var1 = var13;
               }
            }
         }

         if (var1 != null) {
            var7.add(var1);
         }
      }

      List var10 = var7;
      var2.put("voice_text", CollectionsKt.joinToString$default(var10, " / ", null, null, 0, null, null, 62, null));
      return Unit.INSTANCE;
   }

   private final void stopTalk() {
      if (this.recording) {
         this.recording = false;
         VoiceNote var1 = this.voice;
         if (var1 != null) {
            var1.stop();
         }

         this.talk.setText("\ud83c\udfa4  Hold to add more");
         this.sub.setText("saving…");
      }
   }

   private final void typeNote() {
      Bookmarks.Item var2 = this.item;
      if (var2 != null) {
         if (!this.stopped.invoke()) {
            this.sub.setText("Typing only when stopped - hold \ud83c\udfa4 to talk instead");
         } else {
            this.main.removeCallbacks(this.hide);
            EditText var1 = new EditText(this.getContext());
            var1.setText(var2.getNote());
            var1.setHint("What happened here?");
            var1.setSingleLine(false);
            var1.setMinLines(2);
            new Builder(this.getContext())
               .setTitle("Bookmark note")
               .setView((View)var1)
               .setPositiveButton("Save", new BookmarkCard$$ExternalSyntheticLambda5(var1, var2, this))
               .setNegativeButton("Cancel", new BookmarkCard$$ExternalSyntheticLambda6(this))
               .show();
         }
      }
   }

   private static final void typeNote$lambda$19(EditText var0, Bookmarks.Item var1, BookmarkCard var2, DialogInterface var3, int var4) {
      String var5 = StringsKt.trim(var0.getText().toString()).toString();
      Bookmarks.INSTANCE.update(var1, new BookmarkCard$$ExternalSyntheticLambda0(var5));
      TextView var7 = var2.sub;
      boolean var8;
      if (var5.length() == 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      String var6;
      if (var8) {
         var6 = "saved";
      } else {
         var6 = "“" + var5 + "”";
      }

      var7.setText(var6);
      var2.main.postDelayed(var2.hide, 4000L);
   }

   private static final Unit typeNote$lambda$19$lambda$18(String var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "j");
      var1.put("note", var0);
      return Unit.INSTANCE;
   }

   private static final void typeNote$lambda$20(BookmarkCard var0, DialogInterface var1, int var2) {
      var0.main.postDelayed(var0.hide, 4000L);
   }

   public final void show(Bookmarks.Item var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      this.item = var1;
      this.takes = 0;
      this.title.setText("\ud83d\udd16  Bookmarked");
      TextView var5 = this.sub;
      String var6 = var1.getTitle();
      String var9 = var1.getPlace();
      boolean var4 = true;
      Iterable var15 = CollectionsKt.listOf(new String[]{var6, var9});
      var var10 = new ArrayList();

      for (Object var8 : var15) {
         String var7 = (String)var8;
         Intrinsics.checkNotNull(var7);
         boolean var3;
         if (var7.length() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var10.add(var8);
         }
      }

      List var11 = var10;
      TextView var12 = CollectionsKt.joinToString$default(var11, "  ·  ", null, null, 0, null, null, 62, null);
      boolean var14;
      if (var12.length() == 0) {
         var14 = var4;
      } else {
         var14 = false;
      }

      if (var14) {
         var12 = "saved";
      }

      var5.setText((CharSequence)var12);
      this.talk.setText("\ud83c\udfa4  Hold to talk");
      var12 = this.type;
      float var2;
      if (this.stopped.invoke()) {
         var2 = 1.0F;
      } else {
         var2 = 0.4F;
      }

      var12.setAlpha(var2);
      this.setVisibility(0);
      this.bringToFront();
      this.main.removeCallbacks(this.hide);
      this.main.postDelayed(this.hide, 10000L);
   }
}
