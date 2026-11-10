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
import kotlin.jvm.internal.Ref.DoubleRef;

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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public final SharedPlace.Place parse(String var1, String var2) {
        Object var18 = null; Object var64 = null; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "text");
        Iterable var7 = StringsKt.lines(var1);
        var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));
        Iterator var48 = var7.iterator();

        while (var48.hasNext()) {
            var6.add(StringsKt.trim((String & CharSequence)var48.next()).toString());
        }

        List var44 = var6;
        var7 = var44;
        var6 = new ArrayList();
        Iterator var9 = var7.iterator();

        while (true) {
            boolean var5 = var9.hasNext();
            boolean var4 = true;
            if (!var5) {
                List var61 = var6;
                String var59 = ((String) CollectionsKt.getOrNull(var61, 0));
                String var51 = "";
                String var46 = var59;
                if (var59 == null) {
                    var46 = "";
                }

                String var62 = ((String) CollectionsKt.getOrNull(var61, 1));
                var59 = var62;
                if (var62 == null) {
                    var59 = "";
                }

                if (var2 == null) {
                    String var63 = this.link(var1);
                    var2 = var63;
                    if (var63 == null) {
                        var1 = StringsKt.trim(var1).toString();
                        if (!StringsKt.startsWith(var1, "geo:", false)) {
                            var1 = null;
                        }

                        var2 = var1;
                        if (var1 == null) {
                            var2 = var51;
                        }
                    }
                }

                try {
                    var64 = URLDecoder.decode(var2, "UTF-8");
                } catch (Exception var13) {
                    var64 = var2;
                }

                DoubleRef var11 = new DoubleRef();
                var11.element = Double.NaN;
                DoubleRef var10 = new DoubleRef();
                var10.element = Double.NaN;
                String var52 = NUM;
                var1 = NUM;
                Regex var16 = new Regex("!3d" + var52 + "!4d" + var1);
                Intrinsics.checkNotNull(var64);
                if (!parse$take(var11, var10, var16.find(((CharSequence) var64), 0))) {
                    label150: {
                        String var53 = NUM;
                        var1 = NUM;
                        var18 = (new Regex("^geo:" + var53 + "," + var1)).find(((CharSequence) var64), 0);
                        if (var18 != null) {
                            boolean var37;
                            if (Double.parseDouble(var18.getGroupValues().get(1)) == 0.0) {
                                var37 = true;
                            } else {
                                var37 = false;
                            }

                            label146: {
                                if (var37) {
                                    if (Double.parseDouble(var18.getGroupValues().get(2)) == 0.0) {
                                        var37 = true;
                                    } else {
                                        var37 = false;
                                    }

                                    if (var37) {
                                        var37 = false;
                                        break label146;
                                    }
                                }

                                var37 = true;
                            }

                            if (var37) {
                                break label150;
                            }
                        }

                        var18 = null;
                    }

                    if (!parse$take(var11, var10, ((MatchResult) var18))) {
                        String var54 = NUM;
                        var1 = NUM;
                        if (!parse$take(
                            var11, var10, (new Regex("[?&](?:q|ll|query|destination|daddr|sll)=" + var54 + ",\\s*" + var1)).find(((CharSequence) var64), 0)
                        )) {
                            String var55 = NUM;
                            var1 = NUM;
                            if (!parse$take(var11, var10, (new Regex("geo:0,0\\?q=" + var55 + "," + var1)).find(((CharSequence) var64), 0))) {
                                String var56 = NUM;
                                var1 = NUM;
                                parse$take(var11, var10, (new Regex("@" + var56 + "," + var1)).find(((CharSequence) var64), 0));
                            }
                        }
                    }
                }

                boolean var40;
                if (var46.length() == 0) {
                    var40 = true;
                } else {
                    var40 = false;
                }

                Object var23;
                if (var40) {
                    var23 = (new Regex("/place/([^/@]+)")).find(((CharSequence) var64), 0);
                    if (var23 != null) {
                        var23 = StringsKt.replace(var23.getGroupValues().get(1), '+', ' ', false);
                    } else {
                        var23 = (new Regex("\\(([^)]+)\\)\\s*$")).find(((CharSequence) var64), 0);
                        if (var23 != null) {
                            var23 = var23.getGroupValues().get(1);
                        } else {
                            label134: {
                                var23 = (new Regex("[?&](?:q|query|address|daddr)=([^&]+)")).find(((CharSequence) var64), 0);
                                if (var23 != null) {
                                    List var26 = var23.getGroupValues();
                                    if (var26 != null) {
                                        String var27 = (String)var26.get(1);
                                        if (var27 != null) {
                                            String var57 = StringsKt.replace(var27, '+', ' ', false);
                                            if (var57 != null) {
                                                String var12 = NUM;
                                                String var28 = NUM;
                                                if (new Regex("^" + var12 + ",\\s*" + var28 + "$").matches(var57)) {
                                                    var57 = null;
                                                }

                                                var23 = var46;
                                                if (var57 != null) {
                                                    var23 = var57;
                                                }
                                                break label134;
                                            }
                                        }
                                    }
                                }

                                var23 = var46;
                            }
                        }
                    }
                } else {
                    var23 = var46;
                }

                Object var47 = var23;
                if (StringsKt.startsWith(var2, "geo:", false)) {
                    if (((CharSequence)var23).length() == 0) {
                        var40 = true;
                    } else {
                        var40 = false;
                    }

                    var47 = var23;
                    if (var40) {
                        MatchResult var32 = (new Regex("[?&]q=([^&(]+)")).find(((CharSequence) var64), 0);
                        var47 = var23;
                        if (var32 != null) {
                            var47 = StringsKt.trim(StringsKt.replace(var32.getGroupValues().get(1), '+', ' ', false)).toString();
                        }
                    }
                }

                Iterable var33 = CollectionsKt.listOf(new String[]{(String)var47, var59});
                var var29 = new ArrayList();

                for (Object var58 : var33) {
                    if (((String)var58).length() > 0) {
                        var40 = true;
                    } else {
                        var40 = false;
                    }

                    if (var40) {
                        var29.add(var58);
                    }
                }

                List var30 = var29;
                var2 = CollectionsKt.joinToString(var30, ", ", "", "", -1, "...", null);
                var23 = (CharSequence)var47;
                if (((CharSequence) var23).length() == 0) {
                    var40 = true;
                } else {
                    var40 = false;
                }

                if (var40) {
                    var23 = "Shared place";
                }

                return new SharedPlace.Place((String)var23, var59, var11.element, var10.element, var2);
            }

            Object var8 = var9.next();
            String var50 = (String)var8;
            boolean var3;
            if (var50.length() > 0) {
                var3 = true;
            } else {
                var3 = false;
            }

            if (var3 && !StringsKt.startsWith(var50, "http", false) && !StringsKt.startsWith(var50, "geo:", false)) {
                var3 = var4;
            } else {
                var3 = false;
            }

            if (var3) {
                var6.add(var8);
            }
        }
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
