package com.starpilot.starview;

import com.starpilot.starview.BlinkerTurn;
import com.starpilot.starview.OsmRoads;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: BlinkerTurn.kt */
public final class BlinkerTurn {
    public static final double AHEAD_M = 76.0d;
    public static final double BEHIND_M = 8.0d;
    public static final double KEEP_MPS = 11.176d;
    public static final String KEY = "blinker_turn";
    public static final double PASSED_M = 25.0d;
    public static final double START_MPS = 8.9408d;
    private static volatile Boolean commaOk;
    private static long lastSentMs;
    private static volatile boolean mapped;
    private static String sent;
    public static final BlinkerTurn INSTANCE = new BlinkerTurn();
    private static final Set<String> SKIP = SetsKt.setOf(new String[]{"service", "track", "path", "footway", "cycleway", "pedestrian", "steps", "bridleway", "construction", "proposed"});
    private static final Keeper keeper = new Keeper();

    private BlinkerTurn() {
    }

    /* compiled from: BlinkerTurn.kt */
    public static final class Corner {
        private final double ahead;
        private final double e;
        private final double n;
        private final String name;
        private final int side;

        public Corner(double e, double n, double ahead, int side, String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.e = e;
            this.n = n;
            this.ahead = ahead;
            this.side = side;
            this.name = name;
        }

        public final double getAhead() {
            return this.ahead;
        }

        public final double getE() {
            return this.e;
        }

        public final double getN() {
            return this.n;
        }

        public final String getName() {
            return this.name;
        }

        public final int getSide() {
            return this.side;
        }
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final List<BlinkerTurn.Corner> corners(List<OsmRoads.Way> var1, OsmRoads.Way var2, double var3, double var5, double var7, int var9) {
        Intrinsics.checkNotNullParameter(var1, "ways");
        Intrinsics.checkNotNullParameter(var2, "our");
        double var10 = Math.cos(var7);
        double var14 = Math.sin(var7);
        Iterable var29 = var1;
        var var28 = new ArrayList();

        for (Object var29$it : (Iterable) var29) {
            var29 = (Iterable) var29$it;
            boolean var41;
            label200: {
                Object var30 = (OsmRoads.Way)var29;
                if (var30.getId() != var2.getId()) {
                    if (var2.getName().length() > 0) {
                        var41 = true;
                    } else {
                        var41 = false;
                    }

                    if (!var41 || !Intrinsics.areEqual(var30.getKind(), var2.getKind()) || !OsmRoads.INSTANCE.sameStreet(var30.getName(), var2.getName())) {
                        var41 = false;
                        break label200;
                    }
                }

                var41 = true;
            }

            if (var41) {
                var28.add(var29);
            }
        }

        List var51 = var28;
        Collection var56 = var51;
        Object var52 = var56;
        if (var56.isEmpty()) {
            var52 = CollectionsKt.<OsmRoads.Way>listOf(var2);
        }

        Object var34 = (List)var52;
        var52 = new ArrayList();
        Iterator var57 = var1.iterator();
        Object var33 = (ArrayList)var52;

        while (var57.hasNext()) {
            var52 = (OsmRoads.Way)var57.next();
            if (var52.getE().length >= 2 && !SKIP.contains(var52.getKind())) {
                String var58 = var52.getKind();
                byte var21 = 0;
                if (!StringsKt.startsWith(var58, "motorway", false)) {
                    var var59 = var34;
                    int var43;
                    if (var59 instanceof Collection && ((Collection)var59).isEmpty()) {
                        var43 = 0;
                    } else {
                        label222: {
                            Iterator var60 = var59.iterator();

                            while (var60.hasNext()) {
                                if (((OsmRoads.Way)var60.next()).getId() == var52.getId()) {
                                    var43 = (((boolean)true) ? 1 : 0);
                                } else {
                                    var43 = (((boolean)false) ? 1 : 0);
                                }

                                if ((var43 != 0)) {
                                    var43 = 1;
                                    break label222;
                                }
                            }

                            var43 = 0;
                        }
                    }

                    if ((var43 == 0)) {
                        int var23 = var52.getE().length;
                        int var22 = 0;
                        var43 = var21;

                        for (int var45 = var23; var22 < var45; var22++) {
                            double var16 = var52.getE()[var22] - var3;
                            var7 = var52.getN()[var22] - var5;
                            double var12 = var16 * var10 + var7 * var14;
                            if (!(var12 < -8.0) && !(var12 > 76.0) && !(Math.abs(var16 * var14 - var7 * var10) > 25.0)) {
                                Iterator var61 = ((List) var34).iterator();
                                if (!var61.hasNext()) {
                                    throw new NoSuchElementException();
                                }

                                OsmRoads.Way var62 = (OsmRoads.Way)var61.next();
                                LaneRamps var32 = LaneRamps.INSTANCE;
                                var7 = var52.getE()[var22];
                                var16 = var52.getN()[var22];
                                var7 = var32.distTo(var62, var7, var16, var10, var14);

                                while (var61.hasNext()) {
                                    var62 = (OsmRoads.Way)var61.next();
                                    var7 = Math.min(var7, LaneRamps.INSTANCE.distTo(var62, var52.getE()[var22], var52.getN()[var22], var10, var14));
                                }

                                if (!(var7 > 4.0)) {
                                    for (int var46 = var43; var46 < 2; var46++) {
                                        int var26 = new int[]{-1, 1}[var46];
                                        if ((var52.getOneway() != 1 || var26 >= 0) && (var52.getOneway() != -1 || var26 <= 0)) {
                                            int var25 = var22 + var26;
                                            int var24;
                                            if (var25 >= 0 && var25 < var52.getE().length) {
                                                var24 = 1;
                                            } else {
                                                var24 = var43;
                                            }

                                            if (var24 != 0) {
                                                var24 = var25;

                                                while (true) {
                                                    int var27 = var52.getE().length;
                                                    var25 = var24 + var26;
                                                    int var50;
                                                    if (var25 >= 0 && var25 < var27) {
                                                        var50 = 1;
                                                    } else {
                                                        var50 = var43;
                                                    }

                                                    if (var50 == 0
                                                        || !(Math.hypot(var52.getE()[var24] - var52.getE()[var22], var52.getN()[var24] - var52.getN()[var22]) < 10.0)) {
                                                        var7 = var52.getE()[var24] - var52.getE()[var22];
                                                        double var18 = var52.getN()[var24] - var52.getN()[var22];
                                                        var16 = Math.hypot(var7, var18);
                                                        if (!(var16 < 3.0) && !(Math.abs((var7 * var10 + var18 * var14) / var16) > 0.85)) {
                                                            byte var48;
                                                            if (var7 * var14 - var18 * var10 > 0.0) {
                                                                var48 = 1;
                                                            } else {
                                                                var48 = -1;
                                                            }

                                                            if (var48 == var9) {
                                                                ((ArrayList) var33).add(new BlinkerTurn.Corner(var52.getE()[var22], var52.getN()[var22], var12, var48, var52.getName()));
                                                            }
                                                        }
                                                        break;
                                                    }

                                                    var24 += var26;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return CollectionsKt.sortedWith(var33, new Comparator() {
            @Override
            public final int compare(Object var1, Object var2x) {
                return ComparisonsKt.compareValues(Math.abs(((BlinkerTurn.Corner)var1).getAhead()), Math.abs(((BlinkerTurn.Corner)var2x).getAhead()));
            }
        });
    }

    /* compiled from: BlinkerTurn.kt */
    public static final class Keeper {
        private Corner corner;
        private double passedE;
        private double passedN;
        private int passedSide;
        private int side;
        private boolean turned;
        private double yaw0 = Double.NaN;

        public final int getSide() {
            return this.side;
        }

        public final Corner getCorner() {
            return this.corner;
        }

        public final void clear() {
            this.side = 0;
            this.corner = null;
            this.yaw0 = Double.NaN;
        }

        /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
        public final int tick(
            boolean var1,
            int var2,
            double var3,
            boolean var5,
            Pair<Double, Double> var6,
            double var7,
            Function4<? super Double, ? super Double, ? super Double, ? super Integer, ? extends List<BlinkerTurn.Corner>> var9
        ) {
        Object var27 = null; // declarations Vineflower left out
            Intrinsics.checkNotNullParameter(var9, "find");
            if (!var1 || var2 == 0 || var5 || var3 > 11.176 || var2 != this.side && this.side != 0) {
                this.clear();
                if (var2 == 0) {
                    this.passedSide = 0;
                    this.turned = false;
                }
            }

            if (var1 && var2 != 0 && !var5 && !this.turned) {
                BlinkerTurn.Corner var23 = this.corner;
                if (this.side != 0 && var23 != null) {
                    if (var6 == null) {
                        return this.side;
                    }

                    double var10 = ((Number)var6.component1()).doubleValue();
                    double var20 = ((Number)var6.component2()).doubleValue();
                    double var14 = Math.cos(this.yaw0);
                    double var12 = Math.sin(this.yaw0);
                    double var16 = var23.getE();
                    double var18 = var23.getN();
                    if (!(Math.hypot(var10 - var23.getE(), var20 - var23.getN()) > 25.0) || !((var10 - var16) * var14 + (var20 - var18) * var12 > -10.0)) {
                        return this.side;
                    }

                    if (!Double.isNaN(var7) && !(Math.abs(Math.IEEEremainder(var7 - this.yaw0, Math.PI * 2)) > Math.toRadians(30.0))) {
                        var1 = false;
                    } else {
                        var1 = true;
                    }

                    this.turned = var1;
                    this.passedSide = this.side;
                    this.passedE = var23.getE();
                    this.passedN = var23.getN();
                    this.clear();
                    if (this.turned) {
                        return this.side;
                    }
                }

                if (!(var3 > 8.9408) && var6 != null && !Double.isNaN(var7)) {
                    Iterable var26 = (Iterable)var9.invoke(var6.getFirst(), var6.getSecond(), var7, var2);
                    Iterator var30 = var26.iterator();

                    while (true) {
                        if (!var30.hasNext()) {
                            var27 = null;
                            break;
                        }

                        Object var29 = var30.next();
                        BlinkerTurn.Corner var24 = (BlinkerTurn.Corner)var29;
                        boolean var22;
                        if (this.passedSide == var2 && Math.hypot(var24.getE() - this.passedE, var24.getN() - this.passedN) < 15.0) {
                            var22 = false;
                        } else {
                            var22 = true;
                        }

                        if (var22) {
                            var27 = var29;
                            break;
                        }
                    }

                    BlinkerTurn.Corner var28 = (BlinkerTurn.Corner)var27;
                    if (var28 == null) {
                        return this.side;
                    } else {
                        this.side = var2;
                        this.corner = var28;
                        this.yaw0 = var7;
                        return this.side;
                    }
                } else {
                    return this.side;
                }
            } else {
                return this.side;
            }
        }
    }

    public final Keeper getKeeper() {
        return keeper;
    }

    public final Boolean getCommaOk() {
        return commaOk;
    }

    public final boolean getMapped() {
        return mapped;
    }

    public static /* synthetic */ void tick$default(BlinkerTurn blinkerTurn, Telemetry telemetry, UiSettings uiSettings, long j, int i, Object obj) {
        if ((i & 4) != 0) {
            j = System.currentTimeMillis();
        }
        blinkerTurn.tick(telemetry, uiSettings, j);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final void tick(Telemetry var1, UiSettings var2, long var3) {
        Intrinsics.checkNotNullParameter(var1, "t");
        Intrinsics.checkNotNullParameter(var2, "s");
        boolean var11 = var1.getLeftBlinker();
        boolean var12 = true;
        byte var8;
        if (var11 && !var1.getRightBlinker()) {
            var8 = -1;
        } else if (var1.getRightBlinker() && !var1.getLeftBlinker()) {
            var8 = 1;
        } else {
            var8 = 0;
        }

        if (var1.navState() != null) {
            var11 = true;
        } else {
            var11 = false;
        }

        OsmRoads.Fix var15 = OsmRoads.INSTANCE.getLastFix();
        boolean var9;
        if (var15 != null && !Double.isNaN(OsmRoads.INSTANCE.getLat0()) && System.nanoTime() - var15.getTNs() < 3000000000L) {
            var9 = true;
        } else {
            var9 = false;
        }

        Pair var22;
        if (var9) {
            OsmRoads var14 = OsmRoads.INSTANCE;
            Intrinsics.checkNotNull(var15);
            var22 = var14.toLocal(var15.getLat(), var15.getLon());
        } else {
            var22 = null;
        }

        double var5;
        label109: {
            if (var9) {
                Intrinsics.checkNotNull(var15);
                if (var15.getSpeed() > 2.0F) {
                    float var7 = var15.getBearingDeg();
                    if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
                        var9 = true;
                    } else {
                        var9 = false;
                    }

                    if (var9) {
                        var5 = (Math.PI / 2) - Math.toRadians(var15.getBearingDeg());
                        break label109;
                    }
                }
            }

            var5 = Double.NaN;
        }

        boolean var13 = var2.get("blinker_turn");
        int var10 = keeper.tick(var13, var8, var1.getVEgo(), var11, var22, var5, new BlinkerTurn$$ExternalSyntheticLambda0());
        if (var22 != null && !Double.isNaN(var5)) {
            label93: {
                OsmRoads.Match var16 = OsmRoads.INSTANCE.matchSeg(((Number)var22.getFirst()).doubleValue(), ((Number)var22.getSecond()).doubleValue(), var5);
                if (var16 != null) {
                    if (var16.getDist() < 15.0) {
                        var9 = true;
                    } else {
                        var9 = false;
                    }

                    if (var9) {
                        break label93;
                    }
                }

                var12 = false;
            }

            mapped = var12;
        } else if (var22 == null) {
            mapped = false;
        }

        String var17;
        if (!var13 || var8 == 0 || var11) {
            var17 = null;
        } else if (var10 != 0) {
            if (var10 < 0) {
                var17 = "left";
            } else {
                var17 = "right";
            }
        } else if (mapped) {
            var17 = "none";
        } else {
            var17 = null;
        }

        DataClient var18 = Link.INSTANCE.getData();
        if (var18 != null) {
            if (!var18.getConnected()) {
                sent = null;
            } else if (!Intrinsics.areEqual(var17, sent) || var17 != null && var3 - lastSentMs >= 500L) {
                sent = var17;
                lastSentMs = var3;
                var18.request("turn_intent", MapsKt.mapOf(TuplesKt.to("side", var17)), new BlinkerTurn$$ExternalSyntheticLambda1());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List tick$lambda$6(double e, double n, double y, int sd) {
        OsmRoads.Way our;
        OsmRoads.Match it = OsmRoads.INSTANCE.matchSeg(e, n, y);
        if (it != null) {
            if (!(it.getDist() < 15.0d)) {
                it = null;
            }
            if (it != null && (our = it.getWay()) != null) {
                try {
                    return INSTANCE.corners(OsmRoads.INSTANCE.near(e, n, 150.0d), our, e, n, y, sd);
                } catch (Exception e2) {
                    return CollectionsKt.emptyList();
                }
            }
        }
        return CollectionsKt.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tick$lambda$8(JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        commaOk = Boolean.valueOf(ack.optBoolean("ok"));
        return Unit.INSTANCE;
    }

    public final String label() {
        switch (keeper.getSide()) {
            case -1:
                return "turn LEFT kept";
            case 0:
            default:
                return null;
            case 1:
                return "turn RIGHT kept";
        }
    }
}
