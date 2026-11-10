package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class SearchHistory {
   public static final SearchHistory.Companion Companion = new SearchHistory.Companion(null);
   public static final int KEEP = 30;
   private final LinkedHashMap<String, SearchHistory.Item> items = new LinkedHashMap<>();

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public SearchHistory(String var1) {
      JSONArray var5;
      try {
      } catch (Exception var15) {
         return;
      }

      if (var1 == null) {
         var1 = "[]";
      }

      try {
         var5 = new JSONArray(var1);
      } catch (Exception var14) {
         return;
      }

      int var2 = 0;

      int var4;
      try {
         var4 = var5.length();
      } catch (Exception var12) {
         return;
      }

      for (; var2 < var4; var2++) {
         try {
            var16 = var5.optJSONObject(var2);
         } catch (Exception var10) {
            break;
         }

         if (var16 != null) {
            boolean var3;
            String var6;
            label48: {
               label47: {
                  try {
                     var6 = var16.optString("q");
                     SearchHistory.Companion var7 = Companion;
                     Intrinsics.checkNotNull(var6);
                     if (var7.key(var6).length() > 0) {
                        break label47;
                     }
                  } catch (Exception var13) {
                     break;
                  }

                  var3 = false;
                  break label48;
               }

               var3 = true;
            }

            if (var3) {
               try {
                  Map var9 = this.items;
                  String var8 = Companion.key(var6);
                  SearchHistory.Item var17 = new SearchHistory.Item(
                     StringsKt.trim(var6).toString(), RangesKt.coerceAtLeast(var16.optInt("n", 1), 1), var16.optLong("t")
                  );
                  var9.put(var8, var17);
               } catch (Exception var11) {
                  break;
               }
            }
         }
      }
   }

   public final void clear() {
      this.items.clear();
   }

   public final String json() {
      Collection var1 = this.items.values();
      Intrinsics.checkNotNullExpressionValue(var1, "<get-values>(...)");
      Iterable var2 = var1;
      var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

      for (SearchHistory.Item var7 : var2) {
         var4.add(new JSONObject().put("q", var7.getText()).put("n", var7.getCount()).put("t", var7.getLastMs()));
      }

      List var5 = var4;
      String var6 = new JSONArray(var5).toString();
      Intrinsics.checkNotNullExpressionValue(var6, "toString(...)");
      return var6;
   }

   public final List<SearchHistory.Item> list() {
      Collection var1 = this.items.values();
      Intrinsics.checkNotNullExpressionValue(var1, "<get-values>(...)");
      return CollectionsKt.sortedWith(var1, new Comparator(new Comparator() {
         @Override
         public final int compare(T var1, T var2) {
            return ComparisonsKt.compareValues(((SearchHistory.Item)var2).getCount(), ((SearchHistory.Item)var1).getCount());
         }
      }) {
         final Comparator $this_thenByDescending;

         {
            this.$this_thenByDescending = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            int var3 = this.$this_thenByDescending.compare(var1, var2);
            if (var3 == 0) {
               var3 = ComparisonsKt.compareValues(((SearchHistory.Item)var2).getLastMs(), ((SearchHistory.Item)var1).getLastMs());
            }

            return var3;
         }
      });
   }

   public final void record(String var1, long var2) {
      Intrinsics.checkNotNullParameter(var1, "q");
      String var7 = Companion.key(var1);
      if (var7.length() >= 2) {
         SearchHistory.Item var5 = this.items.get(var7);
         Map var6 = this.items;
         CharSequence var8 = StringsKt.trim(var1).toString();
         var1 = new Regex("\\s+").replace(var8, " ");
         int var4;
         if (var5 != null) {
            var4 = var5.getCount();
         } else {
            var4 = 0;
         }

         var6.put(var7, new SearchHistory.Item(var1, var4 + 1, var2));
         if (this.items.size() > 30) {
            for (SearchHistory.Item var11 : CollectionsKt.drop(this.list(), 30)) {
               this.items.remove(Companion.key(var11.getText()));
            }
         }
      }
   }

   public final void remove(String var1) {
      Intrinsics.checkNotNullParameter(var1, "q");
      this.items.remove(Companion.key(var1));
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final String key(String var1) {
         Intrinsics.checkNotNullParameter(var1, "q");
         var1 = StringsKt.trim(var1).toString().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
         CharSequence var3 = var1;
         return new Regex("\\s+").replace(var3, " ");
      }
   }

   public static final class Item {
      private final int count;
      private final long lastMs;
      private final String text;

      public Item(String var1, int var2, long var3) {
         Intrinsics.checkNotNullParameter(var1, "text");
         super();
         this.text = var1;
         this.count = var2;
         this.lastMs = var3;
      }

      public final int getCount() {
         return this.count;
      }

      public final long getLastMs() {
         return this.lastMs;
      }

      public final String getText() {
         return this.text;
      }
   }
}
