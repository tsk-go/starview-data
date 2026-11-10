package com.starpilot.starview;

import java.util.HashMap;
import kotlin.collections.SetsKt;

public final class SchoolBus {
   private final LeadLamps lamps = new LeadLamps();
   private final HashMap<Long, Float> yellow = new HashMap<>();

   public final void feed(long var1, long var3, float var5, float var6, float var7, float var8) {
      Float var10 = this.yellow.get(var1);
      float var9;
      if (var10 != null) {
         var9 = var10;
      } else {
         var9 = var6;
      }

      var6 = var9 * 0.8F + 0.2F * var6;
      this.yellow.put(var1, var6);
      if (this.yellow.size() > 20) {
         this.yellow.keySet().retainAll(SetsKt.setOf(var1));
      }

      this.lamps.feed(var1, var3, var7, var8);
      if (var6 > 0.3F && this.lamps.anyBlink(var3)) {
         Ideas.INSTANCE.getMore().setBusAtMs(var3);
         Ideas.INSTANCE.getMore().setBusX(var5);
      }
   }
}
