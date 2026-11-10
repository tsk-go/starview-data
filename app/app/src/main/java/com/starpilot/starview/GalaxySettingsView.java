package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GalaxySettingsView extends FrameLayout {
   public static final GalaxySettingsView.Companion Companion = new GalaxySettingsView.Companion(null);
   public static final long REFRESH_MS = 3000L;
   private boolean busy;
   private final LinearLayout col;
   private JSONObject data;
   private final Function1<LinearLayout, Unit> footer;
   private final Galaxy7 g;
   private JSONObject hub;
   private String lastShown;
   private final Handler main;
   private final Function2<String, String, Unit> openWeb;
   private final Function2<String, LinearLayout, Unit> pageExtras;
   private final Runnable poll;
   private final GalaxySettingsView.Root root;
   private final ScrollView scroll;
   private final ArrayList<String> stack;
   private final HashMap<String, String> titles;
   private boolean touching;
   private final CarUi ui;
   private VasmEditorCard vasmCard;
   private boolean visible;

   // $VF: synthetic method
   public static Unit $r8$lambda$24Jyvw5cG56DXaEZtHzg1BR7HlA(GalaxySettingsView var0, int var1, JSONObject var2) {
      return draw$lambda$24(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7t5UmbvRX6ht05_Dlf8ye0H0Oms(GalaxySettingsView var0, String var1) {
      return drawRoot$lambda$15$lambda$14(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7vz8wjV3GlPyjAYrDhF9m27CQy8(GalaxySettingsView var0, Function0 var1, JSONObject var2, String var3) {
      return confirm$lambda$81(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8jGFi6JvDujnP8R4GFTMS4rLfMo(GalaxySettingsView var0, GalaxyRow var1, JSONObject var2, String var3) {
      return saveValue$lambda$66(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AfSOY20oSKlFizjsAj_I2UnITpg/* $VF was: $r8$lambda$AfSOY20oSKlFizjsAj_I2UnITpg*/(
      GalaxySettingsView var0, JSONObject var1, String var2
   ) {
      return reviewDraft$lambda$74(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$D7eB68vxvl37Qc0B0JjRiK2A7XU(GalaxyRow var0, GalaxySettingsView var1, ObjectRef var2, int var3) {
      return selectRow$lambda$46(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$DKvU0_UeE76fJH5mgabqfCxxilQ/* $VF was: $r8$lambda$DKvU0_UeE76fJH5mgabqfCxxilQ*/(GalaxyRow var0) {
      return draw$lambda$26(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FmLh8I0enIZ9cL_pWW_ZcCCKJoA/* $VF was: $r8$lambda$FmLh8I0enIZ9cL_pWW_ZcCCKJoA*/(
      GalaxySettingsView var0, GalaxySettingsView.Entry var1
   ) {
      return drawRoot$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$GV4vucs_TlSOaTtYWcAj0GNC3bE(GalaxyRow var0, GalaxySettingsView var1, CompoundButton var2, boolean var3) {
      switchRow$lambda$43(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GgWfY8bDT_c9a0BTkz6v2TRpMVI(GalaxySettingsView var0, JSONObject var1, String var2) {
      return resetDefault$lambda$69(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$HaKQKlkVT6sqJMJ37khE8RB_boA(GalaxySettingsView var0, GalaxySettingsView.Entry var1) {
      return draw$lambda$36(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$JIHNcoyCVDJI0roWeHPyvfzkg0E(GalaxySettingsView var0, String var1, DialogInterface var2, int var3) {
      review$lambda$79$lambda$76(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$K6Po5NtniICQVdiH7V5B6Qv6dL4(GalaxySettingsView var0, GalaxyRow var1, String var2) {
      return draw$lambda$33(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LLP2qfYEOJT0pDSfxCrVK1rUFKg(GalaxySettingsView var0, String var1, boolean var2, JSONObject var3, String var4) {
      return load$lambda$6(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LigAhwKpLIjtO30Rsl2eDBpMOZ4(GalaxySettingsView var0, JSONObject var1, String var2) {
      return load$lambda$5(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$MNb7vbbaIHPkhIZJsImmYL_M2Pw(GalaxySettingsView var0, DialogInterface var1) {
      reviewDraft$lambda$74$lambda$73(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Mlv9Hk9csSYcNXgt95thwwzh6t4(GalaxySettingsView var0, GalaxyRow var1, JSONObject var2, String var3) {
      return review$lambda$79(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MtWn3FafC5nTC__9EkEvsQmxCxY/* $VF was: $r8$lambda$MtWn3FafC5nTC__9EkEvsQmxCxY*/(GalaxyRow var0, GalaxySettingsView var1) {
      return defaultButton$lambda$40(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$QOik1pOoib476iIgkZXyZg_iOlI(GalaxySettingsView var0, View var1) {
      backBar$lambda$8$lambda$7(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$QZwOVId0eIZpndfLiYdcjguDQ2s(GalaxyRow var0, GalaxySettingsView var1, DialogInterface var2, int var3) {
      selectRow$lambda$48$lambda$47(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$RAjyorYbiGFj_nsFd27pDC9oA1k(boolean var0, Function0 var1, View var2) {
      linkCard$lambda$23(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$SXt_8KUhXzGXLoDZ_iNIsCl8x9U(GalaxySettingsView var0, DialogInterface var1) {
      review$lambda$79$lambda$78(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$U00vOgkKrS2A35t9mYYT1mFwLGI(GalaxySettingsView var0, GalaxyRow var1) {
      return draw$lambda$34(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$VBEl7FFNSTiXHIDCY5s_DcvYsQ4() {
      return confirm$lambda$80();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Zp8q4efaBYO1XSYQV_uhIJzJzHs/* $VF was: $r8$lambda$Zp8q4efaBYO1XSYQV_uhIJzJzHs*/(GalaxyRow var0, GalaxySettingsView var1) {
      return actionRow$lambda$61(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_kxR5zsZ2jN_EMNwf8ftPIYHTac/* $VF was: $r8$lambda$_kxR5zsZ2jN_EMNwf8ftPIYHTac*/(
      GalaxySettingsView var0, GalaxySettingsView.Entry var1
   ) {
      return drawRoot$lambda$16(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$bY7VjXH1JiJryu9vxk3ozORyhrk(GalaxySettingsView var0, int var1) {
      draw$lambda$38(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$hCYE9Yw9cMM3bPaQdgr_T1VcOHA(GalaxySettingsView var0, DialogInterface var1, int var2) {
      reviewDraft$lambda$74$lambda$72(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iTMF0PFpjAyNNDAieiaJLu48n6A(
      GalaxyRow var0, GalaxySettingsView var1, SeekBar var2, int var3, String var4, GalaxyRow.Bounds var5, int var6
   ) {
      return sliderRow$lambda$57(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$k42rSBvFeaFjqgeMKab3iQwSJs8(GalaxyRow var0, GalaxySettingsView var1) {
      return selectRow$lambda$48(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$k_I2bcaNEoOrDje1kX0Hon7kJHE(GalaxySettingsView var0, DialogInterface var1, int var2) {
      review$lambda$79$lambda$77(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lh0ho_749XAPB0pMUw1cvNcV2RM/* $VF was: $r8$lambda$lh0ho_749XAPB0pMUw1cvNcV2RM*/(GalaxySettingsView var0, String var1) {
      return drawRoot$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lsolHgBr5yQ_68KHPVpwtCjlNwg/* $VF was: $r8$lambda$lsolHgBr5yQ_68KHPVpwtCjlNwg*/(GalaxySettingsView var0, GalaxyRow var1) {
      return saveValue$lambda$66$lambda$65(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nJsy7v0Gi5hIPpfhfIBkleMrQ4c(
      GalaxyRow var0, GalaxySettingsView var1, SeekBar var2, String var3, GalaxyRow.Bounds var4, int var5
   ) {
      return sliderRow$lambda$55(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pJ5tDm96rE0MGycTkbKwlew4bpo(GalaxySettingsView var0, GalaxyRow var1) {
      return draw$lambda$35(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$rxUshCcqg9nQbgRJhZ7QK0Dc0A0(GalaxySettingsView var0, String var1, DialogInterface var2, int var3) {
      reviewDraft$lambda$74$lambda$71(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vRFaewUbSy_SBgYWpYONNr_9rQE/* $VF was: $r8$lambda$vRFaewUbSy_SBgYWpYONNr_9rQE*/(
      GalaxySettingsView var0, GalaxySettingsView.Entry var1
   ) {
      return draw$lambda$37(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xK05wWEwnpDsTfXlTdsRXTLEFK8(JSONObject var0, String var1) {
      return previewSound$lambda$67(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$y3sxJkNfUCcigolBr09_BU0Diqo/* $VF was: $r8$lambda$y3sxJkNfUCcigolBr09_BU0Diqo*/(GalaxySettingsView var0, GalaxyRow var1) {
      return drawRoot$lambda$19(var0, var1);
   }

   public GalaxySettingsView(
      Context var1,
      CarUi var2,
      GalaxySettingsView.Root var3,
      Function2<? super String, ? super String, Unit> var4,
      Function1<? super LinearLayout, Unit> var5,
      Function2<? super String, ? super LinearLayout, Unit> var6
   ) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "root");
      Intrinsics.checkNotNullParameter(var4, "openWeb");
      super(var1);
      this.ui = var2;
      this.root = var3;
      this.openWeb = var4;
      this.footer = var5;
      this.pageExtras = var6;
      this.g = Galaxy7.Companion.current();
      this.main = new Handler(Looper.getMainLooper());
      this.stack = new ArrayList<>();
      this.lastShown = "";
      this.scroll = new ScrollView(var1);
      LinearLayout var7 = new LinearLayout(var1);
      var7.setOrientation(1);
      var7.setPadding(this.ui.dp(10), this.ui.dp(4), this.ui.dp(24), this.ui.dp(60));
      this.col = var7;
      this.scroll.addView((View)this.col);
      this.addView((View)this.scroll, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      this.poll = new Runnable() {

         @Override
         public void run() {
            if (GalaxySettingsView.this.visible) {
               if (!GalaxySettingsView.this.busy && !GalaxySettingsView.this.touching) {
                  GalaxySettingsView.this.load(true);
               }

               GalaxySettingsView.this.main.postDelayed(this, 3000L);
            }
         }
      };
      this.titles = new HashMap<>();
   }

   // $VF: synthetic method
   public GalaxySettingsView(
      Context var1, CarUi var2, GalaxySettingsView.Root var3, Function2 var4, Function1 var5, Function2 var6, int var7, DefaultConstructorMarker var8
   ) {
      if ((var7 & 16) != 0) {
         var5 = null;
      }

      if ((var7 & 32) != 0) {
         var6 = null;
      }

      this(var1, var2, var3, var4, var5, var6);
   }

   private final void actionRow(GalaxyRow var1) {
      CarUi var6 = this.ui;
      String var5 = var1.getLabel();
      String var7 = this.desc(var1);
      CharSequence var4 = var1.getValue();
      CharSequence var3 = var4;
      if (StringsKt.isBlank(var4)) {
         var3 = null;
      }

      boolean var2 = true;
      var4 = CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull(new String[]{var7, var3}), "\n", null, null, 0, null, null, 62, null);
      var3 = var4;
      if (StringsKt.isBlank(var4)) {
         var3 = null;
      }

      LinearLayout var13 = (LinearLayout)CarUi.row$default(var6, var5, (String)var3, 0, this.icon(var1), this.tag(var1), 4, null).component1();
      if (var1.getAction()) {
         String var14 = var1.getRepairValue();
         String var12 = "Reset";
         String var9 = var12;
         if (!Intrinsics.areEqual(var14, "Reset")) {
            if (new Regex("reset|default", RegexOption.IGNORE_CASE).containsMatchIn(var1.getLabel())) {
               var9 = var12;
            } else {
               if (var1.getRepairValue().length() <= 0) {
                  var2 = false;
               }

               if (var2) {
                  String var10 = var1.getRepairValue();
                  var9 = "Set " + var10;
               } else {
                  var9 = "Run";
               }
            }
         }

         var13.addView((View)CarUi.button$default(this.ui, var9, false, new GalaxySettingsView$$ExternalSyntheticLambda33(var1, this), 2, null));
      }

      this.dim((View)var13, var1);
      this.col.addView((View)var13, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
   }

   private static final Unit actionRow$lambda$61(GalaxyRow var0, GalaxySettingsView var1) {
      if (var0.getEditable()) {
         var1.review(var0);
      }

      return Unit.INSTANCE;
   }

   private final void backBar() {
      int var2 = this.stack.size();
      byte var1;
      if (this.root.getPage() != null) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      if (var2 > var1) {
         String var6;
         if (this.stack.size() >= 2) {
            var6 = this.stack.get(this.stack.size() - 2);
            Intrinsics.checkNotNullExpressionValue(var6, "get(...)");
            var6 = this.titleOf(var6);
         } else {
            var6 = this.root.getTitle();
         }

         LinearLayout var4 = this.col;
         TextView var5 = new TextView(this.getContext());
         var5.setText("‹  " + var6);
         var5.setTextColor(CarUi.Companion.getACCENT());
         var5.setTextSize(18.0F);
         var5.setTypeface(Typeface.DEFAULT_BOLD);
         var5.setPadding(this.ui.dp(6), this.ui.dp(6), this.ui.dp(6), this.ui.dp(12));
         var5.setOnClickListener(new GalaxySettingsView$$ExternalSyntheticLambda5(this));
         var4.addView((View)var5);
      }
   }

   private static final void backBar$lambda$8$lambda$7(GalaxySettingsView var0, View var1) {
      var0.back();
   }

   private final void confirm(String var1, Function0<Unit> var2) {
      Galaxy7 var3 = this.g;
      if (var3 != null) {
         String var4 = this.getCurrent();
         if (var4 != null) {
            JSONObject var5 = new JSONObject().put("intent", var1).put("confirmed", true).put("page", var4);
            Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
            var3.post("/api/settings/confirm", var5, new GalaxySettingsView$$ExternalSyntheticLambda36(this, var2));
         }
      }
   }

   // $VF: synthetic method
   static void confirm$default(GalaxySettingsView var0, String var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = new GalaxySettingsView$$ExternalSyntheticLambda26();
      }

      var0.confirm(var1, (Function0<Unit>)var2);
   }

   private static final Unit confirm$lambda$80() {
      return Unit.INSTANCE;
   }

   private static final Unit confirm$lambda$81(GalaxySettingsView var0, Function0 var1, JSONObject var2, String var3) {
      boolean var5 = false;
      var0.busy = false;
      boolean var4 = var5;
      if (var2 != null) {
         var4 = var5;
         if (var2.optBoolean("saved")) {
            var4 = true;
         }
      }

      if (!var4) {
         if (var3 == null) {
            var3 = "Galaxy couldn't confirm the save - current values are shown";
         }

         var0.failed(var3);
         return Unit.INSTANCE;
      } else {
         var1.invoke();
         JSONObject var6 = var2.optJSONObject("page");
         if (var6 != null && Intrinsics.areEqual(var6.optString("page"), var0.getCurrent())) {
            var0.data = var6;
            var0.draw(var6);
         } else {
            var0.load(true);
         }

         return Unit.INSTANCE;
      }
   }

   private final void defaultButton(LinearLayout var1, GalaxyRow var2) {
      if (var2.getShowDefault()) {
         TextView var5 = CarUi.button$default(this.ui, "Default", false, new GalaxySettingsView$$ExternalSyntheticLambda25(var2, this), 2, null);
         var5.setMinWidth(this.ui.dp(100));
         var5.setTextSize(16.0F);
         View var4 = (View)var5;
         android.widget.LinearLayout.LayoutParams var3 = new android.widget.LinearLayout.LayoutParams(-2, -2);
         var3.rightMargin = this.ui.dp(10);
         Unit var6 = Unit.INSTANCE;
         var1.addView(var4, (LayoutParams)var3);
      }
   }

   private static final Unit defaultButton$lambda$40(GalaxyRow var0, GalaxySettingsView var1) {
      if (var0.getEditable()) {
         var1.resetDefault(var0);
      }

      return Unit.INSTANCE;
   }

   private final String desc(GalaxyRow var1) {
      CharSequence var2 = StringsKt.replace$default(var1.getShownReason(), "https://firestar.link/discord", "the StarPilot Discord", false, 4, null);
      CharSequence var3 = var2;
      if (StringsKt.isBlank(var2)) {
         var3 = null;
      }

      return (String)var3;
   }

   private final void dim(View var1, GalaxyRow var2) {
      float var3;
      if (var2.getEditable()) {
         var3 = 1.0F;
      } else {
         var3 = 0.45F;
      }

      var1.setAlpha(var3);
   }

   private final void draw(JSONObject var1) {
      List var9 = GalaxyRow.Companion.parse(var1);
      int var4 = this.vasmEditorRow(var1);
      if (var4 >= 0) {
         VasmEditorCard var7 = this.vasmCard;
         VasmEditorCard var6 = var7;
         if (var7 == null) {
            Context var21 = this.getContext();
            Intrinsics.checkNotNullExpressionValue(var21, "getContext(...)");
            var6 = new VasmEditorCard(var21, this.ui, new GalaxySettingsView$$ExternalSyntheticLambda9(this, var4));
            this.vasmCard = var6;
         }

         GalaxyRow var32 = CollectionsKt.getOrNull(var9, var4);
         boolean var5;
         if (var32 != null && var32.getAvailable()) {
            var5 = true;
         } else {
            var5 = false;
         }

         var6.update(var1, var5);
      }

      String var22 = var1.optString("title");
      String var33 = var1.optString("subtitle");
      boolean var20 = var1.optBoolean("vehicleIdentified");
      String var8 = CollectionsKt.joinToString$default(var9, "\n", null, null, 0, null, new GalaxySettingsView$$ExternalSyntheticLambda10(), 30, null);
      var22 = var22 + "|" + var33 + "|" + var20 + "|" + var8;
      if (!Intrinsics.areEqual(var22, this.lastShown)) {
         boolean var2;
         if (this.lastShown.length() > 0) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         if (var2) {
            var2 = this.scroll.getScrollY();
         } else {
            var2 = 0;
         }

         this.lastShown = var22;
         var33 = var1.optString("page");
         Map var10 = this.titles;
         CharSequence var36 = var1.optString("title");
         var22 = var36;
         if (StringsKt.isBlank(var36)) {
            Intrinsics.checkNotNull(var33);
            var22 = this.titleOf(var33);
         }

         var10.put(var33, var22);
         this.col.removeAllViews();
         this.backBar();
         if (this.stack.size() > 1 || this.root.getPage() == null) {
            LinearLayout var25 = this.col;
            TextView var37 = new TextView(this.getContext());
            Intrinsics.checkNotNull(var33);
            var37.setText(this.titleOf(var33));
            var37.setTextColor(CarUi.Companion.getTEXT());
            var37.setTextSize(24.0F);
            var37.setTypeface(Typeface.DEFAULT_BOLD);
            var37.setPadding(this.ui.dp(6), 0, 0, this.ui.dp(4));
            var25.addView((View)var37);
         }

         var22 = var1.optString("subtitle");
         Intrinsics.checkNotNull(var22);
         if (StringsKt.isBlank(var22)) {
            var22 = null;
         }

         if (var22 != null) {
            this.col.addView((View)this.ui.note(var22), (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
         }

         if (!var1.optBoolean("vehicleIdentified", true)) {
            this.col
               .addView(
                  (View)this.ui.note("The comma hasn't identified the car yet: settings for this car show once it has (turn the car on once)."),
                  (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null)
               );
         }

         if (Intrinsics.areEqual(this.root.getPage(), var33) && this.root.getNote() != null) {
            this.col.addView((View)this.ui.note(this.root.getNote()), (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
         }

         GalaxyRow.Companion var11 = GalaxyRow.Companion;
         Intrinsics.checkNotNull(var33);
         Iterable var12 = var11.visible(var33, var9);
         var var27 = new ArrayList();

         for (Object var44 : var12) {
            GalaxyRow var42 = (GalaxyRow)var44;
            boolean var3;
            if (var4 < 0 || var42.getIndex() != var4 && !Intrinsics.areEqual(var42.getLabel(), "Saved spot-monitor settings")) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               var27.add(var44);
            }
         }

         List var28 = var27;
         if (var4 >= 0) {
            VasmEditorCard var39 = this.vasmCard;
            if (var39 != null) {
               ViewParent var13 = var39.getParent();
               ViewGroup var14;
               if (var13 instanceof ViewGroup) {
                  var14 = (ViewGroup)var13;
               } else {
                  var14 = null;
               }

               if (var14 != null) {
                  var14.removeView((View)var39);
               }

               this.col.addView((View)var39, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
            }
         }

         if (var28.isEmpty()) {
            this.col.addView((View)this.ui.note("No settings here for this car."), (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
         }

         GalaxyVisuals var15 = GalaxyVisuals.INSTANCE;
         Context var40 = this.getContext();
         Intrinsics.checkNotNullExpressionValue(var40, "getContext(...)");
         Set var41 = var15.header(
            var40,
            this.ui,
            var33,
            var28,
            this.col,
            new GalaxySettingsView$$ExternalSyntheticLambda12(this),
            new GalaxySettingsView$$ExternalSyntheticLambda13(this)
         );
         Iterator var29 = var28.iterator();
         String var16 = var33;

         while (var29.hasNext()) {
            GalaxyRow var35 = (GalaxyRow)var29.next();
            if (!var41.contains(var35.getIndex())) {
               GalaxyRow.Control var43 = var35.getControl();
               switch (GalaxySettingsView.WhenMappings.$EnumSwitchMapping$0[var43.ordinal()]) {
                  case 1:
                     this.titles.put(var35.getPage(), var35.getLabel());
                     linkCard$default(
                        this,
                        var35.getLabel(),
                        var35.getValue(),
                        var35.getAvailable(),
                        false,
                        SettingIcons.INSTANCE.forRow(var16, var35.getLabel(), var35.getPage(), var35.getControl()),
                        new GalaxySettingsView$$ExternalSyntheticLambda14(this, var35),
                        8,
                        null
                     );
                     break;
                  case 2:
                     this.switchRow(var35);
                     break;
                  case 3:
                     this.selectRow(var35);
                     break;
                  case 4:
                     this.sliderRow(var35);
                     break;
                  case 5:
                     this.actionRow(var35);
                     break;
                  case 6:
                     this.readoutRow(var35);
                     break;
                  default:
                     throw new NoWhenBranchMatchedException();
               }
            }
         }

         Function2 var30 = this.pageExtras;
         if (var30 != null) {
            var30.invoke(var16, this.col);
         }

         if (Intrinsics.areEqual(var16, this.root.getPage()) && this.stack.size() == 1) {
            for (GalaxySettingsView.Entry var17 : this.root.getEntries()) {
               if (var17 instanceof GalaxySettingsView.Entry.Web) {
                  this.linkCard(
                     ((GalaxySettingsView.Entry.Web)var17).getLabel(),
                     ((GalaxySettingsView.Entry.Web)var17).getDesc(),
                     true,
                     true,
                     this.webIcon(((GalaxySettingsView.Entry.Web)var17).getPath()),
                     new GalaxySettingsView$$ExternalSyntheticLambda15(this, var17)
                  );
               } else if (var17 instanceof GalaxySettingsView.Entry.Page) {
                  this.titles.put(((GalaxySettingsView.Entry.Page)var17).getPage(), ((GalaxySettingsView.Entry.Page)var17).getLabel());
                  linkCard$default(
                     this,
                     ((GalaxySettingsView.Entry.Page)var17).getLabel(),
                     ((GalaxySettingsView.Entry.Page)var17).getDesc(),
                     true,
                     false,
                     SettingIcons.INSTANCE.forPage(((GalaxySettingsView.Entry.Page)var17).getPage()),
                     new GalaxySettingsView$$ExternalSyntheticLambda16(this, var17),
                     8,
                     null
                  );
               }
            }

            Function1 var18 = this.footer;
            if (var18 != null) {
               var18.invoke(this.col);
            }
         }

         if (var2 > 0) {
            this.scroll.post(new GalaxySettingsView$$ExternalSyntheticLambda17(this, var2));
         }
      }
   }

   private static final Unit draw$lambda$24(GalaxySettingsView var0, int var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "d");
      var0.reviewDraft(var1, var2);
      return Unit.INSTANCE;
   }

   private static final CharSequence draw$lambda$26(GalaxyRow var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      String var6 = var0.getLabel();
      String var7 = var0.getValue();
      boolean var5 = var0.getAvailable();
      String var8 = var0.getReason();
      List var9 = var0.getChoices();
      double var1 = var0.getMinimum();
      double var3 = var0.getMaximum();
      String var10 = var0.getDefaultValue();
      return var6 + "|" + var7 + "|" + var5 + "|" + var8 + "|" + var9 + "|" + var1 + "|" + var3 + "|" + var10;
   }

   private static final Unit draw$lambda$33(GalaxySettingsView var0, GalaxyRow var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "r");
      Intrinsics.checkNotNullParameter(var2, "v");
      if (!var0.busy) {
         var0.saveValue(var1, var2);
      }

      return Unit.INSTANCE;
   }

   private static final Unit draw$lambda$34(GalaxySettingsView var0, GalaxyRow var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (!var0.busy) {
         var0.resetDefault(var1);
      }

      return Unit.INSTANCE;
   }

   private static final Unit draw$lambda$35(GalaxySettingsView var0, GalaxyRow var1) {
      var0.open(var1.getPage());
      return Unit.INSTANCE;
   }

   private static final Unit draw$lambda$36(GalaxySettingsView var0, GalaxySettingsView.Entry var1) {
      var0.openWeb.invoke(((GalaxySettingsView.Entry.Web)var1).getPath(), ((GalaxySettingsView.Entry.Web)var1).getLabel());
      return Unit.INSTANCE;
   }

   private static final Unit draw$lambda$37(GalaxySettingsView var0, GalaxySettingsView.Entry var1) {
      var0.open(((GalaxySettingsView.Entry.Page)var1).getPage());
      return Unit.INSTANCE;
   }

   private static final void draw$lambda$38(GalaxySettingsView var0, int var1) {
      var0.scroll.scrollTo(0, var1);
   }

   private final void drawRoot(String var1) {
      String var5;
      String var17;
      label180: {
         JSONObject var4 = this.hub;
         var5 = "";
         if (var4 != null) {
            JSONArray var16 = var4.optJSONArray("rows");
            if (var16 != null) {
               String var6 = var16.toString();
               var17 = var6;
               if (var6 != null) {
                  break label180;
               }
            }
         }

         var17 = "";
      }

      if (var1 != null) {
         var5 = var1;
      }

      var17 = "root|" + var17 + var5;
      if (!Intrinsics.areEqual(var17, this.lastShown)) {
         this.lastShown = var17;
         this.col.removeAllViews();
         if (this.root.getNote() != null) {
            this.col.addView((View)this.ui.note(this.root.getNote()), (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
         }

         if (var1 != null) {
            this.col.addView((View)this.ui.note(var1), (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
         }

         var var19 = this.root.getEntries();
         boolean var12;
         if (var19 instanceof Collection && ((Collection)var19).isEmpty()) {
            var12 = 0;
         } else {
            Iterator var25 = var19.iterator();

            while (true) {
               if (!var25.hasNext()) {
                  var12 = 0;
                  break;
               }

               GalaxySettingsView.Entry var20 = (GalaxySettingsView.Entry)var25.next();
               if (var20 instanceof GalaxySettingsView.Entry.Hub
                  && Intrinsics.areEqual(
                     CollectionsKt.toSet(((GalaxySettingsView.Entry.Hub)var20).getPages()),
                     SetsKt.setOf(new String[]{"traffic", "aggressive", "standard", "relaxed"})
                  )) {
                  var12 = (boolean)1;
               } else {
                  var12 = (boolean)0;
               }

               if (var12) {
                  var12 = 1;
                  break;
               }
            }
         }

         if (var12) {
            GalaxyVisuals var21 = GalaxyVisuals.INSTANCE;
            Context var26 = this.getContext();
            Intrinsics.checkNotNullExpressionValue(var26, "getContext(...)");
            var21.personalities(var26, this.ui, this.col, new GalaxySettingsView$$ExternalSyntheticLambda27(this));
         }

         label150: {
            JSONObject var22 = this.hub;
            if (var22 != null) {
               List var27 = GalaxyRow.Companion.parse(var22);
               var23 = var27;
               if (var27 != null) {
                  break label150;
               }
            }

            var23 = CollectionsKt.emptyList();
         }

         for (GalaxyRow var33 : var23) {
            if (var33.getPage().length() > 0) {
               var12 = (boolean)1;
            } else {
               var12 = (boolean)0;
            }

            if (var12) {
               this.titles.put(var33.getPage(), var33.getLabel());
            }
         }

         Iterator var7 = this.root.getEntries().iterator();
         var12 = 0;

         while (var7.hasNext()) {
            GalaxySettingsView.Entry var29 = (GalaxySettingsView.Entry)var7.next();
            if (var29 instanceof GalaxySettingsView.Entry.Hub) {
               for (String var40 : ((GalaxySettingsView.Entry.Hub)var29).getPages()) {
                  Iterator var41 = var23.iterator();

                  Object var36;
                  do {
                     if (!var41.hasNext()) {
                        var36 = null;
                        break;
                     }

                     var36 = var41.next();
                  } while (!Intrinsics.areEqual(((GalaxyRow)var36).getPage(), var40));

                  var36 = (GalaxyRow)var36;
                  if (var36 != null) {
                     linkCard$default(
                        this,
                        ((GalaxyRow)var36).getLabel(),
                        ((GalaxyRow)var36).getValue(),
                        ((GalaxyRow)var36).getAvailable(),
                        false,
                        SettingIcons.INSTANCE.forPage(var40),
                        new GalaxySettingsView$$ExternalSyntheticLambda28(this, var40),
                        8,
                        null
                     );
                     var12++;
                  }
               }
            } else if (var29 instanceof GalaxySettingsView.Entry.Page) {
               this.titles.put(((GalaxySettingsView.Entry.Page)var29).getPage(), ((GalaxySettingsView.Entry.Page)var29).getLabel());
               linkCard$default(
                  this,
                  ((GalaxySettingsView.Entry.Page)var29).getLabel(),
                  ((GalaxySettingsView.Entry.Page)var29).getDesc(),
                  true,
                  false,
                  SettingIcons.INSTANCE.forPage(((GalaxySettingsView.Entry.Page)var29).getPage()),
                  new GalaxySettingsView$$ExternalSyntheticLambda29(this, var29),
                  8,
                  null
               );
               var12++;
            } else if (var29 instanceof GalaxySettingsView.Entry.Web) {
               this.linkCard(
                  ((GalaxySettingsView.Entry.Web)var29).getLabel(),
                  ((GalaxySettingsView.Entry.Web)var29).getDesc(),
                  true,
                  true,
                  this.webIcon(((GalaxySettingsView.Entry.Web)var29).getPath()),
                  new GalaxySettingsView$$ExternalSyntheticLambda30(this, var29)
               );
               var12++;
            } else {
               if (!(var29 instanceof GalaxySettingsView.Entry.Unplaced)) {
                  throw new NoWhenBranchMatchedException();
               }

               Iterable var8 = var23;
               var var34 = new ArrayList();

               for (Object var9 : var8) {
                  GalaxyRow var10 = (GalaxyRow)var9;
                  boolean var3;
                  if (var10.getPage().length() > 0) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  if (var3 && !((GalaxySettingsView.Entry.Unplaced)var29).getPlaced().contains(var10.getPage())) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  if (var3) {
                     var34.add(var9);
                  }
               }

               for (GalaxyRow var35 : var34) {
                  linkCard$default(
                     this,
                     var35.getLabel(),
                     var35.getValue(),
                     var35.getAvailable(),
                     false,
                     SettingIcons.INSTANCE.forPage(var35.getPage()),
                     new GalaxySettingsView$$ExternalSyntheticLambda31(this, var35),
                     8,
                     null
                  );
                  var12++;
               }
            }
         }

         if (var12 == 0 && var1 == null) {
            LinearLayout var24 = this.col;
            CarUi var32 = this.ui;
            if (this.hub == null) {
               var1 = "Loading…";
            } else {
               var1 = "Nothing here for this car and StarPilot version.";
            }

            var24.addView((View)var32.note(var1), (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
         }
      }
   }

   private static final Unit drawRoot$lambda$11(GalaxySettingsView var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0.open(var1);
      return Unit.INSTANCE;
   }

   private static final Unit drawRoot$lambda$15$lambda$14(GalaxySettingsView var0, String var1) {
      var0.open(var1);
      return Unit.INSTANCE;
   }

   private static final Unit drawRoot$lambda$16(GalaxySettingsView var0, GalaxySettingsView.Entry var1) {
      var0.open(((GalaxySettingsView.Entry.Page)var1).getPage());
      return Unit.INSTANCE;
   }

   private static final Unit drawRoot$lambda$17(GalaxySettingsView var0, GalaxySettingsView.Entry var1) {
      var0.openWeb.invoke(((GalaxySettingsView.Entry.Web)var1).getPath(), ((GalaxySettingsView.Entry.Web)var1).getLabel());
      return Unit.INSTANCE;
   }

   private static final Unit drawRoot$lambda$19(GalaxySettingsView var0, GalaxyRow var1) {
      var0.open(var1.getPage());
      return Unit.INSTANCE;
   }

   private final void failed(String var1) {
      this.busy = false;
      Toast.makeText(this.getContext(), var1, 1).show();
      this.lastShown = "";
      this.load(true);
   }

   private final String getCurrent() {
      return CollectionsKt.lastOrNull(this.stack);
   }

   private final View icon(GalaxyRow var1) {
      SettingIcons var4 = SettingIcons.INSTANCE;
      String var3 = this.getCurrent();
      String var2 = var3;
      if (var3 == null) {
         var2 = "";
      }

      SettingIcons.Icon var6 = var4.forRow(var2, var1.getLabel(), var1.getPage(), var1.getControl());
      SvIcon.State var5;
      if (!var1.getAvailable()) {
         var5 = SvIcon.State.NA;
      } else if (var1.getControl() == GalaxyRow.Control.SWITCH && !Intrinsics.areEqual(var1.getValue(), "On")) {
         var5 = SvIcon.State.OFF;
      } else {
         var5 = SvIcon.State.ON;
      }

      SvIcon var8 = SvIcon.INSTANCE;
      Context var7 = this.getContext();
      Intrinsics.checkNotNullExpressionValue(var7, "getContext(...)");
      return var8.chip(var7, var6.getName(), var6.getGroup(), 46, var5);
   }

   private final Object jsonValue(GalaxyRow var1, String var2) {
      if (var1.getControl() == GalaxyRow.Control.SLIDER && !Intrinsics.areEqual(var2, "Auto")) {
         Long var3 = StringsKt.toLongOrNull(var2);
         if (var3 != null) {
            return var3;
         }

         Double var5 = StringsKt.toDoubleOrNull(var2);
         if (var5 != null) {
            return var5;
         }
      }

      return var2;
   }

   private final void linkCard(String var1, String var2, boolean var3, boolean var4, SettingIcons.Icon var5, Function0<Unit> var6) {
      CarUi var9 = this.ui;
      CharSequence var8 = var2;
      CharSequence var14 = var8;
      if (StringsKt.isBlank(var8)) {
         var14 = null;
      }

      String var20 = (String)var14;
      View var16;
      if (var5 != null) {
         SvIcon var10 = SvIcon.INSTANCE;
         Context var11 = this.getContext();
         Intrinsics.checkNotNullExpressionValue(var11, "getContext(...)");
         String var12 = var5.getName();
         String var18 = var5.getGroup();
         SvIcon.State var15;
         if (var3) {
            var15 = SvIcon.State.ON;
         } else {
            var15 = SvIcon.State.NA;
         }

         var16 = var10.chip(var11, var12, var18, 46, var15);
      } else {
         var16 = null;
      }

      LinearLayout var17 = (LinearLayout)CarUi.row$default(var9, var1, var20, 0, var16, null, 20, null).component1();
      TextView var19 = new TextView(this.getContext());
      if (var4) {
         var1 = "↗";
      } else {
         var1 = "›";
      }

      var19.setText(var1);
      var19.setTextSize(30.0F);
      var19.setTextColor(CarUi.Companion.getDIM());
      var19.setPadding(this.ui.dp(14), 0, this.ui.dp(6), 0);
      var17.addView((View)var19);
      float var7;
      if (var3) {
         var7 = 1.0F;
      } else {
         var7 = 0.45F;
      }

      var17.setAlpha(var7);
      var17.setClickable(true);
      var17.setOnClickListener(new GalaxySettingsView$$ExternalSyntheticLambda11(var3, var6));
      this.col.addView((View)var17, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
   }

   // $VF: synthetic method
   static void linkCard$default(
      GalaxySettingsView var0, String var1, String var2, boolean var3, boolean var4, SettingIcons.Icon var5, Function0 var6, int var7, Object var8
   ) {
      if ((var7 & 8) != 0) {
         var4 = false;
      }

      if ((var7 & 16) != 0) {
         var5 = null;
      }

      var0.linkCard(var1, var2, var3, var4, var5, var6);
   }

   private static final void linkCard$lambda$23(boolean var0, Function0 var1, View var2) {
      if (var0) {
         var1.invoke();
      }
   }

   private final void load(boolean var1) {
      Galaxy7 var5 = this.g;
      if (var5 == null) {
         this.message("Not connected to the comma. Connect first, then come back.");
      } else {
         String var6 = this.getCurrent();
         String var4 = null;
         if (var6 != null) {
            if (!var1) {
               JSONObject var7 = this.data;
               if (var7 != null) {
                  var4 = var7.optString("page");
               }

               if (!Intrinsics.areEqual(var4, var6)) {
                  this.message("Loading…");
               }
            }

            var4 = Uri.encode(var6);
            var5.get("/api/settings/pages/" + var4, new GalaxySettingsView$$ExternalSyntheticLambda32(this, var6, var1));
         } else {
            var var10 = this.root.getEntries();
            var1 = var10 instanceof Collection;
            boolean var3 = false;
            boolean var9;
            if (var1 && ((Collection)var10).isEmpty()) {
               var9 = var3;
            } else {
               Iterator var11 = var10.iterator();

               while (true) {
                  if (!var11.hasNext()) {
                     var9 = var3;
                     break;
                  }

                  GalaxySettingsView.Entry var13 = (GalaxySettingsView.Entry)var11.next();
                  if (!(var13 instanceof GalaxySettingsView.Entry.Hub) && !(var13 instanceof GalaxySettingsView.Entry.Unplaced)) {
                     var9 = false;
                  } else {
                     var9 = true;
                  }

                  if (var9) {
                     var9 = true;
                     break;
                  }
               }
            }

            if (var9) {
               var5.get("/api/settings/pages/hub", new GalaxySettingsView$$ExternalSyntheticLambda22(this));
            } else {
               this.drawRoot(null);
            }
         }
      }
   }

   private static final Unit load$lambda$5(GalaxySettingsView var0, JSONObject var1, String var2) {
      if (var0.getCurrent() != null) {
         return Unit.INSTANCE;
      } else {
         if (var1 != null) {
            var0.hub = var1;
         }

         boolean var3;
         if (var1 == null && var0.hub == null) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (!var3) {
            var2 = null;
         }

         var0.drawRoot(var2);
         return Unit.INSTANCE;
      }
   }

   private static final Unit load$lambda$6(GalaxySettingsView var0, String var1, boolean var2, JSONObject var3, String var4) {
      if (!Intrinsics.areEqual(var0.getCurrent(), var1)) {
         return Unit.INSTANCE;
      } else if (var3 != null) {
         var0.data = var3;
         var0.draw(var3);
         return Unit.INSTANCE;
      } else {
         if (!var2 || var0.data == null) {
            if (var4 == null) {
               var1 = "Galaxy didn't answer";
            } else {
               var1 = var4;
            }

            var0.message(var1);
         }

         return Unit.INSTANCE;
      }
   }

   private final void message(String var1) {
      this.lastShown = "";
      this.col.removeAllViews();
      this.backBar();
      this.col.addView((View)this.ui.note(var1), (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
   }

   private final void open(String var1) {
      if (!this.busy) {
         this.stack.add(var1);
         this.data = null;
         this.lastShown = "";
         this.load(false);
         this.scroll.scrollTo(0, 0);
      }
   }

   private final void previewSound(GalaxyRow var1) {
      boolean var2;
      if (var1.getPreviewSound().length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2) {
         Galaxy7 var3 = this.g;
         if (var3 != null) {
            JSONObject var4 = new JSONObject().put("key", var1.getPreviewSound());
            Intrinsics.checkNotNullExpressionValue(var4, "put(...)");
            var3.post("/api/sounds/preview", var4, new GalaxySettingsView$$ExternalSyntheticLambda18());
         }
      }
   }

   private static final Unit previewSound$lambda$67(JSONObject var0, String var1) {
      return Unit.INSTANCE;
   }

   private final void readoutRow(GalaxyRow var1) {
      if (!var1.getHeading() && (!StringsKt.isBlank(var1.getValue()) || !StringsKt.isBlank(var1.getReason()))) {
         LinearLayout var2 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), this.desc(var1), 0, this.icon(var1), this.tag(var1), 4, null)
            .component1();
         if (!StringsKt.isBlank(var1.getValue())) {
            TextView var3 = new TextView(this.getContext());
            var3.setText(var1.withUnit(GalaxyRow.shown$default(var1, null, 1, null)));
            var3.setTextColor(CarUi.Companion.getDIM());
            var3.setTextSize(18.0F);
            var3.setPadding(this.ui.dp(14), 0, this.ui.dp(6), 0);
            var3.setMaxWidth(this.ui.dp(360));
            var2.addView((View)var3);
         }

         this.col.addView((View)var2, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
      } else {
         this.col.addView((View)this.ui.header(var1.getLabel()));
      }
   }

   private final void resetDefault(GalaxyRow var1) {
      Galaxy7 var2 = this.g;
      if (var2 != null) {
         String var3 = this.viewId();
         if (var3 != null) {
            this.busy = true;
            JSONObject var4 = new JSONObject().put("view", var3).put("row", var1.getIndex());
            Intrinsics.checkNotNullExpressionValue(var4, "put(...)");
            var2.post("/api/settings/reset-default", var4, new GalaxySettingsView$$ExternalSyntheticLambda24(this));
         }
      }
   }

   private static final Unit resetDefault$lambda$69(GalaxySettingsView var0, JSONObject var1, String var2) {
      label24: {
         if (var1 != null) {
            String var4 = var1.optString("intent");
            if (var4 != null) {
               CharSequence var3 = var4;
               CharSequence var6 = var3;
               if (StringsKt.isBlank(var3)) {
                  var6 = null;
               }

               var5 = (String)var6;
               break label24;
            }
         }

         var5 = null;
      }

      if (var5 == null) {
         if (var2 == null) {
            var2 = "Galaxy didn't accept that";
         }

         var0.failed(var2);
         return Unit.INSTANCE;
      } else {
         confirm$default(var0, var5, null, 2, null);
         return Unit.INSTANCE;
      }
   }

   private final void review(GalaxyRow var1) {
      Galaxy7 var2 = this.g;
      if (var2 != null) {
         String var3 = this.viewId();
         if (var3 != null) {
            this.busy = true;
            JSONObject var4 = new JSONObject().put("view", var3).put("row", var1.getIndex()).put("direction", 1 ^ var1.getConfirm());
            Intrinsics.checkNotNullExpressionValue(var4, "put(...)");
            var2.post("/api/settings/preview", var4, new GalaxySettingsView$$ExternalSyntheticLambda34(this, var1));
         }
      }
   }

   private static final Unit review$lambda$79(GalaxySettingsView var0, GalaxyRow var1, JSONObject var2, String var3) {
      Object var6 = null;
      Object var5 = null;
      String var4 = (String)var6;
      if (var2 != null) {
         String var7 = var2.optString("intent");
         var4 = (String)var6;
         if (var7 != null) {
            var4 = var7;
            if (StringsKt.isBlank(var4)) {
               var4 = (String)var5;
            }

            var4 = var4;
         }
      }

      if (var4 == null) {
         if (var3 == null) {
            var3 = "Galaxy didn't accept that";
         }

         var0.failed(var3);
         return Unit.INSTANCE;
      } else {
         Builder var9 = new Builder(var0.getContext()).setTitle("Confirm");
         String var8 = var1.getLabel();
         var9.setMessage(var2.optString("question", "Apply " + var8 + "?"))
            .setPositiveButton("Confirm", new GalaxySettingsView$$ExternalSyntheticLambda37(var0, var4))
            .setNegativeButton("Cancel", new GalaxySettingsView$$ExternalSyntheticLambda38(var0))
            .setOnCancelListener(new GalaxySettingsView$$ExternalSyntheticLambda1(var0))
            .show();
         return Unit.INSTANCE;
      }
   }

   private static final void review$lambda$79$lambda$76(GalaxySettingsView var0, String var1, DialogInterface var2, int var3) {
      confirm$default(var0, var1, null, 2, null);
   }

   private static final void review$lambda$79$lambda$77(GalaxySettingsView var0, DialogInterface var1, int var2) {
      var0.busy = false;
      var0.load(true);
   }

   private static final void review$lambda$79$lambda$78(GalaxySettingsView var0, DialogInterface var1) {
      var0.busy = false;
      var0.load(true);
   }

   private final void reviewDraft(int var1, JSONObject var2) {
      Galaxy7 var3 = this.g;
      if (var3 != null) {
         String var4 = this.viewId();
         if (var4 != null) {
            if (!this.busy) {
               this.busy = true;
               var2 = new JSONObject().put("view", var4).put("row", var1).put("direction", 0).put("draft", var2);
               Intrinsics.checkNotNullExpressionValue(var2, "put(...)");
               var3.post("/api/settings/preview", var2, new GalaxySettingsView$$ExternalSyntheticLambda35(this));
            }
         }
      }
   }

   private static final Unit reviewDraft$lambda$74(GalaxySettingsView var0, JSONObject var1, String var2) {
      Object var5 = null;
      Object var4 = null;
      String var3 = (String)var5;
      if (var1 != null) {
         String var6 = var1.optString("intent");
         var3 = (String)var5;
         if (var6 != null) {
            var3 = var6;
            if (StringsKt.isBlank(var3)) {
               var3 = (String)var4;
            }

            var3 = var3;
         }
      }

      if (var3 == null) {
         if (var2 == null) {
            var2 = "Galaxy didn't accept those regions";
         }

         var0.failed(var2);
         return Unit.INSTANCE;
      } else {
         new Builder(var0.getContext())
            .setTitle("Save changes?")
            .setMessage(var1.optString("question", "Save the camera window regions?"))
            .setPositiveButton("Save", new GalaxySettingsView$$ExternalSyntheticLambda2(var0, var3))
            .setNegativeButton("Cancel", new GalaxySettingsView$$ExternalSyntheticLambda3(var0))
            .setOnCancelListener(new GalaxySettingsView$$ExternalSyntheticLambda4(var0))
            .show();
         return Unit.INSTANCE;
      }
   }

   private static final void reviewDraft$lambda$74$lambda$71(GalaxySettingsView var0, String var1, DialogInterface var2, int var3) {
      confirm$default(var0, var1, null, 2, null);
   }

   private static final void reviewDraft$lambda$74$lambda$72(GalaxySettingsView var0, DialogInterface var1, int var2) {
      var0.busy = false;
      var0.load(true);
   }

   private static final void reviewDraft$lambda$74$lambda$73(GalaxySettingsView var0, DialogInterface var1) {
      var0.busy = false;
      var0.load(true);
   }

   private final void saveValue(GalaxyRow var1, String var2) {
      Galaxy7 var3 = this.g;
      if (var3 != null) {
         String var4 = this.viewId();
         if (var4 != null) {
            this.busy = true;
            JSONObject var5 = new JSONObject().put("view", var4).put("row", var1.getIndex()).put("value", this.jsonValue(var1, var2));
            Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
            var3.post("/api/settings/preview", var5, new GalaxySettingsView$$ExternalSyntheticLambda19(this, var1));
         }
      }
   }

   private static final Unit saveValue$lambda$66(GalaxySettingsView var0, GalaxyRow var1, JSONObject var2, String var3) {
      Object var6 = null;
      Object var5 = null;
      String var4 = (String)var6;
      if (var2 != null) {
         String var7 = var2.optString("intent");
         var4 = (String)var6;
         if (var7 != null) {
            var var8 = var7;
            if (StringsKt.isBlank(var8)) {
               var8 = (String)var5;
            }

            var4 = var8;
         }
      }

      if (var4 == null) {
         if (var3 == null) {
            var3 = "Galaxy didn't accept that";
         }

         var0.failed(var3);
         return Unit.INSTANCE;
      } else {
         var0.confirm(var4, new GalaxySettingsView$$ExternalSyntheticLambda23(var0, var1));
         return Unit.INSTANCE;
      }
   }

   private static final Unit saveValue$lambda$66$lambda$65(GalaxySettingsView var0, GalaxyRow var1) {
      var0.previewSound(var1);
      return Unit.INSTANCE;
   }

   private final void selectRow(GalaxyRow var1) {
      int var2 = var1.getChoices().size();
      boolean var7;
      if (2 <= var2 && var2 < 5) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      label34: {
         if (var7) {
            Iterator var3 = var1.getChoices().iterator();
            var7 = 0;

            while (var3.hasNext()) {
               String var4 = (String)var3.next();
               var7 += var4.length();
            }

            if (var7 <= 40) {
               var7 = (boolean)1;
               break label34;
            }
         }

         var7 = (boolean)0;
      }

      if (var7) {
         LinearLayout var15 = new LinearLayout(this.getContext());
         var15.setOrientation(1);
         var15.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 0, null, 6, null));
         var15.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
         LinearLayout var11 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), this.desc(var1), 0, this.icon(var1), this.tag(var1), 4, null)
            .component1();
         var11.setBackground(null);
         var11.setPadding(0, 0, 0, 0);
         var11.setMinimumHeight(0);
         this.defaultButton(var11, var1);
         var15.addView((View)var11);
         ObjectRef var12 = new ObjectRef();
         Pair var6 = this.ui.chips(var1.getChoices(), new GalaxySettingsView$$ExternalSyntheticLambda20(var1, this, var12));
         LinearLayout var5 = (LinearLayout)var6.component1();
         var12.element = (T)((Function1)var6.component2());
         var5.setPadding(0, this.ui.dp(10), 0, 0);
         var15.addView((View)var5);
         Function1 var13;
         if (var12.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            var13 = null;
         } else {
            var13 = (Function1)var12.element;
         }

         var13.invoke(var1.getChoices().indexOf(var1.getValue()));
         this.dim((View)var15, var1);
         this.col.addView((View)var15, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
      } else {
         LinearLayout var10 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), this.desc(var1), 0, this.icon(var1), this.tag(var1), 4, null)
            .component1();
         this.defaultButton(var10, var1);
         TextView var14 = CarUi.button$default(this.ui, var1.getValue(), false, new GalaxySettingsView$$ExternalSyntheticLambda21(var1, this), 2, null);
         var14.setMinWidth(this.ui.dp(200));
         var10.addView((View)var14);
         this.dim((View)var10, var1);
         this.col.addView((View)var10, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
      }
   }

   private static final Unit selectRow$lambda$46(GalaxyRow var0, GalaxySettingsView var1, ObjectRef var2, int var3) {
      if (var0.getEditable() && !var1.busy && !Intrinsics.areEqual(var0.getChoices().get(var3), var0.getValue())) {
         Function1 var4;
         if (var2.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            var4 = null;
         } else {
            var4 = (Function1)var2.element;
         }

         var4.invoke(var3);
         var1.saveValue(var0, var0.getChoices().get(var3));
      }

      return Unit.INSTANCE;
   }

   private static final Unit selectRow$lambda$48(GalaxyRow var0, GalaxySettingsView var1) {
      if (var0.getEditable() && !var1.busy) {
         new Builder(var1.getContext())
            .setTitle(var0.getLabel())
            .setSingleChoiceItems(
               var0.getChoices().toArray(new String[0]),
               var0.getChoices().indexOf(var0.getValue()),
               new GalaxySettingsView$$ExternalSyntheticLambda0(var0, var1)
            )
            .setNegativeButton("Cancel", null)
            .show();
         return Unit.INSTANCE;
      } else {
         return Unit.INSTANCE;
      }
   }

   private static final void selectRow$lambda$48$lambda$47(GalaxyRow var0, GalaxySettingsView var1, DialogInterface var2, int var3) {
      var2.dismiss();
      if (!Intrinsics.areEqual(var0.getChoices().get(var3), var0.getValue())) {
         var1.saveValue(var0, var0.getChoices().get(var3));
      }
   }

   private final void sliderRow(GalaxyRow var1) {
      GalaxyRow.Bounds var7 = var1.bounds();
      if (var7 == null) {
         this.readoutRow(var1);
      } else {
         LinearLayout var6 = new LinearLayout(this.getContext());
         var6.setOrientation(1);
         var6.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 0, null, 6, null));
         var6.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(10));
         LinearLayout var4 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), this.desc(var1), 0, this.icon(var1), this.tag(var1), 4, null)
            .component1();
         var4.setBackground(null);
         var4.setPadding(0, 0, 0, 0);
         var4.setMinimumHeight(0);
         TextView var8 = new TextView(this.getContext());
         var8.setTextSize(22.0F);
         var8.setTextColor(CarUi.Companion.getTEXT());
         var8.setTypeface(Typeface.DEFAULT_BOLD);
         var8.setGravity(8388613);
         var8.setPadding(this.ui.dp(12), 0, this.ui.dp(4), 0);
         this.defaultButton(var4, var1);
         var4.addView((View)var8);
         var6.addView((View)var4);
         int var2 = (int)Math.round((var7.getMax() - var7.getMin()) / var7.getStep());
         int var3 = var2 + var7.getAuto();
         String var5 = GalaxyRow.shown$default(var1, null, 1, null);
         var8.setText(sliderRow$label(var1, var5));
         SeekBar var13 = new SeekBar(this.getContext());
         var13.setMax(var3);
         var13.setProgress(sliderRow$posOf(var3, var7, var2, var5));
         var13.setEnabled(var1.getEditable());
         var13.setMinimumHeight(this.ui.dp(48));
         var13.setThumbTintList(ColorStateList.valueOf(-1));
         var13.setProgressTintList(ColorStateList.valueOf(CarUi.Companion.getACCENT()));
         var13.setOnSeekBarChangeListener(new OnSeekBarChangeListener(var8, var7, var2, var1, this, var13, var5) {
            final GalaxyRow.Bounds $b;
            final SeekBar $bar;
            final GalaxyRow $r;
            final String $shownNow;
            final int $steps;
            final TextView $valueTv;
            final GalaxySettingsView this$0;

            {
               this.$valueTv = var1;
               this.$b = var2x;
               this.$steps = var3x;
               this.$r = var4x;
               this.this$0 = var5x;
               this.$bar = var6x;
               this.$shownNow = var7x;
            }

            public void onProgressChanged(SeekBar var1, int var2x, boolean var3x) {
               this.$valueTv.setText(GalaxySettingsView.sliderRow$label(this.$r, GalaxySettingsView.sliderRow$valueAt(this.$b, this.$steps, var2x)));
            }

            public void onStartTrackingTouch(SeekBar var1) {
               this.this$0.touching = true;
            }

            public void onStopTrackingTouch(SeekBar var1) {
               this.this$0.touching = false;
               GalaxySettingsView.sliderRow$commit(this.$shownNow, this.$r, this.this$0, this.$b, this.$steps, this.$bar.getProgress());
            }
         });
         LinearLayout var11 = new LinearLayout(this.getContext());
         var11.setOrientation(0);
         var11.setGravity(16);
         var11.setPadding(0, this.ui.dp(8), 0, 0);
         var11.addView((View)this.ui.stepper("−", new GalaxySettingsView$$ExternalSyntheticLambda7(var1, this, var13, var5, var7, var2)));
         View var9 = (View)var13;
         android.widget.LinearLayout.LayoutParams var10 = new android.widget.LinearLayout.LayoutParams(0, this.ui.dp(48), 1.0F);
         var10.leftMargin = this.ui.dp(8);
         var10.rightMargin = this.ui.dp(8);
         Unit var18 = Unit.INSTANCE;
         var11.addView(var9, (LayoutParams)var10);
         var11.addView((View)this.ui.stepper("+", new GalaxySettingsView$$ExternalSyntheticLambda8(var1, this, var13, var3, var5, var7, var2)));
         var6.addView((View)var11);
         var8 = new TextView(this.getContext());
         String var20 = GalaxyRow.Companion.fmt(var7.getMin());
         String var21 = GalaxyRow.Companion.fmt(var7.getMax());
         boolean var12;
         if (var1.getUnit().length() > 0) {
            var12 = true;
         } else {
            var12 = false;
         }

         var5 = "";
         String var14;
         if (var12 && !var1.getLaneStrength()) {
            String var15 = var1.getUnit();
            var14 = " " + var15;
         } else {
            var14 = "";
         }

         if (var7.getAuto()) {
            var5 = " · Auto at the right";
         }

         String var17 = GalaxyRow.Companion.fmt(var7.getStep());
         var8.setText(var20 + " to " + var21 + var14 + var5 + " · step " + var17);
         var8.setTextColor(CarUi.Companion.getDIM());
         var8.setTextSize(13.0F);
         var8.setPadding(this.ui.dp(2), this.ui.dp(4), 0, 0);
         var6.addView((View)var8);
         this.dim((View)var6, var1);
         this.col.addView((View)var6, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
      }
   }

   private static final void sliderRow$commit(String var0, GalaxyRow var1, GalaxySettingsView var2, GalaxyRow.Bounds var3, int var4, int var5) {
      String var7 = sliderRow$valueAt(var3, var4, var5);
      if (!Intrinsics.areEqual(var7, var0)) {
         var0 = "Auto";
         if (!Intrinsics.areEqual(var7, "Auto")) {
            String var8 = var1.saved(Double.parseDouble(var7));
            var0 = var8;
            if (var8 == null) {
               return;
            }
         }

         var2.saveValue(var1, var0);
      }
   }

   private static final String sliderRow$label(GalaxyRow var0, String var1) {
      return var0.withUnit(var1);
   }

   private static final Unit sliderRow$lambda$55(GalaxyRow var0, GalaxySettingsView var1, SeekBar var2, String var3, GalaxyRow.Bounds var4, int var5) {
      if (var0.getEditable() && !var1.busy && var2.getProgress() > 0) {
         var2.setProgress(var2.getProgress() - 1);
         sliderRow$commit(var3, var0, var1, var4, var5, var2.getProgress());
      }

      return Unit.INSTANCE;
   }

   private static final Unit sliderRow$lambda$57(GalaxyRow var0, GalaxySettingsView var1, SeekBar var2, int var3, String var4, GalaxyRow.Bounds var5, int var6) {
      if (var0.getEditable() && !var1.busy && var2.getProgress() < var3) {
         var2.setProgress(var2.getProgress() + 1);
         sliderRow$commit(var4, var0, var1, var5, var6, var2.getProgress());
      }

      return Unit.INSTANCE;
   }

   private static final int sliderRow$posOf(int var0, GalaxyRow.Bounds var1, int var2, String var3) {
      if (!Intrinsics.areEqual(var3, "Auto")) {
         Double var6 = StringsKt.toDoubleOrNull(var3);
         double var4;
         if (var6 != null) {
            var4 = var6;
         } else {
            var4 = var1.getMin();
         }

         var0 = RangesKt.coerceIn((int)Math.round((var4 - var1.getMin()) / var1.getStep()), 0, var2);
      }

      return var0;
   }

   private static final String sliderRow$valueAt(GalaxyRow.Bounds var0, int var1, int var2) {
      String var3;
      if (var0.getAuto() && var2 > var1) {
         var3 = "Auto";
      } else {
         var3 = GalaxyRow.Companion.fmt(GalaxyRow.Companion.snap(var0.getMin() + var2 * var0.getStep(), var0));
      }

      return var3;
   }

   private final void switchRow(GalaxyRow var1) {
      LinearLayout var3 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), this.desc(var1), 0, this.icon(var1), this.tag(var1), 4, null).component1();
      this.defaultButton(var3, var1);
      Switch var2 = this.ui.bigSwitch();
      var2.setChecked(Intrinsics.areEqual(var1.getValue(), "On"));
      var2.setEnabled(var1.getEditable());
      var2.setOnCheckedChangeListener(new GalaxySettingsView$$ExternalSyntheticLambda6(var1, this));
      var3.addView((View)var2);
      this.dim((View)var3, var1);
      this.col.addView((View)var3, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
   }

   private static final void switchRow$lambda$43(GalaxyRow var0, GalaxySettingsView var1, CompoundButton var2, boolean var3) {
      if (var0.getEditable() && !var1.busy) {
         String var4;
         if (var3) {
            var4 = "On";
         } else {
            var4 = "Off";
         }

         var1.saveValue(var0, var4);
      } else {
         var2.setChecked(var3 ^ true);
      }
   }

   private final String tag(GalaxyRow var1) {
      SettingIcons var4 = SettingIcons.INSTANCE;
      String var3 = this.getCurrent();
      String var2 = var3;
      if (var3 == null) {
         var2 = "";
      }

      String var5;
      if (var4.forRow(var2, var1.getLabel(), var1.getPage(), var1.getControl()).getKnown()) {
         var5 = null;
      } else {
         var5 = "NEW";
      }

      return var5;
   }

   private final String titleOf(String var1) {
      String var5 = this.titles.get(var1);
      String var4 = var5;
      if (var5 == null) {
         boolean var3;
         if (var1.length() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            StringBuilder var7 = new StringBuilder();
            char var2 = var1.charAt(0);
            var5 = String.valueOf(var2);
            Intrinsics.checkNotNull(var5, "null cannot be cast to non-null type java.lang.String");
            var5 = var5.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var5, "toUpperCase(...)");
            StringBuilder var8 = var7.append(var5);
            var1 = var1.substring(1);
            Intrinsics.checkNotNullExpressionValue(var1, "substring(...)");
            var4 = var8.append(var1).toString();
         } else {
            var4 = var1;
         }
      }

      return var4;
   }

   private final int vasmEditorRow(JSONObject var1) {
      boolean var3 = Intrinsics.areEqual(var1.optString("page"), "vasm");
      int var2 = -1;
      if (var3) {
         var2 = var1.optInt("editorRow", -1);
      }

      return var2;
   }

   private final String viewId() {
      JSONObject var4 = this.data;
      Object var3 = null;
      Object var2 = null;
      String var1 = (String)var3;
      if (var4 != null) {
         String var6 = var4.optString("view");
         var1 = (String)var3;
         if (var6 != null) {
            var1 = var6;
            if (StringsKt.isBlank(var1)) {
               var1 = (String)var2;
            }

            var1 = var1;
         }
      }

      return var1;
   }

   private final SettingIcons.Icon webIcon(String var1) {
      if (StringsKt.contains$default(var1, "events", false, 2, null)) {
         var1 = "sentry";
      } else {
         boolean var2 = StringsKt.contains$default(var1, "vasm", false, 2, null);
         String var3 = "crop";
         if (var2) {
            var1 = var3;
         } else if (StringsKt.contains$default(var1, "pip", false, 2, null)) {
            var1 = var3;
         } else if (StringsKt.contains$default(var1, "theme", false, 2, null)) {
            var1 = "palette";
         } else if (StringsKt.contains$default(var1, "vehicle", false, 2, null)) {
            var1 = "car";
         } else {
            CharSequence var7 = var1;
            var1 = "camera";
            if (!StringsKt.contains$default(var7, "camera", false, 2, null)) {
               var1 = "link";
            }
         }
      }

      SettingIcons var5 = SettingIcons.INSTANCE;
      String var4 = this.root.getPage();
      String var8 = var4;
      if (var4 == null) {
         var8 = "cameras";
      }

      return new SettingIcons.Icon(var1, var5.group(var8), false, 4, null);
   }

   public final boolean back() {
      int var2 = this.stack.size();
      byte var1;
      if (this.root.getPage() != null) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      if (var2 <= var1) {
         return false;
      } else {
         this.stack.remove(this.stack.size() - 1);
         this.data = null;
         this.lastShown = "";
         this.load(false);
         this.scroll.scrollTo(0, 0);
         return true;
      }
   }

   public final boolean getNeedsParked() {
      JSONObject var4 = this.data;
      boolean var3 = false;
      boolean var2 = var3;
      if (var4 != null) {
         var2 = var3;
         if (!var4.optBoolean("parked", true)) {
            var var8 = GalaxyRow.Companion.parse(var4);
            boolean var7;
            if (var8 instanceof Collection && ((Collection)var8).isEmpty()) {
               var7 = false;
            } else {
               Iterator var9 = var8.iterator();

               while (true) {
                  if (!var9.hasNext()) {
                     var7 = false;
                     break;
                  }

                  label37: {
                     GalaxyRow var5 = (GalaxyRow)var9.next();
                     if (!var5.getAvailable()) {
                        if (var5.getPage().length() == 0) {
                           var7 = true;
                        } else {
                           var7 = false;
                        }

                        if (var7) {
                           var7 = true;
                           break label37;
                        }
                     }

                     var7 = false;
                  }

                  if (var7) {
                     var7 = true;
                     break;
                  }
               }
            }

            var2 = var3;
            if (var7) {
               var2 = true;
            }
         }
      }

      return var2;
   }

   public final void hide() {
      this.visible = false;
      this.main.removeCallbacks(this.poll);
   }

   public final void show() {
      this.visible = true;
      if (this.root.getPage() != null && this.stack.isEmpty()) {
         this.stack.add(this.root.getPage());
      }

      this.load(false);
      this.main.removeCallbacks(this.poll);
      this.main.postDelayed(this.poll, 3000L);
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }

   public abstract static class Entry {
      private Entry() {
      }

      // $VF: synthetic method
      public Entry(DefaultConstructorMarker var1) {
         this();
      }

      public static final class Hub extends GalaxySettingsView.Entry {
         private final List<String> pages;

         public Hub(List<String> var1) {
            Intrinsics.checkNotNullParameter(var1, "pages");
            super(null);
            this.pages = var1;
         }

         public final List<String> getPages() {
            return this.pages;
         }
      }

      public static final class Page extends GalaxySettingsView.Entry {
         private final String desc;
         private final String label;
         private final String page;

         public Page(String var1, String var2, String var3) {
            Intrinsics.checkNotNullParameter(var1, "label");
            Intrinsics.checkNotNullParameter(var2, "desc");
            Intrinsics.checkNotNullParameter(var3, "page");
            super(null);
            this.label = var1;
            this.desc = var2;
            this.page = var3;
         }

         public final String getDesc() {
            return this.desc;
         }

         public final String getLabel() {
            return this.label;
         }

         public final String getPage() {
            return this.page;
         }
      }

      public static final class Unplaced extends GalaxySettingsView.Entry {
         private final Set<String> placed;

         public Unplaced(Set<String> var1) {
            Intrinsics.checkNotNullParameter(var1, "placed");
            super(null);
            this.placed = var1;
         }

         public final Set<String> getPlaced() {
            return this.placed;
         }
      }

      public static final class Web extends GalaxySettingsView.Entry {
         private final String desc;
         private final String label;
         private final String path;

         public Web(String var1, String var2, String var3) {
            Intrinsics.checkNotNullParameter(var1, "label");
            Intrinsics.checkNotNullParameter(var2, "desc");
            Intrinsics.checkNotNullParameter(var3, "path");
            super(null);
            this.label = var1;
            this.desc = var2;
            this.path = var3;
         }

         public final String getDesc() {
            return this.desc;
         }

         public final String getLabel() {
            return this.label;
         }

         public final String getPath() {
            return this.path;
         }
      }
   }

   public static final class Root {
      private final List<GalaxySettingsView.Entry> entries;
      private final String note;
      private final String page;
      private final String title;

      public Root(String var1, String var2, List<? extends GalaxySettingsView.Entry> var3, String var4) {
         Intrinsics.checkNotNullParameter(var1, "title");
         Intrinsics.checkNotNullParameter(var3, "entries");
         super();
         this.title = var1;
         this.page = var2;
         this.entries = var3;
         this.note = var4;
      }

      // $VF: synthetic method
      public Root(String var1, String var2, List var3, String var4, int var5, DefaultConstructorMarker var6) {
         if ((var5 & 2) != 0) {
            var2 = null;
         }

         if ((var5 & 4) != 0) {
            var3 = CollectionsKt.emptyList();
         }

         if ((var5 & 8) != 0) {
            var4 = null;
         }

         this(var1, var2, var3, var4);
      }

      public final List<GalaxySettingsView.Entry> getEntries() {
         return this.entries;
      }

      public final String getNote() {
         return this.note;
      }

      public final String getPage() {
         return this.page;
      }

      public final String getTitle() {
         return this.title;
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[GalaxyRow.Control.values().length];

         try {
            var0[GalaxyRow.Control.GROUP.ordinal()] = 1;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[GalaxyRow.Control.SWITCH.ordinal()] = 2;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[GalaxyRow.Control.SELECT.ordinal()] = 3;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[GalaxyRow.Control.SLIDER.ordinal()] = 4;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[GalaxyRow.Control.ACTION.ordinal()] = 5;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[GalaxyRow.Control.READOUT.ordinal()] = 6;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
