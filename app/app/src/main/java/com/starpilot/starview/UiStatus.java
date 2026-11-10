package com.starpilot.starview;

import android.graphics.Color;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public enum UiStatus {
   AOL(Color.rgb(10, 186, 181)),
   DISENGAGED(Color.rgb(18, 40, 57)),
   ENGAGED(Color.rgb(22, 127, 64)),
   EXPERIMENTAL(Color.rgb(218, 111, 37)),
   LONG_ONLY(Color.rgb(255, 105, 180)),
   OVERRIDE(Color.rgb(137, 146, 141)),
   PULSE_GLIDE(Color.rgb(24, 72, 150)),
   TRAFFIC(Color.rgb(201, 34, 49));

   private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(UiStatus.$VALUES);
   private static final UiStatus[] $VALUES = $values();
   public static final UiStatus.Companion Companion = new UiStatus.Companion(null);
   private final int color;

   // $VF: synthetic method
   private static final UiStatus[] $values() {
      return new UiStatus[]{DISENGAGED, ENGAGED, OVERRIDE, EXPERIMENTAL, AOL, TRAFFIC, LONG_ONLY, PULSE_GLIDE};
   }

   private UiStatus(int var3) {
      this.color = var3;
   }

   public static EnumEntries<UiStatus> getEntries() {
      return $ENTRIES;
   }

   public final int getColor() {
      return this.color;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final UiStatus from(Telemetry var1) {
         Intrinsics.checkNotNullParameter(var1, "t");
         UiStatus var2;
         if (!var1.getEnabled()) {
            if (var1.getAolEnabled() && var1.getStarted()) {
               var2 = UiStatus.AOL;
            } else {
               var2 = UiStatus.DISENGAGED;
            }
         } else if (var1.getPulseGlideCoasting()) {
            var2 = UiStatus.PULSE_GLIDE;
         } else if (var1.getEnabled() && !var1.getActive()) {
            var2 = UiStatus.OVERRIDE;
         } else if (var1.getPauseLateral()) {
            var2 = UiStatus.LONG_ONLY;
         } else if (var1.getTrafficMode()) {
            var2 = UiStatus.TRAFFIC;
         } else if (var1.getExperimentalMode()) {
            var2 = UiStatus.EXPERIMENTAL;
         } else {
            var2 = UiStatus.ENGAGED;
         }

         return var2;
      }
   }
}
