package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import org.capnproto.SlcPeek.Slc;
import org.json.JSONObject;

public final class SlcActionsView extends View {
   private final Paint bg;
   private final Paint line;
   private int pressed;
   private List<? extends Object> prompt;
   private Function1<? super JSONObject, Unit> send;
   public Telemetry t;
   private final Paint tp;

   public SlcActionsView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.pressed = -1;
      Paint var3 = new Paint(1);
      var3.setColor(-1);
      var3.setTextAlign(Align.CENTER);
      var3.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
      this.tp = var3;
      Paint var4 = new Paint(1);
      var4.setColor(Color.argb(170, 155, 178, 173));
      this.line = var4;
      Paint var5 = new Paint(1);
      var5.setColor(Color.argb(150, 0, 0, 0));
      this.bg = var5;
   }

   // $VF: synthetic method
   public SlcActionsView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private final List<RectF> boxes(int var1) {
      List var2;
      if (var1 == 2) {
         var2 = CollectionsKt.listOf(
            new RectF[]{
               new RectF(0.0F, 0.0F, this.getWidth() / 2.0F, this.getHeight()), new RectF(this.getWidth() / 2.0F, 0.0F, this.getWidth(), this.getHeight())
            }
         );
      } else {
         var2 = CollectionsKt.listOf(new RectF(0.0F, 0.0F, this.getWidth(), this.getHeight()));
      }

      return var2;
   }

   // $VF: synthetic method
   public static List controls$default(SlcActionsView var0, long var1, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = System.nanoTime();
      }

      return var0.controls(var1);
   }

   private final List<Object> pendingContext(Slc var1) {
      if (var1 != null
         && Intrinsics.areEqual(this.getT().getAlertSize(), "none")
         && Intrinsics.areEqual(var1.kind, "valid")
         && var1.enabled
         && !var1.autoAdoptionPending) {
         float var2 = var1.pendingMps;
         boolean var5 = Float.isInfinite(var2);
         boolean var4 = true;
         boolean var3;
         if (!var5 && !Float.isNaN(var2)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            String var6 = var1.sessionId;
            Intrinsics.checkNotNullExpressionValue(var6, "sessionId");
            if (var6.length() == 0) {
               var3 = var4;
            } else {
               var3 = false;
            }

            if (!var3 && var1.decisionId > 0L && var1.presentationId > 0L) {
               return CollectionsKt.listOf(new Object[]{var1.sessionId, var1.decisionId, var1.presentationId, var1.pendingMps});
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public final List<SlcActionsView.Control> controls(long var1) {
      Slc var9 = this.getT().slcLive(var1);
      if (this.prompt != null && !Intrinsics.areEqual(this.prompt, this.pendingContext(var9))) {
         this.prompt = null;
      }

      if (var9 != null && Intrinsics.areEqual(this.getT().getAlertSize(), "none") && var9.enabled && Intrinsics.areEqual(var9.kind, "valid")) {
         String var8 = var9.sessionId;
         Intrinsics.checkNotNullExpressionValue(var8, "sessionId");
         int var6 = var8.length();
         boolean var7 = true;
         boolean var16;
         if (var6 == 0) {
            var16 = 1;
         } else {
            var16 = 0;
         }

         if (!var16) {
            float var3 = var9.pendingMps;
            if (!Float.isInfinite(var3) && !Float.isNaN(var3)) {
               var16 = (boolean)1;
            } else {
               var16 = (boolean)0;
            }

            if (var16 && var3 > 0.0F && var9.decisionId > 0L) {
               if (!var9.autoAdoptionPending && this.prompt != null) {
                  var8 = var9.sessionId;
                  Intrinsics.checkNotNullExpressionValue(var8, "sessionId");
                  SlcActionsView.Control var28 = new SlcActionsView.Control("accept", "Accept", var8, var9.decisionId, var9.presentationId, var3);
                  String var29 = var9.sessionId;
                  Intrinsics.checkNotNullExpressionValue(var29, "sessionId");
                  return CollectionsKt.listOf(
                     new SlcActionsView.Control[]{var28, new SlcActionsView.Control("reject", "Decline", var29, var9.decisionId, var9.presentationId, var3)}
                  );
               }

               return CollectionsKt.emptyList();
            }

            if (!Float.isInfinite(var3) && !Float.isNaN(var3)) {
               var16 = (boolean)1;
            } else {
               var16 = (boolean)0;
            }

            if (!var16) {
               var3 = var9.speedMps;
               if (!Float.isInfinite(var3) && !Float.isNaN(var3)) {
                  var16 = (boolean)1;
               } else {
                  var16 = (boolean)0;
               }

               if (var16 && var9.speedMps > 0.0F && var9.presentationId > 0L) {
                  if (this.getT().isMetric()) {
                     var3 = 3.6F;
                  } else {
                     var3 = 2.2369363F;
                  }

                  float var4 = var9.adjustedMps;
                  if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                     var16 = (boolean)1;
                  } else {
                     var16 = (boolean)0;
                  }

                  if (var16 && var9.adjustedMps > 0.0F) {
                     var4 = var9.adjustedMps;
                  } else {
                     var4 = var9.speedMps;
                  }

                  float var5 = this.getT().getVCruiseCluster();
                  if (1.0F <= var5 && var5 <= 250.0F) {
                     var16 = (boolean)1;
                  } else {
                     var16 = (boolean)0;
                  }

                  if (var16) {
                     var5 = this.getT().getVCruiseCluster();
                  } else {
                     var5 = Float.NaN;
                  }

                  label118: {
                     if (this.getT().getCruiseEnabled()) {
                        if (!Float.isInfinite(var5) && !Float.isNaN(var5)) {
                           var16 = (boolean)1;
                        } else {
                           var16 = (boolean)0;
                        }

                        if (var16 && MathKt.roundToInt(var5 / 3.6F * var3) < MathKt.roundToInt(var4 * var3)) {
                           var16 = (boolean)1;
                           break label118;
                        }
                     }

                     var16 = (boolean)0;
                  }

                  if (var9.hasAccepted && Math.abs(var9.acceptedMps - var9.speedMps) <= 0.02F && !var9.driverOverride && !var16) {
                     return CollectionsKt.emptyList();
                  }

                  var4 = var9.adjustedMps;
                  if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
                     var16 = var7;
                  } else {
                     var16 = (boolean)0;
                  }

                  if (var16 && var9.adjustedMps > 0.0F) {
                     var16 = MathKt.roundToInt(var9.adjustedMps * var3);
                     var8 = "Use limit " + var16;
                  } else {
                     var8 = "USE LIMIT";
                  }

                  String var10 = var9.sessionId;
                  Intrinsics.checkNotNullExpressionValue(var10, "sessionId");
                  return CollectionsKt.listOf(new SlcActionsView.Control("adopt", var8, var10, var9.decisionId, var9.presentationId, var9.speedMps));
               }
            }

            return CollectionsKt.emptyList();
         }
      }

      return CollectionsKt.emptyList();
   }

   public final Function1<JSONObject, Unit> getSend() {
      return this.send;
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
         List var8 = controls$default(this, 0L, 1, null);
         if (!var8.isEmpty()) {
            float var2 = this.getHeight() / 2.0F;
            var1.drawRoundRect(new RectF(0.0F, 0.0F, this.getWidth(), this.getHeight()), var2, var2, this.bg);
            this.tp.setTextSize(this.getHeight() * 0.42F);
            List var7 = this.boxes(var8.size());
            if (var8.size() == 2) {
               this.line.setStrokeWidth(BigUiViewsKt.access$dpf(this, 1.5F));
               var1.drawLine(this.getWidth() / 2.0F, 0.22F * this.getHeight(), this.getWidth() / 2.0F, 0.78F * this.getHeight(), this.line);
            }

            Iterator var9 = var8.iterator();

            for (int var6 = 0; var9.hasNext(); var6++) {
               SlcActionsView.Control var12 = (SlcActionsView.Control)var9.next();
               RectF var10 = (RectF)var7.get(var6);
               float var5 = var10.centerY() + this.tp.getTextSize() * 0.36F;
               this.tp.setShadowLayer(BigUiViewsKt.access$dpf(this, 2.0F), 0.0F, BigUiViewsKt.access$dpf(this, 1.0F), -16777216);
               var1.drawText(var12.getLabel(), var10.centerX(), var5, this.tp);
               if (this.pressed == var6) {
                  float var4 = this.tp.measureText(var12.getLabel());
                  this.tp.setStrokeWidth(BigUiViewsKt.access$dpf(this, 2.5F));
                  float var3 = var10.centerX();
                  var2 = 2;
                  var1.drawLine(
                     var3 - var4 / var2,
                     var5 + BigUiViewsKt.access$dpf(this, 6.0F),
                     var10.centerX() + var4 / var2,
                     var5 + BigUiViewsKt.access$dpf(this, 6.0F),
                     this.tp
                  );
               }
            }
         }
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      if (this.t == null) {
         return false;
      } else {
         List var3 = controls$default(this, 0L, 1, null);
         if (var3.isEmpty()) {
            return false;
         } else {
            List var4 = this.boxes(var3.size());
            int var2 = 0;
            Iterator var7 = var4.iterator();

            while (true) {
               if (!var7.hasNext()) {
                  var2 = -1;
                  break;
               }

               if (((RectF)var7.next()).contains(var1.getX(), var1.getY())) {
                  break;
               }

               var2++;
            }

            switch (var1.getActionMasked()) {
               case 0:
                  this.pressed = var2;
                  this.invalidate();
                  break;
               case 1:
                  if (var2 >= 0 && var2 == this.pressed) {
                     SlcActionsView.Control var8 = (SlcActionsView.Control)var3.get(var2);
                     Function1 var6 = this.send;
                     if (var6 != null) {
                        JSONObject var5 = new JSONObject()
                           .put("action", "slc_action")
                           .put("kind", var8.getKind())
                           .put("session", var8.getSession())
                           .put("decision", var8.getDecision())
                           .put("presentation", var8.getPresentation())
                           .put("speed", var8.getSpeedMps());
                        Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
                        var6.invoke(var5);
                     }

                     if (!Intrinsics.areEqual(var8.getKind(), "adopt")) {
                        this.prompt = null;
                     }
                  }

                  this.pressed = -1;
                  this.invalidate();
               case 2:
               default:
                  break;
               case 3:
                  this.pressed = -1;
                  this.invalidate();
            }

            return true;
         }
      }
   }

   public final boolean openPrompt() {
      List var1 = this.pendingContext(Telemetry.slcLive$default(this.getT(), 0L, 1, null));
      if (var1 == null) {
         return false;
      } else {
         this.prompt = var1;
         this.invalidate();
         return true;
      }
   }

   public final void setSend(Function1<? super JSONObject, Unit> var1) {
      this.send = var1;
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }

   public static final class Control {
      private final long decision;
      private final String kind;
      private final String label;
      private final long presentation;
      private final String session;
      private final float speedMps;

      public Control(String var1, String var2, String var3, long var4, long var6, float var8) {
         Intrinsics.checkNotNullParameter(var1, "kind");
         Intrinsics.checkNotNullParameter(var2, "label");
         Intrinsics.checkNotNullParameter(var3, "session");
         super();
         this.kind = var1;
         this.label = var2;
         this.session = var3;
         this.decision = var4;
         this.presentation = var6;
         this.speedMps = var8;
      }

      public final long getDecision() {
         return this.decision;
      }

      public final String getKind() {
         return this.kind;
      }

      public final String getLabel() {
         return this.label;
      }

      public final long getPresentation() {
         return this.presentation;
      }

      public final String getSession() {
         return this.session;
      }

      public final float getSpeedMps() {
         return this.speedMps;
      }
   }
}
