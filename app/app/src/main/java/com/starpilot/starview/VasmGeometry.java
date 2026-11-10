package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONObject;

public final class VasmGeometry {
   private static final List<Pair<Integer, Integer>> FORMATS = CollectionsKt.listOf(new Pair[]{TuplesKt.to(1928, 1208), TuplesKt.to(1344, 760)});
   public static final VasmGeometry INSTANCE = new VasmGeometry();

   private VasmGeometry() {
   }

   private final boolean between(int var1, int var2, int var3) {
      boolean var4;
      if (var2 >= Math.min(var1, var3) && var2 <= Math.max(var1, var3)) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private static final JSONArray draft$arr(List<int[]> var0) {
      JSONArray var1 = new JSONArray();

      for (int[] var2 : var0) {
         var1.put(new JSONArray().put(var2[0]).put(var2[1]));
      }

      return var1;
   }

   private final boolean intersects(int[] var1, int[] var2, int[] var3, int[] var4) {
      long var11 = this.orient(var1, var2, var3);
      long var9 = this.orient(var1, var2, var4);
      long var7 = this.orient(var3, var4, var1);
      long var13 = this.orient(var3, var4, var2);
      if (!intersects$on(var1, var3, var2) && !intersects$on(var1, var4, var2) && !intersects$on(var3, var1, var4) && !intersects$on(var3, var2, var4)) {
         boolean var5;
         if (var11 > 0L) {
            var5 = true;
         } else {
            var5 = false;
         }

         boolean var6;
         if (var9 > 0L) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var5 == var6) {
            return false;
         }

         if (var7 > 0L) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var13 > 0L) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var5 == var6) {
            return false;
         }
      }

      return true;
   }

   private static final boolean intersects$on(int[] var0, int[] var1, int[] var2) {
      long var5 = INSTANCE.orient(var0, var1, var2);
      boolean var4 = false;
      boolean var3 = var4;
      if (var5 == 0L) {
         var3 = var4;
         if (INSTANCE.between(var0[0], var1[0], var2[0])) {
            var3 = var4;
            if (INSTANCE.between(var0[1], var1[1], var2[1])) {
               var3 = true;
            }
         }
      }

      return var3;
   }

   private final long orient(int[] var1, int[] var2, int[] var3) {
      return (long)(var2[0] - var1[0]) * (var3[1] - var1[1]) - (long)(var2[1] - var1[1]) * (var3[0] - var1[0]);
   }

   public final JSONObject draft(int var1, int var2, List<int[]> var3, List<int[]> var4) {
      Intrinsics.checkNotNullParameter(var3, "cameraLeft");
      Intrinsics.checkNotNullParameter(var4, "cameraRight");
      if (!this.supported(var1, var2)) {
         return null;
      } else if (var3.isEmpty() && var4.isEmpty()) {
         return null;
      } else if (!var3.isEmpty() && !this.validPolygon(var3, var1, var2)) {
         return null;
      } else {
         return !var4.isEmpty() && !this.validPolygon(var4, var1, var2)
            ? null
            : new JSONObject().put("version", 1).put("width", var1).put("height", var2).put("poly_left", draft$arr(var3)).put("poly_right", draft$arr(var4));
      }
   }

   public final List<Pair<Integer, Integer>> getFORMATS() {
      return FORMATS;
   }

   public final boolean supported(int var1, int var2) {
      var var6 = FORMATS;
      boolean var5 = var6 instanceof Collection;
      boolean var4 = false;
      if (!var5 || !((Collection)var6).isEmpty()) {
         for (Pair var8 : var6) {
            boolean var3;
            if (((Number)var8.getFirst()).intValue() == var1 && ((Number)var8.getSecond()).intValue() == var2) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               var4 = true;
               break;
            }
         }
      }

      return var4;
   }

   public final boolean validPolygon(List<int[]> var1, int var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "pts");
      int var6 = var1.size();
      boolean var13;
      if (3 <= var6 && var6 < 33) {
         var13 = 1;
      } else {
         var13 = 0;
      }

      if (!var13) {
         return false;
      } else {
         var var8 = var1;
         if (var8 instanceof Collection && ((Collection)var8).isEmpty()) {
            var13 = (boolean)0;
         } else {
            Iterator var21 = var8.iterator();

            while (true) {
               if (!var21.hasNext()) {
                  var13 = (boolean)0;
                  break;
               }

               int[] var9 = (int[])var21.next();
               if (var9[0] >= 0 && var9[0] <= var2 && var9[1] >= 0 && var9[1] <= var3) {
                  var13 = (boolean)0;
               } else {
                  var13 = (boolean)1;
               }

               if (var13) {
                  var13 = (boolean)1;
                  break;
               }
            }
         }

         if (var13) {
            return false;
         } else {
            Iterable var25 = var1;
            var var22 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var25, 10));

            for (int[] var26 : var25) {
               int var7 = var26[0];
               var13 = var26[1];
               var22.add(var7 + "," + var13);
            }

            List var23 = var22;
            if (CollectionsKt.toSet(var23).size() != var1.size()) {
               return false;
            } else {
               double var4 = 0.0;
               var13 = 0;

               for (int var19 = var1.size(); var13 < var19; var13++) {
                  int[] var24 = (int[])var1.get(var13);
                  int[] var27 = (int[])var1.get((var13 + 1) % var1.size());
                  var4 += (double)var24[0] * var27[1] - (double)var27[0] * var24[1];
               }

               if (Math.abs(var4) <= var2 * 1.0E-9 * var3) {
                  return false;
               } else {
                  var2 = 0;

                  for (int var18 = var1.size(); var2 < var18; var2++) {
                     var3 = var2 + 2;

                     for (int var20 = var1.size(); var3 < var20; var3++) {
                        if ((var2 != 0 || var3 != var1.size() - 1)
                           && this.intersects(
                              (int[])var1.get(var2),
                              (int[])var1.get((var2 + 1) % var1.size()),
                              (int[])var1.get(var3),
                              (int[])var1.get((var3 + 1) % var1.size())
                           )) {
                           return false;
                        }
                     }
                  }

                  return true;
               }
            }
         }
      }
   }
}
