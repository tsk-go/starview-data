package com.starpilot.starview;

import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.DoubleRef;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.OkHttpClient.Builder;

public final class SharedPlace {
   public static final SharedPlace INSTANCE = new SharedPlace();
   private static final String NUM = "(-?\\d{1,3}(?:\\.\\d+)?)";
   private static final Regex SHORT = new Regex("https?://(maps\\.app\\.goo\\.gl|goo\\.gl/maps|g\\.co/kgs)/\\S+");

   private SharedPlace() {
   }

   // $VF: synthetic method
   public static SharedPlace.Place parse$default(SharedPlace var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      return var0.parse(var1, var2);
   }

   private static final boolean parse$take(DoubleRef var0, DoubleRef var1, MatchResult var2) {
      if (var2 == null) {
         return false;
      } else {
         var0.element = Double.parseDouble(var2.getGroupValues().get(1));
         var1.element = Double.parseDouble(var2.getGroupValues().get(2));
         return true;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final String follow(String var1) {
      Intrinsics.checkNotNullParameter(var1, "url");
      Object var6 = null;

      OkHttpClient var7;
      try {
         Builder var4 = new Builder();
         var7 = var4.followRedirects(false).callTimeout(15L, TimeUnit.SECONDS).build();
      } catch (Exception var31) {
         return (String)var6;
      }

      int var2 = 0;

      while (true) {
         if (var2 >= 6) {
            return var1;
         }

         Response var39;
         try {
            okhttp3.Request.Builder var35 = new okhttp3.Request.Builder();
            var39 = var7.newCall(var35.url(var1).header("User-Agent", "Mozilla/5.0 (Linux; Android 14) StarView").build()).execute();
         } catch (Exception var30) {
            return (String)var6;
         }

         String var38;
         label180: {
            try {
               Response var37 = var39;
               if (var37.isRedirect()) {
                  var38 = Response.header$default(var37, "Location", null, 2, null);
                  break label180;
               }
            } catch (Throwable var32) {
               Throwable var36 = var32;

               try {
                  throw var36;
               } finally {
                  try {
                     CloseableKt.closeFinally(var39, var36);
                  } catch (Exception var26) {
                     return (String)var6;
                  }
               }
            }

            var38 = null;
         }

         try {
            CloseableKt.closeFinally(var39, null);
         } catch (Exception var29) {
            return (String)var6;
         }

         if (var38 == null) {
            return var1;
         }

         label189: {
            label188: {
               try {
                  if (StringsKt.startsWith$default(var38, "http", false, 2, null)) {
                     break label188;
                  }
               } catch (Exception var33) {
                  return (String)var6;
               }

               try {
                  URI var40 = new URI(var1);
                  var1 = var40.resolve(var38).toString();
                  Intrinsics.checkNotNullExpressionValue(var1, "toString(...)");
                  break label189;
               } catch (Exception var28) {
                  return (String)var6;
               }
            }

            var1 = var38;
         }

         label201: {
            boolean var3;
            try {
               if (this.isShort(var1)) {
                  break label201;
               }

               if (StringsKt.contains$default(var1, "!3d", false, 2, null) || StringsKt.contains$default(var1, "@", false, 2, null)) {
                  break;
               }

               var3 = StringsKt.contains$default(var1, "q=", false, 2, null);
            } catch (Exception var34) {
               return (String)var6;
            }

            if (var3) {
               break;
            }
         }

         var2++;
      }

      return var1;
   }

   public final boolean isShort(String var1) {
      Intrinsics.checkNotNullParameter(var1, "url");
      return SHORT.containsMatchIn(var1);
   }

   public final String link(String var1) {
      Intrinsics.checkNotNullParameter(var1, "text");
      Regex var3 = new Regex("https?://\\S+");
      CharSequence var4 = var1;
      Object var2 = null;
      MatchResult var6 = Regex.find$default(var3, var4, 0, 2, null);
      var1 = (String)var2;
      if (var6 != null) {
         String var7 = var6.getValue();
         var1 = (String)var2;
         if (var7 != null) {
            var1 = StringsKt.trimEnd(var7, new char[]{'.', ',', ')'});
         }
      }

      return var1;
   }

   public final SharedPlace.Place parse(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "text");
      Iterable var7 = StringsKt.lines(var1);
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));
      Iterator var48 = var7.iterator();

      while (var48.hasNext()) {
         var6.add(StringsKt.trim((String & CharSequence)var48.next()).toString());
      }

      List var44 = var6;
      var7 = var44;
      var6 = new ArrayList();
      Iterator var9 = var7.iterator();

      while (true) {
         boolean var5 = var9.hasNext();
         boolean var4 = true;
         if (!var5) {
            List var61 = var6;
            String var59 = CollectionsKt.getOrNull(var61, 0);
            String var51 = "";
            String var46 = var59;
            if (var59 == null) {
               var46 = "";
            }

            String var62 = CollectionsKt.getOrNull(var61, 1);
            var59 = var62;
            if (var62 == null) {
               var59 = "";
            }

            if (var2 == null) {
               String var63 = this.link(var1);
               var2 = var63;
               if (var63 == null) {
                  var1 = StringsKt.trim(var1).toString();
                  if (!StringsKt.startsWith$default(var1, "geo:", false, 2, null)) {
                     var1 = null;
                  }

                  var2 = var1;
                  if (var1 == null) {
                     var2 = var51;
                  }
               }
            }

            try {
               var64 = URLDecoder.decode(var2, "UTF-8");
            } catch (Exception var13) {
               var64 = var2;
            }

            DoubleRef var11 = new DoubleRef();
            var11.element = Double.NaN;
            DoubleRef var10 = new DoubleRef();
            var10.element = Double.NaN;
            String var52 = NUM;
            var1 = NUM;
            Regex var16 = new Regex("!3d" + var52 + "!4d" + var1);
            Intrinsics.checkNotNull(var64);
            if (!parse$take(var11, var10, Regex.find$default(var16, var64, 0, 2, null))) {
               label150: {
                  String var53 = NUM;
                  var1 = NUM;
                  var18 = Regex.find$default(new Regex("^geo:" + var53 + "," + var1), var64, 0, 2, null);
                  if (var18 != null) {
                     boolean var37;
                     if (Double.parseDouble(var18.getGroupValues().get(1)) == 0.0) {
                        var37 = true;
                     } else {
                        var37 = false;
                     }

                     label146: {
                        if (var37) {
                           if (Double.parseDouble(var18.getGroupValues().get(2)) == 0.0) {
                              var37 = true;
                           } else {
                              var37 = false;
                           }

                           if (var37) {
                              var37 = false;
                              break label146;
                           }
                        }

                        var37 = true;
                     }

                     if (var37) {
                        break label150;
                     }
                  }

                  var18 = null;
               }

               if (!parse$take(var11, var10, var18)) {
                  String var54 = NUM;
                  var1 = NUM;
                  if (!parse$take(
                     var11, var10, Regex.find$default(new Regex("[?&](?:q|ll|query|destination|daddr|sll)=" + var54 + ",\\s*" + var1), var64, 0, 2, null)
                  )) {
                     String var55 = NUM;
                     var1 = NUM;
                     if (!parse$take(var11, var10, Regex.find$default(new Regex("geo:0,0\\?q=" + var55 + "," + var1), var64, 0, 2, null))) {
                        String var56 = NUM;
                        var1 = NUM;
                        parse$take(var11, var10, Regex.find$default(new Regex("@" + var56 + "," + var1), var64, 0, 2, null));
                     }
                  }
               }
            }

            boolean var40;
            if (var46.length() == 0) {
               var40 = true;
            } else {
               var40 = false;
            }

            Object var23;
            if (var40) {
               var23 = Regex.find$default(new Regex("/place/([^/@]+)"), var64, 0, 2, null);
               if (var23 != null) {
                  var23 = StringsKt.replace$default(var23.getGroupValues().get(1), '+', ' ', false, 4, null);
               } else {
                  var23 = Regex.find$default(new Regex("\\(([^)]+)\\)\\s*$"), var64, 0, 2, null);
                  if (var23 != null) {
                     var23 = var23.getGroupValues().get(1);
                  } else {
                     label134: {
                        var23 = Regex.find$default(new Regex("[?&](?:q|query|address|daddr)=([^&]+)"), var64, 0, 2, null);
                        if (var23 != null) {
                           List var26 = var23.getGroupValues();
                           if (var26 != null) {
                              String var27 = (String)var26.get(1);
                              if (var27 != null) {
                                 String var57 = StringsKt.replace$default(var27, '+', ' ', false, 4, null);
                                 if (var57 != null) {
                                    String var12 = NUM;
                                    String var28 = NUM;
                                    if (new Regex("^" + var12 + ",\\s*" + var28 + "$").matches(var57)) {
                                       var57 = null;
                                    }

                                    var23 = var46;
                                    if (var57 != null) {
                                       var23 = var57;
                                    }
                                    break label134;
                                 }
                              }
                           }
                        }

                        var23 = var46;
                     }
                  }
               }
            } else {
               var23 = var46;
            }

            Object var47 = var23;
            if (StringsKt.startsWith$default(var2, "geo:", false, 2, null)) {
               if (((CharSequence)var23).length() == 0) {
                  var40 = true;
               } else {
                  var40 = false;
               }

               var47 = var23;
               if (var40) {
                  MatchResult var32 = Regex.find$default(new Regex("[?&]q=([^&(]+)"), var64, 0, 2, null);
                  var47 = var23;
                  if (var32 != null) {
                     var47 = StringsKt.trim(StringsKt.replace$default(var32.getGroupValues().get(1), '+', ' ', false, 4, null)).toString();
                  }
               }
            }

            Iterable var33 = CollectionsKt.listOf(new String[]{(String)var47, var59});
            var var29 = new ArrayList();

            for (Object var58 : var33) {
               if (((String)var58).length() > 0) {
                  var40 = true;
               } else {
                  var40 = false;
               }

               if (var40) {
                  var29.add(var58);
               }
            }

            List var30 = var29;
            var2 = CollectionsKt.joinToString$default(var30, ", ", null, null, 0, null, null, 62, null);
            var23 = (CharSequence)var47;
            if (var23.length() == 0) {
               var40 = true;
            } else {
               var40 = false;
            }

            if (var40) {
               var23 = "Shared place";
            }

            return new SharedPlace.Place((String)var23, var59, var11.element, var10.element, var2);
         }

         Object var8 = var9.next();
         String var50 = (String)var8;
         boolean var3;
         if (var50.length() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3 && !StringsKt.startsWith$default(var50, "http", false, 2, null) && !StringsKt.startsWith$default(var50, "geo:", false, 2, null)) {
            var3 = var4;
         } else {
            var3 = false;
         }

         if (var3) {
            var6.add(var8);
         }
      }
   }

   public static final class Place {
      private final String address;
      private final double lat;
      private final double lon;
      private final String name;
      private final String query;

      public Place(String var1, String var2, double var3, double var5, String var7) {
         Intrinsics.checkNotNullParameter(var1, "name");
         Intrinsics.checkNotNullParameter(var2, "address");
         Intrinsics.checkNotNullParameter(var7, "query");
         super();
         this.name = var1;
         this.address = var2;
         this.lat = var3;
         this.lon = var5;
         this.query = var7;
      }

      public final String getAddress() {
         return this.address;
      }

      public final boolean getHasCoords() {
         double var1 = this.lat;
         boolean var5 = Double.isInfinite(var1);
         boolean var4 = true;
         boolean var3;
         if (!var5 && !Double.isNaN(var1)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var1 = this.lon;
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               var1 = this.lat;
               if (-90.0 <= var1 && var1 <= 90.0) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var3) {
                  var1 = this.lon;
                  if (-180.0 <= var1 && var1 <= 180.0) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  if (var3) {
                     if (this.lat == 0.0) {
                        var3 = true;
                     } else {
                        var3 = false;
                     }

                     if (!var3) {
                        return var4;
                     }

                     if (this.lon == 0.0) {
                        var3 = true;
                     } else {
                        var3 = false;
                     }

                     if (!var3) {
                        return var4;
                     }
                  }
               }
            }
         }

         return false;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLon() {
         return this.lon;
      }

      public final String getName() {
         return this.name;
      }

      public final String getQuery() {
         return this.query;
      }
   }
}
