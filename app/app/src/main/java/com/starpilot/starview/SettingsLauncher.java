package com.starpilot.starview;

import android.animation.TimeInterpolator;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

public final class SettingsLauncher extends FrameLayout {
   public static final SettingsLauncher.Companion Companion = new SettingsLauncher.Companion(null);
   private static int lastPage = 1;
   private final Function0<List<SettingsIndex.Entry>> allPages;
   private final ShortcutBoard board;
   private final LinearLayout dots;
   private final LinearLayout drawer;
   private float drawerDownY;
   private final LinearLayout drawerList;
   private final ScrollView drawerScroll;
   private final View home;
   private final Function1<String, Unit> open;
   private final SwipePager pager;
   private int pickFor;
   private final UiSettings prefs;
   private final CarUi ui;

   // $VF: synthetic method
   public static Unit $r8$lambda$_xHSQfAqtavE9wOKwegkJmfLetc/* $VF was: $r8$lambda$_xHSQfAqtavE9wOKwegkJmfLetc*/(SettingsLauncher var0, int var1) {
      return _init_$lambda$7(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$0IRZeqgvDSSHuLFwVsaKxGD54QQ(SettingsLauncher var0, int var1, View var2) {
      shortcutPage$lambda$19$lambda$18(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5Ns9V21G3V4itmLp1_Rorc6FBOA(SettingsLauncher var0, ShortcutBoard.Item var1) {
      return shortcutPage$lambda$14(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$6TP0MwIosVvqNG2LlO85ujtMXlY(SettingsLauncher var0, SettingsIndex.Entry var1, View var2) {
      fillDrawer$lambda$46(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$7AzzR4vNz7_23jyvp_XGI8XugIY(SettingsLauncher var0, SettingsIndex.Entry var1, View var2) {
      return fillDrawer$lambda$47(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$8WGghI0pQ0IMQ9hsEe4nktgvPJk(SettingsLauncher var0, int var1, int var2, View var3) {
      return shortcutPage$lambda$15(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$BGyC52xUpEEZEqGA7PevpCqQhPk(SettingsLauncher var0, int var1, int var2) {
      return editShortcut$lambda$27(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FxncqzEfwCyrKxOygQHfjrGEvGQ(SettingsLauncher var0, int var1, int var2, int var3) {
      return editShortcut$lambda$25(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$NXvZO8ODejxAf_ot5TaiWiTzyEw/* $VF was: $r8$lambda$NXvZO8ODejxAf_ot5TaiWiTzyEw*/(SettingsLauncher var0, int var1, int var2) {
      return editShortcut$lambda$23(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$O09dWkpkQgI__jBYt48GOoNOxFo(SettingsLauncher var0, int var1, View var2) {
      paintDots$lambda$11$lambda$10(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$PRkg6gtNi83nV_USn0nZOOmMw2c(SettingsLauncher var0, int var1) {
      rebuild$lambda$9(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$PjOlUaf_m8oPk_ZoCs_Ii5Ewa8I/* $VF was: $r8$lambda$PjOlUaf_m8oPk_ZoCs_Ii5Ewa8I*/(
      SettingsLauncher var0, View var1, MotionEvent var2
   ) {
      return buildDrawer$lambda$37(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$XdEEhMS0waMLzv5Smp6ujRP_bJs/* $VF was: $r8$lambda$XdEEhMS0waMLzv5Smp6ujRP_bJs*/(SettingsLauncher var0, View var1) {
      buildDrawer$lambda$35$lambda$33(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$YB_nwSWoQRuXRNaDky_xaK0fdfw/* $VF was: $r8$lambda$YB_nwSWoQRuXRNaDky_xaK0fdfw*/(
      SettingsLauncher var0, int var1, int var2, int var3
   ) {
      return editShortcut$lambda$26(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$lLJOdmqbvBX9WjZEr5ppRmlwUBA(SettingsLauncher var0) {
      closeDrawer$lambda$38(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nqsbznbIOaj4rMYdlbOv0txowUQ(SettingsLauncher var0, int var1, int var2) {
      return editShortcut$lambda$24(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$o2uYwxGDX_e6tKPNpRAqVXk7p48/* $VF was: $r8$lambda$o2uYwxGDX_e6tKPNpRAqVXk7p48*/(SettingsLauncher var0) {
      return _init_$lambda$8(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$ol_oA9RJvHgxS17h9Rzo4Y4ckac/* $VF was: $r8$lambda$ol_oA9RJvHgxS17h9Rzo4Y4ckac*/(
      SettingsLauncher var0, View var1, MotionEvent var2
   ) {
      return buildDrawer$lambda$35$lambda$34(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$qeQuKbgOKPbE38QanzJmiQdNx_Y/* $VF was: $r8$lambda$qeQuKbgOKPbE38QanzJmiQdNx_Y*/(
      ArrayList var0, SettingsLauncher var1, int var2, ShortcutBoard.Item var3, DialogInterface var4, int var5
   ) {
      editShortcut$lambda$31(var0, var1, var2, var3, var4, var5);
   }

   public SettingsLauncher(Context var1, CarUi var2, View var3, Function0<? extends List<SettingsIndex.Entry>> var4, Function1<? super String, Unit> var5) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "home");
      Intrinsics.checkNotNullParameter(var4, "allPages");
      Intrinsics.checkNotNullParameter(var5, "open");
      super(var1);
      this.ui = var2;
      this.home = var3;
      this.allPages = var4;
      this.open = var5;
      this.prefs = new UiSettings(var1);
      this.board = new ShortcutBoard(this.prefs.getStr("settings_shortcuts", "{}"));
      this.pager = new SwipePager(var1, 1);
      LinearLayout var7 = new LinearLayout(var1);
      var7.setOrientation(0);
      var7.setGravity(17);
      this.dots = var7;
      LinearLayout var8 = new LinearLayout(var1);
      var8.setOrientation(1);
      var8.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getFRAME(), 24, null, 4, null));
      var8.setVisibility(8);
      var8.setClickable(true);
      this.drawer = var8;
      LinearLayout var9 = new LinearLayout(var1);
      var9.setOrientation(1);
      this.drawerList = var9;
      ScrollView var10 = new ScrollView(var1);
      var10.addView((View)this.drawerList);
      this.drawerScroll = var10;
      this.pickFor = -1;
      LinearLayout var11 = new LinearLayout(var1);
      var11.setOrientation(1);
      var11.addView((View)this.pager, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
      var11.addView((View)this.dots, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, this.ui.dp(30))));
      TextView var6 = new TextView(var1);
      var6.setText("Swipe sideways for your shortcuts  ·  swipe up for every setting");
      var6.setTextColor(CarUi.Companion.getDIM());
      var6.setTextSize(14.0F);
      var6.setGravity(17);
      var6.setOnClickListener(new SettingsLauncher$$ExternalSyntheticLambda2(this));
      var11.addView((View)var6, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
      this.addView((View)var11, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      this.buildDrawer();
      this.addView((View)this.drawer, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      this.pager.setOnPage(new SettingsLauncher$$ExternalSyntheticLambda3(this));
      this.pager.setOnSwipeUp(new SettingsLauncher$$ExternalSyntheticLambda4(this));
      this.rebuild(RangesKt.coerceAtLeast(lastPage, 0));
   }

   private static final Unit _init_$lambda$7(SettingsLauncher var0, int var1) {
      lastPage = var1;
      var0.paintDots();
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$8(SettingsLauncher var0) {
      var0.openDrawer(-1);
      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void addShortcut$default(SettingsLauncher var0, SettingsIndex.Entry var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = -1;
      }

      var0.addShortcut(var1, var2);
   }

   private final int boardPage(int var1) {
      if (var1 == 0) {
         var1 = 0;
      } else {
         var1--;
      }

      return var1;
   }

   private final void buildDrawer() {
      LinearLayout var2 = new LinearLayout(this.getContext());
      var2.setOrientation(1);
      var2.setGravity(1);
      var2.setPadding(this.ui.dp(16), this.ui.dp(10), this.ui.dp(16), this.ui.dp(6));
      View var1 = new View(var2.getContext());
      var1.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getDIM(), 99, null, 4, null));
      var2.addView(var1, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.ui.dp(70), this.ui.dp(6))));
      var2.setClickable(true);
      var2.setOnClickListener(new SettingsLauncher$$ExternalSyntheticLambda5(this));
      var2.setOnTouchListener(new SettingsLauncher$$ExternalSyntheticLambda6(this));
      this.drawer.addView((View)var2);
      LinearLayout var5 = this.drawer;
      View var6 = (View)this.drawerScroll;
      android.widget.LinearLayout.LayoutParams var4 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
      var4.setMargins(this.ui.dp(14), 0, this.ui.dp(14), this.ui.dp(10));
      Unit var3 = Unit.INSTANCE;
      var5.addView(var6, (LayoutParams)var4);
      this.drawerScroll.setOnTouchListener(new SettingsLauncher$$ExternalSyntheticLambda7(this));
   }

   private static final void buildDrawer$lambda$35$lambda$33(SettingsLauncher var0, View var1) {
      var0.closeDrawer();
   }

   private static final boolean buildDrawer$lambda$35$lambda$34(SettingsLauncher var0, View var1, MotionEvent var2) {
      switch (var2.getActionMasked()) {
         case 0:
            var0.drawerDownY = var2.getRawY();
            break;
         case 1:
            if (var2.getRawY() - var0.drawerDownY > var0.ui.dp(40)) {
               var0.closeDrawer();
               return true;
            }
      }

      return false;
   }

   private static final boolean buildDrawer$lambda$37(SettingsLauncher var0, View var1, MotionEvent var2) {
      switch (var2.getActionMasked()) {
         case 0:
            var0.drawerDownY = var2.getRawY();
            break;
         case 1:
            if (var0.drawerScroll.getScrollY() == 0 && var2.getRawY() - var0.drawerDownY > var0.ui.dp(90)) {
               var0.closeDrawer();
               return true;
            }
      }

      return false;
   }

   private final android.widget.GridLayout.LayoutParams cell(int var1, int var2) {
      android.widget.GridLayout.LayoutParams var3 = new android.widget.GridLayout.LayoutParams(
         GridLayout.spec(var1 / var2, 1.0F), GridLayout.spec(var1 % var2, 1.0F)
      );
      var3.width = 0;
      var3.height = 0;
      var3.setMargins(this.ui.dp(7), this.ui.dp(7), this.ui.dp(7), this.ui.dp(7));
      return var3;
   }

   private static final void closeDrawer$lambda$38(SettingsLauncher var0) {
      var0.drawer.setVisibility(8);
      var0.pickFor = -1;
   }

   private final void editShortcut(int var1, int var2) {
      ShortcutBoard.Item var7 = CollectionsKt.getOrNull(this.board.getPages().get(var1), var2);
      if (var7 != null) {
         ArrayList var5 = new ArrayList();
         if (var2 > 0) {
            var5.add(TuplesKt.to("Move earlier", new SettingsLauncher$$ExternalSyntheticLambda15(this, var1, var2)));
         }

         int var3 = this.board.getPages().get(var1).size();
         int var4 = 1;
         if (var2 < var3 - 1) {
            var5.add(TuplesKt.to("Move later", new SettingsLauncher$$ExternalSyntheticLambda16(this, var1, var2)));
         }

         if (var1 == 1) {
            var3 = 0;
         } else if (var1 > 1) {
            var3 = var1 - 1;
         } else {
            var3 = -1;
         }

         if (var1 != 0) {
            var4 = var1 + 1;
         }

         if (var3 >= 0 && this.board.getPages().get(var3).size() < 12) {
            var5.add(TuplesKt.to("Move to the page on the left", new SettingsLauncher$$ExternalSyntheticLambda17(this, var1, var2, var3)));
         }

         if (var4 < this.board.getPages().size() && this.board.getPages().get(var4).size() < 12) {
            var5.add(TuplesKt.to("Move to the page on the right", new SettingsLauncher$$ExternalSyntheticLambda18(this, var1, var2, var4)));
         }

         var5.add(TuplesKt.to("Remove", new SettingsLauncher$$ExternalSyntheticLambda19(this, var1, var2)));
         Builder var6 = new Builder(this.getContext()).setTitle(var7.getLabel());
         Iterable var9 = var5;
         var var8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var9, 10));
         Iterator var13 = var9.iterator();

         while (var13.hasNext()) {
            var8.add((String)((Pair)var13.next()).getFirst());
         }

         List var11 = var8;
         Collection var12 = var11;
         var6.setItems(var12.toArray(new String[0]), new SettingsLauncher$$ExternalSyntheticLambda1(var5, this, var1, var7))
            .setNegativeButton("Cancel", null)
            .show();
      }
   }

   private static final Unit editShortcut$lambda$23(SettingsLauncher var0, int var1, int var2) {
      var0.board.move(var1, var2, -1);
      return Unit.INSTANCE;
   }

   private static final Unit editShortcut$lambda$24(SettingsLauncher var0, int var1, int var2) {
      var0.board.move(var1, var2, 1);
      return Unit.INSTANCE;
   }

   private static final Unit editShortcut$lambda$25(SettingsLauncher var0, int var1, int var2, int var3) {
      var0.board.toPage(var1, var2, var3 - var1);
      return Unit.INSTANCE;
   }

   private static final Unit editShortcut$lambda$26(SettingsLauncher var0, int var1, int var2, int var3) {
      var0.board.toPage(var1, var2, var3 - var1);
      return Unit.INSTANCE;
   }

   private static final Unit editShortcut$lambda$27(SettingsLauncher var0, int var1, int var2) {
      var0.board.remove(var1, var2);
      return Unit.INSTANCE;
   }

   private static final void editShortcut$lambda$31(ArrayList var0, SettingsLauncher var1, int var2, ShortcutBoard.Item var3, DialogInterface var4, int var5) {
      ((Function0)((Pair)var0.get(var5)).getSecond()).invoke();
      var1.save();
      List var6 = var1.board.getPages();
      var5 = 0;
      Iterator var7 = var6.iterator();

      while (true) {
         if (!var7.hasNext()) {
            var5 = -1;
            break;
         }

         if (((List)var7.next()).contains(var3)) {
            break;
         }

         var5++;
      }

      Integer var8 = var5;
      boolean var11;
      if (var8.intValue() >= 0) {
         var11 = true;
      } else {
         var11 = false;
      }

      if (!var11) {
         var8 = null;
      }

      if (var8 != null) {
         var2 = var8;
      } else {
         var2 = RangesKt.coerceAtMost(var2, var1.board.getPages().size() - 1);
      }

      var1.rebuild(var1.viewPage(var2));
   }

   private final void fillDrawer() {
      this.drawerList.removeAllViews();
      this.drawerScroll.scrollTo(0, 0);
      LinearLayout var5 = this.drawerList;
      TextView var4 = new TextView(this.getContext());
      String var3;
      if (this.pickFor >= 0) {
         var3 = "Pick a setting for your shortcut page";
      } else {
         var3 = "Every setting";
      }

      var4.setText(var3);
      var4.setTextColor(CarUi.Companion.getTEXT());
      var4.setTextSize(22.0F);
      var4.setTypeface(Typeface.DEFAULT_BOLD);
      var4.setPadding(this.ui.dp(6), this.ui.dp(4), this.ui.dp(6), this.ui.dp(2));
      var5.addView((View)var4);
      var5 = this.drawerList;
      CarUi var21 = this.ui;
      if (this.pickFor >= 0) {
         var3 = "Tap one to add it. The search bar above finds single switches too.";
      } else {
         var3 = "Tap to open. Hold to add it to your shortcuts. The search bar above finds single switches too.";
      }

      var5.addView((View)var21.note(var3));
      Iterable var28 = this.allPages.invoke();
      HashSet var22 = new HashSet();
      ArrayList var13 = new ArrayList();

      for (Object var6 : var28) {
         SettingsIndex.Entry var7 = (SettingsIndex.Entry)var6;
         Pair var38 = TuplesKt.to(var7.getTarget(), var7.getLabel());
         if (var22.add(var38)) {
            var13.add(var6);
         }
      }

      List var14 = var13;
      Iterable var15 = var14;
      Map var23 = new LinkedHashMap();
      Iterator var30 = var15.iterator();

      while (true) {
         boolean var2 = var30.hasNext();
         var3 = null;
         if (!var2) {
            for (Entry var31 : var23.entrySet()) {
               String var24 = (String)var31.getKey();
               List var42 = (List)var31.getValue();
               var5 = this.drawerList;
               TextView var8 = new TextView(this.getContext());
               var8.setText(var24);
               var8.setTextColor(CarUi.Companion.getACCENT());
               var8.setTextSize(17.0F);
               var8.setTypeface(Typeface.DEFAULT_BOLD);
               var8.setPadding(this.ui.dp(6), this.ui.dp(16), this.ui.dp(6), this.ui.dp(6));
               var5.addView((View)var8);

               for (SettingsIndex.Entry var44 : CollectionsKt.sortedWith(var42, new Comparator() {
                  @Override
                  public final int compare(T var1, T var2x) {
                     var1 = ((SettingsIndex.Entry)var1).getLabel().toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                     Comparable var4x = var1;
                     var2x = ((SettingsIndex.Entry)var2x).getLabel().toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var2x, "toLowerCase(...)");
                     return ComparisonsKt.compareValues(var4x, var2x);
                  }
               })) {
                  SettingIcons.Icon var33 = var44.getIcon();
                  SettingIcons.Icon var25 = var33;
                  if (var33 == null) {
                     var25 = new SettingIcons.Icon("new", "device", false, 4, null);
                  }

                  CarUi var10 = this.ui;
                  String var9 = var44.getLabel();
                  var var34 = StringsKt.substringAfter(StringsKt.substringAfter$default(var44.getWhere(), " · ", var3, 2, var3), " · ", "");
                  boolean var1;
                  if (var34.length() == 0) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  if (var1) {
                     var34 = var3;
                  }

                  String var11 = var34;
                  SvIcon var19 = SvIcon.INSTANCE;
                  Context var35 = this.getContext();
                  Intrinsics.checkNotNullExpressionValue(var35, "getContext(...)");
                  LinearLayout var26 = (LinearLayout)CarUi.row$default(
                        var10, var9, var11, 0, SvIcon.chip$default(var19, var35, var25.getName(), var25.getGroup(), 46, null, 16, null), null, 20, null
                     )
                     .component1();
                  if (this.board.contains(var44.getTarget())) {
                     TextView var20 = new TextView(this.getContext());
                     var20.setText("on a page");
                     var20.setTextSize(14.0F);
                     var20.setTextColor(CarUi.Companion.getDIM());
                     var20.setPadding(this.ui.dp(10), 0, this.ui.dp(10), 0);
                     var26.addView((View)var20);
                  }

                  var26.setClickable(true);
                  var26.setOnClickListener(new SettingsLauncher$$ExternalSyntheticLambda13(this, var44));
                  var26.setOnLongClickListener(new SettingsLauncher$$ExternalSyntheticLambda14(this, var44));
                  this.drawerList.addView((View)var26, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
                  var3 = null;
               }
            }

            return;
         }

         Object var36 = var30.next();
         SettingsIndex.Entry var39 = (SettingsIndex.Entry)var36;
         var3 = StringsKt.substringBefore(var39.getWhere(), " · ", var39.getWhere());
         String var40 = StringsKt.substringBefore$default(StringsKt.substringAfter$default(var39.getWhere(), " · ", null, 2, null), " · ", null, 2, null);
         String var41 = var3 + " · " + var40;
         var3 = (String)var23.get(var41);
         if (var3 == null) {
            var3 = new ArrayList();
            var23.put(var41, var3);
         }

         ((List)var3).add(var36);
      }
   }

   private static final void fillDrawer$lambda$46(SettingsLauncher var0, SettingsIndex.Entry var1, View var2) {
      if (var0.pickFor >= 0) {
         int var3 = var0.pickFor;
         var0.closeDrawer();
         var0.addShortcut(var1, var3);
      } else {
         var0.closeDrawer();
         var0.open.invoke(var1.getTarget());
      }
   }

   private static final boolean fillDrawer$lambda$47(SettingsLauncher var0, SettingsIndex.Entry var1, View var2) {
      var0.closeDrawer();
      var0.addShortcut(var1, var0.pickFor);
      return true;
   }

   static final void lambda$6$lambda$5(SettingsLauncher var0, View var1) {
      var0.openDrawer(-1);
   }

   private final void paintDots() {
      this.dots.removeAllViews();
      int var2 = 0;

      for (int var5 = this.pager.getChildCount(); var2 < var5; var2++) {
         LinearLayout var8 = this.dots;
         View var9 = new View(this.getContext());
         CarUi var7 = this.ui;
         int var3;
         if (var2 == this.pager.getIndex()) {
            var3 = CarUi.Companion.getTEXT();
         } else {
            var3 = CarUi.Companion.getDIM();
         }

         var9.setBackground((Drawable)CarUi.rounded$default(var7, var3, 99, null, 4, null));
         float var1;
         if (var2 == this.pager.getIndex()) {
            var1 = 1.0F;
         } else {
            var1 = 0.5F;
         }

         var9.setAlpha(var1);
         String var13;
         if (var2 == 1) {
            var13 = "Settings home";
         } else {
            var13 = "Shortcut page";
         }

         var9.setContentDescription(var13);
         var9.setOnClickListener(new SettingsLauncher$$ExternalSyntheticLambda11(this, var2));
         var7 = this.ui;
         byte var4 = 14;
         byte var11;
         if (var2 == 1) {
            var11 = 14;
         } else {
            var11 = 10;
         }

         int var6 = var7.dp(var11);
         var7 = this.ui;
         if (var2 == 1) {
            var11 = var4;
         } else {
            var11 = 10;
         }

         android.widget.LinearLayout.LayoutParams var10 = new android.widget.LinearLayout.LayoutParams(var6, var7.dp(var11));
         var10.setMargins(this.ui.dp(6), 0, this.ui.dp(6), 0);
         Unit var16 = Unit.INSTANCE;
         var8.addView(var9, (LayoutParams)var10);
      }
   }

   private static final void paintDots$lambda$11$lambda$10(SettingsLauncher var0, int var1, View var2) {
      SwipePager.go$default(var0.pager, var1, false, 2, null);
   }

   private final void rebuild(int var1) {
      ViewParent var4 = this.home.getParent();
      ViewGroup var5;
      if (var4 instanceof ViewGroup) {
         var5 = (ViewGroup)var4;
      } else {
         var5 = null;
      }

      if (var5 != null) {
         var5.removeView(this.home);
      }

      this.pager.removeAllViews();
      this.pager.addView(this.shortcutPage(0));
      this.pager.addView(this.home);
      int var2 = 1;

      for (int var3 = this.board.getPages().size(); var2 < var3; var2++) {
         this.pager.addView(this.shortcutPage(var2));
      }

      this.pager.post(new SettingsLauncher$$ExternalSyntheticLambda12(this, var1));
      this.paintDots();
   }

   private static final void rebuild$lambda$9(SettingsLauncher var0, int var1) {
      var0.pager.go(RangesKt.coerceIn(var1, 0, var0.pager.getChildCount() - 1), false);
      lastPage = var0.pager.getIndex();
      var0.paintDots();
   }

   private final void save() {
      this.prefs.setStr("settings_shortcuts", this.board.json());
   }

   private final View shortcutPage(int var1) {
      List var6 = this.board.getPages().get(var1);
      GridLayout var5 = new GridLayout(this.getContext());
      var5.setColumnCount(4);
      var5.setRowCount(3);
      var5.setUseDefaultMargins(false);
      Iterator var4 = var6.iterator();
      int var2 = 0;

      while (var4.hasNext()) {
         int var3 = var2 + 1;
         ShortcutBoard.Item var7 = (ShortcutBoard.Item)var4.next();
         LinearLayout var14 = this.ui
            .iconTile(var7.getLabel(), var7.getWhere(), var7.getIcon(), var7.getGroup(), new SettingsLauncher$$ExternalSyntheticLambda8(this, var7))
            .component1();
         var14.setOnLongClickListener(new SettingsLauncher$$ExternalSyntheticLambda9(this, var1, var2));
         var5.addView((View)var14, (LayoutParams)this.cell(var2, 4));
         var2 = var3;
      }

      if (var6.size() < 12) {
         LinearLayout var15 = new LinearLayout(this.getContext());
         var15.setOrientation(1);
         var15.setGravity(17);
         var15.setBackground((Drawable)this.ui.rounded(CarUi.Companion.getCARD(), 20, CarUi.Companion.getDIM()));
         TextView var10 = new TextView(var15.getContext());
         var10.setText("+");
         var10.setTextSize(46.0F);
         var10.setTextColor(CarUi.Companion.getDIM());
         var10.setGravity(17);
         var15.addView((View)var10);
         TextView var8 = new TextView(var15.getContext());
         String var11;
         if (var6.isEmpty()) {
            var11 = "Add a shortcut";
         } else {
            var11 = "Add";
         }

         var8.setText(var11);
         var8.setTextSize(17.0F);
         var8.setTextColor(CarUi.Companion.getDIM());
         var8.setGravity(17);
         var15.addView((View)var8);
         var15.setClickable(true);
         var15.setOnClickListener(new SettingsLauncher$$ExternalSyntheticLambda10(this, var1));
         var15.setContentDescription("Add a shortcut to this page");
         var5.addView((View)var15, (LayoutParams)this.cell(var6.size(), 4));

         for (int var9 = var6.size() + 1; var9 < 12; var9++) {
            var5.addView(new View(this.getContext()), (LayoutParams)this.cell(var9, 4));
         }
      }

      LinearLayout var12 = new LinearLayout(this.getContext());
      var12.setOrientation(1);
      if (var6.isEmpty()) {
         TextView var13 = new TextView(var12.getContext());
         var13.setText("Your shortcuts: tap + and pick any setting, or hold one in the list that slides up. Hold a shortcut to move or remove it.");
         var13.setTextColor(CarUi.Companion.getDIM());
         var13.setTextSize(16.0F);
         var13.setPadding(this.ui.dp(10), 0, this.ui.dp(10), this.ui.dp(8));
         var12.addView((View)var13);
      }

      var12.addView((View)var5, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
      return (View)var12;
   }

   private static final Unit shortcutPage$lambda$14(SettingsLauncher var0, ShortcutBoard.Item var1) {
      var0.open.invoke(var1.getTarget());
      return Unit.INSTANCE;
   }

   private static final boolean shortcutPage$lambda$15(SettingsLauncher var0, int var1, int var2, View var3) {
      var0.editShortcut(var1, var2);
      return true;
   }

   private static final void shortcutPage$lambda$19$lambda$18(SettingsLauncher var0, int var1, View var2) {
      var0.openDrawer(var1);
   }

   private final int viewPage(int var1) {
      if (var1 == 0) {
         var1 = 0;
      } else {
         var1++;
      }

      return var1;
   }

   public final void addShortcut(SettingsIndex.Entry var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "e");
      if (this.board.contains(var1.getTarget())) {
         Context var9 = this.getContext();
         String var6 = var1.getLabel();
         Toast.makeText(var9, var6 + " is already a shortcut", 0).show();
      } else {
         SettingIcons.Icon var4 = var1.getIcon();
         SettingIcons.Icon var3 = var4;
         if (var4 == null) {
            var3 = new SettingIcons.Icon("new", "device", false, 4, null);
         }

         if (var2 < 0) {
            if (this.pager.getIndex() != 1) {
               var2 = this.boardPage(this.pager.getIndex());
            } else {
               var2 = -1;
            }
         }

         var2 = this.board.add(new ShortcutBoard.Item(var1.getLabel(), var1.getWhere(), var1.getTarget(), var3.getName(), var3.getGroup()), var2);
         this.save();
         Context var8 = this.getContext();
         String var5 = var1.getLabel();
         Toast.makeText(var8, var5 + " added to your shortcuts", 0).show();
         this.rebuild(this.viewPage(var2));
      }
   }

   public final void closeDrawer() {
      if (this.getDrawerOpen()) {
         this.drawer.animate().translationY(this.getHeight()).setDuration(180L).withEndAction(new SettingsLauncher$$ExternalSyntheticLambda0(this)).start();
      }
   }

   public final boolean getDrawerOpen() {
      boolean var1;
      if (this.drawer.getVisibility() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final void openDrawer(int var1) {
      this.pickFor = var1;
      this.fillDrawer();
      this.drawer.setVisibility(0);
      this.drawer.setTranslationY(RangesKt.coerceAtLeast(this.getHeight(), 1.0F));
      this.drawer.animate().translationY(0.0F).setDuration(220L).setInterpolator((TimeInterpolator)(new DecelerateInterpolator())).start();
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final int getLastPage() {
         return SettingsLauncher.lastPage;
      }

      public final void setLastPage(int var1) {
         SettingsLauncher.lastPage = var1;
      }
   }
}
