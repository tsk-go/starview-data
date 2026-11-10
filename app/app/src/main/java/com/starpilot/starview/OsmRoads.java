package com.starpilot.starview;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.DoubleRef;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

public final class OsmRoads {
   private static final Map<String, String> ABBR;
   private static final Set<String> DIRS = SetsKt.setOf(new String[]{"east", "west", "north", "south", "eastbound", "westbound", "northbound", "southbound"});
   public static final OsmRoads INSTANCE = new OsmRoads();
   private static final Set<String> KINDS = SetsKt.setOf(
      new String[]{
         "motorway",
         "trunk",
         "primary",
         "secondary",
         "tertiary",
         "unclassified",
         "residential",
         "living_street",
         "service",
         "motorway_link",
         "trunk_link",
         "primary_link",
         "secondary_link",
         "tertiary_link"
      }
   );
   private static final double KY = 110540.0;
   private static final String TAG = "StarView/Osm";
   public static final double TILE = 0.01;
   private static final List<String> URLS = CollectionsKt.listOf(
      new String[]{
         "https://overpass-api.de/api/interpreter", "https://overpass.private.coffee/api/interpreter", "https://overpass.kumi.systems/api/interpreter"
      }
   );
   private static final ConcurrentHashMap<String, Integer> boxState = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<String, List<OsmRoads.Way>> boxWays = new ConcurrentHashMap<>();
   private static File dir;
   private static volatile boolean enabled = true;
   private static final ConcurrentHashMap<String, Long> failedAt = new ConcurrentHashMap<>();
   private static final ConcurrentLinkedQueue<OsmRoads.Fix> fixes = new ConcurrentLinkedQueue<>();
   private static volatile String gpsSrc = "";
   private static final Lazy http$delegate = LazyKt.lazy(new OsmRoads$$ExternalSyntheticLambda2());
   private static double kx;
   private static long lastEnsureMs;
   private static volatile OsmRoads.Fix lastFix;
   private static volatile double lat0 = Double.NaN;
   private static final ExecutorService loader = Executors.newSingleThreadExecutor(new OsmRoads$$ExternalSyntheticLambda3());
   private static volatile double lon0;
   private static volatile String matchStatus = "";
   private static volatile long rtkFixNs;
   private static volatile String tileStatus = "no GPS yet";
   private static final ConcurrentHashMap<String, Integer> tiles = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<Long, OsmRoads.Way> ways = new ConcurrentHashMap<>();

   // $VF: synthetic method
   public static Thread $r8$lambda$QanfgN_bDj7hqf2R9NQ8_CMOhxk(Runnable var0) {
      return loader$lambda$2(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$SO7CXvWftdNrg_fL9kvBiztGxg8/* $VF was: $r8$lambda$SO7CXvWftdNrg_fL9kvBiztGxg8*/(File var0, String var1) {
      ensureBoxes$lambda$6(var0, var1);
   }

   // $VF: synthetic method
   public static OkHttpClient $r8$lambda$ebUeA_GO_XjzHA_IX_V0rASedjo() {
      return http_delegate$lambda$0();
   }

   // $VF: synthetic method
   public static void $r8$lambda$u0Ha0OamhbaiyavHBafYFfwsqlk(int var0, int var1, int var2, int var3) {
      ensureTiles$lambda$7(var0, var1, var2, var3);
   }

   static {
      Pair var13 = TuplesKt.to("st", "street");
      Pair var0 = TuplesKt.to("ave", "avenue");
      Pair var6 = TuplesKt.to("av", "avenue");
      Pair var11 = TuplesKt.to("rd", "road");
      Pair var3 = TuplesKt.to("blvd", "boulevard");
      Pair var1 = TuplesKt.to("dr", "drive");
      Pair var18 = TuplesKt.to("ln", "lane");
      Pair var19 = TuplesKt.to("pl", "place");
      Pair var4 = TuplesKt.to("ct", "court");
      Pair var15 = TuplesKt.to("hwy", "highway");
      Pair var9 = TuplesKt.to("pkwy", "parkway");
      Pair var7 = TuplesKt.to("expy", "expressway");
      Pair var14 = TuplesKt.to("tpke", "turnpike");
      Pair var12 = TuplesKt.to("e", "east");
      Pair var8 = TuplesKt.to("w", "west");
      Pair var10 = TuplesKt.to("n", "north");
      Pair var16 = TuplesKt.to("s", "south");
      Pair var2 = TuplesKt.to("ter", "terrace");
      Pair var5 = TuplesKt.to("sq", "square");
      Pair var17 = TuplesKt.to("rte", "route");
      ABBR = MapsKt.mapOf(
         new Pair[]{
            var13,
            var0,
            var6,
            var11,
            var3,
            var1,
            var18,
            var19,
            var4,
            var15,
            var9,
            var7,
            var14,
            var12,
            var8,
            var10,
            var16,
            var2,
            var5,
            var17,
            TuplesKt.to("rt", "route")
         }
      );
   }

   private OsmRoads() {
   }

   private static final void carFrame$flush(
      ArrayList<Double> var0, ArrayList<Pair<Double, OsmRoads.Piece>> var1, DoubleRef var2, OsmRoads.Way var3, ArrayList<Double> var4
   ) {
      if (var0.size() >= 2) {
         var1.add(new Pair<>(var2.element, new OsmRoads.Piece(var3, CollectionsKt.toDoubleArray(var0), CollectionsKt.toDoubleArray(var4))));
      }

      var0.clear();
      var4.clear();
      var2.element = Double.MAX_VALUE;
   }

   private static final void crossStreetsAhead$add(double var0, ArrayList<Pair<Double, String>> var2, double var3, String var5) {
      if (var3 > 2.0 && var3 <= var0) {
         var var9 = var2;
         boolean var8 = var9 instanceof Collection;
         boolean var7 = true;
         boolean var10;
         if (var8 && var9.isEmpty()) {
            var10 = var7;
         } else {
            label40: {
               Iterator var11 = var9.iterator();

               while (var11.hasNext()) {
                  if (Math.abs(((Number)((Pair)var11.next()).getFirst()).doubleValue() - var3) < 10.0) {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  if (var10) {
                     var10 = false;
                     break label40;
                  }
               }

               var10 = var7;
            }
         }

         if (var10) {
            var2.add(TuplesKt.to(var3, var5));
         }
      }
   }

   private final void ensureBoxes(double var1, double var3) {
      long var13 = 4568404364472794339L;
      double var9 = 300.0 / kx;

      for (int var11 = 0; var11 < 3; var11++) {
         double var5 = new double[]{var1 - 0.002713949701465533, var1, var1 + 0.002713949701465533}[var11];

         for (int var12 = 0; var12 < 3; var12++) {
            double var7 = new double[]{var3 - var9, var3, var3 + var9}[var12];
            Pair var16 = MapdMaps.INSTANCE.boxFileFor(var5, var7);
            boolean var15 = (Boolean)var16.component1();
            File var24 = (File)var16.component2();
            if (var15 && var24 != null) {
               String var17 = var24.getName();
               if (!boxState.containsKey(var17)) {
                  boxState.put(var17, 0);
                  loader.execute(new OsmRoads$$ExternalSyntheticLambda1(var24, var17));
               }
            }
         }
      }

      Set var25 = boxWays.keySet();
      Intrinsics.checkNotNullExpressionValue(var25, "<get-keys>(...)");

      for (String var18 : CollectionsKt.toList(var25)) {
         List var26 = StringsKt.split$default(var18, new char[]{'_'}, false, 0, 6, null);
         var9 = Double.parseDouble((String)var26.get(0));
         double var21 = Double.parseDouble((String)var26.get(2));
         double var19 = 2;
         var21 = (var9 + var21) / var19;
         var19 = (Double.parseDouble((String)var26.get(1)) + Double.parseDouble((String)var26.get(3))) / var19;
         if (Math.abs(var21 - var1) > 0.5 || Math.abs(var19 - var3) > 0.5) {
            boxWays.remove(var18);
            boxState.remove(var18);
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final void ensureBoxes$lambda$6(File var0, String var1) {
      label142: {
         label141: {
            label145: {
               ArrayList var10;
               try {
                  var31 = MapdMaps.INSTANCE.readBox(FilesKt.readBytes(var0));
                  ArrayList var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var31, 10));
                  var10 = var9;
               } catch (Exception var29) {
                  Log.e("StarView/Osm", "box " + var1, var29);
                  var30 = var29.getMessage();
                  if (var30 != null) {
                     break label141;
                  }
                  break label145;
               }

               boolean var8 = false;

               Iterator var11;
               try {
                  var11 = var31.iterator();
               } catch (Exception var25) {
                  Log.e("StarView/Osm", "box " + var1, var25);
                  var30 = var25.getMessage();
                  if (var30 != null) {
                     break label141;
                  }
                  break label145;
               }

               while (true) {
                  double[] var12;
                  MapdMaps.W var13;
                  double[] var14;
                  try {
                     if (!var11.hasNext()) {
                        break;
                     }

                     var13 = (MapdMaps.W)var11.next();
                     var14 = new double[var13.getLat().length];
                     var12 = new double[var13.getLat().length];
                  } catch (Exception var26) {
                     Log.e("StarView/Osm", "box " + var1, var26);
                     var30 = var26.getMessage();
                     if (var30 != null) {
                        break label141;
                     }
                     break label145;
                  }

                  int var6 = 0;

                  int var7;
                  try {
                     var7 = var13.getLat().length;
                  } catch (Exception var24) {
                     Log.e("StarView/Osm", "box " + var1, var24);
                     var30 = var24.getMessage();
                     if (var30 != null) {
                        break label141;
                     }
                     break label145;
                  }

                  while (var6 < var7) {
                     double var2;
                     double var4;
                     try {
                        Pair var39 = INSTANCE.toLocal(var13.getLat()[var6], var13.getLon()[var6]);
                        var2 = ((Number)var39.component1()).doubleValue();
                        var4 = ((Number)var39.component2()).doubleValue();
                     } catch (Exception var23) {
                        Log.e("StarView/Osm", "box " + var1, var23);
                        var30 = var23.getMessage();
                        if (var30 != null) {
                           break label141;
                        }
                        break label145;
                     }

                     var14[var6] = var2;
                     var12[var6] = var4;
                     var6++;
                  }

                  String var40;
                  label126: {
                     label125: {
                        try {
                           if (Intrinsics.areEqual(var13.getKind(), "unknown")) {
                              break label125;
                           }
                        } catch (Exception var28) {
                           Log.e("StarView/Osm", "box " + var1, var28);
                           var30 = var28.getMessage();
                           if (var30 != null) {
                              break label141;
                           }
                           break label145;
                        }

                        try {
                           var40 = var13.getKind();
                           break label126;
                        } catch (Exception var22) {
                           Log.e("StarView/Osm", "box " + var1, var22);
                           var30 = var22.getMessage();
                           if (var30 != null) {
                              break label141;
                           }
                           break label145;
                        }
                     }

                     var40 = "residential";
                  }

                  label119: {
                     label118: {
                        try {
                           if (var13.getOneway()) {
                              break label118;
                           }
                        } catch (Exception var27) {
                           Log.e("StarView/Osm", "box " + var1, var27);
                           var30 = var27.getMessage();
                           if (var30 != null) {
                              break label141;
                           }
                           break label145;
                        }

                        var37 = 0;
                        break label119;
                     }

                     var37 = 1;
                  }

                  try {
                     OsmRoads.Way var15 = new OsmRoads.Way(
                        var13.getId(),
                        var13.getName(),
                        var40,
                        var37,
                        INSTANCE.widthOf(var40, var37, var13.getLanes(), null, ""),
                        var14,
                        var12,
                        var13.getLanes()
                     );
                     var10.add(var15);
                  } catch (Exception var21) {
                     Log.e("StarView/Osm", "box " + var1, var21);
                     var30 = var21.getMessage();
                     if (var30 != null) {
                        break label141;
                     }
                     break label145;
                  }
               }

               try {
                  var32 = var10;
               } catch (Exception var20) {
                  Log.e("StarView/Osm", "box " + var1, var20);
                  var30 = var20.getMessage();
                  if (var30 != null) {
                     break label141;
                  }
                  break label145;
               }

               try {
                  boxWays.put(var1, var32);
                  boxState.put(var1, 1);
                  Collection var33 = boxWays.values();
                  Intrinsics.checkNotNullExpressionValue(var33, "<get-values>(...)");
                  var34 = var33.iterator();
               } catch (Exception var19) {
                  Log.e("StarView/Osm", "box " + var1, var19);
                  var30 = var19.getMessage();
                  if (var30 != null) {
                     break label141;
                  }
                  break label145;
               }

               int var38 = 0;

               while (true) {
                  List var41;
                  try {
                     if (!var34.hasNext()) {
                        break;
                     }

                     var41 = (List)var34.next();
                  } catch (Exception var18) {
                     Log.e("StarView/Osm", "box " + var1, var18);
                     var30 = var18.getMessage();
                     if (var30 != null) {
                        break label141;
                     }
                     break label145;
                  }

                  try {
                     var38 += var41.size();
                  } catch (Exception var17) {
                     Log.e("StarView/Osm", "box " + var1, var17);
                     var30 = var17.getMessage();
                     if (var30 != null) {
                        break label141;
                     }
                     break label145;
                  }
               }

               try {
                  StringBuilder var36 = new StringBuilder();
                  tileStatus = var36.append("offline map: ").append(var38).append(" streets").toString();
                  return;
               } catch (Exception var16) {
                  Log.e("StarView/Osm", "box " + var1, var16);
                  var30 = var16.getMessage();
                  if (var30 != null) {
                     break label141;
                  }
               }
            }

            var35 = null;
            break label142;
         }

         var35 = StringsKt.take(var30, 50);
      }

      tileStatus = "offline map error: " + var35;
   }

   private final void ensureTiles(double var1, double var3) {
      this.ensureBoxes(var1, var3);
      long var15 = 4576918229304087675L;
      int var13 = (int)Math.floor(var1 / 0.01);
      int var12 = (int)Math.floor(var3 / 0.01);
      double var5 = var1 / 0.01 - var13;
      var1 = var3 / 0.01 - var12;
      double var7 = 300.0 / (kx * 0.01);
      int var10 = -1;
      var3 = var5;

      while (true) {
         byte var9 = 2;
         if (var10 >= 2) {
            return;
         }

         for (int var11 = -1; var11 < var9; var11++) {
            if (var10 != -1 || !(var3 > 0.2713949701465533)) {
               if ((var10 != 1 || !(var3 < 1 - 0.2713949701465533)) && (var11 != -1 || !(var1 > var7)) && (var11 != 1 || !(var1 < 1 - var7))) {
                  String var23 = this.key(var13 + var10, var12 + var11);
                  MapdMaps var24 = MapdMaps.INSTANCE;
                  var5 = var13 + var10;
                  long var19 = 4576918229304087675L;
                  if (!var24.boxFileFor((var5 + 0.5) * 0.01, (var12 + var11 + 0.5) * 0.01).getFirst()) {
                     Integer var29 = tiles.get(var23);
                     if (var29 != null && var29 == 0) {
                        var9 = 2;
                     } else if (var29 != null && var29 == 1) {
                        var9 = 2;
                     } else {
                        if (var29 != null) {
                           int var14 = var29;
                           var9 = 2;
                           if (var14 == 2) {
                              long var21 = System.currentTimeMillis();
                              Long var30 = failedAt.get(var23);
                              long var17;
                              if (var30 != null) {
                                 var17 = var30;
                              } else {
                                 var17 = 0L;
                              }

                              if (var21 - var17 < 30000L) {
                                 continue;
                              }
                           }
                        }

                        var9 = 2;
                        tiles.put(var23, 0);
                        loader.execute(new OsmRoads$$ExternalSyntheticLambda0(var13, var10, var12, var11));
                     }
                  } else {
                     var9 = 2;
                  }
               } else {
                  var9 = 2;
                  var15 = 4576918229304087675L;
               }
            }
         }

         var10++;
      }
   }

   private static final void ensureTiles$lambda$7(int var0, int var1, int var2, int var3) {
      INSTANCE.load(var0 + var1, var2 + var3);
   }

   private final OkHttpClient getHttp() {
      return (OkHttpClient)http$delegate.getValue();
   }

   private static final OkHttpClient http_delegate$lambda$0() {
      return new Builder().connectTimeout(10L, TimeUnit.SECONDS).readTimeout(40L, TimeUnit.SECONDS).build();
   }

   private final String key(int var1, int var2) {
      return var1 + "_" + var2;
   }

   // $VF: synthetic method
   public static double laneCenter$default(OsmRoads var0, double var1, int var3, int var4, boolean var5, int var6, Object var7) {
      if ((var6 & 8) != 0) {
         var5 = true;
      }

      return var0.laneCenter(var1, var3, var4, var5);
   }

   private static final int laneIndex$count(double var0) {
      return RangesKt.coerceAtLeast((int)Math.floor((var0 - 0.6) / 3.4), 0);
   }

   // $VF: synthetic method
   public static int laneIndex$default(
      OsmRoads var0, int var1, boolean var2, double var3, double var5, double var7, double var9, boolean var11, boolean var12, int var13, Object var14
   ) {
      if ((var13 & 64) != 0) {
         var11 = false;
      }

      if ((var13 & 128) != 0) {
         var12 = false;
      }

      return var0.laneIndex(var1, var2, var3, var5, var7, var9, var11, var12);
   }

   private final void load(int param1, int param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.util.collections.fixed.FastFixedSet.contains(Object)" because "predset" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.lambda$removeErroneousNodes$1(FastExtendedPostdominanceHelper.java:231)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.iterateReachability(FastExtendedPostdominanceHelper.java:373)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.removeErroneousNodes(FastExtendedPostdominanceHelper.java:207)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.getExtendedPostdominators(FastExtendedPostdominanceHelper.java:63)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.findGeneralStatement(DomHelper.java:537)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:472)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:379)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:208)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:184)
      //
      // Bytecode:
      // 000: aload 0
      // 001: iload 1
      // 002: iload 2
      // 003: invokespecial com/starpilot/starview/OsmRoads.key (II)Ljava/lang/String;
      // 006: astore 16
      // 008: new java/io/File
      // 00b: astore 17
      // 00d: getstatic com/starpilot/starview/OsmRoads.dir Ljava/io/File;
      // 010: astore 9
      // 012: aload 9
      // 014: ifnonnull 018
      // 017: return
      // 018: new java/lang/StringBuilder
      // 01b: astore 10
      // 01d: aload 10
      // 01f: invokespecial java/lang/StringBuilder.<init> ()V
      // 022: aload 17
      // 024: aload 9
      // 026: aload 10
      // 028: aload 16
      // 02a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 02d: ldc_w ".json"
      // 030: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 033: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 036: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 039: aload 17
      // 03b: invokevirtual java/io/File.isFile ()Z
      // 03e: istore 8
      // 040: ldc_w ""
      // 043: astore 15
      // 045: iload 8
      // 047: ifeq 063
      // 04a: aload 17
      // 04c: invokevirtual java/io/File.length ()J
      // 04f: ldc2_w 20
      // 052: lcmp
      // 053: ifle 063
      // 056: aload 17
      // 058: aconst_null
      // 059: bipush 1
      // 05a: aconst_null
      // 05b: invokestatic kotlin/io/FilesKt.readText$default (Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/lang/String;
      // 05e: astore 9
      // 060: goto 2dd
      // 063: ldc_w "downloading streets…"
      // 066: putstatic com/starpilot/starview/OsmRoads.tileStatus Ljava/lang/String;
      // 069: iload 1
      // 06a: i2d
      // 06b: ldc2_w 0.01
      // 06e: dmul
      // 06f: dstore 5
      // 071: iload 2
      // 072: i2d
      // 073: ldc2_w 0.01
      // 076: dmul
      // 077: dstore 3
      // 078: getstatic java/util/Locale.US Ljava/util/Locale;
      // 07b: ldc_w "%.4f"
      // 07e: bipush 1
      // 07f: anewarray 4
      // 082: dup
      // 083: bipush 0
      // 084: dload 5
      // 086: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 089: aastore
      // 08a: bipush 1
      // 08b: invokestatic java/util/Arrays.copyOf ([Ljava/lang/Object;I)[Ljava/lang/Object;
      // 08e: invokestatic java/lang/String.format (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 091: astore 11
      // 093: aload 11
      // 095: ldc_w "format(...)"
      // 098: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 09b: getstatic java/util/Locale.US Ljava/util/Locale;
      // 09e: ldc_w "%.4f"
      // 0a1: bipush 1
      // 0a2: anewarray 4
      // 0a5: dup
      // 0a6: bipush 0
      // 0a7: dload 3
      // 0a8: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 0ab: aastore
      // 0ac: bipush 1
      // 0ad: invokestatic java/util/Arrays.copyOf ([Ljava/lang/Object;I)[Ljava/lang/Object;
      // 0b0: invokestatic java/lang/String.format (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 0b3: astore 12
      // 0b5: aload 12
      // 0b7: ldc_w "format(...)"
      // 0ba: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0bd: getstatic java/util/Locale.US Ljava/util/Locale;
      // 0c0: ldc_w "%.4f"
      // 0c3: bipush 1
      // 0c4: anewarray 4
      // 0c7: dup
      // 0c8: bipush 0
      // 0c9: dload 5
      // 0cb: ldc2_w 0.01
      // 0ce: dadd
      // 0cf: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 0d2: aastore
      // 0d3: bipush 1
      // 0d4: invokestatic java/util/Arrays.copyOf ([Ljava/lang/Object;I)[Ljava/lang/Object;
      // 0d7: invokestatic java/lang/String.format (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 0da: astore 9
      // 0dc: aload 9
      // 0de: ldc_w "format(...)"
      // 0e1: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0e4: getstatic java/util/Locale.US Ljava/util/Locale;
      // 0e7: ldc_w "%.4f"
      // 0ea: bipush 1
      // 0eb: anewarray 4
      // 0ee: dup
      // 0ef: bipush 0
      // 0f0: dload 3
      // 0f1: ldc2_w 0.01
      // 0f4: dadd
      // 0f5: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 0f8: aastore
      // 0f9: bipush 1
      // 0fa: invokestatic java/util/Arrays.copyOf ([Ljava/lang/Object;I)[Ljava/lang/Object;
      // 0fd: invokestatic java/lang/String.format (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 100: astore 13
      // 102: aload 13
      // 104: ldc_w "format(...)"
      // 107: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 10a: new java/lang/StringBuilder
      // 10d: astore 10
      // 10f: aload 10
      // 111: invokespecial java/lang/StringBuilder.<init> ()V
      // 114: aload 10
      // 116: ldc_w "[out:json][timeout:30];way[\"highway\"][\"area\"!=\"yes\"]("
      // 119: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 11c: aload 11
      // 11e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 121: ldc_w ","
      // 124: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 127: aload 12
      // 129: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 12c: ldc_w ","
      // 12f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 132: aload 9
      // 134: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 137: ldc_w ","
      // 13a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 13d: aload 13
      // 13f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 142: ldc_w ");out tags geom;"
      // 145: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 148: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 14b: astore 13
      // 14d: getstatic com/starpilot/starview/OsmRoads.URLS Ljava/util/List;
      // 150: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 155: astore 12
      // 157: ldc_w ""
      // 15a: astore 10
      // 15c: aconst_null
      // 15d: astore 9
      // 15f: aload 12
      // 161: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 166: ifeq 29b
      // 169: aload 12
      // 16b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 170: checkcast java/lang/String
      // 173: astore 14
      // 175: new okhttp3/Request$Builder
      // 178: astore 11
      // 17a: aload 11
      // 17c: invokespecial okhttp3/Request$Builder.<init> ()V
      // 17f: aload 11
      // 181: aload 14
      // 183: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 186: ldc_w "User-Agent"
      // 189: ldc_w "StarView/2.25 (openpilot tablet display)"
      // 18c: invokevirtual okhttp3/Request$Builder.header (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 18f: astore 14
      // 191: new okhttp3/FormBody$Builder
      // 194: astore 11
      // 196: aload 11
      // 198: aconst_null
      // 199: bipush 1
      // 19a: aconst_null
      // 19b: invokespecial okhttp3/FormBody$Builder.<init> (Ljava/nio/charset/Charset;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 19e: aload 14
      // 1a0: aload 11
      // 1a2: ldc_w "data"
      // 1a5: aload 13
      // 1a7: invokevirtual okhttp3/FormBody$Builder.add (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/FormBody$Builder;
      // 1aa: invokevirtual okhttp3/FormBody$Builder.build ()Lokhttp3/FormBody;
      // 1ad: checkcast okhttp3/RequestBody
      // 1b0: invokevirtual okhttp3/Request$Builder.post (Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;
      // 1b3: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 1b6: astore 11
      // 1b8: aload 0
      // 1b9: invokespecial com/starpilot/starview/OsmRoads.getHttp ()Lokhttp3/OkHttpClient;
      // 1bc: aload 11
      // 1be: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 1c1: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 1c6: checkcast java/io/Closeable
      // 1c9: astore 14
      // 1cb: aload 14
      // 1cd: checkcast okhttp3/Response
      // 1d0: astore 18
      // 1d2: aload 18
      // 1d4: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 1d7: astore 11
      // 1d9: aload 11
      // 1db: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 1de: aload 11
      // 1e0: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 1e3: astore 11
      // 1e5: aload 18
      // 1e7: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 1ea: ifeq 218
      // 1ed: aload 11
      // 1ef: checkcast java/lang/CharSequence
      // 1f2: invokestatic kotlin/text/StringsKt.trimStart (Ljava/lang/CharSequence;)Ljava/lang/CharSequence;
      // 1f5: astore 19
      // 1f7: aload 19
      // 1f9: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 1fc: astore 19
      // 1fe: aload 19
      // 200: ldc_w "{"
      // 203: bipush 0
      // 204: bipush 2
      // 205: aconst_null
      // 206: invokestatic kotlin/text/StringsKt.startsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 209: ifeq 218
      // 20c: aload 11
      // 20e: astore 9
      // 210: goto 239
      // 213: astore 10
      // 215: goto 257
      // 218: aload 18
      // 21a: invokevirtual okhttp3/Response.code ()I
      // 21d: istore 1
      // 21e: new java/lang/StringBuilder
      // 221: astore 10
      // 223: aload 10
      // 225: invokespecial java/lang/StringBuilder.<init> ()V
      // 228: aload 10
      // 22a: ldc_w "HTTP "
      // 22d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 230: iload 1
      // 231: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 234: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 237: astore 10
      // 239: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 23c: astore 11
      // 23e: aload 9
      // 240: astore 11
      // 242: aload 14
      // 244: aconst_null
      // 245: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 248: goto 290
      // 24b: astore 10
      // 24d: goto 257
      // 250: astore 10
      // 252: goto 257
      // 255: astore 10
      // 257: aload 10
      // 259: athrow
      // 25a: astore 18
      // 25c: aload 9
      // 25e: astore 11
      // 260: aload 14
      // 262: aload 10
      // 264: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 267: aload 9
      // 269: astore 11
      // 26b: aload 18
      // 26d: athrow
      // 26e: astore 14
      // 270: aload 11
      // 272: astore 9
      // 274: goto 279
      // 277: astore 14
      // 279: aload 14
      // 27b: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 27e: astore 11
      // 280: aload 11
      // 282: astore 10
      // 284: aload 11
      // 286: ifnonnull 290
      // 289: aload 14
      // 28b: invokevirtual java/lang/Exception.toString ()Ljava/lang/String;
      // 28e: astore 10
      // 290: aload 9
      // 292: ifnull 298
      // 295: goto 29b
      // 298: goto 15f
      // 29b: aload 9
      // 29d: ifnull 3d2
      // 2a0: new java/io/File
      // 2a3: astore 12
      // 2a5: aload 17
      // 2a7: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 2aa: astore 11
      // 2ac: new java/lang/StringBuilder
      // 2af: astore 10
      // 2b1: aload 10
      // 2b3: invokespecial java/lang/StringBuilder.<init> ()V
      // 2b6: aload 12
      // 2b8: aload 10
      // 2ba: aload 11
      // 2bc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2bf: ldc_w ".tmp"
      // 2c2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2c5: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 2c8: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 2cb: aload 12
      // 2cd: aload 9
      // 2cf: aconst_null
      // 2d0: bipush 2
      // 2d1: aconst_null
      // 2d2: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 2d5: aload 12
      // 2d7: aload 17
      // 2d9: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 2dc: pop
      // 2dd: aload 0
      // 2de: aload 9
      // 2e0: invokevirtual com/starpilot/starview/OsmRoads.parse$app_debug (Ljava/lang/String;)I
      // 2e3: istore 7
      // 2e5: getstatic com/starpilot/starview/OsmRoads.tiles Ljava/util/concurrent/ConcurrentHashMap;
      // 2e8: checkcast java/util/Map
      // 2eb: aload 16
      // 2ed: bipush 1
      // 2ee: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 2f1: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 2f6: pop
      // 2f7: getstatic com/starpilot/starview/OsmRoads.tiles Ljava/util/concurrent/ConcurrentHashMap;
      // 2fa: invokevirtual java/util/concurrent/ConcurrentHashMap.values ()Ljava/util/Collection;
      // 2fd: astore 9
      // 2ff: aload 9
      // 301: ldc_w "<get-values>(...)"
      // 304: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 307: aload 9
      // 309: checkcast java/lang/Iterable
      // 30c: astore 9
      // 30e: aload 9
      // 310: checkcast java/util/Collection
      // 313: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 318: ifeq 320
      // 31b: bipush 0
      // 31c: istore 1
      // 31d: goto 36d
      // 320: bipush 0
      // 321: istore 1
      // 322: aload 9
      // 324: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 329: astore 9
      // 32b: aload 9
      // 32d: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 332: ifeq 36d
      // 335: aload 9
      // 337: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 33c: checkcast java/lang/Integer
      // 33f: astore 10
      // 341: aload 10
      // 343: ifnonnull 349
      // 346: goto 357
      // 349: aload 10
      // 34b: invokevirtual java/lang/Integer.intValue ()I
      // 34e: bipush 1
      // 34f: if_icmpne 357
      // 352: bipush 1
      // 353: istore 2
      // 354: goto 359
      // 357: bipush 0
      // 358: istore 2
      // 359: iload 2
      // 35a: ifeq 36a
      // 35d: iinc 1 1
      // 360: iload 1
      // 361: ifge 367
      // 364: invokestatic kotlin/collections/CollectionsKt.throwCountOverflow ()V
      // 367: goto 32b
      // 36a: goto 32b
      // 36d: getstatic com/starpilot/starview/OsmRoads.ways Ljava/util/concurrent/ConcurrentHashMap;
      // 370: invokevirtual java/util/concurrent/ConcurrentHashMap.size ()I
      // 373: istore 2
      // 374: aload 15
      // 376: astore 9
      // 378: iload 7
      // 37a: ifne 39f
      // 37d: new java/lang/StringBuilder
      // 380: astore 9
      // 382: aload 9
      // 384: invokespecial java/lang/StringBuilder.<init> ()V
      // 387: aload 9
      // 389: ldc_w " (tile "
      // 38c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 38f: aload 16
      // 391: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 394: ldc_w " empty)"
      // 397: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 39a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 39d: astore 9
      // 39f: new java/lang/StringBuilder
      // 3a2: astore 10
      // 3a4: aload 10
      // 3a6: invokespecial java/lang/StringBuilder.<init> ()V
      // 3a9: aload 10
      // 3ab: iload 1
      // 3ac: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 3af: ldc_w " tiles, "
      // 3b2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3b5: iload 2
      // 3b6: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 3b9: ldc_w " streets"
      // 3bc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3bf: aload 9
      // 3c1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3c4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3c7: putstatic com/starpilot/starview/OsmRoads.tileStatus Ljava/lang/String;
      // 3ca: goto 46d
      // 3cd: astore 9
      // 3cf: goto 3e8
      // 3d2: new java/io/IOException
      // 3d5: astore 9
      // 3d7: aload 9
      // 3d9: aload 10
      // 3db: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 3de: aload 9
      // 3e0: athrow
      // 3e1: astore 9
      // 3e3: goto 3e8
      // 3e6: astore 9
      // 3e8: ldc "StarView/Osm"
      // 3ea: new java/lang/StringBuilder
      // 3ed: dup
      // 3ee: invokespecial java/lang/StringBuilder.<init> ()V
      // 3f1: ldc_w "tile "
      // 3f4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3f7: aload 16
      // 3f9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3fc: ldc_w ": "
      // 3ff: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 402: aload 9
      // 404: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 407: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 40a: invokestatic android/util/Log.w (Ljava/lang/String;Ljava/lang/String;)I
      // 40d: pop
      // 40e: getstatic com/starpilot/starview/OsmRoads.tiles Ljava/util/concurrent/ConcurrentHashMap;
      // 411: checkcast java/util/Map
      // 414: aload 16
      // 416: bipush 2
      // 417: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 41a: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 41f: pop
      // 420: getstatic com/starpilot/starview/OsmRoads.failedAt Ljava/util/concurrent/ConcurrentHashMap;
      // 423: checkcast java/util/Map
      // 426: aload 16
      // 428: invokestatic java/lang/System.currentTimeMillis ()J
      // 42b: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 42e: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 433: pop
      // 434: aload 9
      // 436: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 439: astore 9
      // 43b: aload 9
      // 43d: ifnull 44c
      // 440: aload 9
      // 442: bipush 40
      // 444: invokestatic kotlin/text/StringsKt.take (Ljava/lang/String;I)Ljava/lang/String;
      // 447: astore 9
      // 449: goto 44f
      // 44c: aconst_null
      // 44d: astore 9
      // 44f: new java/lang/StringBuilder
      // 452: dup
      // 453: invokespecial java/lang/StringBuilder.<init> ()V
      // 456: ldc_w "street download failed: "
      // 459: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 45c: aload 9
      // 45e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 461: ldc_w " (retrying)"
      // 464: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 467: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 46a: putstatic com/starpilot/starview/OsmRoads.tileStatus Ljava/lang/String;
      // 46d: return
   }

   private static final Thread loader$lambda$2(Runnable var0) {
      Thread var1 = new Thread(var0, "starview-osm");
      var1.setDaemon(true);
      return var1;
   }

   private static final void near$take(double var0, double var2, double var4, HashSet<Long> var6, ArrayList<OsmRoads.Way> var7, OsmRoads.Way var8) {
      if (var8.getMaxE() >= var0 - var2
         && var8.getMinE() <= var0 + var2
         && var8.getMaxN() >= var4 - var2
         && var8.getMinN() <= var4 + var2
         && var6.add(var8.getId())) {
         var7.add(var8);
      }
   }

   // $VF: synthetic method
   public static void onFix$default(
      OsmRoads var0, double var1, double var3, float var5, float var6, float var7, float var8, long var9, long var11, boolean var13, int var14, Object var15
   ) {
      if ((var14 & 32) != 0) {
         var8 = Float.NaN;
      }

      if ((var14 & 64) != 0) {
         var9 = 0L;
      }

      if ((var14 & 128) != 0) {
         var11 = 0L;
      }

      if ((var14 & 256) != 0) {
         var13 = false;
      }

      var0.onFix(var1, var3, var5, var6, var7, var8, var9, var11, var13);
   }

   public final List<OsmRoads.Piece> carFrame(
      List<OsmRoads.Way> var1, Function2<? super Double, ? super Double, Pair<Double, Double>> var2, double var3, double var5
   ) {
      Intrinsics.checkNotNullParameter(var1, "ways");
      Intrinsics.checkNotNullParameter(var2, "toCar");
      ArrayList var29 = new ArrayList();
      ArrayList var31 = new ArrayList();
      ArrayList var32 = new ArrayList();
      Iterator var28 = var1.iterator();
      ArrayList var35 = var29;

      while (var28.hasNext()) {
         OsmRoads.Way var30 = (OsmRoads.Way)var28.next();
         int var25 = var30.getE().length;
         if (var25 >= 2) {
            double[] var47 = new double[var25];
            double[] var33 = new double[var25];

            for (int var23 = 0; var23 < var25; var23++) {
               Pair var34 = (Pair)var2.invoke(var30.getE()[var23], var30.getN()[var23]);
               double var7 = ((Number)var34.component1()).doubleValue();
               double var9 = ((Number)var34.component2()).doubleValue();
               var47[var23] = var7;
               var33[var23] = var9;
            }

            DoubleRef var48 = new DoubleRef();
            var48.element = Double.MAX_VALUE;
            int var24 = 0;

            for (int var46 = var25 - 1; var24 < var46; var24++) {
               double var13 = var47[var24];
               double var41 = var33[var24];
               double var15 = var47[var24 + 1] - var13;
               double var11 = var33[var24 + 1] - var41;
               double var39 = var15 * var15 + var11 * var11;
               if (!(var39 < 1.0E-6)) {
                  double var21 = var13 * var15 + var41 * var11;
                  double var17 = var21 * var21 - var39 * (var13 * var13 + var41 * var41 - var3 * var3);
                  if (var17 <= 0.0) {
                     carFrame$flush(var31, var35, var48, var30, var32);
                  } else {
                     double var19 = Math.sqrt(var17);
                     var17 = Math.max(0.0, (-var21 - var19) / var39);
                     var19 = Math.min(1.0, (-var21 + var19) / var39);
                     if (var17 >= var19) {
                        carFrame$flush(var31, var35, var48, var30, var32);
                     } else {
                        if (var17 > 0.0) {
                           carFrame$flush(var31, var35, var48, var30, var32);
                        }

                        var21 = RangesKt.coerceIn(-var21 / var39, 0.0, 1.0);
                        var48.element = Math.min(var48.element, Math.hypot(var13 + var21 * var15, var41 + var21 * var11));
                        var39 = Math.sqrt(var39) * (var19 - var17);
                        int var27 = Math.max(1, (int)Math.ceil(var39 / var5));
                        if (var31.isEmpty()) {
                           var31.add(var13 + var17 * var15);
                           var32.add(var17 * var11 + var41);
                        }

                        int var26 = 1;
                        if (1 <= var27) {
                           while (true) {
                              var21 = (var19 - var17) * var26 / var27 + var17;
                              var31.add(var13 + var21 * var15);
                              var32.add(var21 * var11 + var41);
                              if (var26 == var27) {
                                 break;
                              }

                              var26++;
                           }
                        }

                        if (var19 < 1.0) {
                           carFrame$flush(var31, var35, var48, var30, var32);
                        }
                     }
                  }
               }
            }

            carFrame$flush(var31, var35, var48, var30, var32);
         }
      }

      Iterable var37 = CollectionsKt.sortedWith(var35, new Comparator() {
         @Override
         public final int compare(T var1, T var2x) {
            return ComparisonsKt.compareValues((T)((Double & Comparable)((Pair)var1).getFirst()), (T)((Double & Comparable)((Pair)var2x).getFirst()));
         }
      });
      var var36 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var37, 10));
      Iterator var38 = var37.iterator();

      while (var38.hasNext()) {
         var36.add((OsmRoads.Piece)((Pair)var38.next()).getSecond());
      }

      return var36;
   }

   public final List<Pair<Double, String>> crossStreetsAhead(double var1, double var3, double var5, double var7) {
      OsmRoads.Match var32 = this.matchSeg(var1, var3, var5);
      if (var32 != null) {
         boolean var23;
         if (var32.getDist() < 15.0) {
            var23 = 1;
         } else {
            var23 = 0;
         }

         if (!var23) {
            var32 = null;
         }

         if (var32 != null) {
            ArrayList var38 = new ArrayList();
            ArrayList var39 = new ArrayList();
            HashSet var37 = new HashSet();
            OsmRoads.Way var35 = var32.getWay();
            var37.add(var35.getId());
            int var24 = 0;
            int var25 = var35.getE().length;
            var23 = 0;
            double var9 = Double.MAX_VALUE;

            while (var24 < var25 - 1) {
               double var11 = var35.getE()[var24];
               double var17 = var35.getN()[var24];
               double var13 = var35.getE()[var24 + 1] - var11;
               double var15 = var35.getN()[var24 + 1] - var17;
               double var19 = var13 * var13 + var15 * var15;
               if (!(var19 < 0.01)) {
                  var19 = RangesKt.coerceIn(((var1 - var11) * var13 + (var3 - var17) * var15) / var19, 0.0, 1.0);
                  var13 = Math.hypot(var1 - (var11 + var19 * var13), var3 - (var17 + var19 * var15));
                  var11 = var9;
                  if (var13 < var9) {
                     var23 = var24;
                     var11 = var13;
                  }
               } else {
                  var11 = var9;
               }

               var24++;
               var9 = var11;
            }

            boolean var88;
            if (Math.cos(Math.atan2(var35.getN()[var23 + 1] - var35.getN()[var23], var35.getE()[var23 + 1] - var35.getE()[var23]) - var5) >= 0.0) {
               var88 = 1;
            } else {
               var88 = 0;
            }

            IntProgression var104;
            if (var88) {
               var104 = RangesKt.until(var23 + 1, var35.getE().length);
            } else {
               var104 = RangesKt.downTo(var23, 0);
            }

            var38.add(var1);
            var39.add(var3);
            var5 = 0.0;
            var25 = 0;

            while (true) {
               double var55;
               label233: {
                  int var26;
                  int var28;
                  int var29;
                  double var61;
                  label290: {
                     int var27 = var104.getFirst();
                     var28 = var104.getLast();
                     var29 = var104.getStep();
                     if (var29 > 0) {
                        var26 = var27;
                        var61 = var5;
                        var88 = var23;
                        var55 = var9;
                        if (var27 <= var28) {
                           break label290;
                        }
                     }

                     if (var29 >= 0 || var28 > var27) {
                        var55 = var5;
                        break label233;
                     }

                     var55 = var9;
                     var88 = var23;
                     var61 = var5;
                     var26 = var27;
                  }

                  while (true) {
                     double var69 = var35.getE()[var26];
                     var9 = CollectionsKt.last(var38).doubleValue();
                     double var74 = var35.getN()[var26];
                     double var78 = CollectionsKt.last(var39).doubleValue();
                     var61 += Math.hypot(var69 - var9, var74 - var78);
                     var38.add(var35.getE()[var26]);
                     var39.add(var35.getN()[var26]);
                     var55 = var61;
                     var23 = var88;
                     var9 = var55;
                     if (var61 >= var7) {
                        break;
                     }

                     var55 = var61;
                     var23 = var88;
                     var9 = var55;
                     if (var26 == var28) {
                        break;
                     }

                     var26 += var29;
                     var55 = var55;
                  }
               }

               var5 = var55;
               if (!(var55 >= var7) && var25 < 8 && var38.size() >= 2) {
                  double var79 = CollectionsKt.last(var38).doubleValue();
                  double var75 = CollectionsKt.last(var39).doubleValue();
                  OsmRoads.Way var105 = (OsmRoads.Way)var39.get(var39.size() - 2);
                  Intrinsics.checkNotNullExpressionValue(var105, "get(...)");
                  var55 = ((Number)var105).doubleValue();
                  var105 = var38.get(var38.size() - 2);
                  Intrinsics.checkNotNullExpressionValue(var105, "get(...)");
                  double var21 = Math.atan2(var75 - var55, var79 - ((Number)var105).doubleValue());
                  var105 = null;
                  boolean var30 = true;
                  var55 = Double.MAX_VALUE;

                  for (OsmRoads.Way var36 : this.near(var79, var75, 6.0)) {
                     if (!var37.contains(var36.getId())
                        && !Intrinsics.areEqual(var36.getKind(), "sidewalk")
                        && !Intrinsics.areEqual(var36.getKind(), "service")) {
                        var88 = var36.getE().length - 1;

                        for (Pair var41 : CollectionsKt.listOf(new Pair[]{TuplesKt.to(0, true), TuplesKt.to(var88, false)})) {
                           int var96 = ((Number)var41.component1()).intValue();
                           boolean var31 = (Boolean)var41.component2();
                           if (!(Math.hypot(var36.getE()[var96] - var79, var36.getN()[var96] - var75) > 4.0)
                              && (var31 || var36.getOneway() <= 0)
                              && (!var31 || var36.getOneway() >= 0)) {
                              double[] var121 = var36.getE();
                              double var62;
                              if (var31) {
                                 var62 = var121[1] - var36.getE()[0];
                              } else {
                                 var62 = var121[var88 - 1] - var36.getE()[var88];
                              }

                              double[] var122 = var36.getN();
                              double var70;
                              if (var31) {
                                 var70 = var122[1] - var36.getN()[0];
                              } else {
                                 var70 = var122[var88 - 1] - var36.getN()[var88];
                              }

                              var70 = Math.abs(Math.IEEEremainder(Math.atan2(var70, var62) - var21, Math.PI * 2));
                              if (!(var70 > Math.toRadians(35.0))) {
                                 if (this.sameStreet(var36.getName(), var35.getName())) {
                                    var62 = 0.0;
                                 } else {
                                    var62 = 0.3;
                                 }

                                 var62 += var70;
                                 if (var62 < var55) {
                                    var55 = var62;
                                    var30 = var31;
                                    var105 = var36;
                                 }
                              }
                           }
                        }
                     }
                  }

                  if (var105 != null) {
                     var35 = var105;
                     var37.add(var105.getId());
                     var25++;
                     double[] var108 = var105.getE();
                     if (var30) {
                        var104 = RangesKt.until(1, var108.length);
                     } else {
                        var104 = RangesKt.downTo(var108.length - 2, 0);
                     }
                     continue;
                  }
               }
               break;
            }

            String var112 = "get(...)";
            double[] var119 = new double[var38.size()];
            var23 = 1;

            for (int var91 = var38.size(); var23 < var91; var23++) {
               var9 = var119[var23 - 1];
               double var65 = ((Number)var38.get(var23)).doubleValue();
               Object var109 = var38.get(var23 - 1);
               Intrinsics.checkNotNullExpressionValue(var109, "get(...)");
               double var58 = ((Number)var109).doubleValue();
               var5 = ((Number)var39.get(var23)).doubleValue();
               var109 = var39.get(var23 - 1);
               Intrinsics.checkNotNullExpressionValue(var109, "get(...)");
               var119[var23] = var9 + Math.hypot(var65 - var58, var5 - ((Number)var109).doubleValue());
            }

            String var113 = var32.getWay().getName();
            ArrayList var120 = new ArrayList();

            for (OsmRoads.Way var123 : this.near(var1, var3, var7 + 30.0)) {
               if (!var37.contains(var123.getId()) && !Intrinsics.areEqual(var123.getKind(), "sidewalk") && !Intrinsics.areEqual(var123.getKind(), "service")) {
                  boolean var85;
                  if (var113.length() > 0) {
                     var85 = 1;
                  } else {
                     var85 = 0;
                  }

                  if (!var85 || !this.sameStreet(var123.getName(), var113)) {
                     var85 = var38.size() - 1;

                     for (int var92 = 0; var92 < var85; var92++) {
                        Object var114 = var38.get(var92);
                        Intrinsics.checkNotNullExpressionValue(var114, var112);
                        var5 = ((Number)var114).doubleValue();
                        var114 = var39.get(var92);
                        Intrinsics.checkNotNullExpressionValue(var114, var112);
                        var3 = ((Number)var114).doubleValue();
                        var114 = var38.get(var92 + 1);
                        Intrinsics.checkNotNullExpressionValue(var114, var112);
                        var1 = ((Number)var114).doubleValue();
                        var114 = var39.get(var92 + 1);
                        Intrinsics.checkNotNullExpressionValue(var114, var112);
                        var9 = ((Number)var114).doubleValue();
                        var1 -= var5;
                        var9 -= var3;
                        double var59 = Math.hypot(var1, var9);
                        if (!(var59 < 0.01)) {
                           var25 = var123.getE().length - 1;

                           for (int var97 = 0; var97 < var25; var97++) {
                              double var66 = var123.getE()[var97];
                              double var72 = var123.getN()[var97];
                              double var82 = var123.getE()[var97 + 1] - var66;
                              double var80 = var123.getN()[var97 + 1] - var72;
                              double var76 = var1 * var80 - var9 * var82;
                              if (Math.abs(var76) > 1.0E-9) {
                                 var80 = ((var66 - var5) * var80 - (var72 - var3) * var82) / var76;
                                 var66 = ((var66 - var5) * var9 - (var72 - var3) * var1) / var76;
                                 boolean var99;
                                 if (0.0 <= var80 && var80 <= 1.0) {
                                    var99 = true;
                                 } else {
                                    var99 = false;
                                 }

                                 if (var99) {
                                    if (0.0 <= var66 && var66 <= 1.0) {
                                       var99 = true;
                                    } else {
                                       var99 = false;
                                    }

                                    if (var99) {
                                       crossStreetsAhead$add(var7, var120, var119[var92] + var80 * var59, var123.getName());
                                    }
                                 }
                              }
                           }

                           int var102 = var123.getE().length;
                           boolean var87 = false;

                           for (int var98 = 0; var98 < 2; var98++) {
                              int var103 = new int[]{0, var102 - 1}[var98];
                              double var68 = ((var123.getE()[var103] - var5) * var1 + (var123.getN()[var103] - var3) * var9) / (var59 * var59);
                              boolean var101;
                              if (0.0 <= var68 && var68 <= 1.0) {
                                 var101 = true;
                              } else {
                                 var101 = var87;
                              }

                              if (var101) {
                                 if (Math.hypot(var123.getE()[var103] - (var5 + var68 * var1), var123.getN()[var103] - (var3 + var68 * var9)) < 6.0) {
                                    double var73 = var119[var92];
                                    var114 = var123.getName();
                                    var87 = false;
                                    crossStreetsAhead$add(var7, var120, var68 * var59 + var73, (String)var114);
                                 } else {
                                    var87 = false;
                                 }
                              }
                           }

                           var85 = var85;
                        }
                     }
                  }
               }
            }

            return CollectionsKt.sortedWith(var120, new Comparator() {
               @Override
               public final int compare(T var1, T var2) {
                  return ComparisonsKt.compareValues((T)((Double & Comparable)((Pair)var1).getFirst()), (T)((Double & Comparable)((Pair)var2).getFirst()));
               }
            });
         }
      }

      return CollectionsKt.emptyList();
   }

   public final boolean getEnabled() {
      return enabled;
   }

   public final ConcurrentLinkedQueue<OsmRoads.Fix> getFixes() {
      return fixes;
   }

   public final String getGpsSrc() {
      return gpsSrc;
   }

   public final OsmRoads.Fix getLastFix() {
      return lastFix;
   }

   public final double getLat0() {
      return lat0;
   }

   public final double getLon0() {
      return lon0;
   }

   public final String getMatchStatus() {
      return matchStatus;
   }

   public final String getTileStatus() {
      return tileStatus;
   }

   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      if (dir == null) {
         File var2 = new File(var1.getFilesDir(), "osm");
         var2.mkdirs();
         dir = var2;
      }
   }

   public final Pair<Double, Double> laneBand(double var1, boolean var3, double var4, double var6) {
      return this.laneBand(var1, var3, var4, Double.NaN, var6, Double.NaN);
   }

   public final Pair<Double, Double> laneBand(double var1, boolean var3, double var4, double var6, double var8, double var10) {
      double var18 = var1 / 2.0;
      if (var3) {
         var1 = -var18 + 0.9;
      } else {
         var1 = 0.3;
      }

      double var16 = var18 - 0.9;
      double var12 = var16;
      double var14 = var1;
      if (var16 < var1) {
         if (var3) {
            var1 = 0.0;
         } else {
            var1 = var18 / 2;
         }

         var14 = var1;
         var12 = var1;
      }

      var3 = Double.isInfinite(var4);
      boolean var21 = true;
      boolean var20;
      if (!var3 && !Double.isNaN(var4)) {
         var20 = true;
      } else {
         var20 = false;
      }

      var16 = Double.NEGATIVE_INFINITY;
      if (var20) {
         var1 = -var18 + var4;
      } else {
         var1 = Double.NEGATIVE_INFINITY;
      }

      if (!Double.isInfinite(var10) && !Double.isNaN(var10)) {
         var20 = true;
      } else {
         var20 = false;
      }

      var4 = var16;
      if (var20) {
         var4 = var18 - var10;
      }

      var10 = Math.max(var1, var4);
      if (!Double.isInfinite(var8) && !Double.isNaN(var8)) {
         var20 = true;
      } else {
         var20 = false;
      }

      var4 = Double.POSITIVE_INFINITY;
      if (var20) {
         var1 = var18 - var8;
      } else {
         var1 = Double.POSITIVE_INFINITY;
      }

      if (!Double.isInfinite(var6) && !Double.isNaN(var6)) {
         var20 = var21;
      } else {
         var20 = false;
      }

      if (var20) {
         var4 = -var18 + var6;
      }

      var1 = Math.min(var1, var4);
      if (var10 > var1) {
         var1 = (var10 + var1) / 2;
         return new Pair<>(var1, var1);
      } else {
         var4 = Math.max(var14, var10);
         var6 = Math.min(var12, var1);
         if (var4 <= var6) {
            return new Pair<>(var4, var6);
         } else {
            Pair var22 = new Pair<>(Math.max(var10, -var18), Math.min(var1, var18));
            if (!(((Number)var22.getFirst()).doubleValue() <= ((Number)var22.getSecond()).doubleValue())) {
               var22 = new Pair<>(var10, var1);
            }

            return var22;
         }
      }
   }

   public final double laneCenter(double var1, int var3, int var4, boolean var5) {
      var3 = Math.max(1, var3);
      var4 = RangesKt.coerceIn(var4, 0, var3 - 1);
      if (var5) {
         var1 = -var1 / 2 + (var4 + 0.5) * var1 / var3;
      } else {
         var1 = (var4 + 0.5) * (var1 / 2) / var3;
      }

      return var1;
   }

   public final int laneIndex(int var1, boolean var2, double var3, double var5, double var7, double var9, boolean var11, boolean var12) {
      boolean var19 = false;
      if (var1 <= 1) {
         return 0;
      } else if (var2) {
         return 0;
      } else {
         byte var15 = 0;
         int var16 = var1 - 1;
         if (var11) {
            var15 = 1;
         }

         if (var12) {
            var16 = var1 - 2;
         }

         int var17 = var16;
         if (var15 > var16) {
            var17 = var15;
         }

         boolean var25;
         if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
            var25 = 1;
         } else {
            var25 = 0;
         }

         double var13;
         label110: {
            var13 = Double.NaN;
            if (var25) {
               if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                  var25 = (boolean)1;
               } else {
                  var25 = (boolean)0;
               }

               if (var25) {
                  var3 = var5 - var3;
                  break label110;
               }
            }

            var3 = Double.NaN;
         }

         if (!Double.isInfinite(var9) && !Double.isNaN(var9)) {
            var25 = (boolean)1;
         } else {
            var25 = (boolean)0;
         }

         var5 = var13;
         if (var25) {
            if (!Double.isInfinite(var7) && !Double.isNaN(var7)) {
               var25 = (boolean)1;
            } else {
               var25 = (boolean)0;
            }

            var5 = var13;
            if (var25) {
               var5 = var9 - var7;
            }
         }

         if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
            var25 = (boolean)1;
         } else {
            var25 = (boolean)0;
         }

         byte var20 = -1;
         if (var25 && var3 > -0.5) {
            var25 = laneIndex$count(var3);
         } else {
            var25 = -1;
         }

         boolean var18 = var19;
         if (!Double.isInfinite(var5)) {
            var18 = var19;
            if (!Double.isNaN(var5)) {
               var18 = true;
            }
         }

         if (var18 && var5 > -0.5) {
            var1 = var1 - 1 - laneIndex$count(var5);
         } else {
            var1 = -1;
         }

         if (var25 >= 0) {
            var1 = var25;
         } else if (var1 < 0) {
            var1 = -1;
         }

         if (var1 >= 0) {
            var1 = RangesKt.coerceIn(var1, var15, var17);
         } else {
            var1 = var20;
            if (var15 == var17) {
               var1 = var15;
            }
         }

         return var1;
      }
   }

   public final Triple<OsmRoads.Way, Double, Double> match(double var1, double var3, double var5) {
      OsmRoads.Way var34 = null;
      double var9 = 0.0;
      double var11 = Double.MAX_VALUE;
      double var7 = 0.0;

      for (OsmRoads.Way var35 : this.near(var1, var3, 30.0)) {
         if (!Intrinsics.areEqual(var35.getKind(), "sidewalk")) {
            int var32 = 0;

            for (int var31 = var35.getE().length - 1; var32 < var31; var32++) {
               double var19 = var35.getE()[var32];
               double var25 = var35.getN()[var32];
               double var23 = var35.getE()[var32 + 1] - var19;
               double var27 = var35.getN()[var32 + 1] - var25;
               double var21 = var23 * var23 + var27 * var27;
               if (!(var21 < 0.01)) {
                  double var13 = RangesKt.coerceIn(((var1 - var19) * var23 + (var3 - var25) * var27) / var21, 0.0, 1.0);
                  double var17 = Math.hypot(var1 - (var19 + var13 * var23), var3 - (var25 + var13 * var27));
                  if (!(var17 > 25.0)) {
                     double var29 = Math.atan2(var27, var23);
                     int var33 = var35.getOneway();
                     double var15 = Math.PI;
                     if (var33 < 0) {
                        var13 = Math.PI;
                     } else {
                        var13 = 0.0;
                     }

                     var13 = var29 + var13;
                     if (var35.getOneway() == 0) {
                        var13 -= var5;
                     } else {
                        var13 -= var5;
                        var15 = Math.PI * 2;
                     }

                     var13 = 15.0 * Math.abs(Math.IEEEremainder(var13, var15)) + var17;
                     if (var13 < var11) {
                        double var42 = ((var3 - var25) * var23 - (var1 - var19) * var27) / Math.sqrt(var21);
                        var34 = var35;
                        var9 = var17;
                        var11 = var13;
                        var7 = var42;
                     }
                  }
               }
            }
         }
      }

      Triple var43;
      if (var34 != null) {
         var43 = new Triple<>(var34, var9, var7);
      } else {
         var43 = null;
      }

      return var43;
   }

   public final OsmRoads.Match matchSeg(double var1, double var3, double var5) {
      OsmRoads.Match var27 = null;
      double var7 = Double.MAX_VALUE;

      for (OsmRoads.Way var29 : this.near(var1, var3, 30.0)) {
         if (!Intrinsics.areEqual(var29.getKind(), "sidewalk")) {
            int var26 = 0;

            for (int var25 = var29.getE().length - 1; var26 < var25; var26++) {
               double var13 = var29.getE()[var26];
               double var17 = var29.getN()[var26];
               double var11 = var29.getE()[var26 + 1] - var13;
               double var15 = var29.getN()[var26 + 1] - var17;
               double var19 = var11 * var11 + var15 * var15;
               if (!(var19 < 0.01)) {
                  double var9 = RangesKt.coerceIn(((var1 - var13) * var11 + (var3 - var17) * var15) / var19, 0.0, 1.0);
                  double var21 = Math.hypot(var1 - (var9 * var11 + var13), var3 - (var17 + var9 * var15));
                  if (!(var21 > 25.0)) {
                     double var23 = Math.atan2(var15, var11);
                     if (var29.getOneway() < 0) {
                        var9 = Math.PI;
                     } else {
                        var9 = 0.0;
                     }

                     var9 = var23 + var9;
                     if (var29.getOneway() == 0) {
                        var9 = Math.IEEEremainder(var9 - var5, Math.PI);
                     } else {
                        var9 = Math.IEEEremainder(var9 - var5, Math.PI * 2);
                     }

                     var9 = var21 + 15.0 * Math.abs(var9);
                     if (var9 < var7) {
                        var7 = var9;
                        var27 = new OsmRoads.Match(var29, var21, ((var3 - var17) * var11 - (var1 - var13) * var15) / Math.sqrt(var19), Math.atan2(var15, var11));
                     }
                  }
               }
            }
         }
      }

      return var27;
   }

   public final Set<String> nameTokens(String var1) {
      Intrinsics.checkNotNullParameter(var1, "name");
      var1 = var1.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
      CharSequence var8 = var1;
      CharSequence var9 = new Regex("[^a-z0-9 ]").replace(var8, " ");
      boolean var3 = true;
      Iterable var4 = StringsKt.split$default(var9, new char[]{' '}, false, 0, 6, null);
      var var10 = new ArrayList();

      for (Object var5 : var4) {
         if (!StringsKt.isBlank((String & CharSequence)var5)) {
            var10.add(var5);
         }
      }

      List var11 = var10;
      Iterable var12 = var11;
      var var20 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var12, 10));

      for (String var13 : var12) {
         String var18 = ABBR.get(var13);
         if (var18 != null) {
            var13 = var18;
         }

         var20.add(var13);
      }

      List var14 = var20;
      Set var15 = CollectionsKt.toSet(var14);
      Set var19 = SetsKt.minus(var15, DIRS);
      if (var19.size() < 2) {
         if (var19.size() != 1) {
            return var15;
         }

         CharSequence var21 = CollectionsKt.first(var19);
         int var2 = 0;

         while (true) {
            if (var2 >= var21.length()) {
               var16 = false;
               break;
            }

            if (Character.isDigit(var21.charAt(var2))) {
               var16 = var3;
               break;
            }

            var2++;
         }

         if (!var16) {
            return var15;
         }
      }

      return var19;
   }

   public final List<OsmRoads.Way> near(double var1, double var3, double var5) {
      ArrayList var7 = new ArrayList();
      HashSet var8 = new HashSet();

      for (Object var10 : ways.values()) {
         Intrinsics.checkNotNullExpressionValue(var10, "next(...)");
         near$take(var1, var5, var3, var8, var7, (OsmRoads.Way)var10);
      }

      for (Object var12 : boxWays.values()) {
         Intrinsics.checkNotNullExpressionValue(var12, "next(...)");
         var12 = ((List)var12).iterator();

         while (var12.hasNext()) {
            near$take(var1, var5, var3, var8, var7, (OsmRoads.Way)var12.next());
         }
      }

      return var7;
   }

   public final void onFix(double var1, double var3, float var5, float var6, float var7, float var8, long var9, long var11, boolean var13) {
      if (enabled) {
         long var18 = System.nanoTime();
         if (var13) {
            rtkFixNs = var18;
         } else if (var18 - rtkFixNs < 1000000000L) {
            return;
         }

         var13 = Double.isInfinite(var1);
         boolean var17 = true;
         boolean var16;
         if (!var13 && !Double.isNaN(var1)) {
            var16 = true;
         } else {
            var16 = false;
         }

         if (var16) {
            if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
               var16 = true;
            } else {
               var16 = false;
            }

            if (var16) {
               if (var1 == 0.0) {
                  var16 = true;
               } else {
                  var16 = false;
               }

               if (var16) {
                  if (var3 == 0.0) {
                     var16 = true;
                  } else {
                     var16 = false;
                  }

                  if (var16) {
                     return;
                  }
               }

               if (Double.isNaN(lat0)) {
                  this.setOrigin(var1, var3);
               }

               double var14;
               if (var9 > 0L) {
                  var14 = (System.currentTimeMillis() - var9) / 1000.0;
               } else {
                  var14 = Double.NaN;
               }

               var9 = System.nanoTime();
               if (-0.5 <= var14 && var14 <= 3.0) {
                  var16 = var17;
               } else {
                  var16 = false;
               }

               if (var16) {
                  var14 = RangesKt.coerceAtLeast(var14, 0.0);
               } else {
                  var14 = Double.NaN;
               }

               OsmRoads.Fix var20 = new OsmRoads.Fix(var1, var3, var5, var6, var7, var9, var8, var14, var11);
               lastFix = var20;
               fixes.add(var20);

               while (fixes.size() > 50) {
                  fixes.poll();
               }

               var9 = System.currentTimeMillis();
               if (var9 - lastEnsureMs < 2000L) {
                  return;
               }

               lastEnsureMs = var9;

               try {
                  this.ensureTiles(var1, var3);
               } catch (Exception var21) {
                  Log.e("StarView/Osm", "tiles", var21);
                  tileStatus = StringsKt.take("map error: " + var21, 80);
               }

               return;
            }
         }
      }
   }

   public final int parse$app_debug(String var1) {
      Intrinsics.checkNotNullParameter(var1, "text");
      JSONArray var21 = new JSONObject(var1).optJSONArray("elements");
      if (var21 == null) {
         return 0;
      } else {
         int var9 = 0;
         int var8 = 0;

         for (int var7 = var21.length(); var8 < var7; var8++) {
            JSONObject var15 = var21.optJSONObject(var8);
            if (var15 != null && Intrinsics.areEqual(var15.optString("type"), "way")) {
               long var12 = var15.optLong("id");
               if (!ways.containsKey(var12)) {
                  JSONObject var17 = var15.optJSONObject("tags");
                  if (var17 != null) {
                     String var16 = var17.optString("highway");
                     String var14 = var16;
                     if (Intrinsics.areEqual(var16, "footway")) {
                        var14 = var16;
                        if (Intrinsics.areEqual(var17.optString("footway"), "sidewalk")) {
                           var14 = "sidewalk";
                        }
                     }

                     if (KINDS.contains(var14) || Intrinsics.areEqual(var14, "sidewalk")) {
                        var16 = var17.optString("service");
                        if (!Intrinsics.areEqual(var14, "service")
                           || !SetsKt.setOf(new String[]{"driveway", "parking_aisle", "drive-through", "emergency_access"}).contains(var16)) {
                           JSONArray var19 = var15.optJSONArray("geometry");
                           if (var19 != null && var19.length() >= 2) {
                              double[] var28 = new double[var19.length()];
                              double[] var18 = new double[var19.length()];
                              int var11 = 0;

                              for (int var10 = var19.length(); var11 < var10; var11++) {
                                 JSONObject var20 = var19.getJSONObject(var11);
                                 Pair var32 = this.toLocal(var20.getDouble("lat"), var20.getDouble("lon"));
                                 double var2 = ((Number)var32.component1()).doubleValue();
                                 double var4 = ((Number)var32.component2()).doubleValue();
                                 var28[var11] = var2;
                                 var18[var11] = var4;
                              }

                              label100: {
                                 label99: {
                                    label98: {
                                       String var25 = var17.optString("oneway");
                                       if (var25 != null) {
                                          switch (var25.hashCode()) {
                                             case 49:
                                                if (var25.equals("1")) {
                                                   break label98;
                                                }
                                                break;
                                             case 1444:
                                                if (var25.equals("-1")) {
                                                   break label99;
                                                }
                                                break;
                                             case 119527:
                                                if (var25.equals("yes")) {
                                                   break label98;
                                                }
                                                break;
                                             case 3569038:
                                                if (var25.equals("true")) {
                                                   break label98;
                                                }
                                                break;
                                             case 1099846370:
                                                if (var25.equals("reverse")) {
                                                   break label99;
                                                }
                                          }
                                       }

                                       if (!Intrinsics.areEqual(var14, "motorway") && !Intrinsics.areEqual(var17.optString("junction"), "roundabout")) {
                                          var22 = 0;
                                          break label100;
                                       }
                                    }

                                    var22 = 1;
                                    break label100;
                                 }

                                 var22 = -1;
                              }

                              Map var31 = ways;
                              String var26 = var17.optString("name", var17.optString("ref", ""));
                              Intrinsics.checkNotNullExpressionValue(var26, "optString(...)");
                              Intrinsics.checkNotNull(var14);
                              float var6 = this.widthOf(var14, var22, var17);
                              String var29 = var17.optString("lanes");
                              Intrinsics.checkNotNullExpressionValue(var29, "optString(...)");
                              Integer var30 = StringsKt.toIntOrNull(var29);
                              if (var30 != null) {
                                 var11 = var30;
                              } else {
                                 var11 = 0;
                              }

                              var31.put(var12, new OsmRoads.Way(var12, var26, var14, var22, var6, var28, var18, var11));
                              var9++;
                              var7 = var7;
                           }
                        }
                     }
                  }
               }
            }
         }

         return var9;
      }
   }

   public final boolean sameStreet(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "a");
      Intrinsics.checkNotNullParameter(var2, "b");
      Set var4 = this.nameTokens(var1);
      Set var5 = this.nameTokens(var2);
      boolean var3;
      if (var4.isEmpty() || !Intrinsics.areEqual(var4, var5) && (var4.size() < 2 || !var5.containsAll(var4)) && (var5.size() < 2 || !var4.containsAll(var5))) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   public final void setEnabled(boolean var1) {
      enabled = var1;
   }

   public final void setGpsSrc(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      gpsSrc = var1;
   }

   public final void setMatchStatus(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      matchStatus = var1;
   }

   public final void setOrigin(double var1, double var3) {
      lon0 = var3;
      kx = Math.cos(Math.toRadians(var1)) * 111320.0;
      lat0 = var1;
   }

   public final Pair<OsmRoads.Way, Double> streetHeading(double var1, double var3, Double var5) {
      OsmRoads.Way var22 = null;
      double var8 = 15.0;
      double var6 = 0.0;

      for (OsmRoads.Way var23 : this.near(var1, var3, 20.0)) {
         if (!Intrinsics.areEqual(var23.getKind(), "sidewalk")) {
            int var21 = 0;

            for (int var20 = var23.getE().length - 1; var21 < var20; var21++) {
               double var10 = var23.getE()[var21];
               double var16 = var23.getN()[var21];
               double var12 = var23.getE()[var21 + 1] - var10;
               double var14 = var23.getN()[var21 + 1] - var16;
               double var18 = var12 * var12 + var14 * var14;
               if (!(var18 < 0.01)) {
                  var18 = RangesKt.coerceIn(((var1 - var10) * var12 + (var3 - var16) * var14) / var18, 0.0, 1.0);
                  var10 = Math.hypot(var1 - (var10 + var18 * var12), var3 - (var16 + var18 * var14));
                  if (var10 < var8) {
                     var22 = var23;
                     var10 = Math.atan2(var14, var12);
                     if (var23.getOneway() < 0) {
                        var8 = Math.PI;
                     } else {
                        var8 = 0.0;
                     }

                     double var30 = var10 + var8;
                     var8 = var10;
                     var6 = var30;
                  }
               }
            }
         }
      }

      if (var22 == null) {
         return null;
      } else {
         var1 = var6;
         if (var22.getOneway() == 0) {
            var1 = var6;
            if (var5 != null) {
               var1 = var6;
               if (Math.abs(Math.IEEEremainder(var6 - var5, Math.PI * 2)) > Math.PI / 2) {
                  var1 = var6 + Math.PI;
               }
            }
         }

         return new Pair<>(var22, var1);
      }
   }

   public final Pair<Double, Double> toLatLon(double var1, double var3) {
      return new Pair<>(lat0 + var3 / 110540.0, lon0 + var1 / kx);
   }

   public final Pair<Double, Double> toLocal(double var1, double var3) {
      return new Pair<>((var3 - lon0) * kx, (var1 - lat0) * 110540.0);
   }

   public final Pair<Double, Double> turn(double var1, double var3, double var5) {
      double var7 = Math.cos(var5);
      var5 = Math.sin(var5);
      var3 = -var3;
      return new Pair<>(var7 * var1 - var5 * var3, -(var5 * var1 + var7 * var3));
   }

   public final float widthOf(String var1, int var2, int var3, Float var4, String var5) {
      Intrinsics.checkNotNullParameter(var1, "kind");
      Intrinsics.checkNotNullParameter(var5, "parkingTag");
      boolean var8 = true;
      if (var4 != null) {
         float var6 = var4.floatValue();
         boolean var7;
         if (2.0F <= var6 && var6 <= 40.0F) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            return var6;
         }
      }

      byte var13;
      label109: {
         byte var9 = 2;
         boolean var10 = SetsKt.setOf(new String[]{"residential", "tertiary", "secondary", "primary", "unclassified", "living_street"}).contains(var1);
         if (!Intrinsics.areEqual(var5, "no")) {
            boolean var12;
            if (var5.length() > 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            if (var12 || var10) {
               var13 = var9;
               break label109;
            }
         }

         var13 = 0;
      }

      if (1 > var3 || var3 >= 11) {
         var8 = false;
      }

      if (var8) {
         return var3 * 3.2F + var13 * 2.3F;
      } else {
         switch (var1.hashCode()) {
            case -2014090663:
               if (var1.equals("living_street")) {
                  return 7.0F;
               }

               return 6.5F;
            case -1174796206:
               if (var1.equals("tertiary")) {
                  return 12.0F;
               }

               return 6.5F;
            case -817598092:
               if (var1.equals("secondary")) {
                  return 13.0F;
               }

               return 6.5F;
            case -314765822:
               if (var1.equals("primary")) {
                  return 15.0F;
               }

               return 6.5F;
            case -151535014:
               if (var1.equals("motorway")) {
                  return 11.0F;
               }

               return 6.5F;
            case 110640564:
               if (var1.equals("trunk")) {
                  return 14.0F;
               }

               return 6.5F;
            case 218164128:
               if (var1.equals("sidewalk")) {
                  return 0.0F;
               }

               return 6.5F;
            case 1098352388:
               if (!var1.equals("residential")) {
                  return 6.5F;
               }
               break;
            case 1840151916:
               if (!var1.equals("unclassified")) {
                  return 6.5F;
               }
               break;
            case 1984153269:
               if (var1.equals("service")) {
                  return 5.0F;
               }

               return 6.5F;
            default:
               return 6.5F;
         }

         float var11;
         if (var2 != 0) {
            var11 = 9.0F;
         } else {
            var11 = 10.0F;
         }

         return var11;
      }
   }

   public final float widthOf(String var1, int var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var1, "kind");
      Intrinsics.checkNotNullParameter(var3, "tags");
      String var5 = var3.optString("lanes");
      Intrinsics.checkNotNullExpressionValue(var5, "optString(...)");
      Integer var7 = StringsKt.toIntOrNull(var5);
      int var4;
      if (var7 != null) {
         var4 = var7;
      } else {
         var4 = 0;
      }

      var5 = var3.optString("width");
      Intrinsics.checkNotNullExpressionValue(var5, "optString(...)");
      Float var9 = StringsKt.toFloatOrNull(StringsKt.trim(StringsKt.replace$default(var5, "m", "", false, 4, null)).toString());
      String var6 = var3.optString("parking:both", var3.optString("parking:lane:both", ""));
      Intrinsics.checkNotNullExpressionValue(var6, "optString(...)");
      return this.widthOf(var1, var2, var4, var9, var6);
   }

   public static final class Align {
      private volatile String guess;
      private int jumps;
      private double lagResid;
      private long lastSeenNs;
      private boolean ok;
      private final ArrayDeque<OsmRoads.Align.P> pts = new ArrayDeque<>();
      private volatile double rejectMiss;
      private volatile long rejectSince;
      private int rejected;
      private double resid;
      private double spread;
      private double th;
      private double tx;
      private double ty;

      public Align() {
         this.lagResid = Double.NaN;
         this.guess = "";
      }

      // $VF: synthetic method
      public static void add$default(
         OsmRoads.Align var0, double var1, double var3, double var5, OsmRoads.Fix var7, long var8, double var10, double var12, int var14, Object var15
      ) {
         if ((var14 & 32) != 0) {
            var10 = 0.0;
         }

         if ((var14 & 64) != 0) {
            var12 = 0.0;
         }

         var0.add(var1, var3, var5, var7, var8, var10, var12);
      }

      // $VF: synthetic method
      public static Pair apply$default(OsmRoads.Align var0, double var1, double var3, double var5, double var7, double var9, int var11, Object var12) {
         if ((var11 & 4) != 0) {
            var5 = var0.th;
         }

         if ((var11 & 8) != 0) {
            var7 = var0.tx;
         }

         if ((var11 & 16) != 0) {
            var9 = var0.ty;
         }

         return var0.apply(var1, var3, var5, var7, var9);
      }

      private final boolean getGuessing() {
         boolean var1;
         if (this.guess.length() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public final void add(double var1, double var3, double var5, OsmRoads.Fix var7, long var8, double var10, double var12) {
         Intrinsics.checkNotNullParameter(var7, "f");
         this.lagResid = Double.NaN;
         long var45 = this.lastSeenNs;
         boolean var43 = false;
         boolean var41;
         if (var45 != 0L && var8 - this.lastSeenNs > 30000000000L) {
            var41 = true;
         } else {
            var41 = false;
         }

         this.lastSeenNs = var8;
         if (!Double.isNaN(OsmRoads.INSTANCE.getLat0()) && var7.getHAcc() > 0.0F && !(var7.getHAcc() > 30.0F)) {
            Pair var47 = OsmRoads.INSTANCE.toLocal(var7.getLat(), var7.getLon());
            double var18 = ((Number)var47.component1()).doubleValue();
            double var20 = ((Number)var47.component2()).doubleValue();
            double var14 = RangesKt.coerceAtLeast(var7.getHAcc() * var7.getHAcc(), 1.0F);
            double var16 = 1.0;
            double var22 = 1.0 / var14;
            if (this.pts.isEmpty()) {
               var14 = Double.MAX_VALUE;
            } else {
               var14 = Math.hypot(var1 - this.pts.last().getOx(), var3 - this.pts.last().getOy());
            }

            if (!this.ok || !(var14 <= 0.5)) {
               boolean var42;
               if (0.05 <= var14 && var14 <= 0.5) {
                  var42 = true;
               } else {
                  var42 = false;
               }

               if (!var42) {
                  if (this.ok && !this.getGuessing()) {
                     var47 = apply$default(this, var1, var3, 0.0, 0.0, 0.0, 28, null);
                     double var24 = Math.hypot(var18 - ((Number)var47.component1()).doubleValue(), var20 - ((Number)var47.component2()).doubleValue());
                     if (var24 > Math.max(10.0, var7.getHAcc() * 3.0)) {
                        if (this.rejectSince == 0L) {
                           this.rejectSince = var8;
                        }

                        this.rejectMiss = var24;
                        this.rejected++;
                        if (!var41 && var8 - this.rejectSince < 10000000000L) {
                           return;
                        }

                        this.pts.clear();
                        this.rejectSince = 0L;
                        this.tx = var18 - (Math.cos(this.th) * var1 - Math.sin(this.th) * var3);
                        this.ty = var20 - (Math.sin(this.th) * var1 + Math.cos(this.th) * var3);
                        this.jumps++;
                        this.pts.addLast(new OsmRoads.Align.P(var1, var3, var18, var20, var22, var8, 0.0, 0.0, 192, null));
                        this.spread = 0.0;
                        this.resid = var7.getHAcc();
                        return;
                     }

                     this.rejectSince = 0L;
                  }

                  while (!this.pts.isEmpty() && (var8 - this.pts.first().getTNs() > 25000000000L || this.pts.size() >= 400)) {
                     this.pts.removeFirst();
                  }

                  if (!this.pts.isEmpty() && !(var14 > 0.5)) {
                     this.pts.set(this.pts.size() - 1, new OsmRoads.Align.P(var1, var3, var18, var20, var22, var8, var10, var12));
                  } else {
                     this.pts.addLast(new OsmRoads.Align.P(var1, var3, var18, var20, var22, var8, var10, var12));
                  }

                  var10 = this.th;
                  OsmRoads.Align.P var109 = this.pts.first();
                  OsmRoads.Align.P var48 = this.pts.last();
                  this.spread = Math.hypot(var48.getOx() - var109.getOx(), var48.getOy() - var109.getOy());
                  if (this.spread >= 25.0 && this.pts.size() >= 8) {
                     var12 = 0.0;
                     var3 = 0.0;
                     var1 = 0.0;
                     Iterator var64 = this.pts.iterator();
                     var10 = 0.0;
                     var5 = 0.0;

                     while (var64.hasNext()) {
                        OsmRoads.Align.P var112 = (OsmRoads.Align.P)var64.next();
                        var5 += var112.getW();
                        var12 += var112.getW() * var112.getOx();
                        var10 += var112.getW() * var112.getOy();
                        var3 += var112.getW() * var112.getE();
                        var1 += var112.getW() * var112.getN();
                     }

                     var18 = var12 / var5;
                     var10 /= var5;
                     var12 = var3 / var5;
                     var5 = var1 / var5;
                     Iterator var113 = this.pts.iterator();
                     var1 = 0.0;
                     var3 = 0.0;

                     while (var113.hasNext()) {
                        OsmRoads.Align.P var65 = (OsmRoads.Align.P)var113.next();
                        var14 = var65.getOx() - var18;
                        var22 = var65.getOy() - var10;
                        var20 = var65.getE() - var12;
                        double var94 = var65.getN() - var5;
                        var3 += var65.getW() * (var14 * var20 + var22 * var94);
                        var1 += var65.getW() * (var14 * var94 - var22 * var20);
                     }

                     var14 = Math.atan2(var1, var3);
                     this.guess = "";
                     var12 -= Math.cos(var14) * var18 - Math.sin(var14) * var10;
                     var18 = var5 - (Math.sin(var14) * var18 + Math.cos(var14) * var10);
                     Iterator var114 = this.pts.iterator();
                     var5 = 0.0;

                     while (var114.hasNext()) {
                        var48 = (OsmRoads.Align.P)var114.next();
                        Pair var66 = this.apply(var48.getOx(), var48.getOy(), var14, var12, var18);
                        var10 = ((Number)var66.component1()).doubleValue();
                        var20 = ((Number)var66.component2()).doubleValue();
                        var5 += Math.hypot(var10 - var48.getE(), var20 - var48.getN());
                     }

                     this.resid = var5 / this.pts.size();
                     OsmRoads.Align var115 = this;
                     var3 = 0.0;
                     var10 = 0.0;
                     Iterator var67 = var115.pts.iterator();
                     var1 = var10;

                     while (var67.hasNext()) {
                        var48 = (OsmRoads.Align.P)var67.next();
                        var10 = Math.hypot(var48.getVx(), var48.getVy());
                        var3 += var48.getW();
                        var1 += var48.getW() * var10;
                     }

                     double var95 = var1 / var3;
                     var10 = 0.0;
                     var5 = 0.0;
                     var22 = Math.cos(var14);
                     var20 = Math.sin(var14);

                     for (OsmRoads.Align.P var49 : var115.pts) {
                        double var26 = Math.hypot(var49.getVx(), var49.getVy());
                        if (!(var26 < 0.5)) {
                           double var36 = (var49.getVx() * var22 - var49.getVy() * var20) / var26;
                           double var32 = (var49.getVx() * var20 + var49.getVy() * var22) / var26;
                           Pair var119 = var115.apply(var49.getOx(), var49.getOy(), var14, var12, var18);
                           double var28 = ((Number)var119.component1()).doubleValue();
                           double var30 = ((Number)var119.component2()).doubleValue();
                           double var34 = var49.getE();
                           double var38 = var49.getN();
                           var10 += var49.getW() * ((var34 - var28) * var36 + (var38 - var30) * var32) * (var26 - var95);
                           var5 += var49.getW() * (var26 - var95) * (var26 - var95);
                        }
                     }

                     if (var5 > 2.0 * var3) {
                        var115.lagResid = RangesKt.coerceIn(-var10 / var5, -1.0, 1.0);
                     }

                     Unit var69 = Unit.INSTANCE;
                     var10 = var14;
                     var3 = var12;
                     var1 = var18;
                  } else {
                     float var40 = var7.getBearingDeg();
                     if (!Float.isInfinite(var40) && !Float.isNaN(var40)) {
                        var41 = true;
                     } else {
                        var41 = false;
                     }

                     label238: {
                        label292: {
                           if (var41) {
                              if (var7.getSpeed() > 3.0F) {
                                 break label292;
                              }

                              var40 = var7.getBearingAcc();
                              if (!Float.isInfinite(var40) && !Float.isNaN(var40)) {
                                 var41 = true;
                              } else {
                                 var41 = false;
                              }

                              if (var41) {
                                 var40 = var7.getBearingAcc();
                                 if (0.0F <= var40 && var40 <= 25.0F) {
                                    var41 = true;
                                 } else {
                                    var41 = false;
                                 }

                                 if (var41) {
                                    break label292;
                                 }
                              }
                           }

                           var41 = false;
                           break label238;
                        }

                        var41 = true;
                     }

                     boolean var44 = this.getGuessing();
                     this.guess = "";
                     if (var41) {
                        var10 = Math.toRadians(90.0 - var7.getBearingDeg()) - var5;
                     } else if (!this.ok || var44) {
                        var40 = var7.getBearingDeg();
                        if (!Float.isInfinite(var40) && !Float.isNaN(var40)) {
                           var41 = true;
                        } else {
                           var41 = false;
                        }

                        label205: {
                           label287: {
                              if (var41) {
                                 var40 = var7.getBearingAcc();
                                 if (!Float.isInfinite(var40) && !Float.isNaN(var40)) {
                                    var41 = true;
                                 } else {
                                    var41 = false;
                                 }

                                 if (var41 && var7.getBearingAcc() < 90.0F) {
                                    var10 = Math.toRadians(90.0 - var7.getBearingDeg());
                                    break label287;
                                 }
                              }

                              if (!this.ok) {
                                 var110 = null;
                                 break label205;
                              }

                              var10 = this.th + var5;
                           }

                           var110 = var10;
                        }

                        Pair var116 = OsmRoads.INSTANCE.streetHeading(var18, var20, var110);
                        if (var116 == null) {
                           return;
                        }

                        var10 = ((Number)var116.getSecond()).doubleValue() - var5;
                        var var111 = ((OsmRoads.Way)var116.getFirst()).getName();
                        if (var111.length() == 0) {
                           var41 = true;
                        } else {
                           var41 = false;
                        }

                        if (var41) {
                           var111 = ((OsmRoads.Way)var116.getFirst()).getKind();
                        }

                        this.guess = var111;
                     }

                     var12 = Math.cos(var10);
                     var14 = Math.sin(var10);
                     var5 = var20 - (Math.sin(var10) * var1 + Math.cos(var10) * var3);
                     this.resid = var7.getHAcc();
                     var3 = var18 - (var12 * var1 - var14 * var3);
                     var1 = var5;
                  }

                  if (!this.ok) {
                     this.th = var10;
                     this.tx = var3;
                     this.ty = var1;
                     this.ok = true;
                     this.jumps++;
                  } else {
                     label156: {
                        var10 = Math.IEEEremainder(var10 - this.th, Math.PI * 2);
                        if (!(Math.abs(var10) > 0.5)) {
                           var41 = var43;
                           if (!(Math.hypot(var3 - this.tx, var1 - this.ty) > 40.0)) {
                              break label156;
                           }
                        }

                        var41 = true;
                     }

                     if (var41) {
                        var5 = var16;
                     } else {
                        var5 = 0.05;
                     }

                     if (var41) {
                        this.jumps++;
                     }

                     this.th += var5 * var10;
                     this.tx = this.tx + (var3 - this.tx) * var5;
                     this.ty = this.ty + (var1 - this.ty) * var5;
                  }
               }
            }
         }
      }

      public final Pair<Double, Double> apply(double var1, double var3, double var5, double var7, double var9) {
         return new Pair<>(Math.cos(var5) * var1 - Math.sin(var5) * var3 + var7, Math.sin(var5) * var1 + Math.cos(var5) * var3 + var9);
      }

      public final String getGuess() {
         return this.guess;
      }

      public final int getJumps() {
         return this.jumps;
      }

      public final double getLagResid() {
         return this.lagResid;
      }

      public final boolean getOk() {
         return this.ok;
      }

      public final double getRejectMiss() {
         return this.rejectMiss;
      }

      public final long getRejectSince() {
         return this.rejectSince;
      }

      public final int getRejected() {
         return this.rejected;
      }

      public final double getResid() {
         return this.resid;
      }

      public final double getSpread() {
         return this.spread;
      }

      public final double getTh() {
         return this.th;
      }

      public final double getTx() {
         return this.tx;
      }

      public final double getTy() {
         return this.ty;
      }

      public final Pair<Double, Double> inverse(double var1, double var3) {
         var1 -= this.tx;
         var3 -= this.ty;
         return new Pair<>(Math.cos(this.th) * var1 + Math.sin(this.th) * var3, -Math.sin(this.th) * var1 + Math.cos(this.th) * var3);
      }

      public final void reset() {
         this.pts.clear();
         this.ok = false;
         this.rejectSince = 0L;
      }

      private static final class P {
         private final double e;
         private final double n;
         private final double ox;
         private final double oy;
         private final long tNs;
         private final double vx;
         private final double vy;
         private final double w;

         public P(double var1, double var3, double var5, double var7, double var9, long var11, double var13, double var15) {
            this.ox = var1;
            this.oy = var3;
            this.e = var5;
            this.n = var7;
            this.w = var9;
            this.tNs = var11;
            this.vx = var13;
            this.vy = var15;
         }

         // $VF: synthetic method
         public P(
            double var1, double var3, double var5, double var7, double var9, long var11, double var13, double var15, int var17, DefaultConstructorMarker var18
         ) {
            if ((var17 & 64) != 0) {
               var13 = 0.0;
            }

            if ((var17 & 128) != 0) {
               var15 = 0.0;
            }

            this(var1, var3, var5, var7, var9, var11, var13, var15);
         }

         public final double getE() {
            return this.e;
         }

         public final double getN() {
            return this.n;
         }

         public final double getOx() {
            return this.ox;
         }

         public final double getOy() {
            return this.oy;
         }

         public final long getTNs() {
            return this.tNs;
         }

         public final double getVx() {
            return this.vx;
         }

         public final double getVy() {
            return this.vy;
         }

         public final double getW() {
            return this.w;
         }
      }
   }

   public static final class Fix {
      private final double ageS;
      private final float bearingAcc;
      private final float bearingDeg;
      private final float hAcc;
      private final double lat;
      private final double lon;
      private final long monoNs;
      private final float speed;
      private final long tNs;

      public Fix(double var1, double var3, float var5, float var6, float var7, long var8, float var10, double var11, long var13) {
         this.lat = var1;
         this.lon = var3;
         this.bearingDeg = var5;
         this.speed = var6;
         this.hAcc = var7;
         this.tNs = var8;
         this.bearingAcc = var10;
         this.ageS = var11;
         this.monoNs = var13;
      }

      // $VF: synthetic method
      public Fix(
         double var1,
         double var3,
         float var5,
         float var6,
         float var7,
         long var8,
         float var10,
         double var11,
         long var13,
         int var15,
         DefaultConstructorMarker var16
      ) {
         if ((var15 & 64) != 0) {
            var10 = Float.NaN;
         }

         if ((var15 & 128) != 0) {
            var11 = Double.NaN;
         }

         if ((var15 & 256) != 0) {
            var13 = 0L;
         }

         this(var1, var3, var5, var6, var7, var8, var10, var11, var13);
      }

      public final double getAgeS() {
         return this.ageS;
      }

      public final float getBearingAcc() {
         return this.bearingAcc;
      }

      public final float getBearingDeg() {
         return this.bearingDeg;
      }

      public final float getHAcc() {
         return this.hAcc;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLon() {
         return this.lon;
      }

      public final long getMonoNs() {
         return this.monoNs;
      }

      public final float getSpeed() {
         return this.speed;
      }

      public final long getTNs() {
         return this.tNs;
      }
   }

   public static final class Match {
      private final double dir;
      private final double dist;
      private final double signedLeft;
      private final OsmRoads.Way way;

      public Match(OsmRoads.Way var1, double var2, double var4, double var6) {
         Intrinsics.checkNotNullParameter(var1, "way");
         super();
         this.way = var1;
         this.dist = var2;
         this.signedLeft = var4;
         this.dir = var6;
      }

      public final double getDir() {
         return this.dir;
      }

      public final double getDist() {
         return this.dist;
      }

      public final double getSignedLeft() {
         return this.signedLeft;
      }

      public final OsmRoads.Way getWay() {
         return this.way;
      }

      public final double rightOf(double var1) {
         if (Math.cos(this.dir - var1) >= 0.0) {
            var1 = -this.signedLeft;
         } else {
            var1 = this.signedLeft;
         }

         return var1;
      }
   }

   public static final class Piece {
      private final OsmRoads.Way way;
      private final double[] x;
      private final double[] y;

      public Piece(OsmRoads.Way var1, double[] var2, double[] var3) {
         Intrinsics.checkNotNullParameter(var1, "way");
         Intrinsics.checkNotNullParameter(var2, "x");
         Intrinsics.checkNotNullParameter(var3, "y");
         super();
         this.way = var1;
         this.x = var2;
         this.y = var3;
      }

      public final OsmRoads.Way getWay() {
         return this.way;
      }

      public final double[] getX() {
         return this.x;
      }

      public final double[] getY() {
         return this.y;
      }
   }

   public static final class Way {
      private final double[] e;
      private final long id;
      private final String kind;
      private final int lanes;
      private final double maxE;
      private final double maxN;
      private final double minE;
      private final double minN;
      private final double[] n;
      private final String name;
      private final int oneway;
      private final float width;

      public Way(long var1, String var3, String var4, int var5, float var6, double[] var7, double[] var8, int var9) {
         Intrinsics.checkNotNullParameter(var3, "name");
         Intrinsics.checkNotNullParameter(var4, "kind");
         Intrinsics.checkNotNullParameter(var7, "e");
         Intrinsics.checkNotNullParameter(var8, "n");
         super();
         this.id = var1;
         this.name = var3;
         this.kind = var4;
         this.oneway = var5;
         this.width = var6;
         this.e = var7;
         this.n = var8;
         this.lanes = var9;
         this.minE = ArraysKt.minOrThrow(this.e);
         this.maxE = ArraysKt.maxOrThrow(this.e);
         this.minN = ArraysKt.minOrThrow(this.n);
         this.maxN = ArraysKt.maxOrThrow(this.n);
      }

      // $VF: synthetic method
      public Way(long var1, String var3, String var4, int var5, float var6, double[] var7, double[] var8, int var9, int var10, DefaultConstructorMarker var11) {
         if ((var10 & 128) != 0) {
            var9 = 0;
         }

         this(var1, var3, var4, var5, var6, var7, var8, var9);
      }

      public final double[] getE() {
         return this.e;
      }

      public final boolean getHighway() {
         String var2 = this.kind;
         boolean var1 = false;
         if (StringsKt.startsWith$default(var2, "motorway", false, 2, null) || StringsKt.startsWith$default(this.kind, "trunk", false, 2, null)) {
            var1 = true;
         }

         return var1;
      }

      public final long getId() {
         return this.id;
      }

      public final String getKind() {
         return this.kind;
      }

      public final int getLaneCount() {
         int var4 = this.lanes;
         int var3 = 1;
         if (var4 > 0) {
            if (this.oneway == 0) {
               var3 = Math.max(1, this.lanes / 2);
            } else {
               var3 = this.lanes;
            }
         } else if (this.getHighway()) {
            float var2 = this.width;
            float var1;
            if (this.oneway == 0) {
               var1 = 6.8F;
            } else {
               var1 = 3.4F;
            }

            var3 = Math.max(1, Math.round(var2 / var1));
         }

         return var3;
      }

      public final int getLanes() {
         return this.lanes;
      }

      public final double getLanesWidth() {
         double var1;
         if (this.getHighway()) {
            var1 = this.width;
         } else {
            var1 = this.width;
            int var4 = this.getLaneCount();
            byte var3;
            if (this.oneway == 0) {
               var3 = 2;
            } else {
               var3 = 1;
            }

            var1 = Math.min(var1, var4 * var3 * 3.3);
         }

         return var1;
      }

      public final double getMaxE() {
         return this.maxE;
      }

      public final double getMaxN() {
         return this.maxN;
      }

      public final double getMinE() {
         return this.minE;
      }

      public final double getMinN() {
         return this.minN;
      }

      public final double[] getN() {
         return this.n;
      }

      public final String getName() {
         return this.name;
      }

      public final int getOneway() {
         return this.oneway;
      }

      public final float getWidth() {
         return this.width;
      }
   }
}
