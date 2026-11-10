package com.starpilot.starview;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.Base64.Encoder;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

public final class NtripCore {
   public static final NtripCore INSTANCE = new NtripCore();

   // $VF: synthetic method
   public static String $r8$lambda$5jFvYD3sZlGXf_fa63_WmGDjPbM/* $VF was: $r8$lambda$5jFvYD3sZlGXf_fa63_WmGDjPbM*/(String var0) {
      return sourcetable$lambda$12(var0);
   }

   // $VF: synthetic method
   public static NtripCore.Mount $r8$lambda$G7zrPgP1Q1NAkzWrGSzd5rTz2aA(String var0) {
      return sourcetable$lambda$14(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$uYGKLgHF0bv4Cwt7xQkyu0WljjY(String var0) {
      return sourcetable$lambda$13(var0);
   }

   private NtripCore() {
   }

   private static final String gga$dm(double var0, int var2) {
      var0 = Math.abs(var0);
      int var7 = (int)Math.floor(var0);
      double var5 = var7;
      double var3 = 60;
      String var8 = "%0" + var2 + "d%010.7f";
      var8 = String.format(Locale.US, var8, Arrays.copyOf(new Object[]{var7, (var0 - var5) * var3}, 2));
      Intrinsics.checkNotNullExpressionValue(var8, "format(...)");
      return var8;
   }

   // $VF: synthetic method
   public static NtripCore.Mount nearestBase$default(NtripCore var0, List var1, double var2, double var4, double var6, Set var8, int var9, Object var10) {
      if ((var9 & 8) != 0) {
         var6 = 25.0;
      }

      if ((var9 & 16) != 0) {
         var8 = SetsKt.emptySet();
      }

      return var0.nearestBase(var1, var2, var4, var6, var8);
   }

   // $VF: synthetic method
   public static String request$default(
      NtripCore var0, String var1, String var2, String var3, String var4, int var5, boolean var6, String var7, int var8, Object var9
   ) {
      if ((var8 & 8) != 0) {
         var4 = "";
      }

      if ((var8 & 16) != 0) {
         var5 = 0;
      }

      if ((var8 & 32) != 0) {
         var6 = false;
      }

      if ((var8 & 64) != 0) {
         var7 = null;
      }

      return var0.request(var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static List sourcetable$default(NtripCore var0, String var1, double var2, double var4, int var6, Object var7) {
      if ((var6 & 2) != 0) {
         var2 = Double.NaN;
      }

      if ((var6 & 4) != 0) {
         var4 = Double.NaN;
      }

      return var0.sourcetable(var1, var2, var4);
   }

   private static final String sourcetable$lambda$12(String var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return StringsKt.trim(var0).toString();
   }

   private static final boolean sourcetable$lambda$13(String var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return StringsKt.startsWith$default(var0, "STR;", false, 2, null);
   }

   private static final NtripCore.Mount sourcetable$lambda$14(String var0) {
      Intrinsics.checkNotNullParameter(var0, "line");
      CharSequence var12 = var0;
      boolean var5 = false;
      List var11 = StringsKt.split$default(var12, new String[]{";"}, false, 0, 6, null);
      if (var11.size() >= 12) {
         if (((CharSequence)var11.get(1)).length() == 0) {
            var5 = true;
         }

         if (!var5) {
            String var8 = (String)var11.get(1);
            var0 = (String)var11.get(2);
            String var7 = (String)var11.get(3);
            String var9 = (String)var11.get(4);
            boolean var6 = Intrinsics.areEqual(var11.get(11), "1");
            Double var10 = StringsKt.toDoubleOrNull((String)var11.get(9));
            double var3 = Double.NaN;
            double var1;
            if (var10 != null) {
               var1 = var10;
            } else {
               var1 = Double.NaN;
            }

            var10 = StringsKt.toDoubleOrNull((String)var11.get(10));
            if (var10 != null) {
               var3 = var10;
            }

            return new NtripCore.Mount(var8, var0, var7, var9, var6, var1, var3);
         }
      }

      return null;
   }

   public final NtripCore.Answer answer(String var1) {
      Intrinsics.checkNotNullParameter(var1, "line");
      var1 = StringsKt.trim(var1).toString();
      boolean var2 = false;
      NtripCore.Answer var4;
      if (StringsKt.startsWith$default(var1, "ICY 200", false, 2, null) || new Regex("^HTTP/1\\.[01] 200").containsMatchIn(var1)) {
         var4 = NtripCore.Answer.Stream.INSTANCE;
      } else if (StringsKt.startsWith$default(var1, "SOURCETABLE 200", false, 2, null)) {
         var4 = NtripCore.Answer.Sourcetable.INSTANCE;
      } else if (StringsKt.contains$default(var1, " 401", false, 2, null)) {
         var4 = new NtripCore.Answer.Refused("login refused: wrong username / password, or the subscription isn't active yet");
      } else if (StringsKt.contains$default(var1, " 403", false, 2, null)) {
         var4 = new NtripCore.Answer.Refused("account not allowed on this mountpoint (or not activated yet)");
      } else if (StringsKt.contains$default(var1, " 404", false, 2, null)) {
         var4 = new NtripCore.Answer.Refused("mountpoint not found");
      } else if (StringsKt.contains$default(var1, " 503", false, 2, null)) {
         var4 = new NtripCore.Answer.Refused("caster busy (503)");
      } else {
         if (var1.length() == 0) {
            var2 = true;
         }

         if (var2) {
            var4 = new NtripCore.Answer.Refused("the caster closed the connection");
         } else {
            var4 = new NtripCore.Answer.Refused(StringsKt.take(var1, 80));
         }
      }

      return var4;
   }

   public final String gga(RtkStatus var1, long var2) {
      Intrinsics.checkNotNullParameter(var1, "st");
      if (!var1.getHasFix()) {
         return null;
      } else {
         Calendar var10 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
         var10.setTimeInMillis(var2);
         String var13 = String.format(
            Locale.US, "%02d%02d%05.2f", Arrays.copyOf(new Object[]{var10.get(11), var10.get(12), var10.get(13) + var10.get(14) / 1000.0}, 3)
         );
         Intrinsics.checkNotNullExpressionValue(var13, "format(...)");
         byte var6;
         if (Intrinsics.areEqual(var1.getRtk(), "fixed")) {
            var6 = 4;
         } else if (Intrinsics.areEqual(var1.getRtk(), "float")) {
            var6 = 5;
         } else if (Intrinsics.areEqual(var1.getFixType(), "dead reckoning")) {
            var6 = 6;
         } else {
            var6 = 1;
         }

         double var4 = var1.getAltM();
         boolean var9 = Double.isInfinite(var4);
         byte var8 = 0;
         boolean var7;
         if (!var9 && !Double.isNaN(var4)) {
            var7 = '\u0001';
         } else {
            var7 = '\u0000';
         }

         String var11;
         if (var7) {
            var11 = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{var1.getAltM()}, 1));
            Intrinsics.checkNotNullExpressionValue(var11, "format(...)");
         } else {
            var11 = "0.0";
         }

         String var14 = gga$dm(var1.getLat(), 2);
         String var22;
         if (var1.getLat() >= 0.0) {
            var22 = "N";
         } else {
            var22 = "S";
         }

         String var15 = gga$dm(var1.getLon(), 3);
         String var12;
         if (var1.getLon() >= 0.0) {
            var12 = "E";
         } else {
            var12 = "W";
         }

         var11 = var6 + ",%02d,1.0," + var11 + ",M,0.0,M,,";
         String var16 = String.format(Locale.US, var11, Arrays.copyOf(new Object[]{RangesKt.coerceIn(var1.getUsed(), 0, 99)}, 1));
         Intrinsics.checkNotNullExpressionValue(var16, "format(...)");
         String var17 = "GPGGA," + var13 + "," + var14 + "," + var22 + "," + var15 + "," + var12 + "," + var16;
         String var23 = "$" + var17 + "*%02X";
         Locale var25 = Locale.US;
         CharSequence var26 = var17;
         Integer var18 = 0;

         for (int var20 = var8; var20 < var26.length(); var20++) {
            var7 = var26.charAt(var20);
            var18 = var18.intValue() ^ var7;
         }

         String var19 = String.format(var25, var23, Arrays.copyOf(new Object[]{var18}, 1));
         Intrinsics.checkNotNullExpressionValue(var19, "format(...)");
         return var19;
      }
   }

   public final NtripCore.Mount nearestBase(List<NtripCore.Mount> var1, double var2, double var4, double var6, Set<String> var8) {
      Intrinsics.checkNotNullParameter(var1, "list");
      Intrinsics.checkNotNullParameter(var8, "skip");
      Iterable var12 = var1;
      var var15 = new ArrayList();
      Iterator var13 = var12.iterator();

      while (true) {
         boolean var11 = var13.hasNext();
         boolean var10 = false;
         if (!var11) {
            var1 = var15;
            Iterable var28 = var1;
            var var17 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var28, 10));

            for (NtripCore.Mount var29 : var28) {
               var17.add(TuplesKt.to(var29, var29.milesFrom(var2, var4)));
            }

            var1 = var17;
            Iterable var30 = var1;
            var var19 = new ArrayList();

            for (Object var31 : var30) {
               Pair var37 = (Pair)var31;
               var2 = ((Number)var37.getSecond()).doubleValue();
               boolean var33;
               if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
                  var33 = true;
               } else {
                  var33 = false;
               }

               if (var33 && ((Number)var37.getSecond()).doubleValue() <= var6) {
                  var33 = true;
               } else {
                  var33 = false;
               }

               if (var33) {
                  var19.add(var31);
               }
            }

            var1 = var19;
            var13 = var1.iterator();
            Object var21;
            if (!var13.hasNext()) {
               var21 = null;
            } else {
               var21 = (Pair)var13.next();
               if (var13.hasNext()) {
                  var2 = ((Number)var21.getSecond()).doubleValue();
                  Object var32 = var21;

                  do {
                     Object var38 = var13.next();
                     var6 = ((Number)((Pair)var38).getSecond()).doubleValue();
                     var21 = (Pair)var32;
                     var4 = var2;
                     if (Double.compare(var2, var6) > 0) {
                        var21 = (Pair)var38;
                        var4 = var6;
                     }

                     var32 = var21;
                     var2 = var4;
                  } while (var13.hasNext());
               }
            }

            var21 = var21;
            NtripCore.Mount var23;
            if (var21 != null) {
               var23 = (NtripCore.Mount)var21.getFirst();
            } else {
               var23 = null;
            }

            return var23;
         }

         Object var35 = var13.next();
         NtripCore.Mount var14 = (NtripCore.Mount)var35;
         boolean var9 = var10;
         if (StringsKt.contains(var14.getFormat(), "RTCM", true)) {
            var9 = var10;
            if (StringsKt.contains$default(var14.getFormat(), "3", false, 2, null)) {
               var9 = var10;
               if (!var8.contains(var14.getName())) {
                  var9 = true;
               }
            }
         }

         if (var9) {
            var15.add(var35);
         }
      }
   }

   public final String request(String var1, String var2, String var3, String var4, int var5, boolean var6, String var7) {
      Intrinsics.checkNotNullParameter(var1, "mount");
      Intrinsics.checkNotNullParameter(var2, "user");
      Intrinsics.checkNotNullParameter(var3, "pass");
      Intrinsics.checkNotNullParameter(var4, "host");
      boolean var8;
      if (var2.length() > 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      String var9 = "";
      if (var8) {
         Encoder var10 = Base64.getEncoder();
         byte[] var13 = (var2 + ":" + var3).getBytes(Charsets.UTF_8);
         Intrinsics.checkNotNullExpressionValue(var13, "getBytes(...)");
         var2 = var10.encodeToString(var13);
         var2 = "Authorization: Basic " + var2 + "\r\n";
      } else {
         var2 = "";
      }

      if (var6) {
         var4 = StringsKt.trim(var4).toString();
         if (var4.length() == 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            var3 = "";
         } else {
            if (var5 > 0 && var5 != 80) {
               var3 = ":" + var5;
            } else {
               var3 = "";
            }

            var3 = "Host: " + var4 + var3 + "\r\n";
         }

         var1 = StringsKt.trimStart(StringsKt.trim(var1).toString(), new char[]{'/'});
         if (var7 != null) {
            var9 = "Ntrip-GGA: " + var7 + "\r\n";
         }

         return "GET /"
            + var1
            + " HTTP/1.1\r\n"
            + var3
            + "Ntrip-Version: Ntrip/2.0\r\nUser-Agent: NTRIP StarView/2.70\r\n"
            + var9
            + var2
            + "Connection: close\r\n\r\n";
      } else {
         var4 = StringsKt.trim(var4).toString();
         if (var4.length() == 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (!var8) {
            var3 = var9;
            if (var5 > 0) {
               var3 = var9;
               if (var5 != 80) {
                  var3 = ":" + var5;
               }
            }

            var9 = "Host: " + var4 + var3 + "\r\n";
         }

         var1 = StringsKt.trimStart(StringsKt.trim(var1).toString(), new char[]{'/'});
         return "GET /" + var1 + " HTTP/1.0\r\n" + var9 + "User-Agent: NTRIP StarView/2.59\r\nAccept: */*\r\n" + var2 + "Connection: close\r\n\r\n";
      }
   }

   public final String retime(String var1, long var2) {
      Intrinsics.checkNotNullParameter(var1, "gga");
      List var9 = CollectionsKt.toMutableList(
         StringsKt.split$default(StringsKt.substringBefore$default(StringsKt.removePrefix(var1, "$"), '*', null, 2, null), new String[]{","}, false, 0, 6, null)
      );
      if (var9.size() < 2) {
         return null;
      } else {
         Calendar var6 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
         var6.setTimeInMillis(var2);
         String var13 = String.format(
            Locale.US, "%02d%02d%05.2f", Arrays.copyOf(new Object[]{var6.get(11), var6.get(12), var6.get(13) + var6.get(14) / 1000.0}, 3)
         );
         Intrinsics.checkNotNullExpressionValue(var13, "format(...)");
         var9.set(1, var13);
         var1 = CollectionsKt.joinToString$default(var9, ",", null, null, 0, null, null, 62, null);
         String var7 = "$" + var1 + "*%02X";
         Locale var14 = Locale.US;
         CharSequence var8 = var1;
         int var4 = 0;

         for (var11 = 0; var4 < var8.length(); var4++) {
            char var5 = var8.charAt(var4);
            var11 = var11.intValue() ^ var5;
         }

         var1 = String.format(var14, var7, Arrays.copyOf(new Object[]{var11}, 1));
         Intrinsics.checkNotNullExpressionValue(var1, "format(...)");
         return var1;
      }
   }

   public final String said(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "first");
      Intrinsics.checkNotNullParameter(var2, "rest");
      List var4 = StringsKt.split$default(StringsKt.replace$default(var2, "\r", "", false, 4, null), new char[]{'\n'}, false, 0, 6, null);
      int var3 = 0;

      while (var3 < var4.size() && new Regex("^[A-Za-z0-9-]+:\\s").containsMatchIn((CharSequence)var4.get(var3))) {
         var3++;
      }

      Iterator var5 = CollectionsKt.take(var4, var3).iterator();

      do {
         if (!var5.hasNext()) {
            var8 = null;
            break;
         }

         var8 = var5.next();
      } while (!StringsKt.startsWith((String)var8, "Server:", true));

      label46: {
         var2 = (String)var8;
         if (var2 != null) {
            var2 = StringsKt.substringAfter$default(var2, ':', null, 2, null);
            if (var2 != null) {
               var2 = StringsKt.trim(var2).toString();
               break label46;
            }
         }

         var2 = null;
      }

      CharSequence var14 = CollectionsKt.joinToString$default(CollectionsKt.drop(var4, var3), " ", null, null, 0, null, null, 62, null);
      CharSequence var15 = new Regex("(?s)<(script|style)[^>]*>.*?</\\1>").replace(var14, " ");
      CharSequence var16 = new Regex("<[^>]*>").replace(var15, " ");
      String var6 = StringsKt.trim(new Regex("\\s+").replace(var16, " ")).toString();
      StringBuilder var18 = new StringBuilder();
      var18.append('"').append(StringsKt.take(StringsKt.trim(var1).toString(), 80)).append('"');
      CharSequence var17 = var2;
      boolean var12;
      if (var17 != null && var17.length() != 0) {
         var12 = false;
      } else {
         var12 = true;
      }

      if (!var12) {
         var18.append(", server ").append(StringsKt.take(var2, 40));
      }

      if (var6.length() > 0) {
         var12 = true;
      } else {
         var12 = false;
      }

      if (var12 && !StringsKt.equals(var6, StringsKt.substringAfter$default(StringsKt.trim(var1).toString(), ' ', null, 2, null), true)) {
         var18.append(": ").append(StringsKt.take(var6, 160));
      }

      var1 = var18.toString();
      Intrinsics.checkNotNullExpressionValue(var1, "toString(...)");
      return var1;
   }

   public final String size(long var1) {
      String var3;
      if (var1 < 1048576L) {
         var1 /= 1024;
         var3 = var1 + " KB";
      } else if (var1 < 1073741824L) {
         StringCompanionObject var5 = StringCompanionObject.INSTANCE;
         var3 = String.format(Locale.US, "%.1f MB", Arrays.copyOf(new Object[]{var1 / 1048576.0}, 1));
         Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      } else {
         StringCompanionObject var6 = StringCompanionObject.INSTANCE;
         var3 = String.format(Locale.US, "%.2f GB", Arrays.copyOf(new Object[]{var1 / 1.0737418E9F}, 1));
         Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      }

      return var3;
   }

   public final List<NtripCore.Mount> sourcetable(String var1, double var2, double var4) {
      Intrinsics.checkNotNullParameter(var1, "text");
      List var7 = SequencesKt.toList(
         SequencesKt.mapNotNull(
            SequencesKt.filter(
               SequencesKt.map(StringsKt.lineSequence(var1), new NtripCore$$ExternalSyntheticLambda0()), new NtripCore$$ExternalSyntheticLambda1()
            ),
            new NtripCore$$ExternalSyntheticLambda2()
         )
      );
      boolean var6;
      if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
         var6 = true;
      } else {
         var6 = false;
      }

      List var8;
      if (var6) {
         var8 = CollectionsKt.sortedWith(var7, new Comparator(new Comparator(var2, var4) {
            final double $lat0$inlined;
            final double $lon0$inlined;

            {
               this.$lat0$inlined = var1;
               this.$lon0$inlined = var3;
            }

            @Override
            public final int compare(T var1, T var2) {
               double var3 = ((NtripCore.Mount)var1).milesFrom(this.$lat0$inlined, this.$lon0$inlined);
               boolean var7 = Double.isNaN(var3);
               double var5 = Double.MAX_VALUE;
               if (var7) {
                  var3 = Double.MAX_VALUE;
               }

               var1 = var3;
               var3 = ((NtripCore.Mount)var2).milesFrom(this.$lat0$inlined, this.$lon0$inlined);
               if (Double.isNaN(var3)) {
                  var3 = var5;
               }

               return ComparisonsKt.compareValues(var1, var3);
            }
         }) {
            final Comparator $this_thenBy;

            {
               this.$this_thenBy = var1;
            }

            @Override
            public final int compare(T var1, T var2x) {
               int var3 = this.$this_thenBy.compare(var1, var2x);
               if (var3 == 0) {
                  var3 = ComparisonsKt.compareValues(((NtripCore.Mount)var1).getName(), ((NtripCore.Mount)var2x).getName());
               }

               return var3;
            }
         });
      } else {
         var8 = CollectionsKt.sortedWith(var7, new Comparator() {
            @Override
            public final int compare(T var1, T var2) {
               return ComparisonsKt.compareValues(((NtripCore.Mount)var1).getName(), ((NtripCore.Mount)var2).getName());
            }
         });
      }

      return var8;
   }

   public abstract static class Answer {
      private Answer() {
      }

      // $VF: synthetic method
      public Answer(DefaultConstructorMarker var1) {
         this();
      }

      public static final class Refused extends NtripCore.Answer {
         private final String why;

         public Refused(String var1) {
            Intrinsics.checkNotNullParameter(var1, "why");
            super(null);
            this.why = var1;
         }

         public final String getWhy() {
            return this.why;
         }
      }

      public static final class Sourcetable extends NtripCore.Answer {
         public static final NtripCore.Answer.Sourcetable INSTANCE = new NtripCore.Answer.Sourcetable();

         private Sourcetable() {
            super(null);
         }
      }

      public static final class Stream extends NtripCore.Answer {
         public static final NtripCore.Answer.Stream INSTANCE = new NtripCore.Answer.Stream();

         private Stream() {
            super(null);
         }
      }
   }

   public static final class BackupPlan {
      private long backupSinceMs;
      private long mainBadSinceMs;
      private boolean onBackup;
      private final long retryMainMs;
      private final long switchMs;

      public BackupPlan() {
         this(0L, 0L, 3, null);
      }

      public BackupPlan(long var1, long var3) {
         this.switchMs = var1;
         this.retryMainMs = var3;
      }

      // $VF: synthetic method
      public BackupPlan(long var1, long var3, int var5, DefaultConstructorMarker var6) {
         if ((var5 & 1) != 0) {
            var1 = 180000L;
         }

         if ((var5 & 2) != 0) {
            var3 = 600000L;
         }

         this(var1, var3);
      }

      public final long getBackupSinceMs() {
         return this.backupSinceMs;
      }

      public final boolean getOnBackup() {
         return this.onBackup;
      }

      public final long getRetryMainMs() {
         return this.retryMainMs;
      }

      public final long getSwitchMs() {
         return this.switchMs;
      }

      public final void mainResult(boolean var1, long var2) {
         long var4 = 0L;
         if (var1) {
            var2 = var4;
         } else if (this.mainBadSinceMs != 0L) {
            var2 = this.mainBadSinceMs;
         }

         this.mainBadSinceMs = var2;
      }

      public final boolean shouldSwitch(long var1) {
         boolean var3;
         if (!this.onBackup && this.mainBadSinceMs != 0L && var1 - this.mainBadSinceMs >= this.switchMs) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public final boolean timeToRetryMain(long var1) {
         boolean var3;
         if (this.onBackup && var1 - this.backupSinceMs >= this.retryMainMs) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public final void toBackup(long var1) {
         this.onBackup = true;
         this.backupSinceMs = var1;
      }

      public final void toMain(long var1) {
         this.onBackup = false;
         this.mainBadSinceMs = var1 - this.switchMs;
      }
   }

   public static final class Dechunker {
      private boolean ended;
      private int left;
      private final StringBuilder size = new StringBuilder();
      private int skip;

      public final void feed(byte[] var1, int var2, OutputStream var3) {
         Intrinsics.checkNotNullParameter(var1, "b");
         Intrinsics.checkNotNullParameter(var3, "out");
         int var5 = 0;

         while (var5 < var2 && !this.ended) {
            if (this.skip > 0) {
               this.skip--;
               var5++;
            } else if (this.left > 0) {
               int var6 = Math.min(this.left, var2 - var5);
               var3.write(var1, var5, var6);
               this.left -= var6;
               var6 = var5 + var6;
               var5 = var6;
               if (this.left == 0) {
                  this.skip = 2;
                  var5 = var6;
               }
            } else {
               char var4 = (char)var1[var5];
               int var7 = var5 + 1;
               if (var4 == '\n') {
                  String var8 = this.size.toString();
                  Intrinsics.checkNotNullExpressionValue(var8, "toString(...)");
                  String var9 = StringsKt.trim(StringsKt.substringBefore$default(var8, ';', null, 2, null)).toString();
                  StringBuilder var12 = this.size;
                  boolean var11 = false;
                  var12.setLength(0);
                  if (var9.length() == 0) {
                     var11 = true;
                  }

                  var5 = var7;
                  if (!var11) {
                     Integer var13 = StringsKt.toIntOrNull(var9, 16);
                     if (var13 == null) {
                        throw new IllegalStateException("caster sent a broken chunk");
                     }

                     this.left = var13;
                     var5 = var7;
                     if (this.left == 0) {
                        this.ended = true;
                        var5 = var7;
                     }
                  }
               } else {
                  var5 = var7;
                  if (var4 != '\r') {
                     if (this.size.length() > 16) {
                        throw new IllegalStateException("caster sent a broken chunk");
                     }

                     this.size.append(var4);
                     var5 = var7;
                  }
               }
            }
         }
      }

      public final boolean getEnded() {
         return this.ended;
      }
   }

   public static final class Mount {
      private final String details;
      private final String format;
      private final double lat;
      private final double lon;
      private final String name;
      private final boolean needsGga;
      private final String place;

      public Mount(String var1, String var2, String var3, String var4, boolean var5, double var6, double var8) {
         Intrinsics.checkNotNullParameter(var1, "name");
         Intrinsics.checkNotNullParameter(var2, "place");
         Intrinsics.checkNotNullParameter(var3, "format");
         Intrinsics.checkNotNullParameter(var4, "details");
         super();
         this.name = var1;
         this.place = var2;
         this.format = var3;
         this.details = var4;
         this.needsGga = var5;
         this.lat = var6;
         this.lon = var8;
      }

      public final String getDetails() {
         return this.details;
      }

      public final String getFormat() {
         return this.format;
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

      public final boolean getNeedsGga() {
         return this.needsGga;
      }

      public final String getPlace() {
         return this.place;
      }

      public final double milesFrom(double var1, double var3) {
         double var5 = this.lat;
         boolean var9 = Double.isInfinite(var5);
         boolean var8 = true;
         boolean var7;
         if (!var9 && !Double.isNaN(var5)) {
            var7 = true;
         } else {
            var7 = false;
         }

         label68: {
            if (var7) {
               var5 = this.lon;
               if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                  var7 = true;
               } else {
                  var7 = false;
               }

               if (var7) {
                  if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (var7) {
                     if (this.lat == 0.0) {
                        var7 = true;
                     } else {
                        var7 = false;
                     }

                     if (!var7) {
                        break label68;
                     }

                     if (this.lon == 0.0) {
                        var7 = var8;
                     } else {
                        var7 = false;
                     }

                     if (!var7) {
                        break label68;
                     }
                  }
               }
            }

            return Double.NaN;
         }

         var5 = (this.lat - var1) * 69.05;
         var1 = (this.lon - var3) * 69.17 * Math.cos(Math.toRadians(var1));
         return Math.sqrt(var1 * var1 + var5 * var5);
      }
   }
}
