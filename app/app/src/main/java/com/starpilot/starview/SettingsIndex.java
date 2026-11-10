package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;

public final class SettingsIndex {
   public static final SettingsIndex INSTANCE = new SettingsIndex();

   private SettingsIndex() {
   }

   // $VF: synthetic method
   public static List search$default(SettingsIndex var0, List var1, String var2, int var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = 40;
      }

      return var0.search(var1, var2, var3);
   }

   // $VF: synthetic method
   public static SettingsIndex.Summary summary$default(SettingsIndex var0, List var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = "";
      }

      return var0.summary(var1, var2);
   }

   public final List<String> linkedPages(List<GalaxyRow> var1) {
      Intrinsics.checkNotNullParameter(var1, "rows");
      Iterable var5 = var1;
      var var3 = new ArrayList();
      Iterator var4 = var5.iterator();

      while (var4.hasNext()) {
         var var6 = ((GalaxyRow)var4.next()).getPage();
         boolean var2;
         if (var6.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            var6 = null;
         }

         String var7 = var6;
         if (var7 != null) {
            var3.add(var7);
         }
      }

      var1 = var3;
      return CollectionsKt.distinct(var1);
   }

   public final List<SettingsIndex.Entry> search(List<SettingsIndex.Entry> var1, String var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "entries");
      Intrinsics.checkNotNullParameter(var2, "query");
      var2 = var2.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
      CharSequence var8 = var2;
      Regex var19 = new Regex("\\s+");
      boolean var5 = false;
      Iterable var29 = var19.split(var8, 0);
      var var20 = new ArrayList();

      for (Object var9 : var29) {
         boolean var4;
         if (((String)var9).length() > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var20.add(var9);
         }
      }

      List var21 = var20;
      if (var21.isEmpty()) {
         return CollectionsKt.emptyList();
      } else {
         Iterable var15 = var1;
         boolean var6 = false;
         var var34 = new ArrayList();

         for (Object var10 : var15) {
            SettingsIndex.Entry var11 = (SettingsIndex.Entry)var10;
            var var12 = var21;
            boolean var23;
            if (var12 instanceof Collection && ((Collection)var12).isEmpty()) {
               var23 = true;
               var6 = var5;
               var5 = var6;
            } else {
               Iterator var40 = var12.iterator();

               while (true) {
                  if (!var40.hasNext()) {
                     var6 = var5;
                     var23 = true;
                     var5 = var6;
                     break;
                  }

                  String var14 = (String)var40.next();
                  CharSequence var13 = var11.getSearch();
                  CharSequence var41 = var14;
                  var5 = var6;
                  var6 = false;
                  if (!StringsKt.contains$default(var13, var41, false, 2, null)) {
                     var23 = false;
                     break;
                  }

                  boolean var7 = false;
                  var6 = var6;
                  var5 = var7;
               }
            }

            if (var23) {
               var34.add(var10);
            }

            var5 = var6;
            var6 = var5;
         }

         var1 = var34;
         Iterable var32 = CollectionsKt.sortedWith(var1, new Comparator(new Comparator(var21) {
            final List $words$inlined;

            {
               this.$words$inlined = var1;
            }

            @Override
            public final int compare(T var1, T var2) {
               var2 = ((SettingsIndex.Entry)var2).getLabel().toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
               Comparable var5 = StringsKt.startsWith$default(var2, (String)this.$words$inlined.get(0), false, 2, null);
               var1 = ((SettingsIndex.Entry)var1).getLabel().toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
               return ComparisonsKt.compareValues(var5, StringsKt.startsWith$default(var1, (String)this.$words$inlined.get(0), false, 2, null));
            }
         }) {
            final Comparator $this_thenBy;

            {
               this.$this_thenBy = var1;
            }

            @Override
            public final int compare(T var1, T var2x) {
               int var3x = this.$this_thenBy.compare(var1, var2x);
               if (var3x == 0) {
                  var1 = ((SettingsIndex.Entry)var1).getLabel().toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                  Comparable var5x = var1;
                  var2x = ((SettingsIndex.Entry)var2x).getLabel().toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var2x, "toLowerCase(...)");
                  var3x = ComparisonsKt.compareValues(var5x, var2x);
               }

               return var3x;
            }
         });
         HashSet var22 = new HashSet();
         ArrayList var17 = new ArrayList();

         for (Object var33 : var32) {
            SettingsIndex.Entry var38 = (SettingsIndex.Entry)var33;
            String var36 = var38.getLabel();
            String var39 = var38.getTarget();
            var36 = var36 + "|" + var39;
            if (var22.add(var36)) {
               var17.add(var33);
            }
         }

         return CollectionsKt.take(var17, var3);
      }
   }

   public final String short_(String var1) {
      Intrinsics.checkNotNullParameter(var1, "label");
      String var2 = var1.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
      CharSequence var3 = var2;
      var1 = "strength";
      if (!StringsKt.contains$default(var3, "strength", false, 2, null)) {
         var3 = var2;
         var1 = "offset";
         if (!StringsKt.contains$default(var3, "offset", false, 2, null)) {
            if (StringsKt.contains$default(var2, "minimum", false, 2, null) && StringsKt.contains$default(var2, "speed", false, 2, null)) {
               var1 = "min speed";
            } else {
               var3 = var2;
               var1 = "delay";
               if (!StringsKt.contains$default(var3, "delay", false, 2, null)) {
                  if (StringsKt.contains$default(var2, "high-speed follow", false, 2, null)) {
                     var1 = "highway";
                  } else {
                     var3 = var2;
                     var1 = "follow";
                     if (!StringsKt.contains$default(var3, "follow", false, 2, null)) {
                        if (StringsKt.contains$default(var2, "threshold", false, 2, null)) {
                           var1 = "below";
                        } else {
                           var1 = CollectionsKt.joinToString$default(
                              CollectionsKt.takeLast(StringsKt.split$default(var2, new char[]{' '}, false, 0, 6, null), 2),
                              " ",
                              null,
                              null,
                              0,
                              null,
                              null,
                              62,
                              null
                           );
                        }
                     }
                  }
               }
            }
         }
      }

      return var1;
   }

   public final SettingsIndex.Summary summary(List<GalaxyRow> var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "rows");
      Intrinsics.checkNotNullParameter(var2, "title");
      Iterable var4 = var1;
      var var10 = new ArrayList();

      for (var var4_it : var4) {

         var4 = var4_it;
         GalaxyRow var6 = (GalaxyRow)var4;
         boolean var3;
         if (!GalaxyRow.Companion.isPipCrop(var6) && var6.getControl() != GalaxyRow.Control.GROUP && var6.getControl() != GalaxyRow.Control.ACTION) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var10.add(var4);
         }
      }

      List var28 = var10;
      String var11 = var2.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var11, "toLowerCase(...)");
      var2 = StringsKt.trim(StringsKt.removeSuffix(var11, " personality")).toString();
      Iterator var7 = var28.iterator();

      boolean var22;
      do {
         if (!var7.hasNext()) {
            var12 = null;
            break;
         }

         label150: {
            var12 = var7.next();
            GalaxyRow var32 = (GalaxyRow)var12;
            if (var32.getControl() == GalaxyRow.Control.SWITCH) {
               if (new Regex("^(enable|use|allow) ", RegexOption.IGNORE_CASE).containsMatchIn(var32.getLabel())) {
                  break label150;
               }

               if (var2.length() > 0) {
                  var22 = true;
               } else {
                  var22 = false;
               }

               if (var22) {
                  String var34 = var32.getLabel().toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var34, "toLowerCase(...)");
                  if (Intrinsics.areEqual(var34, var2)) {
                     break label150;
                  }
               }

               if (StringsKt.endsWith$default(var32.getLabel(), " Controller", false, 2, null)) {
                  break label150;
               }
            }

            var22 = false;
            continue;
         }

         var22 = true;
      } while (!var22);

      GalaxyRow var35 = (GalaxyRow)var12;
      var2 = "On";
      Boolean var13;
      if (var35 != null) {
         var13 = Intrinsics.areEqual(var35.getValue(), "On");
      } else {
         var13 = null;
      }

      ArrayList var33 = new ArrayList();
      if (var35 != null) {
         if (!Intrinsics.areEqual(var13, true)) {
            var2 = "Off";
         }

         var33.add(var2);
      }

      ArrayList var37 = new ArrayList();

      for (GalaxyRow var9 : var28) {
         if (var37.size() < 2 && var9 != var35) {
            if (StringsKt.toDoubleOrNull(var9.getValue()) != null) {
               var22 = true;
            } else {
               var22 = false;
            }

            if (var9.getControl() == GalaxyRow.Control.SELECT) {
               var2 = var9.getValue();
               if (!StringsKt.isBlank(var2) && var2.length() <= 26 && !Intrinsics.areEqual(var2, "Selected Profile")) {
                  var22 = true;
               } else {
                  var22 = false;
               }

               if (!var22) {
                  var2 = null;
               }

               if (var2 != null) {
                  var37.add(var2);
               }
            } else if (var9.getControl() == GalaxyRow.Control.SLIDER || var9.getControl() == GalaxyRow.Control.READOUT && var22) {
               if (var9.getUnit().length() > 0) {
                  var22 = true;
               } else {
                  var22 = false;
               }

               if (var22) {
                  var2 = this.short_(var9.getLabel());
                  String var38 = var9.withUnit(GalaxyRow.shown$default(var9, null, 1, null));
                  var37.add(var2 + " " + var38);
               }
            }
         }
      }

      var33.addAll(var37);
      if (var35 == null && var37.isEmpty()) {
         label109: {
            for (Object var29 : var28) {
               if (((GalaxyRow)var29).getControl() == GalaxyRow.Control.SWITCH) {
                  var22 = true;
               } else {
                  var22 = false;
               }

               if (var22) {
                  var19 = var29;
                  break label109;
               }
            }

            var19 = null;
         }

         GalaxyRow var30 = (GalaxyRow)var19;
         if (var30 != null) {
            var2 = var30.getLabel();
            String var31 = var30.getValue().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var31, "toLowerCase(...)");
            var33.add(var2 + " " + var31);
         }
      }

      return new SettingsIndex.Summary(var13, CollectionsKt.joinToString$default(var33, " · ", null, null, 0, null, null, 62, null));
   }

   public static final class Entry {
      private final SettingIcons.Icon icon;
      private final String label;
      private final String reason;
      private final String search;
      private final String target;
      private final String unit;
      private final String value;
      private final String where;

      public Entry(String var1, String var2, String var3, String var4, String var5, String var6, SettingIcons.Icon var7) {
         Intrinsics.checkNotNullParameter(var1, "label");
         Intrinsics.checkNotNullParameter(var2, "value");
         Intrinsics.checkNotNullParameter(var3, "where");
         Intrinsics.checkNotNullParameter(var4, "target");
         Intrinsics.checkNotNullParameter(var5, "reason");
         Intrinsics.checkNotNullParameter(var6, "unit");
         super();
         this.label = var1;
         this.value = var2;
         this.where = var3;
         this.target = var4;
         this.reason = var5;
         this.unit = var6;
         this.icon = var7;
         var1 = CollectionsKt.joinToString$default(
               CollectionsKt.listOf(new String[]{this.label, this.value, this.reason, this.unit, this.where}), " ", null, null, 0, null, null, 62, null
            )
            .toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
         this.search = var1;
      }

      // $VF: synthetic method
      public Entry(
         String var1, String var2, String var3, String var4, String var5, String var6, SettingIcons.Icon var7, int var8, DefaultConstructorMarker var9
      ) {
         if ((var8 & 16) != 0) {
            var5 = "";
         }

         if ((var8 & 32) != 0) {
            var6 = "";
         }

         if ((var8 & 64) != 0) {
            var7 = null;
         }

         this(var1, var2, var3, var4, var5, var6, var7);
      }

      public final SettingIcons.Icon getIcon() {
         return this.icon;
      }

      public final String getLabel() {
         return this.label;
      }

      public final String getReason() {
         return this.reason;
      }

      public final String getSearch() {
         return this.search;
      }

      public final String getTarget() {
         return this.target;
      }

      public final String getUnit() {
         return this.unit;
      }

      public final String getValue() {
         return this.value;
      }

      public final String getWhere() {
         return this.where;
      }
   }

   public static final class Summary {
      private final String line;
      private final Boolean on;

      public Summary(Boolean var1, String var2) {
         Intrinsics.checkNotNullParameter(var2, "line");
         super();
         this.on = var1;
         this.line = var2;
      }

      public final String getLine() {
         return this.line;
      }

      public final Boolean getOn() {
         return this.on;
      }
   }
}
