package com.starpilot.starview;

import android.content.Context;
import android.content.Intent;
import android.media.AudioRecord;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

public final class VoiceNote {
   private final Context ctx;
   private boolean delivered;
   private File file;
   private volatile float level;
   private final Handler main;
   private Function1<? super String, Unit> onText;
   private String partial;
   private AutoCloseOutputStream pipeOut;
   private AudioRecord rec;
   private SpeechRecognizer recognizer;
   private volatile boolean running;
   private Thread thread;

   // $VF: synthetic method
   public static void $r8$lambda$_CLskCfSwRmv07i0mCgv5NTFHdE/* $VF was: $r8$lambda$_CLskCfSwRmv07i0mCgv5NTFHdE*/(VoiceNote var0) {
      start$lambda$2$lambda$1(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Tr8ro7o7Smt2EuMQi4k9m3HXSS0(File var0, VoiceNote var1, AudioRecord var2, int var3) {
      start$lambda$2(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$lzjs2KyURAjtaiGNbGR6Xi8yeVU(VoiceNote var0, String var1) {
      deliver$lambda$4(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$qG1F_Zr0aTShUdMlg9WZIpvcCAo(VoiceNote var0) {
      start$lambda$2$lambda$1$lambda$0(var0);
   }

   public VoiceNote(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      this.ctx = var1;
      this.main = new Handler(Looper.getMainLooper());
      this.partial = "";
   }

   private final void deliver(String var1) {
      if (!this.delivered) {
         this.delivered = true;
         this.main.post(new VoiceNote$$ExternalSyntheticLambda2(this, var1));
      }
   }

   private static final void deliver$lambda$4(VoiceNote var0, String var1) {
      SpeechRecognizer var2 = var0.recognizer;
      if (var2 != null) {
         var2.destroy();
      }

      var0.recognizer = null;
      Function1 var3 = var0.onText;
      if (var3 != null) {
         var3.invoke(StringsKt.trim(var1).toString());
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final void start$lambda$2(File var0, VoiceNote var1, AudioRecord var2, int var3) {
      FileOutputStream var15 = new FileOutputStream(var0);
      var15.write(new byte[44]);
      short[] var17 = new short[1600];
      byte[] var16 = new byte[3200];
      long var9 = 0L;
      long var13 = System.currentTimeMillis();

      while (var1.running && System.currentTimeMillis() - var13 < 60000L) {
         int var7 = var2.read(var17, 0, var17.length);
         if (var7 > 0) {
            int var6 = 0;
            int var4 = 0;

            while (var4 < var7) {
               short var8 = var17[var4];
               var16[var4 * 2] = (byte)(var8 & 255);
               var16[var4 * 2 + 1] = (byte)(var8 >> 8 & 0xFF);
               int var5 = var6;
               if (Math.abs((int)var8) > var6) {
                  var5 = Math.abs((int)var8);
               }

               var4++;
               var6 = var5;
            }

            var1.level = var6 / 32768.0F;
            var15.write(var16, 0, var7 * 2);
            long var11 = var9 + var7 * 2;

            AutoCloseOutputStream var25;
            try {
               var25 = var1.pipeOut;
            } catch (Exception var23) {
               var1.pipeOut = null;
               Unit var18 = Unit.INSTANCE;
               var9 = var11;
               continue;
            }

            var9 = var11;
            if (var25 != null) {
               try {
                  var25.write(var16, 0, var7 * 2);
                  Unit var27 = Unit.INSTANCE;
               } catch (Exception var22) {
                  var1.pipeOut = null;
                  Unit var26 = Unit.INSTANCE;
                  var9 = var11;
                  continue;
               }

               var9 = var11;
            }
         }
      }

      var15.close();
      var1.wavHeader(var0, var9, var3);

      try {
         var2.stop();
      } catch (Exception var20) {
      }

      var2.release();

      label47: {
         try {
            var24 = var1.pipeOut;
         } catch (Exception var21) {
            break label47;
         }

         if (var24 != null) {
            try {
               var24.close();
            } catch (Exception var19) {
            }
         }
      }

      var1.pipeOut = null;
      var1.main.post(new VoiceNote$$ExternalSyntheticLambda3(var1));
   }

   private static final void start$lambda$2$lambda$1(VoiceNote var0) {
      if (var0.recognizer == null) {
         var0.deliver("");
      } else {
         var0.main.postDelayed(new VoiceNote$$ExternalSyntheticLambda0(var0), 6000L);
      }
   }

   private static final void start$lambda$2$lambda$1$lambda$0(VoiceNote var0) {
      var0.deliver(var0.partial);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final void wavHeader(File var1, long var2, int var4) {
      var var13 = new RandomAccessFile(var1, "rw");

      try {
         RandomAccessFile var14 = var13;
         var14.seek(0L);
         byte[] var6 = "RIFF".getBytes(Charsets.UTF_8);
         Intrinsics.checkNotNullExpressionValue(var6, "getBytes(...)");
         var14.write(var6);
         var14.write(wavHeader$lambda$5$le32(36 + var2));
         var6 = "WAVEfmt ".getBytes(Charsets.UTF_8);
         Intrinsics.checkNotNullExpressionValue(var6, "getBytes(...)");
         var14.write(var6);
         var14.write(wavHeader$lambda$5$le32(16L));
         var14.write(wavHeader$lambda$5$le16(1));
         var14.write(wavHeader$lambda$5$le16(1));
         var14.write(wavHeader$lambda$5$le32(var4));
         var14.write(wavHeader$lambda$5$le32(var4 * 2L));
         var14.write(wavHeader$lambda$5$le16(2));
         var14.write(wavHeader$lambda$5$le16(16));
         var6 = "data".getBytes(Charsets.UTF_8);
         Intrinsics.checkNotNullExpressionValue(var6, "getBytes(...)");
         var14.write(var6);
         var14.write(wavHeader$lambda$5$le32(var2));
         Unit var15 = Unit.INSTANCE;
      } catch (Throwable var12) {
         Throwable var5 = var12;

         try {
            throw var5;
         } finally {
            CloseableKt.closeFinally(var13, var12);
         }
      }

      CloseableKt.closeFinally(var13, null);
   }

   private static final byte[] wavHeader$lambda$5$le16(int var0) {
      return new byte[]{(byte)(var0 & 0xFF), (byte)(var0 >> 8 & 0xFF)};
   }

   private static final byte[] wavHeader$lambda$5$le32(long var0) {
      return new byte[]{(byte)(var0 & 255L), (byte)(var0 >> 8 & 255L), (byte)(var0 >> 16 & 255L), (byte)(255L & var0 >> 24)};
   }

   public final float getLevel() {
      return this.level;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final String start(File var1, Function1<? super String, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "out");
      Intrinsics.checkNotNullParameter(var2, "onText");
      if (this.ctx.checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
         return "no microphone permission";
      } else {
         int var3 = AudioRecord.getMinBufferSize(16000, 16, 2);

         AudioRecord var4;
         try {
            var4 = new AudioRecord(6, 16000, 16, 2, Math.max(var3, 16000));
         } catch (Exception var8) {
            String var14 = var8.getMessage();
            return "microphone: " + var14;
         }

         if (var4.getState() != 1) {
            var4.release();
            return "microphone busy";
         } else {
            this.onText = var2;
            this.file = var1;
            this.partial = "";
            label83:
            if (VERSION.SDK_INT >= 33) {
               label81: {
                  label90: {
                     label91: {
                        try {
                           if (SpeechRecognizer.isOnDeviceRecognitionAvailable(this.ctx)) {
                              var17 = SpeechRecognizer.createOnDeviceSpeechRecognizer(this.ctx);
                              break label91;
                           }
                        } catch (Exception var12) {
                           var16 = this.recognizer;
                           if (var16 == null) {
                              break label81;
                           }
                           break label90;
                        }

                        try {
                           if (SpeechRecognizer.isRecognitionAvailable(this.ctx)) {
                              var17 = SpeechRecognizer.createSpeechRecognizer(this.ctx);
                              break label91;
                           }
                        } catch (Exception var13) {
                           var16 = this.recognizer;
                           if (var16 == null) {
                              break label81;
                           }
                           break label90;
                        }

                        var17 = null;
                     }

                     if (var17 == null) {
                        break label83;
                     }

                     Intent var19;
                     try {
                        ParcelFileDescriptor[] var5 = ParcelFileDescriptor.createPipe();
                        AutoCloseOutputStream var6 = new AutoCloseOutputStream(var5[1]);
                        this.pipeOut = var6;
                        RecognitionListener var20 = new RecognitionListener() {

                           public void onBeginningOfSpeech() {
                           }

                           public void onBufferReceived(byte[] var1) {
                           }

                           public void onEndOfSpeech() {
                           }

                           public void onError(int var1) {
                              VoiceNote.this.deliver(VoiceNote.this.partial);
                           }

                           public void onEvent(int var1, Bundle var2x) {
                           }

                           public void onPartialResults(Bundle var1) {
                              if (var1 != null) {
                                 ArrayList var3x = var1.getStringArrayList("results_recognition");
                                 if (var3x != null) {
                                    String var4x = CollectionsKt.firstOrNull(var3x);
                                    if (var4x != null) {
                                       VoiceNote var2x = VoiceNote.this;
                                       if (!StringsKt.isBlank(var4x)) {
                                          var2x.partial = var4x;
                                       }
                                    }
                                 }
                              }
                           }

                           public void onReadyForSpeech(Bundle var1) {
                           }

                           public void onResults(Bundle var1) {
                              label13: {
                                 if (var1 != null) {
                                    ArrayList var3x = var1.getStringArrayList("results_recognition");
                                    if (var3x != null) {
                                       String var2x = CollectionsKt.firstOrNull(var3x);
                                       var4 = var2x;
                                       if (var2x != null) {
                                          break label13;
                                       }
                                    }
                                 }

                                 var4 = VoiceNote.this.partial;
                              }

                              VoiceNote.this.deliver(var4);
                           }

                           public void onRmsChanged(float var1) {
                           }
                        };
                        var17.setRecognitionListener(var20);
                        Intent var21 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                        var19 = var21.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form")
                           .putExtra("android.speech.extra.PARTIAL_RESULTS", true)
                           .putExtra("android.speech.extra.AUDIO_SOURCE", (Parcelable)var5[0])
                           .putExtra("android.speech.extra.AUDIO_SOURCE_CHANNEL_COUNT", 1)
                           .putExtra("android.speech.extra.AUDIO_SOURCE_ENCODING", 2)
                           .putExtra("android.speech.extra.AUDIO_SOURCE_SAMPLING_RATE", 16000)
                           .putExtra("android.speech.extras.SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS", 60000L)
                           .putExtra("android.speech.extras.SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS", 60000L);
                        Intrinsics.checkNotNullExpressionValue(var19, "putExtra(...)");
                     } catch (Exception var11) {
                        var16 = this.recognizer;
                        if (var16 == null) {
                           break label81;
                        }
                        break label90;
                     }

                     try {
                        var17.startListening(var19);
                        this.recognizer = var17;
                        break label83;
                     } catch (Exception var10) {
                        var16 = this.recognizer;
                        if (var16 == null) {
                           break label81;
                        }
                     }
                  }

                  var16.destroy();
               }

               this.recognizer = null;

               label53: {
                  try {
                     var18 = this.pipeOut;
                  } catch (Exception var9) {
                     break label53;
                  }

                  if (var18 != null) {
                     try {
                        var18.close();
                     } catch (Exception var7) {
                     }
                  }
               }

               this.pipeOut = null;
            }

            this.rec = var4;
            this.running = true;
            var4.startRecording();
            Thread var15 = new Thread(new VoiceNote$$ExternalSyntheticLambda1(var1, this, var4, 16000));
            var15.start();
            this.thread = var15;
            return null;
         }
      }
   }

   public final void stop() {
      this.running = false;
   }
}
