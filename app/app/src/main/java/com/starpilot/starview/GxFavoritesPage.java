package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxFavoritesPage extends GxPage {
   private final String actionPrefix;
   private LinearLayout content;
   private boolean isMetric;
   private boolean loading;
   private List<? extends JSONObject> options;
   private List<GxFavoritesPage.Slot> savedSlots;
   private boolean saving;
   private final String setSpeed;
   private List<GxFavoritesPage.Slot> slots;
   private Runnable speedTimer;
   private final HashMap<String, Object> values;

   // $VF: synthetic method
   public static GxFavoritesPage.Slot $r8$lambda$5HU_x8ji956mUtVzqMdAQ7KtqCI(boolean var0, GxFavoritesPage.Slot var1) {
      return render$lambda$51$lambda$50(var0, var1);
   }

   // $VF: synthetic method
   public static int $r8$lambda$8BYGRxBaV0HIk_zUgBVcL2l9Mho(JSONObject var0, JSONObject var1) {
      return sortOptions$lambda$10(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$9N_4hXYRLSGd60qN13o9Iw3cqYc(GxFavoritesPage var0, int var1, double var2) {
      render$lambda$49$lambda$48(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$A5ZWLxOV4xTizAhyMKLloZGVy5U(
      List var0,
      HashSet var1,
      String var2,
      LinearLayout var3,
      EditText var4,
      Map var5,
      TextView var6,
      GxFavoritesPage var7,
      List var8,
      String var9,
      AlertDialog var10,
      int var11,
      HashMap var12,
      View var13
   ) {
      chooseControl$renderList$lambda$86(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KIXzPcbhGwgI1V9HBCy5taZGPxg(GxFavoritesPage var0, String var1, JSONObject var2) {
      return runAction$lambda$33(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$KvJtrjm06csLjTLLPyLrwYkGfw0(GxFavoritesPage var0, String var1, AlertDialog var2, int var3, View var4) {
      chooseControl$item$lambda$77(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$KxWQ97RMCEsmXTD1fd8KD75jd_U(GxFavoritesPage var0, GxFavoritesPage.Slot var1, CompoundButton var2, boolean var3) {
      render$lambda$40(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static GxFavoritesPage.Slot $r8$lambda$LfWD0nF3OlgpdvajXkjxK_QU5jw/* $VF was: $r8$lambda$LfWD0nF3OlgpdvajXkjxK_QU5jw*/(
      String var0, GxFavoritesPage.Slot var1
   ) {
      return chooseControl$item$lambda$77$lambda$76(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Pd7VWyDWlXVTZ97jGiel40YqkqI(GxFavoritesPage var0, int var1, boolean var2) {
      return render$lambda$43(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PpM7nHiHAfhky_pRNx6WSkqJA2U(GxFavoritesPage var0, int var1, boolean var2) {
      return render$lambda$51(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QCAeznAZhZmi7YZv9ZBLSTP_ZtE/* $VF was: $r8$lambda$QCAeznAZhZmi7YZv9ZBLSTP_ZtE*/(
      GxFavoritesPage var0, String var1, JSONObject var2
   ) {
      return refreshValues$lambda$18(var0, var1, var2);
   }

   // $VF: synthetic method
   public static GxFavoritesPage.Slot $r8$lambda$RTEQfe9AtxUoI0Q_GLo6WikdqT0(boolean var0, GxFavoritesPage.Slot var1) {
      return render$lambda$43$lambda$42(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Rt0Kiqf645Tven2NnGWpfmKo2ws(GxFavoritesPage var0, String var1, JSONObject var2) {
      return saveSlots$lambda$23(var0, var1, var2);
   }

   // $VF: synthetic method
   public static GxFavoritesPage.Slot $r8$lambda$V6mGe2B1C6whqQj2ZqplX7ck7cw(double var0, GxFavoritesPage.Slot var2) {
      return render$lambda$49$lambda$48$lambda$47(var0, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$W_cYBkn_lxm8gXgepvjm9rW8upE/* $VF was: $r8$lambda$W_cYBkn_lxm8gXgepvjm9rW8upE*/(
      GxFavoritesPage var0, GxFavoritesPage.Slot var1
   ) {
      return render$lambda$38(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$aWCv1mvgDIyQbVmBFV9bByKidGc(GxFavoritesPage var0, int var1, double var2) {
      return render$lambda$49(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$ad1M_obSO5825nU08Nd2rMUAQ9k(double var0) {
      return render$lambda$45(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cSfmLgrS9CmvYw3moYXU2QwPYyw(GxFavoritesPage var0, String var1, JSONObject var2) {
      return load$lambda$17(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hLy2Zv0gaT38ExO8ptXfEOcOrLo(GxFavoritesPage var0, String var1, Object var2, String var3, JSONObject var4) {
      return toggleValue$lambda$29(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lkSLqIFbL5BROBLPkD7i7_topYo(GxFavoritesPage var0) {
      return build$lambda$5(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mvSftvmQ2N8hSFnzmr3AUr8me2g(GxFavoritesPage var0, int var1) {
      return render$lambda$44(var0, var1);
   }

   // $VF: synthetic method
   public static int $r8$lambda$nVnLF5hRggxxmxkOFpgc6Nb_ELA/* $VF was: $r8$lambda$nVnLF5hRggxxmxkOFpgc6Nb_ELA*/(Function2 var0, Object var1, Object var2) {
      return sortOptions$lambda$11(var0, var1, var2);
   }

   public GxFavoritesPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.actionPrefix = "__starpilot_favorite_action__:";
      this.setSpeed = "__starpilot_controller_action__:set_speed";
      this.loading = true;
      ArrayList var4 = new ArrayList(3);

      for (int var3 = 0; var3 < 3; var3++) {
         var4.add(new GxFavoritesPage.Slot(false, false, null, null, null, 31, null));
      }

      this.slots = var4;
      ArrayList var5 = new ArrayList(3);

      for (int var6 = 0; var6 < 3; var6++) {
         var5.add(new GxFavoritesPage.Slot(false, false, null, null, null, 31, null));
      }

      this.savedSlots = var5;
      this.options = CollectionsKt.emptyList();
      this.values = new HashMap<>();
   }

   private static final Unit build$lambda$5(GxFavoritesPage var0) {
      var0.refreshValues();
      return Unit.INSTANCE;
   }

   private final Pair<String, List<String>> categoryOf(JSONObject var1) {
      List var5;
      label176: {
         GalaxyApi var3 = this.getApi();
         if (var3 != null) {
            List var19 = var3.getCatalog();
            var5 = var19;
            if (var19 != null) {
               break label176;
            }
         }

         var5 = CollectionsKt.emptyList();
      }

      String var4;
      String var6;
      label171: {
         label170: {
            label169: {
               var6 = var1.optString("key");
               if (var6 != null) {
                  switch (var6.hashCode()) {
                     case -1223291282:
                        if (var6.equals("ConditionalExperimental")) {
                           break label169;
                        }
                        break;
                     case -1119957410:
                        if (var6.equals("ConditionalChill")) {
                           break label169;
                        }
                        break;
                     case -878798333:
                        if (var6.equals("__starpilot_controller_action__:cycle_driving_personality")) {
                           break label170;
                        }
                        break;
                     case -370744618:
                        if (var6.equals("__starpilot_favorite_action__:toggle_traffic_mode")) {
                           break label170;
                        }
                        break;
                     case 499325643:
                        if (var6.equals("ExperimentalMode")) {
                           break label169;
                        }
                  }
               }

               var4 = var6;
               break label171;
            }

            var4 = "LongitudinalControlMode";
            break label171;
         }

         var4 = "CustomPersonalities";
      }

      ListIterator var7 = var5.listIterator(var5.size());

      boolean var2;
      Object var20;
      label155:
      do {
         if (!var7.hasPrevious()) {
            var20 = null;
            break;
         }

         var20 = (GalaxyApi.Category)var7.previous();
         var var8 = var20.getParams();
         if (var8 instanceof Collection && ((Collection)var8).isEmpty()) {
            var2 = false;
         } else {
            Iterator var31 = var8.iterator();

            while (var31.hasNext()) {
               if (Intrinsics.areEqual(((GalaxyApi.Param)var31.next()).getKey(), var4)) {
                  var2 = true;
                  continue label155;
               }
            }

            var2 = false;
         }
      } while (!var2);

      GalaxyApi.Category var27 = var20;
      var20 = var27;
      if (var27 == null) {
         if (Intrinsics.areEqual(var4, "LongitudinalControlMode")) {
            ListIterator var24 = var5.listIterator(var5.size());

            label134:
            do {
               if (!var24.hasPrevious()) {
                  var20 = null;
                  break;
               }

               var20 = (GalaxyApi.Category)var24.previous();
               var var28 = var20.getParams();
               if (var28 instanceof Collection && ((Collection)var28).isEmpty()) {
                  var2 = false;
               } else {
                  Iterator var29 = var28.iterator();

                  while (var29.hasNext()) {
                     if (Intrinsics.areEqual(((GalaxyApi.Param)var29.next()).getKey(), "ConditionalExperimental")) {
                        var2 = true;
                        continue label134;
                     }
                  }

                  var2 = false;
               }
            } while (!var2);

            var20 = var20;
         } else {
            var20 = null;
         }
      }

      if (var20 == null) {
         String var14;
         if (Intrinsics.areEqual(var1.optString("section"), "Actions")) {
            var14 = "Controller Actions";
         } else {
            var var15 = var1.optString("section");
            if (var15.length() == 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (var2) {
               var15 = "Other";
            }

            var14 = var15;
         }

         return TuplesKt.to(var14, CollectionsKt.emptyList());
      } else {
         Iterable var9 = var20.getParams();
         Map var25 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var9, 10)), 16));

         for (Object var30 : var9) {
            var25.put(((GalaxyApi.Param)var30).getKey(), var30);
         }

         GalaxyApi.Param var11 = (GalaxyApi.Param)var25.get(var4);
         if (var11 != null && var11.isParentToggle()) {
            var2 = true;
         } else {
            var2 = false;
         }

         label107:
         if (!var2 && Intrinsics.areEqual(var4, var6)) {
            if (var11 != null) {
               String var12 = var11.getParentKey();
               if (var12 != null) {
                  var11 = (GalaxyApi.Param)var25.get(var12);
                  break label107;
               }
            }

            var11 = null;
         }

         ArrayList var26 = new ArrayList();
         HashSet var23 = new HashSet();

         while (var11 != null && var23.add(var11.getKey())) {
            var26.add(0, var11.getLabel());
            String var13 = var11.getParentKey();
            if (var13 != null) {
               var11 = (GalaxyApi.Param)var25.get(var13);
            } else {
               var11 = null;
            }
         }

         return TuplesKt.to(var20.getName(), var26);
      }
   }

   private final void chooseControl(int var1) {
      GxFavoritesPage.Slot var8 = this.slots.get(var1);
      String var5 = var8.getKey();
      String var7 = "";
      if (var5 == null) {
         var5 = "";
      }

      String var4;
      TextView var9;
      JSONObject var10;
      LinearLayout var11;
      label86: {
         var11 = new LinearLayout((Context)this.getAct());
         var11.setOrientation(1);
         var11.setPadding(this.getUi().dp(20), this.getUi().dp(8), this.getUi().dp(20), 0);
         var11.setBackgroundColor(CarUi.Companion.getBG());
         var10 = this.optionByKey(var8.getKey());
         var9 = new TextView((Context)this.getAct());
         if (var10 != null) {
            String var6 = var10.optString("label");
            var4 = var6;
            if (var6 != null) {
               break label86;
            }
         }

         var4 = var8.getLabel();
         boolean var2;
         if (var4.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            var4 = null;
         }

         String var25 = var4;
         var4 = var25;
         if (var25 == null) {
            var25 = var8.getKey();
            var4 = var25;
            if (var25 == null) {
               var4 = "Not configured";
            }
         }
      }

      String var27 = var7;
      if (var8.getKey() != null) {
         var27 = var7;
         if (var10 == null) {
            var27 = " (unavailable)";
         }
      }

      var9.setText("Current: " + var4 + var27);
      var9.setTextSize(17.0F);
      var9.setTextColor(CarUi.Companion.getTEXT());
      var11.addView((View)var9);
      EditText var36 = new EditText((Context)this.getAct());
      var36.setHint("Search by name, setting or description");
      var36.setTextSize(19.0F);
      var36.setTextColor(CarUi.Companion.getTEXT());
      var36.setHintTextColor(CarUi.Companion.getDIM());
      var36.setSingleLine();
      var11.addView((View)var36);
      TextView var38 = new TextView((Context)this.getAct());
      var38.setTextSize(15.0F);
      var38.setTextColor(CarUi.Companion.getDIM());
      var38.setPadding(0, this.getUi().dp(4), 0, this.getUi().dp(4));
      var11.addView((View)var38);
      LinearLayout var35 = new LinearLayout((Context)this.getAct());
      var35.setOrientation(1);
      ScrollView var37 = new ScrollView((Context)this.getAct());
      var37.addView((View)var35);
      var11.addView(
         (View)var37,
         (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, (int)(this.getAct().getResources().getDisplayMetrics().heightPixels * 0.55)))
      );
      AlertDialog var39 = new Builder((Context)this.getAct())
         .setTitle("Favorite #" + (var1 + 1))
         .setView((View)var11)
         .setNegativeButton("Cancel", null)
         .create();
      HashSet var12 = new HashSet();
      Iterable var28 = this.options;
      LinkedHashMap var18 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var28, 10)), 16));

      for (Object var29 : var28) {
         var18.put(var29, this.categoryOf((JSONObject)var29));
      }

      Map var40;
      label71: {
         var40 = var18;
         GalaxyApi var19 = this.getApi();
         if (var19 != null) {
            List var30 = var19.getCatalog();
            var20 = var30;
            if (var30 != null) {
               break label71;
            }
         }

         var20 = CollectionsKt.emptyList();
      }

      Iterable var31 = var20;
      var var21 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var31, 10));
      Iterator var32 = var31.iterator();

      while (var32.hasNext()) {
         var21.add(((GalaxyApi.Category)var32.next()).getName());
      }

      List var14;
      HashMap var15;
      int var16;
      label60: {
         var14 = var21;
         var15 = new HashMap();
         boolean var3 = false;
         var16 = 0;
         GalaxyApi var22 = this.getApi();
         if (var22 != null) {
            List var33 = var22.getCatalog();
            var23 = var33;
            if (var33 != null) {
               break label60;
            }
         }

         var23 = CollectionsKt.emptyList();
      }

      Iterator var24 = var23.iterator();

      while (var24.hasNext()) {
         for (Iterator var34 = ((GalaxyApi.Category)var24.next()).getParams().iterator(); var34.hasNext(); var16++) {
            var15.put(((GalaxyApi.Param)var34.next()).getLabel(), var16);
         }
      }

      var36.addTextChangedListener(
         new TextWatcher(var37, var35, var36, var40, var38, this, var14, var12, var5, var39, var1, var15) {
            final Map<JSONObject, Pair<String, List<String>>> $cats;
            final TextView $count;
            final String $cur;
            final AlertDialog $dlg;
            final HashSet<String> $expanded;
            final int $index;
            final List<String> $layoutOrder;
            final LinearLayout $list;
            final HashMap<String, Integer> $paramOrder;
            final EditText $search;
            final ScrollView $sv;
            final GxFavoritesPage this$0;

            {
               this.$sv = var1;
               this.$list = var2x;
               this.$search = var3;
               this.$cats = var4x;
               this.$count = var5x;
               this.this$0 = var6;
               this.$layoutOrder = var7x;
               this.$expanded = var8x;
               this.$cur = var9x;
               this.$dlg = var10x;
               this.$index = var11x;
               this.$paramOrder = var12x;
            }

            public void afterTextChanged(Editable var1) {
               GxFavoritesPage.chooseControl$renderList(
                  this.$list,
                  this.$search,
                  this.$cats,
                  this.$count,
                  this.this$0,
                  this.$layoutOrder,
                  this.$expanded,
                  this.$cur,
                  this.$dlg,
                  this.$index,
                  this.$paramOrder
               );
               this.$sv.scrollTo(0, 0);
            }

            public void beforeTextChanged(CharSequence var1, int var2x, int var3, int var4x) {
            }

            public void onTextChanged(CharSequence var1, int var2x, int var3, int var4x) {
            }
         }
      );
      chooseControl$renderList(var35, var36, var40, var38, this, var14, var12, var5, var39, var1, var15);
      var39.show();
   }

   private static final void chooseControl$item(GxFavoritesPage var0, LinearLayout var1, String var2, AlertDialog var3, int var4, JSONObject var5, String var6) {
      String var8;
      String var10;
      label53: {
         var10 = "";
         if (var5 != null) {
            String var9 = var5.optString("key");
            var8 = var9;
            if (var9 != null) {
               break label53;
            }
         }

         var8 = "";
      }

      LinearLayout var11 = new LinearLayout((Context)var0.getAct());
      var11.setOrientation(1);
      CarUi var12 = var0.getUi();
      int var7;
      if (Intrinsics.areEqual(var8, var2)) {
         var7 = CarUi.Companion.getCARD_ON();
      } else {
         var7 = CarUi.Companion.getCARD();
      }

      Integer var24;
      if (Intrinsics.areEqual(var8, var2)) {
         var24 = CarUi.Companion.getACCENT();
      } else {
         var24 = null;
      }

      var11.setBackground((Drawable)var12.rounded(var7, 12, var24));
      var11.setPadding(var0.getUi().dp(16), var0.getUi().dp(10), var0.getUi().dp(16), var0.getUi().dp(10));
      var11.setMinimumHeight(var0.getUi().dp(60));
      var11.setClickable(true);
      TextView var28 = new TextView((Context)var0.getAct());
      String var25 = var10;
      if (Intrinsics.areEqual(var8, var2)) {
         var25 = "✓ ";
      }

      label47: {
         if (var5 != null) {
            var10 = var5.optString("label");
            var2 = var10;
            if (var10 != null) {
               break label47;
            }
         }

         var2 = "Not configured";
      }

      var28.setText(var25 + var2);
      var28.setTextSize(18.0F);
      var28.setTextColor(CarUi.Companion.getTEXT());
      var28.setTypeface(Typeface.DEFAULT_BOLD);
      var11.addView((View)var28);
      if (var5 == null) {
         var2 = "Clear this assignment";
      } else {
         Iterable var20 = CollectionsKt.listOf(new String[]{var6, var5.optString("description")});
         var var16 = new ArrayList();

         for (Object var21 : var20) {
            String var26 = (String)var21;
            Intrinsics.checkNotNull(var26);
            if (!StringsKt.isBlank(var26)) {
               var16.add(var21);
            }
         }

         List var17 = var16;
         var2 = CollectionsKt.joinToString$default(var17, " · ", null, null, 0, null, null, 62, null);
      }

      if (!StringsKt.isBlank(var2)) {
         TextView var22 = new TextView((Context)var0.getAct());
         var22.setText(var2);
         var22.setTextSize(14.0F);
         var22.setTextColor(CarUi.Companion.getDIM());
         var11.addView((View)var22);
      }

      var11.setOnClickListener(new GxFavoritesPage$$ExternalSyntheticLambda19(var0, var8, var3, var4));
      View var19 = (View)var11;
      android.widget.LinearLayout.LayoutParams var18 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var18.bottomMargin = var0.getUi().dp(6);
      Unit var13 = Unit.INSTANCE;
      var1.addView(var19, (LayoutParams)var18);
   }

   private static final void chooseControl$item$lambda$77(GxFavoritesPage var0, String var1, AlertDialog var2, int var3, View var4) {
      label42: {
         if (!var0.saving && !var0.loading) {
            int var5 = var1.length();
            boolean var6 = false;
            boolean var9;
            if (var5 > 0) {
               var9 = true;
            } else {
               var9 = false;
            }

            if (!var9) {
               break label42;
            }

            var var7 = var0.options;
            if (var7 instanceof Collection && ((Collection)var7).isEmpty()) {
               var9 = true;
            } else {
               Iterator var8 = var7.iterator();

               while (true) {
                  if (!var8.hasNext()) {
                     var9 = true;
                     break;
                  }

                  if (Intrinsics.areEqual(((JSONObject)var8.next()).optString("key"), var1)) {
                     var9 = var6;
                     break;
                  }
               }
            }

            if (!var9) {
               break label42;
            }
         }

         return;
      }

      var2.dismiss();
      var0.updateSlot(var3, true, new GxFavoritesPage$$ExternalSyntheticLambda6(var1));
   }

   private static final GxFavoritesPage.Slot chooseControl$item$lambda$77$lambda$76(String var0, GxFavoritesPage.Slot var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0 = var0;
      boolean var2;
      if (var0.length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var0 = null;
      }

      return GxFavoritesPage.Slot.copy$default(var1, false, false, var0, null, null, 27, null);
   }

   private static final void chooseControl$renderList(
      LinearLayout var0,
      EditText var1,
      Map<JSONObject, ? extends Pair<String, ? extends List<String>>> var2,
      TextView var3,
      GxFavoritesPage var4,
      List<String> var5,
      HashSet<String> var6,
      String var7,
      AlertDialog var8,
      int var9,
      HashMap<String, Integer> var10
   ) {
      var0.removeAllViews();
      String var17 = StringsKt.trim(var1.getText().toString()).toString();
      Locale var15 = Locale.ROOT;
      String var16 = "ROOT";
      Intrinsics.checkNotNullExpressionValue(var15, "ROOT");
      var17 = var17.toLowerCase(var15);
      String var32 = "toLowerCase(...)";
      Intrinsics.checkNotNullExpressionValue(var17, "toLowerCase(...)");
      CharSequence var49 = var17;
      Regex var18 = new Regex("\\s+");
      Iterable var55 = var18.split(var49, 0);
      var var50 = new ArrayList();

      for (Object var56 : var55) {
         boolean var11;
         if (((String)var56).length() > 0) {
            var11 = true;
         } else {
            var11 = false;
         }

         if (var11) {
            var50.add(var56);
         }
      }

      List var21 = var50;
      Iterable var51 = var2.keySet();
      boolean var14 = false;
      var var23 = new ArrayList();

      for (Object var24 : var51) {
         JSONObject var57 = (JSONObject)var24;
         Object var68 = var2.get(var57);
         Intrinsics.checkNotNull(var68);
         Pair var20 = (Pair)var68;
         var68 = (String)var20.component1();
         List var25 = (List)var20.component2();
         String[] var75 = new String[]{
            var57.optString("label"),
            var57.optString("key"),
            var57.optString("description"),
            var57.optString("picker_label"),
            var57.optString("picker_description"),
            (String)var68
         };
         String var58 = CollectionsKt.joinToString$default(CollectionsKt.plus(CollectionsKt.listOf(var75), var25), " ", null, null, 0, null, null, 62, null);
         var68 = Locale.ROOT;
         Intrinsics.checkNotNullExpressionValue(var68, var16);
         String var26 = var58.toLowerCase((Locale)var68);
         Intrinsics.checkNotNullExpressionValue(var26, var32);
         var var76 = var21;
         boolean var29;
         Iterable var34;
         if (var76 instanceof Collection && ((Collection)var76).isEmpty()) {
            var17 = var16;
            var29 = true;
            var34 = var51;
            var16 = var32;
         } else {
            Iterator var86 = var76.iterator();

            while (true) {
               if (!var86.hasNext()) {
                  var29 = true;
                  var17 = var16;
                  var16 = var32;
                  var34 = var51;
                  break;
               }

               String var59 = (String)var86.next();
               CharSequence var27 = var26;
               CharSequence var33 = var59;
               if (!StringsKt.contains$default(var27, var33, false, 2, null)) {
                  var29 = false;
                  var34 = var51;
                  var16 = var32;
                  var17 = var16;
                  break;
               }

               var32 = var32;
               var16 = var16;
               var51 = var51;
            }
         }

         if (var29) {
            var23.add(var24);
         }

         var16 = var17;
         var51 = var34;
         var32 = var16;
      }

      byte var30 = 6;
      List var54 = var23;
      CharSequence var35;
      if (var4.saving) {
         var35 = "Selection is currently unavailable.";
      } else {
         int var12 = var54.size();
         String var36;
         if (var54.size() == 1) {
            var36 = "control";
         } else {
            var36 = "controls";
         }

         var35 = var12 + " " + var36;
      }

      var3.setText(var35);
      chooseControl$item(var4, var0, var7, var8, var9, null, "");
      Iterable var41 = var54;
      var var37 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var41, 10));
      Iterator var42 = var41.iterator();

      while (var42.hasNext()) {
         Object var64 = var2.get((JSONObject)var42.next());
         Intrinsics.checkNotNull(var64);
         var37.add((String)((Pair)var64).getFirst());
      }

      List var38 = var37;
      Set var39 = CollectionsKt.toSet(var38);
      Iterable var65 = var5;
      var var43 = new ArrayList();

      for (Object var66 : var65) {
         if (var39.contains((String)var66)) {
            var43.add(var66);
         }
      }

      List var44 = var43;

      for (String var74 : CollectionsKt.distinct(CollectionsKt.plus(var44, var39))) {
         Iterable var77 = var54;
         var var45 = new ArrayList();

         for (Object var81 : var77) {
            Object var84 = var2.get((JSONObject)var81);
            Intrinsics.checkNotNull(var84);
            if (Intrinsics.areEqual(((Pair)var84).getFirst(), var74)) {
               var45.add(var81);
            }
         }

         List var82 = var45;
         if (!var82.isEmpty()) {
            boolean var31;
            if (var21.isEmpty() && !var6.contains(var74)) {
               var31 = false;
            } else {
               var31 = true;
            }

            TextView var79 = new TextView((Context)var4.getAct());
            if (var31) {
               var16 = "▾";
            } else {
               var16 = "▸";
            }

            int var13 = var82.size();
            var79.setText(var16 + "  " + var74 + " (" + var13 + ")");
            var79.setTextSize(18.0F);
            var79.setTextColor(CarUi.Companion.getACCENT());
            var79.setTypeface(Typeface.DEFAULT_BOLD);
            var79.setMinHeight(var4.getUi().dp(56));
            var79.setGravity(16);
            var79.setPadding(var4.getUi().dp(var30), var4.getUi().dp(var30), var4.getUi().dp(var30), var4.getUi().dp(var30));
            var79.setOnClickListener(
               new GxFavoritesPage$$ExternalSyntheticLambda13(var21, var6, var74, var0, var1, var2, var3, var4, var5, var7, var8, var9, var10)
            );
            var0.addView((View)var79);
            if (var31) {
               for (JSONObject var85 : CollectionsKt.sortedWith(
                  var82,
                  new Comparator(new Comparator(var2, var10) {
                     final Map $cats$inlined;
                     final HashMap $paramOrder$inlined;

                     {
                        this.$cats$inlined = var1;
                        this.$paramOrder$inlined = var2x;
                     }

                     @Override
                     public final int compare(T var1, T var2) {
                        var1 = (JSONObject)var1;
                        var1 = this.$cats$inlined.get(var1);
                        Intrinsics.checkNotNull(var1);
                        var1 = (List)((Pair)var1).getSecond();
                        boolean var5 = var1.isEmpty();
                        int var4 = Integer.MAX_VALUE;
                        Integer var6 = -1;
                        Comparable var10;
                        if (var5) {
                           var10 = var6;
                        } else {
                           Integer var11 = (Integer)this.$paramOrder$inlined.get(var1.get(0));
                           int var3;
                           if (var11 != null) {
                              var3 = var11;
                           } else {
                              var3 = Integer.MAX_VALUE;
                           }

                           var10 = var3;
                        }

                        var2 = (JSONObject)var2;
                        var2 = this.$cats$inlined.get(var2);
                        Intrinsics.checkNotNull(var2);
                        var2 = (List)((Pair)var2).getSecond();
                        Comparable var15;
                        if (var2.isEmpty()) {
                           var15 = var6;
                        } else {
                           Integer var16 = (Integer)this.$paramOrder$inlined.get(var2.get(0));
                           int var17 = var4;
                           if (var16 != null) {
                              var17 = var16;
                           }

                           var15 = var17;
                        }

                        return ComparisonsKt.compareValues((T)var10, (T)var15);
                     }
                  }, var2) {
                     final Map $cats$inlined;
                     final Comparator $this_thenBy;

                     {
                        this.$this_thenBy = var1;
                        this.$cats$inlined = var2x;
                     }

                     @Override
                     public final int compare(T var1, T var2x) {
                        int var3x = this.$this_thenBy.compare(var1, (Comparable)var2x);
                        if (var3x == 0) {
                           var1 = (JSONObject)var1;
                           var1 = this.$cats$inlined.get(var1);
                           Intrinsics.checkNotNull(var1);
                           var1 = CollectionsKt.joinToString$default((Iterable)((Pair)var1).getSecond(), "/", null, null, 0, null, null, 62, null);
                           var2x = (JSONObject)var2x;
                           var2x = this.$cats$inlined.get(var2x);
                           Intrinsics.checkNotNull(var2x);
                           var3x = ComparisonsKt.compareValues(
                              var1, CollectionsKt.joinToString$default((Iterable)((Pair)var2x).getSecond(), "/", null, null, 0, null, null, 62, null)
                           );
                        }

                        return var3x;
                     }
                  }
               )) {
                  Collection var47 = CollectionsKt.listOf(var74);
                  Object var83 = var2.get(var85);
                  Intrinsics.checkNotNull(var83);
                  chooseControl$item(
                     var4,
                     var0,
                     var7,
                     var8,
                     var9,
                     var85,
                     CollectionsKt.joinToString$default(
                        CollectionsKt.plus(var47, (Iterable)((Pair)var83).getSecond()), " · ", null, null, 0, null, null, 62, null
                     )
                  );
               }
            }
         }
      }

      if (var54.isEmpty()) {
         TextView var28 = new TextView((Context)var4.getAct());
         var28.setText("No matching controls. Try another search.");
         var28.setTextSize(16.0F);
         var28.setTextColor(CarUi.Companion.getDIM());
         var28.setPadding(0, var4.getUi().dp(10), 0, var4.getUi().dp(10));
         var0.addView((View)var28);
      }
   }

   private static final void chooseControl$renderList$lambda$86(
      List var0,
      HashSet var1,
      String var2,
      LinearLayout var3,
      EditText var4,
      Map var5,
      TextView var6,
      GxFavoritesPage var7,
      List var8,
      String var9,
      AlertDialog var10,
      int var11,
      HashMap var12,
      View var13
   ) {
      if (var0.isEmpty()) {
         if (var1.contains(var2)) {
            var1.remove(var2);
         } else {
            var1.add(var2);
         }

         chooseControl$renderList(var3, var4, var5, var6, var7, var8, var1, var9, var10, var11, var12);
      }
   }

   private final String controlLabel(GxFavoritesPage.Slot var1) {
      String var12;
      label43: {
         JSONObject var4 = this.optionByKey(var1.getKey());
         Object var5 = null;
         boolean var3 = true;
         if (var4 != null) {
            var12 = var4.optString("label");
            if (var12 != null) {
               var12 = var12;
               boolean var2;
               if (var12.length() == 0) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               if (var2) {
                  var12 = null;
               }

               String var6 = var12;
               var12 = var6;
               if (var6 != null) {
                  break label43;
               }
            }
         }

         var12 = var1.getLabel();
         boolean var9;
         if (var12.length() == 0) {
            var9 = var3;
         } else {
            var9 = false;
         }

         if (var9) {
            var12 = (String)var5;
         }

         var5 = var12;
         var12 = (String)var5;
         if (var5 == null) {
            var5 = var1.getKey();
            var12 = (String)var5;
            if (var5 == null) {
               var12 = "Not configured";
            }
         }
      }

      String var8;
      if (this.isSpeedSlot(var1)) {
         String var16 = GxHomeKt.access$gxHomeFmt(this.speedValue(var1));
         String var7 = this.unitLabel();
         var8 = var12 + " " + var16 + " " + var7;
      } else {
         var8 = var12;
      }

      return var8;
   }

   private final boolean isActionSlot(GxFavoritesPage.Slot var1) {
      String var4 = var1.getKey();
      String var3 = var4;
      if (var4 == null) {
         var3 = "";
      }

      String var5 = this.actionPrefix;
      boolean var2 = false;
      var4 = null;
      if (!StringsKt.startsWith$default(var3, var5, false, 2, null)) {
         JSONObject var7 = this.optionByKey(var1.getKey());
         Object var6 = var4;
         if (var7 != null) {
            var6 = var7.opt("action");
         }

         if (!GxHomeKt.access$gxHomeTruthy(var6)) {
            return var2;
         }
      }

      return true;
   }

   private final boolean isSpeedSlot(GxFavoritesPage.Slot var1) {
      return Intrinsics.areEqual(var1.getKey(), this.setSpeed);
   }

   private final void load() {
      this.getJson("/api/favorites/slots", new GxFavoritesPage$$ExternalSyntheticLambda18(this));
   }

   private static final Unit load$lambda$17(GxFavoritesPage var0, String var1, JSONObject var2) {
      if (var1 == null && var2 != null) {
         var0.options = var0.sortOptions(var2.optJSONArray("options"));
         var0.slots = var0.normalizeSlots(var2.optJSONArray("slots"));
         var0.savedSlots = var0.slots;
         var0.mergeValues(var2.optJSONObject("values"));
         var0.isMetric = GxHomeKt.access$gxHomeTruthy(var2.opt("is_metric"));
      } else {
         var0.toast("Failed to load favorite slots.");
      }

      var0.loading = false;
      var0.render();
      return Unit.INSTANCE;
   }

   private final void mergeValues(JSONObject var1) {
      if (var1 != null) {
         Iterator var4 = var1.keys();
         Intrinsics.checkNotNullExpressionValue(var4, "keys(...)");

         while (var4.hasNext()) {
            String var5 = (String)var4.next();
            Map var3 = this.values;
            Object var2;
            if (var1.isNull(var5)) {
               var2 = null;
            } else {
               var2 = var1.opt(var5);
            }

            var3.put(var5, var2);
         }
      }
   }

   private final List<GxFavoritesPage.Slot> normalizeSlots(JSONArray var1) {
      ArrayList var6 = new ArrayList(3);

      for (int var2 = 0; var2 < 3; var2++) {
         var6.add(new GxFavoritesPage.Slot(false, false, null, null, null, 31, null));
      }

      List var9 = var6;
      if (var1 == null) {
         return var9;
      } else {
         int var11 = 0;

         for (int var3 = Math.min(3, var1.length()); var11 < var3; var11++) {
            JSONObject var10 = var1.optJSONObject(var11);
            if (var10 != null) {
               Object var13 = var10.opt("key");
               boolean var4 = GxHomeKt.access$gxHomeTruthy(var13);
               Object var7 = null;
               if (var4) {
                  var13 = var13.toString();
               } else {
                  var13 = null;
               }

               var4 = GxHomeKt.access$gxHomeTruthy(var10.opt("enabled"));
               boolean var5 = GxHomeKt.access$gxHomeTruthy(var10.opt("show_onroad"));
               Object var8 = var7;
               if (Intrinsics.areEqual(var13, this.setSpeed)) {
                  var8 = var7;
                  if (var10.has("value")) {
                     var8 = var7;
                     if (!var10.isNull("value")) {
                        var8 = var10.opt("value");
                     }
                  }
               }

               if (var13 != null) {
                  var7 = var10.opt("label");
                  if (GxHomeKt.access$gxHomeTruthy(var7)) {
                     var7 = var7.toString();
                  } else {
                     var7 = var13;
                  }
               } else {
                  var7 = "";
               }

               var9.set(var11, new GxFavoritesPage.Slot(var4, var5, (String)var13, var8, (String)var7));
            }
         }

         return var9;
      }
   }

   private final JSONObject optionByKey(String var1) {
      CharSequence var3 = var1;
      boolean var2;
      if (var3 != null && var3.length() != 0) {
         var2 = false;
      } else {
         var2 = true;
      }

      Object var4 = null;
      var3 = null;
      JSONObject var6;
      if (var2) {
         var6 = (JSONObject)var4;
      } else {
         Iterator var5 = this.options.iterator();

         while (true) {
            if (!var5.hasNext()) {
               var7 = var3;
               break;
            }

            var4 = var5.next();
            if (Intrinsics.areEqual(((JSONObject)var4).optString("key"), var1)) {
               var7 = var4;
               break;
            }
         }

         var6 = (JSONObject)var7;
      }

      return var6;
   }

   private final void refreshValues() {
      if (!this.loading && !this.saving) {
         this.getJson("/api/favorites/values", new GxFavoritesPage$$ExternalSyntheticLambda11(this));
      }
   }

   private static final Unit refreshValues$lambda$18(GxFavoritesPage var0, String var1, JSONObject var2) {
      if (var1 == null && var2 != null && !var0.saving) {
         var0.mergeValues(var2.optJSONObject("values"));
         var0.render();
      }

      return Unit.INSTANCE;
   }

   private final void render() {
      if (this.speedTimer == null) {
         LinearLayout var13 = this.content;
         Iterable var12 = null;
         LinearLayout var11 = var13;
         if (var13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("content");
            var11 = null;
         }

         var11.removeAllViews();
         if (this.loading) {
            var11 = this.content;
            if (var11 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("content");
               var11 = var12;
            }

            var11.addView((View)this.empty("Loading favorite slots..."));
         } else {
            var12 = CollectionsKt.withIndex(this.slots);
            var var27 = new ArrayList();
            Iterator var51 = var12.iterator();

            while (true) {
               boolean var10 = var51.hasNext();
               boolean var9 = false;
               int var8 = 0;
               if (!var10) {
                  List var63 = var27;
                  var10 = var63.isEmpty();
                  String var41 = "Favorite #";
                  if (!var10) {
                     LinearLayout var15 = new LinearLayout((Context)this.getAct());
                     var15.setOrientation(0);
                     var13 = this.content;
                     var11 = var13;
                     if (var13 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("content");
                        var11 = null;
                     }

                     var11.addView((View)var15, (LayoutParams)CarUi.lp$default(this.getUi(), 0, 0, 3, null));
                     Iterator var16 = var63.iterator();

                     for (int var20 = 0; var16.hasNext(); var20++) {
                        IndexedValue var29 = (IndexedValue)var16.next();
                        var8 = var29.component1();
                        GxFavoritesPage.Slot var18 = (GxFavoritesPage.Slot)var29.component2();
                        LinearLayout var17 = new LinearLayout((Context)this.getAct());
                        var17.setOrientation(1);
                        var17.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD(), 14, Color.rgb(50, 55, 66)));
                        var17.setPadding(this.getUi().dp(18), this.getUi().dp(14), this.getUi().dp(18), this.getUi().dp(14));
                        View var53 = (View)var17;
                        android.widget.LinearLayout.LayoutParams var30 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
                        if (var20 < var63.size() - 1) {
                           var30.rightMargin = this.getUi().dp(8);
                        }

                        GxPage var76;
                        label126: {
                           Unit var19 = Unit.INSTANCE;
                           var15.addView(var53, (LayoutParams)var30);
                           GxPage.text$default(this, "Favorite #" + (var8 + 1), (ViewGroup)var17, 14.0F, 0, false, 24, null);
                           this.text(this.controlLabel(var18), (ViewGroup)var17, 19.0F, CarUi.Companion.getTEXT(), true);
                           var76 = this;
                           JSONObject var31 = this.optionByKey(var18.getKey());
                           if (var31 != null) {
                              String var54 = var31.optString("section");
                              var32 = var54;
                              if (var54 != null) {
                                 break label126;
                              }
                           }

                           var32 = "";
                        }

                        GxPage.text$default(var76, var32, (ViewGroup)var17, 14.0F, 0, false, 24, null);
                        if (this.isActionSlot(var18)) {
                           TextView var33 = CarUi.button$default(
                              this.getUi(), "Press", false, new GxFavoritesPage$$ExternalSyntheticLambda20(this, var18), 2, null
                           );
                           if (this.saving) {
                              var33.setAlpha(0.5F);
                           }

                           View var34 = (View)var33;
                           android.widget.LinearLayout.LayoutParams var55 = new android.widget.LinearLayout.LayoutParams(-2, -2);
                           var55.topMargin = this.getUi().dp(6);
                           Unit var73 = Unit.INSTANCE;
                           var17.addView(var34, (LayoutParams)var55);
                        } else {
                           Switch var35 = this.getUi().bigSwitch();
                           var35.setChecked(GxHomeKt.access$gxHomeTruthy(this.values.get(var18.getKey())));
                           var35.setEnabled(this.saving ^ true);
                           var35.setOnCheckedChangeListener(new GxFavoritesPage$$ExternalSyntheticLambda21(this, var18));
                           View var74 = (View)var35;
                           android.widget.LinearLayout.LayoutParams var56 = new android.widget.LinearLayout.LayoutParams(-2, -2);
                           var56.topMargin = this.getUi().dp(8);
                           var56.leftMargin = this.getUi().dp(10);
                           Unit var36 = Unit.INSTANCE;
                           var17.addView(var74, (LayoutParams)var56);
                        }
                     }
                  }

                  Iterator var68 = this.slots.iterator();
                  int var21 = 0;

                  for (String var37 = var41; var68.hasNext(); var21++) {
                     GxFavoritesPage.Slot var69 = (GxFavoritesPage.Slot)var68.next();
                     String var64 = var37 + (var21 + 1);
                     var13 = this.content;
                     LinearLayout var42 = var13;
                     if (var13 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("content");
                        var42 = null;
                     }

                     this.header(var64, (ViewGroup)var42);
                     String var70 = var37 + (var21 + 1);
                     String var43;
                     if (var69.getEnabled()) {
                        var43 = "Enabled";
                     } else {
                        var43 = "Off";
                     }

                     var10 = var69.getEnabled();
                     LinearLayout var65 = this.content;
                     var13 = var65;
                     if (var65 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("content");
                        var13 = null;
                     }

                     this.toggle(var70, var43, var10, (ViewGroup)var13, new GxFavoritesPage$$ExternalSyntheticLambda1(this, var21))
                        .component2()
                        .setEnabled(this.saving ^ true);
                     GxPage var71 = this;
                     String var66 = this.controlLabel(var69);
                     var13 = this.content;
                     LinearLayout var44 = var13;
                     if (var13 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("content");
                        var44 = null;
                     }

                     TextView var45 = GxPage.Row.button$default(
                        GxPage.row$default(var71, var66, "Control for this favorite", (ViewGroup)var44, 0, 8, null),
                        "Choose control",
                        false,
                        new GxFavoritesPage$$ExternalSyntheticLambda2(this, var21),
                        2,
                        null
                     );
                     if (this.saving) {
                        var45.setAlpha(0.5F);
                     }

                     if (this.isSpeedSlot(var69)) {
                        double var1;
                        if (this.isMetric) {
                           var1 = 8.0;
                        } else {
                           var1 = 5.0;
                        }

                        double var3;
                        if (this.isMetric) {
                           var3 = 145.0;
                        } else {
                           var3 = 90.0;
                        }

                        String var46 = this.unitLabel();
                        String var67 = "Set speed (" + var46 + ")";
                        String var60 = GxHomeKt.access$gxHomeFmt(var1);
                        String var47 = GxHomeKt.access$gxHomeFmt(var3);
                        String var75 = var60 + "–" + var47;
                        double var5 = this.speedValue(var69);
                        GxFavoritesPage$$ExternalSyntheticLambda3 var72 = new GxFavoritesPage$$ExternalSyntheticLambda3();
                        var13 = this.content;
                        LinearLayout var48 = var13;
                        if (var13 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("content");
                           var48 = null;
                        }

                        this.stepper(var67, var75, var5, var1, var3, 1.0, var72, (ViewGroup)var48, new GxFavoritesPage$$ExternalSyntheticLambda4(this, var21));
                     }

                     var10 = var69.getShowOnroad();
                     var13 = this.content;
                     LinearLayout var49 = var13;
                     if (var13 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("content");
                        var49 = null;
                     }

                     Switch var50 = this.toggle(
                           "On-Road Button (C4: tap invisible third)",
                           null,
                           var10,
                           (ViewGroup)var49,
                           new GxFavoritesPage$$ExternalSyntheticLambda5(this, var21)
                        )
                        .component2();
                     if (!this.saving && var69.getEnabled() && var69.getKey() != null) {
                        var10 = true;
                     } else {
                        var10 = var9;
                     }

                     var50.setEnabled(var10);
                  }

                  return;
               }

               Object var14 = var51.next();
               GxFavoritesPage.Slot var40 = (GxFavoritesPage.Slot)((IndexedValue)var14).component2();
               boolean var7 = (boolean)var8;
               if (var40.getEnabled()) {
                  var7 = (boolean)var8;
                  if (var40.getKey() != null) {
                     var7 = (boolean)var8;
                     if (this.optionByKey(var40.getKey()) != null) {
                        var7 = true;
                     }
                  }
               }

               if (var7) {
                  var27.add(var14);
               }
            }
         }
      }
   }

   private static final Unit render$lambda$38(GxFavoritesPage var0, GxFavoritesPage.Slot var1) {
      if (!var0.saving) {
         String var2 = var1.getKey();
         Intrinsics.checkNotNull(var2);
         var0.runAction(var2, var1.getValue());
      }

      return Unit.INSTANCE;
   }

   private static final void render$lambda$40(GxFavoritesPage var0, GxFavoritesPage.Slot var1, CompoundButton var2, boolean var3) {
      if (var2.isPressed()) {
         String var4 = var1.getKey();
         Intrinsics.checkNotNull(var4);
         var0.toggleValue(var4, var3);
      }
   }

   private static final Unit render$lambda$43(GxFavoritesPage var0, int var1, boolean var2) {
      updateSlot$default(var0, var1, false, new GxFavoritesPage$$ExternalSyntheticLambda14(var2), 2, null);
      return Unit.INSTANCE;
   }

   private static final GxFavoritesPage.Slot render$lambda$43$lambda$42(boolean var0, GxFavoritesPage.Slot var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      return GxFavoritesPage.Slot.copy$default(var1, var0, false, null, null, null, 30, null);
   }

   private static final Unit render$lambda$44(GxFavoritesPage var0, int var1) {
      if (!var0.loading && !var0.saving) {
         var0.chooseControl(var1);
      }

      return Unit.INSTANCE;
   }

   private static final String render$lambda$45(double var0) {
      return GxHomeKt.access$gxHomeFmt(var0);
   }

   private static final Unit render$lambda$49(GxFavoritesPage var0, int var1, double var2) {
      if (var0.saving) {
         return Unit.INSTANCE;
      } else {
         Runnable var4 = var0.speedTimer;
         if (var4 != null) {
            var0.getMain().removeCallbacks(var4);
         }

         GxFavoritesPage$$ExternalSyntheticLambda16 var5 = new GxFavoritesPage$$ExternalSyntheticLambda16(var0, var1, var2);
         var0.speedTimer = var5;
         var0.getMain().postDelayed(var5, 900L);
         return Unit.INSTANCE;
      }
   }

   private static final void render$lambda$49$lambda$48(GxFavoritesPage var0, int var1, double var2) {
      var0.speedTimer = null;
      updateSlot$default(var0, var1, false, new GxFavoritesPage$$ExternalSyntheticLambda9(var2), 2, null);
   }

   private static final GxFavoritesPage.Slot render$lambda$49$lambda$48$lambda$47(double var0, GxFavoritesPage.Slot var2) {
      Intrinsics.checkNotNullParameter(var2, "it");
      return GxFavoritesPage.Slot.copy$default(var2, false, false, null, (long)var0, null, 23, null);
   }

   private static final Unit render$lambda$51(GxFavoritesPage var0, int var1, boolean var2) {
      updateSlot$default(var0, var1, false, new GxFavoritesPage$$ExternalSyntheticLambda12(var2), 2, null);
      return Unit.INSTANCE;
   }

   private static final GxFavoritesPage.Slot render$lambda$51$lambda$50(boolean var0, GxFavoritesPage.Slot var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      return GxFavoritesPage.Slot.copy$default(var1, false, var0, null, null, null, 29, null);
   }

   private final void runAction(String var1, Object var2) {
      JSONObject var3 = new JSONObject().put("key", var1);
      if (var2 != null) {
         var3.put("value", var2);
      }

      GxPage.send$default(this, "POST", "/api/favorites/action", var3, 0L, new GxFavoritesPage$$ExternalSyntheticLambda0(this), 8, null);
   }

   private static final Unit runAction$lambda$33(GxFavoritesPage var0, String var1, JSONObject var2) {
      label24:
      if (var1 == null) {
         if (var2 != null) {
            var1 = var2.optString("message");
            if (var1 != null) {
               CharSequence var7 = var1;
               CharSequence var4 = var7;
               if (StringsKt.isBlank(var7)) {
                  var4 = null;
               }

               String var8 = (String)var4;
               var1 = var8;
               if (var8 != null) {
                  break label24;
               }
            }
         }

         var1 = "Favorite action sent.";
      } else {
         CharSequence var9 = var1;
         Object var6 = var9;
         if (StringsKt.isBlank(var9)) {
            var6 = "Failed to send favorite action.";
         }

         var1 = (String)var6;
      }

      var0.toast(var1);
      return Unit.INSTANCE;
   }

   private final void saveSlots() {
      if (!this.saving) {
         this.saving = true;
         this.render();
         JSONObject var1 = new JSONObject();
         JSONArray var2 = new JSONArray();
         Iterator var3 = this.slots.iterator();

         while (var3.hasNext()) {
            var2.put(this.slotJson((GxFavoritesPage.Slot)var3.next()));
         }

         Unit var5 = Unit.INSTANCE;
         var1 = var1.put("slots", var2);
         GxPage.send$default(this, "PUT", "/api/favorites/slots", var1, 0L, new GxFavoritesPage$$ExternalSyntheticLambda17(this), 8, null);
      }
   }

   private static final Unit saveSlots$lambda$23(GxFavoritesPage var0, String var1, JSONObject var2) {
      if (var1 == null) {
         Object var3 = null;
         JSONArray var4;
         if (var2 != null) {
            var4 = var2.optJSONArray("slots");
         } else {
            var4 = null;
         }

         var0.slots = var0.normalizeSlots(var4);
         var0.savedSlots = var0.slots;
         if (var2 != null) {
            JSONArray var5 = var2.optJSONArray("options");
            if (var5 != null) {
               var0.options = var0.sortOptions(var5);
            }
         }

         JSONObject var6;
         if (var2 != null) {
            var6 = var2.optJSONObject("values");
         } else {
            var6 = null;
         }

         label36: {
            var0.mergeValues(var6);
            if (var2 != null) {
               var1 = var2.optString("message");
               if (var1 != null) {
                  var1 = var1;
                  if (StringsKt.isBlank(var1)) {
                     var1 = (String)var3;
                  }

                  String var11 = var1;
                  var1 = var11;
                  if (var11 != null) {
                     break label36;
                  }
               }
            }

            var1 = "Favorite slots saved.";
         }

         var0.toast(var1);
      } else {
         var0.slots = var0.savedSlots;
         CharSequence var12 = var1;
         Object var10 = var12;
         if (StringsKt.isBlank(var12)) {
            var10 = "Failed to save favorite slots.";
         }

         var0.toast((String)var10);
      }

      var0.saving = false;
      var0.render();
      return Unit.INSTANCE;
   }

   private final JSONObject slotJson(GxFavoritesPage.Slot var1) {
      JSONObject var4 = new JSONObject().put("enabled", var1.getEnabled()).put("show_onroad", var1.getShowOnroad());
      String var3 = var1.getKey();
      JSONObject var2 = var3;
      if (var3 == null) {
         var2 = (JSONObject)JSONObject.NULL;
      }

      var2 = var4.put("key", var2).put("label", var1.getLabel());
      if (Intrinsics.areEqual(var1.getKey(), this.setSpeed) && var1.getValue() != null) {
         var2.put("value", var1.getValue());
      }

      Intrinsics.checkNotNullExpressionValue(var2, "apply(...)");
      return var2;
   }

   private final List<JSONObject> sortOptions(JSONArray var1) {
      if (var1 != null) {
         List var2 = this.objects(var1);
         if (var2 != null) {
            return CollectionsKt.sortedWith(var2, new GxFavoritesPage$$ExternalSyntheticLambda8(new GxFavoritesPage$$ExternalSyntheticLambda7()));
         }
      }

      List var3 = CollectionsKt.emptyList();
      return CollectionsKt.sortedWith(var3, new GxFavoritesPage$$ExternalSyntheticLambda8(new GxFavoritesPage$$ExternalSyntheticLambda7()));
   }

   private static final int sortOptions$lambda$10(JSONObject var0, JSONObject var1) {
      var var4 = var0.optString("label");
      int var2 = var4.length();
      boolean var3 = true;
      boolean var6;
      if (var2 == 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var6) {
         var4 = var0.optString("key");
      }

      Intrinsics.checkNotNullExpressionValue(var4, "ifEmpty(...)");
      var4 = var4;
      var var5 = var1.optString("label");
      if (var5.length() == 0) {
         var6 = var3;
      } else {
         var6 = false;
      }

      if (var6) {
         var5 = var1.optString("key");
      }

      Intrinsics.checkNotNullExpressionValue(var5, "ifEmpty(...)");
      return GxHomeKt.access$gxHomeNatural(var4, var5);
   }

   private static final int sortOptions$lambda$11(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   private final double speedValue(GxFavoritesPage.Slot var1) {
      double var2;
      if (var1.getValue() == null) {
         var2 = 30.0;
      } else {
         var2 = GxHomeKt.access$gxHomeNum(var1.getValue());
      }

      return var2;
   }

   private final void toggleValue(String var1, boolean var2) {
      Object var3 = this.values.get(var1);
      this.values.put(var1, var2);
      GxPage.send$default(
         this,
         "PUT",
         "/api/params",
         new JSONObject().put("key", var1).put("value", var2),
         0L,
         new GxFavoritesPage$$ExternalSyntheticLambda15(this, var1, var3),
         8,
         null
      );
   }

   private static final Unit toggleValue$lambda$29(GxFavoritesPage var0, String var1, Object var2, String var3, JSONObject var4) {
      if (var3 == null) {
         if (var4 != null) {
            var2 = var4.optJSONObject("updated");
            if (var2 != null) {
               var0.mergeValues(var2);
            }
         }

         label31: {
            if (var4 != null) {
               var2 = var4.optString("message");
               if (var2 != null) {
                  CharSequence var12 = var2;
                  CharSequence var9 = var12;
                  if (StringsKt.isBlank(var12)) {
                     var9 = null;
                  }

                  var3 = (String)var9;
                  var2 = var3;
                  if (var3 != null) {
                     break label31;
                  }
               }
            }

            var2 = "Parameter '" + var1 + "' updated.";
         }

         var0.toast(var2);
         GalaxyApi var5 = var0.getApi();
         if (var5 != null) {
            GalaxyApi.refreshValues$default(var5, null, 1, null);
         }
      } else {
         var0.values.put(var1, var2);
         CharSequence var11 = var3;
         Object var6 = var11;
         if (StringsKt.isBlank(var11)) {
            var6 = "Network error — is the device reachable?";
         }

         var0.toast((String)var6);
      }

      var0.render();
      return Unit.INSTANCE;
   }

   private final String unitLabel() {
      String var1;
      if (this.isMetric) {
         var1 = "km/h";
      } else {
         var1 = "mph";
      }

      return var1;
   }

   private final void updateSlot(int var1, boolean var2, Function1<? super GxFavoritesPage.Slot, GxFavoritesPage.Slot> var3) {
      if (!this.saving) {
         GxFavoritesPage.Slot var6 = this.slots.get(var1);
         JSONObject var9 = (JSONObject)var3.invoke(var6);
         List var5 = var9;
         if (var2) {
            var2 = Intrinsics.areEqual(((GxFavoritesPage.Slot)var9).getKey(), this.setSpeed);
            var5 = (GxFavoritesPage.Slot)var9;
            if (var2) {
               if (Intrinsics.areEqual(var6.getKey(), this.setSpeed)) {
                  var9 = var6.getValue();
               } else {
                  var9 = 30;
               }
            } else {
               var9 = null;
            }

            var5 = GxFavoritesPage.Slot.copy$default((GxFavoritesPage.Slot)var5, false, false, null, var9, null, 23, null);
         }

         GxFavoritesPage.Slot var7 = (GxFavoritesPage.Slot)var5;
         if (((GxFavoritesPage.Slot)var5).getKey() == null) {
            var9 = "";
         } else {
            label39: {
               var9 = this.optionByKey(((GxFavoritesPage.Slot)var5).getKey());
               if (var9 != null) {
                  String var13 = var9.optString("label");
                  if (var13 != null) {
                     var var14 = var13;
                     boolean var4;
                     if (var14.length() == 0) {
                        var4 = true;
                     } else {
                        var4 = false;
                     }

                     if (var4) {
                        var14 = null;
                     }

                     String var18 = var14;
                     var9 = var18;
                     if (var18 != null) {
                        break label39;
                     }
                  }
               }

               var9 = ((GxFavoritesPage.Slot)var5).getKey();
               Intrinsics.checkNotNull(var9);
            }
         }

         GxFavoritesPage.Slot var15 = GxFavoritesPage.Slot.copy$default(var7, false, false, null, null, (String)var9, 15, null);
         var5 = CollectionsKt.toMutableList(this.slots);
         var5.set(var1, var15);
         this.slots = var5;
         this.saveSlots();
      }
   }

   // $VF: synthetic method
   static void updateSlot$default(GxFavoritesPage var0, int var1, boolean var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      var0.updateSlot(var1, var2, var3);
   }

   @Override
   public void build() {
      GalaxyApi.Param var2;
      label52: {
         GalaxyApi var1 = this.getApi();
         if (var1 != null) {
            List var6 = var1.getCatalog();
            if (var6 != null) {
               Iterable var12 = var6;
               var var7 = new ArrayList();
               Iterator var13 = var12.iterator();

               while (var13.hasNext()) {
                  CollectionsKt.addAll(var7, ((GalaxyApi.Category)var13.next()).getParams());
               }

               List var8 = var7;

               label40: {
                  for (Object var14 : var8) {
                     GalaxyApi.Param var5 = (GalaxyApi.Param)var14;
                     CharSequence var3 = var5.getO().optString("galaxy_ui_type");
                     Object var9 = var3;
                     if (StringsKt.isBlank(var3)) {
                        var9 = var5.getUiType();
                     }

                     if (Intrinsics.areEqual(var9, "favorites")) {
                        var10 = var14;
                        break label40;
                     }
                  }

                  var10 = null;
               }

               var2 = (GalaxyApi.Param)var10;
               break label52;
            }
         }

         var2 = null;
      }

      String var11;
      GxPage var17;
      label31: {
         var17 = this;
         if (var2 != null) {
            String var16 = var2.getLabel();
            var11 = var16;
            if (var16 != null) {
               break label31;
            }
         }

         var11 = "Favorites";
      }

      String var15;
      if (var2 != null) {
         var15 = var2.getDescription();
      } else {
         var15 = null;
      }

      GxPage.row$default(var17, var11, var15, null, 0, 12, null).getTitleView().setTypeface(Typeface.DEFAULT_BOLD);
      this.content = GxPage.box$default(this, null, 1, null);
      this.every(5000L, new GxFavoritesPage$$ExternalSyntheticLambda10(this));
   }

   @Override
   public void onHide() {
      Runnable var1 = this.speedTimer;
      if (var1 != null) {
         this.getMain().removeCallbacks(var1);
      }

      this.speedTimer = null;
   }

   @Override
   public void onShow() {
      this.load();
   }

   private static final class Slot {
      private final boolean enabled;
      private final String key;
      private final String label;
      private final boolean showOnroad;
      private final Object value;

      public Slot() {
         this(false, false, null, null, null, 31, null);
      }

      public Slot(boolean var1, boolean var2, String var3, Object var4, String var5) {
         Intrinsics.checkNotNullParameter(var5, "label");
         super();
         this.enabled = var1;
         this.showOnroad = var2;
         this.key = var3;
         this.value = var4;
         this.label = var5;
      }

      // $VF: synthetic method
      public Slot(boolean var1, boolean var2, String var3, Object var4, String var5, int var6, DefaultConstructorMarker var7) {
         if ((var6 & 1) != 0) {
            var1 = false;
         }

         if ((var6 & 2) != 0) {
            var2 = false;
         }

         if ((var6 & 4) != 0) {
            var3 = null;
         }

         if ((var6 & 8) != 0) {
            var4 = null;
         }

         if ((var6 & 16) != 0) {
            var5 = "";
         }

         this(var1, var2, var3, var4, var5);
      }

      // $VF: synthetic method
      public static GxFavoritesPage.Slot copy$default(
         GxFavoritesPage.Slot var0, boolean var1, boolean var2, String var3, Object var4, String var5, int var6, Object var7
      ) {
         if ((var6 & 1) != 0) {
            var1 = var0.enabled;
         }

         if ((var6 & 2) != 0) {
            var2 = var0.showOnroad;
         }

         if ((var6 & 4) != 0) {
            var3 = var0.key;
         }

         if ((var6 & 8) != 0) {
            var4 = var0.value;
         }

         if ((var6 & 16) != 0) {
            var5 = var0.label;
         }

         return var0.copy(var1, var2, var3, var4, var5);
      }

      public final boolean component1() {
         return this.enabled;
      }

      public final boolean component2() {
         return this.showOnroad;
      }

      public final String component3() {
         return this.key;
      }

      public final Object component4() {
         return this.value;
      }

      public final String component5() {
         return this.label;
      }

      public final GxFavoritesPage.Slot copy(boolean var1, boolean var2, String var3, Object var4, String var5) {
         Intrinsics.checkNotNullParameter(var5, "label");
         return new GxFavoritesPage.Slot(var1, var2, var3, var4, var5);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof GxFavoritesPage.Slot)) {
            return false;
         } else {
            var1 = var1;
            if (this.enabled != var1.enabled) {
               return false;
            } else if (this.showOnroad != var1.showOnroad) {
               return false;
            } else if (!Intrinsics.areEqual(this.key, var1.key)) {
               return false;
            } else {
               return !Intrinsics.areEqual(this.value, var1.value) ? false : Intrinsics.areEqual(this.label, var1.label);
            }
         }
      }

      public final boolean getEnabled() {
         return this.enabled;
      }

      public final String getKey() {
         return this.key;
      }

      public final String getLabel() {
         return this.label;
      }

      public final boolean getShowOnroad() {
         return this.showOnroad;
      }

      public final Object getValue() {
         return this.value;
      }

      @Override
      public int hashCode() {
         int var4 = Boolean.hashCode(this.enabled);
         int var3 = Boolean.hashCode(this.showOnroad);
         String var5 = this.key;
         int var2 = 0;
         int var1;
         if (var5 == null) {
            var1 = 0;
         } else {
            var1 = this.key.hashCode();
         }

         if (this.value != null) {
            var2 = this.value.hashCode();
         }

         return (((var4 * 31 + var3) * 31 + var1) * 31 + var2) * 31 + this.label.hashCode();
      }

      @Override
      public String toString() {
         boolean var1 = this.enabled;
         boolean var2 = this.showOnroad;
         String var5 = this.key;
         Object var4 = this.value;
         String var3 = this.label;
         return "Slot(enabled=" + var1 + ", showOnroad=" + var2 + ", key=" + var5 + ", value=" + var4 + ", label=" + var3 + ")";
      }
   }
}
