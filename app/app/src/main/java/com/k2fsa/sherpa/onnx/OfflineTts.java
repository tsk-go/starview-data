package com.k2fsa.sherpa.onnx;

import android.content.res.AssetManager;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OfflineTts {
   private OfflineTtsConfig config;
   private long ptr;

   public OfflineTts(AssetManager var1, OfflineTtsConfig var2) {
      Intrinsics.checkNotNullParameter(var2, "config");
      super();
      this.config = var2;
      long var4;
      if (var1 != null) {
         var4 = this.newFromAsset(var1, this.config);
      } else {
         var4 = this.newFromFile(this.config);
      }

      this.ptr = var4;
      boolean var3;
      if (this.ptr != 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3) {
         throw new IllegalArgumentException("Invalid OfflineTtsConfig: failed to create native OfflineTts".toString());
      }
   }

   // $VF: synthetic method
   public OfflineTts(AssetManager var1, OfflineTtsConfig var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 1) != 0) {
         var1 = null;
      }

      this(var1, var2);
   }

   // $VF: synthetic method
   public static void allocate$default(OfflineTts var0, AssetManager var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = null;
      }

      var0.allocate(var1);
   }

   private final native void delete(long var1);

   // $VF: synthetic method
   public static GeneratedAudio generate$default(OfflineTts var0, String var1, int var2, float var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = 1.0F;
      }

      return var0.generate(var1, var2, var3);
   }

   private final native GeneratedAudio generateImpl(long var1, String var3, int var4, float var5);

   // $VF: synthetic method
   static GeneratedAudio generateImpl$default(OfflineTts var0, long var1, String var3, int var4, float var5, int var6, Object var7) {
      if ((var6 & 4) != 0) {
         var4 = 0;
      }

      if ((var6 & 8) != 0) {
         var5 = 1.0F;
      }

      return var0.generateImpl(var1, var3, var4, var5);
   }

   // $VF: synthetic method
   public static GeneratedAudio generateWithCallback$default(OfflineTts var0, String var1, int var2, float var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = 1.0F;
      }

      return var0.generateWithCallback(var1, var2, var3, var4);
   }

   private final native GeneratedAudio generateWithCallbackImpl(long var1, String var3, int var4, float var5, Function1<float[], Integer> var6);

   // $VF: synthetic method
   static GeneratedAudio generateWithCallbackImpl$default(OfflineTts var0, long var1, String var3, int var4, float var5, Function1 var6, int var7, Object var8) {
      if ((var7 & 4) != 0) {
         var4 = 0;
      }

      if ((var7 & 8) != 0) {
         var5 = 1.0F;
      }

      return var0.generateWithCallbackImpl(var1, var3, var4, var5, var6);
   }

   private final native GeneratedAudio generateWithConfigImpl(long var1, String var3, GenerationConfig var4, Function1<float[], Integer> var5);

   private final native int getNumSpeakers(long var1);

   private final native int getSampleRate(long var1);

   private final native long newFromAsset(AssetManager var1, OfflineTtsConfig var2);

   private final native long newFromFile(OfflineTtsConfig var1);

   public final void allocate(AssetManager var1) {
      if (this.ptr == 0L) {
         long var3;
         if (var1 != null) {
            var3 = this.newFromAsset(var1, this.config);
         } else {
            var3 = this.newFromFile(this.config);
         }

         this.ptr = var3;
         boolean var2;
         if (this.ptr != 0L) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            throw new IllegalArgumentException("Invalid OfflineTtsConfig: failed to create native OfflineTts".toString());
         }
      }
   }

   @Override
   protected final void finalize() {
      if (this.ptr != 0L) {
         this.delete(this.ptr);
         this.ptr = 0L;
      }
   }

   public final void free() {
      if (this.ptr != 0L) {
         this.delete(this.ptr);
         this.ptr = 0L;
      }
   }

   public final GeneratedAudio generate(String var1, int var2, float var3) {
      Intrinsics.checkNotNullParameter(var1, "text");
      return this.generateImpl(this.ptr, var1, var2, var3);
   }

   public final GeneratedAudio generateWithCallback(String var1, int var2, float var3, Function1<float[], Integer> var4) {
      Intrinsics.checkNotNullParameter(var1, "text");
      Intrinsics.checkNotNullParameter(var4, "callback");
      long var5 = this.ptr;
      return this.generateWithCallbackImpl(var5, var1, var2, var3, var4);
   }

   public final GeneratedAudio generateWithConfig(String var1, GenerationConfig var2) {
      Intrinsics.checkNotNullParameter(var1, "text");
      Intrinsics.checkNotNullParameter(var2, "config");
      return this.generateWithConfigImpl(this.ptr, var1, var2, null);
   }

   public final GeneratedAudio generateWithConfigAndCallback(String var1, GenerationConfig var2, Function1<float[], Integer> var3) {
      Intrinsics.checkNotNullParameter(var1, "text");
      Intrinsics.checkNotNullParameter(var2, "config");
      Intrinsics.checkNotNullParameter(var3, "callback");
      return this.generateWithConfigImpl(this.ptr, var1, var2, var3);
   }

   public final OfflineTtsConfig getConfig() {
      return this.config;
   }

   public final int numSpeakers() {
      return this.getNumSpeakers(this.ptr);
   }

   public final void release() {
      this.finalize();
   }

   public final int sampleRate() {
      return this.getSampleRate(this.ptr);
   }

   public final void setConfig(OfflineTtsConfig var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.config = var1;
   }
}
