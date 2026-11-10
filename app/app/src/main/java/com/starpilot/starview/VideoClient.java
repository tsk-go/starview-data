package com.starpilot.starview;

import android.media.Image;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import com.starpilot.starview.VideoClient;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import okhttp3.OkHttpClient.Builder;

/* compiled from: VideoClient.kt */
public final class VideoClient {
    public static final Companion Companion = new Companion(null);
    public static final int HDR = 28;
    private static final String TAG = "StarView/Video";
    private int cfgH;
    private boolean cfgHevc;
    private int cfgW;
    private volatile double decodeMs;
    private Thread decoderThread;
    private volatile long dropped;
    private volatile double fps;
    private long fpsT0;
    private int fpsWin;
    private volatile long frames;
    private final OkHttpClient http;
    private volatile int lastH;
    private volatile int lastW;
    private volatile boolean needKey;
    private volatile Function1<? super Image, Unit> onImage;
    private final Function2<Integer, Integer, Unit> onSize;
    private final Function1<String, Unit> onStatus;
    private final LinkedBlockingQueue<Frame> queue;
    private volatile boolean running;
    private volatile Surface surface;
    private WebSocket ws;

    /* compiled from: VideoClient.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* VF: from Vineflower (jadx: ) */
    public VideoClient(Function1<? super String, Unit> var1, Function2<? super Integer, ? super Integer, Unit> var2) {
        super();
        Intrinsics.checkNotNullParameter(var1, "onStatus");
        Intrinsics.checkNotNullParameter(var2, "onSize");
        this.onStatus = var1;
        this.onSize = var2;
        this.http = new Builder().pingInterval(5L, TimeUnit.SECONDS).readTimeout(0L, TimeUnit.MILLISECONDS).build();
        this.queue = new LinkedBlockingQueue<>(6);
        this.fpsT0 = System.nanoTime();
        this.needKey = true;
    }

    /* compiled from: VideoClient.kt */
    public static final class Frame {
        private final byte[] data;
        private final long frameId;
        private final int h;
        private final boolean hevc;
        private final boolean key;
        private final long rxNs;
        private final long tEofNs;
        private final long unixNs;
        private final int w;

        public Frame(long frameId, long tEofNs, long unixNs, boolean key, int w, int h, byte[] data, long rxNs, boolean hevc) {
            Intrinsics.checkNotNullParameter(data, "data");
            this.frameId = frameId;
            this.tEofNs = tEofNs;
            this.unixNs = unixNs;
            this.key = key;
            this.w = w;
            this.h = h;
            this.data = data;
            this.rxNs = rxNs;
            this.hevc = hevc;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public /* synthetic */ Frame(long j, long j2, long j3, boolean z, int i, int i2, byte[] bArr, long j4, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, j2, j3, z, i, i2, bArr, j4, r14);
            boolean z3;
            if ((i3 & 256) == 0) {
                z3 = z2;
            } else {
                z3 = false;
            }
        }

        public final byte[] getData() {
            return this.data;
        }

        public final long getFrameId() {
            return this.frameId;
        }

        public final int getH() {
            return this.h;
        }

        public final boolean getKey() {
            return this.key;
        }

        public final long getRxNs() {
            return this.rxNs;
        }

        public final long getTEofNs() {
            return this.tEofNs;
        }

        public final long getUnixNs() {
            return this.unixNs;
        }

        public final int getW() {
            return this.w;
        }

        public final boolean getHevc() {
            return this.hevc;
        }
    }

    public final Surface getSurface() {
        return this.surface;
    }

    public final void setSurface(Surface surface) {
        this.surface = surface;
    }

    public final Function1<Image, Unit> getOnImage() {
        return this.onImage;
    }

    public final void setOnImage(Function1<? super Image, Unit> function1) {
        this.onImage = function1;
    }

    /* recovered: the original JVM name (callers taken from Vineflower use it) */

    public final boolean isRunning() {

        return getRunning();

    }


    /* renamed from: isRunning, reason: from getter */
    public final boolean getRunning() {
        return this.running;
    }

    public final double getDecodeMs() {
        return this.decodeMs;
    }

    public final long getDropped() {
        return this.dropped;
    }

    public final double getFps() {
        return this.fps;
    }

    public final long getFrames() {
        return this.frames;
    }

    public final void setDecodeMs(double d) {
        this.decodeMs = d;
    }

    public final void setDropped(long j) {
        this.dropped = j;
    }

    public final void setFps(double d) {
        this.fps = d;
    }

    public final void setFrames(long j) {
        this.frames = j;
    }

    public final int getLastH() {
        return this.lastH;
    }

    public final int getLastW() {
        return this.lastW;
    }

    public final void setLastH(int i) {
        this.lastH = i;
    }

    public final void setLastW(int i) {
        this.lastW = i;
    }

    public final void connect(String host, int port, final String cam, Surface surface) {
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(cam, "cam");
        close();
        this.surface = surface;
        this.running = true;
        Thread it = new Thread(new Runnable() { // from class: com.starpilot.starview.VideoClient$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VideoClient.this.decodeLoop();
            }
        }, "starview-decoder");
        it.start();
        this.decoderThread = it;
        Request req = Link.INSTANCE.withSession(new Request.Builder().url("ws://" + host + ":" + port + "/video/" + cam + (VideoClientKt.getHevcDecoder() ? "?hevc=1" : "")), host, port).build();
        this.ws = this.http.newWebSocket(req, new WebSocketListener() { // from class: com.starpilot.starview.VideoClient$connect$3
            @Override // okhttp3.WebSocketListener
            public void onOpen(WebSocket webSocket, Response response) {
                Function1 function1;
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(response, "response");
                function1 = VideoClient.this.onStatus;
                function1.invoke("video[" + cam + "]: connected, waiting for keyframe");
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                LinkedBlockingQueue linkedBlockingQueue;
                LinkedBlockingQueue linkedBlockingQueue2;
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(bytes, "bytes");
                ByteBuffer bb = bytes.asByteBuffer().order(ByteOrder.LITTLE_ENDIAN);
                if (bb.remaining() < 28) {
                    return;
                }
                long frameId = bb.getInt() & 4294967295L;
                long tEof = bb.getLong();
                long unixNs = bb.getLong();
                int flags = bb.getInt();
                int w = bb.getShort() & 65535;
                int h = bb.getShort() & 65535;
                byte[] it2 = new byte[bb.remaining()];
                bb.get(it2);
                VideoClient.Frame f = new VideoClient.Frame(frameId, tEof, unixNs, (flags & 1) != 0, w, h, it2, System.nanoTime(), (flags & 2) != 0);
                linkedBlockingQueue = VideoClient.this.queue;
                if (!linkedBlockingQueue.offer(f)) {
                    linkedBlockingQueue2 = VideoClient.this.queue;
                    linkedBlockingQueue2.clear();
                    VideoClient.this.setDropped(VideoClient.this.getDropped() + 1);
                    VideoClient.this.needKey = true;
                    webSocket.send("keyframe");
                }
            }

            @Override // okhttp3.WebSocketListener
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Function1 function1;
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(reason, "reason");
                function1 = VideoClient.this.onStatus;
                function1.invoke("video: closed");
            }

            @Override // okhttp3.WebSocketListener
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Function1 function1;
                Intrinsics.checkNotNullParameter(webSocket, "webSocket");
                Intrinsics.checkNotNullParameter(t, "t");
                function1 = VideoClient.this.onStatus;
                function1.invoke("video: " + t.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01d9 A[Catch: all -> 0x02a2, Exception -> 0x02a7, TryCatch #8 {Exception -> 0x02a7, all -> 0x02a2, blocks: (B:30:0x008f, B:32:0x0095, B:35:0x00a0, B:37:0x00a7, B:38:0x00dd, B:40:0x00e3, B:42:0x00ff, B:44:0x0103, B:47:0x0109, B:49:0x0118, B:55:0x0147, B:57:0x0155, B:58:0x0159, B:60:0x015f, B:62:0x016f, B:70:0x0175, B:71:0x01b2, B:72:0x01d3, B:74:0x01d9, B:76:0x01e1, B:78:0x01e4, B:81:0x020f, B:83:0x0219, B:84:0x021f, B:116:0x017a, B:117:0x0181, B:121:0x0123, B:122:0x0127, B:124:0x012d, B:136:0x0184, B:138:0x0196, B:144:0x01a8), top: B:29:0x008f }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0219 A[Catch: all -> 0x02a2, Exception -> 0x02a7, TryCatch #8 {Exception -> 0x02a7, all -> 0x02a2, blocks: (B:30:0x008f, B:32:0x0095, B:35:0x00a0, B:37:0x00a7, B:38:0x00dd, B:40:0x00e3, B:42:0x00ff, B:44:0x0103, B:47:0x0109, B:49:0x0118, B:55:0x0147, B:57:0x0155, B:58:0x0159, B:60:0x015f, B:62:0x016f, B:70:0x0175, B:71:0x01b2, B:72:0x01d3, B:74:0x01d9, B:76:0x01e1, B:78:0x01e4, B:81:0x020f, B:83:0x0219, B:84:0x021f, B:116:0x017a, B:117:0x0181, B:121:0x0123, B:122:0x0127, B:124:0x012d, B:136:0x0184, B:138:0x0196, B:144:0x01a8), top: B:29:0x008f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void decodeLoop() {
        Throwable th;
        MediaCodec mediaCodec;
        byte[] findNal$default;
        List csd;
        boolean z;
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        MediaCodec c = null;
        while (this.running) {
            try {
                try {
                    Frame poll = this.queue.poll(200L, TimeUnit.MILLISECONDS);
                    if (poll == null) {
                        VideoClient $this$decodeLoop_u24lambda_u243 = this;
                        if (c != null) {
                            MediaCodec it = c;
                            $this$decodeLoop_u24lambda_u243.drain(it, info);
                        }
                    } else {
                        if (c == null || !poll.getKey() || poll.getW() <= 0 || poll.getH() <= 0 || (poll.getW() == this.cfgW && poll.getH() == this.cfgH && poll.getHevc() == this.cfgHevc)) {
                            mediaCodec = c;
                        } else {
                            try {
                                c.stop();
                                c.release();
                            } catch (Exception e) {
                            }
                            this.onStatus.invoke("video: new stream " + poll.getW() + "x" + poll.getH());
                            mediaCodec = null;
                        }
                        boolean z2 = true;
                        if (mediaCodec == null) {
                            try {
                                if (poll.getKey()) {
                                    String mime = poll.getHevc() ? "video/hevc" : "video/avc";
                                    if (poll.getHevc()) {
                                        Iterable $this$map$iv = CollectionsKt.listOf(new Integer[]{32, 33, 34});
                                        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                                        for (Object item$iv$iv : $this$map$iv) {
                                            int it2 = ((Number) item$iv$iv).intValue();
                                            byte[] findNal = findNal(poll.getData(), it2, z2);
                                            if (findNal == null) {
                                                findNal = new byte[0];
                                            }
                                            destination$iv$iv.add(findNal);
                                            z2 = true;
                                        }
                                        Iterable l = (List) destination$iv$iv;
                                        Iterable $this$all$iv = l;
                                        if (!($this$all$iv instanceof Collection) || !((Collection) $this$all$iv).isEmpty()) {
                                            Iterator it3 = $this$all$iv.iterator();
                                            while (true) {
                                                if (!it3.hasNext()) {
                                                    z = true;
                                                    break;
                                                }
                                                Object element$iv = it3.next();
                                                byte[] it4 = (byte[]) element$iv;
                                                if (it4.length == 0) {
                                                    z = false;
                                                    break;
                                                }
                                            }
                                        } else {
                                            z = true;
                                        }
                                        if (!z) {
                                            l = null;
                                        }
                                        if (l != null) {
                                            Iterable $this$reduce$iv = l;
                                            Iterator iterator$iv = $this$reduce$iv.iterator();
                                            if (!iterator$iv.hasNext()) {
                                                throw new UnsupportedOperationException("Empty collection can't be reduced.");
                                            }
                                            Object accumulator$iv = iterator$iv.next();
                                            while (iterator$iv.hasNext()) {
                                                byte[] b = (byte[]) iterator$iv.next();
                                                byte[] a = (byte[]) accumulator$iv;
                                                accumulator$iv = ArraysKt.plus(a, b);
                                            }
                                            byte[] bArr = (byte[]) accumulator$iv;
                                            if (bArr != null) {
                                                csd = CollectionsKt.listOf(bArr);
                                                c = MediaCodec.createDecoderByType(mime);
                                                int i = 0;
                                                MediaFormat fmt = MediaFormat.createVideoFormat(mime, poll.getW(), poll.getH());
                                                Intrinsics.checkNotNullExpressionValue(fmt, "createVideoFormat(...)");
                                                List $this$forEachIndexed$iv = csd;
                                                int k = 0;
                                                for (Object item$iv : $this$forEachIndexed$iv) {
                                                    int index$iv = k + 1;
                                                    if (k < 0) {
                                                        CollectionsKt.throwIndexOverflow();
                                                    }
                                                    byte[] b2 = (byte[]) item$iv;
                                                    fmt.setByteBuffer("csd-" + k, ByteBuffer.wrap(b2));
                                                    k = index$iv;
                                                    csd = csd;
                                                    i = i;
                                                }
                                                if (Build.VERSION.SDK_INT >= 30) {
                                                    fmt.setInteger("low-latency", 1);
                                                }
                                                fmt.setInteger("max-input-size", 2097152);
                                                c.configure(fmt, this.surface, (MediaCrypto) null, 0);
                                                c.start();
                                                this.cfgW = poll.getW();
                                                this.cfgH = poll.getH();
                                                this.cfgHevc = poll.getHevc();
                                                this.lastW = poll.getW();
                                                this.lastH = poll.getH();
                                                this.onSize.invoke(Integer.valueOf(poll.getW()), Integer.valueOf(poll.getH()));
                                                this.onStatus.invoke("video: decoder " + c.getName() + " " + poll.getW() + "x" + poll.getH());
                                                this.needKey = false;
                                            }
                                        }
                                    } else {
                                        byte[] findNal$default2 = findNal$default(this, poll.getData(), 7, false, 4, null);
                                        if (findNal$default2 != null && (findNal$default = findNal$default(this, poll.getData(), 8, false, 4, null)) != null) {
                                            csd = CollectionsKt.listOf(new byte[][]{findNal$default2, findNal$default});
                                            c = MediaCodec.createDecoderByType(mime);
                                            int i2 = 0;
                                            MediaFormat fmt2 = MediaFormat.createVideoFormat(mime, poll.getW(), poll.getH());
                                            Intrinsics.checkNotNullExpressionValue(fmt2, "createVideoFormat(...)");
                                            List $this$forEachIndexed$iv2 = csd;
                                            int k2 = 0;
                                            while (r14.hasNext()) {
                                            }
                                            if (Build.VERSION.SDK_INT >= 30) {
                                            }
                                            fmt2.setInteger("max-input-size", 2097152);
                                            c.configure(fmt2, this.surface, (MediaCrypto) null, 0);
                                            c.start();
                                            this.cfgW = poll.getW();
                                            this.cfgH = poll.getH();
                                            this.cfgHevc = poll.getHevc();
                                            this.lastW = poll.getW();
                                            this.lastH = poll.getH();
                                            this.onSize.invoke(Integer.valueOf(poll.getW()), Integer.valueOf(poll.getH()));
                                            this.onStatus.invoke("video: decoder " + c.getName() + " " + poll.getW() + "x" + poll.getH());
                                            this.needKey = false;
                                        }
                                    }
                                }
                                c = mediaCodec;
                            } catch (Exception e2) {
                                e = e2;
                                c = mediaCodec;
                                Log.e("StarView/Video", "decoder: " + e);
                                this.onStatus.invoke("video: decoder error: " + e.getMessage());
                                try {
                                    MediaCodec mediaCodec2 = c;
                                    if (mediaCodec2 != null) {
                                        mediaCodec2.stop();
                                    }
                                    MediaCodec mediaCodec3 = c;
                                    if (mediaCodec3 != null) {
                                        mediaCodec3.release();
                                        return;
                                    }
                                    return;
                                } catch (Exception e3) {
                                    return;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                c = mediaCodec;
                                try {
                                    MediaCodec mediaCodec4 = c;
                                    if (mediaCodec4 != null) {
                                        mediaCodec4.stop();
                                    }
                                    MediaCodec mediaCodec5 = c;
                                    if (mediaCodec5 == null) {
                                        throw th;
                                    }
                                    mediaCodec5.release();
                                    throw th;
                                } catch (Exception e4) {
                                    throw th;
                                }
                            }
                        } else {
                            c = mediaCodec;
                        }
                        if (!this.needKey || poll.getKey()) {
                            this.needKey = false;
                            MediaCodec c2 = c;
                            int idx = c2.dequeueInputBuffer(50000L);
                            if (idx < 0) {
                                this.dropped++;
                            } else {
                                ByteBuffer ib = c2.getInputBuffer(idx);
                                if (ib != null) {
                                    ib.clear();
                                    ib.put(poll.getData());
                                    c2.queueInputBuffer(idx, 0, poll.getData().length, 50000 * poll.getFrameId(), poll.getKey() ? 1 : 0);
                                    long t0 = System.nanoTime();
                                    drain(c2, info);
                                    this.decodeMs = (this.decodeMs * 0.9d) + (((System.nanoTime() - t0) / 1000000.0d) * 0.1d);
                                    this.frames++;
                                    this.fpsWin++;
                                    long now = System.nanoTime();
                                    if (now - this.fpsT0 > 1000000000) {
                                        this.fps = (this.fpsWin * 1.0E9d) / (now - this.fpsT0);
                                        this.fpsWin = 0;
                                        this.fpsT0 = now;
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Exception e5) {
                e = e5;
            }
        }
        if (c != null) {
            try {
                c.stop();
            } catch (Exception e6) {
                return;
            }
        }
        if (c != null) {
            c.release();
        }
    }

    private final void drain(MediaCodec c, MediaCodec.BufferInfo info) {
        Image img;
        while (true) {
            int o = c.dequeueOutputBuffer(info, 0L);
            if (o >= 0) {
                if (this.surface == null) {
                    Function1 cb = this.onImage;
                    if (cb != null) {
                        try {
                            img = c.getOutputImage(o);
                        } catch (Exception e) {
                            img = null;
                        }
                        if (img != null) {
                            try {
                                cb.invoke(img);
                            } finally {
                                img.close();
                            }
                        }
                    }
                    c.releaseOutputBuffer(o, false);
                } else {
                    c.releaseOutputBuffer(o, true);
                }
            } else if (o == -2) {
                MediaFormat f = c.getOutputFormat();
                Intrinsics.checkNotNullExpressionValue(f, "getOutputFormat(...)");
                int w = f.getInteger("width");
                int h = f.getInteger("height");
                if (w > 0 && h > 0) {
                    this.lastW = w;
                    this.lastH = h;
                    this.onSize.invoke(Integer.valueOf(w), Integer.valueOf(h));
                }
            } else {
                return;
            }
        }
    }

    private final byte[] findNal(byte[] d, int type, boolean hevc) {
        return VideoClientKt.nalOf(d, type, hevc);
    }

    static /* synthetic */ byte[] findNal$default(VideoClient videoClient, byte[] bArr, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return videoClient.findNal(bArr, i, z);
    }

    public final void close() {
        this.running = false;
        WebSocket webSocket = this.ws;
        if (webSocket != null) {
            webSocket.close(1000, "bye");
        }
        this.ws = null;
        Thread thread = this.decoderThread;
        if (thread != null) {
            thread.join(500L);
        }
        this.decoderThread = null;
        this.queue.clear();
    }
}
