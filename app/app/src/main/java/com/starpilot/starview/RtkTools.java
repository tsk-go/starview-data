package com.starpilot.starview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimeZone;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.text.MatchResult.Destructured;

public final class RtkTools {
   public static final RtkTools INSTANCE = new RtkTools();
   private static final Map<String, String> KEY_NAMES;
   private static final List<RtkTools.Tool> TOOLS;

   // $VF: synthetic method
   public static CharSequence $r8$lambda$TmZf7NEVImpzQu4KO8X5pmFnbzA(Entry var0) {
      return sensors$lambda$16(var0);
   }

   static {
      RtkTools.Tool var0 = new RtkTools.Tool(
         "realign",
         "Recalibrate mount",
         "Forgets how the board sits in the car and learns it again. Use it after moving or re-taping the board, then drive with some turns (about 10 turns of 90° or more at 20+ mph).",
         false,
         false,
         24,
         null
      );
      RtkTools.Tool var3 = new RtkTools.Tool(
         "hot", "Restart", "Restarts satellite tracking and keeps everything learned. The fix is back in a few seconds.", false, false, 24, null
      );
      RtkTools.Tool var1 = new RtkTools.Tool(
         "warm",
         "Warm restart",
         "Restarts and forgets the satellites' orbits (keeps the position and time). The fix is back in about 30 seconds.",
         false,
         false,
         24,
         null
      );
      RtkTools.Tool var5 = new RtkTools.Tool(
         "cold",
         "Cold restart",
         "Restarts from nothing: no satellites, position or time. The fix takes a minute or two (longer under a roof). Only while stopped.",
         true,
         false,
         16,
         null
      );
      RtkTools.Tool var2 = new RtkTools.Tool(
         "save",
         "Save settings",
         "Writes StarView's receiver settings (10 Hz, automotive, sensor fusion and mount learning on) to the receiver's own flash, so they stay when it's unplugged.",
         false,
         false,
         24,
         null
      );
      RtkTools.Tool var4 = new RtkTools.Tool(
         "factory",
         "Factory reset",
         "Erases every setting on the receiver, then writes StarView's back. Its calibration starts over. Only while stopped.",
         true,
         true
      );
      TOOLS = CollectionsKt.listOf(new RtkTools.Tool[]{var0, var3, var1, var5, var2, var4});
      Pair var7 = TuplesKt.to("fusion", "Fusion");
      Pair var14 = TuplesKt.to("imuInit", "Motion sensors");
      Pair var9 = TuplesKt.to("insInit", "Position");
      Pair var13 = TuplesKt.to("mountInit", "Mount alignment");
      Pair var6 = TuplesKt.to("mount", "Mount stage");
      Pair var8 = TuplesKt.to("speedInit", "Car speed");
      Pair var10 = TuplesKt.to("speedCalib", "Speed calibration");
      Pair var11 = TuplesKt.to("fixType", "Fix");
      Pair var12 = TuplesKt.to("carHeading", "Car heading");
      KEY_NAMES = MapsKt.mapOf(new Pair[]{var7, var14, var9, var13, var6, var8, var10, var11, var12, TuplesKt.to("tool", "Tool")});
   }

   private RtkTools() {
   }

   private static final CharSequence sensors$lambda$16(Entry var0) {
      Intrinsics.checkNotNullParameter(var0, "<destruct>");
      Object var5 = var0.getKey();
      Intrinsics.checkNotNullExpressionValue(var5, "component1(...)");
      String var6 = (String)var5;
      Iterable var12 = (Iterable)var0.getValue();
      Intrinsics.checkNotNullExpressionValue(var12, "component2(...)");
      List var9 = (List)var12;
      var12 = var9;
      var var8 = new ArrayList();
      Iterator var10 = var12.iterator();

      while (true) {
         boolean var4 = var10.hasNext();
         String var7 = "";
         if (!var4) {
            List var14 = var8;
            if (!var14.isEmpty()) {
               String var15 = CollectionsKt.joinToString$default(var14, "/", null, null, 0, null, null, 62, null);
               var5 = var6 + " " + var15;
            } else {
               var5 = var6;
            }

            var10 = var9.iterator();
            if (!var10.hasNext()) {
               var12 = null;
            } else {
               var12 = (Iterable)var10.next();
               if (var10.hasNext()) {
                  String var30 = ((RtkSensor)var12).getCalib();
                  byte var19;
                  if (Intrinsics.areEqual(var30, "not calibrated")) {
                     var19 = 0;
                  } else if (Intrinsics.areEqual(var30, "calibrating")) {
                     var19 = 1;
                  } else {
                     var19 = 2;
                  }

                  byte var2 = var19;

                  while (true) {
                     Object var31 = var10.next();
                     String var11 = ((RtkSensor)var31).getCalib();
                     if (Intrinsics.areEqual(var11, "not calibrated")) {
                        var19 = 0;
                     } else if (Intrinsics.areEqual(var11, "calibrating")) {
                        var19 = 1;
                     } else {
                        var19 = 2;
                     }

                     byte var3 = var2;
                     if (var2 > var19) {
                        var12 = (Iterable)var31;
                        var3 = var19;
                     }

                     if (!var10.hasNext()) {
                        break;
                     }

                     var2 = var3;
                  }
               }
            }

            Intrinsics.checkNotNull(var12);
            RtkSensor var17 = (RtkSensor)var12;
            Iterator var25 = var9.iterator();
            if (!var25.hasNext()) {
               throw new NoSuchElementException();
            } else {
               int var21 = ((RtkSensor)var25.next()).getHz();

               while (var25.hasNext()) {
                  int var22 = ((RtkSensor)var25.next()).getHz();
                  if (var21 < var22) {
                     var21 = var22;
                  }
               }

               Iterable var32 = var9;
               var var26 = new ArrayList();
               Iterator var33 = var32.iterator();

               while (var33.hasNext()) {
                  CollectionsKt.addAll(var26, ((RtkSensor)var33.next()).getFaults());
               }

               List var27 = var26;
               List var34 = CollectionsKt.distinct(var27);
               String var18;
               if (!var17.getReady()) {
                  var18 = "not ready";
               } else {
                  var18 = var17.getCalib();
               }

               var6 = var7;
               if (!var34.isEmpty()) {
                  var6 = CollectionsKt.joinToString$default(var34, ", ", null, null, 0, null, null, 62, null);
                  var6 = " (" + var6 + ")";
               }

               return var5 + " " + var21 + " Hz: " + var18 + var6;
            }
         }

         var5 = StringsKt.substringAfter(((RtkSensor)var10.next()).getType(), ' ', "");
         boolean var1;
         if (var5.length() == 1) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (!var1) {
            var5 = null;
         }

         if (var5 != null) {
            var8.add(var5);
         }
      }
   }

   private final RtkTools.State st(String var1) {
      switch (var1.hashCode()) {
         case -400079795:
            if (var1.equals("initializing")) {
               return RtkTools.State.NOW;
            }
            break;
         case 109935:
            if (var1.equals("off")) {
               return RtkTools.State.OFF;
            }
            break;
         case 3089282:
            if (var1.equals("done")) {
               return RtkTools.State.DONE;
            }
      }

      return RtkTools.State.WAITING;
   }

   // $VF: synthetic method
   public static List timeline$default(RtkTools var0, RtkStatus var1, long var2, TimeZone var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var4 = TimeZone.getDefault();
      }

      return var0.timeline(var1, var2, var4);
   }

   public final String duration(int var1) {
      StringBuilder var3;
      if (var1 < 60) {
         var3 = new StringBuilder().append(var1);
      } else {
         if (var1 >= 3600) {
            int var5 = var1 / 3600;
            var1 = var1 % 3600 / 60;
            var3 = new StringBuilder().append(var5).append(" h ").append(var1).append(" min");
            return var3.toString();
         }

         int var2 = var1 / 60;
         var3 = new StringBuilder().append(var2).append(" min ").append(var1 % 60);
      }

      var3 = var3.append(" s");
      return var3.toString();
   }

   public final String firmware(RtkStatus var1) {
      Intrinsics.checkNotNullParameter(var1, "k");
      boolean var2;
      if (var1.getFirmware().length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         return "not reported yet";
      } else {
         Iterable var4 = CollectionsKt.listOf(new String[]{var1.getModule(), var1.getFirmware()});
         var var3 = new ArrayList();

         for (Object var5 : var4) {
            if (((String)var5).length() > 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (var2) {
               var3.add(var5);
            }
         }

         List var11 = var3;
         String var12 = CollectionsKt.joinToString$default(var11, ", ", null, null, 0, null, null, 62, null);
         Regex var16 = new Regex("HPS (\\d+)\\.(\\d+)");
         CharSequence var6 = var1.getFirmware();
         var4 = null;
         MatchResult var17 = Regex.find$default(var16, var6, 0, 2, null);
         Integer var7 = var4;
         if (var17 != null) {
            Destructured var18 = var17.getDestructured();
            var7 = var4;
            if (var18 != null) {
               String var8 = var18.getMatch().getGroupValues().get(1);
               String var15 = var18.getMatch().getGroupValues().get(2);
               var7 = Integer.parseInt(var8) * 100 + Integer.parseInt(var15);
            }
         }

         String var9;
         if (var7 != null && var7 < 120) {
            var9 = var12 + ": too old, its IMU may never start (flash HPS 1.30 with u-center)";
         } else {
            var9 = var12;
         }

         return var9;
      }
   }

   public final List<RtkTools.Tool> getTOOLS() {
      return TOOLS;
   }

   public final String label(String var1) {
      Intrinsics.checkNotNullParameter(var1, "op");
      Iterator var3 = TOOLS.iterator();

      Object var2;
      do {
         if (!var3.hasNext()) {
            var2 = null;
            break;
         }

         var2 = (RtkTools.Tool)var3.next();
      } while (!Intrinsics.areEqual(var2.getOp(), var1));

      var2 = var2;
      if (var2 != null) {
         String var6 = var2.getLabel();
         if (var6 != null) {
            return var6;
         }
      }

      String var5;
      if (Intrinsics.areEqual(var1, "speed_on")) {
         var5 = "Speed from the car on";
      } else if (Intrinsics.areEqual(var1, "speed_off")) {
         var5 = "Speed from the car off";
      } else {
         var5 = var1;
      }

      return var5;
   }

   public final String lastAction(RtkStatus var1) {
      if (var1 != null) {
         boolean var4;
         if (var1.getActionOp().length() == 0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         if (!var4) {
            String var11;
            label40: {
               var11 = var1.getActionState();
               switch (var11.hashCode()) {
                  case 3089282:
                     if (var11.equals("done")) {
                        var11 = "done";
                        break label40;
                     }
                     break;
                  case 3526552:
                     if (var11.equals("sent")) {
                        var11 = "sent, waiting for the receiver";
                        break label40;
                     }
                     break;
                  case 144428797:
                     if (var11.equals("no answer")) {
                        var11 = "no answer from the receiver";
                        break label40;
                     }
                     break;
                  case 1085547216:
                     if (var11.equals("refused")) {
                        var11 = "the receiver refused it";
                        break label40;
                     }
               }

               var11 = var1.getActionState();
            }

            double var2 = var1.getActionAgeS();
            StringBuilder var6;
            String var7;
            if (var2 < 60.0) {
               var4 = (int)var2;
               var6 = new StringBuilder().append(var4);
               var7 = " s ago";
            } else {
               var4 = (int)(var2 / 60);
               var6 = new StringBuilder().append(var4);
               var7 = " min ago";
            }

            String var12 = var6.append(var7).toString();
            String var8 = this.label(var1.getActionOp());
            return var8 + ": " + var11 + ", " + var12;
         }
      }

      return "";
   }

   public final String mountAngles(RtkStatus var1) {
      Intrinsics.checkNotNullParameter(var1, "k");
      double var2 = var1.getMountYawDeg();
      boolean var6 = Double.isInfinite(var2);
      boolean var5 = false;
      boolean var4;
      if (!var6 && !Double.isNaN(var2)) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (!var4) {
         return "not reported yet";
      } else {
         String var7 = String.format(Locale.US, "yaw %.1f°", Arrays.copyOf(new Object[]{var1.getMountYawDeg()}, 1));
         Intrinsics.checkNotNullExpressionValue(var7, "format(...)");
         List var16 = CollectionsKt.mutableListOf(new String[]{var7});
         var2 = var1.getMountPitchDeg();
         if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            Collection var9 = var16;
            String var8 = String.format(Locale.US, "pitch %.1f°", Arrays.copyOf(new Object[]{var1.getMountPitchDeg()}, 1));
            Intrinsics.checkNotNullExpressionValue(var8, "format(...)");
            var9.add(var8);
         }

         var2 = var1.getMountRollDeg();
         var4 = var5;
         if (!Double.isInfinite(var2)) {
            var4 = var5;
            if (!Double.isNaN(var2)) {
               var4 = true;
            }
         }

         if (var4) {
            Collection var18 = var16;
            String var19 = String.format(Locale.US, "roll %.1f°", Arrays.copyOf(new Object[]{var1.getMountRollDeg()}, 1));
            Intrinsics.checkNotNullExpressionValue(var19, "format(...)");
            var18.add(var19);
         }

         var7 = CollectionsKt.joinToString$default(var16, ", ", null, null, 0, null, null, 62, null);
         String var11;
         if (!var1.getMountErrors().isEmpty()) {
            String var10 = CollectionsKt.joinToString$default(var1.getMountErrors(), ", ", null, null, 0, null, null, 62, null);
            var11 = "  (errors: " + var10 + ")";
         } else {
            var11 = "";
         }

         return var7 + var11;
      }
   }

   public final List<RtkTools.Need> needs(RtkStatus var1) {
      Intrinsics.checkNotNullParameter(var1, "k");
      RtkCalibration var9 = var1.getCalibration();
      if (var9 == null) {
         return CollectionsKt.emptyList();
      } else {
         int var4 = var9.getLeft() + var9.getRight();
         int var3 = var9.getLeftFast() + var9.getRightFast();
         int var2 = var9.getLeft();
         boolean var8 = true;
         boolean var16;
         if (var2 > 0 && var9.getRight() > 0) {
            var16 = true;
         } else {
            var16 = false;
         }

         String var12;
         if (var9.getStillNowS() > 0) {
            int var5 = var9.getStillNowS();
            int var6 = var9.getLongestStillS();
            var12 = "Stopped now " + var5 + " s (longest " + var6 + " s, 20 s is enough)";
         } else {
            int var17 = var9.getLongestStillS();
            var12 = "Longest stop " + var17 + " s (20 s is enough)";
         }

         boolean var7;
         if (var9.getLongestStillS() >= 20) {
            var7 = true;
         } else {
            var7 = false;
         }

         RtkTools.Need var10 = new RtkTools.Need("A stop with the engine on", var12, var7, RangesKt.coerceAtMost(var9.getLongestStillS() / 20.0F, 1.0F));
         int var19 = var9.getLeft();
         int var18 = var9.getRight();
         String var13;
         if (var4 > 0) {
            var13 = "; " + var3 + " at 20+ mph";
         } else {
            var13 = "";
         }

         String var14 = var4 + " so far: " + var19 + " left, " + var18 + " right" + var13 + " (about 10 at 20+ mph, both ways; slow corners count half here)";
         if (var16 && var3 + (var4 - var3) * 0.5F >= 10.0F) {
            var7 = true;
         } else {
            var7 = false;
         }

         RtkTools.Need var15 = new RtkTools.Need("Turns of 90°", var14, var7, RangesKt.coerceAtMost((var3 + (var4 - var3) * 0.5F) / 10.0F, 1.0F));
         String var11 = this.duration(var9.getFastS());
         var11 = var11 + " so far (5 min is plenty)";
         if (var9.getFastS() >= 300) {
            var7 = var8;
         } else {
            var7 = false;
         }

         RtkTools.Need var22 = new RtkTools.Need("Driving at 20+ mph", var11, var7, RangesKt.coerceAtMost(var9.getFastS() / 300.0F, 1.0F));
         return CollectionsKt.listOf(new RtkTools.Need[]{var10, var15, var22});
      }
   }

   public final float overall(RtkStatus var1) {
      Intrinsics.checkNotNullParameter(var1, "k");
      return Intrinsics.areEqual(var1.getFusion(), "on")
         ? 1.0F
         : RangesKt.coerceIn(
            this.stepProgress(var1, "Motion sensors") * 0.15F
               + this.stepProgress(var1, "Mount alignment") * 0.45F
               + this.stepProgress(var1, "Position") * 0.25F,
            0.0F,
            0.95F
         );
   }

   public final String sensors(List<RtkSensor> var1) {
      Intrinsics.checkNotNullParameter(var1, "list");
      if (var1.isEmpty()) {
         return "none reported";
      } else {
         LinkedHashMap var5 = new LinkedHashMap();

         for (RtkSensor var7 : var1) {
            if (!Intrinsics.areEqual(var7.getType(), "gyro temperature")) {
               String var9;
               if (StringsKt.startsWith$default(var7.getType(), "gyro", false, 2, null)) {
                  var9 = "Gyro";
               } else if (StringsKt.startsWith$default(var7.getType(), "accel", false, 2, null)) {
                  var9 = "Accel";
               } else if (Intrinsics.areEqual(var7.getType(), "speed")) {
                  var9 = "Speed";
               } else {
                  String var4 = var7.getType();
                  boolean var3;
                  if (var4.length() > 0) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  var9 = var4;
                  if (var3) {
                     StringBuilder var10 = new StringBuilder();
                     char var2 = var4.charAt(0);
                     String var8 = String.valueOf(var2);
                     Intrinsics.checkNotNull(var8, "null cannot be cast to non-null type java.lang.String");
                     var8 = var8.toUpperCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var8, "toUpperCase(...)");
                     StringBuilder var11 = var10.append(var8);
                     var4 = var4.substring(1);
                     Intrinsics.checkNotNullExpressionValue(var4, "substring(...)");
                     var9 = var11.append(var4).toString();
                  }
               }

               Map var18 = var5;
               Object var15 = var18.get(var9);
               Object var12;
               if (var15 == null) {
                  var15 = new ArrayList();
                  var18.put(var9, var15);
                  var12 = var15;
               } else {
                  var12 = var15;
               }

               ((List)var12).add(var7);
            }
         }

         Set var13 = var5.entrySet();
         Intrinsics.checkNotNullExpressionValue(var13, "<get-entries>(...)");
         return CollectionsKt.joinToString$default(var13, "  ·  ", null, null, 0, null, new RtkTools$$ExternalSyntheticLambda0(), 30, null);
      }
   }

   public final float stepProgress(RtkStatus var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "k");
      Intrinsics.checkNotNullParameter(var2, "title");
      int var5 = var2.hashCode();
      float var4 = 0.5F;
      switch (var5) {
         case -1332793732:
            if (var2.equals("Mount alignment")) {
               if (!var1.getMountKept() && (!Intrinsics.areEqual(var1.getMountInit(), "done") || !Intrinsics.areEqual(var1.getMount(), "fine"))) {
                  if (Intrinsics.areEqual(var1.getMount(), "coarse")) {
                     return 0.75F;
                  } else {
                     if (!Intrinsics.areEqual(var1.getMount(), "learning heading")) {
                        float var17;
                        if (Intrinsics.areEqual(var1.getMount(), "learning roll/pitch")) {
                           var17 = 0.25F;
                        } else {
                           var17 = 0.0F;
                        }

                        return var17;
                     }

                     return var4;
                  }
               } else {
                  return 1.0F;
               }
            }
            break;
         case -169507621:
            if (var2.equals("Car speed")) {
               if (!var1.getSpeedFeedOn()) {
                  return 0.0F;
               } else if (Intrinsics.areEqual(var1.getSpeedInit(), "done")) {
                  return 1.0F;
               } else {
                  float var16;
                  if (Intrinsics.areEqual(var1.getSpeedInit(), "initializing")) {
                     var16 = var4;
                  } else {
                     var16 = 0.1F;
                  }

                  return var16;
               }
            }
            break;
         case -62835281:
            if (var2.equals("Motion sensors")) {
               String var6 = var1.getImuInit();
               if (Intrinsics.areEqual(var6, "done")) {
                  return 1.0F;
               } else {
                  float var13;
                  if (Intrinsics.areEqual(var6, "initializing")) {
                     var13 = var4;
                  } else {
                     var13 = 0.0F;
                  }

                  return var13;
               }
            }
            break;
         case 812449097:
            if (var2.equals("Position")) {
               var2 = var1.getInsInit();
               if (Intrinsics.areEqual(var2, "done")) {
                  return 1.0F;
               } else {
                  float var8;
                  if (Intrinsics.areEqual(var2, "initializing")) {
                     var8 = var4;
                     if (!Intrinsics.areEqual(var1.getMount(), "coarse")) {
                        if (Intrinsics.areEqual(var1.getMount(), "fine")) {
                           var8 = var4;
                        } else {
                           var8 = 0.15F;
                        }

                        return var8;
                     }
                  } else {
                     var8 = 0.0F;
                  }

                  return var8;
               }
            }
      }

      return 0.0F;
   }

   public final List<RtkTools.Step> steps(RtkStatus var1) {
      Intrinsics.checkNotNullParameter(var1, "k");
      RtkTools.State var9 = this.st(var1.getImuInit());
      RtkTools.State var5 = this.st(var1.getInsInit());
      RtkTools.State var8 = var5;
      if (var5 == RtkTools.State.OFF) {
         var8 = var5;
         if (var9 != RtkTools.State.DONE) {
            var8 = RtkTools.State.WAITING;
         }
      }

      boolean var2;
      if ((!Intrinsics.areEqual(var1.getMountInit(), "done") || !Intrinsics.areEqual(var1.getMount(), "fine")) && !var1.getMountKept()) {
         var2 = false;
      } else {
         var2 = true;
      }

      RtkTools.State var6;
      if (var2) {
         var6 = RtkTools.State.DONE;
      } else if (Intrinsics.areEqual(var1.getMountInit(), "off") && var8 != RtkTools.State.DONE) {
         var6 = RtkTools.State.WAITING;
      } else {
         label101: {
            var5 = this.st(var1.getMountInit());
            if (var5 != RtkTools.State.OFF) {
               var6 = var5;
               if (var5 != RtkTools.State.DONE) {
                  break label101;
               }
            }

            var6 = RtkTools.State.NOW;
         }
      }

      RtkTools.State var7;
      if (!var1.getSpeedFeedOn()) {
         var7 = RtkTools.State.OFF;
      } else if (Intrinsics.areEqual(var1.getSpeedInit(), "done")) {
         var7 = RtkTools.State.DONE;
      } else {
         var7 = RtkTools.State.NOW;
      }

      String var15;
      if (RtkTools.WhenMappings.$EnumSwitchMapping$0[var9.ordinal()] == 1) {
         var15 = "Gyro and accelerometer running";
      } else {
         var15 = "Engine on and the car standing still for about 3 minutes";
      }

      RtkTools.Step var21 = new RtkTools.Step("Motion sensors", var9, var15);
      String var16;
      if (var8 == RtkTools.State.DONE) {
         var16 = "Sensors joined to the GNSS fix";
      } else if (!var1.getHasFix()) {
         var16 = "Needs a GNSS fix first: open sky";
      } else if (var6 != RtkTools.State.DONE) {
         var16 = "Starts once the mount alignment is far enough";
      } else {
         var16 = "Drive a little under open sky";
      }

      RtkTools.Step var20 = new RtkTools.Step("Position", var8, var16);
      String var18;
      if (var1.getMountKept()) {
         StringCompanionObject var17 = StringCompanionObject.INSTANCE;
         var18 = String.format(
            Locale.US,
            "Saved in the receiver from an earlier drive (heading %.1f°) - not learned again after a power cut",
            Arrays.copyOf(new Object[]{var1.getMountKeptYaw()}, 1)
         );
         Intrinsics.checkNotNullExpressionValue(var18, "format(...)");
      } else if (var1.getMountKeeping()) {
         var18 = "Learned (fine) - saving it in the receiver";
      } else if (var6 == RtkTools.State.DONE) {
         var18 = "Learned (fine) - saved in the receiver after 2 minutes";
      } else if (Intrinsics.areEqual(var1.getMount(), "coarse")) {
         var18 = "Coarse so far: keep driving, with turns";
      } else if (var6 == RtkTools.State.WAITING) {
         var18 = "After the position step";
      } else {
         var18 = "Drive about 10 turns of 90° or more at 20+ mph; stops and straight stretches help too";
      }

      RtkTools.Step var19 = new RtkTools.Step("Mount alignment", var6, var18);
      String var10;
      if (var7 == RtkTools.State.OFF) {
         var10 = "Off: switch on \"Speed from the car\" below";
      } else if (var7 == RtkTools.State.DONE) {
         String var11;
         if (var1.getSpeedFeedHz() > 0.0) {
            long var3 = Math.round(var1.getSpeedFeedHz());
            var11 = ", " + var3 + " a second";
         } else {
            var11 = "";
         }

         var10 = "Calibrated" + var11;
      } else if (var1.getSpeedFeedHz() <= 0.0) {
         var10 = "Waiting for the car's speed (it comes with the road view's data)";
      } else {
         long var13 = Math.round(var1.getSpeedFeedHz());
         var10 = "Learning, " + var13 + " a second: just drive";
      }

      RtkTools.Step var12 = new RtkTools.Step("Car speed", var7, var10);
      return CollectionsKt.listOf(new RtkTools.Step[]{var21, var20, var19, var12});
   }

   public final String summary(RtkStatus var1) {
      Intrinsics.checkNotNullParameter(var1, "k");
      if (!var1.getConnected()) {
         return "Receiver unplugged";
      } else {
         boolean var2;
         if (var1.getFusion().length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            return "No sensor report yet";
         } else {
            Iterator var5 = this.steps(var1).iterator();

            Object var3;
            do {
               if (!var5.hasNext()) {
                  var3 = null;
                  break;
               }

               var3 = var5.next();
               RtkTools.Step var4 = (RtkTools.Step)var3;
               if (var4.getState() != RtkTools.State.NOW && var4.getState() != RtkTools.State.WAITING) {
                  var2 = false;
               } else {
                  var2 = true;
               }
            } while (!var2);

            var3 = (RtkTools.Step)var3;
            String var7;
            if (Intrinsics.areEqual(var1.getFusion(), "on") && var3 == null) {
               var7 = "Sensor fusion on: keeps the position in tunnels and garages";
            } else if (Intrinsics.areEqual(var1.getFusion(), "on")) {
               Intrinsics.checkNotNull(var3);
               String var6 = ((RtkTools.Step)var3).getTitle().toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var6, "toLowerCase(...)");
               var7 = "Sensor fusion on. Next: " + var6;
            } else if (Intrinsics.areEqual(var1.getFusion(), "disabled")) {
               var7 = "Sensor fusion is switched off on the receiver: Factory reset turns it back on";
            } else if (Intrinsics.areEqual(var1.getFusion(), "suspended")) {
               var7 = "Sensor fusion paused by the receiver (bad sensor data)";
            } else if (var3 != null) {
               String var8 = ((RtkTools.Step)var3).getTitle().toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var8, "toLowerCase(...)");
               var7 = "Starting: " + var8;
            } else {
               var7 = "Starting";
            }

            return var7;
         }
      }
   }

   public final List<String> timeline(RtkStatus var1, long var2, TimeZone var4) {
      Intrinsics.checkNotNullParameter(var1, "k");
      Intrinsics.checkNotNullParameter(var4, "zone");
      RtkCalibration var13 = var1.getCalibration();
      if (var13 == null) {
         return CollectionsKt.emptyList();
      } else {
         SimpleDateFormat var8 = new SimpleDateFormat("HH:mm:ss", Locale.US);
         var8.setTimeZone(var4);
         Iterable var19 = CollectionsKt.asReversed(var13.getEvents());
         var var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var19, 10));

         for (RtkCalibration.Event var12 : var19) {
            String var11 = var8.format(new Date(var2 - (long)(var12.getAgoS() * 1000)));
            String var5 = KEY_NAMES.get(var12.getKey());
            String var14 = var5;
            if (var5 == null) {
               var14 = var12.getKey();
            }

            String var17;
            if (Intrinsics.areEqual(var12.getKey(), "tool") && Intrinsics.areEqual(var12.getTo(), "speed_on")) {
               var17 = "Speed from the car switched on";
            } else if (Intrinsics.areEqual(var12.getKey(), "tool") && Intrinsics.areEqual(var12.getTo(), "speed_off")) {
               var17 = "Speed from the car switched off";
            } else if (Intrinsics.areEqual(var12.getKey(), "tool")) {
               RtkTools var6 = INSTANCE;
               var5 = var12.getTo();
               String var15 = var5;
               if (var5 == null) {
                  var15 = "";
               }

               String var16 = var6.label(var15);
               var17 = var16 + " used";
            } else {
               String var7 = var12.getFrom();
               String var22 = "?";
               var5 = var7;
               if (var7 == null) {
                  var5 = "?";
               }

               var7 = var12.getTo();
               if (var7 != null) {
                  var22 = var7;
               }

               var17 = var14 + ": " + var5 + " → " + var22;
            }

            String var18 = var11 + "  " + var17;
            var9.add(var18);
         }

         return var9;
      }
   }

   public static final class Need {
      private final boolean met;
      private final float progress;
      private final String title;
      private final String words;

      public Need(String var1, String var2, boolean var3, float var4) {
         Intrinsics.checkNotNullParameter(var1, "title");
         Intrinsics.checkNotNullParameter(var2, "words");
         super();
         this.title = var1;
         this.words = var2;
         this.met = var3;
         this.progress = var4;
      }

      // $VF: synthetic method
      public static RtkTools.Need copy$default(RtkTools.Need var0, String var1, String var2, boolean var3, float var4, int var5, Object var6) {
         if ((var5 & 1) != 0) {
            var1 = var0.title;
         }

         if ((var5 & 2) != 0) {
            var2 = var0.words;
         }

         if ((var5 & 4) != 0) {
            var3 = var0.met;
         }

         if ((var5 & 8) != 0) {
            var4 = var0.progress;
         }

         return var0.copy(var1, var2, var3, var4);
      }

      public final String component1() {
         return this.title;
      }

      public final String component2() {
         return this.words;
      }

      public final boolean component3() {
         return this.met;
      }

      public final float component4() {
         return this.progress;
      }

      public final RtkTools.Need copy(String var1, String var2, boolean var3, float var4) {
         Intrinsics.checkNotNullParameter(var1, "title");
         Intrinsics.checkNotNullParameter(var2, "words");
         return new RtkTools.Need(var1, var2, var3, var4);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof RtkTools.Need)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.title, var1.title)) {
               return false;
            } else if (!Intrinsics.areEqual(this.words, var1.words)) {
               return false;
            } else {
               return this.met != var1.met ? false : Float.compare(this.progress, var1.progress) == 0;
            }
         }
      }

      public final boolean getMet() {
         return this.met;
      }

      public final float getProgress() {
         return this.progress;
      }

      public final String getTitle() {
         return this.title;
      }

      public final String getWords() {
         return this.words;
      }

      @Override
      public int hashCode() {
         return ((this.title.hashCode() * 31 + this.words.hashCode()) * 31 + Boolean.hashCode(this.met)) * 31 + Float.hashCode(this.progress);
      }

      @Override
      public String toString() {
         String var3 = this.title;
         String var4 = this.words;
         boolean var2 = this.met;
         float var1 = this.progress;
         return "Need(title=" + var3 + ", words=" + var4 + ", met=" + var2 + ", progress=" + var1 + ")";
      }
   }

   public static enum State {
      DONE,
      NOW,
      OFF,
      WAITING;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(RtkTools.State.$VALUES);
      private static final RtkTools.State[] $VALUES = $values();

      // $VF: synthetic method
      private static final RtkTools.State[] $values() {
         return new RtkTools.State[]{DONE, NOW, WAITING, OFF};
      }

      public static EnumEntries<RtkTools.State> getEntries() {
         return $ENTRIES;
      }
   }

   public static final class Step {
      private final RtkTools.State state;
      private final String title;
      private final String words;

      public Step(String var1, RtkTools.State var2, String var3) {
         Intrinsics.checkNotNullParameter(var1, "title");
         Intrinsics.checkNotNullParameter(var2, "state");
         Intrinsics.checkNotNullParameter(var3, "words");
         super();
         this.title = var1;
         this.state = var2;
         this.words = var3;
      }

      // $VF: synthetic method
      public static RtkTools.Step copy$default(RtkTools.Step var0, String var1, RtkTools.State var2, String var3, int var4, Object var5) {
         if ((var4 & 1) != 0) {
            var1 = var0.title;
         }

         if ((var4 & 2) != 0) {
            var2 = var0.state;
         }

         if ((var4 & 4) != 0) {
            var3 = var0.words;
         }

         return var0.copy(var1, var2, var3);
      }

      public final String component1() {
         return this.title;
      }

      public final RtkTools.State component2() {
         return this.state;
      }

      public final String component3() {
         return this.words;
      }

      public final RtkTools.Step copy(String var1, RtkTools.State var2, String var3) {
         Intrinsics.checkNotNullParameter(var1, "title");
         Intrinsics.checkNotNullParameter(var2, "state");
         Intrinsics.checkNotNullParameter(var3, "words");
         return new RtkTools.Step(var1, var2, var3);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof RtkTools.Step)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.title, var1.title)) {
               return false;
            } else {
               return this.state != var1.state ? false : Intrinsics.areEqual(this.words, var1.words);
            }
         }
      }

      public final RtkTools.State getState() {
         return this.state;
      }

      public final String getTitle() {
         return this.title;
      }

      public final String getWords() {
         return this.words;
      }

      @Override
      public int hashCode() {
         return (this.title.hashCode() * 31 + this.state.hashCode()) * 31 + this.words.hashCode();
      }

      @Override
      public String toString() {
         String var3 = this.title;
         RtkTools.State var1 = this.state;
         String var2 = this.words;
         return "Step(title=" + var3 + ", state=" + var1 + ", words=" + var2 + ")";
      }
   }

   public static final class Tool {
      private final boolean danger;
      private final String label;
      private final String op;
      private final boolean stoppedOnly;
      private final String what;

      public Tool(String var1, String var2, String var3, boolean var4, boolean var5) {
         Intrinsics.checkNotNullParameter(var1, "op");
         Intrinsics.checkNotNullParameter(var2, "label");
         Intrinsics.checkNotNullParameter(var3, "what");
         super();
         this.op = var1;
         this.label = var2;
         this.what = var3;
         this.stoppedOnly = var4;
         this.danger = var5;
      }

      // $VF: synthetic method
      public Tool(String var1, String var2, String var3, boolean var4, boolean var5, int var6, DefaultConstructorMarker var7) {
         if ((var6 & 8) != 0) {
            var4 = false;
         }

         if ((var6 & 16) != 0) {
            var5 = false;
         }

         this(var1, var2, var3, var4, var5);
      }

      // $VF: synthetic method
      public static RtkTools.Tool copy$default(RtkTools.Tool var0, String var1, String var2, String var3, boolean var4, boolean var5, int var6, Object var7) {
         if ((var6 & 1) != 0) {
            var1 = var0.op;
         }

         if ((var6 & 2) != 0) {
            var2 = var0.label;
         }

         if ((var6 & 4) != 0) {
            var3 = var0.what;
         }

         if ((var6 & 8) != 0) {
            var4 = var0.stoppedOnly;
         }

         if ((var6 & 16) != 0) {
            var5 = var0.danger;
         }

         return var0.copy(var1, var2, var3, var4, var5);
      }

      public final String component1() {
         return this.op;
      }

      public final String component2() {
         return this.label;
      }

      public final String component3() {
         return this.what;
      }

      public final boolean component4() {
         return this.stoppedOnly;
      }

      public final boolean component5() {
         return this.danger;
      }

      public final RtkTools.Tool copy(String var1, String var2, String var3, boolean var4, boolean var5) {
         Intrinsics.checkNotNullParameter(var1, "op");
         Intrinsics.checkNotNullParameter(var2, "label");
         Intrinsics.checkNotNullParameter(var3, "what");
         return new RtkTools.Tool(var1, var2, var3, var4, var5);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof RtkTools.Tool)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.op, var1.op)) {
               return false;
            } else if (!Intrinsics.areEqual(this.label, var1.label)) {
               return false;
            } else if (!Intrinsics.areEqual(this.what, var1.what)) {
               return false;
            } else {
               return this.stoppedOnly != var1.stoppedOnly ? false : this.danger == var1.danger;
            }
         }
      }

      public final boolean getDanger() {
         return this.danger;
      }

      public final String getLabel() {
         return this.label;
      }

      public final String getOp() {
         return this.op;
      }

      public final boolean getStoppedOnly() {
         return this.stoppedOnly;
      }

      public final String getWhat() {
         return this.what;
      }

      @Override
      public int hashCode() {
         return (((this.op.hashCode() * 31 + this.label.hashCode()) * 31 + this.what.hashCode()) * 31 + Boolean.hashCode(this.stoppedOnly)) * 31
            + Boolean.hashCode(this.danger);
      }

      @Override
      public String toString() {
         String var3 = this.op;
         String var4 = this.label;
         String var5 = this.what;
         boolean var1 = this.stoppedOnly;
         boolean var2 = this.danger;
         return "Tool(op=" + var3 + ", label=" + var4 + ", what=" + var5 + ", stoppedOnly=" + var1 + ", danger=" + var2 + ")";
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var1 = new int[RtkTools.State.values().length];

         try {
            var1[RtkTools.State.DONE.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var1;
      }
   }
}
