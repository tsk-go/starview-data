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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GalaxyWeb(Context ctx, CarUi ui, String host, String path, String title, Function0<Boolean> onClose) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(ui, "ui");
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(onClose, "onClose");
        this.ui = ui;
        this.host = host;
        this.path = path;
        this.onClose = onClose;
        this.frame = new FrameLayout(ctx);
        TextView $this$placeholder_u24lambda_u241 = new TextView(ctx);
        $this$placeholder_u24lambda_u241.setText("Opening…");
        $this$placeholder_u24lambda_u241.setTextColor(CarUi.Companion.getDIM());
        $this$placeholder_u24lambda_u241.setTextSize(18.0f);
        $this$placeholder_u24lambda_u241.setGravity(17);
        $this$placeholder_u24lambda_u241.setBackgroundColor(CarUi.Companion.getFRAME());
        this.placeholder = $this$placeholder_u24lambda_u241;
        setOrientation(1);
        LinearLayout bar = new LinearLayout(ctx);
        bar.setOrientation(0);
        bar.setGravity(16);
        bar.setPadding(this.ui.dp(4), 0, this.ui.dp(4), this.ui.dp(8));
        TextView $this$_init__u24lambda_u244 = new TextView(ctx);
        $this$_init__u24lambda_u244.setText("‹  Back");
        $this$_init__u24lambda_u244.setTextColor(CarUi.Companion.getACCENT());
        $this$_init__u24lambda_u244.setTextSize(18.0f);
        $this$_init__u24lambda_u244.setTypeface(Typeface.DEFAULT_BOLD);
        $this$_init__u24lambda_u244.setPadding(this.ui.dp(6), this.ui.dp(6), this.ui.dp(16), this.ui.dp(6));
        $this$_init__u24lambda_u244.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GalaxyWeb$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GalaxyWeb.lambda$4$lambda$3(GalaxyWeb.this, view);
            }
        });
        bar.addView($this$_init__u24lambda_u244);
        TextView $this$_init__u24lambda_u245 = new TextView(ctx);
        $this$_init__u24lambda_u245.setText(title);
        $this$_init__u24lambda_u245.setTextColor(CarUi.Companion.getTEXT());
        $this$_init__u24lambda_u245.setTextSize(22.0f);
        $this$_init__u24lambda_u245.setTypeface(Typeface.DEFAULT_BOLD);
        bar.addView($this$_init__u24lambda_u245, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView $this$_init__u24lambda_u247 = new TextView(ctx);
        $this$_init__u24lambda_u247.setText("↻");
        $this$_init__u24lambda_u247.setTextColor(CarUi.Companion.getDIM());
        $this$_init__u24lambda_u247.setTextSize(24.0f);
        $this$_init__u24lambda_u247.setPadding(this.ui.dp(16), 0, this.ui.dp(10), 0);
        $this$_init__u24lambda_u247.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GalaxyWeb$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GalaxyWebPool.INSTANCE.reload();
            }
        });
        bar.addView($this$_init__u24lambda_u247);
        addView(bar);
        this.frame.setBackgroundColor(CarUi.Companion.getFRAME());
        addView(this.frame, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        show();
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
