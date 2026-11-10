package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

public final class HudLayout {
   public static final HudLayout.Companion Companion = new HudLayout.Companion(null);
   public static final String KEY = "layout";
   private final float d;
   private float displayLong;
   private float displayShort;
   private final List<HudLayout.Item> items;
   private final SharedPreferences prefs;
   private final HashMap<String, HudLayout.State> states;
   private String theme;
   private float windowScale;

   public HudLayout(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      this.prefs = var1.getSharedPreferences("starview_hud", 0);
      this.d = var1.getResources().getDisplayMetrics().density;
      HudLayout.Item var20 = new HudLayout.Item(R.id.maxCard, "maxCard", "MAX set speed", new HudLayout.Anchor(8388659, 24, 20), false, false, 48, null);
      HudLayout.Item var9 = new HudLayout.Item(R.id.slc, "slc", "Speed limit", new HudLayout.Anchor(8388659, 126, 20), false, false, 48, null);
      HudLayout.Item var11 = new HudLayout.Item(
         R.id.slcSources, "slcSources", "Speed limit sources", new HudLayout.Anchor(8388659, 228, 20), false, false, 48, null
      );
      HudLayout.Item var14 = new HudLayout.Item(R.id.gauge, "gauge", "Curve / stop gauge", new HudLayout.Anchor(8388659, 24, 134), false, false, 48, null);
      HudLayout.Item var17 = new HudLayout.Item(R.id.compass, "compass", "Compass", new HudLayout.Anchor(49, 0, 64), false, false, 48, null);
      HudLayout.Item var25 = new HudLayout.Item(R.id.gear, "gear", "Settings", new HudLayout.Anchor(49, 0, 14), false, false, 32, null);
      HudLayout.Item var29 = new HudLayout.Item(R.id.wheel, "wheel", "Steering wheel", new HudLayout.Anchor(8388661, 24, 20), false, false, 48, null);
      HudLayout.Item var5 = new HudLayout.Item(R.id.egpu, "egpu", "eGPU status", new HudLayout.Anchor(8388661, 24, 188), false, false, 48, null);
      HudLayout.Item var13 = new HudLayout.Item(R.id.navCard, "navCard", "Navigation", new HudLayout.Anchor(8388693, 24, 136), false, false, 48, null);
      HudLayout.Item var16 = new HudLayout.Item(R.id.nudgeHint, "nudgeHint", "Exit nudge prompt", new HudLayout.Anchor(49, 0, 100), false, false, 48, null);
      HudLayout.Item var2 = new HudLayout.Item(R.id.slowCard, "slowCard", "Why it's slowing", new HudLayout.Anchor(49, 0, 200), false, false, 48, null);
      HudLayout.Item var27 = new HudLayout.Item(
         R.id.laneCard, "laneCard", "Lane tracker (test)", new HudLayout.Anchor(8388661, 24, 250), false, false, 48, null
      );
      HudLayout.Item var23 = new HudLayout.Item(
         R.id.laneSuggest, "laneSuggest", "Change lanes? (confirm)", new HudLayout.Anchor(49, 0, 310), false, false, 32, null
      );
      HudLayout.Item var7 = new HudLayout.Item(R.id.blinkCard, "blinkCard", "Blinker buttons (test)", new HudLayout.Anchor(81, 0, 64), false, false, 48, null);
      HudLayout.Item var15 = new HudLayout.Item(R.id.gpsCard, "gpsCard", "GPS satellites", new HudLayout.Anchor(8388659, 24, 250), false, false, 48, null);
      HudLayout.Item var18 = new HudLayout.Item(R.id.weather, "weather", "Weather", new HudLayout.Anchor(8388691, 28, 150), false, false, 48, null);
      HudLayout.Item var28 = new HudLayout.Item(R.id.speedBox, "speedBox", "Speed", new HudLayout.Anchor(8388691, 28, 16), false, false, 48, null);
      HudLayout.Item var19 = new HudLayout.Item(R.id.roadName, "roadName", "Road name", new HudLayout.Anchor(81, 0, 10), false, false, 48, null);
      HudLayout.Item var10 = new HudLayout.Item(R.id.devMetrics, "devMetrics", "Device metrics", new HudLayout.Anchor(81, 0, 34), false, false, 48, null);
      HudLayout.Item var4 = new HudLayout.Item(
         R.id.personality, "personality", "Following distance", new HudLayout.Anchor(8388693, 130, 20), false, false, 48, null
      );
      HudLayout.Item var22 = new HudLayout.Item(R.id.dm, "dm", "Driver monitoring", new HudLayout.Anchor(8388693, 24, 20), false, false, 48, null);
      HudLayout.Item var12 = new HudLayout.Item(
         R.id.slcActions, "slcActions", "Speed limit buttons (Accept / Decline)", new HudLayout.Anchor(8388659, 126, 132), false, false, 48, null
      );
      HudLayout.Item var24 = new HudLayout.Item(
         R.id.curveLabel, "curveLabel", "Curve speed label", new HudLayout.Anchor(8388661, 134, 26), false, false, 48, null
      );
      HudLayout.Item var26 = new HudLayout.Item(R.id.clockTv, "clock", "Clock", new HudLayout.Anchor(8388661, 134, 66), false, false, 16, null);
      HudLayout.Item var21 = new HudLayout.Item(R.id.modeText, "modeText", "Driving mode", new HudLayout.Anchor(81, 0, 58), false, false, 16, null);
      HudLayout.Item var6 = new HudLayout.Item(
         R.id.devSidebar, "devSidebar", "Developer sidebar", new HudLayout.Anchor(8388661, 24, 250), false, false, 16, null
      );
      HudLayout.Item var8 = new HudLayout.Item(R.id.pipLeft, "pipLeft", "Side view - left", new HudLayout.Anchor(8388691, 24, 24), false, false, 48, null);
      HudLayout.Item var3 = new HudLayout.Item(R.id.pipRight, "pipRight", "Side view - right", new HudLayout.Anchor(8388693, 24, 24), false, false, 48, null);
      this.items = CollectionsKt.listOf(
         new HudLayout.Item[]{
            var20,
            var9,
            var11,
            var14,
            var17,
            var25,
            var29,
            var5,
            var13,
            var16,
            var2,
            var27,
            var23,
            var7,
            var15,
            var18,
            var28,
            var19,
            var10,
            var4,
            var22,
            var12,
            var24,
            var26,
            var21,
            var6,
            var8,
            var3
         }
      );
      this.states = new HashMap<>();
      this.theme = "day";
      this.windowScale = 1.0F;
   }

   private final boolean defaultOn(String var1) {
      Iterator var4 = this.items.iterator();

      while (true) {
         if (var4.hasNext()) {
            Object var3 = var4.next();
            if (!Intrinsics.areEqual(((HudLayout.Item)var3).getKey(), var1)) {
               continue;
            }

            var5 = var3;
            break;
         }

         var5 = null;
         break;
      }

      HudLayout.Item var6 = (HudLayout.Item)var5;
      boolean var2;
      if (var6 != null) {
         var2 = var6.getDefaultOn();
      } else {
         var2 = true;
      }

      return var2;
   }

   private final int dp(int var1) {
      return (int)(var1 * this.d);
   }

   private final float keep(float var1, float var2, float var3, float var4) {
      float var6 = 2;
      float var5 = var2 / var6 + var1;
      float var7 = 3;
      if (!(var5 < var3 / var7)) {
         if (var5 > var6 * var3 / var7) {
            var1 = var4 - (var3 - var1);
         } else {
            var1 = var4 / var6 + (var5 - var3 / var6) - var2 / var6;
         }
      }

      return var1;
   }

   private final Pair<Integer, Integer> savedPos(HudLayout.State var1, int var2, int var3, int var4, int var5) {
      float var8 = this.windowScale;
      float var6;
      if (Float.isNaN(var1.getCw())) {
         if (this.displayLong > 0.0F) {
            var6 = this.displayLong;
         } else {
            var6 = var2 / var8;
         }
      } else {
         var6 = var1.getCw();
      }

      float var7;
      if (Float.isNaN(var1.getCh())) {
         if (this.displayShort > 0.0F) {
            var7 = this.displayShort;
         } else {
            var7 = var3 / var8;
         }
      } else {
         var7 = var1.getCh();
      }

      float var10 = var4 / var8;
      float var9 = var5 / var8;
      var6 = this.keep(var1.getX() * var6, var10, var6, var2 / var8);
      var7 = this.keep(var1.getY() * var7, var9, var7, var3 / var8);
      return TuplesKt.to((int)(var6 * var8), (int)(var7 * var8));
   }

   public final void adopt(FrameLayout var1) {
      Intrinsics.checkNotNullParameter(var1, "root");
      Iterator var5 = this.items.iterator();

      while (var5.hasNext()) {
         View var6 = var1.findViewById(((HudLayout.Item)var5.next()).getId());
         if (var6 != null) {
            ViewParent var4 = var6.getParent();
            ViewGroup var11;
            if (var4 instanceof ViewGroup) {
               var11 = (ViewGroup)var4;
            } else {
               var11 = null;
            }

            if (var11 != null) {
               int var2 = var6.getLayoutParams().width;
               int var3 = var6.getLayoutParams().height;
               if (var11 != var1) {
                  var11.removeView(var6);
                  var1.addView(var6, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(var2, var3, 8388659)));
               } else {
                  LayoutParams var12 = var6.getLayoutParams();
                  Intrinsics.checkNotNull(var12, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                  ((android.widget.FrameLayout.LayoutParams)var12).gravity = 8388659;
               }

               var6.setPivotX(0.0F);
               var6.setPivotY(0.0F);
            }
         }
      }

      int var10 = R.id.topLeft;

      for (int var8 = 0; var8 < 1; var8++) {
         View var13 = var1.findViewById(new int[]{var10}[var8]);
         if (var13 != null) {
            var1.removeView(var13);
         }
      }

      Iterable var19 = RangesKt.until(0, var1.getChildCount());
      var var14 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var19, 10));
      var5 = var19.iterator();

      while (var5.hasNext()) {
         var14.add(var1.getChildAt(((IntIterator)var5).nextInt()));
      }

      List var15 = var14;
      Iterable var21 = var15;
      var14 = new ArrayList();

      for (Object var23 : var21) {
         View var22 = (View)var23;
         boolean var9;
         if (var22 instanceof ViewGroup && ((ViewGroup)var22).getId() == -1 && ((ViewGroup)var22).getChildCount() == 0) {
            var9 = true;
         } else {
            var9 = false;
         }

         if (var9) {
            var14.add(var23);
         }
      }

      List var17 = var14;
      Iterator var18 = var17.iterator();

      while (var18.hasNext()) {
         var1.removeView((View)var18.next());
      }
   }

   public final void apply(FrameLayout var1) {
      Intrinsics.checkNotNullParameter(var1, "root");
      int var7 = var1.getWidth();
      int var6 = var1.getHeight();
      if (var7 != 0 && var6 != 0) {
         for (HudLayout.Item var11 : this.items) {
            View var13 = var1.findViewById(var11.getId());
            if (var13 != null) {
               LayoutParams var10 = var13.getLayoutParams();
               android.widget.FrameLayout.LayoutParams var19;
               if (var10 instanceof android.widget.FrameLayout.LayoutParams) {
                  var19 = (android.widget.FrameLayout.LayoutParams & LayoutParams)var10;
               } else {
                  var19 = null;
               }

               if (var19 != null) {
                  HudLayout.State var14 = this.states.get(var11.getKey());
                  float var2;
                  if (var14 != null) {
                     var2 = var14.getScale();
                  } else {
                     var2 = 1.0F;
                  }

                  var2 *= this.windowScale;
                  int var3;
                  if (var13.getWidth() > 0) {
                     var3 = var13.getWidth();
                  } else {
                     var3 = var13.getMeasuredWidth();
                  }

                  int var9 = (int)(var3 * var2);
                  if (var13.getHeight() > 0) {
                     var3 = var13.getHeight();
                  } else {
                     var3 = var13.getMeasuredHeight();
                  }

                  int var8 = (int)(var3 * var2);
                  Pair var20 = this.defaultPos(var11, var7, var6, var9, var8);
                  var3 = ((Number)var20.component1()).intValue();
                  int var4 = ((Number)var20.component2()).intValue();
                  Pair var21;
                  if (var14 != null && !Float.isNaN(var14.getX()) && !Float.isNaN(var14.getY())) {
                     var21 = this.savedPos(var14, var7, var6, var9, var8);
                  } else {
                     var21 = null;
                  }

                  boolean var5 = false;
                  if (var21 != null) {
                     var3 = RangesKt.coerceIn(((Number)var21.getFirst()).intValue(), 0, Math.max(0, var7 - var9));
                  }

                  if (var21 != null) {
                     var4 = RangesKt.coerceIn(((Number)var21.getSecond()).intValue(), 0, Math.max(0, var6 - var8));
                  }

                  if (var19.leftMargin != var3 || var19.topMargin != var4) {
                     var19.leftMargin = var3;
                     var19.topMargin = var4;
                     var19.gravity = 8388659;
                     var13.setLayoutParams((LayoutParams)var19);
                  }

                  boolean var18 = var5;
                  if (var13.getScaleX() == var2) {
                     var18 = true;
                  }

                  if (!var18) {
                     var13.setScaleX(var2);
                     var13.setScaleY(var2);
                  }
               }
            }
         }
      }
   }

   public final Pair<Integer, Integer> defaultPos(HudLayout.Item var1, int var2, int var3, int var4, int var5) {
      Intrinsics.checkNotNullParameter(var1, "item");
      int var7 = var1.getAnchor().getGravity();
      float var6 = this.windowScale;
      if ((var7 & 8388613) == 8388613) {
         var2 = var2 - var4 - (int)(this.dp(var1.getAnchor().getDx()) * var6);
      } else if ((var7 & 1) == 1) {
         var2 = (var2 - var4) / 2;
      } else {
         var2 = (int)(this.dp(var1.getAnchor().getDx()) * var6);
      }

      if ((var7 & 80) == 80) {
         var3 = var3 - var5 - (int)(this.dp(var1.getAnchor().getDy()) * var6);
      } else {
         var3 = (int)(this.dp(var1.getAnchor().getDy()) * var6);
      }

      return TuplesKt.to(var2, var3);
   }

   public final boolean enabled(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      HudLayout.State var3 = this.states.get(var1);
      boolean var2;
      if (var3 != null) {
         var2 = var3.getOn();
      } else {
         var2 = this.defaultOn(var1);
      }

      return var2;
   }

   public final float getDisplayLong() {
      return this.displayLong;
   }

   public final float getDisplayShort() {
      return this.displayShort;
   }

   public final List<HudLayout.Item> getItems() {
      return this.items;
   }

   public final HashMap<String, HudLayout.State> getStates() {
      return this.states;
   }

   public final String getTheme() {
      return this.theme;
   }

   public final float getWindowScale() {
      return this.windowScale;
   }

   public final void load(String var1) {
      Intrinsics.checkNotNullParameter(var1, "theme");
      this.theme = var1;
      String var2 = this.prefs.getString("layout", null);
      var1 = var2;
      if (var2 == null) {
         var2 = this.prefs.getString("layout_day", null);
         var1 = var2;
         if (var2 == null) {
            var1 = this.prefs.getString("layout_night", null);
         }
      }

      this.restore(var1);
   }

   public final void place(HudLayout.State var1, float var2, float var3, int var4, int var5) {
      Intrinsics.checkNotNullParameter(var1, "s");
      var1.setX(var2 / var4);
      var1.setY(var3 / var5);
      var1.setCw(var4 / this.windowScale);
      var1.setCh(var5 / this.windowScale);
   }

   public final RectF rect(FrameLayout var1, HudLayout.Item var2) {
      Intrinsics.checkNotNullParameter(var1, "root");
      Intrinsics.checkNotNullParameter(var2, "item");
      View var7 = var1.findViewById(var2.getId());
      if (var7 == null) {
         return null;
      } else {
         LayoutParams var8 = var7.getLayoutParams();
         android.widget.FrameLayout.LayoutParams var9;
         if (var8 instanceof android.widget.FrameLayout.LayoutParams) {
            var9 = (android.widget.FrameLayout.LayoutParams)var8;
         } else {
            var9 = null;
         }

         if (var9 == null) {
            return null;
         } else {
            HudLayout.State var10 = this.states.get(var2.getKey());
            float var3;
            if (var10 != null) {
               var3 = var10.getScale();
            } else {
               var3 = 1.0F;
            }

            float var4 = var3 * this.windowScale;
            int var6;
            if (var7.getWidth() > 0) {
               var6 = var7.getWidth();
            } else {
               var6 = var7.getMeasuredWidth();
            }

            float var5 = var6;
            if (var7.getHeight() > 0) {
               var6 = var7.getHeight();
            } else {
               var6 = var7.getMeasuredHeight();
            }

            var3 = var6;
            return new RectF(var9.leftMargin, var9.topMargin, var9.leftMargin + var5 * var4, var9.topMargin + var3 * var4);
         }
      }
   }

   public final void reset() {
      this.states.clear();
      this.prefs.edit().remove("layout").remove("layout_day").remove("layout_night").apply();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void restore(String var1) {
      this.states.clear();
      if (var1 != null) {
         HudLayout var31;
         try {
            Result.Companion var30 = Result.Companion;
            var31 = this;
         } catch (Throwable var28) {
            Result.Companion var3 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(var28));
            return;
         }

         boolean var2 = false;

         JSONObject var4;
         Iterator var5;
         try {
            var4 = new JSONObject(var1);
            var5 = var4.keys();
            Intrinsics.checkNotNullExpressionValue(var5, "keys(...)");
         } catch (Throwable var27) {
            Result.Companion var32 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(var27));
            return;
         }

         HudLayout var29 = var31;

         while (true) {
            try {
               if (!var5.hasNext()) {
                  break;
               }

               String var34 = (String)var5.next();
               JSONObject var6 = var4.getJSONObject(var34);
               Map var8 = var29.states;
               HudLayout.State var7 = new HudLayout.State(
                  var6.optBoolean("on", true),
                  (float)var6.optDouble("x", Double.NaN),
                  (float)var6.optDouble("y", Double.NaN),
                  (float)var6.optDouble("s", 1.0),
                  (float)var6.optDouble("cw", Double.NaN),
                  (float)var6.optDouble("ch", Double.NaN)
               );
               var8.put(var34, var7);
            } catch (Throwable var26) {
               Result.Companion var33 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var26));
               return;
            }
         }

         try {
            KtCompat.resultOf(Unit.INSTANCE);
         } catch (Throwable var25) {
            Result.Companion var35 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(var25));
            return;
         }
      }
   }

   public final void save() {
      this.prefs.edit().putString("layout", this.snapshot()).apply();
   }

   public final void setDisplayLong(float var1) {
      this.displayLong = var1;
   }

   public final void setDisplayShort(float var1) {
      this.displayShort = var1;
   }

   public final void setWindowScale(float var1) {
      this.windowScale = var1;
   }

   public final String snapshot() {
      JSONObject var2 = new JSONObject();

      for (Entry var1 : this.states.entrySet()) {
         String var4 = (String)var1.getKey();
         HudLayout.State var5 = (HudLayout.State)var1.getValue();
         JSONObject var6 = new JSONObject().put("on", var5.getOn());
         Object var7;
         if (Float.isNaN(var5.getX())) {
            var7 = JSONObject.NULL;
         } else {
            var7 = (double)var5.getX();
         }

         var6 = var6.put("x", var7);
         if (Float.isNaN(var5.getY())) {
            var7 = JSONObject.NULL;
         } else {
            var7 = (double)var5.getY();
         }

         var6 = var6.put("y", var7).put("s", var5.getScale());
         if (Float.isNaN(var5.getCw())) {
            var7 = JSONObject.NULL;
         } else {
            var7 = (double)var5.getCw();
         }

         var6 = var6.put("cw", var7);
         if (Float.isNaN(var5.getCh())) {
            var7 = JSONObject.NULL;
         } else {
            var7 = (double)var5.getCh();
         }

         var2.put(var4, var6.put("ch", var7));
      }

      String var11 = var2.toString();
      Intrinsics.checkNotNullExpressionValue(var11, "toString(...)");
      return var11;
   }

   public final HudLayout.State state(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Map var3 = this.states;
      Object var2 = var3.get(var1);
      Object var4;
      if (var2 == null) {
         var2 = new HudLayout.State(this.defaultOn(var1), 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 62, null);
         var3.put(var1, var2);
         var4 = var2;
      } else {
         var4 = var2;
      }

      return (HudLayout.State)var4;
   }

   public static final class Anchor {
      private final int dx;
      private final int dy;
      private final int gravity;

      public Anchor(int var1, int var2, int var3) {
         this.gravity = var1;
         this.dx = var2;
         this.dy = var3;
      }

      public final int getDx() {
         return this.dx;
      }

      public final int getDy() {
         return this.dy;
      }

      public final int getGravity() {
         return this.gravity;
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

   public static final class Item {
      private final HudLayout.Anchor anchor;
      private final boolean canHide;
      private final boolean defaultOn;
      private final int id;
      private final String key;
      private final String label;

      public Item(int var1, String var2, String var3, HudLayout.Anchor var4, boolean var5, boolean var6) {
         Intrinsics.checkNotNullParameter(var2, "key");
         Intrinsics.checkNotNullParameter(var3, "label");
         Intrinsics.checkNotNullParameter(var4, "anchor");
         super();
         this.id = var1;
         this.key = var2;
         this.label = var3;
         this.anchor = var4;
         this.canHide = var5;
         this.defaultOn = var6;
      }

      // $VF: synthetic method
      public Item(int var1, String var2, String var3, HudLayout.Anchor var4, boolean var5, boolean var6, int var7, DefaultConstructorMarker var8) {
         if ((var7 & 16) != 0) {
            var5 = true;
         }

         if ((var7 & 32) != 0) {
            var6 = true;
         }

         this(var1, var2, var3, var4, var5, var6);
      }

      public final HudLayout.Anchor getAnchor() {
         return this.anchor;
      }

      public final boolean getCanHide() {
         return this.canHide;
      }

      public final boolean getDefaultOn() {
         return this.defaultOn;
      }

      public final int getId() {
         return this.id;
      }

      public final String getKey() {
         return this.key;
      }

      public final String getLabel() {
         return this.label;
      }
   }

   public static final class State {
      private float ch;
      private float cw;
      private boolean on;
      private float scale;
      private float x;
      private float y;

      public State() {
         this(false, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 63, null);
      }

      public State(boolean var1, float var2, float var3, float var4, float var5, float var6) {
         this.on = var1;
         this.x = var2;
         this.y = var3;
         this.scale = var4;
         this.cw = var5;
         this.ch = var6;
      }

      // $VF: synthetic method
      public State(boolean var1, float var2, float var3, float var4, float var5, float var6, int var7, DefaultConstructorMarker var8) {
         if ((var7 & 1) != 0) {
            var1 = true;
         }

         float var9 = Float.NaN;
         if ((var7 & 2) != 0) {
            var2 = Float.NaN;
         }

         if ((var7 & 4) != 0) {
            var3 = Float.NaN;
         }

         if ((var7 & 8) != 0) {
            var4 = 1.0F;
         }

         if ((var7 & 16) != 0) {
            var5 = Float.NaN;
         }

         if ((var7 & 32) != 0) {
            var6 = var9;
         }

         this(var1, var2, var3, var4, var5, var6);
      }

      public final float getCh() {
         return this.ch;
      }

      public final float getCw() {
         return this.cw;
      }

      public final boolean getOn() {
         return this.on;
      }

      public final float getScale() {
         return this.scale;
      }

      public final float getX() {
         return this.x;
      }

      public final float getY() {
         return this.y;
      }

      public final void setCh(float var1) {
         this.ch = var1;
      }

      public final void setCw(float var1) {
         this.cw = var1;
      }

      public final void setOn(boolean var1) {
         this.on = var1;
      }

      public final void setScale(float var1) {
         this.scale = var1;
      }

      public final void setX(float var1) {
         this.x = var1;
      }

      public final void setY(float var1) {
         this.y = var1;
      }
   }
}
