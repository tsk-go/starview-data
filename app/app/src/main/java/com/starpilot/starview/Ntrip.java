package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.starpilot.starview.Ntrip;
import com.starpilot.starview.NtripCore;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: Ntrip.kt */
public final class Ntrip {
    private static final long GGA_EVERY_MS = 10000;
    private static final long KEEP_MS = 120000;
    private static final String NEARER_BASE = "a nearer base";
    public static final String PREFS = "starview_ntrip";
    private static final long QUIET_HANGUP_MS = 90000;
    private static final String RETRY_MAIN = "time to try the main caster again";
    private static final long SEND_EVERY_MS = 250;
    private static final String TAG = "StarView/Ntrip";
    private static volatile long bytesIn;
    private static boolean commaGoneNoted;
    private static volatile int generation;
    private static volatile long lastDataMs;
    private static volatile String lastGga;
    private static String lastSaid;
    private static volatile File logFile;
    private static long mountsAtMs;
    private static volatile ConnectivityManager.NetworkCallback netCb;
    private static volatile Network netNow;
    private static volatile RtkStatus rtk;
    private static volatile long rtkAtMs;
    private static volatile Socket socket;
    private static volatile Thread thread;
    private static volatile long wantedAtMs;
    public static final Ntrip INSTANCE = new Ntrip();
    private static volatile String source = "";
    private static volatile String state = "off";
    private static final ArrayDeque<String> history = new ArrayDeque<>();
    private static List<NtripCore.Mount> mounts = CollectionsKt.emptyList();
    private static String mountsHost = "";
    private static final HashMap<String, Long> badBases = new HashMap<>();

    private Ntrip() {
    }

    /* compiled from: Ntrip.kt */
    public static final class Config {
        private final String host;
        private final String mount;
        private final boolean on;
        private final String pass;
        private final int port;
        private final String user;

        public Config(boolean on, String host, int port, String mount, String user, String pass) {
            Intrinsics.checkNotNullParameter(host, "host");
            Intrinsics.checkNotNullParameter(mount, "mount");
            Intrinsics.checkNotNullParameter(user, "user");
            Intrinsics.checkNotNullParameter(pass, "pass");
            this.on = on;
            this.host = host;
            this.port = port;
            this.mount = mount;
            this.user = user;
            this.pass = pass;
        }

        public final String getHost() {
            return this.host;
        }

        public final String getMount() {
            return this.mount;
        }

        public final boolean getOn() {
            return this.on;
        }

        public final String getPass() {
            return this.pass;
        }

        public final int getPort() {
            return this.port;
        }

        public final String getUser() {
            return this.user;
        }

        public final boolean getComplete() {
            if (StringsKt.isBlank(this.host)) {
                return false;
            }
            int i = this.port;
            return (1 <= i && i < 65536) && !StringsKt.isBlank(this.mount);
        }
    }

    public final Config config(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        SharedPreferences p = ctx.getSharedPreferences("starview_ntrip", 0);
        boolean z = p.getBoolean("on", false);
        String string = p.getString("host", "");
        String str = string == null ? "" : string;
        int i = p.getInt("port", 2101);
        String string2 = p.getString("mount", "");
        String str2 = string2 == null ? "" : string2;
        String string3 = p.getString("user", "");
        String str3 = string3 == null ? "" : string3;
        String string4 = p.getString("pass", "");
        return new Config(z, str, i, str2, str3, string4 == null ? "" : string4);
    }

    public final Config backup(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        SharedPreferences p = ctx.getSharedPreferences("starview_ntrip", 0);
        boolean z = p.getBoolean("b_on", false);
        String string = p.getString("b_host", "rtk2go.com");
        String str = string == null ? "" : string;
        int i = p.getInt("b_port", 2101);
        String string2 = p.getString("b_mount", "");
        String str2 = string2 == null ? "" : string2;
        String string3 = p.getString("b_user", "");
        String str3 = string3 == null ? "" : string3;
        String string4 = p.getString("b_pass", "none");
        return new Config(z, str, i, str2, str3, string4 == null ? "" : string4);
    }

    public final void saveBackup(Context ctx, Config c) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(c, "c");
        ctx.getSharedPreferences("starview_ntrip", 0).edit().putBoolean("b_on", c.getOn()).putString("b_host", StringsKt.trim((CharSequence) c.getHost()).toString()).putInt("b_port", c.getPort()).putString("b_mount", StringsKt.trim((CharSequence) c.getMount()).toString()).putString("b_user", StringsKt.trim((CharSequence) c.getUser()).toString()).putString("b_pass", c.getPass()).apply();
        restart();
    }

    public final boolean backupOnly(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return ctx.getSharedPreferences("starview_ntrip", 0).getBoolean("b_only", false);
    }

    public final void setBackupOnly(Context ctx, boolean v) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        ctx.getSharedPreferences("starview_ntrip", 0).edit().putBoolean("b_only", v).apply();
        restart();
    }

    public final String getSource() {
        return source;
    }

    public final void save(Context ctx, Config c) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(c, "c");
        ctx.getSharedPreferences("starview_ntrip", 0).edit().putBoolean("on", c.getOn()).putString("host", StringsKt.trim((CharSequence) c.getHost()).toString()).putInt("port", c.getPort()).putString("mount", StringsKt.trim((CharSequence) c.getMount()).toString()).putString("user", StringsKt.trim((CharSequence) c.getUser()).toString()).putString("pass", c.getPass()).apply();
        restart();
    }

    public final RtkStatus getRtk() {
        return rtk;
    }

    public final long getRtkAtMs() {
        return rtkAtMs;
    }

    public final void update(JSONObject o) {
        rtk = RtkStatus.Companion.parse(o);
        rtkAtMs = System.currentTimeMillis();
        RtkPosition.INSTANCE.feed(rtk, rtkAtMs);
    }

    private final void setState(String v) {
        if (!Intrinsics.areEqual(v, state) && !StringsKt.startsWith$default(v, "receiving corrections", false, 2, (Object) null)) {
            note(v);
        }
        state = v;
    }

    public final String getState() {
        return state;
    }

    public final ArrayDeque<String> getHistory() {
        return history;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void note(String text) {
        String line = new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()) + "  " + text;
        synchronized (history) {
            history.addFirst(line);
            while (history.size() > 40) {
                history.removeLast();
            }
            Unit unit = Unit.INSTANCE;
        }
        File f = logFile;
        if (f != null) {
            try {
                FilesKt.appendText$default(f, new SimpleDateFormat("yyyy-MM-dd ", Locale.US).format(new Date()) + line + "\n", null, 2, null);
                if (f.length() > 60000) {
                    FilesKt.writeText$default(f, CollectionsKt.joinToString$default(CollectionsKt.takeLast(FilesKt.readLines$default(f, null, 1, null), 300), "\n", null, null, 0, null, null, 62, null) + "\n", null, 2, null);
                }
            } catch (Exception e) {
            }
        }
    }

    public final List<String> recent(int n) {
        List<String> take;
        synchronized (history) {
            take = CollectionsKt.take(history, n);
        }
        return take;
    }

    public final long getBytesIn() {
        return bytesIn;
    }

    public final long getLastDataMs() {
        return lastDataMs;
    }

    private final void restart() {
        generation++;
        try {
            Socket socket2 = socket;
            if (socket2 != null) {
                socket2.close();
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void reopen() {
        try {
            Socket socket2 = socket;
            if (socket2 != null) {
                socket2.close();
            }
        } catch (Exception e) {
        }
    }

    private final void watchNetwork(Context ctx) {
        ConnectivityManager cm;
        if (netCb == null && (cm = (ConnectivityManager) ctx.getSystemService(ConnectivityManager.class)) != null) {
            ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.starpilot.starview.Ntrip$watchNetwork$cb$1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    Network old;
                    Thread thread2;
                    Intrinsics.checkNotNullParameter(network, "network");
                    old = Ntrip.netNow;
                    Ntrip ntrip = Ntrip.INSTANCE;
                    Ntrip.netNow = network;
                    if (old != null && !Intrinsics.areEqual(old, network)) {
                        thread2 = Ntrip.thread;
                        if (thread2 != null) {
                            Ntrip.INSTANCE.note("internet moved to another network - reconnecting now");
                            Ntrip.INSTANCE.reopen();
                        }
                    }
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network) {
                    Network network2;
                    Network network3;
                    Thread thread2;
                    Intrinsics.checkNotNullParameter(network, "network");
                    network2 = Ntrip.netNow;
                    if (Intrinsics.areEqual(network, network2)) {
                        thread2 = Ntrip.thread;
                        if (thread2 != null) {
                            Ntrip.INSTANCE.note("internet lost - keeping the line open until it's back");
                        }
                    }
                    network3 = Ntrip.netNow;
                    if (Intrinsics.areEqual(network, network3)) {
                        Ntrip ntrip = Ntrip.INSTANCE;
                        Ntrip.netNow = null;
                    }
                }
            };
            try {
                cm.registerDefaultNetworkCallback(networkCallback);
                netCb = networkCallback;
            } catch (Exception e) {
                Log.w("StarView/Ntrip", "network callback: " + e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00bf A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x010d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x012f A[ADDED_TO_REGION, ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void tick(Context ctx) {
        Config it;
        boolean ready;
        boolean want;
        Thread thread2;
        String str;
        RtkStatus rtkStatus;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (logFile == null) {
            logFile = new File(ctx.getFilesDir(), "ntrip_log.txt");
        }
        Context applicationContext = ctx.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        watchNetwork(applicationContext);
        final Config c = config(ctx);
        DataClient dc = Link.INSTANCE.getData();
        boolean onlyWanted = backupOnly(ctx);
        final Config b = backup(ctx);
        boolean z = false;
        if ((b.getOn() || onlyWanted) && !StringsKt.isBlank(b.getHost())) {
            int port = b.getPort();
            if (1 <= port && port < 65536) {
                it = 1;
                if (it == null) {
                    b = null;
                }
                final boolean only = b == null && onlyWanted;
                ready = !c.getComplete() || only;
                if (c.getOn() && ready && dc != null && dc.getConnected()) {
                    rtkStatus = rtk;
                    if (rtkStatus == null && rtkStatus.getConnected()) {
                        want = true;
                        long now = System.currentTimeMillis();
                        if (want) {
                            wantedAtMs = now;
                        }
                        boolean keep = !c.getOn() && ready && thread != null && now - wantedAtMs < 120000;
                        if (want && !keep) {
                            if (thread != null) {
                                if (c.getOn() && ready) {
                                    note("comma or receiver gone for 2 minutes - hanging up on the caster");
                                }
                                restart();
                            }
                            if (!c.getOn()) {
                                str = "off";
                            } else if (!ready) {
                                str = "caster, port and mountpoint needed";
                            } else if (rtk == null) {
                                str = "no RTK receiver on the comma";
                            } else {
                                RtkStatus rtkStatus2 = rtk;
                                if (rtkStatus2 != null && rtkStatus2.getConnected()) {
                                    z = true;
                                }
                                str = !z ? "RTK receiver unplugged" : "waiting for the comma";
                            }
                            setState(str);
                            return;
                        }
                        thread2 = thread;
                        if (thread2 != null && thread2.isAlive()) {
                            z = true;
                        }
                        if (z || !want) {
                            return;
                        }
                        generation++;
                        final int gen = generation;
                        Thread $this$tick_u24lambda_u247 = new Thread(new Runnable() { // from class: com.starpilot.starview.Ntrip$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                Ntrip.tick$lambda$6(Ntrip.Config.this, gen, b, only);
                            }
                        }, "starview-ntrip");
                        $this$tick_u24lambda_u247.setDaemon(true);
                        $this$tick_u24lambda_u247.start();
                        thread = $this$tick_u24lambda_u247;
                        return;
                    }
                }
                want = false;
                long now2 = System.currentTimeMillis();
                if (want) {
                }
                if (c.getOn()) {
                }
                if (want) {
                }
                thread2 = thread;
                if (thread2 != null) {
                    z = true;
                }
                if (z) {
                    return;
                } else {
                    return;
                }
            }
        }
        it = null;
        if (it == null) {
        }
        if (b == null) {
        }
        if (c.getComplete()) {
        }
        if (c.getOn()) {
            rtkStatus = rtk;
            if (rtkStatus == null && rtkStatus.getConnected()) {
            }
        }
        want = false;
        long now22 = System.currentTimeMillis();
        if (want) {
        }
        if (c.getOn()) {
        }
        if (want) {
        }
        thread2 = thread;
        if (thread2 != null) {
        }
        if (z) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tick$lambda$6(Config $c, int $gen, Config $b, boolean $only) {
        INSTANCE.run($c, $gen, $b, $only);
    }

    /* compiled from: Ntrip.kt */
    private static final class Role {
        private final Config backup;
        private volatile NtripCore.Mount base;
        private volatile long lastHelpMs;
        private final NtripCore.BackupPlan plan;

        public Role(Config backup, NtripCore.BackupPlan plan) {
            Intrinsics.checkNotNullParameter(plan, "plan");
            this.backup = backup;
            this.plan = plan;
        }

        public final Config getBackup() {
            return this.backup;
        }

        public final NtripCore.Mount getBase() {
            return this.base;
        }

        public final long getLastHelpMs() {
            return this.lastHelpMs;
        }

        public final NtripCore.BackupPlan getPlan() {
            return this.plan;
        }

        public final void setBase(NtripCore.Mount mount) {
            this.base = mount;
        }

        public final void setLastHelpMs(long j) {
            this.lastHelpMs = j;
        }
    }

    private final Set<String> skipBases(final long now) {
        Set<String> set;
        synchronized (badBases) {
            Set<Map.Entry<String, Long>> entrySet = badBases.entrySet();
            Intrinsics.checkNotNullExpressionValue(entrySet, "<get-entries>(...)");
            CollectionsKt.removeAll(entrySet, new Function1() { // from class: com.starpilot.starview.Ntrip$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    boolean skipBases$lambda$9$lambda$8;
                    skipBases$lambda$9$lambda$8 = Ntrip.skipBases$lambda$9$lambda$8(now, (Map.Entry) obj);
                    return Boolean.valueOf(skipBases$lambda$9$lambda$8);
                }
            });
            Set<String> keySet = badBases.keySet();
            Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
            set = CollectionsKt.toSet(keySet);
        }
        return set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean skipBases$lambda$9$lambda$8(long $now, Map.Entry it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return ((Number) it.getValue()).longValue() < $now;
    }

    private final NtripCore.Mount backupMount(Config b) {
        List<NtripCore.Mount> emptyList;
        NtripCore.Mount nearestBase;
        RtkStatus st = rtk;
        if (st == null || !st.getHasFix()) {
            return null;
        }
        long now = System.currentTimeMillis();
        if (mounts.isEmpty() || now - mountsAtMs > 1800000 || !Intrinsics.areEqual(mountsHost, b.getHost())) {
            mountsAtMs = now;
            mountsHost = b.getHost();
            try {
                emptyList = fetchMounts(b.getHost(), b.getPort(), b.getUser(), b.getPass());
            } catch (Exception e) {
                note("backup caster's list: " + e.getMessage());
                emptyList = CollectionsKt.emptyList();
            }
            mounts = emptyList;
        }
        nearestBase = NtripCore.INSTANCE.nearestBase(mounts, st.getLat(), st.getLon(), (r20 & 8) != 0 ? 25.0d : 0.0d, (r20 & 16) != 0 ? SetsKt.emptySet() : skipBases(now));
        return nearestBase;
    }

    static /* synthetic */ void run$default(Ntrip ntrip, Config config, int i, Config config2, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            config2 = null;
        }
        if ((i2 & 8) != 0) {
            z = false;
        }
        ntrip.run(config, i, config2, z);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r16v0 ??, still in use, count: 2, list:
          (r16v0 ?? I:??[OBJECT, ARRAY]) from 0x002d: MOVE (r9v0 ?? I:??[OBJECT, ARRAY]) = (r16v0 ?? I:??[OBJECT, ARRAY])
          (r16v0 ?? I:com.starpilot.starview.NtripCore$BackupPlan) from 0x002a: INVOKE 
          (r16v0 ?? I:com.starpilot.starview.NtripCore$BackupPlan)
          (r18v0 ?? I:long)
          (r20v0 ?? I:long)
          (r22v0 ?? I:int)
          (r23v0 ?? I:kotlin.jvm.internal.DefaultConstructorMarker)
         DIRECT call: com.starpilot.starview.NtripCore.BackupPlan.<init>(long, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void A[MD:(long, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void (m)]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    private final void run(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r16v0 ??, still in use, count: 2, list:
          (r16v0 ?? I:??[OBJECT, ARRAY]) from 0x002d: MOVE (r9v0 ?? I:??[OBJECT, ARRAY]) = (r16v0 ?? I:??[OBJECT, ARRAY])
          (r16v0 ?? I:com.starpilot.starview.NtripCore$BackupPlan) from 0x002a: INVOKE 
          (r16v0 ?? I:com.starpilot.starview.NtripCore$BackupPlan)
          (r18v0 ?? I:long)
          (r20v0 ?? I:long)
          (r22v0 ?? I:int)
          (r23v0 ?? I:kotlin.jvm.internal.DefaultConstructorMarker)
         DIRECT call: com.starpilot.starview.NtripCore.BackupPlan.<init>(long, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void A[MD:(long, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void (m)]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r41v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:238)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
        */

    private final String readLine(InputStream inp) {
        int ch;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        while (b.size() < 1024 && (ch = inp.read()) >= 0 && ch != 10) {
            if (ch != 13) {
                b.write(ch);
            }
        }
        String byteArrayOutputStream = b.toString("ISO-8859-1");
        Intrinsics.checkNotNullExpressionValue(byteArrayOutputStream, "toString(...)");
        return byteArrayOutputStream;
    }

    private final String readUpTo(InputStream inp, int max) {
        int n;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        byte[] buf = new byte[512];
        while (b.size() < max) {
            try {
                n = inp.read(buf);
            } catch (SocketTimeoutException e) {
                n = -1;
            }
            if (n <= 0) {
                break;
            }
            b.write(buf, 0, n);
        }
        String byteArrayOutputStream = b.toString("ISO-8859-1");
        Intrinsics.checkNotNullExpressionValue(byteArrayOutputStream, "toString(...)");
        return byteArrayOutputStream;
    }

    static /* synthetic */ void stream$default(Ntrip ntrip, Config config, int i, boolean z, Role role, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            role = null;
        }
        ntrip.stream(config, i, z, role);
    }

    /* JADX WARN: Code restructure failed: missing block: B:121:0x03fb, code lost:
    
        r0 = r57.getBackup();
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0051, code lost:
    
        if (r13 == null) goto L11;
     */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0289 A[Catch: all -> 0x0190, TryCatch #2 {all -> 0x0190, blocks: (B:26:0x011a, B:28:0x0129, B:30:0x0132, B:32:0x0140, B:33:0x0146, B:35:0x014f, B:41:0x015e, B:42:0x0180, B:56:0x0215, B:58:0x0219, B:61:0x0230, B:63:0x0234, B:66:0x0242, B:68:0x026b, B:70:0x027c, B:71:0x0296, B:74:0x02a0, B:77:0x02b8, B:83:0x02ce, B:85:0x02e3, B:87:0x02f1, B:89:0x0324, B:91:0x032c, B:93:0x0336, B:95:0x0346, B:97:0x034e, B:98:0x0357, B:100:0x0360, B:102:0x0373, B:104:0x0377, B:108:0x03cf, B:111:0x03d9, B:112:0x03e3, B:114:0x03ec, B:118:0x03f1, B:119:0x03f8, B:121:0x03fb, B:224:0x037f, B:225:0x0381, B:229:0x039b, B:230:0x039c, B:231:0x03be, B:234:0x03c0, B:235:0x03c1, B:244:0x02e0, B:255:0x0289, B:228:0x0383), top: B:25:0x011a, inners: #11 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0242 A[Catch: all -> 0x0190, TryCatch #2 {all -> 0x0190, blocks: (B:26:0x011a, B:28:0x0129, B:30:0x0132, B:32:0x0140, B:33:0x0146, B:35:0x014f, B:41:0x015e, B:42:0x0180, B:56:0x0215, B:58:0x0219, B:61:0x0230, B:63:0x0234, B:66:0x0242, B:68:0x026b, B:70:0x027c, B:71:0x0296, B:74:0x02a0, B:77:0x02b8, B:83:0x02ce, B:85:0x02e3, B:87:0x02f1, B:89:0x0324, B:91:0x032c, B:93:0x0336, B:95:0x0346, B:97:0x034e, B:98:0x0357, B:100:0x0360, B:102:0x0373, B:104:0x0377, B:108:0x03cf, B:111:0x03d9, B:112:0x03e3, B:114:0x03ec, B:118:0x03f1, B:119:0x03f8, B:121:0x03fb, B:224:0x037f, B:225:0x0381, B:229:0x039b, B:230:0x039c, B:231:0x03be, B:234:0x03c0, B:235:0x03c1, B:244:0x02e0, B:255:0x0289, B:228:0x0383), top: B:25:0x011a, inners: #11 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void stream(Config c, int gen, boolean v2, Role role) {
        Throwable th;
        String str;
        String rest;
        NtripCore.Dechunker dechunk;
        boolean noGgaNoted;
        int i;
        OutputStream out;
        String first;
        int i2;
        NtripCore.Dechunker dechunk2;
        InputStream inp;
        ArrayDeque gaps;
        long connectedAt;
        InputStream inp2;
        long ggaAt;
        NtripCore.Mount near;
        String g;
        long ggaAt2;
        String str2;
        Role role2 = role;
        long connectedAt2 = System.currentTimeMillis();
        Socket socket2 = new Socket();
        try {
            Socket s = socket2;
            int i3 = 0;
            socket = s;
            s.setKeepAlive(true);
            s.setTcpNoDelay(true);
            s.connect(new InetSocketAddress(c.getHost(), c.getPort()), 10000);
            s.setSoTimeout(15000);
            OutputStream out2 = s.getOutputStream();
            InputStream inp3 = s.getInputStream();
            RtkStatus it = rtk;
            if (it != null) {
                try {
                    str = NtripCore.INSTANCE.gga(it, System.currentTimeMillis());
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        throw th;
                    } catch (Throwable th3) {
                        CloseableKt.closeFinally(socket2, th);
                        throw th3;
                    }
                }
            }
            str = lastGga;
            String gga0 = str;
            byte[] bytes = NtripCore.INSTANCE.request(c.getMount(), c.getUser(), c.getPass(), c.getHost(), c.getPort(), v2, gga0).getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            out2.write(bytes);
            out2.flush();
            Ntrip ntrip = INSTANCE;
            Intrinsics.checkNotNull(inp3);
            String first2 = ntrip.readLine(inp3);
            NtripCore.Answer a = NtripCore.INSTANCE.answer(first2);
            if (a instanceof NtripCore.Answer.Refused) {
                try {
                    s.setSoTimeout(2000);
                    rest = INSTANCE.readUpTo(inp3, 2048);
                } catch (Exception e) {
                    rest = "";
                }
                String said = NtripCore.INSTANCE.said(first2, rest);
                if (!Intrinsics.areEqual(said, lastSaid)) {
                    lastSaid = said;
                    INSTANCE.note("caster said " + said);
                }
                throw new IllegalStateException(((NtripCore.Answer.Refused) a).getWhy());
            }
            try {
                if (Intrinsics.areEqual(a, NtripCore.Answer.Sourcetable.INSTANCE)) {
                    throw new IllegalStateException("mountpoint \"" + c.getMount() + "\" not found on this caster");
                }
                if (!Intrinsics.areEqual(a, NtripCore.Answer.Stream.INSTANCE)) {
                    throw new NoWhenBranchMatchedException();
                }
                NtripCore.Dechunker dechunk3 = null;
                long checkAt = connectedAt2;
                try {
                    if (StringsKt.startsWith$default(StringsKt.trim((CharSequence) first2).toString(), "HTTP/", false, 2, (Object) null)) {
                        for (int k = 0; k < 40; k++) {
                            try {
                                String h = INSTANCE.readLine(inp3);
                                if (StringsKt.isBlank(h)) {
                                    break;
                                }
                                if (StringsKt.startsWith(h, "Transfer-Encoding:", true) && StringsKt.contains((CharSequence) h, (CharSequence) "chunked", true)) {
                                    dechunk3 = new NtripCore.Dechunker();
                                }
                                if (StringsKt.startsWith(h, "Content-Type:", true) && StringsKt.contains((CharSequence) h, (CharSequence) "sourcetable", true)) {
                                    throw new IllegalStateException("mountpoint \"" + c.getMount() + "\" not found on this caster");
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                throw th;
                            }
                        }
                        dechunk = dechunk3;
                    } else {
                        dechunk = null;
                    }
                    INSTANCE.note("connected (NTRIP " + (v2 ? "2.0" : "1.0") + ": " + StringsKt.take(StringsKt.trim((CharSequence) first2).toString(), 40) + ")");
                    s.setSoTimeout(1000);
                    INSTANCE.setState("connected, waiting for corrections");
                    long ggaAt3 = 0;
                    long sendAt = System.currentTimeMillis();
                    long ggaOkAt = System.currentTimeMillis();
                    boolean noGgaNoted2 = false;
                    ByteArrayOutputStream pending = new ByteArrayOutputStream();
                    byte[] buf = new byte[4096];
                    long quietSince = System.currentTimeMillis();
                    boolean stallNoted = false;
                    ArrayDeque gaps2 = new ArrayDeque();
                    while (true) {
                        Socket s2 = s;
                        if (gen != generation) {
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(socket2, null);
                            return;
                        }
                        long now = System.currentTimeMillis();
                        int i4 = i3;
                        if (now - ggaAt3 >= 10000) {
                            RtkStatus st = rtk;
                            if (st != null) {
                                noGgaNoted = noGgaNoted2;
                                i = i4;
                                g = NtripCore.INSTANCE.gga(st, now);
                                if (g == null) {
                                }
                                if (g == null) {
                                    first = first2;
                                    byte[] bytes2 = (g + "\r\n").getBytes(Charsets.UTF_8);
                                    Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
                                    out2.write(bytes2);
                                    out2.flush();
                                    ggaAt2 = now;
                                    if (st != null) {
                                        out = out2;
                                        str2 = NtripCore.INSTANCE.gga(st, now);
                                    } else {
                                        out = out2;
                                        str2 = null;
                                    }
                                    if (str2 != null) {
                                        lastGga = g;
                                        ggaOkAt = now;
                                        noGgaNoted = false;
                                    }
                                } else {
                                    out = out2;
                                    first = first2;
                                    ggaAt2 = (now - 10000) + 2000;
                                }
                                if (now - ggaOkAt > 30000 || noGgaNoted) {
                                    ggaAt3 = ggaAt2;
                                } else {
                                    INSTANCE.note("no fresh position from the receiver for 30 s (sending the last one)");
                                    noGgaNoted = true;
                                    ggaAt3 = ggaAt2;
                                }
                            } else {
                                noGgaNoted = noGgaNoted2;
                                i = i4;
                            }
                            String it2 = lastGga;
                            g = it2 != null ? NtripCore.INSTANCE.retime(it2, now) : null;
                            if (g == null) {
                            }
                            if (now - ggaOkAt > 30000) {
                            }
                            ggaAt3 = ggaAt2;
                        } else {
                            noGgaNoted = noGgaNoted2;
                            i = i4;
                            out = out2;
                            first = first2;
                        }
                        try {
                            i2 = inp3.read(buf);
                        } catch (SocketTimeoutException e2) {
                            i2 = 0;
                        }
                        int n = i2;
                        if (n < 0) {
                            CloseableKt.closeFinally(socket2, null);
                            return;
                        }
                        if (n > 0) {
                            NtripCore.Dechunker dc = dechunk;
                            if (dc != null) {
                                dc.feed(buf, n, pending);
                                if (dc.getEnded()) {
                                    CloseableKt.closeFinally(socket2, null);
                                    return;
                                }
                            } else {
                                pending.write(buf, 0, n);
                            }
                            inp = inp3;
                            bytesIn += n;
                            lastDataMs = now;
                            if (stallNoted) {
                                dechunk2 = dechunk;
                                stallNoted = false;
                                INSTANCE.note("corrections flowing again after " + ((now - quietSince) / 1000) + " s");
                            } else {
                                dechunk2 = dechunk;
                            }
                            if ((role2 != null ? role.getBackup() : null) == null || !role.getPlan().getOnBackup() || !StringsKt.isBlank(role.getBackup().getMount()) || now - quietSince < 8000) {
                                gaps = gaps2;
                            } else {
                                gaps = gaps2;
                                gaps.addLast(Long.valueOf(now));
                                while (!gaps.isEmpty() && now - ((Number) gaps.first()).longValue() > 180000) {
                                    gaps.removeFirst();
                                }
                                if (gaps.size() >= 3) {
                                    synchronized (badBases) {
                                        badBases.put(c.getMount(), Long.valueOf(now + 3600000));
                                        Unit unit2 = Unit.INSTANCE;
                                    }
                                    throw new IllegalStateException("base " + c.getMount() + " keeps stalling - trying the next nearest");
                                }
                            }
                            quietSince = now;
                        } else {
                            dechunk2 = dechunk;
                            inp = inp3;
                            gaps = gaps2;
                        }
                        if (now - quietSince > 15000 && !stallNoted) {
                            INSTANCE.note("no corrections for 15 s - waiting, not hanging up");
                            stallNoted = true;
                        }
                        if (now - quietSince > 90000 && netNow != null) {
                            throw new IllegalStateException("no corrections for 90 s");
                        }
                        Config config = null;
                        if (config == null || now - checkAt < 10000) {
                            connectedAt = connectedAt2;
                            gaps2 = gaps;
                            inp2 = inp;
                            ggaAt = ggaAt3;
                        } else {
                            try {
                                RtkStatus k2 = rtk;
                                if (k2 != null) {
                                    try {
                                        gaps2 = gaps;
                                        if ((Intrinsics.areEqual(k2.getRtk(), "fixed") || Intrinsics.areEqual(k2.getRtk(), "float")) && bytesIn > 0) {
                                            role2.setLastHelpMs(now);
                                        }
                                    } catch (Throwable th5) {
                                        th = th5;
                                        throw th;
                                    }
                                } else {
                                    gaps2 = gaps;
                                }
                                if (!role.getPlan().getOnBackup() && now - Math.max(connectedAt2, role.getLastHelpMs()) >= role.getPlan().getSwitchMs()) {
                                    throw new IllegalStateException("no RTK from these corrections for 3 minutes");
                                }
                                if (role.getPlan().getOnBackup() && role.getPlan().timeToRetryMain(now)) {
                                    throw new IllegalStateException("time to try the main caster again");
                                }
                                NtripCore.Mount base = role.getBase();
                                if (role.getPlan().getOnBackup() && StringsKt.isBlank(role.getBackup().getMount()) && base != null && k2 != null && k2.getHasFix()) {
                                    connectedAt = connectedAt2;
                                    try {
                                        checkAt = now;
                                        if (base.milesFrom(k2.getLat(), k2.getLon()) > 15.0d) {
                                            near = NtripCore.INSTANCE.nearestBase(mounts, k2.getLat(), k2.getLon(), (r20 & 8) != 0 ? 25.0d : 0.0d, (r20 & 16) != 0 ? SetsKt.emptySet() : INSTANCE.skipBases(now));
                                            if (near == null || Intrinsics.areEqual(near.getName(), base.getName())) {
                                                inp2 = inp;
                                                ggaAt = ggaAt3;
                                            } else {
                                                inp2 = inp;
                                                ggaAt = ggaAt3;
                                                if (near.milesFrom(k2.getLat(), k2.getLon()) < base.milesFrom(k2.getLat(), k2.getLon()) - 5.0d) {
                                                    throw new IllegalStateException("a nearer base");
                                                }
                                            }
                                        } else {
                                            inp2 = inp;
                                            ggaAt = ggaAt3;
                                        }
                                    } catch (Throwable th6) {
                                        th = th6;
                                        throw th;
                                    }
                                } else {
                                    connectedAt = connectedAt2;
                                    checkAt = now;
                                    inp2 = inp;
                                    ggaAt = ggaAt3;
                                }
                            } catch (Throwable th7) {
                                th = th7;
                            }
                        }
                        if (pending.size() > 0) {
                            if (now - sendAt < 250 && pending.size() <= 8000) {
                            }
                            String b64 = Base64.getEncoder().encodeToString(pending.toByteArray());
                            pending.reset();
                            sendAt = now;
                            DataClient dc2 = Link.INSTANCE.getData();
                            if (dc2 != null) {
                                commaGoneNoted = false;
                                dc2.request("rtk_rtcm", MapsKt.mapOf(TuplesKt.to("b64", b64)), new Function1() { // from class: com.starpilot.starview.Ntrip$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        Unit stream$lambda$16$lambda$15;
                                        stream$lambda$16$lambda$15 = Ntrip.stream$lambda$16$lambda$15((JSONObject) obj);
                                        return stream$lambda$16$lambda$15;
                                    }
                                });
                            } else if (!commaGoneNoted) {
                                commaGoneNoted = true;
                                INSTANCE.note("comma not connected - corrections not delivered");
                            }
                            INSTANCE.setState("receiving corrections (" + NtripCore.INSTANCE.size(bytesIn) + ")");
                            role2 = role;
                            inp3 = inp2;
                            dechunk = dechunk2;
                            s = s2;
                            connectedAt2 = connectedAt;
                            first2 = first;
                            out2 = out;
                            noGgaNoted2 = noGgaNoted;
                            i3 = i;
                            ggaAt3 = ggaAt;
                        }
                        role2 = role;
                        inp3 = inp2;
                        dechunk = dechunk2;
                        s = s2;
                        connectedAt2 = connectedAt;
                        first2 = first;
                        out2 = out;
                        noGgaNoted2 = noGgaNoted;
                        i3 = i;
                        ggaAt3 = ggaAt;
                    }
                } catch (Throwable th8) {
                    th = th8;
                }
            } catch (Throwable th9) {
                th = th9;
            }
        } catch (Throwable th10) {
            th = th10;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit stream$lambda$16$lambda$15(JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (!ack.optBoolean("ok")) {
            INSTANCE.setState("comma: " + ack.optString("error"));
        }
        return Unit.INSTANCE;
    }

    private final List<NtripCore.Mount> fetchMounts(String host, int port, String user, String pass) {
        String request;
        Socket socket2 = new Socket();
        try {
            Socket s = socket2;
            try {
                s.connect(new InetSocketAddress(StringsKt.trim((CharSequence) host).toString(), port), 10000);
                s.setSoTimeout(10000);
                OutputStream outputStream = s.getOutputStream();
                request = NtripCore.INSTANCE.request("", user, pass, (r17 & 8) != 0 ? "" : host, (r17 & 16) != 0 ? 0 : port, (r17 & 32) != 0 ? false : false, (r17 & 64) != 0 ? null : null);
                byte[] bytes = request.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                outputStream.write(bytes);
                InputStream inputStream = s.getInputStream();
                Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                String text = new String(ByteStreamsKt.readBytes(inputStream), Charsets.ISO_8859_1);
                RtkStatus st = rtk;
                List<NtripCore.Mount> sourcetable = NtripCore.INSTANCE.sourcetable(text, st != null ? st.getLat() : Double.NaN, st != null ? st.getLon() : Double.NaN);
                CloseableKt.closeFinally(socket2, null);
                return sourcetable;
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                try {
                    throw th2;
                } catch (Throwable th3) {
                    CloseableKt.closeFinally(socket2, th2);
                    throw th3;
                }
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public final void loadMounts(final String host, final int port, final String user, final String pass, final Function2<? super List<NtripCore.Mount>, ? super String, Unit> done) {
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(user, "user");
        Intrinsics.checkNotNullParameter(pass, "pass");
        Intrinsics.checkNotNullParameter(done, "done");
        final Handler main = new Handler(Looper.getMainLooper());
        Thread $this$loadMounts_u24lambda_u2422 = new Thread(new Runnable() { // from class: com.starpilot.starview.Ntrip$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                Ntrip.loadMounts$lambda$21(main, host, port, user, pass, done);
            }
        });
        $this$loadMounts_u24lambda_u2422.setDaemon(true);
        $this$loadMounts_u24lambda_u2422.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadMounts$lambda$21(Handler $main, String $host, int $port, String $user, String $pass, final Function2 $done) {
        String request;
        try {
            Socket socket2 = new Socket();
            try {
                Socket s = socket2;
                s.connect(new InetSocketAddress(StringsKt.trim((CharSequence) $host).toString(), $port), 10000);
                s.setSoTimeout(10000);
                OutputStream outputStream = s.getOutputStream();
                request = NtripCore.INSTANCE.request("", $user, $pass, (r17 & 8) != 0 ? "" : $host, (r17 & 16) != 0 ? 0 : $port, (r17 & 32) != 0 ? false : false, (r17 & 64) != 0 ? null : null);
                byte[] bytes = request.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                outputStream.write(bytes);
                InputStream inputStream = s.getInputStream();
                Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                String text = new String(ByteStreamsKt.readBytes(inputStream), Charsets.ISO_8859_1);
                String first = (String) SequencesKt.firstOrNull(StringsKt.lineSequence(text));
                if (first == null) {
                    first = "";
                }
                if ((NtripCore.INSTANCE.answer(first) instanceof NtripCore.Answer.Refused) && !StringsKt.contains$default((CharSequence) text, (CharSequence) "STR;", false, 2, (Object) null)) {
                    NtripCore.Answer answer = NtripCore.INSTANCE.answer(first);
                    Intrinsics.checkNotNull(answer, "null cannot be cast to non-null type com.starpilot.starview.NtripCore.Answer.Refused");
                    throw new IllegalStateException(((NtripCore.Answer.Refused) answer).getWhy());
                }
                RtkStatus st = rtk;
                final List list = NtripCore.INSTANCE.sourcetable(text, st != null ? st.getLat() : Double.NaN, st != null ? st.getLon() : Double.NaN);
                $main.post(new Runnable() { // from class: com.starpilot.starview.Ntrip$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Function2.this.invoke(list, null);
                    }
                });
                CloseableKt.closeFinally(socket2, null);
            } finally {
            }
        } catch (Exception e) {
            $main.post(new Runnable() { // from class: com.starpilot.starview.Ntrip$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Ntrip.loadMounts$lambda$21$lambda$20(Function2.this, e);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadMounts$lambda$21$lambda$20(Function2 $done, Exception $e) {
        String message = $e.getMessage();
        if (message == null) {
            message = $e.getClass().getSimpleName();
        }
        $done.invoke(null, message);
    }
}
