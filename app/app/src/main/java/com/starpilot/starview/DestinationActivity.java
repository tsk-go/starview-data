package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class DestinationActivity extends AppCompatActivity {
   private static final String CLIENT_ID;
   public static final DestinationActivity.Companion Companion = new DestinationActivity.Companion(null);
   public static final String EXTRA_LAT = "lat";
   public static final String EXTRA_LON = "lon";
   public static final String EXTRA_QUERY = "query";
   public static final String EXTRA_ROUTE = "route";
   private static final String KEY_RECENTS = "recents";
   private static final int MAX_RECENTS = 12;
   private static final String PREFS = "starview_nav";
   private static final List<Pair<String, String>> QUICK;
   public static final String R_ADDRESS = "address";
   public static final String R_LAT = "latitude";
   public static final String R_LON = "longitude";
   public static final String R_MAPBOX_ID = "mapbox_id";
   public static final String R_NAME = "name";
   public static final String R_SEARCH_ID = "search_id";
   private boolean along;
   private TextView alongBtn;
   private final ExecutorService bg;
   private boolean commaSearch;
   private final Runnable doSearch;
   private EditText edit;
   private List<DestinationActivity.Place> favorites;
   private Pair<Double, Double> here;
   private LinearLayout list;
   private List<DestinationActivity.Place> results;
   private String resultsTitle;
   private String route;
   private String searchId;
   private int searchSeq;
   private List<DestinationActivity.Place> spRecents;
   private TextView status;
   private final Handler ui = new Handler(Looper.getMainLooper());

   // $VF: synthetic method
   public static void $r8$lambda$45Vyy1Y3LGnKD___bZCLYdwvSks/* $VF was: $r8$lambda$45Vyy1Y3LGnKD___bZCLYdwvSks*/(
      String[] var0, UiSettings var1, JSONObject var2, boolean var3, DataClient var4, DestinationActivity var5, DialogInterface var6, int var7
   ) {
      placeMenu$lambda$79(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static void $r8$lambda$65zVHckM3JEBsB6nxPZjlI300s0(
      String[] var0, DestinationActivity.Place var1, DataClient var2, DestinationActivity var3, DialogInterface var4, int var5
   ) {
      placeMenu$lambda$74(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static void $r8$lambda$6JW18kv41KNfW1kXoQlMEZED1lc(DestinationActivity var0, Pair var1, String var2, int var3) {
      androidSearch$lambda$48(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7jmgMYowtgKPKa76WPNEUBM2GGk(String var0, DestinationActivity var1, String var2) {
      return placeMenu$lambda$74$lambda$72(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Ep6vTwylPolaAm747sMEM6YqCTQ(String var0, DestinationActivity var1, String var2) {
      return placeMenu$lambda$74$lambda$73(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$FFCYERFJrX2foZHEVFMlU0QGeMs(Function0 var0, View var1) {
      chip$lambda$23$lambda$22(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Flt_S_cYcHD6rvOP2YNY74Nmm_g/* $VF was: $r8$lambda$Flt_S_cYcHD6rvOP2YNY74Nmm_g*/(
      DestinationActivity var0, String var1, String var2
   ) {
      return onCreate$lambda$12(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GacM4BxraD1h8Z9c170h6wSpiwE(DestinationActivity var0) {
      return onCreate$lambda$11(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$HY5Zzr3A_5wRj_DR2bdk7oggHOA(DestinationActivity var0, String var1) {
      return placeMenu$lambda$79$lambda$76(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$PcRhB__ibEZx2CTBzG1DrgySaog/* $VF was: $r8$lambda$PcRhB__ibEZx2CTBzG1DrgySaog*/(
      DestinationActivity var0, DestinationActivity.Place var1, JSONObject var2, View var3
   ) {
      row$lambda$69$lambda$68(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Rvd9knqYlTET_5pE5GmMOcGYrbQ(int var0, DestinationActivity var1, String var2, JSONObject var3) {
      return quick$lambda$42(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$S6xjYCXRcr45vlrURFCClVNv5zo(DestinationActivity var0, String var1) {
      return placeMenu$lambda$79$lambda$78(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$SGdz5co514P9cJzpzyIrH9qXAKI(DestinationActivity var0, DestinationActivity.Place var1, View var2) {
      row$lambda$59$lambda$58(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$YnOuwxh3YRbZkNGOxraDC9B5_ig/* $VF was: $r8$lambda$YnOuwxh3YRbZkNGOxraDC9B5_ig*/(DestinationActivity var0, View var1) {
      onCreate$lambda$9$lambda$8(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZU66GvB9vWSiQwiHWiZLUAeEV40(int var0, DestinationActivity var1, String var2, boolean var3, JSONObject var4) {
      return runSearch$lambda$34(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_Y6K4SDSlkhxPmh9MMv7Chi2KE8(DestinationActivity var0, JSONObject var1) {
      return onCreate$lambda$19(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$aanO3_9NA7FsWSLkx1OXhLey4vQ(int var0, DestinationActivity var1, List var2) {
      androidSearch$lambda$48$lambda$47(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$cPF95ZWs4Gbrpjq_RbUN4AKVmbc(DestinationActivity var0, View var1) {
      onCreate$lambda$4$lambda$3(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$dcptpAmPbYRTuFs6cvz0dwZwTzA(DestinationActivity var0) {
      doSearch$lambda$28(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ju2aN0xEcYTFH4Vq62v33BbjC3A(DestinationActivity var0) {
      onCreate$lambda$20(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$oFcRSmWKSqwNiWVhHVN86qYjrqs(DestinationActivity var0, DestinationActivity.Place var1, JSONObject var2, View var3) {
      return row$lambda$60(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$q2ekVE_5DvnG0inR4LD3c1xnojA/* $VF was: $r8$lambda$q2ekVE_5DvnG0inR4LD3c1xnojA*/(DestinationActivity var0, String var1) {
      return placeMenu$lambda$79$lambda$77(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tlf28zaOYHjimhFDQQ4gjLR2tEE(DestinationActivity var0, String var1) {
      return placeMenu$lambda$79$lambda$75(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$vz2FrQb2wBBKHNHMuVD_r6VZ30U/* $VF was: $r8$lambda$vz2FrQb2wBBKHNHMuVD_r6VZ30U*/(
      DestinationActivity var0, EditText var1, TextView var2, int var3, KeyEvent var4
   ) {
      return onCreate$lambda$7$lambda$6(var0, var1, var2, var3, var4);
   }

   static {
      String var0 = UUID.randomUUID().toString();
      Intrinsics.checkNotNullExpressionValue(var0, "toString(...)");
      CLIENT_ID = var0;
      Pair var6 = TuplesKt.to("gas", "⛽  Gas");
      Pair var9 = TuplesKt.to("ev", "⚡  EV charging");
      Pair var4 = TuplesKt.to("coffee", "☕  Coffee");
      Pair var1 = TuplesKt.to("food", "\ud83c\udf7d  Food");
      Pair var2 = TuplesKt.to("fast_food", "\ud83c\udf54  Fast food");
      Pair var7 = TuplesKt.to("parking", "\ud83c\udd7f  Parking");
      Pair var5 = TuplesKt.to("grocery", "\ud83d\uded2  Grocery");
      Pair var8 = TuplesKt.to("pharmacy", "\ud83d\udc8a  Pharmacy");
      Pair var3 = TuplesKt.to("hotel", "\ud83d\udecf  Hotels");
      QUICK = CollectionsKt.listOf(new Pair[]{var6, var9, var4, var1, var2, var7, var5, var8, var3, TuplesKt.to("rest_area", "\ud83d\udebb  Rest areas")});
   }

   public DestinationActivity() {
      this.bg = Executors.newSingleThreadExecutor();
      this.along = true;
      this.favorites = CollectionsKt.emptyList();
      this.spRecents = CollectionsKt.emptyList();
      this.resultsTitle = "";
      String var1 = UUID.randomUUID().toString();
      Intrinsics.checkNotNullExpressionValue(var1, "toString(...)");
      this.searchId = var1;
      this.commaSearch = true;
      this.doSearch = new DestinationActivity$$ExternalSyntheticLambda20(this);
   }

   private final void androidSearch(String var1, int var2) {
      boolean var3 = Geocoder.isPresent();
      Object var5 = null;
      TextView var4 = null;
      if (!var3) {
         TextView var6 = this.status;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            var6 = var4;
         }

         var6.setText("Address search isn't available on this tablet (needs Google Play services)");
      } else {
         var4 = this.status;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            var4 = (TextView)var5;
         }

         var4.setText("Searching addresses…");
         Pair var8 = this.here;
         this.bg.execute(new DestinationActivity$$ExternalSyntheticLambda0(this, var8, var1, var2));
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final void androidSearch$lambda$48(DestinationActivity var0, Pair var1, String var2, int var3) {
      label223: {
         label225: {
            Geocoder var9;
            try {
               var9 = new Geocoder((Context)var0, Locale.getDefault());
            } catch (Exception var35) {
               break label225;
            }

            List var36;
            if (var1 != null) {
               try {
                  double var4 = 0.45 / RangesKt.coerceAtLeast(Math.cos(Math.toRadians(((Number)var1.getFirst()).doubleValue())), 0.2);
                  var36 = var9.getFromLocationName(
                     var2,
                     8,
                     ((Number)var1.getFirst()).doubleValue() - 0.45,
                     ((Number)var1.getSecond()).doubleValue() - var4,
                     ((Number)var1.getFirst()).doubleValue() + 0.45,
                     ((Number)var1.getSecond()).doubleValue() + var4
                  );
               } catch (Exception var34) {
                  break label225;
               }
            } else {
               var36 = null;
            }

            Collection var10;
            try {
               var10 = var36;
            } catch (Exception var33) {
               break label225;
            }

            boolean var6;
            label210: {
               if (var10 != null) {
                  boolean var8;
                  try {
                     var8 = var10.isEmpty();
                  } catch (Exception var32) {
                     break label225;
                  }

                  if (!var8) {
                     var6 = 0;
                     break label210;
                  }
               }

               var6 = 1;
            }

            if (var6) {
               try {
                  var36 = var9.getFromLocationName(var2, 8);
               } catch (Exception var31) {
                  break label225;
               }
            }

            List var39;
            if (var36 == null) {
               try {
                  var39 = CollectionsKt.emptyList();
               } catch (Exception var30) {
                  break label225;
               }
            } else {
               var39 = var36;
            }

            Iterator var11;
            try {
               Iterable var53 = var39;
               ArrayList var40 = new ArrayList();
               var41 = var40;
               var11 = var53.iterator();
            } catch (Exception var28) {
               break label225;
            }

            while (true) {
               Object var12;
               label190: {
                  label189: {
                     try {
                        if (!var11.hasNext()) {
                           break;
                        }

                        var12 = var11.next();
                        Address var54 = (Address)var12;
                        if (var54.hasLatitude() && var54.hasLongitude()) {
                           break label189;
                        }
                     } catch (Exception var29) {
                        break label225;
                     }

                     var6 = (boolean)0;
                     break label190;
                  }

                  var6 = (boolean)1;
               }

               if (var6) {
                  try {
                     var41.add(var12);
                  } catch (Exception var27) {
                     break label225;
                  }
               }
            }

            try {
               var42 = var41;
            } catch (Exception var26) {
               break label225;
            }

            Iterator var13;
            ArrayList var57;
            try {
               Iterable var43 = var42;
               ArrayList var55 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var43, 10));
               var57 = var55;
               var13 = var43.iterator();
            } catch (Exception var25) {
               break label225;
            }

            Geocoder var37 = var9;

            while (true) {
               byte var7 = 0;

               Address var14;
               try {
                  if (!var13.hasNext()) {
                     break;
                  }

                  var14 = (Address)var13.next();
                  var56 = var14.getAddressLine(0);
               } catch (Exception var21) {
                  break label225;
               }

               if (var56 == null) {
                  try {
                     var56 = CollectionsKt.joinToString$default(
                        CollectionsKt.listOfNotNull(new String[]{var14.getThoroughfare(), var14.getLocality()}), ", ", null, null, 0, null, null, 62, null
                     );
                  } catch (Exception var20) {
                     break label225;
                  }
               }

               try {
                  var49 = var14.getFeatureName();
               } catch (Exception var19) {
                  break label225;
               }

               var2 = var49;
               if (var49 == null) {
                  var2 = "";
               }

               label159: {
                  label158: {
                     try {
                        if (var2.length() > 0) {
                           break label158;
                        }
                     } catch (Exception var24) {
                        break label225;
                     }

                     var6 = (boolean)0;
                     break label159;
                  }

                  var6 = (boolean)1;
               }

               label228: {
                  if (var6) {
                     try {
                        var50 = var2;
                     } catch (Exception var18) {
                        break label225;
                     }

                     var6 = var7;

                     label148: {
                        while (true) {
                           label145: {
                              try {
                                 if (var6 < var50.length()) {
                                    if (Character.isLetter(var50.charAt(var6))) {
                                       break;
                                    }
                                    break label145;
                                 }
                              } catch (Exception var23) {
                                 break label225;
                              }

                              var48 = false;
                              break label148;
                           }

                           var6++;
                        }

                        var48 = true;
                     }

                     if (var48) {
                        var9 = var37;

                        try {
                           if (!StringsKt.startsWith$default(var56, var2, false, 2, null)) {
                              break label228;
                           }
                        } catch (Exception var22) {
                           break label225;
                        }
                     } else {
                        var9 = var37;
                     }
                  } else {
                     var9 = var37;
                  }

                  try {
                     var2 = StringsKt.substringBefore$default(var56, ",", null, 2, null);
                  } catch (Exception var17) {
                     break label225;
                  }

                  var37 = var9;
               }

               try {
                  DestinationActivity.Place var52 = new DestinationActivity.Place(
                     var2, var56, var14.getLatitude(), var14.getLongitude(), null, 0.0, null, false, 240, null
                  );
                  var57.add(var52);
               } catch (Exception var16) {
                  break label225;
               }
            }

            try {
               var38 = var57;
               break label223;
            } catch (Exception var15) {
            }
         }

         var38 = null;
      }

      var0.ui.post(new DestinationActivity$$ExternalSyntheticLambda9(var3, var0, var38));
   }

   private static final void androidSearch$lambda$48$lambda$47(int var0, DestinationActivity var1, List var2) {
      if (var0 == var1.searchSeq && !var1.isFinishing()) {
         if (var2 == null) {
            TextView var4 = var1.status;
            TextView var3 = var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("status");
               var3 = null;
            }

            var3.setText("Search failed - is the tablet online?");
         } else {
            var1.results = CollectionsKt.sortedWith(var2, new Comparator(var1) {
               final DestinationActivity this$0;

               {
                  this.this$0 = var1;
               }

               @Override
               public final int compare(T var1, T var2x) {
                  var1 = var1;
                  Double var8 = this.this$0.dist(var1);
                  double var5 = 0.0;
                  double var3;
                  if (var8 != null) {
                     var3 = var8;
                  } else {
                     var3 = 0.0;
                  }

                  Comparable var9 = var3;
                  var2x = var2x;
                  Double var11 = this.this$0.dist(var2x);
                  var3 = var5;
                  if (var11 != null) {
                     var3 = var11;
                  }

                  return ComparisonsKt.compareValues(var9, var3);
               }
            });
            var1.resultsTitle = "";
            var1.render();
         }
      }
   }

   private final TextView chip(String var1, Function0<Unit> var2) {
      TextView var3 = new TextView((Context)this);
      var3.setText(var1);
      var3.setTextSize(15.0F);
      var3.setTextColor(-1);
      var3.setTypeface(var3.getTypeface(), 1);
      var3.setGravity(17);
      var3.setPadding(this.dp(16), this.dp(9), this.dp(16), this.dp(9));
      GradientDrawable var4 = new GradientDrawable();
      var4.setCornerRadius(this.dp(20));
      var4.setColor(Color.rgb(30, 35, 44));
      var3.setBackground((Drawable)var4);
      var3.setOnClickListener(new DestinationActivity$$ExternalSyntheticLambda21(var2));
      return var3;
   }

   private static final void chip$lambda$23$lambda$22(Function0 var0, View var1) {
      var0.invoke();
   }

   private final LayoutParams chipLp() {
      LayoutParams var1 = new LayoutParams(-2, -2);
      var1.setMarginEnd(this.dp(8));
      return var1;
   }

   private final Double dist(DestinationActivity.Place var1) {
      Pair var5 = this.here;
      if (var5 == null) {
         return null;
      } else {
         double var2 = var1.getLat();
         boolean var4;
         if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
            var4 = true;
         } else {
            var4 = false;
         }

         return !var4
            ? null
            : Math.hypot(
                  (var1.getLon() - ((Number)var5.getSecond()).doubleValue()) * 111320.0 * Math.cos(Math.toRadians(((Number)var5.getFirst()).doubleValue())),
                  (var1.getLat() - ((Number)var5.getFirst()).doubleValue()) * 110540.0
               )
               / 1609.344;
      }
   }

   private static final void doSearch$lambda$28(DestinationActivity var0) {
      EditText var2 = var0.edit;
      EditText var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("edit");
         var1 = null;
      }

      var0.runSearch(var1.getText().toString(), false);
   }

   private final int dp(int var1) {
      return MathKt.roundToInt(var1 * this.getResources().getDisplayMetrics().density);
   }

   private final String icon(String var1) {
      CharSequence var4 = var1;
      CharSequence var3 = "fuel";
      boolean var2 = false;
      if (StringsKt.contains$default(var4, var3, false, 2, null) || StringsKt.contains$default(var1, "gas", false, 2, null)) {
         var1 = "⛽";
      } else if (StringsKt.contains$default(var1, "charging", false, 2, null)) {
         var1 = "⚡";
      } else if (StringsKt.contains$default(var1, "cafe", false, 2, null) || StringsKt.contains$default(var1, "coffee", false, 2, null)) {
         var1 = "☕";
      } else if (StringsKt.contains$default(var1, "fast", false, 2, null)) {
         var1 = "\ud83c\udf54";
      } else if (StringsKt.contains$default(var1, "restaurant", false, 2, null) || StringsKt.contains$default(var1, "food", false, 2, null)) {
         var1 = "\ud83c\udf7d";
      } else if (StringsKt.contains$default(var1, "parking", false, 2, null)) {
         var1 = "\ud83c\udd7f";
      } else if (StringsKt.contains$default(var1, "grocery", false, 2, null)
         || StringsKt.contains$default(var1, "shop", false, 2, null)
         || StringsKt.contains$default(var1, "super", false, 2, null)) {
         var1 = "\ud83d\uded2";
      } else if (StringsKt.contains$default(var1, "pharmacy", false, 2, null)
         || StringsKt.contains$default(var1, "hospital", false, 2, null)
         || StringsKt.contains$default(var1, "doctor", false, 2, null)) {
         var1 = "\ud83d\udc8a";
      } else if (!StringsKt.contains$default(var1, "lodging", false, 2, null) && !StringsKt.contains$default(var1, "hotel", false, 2, null)) {
         if (var1.length() == 0) {
            var2 = true;
         }

         var1 = "\ud83d\udccd";
         if (var2) {
         }
      } else {
         var1 = "\ud83d\udecf";
      }

      return var1;
   }

   private static final Unit onCreate$lambda$11(DestinationActivity var0) {
      var0.along ^= true;
      var0.paintAlong();
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$12(DestinationActivity var0, String var1, String var2) {
      var0.quick(var1, var2);
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$19(DestinationActivity var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         Favorites.load$default(Favorites.INSTANCE, Link.INSTANCE.getData(), null, 2, null);
         var0.favorites = var0.places(var1.optJSONArray("favorites"), "name");
         var0.spRecents = var0.places(var1.optJSONArray("recents"), "place_name");
         if (var0.results == null) {
            var0.render();
         }
      }

      return Unit.INSTANCE;
   }

   private static final void onCreate$lambda$20(DestinationActivity var0) {
      Object var1 = var0.getSystemService("input_method");
      Intrinsics.checkNotNull(var1, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
      InputMethodManager var2 = (InputMethodManager)var1;
      var1 = var0.edit;
      EditText var3 = (EditText)var1;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("edit");
         var3 = null;
      }

      var2.showSoftInput((View)var3, 1);
   }

   private static final void onCreate$lambda$4$lambda$3(DestinationActivity var0, View var1) {
      var0.finish();
   }

   private static final boolean onCreate$lambda$7$lambda$6(DestinationActivity var0, EditText var1, TextView var2, int var3, KeyEvent var4) {
      boolean var5 = true;
      if (var3 != 3) {
         boolean var6;
         if (var4 != null && var4.getKeyCode() == 66) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (!var6) {
            var5 = false;
            return var5;
         }
      }

      var0.search(var1.getText().toString(), true);
      return var5;
   }

   private static final void onCreate$lambda$9$lambda$8(DestinationActivity var0, View var1) {
      EditText var2 = var0.edit;
      EditText var3 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("edit");
         var3 = null;
      }

      var3.setText("");
      var0.results = null;
      var0.render();
   }

   private final void paintAlong() {
      TextView var5 = this.alongBtn;
      Object var6 = null;
      TextView var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("alongBtn");
         var4 = null;
      }

      String var10;
      if (this.along) {
         var10 = "↗  Along the route";
      } else {
         var10 = "◎  Near me";
      }

      var4.setText(var10);
      var4 = this.alongBtn;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("alongBtn");
         var4 = (TextView)var6;
      }

      Drawable var8 = var4.getBackground();
      Intrinsics.checkNotNull(var8, "null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
      GradientDrawable var9 = (GradientDrawable)var8;
      byte var1;
      short var2;
      short var3;
      if (this.along) {
         var3 = 128;
         var2 = 237;
         var1 = 47;
      } else {
         var3 = 35;
         var2 = 44;
         var1 = 30;
      }

      var9.setColor(Color.rgb(var1, var3, var2));
   }

   private final void pick(DestinationActivity.Place var1) {
      Intent var3 = new Intent().putExtra("name", var1.getName()).putExtra("address", var1.getAddress());
      Intrinsics.checkNotNullExpressionValue(var3, "putExtra(...)");
      boolean var2;
      if (var1.getMapboxId().length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var3.putExtra("mapbox_id", var1.getMapboxId()).putExtra("search_id", this.searchId);
      } else {
         if (var1.getKeep()) {
            Companion.remember((Context)this, var1.getName(), var1.getAddress(), var1.getLat(), var1.getLon());
         }

         var3.putExtra("latitude", var1.getLat()).putExtra("longitude", var1.getLon());
      }

      this.setResult(-1, var3);
      this.finish();
   }

   private final void placeMenu(DestinationActivity.Place var1, JSONObject var2) {
      DataClient var7 = Link.INSTANCE.getData();
      UiSettings var8 = new UiSettings((Context)this);
      boolean var4 = true;
      boolean var3 = true;
      if (var2 == null) {
         String[] var15 = new String[]{"☆  Save", "\ud83c\udfe0  Save as Home", "\ud83d\udcbc  Save as Work"};
         Builder var14 = new Builder((Context)this);
         Object var11 = var1.getName();
         if (var11.length() != 0) {
            var3 = false;
         }

         if (var3) {
            var11 = var1.getAddress();
         }

         var14.setTitle((CharSequence)var11)
            .setItems(var15, new DestinationActivity$$ExternalSyntheticLambda14(var15, var1, var7, this))
            .setNegativeButton("Cancel", null)
            .show();
      } else {
         boolean var5 = Favorites.INSTANCE.pinned(var8, var1.getLat(), var1.getLon());
         String var9;
         if (var5) {
            var9 = "\ud83d\udccc  Take off the main screen";
         } else {
            var9 = "\ud83d\udccc  Show on the main screen (one tap to go)";
         }

         String var6 = var2.optString("label");
         Intrinsics.checkNotNullExpressionValue(var6, "optString(...)");
         if (var6.length() > 0) {
            var3 = var4;
         } else {
            var3 = false;
         }

         if (var3) {
            var6 = "★  Just a saved place (not Home / Work)";
         } else {
            var6 = null;
         }

         String[] var10 = ArraysKt.filterNotNull(
               new String[]{var9, "\ud83c\udfe0  Make it Home", "\ud83d\udcbc  Make it Work", var6, "\ud83d\uddd1  Remove from saved places"}
            )
            .toArray(new String[0]);
         new Builder((Context)this)
            .setTitle(Favorites.INSTANCE.title(var2))
            .setItems(var10, new DestinationActivity$$ExternalSyntheticLambda15(var10, var8, var2, var5, var7, this))
            .setNegativeButton("Cancel", null)
            .show();
      }
   }

   private static final void placeMenu$done(DestinationActivity var0, String var1, String var2) {
      Context var3 = (Context)var0;
      if (var1 == null) {
         var1 = var2;
      }

      Toast.makeText(var3, var1, 0).show();
      var0.favorites = var0.places(new JSONArray(Favorites.INSTANCE.getList()), "name");
      var0.render();
   }

   private static final void placeMenu$lambda$74(
      String[] var0, DestinationActivity.Place var1, DataClient var2, DestinationActivity var3, DialogInterface var4, int var5
   ) {
      String var11;
      switch (var5) {
         case 1:
            var11 = "home";
            break;
         case 2:
            var11 = "work";
            break;
         default:
            var11 = null;
      }

      String var9;
      if (var11 != null) {
         String var8 = StringsKt.substringAfter$default(var0[var5], "as ", null, 2, null);
         var9 = " as " + var8;
      } else {
         var9 = "";
      }

      String var10 = "Saved" + var9;
      double var6 = var1.getLat();
      boolean var12;
      if (!Double.isInfinite(var6) && !Double.isNaN(var6)) {
         var12 = true;
      } else {
         var12 = false;
      }

      if (var12) {
         Favorites.INSTANCE
            .save(var2, var1.getName(), var1.getAddress(), var1.getLat(), var1.getLon(), var11, new DestinationActivity$$ExternalSyntheticLambda10(var10, var3));
      } else {
         Favorites.INSTANCE
            .saveFound((Context)var3, var2, var1.getName(), var1.getAddress(), var11, new DestinationActivity$$ExternalSyntheticLambda12(var10, var3));
      }
   }

   private static final Unit placeMenu$lambda$74$lambda$72(String var0, DestinationActivity var1, String var2) {
      placeMenu$done(var1, var2, var0);
      return Unit.INSTANCE;
   }

   private static final Unit placeMenu$lambda$74$lambda$73(String var0, DestinationActivity var1, String var2) {
      placeMenu$done(var1, var2, var0);
      return Unit.INSTANCE;
   }

   private static final void placeMenu$lambda$79(
      String[] var0, UiSettings var1, JSONObject var2, boolean var3, DataClient var4, DestinationActivity var5, DialogInterface var6, int var7
   ) {
      if (StringsKt.startsWith$default(var0[var7], "\ud83d\udccc", false, 2, null)) {
         Favorites.INSTANCE.setPinned(var1, var2, var3 ^ true);
         String var8;
         if (var3) {
            var8 = "Taken off the main screen";
         } else {
            var8 = "On the main screen";
         }

         placeMenu$done(var5, null, var8);
      } else if (StringsKt.startsWith$default(var0[var7], "\ud83c\udfe0", false, 2, null)) {
         Favorites.INSTANCE.label(var4, var2, "home", new DestinationActivity$$ExternalSyntheticLambda11(var5));
      } else if (StringsKt.startsWith$default(var0[var7], "\ud83d\udcbc", false, 2, null)) {
         Favorites.INSTANCE.label(var4, var2, "work", new DestinationActivity$$ExternalSyntheticLambda16(var5));
      } else if (StringsKt.startsWith$default(var0[var7], "★", false, 2, null)) {
         Favorites.INSTANCE.label(var4, var2, null, new DestinationActivity$$ExternalSyntheticLambda17(var5));
      } else {
         Favorites.INSTANCE.remove((Context)var5, var4, var2, new DestinationActivity$$ExternalSyntheticLambda18(var5));
      }
   }

   private static final Unit placeMenu$lambda$79$lambda$75(DestinationActivity var0, String var1) {
      placeMenu$done(var0, var1, "Home set");
      return Unit.INSTANCE;
   }

   private static final Unit placeMenu$lambda$79$lambda$76(DestinationActivity var0, String var1) {
      placeMenu$done(var0, var1, "Work set");
      return Unit.INSTANCE;
   }

   private static final Unit placeMenu$lambda$79$lambda$77(DestinationActivity var0, String var1) {
      placeMenu$done(var0, var1, "Done");
      return Unit.INSTANCE;
   }

   private static final Unit placeMenu$lambda$79$lambda$78(DestinationActivity var0, String var1) {
      placeMenu$done(var0, var1, "Removed");
      return Unit.INSTANCE;
   }

   private final List<DestinationActivity.Place> places(JSONArray var1, String var2) {
      if (var1 == null) {
         return CollectionsKt.emptyList();
      } else {
         ArrayList var12 = new ArrayList();
         int var7 = 0;

         for (int var9 = var1.length(); var7 < var9; var7++) {
            JSONObject var13 = var1.optJSONObject(var7);
            if (var13 != null) {
               double var5 = var13.optDouble("latitude", Double.NaN);
               double var3 = var13.optDouble("longitude", Double.NaN);
               boolean var8;
               if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               if (var8) {
                  if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  if (var8) {
                     var var10 = var13.optString(var2);
                     if (var10.length() == 0) {
                        var8 = true;
                     } else {
                        var8 = false;
                     }

                     if (var8) {
                        var10 = var13.optString("name");
                        if (var10.length() == 0) {
                           var8 = true;
                        } else {
                           var8 = false;
                        }

                        if (var8) {
                           var10 = var13.optString("place_name");
                        }

                        var10 = var10;
                     }

                     var10 = var10;
                     Intrinsics.checkNotNull(var10);
                     List var11 = StringsKt.split$default(var10, new String[]{": "}, false, 2, 2, null);
                     Pair var20;
                     if (var11.size() == 2) {
                        var20 = TuplesKt.to(var11.get(0), var11.get(1));
                     } else {
                        var20 = TuplesKt.to(var10, var13.optString("address"));
                     }

                     String var23 = (String)var20.component1();
                     String var14 = (String)var20.component2();
                     var10 = var13.optString("label");
                     if (Intrinsics.areEqual(var10, "home")) {
                        var10 = "Home";
                     } else if (Intrinsics.areEqual(var10, "work")) {
                        var10 = "Work";
                     } else {
                        var10 = var23;
                     }

                     var12.add(new DestinationActivity.Place(var10, var14, var5, var3, null, 0.0, null, false, 240, null));
                  }
               }
            }
         }

         return var12;
      }
   }

   private final void quick(String var1, String var2) {
      DataClient var7 = Link.INSTANCE.getData();
      Pair var8 = this.here;
      String var6 = null;
      TextView var5 = null;
      if (var7 == null || !var7.getConnected()) {
         TextView var12 = this.status;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            var12 = var6;
         }

         var12.setText("Not connected to the comma");
      } else if (var8 == null) {
         TextView var11 = this.status;
         if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            var11 = var5;
         }

         var11.setText("The car's position isn't known yet");
      } else {
         this.searchSeq++;
         int var4 = this.searchSeq;
         boolean var3;
         if (this.route != null && this.along) {
            var3 = true;
         } else {
            var3 = false;
         }

         var6 = this.status;
         var5 = (TextView)var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            var5 = null;
         }

         var6 = StringsKt.substringAfter$default(var2, "  ", null, 2, null);
         Locale var9 = Locale.US;
         Intrinsics.checkNotNullExpressionValue(var9, "US");
         String var17 = var6.toLowerCase(var9);
         Intrinsics.checkNotNullExpressionValue(var17, "toLowerCase(...)");
         if (var3) {
            var6 = " along the route";
         } else {
            var6 = " nearby";
         }

         var5.setText("Looking for " + var17 + var6 + "…");
         HashMap var10 = new HashMap(
            MapsKt.mapOf(new Pair[]{TuplesKt.to("category", var1), TuplesKt.to("latitude", var8.getFirst()), TuplesKt.to("longitude", var8.getSecond())})
         );
         if (var3) {
            var10.put("route", this.route);
         }

         var7.request("nav_category", var10, new DestinationActivity$$ExternalSyntheticLambda19(var4, this, var2));
      }
   }

   private static final Unit quick$lambda$42(int var0, DestinationActivity var1, String var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var3, "r");
      if (var0 == var1.searchSeq && !var1.isFinishing()) {
         if (!var3.optBoolean("ok")) {
            String var24 = var3.optString("error");
            TextView var21 = var1.status;
            TextView var19 = var21;
            if (var21 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("status");
               var19 = null;
            }

            Intrinsics.checkNotNull(var24);
            CharSequence var22;
            if (StringsKt.contains$default(var24, "unknown action", false, 2, null)) {
               var22 = "The comma needs the StarView navigation update for this";
            } else {
               Object var23 = var24;
               boolean var18;
               if (var23.length() == 0) {
                  var18 = true;
               } else {
                  var18 = false;
               }

               if (var18) {
                  var23 = "No answer from the comma";
               }

               var22 = (CharSequence)var23;
            }

            var19.setText(var22);
            return Unit.INSTANCE;
         } else {
            JSONArray var11 = var3.optJSONArray("results");
            JSONArray var10 = var11;
            if (var11 == null) {
               var10 = new JSONArray();
            }

            Iterable var12 = RangesKt.until(0, var10.length());
            var var27 = new ArrayList();
            Iterator var13 = var12.iterator();

            while (var13.hasNext()) {
               JSONObject var32 = var10.optJSONObject(((IntIterator)var13).nextInt());
               if (var32 != null) {
                  var27.add(var32);
               }
            }

            List var28 = var27;
            Iterable var29 = var28;
            var var33 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var29, 10));

            for (JSONObject var16 : var29) {
               String var15 = var16.optString("name");
               Intrinsics.checkNotNullExpressionValue(var15, "optString(...)");
               String var14 = var16.optString("address");
               Intrinsics.checkNotNullExpressionValue(var14, "optString(...)");
               double var6 = var16.optDouble("latitude");
               double var8 = var16.optDouble("longitude");
               double var4 = var16.optDouble("distanceMeters", Double.NaN);
               var var30 = var16.optString("maki");
               boolean var17;
               if (var30.length() == 0) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               if (var17) {
                  var30 = var16.optString("category");
               }

               Intrinsics.checkNotNullExpressionValue(var30, "ifEmpty(...)");
               DestinationActivity.Place var31 = new DestinationActivity.Place(var15, var14, var6, var8, null, var4, var30, false, 16, null);
               var33.add(var31);
            }

            List var25 = var33;
            if (!var3.optBoolean("alongRoute")) {
               var25 = CollectionsKt.sortedWith(var25, new Comparator(var1) {
                  final DestinationActivity this$0;

                  {
                     this.this$0 = var1;
                  }

                  @Override
                  public final int compare(T var1, T var2x) {
                     DestinationActivity.Place var11x = (DestinationActivity.Place)var1;
                     var1 = var11x.getDistM();
                     double var3x = var1.doubleValue();
                     boolean var9 = Double.isInfinite(var3x);
                     boolean var8x = true;
                     boolean var7;
                     if (!var9 && !Double.isNaN(var3x)) {
                        var7 = true;
                     } else {
                        var7 = false;
                     }

                     Object var10x = null;
                     if (!var7) {
                        var1 = null;
                     }

                     double var5 = 99.0;
                     if (var1 != null) {
                        var3x = var1;
                     } else {
                        var1 = this.this$0.dist(var11x);
                        if (var1 != null) {
                           var3x = var1;
                        } else {
                           var3x = 99.0;
                        }

                        var3x *= 1609.344;
                     }

                     Comparable var24x = var3x;
                     DestinationActivity.Place var12x = (DestinationActivity.Place)var2x;
                     var2x = var12x.getDistM();
                     var3x = var2x.doubleValue();
                     if (!Double.isInfinite(var3x) && !Double.isNaN(var3x)) {
                        var7 = var8x;
                     } else {
                        var7 = false;
                     }

                     var1 = (Double)var10x;
                     if (var7) {
                        var1 = var2x;
                     }

                     if (var1 != null) {
                        var3x = var1;
                     } else {
                        var1 = this.this$0.dist(var12x);
                        var3x = var5;
                        if (var1 != null) {
                           var3x = var1;
                        }

                        var3x *= 1609.344;
                     }

                     return ComparisonsKt.compareValues(var24x, var3x);
                  }
               });
            }

            var1.results = var25;
            String var26 = StringsKt.substringAfter$default(var2, "  ", null, 2, null);
            if (var3.optBoolean("alongRoute")) {
               var2 = " along the route";
            } else {
               var2 = " nearby";
            }

            var1.resultsTitle = var26 + var2;
            var1.render();
            return Unit.INSTANCE;
         }
      } else {
         return Unit.INSTANCE;
      }
   }

   private final void render() {
      LinearLayout var5 = this.list;
      Object var4 = null;
      TextView var3 = null;
      LinearLayout var2 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
         var2 = null;
      }

      var2.removeAllViews();
      List var30 = this.results;
      if (var30 != null) {
         TextView var16 = this.status;
         if (var16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
         } else {
            var3 = var16;
         }

         Object var17;
         if (var30.isEmpty()) {
            var17 = "Nothing found - try adding the town";
         } else {
            var17 = this.resultsTitle;
            boolean var10;
            if (var17.length() == 0) {
               var10 = 1;
            } else {
               var10 = 0;
            }

            if (var10) {
               var10 = var30.size();
               var17 = var10 + " found";
            }
         }

         var17 = (CharSequence)var17;
         var3.setText((CharSequence)var17);
         var17 = var30.iterator();

         while (var17.hasNext()) {
            this.row((DestinationActivity.Place)var17.next());
         }
      } else {
         TextView var12 = this.status;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("status");
            var12 = (TextView)var4;
         }

         String var20;
         if (this.here == null) {
            var20 = "No GPS position yet - results won't be sorted by distance";
         } else {
            var20 = "";
         }

         var12.setText(var20);
         if (!this.favorites.isEmpty()) {
            this.section("Saved places");
            Iterator var13 = this.favorites.iterator();

            while (var13.hasNext()) {
               this.row((DestinationActivity.Place)var13.next());
            }
         }

         Iterable var21 = Companion.recents((Context)this);
         var var14 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var21, 10));

         for (JSONObject var31 : var21) {
            var4 = var31.optString("name");
            Intrinsics.checkNotNullExpressionValue(var4, "optString(...)");
            String var6 = var31.optString("address");
            Intrinsics.checkNotNullExpressionValue(var6, "optString(...)");
            var14.add(
               new DestinationActivity.Place((String)var4, var6, var31.optDouble("latitude"), var31.optDouble("longitude"), null, 0.0, null, false, 240, null)
            );
         }

         List var15 = var14;
         if (!var15.isEmpty()) {
            this.section("Recent on this tablet");
            Iterator var23 = var15.iterator();

            while (var23.hasNext()) {
               this.row((DestinationActivity.Place)var23.next());
            }
         }

         var4 = this.spRecents;
         var var24 = new ArrayList();

         for (Object var32 : var4) {
            DestinationActivity.Place var33 = (DestinationActivity.Place)var32;
            var var7 = var15;
            boolean var9;
            if (var7 instanceof Collection && ((Collection)var7).isEmpty()) {
               var9 = true;
            } else {
               Iterator var34 = var7.iterator();

               while (true) {
                  if (!var34.hasNext()) {
                     var9 = true;
                     break;
                  }

                  DestinationActivity.Place var8 = (DestinationActivity.Place)var34.next();
                  if (Math.abs(var8.getLat() - var33.getLat()) < 1.0E-4 && Math.abs(var8.getLon() - var33.getLon()) < 1.0E-4) {
                     var9 = true;
                  } else {
                     var9 = false;
                  }

                  if (var9) {
                     var9 = false;
                     break;
                  }
               }
            }

            if (var9) {
               var24.add(var32);
            }
         }

         List var25 = var24;
         if (!var25.isEmpty()) {
            this.section("Recent in StarPilot");
            var4 = var25.iterator();

            while (var4.hasNext()) {
               this.row((DestinationActivity.Place)var4.next());
            }
         }

         if (this.favorites.isEmpty() && var15.isEmpty() && var25.isEmpty()) {
            this.section("Type a place or an address, or pick a kind of place above");
         }
      }
   }

   private final void row(DestinationActivity.Place var1) {
      LinearLayout var8 = new LinearLayout((Context)this);
      var8.setOrientation(0);
      var8.setGravity(16);
      var8.setPadding(this.dp(14), this.dp(12), this.dp(14), this.dp(12));
      GradientDrawable var6 = new GradientDrawable();
      var6.setCornerRadius(this.dp(12));
      var6.setColor(Color.rgb(22, 25, 32));
      var8.setBackground((Drawable)var6);
      boolean var5 = true;
      var8.setClickable(true);
      TypedValue var19 = new TypedValue();
      var8.getContext().getTheme().resolveAttribute(16843534, var19, true);
      var8.setForeground(var8.getContext().getDrawable(var19.resourceId));
      var8.setOnClickListener(new DestinationActivity$$ExternalSyntheticLambda6(this, var1));
      double var2 = var1.getLat();
      boolean var4;
      if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      JSONObject var7;
      if (var4) {
         var7 = Favorites.INSTANCE.find(var1.getLat(), var1.getLon());
      } else {
         var7 = null;
      }

      var8.setOnLongClickListener(new DestinationActivity$$ExternalSyntheticLambda7(this, var1, var7));
      TextView var9 = new TextView((Context)this);
      String var20;
      if (var7 != null) {
         var20 = Favorites.INSTANCE.icon(var7);
      } else {
         var20 = this.icon(var1.getKind());
      }

      var9.setText(var20);
      var9.setTextSize(22.0F);
      var9.setPadding(0, 0, this.dp(14), 0);
      var8.addView((View)var9);
      LinearLayout var32 = new LinearLayout((Context)this);
      var32.setOrientation(1);
      TextView var10 = new TextView((Context)this);
      TextView var21 = var1.getName();
      if (var21.length() == 0) {
         var4 = (boolean)1;
      } else {
         var4 = (boolean)0;
      }

      if (var4) {
         var21 = var1.getAddress();
      }

      var10.setText((CharSequence)var21);
      var10.setTextColor(-1);
      var10.setTextSize(18.0F);
      var10.setTypeface(var10.getTypeface(), 1);
      var32.addView((View)var10);
      if (var1.getAddress().length() > 0) {
         var4 = (boolean)1;
      } else {
         var4 = (boolean)0;
      }

      if (var4 && !Intrinsics.areEqual(var1.getAddress(), var1.getName())) {
         var21 = new TextView((Context)this);
         var21.setText(var1.getAddress());
         var21.setTextColor(Color.rgb(154, 161, 172));
         var21.setTextSize(14.0F);
         var32.addView((View)var21);
      }

      var8.addView((View)var32, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      var2 = var1.getDistM();
      if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
         var4 = (boolean)1;
      } else {
         var4 = (boolean)0;
      }

      Double var23;
      if (var4) {
         var23 = var1.getDistM() / 1609.344;
      } else {
         var23 = this.dist(var1);
      }

      if (var23 != null) {
         var2 = var23.doubleValue();
         var9 = new TextView((Context)this);
         CharSequence var24;
         if (var2 < 0.1) {
            var24 = "here";
         } else {
            String var25;
            if (var2 < 10.0) {
               var25 = String.format("%.1f mi", Arrays.copyOf(new Object[]{var2}, 1));
            } else {
               var25 = String.format("%.0f mi", Arrays.copyOf(new Object[]{var2}, 1));
            }

            Intrinsics.checkNotNullExpressionValue(var25, "format(...)");
            var24 = var25;
         }

         var9.setText(var24);
         var9.setTextColor(Color.rgb(154, 161, 172));
         var9.setTextSize(15.0F);
         var9.setPadding(this.dp(12), 0, 0, 0);
         var8.addView((View)var9);
      }

      if (var7 != null && Favorites.INSTANCE.pinned(new UiSettings((Context)this), var1.getLat(), var1.getLon())) {
         var4 = var5;
      } else {
         var4 = (boolean)0;
      }

      var9 = new TextView((Context)this);
      String var26;
      if (var7 == null) {
         var26 = "☆";
      } else if (var4) {
         var26 = "★\ud83d\udccc";
      } else {
         var26 = "★";
      }

      CharSequence var27 = var26;
      var9.setText(var27);
      var9.setTextSize(24.0F);
      if (var7 == null) {
         var4 = Color.rgb(154, 161, 172);
      } else {
         var4 = Color.rgb(255, 200, 87);
      }

      var9.setTextColor(var4);
      var9.setPadding(this.dp(16), 0, this.dp(4), 0);
      var9.setOnClickListener(new DestinationActivity$$ExternalSyntheticLambda8(this, var1, var7));
      var8.addView((View)var9);
      LinearLayout var28 = this.list;
      LinearLayout var11 = var28;
      if (var28 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
         var11 = null;
      }

      View var29 = (View)var8;
      LayoutParams var30 = new LayoutParams(-1, -2);
      var30.bottomMargin = this.dp(8);
      Unit var31 = Unit.INSTANCE;
      var11.addView(var29, (android.view.ViewGroup.LayoutParams)var30);
   }

   private static final void row$lambda$59$lambda$58(DestinationActivity var0, DestinationActivity.Place var1, View var2) {
      var0.pick(var1);
   }

   private static final boolean row$lambda$60(DestinationActivity var0, DestinationActivity.Place var1, JSONObject var2, View var3) {
      var0.placeMenu(var1, var2);
      return true;
   }

   private static final void row$lambda$69$lambda$68(DestinationActivity var0, DestinationActivity.Place var1, JSONObject var2, View var3) {
      var0.placeMenu(var1, var2);
   }

   private final void runSearch(String var1, boolean var2) {
      String var6 = StringsKt.trim(var1).toString();
      if (var6.length() >= 2) {
         this.searchSeq++;
         int var3 = this.searchSeq;
         DataClient var5 = Link.INSTANCE.getData();
         if (this.commaSearch && var5 != null && var5.getConnected()) {
            TextView var4 = this.status;
            TextView var7 = var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("status");
               var7 = null;
            }

            var7.setText("Searching…");
            HashMap var8 = new HashMap(
               MapsKt.mapOf(new Pair[]{TuplesKt.to("query", var6), TuplesKt.to("searchId", this.searchId), TuplesKt.to("clientId", CLIENT_ID)})
            );
            Pair var9 = this.here;
            if (var9 != null) {
               var8.put("latitude", var9.getFirst());
               var8.put("longitude", var9.getSecond());
            }

            var5.request("nav_search", var8, new DestinationActivity$$ExternalSyntheticLambda13(var3, this, var6, var2));
         } else {
            if (var6.length() >= 3) {
               this.androidSearch(var6, var3);
            }
         }
      }
   }

   private static final Unit runSearch$lambda$34(int var0, DestinationActivity var1, String var2, boolean var3, JSONObject var4) {
      Intrinsics.checkNotNullParameter(var4, "r");
      if (var0 != var1.searchSeq || var1.isFinishing()) {
         return Unit.INSTANCE;
      } else if (!var4.optBoolean("ok")) {
         String var30 = var4.optString("error");
         Intrinsics.checkNotNull(var30);
         CharSequence var32 = var30;
         CharSequence var24 = "new destination search";
         TextView var19 = null;
         if (StringsKt.contains$default(var32, var24, false, 2, null)) {
            var1.searchId = UUID.randomUUID().toString();
         } else {
            var1.commaSearch = false;
            TextView var25 = var1.status;
            if (var25 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("status");
            } else {
               var19 = var25;
            }

            var24 = var30;
            boolean var20;
            if (var24.length() == 0) {
               var20 = true;
            } else {
               var20 = false;
            }

            if (var20) {
               var24 = "no answer";
            }

            var19.setText("Mapbox search isn't available (" + var24 + ") - Android's address search instead");
         }

         if (var2.length() >= 3) {
            var1.androidSearch(var2, var0);
         }

         return Unit.INSTANCE;
      } else {
         JSONArray var9 = var4.optJSONArray("results");
         JSONArray var17 = var9;
         if (var9 == null) {
            var17 = new JSONArray();
         }

         Iterable var10 = RangesKt.until(0, var17.length());
         var var21 = new ArrayList();
         Iterator var27 = var10.iterator();

         while (var27.hasNext()) {
            JSONObject var11 = var17.optJSONObject(((IntIterator)var27).nextInt());
            if (var11 != null) {
               var21.add(var11);
            }
         }

         List var22 = var21;
         Iterable var23 = var22;
         var var31 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var23, 10));
         boolean var8 = false;

         for (JSONObject var16 : var23) {
            String var13 = var16.optString("name");
            Intrinsics.checkNotNullExpressionValue(var13, "optString(...)");
            String var14 = var16.optString("description");
            Intrinsics.checkNotNullExpressionValue(var14, "optString(...)");
            String var15 = var16.optString("id");
            Intrinsics.checkNotNullExpressionValue(var15, "optString(...)");
            double var5 = var16.optDouble("distanceMeters", Double.NaN);
            var var28 = var16.optString("maki");
            boolean var7;
            if (var28.length() == 0) {
               var7 = true;
            } else {
               var7 = false;
            }

            if (var7) {
               var28 = var16.optString("category");
            }

            Intrinsics.checkNotNullExpressionValue(var28, "ifEmpty(...)");
            DestinationActivity.Place var29 = new DestinationActivity.Place(var13, var14, Double.NaN, Double.NaN, var15, var5, var28, false, 128, null);
            var31.add(var29);
         }

         List var18 = var31;
         if (!var18.isEmpty() || !var3 && var2.length() < 4) {
            var1.results = var18;
            var1.resultsTitle = "";
            var1.render();
            return Unit.INSTANCE;
         } else {
            var1.androidSearch(var2, var0);
            return Unit.INSTANCE;
         }
      }
   }

   private final void search(String var1, boolean var2) {
      this.ui.removeCallbacks(this.doSearch);
      if (StringsKt.trim(var1).toString().length() < 2) {
         this.results = null;
         this.render();
      } else {
         if (var2) {
            this.runSearch(var1, true);
         } else {
            this.ui.postDelayed(this.doSearch, 300L);
         }
      }
   }

   private final void section(String var1) {
      LinearLayout var3 = this.list;
      LinearLayout var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
         var2 = null;
      }

      TextView var6 = new TextView((Context)this);
      Locale var4 = Locale.US;
      Intrinsics.checkNotNullExpressionValue(var4, "US");
      var1 = var1.toUpperCase(var4);
      Intrinsics.checkNotNullExpressionValue(var1, "toUpperCase(...)");
      var6.setText(var1);
      var6.setTextColor(Color.rgb(110, 118, 130));
      var6.setTextSize(13.0F);
      var6.setLetterSpacing(0.08F);
      var6.setTypeface(var6.getTypeface(), 1);
      var6.setPadding(this.dp(4), this.dp(18), this.dp(4), this.dp(6));
      var2.addView((View)var6);
   }

   @Override
   protected void attachBaseContext(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "base");
      super.attachBaseContext(NarrowDensity.wrap$default(NarrowDensity.INSTANCE, var1, 0, 0.0F, 6, null));
   }

   @Override
   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      double var4 = this.getIntent().getDoubleExtra("lat", Double.NaN);
      double var2 = this.getIntent().getDoubleExtra("lon", Double.NaN);
      boolean var6;
      if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var6) {
         if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
            var6 = true;
         } else {
            var6 = false;
         }

         label102:
         if (var6) {
            if (var4 == 0.0) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6) {
               if (var2 == 0.0) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               if (var6) {
                  break label102;
               }
            }

            this.here = TuplesKt.to(var4, var2);
         }
      }

      label94: {
         var11 = this.getIntent().getStringExtra("route");
         if (var11 != null) {
            if (var11.length() >= 10) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6) {
               break label94;
            }
         }

         var11 = null;
      }

      this.route = var11;
      LinearLayout var8 = new LinearLayout((Context)this);
      var8.setOrientation(1);
      var8.setBackgroundColor(Color.rgb(11, 13, 18));
      var8.setPadding(this.dp(20), this.dp(16), this.dp(20), this.dp(8));
      LinearLayout var9 = new LinearLayout((Context)this);
      var9.setOrientation(0);
      var9.setGravity(16);
      TextView var12 = new TextView((Context)this);
      var12.setText("‹");
      var12.setTextSize(34.0F);
      var12.setTextColor(-1);
      var12.setPadding(this.dp(4), 0, this.dp(16), this.dp(4));
      var12.setOnClickListener(new DestinationActivity$$ExternalSyntheticLambda22(this));
      var9.addView((View)var12);
      EditText var13 = new EditText((Context)this);
      var13.setHint("Search places or addresses");
      var13.setHintTextColor(Color.rgb(128, 134, 144));
      var13.setTextColor(-1);
      var13.setTextSize(20.0F);
      var13.setInputType(8193);
      var13.setImeOptions(3);
      var13.setSingleLine(true);
      GradientDrawable var7 = new GradientDrawable();
      var7.setCornerRadius(this.dp(14));
      var7.setColor(Color.rgb(27, 31, 39));
      var13.setBackground((Drawable)var7);
      var13.setPadding(this.dp(16), this.dp(12), this.dp(16), this.dp(12));
      var13.setOnEditorActionListener(new DestinationActivity$$ExternalSyntheticLambda23(this, var13));
      var13.addTextChangedListener(new TextWatcher() {

         public void afterTextChanged(Editable var1) {
            DestinationActivity var3 = DestinationActivity.this;
            String var4x;
            if (var1 != null) {
               var4x = var1.toString();
            } else {
               var4x = null;
            }

            String var2x = var4x;
            if (var4x == null) {
               var2x = "";
            }

            var3.search(var2x, false);
         }

         public void beforeTextChanged(CharSequence var1, int var2x, int var3, int var4x) {
         }

         public void onTextChanged(CharSequence var1, int var2x, int var3, int var4x) {
         }
      });
      this.edit = var13;
      EditText var30 = this.edit;
      EditText var14 = var30;
      if (var30 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("edit");
         var14 = null;
      }

      var9.addView((View)var14, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      TextView var15 = new TextView((Context)this);
      var15.setText("✕");
      var15.setTextSize(22.0F);
      var15.setTextColor(Color.rgb(154, 161, 172));
      var15.setPadding(this.dp(14), 0, this.dp(4), 0);
      var15.setOnClickListener(new DestinationActivity$$ExternalSyntheticLambda1(this));
      var9.addView((View)var15);
      var8.addView((View)var9);
      var9 = new LinearLayout((Context)this);
      var9.setOrientation(0);
      var9.setPadding(0, this.dp(12), 0, this.dp(2));
      this.alongBtn = this.chip("", new DestinationActivity$$ExternalSyntheticLambda2(this));
      if (this.route != null) {
         TextView var31 = this.alongBtn;
         TextView var16 = var31;
         if (var31 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alongBtn");
            var16 = null;
         }

         var9.addView((View)var16, (android.view.ViewGroup.LayoutParams)this.chipLp());
         this.paintAlong();
      }

      for (Pair var10 : QUICK) {
         String var32 = (String)var10.component1();
         String var41 = (String)var10.component2();
         var9.addView(
            (View)this.chip(var41, new DestinationActivity$$ExternalSyntheticLambda3(this, var32, var41)), (android.view.ViewGroup.LayoutParams)this.chipLp()
         );
      }

      HorizontalScrollView var18 = new HorizontalScrollView((Context)this);
      var18.setHorizontalScrollBarEnabled(false);
      var18.addView((View)var9);
      var8.addView((View)var18);
      TextView var19 = new TextView((Context)this);
      var19.setTextColor(Color.rgb(143, 163, 199));
      var19.setTextSize(14.0F);
      var19.setPadding(this.dp(4), this.dp(10), this.dp(4), this.dp(4));
      this.status = var19;
      TextView var33 = this.status;
      TextView var20 = var33;
      if (var33 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("status");
         var20 = null;
      }

      var8.addView((View)var20);
      LinearLayout var21 = new LinearLayout((Context)this);
      var21.setOrientation(1);
      this.list = var21;
      ScrollView var40 = new ScrollView((Context)this);
      LinearLayout var34 = this.list;
      LinearLayout var22 = var34;
      if (var34 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
         var22 = null;
      }

      var40.addView((View)var22);
      var8.addView((View)var40, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, 0, 1.0F)));
      this.setContentView((View)var8);
      String var23 = this.getIntent().getStringExtra("query");
      if (var23 != null) {
         if (StringsKt.isBlank(var23)) {
            var23 = null;
         }

         if (var23 != null) {
            EditText var37 = this.edit;
            EditText var35 = var37;
            if (var37 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("edit");
               var35 = null;
            }

            var35.setText(var23);
            EditText var38 = this.edit;
            EditText var36 = var38;
            if (var38 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("edit");
               var36 = null;
            }

            var36.setSelection(var23.length());
            this.search(var23, true);
         }
      }

      this.favorites = this.places(new JSONArray(Favorites.INSTANCE.getList()), "name");
      DataClient var24 = Link.INSTANCE.getData();
      if (var24 != null) {
         DataClient.request$default(var24, "nav_list", null, new DestinationActivity$$ExternalSyntheticLambda4(this), 2, null);
      }

      this.render();
      EditText var25 = this.edit;
      if (var25 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("edit");
         var25 = null;
      }

      var25.requestFocus();
      this.ui.postDelayed(new DestinationActivity$$ExternalSyntheticLambda5(this), 250L);
   }

   @Override
   protected void onDestroy() {
      this.bg.shutdownNow();
      super.onDestroy();
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      public final List<JSONObject> recents(Context var1) {
         Intrinsics.checkNotNullParameter(var1, "ctx");

         JSONArray var2;
         ArrayList var10;
         try {
            var2 = new JSONArray(var1.getSharedPreferences("starview_nav", 0).getString("recents", "[]"));
            Iterable var8 = RangesKt.until(0, var2.length());
            var10 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10));
            var10 = var10;
            var9 = var8.iterator();
         } catch (Exception var5) {
            return CollectionsKt.emptyList();
         }

         while (true) {
            try {
               if (!var9.hasNext()) {
                  break;
               }

               var10.add(var2.getJSONObject(((IntIterator)var9).nextInt()));
            } catch (Exception var6) {
               return CollectionsKt.emptyList();
            }
         }

         try {
            var7 = var10;
         } catch (Exception var4) {
            var7 = CollectionsKt.emptyList();
         }

         return var7;
      }

      public final void remember(Context var1, String var2, String var3, double var4, double var6) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var2, "name");
         Intrinsics.checkNotNullParameter(var3, "address");
         Iterable var11 = this.recents(var1);
         boolean var9 = false;
         var var13 = new ArrayList();

         for (Object var14 : var11) {
            JSONObject var15 = (JSONObject)var14;
            boolean var8;
            if (Math.abs(var15.optDouble("latitude") - var4) < 1.0E-5 && Math.abs(var15.optDouble("longitude") - var6) < 1.0E-5) {
               var8 = true;
            } else {
               var8 = false;
            }

            if (!var8) {
               var13.add(var14);
            }
         }

         List var10 = var13;
         JSONArray var16 = new JSONArray().put(new JSONObject().put("name", var2).put("address", var3).put("latitude", var4).put("longitude", var6));
         Iterator var17 = CollectionsKt.take(var10, 11).iterator();

         while (var17.hasNext()) {
            var16.put((JSONObject)var17.next());
         }

         var1.getSharedPreferences("starview_nav", 0).edit().putString("recents", var16.toString()).apply();
      }
   }

   private static final class Place {
      private final String address;
      private final double distM;
      private final boolean keep;
      private final String kind;
      private final double lat;
      private final double lon;
      private final String mapboxId;
      private final String name;

      public Place(String var1, String var2, double var3, double var5, String var7, double var8, String var10, boolean var11) {
         Intrinsics.checkNotNullParameter(var1, "name");
         Intrinsics.checkNotNullParameter(var2, "address");
         Intrinsics.checkNotNullParameter(var7, "mapboxId");
         Intrinsics.checkNotNullParameter(var10, "kind");
         super();
         this.name = var1;
         this.address = var2;
         this.lat = var3;
         this.lon = var5;
         this.mapboxId = var7;
         this.distM = var8;
         this.kind = var10;
         this.keep = var11;
      }

      // $VF: synthetic method
      public Place(
         String var1, String var2, double var3, double var5, String var7, double var8, String var10, boolean var11, int var12, DefaultConstructorMarker var13
      ) {
         if ((var12 & 16) != 0) {
            var7 = "";
         }

         if ((var12 & 32) != 0) {
            var8 = Double.NaN;
         }

         if ((var12 & 64) != 0) {
            var10 = "";
         }

         if ((var12 & 128) != 0) {
            var11 = true;
         }

         this(var1, var2, var3, var5, var7, var8, var10, var11);
      }

      public final String getAddress() {
         return this.address;
      }

      public final double getDistM() {
         return this.distM;
      }

      public final boolean getKeep() {
         return this.keep;
      }

      public final String getKind() {
         return this.kind;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLon() {
         return this.lon;
      }

      public final String getMapboxId() {
         return this.mapboxId;
      }

      public final String getName() {
         return this.name;
      }
   }
}
