package com.starpilot.starview;

import kotlin.text.StringsKt;

public final class GnssSat {
   private final int azimuth;
   private final int cno;
   private final int elevation;
   private final int flags;
   private final int gnssId;
   private final int svId;

   public GnssSat(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.gnssId = var1;
      this.svId = var2;
      this.cno = var3;
      this.elevation = var4;
      this.azimuth = var5;
      this.flags = var6;
   }

   public final int getAzimuth() {
      return this.azimuth;
   }

   public final int getCno() {
      return this.cno;
   }

   public final int getElevation() {
      return this.elevation;
   }

   public final int getFlags() {
      return this.flags;
   }

   public final int getGnssId() {
      return this.gnssId;
   }

   public final String getLabel() {
      String var1 = this.getSystem();
      String var2 = StringsKt.padStart(String.valueOf(this.svId), 2, '0');
      return var1 + var2;
   }

   public final int getSvId() {
      return this.svId;
   }

   public final String getSystem() {
      String var1;
      switch (this.gnssId) {
         case 0:
            var1 = "G";
            break;
         case 1:
            var1 = "S";
            break;
         case 2:
            var1 = "E";
            break;
         case 3:
            var1 = "C";
            break;
         case 4:
            var1 = "I";
            break;
         case 5:
            var1 = "J";
            break;
         case 6:
            var1 = "R";
            break;
         default:
            var1 = "?";
      }

      return var1;
   }

   public final boolean getUsed() {
      boolean var1;
      if ((this.flags & 8) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
