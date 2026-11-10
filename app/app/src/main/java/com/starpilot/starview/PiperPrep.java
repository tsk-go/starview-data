package com.starpilot.starview;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.json.JSONObject;

public final class PiperPrep {
   public static final PiperPrep INSTANCE = new PiperPrep();

   private PiperPrep() {
   }

   private final byte[] ld(int var1, byte[] var2) {
      return ArraysKt.plus(ArraysKt.plus(this.varint(var1 << 3 | 2), this.varint(var2.length)), var2);
   }

   private final byte[] varint(int var1) {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();

      while (true) {
         int var2 = var1 & 127;
         var1 >>>= 7;
         if (var1 == 0) {
            var3.write(var2);
            byte[] var4 = var3.toByteArray();
            Intrinsics.checkNotNullExpressionValue(var4, "toByteArray(...)");
            return var4;
         }

         var3.write(var2 | 128);
      }
   }

   public final List<Pair<String, String>> meta(JSONObject var1) {
      Pair var5;
      Pair var6;
      String var11;
      label23: {
         Intrinsics.checkNotNullParameter(var1, "cfg");
         var5 = TuplesKt.to("model_type", "vits");
         var6 = TuplesKt.to("comment", "piper");
         JSONObject var3 = var1.optJSONObject("language");
         if (var3 != null) {
            String var4 = var3.optString("name_english");
            var11 = var4;
            if (var4 != null) {
               break label23;
            }
         }

         var11 = "English";
      }

      Pair var7;
      label18: {
         var7 = TuplesKt.to("language", var11);
         JSONObject var12 = var1.optJSONObject("espeak");
         if (var12 != null) {
            String var15 = var12.optString("voice");
            var11 = var15;
            if (var15 != null) {
               break label18;
            }
         }

         var11 = "en-us";
      }

      Pair var14 = TuplesKt.to("voice", var11);
      Pair var8 = TuplesKt.to("has_espeak", "1");
      Pair var16 = TuplesKt.to("n_speakers", String.valueOf(var1.optInt("num_speakers", 1)));
      var1 = var1.optJSONObject("audio");
      int var2 = 22050;
      if (var1 != null) {
         var2 = var1.optInt("sample_rate", 22050);
      }

      Pair var10 = TuplesKt.to("sample_rate", String.valueOf(var2));
      return CollectionsKt.listOf(new Pair[]{var5, var6, var7, var14, var8, var16, var10});
   }

   public final byte[] metadataBytes(JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "cfg");
      Iterable var2 = this.meta(var1);
      byte[] var7 = new byte[0];

      for (Pair var3 : var2) {
         String var5 = (String)var3.component1();
         String var4 = (String)var3.component2();
         PiperPrep var9 = INSTANCE;
         PiperPrep var6 = INSTANCE;
         byte[] var11 = var5.getBytes(Charsets.UTF_8);
         Intrinsics.checkNotNullExpressionValue(var11, "getBytes(...)");
         byte[] var13 = var6.ld(1, var11);
         PiperPrep var12 = INSTANCE;
         byte[] var10 = var4.getBytes(Charsets.UTF_8);
         Intrinsics.checkNotNullExpressionValue(var10, "getBytes(...)");
         var7 = ArraysKt.plus(var7, var9.ld(14, ArraysKt.plus(var13, var12.ld(2, var10))));
      }

      return var7;
   }

   public final String tokens(JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "cfg");
      var1 = var1.getJSONObject("phoneme_id_map");
      StringBuilder var3 = new StringBuilder();
      Iterator var4 = var1.keys();
      Intrinsics.checkNotNullExpressionValue(var4, "keys(...)");

      while (var4.hasNext()) {
         String var2 = (String)var4.next();
         var3.append(var2).append(' ').append(var1.getJSONArray(var2).getInt(0)).append('\n');
      }

      String var6 = var3.toString();
      Intrinsics.checkNotNullExpressionValue(var6, "toString(...)");
      return var6;
   }
}
