package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class LaneMemory {
   private double lastOdo;
   private final float lineView;
   private volatile List<Pair<Float, Float>> lines;
   private final ArrayList<LaneMemory.Mark> marks = new ArrayList<>();

   // $VF: synthetic method
   public static boolean $r8$lambda$UquKBEBmKaNlnVUaQP6FJjdZOEU(LaneMemory.Mark var0) {
      return pruneMarks$lambda$4(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$x1BRsnEJnLuBPM3EfXmdzdJi8LA(LaneMemory.Mark var0, LaneMemory.Mark var1, LaneMemory.Mark var2) {
      return pruneMarks$lambda$10(var0, var1, var2);
   }

   public LaneMemory() {
      this.lines = CollectionsKt.emptyList();
      this.lineView = 5.6F;
   }

   // $VF: synthetic method
   public static Pair lanes$default(LaneMemory var0, int var1, float var2, float var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = 0.45F;
      }

      return var0.lanes(var1, var2, var3);
   }

   private final LaneMemory.Mark nearest(float var1, boolean var2, float var3) {
      LaneMemory.Mark var5 = null;
      Iterator var7 = this.marks.iterator();
      Intrinsics.checkNotNullExpressionValue(var7, "iterator(...)");

      while (var7.hasNext()) {
         Object var6 = var7.next();
         Intrinsics.checkNotNullExpressionValue(var6, "next(...)");
         var6 = (LaneMemory.Mark)var6;
         if (((LaneMemory.Mark)var6).getEdge() == var2) {
            float var4 = Math.abs(((LaneMemory.Mark)var6).getY() - var1);
            if (var4 < var3) {
               var3 = var4;
               var5 = (LaneMemory.Mark)var6;
            }
         }
      }

      return var5;
   }

   private final void prune() {
      this.pruneMarks();
      Iterable var2 = this.marks;
      var var1 = new ArrayList();

      for (var var2_it : var2) {

         var2 = var2_it;
         if (!((LaneMemory.Mark)var2).getEdge()) {
            var1.add(var2);
         }
      }

      List var4 = var1;
      var2 = var4;
      var1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

      for (LaneMemory.Mark var10 : var2) {
         var1.add(TuplesKt.to(var10.getY(), var10.getConf()));
      }

      List var6 = var1;
      this.lines = var6;
   }

   private final void pruneMarks() {
      CollectionsKt.removeAll(this.marks, new LaneMemory$$ExternalSyntheticLambda0());
      List var7 = this.marks;
      if (var7.size() > 1) {
         CollectionsKt.sortWith(var7, new Comparator() {
            @Override
            public final int compare(T var1, T var2) {
               return ComparisonsKt.compareValues(((LaneMemory.Mark)var1).getY(), ((LaneMemory.Mark)var2).getY());
            }
         });
      }

      int var4 = 1;

      while (var4 < this.marks.size()) {
         var7 = this.marks.get(var4 - 1);
         Intrinsics.checkNotNullExpressionValue(var7, "get(...)");
         LaneMemory.Mark var20 = (LaneMemory.Mark)var7;
         Object var8 = this.marks.get(var4);
         Intrinsics.checkNotNullExpressionValue(var8, "get(...)");
         var8 = (LaneMemory.Mark)var8;
         if (var20.getEdge() == ((LaneMemory.Mark)var8).getEdge() && Math.abs(var20.getY() - ((LaneMemory.Mark)var8).getY()) < 0.6F) {
            if (var20.getConf() >= ((LaneMemory.Mark)var8).getConf()) {
               var7 = this.marks.remove(var4);
            } else {
               var7 = this.marks.remove(var4 - 1);
            }

            Intrinsics.checkNotNull((LaneMemory.Mark)var7);
         } else {
            var4++;
         }
      }

      Iterable var30 = this.marks;
      var var22 = new ArrayList();
      Iterator var9 = var30.iterator();

      while (true) {
         boolean var6 = var9.hasNext();
         boolean var5 = false;
         if (!var6) {
            var7 = var22;
            Iterator var11 = var7.iterator();
            var6 = var11.hasNext();
            Object var36 = null;
            if (!var6) {
               var7 = null;
            } else {
               var7 = (List)var11.next();
               if (var11.hasNext()) {
                  float var1 = ((LaneMemory.Mark)var7).getY();
                  Object var32 = var7;

                  do {
                     Object var38 = var11.next();
                     float var3 = ((LaneMemory.Mark)var38).getY();
                     var7 = (List)var32;
                     float var2 = var1;
                     if (Float.compare(var1, var3) < 0) {
                        var7 = (List)var38;
                        var2 = var3;
                     }

                     var32 = var7;
                     var1 = var2;
                  } while (var11.hasNext());
               }
            }

            LaneMemory.Mark var39 = (LaneMemory.Mark)var7;
            var30 = this.marks;
            var var25 = new ArrayList();

            for (Object var40 : var30) {
               LaneMemory.Mark var34 = (LaneMemory.Mark)var40;
               boolean var17;
               if (var34.getEdge() && var34.getY() > 0.0F && var34.getConf() >= 0.3F) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               if (var17) {
                  var25.add(var40);
               }
            }

            var7 = var25;
            var11 = var7.iterator();
            if (!var11.hasNext()) {
               var7 = (List)var36;
            } else {
               var7 = (List)var11.next();
               if (var11.hasNext()) {
                  float var13 = ((LaneMemory.Mark)var7).getY();
                  Object var35 = var7;

                  do {
                     var36 = var11.next();
                     float var15 = ((LaneMemory.Mark)var36).getY();
                     float var14 = var13;
                     var7 = (List)var35;
                     if (Float.compare(var13, var15) > 0) {
                        var14 = var15;
                        var7 = (List)var36;
                     }

                     var13 = var14;
                     var35 = var7;
                  } while (var11.hasNext());
               }
            }

            LaneMemory.Mark var28 = (LaneMemory.Mark)var7;
            CollectionsKt.removeAll(this.marks, new LaneMemory$$ExternalSyntheticLambda1(var39, var28));
            return;
         }

         Object var31 = var9.next();
         LaneMemory.Mark var10 = (LaneMemory.Mark)var31;
         boolean var16 = var5;
         if (var10.getEdge()) {
            var16 = var5;
            if (var10.getY() < 0.0F) {
               var16 = var5;
               if (var10.getConf() >= 0.3F) {
                  var16 = true;
               }
            }
         }

         if (var16) {
            var22.add(var31);
         }
      }
   }

   private static final boolean pruneMarks$lambda$10(LaneMemory.Mark var0, LaneMemory.Mark var1, LaneMemory.Mark var2) {
      Intrinsics.checkNotNullParameter(var2, "it");
      boolean var3;
      if ((var0 == null || var2 == var0 || !(var2.getY() < var0.getY() - 0.8F)) && (var1 == null || var2 == var1 || !(var2.getY() > var1.getY() + 0.8F))) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   private static final boolean pruneMarks$lambda$4(LaneMemory.Mark var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      boolean var1;
      if (!(var0.getConf() < 0.1F) && !(Math.abs(var0.getY()) > 30.0F)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public final void clear() {
      this.marks.clear();
      this.lines = CollectionsKt.emptyList();
   }

   public final List<Pair<Float, Float>> getLines() {
      return this.lines;
   }

   public final ArrayList<LaneMemory.Mark> getMarks() {
      return this.marks;
   }

   public final Pair<Integer, Boolean> lanes(int var1, float var2, float var3) {
      boolean var8 = Float.isInfinite(var2);
      boolean var9 = false;
      boolean var6;
      if (!var8 && !Float.isNaN(var2)) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      if (!var6) {
         return null;
      } else {
         Iterable var11 = this.marks;
         var var10 = new ArrayList();

         for (Object var13 : var11) {
            label138: {
               LaneMemory.Mark var12 = (LaneMemory.Mark)var13;
               if (!var12.getEdge() && var12.getConf() >= var3) {
                  float var4 = var12.getY();
                  if (var1 < 0 ? !(var4 < var2 - 1.5F) : !(var4 > 1.5F + var2)) {
                     var6 = (boolean)0;
                  } else {
                     var6 = (boolean)1;
                  }

                  if (var6) {
                     var6 = (boolean)1;
                     break label138;
                  }
               }

               var6 = (boolean)0;
            }

            if (var6) {
               var10.add(var13);
            }
         }

         List var25 = var10;
         List var26 = CollectionsKt.sortedWith(
            var25,
            new Comparator(var2) {
               final float $ourLine$inlined;

               {
                  this.$ourLine$inlined = var1;
               }

               @Override
               public final int compare(T var1, T var2x) {
                  return ComparisonsKt.compareValues(
                     Math.abs(((LaneMemory.Mark)var1).getY() - this.$ourLine$inlined), Math.abs(((LaneMemory.Mark)var2x).getY() - this.$ourLine$inlined)
                  );
               }
            }
         );
         var6 = 0;

         for (LaneMemory.Mark var33 : var26) {
            var3 = Math.abs(var33.getY() - var2);
            if (!(var3 < 2.3F)) {
               if (var3 > 4.6F) {
                  break;
               }

               var6++;
               var2 = var33.getY();
            }
         }

         var11 = this.marks;
         var10 = new ArrayList();

         for (Object var37 : var11) {
            boolean var23;
            label109: {
               LaneMemory.Mark var39 = (LaneMemory.Mark)var37;
               if (var39.getEdge() && var39.getConf() >= 0.35F) {
                  var3 = var39.getY();
                  if (var1 < 0 ? !(var3 < 0.0F) : !(var3 > 0.0F)) {
                     var23 = false;
                  } else {
                     var23 = true;
                  }

                  if (var23) {
                     var23 = true;
                     break label109;
                  }
               }

               var23 = false;
            }

            if (var23) {
               var10.add(var37);
            }
         }

         List var29 = var10;
         Iterator var40 = var29.iterator();
         Object var30;
         if (!var40.hasNext()) {
            var30 = null;
         } else {
            var30 = (LaneMemory.Mark)var40.next();
            if (var40.hasNext()) {
               float var19 = Math.abs(var30.getY());
               var11 = var30;

               do {
                  Object var38 = var40.next();
                  float var5 = Math.abs(((LaneMemory.Mark)var38).getY());
                  var30 = var11;
                  var3 = var19;
                  if (Float.compare(var19, var5) > 0) {
                     var30 = (LaneMemory.Mark)var38;
                     var3 = var5;
                  }

                  var11 = var30;
                  var19 = var3;
               } while (var40.hasNext());
            }
         }

         var30 = var30;
         var8 = var9;
         if (var30 != null) {
            var8 = var9;
            if (Math.abs(var30.getY() - var2) < 1.8F) {
               var3 = var30.getY();
               boolean var14;
               if (var1 < 0 ? !(var3 <= 0.3F + var2) : !(var3 >= var2 - 0.3F)) {
                  var14 = false;
               } else {
                  var14 = true;
               }

               var8 = var9;
               if (var14) {
                  var8 = true;
               }
            }
         }

         return var6 == 0 && !var8 ? null : TuplesKt.to(var6, var8);
      }
   }

   public final void shift(float var1) {
      Iterator var2 = this.marks.iterator();
      Intrinsics.checkNotNullExpressionValue(var2, "iterator(...)");

      while (var2.hasNext()) {
         LaneMemory.Mark var3 = (LaneMemory.Mark)var2.next();
         Intrinsics.checkNotNullExpressionValue(var3, "next(...)");
         var3 = var3;
         var3.setY(var3.getY() - var1);
      }
   }

   public final void update(double var1, List<Pair<Float, Float>> var3, float var4, float var5, boolean var6) {
      Intrinsics.checkNotNullParameter(var3, "lines");
      double var7 = RangesKt.coerceAtLeast(var1 - this.lastOdo, 0.0);
      this.lastOdo = var1;
      if (var7 > 0.0) {
         float var9 = (float)Math.exp(-var7 / 1500.0);
         Iterator var14 = this.marks.iterator();
         Intrinsics.checkNotNullExpressionValue(var14, "iterator(...)");

         while (var14.hasNext()) {
            LaneMemory.Mark var15 = (LaneMemory.Mark)var14.next();
            Intrinsics.checkNotNullExpressionValue(var15, "next(...)");
            var15 = var15;
            if (Math.abs(var15.getY()) > this.lineView || var15.getEdge()) {
               var15.setConf(var15.getConf() * var9);
            }
         }
      }

      if (var6) {
         this.prune();
      } else {
         ArrayList var40 = new ArrayList();
         Iterator var17 = var3.iterator();

         while (true) {
            var6 = var17.hasNext();
            boolean var13 = true;
            if (!var6) {
               if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               if (var13) {
                  var40.add(new Triple<>(-var4, true, 0.8F));
               }

               if (!Float.isInfinite(var5) && !Float.isNaN(var5)) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               if (var13) {
                  var40.add(new Triple<>(var5, true, 0.8F));
               }

               if (var40.isEmpty()) {
                  this.prune();
                  return;
               }

               ArrayList var18 = new ArrayList();
               Iterator var35 = var40.iterator();
               Intrinsics.checkNotNullExpressionValue(var35, "iterator(...)");

               while (var35.hasNext()) {
                  Object var16 = var35.next();
                  Intrinsics.checkNotNullExpressionValue(var16, "next(...)");
                  var16 = (Triple)var16;
                  float var25 = ((Number)var16.component1()).floatValue();
                  if (!(Boolean)var16.component2()) {
                     var16 = this.nearest(var25, false, 0.7F);
                     if (var16 != null) {
                        var18.add(var25 - ((LaneMemory.Mark)var16).getY());
                     }
                  }
               }

               if (!var18.isEmpty()) {
                  CollectionsKt.sort(var18);
                  Iterator var36 = (Iterator)var18.get(var18.size() / 2);
                  Intrinsics.checkNotNullExpressionValue(var36, "get(...)");
                  float var26 = ((Number)var36).floatValue();
                  if (var18.size() >= 2 || Math.abs(var26) < 0.3F) {
                     var36 = this.marks.iterator();
                     Intrinsics.checkNotNullExpressionValue(var36, "iterator(...)");

                     while (var36.hasNext()) {
                        LaneMemory.Mark var50 = (LaneMemory.Mark)var36.next();
                        Intrinsics.checkNotNullExpressionValue(var50, "next(...)");
                        var50 = var50;
                        var50.setY(var50.getY() + var26);
                     }
                  }
               }

               HashSet var38 = new HashSet();
               Iterator var41 = var40.iterator();
               Intrinsics.checkNotNullExpressionValue(var41, "iterator(...)");

               while (var41.hasNext()) {
                  LaneMemory.Mark var52 = (LaneMemory.Mark)var41.next();
                  Intrinsics.checkNotNullExpressionValue(var52, "next(...)");
                  var52 = (Triple)var52;
                  float var11 = ((Number)var52.component1()).floatValue();
                  var6 = (Boolean)var52.component2();
                  float var29 = ((Number)var52.component3()).floatValue();
                  float var27;
                  if (var6) {
                     var27 = 0.8F;
                  } else {
                     var27 = 0.45F;
                  }

                  var52 = this.nearest(var11, var6, var27);
                  if (var52 == null || var38.contains(var52)) {
                     var52 = null;
                  }

                  if (var52 != null) {
                     float var12 = var52.getY();
                     if (var6) {
                        var27 = 0.1F;
                     } else {
                        var27 = 0.25F;
                     }

                     var52.setY(var12 + var27 * (var11 - var52.getY()));
                     var52.setConf(Math.min(1.0F, var52.getConf() + 0.04F * var29));
                     var52.setLastOdo(var1);
                     var52.setSeen(var52.getSeen() + 1);
                     var38.add(var52);
                  } else if (var29 > 0.6F) {
                     var52 = new LaneMemory.Mark(var11, var6, 0.25F, var1);
                     var52.setSeen(1);
                     this.marks.add(var52);
                     var38.add(var52);
                  }
               }

               Iterator var19 = this.marks.iterator();
               Intrinsics.checkNotNullExpressionValue(var19, "iterator(...)");

               while (var19.hasNext()) {
                  LaneMemory.Mark var42 = (LaneMemory.Mark)var19.next();
                  Intrinsics.checkNotNullExpressionValue(var42, "next(...)");
                  var42 = var42;
                  if (!var38.contains(var42) && !var42.getEdge() && Math.abs(var42.getY()) <= this.lineView) {
                     var42.setConf(var42.getConf() - 0.01F);
                  }
               }

               Iterator var20 = this.marks.iterator();
               Intrinsics.checkNotNullExpressionValue(var20, "iterator(...)");

               while (var20.hasNext()) {
                  LaneMemory.Mark var44 = (LaneMemory.Mark)var20.next();
                  Intrinsics.checkNotNullExpressionValue(var44, "next(...)");
                  var44 = var44;
                  if (!var38.contains(var44) && var44.getEdge()) {
                     if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                        var13 = true;
                     } else {
                        var13 = false;
                     }

                     if (var13 && var44.getY() < 0.0F && var44.getY() > -var4 + 0.8F) {
                        var44.setConf(var44.getConf() - 0.03F);
                     }
                  }
               }

               Iterator var21 = this.marks.iterator();
               Intrinsics.checkNotNullExpressionValue(var21, "iterator(...)");

               while (var21.hasNext()) {
                  LaneMemory.Mark var46 = (LaneMemory.Mark)var21.next();
                  Intrinsics.checkNotNullExpressionValue(var46, "next(...)");
                  var46 = var46;
                  if (!var38.contains(var46) && var46.getEdge()) {
                     if (!Float.isInfinite(var5) && !Float.isNaN(var5)) {
                        var13 = true;
                     } else {
                        var13 = false;
                     }

                     if (var13 && var46.getY() > 0.0F && var46.getY() < var5 - 0.8F) {
                        var46.setConf(var46.getConf() - 0.03F);
                     }
                  }
               }

               this.prune();
               return;
            }

            Pair var34 = (Pair)var17.next();
            float var24 = ((Number)var34.component1()).floatValue();
            float var10 = ((Number)var34.component2()).floatValue();
            if (Float.isInfinite(var24) || Float.isNaN(var24)) {
               var13 = false;
            }

            if (var13 && var10 > 0.5F) {
               var40.add(new Triple<>(var24, false, var10));
            }
         }
      }
   }

   public static final class Mark {
      private float conf;
      private final boolean edge;
      private double lastOdo;
      private int seen;
      private float y;

      public Mark(float var1, boolean var2, float var3, double var4) {
         this.y = var1;
         this.edge = var2;
         this.conf = var3;
         this.lastOdo = var4;
      }

      public final float getConf() {
         return this.conf;
      }

      public final boolean getEdge() {
         return this.edge;
      }

      public final double getLastOdo() {
         return this.lastOdo;
      }

      public final int getSeen() {
         return this.seen;
      }

      public final float getY() {
         return this.y;
      }

      public final void setConf(float var1) {
         this.conf = var1;
      }

      public final void setLastOdo(double var1) {
         this.lastOdo = var1;
      }

      public final void setSeen(int var1) {
         this.seen = var1;
      }

      public final void setY(float var1) {
         this.y = var1;
      }
   }
}
