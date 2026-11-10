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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0042, code lost:
    
        if (r10 == null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x016c, code lost:
    
        if (r0 != null) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0033, code lost:
    
        if (r9 == null) goto L10;
     */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01fa  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final JSONObject from70(JSONObject r) {
        String stage;
        String detail;
        Pair sub;
        String str;
        int intValue;
        String str2;
        boolean z;
        boolean z2;
        String str3;
        Intrinsics.checkNotNullParameter(r, "r");
        JSONObject inst = r.optJSONObject("installed");
        JSONObject upd = r.optJSONObject("updater");
        JSONObject ops = r.optJSONObject("operations");
        JSONObject fast = upd != null ? upd.optJSONObject("fast") : null;
        String str4 = "";
        if (fast != null) {
            JSONObject it = fast;
            stage = INSTANCE.str(it, "stage");
        }
        stage = "";
        if (fast != null) {
            JSONObject it2 = fast;
            detail = INSTANCE.str(it2, "detail");
        }
        detail = "";
        JSONObject req = ops != null ? ops.optJSONObject("request") : null;
        boolean pending = req != null && Intrinsics.areEqual(str(req, "state"), "pending");
        boolean updating = upd != null && Intrinsics.areEqual(str(upd, "state"), "updating...");
        JSONObject $this$from70_u24lambda_u248 = new JSONObject();
        $this$from70_u24lambda_u248.put("running", ((!updating && !pending) || Intrinsics.areEqual(stage, "error") || Intrinsics.areEqual(stage, "rebooting")) ? false : true);
        $this$from70_u24lambda_u248.put("stage", stage);
        if (Intrinsics.areEqual(stage, "preparing")) {
            if (upd == null || (str3 = INSTANCE.str(upd, "state")) == null) {
                str3 = "";
            }
            switch (str3.hashCode()) {
                case 1268693460:
                    if (str3.equals("downloading...")) {
                        sub = TuplesKt.to("Full update: downloading", 20);
                        break;
                    }
                    sub = null;
                    break;
                case 1392539220:
                    if (str3.equals("checking...")) {
                        sub = TuplesKt.to("Full update: checking", 5);
                        break;
                    }
                    sub = null;
                    break;
                case 1941549184:
                    if (str3.equals("finalizing update...")) {
                        sub = TuplesKt.to("Full update: finishing (copying it into place)", 70);
                        break;
                    }
                    sub = null;
                    break;
                default:
                    sub = null;
                    break;
            }
        } else {
            sub = null;
        }
        if (sub == null || (str = (String) sub.getFirst()) == null) {
            String str5 = detail;
            if (str5.length() == 0) {
                String str6 = stage;
                if (str6.length() == 0) {
                    str6 = "starting";
                }
                str5 = str6;
            }
            str = str5;
        }
        $this$from70_u24lambda_u248.put("progressLabel", StringsKt.take(str, 60));
        if (sub != null) {
            intValue = ((Number) sub.getSecond()).intValue();
        } else {
            Integer num = STAGE_PCT.get(stage);
            intValue = num != null ? num.intValue() : 0;
        }
        $this$from70_u24lambda_u248.put("progressPercent", intValue);
        if (inst != null) {
            str2 = INSTANCE.str(inst, "commit");
        }
        str2 = "";
        $this$from70_u24lambda_u248.put("localCommit", str2);
        if (Intrinsics.areEqual(stage, "error")) {
            String str7 = detail;
            if (str7.length() == 0) {
                str7 = "update failed";
            }
            str4 = str7;
        } else if (req != null) {
            if (INSTANCE.str(req, "error").length() > 0) {
                str4 = INSTANCE.str(req, "error");
            }
        }
        String err = str4;
        if (err.length() > 0) {
            $this$from70_u24lambda_u248.put("lastError", err);
        }
        if (upd != null) {
            z = true;
            if (upd.optBoolean("targetChangeFound")) {
                z2 = true;
                if (z2) {
                    $this$from70_u24lambda_u248.put("updateAvailable", z);
                }
                if (ops != null && !ops.optBoolean("canFastUpdate", true)) {
                    if (INSTANCE.str(ops, "reason").length() <= 0) {
                        $this$from70_u24lambda_u248.put("reason", INSTANCE.str(ops, "reason"));
                    }
                }
                return $this$from70_u24lambda_u248;
            }
        } else {
            z = true;
        }
        z2 = false;
        if (z2) {
        }
        if (ops != null) {
            if (INSTANCE.str(ops, "reason").length() <= 0) {
            }
        }
        return $this$from70_u24lambda_u248;
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
        if (((!(it.length() > 0) || Intrinsics.areEqual(it, "false") || Intrinsics.areEqual(it, "{}")) ? null : 1) != null) {
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
