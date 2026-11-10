package com.starpilot.starview;

import android.content.Context;
import android.content.Intent;
import android.media.AudioRecord;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* compiled from: Bookmarks.kt */
public final class VoiceNote {
    private final Context ctx;
    private boolean delivered;
    private File file;
    private volatile float level;
    private final Handler main;
    private Function1<? super String, Unit> onText;
    private String partial;
    private ParcelFileDescriptor.AutoCloseOutputStream pipeOut;
    private AudioRecord rec;
    private SpeechRecognizer recognizer;
    private volatile boolean running;
    private Thread thread;

    public VoiceNote(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
        this.main = new Handler(Looper.getMainLooper());
        this.partial = "";
    }

    public final float getLevel() {
        return this.level;
    }

    public final String start(final File out, Function1<? super String, Unit> onText) {
        Intrinsics.checkNotNullParameter(out, "out");
        Intrinsics.checkNotNullParameter(onText, "onText");
        if (this.ctx.checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
            return "no microphone permission";
        }
        final int rate = 16000;
        int min = AudioRecord.getMinBufferSize(16000, 16, 2);
        try {
            final AudioRecord r = new AudioRecord(6, 16000, 16, 2, Math.max(min, 16000));
            if (r.getState() != 1) {
                r.release();
                return "microphone busy";
            }
            this.onText = onText;
            this.file = out;
            this.partial = "";
            if (Build.VERSION.SDK_INT >= 33) {
                try {
                    boolean onDevice = SpeechRecognizer.isOnDeviceRecognitionAvailable(this.ctx);
                    SpeechRecognizer sr = onDevice ? SpeechRecognizer.createOnDeviceSpeechRecognizer(this.ctx) : SpeechRecognizer.isRecognitionAvailable(this.ctx) ? SpeechRecognizer.createSpeechRecognizer(this.ctx) : null;
                    if (sr != null) {
                        ParcelFileDescriptor[] pipe = ParcelFileDescriptor.createPipe();
                        this.pipeOut = new ParcelFileDescriptor.AutoCloseOutputStream(pipe[1]);
                        sr.setRecognitionListener(new RecognitionListener() { // from class: com.starpilot.starview.VoiceNote$start$1
                            @Override // android.speech.RecognitionListener
                            public void onResults(Bundle b) {
                                String t;
                                ArrayList<String> stringArrayList;
                                if (b == null || (stringArrayList = b.getStringArrayList("results_recognition")) == null || (t = (String) CollectionsKt.firstOrNull((List) stringArrayList)) == null) {
                                    t = VoiceNote.this.partial;
                                }
                                VoiceNote.this.deliver(t);
                            }

                            @Override // android.speech.RecognitionListener
                            public void onPartialResults(Bundle b) {
                                ArrayList<String> stringArrayList;
                                String it;
                                if (b == null || (stringArrayList = b.getStringArrayList("results_recognition")) == null || (it = (String) CollectionsKt.firstOrNull((List) stringArrayList)) == null) {
                                    return;
                                }
                                VoiceNote voiceNote = VoiceNote.this;
                                if (StringsKt.isBlank(it)) {
                                    return;
                                }
                                voiceNote.partial = it;
                            }

                            @Override // android.speech.RecognitionListener
                            public void onError(int error) {
                                String str;
                                VoiceNote voiceNote = VoiceNote.this;
                                str = VoiceNote.this.partial;
                                voiceNote.deliver(str);
                            }

                            @Override // android.speech.RecognitionListener
                            public void onReadyForSpeech(Bundle p) {
                            }

                            @Override // android.speech.RecognitionListener
                            public void onBeginningOfSpeech() {
                            }

                            @Override // android.speech.RecognitionListener
                            public void onRmsChanged(float v) {
                            }

                            @Override // android.speech.RecognitionListener
                            public void onBufferReceived(byte[] b) {
                            }

                            @Override // android.speech.RecognitionListener
                            public void onEndOfSpeech() {
                            }

                            @Override // android.speech.RecognitionListener
                            public void onEvent(int t, Bundle p) {
                            }
                        });
                        Intent i = new Intent("android.speech.action.RECOGNIZE_SPEECH").putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form").putExtra("android.speech.extra.PARTIAL_RESULTS", true).putExtra("android.speech.extra.AUDIO_SOURCE", pipe[0]).putExtra("android.speech.extra.AUDIO_SOURCE_CHANNEL_COUNT", 1).putExtra("android.speech.extra.AUDIO_SOURCE_ENCODING", 2).putExtra("android.speech.extra.AUDIO_SOURCE_SAMPLING_RATE", 16000).putExtra("android.speech.extras.SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS", 60000L).putExtra("android.speech.extras.SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS", 60000L);
                        Intrinsics.checkNotNullExpressionValue(i, "putExtra(...)");
                        sr.startListening(i);
                        this.recognizer = sr;
                    }
                } catch (Exception e) {
                    SpeechRecognizer speechRecognizer = this.recognizer;
                    if (speechRecognizer != null) {
                        speechRecognizer.destroy();
                    }
                    this.recognizer = null;
                    try {
                        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = this.pipeOut;
                        if (autoCloseOutputStream != null) {
                            autoCloseOutputStream.close();
                        }
                    } catch (Exception e2) {
                    }
                    this.pipeOut = null;
                }
            }
            this.rec = r;
            this.running = true;
            r.startRecording();
            Thread $this$start_u24lambda_u243 = new Thread(new Runnable() { // from class: com.starpilot.starview.VoiceNote$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    VoiceNote.start$lambda$2(out, this, r, rate);
                }
            });
            $this$start_u24lambda_u243.start();
            this.thread = $this$start_u24lambda_u243;
            return null;
        } catch (Exception e3) {
            return "microphone: " + e3.getMessage();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void start$lambda$2(File $out, final VoiceNote this$0, AudioRecord $r, int $rate) {
        FileOutputStream fo = new FileOutputStream($out);
        fo.write(new byte[44]);
        short[] buf = new short[1600];
        byte[] bytes = new byte[3200];
        long total = 0;
        long started = System.currentTimeMillis();
        while (this$0.running && System.currentTimeMillis() - started < 60000) {
            int n = $r.read(buf, 0, buf.length);
            if (n > 0) {
                int peak = 0;
                for (int peak2 = 0; peak2 < n; peak2++) {
                    short s = buf[peak2];
                    bytes[peak2 * 2] = (byte) (s & 255);
                    bytes[(peak2 * 2) + 1] = (byte) ((s >> 8) & 255);
                    if (Math.abs((int) s) > peak) {
                        peak = Math.abs((int) s);
                    }
                }
                this$0.level = peak / 32768.0f;
                fo.write(bytes, 0, n * 2);
                total += n * 2;
                try {
                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = this$0.pipeOut;
                    if (autoCloseOutputStream != null) {
                        autoCloseOutputStream.write(bytes, 0, n * 2);
                        Unit unit = Unit.INSTANCE;
                    }
                } catch (Exception e) {
                    this$0.pipeOut = null;
                    Unit unit2 = Unit.INSTANCE;
                }
            }
        }
        fo.close();
        this$0.wavHeader($out, total, $rate);
        try {
            $r.stop();
        } catch (Exception e2) {
        }
        $r.release();
        try {
            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = this$0.pipeOut;
            if (autoCloseOutputStream2 != null) {
                autoCloseOutputStream2.close();
            }
        } catch (Exception e3) {
        }
        this$0.pipeOut = null;
        this$0.main.post(new Runnable() { // from class: com.starpilot.starview.VoiceNote$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                VoiceNote.start$lambda$2$lambda$1(this$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void start$lambda$2$lambda$1(final VoiceNote this$0) {
        if (this$0.recognizer == null) {
            this$0.deliver("");
        } else {
            this$0.main.postDelayed(new Runnable() { // from class: com.starpilot.starview.VoiceNote$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VoiceNote.start$lambda$2$lambda$1$lambda$0(this$0);
                }
            }, 6000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void start$lambda$2$lambda$1$lambda$0(VoiceNote this$0) {
        this$0.deliver(this$0.partial);
    }

    public final void stop() {
        this.running = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deliver(final String t) {
        if (this.delivered) {
            return;
        }
        this.delivered = true;
        this.main.post(new Runnable() { // from class: com.starpilot.starview.VoiceNote$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                VoiceNote.deliver$lambda$4(VoiceNote.this, t);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deliver$lambda$4(VoiceNote this$0, String $t) {
        SpeechRecognizer speechRecognizer = this$0.recognizer;
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        this$0.recognizer = null;
        Function1<? super String, Unit> function1 = this$0.onText;
        if (function1 != null) {
            function1.invoke(StringsKt.trim((CharSequence) $t).toString());
        }
    }

    private final void wavHeader(File f, long dataBytes, int rate) {
        RandomAccessFile randomAccessFile = new RandomAccessFile(f, "rw");
        try {
            RandomAccessFile r = randomAccessFile;
            r.seek(0L);
            byte[] bytes = "RIFF".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            r.write(bytes);
            r.write(wavHeader$lambda$5$le32(36 + dataBytes));
            byte[] bytes2 = "WAVEfmt ".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
            r.write(bytes2);
            r.write(wavHeader$lambda$5$le32(16L));
            r.write(wavHeader$lambda$5$le16(1));
            r.write(wavHeader$lambda$5$le16(1));
            r.write(wavHeader$lambda$5$le32(rate));
            r.write(wavHeader$lambda$5$le32(rate * 2));
            r.write(wavHeader$lambda$5$le16(2));
            r.write(wavHeader$lambda$5$le16(16));
            byte[] bytes3 = "data".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes3, "getBytes(...)");
            r.write(bytes3);
            r.write(wavHeader$lambda$5$le32(dataBytes));
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(randomAccessFile, null);
        } finally {
        }
    }

    private static final byte[] wavHeader$lambda$5$le32(long v) {
        return new byte[]{(byte) (v & 255), (byte) ((v >> 8) & 255), (byte) ((v >> 16) & 255), (byte) (255 & (v >> 24))};
    }

    private static final byte[] wavHeader$lambda$5$le16(int v) {
        return new byte[]{(byte) (v & 255), (byte) ((v >> 8) & 255)};
    }
}
