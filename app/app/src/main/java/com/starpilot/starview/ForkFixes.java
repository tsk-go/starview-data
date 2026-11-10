package com.starpilot.starview;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

public final class ForkFixes {
   public static final ForkFixes INSTANCE = new ForkFixes();

   // $VF: synthetic method
   public static String $r8$lambda$0Nbyy0Z5YfiKvKXgwskcrYPqNys(MatchResult var0) {
      return parse$lambda$1(var0);
   }

   private ForkFixes() {
   }

   private static final String parse$lambda$1(MatchResult var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return StringsKt.trim(var0.getGroupValues().get(1)).toString();
   }

   public final List<String> parse(String var1) {
      Intrinsics.checkNotNullParameter(var1, "md");
      int var4 = StringsKt.indexOf$default(var1, "# Fork fixes to drop", 0, false, 6, null);
      if (var4 < 0) {
         return CollectionsKt.emptyList();
      } else {
         int var3 = StringsKt.indexOf$default(var1, "\n# ", var4 + 1, false, 4, null);
         int var2 = var3;
         if (var3 < 0) {
            var2 = var1.length();
         }

         Regex var5 = new Regex("(?m)^\\d+\\. \\*\\*(.+?)\\*\\*");
         var1 = var1.substring(var4, var2);
         Intrinsics.checkNotNullExpressionValue(var1, "substring(...)");
         return SequencesKt.toList(SequencesKt.map(Regex.findAll$default(var5, var1, 0, 2, null), new ForkFixes$$ExternalSyntheticLambda0()));
      }
   }
}
