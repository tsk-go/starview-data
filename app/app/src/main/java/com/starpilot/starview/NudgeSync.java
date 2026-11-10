package com.starpilot.starview;

import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

public final class NudgeSync {
   public static final NudgeSync INSTANCE = new NudgeSync();
   private static volatile Integer commaPct;
   private static volatile long retryAt;
   private static volatile int sent = -1;
   private static volatile boolean wasConnected;

   // $VF: synthetic method
   public static Unit $r8$lambda$hH8TibGYKuXNbSszAQw5I0bks_I(int var0, JSONObject var1) {
      return tick$lambda$0(var0, var1);
   }

   private NudgeSync() {
   }

   private static final Unit tick$lambda$0(int var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "ack");
      if (var1.optBoolean("ok")) {
         commaPct = var1.optInt("pct", var0);
      } else {
         commaPct = null;
         sent = -1;
         retryAt = System.currentTimeMillis() + 60000L;
      }

      return Unit.INSTANCE;
   }

   public final Integer getCommaPct() {
      return commaPct;
   }

   public final void push(float var1) {
      sent = -1;
      retryAt = 0L;
      this.tick(var1);
   }

   public final void tick(float var1) {
      DataClient var5 = Link.INSTANCE.getData();
      boolean var2;
      if (var5 != null && var5.getConnected()) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      if (!var2) {
         wasConnected = false;
      } else {
         var2 = RangesKt.coerceIn(Math.round(var1), 30, 150);
         long var3 = System.currentTimeMillis();
         if (!wasConnected || sent != var2) {
            if (var3 >= retryAt) {
               wasConnected = true;
               sent = var2;
               Intrinsics.checkNotNull(var5);
               var5.request("nudge_set", MapsKt.mapOf(TuplesKt.to("pct", var2)), new NudgeSync$$ExternalSyntheticLambda0(var2));
            }
         }
      }
   }
}
