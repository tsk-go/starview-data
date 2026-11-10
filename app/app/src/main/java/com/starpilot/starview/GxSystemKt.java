package com.starpilot.starview;

import android.graphics.Color;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.text.Regex;

public final class GxSystemKt {
   private static final List<String> GxSystem_CORE = CollectionsKt.listOf(new String[]{"StarPilot", "Dom"});
   private static final int GxSystem_DANGER = Color.rgb(255, 105, 105);
   private static final Regex GxSystem_SHA = new Regex("^[a-f0-9]{40}$");

   // $VF: synthetic method
   public static final List access$getGxSystem_CORE$p() {
      return GxSystem_CORE;
   }

   // $VF: synthetic method
   public static final int access$getGxSystem_DANGER$p() {
      return GxSystem_DANGER;
   }

   // $VF: synthetic method
   public static final Regex access$getGxSystem_SHA$p() {
      return GxSystem_SHA;
   }
}
