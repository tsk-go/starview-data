package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class NavCardView extends View {
   private boolean collapsed;
   private final Paint p;
   public Telemetry t;
   private final Paint tp;

   public NavCardView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.p = new Paint(3);
      Paint var3 = new Paint(1);
      var3.setColor(-1);
      var3.setTypeface(Typeface.DEFAULT_BOLD);
      this.tp = var3;
   }

   // $VF: synthetic method
   public NavCardView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private final String fmtDist(float var1) {
      boolean var4 = this.getT().isMetric();
      int var2 = 50;
      if (var4) {
         if (var1 < 1000.0F) {
            if (var1 < 500.0F) {
               var2 = 25;
            }

            int var11 = MathKt.roundToInt(var1 / var2);
            return var11 * var2 + " m";
         } else {
            var1 /= 1000.0F;
            String var12;
            if (var1 >= 10.0F) {
               var2 = (int)((float)Math.floor(var1));
               var12 = var2 + " km";
            } else {
               var12 = String.format("%.1f km", Arrays.copyOf(new Object[]{var1}, 1));
               Intrinsics.checkNotNullExpressionValue(var12, "format(...)");
            }

            return var12;
         }
      } else {
         var1 = 3.28084F * var1;
         if (var1 < 1000.0F) {
            if (var1 <= 100.0F) {
               var2 = 10;
            }

            int var3 = MathKt.roundToInt(var1 / var2);
            return var3 * var2 + " ft";
         } else {
            var1 /= 5280.0F;
            String var5;
            if (var1 >= 10.0F) {
               var2 = (int)((float)Math.floor(var1));
               var5 = var2 + " mi";
            } else {
               var5 = String.format("%.1f mi", Arrays.copyOf(new Object[]{var1}, 1));
               Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
            }

            return var5;
         }
      }
   }

   private final Bitmap icon(String var1, String var2) {
      NavIcons var3 = NavIcons.INSTANCE;
      Context var4 = this.getContext();
      Intrinsics.checkNotNullExpressionValue(var4, "getContext(...)");
      return var3.icon(var4, var1, var2);
   }

   private final List<String> wrap(String var1, float var2, int var3) {
      CharSequence var6 = var1;
      boolean var5 = true;
      List var7 = StringsKt.split$default(var6, new String[]{" "}, false, 0, 6, null);
      ArrayList var9 = new ArrayList();
      String var15 = "";

      for (String var8 : var7) {
         boolean var4;
         if (var15.length() == 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         String var16;
         if (var4) {
            var16 = var8;
         } else {
            var16 = var15 + " " + var8;
         }

         if (!(this.tp.measureText(var16) <= var2)) {
            if (var15.length() == 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (!var4) {
               var9.add(var15);
               var15 = var8;
               if (var9.size() != var3) {
                  continue;
               }

               var15 = var8;
               break;
            }
         }

         var15 = var16;
      }

      if (var9.size() < var3) {
         boolean var14;
         if (var15.length() > 0) {
            var14 = var5;
         } else {
            var14 = false;
         }

         if (var14) {
            var9.add(var15);
         }
      }

      if (var9.size() == var3 && !Intrinsics.areEqual(var1, CollectionsKt.joinToString$default(var9, " ", null, null, 0, null, null, 62, null))) {
         Object var11 = var9.get(var3 - 1);
         Intrinsics.checkNotNullExpressionValue(var11, "get(...)");
         var11 = StringsKt.trimEnd((String & CharSequence)var11).toString();
         var9.set(var3 - 1, var11 + "…");
      }

      return var9;
   }

   public final boolean getCollapsed() {
      return this.collapsed;
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
         Pair var10 = Telemetry.navCard$default(this.getT(), 0L, 1, null);
         if (var10 != null) {
            JSONObject var12 = (JSONObject)var10.getFirst();
            if (var12 != null) {
               this.collapsed = this.getT().mBool("NavInstructionCollapsed", false);
               float var4 = this.getWidth();
               float var5 = this.getHeight();
               float var2 = ExtraViewsKt.access$dpf(this, 16.0F);
               this.p.setStyle(Style.FILL);
               this.p.setColor(Color.argb(192, 0, 0, 0));
               String var11 = var12.optString("maneuverType");
               Intrinsics.checkNotNullExpressionValue(var11, "optString(...)");
               String var18 = var12.optString("maneuverModifier");
               Intrinsics.checkNotNullExpressionValue(var18, "optString(...)");
               Bitmap var19 = this.icon(var11, var18);
               if (this.collapsed) {
                  float var15 = Math.min(var4, var5);
                  var1.drawRoundRect(new RectF(var4 - var15, 0.0F, var4, var15), var2, var2, this.p);
                  if (var19 != null) {
                     var1.drawBitmap(var19, null, new RectF(var4 - var15 + var15 * 0.15F, var15 * 0.15F, var4 - 0.15F * var15, 0.85F * var15), this.p);
                  }

                  return;
               }

               var1.drawRoundRect(new RectF(0.0F, 0.0F, var4, var5), var2, var2, this.p);
               float var6 = ExtraViewsKt.access$dpf(this, 12.0F);
               var2 = var5 * 0.5F;
               if (var19 != null) {
                  var1.drawBitmap(var19, null, new RectF(var6, var6, var6 + var2, var6 + var2), this.p);
               }

               this.tp.setTextAlign(Align.LEFT);
               this.tp.setTextSize(ExtraViewsKt.access$dpf(this, 16.0F));
               this.tp.setTypeface(Typeface.DEFAULT_BOLD);
               var1.drawText(this.fmtDist((float)var12.optDouble("maneuverDistance", 0.0)), var6, var6 + var2 + ExtraViewsKt.access$dpf(this, 20.0F), this.tp);
               float var7 = var6 + var2 + ExtraViewsKt.access$dpf(this, 14.0F);
               float var8 = var4 - var7 - var6;
               String var20 = var12.optString("maneuverPrimaryText");
               var2 = ExtraViewsKt.access$dpf(this, 22.0F);
               this.tp.setTextSize(var2);

               while (this.tp.measureText(var20) > 2 * var8 && var2 > ExtraViewsKt.access$dpf(this, 15.0F)) {
                  var2 -= ExtraViewsKt.access$dpf(this, 1.0F);
                  this.tp.setTextSize(var2);
               }

               Intrinsics.checkNotNull(var20);
               Iterator var21 = this.wrap(var20, var8, 2).iterator();

               float var3;
               for (var3 = var6 + var2; var21.hasNext(); var3 += 1.1F * var2) {
                  var1.drawText((String)var21.next(), var7, var3, this.tp);
               }

               String var22 = var12.optString("maneuverSecondaryText");
               Intrinsics.checkNotNull(var22);
               boolean var9;
               if (var22.length() > 0) {
                  var9 = true;
               } else {
                  var9 = false;
               }

               if (var9) {
                  this.tp.setTypeface(Typeface.DEFAULT);
                  this.tp.setTextSize(ExtraViewsKt.access$dpf(this, 13.0F));
                  this.tp.setColor(Color.argb(180, 255, 255, 255));
                  var11 = CollectionsKt.firstOrNull(this.wrap(var22, var8, 1));
                  String var23 = var11;
                  if (var11 == null) {
                     var23 = "";
                  }

                  var1.drawText(var23, var7, ExtraViewsKt.access$dpf(this, 2.0F) + var3, this.tp);
                  this.tp.setColor(-1);
               }

               String var24 = var12.optString("nextManeuverType");
               var11 = var12.optString("nextManeuverModifier");
               Intrinsics.checkNotNull(var24);
               if (var24.length() > 0) {
                  var9 = true;
               } else {
                  var9 = false;
               }

               if (!var9) {
                  Intrinsics.checkNotNull(var11);
                  if (var11.length() > 0) {
                     var9 = true;
                  } else {
                     var9 = false;
                  }

                  if (!var9) {
                     return;
                  }
               }

               this.p.setColor(Color.argb(50, 255, 255, 255));
               var1.drawRect(var7, var5 - ExtraViewsKt.access$dpf(this, 34.0F), var4 - var6, var5 - ExtraViewsKt.access$dpf(this, 33.0F), this.p);
               this.tp.setTypeface(Typeface.DEFAULT);
               this.tp.setTextSize(ExtraViewsKt.access$dpf(this, 13.0F));
               var1.drawText("Then", var7, var5 - ExtraViewsKt.access$dpf(this, 12.0F), this.tp);
               Intrinsics.checkNotNull(var11);
               Bitmap var25 = this.icon(var24, var11);
               if (var25 != null) {
                  var1.drawBitmap(
                     var25,
                     null,
                     new RectF(
                        ExtraViewsKt.access$dpf(this, 44.0F) + var7,
                        var5 - ExtraViewsKt.access$dpf(this, 30.0F),
                        ExtraViewsKt.access$dpf(this, 66.0F) + var7,
                        var5 - ExtraViewsKt.access$dpf(this, 8.0F)
                     ),
                     this.p
                  );
               }

               return;
            }
         }
      }
   }

   public final void setCollapsed(boolean var1) {
      this.collapsed = var1;
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }
}
