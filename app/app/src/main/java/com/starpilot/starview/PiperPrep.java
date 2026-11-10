package com.starpilot.starview;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.json.JSONObject;

/* compiled from: AmySynth.kt */
public final class PiperPrep {
    public static final PiperPrep INSTANCE = new PiperPrep();

    private PiperPrep() {
    }

    private final byte[] varint(int v) {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        int n = v;
        while (true) {
            int b = n & 127;
            n >>>= 7;
            if (n == 0) {
                o.write(b);
                byte[] byteArray = o.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                return byteArray;
            }
            o.write(b | 128);
        }
    }

    private final byte[] ld(int field, byte[] data) {
        return ArraysKt.plus(ArraysKt.plus(varint((field << 3) | 2), varint(data.length)), data);
    }

    public final List<Pair<String, String>> meta(JSONObject cfg) {
        String str;
        String str2;
        Intrinsics.checkNotNullParameter(cfg, "cfg");
        Pair[] pairArr = new Pair[7];
        pairArr[0] = TuplesKt.to("model_type", "vits");
        pairArr[1] = TuplesKt.to("comment", "piper");
        JSONObject optJSONObject = cfg.optJSONObject("language");
        if (optJSONObject == null || (str = optJSONObject.optString("name_english")) == null) {
            str = "English";
        }
        pairArr[2] = TuplesKt.to("language", str);
        JSONObject optJSONObject2 = cfg.optJSONObject("espeak");
        if (optJSONObject2 == null || (str2 = optJSONObject2.optString("voice")) == null) {
            str2 = "en-us";
        }
        pairArr[3] = TuplesKt.to("voice", str2);
        pairArr[4] = TuplesKt.to("has_espeak", "1");
        pairArr[5] = TuplesKt.to("n_speakers", String.valueOf(cfg.optInt("num_speakers", 1)));
        JSONObject optJSONObject3 = cfg.optJSONObject("audio");
        pairArr[6] = TuplesKt.to("sample_rate", String.valueOf(optJSONObject3 != null ? optJSONObject3.optInt("sample_rate", 22050) : 22050));
        return CollectionsKt.listOf((Object[]) pairArr);
    }

    public final byte[] metadataBytes(JSONObject cfg) {
        Intrinsics.checkNotNullParameter(cfg, "cfg");
        Iterable $this$fold$iv = meta(cfg);
        byte[] bArr = new byte[0];
        for (Object element$iv : $this$fold$iv) {
            Pair pair = (Pair) element$iv;
            byte[] acc = bArr;
            String k = (String) pair.component1();
            String v = (String) pair.component2();
            PiperPrep piperPrep = INSTANCE;
            PiperPrep piperPrep2 = INSTANCE;
            byte[] bytes = k.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            byte[] ld = piperPrep2.ld(1, bytes);
            PiperPrep piperPrep3 = INSTANCE;
            byte[] bytes2 = v.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
            bArr = ArraysKt.plus(acc, piperPrep.ld(14, ArraysKt.plus(ld, piperPrep3.ld(2, bytes2))));
        }
        return bArr;
    }

    public final String tokens(JSONObject cfg) {
        Intrinsics.checkNotNullParameter(cfg, "cfg");
        JSONObject m = cfg.getJSONObject("phoneme_id_map");
        StringBuilder sb = new StringBuilder();
        Iterator<String> keys = m.keys();
        Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
        while (keys.hasNext()) {
            String k = keys.next();
            sb.append(k).append(' ').append(m.getJSONArray(k).getInt(0)).append('\n');
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }
}
