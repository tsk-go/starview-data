package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.graphics.PathParser;
import java.util.HashMap;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SvIcon {
   public static final String CAM = "cam";
   public static final String DEVICE = "device";
   public static final String DISPLAY = "display";
   public static final SvIcon INSTANCE = new SvIcon();
   public static final int NA_BG = -15394526;
   public static final int NA_FG = -11840408;
   public static final int OFF_BG = -14999248;
   public static final int OFF_FG = -7497813;
   public static final String PERSON = "person";
   public static final String SOUND = "sound";
   public static final String SPEED = "speed";
   public static final String STEER = "steer";
   public static final String TABLET = "tablet";
   private static final HashMap<String, Path> cache = new HashMap<>();

   private SvIcon() {
   }

   // $VF: synthetic method
   public static View chip$default(SvIcon var0, Context var1, String var2, String var3, int var4, SvIcon.State var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = SvIcon.State.ON;
      }

      return var0.chip(var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Drawable drawable$default(SvIcon var0, String var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = -1;
      }

      return var0.drawable(var1, var2, var3);
   }

   public final int bg(String var1) {
      Intrinsics.checkNotNullParameter(var1, "group");
      Pair var3 = SvIconPaths.INSTANCE.getGROUPS().get(var1);
      int var2;
      if (var3 != null) {
         var2 = ((Number)var3.getSecond()).intValue();
      } else {
         var2 = -14999248;
      }

      return var2;
   }

   public final View chip(Context var1, String var2, String var3, int var4, SvIcon.State var5) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "name");
      Intrinsics.checkNotNullParameter(var3, "group");
      Intrinsics.checkNotNullParameter(var5, "state");
      SvIcon.Chip var6 = new SvIcon.Chip(var1, var2, var3, var5);
      var4 = (int)(var4 * var1.getResources().getDisplayMetrics().density);
      var6.setMinimumWidth(var4);
      var6.setMinimumHeight(var4);
      return var6;
   }

   public final Drawable drawable(String var1, int var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "name");
      return new SvIcon.Glyph(var1, var2, var3);
   }

   public final int fg(String var1) {
      Intrinsics.checkNotNullParameter(var1, "group");
      Pair var3 = SvIconPaths.INSTANCE.getGROUPS().get(var1);
      int var2;
      if (var3 != null) {
         var2 = ((Number)var3.getFirst()).intValue();
      } else {
         var2 = -7497813;
      }

      return var2;
   }

   public final boolean has(String var1) {
      Intrinsics.checkNotNullParameter(var1, "name");
      return SvIconPaths.INSTANCE.getPATHS().containsKey(var1);
   }

   public final Path path(String var1) {
      Intrinsics.checkNotNullParameter(var1, "name");
      Map var4 = cache;
      Object var2 = var4.get(var1);
      Object var5;
      if (var2 == null) {
         String var3 = SvIconPaths.INSTANCE.getPATHS().get(var1);
         var2 = var3;
         if (var3 == null) {
            var2 = MapsKt.getValue(SvIconPaths.INSTANCE.getPATHS(), "slider");
         }

         Path var8 = PathParser.createPathFromPathData((String)var2);
         var2 = var8;
         if (var8 == null) {
            var2 = new Path();
         }

         var4.put(var1, var2);
         var5 = var2;
      } else {
         var5 = var2;
      }

      return (Path)var5;
   }

   public static final class Chip extends View {
      private final Paint bgPaint;
      private String group;
      private String name;
      private final RectF r;
      private SvIcon.State state;

      public Chip(Context var1, String var2, String var3, SvIcon.State var4) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var2, "name");
         Intrinsics.checkNotNullParameter(var3, "group");
         Intrinsics.checkNotNullParameter(var4, "state");
         super(var1);
         this.name = var2;
         this.group = var3;
         this.state = var4;
         this.bgPaint = new Paint(1);
         this.r = new RectF();
      }

      // $VF: synthetic method
      public static void set$default(SvIcon.Chip var0, String var1, String var2, SvIcon.State var3, int var4, Object var5) {
         if ((var4 & 1) != 0) {
            var1 = var0.name;
         }

         if ((var4 & 2) != 0) {
            var2 = var0.group;
         }

         if ((var4 & 4) != 0) {
            var3 = var0.state;
         }

         var0.set(var1, var2, var3);
      }

      public final String getGroup() {
         return this.group;
      }

      public final String getName() {
         return this.name;
      }

      public final SvIcon.State getState() {
         return this.state;
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         float var3 = Math.min(this.getWidth(), this.getHeight());
         SvIcon.State var7 = this.state;
         int var5;
         Integer var10;
         switch (SvIcon.Chip.WhenMappings.$EnumSwitchMapping$0[var7.ordinal()]) {
            case 1:
               var10 = SvIcon.INSTANCE.fg(this.group);
               var5 = SvIcon.INSTANCE.bg(this.group);
               break;
            case 2:
               var10 = -7497813;
               var5 = -14999248;
               break;
            case 3:
               var10 = -11840408;
               var5 = -15394526;
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }

         Pair var11 = TuplesKt.to(var10, var5);
         int var6 = ((Number)var11.component1()).intValue();
         var5 = ((Number)var11.component2()).intValue();
         this.bgPaint.setColor(var5);
         RectF var12 = this.r;
         float var4 = this.getWidth();
         float var2 = 2;
         var12.set((var4 - var3) / var2, (this.getHeight() - var3) / var2, (this.getWidth() + var3) / var2, (this.getHeight() + var3) / var2);
         var1.drawRoundRect(this.r, var3 * 0.3F, 0.3F * var3, this.bgPaint);
         SvIcon.Glyph var13 = new SvIcon.Glyph(this.name, var6, 0, 4, null);
         var5 = (int)(0.23F * var3);
         var13.setBounds((int)this.r.left + var5, (int)this.r.top + var5, (int)this.r.right - var5, (int)this.r.bottom - var5);
         var13.draw(var1);
      }

      protected void onMeasure(int var1, int var2) {
         this.setMeasuredDimension(View.resolveSize(this.getMinimumWidth(), var1), View.resolveSize(this.getMinimumHeight(), var2));
      }

      public final void set(String var1, String var2, SvIcon.State var3) {
         Intrinsics.checkNotNullParameter(var1, "name");
         Intrinsics.checkNotNullParameter(var2, "group");
         Intrinsics.checkNotNullParameter(var3, "state");
         this.name = var1;
         this.group = var2;
         this.state = var3;
         this.invalidate();
      }

      public final void setGroup(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.group = var1;
      }

      public final void setName(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.name = var1;
      }

      public final void setState(SvIcon.State var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.state = var1;
      }

      // $VF: synthetic class
      public final class WhenMappings {
         public static final int[] $EnumSwitchMapping$0;

         static {
            int[] var0 = new int[SvIcon.State.values().length];

            try {
               var0[SvIcon.State.ON.ordinal()] = 1;
            } catch (NoSuchFieldError var4) {
            }

            try {
               var0[SvIcon.State.OFF.ordinal()] = 2;
            } catch (NoSuchFieldError var3) {
            }

            try {
               var0[SvIcon.State.NA.ordinal()] = 3;
            } catch (NoSuchFieldError var2) {
            }

            $EnumSwitchMapping$0 = var0;
         }
      }
   }

   private static final class Glyph extends Drawable {
      private final int color;
      private final Matrix m;
      private final String name;
      private final Path p;
      private final Paint paint;
      private final int size;

      public Glyph(String var1, int var2, int var3) {
         Intrinsics.checkNotNullParameter(var1, "name");
         super();
         this.name = var1;
         this.color = var2;
         this.size = var3;
         Paint var4 = new Paint(1);
         var4.setStyle(Style.STROKE);
         var4.setStrokeCap(Cap.ROUND);
         var4.setStrokeJoin(Join.ROUND);
         this.paint = var4;
         this.m = new Matrix();
         this.p = new Path();
      }

      // $VF: synthetic method
      public Glyph(String var1, int var2, int var3, int var4, DefaultConstructorMarker var5) {
         if ((var4 & 4) != 0) {
            var3 = -1;
         }

         this(var1, var2, var3);
      }

      public void draw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         Rect var8 = this.getBounds();
         Intrinsics.checkNotNullExpressionValue(var8, "getBounds(...)");
         float var5 = Math.min(var8.width(), var8.height()) / 24.0F;
         this.paint.setColor(this.color);
         this.paint.setStrokeWidth(2.0F * var5);
         this.m.setScale(var5, var5);
         Matrix var7 = this.m;
         float var2 = var8.left;
         float var3 = var8.width();
         float var4 = 24;
         float var6 = 2;
         var7.postTranslate(var2 + (var3 - var4 * var5) / var6, var8.top + (var8.height() - var4 * var5) / var6);
         SvIcon.INSTANCE.path(this.name).transform(this.m, this.p);
         var1.drawPath(this.p, this.paint);
      }

      public final int getColor() {
         return this.color;
      }

      public int getIntrinsicHeight() {
         return this.size;
      }

      public int getIntrinsicWidth() {
         return this.size;
      }

      public final String getName() {
         return this.name;
      }

      @Deprecated(
         message = "Deprecated in Java"
      )
      public int getOpacity() {
         return -3;
      }

      public final int getSize() {
         return this.size;
      }

      public void setAlpha(int var1) {
         this.paint.setAlpha(var1);
      }

      public void setColorFilter(ColorFilter var1) {
         this.paint.setColorFilter(var1);
      }
   }

   public static enum State {
      NA,
      OFF,
      ON;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(SvIcon.State.$VALUES);
      private static final SvIcon.State[] $VALUES = $values();

      // $VF: synthetic method
      private static final SvIcon.State[] $values() {
         return new SvIcon.State[]{ON, OFF, NA};
      }

      public static EnumEntries<SvIcon.State> getEntries() {
         return $ENTRIES;
      }
   }
}
