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
    private final GxRecordingsPage$thumbCache$1 thumbCache;
    private final HashSet<String> thumbFailed;
    private final ArrayDeque<ThumbJob> thumbQueue;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.starpilot.starview.GxRecordingsPage$thumbCache$1] */
    public GxRecordingsPage(Activity act, GxHost gx) {
        super(act, gx);
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(gx, "gx");
        this.routes = new ArrayList<>();
        this.routesError = "";
        this.search = "";
        this.screens = new ArrayList<>();
        this.screenError = "";
        this.thumbCache = new LruCache<String, Bitmap>() { // from class: com.starpilot.starview.GxRecordingsPage$thumbCache$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.util.LruCache
            public int sizeOf(String key, Bitmap value) {
                Intrinsics.checkNotNullParameter(key, "key");
                Intrinsics.checkNotNullParameter(value, "value");
                return value.getByteCount();
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
        GxPage.tabs$default(this, CollectionsKt.listOf((Object[]) new String[]{"Dashcam Routes", "Screen Recordings"}), 0, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda34
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
        choice("Sort", null, CollectionsKt.listOf((Object[]) new String[]{"Newest first", "Oldest first", "Longest duration", "Shortest duration"}), this.sortOrder, b, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildRoutes$lambda$10;
                buildRoutes$lambda$10 = GxRecordingsPage.buildRoutes$lambda$10(GxRecordingsPage.this, ((Integer) obj).intValue());
                return buildRoutes$lambda$10;
            }
        });
        List<String> listOf = CollectionsKt.listOf((Object[]) new String[]{"All", "Preserved"});
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

    /* JADX WARN: Type inference failed for: r2v1, types: [T, okhttp3.Call] */
    private final void loadRoutes() {
        if (this.routesList == null || getHost() == null) {
            return;
        }
        Call call = this.routeCall;
        if (call != null) {
            call.cancel();
        }
        this.routes.clear();
        this.routesLoading = true;
        this.routesError = "";
        this.routesProgress = 0;
        this.routesTotal = 0;
        renderRoutes();
        final HashSet seen = new HashSet();
        final Ref.ObjectRef call2 = new Ref.ObjectRef();
        call2.element = stream("/api/routes", new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda51
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadRoutes$lambda$15;
                loadRoutes$lambda$15 = GxRecordingsPage.loadRoutes$lambda$15(GxRecordingsPage.this, seen, (JSONObject) obj);
                return loadRoutes$lambda$15;
            }
        }, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda52
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadRoutes$lambda$16;
                loadRoutes$lambda$16 = GxRecordingsPage.loadRoutes$lambda$16(GxRecordingsPage.this, call2, (String) obj);
                return loadRoutes$lambda$16;
            }
        });
        this.routeCall = (Call) call2.element;
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
            m1191constructorimpl = Result.m1191constructorimpl(Instant.parse(s).atZone(z));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1197isFailureimpl(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        ZonedDateTime zonedDateTime = (ZonedDateTime) m1191constructorimpl;
        if (zonedDateTime != null) {
            return zonedDateTime;
        }
        try {
            Result.Companion companion3 = Result.Companion;
            GxRecordingsPage gxRecordingsPage2 = this;
            m1191constructorimpl2 = Result.m1191constructorimpl(OffsetDateTime.parse(s).atZoneSameInstant(z));
        } catch (Throwable th2) {
            Result.Companion companion4 = Result.Companion;
            m1191constructorimpl2 = Result.m1191constructorimpl(ResultKt.createFailure(th2));
        }
        if (Result.m1197isFailureimpl(m1191constructorimpl2)) {
            m1191constructorimpl2 = null;
        }
        ZonedDateTime zonedDateTime2 = (ZonedDateTime) m1191constructorimpl2;
        if (zonedDateTime2 != null) {
            return zonedDateTime2;
        }
        try {
            Result.Companion companion5 = Result.Companion;
            GxRecordingsPage gxRecordingsPage3 = this;
            m1191constructorimpl3 = Result.m1191constructorimpl(LocalDateTime.parse(s).atZone(z));
        } catch (Throwable th3) {
            Result.Companion companion6 = Result.Companion;
            m1191constructorimpl3 = Result.m1191constructorimpl(ResultKt.createFailure(th3));
        }
        return (ZonedDateTime) (Result.m1197isFailureimpl(m1191constructorimpl3) ? null : m1191constructorimpl3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0046, code lost:
    
        if (r4 == null) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void normalize(GxRecordingsRoute r) {
        String str;
        String displayDate;
        String gxRecordingsNorm;
        String gxRecordingsNorm2;
        List<String> list;
        String gxRecordingsNorm3;
        List<String> list2;
        String it;
        Instant instant;
        ZonedDateTime tsDate = parseStarted(r.getTimestamp());
        ZonedDateTime date = parseStarted(r.getStartedAt());
        if (date == null) {
            date = tsDate;
        }
        if (date != null) {
            ZonedDateTime it2 = date;
            str = it2.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " at " + it2.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        }
        str = "Unknown date";
        r.setDisplayDate(str);
        r.setCustomName(r.getIsCustomName() || (r.getTimestamp() != null && tsDate == null));
        if (r.getIsCustomName()) {
            displayDate = r.getTimestamp();
            if (displayDate == null) {
                displayDate = "";
            }
        } else {
            displayDate = r.getDisplayDate();
        }
        r.setDisplayName(displayDate);
        r.setStartedAtMs((date == null || (instant = date.toInstant()) == null) ? null : Long.valueOf(instant.toEpochMilli()));
        gxRecordingsNorm = GxRecordingsKt.gxRecordingsNorm(r.getName());
        Iterable $this$filter$iv = CollectionsKt.listOf(gxRecordingsNorm);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it3 = (String) element$iv$iv;
            if (it3.length() > 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        r.setIds((List) destination$iv$iv);
        gxRecordingsNorm2 = GxRecordingsKt.gxRecordingsNorm(r.getIsCustomName() ? r.getDisplayName() : "");
        Iterable $this$filter$iv2 = CollectionsKt.listOf(gxRecordingsNorm2);
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            String it4 = (String) element$iv$iv2;
            if (it4.length() > 0) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        r.setTitles((List) destination$iv$iv2);
        if (date == null) {
            list = CollectionsKt.emptyList();
        } else {
            Iterable $this$map$iv = CollectionsKt.listOf((Object[]) new String[]{date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)), date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US)), date.format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US)), date.format(DateTimeFormatter.ofPattern("M/d/yyyy", Locale.US)), date.format(DateTimeFormatter.ofPattern("yyyy-M-d", Locale.US))});
            Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                String it5 = (String) item$iv$iv;
                gxRecordingsNorm3 = GxRecordingsKt.gxRecordingsNorm(it5);
                destination$iv$iv3.add(gxRecordingsNorm3);
            }
            Iterable $this$filter$iv3 = (List) destination$iv$iv3;
            Collection destination$iv$iv4 = new ArrayList();
            for (Object element$iv$iv3 : $this$filter$iv3) {
                String it6 = (String) element$iv$iv3;
                if (it6.length() > 0) {
                    destination$iv$iv4.add(element$iv$iv3);
                }
            }
            list = (List) destination$iv$iv4;
        }
        r.setDates(list);
        if (date == null) {
            list2 = CollectionsKt.emptyList();
        } else {
            Iterable $this$map$iv2 = CollectionsKt.listOf((Object[]) new String[]{date.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)), date.format(DateTimeFormatter.ofPattern("h:mm a", Locale.US)), date.format(DateTimeFormatter.ofPattern("HH:mm", Locale.US))});
            Collection destination$iv$iv5 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            for (Object item$iv$iv2 : $this$map$iv2) {
                String it7 = (String) item$iv$iv2;
                it = GxRecordingsKt.gxRecordingsNorm(it7);
                destination$iv$iv5.add(it);
            }
            Iterable $this$filter$iv4 = (List) destination$iv$iv5;
            Collection destination$iv$iv6 = new ArrayList();
            for (Object element$iv$iv4 : $this$filter$iv4) {
                String it8 = (String) element$iv$iv4;
                if (it8.length() > 0) {
                    destination$iv$iv6.add(element$iv$iv4);
                }
            }
            list2 = (List) destination$iv$iv6;
        }
        r.setTimes(list2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:108:0x02bf, code lost:
    
        if (r1 == false) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01ef, code lost:
    
        if (r1 != false) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x027d, code lost:
    
        if (r1 == false) goto L141;
     */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x023d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x00cc A[LOOP:8: B:170:0x007a->B:182:0x00cc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x00ca A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0240  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean matches(GxRecordingsRoute r, String query) {
        String nq;
        boolean z;
        boolean z2;
        Iterable iterable;
        boolean z3;
        boolean z4;
        boolean z5;
        Iterable $this$any$iv;
        Iterator it;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        String raw = StringsKt.trim((CharSequence) query).toString();
        nq = GxRecordingsKt.gxRecordingsNorm(query);
        Iterable $this$filter$iv = StringsKt.split$default((CharSequence) nq, new String[]{" "}, false, 0, 6, (Object) null);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it2 = (String) element$iv$iv;
            if (it2.length() > 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List tokens = (List) destination$iv$iv;
        if (tokens.isEmpty()) {
            return true;
        }
        List $this$any$iv2 = tokens;
        if (($this$any$iv2 instanceof Collection) && $this$any$iv2.isEmpty()) {
            z = false;
        } else {
            Iterator it3 = $this$any$iv2.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    z = false;
                    break;
                }
                Object element$iv = it3.next();
                String t = (String) element$iv;
                if (t.length() >= 3) {
                    iterable = GxRecordingsKt.GxRecordingsMonths;
                    Iterable $this$any$iv3 = iterable;
                    if (!($this$any$iv3 instanceof Collection) || !((Collection) $this$any$iv3).isEmpty()) {
                        Iterator it4 = $this$any$iv3.iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                z3 = false;
                                break;
                            }
                            Object element$iv2 = it4.next();
                            String it5 = (String) element$iv2;
                            if (StringsKt.startsWith$default(it5, t, false, 2, (Object) null)) {
                                z3 = true;
                                break;
                            }
                        }
                    } else {
                        z3 = false;
                    }
                    if (z3) {
                        z2 = true;
                        if (!z2) {
                            z = true;
                            break;
                        }
                    }
                }
                z2 = false;
                if (!z2) {
                }
            }
        }
        boolean hasMonth = z;
        boolean isDate = hasMonth || new Regex("\\d\\s*[/-]\\s*\\d").containsMatchIn(raw) || new Regex("\\d+(?:st|nd|rd|th)\\b", RegexOption.IGNORE_CASE).containsMatchIn(raw) || new Regex("^\\d{4}$").matches(nq);
        if (!new Regex("^\\d{1,2}$").matches(nq) && !new Regex("\\d\\s*:\\s*\\d").containsMatchIn(raw)) {
            List $this$any$iv4 = tokens;
            if (!($this$any$iv4 instanceof Collection) || !$this$any$iv4.isEmpty()) {
                Iterator it6 = $this$any$iv4.iterator();
                while (true) {
                    if (!it6.hasNext()) {
                        z12 = false;
                        break;
                    }
                    Object element$iv3 = it6.next();
                    String it7 = (String) element$iv3;
                    if (Intrinsics.areEqual(it7, "am") || Intrinsics.areEqual(it7, "pm")) {
                        z12 = true;
                        break;
                    }
                }
            } else {
                z12 = false;
            }
            if (!z12) {
                z4 = false;
                boolean isTime = z4;
                String compact = StringsKt.replace$default(nq, " ", "", false, 4, (Object) null);
                boolean isHex = new Regex("^[0-9a-f]+$").matches(compact);
                if (!StringsKt.contains$default((CharSequence) raw, (CharSequence) "--", false, 2, (Object) null)) {
                    if (isHex) {
                        if (compact.length() < 8) {
                            if (compact.length() >= 4) {
                                String $this$any$iv5 = compact;
                                int i = 0;
                                while (true) {
                                    if (i >= $this$any$iv5.length()) {
                                        z10 = false;
                                        break;
                                    }
                                    char element$iv4 = $this$any$iv5.charAt(i);
                                    if (Character.isDigit(element$iv4)) {
                                        z10 = true;
                                        break;
                                    }
                                    i++;
                                }
                                if (z10) {
                                    String $this$any$iv6 = compact;
                                    int i2 = 0;
                                    while (true) {
                                        if (i2 >= $this$any$iv6.length()) {
                                            z11 = false;
                                            break;
                                        }
                                        char element$iv5 = $this$any$iv6.charAt(i2);
                                        char it8 = ('a' > element$iv5 || element$iv5 >= 'g') ? (char) 0 : (char) 1;
                                        if (it8 != 0) {
                                            z11 = true;
                                            break;
                                        }
                                        i2++;
                                    }
                                }
                            }
                        }
                    }
                    z5 = false;
                    boolean isId = z5;
                    Regex shortNum = new Regex("^\\d{1,2}$");
                    Regex year = new Regex("^\\d{4}$");
                    $this$any$iv = r.getTitles();
                    if (($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                        it = $this$any$iv.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z6 = false;
                                break;
                            }
                            Object element$iv6 = it.next();
                            String it9 = (String) element$iv6;
                            if (matches$m$default(nq, tokens, shortNum, year, it9, false, 32, null)) {
                                z6 = true;
                                break;
                            }
                        }
                    } else {
                        z6 = false;
                    }
                    if (!z6) {
                        if (isDate) {
                            Iterable $this$any$iv7 = r.getDates();
                            if (!($this$any$iv7 instanceof Collection) || !((Collection) $this$any$iv7).isEmpty()) {
                                Iterator it10 = $this$any$iv7.iterator();
                                while (true) {
                                    if (!it10.hasNext()) {
                                        z9 = false;
                                        break;
                                    }
                                    Object element$iv7 = it10.next();
                                    String it11 = (String) element$iv7;
                                    if (matches$m(nq, tokens, shortNum, year, it11, true)) {
                                        z9 = true;
                                        break;
                                    }
                                }
                            } else {
                                z9 = false;
                            }
                        }
                        if (isTime) {
                            Iterable $this$any$iv8 = r.getTimes();
                            if (!($this$any$iv8 instanceof Collection) || !((Collection) $this$any$iv8).isEmpty()) {
                                Iterator it12 = $this$any$iv8.iterator();
                                while (true) {
                                    if (!it12.hasNext()) {
                                        z8 = false;
                                        break;
                                    }
                                    Object element$iv8 = it12.next();
                                    String it13 = (String) element$iv8;
                                    if (matches$m$default(nq, tokens, shortNum, year, it13, false, 32, null)) {
                                        z8 = true;
                                        break;
                                    }
                                }
                            } else {
                                z8 = false;
                            }
                        }
                        if (!isId) {
                            return false;
                        }
                        Iterable $this$any$iv9 = r.getIds();
                        if (!($this$any$iv9 instanceof Collection) || !((Collection) $this$any$iv9).isEmpty()) {
                            Iterator it14 = $this$any$iv9.iterator();
                            while (true) {
                                if (!it14.hasNext()) {
                                    z7 = false;
                                    break;
                                }
                                Object element$iv9 = it14.next();
                                String it15 = (String) element$iv9;
                                if (matches$m$default(nq, tokens, shortNum, year, it15, false, 32, null)) {
                                    z7 = true;
                                    break;
                                }
                            }
                        } else {
                            z7 = false;
                        }
                        if (!z7) {
                            return false;
                        }
                    }
                    return true;
                }
                z5 = true;
                boolean isId2 = z5;
                Regex shortNum2 = new Regex("^\\d{1,2}$");
                Regex year2 = new Regex("^\\d{4}$");
                $this$any$iv = r.getTitles();
                if ($this$any$iv instanceof Collection) {
                }
                it = $this$any$iv.iterator();
                while (true) {
                    if (!it.hasNext()) {
                    }
                }
                if (!z6) {
                }
                return true;
            }
        }
        z4 = true;
        boolean isTime2 = z4;
        String compact2 = StringsKt.replace$default(nq, " ", "", false, 4, (Object) null);
        boolean isHex2 = new Regex("^[0-9a-f]+$").matches(compact2);
        if (!StringsKt.contains$default((CharSequence) raw, (CharSequence) "--", false, 2, (Object) null)) {
        }
        z5 = true;
        boolean isId22 = z5;
        Regex shortNum22 = new Regex("^\\d{1,2}$");
        Regex year22 = new Regex("^\\d{4}$");
        $this$any$iv = r.getTitles();
        if ($this$any$iv instanceof Collection) {
        }
        it = $this$any$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
            }
        }
        if (!z6) {
        }
        return true;
    }

    static /* synthetic */ boolean matches$m$default(String str, List list, Regex regex, Regex regex2, String str2, boolean z, int i, Object obj) {
        if ((i & 32) != 0) {
            z = false;
        }
        return matches$m(str, list, regex, regex2, str2, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00e3 A[LOOP:2: B:38:0x00a5->B:48:0x00e3, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e1 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final boolean matches$m(String nq, List<String> list, Regex shortNum, Regex year, String v, boolean dateValue) {
        List st;
        boolean z;
        char c;
        boolean z2;
        boolean startsWith$default;
        boolean z3 = false;
        char c2 = 2;
        if (StringsKt.contains$default((CharSequence) v, (CharSequence) nq, false, 2, (Object) null)) {
            return true;
        }
        Iterable $this$filter$iv = StringsKt.split$default((CharSequence) v, new String[]{" "}, false, 0, 6, (Object) null);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it = (String) element$iv$iv;
            if (it.length() > 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List st2 = (List) destination$iv$iv;
        List<String> $this$all$iv = list;
        if (($this$all$iv instanceof Collection) && $this$all$iv.isEmpty()) {
            return true;
        }
        for (Object element$iv : $this$all$iv) {
            String qt = (String) element$iv;
            List $this$any$iv = st2;
            if (($this$any$iv instanceof Collection) && $this$any$iv.isEmpty()) {
                st = st2;
                z = z3;
                z2 = z;
                c = c2;
            } else {
                Iterator it2 = $this$any$iv.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        st = st2;
                        z = z3;
                        c = c2;
                        z2 = z;
                        break;
                    }
                    Object element$iv2 = it2.next();
                    String s = (String) element$iv2;
                    if (dateValue && shortNum.matches(qt)) {
                        if (year.matches(s)) {
                            st = st2;
                            c = 2;
                            z = false;
                            startsWith$default = false;
                            if (startsWith$default) {
                                z2 = true;
                                break;
                            }
                            c2 = c;
                            z3 = z;
                            st2 = st;
                        }
                        st = st2;
                        c = 2;
                        z = false;
                        startsWith$default = StringsKt.startsWith$default(s, qt, false, 2, (Object) null);
                        if (startsWith$default) {
                        }
                    }
                    st = st2;
                    c = 2;
                    z = false;
                    startsWith$default = StringsKt.startsWith$default(s, qt, false, 2, (Object) null);
                    if (startsWith$default) {
                    }
                }
            }
            if (!z2) {
                return z;
            }
            c2 = c;
            z3 = z;
            st2 = st;
        }
        return true;
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
                        sorted$lambda$44 = GxRecordingsPage.sorted$lambda$44(Function2.this, obj, obj2);
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
                        sorted$lambda$46 = GxRecordingsPage.sorted$lambda$46(Function2.this, obj, obj2);
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
            Iterator<T> it = this.routes.iterator();
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
            if ((((!this.preservedOnly || it3.getIsPreserved()) && matches(it3, this.search)) ? 1 : null) != null) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List visible = sorted((List) destination$iv$iv);
        if (visible.isEmpty() && !this.routesLoading) {
            list.addView(empty("No routes found."));
        }
        for (GxRecordingsRoute r : CollectionsKt.take(visible, 250)) {
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
                routeRow$lambda$53 = GxRecordingsPage.routeRow$lambda$53(gen, this);
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

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void togglePreserved$default(GxRecordingsPage gxRecordingsPage, GxRecordingsRoute gxRecordingsRoute, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        gxRecordingsPage.togglePreserved(gxRecordingsRoute, function0);
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
            this$0.toast((StringsKt.startsWith$default(err, "HTTP", false, 2, (Object) null) || Intrinsics.areEqual(err, "bad reply")) ? "Failed to update preserved state." : err);
        } else {
            $r.setPreserved(!$r.getIsPreserved());
            this$0.renderRoutes();
            if ($then != null) {
                $then.invoke();
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void deleteRoute$default(GxRecordingsPage gxRecordingsPage, GxRecordingsRoute gxRecordingsRoute, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        gxRecordingsPage.deleteRoute(gxRecordingsRoute, function0);
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
                deleteRoute$lambda$61$lambda$60 = GxRecordingsPage.deleteRoute$lambda$61$lambda$60(GxRecordingsPage.this, $r, $then, (String) obj, (JSONObject) obj2);
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
                deleteAllRoutes$lambda$64$lambda$63 = GxRecordingsPage.deleteAllRoutes$lambda$64$lambda$63(GxRecordingsPage.this, (String) obj, (JSONObject) obj2);
                return deleteAllRoutes$lambda$64$lambda$63;
            }
        }, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0030, code lost:
    
        if (r0 != null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit deleteAllRoutes$lambda$64$lambda$63(GxRecordingsPage this$0, String err, JSONObject o) {
        String str;
        String str$default;
        if (err != null) {
            this$0.toast(Intrinsics.areEqual(err, "bad reply") ? "Failed to delete routes." : err);
        } else {
            if (o != null && (str$default = GxPage.str$default(this$0, o, "message", null, 2, null)) != null) {
                String str2 = str$default;
                if (StringsKt.isBlank(str2)) {
                    str2 = null;
                }
                str = str2;
            }
            str = "Routes deleted!";
            this$0.toast(str);
            this$0.loadRoutes();
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void renameRoute$default(GxRecordingsPage gxRecordingsPage, GxRecordingsRoute gxRecordingsRoute, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        gxRecordingsPage.renameRoute(gxRecordingsRoute, function0);
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
                GxRecordingsPage.renameRoute$lambda$69($this$renameRoute_u24lambda_u2465, r, this, then, dialogInterface, i);
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
                renameRoute$lambda$69$lambda$68 = GxRecordingsPage.renameRoute$lambda$69$lambda$68(GxRecordingsPage.this, $r, n, $then, (String) obj, (JSONObject) obj2);
                return renameRoute$lambda$69$lambda$68;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
    
        if (r0 != null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit renameRoute$lambda$69$lambda$68(GxRecordingsPage this$0, GxRecordingsRoute $r, String $n, Function0 $then, String err, JSONObject o) {
        String str;
        String str$default;
        if (err != null) {
            this$0.toast("Rename failed.");
        } else {
            if (o != null && (str$default = GxPage.str$default(this$0, o, "name", null, 2, null)) != null) {
                String str2 = str$default;
                if (StringsKt.isBlank(str2)) {
                    str2 = null;
                }
                str = str2;
            }
            str = $n;
            $r.setTimestamp(str);
            $r.setCustomName(true);
            this$0.normalize($r);
            this$0.renderRoutes();
            this$0.toast("Route renamed!");
            if ($then != null) {
                $then.invoke();
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
                renameRoute$lambda$72$lambda$71 = GxRecordingsPage.renameRoute$lambda$72$lambda$71(GxRecordingsPage.this, $r, $then, (String) obj, (JSONObject) obj2);
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
        final List segs;
        String gxRecordingsBytes;
        String gxRecordingsBytes2;
        if (err != null || o == null) {
            this$0.toast((err == null || StringsKt.startsWith$default(err, "HTTP", false, 2, (Object) null) || Intrinsics.areEqual(err, "bad reply")) ? "Could not read logs." : err);
            return Unit.INSTANCE;
        }
        JSONArray optJSONArray = o.optJSONArray("segments");
        if (optJSONArray == null || (segs = this$0.objects(optJSONArray)) == null) {
            segs = CollectionsKt.emptyList();
        }
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
                openLogs$lambda$82$lambda$73 = GxRecordingsPage.openLogs$lambda$82$lambda$73(GxRecordingsPage.this, $r);
                return openLogs$lambda$82$lambda$73;
            }
        });
        GxPage.Row.button$default(top, "Find on map", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit openLogs$lambda$82$lambda$79;
                openLogs$lambda$82$lambda$79 = GxRecordingsPage.openLogs$lambda$82$lambda$79(GxRecordingsPage.this, $r, segs);
                return openLogs$lambda$82$lambda$79;
            }
        }, 2, null);
        for (final JSONObject seg : segs) {
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
                    openLogs$lambda$82$lambda$81 = GxRecordingsPage.openLogs$lambda$82$lambda$81(GxRecordingsPage.this, seg, segName, fn);
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
                JSONObject it3 = it2.optInt("segmentNum") == n ? 1 : null;
                if (it3 != null) {
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
                openLogs$lambda$82$lambda$79$lambda$78$lambda$77 = GxRecordingsPage.openLogs$lambda$82$lambda$79$lambda$78$lambda$77(GxRecordingsPage.Sheet.this, this$0, $r, t, seg, n, ((Integer) obj).intValue());
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

    /* JADX WARN: Type inference failed for: r2v1, types: [T, okhttp3.Call] */
    private final void loadScreens() {
        if (this.screenList == null || getHost() == null) {
            return;
        }
        Call call = this.screenCall;
        if (call != null) {
            call.cancel();
        }
        this.screens.clear();
        this.screenLoading = true;
        this.screenError = "";
        renderScreens();
        final HashSet seen = new HashSet();
        final Ref.ObjectRef call2 = new Ref.ObjectRef();
        call2.element = stream("/api/screen_recordings/list", new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadScreens$lambda$91;
                loadScreens$lambda$91 = GxRecordingsPage.loadScreens$lambda$91(GxRecordingsPage.this, seen, (JSONObject) obj);
                return loadScreens$lambda$91;
            }
        }, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit loadScreens$lambda$92;
                loadScreens$lambda$92 = GxRecordingsPage.loadScreens$lambda$92(GxRecordingsPage.this, call2, (String) obj);
                return loadScreens$lambda$92;
            }
        });
        this.screenCall = (Call) call2.element;
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
        for (JSONObject j : arr) {
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
                    public final int compare(T t, T t2) {
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
            return StringsKt.replace$default(new Regex("\\.mp4$", RegexOption.IGNORE_CASE).replace(rec.getFilename(), ""), "_", " ", false, 4, (Object) null);
        }
        try {
            Result.Companion companion = Result.Companion;
            GxRecordingsPage gxRecordingsPage = this;
            m1191constructorimpl = Result.m1191constructorimpl(LocalDateTime.parse(rec.getTimestamp()));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1197isFailureimpl(m1191constructorimpl)) {
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
                    renderScreens$lambda$96 = GxRecordingsPage.renderScreens$lambda$96(gen, this);
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

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void renameScreen$default(GxRecordingsPage gxRecordingsPage, GxRecordingsScreen gxRecordingsScreen, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        gxRecordingsPage.renameScreen(gxRecordingsScreen, function0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renameScreen(final GxRecordingsScreen rec, final Function0<Unit> then) {
        final String base = new Regex("\\.mp4$", RegexOption.IGNORE_CASE).replace(rec.getFilename(), "");
        GxPage.prompt$default(this, "Rename recording:", null, base, 0, null, new Function1() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renameScreen$lambda$103;
                renameScreen$lambda$103 = GxRecordingsPage.renameScreen$lambda$103(base, this, rec, then, (String) obj);
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
                renameScreen$lambda$103$lambda$102 = GxRecordingsPage.renameScreen$lambda$103$lambda$102(GxRecordingsPage.this, $then, (String) obj, (JSONObject) obj2);
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

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void deleteScreen$default(GxRecordingsPage gxRecordingsPage, GxRecordingsScreen gxRecordingsScreen, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        gxRecordingsPage.deleteScreen(gxRecordingsScreen, function0);
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
                deleteScreen$lambda$106$lambda$105 = GxRecordingsPage.deleteScreen$lambda$106$lambda$105(GxRecordingsPage.this, $then, $rec, (String) obj, (JSONObject) obj2);
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
                    deleteScreen$lambda$106$lambda$105$lambda$104 = GxRecordingsPage.deleteScreen$lambda$106$lambda$105$lambda$104(GxRecordingsScreen.this, (GxRecordingsScreen) obj);
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
                deleteAllScreens$lambda$109$lambda$108 = GxRecordingsPage.deleteAllScreens$lambda$109$lambda$108(GxRecordingsPage.this, (String) obj, (JSONObject) obj2);
                return deleteAllScreens$lambda$109$lambda$108;
            }
        }, 4, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0038, code lost:
    
        if (r0 != null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit deleteAllScreens$lambda$109$lambda$108(GxRecordingsPage this$0, String err, JSONObject o) {
        String str;
        String str$default;
        if (err != null) {
            this$0.toast(Intrinsics.areEqual(err, "bad reply") ? "Delete failed." : err);
        } else {
            this$0.screens.clear();
            this$0.renderScreens();
            if (o != null && (str$default = GxPage.str$default(this$0, o, "message", null, 2, null)) != null) {
                String str2 = str$default;
                if (StringsKt.isBlank(str2)) {
                    str2 = null;
                }
                str = str2;
            }
            str = "All screen recordings deleted!";
            this$0.toast(str);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxRecordings.kt */
    final class RoutePlayer {
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
                    _init_$lambda$15 = GxRecordingsPage.RoutePlayer._init_$lambda$15(GxRecordingsPage.this, this);
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
            for (TextView v : CollectionsKt.listOf((Object[]) new TextView[]{this.prev, this.segLabel, this.next})) {
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
                    _init_$lambda$19 = GxRecordingsPage.RoutePlayer._init_$lambda$19(GxRecordingsPage.this, this);
                    return _init_$lambda$19;
                }
            }), gap());
            CarUi ui3 = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage6 = this.this$0;
            acts.addView(CarUi.button$default(ui3, "Logs", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$20;
                    _init_$lambda$20 = GxRecordingsPage.RoutePlayer._init_$lambda$20(GxRecordingsPage.this, this);
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
                    _init_$lambda$23 = GxRecordingsPage.RoutePlayer._init_$lambda$23(GxRecordingsPage.this, this);
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
                    _init_$lambda$25 = GxRecordingsPage.RoutePlayer._init_$lambda$25(GxRecordingsPage.this, this);
                    return _init_$lambda$25;
                }
            }, 2, null), gap());
            CarUi ui6 = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage9 = this.this$0;
            TextView $this$_init__u24lambda_u2428 = CarUi.button$default(ui6, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda21
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$27;
                    _init_$lambda$27 = GxRecordingsPage.RoutePlayer._init_$lambda$27(GxRecordingsPage.this, this);
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
            return this.paintQual;
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
                    return GxRecordingsPage.RoutePlayer.lambda$15$lambda$14(GxRecordingsPage.RoutePlayer.this, ((Integer) obj).intValue());
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
                    return GxRecordingsPage.RoutePlayer.lambda$23$lambda$22(GxRecordingsPage.RoutePlayer.this);
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
                    return GxRecordingsPage.RoutePlayer.lambda$25$lambda$24(GxRecordingsPage.RoutePlayer.this);
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
                    return GxRecordingsPage.RoutePlayer.lambda$27$lambda$26(GxRecordingsPage.RoutePlayer.this);
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
                Result.m1191constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                Result.m1191constructorimpl(ResultKt.createFailure(th));
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
                MatchResult matchResult = (MatchResult) SequencesKt.lastOrNull(Regex.findAll$default(new Regex("--(\\d+)(?=[/?]|$)"), it, 0, 2, null));
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
                m1191constructorimpl = Result.m1191constructorimpl(new JSONObject(text));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m1197isFailureimpl(m1191constructorimpl)) {
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
            Iterable $this$filter$iv2 = CollectionsKt.listOf((Object[]) new String[]{"forward", "wide", "driver"});
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
                    fetch$lambda$48$lambda$46 = GxRecordingsPage.RoutePlayer.fetch$lambda$48$lambda$46(GxRecordingsPage.RoutePlayer.this, ((Integer) obj).intValue(), (Function1) obj2);
                    return fetch$lambda$48$lambda$46;
                }
            });
            LinearLayout cb = (LinearLayout) chipBar.component1();
            Function1 paintCam = (Function1) chipBar.component2();
            this$0.camBox.removeAllViews();
            this$0.camBox.addView(cb);
            paintCam.invoke(Integer.valueOf(this$0.cameras.indexOf(this$0.cam)));
            Pair chipBar2 = this$1.chipBar(CollectionsKt.listOf((Object[]) new String[]{"Full quality", "Preview"}), new Function2() { // from class: com.starpilot.starview.GxRecordingsPage$RoutePlayer$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit fetch$lambda$48$lambda$47;
                    fetch$lambda$48$lambda$47 = GxRecordingsPage.RoutePlayer.fetch$lambda$48$lambda$47(GxRecordingsPage.RoutePlayer.this, ((Integer) obj).intValue(), (Function1) obj2);
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
            return seg + (StringsKt.contains$default((CharSequence) seg, (CharSequence) "?", false, 2, (Object) null) ? "&" : "?") + CollectionsKt.joinToString$default(parts, "&", null, null, 0, null, null, 62, null);
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
                Result.m1191constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                Result.m1191constructorimpl(ResultKt.createFailure(th));
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
                    probe$lambda$51 = GxRecordingsPage.RoutePlayer.probe$lambda$51(t, this, u, attempt, gxRecordingsPage2, ((Integer) obj).intValue());
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
    final class ScreenPlayer {
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
                    _init_$lambda$7 = GxRecordingsPage.ScreenPlayer._init_$lambda$7(GxRecordingsPage.this);
                    return _init_$lambda$7;
                }
            };
            CarUi ui = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage3 = this.this$0;
            acts.addView(ui.button("Download", true, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$8;
                    _init_$lambda$8 = GxRecordingsPage.ScreenPlayer._init_$lambda$8(GxRecordingsPage.this, this);
                    return _init_$lambda$8;
                }
            }), (ViewGroup.LayoutParams) lp.invoke());
            CarUi ui2 = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage4 = this.this$0;
            acts.addView(CarUi.button$default(ui2, "Rename", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$10;
                    _init_$lambda$10 = GxRecordingsPage.ScreenPlayer._init_$lambda$10(GxRecordingsPage.this, this);
                    return _init_$lambda$10;
                }
            }, 2, null), (ViewGroup.LayoutParams) lp.invoke());
            CarUi ui3 = this.this$0.getUi();
            final GxRecordingsPage gxRecordingsPage5 = this.this$0;
            TextView $this$_init__u24lambda_u2413 = CarUi.button$default(ui3, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxRecordingsPage$ScreenPlayer$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit _init_$lambda$12;
                    _init_$lambda$12 = GxRecordingsPage.ScreenPlayer._init_$lambda$12(GxRecordingsPage.this, this);
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
                    return GxRecordingsPage.ScreenPlayer.lambda$10$lambda$9(GxRecordingsPage.ScreenPlayer.this);
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
                    return GxRecordingsPage.ScreenPlayer.lambda$12$lambda$11(GxRecordingsPage.ScreenPlayer.this);
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
                Result.m1191constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                Result.m1191constructorimpl(ResultKt.createFailure(th));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxRecordings.kt */
    final class Sheet {
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
                chipBar$lambda$111 = GxRecordingsPage.chipBar$lambda$111(views, this, ((Integer) obj).intValue());
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
                    GxRecordingsPage.chipBar$lambda$113$lambda$112(Function2.this, i2, paint, view);
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
        Bitmap cached = get(path);
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
                Bitmap cached = get(j.getPath());
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void fetchThumb$lambda$126(GxRecordingsPage this$0, String $u, final Function1 $then) {
        Object m1191constructorimpl;
        Response execute;
        ResponseBody body;
        byte[] data;
        Bitmap decodeByteArray;
        try {
            Result.Companion companion = Result.Companion;
            try {
                execute = GxPage.Companion.client(30L).newCall(new Request.Builder().url($u).build()).execute();
            } catch (Throwable th) {
                th = th;
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
                final Bitmap bmp = (Bitmap) (Result.m1197isFailureimpl(m1191constructorimpl) ? null : m1191constructorimpl);
                this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda43
                    @Override // java.lang.Runnable
                    public final void run() {
                        Function1.this.invoke(bmp);
                    }
                });
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            Response r = execute;
            if (!r.isSuccessful() || (body = r.body()) == null || (data = body.bytes()) == null) {
                decodeByteArray = null;
            } else {
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(data, 0, data.length, o);
                int s = 1;
                while (o.outWidth / (s * 2) >= 400) {
                    s *= 2;
                }
                int length = data.length;
                BitmapFactory.Options $this$fetchThumb_u24lambda_u24126_u24lambda_u24124_u24lambda_u24123_u24lambda_u24122_u24lambda_u24121 = new BitmapFactory.Options();
                $this$fetchThumb_u24lambda_u24126_u24lambda_u24124_u24lambda_u24123_u24lambda_u24122_u24lambda_u24121.inSampleSize = s;
                Unit unit = Unit.INSTANCE;
                decodeByteArray = BitmapFactory.decodeByteArray(data, 0, length, $this$fetchThumb_u24lambda_u24126_u24lambda_u24124_u24lambda_u24123_u24lambda_u24122_u24lambda_u24121);
            }
            CloseableKt.closeFinally(execute, null);
            m1191constructorimpl = Result.m1191constructorimpl(decodeByteArray);
            final Bitmap bmp2 = (Bitmap) (Result.m1197isFailureimpl(m1191constructorimpl) ? null : m1191constructorimpl);
            this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda43
                @Override // java.lang.Runnable
                public final void run() {
                    Function1.this.invoke(bmp2);
                }
            });
        } finally {
        }
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
            try {
                Response it = execute;
                int code = it.code();
                CloseableKt.closeFinally(execute, null);
                m1191constructorimpl = Result.m1191constructorimpl(Integer.valueOf(code));
            } finally {
            }
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1197isFailureimpl(m1191constructorimpl)) {
            m1191constructorimpl = -1;
        }
        final int code2 = ((Number) m1191constructorimpl).intValue();
        this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                GxRecordingsPage.head$lambda$130$lambda$129(Function1.this, code2);
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
                GxRecordingsPage.stream$lambda$135(Call.this, this, onData, onDone);
            }
        }).start();
        return c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void stream$lambda$135(final Call call, GxRecordingsPage gxRecordingsPage, Function1 function1, final Function1 function12) {
        Response execute;
        Response response;
        BufferedSource source;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        try {
            execute = call.execute();
            try {
                response = execute;
            } finally {
            }
        } catch (Exception e) {
            String message = e.getMessage();
            T t = message;
            if (message == null) {
                t = e.toString();
            }
            objectRef.element = t;
        }
        if (!response.isSuccessful()) {
            throw new IllegalStateException("HTTP " + response.code());
        }
        ResponseBody body = response.body();
        if (body == null || (source = body.getSource()) == null) {
            throw new IllegalStateException("empty reply");
        }
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readUtf8Line = source.readUtf8Line();
            if (readUtf8Line == null) {
                break;
            }
            boolean z = true;
            if (readUtf8Line.length() == 0) {
                stream$lambda$135$lambda$133$flush(sb, gxRecordingsPage, call, function1);
            } else if (StringsKt.startsWith$default(readUtf8Line, "data:", false, 2, (Object) null)) {
                if (sb.length() <= 0) {
                    z = false;
                }
                if (z) {
                    sb.append('\n');
                }
                String substring = readUtf8Line.substring(5);
                Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                sb.append(StringsKt.trimStart((CharSequence) substring).toString());
            }
        }
        stream$lambda$135$lambda$133$flush(sb, gxRecordingsPage, call, function1);
        Unit unit = Unit.INSTANCE;
        CloseableKt.closeFinally(execute, null);
        gxRecordingsPage.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                GxRecordingsPage.stream$lambda$135$lambda$134(Call.this, function12, objectRef);
            }
        });
    }

    private static final void stream$lambda$135$lambda$133$flush(StringBuilder sb, GxRecordingsPage this$0, final Call c, final Function1<? super JSONObject, Unit> function1) {
        Object m1191constructorimpl;
        if (sb.length() == 0) {
            return;
        }
        try {
            Result.Companion companion = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(new JSONObject(sb.toString()));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1197isFailureimpl(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        final JSONObject o = (JSONObject) m1191constructorimpl;
        sb.setLength(0);
        if (o != null) {
            this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxRecordingsPage$$ExternalSyntheticLambda36
                @Override // java.lang.Runnable
                public final void run() {
                    GxRecordingsPage.stream$lambda$135$lambda$133$flush$lambda$132(Call.this, function1, o);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stream$lambda$135$lambda$133$flush$lambda$132(Call $c, Function1 $onData, JSONObject $o) {
        if ($c.getCanceled()) {
            return;
        }
        $onData.invoke($o);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stream$lambda$135$lambda$134(Call $c, Function1 $onDone, Ref.ObjectRef $err) {
        if ($c.getCanceled()) {
            return;
        }
        $onDone.invoke($err.element);
    }
}
