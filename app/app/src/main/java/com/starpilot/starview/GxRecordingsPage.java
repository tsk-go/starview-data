package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.core.widget.NestedScrollView;
import com.starpilot.starview.CarUi;
import com.starpilot.starview.DriveTrack;
import com.starpilot.starview.GxPage;
import com.starpilot.starview.GxRecordingsPage;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.json.JSONArray;
import org.json.JSONObject;
import kotlin.jvm.internal.Ref.ObjectRef;

/* compiled from: GxRecordings.kt */
public final class GxRecordingsPage extends GxPage {
    private boolean preservedOnly;
    private boolean renderPending;
    private Call routeCall;
    private final ArrayList<GxRecordingsRoute> routes;
    private LinearLayout routesDeleteBox;
    private String routesError;
    private int routesGen;
    private LinearLayout routesList;
    private boolean routesLoaded;
    private boolean routesLoading;
    private int routesProgress;
    private TextView routesStats;
    private TextView routesStatus;
    private int routesTotal;
    private Call screenCall;
    private TextView screenCount;
    private LinearLayout screenDeleteBox;
    private String screenError;
    private int screenGen;
    private LinearLayout screenList;
    private boolean screenLoaded;
    private boolean screenLoading;
    private final ArrayList<GxRecordingsScreen> screens;
    private String search;
    private int sortOrder;
    private int tab;
    private int thumbActive;
    private final LruCache<String, Bitmap> thumbCache;
    private final HashSet<String> thumbFailed;
    private final ArrayDeque<ThumbJob> thumbQueue;

    /* VF: from Vineflower (jadx: Type inference failed) */
    public GxRecordingsPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
        this.routes = new ArrayList<>();
        this.routesError = "";
        this.search = "";
        this.screens = new ArrayList<>();
        this.screenError = "";
        this.thumbCache = new LruCache<String, Bitmap>(25165824) {
            protected int sizeOf(String var1, Bitmap var2x) {
                Intrinsics.checkNotNullParameter(var1, "key");
                Intrinsics.checkNotNullParameter(var2x, "value");
                return var2x.getByteCount();
            }
        };
        this.thumbFailed = new HashSet<>();
        this.thumbQueue = new ArrayDeque<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxRecordings.kt */
    static final class ThumbJob {
        private final ImageView iv;
        private final String path;
        private final View ph;
        private final Function0<Boolean> valid;

        public ThumbJob(String path, ImageView iv, View ph, Function0<Boolean> valid) {
            Intrinsics.checkNotNullParameter(path, "path");
            Intrinsics.checkNotNullParameter(iv, "iv");
            Intrinsics.checkNotNullParameter(ph, "ph");
            Intrinsics.checkNotNullParameter(valid, "valid");
            this.path = path;
            this.iv = iv;
            this.ph = ph;
            this.valid = valid;
        }

        public final ImageView getIv() {
            return this.iv;
        }

        public final String getPath() {
            return this.path;
        }

        public final View getPh() {
            return this.ph;
        }

        public final Function0<Boolean> getValid() {
            return this.valid;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$0(GxRecordingsPage this$0, int i) {
        this$0.tab = i;
        if (i == 1 && !this$0.screenLoaded && !this$0.screenLoading) {
            this$0.loadScreens();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1(GxRecordingsPage this$0, int i, LinearLayout b) {
        Intrinsics.checkNotNullParameter(b, "b");
        if (i == 0) {
            this$0.buildRoutes(b);
        } else {
            this$0.buildScreens(b);
        }
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.tabs$default(this, CollectionsKt.listOf(new String[]{"Dashcam Routes", "Screen Recordings"}), 0, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$0;
                build$lambda$0 = GxRecordingsPage.build$lambda$0(GxRecordingsPage.this, ((Integer) obj).intValue());
                return build$lambda$0;
            }
        }, new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit build$lambda$1;
                build$lambda$1 = GxRecordingsPage.build$lambda$1(GxRecordingsPage.this, ((Integer) obj).intValue(), (LinearLayout) obj2);
                return build$lambda$1;
            }
        }, 2, null);
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        loadRoutes();
        if (this.tab == 1 || this.screenLoaded) {
            loadScreens();
        }
    }

    @Override // com.starpilot.starview.GxPage
    public void onHide() {
        Call call = this.routeCall;
        if (call != null) {
            call.cancel();
        }
        this.routeCall = null;
        Call call2 = this.screenCall;
        if (call2 != null) {
            call2.cancel();
        }
        this.screenCall = null;
        if (this.routesLoading) {
            this.routesLoading = false;
            renderRoutes();
        }
        if (this.screenLoading) {
            this.screenLoading = false;
            renderScreens();
        }
    }

    private final void buildRoutes(LinearLayout b) {
        int i;
        LinearLayout card = card(b);
        LinearLayout linearLayout = new LinearLayout(getAct());
        linearLayout.setOrientation(0);
        linearLayout.setGravity(16);
        LinearLayout linearLayout2 = new LinearLayout(getAct());
        linearLayout2.setOrientation(1);
        TextView textView = new TextView(getAct());
        textView.setText("Dashcam Routes");
        textView.setTextColor(CarUi.Companion.getTEXT());
        textView.setTextSize(21.0f);
        linearLayout2.addView(textView);
        TextView textView2 = new TextView(getAct());
        textView2.setTextColor(CarUi.Companion.getDIM());
        textView2.setTextSize(15.0f);
        textView2.setPadding(0, getUi().dp(4), 0, 0);
        this.routesStats = textView2;
        linearLayout2.addView(this.routesStats);
        linearLayout.addView(linearLayout2, new LinearLayout.LayoutParams(0, -2, 1.0f));
        linearLayout.addView(CarUi.button$default(getUi(), "Refresh", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildRoutes$lambda$6;
                buildRoutes$lambda$6 = GxRecordingsPage.buildRoutes$lambda$6(GxRecordingsPage.this);
                return buildRoutes$lambda$6;
            }
        }, 2, null));
        card.addView(linearLayout);
        EditText editText = new EditText(getAct());
        editText.setHint("Search routes, dates, or IDs...");
        editText.setHintTextColor(CarUi.Companion.getDIM());
        editText.setTextColor(CarUi.Companion.getTEXT());
        editText.setTextSize(19.0f);
        editText.setSingleLine(true);
        editText.setInputType(1);
        editText.setBackground(getUi().rounded(CarUi.Companion.getCARD_ON(), 12, Integer.valueOf(Color.rgb(60, 66, 78))));
        editText.setPadding(getUi().dp(16), getUi().dp(12), getUi().dp(16), getUi().dp(12));
        editText.setMinHeight(getUi().dp(56));
        final Runnable runnable = new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                GxRecordingsPage.this.renderRoutes();
            }
        };
        editText.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.GxRecordingsPage$buildRoutes$4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int a, int b2, int c) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int a, int b2, int c) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                String str;
                GxRecordingsPage gxRecordingsPage = GxRecordingsPage.this;
                if (s == null || (str = s.toString()) == null) {
                    str = "";
                }
                gxRecordingsPage.search = str;
                GxRecordingsPage.this.getMain().removeCallbacks(runnable);
                GxRecordingsPage.this.getMain().postDelayed(runnable, 250L);
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = getUi().dp(12);
        Unit unit = Unit.INSTANCE;
        card.addView(editText, layoutParams);
        choice("Sort", null, CollectionsKt.listOf(new String[]{"Newest first", "Oldest first", "Longest duration", "Shortest duration"}), this.sortOrder, b, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildRoutes$lambda$10;
                buildRoutes$lambda$10 = GxRecordingsPage.buildRoutes$lambda$10(GxRecordingsPage.this, ((Integer) obj).intValue());
                return buildRoutes$lambda$10;
            }
        });
        List<String> listOf = CollectionsKt.listOf(new String[]{"All", "Preserved"});
        boolean z = this.preservedOnly;
        choice("Show", null, listOf, z ? 1 : 0, b, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildRoutes$lambda$11;
                buildRoutes$lambda$11 = GxRecordingsPage.buildRoutes$lambda$11(GxRecordingsPage.this, ((Integer) obj).intValue());
                return buildRoutes$lambda$11;
            }
        });
        this.routesStatus = note("", b);
        this.routesList = box(b);
        LinearLayout box = box(b);
        this.routesDeleteBox = box;
        header("Delete local routes", box);
        GxPage.Row row$default = GxPage.row$default(this, "Delete local routes", "This action cannot be undone.", box, 0, 8, null);
        GxPage.Row.button$default(row$default, "Delete Non-Preserved", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildRoutes$lambda$12;
                buildRoutes$lambda$12 = GxRecordingsPage.buildRoutes$lambda$12(GxRecordingsPage.this);
                return buildRoutes$lambda$12;
            }
        }, 2, null);
        TextView button$default = GxPage.Row.button$default(row$default, "Delete All Including Preserved", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildRoutes$lambda$13;
                buildRoutes$lambda$13 = GxRecordingsPage.buildRoutes$lambda$13(GxRecordingsPage.this);
                return buildRoutes$lambda$13;
            }
        }, 2, null);
        i = GxRecordingsKt.GxRecordingsDanger;
        button$default.setTextColor(i);
        renderRoutes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildRoutes$lambda$6(GxRecordingsPage this$0) {
        this$0.loadRoutes();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildRoutes$lambda$10(GxRecordingsPage this$0, int i) {
        this$0.sortOrder = i;
        this$0.renderRoutes();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildRoutes$lambda$11(GxRecordingsPage this$0, int i) {
        this$0.preservedOnly = i == 1;
        this$0.renderRoutes();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildRoutes$lambda$12(GxRecordingsPage this$0) {
        this$0.deleteAllRoutes(false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildRoutes$lambda$13(GxRecordingsPage this$0) {
        this$0.deleteAllRoutes(true);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    private final void loadRoutes() {
        if (this.routesList != null && this.getHost() != null) {
            Call var1 = this.routeCall;
            if (var1 != null) {
                var1.cancel();
            }

            this.routes.clear();
            this.routesLoading = true;
            this.routesError = "";
            this.routesProgress = 0;
            this.routesTotal = 0;
            this.renderRoutes();
            HashSet var2 = new HashSet();
            ObjectRef var3 = new ObjectRef();
            var3.element = this.stream(
                "/api/routes", new GxRecordingsPage$$ExternalSyntheticLambda51(this, var2), new GxRecordingsPage$$ExternalSyntheticLambda52(this, var3)
            );
            this.routeCall = (Call)var3.element;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadRoutes$lambda$15(GxRecordingsPage this$0, HashSet $seen, JSONObject o) {
        Iterable objects;
        Intrinsics.checkNotNullParameter(o, "o");
        if (o.has("progress")) {
            this$0.routesProgress = o.optInt("progress");
        }
        if (o.has("total")) {
            this$0.routesTotal = o.optInt("total");
        }
        JSONArray optJSONArray = o.optJSONArray("routes");
        if (optJSONArray != null && (objects = this$0.objects(optJSONArray)) != null) {
            Iterable $this$forEach$iv = objects;
            for (Object element$iv : $this$forEach$iv) {
                JSONObject j = (JSONObject) element$iv;
                String n = GxPage.str$default(this$0, j, "name", null, 2, null);
                if (!StringsKt.isBlank(n) && $seen.add(n)) {
                    this$0.routes.add(this$0.parseRoute(j));
                }
            }
        }
        this$0.scheduleRender();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadRoutes$lambda$16(GxRecordingsPage this$0, Ref.ObjectRef $call, String err) {
        if (this$0.routeCall != $call.element) {
            return Unit.INSTANCE;
        }
        this$0.routeCall = null;
        this$0.routesLoading = false;
        this$0.routesLoaded = true;
        if (err != null) {
            this$0.routesError = "Couldn't load routes. Try refreshing.";
        }
        this$0.renderRoutes();
        return Unit.INSTANCE;
    }

    private final void scheduleRender() {
        if (this.renderPending) {
            return;
        }
        this.renderPending = true;
        getMain().postDelayed(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda58
            @Override // java.lang.Runnable
            public final void run() {
                GxRecordingsPage.scheduleRender$lambda$17(GxRecordingsPage.this);
            }
        }, 400L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scheduleRender$lambda$17(GxRecordingsPage this$0) {
        this$0.renderPending = false;
        this$0.renderRoutes();
    }

    private final GxRecordingsRoute parseRoute(JSONObject j) {
        String str$default = GxPage.str$default(this, j, "name", null, 2, null);
        String str = null;
        String optString = (!j.has("timestamp") || j.isNull("timestamp")) ? null : j.optString("timestamp");
        String str$default2 = GxPage.str$default(this, j, "startedAt", null, 2, null);
        if (!StringsKt.isBlank(str$default2)) {
            str = str$default2;
        }
        GxRecordingsRoute it = new GxRecordingsRoute(str$default, optString, str, j.optBoolean("isCustomName"), j.optBoolean("is_preserved"), j.optInt("segmentCount"), j.optInt("approxDurationSeconds"), GxPage.str$default(this, j, "png", null, 2, null));
        normalize(it);
        return it;
    }

    private final ZonedDateTime parseStarted(String s) {
        Object m1191constructorimpl;
        Object m1191constructorimpl2;
        Object m1191constructorimpl3;
        String str = s;
        if (str == null || StringsKt.isBlank(str)) {
            return null;
        }
        ZoneId z = ZoneId.systemDefault();
        try {
            Result.Companion companion = Result.Companion;
            GxRecordingsPage gxRecordingsPage = this;
            m1191constructorimpl = KtCompat.resultOf(Instant.parse(s).atZone(z));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        ZonedDateTime zonedDateTime = (ZonedDateTime) m1191constructorimpl;
        if (zonedDateTime != null) {
            return zonedDateTime;
        }
        try {
            Result.Companion companion3 = Result.Companion;
            GxRecordingsPage gxRecordingsPage2 = this;
            m1191constructorimpl2 = KtCompat.resultOf(OffsetDateTime.parse(s).atZoneSameInstant(z));
        } catch (Throwable th2) {
            Result.Companion companion4 = Result.Companion;
            m1191constructorimpl2 = KtCompat.resultOf(ResultKt.createFailure(th2));
        }
        if (KtCompat.isFailure(m1191constructorimpl2)) {
            m1191constructorimpl2 = null;
        }
        ZonedDateTime zonedDateTime2 = (ZonedDateTime) m1191constructorimpl2;
        if (zonedDateTime2 != null) {
            return zonedDateTime2;
        }
        try {
            Result.Companion companion5 = Result.Companion;
            GxRecordingsPage gxRecordingsPage3 = this;
            m1191constructorimpl3 = KtCompat.resultOf(LocalDateTime.parse(s).atZone(z));
        } catch (Throwable th3) {
            Result.Companion companion6 = Result.Companion;
            m1191constructorimpl3 = KtCompat.resultOf(ResultKt.createFailure(th3));
        }
        return (ZonedDateTime) (KtCompat.isFailure(m1191constructorimpl3) ? null : m1191constructorimpl3);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final void normalize(GxRecordingsRoute var1) {
        String var15 = null; Object var18 = null; // declarations Vineflower left out
        ZonedDateTime var6 = this.parseStarted(var1.getTimestamp());
        ZonedDateTime var4 = this.parseStarted(var1.getStartedAt());
        ZonedDateTime var5 = var4;
        if (var4 == null) {
            var5 = var6;
        }

        label141: {
            if (var5 != null) {
                String var7 = var5.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
                String var14 = var5.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
                var7 = var7 + " at " + var14;
                var15 = var7;
                if (var7 != null) {
                    break label141;
                }
            }

            var15 = "Unknown date";
        }

        var1.setDisplayDate(var15);
        boolean var3;
        if (var1.isCustomName() || var1.getTimestamp() != null && var6 == null) {
            var3 = true;
        } else {
            var3 = false;
        }

        var1.setCustomName(var3);
        var3 = var1.isCustomName();
        String var39 = "";
        String var16;
        if (var3) {
            String var50 = var1.getTimestamp();
            var16 = var50;
            if (var50 == null) {
                var16 = "";
            }
        } else {
            var16 = var1.getDisplayDate();
        }

        label126: {
            var1.setDisplayName(var16);
            if (var5 != null) {
                Instant var17 = var5.toInstant();
                if (var17 != null) {
                    var18 = var17.toEpochMilli();
                    break label126;
                }
            }

            var18 = null;
        }

        var1.setStartedAtMs(((Long) var18));
        Iterable var51 = CollectionsKt.listOf(GxRecordingsKt.gxRecordingsNorm(var1.getName()));
        var var19 = new ArrayList();

        for (Object var8 : var51) {
            boolean var2;
            if (((String)var8).length() > 0) {
                var2 = true;
            } else {
                var2 = false;
            }

            if (var2) {
                var19.add(var8);
            }
        }

        List var20 = var19;
        var1.setIds(var20);
        String var21 = var39;
        if (var1.isCustomName()) {
            var21 = var1.getDisplayName();
        }

        Iterable var40 = CollectionsKt.listOf(GxRecordingsKt.gxRecordingsNorm(var21));
        var var22 = new ArrayList();

        for (Object var53 : var40) {
            boolean var10;
            if (((String)var53).length() > 0) {
                var10 = true;
            } else {
                var10 = false;
            }

            if (var10) {
                var22.add(var53);
            }
        }

        List var23 = var22;
        var1.setTitles(var23);
        List var24;
        if (var5 == null) {
            var24 = CollectionsKt.emptyList();
        } else {
            String var9 = var5.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            String var54 = var5.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US));
            String var56 = var5.format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US));
            String var25 = var5.format(DateTimeFormatter.ofPattern("M/d/yyyy", Locale.US));
            String var42 = var5.format(DateTimeFormatter.ofPattern("yyyy-M-d", Locale.US));
            Iterable var43 = CollectionsKt.listOf(new String[]{var9, var54, var56, var25, var42});
            var var26 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var43, 10));
            Iterator var44 = var43.iterator();

            while (var44.hasNext()) {
                var26.add(GxRecordingsKt.gxRecordingsNorm((String)var44.next()));
            }

            List var27 = var26;
            Iterable var45 = var27;
            var var28 = new ArrayList();

            for (Object var46 : var45) {
                boolean var11;
                if (((String)var46).length() > 0) {
                    var11 = true;
                } else {
                    var11 = false;
                }

                if (var11) {
                    var28.add(var46);
                }
            }

            var24 = var28;
        }

        var1.setDates(var24);
        List var29;
        if (var5 == null) {
            var29 = CollectionsKt.emptyList();
        } else {
            String var30 = var5.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            String var47 = var5.format(DateTimeFormatter.ofPattern("h:mm a", Locale.US));
            String var34 = var5.format(DateTimeFormatter.ofPattern("HH:mm", Locale.US));
            Iterable var35 = CollectionsKt.listOf(new String[]{var30, var47, var34});
            var var31 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var35, 10));
            Iterator var36 = var35.iterator();

            while (var36.hasNext()) {
                var31.add(GxRecordingsKt.gxRecordingsNorm((String)var36.next()));
            }

            List var32 = var31;
            Iterable var37 = var32;
            var var33 = new ArrayList();

            for (Object var38 : var37) {
                boolean var12;
                if (((String)var38).length() > 0) {
                    var12 = true;
                } else {
                    var12 = false;
                }

                if (var12) {
                    var33.add(var38);
                }
            }

            var29 = var33;
        }

        var1.setTimes(var29);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final boolean matches(GxRecordingsRoute var1, String var2) {
        boolean var23 = false; boolean var25 = false; // declarations Vineflower left out
        String var10 = StringsKt.trim(var2).toString();
        var2 = GxRecordingsKt.gxRecordingsNorm(var2);
        CharSequence var9 = var2;
        boolean var8 = false;
        Iterable var11 = StringsKt.split(var9, new String[]{" "}, false, 0);
        var var33 = new ArrayList();

        for (Object var12 : var11) {
            boolean var3;
            if (((String)var12).length() > 0) {
                var3 = true;
            } else {
                var3 = false;
            }

            if (var3) {
                var33.add(var12);
            }
        }

        List var34 = var33;
        if (var34.isEmpty()) {
            return true;
        } else {
            var11 = var34;
            int var19;
            if (var11 instanceof Collection && ((Collection)var11).isEmpty()) {
                var19 = 0;
            } else {
                Iterator var45 = var11.iterator();

                while (true) {
                    if (!var45.hasNext()) {
                        var19 = 0;
                        break;
                    }

                    label268: {
                        String var40 = (String)var45.next();
                        if (var40.length() >= 3) {
                            var var13 = GxRecordingsKt.GxRecordingsMonths;
                            if (var13 instanceof Collection && ((Collection)var13).isEmpty()) {
                                var19 = (((boolean)false) ? 1 : 0);
                            } else {
                                Iterator var53 = var13.iterator();

                                while (true) {
                                    if (!var53.hasNext()) {
                                        var19 = (((boolean)false) ? 1 : 0);
                                        break;
                                    }

                                    if (StringsKt.startsWith((String)var53.next(), var40, false)) {
                                        var19 = (((boolean)true) ? 1 : 0);
                                        break;
                                    }
                                }
                            }

                            if ((var19 != 0)) {
                                var19 = (((boolean)true) ? 1 : 0);
                                break label268;
                            }
                        }

                        var19 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var19 != 0)) {
                        var19 = 1;
                        break;
                    }
                }
            }

            boolean var4;
            if ((var19 == 0)
                && !new Regex("\\d\\s*[/-]\\s*\\d").containsMatchIn(var10)
                && !new Regex("\\d+(?:st|nd|rd|th)\\b", RegexOption.IGNORE_CASE).containsMatchIn(var10)
                && !new Regex("^\\d{4}$").matches(var2)) {
                var4 = false;
            } else {
                var4 = true;
            }

            boolean var5;
            label243: {
                if (!new Regex("^\\d{1,2}$").matches(var2) && !new Regex("\\d\\s*:\\s*\\d").containsMatchIn(var10)) {
                    var11 = var34;
                    if (var11 instanceof Collection && ((Collection)var11).isEmpty()) {
                        var19 = (((boolean)false) ? 1 : 0);
                    } else {
                        Iterator var46 = var11.iterator();

                        while (true) {
                            if (!var46.hasNext()) {
                                var19 = (((boolean)false) ? 1 : 0);
                                break;
                            }

                            String var42 = (String)var46.next();
                            if (!Intrinsics.areEqual(var42, "am") && !Intrinsics.areEqual(var42, "pm")) {
                                var19 = (((boolean)false) ? 1 : 0);
                            } else {
                                var19 = (((boolean)true) ? 1 : 0);
                            }

                            if ((var19 != 0)) {
                                var19 = (((boolean)true) ? 1 : 0);
                                break;
                            }
                        }
                    }

                    if ((var19 == 0)) {
                        var5 = false;
                        break label243;
                    }
                }

                var5 = true;
            }

            char var31;
            label221: {
                label220: {
                    String var43 = StringsKt.replace(var2, " ", "", false);
                    boolean var7 = new Regex("^[0-9a-f]+$").matches(var43);
                    if (!StringsKt.contains(var10, "--", false)) {
                        if (!var7) {
                            break label220;
                        }

                        if (var43.length() < 8) {
                            if (var43.length() < 4) {
                                break label220;
                            }

                            CharSequence var35 = var43;
                            var19 = 0;

                            while (true) {
                                if (var19 >= var35.length()) {
                                    var23 = false;
                                    break;
                                }

                                if (Character.isDigit(var35.charAt(var19))) {
                                    var23 = true;
                                    break;
                                }

                                var19++;
                            }

                            if (!var23) {
                                break label220;
                            }

                            CharSequence var36 = var43;
                            var19 = 0;

                            while (true) {
                                if (var19 >= var36.length()) {
                                    var25 = false;
                                    break;
                                }

                                var31 = var36.charAt(var19);
                                boolean var30;
                                if ('a' <= var31 && var31 < 'g') {
                                    var30 = true;
                                } else {
                                    var30 = false;
                                }

                                if (var30) {
                                    var25 = true;
                                    break;
                                }

                                var19++;
                            }

                            if (!var25) {
                                break label220;
                            }
                        }
                    }

                    var31 = '\u0001';
                    break label221;
                }

                var31 = '\u0000';
            }

            Regex var37 = new Regex("^\\d{1,2}$");
            Regex var44 = new Regex("^\\d{4}$");
            var var47 = var1.getTitles();
            boolean var26;
            if (var47 instanceof Collection && ((Collection)var47).isEmpty()) {
                var26 = false;
            } else {
                Iterator var48 = var47.iterator();

                while (true) {
                    if (!var48.hasNext()) {
                        var26 = false;
                        break;
                    }

                    if (matches$m$default(var2, var34, var37, var44, (String)var48.next(), false, 32, null)) {
                        var26 = true;
                        break;
                    }
                }
            }

            if (!var26) {
                if (var4) {
                    var47 = var1.getDates();
                    if (var47 instanceof Collection && ((Collection)var47).isEmpty()) {
                        var26 = false;
                    } else {
                        Iterator var50 = var47.iterator();

                        while (true) {
                            if (!var50.hasNext()) {
                                var26 = false;
                                break;
                            }

                            if (matches$m(var2, var34, var37, var44, (String)var50.next(), true)) {
                                var26 = true;
                                break;
                            }
                        }
                    }

                    if (var26) {
                        return true;
                    }
                }

                if (var5) {
                    var47 = var1.getTimes();
                    if (var47 instanceof Collection && ((Collection)var47).isEmpty()) {
                        var26 = false;
                    } else {
                        Iterator var52 = var47.iterator();

                        while (true) {
                            if (!var52.hasNext()) {
                                var26 = false;
                                break;
                            }

                            if (matches$m$default(var2, var34, var37, var44, (String)var52.next(), false, 32, null)) {
                                var26 = true;
                                break;
                            }
                        }
                    }

                    if (var26) {
                        return true;
                    }
                }

                if (var31 == 0) {
                    return var8;
                }

                var var14 = var1.getIds();
                if (var14 instanceof Collection && ((Collection)var14).isEmpty()) {
                    var26 = false;
                } else {
                    Iterator var15 = var14.iterator();

                    while (true) {
                        if (!var15.hasNext()) {
                            var26 = false;
                            break;
                        }

                        if (matches$m$default(var2, var34, var37, var44, (String)var15.next(), false, 32, null)) {
                            var26 = true;
                            break;
                        }
                    }
                }

                if (!var26) {
                    return var8;
                }
            }

            return true;
        }
    }

    static /* synthetic */ boolean matches$m$default(String str, List list, Regex regex, Regex regex2, String str2, boolean z, int i, Object obj) {
        if ((i & 32) != 0) {
            z = false;
        }
        return matches$m(str, list, regex, regex2, str2, z);
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private static final boolean matches$m(String var0, List<String> var1, Regex var2, Regex var3, String var4, boolean var5) {
        CharSequence var11 = var4;
        CharSequence var13 = var0;
        boolean var8 = false;
        byte var7 = 2;
        boolean var10 = StringsKt.contains(var11, var13, false);
        boolean var9 = true;
        if (var10) {
            return true;
        } else {
            Iterable var18 = StringsKt.split(var4, new String[]{" "}, false, 0);
            var var14 = new ArrayList();

            for (Object var19 : var18) {
                boolean var6;
                if (((String)var19).length() > 0) {
                    var6 = true;
                } else {
                    var6 = false;
                }

                if (var6) {
                    var14.add(var19);
                }
            }

            List var15 = var14;
            var var16 = var1;
            if (var16 instanceof Collection && ((Collection)var16).isEmpty()) {
                var8 = var9;
            } else {
                Iterator var17 = var16.iterator();

                label64:
                do {
                    if (!var17.hasNext()) {
                        var8 = true;
                        break;
                    }

                    var4 = (String)var17.next();
                    var var28 = var15;
                    if (var28 instanceof Collection && ((Collection)var28).isEmpty()) {
                        var9 = var8;
                    } else {
                        for (String var12 : (Iterable<String>) var28) {
                            if (var5 && var2.matches(var4) && var3.matches(var12)) {
                                var8 = false;
                            } else {
                                var8 = StringsKt.startsWith(var12, var4, false);
                            }

                            var9 = false;
                            byte var21 = 2;
                            if (var8) {
                                var10 = true;
                                var8 = var9;
                                var9 = var10;
                                continue label64;
                            }

                            var21 = 2;
                            var8 = false;
                        }

                        var9 = var8;
                    }
                } while (var9);
            }

            return var8;
        }
    }

    private final List<GxRecordingsRoute> sorted(List<GxRecordingsRoute> list) {
        switch (this.sortOrder) {
            case 2:
            case 3:
                final int dir = this.sortOrder != 2 ? 1 : -1;
                final Function2 function2 = new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda14
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        int sorted$lambda$43;
                        sorted$lambda$43 = GxRecordingsPage.sorted$lambda$43(dir, (GxRecordingsRoute) obj, (GxRecordingsRoute) obj2);
                        return Integer.valueOf(sorted$lambda$43);
                    }
                };
                return CollectionsKt.sortedWith(list, new Comparator() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda15
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int sorted$lambda$44;
                        sorted$lambda$44 = GxRecordingsPage.sorted$lambda$44(function2, obj, obj2);
                        return sorted$lambda$44;
                    }
                });
            default:
                final int dir2 = this.sortOrder == 1 ? 1 : -1;
                final Function2 function22 = new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda16
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        int sorted$lambda$45;
                        sorted$lambda$45 = GxRecordingsPage.sorted$lambda$45(dir2, (GxRecordingsRoute) obj, (GxRecordingsRoute) obj2);
                        return Integer.valueOf(sorted$lambda$45);
                    }
                };
                return CollectionsKt.sortedWith(list, new Comparator() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda17
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int sorted$lambda$46;
                        sorted$lambda$46 = GxRecordingsPage.sorted$lambda$46(function22, obj, obj2);
                        return sorted$lambda$46;
                    }
                });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sorted$lambda$43(int $dir, GxRecordingsRoute a, GxRecordingsRoute b) {
        return a.getApproxDurationSeconds() != b.getApproxDurationSeconds() ? (a.getApproxDurationSeconds() - b.getApproxDurationSeconds()) * $dir : a.getName().compareTo(b.getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sorted$lambda$44(Function2 $tmp0, Object p0, Object p1) {
        return ((Number) $tmp0.invoke(p0, p1)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sorted$lambda$46(Function2 $tmp0, Object p0, Object p1) {
        return ((Number) $tmp0.invoke(p0, p1)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sorted$lambda$45(int $dir, GxRecordingsRoute a, GxRecordingsRoute b) {
        Long l = a.getStartedAtMs();
        Long r = b.getStartedAtMs();
        if (l == null && r == null) {
            return a.getName().compareTo(b.getName());
        }
        if (l == null) {
            return 1;
        }
        if (r == null) {
            return -1;
        }
        return !Intrinsics.areEqual(l, r) ? Intrinsics.compare(l.longValue(), r.longValue()) * $dir : a.getName().compareTo(b.getName()) * (-$dir);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderRoutes() {
        String gxRecordingsDuration;
        int count$iv;
        LinearLayout list = this.routesList;
        if (list == null) {
            return;
        }
        this.routesGen++;
        int gen = this.routesGen;
        list.removeAllViews();
        TextView textView = this.routesStats;
        if (textView != null) {
            int size = this.routes.size();
            Iterator it = this.routes.iterator();
            int i = 0;
            while (it.hasNext()) {
                i += ((GxRecordingsRoute) it.next()).getApproxDurationSeconds();
            }
            gxRecordingsDuration = GxRecordingsKt.gxRecordingsDuration(i);
            Iterable $this$count$iv = this.routes;
            if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
                count$iv = 0;
            } else {
                count$iv = 0;
                for (Object element$iv : $this$count$iv) {
                    if (((GxRecordingsRoute) element$iv).getIsPreserved() && (count$iv = count$iv + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            int it2 = count$iv;
            textView.setText(size + " drives · " + gxRecordingsDuration + ((Object) (it2 > 0 ? " · " + it2 + " preserved" : "")));
        }
        TextView $this$renderRoutes_u24lambda_u2450 = this.routesStatus;
        if ($this$renderRoutes_u24lambda_u2450 != null) {
            if (this.routesLoading) {
                $this$renderRoutes_u24lambda_u2450.setText(this.routesTotal > 0 ? "Finding local routes... (" + this.routesProgress + " of " + this.routesTotal + ")" : "Finding local routes...");
                $this$renderRoutes_u24lambda_u2450.setTextColor(CarUi.Companion.getDIM());
                $this$renderRoutes_u24lambda_u2450.setVisibility(0);
            } else if (this.routesError.length() > 0) {
                $this$renderRoutes_u24lambda_u2450.setText(this.routesError);
                $this$renderRoutes_u24lambda_u2450.setTextColor(CarUi.Companion.getWARN());
                $this$renderRoutes_u24lambda_u2450.setVisibility(0);
            } else {
                $this$renderRoutes_u24lambda_u2450.setVisibility(8);
            }
        }
        Iterable $this$filter$iv = this.routes;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GxRecordingsRoute it3 = (GxRecordingsRoute) element$iv$iv;
            if (((!this.preservedOnly || it3.getIsPreserved()) && matches(it3, this.search))) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List visible = sorted((List) destination$iv$iv);
        if (visible.isEmpty() && !this.routesLoading) {
            list.addView(empty("No routes found."));
        }
        for (GxRecordingsRoute r : (Iterable<GxRecordingsRoute>) CollectionsKt.take(visible, 250)) {
            routeRow(r, list, gen);
        }
        if (visible.size() > 250) {
            note("Showing the first 250 of " + visible.size() + " matching routes. Search to narrow the list.", list);
        }
        LinearLayout linearLayout = this.routesDeleteBox;
        if (linearLayout != null) {
            linearLayout.setVisibility(this.routes.isEmpty() ? 8 : 0);
        }
    }

    private final String routeDesc(GxRecordingsRoute r) {
        String gxRecordingsDuration;
        String str = r.getIsPreserved() ? "Preserved · " : "";
        String str2 = r.getIsCustomName() ? r.getDisplayDate() + " · " : "";
        gxRecordingsDuration = GxRecordingsKt.gxRecordingsDuration(r.getApproxDurationSeconds());
        return str + str2 + gxRecordingsDuration + " · " + r.getSegmentCount() + " segments";
    }

    private final void routeRow(final GxRecordingsRoute r, LinearLayout list, final int gen) {
        int i;
        GxRecordingsPage gxRecordingsPage = this;
        String displayName = r.getDisplayName();
        if (StringsKt.isBlank(displayName)) {
            displayName = r.getName();
        }
        GxPage.Row row = GxPage.row$default(gxRecordingsPage, displayName, routeDesc(r), list, 0, 8, null);
        if (r.getIsPreserved()) {
            row.getTitleView().setTextColor(CarUi.Companion.getACCENT());
        }
        thumbInto(row, r.getPng(), 150, 84, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda62
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean routeRow$lambda$53;
                routeRow$lambda$53 = GxRecordingsPage.routeRow$lambda$53(gen, GxRecordingsPage.this);
                return Boolean.valueOf(routeRow$lambda$53);
            }
        });
        GxPage.Row.button$default(row, r.getIsPreserved() ? "Unpreserve" : "Preserve", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit routeRow$lambda$54;
                routeRow$lambda$54 = GxRecordingsPage.routeRow$lambda$54(GxRecordingsPage.this, r);
                return routeRow$lambda$54;
            }
        }, 2, null);
        GxPage.Row.button$default(row, "Logs", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit routeRow$lambda$55;
                routeRow$lambda$55 = GxRecordingsPage.routeRow$lambda$55(GxRecordingsPage.this, r);
                return routeRow$lambda$55;
            }
        }, 2, null);
        GxPage.Row.button$default(row, "Rename", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit routeRow$lambda$56;
                routeRow$lambda$56 = GxRecordingsPage.routeRow$lambda$56(GxRecordingsPage.this, r);
                return routeRow$lambda$56;
            }
        }, 2, null);
        TextView button$default = GxPage.Row.button$default(row, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit routeRow$lambda$57;
                routeRow$lambda$57 = GxRecordingsPage.routeRow$lambda$57(GxRecordingsPage.this, r);
                return routeRow$lambda$57;
            }
        }, 2, null);
        i = GxRecordingsKt.GxRecordingsDanger;
        button$default.setTextColor(i);
        row.getView().setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GxRecordingsPage.routeRow$lambda$58(GxRecordingsPage.this, r, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean routeRow$lambda$53(int $gen, GxRecordingsPage this$0) {
        return $gen == this$0.routesGen;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit routeRow$lambda$54(GxRecordingsPage this$0, GxRecordingsRoute $r) {
        togglePreserved$default(this$0, $r, null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit routeRow$lambda$55(GxRecordingsPage this$0, GxRecordingsRoute $r) {
        this$0.openLogs($r);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit routeRow$lambda$56(GxRecordingsPage this$0, GxRecordingsRoute $r) {
        renameRoute$default(this$0, $r, null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit routeRow$lambda$57(GxRecordingsPage this$0, GxRecordingsRoute $r) {
        deleteRoute$default(this$0, $r, null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void routeRow$lambda$58(GxRecordingsPage this$0, GxRecordingsRoute $r, View it) {
        new RoutePlayer(this$0, $r, null, 2, null);
    }

    /* VF: from Vineflower (jadx: ) */
    static void togglePreserved$default(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = null;
        }

        var0.togglePreserved(var1, var2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void togglePreserved(final GxRecordingsRoute r, final Function0<Unit> then) {
        GxPage.send$default(this, r.getIsPreserved() ? "DELETE" : "POST", "/api/routes/" + enc(r.getName()) + "/preserve", null, 0L, new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda46
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit unit;
                unit = GxRecordingsPage.togglePreserved$lambda$59(GxRecordingsPage.this, r, then, (String) obj, (JSONObject) obj2);
                return unit;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit togglePreserved$lambda$59(GxRecordingsPage this$0, GxRecordingsRoute $r, Function0 $then, String err, JSONObject jSONObject) {
        if (err != null) {
            this$0.toast((StringsKt.startsWith(err, "HTTP", false) || Intrinsics.areEqual(err, "bad reply")) ? "Failed to update preserved state." : err);
        } else {
            $r.setPreserved(!$r.getIsPreserved());
            this$0.renderRoutes();
            if ($then != null) {
                $then.invoke();
            }
        }
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    static void deleteRoute$default(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = null;
        }

        var0.deleteRoute(var1, var2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deleteRoute(final GxRecordingsRoute r, final Function0<Unit> then) {
        confirm("Delete route?", "Delete “" + r.getDisplayName() + "”?", "Delete", new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit deleteRoute$lambda$61;
                deleteRoute$lambda$61 = GxRecordingsPage.deleteRoute$lambda$61(GxRecordingsPage.this, r, then);
                return deleteRoute$lambda$61;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteRoute$lambda$61(final GxRecordingsPage this$0, final GxRecordingsRoute $r, final Function0 $then) {
        GxPage.send$default(this$0, "DELETE", "/api/routes/" + this$0.enc($r.getName()), null, 0L, new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit deleteRoute$lambda$61$lambda$60;
                deleteRoute$lambda$61$lambda$60 = GxRecordingsPage.deleteRoute$lambda$61$lambda$60(this$0, $r, $then, (String) obj, (JSONObject) obj2);
                return deleteRoute$lambda$61$lambda$60;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteRoute$lambda$61$lambda$60(GxRecordingsPage this$0, GxRecordingsRoute $r, Function0 $then, String err, JSONObject jSONObject) {
        if (err != null) {
            this$0.toast("Delete failed.");
        } else {
            this$0.routes.remove($r);
            this$0.renderRoutes();
            this$0.toast("Route deleted!");
            if ($then != null) {
                $then.invoke();
            }
        }
        return Unit.INSTANCE;
    }

    private final void deleteAllRoutes(final boolean includePreserved) {
        String label = includePreserved ? "Delete all routes, including preserved?" : "Delete all non-preserved routes?";
        confirm(label, "This action cannot be undone.", includePreserved ? "Delete Everything" : "Delete Non-Preserved", new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda60
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit deleteAllRoutes$lambda$64;
                deleteAllRoutes$lambda$64 = GxRecordingsPage.deleteAllRoutes$lambda$64(GxRecordingsPage.this, includePreserved);
                return deleteAllRoutes$lambda$64;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteAllRoutes$lambda$64(final GxRecordingsPage this$0, boolean $includePreserved) {
        this$0.toast("Deleting routes…");
        GxPage.send$default(this$0, "DELETE", "/api/routes/delete_all?include_preserved=" + $includePreserved, null, 180L, new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda59
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit deleteAllRoutes$lambda$64$lambda$63;
                deleteAllRoutes$lambda$64$lambda$63 = GxRecordingsPage.deleteAllRoutes$lambda$64$lambda$63(this$0, (String) obj, (JSONObject) obj2);
                return deleteAllRoutes$lambda$64$lambda$63;
            }
        }, 4, null);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit deleteAllRoutes$lambda$64$lambda$63(GxRecordingsPage var0, String var1, JSONObject var2) {
        if (var1 != null) {
            if (Intrinsics.areEqual(var1, "bad reply")) {
                var1 = "Failed to delete routes.";
            }

            var0.toast(var1);
        } else {
            label25: {
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var6 = var1;
                        CharSequence var4 = var6;
                        if (StringsKt.isBlank(var6)) {
                            var4 = null;
                        }

                        var1 = (String)var4;
                        if (var1 != null) {
                            break label25;
                        }
                    }
                }

                var1 = "Routes deleted!";
            }

            var0.toast(var1);
            var0.loadRoutes();
        }

        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    static void renameRoute$default(GxRecordingsPage var0, GxRecordingsRoute var1, Function0 var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = null;
        }

        var0.renameRoute(var1, var2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renameRoute(final GxRecordingsRoute r, final Function0<Unit> then) {
        final EditText $this$renameRoute_u24lambda_u2465 = new EditText(getAct());
        $this$renameRoute_u24lambda_u2465.setText(r.getDisplayName());
        $this$renameRoute_u24lambda_u2465.setTextSize(20.0f);
        $this$renameRoute_u24lambda_u2465.setInputType(1);
        $this$renameRoute_u24lambda_u2465.setSelection($this$renameRoute_u24lambda_u2465.getText().length());
        FrameLayout $this$renameRoute_u24lambda_u2466 = new FrameLayout(getAct());
        $this$renameRoute_u24lambda_u2466.setPadding(getUi().dp(22), getUi().dp(8), getUi().dp(22), 0);
        $this$renameRoute_u24lambda_u2466.addView($this$renameRoute_u24lambda_u2465);
        AlertDialog.Builder b = new AlertDialog.Builder(getAct()).setTitle("Rename route").setMessage("Rename “" + r.getDisplayName() + "”").setView($this$renameRoute_u24lambda_u2466).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda53
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                GxRecordingsPage.renameRoute$lambda$69($this$renameRoute_u24lambda_u2465, r, GxRecordingsPage.this, then, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null);
        if (r.getIsCustomName()) {
            b.setNeutralButton("Reset name", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda54
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    GxRecordingsPage.renameRoute$lambda$72(GxRecordingsPage.this, r, then, dialogInterface, i);
                }
            });
        }
        b.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renameRoute$lambda$69(EditText $e, final GxRecordingsRoute $r, final GxRecordingsPage this$0, final Function0 $then, DialogInterface dialogInterface, int i) {
        final String n = StringsKt.trim((CharSequence) $e.getText().toString()).toString();
        if ((n.length() == 0) || Intrinsics.areEqual(n, $r.getDisplayName())) {
            return;
        }
        GxPage.send$default(this$0, "POST", "/api/routes/rename", new JSONObject().put("old", $r.getName()).put("new", n), 0L, new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit renameRoute$lambda$69$lambda$68;
                renameRoute$lambda$69$lambda$68 = GxRecordingsPage.renameRoute$lambda$69$lambda$68(this$0, $r, n, $then, (String) obj, (JSONObject) obj2);
                return renameRoute$lambda$69$lambda$68;
            }
        }, 8, null);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit renameRoute$lambda$69$lambda$68(
        GxRecordingsPage var0, GxRecordingsRoute var1, String var2, Function0 var3, String var4, JSONObject var5
    ) {
        if (var4 != null) {
            var0.toast("Rename failed.");
        } else {
            if (var5 != null) {
                var4 = GxPage.str$default(var0, var5, "name", null, 2, null);
                if (var4 != null) {
                    CharSequence var9 = var4;
                    CharSequence var7 = var9;
                    if (StringsKt.isBlank(var9)) {
                        var7 = null;
                    }

                    var4 = (String)var7;
                    if (var4 != null) {
                        var2 = var4;
                    }
                }
            }

            var1.setTimestamp(var2);
            var1.setCustomName(true);
            var0.normalize(var1);
            var0.renderRoutes();
            var0.toast("Route renamed!");
            if (var3 != null) {
                var3.invoke();
            }
        }

        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renameRoute$lambda$72(final GxRecordingsPage this$0, final GxRecordingsRoute $r, final Function0 $then, DialogInterface dialogInterface, int i) {
        GxPage.send$default(this$0, "POST", "/api/routes/reset_name", new JSONObject().put("name", $r.getName()), 0L, new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda57
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit renameRoute$lambda$72$lambda$71;
                renameRoute$lambda$72$lambda$71 = GxRecordingsPage.renameRoute$lambda$72$lambda$71(this$0, $r, $then, (String) obj, (JSONObject) obj2);
                return renameRoute$lambda$72$lambda$71;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renameRoute$lambda$72$lambda$71(GxRecordingsPage this$0, GxRecordingsRoute $r, Function0 $then, String err, JSONObject o) {
        String str$default;
        if (err != null) {
            this$0.toast("Resetting name failed...");
        } else {
            String str = null;
            if (o != null && (str$default = GxPage.str$default(this$0, o, "timestamp", null, 2, null)) != null) {
                String str2 = str$default;
                if (!StringsKt.isBlank(str2)) {
                    str = str2;
                }
                str = str;
            }
            $r.setTimestamp(str);
            $r.setCustomName(false);
            this$0.normalize($r);
            this$0.renderRoutes();
            this$0.toast("Route name reset!");
            if ($then != null) {
                $then.invoke();
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openLogs(final GxRecordingsRoute r) {
        getJson("/api/routes/" + enc(r.getName()) + "/logs", new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda48
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit openLogs$lambda$82;
                openLogs$lambda$82 = GxRecordingsPage.openLogs$lambda$82(GxRecordingsPage.this, r, (String) obj, (JSONObject) obj2);
                return openLogs$lambda$82;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit openLogs$lambda$82(final GxRecordingsPage this$0, final GxRecordingsRoute $r, String err, JSONObject o) {
        List segs0;
        String gxRecordingsBytes;
        String gxRecordingsBytes2;
        if (err != null || o == null) {
            this$0.toast((err == null || StringsKt.startsWith(err, "HTTP", false) || Intrinsics.areEqual(err, "bad reply")) ? "Could not read logs." : err);
            return Unit.INSTANCE;
        }
        JSONArray optJSONArray = o.optJSONArray("segments");
        if (optJSONArray == null || (segs0 = this$0.objects(optJSONArray)) == null) {
            segs0 = CollectionsKt.emptyList();
        }
        final List segs = segs0;
        Sheet s = new Sheet(this$0, $r.getDisplayName() + " Logs", true);
        GxRecordingsPage gxRecordingsPage = this$0;
        String str = segs.size() + " segment" + (segs.size() == 1 ? "" : "s");
        gxRecordingsBytes = GxRecordingsKt.gxRecordingsBytes(o.optDouble("totalBytes", 0.0d));
        double d = 0.0d;
        GxPage.Row top = GxPage.row$default(gxRecordingsPage, str, gxRecordingsBytes + " total download", s.getBody(), 0, 8, null);
        top.button("Download all (.tar)", true, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit openLogs$lambda$82$lambda$73;
                openLogs$lambda$82$lambda$73 = GxRecordingsPage.openLogs$lambda$82$lambda$73(this$0, $r);
                return openLogs$lambda$82$lambda$73;
            }
        });
        GxPage.Row.button$default(top, "Find on map", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit openLogs$lambda$82$lambda$79;
                openLogs$lambda$82$lambda$79 = GxRecordingsPage.openLogs$lambda$82$lambda$79(this$0, $r, segs);
                return openLogs$lambda$82$lambda$79;
            }
        }, 2, null);
        for (final JSONObject seg : (Iterable<JSONObject>) segs) {
            final String fn = GxPage.str$default(this$0, seg, "filename", null, 2, null);
            String str2 = "Segment " + seg.optInt("segmentNum");
            gxRecordingsBytes2 = GxRecordingsKt.gxRecordingsBytes(seg.optDouble("bytes", d));
            GxPage.Row sr = GxPage.row$default(this$0, str2, fn + " · " + gxRecordingsBytes2, s.getBody(), 0, 8, null);
            String str$default = GxPage.str$default(this$0, seg, "segment", null, 2, null);
            if (StringsKt.isBlank(str$default)) {
                str$default = $r.getName() + "--" + seg.optInt("segmentNum");
            }
            final String segName = str$default;
            GxPage.Row.button$default(sr, "Download", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda55
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit openLogs$lambda$82$lambda$81;
                    openLogs$lambda$82$lambda$81 = GxRecordingsPage.openLogs$lambda$82$lambda$81(this$0, seg, segName, fn);
                    return openLogs$lambda$82$lambda$81;
                }
            }, 2, null);
            d = 0.0d;
        }
        s.getDialog().show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit openLogs$lambda$82$lambda$73(GxRecordingsPage this$0, GxRecordingsRoute $r) {
        this$0.download("/api/routes/" + this$0.enc($r.getName()) + "/logs/download", $r.getName() + "-logs.tar", "application/x-tar");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit openLogs$lambda$82$lambda$79(final GxRecordingsPage this$0, final GxRecordingsRoute $r, final List $segs) {
        List pts = this$0.routeTrack($r);
        if (pts == null) {
            return Unit.INSTANCE;
        }
        final Sheet ms = new Sheet(this$0, $r.getDisplayName() + " - tap where", false);
        Long startedAtMs = $r.getStartedAtMs();
        Intrinsics.checkNotNull(startedAtMs);
        final long start = startedAtMs.longValue();
        ms.getBody().addView(new TrackMapView(this$0.getAct(), pts, start, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda45
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit openLogs$lambda$82$lambda$79$lambda$78;
                openLogs$lambda$82$lambda$79$lambda$78 = GxRecordingsPage.openLogs$lambda$82$lambda$79$lambda$78(start, $segs, this$0, ms, $r, ((Long) obj).longValue());
                return openLogs$lambda$82$lambda$79$lambda$78;
            }
        }), new LinearLayout.LayoutParams(-1, 0, 1.0f));
        ms.getDialog().show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit openLogs$lambda$82$lambda$79$lambda$78(long $start, List $segs, final GxRecordingsPage this$0, final Sheet $ms, final GxRecordingsRoute $r, final long t) {
        Object element$iv;
        final int n = (int) ((t - $start) / 60000);
        long at = (t - $start) % 60000;
        List $this$firstOrNull$iv = $segs;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                boolean it3 = it2.optInt("segmentNum") == n;
                if (it3) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        final JSONObject seg = (JSONObject) element$iv;
        List opts = CollectionsKt.mutableListOf("Play from here");
        if (seg != null) {
            opts.add("Download segment " + n + " logs");
        }
        this$0.pick("Segment " + n + ", " + (at / 1000) + " s in", opts, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit openLogs$lambda$82$lambda$79$lambda$78$lambda$77;
                openLogs$lambda$82$lambda$79$lambda$78$lambda$77 = GxRecordingsPage.openLogs$lambda$82$lambda$79$lambda$78$lambda$77($ms, this$0, $r, t, seg, n, ((Integer) obj).intValue());
                return openLogs$lambda$82$lambda$79$lambda$78$lambda$77;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit openLogs$lambda$82$lambda$79$lambda$78$lambda$77(Sheet $ms, GxRecordingsPage this$0, GxRecordingsRoute $r, long $t, JSONObject $seg, int $n, int i) {
        if (i == 0) {
            $ms.getDialog().dismiss();
            new RoutePlayer(this$0, $r, Long.valueOf($t));
        } else if ($seg != null) {
            GxRecordingsPage gxRecordingsPage = this$0;
            String str$default = GxPage.str$default(this$0, $seg, "url", null, 2, null);
            String str$default2 = GxPage.str$default(this$0, $seg, "segment", null, 2, null);
            if (StringsKt.isBlank(str$default2)) {
                str$default2 = $r.getName() + "--" + $n;
            }
            GxPage.download$default(gxRecordingsPage, str$default, ((Object) str$default2) + "-" + GxPage.str$default(this$0, $seg, "filename", null, 2, null), null, 4, null);
            Unit unit = Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit openLogs$lambda$82$lambda$81(GxRecordingsPage this$0, JSONObject $seg, String $segName, String $fn) {
        GxPage.download$default(this$0, GxPage.str$default(this$0, $seg, "url", null, 2, null), $segName + "-" + $fn, null, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<DriveTrack.Pt> routeTrack(GxRecordingsRoute r) {
        Long startedAtMs = r.getStartedAtMs();
        if (startedAtMs == null) {
            GxRecordingsPage $this$routeTrack_u24lambda_u2483 = this;
            $this$routeTrack_u24lambda_u2483.toast("This drive has no start time - can't find it on the map.");
            return null;
        }
        long start = startedAtMs.longValue();
        long end = ((Math.max(r.getSegmentCount(), 1) + 1) * 60000) + start;
        List pts = DriveTrack.INSTANCE.between(getAct(), start - 30000, end);
        if (pts.size() >= 2) {
            return pts;
        }
        toast("No path for this drive: the tablet keeps one for drives from StarView 2.70 on, while it is connected.");
        return null;
    }

    private final void buildScreens(LinearLayout b) {
        int i;
        LinearLayout c = card(b);
        LinearLayout top = new LinearLayout(getAct());
        top.setOrientation(0);
        top.setGravity(16);
        LinearLayout tb = new LinearLayout(getAct());
        tb.setOrientation(1);
        TextView $this$buildScreens_u24lambda_u2486 = new TextView(getAct());
        $this$buildScreens_u24lambda_u2486.setText("Screen Recordings");
        $this$buildScreens_u24lambda_u2486.setTextColor(CarUi.Companion.getTEXT());
        $this$buildScreens_u24lambda_u2486.setTextSize(21.0f);
        tb.addView($this$buildScreens_u24lambda_u2486);
        TextView $this$buildScreens_u24lambda_u2487 = new TextView(getAct());
        $this$buildScreens_u24lambda_u2487.setTextColor(CarUi.Companion.getDIM());
        $this$buildScreens_u24lambda_u2487.setTextSize(15.0f);
        $this$buildScreens_u24lambda_u2487.setPadding(0, getUi().dp(4), 0, 0);
        this.screenCount = $this$buildScreens_u24lambda_u2487;
        tb.addView(this.screenCount);
        top.addView(tb, new LinearLayout.LayoutParams(0, -2, 1.0f));
        top.addView(CarUi.button$default(getUi(), "Refresh", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda49
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildScreens$lambda$88;
                buildScreens$lambda$88 = GxRecordingsPage.buildScreens$lambda$88(GxRecordingsPage.this);
                return buildScreens$lambda$88;
            }
        }, 2, null));
        c.addView(top);
        this.screenList = box(b);
        LinearLayout del = box(b);
        this.screenDeleteBox = del;
        header("Delete recordings", del);
        TextView button$default = GxPage.Row.button$default(GxPage.row$default(this, "Delete recordings", "This permanently deletes every screen recording.", del, 0, 8, null), "Delete All Recordings", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda50
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildScreens$lambda$89;
                buildScreens$lambda$89 = GxRecordingsPage.buildScreens$lambda$89(GxRecordingsPage.this);
                return buildScreens$lambda$89;
            }
        }, 2, null);
        i = GxRecordingsKt.GxRecordingsDanger;
        button$default.setTextColor(i);
        renderScreens();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildScreens$lambda$88(GxRecordingsPage this$0) {
        this$0.loadScreens();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildScreens$lambda$89(GxRecordingsPage this$0) {
        this$0.deleteAllScreens();
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    private final void loadScreens() {
        if (this.screenList != null && this.getHost() != null) {
            Call var1 = this.screenCall;
            if (var1 != null) {
                var1.cancel();
            }

            this.screens.clear();
            this.screenLoading = true;
            this.screenError = "";
            this.renderScreens();
            HashSet var3 = new HashSet();
            ObjectRef var2 = new ObjectRef();
            var2.element = this.stream(
                "/api/screen_recordings/list",
                new GxRecordingsPage$$ExternalSyntheticLambda24(this, var3),
                new GxRecordingsPage$$ExternalSyntheticLambda25(this, var2)
            );
            this.screenCall = (Call)var2.element;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadScreens$lambda$91(GxRecordingsPage this$0, HashSet $seen, JSONObject o) {
        List arr;
        Intrinsics.checkNotNullParameter(o, "o");
        JSONArray optJSONArray = o.optJSONArray("recordings");
        if (optJSONArray == null || (arr = this$0.objects(optJSONArray)) == null) {
            arr = CollectionsKt.emptyList();
        }
        boolean added = false;
        for (JSONObject j : (Iterable<JSONObject>) arr) {
            String fn = GxPage.str$default(this$0, j, "filename", null, 2, null);
            if (!StringsKt.isBlank(fn) && $seen.add(fn)) {
                this$0.screens.add(new GxRecordingsScreen(fn, GxPage.str$default(this$0, j, "png", null, 2, null), GxPage.str$default(this$0, j, "timestamp", null, 2, null), j.optBoolean("is_custom_name")));
                added = true;
            }
        }
        if (added) {
            List $this$sortByDescending$iv = this$0.screens;
            if ($this$sortByDescending$iv.size() > 1) {
                CollectionsKt.sortWith($this$sortByDescending$iv, new Comparator() { // from class: com.starpilot.starview.GxRecordingsPage$loadScreens$lambda$91$$inlined$sortByDescending$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(Object t, Object t2) {
                        GxRecordingsScreen it = (GxRecordingsScreen) t2;
                        GxRecordingsScreen it2 = (GxRecordingsScreen) t;
                        return ComparisonsKt.compareValues(it.getTimestamp(), it2.getTimestamp());
                    }
                });
            }
            this$0.renderScreens();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadScreens$lambda$92(GxRecordingsPage this$0, Ref.ObjectRef $call, String err) {
        if (this$0.screenCall != $call.element) {
            return Unit.INSTANCE;
        }
        this$0.screenCall = null;
        this$0.screenLoading = false;
        this$0.screenLoaded = true;
        if (err != null) {
            this$0.screenError = "Couldn't load recordings.";
        }
        this$0.renderScreens();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String screenName(GxRecordingsScreen rec) {
        Object m1191constructorimpl;
        if (rec.getIsCustomName()) {
            return StringsKt.replace(new Regex("\\.mp4$", RegexOption.IGNORE_CASE).replace(rec.getFilename(), ""), "_", " ", false);
        }
        try {
            Result.Companion companion = Result.Companion;
            GxRecordingsPage gxRecordingsPage = this;
            m1191constructorimpl = KtCompat.resultOf(LocalDateTime.parse(rec.getTimestamp()));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        LocalDateTime d = (LocalDateTime) m1191constructorimpl;
        if (d == null) {
            String timestamp = rec.getTimestamp();
            if (StringsKt.isBlank(timestamp)) {
                timestamp = "Unknown date";
            }
            return timestamp;
        }
        int day = d.getDayOfMonth();
        int i = day % 100;
        boolean z = false;
        if (11 <= i && i < 14) {
            z = true;
        }
        String suffix = "th";
        if (!z) {
            switch (day % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
            }
        }
        int hour = d.getHour() % 12 == 0 ? 12 : d.getHour() % 12;
        String format = d.format(DateTimeFormatter.ofPattern("MMMM", Locale.US));
        int year = d.getYear();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format2 = String.format(Locale.US, "%02d", Arrays.copyOf(new Object[]{Integer.valueOf(d.getMinute())}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        return format + " " + day + suffix + ", " + year + " - " + hour + ":" + format2 + (d.getHour() >= 12 ? "pm" : "am");
    }

    private final void renderScreens() {
        int i;
        LinearLayout list = this.screenList;
        if (list == null) {
            return;
        }
        this.screenGen++;
        final int gen = this.screenGen;
        list.removeAllViews();
        TextView textView = this.screenCount;
        if (textView != null) {
            textView.setText(this.screens.size() + " recording" + (this.screens.size() == 1 ? "" : "s"));
        }
        if (this.screenLoading && this.screens.isEmpty()) {
            list.addView(empty("Loading screen recordings..."));
        } else {
            if ((this.screenError.length() > 0) && this.screens.isEmpty()) {
                TextView $this$renderScreens_u24lambda_u2495 = empty(this.screenError);
                $this$renderScreens_u24lambda_u2495.setTextColor(CarUi.Companion.getWARN());
                list.addView($this$renderScreens_u24lambda_u2495);
            } else if (this.screens.isEmpty()) {
                list.addView(empty("No screen recordings found."));
            }
        }
        Iterator<GxRecordingsScreen> it = this.screens.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            GxRecordingsScreen next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            final GxRecordingsScreen rec = next;
            GxPage.Row row = GxPage.row$default(this, screenName(rec), rec.getFilename(), list, 0, 8, null);
            thumbInto(row, rec.getPng(), 150, 76, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda37
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean renderScreens$lambda$96;
                    renderScreens$lambda$96 = GxRecordingsPage.renderScreens$lambda$96(gen, GxRecordingsPage.this);
                    return Boolean.valueOf(renderScreens$lambda$96);
                }
            });
            row.button("Play", true, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda38
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderScreens$lambda$97;
                    renderScreens$lambda$97 = GxRecordingsPage.renderScreens$lambda$97(GxRecordingsPage.this, rec);
                    return renderScreens$lambda$97;
                }
            });
            GxPage.Row.button$default(row, "Rename", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda39
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderScreens$lambda$98;
                    renderScreens$lambda$98 = GxRecordingsPage.renderScreens$lambda$98(GxRecordingsPage.this, rec);
                    return renderScreens$lambda$98;
                }
            }, 2, null);
            GxPage.Row.button$default(row, "Download", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda40
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderScreens$lambda$99;
                    renderScreens$lambda$99 = GxRecordingsPage.renderScreens$lambda$99(GxRecordingsPage.this, rec);
                    return renderScreens$lambda$99;
                }
            }, 2, null);
            TextView button$default = GxPage.Row.button$default(row, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda41
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderScreens$lambda$100;
                    renderScreens$lambda$100 = GxRecordingsPage.renderScreens$lambda$100(GxRecordingsPage.this, rec);
                    return renderScreens$lambda$100;
                }
            }, 2, null);
            i = GxRecordingsKt.GxRecordingsDanger;
            button$default.setTextColor(i);
            row.getView().setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda42
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GxRecordingsPage.renderScreens$lambda$101(GxRecordingsPage.this, rec, view);
                }
            });
        }
        LinearLayout linearLayout = this.screenDeleteBox;
        if (linearLayout != null) {
            linearLayout.setVisibility(this.screens.isEmpty() ? 8 : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean renderScreens$lambda$96(int $gen, GxRecordingsPage this$0) {
        return $gen == this$0.screenGen;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderScreens$lambda$97(GxRecordingsPage this$0, GxRecordingsScreen $rec) {
        new ScreenPlayer(this$0, $rec);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderScreens$lambda$98(GxRecordingsPage this$0, GxRecordingsScreen $rec) {
        renameScreen$default(this$0, $rec, null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderScreens$lambda$99(GxRecordingsPage this$0, GxRecordingsScreen $rec) {
        this$0.downloadScreen($rec);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderScreens$lambda$100(GxRecordingsPage this$0, GxRecordingsScreen $rec) {
        deleteScreen$default(this$0, $rec, null, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renderScreens$lambda$101(GxRecordingsPage this$0, GxRecordingsScreen $rec, View it) {
        new ScreenPlayer(this$0, $rec);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String screenVideoPath(GxRecordingsScreen rec) {
        return "/api/screen_recordings/download/" + enc(rec.getFilename());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void downloadScreen(GxRecordingsScreen rec) {
        download(screenVideoPath(rec), rec.getFilename(), "video/mp4");
    }

    /* VF: from Vineflower (jadx: ) */
    static void renameScreen$default(GxRecordingsPage var0, GxRecordingsScreen var1, Function0 var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = null;
        }

        var0.renameScreen(var1, var2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renameScreen(final GxRecordingsScreen rec, final Function0<Unit> then) {
        final String base = new Regex("\\.mp4$", RegexOption.IGNORE_CASE).replace(rec.getFilename(), "");
        GxPage.prompt$default(this, "Rename recording:", null, base, 0, null, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renameScreen$lambda$103;
                renameScreen$lambda$103 = GxRecordingsPage.renameScreen$lambda$103(base, GxRecordingsPage.this, rec, then, (String) obj);
                return renameScreen$lambda$103;
            }
        }, 26, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renameScreen$lambda$103(String $base, final GxRecordingsPage this$0, GxRecordingsScreen $rec, final Function0 $then, String v) {
        Intrinsics.checkNotNullParameter(v, "v");
        String n = StringsKt.trim((CharSequence) v).toString();
        if ((n.length() == 0) || Intrinsics.areEqual(n, $base)) {
            return Unit.INSTANCE;
        }
        GxPage.send$default(this$0, "POST", "/api/screen_recordings/rename", new JSONObject().put("old", $rec.getFilename()).put("new", n + ".mp4"), 0L, new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda56
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit renameScreen$lambda$103$lambda$102;
                renameScreen$lambda$103$lambda$102 = GxRecordingsPage.renameScreen$lambda$103$lambda$102(this$0, $then, (String) obj, (JSONObject) obj2);
                return renameScreen$lambda$103$lambda$102;
            }
        }, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renameScreen$lambda$103$lambda$102(GxRecordingsPage this$0, Function0 $then, String err, JSONObject jSONObject) {
        if (err != null) {
            this$0.toast("Rename failed.");
        } else {
            this$0.toast("Recording renamed!");
            if ($then != null) {
                $then.invoke();
            }
            this$0.loadScreens();
        }
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    static void deleteScreen$default(GxRecordingsPage var0, GxRecordingsScreen var1, Function0 var2, int var3, Object var4) {
        if ((var3 & 2) != 0) {
            var2 = null;
        }

        var0.deleteScreen(var1, var2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deleteScreen(final GxRecordingsScreen rec, final Function0<Unit> then) {
        confirm("Delete recording?", "Delete “" + rec.getFilename() + "”?", "Delete", new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda61
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit deleteScreen$lambda$106;
                deleteScreen$lambda$106 = GxRecordingsPage.deleteScreen$lambda$106(GxRecordingsPage.this, rec, then);
                return deleteScreen$lambda$106;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteScreen$lambda$106(final GxRecordingsPage this$0, final GxRecordingsScreen $rec, final Function0 $then) {
        GxPage.send$default(this$0, "DELETE", "/api/screen_recordings/delete/" + this$0.enc($rec.getFilename()), null, 0L, new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit deleteScreen$lambda$106$lambda$105;
                deleteScreen$lambda$106$lambda$105 = GxRecordingsPage.deleteScreen$lambda$106$lambda$105(this$0, $then, $rec, (String) obj, (JSONObject) obj2);
                return deleteScreen$lambda$106$lambda$105;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteScreen$lambda$106$lambda$105(GxRecordingsPage this$0, Function0 $then, final GxRecordingsScreen $rec, String err, JSONObject jSONObject) {
        if (err != null) {
            this$0.toast("Delete failed.");
        } else {
            CollectionsKt.removeAll((List) this$0.screens, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    boolean deleteScreen$lambda$106$lambda$105$lambda$104;
                    deleteScreen$lambda$106$lambda$105$lambda$104 = GxRecordingsPage.deleteScreen$lambda$106$lambda$105$lambda$104($rec, (GxRecordingsScreen) obj);
                    return Boolean.valueOf(deleteScreen$lambda$106$lambda$105$lambda$104);
                }
            });
            this$0.renderScreens();
            this$0.toast("Recording deleted!");
            if ($then != null) {
                $then.invoke();
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean deleteScreen$lambda$106$lambda$105$lambda$104(GxRecordingsScreen $rec, GxRecordingsScreen it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Intrinsics.areEqual(it.getFilename(), $rec.getFilename());
    }

    private final void deleteAllScreens() {
        confirm("Delete all recordings?", "This permanently deletes every screen recording.", "Delete All", new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit deleteAllScreens$lambda$109;
                deleteAllScreens$lambda$109 = GxRecordingsPage.deleteAllScreens$lambda$109(GxRecordingsPage.this);
                return deleteAllScreens$lambda$109;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteAllScreens$lambda$109(final GxRecordingsPage this$0) {
        GxPage.send$default(this$0, "DELETE", "/api/screen_recordings/delete_all", null, 120L, new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit deleteAllScreens$lambda$109$lambda$108;
                deleteAllScreens$lambda$109$lambda$108 = GxRecordingsPage.deleteAllScreens$lambda$109$lambda$108(this$0, (String) obj, (JSONObject) obj2);
                return deleteAllScreens$lambda$109$lambda$108;
            }
        }, 4, null);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit deleteAllScreens$lambda$109$lambda$108(GxRecordingsPage var0, String var1, JSONObject var2) {
        if (var1 != null) {
            if (Intrinsics.areEqual(var1, "bad reply")) {
                var1 = "Delete failed.";
            }

            var0.toast(var1);
        } else {
            label25: {
                var0.screens.clear();
                var0.renderScreens();
                if (var2 != null) {
                    var1 = GxPage.str$default(var0, var2, "message", null, 2, null);
                    if (var1 != null) {
                        CharSequence var6 = var1;
                        CharSequence var4 = var6;
                        if (StringsKt.isBlank(var6)) {
                            var4 = null;
                        }

                        var1 = (String)var4;
                        if (var1 != null) {
                            break label25;
                        }
                    }
                }

                var1 = "All screen recordings deleted!";
            }

            var0.toast(var1);
        }

        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxRecordings.kt */
    static final class RoutePlayer {
        private String cam;
        private final LinearLayout camBox;
        private List<String> cameras;
        private final LinearLayout controls;
        private int current;
        private final FrameLayout frame;
        private Long jumpTo;
        private TrackMapView mapView;
        private final MediaController mc;
        private final LinearLayout mid;
        private final TextView next;
        private Function1<? super Integer, Unit> paintQual;
        private int pendingSeekMs;
        private TextView preserveBtn;
        private final TextView prev;
        private boolean preview;
        private final LinearLayout qualBox;
        private final GxRecordingsRoute r;
        private final LinearLayout segBar;
        private final TextView segLabel;
        private List<String> segments;
        private final Sheet sheet;
        private final TextView status;
        final /* synthetic */ GxRecordingsPage this$0;
        private final Runnable tickPos;
        private int token;
        private final VideoView vv;

        public RoutePlayer(GxRecordingsPage this$0, GxRecordingsRoute r, Long startAtMs) {
            int i;
            Intrinsics.checkNotNullParameter(r, "r");
            this.this$0 = this$0;
            this.r = r;
            this.sheet = new Sheet(this.this$0, this.r.getDisplayName(), false);
            FrameLayout $this$frame_u24lambda_u240 = new FrameLayout(this.this$0.getAct());
            $this$frame_u24lambda_u240.setBackgroundColor(-16777216);
            this.frame = $this$frame_u24lambda_u240;
            LinearLayout $this$mid_u24lambda_u241 = new LinearLayout(this.this$0.getAct());
            $this$mid_u24lambda_u241.setOrientation(0);
            this.mid = $this$mid_u24lambda_u241;
            this.jumpTo = startAtMs;
            final GxRecordingsPage gxRecordingsPage = this.this$0;
            this.tickPos = new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$tickPos$1
                @Override // java.lang.Runnable
                public void run() {
                    Long startedAtMs;
                    TrackMapView m = GxRecordingsPage.RoutePlayer.this.getMapView();
                    if (m == null || (startedAtMs = GxRecordingsPage.RoutePlayer.this.getR().getStartedAtMs()) == null) {
                        return;
                    }
                    long st = startedAtMs.longValue();
                    if (GxRecordingsPage.RoutePlayer.this.getSheet().getDialog().isShowing()) {
                        if (GxRecordingsPage.RoutePlayer.this.getVv().isPlaying()) {
                            m.setNowMs((GxRecordingsPage.RoutePlayer.this.segNum(GxRecordingsPage.RoutePlayer.this.getCurrent()) * 60000) + st + GxRecordingsPage.RoutePlayer.this.getVv().getCurrentPosition());
                        }
                        gxRecordingsPage.getMain().postDelayed(this, 500L);
                    }
                }
            };
            this.vv = new VideoView(this.this$0.getAct());
            this.mc = new MediaController(this.this$0.getAct());
            TextView $this$status_u24lambda_u242 = new TextView(this.this$0.getAct());
            GxRecordingsPage gxRecordingsPage2 = this.this$0;
            $this$status_u24lambda_u242.setTextColor(CarUi.Companion.getTEXT());
            $this$status_u24lambda_u242.setTextSize(19.0f);
            $this$status_u24lambda_u242.setGravity(17);
            $this$status_u24lambda_u242.setPadding(gxRecordingsPage2.getUi().dp(20), gxRecordingsPage2.getUi().dp(12), gxRecordingsPage2.getUi().dp(20), gxRecordingsPage2.getUi().dp(12));
            $this$status_u24lambda_u242.setBackground(CarUi.rounded$default(gxRecordingsPage2.getUi(), Color.argb(190, 0, 0, 0), 12, null, 4, null));
            this.status = $this$status_u24lambda_u242;
            LinearLayout $this$controls_u24lambda_u243 = new LinearLayout(this.this$0.getAct());
            $this$controls_u24lambda_u243.setOrientation(1);
            $this$controls_u24lambda_u243.setVisibility(8);
            this.controls = $this$controls_u24lambda_u243;
            LinearLayout $this$segBar_u24lambda_u244 = new LinearLayout(this.this$0.getAct());
            $this$segBar_u24lambda_u244.setOrientation(0);
            $this$segBar_u24lambda_u244.setGravity(16);
            this.segBar = $this$segBar_u24lambda_u244;
            LinearLayout $this$camBox_u24lambda_u245 = new LinearLayout(this.this$0.getAct());
            $this$camBox_u24lambda_u245.setOrientation(0);
            $this$camBox_u24lambda_u245.setGravity(16);
            this.camBox = $this$camBox_u24lambda_u245;
            LinearLayout $this$qualBox_u24lambda_u246 = new LinearLayout(this.this$0.getAct());
            $this$qualBox_u24lambda_u246.setOrientation(0);
            $this$qualBox_u24lambda_u246.setGravity(16);
            this.qualBox = $this$qualBox_u24lambda_u246;
            this.paintQual = new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda18
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit paintQual$lambda$7;
                    paintQual$lambda$7 = GxRecordingsPage.RoutePlayer.paintQual$lambda$7(((Integer) obj).intValue());
                    return paintQual$lambda$7;
                }
            };
            this.segments = CollectionsKt.emptyList();
            this.cameras = CollectionsKt.emptyList();
            this.cam = "forward";
            this.frame.addView(this.vv, new FrameLayout.LayoutParams(-1, -1, 17));
            this.frame.addView(this.status, new FrameLayout.LayoutParams(-2, -2, 17));
            this.mid.addView(this.frame, new LinearLayout.LayoutParams(0, -1, 1.0f));
            LinearLayout body = this.sheet.getBody();
            LinearLayout linearLayout = this.mid;
            LinearLayout.LayoutParams $this$_init__u24lambda_u248 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
            $this$_init__u24lambda_u248.bottomMargin = this.this$0.getUi().dp(10);
            Unit unit = Unit.INSTANCE;
            body.addView(linearLayout, $this$_init__u24lambda_u248);
            this.mc.setAnchorView(this.frame);
            this.vv.setMediaController(this.mc);
            this.vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda23
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    GxRecordingsPage.RoutePlayer._init_$lambda$9(GxRecordingsPage.RoutePlayer.this, mediaPlayer);
                }
            });
            this.vv.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda1
                @Override // android.media.MediaPlayer.OnErrorListener
                public final boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
                    boolean _init_$lambda$10;
                    _init_$lambda$10 = GxRecordingsPage.RoutePlayer._init_$lambda$10(GxRecordingsPage.RoutePlayer.this, mediaPlayer, i2, i3);
                    return _init_$lambda$10;
                }
            });
            this.vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda2
                @Override // android.media.MediaPlayer.OnCompletionListener
                public final void onCompletion(MediaPlayer mediaPlayer) {
                    GxRecordingsPage.RoutePlayer._init_$lambda$11(GxRecordingsPage.RoutePlayer.this, mediaPlayer);
                }
            });
            this.prev = CarUi.button$default(this.this$0.getUi(), "‹ Prev", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$12;
                    _init_$lambda$12 = GxRecordingsPage.RoutePlayer._init_$lambda$12(GxRecordingsPage.RoutePlayer.this);
                    return _init_$lambda$12;
                }
            }, 2, null);
            CarUi ui = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage3 = this.this$0;
            this.segLabel = CarUi.button$default(ui, "Segment", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$15;
                    _init_$lambda$15 = GxRecordingsPage.RoutePlayer._init_$lambda$15(GxRecordingsPage.RoutePlayer.this.this$0, GxRecordingsPage.RoutePlayer.this);
                    return _init_$lambda$15;
                }
            }, 2, null);
            this.next = CarUi.button$default(this.this$0.getUi(), "Next ›", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$16;
                    _init_$lambda$16 = GxRecordingsPage.RoutePlayer._init_$lambda$16(GxRecordingsPage.RoutePlayer.this);
                    return _init_$lambda$16;
                }
            }, 2, null);
            for (TextView v : (Iterable<TextView>) (Iterable) CollectionsKt.listOf(new TextView[]{this.prev, this.segLabel, this.next})) {
                this.segBar.addView(v, gap());
            }
            this.segBar.addView(new View(this.this$0.getAct()), new LinearLayout.LayoutParams(this.this$0.getUi().dp(14), 1));
            this.segBar.addView(this.camBox);
            this.segBar.addView(new View(this.this$0.getAct()), new LinearLayout.LayoutParams(this.this$0.getUi().dp(14), 1));
            this.segBar.addView(this.qualBox);
            LinearLayout linearLayout2 = this.controls;
            HorizontalScrollView $this$_init__u24lambda_u2417 = new HorizontalScrollView(this.this$0.getAct());
            $this$_init__u24lambda_u2417.setHorizontalScrollBarEnabled(false);
            $this$_init__u24lambda_u2417.addView(this.segBar);
            linearLayout2.addView($this$_init__u24lambda_u2417);
            LinearLayout acts = new LinearLayout(this.this$0.getAct());
            GxRecordingsPage gxRecordingsPage4 = this.this$0;
            acts.setOrientation(0);
            acts.setPadding(0, gxRecordingsPage4.getUi().dp(8), 0, 0);
            CarUi ui2 = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage5 = this.this$0;
            acts.addView(ui2.button("Download", true, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$19;
                    _init_$lambda$19 = GxRecordingsPage.RoutePlayer._init_$lambda$19(GxRecordingsPage.RoutePlayer.this.this$0, GxRecordingsPage.RoutePlayer.this);
                    return _init_$lambda$19;
                }
            }), gap());
            CarUi ui3 = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage6 = this.this$0;
            acts.addView(CarUi.button$default(ui3, "Logs", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$20;
                    _init_$lambda$20 = GxRecordingsPage.RoutePlayer._init_$lambda$20(GxRecordingsPage.RoutePlayer.this.this$0, GxRecordingsPage.RoutePlayer.this);
                    return _init_$lambda$20;
                }
            }, 2, null), gap());
            acts.addView(CarUi.button$default(this.this$0.getUi(), "Map", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$21;
                    _init_$lambda$21 = GxRecordingsPage.RoutePlayer._init_$lambda$21(GxRecordingsPage.RoutePlayer.this);
                    return _init_$lambda$21;
                }
            }, 2, null), gap());
            CarUi ui4 = this.this$0.getUi();
            String str = this.r.getIsPreserved() ? "Unpreserve" : "Preserve";
            final GxRecordingsPage gxRecordingsPage7 = this.this$0;
            this.preserveBtn = CarUi.button$default(ui4, str, false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda19
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$23;
                    _init_$lambda$23 = GxRecordingsPage.RoutePlayer._init_$lambda$23(GxRecordingsPage.RoutePlayer.this.this$0, GxRecordingsPage.RoutePlayer.this);
                    return _init_$lambda$23;
                }
            }, 2, null);
            acts.addView(this.preserveBtn, gap());
            CarUi ui5 = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage8 = this.this$0;
            acts.addView(CarUi.button$default(ui5, "Rename", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda20
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$25;
                    _init_$lambda$25 = GxRecordingsPage.RoutePlayer._init_$lambda$25(GxRecordingsPage.RoutePlayer.this.this$0, GxRecordingsPage.RoutePlayer.this);
                    return _init_$lambda$25;
                }
            }, 2, null), gap());
            CarUi ui6 = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage9 = this.this$0;
            TextView $this$_init__u24lambda_u2428 = CarUi.button$default(ui6, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda21
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$27;
                    _init_$lambda$27 = GxRecordingsPage.RoutePlayer._init_$lambda$27(GxRecordingsPage.RoutePlayer.this.this$0, GxRecordingsPage.RoutePlayer.this);
                    return _init_$lambda$27;
                }
            }, 2, null);
            i = GxRecordingsKt.GxRecordingsDanger;
            $this$_init__u24lambda_u2428.setTextColor(i);
            acts.addView($this$_init__u24lambda_u2428, gap());
            LinearLayout linearLayout3 = this.controls;
            HorizontalScrollView $this$_init__u24lambda_u2429 = new HorizontalScrollView(this.this$0.getAct());
            $this$_init__u24lambda_u2429.setHorizontalScrollBarEnabled(false);
            $this$_init__u24lambda_u2429.addView(acts);
            linearLayout3.addView($this$_init__u24lambda_u2429);
            this.sheet.getBody().addView(this.controls);
            Dialog dialog = this.sheet.getDialog();
            final GxRecordingsPage gxRecordingsPage10 = this.this$0;
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda22
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    GxRecordingsPage.RoutePlayer._init_$lambda$31(GxRecordingsPage.RoutePlayer.this, gxRecordingsPage10, dialogInterface);
                }
            });
            this.sheet.getDialog().show();
            fetch();
        }

        public /* synthetic */ RoutePlayer(GxRecordingsPage gxRecordingsPage, GxRecordingsRoute gxRecordingsRoute, Long l, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(gxRecordingsPage, gxRecordingsRoute, (i & 2) != 0 ? null : l);
        }

        public final GxRecordingsRoute getR() {
            return this.r;
        }

        public final Sheet getSheet() {
            return this.sheet;
        }

        public final FrameLayout getFrame() {
            return this.frame;
        }

        public final LinearLayout getMid() {
            return this.mid;
        }

        public final TrackMapView getMapView() {
            return this.mapView;
        }

        public final void setMapView(TrackMapView trackMapView) {
            this.mapView = trackMapView;
        }

        public final int getPendingSeekMs() {
            return this.pendingSeekMs;
        }

        public final void setPendingSeekMs(int i) {
            this.pendingSeekMs = i;
        }

        public final Long getJumpTo() {
            return this.jumpTo;
        }

        public final void setJumpTo(Long l) {
            this.jumpTo = l;
        }

        public final Runnable getTickPos() {
            return this.tickPos;
        }

        public final VideoView getVv() {
            return this.vv;
        }

        public final MediaController getMc() {
            return this.mc;
        }

        public final TextView getStatus() {
            return this.status;
        }

        public final LinearLayout getControls() {
            return this.controls;
        }

        public final LinearLayout getSegBar() {
            return this.segBar;
        }

        public final TextView getNext() {
            return this.next;
        }

        public final TextView getPrev() {
            return this.prev;
        }

        public final TextView getSegLabel() {
            return this.segLabel;
        }

        public final LinearLayout getCamBox() {
            return this.camBox;
        }

        public final LinearLayout getQualBox() {
            return this.qualBox;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit paintQual$lambda$7(int it) {
            return Unit.INSTANCE;
        }

        public final Function1<Integer, Unit> getPaintQual() {
            return (Function1) this.paintQual;
        }

        public final void setPaintQual(Function1<? super Integer, Unit> function1) {
            Intrinsics.checkNotNullParameter(function1, "<set-?>");
            this.paintQual = function1;
        }

        public final TextView getPreserveBtn() {
            return this.preserveBtn;
        }

        public final void setPreserveBtn(TextView textView) {
            this.preserveBtn = textView;
        }

        public final List<String> getSegments() {
            return this.segments;
        }

        public final void setSegments(List<String> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.segments = list;
        }

        public final List<String> getCameras() {
            return this.cameras;
        }

        public final void setCameras(List<String> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.cameras = list;
        }

        public final String getCam() {
            return this.cam;
        }

        public final void setCam(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.cam = str;
        }

        public final boolean getPreview() {
            return this.preview;
        }

        public final void setPreview(boolean z) {
            this.preview = z;
        }

        public final int getCurrent() {
            return this.current;
        }

        public final void setCurrent(int i) {
            this.current = i;
        }

        public final int getToken() {
            return this.token;
        }

        public final void setToken(int i) {
            this.token = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$9(RoutePlayer this$0, MediaPlayer it) {
            this$0.status.setVisibility(8);
            if (this$0.pendingSeekMs > 0) {
                this$0.vv.seekTo(this$0.pendingSeekMs);
                this$0.pendingSeekMs = 0;
            }
            this$0.vv.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean _init_$lambda$10(RoutePlayer this$0, MediaPlayer mediaPlayer, int i, int i2) {
            if (!this$0.preview) {
                this$0.setStatus("This segment could not be played.");
                return true;
            }
            this$0.preview = false;
            this$0.paintQual.invoke(0);
            this$0.play();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$11(RoutePlayer this$0, MediaPlayer it) {
            if (this$0.current < this$0.segments.size() - 1) {
                this$0.current++;
                this$0.play();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$12(RoutePlayer this$0) {
            if (this$0.current > 0) {
                this$0.current--;
                this$0.play();
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$15(GxRecordingsPage this$0, final RoutePlayer this$1) {
            Iterable $this$map$iv = CollectionsKt.getIndices(this$1.segments);
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                destination$iv$iv.add("Segment " + (item$iv$iv + 1) + " of " + this$1.segments.size());
            }
            this$0.pick("Video segment", (List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda12
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return GxRecordingsPage.RoutePlayer.lambda$15$lambda$14(this$1, ((Integer) obj).intValue());
                }
            });
            return Unit.INSTANCE;
        }

        static final Unit lambda$15$lambda$14(RoutePlayer this$0, int i) {
            if (i != this$0.current) {
                this$0.current = i;
                this$0.play();
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$16(RoutePlayer this$0) {
            if (this$0.current < this$0.segments.size() - 1) {
                this$0.current++;
                this$0.play();
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$19(GxRecordingsPage this$0, RoutePlayer this$1) {
            String gxRecordingsSafeName;
            String str = "/video/" + this$0.enc(this$1.r.getName()) + "/combined?camera=" + this$0.enc(this$1.cam);
            gxRecordingsSafeName = GxRecordingsKt.gxRecordingsSafeName(this$1.r.getDisplayName() + "-" + this$1.cam);
            this$0.download(str, gxRecordingsSafeName + ".mp4", "video/mp4");
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$20(GxRecordingsPage this$0, RoutePlayer this$1) {
            this$0.openLogs(this$1.r);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$21(RoutePlayer this$0) {
            this$0.toggleMap();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$23(GxRecordingsPage this$0, final RoutePlayer this$1) {
            this$0.togglePreserved(this$1.r, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda17
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return GxRecordingsPage.RoutePlayer.lambda$23$lambda$22(this$1);
                }
            });
            return Unit.INSTANCE;
        }

        static final Unit lambda$23$lambda$22(RoutePlayer this$0) {
            TextView textView = this$0.preserveBtn;
            if (textView != null) {
                textView.setText(this$0.r.getIsPreserved() ? "Unpreserve" : "Preserve");
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$25(GxRecordingsPage this$0, final RoutePlayer this$1) {
            this$0.renameRoute(this$1.r, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return GxRecordingsPage.RoutePlayer.lambda$25$lambda$24(this$1);
                }
            });
            return Unit.INSTANCE;
        }

        static final Unit lambda$25$lambda$24(RoutePlayer this$0) {
            this$0.sheet.getTitle().setText(this$0.r.getDisplayName());
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$27(GxRecordingsPage this$0, final RoutePlayer this$1) {
            this$0.deleteRoute(this$1.r, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return GxRecordingsPage.RoutePlayer.lambda$27$lambda$26(this$1);
                }
            });
            return Unit.INSTANCE;
        }

        static final Unit lambda$27$lambda$26(RoutePlayer this$0) {
            this$0.sheet.getDialog().dismiss();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$31(RoutePlayer this$0, GxRecordingsPage this$1, DialogInterface it) {
            this$0.token++;
            this$1.getMain().removeCallbacks(this$0.tickPos);
            try {
                Result.Companion companion = Result.Companion;
                this$0.mc.hide();
                this$0.vv.stopPlayback();
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
        }

        public final LinearLayout.LayoutParams gap() {
            LinearLayout.LayoutParams $this$gap_u24lambda_u2432 = new LinearLayout.LayoutParams(-2, -2);
            $this$gap_u24lambda_u2432.rightMargin = this.this$0.getUi().dp(10);
            return $this$gap_u24lambda_u2432;
        }

        public final void setStatus(String s) {
            Intrinsics.checkNotNullParameter(s, "s");
            this.status.setText(s);
            this.status.setVisibility(0);
        }

        public final int segNum(int i) {
            List<String> groupValues;
            String str;
            String it = (String) CollectionsKt.getOrNull(this.segments, i);
            if (it != null) {
                Integer num = null;
                MatchResult matchResult = (MatchResult) SequencesKt.lastOrNull((new Regex("--(\\d+)(?=[/?]|$)")).findAll(it, 0));
                if (matchResult != null && (groupValues = matchResult.getGroupValues()) != null && (str = groupValues.get(1)) != null) {
                    num = StringsKt.toIntOrNull(str);
                }
                if (num != null) {
                    return num.intValue();
                }
            }
            return i;
        }

        public final void toggleMap() {
            Long startedAtMs;
            TrackMapView it = this.mapView;
            if (it == null) {
                List pts = this.this$0.routeTrack(this.r);
                if (pts == null || (startedAtMs = this.r.getStartedAtMs()) == null) {
                    return;
                }
                long st = startedAtMs.longValue();
                TrackMapView m = new TrackMapView(this.this$0.getAct(), pts, st, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda16
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit unit;
                        unit = GxRecordingsPage.RoutePlayer.toggleMap$lambda$35(GxRecordingsPage.RoutePlayer.this, ((Long) obj).longValue());
                        return unit;
                    }
                });
                this.mapView = m;
                LinearLayout.LayoutParams $this$toggleMap_u24lambda_u2436 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                $this$toggleMap_u24lambda_u2436.leftMargin = this.this$0.getUi().dp(8);
                Unit unit = Unit.INSTANCE;
                this.mid.addView(m, $this$toggleMap_u24lambda_u2436);
                this.this$0.getMain().removeCallbacks(this.tickPos);
                this.this$0.getMain().post(this.tickPos);
                return;
            }
            this.mid.removeView(it);
            this.mapView = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit toggleMap$lambda$35(RoutePlayer this$0, long t) {
            this$0.jump(t);
            return Unit.INSTANCE;
        }

        public final void jump(long tMs) {
            Object minElem$iv;
            Object element$iv;
            int idx;
            Long startedAtMs = this.r.getStartedAtMs();
            if (startedAtMs != null) {
                long st = startedAtMs.longValue();
                int n = (int) ((tMs - st) / 60000);
                Iterable $this$firstOrNull$iv = CollectionsKt.getIndices(this.segments);
                Iterator<Integer> it = $this$firstOrNull$iv.iterator();
                while (true) {
                    minElem$iv = null;
                    if (!it.hasNext()) {
                        element$iv = null;
                        break;
                    }
                    element$iv = it.next();
                    int it2 = ((Number) element$iv).intValue();
                    if (segNum(it2) == n) {
                        break;
                    }
                }
                Integer num = (Integer) element$iv;
                if (num != null) {
                    idx = num.intValue();
                } else {
                    Iterable $this$minByOrNull$iv = CollectionsKt.getIndices(this.segments);
                    Iterator iterator$iv = $this$minByOrNull$iv.iterator();
                    if (iterator$iv.hasNext()) {
                        minElem$iv = iterator$iv.next();
                        if (iterator$iv.hasNext()) {
                            int it3 = ((Number) minElem$iv).intValue();
                            int minValue$iv = Math.abs(segNum(it3) - n);
                            do {
                                Object e$iv = iterator$iv.next();
                                int it4 = ((Number) e$iv).intValue();
                                int v$iv = Math.abs(segNum(it4) - n);
                                if (minValue$iv > v$iv) {
                                    minElem$iv = e$iv;
                                    minValue$iv = v$iv;
                                }
                            } while (iterator$iv.hasNext());
                        }
                    }
                    Integer num2 = (Integer) minElem$iv;
                    if (num2 == null) {
                        return;
                    } else {
                        idx = num2.intValue();
                    }
                }
                this.pendingSeekMs = segNum(idx) == n ? (int) ((tMs - st) % 60000) : 0;
                this.current = idx;
                play();
            }
        }

        public final void fetch() {
            setStatus("Loading video...");
            GxRecordingsPage gxRecordingsPage = this.this$0;
            String str = "/api/routes/" + this.this$0.enc(this.r.getName());
            final GxRecordingsPage gxRecordingsPage2 = this.this$0;
            GxPage.call$default(gxRecordingsPage, "GET", str, null, 0L, new Function3() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Unit fetch$lambda$48;
                    fetch$lambda$48 = GxRecordingsPage.RoutePlayer.fetch$lambda$48(GxRecordingsPage.RoutePlayer.this, gxRecordingsPage2, ((Integer) obj).intValue(), (String) obj2, (String) obj3);
                    return fetch$lambda$48;
                }
            }, 12, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit fetch$lambda$48(final RoutePlayer this$0, GxRecordingsPage this$1, int code, String text, String err) {
            Object m1191constructorimpl;
            List<String> emptyList;
            List avail;
            JSONObject o;
            String str;
            Iterable strings;
            String str2;
            Intrinsics.checkNotNullParameter(text, "text");
            if (!this$0.sheet.getDialog().isShowing()) {
                return Unit.INSTANCE;
            }
            if (err != null) {
                if (code == 404) {
                    str2 = "This route is no longer available on this device. Its local video segments may have been deleted or moved.";
                } else {
                    String str3 = err;
                    if (StringsKt.isBlank(str3)) {
                        str3 = "Could not load route details (" + code + ").";
                    }
                    str2 = str3;
                }
                this$0.setStatus(str2);
                return Unit.INSTANCE;
            }
            try {
                Result.Companion companion = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(new JSONObject(text));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            if (KtCompat.isFailure(m1191constructorimpl)) {
                m1191constructorimpl = null;
            }
            JSONObject o2 = (JSONObject) m1191constructorimpl;
            if (o2 == null) {
                this$0.setStatus("Could not load route.");
                return Unit.INSTANCE;
            }
            JSONArray optJSONArray = o2.optJSONArray("segment_urls");
            if (optJSONArray == null || (strings = this$1.strings(optJSONArray)) == null) {
                emptyList = CollectionsKt.emptyList();
            } else {
                Iterable $this$filter$iv = strings;
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    if (!StringsKt.isBlank((String) element$iv$iv)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                emptyList = (List) destination$iv$iv;
            }
            this$0.segments = emptyList;
            JSONArray optJSONArray2 = o2.optJSONArray("available_cameras");
            if (optJSONArray2 == null || (avail = this$1.strings(optJSONArray2)) == null) {
                avail = CollectionsKt.emptyList();
            }
            int i = 0;
            Iterable $this$filter$iv2 = CollectionsKt.listOf(new String[]{"forward", "wide", "driver"});
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv2 : $this$filter$iv2) {
                if (avail.contains((String) element$iv$iv2)) {
                    destination$iv$iv2.add(element$iv$iv2);
                }
            }
            this$0.cameras = (List) destination$iv$iv2;
            if (this$0.segments.isEmpty()) {
                this$0.setStatus("No video segments for this route.");
                return Unit.INSTANCE;
            }
            if (this$0.cameras.isEmpty()) {
                this$0.setStatus("No camera video for this route.");
                return Unit.INSTANCE;
            }
            this$0.cam = this$0.cameras.contains("forward") ? "forward" : this$0.cameras.get(0);
            this$0.current = 0;
            Iterable $this$map$iv = this$0.cameras;
            Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                String it = (String) item$iv$iv;
                if ((it.length() > 0 ? 1 : i) != 0) {
                    o = o2;
                    StringBuilder sb = new StringBuilder();
                    char c = it.charAt(i);
                    String valueOf = String.valueOf(c);
                    Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                    String upperCase = valueOf.toUpperCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                    StringBuilder append = sb.append((Object) upperCase);
                    String substring = it.substring(1);
                    Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                    str = append.append(substring).toString();
                } else {
                    o = o2;
                    str = it;
                }
                destination$iv$iv3.add(str);
                o2 = o;
                i = 0;
            }
            Pair chipBar = this$1.chipBar((List) destination$iv$iv3, new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit fetch$lambda$48$lambda$46;
                    fetch$lambda$48$lambda$46 = GxRecordingsPage.RoutePlayer.fetch$lambda$48$lambda$46(this$0, ((Integer) obj).intValue(), (Function1) obj2);
                    return fetch$lambda$48$lambda$46;
                }
            });
            LinearLayout cb = (LinearLayout) chipBar.component1();
            Function1 paintCam = (Function1) chipBar.component2();
            this$0.camBox.removeAllViews();
            this$0.camBox.addView(cb);
            paintCam.invoke(Integer.valueOf(this$0.cameras.indexOf(this$0.cam)));
            Pair chipBar2 = this$1.chipBar(CollectionsKt.listOf(new String[]{"Full quality", "Preview"}), new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit fetch$lambda$48$lambda$47;
                    fetch$lambda$48$lambda$47 = GxRecordingsPage.RoutePlayer.fetch$lambda$48$lambda$47(this$0, ((Integer) obj).intValue(), (Function1) obj2);
                    return fetch$lambda$48$lambda$47;
                }
            });
            LinearLayout qb = (LinearLayout) chipBar2.component1();
            Function1 pq = (Function1) chipBar2.component2();
            this$0.paintQual = pq;
            this$0.qualBox.removeAllViews();
            this$0.qualBox.addView(qb);
            pq.invoke(0);
            this$0.qualBox.setVisibility(Intrinsics.areEqual(this$0.cam, "forward") ? 0 : 8);
            this$0.controls.setVisibility(0);
            Long j = this$0.jumpTo;
            if (j != null) {
                this$0.jumpTo = null;
                this$0.jump(j.longValue());
                this$0.toggleMap();
            } else {
                this$0.play();
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit fetch$lambda$48$lambda$46(RoutePlayer this$0, int i, Function1 p) {
            Intrinsics.checkNotNullParameter(p, "p");
            this$0.cam = this$0.cameras.get(i);
            p.invoke(Integer.valueOf(i));
            this$0.qualBox.setVisibility(Intrinsics.areEqual(this$0.cam, "forward") ? 0 : 8);
            this$0.play();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit fetch$lambda$48$lambda$47(RoutePlayer this$0, int i, Function1 p) {
            Intrinsics.checkNotNullParameter(p, "p");
            this$0.preview = i == 1;
            p.invoke(Integer.valueOf(i));
            this$0.play();
            return Unit.INSTANCE;
        }

        public final String videoPath(String seg) {
            Intrinsics.checkNotNullParameter(seg, "seg");
            ArrayList parts = new ArrayList();
            if (!Intrinsics.areEqual(this.cam, "forward")) {
                parts.add("camera=" + this.this$0.enc(this.cam));
            }
            if (this.preview && Intrinsics.areEqual(this.cam, "forward")) {
                parts.add("quality=low");
            }
            if (parts.isEmpty()) {
                return seg;
            }
            return seg + (StringsKt.contains((CharSequence) seg, (CharSequence) "?", false) ? "&" : "?") + CollectionsKt.joinToString(parts, "&", "", "", -1, "...", null);
        }

        public final void play() {
            String seg = (String) CollectionsKt.getOrNull(this.segments, this.current);
            if (seg == null) {
                return;
            }
            this.token++;
            int t = this.token;
            this.segLabel.setText("Segment " + (this.current + 1) + " of " + this.segments.size());
            this.prev.setAlpha(this.current > 0 ? 1.0f : 0.4f);
            this.next.setAlpha(this.current >= this.segments.size() + (-1) ? 0.4f : 1.0f);
            try {
                Result.Companion companion = Result.Companion;
                RoutePlayer $this$play_u24lambda_u2449 = this;
                $this$play_u24lambda_u2449.vv.stopPlayback();
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
            setStatus("Loading video...");
            probe(this.this$0.url(videoPath(seg)), t, 0);
        }

        public final void probe(final String u, final int t, final int attempt) {
            Intrinsics.checkNotNullParameter(u, "u");
            GxRecordingsPage gxRecordingsPage = this.this$0;
            final GxRecordingsPage gxRecordingsPage2 = this.this$0;
            gxRecordingsPage.head(u, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda10
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit probe$lambda$51;
                    probe$lambda$51 = GxRecordingsPage.RoutePlayer.probe$lambda$51(t, GxRecordingsPage.RoutePlayer.this, u, attempt, gxRecordingsPage2, ((Integer) obj).intValue());
                    return probe$lambda$51;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit probe$lambda$51(final int $t, final RoutePlayer this$0, final String $u, final int $attempt, GxRecordingsPage this$1, int code) {
            if ($t != this$0.token || !this$0.sheet.getDialog().isShowing()) {
                return Unit.INSTANCE;
            }
            if ((200 <= code && code < 300) || code == -1 || code == 405) {
                this$0.vv.setVideoURI(Uri.parse($u));
            } else if (code == 503 && $attempt < 40) {
                this$0.setStatus("Preparing video...");
                this$1.getMain().postDelayed(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        GxRecordingsPage.RoutePlayer.probe$lambda$51$lambda$50($t, this$0, $u, $attempt);
                    }
                }, 1500L);
            } else if (this$0.preview) {
                this$0.preview = false;
                this$0.paintQual.invoke(0);
                this$0.play();
            } else {
                this$0.setStatus("This segment could not be played.");
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void probe$lambda$51$lambda$50(int $t, RoutePlayer this$0, String $u, int $attempt) {
            if ($t == this$0.token && this$0.sheet.getDialog().isShowing()) {
                this$0.probe($u, $t, $attempt + 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxRecordings.kt */
    static final class ScreenPlayer {
        private final FrameLayout frame;
        private final MediaController mc;
        private GxRecordingsScreen rec;
        private final Sheet sheet;
        private final TextView status;
        final /* synthetic */ GxRecordingsPage this$0;
        private final VideoView vv;

        public ScreenPlayer(GxRecordingsPage this$0, GxRecordingsScreen rec) {
            int i;
            Intrinsics.checkNotNullParameter(rec, "rec");
            this.this$0 = this$0;
            this.rec = rec;
            this.sheet = new Sheet(this.this$0, this.this$0.screenName(this.rec), false);
            FrameLayout $this$frame_u24lambda_u240 = new FrameLayout(this.this$0.getAct());
            $this$frame_u24lambda_u240.setBackgroundColor(-16777216);
            this.frame = $this$frame_u24lambda_u240;
            this.vv = new VideoView(this.this$0.getAct());
            this.mc = new MediaController(this.this$0.getAct());
            TextView $this$status_u24lambda_u241 = new TextView(this.this$0.getAct());
            GxRecordingsPage gxRecordingsPage = this.this$0;
            $this$status_u24lambda_u241.setTextColor(CarUi.Companion.getTEXT());
            $this$status_u24lambda_u241.setTextSize(19.0f);
            $this$status_u24lambda_u241.setGravity(17);
            $this$status_u24lambda_u241.setPadding(gxRecordingsPage.getUi().dp(20), gxRecordingsPage.getUi().dp(12), gxRecordingsPage.getUi().dp(20), gxRecordingsPage.getUi().dp(12));
            $this$status_u24lambda_u241.setBackground(CarUi.rounded$default(gxRecordingsPage.getUi(), Color.argb(190, 0, 0, 0), 12, null, 4, null));
            $this$status_u24lambda_u241.setText("Loading video...");
            this.status = $this$status_u24lambda_u241;
            this.frame.addView(this.vv, new FrameLayout.LayoutParams(-1, -1, 17));
            this.frame.addView(this.status, new FrameLayout.LayoutParams(-2, -2, 17));
            LinearLayout body = this.sheet.getBody();
            FrameLayout frameLayout = this.frame;
            LinearLayout.LayoutParams $this$_init__u24lambda_u242 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
            $this$_init__u24lambda_u242.bottomMargin = this.this$0.getUi().dp(10);
            Unit unit = Unit.INSTANCE;
            body.addView(frameLayout, $this$_init__u24lambda_u242);
            this.mc.setAnchorView(this.frame);
            this.vv.setMediaController(this.mc);
            this.vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda1
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    GxRecordingsPage.ScreenPlayer._init_$lambda$3(GxRecordingsPage.ScreenPlayer.this, mediaPlayer);
                }
            });
            this.vv.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda2
                @Override // android.media.MediaPlayer.OnErrorListener
                public final boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
                    boolean _init_$lambda$4;
                    _init_$lambda$4 = GxRecordingsPage.ScreenPlayer._init_$lambda$4(GxRecordingsPage.ScreenPlayer.this, mediaPlayer, i2, i3);
                    return _init_$lambda$4;
                }
            });
            LinearLayout acts = new LinearLayout(this.this$0.getAct());
            acts.setOrientation(0);
            final GxRecordingsPage gxRecordingsPage2 = this.this$0;
            Function0 lp = new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    LinearLayout.LayoutParams _init_$lambda$7;
                    _init_$lambda$7 = GxRecordingsPage.ScreenPlayer._init_$lambda$7(GxRecordingsPage.ScreenPlayer.this.this$0);
                    return _init_$lambda$7;
                }
            };
            CarUi ui = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage3 = this.this$0;
            acts.addView(ui.button("Download", true, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$8;
                    _init_$lambda$8 = GxRecordingsPage.ScreenPlayer._init_$lambda$8(GxRecordingsPage.ScreenPlayer.this.this$0, GxRecordingsPage.ScreenPlayer.this);
                    return _init_$lambda$8;
                }
            }), (ViewGroup.LayoutParams) lp.invoke());
            CarUi ui2 = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage4 = this.this$0;
            acts.addView(CarUi.button$default(ui2, "Rename", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$10;
                    _init_$lambda$10 = GxRecordingsPage.ScreenPlayer._init_$lambda$10(GxRecordingsPage.ScreenPlayer.this.this$0, GxRecordingsPage.ScreenPlayer.this);
                    return _init_$lambda$10;
                }
            }, 2, null), (ViewGroup.LayoutParams) lp.invoke());
            CarUi ui3 = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage5 = this.this$0;
            TextView $this$_init__u24lambda_u2413 = CarUi.button$default(ui3, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$12;
                    _init_$lambda$12 = GxRecordingsPage.ScreenPlayer._init_$lambda$12(GxRecordingsPage.ScreenPlayer.this.this$0, GxRecordingsPage.ScreenPlayer.this);
                    return _init_$lambda$12;
                }
            }, 2, null);
            i = GxRecordingsKt.GxRecordingsDanger;
            $this$_init__u24lambda_u2413.setTextColor(i);
            acts.addView($this$_init__u24lambda_u2413, (ViewGroup.LayoutParams) lp.invoke());
            LinearLayout body2 = this.sheet.getBody();
            HorizontalScrollView $this$_init__u24lambda_u2414 = new HorizontalScrollView(this.this$0.getAct());
            $this$_init__u24lambda_u2414.setHorizontalScrollBarEnabled(false);
            $this$_init__u24lambda_u2414.addView(acts);
            body2.addView($this$_init__u24lambda_u2414);
            this.sheet.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    GxRecordingsPage.ScreenPlayer._init_$lambda$16(GxRecordingsPage.ScreenPlayer.this, dialogInterface);
                }
            });
            this.sheet.getDialog().show();
            this.vv.setVideoURI(Uri.parse(this.this$0.url(this.this$0.screenVideoPath(this.rec))));
        }

        public final GxRecordingsScreen getRec() {
            return this.rec;
        }

        public final void setRec(GxRecordingsScreen gxRecordingsScreen) {
            Intrinsics.checkNotNullParameter(gxRecordingsScreen, "<set-?>");
            this.rec = gxRecordingsScreen;
        }

        public final Sheet getSheet() {
            return this.sheet;
        }

        public final FrameLayout getFrame() {
            return this.frame;
        }

        public final VideoView getVv() {
            return this.vv;
        }

        public final MediaController getMc() {
            return this.mc;
        }

        public final TextView getStatus() {
            return this.status;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$3(ScreenPlayer this$0, MediaPlayer it) {
            this$0.status.setVisibility(8);
            this$0.vv.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean _init_$lambda$4(ScreenPlayer this$0, MediaPlayer mediaPlayer, int i, int i2) {
            this$0.status.setText("This recording could not be played.");
            this$0.status.setVisibility(0);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final LinearLayout.LayoutParams _init_$lambda$7(GxRecordingsPage this$0) {
            LinearLayout.LayoutParams $this$lambda_u247_u24lambda_u246 = new LinearLayout.LayoutParams(-2, -2);
            $this$lambda_u247_u24lambda_u246.rightMargin = this$0.getUi().dp(10);
            return $this$lambda_u247_u24lambda_u246;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$8(GxRecordingsPage this$0, ScreenPlayer this$1) {
            this$0.downloadScreen(this$1.rec);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$10(GxRecordingsPage this$0, final ScreenPlayer this$1) {
            this$0.renameScreen(this$1.rec, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return GxRecordingsPage.ScreenPlayer.lambda$10$lambda$9(this$1);
                }
            });
            return Unit.INSTANCE;
        }

        static final Unit lambda$10$lambda$9(ScreenPlayer this$0) {
            this$0.sheet.getDialog().dismiss();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$12(GxRecordingsPage this$0, final ScreenPlayer this$1) {
            this$0.deleteScreen(this$1.rec, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return GxRecordingsPage.ScreenPlayer.lambda$12$lambda$11(this$1);
                }
            });
            return Unit.INSTANCE;
        }

        static final Unit lambda$12$lambda$11(ScreenPlayer this$0) {
            this$0.sheet.getDialog().dismiss();
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$16(ScreenPlayer this$0, DialogInterface it) {
            try {
                Result.Companion companion = Result.Companion;
                this$0.mc.hide();
                this$0.vv.stopPlayback();
                KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxRecordings.kt */
    static final class Sheet {
        private final LinearLayout body;
        private final Dialog dialog;
        final /* synthetic */ GxRecordingsPage this$0;
        private final TextView title;

        public Sheet(GxRecordingsPage this$0, String titleText, boolean scrolling) {
            Intrinsics.checkNotNullParameter(titleText, "titleText");
            this.this$0 = this$0;
            this.dialog = new Dialog(this.this$0.getAct(), 16973834);
            LinearLayout $this$body_u24lambda_u240 = new LinearLayout(this.this$0.getAct());
            $this$body_u24lambda_u240.setOrientation(1);
            this.body = $this$body_u24lambda_u240;
            LinearLayout root = new LinearLayout(this.this$0.getAct());
            GxRecordingsPage gxRecordingsPage = this.this$0;
            root.setOrientation(1);
            root.setBackgroundColor(CarUi.Companion.getBG());
            root.setPadding(gxRecordingsPage.getUi().dp(20), gxRecordingsPage.getUi().dp(14), gxRecordingsPage.getUi().dp(20), gxRecordingsPage.getUi().dp(16));
            LinearLayout bar = new LinearLayout(this.this$0.getAct());
            GxRecordingsPage gxRecordingsPage2 = this.this$0;
            bar.setOrientation(0);
            bar.setGravity(16);
            bar.setPadding(0, 0, 0, gxRecordingsPage2.getUi().dp(12));
            TextView $this$_init__u24lambda_u243 = new TextView(this.this$0.getAct());
            $this$_init__u24lambda_u243.setText(titleText);
            $this$_init__u24lambda_u243.setTextColor(CarUi.Companion.getTEXT());
            $this$_init__u24lambda_u243.setTextSize(24.0f);
            $this$_init__u24lambda_u243.setTypeface(Typeface.DEFAULT_BOLD);
            $this$_init__u24lambda_u243.setMaxLines(2);
            this.title = $this$_init__u24lambda_u243;
            bar.addView(this.title, new LinearLayout.LayoutParams(0, -2, 1.0f));
            bar.addView(CarUi.button$default(this.this$0.getUi(), "Close", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$Sheet$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$4;
                    _init_$lambda$4 = GxRecordingsPage.Sheet._init_$lambda$4(GxRecordingsPage.Sheet.this);
                    return _init_$lambda$4;
                }
            }, 2, null));
            root.addView(bar);
            if (scrolling) {
                NestedScrollView $this$_init__u24lambda_u245 = new NestedScrollView(this.this$0.getAct());
                $this$_init__u24lambda_u245.addView(this.body, new ViewGroup.LayoutParams(-1, -2));
                root.addView($this$_init__u24lambda_u245, new LinearLayout.LayoutParams(-1, 0, 1.0f));
            } else {
                root.addView(this.body, new LinearLayout.LayoutParams(-1, 0, 1.0f));
            }
            this.dialog.setContentView(root);
        }

        public final Dialog getDialog() {
            return this.dialog;
        }

        public final TextView getTitle() {
            return this.title;
        }

        public final LinearLayout getBody() {
            return this.body;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _init_$lambda$4(Sheet this$0) {
            this$0.dialog.dismiss();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<LinearLayout, Function1<Integer, Unit>> chipBar(List<String> labels, final Function2<? super Integer, ? super Function1<? super Integer, Unit>, Unit> onPick) {
        LinearLayout bar = new LinearLayout(getAct());
        int i = 0;
        bar.setOrientation(0);
        final ArrayList views = new ArrayList();
        final Function1 paint = new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit chipBar$lambda$111;
                chipBar$lambda$111 = GxRecordingsPage.chipBar$lambda$111(views, GxRecordingsPage.this, ((Integer) obj).intValue());
                return chipBar$lambda$111;
            }
        };
        for (String l : labels) {
            final int i2 = i;
            i++;
            TextView c = new TextView(getAct());
            c.setText(l);
            c.setTextSize(17.0f);
            c.setGravity(17);
            c.setMinHeight(getUi().dp(56));
            c.setMinWidth(getUi().dp(96));
            c.setPadding(getUi().dp(16), getUi().dp(8), getUi().dp(16), getUi().dp(8));
            c.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda32
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GxRecordingsPage.chipBar$lambda$113$lambda$112(onPick, i2, paint, view);
                }
            });
            views.add(c);
            LinearLayout.LayoutParams $this$chipBar_u24lambda_u24114 = new LinearLayout.LayoutParams(-2, -2);
            $this$chipBar_u24lambda_u24114.rightMargin = getUi().dp(8);
            Unit unit = Unit.INSTANCE;
            bar.addView(c, $this$chipBar_u24lambda_u24114);
        }
        return TuplesKt.to(bar, paint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit chipBar$lambda$111(ArrayList $views, GxRecordingsPage this$0, int sel) {
        Iterator it = $views.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i;
            i++;
            TextView v = (TextView) it.next();
            boolean on = i2 == sel;
            CarUi ui = this$0.getUi();
            CarUi.Companion_ companion = CarUi.Companion;
            v.setBackground(ui.rounded(on ? companion.getCARD_ON() : companion.getCARD(), 12, Integer.valueOf(on ? CarUi.Companion.getACCENT() : Color.rgb(50, 55, 66))));
            CarUi.Companion_ companion2 = CarUi.Companion;
            v.setTextColor(on ? companion2.getACCENT() : companion2.getTEXT());
            v.setTypeface(on ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void chipBar$lambda$113$lambda$112(Function2 $onPick, int $i, Function1 $paint, View it) {
        $onPick.invoke(Integer.valueOf($i), $paint);
    }

    private final void thumbInto(GxPage.Row row, String path, int wDp, int hDp, Function0<Boolean> valid) {
        FrameLayout fr = new FrameLayout(getAct());
        fr.setBackground(CarUi.rounded$default(getUi(), Color.rgb(6, 8, 11), 10, null, 4, null));
        fr.setClipToOutline(true);
        TextView ph = new TextView(getAct());
        ph.setText("Preview unavailable");
        ph.setTextColor(CarUi.Companion.getDIM());
        ph.setTextSize(12.0f);
        ph.setGravity(17);
        ImageView iv = new ImageView(getAct());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        fr.addView(ph, new FrameLayout.LayoutParams(-1, -1));
        fr.addView(iv, new FrameLayout.LayoutParams(-1, -1));
        LinearLayout.LayoutParams $this$thumbInto_u24lambda_u24118 = new LinearLayout.LayoutParams(getUi().dp(wDp), getUi().dp(hDp));
        $this$thumbInto_u24lambda_u24118.rightMargin = getUi().dp(16);
        Unit unit = Unit.INSTANCE;
        row.getView().addView(fr, 0, $this$thumbInto_u24lambda_u24118);
        if (StringsKt.isBlank(path)) {
            return;
        }
        Bitmap cached = this.thumbCache.get(path);
        if (cached != null) {
            iv.setImageBitmap(cached);
            ph.setVisibility(8);
        } else {
            if (this.thumbFailed.contains(path)) {
                return;
            }
            this.thumbQueue.addLast(new ThumbJob(path, iv, ph, valid));
            pumpThumbs();
        }
    }

    private final void pumpThumbs() {
        while (this.thumbActive < 2 && !this.thumbQueue.isEmpty()) {
            final ThumbJob j = this.thumbQueue.removeFirst();
            if (j.getValid().invoke().booleanValue() && getHost() != null) {
                Bitmap cached = this.thumbCache.get(j.getPath());
                if (cached != null) {
                    j.getIv().setImageBitmap(cached);
                    j.getPh().setVisibility(8);
                } else {
                    this.thumbActive++;
                    fetchThumb(j.getPath(), new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda30
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            Unit pumpThumbs$lambda$119;
                            pumpThumbs$lambda$119 = GxRecordingsPage.pumpThumbs$lambda$119(GxRecordingsPage.this, j, (Bitmap) obj);
                            return pumpThumbs$lambda$119;
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit pumpThumbs$lambda$119(GxRecordingsPage this$0, ThumbJob $j, Bitmap bmp) {
        this$0.thumbActive--;
        if (bmp != null) {
            this$0.thumbCache.put($j.getPath(), bmp);
            $j.getIv().setImageBitmap(bmp);
            $j.getPh().setVisibility(8);
        } else {
            this$0.thumbFailed.add($j.getPath());
        }
        this$0.pumpThumbs();
        return Unit.INSTANCE;
    }

    private final void fetchThumb(String path, final Function1<? super Bitmap, Unit> then) {
        final String u = url(path);
        new Thread(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                GxRecordingsPage.fetchThumb$lambda$126(GxRecordingsPage.this, u, then);
            }
        }).start();
    }

    /* recovered by hand from the bytecode (jadx lost its try blocks) */
    public static final void fetchThumb$lambda$126(GxRecordingsPage this$0, String $u, final Function1 $then) {
        Object res;
        try {
            Result.Companion companion = Result.Companion;
            Response r = GxPage.Companion.client(30L).newCall(new Request.Builder().url($u).build()).execute();
            Throwable cause = null;
            Bitmap decoded;
            try {
                ResponseBody body;
                byte[] data;
                if (!r.isSuccessful() || (body = r.body()) == null || (data = body.bytes()) == null) {
                    decoded = null;
                } else {
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inJustDecodeBounds = true;
                    BitmapFactory.decodeByteArray(data, 0, data.length, o);
                    int s = 1;
                    while (o.outWidth / (s * 2) >= 400) {
                        s *= 2;
                    }
                    int length = data.length;
                    BitmapFactory.Options o2 = new BitmapFactory.Options();
                    o2.inSampleSize = s;
                    decoded = BitmapFactory.decodeByteArray(data, 0, length, o2);
                }
            } catch (Throwable th) {
                cause = th;
                throw th;
            } finally {
                CloseableKt.closeFinally(r, cause);
            }
            res = KtCompat.resultOf(decoded);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            res = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        final Bitmap bmp = (Bitmap) (KtCompat.isFailure(res) ? null : res);
        this$0.getMain().post(() -> $then.invoke(bmp));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void head(final String u, final Function1<? super Integer, Unit> then) {
        new Thread(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                GxRecordingsPage.head$lambda$130(GxRecordingsPage.this, u, then);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void head$lambda$130(GxRecordingsPage this$0, String $u, final Function1 $then) {
        Object m1191constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            Response execute = GxPage.Companion.client(30L).newCall(new Request.Builder().url($u).head().build()).execute();
            Throwable execute$c = null;
            try {
                Response it = execute;
                int code = it.code();
                m1191constructorimpl = KtCompat.resultOf(Integer.valueOf(code));
            } catch (Throwable execute$t) {
                execute$c = execute$t;
                throw execute$t;
            } finally {
                CloseableKt.closeFinally(execute, execute$c);
            }
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = -1;
        }
        final int code2 = ((Number) m1191constructorimpl).intValue();
        this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                GxRecordingsPage.head$lambda$130$lambda$129($then, code2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void head$lambda$130$lambda$129(Function1 $then, int $code) {
        $then.invoke(Integer.valueOf($code));
    }

    private final Call stream(String path, final Function1<? super JSONObject, Unit> onData, final Function1<? super String, Unit> onDone) {
        if (getHost() == null) {
            onDone.invoke("Not connected");
            return null;
        }
        final Call c = GxPage.Companion.client(120L).newCall(new Request.Builder().url(url(path)).header("Accept", "text/event-stream").build());
        new Thread(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GxRecordingsPage.stream$lambda$135(c, GxRecordingsPage.this, onData, onDone);
            }
        }).start();
        return c;
    }

    /* recovered by hand (jadx lost its try blocks) */
    public static final void stream$lambda$135(final Call call, GxRecordingsPage gxRecordingsPage, Function1 function1, final Function1 function12) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        try {
            Response response = call.execute();
            Throwable cause = null;
            try {
                if (!response.isSuccessful()) {
                    throw new IllegalStateException("HTTP " + response.code());
                }
                BufferedSource source;
                ResponseBody body = response.body();
                if (body == null || (source = body.source()) == null) {
                    throw new IllegalStateException("empty reply");
                }
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = source.readUtf8Line();
                    if (line == null) {
                        break;
                    }
                    if (line.length() == 0) {
                        stream$lambda$135$lambda$133$flush(sb, gxRecordingsPage, call, function1);
                    } else if (StringsKt.startsWith(line, "data:", false)) {
                        if (sb.length() > 0) {
                            sb.append('\n');
                        }
                        String substring = line.substring(5);
                        Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                        sb.append(StringsKt.trimStart((CharSequence) substring).toString());
                    }
                }
                stream$lambda$135$lambda$133$flush(sb, gxRecordingsPage, call, function1);
            } catch (Throwable th) {
                cause = th;
                throw th;
            } finally {
                CloseableKt.closeFinally(response, cause);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            objectRef.element = message == null ? e.toString() : message;
        }
        gxRecordingsPage.getMain().post(() -> GxRecordingsPage.stream$lambda$135$lambda$134(call, function12, objectRef));
    }

    private static final void stream$lambda$135$lambda$133$flush(StringBuilder sb, GxRecordingsPage this$0, final Call c, final Function1<? super JSONObject, Unit> function1) {
        Object m1191constructorimpl;
        if (sb.length() == 0) {
            return;
        }
        try {
            Result.Companion companion = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(new JSONObject(sb.toString()));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        final JSONObject o = (JSONObject) m1191constructorimpl;
        sb.setLength(0);
        if (o != null) {
            this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda36
                @Override // java.lang.Runnable
                public final void run() {
                    GxRecordingsPage.stream$lambda$135$lambda$133$flush$lambda$132(c, function1, o);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stream$lambda$135$lambda$133$flush$lambda$132(Call $c, Function1 $onData, JSONObject $o) {
        if ($c.isCanceled()) {
            return;
        }
        $onData.invoke($o);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stream$lambda$135$lambda$134(Call $c, Function1 $onDone, Ref.ObjectRef $err) {
        if ($c.isCanceled()) {
            return;
        }
        $onDone.invoke($err.element);
    }
}
