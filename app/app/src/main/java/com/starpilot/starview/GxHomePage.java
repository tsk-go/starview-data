package com.starpilot.starview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxHomePage extends GxPage {
   private LinearLayout body;
   private Boolean devOnline;
   private String devStatus;
   private String error;
   private final List<Integer> favColors;
   private GxPage.Row hero;
   private boolean keepRefreshing;
   private JSONObject payload;
   private boolean refreshing;
   private String status;
   private String togglingKey;
   private String unit;

   // $VF: synthetic method
   public static Unit $r8$lambda$6SgqF9EA7r3E_vPzPR_rcylSsb4(GxHomePage var0) {
      return build$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7uDLdl_a_Ei40atsW5a5fl44muo/* $VF was: $r8$lambda$7uDLdl_a_Ei40atsW5a5fl44muo*/(GxHomePage var0) {
      return build$lambda$1(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LdTwMOrmmJw4DTAbu6EbIe_0M6U(GxHomePage var0, String var1, String var2, List var3) {
      return render$lambda$71$lambda$69(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$M1xh8hDCg4R5EJ6Ob_yghSWL8Jo(GxHomePage var0, Pair var1) {
      return cardHead$lambda$19(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MQqrA9YdRNAHH2h6g6n6jEnr0Ew(GxHomePage var0) {
      return render$lambda$27(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PnxtHdujnG0rytDQQ254be3rzJ4(GxHomePage var0, List var1, String var2) {
      return toggleDriveStats$lambda$90(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$T8p59cCSGt08rwV0tSU9lGTwI6w(GxHomePage var0, String var1, JSONObject var2) {
      return load$lambda$11(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cvLfSdI7Oc20iRJiXWrJKlZHZWM(GxHomePage var0) {
      return build$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pLjSVyqYEoPk4pB9xeFw_eC4vyQ(GxHomePage var0, String var1, JSONObject var2) {
      return refreshStatus$lambda$5(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zms_Uc_pleODFHNSrEG44tJC12U(GxHomePage var0, String var1, String var2, JSONObject var3) {
      return toggleDriveStats$go$lambda$89(var0, var1, var2, var3);
   }

   public GxHomePage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.status = "loading";
      this.error = "";
      this.unit = "miles";
      this.togglingKey = "";
      Iterable var5 = CollectionsKt.listOf(new String[]{"#5ec8c8", "#8b6cc5", "#d4a060", "#e05577", "#6cc56e", "#8aa3ff"});
      var var3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
      Iterator var6 = var5.iterator();

      while (var6.hasNext()) {
         var3.add(Color.parseColor((String)var6.next()));
      }

      List var4 = var3;
      this.favColors = var4;
   }

   private static final Unit build$lambda$1(GxHomePage var0) {
      var0.refresh();
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$2(GxHomePage var0) {
      if (var0.keepRefreshing) {
         var0.load();
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$3(GxHomePage var0) {
      var0.refreshStatus();
      return Unit.INSTANCE;
   }

   private final void cardHead(LinearLayout var1, String var2, Pair<String, String> var3) {
      LinearLayout var4 = new LinearLayout((Context)this.getAct());
      var4.setOrientation(0);
      var4.setGravity(16);
      var4.setPadding(0, 0, 0, this.getUi().dp(6));
      TextView var5 = new TextView((Context)this.getAct());
      var5.setText(var2);
      var5.setTextSize(21.0F);
      var5.setTextColor(CarUi.Companion.getTEXT());
      var5.setTypeface(Typeface.DEFAULT_BOLD);
      var4.addView((View)var5, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      if (var3 != null) {
         var4.addView((View)CarUi.button$default(this.getUi(), (String)var3.getFirst(), false, new GxHomePage$$ExternalSyntheticLambda0(this, var3), 2, null));
      }

      var1.addView((View)var4);
   }

   // $VF: synthetic method
   static void cardHead$default(GxHomePage var0, LinearLayout var1, String var2, Pair var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = null;
      }

      var0.cardHead(var1, var2, var3);
   }

   private static final Unit cardHead$lambda$19(GxHomePage var0, Pair var1) {
      var0.getGx().openPage((String)var1.getSecond());
      return Unit.INSTANCE;
   }

   private final List<LinearLayout> columns(int var1, ViewGroup var2) {
      LinearLayout var5 = new LinearLayout((Context)this.getAct());
      var5.setOrientation(0);
      var2.addView((View)var5, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
      Iterable var12 = RangesKt.until(0, var1);
      boolean var4 = false;
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var12, 10));
      Iterator var7 = var12.iterator();

      while (var7.hasNext()) {
         int var3 = ((IntIterator)var7).nextInt();
         LinearLayout var8 = new LinearLayout((Context)this.getAct());
         var8.setOrientation(1);
         View var11 = (View)var8;
         android.widget.LinearLayout.LayoutParams var10 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
         if (var3 < var1 - 1) {
            var10.rightMargin = this.getUi().dp(8);
         }

         Unit var9 = Unit.INSTANCE;
         var5.addView(var11, (LayoutParams)var10);
         var6.add(var8);
      }

      return var6;
   }

   // $VF: synthetic method
   static List columns$default(GxHomePage var0, int var1, ViewGroup var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var4 = var0.body;
         LinearLayout var5 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            var5 = null;
         }

         var2 = (ViewGroup)var5;
      }

      return var0.columns(var1, var2);
   }

   private final JSONObject dash() {
      JSONObject var1 = this.payload;
      if (var1 != null) {
         JSONObject var2 = var1.optJSONObject("dashboard");
         if (var2 != null) {
            return var2;
         }
      }

      return new JSONObject();
   }

   private final boolean driveReady(JSONObject var1) {
      Object var4 = var1.opt("ignored");
      boolean var3 = true;
      boolean var2 = var3;
      if (!Intrinsics.areEqual(var4, true)) {
         if (!Intrinsics.areEqual(var1.opt("attentionKnown"), false)) {
            var2 = var3;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   private final boolean hasPendingWork(JSONObject var1) {
      JSONObject var9 = var1.optJSONObject("analysis");
      JSONObject var8 = var9;
      if (var9 == null) {
         var8 = new JSONObject();
      }

      long var6;
      label41: {
         var6 = Math.max(0L, Math.round(GxHomeKt.access$gxHomeNum(var8.opt("pendingRoutes"))));
         JSONArray var10 = var1.optJSONArray("recentDrives");
         if (var10 != null) {
            List var14 = this.objects(var10);
            var11 = var14;
            if (var14 != null) {
               break label41;
            }
         }

         var11 = CollectionsKt.emptyList();
      }

      boolean var5 = GxHomeKt.access$gxHomeTruthy(var8.opt("running"));
      boolean var4 = true;
      boolean var3 = var4;
      if (!var5) {
         var3 = var4;
         if (var6 <= 0L) {
            var var12 = var11;
            boolean var2;
            if (var12 instanceof Collection && ((Collection)var12).isEmpty()) {
               var2 = false;
            } else {
               Iterator var13 = var12.iterator();

               while (true) {
                  if (!var13.hasNext()) {
                     var2 = false;
                     break;
                  }

                  if (!this.driveReady((JSONObject)var13.next())) {
                     var2 = true;
                     break;
                  }
               }
            }

            if (var2) {
               var3 = var4;
            } else {
               var3 = false;
            }
         }
      }

      return var3;
   }

   private final void kv(LinearLayout var1, String var2, String var3) {
      LinearLayout var4 = new LinearLayout((Context)this.getAct());
      var4.setOrientation(0);
      var4.setGravity(16);
      var4.setPadding(0, this.getUi().dp(8), 0, this.getUi().dp(8));
      TextView var5 = new TextView((Context)this.getAct());
      var5.setText(var2);
      var5.setTextSize(17.0F);
      var5.setTextColor(CarUi.Companion.getDIM());
      var4.addView((View)var5, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      TextView var6 = new TextView((Context)this.getAct());
      var6.setText(var3);
      var6.setTextSize(17.0F);
      var6.setTextColor(CarUi.Companion.getTEXT());
      var6.setTypeface(Typeface.DEFAULT_BOLD);
      var6.setGravity(8388613);
      var6.setPadding(this.getUi().dp(12), 0, 0, 0);
      var4.addView((View)var6);
      var1.addView((View)var4);
   }

   private final void load() {
      if (!this.refreshing) {
         this.refreshing = true;
         if (this.payload == null) {
            this.status = "loading";
         }

         this.error = "";
         this.getJson("/api/stats", new GxHomePage$$ExternalSyntheticLambda8(this));
      }
   }

   private static final Unit load$lambda$11(GxHomePage var0, String var1, JSONObject var2) {
      var0.refreshing = false;
      if (var1 == null && var2 != null) {
         label53: {
            JSONObject var6 = var2.optJSONObject("dashboard");
            Object var4 = null;
            Object var3 = null;
            if (var6 != null) {
               JSONObject var7 = var6.optJSONObject("week");
               if (var7 != null) {
                  var1 = GxPage.str$default(var0, var7, "distanceUnit", null, 2, null);
                  if (var1 != null) {
                     CharSequence var5 = var1;
                     CharSequence var9 = var5;
                     if (StringsKt.isBlank(var5)) {
                        var9 = null;
                     }

                     var1 = (String)var9;
                     if (var1 != null) {
                        break label53;
                     }
                  }
               }
            }

            JSONObject var16 = var2.optJSONObject("driveStats");
            var1 = (String)var4;
            if (var16 != null) {
               var16 = var16.optJSONObject("all");
               var1 = (String)var4;
               if (var16 != null) {
                  String var18 = GxPage.str$default(var0, var16, "unit", null, 2, null);
                  var1 = (String)var4;
                  if (var18 != null) {
                     var1 = var18;
                     if (StringsKt.isBlank(var1)) {
                        var1 = (String)var3;
                     }

                     var1 = var1;
                  }
               }
            }
         }

         var0.payload = var2;
         if (var1 == null) {
            CharSequence var15 = var0.unit;
            Object var12 = var15;
            if (StringsKt.isBlank(var15)) {
               var12 = "miles";
            }

            var1 = (String)var12;
         }

         var0.unit = var1;
         var0.status = "ready";
         var2 = var2.optJSONObject("dashboard");
         JSONObject var13 = var2;
         if (var2 == null) {
            var13 = new JSONObject();
         }

         var0.keepRefreshing = var0.hasPendingWork(var13);
      } else if (var0.payload != null) {
         var0.toast("Couldn't refresh dashboard.");
      } else {
         var0.status = "error";
         if (var1 == null) {
            var1 = "empty stats payload";
         }

         var0.error = var1;
      }

      var0.render();
      return Unit.INSTANCE;
   }

   private final void metrics(LinearLayout var1, List<Pair<String, String>> var2) {
      LinearLayout var3 = new LinearLayout((Context)this.getAct());
      var3.setOrientation(0);
      var3.setPadding(0, this.getUi().dp(6), 0, this.getUi().dp(6));

      for (Pair var5 : var2) {
         String var4 = (String)var5.component1();
         String var6 = (String)var5.component2();
         LinearLayout var10 = new LinearLayout((Context)this.getAct());
         var10.setOrientation(1);
         TextView var7 = new TextView((Context)this.getAct());
         var7.setText(var4);
         var7.setTextSize(26.0F);
         var7.setTextColor(CarUi.Companion.getTEXT());
         var7.setTypeface(Typeface.DEFAULT_BOLD);
         var10.addView((View)var7);
         TextView var9 = new TextView((Context)this.getAct());
         var9.setText(var6);
         var9.setTextSize(14.0F);
         var9.setTextColor(CarUi.Companion.getDIM());
         var10.addView((View)var9);
         var3.addView((View)var10, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      }

      var1.addView((View)var3);
   }

   private final void paintHero() {
      JSONObject var4 = this.payload;
      Object var5 = null;
      GxPage.Row var3 = null;
      if (var4 == null) {
         GxPage.Row var14 = this.hero;
         if (var14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hero");
         } else {
            var3 = var14;
         }

         String var15;
         if (Intrinsics.areEqual(this.status, "error")) {
            var15 = "Failed to load dashboard";
         } else {
            var15 = "Loading dashboard...";
         }

         var3.setDesc(var15);
      } else {
         JSONObject var7 = this.dash().optJSONObject("device");
         var4 = var7;
         if (var7 == null) {
            var4 = new JSONObject();
         }

         Boolean var8 = this.devOnline;
         boolean var2 = false;
         boolean var1 = var2;
         if (!Intrinsics.areEqual(var8, false)) {
            var1 = var2;
            if (!Intrinsics.areEqual(var4.opt("online"), false)) {
               var1 = true;
            }
         }

         String var6 = this.devStatus;
         String var9 = var6;
         if (var6 == null) {
            CharSequence var12 = GxPage.str$default(this, var4, "status", null, 2, null);
            Object var10 = var12;
            if (StringsKt.isBlank(var12)) {
               var10 = "Parked";
            }

            var9 = (String)var10;
         }

         GxPage.Row var13 = this.hero;
         if (var13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hero");
            var13 = (GxPage.Row)var5;
         }

         if (var1) {
            var5 = "device online";
         } else {
            var5 = "device offline";
         }

         var13.setDesc(var9 + " - " + var5);
      }
   }

   private final void refresh() {
      String var2 = this.status;
      String var1 = "loading";
      if (!Intrinsics.areEqual(var2, "loading")) {
         if (this.payload != null) {
            var1 = "ready";
         }

         this.status = var1;
         this.error = "";
         this.load();
      }
   }

   private final void refreshStatus() {
      this.getJson("/api/device/status", new GxHomePage$$ExternalSyntheticLambda1(this));
   }

   private static final Unit refreshStatus$lambda$5(GxHomePage var0, String var1, JSONObject var2) {
      if (var1 == null && var2 != null) {
         var0.devOnline = true;
         Object var3 = "Parked";
         CharSequence var4 = var0.str(var2, "status", "Parked");
         if (!StringsKt.isBlank(var4)) {
            var3 = var4;
         }

         var0.devStatus = (String)var3;
      } else {
         var0.devOnline = false;
      }

      var0.paintHero();
      return Unit.INSTANCE;
   }

   private final void render() {
      this.paintHero();
      LinearLayout var17 = this.body;
      String var30 = "body";
      LinearLayout var16 = var17;
      if (var17 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("body");
         var16 = null;
      }

      var16.removeAllViews();
      if (Intrinsics.areEqual(this.status, "loading") && this.payload == null) {
         var16 = this.body;
         if (var16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            var16 = null;
         }

         var16.addView((View)this.empty("Loading dashboard..."));
      } else if (Intrinsics.areEqual(this.status, "error") && this.payload == null) {
         var16 = this.body;
         if (var16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            var16 = null;
         }

         LinearLayout var188 = this.card((ViewGroup)var16);
         this.text("Failed to load dashboard", (ViewGroup)var188, 21.0F, CarUi.Companion.getWARN(), true);
         GxPage.text$default(this, this.error, (ViewGroup)var188, 0.0F, 0, false, 28, null);
         GxPage var131 = this;
         Pair var97 = TuplesKt.to("Refresh", new GxHomePage$$ExternalSyntheticLambda5(this));
         ViewGroup var189 = (ViewGroup)var188;
         GxPage.buttons$default(var131, new Pair[]{var97}, var189, false, 4, null);
      } else {
         JSONObject var18;
         JSONObject var31;
         label560: {
            var31 = this.dash();
            JSONObject var61 = this.payload;
            if (var61 != null) {
               JSONObject var62 = var61.optJSONObject("driveStats");
               var18 = var62;
               if (var62 != null) {
                  break label560;
               }
            }

            var18 = new JSONObject();
         }

         label555: {
            JSONObject var63 = this.payload;
            if (var63 != null) {
               JSONObject var64 = var63.optJSONObject("softwareInfo");
               var99 = var64;
               if (var64 != null) {
                  break label555;
               }
            }

            var99 = new JSONObject();
         }

         JSONObject var65 = var31.optJSONObject("device");
         JSONObject var19 = var65;
         if (var65 == null) {
            var19 = new JSONObject();
         }

         JSONObject var66 = var31.optJSONObject("week");
         JSONObject var22 = var66;
         if (var66 == null) {
            var22 = new JSONObject();
         }

         JSONObject var20 = var31.optJSONObject("storage");
         JSONObject var67 = var20;
         if (var20 == null) {
            var67 = new JSONObject();
         }

         GxHomePage var24 = this;
         JSONObject var21 = var31.optJSONObject("analysis");
         var20 = var21;
         if (var21 == null) {
            var20 = new JSONObject();
         }

         int var9;
         label544: {
            long var14 = Math.round(GxHomeKt.access$gxHomeNum(var20.opt("pendingRoutes")));
            var9 = (int)Math.max(0L, var14);
            JSONArray var132 = var31.optJSONArray("recentDrives");
            if (var132 != null) {
               List var23 = var24.objects(var132);
               var133 = var23;
               if (var23 != null) {
                  break label544;
               }
            }

            var133 = CollectionsKt.emptyList();
         }

         var var134 = var133;
         int var8;
         if (var134 instanceof Collection && ((Collection)var134).isEmpty()) {
            var8 = 0;
         } else {
            var8 = 0;
            Iterator var135 = var134.iterator();

            while (var135.hasNext()) {
               if (!var24.driveReady((JSONObject)var135.next())) {
                  if (++var8 < 0) {
                     CollectionsKt.throwCountOverflow();
                  }
               }
            }
         }

         var9 = Math.max(var9, var8);
         boolean var11 = GxHomeKt.access$gxHomeTruthy(var20.opt("running"));
         String var25 = "drives";
         if (var11 || var9 > 0) {
            if (var9 != 0) {
               var8 = var9;
            } else {
               var8 = (int)Math.max(1L, Math.round(GxHomeKt.access$gxHomeNum(var20.opt("batchSize"))));
            }

            StringBuilder var137;
            if (var11) {
               String var136;
               if (var8 == 1) {
                  var136 = "drive";
               } else {
                  var136 = "drives";
               }

               var137 = new StringBuilder().append("Analyzing ").append(var8).append(" ").append(var136);
            } else {
               String var138;
               if (var9 == 1) {
                  var138 = "drive";
               } else {
                  var138 = "drives";
               }

               var137 = new StringBuilder().append(var9).append(" ").append(var138).append(" queued");
            }

            String var206 = var137.toString();
            LinearLayout var192 = var24.body;
            LinearLayout var139 = var192;
            if (var192 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("body");
               var139 = null;
            }

            var24.note(var206, (ViewGroup)var139).setTextColor(CarUi.Companion.getACCENT());
         }

         Unit var140 = Unit.INSTANCE;
         GxHomePage var28 = this;
         var18 = var31.optJSONObject("lastDrive");
         JSONObject var207 = var18;
         if (var18 == null) {
            var207 = new JSONObject();
         }

         var11 = var28.driveReady(var207);
         CharSequence var193 = GxPage.str$default(var28, var207, "distanceUnit", null, 2, null);
         var18 = var193;
         if (StringsKt.isBlank(var193)) {
            var18 = var28.unit;
         }

         String var213 = (String)var18;
         CharSequence var194 = GxPage.str$default(var28, var207, "speedUnit", null, 2, null);
         var18 = var194;
         if (StringsKt.isBlank(var194)) {
            if (Intrinsics.areEqual(var213, "kilometers")) {
               var18 = "kph";
            } else {
               var18 = "mph";
            }
         }

         String var33 = (String)var18;
         LinearLayout var195 = var28.body;
         LinearLayout var144 = var195;
         if (var195 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            var144 = null;
         }

         LinearLayout var29 = var28.card((ViewGroup)var144);
         cardHead$default(var28, var29, "Last drive", null, 4, null);
         GxPage.text$default(
            var28,
            GxHomeKt.access$gxHomeRange(GxPage.str$default(var28, var207, "date", null, 2, null), GxPage.str$default(var28, var207, "endDate", null, 2, null)),
            (ViewGroup)var29,
            18.0F,
            CarUi.Companion.getTEXT(),
            false,
            16,
            null
         );
         String var27 = "...";
         String var32 = "distance";
         String var145;
         if (var11) {
            var145 = GxHomeKt.access$gxHomeDec(var207.opt("distance"));
         } else {
            var145 = "...";
         }

         if (!var11) {
            var213 = "analyzing";
         }

         var20 = var99;
         Pair var34 = TuplesKt.to(var145, var213);
         String var26 = "duration";
         Pair var214 = TuplesKt.to(GxHomeKt.access$gxHomeDuration(var207.opt("duration")), "duration");
         String var100;
         if (var11) {
            var100 = GxHomeKt.access$gxHomeInt(var207.opt("avgSpeed"));
         } else {
            var100 = "...";
         }

         String var146;
         if (var11) {
            var146 = var33 + " avg";
         } else {
            var146 = "speed";
         }

         Pair var147 = TuplesKt.to(var100, var146);
         var33 = "engagedPercent";
         String var101 = var27;
         if (var11) {
            var101 = GxHomeKt.access$gxHomePct(var207.opt("engagedPercent"));
         }

         Pair var102 = TuplesKt.to(var101, "engaged");
         var28.metrics(var29, CollectionsKt.listOf(new Pair[]{var34, var214, var147, var102}));
         CharSequence var148 = GxPage.str$default(var28, var207, "model", null, 2, null);
         var17 = var148;
         if (StringsKt.isBlank(var148)) {
            var17 = "Unknown model";
         }

         List var149 = CollectionsKt.mutableListOf(new String[]{var17});
         String var106;
         if (var11) {
            String var104 = GxHomeKt.access$gxHomeInt(var207.opt("distractedMoments"));
            var149.add(var104 + " distracted");
            String var105 = GxHomeKt.access$gxHomeInt(var207.opt("unresponsiveMoments"));
            var106 = var105 + " unresponsive";
         } else {
            var106 = "Analyzing stats";
         }

         var149.add(var106);
         GxPage.text$default(
            var28, CollectionsKt.joinToString$default(var149, "   ·   ", null, null, 0, null, null, 62, null), (ViewGroup)var29, 0.0F, 0, false, 28, null
         );
         LinearLayout var150 = this.body;
         var17 = var150;
         if (var150 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            var17 = null;
         }

         this.header("Your driving", (ViewGroup)var17);
         GxHomePage var240 = this;
         var11 = false;
         List var151 = columns$default(var240, 3, null, 2, null);
         Iterator var248 = CollectionsKt.listOf(
               new Pair[]{TuplesKt.to("All time", "all"), TuplesKt.to("Past week", "week"), TuplesKt.to("StarPilot", "starpilot")}
            )
            .iterator();
         var8 = 0;

         for (JSONObject var108 = var18; var248.hasNext(); var8++) {
            Pair var215 = (Pair)var248.next();
            var207 = var108.optJSONObject((String)var215.getSecond());
            JSONObject var68 = var207;
            if (var207 == null) {
               var68 = new JSONObject();
            }

            var29 = var240.card((ViewGroup)var151.get(var8));
            cardHead$default(var240, var29, (String)var215.getFirst(), null, 4, null);
            var34 = TuplesKt.to(GxHomeKt.access$gxHomeInt(var68.opt("drives")), "drives");
            String var35 = GxHomeKt.access$gxHomeDec(var68.opt("distance"));
            CharSequence var216 = GxPage.str$default(var240, var68, "unit", null, 2, null);
            var207 = var216;
            if (StringsKt.isBlank(var216)) {
               var207 = var240.unit;
            }

            var240.metrics(
               var29, CollectionsKt.listOf(new Pair[]{var34, TuplesKt.to(var35, var207), TuplesKt.to(GxHomeKt.access$gxHomeDec(var68.opt("hours")), "hours")})
            );
         }

         Unit var69 = Unit.INSTANCE;
         GxHomePage var294 = this;
         var11 = false;
         List var241 = columns$default(var294, 2, null, 2, null);
         LinearLayout var210 = var294.card((ViewGroup)var241.get(0));
         cardHead$default(var294, var210, "This week", null, 4, null);
         GxPage var70 = var294;
         String var249 = " ";
         CharSequence var109 = GxPage.str$default(var70, var22, "distanceUnit", null, 2, null);
         Iterable var71 = var109;
         if (StringsKt.isBlank(var109)) {
            var71 = var294.unit;
         }

         label517: {
            var71 = (String)var71;
            String var110 = GxHomeKt.access$gxHomePct(var22.opt("engagedPercent"));
            String var111 = var110 + " engaged";
            var294.text(var111, (ViewGroup)var210, 24.0F, CarUi.Companion.getACCENT(), true);
            var294.setProgress(var294.progress((ViewGroup)var210), GxHomeKt.gxHomeClamp$default(var22.opt("engagedPercent"), 0.0, 0.0, 6, null));
            var294.metrics(
               var210,
               CollectionsKt.listOf(
                  new Pair[]{
                     TuplesKt.to(GxHomeKt.access$gxHomeDec(var22.opt("distance")), var71),
                     TuplesKt.to(GxHomeKt.access$gxHomeDec(var22.opt("hours")), "hours"),
                     TuplesKt.to(GxHomeKt.access$gxHomeInt(var22.opt("drives")), "drives")
                  }
               )
            );
            var71 = var22.optJSONArray("dailyDistance");
            if (var71 != null) {
               var71 = var294.objects((JSONArray)var71);
               if (var71 != null && !((Collection)var71).isEmpty()) {
                  break label517;
               }
            }

            var71 = null;
         }

         List var154;
         if (var71 != null) {
            Iterable var152 = (Iterable)var71;
            var var112 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var152, 10));

            for (JSONObject var201 : var152) {
               var112.add(TuplesKt.to(GxPage.str$default(var294, var201, "label", null, 2, null), GxHomeKt.access$gxHomeNum(var201.opt("distance"))));
            }

            var154 = var112;
         } else {
            var71 = CollectionsKt.listOf(new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"});
            var11 = false;
            var var113 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var71, 10));
            Iterator var155 = var71.iterator();

            while (var155.hasNext()) {
               var113.add(TuplesKt.to((String)var155.next(), 0.0));
            }

            var154 = var113;
         }

         Iterator var76 = var154.iterator();
         Double var77;
         if (!var76.hasNext()) {
            var77 = null;
         } else {
            double var1 = ((Number)((Pair)var76.next()).getSecond()).doubleValue();

            while (var76.hasNext()) {
               var1 = Math.max(var1, ((Number)((Pair)var76.next()).getSecond()).doubleValue());
            }

            var77 = var1;
         }

         double var40;
         if (var77 != null) {
            var40 = var77;
         } else {
            var40 = 1.0;
         }

         var40 = Math.max(1.0, var40);
         LinearLayout var202 = new LinearLayout((Context)var294.getAct());
         var202.setOrientation(0);
         var202.setGravity(80);
         var202.setPadding(0, var294.getUi().dp(8), 0, 0);
         var8 = var294.getUi().dp(110);
         Iterator var217 = var154.iterator();
         String var78 = var25;

         while (var217.hasNext()) {
            Pair var236 = (Pair)var217.next();
            var25 = (String)var236.component1();
            double var3 = ((Number)var236.component2()).doubleValue();
            LinearLayout var237 = new LinearLayout((Context)var294.getAct());
            var237.setOrientation(1);
            var237.setGravity(81);
            TextView var252 = new TextView((Context)var294.getAct());
            var252.setText(GxHomeKt.access$gxHomeDec(var3));
            var252.setTextSize(12.0F);
            var252.setTextColor(CarUi.Companion.getDIM());
            var252.setGravity(17);
            var237.addView((View)var252);
            double var5 = Math.max(4.0, var3 / var40 * 100) / 100.0;
            var3 = var8;
            View var296 = new View((Context)var294.getAct());
            var296.setBackground((Drawable)CarUi.rounded$default(var294.getUi(), CarUi.Companion.getACCENT(), 4, null, 4, null));
            android.widget.LinearLayout.LayoutParams var253 = new android.widget.LinearLayout.LayoutParams(var294.getUi().dp(26), (int)(var5 * var3));
            var253.topMargin = var294.getUi().dp(2);
            Unit var36 = Unit.INSTANCE;
            var237.addView(var296, (LayoutParams)var253);
            TextView var254 = new TextView((Context)var294.getAct());
            var254.setText(var25);
            var254.setTextSize(14.0F);
            var254.setTextColor(CarUi.Companion.getTEXT());
            var254.setGravity(17);
            var254.setPadding(0, var294.getUi().dp(4), 0, 0);
            var237.addView((View)var254);
            var202.addView((View)var237, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, var294.getUi().dp(48) + var8, 1.0F)));
         }

         var26 = var26;
         var210.addView((View)var202);
         LinearLayout var297 = var294.card((ViewGroup)var241.get(1));
         cardHead$default(var294, var297, "Personal records", null, 4, null);
         var18 = var31.optJSONObject("records");
         JSONObject var115 = var18;
         if (var18 == null) {
            var115 = new JSONObject();
         }

         Pair var157 = TuplesKt.to("Longest drive", "longestDrive");
         Pair var255 = TuplesKt.to("Most-engaged day", "mostEngagedDay");
         Pair var218 = TuplesKt.to("Best week", "bestWeek");
         Pair var303 = TuplesKt.to("Highest streak", "highestStreak");
         Pair var242 = TuplesKt.to("Longest undistracted drive", "longestUndistractedDrive");
         Iterator var158 = CollectionsKt.listOf(new Pair[]{var157, var255, var218, var303, var242, TuplesKt.to("Clean-drive streak", "cleanDriveStreak")})
            .iterator();
         JSONObject var219 = var115;

         while (var158.hasNext()) {
            label480: {
               Pair var226 = (Pair)var158.next();
               var304 = (String)var226.component1();
               var256 = var219.optJSONObject((String)var226.component2());
               if (var256 != null) {
                  var25 = (String)var256.opt("value");
                  if (var25 != null) {
                     if (Intrinsics.areEqual(var25, JSONObject.NULL)) {
                        var25 = null;
                     }

                     if (var25 != null) {
                        var27 = var25.toString();
                        var25 = var27;
                        if (var27 != null) {
                           break label480;
                        }
                     }
                  }
               }

               var25 = "0";
            }

            label472: {
               if (var256 != null) {
                  var29 = (LinearLayout)var256.opt("detail");
                  if (var29 != null) {
                     var27 = var29;
                     if (Intrinsics.areEqual(var29, JSONObject.NULL)) {
                        var27 = null;
                     }

                     if (var27 != null) {
                        String var258 = var27.toString();
                        var27 = var258;
                        if (var258 != null) {
                           break label472;
                        }
                     }
                  }
               }

               var27 = "";
            }

            var29 = new LinearLayout((Context)var294.getAct());
            var29.setOrientation(0);
            var29.setGravity(16);
            var29.setPadding(0, var294.getUi().dp(7), 0, var294.getUi().dp(7));
            LinearLayout var37 = new LinearLayout((Context)var294.getAct());
            var37.setOrientation(1);
            TextView var38 = new TextView((Context)var294.getAct());
            var38.setText(var304);
            var38.setTextSize(15.0F);
            var38.setTextColor(CarUi.Companion.getDIM());
            var37.addView((View)var38);
            TextView var305 = new TextView((Context)var294.getAct());
            var305.setText(var25);
            var305.setTextSize(19.0F);
            var305.setTextColor(CarUi.Companion.getTEXT());
            var305.setTypeface(Typeface.DEFAULT_BOLD);
            var37.addView((View)var305);
            var29.addView((View)var37, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
            TextView var229 = new TextView((Context)var294.getAct());
            var229.setText(var27);
            var229.setTextSize(14.0F);
            var229.setTextColor(CarUi.Companion.getDIM());
            var229.setGravity(8388613);
            var229.setPadding(var294.getUi().dp(10), 0, 0, 0);
            var29.addView((View)var229);
            var297.addView((View)var29);
         }

         Unit var116 = Unit.INSTANCE;
         GxHomePage var295 = this;
         var11 = false;
         LinearLayout var159 = var295.body;
         var17 = var159;
         if (var159 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            var17 = null;
         }

         label461: {
            var29 = var295.card((ViewGroup)var17);
            cardHead$default(var295, var29, "Recent drives", null, 4, null);
            JSONArray var118 = var31.optJSONArray("recentDrives");
            if (var118 != null) {
               List var160 = var295.objects(var118);
               var119 = var160;
               if (var160 != null) {
                  break label461;
               }
            }

            var119 = CollectionsKt.emptyList();
         }

         if (var119.isEmpty()) {
            GxPage.text$default(var295, "No local drives found yet.", (ViewGroup)var29, 17.0F, 0, false, 24, null);
         }

         Iterator var230 = var119.iterator();
         var8 = 0;
         String var211 = var249;
         String var220 = var33;
         var27 = var32;

         while (var230.hasNext()) {
            int var10 = var8 + 1;
            JSONObject var291 = (JSONObject)var230.next();
            boolean var13 = Intrinsics.areEqual(var291.opt("ignored"), true);
            boolean var12 = var295.driveReady(var291);
            boolean var53;
            if (!var12 && !var13) {
               var53 = true;
            } else {
               var53 = false;
            }

            var var161 = GxPage.str$default(var295, var291, "distanceUnit", null, 2, null);
            if (StringsKt.isBlank(var161)) {
               var161 = var295.unit;
            }

            String var273 = var161;
            JSONArray var264 = var291.optJSONArray("routeNames");
            List var268;
            if (var264 == null) {
               var268 = CollectionsKt.emptyList();
            } else {
               Iterable var162 = RangesKt.until(0, var264.length());
               var var280 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var162, 10));
               Iterator var298 = var162.iterator();

               while (var298.hasNext()) {
                  var280.add(var264.opt(((IntIterator)var298).nextInt()));
               }

               List var163 = var280;
               Iterable var164 = var163;
               var var281 = new ArrayList();

               for (Object var299 : var164) {
                  if (GxHomeKt.access$gxHomeTruthy(var299)) {
                     var281.add(var299);
                  }
               }

               List var165 = var281;
               Iterable var266 = var165;
               var var166 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var266, 10));
               Iterator var267 = var266.iterator();

               while (var267.hasNext()) {
                  var166.add(var267.next().toString());
               }

               var268 = var166;
            }

            label453: {
               String var167;
               if (var13) {
                  var167 = "Stats excluded";
               } else {
                  if (!var53) {
                     String var168 = GxHomeKt.access$gxHomeDec(var291.opt(var27));
                     var274 = var168 + var211 + var273;
                     break label453;
                  }

                  var167 = "Analyzing stats";
               }

               var274 = var167;
            }

            Pair var169;
            if (var13) {
               var169 = TuplesKt.to("Excluded", CollectionsKt.listOf("Ignored from stats"));
            } else if (var53) {
               var169 = TuplesKt.to("Pending", CollectionsKt.listOf("Waiting for full route analysis"));
            } else {
               String var170 = GxHomeKt.access$gxHomePct(var291.opt(var220));
               String var171 = var170 + " engaged";
               var32 = GxHomeKt.access$gxHomeInt(var291.opt("distractedMoments"));
               var32 = var32 + " distracted";
               String var300 = GxHomeKt.access$gxHomeInt(var291.opt("unresponsiveMoments"));
               var169 = TuplesKt.to(var171, CollectionsKt.listOf(new String[]{var32, var300 + " unresponsive"}));
            }

            String var306 = (String)var169.component1();
            List var307 = (List)var169.component2();
            if (var12 && !var13) {
               var40 = GxHomeKt.gxHomeClamp$default(var291.opt(var220), 0.0, 0.0, 6, null);
            } else {
               var40 = 0.0;
            }

            if (var8 > 0) {
               View var284 = new View((Context)var295.getAct());
               var284.setBackgroundColor(Color.rgb(40, 44, 52));
               android.widget.LinearLayout.LayoutParams var301 = new android.widget.LinearLayout.LayoutParams(-1, var295.getUi().dp(1));
               var301.topMargin = var295.getUi().dp(6);
               var301.bottomMargin = var295.getUi().dp(6);
               Unit var172 = Unit.INSTANCE;
               var29.addView(var284, (LayoutParams)var301);
            }

            var297 = new LinearLayout((Context)var295.getAct());
            var297.setOrientation(0);
            var297.setGravity(16);
            var297.setPadding(0, var295.getUi().dp(6), 0, var295.getUi().dp(6));
            LinearLayout var308 = new LinearLayout((Context)var295.getAct());
            var308.setOrientation(1);
            float var7;
            if (!var13 && !var53) {
               var7 = 1.0F;
            } else {
               var7 = 0.7F;
            }

            var308.setAlpha(var7);
            TextView var173 = new TextView((Context)var295.getAct());
            var173.setText(
               GxHomeKt.access$gxHomeRange(
                  GxPage.str$default(var295, var291, "date", null, 2, null), GxPage.str$default(var295, var291, "endDate", null, 2, null)
               )
            );
            var173.setTextSize(18.0F);
            var173.setTextColor(CarUi.Companion.getTEXT());
            var173.setTypeface(Typeface.DEFAULT_BOLD);
            var308.addView((View)var173);
            TextView var39 = new TextView((Context)var295.getAct());
            CharSequence var285 = GxPage.str$default(var295, var291, "model", null, 2, null);
            var18 = var285;
            if (StringsKt.isBlank(var285)) {
               var18 = "Unknown model";
            }

            var39.setText((CharSequence)var18);
            var39.setTextSize(15.0F);
            var39.setTextColor(CarUi.Companion.getDIM());
            var308.addView((View)var39);
            TextView var175 = new TextView((Context)var295.getAct());
            var32 = GxHomeKt.access$gxHomeDuration(var291.opt(var26));
            var175.setText(var274 + "   ·   " + var32);
            var175.setTextSize(16.0F);
            var175.setTextColor(CarUi.Companion.getTEXT());
            var175.setPadding(0, var295.getUi().dp(4), 0, 0);
            var308.addView((View)var175);
            var295.setProgress(var295.progress((ViewGroup)var308), var40);
            TextView var176 = new TextView((Context)var295.getAct());
            var176.setText(
               CollectionsKt.joinToString$default(CollectionsKt.plus(CollectionsKt.listOf(var306), var307), "   ·   ", null, null, 0, null, null, 62, null)
            );
            var176.setTextSize(15.0F);
            if (!var53 && !var13) {
               var8 = CarUi.Companion.getTEXT();
            } else {
               var8 = CarUi.Companion.getDIM();
            }

            var176.setTextColor(var8);
            var308.addView((View)var176);
            var297.addView((View)var308, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
            if (!var268.isEmpty()) {
               var32 = CollectionsKt.joinToString$default(var268, ",", null, null, 0, null, null, 62, null);
               String var177;
               if (var13) {
                  var177 = "include";
               } else {
                  var177 = "ignore";
               }

               CarUi var292 = var295.getUi();
               String var275;
               if (var13) {
                  var275 = "Include drive stats";
               } else {
                  var275 = "Ignore drive stats";
               }

               TextView var178 = CarUi.button$default(var292, var275, false, new GxHomePage$$ExternalSyntheticLambda6(var295, var32, var177, var268), 2, null);
               if (Intrinsics.areEqual(var295.togglingKey, var32)) {
                  var178.setAlpha(0.45F);
               }

               View var269 = (View)var178;
               android.widget.LinearLayout.LayoutParams var179 = new android.widget.LinearLayout.LayoutParams(-2, -2);
               var179.leftMargin = var295.getUi().dp(12);
               Unit var276 = Unit.INSTANCE;
               var297.addView(var269, (LayoutParams)var179);
            }

            var29.addView((View)var297);
            var8 = var10;
         }

         label399: {
            Unit var180 = Unit.INSTANCE;
            var239 = this;
            var11 = false;
            var247 = columns$default(var239, 2, null, 2, null);
            var250 = var239.card((ViewGroup)var247.get(0));
            var239.cardHead(var250, "Most used models", TuplesKt.to("Models", "gx:models"));
            JSONArray var181 = var31.optJSONArray("favoriteModels");
            if (var181 != null) {
               List var221 = var239.objects(var181);
               var182 = var221;
               if (var221 != null) {
                  break label399;
               }
            }

            var182 = CollectionsKt.emptyList();
         }

         List var222 = CollectionsKt.take(var182, 3);
         if (var222.isEmpty()) {
            GxPage.text$default(var239, "No model usage recorded yet.", (ViewGroup)var250, 17.0F, 0, false, 24, null);
         } else {
            LinearLayout var231 = new LinearLayout((Context)var239.getAct());
            var231.setOrientation(0);
            Iterator var183 = var222.iterator();

            for (int var50 = 0; var183.hasNext(); var50++) {
               JSONObject var261 = (JSONObject)var183.next();
               View var270 = new View((Context)var239.getAct());
               var270.setBackgroundColor(var239.favColors.get(var50).intValue());
               var231.addView(
                  var270,
                  (LayoutParams)(
                     new android.widget.LinearLayout.LayoutParams(
                        0, var239.getUi().dp(18), (float)Math.max(1.0, GxHomeKt.access$gxHomeNum(var261.opt("weight")))
                     )
                  )
               );
            }

            View var262 = (View)var231;
            android.widget.LinearLayout.LayoutParams var184 = new android.widget.LinearLayout.LayoutParams(-1, var239.getUi().dp(18));
            var184.topMargin = var239.getUi().dp(4);
            var184.bottomMargin = var239.getUi().dp(8);
            Unit var232 = Unit.INSTANCE;
            var250.addView(var262, (LayoutParams)var184);
            Iterator var233 = var222.iterator();
            var8 = 0;

            for (Iterator var223 = var233; var223.hasNext(); var8++) {
               JSONObject var234 = (JSONObject)var223.next();
               var29 = new LinearLayout((Context)var239.getAct());
               var29.setOrientation(0);
               var29.setGravity(16);
               var29.setPadding(0, var239.getUi().dp(6), 0, var239.getUi().dp(6));
               View var288 = new View((Context)var239.getAct());
               var288.setBackground((Drawable)CarUi.rounded$default(var239.getUi(), var239.favColors.get(var8).intValue(), 5, null, 4, null));
               android.widget.LinearLayout.LayoutParams var271 = new android.widget.LinearLayout.LayoutParams(var239.getUi().dp(20), var239.getUi().dp(20));
               var271.rightMargin = var239.getUi().dp(12);
               Unit var277 = Unit.INSTANCE;
               var29.addView(var288, (LayoutParams)var271);
               LinearLayout var272 = new LinearLayout((Context)var239.getAct());
               var272.setOrientation(1);
               TextView var278 = new TextView((Context)var239.getAct());
               var278.setText(GxPage.str$default(var239, var234, "name", null, 2, null));
               var278.setTextSize(18.0F);
               var278.setTextColor(CarUi.Companion.getTEXT());
               var278.setTypeface(Typeface.DEFAULT_BOLD);
               var272.addView((View)var278);
               var40 = GxHomeKt.access$gxHomeNum(var234.opt(var78));
               TextView var279 = new TextView((Context)var239.getAct());
               var32 = GxHomeKt.access$gxHomeInt(var40);
               boolean var54;
               if (var40 == 1.0) {
                  var54 = true;
               } else {
                  var54 = false;
               }

               if (var54) {
                  var25 = "drive";
               } else {
                  var25 = var78;
               }

               var279.setText(var32 + var211 + var25 + " using this model");
               var279.setTextSize(15.0F);
               var279.setTextColor(CarUi.Companion.getDIM());
               var272.addView((View)var279);
               var29.addView((View)var272);
               var250.addView((View)var29);
            }

            Unit var79 = Unit.INSTANCE;
         }

         LinearLayout var212 = var239.card((ViewGroup)var247.get(1));
         var239.cardHead(var212, "Storage", TuplesKt.to("Recordings", "gx:recordings"));
         JSONObject var121 = var67.optJSONObject("segmentCounts");
         JSONObject var80 = var121;
         if (var121 == null) {
            var80 = new JSONObject();
         }

         GxPage var224 = var239;
         CharSequence var185 = GxPage.str$default(var239, var67, "legacyText", null, 2, null);
         var17 = var185;
         if (StringsKt.isBlank(var185)) {
            String var186 = GxHomeKt.access$gxHomeBytes(var67.opt("usedBytes"));
            String var123 = GxHomeKt.access$gxHomeBytes(var67.opt("totalBytes"));
            var17 = var186 + " used of " + var123;
         }

         GxPage.text$default(var224, (String)var17, (ViewGroup)var212, 17.0F, CarUi.Companion.getTEXT(), false, 16, null);
         var239.setProgress(var239.progress((ViewGroup)var212), GxHomeKt.gxHomeClamp$default(var67.opt("usedPercent"), 0.0, 0.0, 6, null));
         String var124 = GxHomeKt.access$gxHomeInt(var80.opt("standard"));
         var239.kv(var212, "Dashcam footage", var124 + " segments");
         String var125 = GxHomeKt.access$gxHomeInt(var80.opt("highResolution"));
         var239.kv(var212, "High-resolution footage", var125 + " segments");
         String var81 = GxHomeKt.access$gxHomeInt(var80.opt("alternate"));
         var239.kv(var212, "Konik footage", var81 + " segments");
         var239.kv(var212, "Free space", GxHomeKt.access$gxHomeBytes(var67.opt("freeBytes")));
         Unit var82 = Unit.INSTANCE;
         var17 = this.body;
         LinearLayout var83 = var17;
         if (var17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(var30);
            var83 = null;
         }

         this.header("Your device", (ViewGroup)var83);
         GxHomePage var187 = this;
         List var204 = columns$default(var187, 2, null, 2, null);
         LinearLayout var199 = var187.card((ViewGroup)var204.get(0));
         var187.cardHead(var199, "Vitals", TuplesKt.to("System", "gx:system"));
         CharSequence var127 = GxPage.str$default(var187, var19, "status", null, 2, null);
         var71 = var127;
         if (StringsKt.isBlank(var127)) {
            var71 = "Parked";
         }

         var187.kv(var199, "Status", (String)var71);
         CharSequence var128 = GxPage.str$default(var187, var19, "lanIp", null, 2, null);
         var71 = var128;
         if (StringsKt.isBlank(var128)) {
            var71 = "unknown";
         }

         var187.kv(var199, "LAN IP", (String)var71);
         CharSequence var129 = GxPage.str$default(var187, var19, "networkName", null, 2, null);
         var71 = var129;
         if (StringsKt.isBlank(var129)) {
            var71 = "No wireless connectivity";
         }

         var187.kv(var199, "Network", (String)var71);
         String var87;
         if (var19.has("uptimeSeconds") && !var19.isNull("uptimeSeconds")) {
            var87 = GxHomeKt.access$gxHomeDuration(var19.opt("uptimeSeconds"));
         } else {
            var87 = "unknown";
         }

         var187.kv(var199, "Uptime", var87);
         String var88;
         if (var19.has("cpuTempC") && !var19.isNull("cpuTempC")) {
            String var89 = GxHomeKt.access$gxHomeInt(var19.opt("cpuTempC"));
            var88 = var89 + " C";
         } else {
            var88 = "unknown";
         }

         var187.kv(var199, "CPU temp", var88);
         String var90;
         if (var19.has("gpuTempC") && !var19.isNull("gpuTempC")) {
            String var91 = GxHomeKt.access$gxHomeInt(var19.opt("gpuTempC"));
            var90 = var91 + " C";
         } else {
            var90 = "unknown";
         }

         var187.kv(var199, "GPU temp", var90);
         LinearLayout var200 = var187.card((ViewGroup)var204.get(1));
         var187.cardHead(var200, "Software", TuplesKt.to("Updates", "native:software"));

         for (Pair var92 : CollectionsKt.listOf(
            new Pair[]{
               TuplesKt.to("Branch", "branchName"),
               TuplesKt.to("Build", "buildEnvironment"),
               TuplesKt.to("Commit", "commitHash"),
               TuplesKt.to("Version date", "versionDate"),
               TuplesKt.to("Fork maintainer", "forkMaintainer"),
               TuplesKt.to("Update available", "updateAvailable")
            }
         )) {
            String var205 = (String)var92.component1();
            String var93 = (String)var92.component2();
            CharSequence var130 = GxPage.str$default(var187, var20, var93, null, 2, null);
            var71 = var130;
            if (StringsKt.isBlank(var130)) {
               var71 = "unknown";
            }

            var187.kv(var200, var205, (String)var71);
         }

         Unit var95 = Unit.INSTANCE;
      }
   }

   private static final Unit render$lambda$27(GxHomePage var0) {
      var0.refresh();
      return Unit.INSTANCE;
   }

   private static final Unit render$lambda$71$lambda$69(GxHomePage var0, String var1, String var2, List var3) {
      if (!Intrinsics.areEqual(var0.togglingKey, var1)) {
         var0.toggleDriveStats(var2, var3);
      }

      return Unit.INSTANCE;
   }

   private final void toggleDriveStats(String var1, List<String> var2) {
      if (Intrinsics.areEqual(var1, "ignore")) {
         this.confirm(
            "Ignore this drive's statistics?",
            "It will no longer affect local weekly totals, records, model usage, engagement, or attention streaks.",
            "Ignore",
            new GxHomePage$$ExternalSyntheticLambda7(this, var2, var1)
         );
      } else {
         toggleDriveStats$go(this, var2, var1);
      }
   }

   private static final void toggleDriveStats$go(GxHomePage var0, List<String> var1, String var2) {
      var0.togglingKey = CollectionsKt.joinToString$default(var1, ",", null, null, 0, null, null, 62, null);
      var0.render();
      GxPage.send$default(
         var0,
         "POST",
         "/api/stats/" + var2 + "_drive",
         new JSONObject().put("routeNames", new JSONArray(var1)),
         0L,
         new GxHomePage$$ExternalSyntheticLambda9(var0, var2),
         8,
         null
      );
   }

   private static final Unit toggleDriveStats$go$lambda$89(GxHomePage var0, String var1, String var2, JSONObject var3) {
      if (var2 != null) {
         CharSequence var5 = var2;
         Object var4 = var5;
         if (StringsKt.isBlank(var5)) {
            var4 = "Unable to " + var1 + " drive statistics.";
         }

         var0.toast((String)var4);
         var0.togglingKey = "";
         var0.render();
      } else {
         var0.togglingKey = "";
         var0.refreshing = false;
         var0.load();
      }

      return Unit.INSTANCE;
   }

   private static final Unit toggleDriveStats$lambda$90(GxHomePage var0, List var1, String var2) {
      toggleDriveStats$go(var0, var1, var2);
      return Unit.INSTANCE;
   }

   @Override
   public void build() {
      this.hero = GxPage.row$default(this, "Dashboard", "Loading dashboard...", null, 0, 12, null);
      GxPage.Row var2 = this.hero;
      GxPage.Row var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("hero");
         var1 = null;
      }

      var1.getTitleView().setTextSize(26.0F);
      var2 = this.hero;
      var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("hero");
         var1 = null;
      }

      var1.getTitleView().setTypeface(Typeface.DEFAULT_BOLD);
      var1 = this.hero;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("hero");
         var1 = null;
      }

      GxPage.Row.button$default(var1, "Refresh", false, new GxHomePage$$ExternalSyntheticLambda2(this), 2, null);
      this.body = GxPage.box$default(this, null, 1, null);
      this.every(3500L, new GxHomePage$$ExternalSyntheticLambda3(this));
      this.every(5000L, new GxHomePage$$ExternalSyntheticLambda4(this));
   }

   @Override
   public void onShow() {
      this.load();
   }
}
