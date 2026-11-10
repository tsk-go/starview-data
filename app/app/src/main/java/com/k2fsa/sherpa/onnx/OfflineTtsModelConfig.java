package com.k2fsa.sherpa.onnx;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OfflineTtsModelConfig {
   private boolean debug;
   private OfflineTtsKittenModelConfig kitten;
   private OfflineTtsKokoroModelConfig kokoro;
   private OfflineTtsMatchaModelConfig matcha;
   private int numThreads;
   private OfflineTtsPocketModelConfig pocket;
   private String provider;
   private OfflineTtsSupertonicModelConfig supertonic;
   private OfflineTtsVitsModelConfig vits;
   private OfflineTtsZipVoiceModelConfig zipvoice;

   public OfflineTtsModelConfig() {
      this(null, null, null, null, null, null, null, 0, false, null, 1023, null);
   }

   public OfflineTtsModelConfig(
      OfflineTtsVitsModelConfig var1,
      OfflineTtsMatchaModelConfig var2,
      OfflineTtsKokoroModelConfig var3,
      OfflineTtsZipVoiceModelConfig var4,
      OfflineTtsKittenModelConfig var5,
      OfflineTtsPocketModelConfig var6,
      OfflineTtsSupertonicModelConfig var7,
      int var8,
      boolean var9,
      String var10
   ) {
      Intrinsics.checkNotNullParameter(var1, "vits");
      Intrinsics.checkNotNullParameter(var2, "matcha");
      Intrinsics.checkNotNullParameter(var3, "kokoro");
      Intrinsics.checkNotNullParameter(var4, "zipvoice");
      Intrinsics.checkNotNullParameter(var5, "kitten");
      Intrinsics.checkNotNullParameter(var6, "pocket");
      Intrinsics.checkNotNullParameter(var7, "supertonic");
      Intrinsics.checkNotNullParameter(var10, "provider");
      super();
      this.vits = var1;
      this.matcha = var2;
      this.kokoro = var3;
      this.zipvoice = var4;
      this.kitten = var5;
      this.pocket = var6;
      this.supertonic = var7;
      this.numThreads = var8;
      this.debug = var9;
      this.provider = var10;
   }

   // $VF: synthetic method
   public OfflineTtsModelConfig(
      OfflineTtsVitsModelConfig var1,
      OfflineTtsMatchaModelConfig var2,
      OfflineTtsKokoroModelConfig var3,
      OfflineTtsZipVoiceModelConfig var4,
      OfflineTtsKittenModelConfig var5,
      OfflineTtsPocketModelConfig var6,
      OfflineTtsSupertonicModelConfig var7,
      int var8,
      boolean var9,
      String var10,
      int var11,
      DefaultConstructorMarker var12
   ) {
      if ((var11 & 1) != 0) {
         var1 = new OfflineTtsVitsModelConfig(null, null, null, null, null, 0.0F, 0.0F, 0.0F, 255, null);
      }

      if ((var11 & 2) != 0) {
         var2 = new OfflineTtsMatchaModelConfig(null, null, null, null, null, null, 0.0F, 0.0F, 255, null);
      }

      if ((var11 & 4) != 0) {
         var3 = new OfflineTtsKokoroModelConfig(null, null, null, null, null, null, null, 0.0F, 255, null);
      }

      if ((var11 & 8) != 0) {
         var4 = new OfflineTtsZipVoiceModelConfig(null, null, null, null, null, null, 0.0F, 0.0F, 0.0F, 0.0F, 1023, null);
      }

      if ((var11 & 16) != 0) {
         var5 = new OfflineTtsKittenModelConfig(null, null, null, null, 0.0F, 31, null);
      }

      if ((var11 & 32) != 0) {
         var6 = new OfflineTtsPocketModelConfig(null, null, null, null, null, null, null, 0, 255, null);
      }

      if ((var11 & 64) != 0) {
         var7 = new OfflineTtsSupertonicModelConfig(null, null, null, null, null, null, null, 127, null);
      }

      if ((var11 & 128) != 0) {
         var8 = 1;
      }

      if ((var11 & 256) != 0) {
         var9 = false;
      }

      if ((var11 & 512) != 0) {
         var10 = "cpu";
      }

      this(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   // $VF: synthetic method
   public static OfflineTtsModelConfig copy$default(
      OfflineTtsModelConfig var0,
      OfflineTtsVitsModelConfig var1,
      OfflineTtsMatchaModelConfig var2,
      OfflineTtsKokoroModelConfig var3,
      OfflineTtsZipVoiceModelConfig var4,
      OfflineTtsKittenModelConfig var5,
      OfflineTtsPocketModelConfig var6,
      OfflineTtsSupertonicModelConfig var7,
      int var8,
      boolean var9,
      String var10,
      int var11,
      Object var12
   ) {
      if ((var11 & 1) != 0) {
         var1 = var0.vits;
      }

      if ((var11 & 2) != 0) {
         var2 = var0.matcha;
      }

      if ((var11 & 4) != 0) {
         var3 = var0.kokoro;
      }

      if ((var11 & 8) != 0) {
         var4 = var0.zipvoice;
      }

      if ((var11 & 16) != 0) {
         var5 = var0.kitten;
      }

      if ((var11 & 32) != 0) {
         var6 = var0.pocket;
      }

      if ((var11 & 64) != 0) {
         var7 = var0.supertonic;
      }

      if ((var11 & 128) != 0) {
         var8 = var0.numThreads;
      }

      if ((var11 & 256) != 0) {
         var9 = var0.debug;
      }

      if ((var11 & 512) != 0) {
         var10 = var0.provider;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   public final OfflineTtsVitsModelConfig component1() {
      return this.vits;
   }

   public final String component10() {
      return this.provider;
   }

   public final OfflineTtsMatchaModelConfig component2() {
      return this.matcha;
   }

   public final OfflineTtsKokoroModelConfig component3() {
      return this.kokoro;
   }

   public final OfflineTtsZipVoiceModelConfig component4() {
      return this.zipvoice;
   }

   public final OfflineTtsKittenModelConfig component5() {
      return this.kitten;
   }

   public final OfflineTtsPocketModelConfig component6() {
      return this.pocket;
   }

   public final OfflineTtsSupertonicModelConfig component7() {
      return this.supertonic;
   }

   public final int component8() {
      return this.numThreads;
   }

   public final boolean component9() {
      return this.debug;
   }

   public final OfflineTtsModelConfig copy(
      OfflineTtsVitsModelConfig var1,
      OfflineTtsMatchaModelConfig var2,
      OfflineTtsKokoroModelConfig var3,
      OfflineTtsZipVoiceModelConfig var4,
      OfflineTtsKittenModelConfig var5,
      OfflineTtsPocketModelConfig var6,
      OfflineTtsSupertonicModelConfig var7,
      int var8,
      boolean var9,
      String var10
   ) {
      Intrinsics.checkNotNullParameter(var1, "vits");
      Intrinsics.checkNotNullParameter(var2, "matcha");
      Intrinsics.checkNotNullParameter(var3, "kokoro");
      Intrinsics.checkNotNullParameter(var4, "zipvoice");
      Intrinsics.checkNotNullParameter(var5, "kitten");
      Intrinsics.checkNotNullParameter(var6, "pocket");
      Intrinsics.checkNotNullParameter(var7, "supertonic");
      Intrinsics.checkNotNullParameter(var10, "provider");
      return new OfflineTtsModelConfig(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof OfflineTtsModelConfig)) {
         return false;
      } else {
         var1 = var1;
         if (!Intrinsics.areEqual(this.vits, var1.vits)) {
            return false;
         } else if (!Intrinsics.areEqual(this.matcha, var1.matcha)) {
            return false;
         } else if (!Intrinsics.areEqual(this.kokoro, var1.kokoro)) {
            return false;
         } else if (!Intrinsics.areEqual(this.zipvoice, var1.zipvoice)) {
            return false;
         } else if (!Intrinsics.areEqual(this.kitten, var1.kitten)) {
            return false;
         } else if (!Intrinsics.areEqual(this.pocket, var1.pocket)) {
            return false;
         } else if (!Intrinsics.areEqual(this.supertonic, var1.supertonic)) {
            return false;
         } else if (this.numThreads != var1.numThreads) {
            return false;
         } else {
            return this.debug != var1.debug ? false : Intrinsics.areEqual(this.provider, var1.provider);
         }
      }
   }

   public final boolean getDebug() {
      return this.debug;
   }

   public final OfflineTtsKittenModelConfig getKitten() {
      return this.kitten;
   }

   public final OfflineTtsKokoroModelConfig getKokoro() {
      return this.kokoro;
   }

   public final OfflineTtsMatchaModelConfig getMatcha() {
      return this.matcha;
   }

   public final int getNumThreads() {
      return this.numThreads;
   }

   public final OfflineTtsPocketModelConfig getPocket() {
      return this.pocket;
   }

   public final String getProvider() {
      return this.provider;
   }

   public final OfflineTtsSupertonicModelConfig getSupertonic() {
      return this.supertonic;
   }

   public final OfflineTtsVitsModelConfig getVits() {
      return this.vits;
   }

   public final OfflineTtsZipVoiceModelConfig getZipvoice() {
      return this.zipvoice;
   }

   @Override
   public int hashCode() {
      return (
               (
                        (
                                 (
                                          (
                                                   (
                                                            ((this.vits.hashCode() * 31 + this.matcha.hashCode()) * 31 + this.kokoro.hashCode()) * 31
                                                               + this.zipvoice.hashCode()
                                                         )
                                                         * 31
                                                      + this.kitten.hashCode()
                                                )
                                                * 31
                                             + this.pocket.hashCode()
                                       )
                                       * 31
                                    + this.supertonic.hashCode()
                              )
                              * 31
                           + Integer.hashCode(this.numThreads)
                     )
                     * 31
                  + Boolean.hashCode(this.debug)
            )
            * 31
         + this.provider.hashCode();
   }

   public final void setDebug(boolean var1) {
      this.debug = var1;
   }

   public final void setKitten(OfflineTtsKittenModelConfig var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.kitten = var1;
   }

   public final void setKokoro(OfflineTtsKokoroModelConfig var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.kokoro = var1;
   }

   public final void setMatcha(OfflineTtsMatchaModelConfig var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.matcha = var1;
   }

   public final void setNumThreads(int var1) {
      this.numThreads = var1;
   }

   public final void setPocket(OfflineTtsPocketModelConfig var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.pocket = var1;
   }

   public final void setProvider(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.provider = var1;
   }

   public final void setSupertonic(OfflineTtsSupertonicModelConfig var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.supertonic = var1;
   }

   public final void setVits(OfflineTtsVitsModelConfig var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.vits = var1;
   }

   public final void setZipvoice(OfflineTtsZipVoiceModelConfig var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.zipvoice = var1;
   }

   @Override
   public String toString() {
      OfflineTtsVitsModelConfig var9 = this.vits;
      OfflineTtsMatchaModelConfig var6 = this.matcha;
      OfflineTtsKokoroModelConfig var4 = this.kokoro;
      OfflineTtsZipVoiceModelConfig var8 = this.zipvoice;
      OfflineTtsKittenModelConfig var10 = this.kitten;
      OfflineTtsPocketModelConfig var3 = this.pocket;
      OfflineTtsSupertonicModelConfig var5 = this.supertonic;
      int var1 = this.numThreads;
      boolean var2 = this.debug;
      String var7 = this.provider;
      return "OfflineTtsModelConfig(vits="
         + var9
         + ", matcha="
         + var6
         + ", kokoro="
         + var4
         + ", zipvoice="
         + var8
         + ", kitten="
         + var10
         + ", pocket="
         + var3
         + ", supertonic="
         + var5
         + ", numThreads="
         + var1
         + ", debug="
         + var2
         + ", provider="
         + var7
         + ")";
   }
}
