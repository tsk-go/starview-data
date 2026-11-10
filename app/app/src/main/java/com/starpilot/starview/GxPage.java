package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.widget.NestedScrollView;
import com.starpilot.starview.GxPage;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;
import java.util.Map.Entry;
import kotlin.jvm.internal.Ref.ObjectRef;

/* compiled from: GxKit.kt */
public abstract class GxPage extends FrameLayout {
    public static final Companion Companion = new Companion(null);
    private static final MediaType JSON = MediaType.Companion.get("application/json; charset=utf-8");
    private static final HashMap<Long, OkHttpClient> clients = new HashMap<>();
    private final Activity act;
    private boolean built;
    private final LinearLayout col;
    private final GxHost gx;
    private final Handler main;
    private final boolean needsOffroad;
    private final ArrayList<Runnable> pollRunners;
    private final ArrayList<Pair<Long, Function0<Unit>>> polls;
    private final NestedScrollView scroll;
    private boolean shown;
    private final CarUi ui;

    public abstract void build();

    /* VF: from Vineflower (jadx: ) */
    public GxPage(Activity var1, GxHost var2) {
        super((Context)var1);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
        this.act = var1;
        this.gx = var2;
        this.ui = new CarUi((Context)this.act, false, 2, null);
        this.main = new Handler(Looper.getMainLooper());
        NestedScrollView var3 = new NestedScrollView((Context)this.act);
        var3.setFillViewport(true);
        this.scroll = var3;
        LinearLayout var4 = new LinearLayout((Context)this.act);
        var4.setOrientation(1);
        var4.setPadding(this.ui.dp(10), this.ui.dp(4), this.ui.dp(24), this.ui.dp(60));
        this.col = var4;
        this.polls = new ArrayList<>();
        this.pollRunners = new ArrayList<>();
        this.scroll.addView((View)this.col, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -2)));
        this.addView((View)this.scroll, (android.view.ViewGroup.LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
    }

    public final Activity getAct() {
        return this.act;
    }

    public final GxHost getGx() {
        return this.gx;
    }

    public final CarUi getUi() {
        return this.ui;
    }

    public final Handler getMain() {
        return this.main;
    }

    public final NestedScrollView getScroll() {
        return this.scroll;
    }

    public final LinearLayout getCol() {
        return this.col;
    }

    public final GalaxyApi getApi() {
        return this.gx.getGxApi();
    }

    public final String getHost() {
        return this.gx.getGxHost();
    }

    public final boolean getShown() {
        return this.shown;
    }

    public boolean getNeedsOffroad() {
        return this.needsOffroad;
    }

    public void onShow() {
    }

    public void onHide() {
    }

    public void onTick() {
    }

    public final void rebuild() {
        this.col.removeAllViews();
        this.polls.clear();
        stopPolls();
        build();
        if (this.shown) {
            startPolls();
        }
    }

    public final void show() {
        if (getHost() == null) {
            this.col.removeAllViews();
            this.col.addView(empty("Not connected to the comma. Connect first, then come back."));
            this.built = false;
            return;
        }
        if (!this.built) {
            this.built = true;
            this.col.removeAllViews();
            try {
                build();
            } catch (Exception e) {
                Log.e("StarView/Gx", "build " + getClass().getSimpleName(), e);
                this.col.addView(empty("This page failed to build:\n" + e));
            }
        }
        this.shown = true;
        startPolls();
        try {
            onShow();
        } catch (Exception e2) {
            Log.e("StarView/Gx", "show", e2);
        }
    }

    public final void hide() {
        if (this.shown) {
            this.shown = false;
            stopPolls();
            try {
                onHide();
            } catch (Exception e) {
            }
        }
    }

    public final void tick() {
        if (this.shown) {
            try {
                onTick();
            } catch (Exception e) {
                Log.e("StarView/Gx", "tick", e);
            }
        }
    }

    public final void every(long ms, Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.polls.add(TuplesKt.to(Long.valueOf(ms), block));
        if (this.shown) {
            startPoll(ms, block);
        }
    }

    private final void startPoll(final long ms, final Function0<Unit> block) {
        Runnable runnable = new Runnable() { // from class: com.starpilot.starview.GxPage$startPoll$r$1
            @Override // java.lang.Runnable
            public void run() {
                if (GxPage.this.getShown()) {
                    try {
                        block.invoke();
                    } catch (Exception e) {
                        Log.e("StarView/Gx", "poll", e);
                    }
                    GxPage.this.getMain().postDelayed(this, ms);
                }
            }
        };
        this.pollRunners.add(runnable);
        this.main.post(runnable);
    }

    private final void startPolls() {
        stopPolls();
        Iterator<Pair<Long, Function0<Unit>>> it = this.polls.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Pair<Long, Function0<Unit>> next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Pair<Long, Function0<Unit>> pair = next;
            long ms = pair.component1().longValue();
            Function0 b = pair.component2();
            startPoll(ms, b);
        }
    }

    private final void stopPolls() {
        Iterator<Runnable> it = this.pollRunners.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Runnable next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Runnable r = next;
            this.main.removeCallbacks(r);
        }
        this.pollRunners.clear();
    }

    public final String url(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return "http://" + getHost() + ":8082" + path;
    }

    public final String enc(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        String encode = URLEncoder.encode(s, "UTF-8");
        Intrinsics.checkNotNullExpressionValue(encode, "encode(...)");
        return StringsKt.replace(encode, "+", "%20", false);
    }

    public static /* synthetic */ void call$default(GxPage gxPage, String str, String str2, Object obj, long j, Function3 function3, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: call");
        }
        if ((i & 4) != 0) {
            obj = null;
        }
        Object obj3 = obj;
        if ((i & 8) != 0) {
            j = 20;
        }
        gxPage.call(str, str2, obj3, j, function3);
    }

    public final void call(final String method, final String path, final Object body, final long timeoutS, final Function3<? super Integer, ? super String, ? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(then, "then");
        final String h = getHost();
        if (h == null) {
            then.invoke(-1, "", "Not connected");
        } else {
            new Thread(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    GxPage.call$lambda$8(body, method, h, path, timeoutS, GxPage.this, then);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x011e, code lost:
    
        if (r0 == null) goto L53;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:68:0x015d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void call$lambda$8(Object obj, String str, String str2, String str3, long j, final GxPage gxPage, final Function3 function3) {
        String message;
        RequestBody create;
        Object m1191constructorimpl;
        T t;
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = -1;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        String str4 = "";
        objectRef.element = "";
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        try {
            if (obj == null) {
                if (!Intrinsics.areEqual(str, "GET") && !Intrinsics.areEqual(str, "DELETE")) {
                    create = RequestBody.Companion.create("{}", JSON);
                }
                create = null;
            } else {
                if (!(obj instanceof JSONObject) && !(obj instanceof JSONArray)) {
                    create = obj instanceof RequestBody ? (RequestBody) obj : RequestBody.Companion.create(obj.toString(), JSON);
                }
                create = RequestBody.Companion.create(obj.toString(), JSON);
            }
        } catch (Exception e) {
            e = e;
        }
        try {
        } catch (Exception e2) {
            e = e2;
            message = e.getMessage();
            T t2 = message;
            if (message == null) {
                t2 = e.toString();
            }
            objectRef2.element = t2;
            gxPage.main.post(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    GxPage.call$lambda$8$lambda$7(gxPage, function3, intRef, objectRef, objectRef2);
                }
            });
        }
        try {
        } catch (Exception e3) {
            e = e3;
            message = e.getMessage();
            T t22 = message;
            if (message == null) {
            }
            objectRef2.element = t22;
            gxPage.main.post(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    GxPage.call$lambda$8$lambda$7(gxPage, function3, intRef, objectRef, objectRef2);
                }
            });
        }
        try {
            Response execute = Companion.client(j).newCall(new Request.Builder().url("http://" + str2 + ":8082" + str3).method(str, create).build()).execute();
            try {
                Response response = execute;
                intRef.element = response.code();
                ResponseBody body = response.body();
                T t3 = str4;
                if (body != null) {
                    String string = body.string();
                    t3 = string == null ? str4 : string;
                }
                objectRef.element = t3;
                if (!response.isSuccessful()) {
                    try {
                        Result.Companion companion = Result.Companion;
                        JSONObject jSONObject = new JSONObject((String) objectRef.element);
                        String optString = jSONObject.optString("error");
                        if (StringsKt.isBlank(optString)) {
                            optString = jSONObject.optString("message");
                        }
                        m1191constructorimpl = KtCompat.resultOf(optString);
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.Companion;
                        m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
                    }
                    if (KtCompat.isFailure(m1191constructorimpl)) {
                        m1191constructorimpl = null;
                    }
                    String str5 = (String) m1191constructorimpl;
                    if (str5 != null) {
                        String str6 = str5;
                        if (StringsKt.isBlank(str6)) {
                            str6 = null;
                        }
                        String str7 = str6;
                        t = str7;
                    }
                    t = "HTTP " + response.code();
                    objectRef2.element = t;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(execute, null);
            } finally {
            }
        } catch (Exception e4) {
            e = e4;
            message = e.getMessage();
            T t222 = message;
            if (message == null) {
            }
            objectRef2.element = t222;
            gxPage.main.post(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    GxPage.call$lambda$8$lambda$7(gxPage, function3, intRef, objectRef, objectRef2);
                }
            });
        }
        gxPage.main.post(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                GxPage.call$lambda$8$lambda$7(gxPage, function3, intRef, objectRef, objectRef2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void call$lambda$8$lambda$7(GxPage this$0, Function3 $then, Ref.IntRef $code, Ref.ObjectRef $text, Ref.ObjectRef $err) {
        if (this$0.act.isFinishing() || this$0.act.isDestroyed()) {
            return;
        }
        $then.invoke(Integer.valueOf($code.element), $text.element, $err.element);
    }

    public static /* synthetic */ void json$default(GxPage gxPage, String str, String str2, Object obj, long j, Function2 function2, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: json");
        }
        if ((i & 4) != 0) {
            obj = null;
        }
        Object obj3 = obj;
        if ((i & 8) != 0) {
            j = 20;
        }
        gxPage.json(str, str2, obj3, j, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit json$lambda$10(GxPage this$0, Function2 $then, int i, String text, String err) {
        Object m1191constructorimpl;
        Intrinsics.checkNotNullParameter(text, "text");
        try {
            Result.Companion companion = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(StringsKt.isBlank(text) ? null : new JSONTokener(text).nextValue());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        Object v = KtCompat.isFailure(m1191constructorimpl) ? null : m1191constructorimpl;
        $then.invoke(err, v);
        return Unit.INSTANCE;
    }

    public final void json(String method, String path, Object body, long timeoutS, final Function2<? super String, Object, Unit> then) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(then, "then");
        call(method, path, body, timeoutS, new Function3() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Unit json$lambda$10;
                json$lambda$10 = GxPage.json$lambda$10(GxPage.this, then, ((Integer) obj).intValue(), (String) obj2, (String) obj3);
                return json$lambda$10;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getJson$lambda$11(Function2 $then, String e, Object v) {
        String str;
        if (e == null) {
            str = !(v instanceof JSONObject) ? "bad reply" : null;
        } else {
            str = e;
        }
        $then.invoke(str, v instanceof JSONObject ? (JSONObject) v : null);
        return Unit.INSTANCE;
    }

    public final void getJson(String path, final Function2<? super String, ? super JSONObject, Unit> then) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(then, "then");
        json$default(this, "GET", path, null, 0L, new Function2() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit json$lambda$11;
                json$lambda$11 = GxPage.getJson$lambda$11(then, (String) obj, obj2);
                return json$lambda$11;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getArray$lambda$12(Function2 $then, String e, Object v) {
        String str;
        if (e == null) {
            str = !(v instanceof JSONArray) ? "bad reply" : null;
        } else {
            str = e;
        }
        $then.invoke(str, v instanceof JSONArray ? (JSONArray) v : null);
        return Unit.INSTANCE;
    }

    public final void getArray(String path, final Function2<? super String, ? super JSONArray, Unit> then) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(then, "then");
        json$default(this, "GET", path, null, 0L, new Function2() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit array$lambda$12;
                array$lambda$12 = GxPage.getArray$lambda$12(then, (String) obj, obj2);
                return array$lambda$12;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getText$lambda$13(Function2 $then, int i, String t, String e) {
        Intrinsics.checkNotNullParameter(t, "t");
        $then.invoke(e, t);
        return Unit.INSTANCE;
    }

    public final void getText(String path, final Function2<? super String, ? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(then, "then");
        call$default(this, "GET", path, null, 0L, new Function3() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Unit text$lambda$13;
                text$lambda$13 = GxPage.getText$lambda$13(then, ((Integer) obj).intValue(), (String) obj2, (String) obj3);
                return text$lambda$13;
            }
        }, 12, null);
    }

    public static /* synthetic */ void send$default(GxPage gxPage, String str, String str2, JSONObject jSONObject, long j, Function2 function2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: send");
        }
        if ((i & 4) != 0) {
            jSONObject = null;
        }
        JSONObject jSONObject2 = jSONObject;
        if ((i & 8) != 0) {
            j = 30;
        }
        long j2 = j;
        if ((i & 16) != 0) {
            function2 = new Function2() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda18
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    Unit send$lambda$14;
                    send$lambda$14 = GxPage.send$lambda$14((String) obj2, (JSONObject) obj3);
                    return send$lambda$14;
                }
            };
        }
        gxPage.send(str, str2, jSONObject2, j2, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit send$lambda$14(String str, JSONObject jSONObject) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit send$lambda$15(Function2 $then, String e, Object v) {
        $then.invoke(e, v instanceof JSONObject ? (JSONObject) v : null);
        return Unit.INSTANCE;
    }

    public final void send(String method, String path, JSONObject body, long timeoutS, final Function2<? super String, ? super JSONObject, Unit> then) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(then, "then");
        json(method, path, body, timeoutS, new Function2() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit send$lambda$15;
                send$lambda$15 = GxPage.send$lambda$15(then, (String) obj, obj2);
                return send$lambda$15;
            }
        });
    }

    public static /* synthetic */ void doAction$default(GxPage gxPage, String str, String str2, JSONObject jSONObject, String str3, Function1 function1, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: doAction");
        }
        gxPage.doAction(str, str2, (i & 4) != 0 ? null : jSONObject, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : function1);
    }

    public final void doAction(String method, String path, JSONObject body, final String okMsg, final Function1<? super JSONObject, Unit> then) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(path, "path");
        send$default(this, method, path, body, 0L, new Function2() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit doAction$lambda$17;
                doAction$lambda$17 = GxPage.doAction$lambda$17(GxPage.this, okMsg, then, (String) obj, (JSONObject) obj2);
                return doAction$lambda$17;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit doAction$lambda$17(GxPage this$0, String $okMsg, Function1 $then, String e, JSONObject o) {
        String m;
        String optString;
        if (e != null) {
            this$0.toast(e);
        } else {
            if ($okMsg == null) {
                m = null;
                if (o != null && (optString = o.optString("message")) != null) {
                    String str = optString;
                    if (!StringsKt.isBlank(str)) {
                        m = str;
                    }
                    m = m;
                }
            } else {
                m = $okMsg;
            }
            if (m != null) {
                this$0.toast(m);
            }
        }
        if (e == null && $then != null) {
            $then.invoke(o);
        }
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    public static void upload$default(GxPage var0, String var1, List var2, Map var3, Function2 var4, int var5, Object var6) {
        if (var6 == null) {
            if ((var5 & 4) != 0) {
                var3 = MapsKt.emptyMap();
            }

            var0.upload(var1, var2, var3, var4);
        } else {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: upload");
        }
    }

    public final void upload(String path, List<Triple<String, String, Pair<byte[], String>>> files, Map<String, String> fields, final Function2<? super String, ? super JSONObject, Unit> then) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(fields, "fields");
        Intrinsics.checkNotNullParameter(then, "then");
        MultipartBody.Builder b = new MultipartBody.Builder(null, 1, null).setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            b.addFormDataPart(k, v);
        }
        for (Triple<String, String, Pair<byte[], String>> triple : files) {
            String field = triple.component1();
            String name = triple.component2();
            Pair data = triple.component3();
            b.addFormDataPart(field, name, RequestBody.Companion.create(data.getFirst(), MediaType.Companion.get(((String) data.getSecond())), 0, data.getFirst().length));
        }
        json("POST", path, b.build(), 120L, new Function2() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit upload$lambda$18;
                upload$lambda$18 = GxPage.upload$lambda$18(then, (String) obj, obj2);
                return upload$lambda$18;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit upload$lambda$18(Function2 $then, String e, Object v) {
        $then.invoke(e, v instanceof JSONObject ? (JSONObject) v : null);
        return Unit.INSTANCE;
    }

    public final void readUri(final Uri uri, final Function2<? super byte[], ? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(then, "then");
        new Thread(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                GxPage.readUri$lambda$24(GxPage.this, uri, then);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void readUri$lambda$24(final GxPage gxPage, Uri uri, final Function2 function2) {
        Object m1191constructorimpl;
        Object obj;
        String str;
        byte[] bArr;
        try {
            Result.Companion companion = Result.Companion;
            InputStream openInputStream = gxPage.act.getContentResolver().openInputStream(uri);
            if (openInputStream != null) {
                InputStream inputStream = openInputStream;
                try {
                    bArr = ByteStreamsKt.readBytes(inputStream);
                    CloseableKt.closeFinally(inputStream, null);
                } finally {
                }
            } else {
                bArr = null;
            }
            m1191constructorimpl = KtCompat.resultOf(bArr);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        final byte[] bArr2 = (byte[]) m1191constructorimpl;
        try {
            Result.Companion companion3 = Result.Companion;
            Cursor query = gxPage.act.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
            if (query != null) {
                Cursor cursor = query;
                try {
                    Cursor cursor2 = cursor;
                    str = cursor2.moveToFirst() ? cursor2.getString(0) : null;
                    CloseableKt.closeFinally(cursor, null);
                } finally {
                    try {
                        throw th;
                    } finally {
                    }
                }
            } else {
                str = null;
            }
            obj = KtCompat.resultOf(str);
        } catch (Throwable th2) {
            Result.Companion companion4 = Result.Companion;
            obj = KtCompat.resultOf(ResultKt.createFailure(th2));
        }
        String str2 = (String) (KtCompat.isFailure(obj) ? null : obj);
        if (str2 == null && (str2 = uri.getLastPathSegment()) == null) {
            str2 = "file";
        }
        gxPage.main.post(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                GxPage.readUri$lambda$24$lambda$23(gxPage, function2, bArr2, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void readUri$lambda$24$lambda$23(GxPage this$0, Function2 $then, byte[] $bytes, String $name) {
        if (this$0.act.isDestroyed()) {
            return;
        }
        $then.invoke($bytes, $name);
    }

    public static /* synthetic */ void download$default(GxPage gxPage, String str, String str2, String str3, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: download");
        }
        if ((i & 4) != 0) {
            str3 = "application/octet-stream";
        }
        gxPage.download(str, str2, str3);
    }

    public final void download(final String path, final String fileName, final String mime) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(mime, "mime");
        final String h = getHost();
        if (h == null) {
            return;
        }
        toast("Downloading " + fileName + "…");
        new Thread(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                GxPage.download$lambda$29(h, path, fileName, GxPage.this, mime);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v28, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v2, types: [T, java.lang.String] */
    public static final void download$lambda$29(String $h, String $path, String $fileName, final GxPage this$0, String $mime) {
        Response execute;
        Response r;
        InputStream byteStream;
        final Ref.ObjectRef msg = new Ref.ObjectRef();
        try {
            try {
                execute = Companion.client(300L).newCall(new Request.Builder().url("http://" + $h + ":8082" + $path).build()).execute();
                try {
                    r = execute;
                    try {
                    } catch (Throwable th) {
                        th = th;
                        Throwable th2 = th;
                        try {
                            throw th2;
                        } catch (Throwable th3) {
                            CloseableKt.closeFinally(execute, th2);
                            throw th3;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Exception e) {
                e = e;
                msg.element = "Download failed: " + e.getMessage();
                this$0.main.post(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        GxPage.download$lambda$29$lambda$28(this$0, msg);
                    }
                });
            }
        } catch (Exception e2) {
            e = e2;
            msg.element = "Download failed: " + e.getMessage();
            this$0.main.post(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    GxPage.download$lambda$29$lambda$28(this$0, msg);
                }
            });
        }
        if (!r.isSuccessful()) {
            throw new IllegalStateException("HTTP " + r.code());
        }
        ContentValues $this$download_u24lambda_u2429_u24lambda_u2427_u24lambda_u2425 = new ContentValues();
        $this$download_u24lambda_u2429_u24lambda_u2427_u24lambda_u2425.put("_display_name", $fileName);
        $this$download_u24lambda_u2429_u24lambda_u2427_u24lambda_u2425.put("mime_type", $mime);
        $this$download_u24lambda_u2429_u24lambda_u2427_u24lambda_u2425.put("relative_path", "Download/StarView");
        Uri uri = this$0.act.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, $this$download_u24lambda_u2429_u24lambda_u2427_u24lambda_u2425);
        if (uri == null) {
            throw new IllegalStateException("cannot create file");
        }
        OutputStream openOutputStream = this$0.act.getContentResolver().openOutputStream(uri);
        if (openOutputStream != null) {
            OutputStream outputStream = openOutputStream;
            try {
                OutputStream out = outputStream;
                ResponseBody body = r.body();
                if (body != null && (byteStream = body.byteStream()) != null) {
                    Long.valueOf(ByteStreamsKt.copyTo(byteStream, out, 8192));
                }
                CloseableKt.closeFinally(outputStream, null);
            } finally {
            }
        }
        CloseableKt.closeFinally(execute, null);
        msg.element = "Saved to Downloads/StarView/" + $fileName;
        this$0.main.post(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                GxPage.download$lambda$29$lambda$28(this$0, msg);
            }
        });
    }

    /* VF: from Vineflower (jadx: ) */
    public static final void download$lambda$29$lambda$28(GxPage var0, ObjectRef var1) {
        var0.toast((String)var1.element);
    }

    /* VF: from Vineflower (jadx: ) */
    public static void loadImage$default(GxPage var0, String var1, ImageView var2, Function1 var3, int var4, Object var5) {
        if (var5 == null) {
            if ((var4 & 4) != 0) {
                var3 = null;
            }

            var0.loadImage(var1, var2, var3);
        } else {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: loadImage");
        }
    }

    public final void loadImage(final String path, final ImageView into, final Function1<? super Bitmap, Unit> then) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(into, "into");
        final String h = getHost();
        if (h == null) {
            return;
        }
        new Thread(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                GxPage.loadImage$lambda$34(GxPage.this, h, path, into, then);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadImage$lambda$34(GxPage this$0, String $h, String $path, final ImageView $into, final Function1 $then) {
        Object m1191constructorimpl;
        ResponseBody body;
        byte[] it;
        try {
            Result.Companion companion = Result.Companion;
            Response execute = Companion.client(15L).newCall(new Request.Builder().url("http://" + $h + ":8082" + $path).build()).execute();
            try {
                Response r = execute;
                Bitmap decodeByteArray = (!r.isSuccessful() || (body = r.body()) == null || (it = body.bytes()) == null) ? null : BitmapFactory.decodeByteArray(it, 0, it.length);
                CloseableKt.closeFinally(execute, null);
                m1191constructorimpl = KtCompat.resultOf(decodeByteArray);
            } finally {
            }
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        final Bitmap bmp = (Bitmap) (KtCompat.isFailure(m1191constructorimpl) ? null : m1191constructorimpl);
        this$0.main.post(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                GxPage.loadImage$lambda$34$lambda$33(bmp, $into, $then);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadImage$lambda$34$lambda$33(Bitmap $bmp, ImageView $into, Function1 $then) {
        if ($bmp != null) {
            $into.setImageBitmap($bmp);
        }
        if ($then != null) {
            $then.invoke($bmp);
        }
    }

    public final boolean pBool(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        GalaxyApi api = getApi();
        if (api != null) {
            return api.bool(key);
        }
        return false;
    }

    public final Double pNum(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        GalaxyApi api = getApi();
        if (api != null) {
            return api.m661double(key);
        }
        return null;
    }

    public final String pStr(String key) {
        String string;
        Intrinsics.checkNotNullParameter(key, "key");
        GalaxyApi api = getApi();
        return (api == null || (string = api.string(key)) == null) ? "" : string;
    }

    /* VF: from Vineflower (jadx: ) */
    public static void putParam$default(GxPage var0, String var1, Object var2, Function1 var3, int var4, Object var5) {
        if (var5 == null) {
            if ((var4 & 4) != 0) {
                var3 = null;
            }

            var0.putParam(var1, var2, var3);
        } else {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: putParam");
        }
    }

    public final void putParam(String key, Object value, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(key, "key");
        GalaxyApi a = getApi();
        if (a == null) {
            GxPage $this$putParam_u24lambda_u2435 = this;
            $this$putParam_u24lambda_u2435.toast("Not connected");
        } else {
            GalaxyApi.put$default(a, key, value, null, new Function1() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda22
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit putParam$lambda$36;
                    putParam$lambda$36 = GxPage.putParam$lambda$36(GxPage.this, then, (String) obj);
                    return putParam$lambda$36;
                }
            }, 4, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit putParam$lambda$36(GxPage this$0, Function1 $then, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        if ($then != null) {
            $then.invoke(e);
        }
        return Unit.INSTANCE;
    }

    /* compiled from: GxKit.kt */
    public final class Row {
        private final TextView desc;
        final /* synthetic */ GxPage this$0;
        private final TextView titleView;
        private final LinearLayout view;

        public Row(GxPage this$0, String title, String desc) {
            Intrinsics.checkNotNullParameter(title, "title");
            this.this$0 = this$0;
            LinearLayout $this$_init__u24lambda_u240 = new LinearLayout(this.this$0.getAct());
            GxPage gxPage = this.this$0;
            $this$_init__u24lambda_u240.setOrientation(0);
            $this$_init__u24lambda_u240.setGravity(16);
            $this$_init__u24lambda_u240.setMinimumHeight(gxPage.getUi().dp(76));
            $this$_init__u24lambda_u240.setPadding(gxPage.getUi().dp(20), gxPage.getUi().dp(14), gxPage.getUi().dp(18), gxPage.getUi().dp(14));
            $this$_init__u24lambda_u240.setBackground(CarUi.rounded$default(gxPage.getUi(), CarUi.Companion.getCARD(), 0, null, 6, null));
            this.view = $this$_init__u24lambda_u240;
            LinearLayout tb = new LinearLayout(this.this$0.getAct());
            boolean z = true;
            tb.setOrientation(1);
            TextView $this$_init__u24lambda_u242 = new TextView(this.this$0.getAct());
            $this$_init__u24lambda_u242.setText(title);
            $this$_init__u24lambda_u242.setTextColor(CarUi.Companion.getTEXT());
            $this$_init__u24lambda_u242.setTextSize(21.0f);
            this.titleView = $this$_init__u24lambda_u242;
            TextView $this$_init__u24lambda_u243 = new TextView(this.this$0.getAct());
            GxPage gxPage2 = this.this$0;
            $this$_init__u24lambda_u243.setText(desc != null ? desc : "");
            $this$_init__u24lambda_u243.setTextColor(CarUi.Companion.getDIM());
            $this$_init__u24lambda_u243.setTextSize(15.0f);
            $this$_init__u24lambda_u243.setPadding(0, gxPage2.getUi().dp(4), 0, 0);
            String str = desc;
            if (str != null && !StringsKt.isBlank(str)) {
                z = false;
            }
            $this$_init__u24lambda_u243.setVisibility(z ? 8 : 0);
            this.desc = $this$_init__u24lambda_u243;
            tb.addView(this.titleView);
            tb.addView(this.desc);
            this.view.addView(tb, new LinearLayout.LayoutParams(0, -2, 1.0f));
        }

        public final LinearLayout getView() {
            return this.view;
        }

        public final TextView getTitleView() {
            return this.titleView;
        }

        public final TextView getDesc() {
            return this.desc;
        }

        public final void setDesc(String text) {
            this.desc.setText(text != null ? text : "");
            String str = text;
            this.desc.setVisibility(str == null || StringsKt.isBlank(str) ? 8 : 0);
        }

        public final void setTitle(String text) {
            Intrinsics.checkNotNullParameter(text, "text");
            this.titleView.setText(text);
        }

        public static /* synthetic */ TextView button$default(Row row, String str, boolean z, Function0 function0, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            return row.button(str, z, function0);
        }

        public final TextView button(String label, boolean accent, Function0<Unit> onClick) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(onClick, "onClick");
            TextView b = this.this$0.getUi().button(label, accent, onClick);
            if (this.view.getChildCount() > 1) {
                this.view.addView(new View(this.this$0.getAct()), new LinearLayout.LayoutParams(this.this$0.getUi().dp(8), 1));
            }
            this.view.addView(b);
            return b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void switch$lambda$4(Function1 $onChange, CompoundButton b, boolean v) {
            if (b.isPressed()) {
                $onChange.invoke(Boolean.valueOf(v));
            }
        }

        /* renamed from: switch, reason: not valid java name */
        public final Switch m806switch(boolean checked, final Function1<? super Boolean, Unit> onChange) {
            Intrinsics.checkNotNullParameter(onChange, "onChange");
            Switch s = this.this$0.getUi().bigSwitch();
            s.setChecked(checked);
            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.GxPage$Row$$ExternalSyntheticLambda0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    GxPage.Row.switch$lambda$4(onChange, compoundButton, z);
                }
            });
            this.view.addView(s);
            return s;
        }

        public final TextView value(String text) {
            Intrinsics.checkNotNullParameter(text, "text");
            TextView t = new TextView(this.this$0.getAct());
            GxPage gxPage = this.this$0;
            t.setText(text);
            t.setTextColor(CarUi.Companion.getTEXT());
            t.setTextSize(20.0f);
            t.setTypeface(Typeface.DEFAULT_BOLD);
            t.setPadding(gxPage.getUi().dp(12), 0, gxPage.getUi().dp(4), 0);
            this.view.addView(t);
            return t;
        }

        public final void add(View v) {
            Intrinsics.checkNotNullParameter(v, "v");
            this.view.addView(v);
        }

        public final boolean getVisible() {
            return this.view.getVisibility() == 0;
        }

        public final void setVisible(boolean v) {
            this.view.setVisibility(v ? 0 : 8);
        }
    }

    public static /* synthetic */ Row row$default(GxPage gxPage, String str, String str2, ViewGroup viewGroup, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: row");
        }
        if ((i2 & 2) != 0) {
            str2 = null;
        }
        if ((i2 & 4) != 0) {
            viewGroup = gxPage.col;
        }
        if ((i2 & 8) != 0) {
            i = 0;
        }
        return gxPage.row(str, str2, viewGroup, i);
    }

    public final Row row(String title, String desc, ViewGroup into, int depth) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(into, "into");
        Row r = new Row(this, title, desc);
        into.addView(r.getView(), CarUi.lp$default(this.ui, depth, 0, 2, null));
        return r;
    }

    public static /* synthetic */ TextView header$default(GxPage gxPage, String str, ViewGroup viewGroup, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: header");
        }
        if ((i & 2) != 0) {
            viewGroup = gxPage.col;
        }
        return gxPage.header(str, viewGroup);
    }

    public final TextView header(String text, ViewGroup into) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(into, "into");
        TextView t = this.ui.header(text);
        into.addView(t);
        return t;
    }

    public static /* synthetic */ TextView note$default(GxPage gxPage, String str, ViewGroup viewGroup, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: note");
        }
        if ((i & 2) != 0) {
            viewGroup = gxPage.col;
        }
        return gxPage.note(str, viewGroup);
    }

    public final TextView note(String text, ViewGroup into) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(into, "into");
        TextView t = this.ui.note(text);
        into.addView(t);
        return t;
    }

    public final TextView empty(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        TextView $this$empty_u24lambda_u2437 = new TextView(this.act);
        $this$empty_u24lambda_u2437.setText(text);
        $this$empty_u24lambda_u2437.setTextColor(CarUi.Companion.getDIM());
        $this$empty_u24lambda_u2437.setTextSize(19.0f);
        $this$empty_u24lambda_u2437.setGravity(17);
        $this$empty_u24lambda_u2437.setPadding(this.ui.dp(30), this.ui.dp(40), this.ui.dp(30), this.ui.dp(40));
        return $this$empty_u24lambda_u2437;
    }

    public static /* synthetic */ LinearLayout box$default(GxPage gxPage, ViewGroup viewGroup, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: box");
        }
        if ((i & 1) != 0) {
            viewGroup = gxPage.col;
        }
        return gxPage.box(viewGroup);
    }

    public final LinearLayout box(ViewGroup into) {
        Intrinsics.checkNotNullParameter(into, "into");
        LinearLayout b = new LinearLayout(this.act);
        b.setOrientation(1);
        into.addView(b, new LinearLayout.LayoutParams(-1, -2));
        return b;
    }

    public static /* synthetic */ LinearLayout card$default(GxPage gxPage, ViewGroup viewGroup, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: card");
        }
        if ((i & 1) != 0) {
            viewGroup = gxPage.col;
        }
        return gxPage.card(viewGroup);
    }

    public final LinearLayout card(ViewGroup into) {
        Intrinsics.checkNotNullParameter(into, "into");
        LinearLayout c = new LinearLayout(this.act);
        c.setOrientation(1);
        c.setBackground(CarUi.rounded$default(this.ui, CarUi.Companion.getCARD(), 0, null, 6, null));
        c.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
        into.addView(c, CarUi.lp$default(this.ui, 0, 0, 3, null));
        return c;
    }

    public static /* synthetic */ TextView text$default(GxPage gxPage, String str, ViewGroup viewGroup, float f, int i, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: text");
        }
        if ((i2 & 4) != 0) {
            f = 15.0f;
        }
        float f2 = f;
        if ((i2 & 8) != 0) {
            i = CarUi.Companion.getDIM();
        }
        int i3 = i;
        if ((i2 & 16) != 0) {
            z = false;
        }
        return gxPage.text(str, viewGroup, f2, i3, z);
    }

    public final TextView text(String s, ViewGroup into, float size, int color, boolean bold) {
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(into, "into");
        TextView t = new TextView(this.act);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) {
            t.setTypeface(Typeface.DEFAULT_BOLD);
        }
        t.setPadding(0, this.ui.dp(2), 0, this.ui.dp(2));
        into.addView(t);
        return t;
    }

    public static /* synthetic */ LinearLayout buttons$default(GxPage gxPage, Pair[] pairArr, ViewGroup viewGroup, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: buttons");
        }
        if ((i & 2) != 0) {
            viewGroup = gxPage.col;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return gxPage.buttons(pairArr, viewGroup, z);
    }

    public final LinearLayout buttons(Pair<String, ? extends Function0<Unit>>[] items, ViewGroup into, boolean accentFirst) {
        Intrinsics.checkNotNullParameter(items, "items");
        Intrinsics.checkNotNullParameter(into, "into");
        LinearLayout bar = new LinearLayout(this.act);
        bar.setOrientation(0);
        bar.setPadding(0, this.ui.dp(4), 0, this.ui.dp(8));
        int length = items.length;
        for (int i = 0; i < length; i++) {
            int i2 = i;
            Pair<String, ? extends Function0<Unit>> pair = items[i];
            TextView button = this.ui.button(pair.getFirst(), accentFirst && i2 == 0, pair.getSecond());
            LinearLayout.LayoutParams $this$buttons_u24lambda_u2442 = new LinearLayout.LayoutParams(-2, -2);
            $this$buttons_u24lambda_u2442.rightMargin = this.ui.dp(10);
            Unit unit = Unit.INSTANCE;
            bar.addView(button, $this$buttons_u24lambda_u2442);
        }
        HorizontalScrollView $this$buttons_u24lambda_u2443 = new HorizontalScrollView(this.act);
        $this$buttons_u24lambda_u2443.setHorizontalScrollBarEnabled(false);
        $this$buttons_u24lambda_u2443.addView(bar);
        into.addView($this$buttons_u24lambda_u2443);
        return bar;
    }

    public static /* synthetic */ Pair toggle$default(GxPage gxPage, String str, String str2, boolean z, ViewGroup viewGroup, Function1 function1, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toggle");
        }
        if ((i & 8) != 0) {
            viewGroup = gxPage.col;
        }
        return gxPage.toggle(str, str2, z, viewGroup, function1);
    }

    public final Pair<Row, Switch> toggle(String title, String desc, boolean checked, ViewGroup into, Function1<? super Boolean, Unit> onChange) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(into, "into");
        Intrinsics.checkNotNullParameter(onChange, "onChange");
        Row r = row$default(this, title, desc, into, 0, 8, null);
        return TuplesKt.to(r, r.m806switch(checked, onChange));
    }

    public static /* synthetic */ Pair paramToggle$default(GxPage gxPage, String str, String str2, String str3, ViewGroup viewGroup, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: paramToggle");
        }
        if ((i & 4) != 0) {
            str3 = null;
        }
        if ((i & 8) != 0) {
            viewGroup = gxPage.col;
        }
        return gxPage.paramToggle(str, str2, str3, viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paramToggle$lambda$44(GxPage this$0, String $key, boolean v) {
        putParam$default(this$0, $key, Boolean.valueOf(v), null, 4, null);
        return Unit.INSTANCE;
    }

    public final Pair<Row, Switch> paramToggle(final String key, String title, String desc, ViewGroup into) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(into, "into");
        return toggle(title, desc, pBool(key), into, new Function1() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paramToggle$lambda$44;
                paramToggle$lambda$44 = GxPage.paramToggle$lambda$44(GxPage.this, key, ((Boolean) obj).booleanValue());
                return paramToggle$lambda$44;
            }
        });
    }

    public static /* synthetic */ Function1 choice$default(GxPage gxPage, String str, String str2, List list, int i, ViewGroup viewGroup, Function1 function1, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: choice");
        }
        if ((i2 & 16) != 0) {
            viewGroup = gxPage.col;
        }
        return gxPage.choice(str, str2, list, i, viewGroup, function1);
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public final Function1<Integer, Unit> choice(String var1, String var2, List<String> var3, int var4, ViewGroup var5, Function1<? super Integer, Unit> var6) {
        Intrinsics.checkNotNullParameter(var1, "title");
        Intrinsics.checkNotNullParameter(var3, "labels");
        Intrinsics.checkNotNullParameter(var5, "into");
        Intrinsics.checkNotNullParameter(var6, "onPick");
        LinearLayout var8 = this.card(var5);
        text$default(this, var1, (ViewGroup)var8, 21.0F, CarUi.Companion.getTEXT(), false, 16, null);
        CharSequence var9 = var2;
        boolean var7;
        if (var9 != null && !StringsKt.isBlank(var9)) {
            var7 = false;
        } else {
            var7 = true;
        }

        if (!var7) {
            text$default(this, var2, (ViewGroup)var8, 0.0F, 0, false, 28, null);
        }

        ObjectRef var16 = new ObjectRef();
        Pair var14 = this.ui.chips(var3, new GxPage$$ExternalSyntheticLambda7(var16, var6));
        LinearLayout var10 = (LinearLayout)var14.component1();
        var16.element = ((Function1)var14.component2());
        var10.setPadding(0, this.ui.dp(10), 0, 0);
        var8.addView((View)var10);
        Function1 var11 = ((Function1) var16.element);
        Object var15 = null;
        if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            var11 = null;
        } else {
            var11 = (Function1)var16.element;
        }

        var11.invoke(var4);
        if (var16.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            var11 = (Function1)var15;
        } else {
            var11 = (Function1)var16.element;
        }

        return var11;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit choice$lambda$45(Ref.ObjectRef $paint, Function1 $onPick, int i) {
        Function1 function1;
        if (!$paint.element) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function1 = null;
        } else {
            function1 = (Function1) $paint.element;
        }
        function1.invoke(Integer.valueOf(i));
        $onPick.invoke(Integer.valueOf(i));
        return Unit.INSTANCE;
    }

    public static /* synthetic */ Function1 stepper$default(GxPage gxPage, String str, String str2, double d, double d2, double d3, double d4, Function1 function1, ViewGroup viewGroup, Function1 function12, int i, Object obj) {
        if (obj == null) {
            return gxPage.stepper(str, str2, d, d2, d3, d4, (i & 64) != 0 ? new Function1() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    String stepper$lambda$46;
                    stepper$lambda$46 = GxPage.stepper$lambda$46(((Double) obj2).doubleValue());
                    return stepper$lambda$46;
                }
            } : function1, (i & 128) != 0 ? gxPage.col : viewGroup, function12);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: stepper");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String stepper$lambda$46(double it) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%.2f", Arrays.copyOf(new Object[]{Double.valueOf(it)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final Function1<Double, Unit> stepper(String title, String desc, double value, final double min, final double max, final double step, final Function1<? super Double, String> fmt, ViewGroup into, final Function1<? super Double, Unit> onChange) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(fmt, "fmt");
        Intrinsics.checkNotNullParameter(into, "into");
        Intrinsics.checkNotNullParameter(onChange, "onChange");
        Row r = row$default(this, title, desc, into, 0, 8, null);
        final Ref.DoubleRef cur = new Ref.DoubleRef();
        cur.element = value;
        final TextView tv = new TextView(this.act);
        tv.setTextSize(22.0f);
        tv.setTextColor(CarUi.Companion.getTEXT());
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setMinWidth(this.ui.dp(90));
        tv.setGravity(17);
        r.add(this.ui.stepper("−", new Function0() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit stepper$lambda$48;
                stepper$lambda$48 = GxPage.stepper$lambda$48(cur, step, min, max, onChange, tv, fmt);
                return stepper$lambda$48;
            }
        }));
        r.add(tv);
        r.add(this.ui.stepper("+", new Function0() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit stepper$lambda$49;
                stepper$lambda$49 = GxPage.stepper$lambda$49(cur, step, min, max, onChange, tv, fmt);
                return stepper$lambda$49;
            }
        }));
        stepper$paint(tv, fmt, cur);
        return new Function1() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit stepper$lambda$50;
                stepper$lambda$50 = GxPage.stepper$lambda$50(cur, tv, fmt, ((Double) obj).doubleValue());
                return stepper$lambda$50;
            }
        };
    }

    private static final void stepper$paint(TextView tv, Function1<? super Double, String> function1, Ref.DoubleRef cur) {
        tv.setText(function1.invoke(Double.valueOf(cur.element)));
    }

    private static final void stepper$write(Ref.DoubleRef cur, double $min, double $max, double $step, Function1<? super Double, Unit> function1, TextView tv, Function1<? super Double, String> function12, double v) {
        cur.element = RangesKt.coerceIn(Math.round(RangesKt.coerceIn(v, $min, $max) / $step) * $step, $min, $max);
        stepper$paint(tv, function12, cur);
        function1.invoke(Double.valueOf(cur.element));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit stepper$lambda$48(Ref.DoubleRef $cur, double $step, double $min, double $max, Function1 $onChange, TextView $tv, Function1 $fmt) {
        stepper$write($cur, $min, $max, $step, $onChange, $tv, $fmt, $cur.element - $step);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit stepper$lambda$49(Ref.DoubleRef $cur, double $step, double $min, double $max, Function1 $onChange, TextView $tv, Function1 $fmt) {
        stepper$write($cur, $min, $max, $step, $onChange, $tv, $fmt, $cur.element + $step);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit stepper$lambda$50(Ref.DoubleRef $cur, TextView $tv, Function1 $fmt, double v) {
        $cur.element = v;
        stepper$paint($tv, $fmt, $cur);
        return Unit.INSTANCE;
    }

    public static /* synthetic */ ProgressBar progress$default(GxPage gxPage, ViewGroup viewGroup, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: progress");
        }
        if ((i & 1) != 0) {
            viewGroup = gxPage.col;
        }
        return gxPage.progress(viewGroup);
    }

    public final ProgressBar progress(ViewGroup into) {
        Intrinsics.checkNotNullParameter(into, "into");
        ProgressBar p = new ProgressBar(this.act, null, 16842872);
        p.setMax(100);
        p.setProgressTintList(ColorStateList.valueOf(CarUi.Companion.getACCENT()));
        LinearLayout.LayoutParams $this$progress_u24lambda_u2452 = new LinearLayout.LayoutParams(-1, this.ui.dp(14));
        $this$progress_u24lambda_u2452.topMargin = this.ui.dp(6);
        $this$progress_u24lambda_u2452.bottomMargin = this.ui.dp(6);
        Unit unit = Unit.INSTANCE;
        into.addView(p, $this$progress_u24lambda_u2452);
        return p;
    }

    public final void setProgress(ProgressBar p, double pct) {
        Intrinsics.checkNotNullParameter(p, "p");
        if (pct < 0.0d) {
            p.setIndeterminate(true);
        } else {
            p.setIndeterminate(false);
            p.setProgress(RangesKt.coerceIn((int) pct, 0, 100));
        }
    }

    public static /* synthetic */ TextView logBox$default(GxPage gxPage, int i, ViewGroup viewGroup, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logBox");
        }
        if ((i2 & 1) != 0) {
            i = 420;
        }
        if ((i2 & 2) != 0) {
            viewGroup = gxPage.col;
        }
        return gxPage.logBox(i, viewGroup);
    }

    public final TextView logBox(int heightDp, ViewGroup into) {
        Intrinsics.checkNotNullParameter(into, "into");
        TextView tv = new TextView(this.act);
        tv.setTypeface(Typeface.MONOSPACE);
        tv.setTextSize(13.0f);
        tv.setTextColor(Color.rgb(210, 220, 230));
        tv.setTextIsSelectable(true);
        tv.setPadding(this.ui.dp(12), this.ui.dp(10), this.ui.dp(12), this.ui.dp(10));
        HorizontalScrollView $this$logBox_u24lambda_u2454 = new HorizontalScrollView(this.act);
        $this$logBox_u24lambda_u2454.addView(tv);
        NestedScrollView $this$logBox_u24lambda_u2455 = new NestedScrollView(this.act);
        $this$logBox_u24lambda_u2455.addView($this$logBox_u24lambda_u2454);
        $this$logBox_u24lambda_u2455.setBackground(this.ui.rounded(Color.rgb(6, 8, 11), 12, Integer.valueOf(Color.rgb(40, 44, 52))));
        $this$logBox_u24lambda_u2455.setNestedScrollingEnabled(true);
        LinearLayout.LayoutParams $this$logBox_u24lambda_u2456 = new LinearLayout.LayoutParams(-1, this.ui.dp(heightDp));
        $this$logBox_u24lambda_u2456.bottomMargin = this.ui.dp(8);
        Unit unit = Unit.INSTANCE;
        into.addView($this$logBox_u24lambda_u2455, $this$logBox_u24lambda_u2456);
        return tv;
    }

    public final void logToEnd(TextView tv) {
        Intrinsics.checkNotNullParameter(tv, "tv");
        ViewParent parent = tv.getParent();
        ViewParent parent2 = parent != null ? parent.getParent() : null;
        NestedScrollView nestedScrollView = parent2 instanceof NestedScrollView ? (NestedScrollView) parent2 : null;
        if (nestedScrollView != null) {
            final NestedScrollView s = nestedScrollView;
            s.post(new Runnable() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    s.fullScroll(130);
                }
            });
        }
    }

    public static /* synthetic */ ImageView image$default(GxPage gxPage, int i, ViewGroup viewGroup, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: image");
        }
        if ((i2 & 2) != 0) {
            viewGroup = gxPage.col;
        }
        return gxPage.image(i, viewGroup);
    }

    public final ImageView image(int heightDp, ViewGroup into) {
        Intrinsics.checkNotNullParameter(into, "into");
        ImageView iv = new ImageView(this.act);
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv.setBackground(CarUi.rounded$default(this.ui, Color.rgb(6, 8, 11), 12, null, 4, null));
        LinearLayout.LayoutParams $this$image_u24lambda_u2460 = new LinearLayout.LayoutParams(-1, this.ui.dp(heightDp));
        $this$image_u24lambda_u2460.bottomMargin = this.ui.dp(8);
        Unit unit = Unit.INSTANCE;
        into.addView(iv, $this$image_u24lambda_u2460);
        return iv;
    }

    /* VF: from Vineflower (jadx: ) */
    public static Function1 tabs$default(GxPage var0, List var1, int var2, Function1 var3, Function2 var4, int var5, Object var6) {
        if (var6 == null) {
            if ((var5 & 2) != 0) {
                var2 = 0;
            }

            if ((var5 & 4) != 0) {
                var3 = null;
            }

            return var0.tabs(var1, var2, var3, var4);
        } else {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: tabs");
        }
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public final Function1<Integer, Unit> tabs(
        List<String> var1, int var2, Function1<? super Integer, Unit> var3, Function2<? super Integer, ? super LinearLayout, Unit> var4
    ) {
        Intrinsics.checkNotNullParameter(var1, "labels");
        Intrinsics.checkNotNullParameter(var4, "build");
        ObjectRef var5 = new ObjectRef();
        ObjectRef var6 = new ObjectRef();
        Pair var8 = this.ui.browserTabs(var1, 1, new GxPage$$ExternalSyntheticLambda28(var6));
        View var7 = (View)var8.component1();
        var5.element = ((Function1)var8.component2());
        this.col.addView(var7, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
        LinearLayout var10 = new LinearLayout((Context)this.act);
        var10.setOrientation(1);
        var10.setBackground((Drawable)this.ui.corners(CarUi.Companion.getSECTION(), 0, 0, 14, 14));
        var10.setPadding(this.ui.dp(14), this.ui.dp(14), this.ui.dp(14), this.ui.dp(6));
        this.col.addView((View)var10, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
        HashMap var9 = new HashMap();
        var6.element = (new GxPage$$ExternalSyntheticLambda29(var5, var9, var3, this, var10, var4));
        tabs$select(var5, var9, var3, this, var10, var4, RangesKt.coerceIn(var2, 0, var1.size() - 1));
        return new GxPage$$ExternalSyntheticLambda30(var5, var9, var3, this, var10, var4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabs$lambda$61(Ref.ObjectRef $selectRef, int i) {
        Function1 function1;
        if (!$selectRef.element) {
            Intrinsics.throwUninitializedPropertyAccessException("selectRef");
            function1 = null;
        } else {
            function1 = (Function1) $selectRef.element;
        }
        function1.invoke(Integer.valueOf(i));
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private static final void tabs$select(
        ObjectRef<Function1<Integer, Unit>> var0,
        HashMap<Integer, LinearLayout> var1,
        Function1<? super Integer, Unit> var2,
        GxPage var3,
        LinearLayout var4,
        Function2<? super Integer, ? super LinearLayout, Unit> var5,
        int var6
    ) {
        Function1 var9;
        if (var0.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paintTabs");
            var9 = null;
        } else {
            var9 = (Function1)var0.element;
        }

        var9.invoke(var6);
        Iterator var10 = var1.entrySet().iterator();

        while (var10.hasNext()) {
            ((LinearLayout)((java.util.Map.Entry)var10.next()).getValue()).setVisibility(8);
        }

        Map var12 = var1;
        Integer var7 = var6;
        Object var11 = var12.get(var7);
        if (var11 == null) {
            var11 = new LinearLayout((Context)var3.act);
            var11.setOrientation(1);
            var4.addView((View)var11, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));

            try {
                var5.invoke(var6, var11);
            } catch (Exception var8) {
                Log.e("StarView/Gx", "tab " + var6, var8);
                var11.addView((View)var3.empty("This tab failed to build:\n" + var8));
            }

            var12.put(var7, var11);
        }

        ((LinearLayout)var11).setVisibility(0);
        if (var2 != null) {
            var2.invoke(var6);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabs$lambda$66(Ref.ObjectRef $paintTabs, HashMap $boxes, Function1 $onSelect, GxPage this$0, LinearLayout $holder, Function2 $build, int i) {
        tabs$select($paintTabs, $boxes, $onSelect, this$0, $holder, $build, i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabs$lambda$67(Ref.ObjectRef $paintTabs, HashMap $boxes, Function1 $onSelect, GxPage this$0, LinearLayout $holder, Function2 $build, int i) {
        tabs$select($paintTabs, $boxes, $onSelect, this$0, $holder, $build, i);
        return Unit.INSTANCE;
    }

    public final void toast(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        Toast.makeText(this.act, msg, 0).show();
    }

    public static /* synthetic */ void confirm$default(GxPage gxPage, String str, String str2, String str3, Function0 function0, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: confirm");
        }
        if ((i & 4) != 0) {
            str3 = "OK";
        }
        gxPage.confirm(str, str2, str3, function0);
    }

    public final void confirm(String title, String msg, String yes, final Function0<Unit> then) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(yes, "yes");
        Intrinsics.checkNotNullParameter(then, "then");
        AlertDialog.Builder $this$confirm_u24lambda_u2468 = new AlertDialog.Builder(this.act).setTitle(title);
        if (msg != null) {
            $this$confirm_u24lambda_u2468.setMessage(msg);
        }
        $this$confirm_u24lambda_u2468.setPositiveButton(yes, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda19
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                then.invoke();
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    public final void alert(String title, String msg) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(msg, "msg");
        new AlertDialog.Builder(this.act).setTitle(title).setMessage(msg).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
    }

    public static /* synthetic */ void prompt$default(GxPage gxPage, String str, String str2, String str3, int i, String str4, Function1 function1, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: prompt");
        }
        gxPage.prompt(str, (i2 & 2) != 0 ? null : str2, (i2 & 4) != 0 ? "" : str3, (i2 & 8) != 0 ? 1 : i, (i2 & 16) != 0 ? null : str4, function1);
    }

    public final void prompt(String title, String hint, String initial, int inputType, String msg, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(initial, "initial");
        Intrinsics.checkNotNullParameter(then, "then");
        final EditText $this$prompt_u24lambda_u2470 = new EditText(this.act);
        $this$prompt_u24lambda_u2470.setText(initial);
        $this$prompt_u24lambda_u2470.setHint(hint);
        $this$prompt_u24lambda_u2470.setInputType(inputType);
        $this$prompt_u24lambda_u2470.setTextSize(20.0f);
        $this$prompt_u24lambda_u2470.setSelection($this$prompt_u24lambda_u2470.getText().length());
        FrameLayout $this$prompt_u24lambda_u2471 = new FrameLayout(this.act);
        $this$prompt_u24lambda_u2471.setPadding(this.ui.dp(22), this.ui.dp(8), this.ui.dp(22), 0);
        $this$prompt_u24lambda_u2471.addView($this$prompt_u24lambda_u2470);
        AlertDialog.Builder $this$prompt_u24lambda_u2472 = new AlertDialog.Builder(this.act).setTitle(title);
        if (msg != null) {
            $this$prompt_u24lambda_u2472.setMessage(msg);
        }
        $this$prompt_u24lambda_u2472.setView($this$prompt_u24lambda_u2471).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda15
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GxPage.prompt$lambda$73(then, $this$prompt_u24lambda_u2470, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void prompt$lambda$73(Function1 $then, EditText $e, DialogInterface dialogInterface, int i) {
        $then.invoke($e.getText().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pick$lambda$74(Function1 $then, DialogInterface dialogInterface, int i) {
        $then.invoke(Integer.valueOf(i));
    }

    public final void pick(String title, List<String> labels, final Function1<? super Integer, Unit> then) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(labels, "labels");
        Intrinsics.checkNotNullParameter(then, "then");
        List<String> $this$toTypedArray$iv = labels;
        new AlertDialog.Builder(this.act).setTitle(title).setItems((CharSequence[]) $this$toTypedArray$iv.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda21
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GxPage.pick$lambda$74(then, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    public final void pickMany(String title, List<String> labels, Set<Integer> checked, final Function1<? super Set<Integer>, Unit> then) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(labels, "labels");
        Intrinsics.checkNotNullParameter(checked, "checked");
        Intrinsics.checkNotNullParameter(then, "then");
        int size = labels.size();
        final boolean[] arr = new boolean[size];
        for (int i = 0; i < size; i++) {
            arr[i] = checked.contains(Integer.valueOf(i));
        }
        List<String> $this$toTypedArray$iv = labels;
        new AlertDialog.Builder(this.act).setTitle(title).setMultiChoiceItems((CharSequence[]) $this$toTypedArray$iv.toArray(new String[0]), arr, new DialogInterface.OnMultiChoiceClickListener() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda31
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public final void onClick(DialogInterface dialogInterface, int i2, boolean z) {
                GxPage.pickMany$lambda$75(arr, dialogInterface, i2, z);
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GxPage$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                GxPage.pickMany$lambda$77(then, arr, dialogInterface, i2);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickMany$lambda$75(boolean[] $arr, DialogInterface dialogInterface, int i, boolean v) {
        $arr[i] = v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickMany$lambda$77(Function1 $then, boolean[] $arr, DialogInterface dialogInterface, int i) {
        Iterable $this$filter$iv = ArraysKt.getIndices($arr);
        Collection destination$iv$iv = new ArrayList();
        for (Integer num : ((Integer) $this$filter$iv)) {
            int it = num.intValue();
            if ($arr[it]) {
                destination$iv$iv.add(num);
            }
        }
        $then.invoke(CollectionsKt.toSet((List) destination$iv$iv));
    }

    public final String bytes(double b) {
        String format;
        if (b >= 1.0E9d) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.1f GB", Arrays.copyOf(new Object[]{Double.valueOf(b / 1.0E9d)}, 1));
        } else if (b >= 1000000.0d) {
            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.0f MB", Arrays.copyOf(new Object[]{Double.valueOf(b / 1000000.0d)}, 1));
        } else {
            if (b < 1000.0d) {
                return ((long) b) + " B";
            }
            StringCompanionObject stringCompanionObject3 = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.0f KB", Arrays.copyOf(new Object[]{Double.valueOf(b / 1000.0d)}, 1));
        }
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    public final String ago(double epochS) {
        StringBuilder append;
        String str;
        long s = (long) ((System.currentTimeMillis() / 1000.0d) - epochS);
        if (s < 60) {
            return "just now";
        }
        if (s < 3600) {
            append = new StringBuilder().append(s / 60);
            str = " min ago";
        } else if (s < 86400) {
            append = new StringBuilder().append(s / 3600);
            str = " h ago";
        } else {
            append = new StringBuilder().append(s / 86400);
            str = " d ago";
        }
        return append.append(str).toString();
    }

    public static /* synthetic */ String str$default(GxPage gxPage, JSONObject jSONObject, String str, String str2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: str");
        }
        if ((i & 2) != 0) {
            str2 = "";
        }
        return gxPage.str(jSONObject, str, str2);
    }

    public final String str(JSONObject $this$str, String k, String def) {
        Intrinsics.checkNotNullParameter($this$str, "<this>");
        Intrinsics.checkNotNullParameter(k, "k");
        Intrinsics.checkNotNullParameter(def, "def");
        if (!$this$str.has(k) || $this$str.isNull(k)) {
            return def;
        }
        String optString = $this$str.optString(k, def);
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        return optString;
    }

    public final List<JSONObject> objects(JSONArray $this$objects) {
        Intrinsics.checkNotNullParameter($this$objects, "<this>");
        Iterable $this$mapNotNull$iv = RangesKt.until(0, $this$objects.length());
        Collection destination$iv$iv = new ArrayList();
        Iterator<Integer> it = $this$mapNotNull$iv.iterator();
        while (it.hasNext()) {
            int element$iv$iv$iv = ((IntIterator) it).nextInt();
            JSONObject optJSONObject = $this$objects.optJSONObject(element$iv$iv$iv);
            if (optJSONObject != null) {
                destination$iv$iv.add(optJSONObject);
            }
        }
        return (List) destination$iv$iv;
    }

    public final List<String> strings(JSONArray $this$strings) {
        Intrinsics.checkNotNullParameter($this$strings, "<this>");
        Iterable $this$map$iv = RangesKt.until(0, $this$strings.length());
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it = $this$map$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            destination$iv$iv.add($this$strings.optString(item$iv$iv));
        }
        return (List) destination$iv$iv;
    }

    /* compiled from: GxKit.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MediaType getJSON() {
            return GxPage.JSON;
        }

        public final synchronized OkHttpClient client(long timeoutS) {
            Object answer$iv;
            Map $this$getOrPut$iv = GxPage.clients;
            Long valueOf = Long.valueOf(timeoutS);
            Object value$iv = $this$getOrPut$iv.get(valueOf);
            if (value$iv == null) {
                answer$iv = new OkHttpClient.Builder().connectTimeout(5L, TimeUnit.SECONDS).readTimeout(timeoutS, TimeUnit.SECONDS).writeTimeout(timeoutS, TimeUnit.SECONDS).build();
                $this$getOrPut$iv.put(valueOf, answer$iv);
            } else {
                answer$iv = value$iv;
            }
            return (OkHttpClient) answer$iv;
        }
    }
}
