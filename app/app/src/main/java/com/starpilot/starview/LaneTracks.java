package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KFunction;

public final class LaneTracks {
   public static final LaneTracks.Companion Companion = new LaneTracks.Companion(null);
   public static final float HIGH_EFFORT = 0.65F;
   public static final long MARK_HOLD_MS = 4000L;
   public static final float MATCH_M = 1.5F;
   public static final float MAX_LAT_ACCEL = 3.0F;
   private static final int ORANGE = -34786;
   public static final float OUTER_LINE_PROB = 0.3F;
   private static final int RED = -1364701;
   private boolean approaching;
   private float changeAlpha;
   private int changeDir;
   private boolean crossed;
   private float effort;
   private float laneWidth;
   private long lastFrame;
   private final ArrayList<LaneTracks.Line> lines;
   private LaneTracks.Line refAhead;
   private LaneTracks.Line refLeft;
   private LaneTracks.Line refRight;
   private final float smoothRc;

   // $VF: synthetic method
   public static float[] $r8$lambda$CYZDaP1CfyyCTqPKhxJjXKnvn0g(LaneTracks.Line var0) {
      return targetCenter$lambda$11(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$kkuIaK_Zo38UEFqx3FXMQPM9gCk(LaneTracks.Line var0) {
      return update$lambda$5(var0);
   }

   public LaneTracks() {
      this(0.0F, 1, null);
   }

   public LaneTracks(float var1) {
      this.smoothRc = var1;
      this.lines = new ArrayList<>();
      this.lastFrame = -1L;
      this.laneWidth = 3.5F;
   }

   // $VF: synthetic method
   public LaneTracks(float var1, int var2, DefaultConstructorMarker var3) {
      if ((var2 & 1) != 0) {
         var1 = 0.25F;
      }

      this(var1);
   }

   private static final float targetCenter$at(Function1<? super LaneTracks.Line, float[]> var0, float var1, LaneTracks.Line var2) {
      float[] var5 = (float[])var0.invoke(var2);
      float[] var4 = var2.getX();
      if (var4.length >= 2 && var5.length == var4.length) {
         int var3 = 1;

         while (var3 < var4.length - 1 && var4[var3] < var1) {
            var3++;
         }

         var1 = RangesKt.coerceIn((var1 - var4[var3 - 1]) / Math.max(0.01F, var4[var3] - var4[var3 - 1]), 0.0F, 1.0F);
         return var5[var3 - 1] + (var5[var3] - var5[var3 - 1]) * var1;
      } else {
         return var2.at(var1);
      }
   }

   // $VF: synthetic method
   public static float targetCenter$default(LaneTracks var0, float var1, Function1 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = new LaneTracks$$ExternalSyntheticLambda1();
      }

      return var0.targetCenter(var1, (Function1<? super LaneTracks.Line, float[]>)var2);
   }

   private static final float[] targetCenter$lambda$11(LaneTracks.Line var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.getY();
   }

   // $VF: synthetic method
   public static void update$default(
      LaneTracks var0, ModelSnapshot var1, Telemetry var2, float var3, boolean var4, LaneMarks.Mark[] var5, int var6, Object var7
   ) {
      if ((var6 & 16) != 0) {
         var5 = null;
      }

      var0.update(var1, var2, var3, var4, var5);
   }

   private static final boolean update$lambda$5(LaneTracks.Line var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      boolean var1;
      if (!var0.getSeen() && var0.getAlpha() < 0.02F) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private final void updateLaneChange(ModelSnapshot var1, float var2) {
      boolean var7 = var1.getLaneChanging();
      boolean var8 = true;
      boolean var5;
      if (var7 && var1.getLaneChangeDir() != 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      float var4 = this.changeAlpha;
      float var3;
      if (var5) {
         var3 = 1.0F;
      } else {
         var3 = 0.0F;
      }

      this.changeAlpha = var4 + (var3 - this.changeAlpha) * var2;
      Iterable var10 = this.lines;
      var var9 = new ArrayList();

      for (Object var11 : var10) {
         LaneTracks.Line var35 = (LaneTracks.Line)var11;
         boolean var6;
         if (var35.getSeen() && var35.getY0() < 0.0F) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var6) {
            var9.add(var11);
         }
      }

      List var28 = var9;
      Iterator var13 = var28.iterator();
      var7 = var13.hasNext();
      Object var40 = null;
      Object var29;
      if (!var7) {
         var29 = null;
      } else {
         var29 = (Collection)var13.next();
         if (var13.hasNext()) {
            var2 = ((LaneTracks.Line)var29).getY0();
            var10 = var29;

            do {
               Object var42 = var13.next();
               var4 = ((LaneTracks.Line)var42).getY0();
               var29 = var10;
               var3 = var2;
               if (Float.compare(var2, var4) < 0) {
                  var29 = (Collection)var42;
                  var3 = var4;
               }

               var10 = var29;
               var2 = var3;
            } while (var13.hasNext());
         }
      }

      LaneTracks.Line var43 = (LaneTracks.Line)var29;
      var10 = this.lines;
      var29 = new ArrayList();

      for (Object var14 : var10) {
         LaneTracks.Line var44 = (LaneTracks.Line)var14;
         boolean var24;
         if (var44.getSeen() && var44.getY0() >= 0.0F) {
            var24 = true;
         } else {
            var24 = false;
         }

         if (var24) {
            var29.add(var14);
         }
      }

      List var31 = (List)var29;
      var13 = var31.iterator();
      if (!var13.hasNext()) {
         var29 = (Collection)var40;
      } else {
         var29 = (Collection)var13.next();
         if (var13.hasNext()) {
            var2 = ((LaneTracks.Line)var29).getY0();
            var10 = var29;

            do {
               var40 = var13.next();
               var4 = ((LaneTracks.Line)var40).getY0();
               var3 = var2;
               var29 = var10;
               if (Float.compare(var2, var4) > 0) {
                  var3 = var4;
                  var29 = (Collection)var40;
               }

               var2 = var3;
               var10 = var29;
            } while (var13.hasNext());
         }
      }

      LaneTracks.Line var33 = (LaneTracks.Line)var29;
      if (var43 != null && var33 != null) {
         this.refLeft = var43;
         this.refRight = var33;
         if (!var5) {
            this.approaching = false;
            this.crossed = false;
            this.laneWidth = RangesKt.coerceIn(var33.getY0() - var43.getY0(), 2.5F, 4.5F);
         } else {
            this.changeDir = var1.getLaneChangeDir();
            Pair var15;
            if (this.changeDir < 0) {
               var15 = TuplesKt.to(var43, var33);
            } else {
               var15 = TuplesKt.to(var33, var43);
            }

            LaneTracks.Line var34 = (LaneTracks.Line)var15.component1();
            LaneTracks.Line var16 = (LaneTracks.Line)var15.component2();
            if (Math.abs(var34.getY0()) < Math.abs(var16.getY0())) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (this.crossed || this.approaching && !var5) {
               var7 = true;
            } else {
               var7 = false;
            }

            this.crossed = var7;
            var7 = var8;
            if (!this.approaching) {
               if (var5) {
                  var7 = var8;
               } else {
                  var7 = false;
               }
            }

            this.approaching = var7;
            this.refAhead = var34;
         }
      }
   }

   public final float getChangeAlpha() {
      return this.changeAlpha;
   }

   public final int getChangeDir() {
      return this.changeDir;
   }

   public final boolean getCrossed() {
      return this.crossed;
   }

   public final float getEffort() {
      return this.effort;
   }

   public final float getLaneWidth() {
      return this.laneWidth;
   }

   public final ArrayList<LaneTracks.Line> getLines() {
      return this.lines;
   }

   public final Triple<LaneTracks.Line, LaneTracks.Line, LaneTracks.Line> inner() {
      Iterable var7 = this.lines;
      var var6 = new ArrayList();
      Iterator var26 = var7.iterator();

      while (true) {
         boolean var5 = var26.hasNext();
         boolean var4 = true;
         if (!var5) {
            List var20 = var6;
            Iterator var10 = var20.iterator();
            var5 = var10.hasNext();
            Object var32 = null;
            Object var21;
            if (!var5) {
               var21 = null;
            } else {
               var21 = (Collection)var10.next();
               if (var10.hasNext()) {
                  float var1 = ((LaneTracks.Line)var21).getY0();
                  Object var27 = var21;

                  do {
                     Object var34 = var10.next();
                     float var3 = ((LaneTracks.Line)var34).getY0();
                     var21 = (Collection)var27;
                     float var2 = var1;
                     if (Float.compare(var1, var3) < 0) {
                        var21 = (Collection)var34;
                        var2 = var3;
                     }

                     var27 = var21;
                     var1 = var2;
                  } while (var10.hasNext());
               }
            }

            LaneTracks.Line var38 = (LaneTracks.Line)var21;
            var7 = this.lines;
            var21 = new ArrayList();

            for (Object var29 : var7) {
               LaneTracks.Line var11 = (LaneTracks.Line)var29;
               if (var11.getAlpha() > 0.05F && var11.getY0() >= 0.0F) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               if (var4) {
                  var21.add(var29);
               }
            }

            List var23 = (List)var21;
            Iterator var39 = var23.iterator();
            if (!var39.hasNext()) {
               var21 = null;
            } else {
               var21 = (Collection)var39.next();
               if (var39.hasNext()) {
                  float var14 = ((LaneTracks.Line)var21).getY0();
                  Object var30 = var21;

                  do {
                     Object var36 = var39.next();
                     float var16 = ((LaneTracks.Line)var36).getY0();
                     var21 = (Collection)var30;
                     float var12 = var14;
                     if (Float.compare(var14, var16) > 0) {
                        var21 = (Collection)var36;
                        var12 = var16;
                     }

                     var30 = var21;
                     var14 = var12;
                  } while (var39.hasNext());
               }
            }

            LaneTracks.Line var37 = (LaneTracks.Line)var21;
            var39 = CollectionsKt.listOfNotNull(new LaneTracks.Line[]{var38, var37}).iterator();
            if (!var39.hasNext()) {
               var21 = (Collection)var32;
            } else {
               var21 = (Collection)var39.next();
               if (var39.hasNext()) {
                  float var13 = Math.abs(((LaneTracks.Line)var21).getY0());
                  Object var31 = var21;

                  do {
                     var32 = var39.next();
                     float var17 = Math.abs(((LaneTracks.Line)var32).getY0());
                     float var15 = var13;
                     var21 = (Collection)var31;
                     if (Float.compare(var13, var17) > 0) {
                        var21 = (Collection)var32;
                        var15 = var17;
                     }

                     var13 = var15;
                     var31 = var21;
                  } while (var39.hasNext());
               }
            }

            return new Triple<>(var38, var37, (LaneTracks.Line)var21);
         }

         Object var8 = var26.next();
         LaneTracks.Line var9 = (LaneTracks.Line)var8;
         if (!(var9.getAlpha() > 0.05F) || !(var9.getY0() < 0.0F)) {
            var4 = false;
         }

         if (var4) {
            var6.add(var8);
         }
      }
   }

   public final float targetCenter(float var1, Function1<? super LaneTracks.Line, float[]> var2) {
      Intrinsics.checkNotNullParameter(var2, "shown");
      LaneTracks.Line var3 = this.refLeft;
      LaneTracks.Line var4 = this.refRight;
      if (this.changeDir == 0 || var3 == null || var4 == null) {
         return Float.NaN;
      } else if (this.crossed) {
         return (targetCenter$at(var2, var1, var3) + targetCenter$at(var2, var1, var4)) / 2.0F;
      } else {
         var3 = this.refAhead;
         return var3 == null ? Float.NaN : targetCenter$at(var2, var1, var3) + this.changeDir * this.laneWidth / 2.0F;
      }
   }

   public final void update(ModelSnapshot var1, Telemetry var2, float var3, boolean var4, LaneMarks.Mark[] var5) {
      Intrinsics.checkNotNullParameter(var1, "m");
      Intrinsics.checkNotNullParameter(var2, "t");
      if (var1.getFrameId() != this.lastFrame) {
         this.lastFrame = var1.getFrameId();
         float var7 = RangesKt.coerceIn(var3 / (this.smoothRc + var3), 0.0F, 1.0F);
         float var6;
         if (!var4) {
            var6 = 1.0F;
         } else if (var2.getVEgo() < 0.5F) {
            var6 = 0.0F;
         } else if (var2.getVEgo() < 3.0F) {
            var6 = 0.3F * var7;
         } else {
            var6 = var7;
         }

         Iterator var19 = this.lines.iterator();
         Intrinsics.checkNotNullExpressionValue(var19, "iterator(...)");

         while (var19.hasNext()) {
            LaneTracks.Line var20 = (LaneTracks.Line)var19.next();
            Intrinsics.checkNotNullExpressionValue(var20, "next(...)");
            var20 = var20;
            var20.setSeen(false);
            var20.setSlot(-1);
         }

         long var17 = System.currentTimeMillis();
         int var14 = 0;
         int var13 = Math.min(4, ((Object[])var1.getLaneX()).length);
         float var8 = var6;

         while (true) {
            LaneTracks.Line var41 = null;
            Object var21 = null;
            if (var14 >= var13) {
               Iterator var32 = this.lines.iterator();
               Intrinsics.checkNotNullExpressionValue(var32, "iterator(...)");

               while (var32.hasNext()) {
                  LaneTracks.Line var60;
                  label145: {
                     Object var59 = var32.next();
                     Intrinsics.checkNotNullExpressionValue(var59, "next(...)");
                     var60 = (LaneTracks.Line)var59;
                     if (var60.getSeen()) {
                        boolean var37;
                        if (var60.getOn() ? !(var60.getProb() > 0.04F) : !(var60.getProb() > 0.1F)) {
                           var37 = false;
                        } else {
                           var37 = true;
                        }

                        if (var37) {
                           var4 = true;
                           break label145;
                        }
                     }

                     var4 = false;
                  }

                  var60.setOn(var4);
                  if (var60.getOn()) {
                     var6 = RangesKt.coerceIn(var60.getProb() * 2.0F, 0.4F, 1.0F);
                  } else {
                     var6 = 0.0F;
                  }

                  if (var6 > var60.getAlpha()) {
                     var8 = var7;
                  } else {
                     var8 = var3 / (var3 + 1.0F);
                  }

                  var60.setAlpha(var60.getAlpha() + (var6 - var60.getAlpha()) * var8);
               }

               CollectionsKt.removeAll(this.lines, new LaneTracks$$ExternalSyntheticLambda0());
               this.updateLaneChange(var1, var7);
               Float var33 = var1.getModelDesiredCurvature();
               boolean var38;
               if (var33.floatValue() == 0.0F) {
                  var38 = true;
               } else {
                  var38 = false;
               }

               Float var26 = var41;
               if (!var38) {
                  var26 = var33;
               }

               if (var26 != null) {
                  var3 = var26;
               } else {
                  var3 = var2.getDesiredCurvature();
               }

               var3 = Math.abs(var3) * var2.getVEgo() * var2.getVEgo() / 3.0F;
               if (Companion.pathState(var2) == LaneTracks.PathState.ENGAGED) {
                  var3 = RangesKt.coerceIn(Math.max(Math.abs(var2.getOutTorque()), var3), 0.0F, 1.0F);
               } else {
                  var3 = 0.0F;
               }

               this.effort = this.effort + (var3 - this.effort) * var7;
               return;
            }

            float[] var23 = var1.getLaneX()[var14];
            if (var23.length >= 2 && var14 < ((Object[])var1.getLaneY()).length) {
               var41 = var1.getLaneProb();
               boolean var15;
               if (var14 >= 0 && var14 < ((Object[])var41).length) {
                  var15 = 1;
               } else {
                  var15 = 0;
               }

               float var9;
               if (var15) {
                  var9 = (float)((Object[])var41)[var14];
               } else {
                  var9 = 0.0F;
               }

               if (((Object[])var1.getLaneX()).length != 4 || var14 != 0 && var14 != 3 || Companion.roadThere(var1, var14, var9)) {
                  float[] var50 = var1.getLaneY()[var14];
                  var41 = (Object[])var1.getLaneZ();
                  if (var14 >= 0 && var14 < ((Object[])var41).length) {
                     var15 = (boolean)1;
                  } else {
                     var15 = (boolean)0;
                  }

                  if (var15) {
                     var41 = ((Object[])var41)[var14];
                  } else {
                     var41 = new float[var23.length];
                  }

                  var41 = new LaneTracks.Line(var23, var50, (float[])var41);
                  var6 = var41.getY0();
                  Iterable var51 = this.lines;
                  var var22 = new ArrayList();

                  for (Object var25 : var51) {
                     if (!((LaneTracks.Line)var25).getSeen()) {
                        var22.add(var25);
                     }
                  }

                  List var52 = var22;
                  Iterable var53 = var52;
                  var4 = false;
                  Iterator var64 = var53.iterator();
                  if (!var64.hasNext()) {
                     var50 = null;
                  } else {
                     var50 = (float[])var64.next();
                     if (var64.hasNext()) {
                        float var10 = Math.abs(((LaneTracks.Line)var50).getY0() - var6);

                        while (true) {
                           Object var61 = var64.next();
                           float var12 = Math.abs(((LaneTracks.Line)var61).getY0() - var6);
                           float var11 = var10;
                           if (Float.compare(var10, var12) > 0) {
                              var50 = (float[])var61;
                              var11 = var12;
                           }

                           if (!var64.hasNext()) {
                              break;
                           }

                           var10 = var11;
                        }
                     }
                  }

                  LaneTracks.Line var55 = (LaneTracks.Line)var50;
                  if (var55 != null && Math.abs(var55.getY0() - var6) < 1.5F && var55.getY().length == var41.getY().length) {
                     var15 = 0;

                     for (int var16 = var55.getY().length; var15 < var16; var15++) {
                        float[] var62 = var55.getY();
                        var62[var15] += (var41.getY()[var15] - var55.getY()[var15]) * var8;
                        float[] var63 = var55.getZ();
                        var63[var15] += (var41.getZ()[var15] - var55.getZ()[var15]) * var8;
                     }

                     var55.setX(var23);
                     var41 = var55;
                  } else {
                     var50 = var41.getY();
                     var50 = Arrays.copyOf(var50, var50.length);
                     Intrinsics.checkNotNullExpressionValue(var50, "copyOf(...)");
                     float[] var46 = var41.getZ();
                     float[] var47 = Arrays.copyOf(var46, var46.length);
                     Intrinsics.checkNotNullExpressionValue(var47, "copyOf(...)");
                     var41 = new LaneTracks.Line(var23, var50, var47);
                     this.lines.add(var41);
                  }

                  var41.setSeen(true);
                  var41.setSlot(var14);
                  var41.setProb(var41.getProb() + (var9 - var41.getProb()) * var7);
                  LaneMarks.Mark var58 = (LaneMarks.Mark)var21;
                  if (var5 != null) {
                     var58 = ArraysKt.getOrNull(var5, var14);
                  }

                  if (var58 == null || var58.getKind() != 2 && var58.getKind() != 3) {
                     if (var58 != null && var58.getKind() == 1 && var17 - var41.getMarkMs() > 4000L) {
                        var41.setMark(var58);
                        var41.setMarkMs(var17);
                     }
                  } else {
                     var41.setMark(var58);
                     var41.setMarkMs(var17);
                  }
               }
            }

            var14++;
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

      private static final int blend$ch(int var0, int var1, float var2, int var3) {
         var0 = var0 >>> var3 & 0xFF;
         return RangesKt.coerceIn((int)(var0 + ((var1 >>> var3 & 0xFF) - var0) * var2 + 0.5F), 0, 255) << var3;
      }

      private static final float roadThere$y5(float[] var0, float[] var1) {
         if (var0.length >= 2 && var1.length == var0.length) {
            int var3 = 1;

            while (var3 < var0.length - 1 && var0[var3] < 5.0F) {
               var3++;
            }

            float var2 = RangesKt.coerceIn((5.0F - var0[var3 - 1]) / Math.max(0.01F, var0[var3] - var0[var3 - 1]), 0.0F, 1.0F);
            return var1[var3 - 1] + (var1[var3] - var1[var3 - 1]) * var2;
         } else {
            return Float.NaN;
         }
      }

      public final int blend(int var1, int var2, float var3) {
         var3 = RangesKt.coerceIn(var3, 0.0F, 1.0F);
         return blend$ch(var1, var2, var3, 24) | blend$ch(var1, var2, var3, 16) | blend$ch(var1, var2, var3, 8) | blend$ch(var1, var2, var3, 0);
      }

      public final int effortColor(int var1, float var2) {
         var var3 = (KFunction & Function3)(new Function3<Integer, Integer, Float, Integer>(this) {
            public final Integer invoke(int var1, int var2x, float var3x) {
               return ((LaneTracks.Companion)this.receiver).blend(var1, var2x, var3x);
            }
         });
         Integer var4 = -34786;
         if (var2 < 0.65F) {
            var1 = ((Number)((Function3)var3).invoke(var1, var4, RangesKt.coerceIn((var2 - 0.65F + 0.1F) / 0.1F, 0.0F, 1.0F))).intValue();
         } else {
            var1 = ((Number)((Function3)var3).invoke(var4, -1364701, RangesKt.coerceIn((var2 - 0.65F) / 0.35000002F, 0.0F, 1.0F))).intValue();
         }

         return var1;
      }

      public final LaneTracks.PathState pathState(Telemetry var1) {
         Intrinsics.checkNotNullParameter(var1, "t");
         LaneTracks.PathState var2;
         if (!var1.getEnabled() && !var1.getAolEnabled()) {
            var2 = LaneTracks.PathState.OFF;
         } else if (var1.getSteeringPressed() || Intrinsics.areEqual(var1.getOpState(), "overriding") || var1.getEnabled() && !var1.getActive()) {
            var2 = LaneTracks.PathState.OVERRIDE;
         } else {
            var2 = LaneTracks.PathState.ENGAGED;
         }

         return var2;
      }

      public final boolean roadThere(ModelSnapshot var1, int var2, float var3) {
         Intrinsics.checkNotNullParameter(var1, "m");
         boolean var7 = false;
         if (var3 < 0.3F) {
            return false;
         } else if (((Object[])var1.getEdgeY()).length >= 2 && ((Object[])var1.getEdgeX()).length >= 2) {
            float var4 = roadThere$y5(var1.getEdgeX()[0], var1.getEdgeY()[0]);
            var3 = roadThere$y5(var1.getEdgeX()[1], var1.getEdgeY()[1]);
            boolean var6;
            if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6) {
               if (!Float.isInfinite(var3) && !Float.isNaN(var3)) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               if (var6 && !(var3 - var4 < 2.0F)) {
                  float var5 = roadThere$y5(var1.getLaneX()[var2], var1.getLaneY()[var2]);
                  if (!Float.isInfinite(var5) && !Float.isNaN(var5)) {
                     var6 = true;
                  } else {
                     var6 = false;
                  }

                  if (!var6) {
                     return true;
                  }

                  if (var2 == 0 ? var5 > var4 : var5 < var3) {
                     var7 = true;
                  }

                  return var7;
               }
            }

            return true;
         } else {
            return true;
         }
      }
   }

   public static final class Line {
      private float alpha;
      private LaneMarks.Mark mark;
      private long markMs;
      private boolean on;
      private float prob;
      private boolean seen;
      private int slot;
      private float[] x;
      private float[] y;
      private float[] z;

      public Line(float[] var1, float[] var2, float[] var3) {
         Intrinsics.checkNotNullParameter(var1, "x");
         Intrinsics.checkNotNullParameter(var2, "y");
         Intrinsics.checkNotNullParameter(var3, "z");
         super();
         this.x = var1;
         this.y = var2;
         this.z = var3;
         this.seen = true;
         this.slot = -1;
      }

      public final float at(float var1) {
         boolean var2;
         if (this.x.length == 0) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         if (var2) {
            return 0.0F;
         } else {
            var2 = 1;

            while (var2 < this.x.length - 1 && this.x[var2] < var1) {
               var2++;
            }

            if (this.x.length < 2) {
               return this.y[0];
            } else {
               var1 = RangesKt.coerceIn((var1 - this.x[var2 - 1]) / Math.max(0.01F, this.x[var2] - this.x[var2 - 1]), 0.0F, 1.0F);
               return this.y[var2 - 1] + (this.y[var2] - this.y[var2 - 1]) * var1;
            }
         }
      }

      public final float getAlpha() {
         return this.alpha;
      }

      public final LaneMarks.Mark getMark() {
         return this.mark;
      }

      public final long getMarkMs() {
         return this.markMs;
      }

      public final boolean getOn() {
         return this.on;
      }

      public final float getProb() {
         return this.prob;
      }

      public final boolean getSeen() {
         return this.seen;
      }

      public final int getSlot() {
         return this.slot;
      }

      public final float[] getX() {
         return this.x;
      }

      public final float[] getY() {
         return this.y;
      }

      public final float getY0() {
         return this.at(5.0F);
      }

      public final float[] getZ() {
         return this.z;
      }

      public final void setAlpha(float var1) {
         this.alpha = var1;
      }

      public final void setMark(LaneMarks.Mark var1) {
         this.mark = var1;
      }

      public final void setMarkMs(long var1) {
         this.markMs = var1;
      }

      public final void setOn(boolean var1) {
         this.on = var1;
      }

      public final void setProb(float var1) {
         this.prob = var1;
      }

      public final void setSeen(boolean var1) {
         this.seen = var1;
      }

      public final void setSlot(int var1) {
         this.slot = var1;
      }

      public final void setX(float[] var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.x = var1;
      }

      public final void setY(float[] var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.y = var1;
      }

      public final void setZ(float[] var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.z = var1;
      }
   }

   public static enum PathState {
      ENGAGED,
      OFF,
      OVERRIDE;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(LaneTracks.PathState.$VALUES);
      private static final LaneTracks.PathState[] $VALUES = $values();

      // $VF: synthetic method
      private static final LaneTracks.PathState[] $values() {
         return new LaneTracks.PathState[]{ENGAGED, OVERRIDE, OFF};
      }

      public static EnumEntries<LaneTracks.PathState> getEntries() {
         return $ENTRIES;
      }
   }
}
