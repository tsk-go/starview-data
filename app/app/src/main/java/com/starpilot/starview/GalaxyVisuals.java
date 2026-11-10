package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class GalaxyVisuals {
   public static final GalaxyVisuals INSTANCE = new GalaxyVisuals();
   private static Function1<? super GalaxyRow, Unit> resetFn = new GalaxyVisuals$$ExternalSyntheticLambda4();
   private static final Paint textPaint = new Paint(1);

   // $VF: synthetic method
   public static void $r8$lambda$0SbWEM64_kdqqEPv58duIKGYy3g(GalaxyRow var0, Function2 var1, DialogInterface var2, int var3) {
      wheel$lambda$21$lambda$20$lambda$18(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$1FdM_JSqORl_U4kD_z2SPvuQwMo(GalaxyRow var0, Function2 var1, boolean var2, View var3) {
      mixer$lambda$42$lambda$41$lambda$40(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FFDN6wTTKmQkcGpMpYEgDsqrp3s(TextView var0, boolean var1, GalaxyRow var2, String var3) {
      return mixer$lambda$34(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MU6QSVuvDtH26BUPR67be3eVZVc(HashMap var0, String var1, JSONObject var2) {
      return personalities$lambda$58(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$N3HFEHDOinhK1DmCZFRO_PkmDCM/* $VF was: $r8$lambda$N3HFEHDOinhK1DmCZFRO_PkmDCM*/(GalaxyRow var0) {
      return resetFn$lambda$1(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$PLgGLv1XOO6B_RCH7eSElnPQ7Ag/* $VF was: $r8$lambda$PLgGLv1XOO6B_RCH7eSElnPQ7Ag*/(
      GalaxyRow var0, Function2 var1, boolean var2, View var3
   ) {
      conditions$lambda$68$lambda$66(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$S3Oi0e7HKwvsYq_YuZEALHROxfM(boolean var0, GalaxyRow var1, Function2 var2, String var3) {
      return mixer$lambda$35(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$TiXjI7eklGQBdlQNjWBqcCyl78k(GalaxyRow var0, DialogInterface var1, int var2) {
      about$lambda$3(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$YbHx1QjNmBY6Hc0CJRRwMdkhRuo(Function1 var0, String var1, View var2) {
      personalities$lambda$50$lambda$49(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZxZ5xKgarQpDU_S2Zi5e_jMVeBE/* $VF was: $r8$lambda$ZxZ5xKgarQpDU_S2Zi5e_jMVeBE*/(GalaxyRow var0) {
      return header$lambda$0(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$gsngqW_8OCAOHCZ4b_EVN_5diDM/* $VF was: $r8$lambda$gsngqW_8OCAOHCZ4b_EVN_5diDM*/(
      Context var0, GalaxyRow var1, DialogInterface var2, int var3
   ) {
      wheel$lambda$21$lambda$20$lambda$19(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$lER3K18dS87PFz_bNkLtcjPFYjs(Context var0, GalaxyRow var1, View var2) {
      return conditions$lambda$68$lambda$67(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$m6oYbtg3t5WV_E3px3haQysDfuc/* $VF was: $r8$lambda$m6oYbtg3t5WV_E3px3haQysDfuc*/(Context var0, GalaxyRow var1, View var2) {
      mixer$lambda$42$lambda$39$lambda$38(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$sNdLF7PEiNANQa95D006mNn6yzk(GalaxyRow var0, Context var1, Function2 var2, View var3) {
      wheel$lambda$21$lambda$20(var0, var1, var2, var3);
   }

   private GalaxyVisuals() {
   }

   private static final void about$lambda$3(GalaxyRow var0, DialogInterface var1, int var2) {
      resetFn.invoke(var0);
   }

   private final void bands(Context var1, CarUi var2, List<GalaxyRow> var3, LinearLayout var4) {
      Iterable var12 = var3;
      var var11 = new ArrayList();

      for (Object var14 : var12) {
         GalaxyRow var29 = (GalaxyRow)var14;
         if (new Regex("mph offset$|mph and above$|km/h offset$|km/h and above$", RegexOption.IGNORE_CASE).containsMatchIn(var29.getLabel())) {
            var11.add(var14);
         }
      }

      List var20 = var11;
      Iterable var35 = var20;
      boolean var9 = false;
      var var15 = new ArrayList();
      boolean var8 = false;
      Iterator var16 = var35.iterator();

      while (true) {
         boolean var10 = var16.hasNext();
         String var37 = "";
         if (!var10) {
            List var40 = var15;
            if (var40.size() < 2) {
               return;
            } else {
               LinearLayout var36 = this.card(var1, var2);
               TextView var30 = new TextView(var1);
               var var21 = var40;
               boolean var19;
               if (var21 instanceof Collection && ((Collection)var21).isEmpty()) {
                  var19 = true;
               } else {
                  label93: {
                     Iterator var22 = var21.iterator();

                     while (var22.hasNext()) {
                        if (((Number)((Triple)var22.next()).getSecond()).doubleValue() == 0.0) {
                           var19 = true;
                        } else {
                           var19 = false;
                        }

                        if (!var19) {
                           var19 = false;
                           break label93;
                        }
                     }

                     var19 = true;
                  }
               }

               String var23;
               if (var19) {
                  var23 = "Offset over the limit: none - drives at the limit";
               } else {
                  var23 = "Offset over the limit, by the limit's speed";
               }

               var30.setText(var23);
               var30.setTextColor(CarUi.Companion.getTEXT());
               var30.setTextSize(19.0F);
               var36.addView((View)var30);
               View var31 = new GalaxyVisuals.BandsView(var1, var40, SvIcon.INSTANCE.fg("speed"));
               LayoutParams var41 = new LayoutParams(-1, var2.dp(190));
               var41.topMargin = var2.dp(10);
               Unit var24 = Unit.INSTANCE;
               var36.addView(var31, (android.view.ViewGroup.LayoutParams)var41);
               GalaxyRow var25 = this.find(var3, "^primary source");
               String var26;
               if (var25 != null) {
                  var26 = var25.getValue();
               } else {
                  var26 = null;
               }

               GalaxyRow var32 = this.find(var3, "^secondary source");
               String var33;
               if (var32 != null) {
                  var33 = var32.getValue();
               } else {
                  var33 = null;
               }

               List var27 = CollectionsKt.listOfNotNull(new String[]{var26, var33});
               if (!var27.isEmpty()) {
                  String var28 = CollectionsKt.joinToString$default(var27, ", then ", null, null, 0, null, null, 62, null);
                  GalaxyRow var34 = this.find(var3, "^require confirmation");
                  String var18 = var37;
                  if (var34 != null) {
                     var18 = var37;
                     if (Intrinsics.areEqual(var34.getValue(), "On")) {
                        var18 = " · you confirm each new limit";
                     }
                  }

                  var36.addView((View)this.caption(var1, var2, "Limit from: " + var28 + var18));
               }

               var4.addView((View)var36, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var2, 0, 12, 1, null));
               return;
            }
         }

         GalaxyRow var38 = (GalaxyRow)var16.next();
         Double var17 = INSTANCE.num(var38);
         Triple var39;
         if (var17 != null) {
            double var5 = var17.doubleValue();
            CharSequence var42 = var38.getLabel();
            var39 = new Triple<>(
               StringsKt.replace$default(
                  StringsKt.replace$default(new Regex(" offset$| and above$", RegexOption.IGNORE_CASE).replace(var42, ""), " mph", "", false, 4, null),
                  " km/h",
                  "",
                  false,
                  4,
                  null
               ),
               var5,
               var38.getUnit()
            );
         } else {
            var39 = null;
         }

         if (var39 != null) {
            var15.add(var39);
         }
      }
   }

   private final TextView caption(Context var1, CarUi var2, String var3) {
      TextView var4 = new TextView(var1);
      var4.setText(var3);
      var4.setTextColor(CarUi.Companion.getDIM());
      var4.setTextSize(15.0F);
      var4.setPadding(0, var2.dp(8), 0, 0);
      return var4;
   }

   private final LinearLayout card(Context var1, CarUi var2) {
      LinearLayout var3 = new LinearLayout(var1);
      var3.setOrientation(1);
      var3.setBackground((Drawable)CarUi.rounded$default(var2, CarUi.Companion.getCARD(), 18, null, 4, null));
      var3.setPadding(var2.dp(20), var2.dp(16), var2.dp(20), var2.dp(16));
      return var3;
   }

   private final Set<Integer> conditions(
      Context var1, CarUi var2, String var3, List<GalaxyRow> var4, LinearLayout var5, Function2<? super GalaxyRow, ? super String, Unit> var6
   ) {
      Iterable var13 = var4;
      var var26 = new ArrayList();

      for (Object var14 : var13) {
         boolean var8;
         if (((GalaxyRow)var14).getControl() == GalaxyRow.Control.SWITCH) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            var26.add(var14);
         }
      }

      List var45 = var26;
      if (var45.isEmpty()) {
         return SetsKt.emptySet();
      } else {
         LinearLayout var15 = this.card(var1, var2);
         var13 = var45;
         var var27 = new ArrayList();

         for (var var13_it : var13) {

            var13 = var13_it;
            if (Intrinsics.areEqual(((GalaxyRow)var13).getValue(), "On")) {
               var27.add(var13);
            }
         }

         var4 = var27;
         var13 = var4;
         var var29 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var13, 10));
         Iterator var46 = var13.iterator();

         while (var46.hasNext()) {
            String var41 = ((GalaxyRow)var46.next()).getLabel().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var41, "toLowerCase(...)");
            var29.add(var41);
         }

         List var42 = var29;
         TextView var47 = new TextView(var1);
         String var30;
         if (StringsKt.endsWith$default(var3, "cem", false, 2, null)) {
            var30 = "Switches to Experimental for: ";
         } else {
            var30 = "Switches to Chill for: ";
         }

         String var43;
         if (var42.isEmpty()) {
            var43 = "nothing (all off)";
         } else {
            var43 = CollectionsKt.joinToString$default(var42, ", ", null, null, 0, null, null, 62, null);
         }

         var47.setText(var30 + var43);
         var47.setTextColor(CarUi.Companion.getTEXT());
         var47.setTextSize(19.0F);
         var15.addView((View)var47);
         FlowRow var44 = new FlowRow(var1, var2.dp(10), var2.dp(10));
         var44.setPadding(0, var2.dp(14), 0, 0);

         for (GalaxyRow var17 : var45) {
            SettingIcons.Icon var31 = SettingIcons.INSTANCE.forRow(var3, var17.getLabel(), "", var17.getControl());
            boolean var12 = Intrinsics.areEqual(var17.getValue(), "On");
            LinearLayout var48 = new LinearLayout(var1);
            var48.setOrientation(0);
            var48.setGravity(16);
            var48.setPadding(var2.dp(10), var2.dp(8), var2.dp(18), var2.dp(8));
            var48.setMinimumHeight(var2.dp(60));
            int var35;
            if (var12) {
               var35 = Color.rgb(19, 34, 25);
            } else {
               var35 = CarUi.Companion.getCARD_ON();
            }

            byte var9;
            byte var10;
            byte var11;
            if (var12) {
               var9 = 107;
               var10 = 75;
               var11 = 43;
            } else {
               var9 = 51;
               var10 = 66;
               var11 = 44;
            }

            var48.setBackground((Drawable)var2.rounded(var35, 14, Color.rgb(var11, var9, var10)));
            SvIcon var20 = SvIcon.INSTANCE;
            String var19 = var31.getName();
            String var21 = var31.getGroup();
            SvIcon.State var32;
            if (var12) {
               var32 = SvIcon.State.ON;
            } else {
               var32 = SvIcon.State.OFF;
            }

            View var33 = var20.chip(var1, var19, var21, 40, var32);
            LayoutParams var49 = new LayoutParams(var2.dp(40), var2.dp(40));
            var49.rightMargin = var2.dp(10);
            Unit var51 = Unit.INSTANCE;
            var48.addView(var33, (android.view.ViewGroup.LayoutParams)var49);
            TextView var34 = new TextView(var1);
            var34.setText(var17.getLabel());
            CarUi.Companion var50 = CarUi.Companion;
            if (var12) {
               var35 = var50.getTEXT();
            } else {
               var35 = var50.getDIM();
            }

            var34.setTextColor(var35);
            var34.setTextSize(17.0F);
            var48.addView((View)var34);
            float var7;
            if (var17.getEditable()) {
               var7 = 1.0F;
            } else {
               var7 = 0.45F;
            }

            var48.setAlpha(var7);
            var48.setClickable(true);
            var48.setOnClickListener(new GalaxyVisuals$$ExternalSyntheticLambda12(var17, var6, var12));
            var48.setOnLongClickListener(new GalaxyVisuals$$ExternalSyntheticLambda13(var1, var17));
            var44.addView((View)var48, new android.view.ViewGroup.LayoutParams(-2, -2));
         }

         var15.addView((View)var44);
         var15.addView(
            (View)this.caption(
               var1, var2, "Tap a condition to turn it on or off; hold it for what it does and its default. The speeds and times for them are below."
            )
         );
         var5.addView((View)var15, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var2, 0, 12, 1, null));
         Iterable var22 = var45;
         var var24 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var22, 10));
         Iterator var25 = var22.iterator();

         while (var25.hasNext()) {
            var24.add(((GalaxyRow)var25.next()).getIndex());
         }

         List var23 = var24;
         return CollectionsKt.toSet(var23);
      }
   }

   private static final void conditions$lambda$68$lambda$66(GalaxyRow var0, Function2 var1, boolean var2, View var3) {
      if (var0.getEditable()) {
         String var4;
         if (var2) {
            var4 = "Off";
         } else {
            var4 = "On";
         }

         var1.invoke(var0, var4);
      }
   }

   private static final boolean conditions$lambda$68$lambda$67(Context var0, GalaxyRow var1, View var2) {
      INSTANCE.about(var0, var1);
      return true;
   }

   private final void curve(Context var1, CarUi var2, List<GalaxyRow> var3, LinearLayout var4) {
      GalaxyRow var8 = this.find(var3, "^learning progress");
      if (var8 != null) {
         Double var20;
         label51: {
            MatchResult var7 = Regex.find$default(new Regex("(\\d+(?:\\.\\d+)?)\\s*%"), var8.getValue(), 0, 2, null);
            if (var7 != null) {
               List var18 = var7.getGroupValues();
               if (var18 != null) {
                  String var19 = (String)var18.get(1);
                  if (var19 != null) {
                     var20 = StringsKt.toDoubleOrNull(var19);
                     if (var20 != null) {
                        break label51;
                     }
                  }
               }
            }

            var20 = StringsKt.toDoubleOrNull(var8.getValue());
            if (var20 == null) {
               return;
            }
         }

         LinearLayout var9;
         LinearLayout var10;
         GalaxyVisuals var12;
         LinearLayout var31;
         label44: {
            double var5 = var20;
            var9 = this.card(var1, var2);
            var10 = new LinearLayout(var1);
            var10.setOrientation(0);
            var10.setGravity(16);
            View var28 = new GalaxyVisuals.RingView(var1, var5 / 100.0, SvIcon.INSTANCE.fg("speed"));
            LayoutParams var11 = new LayoutParams(var2.dp(120), var2.dp(120));
            var11.rightMargin = var2.dp(22);
            Unit var21 = Unit.INSTANCE;
            var10.addView(var28, (android.view.ViewGroup.LayoutParams)var11);
            var31 = new LinearLayout(var1);
            var31.setOrientation(1);
            TextView var22 = new TextView(var1);
            var22.setText("Curve learning");
            var22.setTextColor(CarUi.Companion.getTEXT());
            var22.setTextSize(20.0F);
            var22.setTypeface(Typeface.DEFAULT_BOLD);
            var31.addView((View)var22);
            var12 = INSTANCE;
            GalaxyRow var23 = INSTANCE.find(var3, "^saved learning");
            if (var23 != null) {
               String var24 = var23.getValue();
               if (var24 != null) {
                  CharSequence var29 = var24;
                  CharSequence var26 = var29;
                  if (StringsKt.isBlank(var29)) {
                     var26 = null;
                  }

                  var25 = (String)var26;
                  break label44;
               }
            }

            var25 = null;
         }

         label39: {
            GalaxyRow var13 = INSTANCE.find(var3, "^saved cornering");
            if (var13 != null) {
               String var14 = var13.getValue();
               if (var14 != null) {
                  CharSequence var30 = var14;
                  CharSequence var16 = var30;
                  if (StringsKt.isBlank(var30)) {
                     var16 = null;
                  }

                  var15 = (String)var16;
                  break label39;
               }
            }

            var15 = null;
         }

         CharSequence var27 = CollectionsKt.joinToString$default(
            CollectionsKt.listOfNotNull(new String[]{var25, var15}), "\n", null, null, 0, null, null, 62, null
         );
         Object var17 = var27;
         if (StringsKt.isBlank(var27)) {
            var17 = "Learns how fast you like to take curves as you drive them.";
         }

         var31.addView((View)var12.caption(var1, var2, (String)var17));
         var10.addView((View)var31, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
         var9.addView((View)var10);
         var4.addView((View)var9, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var2, 0, 12, 1, null));
      }
   }

   private final GalaxyRow find(List<GalaxyRow> var1, String var2) {
      Iterator var4 = var1.iterator();

      GalaxyRow var3;
      do {
         if (!var4.hasNext()) {
            var5 = null;
            break;
         }

         var5 = var4.next();
         var3 = (GalaxyRow)var5;
      } while (!new Regex(var2, RegexOption.IGNORE_CASE).containsMatchIn(var3.getLabel()));

      return (GalaxyRow)var5;
   }

   private final String fmtGap(double var1, double var3) {
      String var8 = GalaxyRow.Companion.fmt(var1);
      long var6 = Math.round(this.gapFeet(var1, var3));
      int var5 = (int)var3;
      return var8 + " s ≈ " + var6 + " ft at " + var5 + " mph";
   }

   private final void gap(Context var1, CarUi var2, String var3, List<GalaxyRow> var4, LinearLayout var5) {
      Double var11 = this.num(this.find(var4, "^low-speed follow"));
      if (var11 != null) {
         double var6 = var11;
         Double var12 = this.num(this.find(var4, "^high-speed follow"));
         LinearLayout var15 = this.card(var1, var2);
         var15.addView(
            new GalaxyVisuals.GapView(var1, var6, 30.0, SvIcon.INSTANCE.fg("person")), (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, var2.dp(84)))
         );
         if (var12 != null) {
            View var8 = new GalaxyVisuals.GapView(var1, var12, 65.0, SvIcon.INSTANCE.fg("person"));
            LayoutParams var9 = new LayoutParams(-1, var2.dp(84));
            var9.topMargin = var2.dp(6);
            Unit var10 = Unit.INSTANCE;
            var15.addView(var8, (android.view.ViewGroup.LayoutParams)var9);
         }

         String var16 = this.fmtGap(var6, 30.0);
         if (var12 != null) {
            var3 = this.fmtGap(var12, 65.0);
            var3 = ", " + var3 + " on the highway";
         } else {
            var3 = "";
         }

         var15.addView((View)this.caption(var1, var2, "The gap this personality keeps to the car ahead, drawn to scale: " + var16 + " in town" + var3 + "."));
         var5.addView((View)var15, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var2, 0, 12, 1, null));
      }
   }

   // $VF: synthetic method
   public static Set header$default(
      GalaxyVisuals var0, Context var1, CarUi var2, String var3, List var4, LinearLayout var5, Function2 var6, Function1 var7, int var8, Object var9
   ) {
      if ((var8 & 64) != 0) {
         var7 = new GalaxyVisuals$$ExternalSyntheticLambda9();
      }

      return var0.header(var1, var2, var3, var4, var5, var6, (Function1<? super GalaxyRow, Unit>)var7);
   }

   private static final Unit header$lambda$0(GalaxyRow var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return Unit.INSTANCE;
   }

   private final void lane(Context var1, CarUi var2, List<GalaxyRow> var3, LinearLayout var4) {
      GalaxyRow var10 = this.find(var3, "^lane offset");
      if (var10 != null) {
         Double var11 = this.num(var10);
         if (var11 != null) {
            double var7 = var11;
            LinearLayout var12 = this.card(var1, var2);
            String var13 = var10.getUnit();
            GalaxyRow.Bounds var20 = var10.bounds();
            double var5;
            if (var20 != null) {
               var5 = Math.max(Math.abs(var20.getMin()), Math.abs(var20.getMax()));
            } else {
               var5 = 12.0;
            }

            var12.addView(
               new GalaxyVisuals.LaneView(var1, var7, var13, var5, SvIcon.INSTANCE.fg("steer")),
               (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, var2.dp(150)))
            );
            GalaxyRow var23 = this.find(var3, "strength");
            boolean var9;
            if (var7 == 0.0) {
               var9 = true;
            } else {
               var9 = false;
            }

            String var15;
            if (var9) {
               var15 = "Centered in the lane";
            } else {
               String var21 = GalaxyRow.Companion.fmt(Math.abs(var7));
               String var17 = var10.getUnit();
               String var16;
               if (var7 > 0.0) {
                  var16 = "right";
               } else {
                  var16 = "left";
               }

               var15 = var21 + " " + var17 + " " + var16 + " of center";
            }

            String var22 = "";
            String var18 = var22;
            if (var23 != null) {
               String var14 = GalaxyRow.shown$default(var23, null, 1, null);
               String var19;
               if (var23.getLaneStrength()) {
                  var19 = " of 10";
               } else {
                  var19 = "";
               }

               var18 = " · centering strength " + var14 + var19;
               if (var18 == null) {
                  var18 = var22;
               }
            }

            var12.addView((View)this.caption(var1, var2, var15 + var18));
            var4.addView((View)var12, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var2, 0, 12, 1, null));
         }
      }
   }

   private final Set<Integer> mixer(Context var1, CarUi var2, List<GalaxyRow> var3, LinearLayout var4, Function2<? super GalaxyRow, ? super String, Unit> var5) {
      Iterable var22 = var3;
      var var9 = new ArrayList();

      for (GalaxyRow var23 : var22) {
         List var11 = INSTANCE.faderPositions(var23);
         Pair var24;
         if (var11 != null) {
            var24 = TuplesKt.to(var23, var11);
         } else {
            var24 = null;
         }

         if (var24 != null) {
            var9.add(var24);
         }
      }

      List var34 = var9;
      if (var34.size() < 2) {
         return SetsKt.emptySet();
      } else {
         LinearLayout var33 = this.card(var1, var2);
         LinearLayout var12 = new LinearLayout(var1);
         var12.setOrientation(0);
         var12.setGravity(80);
         Iterator var35 = var34.iterator();

         while (true) {
            boolean var8 = var35.hasNext();
            String var25 = "";
            if (!var8) {
               var33.addView((View)var12);
               var var30 = var34;
               boolean var7;
               if (var30 instanceof Collection && ((Collection)var30).isEmpty()) {
                  var7 = false;
               } else {
                  Iterator var31 = var30.iterator();

                  while (true) {
                     if (!var31.hasNext()) {
                        var7 = false;
                        break;
                     }

                     if (((List)((Pair)var31.next()).getSecond()).contains("Auto")) {
                        var7 = true;
                        break;
                     }
                  }
               }

               if (var7) {
                  var25 = " The top of a fader is Auto.";
               }

               var33.addView((View)this.caption(var1, var2, "Drag a fader; it saves when you let go. Tap a name for what it does." + var25));
               var4.addView((View)var33, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var2, 0, 12, 1, null));
               Iterable var20 = var34;
               var var18 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var20, 10));
               Iterator var21 = var20.iterator();

               while (var21.hasNext()) {
                  var18.add(((GalaxyRow)((Pair)var21.next()).getFirst()).getIndex());
               }

               List var19 = var18;
               return CollectionsKt.toSet(var19);
            }

            Pair var26 = (Pair)var35.next();
            GalaxyRow var13 = (GalaxyRow)var26.component1();
            var3 = (List)var26.component2();
            SettingIcons.Icon var15 = SettingIcons.INSTANCE.forRow("sounds", var13.getLabel(), "", var13.getControl());
            if (var13.getControl() == GalaxyRow.Control.SLIDER) {
               var8 = true;
            } else {
               var8 = false;
            }

            TextView var14 = new TextView(var1);
            var14.setTextColor(CarUi.Companion.getTEXT());
            var14.setTextSize(17.0F);
            var14.setTypeface(Typeface.DEFAULT_BOLD);
            var14.setGravity(17);
            GalaxyVisuals.FaderView var16 = new GalaxyVisuals.FaderView(var1, SvIcon.INSTANCE.fg("sound"), var3);
            String var28;
            if (var8) {
               var28 = GalaxyRow.shown$default(var13, null, 1, null);
            } else {
               var28 = var13.getValue();
            }

            var16.setValue(var28);
            var14.setText(mixer$label(var8, var13, var16.getValue()));
            var16.setOnMove(new GalaxyVisuals$$ExternalSyntheticLambda0(var14, var8, var13));
            var16.setOnCommit(new GalaxyVisuals$$ExternalSyntheticLambda5(var8, var13, var5));
            var16.setEnabled(var13.getEditable());
            LinearLayout var29 = new LinearLayout(var1);
            var29.setOrientation(1);
            var29.setGravity(1);
            float var6;
            if (var13.getEditable()) {
               var6 = 1.0F;
            } else {
               var6 = 0.45F;
            }

            var29.setAlpha(var6);
            var29.addView(
               SvIcon.chip$default(SvIcon.INSTANCE, var1, var15.getName(), var15.getGroup(), 48, null, 16, null),
               (android.view.ViewGroup.LayoutParams)(new LayoutParams(var2.dp(48), var2.dp(48)))
            );
            View var17 = var16;
            LayoutParams var39 = new LayoutParams(var2.dp(64), var2.dp(300));
            var39.topMargin = var2.dp(10);
            Unit var41 = Unit.INSTANCE;
            var29.addView(var17, (android.view.ViewGroup.LayoutParams)var39);
            View var36 = (View)var14;
            LayoutParams var42 = new LayoutParams(-2, -2);
            var42.topMargin = var2.dp(8);
            Unit var40 = Unit.INSTANCE;
            var29.addView(var36, (android.view.ViewGroup.LayoutParams)var42);
            var14 = new TextView(var1);
            var14.setText(var13.getLabel());
            var14.setTextColor(CarUi.Companion.getDIM());
            var14.setTextSize(14.0F);
            var14.setGravity(17);
            var14.setMaxLines(2);
            var14.setMinLines(2);
            var14.setOnClickListener(new GalaxyVisuals$$ExternalSyntheticLambda6(var1, var13));
            var29.addView((View)var14);
            if (var13.getShowDefault()) {
               var14 = new TextView(var1);
               var14.setText("Default");
               var14.setTextColor(CarUi.Companion.getACCENT());
               var14.setTextSize(14.0F);
               var14.setPadding(var2.dp(8), var2.dp(6), var2.dp(8), var2.dp(6));
               var14.setOnClickListener(new GalaxyVisuals$$ExternalSyntheticLambda7(var13, var5, var8));
               var29.addView((View)var14);
            }

            var12.addView((View)var29, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
         }
      }
   }

   private static final String mixer$label(boolean var0, GalaxyRow var1, String var2) {
      if (var0) {
         var2 = var1.withUnit(var2);
      }

      return var2;
   }

   private static final Unit mixer$lambda$34(TextView var0, boolean var1, GalaxyRow var2, String var3) {
      Intrinsics.checkNotNullParameter(var3, "v");
      var0.setText(mixer$label(var1, var2, var3));
      return Unit.INSTANCE;
   }

   private static final Unit mixer$lambda$35(boolean var0, GalaxyRow var1, Function2 var2, String var3) {
      Intrinsics.checkNotNullParameter(var3, "v");
      String var4;
      if (var0) {
         var4 = GalaxyRow.shown$default(var1, null, 1, null);
      } else {
         var4 = var1.getValue();
      }

      if (var1.getEditable() && !Intrinsics.areEqual(var3, var4)) {
         if (var0 && !Intrinsics.areEqual(var3, "Auto")) {
            var3 = var1.saved(Double.parseDouble(var3));
         }

         if (var3 != null) {
            var2.invoke(var1, var3);
         }
      }

      return Unit.INSTANCE;
   }

   private static final void mixer$lambda$42$lambda$39$lambda$38(Context var0, GalaxyRow var1, View var2) {
      INSTANCE.about(var0, var1);
   }

   private static final void mixer$lambda$42$lambda$41$lambda$40(GalaxyRow var0, Function2 var1, boolean var2, View var3) {
      if (var0.getEditable()) {
         String var4 = var0.getDefaultValue();
         String var5 = var4;
         if (var2) {
            Double var6 = StringsKt.toDoubleOrNull(var0.shown(var4));
            if (var6 == null) {
               return;
            }

            var4 = var0.saved(var6);
            var5 = var4;
            if (var4 == null) {
               return;
            }
         }

         var1.invoke(var0, var5);
      }
   }

   private final Double num(GalaxyRow var1) {
      Object var3 = null;
      Double var2 = (Double)var3;
      if (var1 != null) {
         String var4 = GalaxyRow.shown$default(var1, null, 1, null);
         var2 = (Double)var3;
         if (var4 != null) {
            var2 = StringsKt.toDoubleOrNull(var4);
         }
      }

      return var2;
   }

   private static final void personalities$lambda$50$lambda$49(Function1 var0, String var1, View var2) {
      var0.invoke(var1);
   }

   private static final Unit personalities$lambda$58(HashMap var0, String var1, JSONObject var2) {
      if (var2 != null) {
         List var9 = GalaxyRow.Companion.parse(var2);
         if (var9 != null) {
            Double var8 = INSTANCE.num(INSTANCE.find(var9, "^low-speed follow"));
            Double var17 = INSTANCE.num(INSTANCE.find(var9, "^high-speed follow"));
            Iterable var10 = var9;
            var var22 = new ArrayList();

            for (var var10_it : var10) {

               var10 = var10_it;
               if (Intrinsics.areEqual(((GalaxyRow)var10).getLabel(), "Preset")) {
                  var22.add(var10);
               }
            }

            var9 = var22;
            var10 = var9;
            var var24 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10, 10));
            Iterator var30 = var10.iterator();

            while (var30.hasNext()) {
               var24.add(((GalaxyRow)var30.next()).getValue());
            }

            var9 = var24;
            var10 = var9;
            var var26 = new ArrayList();

            for (Object var12 : var10) {
               String var34 = (String)var12;
               boolean var5;
               if (!StringsKt.isBlank(var34) && !Intrinsics.areEqual(var34, "Selected Profile")) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               if (var5) {
                  var26.add(var12);
               }
            }

            List var33 = var26;
            TextView var27 = (TextView)var0.get(var1);
            if (var27 != null) {
               String var14;
               if (var8 != null) {
                  double var3 = var8.doubleValue();
                  String var13 = GalaxyRow.Companion.fmt(var3);
                  long var6 = Math.round(INSTANCE.gapFeet(var3, 30.0));
                  var14 = "Town " + var13 + " s · " + var6 + " ft";
               } else {
                  var14 = null;
               }

               if (var17 != null) {
                  double var20 = var17.doubleValue();
                  var1 = GalaxyRow.Companion.fmt(var20);
                  long var21 = Math.round(INSTANCE.gapFeet(var20, 65.0));
                  var1 = "Highway " + var1 + " s · " + var21 + " ft";
               } else {
                  var1 = null;
               }

               String var18 = CollectionsKt.firstOrNull(var33);
               String var19;
               if (var18 != null) {
                  var19 = "Follow: " + var18;
               } else {
                  var19 = null;
               }

               var27.setText(
                  CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull(new String[]{var14, var1, var19}), "\n", null, null, 0, null, null, 62, null)
               );
            }

            return Unit.INSTANCE;
         }
      }

      return Unit.INSTANCE;
   }

   private static final Unit resetFn$lambda$1(GalaxyRow var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return Unit.INSTANCE;
   }

   private final Set<Integer> wheel(Context var1, CarUi var2, List<GalaxyRow> var3, LinearLayout var4, Function2<? super GalaxyRow, ? super String, Unit> var5) {
      Iterable var9 = var3;
      var var26 = new ArrayList();

      for (Object var11 : var9) {
         GalaxyRow var10 = (GalaxyRow)var11;
         boolean var7;
         if (var10.getControl() == GalaxyRow.Control.SELECT && INSTANCE.buttonOf(var10.getLabel()) != null) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            var26.add(var11);
         }
      }

      List var46 = var26;
      if (var46.isEmpty()) {
         return SetsKt.emptySet();
      } else {
         LinkedHashMap var33 = new LinkedHashMap();

         for (GalaxyRow var12 : var46) {
            Pair var27 = this.buttonOf(var12.getLabel());
            Intrinsics.checkNotNull(var27);
            String var14 = (String)var27.component1();
            String var13 = (String)var27.component2();
            Map var15 = var33;
            Object var28 = var15.get(var14);
            if (var28 == null) {
               var28 = new ArrayList();
               var15.put(var14, var28);
            }

            ((List)var28).add(TuplesKt.to(var13, var12));
         }

         int var8 = 12;
         FlowRow var29 = new FlowRow(var1, var2.dp(12), var2.dp(12));
         int var30 = var2.dp(330);

         for (Entry var36 : var33.entrySet()) {
            String var34 = (String)var36.getKey();
            List var49 = (List)var36.getValue();
            LinearLayout var48 = this.card(var1, var2);
            LinearLayout var51 = new LinearLayout(var1);
            var51.setOrientation(0);
            var51.setGravity(16);
            View var16 = SvIcon.chip$default(SvIcon.INSTANCE, var1, "buttons", "display", 40, null, 16, null);
            LayoutParams var17 = new LayoutParams(var2.dp(40), var2.dp(40));
            var17.rightMargin = var2.dp(var8);
            Unit var37 = Unit.INSTANCE;
            var51.addView(var16, (android.view.ViewGroup.LayoutParams)var17);
            TextView var54 = new TextView(var1);
            String var38;
            if (Intrinsics.areEqual(var34, "Star")) {
               var38 = "★ button";
            } else {
               var38 = var34 + " button";
            }

            var54.setText(var38);
            var54.setTextColor(CarUi.Companion.getTEXT());
            var54.setTextSize(20.0F);
            var54.setTypeface(Typeface.DEFAULT_BOLD);
            var51.addView((View)var54);
            var48.addView((View)var51);

            for (Pair var39 : var49) {
               String var57 = (String)var39.component1();
               GalaxyRow var55 = (GalaxyRow)var39.component2();
               SettingIcons.Icon var40 = this.actionIcon(var55.getValue());
               var51 = new LinearLayout(var1);
               var51.setOrientation(0);
               var51.setGravity(16);
               var51.setPadding(0, var2.dp(10), 0, 0);
               var51.setMinimumHeight(var2.dp(56));
               TextView var18 = new TextView(var1);
               var18.setText(var57);
               var18.setTextColor(CarUi.Companion.getDIM());
               var18.setTextSize(15.0F);
               var51.addView((View)var18, (android.view.ViewGroup.LayoutParams)(new LayoutParams(var2.dp(84), -2)));
               SvIcon var58 = SvIcon.INSTANCE;
               String var60 = var40.getName();
               String var19 = var40.getGroup();
               SvIcon.State var41;
               if (Intrinsics.areEqual(var55.getValue(), "Off")) {
                  var41 = SvIcon.State.OFF;
               } else {
                  var41 = SvIcon.State.ON;
               }

               View var42 = var58.chip(var1, var60, var19, 36, var41);
               LayoutParams var61 = new LayoutParams(var2.dp(36), var2.dp(36));
               var61.rightMargin = var2.dp(10);
               Unit var59 = Unit.INSTANCE;
               var51.addView(var42, (android.view.ViewGroup.LayoutParams)var61);
               TextView var43 = new TextView(var1);
               var43.setText(var55.getValue());
               if (Intrinsics.areEqual(var55.getValue(), "Off")) {
                  var8 = CarUi.Companion.getDIM();
               } else {
                  var8 = CarUi.Companion.getTEXT();
               }

               var43.setTextColor(var8);
               var43.setTextSize(18.0F);
               var51.addView((View)var43, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
               TextView var44 = new TextView(var1);
               var44.setText("›");
               var44.setTextSize(26.0F);
               var44.setTextColor(CarUi.Companion.getDIM());
               var51.addView((View)var44);
               var51.setBackground((Drawable)CarUi.rounded$default(var2, CarUi.Companion.getCARD_ON(), 12, null, 4, null));
               var51.setPadding(var2.dp(12), var2.dp(4), var2.dp(12), var2.dp(4));
               float var6;
               if (var55.getAvailable()) {
                  var6 = 1.0F;
               } else {
                  var6 = 0.45F;
               }

               var51.setAlpha(var6);
               var51.setClickable(true);
               var51.setOnClickListener(new GalaxyVisuals$$ExternalSyntheticLambda8(var55, var1, var5));
               View var45 = (View)var51;
               LayoutParams var53 = new LayoutParams(-1, -2);
               var53.topMargin = var2.dp(8);
               Unit var56 = Unit.INSTANCE;
               var48.addView(var45, (android.view.ViewGroup.LayoutParams)var53);
               var8 = 12;
            }

            var29.addView((View)var48, new android.view.ViewGroup.LayoutParams(var30, -2));
         }

         var4.addView((View)var29, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var2, 0, 10, 1, null));
         GalaxyRow var20 = CollectionsKt.firstOrNull(var46);
         if (var20 != null) {
            String var21 = var20.getReason();
            if (var21 != null) {
               if (StringsKt.isBlank(var21)) {
                  var21 = null;
               }

               if (var21 != null) {
                  var4.addView((View)var2.note(var21), (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var2, 0, 0, 3, null));
               }
            }
         }

         Iterable var24 = var46;
         var var22 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var24, 10));
         Iterator var25 = var24.iterator();

         while (var25.hasNext()) {
            var22.add(((GalaxyRow)var25.next()).getIndex());
         }

         List var23 = var22;
         return CollectionsKt.toSet(var23);
      }
   }

   private static final void wheel$lambda$21$lambda$20(GalaxyRow var0, Context var1, Function2 var2, View var3) {
      if (var0.getAvailable()) {
         Builder var5 = new Builder(var1).setTitle(var0.getLabel());
         Iterable var8 = var0.getChoices();
         var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10));

         for (String var4 : var8) {
            String var9 = var4;
            if (Intrinsics.areEqual(var4, var0.getDefaultValue())) {
               var9 = var4 + "   (default)";
            }

            var6.add(var9);
         }

         List var10 = var6;
         Collection var11 = var10;
         var5.setSingleChoiceItems(
               var11.toArray(new String[0]), var0.getChoices().indexOf(var0.getValue()), new GalaxyVisuals$$ExternalSyntheticLambda1(var0, var2)
            )
            .setNeutralButton("About", new GalaxyVisuals$$ExternalSyntheticLambda2(var1, var0))
            .setNegativeButton("Cancel", null)
            .show();
      }
   }

   private static final void wheel$lambda$21$lambda$20$lambda$18(GalaxyRow var0, Function2 var1, DialogInterface var2, int var3) {
      var2.dismiss();
      if (!Intrinsics.areEqual(var0.getChoices().get(var3), var0.getValue())) {
         var1.invoke(var0, var0.getChoices().get(var3));
      }
   }

   private static final void wheel$lambda$21$lambda$20$lambda$19(Context var0, GalaxyRow var1, DialogInterface var2, int var3) {
      INSTANCE.about(var0, var1);
   }

   public final void about(Context var1, GalaxyRow var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "r");
      Builder var5 = new Builder(var1).setTitle(var2.getLabel());
      CharSequence var4 = StringsKt.replace$default(var2.getReason(), "https://firestar.link/discord", "the StarPilot Discord", false, 4, null);
      CharSequence var7 = var4;
      if (StringsKt.isBlank(var4)) {
         var7 = null;
      }

      boolean var3 = false;
      String var6 = var2.withUnit(GalaxyRow.shown$default(var2, null, 1, null));
      if (var2.getDefaultValue().length() > 0) {
         var3 = true;
      }

      String var10;
      if (var3) {
         String var9 = var2.withUnit(var2.shown(var2.getDefaultValue()));
         var10 = "   ·   Default: " + var9;
      } else {
         var10 = "";
      }

      Builder var8 = var5.setMessage(
            CollectionsKt.joinToString$default(
               CollectionsKt.listOfNotNull(new String[]{var7, "Now: " + var6 + var10}), "\n\n", null, null, 0, null, null, 62, null
            )
         )
         .setNegativeButton("Close", null);
      if (var2.getShowDefault() && var2.getEditable()) {
         var8.setPositiveButton("Set to default", new GalaxyVisuals$$ExternalSyntheticLambda3(var2));
      }

      var8.show();
   }

   public final SettingIcons.Icon actionIcon(String var1) {
      Intrinsics.checkNotNullParameter(var1, "action");
      switch (var1.hashCode()) {
         case -1925399022:
            if (var1.equals("Adopt speed limit")) {
               SettingIcons.Icon var3 = new SettingIcons.Icon("sign", "speed", false, 4, null);
               return var3;
            }
            break;
         case -1899271032:
            if (var1.equals("Switchback Mode")) {
               return new SettingIcons.Icon("curve", "steer", false, 4, null);
            }
            break;
         case -1711209605:
            if (var1.equals("Pause steering")) {
               return new SettingIcons.Icon("pause", "steer", false, 4, null);
            }
            break;
         case -1166858732:
            if (var1.equals("Cycle personality")) {
               return new SettingIcons.Icon("standard", "person", false, 4, null);
            }
            break;
         case -268029695:
            if (var1.equals("Force coast")) {
               return new SettingIcons.Icon("coast", "speed", false, 4, null);
            }
            break;
         case 79183:
            if (var1.equals("Off")) {
               return new SettingIcons.Icon("power", "device", false, 4, null);
            }
            break;
         case 776643728:
            if (var1.equals("Pause longitudinal")) {
               return new SettingIcons.Icon("pause", "speed", false, 4, null);
            }
            break;
         case 944116230:
            if (var1.equals("Traffic mode")) {
               return new SettingIcons.Icon("traffic", "person", false, 4, null);
            }
            break;
         case 1139431218:
            if (var1.equals("Toggle AOL")) {
               return new SettingIcons.Icon("aol", "steer", false, 4, null);
            }
            break;
         case 1635958837:
            if (var1.equals("Pulse and glide")) {
               return new SettingIcons.Icon("pulse", "speed", false, 4, null);
            }
            break;
         case 1797878756:
            if (var1.equals("Experimental override")) {
               return new SettingIcons.Icon("conditional", "speed", false, 4, null);
            }
            break;
         case 2070022486:
            if (var1.equals("Bookmark")) {
               return new SettingIcons.Icon("bookmark", "display", false, 4, null);
            }
      }

      SettingIcons.Icon var2;
      if (StringsKt.startsWith$default(var1, "Quick Select", false, 2, null)) {
         var2 = new SettingIcons.Icon("star", "display", false, 4, null);
      } else {
         var2 = new SettingIcons.Icon("buttons", "display", false, 4, null);
      }

      return var2;
   }

   public final Pair<String, String> buttonOf(String var1) {
      Intrinsics.checkNotNullParameter(var1, "label");
      MatchResult var3 = Regex.find$default(
         new Regex("^(LKAS|Main cruise|Distance|Cancel|MODE|Star)(?: button)?\\s*(short press|press|long press|very long press)?$", RegexOption.IGNORE_CASE),
         StringsKt.trim(var1).toString(),
         0,
         2,
         null
      );
      if (var3 == null) {
         return null;
      } else {
         var1 = var3.getGroupValues().get(1);
         String var2 = var1;
         if (Intrinsics.areEqual(var1, "MODE")) {
            var2 = "Mode";
         }

         var1 = var3.getGroupValues().get(2).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
         if (Intrinsics.areEqual(var1, "long press")) {
            var1 = "hold";
         } else if (Intrinsics.areEqual(var1, "very long press")) {
            var1 = "long hold";
         } else {
            var1 = "press";
         }

         return TuplesKt.to(var2, var1);
      }
   }

   public final List<String> faderPositions(GalaxyRow var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      GalaxyRow.Control var5 = var1.getControl();
      GalaxyRow.Control var4 = GalaxyRow.Control.SLIDER;
      int var3 = 0;
      if (var5 == var4) {
         GalaxyRow.Bounds var8 = var1.bounds();
         if (var8 == null) {
            return null;
         } else {
            int var11 = (int)Math.round((var8.getMax() - var8.getMin()) / var8.getStep());
            if (var11 > 400) {
               return null;
            } else {
               Iterable var23 = new IntRange(0, var11);
               var var16 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var23, 10));
               Iterator var24 = var23.iterator();

               while (var24.hasNext()) {
                  var3 = ((IntIterator)var24).nextInt();
                  var16.add(GalaxyRow.Companion.fmt(Math.round((var8.getMin() + var3 * var8.getStep()) * 1000000.0) / 1000000.0));
               }

               List var17 = var16;
               Collection var18 = var17;
               List var9;
               if (var8.getAuto()) {
                  var9 = CollectionsKt.listOf("Auto");
               } else {
                  var9 = CollectionsKt.emptyList();
               }

               return CollectionsKt.plus(var18, var9);
            }
         }
      } else if (var1.getControl() == GalaxyRow.Control.SELECT && var1.getChoices().size() >= 3) {
         Iterable var19 = var1.getChoices();
         var var13 = new ArrayList();

         for (Object var6 : var19) {
            if (!Intrinsics.areEqual((String)var6, "Auto")) {
               var13.add(var6);
            }
         }

         List var14 = var13;
         var var21 = var14;
         boolean var10;
         if (var21 instanceof Collection && ((Collection)var21).isEmpty()) {
            var10 = (boolean)var3;
         } else {
            label91: {
               Iterator var22 = var21.iterator();

               while (var22.hasNext()) {
                  if (StringsKt.toDoubleOrNull(StringsKt.trim(StringsKt.removeSuffix((String)var22.next(), "%")).toString()) == null) {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  if (var10) {
                     var10 = true;
                     break label91;
                  }
               }

               var10 = (boolean)var3;
            }
         }

         if (var10) {
            return null;
         } else {
            Collection var15 = CollectionsKt.sortedWith(
               var14,
               new Comparator() {
                  @Override
                  public final int compare(T var1, T var2) {
                     return ComparisonsKt.compareValues(
                        Double.parseDouble(StringsKt.trim(StringsKt.removeSuffix((String)var1, "%")).toString()),
                        Double.parseDouble(StringsKt.trim(StringsKt.removeSuffix((String)var2, "%")).toString())
                     );
                  }
               }
            );
            List var7;
            if (var1.getChoices().contains("Auto")) {
               var7 = CollectionsKt.listOf("Auto");
            } else {
               var7 = CollectionsKt.emptyList();
            }

            return CollectionsKt.plus(var15, var7);
         }
      } else {
         return null;
      }
   }

   public final double gapFeet(double var1, double var3) {
      return var1 * var3 * 5280.0 / 3600.0;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final Set<Integer> header(
      Context var1,
      CarUi var2,
      String var3,
      List<GalaxyRow> var4,
      LinearLayout var5,
      Function2<? super GalaxyRow, ? super String, Unit> var6,
      Function1<? super GalaxyRow, Unit> var7
   ) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "page");
      Intrinsics.checkNotNullParameter(var4, "rows");
      Intrinsics.checkNotNullParameter(var5, "col");
      Intrinsics.checkNotNullParameter(var6, "write");
      Intrinsics.checkNotNullParameter(var7, "reset");

      label214: {
         label215: {
            label216: {
               label217: {
                  label218: {
                     label219: {
                        label220: {
                           label157: {
                              label156: {
                                 label155: {
                                    label154: {
                                       label153: {
                                          label152: {
                                             try {
                                                resetFn = var7;
                                                switch (var3.hashCode()) {
                                                   case -1067310595:
                                                      break label154;
                                                   case -896509628:
                                                      break label157;
                                                   case 113962:
                                                      break label220;
                                                   case 3314156:
                                                      break label219;
                                                   case 95027439:
                                                      break label218;
                                                   case 113097563:
                                                      break label217;
                                                   case 821741828:
                                                      break label156;
                                                   case 821741890:
                                                      break label155;
                                                   case 1090497327:
                                                      break label153;
                                                   case 1147132932:
                                                      break label152;
                                                   case 1312628413:
                                                      break;
                                                   default:
                                                      break label214;
                                                }
                                             } catch (Exception var27) {
                                                Log.w("StarView/Visuals", "page " + var3, var27);
                                                return SetsKt.emptySet();
                                             }

                                             try {
                                                if (!var3.equals("standard")) {
                                                   break label214;
                                                }
                                                break label215;
                                             } catch (Exception var26) {
                                                Log.w("StarView/Visuals", "page " + var3, var26);
                                                return SetsKt.emptySet();
                                             }
                                          }

                                          try {
                                             if (!var3.equals("aggressive")) {
                                                break label214;
                                             }
                                             break label215;
                                          } catch (Exception var25) {
                                             Log.w("StarView/Visuals", "page " + var3, var25);
                                             return SetsKt.emptySet();
                                          }
                                       }

                                       try {
                                          if (!var3.equals("relaxed")) {
                                             break label214;
                                          }
                                          break label215;
                                       } catch (Exception var24) {
                                          Log.w("StarView/Visuals", "page " + var3, var24);
                                          return SetsKt.emptySet();
                                       }
                                    }

                                    try {
                                       if (!var3.equals("traffic")) {
                                          break label214;
                                       }
                                       break label215;
                                    } catch (Exception var23) {
                                       Log.w("StarView/Visuals", "page " + var3, var23);
                                       Set var39 = SetsKt.emptySet();
                                       return var39;
                                    }
                                 }

                                 try {
                                    if (!var3.equals("conditional/cem")) {
                                       break label214;
                                    }
                                    break label216;
                                 } catch (Exception var17) {
                                    Log.w("StarView/Visuals", "page " + var3, var17);
                                    return SetsKt.emptySet();
                                 }
                              }

                              try {
                                 if (!var3.equals("conditional/ccm")) {
                                    break label214;
                                 }
                                 break label216;
                              } catch (Exception var16) {
                                 Log.w("StarView/Visuals", "page " + var3, var16);
                                 return SetsKt.emptySet();
                              }
                           }

                           try {
                              if (!var3.equals("sounds")) {
                                 break label214;
                              }
                           } catch (Exception var22) {
                              Log.w("StarView/Visuals", "page " + var3, var22);
                              Set var37 = SetsKt.emptySet();
                              return var37;
                           }

                           try {
                              var38 = this.mixer(var1, var2, var4, var5, var6);
                           } catch (Exception var10) {
                              Log.w("StarView/Visuals", "page " + var3, var10);
                              var38 = SetsKt.emptySet();
                           }

                           return var38;
                        }

                        try {
                           if (!var3.equals("slc")) {
                              break label214;
                           }
                        } catch (Exception var21) {
                           Log.w("StarView/Visuals", "page " + var3, var21);
                           Set var35 = SetsKt.emptySet();
                           return var35;
                        }

                        try {
                           this.bands(var1, var2, var4, var5);
                           var36 = SetsKt.emptySet();
                        } catch (Exception var11) {
                           Log.w("StarView/Visuals", "page " + var3, var11);
                           var36 = SetsKt.emptySet();
                        }

                        return var36;
                     }

                     try {
                        if (!var3.equals("lane")) {
                           break label214;
                        }
                     } catch (Exception var20) {
                        Log.w("StarView/Visuals", "page " + var3, var20);
                        Set var33 = SetsKt.emptySet();
                        return var33;
                     }

                     try {
                        this.lane(var1, var2, var4, var5);
                        var34 = SetsKt.emptySet();
                     } catch (Exception var12) {
                        Log.w("StarView/Visuals", "page " + var3, var12);
                        var34 = SetsKt.emptySet();
                     }

                     return var34;
                  }

                  try {
                     if (!var3.equals("curve")) {
                        break label214;
                     }
                  } catch (Exception var19) {
                     Log.w("StarView/Visuals", "page " + var3, var19);
                     Set var31 = SetsKt.emptySet();
                     return var31;
                  }

                  try {
                     this.curve(var1, var2, var4, var5);
                     var32 = SetsKt.emptySet();
                  } catch (Exception var13) {
                     Log.w("StarView/Visuals", "page " + var3, var13);
                     var32 = SetsKt.emptySet();
                  }

                  return var32;
               }

               try {
                  if (!var3.equals("wheel")) {
                     break label214;
                  }
               } catch (Exception var18) {
                  Log.w("StarView/Visuals", "page " + var3, var18);
                  Set var29 = SetsKt.emptySet();
                  return var29;
               }

               try {
                  var30 = this.wheel(var1, var2, var4, var5, var6);
               } catch (Exception var14) {
                  Log.w("StarView/Visuals", "page " + var3, var14);
                  var30 = SetsKt.emptySet();
               }

               return var30;
            }

            try {
               var28 = this.conditions(var1, var2, var3, var4, var5, var6);
            } catch (Exception var15) {
               Log.w("StarView/Visuals", "page " + var3, var15);
               var28 = SetsKt.emptySet();
            }

            return var28;
         }

         try {
            this.gap(var1, var2, var3, var4, var5);
            var40 = SetsKt.emptySet();
         } catch (Exception var9) {
            Log.w("StarView/Visuals", "page " + var3, var9);
            var40 = SetsKt.emptySet();
         }

         return var40;
      }

      try {
         var41 = SetsKt.emptySet();
      } catch (Exception var8) {
         Log.w("StarView/Visuals", "page " + var3, var8);
         var41 = SetsKt.emptySet();
      }

      return var41;
   }

   public final void personalities(Context var1, CarUi var2, LinearLayout var3, Function1<? super String, Unit> var4) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "col");
      Intrinsics.checkNotNullParameter(var4, "open");
      LinearLayout var6 = this.card(var1, var2);
      LinearLayout var8 = new LinearLayout(var1);
      var8.setOrientation(0);
      List var7 = CollectionsKt.listOf(
         new Pair[]{
            TuplesKt.to("traffic", "Traffic"), TuplesKt.to("aggressive", "Aggressive"), TuplesKt.to("standard", "Standard"), TuplesKt.to("relaxed", "Relaxed")
         }
      );
      HashMap var5 = new HashMap();

      for (Pair var11 : var7) {
         String var10 = (String)var11.component1();
         String var12 = (String)var11.component2();
         SettingIcons.Icon var14 = SettingIcons.INSTANCE.forPage(var10);
         TextView var13 = new TextView(var1);
         var13.setText("…");
         var13.setTextColor(CarUi.Companion.getDIM());
         var13.setTextSize(15.0F);
         var13.setPadding(0, var2.dp(8), 0, 0);
         var5.put(var10, var13);
         LinearLayout var17 = new LinearLayout(var1);
         var17.setOrientation(1);
         var17.setPadding(var2.dp(6), var2.dp(4), var2.dp(6), var2.dp(4));
         var17.addView(
            SvIcon.chip$default(SvIcon.INSTANCE, var1, var14.getName(), var14.getGroup(), 56, null, 16, null),
            (android.view.ViewGroup.LayoutParams)(new LayoutParams(var2.dp(56), var2.dp(56)))
         );
         TextView var18 = new TextView(var1);
         var18.setText(var12);
         var18.setTextColor(CarUi.Companion.getTEXT());
         var18.setTextSize(19.0F);
         var18.setTypeface(Typeface.DEFAULT_BOLD);
         var18.setPadding(0, var2.dp(8), 0, 0);
         var17.addView((View)var18);
         var17.addView((View)var13);
         var17.setClickable(true);
         var17.setOnClickListener(new GalaxyVisuals$$ExternalSyntheticLambda10(var4, var10));
         var8.addView((View)var17, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      }

      var6.addView((View)var8);
      var6.addView((View)this.caption(var1, var2, "Follow time in town / on the highway, and the feet that makes at 30 and 65 mph. Tap one to tune it."));
      var3.addView((View)var6, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var2, 0, 12, 1, null));
      Iterator var16 = var7.iterator();

      while (var16.hasNext()) {
         String var15 = (String)((Pair)var16.next()).component1();
         GalaxyPages.get$default(GalaxyPages.INSTANCE, var15, 0L, new GalaxyVisuals$$ExternalSyntheticLambda11(var5, var15), 2, null);
      }
   }

   public static final class BandsView extends View {
      private final List<Triple<String, Double, String>> bands;
      private final int color;
      private final Paint p;

      public BandsView(Context var1, List<Triple<String, Double, String>> var2, int var3) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var2, "bands");
         super(var1);
         this.bands = var2;
         this.color = var3;
         this.p = new Paint(1);
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         float var12 = this.getResources().getDisplayMetrics().density;
         float var9 = this.getWidth();
         float var13 = this.getHeight();
         float var18 = 26 * var12;
         float var14 = var13 - 30 * var12;
         Iterator var27 = this.bands.iterator();
         if (!var27.hasNext()) {
            throw new NoSuchElementException();
         } else {
            double var2 = ((Number)((Triple)var27.next()).getSecond()).doubleValue();

            while (var27.hasNext()) {
               var2 = Math.max(var2, ((Number)((Triple)var27.next()).getSecond()).doubleValue());
            }

            double var4 = Math.max(var2, 1.0);
            var27 = this.bands.iterator();
            if (!var27.hasNext()) {
               throw new NoSuchElementException();
            } else {
               var2 = ((Number)((Triple)var27.next()).getSecond()).doubleValue();

               while (var27.hasNext()) {
                  var2 = Math.min(var2, ((Number)((Triple)var27.next()).getSecond()).doubleValue());
               }

               var2 = Math.min(var2, 0.0);
               float var10 = var18 + (float)((var4 - 0) / (var4 - var2) * (var14 - var18));
               float var15 = var9 / this.bands.size();
               float var17 = var15 * 0.56F;
               this.p.setColor(Color.rgb(60, 66, 78));
               float var16 = 2;
               float var19 = var12 / var16;
               float var11 = var12 / var16;
               Paint var41 = this.p;
               float var8 = var10;
               var1.drawRect(0.0F, var10 - var19, var9, var10 + var11, var41);
               Iterator var29 = this.bands.iterator();

               for (int var24 = 0; var29.hasNext(); var24++) {
                  boolean var26 = true;
                  Triple var30 = (Triple)var29.next();
                  float var20 = var24 * var15 + (var15 - var17) / var16;
                  float var21 = var18 + (float)((var4 - ((Number)var30.getSecond()).doubleValue()) / (var4 - var2) * (var14 - var18));
                  Paint var42 = this.p;
                  boolean var25;
                  if (((Number)var30.getSecond()).doubleValue() == 0.0) {
                     var25 = 1;
                  } else {
                     var25 = 0;
                  }

                  if (var25) {
                     var25 = Color.rgb(60, 66, 78);
                  } else {
                     var25 = this.color;
                  }

                  var42.setColor(var25);
                  var19 = Math.min(var21, var8);
                  boolean var38;
                  if (((Number)var30.getSecond()).doubleValue() == 0.0) {
                     var38 = true;
                  } else {
                     var38 = false;
                  }

                  var11 = 0.0F;
                  if (var38) {
                     var10 = var16 * var12;
                  } else {
                     var10 = 0.0F;
                  }

                  float var22 = Math.max(var21, var8);
                  if (((Number)var30.getSecond()).doubleValue() == 0.0) {
                     var38 = var26;
                  } else {
                     var38 = false;
                  }

                  if (var38) {
                     var11 = var16 * var12;
                  }

                  float var23 = 6;
                  var1.drawRoundRect(var20, var19 - var10, var20 + var17, var22 + var11, var23 * var12, var23 * var12, this.p);
                  GalaxyVisuals.textPaint.setColor(-1);
                  GalaxyVisuals.textPaint.setTextSize(15 * var12);
                  GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                  double var6 = ((Number)var30.getSecond()).doubleValue();
                  String var28 = "";
                  String var43;
                  if (var6 > 0.0) {
                     var43 = "+";
                  } else {
                     var43 = "";
                  }

                  String var31 = GalaxyRow.Companion.fmt(((Number)var30.getSecond()).doubleValue());
                  if (Intrinsics.areEqual(var30.getThird(), "%")) {
                     var28 = "%";
                  }

                  String var44 = var43 + var31 + var28;
                  var1.drawText(
                     var44,
                     var20 + var17 / var16 - GalaxyVisuals.textPaint.measureText(var44) / var16,
                     Math.min(var21, var8) - 7 * var12,
                     GalaxyVisuals.textPaint
                  );
                  GalaxyVisuals.textPaint.setColor(Color.rgb(150, 160, 180));
                  GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT);
                  GalaxyVisuals.textPaint.setTextSize(13 * var12);
                  var1.drawText(
                     (String)var30.getFirst(),
                     var20 + var17 / var16 - GalaxyVisuals.textPaint.measureText((String)var30.getFirst()) / var16,
                     var13 - 8 * var12,
                     GalaxyVisuals.textPaint
                  );
               }
            }
         }
      }
   }

   public static final class FaderView extends View {
      private final int color;
      private final int last;
      private Function1<? super String, Unit> onCommit;
      private Function1<? super String, Unit> onMove;
      private final Paint p;
      private final List<String> positions;
      private String value;

      public FaderView(Context var1, int var2, List<String> var3) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var3, "positions");
         super(var1);
         this.color = var2;
         this.positions = var3;
         this.value = CollectionsKt.first(this.positions);
         this.p = new Paint(1);
         this.last = Math.max(this.positions.size() - 1, 1);
      }

      private final int posOf(String var1) {
         Integer var11 = this.positions.indexOf(var1);
         boolean var10;
         if (var11.intValue() >= 0) {
            var10 = 1;
         } else {
            var10 = 0;
         }

         Iterator var13 = null;
         Object var12 = null;
         if (!var10) {
            var11 = null;
         }

         if (var11 != null) {
            var10 = var11;
         } else {
            Double var22 = StringsKt.toDoubleOrNull(StringsKt.trim(StringsKt.removeSuffix(var1, "%")).toString());
            Integer var14 = var13;
            if (var22 != null) {
               double var8 = var22.doubleValue();
               var13 = CollectionsKt.getIndices(this.positions).iterator();
               Object var15;
               if (!var13.hasNext()) {
                  var15 = (Double)var12;
               } else {
                  var15 = (Double)var13.next();
                  if (var13.hasNext()) {
                     var10 = var15.intValue();
                     Double var23 = StringsKt.toDoubleOrNull(StringsKt.trim(StringsKt.removeSuffix(this.positions.get(var10), "%")).toString());
                     double var2;
                     if (var23 != null) {
                        var2 = var23;
                     } else {
                        var2 = 1.0E9;
                     }

                     var2 = Math.abs(var2 - var8);
                     Object var24 = var15;

                     do {
                        var12 = var13.next();
                        var10 = ((Number)var12).intValue();
                        var15 = StringsKt.toDoubleOrNull(StringsKt.trim(StringsKt.removeSuffix(this.positions.get(var10), "%")).toString());
                        double var4;
                        if (var15 != null) {
                           var4 = var15;
                        } else {
                           var4 = 1.0E9;
                        }

                        double var6 = Math.abs(var4 - var8);
                        var15 = (Double)var24;
                        var4 = var2;
                        if (Double.compare(var2, var6) > 0) {
                           var15 = (Double)var12;
                           var4 = var6;
                        }

                        var24 = var15;
                        var2 = var4;
                     } while (var13.hasNext());
                  }
               }

               var14 = (Integer)var15;
            }

            if (var14 != null) {
               var10 = var14;
            } else {
               var10 = 0;
            }
         }

         return var10;
      }

      public final Function1<String, Unit> getOnCommit() {
         return this.onCommit;
      }

      public final Function1<String, Unit> getOnMove() {
         return this.onMove;
      }

      public final String getValue() {
         return this.value;
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         float var4 = this.getResources().getDisplayMetrics().density;
         float var2 = this.getWidth() / 2.0F;
         float var5 = 16;
         float var3 = var5 * var4;
         float var7 = this.getHeight() - var5 * var4;
         var5 = var7 - (float)this.posOf(this.value) / this.last * (var7 - var3);
         this.p.setColor(Color.rgb(42, 49, 66));
         float var6 = 6;
         var1.drawRoundRect(var2 - var6 * var4, var3, var2 + var6 * var4, var7, var6 * var4, var6 * var4, this.p);
         Paint var9 = this.p;
         int var8;
         if (Intrinsics.areEqual(this.value, "Auto")) {
            var8 = Color.rgb(120, 200, 255);
         } else {
            var8 = this.color;
         }

         var9.setColor(var8);
         var1.drawRoundRect(var2 - var6 * var4, var5, var2 + var6 * var4, var7, var6 * var4, var6 * var4, this.p);
         this.p.setColor(-1);
         var1.drawCircle(var2, var5, 15 * var4, this.p);
      }

      public boolean onTouchEvent(MotionEvent var1) {
         Intrinsics.checkNotNullParameter(var1, "e");
         if (!this.isEnabled()) {
            return false;
         } else {
            float var2 = this.getResources().getDisplayMetrics().density;
            float var3 = 16;
            float var4 = this.getHeight() - var3 * var2;
            String var5 = this.positions
               .get(
                  RangesKt.coerceIn(
                     Math.round(RangesKt.coerceIn((var4 - var1.getY()) / (var4 - var3 * var2), 0.0F, 1.0F) * this.last), 0, this.positions.size() - 1
                  )
               );
            switch (var1.getActionMasked()) {
               case 0:
                  ViewParent var8 = this.getParent();
                  if (var8 != null) {
                     var8.requestDisallowInterceptTouchEvent(true);
                  }

                  this.value = var5;
                  Function1 var9 = this.onMove;
                  if (var9 != null) {
                     var9.invoke(var5);
                  }

                  this.invalidate();
                  break;
               case 1:
                  Function1 var7 = this.onCommit;
                  if (var7 != null) {
                     var7.invoke(this.value);
                  }
                  break;
               case 2:
                  if (!Intrinsics.areEqual(var5, this.value)) {
                     this.value = var5;
                     Function1 var6 = this.onMove;
                     if (var6 != null) {
                        var6.invoke(var5);
                     }

                     this.invalidate();
                  }
            }

            return true;
         }
      }

      public final void setOnCommit(Function1<? super String, Unit> var1) {
         this.onCommit = var1;
      }

      public final void setOnMove(Function1<? super String, Unit> var1) {
         this.onMove = var1;
      }

      public final void setValue(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.value = var1;
      }
   }

   public static final class GapView extends View {
      private final int color;
      private final double mph;
      private final Paint p;
      private final RectF r;
      private final double seconds;

      public GapView(Context var1, double var2, double var4, int var6) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         super(var1);
         this.seconds = var2;
         this.mph = var4;
         this.color = var6;
         this.p = new Paint(1);
         this.r = new RectF();
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         float var5 = this.getResources().getDisplayMetrics().density;
         float var6 = this.getWidth();
         float var9 = this.getHeight();
         double var2 = GalaxyVisuals.INSTANCE.gapFeet(this.seconds, this.mph);
         float var4 = 70 * var5;
         float var8 = 30 * var5;
         float var7 = 2;
         float var10 = 160;
         float var11 = RangesKt.coerceAtLeast((float)(var2 / 286.0 * (var6 - var7 * var4 - var10 * var5)), 6 * var5);
         var9 /= var7;
         this.p.setColor(Color.rgb(34, 40, 55));
         float var12 = var8 / var7;
         var10 = 10;
         var1.drawRoundRect(0.0F, var9 - var12 - var10 * var5, var6, var9 + var8 / var7 + var10 * var5, var10 * var5, var10 * var5, this.p);
         this.p.setColor(this.color);
         RectF var16 = this.r;
         var10 = 12;
         var16.set(var10 * var5, var9 - var8 / var7, var10 * var5 + var4, var9 + var8 / var7);
         var16 = this.r;
         var12 = 9;
         var1.drawRoundRect(var16, var12 * var5, var12 * var5, this.p);
         this.p.setColor(Color.rgb(170, 182, 200));
         this.r.set(var10 * var5 + var4 + var11, var9 - var8 / var7, var10 * var5 + var7 * var4 + var11, var9 + var8 / var7);
         var1.drawRoundRect(this.r, var12 * var5, var12 * var5, this.p);
         this.p.setColor(this.color);
         this.p.setStrokeWidth(var7 * var5);
         this.p.setStyle(Style.STROKE);
         var12 = 4;
         var1.drawLine(var12 * var5 + (var10 * var5 + var4), var9, var10 * var5 + var4 + var11 - var12 * var5, var9, this.p);
         this.p.setStyle(Style.FILL);
         GalaxyVisuals.textPaint.setColor(-1);
         GalaxyVisuals.textPaint.setTextSize(17 * var5);
         GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
         long var14 = Math.round(var2);
         String var27 = var14 + " ft";
         var12 = var11 / var7;
         var11 = GalaxyVisuals.textPaint.measureText(var27) / var7;
         var7 = var8 / var7;
         var8 = 14;
         var1.drawText(var27, var10 * var5 + var4 + var12 - var11, var9 - var7 - var8 * var5, GalaxyVisuals.textPaint);
         GalaxyVisuals.textPaint.setColor(Color.rgb(150, 160, 180));
         GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT);
         GalaxyVisuals.textPaint.setTextSize(15 * var5);
         int var13 = (int)this.mph;
         String var28 = GalaxyRow.Companion.fmt(this.seconds);
         String var29 = var13 + " mph · " + var28 + " s";
         var1.drawText(var29, var6 - GalaxyVisuals.textPaint.measureText(var29) - var8 * var5, var9 + 5 * var5, GalaxyVisuals.textPaint);
      }
   }

   public static final class LaneView extends View {
      private final int color;
      private final double offset;
      private final Paint p;
      private final double range;
      private final String unit;

      public LaneView(Context var1, double var2, String var4, double var5, int var7) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var4, "unit");
         super(var1);
         this.offset = var2;
         this.unit = var4;
         this.range = var5;
         this.color = var7;
         this.p = new Paint(1);
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         float var6 = this.getResources().getDisplayMetrics().density;
         float var5 = this.getWidth();
         float var3 = this.getHeight();
         float var7 = Math.min(0.5F * var5, 420 * var6);
         float var4 = 2;
         float var2 = (var5 - var7) / var4;
         this.p.setColor(Color.rgb(28, 32, 42));
         var1.drawRect(var2, 0.0F, var2 + var7, var3, this.p);
         this.p.setColor(-1);
         this.p.setStrokeWidth(4 * var6);
         var1.drawLine(var2, 0.0F, var2, var3, this.p);
         var1.drawLine(var2 + var7, 0.0F, var2 + var7, var3, this.p);
         this.p.setColor(Color.rgb(80, 88, 104));
         this.p.setStrokeWidth(var4 * var6);

         for (float var16 = 6 * var6; var16 < var3; var16 += 26 * var6) {
            var1.drawLine(var5 / var4, var16, var5 / var4, var16 + 14 * var6, this.p);
         }

         float var10 = var7 * 0.42F;
         var7 = (float)(this.offset / this.range * var7 * 0.25);
         this.p.setColor(this.color);
         var2 = var5 / var4;
         float var9 = var10 / var4;
         float var8 = var5 / var4;
         var10 /= var4;
         float var11 = 18;
         var1.drawRoundRect(var2 - var9 + var7, var3 * 0.22F, var8 + var10 + var7, var3 * 0.86F, var11 * var6, var11 * var6, this.p);
         GalaxyVisuals.textPaint.setColor(Color.rgb(11, 13, 18));
         GalaxyVisuals.textPaint.setTextSize(16 * var6);
         GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
         boolean var12;
         if (this.offset == 0.0) {
            var12 = true;
         } else {
            var12 = false;
         }

         String var13;
         if (var12) {
            var13 = "center";
         } else {
            String var15 = GalaxyRow.Companion.fmt(Math.abs(this.offset));
            String var14 = this.unit;
            if (this.offset > 0.0) {
               var13 = "→";
            } else {
               var13 = "←";
            }

            var13 = var15 + " " + var14 + " " + var13;
         }

         var1.drawText(var13, var5 / var4 + var7 - GalaxyVisuals.textPaint.measureText(var13) / var4, 0.56F * var3, GalaxyVisuals.textPaint);
      }
   }

   public static final class RingView extends View {
      private final int color;
      private final double frac;
      private final Paint p;

      public RingView(Context var1, double var2, int var4) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         super(var1);
         this.frac = var2;
         this.color = var4;
         Paint var5 = new Paint(1);
         var5.setStyle(Style.STROKE);
         var5.setStrokeCap(Cap.ROUND);
         this.p = var5;
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         float var3 = this.getResources().getDisplayMetrics().density;
         float var2 = Math.min(this.getWidth(), this.getHeight());
         float var4 = 10;
         RectF var7 = new RectF(var4 * var3, var4 * var3, var2 - var4 * var3, var2 - var4 * var3);
         this.p.setStrokeWidth(12 * var3);
         this.p.setColor(Color.rgb(34, 40, 55));
         var1.drawArc(var7, 0.0F, 360.0F, false, this.p);
         this.p.setColor(this.color);
         var1.drawArc(var7, -90.0F, (float)(360 * RangesKt.coerceIn(this.frac, 0.0, 1.0)), false, this.p);
         GalaxyVisuals.textPaint.setColor(-1);
         GalaxyVisuals.textPaint.setTextSize(26 * var3);
         GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
         long var5 = Math.round(this.frac * 100);
         String var9 = var5 + "%";
         var4 = 2;
         var1.drawText(var9, var2 / var4 - GalaxyVisuals.textPaint.measureText(var9) / var4, var2 / var4 + 9 * var3, GalaxyVisuals.textPaint);
      }
   }
}
