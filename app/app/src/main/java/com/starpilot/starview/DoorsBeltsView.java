package com.starpilot.starview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class DoorsBeltsView extends CardPage {
   public static final DoorsBeltsView.Companion Companion = new DoorsBeltsView.Companion(null);
   private static DoorsBeltsView.LearnState saved;
   private final SimpleDateFormat dateFmt;
   private final SimpleDateFormat dayFmt;
   private DoorsBeltsView.Learn learn;
   private JSONObject live;
   private String liveErr;
   private LinearLayout nowCard;
   private final Runnable poll;
   private final int red;
   private final SimpleDateFormat shortFmt;
   private boolean shown;
   private JSONObject status;
   private long statusAt;
   private String statusErr;
   private final SimpleDateFormat timeFmt;

   // $VF: synthetic method
   public static Unit $r8$lambda$OuH5LaVUIQcXkgIiyBUEugi6Unc(DoorsBeltsView var0, JSONObject var1) {
      return loadStatus$lambda$2(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fvlHcflJI9TWZYKlX5RQKR98zZQ(DoorsBeltsView var0) {
      return paint$lambda$10(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vI60Wm0miJc6j59Ay3J2N9R0zI8(DoorsBeltsView var0, JSONObject var1) {
      return loadLive$lambda$3(var0, var1);
   }

   public DoorsBeltsView(Context var1, CarUi var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      super(var1, var2);
      this.dayFmt = new SimpleDateFormat("EEEE, MMM d", Locale.US);
      this.timeFmt = new SimpleDateFormat("h:mm:ss a", Locale.US);
      this.shortFmt = new SimpleDateFormat("EEE MMM d, h:mm a", Locale.US);
      this.dateFmt = new SimpleDateFormat("MMM d", Locale.US);
      this.red = Color.rgb(255, 138, 128);
      DoorsBeltsView.LearnState var3 = saved;
      if (var3 != null) {
         if (Intrinsics.areEqual(var3.getPhase(), "still")) {
            var3.setPhase("ready");
         }

         this.learn = new DoorsBeltsView.Learn(this, var3);
         this.getMain().post(new DoorsBeltsView$$ExternalSyntheticLambda3(this));
      }

      this.poll = new Runnable() {

         @Override
         public void run() {
            if (DoorsBeltsView.this.shown) {
               long var3x = System.currentTimeMillis();
               if (DoorsBeltsView.this.learn == null && var3x - DoorsBeltsView.this.statusAt > 30000L) {
                  DoorsBeltsView.this.loadStatus();
               }

               JSONObject var5 = DoorsBeltsView.this.status;
               boolean var2x = false;
               boolean var1x = var2x;
               if (var5 != null) {
                  var1x = var2x;
                  if (var5.optBoolean("carOn")) {
                     var1x = true;
                  }
               }

               if (var1x || DoorsBeltsView.this.learn != null) {
                  DoorsBeltsView.this.loadLive();
               }

               Handler var8 = DoorsBeltsView.this.getMain();
               Runnable var6 = this;
               if (DoorsBeltsView.this.learn != null) {
                  var3x = 5000L;
               } else {
                  var3x = 2000L;
               }

               var8.postDelayed(var6, var3x);
            }
         }
      };
   }

   private final View buttons(View... var1) {
      LinearLayout var6 = new LinearLayout(this.getContext());
      int var2 = 0;
      var6.setOrientation(0);
      var6.setPadding(0, this.getUi().dp(10), 0, 0);

      for (int var3 = var1.length; var2 < var3; var2++) {
         View var5 = var1[var2];
         LayoutParams var4 = new LayoutParams(-2, -2);
         var4.rightMargin = this.getUi().dp(12);
         Unit var7 = Unit.INSTANCE;
         var6.addView(var5, (android.view.ViewGroup.LayoutParams)var4);
      }

      return (View)var6;
   }

   private final void closeLearn() {
      this.learn = null;
      saved = null;
      this.statusAt = 0L;
      this.loadStatus();
      this.paint();
   }

   private final List<JSONObject> items() {
      JSONObject var1 = this.status;
      if (var1 != null) {
         JSONArray var4 = var1.optJSONArray("items");
         if (var4 != null) {
            Iterable var3 = RangesKt.until(0, var4.length());
            var var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
            Iterator var5 = var3.iterator();

            while (var5.hasNext()) {
               var2.add(var4.getJSONObject(((IntIterator)var5).nextInt()));
            }

            return var2;
         }
      }

      return CollectionsKt.emptyList();
   }

   private final String kindOf(String var1) {
      Iterator var3 = this.items().iterator();

      while (true) {
         if (var3.hasNext()) {
            Object var2 = var3.next();
            if (!Intrinsics.areEqual(((JSONObject)var2).optString("id"), var1)) {
               continue;
            }

            var4 = var2;
            break;
         }

         var4 = null;
         break;
      }

      JSONObject var5 = (JSONObject)var4;
      if (var5 != null) {
         String var7 = var5.optString("kind");
         if (var7 != null) {
            return var7;
         }
      }

      return "door";
   }

   static final void lambda$1$lambda$0(DoorsBeltsView var0) {
      DoorsBeltsView.Learn var1 = var0.learn;
      if (var1 != null) {
         var1.paint();
      }
   }

   private final void loadLive() {
      req$default(this, "live", null, new DoorsBeltsView$$ExternalSyntheticLambda1(this), 2, null);
   }

   private static final Unit loadLive$lambda$3(DoorsBeltsView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         var0.live = var1;
         var0.liveErr = null;
      } else {
         var0.live = null;
         var0.liveErr = var1.optString("error");
      }

      if (var0.learn == null) {
         var0.paintNow();
      } else {
         DoorsBeltsView.Learn var2 = var0.learn;
         if (var2 != null) {
            var2.liveTick(var1);
         }
      }

      return Unit.INSTANCE;
   }

   private final void loadStatus() {
      this.statusAt = System.currentTimeMillis();
      req$default(this, "status", null, new DoorsBeltsView$$ExternalSyntheticLambda2(this), 2, null);
   }

   private static final Unit loadStatus$lambda$2(DoorsBeltsView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      String var2;
      if (var1.optBoolean("ok")) {
         var0.status = var1;
         var2 = null;
      } else {
         var2 = var1.optString("error", "The comma didn't answer");
      }

      var0.statusErr = var2;
      if (var0.learn == null) {
         var0.paint();
      }

      return Unit.INSTANCE;
   }

   private final String nameOf(String var1) {
      Iterator var3 = this.items().iterator();

      Object var2;
      do {
         if (!var3.hasNext()) {
            var2 = null;
            break;
         }

         var2 = (JSONObject)var3.next();
      } while (!Intrinsics.areEqual(var2.optString("id"), var1));

      var2 = var2;
      if (var2 != null) {
         String var6 = var2.optString("name");
         if (var6 != null) {
            return var6;
         }
      }

      return var1;
   }

   private final void openLearn() {
      JSONObject var3 = this.status;
      boolean var2 = false;
      boolean var1 = var2;
      if (var3 != null) {
         var1 = var2;
         if (var3.optBoolean("carOn")) {
            var1 = true;
         }
      }

      if (!var1) {
         this.toast("Turn the car on and put it in Park first.");
      } else {
         DoorsBeltsView.LearnState var4 = new DoorsBeltsView.LearnState(this.items());
         saved = var4;
         DoorsBeltsView.Learn var5 = new DoorsBeltsView.Learn(this, var4);
         var5.paint();
         this.learn = var5;
      }
   }

   private final void paint() {
      this.getCol().removeAllViews();
      JSONObject var18 = this.status;
      LinearLayout var12 = this.card();
      this.add((View)var12);
      String var17 = "device";
      var12.addView(this.title("Doors & belts", "car", "device"));
      if (var18 == null) {
         CardPage var96 = this;
         String var81 = this.statusErr;
         String var68 = var81;
         if (var81 == null) {
            var68 = "Asking the comma…";
         }

         var12.addView((View)CardPage.line$default(var96, var68, 0, 0.0F, 6, null));
      } else {
         var12.addView(
            (View)CardPage.line$default(
               this,
               "The comma checks every door and seat-belt switch after each drive, from the drive's own logs, once the car is off. Nothing extra runs while driving.",
               0,
               0.0F,
               6,
               null
            )
         );
         JSONObject var11 = var18.optJSONObject("scanning");
         int var4 = var18.optInt("pending");
         CardPage var13 = this;
         String var10;
         if (var11 != null) {
            int var3 = var11.optInt("done");
            int var5 = var11.optInt("total");
            var10 = "Checking drives now: " + var3 + " of " + var5 + " minutes read";
         } else if (var4 > 0) {
            if (var4 == 1) {
               var10 = "";
            } else {
               var10 = "s";
            }

            var10 = var4 + " minute" + var10 + " of driving to check - it starts a couple of minutes after the car is off";
         } else {
            var10 = "Every drive is checked";
         }

         var12.addView((View)CardPage.line$default(var13, var10, CarUi.Companion.getTEXT(), 0.0F, 4, null));
         long var8 = var18.optLong("learnedAt", 0L);
         var var57 = this.items();
         int var30;
         if (var57 instanceof Collection && ((Collection)var57).isEmpty()) {
            var30 = 0;
         } else {
            var30 = 0;

            for (JSONObject var14 : var57) {
               boolean var40;
               if (!Intrinsics.areEqual(var14.optString("source"), "learned") && !Intrinsics.areEqual(var14.optString("source"), "standard")) {
                  var40 = false;
               } else {
                  var40 = true;
               }

               if (var40) {
                  if (++var30 < 0) {
                     CollectionsKt.throwCountOverflow();
                  }
               }
            }
         }

         var13 = this;
         if (var8 > 0L) {
            var4 = this.items().size();
            var10 = this.dateFmt.format(new Date(1000 * var8));
            var10 = "Watching " + var30 + " of " + var4 + " switches · learned " + var10;
         } else {
            var10 = "Watching the 4 doors and the driver's belt. Learn the rest once (car on, in Park, about 5 minutes).";
         }

         String var19 = "";
         String var69 = "s";
         var12.addView((View)CardPage.line$default(var13, var10, 0, 0.0F, 6, null));
         CarUi var93 = this.getUi();
         if (var8 > 0L) {
            var10 = "Learn again…";
         } else {
            var10 = "Learn…";
         }

         boolean var7;
         if (var8 == 0L) {
            var7 = true;
         } else {
            var7 = false;
         }

         var12.addView(this.buttons(var93.button(var10, var7, new DoorsBeltsView$$ExternalSyntheticLambda0(this))));
         LinearLayout var61 = this.card();
         this.add((View)var61);
         this.nowCard = var61;
         this.paintNow();
         JSONArray var62 = var18.optJSONArray("drives");
         JSONArray var94 = var62;
         if (var62 == null) {
            var94 = new JSONArray();
         }

         HashMap var15 = new HashMap();
         HashMap var16 = new HashMap();
         HashMap var20 = new HashMap();
         var4 = 0;
         int var6 = var94.length();
         var10 = var69;

         while (true) {
            String var24 = "changes";
            String var21 = "keys(...)";
            String var22 = "fast";
            if (var4 >= var6) {
               LinearLayout var25 = this.card();
               this.add((View)var25);
               var25.addView(this.title("Flickers", "pulse", "device"));
               if (var94.length() == 0) {
                  var25.addView((View)CardPage.line$default(this, "No drive checked yet.", 0, 0.0F, 6, null));
               } else if (var15.isEmpty()) {
                  CardPage var70 = this;
                  var30 = var94.length();
                  if (var94.length() == 1) {
                     var10 = "";
                  }

                  var25.addView(
                     (View)CardPage.line$default(
                        var70,
                        "No flicker in the last " + var30 + " drive" + var10 + ": no switch changed twice within 3 seconds.",
                        CarUi.Companion.getACCENT(),
                        0.0F,
                        4,
                        null
                     )
                  );
               } else {
                  Set var71 = var15.entrySet();
                  Intrinsics.checkNotNullExpressionValue(var71, "<get-entries>(...)");
                  Iterator var88 = CollectionsKt.sortedWith(
                        var71,
                        new Comparator() {
                           @Override
                           public final int compare(T var1, T var2) {
                              return ComparisonsKt.compareValues(
                                 (T)((Integer & Comparable)((Entry)var2).getValue()), (T)((Integer & Comparable)((Entry)var1).getValue())
                              );
                           }
                        }
                     )
                     .iterator();
                  HashMap var72 = var20;
                  HashMap var99 = var16;

                  while (var88.hasNext()) {
                     Entry var103 = (Entry)var88.next();
                     Intrinsics.checkNotNullExpressionValue(var103, "next(...)");
                     var103 = var103;
                     Integer var126 = (Integer)var103.getKey();
                     Intrinsics.checkNotNullExpressionValue(var126, "component1(...)");
                     var126 = (String)var126;
                     var103 = (Entry)var103.getValue();
                     Intrinsics.checkNotNullExpressionValue(var103, "component2(...)");
                     Integer var26 = (Integer)var103;
                     String var146 = this.nameOf((String)var126);
                     String var106;
                     if (var26 == 1) {
                        var106 = "";
                     } else {
                        var106 = var10;
                     }

                     Object var27 = var99.get(var126);
                     var4 = var94.length();
                     var126 = (Integer)var72.get(var126);
                     if (var126 != null) {
                        var30 = var126;
                     } else {
                        var30 = var26;
                     }

                     var25.addView(
                        (View)this.line(
                           var146
                              + ": "
                              + var26
                              + " quick change"
                              + var106
                              + " in "
                              + var27
                              + " of the last "
                              + var4
                              + " drives ("
                              + var30
                              + " changes in all)",
                           CarUi.Companion.getWARN(),
                           17.0F
                        )
                     );
                  }
               }

               LinearLayout var147 = this.card();
               this.add((View)var147);
               var147.addView(this.title("Drives", "history", "device"));
               if (var94.length() == 0) {
                  var147.addView((View)CardPage.line$default(this, "Each drive shows up here after the car is off.", 0, 0.0F, 6, null));
               }

               var4 = 0;
               var30 = Math.min(var94.length(), 20);
               String var73 = var19;
               String var101 = var24;
               String var89 = var17;
               JSONArray var111 = var94;
               var10 = var22;
               String var107 = var21;

               while (var4 < var30) {
                  JSONObject var151 = var111.getJSONObject(var4);
                  var18 = var151.optJSONObject(var101);
                  JSONObject var150 = var151.optJSONObject(var10);
                  JSONObject var153 = var151.optJSONObject("moving");
                  String var74;
                  String var90;
                  List var122;
                  if (var18 != null && var18.length() != 0) {
                     Iterator var123 = var18.keys();
                     Intrinsics.checkNotNullExpressionValue(var123, var107);
                     Iterable var130 = CollectionsKt.sortedWith(SequencesKt.toList(SequencesKt.asSequence(var123)), new Comparator(var18) {
                        final JSONObject $c$inlined;

                        {
                           this.$c$inlined = var1;
                        }

                        @Override
                        public final int compare(T var1, T var2) {
                           var2 = var2;
                           Comparable var5 = this.$c$inlined.optInt(var2);
                           var1 = var1;
                           return ComparisonsKt.compareValues(var5, this.$c$inlined.optInt(var1));
                        }
                     });
                     var var152 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var130, 10));

                     for (var var22_it : var130) {

                        var22 = var22_it;
                        String var29;
                        label212: {
                           Intrinsics.checkNotNull(var22);
                           var29 = this.nameOf(var22);
                           var6 = var18.optInt(var22);
                           if (var150 != null) {
                              Integer var135 = var150.optInt(var22);
                              int var44 = var135.intValue();
                              boolean var45;
                              if (var44 > 0) {
                                 var45 = 1;
                              } else {
                                 var45 = 0;
                              }

                              if (!var45) {
                                 var135 = null;
                              }

                              if (var135 != null) {
                                 var45 = var135.intValue();
                                 var21 = var45 + " quick";
                                 break label212;
                              }
                           }

                           var21 = null;
                        }

                        label206: {
                           if (var153 != null) {
                              Integer var142 = var153.optInt(var22);
                              int var47 = var142.intValue();
                              boolean var48;
                              if (var47 > 0) {
                                 var48 = 1;
                              } else {
                                 var48 = 0;
                              }

                              if (!var48) {
                                 var142 = null;
                              }

                              if (var142 != null) {
                                 var48 = var142.intValue();
                                 var22 = var48 + " moving";
                                 break label206;
                              }
                           }

                           var22 = null;
                        }

                        var21 = CollectionsKt.joinToString$default(
                           CollectionsKt.listOfNotNull(new String[]{var21, var22}), ", ", null, null, 0, null, null, 62, null
                        );
                        boolean var50;
                        if (var21.length() == 0) {
                           var50 = true;
                        } else {
                           var50 = false;
                        }

                        if (var50) {
                           var21 = var73;
                        } else {
                           var21 = " (" + var21 + ")";
                        }

                        var152.add(var29 + " " + var6 + "×" + var21);
                     }

                     var10 = var89;
                     var122 = var152;
                     var90 = var73;
                     var74 = var10;
                  } else {
                     var90 = var73;
                     List var129 = CollectionsKt.listOf("nothing changed");
                     var10 = var89;
                     var74 = var10;
                     var122 = var129;
                  }

                  String var116 = this.shortFmt.format(new Date(var151.optLong("start") * 1000));
                  int var51 = var151.optInt("segs");
                  var147.addView((View)this.line(var116 + " · " + var51 + " min", CarUi.Companion.getTEXT(), 17.0F));
                  String var117 = CollectionsKt.joinToString$default(var122, " · ", null, null, 0, null, null, 62, null);
                  String var118 = "      " + var117;
                  if (var150 != null && var150.length() > 0) {
                     var51 = CarUi.Companion.getWARN();
                  } else {
                     var51 = CarUi.Companion.getDIM();
                  }

                  var147.addView((View)this.line(var118, var51, 15.0F));
                  var4++;
                  var89 = var10;
                  var10 = var74;
                  var73 = var90;
               }

               String var100 = var10;
               JSONArray var75 = var18.optJSONArray("events");
               JSONArray var66 = var75;
               if (var75 == null) {
                  var66 = new JSONArray();
               }

               if (var66.length() > 0) {
                  LinearLayout var112 = this.card();
                  this.add((View)var112);
                  var112.addView(this.title("Every change", "bell", var89));
                  String var76 = "";
                  var4 = 0;
                  var30 = Math.min(var66.length(), 300);
                  JSONArray var102 = var66;

                  while (var4 < var30) {
                     var18 = var102.getJSONObject(var4);
                     double var1 = var18.optDouble("t");
                     Date var124 = new Date((long)(1000 * var1));
                     String var108 = this.dayFmt.format(var124);
                     var10 = var76;
                     if (!Intrinsics.areEqual(var108, var76)) {
                        var10 = var108;
                        CarUi var77 = this.getUi();
                        Intrinsics.checkNotNull(var108);
                        var112.addView((View)var77.header(var108));
                     }

                     String var131 = var18.optString("item");
                     String var78;
                     if (var18.optBoolean("moving")) {
                        int var53 = var18.optInt("mph");
                        var78 = var53 + " mph";
                     } else {
                        var78 = "parked";
                     }

                     String var109;
                     if (var18.optBoolean(var100)) {
                        var109 = "QUICK";
                     } else {
                        var109 = null;
                     }

                     String var79 = CollectionsKt.joinToString$default(
                        CollectionsKt.listOfNotNull(new String[]{var78, var109}), " · ", null, null, 0, null, null, 62, null
                     );
                     String var110 = this.timeFmt.format(var124);
                     Intrinsics.checkNotNull(var131);
                     var19 = this.nameOf(var131);
                     String var132 = this.word(var131, var18.optBoolean("on"));
                     String var80 = var110 + "   " + var19 + " " + var132 + "   · " + var79;
                     int var54;
                     if (var18.optBoolean(var100)) {
                        var54 = CarUi.Companion.getWARN();
                     } else {
                        var54 = CarUi.Companion.getTEXT();
                     }

                     var112.addView((View)this.line(var80, var54, 16.0F));
                     var4++;
                     var76 = var10;
                  }
               }

               return;
            }

            JSONObject var133 = var94.getJSONObject(var4);
            JSONObject var82 = var133.optJSONObject("fast");
            if (var82 != null) {
               Iterator var97 = var82.keys();
               Intrinsics.checkNotNullExpressionValue(var97, "keys(...)");

               while (var97.hasNext()) {
                  var22 = (String)var97.next();
                  Map var148 = var15;
                  Integer var23 = (Integer)var15.get(var22);
                  int var41;
                  if (var23 != null) {
                     var41 = var23;
                  } else {
                     var41 = 0;
                  }

                  var148.put(var22, var41 + var82.optInt(var22));
                  Map var144 = var16;
                  Integer var149 = (Integer)var16.get(var22);
                  if (var149 != null) {
                     var41 = var149;
                  } else {
                     var41 = 0;
                  }

                  var144.put(var22, var41 + 1);
               }

               Unit var83 = Unit.INSTANCE;
               Unit var84 = Unit.INSTANCE;
            }

            JSONObject var85 = var133.optJSONObject("changes");
            if (var85 != null) {
               Iterator var98 = var85.keys();
               Intrinsics.checkNotNullExpressionValue(var98, "keys(...)");

               while (var98.hasNext()) {
                  var22 = (String)var98.next();
                  Map var134 = var20;
                  Integer var145 = (Integer)var20.get(var22);
                  int var43;
                  if (var145 != null) {
                     var43 = var145;
                  } else {
                     var43 = 0;
                  }

                  var134.put(var22, var43 + var85.optInt(var22));
               }

               Unit var86 = Unit.INSTANCE;
               Unit var87 = Unit.INSTANCE;
            }

            var4++;
         }
      }
   }

   private static final Unit paint$lambda$10(DoorsBeltsView var0) {
      var0.openLearn();
      return Unit.INSTANCE;
   }

   private final void paintNow() {
      LinearLayout var6 = this.nowCard;
      if (var6 != null) {
         var6.removeAllViews();
         var6.addView(this.title("Right now", "eye", "device"));
         JSONObject var3 = this.status;
         if (var3 != null) {
            JSONObject var2 = this.live;
            if (!var3.optBoolean("carOn")) {
               var6.addView((View)CardPage.line$default(this, "Shows every switch live with the car on and in Park.", 0, 0.0F, 6, null));
            } else if (var2 == null) {
               CardPage var22 = this;
               String var21 = this.liveErr;
               String var20 = var21;
               if (var21 == null) {
                  var20 = "Reading…";
               }

               var6.addView((View)CardPage.line$default(var22, var20, 0, 0.0F, 6, null));
            } else {
               JSONObject var7 = var2.optJSONObject("words");
               LinearLayout var8 = new LinearLayout(this.getContext());
               var8.setOrientation(1);

               for (JSONObject var10 : this.items()) {
                  String var5 = var10.optString("id");
                  CarUi.Companion var14 = null;
                  String var4 = var14;
                  if (var7 != null) {
                     var5 = var7.optString(var5);
                     var4 = var14;
                     if (var5 != null) {
                        boolean var1;
                        if (var5.length() > 0) {
                           var1 = true;
                        } else {
                           var1 = false;
                        }

                        if (var1 && !Intrinsics.areEqual(var5, "null")) {
                           var1 = true;
                        } else {
                           var1 = false;
                        }

                        var4 = var14;
                        if (var1) {
                           var4 = var5;
                        }
                     }
                  }

                  var14 = var10.optString("source");
                  LinearLayout var24 = new LinearLayout(this.getContext());
                  var24.setOrientation(0);
                  var24.setGravity(16);
                  var24.setPadding(0, this.getUi().dp(3), 0, this.getUi().dp(3));
                  TextView var11 = new TextView(var24.getContext());
                  var11.setText(var10.optString("name"));
                  var11.setTextColor(CarUi.Companion.getTEXT());
                  var11.setTextSize(17.0F);
                  var24.addView((View)var11, (android.view.ViewGroup.LayoutParams)(new LayoutParams(this.getUi().dp(260), -2)));
                  TextView var25 = new TextView(var24.getContext());
                  if (var4 != null) {
                     var14 = var4;
                  } else {
                     label82: {
                        label81: {
                           if (var14 != null) {
                              switch (var14.hashCode()) {
                                 case -1282241876:
                                    if (var14.equals("not on bus")) {
                                       var14 = "not on the comma's bus";
                                       break label82;
                                    }
                                    break;
                                 case 0:
                                    if (var14.equals("")) {
                                       break label81;
                                    }
                                    break;
                                 case 3392903:
                                    if (var14.equals("null")) {
                                       break label81;
                                    }
                              }
                           }

                           var14 = "–";
                           break label82;
                        }

                        var14 = "not learned";
                     }

                     var14 = (CharSequence)var14;
                  }

                  var25.setText((CharSequence)var14);
                  int var13;
                  if (Intrinsics.areEqual(var4, "open")) {
                     var13 = CarUi.Companion.getWARN();
                  } else if (Intrinsics.areEqual(var4, "buckled")) {
                     var13 = CarUi.Companion.getACCENT();
                  } else {
                     var14 = CarUi.Companion;
                     if (var4 == null) {
                        var13 = var14.getDIM();
                     } else {
                        var13 = var14.getTEXT();
                     }
                  }

                  var25.setTextColor(var13);
                  var25.setTextSize(17.0F);
                  Typeface var19;
                  if (var4 != null) {
                     var19 = Typeface.DEFAULT_BOLD;
                  } else {
                     var19 = Typeface.DEFAULT;
                  }

                  var25.setTypeface(var19);
                  var24.addView((View)var25);
                  var8.addView((View)var24);
               }

               var6.addView((View)var8);
            }
         }
      }
   }

   private final void req(String var1, String var2, Function1<? super JSONObject, Unit> var3) {
      DataClient var4 = Link.INSTANCE.getData();
      if (var4 == null) {
         JSONObject var6 = new JSONObject().put("ok", false).put("error", "Not connected to the comma.");
         Intrinsics.checkNotNullExpressionValue(var6, "put(...)");
         var3.invoke(var6);
      } else {
         Map var5;
         if (var2 == null) {
            var5 = MapsKt.mapOf(TuplesKt.to("op", var1));
         } else {
            var5 = MapsKt.mapOf(new Pair[]{TuplesKt.to("op", var1), TuplesKt.to("item", var2)});
         }

         var4.request("doorbelt", var5, var3);
      }
   }

   // $VF: synthetic method
   static void req$default(DoorsBeltsView var0, String var1, String var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = null;
      }

      var0.req(var1, var2, var3);
   }

   private final void toast(String var1) {
      Toast.makeText(this.getContext(), var1, 1).show();
   }

   private final String word(String var1, boolean var2) {
      if (Intrinsics.areEqual(this.kindOf(var1), "belt")) {
         if (var2) {
            var1 = "buckled";
         } else {
            var1 = "unbuckled";
         }
      } else if (var2) {
         var1 = "open";
      } else {
         var1 = "closed";
      }

      return var1;
   }

   @Override
   public void refresh() {
      this.start();
   }

   public final void start() {
      this.shown = true;
      this.getMain().removeCallbacks(this.poll);
      this.statusAt = 0L;
      this.getMain().post(this.poll);
   }

   public final void stop() {
      this.shown = false;
      this.getMain().removeCallbacks(this.poll);
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }

   private final class Learn {
      private final DoorsBeltsView.LearnState st;
      final DoorsBeltsView this$0;

      // $VF: synthetic method
      public static Unit $r8$lambda$7KVHkG_NhiVz5lYf16x5s4ZW6fo(DoorsBeltsView var0, DoorsBeltsView.Learn var1) {
         return paint$lambda$12(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$8pjRjrG8E6BaPvS52r1ptgAkxQI(DoorsBeltsView var0) {
         return paint$lambda$14(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$AMZYH8tfar8SVXc7XSEXMggWcVE(DoorsBeltsView.Learn var0, JSONObject var1) {
         return paint$lambda$12$lambda$11(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$AmGFOrosri53t8_k1y_wrSs5E_c(DoorsBeltsView.Learn var0, DoorsBeltsView var1, JSONObject var2) {
         return paint$lambda$21$lambda$20(var0, var1, var2);
      }

      // $VF: synthetic method
      public static void $r8$lambda$C0LurjCF05vvR2OxddUfyDESPGs(DoorsBeltsView var0, DoorsBeltsView.Learn var1) {
         paint$lambda$2$lambda$1$lambda$0(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$C1ynAFZJoWpJiuriDhSDe_BFLX8/* $VF was: $r8$lambda$C1ynAFZJoWpJiuriDhSDe_BFLX8*/(DoorsBeltsView var0) {
         return paint$lambda$10(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$K_y7AzJ8uZ4LjhExERUvRFCbKfo/* $VF was: $r8$lambda$K_y7AzJ8uZ4LjhExERUvRFCbKfo*/(
         DoorsBeltsView var0, DoorsBeltsView.Learn var1
      ) {
         return paint$lambda$21(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$LomhpZqVAlF_cywxFt5KKcJg2_0/* $VF was: $r8$lambda$LomhpZqVAlF_cywxFt5KKcJg2_0*/(DoorsBeltsView var0, JSONObject var1) {
         return paint$lambda$10$lambda$9(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$PcjnLw_Wpapd5s3vogNiclYA2wU(DoorsBeltsView var0, DoorsBeltsView.Learn var1) {
         return paint$lambda$6(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$Z8DDdTgTLECGl68UUGtwm80uKWg(DoorsBeltsView var0) {
         return paint$lambda$26(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$ZdK5FkJHoqgRKJ12xtPXBTRDX0o(DoorsBeltsView var0, JSONObject var1) {
         return paint$lambda$14$lambda$13(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$ak_lE_ZVqGKaSCjqdhec2yIPZg8/* $VF was: $r8$lambda$ak_lE_ZVqGKaSCjqdhec2yIPZg8*/(
         DoorsBeltsView var0, DoorsBeltsView.Learn var1
      ) {
         return paint$lambda$8(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$b0WJhg__wI7ANLxgJKJHKprggco/* $VF was: $r8$lambda$b0WJhg__wI7ANLxgJKJHKprggco*/(
         DoorsBeltsView.Learn var0, DoorsBeltsView var1, JSONObject var2
      ) {
         return paint$lambda$2$lambda$1(var0, var1, var2);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$bJhpkAVk2A5AMgDNDZnmoCKthR4(DoorsBeltsView var0, DoorsBeltsView.Learn var1) {
         return paint$lambda$2(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$bKIutpXwGEcat6W121cklpcbeTg(DoorsBeltsView.Learn var0, JSONObject var1) {
         return paint$lambda$8$lambda$7(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$cQIZtnjyf5Wk0FJVyUuYBW_crjE/* $VF was: $r8$lambda$cQIZtnjyf5Wk0FJVyUuYBW_crjE*/(DoorsBeltsView.Learn var0) {
         return paint$lambda$16(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$dXieAEqCYPyaBs60bxQr8zNEQX0(DoorsBeltsView var0, JSONObject var1) {
         return paint$lambda$26$lambda$25(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$gsn7WJbNHKDRW_HbkNM_CvLgA0o/* $VF was: $r8$lambda$gsn7WJbNHKDRW_HbkNM_CvLgA0o*/(DoorsBeltsView var0) {
         return paint$lambda$3(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$m5onjVqr0SWoxyiaRVsdIj6J_N4(DoorsBeltsView.Learn var0) {
         return paint$lambda$24(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$o3ZY8pSRnannMUE95q8hFt0WWxc(DoorsBeltsView.Learn var0) {
         return paint$lambda$17(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$tpSZONI6FJ79IVS09N5qTsHXyjQ(DoorsBeltsView.Learn var0, JSONObject var1) {
         return paint$lambda$6$lambda$5(var0, var1);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$wR3rMsoN7avQq9b6T8ZTzFY_wgA(DoorsBeltsView var0) {
         return paint$lambda$23(var0);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$y1m_PidWgSL18OE25M0cL1GTE7Q/* $VF was: $r8$lambda$y1m_PidWgSL18OE25M0cL1GTE7Q*/(DoorsBeltsView var0, JSONObject var1) {
         return paint$lambda$23$lambda$22(var0, var1);
      }

      public Learn(DoorsBeltsView.LearnState var1, DoorsBeltsView.LearnState var2) {
         Intrinsics.checkNotNullParameter(var2, "st");
         this.this$0 = var1;
         super();
         this.st = var2;
      }

      private static final Unit paint$lambda$10(DoorsBeltsView var0) {
         DoorsBeltsView.req$default(var0, "cancel", null, new DoorsBeltsView$Learn$$ExternalSyntheticLambda20(var0), 2, null);
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$10$lambda$9(DoorsBeltsView var0, JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "it");
         var0.closeLearn();
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$12(DoorsBeltsView var0, DoorsBeltsView.Learn var1) {
         var0.req("done", var1.id(), new DoorsBeltsView$Learn$$ExternalSyntheticLambda18(var1));
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$12$lambda$11(DoorsBeltsView.Learn var0, JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "r");
         if (!var1.optBoolean("ok")) {
            var0.fail(var1);
         } else {
            var0.setResult(var1.optJSONObject("found"));
            var0.getFound().put(var0.id(), var0.getResult());
            var0.setPhase("result");
            var0.paint();
         }

         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$14(DoorsBeltsView var0) {
         DoorsBeltsView.req$default(var0, "cancel", null, new DoorsBeltsView$Learn$$ExternalSyntheticLambda15(var0), 2, null);
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$14$lambda$13(DoorsBeltsView var0, JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "it");
         var0.closeLearn();
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$16(DoorsBeltsView.Learn var0) {
         var0.next();
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$17(DoorsBeltsView.Learn var0) {
         var0.setPhase("ready");
         var0.paint();
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$2(DoorsBeltsView var0, DoorsBeltsView.Learn var1) {
         DoorsBeltsView.req$default(var0, "begin", null, new DoorsBeltsView$Learn$$ExternalSyntheticLambda21(var1, var0), 2, null);
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$2$lambda$1(DoorsBeltsView.Learn var0, DoorsBeltsView var1, JSONObject var2) {
         Intrinsics.checkNotNullParameter(var2, "r");
         if (!var2.optBoolean("ok")) {
            var0.fail(var2);
         } else {
            var0.setPhase("still");
            var0.paint();
            var1.getMain().postDelayed(new DoorsBeltsView$Learn$$ExternalSyntheticLambda16(var1, var0), 5000L);
         }

         return Unit.INSTANCE;
      }

      private static final void paint$lambda$2$lambda$1$lambda$0(DoorsBeltsView var0, DoorsBeltsView.Learn var1) {
         if (var0.learn == var1 && Intrinsics.areEqual(var1.getPhase(), "still")) {
            var1.setPhase("ready");
            var1.paint();
         }
      }

      private static final Unit paint$lambda$21(DoorsBeltsView var0, DoorsBeltsView.Learn var1) {
         DoorsBeltsView.req$default(var0, "save", null, new DoorsBeltsView$Learn$$ExternalSyntheticLambda22(var1, var0), 2, null);
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$21$lambda$20(DoorsBeltsView.Learn var0, DoorsBeltsView var1, JSONObject var2) {
         Intrinsics.checkNotNullParameter(var2, "r");
         if (!var2.optBoolean("ok")) {
            var0.fail(var2);
         } else {
            var1.toast("Saved");
            var1.closeLearn();
         }

         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$23(DoorsBeltsView var0) {
         DoorsBeltsView.req$default(var0, "cancel", null, new DoorsBeltsView$Learn$$ExternalSyntheticLambda17(var0), 2, null);
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$23$lambda$22(DoorsBeltsView var0, JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "it");
         var0.closeLearn();
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$24(DoorsBeltsView.Learn var0) {
         var0.getFound().clear();
         var0.setI(0);
         var0.setPhase("intro");
         var0.paint();
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$26(DoorsBeltsView var0) {
         DoorsBeltsView.req$default(var0, "cancel", null, new DoorsBeltsView$Learn$$ExternalSyntheticLambda0(var0), 2, null);
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$26$lambda$25(DoorsBeltsView var0, JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "it");
         var0.closeLearn();
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$3(DoorsBeltsView var0) {
         var0.closeLearn();
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$6(DoorsBeltsView var0, DoorsBeltsView.Learn var1) {
         var0.req("start", var1.id(), new DoorsBeltsView$Learn$$ExternalSyntheticLambda11(var1));
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$6$lambda$5(DoorsBeltsView.Learn var0, JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "r");
         if (!var1.optBoolean("ok")) {
            var0.fail(var1);
         } else {
            var0.setPhase("working");
            var0.paint();
         }

         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$8(DoorsBeltsView var0, DoorsBeltsView.Learn var1) {
         var0.req("skip", var1.id(), new DoorsBeltsView$Learn$$ExternalSyntheticLambda19(var1));
         return Unit.INSTANCE;
      }

      private static final Unit paint$lambda$8$lambda$7(DoorsBeltsView.Learn var0, JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "it");
         var0.next();
         return Unit.INSTANCE;
      }

      public final boolean belt() {
         return Intrinsics.areEqual(this.getList().get(this.getI()).optString("kind"), "belt");
      }

      public final void fail(JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "r");
         this.setError(var1.optString("error", "The comma didn't answer"));
         this.setPhase("error");
         this.paint();
      }

      public final String getError() {
         return this.st.getError();
      }

      public final LinkedHashMap<String, JSONObject> getFound() {
         return this.st.getFound();
      }

      public final int getI() {
         return this.st.getI();
      }

      public final List<JSONObject> getList() {
         return this.st.getList();
      }

      public final String getPhase() {
         return this.st.getPhase();
      }

      public final JSONObject getResult() {
         return this.st.getResult();
      }

      public final DoorsBeltsView.LearnState getSt() {
         return this.st;
      }

      public final String id() {
         return this.getList().get(this.getI()).optString("id");
      }

      public final void liveTick(JSONObject var1) {
         Intrinsics.checkNotNullParameter(var1, "r");
         if (!SetsKt.setOf(new String[]{"intro", "error"}).contains(this.getPhase())) {
            if (!var1.optBoolean("ok")) {
               this.fail(var1);
            } else if (!var1.optBoolean("learning") && !Intrinsics.areEqual(this.getPhase(), "still")) {
               var1 = new JSONObject().put("error", "Learn stopped on the comma - start it again.");
               Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
               this.fail(var1);
            }
         }
      }

      public final String name() {
         return this.getList().get(this.getI()).optString("name");
      }

      public final void next() {
         String var1;
         if (this.getI() + 1 < this.getList().size()) {
            this.setI(this.getI() + 1);
            var1 = "ready";
         } else {
            var1 = "summary";
         }

         this.setPhase(var1);
         this.paint();
      }

      public final void paint() {
         this.this$0.getCol().removeAllViews();
         LinearLayout var6 = this.this$0.card();
         this.this$0.add((View)var6);
         var6.addView(this.this$0.title("Learn doors & belts", "car", "device"));
         String var5 = this.getPhase();
         switch (var5.hashCode()) {
            case -1857640538:
               if (var5.equals("summary")) {
                  Map var35 = this.getFound();
                  LinkedHashMap var26 = new LinkedHashMap();

                  for (Entry var36 : var35.entrySet()) {
                     boolean var14;
                     if ((JSONObject)var36.getValue() != null) {
                        var14 = true;
                     } else {
                        var14 = false;
                     }

                     if (var14) {
                        var26.put(var36.getKey(), var36.getValue());
                     }
                  }

                  Set var27 = var26.keySet();
                  DoorsBeltsView var37 = this.this$0;
                  int var15 = var27.size();
                  int var18 = this.getFound().size();
                  var6.addView((View)var37.line("Found " + var15 + " of " + var18 + " you worked.", CarUi.Companion.getTEXT(), 18.0F));

                  label87:
                  for (Entry var28 : this.getFound().entrySet()) {
                     String var10 = (String)var28.getKey();
                     JSONObject var42 = (JSONObject)var28.getValue();
                     DoorsBeltsView var9 = this.this$0;
                     if (var42 != null) {
                        var5 = "✓  ";
                     } else {
                        var5 = "✗  ";
                     }

                     for (Object var11 : this.getList()) {
                        if (Intrinsics.areEqual(((JSONObject)var11).optString("id"), var10)) {
                           var10 = ((JSONObject)var11).optString("name");
                           var10 = var5 + var10;
                           CarUi.Companion var30 = CarUi.Companion;
                           if (var42 != null) {
                              var15 = var30.getACCENT();
                           } else {
                              var15 = var30.getWARN();
                           }

                           var6.addView((View)var9.line(var10, var15, 17.0F));
                           continue label87;
                        }
                     }

                     throw new NoSuchElementException("Collection contains no element matching the predicate.");
                  }

                  var6.addView(
                     (View)CardPage.line$default(
                        this.this$0, "Save, and the comma checks these from the next drive on (and the last week again).", 0, 0.0F, 6, null
                     )
                  );
                  var6.addView(
                     this.this$0
                        .buttons(
                           this.this$0.getUi().button("Save", true, new DoorsBeltsView$Learn$$ExternalSyntheticLambda14(this.this$0, this)),
                           CarUi.button$default(this.this$0.getUi(), "Cancel", false, new DoorsBeltsView$Learn$$ExternalSyntheticLambda2(this.this$0), 2, null)
                        )
                  );
               }

               return;
            case -934426595:
               if (var5.equals("result")) {
                  TextView var24 = new TextView(this.this$0.getContext());
                  var24.setText(this.name());
                  var24.setTextColor(CarUi.Companion.getTEXT());
                  var24.setTextSize(26.0F);
                  var24.setTypeface(Typeface.DEFAULT_BOLD);
                  var6.addView((View)var24);
                  JSONObject var34 = this.getResult();
                  if (var34 != null) {
                     DoorsBeltsView var25 = this.this$0;
                     int var17 = var34.optInt("bus");
                     String var39 = Integer.toHexString(var34.optInt("addr"));
                     Intrinsics.checkNotNullExpressionValue(var39, "toHexString(...)");
                     var39 = var39.toUpperCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var39, "toUpperCase(...)");
                     int var4 = var34.optInt("byte");
                     int var13 = var34.optInt("bit");
                     int var3 = var34.optInt("changes");
                     var6.addView(
                        (View)var25.line(
                           "Found it ✓  (bus " + var17 + ", 0x" + var39 + " byte " + var4 + " bit " + var13 + ", changed " + var3 + " times)",
                           CarUi.Companion.getACCENT(),
                           18.0F
                        )
                     );
                  } else {
                     var6.addView(
                        (View)this.this$0
                           .line(
                              "Nothing on the comma's bus moved with it. Try again, or skip it: that switch may only be on a bus the comma doesn't see.",
                              CarUi.Companion.getWARN(),
                              18.0F
                           )
                     );
                  }

                  var6.addView(
                     this.this$0
                        .buttons(
                           this.this$0.getUi().button("Next", true, new DoorsBeltsView$Learn$$ExternalSyntheticLambda12(this)),
                           CarUi.button$default(this.this$0.getUi(), "Again", false, new DoorsBeltsView$Learn$$ExternalSyntheticLambda13(this), 2, null)
                        )
                  );
               }

               return;
            case 96784904:
               if (var5.equals("error")) {
                  var6.addView((View)this.this$0.line(this.getError(), this.this$0.red, 18.0F));
                  var6.addView(
                     this.this$0
                        .buttons(
                           this.this$0.getUi().button("Start over", true, new DoorsBeltsView$Learn$$ExternalSyntheticLambda3(this)),
                           CarUi.button$default(this.this$0.getUi(), "Close", false, new DoorsBeltsView$Learn$$ExternalSyntheticLambda4(this.this$0), 2, null)
                        )
                  );
               }

               return;
            case 100361836:
               if (var5.equals("intro")) {
                  var6.addView((View)this.this$0.line("Car on, in Park. Close every door and unbuckle every belt first.", CarUi.Companion.getTEXT(), 18.0F));
                  var6.addView(
                     (View)CardPage.line$default(
                        this.this$0,
                        "For each one: tap Start, then open and close it twice (a belt: buckle and unbuckle it twice), then tap Done. Skip the seats this van doesn't have.",
                        0,
                        0.0F,
                        6,
                        null
                     )
                  );
                  var6.addView(
                     (View)CardPage.line$default(
                        this.this$0,
                        "Move between seats or step out between steps if you need to - just not while a step runs, and don't touch any other door or belt during one.",
                        0,
                        0.0F,
                        6,
                        null
                     )
                  );
                  var6.addView(
                     this.this$0
                        .buttons(
                           this.this$0.getUi().button("Begin", true, new DoorsBeltsView$Learn$$ExternalSyntheticLambda1(this.this$0, this)),
                           CarUi.button$default(this.this$0.getUi(), "Cancel", false, new DoorsBeltsView$Learn$$ExternalSyntheticLambda5(this.this$0), 2, null)
                        )
                  );
               }

               return;
            case 108386723:
               if (!var5.equals("ready")) {
                  return;
               }
               break;
            case 109765032:
               if (var5.equals("still")) {
                  var6.addView((View)this.this$0.line("Hold still a few seconds - the comma is listening to the bus…", CarUi.Companion.getTEXT(), 18.0F));
               }

               return;
            case 1525164849:
               if (!var5.equals("working")) {
                  return;
               }
               break;
            default:
               return;
         }

         DoorsBeltsView var19 = this.this$0;
         int var2 = this.getI();
         int var1 = this.getList().size();
         var6.addView((View)var19.line(var2 + 1 + " of " + var1, CarUi.Companion.getDIM(), 15.0F));
         TextView var20 = new TextView(this.this$0.getContext());
         DoorsBeltsView var7 = this.this$0;
         var20.setText(this.name());
         var20.setTextColor(CarUi.Companion.getTEXT());
         var20.setTextSize(30.0F);
         var20.setTypeface(Typeface.DEFAULT_BOLD);
         var20.setPadding(0, var7.getUi().dp(6), 0, var7.getUi().dp(6));
         var6.addView((View)var20);
         if (Intrinsics.areEqual(this.getPhase(), "ready")) {
            var7 = this.this$0;
            if (this.belt()) {
               var5 = "Tap Start, then buckle and unbuckle it twice. Tap Done when it's unbuckled.";
            } else {
               var5 = "Tap Start, then open and close it twice. Tap Done when it's closed.";
            }

            var6.addView((View)var7.line(var5, CarUi.Companion.getTEXT(), 18.0F));
            DoorsBeltsView var8 = this.this$0;
            TextView var32 = this.this$0.getUi().button("Start", true, new DoorsBeltsView$Learn$$ExternalSyntheticLambda6(this.this$0, this));
            TextView var22 = CarUi.button$default(
               this.this$0.getUi(), "Skip", false, new DoorsBeltsView$Learn$$ExternalSyntheticLambda7(this.this$0, this), 2, null
            );
            var6.addView(
               var8.buttons(
                  var32,
                  var22,
                  CarUi.button$default(this.this$0.getUi(), "Cancel", false, new DoorsBeltsView$Learn$$ExternalSyntheticLambda8(this.this$0), 2, null)
               )
            );
         } else {
            var7 = this.this$0;
            if (this.belt()) {
               var5 = "Now: buckle, unbuckle, buckle, unbuckle.";
            } else {
               var5 = "Now: open, close, open, close.";
            }

            var6.addView((View)var7.line(var5, CarUi.Companion.getWARN(), 20.0F));
            var6.addView(
               this.this$0
                  .buttons(
                     this.this$0.getUi().button("Done", true, new DoorsBeltsView$Learn$$ExternalSyntheticLambda9(this.this$0, this)),
                     CarUi.button$default(this.this$0.getUi(), "Cancel", false, new DoorsBeltsView$Learn$$ExternalSyntheticLambda10(this.this$0), 2, null)
                  )
            );
         }
      }

      public final void setError(String var1) {
         Intrinsics.checkNotNullParameter(var1, "v");
         this.st.setError(var1);
      }

      public final void setI(int var1) {
         this.st.setI(var1);
      }

      public final void setPhase(String var1) {
         Intrinsics.checkNotNullParameter(var1, "v");
         this.st.setPhase(var1);
      }

      public final void setResult(JSONObject var1) {
         this.st.setResult(var1);
      }
   }

   private static final class LearnState {
      private String error;
      private final LinkedHashMap<String, JSONObject> found;
      private int i;
      private final List<JSONObject> list;
      private String phase;
      private JSONObject result;

      public LearnState(List<? extends JSONObject> var1) {
         Intrinsics.checkNotNullParameter(var1, "list");
         super();
         this.list = var1;
         this.phase = "intro";
         this.error = "";
         this.found = new LinkedHashMap<>();
      }

      public final String getError() {
         return this.error;
      }

      public final LinkedHashMap<String, JSONObject> getFound() {
         return this.found;
      }

      public final int getI() {
         return this.i;
      }

      public final List<JSONObject> getList() {
         return this.list;
      }

      public final String getPhase() {
         return this.phase;
      }

      public final JSONObject getResult() {
         return this.result;
      }

      public final void setError(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.error = var1;
      }

      public final void setI(int var1) {
         this.i = var1;
      }

      public final void setPhase(String var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.phase = var1;
      }

      public final void setResult(JSONObject var1) {
         this.result = var1;
      }
   }
}
