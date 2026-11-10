package com.starpilot.starview;

import android.util.Log;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: Discovery.kt */
public final class Discovery {
    public static final Discovery INSTANCE = new Discovery();
    public static final int PORT = 8091;
    private static final String TAG = "StarView/Discovery";

    private Discovery() {
    }

    /* compiled from: Discovery.kt */
    public static final /* data */ class Found {
        private final String branch;
        private final String commit;
        private final String dongleId;
        private final String host;
        private final int port;
        private final boolean verified;

        public static /* synthetic */ Found copy$default(Found found, String str, int i, String str2, String str3, String str4, boolean z, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = found.host;
            }
            if ((i2 & 2) != 0) {
                i = found.port;
            }
            int i3 = i;
            if ((i2 & 4) != 0) {
                str2 = found.dongleId;
            }
            String str5 = str2;
            if ((i2 & 8) != 0) {
                str3 = found.branch;
            }
            String str6 = str3;
            if ((i2 & 16) != 0) {
                str4 = found.commit;
            }
            String str7 = str4;
            if ((i2 & 32) != 0) {
                z = found.verified;
            }
            return found.copy(str, i3, str5, str6, str7, z);
        }

        /* renamed from: component1, reason: from getter */
        public final String getHost() {
            return this.host;
        }

        /* renamed from: component2, reason: from getter */
        public final int getPort() {
            return this.port;
        }

        /* renamed from: component3, reason: from getter */
        public final String getDongleId() {
            return this.dongleId;
        }

        /* renamed from: component4, reason: from getter */
        public final String getBranch() {
            return this.branch;
        }

        /* renamed from: component5, reason: from getter */
        public final String getCommit() {
            return this.commit;
        }

        /* renamed from: component6, reason: from getter */
        public final boolean getVerified() {
            return this.verified;
        }

        public final Found copy(String host, int port, String dongleId, String branch, String commit, boolean verified) {
            Intrinsics.checkNotNullParameter(host, "host");
            Intrinsics.checkNotNullParameter(dongleId, "dongleId");
            Intrinsics.checkNotNullParameter(branch, "branch");
            Intrinsics.checkNotNullParameter(commit, "commit");
            return new Found(host, port, dongleId, branch, commit, verified);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Found)) {
                return false;
            }
            Found found = (Found) other;
            return Intrinsics.areEqual(this.host, found.host) && this.port == found.port && Intrinsics.areEqual(this.dongleId, found.dongleId) && Intrinsics.areEqual(this.branch, found.branch) && Intrinsics.areEqual(this.commit, found.commit) && this.verified == found.verified;
        }

        public int hashCode() {
            return (((((((((this.host.hashCode() * 31) + Integer.hashCode(this.port)) * 31) + this.dongleId.hashCode()) * 31) + this.branch.hashCode()) * 31) + this.commit.hashCode()) * 31) + Boolean.hashCode(this.verified);
        }

        public String toString() {
            return "Found(host=" + this.host + ", port=" + this.port + ", dongleId=" + this.dongleId + ", branch=" + this.branch + ", commit=" + this.commit + ", verified=" + this.verified + ")";
        }

        public Found(String host, int port, String dongleId, String branch, String commit, boolean verified) {
            Intrinsics.checkNotNullParameter(host, "host");
            Intrinsics.checkNotNullParameter(dongleId, "dongleId");
            Intrinsics.checkNotNullParameter(branch, "branch");
            Intrinsics.checkNotNullParameter(commit, "commit");
            this.host = host;
            this.port = port;
            this.dongleId = dongleId;
            this.branch = branch;
            this.commit = commit;
            this.verified = verified;
        }

        public final String getBranch() {
            return this.branch;
        }

        public final String getCommit() {
            return this.commit;
        }

        public final String getDongleId() {
            return this.dongleId;
        }

        public final String getHost() {
            return this.host;
        }

        public final int getPort() {
            return this.port;
        }

        public final boolean getVerified() {
            return this.verified;
        }
    }

    private final List<InetAddress> broadcastAddresses() {
        List out = new ArrayList();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            Intrinsics.checkNotNullExpressionValue(networkInterfaces, "getNetworkInterfaces(...)");
            Iterator it = CollectionsKt.iterator(networkInterfaces);
            while (it.hasNext()) {
                NetworkInterface nif = (NetworkInterface) it.next();
                if (nif.isUp() && !nif.isLoopback()) {
                    for (InterfaceAddress ia : nif.getInterfaceAddresses()) {
                        InetAddress b = ia.getBroadcast();
                        if (b != null) {
                            out.add(b);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.w("StarView/Discovery", "enumerating interfaces: " + e);
        }
        InetAddress byName = InetAddress.getByName("255.255.255.255");
        Intrinsics.checkNotNullExpressionValue(byName, "getByName(...)");
        out.add(byName);
        return CollectionsKt.distinct(out);
    }

    private final List<Triple<Integer, Integer, String>> subnets() {
        List out = new ArrayList();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            Intrinsics.checkNotNullExpressionValue(networkInterfaces, "getNetworkInterfaces(...)");
            Iterator it = CollectionsKt.iterator(networkInterfaces);
            while (it.hasNext()) {
                NetworkInterface nif = (NetworkInterface) it.next();
                if (nif.isUp() && !nif.isLoopback()) {
                    for (InterfaceAddress ia : nif.getInterfaceAddresses()) {
                        byte[] a = ia.getAddress().getAddress();
                        if (a.length == 4) {
                            int ip = ByteBuffer.wrap(a).getInt();
                            int mask = ia.getNetworkPrefixLength() <= 0 ? 0 : (-1) << (32 - ia.getNetworkPrefixLength());
                            out.add(new Triple(Integer.valueOf(ip & mask), Integer.valueOf(mask), nif.getName()));
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return out;
    }

    public final String interfaceFor(String host) {
        Intrinsics.checkNotNullParameter(host, "host");
        try {
            int ip = ByteBuffer.wrap(InetAddress.getByName(host).getAddress()).getInt();
            for (Triple<Integer, Integer, String> triple : subnets()) {
                int net = triple.component1().intValue();
                int mask = triple.component2().intValue();
                String name = triple.component3();
                if ((ip & mask) == net) {
                    return name;
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public final boolean usbInterfaceUp() {
        Iterable $this$any$iv = subnets();
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            Triple it = (Triple) element$iv;
            if (INSTANCE.isUsb((String) it.getThird())) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUsb(String ifname) {
        Intrinsics.checkNotNullParameter(ifname, "ifname");
        return StringsKt.startsWith$default(ifname, "rndis", false, 2, (Object) null) || StringsKt.startsWith$default(ifname, "ncm", false, 2, (Object) null) || StringsKt.startsWith$default(ifname, "usb", false, 2, (Object) null);
    }

    public static /* synthetic */ Found find$default(Discovery discovery, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 1500;
        }
        return discovery.find(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x0236 A[Catch: all -> 0x0254, TRY_LEAVE, TryCatch #13 {all -> 0x0254, blocks: (B:8:0x007b, B:10:0x0087, B:11:0x008b, B:13:0x0091, B:16:0x0099, B:19:0x00a2, B:26:0x00b4, B:32:0x00d9, B:36:0x00e6, B:39:0x00ec, B:42:0x00fb, B:45:0x0106, B:48:0x010a, B:50:0x0122, B:56:0x012e, B:147:0x014e, B:62:0x0173, B:65:0x0180, B:67:0x01ac, B:68:0x01b2, B:71:0x01be, B:84:0x0299, B:86:0x02ad, B:94:0x02c0, B:98:0x02de, B:105:0x030f, B:135:0x0236, B:145:0x020e), top: B:7:0x007b }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01ac A[Catch: Exception -> 0x01ca, SocketTimeoutException -> 0x01cc, all -> 0x0254, TryCatch #13 {all -> 0x0254, blocks: (B:8:0x007b, B:10:0x0087, B:11:0x008b, B:13:0x0091, B:16:0x0099, B:19:0x00a2, B:26:0x00b4, B:32:0x00d9, B:36:0x00e6, B:39:0x00ec, B:42:0x00fb, B:45:0x0106, B:48:0x010a, B:50:0x0122, B:56:0x012e, B:147:0x014e, B:62:0x0173, B:65:0x0180, B:67:0x01ac, B:68:0x01b2, B:71:0x01be, B:84:0x0299, B:86:0x02ad, B:94:0x02c0, B:98:0x02de, B:105:0x030f, B:135:0x0236, B:145:0x020e), top: B:7:0x007b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Found find(int timeoutMs) {
        String str;
        byte[] payload;
        long deadline;
        byte[] buf;
        String str2;
        Object obj;
        long lastSend;
        DatagramPacket p;
        JSONObject j;
        byte[] data;
        boolean z;
        boolean z2;
        long lastSend2;
        InetAddress t;
        String str3 = "<get-values>(...)";
        DatagramSocket sock = new DatagramSocket();
        LinkedHashMap found = new LinkedHashMap();
        try {
            sock.setBroadcast(true);
            sock.setSoTimeout(250);
            String cn = Link.INSTANCE.nonce();
            byte[] bytes = ("STARVIEW? " + cn).getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            byte[] payload2 = bytes;
            List targets = broadcastAddresses();
            Log.i("StarView/Discovery", "broadcasting to " + targets);
            long deadline2 = System.currentTimeMillis() + timeoutMs;
            long lastSend3 = 0;
            byte[] buf2 = new byte[2048];
            long firstReplyAt = 0;
            while (true) {
                if (System.currentTimeMillis() >= deadline2) {
                    str = "";
                    payload = payload2;
                    deadline = deadline2;
                    buf = buf2;
                    str2 = str3;
                    break;
                }
                try {
                    if (System.currentTimeMillis() - lastSend3 > 500) {
                        for (InetAddress t2 : targets) {
                            try {
                                lastSend2 = lastSend3;
                                t = t2;
                            } catch (Exception e) {
                                e = e;
                                lastSend2 = lastSend3;
                                t = t2;
                            }
                            try {
                                sock.send(new DatagramPacket(payload2, payload2.length, t, 8091));
                                lastSend3 = lastSend2;
                            } catch (Exception e2) {
                                e = e2;
                                Log.d("StarView/Discovery", "send " + t + ": " + e);
                                lastSend3 = lastSend2;
                            }
                        }
                        lastSend3 = System.currentTimeMillis();
                    }
                    try {
                        lastSend = lastSend3;
                        buf = buf2;
                        try {
                            p = new DatagramPacket(buf, buf.length);
                            sock.receive(p);
                            data = p.getData();
                            str2 = str3;
                        } catch (SocketTimeoutException e3) {
                            str2 = str3;
                        } catch (Exception e4) {
                            e = e4;
                            str2 = str3;
                        }
                        try {
                            Intrinsics.checkNotNullExpressionValue(data, "getData(...)");
                            payload = payload2;
                            try {
                                deadline = deadline2;
                                try {
                                    j = new JSONObject(new String(data, 0, p.getLength(), Charsets.UTF_8));
                                } catch (SocketTimeoutException e5) {
                                } catch (Exception e6) {
                                    e = e6;
                                }
                            } catch (SocketTimeoutException e7) {
                                str = "";
                                deadline = deadline2;
                            } catch (Exception e8) {
                                e = e8;
                                str = "";
                                deadline = deadline2;
                            }
                        } catch (SocketTimeoutException e9) {
                            str = "";
                            payload = payload2;
                            deadline = deadline2;
                            if (firstReplyAt == 0) {
                            }
                            str3 = str2;
                            lastSend3 = lastSend;
                            payload2 = payload;
                            deadline2 = deadline;
                            buf2 = buf;
                        } catch (Exception e10) {
                            e = e10;
                            str = "";
                            payload = payload2;
                            deadline = deadline2;
                            Log.d("StarView/Discovery", "recv: " + e);
                            if (firstReplyAt == 0) {
                            }
                            str3 = str2;
                            lastSend3 = lastSend;
                            payload2 = payload;
                            deadline2 = deadline;
                            buf2 = buf;
                        }
                    } catch (SocketTimeoutException e11) {
                        str = "";
                        payload = payload2;
                        deadline = deadline2;
                        lastSend = lastSend3;
                        buf = buf2;
                        str2 = str3;
                    } catch (Exception e12) {
                        e = e12;
                        str = "";
                        payload = payload2;
                        deadline = deadline2;
                        lastSend = lastSend3;
                        buf = buf2;
                        str2 = str3;
                    }
                    if (Intrinsics.areEqual(j.optString("type"), "starview")) {
                        String host = p.getAddress().getHostAddress();
                        if (host == null) {
                            z = true;
                        } else {
                            String dongle = j.optString("dongleId");
                            Link link = Link.INSTANCE;
                            Intrinsics.checkNotNull(dongle);
                            String optString = j.optString("proof");
                            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                            boolean verified = link.commaProofOk(dongle, cn, "", optString);
                            Found found2 = (Found) found.get(host);
                            try {
                                if (found2 != null) {
                                    try {
                                        z = true;
                                        if (found2.getVerified()) {
                                            z2 = true;
                                            if (z2 || verified) {
                                                LinkedHashMap linkedHashMap = found;
                                                str = "";
                                                int optInt = j.optInt("port", 8090);
                                                String optString2 = j.optString("branch");
                                                Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                                                String optString3 = j.optString("commit");
                                                Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
                                                linkedHashMap.put(host, new Found(host, optInt, dongle, optString2, optString3, verified));
                                                if (firstReplyAt == 0) {
                                                    firstReplyAt = System.currentTimeMillis();
                                                }
                                                if (isUsb(interfaceFor(host)) && (verified || !Link.INSTANCE.getPaired())) {
                                                    break;
                                                }
                                            }
                                        }
                                    } catch (SocketTimeoutException e13) {
                                        str = "";
                                        if (firstReplyAt == 0) {
                                        }
                                        str3 = str2;
                                        lastSend3 = lastSend;
                                        payload2 = payload;
                                        deadline2 = deadline;
                                        buf2 = buf;
                                    } catch (Exception e14) {
                                        e = e14;
                                        str = "";
                                        Log.d("StarView/Discovery", "recv: " + e);
                                        if (firstReplyAt == 0) {
                                        }
                                        str3 = str2;
                                        lastSend3 = lastSend;
                                        payload2 = payload;
                                        deadline2 = deadline;
                                        buf2 = buf;
                                    }
                                } else {
                                    z = true;
                                }
                                int optInt2 = j.optInt("port", 8090);
                                String optString22 = j.optString("branch");
                                Intrinsics.checkNotNullExpressionValue(optString22, "optString(...)");
                                String optString32 = j.optString("commit");
                                Intrinsics.checkNotNullExpressionValue(optString32, "optString(...)");
                                linkedHashMap.put(host, new Found(host, optInt2, dongle, optString22, optString32, verified));
                                if (firstReplyAt == 0) {
                                }
                                if (isUsb(interfaceFor(host))) {
                                    break;
                                }
                            } catch (SocketTimeoutException e15) {
                            } catch (Exception e16) {
                                e = e16;
                                Log.d("StarView/Discovery", "recv: " + e);
                            }
                            z2 = false;
                            if (z2) {
                            }
                            LinkedHashMap linkedHashMap2 = found;
                            str = "";
                        }
                        str3 = str2;
                        lastSend3 = lastSend;
                        payload2 = payload;
                        deadline2 = deadline;
                        buf2 = buf;
                    } else {
                        str = "";
                    }
                    if (firstReplyAt == 0 && System.currentTimeMillis() - firstReplyAt > 600) {
                        lastSend3 = lastSend;
                        break;
                    }
                    str3 = str2;
                    lastSend3 = lastSend;
                    payload2 = payload;
                    deadline2 = deadline;
                    buf2 = buf;
                } catch (Throwable th) {
                    th = th;
                    sock.close();
                    throw th;
                }
            }
            lastSend3 = lastSend;
            if (found.isEmpty()) {
                sock.close();
                return null;
            }
            Iterable values = found.values();
            String str4 = str2;
            Intrinsics.checkNotNullExpressionValue(values, str4);
            Iterable $this$filter$iv = values;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                if (((Found) element$iv$iv).getVerified()) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            ArrayList arrayList = (List) destination$iv$iv;
            if (arrayList.isEmpty()) {
                Collection values2 = found.values();
                Intrinsics.checkNotNullExpressionValue(values2, str4);
                arrayList = CollectionsKt.toList(values2);
            }
            List pool = arrayList;
            List $this$firstOrNull$iv = pool;
            int $i$f$firstOrNull = 0;
            Iterator it = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                Object element$iv = it.next();
                Iterable $this$firstOrNull$iv2 = $this$firstOrNull$iv;
                int $i$f$firstOrNull2 = $i$f$firstOrNull;
                if (INSTANCE.isUsb(INSTANCE.interfaceFor(((Found) element$iv).getHost()))) {
                    obj = element$iv;
                    break;
                }
                $this$firstOrNull$iv = $this$firstOrNull$iv2;
                $i$f$firstOrNull = $i$f$firstOrNull2;
            }
            Found found3 = (Found) obj;
            if (found3 == null) {
                Object first = CollectionsKt.first((List<? extends Object>) pool);
                Intrinsics.checkNotNullExpressionValue(first, "first(...)");
                found3 = (Found) first;
            }
            Found best = found3;
            Iterable values3 = found.values();
            Intrinsics.checkNotNullExpressionValue(values3, str4);
            Iterable $this$map$iv = values3;
            int $i$f$map = 0;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterable $this$mapTo$iv$iv = $this$map$iv;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                Found it2 = (Found) item$iv$iv;
                Iterable $this$map$iv2 = $this$map$iv;
                int $i$f$map2 = $i$f$map;
                LinkedHashMap found4 = found;
                try {
                    destination$iv$iv2.add(it2.getHost() + "(" + INSTANCE.interfaceFor(it2.getHost()) + (it2.getVerified() ? ", verified" : str) + ")");
                    $this$map$iv = $this$map$iv2;
                    $i$f$map = $i$f$map2;
                    found = found4;
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv;
                } catch (Throwable th2) {
                    th = th2;
                    sock.close();
                    throw th;
                }
            }
            Log.i("StarView/Discovery", "responders: " + ((List) destination$iv$iv2) + " -> " + best.getHost());
            sock.close();
            return best;
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
