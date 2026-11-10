package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class IdeasMemory {
   private volatile String comfortText;
   private long cornerCheckMs;
   private final DayView dayView;
   private File dir;
   private final HashMap<String, float[]> lanes;
   private boolean lanesDirty;
   private float[] lastTeach;
   private float leadY;
   private final Lights lights = new Lights();
   private volatile double nudgeOffset;
   private volatile long nudgeUntil;
   private float prevPitch;
   private float prevYaw;
   private volatile long roomUntil;
   private int swayN;
   private double swaySum;
   private ArrayList<JSONObject> teachPaths;
   private volatile ArrayList<float[]> teaching;
   private long turnSentMs;
   private volatile int turnSide;
   private long turnStartMs;
   private double turnYaw;

   public IdeasMemory() {
      this.lanes = new HashMap<>();
      this.leadY = Float.NaN;
      this.turnYaw = Double.NaN;
      this.prevYaw = Float.NaN;
      this.prevPitch = Float.NaN;
      this.comfortText = "";
      this.teachPaths = new ArrayList<>();
      this.dayView = new DayView();
      this.lastTeach = new float[0];
   }

   private final void carsick(Telemetry var1, double var2) {
      if (var1.getDmFace() && !(var2 < 3.0)) {
         if (!Float.isNaN(this.prevYaw)) {
            float var4 = var1.getDmYaw() - this.prevYaw;
            float var5 = var1.getDmPitch() - this.prevPitch;
            this.swaySum += var4 * var4 + var5 * var5;
            this.swayN++;
         }

         this.prevYaw = var1.getDmYaw();
         this.prevPitch = var1.getDmPitch();
         if (this.swayN > 0 && this.swayN % 600 == 0) {
            var2 = Math.sqrt(this.swaySum / this.swayN);
            int var6 = RangesKt.coerceIn((int)(100 - 4000 * var2), 0, 100);
            this.comfortText = "Comfort " + var6 + "/100";
         }
      } else {
         this.prevYaw = Float.NaN;
      }
   }

   private final String cell(Ideas.Here var1) {
      int var6 = (int)((var1.getHdgDeg() + 22.5) / 45.0);
      double var4 = var1.getLat();
      double var2 = 25000;
      long var7 = Math.round(var4 * var2);
      long var9 = Math.round(var1.getLon() * var2);
      return var7 + "_" + var9 + "_" + var6 % 8;
   }

   private final void crowd(Telemetry var1, boolean var2, List<Ideas.Part> var3) {
      Lead var5 = var1.getLeadOne();
      if (var2 && var5.getStatus() && !(var5.getDRel() > 50.0F) && !(Math.abs(var5.getYRel()) > 1.6F)) {
         float var4;
         if (Float.isNaN(this.leadY)) {
            var4 = var5.getYRel();
         } else {
            var4 = this.leadY + (var5.getYRel() - this.leadY) * 0.05F;
         }

         this.leadY = var4;
         if (Math.abs(this.leadY) > 0.15F) {
            var3.add(new Ideas.Part("crowd_line", null, 0.0, RangesKt.coerceIn(-this.leadY * 0.6, -0.3, 0.3), "Following the car ahead's line", 9, 6, null));
         }
      } else {
         this.leadY = Float.NaN;
      }
   }

   private final void laneMemory(Telemetry var1, Ideas.Here var2, boolean var3, ModelSnapshot var4, List<Ideas.Part> var5) {
      if (!Double.isNaN(var2.getYaw()) && !(var2.getV() < 3.0)) {
         double var6 = Ideas.INSTANCE.lateralOnStreet(var2);
         if (!Double.isNaN(var6)) {
            String var15 = this.cell(var2);
            boolean var11 = false;
            if (var3 && var1.getEnabled()) {
               boolean var10;
               if (var4 != null && !var4.getLaneChangeOff()) {
                  var10 = true;
               } else {
                  var10 = false;
               }

               if (!var10) {
                  Map var16 = this.lanes;
                  Object var13 = var16.get(var15);
                  if (var13 == null) {
                     var13 = new float[2];
                     var16.put(var15, var13);
                  }

                  var13 = (float[])var13;
                  if (((Object[])var13)[1] < 50.0F) {
                     ((Object[])var13)[0] += (float)var6;
                     ((Object[])var13)[1]++;
                     this.lanesDirty = true;
                  }

                  return;
               }
            }

            float[] var12 = this.lanes.get(var15);
            if (var12 != null) {
               if (!(var12[1] < 3.0F)) {
                  double var8 = var12[0] / var12[1] - var6;
                  var6 = Math.abs(var8);
                  boolean var18 = var11;
                  if (0.08 <= var6) {
                     var18 = var11;
                     if (var6 <= 1.2) {
                        var18 = true;
                     }
                  }

                  if (var18) {
                     var5.add(new Ideas.Part("lane_memory", null, 0.0, RangesKt.coerceIn(var8, -0.3, 0.3), "Lane from memory", 10, 6, null));
                  }
               }
            }
         }
      }
   }

   private final void teach(Ideas.Here var1, List<Ideas.Part> var2) {
      double var9 = Ideas.INSTANCE.lateralOnStreet(var1);
      ArrayList var16 = this.teaching;
      if (var16 != null) {
         if (Intrinsics.areEqual(var1.getRtk(), "fixed") && !Double.isNaN(var9)) {
            float[] var21 = CollectionsKt.lastOrNull(var16);
            if (var21 == null
               || Math.hypot((var1.getLat() - var21[0]) * 111195.0, (var1.getLon() - var21[1]) * 111320.0 * Math.cos(Math.toRadians(var1.getLat()))) >= 1.0) {
               var16.add(new float[]{(float)var1.getLat(), (float)var1.getLon(), (float)var9, (float)var1.getV()});
            }
         }
      } else if (!Double.isNaN(var9) && !Double.isNaN(var1.getYaw())) {
         JSONArray var25 = null;
         double var3 = 3.0;
         Iterator var19 = this.teachPaths.iterator();
         Intrinsics.checkNotNullExpressionValue(var19, "iterator(...)");

         while (var19.hasNext()) {
            Object var17 = var19.next();
            Intrinsics.checkNotNullExpressionValue(var17, "next(...)");
            JSONArray var20 = ((JSONObject)var17).getJSONArray("pts");
            int var14 = 0;
            int var13 = var20.length() - 1;

            while (var14 < var13) {
               JSONArray var18 = var20.getJSONArray(var14);
               double var7 = (var18.getDouble(1) - var1.getLon()) * 111320.0 * Math.cos(Math.toRadians(var1.getLat()));
               double var11 = (var18.getDouble(0) - var1.getLat()) * 111195.0;
               double var5 = Math.cos(var1.getYaw()) * var7 + Math.sin(var1.getYaw()) * var11;
               var7 = Math.hypot(var7, var11);
               boolean var15;
               if (0.0 <= var5 && var5 <= 6.0) {
                  var15 = true;
               } else {
                  var15 = false;
               }

               var17 = var25;
               var5 = var3;
               if (var15) {
                  var17 = var25;
                  var5 = var3;
                  if (var7 < var3) {
                     var5 = var7;
                     var17 = var18;
                  }
               }

               var14++;
               var25 = (JSONArray)var17;
               var3 = var5;
            }
         }

         if (var25 != null) {
            var3 = var25.getDouble(2);
            var2.add(
               new Ideas.Part("teach", var25.getDouble(3) + 1.0, 0.0, RangesKt.coerceIn(var3 - var9, -0.3, 0.3), "Teach: following your path", 12, 4, null)
            );
         }
      }
   }

   private final void voiceTurnTick(Ideas.Here var1, long var2) {
      if (Double.isNaN(this.turnYaw) && var1 != null && !Double.isNaN(var1.getYaw())) {
         this.turnYaw = var1.getYaw();
      }

      boolean var4;
      if (var1 != null
         && !Double.isNaN(var1.getYaw())
         && !Double.isNaN(this.turnYaw)
         && Math.abs(Math.IEEEremainder(var1.getYaw() - this.turnYaw, Math.PI * 2)) > Math.toRadians(60.0)) {
         var4 = true;
      } else {
         var4 = false;
      }

      DataClient var5 = Link.INSTANCE.getData();
      if (!var4 && var2 - this.turnStartMs <= 60000L) {
         if (var2 - this.turnSentMs >= 250L && var5 != null && var5.getConnected()) {
            this.turnSentMs = var2;
            String var6;
            if (this.turnSide < 0) {
               var6 = "left";
            } else {
               var6 = "right";
            }

            DataClient.request$default(var5, "blink", MapsKt.mapOf(new Pair[]{TuplesKt.to("side", var6), TuplesKt.to("why", "turn")}), null, 4, null);
         }
      } else {
         this.turnSide = 0;
         if (var5 != null) {
            DataClient.request$default(var5, "blink", MapsKt.mapOf(TuplesKt.to("side", "off")), null, 4, null);
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void endOfDrive() {
      label76:
      if (this.lanesDirty) {
         Iterator var5;
         JSONObject var6;
         try {
            var6 = new JSONObject();
            var5 = this.lanes.entrySet().iterator();
         } catch (Exception var15) {
            break label76;
         }

         while (true) {
            try {
               if (!var5.hasNext()) {
                  break;
               }

               Entry var7 = (Entry)var5.next();
               String var4 = (String)var7.getKey();
               float[] var8 = (float[])var7.getValue();
               JSONArray var27 = new JSONArray(CollectionsKt.listOf(new Double[]{(double)var8[0], (double)var8[1]}));
               var6.put(var4, var27);
            } catch (Exception var16) {
               break label76;
            }
         }

         File var28;
         try {
            var22 = this.dir;
         } catch (Exception var14) {
            break label76;
         }

         File var17 = var22;
         if (var22 == null) {
            try {
               Intrinsics.throwUninitializedPropertyAccessException("dir");
            } catch (Exception var13) {
               break label76;
            }

            var17 = null;
         }

         try {
            var28 = new File(var17, "lanes.json");
            String var18 = var6.toString();
            Intrinsics.checkNotNullExpressionValue(var18, "toString(...)");
            FilesKt.writeText$default(var28, var18, null, 2, null);
            this.lanesDirty = false;
         } catch (Exception var10) {
         }
      }

      Lights var25 = this.lights;
      File var23 = this.dir;
      File var19 = var23;
      if (var23 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("dir");
         var19 = null;
      }

      var25.save(new File(var19, "lights.json"));
      label55:
      if (this.swayN > 600) {
         int var1 = RangesKt.coerceIn((int)(100 - Math.sqrt(this.swaySum / this.swayN) * 4000), 0, 100);

         try {
            var23 = this.dir;
         } catch (Exception var12) {
            break label55;
         }

         var19 = var23;
         if (var23 == null) {
            try {
               Intrinsics.throwUninitializedPropertyAccessException("dir");
            } catch (Exception var11) {
               break label55;
            }

            var19 = null;
         }

         try {
            var26 = new File(var19, "comfort.csv");
            long var2 = System.currentTimeMillis();
            StringBuilder var21 = new StringBuilder();
            FilesKt.appendText$default(var26, var21.append(var2).append(",").append(var1).append("\n").toString(), null, 2, null);
         } catch (Exception var9) {
         }
      }

      this.swayN = 0;
      this.swaySum = 0.0;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void forgetTaught() {
      this.teachPaths.clear();

      File var2;
      File var3;
      try {
         var2 = this.dir;
      } catch (Exception var6) {
         return;
      }

      File var1 = var2;
      if (var2 == null) {
         try {
            Intrinsics.throwUninitializedPropertyAccessException("dir");
         } catch (Exception var5) {
            return;
         }

         var1 = null;
      }

      try {
         var3 = new File(var1, "teach.json");
         var3.delete();
      } catch (Exception var4) {
      }
   }

   public final String getComfortText() {
      return this.comfortText;
   }

   public final DayView getDayView() {
      return this.dayView;
   }

   public final Lights getLights() {
      return this.lights;
   }

   public final double getNudgeOffset() {
      return this.nudgeOffset;
   }

   public final long getNudgeUntil() {
      return this.nudgeUntil;
   }

   public final long getRoomUntil() {
      return this.roomUntil;
   }

   public final ArrayList<float[]> getTeaching() {
      return this.teaching;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      File var4 = new File(var1.getFilesDir(), "crazy");
      var4.mkdirs();
      this.dir = var4;

      label123: {
         File var5;
         File var6;
         try {
            var6 = this.dir;
         } catch (Exception var18) {
            break label123;
         }

         var4 = var6;
         if (var6 == null) {
            try {
               Intrinsics.throwUninitializedPropertyAccessException("dir");
            } catch (Exception var17) {
               break label123;
            }

            var4 = null;
         }

         label110: {
            label109: {
               try {
                  var5 = new File(var4, "lanes.json");
                  if (var5.isFile()) {
                     break label109;
                  }
               } catch (Exception var16) {
                  break label123;
               }

               var4 = null;
               break label110;
            }

            var4 = var5;
         }

         label103:
         if (var4 != null) {
            try {
               var26 = new JSONObject(FilesKt.readText$default(var4, null, 1, null));
               var21 = var26.keys();
               Intrinsics.checkNotNullExpressionValue(var21, "keys(...)");
            } catch (Exception var15) {
               break label103;
            }

            while (true) {
               try {
                  if (!var21.hasNext()) {
                     break;
                  }

                  String var7 = (String)var21.next();
                  JSONArray var31 = var26.getJSONArray(var7);
                  this.lanes.put(var7, new float[]{(float)var31.getDouble(0), (float)var31.getDouble(1)});
               } catch (Exception var14) {
                  break;
               }
            }
         }
      }

      label124: {
         File var27;
         File var32;
         try {
            var32 = this.dir;
         } catch (Exception var13) {
            break label124;
         }

         var4 = var32;
         if (var32 == null) {
            try {
               Intrinsics.throwUninitializedPropertyAccessException("dir");
            } catch (Exception var12) {
               break label124;
            }

            var4 = null;
         }

         label84: {
            label83: {
               try {
                  var27 = new File(var4, "teach.json");
                  if (var27.isFile()) {
                     break label83;
                  }
               } catch (Exception var11) {
                  break label124;
               }

               var4 = null;
               break label84;
            }

            var4 = var27;
         }

         label77:
         if (var4 != null) {
            try {
               var28 = new JSONArray(FilesKt.readText$default(var4, null, 1, null));
            } catch (Exception var10) {
               break label77;
            }

            int var2 = 0;

            int var3;
            try {
               var3 = var28.length();
            } catch (Exception var9) {
               break label77;
            }

            for (; var2 < var3; var2++) {
               try {
                  this.teachPaths.add(var28.getJSONObject(var2));
               } catch (Exception var8) {
                  break;
               }
            }
         }
      }

      Lights var33 = this.lights;
      File var29 = this.dir;
      var4 = var29;
      if (var29 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("dir");
         var4 = null;
      }

      var33.load(new File(var4, "lights.json"));
      DayView var34 = this.dayView;
      var29 = var1.getExternalFilesDir(null);
      var4 = var29;
      if (var29 == null) {
         var4 = var1.getFilesDir();
      }

      var34.init(new File(var4, "dayview"));
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final String lastComfort() {
      String var2 = "";

      File var3;
      File var4;
      try {
         var3 = this.dir;
      } catch (Exception var10) {
         return var2;
      }

      File var11 = var3;
      if (var3 == null) {
         try {
            Intrinsics.throwUninitializedPropertyAccessException("dir");
         } catch (Exception var9) {
            return var2;
         }

         var11 = null;
      }

      try {
         var4 = new File(var11, "comfort.csv");
         var13 = CollectionsKt.lastOrNull(FilesKt.readLines$default(var4, null, 1, null));
      } catch (Exception var8) {
         return var2;
      }

      String var1 = var2;
      if (var13 != null) {
         try {
            var14 = StringsKt.split$default(var13, new String[]{","}, false, 0, 6, null);
         } catch (Exception var7) {
            return var2;
         }

         var1 = var2;
         if (var14 != null) {
            try {
               var15 = CollectionsKt.getOrNull(var14, 1);
            } catch (Exception var6) {
               return var2;
            }

            var1 = var2;
            if (var15 != null) {
               try {
                  StringBuilder var12 = new StringBuilder();
                  var1 = var12.append("last drive ").append(var15).append("/100").toString();
               } catch (Exception var5) {
                  return var2;
               }

               if (var1 == null) {
                  var1 = var2;
               }
            }
         }
      }

      return var1;
   }

   public final void setComfortText(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.comfortText = var1;
   }

   public final void setNudgeOffset(double var1) {
      this.nudgeOffset = var1;
   }

   public final void setNudgeUntil(long var1) {
      this.nudgeUntil = var1;
   }

   public final void setRoomUntil(long var1) {
      this.roomUntil = var1;
   }

   public final void setTeaching(ArrayList<float[]> var1) {
      this.teaching = var1;
   }

   public final void startTeaching() {
      this.teaching = new ArrayList<>();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final int stopTeaching(String var1) {
      Intrinsics.checkNotNullParameter(var1, "name");
      ArrayList var6 = this.teaching;
      if (var6 == null) {
         return 0;
      } else {
         this.teaching = null;
         if (var6.size() < 10) {
            return 0;
         } else {
            ArrayList var5 = this.teachPaths;
            JSONObject var8 = new JSONObject().put("name", var1);
            JSONArray var7 = new JSONArray();
            boolean var4 = false;
            Iterator var14 = var6.iterator();
            Intrinsics.checkNotNullExpressionValue(var14, "iterator(...)");

            while (var14.hasNext()) {
               Collection var9 = (Collection)var14.next();
               Intrinsics.checkNotNullExpressionValue(var9, "next(...)");
               float[] var10 = (float[])var9;
               var9 = new ArrayList(var10.length);
               int var3 = var10.length;

               for (int var2 = 0; var2 < var3; var2++) {
                  var9.add((double)var10[var2]);
               }

               List var22 = (List)var9;
               var7.put(new JSONArray(var22));
            }

            Unit var15 = Unit.INSTANCE;
            var5.add(var8.put("pts", var7));

            try {
               var19 = this.dir;
            } catch (Exception var13) {
               return var6.size();
            }

            File var16 = var19;
            if (var19 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("dir");
               } catch (Exception var12) {
                  return var6.size();
               }

               var16 = null;
            }

            try {
               var20 = new File(var16, "teach.json");
               JSONArray var17 = new JSONArray(this.teachPaths);
               var1 = var17.toString();
               Intrinsics.checkNotNullExpressionValue(var1, "toString(...)");
               FilesKt.writeText$default(var20, var1, null, 2, null);
            } catch (Exception var11) {
            }

            return var6.size();
         }
      }
   }

   public final int taughtCount() {
      return this.teachPaths.size();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void tick(Telemetry var1, UiSettings var2, Ideas.Here var3, Detector var4, long var5, List<Ideas.Part> var7) {
      Intrinsics.checkNotNullParameter(var1, "t");
      Intrinsics.checkNotNullParameter(var2, "s");
      Intrinsics.checkNotNullParameter(var7, "out");
      ModelSnapshot var30 = var1.getModel();
      double var8 = var1.getVEgo();
      boolean var15;
      if (var30 != null && var30.getLaneProb().length > 2 && var30.getLaneProb()[1] > 0.7F && var30.getLaneProb()[2] > 0.7F) {
         var15 = true;
      } else {
         var15 = false;
      }

      boolean var16;
      if (var30 != null && var30.getLaneProb().length > 2 && var30.getLaneProb()[1] < 0.4F && var30.getLaneProb()[2] < 0.4F) {
         var16 = true;
      } else {
         var16 = false;
      }

      if (Ideas.INSTANCE.on(var2, "green_countdown")) {
         String var17 = this.lights.chip(var3, var5);
         if (var17 != null) {
            var7.add(new Ideas.Part("green_countdown", null, 0.0, 0.0, var17, 35, 14, null));
         }
      }

      if (Ideas.INSTANCE.on(var2, "hybrid")) {
         Ideas.Part var47 = this.lights.coast(var1, var3, var5);
         if (var47 != null) {
            var7.add(var47);
         }
      }

      if (Ideas.INSTANCE.on(var2, "lane_memory") && var3 != null) {
         this.laneMemory(var1, var3, var15, var30, var7);
      }

      if (Ideas.INSTANCE.on(var2, "crowd_line")) {
         this.crowd(var1, var16, var7);
      } else {
         this.leadY = Float.NaN;
      }

      if (Ideas.INSTANCE.on(var2, "teach") && var3 != null) {
         this.teach(var3, var7);
      }

      if (Ideas.INSTANCE.on(var2, "voice_nudges")) {
         if (var5 < this.nudgeUntil) {
            boolean var14;
            if (this.nudgeOffset == 0.0) {
               var14 = true;
            } else {
               var14 = false;
            }

            if (!var14) {
               double var10 = this.nudgeOffset;
               String var31;
               if (this.nudgeOffset < 0.0) {
                  var31 = "left";
               } else {
                  var31 = "right";
               }

               var7.add(new Ideas.Part("voice_nudges", null, 0.0, var10, "Moved over " + var31 + " (voice)", 15, 6, null));
            }
         }

         if (var5 < this.roomUntil) {
            var7.add(new Ideas.Part("voice_nudges", null, 0.5, 0.0, "Extra room (voice)", 14, 10, null));
         }
      }

      if (Ideas.INSTANCE.on(var2, "carsick")) {
         this.carsick(var1, var8);
      }

      if (this.turnSide != 0) {
         this.voiceTurnTick(var3, var5);
      }

      if (Ideas.INSTANCE.on(var2, "shadows") && var3 != null && !Double.isNaN(var3.getYaw()) && var5 - this.cornerCheckMs > 1000L) {
         this.cornerCheckMs = var5;
         ShadowWatch var48 = ShadowWatch.INSTANCE;

         label206: {
            label205: {
               label242: {
                  try {
                     var27 = OsmRoads.INSTANCE;
                  } catch (Exception var26) {
                     break label242;
                  }

                  double var12;
                  double var40;
                  try {
                     Pair var28 = var27.toLocal(var3.getLat(), var3.getLon());
                     var12 = ((Number)var28.component1()).doubleValue();
                     var40 = ((Number)var28.component2()).doubleValue();
                     var32 = OsmRoads.INSTANCE.matchSeg(var12, var40, var3.getYaw());
                  } catch (Exception var25) {
                     break label242;
                  }

                  Iterable var36 = null;
                  OsmRoads.Way var29 = var36;
                  if (var32 != null) {
                     boolean var41;
                     label195: {
                        label194: {
                           try {
                              if (var32.getDist() < 15.0) {
                                 break label194;
                              }
                           } catch (Exception var24) {
                              break label242;
                           }

                           var41 = false;
                           break label195;
                        }

                        var41 = true;
                     }

                     if (!var41) {
                        var32 = null;
                     }

                     var29 = var36;
                     if (var32 != null) {
                        try {
                           var29 = var32.getWay();
                        } catch (Exception var23) {
                           break label242;
                        }
                     }
                  }

                  if (var29 == null) {
                     break label205;
                  }

                  int var45;
                  label183: {
                     label244: {
                        try {
                           IntRange var33 = new IntRange(-1, 1);
                           var34 = RangesKt.step(var33, 2);
                           if (var34 instanceof Collection && ((Collection)var34).isEmpty()) {
                              break label244;
                           }
                        } catch (Exception var22) {
                           break label242;
                        }

                        try {
                           var35 = var34.iterator();
                        } catch (Exception var19) {
                           break label242;
                        }

                        while (true) {
                           label170: {
                              label245: {
                                 try {
                                    if (!var35.hasNext()) {
                                       break;
                                    }

                                    var45 = ((IntIterator)var35).nextInt();
                                    var36 = BlinkerTurn.INSTANCE.corners(OsmRoads.INSTANCE.near(var12, var40, 80.0), var29, var12, var40, var3.getYaw(), var45);
                                    if (var36 instanceof Collection && ((Collection)var36).isEmpty()) {
                                       break label245;
                                    }
                                 } catch (Exception var21) {
                                    break label242;
                                 }

                                 try {
                                    var38 = var36.iterator();
                                 } catch (Exception var18) {
                                    break label242;
                                 }

                                 while (true) {
                                    try {
                                       if (!var38.hasNext()) {
                                          break;
                                       }

                                       var8 = ((BlinkerTurn.Corner)var38.next()).getAhead();
                                    } catch (Exception var20) {
                                       break label242;
                                    }

                                    boolean var43;
                                    if (-5.0 <= var8 && var8 <= 40.0) {
                                       var43 = true;
                                    } else {
                                       var43 = false;
                                    }

                                    if (var43) {
                                       var45 = (boolean)1;
                                       break label170;
                                    }
                                 }

                                 var45 = (boolean)0;
                                 break label170;
                              }

                              var45 = (boolean)0;
                           }

                           if (var45) {
                              var45 = 1;
                              break label183;
                           }
                        }

                        var45 = 0;
                        break label183;
                     }

                     var45 = 0;
                  }

                  if (var45) {
                     var15 = true;
                     break label206;
                  }
                  break label205;
               }

               var15 = false;
               break label206;
            }

            var15 = false;
         }

         var48.setNearCorner(var15);
      }

      ShadowWatch.INSTANCE.setWanted(Ideas.INSTANCE.on(var2, "shadows"));
      this.dayView.setWanted(Ideas.INSTANCE.on(var2, "day_view"));
      if (this.dayView.getWanted() && var3 != null) {
         this.dayView.setHere(var3);
      }
   }

   public final void voiceTurn(int var1) {
      this.turnSide = var1;
      this.turnStartMs = System.currentTimeMillis();
      this.turnYaw = Double.NaN;
   }
}
