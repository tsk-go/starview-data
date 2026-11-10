package com.starpilot.starview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GxVehicle.kt */
public final class GxVehicleKt {
    private static final int GX_VEHICLE_ERR = Color.rgb(255, 107, 107);

    /* JADX INFO: Access modifiers changed from: private */
    public static final void gxVehicleEnable(View v, boolean on) {
        v.setEnabled(on);
        v.setAlpha(on ? 1.0f : 0.4f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean gxVehicleOpenUrl(Activity act, String url) {
        Object m1191constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            act.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)).addFlags(268435456));
            m1191constructorimpl = Result.m1191constructorimpl(true);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1197isFailureimpl(m1191constructorimpl)) {
            m1191constructorimpl = false;
        }
        return ((Boolean) m1191constructorimpl).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TextView gxVehicleNotice(GxPage page, String text, int color, ViewGroup into) {
        TextView $this$gxVehicleNotice_u24lambda_u241 = new TextView(page.getAct());
        $this$gxVehicleNotice_u24lambda_u241.setText(text);
        $this$gxVehicleNotice_u24lambda_u241.setTextColor(color);
        $this$gxVehicleNotice_u24lambda_u241.setTextSize(17.0f);
        $this$gxVehicleNotice_u24lambda_u241.setPadding(page.getUi().dp(16), page.getUi().dp(12), page.getUi().dp(16), page.getUi().dp(12));
        $this$gxVehicleNotice_u24lambda_u241.setBackground(page.getUi().rounded(Color.argb(40, Color.red(color), Color.green(color), Color.blue(color)), 12, Integer.valueOf(Color.argb(120, Color.red(color), Color.green(color), Color.blue(color)))));
        into.addView($this$gxVehicleNotice_u24lambda_u241, CarUi.lp$default(page.getUi(), 0, 0, 3, null));
        return $this$gxVehicleNotice_u24lambda_u241;
    }

    static /* synthetic */ List gxVehicleButtons$default(GxPage gxPage, ViewGroup viewGroup, List list, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return gxVehicleButtons(gxPage, viewGroup, list, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<TextView> gxVehicleButtons(GxPage page, ViewGroup into, List<? extends Triple<String, Boolean, ? extends Function0<Unit>>> list, boolean accentFirst) {
        List<? extends Triple<String, Boolean, ? extends Function0<Unit>>> $this$map$iv = list;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Triple it = (Triple) item$iv$iv;
            destination$iv$iv.add(TuplesKt.to(it.getFirst(), it.getThird()));
        }
        Collection thisCollection$iv = (List) destination$iv$iv;
        Pair[] pairArr = (Pair[]) thisCollection$iv.toArray(new Pair[0]);
        LinearLayout bar = page.buttons((Pair[]) Arrays.copyOf(pairArr, pairArr.length), into, accentFirst);
        ArrayList out = new ArrayList();
        int childCount = bar.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = bar.getChildAt(i);
            Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
            TextView b = (TextView) childAt;
            gxVehicleEnable(b, list.get(i).getSecond().booleanValue());
            out.add(b);
        }
        return out;
    }

    static /* synthetic */ EditText gxVehicleEdit$default(GxPage gxPage, String str, String str2, int i, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            i = 1;
        }
        return gxVehicleEdit(gxPage, str, str2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final EditText gxVehicleEdit(GxPage page, String hint, String value, int inputType) {
        EditText $this$gxVehicleEdit_u24lambda_u243 = new EditText(page.getAct());
        $this$gxVehicleEdit_u24lambda_u243.setText(value);
        $this$gxVehicleEdit_u24lambda_u243.setHint(hint);
        $this$gxVehicleEdit_u24lambda_u243.setInputType(inputType);
        $this$gxVehicleEdit_u24lambda_u243.setTextSize(20.0f);
        $this$gxVehicleEdit_u24lambda_u243.setTextColor(CarUi.Companion.getTEXT());
        $this$gxVehicleEdit_u24lambda_u243.setHintTextColor(CarUi.Companion.getDIM());
        $this$gxVehicleEdit_u24lambda_u243.setSingleLine(true);
        return $this$gxVehicleEdit_u24lambda_u243;
    }
}
