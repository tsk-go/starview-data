package com.starpilot.starview;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class DriveTrack {
   public static final DriveTrack INSTANCE = new DriveTrack();
   private static final StringBuilder buf = new StringBuilder();
   private static File dir;
   private static File file;
   private static double lastLat = Double.NaN;
   private static double lastLon = Double.NaN;
   private static long lastT;

   // $VF: synthetic method
   public static boolean $r8$lambda$2iaI7mRw3F53F1fuQOcdYE8j4H4(File var0) {
      return between$lambda$1(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$VxuUuK0a_neI3IBiZ2N84m3V6Z0(long var0, long var2, ArrayList var4, String var5) {
      return between$lambda$2(var0, var2, var4, var5);
   }

   private DriveTrack() {
   }

   private static final boolean between$lambda$1(File var0) {
      String var1 = var0.getName();
      Intrinsics.checkNotNullExpressionValue(var1, "getName(...)");
      return StringsKt.endsWith$default(var1, ".csv", false, 2, null);
   }

   private static final Unit between$lambda$2(long var0, long var2, ArrayList var4, String var5) {
      Intrinsics.checkNotNullParameter(var5, "l");
      CharSequence var11 = var5;
      boolean var7 = false;
      List var12 = StringsKt.split$default(var11, new char[]{','}, false, 0, 6, null);
      if (var12.size() < 3) {
         return Unit.INSTANCE;
      } else {
         Long var10 = StringsKt.toLongOrNull((String)var12.get(0));
         if (var10 != null) {
            long var8 = var10;
            boolean var6 = var7;
            if (var0 <= var8) {
               var6 = var7;
               if (var8 <= var2) {
                  var6 = true;
               }
            }

            if (var6) {
               var4.add(new DriveTrack.Pt(var8, Double.parseDouble((String)var12.get(1)), Double.parseDouble((String)var12.get(2))));
            }

            return Unit.INSTANCE;
         } else {
            return Unit.INSTANCE;
         }
      }
   }

   private final void flush() {
      File var2 = file;
      if (var2 != null) {
         boolean var1;
         if (buf.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (!var1) {
            String var3 = buf.toString();
            Intrinsics.checkNotNullExpressionValue(var3, "toString(...)");
            buf.setLength(0);

            try {
               FilesKt.appendText$default(var2, var3, null, 2, null);
            } catch (Exception var4) {
            }
         }
      }
   }

   private final void prune(File var1) {
      long var4 = System.currentTimeMillis();
      File[] var6 = var1.listFiles();
      if (var6 != null) {
         for (File var7 : var6) {
            if (var7.lastModified() < var4 - 7776000000L) {
               var7.delete();
            }
         }
      }
   }

   // $VF: synthetic method
   public static void tick$default(DriveTrack var0, Telemetry var1, long var2, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = System.currentTimeMillis();
      }

      var0.tick(var1, var2);
   }

   public final List<DriveTrack.Pt> between(Context var1, long var2, long var4) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      this.flush();
      File var9 = dir;
      File var8 = var9;
      if (var9 == null) {
         var8 = new File(var1.getFilesDir(), "tracks");
      }

      ArrayList var16 = new ArrayList();
      File[] var14 = var8.listFiles(new DriveTrack$$ExternalSyntheticLambda0());
      File[] var12 = var14;
      if (var14 == null) {
         var12 = new File[0];
      }

      for (File var15 : var12) {
         Intrinsics.checkNotNull(var15);
         Long var10 = StringsKt.toLongOrNull(FilesKt.getNameWithoutExtension(var15));
         if (var10 != null && var10 <= var4 && var15.lastModified() >= var2) {
            try {
               DriveTrack$$ExternalSyntheticLambda1 var17 = new DriveTrack$$ExternalSyntheticLambda1(var2, var4, var16);
               FilesKt.forEachLine$default(var15, null, var17, 1, null);
            } catch (Exception var11) {
            }
         }
      }

      List var13 = var16;
      if (var13.size() > 1) {
         CollectionsKt.sortWith(var13, new Comparator() {
            @Override
            public final int compare(T var1, T var2x) {
               return ComparisonsKt.compareValues(((DriveTrack.Pt)var1).getT(), ((DriveTrack.Pt)var2x).getT());
            }
         });
      }

      return var16;
   }

   public final void init(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      if (dir == null) {
         File var2 = new File(var1.getFilesDir(), "tracks");
         var2.mkdirs();
         INSTANCE.prune(var2);
         dir = var2;
      }
   }

   public final void tick(Telemetry var1, long var2) {
      Intrinsics.checkNotNullParameter(var1, "t");
      File var12 = dir;
      if (var12 != null) {
         if (!var1.getStarted()) {
            if (file != null) {
               this.flush();
               file = null;
            }
         } else {
            if (file == null) {
               file = new File(var12, var2 + ".csv");
               lastT = 0L;
            }

            if (var2 - lastT >= 2000L) {
               double[] var18 = RtkPosition.INSTANCE.now(var2, var1.getVEgo());
               boolean var11 = false;
               double var4;
               if (var18 != null) {
                  var4 = var18[0];
               } else {
                  var4 = var1.getGpsLat();
               }

               double var6;
               if (var18 != null) {
                  var6 = var18[1];
               } else {
                  var6 = var1.getGpsLon();
               }

               boolean var10;
               if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                  var10 = true;
               } else {
                  var10 = false;
               }

               if (var10) {
                  if (!Double.isInfinite(var6) && !Double.isNaN(var6)) {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  if (var10) {
                     if (var4 == 0.0) {
                        var10 = true;
                     } else {
                        var10 = false;
                     }

                     if (var10) {
                        var10 = var11;
                        if (var6 == 0.0) {
                           var10 = true;
                        }

                        if (var10) {
                           return;
                        }
                     }

                     double var8;
                     if (Double.isNaN(lastLat)) {
                        var8 = 1.0E9;
                     } else {
                        var8 = Math.hypot((var4 - lastLat) * 111195.0, (var6 - lastLon) * 111320.0 * Math.cos(Math.toRadians(var4)));
                     }

                     if (var8 < 15.0 && var2 - lastT < 20000L) {
                        return;
                     }

                     lastT = var2;
                     lastLat = var4;
                     lastLon = var6;
                     StringBuilder var19 = buf.append(var2).append(',');
                     String var13 = String.format(Locale.US, "%.6f", Arrays.copyOf(new Object[]{var4}, 1));
                     Intrinsics.checkNotNullExpressionValue(var13, "format(...)");
                     StringBuilder var14 = var19.append(var13).append(',');
                     String var20 = String.format(Locale.US, "%.6f", Arrays.copyOf(new Object[]{var6}, 1));
                     Intrinsics.checkNotNullExpressionValue(var20, "format(...)");
                     var14.append(var20).append('\n');
                     if (buf.length() > 4000) {
                        this.flush();
                     }

                     return;
                  }
               }
            }
         }
      }
   }

   public static final class Pt {
      private final double lat;
      private final double lon;
      private final long t;

      public Pt(long var1, double var3, double var5) {
         this.t = var1;
         this.lat = var3;
         this.lon = var5;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLon() {
         return this.lon;
      }

      public final long getT() {
         return this.t;
      }
   }
}
