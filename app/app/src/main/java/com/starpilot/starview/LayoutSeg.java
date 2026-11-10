package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;
import org.tensorflow.lite.gpu.GpuDelegate.Options;

public final class LayoutSeg {
   public static final float CELL = 0.5F;
   public static final LayoutSeg.Companion Companion = new LayoutSeg.Companion(null);
   public static final int DARK = 55;
   public static final String FILE = "layout_segformer_b0.lite";
   public static final float FP = 200.0F;
   private static final int[] GROUP;
   public static final int GX = 80;
   public static final int GY = 80;
   public static final int HR = 144;
   private static final float[] MEAN;
   public static final int MH = 56;
   public static final int MW = 160;
   public static final int NC = 19;
   public static final int OH = 224;
   public static final int OW = 640;
   private static final float[] STD;
   private static final String TAG = "StarView/Layout";
   private volatile String accel;
   private final int[] blockCls;
   private final int[] blockRow;
   private volatile boolean busy;
   private final byte[] cls;
   private final Context ctx;
   private volatile boolean dark;
   private volatile boolean enabled;
   private float feedH;
   private GpuDelegate gpu;
   private volatile String gpuError;
   private volatile LayoutSeg.Grid grid;
   private final ByteBuffer inBuf;
   private Interpreter interp;
   private long lastFeedNs;
   private final float[] lg;
   private long loadedMtime;
   private final Object lock;
   private int[] lut;
   private String lutKey;
   private volatile double ms;
   private final ByteBuffer outBuf;
   private long pendingNs;
   private final float[] px;
   private volatile boolean running;
   private volatile String status;
   private Thread worker;

   // $VF: synthetic method
   public static void $r8$lambda$gG0_tSGl9CbOLMrnioezsAYqiqI(LayoutSeg var0) {
      start$lambda$4(var0);
   }

   static {
      int[] var2 = new int[19];
      int var1 = 0;

      while (true) {
         short var0 = 3;
         if (var1 >= 19) {
            GROUP = var2;
            MEAN = new float[]{0.485F, 0.456F, 0.406F};
            STD = new float[]{0.229F, 0.224F, 0.225F};
            return;
         }

         switch (var1) {
            case 0:
               var0 = 0;
               break;
            case 1:
               var0 = 1;
            case 2:
            case 3:
            case 4:
               break;
            case 5:
            case 6:
            case 7:
            default:
               var0 = 255;
               break;
            case 8:
            case 9:
               var0 = 2;
         }

         var2[var1] = var0;
         var1++;
      }
   }

   public LayoutSeg(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      this.ctx = var1;
      this.enabled = true;
      this.status = "off";
      this.accel = "-";
      this.gpuError = "";
      this.px = new float[430080];
      this.lut = new int[0];
      this.lutKey = "";
      this.feedH = 1.22F;
      this.lock = new Object();
      ByteBuffer var2 = ByteBuffer.allocateDirect(1720320).order(ByteOrder.nativeOrder());
      Intrinsics.checkNotNullExpressionValue(var2, "order(...)");
      this.inBuf = var2;
      ByteBuffer var3 = ByteBuffer.allocateDirect(680960).order(ByteOrder.nativeOrder());
      Intrinsics.checkNotNullExpressionValue(var3, "order(...)");
      this.outBuf = var3;
      this.cls = new byte[8960];
      this.lg = new float[170240];
      this.blockRow = new int[160];
      this.blockCls = new int[160];
   }

   private final void buildLut(int var1, int var2, float[] var3) {
      Projection.Companion.Cam var15 = Projection.Companion.camFor(var1, var2, true);
      Projection.Companion var16 = Projection.Companion;
      double[] var18 = Projection.Companion.getVIEW_FROM_DEVICE();
      Projection.Companion var17 = Projection.Companion;
      double var6 = var3[0];
      double var4 = var3[1];
      byte var12 = 2;
      double[] var19 = var16.mul(var18, var17.rotFromEuler(var6, var4, var3[2]));
      int[] var29 = new int[143360];

      for (int var10 = 0; var10 < 224; var10++) {
         int var11 = 0;

         while (var11 < 640) {
            var17 = Projection.Companion;
            var6 = var11;
            var4 = 200.0F;
            double[] var31 = var17.mulv(var19, 1.0, (var6 + 0.5 - 320.0) / var4, (var10 + 0.5 - 144) / var4);
            if (var31[2] <= 1.0E-6) {
               var29[var10 * 640 + var11] = -1;
            } else {
               var6 = var31[0] / var31[2];
               double var8 = var31[1] / var31[2];
               var4 = Math.hypot(var6, var8);
               if (var4 < 1.0E-9) {
                  var4 = var15.getF();
               } else {
                  var4 = var15.getF() * Math.atan(var4) / var4;
               }

               int var13 = (int)(var15.getCx() + var6 * var4);
               int var14 = (int)(var15.getCy() + var8 * var4);
               boolean var25;
               if (var13 >= 0 && var13 < var1) {
                  var25 = 1;
               } else {
                  var25 = 0;
               }

               label43: {
                  if (var25) {
                     boolean var26;
                     if (var14 >= 0 && var14 < var2) {
                        var26 = true;
                     } else {
                        var26 = false;
                     }

                     if (var26) {
                        var25 = var14 * var1 + var13;
                        break label43;
                     }
                  }

                  var25 = -1;
               }

               var29[var10 * 640 + var11] = var25;
            }

            var11++;
            var12 = 2;
         }
      }

      this.lut = var29;
   }

   private final void close() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield com/starpilot/starview/LayoutSeg.interp Lorg/tensorflow/lite/Interpreter;
      // 04: astore 1
      // 05: aload 1
      // 06: ifnull 11
      // 09: aload 1
      // 0a: invokevirtual org/tensorflow/lite/Interpreter.close ()V
      // 0d: goto 11
      // 10: astore 1
      // 11: aload 0
      // 12: aconst_null
      // 13: putfield com/starpilot/starview/LayoutSeg.interp Lorg/tensorflow/lite/Interpreter;
      // 16: aload 0
      // 17: getfield com/starpilot/starview/LayoutSeg.gpu Lorg/tensorflow/lite/gpu/GpuDelegate;
      // 1a: astore 1
      // 1b: aload 1
      // 1c: ifnull 27
      // 1f: aload 1
      // 20: invokevirtual org/tensorflow/lite/gpu/GpuDelegate.close ()V
      // 23: goto 27
      // 26: astore 1
      // 27: aload 0
      // 28: aconst_null
      // 29: putfield com/starpilot/starview/LayoutSeg.gpu Lorg/tensorflow/lite/gpu/GpuDelegate;
      // 2c: return
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final Interpreter ensure() {
      Interpreter var9 = this.interp;
      if (var9 != null) {
         if (this.modelFile().lastModified() == this.loadedMtime) {
            return var9;
         }

         this.close();
      }

      this.loadedMtime = this.modelFile().lastModified();
      if (!this.getHasModel()) {
         this.status = "no model imported";
         return null;
      } else {
         this.status = "loading…";
         byte[] var722 = FilesKt.readBytes(this.modelFile());
         ByteBuffer var15 = ByteBuffer.allocateDirect(var722.length).order(ByteOrder.nativeOrder());
         var15.put(var722);
         ((Buffer)var15).rewind();
         File var10 = new File(this.ctx.getCacheDir(), "gpu_layout");
         var10.mkdirs();
         ArrayList var17 = new ArrayList();
         byte var3 = 2;
         byte var1 = 0;

         for (boolean var6 : CollectionsKt.listOf(new Boolean[]{true, false})) {
            Throwable var12 = null;
            GpuDelegate var11 = var12;

            String var13;
            Collection var18;
            label4414: {
               label4423: {
                  try {
                  } catch (Throwable var720) {
                     var12 = var720;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var720);
                     var18 = var17;
                     String var14 = var720.getMessage();
                     var13 = var14;
                     if (var14 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  var11 = var12;

                  try {
                     var727 = new Options();
                  } catch (Throwable var719) {
                     var12 = var719;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var719);
                     var18 = var17;
                     String var736 = var719.getMessage();
                     var13 = var736;
                     if (var736 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  var11 = var12;

                  org.tensorflow.lite.gpu.GpuDelegateFactory.Options var738;
                  try {
                     var738 = var727.setPrecisionLossAllowed(true).setQuantizedModelsAllowed(true).setInferencePreference(1);
                  } catch (Throwable var718) {
                     var12 = var718;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var718);
                     var18 = var17;
                     String var737 = var718.getMessage();
                     var13 = var737;
                     if (var737 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  if (var6) {
                     var11 = var12;

                     try {
                        var763 = var10.getAbsolutePath();
                     } catch (Throwable var717) {
                        var12 = var717;
                        Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var717);
                        var18 = var17;
                        String var739 = var717.getMessage();
                        var13 = var739;
                        if (var739 != null) {
                           break label4414;
                        }
                        break label4423;
                     }

                     var11 = var12;

                     long var7;
                     try {
                        var7 = this.modelFile().length();
                     } catch (Throwable var716) {
                        var12 = var716;
                        Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var716);
                        var18 = var17;
                        String var740 = var716.getMessage();
                        var13 = var740;
                        if (var740 != null) {
                           break label4414;
                        }
                        break label4423;
                     }

                     var11 = var12;

                     try {
                     } catch (Throwable var715) {
                        var12 = var715;
                        Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var715);
                        var18 = var17;
                        String var741 = var715.getMessage();
                        var13 = var741;
                        if (var741 != null) {
                           break label4414;
                        }
                        break label4423;
                     }

                     var11 = var12;

                     try {
                        var728 = new StringBuilder();
                     } catch (Throwable var714) {
                        var12 = var714;
                        Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var714);
                        var18 = var17;
                        String var742 = var714.getMessage();
                        var13 = var742;
                        if (var742 != null) {
                           break label4414;
                        }
                        break label4423;
                     }

                     var11 = var12;

                     try {
                        var738.setSerializationParams(var763, var728.append("layout_b0_v3_").append(var7).toString());
                     } catch (Throwable var713) {
                        var12 = var713;
                        Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var713);
                        var18 = var17;
                        String var743 = var713.getMessage();
                        var13 = var743;
                        if (var743 != null) {
                           break label4414;
                        }
                        break label4423;
                     }
                  }

                  var11 = var12;

                  try {
                  } catch (Throwable var712) {
                     var12 = var712;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var712);
                     var18 = var17;
                     String var744 = var712.getMessage();
                     var13 = var744;
                     if (var744 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  var11 = var12;

                  try {
                     var729 = new GpuDelegate(var738);
                  } catch (Throwable var711) {
                     var12 = var711;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var711);
                     var18 = var17;
                     String var745 = var711.getMessage();
                     var13 = var745;
                     if (var745 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  GpuDelegate var726 = var729;
                  var11 = var729;

                  try {
                  } catch (Throwable var710) {
                     var12 = var710;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var710);
                     var18 = var17;
                     String var746 = var710.getMessage();
                     var13 = var746;
                     if (var746 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  var11 = var729;

                  try {
                  } catch (Throwable var709) {
                     var12 = var709;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var709);
                     var18 = var17;
                     String var748 = var709.getMessage();
                     var13 = var748;
                     if (var748 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  var11 = var729;

                  try {
                     var730 = new org.tensorflow.lite.Interpreter.Options();
                  } catch (Throwable var708) {
                     var12 = var708;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var708);
                     var18 = var17;
                     String var749 = var708.getMessage();
                     var13 = var749;
                     if (var749 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  var11 = var729;

                  try {
                     var747 = new Interpreter(var15, var730.addDelegate(var726));
                  } catch (Throwable var707) {
                     var12 = var707;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var707);
                     var18 = var17;
                     String var750 = var707.getMessage();
                     var13 = var750;
                     if (var750 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  var11 = var729;

                  try {
                     this.gpu = var726;
                  } catch (Throwable var706) {
                     var12 = var706;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var706);
                     var18 = var17;
                     String var751 = var706.getMessage();
                     var13 = var751;
                     if (var751 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  if (var6) {
                     var13 = "gpu";
                  } else {
                     var13 = "gpu (no cache)";
                  }

                  var11 = var729;

                  try {
                     this.accel = var13;
                  } catch (Throwable var705) {
                     var12 = var705;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var705);
                     var18 = var17;
                     String var752 = var705.getMessage();
                     var13 = var752;
                     if (var752 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  var11 = var729;

                  try {
                     this.interp = var747;
                  } catch (Throwable var704) {
                     var12 = var704;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var704);
                     var18 = var17;
                     String var753 = var704.getMessage();
                     var13 = var753;
                     if (var753 != null) {
                        break label4414;
                     }
                     break label4423;
                  }

                  var11 = var729;

                  label4347: {
                     label4425: {
                        try {
                           if (var17.isEmpty()) {
                              break label4425;
                           }
                        } catch (Throwable var703) {
                           var12 = var703;
                           Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var703);
                           var18 = var17;
                           String var754 = var703.getMessage();
                           var13 = var754;
                           if (var754 != null) {
                              break label4414;
                           }
                           break label4423;
                        }

                        var11 = var729;

                        try {
                           var13 = (String)var17.get(var1);
                        } catch (Throwable var702) {
                           var12 = var702;
                           Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var702);
                           var18 = var17;
                           String var755 = var702.getMessage();
                           var13 = var755;
                           if (var755 != null) {
                              break label4414;
                           }
                           break label4423;
                        }

                        var11 = var729;

                        try {
                           Intrinsics.checkNotNullExpressionValue(var13, "get(...)");
                        } catch (Throwable var701) {
                           var12 = var701;
                           Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var701);
                           var18 = var17;
                           String var756 = var701.getMessage();
                           var13 = var756;
                           if (var756 != null) {
                              break label4414;
                           }
                           break label4423;
                        }

                        var11 = var729;

                        try {
                           var764 = StringsKt.take(var13, 100);
                        } catch (Throwable var700) {
                           var12 = var700;
                           Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var700);
                           var18 = var17;
                           String var757 = var700.getMessage();
                           var13 = var757;
                           if (var757 != null) {
                              break label4414;
                           }
                           break label4423;
                        }

                        var11 = var729;

                        try {
                        } catch (Throwable var699) {
                           var12 = var699;
                           Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var699);
                           var18 = var17;
                           String var758 = var699.getMessage();
                           var13 = var758;
                           if (var758 != null) {
                              break label4414;
                           }
                           break label4423;
                        }

                        var11 = var729;

                        try {
                           var733 = new StringBuilder();
                        } catch (Throwable var698) {
                           var12 = var698;
                           Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var698);
                           var18 = var17;
                           String var759 = var698.getMessage();
                           var13 = var759;
                           if (var759 != null) {
                              break label4414;
                           }
                           break label4423;
                        }

                        var11 = var729;

                        try {
                           var13 = var733.append("cache: ").append(var764).toString();
                           break label4347;
                        } catch (Throwable var697) {
                           var12 = var697;
                           Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var697);
                           var18 = var17;
                           String var760 = var697.getMessage();
                           var13 = var760;
                           if (var760 != null) {
                              break label4414;
                           }
                           break label4423;
                        }
                     }

                     var13 = "";
                  }

                  var11 = var729;

                  label4322:
                  try {
                     this.gpuError = var13;
                     return var747;
                  } catch (Throwable var696) {
                     var12 = var696;
                     Log.w("StarView/Layout", "GPU (cache=" + var6 + "): " + var696);
                     var18 = var17;
                     String var761 = var696.getMessage();
                     var13 = var761;
                     if (var761 != null) {
                        break label4414;
                     }
                     break label4322;
                  }
               }

               var13 = var12.toString();
            }

            var18.add(StringsKt.replace$default(var13, "\n", " ", false, 4, null));
            if (var11 != null) {
               label4291:
               try {
                  var11.close();
               } finally {
                  break label4291;
               }
            }

            if (var6) {
               File[] var724 = var10.listFiles();
               if (var724 != null) {
                  int var5 = var724.length;
                  int var2 = var1;
                  byte var4 = var1;

                  for (var1 = var3; var2 < var5; var4 = 0) {
                     File var735 = var724[var2];
                     String var762 = var735.getName();
                     Intrinsics.checkNotNullExpressionValue(var762, "getName(...)");
                     if (StringsKt.startsWith$default(var762, "layout_", false, 2, null)) {
                        var735.delete();
                     }

                     var2++;
                     var1 = 2;
                  }

                  var3 = var1;
                  var1 = var4;
               }
            }
         }

         this.gpuError = StringsKt.take(CollectionsKt.last(var17), 160);
         var9 = new Interpreter(var15, new org.tensorflow.lite.Interpreter.Options().setNumThreads(3));
         this.accel = "cpu";
         this.interp = var9;
         return var9;
      }
   }

   private final int interpClass(float var1, float var2) {
      int var12 = RangesKt.coerceIn((int)var1, 0, 55);
      int var9 = RangesKt.coerceIn((int)var2, 0, 159);
      int var11 = Math.min(55, var12 + 1);
      int var10 = Math.min(159, var9 + 1);
      float var4 = RangesKt.coerceIn(var1 - var12, 0.0F, 1.0F);
      float var5 = RangesKt.coerceIn(var2 - var9, 0.0F, 1.0F);
      float var6 = 1;
      int var8 = 0;
      var2 = -Float.MAX_VALUE;
      int var7 = 0;

      while (var7 < 19) {
         float var3 = this.lg[(var12 * 160 + var9) * 19 + var7] * ((var6 - var4) * (var6 - var5))
            + this.lg[(var12 * 160 + var10) * 19 + var7] * ((var6 - var4) * var5)
            + this.lg[(var11 * 160 + var9) * 19 + var7] * ((var6 - var5) * var4)
            + this.lg[(var11 * 160 + var10) * 19 + var7] * (var4 * var5);
         var1 = var2;
         if (var3 > var2) {
            var8 = var7;
            var1 = var3;
         }

         var7++;
         var2 = var1;
      }

      return var8;
   }

   private final void loop() {
      try {
         this.loopInner();
      } finally {
         this.close();
      }
   }

   private final void loopInner() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield com/starpilot/starview/LayoutSeg.running Z
      // 004: ifeq 408
      // 007: aload 0
      // 008: getfield com/starpilot/starview/LayoutSeg.enabled Z
      // 00b: ifne 024
      // 00e: aload 0
      // 00f: getfield com/starpilot/starview/LayoutSeg.interp Lorg/tensorflow/lite/Interpreter;
      // 012: ifnull 01f
      // 015: aload 0
      // 016: invokespecial com/starpilot/starview/LayoutSeg.close ()V
      // 019: aload 0
      // 01a: ldc "off"
      // 01c: putfield com/starpilot/starview/LayoutSeg.status Ljava/lang/String;
      // 01f: aload 0
      // 020: bipush 0
      // 021: putfield com/starpilot/starview/LayoutSeg.busy Z
      // 024: aload 0
      // 025: getfield com/starpilot/starview/LayoutSeg.lock Ljava/lang/Object;
      // 028: astore 18
      // 02a: aload 18
      // 02c: monitorenter
      // 02d: aload 0
      // 02e: getfield com/starpilot/starview/LayoutSeg.running Z
      // 031: ifeq 04c
      // 034: aload 0
      // 035: getfield com/starpilot/starview/LayoutSeg.busy Z
      // 038: ifeq 042
      // 03b: aload 0
      // 03c: getfield com/starpilot/starview/LayoutSeg.enabled Z
      // 03f: ifne 04c
      // 042: aload 0
      // 043: getfield com/starpilot/starview/LayoutSeg.lock Ljava/lang/Object;
      // 046: ldc2_w 500
      // 049: invokevirtual java/lang/Object.wait (J)V
      // 04c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 04f: astore 19
      // 051: aload 18
      // 053: monitorexit
      // 054: aload 0
      // 055: getfield com/starpilot/starview/LayoutSeg.running Z
      // 058: ifeq 000
      // 05b: aload 0
      // 05c: getfield com/starpilot/starview/LayoutSeg.busy Z
      // 05f: ifeq 000
      // 062: aload 0
      // 063: getfield com/starpilot/starview/LayoutSeg.enabled Z
      // 066: ifne 06c
      // 069: goto 000
      // 06c: aload 0
      // 06d: invokespecial com/starpilot/starview/LayoutSeg.ensure ()Lorg/tensorflow/lite/Interpreter;
      // 070: astore 19
      // 072: aload 19
      // 074: ifnonnull 088
      // 077: aload 0
      // 078: checkcast com/starpilot/starview/LayoutSeg
      // 07b: bipush 0
      // 07c: putfield com/starpilot/starview/LayoutSeg.busy Z
      // 07f: ldc2_w 2000
      // 082: invokestatic java/lang/Thread.sleep (J)V
      // 085: goto 000
      // 088: invokestatic java/lang/System.nanoTime ()J
      // 08b: lstore 14
      // 08d: aload 0
      // 08e: getfield com/starpilot/starview/LayoutSeg.inBuf Ljava/nio/ByteBuffer;
      // 091: invokevirtual java/nio/ByteBuffer.rewind ()Ljava/nio/Buffer;
      // 094: pop
      // 095: aload 0
      // 096: getfield com/starpilot/starview/LayoutSeg.inBuf Ljava/nio/ByteBuffer;
      // 099: invokevirtual java/nio/ByteBuffer.asFloatBuffer ()Ljava/nio/FloatBuffer;
      // 09c: aload 0
      // 09d: getfield com/starpilot/starview/LayoutSeg.px [F
      // 0a0: invokevirtual java/nio/FloatBuffer.put ([F)Ljava/nio/FloatBuffer;
      // 0a3: pop
      // 0a4: aload 0
      // 0a5: getfield com/starpilot/starview/LayoutSeg.inBuf Ljava/nio/ByteBuffer;
      // 0a8: invokevirtual java/nio/ByteBuffer.rewind ()Ljava/nio/Buffer;
      // 0ab: pop
      // 0ac: aload 0
      // 0ad: getfield com/starpilot/starview/LayoutSeg.outBuf Ljava/nio/ByteBuffer;
      // 0b0: invokevirtual java/nio/ByteBuffer.rewind ()Ljava/nio/Buffer;
      // 0b3: pop
      // 0b4: aload 0
      // 0b5: getfield com/starpilot/starview/LayoutSeg.pendingNs J
      // 0b8: lstore 16
      // 0ba: aload 0
      // 0bb: getfield com/starpilot/starview/LayoutSeg.feedH F
      // 0be: fstore 6
      // 0c0: aload 0
      // 0c1: bipush 0
      // 0c2: putfield com/starpilot/starview/LayoutSeg.busy Z
      // 0c5: aload 19
      // 0c7: aload 0
      // 0c8: getfield com/starpilot/starview/LayoutSeg.inBuf Ljava/nio/ByteBuffer;
      // 0cb: aload 0
      // 0cc: getfield com/starpilot/starview/LayoutSeg.outBuf Ljava/nio/ByteBuffer;
      // 0cf: invokevirtual org/tensorflow/lite/Interpreter.run (Ljava/lang/Object;Ljava/lang/Object;)V
      // 0d2: aload 0
      // 0d3: getfield com/starpilot/starview/LayoutSeg.outBuf Ljava/nio/ByteBuffer;
      // 0d6: astore 18
      // 0d8: aload 18
      // 0da: invokevirtual java/nio/ByteBuffer.rewind ()Ljava/nio/Buffer;
      // 0dd: pop
      // 0de: aload 18
      // 0e0: invokevirtual java/nio/ByteBuffer.asFloatBuffer ()Ljava/nio/FloatBuffer;
      // 0e3: astore 18
      // 0e5: aload 18
      // 0e7: aload 0
      // 0e8: getfield com/starpilot/starview/LayoutSeg.lg [F
      // 0eb: invokevirtual java/nio/FloatBuffer.get ([F)Ljava/nio/FloatBuffer;
      // 0ee: pop
      // 0ef: bipush 0
      // 0f0: istore 9
      // 0f2: iload 9
      // 0f4: sipush 8960
      // 0f7: if_icmpge 14d
      // 0fa: bipush 0
      // 0fb: istore 10
      // 0fd: aload 0
      // 0fe: getfield com/starpilot/starview/LayoutSeg.lg [F
      // 101: iload 9
      // 103: bipush 19
      // 105: imul
      // 106: faload
      // 107: fstore 4
      // 109: bipush 1
      // 10a: istore 8
      // 10c: iload 8
      // 10e: bipush 19
      // 110: if_icmpge 13d
      // 113: aload 0
      // 114: getfield com/starpilot/starview/LayoutSeg.lg [F
      // 117: iload 9
      // 119: bipush 19
      // 11b: imul
      // 11c: iload 8
      // 11e: iadd
      // 11f: faload
      // 120: fstore 5
      // 122: fload 4
      // 124: fstore 3
      // 125: fload 5
      // 127: fload 4
      // 129: fcmpl
      // 12a: ifle 134
      // 12d: fload 5
      // 12f: fstore 3
      // 130: iload 8
      // 132: istore 10
      // 134: iinc 8 1
      // 137: fload 3
      // 138: fstore 4
      // 13a: goto 10c
      // 13d: aload 0
      // 13e: getfield com/starpilot/starview/LayoutSeg.cls [B
      // 141: iload 9
      // 143: iload 10
      // 145: i2b
      // 146: bastore
      // 147: iinc 9 1
      // 14a: goto 0f2
      // 14d: bipush 0
      // 14e: istore 9
      // 150: iload 9
      // 152: sipush 160
      // 155: if_icmpge 1b6
      // 158: bipush -1
      // 159: istore 12
      // 15b: bipush -1
      // 15c: istore 13
      // 15e: bipush 53
      // 160: istore 8
      // 162: iload 12
      // 164: istore 10
      // 166: iload 13
      // 168: istore 11
      // 16a: iload 8
      // 16c: iflt 19e
      // 16f: aload 0
      // 170: getfield com/starpilot/starview/LayoutSeg.cls [B
      // 173: iload 8
      // 175: sipush 160
      // 178: imul
      // 179: iload 9
      // 17b: iadd
      // 17c: baload
      // 17d: istore 11
      // 17f: iload 11
      // 181: ifeq 198
      // 184: iload 11
      // 186: bipush 1
      // 187: if_icmpeq 198
      // 18a: iload 11
      // 18c: bipush 9
      // 18e: if_icmpeq 198
      // 191: iload 8
      // 193: istore 10
      // 195: goto 19e
      // 198: iinc 8 -1
      // 19b: goto 162
      // 19e: aload 0
      // 19f: getfield com/starpilot/starview/LayoutSeg.blockRow [I
      // 1a2: iload 9
      // 1a4: iload 10
      // 1a6: iastore
      // 1a7: aload 0
      // 1a8: getfield com/starpilot/starview/LayoutSeg.blockCls [I
      // 1ab: iload 9
      // 1ad: iload 11
      // 1af: iastore
      // 1b0: iinc 9 1
      // 1b3: goto 150
      // 1b6: sipush 6400
      // 1b9: newarray 8
      // 1bb: astore 20
      // 1bd: bipush 0
      // 1be: istore 8
      // 1c0: iload 8
      // 1c2: sipush 6400
      // 1c5: if_icmpge 1d4
      // 1c8: aload 20
      // 1ca: iload 8
      // 1cc: bipush -1
      // 1cd: bastore
      // 1ce: iinc 8 1
      // 1d1: goto 1c0
      // 1d4: bipush 0
      // 1d5: istore 9
      // 1d7: iload 9
      // 1d9: bipush 80
      // 1db: if_icmpge 34d
      // 1de: iload 9
      // 1e0: i2f
      // 1e1: ldc 0.5
      // 1e3: fadd
      // 1e4: ldc 0.5
      // 1e6: fmul
      // 1e7: fstore 5
      // 1e9: sipush 144
      // 1ec: i2f
      // 1ed: fstore 4
      // 1ef: fload 4
      // 1f1: fload 6
      // 1f3: ldc 200.0
      // 1f5: fmul
      // 1f6: fload 5
      // 1f8: fdiv
      // 1f9: fadd
      // 1fa: ldc_w 4.0
      // 1fd: fdiv
      // 1fe: fstore 7
      // 200: fload 7
      // 202: ldc_w 56.0
      // 205: fcmpl
      // 206: ifge 347
      // 209: fload 7
      // 20b: fconst_0
      // 20c: fcmpg
      // 20d: ifge 213
      // 210: goto 347
      // 213: fload 7
      // 215: f2i
      // 216: istore 11
      // 218: bipush 0
      // 219: istore 10
      // 21b: iload 10
      // 21d: bipush 80
      // 21f: if_icmpge 344
      // 222: iload 10
      // 224: i2f
      // 225: ldc 0.5
      // 227: fadd
      // 228: ldc 0.5
      // 22a: fmul
      // 22b: ldc_w 20.0
      // 22e: fsub
      // 22f: ldc 200.0
      // 231: fmul
      // 232: fload 5
      // 234: fdiv
      // 235: ldc_w 320.0
      // 238: fadd
      // 239: ldc_w 4.0
      // 23c: fdiv
      // 23d: fstore 3
      // 23e: fload 3
      // 23f: fconst_0
      // 240: fcmpg
      // 241: iflt 33e
      // 244: fload 3
      // 245: ldc_w 160.0
      // 248: fcmpl
      // 249: iflt 24f
      // 24c: goto 33e
      // 24f: fload 3
      // 250: f2i
      // 251: istore 12
      // 253: iload 11
      // 255: aload 0
      // 256: getfield com/starpilot/starview/LayoutSeg.blockRow [I
      // 259: iload 12
      // 25b: iaload
      // 25c: if_icmple 2ae
      // 25f: fload 5
      // 261: ldc_w 30.0
      // 264: fcmpl
      // 265: ifgt 2ab
      // 268: getstatic com/starpilot/starview/LayoutSeg.GROUP [I
      // 26b: aload 0
      // 26c: fload 7
      // 26e: ldc 0.5
      // 270: fsub
      // 271: fload 3
      // 272: ldc 0.5
      // 274: fsub
      // 275: invokespecial com/starpilot/starview/LayoutSeg.interpClass (FF)I
      // 278: iaload
      // 279: istore 8
      // 27b: iload 8
      // 27d: sipush 255
      // 280: if_icmpne 29a
      // 283: getstatic com/starpilot/starview/LayoutSeg.GROUP [I
      // 286: aload 0
      // 287: getfield com/starpilot/starview/LayoutSeg.cls [B
      // 28a: iload 11
      // 28c: sipush 160
      // 28f: imul
      // 290: iload 12
      // 292: iadd
      // 293: baload
      // 294: iaload
      // 295: istore 8
      // 297: goto 29a
      // 29a: aload 20
      // 29c: iload 9
      // 29e: bipush 80
      // 2a0: imul
      // 2a1: iload 10
      // 2a3: iadd
      // 2a4: iload 8
      // 2a6: i2b
      // 2a7: bastore
      // 2a8: goto 33e
      // 2ab: goto 33e
      // 2ae: aload 0
      // 2af: getfield com/starpilot/starview/LayoutSeg.blockCls [I
      // 2b2: iload 12
      // 2b4: iaload
      // 2b5: tableswitch 43 2 8 55 55 55 43 43 43 49
      // 2e0: bipush -1
      // 2e1: istore 8
      // 2e3: goto 2ef
      // 2e6: bipush 2
      // 2e7: istore 8
      // 2e9: goto 2ef
      // 2ec: bipush 3
      // 2ed: istore 8
      // 2ef: iload 8
      // 2f1: iflt 33e
      // 2f4: aload 0
      // 2f5: getfield com/starpilot/starview/LayoutSeg.blockRow [I
      // 2f8: iload 12
      // 2fa: iaload
      // 2fb: bipush 1
      // 2fc: iadd
      // 2fd: i2f
      // 2fe: ldc_w 4.0
      // 301: fmul
      // 302: fload 4
      // 304: fsub
      // 305: fstore 3
      // 306: fload 3
      // 307: fconst_0
      // 308: fcmpl
      // 309: ifle 317
      // 30c: fload 6
      // 30e: ldc 200.0
      // 310: fmul
      // 311: fload 3
      // 312: fdiv
      // 313: fstore 3
      // 314: goto 31b
      // 317: ldc_w 999.0
      // 31a: fstore 3
      // 31b: fload 5
      // 31d: fload 3
      // 31e: fcmpl
      // 31f: iflt 33e
      // 322: fload 5
      // 324: fload 3
      // 325: ldc_w 2.5
      // 328: fadd
      // 329: fcmpg
      // 32a: ifgt 33e
      // 32d: aload 20
      // 32f: iload 9
      // 331: bipush 80
      // 333: imul
      // 334: iload 10
      // 336: iadd
      // 337: iload 8
      // 339: i2b
      // 33a: bastore
      // 33b: goto 33e
      // 33e: iinc 10 1
      // 341: goto 21b
      // 344: goto 347
      // 347: iinc 9 1
      // 34a: goto 1d7
      // 34d: new com/starpilot/starview/LayoutSeg$Grid
      // 350: astore 18
      // 352: aload 18
      // 354: aload 20
      // 356: lload 16
      // 358: invokespecial com/starpilot/starview/LayoutSeg$Grid.<init> ([BJ)V
      // 35b: aload 0
      // 35c: aload 18
      // 35e: putfield com/starpilot/starview/LayoutSeg.grid Lcom/starpilot/starview/LayoutSeg$Grid;
      // 361: invokestatic java/lang/System.nanoTime ()J
      // 364: lload 14
      // 366: lsub
      // 367: l2d
      // 368: ldc2_w 1000000.0
      // 36b: ddiv
      // 36c: dstore 1
      // 36d: aload 0
      // 36e: getfield com/starpilot/starview/LayoutSeg.ms D
      // 371: dconst_0
      // 372: dcmpg
      // 373: ifne 37c
      // 376: bipush 1
      // 377: istore 8
      // 379: goto 37f
      // 37c: bipush 0
      // 37d: istore 8
      // 37f: iload 8
      // 381: ifeq 387
      // 384: goto 396
      // 387: aload 0
      // 388: getfield com/starpilot/starview/LayoutSeg.ms D
      // 38b: ldc2_w 0.8
      // 38e: dmul
      // 38f: ldc2_w 0.2
      // 392: dload 1
      // 393: dmul
      // 394: dadd
      // 395: dstore 1
      // 396: aload 0
      // 397: dload 1
      // 398: putfield com/starpilot/starview/LayoutSeg.ms D
      // 39b: aload 0
      // 39c: ldc_w "running"
      // 39f: putfield com/starpilot/starview/LayoutSeg.status Ljava/lang/String;
      // 3a2: goto 000
      // 3a5: astore 18
      // 3a7: ldc "StarView/Layout"
      // 3a9: new java/lang/StringBuilder
      // 3ac: dup
      // 3ad: invokespecial java/lang/StringBuilder.<init> ()V
      // 3b0: ldc_w "layout: "
      // 3b3: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3b6: aload 18
      // 3b8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 3bb: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3be: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 3c1: pop
      // 3c2: aload 18
      // 3c4: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 3c7: astore 18
      // 3c9: aload 0
      // 3ca: new java/lang/StringBuilder
      // 3cd: dup
      // 3ce: invokespecial java/lang/StringBuilder.<init> ()V
      // 3d1: ldc_w "error: "
      // 3d4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3d7: aload 18
      // 3d9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3dc: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3df: putfield com/starpilot/starview/LayoutSeg.status Ljava/lang/String;
      // 3e2: aload 0
      // 3e3: bipush 0
      // 3e4: putfield com/starpilot/starview/LayoutSeg.busy Z
      // 3e7: aload 0
      // 3e8: invokespecial com/starpilot/starview/LayoutSeg.close ()V
      // 3eb: ldc2_w 3000
      // 3ee: invokestatic java/lang/Thread.sleep (J)V
      // 3f1: goto 3f6
      // 3f4: astore 18
      // 3f6: aload 0
      // 3f7: ldc_w "retrying"
      // 3fa: putfield com/starpilot/starview/LayoutSeg.status Ljava/lang/String;
      // 3fd: goto 000
      // 400: astore 19
      // 402: aload 18
      // 404: monitorexit
      // 405: aload 19
      // 407: athrow
      // 408: return
   }

   private static final void start$lambda$4(LayoutSeg var0) {
      var0.loop();
   }

   public final void feed(byte[] var1, byte[] var2, byte[] var3, int var4, int var5, int var6, int var7, int var8, float[] var9, float var10, long var11) {
      Intrinsics.checkNotNullParameter(var1, "y");
      Intrinsics.checkNotNullParameter(var2, "u");
      Intrinsics.checkNotNullParameter(var3, "v");
      Intrinsics.checkNotNullParameter(var9, "rpy");
      if (!this.busy) {
         this.lastFeedNs = System.nanoTime();
         String var29 = String.format("%.3f", Arrays.copyOf(new Object[]{var9[0]}, 1));
         Intrinsics.checkNotNullExpressionValue(var29, "format(...)");
         String var30 = String.format("%.3f", Arrays.copyOf(new Object[]{var9[1]}, 1));
         Intrinsics.checkNotNullExpressionValue(var30, "format(...)");
         String var31 = String.format("%.3f", Arrays.copyOf(new Object[]{var9[2]}, 1));
         Intrinsics.checkNotNullExpressionValue(var31, "format(...)");
         var29 = var7 + ":" + var8 + ":" + var29 + ":" + var30 + ":" + var31;
         if (!Intrinsics.areEqual(var29, this.lutKey)) {
            this.buildLut(var7, var8, var9);
            this.lutKey = var29;
         }

         int var20;
         long var49;
         label104: {
            int var17;
            int var19;
            int var23;
            int var24;
            label111: {
               var38 = this.lut;
               byte var18 = 0;
               long var25 = 0L;
               var20 = 0;
               byte var22 = 0;
               IntProgression var51 = RangesKt.step(RangesKt.until(0, 143360), 97);
               int var21 = var51.getFirst();
               var24 = var51.getLast();
               var23 = var51.getStep();
               if (var23 > 0) {
                  var36 = var18;
                  var19 = var21;
                  var49 = var25;
                  var17 = var22;
                  if (var21 <= var24) {
                     break label111;
                  }
               }

               if (var23 >= 0 || var24 > var21) {
                  var37 = 0;
                  var49 = var25;
                  break label104;
               }

               var17 = var22;
               var49 = var25;
               var19 = var21;
               var36 = var18;
            }

            while (true) {
               int var42 = var38[var19];
               long var48;
               if (var42 >= 0) {
                  var48 = var49 + (var1[var42 / var7 * var4 + var42 % var7] & 255);
                  var17++;
               } else {
                  var48 = var49;
               }

               var49 = var48;
               var20 = var17;
               var37 = var36;
               if (var19 == var24) {
                  break;
               }

               var19 += var23;
               var36 = var36;
               var49 = var48;
            }
         }

         if (var20 > 0 && var49 / var20 < 55L) {
            this.dark = true;
            this.grid = null;
            this.status = "paused: too dark";
         } else {
            this.dark = false;

            for (int var41 = 0; var41 < 143360; var41++) {
               int var46 = var38[var41];
               if (var46 < 0) {
                  this.px[var37] = (0.45F - MEAN[0]) / STD[0];
                  this.px[var37 + 1] = (0.45F - MEAN[1]) / STD[1];
                  this.px[var37 + 2] = (0.45F - MEAN[2]) / STD[2];
                  var37 += 3;
               } else {
                  int var44 = var46 % var7;
                  var46 /= var7;
                  float var14 = (var1[var46 * var4 + var44] & 255) - 16.0F;
                  var44 = (var46 >> 1) * var5 + (var44 >> 1) * var6;
                  float var16 = (var2[var44] & 255) - 128.0F;
                  float var15 = (var3[var44] & 255) - 128.0F;
                  float var13 = RangesKt.coerceIn(var14 * 1.164F + 1.596F * var15, 0.0F, 255.0F);
                  var15 = RangesKt.coerceIn(var14 * 1.164F - 0.391F * var16 - 0.813F * var15, 0.0F, 255.0F);
                  var14 = RangesKt.coerceIn(1.164F * var14 + 2.018F * var16, 0.0F, 255.0F);
                  this.px[var37] = (var13 / 255.0F - MEAN[0]) / STD[0];
                  this.px[var37 + 1] = (var15 / 255.0F - MEAN[1]) / STD[1];
                  this.px[var37 + 2] = (var14 / 255.0F - MEAN[2]) / STD[2];
                  var37 += 3;
               }
            }

            this.feedH = var10;
            this.pendingNs = var11;
            this.busy = true;
            Object var34 = this.lock;
            synchronized (var34) {
               this.lock.notifyAll();
               Unit var35 = Unit.INSTANCE;
            }
         }
      }
   }

   public final String getAccel() {
      return this.accel;
   }

   public final boolean getDark() {
      return this.dark;
   }

   public final boolean getEnabled() {
      return this.enabled;
   }

   public final String getGpuError() {
      return this.gpuError;
   }

   public final LayoutSeg.Grid getGrid() {
      return this.grid;
   }

   public final boolean getHasModel() {
      boolean var1;
      if (this.modelFile().length() > 1000000L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final double getMs() {
      return this.ms;
   }

   public final String getStatus() {
      return this.status;
   }

   public final String importModel(Uri param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: aload 1
      // 001: ldc_w "uri"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: new java/io/File
      // 00a: astore 2
      // 00b: aload 2
      // 00c: aload 0
      // 00d: getfield com/starpilot/starview/LayoutSeg.ctx Landroid/content/Context;
      // 010: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 013: ldc_w "layout_segformer_b0.lite.part"
      // 016: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 019: aload 0
      // 01a: getfield com/starpilot/starview/LayoutSeg.ctx Landroid/content/Context;
      // 01d: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 020: aload 1
      // 021: invokevirtual android/content/ContentResolver.openInputStream (Landroid/net/Uri;)Ljava/io/InputStream;
      // 024: astore 1
      // 025: aload 1
      // 026: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 029: aload 1
      // 02a: checkcast java/io/Closeable
      // 02d: astore 3
      // 02e: aload 3
      // 02f: checkcast java/io/InputStream
      // 032: astore 5
      // 034: new java/io/FileOutputStream
      // 037: astore 1
      // 038: aload 1
      // 039: aload 2
      // 03a: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 03d: aload 1
      // 03e: checkcast java/io/Closeable
      // 041: astore 4
      // 043: aload 4
      // 045: checkcast java/io/FileOutputStream
      // 048: checkcast java/io/OutputStream
      // 04b: astore 6
      // 04d: aconst_null
      // 04e: astore 1
      // 04f: aload 5
      // 051: aload 6
      // 053: bipush 0
      // 054: bipush 2
      // 055: aconst_null
      // 056: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
      // 059: pop2
      // 05a: aload 4
      // 05c: aconst_null
      // 05d: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 060: aload 3
      // 061: aconst_null
      // 062: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 065: new java/io/FileInputStream
      // 068: astore 3
      // 069: aload 3
      // 06a: aload 2
      // 06b: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 06e: aload 3
      // 06f: checkcast java/io/Closeable
      // 072: astore 3
      // 073: aload 3
      // 074: checkcast java/io/FileInputStream
      // 077: astore 5
      // 079: bipush 8
      // 07b: newarray 8
      // 07d: astore 4
      // 07f: aload 5
      // 081: aload 4
      // 083: invokevirtual java/io/FileInputStream.read ([B)I
      // 086: pop
      // 087: aload 3
      // 088: aconst_null
      // 089: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 08c: aload 2
      // 08d: invokevirtual java/io/File.length ()J
      // 090: ldc2_w 1000000
      // 093: lcmp
      // 094: iflt 0ca
      // 097: new java/lang/String
      // 09a: astore 3
      // 09b: aload 3
      // 09c: aload 4
      // 09e: bipush 4
      // 09f: bipush 4
      // 0a0: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 0a3: invokespecial java/lang/String.<init> ([BIILjava/nio/charset/Charset;)V
      // 0a6: aload 3
      // 0a7: ldc_w "TFL3"
      // 0aa: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0ad: ifne 0b3
      // 0b0: goto 0ca
      // 0b3: aload 0
      // 0b4: invokespecial com/starpilot/starview/LayoutSeg.close ()V
      // 0b7: aload 2
      // 0b8: aload 0
      // 0b9: invokevirtual com/starpilot/starview/LayoutSeg.modelFile ()Ljava/io/File;
      // 0bc: invokevirtual java/io/File.renameTo (Ljava/io/File;)Z
      // 0bf: pop
      // 0c0: aload 0
      // 0c1: ldc_w "model imported"
      // 0c4: putfield com/starpilot/starview/LayoutSeg.status Ljava/lang/String;
      // 0c7: goto 0d3
      // 0ca: aload 2
      // 0cb: invokevirtual java/io/File.delete ()Z
      // 0ce: pop
      // 0cf: ldc_w "That file isn't the layout model (.lite)"
      // 0d2: astore 1
      // 0d3: goto 113
      // 0d6: astore 1
      // 0d7: aload 1
      // 0d8: athrow
      // 0d9: astore 2
      // 0da: aload 3
      // 0db: aload 1
      // 0dc: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0df: aload 2
      // 0e0: athrow
      // 0e1: astore 1
      // 0e2: aload 1
      // 0e3: athrow
      // 0e4: astore 2
      // 0e5: aload 4
      // 0e7: aload 1
      // 0e8: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0eb: aload 2
      // 0ec: athrow
      // 0ed: astore 2
      // 0ee: aload 2
      // 0ef: athrow
      // 0f0: astore 1
      // 0f1: aload 3
      // 0f2: aload 2
      // 0f3: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0f6: aload 1
      // 0f7: athrow
      // 0f8: astore 1
      // 0f9: aload 1
      // 0fa: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 0fd: astore 1
      // 0fe: new java/lang/StringBuilder
      // 101: dup
      // 102: invokespecial java/lang/StringBuilder.<init> ()V
      // 105: ldc_w "Import failed: "
      // 108: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 10b: aload 1
      // 10c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 10f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 112: astore 1
      // 113: aload 1
      // 114: areturn
   }

   public final File modelFile() {
      return new File(this.ctx.getFilesDir(), "layout_segformer_b0.lite");
   }

   public final void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public final void shutdown() {
      this.enabled = false;
      this.running = false;
      this.grid = null;
      Object var1 = this.lock;
      synchronized (var1) {
         this.lock.notifyAll();
         Unit var2 = Unit.INSTANCE;
      }
   }

   public final void start() {
      if (!this.running) {
         this.running = true;
         Thread var1 = new Thread(new LayoutSeg$$ExternalSyntheticLambda0(this), "starview-layout");
         var1.setPriority(3);
         var1.setDaemon(true);
         var1.start();
         this.worker = var1;
      }
   }

   public final void stop() {
      this.enabled = false;
      this.grid = null;
      Object var1 = this.lock;
      synchronized (var1) {
         this.lock.notifyAll();
         Unit var2 = Unit.INSTANCE;
      }
   }

   public final boolean wantsFrame(long var1) {
      boolean var5 = this.enabled;
      boolean var4 = false;
      boolean var3 = var4;
      if (var5) {
         var3 = var4;
         if (this.getHasModel()) {
            var3 = var4;
            if (!this.busy) {
               var3 = var4;
               if (!StringsKt.startsWith$default(this.status, "error", false, 2, null)) {
                  var3 = var4;
                  if (var1 - this.lastFeedNs > 250000000L) {
                     var3 = true;
                  }
               }
            }
         }
      }

      return var3;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final int[] getGROUP() {
         return LayoutSeg.GROUP;
      }
   }

   public static final class Grid {
      private final long atNs;
      private final byte[] g;

      public Grid(byte[] var1, long var2) {
         Intrinsics.checkNotNullParameter(var1, "g");
         super();
         this.g = var1;
         this.atNs = var2;
      }

      public final long getAtNs() {
         return this.atNs;
      }

      public final byte[] getG() {
         return this.g;
      }
   }
}
