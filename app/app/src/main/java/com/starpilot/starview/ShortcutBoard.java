package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class ShortcutBoard {
   public static final ShortcutBoard.Companion Companion = new ShortcutBoard.Companion(null);
   public static final int PER_PAGE = 12;
   private final List<List<ShortcutBoard.Item>> pages = new ArrayList<>();

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public ShortcutBoard(String var1) {
      label102: {
         JSONObject var4;
         try {
         } catch (Exception var20) {
            this.pages.clear();
            break label102;
         }

         if (var1 == null) {
            var1 = "{}";
         }

         try {
            var4 = new JSONObject(var1);
            var22 = var4.optJSONArray("pages");
         } catch (Exception var19) {
            this.pages.clear();
            break label102;
         }

         JSONArray var21 = var22;
         if (var22 == null) {
            try {
               var21 = new JSONArray();
            } catch (Exception var18) {
               this.pages.clear();
               break label102;
            }
         }

         int var3 = 0;

         int var2;
         try {
            var2 = var21.length();
         } catch (Exception var16) {
            this.pages.clear();
            break label102;
         }

         label84:
         for (; var3 < var2; var3++) {
            JSONArray var5;
            try {
               var5 = var21.optJSONArray(var3);
            } catch (Exception var10) {
               this.pages.clear();
               break;
            }

            JSONArray var23 = var5;
            if (var5 == null) {
               try {
                  var23 = new JSONArray();
               } catch (Exception var9) {
                  this.pages.clear();
                  break;
               }
            }

            List var6;
            Iterator var8;
            ArrayList var28;
            try {
               var6 = this.pages;
               Iterable var25 = RangesKt.until(0, var23.length());
               var28 = new ArrayList();
               var28 = var28;
               var8 = var25.iterator();
            } catch (Exception var15) {
               this.pages.clear();
               break;
            }

            while (true) {
               try {
                  if (!var8.hasNext()) {
                     break;
                  }

                  var26 = var23.optJSONObject(((IntIterator)var8).nextInt());
               } catch (Exception var17) {
                  this.pages.clear();
                  break label84;
               }

               ShortcutBoard.Item var27;
               if (var26 != null) {
                  try {
                     var27 = Companion.item(var26);
                  } catch (Exception var14) {
                     this.pages.clear();
                     break label84;
                  }
               } else {
                  var27 = null;
               }

               if (var27 != null) {
                  try {
                     var28.add(var27);
                  } catch (Exception var13) {
                     this.pages.clear();
                     break label84;
                  }
               }
            }

            try {
               var24 = var28;
            } catch (Exception var12) {
               this.pages.clear();
               break;
            }

            try {
               var6.add(CollectionsKt.toMutableList(CollectionsKt.take(var24, 12)));
            } catch (Exception var11) {
               this.pages.clear();
               break;
            }
         }
      }

      this.normalize();
   }

   // $VF: synthetic method
   public static int add$default(ShortcutBoard var0, ShortcutBoard.Item var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = -1;
      }

      return var0.add(var1, var2);
   }

   public final int add(ShortcutBoard.Item var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "item");
      boolean var3;
      if (var2 >= 0 && var2 < this.pages.size()) {
         var3 = true;
      } else {
         var3 = false;
      }

      List var4;
      if (var3) {
         var4 = CollectionsKt.plus(RangesKt.until(var2, this.pages.size()), RangesKt.until(0, var2));
      } else {
         var4 = CollectionsKt.toList(CollectionsKt.getIndices(this.pages));
      }

      Iterator var5 = var4.iterator();

      do {
         if (!var5.hasNext()) {
            var4 = null;
            break;
         }

         var4 = (List)var5.next();
         var2 = ((Number)var4).intValue();
         if (this.pages.get(var2).size() < 12) {
            var8 = true;
         } else {
            var8 = false;
         }
      } while (!var8);

      Integer var13 = (Integer)var4;
      if (var13 != null) {
         var2 = var13;
      } else {
         ShortcutBoard var14 = this;
         var14.pages.add(new ArrayList<>());
         var2 = var14.pages.size() - 1;
      }

      this.pages.get(var2).add(var1);
      List var6 = this.pages.get(var2);
      this.normalize();
      var4 = this.pages;
      var2 = 0;
      Iterator var16 = var4.iterator();

      while (true) {
         if (!var16.hasNext()) {
            var2 = -1;
            break;
         }

         if ((List)var16.next() == var6) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            break;
         }

         var2++;
      }

      return var2;
   }

   public final boolean contains(String var1) {
      Intrinsics.checkNotNullParameter(var1, "target");
      var var4 = this.pages;
      boolean var3;
      if (var4 instanceof Collection && ((Collection)var4).isEmpty()) {
         var3 = false;
      } else {
         Iterator var6 = var4.iterator();

         while (true) {
            if (!var6.hasNext()) {
               var3 = false;
               break;
            }

            var var5 = (Iterable & List)var6.next();
            boolean var2;
            if (var5 instanceof Collection && ((Collection)var5).isEmpty()) {
               var2 = false;
            } else {
               Iterator var7 = var5.iterator();

               while (true) {
                  if (!var7.hasNext()) {
                     var2 = false;
                     break;
                  }

                  if (Intrinsics.areEqual(((ShortcutBoard.Item)var7.next()).getTarget(), var1)) {
                     var2 = true;
                     break;
                  }
               }
            }

            if (var2) {
               var3 = true;
               break;
            }
         }
      }

      return var3;
   }

   public final List<List<ShortcutBoard.Item>> getPages() {
      return this.pages;
   }

   public final String json() {
      JSONObject var1 = new JSONObject();
      Iterable var3 = this.pages;
      var var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));

      for (Iterable var5 : var3) {
         var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
         Iterator var10 = var5.iterator();

         while (var10.hasNext()) {
            var4.add(((ShortcutBoard.Item)var10.next()).json());
         }

         List var9 = var4;
         var2.add(new JSONArray(var9));
      }

      List var7 = var2;
      String var6 = var1.put("pages", new JSONArray(var7)).toString();
      Intrinsics.checkNotNullExpressionValue(var6, "toString(...)");
      return var6;
   }

   public final void move(int var1, int var2, int var3) {
      List var6 = CollectionsKt.getOrNull(this.pages, var1);
      if (var6 != null) {
         int var4 = var2 + var3;
         boolean var9 = true;
         boolean var7;
         if (var2 >= 0 && var2 < var6.size()) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            if (var4 >= 0 && var4 < var6.size()) {
               var7 = var9;
            } else {
               var7 = false;
            }

            if (var7) {
               ShortcutBoard.Item var5 = (ShortcutBoard.Item)var6.get(var2);
               var6.set(var2, var6.get(var4));
               var6.set(var4, var5);
               return;
            }
         }
      }
   }

   public final void normalize() {
      if (this.pages.isEmpty()) {
         this.pages.add(new ArrayList<>());
      }

      Iterable var2 = CollectionsKt.drop(this.pages, 1);
      var var1 = new ArrayList();

      for (var var2_it : var2) {

         var2 = var2_it;
         if (!((List)var2).isEmpty()) {
            var1.add(var2);
         }
      }

      List var4 = var1;
      List var5 = CollectionsKt.toMutableList(var4);
      if (var5.isEmpty() || CollectionsKt.<List>last(var5).size() >= 12) {
         var5.add(new ArrayList());
      }

      while (this.pages.size() > 1) {
         this.pages.remove(this.pages.size() - 1);
      }

      this.pages.addAll(var5);
   }

   public final void remove(int var1, int var2) {
      List var4 = CollectionsKt.getOrNull(this.pages, var1);
      if (var4 != null) {
         boolean var3 = false;
         boolean var5 = var3;
         if (var2 >= 0) {
            var5 = var3;
            if (var2 < var4.size()) {
               var5 = true;
            }
         }

         if (!var5) {
            var4 = null;
         }

         if (var4 != null) {
            ShortcutBoard.Item var6 = (ShortcutBoard.Item)var4.remove(var2);
         }
      }

      this.normalize();
   }

   public final boolean toPage(int var1, int var2, int var3) {
      List var5 = CollectionsKt.getOrNull(this.pages, var1);
      if (var5 == null) {
         return false;
      } else {
         List var4 = CollectionsKt.getOrNull(this.pages, var1 + var3);
         if (var4 == null) {
            return false;
         } else {
            boolean var6;
            if (var2 >= 0 && var2 < var5.size()) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6 && var4.size() < 12) {
               var4.add(var5.remove(var2));
               this.normalize();
               return true;
            } else {
               return false;
            }
         }
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final ShortcutBoard.Item item(JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "o");
         var var3 = var1.optString("t");
         boolean var2;
         if (var3.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Object var4 = null;
         if (var2) {
            var3 = null;
         }

         String var5 = var3;
         ShortcutBoard.Item var8 = (ShortcutBoard.Item)var4;
         if (var5 != null) {
            String var6 = var1.optString("l", var5);
            Intrinsics.checkNotNullExpressionValue(var6, "optString(...)");
            var3 = var1.optString("w");
            Intrinsics.checkNotNullExpressionValue(var3, "optString(...)");
            var4 = var1.optString("i", "new");
            Intrinsics.checkNotNullExpressionValue(var4, "optString(...)");
            String var7 = var1.optString("g", "device");
            Intrinsics.checkNotNullExpressionValue(var7, "optString(...)");
            var8 = new ShortcutBoard.Item(var6, var3, var5, (String)var4, var7);
         }

         return var8;
      }
   }

   public static final class Item {
      private final String group;
      private final String icon;
      private final String label;
      private final String target;
      private final String where;

      public Item(String var1, String var2, String var3, String var4, String var5) {
         Intrinsics.checkNotNullParameter(var1, "label");
         Intrinsics.checkNotNullParameter(var2, "where");
         Intrinsics.checkNotNullParameter(var3, "target");
         Intrinsics.checkNotNullParameter(var4, "icon");
         Intrinsics.checkNotNullParameter(var5, "group");
         super();
         this.label = var1;
         this.where = var2;
         this.target = var3;
         this.icon = var4;
         this.group = var5;
      }

      public final String getGroup() {
         return this.group;
      }

      public final String getIcon() {
         return this.icon;
      }

      public final String getLabel() {
         return this.label;
      }

      public final String getTarget() {
         return this.target;
      }

      public final String getWhere() {
         return this.where;
      }

      public final JSONObject json() {
         JSONObject var1 = new JSONObject().put("l", this.label).put("w", this.where).put("t", this.target).put("i", this.icon).put("g", this.group);
         Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
         return var1;
      }
   }
}
