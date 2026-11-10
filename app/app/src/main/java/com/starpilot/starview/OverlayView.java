package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path.FillType;
import android.graphics.Shader.TileMode;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import androidx.core.graphics.ColorUtils;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KFunction;
import kotlin.text.StringsKt;

public final class OverlayView extends View {
   public static final OverlayView.Companion Companion = new OverlayView.Companion(null);
   private final Bitmap ambBmp;
   private final Paint ambFade;
   private final float[] ambNow;
   private final Paint ambPaint;
   private float blendThrottle;
   private long blinkPhase;
   private final Paint detBg;
   private final Paint detBox;
   private final Paint detText;
   private Detector detector;
   private final Paint fill;
   private long lastDrawNs;
   private Function0<Unit> onResized;
   private final Path path;
   private final Projection proj;
   private boolean sceneMode;
   private final Paint stroke;
   public Telemetry t;
   private final Paint text;
   private final Paint textOutline;
   private final float[] tmp;
   private float torqueSmooth;
   private final LaneTracks tracks;
   private UiStatus uiStatus;

   public OverlayView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.proj = new Projection();
      this.uiStatus = UiStatus.DISENGAGED;
      Paint var4 = new Paint(1);
      var4.setStyle(Style.FILL);
      this.fill = var4;
      Paint var5 = new Paint(1);
      var5.setStyle(Style.STROKE);
      this.stroke = var5;
      Paint var6 = new Paint(1);
      var6.setColor(-1);
      var6.setTextAlign(Align.CENTER);
      var6.setTypeface(Typeface.DEFAULT_BOLD);
      this.text = var6;
      Paint var7 = new Paint(1);
      var7.setColor(-16777216);
      var7.setTextAlign(Align.CENTER);
      var7.setStyle(Style.STROKE);
      var7.setStrokeWidth(3.0F);
      var7.setTypeface(Typeface.DEFAULT_BOLD);
      this.textOutline = var7;
      this.path = new Path();
      this.tmp = new float[2];
      this.blendThrottle = 1.0F;
      this.tracks = new LaneTracks(0.0F, 1, null);
      Bitmap var8 = Bitmap.createBitmap(16, 1, Config.ARGB_8888);
      Intrinsics.checkNotNullExpressionValue(var8, "createBitmap(...)");
      this.ambBmp = var8;
      this.ambPaint = new Paint(2);
      this.ambFade = new Paint();
      float[] var9 = new float[96];

      for (int var3 = 0; var3 < 96; var3++) {
         var9[var3] = -1.0F;
      }

      this.ambNow = var9;
      Paint var10 = new Paint(1);
      var10.setStyle(Style.STROKE);
      var10.setStrokeWidth(4.0F);
      this.detBox = var10;
      Paint var11 = new Paint(1);
      var11.setColor(-1);
      var11.setTextSize(26.0F);
      var11.setFakeBoldText(true);
      this.detText = var11;
      Paint var12 = new Paint();
      var12.setStyle(Style.FILL);
      this.detBg = var12;
   }

   // $VF: synthetic method
   public OverlayView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private final float dp(float var1) {
      return this.getResources().getDisplayMetrics().density * var1;
   }

   private final void drawAmbient(Canvas var1) {
      Projection var15 = this.proj;
      if (var15.getValid() && var15.getFrameW() != 0) {
         float var9 = (float)var15.getVideoTx();
         float var3 = (float)var15.getVideoTy();
         float var8 = (float)(var15.getFrameW() * var15.getVideoScale());
         float var2 = (float)(var15.getFrameH() * var15.getVideoScale()) + var3;
         if (!(var3 < 2.0F) || !(var2 > this.getHeight() - 2.0F)) {
            Detector var18 = this.detector;
            if (var18 != null) {
               int[] var17 = var18.ambient(var15.getWide());
               int[] var16 = var17;
               if (var17 == null) {
                  var17 = var18.ambient(var15.getWide() ^ true);
                  var16 = var17;
                  if (var17 == null) {
                     return;
                  }
               }

               for (int var11 = 0; var11 < 2; var11++) {
                  float var4;
                  if (var11 == 0) {
                     var4 = 0.0F;
                  } else {
                     var4 = var2;
                  }

                  float var5;
                  if (var11 == 0) {
                     var5 = var3;
                  } else {
                     var5 = this.getHeight();
                  }

                  if (!(var5 - var4 < 2.0F)) {
                     for (int var12 = 0; var12 < 16; var12++) {
                        int var14 = var16[var11 * 16 + var12];
                        int var13 = (var11 * 16 + var12) * 3;
                        float var10 = Color.red(var14) * 0.8F;
                        float var7 = Color.green(var14) * 0.8F;
                        float var6 = Color.blue(var14) * 0.8F;
                        if (this.ambNow[var13] < 0.0F) {
                           this.ambNow[var13] = var10;
                           this.ambNow[var13 + 1] = var7;
                           this.ambNow[var13 + 2] = var6;
                        } else {
                           float[] var24 = this.ambNow;
                           var24[var13] += (var10 - this.ambNow[var13]) * 0.08F;
                           float[] var25 = this.ambNow;
                           var14 = var13 + 1;
                           var25[var14] += (var7 - this.ambNow[var13 + 1]) * 0.08F;
                           float[] var26 = this.ambNow;
                           var14 = var13 + 2;
                           var26[var14] += (var6 - this.ambNow[var13 + 2]) * 0.08F;
                        }

                        this.ambBmp.setPixel(var12, 0, Color.rgb((int)this.ambNow[var13], (int)this.ambNow[var13 + 1], (int)this.ambNow[var13 + 2]));
                     }

                     float var20 = 0.0F;
                     var1.drawBitmap(this.ambBmp, null, new RectF(var9, var4, var8 + var9, var5), this.ambPaint);
                     float var19;
                     if (var11 == 0) {
                        var19 = var3;
                     } else {
                        var19 = var2;
                     }

                     if (var11 != 0) {
                        var20 = this.getHeight();
                     }

                     this.ambFade
                        .setShader(
                           (Shader)(new LinearGradient(0.0F, var19, 0.0F, var19 + (var20 - var19) * 0.9F, Color.argb(40, 0, 0, 0), -16777216, TileMode.CLAMP))
                        );
                     var1.drawRect(0.0F, var4, this.getWidth(), var5, this.ambFade);
                  }
               }
            }
         }
      }
   }

   private final void drawBorder(Canvas var1) {
      int var7 = this.uiStatus.getColor();
      boolean var5;
      if (this.getT().s("csc_glow")
         && this.getT().getStarted()
         && (this.getT().getCscControlling() || this.getT().getCscTraining())
         && this.getT().getCruiseEnabled()) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      int var6 = var7;
      if (var5) {
         if (this.getT().getCscTraining()) {
            var5 = Color.rgb(112, 192, 216);
         } else {
            var5 = Companion.cscColor(this.getT().getRoadCurvature());
         }

         float var2 = (float)Math.sin(System.nanoTime() / 1.0E9 * 2 * Math.PI / 4.5);
         var6 = ColorUtils.blendARGB(var7, var5, 0.85F);
         float var3 = this.dp(50.0F);
         var7 = MathKt.roundToInt(200 * (var2 * 0.25F + 0.75F));
         this.fill
            .setShader((Shader)(new LinearGradient(0.0F, 0.0F, var3, 0.0F, Companion.withAlpha(var5, var7), Companion.withAlpha(var5, 0), TileMode.CLAMP)));
         var1.drawRect(0.0F, 0.0F, var3, this.getHeight(), this.fill);
         this.fill
            .setShader(
               (Shader)(
                  new LinearGradient(
                     this.getWidth(), 0.0F, this.getWidth() - var3, 0.0F, Companion.withAlpha(var5, var7), Companion.withAlpha(var5, 0), TileMode.CLAMP
                  )
               )
            );
         var1.drawRect(this.getWidth() - var3, 0.0F, this.getWidth(), this.getHeight(), this.fill);
         this.fill
            .setShader((Shader)(new LinearGradient(0.0F, 0.0F, 0.0F, var3, Companion.withAlpha(var5, var7), Companion.withAlpha(var5, 0), TileMode.CLAMP)));
         var1.drawRect(0.0F, 0.0F, this.getWidth(), var3, this.fill);
         this.fill
            .setShader(
               (Shader)(
                  new LinearGradient(
                     0.0F, this.getHeight(), 0.0F, this.getHeight() - var3, Companion.withAlpha(var5, var7), Companion.withAlpha(var5, 0), TileMode.CLAMP
                  )
               )
            );
         var1.drawRect(0.0F, this.getHeight() - var3, this.getWidth(), this.getHeight(), this.fill);
         this.fill.setShader(null);
      }

      float var8 = this.dp(6.0F);
      this.stroke.setColor(var6);
      this.stroke.setStrokeWidth(var8);
      float var4 = this.dp(26.0F);
      float var9 = 2;
      var1.drawRoundRect(new RectF(var8 / var9, var8 / var9, this.getWidth() - var8 / var9, this.getHeight() - var8 / var9), var4, var4, this.stroke);
   }

   private final void drawDetections(Canvas var1) {
      Detector var8 = this.detector;
      if (var8 != null) {
         if (System.nanoTime() - var8.getDetsAtNs() <= 1000000000L) {
            float var3 = (float)this.proj.getVideoScale();
            float var5 = (float)this.proj.getVideoTx();
            float var6 = (float)this.proj.getVideoTy();

            for (Detector.Det var10 : var8.getDets()) {
               int var7;
               label58: {
                  label57: {
                     label56: {
                        label55: {
                           String var11 = var10.getName();
                           switch (var11.hashCode()) {
                              case -991716523:
                                 if (var11.equals("person")) {
                                    break label55;
                                 }
                                 break;
                              case 97920:
                                 if (var11.equals("bus")) {
                                    break label56;
                                 }
                                 break;
                              case 98260:
                                 if (var11.equals("car")) {
                                    var7 = Color.rgb(64, 160, 255);
                                    break label58;
                                 }
                                 break;
                              case 3023841:
                                 if (var11.equals("bike")) {
                                    break label55;
                                 }
                                 break;
                              case 3357597:
                                 if (var11.equals("moto")) {
                                    break label55;
                                 }
                                 break;
                              case 3540994:
                                 if (var11.equals("stop")) {
                                    break label57;
                                 }
                                 break;
                              case 102970646:
                                 if (var11.equals("light")) {
                                    break label57;
                                 }
                                 break;
                              case 110640223:
                                 if (var11.equals("truck")) {
                                    break label56;
                                 }
                           }

                           var7 = -1;
                           break label58;
                        }

                        var7 = Color.rgb(255, 80, 120);
                        break label58;
                     }

                     var7 = Color.rgb(255, 170, 60);
                     break label58;
                  }

                  var7 = Color.rgb(90, 220, 120);
               }

               this.detBox.setColor(var7);
               this.detBg.setColor(var7);
               float var2 = var10.getX0() * var3 + var5;
               float var4 = var10.getY0() * var3 + var6;
               var1.drawRect(var2, var4, var10.getX1() * var3 + var5, var10.getY1() * var3 + var6, this.detBox);
               String var14 = var10.getName();
               var7 = (int)(var10.getScore() * 100);
               String var13 = var14 + " " + var7;
               var1.drawRect(var2, var4 - 32.0F, var2 + (this.detText.measureText(var13) + 12.0F), var4, this.detBg);
               var1.drawText(var13, 6.0F + var2, var4 - 8.0F, this.detText);
            }
         }
      }
   }

   private final void drawLead(Canvas var1, Lead var2, double var3, int var5, boolean var6) {
      double var7;
      label43: {
         ModelSnapshot var17 = this.getT().getModel();
         if (var17 != null) {
            boolean var15;
            if (var17.getPosX().length == 0) {
               var15 = true;
            } else {
               var15 = false;
            }

            if (!var15) {
               int var16 = this.lastIdxBelow(var17.getPosX(), var2.getDRel());
               float[] var23 = var17.getPosZ();
               if (var16 >= 0 && var16 < var23.length) {
                  var15 = true;
               } else {
                  var15 = false;
               }

               float var9;
               if (var15) {
                  var9 = var23[var16];
               } else {
                  var9 = 0.0F;
               }

               var7 = var9;
               break label43;
            }
         }

         var7 = 0.0;
      }

      if (this.proj.map(var2.getDRel(), -var2.getYRel(), var7 + var3, this.tmp)) {
         float var18 = 0.0F;
         if (var2.getDRel() < 40.0F) {
            float var10 = (1.0F - var2.getDRel() / 40.0F) * 255.0F;
            var18 = var10;
            if (var2.getVRel() < 0.0F) {
               var18 = var10 + -var2.getVRel() / 10.0F * 255.0F;
            }

            var18 = Math.min(var18, 255.0F);
         }

         float var20 = this.getResources().getDisplayMetrics().density / 2.0F;
         float var12 = RangesKt.coerceIn(750.0F / (var2.getDRel() / 3.0F + 30.0F), 15.0F, 30.0F) * 2.35F * var20;
         var20 = RangesKt.coerceIn(this.tmp[0], 0.0F, this.getWidth() - var12 / 2);
         float var11 = Math.min(this.tmp[1], this.getHeight() - 0.6F * var12);
         float var13 = var12 / 5;
         float var14 = var12 / 10;
         this.path.rewind();
         this.path.moveTo(var20 + var12 * 1.35F + var13, var11 + var12 + var14);
         this.path.lineTo(var20, var11 - var14);
         this.path.lineTo(var20 - var12 * 1.35F - var13, var11 + var12 + var14);
         this.path.close();
         this.fill.setShader(null);
         this.fill.setColor(Color.rgb(218, 202, 37));
         var1.drawPath(this.path, this.fill);
         this.path.rewind();
         this.path.moveTo(var12 * 1.25F + var20, var11 + var12);
         this.path.lineTo(var20, var11);
         this.path.lineTo(var20 - 1.25F * var12, var11 + var12);
         this.path.close();
         this.fill.setColor(Companion.withAlpha(var5, MathKt.roundToInt(var18)));
         var1.drawPath(this.path, this.fill);
      }
   }

   private final void drawModel(Canvas var1, float var2) {
      ModelSnapshot var35 = this.getT().getModel();
      if (var35 != null) {
         int var21 = var35.getPosX().length;
         if (var21 >= 2) {
            Lead var33 = this.getT().getLeadOne();
            boolean var29 = this.getT().isMetric();
            Pair var34 = drawModel$custom(this, "PathWidth", 6.1F);
            boolean var32 = (Boolean)var34.component1();
            float var17 = ((Number)var34.component2()).floatValue();
            var34 = drawModel$custom(this, "LaneLinesWidth", 4.0F);
            boolean var30 = (Boolean)var34.component1();
            float var20 = ((Number)var34.component2()).floatValue();
            var34 = drawModel$custom(this, "RoadEdgesWidth", 2.0F);
            boolean var31 = (Boolean)var34.component1();
            float var19 = ((Number)var34.component2()).floatValue();
            var34 = drawModel$custom(this, "PathEdgeWidth", 20.0F);
            var34.component1();
            float var18 = ((Number)var34.component2()).floatValue();
            double var3;
            if (var32) {
               var3 = this.halfM(var17, var29);
            } else {
               var3 = 0.9;
            }

            double var7 = 0.025;
            double var5;
            if (var30) {
               var5 = this.smallHalfM(var20, var29);
            } else {
               var5 = 0.025;
            }

            if (var31) {
               var7 = this.smallHalfM(var19, var29);
            }

            if (this.getT().s("path_edges")) {
               var18 = RangesKt.coerceIn(var18 / 100.0F, 0.0F, 1.0F);
            } else {
               var18 = 0.0F;
            }

            double var11 = RangesKt.coerceIn(var35.getPosX()[var21 - 1], 10.0, 100.0);
            double var46;
            if (var33.getStatus()) {
               var46 = var33.getDRel() * 2.0;
               var46 = RangesKt.coerceIn(var46 - Math.min(var46 * 0.35, 10.0), 0.0, var11);
            } else {
               var46 = var11;
            }

            int var28 = this.lastIdxBelow(var35.getPosX(), var46);
            double var13 = this.proj.getPathOffsetZ();
            int var26 = Companion.parseColor(Telemetry.pStr$default(this.getT(), "LaneLinesColor", null, 2, null), -1);
            int var23;
            if (this.getT().s("lane_centering_cue")) {
               var23 = LaneCentering.direction$default(LaneCentering.INSTANCE, this.getT(), 0L, 2, null);
            } else {
               var23 = 0;
            }

            LaneTracks.update$default(this.tracks, var35, this.getT(), 0.05F, false, null, 16, null);
            LaneTracks.PathState var70 = LaneTracks.Companion.pathState(this.getT());
            if (this.getT().s("lane_lines")) {
               Triple var62 = this.tracks.inner();
               LaneTracks.Line var37 = (LaneTracks.Line)var62.component1();
               LaneTracks.Line var36 = (LaneTracks.Line)var62.component2();
               LaneTracks.Line var63 = (LaneTracks.Line)var62.component3();
               Iterator var38 = this.tracks.getLines().iterator();
               Intrinsics.checkNotNullExpressionValue(var38, "iterator(...)");

               while (var38.hasNext()) {
                  Object var39 = var38.next();
                  Intrinsics.checkNotNullExpressionValue(var39, "next(...)");
                  LaneTracks.Line var40 = (LaneTracks.Line)var39;
                  int var27 = MathKt.roundToInt(var40.getAlpha() * 0.7F * 255);
                  if (var27 > 2) {
                     boolean var24;
                     if (var40 == var63 && var70 == LaneTracks.PathState.ENGAGED) {
                        var24 = true;
                     } else {
                        var24 = false;
                     }

                     double var15 = var40.getAlpha();
                     if (var24 && this.tracks.getEffort() > 0.55F) {
                        var46 = 1.8;
                     } else {
                        var46 = 1.0;
                     }

                     var39 = this.proj
                        .ribbon(var40.getX(), var40.getY(), var40.getZ(), var15 * var5 * var46, 0.0, this.lastIdxBelow(var40.getX(), var11), true);
                     if (var39 != null) {
                        boolean var22;
                        if ((var23 <= 0 || var40 != var36) && (var23 >= 0 || var40 != var37)) {
                           var22 = 0;
                        } else {
                           var22 = 1;
                        }

                        if (var22) {
                           var22 = Color.rgb(0, 176, 220);
                        } else {
                           var22 = var26;
                        }

                        int var25 = var22;
                        if (var24) {
                           var25 = LaneTracks.Companion.effortColor(var22, this.tracks.getEffort());
                        }

                        this.fill.setShader(null);
                        Paint var41 = this.fill;
                        OverlayView.Companion var77 = Companion;
                        if (var24 && this.tracks.getEffort() > 0.55F) {
                           var22 = Math.max(var27, 220);
                        } else {
                           var22 = var27;
                        }

                        var41.setColor(var77.withAlpha(var25, var22));
                        this.drawPoly(var1, (float[])var39, this.fill);
                     }
                  }
               }
            }

            if (this.getT().s("road_edges")) {
               var21 = 0;

               for (int var58 = ((Object[])var35.getEdgeX()).length; var21 < var58; var21++) {
                  if (var21 < var35.getEdgeStd().length) {
                     var17 = var35.getEdgeStd()[var21];
                  } else {
                     var17 = 1.0F;
                  }

                  var23 = MathKt.roundToInt(RangesKt.coerceIn(1.0F - var17, 0.0F, 1.0F) * 255);
                  if (var23 > 2) {
                     float[] var64 = this.proj
                        .ribbon(
                           var35.getEdgeX()[var21],
                           var35.getEdgeY()[var21],
                           var35.getEdgeZ()[var21],
                           var7,
                           0.0,
                           this.lastIdxBelow(var35.getEdgeX()[var21], var11),
                           true
                        );
                     if (var64 != null) {
                        this.fill.setShader(null);
                        this.fill.setColor(Color.argb(var23, 255, 0, 0));
                        this.drawPoly(var1, var64, this.fill);
                     }
                  }
               }
            }

            float[] var65 = this.proj.ribbon(var35.getPosX(), var35.getPosY(), var35.getPosZ(), var3 * (1 - var18), var13, var28, false);
            if (var65 != null) {
               this.drawPathFill(var1, var65, var35, var28, var2);
               if (var18 > 0.0F) {
                  float[] var71 = this.proj.ribbon(var35.getPosX(), var35.getPosY(), var35.getPosZ(), var3, var13, var28, false);
                  if (var71 != null) {
                     this.drawPathEdges(var1, var71, var65);
                  }
               }
            }

            if (this.getT().s("radar_tracks")) {
               float[] var74 = this.getT().getTracks();
               this.fill.setShader(null);
               byte var54 = 0;
               ModelSnapshot var72 = var35;

               for (float[] var73 = var74; var54 + 1 < var73.length; var54 += 2) {
                  var18 = var73[var54];
                  var17 = var73[var54 + 1];
                  var23 = this.lastIdxBelow(var72.getPosX(), var18);
                  var74 = var72.getPosZ();
                  boolean var59;
                  if (var23 >= 0 && var23 < var74.length) {
                     var59 = true;
                  } else {
                     var59 = false;
                  }

                  if (var59) {
                     var2 = var74[var23];
                  } else {
                     var2 = 0.0F;
                  }

                  var3 = var2;
                  if (this.proj.map(var18, -var17, var3 + var13, this.tmp)) {
                     var17 = RangesKt.coerceIn(this.tmp[0], 0.0F, this.getWidth());
                     var2 = RangesKt.coerceIn(this.tmp[1], 0.0F, this.getHeight());
                     this.fill.setColor(Color.argb(170, 0, 0, 0));
                     var1.drawCircle(var17, var2, this.dp(4.5F), this.fill);
                     this.fill.setColor(Color.argb(230, 255, 40, 40));
                     var1.drawCircle(var17, var2, this.dp(3.5F), this.fill);
                  }
               }
            }

            if (this.getT().s("lead_marker")) {
               var2 = this.getT().pInt("LeadDetectionThreshold", 35) / 100.0F;
               if (this.getT().getLeadOne().getStatus()) {
                  var33 = this.getT().getLeadOne();
                  if (this.getT().getLeadOne().getModelProb() < var2) {
                     var21 = -1;
                  } else {
                     var21 = Color.rgb(201, 34, 49);
                  }

                  this.drawLead(var1, var33, var13, var21, true);
               }

               if (this.getT().getLeadTwo().getStatus() && Math.abs(this.getT().getLeadTwo().getDRel() - this.getT().getLeadOne().getDRel()) > 3.0) {
                  this.drawLead(var1, this.getT().getLeadTwo(), var13, Color.rgb(201, 34, 49), false);
               }
            }
         }
      }
   }

   private static final Pair<Boolean, Float> drawModel$custom(OverlayView var0, String var1, float var2) {
      float var3 = var0.getT().pFloat(var1, var2);
      boolean var4;
      if (Math.abs(var3 - var2) > 0.001F) {
         var4 = true;
      } else {
         var4 = false;
      }

      return new Pair<>(var4, var3);
   }

   private final void drawPathEdges(Canvas var1, float[] var2, float[] var3) {
      float[] var7 = new float[var2.length + var3.length];
      System.arraycopy(var2, 0, var7, 0, var2.length);
      int var5 = var3.length / 2;

      for (int var4 = 0; var4 < var5; var4++) {
         int var6 = var5 - 1 - var4;
         var7[var2.length + var4 * 2] = var3[var6 * 2];
         var7[var2.length + var4 * 2 + 1] = var3[var6 * 2 + 1];
      }

      int var8 = Companion.parseColor(Telemetry.pStr$default(this.getT(), "PathEdgesColor", null, 2, null), Color.rgb(0, 255, 0));
      RectF var11 = this.polyBounds(var7);
      this.fill
         .setShader(
            this.vGradient(
               var11, new int[]{Companion.withAlpha(var8, 102), Companion.withAlpha(var8, 89), Companion.withAlpha(var8, 0)}, new float[]{0.0F, 0.5F, 1.0F}
            )
         );
      this.path.rewind();
      this.path.setFillType(FillType.EVEN_ODD);
      this.path.moveTo(var2[0], var2[1]);

      for (byte var9 = 2; var9 < var2.length; var9 += 2) {
         this.path.lineTo(var2[var9], var2[var9 + 1]);
      }

      this.path.close();
      this.path.moveTo(var3[0], var3[1]);

      for (byte var10 = 2; var10 < var3.length; var10 += 2) {
         this.path.lineTo(var3[var10], var3[var10 + 1]);
      }

      this.path.close();
      var1.drawPath(this.path, this.fill);
      this.path.setFillType(FillType.WINDING);
      this.fill.setShader(null);
   }

   private final void drawPathFill(Canvas var1, float[] var2, ModelSnapshot var3, int var4, float var5) {
      RectF var19 = this.polyBounds(var2);
      if (LaneTracks.Companion.pathState(this.getT()) == LaneTracks.PathState.OVERRIDE) {
         this.fill
            .setShader(
               this.vGradient(
                  var19,
                  new int[]{Color.argb(150, 160, 164, 170), Color.argb(110, 160, 164, 170), Color.argb(20, 160, 164, 170)},
                  new float[]{0.0F, 0.5F, 1.0F}
               )
            );
         this.drawPoly(var1, var2, this.fill);
         this.fill.setShader(null);
      } else {
         boolean var17 = this.getT().s("acceleration_path");
         float var6;
         if (this.getT().getAllowThrottle()) {
            var6 = 1.0F;
         } else {
            var6 = 0.0F;
         }

         this.blendThrottle = this.blendThrottle + (var6 - this.blendThrottle) * (var5 / (var5 + 0.25F));
         if (this.getT().s("rainbow_path")) {
            var5 = (float)(System.nanoTime() / 1.0E9 * 5.0 * this.getT().getVEgo() % 360.0);
            int[] var18 = new int[12];

            for (int var24 = 0; var24 < 12; var24++) {
               var6 = var24;
               var18[var24] = Companion.withAlpha(
                  ColorUtils.HSLToColor(new float[]{(var6 * 10.0F + var5) % 360.0F, 1.0F, 0.5F}), RangesKt.coerceIn(127 - var24 * 9, 25, 127)
               );
            }

            Paint var20 = this.fill;
            float[] var22 = new float[12];

            for (int var25 = 0; var25 < 12; var25++) {
               var22[var25] = var25 / 11.0F;
            }

            var20.setShader(this.vGradient(var19, var18, var22));
         } else if (var17 && this.getT().getExperimentalMode()) {
            int var37 = var2.length / 4;
            ArrayList var40 = new ArrayList();
            ArrayList var39 = new ArrayList();
            var6 = RangesKt.coerceAtLeast(var19.height(), 1.0F);

            for (int var34 = 0; var34 < var37; var34++) {
               float var7 = var2[var34 * 2 + 1];
               float var8 = (var7 - var19.top) / var6;
               float[] var21 = var3.getAccelX();
               int var38 = Math.min(var34, var4);
               boolean var35;
               if (var38 >= 0 && var38 < var21.length) {
                  var35 = 1;
               } else {
                  var35 = 0;
               }

               if (var35) {
                  var5 = var21[var38];
               } else {
                  var5 = 0.0F;
               }

               float var9 = RangesKt.coerceIn(35.0F * var5 + 60.0F, 0.0F, 120.0F);
               float var10 = Math.min(Math.abs(1.5F * var5), 1.0F);
               var5 = this.interp(1.0F - var8, 0.375F, 0.75F, 0.4F, 0.0F);
               var35 = ColorUtils.HSLToColor(new float[]{var9, var10, -0.32999998F * var10 + 0.95F});
               var7 = RangesKt.coerceIn(1.0F - (var7 - var19.top) / var6, 0.0F, 1.0F);
               if (var39.isEmpty() || !(var7 <= CollectionsKt.last(var39).floatValue())) {
                  var40.add(Companion.withAlpha(var35, MathKt.roundToInt(255 * var5)));
                  var39.add(var7);
               }
            }

            if (var40.size() >= 2) {
               this.fill.setShader(this.vGradient(var19, CollectionsKt.toIntArray(var40), CollectionsKt.toFloatArray(var39)));
            } else {
               this.fill.setShader(null);
               this.fill.setColor(Color.argb(30, 255, 255, 255));
            }
         } else if (var17) {
            int var11 = Color.argb(102, 13, 248, 122);
            int var12 = Color.argb(89, 114, 255, 92);
            int var15 = Color.argb(0, 114, 255, 92);
            int var14 = Color.argb(102, 242, 242, 242);
            int var13 = Color.argb(89, 242, 242, 242);
            int var16 = Color.argb(0, 242, 242, 242);
            int[] var23 = new int[3];

            for (int var26 = 0; var26 < 3; var26++) {
               var23[var26] = ColorUtils.blendARGB(new int[]{var14, var13, var16}[var26], new int[]{var11, var12, var15}[var26], this.blendThrottle);
            }

            this.fill.setShader(this.vGradient(var19, var23, new float[]{0.0F, 0.5F, 1.0F}));
         } else {
            var4 = Companion.parseColor(Telemetry.pStr$default(this.getT(), "PathColor", null, 2, null), Color.rgb(48, 255, 156));
            this.fill
               .setShader(
                  this.vGradient(
                     var19,
                     new int[]{Companion.withAlpha(var4, 255), Companion.withAlpha(var4, 140), Companion.withAlpha(var4, 26)},
                     new float[]{0.0F, 0.5F, 1.0F}
                  )
               );
         }

         this.drawPoly(var1, var2, this.fill);
         this.fill.setShader(null);
      }
   }

   private final void drawPoly(Canvas var1, float[] var2, Paint var3) {
      this.path.rewind();
      this.path.moveTo(var2[0], var2[1]);

      for (byte var4 = 2; var4 < var2.length; var4 += 2) {
         this.path.lineTo(var2[var4], var2[var4 + 1]);
      }

      this.path.close();
      var1.drawPath(this.path, var3);
   }

   private final void drawSignalFlashes(Canvas var1) {
      if (this.getT().getStarted()) {
         boolean var9 = this.getT().s("signal_flashes");
         boolean var8 = this.getT().s("blindspot_flashes");

         for (int var5 = 0; var5 < 2; var5++) {
            Telemetry var12 = this.getT();
            boolean var10;
            if (var5 == 0) {
               var10 = var12.getLeftBlinker();
            } else {
               var10 = var12.getRightBlinker();
            }

            var12 = this.getT();
            boolean var11;
            if (var5 == 0) {
               var11 = var12.getLeftBlindspot();
            } else {
               var11 = var12.getRightBlindspot();
            }

            boolean var4;
            if (var9 && var10) {
               var4 = 1;
            } else {
               var4 = 0;
            }

            boolean var6;
            if (var8 && var11) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var4 || var6) {
               short var7;
               if (var4 && var6) {
                  var7 = 250;
               } else {
                  var7 = 500;
               }

               boolean var17;
               if (this.blinkPhase / var7 % 2 == 0L) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               if (var4 && var6) {
                  if (var17) {
                     var4 = Color.rgb(255, 214, 0);
                  } else {
                     var4 = Color.rgb(201, 34, 49);
                  }
               } else if (var4) {
                  if (!var17) {
                     continue;
                  }

                  var4 = Color.rgb(255, 214, 0);
               } else {
                  var4 = Color.rgb(201, 34, 49);
               }

               float var2 = this.dp(120.0F);
               RectF var14 = null;
               if (var5 == 0) {
                  var14 = new RectF(0.0F, 0.0F, var2, this.getHeight());
               } else {
                  var14 = new RectF(this.getWidth() - var2, 0.0F, this.getWidth(), this.getHeight());
               }

               Paint var13 = this.fill;
               float var3 = var14.left;
               var2 = var14.right;
               int[] var19;
               if (var5 == 0) {
                  var19 = new int[]{Companion.withAlpha(var4, 170), Companion.withAlpha(var4, 0)};
               } else {
                  var19 = new int[]{Companion.withAlpha(var4, 0), Companion.withAlpha(var4, 170)};
               }

               var13.setShader((Shader)(new LinearGradient(var3, 0.0F, var2, 0.0F, var19, null, TileMode.CLAMP)));
               var1.drawRect(var14, this.fill);
               this.fill.setShader(null);
            }
         }
      }
   }

   private final void drawTorqueBar(Canvas var1, float var2) {
      this.torqueSmooth = 0.25F * RangesKt.coerceIn(this.getT().getOutTorque(), -1.0F, 1.0F) + this.torqueSmooth * 0.75F;
      float var3 = this.getWidth() * 1.25F;
      float var4 = this.getWidth() / 2.0F;
      float var5 = this.getHeight() + var3 - this.dp(22.0F);
      boolean var7;
      if (this.getT().getEnabled() && !this.getT().getPauseLateral()) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      this.stroke.setStrokeCap(Cap.ROUND);
      Paint var8 = this.stroke;
      if (var7) {
         var7 = (byte)100;
      } else {
         var7 = (byte)45;
      }

      var8.setColor(Color.argb(var7, 255, 255, 255));
      this.stroke.setStrokeWidth(this.dp(5.0F));
      RectF var9 = new RectF(var4 - var3, var5 - var3, var4 + var3, var5 + var3);
      var1.drawArc(var9, -90.0F - 12.7F, 12.7F * 2, false, this.stroke);
      var2 = Math.abs(this.torqueSmooth);
      if (var2 > 0.01F) {
         label30: {
            short var13;
            if (var2 > 0.75F) {
               var13 = 115;
            } else {
               if (!(var2 > 0.5F)) {
                  var7 = -1;
                  break label30;
               }

               var13 = 200;
            }

            var7 = Color.rgb(255, var13, 0);
         }

         this.stroke.setColor(Companion.withAlpha(var7, 235));
         this.stroke.setStrokeWidth(this.dp(9.0F));
         float var6 = 12.7F * var2;
         if (this.torqueSmooth > 0.0F) {
            var2 = -90.0F - var6;
            var8 = this.stroke;
         } else {
            var8 = this.stroke;
            var2 = -90.0F;
         }

         var1.drawArc(var9, var2, var6, false, var8);
      }

      this.fill.setShader(null);
      this.fill.setColor(Color.argb(230, 182, 182, 182));
      var1.drawCircle(var4, var5 - var3, this.dp(4.0F), this.fill);
      this.stroke.setStrokeCap(Cap.BUTT);
   }

   private final double halfM(float var1, boolean var2) {
      double var3 = var1;
      if (!var2) {
         var3 *= 0.3048;
      }

      return var3 / 2.0;
   }

   private final float interp(float var1, float var2, float var3, float var4, float var5) {
      if (var1 <= var2) {
         var1 = var4;
      } else if (var1 >= var3) {
         var1 = var5;
      } else {
         var1 = (var5 - var4) * (var1 - var2) / (var3 - var2) + var4;
      }

      return var1;
   }

   private final int lastIdxBelow(float[] var1, double var2) {
      int var6 = var1.length - 1;
      int var5 = 0;
      int var7 = var1.length;

      int var4;
      while (true) {
         var4 = var6;
         if (var5 >= var7) {
            break;
         }

         if (var1[var5] > var2) {
            var4 = Math.max(0, var5 - 1);
            break;
         }

         var5++;
      }

      return var4;
   }

   private final RectF polyBounds(float[] var1) {
      float var3 = Float.MAX_VALUE;
      float var2 = -Float.MAX_VALUE;

      for (byte var4 = 1; var4 < var1.length; var4 += 2) {
         var3 = Math.min(var3, var1[var4]);
         var2 = Math.max(var2, var1[var4]);
      }

      return new RectF(0.0F, var3, this.getWidth(), var2);
   }

   private final double smallHalfM(float var1, boolean var2) {
      double var5 = var1;
      double var3;
      if (var2) {
         var3 = 200.0;
      } else {
         var5 *= 0.0254;
         var3 = 2.0;
      }

      return var5 / var3;
   }

   private final Shader vGradient(RectF var1, int[] var2, float[] var3) {
      return (Shader)(new LinearGradient(0.0F, var1.bottom, 0.0F, var1.top, var2, var3, TileMode.CLAMP));
   }

   public final Detector getDetector() {
      return this.detector;
   }

   public final Function0<Unit> getOnResized() {
      return this.onResized;
   }

   public final Projection getProj() {
      return this.proj;
   }

   public final boolean getSceneMode() {
      return this.sceneMode;
   }

   public final Telemetry getT() {
      Telemetry var1 = this.t;
      if (var1 != null) {
         return var1;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("t");
         return null;
      }
   }

   public final UiStatus getUiStatus() {
      return this.uiStatus;
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      if (this.t != null) {
         long var3 = System.nanoTime();
         float var2;
         if (this.lastDrawNs == 0L) {
            var2 = 0.05F;
         } else {
            var2 = RangesKt.coerceIn((float)(var3 - this.lastDrawNs) / 1.0E9F, 0.001F, 0.25F);
         }

         this.lastDrawNs = var3;
         this.blinkPhase = var3 / 1000000L;
         if (!this.sceneMode) {
            this.drawAmbient(var1);
         }

         if (!this.sceneMode && this.getT().getStarted() && this.proj.getValid() && this.getT().getCalValid() && this.getT().s("model_ui")) {
            this.drawModel(var1, var2);
         }

         if (!this.sceneMode && this.getT().getStarted() && this.detector != null && this.getT().s("detector_boxes")) {
            this.drawDetections(var1);
         }

         this.drawSignalFlashes(var1);
         if (this.getT().s("status_border")) {
            this.drawBorder(var1);
         }

         if (this.getT().getStarted() && this.getT().s("torque_bar")) {
            this.drawTorqueBar(var1, var2);
         }
      }
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      this.proj.setViewW(var1);
      this.proj.setViewH(var2);
      Rect var6;
      if (VERSION.SDK_INT >= 30) {
         var6 = (Rect)this.getContext().getSystemService("window");
         Intrinsics.checkNotNull(var6, "null cannot be cast to non-null type android.view.WindowManager");
         var6 = ((WindowManager)var6).getMaximumWindowMetrics().getBounds();
      } else {
         var6 = new Rect(0, 0, this.getResources().getDisplayMetrics().widthPixels, this.getResources().getDisplayMetrics().heightPixels);
      }

      Intrinsics.checkNotNull(var6);
      this.proj.setFullW(Math.max(var6.width(), var6.height()));
      this.proj.setFullH(Math.min(var6.width(), var6.height()));
      this.proj.update();
      Function0 var7 = this.onResized;
      if (var7 != null) {
         var7.invoke();
      }
   }

   public final void setDetector(Detector var1) {
      this.detector = var1;
   }

   public final void setFrameSize(int var1, int var2) {
      if (var1 != this.proj.getFrameW() || var2 != this.proj.getFrameH()) {
         this.proj.setFrameW(var1);
         this.proj.setFrameH(var2);
         this.proj.update();
      }
   }

   public final void setOnResized(Function0<Unit> var1) {
      this.onResized = var1;
   }

   public final void setSceneMode(boolean var1) {
      this.sceneMode = var1;
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }

   public final void setUiStatus(UiStatus var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.uiStatus = var1;
   }

   public final boolean updateCalibration() {
      float[] var3 = this.getT().getCalRpy();
      boolean var1;
      if (this.proj.getRpy()[0] == var3[0]) {
         var1 = true;
      } else {
         var1 = false;
      }

      boolean var2;
      label40: {
         if (var1) {
            if (this.proj.getRpy()[1] == var3[1]) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (var1) {
               if (this.proj.getRpy()[2] == var3[2]) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               if (var1) {
                  if (this.proj.getPathOffsetZ() == this.getT().getCalHeight()) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  if (var1) {
                     var2 = false;
                     break label40;
                  }
               }
            }
         }

         var2 = true;
      }

      if (var2) {
         this.proj.setRpy(new double[]{var3[0], var3[1], var3[2]});
         this.proj.setPathOffsetZ(this.getT().getCalHeight());
         this.proj.update();
      }

      return var2;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final int cscColor(float var1) {
         var1 = 0.3F * RangesKt.coerceIn((Math.abs(var1) - 5.0E-4F) / 0.0195F, 0.0F, 1.0F) + 0.7F;
         int var2 = Color.rgb(34, 197, 94);
         int var3 = Color.rgb(251, 191, 36);
         int var5 = Color.rgb(234, 88, 12);
         int var4 = Color.rgb(201, 34, 49);
         var var6 = (<unrepresentable> & KFunction)<unrepresentable>.INSTANCE;
         Integer var7;
         Integer var8;
         Function3 var11;
         if (var1 < 0.5F) {
            var11 = var6;
            var8 = var2;
            var7 = var3;
            var1 /= 0.5F;
         } else {
            if (!(var1 < 0.6F)) {
               var6 = var6.invoke(var5, var4, RangesKt.coerceIn((var1 - 0.6F) / 0.4F, 0.0F, 1.0F));
               return ((Number)var6).intValue();
            }

            var11 = var6;
            var8 = var3;
            var7 = var5;
            var1 = (var1 - 0.5F) / 0.1F;
         }

         var6 = var11.invoke(var8, var7, var1);
         return ((Number)var6).intValue();
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      public final int parseColor(String var1, int var2) {
         Intrinsics.checkNotNullParameter(var1, "s");
         if (StringsKt.isBlank(var1)) {
            return var2;
         } else {
            label42: {
               String var4;
               try {
                  var4 = StringsKt.removePrefix(StringsKt.trim(var1).toString(), "#");
                  if (var4.length() == 6) {
                     StringBuilder var11 = new StringBuilder();
                     var1 = var11.append("#").append(var4).toString();
                     break label42;
                  }
               } catch (Exception var8) {
                  return var2;
               }

               try {
                  if (var4.length() != 8) {
                     return var2;
                  }

                  var1 = var4.substring(6);
                  Intrinsics.checkNotNullExpressionValue(var1, "substring(...)");
                  String var5 = var4.substring(0, 6);
                  Intrinsics.checkNotNullExpressionValue(var5, "substring(...)");
                  StringBuilder var12 = new StringBuilder();
                  var1 = var12.append("#").append(var1).append(var5).toString();
               } catch (Exception var7) {
                  return var2;
               }
            }

            int var3;
            try {
               var3 = Color.parseColor(var1);
            } catch (Exception var6) {
               return var2;
            }

            return var3;
         }
      }

      public final int withAlpha(int var1, int var2) {
         return Color.argb(RangesKt.coerceIn(var2, 0, 255), Color.red(var1), Color.green(var1), Color.blue(var1));
      }
   }
}
