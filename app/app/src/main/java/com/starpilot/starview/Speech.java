package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioAttributes.Builder;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

public final class Speech {
   public static final Speech INSTANCE = new Speech();
   public static final float MAX_SPEED = 1.8F;
   public static final float MIN_SPEED = 0.6F;
   private static Context app;
   private static String pending;
   private static volatile boolean ready;
   private static TextToSpeech tts;

   // $VF: synthetic method
   public static void $r8$lambda$31UkncFEB_6iaeLaurRhrd1_p6c/* $VF was: $r8$lambda$31UkncFEB_6iaeLaurRhrd1_p6c*/(TextToSpeech var0, Voice var1) {
      preview$lambda$9$lambda$8(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$6MG9a0xOTJe0xm6r783cOLZq9_c/* $VF was: $r8$lambda$6MG9a0xOTJe0xm6r783cOLZq9_c*/(Function0 var0, int var1) {
      init$lambda$1(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$H_9j1lCHfm7HckqeNyb2WRauCY8/* $VF was: $r8$lambda$H_9j1lCHfm7HckqeNyb2WRauCY8*/(MatchResult var0) {
      return spoken$lambda$12(var0);
   }

   // $VF: synthetic method
   public static Comparable $r8$lambda$HJ2efSGPUv2wF8kwy7vvqlr2RfE(Voice var0) {
      return voices$lambda$7(var0);
   }

   // $VF: synthetic method
   public static Comparable $r8$lambda$X1invmFa_kPmL1kvM5dVEEzPsI0(Voice var0) {
      return voices$lambda$6(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$rBFY_ls6qqKAJEvKhPKInBTHr9A(MatchResult var0) {
      return spoken$lambda$11(var0);
   }

   // $VF: synthetic method
   public static Comparable $r8$lambda$wYODSDrMvFVgFG7E3EuYx7f48nk(Voice var0) {
      return voices$lambda$5(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$yJFzmbwLVXB7dapUU7EDSrgNDbo(MatchResult var0) {
      return spoken$lambda$10(var0);
   }

   private Speech() {
   }

   private final void applyVoice() {
      TextToSpeech var3 = tts;
      if (var3 != null) {
         String var4 = this.getVoiceName();
         boolean var1;
         if (var4.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (!var1) {
            Set var2 = var3.getVoices();
            if (var2 != null) {
               Iterator var5 = var2.iterator();

               do {
                  if (!var5.hasNext()) {
                     var2 = null;
                     break;
                  }

                  var2 = (Set)var5.next();
               } while (!Intrinsics.areEqual(((Voice)var2).getName(), var4));

               Voice var7 = (Voice)var2;
               if (var7 != null) {
                  var3.setVoice(var7);
               }
            }
         }
      }
   }

   private final SharedPreferences getPrefs() {
      Context var2 = app;
      Context var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("app");
         var1 = null;
      }

      return var1.getSharedPreferences("speech", 0);
   }

   // $VF: synthetic method
   public static void init$default(Speech var0, Context var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.init(var1, var2);
   }

   private static final void init$lambda$1(Function0 var0, int var1) {
      boolean var2;
      if (var1 == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      ready = var2;
      if (ready) {
         TextToSpeech var3 = tts;
         if (var3 != null) {
            var3.setAudioAttributes(new Builder().setUsage(4).setContentType(1).build());
         }

         var3 = tts;
         if (var3 != null) {
            var3.setLanguage(Locale.US);
         }

         INSTANCE.applyVoice();
         var3 = tts;
         if (var3 != null) {
            var3.setSpeechRate(INSTANCE.getRate());
         }

         String var6 = pending;
         if (var6 != null) {
            speak$default(INSTANCE, var6, false, 2, null);
         }

         pending = null;
         if (var0 != null) {
            var0.invoke();
         }
      }
   }

   private static final void preview$lambda$9$lambda$8(TextToSpeech var0, Voice var1) {
      boolean var2;
      if (INSTANCE.getVoiceName().length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         INSTANCE.applyVoice();
      } else {
         var0.setVoice(var1);
      }
   }

   // $VF: synthetic method
   public static void speak$default(Speech var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = true;
      }

      var0.speak(var1, var2);
   }

   private static final CharSequence spoken$lambda$10(MatchResult var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      Object var1 = var0.getGroupValues().get(1);
      return var1 + " miles";
   }

   private static final CharSequence spoken$lambda$11(MatchResult var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      Object var1 = var0.getGroupValues().get(1);
      return var1 + " feet";
   }

   private static final CharSequence spoken$lambda$12(MatchResult var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      Object var1 = var0.getGroupValues().get(1);
      return var1 + " kilometers";
   }

   private static final Comparable voices$lambda$5(Voice var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.isNetworkConnectionRequired();
   }

   private static final Comparable voices$lambda$6(Voice var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return Intrinsics.areEqual(var0.getLocale().getCountry(), "US") ^ true;
   }

   private static final Comparable voices$lambda$7(Voice var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.getName();
   }

   public final float getChime() {
      return this.getPrefs().getFloat("chime", 1.0F);
   }

   public final boolean getEnabled() {
      return this.getPrefs().getBoolean("on", true);
   }

   public final float getPad() {
      return this.getPrefs().getFloat("pad", 1.0F);
   }

   public final float getPitch() {
      return this.getPrefs().getFloat("pitch", 0.0F);
   }

   public final float getRate() {
      return this.getSpeed() * 1.05F;
   }

   public final boolean getReady() {
      return ready;
   }

   public final float getSpeed() {
      return this.getPrefs().getFloat("speed", 1.0F);
   }

   public final boolean getUseAmy() {
      return Intrinsics.areEqual(this.getPrefs().getString("engine", "amy"), "amy");
   }

   public final String getVoiceName() {
      SharedPreferences var2 = this.getPrefs();
      String var1 = "";
      String var3 = var2.getString("voice", "");
      if (var3 != null) {
         var1 = var3;
      }

      return var1;
   }

   public final void init(Context var1, Function0<Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      AmyVoice.INSTANCE.init(var1);
      if (tts != null) {
         if (ready && var2 != null) {
            var2.invoke();
         }
      } else {
         app = var1.getApplicationContext();
         Context var3 = app;
         var1 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("app");
            var1 = null;
         }

         tts = new TextToSpeech(var1, new Speech$$ExternalSyntheticLambda3(var2));
      }
   }

   public final String label(Voice var1) {
      String var6;
      label42: {
         Intrinsics.checkNotNullParameter(var1, "v");
         var6 = var1.getLocale().getCountry();
         if (var6 != null) {
            switch (var6.hashCode()) {
               case 2100:
                  if (var6.equals("AU")) {
                     var6 = "Australian";
                     break label42;
                  }
                  break;
               case 2142:
                  if (var6.equals("CA")) {
                     var6 = "Canadian";
                     break label42;
                  }
                  break;
               case 2267:
                  if (var6.equals("GB")) {
                     var6 = "British";
                     break label42;
                  }
                  break;
               case 2341:
                  if (var6.equals("IN")) {
                     var6 = "Indian";
                     break label42;
                  }
                  break;
               case 2489:
                  if (var6.equals("NG")) {
                     var6 = "Nigerian";
                     break label42;
                  }
                  break;
               case 2718:
                  if (var6.equals("US")) {
                     var6 = "American";
                     break label42;
                  }
            }
         }

         var6 = var1.getLocale().getDisplayCountry();
      }

      String var3 = var1.getName();
      Intrinsics.checkNotNullExpressionValue(var3, "getName(...)");
      String var4 = var1.getName();
      Intrinsics.checkNotNullExpressionValue(var4, "getName(...)");
      var3 = StringsKt.substringBefore$default(StringsKt.substringAfter(var3, "-x-", var4), "-", null, 2, null).toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var3, "toUpperCase(...)");
      String var5;
      if (var1.isNetworkConnectionRequired()) {
         var5 = " · needs internet";
      } else {
         var5 = "";
      }

      return var6 + " · " + var3 + var5;
   }

   public final void preview(Voice var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "v");
      Intrinsics.checkNotNullParameter(var2, "text");
      TextToSpeech var3 = tts;
      if (var3 != null) {
         Voice var4 = var3.getVoice();
         var3.setVoice(var1);
         var3.setSpeechRate(this.getRate());
         var3.speak(var2, 0, null, "preview");
         if (var4 != null) {
            new Handler(Looper.getMainLooper()).postDelayed(new Speech$$ExternalSyntheticLambda7(var3, var4), 6000L);
         }
      }
   }

   public final void setChime(float var1) {
      this.getPrefs().edit().putFloat("chime", RangesKt.coerceIn(var1, 0.0F, 2.0F)).apply();
   }

   public final void setEnabled(boolean var1) {
      this.getPrefs().edit().putBoolean("on", var1).apply();
   }

   public final void setPad(float var1) {
      this.getPrefs().edit().putFloat("pad", RangesKt.coerceIn(var1, 0.0F, 2.0F)).apply();
   }

   public final void setPitch(float var1) {
      this.getPrefs().edit().putFloat("pitch", RangesKt.coerceIn(var1, -6.0F, 6.0F)).apply();
   }

   public final void setSpeed(float var1) {
      this.getPrefs().edit().putFloat("speed", RangesKt.coerceIn(var1, 0.6F, 1.8F)).apply();
      TextToSpeech var2 = tts;
      if (var2 != null) {
         var2.setSpeechRate(this.getRate());
      }
   }

   public final void setUseAmy(boolean var1) {
      Editor var3 = this.getPrefs().edit();
      String var2;
      if (var1) {
         var2 = "amy";
      } else {
         var2 = "tts";
      }

      var3.putString("engine", var2).apply();
   }

   public final void setVoiceName(String var1) {
      Intrinsics.checkNotNullParameter(var1, "v");
      this.getPrefs().edit().putString("voice", var1).apply();
      this.setUseAmy(false);
      this.applyVoice();
   }

   public final void speak(String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "text");
      if (this.getEnabled()) {
         TextToSpeech var5 = tts;
         if (var5 != null && ready) {
            CharSequence var6 = var1;
            long var3 = System.nanoTime();
            var5.speak(var6, var2 ^ 1, null, "sv" + var3);
         } else {
            pending = var1;
         }
      }
   }

   public final String spoken(String var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      CharSequence var2 = var1;
      CharSequence var3 = new Regex("(\\d+(?:\\.\\d+)?) mi\\b").replace(var2, new Speech$$ExternalSyntheticLambda4());
      CharSequence var4 = new Regex("(\\d+) ft\\b").replace(var3, new Speech$$ExternalSyntheticLambda5());
      return StringsKt.replace$default(new Regex("(\\d+(?:\\.\\d+)?) km\\b").replace(var4, new Speech$$ExternalSyntheticLambda6()), " - ", ", ", false, 4, null);
   }

   public final List<Voice> voices() {
      Set var4;
      label20: {
         TextToSpeech var1 = tts;
         if (var1 != null) {
            Set var2 = var1.getVoices();
            var4 = var2;
            if (var2 != null) {
               break label20;
            }
         }

         var4 = SetsKt.emptySet();
      }

      Iterable var7 = var4;
      var var5 = new ArrayList();

      for (var var7_it : var7) {

         var7 = var7_it;
         if (Intrinsics.areEqual(((Voice)var7).getLocale().getLanguage(), "en")) {
            var5.add(var7);
         }
      }

      List var6 = var5;
      return CollectionsKt.sortedWith(
         var6,
         ComparisonsKt.compareBy(
            new Function1[]{new Speech$$ExternalSyntheticLambda0(), new Speech$$ExternalSyntheticLambda1(), new Speech$$ExternalSyntheticLambda2()}
         )
      );
   }
}
