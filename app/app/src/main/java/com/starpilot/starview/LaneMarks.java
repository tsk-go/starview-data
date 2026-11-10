package com.starpilot.starview;

import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

public final class LaneMarks {
   public static final LaneMarks.Companion Companion = new LaneMarks.Companion(null);
   public static final int DASHED = 2;
   private static final String[] NAMES = new String[]{"?", "none", "dash", "solid"};
   public static final int NONE = 1;
   public static final int SOLID = 3;
   public static final int UNKNOWN = 0;
   private final float[] dbl;
   private volatile String debug;
   private volatile long lastNs;
   private volatile LaneMarks.Mark[] marks;
   private final int[] prof;
   private final int[] profU;
   private final int[] profV;
   private final float[][] score;
   private final int[] tmp;
   private final float[] yel;

   public LaneMarks() {
      LaneMarks.Mark[] var3 = new LaneMarks.Mark[4];
      byte var2 = 0;

      for (int var1 = 0; var1 < 4; var1++) {
         var3[var1] = new LaneMarks.Mark(0, false, false);
      }

      this.marks = var3;
      this.debug = "waiting";
      float[][] var5 = new float[4][];

      for (int var4 = var2; var4 < 4; var4++) {
         var5[var4] = new float[4];
      }

      this.score = var5;
      this.yel = new float[4];
      this.dbl = new float[4];
      this.prof = new int[25];
      this.profU = new int[25];
      this.profV = new int[25];
      this.tmp = new int[25];
   }

   private static final boolean analyze$proj(double[] var0, double var1, double var3, double var5, double[] var7) {
      var0 = Projection.Companion.mulv(var0, var1, var3, var5);
      if (var0[2] < 0.5) {
         return false;
      } else {
         var7[0] = var0[0] / var0[2];
         var7[1] = var0[1] / var0[2];
         return true;
      }
   }

   public final void analyze(
      byte[] var1, byte[] var2, byte[] var3, int var4, int var5, int var6, int var7, int var8, float[] var9, ModelSnapshot var10, float var11, long var12
   ) {
      LaneMarks var54 = this;
      Intrinsics.checkNotNullParameter(var1, "y");
      Intrinsics.checkNotNullParameter(var2, "u");
      Intrinsics.checkNotNullParameter(var3, "v");
      Intrinsics.checkNotNullParameter(var9, "rpy");
      this.lastNs = var12;
      if (var10 != null && ((Object[])var10.getLaneX()).length >= 4) {
         Projection.Companion.Cam var52 = Projection.Companion.camFor(var7, var8, false);
         double var14 = var52.getF();
         double var18 = var52.getCx();
         double var16 = var52.getF();
         double var20 = var52.getCy();
         double[] var57 = new double[]{var14, 0.0, var18, 0.0, var16, var20, 0.0, 0.0, 1.0};
         double[] var55 = Projection.Companion
            .mul(var57, Projection.Companion.mul(Projection.Companion.getVIEW_FROM_DEVICE(), Projection.Companion.rotFromEuler(var9[0], var9[1], var9[2])));
         double[] var117 = new double[2];
         double[] var62 = new double[2];
         StringBuilder var53 = new StringBuilder();
         var14 = 40.0;
         if (var11 > 3.0F) {
            var14 = Math.min(40.0, var11 - 3.0);
         }

         int var30 = 0;

         while (var30 < 4) {
            float[] var56 = var10.getLaneProb();
            boolean var31;
            if (var30 >= 0 && var30 < var56.length) {
               var31 = 1;
            } else {
               var31 = 0;
            }

            float var28 = 0.0F;
            if (var31) {
               var11 = var56[var30];
            } else {
               var11 = 0.0F;
            }

            float[] var58 = var10.getLaneX()[var30];
            float[] var60 = var10.getLaneY()[var30];
            float[] var59 = var10.getLaneZ()[var30];
            int var33 = 0;
            int var36 = 0;
            int var34 = 0;
            int var35 = 0;
            var31 = 0;
            int var32 = 0;
            double[] var119;
            StringBuilder var120;
            LaneMarks var126;
            if (var11 > 0.12F && var58.length > 4) {
               var16 = 6.0;
               int var37 = 1;
               byte var93 = -1;
               var35 = 0;
               var119 = var55;

               while (var16 <= var14) {
                  int var40 = var37;

                  while (var40 < var58.length - 1 && var58[var40] < var16) {
                     var40++;
                  }

                  if (var58[var40] < var16) {
                     break;
                  }

                  var18 = RangesKt.coerceIn((var16 - var58[var40 - 1]) / Math.max(0.01F, var58[var40] - var58[var40 - 1]), 0.0, 1.0);
                  var20 = var60[var40 - 1] + (var60[var40] - var60[var40 - 1]) * var18;
                  var18 = var59[var40 - 1] + (var59[var40] - var59[var40 - 1]) * var18;
                  double[] var63;
                  byte var79;
                  double[] var118;
                  if (analyze$proj(var119, var16, var20, var18, var117) && analyze$proj(var119, var16, var20 + 0.5, var18, var62)) {
                     var20 = (var62[0] - var117[0]) * 2.0;
                     double var24 = var62[1];
                     double var22 = var117[1];
                     if (Math.abs(var20) * 0.1 >= 1.5) {
                        int var80 = 1;

                        for (int var106 = 0; var106 < 25; var106++) {
                           double var26 = (var106 - 12) * 0.04;
                           int var41 = (int)(var117[0] + var26 * var20);
                           int var38 = (int)(var117[1] + var26 * ((var24 - var22) * 2.0));
                           if (var41 < 2 || var38 < 2 || var41 >= var7 - 2 || var38 >= var8 - 2) {
                              var80 = 0;
                              break;
                           }

                           this.prof[var106] = var1[var38 * var4 + var41] & 255;
                           var38 = (var38 >> 1) * var5 + (var41 >> 1) * var6;
                           this.profU[var106] = var2[var38] & 255;
                           this.profV[var106] = var3[var38] & 255;
                        }

                        if (var80) {
                           int var47 = var33 + 1;
                           int var42 = 0;

                           for (int var94 = 0; var94 < 25; var94++) {
                              if (Math.abs(var94 - 12) >= 8) {
                                 this.tmp[var42] = this.prof[var94];
                                 var42++;
                              }
                           }

                           Arrays.sort(this.tmp, 0, var42);
                           var80 = this.tmp[var42 / 2];
                           int var48 = Math.max(18, var80 * 12 / 100);
                           int var111 = 0;
                           var33 = 5;
                           var37 = 0;
                           int var110 = 0;

                           while (var33 <= 19) {
                              if (this.prof[var33] - var80 > var48) {
                                 int var43 = var33;

                                 while (var43 + 1 <= 19 && this.prof[var43 + 1] - var80 > var48) {
                                    var43++;
                                 }

                                 if (var43 - var33 + 1 <= 6) {
                                    int var49 = var111 + 1;
                                    if (var33 <= var43) {
                                       int var44 = var33;

                                       while (true) {
                                          int var45 = var37 + 1;
                                          var37 = this.profU[var44];
                                          var33 = var80;
                                          int var46 = var110 + 128 - var37 + (this.profV[var44] - 128);
                                          var111 = var49;
                                          var37 = var45;
                                          var110 = var46;
                                          var80 = var80;
                                          if (var44 == var43) {
                                             break;
                                          }

                                          var44++;
                                          var80 = var33;
                                          var37 = var45;
                                          var110 = var46;
                                       }
                                    } else {
                                       var111 = var49;
                                    }
                                 }

                                 Unit var122 = Unit.INSTANCE;
                                 var33 = var43 + 1;
                              } else {
                                 var33++;
                              }
                           }

                           boolean var112;
                           if (var111 > 0) {
                              var112 = true;
                           } else {
                              var112 = false;
                           }

                           if (!var112) {
                              var33 = var35;
                           } else {
                              int var116 = var36 + 1;
                              int var113 = 0;
                              var36 = 0;
                              var80 = 0;

                              while (var80 < 25) {
                                 int var115 = var113;
                                 int var114 = var36;
                                 if (Math.abs(var80 - 12) >= 8) {
                                    var115 = var113 + 128 - this.profU[var80] + (this.profV[var80] - 128);
                                    var114 = var36 + 1;
                                 }

                                 var80++;
                                 var113 = var115;
                                 var36 = var114;
                              }

                              var80 = var31;
                              if (var110 / var37 - var113 / Math.max(1, var36) > 22) {
                                 var80 = var31 + 1;
                              }

                              var36 = var116;
                              var33 = var35;
                              var31 = var80;
                              if (var111 >= 2) {
                                 var33 = var35 + 1;
                                 var36 = var116;
                                 var31 = var80;
                              }
                           }

                           if (var112) {
                              var79 = 1;
                           } else {
                              var79 = 0;
                           }

                           if (var93 >= 0) {
                              var35 = var34;
                              if (var79 != var93) {
                                 var35 = var34 + 1;
                              }
                           } else {
                              var35 = var34;
                           }

                           var37 = var47;
                           var34 = var35;
                           var35 = var33;
                           var63 = var117;
                           var118 = var62;
                        } else {
                           var118 = var62;
                           var79 = var93;
                           var37 = var33;
                           var63 = var117;
                        }
                     } else {
                        var118 = var62;
                        var79 = var93;
                        var37 = var33;
                        var63 = var117;
                     }
                  } else {
                     var79 = var93;
                     var118 = var62;
                     var63 = var117;
                     var37 = var33;
                  }

                  var16 += 0.5;
                  var54 = this;
                  var62 = var118;
                  var117 = var63;
                  var93 = var79;
                  var33 = var37;
                  var37 = var40;
               }

               var32 = var30;
               var126 = var54;
               var34 = var36;
               var36 = var34;
               var35 = var31;
               var31 = var35;
               var120 = var53;
            } else {
               var33 = 0;
               var34 = 0;
               var36 = 0;
               var120 = var53;
               var119 = var55;
               var31 = var32;
               var32 = var30;
               var126 = var54;
            }

            var56 = var126.score[var32];
            if (var33 < 8) {
               if (var11 <= 0.12F) {
                  for (int var86 = 1; var86 < 4; var86++) {
                     var56[var86] *= 0.95F;
                  }
               }
            } else {
               var11 = (float)var34 / var33;
               byte var85;
               if (var11 < 0.12F) {
                  var85 = 1;
               } else if (!(var11 > 0.75F) && (var36 >= 2 || !(var11 > 0.5F))) {
                  var85 = 2;
               } else {
                  var85 = 3;
               }

               var36 = 1;

               while (true) {
                  var11 = 1.0F;
                  if (var36 >= 4) {
                     if (var34 >= 3) {
                        var58 = var126.yel;
                        float var77 = var58[var32];
                        if (var35 * 2 > var34) {
                           var11 = 1.0F;
                        } else {
                           var11 = 0.0F;
                        }

                        var58[var32] = var77 + (var11 - var126.yel[var32]) * 0.1F;
                        var58 = var126.dbl;
                        var77 = var58[var32];
                        var11 = var28;
                        if (var31 * 5 > var34 * 2) {
                           var11 = 1.0F;
                        }

                        var58[var32] = var77 + (var11 - var126.dbl[var32]) * 0.1F;
                     }
                     break;
                  }

                  float var29 = var56[var36];
                  if (var36 != var85) {
                     var11 = 0.0F;
                  }

                  var56[var36] = var29 + (var11 - var56[var36]) * 0.1F;
                  var36++;
               }
            }

            var31 = 0;
            var28 = 0.45F;
            var30 = 1;

            while (var30 < 4) {
               var11 = var28;
               if (var56[var30] > var28) {
                  var11 = var56[var30];
                  var31 = var30;
               }

               var30++;
               var28 = var11;
            }

            LaneMarks.Mark var139 = var126.marks[var32];
            var30 = var31;
            if (var139.getKind() != 0) {
               var30 = var31;
               if (var31 != var139.getKind()) {
                  var30 = var31;
                  if (var56[var139.getKind()] > 0.3F) {
                     var30 = var31;
                     if (var28 - var56[var139.getKind()] < 0.25F) {
                        var30 = var139.getKind();
                     }
                  }
               }
            }

            boolean var50;
            if (var139.getYellow() ? !(var126.yel[var32] > 0.35F) : !(var126.yel[var32] > 0.65F)) {
               var50 = false;
            } else {
               var50 = true;
            }

            boolean var51;
            if (var139.getDouble() ? !(var126.dbl[var32] > 0.35F) : !(var126.dbl[var32] > 0.65F)) {
               var51 = false;
            } else {
               var51 = true;
            }

            LaneMarks.Mark[] var131 = var126.marks;
            Object[] var132 = Arrays.copyOf(var131, var131.length);
            Intrinsics.checkNotNullExpressionValue(var132, "copyOf(...)");
            LaneMarks.Mark[] var133 = (LaneMarks.Mark[])var132;
            var133[var32] = new LaneMarks.Mark(var30, var50, var51);
            var126.marks = var133;
            LaneMarks.Mark var134 = var126.marks[var32];
            var120.append(new String[]{"LL", "L", "R", "RR"}[var32]).append(':').append(NAMES[var134.getKind()]);
            if (var134.getKind() >= 2) {
               if (var134.getDouble()) {
                  var120.append(" dbl");
               }

               String var135;
               if (var134.getYellow()) {
                  var135 = " yel";
               } else {
                  var135 = " wht";
               }

               var120.append(var135);
            }

            var120.append(" (").append(var33).append(")  ");
            var30 = var32 + 1;
            var54 = var126;
            var55 = var119;
            var53 = var120;
         }

         String var61 = var53.toString();
         Intrinsics.checkNotNullExpressionValue(var61, "toString(...)");
         var54.debug = StringsKt.trim(var61).toString();
      }
   }

   public final String getDebug() {
      return this.debug;
   }

   public final long getLastNs() {
      return this.lastNs;
   }

   public final LaneMarks.Mark[] getMarks() {
      return this.marks;
   }

   public final boolean wants(long var1) {
      boolean var3;
      if (var1 - this.lastNs > 200000000L) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }

   public static final class Mark {
      private final boolean double_;
      private final int kind;
      private final boolean yellow;

      public Mark(int var1, boolean var2, boolean var3) {
         this.kind = var1;
         this.yellow = var2;
         this.double_ = var3;
      }

      public final boolean getDouble() {
         return this.double_;
      }

      public final int getKind() {
         return this.kind;
      }

      public final boolean getYellow() {
         return this.yellow;
      }
   }
}
