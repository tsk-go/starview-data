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

public final class RtkCalibration {
   public static final RtkCalibration.Companion Companion = new RtkCalibration.Companion(null);
   private final List<RtkCalibration.Event> events;
   private final int fastS;
   private final int left;
   private final int leftFast;
   private final int longestStillS;
   private final int right;
   private final int rightFast;
   private final int sinceAgoS;
   private final Integer speedBadPct;
   private final int stillNowS;

   public RtkCalibration(List<RtkCalibration.Event> var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, Integer var10) {
      Intrinsics.checkNotNullParameter(var1, "events");
      super();
      this.events = var1;
      this.sinceAgoS = var2;
      this.left = var3;
      this.right = var4;
      this.leftFast = var5;
      this.rightFast = var6;
      this.fastS = var7;
      this.longestStillS = var8;
      this.stillNowS = var9;
      this.speedBadPct = var10;
   }

   // $VF: synthetic method
   public static RtkCalibration copy$default(
      RtkCalibration var0, List var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, Integer var10, int var11, Object var12
   ) {
      if ((var11 & 1) != 0) {
         var1 = var0.events;
      }

      if ((var11 & 2) != 0) {
         var2 = var0.sinceAgoS;
      }

      if ((var11 & 4) != 0) {
         var3 = var0.left;
      }

      if ((var11 & 8) != 0) {
         var4 = var0.right;
      }

      if ((var11 & 16) != 0) {
         var5 = var0.leftFast;
      }

      if ((var11 & 32) != 0) {
         var6 = var0.rightFast;
      }

      if ((var11 & 64) != 0) {
         var7 = var0.fastS;
      }

      if ((var11 & 128) != 0) {
         var8 = var0.longestStillS;
      }

      if ((var11 & 256) != 0) {
         var9 = var0.stillNowS;
      }

      if ((var11 & 512) != 0) {
         var10 = var0.speedBadPct;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   public final List<RtkCalibration.Event> component1() {
      return this.events;
   }

   public final Integer component10() {
      return this.speedBadPct;
   }

   public final int component2() {
      return this.sinceAgoS;
   }

   public final int component3() {
      return this.left;
   }

   public final int component4() {
      return this.right;
   }

   public final int component5() {
      return this.leftFast;
   }

   public final int component6() {
      return this.rightFast;
   }

   public final int component7() {
      return this.fastS;
   }

   public final int component8() {
      return this.longestStillS;
   }

   public final int component9() {
      return this.stillNowS;
   }

   public final RtkCalibration copy(
      List<RtkCalibration.Event> var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, Integer var10
   ) {
      Intrinsics.checkNotNullParameter(var1, "events");
      return new RtkCalibration(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof RtkCalibration)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.events, var1.events)) {
            return false;
         } else if (this.sinceAgoS != var1.sinceAgoS) {
            return false;
         } else if (this.left != var1.left) {
            return false;
         } else if (this.right != var1.right) {
            return false;
         } else if (this.leftFast != var1.leftFast) {
            return false;
         } else if (this.rightFast != var1.rightFast) {
            return false;
         } else if (this.fastS != var1.fastS) {
            return false;
         } else if (this.longestStillS != var1.longestStillS) {
            return false;
         } else {
            return this.stillNowS != var1.stillNowS ? false : Intrinsics.areEqual(this.speedBadPct, var1.speedBadPct);
         }
      }
   }

   public final List<RtkCalibration.Event> getEvents() {
      return this.events;
   }

   public final int getFastS() {
      return this.fastS;
   }

   public final int getLeft() {
      return this.left;
   }

   public final int getLeftFast() {
      return this.leftFast;
   }

   public final int getLongestStillS() {
      return this.longestStillS;
   }

   public final int getRight() {
      return this.right;
   }

   public final int getRightFast() {
      return this.rightFast;
   }

   public final int getSinceAgoS() {
      return this.sinceAgoS;
   }

   public final Integer getSpeedBadPct() {
      return this.speedBadPct;
   }

   public final int getStillNowS() {
      return this.stillNowS;
   }

   @Override
   public int hashCode() {
      int var3 = this.events.hashCode();
      int var10 = Integer.hashCode(this.sinceAgoS);
      int var2 = Integer.hashCode(this.left);
      int var9 = Integer.hashCode(this.right);
      int var8 = Integer.hashCode(this.leftFast);
      int var5 = Integer.hashCode(this.rightFast);
      int var7 = Integer.hashCode(this.fastS);
      int var4 = Integer.hashCode(this.longestStillS);
      int var6 = Integer.hashCode(this.stillNowS);
      int var1;
      if (this.speedBadPct == null) {
         var1 = 0;
      } else {
         var1 = this.speedBadPct.hashCode();
      }

      return ((((((((var3 * 31 + var10) * 31 + var2) * 31 + var9) * 31 + var8) * 31 + var5) * 31 + var7) * 31 + var4) * 31 + var6) * 31 + var1;
   }

   @Override
   public String toString() {
      List var9 = this.events;
      int var3 = this.sinceAgoS;
      int var6 = this.left;
      int var1 = this.right;
      int var7 = this.leftFast;
      int var8 = this.rightFast;
      int var5 = this.fastS;
      int var4 = this.longestStillS;
      int var2 = this.stillNowS;
      Integer var10 = this.speedBadPct;
      return "RtkCalibration(events="
         + var9
         + ", sinceAgoS="
         + var3
         + ", left="
         + var6
         + ", right="
         + var1
         + ", leftFast="
         + var7
         + ", rightFast="
         + var8
         + ", fastS="
         + var5
         + ", longestStillS="
         + var4
         + ", stillNowS="
         + var2
         + ", speedBadPct="
         + var10
         + ")";
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private static final String parse$s(JSONObject var0, String var1) {
         String var2;
         if (var0.has(var1) && !var0.isNull(var1)) {
            var2 = var0.optString(var1);
         } else {
            var2 = null;
         }

         return var2;
      }

      public final RtkCalibration parse(JSONObject var1) {
         if (var1 == null) {
            return null;
         } else {
            JSONArray var14 = var1.optJSONArray("events");
            JSONObject var13 = var1.optJSONObject("drive");
            JSONObject var12 = var13;
            if (var13 == null) {
               var12 = new JSONObject();
            }

            List var20;
            if (var14 == null) {
               var20 = CollectionsKt.emptyList();
            } else {
               Iterable var21 = RangesKt.until(0, var14.length());
               var var16 = new ArrayList();
               Iterator var17 = var21.iterator();

               while (var17.hasNext()) {
                  JSONObject var18 = var14.optJSONObject(((IntIterator)var17).nextInt());
                  RtkCalibration.Event var22;
                  if (var18 != null) {
                     double var2 = var18.optDouble("agoS", 0.0);
                     String var15 = var18.optString("k");
                     Intrinsics.checkNotNullExpressionValue(var15, "optString(...)");
                     var22 = new RtkCalibration.Event(var2, var15, parse$s(var18, "from"), parse$s(var18, "to"));
                  } else {
                     var22 = null;
                  }

                  if (var22 != null) {
                     var16.add(var22);
                  }
               }

               var20 = var16;
            }

            int var8 = var12.optInt("sinceAgoS", 0);
            int var6 = var12.optInt("left", 0);
            int var9 = var12.optInt("right", 0);
            int var11 = var12.optInt("leftFast", 0);
            int var4 = var12.optInt("rightFast", 0);
            int var10 = var12.optInt("fastS", 0);
            int var7 = var12.optInt("longestStillS", 0);
            int var5 = var12.optInt("stillNowS", 0);
            Integer var19;
            if (var1.has("speedBadPct")) {
               var19 = var1.optInt("speedBadPct");
            } else {
               var19 = null;
            }

            return new RtkCalibration(var20, var8, var6, var9, var11, var4, var10, var7, var5, var19);
         }
      }
   }

   public static final class Event {
      private final double agoS;
      private final String from;
      private final String key;
      private final String to;

      public Event(double var1, String var3, String var4, String var5) {
         Intrinsics.checkNotNullParameter(var3, "key");
         super();
         this.agoS = var1;
         this.key = var3;
         this.from = var4;
         this.to = var5;
      }

      // $VF: synthetic method
      public static RtkCalibration.Event copy$default(RtkCalibration.Event var0, double var1, String var3, String var4, String var5, int var6, Object var7) {
         if ((var6 & 1) != 0) {
            var1 = var0.agoS;
         }

         if ((var6 & 2) != 0) {
            var3 = var0.key;
         }

         if ((var6 & 4) != 0) {
            var4 = var0.from;
         }

         if ((var6 & 8) != 0) {
            var5 = var0.to;
         }

         return var0.copy(var1, var3, var4, var5);
      }

      public final double component1() {
         return this.agoS;
      }

      public final String component2() {
         return this.key;
      }

      public final String component3() {
         return this.from;
      }

      public final String component4() {
         return this.to;
      }

      public final RtkCalibration.Event copy(double var1, String var3, String var4, String var5) {
         Intrinsics.checkNotNullParameter(var3, "key");
         return new RtkCalibration.Event(var1, var3, var4, var5);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof RtkCalibration.Event)) {
            return false;
         } else {
            var1 = var1;
            if (Double.compare(this.agoS, var1.agoS) != 0) {
               return false;
            } else if (!Intrinsics.areEqual(this.key, var1.key)) {
               return false;
            } else {
               return !Intrinsics.areEqual(this.from, var1.from) ? false : Intrinsics.areEqual(this.to, var1.to);
            }
         }
      }

      public final double getAgoS() {
         return this.agoS;
      }

      public final String getFrom() {
         return this.from;
      }

      public final String getKey() {
         return this.key;
      }

      public final String getTo() {
         return this.to;
      }

      @Override
      public int hashCode() {
         int var4 = Double.hashCode(this.agoS);
         int var3 = this.key.hashCode();
         String var5 = this.from;
         int var2 = 0;
         int var1;
         if (var5 == null) {
            var1 = 0;
         } else {
            var1 = this.from.hashCode();
         }

         if (this.to != null) {
            var2 = this.to.hashCode();
         }

         return ((var4 * 31 + var3) * 31 + var1) * 31 + var2;
      }

      @Override
      public String toString() {
         double var1 = this.agoS;
         String var5 = this.key;
         String var4 = this.from;
         String var3 = this.to;
         return "Event(agoS=" + var1 + ", key=" + var5 + ", from=" + var4 + ", to=" + var3 + ")";
      }
   }
}
