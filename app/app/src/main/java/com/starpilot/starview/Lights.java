package com.starpilot.starview;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONObject;

public final class Lights {
   private volatile boolean actuated;
   private boolean dirty;
   private final HashMap<String, Integer> lastLamp;
   private Triple<String, Double, Long> redAhead;
   private final HashMap<String, ArrayList<Integer>> redLens;
   private final HashMap<String, Long> redSince;
   private final HashMap<String, ArrayList<Long>> seen = new HashMap<>();

   public Lights() {
      this.lastLamp = new HashMap<>();
      this.redSince = new HashMap<>();
      this.redLens = new HashMap<>();
   }

   public final String chip(Ideas.Here var1, long var2) {
      Triple var5 = this.redAhead;
      if (var5 == null) {
         return null;
      } else if (var2 - ((Number)var5.getThird()).longValue() > 3000L) {
         return null;
      } else {
         if (this.actuated) {
            Pair var6 = this.nextGreenS(var2);
            Integer var7;
            if (var6 != null) {
               var7 = (Integer)var6.getFirst();
            } else {
               var7 = null;
            }

            if (var7 == null) {
               ArrayList var8 = this.redLens.get(var5.getFirst());
               if (var8 != null && var8.size() >= 2) {
                  int var14 = ((Number)CollectionsKt.sorted(var8).get(var8.size() / 2)).intValue();
                  Long var11 = this.redSince.get(var5.getFirst());
                  StringBuilder var12;
                  if (var11 != null) {
                     var14 = Math.max(0, var14 - (int)((var2 - var11) / 1000));
                     var12 = new StringBuilder().append("Green in about ").append(var14);
                  } else {
                     var12 = new StringBuilder().append("Red here usually lasts about ").append(var14);
                  }

                  return var12.append(" s").toString();
               }
            }
         }

         Pair var9 = this.nextGreenS(var2);
         if (var9 == null) {
            return null;
         } else {
            Integer var16 = (Integer)var9.component1();
            int var4 = ((Number)var9.component2()).intValue();
            StringBuilder var10;
            if (var16 != null) {
               var10 = new StringBuilder().append("Green in ").append(var16).append(" s");
            } else {
               var4 = Math.min(var4, 2);
               var10 = new StringBuilder().append("Learning this light (").append(var4).append(" of 3 greens seen)");
            }

            return var10.toString();
         }
      }
   }

   public final Ideas.Part coast(Telemetry var1, Ideas.Here var2, long var3) {
      Intrinsics.checkNotNullParameter(var1, "t");
      Triple var8 = this.redAhead;
      Object var7 = null;
      if (var8 == null) {
         return null;
      } else if (var3 - ((Number)var8.getThird()).longValue() <= 1500L && var2 != null) {
         double var5 = ((Number)var8.getSecond()).doubleValue();
         if (!(var5 < 25.0) && !(var5 > 160.0)) {
            if (var1.getLeadOne().getStatus() && var1.getLeadOne().getDRel() < var5 - 10.0) {
               return null;
            } else {
               Pair var9 = this.nextGreenS(var3);
               Integer var10;
               if (var9 != null) {
                  var10 = (Integer)var9.getFirst();
               } else {
                  var10 = null;
               }

               if (var10 != null && var2.getV() > 1.0 && var10.intValue() < var5 / var2.getV()) {
                  return null;
               } else {
                  var5 = Ideas.INSTANCE.approach(4.4704, var5 - 20.0, 0.5);
                  Ideas.Part var11 = (Ideas.Part)var7;
                  if (var5 < var2.getV() - 0.5) {
                     var11 = new Ideas.Part("hybrid", var5, 0.0, 0.0, "Red ahead - coasting", 18, 12, null);
                  }

                  return var11;
               }
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public final Pair<Integer, Long> fit(List<Long> var1, long var2) {
      Intrinsics.checkNotNullParameter(var1, "times");
      Iterable var17 = var1;
      var var21 = new ArrayList();

      for (Object var18 : var17) {
         boolean var14;
         if (var2 - ((Number)var18).longValue() < 1209600000L) {
            var14 = true;
         } else {
            var14 = false;
         }

         if (var14) {
            var21.add(var18);
         }
      }

      var1 = var21;
      if (var1.size() < 3) {
         return null;
      } else {
         ArrayList var43 = new ArrayList();

         for (int var34 = 45; var34 < 151; var34++) {
            double var12 = var34 * 1000.0;
            double var6 = 0.0;
            double var4 = 0.0;
            Iterator var39 = var1.iterator();

            while (var39.hasNext()) {
               double var8 = ((Number)var39.next()).longValue() % var12 / var12 * (Math.PI * 2);
               var6 += Math.cos(var8);
               var4 += Math.sin(var8);
            }

            double var10 = Math.atan2(var4, var6) / (Math.PI * 2) * var12;
            double var32 = var10;
            if (var10 < 0.0) {
               var32 = var10 + var12;
            }

            var43.add(new Triple<>(var34, Math.hypot(var6, var4) / var1.size(), (long)var32));
         }

         Iterator var19 = var43.iterator();
         Object var23;
         if (!var19.hasNext()) {
            var23 = null;
         } else {
            var23 = (Pair)var19.next();
            if (var19.hasNext()) {
               double var29 = ((Number)((Triple)var23).getSecond()).doubleValue();

               while (true) {
                  var17 = (Iterable)var19.next();
                  double var33 = ((Number)((Triple)var17).getSecond()).doubleValue();
                  double var31 = var29;
                  if (Double.compare(var29, var33) < 0) {
                     var23 = var17;
                     var31 = var33;
                  }

                  if (!var19.hasNext()) {
                     break;
                  }

                  var29 = var31;
               }
            }
         }

         var23 = (Triple)var23;
         if (var23 == null) {
            return null;
         } else {
            double var30 = ((Number)var23.getSecond()).doubleValue();
            Iterable var44 = var43;
            var var41 = new ArrayList();

            for (Object var47 : var44) {
               Triple var20 = (Triple)var47;
               boolean var35;
               if (((Number)var20.getFirst()).intValue() % ((Number)var23.getFirst()).intValue() == 0
                  && ((Number)var20.getSecond()).doubleValue() >= var30 - 0.02) {
                  var35 = true;
               } else {
                  var35 = false;
               }

               if (var35) {
                  var41.add(var47);
               }
            }

            var23 = var41;
            var19 = ((Iterable)var23).iterator();
            if (!var19.hasNext()) {
               var23 = null;
            } else {
               var23 = var19.next();
               if (var19.hasNext()) {
                  int var36 = ((Number)((Triple)var23).getFirst()).intValue();
                  var17 = (Iterable)var23;

                  do {
                     Object var46 = var19.next();
                     int var16 = ((Number)((Triple)var46).getFirst()).intValue();
                     var23 = var17;
                     int var15 = var36;
                     if (var36 < var16) {
                        var23 = var46;
                        var15 = var16;
                     }

                     var17 = (Iterable)var23;
                     var36 = var15;
                  } while (var19.hasNext());
               }
            }

            var23 = (Triple)var23;
            if (var23 != null) {
               var23 = TuplesKt.to(var23.getFirst(), var23.getThird());
            } else {
               var23 = null;
            }

            if (var23 != null) {
               boolean var37;
               if (var30 > 0.93) {
                  var37 = true;
               } else {
                  var37 = false;
               }

               if (var37) {
                  return var23;
               }
            }

            return null;
         }
      }
   }

   public final boolean getActuated() {
      return this.actuated;
   }

   public final int known() {
      return this.seen.size();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final Unit load(File var1) {
      Intrinsics.checkNotNullParameter(var1, "f");

      boolean var4;
      try {
         var4 = var1.isFile();
      } catch (Exception var15) {
         return Unit.INSTANCE;
      }

      Map var6 = null;
      File var5;
      if (var4) {
         var5 = var1;
      } else {
         var5 = null;
      }

      Unit var16 = var6;
      if (var5 != null) {
         try {
            var17 = new JSONObject(FilesKt.readText$default(var5, null, 1, null));
            var18 = var17.keys();
            Intrinsics.checkNotNullExpressionValue(var18, "keys(...)");
         } catch (Exception var13) {
            Unit var22 = Unit.INSTANCE;
            return var22;
         }

         while (true) {
            int var3;
            String var7;
            ArrayList var8;
            JSONArray var9;
            try {
               if (!var18.hasNext()) {
                  break;
               }

               var7 = (String)var18.next();
               var9 = var17.getJSONArray(var7);
               var6 = this.seen;
               var3 = var9.length();
               var8 = new ArrayList(var3);
            } catch (Exception var14) {
               Unit var23 = Unit.INSTANCE;
               return var23;
            }

            for (int var2 = 0; var2 < var3; var2++) {
               try {
                  var8.add(var9.getLong(var2));
               } catch (Exception var12) {
                  Unit var25 = Unit.INSTANCE;
                  return var25;
               }
            }

            try {
               var var20 = var8;
               ArrayList var21 = new ArrayList(var20);
               var6.put(var7, var21);
            } catch (Exception var11) {
               Unit var24 = Unit.INSTANCE;
               return var24;
            }
         }

         try {
            var16 = Unit.INSTANCE;
         } catch (Exception var10) {
            var16 = Unit.INSTANCE;
         }
      }

      return var16;
   }

   public final Pair<Integer, Integer> nextGreenS(long var1) {
      Triple var10 = this.redAhead;
      if (var10 == null) {
         return null;
      } else {
         ArrayList var13 = this.seen.get(var10.getFirst());
         if (var13 == null) {
            return TuplesKt.to(null, 0);
         } else {
            Pair var11 = this.fit(var13, var1);
            if (var11 == null) {
               return TuplesKt.to(null, var13.size());
            } else {
               int var3 = ((Number)var11.component1()).intValue();
               long var4 = ((Number)var11.component2()).longValue();
               long var8 = var3 * 1000L;
               long var6 = var1 - var1 % var8 + var4;
               var4 = var6;
               if (var6 < var1) {
                  var4 = var6 + var8;
               }

               return TuplesKt.to((int)((var4 - var1) / 1000), var13.size());
            }
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final void save(File var1) {
      Intrinsics.checkNotNullParameter(var1, "f");
      if (this.dirty) {
         JSONObject var3;
         Iterator var4;
         try {
            var3 = new JSONObject();
            var4 = this.seen.entrySet().iterator();
         } catch (Exception var8) {
            return;
         }

         while (true) {
            try {
               if (!var4.hasNext()) {
                  break;
               }

               Entry var5 = (Entry)var4.next();
               String var2 = (String)var5.getKey();
               ArrayList var11 = (ArrayList)var5.getValue();
               JSONArray var6 = new JSONArray(var11);
               var3.put(var2, var6);
            } catch (Exception var9) {
               return;
            }
         }

         try {
            String var10 = var3.toString();
            Intrinsics.checkNotNullExpressionValue(var10, "toString(...)");
            FilesKt.writeText$default(var1, var10, null, 2, null);
            this.dirty = false;
         } catch (Exception var7) {
         }
      }
   }

   public final void see(Ideas.Here var1, List<Detector.Obj> var2, long var3) {
      Intrinsics.checkNotNullParameter(var2, "objs");
      if (var1 != null && !Double.isNaN(var1.getYaw())) {
         Pair var30 = null;

         for (Detector.Obj var34 : var2) {
            if (var34.getCls() == 9 && var34.getLamp() >= 0 && !var34.getWideCam()) {
               float var23 = var34.getX();
               boolean var24;
               if (5.0F <= var23 && var23 <= 130.0F) {
                  var24 = 1;
               } else {
                  var24 = 0;
               }

               if (var24 && !(Math.abs(var34.getY()) > 12.0F)) {
                  double var9 = var34.getX();
                  double var7 = Math.cos(var1.getYaw());
                  double var11 = var34.getY();
                  double var13 = Math.sin(var1.getYaw());
                  double var19 = var34.getX();
                  double var17 = Math.sin(var1.getYaw());
                  double var21 = var34.getY();
                  double var15 = Math.cos(var1.getYaw());
                  double var5 = var1.getLat();
                  var15 = (var19 * var17 - var21 * var15) / 111195.0;
                  var17 = var1.getLon();
                  var7 = (var9 * var7 + var11 * var13) / (Math.cos(Math.toRadians(var1.getLat())) * 111320.0);
                  long var26 = Math.round((var5 + var15) / 3.0E-4);
                  long var28 = Math.round((var17 + var7) / 4.0E-4);
                  var24 = (int)((var1.getHdgDeg() + 45) / 90);
                  String var32 = var26 + "_" + var28 + "_" + var24 % 4;
                  Integer var33 = this.lastLamp.put(var32, var34.getLamp());
                  if (var33 != null && var33 == 0 && var34.getLamp() == 2) {
                     Map var35 = this.seen;
                     Object var31 = var35.get(var32);
                     if (var31 == null) {
                        var31 = new ArrayList();
                        var35.put(var32, var31);
                     }

                     ((ArrayList)var31).add(var3);
                     this.dirty = true;
                     var31 = this.redSince.remove(var32);
                     if (var31 != null) {
                        int var25 = (int)((var3 - ((Number)var31).longValue()) / 1000);
                        boolean var42;
                        if (8 <= var25 && var25 < 241) {
                           var42 = true;
                        } else {
                           var42 = false;
                        }

                        if (var42) {
                           var35 = this.redLens;
                           var31 = var35.get(var32);
                           if (var31 == null) {
                              var31 = new ArrayList();
                              var35.put(var32, var31);
                           }

                           ((ArrayList)var31).add(var25);
                        }
                     }
                  }

                  if (var33 != null && var33 != 0 && var34.getLamp() == 0) {
                     this.redSince.put(var32, var3);
                  }

                  if (var34.getLamp() == 0 && (var30 == null || var34.getX() < ((Number)var30.getSecond()).doubleValue())) {
                     var30 = TuplesKt.to(var32, (double)var34.getX());
                  }
               }
            }
         }

         Triple var36;
         if (var30 != null) {
            var36 = new Triple<>(var30.getFirst(), var30.getSecond(), var3);
         } else {
            label67: {
               var36 = this.redAhead;
               if (var36 != null) {
                  boolean var43;
                  if (var3 - ((Number)var36.getThird()).longValue() < 3000L) {
                     var43 = true;
                  } else {
                     var43 = false;
                  }

                  if (var43) {
                     break label67;
                  }
               }

               var36 = null;
            }
         }

         this.redAhead = var36;
      }
   }

   public final void setActuated(boolean var1) {
      this.actuated = var1;
   }
}
