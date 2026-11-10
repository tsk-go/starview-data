package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.FloatRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;

public final class MaskEditor extends View {
   private final Path all;
   private final LinearLayout bar;
   private final ArrayList<Float> cur;
   private final float d;
   private final Detector det;
   private float downX;
   private float downY;
   private int dragP;
   private int dragV;
   private final Paint fill;
   private Bitmap img;
   private final Paint line;
   private boolean moved;
   private final Function1<Boolean, Unit> onCamera;
   private final Function0<Unit> onDone;
   private final Function1<Integer, Unit> paintCam;
   private final Function1<Integer, Unit> paintMode;
   private final Path path;
   private final RectF pic;
   private final Runnable poll;
   private ArrayList<float[]> polys;
   private final LinearLayout row;
   private int sel;
   private final Paint text;
   private final CarUi ui;
   private boolean wide;

   // $VF: synthetic method
   public static Unit $r8$lambda$8q4AZ24QdFtUahqEcARPS3IRYGE(MaskEditor var0) {
      return _init_$lambda$7(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Nswj9WHyfdElaIWZJ2Yo3TTp9wY(MaskEditor var0) {
      return _init_$lambda$5(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$aQbqzgZb4NtrtRLgd6dt2MxxFzU(MaskEditor var0) {
      return _init_$lambda$6(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lwquJjQvRtLi7IoDij7AcwU6f24(MaskEditor var0) {
      return _init_$lambda$8(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pFd_l_YbOH__2XiRxcCBSkQ6IK4/* $VF was: $r8$lambda$pFd_l_YbOH__2XiRxcCBSkQ6IK4*/(MaskEditor var0, int var1) {
      return _init_$lambda$9(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$uv9Du3qhzCE6fdl4tn6cF2bFFeg(MaskEditor var0, int var1) {
      return _init_$lambda$4(var0, var1);
   }

   public MaskEditor(Context var1, Detector var2, boolean var3, Function1<? super Boolean, Unit> var4, Function0<Unit> var5) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "det");
      Intrinsics.checkNotNullParameter(var4, "onCamera");
      Intrinsics.checkNotNullParameter(var5, "onDone");
      super(var1);
      this.det = var2;
      this.wide = var3;
      this.onCamera = var4;
      this.onDone = var5;
      this.ui = new CarUi(var1, false, 2, null);
      this.d = var1.getResources().getDisplayMetrics().density;
      this.polys = new ArrayList<>(DetMasks.INSTANCE.get(this.wide));
      this.cur = new ArrayList<>();
      this.sel = -1;
      this.dragP = -2;
      this.dragV = -1;
      this.pic = new RectF();
      this.fill = new Paint(1);
      Paint var9 = new Paint(1);
      var9.setStyle(Style.STROKE);
      var9.setStrokeWidth(3 * this.d);
      this.line = var9;
      Paint var10 = new Paint(1);
      var10.setColor(-1);
      var10.setTextSize(17 * this.d);
      var10.setTextAlign(Align.CENTER);
      this.text = var10;
      this.path = new Path();
      this.all = new Path();
      LinearLayout var11 = new LinearLayout(var1);
      var11.setOrientation(1);
      var11.setGravity(1);
      var11.setPadding(this.ui.dp(12), this.ui.dp(12), this.ui.dp(12), this.ui.dp(8));
      this.bar = var11;
      LinearLayout var12 = new LinearLayout(var1);
      var12.setOrientation(0);
      this.row = var12;
      this.poll = new Runnable() {

         @Override
         public void run() {
            Bitmap var2x = MaskEditor.this.det.getSnap();
            if (var2x != null && MaskEditor.this.det.getSnapWide() == MaskEditor.this.getWide()) {
               MaskEditor.this.img = var2x;
               MaskEditor.this.invalidate();
            }

            Detector var3x = MaskEditor.this.det;
            byte var1x;
            if (MaskEditor.this.getWide()) {
               var1x = 2;
            } else {
               var1x = 1;
            }

            var3x.setSnapWant(var1x);
            MaskEditor.this.postDelayed(this, 1000L);
         }
      };
      this.setBackgroundColor(Color.rgb(11, 13, 18));
      Pair var17 = this.ui.chips(CollectionsKt.listOf(new String[]{"Road camera", "Wide camera"}), new MaskEditor$$ExternalSyntheticLambda0(this));
      LinearLayout var13 = (LinearLayout)var17.component1();
      var4 = (Function1)var17.component2();
      this.paintCam = var4;
      var4.invoke(Integer.valueOf(this.wide));
      this.bar.addView((View)this.row, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-2, -2)));
      this.row.addView((View)var13, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.ui.dp(400), -2)));

      for (Pair var21 : CollectionsKt.listOf(
         new Pair[]{
            TuplesKt.to("Finish area", new MaskEditor$$ExternalSyntheticLambda1(this)),
            TuplesKt.to("Undo corner", new MaskEditor$$ExternalSyntheticLambda2(this)),
            TuplesKt.to("Delete area", new MaskEditor$$ExternalSyntheticLambda3(this))
         }
      )) {
         String var14 = (String)var21.component1();
         var5 = (Function0)var21.component2();
         this.row.addView(new View(var1), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.ui.dp(10), 1)));
         this.row.addView((View)CarUi.button$default(this.ui, var14, false, var5, 2, null));
      }

      this.row.addView(new View(var1), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.ui.dp(10), 1)));
      this.row.addView((View)this.ui.button("Done", true, new MaskEditor$$ExternalSyntheticLambda4(this)));
      Pair var6 = this.ui
         .chips(CollectionsKt.listOf(new String[]{"Ignore inside the areas", "Only look inside the areas"}), new MaskEditor$$ExternalSyntheticLambda5(this));
      LinearLayout var15 = (LinearLayout)var6.component1();
      Function1 var7 = (Function1)var6.component2();
      this.paintMode = var7;
      var7.invoke(Integer.valueOf(DetMasks.INSTANCE.keep(this.wide)));
      LinearLayout var8 = this.bar;
      View var20 = (View)var15;
      android.widget.LinearLayout.LayoutParams var16 = new android.widget.LinearLayout.LayoutParams(this.ui.dp(620), -2);
      var16.topMargin = this.ui.dp(8);
      Unit var23 = Unit.INSTANCE;
      var8.addView(var20, (LayoutParams)var16);
   }

   private static final Unit _init_$lambda$4(MaskEditor var0, int var1) {
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      var0.camera(var2);
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$5(MaskEditor var0) {
      var0.close();
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$6(MaskEditor var0) {
      if (!var0.cur.isEmpty()) {
         var0.cur.remove(var0.cur.size() - 1);
         var0.cur.remove(var0.cur.size() - 1);
         var0.invalidate();
      }

      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$7(MaskEditor var0) {
      if (!var0.cur.isEmpty()) {
         var0.cur.clear();
      } else {
         int var3 = var0.polys.size();
         int var4 = var0.sel;
         boolean var2 = false;
         boolean var1 = var2;
         if (var4 >= 0) {
            var1 = var2;
            if (var4 < var3) {
               var1 = true;
            }
         }

         if (var1) {
            var0.polys.remove(var0.sel);
            var0.sel = -1;
            var0.save();
         }
      }

      var0.invalidate();
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$8(MaskEditor var0) {
      var0.close();
      var0.onDone.invoke();
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$9(MaskEditor var0, int var1) {
      DetMasks var4 = DetMasks.INSTANCE;
      boolean var3 = var0.wide;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      var4.setKeep(var3, var2);
      var0.paintMode.invoke(var1);
      var0.invalidate();
      return Unit.INSTANCE;
   }

   private final void camera(boolean var1) {
      if (var1 != this.wide) {
         this.close();
         this.wide = (boolean)var1;
         this.polys = new ArrayList<>(DetMasks.INSTANCE.get((boolean)var1));
         this.sel = -1;
         this.img = null;
         this.paintCam.invoke(Integer.valueOf(var1));
         this.paintMode.invoke(Integer.valueOf(DetMasks.INSTANCE.keep((boolean)var1)));
         this.onCamera.invoke(Boolean.valueOf((boolean)var1));
         this.invalidate();
      }
   }

   private final void close() {
      if (this.cur.size() >= 6) {
         this.polys.add(CollectionsKt.toFloatArray(this.cur));
         this.sel = this.polys.size() - 1;
         this.save();
      }

      this.cur.clear();
      this.invalidate();
   }

   private final Pair<Integer, Integer> corner(float var1, float var2) {
      ObjectRef var4 = new ObjectRef();
      FloatRef var5 = new FloatRef();
      var5.element = 30 * this.d;
      corner$look(this, var1, var2, var5, var4, -1, this.cur);
      Iterator var6 = this.polys.iterator();

      for (int var3 = 0; var6.hasNext(); var3++) {
         float[] var7 = (float[])var6.next();
         Intrinsics.checkNotNull(var7);
         corner$look(this, var1, var2, var5, var4, var3, ArraysKt.toList(var7));
      }

      return (Pair<Integer, Integer>)var4.element;
   }

   private static final void corner$look(
      MaskEditor var0, float var1, float var2, FloatRef var3, ObjectRef<Pair<Integer, Integer>> var4, int var5, List<Float> var6
   ) {
      int var8 = 0;

      for (int var9 = var6.size() / 2; var8 < var9; var8++) {
         float var7 = (float)Math.hypot(
            var0.sx(((Number)var6.get(var8 * 2)).floatValue()) - var1, var0.sy(((Number)var6.get(var8 * 2 + 1)).floatValue()) - var2
         );
         if (var7 < var3.element) {
            var3.element = var7;
            var4.element = (T)TuplesKt.<Integer, Integer>to(var5, var8);
         }
      }
   }

   private final float fx(float var1) {
      return RangesKt.coerceIn((var1 - this.pic.left) / this.pic.width(), 0.0F, 1.0F);
   }

   private final float fy(float var1) {
      return RangesKt.coerceIn((var1 - this.pic.top) / this.pic.height(), 0.0F, 1.0F);
   }

   private final void outline(Path var1, float[] var2) {
      int var3 = 0;

      for (int var4 = var2.length / 2; var3 < var4; var3++) {
         if (var3 == 0) {
            var1.moveTo(this.sx(var2[0]), this.sy(var2[1]));
         } else {
            var1.lineTo(this.sx(var2[var3 * 2]), this.sy(var2[var3 * 2 + 1]));
         }
      }

      var1.close();
   }

   private final void save() {
      DetMasks.INSTANCE.set(this.wide, this.polys);
   }

   private final float sx(float var1) {
      return this.pic.left + this.pic.width() * var1;
   }

   private final float sy(float var1) {
      return this.pic.top + this.pic.height() * var1;
   }

   public final LinearLayout getBar() {
      return this.bar;
   }

   public final boolean getWide() {
      return this.wide;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.post(this.poll);
   }

   protected void onDetachedFromWindow() {
      this.removeCallbacks(this.poll);
      this.det.setSnapWant(0);
      super.onDetachedFromWindow();
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      float var4 = this.bar.getHeight() + 8 * this.d;
      Bitmap var13 = this.img;
      if (var13 == null) {
         String var30;
         if (this.wide) {
            var30 = "wide";
         } else {
            var30 = "road";
         }

         var1.drawText("Waiting for the " + var30 + " camera… (it streams while the car is on)", this.getWidth() / 2.0F, this.getHeight() / 2.0F, this.text);
      } else {
         float var7 = Math.min((float)this.getWidth() / var13.getWidth(), (this.getHeight() - var4 - 40 * this.d) / var13.getHeight());
         float var6 = var13.getWidth() * var7;
         float var2 = var13.getHeight();
         RectF var12 = this.pic;
         float var5 = this.getWidth();
         float var3 = 2;
         var12.set((var5 - var6) / var3, var4, (this.getWidth() + var6) / var3, var4 + var2 * var7);
         var1.drawBitmap(var13, null, this.pic, null);
         boolean var11 = DetMasks.INSTANCE.keep(this.wide);
         this.fill.setColor(Color.argb(170, 114, 114, 114));
         if (var11 && !this.polys.isEmpty()) {
            this.all.rewind();
            Iterator var14 = this.polys.iterator();
            Intrinsics.checkNotNullExpressionValue(var14, "iterator(...)");

            while (var14.hasNext()) {
               float[] var31 = (float[])var14.next();
               Path var23 = this.all;
               Intrinsics.checkNotNull(var31);
               this.outline(var23, var31);
            }

            var1.save();
            var1.clipOutPath(this.all);
            var1.drawRect(this.pic, this.fill);
            var1.restore();
         }

         Iterator var24 = this.polys.iterator();
         int var8 = 0;

         while (var24.hasNext()) {
            int var10 = var8 + 1;
            float[] var32 = (float[])var24.next();
            this.path.rewind();
            Path var33 = this.path;
            Intrinsics.checkNotNull(var32);
            this.outline(var33, var32);
            if (!var11) {
               this.fill.setColor(Color.argb(170, 114, 114, 114));
               var1.drawPath(this.path, this.fill);
            }

            Paint var34 = this.line;
            if (var8 == this.sel) {
               var8 = CarUi.Companion.getACCENT();
            } else {
               var8 = Color.argb(200, 255, 255, 255);
            }

            var34.setColor(var8);
            var1.drawPath(this.path, this.line);
            this.fill.setColor(this.line.getColor());
            int var9 = 0;

            for (int var19 = var32.length / 2; var9 < var19; var9++) {
               var1.drawCircle(this.sx(var32[var9 * 2]), this.sy(var32[var9 * 2 + 1]), 7 * this.d, this.fill);
            }

            var8 = var10;
         }

         if (!this.cur.isEmpty()) {
            this.line.setColor(CarUi.Companion.getACCENT());
            this.fill.setColor(CarUi.Companion.getACCENT());
            int var22 = this.cur.size() / 2;

            for (int var20 = 0; var20 < var22; var20++) {
               Object var25 = this.cur.get(var20 * 2);
               Intrinsics.checkNotNullExpressionValue(var25, "get(...)");
               var3 = this.sx(((Number)var25).floatValue());
               var25 = this.cur.get(var20 * 2 + 1);
               Intrinsics.checkNotNullExpressionValue(var25, "get(...)");
               var2 = this.sy(((Number)var25).floatValue());
               byte var21;
               if (var20 == 0) {
                  var21 = 11;
               } else {
                  var21 = 7;
               }

               var1.drawCircle(var3, var2, var21 * this.d, this.fill);
               if (var20 > 0) {
                  var25 = this.cur.get(var20 * 2 - 2);
                  Intrinsics.checkNotNullExpressionValue(var25, "get(...)");
                  var4 = this.sx(((Number)var25).floatValue());
                  var25 = this.cur.get(var20 * 2 - 1);
                  Intrinsics.checkNotNullExpressionValue(var25, "get(...)");
                  var1.drawLine(var4, this.sy(((Number)var25).floatValue()), var3, var2, this.line);
               }
            }
         }

         String var29;
         if (!this.cur.isEmpty()) {
            var29 = "Tap to add corners. Tap the first corner or Finish area to close it.";
         } else if (var11) {
            var29 = "Draw around what the detector should look at (the road through the windshield). Grey = ignored.";
         } else {
            var29 = "Draw around what the detector should ignore (hood, dash, mirror mount, reflections). Grey = ignored.";
         }

         var1.drawText(var29, this.getWidth() / 2.0F, this.pic.bottom + 28 * this.d, this.text);
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      int var4 = var1.getActionMasked();
      int var2 = -2;
      byte var3 = -1;
      switch (var4) {
         case 0:
            this.downX = var1.getX();
            this.downY = var1.getY();
            this.moved = false;
            Pair var8 = this.corner(var1.getX(), var1.getY());
            if (var8 != null) {
               var2 = ((Number)var8.getFirst()).intValue();
            }

            this.dragP = var2;
            var2 = var3;
            if (var8 != null) {
               var2 = ((Number)var8.getSecond()).intValue();
            }

            this.dragV = var2;
            break;
         case 1:
            if (this.moved) {
               if (this.dragP >= 0) {
                  this.save();
               }

               return true;
            }

            if (this.img == null || !this.pic.contains(var1.getX(), var1.getY())) {
               return true;
            }

            if (this.dragP == -1 && this.dragV == 0 && this.cur.size() >= 6) {
               this.close();
               return true;
            }

            if (this.cur.isEmpty()) {
               List var5 = this.polys;
               ListIterator var6 = var5.listIterator(var5.size());

               while (true) {
                  if (var6.hasPrevious()) {
                     float[] var11 = (float[])var6.previous();
                     DetMasks var7 = DetMasks.INSTANCE;
                     Intrinsics.checkNotNull(var11);
                     if (!var7.inside(var11, this.fx(var1.getX()), this.fy(var1.getY()))) {
                        continue;
                     }

                     var2 = var6.nextIndex();
                     break;
                  }

                  var2 = -1;
                  break;
               }

               if (var2 >= 0) {
                  this.sel = var2;
                  this.invalidate();
                  return true;
               }
            }

            if (this.cur.size() < 200) {
               this.cur.add(this.fx(var1.getX()));
               this.cur.add(this.fy(var1.getY()));
               this.sel = -1;
               this.invalidate();
            }
            break;
         case 2:
            if ((float)Math.hypot(var1.getX() - this.downX, var1.getY() - this.downY) > 8 * this.d) {
               this.moved = true;
            }

            if (this.moved && this.dragP > -2) {
               if (this.dragP == -1) {
                  this.cur.set(this.dragV * 2, this.fx(var1.getX()));
                  this.cur.set(this.dragV * 2 + 1, this.fy(var1.getY()));
               } else {
                  this.polys.get(this.dragP)[this.dragV * 2] = this.fx(var1.getX());
                  this.polys.get(this.dragP)[this.dragV * 2 + 1] = this.fy(var1.getY());
               }

               this.invalidate();
            }
      }

      return true;
   }

   public final void setWide(boolean var1) {
      this.wide = var1;
   }
}
