package com.starpilot.starlock;

import com.starpilot.starlock.Pairing;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import java.util.Iterator;

/* compiled from: Pairing.kt */
public final /* data */ class Pairing {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion Companion = new Companion(null);
    private final String dongle;
    private final List<String> hosts;
    private final String key;
    private final int port;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Pairing copy$default(Pairing pairing, String str, int i, String str2, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = pairing.key;
        }
        if ((i2 & 2) != 0) {
            i = pairing.port;
        }
        if ((i2 & 4) != 0) {
            str2 = pairing.dongle;
        }
        if ((i2 & 8) != 0) {
            list = pairing.hosts;
        }
        return pairing.copy(str, i, str2, list);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.key;
    }

    /* renamed from: component2, reason: from getter */
    public final int component2() {
        return this.port;
    }

    /* renamed from: component3, reason: from getter */
    public final String component3() {
        return this.dongle;
    }

    public final List<String> component4() {
        return this.hosts;
    }

    public final Pairing copy(String key, int port, String dongle, List<String> hosts) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(dongle, "dongle");
        Intrinsics.checkNotNullParameter(hosts, "hosts");
        return new Pairing(key, port, dongle, hosts);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Pairing)) {
            return false;
        }
        Pairing pairing = (Pairing) other;
        return Intrinsics.areEqual(this.key, pairing.key) && this.port == pairing.port && Intrinsics.areEqual(this.dongle, pairing.dongle) && Intrinsics.areEqual(this.hosts, pairing.hosts);
    }

    public int hashCode() {
        return (((((this.key.hashCode() * 31) + Integer.hashCode(this.port)) * 31) + this.dongle.hashCode()) * 31) + this.hosts.hashCode();
    }

    public String toString() {
        return "Pairing(key=" + this.key + ", port=" + this.port + ", dongle=" + this.dongle + ", hosts=" + this.hosts + ")";
    }

    /* compiled from: Pairing.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
        public final Pairing parse(String var1) {
            String var4;
            int var14;
            label118: {
                Intrinsics.checkNotNullParameter(var1, "link");
                String var5 = StringsKt.trim(var1).toString();
                var4 = "";
                var1 = StringsKt.substringAfter(var5, "?", "");
                if (StringsKt.startsWith(var5, "starview://pair", false)) {
                    if (var1.length() > 0) {
                        var14 = (((boolean)true) ? 1 : 0);
                    } else {
                        var14 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var14 != 0)) {
                        var14 = 1;
                        break label118;
                    }
                }

                var14 = 0;
            }

            if ((var14 == 0)) {
                var1 = null;
            }

            if (var1 == null) {
                return null;
            } else {
                Iterable var9 = StringsKt.split(var1, new String[]{"&"}, false, 0);
                var var18 = new ArrayList();
                Iterator var6 = var9.iterator();

                while (var6.hasNext()) {
                    List var10 = StringsKt.split((String & CharSequence)var6.next(), new String[]{"="}, false, 2);
                    if (var10.size() == 2) {
                        var14 = (((boolean)true) ? 1 : 0);
                    } else {
                        var14 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var14 == 0)) {
                        var10 = null;
                    }

                    Pair var11;
                    if (var10 != null) {
                        var11 = TuplesKt.to(var10.get(0), URLDecoder.decode((String)var10.get(1), "UTF-8"));
                    } else {
                        var11 = null;
                    }

                    if (var11 != null) {
                        var18.add(var11);
                    }
                }

                List var12 = var18;
                Map var28 = MapsKt.toMap(var12);
                var1 = (String)var28.get("k");
                if (var1 != null) {
                    if (var1.length() >= 32) {
                        var14 = (((boolean)true) ? 1 : 0);
                    } else {
                        var14 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var14 == 0)) {
                        var1 = null;
                    }

                    if (var1 != null) {
                        label102: {
                            String var19 = (String)var28.get("p");
                            if (var19 != null) {
                                Integer var20 = StringsKt.toIntOrNull(var19);
                                if (var20 != null) {
                                    var14 = var20;
                                    break label102;
                                }
                            }

                            var14 = 8090;
                        }

                        String var21 = (String)var28.get("d");
                        if (var21 != null) {
                            var4 = var21;
                        }

                        String var22 = (String)var28.get("h");
                        if (var22 != null) {
                            List var23 = StringsKt.split(var22, new String[]{","}, false, 0);
                            if (var23 != null) {
                                Iterable var29 = var23;
                                var18 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var29, 10));
                                var6 = var29.iterator();

                                while (var6.hasNext()) {
                                    var18.add(StringsKt.trim((String & CharSequence)var6.next()).toString());
                                }

                                List var26 = var18;
                                Iterable var31 = var26;
                                var18 = new ArrayList();

                                for (Object var32 : var31) {
                                    boolean var3;
                                    if (((String)var32).length() > 0) {
                                        var3 = true;
                                    } else {
                                        var3 = false;
                                    }

                                    if (var3) {
                                        var18.add(var32);
                                    }
                                }

                                List var33 = var18;
                                return new Pairing(var1, var14, var4, var33);
                            }
                        }

                        List var24 = CollectionsKt.emptyList();
                        return new Pairing(var1, var14, var4, var24);
                    }
                }

                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final CharSequence nonce$lambda$7(byte it) {
            String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            return format;
        }

        public final String nonce() {
            byte[] b = new byte[16];
            new SecureRandom().nextBytes(b);
            return ArraysKt.joinToString(b, (CharSequence) "", "", "", -1, "...", new Function1() { // from class: com.starpilot.starlock.Pairing$Companion$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CharSequence nonce$lambda$7;
                    nonce$lambda$7 = Pairing.Companion.nonce$lambda$7(((Byte) obj).byteValue());
                    return nonce$lambda$7;
                }
            });
        }
    }

    public Pairing(String key, int port, String dongle, List<String> hosts) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(dongle, "dongle");
        Intrinsics.checkNotNullParameter(hosts, "hosts");
        this.key = key;
        this.port = port;
        this.dongle = dongle;
        this.hosts = hosts;
    }

    public final String getDongle() {
        return this.dongle;
    }

    public final List<String> getHosts() {
        return this.hosts;
    }

    public final String getKey() {
        return this.key;
    }

    public final int getPort() {
        return this.port;
    }

    private final String mac(String... parts) {
        Mac h = Mac.getInstance("HmacSHA256");
        byte[] bytes = this.key.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        h.init(new SecretKeySpec(bytes, "HmacSHA256"));
        byte[] bytes2 = ArraysKt.joinToString(parts, "|", "", "", -1, "...", null).getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
        byte[] doFinal = h.doFinal(bytes2);
        Intrinsics.checkNotNullExpressionValue(doFinal, "doFinal(...)");
        return ArraysKt.joinToString(doFinal, (CharSequence) "", "", "", -1, "...", new Function1() { // from class: com.starpilot.starlock.Pairing$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence mac$lambda$0;
                mac$lambda$0 = Pairing.mac$lambda$0(((Byte) obj).byteValue());
                return mac$lambda$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence mac$lambda$0(byte it) {
        String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public static /* synthetic */ String serverProof$default(Pairing pairing, String str, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = "";
        }
        return pairing.serverProof(str, str2, str3);
    }

    public final String serverProof(String dongle, String cn, String sn) {
        Intrinsics.checkNotNullParameter(dongle, "dongle");
        Intrinsics.checkNotNullParameter(cn, "cn");
        Intrinsics.checkNotNullParameter(sn, "sn");
        return mac("srv", dongle, cn, sn);
    }

    public final String clientProof(String dongle, String sn, String cn) {
        Intrinsics.checkNotNullParameter(dongle, "dongle");
        Intrinsics.checkNotNullParameter(sn, "sn");
        Intrinsics.checkNotNullParameter(cn, "cn");
        return mac("cli", dongle, sn, cn);
    }

    public final String encode() {
        return "starview://pair?k=" + this.key + "&p=" + this.port + (this.dongle.length() > 0 ? "&d=" + this.dongle : "") + (this.hosts.isEmpty() ? "" : "&h=" + CollectionsKt.joinToString(this.hosts, ",", "", "", -1, "...", null));
    }
}
