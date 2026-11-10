package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class RtkSensor {
   public static final RtkSensor.Companion Companion = new RtkSensor.Companion(null);
   private final String calib;
   private final List<String> faults;
   private final int hz;
   private final boolean ready;
   private final String type;
   private final boolean used;

   public RtkSensor(String var1, boolean var2, boolean var3, String var4, int var5, List<String> var6) {
      Intrinsics.checkNotNullParameter(var1, "type");
      Intrinsics.checkNotNullParameter(var4, "calib");
      Intrinsics.checkNotNullParameter(var6, "faults");
      super();
      this.type = var1;
      this.used = var2;
      this.ready = var3;
      this.calib = var4;
      this.hz = var5;
      this.faults = var6;
   }

   // $VF: synthetic method
   public static RtkSensor copy$default(RtkSensor var0, String var1, boolean var2, boolean var3, String var4, int var5, List var6, int var7, Object var8) {
      if ((var7 & 1) != 0) {
         var1 = var0.type;
      }

      if ((var7 & 2) != 0) {
         var2 = var0.used;
      }

      if ((var7 & 4) != 0) {
         var3 = var0.ready;
      }

      if ((var7 & 8) != 0) {
         var4 = var0.calib;
      }

      if ((var7 & 16) != 0) {
         var5 = var0.hz;
      }

      if ((var7 & 32) != 0) {
         var6 = var0.faults;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6);
   }

   public final String component1() {
      return this.type;
   }

   public final boolean component2() {
      return this.used;
   }

   public final boolean component3() {
      return this.ready;
   }

   public final String component4() {
      return this.calib;
   }

   public final int component5() {
      return this.hz;
   }

   public final List<String> component6() {
      return this.faults;
   }

   public final RtkSensor copy(String var1, boolean var2, boolean var3, String var4, int var5, List<String> var6) {
      Intrinsics.checkNotNullParameter(var1, "type");
      Intrinsics.checkNotNullParameter(var4, "calib");
      Intrinsics.checkNotNullParameter(var6, "faults");
      return new RtkSensor(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof RtkSensor)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.type, var1.type)) {
            return false;
         } else if (this.used != var1.used) {
            return false;
         } else if (this.ready != var1.ready) {
            return false;
         } else if (!Intrinsics.areEqual(this.calib, var1.calib)) {
            return false;
         } else {
            return this.hz != var1.hz ? false : Intrinsics.areEqual(this.faults, var1.faults);
         }
      }
   }

   public final String getCalib() {
      return this.calib;
   }

   public final List<String> getFaults() {
      return this.faults;
   }

   public final int getHz() {
      return this.hz;
   }

   public final boolean getReady() {
      return this.ready;
   }

   public final String getType() {
      return this.type;
   }

   public final boolean getUsed() {
      return this.used;
   }

   @Override
   public int hashCode() {
      return (
               (((this.type.hashCode() * 31 + Boolean.hashCode(this.used)) * 31 + Boolean.hashCode(this.ready)) * 31 + this.calib.hashCode()) * 31
                  + Integer.hashCode(this.hz)
            )
            * 31
         + this.faults.hashCode();
   }

   @Override
   public String toString() {
      String var4 = this.type;
      boolean var2 = this.used;
      boolean var3 = this.ready;
      String var6 = this.calib;
      int var1 = this.hz;
      List var5 = this.faults;
      return "RtkSensor(type=" + var4 + ", used=" + var2 + ", ready=" + var3 + ", calib=" + var6 + ", hz=" + var1 + ", faults=" + var5 + ")";
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final RtkSensor parse(JSONObject var1) {
         if (var1 == null) {
            return null;
         } else {
            JSONArray var7 = var1.optJSONArray("faults");
            String var5 = var1.optString("type", "?");
            Intrinsics.checkNotNullExpressionValue(var5, "optString(...)");
            boolean var3 = var1.optBoolean("used");
            boolean var4 = var1.optBoolean("ready");
            String var6 = var1.optString("calib", "");
            Intrinsics.checkNotNullExpressionValue(var6, "optString(...)");
            int var2 = var1.optInt("hz", 0);
            List var9;
            if (var7 == null) {
               var9 = CollectionsKt.emptyList();
            } else {
               Iterable var8 = RangesKt.until(0, var7.length());
               var var10 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10));
               Iterator var11 = var8.iterator();

               while (var11.hasNext()) {
                  var10.add(var7.optString(((IntIterator)var11).nextInt()));
               }

               var9 = var10;
            }

            return new RtkSensor(var5, var3, var4, var6, var2, var9);
         }
      }
   }
}
