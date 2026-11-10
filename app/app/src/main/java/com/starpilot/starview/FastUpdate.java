package com.starpilot.starview;

import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: FastUpdate.kt */
public final class FastUpdate {
    public static final FastUpdate INSTANCE = new FastUpdate();
    private static final Map<String, Integer> STAGE_PCT = MapsKt.mapOf(TuplesKt.to("preparing", 5), TuplesKt.to("fetching", 25), TuplesKt.to("validating", 50), TuplesKt.to("applying", 70), TuplesKt.to("submodules", 85), TuplesKt.to("rebooting", 100), TuplesKt.to("complete", 100));
    public static final String START = "/api/update/fast";
    public static final String STATUS = "/api/update/fast/status";

    private FastUpdate() {
    }

    private final String str(JSONObject s, String k) {
        if (!s.has(k) || s.isNull(k)) {
            return "";
        }
        String optString = s.optString(k);
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        return StringsKt.trim((CharSequence) optString).toString();
    }

    public final boolean running(JSONObject s) {
        return s != null && s.optBoolean("running");
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    public final JSONObject from70(JSONObject var1) {
        String var17 = null; Object var18 = null; String var20 = null; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "r");
        JSONObject var13 = var1.optJSONObject("installed");
        JSONObject var11 = var1.optJSONObject("updater");
        JSONObject var12 = var1.optJSONObject("operations");
        if (var11 != null) {
            var1 = var11.optJSONObject("fast");
        } else {
            var1 = null;
        }

        String var5;
        String var9;
        label196: {
            var9 = "";
            if (var1 != null) {
                String var6 = INSTANCE.str(var1, "stage");
                var5 = var6;
                if (var6 != null) {
                    break label196;
                }
            }

            var5 = "";
        }

        String var31;
        label191: {
            if (var1 != null) {
                String var16 = INSTANCE.str(var1, "detail");
                var31 = var16;
                if (var16 != null) {
                    break label191;
                }
            }

            var31 = "";
        }

        JSONObject var7;
        if (var12 != null) {
            var7 = var12.optJSONObject("request");
        } else {
            var7 = null;
        }

        int var2;
        if (var7 != null && Intrinsics.areEqual(this.str(var7, "state"), "pending")) {
            var2 = 1;
        } else {
            var2 = 0;
        }

        boolean var3;
        if (var11 != null && Intrinsics.areEqual(this.str(var11, "state"), "updating...")) {
            var3 = true;
        } else {
            var3 = false;
        }

        JSONObject var14 = new JSONObject();
        boolean var4;
        if (((var3 != 0) || (var2 != 0)) && !Intrinsics.areEqual(var5, "error") && !Intrinsics.areEqual(var5, "rebooting")) {
            var4 = true;
        } else {
            var4 = false;
        }

        var14.put("running", var4);
        var14.put("stage", var5);
        label166:
        if (Intrinsics.areEqual(var5, "preparing")) {
            label164: {
                if (var11 != null) {
                    String var8 = INSTANCE.str(var11, "state");
                    var17 = var8;
                    if (var8 != null) {
                        break label164;
                    }
                }

                var17 = "";
            }

            switch (var17.hashCode()) {
                case 1268693460:
                    if (var17.equals("downloading...")) {
                        var18 = TuplesKt.to("Full update: downloading", 20);
                        break label166;
                    }
                    break;
                case 1392539220:
                    if (var17.equals("checking...")) {
                        var18 = TuplesKt.to("Full update: checking", 5);
                        break label166;
                    }
                    break;
                case 1941549184:
                    if (var17.equals("finalizing update...")) {
                        var18 = TuplesKt.to("Full update: finishing (copying it into place)", 70);
                        break label166;
                    }
            }

            var18 = null;
        } else {
            var18 = null;
        }

        String var32;
        label201: {
            if (var18 != null) {
                String var10 = (String)var18.getFirst();
                var32 = var10;
                if (var10 != null) {
                    break label201;
                }
            }

            var32 = var31;
            if (var32.length() == 0) {
                var2 = (((boolean)true) ? 1 : 0);
            } else {
                var2 = (((boolean)false) ? 1 : 0);
            }

            if ((var2 != 0)) {
                var32 = var5;
                if (var32.length() == 0) {
                    var2 = (((boolean)true) ? 1 : 0);
                } else {
                    var2 = (((boolean)false) ? 1 : 0);
                }

                if ((var2 != 0)) {
                    var32 = "starting";
                }

                var32 = var32;
            }

            var32 = var32;
        }

        var14.put("progressLabel", StringsKt.take(var32, 60));
        if (var18 != null) {
            var2 = ((Number)var18.getSecond()).intValue();
        } else {
            Integer var19 = STAGE_PCT.get(var5);
            if (var19 != null) {
                var2 = var19;
            } else {
                var2 = 0;
            }
        }

        label146: {
            var14.put("progressPercent", var2);
            if (var13 != null) {
                var32 = INSTANCE.str(var13, "commit");
                var20 = var32;
                if (var32 != null) {
                    break label146;
                }
            }

            var20 = "";
        }

        var14.put("localCommit", var20);
        String var22;
        if (Intrinsics.areEqual(var5, "error")) {
            var var21 = var31;
            boolean var26;
            if (var21.length() == 0) {
                var26 = true;
            } else {
                var26 = false;
            }

            if (var26) {
                var21 = "update failed";
            }

            var22 = var21;
        } else {
            label140: {
                if (var7 != null) {
                    boolean var27;
                    if (INSTANCE.str(var7, "error").length() > 0) {
                        var27 = true;
                    } else {
                        var27 = false;
                    }

                    if (var27) {
                        var22 = INSTANCE.str(var7, "error");
                        break label140;
                    }
                }

                var22 = var9;
            }
        }

        boolean var28;
        if (var22.length() > 0) {
            var28 = true;
        } else {
            var28 = false;
        }

        if (var28) {
            var14.put("lastError", var22);
        }

        if (var11 != null && var11.optBoolean("targetChangeFound")) {
            var28 = true;
        } else {
            var28 = false;
        }

        if (var28) {
            var14.put("updateAvailable", true);
        }

        if (var12 != null && !var12.optBoolean("canFastUpdate", true)) {
            if (INSTANCE.str(var12, "reason").length() > 0) {
                var28 = true;
            } else {
                var28 = false;
            }

            if (var28) {
                var14.put("reason", INSTANCE.str(var12, "reason"));
            }
        }

        return var14;
    }

    public final String summary(JSONObject s) {
        if (s == null) {
            return "checking…";
        }
        String local = StringsKt.take(str(s, "localCommit"), 7);
        String remote = StringsKt.take(str(s, "remoteCommit"), 7);
        if (running(s)) {
            String str = str(s, "progressLabel");
            if (str.length() == 0) {
                str = "updating";
            }
            return ((Object) str) + "  " + MathKt.roundToInt(s.optDouble("progressPercent", 0.0d)) + "%";
        }
        if (Intrinsics.areEqual(str(s, "stage"), "rebooting")) {
            return "rebooting…";
        }
        if (str(s, "lastError").length() > 0) {
            return "failed: " + StringsKt.take(str(s, "lastError"), 80);
        }
        if (str(s, "reason").length() > 0) {
            return "not now: " + StringsKt.take(str(s, "reason"), 60) + (local.length() > 0 ? "  (" + local + ")" : "");
        }
        String it = str(s, "versionPin");
        if (!(!(it.length() > 0) || Intrinsics.areEqual(it, "false") || Intrinsics.areEqual(it, "{}"))) {
            return "an older version is pinned - choose Latest in Galaxy";
        }
        if (str(s, "remoteError").length() > 0) {
            return "can't reach GitHub" + (local.length() > 0 ? "  (" + local + ")" : "");
        }
        if (s.optBoolean("updateAvailable")) {
            return "update available  " + local + " → " + remote;
        }
        return local.length() > 0 ? "up to date  " + local : "";
    }
}
