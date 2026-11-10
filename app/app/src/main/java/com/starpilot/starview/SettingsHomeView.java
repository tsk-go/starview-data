package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class SettingsHomeView extends LinearLayout {
   private final Function1<SettingsIndex.Entry, Unit> addShortcut;
   private boolean crawled;
   private List<SettingsIndex.Entry> entries;
   private final Function0<List<SettingsIndex.Entry>> extraEntries;
   private final EditText field;
   private final View grid;
   private final SearchHistory history;
   private final Handler main;
   private final Function1<String, Unit> open;
   private final UiSettings prefs;
   private final LinearLayout results;
   private final ScrollView resultsScroll;
   private final CarUi ui;

   // $VF: synthetic method
   public static Unit $r8$lambda$BRSZduRCzEwpzr1c84dA_CM7PtI(SettingsHomeView var0, List var1) {
      return startIndex$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$McIdLgVLX8mwLiSbDD1f_AiP6WY/* $VF was: $r8$lambda$McIdLgVLX8mwLiSbDD1f_AiP6WY*/(
      SettingsHomeView var0, SearchHistory.Item var1, View var2
   ) {
      showHistory$lambda$18$lambda$17(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$RYGJdeLOEwQfJI8dQybTdd3b9YU(TextView var0, List var1, SettingsHomeView var2, View var3) {
      showHistory$lambda$16$lambda$15(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$dBFmSWrhm3v8_02zdMrOMybMKQA(SettingsHomeView var0, DialogInterface var1, int var2) {
      showHistory$lambda$16$lambda$15$lambda$14(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$fZNzQAcSItLS29NU8wqLb5a89v0(SettingsHomeView var0, SettingsIndex.Entry var1, View var2) {
      showResults$lambda$22(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$kiGuEuS5FyCxr8__w7S74g4fmqk/* $VF was: $r8$lambda$kiGuEuS5FyCxr8__w7S74g4fmqk*/(
      SettingsHomeView var0, SearchHistory.Item var1, View var2
   ) {
      showHistory$lambda$19(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$mtcsnY3Gg6KCcemUE3PcA2OSwD0(SettingsHomeView var0, SettingsIndex.Entry var1, View var2) {
      return showResults$lambda$23(var0, var1, var2);
   }

   public SettingsHomeView(
      Context var1,
      CarUi var2,
      View var3,
      Function0<? extends List<SettingsIndex.Entry>> var4,
      Function1<? super SettingsIndex.Entry, Unit> var5,
      Function1<? super String, Unit> var6
   ) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "grid");
      Intrinsics.checkNotNullParameter(var4, "extraEntries");
      Intrinsics.checkNotNullParameter(var6, "open");
      super(var1);
      this.ui = var2;
      this.grid = var3;
      this.extraEntries = var4;
      this.addShortcut = var5;
      this.open = var6;
      LinearLayout var8 = new LinearLayout(var1);
      var8.setOrientation(1);
      this.results = var8;
      ScrollView var9 = new ScrollView(var1);
      var9.addView((View)this.results);
      var9.setVisibility(8);
      this.resultsScroll = var9;
      this.entries = CollectionsKt.emptyList();
      this.main = new Handler(Looper.getMainLooper());
      this.prefs = new UiSettings(var1);
      this.history = new SearchHistory(this.prefs.getStr("settings_search_history", "[]"));
      this.setOrientation(1);
      LinearLayout var13 = new LinearLayout(var1);
      var13.setOrientation(0);
      var13.setGravity(16);
      var13.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 18, null, 4, null));
      var13.setPadding(this.ui.dp(18), 0, this.ui.dp(10), 0);
      View var10 = new View(var1);
      var10.setBackground(SvIcon.drawable$default(SvIcon.INSTANCE, "eye", CarUi.Companion.getDIM(), 0, 4, null));
      var13.addView(var10, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.ui.dp(26), this.ui.dp(26))));
      EditText var11 = new EditText(var1);
      var11.setHint("Search every setting - comma and tablet");
      var11.setHintTextColor(CarUi.Companion.getDIM());
      var11.setTextColor(CarUi.Companion.getTEXT());
      var11.setTextSize(20.0F);
      var11.setBackground(null);
      var11.setSingleLine(true);
      var11.setImeOptions(3);
      var11.setPadding(this.ui.dp(14), this.ui.dp(16), this.ui.dp(8), this.ui.dp(16));
      var11.setOnFocusChangeListener(new SettingsHomeView$$ExternalSyntheticLambda0(this));
      var11.setOnClickListener(new SettingsHomeView$$ExternalSyntheticLambda2(this));
      var11.setOnEditorActionListener(new SettingsHomeView$$ExternalSyntheticLambda3(this));
      this.field = var11;
      var13.addView((View)this.field, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      TextView var12 = new TextView(var1);
      var12.setText("✕");
      var12.setTextSize(22.0F);
      var12.setTextColor(CarUi.Companion.getDIM());
      var12.setPadding(this.ui.dp(14), this.ui.dp(8), this.ui.dp(10), this.ui.dp(8));
      var12.setVisibility(8);
      var12.setOnClickListener(new SettingsHomeView$$ExternalSyntheticLambda4(this));
      var13.addView((View)var12);
      View var15 = (View)var13;
      android.widget.LinearLayout.LayoutParams var16 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var16.bottomMargin = this.ui.dp(16);
      Unit var14 = Unit.INSTANCE;
      this.addView(var15, (LayoutParams)var16);
      this.field.addTextChangedListener(new TextWatcher(var12, this) {
         final TextView $clear;
         final SettingsHomeView this$0;

         {
            this.$clear = var1;
            this.this$0 = var2x;
         }

         public void afterTextChanged(Editable var1) {
            TextView var4x = this.$clear;
            CharSequence var5x = (CharSequence)var1;
            byte var3x = 0;
            boolean var2x;
            if (var5x != null && var5x.length() != 0) {
               var2x = false;
            } else {
               var2x = true;
            }

            if (var2x) {
               var3x = 8;
            }

            var4x.setVisibility(var3x);
            this.this$0.startIndex();
            this.this$0.showResults();
         }

         public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }

         public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }
      });
      FrameLayout var7 = new FrameLayout(var1);
      var7.addView(this.grid, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      var7.addView((View)this.resultsScroll, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      this.addView((View)var7, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
   }

   // $VF: synthetic method
   public SettingsHomeView(Context var1, CarUi var2, View var3, Function0 var4, Function1 var5, Function1 var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 16) != 0) {
         var5 = null;
      }

      this(var1, var2, var3, var4, var5, var6);
   }

   static final void lambda$7$lambda$4(SettingsHomeView var0, View var1, boolean var2) {
      if (var2) {
         var0.startIndex();
      }

      var0.showResults();
   }

   static final void lambda$7$lambda$5(SettingsHomeView var0, View var1) {
      var0.showResults();
   }

   static final boolean lambda$7$lambda$6(SettingsHomeView var0, TextView var1, int var2, KeyEvent var3) {
      boolean var4;
      if (var2 == 3) {
         var0.remember();
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   static final void lambda$9$lambda$8(SettingsHomeView var0, View var1) {
      var0.field.setText("");
   }

   // $VF: synthetic method
   public static void query$default(SettingsHomeView var0, String var1, List var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.query(var1, var2);
   }

   private final void remember() {
      String var1 = this.field.getText().toString();
      if (!StringsKt.isBlank(var1)) {
         this.history.record(var1, System.currentTimeMillis());
         this.saveHistory();
      }
   }

   private final void saveHistory() {
      this.prefs.setStr("settings_search_history", this.history.json());
   }

   private final void showHistory() {
      this.grid.setVisibility(8);
      this.resultsScroll.setVisibility(0);
      this.results.removeAllViews();
      List var4 = this.history.list();
      LinearLayout var3 = new LinearLayout(this.getContext());
      var3.setOrientation(0);
      var3.setGravity(16);
      var3.setPadding(this.ui.dp(6), 0, this.ui.dp(6), this.ui.dp(8));
      TextView var5 = new TextView(this.getContext());
      String var2;
      if (var4.isEmpty()) {
         var2 = "No searches yet";
      } else {
         var2 = "Your searches - most used first";
      }

      var5.setText(var2);
      var5.setTextColor(CarUi.Companion.getDIM());
      var5.setTextSize(16.0F);
      var3.addView((View)var5, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      if (var4.size() > 1) {
         TextView var9 = new TextView(this.getContext());
         var9.setText("Clear all");
         var9.setTextColor(CarUi.Companion.getDIM());
         var9.setTextSize(16.0F);
         var9.setPadding(this.ui.dp(12), this.ui.dp(8), this.ui.dp(4), this.ui.dp(8));
         var9.setOnClickListener(new SettingsHomeView$$ExternalSyntheticLambda6(var9, var4, this));
         var3.addView((View)var9);
      }

      this.results.addView((View)var3);
      if (var4.isEmpty()) {
         this.results.addView((View)this.ui.note("What you search for here is kept on the tablet, so it's one tap next time."));
      } else {
         for (SearchHistory.Item var13 : var4) {
            CarUi var6 = this.ui;
            String var14 = var13.getText();
            if (var13.getCount() > 1) {
               int var1 = var13.getCount();
               var2 = "searched " + var1 + " times";
            } else {
               var2 = "searched once";
            }

            SvIcon var8 = SvIcon.INSTANCE;
            Context var7 = this.getContext();
            Intrinsics.checkNotNullExpressionValue(var7, "getContext(...)");
            LinearLayout var11 = (LinearLayout)CarUi.row$default(
                  var6, var14, var2, 0, SvIcon.chip$default(var8, var7, "history", "tablet", 46, null, 16, null), null, 20, null
               )
               .component1();
            var5 = new TextView(this.getContext());
            var5.setText("✕");
            var5.setTextSize(22.0F);
            var5.setTextColor(CarUi.Companion.getDIM());
            var5.setPadding(this.ui.dp(18), this.ui.dp(10), this.ui.dp(10), this.ui.dp(10));
            String var16 = var13.getText();
            var5.setContentDescription("Remove " + var16 + " from the history");
            var5.setOnClickListener(new SettingsHomeView$$ExternalSyntheticLambda7(this, var13));
            var11.addView((View)var5);
            var11.setClickable(true);
            var11.setOnClickListener(new SettingsHomeView$$ExternalSyntheticLambda8(this, var13));
            this.results.addView((View)var11, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
         }
      }
   }

   private static final void showHistory$lambda$16$lambda$15(TextView var0, List var1, SettingsHomeView var2, View var3) {
      Builder var5 = new Builder(var0.getContext());
      int var4 = var1.size();
      var5.setMessage("Clear all " + var4 + " searches?")
         .setPositiveButton("Clear", new SettingsHomeView$$ExternalSyntheticLambda9(var2))
         .setNegativeButton("Keep", null)
         .show();
   }

   private static final void showHistory$lambda$16$lambda$15$lambda$14(SettingsHomeView var0, DialogInterface var1, int var2) {
      var0.history.clear();
      var0.saveHistory();
      var0.showHistory();
   }

   private static final void showHistory$lambda$18$lambda$17(SettingsHomeView var0, SearchHistory.Item var1, View var2) {
      var0.history.remove(var1.getText());
      var0.saveHistory();
      var0.showHistory();
   }

   private static final void showHistory$lambda$19(SettingsHomeView var0, SearchHistory.Item var1, View var2) {
      var0.field.setText(var1.getText());
      var0.field.setSelection(var1.getText().length());
   }

   private final void showResults() {
      String var1 = this.field.getText().toString();
      if (StringsKt.isBlank(var1) && this.field.hasFocus()) {
         this.showHistory();
      } else if (StringsKt.isBlank(var1)) {
         this.resultsScroll.setVisibility(8);
         this.grid.setVisibility(0);
      } else {
         this.grid.setVisibility(8);
         this.resultsScroll.setVisibility(0);
         this.results.removeAllViews();
         List var2 = SettingsIndex.search$default(SettingsIndex.INSTANCE, this.entries, var1, 0, 4, null);
         if (var2.isEmpty()) {
            LinearLayout var15 = this.results;
            CarUi var16 = this.ui;
            if (this.entries.isEmpty()) {
               var1 = "Reading the settings…";
            } else {
               var1 = "Nothing matches \"" + var1 + "\"";
            }

            var15.addView((View)var16.note(var1));
         } else {
            for (SettingsIndex.Entry var4 : var2) {
               SettingIcons.Icon var12 = var4.getIcon();
               SettingIcons.Icon var9 = var12;
               if (var12 == null) {
                  var9 = new SettingIcons.Icon("info", "device", false, 4, null);
               }

               CarUi var5 = this.ui;
               String var13 = var4.getLabel();
               Iterable var7 = CollectionsKt.listOf(new String[]{var4.getWhere(), var4.getValue()});
               var var6 = new ArrayList();

               for (var var7_it : var7) {

                  var7 = var7_it;
                  if (!StringsKt.isBlank((String & CharSequence)var7)) {
                     var6.add(var7);
                  }
               }

               List var17 = var6;
               String var21 = CollectionsKt.joinToString$default(var17, "  ·  ", null, null, 0, null, null, 62, null);
               SvIcon var18 = SvIcon.INSTANCE;
               Context var20 = this.getContext();
               Intrinsics.checkNotNullExpressionValue(var20, "getContext(...)");
               LinearLayout var10 = (LinearLayout)CarUi.row$default(
                     var5, var13, var21, 0, SvIcon.chip$default(var18, var20, var9.getName(), var9.getGroup(), 46, null, 16, null), null, 20, null
                  )
                  .component1();
               TextView var14 = new TextView(this.getContext());
               var14.setText("›");
               var14.setTextSize(30.0F);
               var14.setTextColor(CarUi.Companion.getDIM());
               var14.setPadding(this.ui.dp(14), 0, this.ui.dp(6), 0);
               var10.addView((View)var14);
               var10.setClickable(true);
               var10.setOnClickListener(new SettingsHomeView$$ExternalSyntheticLambda10(this, var4));
               if (this.addShortcut != null) {
                  var10.setOnLongClickListener(new SettingsHomeView$$ExternalSyntheticLambda1(this, var4));
               }

               this.results.addView((View)var10, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
            }
         }
      }
   }

   private static final void showResults$lambda$22(SettingsHomeView var0, SettingsIndex.Entry var1, View var2) {
      var0.remember();
      var0.open.invoke(var1.getTarget());
   }

   private static final boolean showResults$lambda$23(SettingsHomeView var0, SettingsIndex.Entry var1, View var2) {
      var0.remember();
      var0.addShortcut.invoke(var1);
      var0.field.setText("");
      var0.field.clearFocus();
      return true;
   }

   private final void startIndex() {
      if (!this.crawled) {
         this.crawled = true;
         this.entries = this.extraEntries.invoke();
         GalaxyPages.INSTANCE.crawl(new SettingsHomeView$$ExternalSyntheticLambda5(this));
      }
   }

   private static final Unit startIndex$lambda$11(SettingsHomeView var0, List var1) {
      Intrinsics.checkNotNullParameter(var1, "galaxy");
      var0.entries = CollectionsKt.plus(var1, var0.extraEntries.invoke());
      Editable var3 = var0.field.getText();
      Intrinsics.checkNotNullExpressionValue(var3, "getText(...)");
      boolean var2;
      if (((CharSequence)var3).length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var0.showResults();
      }

      return Unit.INSTANCE;
   }

   public final void query(String var1, List<SettingsIndex.Entry> var2) {
      Intrinsics.checkNotNullParameter(var1, "q");
      if (var2 != null) {
         this.entries = var2;
         this.crawled = true;
      }

      this.field.setText(var1);
   }
}
