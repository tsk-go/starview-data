package com.starpilot.starview;

import com.starpilot.starview.NtripCore;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: RtkCore.kt */
public final class NtripCore {
    public static final NtripCore INSTANCE = new NtripCore();

    private NtripCore() {
    }

    public final String size(long bytes) {
        if (bytes < 1048576) {
            return (bytes / 1024) + " KB";
        }
        if (bytes >= 1073741824) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(Locale.US, "%.2f GB", Arrays.copyOf(new Object[]{Double.valueOf(bytes / 1.073741824E9d)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            return format;
        }
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        String format2 = String.format(Locale.US, "%.1f MB", Arrays.copyOf(new Object[]{Double.valueOf(bytes / 1048576.0d)}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        return format2;
    }

    public final String gga(RtkStatus st, long utcMs) {
        String alt;
        Intrinsics.checkNotNullParameter(st, "st");
        if (!st.getHasFix()) {
            return null;
        }
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.setTimeInMillis(utcMs);
        String time = String.format(Locale.US, "%02d%02d%05.2f", Arrays.copyOf(new Object[]{Integer.valueOf(c.get(11)), Integer.valueOf(c.get(12)), Double.valueOf(c.get(13) + (c.get(14) / 1000.0d))}, 3));
        Intrinsics.checkNotNullExpressionValue(time, "format(...)");
        int quality = Intrinsics.areEqual(st.getRtk(), "fixed") ? 4 : Intrinsics.areEqual(st.getRtk(), "float") ? 5 : Intrinsics.areEqual(st.getFixType(), "dead reckoning") ? 6 : 1;
        double altM = st.getAltM();
        if ((Double.isInfinite(altM) || Double.isNaN(altM)) ? false : true) {
            alt = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{Double.valueOf(st.getAltM())}, 1));
            Intrinsics.checkNotNullExpressionValue(alt, "format(...)");
        } else {
            alt = "0.0";
        }
        String gga$dm = gga$dm(st.getLat(), 2);
        String str = st.getLat() >= 0.0d ? "N" : "S";
        String gga$dm2 = gga$dm(st.getLon(), 3);
        String str2 = st.getLon() >= 0.0d ? "E" : "W";
        String format = String.format(Locale.US, quality + ",%02d,1.0," + alt + ",M,0.0,M,,", Arrays.copyOf(new Object[]{Integer.valueOf(RangesKt.coerceIn(st.getUsed(), 0, 99))}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        String body = "GPGGA," + time + "," + gga$dm + "," + str + "," + gga$dm2 + "," + str2 + "," + format;
        String str3 = "$" + body + "*%02X";
        Locale locale = Locale.US;
        String $this$fold$iv = body;
        Object accumulator$iv = 0;
        for (int i = 0; i < $this$fold$iv.length(); i++) {
            char element$iv = $this$fold$iv.charAt(i);
            int x = ((Number) accumulator$iv).intValue();
            accumulator$iv = Integer.valueOf(x ^ element$iv);
        }
        String format2 = String.format(locale, str3, Arrays.copyOf(new Object[]{accumulator$iv}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        return format2;
    }

    private static final String gga$dm(double v, int degDigits) {
        double a = Math.abs(v);
        int deg = (int) Math.floor(a);
        double min = (a - deg) * 60;
        String format = String.format(Locale.US, "%0" + degDigits + "d%010.7f", Arrays.copyOf(new Object[]{Integer.valueOf(deg), Double.valueOf(min)}, 2));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final String retime(String gga, long utcMs) {
        Intrinsics.checkNotNullParameter(gga, "gga");
        String body = StringsKt.substringBefore(StringsKt.removePrefix(gga, (CharSequence) "$"), '*', (StringsKt.removePrefix(gga, (CharSequence) "$")));
        List f = CollectionsKt.toMutableList((Collection) StringsKt.split((CharSequence) body, new String[]{","}, false, 0));
        if (f.size() < 2) {
            return null;
        }
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.setTimeInMillis(utcMs);
        String format = String.format(Locale.US, "%02d%02d%05.2f", Arrays.copyOf(new Object[]{Integer.valueOf(c.get(11)), Integer.valueOf(c.get(12)), Double.valueOf(c.get(13) + (c.get(14) / 1000.0d))}, 3));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        f.set(1, format);
        String b = CollectionsKt.joinToString(f, ",", "", "", -1, "...", null);
        String str = "$" + b + "*%02X";
        Locale locale = Locale.US;
        String $this$fold$iv = b;
        Object accumulator$iv = 0;
        for (int i = 0; i < $this$fold$iv.length(); i++) {
            char element$iv = $this$fold$iv.charAt(i);
            int x = ((Number) accumulator$iv).intValue();
            accumulator$iv = Integer.valueOf(x ^ element$iv);
        }
        String format2 = String.format(locale, str, Arrays.copyOf(new Object[]{accumulator$iv}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        return format2;
    }

    public final String request(String mount, String user, String pass, String host, int port, boolean v2, String gga) {
        String auth;
        String str;
        String h;
        Intrinsics.checkNotNullParameter(mount, "mount");
        Intrinsics.checkNotNullParameter(user, "user");
        Intrinsics.checkNotNullParameter(pass, "pass");
        Intrinsics.checkNotNullParameter(host, "host");
        if (user.length() > 0) {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] bytes = (user + ":" + pass).getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            auth = "Authorization: Basic " + encoder.encodeToString(bytes) + "\r\n";
        } else {
            auth = "";
        }
        if (v2) {
            String it = StringsKt.trim((CharSequence) host).toString();
            if (it.length() == 0) {
                h = "";
            } else {
                h = "Host: " + it + ((port <= 0 || port == 80) ? "" : ":" + port) + "\r\n";
            }
            return "GET /" + StringsKt.trimStart(StringsKt.trim((CharSequence) mount).toString(), '/') + " HTTP/1.1\r\n" + h + "Ntrip-Version: Ntrip/2.0\r\nUser-Agent: NTRIP StarView/2.70\r\n" + (gga != null ? "Ntrip-GGA: " + gga + "\r\n" : "") + auth + "Connection: close\r\n\r\n";
        }
        String it2 = StringsKt.trim((CharSequence) host).toString();
        if (!(it2.length() == 0)) {
            if (port > 0 && port != 80) {
                str = ":" + port;
            }
            str = "Host: " + it2 + str + "\r\n";
        }
        String h2 = str;
        return "GET /" + StringsKt.trimStart(StringsKt.trim((CharSequence) mount).toString(), '/') + " HTTP/1.0\r\n" + h2 + "User-Agent: NTRIP StarView/2.59\r\nAccept: */*\r\n" + auth + "Connection: close\r\n\r\n";
    }

    public final String said(String first, String rest) {
        Object element$iv;
        String substringAfter$default;
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(rest, "rest");
        List lines = StringsKt.split((CharSequence) StringsKt.replace(rest, "\r", "", false), new char[]{'\n'}, false, 0);
        int i = 0;
        while (i < lines.size() && new Regex("^[A-Za-z0-9-]+:\\s").containsMatchIn((CharSequence) lines.get(i))) {
            i++;
        }
        Iterable head = CollectionsKt.take(lines, i);
        Iterable $this$firstOrNull$iv = head;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                String it2 = (String) element$iv;
                if (StringsKt.startsWith(it2, "Server:", true)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        String str = (String) element$iv;
        String server = (str == null || (substringAfter$default = StringsKt.substringAfter(str, ':', str)) == null) ? null : StringsKt.trim((CharSequence) substringAfter$default).toString();
        String body = StringsKt.trim((CharSequence) new Regex("\\s+").replace(new Regex("<[^>]*>").replace(new Regex("(?s)<(script|style)[^>]*>.*?</\\1>").replace(CollectionsKt.joinToString(CollectionsKt.drop(lines, i), " ", "", "", -1, "...", null), " "), " "), " ")).toString();
        StringBuilder $this$said_u24lambda_u247 = new StringBuilder();
        $this$said_u24lambda_u247.append('\"').append(StringsKt.take(StringsKt.trim((CharSequence) first).toString(), 80)).append('\"');
        String str2 = server;
        if (!(str2 == null || str2.length() == 0)) {
            $this$said_u24lambda_u247.append(", server ").append(StringsKt.take(server, 40));
        }
        if ((body.length() > 0) && !StringsKt.equals(body, StringsKt.substringAfter(StringsKt.trim((CharSequence) first).toString(), ' ', (StringsKt.trim((CharSequence) first).toString())), true)) {
            $this$said_u24lambda_u247.append(": ").append(StringsKt.take(body, 160));
        }
        String sb = $this$said_u24lambda_u247.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
        return sb;
    }

    /* compiled from: RtkCore.kt */
    public static final class Dechunker {
        private boolean ended;
        private int left;
        private final StringBuilder size = new StringBuilder();
        private int skip;

        public final boolean getEnded() {
            return this.ended;
        }

        public final void feed(byte[] b, int n, OutputStream out) {
            Intrinsics.checkNotNullParameter(b, "b");
            Intrinsics.checkNotNullParameter(out, "out");
            int i = 0;
            while (i < n && !this.ended) {
                if (this.skip > 0) {
                    this.skip--;
                    i++;
                } else if (this.left > 0) {
                    int k = Math.min(this.left, n - i);
                    out.write(b, i, k);
                    this.left -= k;
                    i += k;
                    if (this.left == 0) {
                        this.skip = 2;
                    }
                } else {
                    char ch = (char) b[i];
                    i++;
                    if (ch == '\n') {
                        String sb = this.size.toString();
                        Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
                        String hex = StringsKt.trim((CharSequence) StringsKt.substringBefore(sb, ';', sb)).toString();
                        this.size.setLength(0);
                        if (hex.length() == 0) {
                            continue;
                        } else {
                            Integer intOrNull = StringsKt.toIntOrNull(hex, 16);
                            if (intOrNull == null) {
                                throw new IllegalStateException("caster sent a broken chunk");
                            }
                            this.left = intOrNull.intValue();
                            if (this.left == 0) {
                                this.ended = true;
                            }
                        }
                    } else if (ch == '\r') {
                        continue;
                    } else {
                        if (this.size.length() > 16) {
                            throw new IllegalStateException("caster sent a broken chunk");
                        }
                        this.size.append(ch);
                    }
                }
            }
        }
    }

    /* compiled from: RtkCore.kt */
    public static final class BackupPlan {
        private long backupSinceMs;
        private long mainBadSinceMs;
        private boolean onBackup;
        private final long retryMainMs;
        private final long switchMs;

        public BackupPlan() {
            this(0L, 0L, 3, null);
        }

        public BackupPlan(long switchMs, long retryMainMs) {
            this.switchMs = switchMs;
            this.retryMainMs = retryMainMs;
        }

        public /* synthetic */ BackupPlan(long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 180000L : j, (i & 2) != 0 ? 600000L : j2);
        }

        public final long getRetryMainMs() {
            return this.retryMainMs;
        }

        public final long getSwitchMs() {
            return this.switchMs;
        }

        public final boolean getOnBackup() {
            return this.onBackup;
        }

        public final long getBackupSinceMs() {
            return this.backupSinceMs;
        }

        public final void mainResult(boolean helped, long startedMs) {
            long j = 0;
            if (!helped) {
                j = this.mainBadSinceMs == 0 ? startedMs : this.mainBadSinceMs;
            }
            this.mainBadSinceMs = j;
        }

        public final boolean shouldSwitch(long nowMs) {
            return (this.onBackup || this.mainBadSinceMs == 0 || nowMs - this.mainBadSinceMs < this.switchMs) ? false : true;
        }

        public final void toBackup(long nowMs) {
            this.onBackup = true;
            this.backupSinceMs = nowMs;
        }

        public final boolean timeToRetryMain(long nowMs) {
            return this.onBackup && nowMs - this.backupSinceMs >= this.retryMainMs;
        }

        public final void toMain(long nowMs) {
            this.onBackup = false;
            this.mainBadSinceMs = nowMs - this.switchMs;
        }
    }

    /* compiled from: RtkCore.kt */
    public static abstract class Answer {
        public /* synthetic */ Answer(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* compiled from: RtkCore.kt */
        public static final class Refused extends Answer {
            private final String why;

            /* VF: from Vineflower (jadx: ) */
            public Refused(String var1) {
                super(null);
                Intrinsics.checkNotNullParameter(var1, "why");
                this.why = var1;
            }

            public final String getWhy() {
                return this.why;
            }
        }

        /* compiled from: RtkCore.kt */
        public static final class Sourcetable extends Answer {
            public static final Sourcetable INSTANCE = new Sourcetable();

            private Sourcetable() {
                super(null);
            }
        }

        /* compiled from: RtkCore.kt */
        public static final class Stream extends Answer {
            public static final Stream INSTANCE = new Stream();

            private Stream() {
                super(null);
            }
        }

        private Answer() {
        }
    }

    public final Mount nearestBase(List<Mount> list, double lat, double lon, double maxMiles, Set<String> skip) {
        Object minElem$iv;
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(skip, "skip");
        List<Mount> $this$filter$iv = list;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            boolean z = false;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            Mount it2 = (Mount) element$iv$iv;
            if (StringsKt.contains((CharSequence) it2.getFormat(), (CharSequence) "RTCM", true) && StringsKt.contains((CharSequence) it2.getFormat(), (CharSequence) "3", false) && !skip.contains(it2.getName())) {
                z = true;
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$map$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Mount it3 = (Mount) item$iv$iv;
            destination$iv$iv2.add(TuplesKt.to(it3, Double.valueOf(it3.milesFrom(lat, lon))));
        }
        Iterable $this$filter$iv2 = (List) destination$iv$iv2;
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            Pair it4 = (Pair) element$iv$iv2;
            double doubleValue = ((Number) it4.getSecond()).doubleValue();
            if ((!Double.isInfinite(doubleValue) && !Double.isNaN(doubleValue)) && ((Number) it4.getSecond()).doubleValue() <= maxMiles) {
                destination$iv$iv3.add(element$iv$iv2);
            }
        }
        Iterable $this$minByOrNull$iv = (List) destination$iv$iv3;
        Iterator iterator$iv = $this$minByOrNull$iv.iterator();
        if (iterator$iv.hasNext()) {
            minElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                Pair it5 = (Pair) minElem$iv;
                double minValue$iv = ((Number) it5.getSecond()).doubleValue();
                do {
                    Object e$iv = iterator$iv.next();
                    Pair it6 = (Pair) e$iv;
                    double v$iv = ((Number) it6.getSecond()).doubleValue();
                    if (Double.compare(minValue$iv, v$iv) > 0) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        } else {
            minElem$iv = null;
        }
        Pair pair = (Pair) minElem$iv;
        if (pair != null) {
            return (Mount) pair.getFirst();
        }
        return null;
    }

    public final Answer answer(String line) {
        Intrinsics.checkNotNullParameter(line, "line");
        String l = StringsKt.trim((CharSequence) line).toString();
        if (StringsKt.startsWith(l, "ICY 200", false) || new Regex("^HTTP/1\\.[01] 200").containsMatchIn(l)) {
            return Answer.Stream.INSTANCE;
        }
        if (StringsKt.startsWith(l, "SOURCETABLE 200", false)) {
            return Answer.Sourcetable.INSTANCE;
        }
        if (StringsKt.contains((CharSequence) l, (CharSequence) " 401", false)) {
            return new Answer.Refused("login refused: wrong username / password, or the subscription isn't active yet");
        }
        if (StringsKt.contains((CharSequence) l, (CharSequence) " 403", false)) {
            return new Answer.Refused("account not allowed on this mountpoint (or not activated yet)");
        }
        if (StringsKt.contains((CharSequence) l, (CharSequence) " 404", false)) {
            return new Answer.Refused("mountpoint not found");
        }
        if (StringsKt.contains((CharSequence) l, (CharSequence) " 503", false)) {
            return new Answer.Refused("caster busy (503)");
        }
        return l.length() == 0 ? new Answer.Refused("the caster closed the connection") : new Answer.Refused(StringsKt.take(l, 80));
    }

    /* compiled from: RtkCore.kt */
    public static final class Mount {
        private final String details;
        private final String format;
        private final double lat;
        private final double lon;
        private final String name;
        private final boolean needsGga;
        private final String place;

        public Mount(String name, String place, String format, String details, boolean needsGga, double lat, double lon) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(place, "place");
            Intrinsics.checkNotNullParameter(format, "format");
            Intrinsics.checkNotNullParameter(details, "details");
            this.name = name;
            this.place = place;
            this.format = format;
            this.details = details;
            this.needsGga = needsGga;
            this.lat = lat;
            this.lon = lon;
        }

        public final String getDetails() {
            return this.details;
        }

        public final String getFormat() {
            return this.format;
        }

        public final double getLat() {
            return this.lat;
        }

        public final double getLon() {
            return this.lon;
        }

        public final String getName() {
            return this.name;
        }

        public final boolean getNeedsGga() {
            return this.needsGga;
        }

        public final String getPlace() {
            return this.place;
        }

        public final double milesFrom(double lat0, double lon0) {
            double d = this.lat;
            if (!((Double.isInfinite(d) || Double.isNaN(d)) ? false : true)) {
                return Double.NaN;
            }
            double d2 = this.lon;
            if (!((Double.isInfinite(d2) || Double.isNaN(d2)) ? false : true)) {
                return Double.NaN;
            }
            if (!((Double.isInfinite(lat0) || Double.isNaN(lat0)) ? false : true)) {
                return Double.NaN;
            }
            if (this.lat == 0.0d) {
                if (this.lon == 0.0d) {
                    return Double.NaN;
                }
            }
            double dy = (this.lat - lat0) * 69.05d;
            double dx = (this.lon - lon0) * 69.17d * Math.cos(Math.toRadians(lat0));
            return Math.sqrt((dx * dx) + (dy * dy));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String sourcetable$lambda$12(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return StringsKt.trim((CharSequence) it).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean sourcetable$lambda$13(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return StringsKt.startsWith(it, "STR;", false);
    }

    public final List<Mount> sourcetable(String text, final double lat0, final double lon0) {
        Intrinsics.checkNotNullParameter(text, "text");
        Iterable out = SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.filter(SequencesKt.map(StringsKt.lineSequence(text), new Function1() { // from class: com.starpilot.starview.NtripCore$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String sourcetable$lambda$12;
                sourcetable$lambda$12 = NtripCore.sourcetable$lambda$12((String) obj);
                return sourcetable$lambda$12;
            }
        }), new Function1() { // from class: com.starpilot.starview.NtripCore$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                boolean sourcetable$lambda$13;
                sourcetable$lambda$13 = NtripCore.sourcetable$lambda$13((String) obj);
                return Boolean.valueOf(sourcetable$lambda$13);
            }
        }), new Function1() { // from class: com.starpilot.starview.NtripCore$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                NtripCore.Mount sourcetable$lambda$14;
                sourcetable$lambda$14 = NtripCore.sourcetable$lambda$14((String) obj);
                return sourcetable$lambda$14;
            }
        }));
        if ((Double.isInfinite(lat0) || Double.isNaN(lat0)) ? false : true) {
            final Comparator comparator = new Comparator() { // from class: com.starpilot.starview.NtripCore$sourcetable$$inlined$compareBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    NtripCore.Mount it = (NtripCore.Mount) t;
                    double d = it.milesFrom(lat0, lon0);
                    if (Double.isNaN(d)) {
                        d = Double.MAX_VALUE;
                    }
                    Double valueOf = Double.valueOf(d);
                    NtripCore.Mount it2 = (NtripCore.Mount) t2;
                    double d2 = it2.milesFrom(lat0, lon0);
                    return ComparisonsKt.compareValues(valueOf, Double.valueOf(Double.isNaN(d2) ? Double.MAX_VALUE : d2));
                }
            };
            return CollectionsKt.sortedWith(out, new Comparator() { // from class: com.starpilot.starview.NtripCore$sourcetable$$inlined$thenBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    int previousCompare = comparator.compare(t, t2);
                    if (previousCompare != 0) {
                        return previousCompare;
                    }
                    NtripCore.Mount it = (NtripCore.Mount) t;
                    String name = it.getName();
                    NtripCore.Mount it2 = (NtripCore.Mount) t2;
                    return ComparisonsKt.compareValues(name, it2.getName());
                }
            });
        }
        Iterable $this$sortedBy$iv = out;
        return CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.NtripCore$sourcetable$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                NtripCore.Mount it = (NtripCore.Mount) t;
                NtripCore.Mount it2 = (NtripCore.Mount) t2;
                return ComparisonsKt.compareValues(it.getName(), it2.getName());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Mount sourcetable$lambda$14(String line) {
        Intrinsics.checkNotNullParameter(line, "line");
        List f = StringsKt.split((CharSequence) line, new String[]{";"}, false, 0);
        if (f.size() >= 12) {
            if (!(((CharSequence) f.get(1)).length() == 0)) {
                String str = (String) f.get(1);
                String str2 = (String) f.get(2);
                String str3 = (String) f.get(3);
                String str4 = (String) f.get(4);
                boolean areEqual = Intrinsics.areEqual(f.get(11), "1");
                Double doubleOrNull = StringsKt.toDoubleOrNull((String) f.get(9));
                double doubleValue = doubleOrNull != null ? doubleOrNull.doubleValue() : Double.NaN;
                Double doubleOrNull2 = StringsKt.toDoubleOrNull((String) f.get(10));
                return new Mount(str, str2, str3, str4, areEqual, doubleValue, doubleOrNull2 != null ? doubleOrNull2.doubleValue() : Double.NaN);
            }
        }
        return null;
    }
}
