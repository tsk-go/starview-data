package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewParent;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class TrackMapView extends View {
   private double cx;
   private double cy;
   private final float d;
   private final Paint dot;
   private boolean fitted;
   private final GestureDetector gest;
   private final Paint line;
   private final double[] mx;
   private final double[] my;
   private long nowMs;
   private final Function1<Long, Unit> onPick;
   private final List<DriveTrack.Pt> pts;
   private final Paint ring;
   private final ScaleGestureDetector scaler;
   private final Paint seg;
   private final long segStartMs;
   private final Paint tileP;
   private final MapTiles tiles;
   private final Paint txt;
   private double zoom;

   // $VF: synthetic method
   public static Unit $r8$lambda$N8surjs2ey4Rw3b8vitc16hxTag(TrackMapView var0) {
      return tiles$lambda$1(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$lG4EEeQ3ZaHtsl7GsTaRkKotZi4(TrackMapView var0) {
      tiles$lambda$1$lambda$0(var0);
   }

   public TrackMapView(Context var1, List<DriveTrack.Pt> var2, long var3, Function1<? super Long, Unit> var5) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "pts");
      Intrinsics.checkNotNullParameter(var5, "onPick");
      super(var1);
      this.pts = var2;
      this.segStartMs = var3;
      this.onPick = var5;
      this.d = this.getResources().getDisplayMetrics().density;
      MapTiles var9 = new MapTiles(var1, new TrackMapView$$ExternalSyntheticLambda1(this));
      var9.setHost(Link.INSTANCE.getHost());
      var9.setDirectKey(Nav.INSTANCE.getTileKey());
      this.tiles = var9;
      int var8 = this.pts.size();
      double[] var10 = new double[var8];
      byte var7 = 0;

      for (int var6 = 0; var6 < var8; var6++) {
         var10[var6] = Nav.INSTANCE.mercX(this.pts.get(var6).getLon());
      }

      this.mx = var10;
      var8 = this.pts.size();
      double[] var11 = new double[var8];

      for (int var17 = var7; var17 < var8; var17++) {
         var11[var17] = Nav.INSTANCE.mercY(this.pts.get(var17).getLat());
      }

      this.my = var11;
      this.cx = 0.5;
      this.cy = 0.5;
      this.zoom = 14.0;
      this.nowMs = Long.MIN_VALUE;
      Paint var12 = new Paint(1);
      var12.setStyle(Style.STROKE);
      var12.setStrokeWidth(this.d * 5.0F);
      var12.setColor(-13664019);
      var12.setStrokeCap(Cap.ROUND);
      var12.setStrokeJoin(Join.ROUND);
      this.line = var12;
      Paint var13 = new Paint(1);
      var13.setColor(-1);
      var13.setStyle(Style.FILL);
      this.seg = var13;
      Paint var14 = new Paint(1);
      var14.setColor(-16121);
      var14.setStyle(Style.FILL);
      this.dot = var14;
      Paint var15 = new Paint(1);
      var15.setColor(-16777216);
      var15.setStyle(Style.STROKE);
      var15.setStrokeWidth(this.d * 2.0F);
      this.ring = var15;
      Paint var16 = new Paint(1);
      var16.setColor(-1);
      var16.setTextSize(this.d * 13.0F);
      var16.setShadowLayer(this.d * 3.0F, 0.0F, 0.0F, -16777216);
      this.txt = var16;
      this.tileP = new Paint(2);
      this.scaler = new ScaleGestureDetector(var1, (OnScaleGestureListener)(new SimpleOnScaleGestureListener() {

         public boolean onScale(ScaleGestureDetector var1) {
            Intrinsics.checkNotNullParameter(var1, "det");
            TrackMapView.this.zoom = RangesKt.coerceIn(TrackMapView.this.zoom + Math.log(var1.getScaleFactor()) / Math.log(2.0), 3.0, 18.5);
            TrackMapView.this.invalidate();
            return true;
         }
      }));
      this.gest = new GestureDetector(var1, (OnGestureListener)(new SimpleOnGestureListener() {

         public boolean onDoubleTap(MotionEvent var1) {
            Intrinsics.checkNotNullParameter(var1, "e");
            TrackMapView.this.zoom = RangesKt.coerceAtMost(TrackMapView.this.zoom + 1, 18.5);
            TrackMapView.this.invalidate();
            return true;
         }

         public boolean onDown(MotionEvent var1) {
            Intrinsics.checkNotNullParameter(var1, "e");
            return true;
         }

         public boolean onScroll(MotionEvent var1, MotionEvent var2x, float var3x, float var4) {
            Intrinsics.checkNotNullParameter(var2x, "e2");
            TrackMapView.this.cx = TrackMapView.this.cx + var3x / TrackMapView.this.world();
            TrackMapView.this.cy = TrackMapView.this.cy + var4 / TrackMapView.this.world();
            TrackMapView.this.invalidate();
            return true;
         }

         public boolean onSingleTapConfirmed(MotionEvent var1) {
            Intrinsics.checkNotNullParameter(var1, "e");
            int var6 = -1;
            float var2x = TrackMapView.this.d * 40.0F;
            int var5x = 0;
            int var7x = TrackMapView.this.pts.size();

            while (var5x < var7x) {
               float var4 = (float)Math.hypot(TrackMapView.this.sx(TrackMapView.this.mx[var5x]) - var1.getX(), TrackMapView.this.sy(TrackMapView.this.my[var5x]) - var1.getY());
               float var3x = var2x;
               if (var4 < var2x) {
                  var3x = var4;
                  var6 = var5x;
               }

               var5x++;
               var2x = var3x;
            }

            if (var6 >= 0) {
               TrackMapView.this.setNowMs(TrackMapView.this.pts.get(var6).getT());
               TrackMapView.this.onPick.invoke(TrackMapView.this.pts.get(var6).getT());
            }

            return true;
         }
      }));
   }

   private final void fit() {
      if (!this.pts.isEmpty() && this.getWidth() != 0) {
         double var9 = ArraysKt.minOrThrow(this.mx);
         double var7 = ArraysKt.maxOrThrow(this.mx);
         double var1 = ArraysKt.minOrThrow(this.my);
         double var3 = ArraysKt.maxOrThrow(this.my);
         double var5 = 2;
         this.cx = (var9 + var7) / var5;
         this.cy = (var1 + var3) / var5;
         float var12 = this.getWidth();
         float var11 = 60;
         var5 = var12 - this.d * var11;
         var9 = Math.max(var7 - var9, 1.0E-7);
         var7 = 512;
         this.zoom = RangesKt.coerceIn(
            Math.min(
               Math.log(var5 / (var9 * var7)) / Math.log(2.0),
               Math.log((this.getHeight() - var11 * this.d) / (Math.max(var3 - var1, 1.0E-7) * var7)) / Math.log(2.0)
            ),
            3.0,
            17.5
         );
         this.fitted = true;
      }
   }

   private final float sx(double var1) {
      return (float)((var1 - this.cx) * this.world() + this.getWidth() / 2.0);
   }

   private final float sy(double var1) {
      return (float)((var1 - this.cy) * this.world() + this.getHeight() / 2.0);
   }

   private static final Unit tiles$lambda$1(TrackMapView var0) {
      var0.post(new TrackMapView$$ExternalSyntheticLambda0(var0));
      return Unit.INSTANCE;
   }

   private static final void tiles$lambda$1$lambda$0(TrackMapView var0) {
      var0.invalidate();
   }

   private final double world() {
      return Math.pow(2.0, this.zoom) * 512.0;
   }

   public final long getNowMs() {
      return this.nowMs;
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      if (!this.fitted) {
         this.fit();
      }

      var1.drawColor(-15000028);
      int var15 = RangesKt.coerceIn((int)Math.floor(this.zoom), 0, 18);
      int var16 = 1 << var15;
      double var2 = this.world() / (var16 * 512.0);
      int var7 = (int)Math.floor((this.cx - this.getWidth() / 2.0 / this.world()) * var16);
      int var14 = (int)Math.floor((this.cx + this.getWidth() / 2.0 / this.world()) * var16);
      int var11 = (int)Math.floor((this.cy - this.getHeight() / 2.0 / this.world()) * var16);
      int var8 = (int)Math.floor((this.cy + this.getHeight() / 2.0 / this.world()) * var16);
      HashSet var23 = new HashSet();
      String var24 = "dark";
      int var12 = var11;
      HashSet var48;
      if (var11 > var8) {
         var48 = var23;
      } else {
         while (true) {
            int var13 = var7;
            int var9;
            String var25;
            if (var7 <= var14) {
               var9 = var7;
               var25 = var24;
               var7 = var8;

               while (true) {
                  if (var12 >= 0 && var12 < var16) {
                     var8 = (var13 % var16 + var16) % var16;
                     var23.add(MapTiles.Companion.key(var25, var15, var8, var12));
                     Bitmap var46 = this.tiles.get(var25, var15, var8, var12);
                     if (var46 != null) {
                        float var6 = this.sx((double)var13 / var16);
                        float var4 = this.sy((double)var12 / var16);
                        float var5 = (float)(512 * var2);
                        var1.drawBitmap(var46, null, new RectF(var6, var4, var6 + var5 + 0.6F, var4 + var5 + 0.6F), this.tileP);
                     }
                  }

                  HashSet var47 = var23;
                  var7 = var7;
                  var9 = var9;
                  var25 = var25;
                  var23 = var23;
                  if (var13 == var14) {
                     break;
                  }

                  var13++;
                  var7 = var7;
                  var25 = var25;
                  var23 = var47;
                  var9 = var9;
               }
            } else {
               var9 = var7;
               var7 = var8;
               var25 = var24;
            }

            var48 = var23;
            if (var12 == var7) {
               break;
            }

            var12++;
            var24 = var25;
            var8 = var7;
            var7 = var9;
         }
      }

      this.tiles.setWanted(var48);
      if (this.pts.isEmpty()) {
         var1.drawText("No path for this drive (the tablet wasn't recording one)", 16 * this.d, this.getHeight() / 2.0F, this.txt);
      } else {
         Path var43 = new Path();
         var43.moveTo(this.sx(this.mx[0]), this.sy(this.my[0]));
         var7 = 1;

         for (int var38 = this.pts.size(); var7 < var38; var7++) {
            var43.lineTo(this.sx(this.mx[var7]), this.sy(this.my[var7]));
         }

         var1.drawPath(var43, this.line);
         long var17 = -1L;
         int var39 = 0;

         for (int var40 = this.pts.size(); var39 < var40; var39++) {
            long var19 = (this.pts.get(var39).getT() - this.segStartMs) / 60000L;
            if (var19 != var17) {
               var1.drawCircle(this.sx(this.mx[var39]), this.sy(this.my[var39]), this.d * 3.2F, this.seg);
               var17 = var19;
            }
         }

         float var28 = this.sx(this.mx[0]);
         float var27 = 8;
         var1.drawText("Start", var28 + this.d * var27, this.sy(this.my[0]) - var27 * this.d, this.txt);
         if (this.nowMs != Long.MIN_VALUE) {
            Iterator var51 = CollectionsKt.getIndices(this.pts).iterator();
            Object var44;
            if (!var51.hasNext()) {
               var44 = null;
            } else {
               var44 = (Integer)var51.next();
               if (var51.hasNext()) {
                  var7 = var44.intValue();
                  var17 = Math.abs(this.pts.get(var7).getT() - this.nowMs);

                  while (true) {
                     var24 = (String)var51.next();
                     var8 = ((Number)var24).intValue();
                     long var21 = Math.abs(this.pts.get(var8).getT() - this.nowMs);
                     long var42 = var17;
                     if (var17 > var21) {
                        var44 = var24;
                        var42 = var21;
                     }

                     if (!var51.hasNext()) {
                        break;
                     }

                     var17 = var42;
                  }
               }
            }

            var44 = var44;
            if (var44 == null) {
               return;
            }

            var7 = var44;
            var1.drawCircle(this.sx(this.mx[var7]), this.sy(this.my[var7]), this.d * 8.0F, this.dot);
            var1.drawCircle(this.sx(this.mx[var7]), this.sy(this.my[var7]), this.d * 8.0F, this.ring);
         }
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      this.scaler.onTouchEvent(var1);
      if (!this.scaler.isInProgress()) {
         this.gest.onTouchEvent(var1);
      }

      ViewParent var2 = this.getParent();
      if (var2 != null) {
         var2.requestDisallowInterceptTouchEvent(true);
      }

      return true;
   }

   public final void setNowMs(long var1) {
      this.nowMs = var1;
      this.invalidate();
   }
}
