package com.starpilot.starview;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.text.MatchResult.Destructured;

public final class AmyPrompts {
   public static final AmyPrompts INSTANCE = new AmyPrompts();
   private static final Regex ROUTE = new Regex("^(exit|fork|turn) on the (left|right) in ([0-9.]+) (ft|mi|km) - (\\d+) lanes? to go$");

   private AmyPrompts() {
   }

   public final List<String> ask(int var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "why");
      String var8;
      if (var1 < 0) {
         var8 = "left";
      } else {
         var8 = "right";
      }

      Regex var10 = ROUTE;
      CharSequence var15 = StringsKt.trim(var2).toString();
      String var9 = null;
      MatchResult var16 = Regex.find$default(var10, var15, 0, 2, null);
      if (var16 == null) {
         return CollectionsKt.listOf("ask_" + var8);
      } else {
         String var11;
         String var23;
         Destructured var12 = var16.getDestructured();
         var22 = var12.getMatch().getGroupValues().get(1);
         var11 = var12.getMatch().getGroupValues().get(2);
         String var13 = var12.getMatch().getGroupValues().get(3);
         var2 = var12.getMatch().getGroupValues().get(4);
         var23 = var12.getMatch().getGroupValues().get(5);
         Double var25 = StringsKt.toDoubleOrNull(var13);
         label53:
         if (var25 != null) {
            double var5 = var25.doubleValue();
            double var3;
            if (Intrinsics.areEqual(var2, "ft")) {
               var3 = 0.3048;
            } else {
               if (!Intrinsics.areEqual(var2, "mi")) {
                  var18 = null;
                  break label53;
               }

               var3 = 1609.34;
            }

            var18 = var3 * var5;
         } else {
            var18 = null;
         }

         if (var18 != null) {
            double var21 = var18.doubleValue();
            var9 = INSTANCE.distKey(var21);
         }

         Integer var19 = StringsKt.toIntOrNull(var23);
         if (var19 != null) {
            var1 = var19;
         } else {
            var1 = 0;
         }

         ArrayList var24 = new ArrayList();
         if (var9 != null) {
            var24.add(var22 + "_" + var11 + "_" + var9);
         }

         boolean var7;
         if (1 <= var1 && var1 < 5) {
            var7 = true;
         } else {
            var7 = false;
         }

         StringBuilder var20;
         if (var7) {
            var20 = new StringBuilder().append("lanes").append(var1).append("_");
         } else {
            var20 = new StringBuilder().append("ask_");
         }

         var24.add(var20.append(var8).toString());
         return var24;
      }
   }

   public final String distKey(double var1) {
      String var3;
      if (var1 < 230.0) {
         var3 = "ft500";
      } else if (var1 < 350.0) {
         var3 = "ft1000";
      } else if (var1 < 600.0) {
         var3 = "mi025";
      } else if (var1 < 1000.0) {
         var3 = "mi05";
      } else if (var1 < 1400.0) {
         var3 = "mi075";
      } else {
         var3 = "mi1";
      }

      return var3;
   }

   public final Pair<AmySynth.Style, String> result(String var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "text");
      String var4 = "left";
      String var3;
      if (var2 < 0) {
         var3 = "left";
      } else {
         var3 = "right";
      }

      var1 = var1.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
      Pair var7;
      if (StringsKt.startsWith$default(var1, "changed lanes", false, 2, null)) {
         var7 = TuplesKt.to(AmySynth.Style.DONE, "done");
      } else if (StringsKt.startsWith$default(var1, "test done", false, 2, null)) {
         var7 = TuplesKt.to(AmySynth.Style.SHORT, "test_done");
      } else if (StringsKt.startsWith$default(var1, "openpilot disengaged", false, 2, null)) {
         var7 = TuplesKt.to(AmySynth.Style.STOPPED, "stop_disengaged");
      } else if (StringsKt.startsWith$default(var1, "brake pressed", false, 2, null)) {
         var7 = TuplesKt.to(AmySynth.Style.STOPPED, "stop_brake");
      } else if (StringsKt.startsWith$default(var1, "other blinker", false, 2, null)) {
         var7 = TuplesKt.to(AmySynth.Style.STOPPED, "stop_blinker");
      } else if (StringsKt.contains$default(var1, "blind spot", false, 2, null)) {
         AmySynth.Style var5 = AmySynth.Style.STOPPED;
         if (StringsKt.contains$default(var1, "right", false, 2, null)) {
            var1 = "right";
         } else if (StringsKt.contains$default(var1, "left", false, 2, null)) {
            var1 = var4;
         } else {
            var1 = var3;
         }

         var7 = TuplesKt.to(var5, "stop_bsm_" + var1);
      } else if (StringsKt.startsWith$default(var1, "openpilot didn't start", false, 2, null)) {
         var7 = TuplesKt.to(AmySynth.Style.STOPPED, "stop_nostart");
      } else if (StringsKt.contains$default(var1, "too long", false, 2, null)) {
         var7 = TuplesKt.to(AmySynth.Style.STOPPED, "stop_toolong");
      } else if (StringsKt.startsWith$default(var1, "cancelled", false, 2, null)) {
         var7 = TuplesKt.to(AmySynth.Style.STOPPED, "stop_cancelled");
      } else {
         var7 = TuplesKt.to(AmySynth.Style.STOPPED, "stop_generic");
      }

      return var7;
   }
}
