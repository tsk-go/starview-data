package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GalaxyRow {
   public static final GalaxyRow.Companion Companion = new GalaxyRow.Companion(null);
   private final boolean action;
   private final boolean available;
   private final List<String> choices;
   private final boolean confirm;
   private final GalaxyRow.Control control;
   private final String coordinate;
   private final String defaultValue;
   private final boolean heading;
   private final int index;
   private final String label;
   private final boolean laneStrength;
   private final double maximum;
   private final double minimum;
   private final String page;
   private final String previewSound;
   private final String reason;
   private final String repairValue;
   private final boolean resetAvailable;
   private final double step;
   private final String unit;
   private final String value;

   public GalaxyRow(int var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "o");
      super();
      this.index = var1;
      String var5 = var2.optString("label");
      Intrinsics.checkNotNullExpressionValue(var5, "optString(...)");
      this.label = var5;
      this.value = Companion.str(var2, "value");
      JSONArray var11 = var2.optJSONArray("choices");
      boolean var3 = false;
      List var12;
      if (var11 != null) {
         Iterable var7 = RangesKt.until(0, var11.length());
         var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));
         Iterator var13 = var7.iterator();

         while (var13.hasNext()) {
            var6.add(var11.opt(((IntIterator)var13).nextInt()).toString());
         }

         var12 = var6;
      } else {
         var12 = CollectionsKt.emptyList();
      }

      this.choices = var12;
      this.step = Companion.num(var2, "step");
      this.minimum = Companion.num(var2, "minimum");
      this.maximum = Companion.num(var2, "maximum");
      this.unit = Companion.str(var2, "unit");
      this.available = var2.optBoolean("available", false);
      this.reason = Companion.str(var2, "reason");
      this.page = Companion.str(var2, "page");
      this.action = var2.optBoolean("action", false);
      this.confirm = var2.optBoolean("confirm", false);
      this.repairValue = Companion.str(var2, "repairValue");
      this.defaultValue = Companion.str(var2, "defaultValue");
      this.resetAvailable = var2.optBoolean("resetAvailable", false);
      this.laneStrength = Intrinsics.areEqual(Companion.str(var2, "displayScale"), "lane-strength");
      boolean var4;
      if (!var2.optBoolean("heading", false) && !StringsKt.startsWith$default(Companion.str(var2, "key"), "heading:", false, 2, null)) {
         var4 = false;
      } else {
         var4 = true;
      }

      this.heading = var4;
      this.coordinate = Companion.str(var2, "coordinate");
      this.previewSound = Companion.str(var2, "previewSound");
      GalaxyRow.Control var10;
      if (this.heading) {
         var10 = GalaxyRow.Control.READOUT;
      } else {
         boolean var8;
         if (this.page.length() > 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            var10 = GalaxyRow.Control.GROUP;
         } else {
            label68: {
               if (!this.confirm) {
                  var8 = var3;
                  if (this.repairValue.length() > 0) {
                     var8 = true;
                  }

                  if (!var8) {
                     if (this.choices.size() == 2 && this.choices.contains("Off") && this.choices.contains("On") && this.choices.contains(this.value)) {
                        var10 = GalaxyRow.Control.SWITCH;
                     } else if (this.bounds() == null
                        || StringsKt.toDoubleOrNull(this.value) == null && (!Intrinsics.areEqual(this.value, "Auto") || !this.choices.contains("Auto"))) {
                        if (!this.choices.isEmpty() && this.choices.contains(this.value)) {
                           var10 = GalaxyRow.Control.SELECT;
                        } else {
                           var10 = GalaxyRow.Control.READOUT;
                        }
                     } else {
                        var10 = GalaxyRow.Control.SLIDER;
                     }
                     break label68;
                  }
               }

               var10 = GalaxyRow.Control.ACTION;
            }
         }
      }

      this.control = var10;
   }

   // $VF: synthetic method
   public static String shown$default(GalaxyRow var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = var0.value;
      }

      return var0.shown(var1);
   }

   public final GalaxyRow.Bounds bounds() {
      double var7 = this.minimum;
      double var11 = this.maximum;
      double var9 = this.step;
      double var5 = var7;
      double var3 = var11;
      double var1 = var9;
      if (this.laneStrength) {
         var5 = var7;
         var3 = var11;
         var1 = var9;
         if (var7 < 100.0) {
            var5 = var7;
            var3 = var11;
            var1 = var9;
            if (var11 > 100.0) {
               var5 = 1.0;
               var3 = 10.0;
               if (this.step > 0.0) {
                  var1 = 1.0;
               } else {
                  var1 = 0.0;
               }
            }
         }
      }

      boolean var15 = Double.isInfinite(var5);
      boolean var14 = true;
      boolean var13;
      if (!var15 && !Double.isNaN(var5)) {
         var13 = true;
      } else {
         var13 = false;
      }

      if (var13) {
         if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
            var13 = true;
         } else {
            var13 = false;
         }

         if (var13) {
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
               var13 = var14;
            } else {
               var13 = false;
            }

            if (var13 && var3 > var5 && var1 > 0.0) {
               return new GalaxyRow.Bounds(var5, var3, var1, this.choices.contains("Auto"));
            }
         }
      }

      return null;
   }

   public final boolean getAction() {
      return this.action;
   }

   public final boolean getAvailable() {
      return this.available;
   }

   public final List<String> getChoices() {
      return this.choices;
   }

   public final boolean getConfirm() {
      return this.confirm;
   }

   public final GalaxyRow.Control getControl() {
      return this.control;
   }

   public final String getCoordinate() {
      return this.coordinate;
   }

   public final String getDefaultValue() {
      return this.defaultValue;
   }

   public final boolean getEditable() {
      boolean var4 = this.available;
      boolean var3 = false;
      boolean var2 = var3;
      if (var4) {
         if (!this.action) {
            boolean var1;
            if (this.page.length() > 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (!var1) {
               return var3;
            }
         }

         var2 = true;
      }

      return var2;
   }

   public final boolean getHeading() {
      return this.heading;
   }

   public final int getIndex() {
      return this.index;
   }

   public final String getLabel() {
      return this.label;
   }

   public final boolean getLaneStrength() {
      return this.laneStrength;
   }

   public final double getMaximum() {
      return this.maximum;
   }

   public final double getMinimum() {
      return this.minimum;
   }

   public final String getPage() {
      return this.page;
   }

   public final String getPreviewSound() {
      return this.previewSound;
   }

   public final String getReason() {
      return this.reason;
   }

   public final String getRepairValue() {
      return this.repairValue;
   }

   public final boolean getResetAvailable() {
      return this.resetAvailable;
   }

   public final boolean getShowDefault() {
      boolean var3 = this.resetAvailable;
      boolean var2 = false;
      if (var3) {
         boolean var1;
         if (this.defaultValue.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (!var1) {
            if (this.page.length() > 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (!var1) {
               Double var4 = StringsKt.toDoubleOrNull(this.shown(this.value));
               Double var5 = StringsKt.toDoubleOrNull(this.shown(this.defaultValue));
               if (var4 != null && var5 != null
                  ? !Intrinsics.areEqual(var4, var5)
                  : !Intrinsics.areEqual(this.shown(this.value), this.shown(this.defaultValue))) {
                  var2 = true;
               }

               return var2;
            }
         }
      }

      return false;
   }

   public final String getShownReason() {
      String var1;
      if (this.laneStrength && this.available && this.minimum < 100.0 && this.maximum > 100.0) {
         var1 = Companion.fmt(this.maximum);
         var1 = "1–10; 5 is normal (100%). Lower values reduce centering, higher values strengthen it (10 = " + var1 + "%).";
      } else {
         var1 = this.reason;
      }

      return var1;
   }

   public final double getStep() {
      return this.step;
   }

   public final String getUnit() {
      return this.unit;
   }

   public final String getValue() {
      return this.value;
   }

   public final String saved(double var1) {
      if (!this.laneStrength) {
         return Companion.fmt(var1);
      } else if (!(var1 < 1.0) && !(var1 > 10.0)) {
         GalaxyRow.Companion var7 = Companion;
         if (var1 <= 5.0) {
            var1 = this.minimum + (var1 - 1) * (100 - this.minimum) / 4;
         } else {
            double var5 = 100;
            double var3 = 5;
            var1 = var5 + (var1 - var3) * (this.maximum - var5) / var3;
         }

         return var7.fmt(Math.round(var1));
      } else {
         return null;
      }
   }

   public final String shown(String var1) {
      Intrinsics.checkNotNullParameter(var1, "v");
      if (!this.laneStrength) {
         return var1;
      } else {
         Double var8 = StringsKt.toDoubleOrNull(var1);
         if (var8 != null) {
            double var2 = var8;
            if (!(this.minimum >= 100.0) && !(this.maximum <= 100.0)) {
               if (var2 <= 100.0) {
                  var2 = 1 + (var2 - this.minimum) * 4 / (100 - this.minimum);
               } else {
                  double var6 = 5;
                  double var4 = 100;
                  var2 = var6 + (var2 - var4) * var6 / (this.maximum - var4);
               }

               GalaxyRow.Companion var9 = Companion;
               double var11 = 100;
               return var9.fmt(Math.rint(var2 * var11) / var11);
            } else {
               return var1;
            }
         } else {
            return var1;
         }
      }
   }

   public final String withUnit(String var1) {
      Intrinsics.checkNotNullParameter(var1, "v");
      if (this.laneStrength) {
         Double var4 = StringsKt.toDoubleOrNull(var1);
         String var5;
         if (var4 != null) {
            var5 = this.saved(var4.doubleValue());
         } else {
            var5 = null;
         }

         if (var5 != null) {
            var1 = var1 + " of 10 (" + var5 + "%)";
         }

         return var1;
      } else {
         if (!Intrinsics.areEqual(var1, "Auto")) {
            boolean var2;
            if (this.unit.length() == 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (!var2) {
               String var3 = this.unit;
               var1 = var1 + " " + var3;
            }
         }

         return var1;
      }
   }

   public static final class Bounds {
      private final boolean auto;
      private final double max;
      private final double min;
      private final double step;

      public Bounds(double var1, double var3, double var5, boolean var7) {
         this.min = var1;
         this.max = var3;
         this.step = var5;
         this.auto = var7;
      }

      public final boolean getAuto() {
         return this.auto;
      }

      public final double getMax() {
         return this.max;
      }

      public final double getMin() {
         return this.min;
      }

      public final double getStep() {
         return this.step;
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private final double num(JSONObject var1, String var2) {
         boolean var7 = var1.has(var2);
         double var5 = Double.NaN;
         double var3 = var5;
         if (var7) {
            if (var1.isNull(var2)) {
               var3 = var5;
            } else {
               var3 = var1.optDouble(var2, Double.NaN);
            }
         }

         return var3;
      }

      private final String str(JSONObject var1, String var2) {
         String var3;
         if (var1.has(var2) && !var1.isNull(var2)) {
            Object var4 = var1.opt(var2);
            if (var4 instanceof Number && !(var4 instanceof Integer) && !(var4 instanceof Long)) {
               var3 = GalaxyRow.Companion.fmt(((Number)var4).doubleValue());
            } else {
               var3 = var4.toString();
            }
         } else {
            var3 = "";
         }

         return var3;
      }

      public final String fmt(double var1) {
         boolean var3;
         if (var1 == Math.floor(var1)) {
            var3 = true;
         } else {
            var3 = false;
         }

         String var5;
         if (var3 && Math.abs(var1) < 1.0E15) {
            var5 = String.valueOf((long)var1);
         } else {
            var5 = String.format(Locale.US, "%.8f", Arrays.copyOf(new Object[]{var1}, 1));
            Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
            var5 = StringsKt.trimEnd(StringsKt.trimEnd(var5, new char[]{'0'}), new char[]{'.'});
         }

         return var5;
      }

      public final boolean isPipCrop(GalaxyRow var1) {
         Intrinsics.checkNotNullParameter(var1, "r");
         boolean var2 = false;
         if (SetsKt.setOf(new String[]{"Visual camera crop editor", "Camera crop size", "Restore default camera crop"}).contains(var1.getLabel())
            || new Regex("^Vehicle (left|right) crop").containsMatchIn(var1.getLabel())) {
            var2 = true;
         }

         return var2;
      }

      public final List<GalaxyRow> parse(JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "page");
         JSONArray var3 = var1.optJSONArray("rows");
         JSONArray var6 = var3;
         if (var3 == null) {
            var6 = new JSONArray();
         }

         Iterable var4 = RangesKt.until(0, var6.length());
         var var7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));
         Iterator var8 = var4.iterator();

         while (var8.hasNext()) {
            int var2 = ((IntIterator)var8).nextInt();
            JSONObject var5 = var6.getJSONObject(var2);
            Intrinsics.checkNotNullExpressionValue(var5, "getJSONObject(...)");
            var7.add(new GalaxyRow(var2, var5));
         }

         return var7;
      }

      public final double snap(double var1, GalaxyRow.Bounds var3) {
         Intrinsics.checkNotNullParameter(var3, "b");
         int var5 = (int)Math.floor((var3.getMax() - var3.getMin()) / var3.getStep() + 1.0E-9);
         int var4 = var5;
         if (Math.rint((var3.getMin() + var5 * var3.getStep()) * 1.0E8) / 1.0E8 > var3.getMax()) {
            var4 = var5 - 1;
         }

         var4 = RangesKt.coerceIn((int)Math.rint((var1 - var3.getMin()) / var3.getStep()), 0, var4);
         return Math.rint((var3.getMin() + var4 * var3.getStep()) * 1.0E8) / 1.0E8;
      }

      public final List<GalaxyRow> visible(String var1, List<GalaxyRow> var2) {
         Intrinsics.checkNotNullParameter(var1, "page");
         Intrinsics.checkNotNullParameter(var2, "rows");
         Iterable var6 = var2;
         var var5 = new ArrayList();

         for (Object var7 : var6) {
            boolean var3;
            GalaxyRow var9 = (GalaxyRow)var7;
            boolean var4 = GalaxyRow.Companion.isPipCrop(var9);
            var3 = false;
            label31:
            if (!var4) {
               GalaxyRow var8 = CollectionsKt.getOrNull(var2, var9.getIndex() - 1);
               if (SetsKt.setOf(new String[]{"sounds", "display"}).contains(var1)
                  && Intrinsics.areEqual(var9.getRepairValue(), "Auto")
                  && var8 != null
                  && var8.getChoices().contains("Auto")
                  && var8.getControl() == GalaxyRow.Control.SLIDER) {
                  String var12 = var9.getLabel();
                  String var11 = var8.getLabel();
                  if (Intrinsics.areEqual(var12, "Use Auto " + var11)) {
                     var3 = false;
                     break label31;
                  }
               }

               var3 = true;
            }

            if (var3) {
               var5.add(var7);
            }
         }

         return var5;
      }
   }

   public static enum Control {
      ACTION,
      GROUP,
      READOUT,
      SELECT,
      SLIDER,
      SWITCH;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(GalaxyRow.Control.$VALUES);
      private static final GalaxyRow.Control[] $VALUES = $values();

      // $VF: synthetic method
      private static final GalaxyRow.Control[] $values() {
         return new GalaxyRow.Control[]{GROUP, ACTION, SWITCH, SLIDER, SELECT, READOUT};
      }

      public static EnumEntries<GalaxyRow.Control> getEntries() {
         return $ENTRIES;
      }
   }
}
