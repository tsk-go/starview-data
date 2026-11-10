package com.starpilot.starview;

import com.starpilot.starview.LaneRamps;
import com.starpilot.starview.OsmRoads;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: LaneRamps.kt */
public final class LaneRamps {
    public static final double EXIT_AHEAD = 320.0d;
    public static final LaneRamps INSTANCE = new LaneRamps();
    public static final double MERGE_BEHIND = 260.0d;

    private LaneRamps() {
    }

    /* compiled from: LaneRamps.kt */
    public static final class Ramp {
        private final double dist;
        private final String kind;
        private final int side;

        public Ramp(String kind, int side, double dist) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.kind = kind;
            this.side = side;
            this.dist = dist;
        }

        public final double getDist() {
            return this.dist;
        }

        public final String getKind() {
            return this.kind;
        }

        public final int getSide() {
            return this.side;
        }

        public final long getFeet() {
            return Math.round((Math.abs(this.dist) * 3.28084d) / 10.0d) * 10;
        }

        public final String text() {
            StringBuilder append;
            String str;
            String str2 = Intrinsics.areEqual(this.kind, "exit") ? "exit" : "merge";
            String str3 = this.side < 0 ? "left" : "right";
            double d = this.dist;
            long feet = getFeet();
            if (d >= 0.0d) {
                append = new StringBuilder().append(", ").append(feet);
                str = " ft ahead";
            } else {
                append = new StringBuilder().append(", ").append(feet);
                str = " ft behind";
            }
            return str2 + " on the " + str3 + append.append(str).toString();
        }

        public final String code() {
            return this.kind + "," + (this.side < 0 ? "L" : "R") + "," + Math.round(this.dist);
        }
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;Type inference failed;incorrect) */
    public final List<LaneRamps.Ramp> scan(List<OsmRoads.Way> var1, OsmRoads.Way var2, double var3, double var5, double var7) {
        Intrinsics.checkNotNullParameter(var1, "ways");
        Intrinsics.checkNotNullParameter(var2, "our");
        long var25 = var2.getId();
        Iterable var30 = var1;
        var var29 = new ArrayList();

        for (Object var31 : var30) {
            boolean var44;
            label155: {
                label154: {
                    OsmRoads.Way var54 = (OsmRoads.Way)var31;
                    if (var54.getId() != var25) {
                        if (StringsKt.endsWith(var54.getKind(), "_link", false) || !Intrinsics.areEqual(var54.getKind(), var2.getKind())) {
                            break label154;
                        }

                        if (var2.getName().length() > 0) {
                            var44 = true;
                        } else {
                            var44 = false;
                        }

                        if (!var44 || !OsmRoads.INSTANCE.sameStreet(var54.getName(), var2.getName())) {
                            break label154;
                        }
                    }

                    var44 = true;
                    break label155;
                }

                var44 = false;
            }

            if (var44) {
                var29.add(var31);
            }
        }

        List var48 = var29;
        Collection var55 = var48;
        Iterator var49 = var55;
        if (var55.isEmpty()) {
            var49 = CollectionsKt.<OsmRoads.Way>listOf(var2);
        }

        List var35 = (List)var49;
        double var9 = Math.cos(var7);
        double var13 = Math.sin(var7);
        HashMap var56 = new HashMap();
        Iterator var57 = var1.iterator();
        var7 = var9;
        var1 = var35;

        while (var57.hasNext()) {
            var2 = (OsmRoads.Way)var57.next();
            if (var2.getId() != var25 && StringsKt.endsWith(var2.getKind(), "_link", false) && var2.getE().length >= 2) {
                for (int var45 = 0; var45 < 2; var45++) {
                    int var22;
                    if (var45 == 0) {
                        var22 = 0;
                    } else {
                        var22 = var2.getE().length - 1;
                    }

                    double var11 = var2.getE()[var22] - var3;
                    var9 = var2.getN()[var22] - var5;
                    double var15 = var11 * var7 + var9 * var13;
                    if (!(Math.abs(var11 * var13 - var9 * var7) > 40.0)) {
                        var49 = var1.iterator();
                        if (!var49.hasNext()) {
                            throw new NoSuchElementException();
                        }

                        OsmRoads.Way var58 = (OsmRoads.Way)var49.next();
                        var9 = INSTANCE.distTo(var58, var2.getE()[var22], var2.getN()[var22], var7, var13);

                        while (var49.hasNext()) {
                            var58 = (OsmRoads.Way)var49.next();
                            var9 = Math.min(var9, INSTANCE.distTo(var58, var2.getE()[var22], var2.getN()[var22], var7, var13));
                        }

                        long var27 = var25;
                        if (var9 > 10.0) {
                            var25 = var25;
                        } else {
                            int var23;
                            if (var45 == 0) {
                                var23 = var2.getE().length - 1;
                            } else {
                                var23 = 0;
                            }

                            double[] var51 = var2.getE();
                            int var24;
                            if (var45 == 0) {
                                var24 = Math.min(var51.length - 1, 1);
                            } else {
                                var24 = Math.max(0, var51.length - 2);
                            }

                            double var17 = var2.getE()[var23] - var2.getE()[var22];
                            var9 = var2.getN()[var23];
                            var11 = var2.getN()[var22];
                            double var19 = var9 - var11;
                            if (Math.hypot(var17, var19) < 30.0) {
                                var25 = var25;
                            } else {
                                var11 = (var2.getE()[var24] - var2.getE()[var22]) * var13 - (var2.getN()[var24] - var2.getN()[var22]) * var7;
                                var9 = var11;
                                if (Math.abs(var11) < 1.0) {
                                    var9 = var17 * var13 - var19 * var7;
                                }

                                byte var46;
                                if (var9 > 0.0) {
                                    var46 = 1;
                                } else {
                                    var46 = -1;
                                }

                                String var52;
                                if (var17 * var7 + var19 * var13 > 0.0) {
                                    var52 = "exit";
                                } else {
                                    var52 = "merge";
                                }

                                boolean var47;
                                if (Intrinsics.areEqual(var52, "exit") ? !(-20.0 <= var15) || !(var15 <= 320.0) : !(-260.0 <= var15) || !(var15 <= 60.0)) {
                                    var47 = false;
                                } else {
                                    var47 = true;
                                }

                                if (var47) {
                                    LaneRamps.Ramp var60 = new LaneRamps.Ramp(var52, var46, var15);
                                    LaneRamps.Ramp var53 = (LaneRamps.Ramp)var56.get(Integer.valueOf(var46));
                                    if (var53 != null) {
                                        var25 = var25;
                                        if (!(Math.abs(var60.getDist()) < Math.abs(var53.getDist()))) {
                                            continue;
                                        }
                                    }

                                    var56.put(Integer.valueOf(var46), var60);
                                    var25 = var27;
                                } else {
                                    var25 = var25;
                                }
                            }
                        }
                    }
                }
            }
        }

        Collection var34 = var56.values();
        Intrinsics.checkNotNullExpressionValue(var34, "<get-values>(...)");
        return CollectionsKt.sortedWith(var34, new Comparator() {
            @Override
            public final int compare(T var1, T var2x) {
                return ComparisonsKt.compareValues(((LaneRamps.Ramp)var1).getSide(), ((LaneRamps.Ramp)var2x).getSide());
            }
        });
    }

    public final double distTo(OsmRoads.Way w, double x, double y, double hx, double hy) {
        int i;
        int i2;
        Intrinsics.checkNotNullParameter(w, "w");
        double d = Double.MAX_VALUE;
        int i3 = 0;
        int length = w.getE().length - 1;
        while (i3 < length) {
            double ax = w.getE()[i3];
            double ay = w.getN()[i3];
            double dx = w.getE()[i3 + 1] - ax;
            double dy = w.getN()[i3 + 1] - ay;
            double l2 = (dx * dx) + (dy * dy);
            if (w.getOneway() == 0 || l2 <= 1.0E-6d) {
                i = i3;
            } else {
                i = i3;
                if ((w.getOneway() * ((dx * hx) + (dy * hy))) / Math.sqrt(l2) < 0.3d) {
                    i2 = length;
                    i3 = i + 1;
                    length = i2;
                }
            }
            double t = l2 < 1.0E-6d ? 0.0d : RangesKt.coerceIn((((x - ax) * dx) + ((y - ay) * dy)) / l2, 0.0d, 1.0d);
            i2 = length;
            d = Math.min(d, Math.hypot(x - ((t * dx) + ax), y - ((t * dy) + ay)));
            i3 = i + 1;
            length = i2;
        }
        return d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence codes$lambda$4(Ramp it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.code();
    }

    public final String codes(List<Ramp> l) {
        Intrinsics.checkNotNullParameter(l, "l");
        return CollectionsKt.joinToString(l, ";", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.LaneRamps$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence codes$lambda$4;
                codes$lambda$4 = LaneRamps.codes$lambda$4((LaneRamps.Ramp) obj);
                return codes$lambda$4;
            }
        });
    }

    public final List<Ramp> parse(String s) {
        Ramp ramp;
        Iterable $this$mapNotNull$iv;
        int $i$f$mapNotNull;
        Iterable $this$mapNotNullTo$iv$iv;
        int $i$f$mapNotNullTo;
        Intrinsics.checkNotNullParameter(s, "s");
        int i = 1;
        char c = 0;
        Iterable $this$mapNotNull$iv2 = StringsKt.split((CharSequence) s, new String[]{";"}, false, 0);
        int $i$f$mapNotNull2 = 0;
        Collection destination$iv$iv = new ArrayList();
        Iterable $this$mapNotNullTo$iv$iv2 = $this$mapNotNull$iv2;
        int $i$f$mapNotNullTo2 = 0;
        for (Object element$iv$iv$iv : $this$mapNotNullTo$iv$iv2) {
            String p = (String) element$iv$iv$iv;
            String[] strArr = new String[i];
            strArr[c] = ",";
            List f = StringsKt.split((CharSequence) p, strArr, false, 0);
            if (f.size() < 3) {
                ramp = null;
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
                $i$f$mapNotNull = $i$f$mapNotNull2;
                $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                $i$f$mapNotNullTo = $i$f$mapNotNullTo2;
            } else {
                String str = (String) f.get(0);
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
                $i$f$mapNotNull = $i$f$mapNotNull2;
                int i2 = Intrinsics.areEqual(f.get(1), "L") ? -1 : 1;
                Double doubleOrNull = StringsKt.toDoubleOrNull((String) f.get(2));
                double doubleValue = doubleOrNull != null ? doubleOrNull.doubleValue() : 0.0d;
                $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                $i$f$mapNotNullTo = $i$f$mapNotNullTo2;
                ramp = new Ramp(str, i2, doubleValue);
            }
            if (ramp != null) {
                destination$iv$iv.add(ramp);
            }
            $this$mapNotNullTo$iv$iv2 = $this$mapNotNullTo$iv$iv;
            $i$f$mapNotNull2 = $i$f$mapNotNull;
            $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            $i$f$mapNotNullTo2 = $i$f$mapNotNullTo;
            i = 1;
            c = 0;
        }
        return (List) destination$iv$iv;
    }
}
