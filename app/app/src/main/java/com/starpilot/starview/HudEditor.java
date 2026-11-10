package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

public final class HudEditor extends FrameLayout {
   private final LinearLayout content;
   private final float d;
   private final HudLayout layout;
   private final Function0<Unit> onChange;
   private final Function0<Unit> onDone;
   private final HudEditor.Preview preview;
   private final FrameLayout root;
   private HudLayout.Item selected;
   private int tab;
   private final ArrayList<TextView> tabBtns;
   private final CarUi ui;
   private final ArrayDeque<String> undo;
   private TextView undoBtn;

   // $VF: synthetic method
   public static Unit $r8$lambda$ARHNHTLdDBHcXen_wRtOeWZIuvM/* $VF was: $r8$lambda$ARHNHTLdDBHcXen_wRtOeWZIuvM*/(HudEditor var0) {
      return panel$lambda$19(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GYA4vc7QoOtReDgECNATbrhNJDs(HudEditor var0) {
      return panel$lambda$15(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$H0gNJRInmSB9jfkWPGG6sr5UhqI(boolean var0, HudEditor var1, HudLayout.Item var2, View var3) {
      listRow$lambda$31$lambda$30(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$IzW6Je_oKBsiILFRGNBvKdAVtVk(HudEditor var0, DialogInterface var1, int var2) {
      panel$lambda$17$lambda$16(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$MFt38oykvb4VzxQABtqqFILsfXs(HudEditor var0, Map var1, DialogInterface var2, int var3) {
      presets$lambda$10$lambda$9(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OVDNTst2RKxAQa0qnvQetCiPCwQ(Context var0, HudEditor var1) {
      return panel$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Q2ikRfr2VNC57XTmHlSZgorjnFc(Function0 var0, View var1) {
      roundBtn$lambda$36$lambda$35(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SkEpTPd0eM6Y0KUBwvPKOA6eAI4(HudEditor var0, HudLayout.Item var1) {
      return listRow$lambda$34(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Xvgl2akpPIOGLJbjV8UAm4VeUAQ(HudEditor var0, HudLayout.Item var1) {
      return editTab$lambda$42(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_choKxL0Zh_Az_O7uSCcWiPMxyc/* $VF was: $r8$lambda$_choKxL0Zh_Az_O7uSCcWiPMxyc*/(HudEditor var0, String var1) {
      return presets$lambda$10$lambda$4(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$bii296Z5N9XooX4UbsULpnRwHvQ(HudEditor var0, HudLayout.Item var1) {
      return listRow$lambda$33(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$c0QijvxDhxcV_eZoby04Yq1SnL0/* $VF was: $r8$lambda$c0QijvxDhxcV_eZoby04Yq1SnL0*/(HudEditor var0, HudLayout.State var1) {
      return editTab$lambda$43(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$eysv5jl2U6537nbBb_IzyQ8ggJ4/* $VF was: $r8$lambda$eysv5jl2U6537nbBb_IzyQ8ggJ4*/(
      HudEditor var0, HudLayout.Item var1, HudLayout.State var2
   ) {
      return editTab$lambda$39(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$fYPonoSpQz57zkOYLD0whWdwJSc(HudEditor var0, List var1, Map var2, List var3, DialogInterface var4, int var5) {
      presets$lambda$10(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static void $r8$lambda$fyjc5Ak4spOZcMcwOAsCwTTp_Ks(HudEditor var0, int var1, View var2) {
      panel$lambda$22$lambda$21(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$o0LhjUw2upy_sU2jV9knI9Z1zAM/* $VF was: $r8$lambda$o0LhjUw2upy_sU2jV9knI9Z1zAM*/(
      EditText var0, HudEditor var1, DialogInterface var2, int var3
   ) {
      presets$lambda$10$lambda$8(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$oOvhs2IWLYWp3_DuVlFG1JLwo1E/* $VF was: $r8$lambda$oOvhs2IWLYWp3_DuVlFG1JLwo1E*/(HudEditor var0) {
      return panel$lambda$18(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$o_Wo8QYUaJ_jHhOg0oFml_bsSJc/* $VF was: $r8$lambda$o_Wo8QYUaJ_jHhOg0oFml_bsSJc*/(
      HudEditor var0, HudLayout.Item var1, HudLayout.State var2
   ) {
      return editTab$lambda$41(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vp_AYUGYhoPzaFh9e9TMvXQ_GHQ(HudEditor var0, HudLayout.Item var1) {
      return editTab$lambda$44(var0, var1);
   }

   public HudEditor(Context var1, FrameLayout var2, HudLayout var3, Function0<Unit> var4, Function0<Unit> var5) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "root");
      Intrinsics.checkNotNullParameter(var3, "layout");
      Intrinsics.checkNotNullParameter(var4, "onChange");
      Intrinsics.checkNotNullParameter(var5, "onDone");
      super(var1);
      this.root = var2;
      this.layout = var3;
      this.onChange = var4;
      this.onDone = var5;
      this.ui = new CarUi(var1, false, 2, null);
      this.d = var1.getResources().getDisplayMetrics().density;
      this.undo = new ArrayDeque<>();
      this.preview = new HudEditor.Preview(this, var1);
      LinearLayout var6 = new LinearLayout(var1);
      var6.setOrientation(1);
      this.content = var6;
      this.tabBtns = new ArrayList<>();
      this.setBackgroundColor(CarUi.Companion.getBG());
      this.setClickable(true);
      this.setTranslationZ(this.dpf(120.0F));
      LinearLayout var7 = new LinearLayout(var1);
      var7.setOrientation(0);
      var7.addView(this.preview, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -1, 0.58F)));
      var7.addView(this.panel(var1), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -1, 0.42F)));
      this.addView((View)var7, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      this.refresh();
   }

   private final void add(HudLayout.Item var1) {
      this.remember();
      this.layout.state(var1.getKey()).setOn(true);
      this.selected = var1;
      this.changed();
   }

   private final void center(HudLayout.Item var1) {
      RectF var2 = this.layout.rect(this.root, var1);
      if (var2 != null) {
         this.remember();
         this.layout
            .place(this.layout.state(var1.getKey()), (this.root.getWidth() - var2.width()) / 2.0F, var2.top, this.root.getWidth(), this.root.getHeight());
         this.changed();
      }
   }

   private final void changed() {
      this.layout.apply(this.root);
      this.layout.save();
      this.onChange.invoke();
      this.refresh();
   }

   private final void doUndo() {
      String var1 = this.undo.removeLastOrNull();
      if (var1 != null) {
         this.layout.restore(var1);
         HudLayout.Item var2 = this.selected;
         if (var2 != null && !this.layout.enabled(var2.getKey())) {
            this.selected = null;
         }

         this.changed();
      }
   }

   private final float dpf(float var1) {
      return this.d * var1;
   }

   private final void editTab() {
      HudLayout.Item var3 = this.selected;
      if (var3 == null) {
         this.content.addView((View)this.ui.note("Pick a widget in the preview or in the list."));
      } else {
         HudLayout.State var2 = this.layout.state(var3.getKey());
         LinearLayout var5 = this.content;
         TextView var4 = new TextView(this.getContext());
         var4.setText(var3.getLabel());
         var4.setTextSize(22.0F);
         var4.setTextColor(-1);
         var4.setTypeface(Typeface.DEFAULT_BOLD);
         var4.setPadding(this.ui.dp(4), this.ui.dp(4), 0, this.ui.dp(12));
         var5.addView((View)var4);
         this.content.addView((View)this.ui.header("Size"));
         var5 = new LinearLayout(this.getContext());
         var5.setOrientation(0);
         var5.setGravity(16);
         var5.addView((View)this.ui.stepper("−", new HudEditor$$ExternalSyntheticLambda5(this, var3, var2)));
         var4 = new TextView(this.getContext());
         int var1 = MathKt.roundToInt(var2.getScale() * 100);
         var4.setText(var1 + " %");
         var4.setTextSize(22.0F);
         var4.setTextColor(-1);
         var4.setGravity(17);
         var5.addView((View)var4, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
         var5.addView((View)this.ui.stepper("+", new HudEditor$$ExternalSyntheticLambda6(this, var3, var2)));
         this.content.addView((View)var5, (LayoutParams)CarUi.lp$default(this.ui, 0, this.ui.dp(12), 1, null));
         this.content.addView((View)this.ui.header("Position"));
         this.content
            .addView(
               (View)CarUi.button$default(this.ui, "Center it left to right", false, new HudEditor$$ExternalSyntheticLambda7(this, var3), 2, null),
               (LayoutParams)CarUi.lp$default(this.ui, 0, this.ui.dp(8), 1, null)
            );
         this.content
            .addView(
               (View)CarUi.button$default(this.ui, "Back to its first spot and size", false, new HudEditor$$ExternalSyntheticLambda8(this, var2), 2, null),
               (LayoutParams)CarUi.lp$default(this.ui, 0, this.ui.dp(8), 1, null)
            );
         if (var3.getCanHide()) {
            this.content.addView((View)this.ui.header("Screen"));
            this.content
               .addView(
                  (View)CarUi.button$default(this.ui, "Take it off the screen", false, new HudEditor$$ExternalSyntheticLambda9(this, var3), 2, null),
                  (LayoutParams)CarUi.lp$default(this.ui, 0, this.ui.dp(8), 1, null)
               );
         }
      }
   }

   private static final Unit editTab$lambda$39(HudEditor var0, HudLayout.Item var1, HudLayout.State var2) {
      var0.setScale(var1, var2.getScale() - 0.1F);
      return Unit.INSTANCE;
   }

   private static final Unit editTab$lambda$41(HudEditor var0, HudLayout.Item var1, HudLayout.State var2) {
      var0.setScale(var1, var2.getScale() + 0.1F);
      return Unit.INSTANCE;
   }

   private static final Unit editTab$lambda$42(HudEditor var0, HudLayout.Item var1) {
      var0.center(var1);
      return Unit.INSTANCE;
   }

   private static final Unit editTab$lambda$43(HudEditor var0, HudLayout.State var1) {
      var0.remember();
      var1.setX(Float.NaN);
      var1.setY(Float.NaN);
      var1.setCw(Float.NaN);
      var1.setCh(Float.NaN);
      var1.setScale(1.0F);
      var0.changed();
      return Unit.INSTANCE;
   }

   private static final Unit editTab$lambda$44(HudEditor var0, HudLayout.Item var1) {
      var0.remove(var1);
      return Unit.INSTANCE;
   }

   private final View listRow(HudLayout.Item var1, boolean var2) {
      boolean var3;
      if (var1 == this.selected) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      LinearLayout var6 = new LinearLayout(this.getContext());
      var6.setOrientation(0);
      var6.setGravity(16);
      CarUi var7 = this.ui;
      CarUi.Companion var5 = CarUi.Companion;
      int var4;
      if (var3) {
         var4 = var5.getCARD_ON();
      } else {
         var4 = var5.getCARD();
      }

      Integer var9;
      if (var3) {
         var9 = CarUi.Companion.getACCENT();
      } else {
         var9 = null;
      }

      var6.setBackground((Drawable)var7.rounded(var4, 14, var9));
      var6.setPadding(this.ui.dp(16), this.ui.dp(6), this.ui.dp(8), this.ui.dp(6));
      var6.setMinimumHeight(this.ui.dp(58));
      var6.setOnClickListener(new HudEditor$$ExternalSyntheticLambda10(var2, this, var1));
      TextView var10 = new TextView(this.getContext());
      var10.setText(var1.getLabel());
      var10.setTextSize(18.0F);
      if (var2) {
         var3 = -1;
      } else {
         var3 = CarUi.Companion.getDIM();
      }

      var10.setTextColor(var3);
      var6.addView((View)var10, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      if (!var2) {
         var6.addView((View)this.roundBtn("+", CarUi.Companion.getACCENT(), new HudEditor$$ExternalSyntheticLambda11(this, var1)));
      } else if (var1.getCanHide()) {
         var6.addView((View)this.roundBtn("−", Color.rgb(235, 90, 110), new HudEditor$$ExternalSyntheticLambda12(this, var1)));
      }

      return (View)var6;
   }

   private static final void listRow$lambda$31$lambda$30(boolean var0, HudEditor var1, HudLayout.Item var2, View var3) {
      if (var0) {
         var1.selected = var2;
         var1.tab = 1;
         var1.refresh();
      } else {
         var1.add(var2);
      }
   }

   private static final Unit listRow$lambda$33(HudEditor var0, HudLayout.Item var1) {
      var0.add(var1);
      return Unit.INSTANCE;
   }

   private static final Unit listRow$lambda$34(HudEditor var0, HudLayout.Item var1) {
      var0.remove(var1);
      return Unit.INSTANCE;
   }

   private final float order(HudLayout.Item var1) {
      RectF var3 = this.layout.rect(this.root, var1);
      float var2;
      if (var3 != null) {
         var2 = var3.top / Math.max(1, this.root.getHeight()) * 4.0F + var3.left / Math.max(1, this.root.getWidth());
      } else {
         var2 = 99.0F;
      }

      return var2;
   }

   private final View panel(Context var1) {
      LinearLayout var6 = new LinearLayout(var1);
      var6.setOrientation(1);
      var6.setPadding(this.ui.dp(18), this.ui.dp(16), this.ui.dp(18), this.ui.dp(12));
      var6.setBackgroundColor(CarUi.Companion.getFRAME());
      TextView var4 = new TextView(var1);
      var4.setText("Driving screen");
      var4.setTextSize(24.0F);
      var4.setTextColor(-1);
      var4.setTypeface(Typeface.DEFAULT_BOLD);
      var6.addView((View)var4);
      var4 = new TextView(var1);
      var4.setText("Drag a widget in the preview to move it, its corner to resize it. Changes save as you go.");
      var4.setTextSize(14.0F);
      var4.setTextColor(CarUi.Companion.getDIM());
      var4.setPadding(0, this.ui.dp(4), 0, this.ui.dp(12));
      var6.addView((View)var4);
      LinearLayout var7 = new LinearLayout(var1);
      var7.setOrientation(0);
      var7.setGravity(16);
      this.undoBtn = CarUi.button$default(this.ui, "Undo", false, new HudEditor$$ExternalSyntheticLambda16(this), 2, null);
      TextView var5 = this.undoBtn;
      var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("undoBtn");
         var4 = null;
      }

      var7.addView((View)var4);
      var7.addView(new View(var1), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.ui.dp(8), 1)));
      var7.addView((View)CarUi.button$default(this.ui, "Reset", false, new HudEditor$$ExternalSyntheticLambda17(var1, this), 2, null));
      var7.addView(new View(var1), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.ui.dp(8), 1)));
      var7.addView((View)CarUi.button$default(this.ui, "Presets", false, new HudEditor$$ExternalSyntheticLambda18(this), 2, null));
      var7.addView(new View(var1), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, 1, 1.0F)));
      var7.addView((View)this.ui.button("Done", true, new HudEditor$$ExternalSyntheticLambda1(this)));
      var6.addView((View)var7);
      LinearLayout var14 = new LinearLayout(var1);
      var14.setOrientation(0);
      var14.setPadding(0, this.ui.dp(14), 0, this.ui.dp(10));
      Iterator var13 = CollectionsKt.listOf(new String[]{"Widgets", "Edit widget"}).iterator();
      int var2 = 0;

      while (var13.hasNext()) {
         int var3 = var2 + 1;
         String var15 = (String)var13.next();
         TextView var8 = new TextView(var1);
         var8.setText(var15);
         var8.setTextSize(17.0F);
         var8.setGravity(17);
         var8.setTypeface(Typeface.DEFAULT_BOLD);
         var8.setPadding(this.ui.dp(10), this.ui.dp(12), this.ui.dp(10), this.ui.dp(12));
         var8.setOnClickListener(new HudEditor$$ExternalSyntheticLambda2(this, var2));
         this.tabBtns.add(var8);
         View var17 = (View)var8;
         android.widget.LinearLayout.LayoutParams var16 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
         if (var2 == 0) {
            var16.setMarginEnd(this.ui.dp(8));
         }

         Unit var9 = Unit.INSTANCE;
         var14.addView(var17, (LayoutParams)var16);
         var2 = var3;
      }

      var6.addView((View)var14);
      ScrollView var10 = new ScrollView(var1);
      var10.addView((View)this.content);
      var6.addView((View)var10, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
      return (View)var6;
   }

   private static final Unit panel$lambda$15(HudEditor var0) {
      var0.doUndo();
      return Unit.INSTANCE;
   }

   private static final Unit panel$lambda$17(Context var0, HudEditor var1) {
      new Builder(var0)
         .setMessage("Put every widget back where it started and show all of them?")
         .setPositiveButton("Reset", new HudEditor$$ExternalSyntheticLambda3(var1))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void panel$lambda$17$lambda$16(HudEditor var0, DialogInterface var1, int var2) {
      var0.remember();
      var0.layout.reset();
      var0.layout.save();
      var0.selected = null;
      var0.changed();
   }

   private static final Unit panel$lambda$18(HudEditor var0) {
      var0.presets();
      return Unit.INSTANCE;
   }

   private static final Unit panel$lambda$19(HudEditor var0) {
      var0.finish();
      return Unit.INSTANCE;
   }

   private static final void panel$lambda$22$lambda$21(HudEditor var0, int var1, View var2) {
      var0.tab = var1;
      var0.refresh();
   }

   private final void presets() {
      HudPresets var2 = HudPresets.INSTANCE;
      Context var1 = this.getContext();
      Intrinsics.checkNotNullExpressionValue(var1, "getContext(...)");
      Map var11 = var2.saved(var1);
      Iterable var3 = this.layout.getItems();
      var var7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
      Iterator var12 = var3.iterator();

      while (var12.hasNext()) {
         var7.add(((HudLayout.Item)var12.next()).getKey());
      }

      List var13 = var7;
      Collection var8 = CollectionsKt.listOf(new String[]{"Highway focus (only what matters)", "Everything on", "Cockpit (cards in a rail on the right)"});
      Iterable var5 = var11.keySet();
      var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));

      for (String var6 : var5) {
         var4.add("★ " + var6);
      }

      List var14 = var4;
      Collection var15 = CollectionsKt.plus(CollectionsKt.plus(var8, var14), CollectionsKt.listOf("Save this layout as…"));
      List var9;
      if (!var11.isEmpty()) {
         var9 = CollectionsKt.listOf("Delete a saved layout…");
      } else {
         var9 = CollectionsKt.emptyList();
      }

      List var10 = CollectionsKt.plus(var15, var9);
      new Builder(this.getContext())
         .setTitle("Driving screen layouts")
         .setItems(var10.toArray(new String[0]), new HudEditor$$ExternalSyntheticLambda4(this, var13, var11, var10))
         .setNegativeButton("Cancel", null)
         .show();
   }

   private static final void presets$lambda$10(HudEditor var0, List var1, Map var2, List var3, DialogInterface var4, int var5) {
      HudEditor$$ExternalSyntheticLambda13 var7 = new HudEditor$$ExternalSyntheticLambda13(var0);
      if (var5 == 0) {
         var7.invoke(HudPresets.INSTANCE.only(var0.layout.snapshot(), var1, HudPresets.INSTANCE.getFOCUS_KEYS()));
      } else if (var5 == 1) {
         var7.invoke(HudPresets.INSTANCE.everything(var0.layout.snapshot(), var1));
      } else if (var5 == 2) {
         var7.invoke(HudPresets.INSTANCE.cockpit(var0.layout.snapshot(), var1));
      } else if (var5 < var2.size() + 3) {
         var7.invoke(CollectionsKt.elementAt(var2.values(), var5 - 3));
      } else if (StringsKt.startsWith$default((String)var3.get(var5), "Save", false, 2, null)) {
         EditText var6 = new EditText(var0.getContext());
         var6.setHint("Name, e.g. Night highway");
         new Builder(var0.getContext())
            .setTitle("Save layout")
            .setView((View)var6)
            .setPositiveButton("Save", new HudEditor$$ExternalSyntheticLambda14(var6, var0))
            .setNegativeButton("Cancel", null)
            .show();
      } else {
         new Builder(var0.getContext())
            .setTitle("Delete which?")
            .setItems(var2.keySet().toArray(new String[0]), new HudEditor$$ExternalSyntheticLambda15(var0, var2))
            .show();
      }
   }

   private static final Unit presets$lambda$10$lambda$4(HudEditor var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "j");
      var0.remember();
      var0.layout.restore(var1);
      var0.layout.save();
      var0.selected = null;
      var0.changed();
      return Unit.INSTANCE;
   }

   private static final void presets$lambda$10$lambda$8(EditText var0, HudEditor var1, DialogInterface var2, int var3) {
      String var5 = StringsKt.trim(var0.getText().toString()).toString();
      boolean var7;
      if (var5.length() > 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (!var7) {
         var5 = null;
      }

      if (var5 != null) {
         HudPresets var6 = HudPresets.INSTANCE;
         Context var4 = var1.getContext();
         Intrinsics.checkNotNullExpressionValue(var4, "getContext(...)");
         var6.save(var4, var5, var1.layout.snapshot());
      }
   }

   private static final void presets$lambda$10$lambda$9(HudEditor var0, Map var1, DialogInterface var2, int var3) {
      HudPresets var5 = HudPresets.INSTANCE;
      Context var4 = var0.getContext();
      Intrinsics.checkNotNullExpressionValue(var4, "getContext(...)");
      var5.delete(var4, CollectionsKt.elementAt(var1.keySet(), var3));
   }

   private final void refresh() {
      Iterator var5 = this.tabBtns.iterator();
      int var2 = 0;

      while (var5.hasNext()) {
         int var3 = 1;
         int var4 = var2 + 1;
         TextView var7 = (TextView)var5.next();
         boolean var9;
         if (var2 == this.tab) {
            var9 = var3;
         } else {
            var9 = 0;
         }

         CarUi var6 = this.ui;
         CarUi.Companion var8 = CarUi.Companion;
         if (var9) {
            var3 = var8.getACCENT();
         } else {
            var3 = var8.getCARD();
         }

         var7.setBackground((Drawable)CarUi.rounded$default(var6, var3, 22, null, 4, null));
         if (var9) {
            var9 = CarUi.Companion.getBG();
         } else {
            var9 = -1;
         }

         var7.setTextColor(var9);
         var2 = var4;
      }

      TextView var13 = this.undoBtn;
      TextView var12 = var13;
      if (var13 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("undoBtn");
         var12 = null;
      }

      float var1;
      if (this.undo.isEmpty()) {
         var1 = 0.4F;
      } else {
         var1 = 1.0F;
      }

      var12.setAlpha(var1);
      this.content.removeAllViews();
      if (this.tab == 0) {
         this.widgetsTab();
      } else {
         this.editTab();
      }

      this.preview.invalidate();
   }

   private final void remember() {
      this.undo.addLast(this.layout.snapshot());

      while (this.undo.size() > 40) {
         this.undo.removeFirst();
      }
   }

   private final void remove(HudLayout.Item var1) {
      this.remember();
      this.layout.state(var1.getKey()).setOn(false);
      if (this.selected == var1) {
         this.selected = null;
      }

      this.tab = 0;
      this.changed();
   }

   private final TextView roundBtn(String var1, int var2, Function0<Unit> var3) {
      TextView var4 = new TextView(this.getContext());
      var4.setText(var1);
      var4.setTextSize(26.0F);
      var4.setGravity(17);
      var4.setTextColor(var2);
      var4.setTypeface(Typeface.DEFAULT_BOLD);
      var4.setLayoutParams((LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.ui.dp(52), this.ui.dp(46))));
      var4.setOnClickListener(new HudEditor$$ExternalSyntheticLambda0(var3));
      return var4;
   }

   private static final void roundBtn$lambda$36$lambda$35(Function0 var0, View var1) {
      var0.invoke();
   }

   private final void setScale(HudLayout.Item var1, float var2) {
      this.remember();
      this.layout.state(var1.getKey()).setScale(RangesKt.coerceIn(MathKt.roundToInt(10 * var2) / 10.0F, 0.5F, 2.5F));
      this.changed();
   }

   private final Pair<Float, Float> snap(HudLayout.Item var1, float var2, float var3, float var4, float var5) {
      float var8 = this.dpf(14.0F);
      float var6 = this.dpf(24.0F);
      ArrayList var13 = new ArrayList();
      ArrayList var12 = new ArrayList();
      Collection var14 = var13;
      float var7 = this.root.getWidth();
      float var10 = this.root.getWidth();
      float var9 = 2;
      CollectionsKt.addAll(var14, CollectionsKt.listOf(new Float[]{var6, var7 - var6 - var4, (var10 - var4) / var9}));
      CollectionsKt.addAll(var12, CollectionsKt.listOf(new Float[]{var6, this.root.getHeight() - var6 - var5, (this.root.getHeight() - var5) / var9}));

      for (HudLayout.Item var15 : this.layout.getItems()) {
         if (var15 != var1 && this.layout.enabled(var15.getKey())) {
            RectF var27 = this.layout.rect(this.root, var15);
            if (var27 != null) {
               CollectionsKt.addAll(
                  var13, CollectionsKt.listOf(new Float[]{var27.left, var27.right - var4, var27.right + this.dpf(10.0F), var27.left - var4 - this.dpf(10.0F)})
               );
               CollectionsKt.addAll(
                  var12, CollectionsKt.listOf(new Float[]{var27.top, var27.bottom - var5, var27.bottom + this.dpf(10.0F), var27.top - var5 - this.dpf(10.0F)})
               );
            }
         }
      }

      var6 = var2;
      var7 = var3;
      var9 = var8;
      Iterator var25 = var13.iterator();
      Intrinsics.checkNotNullExpressionValue(var25, "iterator(...)");

      while (var25.hasNext()) {
         Object var16 = var25.next();
         Intrinsics.checkNotNullExpressionValue(var16, "next(...)");
         var10 = ((Number)var16).floatValue();
         float var11 = Math.abs(var10 - var2);
         if (var11 < var9) {
            var9 = var11;
            var6 = var10;
         }
      }

      Iterator var17 = var12.iterator();
      Intrinsics.checkNotNullExpressionValue(var17, "iterator(...)");

      while (var17.hasNext()) {
         Object var24 = var17.next();
         Intrinsics.checkNotNullExpressionValue(var24, "next(...)");
         var2 = ((Number)var24).floatValue();
         var9 = Math.abs(var2 - var3);
         if (var9 < var8) {
            var8 = var9;
            var7 = var2;
         }
      }

      return TuplesKt.to(
         RangesKt.coerceIn(var6, 0.0F, Math.max(0.0F, this.root.getWidth() - var4)),
         RangesKt.coerceIn(var7, 0.0F, Math.max(0.0F, this.root.getHeight() - var5))
      );
   }

   private final void widgetsTab() {
      Iterable var3 = this.layout.getItems();
      var var2 = new ArrayList();

      for (Object var5 : var3) {
         HudLayout.Item var4 = (HudLayout.Item)var5;
         if (this.layout.enabled(var4.getKey())) {
            var2.add(var5);
         }
      }

      List var8 = var2;
      Iterable var16 = this.layout.getItems();
      var var13 = new ArrayList();

      for (Object var6 : var16) {
         HudLayout.Item var21 = (HudLayout.Item)var6;
         if (!this.layout.enabled(var21.getKey())) {
            var13.add(var6);
         }
      }

      List var14 = var13;
      LinearLayout var18 = this.content;
      CarUi var22 = this.ui;
      int var1 = var8.size();
      var18.addView((View)var22.header("On screen  ·  " + var1));

      for (HudLayout.Item var19 : CollectionsKt.sortedWith(var8, new Comparator() {

         @Override
         public final int compare(T var1, T var2x) {
            var1 = var1;
            Comparable var4 = HudEditor.this.order(var1);
            var2x = var2x;
            return ComparisonsKt.compareValues(var4, HudEditor.this.order(var2x));
         }
      })) {
         this.content.addView(this.listRow(var19, true), (LayoutParams)CarUi.lp$default(this.ui, 0, this.ui.dp(8), 1, null));
      }

      LinearLayout var20 = this.content;
      CarUi var10 = this.ui;
      var1 = var14.size();
      var20.addView((View)var10.header("Not on screen  ·  " + var1));
      if (var14.isEmpty()) {
         this.content.addView((View)this.ui.note("Every widget is on the screen."));
      }

      for (HudLayout.Item var15 : CollectionsKt.sortedWith(var14, new Comparator() {
         @Override
         public final int compare(T var1, T var2) {
            return ComparisonsKt.compareValues(((HudLayout.Item)var1).getLabel(), ((HudLayout.Item)var2).getLabel());
         }
      })) {
         this.content.addView(this.listRow(var15, false), (LayoutParams)CarUi.lp$default(this.ui, 0, this.ui.dp(8), 1, null));
      }
   }

   public final void finish() {
      this.layout.save();
      this.onDone.invoke();
   }

   private final class Preview extends View {
      private String before;
      private final Paint bg;
      private final Paint dash;
      private float downX;
      private float downY;
      private final Paint fill;
      private final RectF frame;
      private float k;
      private final Paint label;
      private final Paint line;
      private int mode;
      private boolean moved;
      private final Path road;
      private final Paint selP;
      private float startL;
      private float startScale;
      private float startT;
      private float startW;
      final HudEditor this$0;

      public Preview(Context var1, Context var2) {
         Intrinsics.checkNotNullParameter(var2, "ctx");
         this.this$0 = var1;
         super(var2);
         this.frame = new RectF();
         this.k = 1.0F;
         this.bg = new Paint(1);
         Paint var3 = new Paint(1);
         var3.setStyle(Style.STROKE);
         this.line = var3;
         Paint var7 = new Paint(1);
         var1 = this.this$0;
         var7.setStyle(Style.STROKE);
         var7.setStrokeWidth(var1.dpf(1.5F));
         var7.setPathEffect((PathEffect)(new DashPathEffect(new float[]{var1.dpf(7.0F), var1.dpf(5.0F)}, 0.0F)));
         var7.setColor(Color.argb(150, 255, 255, 255));
         this.dash = var7;
         Paint var8 = new Paint(1);
         var1 = this.this$0;
         var8.setStyle(Style.STROKE);
         var8.setStrokeWidth(var1.dpf(3.0F));
         var8.setColor(CarUi.Companion.getACCENT());
         this.selP = var8;
         this.fill = new Paint(1);
         Paint var9 = new Paint(1);
         var1 = this.this$0;
         var9.setColor(-1);
         var9.setTextSize(var1.dpf(12.0F));
         var9.setTypeface(Typeface.DEFAULT_BOLD);
         this.label = var9;
         this.road = new Path();
         this.startScale = 1.0F;
      }

      private final RectF box(HudLayout.Item var1) {
         RectF var3 = this.this$0.layout.rect(this.this$0.root, var1);
         if (var3 == null) {
            return null;
         } else {
            float var2 = this.this$0.dpf(26.0F);
            return new RectF(
               this.frame.left + var3.left * this.k,
               this.frame.top + var3.top * this.k,
               this.frame.left + Math.max(var3.right * this.k, var3.left * this.k + var2),
               this.frame.top + Math.max(var3.bottom * this.k, var3.top * this.k + var2)
            );
         }
      }

      private final HudLayout.Item hit(float var1, float var2) {
         if (this.k <= 0.0F) {
            return null;
         } else {
            HudLayout.Item var3 = this.this$0.selected;
            if (var3 != null) {
               if (!this.this$0.layout.enabled(var3.getKey())) {
                  var3 = null;
               }

               if (var3 != null) {
                  HudEditor var5 = this.this$0;
                  RectF var4 = this.box(var3);
                  if (var4 != null && Math.abs(var1 - var4.right) < var5.dpf(26.0F) && Math.abs(var2 - var4.bottom) < var5.dpf(26.0F)) {
                     return var3;
                  }
               }
            }

            for (HudLayout.Item var7 : CollectionsKt.reversed(this.this$0.layout.getItems())) {
               if (this.this$0.layout.enabled(var7.getKey())) {
                  RectF var6 = this.box(var7);
                  if (var6 != null) {
                     var6.inset(-this.this$0.dpf(6.0F), -this.this$0.dpf(6.0F));
                     if (var6.contains(var1, var2)) {
                        return var7;
                     }
                  }
               }
            }

            return null;
         }
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         float var3 = Math.max(1, this.this$0.root.getWidth());
         float var5 = Math.max(1, this.this$0.root.getHeight());
         float var2 = this.this$0.dpf(20.0F);
         float var6 = this.getWidth();
         float var4 = 2;
         this.k = Math.min((var6 - var4 * var2) / var3, (this.getHeight() - var4 * var2) / var5);
         if (!(this.k <= 0.0F)) {
            var2 = var3 * this.k;
            var5 *= this.k;
            this.frame
               .set((this.getWidth() - var2) / var4, (this.getHeight() - var5) / var4, (this.getWidth() + var2) / var4, (this.getHeight() + var5) / var4);
            this.bg.setColor(Color.rgb(26, 29, 36));
            var1.drawRoundRect(this.frame, this.this$0.dpf(14.0F), this.this$0.dpf(14.0F), this.bg);
            var4 = this.frame.centerX();
            var5 = this.frame.top + 0.42F * var5;
            this.road.reset();
            this.road.moveTo(var4 - var2 * 0.06F, var5);
            this.road.lineTo(var4 + var2 * 0.06F, var5);
            this.road.lineTo(this.frame.left + var2 * 0.95F, this.frame.bottom);
            this.road.lineTo(this.frame.left + var2 * 0.05F, this.frame.bottom);
            this.road.close();
            this.bg.setColor(Color.rgb(44, 48, 56));
            var1.drawPath(this.road, this.bg);
            this.road.reset();
            this.road.moveTo(var4 - var2 * 0.025F, var5);
            this.road.lineTo(var4 + 0.025F * var2, var5);
            this.road.lineTo(var4 + var2 * 0.17F, this.frame.bottom);
            this.road.lineTo(var4 - 0.17F * var2, this.frame.bottom);
            this.road.close();
            this.bg.setColor(Color.argb(150, 40, 170, 90));
            var1.drawPath(this.road, this.bg);
            this.line.setColor(Color.argb(200, 220, 220, 220));
            this.line.setStrokeWidth(this.this$0.dpf(2.0F));
            var1.drawLine(var4 - var2 * 0.06F, var5, this.frame.left + 0.05F * var2, this.frame.bottom, this.line);
            var1.drawLine(var4 + 0.06F * var2, var5, this.frame.left + 0.95F * var2, this.frame.bottom, this.line);
            this.line.setColor(Color.argb(220, 60, 210, 110));
            this.line.setStrokeWidth(this.this$0.dpf(3.0F));
            var1.drawRoundRect(this.frame, this.this$0.dpf(14.0F), this.this$0.dpf(14.0F), this.line);
            var1.save();
            var1.clipRect(this.frame);

            for (HudLayout.Item var11 : this.this$0.layout.getItems()) {
               if (this.this$0.layout.enabled(var11.getKey())) {
                  View var10 = this.this$0.root.findViewById(var11.getId());
                  if (var10 != null) {
                     RectF var32 = this.this$0.layout.rect(this.this$0.root, var11);
                     if (var32 != null) {
                        var4 = this.k;
                        if (var10.getScaleX() > 0.0F) {
                           var2 = var10.getScaleX();
                        } else {
                           var2 = 1.0F;
                        }

                        var2 *= var4;
                        var1.save();
                        var1.translate(this.frame.left + var32.left * this.k, this.frame.top + var32.top * this.k);
                        var1.scale(var2, var2);

                        try {
                           var10.draw(var1);
                        } catch (Exception var14) {
                        }

                        var1.restore();
                     }
                  }
               }
            }

            for (HudLayout.Item var33 : this.this$0.layout.getItems()) {
               if (this.this$0.layout.enabled(var33.getKey())) {
                  RectF var12 = this.box(var33);
                  if (var12 != null) {
                     boolean var7;
                     if (var33 == this.this$0.selected) {
                        var7 = true;
                     } else {
                        var7 = false;
                     }

                     var3 = this.this$0.dpf(8.0F);
                     var4 = this.this$0.dpf(8.0F);
                     Paint var28;
                     if (var7) {
                        var28 = this.selP;
                     } else {
                        var28 = this.dash;
                     }

                     var1.drawRoundRect(var12, var3, var4, var28);
                     Paint var13 = this.label;
                     HudEditor var29 = this.this$0;
                     if (var7) {
                        var3 = 14.0F;
                     } else {
                        var3 = 11.5F;
                     }

                     var13.setTextSize(var29.dpf(var3));
                     var3 = this.label.measureText(var33.getLabel());
                     var28 = this.fill;
                     int var8;
                     if (var7) {
                        var8 = CarUi.Companion.getACCENT();
                     } else {
                        var8 = Color.argb(190, 16, 18, 24);
                     }

                     var28.setColor(var8);
                     var1.drawRoundRect(
                        new RectF(
                           var12.left + this.this$0.dpf(3.0F),
                           var12.top + this.this$0.dpf(3.0F),
                           var12.left + this.this$0.dpf(11.0F) + var3,
                           var12.top + this.this$0.dpf(5.0F) + this.label.getTextSize() * 1.25F
                        ),
                        this.this$0.dpf(5.0F),
                        this.this$0.dpf(5.0F),
                        this.fill
                     );
                     var28 = this.label;
                     if (var7) {
                        var8 = CarUi.Companion.getBG();
                     } else {
                        var8 = -1;
                     }

                     var28.setColor(var8);
                     var1.drawText(
                        var33.getLabel(), var12.left + this.this$0.dpf(7.0F), var12.top + this.this$0.dpf(3.0F) + this.label.getTextSize() * 1.05F, this.label
                     );
                     if (var7) {
                        this.fill.setColor(CarUi.Companion.getACCENT());
                        var1.drawCircle(var12.right, var12.bottom, this.this$0.dpf(11.0F), this.fill);
                        this.fill.setColor(CarUi.Companion.getBG());
                        var1.drawCircle(var12.right, var12.bottom, this.this$0.dpf(4.0F), this.fill);
                     }
                  }
               }
            }

            var1.restore();
            this.postInvalidateDelayed(150L);
         }
      }

      public boolean onTouchEvent(MotionEvent var1) {
         Intrinsics.checkNotNullParameter(var1, "e");
         switch (var1.getActionMasked()) {
            case 0:
               this.downX = var1.getX();
               this.downY = var1.getY();
               this.moved = false;
               HudLayout.Item var7 = this.hit(var1.getX(), var1.getY());
               if (var7 == null) {
                  this.mode = 0;
                  return true;
               } else {
                  RectF var8 = this.this$0.layout.rect(this.this$0.root, var7);
                  if (var8 == null) {
                     return true;
                  } else {
                     HudLayout.State var20 = this.this$0.layout.state(var7.getKey());
                     RectF var18 = this.box(var7);
                     Intrinsics.checkNotNull(var18);
                     byte var4;
                     if (var7 == this.this$0.selected
                        && Math.abs(var1.getX() - var18.right) < this.this$0.dpf(26.0F)
                        && Math.abs(var1.getY() - var18.bottom) < this.this$0.dpf(26.0F)) {
                        var4 = 2;
                     } else {
                        var4 = 1;
                     }

                     this.mode = var4;
                     if (this.this$0.selected != var7) {
                        this.this$0.selected = var7;
                        this.this$0.refresh();
                     }

                     this.before = this.this$0.layout.snapshot();
                     this.startL = var8.left;
                     this.startT = var8.top;
                     this.startScale = var20.getScale();
                     this.startW = var8.width();
                     ViewParent var11 = this.getParent();
                     if (var11 != null) {
                        var11.requestDisallowInterceptTouchEvent(true);
                     }

                     return true;
                  }
               }
            case 1:
            case 3:
               if (this.mode == 0 && !this.moved && var1.getActionMasked() == 1 && this.this$0.selected != null) {
                  this.this$0.selected = null;
                  this.this$0.refresh();
                  return true;
               }

               if (this.moved) {
                  String var10 = this.before;
                  if (var10 != null) {
                     HudEditor var17 = this.this$0;
                     var17.undo.addLast(var10);

                     while (var17.undo.size() > 40) {
                        var17.undo.removeFirst();
                     }
                  }

                  this.this$0.layout.save();
                  this.this$0.refresh();
               }

               this.mode = 0;
               this.before = null;
               return true;
            case 2:
               HudLayout.Item var5 = this.this$0.selected;
               if (var5 == null) {
                  return true;
               } else if (this.mode == 0) {
                  return true;
               } else {
                  float var2 = (var1.getX() - this.downX) / this.k;
                  float var3 = (var1.getY() - this.downY) / this.k;
                  if (Math.abs(var1.getX() - this.downX) > this.this$0.dpf(4.0F) || Math.abs(var1.getY() - this.downY) > this.this$0.dpf(4.0F)) {
                     this.moved = true;
                  }

                  if (!this.moved) {
                     return true;
                  } else {
                     HudLayout.State var9 = this.this$0.layout.state(var5.getKey());
                     if (Float.isNaN(var9.getX())) {
                        RectF var6 = this.this$0.layout.rect(this.this$0.root, var5);
                        if (var6 != null) {
                           this.this$0.layout.place(var9, var6.left, var6.top, this.this$0.root.getWidth(), this.this$0.root.getHeight());
                        }
                     }

                     if (this.mode == 1) {
                        RectF var19 = this.this$0.layout.rect(this.this$0.root, var5);
                        if (var19 == null) {
                           return true;
                        }

                        var2 = RangesKt.coerceIn(this.startL + var2, 0.0F, Math.max(0.0F, this.this$0.root.getWidth() - var19.width()));
                        var3 = RangesKt.coerceIn(this.startT + var3, 0.0F, Math.max(0.0F, this.this$0.root.getHeight() - var19.height()));
                        Pair var16 = this.this$0.snap(var5, var2, var3, var19.width(), var19.height());
                        var2 = ((Number)var16.component1()).floatValue();
                        var3 = ((Number)var16.component2()).floatValue();
                        this.this$0.layout.place(var9, var2, var3, this.this$0.root.getWidth(), this.this$0.root.getHeight());
                     } else {
                        var9.setScale(RangesKt.coerceIn(this.startScale * (this.startW + var2) / Math.max(1.0F, this.startW), 0.5F, 2.5F));
                     }

                     this.this$0.layout.apply(this.this$0.root);
                     this.this$0.onChange.invoke();
                     this.invalidate();
                     return true;
                  }
               }
            default:
               return true;
         }
      }
   }
}
