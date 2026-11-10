package com.starpilot.starview;

import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* compiled from: SharedPlace.kt */
public final class SharedPlace {
    public static final SharedPlace INSTANCE = new SharedPlace();
    private static final String NUM = "(-?\\d{1,3}(?:\\.\\d+)?)";
    private static final Regex SHORT = new Regex("https?://(maps\\.app\\.goo\\.gl|goo\\.gl/maps|g\\.co/kgs)/\\S+");

    private SharedPlace() {
    }

    /* compiled from: SharedPlace.kt */
    public static final class Place {
        private final String address;
        private final double lat;
        private final double lon;
        private final String name;
        private final String query;

        public Place(String name, String address, double lat, double lon, String query) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(address, "address");
            Intrinsics.checkNotNullParameter(query, "query");
            this.name = name;
            this.address = address;
            this.lat = lat;
            this.lon = lon;
            this.query = query;
        }

        public final String getAddress() {
            return this.address;
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

        public final String getQuery() {
            return this.query;
        }

        public final boolean getHasCoords() {
            double d = this.lat;
            if ((Double.isInfinite(d) || Double.isNaN(d)) ? false : true) {
                double d2 = this.lon;
                if ((Double.isInfinite(d2) || Double.isNaN(d2)) ? false : true) {
                    double d3 = this.lat;
                    if (-90.0d <= d3 && d3 <= 90.0d) {
                        double d4 = this.lon;
                        if (-180.0d <= d4 && d4 <= 180.0d) {
                            if (!(this.lat == 0.0d)) {
                                return true;
                            }
                            if (!(this.lon == 0.0d)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }

    public final String link(String text) {
        String value;
        Intrinsics.checkNotNullParameter(text, "text");
        MatchResult find$default = (new Regex("https?://\\S+")).find(text, 0);
        if (find$default == null || (value = find$default.getValue()) == null) {
            return null;
        }
        return StringsKt.trimEnd(value, '.', ',', ')');
    }

    public final boolean isShort(String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        return SHORT.containsMatchIn(url);
    }

    public static /* synthetic */ Place parse$default(SharedPlace sharedPlace, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        return sharedPlace.parse(str, str2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00c6, code lost:
    
        if (r0 == null) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0188, code lost:
    
        if (r11 == false) goto L63;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Place parse(String text, String fullUrl) {
        String u;
        MatchResult it;
        List<String> groupValues;
        String str;
        String it2;
        Intrinsics.checkNotNullParameter(text, "text");
        Iterable $this$map$iv = StringsKt.lines(text);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            destination$iv$iv.add(StringsKt.trim((CharSequence) item$iv$iv).toString());
        }
        Iterable $this$filter$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        Iterator it3 = $this$filter$iv.iterator();
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            Object element$iv$iv = it3.next();
            String it4 = (String) element$iv$iv;
            if ((!(it4.length() > 0) || StringsKt.startsWith(it4, "http", false) || StringsKt.startsWith(it4, "geo:", false)) ? false : true) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        List lines = (List) destination$iv$iv2;
        String name = (String) CollectionsKt.getOrNull(lines, 0);
        String url = "";
        if (name == null) {
            name = "";
        }
        String str2 = (String) CollectionsKt.getOrNull(lines, 1);
        if (str2 == null) {
            str2 = "";
        }
        String address = str2;
        if (fullUrl == null) {
            String it5 = link(text);
            if (it5 == null) {
                it5 = StringsKt.trim((CharSequence) text).toString();
                if (!StringsKt.startsWith(it5, "geo:", false)) {
                    it5 = null;
                }
            }
            url = it5;
        } else {
            url = fullUrl;
        }
        try {
            u = URLDecoder.decode(url, "UTF-8");
        } catch (Exception e) {
            u = url;
        }
        Ref.DoubleRef lat = new Ref.DoubleRef();
        lat.element = Double.NaN;
        Ref.DoubleRef lon = new Ref.DoubleRef();
        lon.element = Double.NaN;
        Regex regex = new Regex("!3d" + NUM + "!4d" + NUM);
        Intrinsics.checkNotNull(u);
        if (!parse$take(lat, lon, regex.find(u, 0))) {
            MatchResult it6 = (new Regex("^geo:" + NUM + "," + NUM)).find(u, 0);
            if (it6 != null) {
                if (Double.parseDouble(it6.getGroupValues().get(1)) == 0.0d) {
                    boolean z = (Double.parseDouble(it6.getGroupValues().get(2)) > 0.0d ? 1 : (Double.parseDouble(it6.getGroupValues().get(2)) == 0.0d ? 0 : -1)) == 0 ? false : true;
                }
            }
            it6 = null;
            if (!parse$take(lat, lon, it6)) {
                if (!parse$take(lat, lon, (new Regex("[?&](?:q|ll|query|destination|daddr|sll)=" + NUM + ",\\s*" + NUM)).find(u, 0))) {
                    if (!parse$take(lat, lon, (new Regex("geo:0,0\\?q=" + NUM + "," + NUM)).find(u, 0))) {
                        parse$take(lat, lon, (new Regex("@" + NUM + "," + NUM)).find(u, 0));
                    }
                }
            }
        }
        if (name.length() == 0) {
            MatchResult it7 = (new Regex("/place/([^/@]+)")).find(u, 0);
            if (it7 != null) {
                name = StringsKt.replace(it7.getGroupValues().get(1), '+', ' ', false);
            } else {
                MatchResult it8 = (new Regex("\\(([^)]+)\\)\\s*$")).find(u, 0);
                if (it8 != null) {
                    name = it8.getGroupValues().get(1);
                } else {
                    MatchResult find$default = (new Regex("[?&](?:q|query|address|daddr)=([^&]+)")).find(u, 0);
                    if (find$default != null && (groupValues = find$default.getGroupValues()) != null && (str = groupValues.get(1)) != null && (it2 = StringsKt.replace(str, '+', ' ', false)) != null) {
                        if (new Regex("^" + NUM + ",\\s*" + NUM + "$").matches(it2)) {
                            it2 = null;
                        }
                        if (it2 != null) {
                            name = it2;
                        }
                    }
                }
            }
        }
        if (StringsKt.startsWith(url, "geo:", false)) {
            if ((name.length() == 0) && (it = (new Regex("[?&]q=([^&(]+)")).find(u, 0)) != null) {
                name = StringsKt.trim((CharSequence) StringsKt.replace(it.getGroupValues().get(1), '+', ' ', false)).toString();
            }
        }
        Iterable $this$filter$iv2 = CollectionsKt.listOf((Object[]) new String[]{name, address});
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            if (((String) element$iv$iv2).length() > 0) {
                destination$iv$iv3.add(element$iv$iv2);
            }
        }
        String query = CollectionsKt.joinToString((List) destination$iv$iv3, ", ", "", "", -1, "...", null);
        String str3 = name;
        if (str3.length() == 0) {
            str3 = "Shared place";
        }
        return new Place(str3, address, lat.element, lon.element, query);
    }

    private static final boolean parse$take(Ref.DoubleRef lat, Ref.DoubleRef lon, MatchResult m) {
        if (m == null) {
            return false;
        }
        lat.element = Double.parseDouble(m.getGroupValues().get(1));
        lon.element = Double.parseDouble(m.getGroupValues().get(2));
        return true;
    }

    public final String follow(String url) {
        String uri;
        Intrinsics.checkNotNullParameter(url, "url");
        try {
            OkHttpClient http = new OkHttpClient.Builder().followRedirects(false).callTimeout(15L, TimeUnit.SECONDS).build();
            String cur = url;
            int hops = 0;
            while (true) {
                int hops2 = hops + 1;
                if (hops >= 6) {
                    break;
                }
                Response execute = http.newCall(new Request.Builder().url(cur).header("User-Agent", "Mozilla/5.0 (Linux; Android 14) StarView").build()).execute();
                try {
                    Response r = execute;
                    String header$default = r.isRedirect() ? r.header("Location", null) : null;
                    CloseableKt.closeFinally(execute, null);
                    String loc = header$default;
                    if (loc != null) {
                        if (StringsKt.startsWith(loc, "http", false)) {
                            uri = loc;
                        } else {
                            uri = new URI(cur).resolve(loc).toString();
                            Intrinsics.checkNotNullExpressionValue(uri, "toString(...)");
                        }
                        cur = uri;
                        if (!isShort(cur) && (StringsKt.contains((CharSequence) cur, (CharSequence) "!3d", false) || StringsKt.contains((CharSequence) cur, (CharSequence) "@", false) || StringsKt.contains((CharSequence) cur, (CharSequence) "q=", false))) {
                            break;
                        }
                        hops = hops2;
                    } else {
                        break;
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(execute, th);
                        throw th2;
                    }
                }
            }
            return cur;
        } catch (Exception e) {
            return null;
        }
    }
}
