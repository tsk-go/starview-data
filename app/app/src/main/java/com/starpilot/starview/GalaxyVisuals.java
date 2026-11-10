package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.starpilot.starview.CarUi;
import com.starpilot.starview.GalaxyRow;
import com.starpilot.starview.SettingIcons;
import com.starpilot.starview.SvIcon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;

/* compiled from: GalaxyVisuals.kt */
public final class GalaxyVisuals {
    public static final GalaxyVisuals INSTANCE = new GalaxyVisuals();
    private static Function1<? super GalaxyRow, Unit> resetFn = new Function1() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            Unit resetFn$lambda$1;
            resetFn$lambda$1 = GalaxyVisuals.resetFn$lambda$1((GalaxyRow) obj);
            return resetFn$lambda$1;
        }
    };
    private static final Paint textPaint = new Paint(1);

    private GalaxyVisuals() {
    }

    public static /* synthetic */ Set header$default(GalaxyVisuals galaxyVisuals, Context context, CarUi carUi, String str, List list, LinearLayout linearLayout, Function2 function2, Function1 function1, int i, Object obj) {
        Function1 function12;
        if ((i & 64) == 0) {
            function12 = function1;
        } else {
            function12 = new Function1() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Unit header$lambda$0;
                    header$lambda$0 = GalaxyVisuals.header$lambda$0((GalaxyRow) obj2);
                    return header$lambda$0;
                }
            };
        }
        return galaxyVisuals.header(context, carUi, str, list, linearLayout, function2, function12);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit header$lambda$0(GalaxyRow it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    public final Set<Integer> header(
        Context var1,
        CarUi var2,
        String var3,
        List<GalaxyRow> var4,
        LinearLayout var5,
        Function2<? super GalaxyRow, ? super String, Unit> var6,
        Function1<? super GalaxyRow, Unit> var7
    ) {
        Set var28 = null; Set var30 = null; Object var32 = null; Object var34 = null; Object var36 = null; Set var38 = null; Object var40 = null; Object var41 = null; // declarations Vineflower left out
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        Intrinsics.checkNotNullParameter(var3, "page");
        Intrinsics.checkNotNullParameter(var4, "rows");
        Intrinsics.checkNotNullParameter(var5, "col");
        Intrinsics.checkNotNullParameter(var6, "write");
        Intrinsics.checkNotNullParameter(var7, "reset");

        label214: {
            label215: {
                label216: {
                    label217: {
                        label218: {
                            label219: {
                                label220: {
                                    label157: {
                                        label156: {
                                            label155: {
                                                label154: {
                                                    label153: {
                                                        label152: {
                                                            try {
                                                                resetFn = var7;
                                                                switch (var3.hashCode()) {
                                                                    case -1067310595:
                                                                        break label154;
                                                                    case -896509628:
                                                                        break label157;
                                                                    case 113962:
                                                                        break label220;
                                                                    case 3314156:
                                                                        break label219;
                                                                    case 95027439:
                                                                        break label218;
                                                                    case 113097563:
                                                                        break label217;
                                                                    case 821741828:
                                                                        break label156;
                                                                    case 821741890:
                                                                        break label155;
                                                                    case 1090497327:
                                                                        break label153;
                                                                    case 1147132932:
                                                                        break label152;
                                                                    case 1312628413:
                                                                        break;
                                                                    default:
                                                                        break label214;
                                                                }
                                                            } catch (Exception var27) {
                                                                Log.w("StarView/Visuals", "page " + var3, var27);
                                                                return SetsKt.emptySet();
                                                            }

                                                            try {
                                                                if (!var3.equals("standard")) {
                                                                    break label214;
                                                                }
                                                                break label215;
                                                            } catch (Exception var26) {
                                                                Log.w("StarView/Visuals", "page " + var3, var26);
                                                                return SetsKt.emptySet();
                                                            }
                                                        }

                                                        try {
                                                            if (!var3.equals("aggressive")) {
                                                                break label214;
                                                            }
                                                            break label215;
                                                        } catch (Exception var25) {
                                                            Log.w("StarView/Visuals", "page " + var3, var25);
                                                            return SetsKt.emptySet();
                                                        }
                                                    }

                                                    try {
                                                        if (!var3.equals("relaxed")) {
                                                            break label214;
                                                        }
                                                        break label215;
                                                    } catch (Exception var24) {
                                                        Log.w("StarView/Visuals", "page " + var3, var24);
                                                        return SetsKt.emptySet();
                                                    }
                                                }

                                                try {
                                                    if (!var3.equals("traffic")) {
                                                        break label214;
                                                    }
                                                    break label215;
                                                } catch (Exception var23) {
                                                    Log.w("StarView/Visuals", "page " + var3, var23);
                                                    Set var39 = SetsKt.emptySet();
                                                    return var39;
                                                }
                                            }

                                            try {
                                                if (!var3.equals("conditional/cem")) {
                                                    break label214;
                                                }
                                                break label216;
                                            } catch (Exception var17) {
                                                Log.w("StarView/Visuals", "page " + var3, var17);
                                                return SetsKt.emptySet();
                                            }
                                        }

                                        try {
                                            if (!var3.equals("conditional/ccm")) {
                                                break label214;
                                            }
                                            break label216;
                                        } catch (Exception var16) {
                                            Log.w("StarView/Visuals", "page " + var3, var16);
                                            return SetsKt.emptySet();
                                        }
                                    }

                                    try {
                                        if (!var3.equals("sounds")) {
                                            break label214;
                                        }
                                    } catch (Exception var22) {
                                        Log.w("StarView/Visuals", "page " + var3, var22);
                                        Set var37 = SetsKt.emptySet();
                                        return var37;
                                    }

                                    try {
                                        var38 = this.mixer(var1, var2, var4, var5, var6);
                                    } catch (Exception var10) {
                                        Log.w("StarView/Visuals", "page " + var3, var10);
                                        var38 = SetsKt.emptySet();
                                    }

                                    return var38;
                                }

                                try {
                                    if (!var3.equals("slc")) {
                                        break label214;
                                    }
                                } catch (Exception var21) {
                                    Log.w("StarView/Visuals", "page " + var3, var21);
                                    Set var35 = SetsKt.emptySet();
                                    return var35;
                                }

                                try {
                                    this.bands(var1, var2, var4, var5);
                                    var36 = SetsKt.emptySet();
                                } catch (Exception var11) {
                                    Log.w("StarView/Visuals", "page " + var3, var11);
                                    var36 = SetsKt.emptySet();
                                }

                                return var36;
                            }

                            try {
                                if (!var3.equals("lane")) {
                                    break label214;
                                }
                            } catch (Exception var20) {
                                Log.w("StarView/Visuals", "page " + var3, var20);
                                Set var33 = SetsKt.emptySet();
                                return var33;
                            }

                            try {
                                this.lane(var1, var2, var4, var5);
                                var34 = SetsKt.emptySet();
                            } catch (Exception var12) {
                                Log.w("StarView/Visuals", "page " + var3, var12);
                                var34 = SetsKt.emptySet();
                            }

                            return var34;
                        }

                        try {
                            if (!var3.equals("curve")) {
                                break label214;
                            }
                        } catch (Exception var19) {
                            Log.w("StarView/Visuals", "page " + var3, var19);
                            Set var31 = SetsKt.emptySet();
                            return var31;
                        }

                        try {
                            this.curve(var1, var2, var4, var5);
                            var32 = SetsKt.emptySet();
                        } catch (Exception var13) {
                            Log.w("StarView/Visuals", "page " + var3, var13);
                            var32 = SetsKt.emptySet();
                        }

                        return var32;
                    }

                    try {
                        if (!var3.equals("wheel")) {
                            break label214;
                        }
                    } catch (Exception var18) {
                        Log.w("StarView/Visuals", "page " + var3, var18);
                        Set var29 = SetsKt.emptySet();
                        return var29;
                    }

                    try {
                        var30 = this.wheel(var1, var2, var4, var5, var6);
                    } catch (Exception var14) {
                        Log.w("StarView/Visuals", "page " + var3, var14);
                        var30 = SetsKt.emptySet();
                    }

                    return var30;
                }

                try {
                    var28 = this.conditions(var1, var2, var3, var4, var5, var6);
                } catch (Exception var15) {
                    Log.w("StarView/Visuals", "page " + var3, var15);
                    var28 = SetsKt.emptySet();
                }

                return var28;
            }

            try {
                this.gap(var1, var2, var3, var4, var5);
                var40 = SetsKt.emptySet();
            } catch (Exception var9) {
                Log.w("StarView/Visuals", "page " + var3, var9);
                var40 = SetsKt.emptySet();
            }

            return var40;
        }

        try {
            var41 = SetsKt.emptySet();
        } catch (Exception var8) {
            Log.w("StarView/Visuals", "page " + var3, var8);
            var41 = SetsKt.emptySet();
        }

        return var41;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit resetFn$lambda$1(GalaxyRow it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Unit.INSTANCE;
    }

    public final void about(Context ctx, final GalaxyRow r) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(r, "r");
        AlertDialog.Builder title = new AlertDialog.Builder(ctx).setTitle(r.getLabel());
        String[] strArr = new String[2];
        String replace$default = StringsKt.replace(r.getReason(), "https://firestar.link/discord", "the StarPilot Discord", false);
        if (StringsKt.isBlank(replace$default)) {
            replace$default = null;
        }
        strArr[0] = replace$default;
        strArr[1] = "Now: " + r.withUnit(GalaxyRow.shown$default(r, null, 1, null)) + (r.getDefaultValue().length() > 0 ? "   ·   Default: " + r.withUnit(r.shown(r.getDefaultValue())) : "");
        AlertDialog.Builder b = title.setMessage(CollectionsKt.joinToString(CollectionsKt.listOfNotNull((Object[]) strArr), "\n\n", "", "", -1, "...", null)).setNegativeButton("Close", (DialogInterface.OnClickListener) null);
        if (r.getShowDefault() && r.getEditable()) {
            b.setPositiveButton("Set to default", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    GalaxyVisuals.about$lambda$3(r, dialogInterface, i);
                }
            });
        }
        b.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void about$lambda$3(GalaxyRow $r, DialogInterface dialogInterface, int i) {
        resetFn.invoke($r);
    }

    private final LinearLayout card(Context ctx, CarUi ui) {
        LinearLayout $this$card_u24lambda_u244 = new LinearLayout(ctx);
        $this$card_u24lambda_u244.setOrientation(1);
        $this$card_u24lambda_u244.setBackground(CarUi.rounded$default(ui, CarUi.Companion.getCARD(), 18, null, 4, null));
        $this$card_u24lambda_u244.setPadding(ui.dp(20), ui.dp(16), ui.dp(20), ui.dp(16));
        return $this$card_u24lambda_u244;
    }

    private final TextView caption(Context ctx, CarUi ui, String text) {
        TextView $this$caption_u24lambda_u245 = new TextView(ctx);
        $this$caption_u24lambda_u245.setText(text);
        $this$caption_u24lambda_u245.setTextColor(CarUi.Companion.getDIM());
        $this$caption_u24lambda_u245.setTextSize(15.0f);
        $this$caption_u24lambda_u245.setPadding(0, ui.dp(8), 0, 0);
        return $this$caption_u24lambda_u245;
    }

    private final Double num(GalaxyRow r) {
        String shown$default;
        if (r == null || (shown$default = GalaxyRow.shown$default(r, null, 1, null)) == null) {
            return null;
        }
        return StringsKt.toDoubleOrNull(shown$default);
    }

    private final GalaxyRow find(List<GalaxyRow> rows, String re) {
        Object element$iv;
        List<GalaxyRow> $this$firstOrNull$iv = rows;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                GalaxyRow it2 = (GalaxyRow) element$iv;
                if (new Regex(re, RegexOption.IGNORE_CASE).containsMatchIn(it2.getLabel())) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        return (GalaxyRow) element$iv;
    }

    /* VF: from Vineflower (jadx: ) */
    public final SettingIcons.Icon actionIcon(String var1) {
        Intrinsics.checkNotNullParameter(var1, "action");
        switch (var1.hashCode()) {
            case -1925399022:
                if (var1.equals("Adopt speed limit")) {
                    SettingIcons.Icon var3 = new SettingIcons.Icon("sign", "speed", false, 4, null);
                    return var3;
                }
                break;
            case -1899271032:
                if (var1.equals("Switchback Mode")) {
                    return new SettingIcons.Icon("curve", "steer", false, 4, null);
                }
                break;
            case -1711209605:
                if (var1.equals("Pause steering")) {
                    return new SettingIcons.Icon("pause", "steer", false, 4, null);
                }
                break;
            case -1166858732:
                if (var1.equals("Cycle personality")) {
                    return new SettingIcons.Icon("standard", "person", false, 4, null);
                }
                break;
            case -268029695:
                if (var1.equals("Force coast")) {
                    return new SettingIcons.Icon("coast", "speed", false, 4, null);
                }
                break;
            case 79183:
                if (var1.equals("Off")) {
                    return new SettingIcons.Icon("power", "device", false, 4, null);
                }
                break;
            case 776643728:
                if (var1.equals("Pause longitudinal")) {
                    return new SettingIcons.Icon("pause", "speed", false, 4, null);
                }
                break;
            case 944116230:
                if (var1.equals("Traffic mode")) {
                    return new SettingIcons.Icon("traffic", "person", false, 4, null);
                }
                break;
            case 1139431218:
                if (var1.equals("Toggle AOL")) {
                    return new SettingIcons.Icon("aol", "steer", false, 4, null);
                }
                break;
            case 1635958837:
                if (var1.equals("Pulse and glide")) {
                    return new SettingIcons.Icon("pulse", "speed", false, 4, null);
                }
                break;
            case 1797878756:
                if (var1.equals("Experimental override")) {
                    return new SettingIcons.Icon("conditional", "speed", false, 4, null);
                }
                break;
            case 2070022486:
                if (var1.equals("Bookmark")) {
                    return new SettingIcons.Icon("bookmark", "display", false, 4, null);
                }
        }

        SettingIcons.Icon var2;
        if (StringsKt.startsWith(var1, "Quick Select", false)) {
            var2 = new SettingIcons.Icon("star", "display", false, 4, null);
        } else {
            var2 = new SettingIcons.Icon("buttons", "display", false, 4, null);
        }

        return var2;
    }

    public final Pair<String, String> buttonOf(String label) {
        Intrinsics.checkNotNullParameter(label, "label");
        MatchResult m = (new Regex("^(LKAS|Main cruise|Distance|Cancel|MODE|Star)(?: button)?\\s*(short press|press|long press|very long press)?$", RegexOption.IGNORE_CASE)).find(StringsKt.trim((CharSequence) label).toString(), 0);
        if (m == null) {
            return null;
        }
        String it = m.getGroupValues().get(1);
        if (Intrinsics.areEqual(it, "MODE")) {
            it = "Mode";
        }
        String lowerCase = m.getGroupValues().get(2).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        String how = Intrinsics.areEqual(lowerCase, "long press") ? "hold" : Intrinsics.areEqual(lowerCase, "very long press") ? "long hold" : "press";
        return TuplesKt.to(it, how);
    }

    private final Set<Integer> wheel(final Context ctx, CarUi ui, List<GalaxyRow> rows, LinearLayout col, final Function2<? super GalaxyRow, ? super String, Unit> write) {
        int i;
        String it;
        Object answer$iv;
        GalaxyVisuals galaxyVisuals = this;
        List<GalaxyRow> $this$filter$iv = rows;
        Collection destination$iv$iv = new ArrayList();
        Iterator it2 = $this$filter$iv.iterator();
        while (true) {
            i = 0;
            if (!it2.hasNext()) {
                break;
            }
            Object element$iv$iv = it2.next();
            GalaxyRow it3 = (GalaxyRow) element$iv$iv;
            if (it3.getControl() == GalaxyRow.Control.SELECT && INSTANCE.buttonOf(it3.getLabel()) != null) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List<GalaxyRow> mine = (List) destination$iv$iv;
        if (mine.isEmpty()) {
            return SetsKt.emptySet();
        }
        Map byButton = new LinkedHashMap();
        for (GalaxyRow r : mine) {
            Pair<String, String> buttonOf = galaxyVisuals.buttonOf(r.getLabel());
            Intrinsics.checkNotNull(buttonOf);
            String b = buttonOf.component1();
            String how = buttonOf.component2();
            Map $this$getOrPut$iv = byButton;
            Object value$iv = $this$getOrPut$iv.get(b);
            if (value$iv == null) {
                answer$iv = new ArrayList();
                $this$getOrPut$iv.put(b, answer$iv);
            } else {
                answer$iv = value$iv;
            }
            ((List) answer$iv).add(TuplesKt.to(how, r));
        }
        int i2 = 12;
        FlowRow grid = new FlowRow(ctx, ui.dp(12), ui.dp(12));
        int w = ui.dp(330);
        for (Map.Entry entry : byButton.entrySet()) {
            String b2 = (String) entry.getKey();
            List<Pair> list = (List) entry.getValue();
            LinearLayout c = card(ctx, ui);
            LinearLayout $this$wheel_u24lambda_u2412 = new LinearLayout(ctx);
            $this$wheel_u24lambda_u2412.setOrientation(i);
            $this$wheel_u24lambda_u2412.setGravity(16);
            int w2 = w;
            FlowRow grid2 = grid;
            View chip$default = SvIcon.chip$default(SvIcon.INSTANCE, ctx, "buttons", "display", 40, null, 16, null);
            LinearLayout.LayoutParams $this$wheel_u24lambda_u2412_u24lambda_u2410 = new LinearLayout.LayoutParams(ui.dp(40), ui.dp(40));
            $this$wheel_u24lambda_u2412_u24lambda_u2410.rightMargin = ui.dp(i2);
            Unit unit = Unit.INSTANCE;
            $this$wheel_u24lambda_u2412.addView(chip$default, $this$wheel_u24lambda_u2412_u24lambda_u2410);
            TextView $this$wheel_u24lambda_u2412_u24lambda_u2411 = new TextView(ctx);
            String b3 = b2;
            $this$wheel_u24lambda_u2412_u24lambda_u2411.setText(Intrinsics.areEqual(b3, "Star") ? "★ button" : b3 + " button");
            $this$wheel_u24lambda_u2412_u24lambda_u2411.setTextColor(CarUi.Companion.getTEXT());
            $this$wheel_u24lambda_u2412_u24lambda_u2411.setTextSize(20.0f);
            $this$wheel_u24lambda_u2412_u24lambda_u2411.setTypeface(Typeface.DEFAULT_BOLD);
            $this$wheel_u24lambda_u2412.addView($this$wheel_u24lambda_u2412_u24lambda_u2411);
            c.addView($this$wheel_u24lambda_u2412);
            for (Pair pair : list) {
                String how2 = (String) pair.component1();
                final GalaxyRow r2 = (GalaxyRow) pair.component2();
                SettingIcons.Icon ic = galaxyVisuals.actionIcon(r2.getValue());
                LinearLayout $this$wheel_u24lambda_u2421 = new LinearLayout(ctx);
                $this$wheel_u24lambda_u2421.setOrientation(0);
                $this$wheel_u24lambda_u2421.setGravity(16);
                $this$wheel_u24lambda_u2421.setPadding(0, ui.dp(10), 0, 0);
                $this$wheel_u24lambda_u2421.setMinimumHeight(ui.dp(56));
                TextView $this$wheel_u24lambda_u2421_u24lambda_u2413 = new TextView(ctx);
                $this$wheel_u24lambda_u2421_u24lambda_u2413.setText(how2);
                $this$wheel_u24lambda_u2421_u24lambda_u2413.setTextColor(CarUi.Companion.getDIM());
                $this$wheel_u24lambda_u2421_u24lambda_u2413.setTextSize(15.0f);
                $this$wheel_u24lambda_u2421.addView($this$wheel_u24lambda_u2421_u24lambda_u2413, new LinearLayout.LayoutParams(ui.dp(84), -2));
                String b4 = b3;
                View chip = SvIcon.INSTANCE.chip(ctx, ic.getName(), ic.getGroup(), 36, Intrinsics.areEqual(r2.getValue(), "Off") ? SvIcon.State.OFF : SvIcon.State.ON);
                LinearLayout.LayoutParams $this$wheel_u24lambda_u2421_u24lambda_u2414 = new LinearLayout.LayoutParams(ui.dp(36), ui.dp(36));
                $this$wheel_u24lambda_u2421_u24lambda_u2414.rightMargin = ui.dp(10);
                Unit unit2 = Unit.INSTANCE;
                $this$wheel_u24lambda_u2421.addView(chip, $this$wheel_u24lambda_u2421_u24lambda_u2414);
                TextView $this$wheel_u24lambda_u2421_u24lambda_u2415 = new TextView(ctx);
                $this$wheel_u24lambda_u2421_u24lambda_u2415.setText(r2.getValue());
                $this$wheel_u24lambda_u2421_u24lambda_u2415.setTextColor(Intrinsics.areEqual(r2.getValue(), "Off") ? CarUi.Companion.getDIM() : CarUi.Companion.getTEXT());
                $this$wheel_u24lambda_u2421_u24lambda_u2415.setTextSize(18.0f);
                $this$wheel_u24lambda_u2421.addView($this$wheel_u24lambda_u2421_u24lambda_u2415, new LinearLayout.LayoutParams(0, -2, 1.0f));
                TextView $this$wheel_u24lambda_u2421_u24lambda_u2416 = new TextView(ctx);
                $this$wheel_u24lambda_u2421_u24lambda_u2416.setText("›");
                $this$wheel_u24lambda_u2421_u24lambda_u2416.setTextSize(26.0f);
                $this$wheel_u24lambda_u2421_u24lambda_u2416.setTextColor(CarUi.Companion.getDIM());
                $this$wheel_u24lambda_u2421.addView($this$wheel_u24lambda_u2421_u24lambda_u2416);
                $this$wheel_u24lambda_u2421.setBackground(CarUi.rounded$default(ui, CarUi.Companion.getCARD_ON(), 12, null, 4, null));
                $this$wheel_u24lambda_u2421.setPadding(ui.dp(12), ui.dp(4), ui.dp(12), ui.dp(4));
                $this$wheel_u24lambda_u2421.setAlpha(r2.getAvailable() ? 1.0f : 0.45f);
                $this$wheel_u24lambda_u2421.setClickable(true);
                $this$wheel_u24lambda_u2421.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        GalaxyVisuals.wheel$lambda$21$lambda$20(r2, ctx, write, view);
                    }
                });
                LinearLayout.LayoutParams $this$wheel_u24lambda_u2422 = new LinearLayout.LayoutParams(-1, -2);
                $this$wheel_u24lambda_u2422.topMargin = ui.dp(8);
                Unit unit3 = Unit.INSTANCE;
                c.addView($this$wheel_u24lambda_u2421, $this$wheel_u24lambda_u2422);
                galaxyVisuals = this;
                i2 = 12;
                b3 = b4;
            }
            grid2.addView(c, new ViewGroup.LayoutParams(w2, -2));
            galaxyVisuals = this;
            w = w2;
            grid = grid2;
            i = 0;
        }
        col.addView(grid, CarUi.lp$default(ui, 0, 10, 1, null));
        GalaxyRow galaxyRow = (GalaxyRow) CollectionsKt.firstOrNull(mine);
        if (galaxyRow != null && (it = galaxyRow.getReason()) != null) {
            if (StringsKt.isBlank(it)) {
                it = null;
            }
            if (it != null) {
                col.addView(ui.note(it), CarUi.lp$default(ui, 0, 0, 3, null));
            }
        }
        List $this$map$iv = mine;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            destination$iv$iv2.add(Integer.valueOf(((GalaxyRow) item$iv$iv).getIndex()));
        }
        return CollectionsKt.toSet((List) destination$iv$iv2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wheel$lambda$21$lambda$20(final GalaxyRow $r, final Context $ctx, final Function2 $write, View it) {
        if ($r.getAvailable()) {
            AlertDialog.Builder title = new AlertDialog.Builder($ctx).setTitle($r.getLabel());
            Iterable $this$map$iv = $r.getChoices();
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                String it2 = (String) item$iv$iv;
                if (Intrinsics.areEqual(it2, $r.getDefaultValue())) {
                    it2 = it2 + "   (default)";
                }
                destination$iv$iv.add(it2);
            }
            Collection thisCollection$iv = (List) destination$iv$iv;
            title.setSingleChoiceItems((CharSequence[]) thisCollection$iv.toArray(new String[0]), $r.getChoices().indexOf($r.getValue()), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    GalaxyVisuals.wheel$lambda$21$lambda$20$lambda$18($r, $write, dialogInterface, i);
                }
            }).setNeutralButton("About", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    GalaxyVisuals.wheel$lambda$21$lambda$20$lambda$19($ctx, $r, dialogInterface, i);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wheel$lambda$21$lambda$20$lambda$18(GalaxyRow $r, Function2 $write, DialogInterface d, int i) {
        d.dismiss();
        if (Intrinsics.areEqual($r.getChoices().get(i), $r.getValue())) {
            return;
        }
        $write.invoke($r, $r.getChoices().get(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wheel$lambda$21$lambda$20$lambda$19(Context $ctx, GalaxyRow $r, DialogInterface dialogInterface, int i) {
        INSTANCE.about($ctx, $r);
    }

    public final List<String> faderPositions(GalaxyRow r) {
        int n;
        Intrinsics.checkNotNullParameter(r, "r");
        boolean z = false;
        if (r.getControl() == GalaxyRow.Control.SLIDER) {
            GalaxyRow.Bounds b = r.bounds();
            if (b == null || (n = (int) Math.round((b.getMax() - b.getMin()) / b.getStep())) > 400) {
                return null;
            }
            Iterable $this$map$iv = new IntRange(0, n);
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                destination$iv$iv.add(GalaxyRow.Companion.fmt(Math.round((b.getMin() + (item$iv$iv * b.getStep())) * 1000000.0d) / 1000000.0d));
                n = n;
            }
            return CollectionsKt.plus(destination$iv$iv, (Iterable) (b.getAuto() ? CollectionsKt.listOf("Auto") : CollectionsKt.emptyList()));
        }
        if (r.getControl() != GalaxyRow.Control.SELECT || r.getChoices().size() < 3) {
            return null;
        }
        Iterable $this$filter$iv = r.getChoices();
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it2 = (String) element$iv$iv;
            if (!Intrinsics.areEqual(it2, "Auto")) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        Iterable nums = (List) destination$iv$iv2;
        Iterable $this$any$iv = nums;
        if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
            Iterator it3 = $this$any$iv.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                Object element$iv = it3.next();
                String it4 = (String) element$iv;
                String it5 = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) StringsKt.removeSuffix(it4, (CharSequence) "%")).toString()) == null ? 1 : null;
                if (it5 != null) {
                    z = true;
                    break;
                }
            }
        }
        if (z) {
            return null;
        }
        Iterable $this$sortedBy$iv = nums;
        return CollectionsKt.plus((Collection) CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.GalaxyVisuals$faderPositions$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                String it6 = (String) t;
                Double valueOf = Double.valueOf(Double.parseDouble(StringsKt.trim((CharSequence) StringsKt.removeSuffix(it6, (CharSequence) "%")).toString()));
                String it7 = (String) t2;
                return ComparisonsKt.compareValues(valueOf, Double.valueOf(Double.parseDouble(StringsKt.trim((CharSequence) StringsKt.removeSuffix(it7, (CharSequence) "%")).toString())));
            }
        }), (Iterable) (r.getChoices().contains("Auto") ? CollectionsKt.listOf("Auto") : CollectionsKt.emptyList()));
    }

    private final Set<Integer> mixer(final Context ctx, CarUi ui, List<GalaxyRow> rows, LinearLayout col, final Function2<? super GalaxyRow, ? super String, Unit> write) {
        boolean z;
        List<GalaxyRow> $this$mapNotNull$iv = rows;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            GalaxyRow r = (GalaxyRow) element$iv$iv$iv;
            List it = INSTANCE.faderPositions(r);
            Pair pair = it != null ? TuplesKt.to(r, it) : null;
            if (pair != null) {
                destination$iv$iv.add(pair);
            }
        }
        List mine = (List) destination$iv$iv;
        if (mine.size() < 2) {
            return SetsKt.emptySet();
        }
        LinearLayout c = card(ctx, ui);
        LinearLayout $this$mixer_u24lambda_u2432 = new LinearLayout(ctx);
        boolean z2 = false;
        $this$mixer_u24lambda_u2432.setOrientation(0);
        $this$mixer_u24lambda_u2432.setGravity(80);
        Iterator it2 = mine.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Pair pair2 = (Pair) it2.next();
            final GalaxyRow r2 = (GalaxyRow) pair2.component1();
            List positions = (List) pair2.component2();
            SettingIcons.Icon ic = SettingIcons.INSTANCE.forRow("sounds", r2.getLabel(), "", r2.getControl());
            final boolean slider = r2.getControl() == GalaxyRow.Control.SLIDER ? true : z2;
            final TextView $this$mixer_u24lambda_u2433 = new TextView(ctx);
            $this$mixer_u24lambda_u2433.setTextColor(CarUi.Companion.getTEXT());
            $this$mixer_u24lambda_u2433.setTextSize(17.0f);
            $this$mixer_u24lambda_u2433.setTypeface(Typeface.DEFAULT_BOLD);
            $this$mixer_u24lambda_u2433.setGravity(17);
            FaderView fader = new FaderView(ctx, SvIcon.INSTANCE.fg("sound"), positions);
            fader.setValue(slider ? GalaxyRow.shown$default(r2, null, 1, null) : r2.getValue());
            $this$mixer_u24lambda_u2433.setText(mixer$label(slider, r2, fader.getValue()));
            fader.setOnMove(new Function1() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit mixer$lambda$34;
                    mixer$lambda$34 = GalaxyVisuals.mixer$lambda$34($this$mixer_u24lambda_u2433, slider, r2, (String) obj);
                    return mixer$lambda$34;
                }
            });
            fader.setOnCommit(new Function1() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit mixer$lambda$35;
                    mixer$lambda$35 = GalaxyVisuals.mixer$lambda$35(slider, r2, write, (String) obj);
                    return mixer$lambda$35;
                }
            });
            fader.setEnabled(r2.getEditable());
            LinearLayout $this$mixer_u24lambda_u2442 = new LinearLayout(ctx);
            $this$mixer_u24lambda_u2442.setOrientation(1);
            $this$mixer_u24lambda_u2442.setGravity(1);
            $this$mixer_u24lambda_u2442.setAlpha(r2.getEditable() ? 1.0f : 0.45f);
            $this$mixer_u24lambda_u2442.addView(SvIcon.chip$default(SvIcon.INSTANCE, ctx, ic.getName(), ic.getGroup(), 48, null, 16, null), new LinearLayout.LayoutParams(ui.dp(48), ui.dp(48)));
            LinearLayout.LayoutParams $this$mixer_u24lambda_u2442_u24lambda_u2436 = new LinearLayout.LayoutParams(ui.dp(64), ui.dp(300));
            $this$mixer_u24lambda_u2442_u24lambda_u2436.topMargin = ui.dp(10);
            Unit unit = Unit.INSTANCE;
            $this$mixer_u24lambda_u2442.addView(fader, $this$mixer_u24lambda_u2442_u24lambda_u2436);
            LinearLayout.LayoutParams $this$mixer_u24lambda_u2442_u24lambda_u2437 = new LinearLayout.LayoutParams(-2, -2);
            $this$mixer_u24lambda_u2442_u24lambda_u2437.topMargin = ui.dp(8);
            Unit unit2 = Unit.INSTANCE;
            $this$mixer_u24lambda_u2442.addView($this$mixer_u24lambda_u2433, $this$mixer_u24lambda_u2442_u24lambda_u2437);
            TextView $this$mixer_u24lambda_u2442_u24lambda_u2439 = new TextView(ctx);
            $this$mixer_u24lambda_u2442_u24lambda_u2439.setText(r2.getLabel());
            $this$mixer_u24lambda_u2442_u24lambda_u2439.setTextColor(CarUi.Companion.getDIM());
            $this$mixer_u24lambda_u2442_u24lambda_u2439.setTextSize(14.0f);
            $this$mixer_u24lambda_u2442_u24lambda_u2439.setGravity(17);
            $this$mixer_u24lambda_u2442_u24lambda_u2439.setMaxLines(2);
            $this$mixer_u24lambda_u2442_u24lambda_u2439.setMinLines(2);
            $this$mixer_u24lambda_u2442_u24lambda_u2439.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GalaxyVisuals.mixer$lambda$42$lambda$39$lambda$38(ctx, r2, view);
                }
            });
            $this$mixer_u24lambda_u2442.addView($this$mixer_u24lambda_u2442_u24lambda_u2439);
            if (r2.getShowDefault()) {
                TextView $this$mixer_u24lambda_u2442_u24lambda_u2441 = new TextView(ctx);
                $this$mixer_u24lambda_u2442_u24lambda_u2441.setText("Default");
                $this$mixer_u24lambda_u2442_u24lambda_u2441.setTextColor(CarUi.Companion.getACCENT());
                $this$mixer_u24lambda_u2442_u24lambda_u2441.setTextSize(14.0f);
                $this$mixer_u24lambda_u2442_u24lambda_u2441.setPadding(ui.dp(8), ui.dp(6), ui.dp(8), ui.dp(6));
                $this$mixer_u24lambda_u2442_u24lambda_u2441.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda7
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        GalaxyVisuals.mixer$lambda$42$lambda$41$lambda$40(r, write, slider, view);
                    }
                });
                $this$mixer_u24lambda_u2442.addView($this$mixer_u24lambda_u2442_u24lambda_u2441);
            }
            $this$mixer_u24lambda_u2432.addView($this$mixer_u24lambda_u2442, new LinearLayout.LayoutParams(0, -2, 1.0f));
            z2 = false;
        }
        c.addView($this$mixer_u24lambda_u2432);
        List $this$any$iv = mine;
        if (!($this$any$iv instanceof Collection) || !$this$any$iv.isEmpty()) {
            Iterator it3 = $this$any$iv.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    z = false;
                    break;
                }
                Object element$iv = it3.next();
                Pair it4 = (Pair) element$iv;
                if (((List) it4.getSecond()).contains("Auto")) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
        }
        c.addView(caption(ctx, ui, "Drag a fader; it saves when you let go. Tap a name for what it does." + (z ? " The top of a fader is Auto." : "")));
        col.addView(c, CarUi.lp$default(ui, 0, 12, 1, null));
        List $this$map$iv = mine;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair it5 = (Pair) item$iv$iv;
            destination$iv$iv2.add(Integer.valueOf(((GalaxyRow) it5.getFirst()).getIndex()));
        }
        return CollectionsKt.toSet((List) destination$iv$iv2);
    }

    private static final String mixer$label(boolean slider, GalaxyRow r, String v) {
        return slider ? r.withUnit(v) : v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit mixer$lambda$34(TextView $valueTv, boolean $slider, GalaxyRow $r, String v) {
        Intrinsics.checkNotNullParameter(v, "v");
        $valueTv.setText(mixer$label($slider, $r, v));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit mixer$lambda$35(boolean $slider, GalaxyRow $r, Function2 $write, String v) {
        Intrinsics.checkNotNullParameter(v, "v");
        String now = $slider ? GalaxyRow.shown$default($r, null, 1, null) : $r.getValue();
        if ($r.getEditable() && !Intrinsics.areEqual(v, now)) {
            String out = (!$slider || Intrinsics.areEqual(v, "Auto")) ? v : $r.saved(Double.parseDouble(v));
            if (out != null) {
                $write.invoke($r, out);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mixer$lambda$42$lambda$39$lambda$38(Context $ctx, GalaxyRow $r, View it) {
        INSTANCE.about($ctx, $r);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mixer$lambda$42$lambda$41$lambda$40(GalaxyRow $r, Function2 $write, boolean $slider, View it) {
        Double doubleOrNull;
        if ($r.getEditable()) {
            String defaultValue = $r.getDefaultValue();
            if ($slider && ((doubleOrNull = StringsKt.toDoubleOrNull($r.shown(defaultValue))) == null || (defaultValue = $r.saved(doubleOrNull.doubleValue())) == null)) {
                return;
            }
            $write.invoke($r, defaultValue);
        }
    }

    private final void gap(Context ctx, CarUi ui, String page, List<GalaxyRow> rows, LinearLayout col) {
        String str;
        Double num = num(find(rows, "^low-speed follow"));
        if (num != null) {
            double low = num.doubleValue();
            Double high = num(find(rows, "^high-speed follow"));
            LinearLayout c = card(ctx, ui);
            c.addView(new GapView(ctx, low, 30.0d, SvIcon.INSTANCE.fg("person")), new LinearLayout.LayoutParams(-1, ui.dp(84)));
            if (high != null) {
                GapView gapView = new GapView(ctx, high.doubleValue(), 65.0d, SvIcon.INSTANCE.fg("person"));
                LinearLayout.LayoutParams $this$gap_u24lambda_u2445 = new LinearLayout.LayoutParams(-1, ui.dp(84));
                $this$gap_u24lambda_u2445.topMargin = ui.dp(6);
                Unit unit = Unit.INSTANCE;
                c.addView(gapView, $this$gap_u24lambda_u2445);
            }
            String fmtGap = fmtGap(low, 30.0d);
            if (high != null) {
                str = ", " + fmtGap(high.doubleValue(), 65.0d) + " on the highway";
            } else {
                str = "";
            }
            c.addView(caption(ctx, ui, "The gap this personality keeps to the car ahead, drawn to scale: " + fmtGap + " in town" + str + "."));
            col.addView(c, CarUi.lp$default(ui, 0, 12, 1, null));
        }
    }

    public final double gapFeet(double seconds, double mph) {
        return ((seconds * mph) * 5280.0d) / 3600.0d;
    }

    private final String fmtGap(double s, double mph) {
        return GalaxyRow.Companion.fmt(s) + " s ≈ " + Math.round(gapFeet(s, mph)) + " ft at " + ((int) mph) + " mph";
    }

    public final void personalities(Context ctx, CarUi ui, LinearLayout col, final Function1<? super String, Unit> open) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        Intrinsics.checkNotNullParameter(col, "col");
        Intrinsics.checkNotNullParameter(open, "open");
        LinearLayout c = card(ctx, ui);
        LinearLayout $this$personalities_u24lambda_u2446 = new LinearLayout(ctx);
        int i = 0;
        $this$personalities_u24lambda_u2446.setOrientation(0);
        int i2 = 4;
        int i3 = 1;
        List<Pair> pages = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("traffic", "Traffic"), TuplesKt.to("aggressive", "Aggressive"), TuplesKt.to("standard", "Standard"), TuplesKt.to("relaxed", "Relaxed")});
        HashMap cells = new HashMap();
        for (Pair pair : pages) {
            final String p = (String) pair.component1();
            String t = (String) pair.component2();
            SettingIcons.Icon ic = SettingIcons.INSTANCE.forPage(p);
            TextView $this$personalities_u24lambda_u2447 = new TextView(ctx);
            $this$personalities_u24lambda_u2447.setText("…");
            $this$personalities_u24lambda_u2447.setTextColor(CarUi.Companion.getDIM());
            $this$personalities_u24lambda_u2447.setTextSize(15.0f);
            $this$personalities_u24lambda_u2447.setPadding(i, ui.dp(8), i, i);
            cells.put(p, $this$personalities_u24lambda_u2447);
            LinearLayout $this$personalities_u24lambda_u2450 = new LinearLayout(ctx);
            $this$personalities_u24lambda_u2450.setOrientation(i3);
            $this$personalities_u24lambda_u2450.setPadding(ui.dp(6), ui.dp(i2), ui.dp(6), ui.dp(i2));
            $this$personalities_u24lambda_u2450.addView(SvIcon.chip$default(SvIcon.INSTANCE, ctx, ic.getName(), ic.getGroup(), 56, null, 16, null), new LinearLayout.LayoutParams(ui.dp(56), ui.dp(56)));
            TextView $this$personalities_u24lambda_u2450_u24lambda_u2448 = new TextView(ctx);
            $this$personalities_u24lambda_u2450_u24lambda_u2448.setText(t);
            $this$personalities_u24lambda_u2450_u24lambda_u2448.setTextColor(CarUi.Companion.getTEXT());
            $this$personalities_u24lambda_u2450_u24lambda_u2448.setTextSize(19.0f);
            $this$personalities_u24lambda_u2450_u24lambda_u2448.setTypeface(Typeface.DEFAULT_BOLD);
            $this$personalities_u24lambda_u2450_u24lambda_u2448.setPadding(0, ui.dp(8), 0, 0);
            $this$personalities_u24lambda_u2450.addView($this$personalities_u24lambda_u2450_u24lambda_u2448);
            $this$personalities_u24lambda_u2450.addView($this$personalities_u24lambda_u2447);
            $this$personalities_u24lambda_u2450.setClickable(true);
            $this$personalities_u24lambda_u2450.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    open.invoke(p);
                }
            });
            $this$personalities_u24lambda_u2446.addView($this$personalities_u24lambda_u2450, new LinearLayout.LayoutParams(0, -2, 1.0f));
            i3 = 1;
            cells = cells;
            i = 0;
            i2 = 4;
        }
        final HashMap cells2 = cells;
        c.addView($this$personalities_u24lambda_u2446);
        c.addView(caption(ctx, ui, "Follow time in town / on the highway, and the feet that makes at 30 and 65 mph. Tap one to tune it."));
        col.addView(c, CarUi.lp$default(ui, 0, 12, i3, null));
        Iterator it = pages.iterator();
        while (it.hasNext()) {
            final String p2 = (String) ((Pair) it.next()).component1();
            GalaxyPages.get$default(GalaxyPages.INSTANCE, p2, 0L, new Function1() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit personalities$lambda$58;
                    personalities$lambda$58 = GalaxyVisuals.personalities$lambda$58(cells2, p2, (JSONObject) obj);
                    return personalities$lambda$58;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit personalities$lambda$58(HashMap $cells, String $p, JSONObject o) {
        List rows;
        String str;
        String str2;
        if (o == null || (rows = GalaxyRow.Companion.parse(o)) == null) {
            return Unit.INSTANCE;
        }
        Double low = INSTANCE.num(INSTANCE.find(rows, "^low-speed follow"));
        Double high = INSTANCE.num(INSTANCE.find(rows, "^high-speed follow"));
        List $this$filter$iv = rows;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            if (Intrinsics.areEqual(((GalaxyRow) element$iv$iv).getLabel(), "Preset")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$map$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            destination$iv$iv2.add(((GalaxyRow) item$iv$iv).getValue());
        }
        Iterable $this$filter$iv2 = (List) destination$iv$iv2;
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            String it = (String) element$iv$iv2;
            if ((StringsKt.isBlank(it) || Intrinsics.areEqual(it, "Selected Profile")) ? false : true) {
                destination$iv$iv3.add(element$iv$iv2);
            }
        }
        List presets = (List) destination$iv$iv3;
        TextView textView = (TextView) $cells.get($p);
        if (textView != null) {
            String[] strArr = new String[3];
            if (low != null) {
                double it2 = low.doubleValue();
                str = "Town " + GalaxyRow.Companion.fmt(it2) + " s · " + Math.round(INSTANCE.gapFeet(it2, 30.0d)) + " ft";
            } else {
                str = null;
            }
            strArr[0] = str;
            if (high != null) {
                double it3 = high.doubleValue();
                str2 = "Highway " + GalaxyRow.Companion.fmt(it3) + " s · " + Math.round(INSTANCE.gapFeet(it3, 65.0d)) + " ft";
            } else {
                str2 = null;
            }
            strArr[1] = str2;
            String it4 = (String) CollectionsKt.firstOrNull(presets);
            strArr[2] = it4 != null ? "Follow: " + it4 : null;
            textView.setText(CollectionsKt.joinToString(CollectionsKt.listOfNotNull((Object[]) strArr), "\n", "", "", -1, "...", null));
        }
        return Unit.INSTANCE;
    }

    private final Set<Integer> conditions(final Context ctx, CarUi ui, String page, List<GalaxyRow> rows, LinearLayout col, final Function2<? super GalaxyRow, ? super String, Unit> write) {
        int i;
        int i2;
        int i3;
        String str = page;
        List<GalaxyRow> $this$filter$iv = rows;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GalaxyRow it = (GalaxyRow) element$iv$iv;
            if (it.getControl() == GalaxyRow.Control.SWITCH) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List<GalaxyRow> mine = (List) destination$iv$iv;
        if (mine.isEmpty()) {
            return SetsKt.emptySet();
        }
        LinearLayout c = card(ctx, ui);
        List $this$filter$iv2 = mine;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            GalaxyRow it2 = (GalaxyRow) element$iv$iv2;
            if (Intrinsics.areEqual(it2.getValue(), "On")) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        Iterable $this$map$iv = (List) destination$iv$iv2;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            GalaxyRow it3 = (GalaxyRow) item$iv$iv;
            String lowerCase = it3.getLabel().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            destination$iv$iv3.add(lowerCase);
        }
        List on = (List) destination$iv$iv3;
        TextView $this$conditions_u24lambda_u2462 = new TextView(ctx);
        $this$conditions_u24lambda_u2462.setText((StringsKt.endsWith(str, "cem", false) ? "Switches to Experimental for: " : "Switches to Chill for: ") + (on.isEmpty() ? "nothing (all off)" : CollectionsKt.joinToString(on, ", ", "", "", -1, "...", null)));
        $this$conditions_u24lambda_u2462.setTextColor(CarUi.Companion.getTEXT());
        $this$conditions_u24lambda_u2462.setTextSize(19.0f);
        c.addView($this$conditions_u24lambda_u2462);
        FlowRow $this$conditions_u24lambda_u2463 = new FlowRow(ctx, ui.dp(10), ui.dp(10));
        $this$conditions_u24lambda_u2463.setPadding(0, ui.dp(14), 0, 0);
        for (final GalaxyRow r : mine) {
            SettingIcons.Icon ic = SettingIcons.INSTANCE.forRow(str, r.getLabel(), "", r.getControl());
            final boolean lit = Intrinsics.areEqual(r.getValue(), "On");
            LinearLayout $this$conditions_u24lambda_u2468 = new LinearLayout(ctx);
            $this$conditions_u24lambda_u2468.setOrientation(0);
            $this$conditions_u24lambda_u2468.setGravity(16);
            $this$conditions_u24lambda_u2468.setPadding(ui.dp(10), ui.dp(8), ui.dp(18), ui.dp(8));
            $this$conditions_u24lambda_u2468.setMinimumHeight(ui.dp(60));
            int rgb = lit ? Color.rgb(19, 34, 25) : CarUi.Companion.getCARD_ON();
            if (lit) {
                i = 107;
                i2 = 75;
                i3 = 43;
            } else {
                i = 51;
                i2 = 66;
                i3 = 44;
            }
            $this$conditions_u24lambda_u2468.setBackground(ui.rounded(rgb, 14, Integer.valueOf(Color.rgb(i3, i, i2))));
            View chip = SvIcon.INSTANCE.chip(ctx, ic.getName(), ic.getGroup(), 40, lit ? SvIcon.State.ON : SvIcon.State.OFF);
            LinearLayout.LayoutParams $this$conditions_u24lambda_u2468_u24lambda_u2464 = new LinearLayout.LayoutParams(ui.dp(40), ui.dp(40));
            $this$conditions_u24lambda_u2468_u24lambda_u2464.rightMargin = ui.dp(10);
            Unit unit = Unit.INSTANCE;
            $this$conditions_u24lambda_u2468.addView(chip, $this$conditions_u24lambda_u2468_u24lambda_u2464);
            TextView $this$conditions_u24lambda_u2468_u24lambda_u2465 = new TextView(ctx);
            $this$conditions_u24lambda_u2468_u24lambda_u2465.setText(r.getLabel());
            CarUi.Companion_ companion = CarUi.Companion;
            $this$conditions_u24lambda_u2468_u24lambda_u2465.setTextColor(lit ? companion.getTEXT() : companion.getDIM());
            $this$conditions_u24lambda_u2468_u24lambda_u2465.setTextSize(17.0f);
            $this$conditions_u24lambda_u2468.addView($this$conditions_u24lambda_u2468_u24lambda_u2465);
            $this$conditions_u24lambda_u2468.setAlpha(r.getEditable() ? 1.0f : 0.45f);
            $this$conditions_u24lambda_u2468.setClickable(true);
            $this$conditions_u24lambda_u2468.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda12
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GalaxyVisuals.conditions$lambda$68$lambda$66(it3, write, lit, view);
                }
            });
            $this$conditions_u24lambda_u2468.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.GalaxyVisuals$$ExternalSyntheticLambda13
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    boolean conditions$lambda$68$lambda$67;
                    conditions$lambda$68$lambda$67 = GalaxyVisuals.conditions$lambda$68$lambda$67(ctx, r, view);
                    return conditions$lambda$68$lambda$67;
                }
            });
            $this$conditions_u24lambda_u2463.addView($this$conditions_u24lambda_u2468, new ViewGroup.LayoutParams(-2, -2));
            str = page;
        }
        c.addView($this$conditions_u24lambda_u2463);
        c.addView(caption(ctx, ui, "Tap a condition to turn it on or off; hold it for what it does and its default. The speeds and times for them are below."));
        col.addView(c, CarUi.lp$default(ui, 0, 12, 1, null));
        List $this$map$iv2 = mine;
        Collection destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        for (Object item$iv$iv2 : $this$map$iv2) {
            GalaxyRow it4 = (GalaxyRow) item$iv$iv2;
            destination$iv$iv4.add(Integer.valueOf(it4.getIndex()));
            $this$map$iv2 = $this$map$iv2;
        }
        return CollectionsKt.toSet((List) destination$iv$iv4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void conditions$lambda$68$lambda$66(GalaxyRow $r, Function2 $write, boolean $lit, View it) {
        if ($r.getEditable()) {
            $write.invoke($r, $lit ? "Off" : "On");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean conditions$lambda$68$lambda$67(Context $ctx, GalaxyRow $r, View it) {
        INSTANCE.about($ctx, $r);
        return true;
    }

    private final void bands(Context ctx, CarUi ui, List<GalaxyRow> rows, LinearLayout col) {
        String str;
        Iterable $this$all$iv;
        int $i$f$mapNotNull;
        Iterable $this$mapNotNullTo$iv$iv;
        int $i$f$mapNotNullTo;
        Iterable $this$forEach$iv$iv$iv;
        Triple triple;
        List<GalaxyRow> $this$filter$iv = rows;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GalaxyRow it = (GalaxyRow) element$iv$iv;
            if (new Regex("mph offset$|mph and above$|km/h offset$|km/h and above$", RegexOption.IGNORE_CASE).containsMatchIn(it.getLabel())) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$mapNotNull$iv = (List) destination$iv$iv;
        int $i$f$mapNotNull2 = 0;
        Collection destination$iv$iv2 = new ArrayList();
        Iterable $this$mapNotNullTo$iv$iv2 = $this$mapNotNull$iv;
        int $i$f$mapNotNullTo2 = 0;
        Iterable $this$forEach$iv$iv$iv2 = $this$mapNotNullTo$iv$iv2;
        Iterator it2 = $this$forEach$iv$iv$iv2.iterator();
        while (true) {
            str = "";
            if (!it2.hasNext()) {
                break;
            }
            Object element$iv$iv$iv = it2.next();
            GalaxyRow r = (GalaxyRow) element$iv$iv$iv;
            Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            Double num = INSTANCE.num(r);
            if (num != null) {
                double it3 = num.doubleValue();
                $i$f$mapNotNull = $i$f$mapNotNull2;
                $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                $i$f$mapNotNullTo = $i$f$mapNotNullTo2;
                $this$forEach$iv$iv$iv = $this$forEach$iv$iv$iv2;
                triple = new Triple(StringsKt.replace(StringsKt.replace(new Regex(" offset$| and above$", RegexOption.IGNORE_CASE).replace(r.getLabel(), ""), " mph", "", false), " km/h", "", false), Double.valueOf(it3), r.getUnit());
            } else {
                $i$f$mapNotNull = $i$f$mapNotNull2;
                $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                $i$f$mapNotNullTo = $i$f$mapNotNullTo2;
                $this$forEach$iv$iv$iv = $this$forEach$iv$iv$iv2;
                triple = null;
            }
            if (triple != null) {
                destination$iv$iv2.add(triple);
            }
            $this$mapNotNull$iv = $this$mapNotNull$iv2;
            $this$mapNotNullTo$iv$iv2 = $this$mapNotNullTo$iv$iv;
            $i$f$mapNotNull2 = $i$f$mapNotNull;
            $i$f$mapNotNullTo2 = $i$f$mapNotNullTo;
            $this$forEach$iv$iv$iv2 = $this$forEach$iv$iv$iv;
        }
        List bands = (List) destination$iv$iv2;
        if (bands.size() < 2) {
            return;
        }
        LinearLayout c = card(ctx, ui);
        TextView $this$bands_u24lambda_u2474 = new TextView(ctx);
        List $this$all$iv2 = bands;
        if (!($this$all$iv2 instanceof Collection) || !$this$all$iv2.isEmpty()) {
            Iterator it4 = $this$all$iv2.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    $this$all$iv = 1;
                    break;
                }
                Object element$iv = it4.next();
                Triple it5 = (Triple) element$iv;
                Triple it6 = ((Number) it5.getSecond()).doubleValue() == 0.0d ? 1 : null;
                if (it6 == null) {
                    $this$all$iv = null;
                    break;
                }
            }
        } else {
            $this$all$iv = 1;
        }
        $this$bands_u24lambda_u2474.setText($this$all$iv != null ? "Offset over the limit: none - drives at the limit" : "Offset over the limit, by the limit's speed");
        $this$bands_u24lambda_u2474.setTextColor(CarUi.Companion.getTEXT());
        $this$bands_u24lambda_u2474.setTextSize(19.0f);
        c.addView($this$bands_u24lambda_u2474);
        BandsView bandsView = new BandsView(ctx, bands, SvIcon.INSTANCE.fg("speed"));
        LinearLayout.LayoutParams $this$bands_u24lambda_u2475 = new LinearLayout.LayoutParams(-1, ui.dp(190));
        $this$bands_u24lambda_u2475.topMargin = ui.dp(10);
        Unit unit = Unit.INSTANCE;
        c.addView(bandsView, $this$bands_u24lambda_u2475);
        String[] strArr = new String[2];
        GalaxyRow find = find(rows, "^primary source");
        strArr[0] = find != null ? find.getValue() : null;
        GalaxyRow find2 = find(rows, "^secondary source");
        strArr[1] = find2 != null ? find2.getValue() : null;
        List src = CollectionsKt.listOfNotNull((Object[]) strArr);
        if (!src.isEmpty()) {
            String joinToString$default = CollectionsKt.joinToString(src, ", then ", "", "", -1, "...", null);
            GalaxyRow it7 = find(rows, "^require confirmation");
            if (it7 != null && Intrinsics.areEqual(it7.getValue(), "On")) {
                str = " · you confirm each new limit";
            }
            c.addView(caption(ctx, ui, "Limit from: " + joinToString$default + str));
        }
        col.addView(c, CarUi.lp$default(ui, 0, 12, 1, null));
    }

    private final void curve(Context ctx, CarUi ui, List<GalaxyRow> rows, LinearLayout col) {
        Double doubleOrNull;
        String str;
        String str2;
        String value;
        String value2;
        List<String> groupValues;
        String str3;
        GalaxyRow progress = find(rows, "^learning progress");
        if (progress == null) {
            return;
        }
        MatchResult find$default = (new Regex("(\\d+(?:\\.\\d+)?)\\s*%")).find(progress.getValue(), 0);
        if ((find$default == null || (groupValues = find$default.getGroupValues()) == null || (str3 = groupValues.get(1)) == null || (doubleOrNull = StringsKt.toDoubleOrNull(str3)) == null) && (doubleOrNull = StringsKt.toDoubleOrNull(progress.getValue())) == null) {
            return;
        }
        double pct = doubleOrNull.doubleValue();
        LinearLayout c = card(ctx, ui);
        LinearLayout line = new LinearLayout(ctx);
        line.setOrientation(0);
        line.setGravity(16);
        RingView ringView = new RingView(ctx, pct / 100.0d, SvIcon.INSTANCE.fg("speed"));
        LinearLayout.LayoutParams $this$curve_u24lambda_u2478 = new LinearLayout.LayoutParams(ui.dp(120), ui.dp(120));
        $this$curve_u24lambda_u2478.rightMargin = ui.dp(22);
        Unit unit = Unit.INSTANCE;
        line.addView(ringView, $this$curve_u24lambda_u2478);
        LinearLayout $this$curve_u24lambda_u2483 = new LinearLayout(ctx);
        $this$curve_u24lambda_u2483.setOrientation(1);
        TextView $this$curve_u24lambda_u2483_u24lambda_u2479 = new TextView(ctx);
        $this$curve_u24lambda_u2483_u24lambda_u2479.setText("Curve learning");
        $this$curve_u24lambda_u2483_u24lambda_u2479.setTextColor(CarUi.Companion.getTEXT());
        $this$curve_u24lambda_u2483_u24lambda_u2479.setTextSize(20.0f);
        $this$curve_u24lambda_u2483_u24lambda_u2479.setTypeface(Typeface.DEFAULT_BOLD);
        $this$curve_u24lambda_u2483.addView($this$curve_u24lambda_u2483_u24lambda_u2479);
        GalaxyVisuals galaxyVisuals = INSTANCE;
        String[] strArr = new String[2];
        GalaxyRow find = INSTANCE.find(rows, "^saved learning");
        if (find == null || (value2 = find.getValue()) == null) {
            str = null;
        } else {
            String str4 = value2;
            if (StringsKt.isBlank(str4)) {
                str4 = null;
            }
            str = str4;
        }
        strArr[0] = str;
        GalaxyRow find2 = INSTANCE.find(rows, "^saved cornering");
        if (find2 == null || (value = find2.getValue()) == null) {
            str2 = null;
        } else {
            String str5 = value;
            if (StringsKt.isBlank(str5)) {
                str5 = null;
            }
            str2 = str5;
        }
        strArr[1] = str2;
        String joinToString$default = CollectionsKt.joinToString(CollectionsKt.listOfNotNull((Object[]) strArr), "\n", "", "", -1, "...", null);
        if (StringsKt.isBlank(joinToString$default)) {
            joinToString$default = "Learns how fast you like to take curves as you drive them.";
        }
        $this$curve_u24lambda_u2483.addView(galaxyVisuals.caption(ctx, ui, joinToString$default));
        line.addView($this$curve_u24lambda_u2483, new LinearLayout.LayoutParams(0, -2, 1.0f));
        c.addView(line);
        col.addView(c, CarUi.lp$default(ui, 0, 12, 1, null));
    }

    private final void lane(Context ctx, CarUi ui, List<GalaxyRow> rows, LinearLayout col) {
        Double num;
        String str;
        GalaxyRow offset = find(rows, "^lane offset");
        if (offset != null && (num = num(offset)) != null) {
            double v = num.doubleValue();
            LinearLayout c = card(ctx, ui);
            String unit = offset.getUnit();
            GalaxyRow.Bounds it = offset.bounds();
            c.addView(new LaneView(ctx, v, unit, it != null ? Math.max(Math.abs(it.getMin()), Math.abs(it.getMax())) : 12.0d, SvIcon.INSTANCE.fg("steer")), new LinearLayout.LayoutParams(-1, ui.dp(150)));
            GalaxyRow strength = find(rows, "strength");
            if (v == 0.0d) {
                str = "Centered in the lane";
            } else {
                str = GalaxyRow.Companion.fmt(Math.abs(v)) + " " + offset.getUnit() + " " + (v > 0.0d ? "right" : "left") + " of center";
            }
            String str2 = "";
            if (strength != null) {
                String str3 = " · centering strength " + GalaxyRow.shown$default(strength, null, 1, null) + (strength.getLaneStrength() ? " of 10" : "");
                if (str3 != null) {
                    str2 = str3;
                }
            }
            c.addView(caption(ctx, ui, str + str2));
            col.addView(c, CarUi.lp$default(ui, 0, 12, 1, null));
        }
    }

    /* compiled from: GalaxyVisuals.kt */
    public static final class GapView extends View {
        private final int color;
        private final double mph;
        private final Paint p;
        private final RectF r;
        private final double seconds;

        /* VF: from Vineflower (jadx: ) */
        public GapView(Context var1, double var2, double var4, int var6) {
            super(var1);
            Intrinsics.checkNotNullParameter(var1, "ctx");
            this.seconds = var2;
            this.mph = var4;
            this.color = var6;
            this.p = new Paint(1);
            this.r = new RectF();
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            Intrinsics.checkNotNullParameter(c, "c");
            float d = getResources().getDisplayMetrics().density;
            float w = getWidth();
            float h = getHeight();
            double feet = GalaxyVisuals.INSTANCE.gapFeet(this.seconds, this.mph);
            float carW = 70 * d;
            float carH = 30 * d;
            float f = 2;
            float usable = (w - (f * carW)) - (160 * d);
            float gapPx = RangesKt.coerceAtLeast((float) ((feet / 286.0d) * usable), 6 * d);
            float y = h / f;
            this.p.setColor(Color.rgb(34, 40, 55));
            float f2 = 10;
            c.drawRoundRect(0.0f, (y - (carH / f)) - (f2 * d), w, y + (carH / f) + (f2 * d), f2 * d, f2 * d, this.p);
            this.p.setColor(this.color);
            float f3 = 12;
            this.r.set(f3 * d, y - (carH / f), (f3 * d) + carW, y + (carH / f));
            float f4 = 9;
            c.drawRoundRect(this.r, f4 * d, f4 * d, this.p);
            this.p.setColor(Color.rgb(170, 182, 200));
            this.r.set((f3 * d) + carW + gapPx, y - (carH / f), (f3 * d) + (f * carW) + gapPx, y + (carH / f));
            c.drawRoundRect(this.r, f4 * d, f4 * d, this.p);
            this.p.setColor(this.color);
            this.p.setStrokeWidth(f * d);
            this.p.setStyle(Paint.Style.STROKE);
            float f5 = 4;
            c.drawLine((f5 * d) + (f3 * d) + carW, y, (((f3 * d) + carW) + gapPx) - (f5 * d), y, this.p);
            this.p.setStyle(Paint.Style.FILL);
            GalaxyVisuals.textPaint.setColor(-1);
            GalaxyVisuals.textPaint.setTextSize(17 * d);
            GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            String label = Math.round(feet) + " ft";
            float f6 = 14;
            c.drawText(label, (((f3 * d) + carW) + (gapPx / f)) - (GalaxyVisuals.textPaint.measureText(label) / f), (y - (carH / f)) - (f6 * d), GalaxyVisuals.textPaint);
            GalaxyVisuals.textPaint.setColor(Color.rgb(150, 160, 180));
            GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT);
            GalaxyVisuals.textPaint.setTextSize(15 * d);
            String right = ((int) this.mph) + " mph · " + GalaxyRow.Companion.fmt(this.seconds) + " s";
            c.drawText(right, (w - GalaxyVisuals.textPaint.measureText(right)) - (f6 * d), y + (5 * d), GalaxyVisuals.textPaint);
        }
    }

    /* compiled from: GalaxyVisuals.kt */
    public static final class BandsView extends View {
        private final List<Triple<String, Double, String>> bands;
        private final int color;
        private final Paint p;

        /* VF: from Vineflower (jadx: ) */
        public BandsView(Context var1, List<Triple<String, Double, String>> var2, int var3) {
            super(var1);
            Intrinsics.checkNotNullParameter(var1, "ctx");
            Intrinsics.checkNotNullParameter(var2, "bands");
            this.bands = var2;
            this.color = var3;
            this.p = new Paint(1);
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            char c2;
            char c3;
            char c4;
            int i;
            BandsView bandsView = this;
            Intrinsics.checkNotNullParameter(c, "c");
            float d = getResources().getDisplayMetrics().density;
            float y = getWidth();
            float h = getHeight();
            float top = 26 * d;
            float bottom = h - (30 * d);
            Iterator<T> it = bandsView.bands.iterator();
            if (!it.hasNext()) {
                throw new NoSuchElementException();
            }
            Triple it2 = (Triple) it.next();
            double doubleValue = ((Number) it2.getSecond()).doubleValue();
            while (it.hasNext()) {
                Triple it3 = (Triple) it.next();
                doubleValue = Math.max(doubleValue, ((Number) it3.getSecond()).doubleValue());
            }
            double hi = Math.max(doubleValue, 1.0d);
            Iterator<T> it4 = bandsView.bands.iterator();
            if (!it4.hasNext()) {
                throw new NoSuchElementException();
            }
            Triple it5 = (Triple) it4.next();
            double doubleValue2 = ((Number) it5.getSecond()).doubleValue();
            while (it4.hasNext()) {
                Triple it6 = (Triple) it4.next();
                doubleValue2 = Math.min(doubleValue2, ((Number) it6.getSecond()).doubleValue());
            }
            double lo = Math.min(doubleValue2, 0.0d);
            float zero = top + ((float) (((hi - 0) / (hi - lo)) * (bottom - top)));
            float slot = y / bandsView.bands.size();
            float bw = slot * 0.56f;
            bandsView.p.setColor(Color.rgb(60, 66, 78));
            float f = 2;
            float zero2 = zero;
            c.drawRect(0.0f, zero - (d / f), y, zero + (d / f), bandsView.p);
            Iterator<T> it7 = bandsView.bands.iterator();
            int i2 = 0;
            while (it7.hasNext()) {
                int i3 = i2;
                int i4 = i2 + 1;
                Triple b = (Triple) it7.next();
                float x = (i3 * slot) + ((slot - bw) / f);
                float y2 = top + ((float) (((hi - ((Number) b.getSecond()).doubleValue()) / (hi - lo)) * (bottom - top)));
                Paint paint = bandsView.p;
                if (((Number) b.getSecond()).doubleValue() == 0.0d) {
                    c2 = '<';
                    c3 = 'B';
                    c4 = 'N';
                    i = Color.rgb(60, 66, 78);
                } else {
                    c2 = '<';
                    c3 = 'B';
                    c4 = 'N';
                    i = bandsView.color;
                }
                paint.setColor(i);
                float zero3 = zero2;
                float f2 = 6;
                float w = f2 * d;
                float w2 = y;
                c.drawRoundRect(x, Math.min(y2, zero3) - ((((Number) b.getSecond()).doubleValue() > 0.0d ? 1 : (((Number) b.getSecond()).doubleValue() == 0.0d ? 0 : -1)) == 0 ? f * d : 0.0f), x + bw, Math.max(y2, zero3) + (((Number) b.getSecond()).doubleValue() == 0.0d ? f * d : 0.0f), f2 * d, w, bandsView.p);
                GalaxyVisuals.textPaint.setColor(-1);
                GalaxyVisuals.textPaint.setTextSize(15 * d);
                GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                String str = "";
                String str2 = ((Number) b.getSecond()).doubleValue() > 0.0d ? "+" : "";
                String fmt = GalaxyRow.Companion.fmt(((Number) b.getSecond()).doubleValue());
                if (Intrinsics.areEqual(b.getThird(), "%")) {
                    str = "%";
                }
                String v = str2 + fmt + str;
                c.drawText(v, (x + (bw / f)) - (GalaxyVisuals.textPaint.measureText(v) / f), Math.min(y2, zero3) - (7 * d), GalaxyVisuals.textPaint);
                GalaxyVisuals.textPaint.setColor(Color.rgb(150, 160, 180));
                GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT);
                GalaxyVisuals.textPaint.setTextSize(13 * d);
                c.drawText((String) b.getFirst(), (x + (bw / f)) - (GalaxyVisuals.textPaint.measureText((String) b.getFirst()) / f), h - (8 * d), GalaxyVisuals.textPaint);
                zero2 = zero3;
                i2 = i4;
                y = w2;
                bandsView = this;
            }
        }
    }

    /* compiled from: GalaxyVisuals.kt */
    public static final class RingView extends View {
        private final int color;
        private final double frac;
        private final Paint p;

        /* VF: from Vineflower (jadx: ) */
        public RingView(Context var1, double var2, int var4) {
            super(var1);
            Intrinsics.checkNotNullParameter(var1, "ctx");
            this.frac = var2;
            this.color = var4;
            Paint var5 = new Paint(1);
            var5.setStyle(android.graphics.Paint.Style.STROKE);
            var5.setStrokeCap(android.graphics.Paint.Cap.ROUND);
            this.p = var5;
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            Intrinsics.checkNotNullParameter(c, "c");
            float d = getResources().getDisplayMetrics().density;
            float s = Math.min(getWidth(), getHeight());
            float f = 10;
            RectF r = new RectF(f * d, f * d, s - (f * d), s - (f * d));
            this.p.setStrokeWidth(12 * d);
            this.p.setColor(Color.rgb(34, 40, 55));
            c.drawArc(r, 0.0f, 360.0f, false, this.p);
            this.p.setColor(this.color);
            c.drawArc(r, -90.0f, (float) (360 * RangesKt.coerceIn(this.frac, 0.0d, 1.0d)), false, this.p);
            GalaxyVisuals.textPaint.setColor(-1);
            GalaxyVisuals.textPaint.setTextSize(26 * d);
            GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            String t = Math.round(this.frac * 100) + "%";
            float f2 = 2;
            c.drawText(t, (s / f2) - (GalaxyVisuals.textPaint.measureText(t) / f2), (s / f2) + (9 * d), GalaxyVisuals.textPaint);
        }
    }

    /* compiled from: GalaxyVisuals.kt */
    public static final class LaneView extends View {
        private final int color;
        private final double offset;
        private final Paint p;
        private final double range;
        private final String unit;

        /* VF: from Vineflower (jadx: ) */
        public LaneView(Context var1, double var2, String var4, double var5, int var7) {
            super(var1);
            Intrinsics.checkNotNullParameter(var1, "ctx");
            Intrinsics.checkNotNullParameter(var4, "unit");
            this.offset = var2;
            this.unit = var4;
            this.range = var5;
            this.color = var7;
            this.p = new Paint(1);
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            String t;
            Intrinsics.checkNotNullParameter(c, "c");
            float d = getResources().getDisplayMetrics().density;
            float w = getWidth();
            float h = getHeight();
            float laneW = Math.min(0.5f * w, 420 * d);
            float f = 2;
            float left = (w - laneW) / f;
            this.p.setColor(Color.rgb(28, 32, 42));
            c.drawRect(left, 0.0f, left + laneW, h, this.p);
            this.p.setColor(-1);
            this.p.setStrokeWidth(4 * d);
            c.drawLine(left, 0.0f, left, h, this.p);
            c.drawLine(left + laneW, 0.0f, left + laneW, h, this.p);
            this.p.setColor(Color.rgb(80, 88, 104));
            this.p.setStrokeWidth(f * d);
            for (float y = 6 * d; y < h; y += 26 * d) {
                c.drawLine(w / f, y, w / f, y + (14 * d), this.p);
            }
            float carW = laneW * 0.42f;
            float shift = (float) ((this.offset / this.range) * laneW * 0.25d);
            this.p.setColor(this.color);
            float f2 = 18;
            c.drawRoundRect(((w / f) - (carW / f)) + shift, h * 0.22f, (w / f) + (carW / f) + shift, h * 0.86f, f2 * d, f2 * d, this.p);
            GalaxyVisuals.textPaint.setColor(Color.rgb(11, 13, 18));
            GalaxyVisuals.textPaint.setTextSize(16 * d);
            GalaxyVisuals.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            if (this.offset == 0.0d) {
                t = "center";
            } else {
                t = GalaxyRow.Companion.fmt(Math.abs(this.offset)) + " " + this.unit + " " + (this.offset > 0.0d ? "→" : "←");
            }
            c.drawText(t, ((w / f) + shift) - (GalaxyVisuals.textPaint.measureText(t) / f), 0.56f * h, GalaxyVisuals.textPaint);
        }
    }

    /* compiled from: GalaxyVisuals.kt */
    public static final class FaderView extends View {
        private final int color;
        private final int last;
        private Function1<? super String, Unit> onCommit;
        private Function1<? super String, Unit> onMove;
        private final Paint p;
        private final List<String> positions;
        private String value;

        /* VF: from Vineflower (jadx: ) */
        public FaderView(Context var1, int var2, List<String> var3) {
            super(var1);
            Intrinsics.checkNotNullParameter(var1, "ctx");
            Intrinsics.checkNotNullParameter(var3, "positions");
            this.color = var2;
            this.positions = var3;
            this.value = CollectionsKt.first(this.positions);
            this.p = new Paint(1);
            this.last = Math.max(this.positions.size() - 1, 1);
        }

        public final String getValue() {
            return this.value;
        }

        public final void setValue(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.value = str;
        }

        public final Function1<String, Unit> getOnMove() {
            return this.onMove;
        }

        public final void setOnMove(Function1<? super String, Unit> function1) {
            this.onMove = function1;
        }

        public final Function1<String, Unit> getOnCommit() {
            return this.onCommit;
        }

        public final void setOnCommit(Function1<? super String, Unit> function1) {
            this.onCommit = function1;
        }

        private final int posOf(String v) {
            Integer valueOf = Integer.valueOf(this.positions.indexOf(v));
            int it = valueOf.intValue();
            boolean z = it >= 0;
            Integer num = null;
            if (!z) {
                valueOf = null;
            }
            if (valueOf != null) {
                return valueOf.intValue();
            }
            Double doubleOrNull = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) StringsKt.removeSuffix(v, (CharSequence) "%")).toString());
            if (doubleOrNull != null) {
                double x = doubleOrNull.doubleValue();
                Iterable $this$minByOrNull$iv = CollectionsKt.getIndices(this.positions);
                Iterator iterator$iv = $this$minByOrNull$iv.iterator();
                if (iterator$iv.hasNext()) {
                    num = iterator$iv.next();
                    if (iterator$iv.hasNext()) {
                        int it2 = num.intValue();
                        Double doubleOrNull2 = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) StringsKt.removeSuffix(this.positions.get(it2), (CharSequence) "%")).toString());
                        double minValue$iv = Math.abs((doubleOrNull2 != null ? doubleOrNull2.doubleValue() : 1.0E9d) - x);
                        do {
                            Integer next = iterator$iv.next();
                            int it3 = next.intValue();
                            Double doubleOrNull3 = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) StringsKt.removeSuffix(this.positions.get(it3), (CharSequence) "%")).toString());
                            double v$iv = Math.abs((doubleOrNull3 != null ? doubleOrNull3.doubleValue() : 1.0E9d) - x);
                            if (Double.compare(minValue$iv, v$iv) > 0) {
                                num = next;
                                minValue$iv = v$iv;
                            }
                        } while (iterator$iv.hasNext());
                    }
                }
                num = num;
            }
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }

        @Override // android.view.View
        protected void onDraw(Canvas c) {
            Intrinsics.checkNotNullParameter(c, "c");
            float d = getResources().getDisplayMetrics().density;
            float cx = getWidth() / 2.0f;
            float f = 16;
            float top = f * d;
            float bot = getHeight() - (f * d);
            float y = bot - ((posOf(this.value) / this.last) * (bot - top));
            this.p.setColor(Color.rgb(42, 49, 66));
            float f2 = 6;
            c.drawRoundRect(cx - (f2 * d), top, cx + (f2 * d), bot, f2 * d, f2 * d, this.p);
            this.p.setColor(Intrinsics.areEqual(this.value, "Auto") ? Color.rgb(120, 200, 255) : this.color);
            c.drawRoundRect(cx - (f2 * d), y, cx + (f2 * d), bot, f2 * d, f2 * d, this.p);
            this.p.setColor(-1);
            c.drawCircle(cx, y, 15 * d, this.p);
        }

        /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
        public boolean onTouchEvent(MotionEvent var1) {
            Intrinsics.checkNotNullParameter(var1, "e");
            if (!this.isEnabled()) {
                return false;
            } else {
                float var2 = this.getResources().getDisplayMetrics().density;
                float var3 = 16;
                float var4 = this.getHeight() - var3 * var2;
                String var5 = this.positions
                    .get(
                        RangesKt.coerceIn(
                            Math.round(RangesKt.coerceIn((var4 - var1.getY()) / (var4 - var3 * var2), 0.0F, 1.0F) * this.last), 0, this.positions.size() - 1
                        )
                    );
                switch (var1.getActionMasked()) {
                    case 0:
                        ViewParent var8 = this.getParent();
                        if (var8 != null) {
                            var8.requestDisallowInterceptTouchEvent(true);
                        }

                        this.value = var5;
                        Function1 var9 = this.onMove;
                        if (var9 != null) {
                            var9.invoke(var5);
                        }

                        this.invalidate();
                        break;
                    case 1:
                        Function1 var7 = this.onCommit;
                        if (var7 != null) {
                            var7.invoke(this.value);
                        }
                        break;
                    case 2:
                        if (!Intrinsics.areEqual(var5, this.value)) {
                            this.value = var5;
                            Function1 var6 = this.onMove;
                            if (var6 != null) {
                                var6.invoke(var5);
                            }

                            this.invalidate();
                        }
                }

                return true;
            }
        }
    }
}
