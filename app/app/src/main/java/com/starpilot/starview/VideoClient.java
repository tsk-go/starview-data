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
        this.onStatus = (Function1) var1;
        this.onSize = (Function2) var2;
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

        public /* synthetic */ Frame(long j, long j2, long j3, boolean z, int i, int i2, byte[] bArr, long j4, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, j2, j3, z, i, i2, bArr, j4, (i3 & 256) != 0 ? false : z2);
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
        return (Function1) this.onImage;
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
    public final void decodeLoop() {
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        MediaCodec c = null;
        try {
            while (this.running) {
                Frame f = this.queue.poll(200L, TimeUnit.MILLISECONDS);
                if (f == null) {
                    if (c != null) {
                        drain(c, info);
                    }
                    continue;
                }
                if (c != null && f.getKey() && f.getW() > 0 && f.getH() > 0 && (f.getW() != this.cfgW || f.getH() != this.cfgH || f.getHevc() != this.cfgHevc)) {
                    try {
                        c.stop();
                        c.release();
                    } catch (Exception e) {
                    }
                    c = null;
                    this.onStatus.invoke("video: new stream " + f.getW() + "x" + f.getH());
                }
                if (c == null) {
                    if (!f.getKey()) {
                        continue;
                    }
                    String mime = f.getHevc() ? "video/hevc" : "video/avc";
                    List csd;
                    if (f.getHevc()) {
                        Iterable $this$map$iv = CollectionsKt.listOf(new Integer[]{32, 33, 34});
                        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        for (Object item$iv$iv : $this$map$iv) {
                            byte[] nal = findNal(f.getData(), ((Number) item$iv$iv).intValue(), true);
                            if (nal == null) {
                                nal = new byte[0];
                            }
                            destination$iv$iv.add(nal);
                        }
                        List l = (List) destination$iv$iv;
                        boolean all = true;
                        for (Object o : l) {
                            if (((byte[]) o).length == 0) {
                                all = false;
                                break;
                            }
                        }
                        if (!all) {
                            continue;
                        }
                        Iterator iterator$iv = l.iterator();
                        if (!iterator$iv.hasNext()) {
                            throw new UnsupportedOperationException("Empty collection can't be reduced.");
                        }
                        Object accumulator$iv = iterator$iv.next();
                        while (iterator$iv.hasNext()) {
                            accumulator$iv = ArraysKt.plus((byte[]) accumulator$iv, (byte[]) iterator$iv.next());
                        }
                        byte[] joined = (byte[]) accumulator$iv;
                        if (joined == null) {
                            continue;
                        }
                        csd = CollectionsKt.listOf(joined);
                    } else {
                        byte[] sps = findNal$default(this, f.getData(), 7, false, 4, null);
                        if (sps == null) {
                            continue;
                        }
                        byte[] pps = findNal$default(this, f.getData(), 8, false, 4, null);
                        if (pps == null) {
                            continue;
                        }
                        csd = CollectionsKt.listOf(new byte[][]{sps, pps});
                    }
                    MediaCodec nc = MediaCodec.createDecoderByType(mime);
                    MediaFormat fmt = MediaFormat.createVideoFormat(mime, f.getW(), f.getH());
                    Intrinsics.checkNotNullExpressionValue(fmt, "createVideoFormat(...)");
                    int k = 0;
                    for (Object item$iv : csd) {
                        int index$iv = k + 1;
                        if (k < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        fmt.setByteBuffer("csd-" + k, ByteBuffer.wrap((byte[]) item$iv));
                        k = index$iv;
                    }
                    if (Build.VERSION.SDK_INT >= 30) {
                        fmt.setInteger("low-latency", 1);
                    }
                    fmt.setInteger("max-input-size", 2097152);
                    nc.configure(fmt, this.surface, (MediaCrypto) null, 0);
                    nc.start();
                    c = nc;
                    this.cfgW = f.getW();
                    this.cfgH = f.getH();
                    this.cfgHevc = f.getHevc();
                    this.lastW = f.getW();
                    this.lastH = f.getH();
                    this.onSize.invoke(Integer.valueOf(f.getW()), Integer.valueOf(f.getH()));
                    this.onStatus.invoke("video: decoder " + nc.getName() + " " + f.getW() + "x" + f.getH());
                    this.needKey = false;
                }
                if (this.needKey && !f.getKey()) {
                    continue;
                }
                this.needKey = false;
                MediaCodec c2 = c;
                int idx = c2.dequeueInputBuffer(50000L);
                if (idx < 0) {
                    this.dropped++;
                    continue;
                }
                ByteBuffer ib = c2.getInputBuffer(idx);
                if (ib == null) {
                    continue;
                }
                ib.clear();
                ib.put(f.getData());
                c2.queueInputBuffer(idx, 0, f.getData().length, 50000 * f.getFrameId(), f.getKey() ? 1 : 0);
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
        } catch (Exception e2) {
            Log.e("StarView/Video", "decoder: " + e2);
            this.onStatus.invoke("video: decoder error: " + e2.getMessage());
        } finally {
            try {
                if (c != null) {
                    c.stop();
                }
                if (c != null) {
                    c.release();
                }
            } catch (Exception e3) {
            }
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
