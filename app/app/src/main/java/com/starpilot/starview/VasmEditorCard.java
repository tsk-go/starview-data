package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.View.MeasureSpec;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.Result.Companion;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class VasmEditorCard extends LinearLayout {
   private String active;
   private final ArrayList<int[]> camLeft;
   private final ArrayList<int[]> camRight;
   private String cameraError;
   private final VasmEditorCard.Board canvas;
   private final TextView clearBtn;
   private boolean editable;
   private int height0;
   private Bitmap image;
   private String lastEditor;
   private final TextView leftBtn;
   private String localNote;
   private final TextView note;
   private final TextView parkedNote;
   private final TextView rightBtn;
   private final Function1<JSONObject, Unit> save;
   private final TextView saveBtn;
   private final TextView snapBtn;
   private boolean snapshotAsked;
   private final TextView status;
   private final CarUi ui;
   private final TextView undoBtn;
   private boolean warming;
   private int width0;

   // $VF: synthetic method
   public static Unit $r8$lambda$6wWsblr0Hd4DofwvnJFYDSKtj_E(VasmEditorCard var0) {
      return leftBtn$lambda$1(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KcT57r8uhNK3cZ91_OZwJ7bwyro/* $VF was: $r8$lambda$KcT57r8uhNK3cZ91_OZwJ7bwyro*/(VasmEditorCard var0) {
      return clearBtn$lambda$5(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$TRpGqW56hUrxZ8MJl5XDZCes_zc(VasmEditorCard var0) {
      return undoBtn$lambda$4(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XMJBBpJtw5ldwUgnuGGi8jD2PQc(VasmEditorCard var0, byte[] var1, String var2) {
      return snapshot$lambda$16(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_UNXXPxyPOhr4dCwGxtB6qXUtA4(VasmEditorCard var0) {
      return snapBtn$lambda$7(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vWt0e6PV_eAfQ_VZCijbmk__G1w/* $VF was: $r8$lambda$vWt0e6PV_eAfQ_VZCijbmk__G1w*/(VasmEditorCard var0) {
      return saveBtn$lambda$6(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xBldGuOjFdm5o1hdZJSzLQjbcMY(VasmEditorCard var0) {
      return rightBtn$lambda$2(var0);
   }

   public VasmEditorCard(Context var1, CarUi var2, Function1<? super JSONObject, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "save");
      super(var1);
      this.ui = var2;
      this.save = var3;
      this.width0 = 1928;
      this.height0 = 1208;
      this.camLeft = new ArrayList<>();
      this.camRight = new ArrayList<>();
      this.active = "cameraRight";
      this.lastEditor = "";
      this.cameraError = "";
      this.localNote = "";
      this.status = this.ui.note("");
      this.note = this.ui.note("");
      TextView var9 = this.ui.note("Turn the vehicle off to change these settings.");
      var9.setTextColor(Color.rgb(255, 184, 101));
      this.parkedNote = var9;
      this.leftBtn = CarUi.button$default(this.ui, "Left", false, new VasmEditorCard$$ExternalSyntheticLambda1(this), 2, null);
      this.rightBtn = CarUi.button$default(this.ui, "Right", false, new VasmEditorCard$$ExternalSyntheticLambda2(this), 2, null);
      this.undoBtn = CarUi.button$default(this.ui, "↶ Undo", false, new VasmEditorCard$$ExternalSyntheticLambda3(this), 2, null);
      this.clearBtn = CarUi.button$default(this.ui, "Clear", false, new VasmEditorCard$$ExternalSyntheticLambda4(this), 2, null);
      this.saveBtn = this.ui.button("Save", true, new VasmEditorCard$$ExternalSyntheticLambda5(this));
      this.snapBtn = CarUi.button$default(this.ui, "Take new snapshot", false, new VasmEditorCard$$ExternalSyntheticLambda6(this), 2, null);
      this.canvas = new VasmEditorCard.Board(this, var1);
      this.setOrientation(1);
      this.setBackground((Drawable)CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 0, null, 6, null));
      this.setPadding(this.ui.dp(18), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
      TextView var10 = new TextView(var1);
      var10.setText("Camera Window Regions");
      var10.setTextColor(CarUi.Companion.getTEXT());
      var10.setTextSize(20.0F);
      var10.setTypeface(Typeface.DEFAULT_BOLD);
      var10.setPadding(this.ui.dp(6), 0, 0, this.ui.dp(6));
      this.addView((View)var10);
      this.addView(
         (View)this.ui
            .note(
               "The display is mirrored: vehicle left is camera right; vehicle right is camera left. Trace visible side glass, leaving pillars and interior out."
            )
      );
      this.addView((View)this.parkedNote);
      this.addView((View)this.status);
      LinearLayout var11 = new LinearLayout(var1);
      var11.setOrientation(0);

      for (View var13 : CollectionsKt.listOf(new TextView[]{this.leftBtn, this.rightBtn, this.undoBtn, this.clearBtn, this.saveBtn})) {
         LayoutParams var4 = new LayoutParams(-2, -2);
         var4.rightMargin = this.ui.dp(10);
         Unit var5 = Unit.INSTANCE;
         var11.addView(var13, (android.view.ViewGroup.LayoutParams)var4);
      }

      var11.addView(new View(var1), (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, 1, 1.0F)));
      var11.addView((View)this.snapBtn);
      HorizontalScrollView var7 = new HorizontalScrollView(var1);
      var7.setHorizontalScrollBarEnabled(false);
      var7.addView((View)var11);
      View var12 = (View)var7;
      LayoutParams var14 = new LayoutParams(-1, -2);
      var14.bottomMargin = this.ui.dp(10);
      Unit var8 = Unit.INSTANCE;
      this.addView(var12, (android.view.ViewGroup.LayoutParams)var14);
      this.addView(this.canvas, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, -2)));
      this.addView((View)this.note);
      this.addView((View)this.ui.note("A saved choice alone does not activate monitoring."));
      this.paint();
   }

   private final void changed() {
      this.localNote = "Draft only; review the regions before saving.";
      this.paint();
   }

   private static final Unit clearBtn$lambda$5(VasmEditorCard var0) {
      var0.pts().clear();
      var0.changed();
      return Unit.INSTANCE;
   }

   private final boolean drawable() {
      boolean var1;
      if (this.editable && VasmGeometry.INSTANCE.supported(this.width0, this.height0) && this.image != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final Unit leftBtn$lambda$1(VasmEditorCard var0) {
      var0.pick("cameraRight");
      return Unit.INSTANCE;
   }

   private final void paint() {
      TextView var8 = this.status;
      String var7;
      if (this.warming) {
         var7 = "Warming up the camera · about 10 seconds…";
      } else {
         boolean var3;
         if (this.cameraError.length() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var7 = this.cameraError;
         } else if (this.image != null) {
            var7 = "Snapshot ready";
         } else {
            var7 = "Take a cabin snapshot while parked.";
         }
      }

      CharSequence var17 = var7;
      var8.setText(var17);
      TextView var18 = this.status;
      boolean var11;
      if (this.cameraError.length() > 0) {
         var11 = 1;
      } else {
         var11 = 0;
      }

      if (var11 && !this.warming) {
         var11 = Color.rgb(255, 120, 120);
      } else {
         var11 = CarUi.Companion.getDIM();
      }

      var18.setTextColor(var11);
      this.note.setText(this.localNote);
      TextView var19 = this.note;
      boolean var13;
      if (this.localNote.length() == 0) {
         var13 = 1;
      } else {
         var13 = 0;
      }

      if (var13) {
         var13 = (byte)8;
      } else {
         var13 = (byte)0;
      }

      var19.setVisibility(var13);
      boolean var4 = this.drawable();
      boolean var5 = this.pts().isEmpty();
      Iterator var24 = CollectionsKt.listOf(
            new Pair[]{
               TuplesKt.to(this.leftBtn, Intrinsics.areEqual(this.active, "cameraRight")),
               TuplesKt.to(this.rightBtn, Intrinsics.areEqual(this.active, "cameraLeft"))
            }
         )
         .iterator();

      while (true) {
         boolean var6 = var24.hasNext();
         float var1 = 1.0F;
         if (!var6) {
            TextView var21 = this.undoBtn;
            float var2;
            if (var4 && var5 ^ true) {
               var2 = 1.0F;
            } else {
               var2 = 0.45F;
            }

            var21.setAlpha(var2);
            this.clearBtn.setAlpha(this.undoBtn.getAlpha());
            TextView var22 = this.saveBtn;
            if (var4) {
               var2 = 1.0F;
            } else {
               var2 = 0.45F;
            }

            var22.setAlpha(var2);
            TextView var23 = this.snapBtn;
            if (this.warming) {
               var1 = 0.45F;
            }

            var23.setAlpha(var1);
            this.canvas.invalidate();
            return;
         }

         Pair var9 = (Pair)var24.next();
         TextView var20 = (TextView)var9.component1();
         var6 = (Boolean)var9.component2();
         CarUi var25 = this.ui;
         if (var6) {
            var13 = Color.rgb(150, 110, 240);
         } else {
            var13 = CarUi.Companion.getCARD_ON();
         }

         var20.setBackground((Drawable)CarUi.rounded$default(var25, var13, 12, null, 4, null));
         if (!var4) {
            var1 = 0.45F;
         }

         var20.setAlpha(var1);
      }
   }

   private final void pick(String var1) {
      if (this.drawable()) {
         this.active = var1;
         this.paint();
      }
   }

   private final ArrayList<int[]> pts() {
      ArrayList var1;
      if (Intrinsics.areEqual(this.active, "cameraLeft")) {
         var1 = this.camLeft;
      } else {
         var1 = this.camRight;
      }

      return var1;
   }

   private static final Unit rightBtn$lambda$2(VasmEditorCard var0) {
      var0.pick("cameraLeft");
      return Unit.INSTANCE;
   }

   private static final Unit saveBtn$lambda$6(VasmEditorCard var0) {
      var0.trySave();
      return Unit.INSTANCE;
   }

   private static final Unit snapBtn$lambda$7(VasmEditorCard var0) {
      var0.snapshot();
      return Unit.INSTANCE;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final Unit snapshot$lambda$16(VasmEditorCard var0, byte[] var1, String var2) {
      var0.warming = false;
      Bitmap var3 = null;
      Object var4 = null;
      if (var1 != null) {
         label50:
         try {
            Companion var10 = Result.Companion;
            var3 = (Bitmap)KtCompat.resultOf(BitmapFactory.decodeByteArray(var1, 0, var1.length));
         } catch (Throwable var7) {
            Companion var5 = Result.Companion;
            var3 = (Bitmap)KtCompat.resultOf(ResultKt.createFailure(var7));
            break label50;
         }

         if (KtCompat.isFailure(var3)) {
            var3 = (Bitmap)var4;
         }

         var3 = var3;
      }

      if (var3 != null) {
         var0.image = var3;
      }

      String var8;
      if (var3 != null) {
         var8 = "";
      } else if (var2 == null) {
         if (var1 != null) {
            var8 = "Camera frame could not be displayed.";
         } else {
            var8 = "No cabin frame was available. Try taking a new snapshot.";
         }
      } else {
         var8 = var2;
      }

      var0.cameraError = var8;
      var0.paint();
      return Unit.INSTANCE;
   }

   private final void trySave() {
      if (this.drawable()) {
         JSONObject var1 = VasmGeometry.INSTANCE.draft(this.width0, this.height0, this.camLeft, this.camRight);
         if (var1 == null) {
            this.localNote = "Draw 3–32 vertices around at least one window. Regions cannot cross or leave the frame.";
            this.paint();
         } else {
            this.localNote = "";
            this.paint();
            this.save.invoke(var1);
         }
      }
   }

   private static final Unit undoBtn$lambda$4(VasmEditorCard var0) {
      ArrayList var1 = var0.pts();
      if (!var1.isEmpty()) {
         var1.remove(var1.size() - 1);
         var0.changed();
      }

      return Unit.INSTANCE;
   }

   private static final void update$read(JSONArray var0, List<int[]> var1) {
      var1.clear();
      int var2 = 0;

      for (int var3 = var0.length(); var2 < var3; var2++) {
         JSONArray var4 = var0.optJSONArray(var2);
         if (var4 != null) {
            var1.add(new int[]{MathKt.roundToInt(var4.optDouble(0)), MathKt.roundToInt(var4.optDouble(1))});
         }
      }
   }

   public final void snapshot() {
      Galaxy7 var1 = Galaxy7.Companion.current();
      if (var1 != null) {
         if (!this.warming) {
            this.warming = true;
            this.cameraError = "";
            this.paint();
            JSONObject var2 = new JSONObject().put("camera", "cabin");
            Intrinsics.checkNotNullExpressionValue(var2, "put(...)");
            var1.postImage("/api/cameras/snapshot", var2, new VasmEditorCard$$ExternalSyntheticLambda0(this));
         }
      }
   }

   public final void update(JSONObject var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "page");
      byte var3 = 0;
      boolean var4 = var1.optBoolean("parked", false);
      if (var4 && var2) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.editable = var2;
      TextView var5 = this.parkedNote;
      if (var4) {
         var3 = 8;
      }

      JSONObject var6;
      label52: {
         var5.setVisibility(var3);
         var6 = var1.optJSONObject("editor");
         if (var6 != null) {
            String var11 = var6.toString();
            var7 = var11;
            if (var11 != null) {
               break label52;
            }
         }

         var7 = "null";
      }

      if (!Intrinsics.areEqual(var7, this.lastEditor)) {
         this.lastEditor = var7;
         JSONArray var12 = null;
         JSONArray var8;
         if (var6 != null) {
            var8 = var6.optJSONArray("cameraLeft");
         } else {
            var8 = null;
         }

         if (var6 != null) {
            var12 = var6.optJSONArray("cameraRight");
         }

         if (var6 != null && var8 != null && var12 != null) {
            this.width0 = var6.optInt("width", 1928);
            this.height0 = var6.optInt("height", 1208);
            update$read(var8, this.camLeft);
            update$read(var12, this.camRight);
            String var9;
            if (VasmGeometry.INSTANCE.supported(this.width0, this.height0)) {
               var9 = "";
            } else {
               var9 = "This saved camera size cannot be edited here. Choose a supported size and redraw the regions.";
            }

            this.localNote = var9;
         } else {
            this.localNote = "Camera regions are unavailable. Restore the saved settings before editing.";
         }
      }

      if (var4 && !this.snapshotAsked) {
         this.snapshotAsked = true;
         this.snapshot();
      }

      this.paint();
   }

   private final class Board extends View {
      private int drag;
      private String dragSide;
      private final Paint p;
      final VasmEditorCard this$0;

      public Board(Context var1, Context var2) {
         Intrinsics.checkNotNullParameter(var2, "ctx");
         this.this$0 = var1;
         super(var2);
         this.p = new Paint(3);
         this.drag = -1;
         this.dragSide = "";
      }

      private final int[] source(float var1, float var2) {
         var1 /= this.getWidth();
         var2 /= this.getHeight();
         boolean var4 = false;
         boolean var3;
         if (0.0F <= var1 && var1 <= 1.0F) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var3 = var4;
            if (0.0F <= var2) {
               var3 = var4;
               if (var2 <= 1.0F) {
                  var3 = true;
               }
            }

            if (var3) {
               return new int[]{MathKt.roundToInt((1 - var1) * this.this$0.width0), MathKt.roundToInt(this.this$0.height0 * var2)};
            }
         }

         return null;
      }

      private final float sx(int var1) {
         return this.getWidth() - (float)var1 * this.getWidth() / this.this$0.width0;
      }

      private final float sy(int var1) {
         return (float)var1 * this.getHeight() / this.this$0.height0;
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         float var2 = this.getWidth();
         float var3 = this.getHeight();
         this.p.setStyle(Style.FILL);
         this.p.setColor(Color.rgb(21, 24, 33));
         var1.drawRect(0.0F, 0.0F, var2, var3, this.p);
         Bitmap var5 = this.this$0.image;
         if (var5 != null) {
            var1.save();
            var1.scale(-1.0F, 1.0F, var2 / 2, 0.0F);
            var1.drawBitmap(var5, null, new RectF(0.0F, 0.0F, var2, var3), this.p);
            var1.restore();
         }

         var3 = Math.max(5.0F, var2 / 320.0F);

         for (Pair var7 : CollectionsKt.listOf(
            new Pair[]{TuplesKt.to(this.this$0.camRight, Color.rgb(94, 229, 238)), TuplesKt.to(this.this$0.camLeft, Color.rgb(255, 184, 101))}
         )) {
            ArrayList var6 = (ArrayList)var7.component1();
            int var4 = ((Number)var7.component2()).intValue();
            if (!var6.isEmpty()) {
               Path var13 = new Path();
               var13.moveTo(this.sx(((int[])var6.get(0))[0]), this.sy(((int[])var6.get(0))[1]));

               for (int[] var9 : CollectionsKt.drop(var6, 1)) {
                  var13.lineTo(this.sx(var9[0]), this.sy(var9[1]));
               }

               if (var6.size() >= 3) {
                  var13.close();
                  this.p.setStyle(Style.FILL);
                  this.p.setColor(Color.argb(56, Color.red(var4), Color.green(var4), Color.blue(var4)));
                  var1.drawPath(var13, this.p);
               }

               this.p.setStyle(Style.STROKE);
               this.p.setStrokeWidth(Math.max(2.0F, var2 / 640.0F) * 1.5F);
               this.p.setColor(var4);
               var1.drawPath(var13, this.p);
               this.p.setStyle(Style.FILL);
               Iterator var12 = var6.iterator();
               Intrinsics.checkNotNullExpressionValue(var12, "iterator(...)");

               while (var12.hasNext()) {
                  Object var14 = var12.next();
                  Intrinsics.checkNotNullExpressionValue(var14, "next(...)");
                  var14 = (int[])var14;
                  var1.drawCircle(this.sx((int)((Object[])var14)[0]), this.sy((int)((Object[])var14)[1]), var3 * 1.6F, this.p);
               }
            }
         }
      }

      protected void onMeasure(int var1, int var2) {
         var1 = MeasureSpec.getSize(var1);
         this.setMeasuredDimension(var1, (int)((long)var1 * this.this$0.height0 / Math.max(1, this.this$0.width0)));
      }

      public boolean onTouchEvent(MotionEvent var1) {
         Intrinsics.checkNotNullParameter(var1, "e");
         if (!this.this$0.drawable()) {
            return false;
         } else {
            switch (var1.getActionMasked()) {
               case 0:
                  ViewParent var11 = this.getParent();
                  if (var11 != null) {
                     var11.requestDisallowInterceptTouchEvent(true);
                  }

                  int[] var12 = this.source(var1.getX(), var1.getY());
                  if (var12 == null) {
                     return true;
                  }

                  ArrayList var10 = this.this$0.pts();
                  float var2 = (float)this.this$0.ui.dp(20) * this.this$0.width0 / Math.max(1, this.getWidth());
                  List var6 = var10;
                  int var3 = 0;

                  label53: {
                     for (int[] var7 : var6) {
                        boolean var4;
                        if ((float)Math.hypot(var7[0] - var12[0], var7[1] - var12[1]) <= var2) {
                           var4 = true;
                        } else {
                           var4 = false;
                        }

                        if (var4) {
                           break label53;
                        }

                        var3++;
                     }

                     var3 = -1;
                  }

                  if (var3 >= 0) {
                     this.drag = var3;
                     this.dragSide = this.this$0.active;
                  } else if (var10.size() < 32) {
                     var10.add(var12);
                     this.this$0.changed();
                  } else {
                     this.this$0.localNote = "Each side allows at most 32 vertices.";
                     this.this$0.paint();
                  }
                  break;
               case 1:
               case 3:
                  this.drag = -1;
                  ViewParent var9 = this.getParent();
                  if (var9 != null) {
                     var9.requestDisallowInterceptTouchEvent(false);
                  }
                  break;
               case 2:
                  if (this.drag >= 0 && Intrinsics.areEqual(this.dragSide, this.this$0.active)) {
                     int[] var5 = this.source(RangesKt.coerceIn(var1.getX(), 0.0F, this.getWidth()), RangesKt.coerceIn(var1.getY(), 0.0F, this.getHeight()));
                     if (var5 != null) {
                        VasmEditorCard var8 = this.this$0;
                        var8.pts().set(this.drag, var5);
                        var8.changed();
                     }
                  }
            }

            return true;
         }
      }
   }
}
