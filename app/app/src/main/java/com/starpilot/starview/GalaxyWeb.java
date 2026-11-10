package com.starpilot.starview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import android.view.ViewGroup.LayoutParams;

/* compiled from: GalaxyWeb.kt */
public final class GalaxyWeb extends LinearLayout {
    private final FrameLayout frame;
    private final String host;
    private final Function0<Boolean> onClose;
    private final String path;
    private final TextView placeholder;
    private final CarUi ui;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ GalaxyWeb(Context context, CarUi carUi, String str, String str2, String str3, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, carUi, str, str2, str3, r6);
        Function0 function02;
        if ((i & 32) == 0) {
            function02 = function0;
        } else {
            function02 = new Function0() { // from class: com.starpilot.starview.GalaxyWeb$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean _init_$lambda$0;
                    _init_$lambda$0 = GalaxyWeb._init_$lambda$0();
                    return Boolean.valueOf(_init_$lambda$0);
                }
            };
        }
    }

    /* VF: from Vineflower (jadx: ) */
    public GalaxyWeb(Context var1, CarUi var2, String var3, String var4, String var5, Function0<Boolean> var6) {
        super(var1);
        Intrinsics.checkNotNullParameter(var1, "ctx");
        Intrinsics.checkNotNullParameter(var2, "ui");
        Intrinsics.checkNotNullParameter(var3, "host");
        Intrinsics.checkNotNullParameter(var4, "path");
        Intrinsics.checkNotNullParameter(var5, "title");
        Intrinsics.checkNotNullParameter(var6, "onClose");
        this.ui = var2;
        this.host = var3;
        this.path = var4;
        this.onClose = var6;
        this.frame = new FrameLayout(var1);
        TextView var8 = new TextView(var1);
        var8.setText("Opening…");
        var8.setTextColor(CarUi.Companion.getDIM());
        var8.setTextSize(18.0F);
        var8.setGravity(17);
        var8.setBackgroundColor(CarUi.Companion.getFRAME());
        this.placeholder = var8;
        this.setOrientation(1);
        LinearLayout var9 = new LinearLayout(var1);
        var9.setOrientation(0);
        var9.setGravity(16);
        var9.setPadding(this.ui.dp(4), 0, this.ui.dp(4), this.ui.dp(8));
        TextView var10 = new TextView(var1);
        var10.setText("‹  Back");
        var10.setTextColor(CarUi.Companion.getACCENT());
        var10.setTextSize(18.0F);
        var10.setTypeface(Typeface.DEFAULT_BOLD);
        var10.setPadding(this.ui.dp(6), this.ui.dp(6), this.ui.dp(16), this.ui.dp(6));
        var10.setOnClickListener(new GalaxyWeb$$ExternalSyntheticLambda1(this));
        var9.addView((View)var10);
        TextView var11 = new TextView(var1);
        var11.setText(var5);
        var11.setTextColor(CarUi.Companion.getTEXT());
        var11.setTextSize(22.0F);
        var11.setTypeface(Typeface.DEFAULT_BOLD);
        var9.addView((View)var11, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
        TextView var7 = new TextView(var1);
        var7.setText("↻");
        var7.setTextColor(CarUi.Companion.getDIM());
        var7.setTextSize(24.0F);
        var7.setPadding(this.ui.dp(16), 0, this.ui.dp(10), 0);
        var7.setOnClickListener(new GalaxyWeb$$ExternalSyntheticLambda2());
        var9.addView((View)var7);
        this.addView((View)var9);
        this.frame.setBackgroundColor(CarUi.Companion.getFRAME());
        this.addView((View)this.frame, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
        this.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean _init_$lambda$0() {
        return false;
    }

    static final void lambda$4$lambda$3(GalaxyWeb this$0, View it) {
        if (this$0.back()) {
            return;
        }
        this$0.show();
    }

    public final void show() {
        GalaxyWebPool galaxyWebPool = GalaxyWebPool.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        WebView web = galaxyWebPool.get(context, this.host);
        if (web.getParent() != this.frame) {
            ViewParent parent = web.getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeView(web);
            }
            this.frame.removeAllViews();
            this.frame.addView(web, new FrameLayout.LayoutParams(-1, -1));
            this.frame.addView(this.placeholder, new FrameLayout.LayoutParams(-1, -1));
        }
        this.placeholder.setVisibility(GalaxyWebPool.INSTANCE.getReady() ? 8 : 0);
        GalaxyWebPool.INSTANCE.whenReady(new Function0() { // from class: com.starpilot.starview.GalaxyWeb$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit show$lambda$8;
                show$lambda$8 = GalaxyWeb.show$lambda$8(GalaxyWeb.this);
                return show$lambda$8;
            }
        });
        GalaxyWebPool.INSTANCE.go(this.path);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit show$lambda$8(GalaxyWeb this$0) {
        this$0.placeholder.setVisibility(8);
        return Unit.INSTANCE;
    }

    public final boolean back() {
        String here;
        String substringAfter;
        WebView web = GalaxyWebPool.INSTANCE.getWeb();
        if (web == null) {
            return this.onClose.invoke().booleanValue();
        }
        String url = web.getUrl();
        if (url == null || (substringAfter = StringsKt.substringAfter(url, '#', "")) == null) {
            here = null;
        } else {
            String str = substringAfter;
            if (str.length() == 0) {
                str = "/";
            }
            here = str;
        }
        if (web.getParent() != this.frame || here == null || Intrinsics.areEqual(here, this.path) || !web.canGoBack()) {
            return this.onClose.invoke().booleanValue();
        }
        web.goBack();
        return true;
    }

    public final void destroy() {
        WebView web = GalaxyWebPool.INSTANCE.getWeb();
        if ((web != null ? web.getParent() : null) == this.frame) {
            this.frame.removeView(web);
        }
        GalaxyWebPool.INSTANCE.park();
    }
}
