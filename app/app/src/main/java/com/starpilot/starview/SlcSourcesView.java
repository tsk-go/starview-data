package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import org.capnproto.SlcPeek.Reading;
import org.capnproto.SlcPeek.Slc;

public final class SlcSourcesView extends View {
   private final Paint p;
   public Telemetry t;
   private final Paint tp;

   public SlcSourcesView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.p = new Paint(1);
      Paint var3 = new Paint(1);
      var3.setTypeface(Typeface.DEFAULT_BOLD);
      this.tp = var3;
   }

   // $VF: synthetic method
   public SlcSourcesView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
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
      if (this.t != null && this.getT().getStarted()) {
         boolean var8;
         if (this.getT().getSp7()) {
            var8 = this.getT().s("slc_sources_sp7");
         } else {
            var8 = this.getT().pBool("SpeedLimitSources", false);
         }

         if (var8) {
            float var2;
            if (this.getT().isMetric()) {
               var2 = 3.6F;
            } else {
               var2 = 2.23694F;
            }

            Slc var9;
            Object var10;
            label102: {
               var9 = this.getT().getSlc();
               var10 = null;
               if (var9 != null) {
                  boolean var6;
                  if (System.nanoTime() - this.getT().getSlcAtNs() < 3000000000L) {
                     var6 = true;
                  } else {
                     var6 = false;
                  }

                  if (var6) {
                     break label102;
                  }
               }

               var9 = null;
            }

            Map var15 = MapsKt.mapOf(
               new Pair[]{TuplesKt.to("dashboard", "DASH"), TuplesKt.to("map", "MAP"), TuplesKt.to("vision", "VISION"), TuplesKt.to("online", "ONLINE")}
            );
            List var24 = var10;
            if (var9 != null) {
               List var12 = var9.readings;
               var24 = var10;
               if (var12 != null) {
                  var10 = var12;
                  var var25 = new ArrayList();

                  for (Object var32 : var10) {
                     if (((Reading)var32).enabled) {
                        var25.add(var32);
                     }
                  }

                  var24 = var25;
               }
            }

            var10 = var24;
            if (var24 == null) {
               var10 = CollectionsKt.emptyList();
            }

            float var3 = this.getWidth();
            float var4 = this.getHeight();
            float var5 = ExtraViewsKt.access$dpf(this, 12.0F);
            this.p.setStyle(Style.FILL);
            this.p.setColor(Color.argb(175, 0, 0, 0));
            var1.drawRoundRect(new RectF(0.0F, 0.0F, var3, var4), var5, var5, this.p);
            this.p.setStyle(Style.STROKE);
            this.p.setStrokeWidth(1.0F);
            this.p.setColor(Color.argb(80, 196, 205, 208));
            var1.drawRoundRect(new RectF(0.5F, 0.5F, var3 - 0.5F, var4 - 0.5F), var5, var5, this.p);
            if (var10.isEmpty()) {
               this.tp.setTextAlign(Align.CENTER);
               this.tp.setTextSize(ExtraViewsKt.access$dpf(this, 14.0F));
               this.tp.setColor(Color.rgb(166, 166, 166));
               String var28;
               if (var9 == null) {
                  var28 = "No speed limit data";
               } else if (var9.readings.isEmpty()) {
                  var28 = "No speed limit";
               } else {
                  var28 = "No sources enabled";
               }

               var2 = 2;
               var1.drawText(var28, var3 / var2, var4 / var2 + ExtraViewsKt.access$dpf(this, 5.0F), this.tp);
               return;
            }

            var4 /= var10.size();
            Intrinsics.checkNotNull(var9);
            String var26 = var9.kind;
            String var33 = "valid";
            String var27;
            if (Intrinsics.areEqual(var26, "valid")) {
               var27 = var9.source;
            } else {
               var27 = var9.acceptedSource;
            }

            Iterator var16 = var10.iterator();
            int var21 = 0;

            while (var16.hasNext()) {
               int var7 = var21 + 1;
               Reading var17 = (Reading)var16.next();
               var5 = var21 * var4;
               var8 = Intrinsics.areEqual(var17.source, var27);
               if (var21 > 0) {
                  this.p.setStyle(Style.FILL);
                  this.p.setColor(Color.argb(100, 196, 205, 208));
                  var1.drawRect(ExtraViewsKt.access$dpf(this, 10.0F), var5, var3 - ExtraViewsKt.access$dpf(this, 10.0F), var5 + 1.0F, this.p);
               }

               if (var8) {
                  this.p.setStyle(Style.FILL);
                  this.p.setColor(Color.argb(230, 196, 205, 208));
                  var1.drawRoundRect(
                     new RectF(ExtraViewsKt.access$dpf(this, 6.0F), var5 + 0.25F * var4, ExtraViewsKt.access$dpf(this, 10.0F), var5 + 0.75F * var4),
                     2.0F,
                     2.0F,
                     this.p
                  );
               }

               this.tp.setTextAlign(Align.LEFT);
               this.tp.setTextSize(Math.min(0.36F * var4, ExtraViewsKt.access$dpf(this, 20.0F)));
               Paint var13 = this.tp;
               if (var8) {
                  var21 = -1;
               } else {
                  var21 = Color.rgb(166, 166, 166);
               }

               var13.setColor(var21);
               String var14 = (String)var15.get(var17.source);
               String var34 = var14;
               if (var14 == null) {
                  String var35 = var17.source;
                  Intrinsics.checkNotNullExpressionValue(var35, "source");
                  var34 = var35.toUpperCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var34, "toUpperCase(...)");
               }

               var1.drawText(var34, ExtraViewsKt.access$dpf(this, 18.0F), var5 + var4 * 0.64F, this.tp);
               this.tp.setTextAlign(Align.RIGHT);
               String var36;
               if (Intrinsics.areEqual(var17.kind, var33) && var17.speedMps > 0.0F) {
                  var36 = String.valueOf(MathKt.roundToInt(var17.speedMps * var2));
               } else {
                  var36 = "–";
               }

               var1.drawText(var36, var3 - ExtraViewsKt.access$dpf(this, 14.0F), var5 + 0.64F * var4, this.tp);
               var21 = var7;
            }

            return;
         }
      }
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }
}
