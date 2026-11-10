package com.starpilot.starview;

import com.starpilot.starview.AmySynth;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: AmySynth.kt */
public final class AmyPrompts {
    public static final AmyPrompts INSTANCE = new AmyPrompts();
    private static final Regex ROUTE = new Regex("^(exit|fork|turn) on the (left|right) in ([0-9.]+) (ft|mi|km) - (\\d+) lanes? to go$");

    private AmyPrompts() {
    }

    public final List<String> ask(int dir, String why) {
        Double meters;
        Intrinsics.checkNotNullParameter(why, "why");
        String side = dir < 0 ? "left" : "right";
        String dk = null;
        MatchResult m = ROUTE.find(StringsKt.trim((CharSequence) why).toString(), 0);
        if (m == null) {
            return CollectionsKt.listOf("ask_" + side);
        }
        MatchResult.Destructured destructured = m.getDestructured();
        String what = destructured.getMatch().getGroupValues().get(1);
        String s = destructured.getMatch().getGroupValues().get(2);
        String num = destructured.getMatch().getGroupValues().get(3);
        String unit = destructured.getMatch().getGroupValues().get(4);
        String lanes = destructured.getMatch().getGroupValues().get(5);
        Double doubleOrNull = StringsKt.toDoubleOrNull(num);
        if (doubleOrNull != null) {
            double it = doubleOrNull.doubleValue();
            if (Intrinsics.areEqual(unit, "ft")) {
                meters = Double.valueOf(0.3048d * it);
            } else if (Intrinsics.areEqual(unit, "mi")) {
                meters = Double.valueOf(1609.34d * it);
            } else {
                meters = null;
            }
        } else {
            meters = null;
        }
        if (meters != null) {
            double it2 = meters.doubleValue();
            dk = INSTANCE.distKey(it2);
        }
        Integer intOrNull = StringsKt.toIntOrNull(lanes);
        int n = intOrNull != null ? intOrNull.intValue() : 0;
        ArrayList out = new ArrayList();
        if (dk != null) {
            out.add(what + "_" + s + "_" + dk);
        }
        out.add((1 <= n && n < 5 ? new StringBuilder().append("lanes").append(n).append("_") : new StringBuilder().append("ask_")).append(side).toString());
        return out;
    }

    public final String distKey(double m) {
        return m < 230.0d ? "ft500" : m < 350.0d ? "ft1000" : m < 600.0d ? "mi025" : m < 1000.0d ? "mi05" : m < 1400.0d ? "mi075" : "mi1";
    }

    public final Pair<AmySynth.Style, String> result(String text, int dir) {
        Intrinsics.checkNotNullParameter(text, "text");
        String str = "left";
        String side = dir < 0 ? "left" : "right";
        String t = text.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(t, "toLowerCase(...)");
        if (StringsKt.startsWith(t, "changed lanes", false)) {
            return TuplesKt.to(AmySynth.Style.DONE, "done");
        }
        if (StringsKt.startsWith(t, "test done", false)) {
            return TuplesKt.to(AmySynth.Style.SHORT, "test_done");
        }
        if (StringsKt.startsWith(t, "openpilot disengaged", false)) {
            return TuplesKt.to(AmySynth.Style.STOPPED, "stop_disengaged");
        }
        if (StringsKt.startsWith(t, "brake pressed", false)) {
            return TuplesKt.to(AmySynth.Style.STOPPED, "stop_brake");
        }
        if (StringsKt.startsWith(t, "other blinker", false)) {
            return TuplesKt.to(AmySynth.Style.STOPPED, "stop_blinker");
        }
        if (!StringsKt.contains((CharSequence) t, (CharSequence) "blind spot", false)) {
            return StringsKt.startsWith(t, "openpilot didn't start", false) ? TuplesKt.to(AmySynth.Style.STOPPED, "stop_nostart") : StringsKt.contains((CharSequence) t, (CharSequence) "too long", false) ? TuplesKt.to(AmySynth.Style.STOPPED, "stop_toolong") : StringsKt.startsWith(t, "cancelled", false) ? TuplesKt.to(AmySynth.Style.STOPPED, "stop_cancelled") : TuplesKt.to(AmySynth.Style.STOPPED, "stop_generic");
        }
        AmySynth.Style style = AmySynth.Style.STOPPED;
        if (StringsKt.contains((CharSequence) t, (CharSequence) "right", false)) {
            str = "right";
        } else if (!StringsKt.contains((CharSequence) t, (CharSequence) "left", false)) {
            str = side;
        }
        return TuplesKt.to(style, "stop_bsm_" + str);
    }
}
