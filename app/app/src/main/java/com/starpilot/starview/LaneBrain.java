package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class LaneBrain {
   public static final LaneBrain.Companion Companion = new LaneBrain.Companion(null);
   public static final int N = 6;
   public static final double SHOW = 0.35;
   private static final double[] W;
   private long bsmMs;
   private final ArrayDeque<double[]> camLog;
   private final ArrayDeque<long[]> carsL;
   private final ArrayDeque<long[]> carsR;
   private long carsUsedMs;
   private double driveS;
   private long entryMs;
   private int entrySide;
   private final ArrayDeque<LaneBrain.Event> events;
   private double heading;
   private final ArrayDeque<double[]> hist;
   private long holdUntil;
   private LaneBrain.RoadInfo info;
   private double knownS;
   private float laneW;
   private double lastBlurOdo;
   private long lastCameraMs;
   private int lastChangeDir;
   private long lastChangeMs;
   private long lastMs;
   private final LaneMemory memory;
   private LaneBrain.RoadModel model;
   private volatile long modelMs;
   private volatile String modelText;
   private final HashMap<Long, Integer> myCounts;
   private int nChanges;
   private int nOpMissed;
   private int nRamps;
   private int nTurns;
   private int nUndone;
   private double odo;
   private Function0<Unit> onCountsChanged;
   private Function1<? super LaneBrain.Event, Unit> onEvent;
   private final ArrayDeque<Long> oncoming;
   private boolean opCounted;
   private int opDir;
   private long opSince;
   private double[][] p = Companion.prior();
   private LaneBrain.Pending pending;
   private String prevAnswer;
   private float prevC;
   private long prevMs;
   private float prevYL;
   private float prevYR;
   private double[][] q;
   private LaneBrain.Road road;
   private long roadChangeMs;
   private long rtkCheckMs;
   private long rtkDoubtMs;
   private final ArrayDeque<double[]> rtkLog;
   private long straightSince;
   private final LaneBrain.Vote vDashL;
   private final LaneBrain.Vote vDashR;
   private final LaneBrain.Vote vEdgeL;
   private final LaneBrain.Vote vEdgeR;
   private final LaneBrain.Vote vOuterL;
   private final LaneBrain.Vote vOuterR;
   private final LaneBrain.Vote vSolidR;
   private final LaneBrain.Vote vYellow;
   private final ArrayDeque<LaneBrain.Event> why;

   static {
      double[] var5 = new double[6];
      byte var3 = 0;

      for (int var2 = 0; var2 < 6; var2++) {
         var5[var2] = Math.pow(0.65, var2);
      }

      double var0 = ArraysKt.sum(var5);
      double[] var4 = new double[6];

      for (int var6 = var3; var6 < 6; var6++) {
         var4[var6] = var5[var6] / var0;
      }

      W = var4;
   }

   public LaneBrain() {
      int var1 = 0;
      this.model = new LaneBrain.RoadModel(0, "", 0.0, 0, 0, "", "", false, false);
      this.events = new ArrayDeque<>();
      this.why = new ArrayDeque<>();
      this.myCounts = new HashMap<>();
      this.memory = new LaneMemory();
      this.hist = new ArrayDeque<>();
      this.laneW = 3.5F;

      double[][] var2;
      for (var2 = new double[6][]; var1 < 6; var1++) {
         var2[var1] = new double[6];
      }

      this.q = var2;
      this.prevAnswer = "";
      this.rtkLog = new ArrayDeque<>();
      this.camLog = new ArrayDeque<>();
      this.vYellow = new LaneBrain.Vote();
      this.vDashL = new LaneBrain.Vote();
      this.vDashR = new LaneBrain.Vote();
      this.vSolidR = new LaneBrain.Vote();
      this.vOuterL = new LaneBrain.Vote();
      this.vOuterR = new LaneBrain.Vote();
      this.vEdgeL = new LaneBrain.Vote();
      this.vEdgeR = new LaneBrain.Vote();
      this.carsL = new ArrayDeque<>();
      this.carsR = new ArrayDeque<>();
      this.oncoming = new ArrayDeque<>();
      this.modelText = "";
   }

   private final void answer() {
      double var1 = 0.0;

      for (int var5 = 0; var5 < 6; var5++) {
         for (int var6 = 0; var6 < 6; var6++) {
            double var3 = this.p[var5][var6] * this.fits(var5 + var6 + 1, this.model);
            this.q[var5][var6] = var3;
            var1 += var3;
         }
      }

      if (var1 > 0.0) {
         for (double[] var9 : this.q) {
            int var12 = 0;

            for (int var8 = var9.length; var12 < var8; var12++) {
               var9[var12] /= var1;
            }
         }
      }
   }

   private final void apply(LaneBrain.Pending var1, String var2) {
      double var5 = this.sideways(var1.getAtMs() - 4000, this.lastMs);
      boolean var10 = Intrinsics.areEqual(var2, "openpilot");
      double var3 = 0.95;
      if (!var10) {
         if (Intrinsics.areEqual(var2, "rtk")) {
            var3 = 0.85;
         } else {
            boolean var7;
            if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
               var7 = true;
            } else {
               var7 = false;
            }

            if (!var7) {
               var3 = 0.9;
            } else if (Math.abs(var5) < 0.3) {
               long var8 = var1.getAtMs();
               String var11 = this.dirName(var1.getDir());
               this.log(var8, "RTK saw no sideways move - lane change " + var11 + " doubted");
               var3 = 0.5;
            }
         }
      }

      this.lastChangeMs = var1.getAtMs();
      this.lastChangeDir = var1.getDir();
      this.nChanges++;
      int var15 = var1.getDir();
      long var16 = var1.getAtMs();
      String var19;
      if (var1.getDir() < 0) {
         var19 = "left";
      } else {
         var19 = "right";
      }

      var10 = Intrinsics.areEqual(var2, "lines");
      String var13 = "";
      String var12;
      if (!var10) {
         var12 = " (" + var2 + ")";
      } else {
         var12 = "";
      }

      this.moved(var15, var3, var16, "lane change " + var19 + var12);
      if (Intrinsics.areEqual(var2, "openpilot")) {
         this.memory.shift(var1.getDir() * this.laneW);
      }

      var16 = var1.getAtMs();
      var19 = this.dirName(var1.getDir());
      String var14 = var13;
      if (!Intrinsics.areEqual(var2, "lines")) {
         var14 = " (" + var2 + ")";
      }

      this.log(var16, "lane change " + var19 + var14);
   }

   private final void atLeast(int var1, int var2, double var3, long var5, String var7) {
      if (var5 >= this.holdUntil || this.side(var1).getFirst().intValue() >= var2) {
         this.lastCameraMs = var5;
         var3 /= 1 - var3;

         for (int var8 = 0; var8 < 6; var8++) {
            for (int var9 = 0; var9 < 6; var9++) {
               if (this.count(var8, var9, var1) >= var2) {
                  double[] var10 = this.p[var8];
                  var10[var9] *= var3;
               }
            }
         }

         this.norm();
         this.note(var5, var7);
      }
   }

   private final void blindSpot(LaneBrain.Frame var1, long var2) {
      if (!(var1.getVEgo() < 8.0F) && (var1.getBsmL() || var1.getBsmR()) && var2 - this.bsmMs >= 1000L) {
         this.bsmMs = var2;
         if (var1.getBsmL()) {
            this.atLeast(-1, 1, 0.6, var2, "car in the left blind spot");
         }

         if (var1.getBsmR()) {
            this.atLeast(1, 1, 0.6, var2, "car in the right blind spot");
         }
      }
   }

   private final void blur(double var1) {
      var1 = RangesKt.coerceIn(var1 / 300.0 * 0.03, 0.0, 0.3);

      for (int var3 = 0; var3 < 6; var3++) {
         for (int var4 = 0; var4 < 6; var4++) {
            this.p[var3][var4] = this.p[var3][var4] * (1 - var1) + Companion.prior(var3, var4) * var1;
         }
      }
   }

   private final void camNote(LaneBrain.Frame var1, long var2) {
      boolean var17;
      label70: {
         if (var1.getPL() > 0.4F && var1.getPR() > 0.4F) {
            float var10 = var1.getYL();
            if (!Float.isInfinite(var10) && !Float.isNaN(var10)) {
               var17 = true;
            } else {
               var17 = false;
            }

            if (var17) {
               var10 = var1.getYR();
               if (!Float.isInfinite(var10) && !Float.isNaN(var10)) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               if (var17 && var1.getYL() < 0.3F && var1.getYR() > -0.3F) {
                  var10 = var1.getYR() - var1.getYL();
                  if (2.3F <= var10 && var10 <= 4.9F) {
                     var17 = true;
                  } else {
                     var17 = false;
                  }

                  if (var17) {
                     var17 = true;
                     break label70;
                  }
               }
            }
         }

         var17 = false;
      }

      ArrayDeque var12 = this.camLog;
      double var8 = var2;
      double var6 = 0.0;
      double var4;
      if (var17) {
         var4 = 1.0;
      } else {
         var4 = 0.0;
      }

      if (var17) {
         var6 = Math.abs((var1.getYL() + var1.getYR()) / 2.0);
      }

      var12.addLast(new double[]{var8, var4, var6});

      while (!this.camLog.isEmpty() && var2 - ((double[])this.camLog.first())[0] > 15000.0) {
         this.camLog.removeFirst();
      }
   }

   private final boolean cameraStayed(long var1, long var3) {
      int var10 = 0;
      int var9 = 0;
      double var5 = 0.0;
      Iterator var13 = this.camLog.iterator();

      while (true) {
         boolean var11 = var13.hasNext();
         boolean var12 = false;
         if (!var11) {
            var11 = var12;
            if (var10 >= 20) {
               var11 = var12;
               if (var9 >= var10 * 0.7) {
                  var11 = var12;
                  if (var5 < 1.0) {
                     var11 = true;
                  }
               }
            }

            return var11;
         }

         double[] var14 = (double[])var13.next();
         if (var14[0] >= var1) {
            double var7 = var14[0];
            if (var7 <= var3) {
               var10++;
               if (var14[1] > 0.5) {
                  var9++;
                  var5 = Math.max(var5, var14[2]);
               }
            }
         }
      }
   }

   private final void cars(LaneBrain.Frame var1, long var2) {
      if (!(var1.getVEgo() < 3.0F)) {
         if (var1.getCarsLeft() > 0) {
            this.carsL.addLast(new long[]{var2, var1.getCarsLeft()});
         }

         if (var1.getCarsRight() > 0) {
            this.carsR.addLast(new long[]{var2, var1.getCarsRight()});
         }

         if (var1.getOncomingLeft()) {
            this.oncoming.addLast(var2);
         }

         while (!this.oncoming.isEmpty() && var2 - this.oncoming.first().longValue() > 2000L) {
            this.oncoming.removeFirst();
         }

         if (var2 - this.carsUsedMs >= 1000L) {
            int var4 = this.held(this.carsL, var2);
            int var5 = this.held(this.carsR, var2);
            if (var4 > 0) {
               String var6;
               if (var4 > 1) {
                  var6 = "s";
               } else {
                  var6 = "";
               }

               this.atLeast(-1, var4, 0.65, var2, "car driving our way " + var4 + " lane" + var6 + " to the left");
            }

            if (var5 > 0) {
               String var7;
               if (var5 > 1) {
                  var7 = "s";
               } else {
                  var7 = "";
               }

               this.atLeast(1, var5, 0.65, var2, "car driving our way " + var5 + " lane" + var7 + " to the right");
            }

            if (this.oncoming.size() >= 6 && var4 == 0) {
               this.see(-1, 0, 0.7, var2, "oncoming traffic in the lane to our left");
            }

            if (var4 > 0 || var5 > 0 || this.oncoming.size() >= 6) {
               this.carsUsedMs = var2;
            }
         }
      }
   }

   private final boolean centred(LaneBrain.Frame var1) {
      float var2 = var1.getYL();
      boolean var5 = Float.isInfinite(var2);
      boolean var4 = true;
      boolean var3;
      if (!var5 && !Float.isNaN(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         var2 = var1.getYR();
         if (!Float.isInfinite(var2) && !Float.isNaN(var2)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3 && Math.abs((var1.getYL() + var1.getYR()) / 2.0F) < 0.6F && this.pending == null && this.opDir == 0) {
            return var4;
         }
      }

      return false;
   }

   // $VF: synthetic method
   public static void check$default(LaneBrain var0, boolean var1, long var2, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = var0.lastMs;
      }

      var0.check(var1, var2);
   }

   private final void checkOpChange(LaneBrain.Frame var1, long var2) {
      if (var1.getOpChange() != 0 && this.opDir == 0) {
         this.opDir = var1.getOpChange();
         this.opSince = var2;
         this.opCounted = false;
         this.pending = null;
      }

      if (this.opDir != 0 && var1.getOpFinishing() && !this.opCounted) {
         this.opCounted = true;
         this.apply(new LaneBrain.Pending(this.opDir, var2), "openpilot");
      }

      if (var1.getOpChange() == 0 && this.opDir != 0) {
         long var6 = var2 - this.opSince;
         if (!this.opCounted) {
            float var4 = var1.getOpLcProb();
            boolean var5;
            if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var5 && var1.getOpLcProb() < 0.1F) {
               if (400L <= var6 && var6 < 10001L) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               if (var5) {
                  this.opCounted = true;
                  this.apply(new LaneBrain.Pending(this.opDir, var2), "openpilot");
               }
            }
         }

         if (!this.opCounted) {
            this.nOpMissed++;
            String var8 = this.dirName(this.opDir);
            this.log(var2, "openpilot lane change " + var8 + " ended without finishing - not counted");
         }

         this.opDir = 0;
         this.prevMs = 0L;
      }
   }

   private final int count(int var1, int var2, int var3) {
      if (var3 >= 0) {
         var1 = var2;
      }

      return var1;
   }

   private final void detectLaneChange(LaneBrain.Frame var1, long var2) {
      if (var1.getOpChange() == 0 && this.opDir == 0) {
         boolean var8;
         label119: {
            if (var1.getPL() > 0.25F && var1.getPR() > 0.25F) {
               float var4 = var1.getYL();
               boolean var7;
               if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                  var7 = true;
               } else {
                  var7 = false;
               }

               if (var7) {
                  var4 = var1.getYR();
                  if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (var7 && var1.getYL() < 0.3F && var1.getYR() > -0.3F) {
                     var4 = var1.getYR() - var1.getYL();
                     if (2.3F <= var4 && var4 <= 4.9F) {
                        var7 = true;
                     } else {
                        var7 = false;
                     }

                     if (var7) {
                        var8 = true;
                        break label119;
                     }
                  }
               }
            }

            var8 = false;
         }

         if (var8) {
            float var5 = var1.getYR() - var1.getYL();
            float var13 = (var1.getYL() + var1.getYR()) / 2.0F;
            if (Math.abs(var13) < 0.6F) {
               this.laneW = this.laneW + (RangesKt.coerceIn(var5, 2.6F, 4.2F) - this.laneW) * 0.02F;
            }

            if (this.prevMs > 0L && var2 - this.prevMs < 700L) {
               float var6 = var13 - this.prevC;
               byte var16;
               if (var6 < -0.55F * var5 && Math.abs(this.prevYL) < 1.3F && Math.abs(var1.getYR() - this.prevYL) < 0.9F) {
                  var16 = -1;
               } else if (var6 > 0.55F * var5 && Math.abs(this.prevYR) < 1.3F && Math.abs(var1.getYL() - this.prevYR) < 0.9F) {
                  var16 = 1;
               } else {
                  var16 = 0;
               }

               if (var16 != 0) {
                  LaneBrain.Pending var9 = this.pending;
                  if (var9 != null && var9.getDir() == -var16 && var2 - var9.getAtMs() < 1500L) {
                     this.pending = null;
                     if (var2 - var9.getAtMs() > 300L) {
                        this.nUndone++;
                        String var17 = this.dirName(var9.getDir());
                        this.log(var2, "lane change " + var17 + " undone - not counted");
                     }
                  } else {
                     if (var9 != null) {
                        this.apply(var9, "lines");
                     }

                     this.pending = new LaneBrain.Pending(var16, var2);
                  }
               }
            }

            this.prevC = var13;
            this.prevYL = var1.getYL();
            this.prevYR = var1.getYR();
            this.prevMs = var2;
         }

         LaneBrain.Pending var10 = this.pending;
         if (var10 != null && var2 - var10.getAtMs() >= 600L && (var2 - var10.getAtMs() >= 1500L || var8)) {
            this.apply(var10, "lines");
            this.pending = null;
         }
      }
   }

   private final void detectTurn(LaneBrain.Frame var1, long var2) {
      float var6 = var1.getYawRate();
      boolean var7;
      if (!Float.isInfinite(var6) && !Float.isNaN(var6)) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      if (var7) {
         this.hist.addLast(new double[]{var2, this.heading, this.odo});

         while (!this.hist.isEmpty() && var2 - ((double[])this.hist.first())[0] > 12000.0) {
            this.hist.removeFirst();
         }

         if (!(Math.abs(var1.getYawRate()) < 0.05F) && !(var1.getVEgo() < 1.0F)) {
            this.straightSince = 0L;
         } else if (this.straightSince == 0L) {
            this.straightSince = var2;
         }

         if (!(var1.getVEgo() > 13.5F) && this.straightSince != 0L && var2 - this.straightSince >= 1500L && this.hist.size() >= 5) {
            double var4 = 0.0;

            for (double[] var8 : this.hist) {
               if (Math.abs(this.heading - var8[1]) > Math.abs(var4)) {
                  var4 = this.heading - var8[1];
               }
            }

            var4 = Math.toDegrees(var4);
            if (!(Math.abs(var4) < 55.0)) {
               this.hist.clear();
               this.memory.clear();
               this.nTurns++;
               if (this.lastCameraMs < this.straightSince) {
                  if (var4 > 0.0) {
                     var7 = (byte)-1;
                  } else {
                     var7 = (byte)1;
                  }

                  String var10;
                  if (var4 > 0.0) {
                     var10 = "left turn: usually the leftmost lane";
                  } else {
                     var10 = "right turn: usually the rightmost lane";
                  }

                  this.restart(var7, 0, 0.45, var2, var10);
               }

               String var11;
               if (var4 > 0.0) {
                  var11 = "LEFT";
               } else {
                  var11 = "RIGHT";
               }

               var7 = (int)Math.abs(var4);
               this.log(var2, var11 + " turn (" + var7 + "°)");
            }
         }
      }
   }

   private final String dirName(int var1) {
      String var2;
      if (var1 < 0) {
         var2 = "LEFT";
      } else {
         var2 = "RIGHT";
      }

      return var2;
   }

   private final void edges(LaneBrain.Frame var1, long var2) {
      if (this.centred(var1)) {
         LaneBrain.Road var7 = this.road;
         boolean var6 = true;
         boolean var5;
         if (var7 != null) {
            if (!var7.getOneway() && !var7.getHighway()) {
               var5 = false;
            } else {
               var5 = true;
            }
         } else {
            var5 = false;
         }

         float var4;
         if (this.model.getBridge()) {
            var4 = 4.2F;
         } else {
            var7 = this.road;
            if (var7 == null || !var7.getHighway()) {
               var6 = false;
            }

            if (var6) {
               var4 = 3.8F;
            } else {
               var4 = 2.8F;
            }
         }

         if (var5) {
            edges$side$12(this, var4, var2, -1, this.vEdgeL, var1.getEdgeL(), var1.getYL(), var1.getPL(), var1.getLeftMark(), var1.getPL2());
         } else {
            this.vEdgeL.clear();
         }

         edges$side$12(this, var4, var2, 1, this.vEdgeR, var1.getEdgeR(), var1.getYR(), var1.getPR(), var1.getRightMark(), var1.getPR2());
      }
   }

   private static final void edges$side$12(
      LaneBrain var0, float var1, long var2, int var4, LaneBrain.Vote var5, float var6, float var7, float var8, LaneMarks.Mark var9, float var10
   ) {
      boolean var17 = Float.isInfinite(var6);
      int var16 = 0;
      boolean var15;
      if (!var17 && !Float.isNaN(var6)) {
         var15 = 1;
      } else {
         var15 = 0;
      }

      if (var15) {
         if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
            var15 = (boolean)1;
         } else {
            var15 = (boolean)0;
         }

         if (var15 && !(var8 < 0.3F)) {
            var6 -= Math.abs(var7);
            if (var6 < -0.5F) {
               return;
            }

            label116: {
               if (var9 != null && var9.getKind() == 3 && !var9.getYellow() && !var9.getDouble() && var0.solidMeansEdge()) {
                  if (!Float.isInfinite(var10) && !Float.isNaN(var10)) {
                     var15 = (boolean)1;
                  } else {
                     var15 = (boolean)0;
                  }

                  if (!var15 || !(var10 >= 0.3F)) {
                     var15 = (boolean)1;
                     break label116;
                  }
               }

               var15 = (boolean)0;
            }

            if (var15) {
               var15 = var16;
            } else {
               var15 = RangesKt.coerceAtLeast((int)Math.ceil((var6 - var1) / var0.laneW), 0);
            }

            var16 = RangesKt.coerceAtMost((int)Math.floor((0.4F + var6) / var0.laneW), 5);
            if (var16 < var15) {
               return;
            }

            Integer var18 = var0.vote(var5, var15 * 10 + var16, var2, 2500L);
            if (var18 != null) {
               var16 = var18.intValue();
               var15 = var16 / 10;
               var16 %= 10;
               var17 = var0.model.getTunnel();
               double var13 = 0.65;
               double var11;
               if (var17) {
                  var11 = 0.5;
               } else {
                  var11 = 0.65;
               }

               if (var16 == 0) {
                  if (var0.solidMeansEdge()) {
                     var11 = var13;
                  } else {
                     var11 = 0.5;
                  }

                  String var19 = Companion.sideName(var4);
                  var0.see(var4, 0, var11, var2, "road edge right past our " + var19 + " line");
               } else if (var15 == var16) {
                  String var23 = Companion.sideName(var4);
                  String var20;
                  if (var15 > 1) {
                     var20 = "s";
                  } else {
                     var20 = "";
                  }

                  var0.see(var4, var15, var11 * 0.75, var2, var23 + " road edge " + var15 + " lane" + var20 + " away");
               } else {
                  String var21 = Companion.sideName(var4);
                  var0.seeRange(var4, var15, var16, var11, var2, var21 + " road edge " + var15 + "-" + var16 + " lanes away (shoulder width unknown)");
               }
            }

            return;
         }
      }
   }

   private final String extraText() {
      int var1 = this.model.getExtraL();
      String var4 = "merge";
      String var3 = null;
      String var5;
      if (var1 > 0) {
         if (Intrinsics.areEqual(this.model.getKindL(), "exit")) {
            var5 = "exit";
         } else {
            var5 = "merge";
         }

         var5 = var5 + " lane left";
      } else {
         var5 = null;
      }

      if (this.model.getExtraR() > 0) {
         var3 = var4;
         if (Intrinsics.areEqual(this.model.getKindR(), "exit")) {
            var3 = "exit";
         }

         var3 = var3 + " lane right";
      }

      return CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull(new String[]{var5, var3}), " + ", null, null, 0, null, null, 62, null);
   }

   private final double fits(int var1, LaneBrain.RoadModel var2) {
      if (var2.getLanes() <= 0) {
         return 1.0;
      } else {
         int var7 = var2.getTotal();
         double var3;
         if (var2.getConf() >= 0.95) {
            var3 = 3.5;
         } else if (var2.getConf() >= 0.85) {
            var3 = 2.5;
         } else if (var2.getConf() >= 0.7) {
            var3 = 1.3;
         } else {
            var3 = 0.8;
         }

         double var5 = Math.exp(-var3 * Math.abs(var1 - var7));
         var3 = var5;
         if (var2.getExtraL() + var2.getExtraR() > 0) {
            var3 = var5;
            if (var1 == var2.getLanes()) {
               var3 = Math.max(var5, 0.45);
            }
         }

         return var3;
      }
   }

   private final int held(ArrayDeque<long[]> var1, long var2) {
      while (!var1.isEmpty() && var2 - ((long[])var1.first())[0] > 2000L) {
         var1.removeFirst();
      }

      for (int var5 = 4; var5 > 0; var5--) {
         var var7 = var1;
         int var4;
         if (var7 instanceof Collection && var7.isEmpty()) {
            var4 = 0;
         } else {
            var4 = 0;

            for (long[] var10 : var7) {
               int var6 = 1;
               if (var10[1] < var5) {
                  var6 = 0;
               }

               if (var6) {
                  var6 = var4 + 1;
                  var4 = var6;
                  if (var6 < 0) {
                     CollectionsKt.throwCountOverflow();
                     var4 = var6;
                  }
               }
            }
         }

         if (var4 >= 6) {
            return var5;
         }
      }

      return 0;
   }

   private final void lanesOnSide(int var1, int var2) {
      if (var2 != 0) {
         double[][] var7 = new double[6][];

         for (int var3 = 0; var3 < 6; var3++) {
            var7[var3] = new double[6];
         }

         for (int var9 = 0; var9 < 6; var9++) {
            for (int var4 = 0; var4 < 6; var4++) {
               int var5;
               if (var1 < 0) {
                  var5 = RangesKt.coerceIn(var9 + var2, 0, 5);
               } else {
                  var5 = var9;
               }

               int var6;
               if (var1 > 0) {
                  var6 = RangesKt.coerceIn(var4 + var2, 0, 5);
               } else {
                  var6 = var4;
               }

               double[] var8 = var7[var5];
               var8[var6] += this.p[var9][var4];
            }
         }

         this.p = var7;
         this.norm();
      }
   }

   private final void log(long var1, String var3) {
      if (this.events.size() < 2 || !Intrinsics.areEqual(this.events.get(1).getText(), var3) || var1 - this.events.get(1).getAtMs() >= 5000L) {
         LaneBrain.Event var5 = new LaneBrain.Event(var1, var3);
         this.events.addFirst(var5);

         while (this.events.size() > 80) {
            this.events.removeLast();
         }

         Function1 var4 = this.onEvent;
         if (var4 != null) {
            var4.invoke(var5);
         }
      }
   }

   private static final int modelSaw$best(float[] var0) {
      Iterator var8 = ArraysKt.getIndices(var0).iterator();
      Object var5;
      if (!var8.hasNext()) {
         var5 = null;
      } else {
         var5 = var8.next();
         if (var8.hasNext()) {
            float var2 = var0[((Number)var5).intValue()];
            Object var6 = var5;

            do {
               Object var7 = var8.next();
               float var3 = var0[((Number)var7).intValue()];
               var5 = var6;
               float var1 = var2;
               if (Float.compare(var2, var3) < 0) {
                  var5 = var7;
                  var1 = var3;
               }

               var6 = var5;
               var2 = var1;
            } while (var8.hasNext());
         }
      }

      Integer var9 = (Integer)var5;
      int var4;
      if (var9 != null) {
         var4 = var9;
      } else {
         var4 = 0;
      }

      return var4;
   }

   // $VF: synthetic method
   public static void modelSaw$default(LaneBrain var0, float[] var1, float[] var2, long var3, long var5, int var7, Object var8) {
      if ((var7 & 8) != 0) {
         var5 = var0.lastMs;
      }

      var0.modelSaw(var1, var2, var3, var5);
   }

   private final void moved(int var1, double var2, long var4, String var6) {
      double[][] var14 = new double[6][];

      for (int var9 = 0; var9 < 6; var9++) {
         var14[var9] = new double[6];
      }

      for (int var11 = 0; var11 < 6; var11++) {
         for (int var12 = 0; var12 < 6; var12++) {
            double var7 = this.p[var11][var12];
            double[] var15 = var14[var11];
            var15[var12] += (1 - var2) * var7;
            int var13 = var11 + var1;
            int var16 = var12 - var1;
            int var10 = var13;
            if (var13 < 0) {
               var10 = 0;
               var16 = RangesKt.coerceAtMost(var12 + 1, 5);
            }

            var13 = var10;
            var10 = var16;
            if (var16 < 0) {
               var10 = 0;
               var13 = RangesKt.coerceAtMost(var11 + 1, 5);
            }

            var15 = var14[RangesKt.coerceAtMost(var13, 5)];
            var16 = RangesKt.coerceAtMost(var10, 5);
            var15[var16] += var7 * var2;
         }
      }

      this.p = var14;
      this.norm();
      this.note(var4, var6);
      if (var4 < this.holdUntil) {
         this.holdUntil = 60000L + var4;
      }
   }

   private final void norm() {
      double var1 = 0.0;
      double[][] var12 = this.p;
      int var10 = ((Object[])var12).length;
      byte var9 = 0;

      for (int var7 = 0; var7 < var10; var7++) {
         double[] var13 = var12[var7];
         int var11 = var13.length;

         for (int var8 = 0; var8 < var11; var8++) {
            var1 += var13[var8];
         }
      }

      if (!(var1 <= 0.0)) {
         boolean var15;
         if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
            var15 = true;
         } else {
            var15 = false;
         }

         if (var15) {
            double var5 = 0.0;

            for (int var16 = 0; var16 < 6; var16++) {
               int var18 = 0;

               while (var18 < 6) {
                  this.p[var16][var18] = this.p[var16][var18] / var1 * 0.996 + Companion.prior(var16, var18) * 0.004;
                  double var3 = var5;
                  if (this.p[var16][var18] > var5) {
                     var3 = this.p[var16][var18];
                  }

                  var18++;
                  var5 = var3;
               }
            }

            var1 = 0.001 * var5;
            double[][] var23 = this.p;
            var10 = ((Object[])var23).length;

            for (int var17 = var9; var17 < var10; var17++) {
               double[] var22 = var23[var17];
               int var19 = 0;

               for (int var20 = var22.length; var19 < var20; var19++) {
                  if (var22[var19] < var1) {
                     var22[var19] = var1;
                  }
               }
            }

            return;
         }
      }

      this.p = Companion.prior();
   }

   private final void note(long var1, String var3) {
      this.why.addFirst(new LaneBrain.Event(var1, var3));

      while (this.why.size() > 6) {
         this.why.removeLast();
      }
   }

   private final void outerLines(LaneBrain.Frame var1, long var2) {
      if (this.centred(var1)) {
         LaneBrain.Road var6 = this.road;
         boolean var5 = true;
         boolean var4;
         if (var6 != null) {
            if (!var6.getOneway() && !var6.getHighway()) {
               var4 = false;
            } else {
               var4 = true;
            }
         } else if (var1.getVEgo() > 17.0F) {
            var4 = true;
         } else {
            var4 = false;
         }

         label39: {
            if (var4) {
               LaneMarks.Mark var9 = var1.getLeftMark();
               if (var9 != null && var9.getYellow()) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               if (!var4) {
                  var4 = var5;
                  break label39;
               }
            }

            var4 = false;
         }

         if (var4) {
            outerLines$side(this, var2, -1, this.vOuterL, var1.getPL(), var1.getYL(), var1.getPL2(), var1.getYL2(), var1.getEdgeL());
         } else {
            this.vOuterL.clear();
         }

         outerLines$side(this, var2, 1, this.vOuterR, var1.getPR(), var1.getYR(), var1.getPR2(), var1.getYR2(), var1.getEdgeR());
      }
   }

   private static final void outerLines$side(
      LaneBrain var0, long var1, int var3, LaneBrain.Vote var4, float var5, float var6, float var7, float var8, float var9
   ) {
      if (!(var5 < 0.5F)) {
         boolean var12;
         if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
            var12 = 1;
         } else {
            var12 = 0;
         }

         if (var12) {
            label122: {
               var5 = Math.abs(var6);
               var6 = Math.abs(var8);
               if (var7 > 0.6F) {
                  boolean var19;
                  if (!Float.isInfinite(var8) && !Float.isNaN(var8)) {
                     var19 = true;
                  } else {
                     var19 = false;
                  }

                  if (var19) {
                     var8 = var6 - var5;
                     if (2.3F <= var8 && var8 <= 4.6F) {
                        var19 = true;
                     } else {
                        var19 = false;
                     }

                     if (var19) {
                        if (!Float.isInfinite(var9) && !Float.isNaN(var9)) {
                           var19 = true;
                        } else {
                           var19 = false;
                        }

                        if (!var19 || var9 - var6 > -0.3F) {
                           var12 = 1;
                           break label122;
                        }
                     }
                  }
               }

               if (!(var7 < 0.15F)) {
                  return;
               }

               boolean var22;
               if (!Float.isInfinite(var9) && !Float.isNaN(var9)) {
                  var22 = true;
               } else {
                  var22 = false;
               }

               if (!var22 || !(var9 - var5 < 1.6F)) {
                  return;
               }

               var12 = 0;
            }

            Integer var13 = var0.vote(var4, var12, var1, 1500L);
            if (var13 != null && var13 == 0) {
               double var10;
               if (var0.solidMeansEdge()) {
                  var10 = 0.75;
               } else {
                  var10 = 0.55;
               }

               String var15 = Companion.sideName(var3);
               var0.see(var3, 0, var10, var1, "no lane line past ours on the " + var15 + ", road edge right there");
            } else if (var13 != null && var13 == 1) {
               String var14 = Companion.sideName(var3);
               var0.atLeast(var3, 1, 0.7, var1, "a lane line one lane over on the " + var14);
            }

            return;
         }
      }
   }

   private final void paint(LaneBrain.Frame var1, long var2) {
      LaneMarks.Mark var7 = var1.getLeftMark();
      LaneMarks.Mark var6 = var1.getRightMark();
      if (var7 != null && var7.getKind() != 0) {
         boolean var5;
         if (!var7.getYellow() || var7.getKind() != 3 && var7.getKind() != 2) {
            var5 = 0;
         } else {
            var5 = 1;
         }

         LaneBrain.Vote var8 = this.vYellow;
         if (var5) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         Integer var24 = this.vote(var8, var5, var2, 1000L);
         if (var24 != null && var24 == 1) {
            this.see(-1, 0, 0.9, var2, "yellow line on the left");
         }

         var8 = this.vDashL;
         if (!var7.getYellow() && var7.getKind() == 2) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         Integer var20 = this.vote(var8, var5, var2, 1500L);
         if (var20 != null && var20 == 1) {
            this.atLeast(-1, 1, 0.6, var2, "dashed line on the left");
         }
      }

      if (var6 != null && var6.getKind() != 0) {
         LaneBrain.Vote var21 = this.vDashR;
         byte var14;
         if (!var6.getYellow() && var6.getKind() == 2) {
            var14 = 1;
         } else {
            var14 = 0;
         }

         Integer var22 = this.vote(var21, var14, var2, 1500L);
         if (var22 != null && var22 == 1) {
            this.atLeast(1, 1, 0.6, var2, "dashed line on the right");
         }

         LaneBrain.Road var23 = this.road;
         boolean var15;
         if (var23 != null && var23.getHighway()) {
            var15 = 1;
         } else {
            var15 = 0;
         }

         label97: {
            if (var15 && this.solidMeansEdge() && !var6.getYellow() && !var6.getDouble() && var6.getKind() == 3) {
               float var4 = var1.getEdgeR();
               if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                  var15 = (boolean)1;
               } else {
                  var15 = (boolean)0;
               }

               if (var15) {
                  var4 = var1.getYR();
                  if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                     var15 = (boolean)1;
                  } else {
                     var15 = (boolean)0;
                  }

                  if (var15 && var1.getEdgeR() - Math.abs(var1.getYR()) < 1.6F) {
                     var15 = (boolean)1;
                     break label97;
                  }
               }
            }

            var15 = (boolean)0;
         }

         LaneBrain.Vote var9 = this.vSolidR;
         if (var15) {
            var15 = 1;
         } else {
            var15 = 0;
         }

         Integer var10 = this.vote(var9, var15, var2, 1500L);
         if (var10 != null && var10 == 1) {
            this.see(1, 0, 0.75, var2, "solid edge line, road edge past it");
         }
      }
   }

   // $VF: synthetic method
   public static void reset$default(LaneBrain var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = true;
      }

      var0.reset(var1);
   }

   private final void restart(int var1, int var2, double var3, long var5, String var7) {
      for (int var12 = 0; var12 < 6; var12++) {
         for (int var13 = 0; var13 < 6; var13++) {
            int var14 = this.count(var12, var13, var1);
            double[] var15 = W;
            double var8;
            if (var1 < 0) {
               var8 = var15[var13];
            } else {
               var8 = var15[var12];
            }

            var15 = this.p[var12];
            double var10;
            if (var14 == var2) {
               var10 = var3;
            } else {
               var10 = (1 - var3) / 5;
            }

            var15[var13] = var10 * var8;
         }
      }

      this.norm();
      this.holdUntil = 0L;
      this.note(var5, var7);
   }

   private final LaneBrain.RoadModel roadModel(LaneBrain.Road var1, LaneBrain.RoadInfo var2) {
      Integer var10;
      if (var1 != null) {
         var10 = this.myCounts.get(var1.getId());
      } else {
         var10 = null;
      }

      int var5;
      if (var2 != null) {
         var5 = var2.getHpmsLanes();
      } else {
         var5 = 0;
      }

      Triple var12;
      if (var10 != null && var10 > 0) {
         var12 = new Triple<>(var10, "your count", 0.97);
      } else if (var5 > 0) {
         boolean var6;
         if (var2 != null && !var2.getHpmsSure()) {
            var6 = true;
         } else {
            var6 = false;
         }

         double var3;
         if (var6) {
            var3 = 0.6;
         } else {
            label144: {
               if (var1 != null && var1.getHighway()) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               if (!var6) {
                  if (var2 != null && var2.getHpmsRamp()) {
                     var6 = true;
                  } else {
                     var6 = false;
                  }

                  if (!var6) {
                     var3 = 0.75;
                     break label144;
                  }
               }

               var3 = 0.9;
            }
         }

         var12 = new Triple<>(var5, "HPMS", var3);
      } else {
         if (var1 != null) {
            var5 = var1.getLanesOurWay();
         } else {
            var5 = 0;
         }

         double var14 = 0.55;
         if (var5 > 0) {
            Intrinsics.checkNotNull(var1);
            var5 = var1.getLanesOurWay();
            if (var1.getLanesOurWay() == 1) {
               var14 = 0.8;
            }

            var12 = new Triple<>(var5, "map", var14);
         } else {
            if (var2 != null) {
               var5 = var2.getNavStraight();
            } else {
               var5 = 0;
            }

            if (var5 > 0) {
               Intrinsics.checkNotNull(var2);
               var12 = new Triple<>(var2.getNavStraight(), "route", 0.55);
            } else {
               var12 = new Triple<>(0, "", 0.0);
            }
         }
      }

      int var7 = ((Number)var12.component1()).intValue();
      String var11 = (String)var12.component2();
      double var15 = ((Number)var12.component3()).doubleValue();
      if (var2 != null) {
         var5 = var2.getExtraL();
      } else {
         var5 = 0;
      }

      int var22;
      if (var2 != null) {
         var22 = var2.getExtraR();
      } else {
         var22 = 0;
      }

      label104: {
         if (var2 != null) {
            var13 = var2.getKindL();
            if (var13 != null) {
               break label104;
            }
         }

         var13 = "";
      }

      label99: {
         if (var2 != null) {
            var23 = var2.getKindR();
            if (var23 != null) {
               break label99;
            }
         }

         var23 = "";
      }

      boolean var8;
      if (var2 != null && var2.getTunnel()) {
         var8 = true;
      } else {
         var8 = false;
      }

      boolean var9;
      if (var2 != null && var2.getBridge()) {
         var9 = true;
      } else {
         var9 = false;
      }

      return new LaneBrain.RoadModel(var7, var11, var15, var5, var22, var13, var23, var8, var9);
   }

   private final void roadUpdate(LaneBrain.Frame var1, long var2) {
      LaneBrain.Road var9 = var1.getRoad();
      LaneBrain.Road var10 = this.road;
      if (var9 != null) {
         this.road = var9;
      }

      this.info = var1.getInfo();
      LaneBrain.RoadModel var7 = this.model;
      this.model = this.roadModel(this.road, this.info);
      if (this.model.getLanes() != var7.getLanes()
         || this.model.getExtraL() != var7.getExtraL()
         || this.model.getExtraR() != var7.getExtraR()
         || var9 != null && var10 != null && !Intrinsics.areEqual(var9.getName(), var10.getName())) {
         this.roadChangeMs = var2;
      }

      if (var7.getLanes() > 0 || this.model.getLanes() > 0) {
         if (!roadUpdate$ours(this, var2, -1, this.model.getExtraL() - var7.getExtraL())) {
            this.lanesOnSide(-1, this.model.getExtraL() - var7.getExtraL());
         }

         if (!roadUpdate$ours(this, var2, 1, this.model.getExtraR() - var7.getExtraR())) {
            this.lanesOnSide(1, this.model.getExtraR() - var7.getExtraR());
         }

         if (this.model.getExtraL() != var7.getExtraL() || this.model.getExtraR() != var7.getExtraR()) {
            String var16;
            if (this.model.getExtraL() + this.model.getExtraR() <= var7.getExtraL() + var7.getExtraR()) {
               var16 = "extra lane ended";
            } else {
               String var11;
               label97: {
                  var11 = this.extraText();
                  LaneBrain.RoadInfo var14 = this.info;
                  if (var14 != null) {
                     String var8 = var14.getExtraWhy();
                     var15 = var8;
                     if (var8 != null) {
                        break label97;
                     }
                  }

                  var15 = "map";
               }

               var16 = var11 + " (" + var15 + ")";
            }

            this.note(var2, var16);
         }
      }

      if (var9 != null && (var10 == null || var10.getId() != var9.getId())) {
         if (var10 != null
            && (!Intrinsics.areEqual(var10.getName(), var9.getName()) || var10.getHighway() != var9.getHighway() || var10.getRamp() != var9.getRamp())) {
            this.memory.clear();
            this.blur(600.0);
         }

         if (this.model.getLanes() == 1) {
            String var17 = this.model.getSrc();
            this.restart(-1, 0, 0.85, var2, var17 + ": 1 lane our way");
         }
      }

      if (var1.getEntrySide() != 0) {
         this.entrySide = var1.getEntrySide();
         this.entryMs = var2;
         this.roadChangeMs = var2;
         double var4;
         byte var6;
         String var18;
         if (var1.getEntrySide() < 0) {
            var4 = 0.75;
            var18 = "joined from a left ramp";
            var6 = -1;
         } else {
            var4 = 0.6;
            var18 = "joined from a right ramp";
            var6 = 1;
         }

         this.restart(var6, 0, var4, var2, var18);
         this.nRamps++;
         String var12;
         if (var1.getEntrySide() < 0) {
            var12 = "LEFT";
         } else {
            var12 = "RIGHT";
         }

         label73: {
            if (var9 != null) {
               String var19 = var9.getName();
               if (var19 != null) {
                  var var20 = var19;
                  boolean var13;
                  if (var20.length() == 0) {
                     var13 = true;
                  } else {
                     var13 = false;
                  }

                  if (var13) {
                     var20 = var9.getKind();
                  }

                  String var22 = var20;
                  var21 = var22;
                  if (var22 != null) {
                     break label73;
                  }
               }
            }

            var21 = "?";
         }

         this.log(var2, "highway entry from the " + var12 + " (" + var21 + ")");
      }
   }

   private static final boolean roadUpdate$ours(LaneBrain var0, long var1, int var3, int var4) {
      boolean var5;
      if (var4 > 0 && var0.entrySide == var3 && var1 - var0.entryMs < 20000L) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   private final void rtk(LaneBrain.Frame var1, long var2) {
      this.camNote(var1, var2);
      double var4 = var1.getRtkOffset();
      boolean var8 = Double.isInfinite(var4);
      boolean var7 = false;
      boolean var6;
      if (!var8 && !Double.isNaN(var4)) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      if (var6 && !(var1.getRtkSigma() > 1.2)) {
         this.rtkLog.addLast(new double[]{var2, var1.getRtkOffset(), var1.getRtkWay()});

         while (!this.rtkLog.isEmpty() && var2 - ((double[])this.rtkLog.first())[0] > 15000.0) {
            this.rtkLog.removeFirst();
         }

         var var9 = this.hist;
         if (var9 instanceof Collection && var9.isEmpty()) {
            var6 = (boolean)0;
         } else {
            Iterator var10 = var9.iterator();

            while (true) {
               if (!var10.hasNext()) {
                  var6 = (boolean)0;
                  break;
               }

               double[] var17 = (double[])var10.next();
               if (var2 - var17[0] <= 8000.0 && Math.abs(this.heading - var17[1]) > Math.toRadians(10.0)) {
                  var6 = (boolean)1;
               } else {
                  var6 = (boolean)0;
               }

               if (var6) {
                  var6 = (boolean)1;
                  break;
               }
            }
         }

         if (!(var1.getVEgo() < 6.0F)
            && !var6
            && this.pending == null
            && this.opDir == 0
            && var2 - this.rtkCheckMs >= 1000L
            && var2 - this.lastChangeMs >= 10000L) {
            this.rtkCheckMs = var2;
            var4 = this.sideways(var2 - 7000, var2 - 1000);
            var6 = var7;
            if (!Double.isInfinite(var4)) {
               var6 = var7;
               if (!Double.isNaN(var4)) {
                  var6 = (boolean)1;
               }
            }

            if (var6 && !(Math.abs(var4) < 0.75)) {
               if (var4 > 0.0) {
                  var6 = 1;
               } else {
                  var6 = -1;
               }

               String var11;
               if (var2 - this.roadChangeMs < 9000L) {
                  var11 = "the road changed here";
               } else if (this.cameraStayed(var2 - 8000, var2)) {
                  var11 = "the camera saw us stay in our lane";
               } else {
                  var11 = null;
               }

               if (var11 != null) {
                  if (var2 - this.rtkDoubtMs > 10000L) {
                     this.rtkDoubtMs = var2;
                     String var18 = String.format("%.1f", Arrays.copyOf(new Object[]{Math.abs(var4)}, 1));
                     Intrinsics.checkNotNullExpressionValue(var18, "format(...)");
                     String var19 = this.dirName(var6);
                     this.log(var2, "RTK moved " + var18 + " lanes " + var19 + " on the map, but " + var11 + " - not counted");
                  }
               } else {
                  this.apply(new LaneBrain.Pending(var6, var2), "rtk");
               }
            }
         }
      }
   }

   private final void see(int var1, int var2, double var3, long var5, String var7) {
      if (var5 >= this.holdUntil || this.side(var1).getFirst().intValue() == var2) {
         this.lastCameraMs = var5;
         var3 /= (1 - var3) / 5;

         for (int var8 = 0; var8 < 6; var8++) {
            for (int var9 = 0; var9 < 6; var9++) {
               if (this.count(var8, var9, var1) == RangesKt.coerceIn(var2, 0, 5)) {
                  double[] var10 = this.p[var8];
                  var10[var9] *= var3;
               }
            }
         }

         this.norm();
         this.note(var5, var7);
      }
   }

   private final void seeRange(int var1, int var2, int var3, double var4, long var6, String var8) {
      if (var6 < this.holdUntil) {
         int var9 = this.side(var1).getFirst().intValue();
         boolean var14;
         if (var2 <= var9 && var9 <= var3) {
            var14 = true;
         } else {
            var14 = false;
         }

         if (!var14) {
            return;
         }
      }

      this.lastCameraMs = var6;
      int var15 = RangesKt.coerceAtLeast(var3 - var2 + 1, 1);
      int var10 = RangesKt.coerceAtLeast(6 - var15, 1);
      var4 = var4 / var15 / ((1 - var4) / var10);

      for (int var16 = 0; var16 < 6; var16++) {
         for (int var17 = 0; var17 < 6; var17++) {
            int var11 = this.count(var16, var17, var1);
            boolean var18;
            if (var2 <= var11 && var11 <= var3) {
               var18 = true;
            } else {
               var18 = false;
            }

            if (var18) {
               double[] var12 = this.p[var16];
               var12[var17] *= var4;
            }
         }
      }

      this.norm();
      this.note(var6, var8);
   }

   // $VF: synthetic method
   public static void setCount$default(
      LaneBrain var0, int var1, int var2, boolean var3, long var4, int var6, int var7, String var8, String var9, int var10, Object var11
   ) {
      if ((var10 & 8) != 0) {
         var4 = var0.lastMs;
      }

      if ((var10 & 16) != 0) {
         var6 = 0;
      }

      if ((var10 & 32) != 0) {
         var7 = 0;
      }

      if ((var10 & 64) != 0) {
         var8 = "";
      }

      if ((var10 & 128) != 0) {
         var9 = "";
      }

      var0.setCount(var1, var2, var3, var4, var6, var7, var8, var9);
   }

   private final double sideways(long var1, long var3) {
      List var7 = this.rtkLog;
      ListIterator var8 = var7.listIterator(var7.size());

      boolean var5;
      do {
         if (!var8.hasPrevious()) {
            var12 = null;
            break;
         }

         var12 = var8.previous();
         double[] var9 = (double[])var12;
         if (var9[0] <= var1 + 500 && var9[0] >= var1 - 1500) {
            var5 = true;
         } else {
            var5 = false;
         }
      } while (!var5);

      double[] var16 = (double[])var12;
      if (var16 == null) {
         return Double.NaN;
      } else {
         var7 = this.rtkLog;
         boolean var6 = false;
         ListIterator var17 = var7.listIterator(var7.size());

         do {
            if (!var17.hasPrevious()) {
               var14 = null;
               break;
            }

            var14 = var17.previous();
            if (((double[])var14)[0] <= var3) {
               var5 = true;
            } else {
               var5 = false;
            }
         } while (!var5);

         double[] var15 = (double[])var14;
         if (var15 == null) {
            return Double.NaN;
         } else {
            if (var16[2] == var15[2]) {
               var5 = true;
            } else {
               var5 = false;
            }

            return !var5 ? Double.NaN : (var15[1] - var16[1]) / this.laneW;
         }
      }
   }

   private final boolean solidMeansEdge() {
      boolean var1;
      if (!this.model.getTunnel() && !this.model.getBridge()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   // $VF: synthetic method
   public static boolean turning$default(LaneBrain var0, long var1, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.lastMs;
      }

      return var0.turning(var1);
   }

   private final Integer vote(LaneBrain.Vote var1, int var2, long var3, long var5) {
      var1.getQ().addLast(new long[]{var3, var2});

      while (!var1.getQ().isEmpty() && var3 - ((long[])var1.getQ().first())[0] > var5) {
         var1.getQ().removeFirst();
      }

      if (var3 - var1.getUsed() >= 500L && var1.getQ().size() >= 8 && !(var3 - var1.getQ().first()[0] < var5 * 0.7)) {
         Iterator var12 = GroupingKt.eachCount(new Grouping<long[], Long>(var1.getQ()) {
            final Iterable $this_groupingBy;

            {
               this.$this_groupingBy = var1;
            }

            @Override
            public Long keyOf(long[] var1) {
               return ((long[])var1)[1];
            }

            @Override
            public Iterator<long[]> sourceIterator() {
               return this.$this_groupingBy.iterator();
            }
         }).entrySet().iterator();
         Object var9;
         if (!var12.hasNext()) {
            var9 = null;
         } else {
            var9 = var12.next();
            if (var12.hasNext()) {
               var2 = ((Number)((Entry)var9).getValue()).intValue();
               Object var10 = var9;

               do {
                  Object var11 = var12.next();
                  int var8 = ((Number)((Entry)var11).getValue()).intValue();
                  var9 = var10;
                  int var7 = var2;
                  if (var2 < var8) {
                     var9 = var11;
                     var7 = var8;
                  }

                  var10 = var9;
                  var2 = var7;
               } while (var12.hasNext());
            }
         }

         var9 = (Entry)var9;
         if (var9 == null) {
            return null;
         } else if (((Number)var9.getValue()).intValue() < var1.getQ().size() * 0.75) {
            return null;
         } else {
            var1.setUsed(var3);
            return (int)((Number)var9.getKey()).longValue();
         }
      } else {
         return null;
      }
   }

   public final void check(boolean var1, long var2) {
      int var5 = this.side(-1).component1().intValue();
      int var4 = this.side(1).component1().intValue();
      if (var1) {
         this.see(-1, var5, 0.9, var2, "your check");
         this.see(1, var4, 0.9, var2, "your check");
      } else {
         double[] var6 = this.p[var5];
         var6[var4] *= 0.1;
         this.norm();
         this.note(var2, "your ✗");
      }

      this.answer();
   }

   public final void endDrive() {
      if (this.driveS > 30.0) {
         Long var4 = this.lastMs;
         long var2 = var4.longValue();
         boolean var1;
         if (var2 > 0L) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (!var1) {
            var4 = null;
         }

         if (var4 != null) {
            var2 = var4;
         } else {
            var2 = System.currentTimeMillis();
         }

         String var6 = this.summary();
         this.log(var2, "drive summary: " + var6);
      }

      reset$default(this, false, 1, null);
   }

   public final double getDriveS() {
      return this.driveS;
   }

   public final ArrayDeque<LaneBrain.Event> getEvents() {
      return this.events;
   }

   public final LaneBrain.RoadInfo getInfo() {
      return this.info;
   }

   public final double getKnownS() {
      return this.knownS;
   }

   public final float getLaneW() {
      return this.laneW;
   }

   public final long getLastCameraMs() {
      return this.lastCameraMs;
   }

   public final int getLastChangeDir() {
      return this.lastChangeDir;
   }

   public final long getLastChangeMs() {
      return this.lastChangeMs;
   }

   public final LaneMemory getMemory() {
      return this.memory;
   }

   public final LaneBrain.RoadModel getModel() {
      return this.model;
   }

   public final String getModelText() {
      return this.modelText;
   }

   public final HashMap<Long, Integer> getMyCounts() {
      return this.myCounts;
   }

   public final int getNChanges() {
      return this.nChanges;
   }

   public final int getNOpMissed() {
      return this.nOpMissed;
   }

   public final int getNRamps() {
      return this.nRamps;
   }

   public final int getNTurns() {
      return this.nTurns;
   }

   public final int getNUndone() {
      return this.nUndone;
   }

   public final double getOdo() {
      return this.odo;
   }

   public final Function0<Unit> getOnCountsChanged() {
      return this.onCountsChanged;
   }

   public final Function1<LaneBrain.Event, Unit> getOnEvent() {
      return this.onEvent;
   }

   public final LaneBrain.Road getRoad() {
      return this.road;
   }

   public final ArrayDeque<LaneBrain.Event> getWhy() {
      return this.why;
   }

   public final int inExtra() {
      Pair var8 = this.side(-1);
      int var6 = ((Number)var8.component1()).intValue();
      double var1 = ((Number)var8.component2()).doubleValue();
      var8 = this.side(1);
      int var5 = ((Number)var8.component1()).intValue();
      double var3 = ((Number)var8.component2()).doubleValue();
      int var7 = this.total().component1().intValue();
      if (this.model.getLanes() <= 0 || var7 != this.model.getTotal()) {
         return 0;
      } else if (this.model.getExtraR() > 0 && var5 < this.model.getExtraR() && var3 >= 0.5) {
         return 1;
      } else {
         return this.model.getExtraL() > 0 && var6 < this.model.getExtraL() && var1 >= 0.5 ? -1 : 0;
      }
   }

   public final int knownPct() {
      int var1;
      if (this.driveS < 1.0) {
         var1 = 0;
      } else {
         var1 = (int)(100 * this.knownS / this.driveS);
      }

      return var1;
   }

   public final String label() {
      int var1 = this.laneFromLeft().component1().intValue();
      if (var1 <= 0) {
         return "Lane ?";
      } else {
         int var2 = this.inExtra();
         LaneBrain.RoadModel var4 = this.model;
         String var5;
         if (var2 < 0) {
            var5 = var4.getKindL();
         } else {
            var5 = var4.getKindR();
         }

         String var14 = "Exit";
         String var6 = "exit";
         if (var2 != 0) {
            if (!Intrinsics.areEqual(var5, "exit")) {
               var14 = "Merge";
            }

            if (var2 < 0) {
               var5 = "left";
            } else {
               var5 = "right";
            }

            var1 = this.model.getLanes();
            return var14 + " lane (" + var5 + " of " + var1 + " lanes)";
         } else {
            int var3 = this.laneCount().component1().intValue();
            var2 = this.total().component1().intValue();
            if (this.model.getLanes() > 0 && var2 == this.model.getTotal() && this.model.getExtraL() + this.model.getExtraR() > 0) {
               var3 = var1 - this.model.getExtraL();
               if (var3 > this.model.getLanes() && this.model.getExtraR() > 0) {
                  if (!Intrinsics.areEqual(this.model.getKindR(), "exit")) {
                     var14 = "Merge";
                  }

                  var1 = this.model.getLanes();
                  return var14 + " lane (right of " + var1 + " lanes)";
               } else if (var3 < 1 && this.model.getExtraL() > 0) {
                  if (!Intrinsics.areEqual(this.model.getKindL(), "exit")) {
                     var14 = "Merge";
                  }

                  var1 = this.model.getLanes();
                  return var14 + " lane (left of " + var1 + " lanes)";
               } else {
                  var2 = this.model.getLanes();
                  var1 = this.model.getExtraL();
                  var5 = "";
                  String var17;
                  if (var1 > 0) {
                     String var16;
                     if (Intrinsics.areEqual(this.model.getKindL(), "exit")) {
                        var16 = "exit";
                     } else {
                        var16 = "merge";
                     }

                     var17 = " + " + var16 + " lane ←";
                  } else {
                     var17 = "";
                  }

                  if (this.model.getExtraR() > 0) {
                     if (Intrinsics.areEqual(this.model.getKindR(), "exit")) {
                        var5 = var6;
                     } else {
                        var5 = "merge";
                     }

                     var5 = " + " + var5 + " lane →";
                  }

                  return "Lane " + var3 + " of " + var2 + var17 + var5;
               }
            } else {
               String var15;
               if (var3 >= var1) {
                  var15 = String.valueOf(var3);
               } else {
                  var15 = "?";
               }

               return "Lane " + var1 + " of " + var15;
            }
         }
      }
   }

   public final Pair<Integer, String> laneCount() {
      Pair var5 = this.total();
      int var3 = ((Number)var5.component1()).intValue();
      double var1 = ((Number)var5.component2()).doubleValue();
      int var4 = this.model.getLanes();
      String var8 = "";
      if (var4 > 0 && var3 == this.model.getTotal()) {
         String var10 = this.model.getSrc();
         if (this.model.getExtraL() + this.model.getExtraR() > 0) {
            String var9 = this.extraText();
            var8 = " + " + var9;
         }

         return TuplesKt.to(var3, var10 + var8);
      } else if (this.model.getLanes() > 0 && var3 == this.model.getLanes()) {
         return TuplesKt.to(var3, this.model.getSrc());
      } else {
         Integer var6;
         if (var1 >= 0.5) {
            var6 = var3;
            var3 = (int)(100 * var1);
            var8 = "camera, " + var3 + "%";
         } else {
            var6 = 0;
         }

         return TuplesKt.to(var6, var8);
      }
   }

   public final Pair<Integer, Double> laneFromLeft() {
      Pair var4 = this.side(-1);
      int var3 = ((Number)var4.component1()).intValue();
      double var1 = ((Number)var4.component2()).doubleValue();
      if (var1 >= 0.35) {
         var3++;
      } else {
         var3 = 0;
      }

      return TuplesKt.to(var3, var1);
   }

   public final int lanesLeft() {
      return this.side(-1).getFirst().intValue();
   }

   public final int lanesRight() {
      return this.side(1).getFirst().intValue();
   }

   public final double[] marg(int var1) {
      double[] var5 = new double[6];

      for (int var2 = 0; var2 < 6; var2++) {
         for (int var3 = 0; var3 < 6; var3++) {
            int var4;
            if (var1 < 0) {
               var4 = var2;
            } else {
               var4 = var3;
            }

            var5[var4] += this.q[var2][var3];
         }
      }

      return var5;
   }

   public final boolean modelFresh() {
      int var1 = this.modelText.length();
      boolean var2 = true;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3 || System.currentTimeMillis() - this.modelMs >= 6000L) {
         var2 = false;
      }

      return var2;
   }

   public final void modelSaw(float[] var1, float[] var2, long var3, long var5) {
      Intrinsics.checkNotNullParameter(var1, "pL");
      Intrinsics.checkNotNullParameter(var2, "pR");
      if (var3 > this.modelMs) {
         this.modelMs = var3;
         LaneBrain.Road var14 = this.road;
         boolean var9;
         if (var14 != null && !var14.getHighway() && !var14.getRamp()) {
            var9 = true;
         } else {
            var9 = false;
         }

         int var12 = modelSaw$best(var1);
         int var11 = modelSaw$best(var2);
         String var15 = "3+";
         String var16;
         if (var12 == 3) {
            var16 = "3+";
         } else {
            var16 = String.valueOf(var12);
         }

         if (var11 != 3) {
            var15 = String.valueOf(var11);
         }

         float var7 = var1[var12];
         float var8 = 100;
         int var10 = (int)(var7 * var8);
         int var13 = (int)(var2[var11] * var8);
         var15 = "model: " + var16 + " left, " + var15 + " right (" + var10 + "% / " + var13 + "%)";
         String var17;
         if (var9) {
            var17 = var15;
         } else {
            var17 = var15 + " - not used on highways";
         }

         this.modelText = var17;
         if (var9) {
            if (var1[var12] >= 0.6F) {
               if (var12 == 3) {
                  this.atLeast(-1, 3, 0.6, var5, var15);
               } else {
                  this.see(-1, var12, 0.6, var5, var15);
               }
            }

            if (var2[var11] >= 0.6F) {
               if (var11 == 3) {
                  this.atLeast(1, 3, 0.6, var5, var15);
               } else {
                  this.see(1, var11, 0.6, var5, var15);
               }
            }

            this.answer();
         }
      }
   }

   public final void reset(boolean var1) {
      this.p = Companion.prior();
      this.q = Companion.prior();
      this.road = null;
      this.info = null;
      this.model = new LaneBrain.RoadModel(0, "", 0.0, 0, 0, "", "", false, false);
      this.memory.clear();
      this.why.clear();
      this.pending = null;
      this.prevMs = 0L;
      this.hist.clear();
      this.opDir = 0;
      this.lastMs = 0L;
      this.holdUntil = 0L;
      this.rtkLog.clear();
      this.prevAnswer = "";
      this.camLog.clear();
      this.roadChangeMs = 0L;
      this.rtkDoubtMs = 0L;
      Iterator var2 = CollectionsKt.listOf(
            new LaneBrain.Vote[]{this.vYellow, this.vDashL, this.vDashR, this.vSolidR, this.vOuterL, this.vOuterR, this.vEdgeL, this.vEdgeR}
         )
         .iterator();

      while (var2.hasNext()) {
         ((LaneBrain.Vote)var2.next()).clear();
      }

      this.carsL.clear();
      this.carsR.clear();
      this.oncoming.clear();
      this.nChanges = 0;
      this.nUndone = 0;
      this.nOpMissed = 0;
      this.nTurns = 0;
      this.nRamps = 0;
      this.driveS = 0.0;
      this.knownS = 0.0;
      this.lastCameraMs = 0L;
      if (!var1) {
         this.myCounts.clear();
      }
   }

   public final void roadLaneEnded(int var1, long var2) {
      this.lanesOnSide(var1, -1);
      String var4 = Companion.sideName(var1);
      this.note(var2, "the " + var4 + " lane ended");
      this.answer();
      this.roadChangeMs = var2;
      if (var1 < 0) {
         var4 = "left";
      } else {
         var4 = "right";
      }

      this.log(var2, var4 + " lane ended (road data)");
   }

   public final void setCount(int var1, int var2, boolean var3, long var4, int var6, int var7, String var8, String var9) {
      Intrinsics.checkNotNullParameter(var8, "kind");
      Intrinsics.checkNotNullParameter(var9, "kindR");
      LaneBrain.Road var19 = this.road;
      boolean var14 = false;
      boolean var12 = false;
      if (var1 <= 0) {
         if (var19 != null && this.myCounts.remove(var19.getId()) != null) {
            Function0 var28 = this.onCountsChanged;
            if (var28 != null) {
               var28.invoke();
            }

            Object var29 = var19.getName();
            boolean var20 = var12;
            if (var29.length() == 0) {
               var20 = true;
            }

            if (var20) {
               var29 = var19.getKind();
            }

            this.log(var4, "your count for " + var29 + " forgotten");
         }

         this.model = this.roadModel(this.road, this.info);
         this.answer();
      } else {
         if (var19 != null && var3) {
            this.myCounts.put(var19.getId(), var1);
            Function0 var18 = this.onCountsChanged;
            if (var18 != null) {
               var18.invoke();
            }
         }

         int var15 = var1 + var6 + var7;
         if (1 <= var2 && var2 <= var15) {
            var12 = true;
         } else {
            var12 = false;
         }

         if (var12) {
            for (int var33 = 0; var33 < 6; var33++) {
               for (int var13 = 0; var13 < 6; var13++) {
                  double[] var35 = this.p[var33];
                  double var10;
                  if (var33 == var2 - 1 && var13 == var15 - var2) {
                     var10 = 0.97;
                  } else {
                     var10 = 8.571428571428571E-4;
                  }

                  var35[var13] = var10;
               }
            }

            this.norm();
            long var16;
            if (var6 + var7 > 0) {
               var16 = 60000L;
            } else {
               var16 = 120000L;
            }

            this.holdUntil = var16 + var4;
            this.note(var4, "you: lane " + var2 + " of " + var15);
         }

         LaneBrain.RoadModel var36 = this.roadModel(this.road, this.info);
         LaneBrain.RoadModel var23;
         if (var6 + var7 > 0 && var36.getExtraL() + var36.getExtraR() == 0) {
            var9 = var9;
            if (var9.length() == 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            if (var12) {
               var9 = var8;
            }

            var23 = new LaneBrain.RoadModel(var1, "your count", 0.97, var6, var7, var8, var9, var36.getTunnel(), var36.getBridge());
         } else {
            var23 = var36;
         }

         this.model = var23;
         this.answer();
         boolean var22;
         if (1 <= var2 && var2 <= var15) {
            var22 = true;
         } else {
            var22 = false;
         }

         StringBuilder var24 = null;
         StringBuilder var25;
         if (var22) {
            var24 = new StringBuilder();
            var25 = var24.append("lane ").append(var2).append(" of ").append(var15);
         } else {
            var24 = new StringBuilder();
            var25 = var24.append(var15).append(" lanes");
         }

         var9 = var25.toString();
         if (var19 != null && var3) {
            Object var27 = var19.getName();
            boolean var21 = var14;
            if (var27.length() == 0) {
               var21 = true;
            }

            if (var21) {
               var27 = var19.getKind();
            }

            var8 = " - " + var1 + " remembered for " + var27;
         } else {
            var8 = "";
         }

         this.log(var4, "you set " + var9 + var8);
      }
   }

   public final void setOnCountsChanged(Function0<Unit> var1) {
      this.onCountsChanged = var1;
   }

   public final void setOnEvent(Function1<? super LaneBrain.Event, Unit> var1) {
      this.onEvent = var1;
   }

   public final Pair<Integer, Double> side(int var1) {
      double[] var4 = this.marg(var1);
      int var2 = 0;
      var1 = 1;

      while (var1 < 6) {
         int var3 = var2;
         if (var4[var1] > var4[var2]) {
            var3 = var1;
         }

         var1++;
         var2 = var3;
      }

      return TuplesKt.to(var2, var4[var2]);
   }

   public final Pair<Integer, Double> sideLanes(int var1) {
      double[] var8 = this.marg(var1);
      if (var8[0] >= 0.6) {
         return TuplesKt.to(0, var8[0]);
      } else {
         double var4 = 1.0;
         int var7 = 0;
         double var2 = var8[0];

         for (int var6 = 1; var6 < 6; var6++) {
            var4 -= var8[var6 - 1];
            if (!(var4 >= 0.6)) {
               break;
            }

            var7 = var6;
            var2 = var4;
         }

         Pair var9;
         if (var7 == 0) {
            var9 = this.side(var1);
         } else {
            var9 = TuplesKt.to(var7, var2);
         }

         return var9;
      }
   }

   public final String summary() {
      int var1 = this.nChanges;
      int var2 = this.nChanges;
      String var10 = "s";
      String var6;
      if (var2 == 1) {
         var6 = "";
      } else {
         var6 = "s";
      }

      String var7;
      if (this.nUndone > 0) {
         var2 = this.nUndone;
         var7 = " (" + var2 + " undone)";
      } else {
         var7 = "";
      }

      String var8;
      if (this.nOpMissed > 0) {
         var2 = this.nOpMissed;
         var8 = " (" + var2 + " by openpilot not finished)";
      } else {
         var8 = "";
      }

      int var4 = this.nTurns;
      String var9;
      if (this.nTurns == 1) {
         var9 = "";
      } else {
         var9 = "s";
      }

      int var3 = this.nRamps;
      if (this.nRamps == 1) {
         var10 = "";
      }

      var2 = this.knownPct();
      int var5 = (int)(this.driveS / 60);
      return var1
         + " lane change"
         + var6
         + var7
         + var8
         + " · "
         + var4
         + " turn"
         + var9
         + " · "
         + var3
         + " ramp"
         + var10
         + " · lane known "
         + var2
         + "% of "
         + var5
         + " min";
   }

   public final Pair<Integer, Double> total() {
      double[] var5 = new double[12];

      for (int var1 = 0; var1 < 6; var1++) {
         for (int var2 = 0; var2 < 6; var2++) {
            int var3 = var1 + var2 + 1;
            var5[var3] += this.q[var1][var2];
         }
      }

      int var8 = 1;
      int var6 = 2;
      int var4 = var5.length;

      while (var6 < var4) {
         int var7 = var8;
         if (var5[var6] > var5[var8]) {
            var7 = var6;
         }

         var6++;
         var8 = var7;
      }

      return TuplesKt.to(var8, var5[var8]);
   }

   public final boolean turning(long var1) {
      var var6 = this.hist;
      boolean var5 = var6 instanceof Collection;
      boolean var4 = false;
      if (!var5 || !var6.isEmpty()) {
         for (double[] var8 : var6) {
            boolean var3;
            if (var1 - var8[0] <= 8000.0 && Math.abs(this.heading - var8[1]) > Math.toRadians(10.0)) {
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

   public final void update(LaneBrain.Frame var1) {
      Intrinsics.checkNotNullParameter(var1, "f");
      long var11 = var1.getNowMs();
      double var2;
      if (this.lastMs == 0L) {
         var2 = 0.0;
      } else {
         var2 = RangesKt.coerceIn((var11 - this.lastMs) / 1000.0, 0.0, 0.5);
      }

      this.lastMs = var11;
      this.odo = this.odo + var1.getVEgo() * var2;
      float var6 = var1.getYawRate();
      boolean var10 = Float.isInfinite(var6);
      boolean var9 = true;
      boolean var8;
      if (!var10 && !Float.isNaN(var6)) {
         var8 = true;
      } else {
         var8 = false;
      }

      if (var8) {
         this.heading = this.heading + var1.getYawRate() * var2;
      }

      if (this.odo - this.lastBlurOdo >= 20.0) {
         this.blur(this.odo - this.lastBlurOdo);
         this.lastBlurOdo = this.odo;
      }

      this.roadUpdate(var1, var11);
      this.detectTurn(var1, var11);
      this.checkOpChange(var1, var11);
      this.detectLaneChange(var1, var11);
      LaneMemory var13 = this.memory;
      double var4 = this.odo;
      List var14 = CollectionsKt.listOf(
         new Pair[]{
            TuplesKt.to(var1.getYL2(), var1.getPL2()),
            TuplesKt.to(var1.getYL(), var1.getPL()),
            TuplesKt.to(var1.getYR(), var1.getPR()),
            TuplesKt.to(var1.getYR2(), var1.getPR2())
         }
      );
      float var7 = var1.getEdgeL();
      var6 = var1.getEdgeR();
      if (var1.getOpChange() == 0 && this.opDir == 0 && !(var1.getVEgo() < 2.0F)) {
         var10 = false;
      } else {
         var10 = true;
      }

      var13.update(var4, var14, var7, var6, var10);
      if (var1.getVEgo() >= 2.0F) {
         this.paint(var1, var11);
         this.outerLines(var1, var11);
         this.edges(var1, var11);
         this.cars(var1, var11);
      }

      this.blindSpot(var1, var11);
      this.rtk(var1, var11);
      this.answer();
      if (var1.getVEgo() > 1.0F) {
         this.driveS += var2;
         if (this.laneFromLeft().getSecond().doubleValue() >= 0.5) {
            this.knownS += var2;
         }
      }

      String var21 = this.label();
      if (!Intrinsics.areEqual(var21, this.prevAnswer)) {
         if (this.prevAnswer.length() > 0) {
            var8 = var9;
         } else {
            var8 = false;
         }

         if (var8 || !Intrinsics.areEqual(var21, "Lane ?")) {
            label49: {
               LaneBrain.Event var15 = this.why.firstOrNull();
               if (var15 != null) {
                  String var20 = var15.getText();
                  var16 = var20;
                  if (var20 != null) {
                     break label49;
                  }
               }

               var16 = "start";
            }

            this.log(var11, var21 + "  (" + var16 + ")");
         }

         this.prevAnswer = var21;
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private final double[][] prior() {
         double[][] var3 = new double[6][];

         for (int var1 = 0; var1 < 6; var1++) {
            double[] var4 = new double[6];

            for (int var2 = 0; var2 < 6; var2++) {
               var4[var2] = LaneBrain.Companion.prior(var1, var2);
            }

            var3[var1] = var4;
         }

         return var3;
      }

      private final String sideName(int var1) {
         String var2;
         if (var1 < 0) {
            var2 = "left";
         } else {
            var2 = "right";
         }

         return var2;
      }

      public final double prior(int var1, int var2) {
         return LaneBrain.W[var1] * LaneBrain.W[var2];
      }
   }

   public static final class Event {
      private final long atMs;
      private final String text;

      public Event(long var1, String var3) {
         Intrinsics.checkNotNullParameter(var3, "text");
         super();
         this.atMs = var1;
         this.text = var3;
      }

      public final long getAtMs() {
         return this.atMs;
      }

      public final String getText() {
         return this.text;
      }
   }

   public static final class Frame {
      private final boolean bsmL;
      private final boolean bsmR;
      private final int carsLeft;
      private final int carsRight;
      private final float edgeL;
      private final float edgeR;
      private final int entrySide;
      private final LaneBrain.RoadInfo info;
      private final LaneMarks.Mark leftMark;
      private final long nowMs;
      private final boolean oncomingLeft;
      private final int opChange;
      private final boolean opFinishing;
      private final float opLcProb;
      private final float pL;
      private final float pL2;
      private final float pR;
      private final float pR2;
      private final LaneMarks.Mark rightMark;
      private final LaneBrain.Road road;
      private final double rtkOffset;
      private final double rtkSigma;
      private final long rtkWay;
      private final float vEgo;
      private final float yL;
      private final float yL2;
      private final float yR;
      private final float yR2;
      private final float yawRate;

      public Frame(
         long var1,
         float var3,
         float var4,
         float var5,
         float var6,
         float var7,
         float var8,
         float var9,
         float var10,
         LaneMarks.Mark var11,
         LaneMarks.Mark var12,
         LaneBrain.Road var13,
         int var14,
         int var15,
         boolean var16,
         int var17,
         int var18,
         boolean var19,
         float var20,
         float var21,
         float var22,
         float var23,
         boolean var24,
         boolean var25,
         double var26,
         double var28,
         long var30,
         float var32,
         LaneBrain.RoadInfo var33
      ) {
         this.nowMs = var1;
         this.vEgo = var3;
         this.yawRate = var4;
         this.yL = var5;
         this.yR = var6;
         this.pL = var7;
         this.pR = var8;
         this.edgeL = var9;
         this.edgeR = var10;
         this.leftMark = var11;
         this.rightMark = var12;
         this.road = var13;
         this.entrySide = var14;
         this.opChange = var15;
         this.opFinishing = var16;
         this.carsLeft = var17;
         this.carsRight = var18;
         this.oncomingLeft = var19;
         this.yL2 = var20;
         this.yR2 = var21;
         this.pL2 = var22;
         this.pR2 = var23;
         this.bsmL = var24;
         this.bsmR = var25;
         this.rtkOffset = var26;
         this.rtkSigma = var28;
         this.rtkWay = var30;
         this.opLcProb = var32;
         this.info = var33;
      }

      // $VF: synthetic method
      public Frame(
         long var1,
         float var3,
         float var4,
         float var5,
         float var6,
         float var7,
         float var8,
         float var9,
         float var10,
         LaneMarks.Mark var11,
         LaneMarks.Mark var12,
         LaneBrain.Road var13,
         int var14,
         int var15,
         boolean var16,
         int var17,
         int var18,
         boolean var19,
         float var20,
         float var21,
         float var22,
         float var23,
         boolean var24,
         boolean var25,
         double var26,
         double var28,
         long var30,
         float var32,
         LaneBrain.RoadInfo var33,
         int var34,
         DefaultConstructorMarker var35
      ) {
         if ((var34 & 512) != 0) {
            var11 = null;
         }

         if ((var34 & 1024) != 0) {
            var12 = null;
         }

         if ((var34 & 2048) != 0) {
            var13 = null;
         }

         if ((var34 & 4096) != 0) {
            var14 = 0;
         }

         if ((var34 & 8192) != 0) {
            var15 = 0;
         }

         if ((var34 & 16384) != 0) {
            var16 = false;
         }

         if ((32768 & var34) != 0) {
            var17 = 0;
         }

         if ((65536 & var34) != 0) {
            var18 = 0;
         }

         if ((131072 & var34) != 0) {
            var19 = false;
         }

         if ((262144 & var34) != 0) {
            var20 = Float.NaN;
         }

         if ((524288 & var34) != 0) {
            var21 = Float.NaN;
         }

         if ((1048576 & var34) != 0) {
            var22 = Float.NaN;
         }

         if ((2097152 & var34) != 0) {
            var23 = Float.NaN;
         }

         if ((4194304 & var34) != 0) {
            var24 = false;
         }

         if ((8388608 & var34) != 0) {
            var25 = false;
         }

         if ((16777216 & var34) != 0) {
            var26 = Double.NaN;
         }

         if ((33554432 & var34) != 0) {
            var28 = Double.NaN;
         }

         if ((67108864 & var34) != 0) {
            var30 = 0L;
         }

         if ((134217728 & var34) != 0) {
            var32 = Float.NaN;
         }

         if ((var34 & 268435456) != 0) {
            var33 = null;
         }

         this(
            var1,
            var3,
            var4,
            var5,
            var6,
            var7,
            var8,
            var9,
            var10,
            var11,
            var12,
            var13,
            var14,
            var15,
            var16,
            var17,
            var18,
            var19,
            var20,
            var21,
            var22,
            var23,
            var24,
            var25,
            var26,
            var28,
            var30,
            var32,
            var33
         );
      }

      public final boolean getBsmL() {
         return this.bsmL;
      }

      public final boolean getBsmR() {
         return this.bsmR;
      }

      public final int getCarsLeft() {
         return this.carsLeft;
      }

      public final int getCarsRight() {
         return this.carsRight;
      }

      public final float getEdgeL() {
         return this.edgeL;
      }

      public final float getEdgeR() {
         return this.edgeR;
      }

      public final int getEntrySide() {
         return this.entrySide;
      }

      public final LaneBrain.RoadInfo getInfo() {
         return this.info;
      }

      public final LaneMarks.Mark getLeftMark() {
         return this.leftMark;
      }

      public final long getNowMs() {
         return this.nowMs;
      }

      public final boolean getOncomingLeft() {
         return this.oncomingLeft;
      }

      public final int getOpChange() {
         return this.opChange;
      }

      public final boolean getOpFinishing() {
         return this.opFinishing;
      }

      public final float getOpLcProb() {
         return this.opLcProb;
      }

      public final float getPL() {
         return this.pL;
      }

      public final float getPL2() {
         return this.pL2;
      }

      public final float getPR() {
         return this.pR;
      }

      public final float getPR2() {
         return this.pR2;
      }

      public final LaneMarks.Mark getRightMark() {
         return this.rightMark;
      }

      public final LaneBrain.Road getRoad() {
         return this.road;
      }

      public final double getRtkOffset() {
         return this.rtkOffset;
      }

      public final double getRtkSigma() {
         return this.rtkSigma;
      }

      public final long getRtkWay() {
         return this.rtkWay;
      }

      public final float getVEgo() {
         return this.vEgo;
      }

      public final float getYL() {
         return this.yL;
      }

      public final float getYL2() {
         return this.yL2;
      }

      public final float getYR() {
         return this.yR;
      }

      public final float getYR2() {
         return this.yR2;
      }

      public final float getYawRate() {
         return this.yawRate;
      }
   }

   public static final class Live {
      public static final LaneBrain.Live INSTANCE = new LaneBrain.Live();
      private static volatile LaneBrain brain;

      private Live() {
      }

      public final void forgetCount(Context var1, long var2) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         LaneBrain var4 = brain;
         if (var4 != null) {
            if (var4.getMyCounts().remove(var2) != null) {
               Function0 var7 = var4.getOnCountsChanged();
               if (var7 != null) {
                  var7.invoke();
               }
            }
         } else {
            File var6 = new File(var1.getFilesDir(), "lane_counts.json");

            try {
               JSONObject var8 = new JSONObject(FilesKt.readText$default(var6, null, 1, null));
               var8.remove(String.valueOf(var2));
               String var9 = var8.toString();
               Intrinsics.checkNotNullExpressionValue(var9, "toString(...)");
               FilesKt.writeText$default(var6, var9, null, 2, null);
            } catch (Exception var5) {
            }
         }
      }

      public final LaneBrain getBrain() {
         return brain;
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      public final void rememberCount(Context var1, long var2, int var4) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         LaneBrain var5 = brain;
         if (var5 != null) {
            var5.getMyCounts().put(var2, var4);
            Function0 var11 = var5.getOnCountsChanged();
            if (var11 != null) {
               var11.invoke();
            }
         } else {
            File var12 = new File(var1.getFilesDir(), "lane_counts.json");

            try {
               var9 = new JSONObject(FilesKt.readText$default(var12, null, 1, null));
            } catch (Exception var8) {
               try {
                  var9 = new JSONObject();
               } catch (Exception var7) {
                  return;
               }
            }

            try {
               var9.put(String.valueOf(var2), var4);
               String var10 = var9.toString();
               Intrinsics.checkNotNullExpressionValue(var10, "toString(...)");
               FilesKt.writeText$default(var12, var10, null, 2, null);
            } catch (Exception var6) {
            }
         }
      }

      public final int savedCount(Context var1, long var2) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         LaneBrain var6 = brain;
         if (var6 != null) {
            HashMap var9 = var6.getMyCounts();
            if (var9 != null) {
               Integer var10 = (Integer)var9.get(var2);
               if (var10 != null) {
                  int var12 = var10;
                  return var12;
               }
            }
         }

         byte var4 = 0;

         int var5;
         try {
            File var7 = new File(var1.getFilesDir(), "lane_counts.json");
            JSONObject var11 = new JSONObject(FilesKt.readText$default(var7, null, 1, null));
            var5 = var11.optInt(String.valueOf(var2), 0);
         } catch (Exception var8) {
            return var4;
         }

         return var5;
      }

      public final void setBrain(LaneBrain var1) {
         brain = var1;
      }
   }

   private static final class Pending {
      private final long atMs;
      private final int dir;

      public Pending(int var1, long var2) {
         this.dir = var1;
         this.atMs = var2;
      }

      public final long getAtMs() {
         return this.atMs;
      }

      public final int getDir() {
         return this.dir;
      }
   }

   public static final class Road {
      private final boolean highway;
      private final long id;
      private final String kind;
      private final int lanesOurWay;
      private final String name;
      private final boolean oneway;

      public Road(long var1, String var3, String var4, boolean var5, boolean var6, int var7) {
         Intrinsics.checkNotNullParameter(var3, "name");
         Intrinsics.checkNotNullParameter(var4, "kind");
         super();
         this.id = var1;
         this.name = var3;
         this.kind = var4;
         this.oneway = var5;
         this.highway = var6;
         this.lanesOurWay = var7;
      }

      public final boolean getHighway() {
         return this.highway;
      }

      public final long getId() {
         return this.id;
      }

      public final String getKind() {
         return this.kind;
      }

      public final int getLanesOurWay() {
         return this.lanesOurWay;
      }

      public final String getName() {
         return this.name;
      }

      public final boolean getOneway() {
         return this.oneway;
      }

      public final boolean getRamp() {
         return StringsKt.endsWith$default(this.kind, "_link", false, 2, null);
      }
   }

   public static final class RoadInfo {
      private final boolean bridge;
      private final int extraL;
      private final int extraR;
      private final String extraWhy;
      private final int hpmsLanes;
      private final String hpmsName;
      private final boolean hpmsRamp;
      private final boolean hpmsSure;
      private final String kindL;
      private final String kindR;
      private final double navDist;
      private final int navStraight;
      private final boolean tunnel;

      public RoadInfo() {
         this(0, null, false, false, false, false, 0, 0, null, null, null, 0, 0.0, 8191, null);
      }

      public RoadInfo(
         int var1,
         String var2,
         boolean var3,
         boolean var4,
         boolean var5,
         boolean var6,
         int var7,
         int var8,
         String var9,
         String var10,
         String var11,
         int var12,
         double var13
      ) {
         Intrinsics.checkNotNullParameter(var2, "hpmsName");
         Intrinsics.checkNotNullParameter(var9, "kindL");
         Intrinsics.checkNotNullParameter(var10, "kindR");
         Intrinsics.checkNotNullParameter(var11, "extraWhy");
         super();
         this.hpmsLanes = var1;
         this.hpmsName = var2;
         this.hpmsRamp = var3;
         this.tunnel = var4;
         this.bridge = var5;
         this.hpmsSure = var6;
         this.extraL = var7;
         this.extraR = var8;
         this.kindL = var9;
         this.kindR = var10;
         this.extraWhy = var11;
         this.navStraight = var12;
         this.navDist = var13;
      }

      // $VF: synthetic method
      public RoadInfo(
         int var1,
         String var2,
         boolean var3,
         boolean var4,
         boolean var5,
         boolean var6,
         int var7,
         int var8,
         String var9,
         String var10,
         String var11,
         int var12,
         double var13,
         int var15,
         DefaultConstructorMarker var16
      ) {
         byte var17 = 0;
         if ((var15 & 1) != 0) {
            var1 = 0;
         }

         String var18 = "";
         if ((var15 & 2) != 0) {
            var2 = "";
         }

         if ((var15 & 4) != 0) {
            var3 = false;
         }

         if ((var15 & 8) != 0) {
            var4 = false;
         }

         if ((var15 & 16) != 0) {
            var5 = false;
         }

         if ((var15 & 32) != 0) {
            var6 = true;
         }

         if ((var15 & 64) != 0) {
            var7 = 0;
         }

         if ((var15 & 128) != 0) {
            var8 = 0;
         }

         if ((var15 & 256) != 0) {
            var9 = "";
         }

         if ((var15 & 512) != 0) {
            var10 = "";
         }

         if ((var15 & 1024) != 0) {
            var11 = var18;
         }

         if ((var15 & 2048) != 0) {
            var12 = var17;
         }

         if ((var15 & 4096) != 0) {
            var13 = Double.NaN;
         }

         this(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
      }

      public final boolean getBridge() {
         return this.bridge;
      }

      public final int getExtraL() {
         return this.extraL;
      }

      public final int getExtraR() {
         return this.extraR;
      }

      public final String getExtraWhy() {
         return this.extraWhy;
      }

      public final int getHpmsLanes() {
         return this.hpmsLanes;
      }

      public final String getHpmsName() {
         return this.hpmsName;
      }

      public final boolean getHpmsRamp() {
         return this.hpmsRamp;
      }

      public final boolean getHpmsSure() {
         return this.hpmsSure;
      }

      public final String getKindL() {
         return this.kindL;
      }

      public final String getKindR() {
         return this.kindR;
      }

      public final double getNavDist() {
         return this.navDist;
      }

      public final int getNavStraight() {
         return this.navStraight;
      }

      public final boolean getTunnel() {
         return this.tunnel;
      }
   }

   public static final class RoadModel {
      private final boolean bridge;
      private final double conf;
      private final int extraL;
      private final int extraR;
      private final String kindL;
      private final String kindR;
      private final int lanes;
      private final String src;
      private final boolean tunnel;

      public RoadModel(int var1, String var2, double var3, int var5, int var6, String var7, String var8, boolean var9, boolean var10) {
         Intrinsics.checkNotNullParameter(var2, "src");
         Intrinsics.checkNotNullParameter(var7, "kindL");
         Intrinsics.checkNotNullParameter(var8, "kindR");
         super();
         this.lanes = var1;
         this.src = var2;
         this.conf = var3;
         this.extraL = var5;
         this.extraR = var6;
         this.kindL = var7;
         this.kindR = var8;
         this.tunnel = var9;
         this.bridge = var10;
      }

      public final boolean getBridge() {
         return this.bridge;
      }

      public final double getConf() {
         return this.conf;
      }

      public final int getExtraL() {
         return this.extraL;
      }

      public final int getExtraR() {
         return this.extraR;
      }

      public final String getKindL() {
         return this.kindL;
      }

      public final String getKindR() {
         return this.kindR;
      }

      public final int getLanes() {
         return this.lanes;
      }

      public final String getSrc() {
         return this.src;
      }

      public final int getTotal() {
         int var1;
         if (this.lanes > 0) {
            var1 = this.lanes + this.extraL + this.extraR;
         } else {
            var1 = 0;
         }

         return var1;
      }

      public final boolean getTunnel() {
         return this.tunnel;
      }
   }

   private static final class Vote {
      private final ArrayDeque<long[]> q = new ArrayDeque<>();
      private long used;

      public Vote() {
      }

      public final void clear() {
         this.q.clear();
      }

      public final ArrayDeque<long[]> getQ() {
         return this.q;
      }

      public final long getUsed() {
         return this.used;
      }

      public final void setUsed(long var1) {
         this.used = var1;
      }
   }
}
