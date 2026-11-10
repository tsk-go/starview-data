package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import org.capnproto.SlcPeek.Slc;

public final class SlcView extends View {
   public static final SlcView.Companion Companion = new SlcView.Companion(null);
   private static final Map<String, String> LABELS = MapsKt.mapOf(
      new Pair[]{TuplesKt.to("dashboard", "DASH"), TuplesKt.to("map", "OSM"), TuplesKt.to("vision", "VISION"), TuplesKt.to("mapbox", "NAV")}
   );
   private final Rect bounds;
   private final Paint p;
   public Telemetry t;
   private final Paint tp;

   public SlcView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.p = new Paint(1);
      Paint var3 = new Paint(1);
      var3.setTextAlign(Align.CENTER);
      var3.setTypeface(Typeface.DEFAULT_BOLD);
      this.tp = var3;
      this.bounds = new Rect();
   }

   // $VF: synthetic method
   public SlcView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private final float toDisplay(float var1) {
      float var2;
      if (this.getT().isMetric()) {
         var2 = 3.6F;
      } else {
         var2 = 2.23694F;
      }

      return var2 * var1;
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

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      if (this.t != null) {
         Telemetry var11 = this.getT();
         Object var13 = null;
         Slc var38 = Telemetry.slcLive$default(var11, 0L, 1, null);
         SlcView.Shown var39;
         if (var38 != null) {
            SlcView.Shown var12 = Companion.shown(var38, this.getT().isMetric(), this.getT().getVEgo());
            var39 = var12;
            if (var12 == null) {
               return;
            }
         } else {
            boolean var10;
            if (this.getT().getSlcChanged() && this.getT().getSlcUnconfirmed() > 0.0F) {
               var10 = true;
            } else {
               var10 = false;
            }

            var11 = this.getT();
            float var2;
            if (var10) {
               var2 = var11.getSlcUnconfirmed();
            } else {
               var2 = var11.getSlcSpeedLimit();
            }

            if (var2 <= 0.0F) {
               return;
            }

            int var9 = MathKt.roundToInt(this.toDisplay(this.getT().getSlcOffset()));
            String var41;
            if (var10) {
               var41 = "PENDING";
            } else {
               var var42 = this.getT().getSlcSource();
               boolean var8;
               if (var42.length() == 0) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               if (var8) {
                  var42 = "LIMIT";
               }

               String var43 = var42.toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var43, "toUpperCase(...)");
               var41 = StringsKt.take(var43, 6);
            }

            int var33 = MathKt.roundToInt(this.toDisplay(var2));
            String var45 = (String)var13;
            if (var9 != 0) {
               var45 = (String)var13;
               if (!var10) {
                  var45 = String.format("%+d", Arrays.copyOf(new Object[]{var9}, 1));
                  Intrinsics.checkNotNullExpressionValue(var45, "format(...)");
               }
            }

            var39 = new SlcView.Shown(var41, String.valueOf(var33), var45, 0, false, var10);
         }

         float var4 = this.getWidth();
         float var5 = this.getHeight();
         boolean var34;
         if (System.nanoTime() / 500000000L % 2 == 0L) {
            var34 = 1;
         } else {
            var34 = 0;
         }

         RectF var48;
         if (this.getT().s("vienna")) {
            float var14 = Math.min(var4, var5);
            float var6 = 2;
            float var7 = var14 / var6;
            this.p.setStyle(Style.FILL);
            this.p.setColor(-1);
            var1.drawCircle(var4 / var6, var5 / var6, var7, this.p);
            this.p.setStyle(Style.STROKE);
            this.p.setStrokeWidth(0.23F * var7);
            Paint var46 = this.p;
            if (var39.getPending() && !var34) {
               var34 = -1;
            } else {
               var34 = Color.rgb(201, 34, 49);
            }

            var46.setColor(var34);
            var1.drawCircle(var4 / var6, var5 / var6, var7 - this.p.getStrokeWidth() / var6, this.p);
            this.tp.setColor(-16777216);
            Paint var47 = this.tp;
            if (var39.getPosted().length() <= 2) {
               var14 = 0.8F;
            } else {
               var14 = 0.68F;
            }

            float var3;
            if (var39.getOffset() != null) {
               var3 = 0.85F;
            } else {
               var3 = 1.0F;
            }

            var47.setTextSize(var14 * var7 * var3);
            var5 /= var6;
            var3 = this.tp.getTextSize();
            if (var39.getOffset() != null) {
               var14 = 0.12F * var7;
            } else {
               var14 = 0.0F;
            }

            var14 = var5 + var3 * 0.36F - var14;
            var1.drawText(var39.getPosted(), var4 / var6, var14, this.tp);
            var13 = new RectF(
               var4 / var6 - this.tp.measureText(var39.getPosted()) / var6,
               var14 - this.tp.getTextSize() * 0.72F,
               var4 / var6 + this.tp.measureText(var39.getPosted()) / var6,
               var14
            );
            var48 = (RectF)var13;
            if (var39.getOffset() != null) {
               this.tp.setTextSize(var7 * 0.3F);
               var1.drawText(var39.getOffset(), var4 / var6, var14 + 0.36F * var7, this.tp);
               var48 = (RectF)var13;
            }
         } else {
            float var18 = HudViewsKt.access$dp(this, 14.0F);
            this.p.setStyle(Style.FILL);
            this.p.setColor(Color.argb(166, 0, 0, 0));
            var1.drawRoundRect(new RectF(0.0F, 0.0F, var4, var5), var18, var18, this.p);
            this.p.setStyle(Style.STROKE);
            this.p.setStrokeWidth(HudViewsKt.access$dp(this, 3.0F));
            Paint var49 = this.p;
            if (var39.getPending()) {
               if (var34) {
                  var34 = -1;
               } else {
                  var34 = Color.rgb(201, 34, 49);
               }
            } else {
               var34 = Color.argb(180, 196, 205, 208);
            }

            var49.setColor(var34);
            var1.drawRoundRect(
               new RectF(
                  HudViewsKt.access$dp(this, 1.5F),
                  HudViewsKt.access$dp(this, 1.5F),
                  var4 - HudViewsKt.access$dp(this, 1.5F),
                  var5 - HudViewsKt.access$dp(this, 1.5F)
               ),
               var18,
               var18,
               this.p
            );
            this.tp.setColor(-1);
            this.tp.setTextSize(0.16F * var5);
            float var29 = this.tp.measureText(var39.getHeading());
            if (var39.getArrow() != 0) {
               var18 = var5 * 0.12F;
            } else {
               var18 = 0.0F;
            }

            float var25;
            if (var39.getOffset() != null) {
               var25 = 0.2F;
            } else {
               var25 = 0.24F;
            }

            float var31 = var25 * var5;
            String var50 = var39.getHeading();
            var25 = 2;
            var1.drawText(var50, var4 / var25 - var18 / var25, var31, this.tp);
            if (var39.getArrow() != 0) {
               var29 = var4 / var25 + var29 / var25 + 0.1F * var18;
               var31 -= this.tp.getTextSize() * 0.36F;
               var18 = 0.45F * var18;
               Path var51 = new Path();
               if (var39.getArrow() < 0) {
                  var51.moveTo(var29, var31 + var18);
                  var51.lineTo(var29 + var18, var31 - 0.3F * var18);
                  var51.lineTo(var25 * var18 + var29, var31 + var18);
               } else {
                  var51.moveTo(var29, var31 - var18);
                  var51.lineTo(var29 + var18, var31 + 0.3F * var18);
                  var51.lineTo(var25 * var18 + var29, var31 - var18);
               }

               this.p.setStyle(Style.STROKE);
               this.p.setStrokeWidth(HudViewsKt.access$dp(this, 2.0F));
               this.p.setColor(-1);
               var1.drawPath(var51, this.p);
            }

            this.tp.setTextSize(0.42F * var5);
            if (var39.getOffset() != null) {
               var18 = 0.6F;
            } else {
               var18 = 0.72F;
            }

            var18 *= var5;
            var1.drawText(var39.getPosted(), var4 / var25, var18, this.tp);
            var13 = new RectF(
               var4 / var25 - this.tp.measureText(var39.getPosted()) / var25,
               var18 - this.tp.getTextSize() * 0.72F,
               var4 / var25 + this.tp.measureText(var39.getPosted()) / var25,
               var18
            );
            var48 = (RectF)var13;
            if (var39.getOffset() != null) {
               this.tp.setTextSize(0.17F * var5);
               this.tp.setColor(Color.rgb(223, 232, 227));
               var1.drawText(var39.getOffset(), var4 / var25, 0.86F * var5, this.tp);
               var48 = (RectF)var13;
            }
         }

         if (var39.getOverride()) {
            float var23 = HudViewsKt.access$dp(this, 3.0F);

            for (Pair var44 : CollectionsKt.listOf(
               new Pair[]{
                  TuplesKt.to(HudViewsKt.access$dp(this, 4.5F), Color.argb(220, 6, 14, 10)),
                  TuplesKt.to(HudViewsKt.access$dp(this, 2.5F), Color.argb(240, 197, 163, 255))
               }
            )) {
               float var27 = ((Number)var44.component1()).floatValue();
               var34 = ((Number)var44.component2()).intValue();
               this.p.setStyle(Style.STROKE);
               this.p.setStrokeWidth(var27);
               this.p.setColor(var34);
               this.p.setStrokeCap(Cap.ROUND);
               var1.drawLine(var48.left + var23, var48.top + var23, var48.right - var23, var48.bottom - var23, this.p);
               var1.drawLine(var48.right - var23, var48.top + var23, var48.left + var23, var48.bottom - var23, this.p);
            }

            this.p.setStrokeCap(Cap.BUTT);
         }
      }
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final SlcView.Shown shown(Slc var1, boolean var2, float var3) {
         Intrinsics.checkNotNullParameter(var1, "s");
         float var5;
         if (var2) {
            var5 = 3.6F;
         } else {
            var5 = 2.2369363F;
         }

         boolean var12 = Intrinsics.areEqual(var1.kind, "valid");
         float var4;
         if (var12) {
            var4 = var1.speedMps;
         } else if (var1.hasAccepted) {
            var4 = var1.acceptedMps;
         } else {
            var4 = Float.NaN;
         }

         var2 = Float.isInfinite(var4);
         byte var10 = 0;
         boolean var8;
         if (!var2 && !Float.isNaN(var4)) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         String var14 = null;
         if (var8 && !(var4 <= 0.0F)) {
            float var18;
            label101: {
               if (var12) {
                  var18 = var1.adjustedMps;
                  if (!Float.isInfinite(var18) && !Float.isNaN(var18)) {
                     var8 = (boolean)1;
                  } else {
                     var8 = (boolean)0;
                  }

                  if (var8 && var1.adjustedMps > 0.0F) {
                     var18 = var1.adjustedMps;
                     break label101;
                  }
               }

               var18 = var1.offsetMps + var4;
            }

            int var11;
            label90: {
               var11 = MathKt.roundToInt(var18 * var5) - MathKt.roundToInt(var4 * var5);
               if (var12) {
                  float var7 = var1.pendingMps;
                  if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
                     var8 = (boolean)1;
                  } else {
                     var8 = (boolean)0;
                  }

                  if (var8 && var1.pendingMps > 0.0F && !var1.autoAdoptionPending) {
                     var2 = true;
                     break label90;
                  }
               }

               var2 = false;
            }

            var8 = var10;
            if (var2) {
               boolean var9;
               if (!Float.isInfinite(var18) && !Float.isNaN(var18)) {
                  var9 = true;
               } else {
                  var9 = false;
               }

               var8 = var10;
               if (var9) {
                  if (var18 == var3) {
                     var9 = true;
                  } else {
                     var9 = false;
                  }

                  var8 = var10;
                  if (!var9) {
                     if (var18 > var3) {
                        var8 = -1;
                     } else {
                        var8 = 1;
                     }
                  }
               }
            }

            String var13;
            if (!var12) {
               var13 = "LAST";
            } else {
               String var15 = SlcView.LABELS.get(var1.source);
               var13 = var15;
               if (var15 == null) {
                  var13 = "SPEED";
               }
            }

            int var23 = MathKt.roundToInt(var4 * var5);
            if (var11 != 0) {
               var14 = String.format("%+d", Arrays.copyOf(new Object[]{var11}, 1));
               Intrinsics.checkNotNullExpressionValue(var14, "format(...)");
            }

            return new SlcView.Shown(var13, String.valueOf(var23), var14, var8, var1.driverOverride, var2);
         } else {
            return null;
         }
      }
   }

   public static final class Shown {
      private final int arrow;
      private final String heading;
      private final String offset;
      private final boolean override;
      private final boolean pending;
      private final String posted;

      public Shown(String var1, String var2, String var3, int var4, boolean var5, boolean var6) {
         Intrinsics.checkNotNullParameter(var1, "heading");
         Intrinsics.checkNotNullParameter(var2, "posted");
         super();
         this.heading = var1;
         this.posted = var2;
         this.offset = var3;
         this.arrow = var4;
         this.override = var5;
         this.pending = var6;
      }

      public final int getArrow() {
         return this.arrow;
      }

      public final String getHeading() {
         return this.heading;
      }

      public final String getOffset() {
         return this.offset;
      }

      public final boolean getOverride() {
         return this.override;
      }

      public final boolean getPending() {
         return this.pending;
      }

      public final String getPosted() {
         return this.posted;
      }
   }
}
