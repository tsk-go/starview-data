package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.text.SpannableStringBuilder;
import android.text.TextUtils.TruncateAt;
import android.text.style.StyleSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class OffroadHome extends FrameLayout {
   public static final long AMBIENT_MS = 180000L;
   public static final OffroadHome.Companion Companion = new OffroadHome.Companion(null);
   private final OffroadHome.Actions actions;
   private final TextView alertsBtn;
   private final OffroadHome.StatBlock allTime;
   private final TextView ambClock;
   private final TextView ambDate;
   private final TextView ambLine;
   private final LinearLayout ambient;
   private final float d;
   private final TextView detailAction;
   private final LinearLayout detailBody;
   private final LinearLayout detailContent;
   private final TextView detailTitle;
   private final TextView devLines;
   private final TextView devName;
   private final TextView expBtn;
   private final LinearLayout homeContent;
   private final TextView lastDrive;
   private long lastDriveAt;
   private String lastDriveLine;
   private int lastGen;
   private Telemetry lastT;
   private long lastTouch;
   private final OffroadHome.StatBlock pastWeek;
   private final List<TextView> persBtns;
   private boolean prevAlerts;
   private boolean prevUpdate;
   private OffroadHome.State state;
   private final TextView statsNote;
   private JSONObject storage;
   private final TextView updateBtn;
   private final TextView version;

   // $VF: synthetic method
   public static Unit $r8$lambda$2zpruIcmrIXP7fTvCze_e_mdHYU/* $VF was: $r8$lambda$2zpruIcmrIXP7fTvCze_e_mdHYU*/(OffroadHome var0) {
      return _init_$lambda$49(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$S0iKKoWVK0g3C7M49zOtbbqKb5w(OffroadHome var0, View var1) {
      _init_$lambda$22(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ZBIXojftdtiV_a10gWXDTHLPa3M(OffroadHome var0, View var1) {
      _init_$lambda$23(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ZiKwe2_XIgvUsGrnwt1LnuIMbWo/* $VF was: $r8$lambda$ZiKwe2_XIgvUsGrnwt1LnuIMbWo*/(OffroadHome var0, View var1) {
      _init_$lambda$34(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$eh9M_JtXvsQOSuiKLhpN0c_pjgg/* $VF was: $r8$lambda$eh9M_JtXvsQOSuiKLhpN0c_pjgg*/(OffroadHome var0) {
      return _init_$lambda$47(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fNjOXbNatU6GSz14BfQG3Jq0yf4(OffroadHome var0) {
      return _init_$lambda$48(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$jIRbb_GoHx6ZFFgdVYAlhrspLuE/* $VF was: $r8$lambda$jIRbb_GoHx6ZFFgdVYAlhrspLuE*/(
      OffroadHome var0, DialogInterface var1, int var2
   ) {
      toggleExperimental$lambda$86(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$qqX3kqv_WK3oNruaLov89_CEtxI/* $VF was: $r8$lambda$qqX3kqv_WK3oNruaLov89_CEtxI*/(OffroadHome var0, View var1) {
      refresh$lambda$83(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$x8sMlsJdFLxPcADSIQwaxuQ_C9g(OffroadHome var0, View var1) {
      _init_$lambda$32(var0, var1);
   }

   public OffroadHome(Context var1, OffroadHome.Actions var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "actions");
      super(var1);
      this.actions = var2;
      this.d = this.getResources().getDisplayMetrics().density;
      this.state = OffroadHome.State.HOME;
      TextView var5 = tv$default(this, 22.0F, 0, true, 2, null);
      var5.setText("UPDATE");
      var5.setGravity(17);
      var5.setPadding(this.dp(26), this.dp(12), this.dp(26), this.dp(12));
      var5.setBackground((Drawable)this.rounded(Color.rgb(54, 77, 239), 14));
      Unit var18 = Unit.INSTANCE;
      this.updateBtn = var5;
      var5 = tv$default(this, 22.0F, 0, true, 2, null);
      var5.setGravity(17);
      var5.setPadding(this.dp(26), this.dp(12), this.dp(26), this.dp(12));
      var5.setBackground((Drawable)this.rounded(Color.rgb(226, 44, 44), 14));
      Unit var19 = Unit.INSTANCE;
      this.alertsBtn = var5;
      var5 = tv$default(this, 26.0F, 0, false, 6, null);
      var5.setGravity(8388629);
      var5.setMaxLines(1);
      var5.setEllipsize(TruncateAt.END);
      Unit var20 = Unit.INSTANCE;
      this.version = var5;
      this.allTime = new OffroadHome.StatBlock(this, (int)"ALL TIME", Color.rgb(145, 110, 255));
      this.pastWeek = new OffroadHome.StatBlock(this, (int)"PAST WEEK", Color.rgb(40, 200, 190));
      this.statsNote = tv$default(this, 15.0F, Color.rgb(150, 160, 180), false, 4, null);
      var5 = tv$default(this, 26.0F, 0, true, 2, null);
      var5.setGravity(16);
      var5.setPadding(this.dp(30), 0, this.dp(30), 0);
      Unit var21 = Unit.INSTANCE;
      this.expBtn = var5;
      Iterable var22 = CollectionsKt.listOf(new Pair[]{TuplesKt.to("Aggressive", 0), TuplesKt.to("Standard", 1), TuplesKt.to("Relaxed", 2)});
      var var48 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var22, 10));

      for (Pair var8 : var22) {
         String var7 = (String)var8.component1();
         int var3 = ((Number)var8.component2()).intValue();
         TextView var109 = tv$default(this, 19.0F, 0, true, 2, null);
         var109.setText(var7);
         var109.setGravity(17);
         var109.setPadding(0, this.dp(16), 0, this.dp(16));
         var109.setTag(var3);
         var48.add(var109);
      }

      List var23 = var48;
      this.persBtns = var23;
      this.devName = tv$default(this, 24.0F, 0, true, 2, null);
      var5 = tv$default(this, 17.0F, Color.rgb(190, 198, 214), false, 4, null);
      var5.setLineSpacing(this.dp(6), 1.0F);
      Unit var24 = Unit.INSTANCE;
      this.devLines = var5;
      this.detailTitle = tv$default(this, 34.0F, 0, true, 2, null);
      LinearLayout var25 = new LinearLayout(var1);
      var25.setOrientation(1);
      Unit var50 = Unit.INSTANCE;
      this.detailBody = var25;
      var5 = tv$default(this, 20.0F, 0, true, 2, null);
      var5.setGravity(17);
      var5.setPadding(this.dp(28), this.dp(16), this.dp(28), this.dp(16));
      var5.setBackground((Drawable)this.rounded(Color.rgb(54, 77, 239), 14));
      Unit var26 = Unit.INSTANCE;
      this.detailAction = var5;
      TextView var27 = tv$default(this, 17.0F, Color.rgb(190, 198, 214), false, 4, null);
      var27.setPadding(this.dp(18), this.dp(14), this.dp(18), this.dp(14));
      var27.setBackground((Drawable)this.rounded(Color.rgb(34, 36, 44), 14));
      var27.setLineSpacing(this.dp(4), 1.0F);
      var27.setVisibility(8);
      Unit var52 = Unit.INSTANCE;
      this.lastDrive = var27;
      LinearLayout var53 = new LinearLayout(var1);
      var53.setOrientation(1);
      var53.setGravity(17);
      var53.setBackgroundColor(-16777216);
      var53.setVisibility(8);
      var53.setClickable(true);
      Unit var28 = Unit.INSTANCE;
      this.ambient = var53;
      TextView var29 = tv$default(this, 150.0F, Color.rgb(205, 210, 220), false, 4, null);
      var29.setGravity(17);
      var29.setTypeface(Typeface.create("sans-serif-thin", 0));
      Unit var54 = Unit.INSTANCE;
      this.ambClock = var29;
      var5 = tv$default(this, 28.0F, Color.rgb(130, 138, 155), false, 4, null);
      var5.setGravity(17);
      Unit var30 = Unit.INSTANCE;
      this.ambDate = var5;
      TextView var31 = tv$default(this, 20.0F, Color.rgb(100, 108, 124), false, 4, null);
      var31.setGravity(17);
      Unit var56 = Unit.INSTANCE;
      this.ambLine = var31;
      this.lastTouch = System.currentTimeMillis();
      this.setBackgroundColor(-16777216);
      LinearLayout var57 = new LinearLayout(var1);
      var57.setOrientation(1);
      var57.setPadding(this.dp(36), this.dp(28), this.dp(36), this.dp(28));
      this.addView((View)var57, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      LinearLayout var32 = new LinearLayout(var1);
      var32.setOrientation(0);
      var32.setGravity(16);
      View var71 = (View)this.updateBtn;
      android.widget.LinearLayout.LayoutParams var110 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var110.setMarginEnd(this.dp(16));
      Unit var94 = Unit.INSTANCE;
      var32.addView(var71, (LayoutParams)var110);
      var71 = (View)this.alertsBtn;
      android.widget.LinearLayout.LayoutParams var95 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var95.setMarginEnd(this.dp(16));
      Unit var111 = Unit.INSTANCE;
      var32.addView(var71, (LayoutParams)var95);
      var32.addView((View)this.version, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      var57.addView((View)var32, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, this.dp(72))));
      this.updateBtn.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda0(this));
      this.alertsBtn.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda3(this));
      FrameLayout var112 = new FrameLayout(var1);
      View var9 = (View)var112;
      android.widget.LinearLayout.LayoutParams var73 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
      var73.topMargin = this.dp(20);
      var94 = Unit.INSTANCE;
      var57.addView(var9, (LayoutParams)var73);
      LinearLayout var58 = new LinearLayout(var1);
      var58.setOrientation(0);
      Unit var74 = Unit.INSTANCE;
      this.homeContent = var58;
      LinearLayout var59 = new LinearLayout(var1);
      var59.setOrientation(1);
      int var42 = Color.rgb(24, 24, 28);
      var59.setBackground((Drawable)rounded$default(this, var42, 0, 2, null));
      var59.setPadding(this.dp(32), this.dp(28), this.dp(32), this.dp(28));
      TextView var97 = this.tv(16.0F, Color.rgb(150, 160, 180), true);
      var97.setText("DRIVE STATS");
      var97.setLetterSpacing(0.12F);
      Unit var75 = Unit.INSTANCE;
      var59.addView((View)var97);
      var71 = (View)this.allTime.getView();
      android.widget.LinearLayout.LayoutParams var113 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
      var113.topMargin = this.dp(18);
      var94 = Unit.INSTANCE;
      var59.addView(var71, (LayoutParams)var113);
      var9 = (View)this.pastWeek.getView();
      android.widget.LinearLayout.LayoutParams var99 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
      var99.topMargin = this.dp(18);
      Unit var77 = Unit.INSTANCE;
      var59.addView(var9, (LayoutParams)var99);
      var71 = (View)this.statsNote;
      android.widget.LinearLayout.LayoutParams var100 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var100.topMargin = this.dp(12);
      Unit var115 = Unit.INSTANCE;
      var59.addView(var71, (LayoutParams)var100);
      var9 = (View)this.lastDrive;
      android.widget.LinearLayout.LayoutParams var101 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var101.topMargin = this.dp(14);
      Unit var79 = Unit.INSTANCE;
      var59.addView(var9, (LayoutParams)var101);
      this.lastDrive.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda4(this));
      this.homeContent.addView((View)var59, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -1, 1.25F)));
      LinearLayout var117 = new LinearLayout(var1);
      var117.setOrientation(1);
      var117.addView((View)this.expBtn, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, this.dp(110))));
      this.expBtn.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda5(this));
      LinearLayout var60 = new LinearLayout(var1);
      var60.setOrientation(1);
      var60.setBackground((Drawable)rounded$default(this, Color.rgb(24, 24, 28), 0, 2, null));
      var60.setPadding(this.dp(22), this.dp(20), this.dp(22), this.dp(22));
      TextView var102 = this.tv(16.0F, Color.rgb(150, 160, 180), true);
      var102.setText("DRIVING PERSONALITY");
      var102.setLetterSpacing(0.12F);
      Unit var80 = Unit.INSTANCE;
      var60.addView((View)var102);
      LinearLayout var10 = new LinearLayout(var1);
      var10.setOrientation(0);
      Iterable var103 = this.persBtns;
      boolean var4 = false;
      var42 = 0;

      for (Object var11 : var103) {
         if (var42 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         TextView var14 = (TextView)var11;
         View var13 = (View)var14;
         android.widget.LinearLayout.LayoutParams var12 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
         if (var42 > 0) {
            var12.setMarginStart(this.dp(10));
         }

         var11 = Unit.INSTANCE;
         var10.addView(var13, (LayoutParams)var12);
         var14.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda6(this, var14));
         var42++;
      }

      View var118 = (View)var10;
      android.widget.LinearLayout.LayoutParams var104 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var104.topMargin = this.dp(14);
      Unit var82 = Unit.INSTANCE;
      var60.addView(var118, (LayoutParams)var104);
      View var119 = (View)var60;
      android.widget.LinearLayout.LayoutParams var83 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var83.topMargin = this.dp(20);
      var94 = Unit.INSTANCE;
      var117.addView(var119, (LayoutParams)var83);
      LinearLayout var106 = new LinearLayout(var1);
      var106.setOrientation(1);
      var106.setBackground((Drawable)rounded$default(this, Color.rgb(24, 24, 28), 0, 2, null));
      var106.setPadding(this.dp(26), this.dp(22), this.dp(26), this.dp(22));
      var106.addView((View)this.devName);
      View var120 = (View)this.devLines;
      android.widget.LinearLayout.LayoutParams var124 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
      var124.topMargin = this.dp(12);
      Unit var84 = Unit.INSTANCE;
      var106.addView(var120, (LayoutParams)var124);
      LinearLayout var85 = new LinearLayout(var1);
      var85.setOrientation(0);
      Iterable var121 = CollectionsKt.listOf(
         new Pair[]{
            TuplesKt.to("Settings", new OffroadHome$$ExternalSyntheticLambda7(this)),
            TuplesKt.to("Files", new OffroadHome$$ExternalSyntheticLambda8(this)),
            TuplesKt.to("Terminal", new OffroadHome$$ExternalSyntheticLambda9(this))
         }
      );
      var42 = 0;

      for (Object var125 : var121) {
         if (var42 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         Pair var128 = (Pair)var125;
         String var126 = (String)var128.component1();
         Function0 var131 = (Function0)var128.component2();
         TextView var129 = tv$default(this, 18.0F, 0, true, 2, null);
         var129.setText(var126);
         var129.setGravity(17);
         var129.setPadding(0, this.dp(16), 0, this.dp(16));
         var129.setBackground((Drawable)this.rounded(Color.rgb(44, 48, 60), 14));
         var129.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda10(var131));
         View var127 = (View)var129;
         android.widget.LinearLayout.LayoutParams var132 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
         if (var42 > 0) {
            var132.setMarginStart(this.dp(10));
         }

         Unit var130 = Unit.INSTANCE;
         var85.addView(var127, (LayoutParams)var132);
         var42++;
      }

      var71 = (View)var85;
      android.widget.LinearLayout.LayoutParams var33 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var33.topMargin = this.dp(12);
      Unit var61 = Unit.INSTANCE;
      var106.addView(var71, (LayoutParams)var33);
      View var62 = (View)var106;
      android.widget.LinearLayout.LayoutParams var87 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
      var87.topMargin = this.dp(20);
      Unit var34 = Unit.INSTANCE;
      var117.addView(var62, (LayoutParams)var87);
      LinearLayout var35 = this.homeContent;
      var71 = (View)var117;
      android.widget.LinearLayout.LayoutParams var107 = new android.widget.LinearLayout.LayoutParams(0, -1, 1.0F);
      var107.setMarginStart(this.dp(24));
      Unit var63 = Unit.INSTANCE;
      var35.addView(var71, (LayoutParams)var107);
      var112.addView((View)this.homeContent, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      LinearLayout var36 = new LinearLayout(var1);
      var36.setOrientation(1);
      var36.setBackground((Drawable)rounded$default(this, Color.rgb(24, 24, 28), 0, 2, null));
      var36.setPadding(this.dp(36), this.dp(30), this.dp(36), this.dp(30));
      var36.setVisibility(8);
      Unit var64 = Unit.INSTANCE;
      this.detailContent = var36;
      this.detailContent.addView((View)this.detailTitle);
      LinearLayout var37 = this.detailContent;
      ScrollView var89 = new ScrollView(var1);
      var89.addView((View)this.detailBody);
      Unit var65 = Unit.INSTANCE;
      View var108 = (View)var89;
      android.widget.LinearLayout.LayoutParams var66 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
      var66.topMargin = this.dp(20);
      var66.bottomMargin = this.dp(20);
      Unit var90 = Unit.INSTANCE;
      var37.addView(var108, (LayoutParams)var66);
      LinearLayout var15 = new LinearLayout(var1);
      var15.setOrientation(0);
      var15.setGravity(8388613);
      var5 = tv$default(this, 20.0F, 0, true, 2, null);
      var5.setText("Close");
      var5.setGravity(17);
      var5.setPadding(this.dp(28), this.dp(16), this.dp(28), this.dp(16));
      var5.setBackground((Drawable)this.rounded(Color.rgb(44, 48, 60), 14));
      var5.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda11(this));
      Unit var38 = Unit.INSTANCE;
      var15.addView((View)var5);
      View var68 = (View)this.detailAction;
      android.widget.LinearLayout.LayoutParams var91 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var91.setMarginStart(this.dp(14));
      Unit var39 = Unit.INSTANCE;
      var15.addView(var68, (LayoutParams)var91);
      this.detailContent.addView((View)var15, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
      var112.addView((View)this.detailContent, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      this.ambient.addView((View)this.ambClock);
      LinearLayout var40 = this.ambient;
      View var69 = (View)this.ambDate;
      android.widget.LinearLayout.LayoutParams var92 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var92.topMargin = this.dp(8);
      Unit var16 = Unit.INSTANCE;
      var40.addView(var69, (LayoutParams)var92);
      LinearLayout var93 = this.ambient;
      View var41 = (View)this.ambLine;
      android.widget.LinearLayout.LayoutParams var17 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var17.topMargin = this.dp(30);
      Unit var70 = Unit.INSTANCE;
      var93.addView(var41, (LayoutParams)var17);
      this.addView((View)this.ambient, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      this.lastGen = -1;
   }

   private static final void _init_$lambda$22(OffroadHome var0, View var1) {
      OffroadHome.State var2;
      if (var0.state == OffroadHome.State.UPDATE) {
         var2 = OffroadHome.State.HOME;
      } else {
         var2 = OffroadHome.State.UPDATE;
      }

      var0.setState(var2);
   }

   private static final void _init_$lambda$23(OffroadHome var0, View var1) {
      OffroadHome.State var2;
      if (var0.state == OffroadHome.State.ALERTS) {
         var2 = OffroadHome.State.HOME;
      } else {
         var2 = OffroadHome.State.ALERTS;
      }

      var0.setState(var2);
   }

   private static final void _init_$lambda$32(OffroadHome var0, View var1) {
      var0.actions.openSettings("journal:last");
   }

   private static final void _init_$lambda$34(OffroadHome var0, View var1) {
      var0.toggleExperimental();
   }

   private static final Unit _init_$lambda$47(OffroadHome var0) {
      var0.actions.openSettings(null);
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$48(OffroadHome var0) {
      var0.actions.openFiles();
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$49(OffroadHome var0) {
      var0.actions.openTerminal();
      return Unit.INSTANCE;
   }

   private final List<OffroadHome.Alert> alerts(Telemetry var1) {
      Map var4 = var1.getParams();
      LinkedHashMap var10 = new LinkedHashMap();
      Iterator var5 = var4.entrySet().iterator();

      while (true) {
         boolean var3 = var5.hasNext();
         boolean var2 = false;
         if (!var3) {
            Map var19 = var10;
            var3 = false;
            var var7 = new ArrayList();
            Iterator var6 = var19.entrySet().iterator();

            while (var6.hasNext()) {
               JSONObject var11 = this.jobj(((Entry)var6.next()).getValue());
               OffroadHome.Alert var12;
               if (var11 == null) {
                  var12 = null;
               } else {
                  String var9 = var11.optString("text");
                  Intrinsics.checkNotNullExpressionValue(var9, "optString(...)");
                  String var8 = var11.optString("extra");
                  Intrinsics.checkNotNullExpressionValue(var8, "optString(...)");
                  var8 = StringsKt.replace$default(var9, "%1", var8, false, 4, null);
                  if (StringsKt.isBlank(var8)) {
                     var12 = null;
                     var2 = false;
                  } else {
                     var2 = false;
                     var12 = new OffroadHome.Alert(var8, var11.optInt("severity", 0));
                  }
               }

               if (var12 != null) {
                  var7.add(var12);
               }
            }

            List var13 = var7;
            Iterable var14 = var13;
            return CollectionsKt.sortedWith(var14, new Comparator() {
               @Override
               public final int compare(T var1, T var2x) {
                  return ComparisonsKt.compareValues(((OffroadHome.Alert)var2x).getSeverity(), ((OffroadHome.Alert)var1).getSeverity());
               }
            });
         }

         Entry var18 = (Entry)var5.next();
         if (StringsKt.startsWith$default((String)var18.getKey(), "Offroad_", false, 2, null)) {
            var10.put(var18.getKey(), var18.getValue());
         }
      }
   }

   private final void ambientTick(Telemetry var1) {
      if (!this.isShown()) {
         this.lastTouch = System.currentTimeMillis();
      } else {
         long var5 = System.currentTimeMillis();
         long var7 = this.lastTouch;
         boolean var4 = false;
         boolean var3;
         if (var5 - var7 > 180000L) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         if (var3 && this.ambient.getVisibility() != 0) {
            this.ambient.setVisibility(0);
         }

         if (this.ambient.getVisibility() == 0) {
            Date var9 = new Date();
            this.ambClock.setText(new SimpleDateFormat("h:mm", Locale.US).format(var9));
            this.ambDate.setText(new SimpleDateFormat("EEEE, MMMM d", Locale.US).format(var9));
            TextView var10 = this.ambLine;
            String var11 = this.lastDriveLine;
            Float var12 = var1.getCpuTempC();
            float var2 = var12.floatValue();
            var3 = var4;
            if (var2 > 0.0F) {
               var3 = (boolean)1;
            }

            String var16 = null;
            if (!var3) {
               var12 = null;
            }

            if (var12 != null) {
               var2 = var12.floatValue();
               var3 = Math.round(9 * var2 / 5 + 32);
               var16 = "comma " + var3 + "°F";
            }

            var10.setText(
               CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull(new String[]{var11, var16}), "   ·   ", null, null, 0, null, null, 62, null)
            );
         }
      }
   }

   private final int dp(int var1) {
      return (int)(var1 * this.d);
   }

   private final String fmt(double var1) {
      String var3;
      if (var1 >= 100.0) {
         var3 = String.format("%,d", Arrays.copyOf(new Object[]{MathKt.roundToInt(var1)}, 1));
      } else {
         var3 = String.format("%.1f", Arrays.copyOf(new Object[]{var1}, 1));
      }

      Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      return var3;
   }

   private final String gb(JSONObject var1, String var2) {
      double var3 = var1.optDouble(var2, Double.NaN);
      String var5;
      if (Double.isNaN(var3)) {
         var5 = "?";
      } else if (var3 >= 100.0) {
         var5 = String.valueOf(MathKt.roundToInt(var3));
      } else {
         var5 = String.format("%.1f", Arrays.copyOf(new Object[]{var3}, 1));
         Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
      }

      return var5;
   }

   private final JSONObject jobj(Object var1) {
      if (var1 instanceof JSONObject) {
         var1 = var1;
      } else if (var1 instanceof Map) {
         var1 = new JSONObject((Map)var1);
      } else if (var1 instanceof String) {
         JSONObject var2;
         try {
            var2 = new JSONObject((String)var1);
         } catch (Exception var3) {
            var1 = null;
            return var1;
         }

         var1 = var2;
      } else {
         var1 = null;
      }

      return var1;
   }

   static final void lambda$41$lambda$40(OffroadHome var0, TextView var1, View var2) {
      OffroadHome.Actions var7 = var0.actions;
      Pair var3 = TuplesKt.to("action", "param");
      Pair var4 = TuplesKt.to("key", "LongitudinalPersonality");
      Object var5 = var1.getTag();
      Intrinsics.checkNotNull(var5, "null cannot be cast to non-null type kotlin.Int");
      var7.control(new JSONObject(MapsKt.mapOf(new Pair[]{var3, var4, TuplesKt.to("value", (Integer)var5)})));
      var5 = var0.lastT;
      if (var5 != null) {
         var0.refresh((Telemetry)var5, true);
      }
   }

   static final void lambda$53$lambda$51$lambda$50(Function0 var0, View var1) {
      var0.invoke();
   }

   static final void lambda$62$lambda$61(OffroadHome var0, View var1) {
      var0.setState(OffroadHome.State.HOME);
   }

   private final double num(JSONObject var1, String var2) {
      double var5 = 0.0;
      double var3 = var5;
      if (var1 != null) {
         Double var7 = var1.optDouble(var2, 0.0);
         if (Double.isNaN(var7.doubleValue())) {
            var7 = null;
         }

         var3 = var5;
         if (var7 != null) {
            var3 = var7;
         }
      }

      return var3;
   }

   private final void paintLastDrive() {
      long var3 = System.currentTimeMillis();
      if (var3 - this.lastDriveAt >= 30000L) {
         this.lastDriveAt = var3;
         JSONObject var5 = CollectionsKt.lastOrNull(DriveJournal.INSTANCE.read("drive"));
         if (var5 != null) {
            double var1 = var5.optDouble("meters") / 1609.344;
            var3 = (var5.optLong("end") - var5.optLong("t")) / '\uea60';
            String var6 = new SimpleDateFormat("EEE h:mm a", Locale.US).format(new Date(var5.optLong("t")));
            StringCompanionObject var7 = StringCompanionObject.INSTANCE;
            String var11 = String.format(Locale.US, "last drive %.1f mi", Arrays.copyOf(new Object[]{var1}, 1));
            Intrinsics.checkNotNullExpressionValue(var11, "format(...)");
            this.lastDriveLine = var11;
            TextView var12 = this.lastDrive;
            StringCompanionObject var8 = StringCompanionObject.INSTANCE;
            String var10 = String.format(
               Locale.US,
               "LAST DRIVE  ·  %s\n%.1f mi  ·  %d min  ·  %d%% openpilot  ·  %d takeovers  ·  %d alerts   ›",
               Arrays.copyOf(new Object[]{var6, var1, var3, var5.optInt("engagedPct"), var5.optInt("takeovers"), var5.optInt("alerts")}, 6)
            );
            Intrinsics.checkNotNullExpressionValue(var10, "format(...)");
            var12.setText(var10);
            this.lastDrive.setVisibility(0);
         }
      }
   }

   private static final double refresh$cloudDist(OffroadHome var0, boolean var1, double var2, JSONObject var4) {
      double var7 = var0.num(var4, "distance");
      double var5 = var7;
      if (var1) {
         var5 = var7 * var2;
      }

      return var5;
   }

   // $VF: synthetic method
   public static void refresh$default(OffroadHome var0, Telemetry var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      var0.refresh(var1, var2);
   }

   private static final void refresh$lambda$83(OffroadHome var0, View var1) {
      var0.actions.openSettings("software");
   }

   private final GradientDrawable rounded(int var1, int var2) {
      GradientDrawable var3 = new GradientDrawable();
      var3.setColor(var1);
      var3.setCornerRadius(this.dp(var2));
      return var3;
   }

   // $VF: synthetic method
   static GradientDrawable rounded$default(OffroadHome var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 20;
      }

      return var0.rounded(var1, var2);
   }

   private final void setState(OffroadHome.State var1) {
      this.state = var1;
      LinearLayout var5 = this.homeContent;
      OffroadHome.State var4 = OffroadHome.State.HOME;
      byte var3 = 0;
      byte var2;
      if (var1 == var4) {
         var2 = 0;
      } else {
         var2 = 8;
      }

      var5.setVisibility(var2);
      LinearLayout var8 = this.detailContent;
      var2 = var3;
      if (var1 == OffroadHome.State.HOME) {
         var2 = 8;
      }

      var8.setVisibility(var2);
      Telemetry var6 = this.lastT;
      if (var6 != null) {
         this.refresh(var6, true);
      }
   }

   private final void toggleExperimental() {
      Telemetry var1 = this.lastT;
      if (var1 != null) {
         if (!var1.pBool("ExperimentalMode", false) && !var1.pBool("ExperimentalModeConfirmed", false)) {
            new Builder(this.getContext())
               .setTitle("Experimental mode")
               .setMessage(
                  "openpilot defaults to driving in chill mode. Experimental mode enables alpha-level features that aren't ready for chill mode: end-to-end longitudinal control (openpilot drives the speed, including stopping for red lights and stop signs) and more. Expect mistakes and keep your attention on the road."
               )
               .setPositiveButton("Enable", new OffroadHome$$ExternalSyntheticLambda2(this))
               .setNegativeButton("Cancel", null)
               .show();
         } else {
            this.actions.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "exp_toggle"))));
         }
      }
   }

   private static final void toggleExperimental$lambda$86(OffroadHome var0, DialogInterface var1, int var2) {
      var0.actions
         .control(new JSONObject(MapsKt.mapOf(new Pair[]{TuplesKt.to("action", "param"), TuplesKt.to("key", "ExperimentalMode"), TuplesKt.to("value", true)})));
   }

   private final TextView tv(float var1, int var2, boolean var3) {
      TextView var4 = new TextView(this.getContext());
      var4.setTextSize(var1);
      var4.setTextColor(var2);
      if (var3) {
         var4.setTypeface(Typeface.DEFAULT_BOLD);
      }

      var4.setIncludeFontPadding(false);
      return var4;
   }

   // $VF: synthetic method
   static TextView tv$default(OffroadHome var0, float var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = -1;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return var0.tv(var1, var2, var3);
   }

   private final String whenStr(double var1) {
      if (!Double.isNaN(var1) && !(var1 <= 0.0)) {
         Calendar var3 = Calendar.getInstance();
         var3.setTimeInMillis((long)(1000 * var1));
         Calendar var4 = Calendar.getInstance();
         String var5 = String.format("%02d:%02d", Arrays.copyOf(new Object[]{var3.get(11), var3.get(12)}, 2));
         Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
         StringBuilder var7;
         String var8;
         switch ((var4.get(1) - var3.get(1)) * 366 + var4.get(6) - var3.get(6)) {
            case 0:
               var7 = new StringBuilder();
               var8 = "today ";
               break;
            case 1:
               var7 = new StringBuilder();
               var8 = "yesterday ";
               break;
            default:
               String var6 = new SimpleDateFormat("MMM d HH:mm", Locale.US).format(var3.getTime());
               Intrinsics.checkNotNullExpressionValue(var6, "format(...)");
               return var6;
         }

         return var7.append(var8).append(var5).toString();
      } else {
         return "–";
      }
   }

   public boolean dispatchTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "ev");
      this.lastTouch = System.currentTimeMillis();
      if (this.ambient.getVisibility() == 0) {
         if (var1.getActionMasked() == 1) {
            this.ambient.setVisibility(8);
         }

         return true;
      } else {
         return super.dispatchTouchEvent(var1);
      }
   }

   public final JSONObject getStorage() {
      return this.storage;
   }

   public final void refresh(Telemetry var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "t");
      this.lastT = var1;
      this.ambientTick(var1);
      this.paintLastDrive();
      if (var2 || var1.getParamsGeneration() != this.lastGen) {
         this.lastGen = var1.getParamsGeneration();
         var2 = var1.pBool("IsMetric", false);
         List var14 = this.alerts(var1);
         boolean var9 = var1.pBool("UpdateAvailable", false);
         TextView var12 = this.updateBtn;
         byte var7;
         if (var9) {
            var7 = 0;
         } else {
            var7 = 8;
         }

         var12.setVisibility(var7);
         var12 = this.alertsBtn;
         if (!var14.isEmpty()) {
            var7 = (byte)0;
         } else {
            var7 = (byte)8;
         }

         var12.setVisibility(var7);
         TextView var13 = this.alertsBtn;
         String var64;
         if (var14.size() == 1) {
            var64 = "1 ALERT";
         } else {
            var7 = var14.size();
            var64 = var7 + " ALERTS";
         }

         var13.setText(var64);
         String var15 = StringsKt.trim(Telemetry.pStr$default(var1, "UpdaterCurrentDescription", null, 2, null)).toString();
         CharSequence var87 = Telemetry.pStr$default(var1, "DrivingModelName", null, 2, null);
         LinearLayout var65 = var87;
         if (StringsKt.isBlank(var87)) {
            var65 = Telemetry.pStr$default(var1, "Model", null, 2, null);
         }

         var65 = StringsKt.trim(
               StringsKt.replace$default(StringsKt.replace$default((String)var65, "_default", "", false, 4, null), "(Default)", "", false, 4, null)
            )
            .toString();
         SpannableStringBuilder var88 = new SpannableStringBuilder();
         var88.append("StarPilot");
         var88.setSpan(new StyleSpan(1), 0, var88.length(), 0);
         boolean var43;
         if (var15.length() > 0) {
            var43 = 1;
         } else {
            var43 = 0;
         }

         if (var43) {
            var88.append("  ").append(var15);
         }

         if (((CharSequence)var65).length() > 0) {
            var43 = (boolean)1;
         } else {
            var43 = (boolean)0;
         }

         if (var43) {
            var88.append("  -  ").append((CharSequence)var65);
         }

         this.version.setText((CharSequence)var88);
         if (var9 && !this.prevUpdate) {
            this.state = OffroadHome.State.UPDATE;
         } else if (!var14.isEmpty() && !this.prevAlerts) {
            this.state = OffroadHome.State.ALERTS;
         }

         if (!var9 && this.state == OffroadHome.State.UPDATE) {
            if (!var14.isEmpty()) {
               var65 = OffroadHome.State.ALERTS;
            } else {
               var65 = OffroadHome.State.HOME;
            }

            this.state = (OffroadHome.State)var65;
         }

         if (var14.isEmpty() && this.state == OffroadHome.State.ALERTS) {
            if (var9) {
               var65 = OffroadHome.State.UPDATE;
            } else {
               var65 = OffroadHome.State.HOME;
            }

            this.state = (OffroadHome.State)var65;
         }

         this.prevUpdate = var9;
         this.prevAlerts = var14.isEmpty() ^ true;
         var65 = this.homeContent;
         if (this.state == OffroadHome.State.HOME) {
            var43 = (byte)0;
         } else {
            var43 = (byte)8;
         }

         var65.setVisibility(var43);
         var65 = this.detailContent;
         if (this.state == OffroadHome.State.HOME) {
            var43 = (byte)8;
         } else {
            var43 = (byte)0;
         }

         var65.setVisibility(var43);
         OffroadHome.State var71 = this.state;
         switch (OffroadHome.WhenMappings.$EnumSwitchMapping$0[var71.ordinal()]) {
            case 1:
               JSONObject var111 = this.jobj(var1.getParams().get("ApiCache_DriveStats"));
               JSONObject var108 = this.jobj(var1.getParams().get("StarPilotStats"));
               JSONObject var92;
               if (var111 != null) {
                  var92 = var111.optJSONObject("all");
               } else {
                  var92 = null;
               }

               JSONObject var76;
               if (var111 != null) {
                  var76 = var111.optJSONObject("week");
               } else {
                  var76 = null;
               }

               long var10 = 4609926660211357856L;
               if (var92 != null && this.num(var92, "routes") > 0.0) {
                  this.allTime.set((int)this.num(var92, "routes"), refresh$cloudDist(this, var2, 1.609344, var92), this.num(var92, "minutes") / 60.0, var2);
               } else if (var108 != null) {
                  OffroadHome.StatBlock var93 = this.allTime;
                  var43 = (int)this.num(var108, "StarPilotDrives");
                  double var5 = this.num(var108, "StarPilotMeters");
                  double var3;
                  if (var2) {
                     var3 = 1000.0;
                  } else {
                     var3 = 1609.344;
                  }

                  var93.set(var43, var5 / var3, this.num(var108, "StarPilotSeconds") / 3600.0, var2);
               } else {
                  this.allTime.set(0, 0.0, 0.0, var2);
               }

               OffroadHome.StatBlock var94 = this.pastWeek;
               var43 = (int)this.num(var76, "routes");
               var94.set(var43, refresh$cloudDist(this, var2, 1.609344, var76), this.num(var76, "minutes") / 60.0, var2);
               var13 = this.statsNote;
               String var109 = "";
               CharSequence var77;
               if (var111 != null) {
                  var77 = "";
               } else if (var1.getSp7()) {
                  var77 = "StarPilot 7 doesn't keep drive stats yet.";
               } else {
                  var77 = "Past week needs comma connect drive stats (the comma caches them when online).";
               }

               var13.setText(var77);
               var9 = var1.pBool("ExperimentalMode", false);
               var13 = this.expBtn;
               String var78;
               if (var9) {
                  var78 = "EXPERIMENTAL MODE ON";
               } else {
                  var78 = "CHILL MODE ON";
               }

               var13.setText(var78);
               TextView var79 = this.expBtn;
               if (var9) {
                  var43 = -1;
               } else {
                  var43 = Color.rgb(20, 30, 28);
               }

               var79.setTextColor(var43);
               var13 = this.expBtn;
               Orientation var112 = Orientation.LEFT_RIGHT;
               int[] var80;
               if (var9) {
                  var80 = new int[]{Color.rgb(255, 155, 63), Color.rgb(219, 56, 34)};
               } else {
                  var80 = new int[]{Color.rgb(20, 255, 171), Color.rgb(35, 149, 255)};
               }

               GradientDrawable var81 = new GradientDrawable(var112, var80);
               var81.setCornerRadius(this.dp(20));
               var13.setBackground((Drawable)var81);
               var43 = var1.pInt("LongitudinalPersonality", 1);

               for (TextView var98 : this.persBtns) {
                  var9 = Intrinsics.areEqual(var98.getTag(), var43);
                  int var8;
                  if (var9) {
                     var8 = Color.rgb(54, 77, 239);
                  } else {
                     var8 = Color.rgb(44, 48, 60);
                  }

                  var98.setBackground((Drawable)this.rounded(var8, 14));
                  if (var9) {
                     var8 = -1;
                  } else {
                     var8 = Color.rgb(190, 198, 214);
                  }

                  var98.setTextColor(var8);
               }

               var13 = this.devName;
               var65 = Telemetry.pStr$default(var1, "GalaxyDeviceName", null, 2, null);
               boolean var53;
               if (var65.length() == 0) {
                  var53 = 1;
               } else {
                  var53 = 0;
               }

               if (var53) {
                  var65 = "comma";
               }

               label344: {
                  var13.setText((CharSequence)var65);
                  var15 = Telemetry.Companion.chestnutWhy(Telemetry.pStr$default(var1, "ChestnutWhy", null, 2, null));
                  String var100 = Telemetry.pStr$default(var1, "ChestnutState", null, 2, null);
                  var53 = var100.hashCode();
                  var84 = "not detected";
                  switch (var53) {
                     case -1422950650:
                        if (var100.equals("active")) {
                           var84 = "active";
                           break label344;
                        }
                        break;
                     case -1381388741:
                        if (var100.equals("disconnected")) {
                           break label344;
                        }
                        break;
                     case -1281977283:
                        if (var100.equals("failed")) {
                           boolean var55;
                           if (var15.length() > 0) {
                              var55 = true;
                           } else {
                              var55 = false;
                           }

                           String var85;
                           if (var55) {
                              var85 = " - " + var15;
                           } else {
                              var85 = "";
                           }

                           var84 = "FAILED" + var85;
                           break label344;
                        }
                        break;
                     case 108386723:
                        if (var100.equals("ready")) {
                           var84 = "connected, model ready";
                           break label344;
                        }
                        break;
                     case 336650556:
                        if (var100.equals("loading")) {
                           var84 = "loading";
                           break label344;
                        }
                  }

                  if (var1.pBool("UsbGpuActive", false)) {
                     var84 = "active";
                  } else if (var1.getChestnutPresent() && var1.pBool("UsbGpuCompiled", false)) {
                     var84 = "connected, model ready";
                  } else if (var1.getChestnutPresent()) {
                     var84 = "connected";
                  }
               }

               var2 = var1.pBool("SshEnabled", false);
               String var17 = Telemetry.pStr$default(var1, "GitBranch", null, 2, null);
               TextView var114 = this.devLines;
               StringBuilder var16 = new StringBuilder();
               Object var101 = Telemetry.pStr$default(var1, "DongleId", null, 2, null);
               boolean var56;
               if (var101.length() == 0) {
                  var56 = 1;
               } else {
                  var56 = 0;
               }

               if (var56) {
                  var101 = "–";
               }

               var16.append("Dongle ID   " + var101 + "\n");
               if (var17.length() > 0) {
                  var56 = (boolean)1;
               } else {
                  var56 = (boolean)0;
               }

               if (var56) {
                  var16.append("Branch      " + var17 + "\n");
               }

               var16.append("eGPU        " + var84 + "\n");
               JSONObject var115 = this.storage;
               if (var115 != null) {
                  var101 = var115.optJSONObject("internal");
               } else {
                  var101 = null;
               }

               JSONObject var86;
               if (var115 != null) {
                  var86 = var115.optJSONObject("stick");
               } else {
                  var86 = null;
               }

               if (var101 != null) {
                  String var25 = this.gb((JSONObject)var101, "free_GB");
                  String var18 = this.gb((JSONObject)var101, "total_GB");
                  var16.append("Internal    " + var25 + " free of " + var18 + " GB");
                  if (var101.has("newest_time")) {
                     String var26 = this.whenStr(var101.optDouble("newest_time"));
                     var16.append("  ·  newest drive " + var26);
                  }

                  var16.append("\n");
               } else {
                  var56 = MathKt.roundToInt(var1.getFreeSpacePct());
                  var16.append("Storage     " + var56 + "% free\n");
               }

               if (var86 != null) {
                  var16.append("USB stick   ");
                  if (!var86.optBoolean("present")) {
                     var16.append("not connected");
                  } else if (var86.has("stick_free_GB")) {
                     String var27 = this.gb(var86, "stick_free_GB");
                     var101 = this.gb(var86, "stick_size_GB");
                     var16.append(var27 + " free of " + var101 + " GB");
                  } else {
                     var16.append("connected, no copy finished yet");
                  }

                  var16.append("\n");
                  StorageInfo.Copying var28 = StorageInfo.INSTANCE.copying(var115);
                  if (var28 != null) {
                     String var29 = var28.getLine();
                     var16.append("  copying now: " + var29 + "\n");
                  }

                  if (var86.optBoolean("present")) {
                     double var40 = var86.optDouble("last_copy_time", Double.NaN);
                     double var39 = var86.optDouble("run_time", Double.NaN);
                     String var30;
                     if (Double.isNaN(var40)) {
                        var30 = "–";
                     } else {
                        var101 = this.whenStr(var40);
                        String var31 = StorageInfo.INSTANCE.mb(var86.optInt("last_copy_MB"));
                        var30 = var101 + " (" + var31 + ")";
                     }

                     var16.append("  last copy " + var30);
                     if (var86.has("stick_newest_time")) {
                        String var32 = this.whenStr(var86.optDouble("stick_newest_time"));
                        var16.append("  ·  newest on stick " + var32);
                     }

                     var16.append("\n");
                     var17 = var86.optString("result");
                     String var33;
                     if (Double.isNaN(var39)) {
                        var33 = "never";
                     } else {
                        var33 = this.whenStr(var39);
                     }

                     Intrinsics.checkNotNull(var17);
                     boolean var59;
                     if (var17.length() > 0) {
                        var59 = true;
                     } else {
                        var59 = false;
                     }

                     var101 = var109;
                     if (var59) {
                        var101 = var109;
                        if (!Intrinsics.areEqual(var17, "ok")) {
                           var101 = "  ·  " + var17;
                        }
                     }

                     var16.append("  checked " + var33 + var101);
                     if (!var86.optBoolean("auto", true)) {
                        var16.append("  ·  auto copy OFF");
                     }

                     var16.append("\n");
                  }
               }

               String var34;
               if (var2) {
                  var34 = "on";
               } else {
                  var34 = "off";
               }

               var16.append("SSH         " + var34);
               String var35 = var16.toString();
               Intrinsics.checkNotNullExpressionValue(var35, "toString(...)");
               var114.setText(var35);
               Unit var36 = Unit.INSTANCE;
               break;
            case 2:
               this.detailTitle.setText("Update available");
               this.detailBody.removeAllViews();
               CharSequence var74 = Telemetry.pStr$default(var1, "UpdaterNewDescription", null, 2, null);
               LinearLayout var22 = var74;
               if (StringsKt.isBlank(var74)) {
                  var22 = "A new version is ready to install.";
               }

               String var75 = (String)var22;
               var22 = this.detailBody;
               var13 = tv$default(this, 22.0F, Color.rgb(210, 216, 228), false, 4, null);
               var13.setText(var75);
               var13.setLineSpacing(this.dp(6), 1.0F);
               var22.addView((View)var13);
               this.detailAction.setText("Open Software settings");
               this.detailAction.setVisibility(0);
               this.detailAction.setOnClickListener(new OffroadHome$$ExternalSyntheticLambda1(this));
               Unit var24 = Unit.INSTANCE;
               break;
            case 3:
               TextView var72 = this.detailTitle;
               String var19;
               if (var14.size() == 1) {
                  var19 = "1 alert";
               } else {
                  var43 = var14.size();
                  var19 = var43 + " alerts";
               }

               var72.setText(var19);
               this.detailBody.removeAllViews();

               for (OffroadHome.Alert var89 : var14) {
                  LinearLayout var20 = this.detailBody;
                  TextView var106 = tv$default(this, 21.0F, -1, false, 4, null);
                  var106.setText(var89.getText());
                  var106.setLineSpacing(this.dp(5), 1.0F);
                  var106.setPadding(this.dp(24), this.dp(20), this.dp(24), this.dp(20));
                  if (var89.getSeverity() > 0) {
                     var43 = Color.rgb(201, 34, 49);
                  } else {
                     var43 = Color.rgb(44, 48, 60);
                  }

                  var106.setBackground((Drawable)this.rounded(var43, 16));
                  View var110 = (View)var106;
                  android.widget.LinearLayout.LayoutParams var90 = new android.widget.LinearLayout.LayoutParams(-1, -2);
                  var90.bottomMargin = this.dp(12);
                  Unit var107 = Unit.INSTANCE;
                  var20.addView(var110, (LayoutParams)var90);
               }

               this.detailAction.setVisibility(8);
               Unit var21 = Unit.INSTANCE;
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }
      }
   }

   public final void setStorage(JSONObject var1) {
      this.storage = var1;
      Telemetry var2 = this.lastT;
      if (var2 != null) {
         this.refresh(var2, true);
      }
   }

   public interface Actions {
      void control(JSONObject var1);

      void openFiles();

      void openSettings(String var1);

      void openTerminal();
   }

   private static final class Alert {
      private final int severity;
      private final String text;

      public Alert(String var1, int var2) {
         Intrinsics.checkNotNullParameter(var1, "text");
         super();
         this.text = var1;
         this.severity = var2;
      }

      // $VF: synthetic method
      public static OffroadHome.Alert copy$default(OffroadHome.Alert var0, String var1, int var2, int var3, Object var4) {
         if ((var3 & 1) != 0) {
            var1 = var0.text;
         }

         if ((var3 & 2) != 0) {
            var2 = var0.severity;
         }

         return var0.copy(var1, var2);
      }

      public final String component1() {
         return this.text;
      }

      public final int component2() {
         return this.severity;
      }

      public final OffroadHome.Alert copy(String var1, int var2) {
         Intrinsics.checkNotNullParameter(var1, "text");
         return new OffroadHome.Alert(var1, var2);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof OffroadHome.Alert)) {
            return false;
         } else {
            var1 = var1;
            return !Intrinsics.areEqual(this.text, var1.text) ? false : this.severity == var1.severity;
         }
      }

      public final int getSeverity() {
         return this.severity;
      }

      public final String getText() {
         return this.text;
      }

      @Override
      public int hashCode() {
         return this.text.hashCode() * 31 + Integer.hashCode(this.severity);
      }

      @Override
      public String toString() {
         String var2 = this.text;
         int var1 = this.severity;
         return "Alert(text=" + var2 + ", severity=" + var1 + ")";
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }

   private final class StatBlock {
      private final TextView dist;
      private final TextView distUnit;
      private final TextView drives;
      private final TextView hours;
      final OffroadHome this$0;
      private final LinearLayout view;

      public StatBlock(String var1, int var2, int var3) {
         Intrinsics.checkNotNullParameter(var2, "title");
         this.this$0 = var1;
         super();
         this.drives = OffroadHome.tv$default(this.this$0, 44.0F, 0, true, 2, null);
         this.dist = OffroadHome.tv$default(this.this$0, 44.0F, 0, true, 2, null);
         this.hours = OffroadHome.tv$default(this.this$0, 44.0F, 0, true, 2, null);
         this.distUnit = OffroadHome.tv$default(this.this$0, 16.0F, Color.rgb(150, 160, 180), false, 4, null);
         LinearLayout var10 = new LinearLayout(this.this$0.getContext());
         OffroadHome var4 = this.this$0;
         var10.setOrientation(1);
         var10.setBackground((Drawable)var4.rounded(Color.rgb(34, 34, 40), 16));
         var10.setPadding(var4.dp(26), var4.dp(18), var4.dp(26), var4.dp(18));
         TextView var5 = var4.tv(15.0F, var3, true);
         var5.setText(var2);
         var5.setLetterSpacing(0.12F);
         var10.addView((View)var5);
         LinearLayout var11 = new LinearLayout(var10.getContext());
         var11.setOrientation(0);
         var11.setGravity(16);
         Pair[] var14 = new Pair[3];
         TextView var6 = this.drives;
         TextView var8 = OffroadHome.tv$default(var4, 16.0F, Color.rgb(150, 160, 180), false, 4, null);
         var8.setText("drives");
         Unit var7 = Unit.INSTANCE;
         var14[0] = TuplesKt.to(var6, var8);
         var14[1] = TuplesKt.to(this.dist, this.distUnit);
         var6 = this.hours;
         var8 = OffroadHome.tv$default(var4, 16.0F, Color.rgb(150, 160, 180), false, 4, null);
         var8.setText("hours");
         var7 = Unit.INSTANCE;
         var14[2] = TuplesKt.to(var6, var8);

         for (Pair var18 : CollectionsKt.listOf(var14)) {
            TextView var21 = (TextView)var18.component1();
            var8 = (TextView)var18.component2();
            LinearLayout var19 = new LinearLayout(var10.getContext());
            var19.setOrientation(1);
            var19.addView((View)var21);
            View var22 = (View)var8;
            android.widget.LinearLayout.LayoutParams var9 = new android.widget.LinearLayout.LayoutParams(-2, -2);
            var9.topMargin = var4.dp(6);
            Unit var25 = Unit.INSTANCE;
            var19.addView(var22, (LayoutParams)var9);
            var11.addView((View)var19, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
         }

         View var16 = (View)var11;
         android.widget.LinearLayout.LayoutParams var12 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
         var12.topMargin = var4.dp(10);
         Unit var13 = Unit.INSTANCE;
         var10.addView(var16, (LayoutParams)var12);
         this.view = var10;
      }

      public final TextView getDist() {
         return this.dist;
      }

      public final TextView getDistUnit() {
         return this.distUnit;
      }

      public final TextView getDrives() {
         return this.drives;
      }

      public final TextView getHours() {
         return this.hours;
      }

      public final LinearLayout getView() {
         return this.view;
      }

      public final void set(int var1, double var2, double var4, boolean var6) {
         TextView var7 = this.drives;
         String var8 = String.format("%,d", Arrays.copyOf(new Object[]{var1}, 1));
         Intrinsics.checkNotNullExpressionValue(var8, "format(...)");
         var7.setText(var8);
         this.dist.setText(this.this$0.fmt(var2));
         this.hours.setText(this.this$0.fmt(var4));
         TextView var10 = this.distUnit;
         String var9;
         if (var6) {
            var9 = "km";
         } else {
            var9 = "miles";
         }

         var10.setText(var9);
      }
   }

   private static enum State {
      ALERTS,
      HOME,
      UPDATE;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(OffroadHome.State.$VALUES);
      private static final OffroadHome.State[] $VALUES = $values();

      // $VF: synthetic method
      private static final OffroadHome.State[] $values() {
         return new OffroadHome.State[]{HOME, UPDATE, ALERTS};
      }

      public static EnumEntries<OffroadHome.State> getEntries() {
         return $ENTRIES;
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[OffroadHome.State.values().length];

         try {
            var0[OffroadHome.State.HOME.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[OffroadHome.State.UPDATE.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[OffroadHome.State.ALERTS.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
